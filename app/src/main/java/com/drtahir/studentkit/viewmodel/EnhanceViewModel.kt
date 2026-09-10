package com.drtahir.studentkit.viewmodel

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
import com.drtahir.studentkit.data.FaceRestorer
import com.drtahir.studentkit.data.ImageEnhancer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

enum class TargetScale(val factor: Float, val title: String, val subtitle: String) {
    SCALE_1X(1.0f, "1x Native Size", "Deblur & Clear Face (No Size Bloat)"),
    SCALE_2X(2.0f, "2x HD Upscale", "Clear Details + High Definition"),
    SCALE_4X(4.0f, "4x Ultra HD", "Quadruple Res (For small/old photos)")
}

enum class EnhancePassProfile(val displayName: String, val description: String, val totalPasses: Int) {
    FAST("Fast Deblur", "Quick multi-scale clarity & face sharpening", 2),
    BALANCED("Balanced Portrait", "Pre-Denoise + De-blur + Facial Reconstruction", 3),
    ULTRA_STUDIO("Ultra Studio Pro", "Full Multi-Pass: Deblur, Eyes/Mouth Recovery, Edge & Vibrance", 4)
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
        val passesApplied: Int,
        val scaleFactor: Float
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

    // Scale / Output Size Control (defaults to 1x for normal images to avoid file size inflation!)
    private val _targetScale = MutableStateFlow(TargetScale.SCALE_1X)
    val targetScale: StateFlow<TargetScale> = _targetScale.asStateFlow()

    private val _faceClarityStrength = MutableStateFlow(0.85f) // 0.0f to 1.0f
    val faceClarityStrength: StateFlow<Float> = _faceClarityStrength.asStateFlow()

    private val _sharpeningStrength = MutableStateFlow(0.65f) // 0.0f to 1.0f
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

    fun setTargetScale(scale: TargetScale) {
        _targetScale.value = scale
    }

    fun setFaceClarityStrength(value: Float) {
        _faceClarityStrength.value = value.coerceIn(0f, 1f)
    }

    fun setPassProfile(profile: EnhancePassProfile) {
        _passProfile.value = profile
        when (profile) {
            EnhancePassProfile.FAST -> {
                _faceClarityStrength.value = 0.70f
                _sharpeningStrength.value = 0.40f
                _skinSmoothStrength.value = 0.70f
                _enablePreDenoise.value = false
                _enableColorBoost.value = false
            }
            EnhancePassProfile.BALANCED -> {
                _faceClarityStrength.value = 0.85f
                _sharpeningStrength.value = 0.60f
                _skinSmoothStrength.value = 0.82f
                _enablePreDenoise.value = true
                _enableColorBoost.value = false
            }
            EnhancePassProfile.ULTRA_STUDIO -> {
                _faceClarityStrength.value = 0.92f
                _sharpeningStrength.value = 0.75f
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

        // Auto-select smart scale:
        // For standard phone images (>= 1000px), default to 1x to avoid bloated file sizes!
        // For small images (< 1000px), default to 2x HD upscale.
        if (bitmap.width >= 1000 || bitmap.height >= 1000) {
            _targetScale.value = TargetScale.SCALE_1X
        } else {
            _targetScale.value = TargetScale.SCALE_2X
        }
    }

    fun startEnhancement() {
        val original = _originalImage.value ?: run {
            _uiState.value = EnhanceUiState.Error("Please select or capture an image first.")
            return
        }

        viewModelScope.launch {
            val profile = _passProfile.value
            val scale = _targetScale.value
            val doDenoise = _enablePreDenoise.value
            val doColorBoost = _enableColorBoost.value
            val sharpLevel = _sharpeningStrength.value
            val skinLevel = _skinSmoothStrength.value
            val clarityLevel = _faceClarityStrength.value

            // Calculate active total passes
            var totalPassesCount = 1 // Deblur & Rescaling is mandatory
            if (doDenoise) totalPassesCount++
            totalPassesCount++ // Face detection & restoration
            if (sharpLevel > 0.05f) totalPassesCount++
            if (doColorBoost) totalPassesCount++

            var currentPassNumber = 1

            _uiState.value = EnhanceUiState.Processing(
                progress = 0.02f,
                message = "Initializing Clarity Engine (${scale.title})...",
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
                            message = "Pass $currentPassNumber/$totalPassesCount: Pre-denoising & noise reduction...",
                            currentPass = currentPassNumber,
                            totalPasses = totalPassesCount
                        )
                        currentBitmap = ImageEnhancer.applyPreDenoiseFilter(currentBitmap)
                        currentPassNumber++
                    }

                    // PASS 2: Multi-Scale Frequency De-blurring & Scaling
                    _uiState.value = EnhanceUiState.Processing(
                        progress = 0.20f,
                        message = "Pass $currentPassNumber/$totalPassesCount: Multi-scale frequency deblurring (${scale.title})...",
                        currentPass = currentPassNumber,
                        totalPasses = totalPassesCount
                    )

                    var enhancedBg = ImageEnhancer.enhanceImage(
                        context = context,
                        inputBitmap = currentBitmap,
                        targetScale = scale.factor,
                        deblurStrength = clarityLevel
                    ) { progress ->
                        val baseProgress = 0.20f + (progress * 0.35f)
                        val pct = (progress * 100).toInt()
                        _uiState.value = EnhanceUiState.Processing(
                            progress = baseProgress,
                            message = "Pass $currentPassNumber/$totalPassesCount: Restoring lost blur gradients ($pct%)...",
                            currentPass = currentPassNumber,
                            totalPasses = totalPassesCount
                        )
                    }
                    currentPassNumber++

                    // PASS 3: ML Kit Portrait Landmark & Contour Face Detection
                    _uiState.value = EnhanceUiState.Processing(
                        progress = 0.60f,
                        message = "Pass $currentPassNumber/$totalPassesCount: Detecting facial landmarks & eye positions...",
                        currentPass = currentPassNumber,
                        totalPasses = totalPassesCount
                    )
                    val detectedFaces = FaceRestorer.detectFaces(original)
                    val facesCount = detectedFaces.size

                    // PASS 4: Anatomical Face Restoration (Eyes, Pupils, Brows, Lips & Skin)
                    _uiState.value = EnhanceUiState.Processing(
                        progress = 0.68f,
                        message = "Pass $currentPassNumber/$totalPassesCount: Deep facial clarity & eye iris restoration...",
                        currentPass = currentPassNumber,
                        totalPasses = totalPassesCount
                    )
                    val faceRestoredResult = FaceRestorer.restoreFacesAndStitch(
                        context = context,
                        originalBitmap = original,
                        enhancedBackground = enhancedBg,
                        faces = detectedFaces,
                        faceBlendAlpha = skinLevel,
                        clarityStrength = clarityLevel
                    ) { progress ->
                        val baseProg = 0.68f + (progress * 0.15f)
                        _uiState.value = EnhanceUiState.Processing(
                            progress = baseProg,
                            message = "Pass $currentPassNumber/$totalPassesCount: Restoring facial contours (${(progress * 100).toInt()}%)...",
                            currentPass = currentPassNumber,
                            totalPasses = totalPassesCount
                        )
                    }
                    currentPassNumber++

                    // PASS 5: Micro-Detail Edge Crispness
                    var postSharpBitmap = faceRestoredResult
                    if (sharpLevel > 0.05f) {
                        _uiState.value = EnhanceUiState.Processing(
                            progress = 0.86f,
                            message = "Pass $currentPassNumber/$totalPassesCount: Micro-detail edge recovery (${(sharpLevel * 100).toInt()}%)...",
                            currentPass = currentPassNumber,
                            totalPasses = totalPassesCount
                        )
                        postSharpBitmap = ImageEnhancer.applyUnsharpMask(faceRestoredResult, sharpLevel)
                        currentPassNumber++
                    }

                    // PASS 6: Studio Dynamic Contrast & De-Haze
                    val finalResult = if (doColorBoost) {
                        _uiState.value = EnhanceUiState.Processing(
                            progress = 0.94f,
                            message = "Pass $currentPassNumber/$totalPassesCount: Studio lighting, contrast de-haze & vibrance...",
                            currentPass = currentPassNumber,
                            totalPasses = totalPassesCount
                        )
                        ImageEnhancer.applyColorAndVibranceBoost(postSharpBitmap)
                    } else {
                        postSharpBitmap
                    }

                    _uiState.value = EnhanceUiState.Processing(1.0f, "Finalizing crystal clear output...", totalPassesCount, totalPassesCount)
                    _enhancedImage.value = finalResult
                    _uiState.value = EnhanceUiState.Success(
                        original = original,
                        enhanced = finalResult,
                        facesCount = facesCount,
                        isModelMode = ImageEnhancer.isModelLoaded,
                        profileName = profile.displayName,
                        passesApplied = totalPassesCount,
                        scaleFactor = scale.factor
                    )
                }

            } catch (oom: OutOfMemoryError) {
                Log.e(TAG, "Out of Memory during image enhancement process: ${oom.message}")
                System.gc()
                _uiState.value = EnhanceUiState.Error(
                    "Out of Memory Error!\nThe image is too large for your device's RAM budget. Please switch to '1x Native Size' or select a smaller photo."
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

