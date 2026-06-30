package com.example.ui.screens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.EnhanceUiState
import com.example.viewmodel.EnhanceViewModel
import com.example.viewmodel.StudentKitViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.InputStream

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImageEnhancerScreen(
    viewModel: EnhanceViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val originalImage by viewModel.originalImage.collectAsState()
    val enhancedImage by viewModel.enhancedImage.collectAsState()
    val isModelLoaded by viewModel.modelLoaded.collectAsState()

    var showDownloadGuide by remember { mutableStateOf(false) }

    // Permission states
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    // Activity launchers for image picking
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = loadUriAsBitmap(context, it)
            if (bitmap != null) {
                viewModel.setOriginalImage(bitmap)
            } else {
                Toast.makeText(context, "Failed to load selected image.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            viewModel.setOriginalImage(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkModelsStatus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // AI Model Engine Status Badge
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isModelLoaded) Color(0xFFE8F5E9) else Color(0xFFFFF3E0),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (isModelLoaded) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (isModelLoaded) Color(0xFF2E7D32) else Color(0xFFE65100),
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = if (isModelLoaded) "On-Device Neural Engine Active" else "Enhancement Running in Fallback",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (isModelLoaded) Color(0xFF1B5E20) else Color(0xFF5D4037)
                        )
                        Text(
                            text = if (isModelLoaded) "Hardware Accelerated TFLite (Real-ESRGAN + GFPGAN)" else "Local pixel-sharpening refinement fallback active",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }
                
                IconButton(
                    onClick = { showDownloadGuide = true },
                    modifier = Modifier.testTag("download_guide_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = "Show TFLite download guide",
                        tint = Color.Gray
                    )
                }
            }
        }

        // Image Preview & Interaction Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF5F5F7))
                .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (originalImage == null) {
                // Empty State Frame
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color(0xFF00ACC1),
                        modifier = Modifier.size(56.dp)
                    )
                    Text(
                        text = "Super-Resolution AI Portrait Enhancer",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Select any low-res or blurry photo to upscale to 4x Ultra HD, smooth skin, and restore sharp facial features offline.",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { galleryLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00ACC1)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("pick_gallery_button")
                        ) {
                            Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Gallery", fontSize = 12.sp)
                        }
                        Button(
                            onClick = {
                                if (cameraPermissionState.status.isGranted) {
                                    cameraLauncher.launch()
                                } else {
                                    cameraPermissionState.launchPermissionRequest()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF37474F)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("pick_camera_button")
                        ) {
                            Icon(Icons.Default.PhotoCamera, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Camera", fontSize = 12.sp)
                        }
                    }
                }
            } else {
                // Image Display Container
                val original = originalImage!!
                val enhanced = enhancedImage

                if (enhanced == null) {
                    // Only original selected, not enhanced yet
                    Image(
                        bitmap = original.asImageBitmap(),
                        contentDescription = "Original low-res image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                    
                    // Small "Original" badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("Original Low-Res", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    // BEFORE/AFTER SEAMLESS SLIDER UI
                    BeforeAfterSlider(
                        original = original,
                        enhanced = enhanced,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // Active State Renderings
        when (val state = uiState) {
            is EnhanceUiState.Idle -> {
                if (originalImage != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewModel.setOriginalImage(originalImage!!) // reset
                                galleryLauncher.launch("image/*")
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Refresh, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Change Photo")
                        }

                        Button(
                            onClick = { viewModel.startEnhancement() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00ACC1)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1.5f)
                                .testTag("enhance_start_button")
                        ) {
                            Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Enhance HD (4x)")
                        }
                    }
                }
            }

            is EnhanceUiState.Processing -> {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                progress = { state.progress },
                                strokeWidth = 5.dp,
                                color = Color(0xFF00ACC1),
                                modifier = Modifier.size(48.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = state.message,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "${(state.progress * 100).toInt()}% completed on-device",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        LinearProgressIndicator(
                            progress = { state.progress },
                            color = Color(0xFF00ACC1),
                            trackColor = Color.LightGray.copy(alpha = 0.3f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }

            is EnhanceUiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Detail Summary Box
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8F9)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Upscale Scale", fontSize = 10.sp, color = Color.Gray)
                                Text("4x Ultra HD", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00ACC1))
                            }
                            Box(modifier = Modifier.height(24.dp).width(1.dp).background(Color.LightGray))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Portrait Faces", fontSize = 10.sp, color = Color.Gray)
                                Text("${state.facesCount} Detected", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                            Box(modifier = Modifier.height(24.dp).width(1.dp).background(Color.LightGray))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Restore Mode", fontSize = 10.sp, color = Color.Gray)
                                Text(if (state.isModelMode) "TFLite" else "Pixel Refine", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Bottom Actions
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewModel.setOriginalImage(originalImage!!) // reset
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Refresh, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Enhance Another")
                        }

                        Button(
                            onClick = {
                                viewModel.saveToGallery(context) { success, message ->
                                    Toast.makeText(context, message ?: "Save output processed.", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00ACC1)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1.5f)
                                .testTag("save_gallery_button")
                        ) {
                            Icon(Icons.Default.Download, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Save to Gallery")
                        }
                    }
                }
            }

            is EnhanceUiState.Error -> {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Error, null, tint = Color.Red, modifier = Modifier.size(32.dp))
                        Text(
                            text = state.message,
                            color = Color(0xFFC62828),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewModel.startEnhancement() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Retry Enhancement", fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // TFLite Download Guide Card (Always available)
        ElevatedCard(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable { showDownloadGuide = !showDownloadGuide }
                ) {
                    Icon(Icons.Default.Info, null, tint = Color(0xFF00ACC1), modifier = Modifier.size(20.dp))
                    Text(
                        text = "How to load offline AI .tflite models?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (showDownloadGuide) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }

                AnimatedVisibility(visible = showDownloadGuide) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray.copy(alpha = 0.5f)))
                        Text(
                            text = "To unlock native hardware neural networks directly in the app, follow these steps:",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                        
                        StepRow("1", "Download Real-ESRGAN x4 .tflite (Super-Resolution background model).")
                        StepRow("2", "Download GFPGAN-lite .tflite (Facial portrait restoration model).")
                        StepRow("3", "Rename the files exactly to:\n- real_esrgan_x4.tflite\n- gfpgan_lite.tflite")
                        StepRow("4", "Place them inside the project's assets folder:\napp/src/main/assets/\n(Create the assets directory if it doesn't exist).")
                        StepRow("5", "Rebuild the application. The engine status badge will automatically turn Green and use full GPU acceleration!")

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Note: If no models are found, the app uses its custom high-performance digital pixel upscaling fallback so it is fully functional out-of-the-box!",
                            fontSize = 11.sp,
                            color = Color(0xFF00ACC1),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StepRow(num: String, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color(0xFF00ACC1), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(num, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Text(text, fontSize = 11.sp, color = Color.DarkGray)
    }
}

@Composable
fun BeforeAfterSlider(
    original: Bitmap,
    enhanced: Bitmap,
    modifier: Modifier = Modifier
) {
    var sliderX by remember { mutableStateOf(0.5f) } // Slider position (0f to 1f)
    var viewSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .onSizeChanged { viewSize = it }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val dragX = change.position.x
                    if (viewSize.width > 0) {
                        sliderX = (dragX / viewSize.width).coerceIn(0f, 1f)
                    }
                }
            }
            .clipToBounds()
    ) {
        val originalBitmap = original.asImageBitmap()
        val enhancedBitmap = enhanced.asImageBitmap()

        // 1. Enhanced Image (Drawn fully in background)
        Image(
            bitmap = enhancedBitmap,
            contentDescription = "Enhanced image view",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Original Image (Drawn on top, clipped horizontally matching slider position)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(sliderX)
                .clipToBounds()
        ) {
            Image(
                bitmap = originalBitmap,
                contentDescription = "Original image view",
                contentScale = ContentScale.Crop, // To maintain strict sync overlay, Crop coordinates match perfectly
                modifier = Modifier.fillMaxHeight().width(
                    if (viewSize.width > 0) (viewSize.width / LocalContext.current.resources.displayMetrics.density).dp else 300.dp
                ),
                alignment = Alignment.CenterStart
            )
        }

        // 3. Slider Handle Line & Partition Separator
        val currentPositionX = if (viewSize.width > 0) sliderX * viewSize.width else 150f
        val currentPositionDp = (currentPositionX / LocalContext.current.resources.displayMetrics.density).dp

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .offset(x = currentPositionDp)
                .background(Color.White)
                .align(Alignment.CenterStart)
        )

        // Slider Thumb / Pull Handle
        Box(
            modifier = Modifier
                .size(36.dp)
                .offset(x = currentPositionDp - 18.dp)
                .shadow(4.dp, CircleShape)
                .background(Color.White, CircleShape)
                .border(2.dp, Color(0xFF00ACC1), CircleShape)
                .align(Alignment.CenterStart),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CompareArrows,
                contentDescription = "Drag to compare before and after",
                tint = Color(0xFF00ACC1),
                modifier = Modifier.size(22.dp)
            )
        }

        // "Before" & "After" indicator labels
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text("Before", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .background(Color(0xFF00ACC1).copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text("After HD", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
    }
}

private fun loadUriAsBitmap(context: Context, uri: Uri): Bitmap? {
    var inputStream: InputStream? = null
    return try {
        inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        null
    } finally {
        inputStream?.close()
    }
}
