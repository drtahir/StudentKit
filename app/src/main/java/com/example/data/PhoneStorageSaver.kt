package com.example.data

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

object PhoneStorageSaver {

    private const val TAG = "PhoneStorageSaver"

    /**
     * Saves a PDF file to the device's public Downloads directory.
     * Returns theUri or null if failed.
     */
    suspend fun savePdfToPhoneMemory(
        context: Context,
        pdfFile: File,
        desiredFileName: String
    ): Uri? = withContext(Dispatchers.IO) {
        try {
            val fileName = if (desiredFileName.endsWith(".pdf", ignoreCase = true)) desiredFileName else "$desiredFileName.pdf"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }

                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                if (uri != null) {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        pdfFile.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "📄 Saved to Phone Memory Downloads: $fileName", Toast.LENGTH_LONG).show()
                    }
                    return@withContext uri
                }
            }

            // Fallback for older devices or public storage
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs()
            }
            val destinationFile = File(downloadsDir, fileName)
            pdfFile.copyTo(destinationFile, overwrite = true)

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                destinationFile
            )

            // Trigger Media Scanner
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            mediaScanIntent.data = Uri.fromFile(destinationFile)
            context.sendBroadcast(mediaScanIntent)

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "📄 Saved to Phone Memory: ${destinationFile.absolutePath}", Toast.LENGTH_LONG).show()
            }
            return@withContext uri
        } catch (e: Exception) {
            Log.e(TAG, "Error saving PDF to phone memory: ${e.message}", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Failed to save PDF to phone memory: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
            return@withContext null
        }
    }

    /**
     * Saves raw ByteArray bytes as a PDF to Phone Downloads
     */
    suspend fun savePdfBytesToPhoneMemory(
        context: Context,
        bytes: ByteArray,
        desiredFileName: String
    ): Uri? = withContext(Dispatchers.IO) {
        val tempFile = File(context.cacheDir, "temp_${System.currentTimeMillis()}.pdf")
        try {
            FileOutputStream(tempFile).use { it.write(bytes) }
            return@withContext savePdfToPhoneMemory(context, tempFile, desiredFileName)
        } finally {
            if (tempFile.exists()) tempFile.delete()
        }
    }

    /**
     * Saves an image File (PNG or JPEG) to device Pictures/Gallery or Downloads
     */
    suspend fun saveImageToPhoneMemory(
        context: Context,
        imageFile: File,
        desiredFileName: String,
        mimeType: String = "image/jpeg"
    ): Uri? = withContext(Dispatchers.IO) {
        try {
            val extension = if (mimeType.contains("png")) ".png" else ".jpg"
            val fileName = if (desiredFileName.contains(".")) desiredFileName else "$desiredFileName$extension"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/StudentKit")
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }

                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                if (uri != null) {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        imageFile.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "🖼️ Saved image to Phone Pictures: $fileName", Toast.LENGTH_LONG).show()
                    }
                    return@withContext uri
                }
            }

            val picturesDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StudentKit")
            if (!picturesDir.exists()) {
                picturesDir.mkdirs()
            }
            val destinationFile = File(picturesDir, fileName)
            imageFile.copyTo(destinationFile, overwrite = true)

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                destinationFile
            )

            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            mediaScanIntent.data = Uri.fromFile(destinationFile)
            context.sendBroadcast(mediaScanIntent)

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "🖼️ Saved image to Phone Gallery: ${destinationFile.absolutePath}", Toast.LENGTH_LONG).show()
            }
            return@withContext uri
        } catch (e: Exception) {
            Log.e(TAG, "Error saving image to phone memory: ${e.message}", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Failed to save image to phone memory", Toast.LENGTH_SHORT).show()
            }
            return@withContext null
        }
    }

    /**
     * Saves text or CSV/JSON documents to device Downloads
     */
    suspend fun saveTextDocumentToPhoneMemory(
        context: Context,
        content: String,
        desiredFileName: String,
        mimeType: String = "text/plain"
    ): Uri? = withContext(Dispatchers.IO) {
        try {
            val fileName = desiredFileName
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }

                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                if (uri != null) {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        outputStream.write(content.toByteArray(Charsets.UTF_8))
                    }
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "📝 Document saved to Downloads: $fileName", Toast.LENGTH_LONG).show()
                    }
                    return@withContext uri
                }
            }

            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists()) downloadsDir.mkdirs()
            val destinationFile = File(downloadsDir, fileName)
            destinationFile.writeText(content, Charsets.UTF_8)

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                destinationFile
            )

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "📝 Document saved to Phone Storage: ${destinationFile.name}", Toast.LENGTH_LONG).show()
            }
            return@withContext uri
        } catch (e: Exception) {
            Log.e(TAG, "Error saving document: ${e.message}", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Failed to save document to phone memory", Toast.LENGTH_SHORT).show()
            }
            return@withContext null
        }
    }

    /**
     * General File Copy to Downloads with FileProvider Uri returned for instant viewing/sharing
     */
    suspend fun exportFileToPublicStorage(
        context: Context,
        sourceFile: File,
        outputFileName: String,
        mimeType: String = "*/*"
    ): Uri? = withContext(Dispatchers.IO) {
        if (!sourceFile.exists()) return@withContext null
        if (mimeType.contains("pdf", ignoreCase = true)) {
            return@withContext savePdfToPhoneMemory(context, sourceFile, outputFileName)
        } else if (mimeType.contains("image", ignoreCase = true)) {
            return@withContext saveImageToPhoneMemory(context, sourceFile, outputFileName, mimeType)
        } else {
            return@withContext saveTextDocumentToPhoneMemory(context, sourceFile.readText(), outputFileName, mimeType)
        }
    }
}
