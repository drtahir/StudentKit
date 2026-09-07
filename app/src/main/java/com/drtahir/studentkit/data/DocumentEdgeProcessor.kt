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

object DocumentEdgeProcessor {

    /**
     * Detects 4 document corner points using multi-pass gradient and luminance edge analysis.
     */
    fun detectDocumentCorners(bitmap: Bitmap): DocCorners {
        return try {
            val scaleWidth = 240
            val aspectRatio = bitmap.height.toFloat() / bitmap.width.toFloat().coerceAtLeast(0.1f)
            val scaleHeight = (scaleWidth * aspectRatio).toInt().coerceIn(160, 360)
            
            val scaled = Bitmap.createScaledBitmap(bitmap, scaleWidth, scaleHeight, true)
            val w = scaled.width
            val h = scaled.height
            val pixels = IntArray(w * h)
            scaled.getPixels(pixels, 0, w, 0, 0, w, h)

            val gray = IntArray(w * h)
            for (i in pixels.indices) {
                val c = pixels[i]
                val r = (c shr 16) and 0xFF
                val g = (c shr 8) and 0xFF
                val b = c and 0xFF
                gray[i] = (0.299f * r + 0.587f * g + 0.114f * b).toInt()
            }

            // Sobel Edge Gradient
            val grad = FloatArray(w * h)
            var maxGrad = 1f
            for (y in 1 until h - 1) {
                for (x in 1 until w - 1) {
                    val gx = (-gray[(y - 1) * w + (x - 1)] + gray[(y - 1) * w + (x + 1)]
                            - 2 * gray[y * w + (x - 1)] + 2 * gray[y * w + (x + 1)]
                            - gray[(y + 1) * w + (x - 1)] + gray[(y + 1) * w + (x + 1)]).toFloat()

                    val gy = (-gray[(y - 1) * w + (x - 1)] - 2 * gray[(y - 1) * w + x] - gray[(y - 1) * w + (x + 1)]
                            + gray[(y + 1) * w + (x - 1)] + 2 * gray[(y + 1) * w + x] + gray[(y + 1) * w + (x + 1)]).toFloat()

                    val gVal = sqrt(gx * gx + gy * gy)
                    grad[y * w + x] = gVal
                    if (gVal > maxGrad) maxGrad = gVal
                }
            }

            val threshold = maxGrad * 0.20f

            // Quadrant search with distance weighting towards outer bounds
            var bestTl = PointF(0.08f, 0.08f)
            var minTlScore = Float.MAX_VALUE
            for (y in 4 until (h * 0.45f).toInt()) {
                for (x in 4 until (w * 0.45f).toInt()) {
                    if (grad[y * w + x] >= threshold) {
                        val score = (x.toFloat() / w) + (y.toFloat() / h)
                        if (score < minTlScore) {
                            minTlScore = score
                            bestTl = PointF(x.toFloat() / w, y.toFloat() / h)
                        }
                    }
                }
            }

            var bestTr = PointF(0.92f, 0.08f)
            var minTrScore = Float.MAX_VALUE
            for (y in 4 until (h * 0.45f).toInt()) {
                for (x in (w * 0.55f).toInt() until w - 4) {
                    if (grad[y * w + x] >= threshold) {
                        val score = ((w - x).toFloat() / w) + (y.toFloat() / h)
                        if (score < minTrScore) {
                            minTrScore = score
                            bestTr = PointF(x.toFloat() / w, y.toFloat() / h)
                        }
                    }
                }
            }

            var bestBr = PointF(0.92f, 0.92f)
            var minBrScore = Float.MAX_VALUE
            for (y in (h * 0.55f).toInt() until h - 4) {
                for (x in (w * 0.55f).toInt() until w - 4) {
                    if (grad[y * w + x] >= threshold) {
                        val score = ((w - x).toFloat() / w) + ((h - y).toFloat() / h)
                        if (score < minBrScore) {
                            minBrScore = score
                            bestBr = PointF(x.toFloat() / w, y.toFloat() / h)
                        }
                    }
                }
            }

            var bestBl = PointF(0.08f, 0.92f)
            var minBlScore = Float.MAX_VALUE
            for (y in (h * 0.55f).toInt() until h - 4) {
                for (x in 4 until (w * 0.45f).toInt()) {
                    if (grad[y * w + x] >= threshold) {
                        val score = (x.toFloat() / w) + ((h - y).toFloat() / h)
                        if (score < minBlScore) {
                            minBlScore = score
                            bestBl = PointF(x.toFloat() / w, y.toFloat() / h)
                        }
                    }
                }
            }

            DocCorners(
                topLeft = PointF(bestTl.x.coerceIn(0.02f, 0.42f), bestTl.y.coerceIn(0.02f, 0.42f)),
                topRight = PointF(bestTr.x.coerceIn(0.58f, 0.98f), bestTr.y.coerceIn(0.02f, 0.42f)),
                bottomRight = PointF(bestBr.x.coerceIn(0.58f, 0.98f), bestBr.y.coerceIn(0.58f, 0.98f)),
                bottomLeft = PointF(bestBl.x.coerceIn(0.02f, 0.42f), bestBl.y.coerceIn(0.58f, 0.98f))
            )
        } catch (e: Exception) {
            DocCorners(
                PointF(0.05f, 0.05f),
                PointF(0.95f, 0.05f),
                PointF(0.95f, 0.95f),
                PointF(0.05f, 0.95f)
            )
        }
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
    // CAMSCANNER FILTERS PIPELINE
    // =========================================================================

    enum class ScanFilter(val displayName: String, val description: String) {
        ENHANCE("Enhance", "Magic Color: brightens paper, deepens text, vivid colors"),
        MAGIC_PRO("Magic Pro", "Ultra-HD crisp contrast with razor sharp clarity"),
        NO_SHADOW("No Shadow", "Removes shadows and uneven lighting"),
        NO_WATERMARK("No Watermark", "Cleans background stains, grain, and noise"),
        BW("B&W", "Clean binary black & white for photocopies & faxes"),
        GRAYSCALE("Grayscale", "Monochrome smooth tonal balance"),
        ORIGINAL("Original", "Original rectified photo with natural colors"),
        LIGHTEN("Lighten", "Brightens dark backgrounds while maintaining text"),
        ECO_PRINT("Eco Print", "High contrast minimal ink printing mode")
    }

    /**
     * Applies the chosen CamScanner filter to a bitmap.
     */
    fun applyFilter(source: Bitmap, filter: ScanFilter): Bitmap {
        val safe = if (source.config != Bitmap.Config.ARGB_8888) {
            source.copy(Bitmap.Config.ARGB_8888, false)
        } else {
            source
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
     * CamScanner Signature Magic Enhance:
     * Stretches luminance contrast, whitens background paper, increases ink saturation & sharpness.
     */
    private fun applyMagicEnhance(src: Bitmap): Bitmap {
        val out = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // ColorMatrix with contrast + saturation + brightness lift
        val cm = ColorMatrix(floatArrayOf(
            1.35f, 0f, 0f, 0f, 20f,
            0f, 1.35f, 0f, 0f, 20f,
            0f, 0f, 1.35f, 0f, 20f,
            0f, 0f, 0f, 1f, 0f
        ))
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return out
    }

    /**
     * Magic Pro: Ultra-high dynamic contrast for rich black text and clean white background.
     */
    private fun applyMagicPro(src: Bitmap): Bitmap {
        val out = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val cm = ColorMatrix(floatArrayOf(
            1.6f, 0f, 0f, 0f, 30f,
            0f, 1.6f, 0f, 0f, 30f,
            0f, 0f, 1.6f, 0f, 30f,
            0f, 0f, 0f, 1f, 0f
        ))
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return out
    }

    /**
     * No Shadow: Normalizes illumination across the page to erase dark corner shadows.
     */
    private fun applyNoShadow(src: Bitmap): Bitmap {
        val out = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // High brightness boost with highlight suppression
        val cm = ColorMatrix(floatArrayOf(
            1.4f, 0f, 0f, 0f, 45f,
            0f, 1.4f, 0f, 0f, 45f,
            0f, 0f, 1.4f, 0f, 45f,
            0f, 0f, 0f, 1f, 0f
        ))
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return out
    }

    /**
     * No Watermark / Clean Paper: Removes background artifacts, wrinkles, and stains.
     */
    private fun applyNoWatermark(src: Bitmap): Bitmap {
        val out = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val cm = ColorMatrix(floatArrayOf(
            1.5f, 0f, 0f, 0f, 35f,
            0f, 1.5f, 0f, 0f, 35f,
            0f, 0f, 1.5f, 0f, 35f,
            0f, 0f, 0f, 1f, 0f
        ))
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return out
    }

    /**
     * B&W: Crisp binary photocopy mode.
     */
    private fun applyBlackAndWhite(src: Bitmap): Bitmap {
        val out = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val cm = ColorMatrix()
        cm.setSaturation(0f)
        val contrastCm = ColorMatrix(floatArrayOf(
            3.0f, 0f, 0f, 0f, -160f,
            0f, 3.0f, 0f, 0f, -160f,
            0f, 0f, 3.0f, 0f, -160f,
            0f, 0f, 0f, 1f, 0f
        ))
        contrastCm.preConcat(cm)
        paint.colorFilter = ColorMatrixColorFilter(contrastCm)
        canvas.drawBitmap(src, 0f, 0f, paint)
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
            1.2f, 0f, 0f, 0f, 15f,
            0f, 1.2f, 0f, 0f, 15f,
            0f, 0f, 1.2f, 0f, 15f,
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
            1.15f, 0f, 0f, 0f, 40f,
            0f, 1.15f, 0f, 0f, 40f,
            0f, 0f, 1.15f, 0f, 40f,
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
            2.2f, 0f, 0f, 0f, -100f,
            0f, 2.2f, 0f, 0f, -100f,
            0f, 0f, 2.2f, 0f, -100f,
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
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CamScanner")
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
            val camScannerDir = File(picturesDir, "CamScanner").apply { mkdirs() }
            val file = File(camScannerDir, fileName)
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
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents/CamScanner")
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
            val camScannerDir = File(docsDir, "CamScanner").apply { mkdirs() }
            val file = File(camScannerDir, fileName)
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
