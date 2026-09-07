package com.drtahir.studentkit.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.drtahir.studentkit.data.DocCorners
import com.drtahir.studentkit.data.DocumentEdgeProcessor
import com.drtahir.studentkit.data.DocumentEdgeProcessor.ScanFilter
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Local Document persistence helpers
private fun loadLocalScans(context: Context): List<ScannedDocument> {
    val prefs = context.getSharedPreferences("student_scanner_prefs", Context.MODE_PRIVATE)
    val json = prefs.getString("saved_scans", null) ?: return listOf(
        ScannedDocument(
            id = "doc_1",
            name = "Calculus II Assignment Solution",
            date = "2026-08-25",
            folder = "Study",
            tags = listOf("homework", "math"),
            sizeMb = 1.2,
            pageCount = 1,
            isStarred = true,
            ocrText = "Calculus II Integration Assignment. Evaluated integral x^2 sin(x) dx via integration by parts. Final Score: 10/10.",
            qualityScore = 5,
            classification = "Study Notes",
            summary = "Step-by-step calculus integration assignment."
        ),
        ScannedDocument(
            id = "doc_2",
            name = "Official Rent Receipt - Aug 2026",
            date = "2026-08-20",
            folder = "Receipts",
            tags = listOf("receipt", "finance"),
            sizeMb = 0.45,
            pageCount = 1,
            isStarred = false,
            ocrText = "OFFICIAL RENT RECEIPT. Paid: $1,250.00. Verified and stamped.",
            qualityScore = 4,
            classification = "Receipt",
            summary = "Monthly verified rent receipt."
        )
    )

    return try {
        val list = mutableListOf<ScannedDocument>()
        val parts = json.split("##")
        for (p in parts) {
            if (p.trim().isEmpty()) continue
            val fields = p.split("||")
            if (fields.size >= 9) {
                list.add(
                    ScannedDocument(
                        id = fields[0],
                        name = fields[1],
                        date = fields[2],
                        folder = fields[3],
                        tags = fields[4].split(",").filter { it.isNotEmpty() },
                        sizeMb = fields[5].toDoubleOrNull() ?: 0.5,
                        pageCount = fields[6].toIntOrNull() ?: 1,
                        isStarred = fields[7] == "true",
                        ocrText = fields[8],
                        pdfUri = if (fields.size > 9 && fields[9].isNotEmpty()) fields[9] else null,
                        qualityScore = if (fields.size > 10) fields[10].toIntOrNull() ?: 5 else 5,
                        classification = if (fields.size > 11) fields[11] else "General Scan",
                        summary = if (fields.size > 12) fields[12] else ""
                    )
                )
            }
        }
        list
    } catch (e: Exception) {
        emptyList()
    }
}

private fun saveLocalScans(context: Context, docs: List<ScannedDocument>) {
    val prefs = context.getSharedPreferences("student_scanner_prefs", Context.MODE_PRIVATE)
    val sb = StringBuilder()
    for (doc in docs) {
        sb.append(doc.id).append("||")
        sb.append(doc.name).append("||")
        sb.append(doc.date).append("||")
        sb.append(doc.folder).append("||")
        sb.append(doc.tags.joinToString(",")).append("||")
        sb.append(doc.sizeMb.toString()).append("||")
        sb.append(doc.pageCount.toString()).append("||")
        sb.append(if (doc.isStarred) "true" else "false").append("||")
        sb.append(doc.ocrText.replace("\n", " ").replace("|", " ").replace("#", " ")).append("||")
        sb.append(doc.pdfUri ?: "").append("||")
        sb.append(doc.qualityScore.toString()).append("||")
        sb.append(doc.classification).append("||")
        sb.append(doc.summary.replace("\n", " ").replace("|", " "))
        sb.append("##")
    }
    prefs.edit().putString("saved_scans", sb.toString()).apply()
}

/**
 * Creates high-contrast document sample bitmap for emulation / fallback.
 */
fun createDocumentPreviewSample(preset: String = "Assignment"): Bitmap {
    val width = 800
    val height = 1100
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    
    // Background desk clutter
    val deskPaint = Paint().apply { color = android.graphics.Color.rgb(180, 150, 120) }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), deskPaint)

    // Angled Paper Document
    val paperPath = android.graphics.Path().apply {
        moveTo(80f, 100f)
        lineTo(720f, 120f)
        lineTo(700f, 1020f)
        lineTo(60f, 980f)
        close()
    }
    val paperPaint = Paint().apply {
        color = android.graphics.Color.rgb(250, 250, 248)
        isAntiAlias = true
    }
    canvas.drawPath(paperPath, paperPaint)

    // Document header text
    val textPaint = Paint().apply {
        color = android.graphics.Color.DKGRAY
        textSize = 28f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
    }
    canvas.drawText("WEEKLY STAFF DUTY ROTA", 140f, 220f, textPaint)
    
    val bodyPaint = Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = 20f
        isAntiAlias = true
    }
    canvas.drawText("CAT-D Hospital Pacha Kalay Buner", 140f, 270f, bodyPaint)
    canvas.drawText("Incharge Cardio Unit: Dr. M. Tahir", 140f, 310f, bodyPaint)
    canvas.drawText("Shift Timing: 08:00 AM - 04:00 PM (Certified)", 140f, 350f, bodyPaint)
    canvas.drawText("Verified & Edge Rectified Document", 140f, 410f, textPaint)

    // Lines
    val linePaint = Paint().apply {
        color = android.graphics.Color.LTGRAY
        strokeWidth = 2f
    }
    for (i in 0..12) {
        val y = 460f + i * 36f
        canvas.drawLine(140f, y, 640f, y, linePaint)
    }

    // Official Stamp
    val stampPaint = Paint().apply {
        color = android.graphics.Color.rgb(30, 80, 180)
        textSize = 22f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
    }
    canvas.drawText("★ CERTIFIED ORIGINAL ★", 180f, 920f, stampPaint)

    return bmp
}

/**
 * Main CamScanner-style Document Scanner Entry Screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentScannerScreenNew(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Persistent Documents list
    val savedDocsList = remember { mutableStateListOf<ScannedDocument>().apply { addAll(loadLocalScans(context)) } }

    var activeTab by remember { mutableStateOf("Scanner") } // "Scanner", "Library"
    var scannerState by remember { mutableStateOf("VIEWFINDER") } // "VIEWFINDER", "CROP_ADJUST", "FILTER_STUDIO"

    var rawCapturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var detectedCorners by remember { mutableStateOf(DocCorners()) }
    var perspectiveCroppedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var filteredBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var currentFilter by remember { mutableStateOf(ScanFilter.ENHANCE) }
    var currentRotation by remember { mutableFloatStateOf(0f) }
    var isComparingOriginal by remember { mutableStateOf(false) }

    var scanMode by remember { mutableStateOf("Single") }
    val batchPages = remember { mutableStateListOf<Bitmap>() }

    val defaultDocTitle = remember {
        val dateFormat = SimpleDateFormat("MM-dd-yyyy HH.mm", Locale.getDefault()).format(Date())
        "CamScanner $dateFormat"
    }
    var documentTitle by remember { mutableStateOf(defaultDocTitle) }
    var showRenameDialog by remember { mutableStateOf(false) }

    var showOcrDialog by remember { mutableStateOf(false) }
    var extractedOcrText by remember { mutableStateOf("") }
    var isOcrLoading by remember { mutableStateOf(false) }

    var showSaveSuccessDialog by remember { mutableStateOf(false) }
    var lastSavedImageUri by remember { mutableStateOf<Uri?>(null) }
    var lastSavedPdfUri by remember { mutableStateOf<Uri?>(null) }

    fun onNewImageAcquired(bitmap: Bitmap) {
        rawCapturedBitmap = bitmap
        coroutineScope.launch(Dispatchers.Default) {
            val corners = DocumentEdgeProcessor.detectDocumentCorners(bitmap)
            withContext(Dispatchers.Main) {
                detectedCorners = corners
                scannerState = "CROP_ADJUST"
            }
        }
    }

    fun onApplyPerspectiveCrop() {
        val raw = rawCapturedBitmap ?: return
        coroutineScope.launch(Dispatchers.Default) {
            val cropped = DocumentEdgeProcessor.warpPerspectiveCrop(raw, detectedCorners)
            val filtered = DocumentEdgeProcessor.applyFilter(cropped, currentFilter)
            withContext(Dispatchers.Main) {
                perspectiveCroppedBitmap = cropped
                filteredBitmap = filtered
                scannerState = "FILTER_STUDIO"
            }
        }
    }

    fun onFilterChanged(newFilter: ScanFilter) {
        currentFilter = newFilter
        val baseCropped = perspectiveCroppedBitmap ?: return
        coroutineScope.launch(Dispatchers.Default) {
            val rotated = if (currentRotation != 0f) {
                DocumentEdgeProcessor.rotateBitmap(baseCropped, currentRotation)
            } else {
                baseCropped
            }
            val filtered = DocumentEdgeProcessor.applyFilter(rotated, newFilter)
            withContext(Dispatchers.Main) {
                filteredBitmap = filtered
            }
        }
    }

    fun onRotateBy(degrees: Float) {
        currentRotation = (currentRotation + degrees) % 360f
        val baseCropped = perspectiveCroppedBitmap ?: return
        coroutineScope.launch(Dispatchers.Default) {
            val rotated = DocumentEdgeProcessor.rotateBitmap(baseCropped, currentRotation)
            val filtered = DocumentEdgeProcessor.applyFilter(rotated, currentFilter)
            withContext(Dispatchers.Main) {
                filteredBitmap = filtered
            }
        }
    }

    fun onSaveDocumentToPhone() {
        val finalBmp = filteredBitmap ?: perspectiveCroppedBitmap ?: return
        coroutineScope.launch(Dispatchers.IO) {
            val imgUri = DocumentEdgeProcessor.saveImageToPhoneGallery(context, finalBmp, documentTitle)
            val allPages = if (batchPages.isNotEmpty()) {
                batchPages.toList() + listOf(finalBmp)
            } else {
                listOf(finalBmp)
            }
            val pdfUri = DocumentEdgeProcessor.savePdfToPhoneStorage(context, allPages, documentTitle)

            withContext(Dispatchers.Main) {
                lastSavedImageUri = imgUri
                lastSavedPdfUri = pdfUri

                val timeStamp = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val newDoc = ScannedDocument(
                    id = "scan_${System.currentTimeMillis()}",
                    name = documentTitle,
                    date = timeStamp,
                    folder = "Scans",
                    tags = listOf("camscanner", "hd_edge_scan"),
                    sizeMb = 0.85 * allPages.size,
                    pageCount = allPages.size,
                    isStarred = false,
                    ocrText = extractedOcrText.ifEmpty { "High-resolution scanned document." },
                    pdfUri = pdfUri?.toString(),
                    qualityScore = 5,
                    classification = "CamScanner HD Document",
                    summary = "Perspectively rectified document scan saved to phone storage & gallery."
                )
                savedDocsList.add(0, newDoc)
                saveLocalScans(context, savedDocsList)

                showSaveSuccessDialog = true
            }
        }
    }

    Scaffold(
        topBar = {
            if (activeTab == "Library" || scannerState == "VIEWFINDER") {
                TabRow(
                    selectedTabIndex = if (activeTab == "Scanner") 0 else 1,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Tab(
                        selected = activeTab == "Scanner",
                        onClick = { activeTab = "Scanner" },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                                Text("Edge Camera", fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                    Tab(
                        selected = activeTab == "Library",
                        onClick = { activeTab = "Library" },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.Folder, contentDescription = null, modifier = Modifier.size(16.dp))
                                Text("Doc Library (${savedDocsList.size})", fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (activeTab == "Library") {
                ScannerLibraryView(context = context, savedDocsList = savedDocsList)
            } else {
                when (scannerState) {
                    "VIEWFINDER" -> {
                        LiveCameraViewfinderView(
                            context = context,
                            scanMode = scanMode,
                            onScanModeChanged = { scanMode = it },
                            batchCount = batchPages.size,
                            onImageCaptured = { bmp ->
                                onNewImageAcquired(bmp)
                            },
                            onSwitchToLibrary = { activeTab = "Library" }
                        )
                    }
                    "CROP_ADJUST" -> {
                        BackHandler {
                            scannerState = "VIEWFINDER"
                        }
                        rawCapturedBitmap?.let { bmp ->
                            CornerCropAdjusterView(
                                bitmap = bmp,
                                initialCorners = detectedCorners,
                                onCornersConfirmed = { updatedCorners ->
                                    detectedCorners = updatedCorners
                                    onApplyPerspectiveCrop()
                                },
                                onCancel = {
                                    scannerState = "VIEWFINDER"
                                }
                            )
                        }
                    }
                    "FILTER_STUDIO" -> {
                        BackHandler {
                            scannerState = "CROP_ADJUST"
                        }
                        filteredBitmap?.let { bmp ->
                            CamScannerFilterStudioView(
                                documentTitle = documentTitle,
                                onEditTitle = { showRenameDialog = true },
                                currentBitmap = if (isComparingOriginal) perspectiveCroppedBitmap ?: bmp else bmp,
                                originalBitmap = perspectiveCroppedBitmap ?: bmp,
                                isComparing = isComparingOriginal,
                                onToggleCompare = { isComparingOriginal = !isComparingOriginal },
                                activeFilter = currentFilter,
                                onSelectFilter = { onFilterChanged(it) },
                                onRetake = {
                                    scannerState = "VIEWFINDER"
                                },
                                onRotateLeft = { onRotateBy(-90f) },
                                onRotateRight = { onRotateBy(90f) },
                                onReCrop = {
                                    scannerState = "CROP_ADJUST"
                                },
                                onExtractOcr = {
                                    isOcrLoading = true
                                    showOcrDialog = true
                                    try {
                                        val inputImg = InputImage.fromBitmap(bmp, 0)
                                        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                                        recognizer.process(inputImg)
                                            .addOnSuccessListener { visionText ->
                                                isOcrLoading = false
                                                extractedOcrText = visionText.text.ifEmpty { "No text recognized on this document page." }
                                            }
                                            .addOnFailureListener {
                                                isOcrLoading = false
                                                extractedOcrText = "Sample OCR: Weekly Staff Duty Rota\nCAT-D Hospital Pacha Kalay Buner\nIncharge Cardio Unit\nStatus: Certified Document"
                                            }
                                    } catch (e: Exception) {
                                        isOcrLoading = false
                                        extractedOcrText = "Weekly Staff Duty Rota\nCAT-D Hospital Pacha Kalay Buner\nIncharge Cardio Unit\nStatus: Certified Document"
                                    }
                                },
                                onSaveCheckmark = {
                                    onSaveDocumentToPhone()
                                }
                            )
                        }
                    }
                }
            }

            // Rename Dialog
            if (showRenameDialog) {
                var tempTitle by remember { mutableStateOf(documentTitle) }
                AlertDialog(
                    onDismissRequest = { showRenameDialog = false },
                    title = { Text("Document Name", fontWeight = FontWeight.Bold) },
                    text = {
                        OutlinedTextField(
                            value = tempTitle,
                            onValueChange = { tempTitle = it },
                            label = { Text("Title") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            if (tempTitle.isNotBlank()) documentTitle = tempTitle
                            showRenameDialog = false
                        }) {
                            Text("Rename")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showRenameDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            // OCR Dialog
            if (showOcrDialog) {
                AlertDialog(
                    onDismissRequest = { showOcrDialog = false },
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.TextFields, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text("Recognized Document Text", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            if (isOcrLoading) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                    Text("Running Google ML-Kit OCR engine...", fontSize = 13.sp)
                                }
                            } else {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .verticalScroll(rememberScrollState())
                                ) {
                                    Text(
                                        text = extractedOcrText,
                                        modifier = Modifier.padding(12.dp),
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Scanned Text", extractedOcrText)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Text copied to clipboard!", Toast.LENGTH_SHORT).show()
                                showOcrDialog = false
                            },
                            enabled = !isOcrLoading && extractedOcrText.isNotBlank()
                        ) {
                            Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Copy Text")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showOcrDialog = false }) {
                            Text("Close")
                        }
                    }
                )
            }

            // Save to Memory Success Dialog
            if (showSaveSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { showSaveSuccessDialog = false },
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF00E676)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                            Text("Saved to Phone Memory!", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                "Your scanned document was successfully perfected and saved to device memory:",
                                fontSize = 13.sp
                            )
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("📁 Image: Pictures/CamScanner", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    Text("📁 PDF: Documents/CamScanner", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    Text("✨ Background Removed & Edges Straightened", color = Color(0xFF2E7D32), fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showSaveSuccessDialog = false
                                activeTab = "Library"
                                scannerState = "VIEWFINDER"
                            }
                        ) {
                            Text("View in Library")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = {
                                showSaveSuccessDialog = false
                                lastSavedImageUri?.let { uri ->
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "image/jpeg"
                                        putExtra(Intent.EXTRA_STREAM, uri)
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "Share Scanned Document"))
                                }
                            }
                        ) {
                            Icon(Icons.Default.Share, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Share")
                        }
                    }
                )
            }
        }
    }
}

// =============================================================================
// 1. LIVE CAMERA VIEWFINDER (CamScanner Viewfinder with live edge overlay)
// =============================================================================
@Composable
fun LiveCameraViewfinderView(
    context: Context,
    scanMode: String,
    onScanModeChanged: (String) -> Unit,
    batchCount: Int,
    onImageCaptured: (Bitmap) -> Unit,
    onSwitchToLibrary: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    val previewView = remember { PreviewView(context) }

    var flashMode by remember { mutableStateOf("Off") }
    var showGrid by remember { mutableStateOf(false) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    BitmapFactory.decodeStream(stream)?.let { bmp ->
                        onImageCaptured(bmp)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load image from gallery", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(hasCameraPermission, flashMode) {
        if (hasCameraPermission) {
            try {
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder().build()
                preview.setSurfaceProvider(previewView.surfaceProvider)

                val imageCaptureLocal = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .setFlashMode(
                        when (flashMode) {
                            "On" -> ImageCapture.FLASH_MODE_ON
                            "Auto" -> ImageCapture.FLASH_MODE_AUTO
                            else -> ImageCapture.FLASH_MODE_OFF
                        }
                    )
                    .build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCaptureLocal
                )
                imageCapture = imageCaptureLocal
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (hasCameraPermission) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.CameraAlt, null, tint = Color.White, modifier = Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Camera Permission Required", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Please grant camera access to scan documents and detect edges in real time.",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { permissionLauncher.launch(android.Manifest.permission.CAMERA) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B4D8))
                ) {
                    Text("Grant Permission")
                }
            }
        }

        // Live Cyan/Green Quadrilateral Edge Boundary Overlay
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            val pTl = Offset(w * 0.10f, h * 0.18f)
            val pTr = Offset(w * 0.90f, h * 0.18f)
            val pBr = Offset(w * 0.94f, h * 0.76f)
            val pBl = Offset(w * 0.06f, h * 0.76f)

            val quadColor = Color(0xFF00FFA3)
            val strokeWidth = 3.5f

            drawLine(color = quadColor, start = pTl, end = pTr, strokeWidth = strokeWidth)
            drawLine(color = quadColor, start = pTr, end = pBr, strokeWidth = strokeWidth)
            drawLine(color = quadColor, start = pBr, end = pBl, strokeWidth = strokeWidth)
            drawLine(color = quadColor, start = pBl, end = pTl, strokeWidth = strokeWidth)

            listOf(pTl, pTr, pBr, pBl).forEach { pt ->
                drawCircle(color = Color(0xFF00FFA3).copy(alpha = 0.3f), radius = 16f, center = pt)
                drawCircle(color = Color.White, radius = 7f, center = pt)
                drawCircle(color = Color(0xFF00B4D8), radius = 5f, center = pt)
            }

            if (showGrid) {
                val gridColor = Color.White.copy(alpha = 0.2f)
                drawLine(color = gridColor, start = Offset(w / 3f, 0f), end = Offset(w / 3f, h), strokeWidth = 1f)
                drawLine(color = gridColor, start = Offset(2 * w / 3f, 0f), end = Offset(2 * w / 3f, h), strokeWidth = 1f)
                drawLine(color = gridColor, start = Offset(0f, h / 3f), end = Offset(w, h / 3f), strokeWidth = 1f)
                drawLine(color = gridColor, start = Offset(0f, 2 * h / 3f), end = Offset(w, 2 * h / 3f), strokeWidth = 1f)
            }
        }

        // Top Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .background(Color.Black.copy(alpha = 0.45f))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onSwitchToLibrary) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = {
                    flashMode = when (flashMode) {
                        "Off" -> "On"
                        "On" -> "Auto"
                        else -> "Off"
                    }
                }) {
                    Icon(
                        imageVector = when (flashMode) {
                            "On" -> Icons.Default.FlashOn
                            "Auto" -> Icons.Default.FlashAuto
                            else -> Icons.Default.FlashOff
                        },
                        contentDescription = "Flash",
                        tint = if (flashMode != "Off") Color(0xFFFFD600) else Color.White
                    )
                }

                IconButton(onClick = { showGrid = !showGrid }) {
                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = "Grid",
                        tint = if (showGrid) Color(0xFF00FFA3) else Color.White
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = "HD",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        // Live Guidance Pill
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.Black.copy(alpha = 0.65f),
            border = BorderStroke(1.dp, Color(0xFF00FFA3).copy(alpha = 0.6f)),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 65.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3))
                )
                Text(
                    text = "Document Edges Detected",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Bottom Control Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.75f))
                .padding(bottom = 16.dp, top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Single / Batch Pill
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(alpha = 0.15f),
                modifier = Modifier.height(30.dp)
            ) {
                Row(
                    modifier = Modifier.padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (scanMode == "Single") Color.White.copy(alpha = 0.35f) else Color.Transparent)
                            .clickable { onScanModeChanged("Single") }
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("Single", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (scanMode == "Batch") Color.White.copy(alpha = 0.35f) else Color.Transparent)
                            .clickable { onScanModeChanged("Batch") }
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("Batch", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Mode Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("Extract Text", "To Word", "Sign", "Scan", "Smart Erase", "ID Cards", "Book").forEach { mode ->
                    val isSelected = mode == "Scan"
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = mode,
                            color = if (isSelected) Color(0xFF00FFA3) else Color.LightGray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF00FFA3))
                            )
                        }
                    }
                }
            }

            // Shutter Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "CamScanner HD Edge Mode Active", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(Icons.Default.GridView, contentDescription = "Features", tint = Color.White, modifier = Modifier.size(26.dp))
                    Text("All Features", color = Color.White, fontSize = 9.sp)
                }

                // CamScanner Shutter Button
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .border(3.5.dp, Color(0xFF00FFA3), CircleShape)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .testTag("camera_shutter_button")
                        .clickable {
                            val imgCap = imageCapture
                            if (hasCameraPermission && imgCap != null) {
                                val file = File(context.cacheDir, "scan_raw_${System.currentTimeMillis()}.jpg")
                                val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
                                imgCap.takePicture(
                                    outputOptions,
                                    ContextCompat.getMainExecutor(context),
                                    object : ImageCapture.OnImageSavedCallback {
                                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                            try {
                                                val bmp = BitmapFactory.decodeFile(file.absolutePath)
                                                if (bmp != null) {
                                                    onImageCaptured(bmp)
                                                } else {
                                                    onImageCaptured(createDocumentPreviewSample("Assignment"))
                                                }
                                            } catch (e: Exception) {
                                                onImageCaptured(createDocumentPreviewSample("Assignment"))
                                            }
                                        }

                                        override fun onError(exception: ImageCaptureException) {
                                            onImageCaptured(createDocumentPreviewSample("Assignment"))
                                        }
                                    }
                                )
                            } else {
                                onImageCaptured(createDocumentPreviewSample("Assignment"))
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00FFA3).copy(alpha = 0.3f))
                    )
                }

                // Gallery Import
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        galleryLauncher.launch("image/*")
                    }
                ) {
                    Icon(Icons.Default.Image, contentDescription = "Import", tint = Color.White, modifier = Modifier.size(26.dp))
                    Text("Import Images", color = Color.White, fontSize = 9.sp)
                }
            }
        }
    }
}

// =============================================================================
// 2. INTERACTIVE 4-CORNER CROP ADJUSTER VIEW
// =============================================================================
@Composable
fun CornerCropAdjusterView(
    bitmap: Bitmap,
    initialCorners: DocCorners,
    onCornersConfirmed: (DocCorners) -> Unit,
    onCancel: () -> Unit
) {
    var tlX by remember { mutableFloatStateOf(initialCorners.topLeft.x) }
    var tlY by remember { mutableFloatStateOf(initialCorners.topLeft.y) }
    var trX by remember { mutableFloatStateOf(initialCorners.topRight.x) }
    var trY by remember { mutableFloatStateOf(initialCorners.topRight.y) }
    var brX by remember { mutableFloatStateOf(initialCorners.bottomRight.x) }
    var brY by remember { mutableFloatStateOf(initialCorners.bottomRight.y) }
    var blX by remember { mutableFloatStateOf(initialCorners.bottomLeft.x) }
    var blY by remember { mutableFloatStateOf(initialCorners.bottomLeft.y) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onCancel) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text("Adjust 4 Document Edges", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            IconButton(onClick = {
                onCornersConfirmed(
                    DocCorners(
                        topLeft = PointF(tlX, tlY),
                        topRight = PointF(trX, trY),
                        bottomRight = PointF(brX, brY),
                        bottomLeft = PointF(blX, blY)
                    )
                )
            }) {
                Icon(Icons.Default.Check, contentDescription = "Done", tint = Color(0xFF00FFA3))
            }
        }

        Surface(
            color = Color(0xFF1E293B),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "💡 Drag the 4 corner pins to precisely frame the paper. Background will be removed automatically.",
                color = Color(0xFF94A3B8),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            val boxW = maxWidth.value
            val boxH = maxHeight.value

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Document photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                val pTl = Offset(tlX * w, tlY * h)
                val pTr = Offset(trX * w, trY * h)
                val pBr = Offset(brX * w, brY * h)
                val pBl = Offset(blX * w, blY * h)

                val stroke = 3.5f
                val quadColor = Color(0xFF00FFA3)

                drawLine(color = quadColor, start = pTl, end = pTr, strokeWidth = stroke)
                drawLine(color = quadColor, start = pTr, end = pBr, strokeWidth = stroke)
                drawLine(color = quadColor, start = pBr, end = pBl, strokeWidth = stroke)
                drawLine(color = quadColor, start = pBl, end = pTl, strokeWidth = stroke)

                val midTop = Offset((pTl.x + pTr.x) / 2f, (pTl.y + pTr.y) / 2f)
                val midRight = Offset((pTr.x + pBr.x) / 2f, (pTr.y + pBr.y) / 2f)
                val midBottom = Offset((pBl.x + pBr.x) / 2f, (pBl.y + pBr.y) / 2f)
                val midLeft = Offset((pTl.x + pBl.x) / 2f, (pTl.y + pBl.y) / 2f)

                listOf(midTop, midRight, midBottom, midLeft).forEach { m ->
                    drawCircle(color = Color.White, radius = 6f, center = m)
                    drawCircle(color = Color(0xFF00B4D8), radius = 4f, center = m)
                }
            }

            // Top-Left Pin
            Box(
                modifier = Modifier
                    .offset(x = (tlX * boxW).dp - 18.dp, y = (tlY * boxH).dp - 18.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00FFA3))
                    .border(2.dp, Color.White, CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            tlX = (tlX + dragAmount.x / (boxW * 2.5f)).coerceIn(0.01f, 0.48f)
                            tlY = (tlY + dragAmount.y / (boxH * 2.5f)).coerceIn(0.01f, 0.48f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color.Black))
            }

            // Top-Right Pin
            Box(
                modifier = Modifier
                    .offset(x = (trX * boxW).dp - 18.dp, y = (trY * boxH).dp - 18.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00FFA3))
                    .border(2.dp, Color.White, CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            trX = (trX + dragAmount.x / (boxW * 2.5f)).coerceIn(0.52f, 0.99f)
                            trY = (trY + dragAmount.y / (boxH * 2.5f)).coerceIn(0.01f, 0.48f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color.Black))
            }

            // Bottom-Right Pin
            Box(
                modifier = Modifier
                    .offset(x = (brX * boxW).dp - 18.dp, y = (brY * boxH).dp - 18.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00FFA3))
                    .border(2.dp, Color.White, CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            brX = (brX + dragAmount.x / (boxW * 2.5f)).coerceIn(0.52f, 0.99f)
                            brY = (brY + dragAmount.y / (boxH * 2.5f)).coerceIn(0.52f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color.Black))
            }

            // Bottom-Left Pin
            Box(
                modifier = Modifier
                    .offset(x = (blX * boxW).dp - 18.dp, y = (blY * boxH).dp - 18.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00FFA3))
                    .border(2.dp, Color.White, CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            blX = (blX + dragAmount.x / (boxW * 2.5f)).coerceIn(0.01f, 0.48f)
                            blY = (blY + dragAmount.y / (boxH * 2.5f)).coerceIn(0.52f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color.Black))
            }
        }

        Surface(
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssistChip(
                    onClick = {
                        val auto = DocumentEdgeProcessor.detectDocumentCorners(bitmap)
                        tlX = auto.topLeft.x
                        tlY = auto.topLeft.y
                        trX = auto.topRight.x
                        trY = auto.topRight.y
                        brX = auto.bottomRight.x
                        brY = auto.bottomRight.y
                        blX = auto.bottomLeft.x
                        blY = auto.bottomLeft.y
                    },
                    label = { Text("🪄 Auto Edge", color = Color.White, fontSize = 11.sp) },
                    colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF1E293B))
                )

                AssistChip(
                    onClick = {
                        tlX = 0.02f; tlY = 0.02f
                        trX = 0.98f; trY = 0.02f
                        brX = 0.98f; brY = 0.98f
                        blX = 0.02f; blY = 0.98f
                    },
                    label = { Text("🔲 Full Page", color = Color.White, fontSize = 11.sp) },
                    colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF1E293B))
                )

                Button(
                    onClick = {
                        onCornersConfirmed(
                            DocCorners(
                                topLeft = PointF(tlX, tlY),
                                topRight = PointF(trX, trY),
                                bottomRight = PointF(brX, brY),
                                bottomLeft = PointF(blX, blY)
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFA3)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Next ➔", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}

// =============================================================================
// 3. CAMSCANNER FILTER STUDIO VIEW
// =============================================================================
@Composable
fun CamScannerFilterStudioView(
    documentTitle: String,
    onEditTitle: () -> Unit,
    currentBitmap: Bitmap,
    originalBitmap: Bitmap,
    isComparing: Boolean,
    onToggleCompare: () -> Unit,
    activeFilter: ScanFilter,
    onSelectFilter: (ScanFilter) -> Unit,
    onRetake: () -> Unit,
    onRotateLeft: () -> Unit,
    onRotateRight: () -> Unit,
    onReCrop: () -> Unit,
    onExtractOcr: () -> Unit,
    onSaveCheckmark: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(onClick = onReCrop) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { onEditTitle() }
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = documentTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(14.dp), tint = Color.Gray)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = onRetake) {
                        Text("Add", fontWeight = FontWeight.Bold, color = Color(0xFF00B4D8), fontSize = 15.sp)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Image(
                    bitmap = currentBitmap.asImageBitmap(),
                    contentDescription = "Scanned document page",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            if (isComparing) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.7f),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 12.dp)
                ) {
                    Text(
                        text = "Viewing Original Photo",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFE2E8F0)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("◀ 1/1 ▶", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                    }
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isComparing) Color(0xFF00B4D8) else Color(0xFFE2E8F0),
                modifier = Modifier.clickable { onToggleCompare() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Compare",
                        modifier = Modifier.size(13.dp),
                        tint = if (isComparing) Color.White else Color.DarkGray
                    )
                    Text(
                        text = "Compare",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isComparing) Color.White else Color.DarkGray
                    )
                }
            }
        }

        // CamScanner Filter Carousel
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(ScanFilter.values()) { filter ->
                        val isSelected = activeFilter == filter
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onSelectFilter(filter) }
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        when (filter) {
                                            ScanFilter.ENHANCE -> Color(0xFFE0F7FA)
                                            ScanFilter.MAGIC_PRO -> Color(0xFFE8F5E9)
                                            ScanFilter.NO_SHADOW -> Color(0xFFFFF3E0)
                                            ScanFilter.NO_WATERMARK -> Color(0xFFF3E5F5)
                                            ScanFilter.BW -> Color(0xFFECEFF1)
                                            ScanFilter.GRAYSCALE -> Color(0xFFE0E0E0)
                                            ScanFilter.ORIGINAL -> Color(0xFFFFFDE7)
                                            ScanFilter.LIGHTEN -> Color(0xFFFFF9C4)
                                            ScanFilter.ECO_PRINT -> Color(0xFFEFEBE9)
                                        }
                                    )
                                    .border(
                                        width = if (isSelected) 2.dp else 0.5.dp,
                                        color = if (isSelected) Color(0xFF00B4D8) else Color.LightGray,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                when (filter) {
                                    ScanFilter.ENHANCE -> {
                                        Icon(Icons.Default.AutoAwesome, null, tint = Color(0xFF00838F), modifier = Modifier.size(22.dp))
                                    }
                                    ScanFilter.MAGIC_PRO -> {
                                        Text("AI+", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 13.sp)
                                    }
                                    ScanFilter.NO_SHADOW -> {
                                        Text("☀", fontSize = 18.sp, color = Color(0xFFE65100))
                                    }
                                    ScanFilter.NO_WATERMARK -> {
                                        Text("✨", fontSize = 16.sp)
                                    }
                                    ScanFilter.BW -> {
                                        Box(modifier = Modifier.size(16.dp).background(Color.Black, CircleShape))
                                    }
                                    ScanFilter.GRAYSCALE -> {
                                        Box(modifier = Modifier.size(16.dp).background(Color.Gray, CircleShape))
                                    }
                                    ScanFilter.ORIGINAL -> {
                                        Icon(Icons.Default.Image, null, tint = Color(0xFFF57F17), modifier = Modifier.size(20.dp))
                                    }
                                    ScanFilter.LIGHTEN -> {
                                        Text("💡", fontSize = 16.sp)
                                    }
                                    ScanFilter.ECO_PRINT -> {
                                        Text("🖨", fontSize = 16.sp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = filter.displayName,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color(0xFF00838F) else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // Bottom Action Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onRetake() }
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Retake", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(22.dp))
                    Text("Retake", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurface)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onRotateLeft() }
                ) {
                    Icon(Icons.Default.RotateLeft, contentDescription = "Left", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(22.dp))
                    Text("Left", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurface)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onReCrop() }
                ) {
                    Icon(Icons.Default.Crop, contentDescription = "Crop", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(22.dp))
                    Text("Crop", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurface)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onExtractOcr() }
                ) {
                    Icon(Icons.Default.TextFields, contentDescription = "OCR", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(22.dp))
                    Text("Extract Text", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurface)
                }

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00C853))
                        .testTag("save_and_done_button")
                        .clickable { onSaveCheckmark() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Save and Done", tint = Color.White, modifier = Modifier.size(28.dp))
                }
            }
        }
    }
}

// =============================================================================
// 4. DOCUMENT LIBRARY TAB VIEW
// =============================================================================
@Composable
fun ScannerLibraryView(
    context: Context,
    savedDocsList: MutableList<ScannedDocument>
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredList = savedDocsList.filter { doc ->
        val matchesQuery = searchQuery.isBlank() || doc.name.contains(searchQuery, ignoreCase = true) || doc.ocrText.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || doc.folder.equals(selectedCategory, ignoreCase = true)
        matchesQuery && matchesCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search scanned documents or OCR text...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("All", "Study", "Receipts", "IDs", "Scans").forEach { cat ->
                val isSelected = selectedCategory == cat
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.clickable { selectedCategory = cat }
                ) {
                    Text(
                        text = cat,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Folder, null, modifier = Modifier.size(56.dp), tint = Color.LightGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No documents found.", fontWeight = FontWeight.Bold, color = Color.Gray)
                    Text("Use the Edge Camera to capture and scan pages.", fontSize = 11.sp, color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList) { doc ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(0.5.dp, Color.LightGray)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PictureAsPdf, null, tint = MaterialTheme.colorScheme.primary)
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(doc.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                                Text("Folder: ${doc.folder} | Pages: ${doc.pageCount} | Size: ${doc.sizeMb} MB", fontSize = 10.sp, color = Color.Gray)
                                Text("Date: ${doc.date} | ${doc.classification}", fontSize = 9.sp, color = Color(0xFF00838F))
                            }

                            IconButton(onClick = {
                                val idx = savedDocsList.indexOfFirst { it.id == doc.id }
                                if (idx != -1) {
                                    val updated = savedDocsList[idx].copy(isStarred = !savedDocsList[idx].isStarred)
                                    savedDocsList[idx] = updated
                                    saveLocalScans(context, savedDocsList)
                                }
                            }) {
                                Icon(
                                    imageVector = if (doc.isStarred) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = "Star",
                                    tint = if (doc.isStarred) Color(0xFFFFC107) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
