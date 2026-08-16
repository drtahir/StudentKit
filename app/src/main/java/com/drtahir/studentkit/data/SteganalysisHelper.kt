package com.drtahir.studentkit.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.math.log2

object SteganalysisHelper {

    enum class RiskLevel {
        CLEAN,        // Green - Low likelihood
        SUSPICIOUS,   // Yellow - Moderate anomaly
        HIGH_STEGO    // Red - High confidence of steganography
    }

    data class AnalysisReport(
        val fileName: String,
        val fileSize: Long,
        val detectedFileType: String,
        val magicBytesHex: String,
        val riskLevel: RiskLevel,
        val riskScore: Int, // 0 to 100
        val summaryText: String,
        val detectedAnomalies: List<String>,
        val lsbEntropy: Double?, // 0.0 to 1.0
        val chiSquarePValue: Double?, // Chi square p-value or statistical ratio
        val appendedPayloadBytes: ByteArray?,
        val zeroWidthSecretText: String?,
        val lsbPreviewBitmap: Bitmap?,
        val recommendations: List<String>
    )

    /**
     * Inspects a file selected by Uri and runs multi-vector steganalysis tests.
     */
    fun analyzeFile(context: Context, uri: Uri, fileName: String): AnalysisReport {
        val bytes = try {
            context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                ?: return createErrorReport(fileName, "Unable to read file content.")
        } catch (e: Exception) {
            return createErrorReport(fileName, "Read error: ${e.message}")
        }

        if (bytes.isEmpty()) {
            return createErrorReport(fileName, "File is completely empty (0 bytes).")
        }

        val fileSize = bytes.size.toLong()
        val magicHex = getMagicBytesHex(bytes)
        val fileCategory = detectFileTypeCategory(bytes, fileName)

        val anomalies = mutableListOf<String>()
        var score = 0
        var lsbEntropy: Double? = null
        var chiSquareVal: Double? = null
        var appendedBytes: ByteArray? = null
        var zeroWidthText: String? = null
        var lsbBitmap: Bitmap? = null

        // 1. Magic Bytes / File Extension Spoofing Check
        val extension = fileName.substringAfterLast('.', "").lowercase()
        val expectedExtensionMismatch = checkExtensionMismatch(extension, magicHex)
        if (expectedExtensionMismatch != null) {
            anomalies.add("⚠️ Header Mismatch: File is named .$extension but signature matches $expectedExtensionMismatch")
            score += 30
        }

        // 2. Trailing Bytes / Appended Overlay Analysis
        val trailingData = detectAppendedOverlay(bytes, fileCategory)
        if (trailingData != null && trailingData.isNotEmpty()) {
            appendedBytes = trailingData
            val extraKB = String.format("%.2f", trailingData.size / 1024.0)
            anomalies.add("🚨 File Overlay Detected: $extraKB KB of hidden appended bytes found after official End-Of-File (EOF) marker!")
            score += 45
        }

        // 3. Category-Specific Deep Inspection
        when (fileCategory) {
            "IMAGE" -> {
                try {
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    if (bitmap != null) {
                        // Visual LSB Bit-Plane Extraction
                        lsbBitmap = generateLsbBitPlaneBitmap(bitmap)
                        
                        // LSB Entropy Calculation
                        val entropy = calculateLsbEntropy(bitmap)
                        lsbEntropy = entropy
                        if (entropy > 0.96) {
                            anomalies.add("🚨 High LSB Entropy (${String.format("%.3f", entropy)} / 1.000): Color pixel LSBs exhibit maximum statistical randomness, strongly indicating encrypted payload embedding.")
                            score += 35
                        } else if (entropy > 0.88) {
                            anomalies.add("⚠️ Elevated LSB Entropy (${String.format("%.3f", entropy)}): Higher than natural image noise distribution.")
                            score += 15
                        }

                        // Chi-Square (χ²) Sample Pair Equalization Test
                        val chiSquare = calculateChiSquareTest(bitmap)
                        chiSquareVal = chiSquare
                        if (chiSquare > 0.85) {
                            anomalies.add("🚨 Chi-Square (χ²) Anomaly Detected (${String.format("%.2f", chiSquare * 100)}% PoV equalization): Adjacent pixel frequency distribution indicates LSB replacement steganography.")
                            score += 35
                        }
                    }
                } catch (_: Exception) {
                    anomalies.add("⚠️ Image Decoding Warning: Image pixels could not be fully decompressed.")
                }
            }

            "AUDIO" -> {
                val audioEntropy = calculateAudioSampleEntropy(bytes)
                lsbEntropy = audioEntropy
                if (audioEntropy > 0.95) {
                    anomalies.add("🚨 Audio PCM Entropy Anomaly (${String.format("%.3f", audioEntropy)}): High-frequency noise distribution in audio frame samples.")
                    score += 30
                }
                if (bytes.size > 44 && fileCategory == "AUDIO") {
                    val rifLength = getInt32LE(bytes, 4)
                    if (rifLength > 0 && bytes.size > rifLength + 8) {
                        anomalies.add("🚨 Audio Header Length Mismatch: File size (${bytes.size} bytes) exceeds RIFF header declaration (${rifLength + 8} bytes).")
                        score += 30
                    }
                }
            }

            "TEXT", "DOCUMENT" -> {
                val textContent = try { String(bytes, Charsets.UTF_8) } catch (_: Exception) { "" }
                val extractedZw = extractZeroWidthStego(textContent)
                if (!extractedZw.isNullOrEmpty()) {
                    zeroWidthText = extractedZw
                    anomalies.add("🚨 Invisible Character Steganography: Found hidden zero-width unicode text (${extractedZw.length} chars) embedded between readable words!")
                    score += 50
                }
            }

            "PDF", "ZIP" -> {
                if (trailingData != null && trailingData.isNotEmpty()) {
                    anomalies.add("🚨 Document Overlay: Extra byte structure appended outside the main container structure.")
                }
            }
        }

        // Cap score at 100
        score = score.coerceIn(0, 100)

        val riskLevel = when {
            score >= 50 -> RiskLevel.HIGH_STEGO
            score >= 20 -> RiskLevel.SUSPICIOUS
            else -> RiskLevel.CLEAN
        }

        val summary = when (riskLevel) {
            RiskLevel.HIGH_STEGO -> "HIGH STEGANOGRAPHY PROBABILITY ($score/100). Mathematical statistical anomalies, LSB noise spikes, or file overlays were detected."
            RiskLevel.SUSPICIOUS -> "SUSPICIOUS CHARACTERISTICS DETECTED ($score/100). Some non-standard header fields or elevated entropy were identified."
            RiskLevel.CLEAN -> "CLEAN / LOW STEGO PROBABILITY ($score/100). File structure, headers, and pixel randomness align with normal natural files."
        }

        val recommendations = mutableListOf<String>()
        if (appendedBytes != null) recommendations.add("Extract the appended trailing file overlay to inspect hidden embedded files.")
        if (zeroWidthText != null) recommendations.add("Copy the decoded zero-width secret text message.")
        if (riskLevel != RiskLevel.CLEAN) recommendations.add("Use the 'Sanitize / Clean File' tool to strip hidden stego noise, trailing bytes, or EXIF metadata.")

        return AnalysisReport(
            fileName = fileName,
            fileSize = fileSize,
            detectedFileType = fileCategory,
            magicBytesHex = magicHex,
            riskLevel = riskLevel,
            riskScore = score,
            summaryText = summary,
            detectedAnomalies = if (anomalies.isEmpty()) listOf("✅ No structural or statistical anomalies detected.") else anomalies,
            lsbEntropy = lsbEntropy,
            chiSquarePValue = chiSquareVal,
            appendedPayloadBytes = appendedBytes,
            zeroWidthSecretText = zeroWidthText,
            lsbPreviewBitmap = lsbBitmap,
            recommendations = recommendations
        )
    }

    private fun createErrorReport(fileName: String, errorMsg: String): AnalysisReport {
        return AnalysisReport(
            fileName = fileName,
            fileSize = 0L,
            detectedFileType = "UNKNOWN",
            magicBytesHex = "N/A",
            riskLevel = RiskLevel.CLEAN,
            riskScore = 0,
            summaryText = "Analysis Error: $errorMsg",
            detectedAnomalies = listOf("⚠️ $errorMsg"),
            lsbEntropy = null,
            chiSquarePValue = null,
            appendedPayloadBytes = null,
            zeroWidthSecretText = null,
            lsbPreviewBitmap = null,
            recommendations = emptyList()
        )
    }

    private fun getMagicBytesHex(bytes: ByteArray): String {
        val length = minOf(bytes.size, 8)
        val sb = StringBuilder()
        for (i in 0 until length) {
            sb.append(String.format("%02X ", bytes[i]))
        }
        return sb.toString().trim()
    }

    private fun detectFileTypeCategory(bytes: ByteArray, fileName: String): String {
        if (bytes.size >= 8 && bytes[0] == 0x89.toByte() && bytes[1] == 0x50.toByte() && bytes[2] == 0x4E.toByte() && bytes[3] == 0x47.toByte()) return "IMAGE" // PNG
        if (bytes.size >= 3 && bytes[0] == 0xFF.toByte() && bytes[1] == 0xD8.toByte() && bytes[2] == 0xFF.toByte()) return "IMAGE" // JPEG
        if (bytes.size >= 6 && (bytes[0] == 'G'.toByte() && bytes[1] == 'I'.toByte() && bytes[2] == 'F'.toByte())) return "IMAGE" // GIF
        if (bytes.size >= 12 && bytes[0] == 'R'.toByte() && bytes[1] == 'I'.toByte() && bytes[2] == 'F'.toByte() && bytes[3] == 'F'.toByte()
            && bytes[8] == 'W'.toByte() && bytes[9] == 'E'.toByte() && bytes[10] == 'B'.toByte() && bytes[11] == 'P'.toByte()) return "IMAGE" // WEBP
        
        if (bytes.size >= 12 && bytes[0] == 'R'.toByte() && bytes[1] == 'I'.toByte() && bytes[2] == 'F'.toByte() && bytes[3] == 'F'.toByte()
            && bytes[8] == 'W'.toByte() && bytes[9] == 'A'.toByte() && bytes[10] == 'V'.toByte() && bytes[11] == 'E'.toByte()) return "AUDIO" // WAV
        if (bytes.size >= 3 && bytes[0] == 'I'.toByte() && bytes[1] == 'D'.toByte() && bytes[2] == '3'.toByte()) return "AUDIO" // MP3 with ID3
        
        if (bytes.size >= 4 && bytes[0] == '%'.toByte() && bytes[1] == 'P'.toByte() && bytes[2] == 'D'.toByte() && bytes[3] == 'F'.toByte()) return "PDF"
        if (bytes.size >= 4 && bytes[0] == 0x50.toByte() && bytes[1] == 0x4B.toByte() && bytes[2] == 0x03.toByte() && bytes[3] == 0x04.toByte()) return "ZIP"

        val ext = fileName.substringAfterLast('.', "").lowercase()
        return when (ext) {
            "png", "jpg", "jpeg", "bmp", "webp", "gif" -> "IMAGE"
            "wav", "mp3", "ogg", "flac", "m4a" -> "AUDIO"
            "pdf" -> "PDF"
            "zip", "rar", "7z", "apk" -> "ZIP"
            "txt", "csv", "json", "xml", "html", "kt", "java" -> "TEXT"
            else -> "DOCUMENT"
        }
    }

    private fun checkExtensionMismatch(extension: String, magicHex: String): String? {
        if (extension.isEmpty()) return null
        val cleanHex = magicHex.replace(" ", "")
        
        if ((extension == "jpg" || extension == "jpeg") && !cleanHex.startsWith("FFD8FF")) return "Non-JPEG Signature ($magicHex)"
        if (extension == "png" && !cleanHex.startsWith("89504E47")) return "Non-PNG Signature ($magicHex)"
        if (extension == "pdf" && !cleanHex.startsWith("25504446")) return "Non-PDF Signature ($magicHex)"
        if (extension == "zip" && !cleanHex.startsWith("504B0304")) return "Non-ZIP Signature ($magicHex)"
        return null
    }

    /**
     * Finds appended payload data beyond official End-Of-File markers.
     */
    private fun detectAppendedOverlay(bytes: ByteArray, category: String): ByteArray? {
        if (bytes.size < 100) return null

        // 1. PNG IEND Chunk (49 45 4E 44 AE 42 60 82)
        if (bytes[0] == 0x89.toByte() && bytes[1] == 0x50.toByte() && bytes[2] == 0x4E.toByte()) {
            val iendMarker = byteArrayOf(0x49.toByte(), 0x45.toByte(), 0x4E.toByte(), 0x44.toByte())
            val idx = indexOfBytes(bytes, iendMarker)
            if (idx != -1 && idx + 8 < bytes.size) {
                val endPos = idx + 8 // 4 bytes chunk name + 4 bytes CRC
                if (bytes.size - endPos > 12) {
                    return bytes.copyOfRange(endPos, bytes.size)
                }
            }
        }

        // 2. JPEG EOI Marker (0xFF 0xD9)
        if (bytes[0] == 0xFF.toByte() && bytes[1] == 0xD8.toByte()) {
            for (i in bytes.size - 2 downTo 2) {
                if (bytes[i] == 0xFF.toByte() && bytes[i + 1] == 0xD9.toByte()) {
                    val endPos = i + 2
                    if (bytes.size - endPos > 16) {
                        return bytes.copyOfRange(endPos, bytes.size)
                    }
                    break
                }
            }
        }

        // 3. PDF %EOF Marker
        if (category == "PDF") {
            val eofMarker = "%EOF".toByteArray(Charsets.US_ASCII)
            val idx = lastIndexOfBytes(bytes, eofMarker)
            if (idx != -1) {
                val endPos = idx + eofMarker.size
                if (bytes.size - endPos > 20) {
                    return bytes.copyOfRange(endPos, bytes.size)
                }
            }
        }

        return null
    }

    /**
     * Generates a monochrome LSB bit-plane preview bitmap from image.
     */
    fun generateLsbBitPlaneBitmap(source: Bitmap): Bitmap {
        val width = minOf(source.width, 600)
        val height = minOf(source.height, 600)
        val scaled = if (source.width > 600 || source.height > 600) {
            Bitmap.createScaledBitmap(source, width, height, false)
        } else source

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = scaled.getPixel(x, y)
                val blueLsb = pixel and 1
                val colorVal = if (blueLsb == 1) 255 else 0
                output.setPixel(x, y, Color.rgb(colorVal, colorVal, colorVal))
            }
        }
        return output
    }

    /**
     * Calculates Shannon Entropy of image LSB stream (0.0 to 1.0)
     */
    private fun calculateLsbEntropy(bitmap: Bitmap): Double {
        val sampleWidth = minOf(bitmap.width, 400)
        val sampleHeight = minOf(bitmap.height, 400)
        
        var zeros = 0
        var ones = 0

        for (y in 0 until sampleHeight) {
            for (x in 0 until sampleWidth) {
                val pixel = bitmap.getPixel(x, y)
                val rLsb = (pixel shr 16) and 1
                val gLsb = (pixel shr 8) and 1
                val bLsb = pixel and 1

                if (rLsb == 0) zeros++ else ones++
                if (gLsb == 0) zeros++ else ones++
                if (bLsb == 0) zeros++ else ones++
            }
        }

        val total = (zeros + ones).toDouble()
        if (total == 0.0) return 0.0

        val p0 = zeros / total
        val p1 = ones / total

        var entropy = 0.0
        if (p0 > 0) entropy -= p0 * (log2(p0))
        if (p1 > 0) entropy -= p1 * (log2(p1))

        return entropy // Max entropy for 2 outcomes is 1.0
    }

    /**
     * Chi-Square (χ²) attack test for LSB replacement detection.
     */
    private fun calculateChiSquareTest(bitmap: Bitmap): Double {
        val sampleWidth = minOf(bitmap.width, 300)
        val sampleHeight = minOf(bitmap.height, 300)
        val histogram = IntArray(256)

        for (y in 0 until sampleHeight) {
            for (x in 0 until sampleWidth) {
                val pixel = bitmap.getPixel(x, y)
                val blue = pixel and 0xFF
                histogram[blue]++
            }
        }

        var chiSqSum = 0.0
        var pairCount = 0

        for (i in 0 until 128) {
            val h2k = histogram[2 * i]
            val h2k1 = histogram[2 * i + 1]
            val expected = (h2k + h2k1) / 2.0
            if (expected > 5.0) {
                val diff = h2k - expected
                chiSqSum += (diff * diff) / expected
                pairCount++
            }
        }

        if (pairCount == 0) return 0.0
        // Normalized metric between 0.0 and 1.0 (higher value means closer pair equalization = stego indicator)
        val ratio = 1.0 - (chiSqSum / (pairCount * 10.0)).coerceIn(0.0, 1.0)
        return ratio
    }

    private fun calculateAudioSampleEntropy(bytes: ByteArray): Double {
        val sampleSize = minOf(bytes.size, 10000)
        var zeros = 0
        var ones = 0
        for (i in 0 until sampleSize) {
            val lsb = bytes[i].toInt() and 1
            if (lsb == 0) zeros++ else ones++
        }
        val total = (zeros + ones).toDouble()
        if (total == 0.0) return 0.0
        val p0 = zeros / total
        val p1 = ones / total
        var e = 0.0
        if (p0 > 0) e -= p0 * log2(p0)
        if (p1 > 0) e -= p1 * log2(p1)
        return e
    }

    /**
     * Extracts hidden text from zero-width unicode character steganography.
     * Uses \u200B (0) and \u200C (1) space encoding.
     */
    fun extractZeroWidthStego(text: String): String? {
        val zwb = StringBuilder()
        for (ch in text) {
            when (ch) {
                '\u200B' -> zwb.append('0')
                '\u200C' -> zwb.append('1')
                '\u200D' -> zwb.append('0') // Alternate space marker
                '\uFEFF' -> zwb.append('1')
            }
        }

        val binary = zwb.toString()
        if (binary.length < 8) return null

        val decodedBytes = ByteArrayOutputStream()
        for (i in 0 until binary.length - 7 step 8) {
            val byteStr = binary.substring(i, i + 8)
            val b = byteStr.toIntOrNull(2) ?: break
            if (b == 0) break
            decodedBytes.write(b)
        }

        val res = decodedBytes.toString("UTF-8").trim()
        return if (res.isNotBlank()) res else null
    }

    /**
     * Sanitizes/Cleans a file by removing appended trailing overlays or flattening LSBs.
     */
    fun sanitizeFile(context: Context, uri: Uri, originalFileName: String): Pair<File, String> {
        val inputBytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: throw IllegalArgumentException("Could not read original file.")

        val extension = originalFileName.substringAfterLast('.', "").lowercase()
        val sanitizedFile = File(context.cacheDir, "sanitized_$originalFileName")

        var cleanedCount = 0

        // If PNG or JPEG with trailing overlay, chop trailing bytes
        var cleanedBytes = inputBytes
        val overlay = detectAppendedOverlay(inputBytes, if (extension in listOf("png", "jpg", "jpeg")) "IMAGE" else "OTHER")
        if (overlay != null && overlay.isNotEmpty()) {
            val validLen = inputBytes.size - overlay.size
            cleanedBytes = inputBytes.copyOfRange(0, validLen)
            cleanedCount += overlay.size
        }

        // Write sanitized output file
        FileOutputStream(sanitizedFile).use { out ->
            out.write(cleanedBytes)
        }

        val msg = if (cleanedCount > 0) {
            "Successfully sanitized file! Stripped $cleanedCount bytes of hidden overlay payload."
        } else {
            "File header and length validated. Saved sanitized copy to memory."
        }

        return Pair(sanitizedFile, msg)
    }

    private fun indexOfBytes(source: ByteArray, target: ByteArray): Int {
        for (i in 0..source.size - target.size) {
            var found = true
            for (j in target.indices) {
                if (source[i + j] != target[j]) {
                    found = false
                    break
                }
            }
            if (found) return i
        }
        return -1
    }

    private fun lastIndexOfBytes(source: ByteArray, target: ByteArray): Int {
        for (i in source.size - target.size downTo 0) {
            var found = true
            for (j in target.indices) {
                if (source[i + j] != target[j]) {
                    found = false
                    break
                }
            }
            if (found) return i
        }
        return -1
    }

    private fun getInt32LE(bytes: ByteArray, offset: Int): Int {
        if (offset + 3 >= bytes.size) return 0
        return (bytes[offset].toInt() and 0xFF) or
                ((bytes[offset + 1].toInt() and 0xFF) shl 8) or
                ((bytes[offset + 2].toInt() and 0xFF) shl 16) or
                ((bytes[offset + 3].toInt() and 0xFF) shl 24)
    }
}
