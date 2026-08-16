package com.drtahir.studentkit.data

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object SteganographyHelper {

    sealed class DecodedPayload {
        data class Text(val text: String) : DecodedPayload()
        data class FilePayload(val fileName: String, val fileBytes: ByteArray) : DecodedPayload()
        data class Error(val message: String) : DecodedPayload()
    }

    /**
     * Hides secret message inside a carrier Bitmap using Least Significant Bit (LSB) steganography on the Blue channel.
     * Legacy method preserved for compatibility.
     */
    fun encode(carrier: Bitmap, secretText: String): Bitmap {
        val textWithTerminator = secretText + "\u0000"
        val bytes = textWithTerminator.toByteArray(Charsets.UTF_8)
        
        val totalBits = bytes.size * 8
        val width = carrier.width
        val height = carrier.height
        
        if (totalBits > width * height) {
            throw IllegalArgumentException("Carrier image is too small! Maximum capacity: ${(width * height) / 8} characters.")
        }

        val mutableBitmap = carrier.copy(Bitmap.Config.ARGB_8888, true)
        
        var bitIndex = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (bitIndex >= totalBits) {
                    return mutableBitmap
                }

                val pixel = mutableBitmap.getPixel(x, y)
                val alpha = (pixel shr 24) and 0xFF
                val red = (pixel shr 16) and 0xFF
                val green = (pixel shr 8) and 0xFF
                var blue = pixel and 0xFF

                val bytePos = bitIndex / 8
                val bitPos = 7 - (bitIndex % 8)
                val bit = (bytes[bytePos].toInt() shr bitPos) and 1

                blue = (blue and 0xFE) or bit

                val newPixel = (alpha shl 24) or (red shl 16) or (green shl 8) or blue
                mutableBitmap.setPixel(x, y, newPixel)

                bitIndex++
            }
        }
        return mutableBitmap
    }

    /**
     * Extracts legacy blue-channel-only text steganography.
     */
    fun decode(stegoBitmap: Bitmap): String {
        return decodeOldBlue(stegoBitmap)
    }

    /**
     * Calculates the maximum payload capacity in bytes for a given cover image.
     * Using 3-channel LSB (R, G, B), we can store 3 bits per pixel.
     * We leave a small margin and require 9 bytes of header.
     */
    fun getMaximumCapacityBytes(width: Int, height: Int): Int {
        val totalBits = width * height * 3
        val totalBytes = totalBits / 8
        return if (totalBytes > 9) totalBytes - 9 else 0
    }

    /**
     * Advanced Encoding: Encodes a text string with optional encryption.
     */
    fun encodeTextAdvanced(carrier: Bitmap, text: String, password: String? = null): Bitmap {
        val textBytes = text.toByteArray(Charsets.UTF_8)
        val type = if (password.isNullOrEmpty()) 1 else 2
        val payloadBytes = if (password.isNullOrEmpty()) {
            textBytes
        } else {
            encryptAES(textBytes, password)
        }
        
        val header = ByteArray(9)
        header[0] = 'S'.toByte()
        header[1] = 'T'.toByte()
        header[2] = 'E'.toByte()
        header[3] = 'G'.toByte()
        header[4] = type.toByte()
        
        val len = payloadBytes.size
        header[5] = ((len shr 24) and 0xFF).toByte()
        header[6] = ((len shr 16) and 0xFF).toByte()
        header[7] = ((len shr 8) and 0xFF).toByte()
        header[8] = (len and 0xFF).toByte()
        
        val finalPayload = header + payloadBytes
        return encodeBytes(carrier, finalPayload)
    }

    /**
     * Advanced Encoding: Encodes a file with optional encryption.
     */
    fun encodeFileAdvanced(carrier: Bitmap, fileName: String, fileBytes: ByteArray, password: String? = null): Bitmap {
        val truncatedFileName = if (fileName.length > 255) fileName.substring(0, 255) else fileName
        val nameBytes = truncatedFileName.toByteArray(Charsets.UTF_8)
        
        val rawPayload = ByteArray(1 + nameBytes.size + fileBytes.size)
        rawPayload[0] = nameBytes.size.toByte()
        System.arraycopy(nameBytes, 0, rawPayload, 1, nameBytes.size)
        System.arraycopy(fileBytes, 0, rawPayload, 1 + nameBytes.size, fileBytes.size)
        
        val type = if (password.isNullOrEmpty()) 3 else 4
        val payloadBytes = if (password.isNullOrEmpty()) {
            rawPayload
        } else {
            encryptAES(rawPayload, password)
        }
        
        val header = ByteArray(9)
        header[0] = 'S'.toByte()
        header[1] = 'T'.toByte()
        header[2] = 'E'.toByte()
        header[3] = 'G'.toByte()
        header[4] = type.toByte()
        
        val len = payloadBytes.size
        header[5] = ((len shr 24) and 0xFF).toByte()
        header[6] = ((len shr 16) and 0xFF).toByte()
        header[7] = ((len shr 8) and 0xFF).toByte()
        header[8] = (len and 0xFF).toByte()
        
        val finalPayload = header + payloadBytes
        return encodeBytes(carrier, finalPayload)
    }

    /**
     * Decodes stego image, automatically supporting legacy formats, advanced text formats, and advanced file formats.
     */
    fun decodeAdvanced(stegoBitmap: Bitmap, password: String? = null): DecodedPayload {
        try {
            val decodedBytes = decodeBytes(stegoBitmap)
            if (decodedBytes != null && decodedBytes.size >= 9) {
                val magic = String(decodedBytes.sliceArray(0..3), Charsets.UTF_8)
                if (magic == "STEG") {
                    val type = decodedBytes[4].toInt()
                    val payloadLength = ((decodedBytes[5].toInt() and 0xFF) shl 24) or
                                        ((decodedBytes[6].toInt() and 0xFF) shl 16) or
                                        ((decodedBytes[7].toInt() and 0xFF) shl 8) or
                                        (decodedBytes[8].toInt() and 0xFF)
                    
                    val payloadStart = 9
                    val payloadEnd = payloadStart + payloadLength
                    if (payloadEnd <= decodedBytes.size) {
                        val payloadBytes = decodedBytes.sliceArray(payloadStart until payloadEnd)
                        
                        return when (type) {
                            1 -> { // Plain Text
                                val text = String(payloadBytes, Charsets.UTF_8)
                                DecodedPayload.Text(text)
                            }
                            2 -> { // Encrypted Text
                                if (password.isNullOrEmpty()) {
                                    DecodedPayload.Error("This text is encrypted. Please provide the correct decryption password.")
                                } else {
                                    val decryptedBytes = decryptAES(payloadBytes, password)
                                    val text = String(decryptedBytes, Charsets.UTF_8)
                                    DecodedPayload.Text(text)
                                }
                            }
                            3 -> { // Plain File
                                val fileNameLen = payloadBytes[0].toInt() and 0xFF
                                val fileName = String(payloadBytes.sliceArray(1..fileNameLen), Charsets.UTF_8)
                                val fileData = payloadBytes.sliceArray((1 + fileNameLen) until payloadBytes.size)
                                DecodedPayload.FilePayload(fileName, fileData)
                            }
                            4 -> { // Encrypted File
                                if (password.isNullOrEmpty()) {
                                    DecodedPayload.Error("This file is encrypted. Please provide the correct decryption password.")
                                } else {
                                    val decryptedBytes = decryptAES(payloadBytes, password)
                                    val fileNameLen = decryptedBytes[0].toInt() and 0xFF
                                    val fileName = String(decryptedBytes.sliceArray(1..fileNameLen), Charsets.UTF_8)
                                    val fileData = decryptedBytes.sliceArray((1 + fileNameLen) until decryptedBytes.size)
                                    DecodedPayload.FilePayload(fileName, fileData)
                                }
                            }
                            else -> DecodedPayload.Error("Unsupported advanced payload type index: $type.")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            return DecodedPayload.Error("Failed to parse advanced stego payload: ${e.message}")
        }
        
        // Fallback to legacy
        try {
            val oldText = decodeOldBlue(stegoBitmap)
            if (oldText.isNotEmpty()) {
                return DecodedPayload.Text(oldText)
            }
        } catch (e: Exception) {
            // ignore
        }
        
        return DecodedPayload.Error("No valid steganography signature found in this image.")
    }

    private fun encodeBytes(carrier: Bitmap, payload: ByteArray): Bitmap {
        val width = carrier.width
        val height = carrier.height
        val totalBits = payload.size * 8
        
        val maxBits = width * height * 3
        if (totalBits > maxBits) {
            throw IllegalArgumentException("Cover image too small. Required capacity: ${payload.size} bytes. Maximum: ${maxBits / 8} bytes.")
        }
        
        val mutableBitmap = carrier.copy(Bitmap.Config.ARGB_8888, true)
        
        var bitIndex = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (bitIndex >= totalBits) {
                    return mutableBitmap
                }
                
                val pixel = mutableBitmap.getPixel(x, y)
                val alpha = (pixel shr 24) and 0xFF
                var red = (pixel shr 16) and 0xFF
                var green = (pixel shr 8) and 0xFF
                var blue = pixel and 0xFF
                
                // Embed in Red channel LSB
                if (bitIndex < totalBits) {
                    val bytePos = bitIndex / 8
                    val bitPos = 7 - (bitIndex % 8)
                    val bit = (payload[bytePos].toInt() shr bitPos) and 1
                    red = (red and 0xFE) or bit
                    bitIndex++
                }
                
                // Embed in Green channel LSB
                if (bitIndex < totalBits) {
                    val bytePos = bitIndex / 8
                    val bitPos = 7 - (bitIndex % 8)
                    val bit = (payload[bytePos].toInt() shr bitPos) and 1
                    green = (green and 0xFE) or bit
                    bitIndex++
                }
                
                // Embed in Blue channel LSB
                if (bitIndex < totalBits) {
                    val bytePos = bitIndex / 8
                    val bitPos = 7 - (bitIndex % 8)
                    val bit = (payload[bytePos].toInt() shr bitPos) and 1
                    blue = (blue and 0xFE) or bit
                    bitIndex++
                }
                
                val newPixel = (alpha shl 24) or (red shl 16) or (green shl 8) or blue
                mutableBitmap.setPixel(x, y, newPixel)
            }
        }
        return mutableBitmap
    }

    private fun decodeBytes(stegoBitmap: Bitmap): ByteArray? {
        val width = stegoBitmap.width
        val height = stegoBitmap.height
        
        // Decode header first to get correct length
        val headerBytes = decodeBits(stegoBitmap, 9 * 8) ?: return null
        if (headerBytes.size < 9) return null
        
        val magic = String(headerBytes.sliceArray(0..3), Charsets.UTF_8)
        if (magic != "STEG") {
            return null
        }
        
        val payloadLength = ((headerBytes[5].toInt() and 0xFF) shl 24) or
                            ((headerBytes[6].toInt() and 0xFF) shl 16) or
                            ((headerBytes[7].toInt() and 0xFF) shl 8) or
                            (headerBytes[8].toInt() and 0xFF)
                            
        if (payloadLength < 0 || payloadLength > width * height * 3 / 8) {
            return null
        }
        
        val totalRequiredBytes = 9 + payloadLength
        return decodeBits(stegoBitmap, totalRequiredBytes * 8)
    }

    private fun decodeBits(stegoBitmap: Bitmap, totalBits: Int): ByteArray? {
        val width = stegoBitmap.width
        val height = stegoBitmap.height
        
        val maxBits = width * height * 3
        val bitsToRead = minOf(totalBits, maxBits)
        val resultBytes = ByteArray((bitsToRead + 7) / 8)
        
        var bitIndex = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (bitIndex >= bitsToRead) {
                    return resultBytes
                }
                
                val pixel = stegoBitmap.getPixel(x, y)
                val red = (pixel shr 16) and 1
                val green = (pixel shr 8) and 1
                val blue = pixel and 1
                
                // Read Red
                if (bitIndex < bitsToRead) {
                    val bytePos = bitIndex / 8
                    val bitPos = 7 - (bitIndex % 8)
                    resultBytes[bytePos] = (resultBytes[bytePos].toInt() or (red shl bitPos)).toByte()
                    bitIndex++
                }
                
                // Read Green
                if (bitIndex < bitsToRead) {
                    val bytePos = bitIndex / 8
                    val bitPos = 7 - (bitIndex % 8)
                    resultBytes[bytePos] = (resultBytes[bytePos].toInt() or (green shl bitPos)).toByte()
                    bitIndex++
                }
                
                // Read Blue
                if (bitIndex < bitsToRead) {
                    val bytePos = bitIndex / 8
                    val bitPos = 7 - (bitIndex % 8)
                    resultBytes[bytePos] = (resultBytes[bytePos].toInt() or (blue shl bitPos)).toByte()
                    bitIndex++
                }
            }
        }
        return resultBytes
    }

    private fun decodeOldBlue(stegoBitmap: Bitmap): String {
        val width = stegoBitmap.width
        val height = stegoBitmap.height
        val byteList = mutableListOf<Byte>()
        
        var currentByteValue = 0
        var bitCount = 0

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = stegoBitmap.getPixel(x, y)
                val blue = pixel and 1

                currentByteValue = (currentByteValue shl 1) or blue
                bitCount++

                if (bitCount == 8) {
                    val decodedByte = currentByteValue.toByte()
                    if (decodedByte.toInt() == 0) {
                        return String(byteList.toByteArray(), Charsets.UTF_8)
                    }
                    byteList.add(decodedByte)
                    currentByteValue = 0
                    bitCount = 0
                }
            }
        }
        
        return String(byteList.toByteArray(), Charsets.UTF_8)
    }

    private fun encryptAES(data: ByteArray, password: String): ByteArray {
        val keyBytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray(Charsets.UTF_8))
        val secretKey = SecretKeySpec(keyBytes, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = MessageDigest.getInstance("MD5").digest(password.toByteArray(Charsets.UTF_8))
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
        return cipher.doFinal(data)
    }

    private fun decryptAES(encryptedData: ByteArray, password: String): ByteArray {
        val keyBytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray(Charsets.UTF_8))
        val secretKey = SecretKeySpec(keyBytes, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = MessageDigest.getInstance("MD5").digest(password.toByteArray(Charsets.UTF_8))
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
        return cipher.doFinal(encryptedData)
    }
}
