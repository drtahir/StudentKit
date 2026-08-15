package com.example.viewmodel

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FaceRestorer
import com.example.data.ImageEnhancer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

enum class EnhancePassProfile(val displayName: String, val description: String, val totalPasses: Int) {
    FAST("Fast (1 Pass)", "Standard 4x Super Resolution + Face restore", 1),
    BALANCED("Balanced (2 Pass)", "Pre-Denoise + 4x Super-Res + Micro-Detail Edge Refine", 2),
    ULTRA_STUDIO("Ultra Studio (3 Pass)", "Full Multi-Pass: Denoise, 4x TFLite, Sharpening, GFPGAN & Vibrance", 3)
}

sealed class EnhanceUiState {
    object Idle : EnhanceUiState()
    data class Processing(val progress: Float, val message: String, val currentPass: Int, val totalPasses: Int) : EnhanceUiState()
    data class Success(
        val original: Bitmap,
        val enhanced: Bitmap,
        val facesCount: Int,
        val isModelMode: Boolean,
        val profileName: String,
        val passesApplied: Int
    ) : EnhanceUiState()
    data class Error(val message: String) : EnhanceUiState()
}

class EnhanceViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "EnhanceViewModel"

    private val _uiState = MutableStateFlow<EnhanceUiState>(EnhanceUiState.Idle)
    val uiState: StateFlow<EnhanceUiState> = _uiState.asStateFlow()

    private val _originalImage = MutableStateFlow<Bitmap?>(null)
    val originalImage: StateFlow<Bitmap?> = _originalImage.asStateFlow()

    private val _enhancedImage = MutableStateFlow<Bitmap?>(null)
    val enhancedImage: StateFlow<Bitmap?> = _enhancedImage.asStateFlow()

    private val _modelLoaded = MutableStateFlow(false)
    val modelLoaded: StateFlow<Boolean> = _modelLoaded.asStateFlow()

    // Multi-pass Quality Controls State
    private val _passProfile = MutableStateFlow(EnhancePassProfile.ULTRA_STUDIO)
    val passProfile: StateFlow<EnhancePassProfile> = _passProfile.asStateFlow()

    private val _sharpeningStrength = MutableStateFlow(0.5f) // 0.0f to 1.0f
    val sharpeningStrength: StateFlow<Float> = _sharpeningStrength.asStateFlow()

    private val _skinSmoothStrength = MutableStateFlow(0.85f) // 0.0f to 1.0f
    val skinSmoothStrength: StateFlow<Float> = _skinSmoothStrength.asStateFlow()

    private val _enablePreDenoise = MutableStateFlow(true)
    val enablePreDenoise: StateFlow<Boolean> = _enablePreDenoise.asStateFlow()

    private val _enableColorBoost = MutableStateFlow(true)
    val enableColorBoost: StateFlow<Boolean> = _enableColorBoost.asStateFlow()

    init {
        checkModelsStatus()
    }

    fun setPassProfile(profile: EnhancePassProfile) {
        _passProfile.value = profile
        when (profile) {
            EnhancePassProfile.FAST -> {
                _sharpeningStrength.value = 0.2f
                _skinSmoothStrength.value = 0.70f
                _enablePreDenoise.value = false
                _enableColorBoost.value = false
            }
            EnhancePassProfile.BALANCED -> {
                _sharpeningStrength.value = 0.4f
                _skinSmoothStrength.value = 0.80f
                _enablePreDenoise.value = true
                _enableColorBoost.value = false
            }
            EnhancePassProfile.ULTRA_STUDIO -> {
                _sharpeningStrength.value = 0.6f
                _skinSmoothStrength.value = 0.88f
                _enablePreDenoise.value = true
                _enableColorBoost.value = true
            }
        }
    }

    fun setSharpeningStrength(value: Float) {
        _sharpeningStrength.value = value.coerceIn(0f, 1f)
    }

    fun setSkinSmoothStrength(value: Float) {
        _skinSmoothStrength.value = value.coerceIn(0f, 1f)
    }

    fun setEnablePreDenoise(enabled: Boolean) {
        _enablePreDenoise.value = enabled
    }

    fun setEnableColorBoost(enabled: Boolean) {
        _enableColorBoost.value = enabled
    }

    fun checkModelsStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>().applicationContext
            val isEnhancerOk = ImageEnhancer.initInterpreter(context)
            val isRestorerOk = FaceRestorer.initInterpreter(context)
            _modelLoaded.value = isEnhancerOk && isRestorerOk
        }
    }

    fun setOriginalImage(bitmap: Bitmap) {
        _originalImage.value = bitmap
        _enhancedImage.value = null
        _uiState.value = EnhanceUiState.Idle
    }

    fun startEnhancement() {
        val original = _originalImage.value ?: run {
            _uiState.value = EnhanceUiState.Error("Please select or capture an image first.")
            return
        }

        viewModelScope.launch {
            val profile = _passProfile.value
            val doDenoise = _enablePreDenoise.value
            val doColorBoost = _enableColorBoost.value
            val sharpLevel = _sharpeningStrength.value
            val skinLevel = _skinSmoothStrength.value

            // Calculate active total passes
            var totalPassesCount = 1 // Super Res is mandatory
            if (doDenoise) totalPassesCount++
            if (sharpLevel > 0.05f) totalPassesCount++
            if (doColorBoost) totalPassesCount++

            var currentPassNumber = 1

            _uiState.value = EnhanceUiState.Processing(
                progress = 0.01f,
                message = "Initializing Multi-Pass Engine (${profile.displayName})...",
                currentPass = currentPassNumber,
                totalPasses = totalPassesCount
            )

            try {
                val context = getApplication<Application>().applicationContext

                withContext(Dispatchers.Default) {
                    var currentBitmap = original

                    // PASS 1: Pre-Denoise Filter
                    if (doDenoise) {
                        _uiState.value = EnhanceUiState.Processing(
                            progress = 0.08f,
                            message = "Pass $currentPassNumber/$totalPassesCount: Pre-denoising & artifact smoothing...",
                            currentPass = currentPassNumber,
                            totalPasses = totalPassesCount
                        )
                        currentBitmap = ImageEnhancer.applyPreDenoiseFilter(currentBitmap)
                        currentPassNumber++
                    }

                    // PASS 2 (or next): ML Kit Portrait Face Detection
                    _uiState.value = EnhanceUiState.Processing(
                        progress = 0.15f,
                        message = "Detecting portrait face landmarks...",
                        currentPass = currentPassNumber,
                        totalPasses = totalPassesCount
                    )
                    val detectedFaces = FaceRestorer.detectFaces(original)
                    val facesCount = detectedFaces.size
                    val facePassExtra = if (facesCount > 0) 1 else 0
                    val finalTotalPasses = totalPassesCount + facePassExtra

                    // PASS NEXT: TFLite 4x Super-Resolution Upscaling (Real-ESRGAN)
                    _uiState.value = EnhanceUiState.Processing(
                        progress = 0.20f,
                        message = "Pass $currentPassNumber/$finalTotalPasses: Neural 4x Super-Resolution Tiling...",
                        currentPass = currentPassNumber,
                        totalPasses = finalTotalPasses
                    )

                    var enhancedBg = ImageEnhancer.enhanceImage(context, currentBitmap) { progress ->
                        val baseProgress = 0.20f + (progress * 0.45f)
                        val pct = (progress * 100).toInt()
                        _uiState.value = EnhanceUiState.Processing(
                            progress = baseProgress,
                            message = "Pass $currentPassNumber/$finalTotalPasses: TFLite 4x Super-Res ($pct%)...",
                            currentPass = currentPassNumber,
                            totalPasses = finalTotalPasses
                        )
                    }
                    currentPassNumber++

                    // PASS NEXT: Micro-Detail Unsharp Edge Sharpening
                    if (sharpLevel > 0.05f) {
                        _uiState.value = EnhanceUiState.Processing(
                            progress = 0.68f,
                            message = "Pass $currentPassNumber/$finalTotalPasses: Micro-detail edge recovery (${(sharpLevel * 100).toInt()}%)...",
                            currentPass = currentPassNumber,
                            totalPasses = finalTotalPasses
                        )
                        enhancedBg = ImageEnhancer.applyUnsharpMask(enhancedBg, sharpLevel)
                        currentPassNumber++
                    }

                    // PASS NEXT: GFPGAN Portrait & Skin Restoration
                    val faceRestoredResult = if (facesCount > 0) {
                        _uiState.value = EnhanceUiState.Processing(
                            progress = 0.78f,
                            message = "Pass $currentPassNumber/$finalTotalPasses: GFPGAN face restoration & skin blend...",
                            currentPass = currentPassNumber,
                            totalPasses = finalTotalPasses
                        )
                        val res = FaceRestorer.restoreFacesAndStitch(
                            context = context,
                            originalBitmap = original,
                            enhancedBackground = enhancedBg,
                            faces = detectedFaces,
                            faceBlendAlpha = skinLevel
                        ) { progress ->
                            val baseProg = 0.78f + (progress * 0.15f)
                            _uiState.value = EnhanceUiState.Processing(
                                progress = baseProg,
                                message = "Pass $currentPassNumber/$finalTotalPasses: Restoring portrait faces (${(progress * 100).toInt()}%)...",
                                currentPass = currentPassNumber,
                                totalPasses = finalTotalPasses
                            )
                        }
                        currentPassNumber++
                        res
                    } else {
                        enhancedBg
                    }

                    // PASS NEXT: Studio Color & Dynamic Contrast Finishing Pass
                    val finalResult = if (doColorBoost) {
                        _uiState.value = EnhanceUiState.Processing(
                            progress = 0.95f,
                            message = "Pass $currentPassNumber/$finalTotalPasses: Studio color vibrance & dynamic tone curve...",
                            currentPass = currentPassNumber,
                            totalPasses = finalTotalPasses
                        )
                        ImageEnhancer.applyColorAndVibranceBoost(faceRestoredResult)
                    } else {
                        faceRestoredResult
                    }

                    _uiState.value = EnhanceUiState.Processing(1.0f, "Polishing high-res output...", finalTotalPasses, finalTotalPasses)
                    _enhancedImage.value = finalResult
                    _uiState.value = EnhanceUiState.Success(
                        original = original,
                        enhanced = finalResult,
                        facesCount = facesCount,
                        isModelMode = ImageEnhancer.isModelLoaded,
                        profileName = profile.displayName,
                        passesApplied = finalTotalPasses
                    )
                }

            } catch (oom: OutOfMemoryError) {
                Log.e(TAG, "Out of Memory during image enhancement process: ${oom.message}")
                System.gc()
                _uiState.value = EnhanceUiState.Error(
                    "Out of Memory Error!\nThe image is too large for your device's RAM budget. Please crop the image or select a smaller image."
                )
            } catch (e: Exception) {
                Log.e(TAG, "Enhancement failed: ${e.message}", e)
                _uiState.value = EnhanceUiState.Error("Enhancement Failed: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }

    /**
     * Saves the upscaled enhanced bitmap to the device Gallery.
     */
    fun saveToGallery(context: Context, callback: (Boolean, String?) -> Unit) {
        val bitmap = _enhancedImage.value ?: run {
            callback(false, "No enhanced image to save.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val filename = "Enhanced_${System.currentTimeMillis()}.png"
            var outputStream: OutputStream? = null
            var imageUri: Uri? = null

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val resolver = context.contentResolver
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/AIEnhancer")
                    }
                    imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    if (imageUri != null) {
                        outputStream = resolver.openOutputStream(imageUri)
                    }
                } else {
                    val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/AIEnhancer"
                    val dir = File(imagesDir)
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                    val file = File(dir, filename)
                    outputStream = FileOutputStream(file)
                    // Trigger media scanner
                    val values = ContentValues().apply {
                        put(MediaStore.Images.Media.DATA, file.absolutePath)
                        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    }
                    context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                }

                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()
                    withContext(Dispatchers.Main) {
                        callback(true, "Successfully saved to gallery!")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        callback(false, "Could not open stream to save image.")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to save image: ${e.message}")
                withContext(Dispatchers.Main) {
                    callback(false, "Save failed: ${e.localizedMessage}")
                }
            } finally {
                outputStream?.close()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ImageEnhancer.close()
        FaceRestorer.close()
    }
}
