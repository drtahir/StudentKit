package com.drtahir.studentkit.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Locale

/**
 * Standard PDF ISO 32000-1 / PDF 1.4-1.7 Compliant Encryption & Security Engine.
 *
 * Implements the official PDF Standard Security Handler (Algorithm 3.2 - 3.5):
 * - 128-bit RC4 Encryption (V=2, R=3)
 * - Owner Key (/O) & User Key (/U) generation
 * - Object-specific key derivation: MD5(DocumentKey + ObjNum + GenNum)
 * - Encrypted Page Streams & Image XObjects
 * - Fully compatible with Adobe Acrobat Reader, Google Drive Viewer, Chrome, Android PDF Viewer, etc.
 */
object StandardPdfEncryptor {

    // Standard 32-byte padding string specified in ISO 32000-1 Section 7.6.3.3
    private val PADDING = byteArrayOf(
        0x28.toByte(), 0xBF.toByte(), 0x4E.toByte(), 0x5E.toByte(),
        0x4E.toByte(), 0x75.toByte(), 0x8A.toByte(), 0x41.toByte(),
        0x64.toByte(), 0x00.toByte(), 0x4E.toByte(), 0x56.toByte(),
        0xFF.toByte(), 0xFA.toByte(), 0x01.toByte(), 0x08.toByte(),
        0x2E.toByte(), 0x2E.toByte(), 0x00.toByte(), 0xB6.toByte(),
        0xD0.toByte(), 0x68.toByte(), 0x3E.toByte(), 0x80.toByte(),
        0x2F.toByte(), 0x0C.toByte(), 0xA9.toByte(), 0xFE.toByte(),
        0x64.toByte(), 0x53.toByte(), 0x69.toByte(), 0x7A.toByte()
    )

    // Standard Permissions: Default to -3904 (0xFFFF F0C0: allows printing, high-res print, accessibility once unlocked)
    const val DEFAULT_PERMISSIONS = -3904

    /**
     * RC4 / ARC4 Stream Cipher (pure Kotlin implementation for 100% deterministic reliability).
     */
    fun rc4(key: ByteArray, data: ByteArray): ByteArray {
        val s = IntArray(256) { it }
        var j = 0
        for (i in 0..255) {
            j = (j + s[i] + (key[i % key.size].toInt() and 0xFF)) and 0xFF
            val tmp = s[i]
            s[i] = s[j]
            s[j] = tmp
        }
        var i = 0
        j = 0
        val out = ByteArray(data.size)
        for (k in data.indices) {
            i = (i + 1) and 0xFF
            j = (j + s[i]) and 0xFF
            val tmp = s[i]
            s[i] = s[j]
            s[j] = tmp
            val t = (s[i] + s[j]) and 0xFF
            out[k] = (data[k].toInt() xor s[t]).toByte()
        }
        return out
    }

    private fun md5(vararg byteArrays: ByteArray): ByteArray {
        val md = MessageDigest.getInstance("MD5")
        for (ba in byteArrays) {
            md.update(ba)
        }
        return md.digest()
    }

    /**
     * Pad or truncate password to exactly 32 bytes using the standard PDF padding string.
     */
    fun padPassword(password: String): ByteArray {
        val passBytes = password.toByteArray(Charsets.ISO_8859_1)
        val result = ByteArray(32)
        if (passBytes.size >= 32) {
            System.arraycopy(passBytes, 0, result, 0, 32)
        } else {
            System.arraycopy(passBytes, 0, result, 0, passBytes.size)
            System.arraycopy(PADDING, 0, result, passBytes.size, 32 - passBytes.size)
        }
        return result
    }

    /**
     * Algorithm 3.3: Compute the /O (Owner password hash) entry for Revision 3 (128-bit key).
     */
    fun computeOwnerEntry(userPass: String, ownerPass: String, keyLengthBytes: Int = 16): ByteArray {
        val paddedOwner = padPassword(ownerPass)
        var ownerKey = md5(paddedOwner)
        // 50 iterations of MD5 for Revision 3
        for (i in 0 until 50) {
            ownerKey = md5(ownerKey.copyOf(keyLengthBytes))
        }

        val paddedUser = padPassword(userPass)
        var encrypted = rc4(ownerKey.copyOf(keyLengthBytes), paddedUser)

        // 19 iterations with XOR keys
        val baseKey = ownerKey.copyOf(keyLengthBytes)
        for (count in 1..19) {
            val iterKey = ByteArray(keyLengthBytes)
            for (b in 0 until keyLengthBytes) {
                iterKey[b] = (baseKey[b].toInt() xor count).toByte()
            }
            encrypted = rc4(iterKey, encrypted)
        }
        return encrypted
    }

    /**
     * Algorithm 3.2: Compute the Master Document Encryption Key (16 bytes for 128-bit).
     */
    fun computeEncryptionKey(
        userPass: String,
        oBytes: ByteArray,
        permissions: Int,
        fileId: ByteArray,
        keyLengthBytes: Int = 16
    ): ByteArray {
        val paddedUser = padPassword(userPass)
        val pBytes = byteArrayOf(
            (permissions and 0xFF).toByte(),
            ((permissions shr 8) and 0xFF).toByte(),
            ((permissions shr 16) and 0xFF).toByte(),
            ((permissions shr 24) and 0xFF).toByte()
        )

        var hash = md5(paddedUser, oBytes, pBytes, fileId)
        // 50 iterations of MD5 for Revision 3
        for (i in 0 until 50) {
            hash = md5(hash.copyOf(keyLengthBytes))
        }
        return hash.copyOf(keyLengthBytes)
    }

    /**
     * Algorithm 3.4 / 3.5: Compute the /U (User password hash) entry for Revision 3 (128-bit key).
     */
    fun computeUserEntry(
        encryptionKey: ByteArray,
        fileId: ByteArray,
        keyLengthBytes: Int = 16
    ): ByteArray {
        val padHash = md5(PADDING, fileId)
        var encrypted = rc4(encryptionKey.copyOf(keyLengthBytes), padHash)

        // 19 iterations with XOR keys
        for (count in 1..19) {
            val iterKey = ByteArray(keyLengthBytes)
            for (b in 0 until keyLengthBytes) {
                iterKey[b] = (encryptionKey[b].toInt() xor count).toByte()
            }
            encrypted = rc4(iterKey, encrypted)
        }

        val uBytes = ByteArray(32)
        System.arraycopy(encrypted, 0, uBytes, 0, 16)
        // Remaining 16 bytes padded with zeroes
        return uBytes
    }

    /**
     * Algorithm 3.1: Compute object-specific key for object number [objNum] and generation [genNum].
     */
    fun computeObjectKey(masterKey: ByteArray, objNum: Int, genNum: Int = 0): ByteArray {
        val objBytes = byteArrayOf(
            (objNum and 0xFF).toByte(),
            ((objNum shr 8) and 0xFF).toByte(),
            ((objNum shr 16) and 0xFF).toByte(),
            (genNum and 0xFF).toByte(),
            ((genNum shr 8) and 0xFF).toByte()
        )
        val hash = md5(masterKey, objBytes)
        val keyLen = minOf(masterKey.size + 5, 16)
        return hash.copyOf(keyLen)
    }

    fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuilder(bytes.size * 2)
        for (b in bytes) {
            sb.append(String.format(Locale.US, "%02x", b.toInt() and 0xFF))
        }
        return sb.toString()
    }

    /**
     * Check if a PDF stream or URI contains an /Encrypt dictionary indicating password protection.
     */
    fun isPdfPasswordProtected(inputStream: InputStream): Boolean {
        try {
            val buffer = ByteArray(8192)
            var bytesRead: Int
            val textBuilder = StringBuilder()
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                textBuilder.append(String(buffer, 0, bytesRead, Charsets.ISO_8859_1))
                if (textBuilder.length > 65536) {
                    textBuilder.delete(0, 32768)
                }
                if (textBuilder.contains("/Encrypt")) {
                    return true
                }
            }
            return textBuilder.contains("/Encrypt")
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun createLockedPreviewBitmap(): Bitmap {
        val bmp = Bitmap.createBitmap(220, 300, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        canvas.drawColor(android.graphics.Color.rgb(245, 247, 250))
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            color = android.graphics.Color.rgb(229, 57, 53)
            style = android.graphics.Paint.Style.FILL
        }
        // Lock body
        canvas.drawRoundRect(android.graphics.RectF(60f, 120f, 160f, 210f), 12f, 12f, paint)
        // Lock shackle
        paint.style = android.graphics.Paint.Style.STROKE
        paint.strokeWidth = 14f
        canvas.drawArc(android.graphics.RectF(75f, 65f, 145f, 135f), 180f, 180f, false, paint)
        // Keyhole
        paint.style = android.graphics.Paint.Style.FILL
        paint.color = android.graphics.Color.WHITE
        canvas.drawCircle(110f, 155f, 8f, paint)
        paint.strokeWidth = 6f
        canvas.drawRect(107f, 155f, 113f, 180f, paint)
        // Text
        paint.color = android.graphics.Color.rgb(30, 41, 59)
        paint.textSize = 14f
        paint.textAlign = android.graphics.Paint.Align.CENTER
        paint.isFakeBoldText = true
        canvas.drawText("PASSWORD LOCKED", 110f, 245f, paint)
        paint.color = android.graphics.Color.rgb(100, 116, 139)
        paint.textSize = 11f
        paint.isFakeBoldText = false
        canvas.drawText("128-bit Encrypted", 110f, 268f, paint)
        return bmp
    }

    data class PageBitmapSpec(
        val bitmap: Bitmap,
        val widthPt: Float,
        val heightPt: Float
    )

    /**
     * Creates a fully compliant Standard PDF with 128-bit Password Encryption from a list of pages.
     */
    fun generateEncryptedPdf(
        pages: List<PageBitmapSpec>,
        userPassword: String,
        ownerPassword: String = userPassword.ifEmpty { "studentkit_owner" },
        permissions: Int = DEFAULT_PERMISSIONS,
        title: String = "Protected Document",
        author: String = "StudentKit Secured"
    ): ByteArray {
        val pageCount = pages.size.coerceAtLeast(1)
        val random = SecureRandom()
        val fileId = ByteArray(16).apply { random.nextBytes(this) }

        // 1. Calculate Standard Security Encryption Keys
        val oBytes = computeOwnerEntry(userPassword, ownerPassword, 16)
        val masterKey = computeEncryptionKey(userPassword, oBytes, permissions, fileId, 16)
        val uBytes = computeUserEntry(masterKey, fileId, 16)

        // Object ID Layout:
        // 1: Catalog
        // 2: Pages tree
        // For page i (0 until pageCount):
        //   pageObjId = 3 + i * 3
        //   contentObjId = 3 + i * 3 + 1
        //   imageObjId = 3 + i * 3 + 2
        // encryptObjId = 3 + pageCount * 3
        // infoObjId = encryptObjId + 1
        val encryptObjId = 3 + pageCount * 3
        val infoObjId = encryptObjId + 1
        val totalObjects = infoObjId

        val offsets = LongArray(totalObjects + 1)
        val out = ByteArrayOutputStream()

        fun write(str: String) {
            out.write(str.toByteArray(Charsets.ISO_8859_1))
        }

        fun writeBytes(bytes: ByteArray) {
            out.write(bytes)
        }

        // Header
        write("%PDF-1.4\n%\u00E2\u00E3\u00CF\u00D3\n")

        // 1. Catalog Object
        offsets[1] = out.size().toLong()
        write("1 0 obj\n<<\n  /Type /Catalog\n  /Pages 2 0 R\n>>\nendobj\n")

        // 2. Pages Object
        offsets[2] = out.size().toLong()
        val kidsList = (0 until pageCount).joinToString(" ") { i -> "${3 + i * 3} 0 R" }
        write("2 0 obj\n<<\n  /Type /Pages\n  /Kids [ $kidsList ]\n  /Count $pageCount\n>>\nendobj\n")

        // Pages, Contents, and Image Objects
        for (i in 0 until pageCount) {
            val pageSpec = pages[i]
            val pageObjId = 3 + i * 3
            val contentObjId = pageObjId + 1
            val imageObjId = pageObjId + 2

            val wPt = pageSpec.widthPt
            val hPt = pageSpec.heightPt
            val bmp = pageSpec.bitmap

            // Page Object
            offsets[pageObjId] = out.size().toLong()
            write("$pageObjId 0 obj\n<<\n  /Type /Page\n  /Parent 2 0 R\n  /MediaBox [ 0 0 $wPt $hPt ]\n  /Resources <<\n    /XObject << /Im0 $imageObjId 0 R >>\n    /ProcSet [ /PDF /ImageC ]\n  >>\n  /Contents $contentObjId 0 R\n>>\nendobj\n")

            // Content Stream (Draws Im0 scaled to full page)
            val contentStr = "q\n$wPt 0 0 $hPt 0 0 cm\n/Im0 Do\nQ\n"
            val rawContentBytes = contentStr.toByteArray(Charsets.ISO_8859_1)
            val contentKey = computeObjectKey(masterKey, contentObjId, 0)
            val encryptedContentBytes = rc4(contentKey, rawContentBytes)

            offsets[contentObjId] = out.size().toLong()
            write("$contentObjId 0 obj\n<<\n  /Length ${encryptedContentBytes.size}\n>>\nstream\n")
            writeBytes(encryptedContentBytes)
            write("\nendstream\nendobj\n")

            // Image XObject (JPEG Compressed Bitmap)
            val jpegStream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 92, jpegStream)
            val rawJpegBytes = jpegStream.toByteArray()

            val imageKey = computeObjectKey(masterKey, imageObjId, 0)
            val encryptedJpegBytes = rc4(imageKey, rawJpegBytes)

            offsets[imageObjId] = out.size().toLong()
            write("$imageObjId 0 obj\n<<\n  /Type /XObject\n  /Subtype /Image\n  /Width ${bmp.width}\n  /Height ${bmp.height}\n  /ColorSpace /DeviceRGB\n  /BitsPerComponent 8\n  /Filter /DCTDecode\n  /Length ${encryptedJpegBytes.size}\n>>\nstream\n")
            writeBytes(encryptedJpegBytes)
            write("\nendstream\nendobj\n")
        }

        // Encrypt Object
        offsets[encryptObjId] = out.size().toLong()
        val oHex = bytesToHex(oBytes)
        val uHex = bytesToHex(uBytes)
        write("$encryptObjId 0 obj\n<<\n  /Filter /Standard\n  /V 2\n  /R 3\n  /Length 128\n  /P $permissions\n  /O <$oHex>\n  /U <$uHex>\n>>\nendobj\n")

        // Info Object (Encrypted strings)
        offsets[infoObjId] = out.size().toLong()
        val infoKey = computeObjectKey(masterKey, infoObjId, 0)
        val encTitleHex = bytesToHex(rc4(infoKey, title.toByteArray(Charsets.ISO_8859_1)))
        val encAuthorHex = bytesToHex(rc4(infoKey, author.toByteArray(Charsets.ISO_8859_1)))
        val encCreatorHex = bytesToHex(rc4(infoKey, "StudentKit Security Suite".toByteArray(Charsets.ISO_8859_1)))
        write("$infoObjId 0 obj\n<<\n  /Title <$encTitleHex>\n  /Author <$encAuthorHex>\n  /Creator <$encCreatorHex>\n>>\nendobj\n")

        // XRef Table
        val startXref = out.size().toLong()
        write("xref\n0 ${totalObjects + 1}\n0000000000 65535 f \n")
        for (objIdx in 1..totalObjects) {
            val offsetFormatted = String.format(Locale.US, "%010d", offsets[objIdx])
            write("$offsetFormatted 00000 n \n")
        }

        // Trailer
        val fileIdHex = bytesToHex(fileId)
        write("trailer\n<<\n  /Size ${totalObjects + 1}\n  /Root 1 0 R\n  /Info $infoObjId 0 R\n  /Encrypt $encryptObjId 0 R\n  /ID [ <$fileIdHex> <$fileIdHex> ]\n>>\nstartxref\n$startXref\n%%EOF\n")

        return out.toByteArray()
    }

    /**
     * Encrypt an existing PDF file directly by rendering its pages with crisp DPI and packing into standard encrypted PDF format.
     */
    fun encryptPdfFile(
        context: Context,
        uri: Uri,
        passwordText: String,
        hintText: String = "",
        onProgress: (Float, String) -> Unit = { _, _ -> }
    ): ByteArray? {
        val pfd: ParcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r") ?: return null
        return try {
            val renderer = PdfRenderer(pfd)
            val totalPages = renderer.pageCount.coerceAtLeast(1)
            val pageSpecs = ArrayList<PageBitmapSpec>(totalPages)

            for (i in 0 until totalPages) {
                val prog = 0.05f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.75f
                onProgress(prog, "Encrypting Page ${i + 1} of $totalPages...")

                val page = renderer.openPage(i)
                val widthPt = page.width.toFloat()
                val heightPt = page.height.toFloat()

                // Render at high resolution 1.8x scale (130 DPI) for crisp rendering
                val scale = 1.8f
                val targetW = (widthPt * scale).toInt().coerceAtLeast(100)
                val targetH = (heightPt * scale).toInt().coerceAtLeast(100)
                val bmp = Bitmap.createBitmap(targetW, targetH, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bmp)
                canvas.drawColor(android.graphics.Color.WHITE)
                page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()

                pageSpecs.add(PageBitmapSpec(bmp, widthPt, heightPt))
            }
            renderer.close()
            pfd.close()

            onProgress(0.85f, "Assembling Standard 128-bit Security Envelope...")
            val finalPassword = passwordText.ifEmpty { "studentkit" }
            val encryptedBytes = generateEncryptedPdf(
                pages = pageSpecs,
                userPassword = finalPassword,
                title = if (hintText.isNotBlank()) "Protected (Hint: $hintText)" else "Protected Document"
            )

            // Recycle intermediate bitmaps
            for (spec in pageSpecs) {
                spec.bitmap.recycle()
            }

            onProgress(0.98f, "Finalizing Secured PDF...")
            encryptedBytes
        } catch (e: Exception) {
            e.printStackTrace()
            try { pfd.close() } catch (_: Exception) {}
            null
        }
    }
}
