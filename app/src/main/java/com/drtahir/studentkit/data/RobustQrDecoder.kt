package com.drtahir.studentkit.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.net.Uri
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.GlobalHistogramBinarizer
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.util.EnumMap

data class QrDecodeResult(
    val text: String,
    val format: String,
    val engine: String
)

object RobustQrDecoder {

    private val zxingHints = EnumMap<DecodeHintType, Any>(DecodeHintType::class.java).apply {
        put(DecodeHintType.TRY_HARDER, java.lang.Boolean.TRUE)
        put(
            DecodeHintType.POSSIBLE_FORMATS,
            listOf(
                BarcodeFormat.QR_CODE,
                BarcodeFormat.DATA_MATRIX,
                BarcodeFormat.AZTEC,
                BarcodeFormat.PDF_417,
                BarcodeFormat.CODE_128,
                BarcodeFormat.CODE_39,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E
            )
        )
        put(DecodeHintType.CHARACTER_SET, "UTF-8")
    }

    private val mlKitScanner by lazy {
        BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE,
                    Barcode.FORMAT_DATA_MATRIX,
                    Barcode.FORMAT_AZTEC,
                    Barcode.FORMAT_PDF417,
                    Barcode.FORMAT_ALL_FORMATS
                )
                .build()
        )
    }

    /**
     * Decodes any QR code or barcode from a content URI using a multi-tiered pipeline:
     * 1. ML Kit on full resolution uncompressed bitmap
     * 2. ZXing QRCodeReader with HybridBinarizer (Try harder)
     * 3. ZXing with GlobalHistogramBinarizer
     * 4. Multi-region candidate cropping (essential for full-screen screenshots where QR is surrounded by tables/text)
     * 5. Contrast-enhanced and binarized variations
     */
    suspend fun decodeFromUri(context: Context, uri: Uri): QrDecodeResult? = withContext(Dispatchers.IO) {
        val bitmap = loadOriginalBitmap(context, uri) ?: return@withContext null
        try {
            decodeBitmapMultiPass(bitmap)
        } finally {
            // Note: bitmap recycled if created
        }
    }

    /**
     * Decodes a bitmap directly with multi-pass and multi-crop support.
     */
    suspend fun decodeBitmapMultiPass(bitmap: Bitmap): QrDecodeResult? = withContext(Dispatchers.Default) {
        // --- Pass 1: ML Kit Full Frame ---
        val mlKitFull = decodeWithMlKit(bitmap)
        if (mlKitFull != null) return@withContext mlKitFull

        // --- Pass 2: ZXing HybridBinarizer on Full Bitmap ---
        val zxingHybrid = decodeWithZxing(bitmap, useGlobalHistogram = false, invert = false)
        if (zxingHybrid != null) return@withContext zxingHybrid

        // --- Pass 3: ZXing GlobalHistogramBinarizer on Full Bitmap ---
        val zxingGlobal = decodeWithZxing(bitmap, useGlobalHistogram = true, invert = false)
        if (zxingGlobal != null) return@withContext zxingGlobal

        // --- Pass 4: Inverted ZXing on Full Bitmap ---
        val zxingInvert = decodeWithZxing(bitmap, useGlobalHistogram = false, invert = true)
        if (zxingInvert != null) return@withContext zxingInvert

        // --- Pass 5: Multi-Region Candidate Crops ---
        // Dense QR codes on documents/screenshots (such as utility bills) often fail when the full image
        // is downsampled by ML Kit or when surrounding text/tables overwhelm finder pattern detection.
        // Cropping candidate regions isolates the QR code and preserves high module pixel density.
        val candidateCrops = generateCandidateCrops(bitmap)
        for (crop in candidateCrops) {
            // Check ML Kit on crop
            val cropMlKit = decodeWithMlKit(crop)
            if (cropMlKit != null) {
                if (crop != bitmap && !crop.isRecycled) crop.recycle()
                return@withContext cropMlKit.copy(engine = "${cropMlKit.engine} (ROI Crop)")
            }

            // Check ZXing Hybrid on crop
            val cropZxing = decodeWithZxing(crop, useGlobalHistogram = false, invert = false)
            if (cropZxing != null) {
                if (crop != bitmap && !crop.isRecycled) crop.recycle()
                return@withContext cropZxing.copy(engine = "${cropZxing.engine} (ROI Crop)")
            }

            // Check ZXing Global on crop
            val cropZxingGlobal = decodeWithZxing(crop, useGlobalHistogram = true, invert = false)
            if (cropZxingGlobal != null) {
                if (crop != bitmap && !crop.isRecycled) crop.recycle()
                return@withContext cropZxingGlobal.copy(engine = "${cropZxingGlobal.engine} (ROI Crop)")
            }

            if (crop != bitmap && !crop.isRecycled) {
                crop.recycle()
            }
        }

        // --- Pass 6: High Contrast / Thresholded Pass ---
        val highContrast = enhanceContrast(bitmap)
        val zxingContrast = decodeWithZxing(highContrast, useGlobalHistogram = false, invert = false)
        if (highContrast != bitmap && !highContrast.isRecycled) {
            highContrast.recycle()
        }
        if (zxingContrast != null) return@withContext zxingContrast.copy(engine = "ZXing (Contrast Boost)")

        null
    }

    /**
     * Decode with Google ML Kit synchronously.
     */
    fun decodeWithMlKit(bitmap: Bitmap): QrDecodeResult? {
        return try {
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            val task = mlKitScanner.process(inputImage)
            val barcodes = Tasks.await(task)
            for (barcode in barcodes) {
                val raw = barcode.rawValue
                if (!raw.isNullOrBlank()) {
                    val formatName = when (barcode.format) {
                        Barcode.FORMAT_QR_CODE -> "QR Code"
                        Barcode.FORMAT_DATA_MATRIX -> "Data Matrix"
                        Barcode.FORMAT_AZTEC -> "Aztec"
                        Barcode.FORMAT_PDF417 -> "PDF417"
                        Barcode.FORMAT_EAN_13 -> "EAN-13"
                        Barcode.FORMAT_CODE_128 -> "Code 128"
                        else -> "Barcode (${barcode.format})"
                    }
                    return QrDecodeResult(text = raw, format = formatName, engine = "Google ML-Kit")
                }
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Decode with ZXing using either Hybrid or Global binarizer, and optional color inversion.
     */
    fun decodeWithZxing(bitmap: Bitmap, useGlobalHistogram: Boolean, invert: Boolean): QrDecodeResult? {
        return try {
            val width = bitmap.width
            val height = bitmap.height
            val pixels = IntArray(width * height)
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

            var source = RGBLuminanceSource(width, height, pixels)
            if (invert) {
                source = source.invert() as RGBLuminanceSource
            }

            val binarizer = if (useGlobalHistogram) {
                GlobalHistogramBinarizer(source)
            } else {
                HybridBinarizer(source)
            }
            val binaryBitmap = BinaryBitmap(binarizer)

            // Try dedicated QRCodeReader first for dense QR codes
            try {
                val qrReader = QRCodeReader()
                val result = qrReader.decode(binaryBitmap, zxingHints)
                if (!result.text.isNullOrBlank()) {
                    return QrDecodeResult(
                        text = result.text,
                        format = "QR Code",
                        engine = if (useGlobalHistogram) "ZXing (Global)" else "ZXing (Hybrid)"
                    )
                }
            } catch (ignored: Exception) {
                // Fallthrough to MultiFormatReader
            }

            val multiReader = MultiFormatReader()
            val result = multiReader.decode(binaryBitmap, zxingHints)
            if (!result.text.isNullOrBlank()) {
                val formatName = when (result.barcodeFormat) {
                    BarcodeFormat.QR_CODE -> "QR Code"
                    BarcodeFormat.DATA_MATRIX -> "Data Matrix"
                    BarcodeFormat.AZTEC -> "Aztec"
                    BarcodeFormat.PDF_417 -> "PDF417"
                    else -> result.barcodeFormat.name
                }
                QrDecodeResult(
                    text = result.text,
                    format = formatName,
                    engine = if (useGlobalHistogram) "ZXing (Global)" else "ZXing (Hybrid)"
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Generates candidate crops for screenshots and tall/wide documents where the QR code
     * is centered or located in middle/upper-middle/lower-middle sections.
     */
    private fun generateCandidateCrops(original: Bitmap): List<Bitmap> {
        val crops = mutableListOf<Bitmap>()
        val w = original.width
        val h = original.height

        try {
            // Crop 1: Center 75% square
            val squareDim = (minOf(w, h) * 0.85f).toInt()
            if (squareDim in 100 until minOf(w, h)) {
                val startX = (w - squareDim) / 2
                val startY = (h - squareDim) / 2
                crops.add(Bitmap.createBitmap(original, startX, startY, squareDim, squareDim))
            }

            // If tall image (e.g. mobile screenshot like 1080x2400):
            if (h > w * 1.25f) {
                // Crop 2: Middle band (height 25% to 75% - standard utility bill QR placement)
                val midY = (h * 0.25f).toInt()
                val midH = (h * 0.55f).toInt()
                if (midY + midH <= h) {
                    crops.add(Bitmap.createBitmap(original, 0, midY, w, midH))
                }

                // Crop 3: Upper-middle band (height 15% to 55%)
                val upperY = (h * 0.15f).toInt()
                val upperH = (h * 0.40f).toInt()
                if (upperY + upperH <= h) {
                    crops.add(Bitmap.createBitmap(original, 0, upperY, w, upperH))
                }

                // Crop 4: Lower-middle band (height 40% to 80%)
                val lowerY = (h * 0.40f).toInt()
                val lowerH = (h * 0.40f).toInt()
                if (lowerY + lowerH <= h) {
                    crops.add(Bitmap.createBitmap(original, 0, lowerY, w, lowerH))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return crops
    }

    /**
     * Applies high contrast stretching to enhance faint or screen-glare QR codes.
     */
    private fun enhanceContrast(source: Bitmap): Bitmap {
        return try {
            val result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(result)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)

            // High contrast color matrix (scale contrast by 1.8x)
            val cm = ColorMatrix(
                floatArrayOf(
                    1.8f, 0f, 0f, 0f, -60f,
                    0f, 1.8f, 0f, 0f, -60f,
                    0f, 0f, 1.8f, 0f, -60f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
            paint.colorFilter = ColorMatrixColorFilter(cm)
            canvas.drawBitmap(source, 0f, 0f, paint)
            result
        } catch (e: Exception) {
            source
        }
    }

    /**
     * Loads the uncompressed original bitmap from URI with high-resolution decoding.
     */
    private fun loadOriginalBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            // First check bounds
            var inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()

            // For QR codes, we want high resolution so modules aren't crushed.
            // Target max dimension ~2500px (generous enough for any 1080p/4K phone screenshot)
            val maxDim = maxOf(options.outWidth, options.outHeight)
            var sampleSize = 1
            if (maxDim > 2800) {
                sampleSize = 2
            }

            inputStream = context.contentResolver.openInputStream(uri)
            val decodeOptions = BitmapFactory.Options().apply {
                inSampleSize = sampleSize
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }
            val bmp = BitmapFactory.decodeStream(inputStream, null, decodeOptions)
            inputStream?.close()
            bmp
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
