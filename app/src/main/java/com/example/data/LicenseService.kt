package com.studentkit.buner.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeParseException

enum class LicenseStatus { VALID, EXPIRED, INVALID, GRACE }

class LicenseService(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "studentkit_license_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val firestore = FirebaseFirestore.getInstance()
    private val collectionName = "studentkit_licenses"

    private val keyPhone = "license_phone"
    private val keyKey = "license_key"
    private val keyGraceStart = "grace_start"

    suspend fun checkLicense(): LicenseStatus {
        val phone = prefs.getString(keyPhone, null)
        val key = prefs.getString(keyKey, null)
        if (phone == null || key == null) return LicenseStatus.INVALID

        return try {
            val doc = firestore.collection(collectionName).document(phone).get().await()
            if (!doc.exists()) return LicenseStatus.INVALID
            val storedKey = doc.getString("licenseKey")
            if (storedKey != key) return LicenseStatus.INVALID
            val expiry = doc.getString("expiryDate")
            if (expiry.isNullOrBlank()) return LicenseStatus.VALID
            val expiryDate = try { LocalDate.parse(expiry) } catch (e: DateTimeParseException) { return LicenseStatus.VALID }
            if (!LocalDate.now().isAfter(expiryDate)) LicenseStatus.VALID else handleExpired()
        } catch (e: Exception) {
            handleOffline()
        }
    }

    private fun handleExpired(): LicenseStatus {
        prefs.edit().putString(keyGraceStart, LocalDate.now().toString()).apply()
        return LicenseStatus.EXPIRED
    }

    private fun handleOffline(): LicenseStatus {
        val graceStr = prefs.getString(keyGraceStart, null)
        if (graceStr == null) {
            prefs.edit().putString(keyGraceStart, LocalDate.now().toString()).apply()
            return LicenseStatus.GRACE
        }
        val graceStart = LocalDate.parse(graceStr)
        val days = java.time.temporal.ChronoUnit.DAYS.between(graceStart, LocalDate.now())
        return if (days <= 7) LicenseStatus.GRACE else LicenseStatus.INVALID
    }

    suspend fun activateLicense(phone: String, key: String): Boolean {
        return try {
            val doc = firestore.collection(collectionName).document(phone).get().await()
            if (!doc.exists()) return false
            if (doc.getString("licenseKey") != key) return false
            prefs.edit().putString(keyPhone, phone).putString(keyKey, key).remove(keyGraceStart).apply()
            true
        } catch (e: Exception) { false }
    }

    fun logout() {
        prefs.edit().remove(keyPhone).remove(keyKey).remove(keyGraceStart).apply()
    }
}
