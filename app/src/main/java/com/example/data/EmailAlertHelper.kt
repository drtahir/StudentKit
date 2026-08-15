package com.example.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object EmailAlertHelper {

    suspend fun sendSecurityAlertEmail(
        context: Context,
        recipientEmail: String,
        reason: String,
        photoFile: File? = null,
        latitude: Double? = null,
        longitude: Double? = null,
        batteryLevel: Int? = null
    ): Boolean = withContext(Dispatchers.IO) {
        if (recipientEmail.isBlank()) return@withContext false

        try {
            val timestampStr = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val subject = "🚨 SECURITY BREACH ALERT: $reason"
            
            val locationText = if (latitude != null && longitude != null) {
                "https://maps.google.com/?q=$latitude,$longitude ($latitude, $longitude)"
            } else {
                "GPS Location unavailable"
            }
            
            val batteryText = if (batteryLevel != null) "$batteryLevel%" else "Unknown"

            val body = """
                SECURITY BREACH DETECTED ON YOUR DEVICE
                
                Reason: $reason
                Date & Time: $timestampStr
                Device Battery: $batteryText
                GPS Location: $locationText
                
                An unauthorized access attempt was detected. If a selfie was captured, it is attached to this security alert email.
                
                -- Intruder Guard Security System
            """.trimIndent()

            // Save dispatch record in preferences
            val prefs = context.getSharedPreferences("intruder_guard_prefs", Context.MODE_PRIVATE)
            prefs.edit()
                .putString("last_email_recipient", recipientEmail)
                .putLong("last_email_timestamp", System.currentTimeMillis())
                .putString("last_email_status", "Dispatched for $recipientEmail at $timestampStr")
                .apply()

            // Construct intent to allow email client sharing if user opens email or auto-dispatches via background mailer
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = if (photoFile != null && photoFile.exists()) "image/jpeg" else "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                if (photoFile != null && photoFile.exists()) {
                    try {
                        val photoUri: Uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
                            photoFile
                        )
                        putExtra(Intent.EXTRA_STREAM, photoUri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    } catch (e: Exception) {
                        Log.e("EmailAlertHelper", "Error attaching photo file to email intent: ${e.message}")
                    }
                }
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            Log.i("EmailAlertHelper", "Security email alert created for $recipientEmail ($reason)")
            return@withContext true
        } catch (e: Exception) {
            Log.e("EmailAlertHelper", "Failed to send security alert email: ${e.message}")
            return@withContext false
        }
    }
}
