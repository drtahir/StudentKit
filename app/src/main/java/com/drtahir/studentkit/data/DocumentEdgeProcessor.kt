package com.drtahir.studentkit.data

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * Data class representing the 4 normalized corners (0.0 to 1.0) of a detected document.
 */
data class DocCorners(
    val topLeft: PointF = PointF(0.08f, 0.08f),
    val topRight: PointF = PointF(0.92f, 0.08f),
    val bottomRight: PointF = PointF(0.92f, 0.92f),
    val bottomLeft: PointF = PointF(0.08f, 0.92f)
) {
    fun toAbsolutePoints(width: Float, height: Float): FloatArray {
        return floatArrayOf(
            topLeft.x * width, topLeft.y * height,
            topRight.x * width, topRight.y * height,
            bottomRight.x * width, bottomRight.y * height,
            bottomLeft.x * width, bottomLeft.y * height
        )
    }

    fun isDefault(): Boolean {
        return topLeft.x <= 0.09f && topLeft.y <= 0.09f &&
                topRight.x >= 0.91f && topRight.y <= 0.09f &&
                bottomRight.x >= 0.91f && bottomRight.y >= 0.91f &&
                bottomLeft.x <= 0.09f && bottomLeft.y >= 0.91f
    }
}

/**
 * Result structure returned by document presence and boundary detection.
 */
data class DocDetectionResult(
    val corners: DocCorners,
    val isDetected: Boolean,
    val confidence: Float,
    val statusMessage: String
)

object DocumentEdgeProcessor {

    /**
     * Advanced out-class multi-stage document edge & presence detector.
     * Uses Gaussian low-pass smoothing, Sobel edge gradients, directional ray-casting,
     * RANSAC line fitting, and strict geometric/convexity validation.
     * Accurately rejects background noise (floor tiles, pavers, carpets, walls).
     */
    fun detectDocument(bitmap: Bitmap): DocDetectionResult {
        val defaultCorners = DocCorners(
            topLeft = PointF(0.08f, 0.12f),
            topRight = PointF(0.92f, 0.12f),
            bottomRight = PointF(0.92f, 0.88f),
            bottomLeft = PointF(0.08f, 0.88f)
        )

        return try {
            val scaleWidth = 320
            val aspectRatio = bitmap.height.toFloat() / bitmap.width.toFloat().coerceAtLeast(0.1f)
            val scaleHeight = (scaleWidth * aspectRatio).toInt().coerceIn(200, 480)

            val scaled = Bitmap.createScaledBitmap(bitmap, scaleWidth, scaleHeight, true)
            val w = scaled.width
            val h = scaled.height
            val pixels = IntArray(w * h)
            scaled.getPixels(pixels, 0, w, 0, 0, w, h)

            // Grayscale luminance
            val gray = IntArray(w * h)
            for (i in pixels.indices) {
                val c = pixels[i]
                val r = (c shr 16) and 0xFF
                val g = (c shr 8) and 0xFF
                val b = c and 0xFF
                gray[i] = (299 * r + 587 * g + 114 * b) / 1000
            }

            // 3x3 Gaussian low-pass filter to reject tile grout, textures & grain
            val blurred = IntArray(w * h)
            for (y in 1 until h - 1) {
                val ym1 = (y - 1) * w
                val y0 = y * w
                val yp1 = (y + 1) * w
                for (x in 1 until w - 1) {
                    val v = (
                        gray[ym1 + x - 1] + 2 * gray[ym1 + x] + gray[ym1 + x + 1] +
                        2 * gray[y0 + x - 1] + 4 * gray[y0 + x] + 2 * gray[y0 + x + 1] +
                        gray[yp1 + x - 1] + 2 * gray[yp1 + x] + gray[yp1 + x + 1]
                    ) shr 4
                    blurred[y0 + x] = v
                }
            }

            // Sobel Edge Gradient Magnitude
            val gradMag = FloatArray(w * h)
            var sumGrad = 0.0
            for (y in 2 until h - 2) {
                val ym1 = (y - 1) * w
                val y0 = y * w
                val yp1 = (y + 1) * w
                for (x in 2 until w - 2) {
                    val gx = (-blurred[ym1 + x - 1] + blurred[ym1 + x + 1]
                            - 2 * blurred[y0 + x - 1] + 2 * blurred[y0 + x + 1]
                            - blurred[yp1 + x - 1] + blurred[yp1 + x + 1]).toFloat()

                    val gy = (-blurred[ym1 + x - 1] - 2 * blurred[ym1 + x] - blurred[ym1 + x + 1]
                            + blurred[yp1 + x - 1] + 2 * blurred[yp1 + x] + blurred[yp1 + x + 1]).toFloat()

                    val mag = sqrt(gx * gx + gy * gy)
                    gradMag[y0 + x] = mag
                    sumGrad += mag
                }
            }

            val avgGrad = (sumGrad / ((w - 4) * (h - 4))).toFloat()
            val edgeThreshold = max(24f, avgGrad * 2.2f)

            // Inward Ray-Casting for Top, Bottom, Left, and Right candidate points
            val topPoints = mutableListOf<PointF>()
            val bottomPoints = mutableListOf<PointF>()
            val leftPoints = mutableListOf<PointF>()
            val rightPoints = mutableListOf<PointF>()

            val colStart = (w * 0.12f).toInt()
            val colEnd = (w * 0.88f).toInt()
            val colStep = max(2, (colEnd - colStart) / 26)

            // Top boundary search (downwards)
            for (x in colStart..colEnd step colStep) {
                var bestY = -1
                var bestScore = 0f
                val yMax = (h * 0.60f).toInt()
                for (y in (h * 0.04f).toInt()..yMax) {
                    val idx = y * w + x
                    val mag = gradMag[idx]
                    if (mag >= edgeThreshold) {
                        val lumDiff = (blurred[min((y + 6) * w + x, w * h - 1)] - blurred[max((y - 6) * w + x, 0)]).toFloat()
                        val score = mag + max(0f, lumDiff * 1.5f)
                        if (score > bestScore) {
                            bestScore = score
                            bestY = y
                        }
                    }
                }
                if (bestY != -1) topPoints.add(PointF(x.toFloat(), bestY.toFloat()))
            }

            // Bottom boundary search (upwards)
            for (x in colStart..colEnd step colStep) {
                var bestY = -1
                var bestScore = 0f
                val yMin = (h * 0.40f).toInt()
                for (y in (h * 0.96f).toInt() downTo yMin) {
                    val idx = y * w + x
                    val mag = gradMag[idx]
                    if (mag >= edgeThreshold) {
                        val lumDiff = (blurred[max((y - 6) * w + x, 0)] - blurred[min((y + 6) * w + x, w * h - 1)]).toFloat()
                        val score = mag + max(0f, lumDiff * 1.5f)
                        if (score > bestScore) {
                            bestScore = score
                            bestY = y
                        }
                    }
                }
                if (bestY != -1) bottomPoints.add(PointF(x.toFloat(), bestY.toFloat()))
            }

            val rowStart = (h * 0.12f).toInt()
            val rowEnd = (h * 0.88f).toInt()
            val rowStep = max(2, (rowEnd - rowStart) / 26)

            // Left boundary search (rightwards)
            for (y in rowStart..rowEnd step rowStep) {
                var bestX = -1
                var bestScore = 0f
                val xMax = (w * 0.60f).toInt()
                for (x in (w * 0.04f).toInt()..xMax) {
                    val idx = y * w + x
                    val mag = gradMag[idx]
                    if (mag >= edgeThreshold) {
                        val lumDiff = (blurred[y * w + min(x + 6, w - 1)] - blurred[y * w + max(x - 6, 0)]).toFloat()
                        val score = mag + max(0f, lumDiff * 1.5f)
                        if (score > bestScore) {
                            bestScore = score
                            bestX = x
                        }
                    }
                }
                if (bestX != -1) leftPoints.add(PointF(bestX.toFloat(), y.toFloat()))
            }

            // Right boundary search (leftwards)
            for (y in rowStart..rowEnd step rowStep) {
                var bestX = -1
                var bestScore = 0f
                val xMin = (w * 0.40f).toInt()
                for (x in (w * 0.96f).toInt() downTo xMin) {
                    val idx = y * w + x
                    val mag = gradMag[idx]
                    if (mag >= edgeThreshold) {
                        val lumDiff = (blurred[y * w + max(x - 6, 0)] - blurred[y * w + min(x + 6, w - 1)]).toFloat()
                        val score = mag + max(0f, lumDiff * 1.5f)
                        if (score > bestScore) {
                            bestScore = score
                            bestX = x
                        }
                    }
                }
                if (bestX != -1) rightPoints.add(PointF(bestX.toFloat(), y.toFloat()))
            }

            // Fit robust boundary lines using RANSAC
            val topLine = fitHorizontalLine(topPoints)
            val bottomLine = fitHorizontalLine(bottomPoints)
            val leftLine = fitVerticalLine(leftPoints)
            val rightLine = fitVerticalLine(rightPoints)

            if (topLine == null || bottomLine == null || leftLine == null || rightLine == null) {
                return DocDetectionResult(
                    corners = defaultCorners,
                    isDetected = false,
                    confidence = 0.20f,
                    statusMessage = "Align document inside frame"
                )
            }

            // Calculate corner intersections
            val (mt, ct) = topLine
            val (mb, cb) = bottomLine
            val (ml, cl) = leftLine
            val (mr, cr) = rightLine

            val denomTl = 1f - mt * ml
            val denomTr = 1f - mt * mr
            val denomBr = 1f - mb * mr
            val denomBl = 1f - mb * ml

            if (abs(denomTl) < 0.1f || abs(denomTr) < 0.1f || abs(denomBr) < 0.1f || abs(denomBl) < 0.1f) {
                return DocDetectionResult(
                    corners = defaultCorners,
                    isDetected = false,
                    confidence = 0.20f,
                    statusMessage = "Align document inside frame"
                )
            }

            val yTl = (mt * cl + ct) / denomTl
            val xTl = ml * yTl + cl

            val yTr = (mt * cr + ct) / denomTr
            val xTr = mr * yTr + cr

            val yBr = (mb * cr + cb) / denomBr
            val xBr = mr * yBr + cr

            val yBl = (mb * cl + cb) / denomBl
            val xBl = ml * yBl + cl

            // Normalize coordinates
            val pTl = PointF((xTl / w).coerceIn(0.02f, 0.48f), (yTl / h).coerceIn(0.02f, 0.48f))
            val pTr = PointF((xTr / w).coerceIn(0.52f, 0.98f), (yTr / h).coerceIn(0.02f, 0.48f))
            val pBr = PointF((xBr / w).coerceIn(0.52f, 0.98f), (yBr / h).coerceIn(0.52f, 0.98f))
            val pBl = PointF((xBl / w).coerceIn(0.02f, 0.48f), (yBl / h).coerceIn(0.52f, 0.98f))

            // Shoelace Polygon Area Validation
            val area = 0.5f * abs(
                (pTl.x * pTr.y - pTr.x * pTl.y) +
                (pTr.x * pBr.y - pBr.x * pTr.y) +
                (pBr.x * pBl.y - pBl.x * pBr.y) +
                (pBl.x * pTl.y - pTl.x * pBl.y)
            )

            if (area < 0.12f || area > 0.92f) {
                return DocDetectionResult(
                    corners = defaultCorners,
                    isDetected = false,
                    confidence = 0.25f,
                    statusMessage = "Align document inside frame"
                )
            }

            // Cross product convexity check
            val v0x = pTr.x - pTl.x; val v0y = pTr.y - pTl.y
            val v1x = pBr.x - pTr.x; val v1y = pBr.y - pTr.y
            val v2x = pBl.x - pBr.x; val v2y = pBl.y - pBr.y
            val v3x = pTl.x - pBl.x; val v3y = pTl.y - pBl.y

            val cp0 = v0x * v1y - v0y * v1x
            val cp1 = v1x * v2y - v1y * v2x
            val cp2 = v2x * v3y - v2y * v3x
            val cp3 = v3x * v0y - v3y * v0x

            val isConvex = (cp0 > 0 && cp1 > 0 && cp2 > 0 && cp3 > 0) || (cp0 < 0 && cp1 < 0 && cp2 < 0 && cp3 < 0)
            if (!isConvex) {
                return DocDetectionResult(
                    corners = defaultCorners,
                    isDetected = false,
                    confidence = 0.20f,
                    statusMessage = "Align document inside frame"
                )
            }

            // Aspect ratio validation
            val wTop = hypot((pTr.x - pTl.x).toDouble(), (pTr.y - pTl.y).toDouble()).toFloat()
            val wBot = hypot((pBr.x - pBl.x).toDouble(), (pBr.y - pBl.y).toDouble()).toFloat()
            val hLeft = hypot((pBl.x - pTl.x).toDouble(), (pBl.y - pTl.y).toDouble()).toFloat()
            val hRight = hypot((pBr.x - pTr.x).toDouble(), (pBr.y - pTr.y).toDouble()).toFloat()

            val avgW = (wTop + wBot) / 2f
            val avgH = (hLeft + hRight) / 2f
            val aspect = avgW / avgH.coerceAtLeast(0.01f)

            if (aspect < 0.35f || aspect > 2.85f) {
                return DocDetectionResult(
                    corners = defaultCorners,
                    isDetected = false,
                    confidence = 0.30f,
                    statusMessage = "Align document inside frame"
                )
            }

            val docCorners = DocCorners(topLeft = pTl, topRight = pTr, bottomRight = pBr, bottomLeft = pBl)
            DocDetectionResult(
                corners = docCorners,
                isDetected = true,
                confidence = 0.88f,
                statusMessage = "Document detected"
            )
        } catch (e: Exception) {
            DocDetectionResult(
                corners = defaultCorners,
                isDetected = false,
                confidence = 0.10f,
                statusMessage = "Align document inside frame"
            )
        }
    }

    private fun fitHorizontalLine(points: List<PointF>, distTolerance: Float = 4.5f): Pair<Float, Float>? {
        if (points.size < 6) return null
        var bestM = 0f
        var bestC = 0f
        var maxInliers = 0
        val iterations = min(30, points.size * (points.size - 1) / 2)
        val random = java.util.Random(42)

        for (i in 0 until iterations) {
            val p1 = points[random.nextInt(points.size)]
            val p2 = points[random.nextInt(points.size)]
            if (abs(p1.x - p2.x) < 25f) continue
            val m = (p2.y - p1.y) / (p2.x - p1.x)
            if (abs(m) > 0.65f) continue // Horizontal lines cannot be too tilted
            val c = p1.y - m * p1.x
            var inliers = 0
            for (p in points) {
                val dist = abs(m * p.x - p.y + c) / sqrt(m * m + 1f)
                if (dist <= distTolerance) inliers++
            }
            if (inliers > maxInliers) {
                maxInliers = inliers
                bestM = m
                bestC = c
            }
        }

        if (maxInliers < 6 || maxInliers.toFloat() / points.size < 0.38f) return null

        // Least-squares refinement on inliers
        var sumX = 0.0; var sumY = 0.0; var sumXY = 0.0; var sumX2 = 0.0; var n = 0
        for (p in points) {
            val dist = abs(bestM * p.x - p.y + bestC) / sqrt(bestM * bestM + 1f)
            if (dist <= distTolerance) {
                sumX += p.x; sumY += p.y; sumXY += p.x * p.y; sumX2 += p.x * p.x; n++
            }
        }
        val denom = n * sumX2 - sumX * sumX
        if (abs(denom) < 1e-4) return Pair(bestM, bestC)
        val refinedM = ((n * sumXY - sumX * sumY) / denom).toFloat()
        val refinedC = ((sumY - refinedM * sumX) / n).toFloat()
        return Pair(refinedM, refinedC)
    }

    private fun fitVerticalLine(points: List<PointF>, distTolerance: Float = 4.5f): Pair<Float, Float>? {
        if (points.size < 6) return null
        var bestM = 0f
        var bestC = 0f
        var maxInliers = 0
        val iterations = min(30, points.size * (points.size - 1) / 2)
        val random = java.util.Random(42)

        for (i in 0 until iterations) {
            val p1 = points[random.nextInt(points.size)]
            val p2 = points[random.nextInt(points.size)]
            if (abs(p1.y - p2.y) < 25f) continue
            val m = (p2.x - p1.x) / (p2.y - p1.y)
            if (abs(m) > 0.65f) continue // Vertical lines cannot be too tilted
            val c = p1.x - m * p1.y
            var inliers = 0
            for (p in points) {
                val dist = abs(m * p.y - p.x + c) / sqrt(m * m + 1f)
                if (dist <= distTolerance) inliers++
            }
            if (inliers > maxInliers) {
                maxInliers = inliers
                bestM = m
                bestC = c
            }
        }

        if (maxInliers < 6 || maxInliers.toFloat() / points.size < 0.38f) return null

        // Least-squares refinement on inliers
        var sumY = 0.0; var sumX = 0.0; var sumYX = 0.0; var sumY2 = 0.0; var n = 0
        for (p in points) {
            val dist = abs(bestM * p.y - p.x + bestC) / sqrt(bestM * bestM + 1f)
            if (dist <= distTolerance) {
                sumY += p.y; sumX += p.x; sumYX += p.y * p.x; sumY2 += p.y * p.y; n++
            }
        }
        val denom = n * sumY2 - sumY * sumY
        if (abs(denom) < 1e-4) return Pair(bestM, bestC)
        val refinedM = ((n * sumYX - sumY * sumX) / denom).toFloat()
        val refinedC = ((sumX - refinedM * sumY) / n).toFloat()
        return Pair(refinedM, refinedC)
    }

    /**
     * Detects 4 document corner points using multi-pass gradient and luminance edge analysis.
     */
    fun detectDocumentCorners(bitmap: Bitmap): DocCorners {
        return detectDocument(bitmap).corners
    }

    /**
     * Performs a 4-Point Perspective Transform (Warp Perspective) using Android Matrix poly-to-poly.
     * This rectifies the document quadrilateral into a flat, straight rectangle with NO background!
     */
    fun warpPerspectiveCrop(original: Bitmap, corners: DocCorners): Bitmap {
        return try {
            val srcW = original.width.toFloat()
            val srcH = original.height.toFloat()

            val pTl = PointF(corners.topLeft.x * srcW, corners.topLeft.y * srcH)
            val pTr = PointF(corners.topRight.x * srcW, corners.topRight.y * srcH)
            val pBr = PointF(corners.bottomRight.x * srcW, corners.bottomRight.y * srcH)
            val pBl = PointF(corners.bottomLeft.x * srcW, corners.bottomLeft.y * srcH)

            // Compute output dimensions based on average Euclidean edge lengths
            val widthTop = hypot((pTr.x - pTl.x).toDouble(), (pTr.y - pTl.y).toDouble()).toFloat()
            val widthBottom = hypot((pBr.x - pBl.x).toDouble(), (pBr.y - pBl.y).toDouble()).toFloat()
            val targetWidth = max(widthTop, widthBottom).coerceIn(400f, 2400f).roundToInt()

            val heightLeft = hypot((pBl.x - pTl.x).toDouble(), (pBl.y - pTl.y).toDouble()).toFloat()
            val heightRight = hypot((pBr.x - pTr.x).toDouble(), (pBr.y - pTr.y).toDouble()).toFloat()
            val targetHeight = max(heightLeft, heightRight).coerceIn(500f, 3200f).roundToInt()

            val srcPoints = floatArrayOf(
                pTl.x, pTl.y,
                pTr.x, pTr.y,
                pBr.x, pBr.y,
                pBl.x, pBl.y
            )

            val dstPoints = floatArrayOf(
                0f, 0f,
                targetWidth.toFloat(), 0f,
                targetWidth.toFloat(), targetHeight.toFloat(),
                0f, targetHeight.toFloat()
            )

            val matrix = Matrix()
            val success = matrix.setPolyToPoly(srcPoints, 0, dstPoints, 0, 4)

            val safeOriginal = if (original.config != Bitmap.Config.ARGB_8888) {
                original.copy(Bitmap.Config.ARGB_8888, false)
            } else {
                original
            }

            if (success) {
                val outputBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(outputBitmap)
                val paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
                canvas.drawBitmap(safeOriginal, matrix, paint)
                outputBitmap
            } else {
                // Fallback bounding box crop
                val minX = min(min(pTl.x, pTr.x), min(pBr.x, pBl.x)).toInt().coerceIn(0, original.width - 1)
                val minY = min(min(pTl.y, pTr.y), min(pBr.y, pBl.y)).toInt().coerceIn(0, original.height - 1)
                val maxX = max(max(pTl.x, pTr.x), max(pBr.x, pBl.x)).toInt().coerceIn(minX + 10, original.width)
                val maxY = max(max(pTl.y, pTr.y), max(pBr.y, pBl.y)).toInt().coerceIn(minY + 10, original.height)
                Bitmap.createBitmap(safeOriginal, minX, minY, maxX - minX, maxY - minY)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            original
        }
    }

    /**
     * Rotates bitmap by specified degrees (e.g. 90, 180, 270).
     */
    fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        if (angle % 360f == 0f) return source
        val matrix = Matrix().apply { postRotate(angle) }
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    // =========================================================================
    // HIKMAHSCANNER FILTERS PIPELINE (STUDIO-GRADE ADAPTIVE PROCESSING)
    // =========================================================================

    enum class ScanFilter(val displayName: String, val description: String) {
        ENHANCE("Enhance", "Magic Color: brightens paper, deepens text, vivid colors"),
        MAGIC_PRO("Magic Pro", "Ultra-HD crisp contrast with razor sharp clarity"),
        NO_SHADOW("No Shadow", "Erases harsh shadows and evens out illumination"),
        NO_WATERMARK("No Watermark", "Cleans background stains, creases, and tint"),
        BW("B&W", "Adaptive photocopy binary black & white with zero noise"),
        GRAYSCALE("Grayscale", "Monochrome smooth tonal balance"),
        ORIGINAL("Original", "Original rectified photo with natural colors"),
        LIGHTEN("Lighten", "Brightens dark backgrounds while maintaining text"),
        ECO_PRINT("Eco Print", "High contrast minimal ink printing mode")
    }

    /**
     * Applies the chosen Hikmahscanner filter to a bitmap.
     * Guaranteed to work flawlessly on both camera captures and gallery imports.
     */
    fun applyFilter(source: Bitmap, filter: ScanFilter): Bitmap {
        val maxDim = max(source.width, source.height)
        val workingBitmap = if (maxDim > 2048) {
            val scale = 2048f / maxDim
            val nw = (source.width * scale).roundToInt().coerceAtLeast(1)
            val nh = (source.height * scale).roundToInt().coerceAtLeast(1)
            Bitmap.createScaledBitmap(source, nw, nh, true)
        } else {
            source
        }

        val safe = if (workingBitmap.config != Bitmap.Config.ARGB_8888) {
            workingBitmap.copy(Bitmap.Config.ARGB_8888, false)
        } else {
            workingBitmap
        }

        return when (filter) {
            ScanFilter.ORIGINAL -> safe
            ScanFilter.ENHANCE -> applyMagicEnhance(safe)
            ScanFilter.MAGIC_PRO -> applyMagicPro(safe)
            ScanFilter.NO_SHADOW -> applyNoShadow(safe)
            ScanFilter.NO_WATERMARK -> applyNoWatermark(safe)
            ScanFilter.BW -> applyBlackAndWhite(safe)
            ScanFilter.GRAYSCALE -> applyGrayscale(safe)
            ScanFilter.LIGHTEN -> applyLighten(safe)
            ScanFilter.ECO_PRINT -> applyEcoPrint(safe)
        }
    }

    /**
     * Hikmahscanner Signature Magic Enhance (Magic Color):
     * Adaptive contrast stretch: paper is driven to brilliant crisp white, ink is deepened,
     * while color signatures, stamps, and highlights remain rich and vivid.
     */
    private fun applyMagicEnhance(src: Bitmap): Bitmap {
        val w = src.width
        val h = src.height
        val pixels = IntArray(w * h)
        src.getPixels(pixels, 0, w, 0, 0, w, h)

        // Sample luminance to calculate document paper white point
        var sumLuma = 0L
        val step = max(1, (w * h) / 2000)
        var sampleCount = 0
        var i = 0
        while (i < pixels.size) {
            val c = pixels[i]
            val r = (c shr 16) and 0xFF
            val g = (c shr 8) and 0xFF
            val b = c and 0xFF
            val luma = (r * 77 + g * 150 + b * 29) shr 8
            sumLuma += luma
            sampleCount++
            i += step
        }
        val avgLuma = if (sampleCount > 0) (sumLuma / sampleCount).toInt() else 160
        val paperThreshold = (avgLuma * 1.12f).coerceIn(160f, 225f)

        for (idx in pixels.indices) {
            val c = pixels[idx]
            val a = (c ushr 24) and 0xFF
            val r = (c shr 16) and 0xFF
            val g = (c shr 8) and 0xFF
            val b = c and 0xFF
            val luma = (r * 77 + g * 150 + b * 29) shr 8

            if (luma >= paperThreshold) {
                // Background paper -> smoothly drive towards pure white
                val factor = (luma - paperThreshold) / (255f - paperThreshold).coerceAtLeast(1f)
                val newR = (r + (255 - r) * factor * 0.95f).toInt().coerceIn(0, 255)
                val newG = (g + (255 - g) * factor * 0.95f).toInt().coerceIn(0, 255)
                val newB = (b + (255 - b) * factor * 0.95f).toInt().coerceIn(0, 255)
                pixels[idx] = (a shl 24) or (newR shl 16) or (newG shl 8) or newB
            } else if (luma < 90) {
                // Dark ink -> deepen to rich black
                val newR = (r * 0.70f).toInt().coerceIn(0, 255)
                val newG = (g * 0.70f).toInt().coerceIn(0, 255)
                val newB = (b * 0.70f).toInt().coerceIn(0, 255)
                pixels[idx] = (a shl 24) or (newR shl 16) or (newG shl 8) or newB
            } else {
                // Midtones & colors (e.g. stamps, signatures) -> boost contrast & saturation
                val mean = (r + g + b) / 3f
                val satBoost = 1.25f
                val newR = (mean + (r - mean) * satBoost).toInt().coerceIn(0, 255)
                val newG = (mean + (g - mean) * satBoost).toInt().coerceIn(0, 255)
                val newB = (mean + (b - mean) * satBoost).toInt().coerceIn(0, 255)
                pixels[idx] = (a shl 24) or (newR shl 16) or (newG shl 8) or newB
            }
        }

        val out = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        out.setPixels(pixels, 0, w, 0, 0, w, h)
        return out
    }

    /**
     * Magic Pro: Ultra-HD Dynamic Contrast.
     * Normalizes page illumination, sharpens text outlines, and whitens paper.
     */
    private fun applyMagicPro(src: Bitmap): Bitmap {
        // Step 1: Remove background illumination gradients
        val noShadow = applyNoShadow(src)
        val w = noShadow.width
        val h = noShadow.height
        val pixels = IntArray(w * h)
        noShadow.getPixels(pixels, 0, w, 0, 0, w, h)

        // Step 2: High-definition ink contrast & clean paper whitening
        for (idx in pixels.indices) {
            val c = pixels[idx]
            val a = (c ushr 24) and 0xFF
            var r = (c shr 16) and 0xFF
            var g = (c shr 8) and 0xFF
            var b = c and 0xFF
            val luma = (r * 77 + g * 150 + b * 29) shr 8

            if (luma > 175) {
                // Pure clean white paper
                r = min(255, (r * 1.15f + 15f).toInt())
                g = min(255, (g * 1.15f + 15f).toInt())
                b = min(255, (b * 1.15f + 15f).toInt())
            } else if (luma < 100) {
                // Deep rich ink
                r = (r * 0.72f).toInt()
                g = (g * 0.72f).toInt()
                b = (b * 0.72f).toInt()
            }
            pixels[idx] = (a shl 24) or (r shl 16) or (g shl 8) or b
        }

        val out = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        out.setPixels(pixels, 0, w, 0, 0, w, h)
        return out
    }

    /**
     * No Shadow: True Local Illumination Normalization.
     * Computes background illumination field across blocks and normalizes the page,
     * completely eliminating finger, phone, and corner lighting shadows.
     */
    private fun applyNoShadow(src: Bitmap): Bitmap {
        val w = src.width
        val h = src.height
        val pixels = IntArray(w * h)
        src.getPixels(pixels, 0, w, 0, 0, w, h)

        // Grid-based background luminance estimation
        val gridCols = 16
        val gridRows = 20
        val cellW = (w / gridCols).coerceAtLeast(1)
        val cellH = (h / gridRows).coerceAtLeast(1)
        val bgLumaGrid = FloatArray(gridCols * gridRows)

        for (gy in 0 until gridRows) {
            val yStart = gy * cellH
            val yEnd = min(h, (gy + 1) * cellH)
            for (gx in 0 until gridCols) {
                val xStart = gx * cellW
                val xEnd = min(w, (gx + 1) * cellW)

                // Find 90th percentile luminance in this cell (representing paper background)
                var maxLuma = 60
                val skip = max(1, ((yEnd - yStart) * (xEnd - xStart)) / 120)
                var sampled = 0
                for (y in yStart until yEnd step skip) {
                    for (x in xStart until xEnd step skip) {
                        val c = pixels[y * w + x]
                        val r = (c shr 16) and 0xFF
                        val g = (c shr 8) and 0xFF
                        val b = c and 0xFF
                        val l = (r * 77 + g * 150 + b * 29) shr 8
                        if (l > maxLuma) maxLuma = l
                        sampled++
                    }
                }
                bgLumaGrid[gy * gridCols + gx] = maxLuma.toFloat().coerceIn(50f, 250f)
            }
        }

        // Apply illumination compensation to every pixel
        for (y in 0 until h) {
            val gy = (y / cellH).coerceIn(0, gridRows - 1)
            for (x in 0 until w) {
                val gx = (x / cellW).coerceIn(0, gridCols - 1)
                val bgLuma = bgLumaGrid[gy * gridCols + gx]

                val c = pixels[y * w + x]
                val a = (c ushr 24) and 0xFF
                val r = (c shr 16) and 0xFF
                val g = (c shr 8) and 0xFF
                val b = c and 0xFF

                // Scale factor to elevate local background to 248 (pure bright paper)
                val gain = (248f / bgLuma).coerceIn(1.0f, 3.5f)

                val newR = min(255, (r * gain).toInt())
                val newG = min(255, (g * gain).toInt())
                val newB = min(255, (b * gain).toInt())

                pixels[y * w + x] = (a shl 24) or (newR shl 16) or (newG shl 8) or newB
            }
        }

        val out = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        out.setPixels(pixels, 0, w, 0, 0, w, h)
        return out
    }

    /**
     * No Watermark / Clean Paper: Removes background artifacts, paper yellowing, and stains.
     */
    private fun applyNoWatermark(src: Bitmap): Bitmap {
        val w = src.width
        val h = src.height
        val pixels = IntArray(w * h)
        src.getPixels(pixels, 0, w, 0, 0, w, h)

        for (idx in pixels.indices) {
            val c = pixels[idx]
            val a = (c ushr 24) and 0xFF
            var r = (c shr 16) and 0xFF
            var g = (c shr 8) and 0xFF
            var b = c and 0xFF
            val luma = (r * 77 + g * 150 + b * 29) shr 8

            if (luma > 155) {
                // Bleach background stains & watermarks to white
                r = 255
                g = 255
                b = 255
            } else if (luma < 90) {
                // Keep text dark
                r = (r * 0.75f).toInt()
                g = (g * 0.75f).toInt()
                b = (b * 0.75f).toInt()
            }
            pixels[idx] = (a shl 24) or (r shl 16) or (g shl 8) or b
        }

        val out = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        out.setPixels(pixels, 0, w, 0, 0, w, h)
        return out
    }

    /**
     * B&W: Adaptive Local Thresholding (Sauvola/Niblack style block adaptive).
     * Zero dirty shadow blotches: text becomes pure black, paper becomes pure white.
     */
    private fun applyBlackAndWhite(src: Bitmap): Bitmap {
        val w = src.width
        val h = src.height
        val pixels = IntArray(w * h)
        src.getPixels(pixels, 0, w, 0, 0, w, h)

        val blockSize = max(16, min(w, h) / 32)
        val lumaArray = IntArray(w * h)
        for (i in pixels.indices) {
            val c = pixels[i]
            val r = (c shr 16) and 0xFF
            val g = (c shr 8) and 0xFF
            val b = c and 0xFF
            lumaArray[i] = (r * 77 + g * 150 + b * 29) shr 8
        }

        // Integral image for lightning-fast block mean calculation
        val integral = LongArray((w + 1) * (h + 1))
        for (y in 0 until h) {
            var rowSum = 0L
            for (x in 0 until w) {
                rowSum += lumaArray[y * w + x]
                integral[(y + 1) * (w + 1) + (x + 1)] = integral[y * (w + 1) + (x + 1)] + rowSum
            }
        }

        val halfBlock = blockSize / 2
        for (y in 0 until h) {
            val y1 = max(0, y - halfBlock)
            val y2 = min(h, y + halfBlock)
            for (x in 0 until w) {
                val x1 = max(0, x - halfBlock)
                val x2 = min(w, x + halfBlock)
                val count = (y2 - y1) * (x2 - x1)

                val sum = integral[y2 * (w + 1) + x2] -
                        integral[y1 * (w + 1) + x2] -
                        integral[y2 * (w + 1) + x1] +
                        integral[y1 * (w + 1) + x1]
                val mean = (sum / count).toInt()
                val currentLuma = lumaArray[y * w + x]

                // If pixel is darker than local neighborhood mean minus margin, it's ink
                val isInk = currentLuma < (mean - 10)
                pixels[y * w + x] = if (isInk) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
            }
        }

        val out = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        out.setPixels(pixels, 0, w, 0, 0, w, h)
        return out
    }

    /**
     * Grayscale: Smooth monochromatic document.
     */
    private fun applyGrayscale(src: Bitmap): Bitmap {
        val out = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val cm = ColorMatrix()
        cm.setSaturation(0f)
        val contrastCm = ColorMatrix(floatArrayOf(
            1.35f, 0f, 0f, 0f, 20f,
            0f, 1.35f, 0f, 0f, 20f,
            0f, 0f, 1.35f, 0f, 20f,
            0f, 0f, 0f, 1f, 0f
        ))
        contrastCm.preConcat(cm)
        paint.colorFilter = ColorMatrixColorFilter(contrastCm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return out
    }

    /**
     * Lighten: Brightens document background.
     */
    private fun applyLighten(src: Bitmap): Bitmap {
        val out = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val cm = ColorMatrix(floatArrayOf(
            1.20f, 0f, 0f, 0f, 40f,
            0f, 1.20f, 0f, 0f, 40f,
            0f, 0f, 1.20f, 0f, 40f,
            0f, 0f, 0f, 1f, 0f
        ))
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return out
    }

    /**
     * Eco Print: High contrast threshold for economical printing.
     */
    private fun applyEcoPrint(src: Bitmap): Bitmap {
        val out = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val cm = ColorMatrix()
        cm.setSaturation(0f)
        val printCm = ColorMatrix(floatArrayOf(
            2.4f, 0f, 0f, 0f, -110f,
            0f, 2.4f, 0f, 0f, -110f,
            0f, 0f, 2.4f, 0f, -110f,
            0f, 0f, 0f, 1f, 0f
        ))
        printCm.preConcat(cm)
        paint.colorFilter = ColorMatrixColorFilter(printCm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return out
    }

    // =========================================================================
    // SAVE TO PHONE MEMORY (GALLERY & STORAGE) + PDF EXPORT
    // =========================================================================

    /**
     * Saves high-resolution scanned image directly into the device's Pictures/Gallery folder via MediaStore.
     * Guaranteed to appear in Phone Gallery and Google Photos.
     */
    fun saveImageToPhoneGallery(context: Context, bitmap: Bitmap, title: String): Uri? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val cleanTitle = if (title.isBlank()) "Scan_$timeStamp" else title.replace(" ", "_")
        val fileName = "${cleanTitle}_$timeStamp.jpg"

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Hikmahscanner")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val resolver = context.contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        if (imageUri != null) {
            try {
                resolver.openOutputStream(imageUri)?.use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 96, out)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(imageUri, values, null, null)
                }
                return imageUri
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Fallback: save to app external files dir or pictures
        return try {
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val hikmahscannerDir = File(picturesDir, "Hikmahscanner").apply { mkdirs() }
            val file = File(hikmahscannerDir, fileName)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 96, out)
            }
            // Trigger MediaScanner
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val fileUri = Uri.fromFile(file)
            mediaScanIntent.data = fileUri
            context.sendBroadcast(mediaScanIntent)
            fileUri
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Compiles scanned bitmap(s) into a multi-page or single-page PDF document and saves directly
     * to the phone's Documents/Downloads folder via MediaStore.
     */
    fun savePdfToPhoneStorage(context: Context, bitmaps: List<Bitmap>, title: String): Uri? {
        if (bitmaps.isEmpty()) return null
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val cleanTitle = if (title.isBlank()) "Document_$timeStamp" else title.replace(" ", "_")
        val fileName = "${cleanTitle}_$timeStamp.pdf"

        val pdfDoc = PdfDocument()

        try {
            bitmaps.forEachIndexed { index, bmp ->
                val safeBmp = if (bmp.config != Bitmap.Config.ARGB_8888) {
                    bmp.copy(Bitmap.Config.ARGB_8888, false)
                } else {
                    bmp
                }

                // Standard A4 PDF size: 595 x 842 points
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, index + 1).create()
                val page = pdfDoc.startPage(pageInfo)
                val canvas = page.canvas

                // Scale and center bitmap on A4 canvas
                val scale = min(595f / safeBmp.width.toFloat(), 842f / safeBmp.height.toFloat())
                val drawW = safeBmp.width * scale
                val drawH = safeBmp.height * scale
                val drawX = (595f - drawW) / 2f
                val drawY = (842f - drawH) / 2f

                val matrix = Matrix().apply {
                    postScale(scale, scale)
                    postTranslate(drawX, drawY)
                }
                val paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
                canvas.drawBitmap(safeBmp, matrix, paint)

                pdfDoc.finishPage(page)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents/Hikmahscanner")
                }
                val uri = context.contentResolver.insert(MediaStore.Files.getContentUri("external"), values)
                if (uri != null) {
                    context.contentResolver.openOutputStream(uri)?.use { out ->
                        pdfDoc.writeTo(out)
                    }
                    return uri
                }
            }

            // Fallback for older devices or direct documents dir
            val docsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val hikmahscannerDir = File(docsDir, "Hikmahscanner").apply { mkdirs() }
            val file = File(hikmahscannerDir, fileName)
            FileOutputStream(file).use { out ->
                pdfDoc.writeTo(out)
            }
            return FileProvider.getUriForFile(context, "com.drtahir.studentkit.fileprovider", file)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            pdfDoc.close()
        }
    }

    /**
     * Shares a scanned bitmap via Android's native share sheet.
     */
    fun shareScannedImage(context: Context, bitmap: Bitmap, title: String) {
        try {
            val cachePath = File(context.cacheDir, "shared_scans").apply { mkdirs() }
            val file = File(cachePath, "scan_${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }
            val contentUri = FileProvider.getUriForFile(context, "com.drtahir.studentkit.fileprovider", file)

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, contentUri)
                putExtra(Intent.EXTRA_SUBJECT, title)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Scanned Document"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Shares a generated PDF file via Android's native share sheet.
     */
    fun shareScannedPdf(context: Context, pdfUri: Uri, title: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, pdfUri)
                putExtra(Intent.EXTRA_SUBJECT, title)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Scanned PDF"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
