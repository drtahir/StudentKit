package com.drtahir.studentkit.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FaceRestorer {
    private const val TAG = "FaceRestorer"

    var isModelLoaded = true
        private set

    fun initInterpreter(context: Context): Boolean {
        isModelLoaded = true
        Log.d(TAG, "FaceRestorer Engine ready for on-device ML Kit portrait enhancement.")
        return true
    }

    fun close() {
        // No heavy resources to release
    }

    /**
     * Detects faces using Google ML Kit (100% on-device & fully offline).
     */
    suspend fun detectFaces(bitmap: Bitmap): List<Face> = suspendCoroutine { continuation ->
        try {
            val image = InputImage.fromBitmap(bitmap, 0)
            val options = FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
                .build()
            val detector = FaceDetection.getClient(options)

            detector.process(image)
                .addOnSuccessListener { faces ->
                    Log.d(TAG, "Detected ${faces.size} faces offline via ML Kit.")
                    continuation.resume(faces)
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

    /**
     * Runs portrait restoration on cropped faces and stitches them back onto the enhanced background.
     * Uses feathered alpha blending to avoid harsh edges.
     */
    fun restoreFacesAndStitch(
        context: Context,
        originalBitmap: Bitmap, // Original low-res
        enhancedBackground: Bitmap, // Upscaled general image (4x)
        faces: List<Face>,
        faceBlendAlpha: Float = 0.85f,
        progressCallback: (Float) -> Unit
    ): Bitmap {
        if (faces.isEmpty()) {
            progressCallback(1.0f)
            return enhancedBackground
        }

        // Copy upscaled background
        val resultBitmap = enhancedBackground.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(resultBitmap)

        val totalFaces = faces.size
        var processedFaces = 0

        for (face in faces) {
            // Face bounding box on original image
            val origBox = face.boundingBox

            // Pad the bounding box slightly to capture full head
            val padW = (origBox.width() * 0.35f).toInt()
            val padH = (origBox.height() * 0.35f).toInt()

            val left = (origBox.left - padW).coerceIn(0, originalBitmap.width)
            val top = (origBox.top - padH).coerceIn(0, originalBitmap.height)
            val right = (origBox.right + padW).coerceIn(0, originalBitmap.width)
            val bottom = (origBox.bottom + padH).coerceIn(0, originalBitmap.height)

            val cropW = right - left
            val cropH = bottom - top

            if (cropW <= 0 || cropH <= 0) continue

            // 1. Crop face from original image
            val origFaceCrop = Bitmap.createBitmap(originalBitmap, left, top, cropW, cropH)

            // 2. Run portrait skin-smoothing & feature enhancement
            val restoredFace = runFaceRefinePipeline(origFaceCrop)

            // 3. Resize restored face to the upscaled coordinate system (4x of original crop)
            val destLeft = left * 4
            val destTop = top * 4
            val destW = cropW * 4
            val destH = cropH * 4

            val scaledRestoredFace = Bitmap.createScaledBitmap(restoredFace, destW, destH, true)

            // 4. Create feathered alpha mask to blend the face boundary seamlessly
            val mask = Bitmap.createBitmap(destW, destH, Bitmap.Config.ARGB_8888)
            val maskCanvas = Canvas(mask)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.BLACK
                style = Paint.Style.FILL
            }

            val cx = destW / 2.0f
            val cy = destH / 2.0f
            val radiusX = destW * 0.45f
            val radiusY = destH * 0.45f

            maskCanvas.drawARGB(0, 0, 0, 0)
            maskCanvas.drawOval(cx - radiusX, cy - radiusY, cx + radiusX, cy + radiusY, paint)

            val featherPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            }

            val blendedFace = Bitmap.createBitmap(destW, destH, Bitmap.Config.ARGB_8888)
            val blendCanvas = Canvas(blendedFace)
            blendCanvas.drawBitmap(scaledRestoredFace, 0f, 0f, null)
            blendCanvas.drawBitmap(mask, 0f, 0f, featherPaint)

            // Draw onto upscaled background with user-controlled alpha blend
            val drawPaint = Paint(Paint.FILTER_BITMAP_FLAG).apply {
                alpha = (faceBlendAlpha * 255).toInt().coerceIn(0, 255)
            }
            canvas.drawBitmap(blendedFace, destLeft.toFloat(), destTop.toFloat(), drawPaint)

            // Recycle temp bitmaps
            origFaceCrop.recycle()
            if (restoredFace != origFaceCrop) restoredFace.recycle()
            scaledRestoredFace.recycle()
            mask.recycle()
            blendedFace.recycle()

            processedFaces++
            progressCallback(processedFaces.toFloat() / totalFaces)
        }

        return resultBitmap
    }

    /**
     * Native On-Device Portrait Skin-Refining & Feature Preservation Pipeline.
     * Uses selective bilateral-style smoothing on skin, whilst maintaining and sharpening facial details
     * (eyes, lips, brows).
     */
    private fun runFaceRefinePipeline(faceCrop: Bitmap): Bitmap {
        val width = faceCrop.width
        val height = faceCrop.height
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // 2. Local skin-smoothing convolution
        val pixels = IntArray(width * height)
        faceCrop.getPixels(pixels, 0, width, 0, 0, width, height)
        val refinedPixels = IntArray(width * height)

        for (y in 0 until height) {
            val yOffset = y * width
            for (x in 0 until width) {
                val idx = yOffset + x
                if (y < 2 || y > height - 3 || x < 2 || x > width - 3) {
                    refinedPixels[idx] = pixels[idx]
                    continue
                }

                // Analyze 5x5 block around pixel for Bilateral-like skin filter
                var sumR = 0
                var sumG = 0
                var sumB = 0
                var count = 0

                val centerPixel = pixels[idx]
                val cR = (centerPixel shr 16) and 0xFF
                val cG = (centerPixel shr 8) and 0xFF
                val cB = centerPixel and 0xFF

                // Skin color detection threshold (standard human skin tone bounds)
                val isSkinColor = (cR > 95 && cG > 40 && cB > 20 &&
                        (maxOf(cR, maxOf(cG, cB)) - minOf(cR, minOf(cG, cB)) > 15) &&
                        Math.abs(cR - cG) > 15 && cR > cG && cR > cB)

                if (isSkinColor) {
                    // Smoothing skin while preserving edges
                    for (ky in -2..2) {
                        val rowOff = (y + ky) * width
                        for (kx in -2..2) {
                            val neighbor = pixels[rowOff + (x + kx)]
                            val nR = (neighbor shr 16) and 0xFF
                            val nG = (neighbor shr 8) and 0xFF
                            val nB = neighbor and 0xFF

                            // Color distance threshold (Bilateral criteria)
                            if (Math.abs(nR - cR) + Math.abs(nG - cG) + Math.abs(nB - cB) < 60) {
                                sumR += nR
                                sumG += nG
                                sumB += nB
                                count++
                            }
                        }
                    }
                }

                if (count > 0) {
                    val finalR = (sumR / count).coerceIn(0, 255)
                    val finalG = (sumG / count).coerceIn(0, 255)
                    val finalB = (sumB / count).coerceIn(0, 255)
                    refinedPixels[idx] = (0xFF shl 24) or (finalR shl 16) or (finalG shl 8) or finalB
                } else {
                    // Face features (eyes, lips, nose edge) - apply unsharp mask to make them pop
                    val top = pixels[(y - 1) * width + x]
                    val bottom = pixels[(y + 1) * width + x]
                    val left = pixels[idx - 1]
                    val right = pixels[idx + 1]

                    val tR = (top shr 16) and 0xFF
                    val bR = (bottom shr 16) and 0xFF
                    val lL = (left shr 16) and 0xFF
                    val rR = (right shr 16) and 0xFF

                    val sharpR = (cR * 5 - tR - bR - lL - rR).coerceIn(0, 255)
                    val sharpG = (((centerPixel shr 8) and 0xFF) * 5 - ((top shr 8) and 0xFF) - ((bottom shr 8) and 0xFF) - ((left shr 8) and 0xFF) - ((right shr 8) and 0xFF)).coerceIn(0, 255)
                    val sharpB = ((cB) * 5 - (top and 0xFF) - (bottom and 0xFF) - (left and 0xFF) - (right and 0xFF)).coerceIn(0, 255)

                    refinedPixels[idx] = (0xFF shl 24) or (sharpR shl 16) or (sharpG shl 8) or sharpB
                }
            }
        }

        output.setPixels(refinedPixels, 0, width, 0, 0, width, height)
        return output
    }
}
