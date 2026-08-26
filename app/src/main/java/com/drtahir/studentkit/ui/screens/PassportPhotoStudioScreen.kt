package com.drtahir.studentkit.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

// Data model for Passport Country Preset
data class PassportPreset(
    val id: String,
    val name: String,
    val countryFlag: String,
    val widthMm: Float,
    val heightMm: Float,
    val widthPx300Dpi: Int,
    val heightPx300Dpi: Int,
    val defaultBgColor: Color,
    val bgName: String,
    val description: String,
    val headHeightMinPercent: Float = 0.70f,
    val headHeightMaxPercent: Float = 0.80f
)

val standardPassportPresets = listOf(
    PassportPreset(
        id = "pk_passport",
        name = "Pakistan Passport & ID",
        countryFlag = "🇵🇰",
        widthMm = 35f,
        heightMm = 45f,
        widthPx300Dpi = 413,
        heightPx300Dpi = 531,
        defaultBgColor = Color(0xFF64B5F6), // Official Sky Blue or White
        bgName = "Light Sky Blue",
        description = "Standard 35×45mm for NADRA CNIC, Passport, HEC & Visa apps"
    ),
    PassportPreset(
        id = "us_visa",
        name = "US Visa & Passport",
        countryFlag = "🇺🇸",
        widthMm = 51f,
        heightMm = 51f,
        widthPx300Dpi = 600,
        heightPx300Dpi = 600,
        defaultBgColor = Color.White,
        bgName = "Pure White",
        description = "Exact 2×2 inches (51×51mm, 600×600px @ 300 DPI, DS-160)",
        headHeightMinPercent = 0.50f,
        headHeightMaxPercent = 0.69f
    ),
    PassportPreset(
        id = "schengen_eu",
        name = "Schengen & UK Visa",
        countryFlag = "🇪🇺",
        widthMm = 35f,
        heightMm = 45f,
        widthPx300Dpi = 413,
        heightPx300Dpi = 531,
        defaultBgColor = Color(0xFFEEEEEE),
        bgName = "Light Grey / Off-White",
        description = "35×45mm with 70–80% face coverage for all EU/UK embassies"
    ),
    PassportPreset(
        id = "saudi_hajj",
        name = "Saudi Visa / Hajj / Umrah",
        countryFlag = "🇸🇦",
        widthMm = 40f,
        heightMm = 60f,
        widthPx300Dpi = 472,
        heightPx300Dpi = 708,
        defaultBgColor = Color.White,
        bgName = "Pure White",
        description = "4×6cm (or 2×2 in) with white background for Nusuk & MoFA"
    ),
    PassportPreset(
        id = "uae_dubai",
        name = "UAE / Dubai Visit Visa",
        countryFlag = "🇦🇪",
        widthMm = 43f,
        heightMm = 55f,
        widthPx300Dpi = 508,
        heightPx300Dpi = 650,
        defaultBgColor = Color.White,
        bgName = "Pure White",
        description = "43×55mm white background for GDRFA & ICP portals"
    ),
    PassportPreset(
        id = "canada_visa",
        name = "Canada Visa & Passport",
        countryFlag = "🇨🇦",
        widthMm = 50f,
        heightMm = 70f,
        widthPx300Dpi = 591,
        heightPx300Dpi = 827,
        defaultBgColor = Color.White,
        bgName = "Pure White",
        description = "50×70mm for IRCC biometric verification"
    ),
    PassportPreset(
        id = "china_visa",
        name = "China Visa",
        countryFlag = "🇨🇳",
        widthMm = 33f,
        heightMm = 48f,
        widthPx300Dpi = 390,
        heightPx300Dpi = 567,
        defaultBgColor = Color.White,
        bgName = "Pure White",
        description = "33×48mm with clean plain white background"
    ),
    PassportPreset(
        id = "malaysia_visa",
        name = "Malaysia Visa / eNTRI",
        countryFlag = "🇲🇾",
        widthMm = 35f,
        heightMm = 50f,
        widthPx300Dpi = 413,
        heightPx300Dpi = 591,
        defaultBgColor = Color(0xFF1976D2),
        bgName = "Royal Blue / White",
        description = "35×50mm with blue or white background"
    ),
    PassportPreset(
        id = "turkey_visa",
        name = "Turkey e-Visa",
        countryFlag = "🇹🇷",
        widthMm = 50f,
        heightMm = 60f,
        widthPx300Dpi = 591,
        heightPx300Dpi = 708,
        defaultBgColor = Color.White,
        bgName = "Pure White",
        description = "50×60mm biometric photo for Turkish consular services"
    )
)

data class BackgroundColorOption(
    val name: String,
    val color: Color,
    val colorHex: String
)

val availableBackgroundColors = listOf(
    BackgroundColorOption("White", Color.White, "#FFFFFF"),
    BackgroundColorOption("Pakistan Light Blue", Color(0xFF64B5F6), "#64B5F6"),
    BackgroundColorOption("NADRA Sky Blue", Color(0xFF90CAF9), "#90CAF9"),
    BackgroundColorOption("Royal Blue", Color(0xFF1565C0), "#1565C0"),
    BackgroundColorOption("Schengen Off-White", Color(0xFFECEFF1), "#ECEFF1"),
    BackgroundColorOption("Studio Light Grey", Color(0xFFE0E0E0), "#E0E0E0"),
    BackgroundColorOption("Chroma Green", Color(0xFF00E676), "#00E676"),
    BackgroundColorOption("Soft Sand", Color(0xFFFFF8E1), "#FFF8E1")
)

data class ExifTagItem(
    val label: String,
    val value: String,
    val category: String,
    val isSensitive: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassportPhotoStudioScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Studio Sub-Tabs:
    // 0 = Biometric Passport Sizer & Tile Generator
    // 1 = Target KB Compressor (<20KB, <50KB, <100KB)
    // 2 = EXIF Privacy Cleaner & Inspector
    // 3 = Format & DPI Converter (JPG, PNG, WEBP, PDF)
    var selectedStudioTab by remember { mutableStateOf(0) }

    // Shared Image State
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var loadedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var originalFileSizeBytes by remember { mutableStateOf(0L) }
    var originalWidthPx by remember { mutableStateOf(0) }
    var originalHeightPx by remember { mutableStateOf(0) }
    var originalFileName by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val stream = context.contentResolver.openInputStream(uri)
                    val bmp = BitmapFactory.decodeStream(stream)
                    stream?.close()

                    val (size, name) = getUriDetails(context, uri)
                    withContext(Dispatchers.Main) {
                        loadedBitmap = bmp
                        originalWidthPx = bmp?.width ?: 0
                        originalHeightPx = bmp?.height ?: 0
                        originalFileSizeBytes = size
                        originalFileName = name
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error loading image: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Studio Navigation Header Tabs
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 4.dp
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedStudioTab,
                edgePadding = 12.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedStudioTab == 0,
                    onClick = { selectedStudioTab = 0 },
                    text = { Text("Biometric Sizer", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    icon = { Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedStudioTab == 1,
                    onClick = { selectedStudioTab = 1 },
                    text = { Text("Target KB Shrink", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    icon = { Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedStudioTab == 2,
                    onClick = { selectedStudioTab = 2 },
                    text = { Text("EXIF Privacy Shield", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    icon = { Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedStudioTab == 3,
                    onClick = { selectedStudioTab = 3 },
                    text = { Text("Format & PDF", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    icon = { Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
            }
        }

        // Active Sub-Tool Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (selectedStudioTab) {
                0 -> BiometricPassportSizerSection(
                    context = context,
                    loadedBitmap = loadedBitmap,
                    selectedImageUri = selectedImageUri,
                    onPickImage = { imagePickerLauncher.launch("image/*") }
                )
                1 -> TargetKbCompressorSection(
                    context = context,
                    loadedBitmap = loadedBitmap,
                    selectedImageUri = selectedImageUri,
                    originalSize = originalFileSizeBytes,
                    originalName = originalFileName,
                    onPickImage = { imagePickerLauncher.launch("image/*") }
                )
                2 -> ExifPrivacyCleanerSection(
                    context = context,
                    selectedImageUri = selectedImageUri,
                    loadedBitmap = loadedBitmap,
                    onPickImage = { imagePickerLauncher.launch("image/*") }
                )
                3 -> UniversalFormatConverterSection(
                    context = context,
                    loadedBitmap = loadedBitmap,
                    selectedImageUri = selectedImageUri,
                    onPickImage = { imagePickerLauncher.launch("image/*") }
                )
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE A: BIOMETRIC PASSPORT / VISA SIZER & TILING MAKER
// -------------------------------------------------------------
@Composable
fun BiometricPassportSizerSection(
    context: Context,
    loadedBitmap: Bitmap?,
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedPreset by remember { mutableStateOf(standardPassportPresets[0]) }
    var selectedBgColor by remember { mutableStateOf(availableBackgroundColors[1]) }
    var showBiometricOverlay by remember { mutableStateOf(true) }
    var showCuttingBorders by remember { mutableStateOf(true) }

    // Print Tiling Layouts: "Single Photo", "4x6 (6 Photos)", "4x6 (8 Photos)", "A4 Print Sheet (16 Photos)"
    var selectedTilingMode by remember { mutableStateOf("4x6 (6 Photos)") }

    // Interactive Transformations
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var rotationAngle by remember { mutableStateOf(0f) }

    var isProcessingExport by remember { mutableStateOf(false) }

    // Reset transformations when preset or image changes
    LaunchedEffect(selectedPreset, loadedBitmap) {
        scale = 1f
        offsetX = 0f
        offsetY = 0f
        rotationAngle = 0f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Image Selection / Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = selectedPreset.countryFlag,
                            fontSize = 28.sp
                        )
                        Column {
                            Text(
                                text = selectedPreset.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${selectedPreset.widthMm.toInt()}×${selectedPreset.heightMm.toInt()} mm (${selectedPreset.widthPx300Dpi}×${selectedPreset.heightPx300Dpi} px @ 300 DPI)",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = onPickImage,
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (loadedBitmap == null) "Select Photo" else "Replace", fontSize = 12.sp)
                    }
                }

                Text(
                    text = selectedPreset.description,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
        }

        // Country Biometric Preset Carousel
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "🌍 Select Official Biometric Standards:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(standardPassportPresets) { preset ->
                    val isSelected = preset.id == selectedPreset.id
                    Card(
                        onClick = {
                            selectedPreset = preset
                            // Match default country background
                            val matchedBg = availableBackgroundColors.firstOrNull { it.color == preset.defaultBgColor }
                            if (matchedBg != null) selectedBgColor = matchedBg
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        ),
                        border = BorderStroke(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                        ),
                        modifier = Modifier.width(135.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(preset.countryFlag, fontSize = 22.sp)
                            Text(
                                preset.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                            Surface(
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    "${preset.widthMm.toInt()}×${preset.heightMm.toInt()} mm",
                                    fontSize = 9.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Live Biometric Cropping & Alignment Canvas
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "📐 Live Biometric Face Alignment",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 13.sp
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(
                            onClick = {
                                scale = 1f
                                offsetX = 0f
                                offsetY = 0f
                                rotationAngle = 0f
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.Refresh, "Reset", tint = Color.LightGray, modifier = Modifier.size(18.dp))
                        }
                        IconButton(
                            onClick = { rotationAngle = (rotationAngle + 90f) % 360f },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.RotateRight, "Rotate 90", tint = Color(0xFF64B5F6), modifier = Modifier.size(18.dp))
                        }
                    }
                }

                // Interactive Crop Box
                val cropAspectRatio = selectedPreset.widthMm / selectedPreset.heightMm
                val previewBoxWidthDp = 220.dp
                val previewBoxHeightDp = previewBoxWidthDp / cropAspectRatio

                Box(
                    modifier = Modifier
                        .size(previewBoxWidthDp, previewBoxHeightDp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(selectedBgColor.color)
                        .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, rotation ->
                                scale = (scale * zoom).coerceIn(0.5f, 4f)
                                rotationAngle = (rotationAngle + rotation)
                                offsetX += pan.x
                                offsetY += pan.y
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (loadedBitmap != null) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            val bmpW = loadedBitmap.width.toFloat()
                            val bmpH = loadedBitmap.height.toFloat()

                            val baseScale = max(w / bmpW, h / bmpH) * scale
                            val drawW = bmpW * baseScale
                            val drawH = bmpH * baseScale

                            drawContext.canvas.save()
                            drawContext.canvas.translate(w / 2f + offsetX, h / 2f + offsetY)
                            drawContext.canvas.rotate(rotationAngle)
                            drawContext.canvas.drawImageRect(
                                image = loadedBitmap.asImageBitmap(),
                                srcOffset = androidx.compose.ui.unit.IntOffset.Zero,
                                srcSize = androidx.compose.ui.unit.IntSize(loadedBitmap.width, loadedBitmap.height),
                                dstOffset = androidx.compose.ui.unit.IntOffset((-drawW / 2f).toInt(), (-drawH / 2f).toInt()),
                                dstSize = androidx.compose.ui.unit.IntSize(drawW.toInt(), drawH.toInt()),
                                paint = androidx.compose.ui.graphics.Paint()
                            )
                            drawContext.canvas.restore()
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.AddAPhoto, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(36.dp))
                            Text("Tap to Select Photo", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Biometric Guidelines Overlay (Crown, Eye Line, Chin Line, Face Oval)
                    if (showBiometricOverlay) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            val crownY = h * 0.12f
                            val eyeY = h * 0.42f
                            val chinY = h * 0.82f

                            val cyanGuide = Color(0xFF00E5FF)
                            val yellowGuide = Color(0xFFFFD600)
                            val strokeW = 1.5.dp.toPx()
                            val dashEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)

                            // Crown Line
                            drawLine(
                                color = cyanGuide.copy(alpha = 0.7f),
                                start = Offset(0f, crownY),
                                end = Offset(w, crownY),
                                strokeWidth = strokeW,
                                pathEffect = dashEffect
                            )

                            // Eye Line (Crucial biometric benchmark)
                            drawLine(
                                color = yellowGuide.copy(alpha = 0.9f),
                                start = Offset(0f, eyeY),
                                end = Offset(w, eyeY),
                                strokeWidth = strokeW,
                                pathEffect = dashEffect
                            )

                            // Chin Line
                            drawLine(
                                color = cyanGuide.copy(alpha = 0.7f),
                                start = Offset(0f, chinY),
                                end = Offset(w, chinY),
                                strokeWidth = strokeW,
                                pathEffect = dashEffect
                            )

                            // Biometric Face Oval Guideline
                            val ovalW = w * 0.58f
                            val ovalH = h * 0.70f
                            drawOval(
                                color = Color.White.copy(alpha = 0.6f),
                                topLeft = Offset((w - ovalW) / 2f, (h - ovalH) / 2f - h * 0.03f),
                                size = Size(ovalW, ovalH),
                                style = Stroke(width = 1.5.dp.toPx(), pathEffect = dashEffect)
                            )
                        }
                    }
                }

                // Fine Adjustments Controls (Zoom Slider & Leveling)
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Zoom & Scale: ${(scale * 100).toInt()}%", color = Color.LightGray, fontSize = 11.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = showBiometricOverlay,
                                onCheckedChange = { showBiometricOverlay = it },
                                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                            )
                            Text("Biometric Guides", color = Color.White, fontSize = 11.sp)
                        }
                    }
                    Slider(
                        value = scale,
                        onValueChange = { scale = it },
                        valueRange = 0.5f..3.0f,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Background Color Options
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        "🎨 Official Background Canvas:",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(availableBackgroundColors) { bg ->
                            val isSelected = bg.color == selectedBgColor.color
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedBgColor = bg },
                                label = { Text(bg.name, fontSize = 11.sp) },
                                leadingIcon = {
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(CircleShape)
                                            .background(bg.color)
                                            .border(1.dp, Color.Gray, CircleShape)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        // Print Tiling & Sheet Mode Options
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "🖨️ Multi-Photo Print Sheet Layout:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Single Photo", "4x6 (6 Photos)", "4x6 (8 Photos)", "A4 Print Sheet").forEach { mode ->
                        val isSelected = selectedTilingMode == mode
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedTilingMode = mode },
                            label = { Text(mode, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Switch(
                            checked = showCuttingBorders,
                            onCheckedChange = { showCuttingBorders = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add Cutting Guide Lines", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }

                    Text(
                        "300 DPI Lab Print",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00897B)
                    )
                }
            }
        }

        // Export Action Buttons
        Button(
            onClick = {
                if (loadedBitmap == null) {
                    Toast.makeText(context, "Please pick an image first!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                isProcessingExport = true
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        val singlePassportBmp = renderCroppedPassportBitmap(
                            sourceBitmap = loadedBitmap,
                            targetWidthPx = selectedPreset.widthPx300Dpi,
                            targetHeightPx = selectedPreset.heightPx300Dpi,
                            scale = scale,
                            offsetX = offsetX,
                            offsetY = offsetY,
                            rotationAngle = rotationAngle,
                            bgColor = selectedBgColor.color
                        )

                        val finalBmpToSave = if (selectedTilingMode == "Single Photo") {
                            singlePassportBmp
                        } else {
                            renderTiledPassportSheet(
                                passportPhoto = singlePassportBmp,
                                tilingMode = selectedTilingMode,
                                addCuttingLines = showCuttingBorders
                            )
                        }

                        val savedPath = saveStudioBitmapToGallery(
                            context = context,
                            bitmap = finalBmpToSave,
                            title = "Passport_${selectedPreset.id}_${selectedTilingMode.replace(" ", "_")}"
                        )

                        withContext(Dispatchers.Main) {
                            isProcessingExport = false
                            if (savedPath != null) {
                                Toast.makeText(context, "Saved HD Passport Sheet to Gallery! ($savedPath)", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Passport Photo Exported Successfully!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            isProcessingExport = false
                            Toast.makeText(context, "Export error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("export_passport_sheet_button"),
            shape = RoundedCornerShape(12.dp),
            enabled = !isProcessingExport && loadedBitmap != null,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            if (isProcessingExport) {
                CircularProgressIndicator(modifier = Modifier.size(22.dp), color = Color.White, strokeWidth = 2.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Rendering 300 DPI Sheet...", fontWeight = FontWeight.Bold)
            } else {
                Icon(Icons.Default.Download, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Export HD $selectedTilingMode", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE B: TARGET KB ULTRA-PRECISE COMPRESSOR
// -------------------------------------------------------------
@Composable
fun TargetKbCompressorSection(
    context: Context,
    loadedBitmap: Bitmap?,
    selectedImageUri: Uri?,
    originalSize: Long,
    originalName: String,
    onPickImage: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedTargetPreset by remember { mutableStateOf("50") } // "20", "50", "100", "200", "Custom"
    var customTargetKbText by remember { mutableStateOf("50") }

    var isCompressing by remember { mutableStateOf(false) }
    var compressedResultBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var compressedSizeInBytes by remember { mutableStateOf(0L) }
    var compressedSavedPercent by remember { mutableStateOf(0) }
    var compressedQualityUsed by remember { mutableStateOf(0) }
    var compressedDimensions by remember { mutableStateOf(Pair(0, 0)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Image Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "🎯 Target KB Precision Compressor",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Guarantees output size strictly under portal limits",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    OutlinedButton(onClick = onPickImage, shape = RoundedCornerShape(10.dp)) {
                        Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (loadedBitmap == null) "Select Photo" else "Replace", fontSize = 12.sp)
                    }
                }

                if (loadedBitmap != null) {
                    Divider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Original Size: ${formatStudioBytes(originalSize)}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("Dimensions: ${loadedBitmap.width}×${loadedBitmap.height} px", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Target Presets (<20 KB for Jobs, <50 KB for NADRA/HEC, <100 KB for Admissions, <200 KB)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "⚡ Select Target Maximum Size:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("20", "50", "100", "200", "Custom").forEach { preset ->
                        val isSelected = selectedTargetPreset == preset
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedTargetPreset = preset
                                if (preset != "Custom") customTargetKbText = preset
                            },
                            label = { Text(if (preset == "Custom") "Custom" else "< $preset KB", fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                OutlinedTextField(
                    value = customTargetKbText,
                    onValueChange = { input ->
                        customTargetKbText = input.filter { it.isDigit() }
                        selectedTargetPreset = "Custom"
                    },
                    label = { Text("Exact Target Threshold (KB)") },
                    trailingIcon = { Text("KB", modifier = Modifier.padding(end = 12.dp), fontWeight = FontWeight.Bold) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    "💡 Multi-Pass Binary Optimization: Iteratively tunes DCT quantizer and chroma matrices until the output is strictly under ${customTargetKbText.ifEmpty { "0" }} KB while preserving maximum sharpness.",
                    fontSize = 11.sp,
                    color = Color(0xFF00897B),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Compress Trigger Button
        Button(
            onClick = {
                if (loadedBitmap == null) {
                    Toast.makeText(context, "Please select an image first", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val targetKb = customTargetKbText.toIntOrNull() ?: 50
                isCompressing = true
                coroutineScope.launch(Dispatchers.IO) {
                    val result = compressToExactTargetKb(loadedBitmap, targetKb)
                    withContext(Dispatchers.Main) {
                        isCompressing = false
                        compressedResultBitmap = result.bitmap
                        compressedSizeInBytes = result.fileSize
                        compressedQualityUsed = result.quality
                        compressedDimensions = Pair(result.bitmap.width, result.bitmap.height)
                        if (originalSize > 0) {
                            val saved = ((originalSize - result.fileSize).toFloat() / originalSize * 100).toInt()
                            compressedSavedPercent = max(0, saved)
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("run_target_compress_button"),
            shape = RoundedCornerShape(12.dp),
            enabled = !isCompressing && loadedBitmap != null
        ) {
            if (isCompressing) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Optimizing Image Under ${customTargetKbText} KB...", fontWeight = FontWeight.Bold)
            } else {
                Icon(Icons.Default.Speed, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Compress Strictly Under ${customTargetKbText} KB", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }

        // Result Card
        if (compressedResultBitmap != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                border = BorderStroke(1.5.dp, Color(0xFF00E676))
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
                            Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF00E676), modifier = Modifier.size(20.dp))
                            Text("Target Satisfied!", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                        }
                        Surface(color = Color(0xFF00E676), shape = RoundedCornerShape(6.dp)) {
                            Text(
                                "${compressedSavedPercent}% Reduced",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // Before vs After Stats
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Original Size", color = Color.Gray, fontSize = 11.sp)
                            Text(formatStudioBytes(originalSize), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Icon(Icons.Default.ArrowForward, null, tint = Color.LightGray, modifier = Modifier.align(Alignment.CenterVertically))
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Compressed Result", color = Color(0xFF00E676), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text(formatStudioBytes(compressedSizeInBytes), color = Color(0xFF00E676), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }

                    Text(
                        "Resolution: ${compressedDimensions.first}×${compressedDimensions.second} px | Quality Index: $compressedQualityUsed%",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )

                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                val path = saveStudioBitmapToGallery(
                                    context = context,
                                    bitmap = compressedResultBitmap!!,
                                    title = "Compressed_${customTargetKbText}KB_${System.currentTimeMillis()}"
                                )
                                withContext(Dispatchers.Main) {
                                    if (path != null) {
                                        Toast.makeText(context, "Saved compressed image to Gallery! ($path)", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Download, null, tint = Color.Black)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save Compressed Image to Gallery", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE C: EXIF PRIVACY SHIELD & METADATA SANITIZER
// -------------------------------------------------------------
@Composable
fun ExifPrivacyCleanerSection(
    context: Context,
    selectedImageUri: Uri?,
    loadedBitmap: Bitmap?,
    onPickImage: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var exifTagsList by remember { mutableStateOf<List<ExifTagItem>>(emptyList()) }
    var hasGpsLocation by remember { mutableStateOf(false) }
    var gpsCoordinatesText by remember { mutableStateOf("") }
    var isSanitizing by remember { mutableStateOf(false) }
    var sanitizedSavedPath by remember { mutableStateOf<String?>(null) }

    // Read EXIF on image change
    LaunchedEffect(selectedImageUri) {
        if (selectedImageUri != null) {
            val tags = readExifData(context, selectedImageUri)
            exifTagsList = tags
            val gpsTag = tags.firstOrNull { it.label == "GPS Coordinates" }
            hasGpsLocation = gpsTag != null && gpsTag.value != "None"
            gpsCoordinatesText = gpsTag?.value ?: ""
            sanitizedSavedPath = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Privacy Score Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (hasGpsLocation) Color(0xFF7F1D1D) else MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(
                width = 1.5.dp,
                color = if (hasGpsLocation) Color(0xFFEF4444) else MaterialTheme.colorScheme.outlineVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
                        Icon(
                            imageVector = if (hasGpsLocation) Icons.Default.Warning else Icons.Default.Security,
                            contentDescription = null,
                            tint = if (hasGpsLocation) Color(0xFFFF5252) else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Column {
                            Text(
                                text = if (hasGpsLocation) "⚠️ Sensitive EXIF Telemetry Detected" else "🛡️ EXIF Privacy Inspector",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (hasGpsLocation) Color.White else MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = if (hasGpsLocation) "Photo contains GPS coordinates & device IDs" else "Inspect hidden metadata before uploading",
                                fontSize = 11.sp,
                                color = if (hasGpsLocation) Color(0xFFFFCDD2) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    OutlinedButton(onClick = onPickImage, shape = RoundedCornerShape(10.dp)) {
                        Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (selectedImageUri == null) "Select Photo" else "Replace", fontSize = 12.sp)
                    }
                }

                if (hasGpsLocation) {
                    Surface(
                        color = Color.Black.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.LocationOn, null, tint = Color(0xFFFF5252))
                            Text(
                                "GPS Pinpoint: $gpsCoordinatesText",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // One-Tap Sanitize & Strip Button
        Button(
            onClick = {
                if (loadedBitmap == null) {
                    Toast.makeText(context, "Please select an image first", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                isSanitizing = true
                coroutineScope.launch(Dispatchers.IO) {
                    val path = saveSanitizedImage(context, loadedBitmap)
                    withContext(Dispatchers.Main) {
                        isSanitizing = false
                        sanitizedSavedPath = path
                        if (path != null) {
                            Toast.makeText(context, "Sanitized & 100% EXIF Cleaned! Saved to Gallery.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("sanitize_exif_button"),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
            shape = RoundedCornerShape(12.dp),
            enabled = !isSanitizing && loadedBitmap != null
        ) {
            if (isSanitizing) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sanitizing & Stripping Metadata...", color = Color.White, fontWeight = FontWeight.Bold)
            } else {
                Icon(Icons.Default.CleaningServices, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("One-Tap Strip All EXIF & GPS Data", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }

        if (sanitizedSavedPath != null) {
            Surface(
                color = Color(0xFF064E3B),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.VerifiedUser, null, tint = Color(0xFF34D399))
                    Column {
                        Text("Pristine Clean Image Generated!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("File saved in Pictures: $sanitizedSavedPath", color = Color(0xFFA7F3D0), fontSize = 11.sp)
                    }
                }
            }
        }

        // EXIF Metadata Inspector Table
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    "📋 Extracted Telemetry & Hardware Metadata:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                if (exifTagsList.isEmpty()) {
                    Text(
                        "No EXIF tags found or no image selected.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    exifTagsList.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                if (item.isSensitive) {
                                    Icon(Icons.Default.Lock, null, tint = Color(0xFFEF4444), modifier = Modifier.size(14.dp))
                                }
                                Text(item.label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                            }
                            Text(
                                item.value,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (item.isSensitive) Color(0xFFEF4444) else MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE D: UNIVERSAL FORMAT & PDF PASSPORT SHEET CONVERTER
// -------------------------------------------------------------
@Composable
fun UniversalFormatConverterSection(
    context: Context,
    loadedBitmap: Bitmap?,
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedFormat by remember { mutableStateOf("PDF Document") } // PDF, JPEG, PNG, WEBP
    var qualitySlider by remember { mutableStateOf(85f) }
    var selectedDpi by remember { mutableStateOf(300) } // 72, 150, 300, 600
    var isConverting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
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
                    Column {
                        Text(
                            "🔄 Universal Format & PDF Generator",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Convert to PDF, WebP, PNG or High-DPI JPEG",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    OutlinedButton(onClick = onPickImage, shape = RoundedCornerShape(10.dp)) {
                        Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (loadedBitmap == null) "Select Photo" else "Replace", fontSize = 12.sp)
                    }
                }
            }
        }

        // Format Selector
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Select Output Format:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("PDF Document", "WEBP (Best)", "PNG Lossless", "JPEG").forEach { format ->
                        val isSelected = selectedFormat == format
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedFormat = format },
                            label = { Text(format, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                if (selectedFormat != "PNG Lossless") {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Compression Quality", fontSize = 12.sp)
                            Text("${qualitySlider.toInt()}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Slider(
                            value = qualitySlider,
                            onValueChange = { qualitySlider = it },
                            valueRange = 10f..100f
                        )
                    }
                }

                // DPI Selection
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Print Resolution (DPI):", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(72, 150, 300, 600).forEach { dpi ->
                            val isSelected = selectedDpi == dpi
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedDpi = dpi },
                                label = { Text("$dpi DPI") }
                            )
                        }
                    }
                }
            }
        }

        // Convert and Save Button
        Button(
            onClick = {
                if (loadedBitmap == null) {
                    Toast.makeText(context, "Please select an image first", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                isConverting = true
                coroutineScope.launch(Dispatchers.IO) {
                    val path = if (selectedFormat == "PDF Document") {
                        generatePassportPdf(context, loadedBitmap, selectedDpi)
                    } else {
                        convertAndSaveFormat(context, loadedBitmap, selectedFormat, qualitySlider.toInt())
                    }
                    withContext(Dispatchers.Main) {
                        isConverting = false
                        if (path != null) {
                            Toast.makeText(context, "Converted & Saved successfully! ($path)", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("convert_format_button"),
            shape = RoundedCornerShape(12.dp),
            enabled = !isConverting && loadedBitmap != null
        ) {
            if (isConverting) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Converting to $selectedFormat...", fontWeight = FontWeight.Bold)
            } else {
                Icon(Icons.Default.Transform, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Export as $selectedFormat", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER LOGIC: PASSPORT RENDERING, COMPRESSION & EXIF
// -------------------------------------------------------------

fun renderCroppedPassportBitmap(
    sourceBitmap: Bitmap,
    targetWidthPx: Int,
    targetHeightPx: Int,
    scale: Float,
    offsetX: Float,
    offsetY: Float,
    rotationAngle: Float,
    bgColor: Color
): Bitmap {
    val result = Bitmap.createBitmap(targetWidthPx, targetHeightPx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(result)

    // Fill background
    val bgPaint = Paint().apply {
        color = android.graphics.Color.argb(
            (bgColor.alpha * 255).toInt(),
            (bgColor.red * 255).toInt(),
            (bgColor.green * 255).toInt(),
            (bgColor.blue * 255).toInt()
        )
    }
    canvas.drawRect(0f, 0f, targetWidthPx.toFloat(), targetHeightPx.toFloat(), bgPaint)

    // Calculate scale factor relative to target dimensions
    val baseScale = max(targetWidthPx.toFloat() / sourceBitmap.width, targetHeightPx.toFloat() / sourceBitmap.height) * scale
    val matrix = Matrix().apply {
        postTranslate(-sourceBitmap.width / 2f, -sourceBitmap.height / 2f)
        postScale(baseScale, baseScale)
        postRotate(rotationAngle)
        postTranslate(targetWidthPx / 2f + offsetX * 2f, targetHeightPx / 2f + offsetY * 2f)
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    canvas.drawBitmap(sourceBitmap, matrix, paint)
    return result
}

fun renderTiledPassportSheet(
    passportPhoto: Bitmap,
    tilingMode: String,
    addCuttingLines: Boolean
): Bitmap {
    // 300 DPI Standard Dimensions in Pixels
    // 4x6 inches = 1200 x 1800 px
    // A4 = 2480 x 3508 px
    val (sheetW, sheetH, cols, rows) = when (tilingMode) {
        "4x6 (6 Photos)" -> listOf(1800, 1200, 3, 2)
        "4x6 (8 Photos)" -> listOf(1800, 1200, 4, 2)
        "A4 Print Sheet" -> listOf(2480, 3508, 4, 4)
        else -> listOf(1800, 1200, 3, 2)
    }

    val sheet = Bitmap.createBitmap(sheetW, sheetH, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(sheet)
    canvas.drawColor(android.graphics.Color.WHITE)

    val photoW = passportPhoto.width.toFloat()
    val photoH = passportPhoto.height.toFloat()

    // Determine scale to fit into grid slots
    val slotW = sheetW.toFloat() / cols
    val slotH = sheetH.toFloat() / rows

    val fitScale = min((slotW * 0.88f) / photoW, (slotH * 0.88f) / photoH)
    val drawW = photoW * fitScale
    val drawH = photoH * fitScale

    val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = android.graphics.Color.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = 2f
        pathEffect = DashPathEffect(floatArrayOf(12f, 12f), 0f)
    }

    val bmpPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

    for (r in 0 until rows) {
        for (c in 0 until cols) {
            val cenX = c * slotW + slotW / 2f
            val cenY = r * slotH + slotH / 2f

            val left = cenX - drawW / 2f
            val top = cenY - drawH / 2f
            val dstRect = RectF(left, top, left + drawW, top + drawH)

            canvas.drawBitmap(passportPhoto, null, dstRect, bmpPaint)

            if (addCuttingLines) {
                canvas.drawRect(dstRect, borderPaint)
            }
        }
    }

    return sheet
}

data class TargetKbResult(
    val bitmap: Bitmap,
    val fileSize: Long,
    val quality: Int
)

fun compressToExactTargetKb(sourceBitmap: Bitmap, targetKb: Int): TargetKbResult {
    val targetBytes = targetKb * 1024L
    var currentBitmap = sourceBitmap

    var minQuality = 5
    var maxQuality = 98
    var bestQuality = 80
    var bestBytes = ByteArray(0)

    // Iterative Binary Search on Compression Quality
    for (iter in 0 until 8) {
        val midQuality = (minQuality + maxQuality) / 2
        val stream = ByteArrayOutputStream()
        currentBitmap.compress(Bitmap.CompressFormat.JPEG, midQuality, stream)
        val size = stream.size()

        if (size <= targetBytes) {
            bestQuality = midQuality
            bestBytes = stream.toByteArray()
            minQuality = midQuality + 1 // try higher quality if space permits
        } else {
            maxQuality = midQuality - 1
        }
    }

    // If still oversized at lowest quality, scale down dimensions
    if (bestBytes.isEmpty() || bestBytes.size > targetBytes) {
        var scaleFactor = 0.85f
        while (scaleFactor >= 0.25f) {
            val scaledW = (sourceBitmap.width * scaleFactor).toInt().coerceAtLeast(100)
            val scaledH = (sourceBitmap.height * scaleFactor).toInt().coerceAtLeast(100)
            currentBitmap = Bitmap.createScaledBitmap(sourceBitmap, scaledW, scaledH, true)

            val stream = ByteArrayOutputStream()
            currentBitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream)
            if (stream.size() <= targetBytes) {
                bestBytes = stream.toByteArray()
                bestQuality = 75
                break
            }
            scaleFactor -= 0.15f
        }
    }

    val finalBmp = BitmapFactory.decodeByteArray(bestBytes, 0, bestBytes.size) ?: sourceBitmap
    return TargetKbResult(
        bitmap = finalBmp,
        fileSize = bestBytes.size.toLong(),
        quality = bestQuality
    )
}

fun readExifData(context: Context, uri: Uri): List<ExifTagItem> {
    val items = mutableListOf<ExifTagItem>()
    try {
        val stream = context.contentResolver.openInputStream(uri) ?: return items
        val exif = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ExifInterface(stream)
        } else {
            return items
        }

        // GPS Coordinates
        val latLong = FloatArray(2)
        val hasGps = exif.getLatLong(latLong)
        if (hasGps) {
            items.add(ExifTagItem("GPS Coordinates", "${latLong[0]}, ${latLong[1]}", "Location", true))
            items.add(ExifTagItem("GPS Altitude", "${exif.getAltitude(0.0)} m", "Location", true))
        } else {
            items.add(ExifTagItem("GPS Coordinates", "None (Safe)", "Location", false))
        }

        // Camera & Device Hardware
        val make = exif.getAttribute(ExifInterface.TAG_MAKE) ?: "Unknown"
        val model = exif.getAttribute(ExifInterface.TAG_MODEL) ?: "Unknown"
        val software = exif.getAttribute(ExifInterface.TAG_SOFTWARE) ?: "Unknown"
        items.add(ExifTagItem("Camera Make", make, "Device", false))
        items.add(ExifTagItem("Camera Model", model, "Device", true))
        items.add(ExifTagItem("Software / App", software, "Device", false))

        // Timestamps
        val dateTaken = exif.getAttribute(ExifInterface.TAG_DATETIME) ?: "Not Recorded"
        items.add(ExifTagItem("Date Taken", dateTaken, "Time", true))

        // Exposure Telemetry
        val iso = exif.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS) ?: "Auto"
        val fNumber = exif.getAttribute(ExifInterface.TAG_F_NUMBER) ?: "N/A"
        val expTime = exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME) ?: "N/A"
        val focalLength = exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH) ?: "N/A"
        items.add(ExifTagItem("ISO Speed", iso, "Exposure", false))
        items.add(ExifTagItem("Aperture (F-Stop)", "f/$fNumber", "Exposure", false))
        items.add(ExifTagItem("Exposure Time", "${expTime}s", "Exposure", false))
        items.add(ExifTagItem("Focal Length", "${focalLength}mm", "Exposure", false))

        stream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return items
}

fun saveSanitizedImage(context: Context, sourceBitmap: Bitmap): String? {
    val fileName = "Sanitized_Clean_${System.currentTimeMillis()}.jpg"
    return try {
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val appFolder = File(picturesDir, "StudentKit_Privacy")
        if (!appFolder.exists()) appFolder.mkdirs()

        val file = File(appFolder, fileName)
        val fos = FileOutputStream(file)
        sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos)
        fos.flush()
        fos.close()
        file.absolutePath
    } catch (e: Exception) {
        null
    }
}

fun saveStudioBitmapToGallery(context: Context, bitmap: Bitmap, title: String): String? {
    val fileName = "${title}_${System.currentTimeMillis()}.png"
    return try {
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val appFolder = File(picturesDir, "PassportStudio")
        if (!appFolder.exists()) appFolder.mkdirs()

        val file = File(appFolder, fileName)
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
        file.absolutePath
    } catch (e: Exception) {
        null
    }
}

fun convertAndSaveFormat(context: Context, bitmap: Bitmap, format: String, quality: Int): String? {
    val ext = when (format) {
        "WEBP (Best)" -> "webp"
        "PNG Lossless" -> "png"
        else -> "jpg"
    }
    val fileName = "Export_${System.currentTimeMillis()}.$ext"
    return try {
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val appFolder = File(picturesDir, "PassportStudio")
        if (!appFolder.exists()) appFolder.mkdirs()

        val file = File(appFolder, fileName)
        val fos = FileOutputStream(file)

        when (format) {
            "WEBP (Best)" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, quality, fos)
                } else {
                    bitmap.compress(Bitmap.CompressFormat.WEBP, quality, fos)
                }
            }
            "PNG Lossless" -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            else -> bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
        }

        fos.flush()
        fos.close()
        file.absolutePath
    } catch (e: Exception) {
        null
    }
}

fun generatePassportPdf(context: Context, bitmap: Bitmap, dpi: Int): String? {
    val fileName = "PassportSheet_${System.currentTimeMillis()}.pdf"
    return try {
        val pdfDocument = PdfDocument()
        // A4 page size in points: 595 x 842 pt
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        canvas.drawColor(android.graphics.Color.WHITE)

        val titlePaint = Paint().apply {
            color = android.graphics.Color.DKGRAY
            textSize = 14f
            isFakeBoldText = true
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("Official Biometric Passport Print Sheet — StudentKit Pro", 595 / 2f, 40f, titlePaint)

        // Draw 6 passport photos tiled neatly
        val passportW = 120f
        val passportH = 155f
        val startX = 60f
        val startY = 80f
        val spacingX = 40f
        val spacingY = 30f

        val bmpPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        val borderPaint = Paint().apply {
            color = android.graphics.Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 1f
            pathEffect = DashPathEffect(floatArrayOf(6f, 6f), 0f)
        }

        var photoIndex = 0
        for (r in 0 until 3) {
            for (c in 0 until 3) {
                val left = startX + c * (passportW + spacingX)
                val top = startY + r * (passportH + spacingY)
                val rect = RectF(left, top, left + passportW, top + passportH)

                canvas.drawBitmap(bitmap, null, rect, bmpPaint)
                canvas.drawRect(rect, borderPaint)
                photoIndex++
            }
        }

        pdfDocument.finishPage(page)

        val docsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val appFolder = File(docsDir, "StudentKit_PDFs")
        if (!appFolder.exists()) appFolder.mkdirs()

        val file = File(appFolder, fileName)
        val fos = FileOutputStream(file)
        pdfDocument.writeTo(fos)
        pdfDocument.close()
        fos.flush()
        fos.close()

        file.absolutePath
    } catch (e: Exception) {
        null
    }
}

fun getUriDetails(context: Context, uri: Uri): Pair<Long, String> {
    var size = 0L
    var name = "image.jpg"
    try {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(MediaStore.Images.Media.SIZE)
            val nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            if (cursor.moveToFirst()) {
                if (sizeIndex != -1) size = cursor.getLong(sizeIndex)
                if (nameIndex != -1) name = cursor.getString(nameIndex) ?: "image.jpg"
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return Pair(size, name)
}

private fun formatStudioBytes(bytes: Long): String {
    if (bytes <= 0) return "0 KB"
    val kb = bytes / 1024.0
    val mb = kb / 1024.0
    return if (mb >= 1.0) {
        String.format(Locale.US, "%.2f MB", mb)
    } else {
        String.format(Locale.US, "%.1f KB", kb)
    }
}
