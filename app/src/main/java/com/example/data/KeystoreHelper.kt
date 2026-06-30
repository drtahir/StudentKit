package com.example.data

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object KeystoreHelper {

    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val FILE_ENCRYPT_ALIAS = "com.example.studentkit.filekey"

    /**
     * Retrieves or generates a 256-bit AES key securely inside the Android KeyStore hardware.
     */
    fun getOrCreateHardwareKey(alias: String = FILE_ENCRYPT_ALIAS): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        val existingKey = keyStore.getKey(alias, null) as? SecretKey
        if (existingKey != null) return existingKey

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )
        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    /**
     * Encrypt an entire file in-place or to a destination using Android Keystore AES-256-GCM.
     */
    fun encryptFileWithKeystore(inputFile: File, outputFile: File, alias: String = FILE_ENCRYPT_ALIAS) {
        val secretKey = getOrCreateHardwareKey(alias)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        
        val iv = cipher.iv
        val plainBytes = inputFile.readBytes()
        val cipherBytes = cipher.doFinal(plainBytes)

        outputFile.outputStream().use { fos ->
            // Format: [1 byte: IV length] + [IV bytes] + [CipherBytes]
            fos.write(iv.size)
            fos.write(iv)
            fos.write(cipherBytes)
        }
    }

    /**
     * Decrypt an entire file in-place or to a destination using Android Keystore AES-256-GCM.
     */
    fun decryptFileWithKeystore(inputFile: File, outputFile: File, alias: String = FILE_ENCRYPT_ALIAS) {
        val secretKey = getOrCreateHardwareKey(alias)
        inputFile.inputStream().use { fis ->
            val ivSize = fis.read()
            if (ivSize <= 0 || ivSize > 128) throw IllegalArgumentException("Invalid IV size in encrypted file")
            val iv = ByteArray(ivSize)
            fis.read(iv)
            val cipherBytes = fis.readBytes()

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val spec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
            val plainBytes = cipher.doFinal(cipherBytes)
            outputFile.writeBytes(plainBytes)
        }
    }

    /**
     * Write a secure file to private storage using Jetpack security's EncryptedFile.
     */
    fun writeSecureFile(context: Context, destinationFile: File, dataBytes: ByteArray) {
        if (destinationFile.exists()) {
            destinationFile.delete()
        }
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val encryptedFile = EncryptedFile.Builder(
            destinationFile,
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        encryptedFile.openFileOutput().use { outputStream ->
            outputStream.write(dataBytes)
        }
    }

    /**
     * Read a secure file from private storage using Jetpack security's EncryptedFile.
     */
    fun readSecureFile(context: Context, sourceFile: File): ByteArray {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val encryptedFile = EncryptedFile.Builder(
            sourceFile,
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        encryptedFile.openFileInput().use { inputStream ->
            return inputStream.readBytes()
        }
    }
}
