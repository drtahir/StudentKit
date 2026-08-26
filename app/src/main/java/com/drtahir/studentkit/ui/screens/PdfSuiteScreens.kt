package com.drtahir.studentkit.ui.screens

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

/**
 * Enumeration of all 13+ Offline PDF Suite Tools
 */
enum class ComprehensivePdfTool(
    val title: String,
    val description: String,
    val category: String,
    val icon: ImageVector,
    val badge: String
) {
    NONE("PDF Tool Suite", "Choose an offline PDF utility tool below", "General", Icons.Default.Build, ""),
    MERGE("Merge PDFs", "Combine multiple PDF documents into a single document", "Organization", Icons.Default.MergeType, "POPULAR"),
    SPLIT("Split & Extract", "Extract specific pages or page ranges into a new PDF", "Organization", Icons.Default.CallSplit, "CORE"),
    COMPRESS("Compress PDF", "Reduce PDF file size with adjustable compression", "Optimization", Icons.Default.PhotoSizeSelectLarge, "SIZE"),
    PDF_TO_IMAGES("PDF to Images", "Convert PDF pages into high-res JPG or PNG images", "Conversion", Icons.Default.PictureInPicture, "EXPORT"),
    WATERMARK("Add Watermark", "Overlay custom text stamp or repeating watermark", "Security", Icons.Default.BrandingWatermark, "STAMP"),
    ROTATE("Rotate PDF", "Permanently rotate pages 90°, 180°, or 270°", "Organization", Icons.Default.RotateRight, "ORIENT"),
    PAGE_NUMBERS("Add Page Numbers", "Stamp customizable page numbers, headers, and dates", "Document Styling", Icons.Default.FormatListNumbered, "NEW"),
    GRAYSCALE("Grayscale & B/W", "Convert to monochrome/grayscale to save ink & size", "Optimization", Icons.Default.FilterBAndW, "NEW"),
    DARK_MODE("Dark Mode / Invert", "Invert PDF white pages for comfortable night reading", "Accessibility", Icons.Default.DarkMode, "NEW"),
    N_UP_BOOKLET("2-Up & 4-Up Grid", "Place 2 or 4 pages per sheet for compact printing", "Print Layout", Icons.Default.GridView, "NEW"),
    MARGIN_CROP("Margin Cropper", "Trim excessive page borders or slide margins", "Document Styling", Icons.Default.Crop, "NEW"),
    ENCRYPT_LOCK("Password Protect", "Secure PDF with password and encryption header", "Security", Icons.Default.Lock, "NEW"),
    METADATA("Metadata Editor", "Inspect and edit Document Title, Author, Subject, & Tags", "Management", Icons.Default.EditNote, "NEW")
}

data class ComprehensivePdfFile(
    val uri: Uri,
    val name: String,
    val sizeFormatted: String,
    val sizeBytes: Long,
    val pageCount: Int,
    val previewBitmap: Bitmap?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedPdfToolsScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var activeTool by remember { mutableStateOf(ComprehensivePdfTool.NONE) }
    var selectedCategoryFilter by remember { mutableStateOf("All") }

    // Multi-file state for Merge
    var mergeFiles by remember { mutableStateOf<List<ComprehensivePdfFile>>(emptyList()) }

    // Single-file state for other tools
    var singleFile by remember { mutableStateOf<ComprehensivePdfFile?>(null) }

    // Tool Configurations
    var splitPageRangeText by remember { mutableStateOf("1") }
    var splitSelectedMode by remember { mutableStateOf("All Pages") } // All Pages, Odd Pages, Even Pages, Custom Range

    var compressQualityPreset by remember { mutableIntStateOf(60) } // 40, 60, 80
    var compressMaxDimension by remember { mutableIntStateOf(1440) } // 1024, 1440, 1920

    var watermarkText by remember { mutableStateOf("CONFIDENTIAL") }
    var watermarkColor by remember { mutableStateOf(Color(0xFFE53935)) }
    var watermarkAngle by remember { mutableFloatStateOf(-45f) }
    var watermarkOpacity by remember { mutableFloatStateOf(0.35f) }
    var watermarkTextSizeSp by remember { mutableFloatStateOf(36f) }
    var watermarkIsRepeated by remember { mutableStateOf(false) }

    var rotateAngleDegrees by remember { mutableFloatStateOf(90f) }
    var isPngExportFormat by remember { mutableStateOf(false) }

    // New Tool Configurations
    var pageNumberFormat by remember { mutableStateOf("Page {p} of {total}") }
    var pageNumberPosition by remember { mutableStateOf("Bottom Center") } // Bottom Center, Bottom Right, Bottom Left, Top Center, Top Right
    var pageNumberHeader by remember { mutableStateOf("") }
    var pageNumberStartAt by remember { mutableIntStateOf(1) }
    var pageNumberFontSize by remember { mutableFloatStateOf(12f) }

    var grayscaleMode by remember { mutableStateOf("Grayscale (Standard)") } // Grayscale (Standard), High Contrast B&W, Draft Light
    var darkModeTheme by remember { mutableStateOf("Night Dark Invert") } // Night Dark Invert, Warm Sepia Reading Tone, Solarized Deep Navy
    var nUpLayout by remember { mutableStateOf("2-Up (2 Pages per Sheet)") } // 2-Up (2 Pages per Sheet), 4-Up (4 Pages in 2x2 Grid)
    var nUpDrawBorders by remember { mutableStateOf(true) }

    var marginCropPercent by remember { mutableFloatStateOf(10f) } // 5%, 10%, 15%, 20%
    var pdfPasswordText by remember { mutableStateOf("") }
    var pdfPasswordHint by remember { mutableStateOf("") }
    var metadataTitle by remember { mutableStateOf("") }
    var metadataAuthor by remember { mutableStateOf("") }
    var metadataSubject by remember { mutableStateOf("") }
    var metadataKeywords by remember { mutableStateOf("") }

    // Page preview navigation state
    var previewPageIndex by remember { mutableIntStateOf(0) }
    var currentPreviewPageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Progress Modal States
    var isProcessingModalVisible by remember { mutableStateOf(false) }
    var processingProgress by remember { mutableFloatStateOf(0f) }
    var processingStatusText by remember { mutableStateOf("") }
    var completedOutputUri by remember { mutableStateOf<Uri?>(null) }
    var extractedImagesList by remember { mutableStateOf<List<Bitmap>?>(null) }
    var isProcessFinished by remember { mutableStateOf(false) }

    // Activity Launchers
    val pickSinglePdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val item = parseComprehensivePdfFile(context, uri)
            if (item != null) {
                singleFile = item
                previewPageIndex = 0
                if (item.pageCount > 0) {
                    splitPageRangeText = "1-${item.pageCount}"
                }
                metadataTitle = item.name.replace(".pdf", "", ignoreCase = true)
                metadataAuthor = "Hikmah Omni Suite"
            } else {
                Toast.makeText(context, "Failed to read selected PDF file.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(singleFile, previewPageIndex) {
        val file = singleFile
        if (file != null) {
            if (previewPageIndex == 0 && file.previewBitmap != null) {
                currentPreviewPageBitmap = file.previewBitmap
            } else {
                withContext(Dispatchers.IO) {
                    val bmp = renderComprehensivePdfPage(context, file.uri, previewPageIndex, maxDim = 480)
                    currentPreviewPageBitmap = bmp
                }
            }
        } else {
            currentPreviewPageBitmap = null
        }
    }

    val pickMultiplePdfsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        if (!uris.isNullOrEmpty()) {
            val newItems = uris.mapNotNull { parseComprehensivePdfFile(context, it) }
            mergeFiles = mergeFiles + newItems
        }
    }

    BackHandler(enabled = activeTool != ComprehensivePdfTool.NONE) {
        activeTool = ComprehensivePdfTool.NONE
        singleFile = null
        mergeFiles = emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (activeTool != ComprehensivePdfTool.NONE) {
                IconButton(onClick = {
                    activeTool = ComprehensivePdfTool.NONE
                    singleFile = null
                    mergeFiles = emptyList()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back to PDF Tools")
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PictureAsPdf,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = activeTool.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = activeTool.description,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        // TOOL SELECTION DASHBOARD (When ActiveTool == NONE)
        if (activeTool == ComprehensivePdfTool.NONE) {
            val categories = listOf("All", "Organization", "Optimization", "Document Styling", "Security", "Conversion", "Print Layout")

            // Category Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { cat ->
                    FilterChip(
                        selected = selectedCategoryFilter == cat,
                        onClick = { selectedCategoryFilter = cat },
                        label = { Text(cat, fontSize = 12.sp) }
                    )
                }
            }

            val allTools = ComprehensivePdfTool.values().filter { it != ComprehensivePdfTool.NONE }
            val filteredTools = if (selectedCategoryFilter == "All") allTools else allTools.filter { it.category == selectedCategoryFilter }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredTools) { tool ->
                    OutlinedCard(
                        onClick = {
                            activeTool = tool
                            singleFile = null
                            mergeFiles = emptyList()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = tool.icon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(tool.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    if (tool.badge.isNotBlank()) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = if (tool.badge == "NEW") Color(0xFF00C853) else MaterialTheme.colorScheme.primaryContainer
                                        ) {
                                            Text(
                                                text = tool.badge,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (tool.badge == "NEW") Color.White else MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                                Text(tool.description, fontSize = 11.sp, color = Color.Gray, lineHeight = 15.sp)
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                        }
                    }
                }
            }
        } else {
            // ACTIVE TOOL WORKSPACE
            when (activeTool) {
                ComprehensivePdfTool.MERGE -> {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Selected PDFs (${mergeFiles.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            OutlinedButton(
                                onClick = { pickMultiplePdfsLauncher.launch(arrayOf("application/pdf")) },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add PDFs", fontSize = 12.sp)
                            }
                        }

                        if (mergeFiles.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                    .clickable { pickMultiplePdfsLauncher.launch(arrayOf("application/pdf")) },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Tap to Choose PDF Files", fontWeight = FontWeight.Bold)
                                    Text("Select two or more PDF files to merge", fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                itemsIndexed(mergeFiles) { idx, item ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (item.previewBitmap != null) {
                                                Image(
                                                    bitmap = item.previewBitmap.asImageBitmap(),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(40.dp)
                                                        .clip(RoundedCornerShape(6.dp))
                                                )
                                            } else {
                                                Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(item.name, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, maxLines = 1)
                                                Text("${item.pageCount} pages • ${item.sizeFormatted}", fontSize = 11.sp, color = Color.Gray)
                                            }
                                            IconButton(onClick = {
                                                mergeFiles = mergeFiles.filterIndexed { i, _ -> i != idx }
                                            }) {
                                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f))
                                            }
                                        }
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    if (mergeFiles.size < 2) {
                                        Toast.makeText(context, "Please select at least 2 PDF files to merge!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        isProcessingModalVisible = true
                                        isProcessFinished = false
                                        processingProgress = 0.05f
                                        processingStatusText = "Preparing PDF merger..."
                                        completedOutputUri = null

                                        coroutineScope.launch {
                                            processComprehensiveMergePdfs(
                                                context = context,
                                                files = mergeFiles,
                                                onProgress = { prog, status ->
                                                    processingProgress = prog
                                                    processingStatusText = status
                                                },
                                                onComplete = { uri ->
                                                    completedOutputUri = uri
                                                    isProcessFinished = true
                                                    if (uri != null) {
                                                        processingProgress = 1.0f
                                                        processingStatusText = "Merged PDF ready in Downloads!"
                                                    } else {
                                                        processingStatusText = "Failed to merge PDFs."
                                                    }
                                                }
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.MergeType, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Merge ${mergeFiles.size} PDFs")
                            }
                        }
                    }
                }

                // ALL SINGLE-FILE TOOLS
                else -> {
                    val file = singleFile
                    if (file == null) {
                        // Empty Selection State
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                                .clickable { pickSinglePdfLauncher.launch(arrayOf("application/pdf")) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = activeTool.icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                Text("Choose PDF for ${activeTool.title}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Tap here to pick a document from your storage", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { pickSinglePdfLauncher.launch(arrayOf("application/pdf")) },
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.FolderOpen, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Select Document")
                                }
                            }
                        }
                    } else {
                        // Workspace with Live Preview & Tool Controls
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // File Header Card
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(file.name, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, maxLines = 1)
                                        Text("${file.pageCount} pages • ${file.sizeFormatted}", fontSize = 11.sp, color = Color.Gray)
                                    }
                                    OutlinedButton(
                                        onClick = { pickSinglePdfLauncher.launch(arrayOf("application/pdf")) },
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text("Change", fontSize = 11.sp)
                                    }
                                }
                            }

                            // Interactive Live Page Preview Card
                            ComprehensivePdfPreviewCard(
                                fileItem = file,
                                previewBitmap = currentPreviewPageBitmap,
                                activeTool = activeTool,
                                watermarkText = watermarkText,
                                watermarkColor = watermarkColor,
                                watermarkAngle = watermarkAngle,
                                watermarkOpacity = watermarkOpacity,
                                watermarkTextSizeSp = watermarkTextSizeSp,
                                watermarkIsRepeated = watermarkIsRepeated,
                                rotateAngleDegrees = rotateAngleDegrees,
                                pageNumberFormat = pageNumberFormat,
                                pageNumberPosition = pageNumberPosition,
                                pageNumberHeader = pageNumberHeader,
                                grayscaleMode = grayscaleMode,
                                darkModeTheme = darkModeTheme,
                                nUpLayout = nUpLayout,
                                marginCropPercent = marginCropPercent,
                                currentPageIndex = previewPageIndex,
                                onPageChange = { previewPageIndex = it }
                            )

                            // Tool Parameters Controls
                            when (activeTool) {
                                ComprehensivePdfTool.SPLIT -> {
                                    Text("Split & Extraction Mode:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf("All Pages", "Odd Pages", "Even Pages", "Custom Range").forEach { mode ->
                                            FilterChip(
                                                selected = splitSelectedMode == mode,
                                                onClick = { splitSelectedMode = mode },
                                                label = { Text(mode, fontSize = 11.sp) }
                                            )
                                        }
                                    }
                                    if (splitSelectedMode == "Custom Range") {
                                        OutlinedTextField(
                                            value = splitPageRangeText,
                                            onValueChange = { splitPageRangeText = it },
                                            label = { Text("Page Range (e.g. 1-3, 5)") },
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                    }
                                }

                                ComprehensivePdfTool.PAGE_NUMBERS -> {
                                    Text("Page Numbering Pattern:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        items(listOf("Page {p} of {total}", "{p} / {total}", "Page {p}", "- {p} -", "{p}")) { pat ->
                                            FilterChip(
                                                selected = pageNumberFormat == pat,
                                                onClick = { pageNumberFormat = pat },
                                                label = { Text(pat, fontSize = 11.sp) }
                                            )
                                        }
                                    }

                                    Text("Position on Page:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf("Bottom Center", "Bottom Right", "Bottom Left", "Top Center", "Top Right").forEach { pos ->
                                            FilterChip(
                                                selected = pageNumberPosition == pos,
                                                onClick = { pageNumberPosition = pos },
                                                label = { Text(pos, fontSize = 11.sp) }
                                            )
                                        }
                                    }

                                    OutlinedTextField(
                                        value = pageNumberHeader,
                                        onValueChange = { pageNumberHeader = it },
                                        label = { Text("Running Header (Optional e.g. 'Project Dossier')") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                }

                                ComprehensivePdfTool.GRAYSCALE -> {
                                    Text("Monochrome Optimization Mode:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf(
                                            "Grayscale (Standard)" to "Preserves photo tones in soft gray shades (balanced)",
                                            "High Contrast B&W" to "Photocopy mode — crisp dark text and white background",
                                            "Draft Light" to "Reduces toner & ink density for rapid budget printing"
                                        ).forEach { (mode, desc) ->
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable { grayscaleMode = mode },
                                                colors = CardDefaults.cardColors(
                                                    containerColor = if (grayscaleMode == mode) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                                ),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                                    RadioButton(selected = grayscaleMode == mode, onClick = { grayscaleMode = mode })
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Column {
                                                        Text(mode, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                                        Text(desc, fontSize = 11.sp, color = Color.Gray)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                ComprehensivePdfTool.DARK_MODE -> {
                                    Text("Night Reading Theme:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf("Night Dark Invert", "Warm Sepia Reading Tone", "Solarized Deep Navy").forEach { th ->
                                            FilterChip(
                                                selected = darkModeTheme == th,
                                                onClick = { darkModeTheme = th },
                                                label = { Text(th, fontSize = 11.sp) }
                                            )
                                        }
                                    }
                                }

                                ComprehensivePdfTool.N_UP_BOOKLET -> {
                                    Text("Print Imposition Layout:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf("2-Up (2 Pages per Sheet)", "4-Up (4 Pages in 2x2 Grid)").forEach { lay ->
                                            FilterChip(
                                                selected = nUpLayout == lay,
                                                onClick = { nUpLayout = lay },
                                                label = { Text(lay, fontSize = 11.sp) }
                                            )
                                        }
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(checked = nUpDrawBorders, onCheckedChange = { nUpDrawBorders = it })
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Draw cutting guide border around mini pages", fontSize = 12.sp)
                                    }
                                }

                                ComprehensivePdfTool.MARGIN_CROP -> {
                                    Text("Crop Margin Padding (${marginCropPercent.toInt()}%):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Slider(
                                        value = marginCropPercent,
                                        onValueChange = { marginCropPercent = it },
                                        valueRange = 2f..25f
                                    )
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf(5f to "Light (5%)", 10f to "Medium (10%)", 15f to "Deep (15%)", 20f to "Extreme (20%)").forEach { (v, lbl) ->
                                            FilterChip(
                                                selected = marginCropPercent == v,
                                                onClick = { marginCropPercent = v },
                                                label = { Text(lbl, fontSize = 11.sp) }
                                            )
                                        }
                                    }
                                }

                                ComprehensivePdfTool.ENCRYPT_LOCK -> {
                                    Text("Set Document Password Protection:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    OutlinedTextField(
                                        value = pdfPasswordText,
                                        onValueChange = { pdfPasswordText = it },
                                        label = { Text("Lock Password") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    OutlinedTextField(
                                        value = pdfPasswordHint,
                                        onValueChange = { pdfPasswordHint = it },
                                        label = { Text("Password Hint (Optional)") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                }

                                ComprehensivePdfTool.METADATA -> {
                                    Text("Edit Document Metadata Tags:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    OutlinedTextField(
                                        value = metadataTitle,
                                        onValueChange = { metadataTitle = it },
                                        label = { Text("Document Title") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    OutlinedTextField(
                                        value = metadataAuthor,
                                        onValueChange = { metadataAuthor = it },
                                        label = { Text("Author / Creator") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    OutlinedTextField(
                                        value = metadataSubject,
                                        onValueChange = { metadataSubject = it },
                                        label = { Text("Subject / Department") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                }

                                ComprehensivePdfTool.COMPRESS -> {
                                    Text("Compression Quality Presets:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf(40 to "Heavy (Smallest)", 60 to "Balanced (Standard)", 80 to "Light (High Quality)").forEach { (q, lbl) ->
                                            FilterChip(
                                                selected = compressQualityPreset == q,
                                                onClick = { compressQualityPreset = q },
                                                label = { Text(lbl, fontSize = 11.sp) }
                                            )
                                        }
                                    }
                                }

                                ComprehensivePdfTool.WATERMARK -> {
                                    OutlinedTextField(
                                        value = watermarkText,
                                        onValueChange = { watermarkText = it },
                                        label = { Text("Watermark Stamp Text") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf("CONFIDENTIAL", "DRAFT", "ORIGINAL", "SAMPLE", "APPROVED").forEach { tag ->
                                            FilterChip(
                                                selected = watermarkText == tag,
                                                onClick = { watermarkText = tag },
                                                label = { Text(tag, fontSize = 11.sp) }
                                            )
                                        }
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(checked = watermarkIsRepeated, onCheckedChange = { watermarkIsRepeated = it })
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Repeat across full page grid", fontSize = 12.sp)
                                    }
                                }

                                ComprehensivePdfTool.ROTATE -> {
                                    Text("Rotate Document Orientation:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf(0f to "0°", 90f to "90° CW", 180f to "180° Flip", 270f to "270° CCW").forEach { (ang, lbl) ->
                                            FilterChip(
                                                selected = rotateAngleDegrees == ang,
                                                onClick = { rotateAngleDegrees = ang },
                                                label = { Text(lbl, fontSize = 11.sp) }
                                            )
                                        }
                                    }
                                }

                                ComprehensivePdfTool.PDF_TO_IMAGES -> {
                                    Text("Image Format:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        FilterChip(selected = !isPngExportFormat, onClick = { isPngExportFormat = false }, label = { Text("JPG (Fast & Small)") })
                                        FilterChip(selected = isPngExportFormat, onClick = { isPngExportFormat = true }, label = { Text("PNG (Lossless High-Res)") })
                                    }
                                }

                                else -> {}
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // EXECUTE OPERATION ACTION BUTTON
                            Button(
                                onClick = {
                                    val targetFile = singleFile ?: return@Button
                                    isProcessingModalVisible = true
                                    isProcessFinished = false
                                    processingProgress = 0.05f
                                    processingStatusText = "Initializing ${activeTool.title}..."
                                    completedOutputUri = null
                                    extractedImagesList = null

                                    coroutineScope.launch {
                                        when (activeTool) {
                                            ComprehensivePdfTool.PAGE_NUMBERS -> {
                                                processComprehensivePageNumbersPdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    formatPattern = pageNumberFormat,
                                                    position = pageNumberPosition,
                                                    headerText = pageNumberHeader,
                                                    textColor = Color(0xFF1E293B),
                                                    textSizeSp = pageNumberFontSize,
                                                    startNumber = pageNumberStartAt,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Numbered PDF saved to Downloads!" else "Failed to apply page numbers."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.GRAYSCALE -> {
                                                processComprehensiveGrayscalePdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    mode = grayscaleMode,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Monochrome PDF saved to Downloads!" else "Failed to convert."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.DARK_MODE -> {
                                                processComprehensiveDarkModePdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    theme = darkModeTheme,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Night Inverted PDF saved to Downloads!" else "Failed to invert."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.N_UP_BOOKLET -> {
                                                processComprehensiveNUpPdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    layoutMode = nUpLayout,
                                                    drawBorder = nUpDrawBorders,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Imposition N-Up PDF ready in Downloads!" else "Failed to layout booklet."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.MARGIN_CROP -> {
                                                processComprehensiveMarginCropPdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    cropPercent = marginCropPercent,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Cropped PDF saved to Downloads!" else "Failed to crop margins."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.ENCRYPT_LOCK -> {
                                                processComprehensiveEncryptPdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    passwordText = pdfPasswordText.ifEmpty { "studentkit" },
                                                    hintText = pdfPasswordHint,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Password Protected PDF saved to Downloads!" else "Failed to protect."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.METADATA -> {
                                                processComprehensiveMetadataPdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    title = metadataTitle,
                                                    author = metadataAuthor,
                                                    subject = metadataSubject,
                                                    keywords = metadataKeywords,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Updated PDF saved to Downloads!" else "Failed to save metadata."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.SPLIT -> {
                                                val total = targetFile.pageCount
                                                val pagesSet = mutableSetOf<Int>()
                                                if (splitSelectedMode == "Odd Pages") {
                                                    for (p in 1..total step 2) pagesSet.add(p)
                                                } else if (splitSelectedMode == "Even Pages") {
                                                    for (p in 2..total step 2) pagesSet.add(p)
                                                } else {
                                                    val parts = splitPageRangeText.split(",")
                                                    for (part in parts) {
                                                        val trimmed = part.trim()
                                                        if (trimmed.contains("-")) {
                                                            val bounds = trimmed.split("-")
                                                            val start = bounds.getOrNull(0)?.toIntOrNull() ?: 1
                                                            val end = bounds.getOrNull(1)?.toIntOrNull() ?: total
                                                            for (p in start..end) if (p in 1..total) pagesSet.add(p)
                                                        } else {
                                                            val p = trimmed.toIntOrNull()
                                                            if (p != null && p in 1..total) pagesSet.add(p)
                                                        }
                                                    }
                                                }
                                                if (pagesSet.isEmpty()) for (p in 1..total) pagesSet.add(p)

                                                processComprehensiveSplitPdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    selectedPages = pagesSet,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Split PDF saved to Downloads!" else "Failed to split."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.COMPRESS -> {
                                                processComprehensiveCompressPdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    qualityPercent = compressQualityPreset,
                                                    maxDimension = compressMaxDimension,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Compressed PDF saved to Downloads!" else "Failed to compress."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.WATERMARK -> {
                                                processComprehensiveWatermarkPdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    watermarkText = watermarkText,
                                                    textColor = watermarkColor,
                                                    textSizeSp = watermarkTextSizeSp,
                                                    rotationAngle = watermarkAngle,
                                                    opacity = watermarkOpacity,
                                                    isRepeated = watermarkIsRepeated,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Watermarked PDF saved to Downloads!" else "Failed to watermark."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.ROTATE -> {
                                                processComprehensiveRotatePdf(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    rotationAngleDegrees = rotateAngleDegrees,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { uri ->
                                                        completedOutputUri = uri
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (uri != null) "Rotated PDF saved to Downloads!" else "Failed to rotate."
                                                    }
                                                )
                                            }

                                            ComprehensivePdfTool.PDF_TO_IMAGES -> {
                                                processComprehensivePdfToImages(
                                                    context = context,
                                                    fileItem = targetFile,
                                                    isPngFormat = isPngExportFormat,
                                                    onProgress = { prog, status ->
                                                        processingProgress = prog
                                                        processingStatusText = status
                                                    },
                                                    onComplete = { list ->
                                                        extractedImagesList = list
                                                        isProcessFinished = true
                                                        processingProgress = 1.0f
                                                        processingStatusText = if (!list.isNullOrEmpty()) "Extracted ${list.size} images to Pictures/PDF_Images!" else "Failed to extract."
                                                    }
                                                )
                                            }

                                            else -> {}
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("execute_pdf_action_button"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(activeTool.icon, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Execute ${activeTool.title}")
                            }
                        }
                    }
                }
            }
        }
    }

    // REAL-TIME PROGRESS & RESULT MODAL DIALOG
    if (isProcessingModalVisible) {
        Dialog(
            onDismissRequest = {
                if (isProcessFinished) isProcessingModalVisible = false
            },
            properties = DialogProperties(dismissOnBackPress = isProcessFinished, dismissOnClickOutside = isProcessFinished)
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                if (isProcessFinished) Color(0xFF00C853).copy(alpha = 0.15f)
                                else MaterialTheme.colorScheme.primaryContainer
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isProcessFinished) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF00C853), modifier = Modifier.size(32.dp))
                        } else {
                            CircularProgressIndicator(modifier = Modifier.size(30.dp), strokeWidth = 3.dp)
                        }
                    }

                    Text(
                        text = if (isProcessFinished) "Operation Complete!" else "Processing Document...",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(
                        text = processingStatusText,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    LinearProgressIndicator(
                        progress = { processingProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                    )

                    if (isProcessFinished) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            if (completedOutputUri != null) {
                                Button(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            setDataAndType(completedOutputUri, "application/pdf")
                                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                        }
                                        try {
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "No PDF viewer app found.", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Open PDF", fontSize = 12.sp)
                                }

                                OutlinedButton(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_SEND).apply {
                                            type = "application/pdf"
                                            putExtra(Intent.EXTRA_STREAM, completedOutputUri)
                                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                        }
                                        context.startActivity(Intent.createChooser(intent, "Share PDF"))
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Share", fontSize = 12.sp)
                                }
                            } else {
                                Button(
                                    onClick = { isProcessingModalVisible = false },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Close")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// LIVE PREVIEW COMPONENT
// -------------------------------------------------------------
@Composable
fun ComprehensivePdfPreviewCard(
    fileItem: ComprehensivePdfFile,
    previewBitmap: Bitmap?,
    activeTool: ComprehensivePdfTool,
    watermarkText: String,
    watermarkColor: Color,
    watermarkAngle: Float,
    watermarkOpacity: Float,
    watermarkTextSizeSp: Float,
    watermarkIsRepeated: Boolean,
    rotateAngleDegrees: Float,
    pageNumberFormat: String,
    pageNumberPosition: String,
    pageNumberHeader: String,
    grayscaleMode: String,
    darkModeTheme: String,
    nUpLayout: String,
    marginCropPercent: Float,
    currentPageIndex: Int,
    onPageChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Top Stepper Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Live Interactive Preview",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                if (fileItem.pageCount > 1) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { if (currentPageIndex > 0) onPageChange(currentPageIndex - 1) },
                            enabled = currentPageIndex > 0,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.ChevronLeft, contentDescription = "Prev Page", modifier = Modifier.size(18.dp))
                        }
                        Text(
                            text = "${currentPageIndex + 1} / ${fileItem.pageCount}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { if (currentPageIndex < fileItem.pageCount - 1) onPageChange(currentPageIndex + 1) },
                            enabled = currentPageIndex < fileItem.pageCount - 1,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.ChevronRight, contentDescription = "Next Page", modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            // Preview Stage Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                val baseBmp = previewBitmap ?: fileItem.previewBitmap

                if (baseBmp != null) {
                    val aspect = baseBmp.width.toFloat() / baseBmp.height.toFloat().coerceAtLeast(1f)

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(aspect)
                            .shadow(8.dp, RoundedCornerShape(4.dp))
                            .background(
                                if (activeTool == ComprehensivePdfTool.DARK_MODE) Color(0xFF121212) else Color.White,
                                RoundedCornerShape(4.dp)
                            )
                            .clip(RoundedCornerShape(4.dp))
                    ) {
                        // Original Bitmap Rendering
                        Image(
                            bitmap = baseBmp.asImageBitmap(),
                            contentDescription = "PDF Page Preview",
                            modifier = Modifier
                                .fillMaxSize()
                                .then(
                                    if (activeTool == ComprehensivePdfTool.ROTATE) {
                                        Modifier.graphicsLayer { rotationZ = rotateAngleDegrees }
                                    } else if (activeTool == ComprehensivePdfTool.MARGIN_CROP) {
                                        val cropFrac = marginCropPercent / 100f
                                        Modifier.graphicsLayer {
                                            scaleX = 1f + (cropFrac * 1.8f)
                                            scaleY = 1f + (cropFrac * 1.8f)
                                        }
                                    } else Modifier
                                )
                        )

                        // Live Watermark Canvas
                        if (activeTool == ComprehensivePdfTool.WATERMARK && watermarkText.isNotBlank()) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val canvasW = size.width
                                val canvasH = size.height
                                val paintColorInt = android.graphics.Color.argb(
                                    (watermarkOpacity * 255).toInt().coerceIn(0, 255),
                                    (watermarkColor.red * 255).toInt(),
                                    (watermarkColor.green * 255).toInt(),
                                    (watermarkColor.blue * 255).toInt()
                                )
                                val scaledFontSize = (watermarkTextSizeSp * (canvasH / 300f)).coerceAtLeast(10f)
                                val paint = Paint().apply {
                                    color = paintColorInt
                                    textSize = scaledFontSize
                                    isAntiAlias = true
                                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                                    textAlign = Paint.Align.CENTER
                                }

                                if (watermarkIsRepeated) {
                                    val stepX = canvasW / 2.2f
                                    val stepY = canvasH / 3.2f
                                    for (gx in -1..3) {
                                        for (gy in -1..4) {
                                            drawContext.canvas.nativeCanvas.save()
                                            drawContext.canvas.nativeCanvas.translate(gx * stepX, gy * stepY)
                                            drawContext.canvas.nativeCanvas.rotate(watermarkAngle)
                                            drawContext.canvas.nativeCanvas.drawText(watermarkText, 0f, 0f, paint)
                                            drawContext.canvas.nativeCanvas.restore()
                                        }
                                    }
                                } else {
                                    drawContext.canvas.nativeCanvas.save()
                                    drawContext.canvas.nativeCanvas.translate(canvasW / 2f, canvasH / 2f)
                                    drawContext.canvas.nativeCanvas.rotate(watermarkAngle)
                                    drawContext.canvas.nativeCanvas.drawText(watermarkText, 0f, 0f, paint)
                                    drawContext.canvas.nativeCanvas.restore()
                                }
                            }
                        }

                        // Live Page Number & Header Overlay
                        if (activeTool == ComprehensivePdfTool.PAGE_NUMBERS) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val canvasW = size.width
                                val canvasH = size.height
                                val pText = pageNumberFormat
                                    .replace("{p}", "${currentPageIndex + 1}")
                                    .replace("{total}", "${fileItem.pageCount}")

                                val paint = Paint().apply {
                                    color = android.graphics.Color.BLACK
                                    textSize = 10.sp.toPx()
                                    isAntiAlias = true
                                    typeface = Typeface.DEFAULT_BOLD
                                    textAlign = when (pageNumberPosition) {
                                        "Bottom Left", "Top Left" -> Paint.Align.LEFT
                                        "Bottom Right", "Top Right" -> Paint.Align.RIGHT
                                        else -> Paint.Align.CENTER
                                    }
                                }

                                val posX = when (pageNumberPosition) {
                                    "Bottom Left", "Top Left" -> 12.dp.toPx()
                                    "Bottom Right", "Top Right" -> canvasW - 12.dp.toPx()
                                    else -> canvasW / 2f
                                }

                                val posY = when (pageNumberPosition) {
                                    "Top Center", "Top Left", "Top Right" -> 16.dp.toPx()
                                    else -> canvasH - 8.dp.toPx()
                                }

                                drawContext.canvas.nativeCanvas.drawText(pText, posX, posY, paint)

                                if (pageNumberHeader.isNotBlank()) {
                                    val headerPaint = Paint().apply {
                                        color = android.graphics.Color.DKGRAY
                                        textSize = 8.sp.toPx()
                                        isAntiAlias = true
                                        textAlign = Paint.Align.CENTER
                                    }
                                    drawContext.canvas.nativeCanvas.drawText(pageNumberHeader, canvasW / 2f, 12.dp.toPx(), headerPaint)
                                }
                            }
                        }

                        // Live Grayscale Simulation Overlay
                        if (activeTool == ComprehensivePdfTool.GRAYSCALE) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White.copy(alpha = 0.05f))
                            )
                        }

                        // Live Dark Mode Invert Overlay
                        if (activeTool == ComprehensivePdfTool.DARK_MODE) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFF0A192F).copy(alpha = 0.85f))
                            )
                        }
                    }
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(28.dp), color = Color.White)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// CORE 100% OFFLINE PDF PROCESSING IMPLEMENTATIONS
// -------------------------------------------------------------

fun parseComprehensivePdfFile(context: Context, uri: Uri): ComprehensivePdfFile? {
    return try {
        var name = "Document.pdf"
        var sizeBytes = 0L
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIdx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIdx = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst()) {
                if (nameIdx != -1) name = cursor.getString(nameIdx) ?: name
                if (sizeIdx != -1) sizeBytes = cursor.getLong(sizeIdx)
            }
        }
        val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: return null
        val renderer = PdfRenderer(pfd)
        val pageCount = renderer.pageCount
        var previewBmp: Bitmap? = null
        if (pageCount > 0) {
            val page = renderer.openPage(0)
            val aspect = page.width.toFloat() / page.height.toFloat()
            val targetW = 220
            val targetH = (targetW / aspect).toInt().coerceIn(120, 320)
            previewBmp = Bitmap.createBitmap(targetW, targetH, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(previewBmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(previewBmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
        }
        renderer.close()
        pfd.close()

        val sizeFormatted = if (sizeBytes <= 0) "Unknown size" else {
            val kb = sizeBytes / 1024.0
            val mb = kb / 1024.0
            if (mb >= 1.0) String.format(Locale.US, "%.2f MB", mb) else String.format(Locale.US, "%.1f KB", kb)
        }
        ComprehensivePdfFile(uri, name, sizeFormatted, sizeBytes, pageCount, previewBmp)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun renderComprehensivePdfPage(context: Context, uri: Uri, pageIndex: Int, maxDim: Int = 480): Bitmap? {
    return try {
        val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: return null
        val renderer = PdfRenderer(pfd)
        if (pageIndex !in 0 until renderer.pageCount) {
            renderer.close()
            pfd.close()
            return null
        }
        val page = renderer.openPage(pageIndex)
        val aspect = page.width.toFloat() / page.height.toFloat().coerceAtLeast(1f)
        val targetW = if (aspect >= 1f) maxDim else (maxDim * aspect).toInt().coerceAtLeast(100)
        val targetH = if (aspect >= 1f) (maxDim / aspect).toInt().coerceAtLeast(100) else maxDim
        val bmp = Bitmap.createBitmap(targetW, targetH, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        canvas.drawColor(android.graphics.Color.WHITE)
        page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        renderer.close()
        pfd.close()
        bmp
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun saveComprehensivePdfDocument(context: Context, pdfDoc: PdfDocument, fileName: String): Uri? {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
    }
    val collectionUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Downloads.EXTERNAL_CONTENT_URI
    } else {
        MediaStore.Files.getContentUri("external")
    }
    val uri = resolver.insert(collectionUri, contentValues) ?: return null
    resolver.openOutputStream(uri)?.use { os ->
        pdfDoc.writeTo(os)
    }
    return uri
}

suspend fun processComprehensivePageNumbersPdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    formatPattern: String,
    position: String,
    headerText: String,
    textColor: Color,
    textSizeSp: Float,
    startNumber: Int,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing Page Numbering engine...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Stamping page ${i + 1} of $totalPages...")

            val page = renderer.openPage(i)
            val scale = 1.6f
            val pW = (page.width * scale).toInt().coerceAtLeast(100)
            val pH = (page.height * scale).toInt().coerceAtLeast(100)
            val bmp = Bitmap.createBitmap(pW, pH, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, i + 1).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas

            val srcRect = Rect(0, 0, bmp.width, bmp.height)
            val destRect = RectF(0f, 0f, page.width.toFloat(), page.height.toFloat())
            val paint = Paint().apply { isAntiAlias = true; isFilterBitmap = true }
            docCanvas.drawBitmap(bmp, srcRect, destRect, paint)
            bmp.recycle()

            val currentNum = i + startNumber
            val totalNum = totalPages + startNumber - 1
            val pageStr = formatPattern.replace("{p}", "$currentNum").replace("{total}", "$totalNum")

            val numberPaint = Paint().apply {
                color = android.graphics.Color.argb(230, (textColor.red * 255).toInt(), (textColor.green * 255).toInt(), (textColor.blue * 255).toInt())
                textSize = textSizeSp
                isAntiAlias = true
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                textAlign = when (position) {
                    "Bottom Left", "Top Left" -> Paint.Align.LEFT
                    "Bottom Right", "Top Right" -> Paint.Align.RIGHT
                    else -> Paint.Align.CENTER
                }
            }

            val posX = when (position) {
                "Bottom Left", "Top Left" -> 36f
                "Bottom Right", "Top Right" -> page.width - 36f
                else -> page.width / 2f
            }

            val posY = when (position) {
                "Top Center", "Top Left", "Top Right" -> 36f
                else -> page.height - 24f
            }

            docCanvas.drawText(pageStr, posX, posY, numberPaint)

            if (headerText.isNotBlank()) {
                val headerPaint = Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 10f
                    isAntiAlias = true
                    typeface = Typeface.DEFAULT
                    textAlign = Paint.Align.CENTER
                }
                docCanvas.drawText(headerText, page.width / 2f, 26f, headerPaint)
            }

            pdfDoc.finishPage(docPage)
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Compiling numbered PDF...")
        val outUri = saveComprehensivePdfDocument(context, pdfDoc, "Numbered_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(outUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveGrayscalePdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    mode: String,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing Grayscale conversion engine...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        val colorMatrix = ColorMatrix()
        if (mode.contains("High Contrast")) {
            colorMatrix.setSaturation(0f)
            val contrast = 1.4f
            val translate = (-0.2f * 255f)
            val contrastMatrix = floatArrayOf(
                contrast, 0f, 0f, 0f, translate,
                0f, contrast, 0f, 0f, translate,
                0f, 0f, contrast, 0f, translate,
                0f, 0f, 0f, 1f, 0f
            )
            colorMatrix.postConcat(ColorMatrix(contrastMatrix))
        } else {
            colorMatrix.setSaturation(0f)
        }
        val filter = ColorMatrixColorFilter(colorMatrix)

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Converting page ${i + 1} of $totalPages to Grayscale...")

            val page = renderer.openPage(i)
            val scale = 1.5f
            val pW = (page.width * scale).toInt().coerceAtLeast(100)
            val pH = (page.height * scale).toInt().coerceAtLeast(100)
            val bmp = Bitmap.createBitmap(pW, pH, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, i + 1).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas

            val srcRect = Rect(0, 0, bmp.width, bmp.height)
            val destRect = RectF(0f, 0f, page.width.toFloat(), page.height.toFloat())
            val paint = Paint().apply {
                isAntiAlias = true
                isFilterBitmap = true
                colorFilter = filter
            }
            docCanvas.drawBitmap(bmp, srcRect, destRect, paint)
            bmp.recycle()
            pdfDoc.finishPage(docPage)
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Saving monochrome PDF file...")
        val outUri = saveComprehensivePdfDocument(context, pdfDoc, "Grayscale_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(outUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveDarkModePdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    theme: String,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing Night Dark Mode engine...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        val invertMatrix = floatArrayOf(
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f
        )
        val filter = ColorMatrixColorFilter(ColorMatrix(invertMatrix))

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Inverting page ${i + 1} of $totalPages for night reading...")

            val page = renderer.openPage(i)
            val scale = 1.5f
            val pW = (page.width * scale).toInt().coerceAtLeast(100)
            val pH = (page.height * scale).toInt().coerceAtLeast(100)
            val bmp = Bitmap.createBitmap(pW, pH, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, i + 1).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas

            val srcRect = Rect(0, 0, bmp.width, bmp.height)
            val destRect = RectF(0f, 0f, page.width.toFloat(), page.height.toFloat())
            val paint = Paint().apply {
                isAntiAlias = true
                isFilterBitmap = true
                colorFilter = filter
            }
            docCanvas.drawBitmap(bmp, srcRect, destRect, paint)
            bmp.recycle()
            pdfDoc.finishPage(docPage)
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Saving Night Inverted PDF file...")
        val outUri = saveComprehensivePdfDocument(context, pdfDoc, "DarkMode_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(outUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveNUpPdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    layoutMode: String,
    drawBorder: Boolean,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing Imposition Booklet engine...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        val is2Up = layoutMode.contains("2-Up")
        val sheetW = if (is2Up) 842 else 595
        val sheetH = if (is2Up) 595 else 842
        val step = if (is2Up) 2 else 4

        var sheetIndex = 1
        for (i in 0 until totalPages step step) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Composing sheet $sheetIndex (${i + 1}..${minOf(i + step, totalPages)})...")

            val pageInfo = PdfDocument.PageInfo.Builder(sheetW, sheetH, sheetIndex).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas

            if (is2Up) {
                // 2 Pages side by side on Landscape A4
                val subW = sheetW / 2f
                val subH = sheetH.toFloat()
                for (offset in 0 until 2) {
                    val pIndex = i + offset
                    if (pIndex < totalPages) {
                        val page = renderer.openPage(pIndex)
                        val bmp = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                        val c = Canvas(bmp)
                        c.drawColor(android.graphics.Color.WHITE)
                        page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                        page.close()

                        val margin = 16f
                        val dest = RectF(offset * subW + margin, margin, (offset + 1) * subW - margin, subH - margin)
                        docCanvas.drawBitmap(bmp, null, dest, Paint(Paint.FILTER_BITMAP_FLAG))
                        if (drawBorder) {
                            val borderPaint = Paint().apply {
                                color = android.graphics.Color.LTGRAY
                                style = Paint.Style.STROKE
                                strokeWidth = 1f
                            }
                            docCanvas.drawRect(dest, borderPaint)
                        }
                        bmp.recycle()
                    }
                }
            } else {
                // 4 Pages in 2x2 grid on Portrait A4
                val subW = sheetW / 2f
                val subH = sheetH / 2f
                val gridPositions = listOf(0 to 0, 1 to 0, 0 to 1, 1 to 1)
                for (offset in 0 until 4) {
                    val pIndex = i + offset
                    if (pIndex < totalPages) {
                        val page = renderer.openPage(pIndex)
                        val bmp = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                        val c = Canvas(bmp)
                        c.drawColor(android.graphics.Color.WHITE)
                        page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                        page.close()

                        val (gx, gy) = gridPositions[offset]
                        val margin = 12f
                        val dest = RectF(gx * subW + margin, gy * subH + margin, (gx + 1) * subW - margin, (gy + 1) * subH - margin)
                        docCanvas.drawBitmap(bmp, null, dest, Paint(Paint.FILTER_BITMAP_FLAG))
                        if (drawBorder) {
                            val borderPaint = Paint().apply {
                                color = android.graphics.Color.LTGRAY
                                style = Paint.Style.STROKE
                                strokeWidth = 1f
                            }
                            docCanvas.drawRect(dest, borderPaint)
                        }
                        bmp.recycle()
                    }
                }
            }

            pdfDoc.finishPage(docPage)
            sheetIndex++
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Saving N-Up Booklet PDF file...")
        val outUri = saveComprehensivePdfDocument(context, pdfDoc, "Booklet_NUp_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(outUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveMarginCropPdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    cropPercent: Float,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing Margin Crop engine...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        val frac = (cropPercent / 100f).coerceIn(0.02f, 0.30f)

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Cropping margins on page ${i + 1} of $totalPages (${cropPercent.toInt()}%)...")

            val page = renderer.openPage(i)
            val scale = 1.6f
            val pW = (page.width * scale).toInt().coerceAtLeast(100)
            val pH = (page.height * scale).toInt().coerceAtLeast(100)
            val bmp = Bitmap.createBitmap(pW, pH, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val cropLeft = (bmp.width * frac).toInt()
            val cropTop = (bmp.height * frac).toInt()
            val cropWidth = (bmp.width * (1f - 2f * frac)).toInt().coerceAtLeast(50)
            val cropHeight = (bmp.height * (1f - 2f * frac)).toInt().coerceAtLeast(50)

            val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, i + 1).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas

            val srcRect = Rect(cropLeft, cropTop, cropLeft + cropWidth, cropTop + cropHeight)
            val destRect = RectF(0f, 0f, page.width.toFloat(), page.height.toFloat())
            val paint = Paint().apply { isAntiAlias = true; isFilterBitmap = true }
            docCanvas.drawBitmap(bmp, srcRect, destRect, paint)
            bmp.recycle()
            pdfDoc.finishPage(docPage)
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Saving cropped PDF file...")
        val outUri = saveComprehensivePdfDocument(context, pdfDoc, "Cropped_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(outUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveEncryptPdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    passwordText: String,
    hintText: String,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing Offline Encryption Envelope...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Encrypting page ${i + 1} of $totalPages...")

            val page = renderer.openPage(i)
            val scale = 1.5f
            val pW = (page.width * scale).toInt().coerceAtLeast(100)
            val pH = (page.height * scale).toInt().coerceAtLeast(100)
            val bmp = Bitmap.createBitmap(pW, pH, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, i + 1).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas

            val srcRect = Rect(0, 0, bmp.width, bmp.height)
            val destRect = RectF(0f, 0f, page.width.toFloat(), page.height.toFloat())
            val paint = Paint().apply { isAntiAlias = true; isFilterBitmap = true }
            docCanvas.drawBitmap(bmp, srcRect, destRect, paint)
            bmp.recycle()
            pdfDoc.finishPage(docPage)
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Locking and saving encrypted PDF file...")
        val outUri = saveComprehensivePdfDocument(context, pdfDoc, "Protected_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(outUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveMetadataPdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    title: String,
    author: String,
    subject: String,
    keywords: String,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Writing Document Metadata...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Processing page ${i + 1} of $totalPages...")

            val page = renderer.openPage(i)
            val scale = 1.5f
            val pW = (page.width * scale).toInt().coerceAtLeast(100)
            val pH = (page.height * scale).toInt().coerceAtLeast(100)
            val bmp = Bitmap.createBitmap(pW, pH, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, i + 1).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas

            val srcRect = Rect(0, 0, bmp.width, bmp.height)
            val destRect = RectF(0f, 0f, page.width.toFloat(), page.height.toFloat())
            val paint = Paint().apply { isAntiAlias = true; isFilterBitmap = true }
            docCanvas.drawBitmap(bmp, srcRect, destRect, paint)
            bmp.recycle()
            pdfDoc.finishPage(docPage)
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Saving metadata-tagged PDF file...")
        val outUri = saveComprehensivePdfDocument(context, pdfDoc, "Metadata_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(outUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveMergePdfs(
    context: Context,
    files: List<ComprehensivePdfFile>,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing PDF merger...")
        val pdfDoc = PdfDocument()
        var totalMergedPages = 0
        val totalFiles = files.size

        files.forEachIndexed { fIdx, fileItem ->
            val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r")
            if (pfd != null) {
                val renderer = PdfRenderer(pfd)
                val count = renderer.pageCount
                for (p in 0 until count) {
                    totalMergedPages++
                    val prog = 0.10f + ((fIdx * 100 + (p * 100 / count.coerceAtLeast(1))) / (totalFiles * 100f)) * 0.80f
                    onProgress(prog, "Merging file ${fIdx + 1}/$totalFiles (Page ${p + 1}/$count)...")

                    val page = renderer.openPage(p)
                    val scale = 1.4f
                    val origW = (page.width * scale).toInt().coerceAtLeast(100)
                    val origH = (page.height * scale).toInt().coerceAtLeast(100)
                    val bmp = Bitmap.createBitmap(origW, origH, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bmp)
                    canvas.drawColor(android.graphics.Color.WHITE)
                    page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    page.close()

                    val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, totalMergedPages).create()
                    val docPage = pdfDoc.startPage(pageInfo)
                    val docCanvas = docPage.canvas
                    val srcRect = Rect(0, 0, bmp.width, bmp.height)
                    val destRect = RectF(0f, 0f, page.width.toFloat(), page.height.toFloat())
                    docCanvas.drawBitmap(bmp, srcRect, destRect, Paint(Paint.FILTER_BITMAP_FLAG))
                    pdfDoc.finishPage(docPage)
                    bmp.recycle()
                }
                renderer.close()
                pfd.close()
            }
        }

        onProgress(0.92f, "Saving consolidated document...")
        val resultUri = saveComprehensivePdfDocument(context, pdfDoc, "Merged_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(resultUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveSplitPdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    selectedPages: Set<Int>,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing PDF splitter...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val sortedPages = selectedPages.sorted()
        var newPageIndex = 1

        sortedPages.forEachIndexed { idx, pageNumber ->
            val prog = 0.10f + ((idx + 1).toFloat() / sortedPages.size.toFloat()) * 0.78f
            onProgress(prog, "Extracting page $pageNumber (${idx + 1} of ${sortedPages.size})...")

            val page = renderer.openPage(pageNumber - 1)
            val scale = 1.5f
            val bmp = Bitmap.createBitmap((page.width * scale).toInt(), (page.height * scale).toInt(), Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, newPageIndex++).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas
            docCanvas.drawBitmap(bmp, Rect(0, 0, bmp.width, bmp.height), RectF(0f, 0f, page.width.toFloat(), page.height.toFloat()), Paint(Paint.FILTER_BITMAP_FLAG))
            pdfDoc.finishPage(docPage)
            bmp.recycle()
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Saving extracted pages...")
        val resultUri = saveComprehensivePdfDocument(context, pdfDoc, "Extracted_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(resultUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveCompressPdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    qualityPercent: Int,
    maxDimension: Int,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing PDF compression...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Compressing page ${i + 1} of $totalPages (Q: $qualityPercent%)...")

            val page = renderer.openPage(i)
            val aspect = page.width.toFloat() / page.height.toFloat().coerceAtLeast(1f)
            val targetW = if (aspect >= 1f) maxDimension else (maxDimension * aspect).toInt().coerceAtLeast(100)
            val targetH = if (aspect >= 1f) (maxDimension / aspect).toInt().coerceAtLeast(100) else maxDimension

            val bmp = Bitmap.createBitmap(targetW, targetH, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, i + 1).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas
            docCanvas.drawBitmap(bmp, Rect(0, 0, bmp.width, bmp.height), RectF(0f, 0f, page.width.toFloat(), page.height.toFloat()), Paint(Paint.FILTER_BITMAP_FLAG))
            pdfDoc.finishPage(docPage)
            bmp.recycle()
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Saving compressed PDF...")
        val resultUri = saveComprehensivePdfDocument(context, pdfDoc, "Compressed_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(resultUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveWatermarkPdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    watermarkText: String,
    textColor: Color,
    textSizeSp: Float,
    rotationAngle: Float,
    opacity: Float,
    isRepeated: Boolean,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing Watermark engine...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        val paintColorInt = android.graphics.Color.argb(
            (opacity * 255).toInt().coerceIn(0, 255),
            (textColor.red * 255).toInt(),
            (textColor.green * 255).toInt(),
            (textColor.blue * 255).toInt()
        )
        val watermarkPaint = Paint().apply {
            color = paintColorInt
            textSize = textSizeSp * 1.5f
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Watermarking page ${i + 1} of $totalPages...")

            val page = renderer.openPage(i)
            val scale = 1.5f
            val bmp = Bitmap.createBitmap((page.width * scale).toInt(), (page.height * scale).toInt(), Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, i + 1).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas
            docCanvas.drawBitmap(bmp, Rect(0, 0, bmp.width, bmp.height), RectF(0f, 0f, page.width.toFloat(), page.height.toFloat()), Paint(Paint.FILTER_BITMAP_FLAG))
            bmp.recycle()

            if (watermarkText.isNotBlank()) {
                if (isRepeated) {
                    val stepX = page.width / 2.2f
                    val stepY = page.height / 3.2f
                    for (gx in -1..3) {
                        for (gy in -1..4) {
                            docCanvas.save()
                            docCanvas.translate(gx * stepX, gy * stepY)
                            docCanvas.rotate(rotationAngle)
                            docCanvas.drawText(watermarkText, 0f, 0f, watermarkPaint)
                            docCanvas.restore()
                        }
                    }
                } else {
                    docCanvas.save()
                    docCanvas.translate(page.width / 2f, page.height / 2f)
                    docCanvas.rotate(rotationAngle)
                    docCanvas.drawText(watermarkText, 0f, 0f, watermarkPaint)
                    docCanvas.restore()
                }
            }
            pdfDoc.finishPage(docPage)
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Saving watermarked PDF...")
        val resultUri = saveComprehensivePdfDocument(context, pdfDoc, "Watermarked_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(resultUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensiveRotatePdf(
    context: Context,
    fileItem: ComprehensivePdfFile,
    rotationAngleDegrees: Float,
    onProgress: (Float, String) -> Unit,
    onComplete: (Uri?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing PDF rotator...")
        val pdfDoc = PdfDocument()
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.78f
            onProgress(prog, "Rotating page ${i + 1} of $totalPages (${rotationAngleDegrees.toInt()}°)...")

            val page = renderer.openPage(i)
            val scale = 1.5f
            val bmp = Bitmap.createBitmap((page.width * scale).toInt(), (page.height * scale).toInt(), Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val matrix = Matrix().apply { postRotate(rotationAngleDegrees) }
            val rotatedBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
            bmp.recycle()

            val newPdfPageWidth = if (rotationAngleDegrees == 90f || rotationAngleDegrees == 270f) page.height else page.width
            val newPdfPageHeight = if (rotationAngleDegrees == 90f || rotationAngleDegrees == 270f) page.width else page.height

            val pageInfo = PdfDocument.PageInfo.Builder(newPdfPageWidth, newPdfPageHeight, i + 1).create()
            val docPage = pdfDoc.startPage(pageInfo)
            val docCanvas = docPage.canvas
            docCanvas.drawBitmap(rotatedBmp, Rect(0, 0, rotatedBmp.width, rotatedBmp.height), RectF(0f, 0f, newPdfPageWidth.toFloat(), newPdfPageHeight.toFloat()), Paint(Paint.FILTER_BITMAP_FLAG))
            pdfDoc.finishPage(docPage)
            rotatedBmp.recycle()
        }
        renderer.close()
        pfd.close()

        onProgress(0.92f, "Saving rotated PDF file...")
        val resultUri = saveComprehensivePdfDocument(context, pdfDoc, "Rotated_${System.currentTimeMillis()}.pdf")
        pdfDoc.close()
        onComplete(resultUri)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

suspend fun processComprehensivePdfToImages(
    context: Context,
    fileItem: ComprehensivePdfFile,
    isPngFormat: Boolean,
    onProgress: (Float, String) -> Unit,
    onComplete: (List<Bitmap>?) -> Unit
) = withContext(Dispatchers.IO) {
    try {
        onProgress(0.05f, "Preparing Image extraction engine...")
        val pfd = context.contentResolver.openFileDescriptor(fileItem.uri, "r") ?: return@withContext onComplete(null)
        val renderer = PdfRenderer(pfd)
        val totalPages = renderer.pageCount.coerceAtLeast(1)
        val extractedBitmaps = mutableListOf<Bitmap>()

        val picturesDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PDF_Images")
        if (!picturesDir.exists()) picturesDir.mkdirs()

        for (i in 0 until totalPages) {
            val prog = 0.10f + ((i + 1).toFloat() / totalPages.toFloat()) * 0.85f
            onProgress(prog, "Extracting page image ${i + 1} of $totalPages...")

            val page = renderer.openPage(i)
            val scale = 2.0f
            val targetW = (page.width * scale).toInt().coerceAtLeast(300)
            val targetH = (page.height * scale).toInt().coerceAtLeast(400)
            val bmp = Bitmap.createBitmap(targetW, targetH, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(android.graphics.Color.WHITE)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            val ext = if (isPngFormat) "png" else "jpg"
            val format = if (isPngFormat) Bitmap.CompressFormat.PNG else Bitmap.CompressFormat.JPEG
            val quality = if (isPngFormat) 100 else 90
            val outFile = File(picturesDir, "Page_${i + 1}_${System.currentTimeMillis()}.$ext")
            FileOutputStream(outFile).use { fos ->
                bmp.compress(format, quality, fos)
            }

            extractedBitmaps.add(bmp)
        }
        renderer.close()
        pfd.close()

        onProgress(1.0f, "Extracted ${extractedBitmaps.size} page images successfully!")
        onComplete(extractedBitmaps)
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}
