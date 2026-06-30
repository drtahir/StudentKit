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

sealed class EnhanceUiState {
    object Idle : EnhanceUiState()
    data class Processing(val progress: Float, val message: String) : EnhanceUiState()
    data class Success(val original: Bitmap, val enhanced: Bitmap, val facesCount: Int, val isModelMode: Boolean) : EnhanceUiState()
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

    init {
        checkModelsStatus()
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
            _uiState.value = EnhanceUiState.Processing(0.01f, "Initializing AI models...")

            try {
                val context = getApplication<Application>().applicationContext

                withContext(Dispatchers.Default) {
                    // Step 1: Detect faces using ML Kit Face Detection (completely offline)
                    _uiState.value = EnhanceUiState.Processing(0.1f, "Detecting portrait faces...")
                    val detectedFaces = FaceRestorer.detectFaces(original)
                    val facesCount = detectedFaces.size
                    Log.d(TAG, "Faces detected: $facesCount")

                    // Step 2: Super-resolution general background upscaling via Real-ESRGAN
                    _uiState.value = EnhanceUiState.Processing(0.2f, "Upscaling image details...")
                    
                    val enhancedBg = ImageEnhancer.enhanceImage(context, original) { progress ->
                        // Map 0.0f - 1.0f to 0.2f - 0.7f progress range
                        val mappedProgress = 0.2f + (progress * 0.5f)
                        val pct = (progress * 100).toInt()
                        _uiState.value = EnhanceUiState.Processing(
                            mappedProgress, 
                            "Upscaling detail blocks ($pct%)..."
                        )
                    }

                    // Step 3: Facial structure restoration via GFPGAN (or skin refine fallback)
                    val finalResult = if (facesCount > 0) {
                        _uiState.value = EnhanceUiState.Processing(0.75f, "Restoring facial contours...")
                        FaceRestorer.restoreFacesAndStitch(context, original, enhancedBg, detectedFaces) { progress ->
                            // Map 0.0f - 1.0f to 0.75f - 0.95f progress range
                            val mappedProgress = 0.75f + (progress * 0.2f)
                            val index = (progress * facesCount).toInt().coerceIn(1, facesCount)
                            _uiState.value = EnhanceUiState.Processing(
                                mappedProgress, 
                                "Stitching face portrait $index of $facesCount..."
                            )
                        }
                    } else {
                        enhancedBg
                    }

                    _uiState.value = EnhanceUiState.Processing(1.0f, "Polishing output...")
                    _enhancedImage.value = finalResult
                    _uiState.value = EnhanceUiState.Success(
                        original = original,
                        enhanced = finalResult,
                        facesCount = facesCount,
                        isModelMode = ImageEnhancer.isModelLoaded
                    )
                }

            } catch (oom: OutOfMemoryError) {
                Log.e(TAG, "Out of Memory during image enhancement process: ${oom.message}")
                System.gc() // Advise system to run GC
                _uiState.value = EnhanceUiState.Error(
                    "Out of Memory Error!\nThe image is too large for your device's RAM budget. Please crop the image or use a smaller resolution."
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
