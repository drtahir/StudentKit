package com.drtahir.studentkit.ui.screens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
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
import kotlin.math.max
import com.drtahir.studentkit.viewmodel.EnhanceUiState
import com.drtahir.studentkit.viewmodel.EnhanceViewModel
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
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

    val passProfile by viewModel.passProfile.collectAsState()
    val sharpeningStrength by viewModel.sharpeningStrength.collectAsState()
    val skinSmoothStrength by viewModel.skinSmoothStrength.collectAsState()
    val enablePreDenoise by viewModel.enablePreDenoise.collectAsState()
    val enableColorBoost by viewModel.enableColorBoost.collectAsState()

    var showAdvancedTuning by remember { mutableStateOf(false) }

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

    fun loadSampleImage(resourceId: Int) {
        val loaded = android.graphics.BitmapFactory.decodeResource(context.resources, resourceId)
        if (loaded != null) {
            viewModel.setOriginalImage(loaded)
        } else {
            Toast.makeText(context, "Failed to load sample image.", Toast.LENGTH_SHORT).show()
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
        // Header Banner
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFE0F7FA),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color(0xFF00ACC1),
                    modifier = Modifier.size(24.dp)
                )
                Column {
                    Text(
                        text = "Powerful Offline AI Image Enhancer",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF006064)
                    )
                    Text(
                        text = "100% On-Device • Multi-Pass Neural Super-Resolution",
                        fontSize = 11.sp,
                        color = Color(0xFF00838F)
                    )
                }
            }
        }

        // Image Selection & Preview Section
        if (originalImage == null) {
            // Empty State Card (Adaptive height, no clipping)
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.padding(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0F7FA)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFF00ACC1),
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Super-Resolution AI Portrait Enhancer",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Select any low-res, old, or blurry photo to upscale to 4x Ultra HD, smooth skin, and restore sharp facial features offline.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    // Main Action Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { galleryLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00ACC1)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("pick_gallery_button")
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Choose Photo from Gallery", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                if (cameraPermissionState.status.isGranted) {
                                    try {
                                        cameraLauncher.launch(null)
                                    } catch (e: SecurityException) {
                                        cameraPermissionState.launchPermissionRequest()
                                        Toast.makeText(context, "Camera permission required", Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Unable to launch camera: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    cameraPermissionState.launchPermissionRequest()
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("pick_camera_button")
                        ) {
                            Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF00ACC1))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Take Photo with Camera", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    // Sample Images Section
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                            Text("Or test sample photos", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = { loadSampleImage(com.drtahir.studentkit.R.drawable.sample_portrait) },
                                modifier = Modifier.weight(1f).height(40.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Portrait", fontSize = 12.sp)
                            }
                            OutlinedButton(
                                onClick = { loadSampleImage(com.drtahir.studentkit.R.drawable.sample_object) },
                                modifier = Modifier.weight(1f).height(40.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Landscape, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Object", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        } else {
            // Image Preview & Interaction Card (When image is loaded)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1E293B))
                    .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
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
                        Text("Original Low-Res (${original.width}×${original.height} px)", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
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
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MultiPassQualityControlPanel(
                            selectedProfile = passProfile,
                            sharpeningStrength = sharpeningStrength,
                            skinSmoothStrength = skinSmoothStrength,
                            enablePreDenoise = enablePreDenoise,
                            enableColorBoost = enableColorBoost,
                            showAdvancedTuning = showAdvancedTuning,
                            onProfileSelected = { viewModel.setPassProfile(it) },
                            onSharpeningChanged = { viewModel.setSharpeningStrength(it) },
                            onSkinSmoothChanged = { viewModel.setSkinSmoothStrength(it) },
                            onPreDenoiseToggled = { viewModel.setEnablePreDenoise(it) },
                            onColorBoostToggled = { viewModel.setEnableColorBoost(it) },
                            onToggleAdvanced = { showAdvancedTuning = !showAdvancedTuning }
                        )

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
                                Text("Start Multi-Pass")
                            }
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
                        verticalArrangement = Arrangement.spacedBy(12.dp)
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color(0xFF00ACC1).copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = "PASS ${state.currentPass}/${state.totalPasses}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            color = Color(0xFF00ACC1),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = "${(state.progress * 100).toInt()}% completed",
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = state.message,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                        LinearProgressIndicator(
                            progress = { state.progress },
                            color = Color(0xFF00ACC1),
                            trackColor = Color.LightGray.copy(alpha = 0.3f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
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
                                Text("Quality Profile", fontSize = 10.sp, color = Color.Gray)
                                Text(state.profileName.split(" ")[0], fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00ACC1))
                            }
                            Box(modifier = Modifier.height(24.dp).width(1.dp).background(Color.LightGray))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Passes Run", fontSize = 10.sp, color = Color.Gray)
                                Text("${state.passesApplied} Passes", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                            Box(modifier = Modifier.height(24.dp).width(1.dp).background(Color.LightGray))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Faces Restored", fontSize = 10.sp, color = Color.Gray)
                                Text("${state.facesCount} Detected", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                            Box(modifier = Modifier.height(24.dp).width(1.dp).background(Color.LightGray))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Engine", fontSize = 10.sp, color = Color.Gray)
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

@Composable
fun MultiPassQualityControlPanel(
    selectedProfile: com.drtahir.studentkit.viewmodel.EnhancePassProfile,
    sharpeningStrength: Float,
    skinSmoothStrength: Float,
    enablePreDenoise: Boolean,
    enableColorBoost: Boolean,
    showAdvancedTuning: Boolean,
    onProfileSelected: (com.drtahir.studentkit.viewmodel.EnhancePassProfile) -> Unit,
    onSharpeningChanged: (Float) -> Unit,
    onSkinSmoothChanged: (Float) -> Unit,
    onPreDenoiseToggled: (Boolean) -> Unit,
    onColorBoostToggled: (Boolean) -> Unit,
    onToggleAdvanced: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Tune, contentDescription = null, tint = Color(0xFF00ACC1), modifier = Modifier.size(20.dp))
                    Text(
                        text = "Multi-Pass Quality Pipeline",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                TextButton(
                    onClick = onToggleAdvanced,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = if (showAdvancedTuning) "Hide Fine-Tuning" else "Fine-Tune",
                        fontSize = 12.sp,
                        color = Color(0xFF00ACC1),
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = if (showAdvancedTuning) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = Color(0xFF00ACC1),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Preset Profile Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                com.drtahir.studentkit.viewmodel.EnhancePassProfile.values().forEach { profile ->
                    val isSelected = profile == selectedProfile
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Color(0xFF00ACC1) else Color(0xFFF0F4F8),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onProfileSelected(profile) }
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = profile.displayName.split(" ")[0], // "Fast", "Balanced", "Ultra"
                                color = if (isSelected) Color.White else Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "Passes: ${profile.totalPasses}",
                                color = if (isSelected) Color.White.copy(alpha = 0.85f) else Color.Gray,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = showAdvancedTuning) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.4f))

                    // 1. Edge Sharpening Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Micro-Detail Edge Sharpening", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Text("${(sharpeningStrength * 100).toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00ACC1))
                        }
                        Slider(
                            value = sharpeningStrength,
                            onValueChange = onSharpeningChanged,
                            valueRange = 0f..1f,
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF00ACC1),
                                activeTrackColor = Color(0xFF00ACC1)
                            )
                        )
                    }

                    // 2. Skin Smooth & Face Restoration Blend Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Portrait Skin Restore & Feather Blend", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Text("${(skinSmoothStrength * 100).toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00ACC1))
                        }
                        Slider(
                            value = skinSmoothStrength,
                            onValueChange = onSkinSmoothChanged,
                            valueRange = 0f..1f,
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF00ACC1),
                                activeTrackColor = Color(0xFF00ACC1)
                            )
                        )
                    }

                    // 3. Pre-Denoise Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Pre-Processing Denoise Filter", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Text("Suppresses compression noise before upscaling", fontSize = 10.sp, color = Color.Gray)
                        }
                        Switch(
                            checked = enablePreDenoise,
                            onCheckedChange = onPreDenoiseToggled,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF00ACC1)
                            )
                        )
                    }

                    // 4. Color & Vibrance Boost Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Studio Dynamic Contrast & Vibrance", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Text("Enhances dynamic range and vivid colors", fontSize = 10.sp, color = Color.Gray)
                        }
                        Switch(
                            checked = enableColorBoost,
                            onCheckedChange = onColorBoostToggled,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF00ACC1)
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun loadUriAsBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        // 1. Measure dimensions without full decode to avoid OOM
        val boundsOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        context.contentResolver.openInputStream(uri)?.use { stream ->
            BitmapFactory.decodeStream(stream, null, boundsOptions)
        }

        if (boundsOptions.outWidth <= 0 || boundsOptions.outHeight <= 0) {
            return null
        }

        // 2. Safe downsampling for neural models (Max dimension 2048px)
        val maxDimension = 2048
        var sampleSize = 1
        val maxSrc = max(boundsOptions.outWidth, boundsOptions.outHeight)
        while (maxSrc / sampleSize > maxDimension) {
            sampleSize *= 2
        }

        val decodeOptions = BitmapFactory.Options().apply {
            inSampleSize = sampleSize
            inPreferredConfig = Bitmap.Config.ARGB_8888
        }

        var bitmap = context.contentResolver.openInputStream(uri)?.use { stream ->
            BitmapFactory.decodeStream(stream, null, decodeOptions)
        } ?: return null

        // 3. Handle EXIF Rotation (for photos taken in portrait/landscape)
        try {
            context.contentResolver.openInputStream(uri)?.use { exifStream ->
                val exif = ExifInterface(exifStream)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                val matrix = Matrix()
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                    ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                    ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1f, 1f)
                    ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
                }
                if (!matrix.isIdentity) {
                    val rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                    if (rotated != bitmap) {
                        bitmap.recycle()
                        bitmap = rotated
                    }
                }
            }
        } catch (ignored: Exception) {
            // Keep decoded bitmap if EXIF reading is not supported for URI
        }

        bitmap
    } catch (e: Exception) {
        null
    }
}
