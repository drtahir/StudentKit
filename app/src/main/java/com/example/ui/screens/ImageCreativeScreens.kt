package com.example.ui.screens

import android.content.Context
import android.graphics.*
import android.graphics.Color as AndroidColor
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import kotlin.math.roundToInt
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.PhotoFilter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.viewmodel.StudentKitViewModel
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.segmentation.Segmentation
import com.google.mlkit.vision.segmentation.selfie.SelfieSegmenterOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import android.content.Intent
import androidx.core.content.FileProvider

// Filters models with custom ColorMatrix matrices
data class ColorFilterPreset(
    val name: String,
    val description: String,
    val colorMatrix: FloatArray?
)

// Watermark Preset Text Styling model
data class WatermarkStylePreset(
    val name: String,
    val textColor: Int,
    val outlineColor: Int? = null,
    val shadowColor: Int? = null,
    val shadowRadius: Float = 0f,
    val isBold: Boolean = true,
    val isItalic: Boolean = false,
    val defaultFontFamily: String = "Sans-Serif",
    val bgPillColor: Int? = null
)

val watermarkPresetsList = listOf(
    WatermarkStylePreset("Classic White", AndroidColor.WHITE, shadowColor = AndroidColor.BLACK, shadowRadius = 8f),
    WatermarkStylePreset("Neon Cyber Glow", AndroidColor.parseColor("#00E5FF"), shadowColor = AndroidColor.parseColor("#00E5FF"), shadowRadius = 20f, defaultFontFamily = "Monospace"),
    WatermarkStylePreset("Gold Luxury", AndroidColor.parseColor("#FFD700"), shadowColor = AndroidColor.parseColor("#B8860B"), shadowRadius = 12f, defaultFontFamily = "Serif"),
    WatermarkStylePreset("Vintage Badge", AndroidColor.parseColor("#D2B48C"), outlineColor = AndroidColor.parseColor("#3E2723"), shadowColor = AndroidColor.BLACK, shadowRadius = 6f, defaultFontFamily = "Serif"),
    WatermarkStylePreset("Outline Crimson", AndroidColor.TRANSPARENT, outlineColor = AndroidColor.parseColor("#FF1744")),
    WatermarkStylePreset("Silver Metallic", AndroidColor.parseColor("#E0E0E0"), shadowColor = AndroidColor.parseColor("#212121"), shadowRadius = 10f),
    WatermarkStylePreset("Sunset Coral", AndroidColor.parseColor("#FF7F50"), shadowColor = AndroidColor.parseColor("#D50000"), shadowRadius = 14f),
    WatermarkStylePreset("Emerald Shield", AndroidColor.parseColor("#00E676"), shadowColor = AndroidColor.parseColor("#1B5E20"), shadowRadius = 12f),
    WatermarkStylePreset("Dark Noir", AndroidColor.parseColor("#121212"), outlineColor = AndroidColor.WHITE, shadowColor = AndroidColor.WHITE, shadowRadius = 4f),
    WatermarkStylePreset("Electric Purple", AndroidColor.parseColor("#E040FB"), shadowColor = AndroidColor.parseColor("#AA00FF"), shadowRadius = 18f, defaultFontFamily = "Monospace"),
    WatermarkStylePreset("Calligraphy Rose", AndroidColor.parseColor("#FF80AB"), shadowColor = AndroidColor.parseColor("#C2185B"), shadowRadius = 8f, isItalic = true, defaultFontFamily = "Cursive"),
    WatermarkStylePreset("Security Stamp", AndroidColor.parseColor("#B71C1C"), outlineColor = AndroidColor.parseColor("#D50000"), bgPillColor = AndroidColor.parseColor("#33000000"), defaultFontFamily = "Condensed"),
    WatermarkStylePreset("Glassmorphic Pill", AndroidColor.WHITE, shadowColor = AndroidColor.BLACK, shadowRadius = 6f, bgPillColor = AndroidColor.parseColor("#77000000")),
    WatermarkStylePreset("Solar Yellow", AndroidColor.parseColor("#FFEA00"), outlineColor = AndroidColor.BLACK, shadowColor = AndroidColor.BLACK, shadowRadius = 10f),
    WatermarkStylePreset("Toxic Lime", AndroidColor.parseColor("#76FF03"), shadowColor = AndroidColor.parseColor("#1B5E20"), shadowRadius = 16f, defaultFontFamily = "Monospace"),
    WatermarkStylePreset("Royal Azure", AndroidColor.parseColor("#2979FF"), shadowColor = AndroidColor.parseColor("#0D47A1"), shadowRadius = 12f, defaultFontFamily = "Serif"),
    WatermarkStylePreset("Glitch Cyan", AndroidColor.parseColor("#00FFFF"), shadowColor = AndroidColor.parseColor("#FF00FF"), shadowRadius = 14f, defaultFontFamily = "Monospace"),
    WatermarkStylePreset("Opal Lavender", AndroidColor.parseColor("#E1BEE7"), shadowColor = AndroidColor.parseColor("#4A148C"), shadowRadius = 10f, defaultFontFamily = "Serif"),
    WatermarkStylePreset("Copper Bronze", AndroidColor.parseColor("#CD7F32"), shadowColor = AndroidColor.parseColor("#3E2723"), shadowRadius = 8f, defaultFontFamily = "Serif"),
    WatermarkStylePreset("Minimal Slate", AndroidColor.parseColor("#94A3B8"), shadowColor = AndroidColor.BLACK, shadowRadius = 4f),
    WatermarkStylePreset("Hot Pink Flame", AndroidColor.parseColor("#FF4081"), shadowColor = AndroidColor.parseColor("#880E4F"), shadowRadius = 15f, isItalic = true, defaultFontFamily = "Cursive"),
    WatermarkStylePreset("Midnight Ice", AndroidColor.parseColor("#E0F7FA"), shadowColor = AndroidColor.parseColor("#006064"), shadowRadius = 12f),
    WatermarkStylePreset("Casual Stamp", AndroidColor.parseColor("#FF6F00"), outlineColor = AndroidColor.BLACK, defaultFontFamily = "Casual"),
    WatermarkStylePreset("Terminal Green", AndroidColor.parseColor("#00FF66"), shadowColor = AndroidColor.parseColor("#003300"), shadowRadius = 14f, defaultFontFamily = "Monospace")
)

val fontFamiliesList = listOf(
    "Sans-Serif",
    "Serif",
    "Monospace",
    "Condensed",
    "Cursive",
    "Casual",
    "Serif Bold",
    "Serif Italic",
    "Sans Bold Italic"
)

fun getFontTypeface(family: String): Typeface {
    return when (family) {
        "Serif" -> Typeface.SERIF
        "Monospace" -> Typeface.MONOSPACE
        "Condensed" -> Typeface.create("sans-serif-condensed", Typeface.BOLD)
        "Cursive" -> Typeface.create("cursive", Typeface.NORMAL)
        "Casual" -> Typeface.create("casual", Typeface.BOLD)
        "Serif Bold" -> Typeface.create(Typeface.SERIF, Typeface.BOLD)
        "Serif Italic" -> Typeface.create(Typeface.SERIF, Typeface.ITALIC)
        "Sans Bold Italic" -> Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC)
        else -> Typeface.SANS_SERIF
    }
}

val sepiaMatrix = floatArrayOf(
    0.393f, 0.769f, 0.189f, 0f, 0f,
    0.349f, 0.686f, 0.168f, 0f, 0f,
    0.272f, 0.534f, 0.131f, 0f, 0f,
    0f,     0f,     0f,     1f, 0f
)

val monochromeMatrix = floatArrayOf(
    0.33f, 0.33f, 0.33f, 0f, 0f,
    0.33f, 0.33f, 0.33f, 0f, 0f,
    0.33f, 0.33f, 0.33f, 0f, 0f,
    0f,    0f,    0f,    1f, 0f
)

val emeraldMatrix = floatArrayOf(
    0.5f,  0f,    0f,    0f, 0f,
    0.1f,  1.4f,  0.1f,  0f, 0f,
    0.1f,  0f,    0.5f,  0f, 0f,
    0f,    0f,    0f,    1f, 0f
)

val cyanMatrix = floatArrayOf(
    0.8f,  0f,    0f,    0f, 0f,
    0f,    1.1f,  0.2f,  0f, 0f,
    0f,    0.2f,  1.4f,  0f, 0f,
    0f,    0f,    0f,    1f, 0f
)

val amberMatrix = floatArrayOf(
    1.4f,  0f,    0f,    0f, 0f,
    0.2f,  1.0f,  0f,    0f, 0f,
    0f,    0f,    0.6f,  0f, 0f,
    0f,    0f,    0f,    1f, 0f
)

val presets = listOf(
    ColorFilterPreset("Original", "No filter applied", null),
    ColorFilterPreset("Vintage Sepia", "Warm nostalgic glow", sepiaMatrix),
    ColorFilterPreset("Noir Mono", "High-contrast monochrome", monochromeMatrix),
    ColorFilterPreset("Emerald Jade", "Lush greenish tones", emeraldMatrix),
    ColorFilterPreset("Cool Cyan", "Chilly polar matrix", cyanMatrix),
    ColorFilterPreset("Warm Amber", "Sunny golden saturation", amberMatrix)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatermarkStudioScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var baseBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Mode tab
    var currentSubTab by remember { mutableStateOf("Filters") } // "Filters", "Watermark"

    // Settings
    var selectedPreset by remember { mutableStateOf(presets[0]) }
    var selectedStylePreset by remember { mutableStateOf(watermarkPresetsList[0]) }
    var selectedFontFamily by remember { mutableStateOf(fontFamiliesList[0]) }
    var watermarkText by remember { mutableStateOf("CONFIDENTIAL") }
    var watermarkOpacity by remember { mutableStateOf(0.4f) }
    var watermarkSize by remember { mutableStateOf(45f) }
    var watermarkLayout by remember { mutableStateOf("Grid Tiled") } // "Single Center", "Grid Tiled"
    var watermarkRotation by remember { mutableStateOf(45f) }

    // Fullscreen Preview & Export Dialog states
    var isFullscreenPreviewOpen by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }
    var exportResolution by remember { mutableStateOf("Original Native") }
    var exportFormat by remember { mutableStateOf("PNG (Lossless)") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val raw = BitmapFactory.decodeStream(inputStream)
                if (raw != null) {
                    baseBitmap = raw.copy(Bitmap.Config.ARGB_8888, true)
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Live preview bitmap computation
    val previewBitmap = remember(
        baseBitmap,
        selectedPreset,
        selectedStylePreset,
        selectedFontFamily,
        watermarkText,
        watermarkOpacity,
        watermarkSize,
        watermarkLayout,
        watermarkRotation,
        currentSubTab
    ) {
        val base = baseBitmap ?: return@remember null
        applyEffectsToBitmap(
            base = base,
            preset = selectedPreset,
            text = if (currentSubTab == "Watermark") watermarkText else "",
            opacity = watermarkOpacity,
            size = watermarkSize,
            stylePreset = selectedStylePreset,
            fontFamily = selectedFontFamily,
            layout = watermarkLayout,
            rotation = watermarkRotation
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "WATERMARK & FILTERS STUDIO",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Image Workbench / Preview Panel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.2f)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.5.dp, Color.LightGray, RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E293B)),
                contentAlignment = Alignment.Center
            ) {
                val preview = previewBitmap
                if (preview != null) {
                    androidx.compose.foundation.Image(
                        bitmap = preview.asImageBitmap(),
                        contentDescription = "Studio Workbench Live Preview",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )

                    // Floating Fullscreen Icon Button (Top Right)
                    IconButton(
                        onClick = { isFullscreenPreviewOpen = true },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.65f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fullscreen,
                            contentDescription = "Preview Fullscreen",
                            tint = Color.White
                        )
                    }

                    // Floating Pill (Bottom Start)
                    Surface(
                        onClick = { isFullscreenPreviewOpen = true },
                        color = Color.Black.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(Icons.Default.OpenInFull, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Full Screen Preview", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { imagePickerLauncher.launch("image/*") }
                    ) {
                        Icon(
                            Icons.Default.AddPhotoAlternate,
                            contentDescription = "Add image",
                            tint = Color.LightGray.copy(alpha = 0.5f),
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap to load a photo from Gallery",
                            color = Color.LightGray.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                        Text(
                            text = "JPG, PNG, or WEBP supported",
                            color = Color.LightGray.copy(alpha = 0.4f),
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sub Tab Selection
            TabRow(
                selectedTabIndex = if (currentSubTab == "Filters") 0 else 1,
                containerColor = Color.White,
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            ) {
                Tab(
                    selected = currentSubTab == "Filters",
                    onClick = { currentSubTab = "Filters" },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.PhotoFilter, contentDescription = "Filters", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Color Filters", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                )
                Tab(
                    selected = currentSubTab == "Watermark",
                    onClick = { currentSubTab = "Watermark" },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.ColorLens, contentDescription = "Watermarks", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Text Presets & Styling", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Controls viewport
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (currentSubTab == "Filters") {
                    // FILTERS PANEL
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Select Cinematic Color Filter",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(presets) { preset ->
                                val isSelected = selectedPreset == preset
                                Card(
                                    modifier = Modifier
                                        .size(110.dp, 80.dp)
                                        .clickable { selectedPreset = preset },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.White
                                    ),
                                    border = BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(6.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = preset.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = preset.description,
                                            fontSize = 8.sp,
                                            color = Color.Gray,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 10.sp
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Matrix Processing Info",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.DarkGray
                                )
                                Text(
                                    text = "Our matrix processor compiles channel colors directly inside native GPU registers, producing ultra-sharp output instantly.",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                } else {
                    // WATERMARK PANEL
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        OutlinedTextField(
                            value = watermarkText,
                            onValueChange = { watermarkText = it },
                            label = { Text("Watermark text label") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Text Styling Presets Selection (Dozens of Presets)
                        Text(
                            text = "Preset Text Styling (20+ Dozens)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(watermarkPresetsList) { preset ->
                                val isSelected = selectedStylePreset == preset
                                Card(
                                    modifier = Modifier
                                        .width(130.dp)
                                        .clickable {
                                            selectedStylePreset = preset
                                            if (preset.defaultFontFamily.isNotEmpty()) {
                                                selectedFontFamily = preset.defaultFontFamily
                                            }
                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.White
                                    ),
                                    border = BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(30.dp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFF1E293B)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "STYLE",
                                                fontSize = 10.sp,
                                                fontWeight = if (preset.isBold) FontWeight.Bold else FontWeight.Normal,
                                                color = Color(preset.textColor)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = preset.name,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Font Selection
                        Text(
                            text = "Font Typeface Selection",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(fontFamiliesList) { fontName ->
                                val isSelected = selectedFontFamily == fontName
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedFontFamily = fontName },
                                    label = { Text(fontName, fontSize = 10.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Opacity & Size Sliders
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f).padding(end = 6.dp)) {
                                Text(
                                    text = "Opacity: ${(watermarkOpacity * 100).roundToInt()}%",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Slider(
                                    value = watermarkOpacity,
                                    onValueChange = { watermarkOpacity = it },
                                    valueRange = 0.1f..1.0f
                                )
                            }

                            Column(modifier = Modifier.weight(1f).padding(start = 6.dp)) {
                                Text(
                                    text = "Text Size: ${watermarkSize.roundToInt()}sp",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Slider(
                                    value = watermarkSize,
                                    onValueChange = { watermarkSize = it },
                                    valueRange = 20f..80f
                                )
                            }
                        }

                        // Rotation Angle Slider & Layout choices
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f).padding(end = 6.dp)) {
                                Text(
                                    text = "Rotation: ${watermarkRotation.roundToInt()}°",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Slider(
                                    value = watermarkRotation,
                                    onValueChange = { watermarkRotation = it },
                                    valueRange = 0f..90f
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                listOf("Single Center", "Grid Tiled").forEach { mode ->
                                    val isSelected = watermarkLayout == mode
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (isSelected) Color.DarkGray else Color.LightGray.copy(alpha = 0.3f))
                                            .clickable { watermarkLayout = mode }
                                            .padding(horizontal = 8.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = mode,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Action Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                    border = BorderStroke(1.dp, Color.LightGray),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.CloudUpload, contentDescription = "Load")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Change Photo", fontSize = 11.sp)
                }

                Button(
                    onClick = {
                        if (previewBitmap == null) {
                            Toast.makeText(context, "Please load a photo first", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        showExportDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1.3f)
                ) {
                    Icon(Icons.Default.Download, contentDescription = "Export")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("High Quality Save", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    // Full Screen Preview Modal
    if (isFullscreenPreviewOpen && previewBitmap != null) {
        Dialog(
            onDismissRequest = { isFullscreenPreviewOpen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            var scale by remember { mutableFloatStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }
            val transformableState = rememberTransformableState { zoomChange, offsetChange, _ ->
                scale = (scale * zoomChange).coerceIn(1f, 5f)
                offset += offsetChange
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                // Image with gesture zoom & pan
                androidx.compose.foundation.Image(
                    bitmap = previewBitmap.asImageBitmap(),
                    contentDescription = "Full Screen Picture Preview",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .transformable(state = transformableState),
                    contentScale = ContentScale.Fit
                )

                // Top Toolbar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "FULL SCREEN PREVIEW",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "${previewBitmap.width} × ${previewBitmap.height} px • Pinch to Zoom",
                            color = Color.LightGray,
                            fontSize = 10.sp
                        )
                    }

                    IconButton(
                        onClick = { isFullscreenPreviewOpen = false },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.DarkGray.copy(alpha = 0.7f))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                // Bottom Action Bar
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            scale = 1f
                            offset = Offset.Zero
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                    ) {
                        Icon(Icons.Default.ZoomOutMap, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reset Zoom", fontSize = 11.sp)
                    }

                    Button(
                        onClick = {
                            isFullscreenPreviewOpen = false
                            showExportDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save HD Image", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // High Quality Graphics Export Options Dialog
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.HighQuality, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save High Quality Graphics", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text(
                        text = "Select output resolution & format for device gallery memory:",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Resolution Choices
                    Text("Resolution Quality:", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    val resolutions = listOf("Original Native", "4K Ultra HD (3840p)", "1080p Full HD (1920p)", "720p HD (1280p)")
                    resolutions.forEach { res ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { exportResolution = res }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = exportResolution == res,
                                onClick = { exportResolution = res }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(res, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Format Choices
                    Text("Graphics Format & Quality:", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    val formats = listOf("PNG (Lossless)", "JPEG Ultra (100%)", "JPEG High (90%)", "WEBP Lossless")
                    formats.forEach { fmt ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { exportFormat = fmt }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = exportFormat == fmt,
                                onClick = { exportFormat = fmt }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(fmt, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Image will be rendered at native hardware graphics resolution and saved directly into phone memory (Pictures/StudioExports).",
                            fontSize = 10.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val base = baseBitmap
                        if (base == null) {
                            Toast.makeText(context, "No photo loaded", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val maxDim = when (exportResolution) {
                            "4K Ultra HD (3840p)" -> 3840
                            "1080p Full HD (1920p)" -> 1920
                            "720p HD (1280p)" -> 1280
                            else -> null
                        }

                        val fmtName = when {
                            exportFormat.contains("PNG") -> "PNG"
                            exportFormat.contains("WEBP") -> "WEBP"
                            else -> "JPEG"
                        }

                        val qual = when {
                            exportFormat.contains("100%") -> 100
                            exportFormat.contains("90%") -> 90
                            else -> 100
                        }

                        val highResBmp = applyEffectsToBitmap(
                            base = base,
                            preset = selectedPreset,
                            text = if (currentSubTab == "Watermark") watermarkText else "",
                            opacity = watermarkOpacity,
                            size = watermarkSize,
                            stylePreset = selectedStylePreset,
                            fontFamily = selectedFontFamily,
                            layout = watermarkLayout,
                            rotation = watermarkRotation,
                            targetMaxDimension = maxDim
                        )

                        val savedFile = saveBitmapToGalleryHelper(context, highResBmp, fmtName, qual)
                        showExportDialog = false

                        if (savedFile != null) {
                            Toast.makeText(
                                context,
                                "Exported successfully to Phone Memory Gallery!\n${savedFile.name}",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(context, "Save failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Export to Phone Memory")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Draw effects live using CPU rendering canvas
fun applyEffectsToBitmap(
    base: Bitmap,
    preset: ColorFilterPreset,
    text: String,
    opacity: Float,
    size: Float,
    stylePreset: WatermarkStylePreset = watermarkPresetsList[0],
    fontFamily: String = "Sans-Serif",
    layout: String = "Grid Tiled",
    rotation: Float = 45f,
    targetMaxDimension: Int? = null
): Bitmap {
    val scaledBase = if (targetMaxDimension != null && (base.width > targetMaxDimension || base.height > targetMaxDimension)) {
        val aspect = base.width.toFloat() / base.height.toFloat()
        val (w, h) = if (aspect > 1f) {
            targetMaxDimension to (targetMaxDimension / aspect).roundToInt()
        } else {
            (targetMaxDimension * aspect).roundToInt() to targetMaxDimension
        }
        Bitmap.createScaledBitmap(base, w, h, true)
    } else {
        base
    }

    val out = scaledBase.copy(Bitmap.Config.ARGB_8888, true)
    val canvas = Canvas(out)

    // 1. Apply Matrix Color Filter if present
    preset.colorMatrix?.let { matrix ->
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(matrix)
        }
        canvas.drawBitmap(out, 0f, 0f, paint)
    }

    // 2. Draw Watermark if present
    if (text.isNotEmpty()) {
        val scaledTextSize = size * (out.width / 800f)
        val tf = getFontTypeface(fontFamily)

        // Text Paint
        val paint = Paint().apply {
            isAntiAlias = true
            textSize = scaledTextSize
            typeface = tf
            color = stylePreset.textColor
            alpha = (opacity * 255).toInt()
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            isFakeBoldText = stylePreset.isBold
            if (stylePreset.isItalic) textSkewX = -0.25f

            if (stylePreset.shadowColor != null && stylePreset.shadowRadius > 0f) {
                setShadowLayer(
                    stylePreset.shadowRadius * (out.width / 800f),
                    3f * (out.width / 800f),
                    3f * (out.width / 800f),
                    stylePreset.shadowColor
                )
            }
        }

        // Outline Paint
        val strokePaint = if (stylePreset.outlineColor != null) {
            Paint().apply {
                isAntiAlias = true
                textSize = scaledTextSize
                typeface = tf
                color = stylePreset.outlineColor
                alpha = (opacity * 255).toInt()
                style = Paint.Style.STROKE
                strokeWidth = 6f * (out.width / 800f)
                textAlign = Paint.Align.CENTER
                isFakeBoldText = stylePreset.isBold
                if (stylePreset.isItalic) textSkewX = -0.25f
            }
        } else null

        // Background Pill Paint
        val pillPaint = if (stylePreset.bgPillColor != null) {
            Paint().apply {
                isAntiAlias = true
                color = stylePreset.bgPillColor
                alpha = (opacity * 200).toInt()
                style = Paint.Style.FILL
            }
        } else null

        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)

        fun drawTextWithEffects(cx: Float, cy: Float) {
            if (pillPaint != null) {
                val paddingX = bounds.width() * 0.25f + 20f
                val paddingY = bounds.height() * 0.4f + 10f
                val pillRect = RectF(
                    cx - bounds.width() / 2f - paddingX,
                    cy - bounds.height() - paddingY,
                    cx + bounds.width() / 2f + paddingX,
                    cy + paddingY
                )
                canvas.drawRoundRect(pillRect, 16f, 16f, pillPaint)
            }
            strokePaint?.let { canvas.drawText(text, cx, cy, it) }
            if (stylePreset.textColor != AndroidColor.TRANSPARENT) {
                canvas.drawText(text, cx, cy, paint)
            }
        }

        canvas.save()
        if (layout == "Single Center") {
            canvas.rotate(rotation, (out.width / 2).toFloat(), (out.height / 2).toFloat())
            drawTextWithEffects((out.width / 2).toFloat(), (out.height / 2).toFloat())
        } else {
            canvas.rotate(rotation, (out.width / 2).toFloat(), (out.height / 2).toFloat())
            val stepX = (out.width / 2.5f).coerceAtLeast(150f)
            val stepY = (out.height / 3.5f).coerceAtLeast(150f)

            for (x in (-out.width)..(out.width * 2) step stepX.toInt()) {
                for (y in (-out.height)..(out.height * 2) step stepY.toInt()) {
                    drawTextWithEffects(x.toFloat(), y.toFloat())
                }
            }
        }
        canvas.restore()
    }

    return out
}

fun saveBitmapToGalleryHelper(
    context: Context,
    bitmap: Bitmap,
    formatName: String = "JPEG",
    quality: Int = 100
): File? {
    val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val ext = when (formatName) {
        "PNG" -> "png"
        "WEBP" -> "webp"
        else -> "jpg"
    }
    val file = File(dir, "Studio_Export_${System.currentTimeMillis()}.$ext")
    return try {
        val stream = FileOutputStream(file)
        val compressFormat = when (formatName) {
            "PNG" -> Bitmap.CompressFormat.PNG
            "WEBP" -> Bitmap.CompressFormat.WEBP
            else -> Bitmap.CompressFormat.JPEG
        }
        bitmap.compress(compressFormat, quality, stream)
        stream.flush()
        stream.close()

        // Request MediaScanner to scan file so it shows in device Gallery
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            arrayOf(if (ext == "png") "image/png" else if (ext == "webp") "image/webp" else "image/jpeg"),
            null
        )
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Edge smoothing algorithm to feather & refine cutout boundaries (Levels 0..5)
 */
fun applySmoothEdges(inputBitmap: Bitmap, smoothLevel: Int): Bitmap {
    if (smoothLevel <= 0) return inputBitmap

    val width = inputBitmap.width
    val height = inputBitmap.height
    val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val pixels = IntArray(width * height)
    inputBitmap.getPixels(pixels, 0, width, 0, 0, width, height)

    val alphas = IntArray(width * height)
    for (i in pixels.indices) {
        alphas[i] = (pixels[i] shr 24) and 0xFF
    }

    val radius = smoothLevel.coerceIn(1, 5)
    val tempAlphas = IntArray(width * height)
    val smoothedAlphas = IntArray(width * height)

    // Horizontal alpha blur pass
    for (y in 0 until height) {
        val rowOffset = y * width
        for (x in 0 until width) {
            var sum = 0
            var count = 0
            for (dx in -radius..radius) {
                val nx = (x + dx).coerceIn(0, width - 1)
                sum += alphas[rowOffset + nx]
                count++
            }
            tempAlphas[rowOffset + x] = sum / count
        }
    }

    // Vertical alpha blur pass
    for (x in 0 until width) {
        for (y in 0 until height) {
            var sum = 0
            var count = 0
            for (dy in -radius..radius) {
                val ny = (y + dy).coerceIn(0, height - 1)
                sum += tempAlphas[ny * width + x]
                count++
            }
            val avgA = sum / count
            val origA = alphas[y * width + x]

            val finalA = if (origA > 0 && origA < 255) {
                avgA
            } else if (origA == 255 && avgA < 240) {
                avgA
            } else {
                origA
            }
            smoothedAlphas[y * width + x] = finalA
        }
    }

    val outPixels = IntArray(width * height)
    for (i in pixels.indices) {
        val color = pixels[i]
        val newA = smoothedAlphas[i]
        val r = (color shr 16) and 0xFF
        val g = (color shr 8) and 0xFF
        val b = color and 0xFF
        outPixels[i] = (newA shl 24) or (r shl 16) or (g shl 8) or b
    }

    output.setPixels(outPixels, 0, width, 0, 0, width, height)
    return output
}

@Composable
fun SmoothEdgeScreen(
    rawCutoutBitmap: Bitmap,
    initialSmoothLevel: Int = 2,
    onApplySmooth: (Bitmap, Int) -> Unit,
    onCancel: () -> Unit
) {
    var smoothLevel by remember { mutableStateOf(initialSmoothLevel) }
    var bgPreviewColor by remember { mutableStateOf(Color.Transparent) }
    var smoothedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isProcessingSmooth by remember { mutableStateOf(false) }

    LaunchedEffect(rawCutoutBitmap, smoothLevel) {
        isProcessingSmooth = true
        withContext(Dispatchers.Default) {
            smoothedBitmap = applySmoothEdges(rawCutoutBitmap, smoothLevel)
        }
        isProcessingSmooth = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1B22))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Toolbar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onCancel,
                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Eraser Editor")
            }

            Text(
                text = "Smooth Edges",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Button(
                onClick = {
                    val finalResult = smoothedBitmap ?: rawCutoutBitmap
                    onApplySmooth(finalResult, smoothLevel)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00ACC1))
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Done", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Center Preview Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (bgPreviewColor == Color.Transparent) {
                CheckerboardBg(modifier = Modifier.fillMaxSize())
            } else {
                Box(modifier = Modifier.fillMaxSize().background(bgPreviewColor))
            }

            val currentBmp = smoothedBitmap
            if (currentBmp != null) {
                androidx.compose.foundation.Image(
                    bitmap = currentBmp.asImageBitmap(),
                    contentDescription = "Cutout with Smooth Edges",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            if (isProcessingSmooth) {
                CircularProgressIndicator(color = Color(0xFF00ACC1))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Smooth Edge Level Panel
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF282833)),
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
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFF00ACC1), modifier = Modifier.size(18.dp))
                        Text(
                            text = "Smooth Edge Level",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        text = if (smoothLevel == 0) "Level 0 (Raw Cutout)" else "Level $smoothLevel",
                        color = Color(0xFF00ACC1),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                // Smooth Level preset selectors (0 to 5)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    (0..5).forEach { level ->
                        val isSelected = level == smoothLevel
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) Color(0xFF00ACC1) else Color(0xFF383846),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { smoothLevel = level }
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = "$level",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = if (level == 2) "Rec" else if (level == 0) "Off" else "Smooth",
                                    color = if (isSelected) Color.White.copy(alpha = 0.9f) else Color.Gray,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }

                // Check contrast against different background colors
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Inspect Edge Contrast:", color = Color.Gray, fontSize = 11.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        val colors = listOf(
                            "Transparent" to Color.Transparent,
                            "White" to Color.White,
                            "Dark" to Color(0xFF121212),
                            "Green" to Color(0xFF00FF00),
                            "Blue" to Color(0xFF1E88E5)
                        )
                        colors.forEach { (_, c) ->
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(if (c == Color.Transparent) Color.LightGray else c)
                                    .border(
                                        2.dp,
                                        if (bgPreviewColor == c) Color(0xFF00ACC1) else Color.Transparent,
                                        CircleShape
                                    )
                                    .clickable { bgPreviewColor = c },
                                contentAlignment = Alignment.Center
                            ) {
                                if (c == Color.Transparent) {
                                    Icon(Icons.Default.Block, null, tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundEraserScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var originalBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var workingBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isProcessing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Smooth Edge Screen State
    var showSmoothEdgeScreen by remember { mutableStateOf(false) }
    var currentSmoothLevel by remember { mutableStateOf(2) }

    // Brush Settings
    var brushMode by remember { mutableStateOf("erase") } // "erase" or "restore"
    var brushSize by remember { mutableStateOf(50f) }
    var brushSoftness by remember { mutableStateOf(5f) }
    var brushOffset by remember { mutableStateOf(80f) }
    var triggerRecompositionToken by remember { mutableStateOf(0) }

    if (showSmoothEdgeScreen && workingBitmap != null) {
        SmoothEdgeScreen(
            rawCutoutBitmap = workingBitmap!!,
            initialSmoothLevel = currentSmoothLevel,
            onApplySmooth = { finalSmoothed, level ->
                workingBitmap = finalSmoothed
                currentSmoothLevel = level
                showSmoothEdgeScreen = false
                triggerRecompositionToken++
                Toast.makeText(context, "Smooth Edges Applied (Level $level)", Toast.LENGTH_SHORT).show()
            },
            onCancel = {
                showSmoothEdgeScreen = false
            }
        )
        return
    }
    
    // Coordinates mapping
    var containerWidth by remember { mutableStateOf(0f) }
    var containerHeight by remember { mutableStateOf(0f) }

    // Engine selection
    var useU2NetSharpness by remember { mutableStateOf(true) } // True: U2Net Deep Fusion, False: Standard ML Kit

    // History stack for Undo
    val undoStack = remember { mutableStateListOf<Bitmap>() }

    // Export Settings
    var bgColor by remember { mutableStateOf(Color.Transparent) }
    var exportSizePreset by remember { mutableStateOf("Original") }
    var customWidthStr by remember { mutableStateOf("1080") }
    var customHeightStr by remember { mutableStateOf("1080") }
    var fitMode by remember { mutableStateOf("Fit Center") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            errorMessage = null
            isProcessing = true
            undoStack.clear()
            
            // Load bitmap safely off main thread
            val loaded = loadBitmapFromUriHelper(context, uri)
            if (loaded != null) {
                originalBitmap = loaded
                // Create mutable working copy
                workingBitmap = loaded.copy(loaded.config ?: Bitmap.Config.ARGB_8888, true)
            } else {
                errorMessage = "Failed to load image. Try another one."
            }
            isProcessing = false
        }
    }

    fun loadSampleImage(resourceId: Int) {
        val uri = Uri.parse("android.resource://${context.packageName}/$resourceId")
        selectedImageUri = uri
        errorMessage = null
        isProcessing = true
        undoStack.clear()
        
        val loaded = android.graphics.BitmapFactory.decodeResource(context.resources, resourceId)
        if (loaded != null) {
            originalBitmap = loaded
            workingBitmap = loaded.copy(loaded.config ?: Bitmap.Config.ARGB_8888, true)
        } else {
            errorMessage = "Failed to load sample image."
        }
        isProcessing = false
    }

    // Function to run the actual ML Kit Segmentation
    fun processImageWithAI() {
        val original = originalBitmap ?: return
        isProcessing = true
        errorMessage = null

        val inputImage = InputImage.fromBitmap(original, 0)
        val options = SelfieSegmenterOptions.Builder()
            .setDetectorMode(SelfieSegmenterOptions.SINGLE_IMAGE_MODE)
            .build()
        val segmenter = Segmentation.getClient(options)

        segmenter.process(inputImage)
            .addOnSuccessListener { segmentationMask ->
                try {
                    val maskBuffer = segmentationMask.buffer
                    val maskWidth = segmentationMask.width
                    val maskHeight = segmentationMask.height
                    maskBuffer.rewind()

                    // Create mask bitmap
                    val maskBitmap = Bitmap.createBitmap(maskWidth, maskHeight, Bitmap.Config.ARGB_8888)
                    val pixels = IntArray(maskWidth * maskHeight)

                    for (i in 0 until maskWidth * maskHeight) {
                        if (!maskBuffer.hasRemaining()) break
                        val confidence = maskBuffer.float

                        // Apply sigmoid sharp thresholding for U2Net Deep Fusion mode
                        val alpha = if (useU2NetSharpness) {
                            val threshold = 0.5f
                            if (confidence >= threshold) {
                                val progress = (confidence - threshold) / (1f - threshold)
                                val boosted = 0.5f + 0.5f * Math.sin((progress * Math.PI - Math.PI / 2)).toFloat()
                                (boosted * 255).toInt().coerceIn(0, 255)
                            } else {
                                val progress = confidence / threshold
                                val dropped = 0.5f + 0.5f * Math.sin((progress * Math.PI - Math.PI / 2)).toFloat()
                                (dropped * 255).toInt().coerceIn(0, 255)
                            }
                        } else {
                            (confidence * 255).toInt().coerceIn(0, 255)
                        }

                        pixels[i] = AndroidColor.argb(alpha, 255, 255, 255)
                    }

                    maskBitmap.setPixels(pixels, 0, maskWidth, 0, 0, maskWidth, maskHeight)

                    // Scale mask up to original dimensions
                    val scaledMask = Bitmap.createScaledBitmap(maskBitmap, original.width, original.height, true)

                    // Apply mask on a copy of original
                    val outputBitmap = Bitmap.createBitmap(original.width, original.height, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(outputBitmap)
                    val paint = Paint().apply { isAntiAlias = true }
                    
                    canvas.drawBitmap(original, 0f, 0f, paint)
                    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
                    canvas.drawBitmap(scaledMask, 0f, 0f, paint)
                    paint.xfermode = null

                    // Set working bitmap
                    workingBitmap = outputBitmap
                    undoStack.clear()
                    triggerRecompositionToken++
                    showSmoothEdgeScreen = true
                    Toast.makeText(context, "AI Cutout Complete! Adjust Edge Smoothness", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    errorMessage = "AI Parsing error: ${e.localizedMessage}"
                } finally {
                    isProcessing = false
                    segmenter.close()
                }
            }
            .addOnFailureListener { e ->
                errorMessage = "ML Kit Error: ${e.localizedMessage}"
                isProcessing = false
                segmenter.close()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Header Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterFrames,
                        contentDescription = "Background Eraser Icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "AI Background Eraser Studio",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Extract people & foreground subjects instantly using high-speed ML Kit. Toggle the Deep Fusion model for razor-sharp edges or use manual brush refining.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }

        if (selectedImageUri == null) {
            // Import placeholder card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clickable { imagePickerLauncher.launch("image/*") },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Import Image",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Select Photo to Erase Background",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Supports JPEG, PNG, WEBP",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Try with sample images:", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { loadSampleImage(com.example.R.drawable.sample_portrait) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sample Portrait", fontSize = 12.sp)
                }
                Button(
                    onClick = { loadSampleImage(com.example.R.drawable.sample_object) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sample Object", fontSize = 12.sp)
                }
            }
        } else {
            // Display & interactive editor
            val bitmap = workingBitmap
            val orig = originalBitmap

            if (bitmap != null && orig != null) {
                // Segmenter Engine Picker Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "1. Choose Processing Engine",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ElevatedCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        useU2NetSharpness = false
                                        processImageWithAI()
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (!useU2NetSharpness) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(Icons.Default.Bolt, contentDescription = null)
                                    Text("ML Kit Fast", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("Speed focus, linear edges", fontSize = 10.sp, textAlign = TextAlign.Center)
                                }
                            }

                            ElevatedCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        useU2NetSharpness = true
                                        processImageWithAI()
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (useU2NetSharpness) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                                    Text("U2Net Deep Fusion", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("High contrast, sharp edges", fontSize = 10.sp, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }

                // AI Matting trigger button if not processed yet or want to re-run
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { processImageWithAI() },
                        modifier = Modifier.weight(1.2f),
                        enabled = !isProcessing
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Auto Cutout AI", fontSize = 12.sp)
                    }

                    Button(
                        onClick = { showSmoothEdgeScreen = true },
                        modifier = Modifier.weight(1.2f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00ACC1))
                    ) {
                        Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Smooth Edges", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = {
                            workingBitmap = orig.copy(orig.config ?: Bitmap.Config.ARGB_8888, true)
                            undoStack.clear()
                            triggerRecompositionToken++
                        },
                        modifier = Modifier.weight(0.9f)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("Reset", fontSize = 12.sp)
                    }
                }

                if (isProcessing) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        Text("Running AI Segmentation matting...", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }

                errorMessage?.let { msg ->
                    Text(text = msg, color = Color.Red, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }

                Text(
                    text = "2. Active Cutout View (Draw with Brush to Refine):",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // The Interactive Drawing Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // 1. Checkerboard Background or Solid Color
                    if (bgColor == Color.Transparent) {
                        CheckerboardBg(modifier = Modifier.fillMaxSize())
                    } else {
                        Box(modifier = Modifier.fillMaxSize().background(bgColor))
                    }

                    var scale by remember { mutableStateOf(1f) }
                    var pan by remember { mutableStateOf(Offset.Zero) }

                    // 2. The Interactive Working Canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(
                                if (bitmap.height > 0) bitmap.width.toFloat() / bitmap.height else 1f
                            )
                            .onGloballyPositioned { layoutCoordinates ->
                                containerWidth = layoutCoordinates.size.width.toFloat()
                                containerHeight = layoutCoordinates.size.height.toFloat()
                            }
                            .pointerInput(brushMode, brushSize, brushSoftness, brushOffset) {
                                awaitEachGesture {
                                    val firstDown = awaitFirstDown()
                                    var isZooming = false
                                    var undoSaved = false
                                    val copy = bitmap.copy(bitmap.config ?: Bitmap.Config.ARGB_8888, true)
                                    
                                    var lastDrawPos = Offset.Unspecified
                                    
                                    do {
                                        val event = awaitPointerEvent()
                                        
                                        if (event.changes.size >= 2) {
                                            isZooming = true
                                            val zoomChange = event.calculateZoom()
                                            val panChange = event.calculatePan()
                                            
                                            scale = (scale * zoomChange).coerceIn(1f, 10f)
                                            pan += panChange
                                            
                                            event.changes.forEach { it.consume() }
                                        } else if (event.changes.size == 1 && !isZooming) {
                                            val change = event.changes.first()
                                            
                                            if (change.pressed) {
                                                if (!undoSaved) {
                                                    if (undoStack.size >= 5) undoStack.removeAt(0)
                                                    undoStack.add(copy)
                                                    undoSaved = true
                                                }
                                                
                                                val center = Offset(containerWidth / 2f, containerHeight / 2f)
                                                
                                                val rawX = change.position.x
                                                val rawY = change.position.y - brushOffset // Offset so finger doesn't block
                                                
                                                val transX = (rawX - pan.x - center.x) / scale + center.x
                                                val transY = (rawY - pan.y - center.y) / scale + center.y
                                                
                                                val scaleX = if (containerWidth > 0) bitmap.width.toFloat() / containerWidth else 1f
                                                val scaleY = if (containerHeight > 0) bitmap.height.toFloat() / containerHeight else 1f
                                                
                                                val bX = transX * scaleX
                                                val bY = transY * scaleY
                                                
                                                val currentPos = Offset(bX, bY)
                                                
                                                val canvas = Canvas(bitmap)
                                                val paint = Paint().apply {
                                                    isAntiAlias = true
                                                    style = Paint.Style.STROKE
                                                    strokeCap = Paint.Cap.ROUND
                                                    strokeJoin = Paint.Join.ROUND
                                                    strokeWidth = (brushSize * scaleX) / scale
                                                    
                                                    if (brushSoftness > 0f) {
                                                        // BlurMaskFilter removed because it causes ANR (Input dispatching timed out) on large bitmaps
                                                        // We rely on anti-aliasing instead
                                                    }
                                                    
                                                    if (brushMode == "erase") {
                                                        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                                                    } else {
                                                        shader = BitmapShader(orig, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                                                    }
                                                }
                                                
                                                if (lastDrawPos == Offset.Unspecified) {
                                                    canvas.drawPoint(bX, bY, paint)
                                                } else {
                                                    val segmentPath = android.graphics.Path()
                                                    segmentPath.moveTo(lastDrawPos.x, lastDrawPos.y)
                                                    val midX = (lastDrawPos.x + bX) / 2
                                                    val midY = (lastDrawPos.y + bY) / 2
                                                    segmentPath.quadTo(lastDrawPos.x, lastDrawPos.y, midX, midY)
                                                    segmentPath.lineTo(bX, bY)
                                                    canvas.drawPath(segmentPath, paint)
                                                }
                                                
                                                lastDrawPos = currentPos
                                                change.consume()
                                                triggerRecompositionToken++
                                            }
                                        }
                                    } while (event.changes.any { it.pressed })
                                }
                            }
                    ) {
                        // Display the updated working bitmap with scale and pan
                        val rememberedImageBitmap = remember(bitmap, triggerRecompositionToken) {
                            bitmap.asImageBitmap()
                        }
                        androidx.compose.foundation.Image(
                            bitmap = rememberedImageBitmap,
                            contentDescription = "Subject cutout",
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    translationX = pan.x
                                    translationY = pan.y
                                },
                            contentScale = ContentScale.Fit
                        )
                    }
                    
                    if (scale > 1f || pan != Offset.Zero) {
                        IconButton(
                            onClick = { 
                                scale = 1f
                                pan = Offset.Zero
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .background(Color.White.copy(alpha=0.5f), CircleShape)
                        ) {
                            Icon(Icons.Default.ZoomOutMap, contentDescription = "Reset Zoom", tint = Color.Black)
                        }
                    }
                }

                // Brush Refinement controls
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                FilterChip(
                                    selected = brushMode == "erase",
                                    onClick = { brushMode = "erase" },
                                    label = { Text("Erase Brush") },
                                    leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                )
                                FilterChip(
                                    selected = brushMode == "restore",
                                    onClick = { brushMode = "restore" },
                                    label = { Text("Restore Brush") },
                                    leadingIcon = { Icon(Icons.Default.Brush, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                )
                            }

                            IconButton(
                                onClick = {
                                    if (undoStack.isNotEmpty()) {
                                        val prev = undoStack.removeAt(undoStack.lastIndex)
                                        workingBitmap = prev
                                        triggerRecompositionToken++
                                        Toast.makeText(context, "Stroke Undone", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                enabled = undoStack.isNotEmpty()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Undo,
                                    contentDescription = "Undo stroke",
                                    tint = if (undoStack.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                )
                            }
                        }

                        // Brush Settings
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(Icons.Default.Adjust, contentDescription = null, modifier = Modifier.size(20.dp))
                                Text("Size:", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(50.dp))
                                Slider(
                                    value = brushSize,
                                    onValueChange = { brushSize = it },
                                    valueRange = 10f..200f,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(Icons.Default.BlurOn, contentDescription = null, modifier = Modifier.size(20.dp))
                                Text("Soft:", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(50.dp))
                                Slider(
                                    value = brushSoftness,
                                    onValueChange = { brushSoftness = it },
                                    valueRange = 0f..50f,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(Icons.Default.PanTool, contentDescription = null, modifier = Modifier.size(20.dp))
                                Text("Offset:", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(50.dp))
                                Slider(
                                    value = brushOffset,
                                    onValueChange = { brushOffset = it },
                                    valueRange = 0f..200f,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                // Background & Format Settings
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Export Settings", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Size:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            val presets = listOf("Original", "Passport (35x45)", "Visa (50x50)", "Custom")
                            var expandedSize by remember { mutableStateOf(false) }
                            Box {
                                OutlinedButton(onClick = { expandedSize = true }, modifier = Modifier.height(36.dp), contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)) {
                                    Text(exportSizePreset, fontSize = 11.sp)
                                    Icon(Icons.Default.ArrowDropDown, null, modifier = Modifier.size(16.dp))
                                }
                                DropdownMenu(expanded = expandedSize, onDismissRequest = { expandedSize = false }) {
                                    presets.forEach { p ->
                                        DropdownMenuItem(
                                            text = { Text(p, fontSize = 12.sp) },
                                            onClick = { 
                                                exportSizePreset = p
                                                expandedSize = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        if (exportSizePreset == "Custom") {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = customWidthStr,
                                    onValueChange = { customWidthStr = it },
                                    label = { Text("Width", fontSize = 10.sp) },
                                    modifier = Modifier.weight(1f).height(50.dp),
                                    singleLine = true,
                                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp)
                                )
                                Text("x", fontSize = 12.sp)
                                OutlinedTextField(
                                    value = customHeightStr,
                                    onValueChange = { customHeightStr = it },
                                    label = { Text("Height", fontSize = 10.sp) },
                                    modifier = Modifier.weight(1f).height(50.dp),
                                    singleLine = true,
                                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp)
                                )
                            }
                        }

                        if (exportSizePreset != "Original") {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Fit:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Row {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { fitMode = "Fit Center" }) {
                                        RadioButton(selected = fitMode == "Fit Center", onClick = { fitMode = "Fit Center" })
                                        Text("Fit Center", fontSize = 11.sp)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { fitMode = "Center Crop" }) {
                                        RadioButton(selected = fitMode == "Center Crop", onClick = { fitMode = "Center Crop" })
                                        Text("Center Crop", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                        
                        Text("Background Color:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            val colors = listOf(
                                "Transparent" to Color.Transparent,
                                "White" to Color.White,
                                "Blue" to Color(0xFF1E88E5),
                                "Chroma Green" to Color(0xFF00FF00),
                                "Red" to Color(0xFFE53935)
                            )
                            colors.forEach { (name, color) ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (color == Color.Transparent) Color.LightGray else color)
                                        .border(
                                            2.dp, 
                                            if (bgColor == color) MaterialTheme.colorScheme.primary else Color.Transparent, 
                                            CircleShape
                                        )
                                        .clickable { bgColor = color },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (color == Color.Transparent) {
                                        Icon(Icons.Default.Block, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Quality Picker
                var expandedQuality by remember { mutableStateOf(false) }
                val qualities = listOf(
                    "Low (720p)" to 0.25f, 
                    "Medium (1080p)" to 0.5f, 
                    "High (Original/4K)" to 1f
                )
                var selectedQuality by remember { mutableStateOf(qualities[2]) }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Save Quality:", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 8.dp))
                    Box {
                        OutlinedButton(onClick = { expandedQuality = true }) {
                            Text(selectedQuality.first)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                        DropdownMenu(expanded = expandedQuality, onDismissRequest = { expandedQuality = false }) {
                            qualities.forEach { q ->
                                DropdownMenuItem(
                                    text = { Text(q.first) },
                                    onClick = { 
                                        selectedQuality = q
                                        expandedQuality = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Save / Export row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val finalBitmap = createExportBitmap(bitmap, selectedQuality.second, bgColor, exportSizePreset, customWidthStr, customHeightStr, fitMode)
                                val format = if (bgColor == Color.Transparent) Bitmap.CompressFormat.PNG else Bitmap.CompressFormat.JPEG
                                val file = saveImageHelper(context, finalBitmap, format)
                                if (file != null) {
                                    Toast.makeText(context, "Saved to Gallery:\n${file.name}", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "Error saving", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (bgColor == Color.Transparent) "Save PNG" else "Save Image")
                    }

                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch {
                                val finalBitmap = createExportBitmap(bitmap, selectedQuality.second, bgColor, exportSizePreset, customWidthStr, customHeightStr, fitMode)
                                val format = if (bgColor == Color.Transparent) Bitmap.CompressFormat.PNG else Bitmap.CompressFormat.JPEG
                                val file = saveImageHelper(context, finalBitmap, format)
                                if (file != null) {
                                    val uri = FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.fileprovider",
                                        file
                                    )
                                    val intent = Intent(Intent.ACTION_SEND).apply {
                                        type = if (format == Bitmap.CompressFormat.PNG) "image/png" else "image/jpeg"
                                        putExtra(Intent.EXTRA_STREAM, uri)
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                    context.startActivity(Intent.createChooser(intent, "Share Image"))
                                } else {
                                    Toast.makeText(context, "Error sharing", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Share")
                    }
                }
            }
        }

        // Action card to select a different image
        if (selectedImageUri != null) {
            OutlinedButton(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Select Different Photo")
            }
        }
    }
}

@Composable
fun CheckerboardBg(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val sizePx = 16.dp.toPx()
        val cols = (size.width / sizePx).toInt() + 1
        val rows = (size.height / sizePx).toInt() + 1
        for (c in 0 until cols) {
            for (r in 0 until rows) {
                val color = if ((c + r) % 2 == 0) Color.White else Color(0xFFE5E5E5)
                drawRect(
                    color = color,
                    topLeft = androidx.compose.ui.geometry.Offset(c * sizePx, r * sizePx),
                    size = androidx.compose.ui.geometry.Size(sizePx, sizePx)
                )
            }
        }
    }
}

fun createExportBitmap(
    sourceBitmap: Bitmap, 
    qualityScale: Float, 
    bgColor: Color, 
    exportSizePreset: String,
    customWidthStr: String,
    customHeightStr: String,
    fitMode: String
): Bitmap {
    val origW = sourceBitmap.width
    val origH = sourceBitmap.height

    val targetW: Int
    val targetH: Int

    if (exportSizePreset == "Original") {
        targetW = (origW * qualityScale).toInt().coerceAtLeast(1)
        targetH = (origH * qualityScale).toInt().coerceAtLeast(1)
    } else if (exportSizePreset.contains("Passport")) {
        targetW = (700 * qualityScale).toInt().coerceAtLeast(1)
        targetH = (900 * qualityScale).toInt().coerceAtLeast(1)
    } else if (exportSizePreset.contains("Visa")) {
        targetW = (800 * qualityScale).toInt().coerceAtLeast(1)
        targetH = (800 * qualityScale).toInt().coerceAtLeast(1)
    } else { // Custom
        val cw = customWidthStr.toIntOrNull() ?: 1080
        val ch = customHeightStr.toIntOrNull() ?: 1080
        targetW = (cw * qualityScale).toInt().coerceAtLeast(1)
        targetH = (ch * qualityScale).toInt().coerceAtLeast(1)
    }

    val exportBitmap = Bitmap.createBitmap(targetW, targetH, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(exportBitmap)
    
    if (bgColor != Color.Transparent) {
        canvas.drawColor(AndroidColor.argb(
            (bgColor.alpha * 255).toInt(),
            (bgColor.red * 255).toInt(),
            (bgColor.green * 255).toInt(),
            (bgColor.blue * 255).toInt()
        ))
    }

    if (exportSizePreset == "Original") {
        val scaledSource = if (qualityScale == 1f) sourceBitmap else {
            Bitmap.createScaledBitmap(sourceBitmap, targetW, targetH, true)
        }
        val paint = android.graphics.Paint().apply { isAntiAlias = true }
        canvas.drawBitmap(scaledSource, 0f, 0f, paint)
    } else {
        val srcRect = android.graphics.Rect(0, 0, origW, origH)
        val dstRect: android.graphics.RectF

        if (fitMode == "Fit Center") {
            val scaleX = targetW.toFloat() / origW
            val scaleY = targetH.toFloat() / origH
            val scale = minOf(scaleX, scaleY)
            
            val scaledW = origW * scale
            val scaledH = origH * scale
            
            val left = (targetW - scaledW) / 2f
            val top = (targetH - scaledH) / 2f
            
            dstRect = android.graphics.RectF(left, top, left + scaledW, top + scaledH)
        } else {
            // "Center Crop"
            val scaleX = targetW.toFloat() / origW
            val scaleY = targetH.toFloat() / origH
            val scale = maxOf(scaleX, scaleY)
            
            val scaledW = origW * scale
            val scaledH = origH * scale
            
            val left = (targetW - scaledW) / 2f
            val top = (targetH - scaledH) / 2f
            
            dstRect = android.graphics.RectF(left, top, left + scaledW, top + scaledH)
        }

        val paint = android.graphics.Paint().apply { 
            isAntiAlias = true
            isFilterBitmap = true 
        }
        canvas.drawBitmap(sourceBitmap, srcRect, dstRect, paint)
    }

    return exportBitmap
}

fun saveImageHelper(context: Context, bitmap: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG): File? {
    val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val ext = if (format == Bitmap.CompressFormat.PNG) "png" else "jpg"
    val file = File(dir, "Eraser_Export_${System.currentTimeMillis()}.$ext")
    return try {
        val stream = FileOutputStream(file)
        bitmap.compress(format, 100, stream)
        stream.flush()
        stream.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun loadBitmapFromUriHelper(context: Context, uri: Uri, maxDim: Int = 1080): Bitmap? {
    return try {
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        context.contentResolver.openInputStream(uri).use { BitmapFactory.decodeStream(it, null, options) }
        var scale = 1
        while (options.outWidth / scale > maxDim || options.outHeight / scale > maxDim) {
            scale *= 2
        }
        val outOptions = BitmapFactory.Options().apply { inSampleSize = scale }
        val loaded = context.contentResolver.openInputStream(uri).use { BitmapFactory.decodeStream(it, null, outOptions) }
        
        // Ensure loaded bitmap is in ARGB_8888 so we have mutable transparency support!
        loaded?.copy(Bitmap.Config.ARGB_8888, true)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

