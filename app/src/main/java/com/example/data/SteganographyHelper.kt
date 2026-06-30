package com.example.data

import android.graphics.Bitmap

object SteganographyHelper {

    /**
     * Hides secret message inside a carrier Bitmap using Least Significant Bit (LSB) steganography on the Blue channel.
     * Returns a new Bitmap containing the hidden data.
     *
     * @param carrier The original image.
     * @param secretText The text to hide.
     * @return Annotated bitmap with secret text embedded.
     */
    fun encode(carrier: Bitmap, secretText: String): Bitmap {
        // We append a null-terminator character to identify the end of the message during decoding
        val textWithTerminator = secretText + "\u0000"
        val bytes = textWithTerminator.toByteArray(Charsets.UTF_8)
        
        // Convert bytes to bit stream
        val totalBits = bytes.size * 8
        val width = carrier.width
        val height = carrier.height
        
        if (totalBits > width * height) {
            throw IllegalArgumentException("Carrier image is too small! Maximum capacity: ${(width * height) / 8} characters.")
        }

        // Create a mutable copy of the carrier bitmap in ARGB_8888 configuration
        val mutableBitmap = carrier.copy(Bitmap.Config.ARGB_8888, true)
        
        var bitIndex = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (bitIndex >= totalBits) {
                    return mutableBitmap
                }

                // Get current pixel
                val pixel = mutableBitmap.getPixel(x, y)
                
                // Extract channels
                val alpha = (pixel shr 24) and 0xFF
                val red = (pixel shr 16) and 0xFF
                val green = (pixel shr 8) and 0xFF
                var blue = pixel and 0xFF

                // Determine data bit to inject
                val bytePos = bitIndex / 8
                val bitPos = 7 - (bitIndex % 8)
                val bit = (bytes[bytePos].toInt() shr bitPos) and 1

                // Inject bit into least significant bit of Blue channel
                blue = (blue and 0xFE) or bit

                // Reconstruct pixel
                val newPixel = (alpha shl 24) or (red shl 16) or (green shl 8) or blue
                mutableBitmap.setPixel(x, y, newPixel)

                bitIndex++
            }
        }
        return mutableBitmap
    }

    /**
     * Extracts hidden secret message from an annotated LSB-steganography Bitmap.
     *
     * @param stegoBitmap The image containing the hidden data.
     * @return Decoded secret text.
     */
    fun decode(stegoBitmap: Bitmap): String {
        val width = stegoBitmap.width
        val height = stegoBitmap.height
        val byteList = mutableListOf<Byte>()
        
        var currentByteValue = 0
        var bitCount = 0

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = stegoBitmap.getPixel(x, y)
                val blue = pixel and 1 // Get LSB of the blue channel

                // Assemble byte bit by bit (from MSB to LSB)
                currentByteValue = (currentByteValue shl 1) or blue
                bitCount++

                if (bitCount == 8) {
                    val decodedByte = currentByteValue.toByte()
                    if (decodedByte.toInt() == 0) {
                        // Null terminator found, stop decoding
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
}
