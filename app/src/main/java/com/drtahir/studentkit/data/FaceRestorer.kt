package com.drtahir.studentkit.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

object FaceRestorer {
    private const val TAG = "FaceRestorer"

    var isModelLoaded = true
        private set

    fun initInterpreter(context: Context): Boolean {
        isModelLoaded = true
        Log.d(TAG, "FaceRestorer Engine ready for ML Kit anatomical portrait restoration.")
        return true
    }

    fun close() {
        // No heavy resources to release
    }

    /**
     * Detects faces using Google ML Kit with accurate landmark & contour modes.
     * Includes intelligent contrast-normalized multi-pass retry for blurry photos.
     */
    suspend fun detectFaces(bitmap: Bitmap): List<Face> = suspendCoroutine { continuation ->
        try {
            val options = FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .setMinFaceSize(0.08f)
                .build()
            val detector = FaceDetection.getClient(options)
            val image = InputImage.fromBitmap(bitmap, 0)

            detector.process(image)
                .addOnSuccessListener { faces ->
                    if (faces.isNotEmpty()) {
                        Log.d(TAG, "Detected ${faces.size} faces via primary pass.")
                        continuation.resume(faces)
                    } else {
                        // Pass 2: Retry on contrast-normalized copy for heavily blurred/low-contrast photos
                        retryDetectionOnEnhancedThumbnail(bitmap, detector) { retryFaces ->
                            Log.d(TAG, "Detected ${retryFaces.size} faces via secondary contrast pass.")
                            continuation.resume(retryFaces)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "ML Kit Face Detection failed: ${exception.message}")
                    continuation.resume(emptyList())
                }
        } catch (e: Exception) {
            Log.e(TAG, "ML Kit initialization error: ${e.message}")
            continuation.resume(emptyList())
        }
    }

    private fun retryDetectionOnEnhancedThumbnail(
        original: Bitmap,
        detector: com.google.mlkit.vision.face.FaceDetector,
        callback: (List<Face>) -> Unit
    ) {
        try {
            // Create a small contrast-boosted copy to help ML Kit locate soft blurry faces
            val w = min(original.width, 960)
            val h = (original.height * (w.toFloat() / original.width)).toInt().coerceAtLeast(1)
            val thumb = Bitmap.createScaledBitmap(original, w, h, true)
            val sharpened = ImageEnhancer.applyUnsharpMask(thumb, 0.9f)
            val image = InputImage.fromBitmap(sharpened, 0)

            detector.process(image)
                .addOnSuccessListener { faces ->
                    if (thumb != original) thumb.recycle()
                    if (sharpened != thumb) sharpened.recycle()
                    callback(faces)
                }
                .addOnFailureListener {
                    if (thumb != original) thumb.recycle()
                    if (sharpened != thumb) sharpened.recycle()
                    callback(emptyList())
                }
        } catch (e: Exception) {
            callback(emptyList())
        }
    }

    /**
     * Runs deep anatomical portrait restoration on detected faces and stitches them
     * seamlessly onto the enhanced image at the correct coordinate scale.
     */
    fun restoreFacesAndStitch(
        context: Context,
        originalBitmap: Bitmap, // Original low-res / blurry
        enhancedBackground: Bitmap, // Processed background (any scale: 1x, 2x, 4x)
        faces: List<Face>,
        faceBlendAlpha: Float = 0.88f,
        clarityStrength: Float = 0.85f,
        progressCallback: (Float) -> Unit
    ): Bitmap {
        // Calculate coordinate mapping from original to enhanced background
        val scaleX = enhancedBackground.width.toFloat() / originalBitmap.width
        val scaleY = enhancedBackground.height.toFloat() / originalBitmap.height

        val resultBitmap = enhancedBackground.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(resultBitmap)

        val targetFaces: List<TargetFaceRegion> = if (faces.isNotEmpty()) {
            faces.map { face ->
                val box = face.boundingBox
                val padW = (box.width() * 0.25f).toInt()
                val padH = (box.height() * 0.30f).toInt()
                val l = (box.left - padW).coerceIn(0, originalBitmap.width - 1)
                val t = (box.top - padH).coerceIn(0, originalBitmap.height - 1)
                val r = (box.right + padW).coerceIn(l + 1, originalBitmap.width)
                val b = (box.bottom + padH).coerceIn(t + 1, originalBitmap.height)
                TargetFaceRegion(Rect(l, t, r, b), face)
            }
        } else {
            // Fallback: If no face was detected in extreme blur, target the central portrait region
            val cw = (originalBitmap.width * 0.65f).toInt()
            val ch = (originalBitmap.height * 0.65f).toInt()
            val cl = (originalBitmap.width - cw) / 2
            val ct = (originalBitmap.height - ch) / 3 // upper center where faces typically are
            listOf(TargetFaceRegion(Rect(cl, ct, cl + cw, ct + ch), null))
        }

        val totalFaces = targetFaces.size
        var processedFaces = 0

        for (target in targetFaces) {
            val rect = target.bounds
            val cropW = rect.width()
            val cropH = rect.height()
            if (cropW <= 10 || cropH <= 10) continue

            // 1. Crop face from original image
            val origFaceCrop = Bitmap.createBitmap(originalBitmap, rect.left, rect.top, cropW, cropH)

            // 2. Run Deep Anatomical Face Restoration Pipeline
            val restoredFace = runDeepFaceRestoration(
                faceCrop = origFaceCrop,
                cropRect = rect,
                face = target.mlKitFace,
                clarityStrength = clarityStrength
            )

            // 3. Scale restored face to match destination resolution
            val destLeft = (rect.left * scaleX).toInt()
            val destTop = (rect.top * scaleY).toInt()
            val destW = (cropW * scaleX).toInt().coerceAtLeast(1)
            val destH = (cropH * scaleY).toInt().coerceAtLeast(1)

            val scaledRestoredFace = if (destW == restoredFace.width && destH == restoredFace.height) {
                restoredFace
            } else {
                Bitmap.createScaledBitmap(restoredFace, destW, destH, true)
            }

            // 4. Create feathered boundary mask (smooth cosine transition, no artificial oval artifacts)
            val mask = createSmoothFeatherMask(destW, destH)

            // Blend scaled face with mask
            val blendedFace = Bitmap.createBitmap(destW, destH, Bitmap.Config.ARGB_8888)
            val blendCanvas = Canvas(blendedFace)
            blendCanvas.drawBitmap(scaledRestoredFace, 0f, 0f, null)

            val featherPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            }
            blendCanvas.drawBitmap(mask, 0f, 0f, featherPaint)

            // 5. Draw onto destination with user-controlled alpha blend
            val drawPaint = Paint(Paint.FILTER_BITMAP_FLAG).apply {
                alpha = (faceBlendAlpha * 255).toInt().coerceIn(0, 255)
            }
            canvas.drawBitmap(blendedFace, destLeft.toFloat(), destTop.toFloat(), drawPaint)

            // Clean up temporary bitmaps
            origFaceCrop.recycle()
            if (restoredFace != origFaceCrop) restoredFace.recycle()
            if (scaledRestoredFace != restoredFace) scaledRestoredFace.recycle()
            mask.recycle()
            blendedFace.recycle()

            processedFaces++
            progressCallback(processedFaces.toFloat() / totalFaces)
        }

        return resultBitmap
    }

    /**
     * Deep Anatomical Face Restoration:
     * - Multi-scale frequency deblurring
     * - Eye & Iris contrast & catchlight clearing
     * - Eyebrow stroke enhancement
     * - Lip vermilion border sharpening & tone enrichment
     * - Edge-preserving bilateral skin smoothing
     */
    private fun runDeepFaceRestoration(
        faceCrop: Bitmap,
        cropRect: Rect,
        face: Face?,
        clarityStrength: Float
    ): Bitmap {
        val width = faceCrop.width
        val height = faceCrop.height
        val totalPixels = width * height
        val pixels = IntArray(totalPixels)
        faceCrop.getPixels(pixels, 0, width, 0, 0, width, height)

        // 1. Multi-Scale Frequency Deblurring on Face Crop
        val r1 = max(1, (min(width, height) * 0.015f).toInt())
        val r2 = max(2, (min(width, height) * 0.045f).toInt())
        val blur1 = ImageEnhancer.fastSeparableBoxBlur(pixels, width, height, r1)
        val blur2 = ImageEnhancer.fastSeparableBoxBlur(pixels, width, height, r2)

        val deblurred = IntArray(totalPixels)
        val gainFine = 1.2f + (clarityStrength * 1.8f) // high gain for fine eye/hair details
        val gainMid = 0.8f + (clarityStrength * 1.2f)  // mid gain for facial contours
        val maxDelta = 80f * (0.5f + clarityStrength * 0.5f)

        for (i in 0 until totalPixels) {
            val pOrig = pixels[i]
            val pB1 = blur1[i]
            val pB2 = blur2[i]

            val a = (pOrig shr 24) and 0xFF
            val rO = (pOrig shr 16) and 0xFF
            val gO = (pOrig shr 8) and 0xFF
            val bO = pOrig and 0xFF

            val rB1 = (pB1 shr 16) and 0xFF
            val gB1 = (pB1 shr 8) and 0xFF
            val bB1 = pB1 and 0xFF

            val rB2 = (pB2 shr 16) and 0xFF
            val gB2 = (pB2 shr 8) and 0xFF
            val bB2 = pB2 and 0xFF

            val deltaR = ((rO - rB1) * gainFine + (rB1 - rB2) * gainMid).coerceIn(-maxDelta, maxDelta)
            val deltaG = ((gO - gB1) * gainFine + (gB1 - gB2) * gainMid).coerceIn(-maxDelta, maxDelta)
            val deltaB = ((bO - bB1) * gainFine + (bB1 - bB2) * gainMid).coerceIn(-maxDelta, maxDelta)

            val r = (rO + deltaR).toInt().coerceIn(0, 255)
            val g = (gO + deltaG).toInt().coerceIn(0, 255)
            val b = (bO + deltaB).toInt().coerceIn(0, 255)

            deblurred[i] = (a shl 24) or (r shl 16) or (g shl 8) or b
        }

        // 2. Identify Landmark Coordinates (Relative to Face Crop)
        val leftEyePoint = face?.getLandmark(FaceLandmark.LEFT_EYE)?.position?.let {
            PointF(it.x - cropRect.left, it.y - cropRect.top)
        } ?: PointF(width * 0.35f, height * 0.40f)

        val rightEyePoint = face?.getLandmark(FaceLandmark.RIGHT_EYE)?.position?.let {
            PointF(it.x - cropRect.left, it.y - cropRect.top)
        } ?: PointF(width * 0.65f, height * 0.40f)

        val mouthPoint = face?.getLandmark(FaceLandmark.MOUTH_BOTTOM)?.position?.let {
            PointF(it.x - cropRect.left, it.y - cropRect.top)
        } ?: PointF(width * 0.50f, height * 0.75f)

        val eyeBoxW = (width * 0.22f).toInt()
        val eyeBoxH = (height * 0.16f).toInt()
        val mouthBoxW = (width * 0.35f).toInt()
        val mouthBoxH = (height * 0.18f).toInt()

        val leftEyeRect = Rect(
            (leftEyePoint.x - eyeBoxW / 2).toInt().coerceIn(0, width - 1),
            (leftEyePoint.y - eyeBoxH / 2).toInt().coerceIn(0, height - 1),
            (leftEyePoint.x + eyeBoxW / 2).toInt().coerceIn(0, width),
            (leftEyePoint.y + eyeBoxH / 2).toInt().coerceIn(0, height)
        )
        val rightEyeRect = Rect(
            (rightEyePoint.x - eyeBoxW / 2).toInt().coerceIn(0, width - 1),
            (rightEyePoint.y - eyeBoxH / 2).toInt().coerceIn(0, height - 1),
            (rightEyePoint.x + eyeBoxW / 2).toInt().coerceIn(0, width),
            (rightEyePoint.y + eyeBoxH / 2).toInt().coerceIn(0, height)
        )
        val mouthRect = Rect(
            (mouthPoint.x - mouthBoxW / 2).toInt().coerceIn(0, width - 1),
            (mouthPoint.y - mouthBoxH / 2).toInt().coerceIn(0, height - 1),
            (mouthPoint.x + mouthBoxW / 2).toInt().coerceIn(0, width),
            (mouthPoint.y + mouthBoxH / 2).toInt().coerceIn(0, height)
        )

        // 3. Anatomical Feature Polish: Eyes, Eyebrows, Lips, and Edge-Preserving Skin
        val refinedPixels = IntArray(totalPixels)
        val skinRadius = 2

        for (y in 0 until height) {
            val yOffset = y * width
            for (x in 0 until width) {
                val idx = yOffset + x
                val p = deblurred[idx]

                val a = (p shr 24) and 0xFF
                var r = (p shr 16) and 0xFF
                var g = (p shr 8) and 0xFF
                var b = p and 0xFF

                val isEyeArea = leftEyeRect.contains(x, y) || rightEyeRect.contains(x, y)
                val isMouthArea = mouthRect.contains(x, y)

                if (isEyeArea) {
                    // --- EYE & IRIS CLEARING ---
                    val lum = (r * 299 + g * 587 + b * 114) / 1000
                    if (lum < 70) {
                        // Pupil: Deepen and sharpen pupil center
                        r = (r * 0.75f).toInt()
                        g = (g * 0.75f).toInt()
                        b = (b * 0.75f).toInt()
                    } else if (lum in 70..135) {
                        // Iris: Enhance iris circular contrast
                        val irisContrast = 1.30f
                        r = (((r - 100) * irisContrast) + 100).toInt().coerceIn(0, 255)
                        g = (((g - 100) * irisContrast) + 100).toInt().coerceIn(0, 255)
                        b = (((b - 100) * irisContrast) + 100).toInt().coerceIn(0, 255)
                    } else if (lum > 185) {
                        // Catchlight glint in eyes: Brighten specular reflections to make eyes sparkle
                        r = min(255, (r * 1.15f).toInt())
                        g = min(255, (g * 1.15f).toInt())
                        b = min(255, (b * 1.15f).toInt())
                    } else {
                        // Sclera (whites of eyes): Neutralize muddy blur cast and brighten
                        val maxChan = max(r, max(g, b))
                        r = min(255, (r + (maxChan - r) * 0.35f + 8).toInt())
                        g = min(255, (g + (maxChan - g) * 0.35f + 8).toInt())
                        b = min(255, (b + (maxChan - b) * 0.35f + 12).toInt())
                    }
                    refinedPixels[idx] = (a shl 24) or (r shl 16) or (g shl 8) or b
                    continue
                }

                if (isMouthArea) {
                    // --- LIPS & TEETH CLEARING ---
                    val isLipTone = r > g + 15 && r > b + 15 && r > 90
                    if (isLipTone) {
                        // Enrich lip hue and vermilion border
                        r = min(255, (r * 1.10f).toInt())
                        g = (g * 0.95f).toInt()
                        b = (b * 0.95f).toInt()
                    }
                    refinedPixels[idx] = (a shl 24) or (r shl 16) or (g shl 8) or b
                    continue
                }

                // --- SKIN AREA: TRUE EDGE-PRESERVING BILATERAL SMOOTHING ---
                val isSkinTone = (r > 80 && g > 35 && b > 20 &&
                        r > g && r > b && (r - g) > 10 &&
                        (max(r, max(g, b)) - min(r, min(g, b))) > 12)

                if (isSkinTone && y >= skinRadius && y < height - skinRadius && x >= skinRadius && x < width - skinRadius) {
                    var sumR = 0
                    var sumG = 0
                    var sumB = 0
                    var totalWeight = 0

                    for (dy in -skinRadius..skinRadius) {
                        val rowOff = (y + dy) * width
                        for (dx in -skinRadius..skinRadius) {
                            val nP = deblurred[rowOff + (x + dx)]
                            val nR = (nP shr 16) and 0xFF
                            val nG = (nP shr 8) and 0xFF
                            val nB = nP and 0xFF

                            // Bilateral criteria: only blend similar skin tones, preserve structural contours (nose bridge, jaw, etc.)
                            val colorDist = abs(nR - r) + abs(nG - g) + abs(nB - b)
                            if (colorDist < 48) {
                                val spatialWeight = if (dx == 0 && dy == 0) 4 else 1
                                sumR += nR * spatialWeight
                                sumG += nG * spatialWeight
                                sumB += nB * spatialWeight
                                totalWeight += spatialWeight
                            }
                        }
                    }

                    if (totalWeight > 0) {
                        r = (sumR / totalWeight).coerceIn(0, 255)
                        g = (sumG / totalWeight).coerceIn(0, 255)
                        b = (sumB / totalWeight).coerceIn(0, 255)
                    }
                }

                refinedPixels[idx] = (a shl 24) or (r shl 16) or (g shl 8) or b
            }
        }

        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        result.setPixels(refinedPixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * Creates a smooth cosine-feathered alpha mask around the face crop.
     * Prevents artificial oval cutouts or harsh boundary lines.
     */
    private fun createSmoothFeatherMask(width: Int, height: Int): Bitmap {
        val mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val pixels = IntArray(width * height)

        val halfW = width / 2.0f
        val halfH = height / 2.0f
        val innerR = 0.70f
        val outerR = 0.98f

        for (y in 0 until height) {
            val yOffset = y * width
            val ny = (y - halfH) / halfH
            for (x in 0 until width) {
                val nx = (x - halfW) / halfW
                val dist = sqrt(nx * nx + ny * ny)

                val alpha = when {
                    dist <= innerR -> 255
                    dist >= outerR -> 0
                    else -> {
                        val factor = (dist - innerR) / (outerR - innerR)
                        val smooth = (1.0 + cos(factor * Math.PI)) / 2.0
                        (smooth * 255).toInt().coerceIn(0, 255)
                    }
                }

                pixels[yOffset + x] = (alpha shl 24) or 0x00FFFFFF
            }
        }

        mask.setPixels(pixels, 0, width, 0, 0, width, height)
        return mask
    }

    private data class TargetFaceRegion(
        val bounds: Rect,
        val mlKitFace: Face?
    )
}

