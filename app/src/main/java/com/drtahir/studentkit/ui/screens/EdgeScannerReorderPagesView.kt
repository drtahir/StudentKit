package com.drtahir.studentkit.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FirstPage
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LastPage
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.drtahir.studentkit.data.DocumentEdgeProcessor
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.math.min

/**
 * Data model representing an individual reorderable page in Edge Scanner.
 */
data class EdgeScanPage(
    val id: String = UUID.randomUUID().toString(),
    var bitmap: Bitmap,
    var rotation: Float = 0f,
    var label: String = ""
)

/**
 * Extracts all pages of a PDF into a list of crisp Bitmaps using Android's native PdfRenderer.
 */
suspend fun extractAllPagesFromPdfUri(context: Context, pdfUri: Uri): List<Bitmap> {
    return withContext(Dispatchers.IO) {
        val pages = mutableListOf<Bitmap>()
        try {
            context.contentResolver.openFileDescriptor(pdfUri, "r")?.use { pfd ->
                PdfRenderer(pfd).use { renderer ->
                    val count = renderer.pageCount
                    for (i in 0 until count) {
                        renderer.openPage(i).use { page ->
                            val scale = 2.0f
                            val targetW = (page.width * scale).toInt().coerceIn(300, 2200)
                            val targetH = (page.height * scale).toInt().coerceIn(400, 3000)
                            val bmp = Bitmap.createBitmap(targetW, targetH, Bitmap.Config.ARGB_8888)
                            val canvas = Canvas(bmp)
                            canvas.drawColor(android.graphics.Color.WHITE)
                            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                            pages.add(bmp)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        pages
    }
}

/**
 * Extracts clean display file name from any content Uri.
 */
fun getDocumentDisplayName(context: Context, uri: Uri): String {
    var name = "Scanned_Document"
    try {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    val rawName = it.getString(index)
                    if (!rawName.isNullOrBlank()) {
                        name = rawName.substringBeforeLast(".")
                    }
                }
            }
        }
    } catch (e: Exception) {
        // fallback
    }
    return name
}

/**
 * Rotates a bitmap by a given angle (e.g. 90, 180, 270).
 */
fun rotatePageBitmap(source: Bitmap, degrees: Float): Bitmap {
    if (degrees % 360f == 0f) return source
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
}

/**
 * Comprehensive, High-Performance Page Reordering Studio for Hikmahscanner Edge Scanner.
 * Supports:
 * - Drag/tap-to-swap reordering
 * - Instant directional shifting (Move left, right, top, bottom)
 * - Reverse page order
 * - Rotate individual or all pages
 * - Add pages from multi-page PDF, multi-image gallery, or camera
 * - Duplicate & delete pages
 * - Full-screen zoom inspection
 * - Compiling back into a high-quality A4 PDF with direct Share and Library sync.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdgeScannerReorderPagesView(
    initialPages: List<Bitmap>,
    documentTitle: String,
    originalDocId: String? = null,
    onSaveFinished: (title: String, reorderedPages: List<Bitmap>, savedPdfUri: Uri?) -> Unit,
    onBack: () -> Unit,
    onRequestCameraCapture: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Page items state
    val pages = remember {
        mutableStateListOf<EdgeScanPage>().apply {
            addAll(initialPages.mapIndexed { idx, bmp ->
                EdgeScanPage(bitmap = bmp, label = "Page ${idx + 1}")
            })
        }
    }

    var currentTitle by remember { mutableStateOf(documentTitle.ifBlank { "Scanned_Document" }) }
    var showRenameDialog by remember { mutableStateOf(false) }
    var showAddPagesSheet by remember { mutableStateOf(false) }
    var showDeleteAllConfirm by remember { mutableStateOf(false) }
    var selectedForSwapIndex by remember { mutableIntStateOf(-1) }
    var previewingPage by remember { mutableStateOf<EdgeScanPage?>(null) }
    var isGridView by remember { mutableStateOf(true) }
    var isProcessingLoading by remember { mutableStateOf(false) }
    var processingMessage by remember { mutableStateOf("") }

    // Export success state
    var exportedPdfUri by remember { mutableStateOf<Uri?>(null) }
    var showExportSuccessDialog by remember { mutableStateOf(false) }
    var pageToDeleteIndex by remember { mutableIntStateOf(-1) }

    // Multi-image picker launcher
    val multiImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            isProcessingLoading = true
            processingMessage = "Importing ${uris.size} image(s)..."
            coroutineScope.launch(Dispatchers.IO) {
                val loadedBitmaps = mutableListOf<Bitmap>()
                for (uri in uris) {
                    val bmp = loadAndCorrectOrientationFromUri(context, uri)
                    if (bmp != null) loadedBitmaps.add(bmp)
                }
                withContext(Dispatchers.Main) {
                    loadedBitmaps.forEachIndexed { i, bmp ->
                        pages.add(EdgeScanPage(bitmap = bmp, label = "Imported ${pages.size + 1}"))
                    }
                    isProcessingLoading = false
                    Toast.makeText(context, "Added ${loadedBitmaps.size} page(s) to document!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // PDF document picker launcher
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            isProcessingLoading = true
            processingMessage = "Extracting multi-page PDF..."
            coroutineScope.launch {
                val extracted = extractAllPagesFromPdfUri(context, uri)
                val docName = getDocumentDisplayName(context, uri)
                withContext(Dispatchers.Main) {
                    if (extracted.isNotEmpty()) {
                        extracted.forEachIndexed { idx, bmp ->
                            pages.add(EdgeScanPage(bitmap = bmp, label = "$docName p.${idx + 1}"))
                        }
                        if (currentTitle == "Scanned_Document" || currentTitle.isBlank()) {
                            currentTitle = docName
                        }
                        Toast.makeText(context, "Extracted ${extracted.size} pages from PDF!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Could not extract pages from selected PDF", Toast.LENGTH_LONG).show()
                    }
                    isProcessingLoading = false
                }
            }
        }
    }

    BackHandler {
        onBack()
    }

    // Save and compile PDF
    fun performSaveAndExport() {
        if (pages.isEmpty()) {
            Toast.makeText(context, "Document has no pages to export!", Toast.LENGTH_SHORT).show()
            return
        }
        isProcessingLoading = true
        processingMessage = "Compiling ${pages.size} reordered pages into PDF..."

        coroutineScope.launch(Dispatchers.IO) {
            val bitmapList = pages.map { it.bitmap }
            val uri = DocumentEdgeProcessor.savePdfToPhoneStorage(context, bitmapList, currentTitle)
            withContext(Dispatchers.Main) {
                isProcessingLoading = false
                if (uri != null) {
                    exportedPdfUri = uri
                    showExportSuccessDialog = true
                    onSaveFinished(currentTitle, bitmapList, uri)
                } else {
                    Toast.makeText(context, "Failed to compile PDF. Check storage permissions.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // TOP APP BAR
        Surface(
            color = Color(0xFF1E293B),
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("reorder_back_button")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { showRenameDialog = true }
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = currentTitle,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Rename",
                                tint = Color(0xFF00FFA3),
                                modifier = Modifier.size(13.dp)
                            )
                        }
                        Text(
                            text = "${pages.size} Page(s) • Tap to rename",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // View Mode Toggle (Grid vs List)
                    IconButton(
                        onClick = { isGridView = !isGridView },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            if (isGridView) Icons.Default.ViewAgenda else Icons.Default.GridView,
                            contentDescription = "Toggle View",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Save / Export PDF Action Button
                    Button(
                        onClick = { performSaveAndExport() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00FFA3),
                            contentColor = Color(0xFF003822)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("reorder_save_button")
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Save PDF", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                    }
                }
            }
        }

        // SWAP MODE / TIP BANNER
        AnimatedVisibility(
            visible = selectedForSwapIndex != -1,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Surface(
                color = Color(0xFF00FFA3).copy(alpha = 0.18f),
                border = BorderStroke(1.dp, Color(0xFF00FFA3)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = Color(0xFF00FFA3))
                        Text(
                            text = "Page #${selectedForSwapIndex + 1} selected. Tap any other page to swap positions!",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    TextButton(
                        onClick = { selectedForSwapIndex = -1 },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("Cancel", color = Color(0xFFFF5252), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // TOOLBAR OF QUICK BATCH REORDER ACTIONS
        Surface(
            color = Color(0xFF1E293B).copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reverse Page Order
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF334155),
                    modifier = Modifier.clickable(enabled = pages.size > 1) {
                        pages.reverse()
                        selectedForSwapIndex = -1
                        Toast.makeText(context, "Page order reversed (N..1)!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.SwapVert, contentDescription = null, tint = Color(0xFF00FFA3), modifier = Modifier.size(16.dp))
                        Text("Reverse Order", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                // Rotate All Pages 90°
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF334155),
                    modifier = Modifier.clickable(enabled = pages.isNotEmpty()) {
                        isProcessingLoading = true
                        processingMessage = "Rotating all pages 90°..."
                        coroutineScope.launch(Dispatchers.Default) {
                            val rotatedList = pages.map { page ->
                                val rot = (page.rotation + 90f) % 360f
                                val bmp = rotatePageBitmap(page.bitmap, 90f)
                                page.copy(bitmap = bmp, rotation = rot)
                            }
                            withContext(Dispatchers.Main) {
                                pages.clear()
                                pages.addAll(rotatedList)
                                isProcessingLoading = false
                                Toast.makeText(context, "Rotated all pages 90°", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.RotateRight, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(16.dp))
                        Text("Rotate All 90°", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                // Add More Pages
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF0F766E),
                    modifier = Modifier.clickable { showAddPagesSheet = true }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Text("Add Pages", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Delete All / Clear
                if (pages.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF334155),
                        modifier = Modifier.clickable { showDeleteAllConfirm = true }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = null, tint = Color(0xFFF87171), modifier = Modifier.size(16.dp))
                            Text("Clear All", color = Color(0xFFF87171), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // MAIN CONTENT AREA: Empty state or Pages List / Grid
        if (pages.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Layers,
                            contentDescription = null,
                            tint = Color(0xFF00FFA3),
                            modifier = Modifier.size(38.dp)
                        )
                    }
                    Text(
                        "No Pages in Document",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Import a multi-page PDF, select photos from your gallery, or capture pages with the Edge camera to reorder.",
                        color = Color(0xFF94A3B8),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.85f)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Button(
                            onClick = { pdfPickerLauncher.launch(arrayOf("application/pdf")) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.PictureAsPdf, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Import PDF", fontSize = 13.sp)
                        }

                        Button(
                            onClick = { multiImagePicker.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Import Photos", fontSize = 13.sp)
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (isGridView) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(pages, key = { _, page -> page.id }) { index, page ->
                            val isSelectedForSwap = selectedForSwapIndex == index
                            val borderColor by animateColorAsState(
                                targetValue = if (isSelectedForSwap) Color(0xFF00FFA3) else Color(0xFF334155),
                                animationSpec = tween(200)
                            )

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = if (isSelectedForSwap) 2.5.dp else 1.dp,
                                        color = borderColor,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        if (selectedForSwapIndex == -1) {
                                            selectedForSwapIndex = index
                                        } else if (selectedForSwapIndex == index) {
                                            selectedForSwapIndex = -1
                                        } else {
                                            // Perform swap
                                            val temp = pages[selectedForSwapIndex]
                                            pages[selectedForSwapIndex] = pages[index]
                                            pages[index] = temp
                                            Toast.makeText(
                                                context,
                                                "Swapped Page #${selectedForSwapIndex + 1} with Page #${index + 1}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            selectedForSwapIndex = -1
                                        }
                                    },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                elevation = CardDefaults.cardElevation(if (isSelectedForSwap) 8.dp else 2.dp)
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    // Thumbnail preview container
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(0.72f)
                                            .background(Color(0xFF0B1120)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            bitmap = page.bitmap.asImageBitmap(),
                                            contentDescription = "Page ${index + 1}",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(6.dp),
                                            contentScale = ContentScale.Fit
                                        )

                                        // Page Number Badge
                                        Surface(
                                            shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 10.dp),
                                            color = if (isSelectedForSwap) Color(0xFF00FFA3) else Color(0xFF0284C7),
                                            modifier = Modifier.align(Alignment.TopStart)
                                        ) {
                                            Text(
                                                text = "#${index + 1}",
                                                color = if (isSelectedForSwap) Color(0xFF003822) else Color.White,
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 12.sp,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }

                                        // Fullscreen Zoom Inspect Button
                                        IconButton(
                                            onClick = { previewingPage = page },
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(4.dp)
                                                .size(28.dp)
                                                .clip(CircleShape)
                                                .background(Color.Black.copy(alpha = 0.6f))
                                        ) {
                                            Icon(
                                                Icons.Default.ZoomIn,
                                                contentDescription = "Zoom",
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }

                                    // Action Controls Bar under thumbnail
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFF0F172A))
                                            .padding(horizontal = 4.dp, vertical = 2.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Move Left / Backward
                                        IconButton(
                                            onClick = {
                                                if (index > 0) {
                                                    val temp = pages[index]
                                                    pages[index] = pages[index - 1]
                                                    pages[index - 1] = temp
                                                    selectedForSwapIndex = -1
                                                }
                                            },
                                            enabled = index > 0,
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.ArrowBack,
                                                contentDescription = "Move Left",
                                                tint = if (index > 0) Color(0xFF00FFA3) else Color.DarkGray,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        // Rotate 90°
                                        IconButton(
                                            onClick = {
                                                val newBmp = rotatePageBitmap(page.bitmap, 90f)
                                                val newRot = (page.rotation + 90f) % 360f
                                                pages[index] = page.copy(bitmap = newBmp, rotation = newRot)
                                            },
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.RotateRight,
                                                contentDescription = "Rotate",
                                                tint = Color(0xFF38BDF8),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        // Delete Page
                                        IconButton(
                                            onClick = { pageToDeleteIndex = index },
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = Color(0xFFF87171),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        // Move Right / Forward
                                        IconButton(
                                            onClick = {
                                                if (index < pages.size - 1) {
                                                    val temp = pages[index]
                                                    pages[index] = pages[index + 1]
                                                    pages[index + 1] = temp
                                                    selectedForSwapIndex = -1
                                                }
                                            },
                                            enabled = index < pages.size - 1,
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.ArrowForward,
                                                contentDescription = "Move Right",
                                                tint = if (index < pages.size - 1) Color(0xFF00FFA3) else Color.DarkGray,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // LIST VIEW (Detailed Row View)
                    LazyColumn(
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(pages, key = { _, page -> page.id }) { index, page ->
                            val isSelectedForSwap = selectedForSwapIndex == index

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = if (isSelectedForSwap) 2.dp else 1.dp,
                                        color = if (isSelectedForSwap) Color(0xFF00FFA3) else Color(0xFF334155),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clickable {
                                        if (selectedForSwapIndex == -1) {
                                            selectedForSwapIndex = index
                                        } else if (selectedForSwapIndex == index) {
                                            selectedForSwapIndex = -1
                                        } else {
                                            val temp = pages[selectedForSwapIndex]
                                            pages[selectedForSwapIndex] = pages[index]
                                            pages[index] = temp
                                            selectedForSwapIndex = -1
                                        }
                                    },
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Page Number Indicator
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelectedForSwap) Color(0xFF00FFA3) else Color(0xFF0284C7)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "#${index + 1}",
                                            color = if (isSelectedForSwap) Color(0xFF003822) else Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    }

                                    // Thumbnail preview
                                    Box(
                                        modifier = Modifier
                                            .size(width = 54.dp, height = 72.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color.Black)
                                            .clickable { previewingPage = page },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            bitmap = page.bitmap.asImageBitmap(),
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Fit
                                        )
                                    }

                                    // Meta info
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            "Page ${index + 1}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = Color.White
                                        )
                                        Text(
                                            "${page.bitmap.width} × ${page.bitmap.height} px",
                                            fontSize = 11.sp,
                                            color = Color(0xFF94A3B8)
                                        )
                                        Text(
                                            if (page.rotation != 0f) "Rotated ${page.rotation.toInt()}°" else "Standard Orientation",
                                            fontSize = 10.sp,
                                            color = Color(0xFF38BDF8)
                                        )
                                    }

                                    // Action buttons
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Move to Top
                                        IconButton(
                                            onClick = {
                                                if (index > 0) {
                                                    val item = pages.removeAt(index)
                                                    pages.add(0, item)
                                                    selectedForSwapIndex = -1
                                                }
                                            },
                                            enabled = index > 0,
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.FirstPage, "To Top", tint = if (index > 0) Color.White else Color.DarkGray, modifier = Modifier.size(18.dp))
                                        }

                                        // Move Up
                                        IconButton(
                                            onClick = {
                                                if (index > 0) {
                                                    val temp = pages[index]
                                                    pages[index] = pages[index - 1]
                                                    pages[index - 1] = temp
                                                    selectedForSwapIndex = -1
                                                }
                                            },
                                            enabled = index > 0,
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.ArrowUpward, "Move Up", tint = if (index > 0) Color(0xFF00FFA3) else Color.DarkGray, modifier = Modifier.size(18.dp))
                                        }

                                        // Move Down
                                        IconButton(
                                            onClick = {
                                                if (index < pages.size - 1) {
                                                    val temp = pages[index]
                                                    pages[index] = pages[index + 1]
                                                    pages[index + 1] = temp
                                                    selectedForSwapIndex = -1
                                                }
                                            },
                                            enabled = index < pages.size - 1,
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.ArrowDownward, "Move Down", tint = if (index < pages.size - 1) Color(0xFF00FFA3) else Color.DarkGray, modifier = Modifier.size(18.dp))
                                        }

                                        // Rotate
                                        IconButton(
                                            onClick = {
                                                val newBmp = rotatePageBitmap(page.bitmap, 90f)
                                                val newRot = (page.rotation + 90f) % 360f
                                                pages[index] = page.copy(bitmap = newBmp, rotation = newRot)
                                            },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.RotateRight, "Rotate", tint = Color(0xFF38BDF8), modifier = Modifier.size(18.dp))
                                        }

                                        // Delete
                                        IconButton(
                                            onClick = { pageToDeleteIndex = index },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.Delete, "Delete", tint = Color(0xFFF87171), modifier = Modifier.size(18.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // =========================================================================
    // DIALOGS & BOTTOM SHEETS
    // =========================================================================

    // 1. ADD PAGES BOTTOM SHEET
    if (showAddPagesSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAddPagesSheet = false },
            containerColor = Color(0xFF1E293B)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    "Add More Pages to Document",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Text(
                    "Append additional pages from an external PDF, gallery images, or camera:",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )

                // Option: Import PDF
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF334155)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showAddPagesSheet = false
                            pdfPickerLauncher.launch(arrayOf("application/pdf"))
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF0284C7).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.PictureAsPdf, null, tint = Color(0xFF38BDF8))
                        }
                        Column {
                            Text("Import Multi-Page PDF Document", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Extracts and appends all pages from any PDF file", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                    }
                }

                // Option: Import Multiple Images
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF334155)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showAddPagesSheet = false
                            multiImagePicker.launch("image/*")
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF059669).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.PhotoLibrary, null, tint = Color(0xFF00FFA3))
                        }
                        Column {
                            Text("Import Photos from Gallery", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Select single or multiple photos to add to this sequence", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                    }
                }

                // Option: Snap with Camera
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF334155)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showAddPagesSheet = false
                            onRequestCameraCapture()
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFD97706).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CameraAlt, null, tint = Color(0xFFFBBF24))
                        }
                        Column {
                            Text("Snap New Page with Camera", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Opens Edge Scanner camera to capture additional pages", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }

    // 2. FULLSCREEN PAGE ZOOM / INSPECT DIALOG
    previewingPage?.let { page ->
        Dialog(
            onDismissRequest = { previewingPage = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        bitmap = page.bitmap.asImageBitmap(),
                        contentDescription = "Page Preview",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentScale = ContentScale.Fit
                    )

                    // Close button
                    IconButton(
                        onClick = { previewingPage = null },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(20.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray.copy(alpha = 0.7f))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }

                    // Bottom info bar
                    Surface(
                        color = Color.Black.copy(alpha = 0.75f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Resolution: ${page.bitmap.width} × ${page.bitmap.height}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Button(
                                onClick = { previewingPage = null },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFA3)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Done", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }

    // 3. CONFIRM DELETE SINGLE PAGE
    if (pageToDeleteIndex != -1) {
        AlertDialog(
            onDismissRequest = { pageToDeleteIndex = -1 },
            title = { Text("Delete Page #${pageToDeleteIndex + 1}?", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to remove this page from the document? Other pages will automatically re-number.") },
            confirmButton = {
                Button(
                    onClick = {
                        val removedIndex = pageToDeleteIndex
                        pages.removeAt(removedIndex)
                        if (selectedForSwapIndex == removedIndex) {
                            selectedForSwapIndex = -1
                        } else if (selectedForSwapIndex > removedIndex) {
                            selectedForSwapIndex--
                        }
                        pageToDeleteIndex = -1
                        Toast.makeText(context, "Page #${removedIndex + 1} deleted", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                ) {
                    Text("Delete Page")
                }
            },
            dismissButton = {
                TextButton(onClick = { pageToDeleteIndex = -1 }) {
                    Text("Cancel")
                }
            }
        )
    }

    // 4. CONFIRM CLEAR ALL PAGES
    if (showDeleteAllConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteAllConfirm = false },
            title = { Text("Clear All Pages?", fontWeight = FontWeight.Bold) },
            text = { Text("This will remove all ${pages.size} pages currently in the reorder queue.") },
            confirmButton = {
                Button(
                    onClick = {
                        pages.clear()
                        selectedForSwapIndex = -1
                        showDeleteAllConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                ) {
                    Text("Clear All")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAllConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // 5. RENAME DOCUMENT DIALOG
    if (showRenameDialog) {
        var tempTitle by remember { mutableStateOf(currentTitle) }
        AlertDialog(
            onDismissRequest = { showRenameDialog = false },
            title = { Text("Rename Document", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = tempTitle,
                    onValueChange = { tempTitle = it },
                    label = { Text("Document Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (tempTitle.isNotBlank()) {
                        currentTitle = tempTitle.trim()
                    }
                    showRenameDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRenameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // 6. ASYNC LOADING OVERLAY
    if (isProcessingLoading) {
        Dialog(
            onDismissRequest = { /* Non-dismissible while processing */ },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF1E293B),
                modifier = Modifier.padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(color = Color(0xFF00FFA3))
                    Text(
                        text = processingMessage,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    // 7. EXPORT SUCCESS DIALOG
    if (showExportSuccessDialog && exportedPdfUri != null) {
        AlertDialog(
            onDismissRequest = { showExportSuccessDialog = false },
            icon = {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF00C853),
                    modifier = Modifier.size(44.dp)
                )
            },
            title = {
                Text(
                    "Reordered PDF Exported!",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Your document \"$currentTitle\" with ${pages.size} reordered page(s) has been compiled and saved to your device.",
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Saved in: Documents/Hikmahscanner",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        try {
                            val uri = exportedPdfUri!!
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                setDataAndType(uri, "application/pdf")
                                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            context.startActivity(Intent.createChooser(intent, "Open PDF with"))
                        } catch (e: Exception) {
                            Toast.makeText(context, "No PDF viewer app installed", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00838F))
                ) {
                    Icon(Icons.Default.PictureAsPdf, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Open PDF")
                }
            },
            dismissButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    TextButton(
                        onClick = {
                            try {
                                val uri = exportedPdfUri!!
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "application/pdf"
                                    putExtra(Intent.EXTRA_STREAM, uri)
                                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Share Reordered PDF"))
                            } catch (e: Exception) {
                                Toast.makeText(context, "Failed to share", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Share, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Share")
                    }
                    TextButton(
                        onClick = {
                            showExportSuccessDialog = false
                            onBack()
                        }
                    ) {
                        Text("Done")
                    }
                }
            }
        )
    }
}
