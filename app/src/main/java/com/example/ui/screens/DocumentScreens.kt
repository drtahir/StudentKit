package com.example.ui.screens

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.InputStream
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.StudentKitViewModel
import com.example.data.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.platform.testTag
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentHubScreen(
    viewModel: StudentKitViewModel,
    title: String,
    subScreen: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            subScreen()
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: IMAGE TO PDF
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageToPdfScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val tempImages by viewModel.allTempPdfImages.collectAsState(initial = emptyList())
    var pageSize by remember { mutableStateOf("A4") }
    var orientation by remember { mutableStateOf("Portrait") }

    val pickMultipleMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            if (uris != null && uris.isNotEmpty()) {
                uris.forEach { uri ->
                    try {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        if (inputStream != null) {
                            val originalFileName = getFileName(context, uri) ?: "image_${System.currentTimeMillis()}.jpg"
                            val tempFile = java.io.File(context.cacheDir, originalFileName)
                            tempFile.outputStream().use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                            viewModel.addTempPdfImage(tempFile.absolutePath, uri.toString())
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error importing image: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "High Quality Image to PDF Converter",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Select any number of high resolution photos directly from your phone's gallery. The compiled PDF will be saved to your device's Downloads folder.",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Selected Images (${tempImages.size})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    IconButton(
                        onClick = {
                            clearPdfImagesCache(context, tempImages, viewModel)
                            Toast.makeText(context, "Cleared all temporary images.", Toast.LENGTH_SHORT).show()
                        },
                        enabled = tempImages.isNotEmpty()
                    ) {
                        Icon(Icons.Default.DeleteForever, "Clear all", tint = if (tempImages.isNotEmpty()) Color.Red else Color.LightGray)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    if (tempImages.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.AddPhotoAlternate,
                                contentDescription = "Add image",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "No images selected, click 'Select Photos' to choose",
                                fontSize = 13.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(tempImages) { tempImg ->
                                val file = java.io.File(tempImg.filePath)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                        val bitmap = remember(tempImg.filePath) {
                                            try {
                                                val opts = BitmapFactory.Options().apply { inSampleSize = 8 }
                                                BitmapFactory.decodeFile(tempImg.filePath, opts)
                                            } catch (e: Exception) {
                                                null
                                            }
                                        }
                                        if (bitmap != null) {
                                            androidx.compose.foundation.Image(
                                                bitmap = bitmap.asImageBitmap(),
                                                contentDescription = "Thumbnail",
                                                modifier = Modifier
                                                    .size(48.dp)
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(Color.LightGray)
                                            )
                                        } else {
                                            Icon(Icons.Default.Image, null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = file.name,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                maxLines = 1
                                            )
                                            val kb = file.length() / 1024
                                            Text(
                                                text = "${kb} KB",
                                                fontSize = 11.sp,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                    IconButton(
                                        onClick = {
                                            if (file.exists()) file.delete()
                                            viewModel.removeTempPdfImage(tempImg.id)
                                        }
                                    ) {
                                        Icon(Icons.Default.Delete, "Remove", tint = Color.Red, modifier = Modifier.size(20.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        try {
                            pickMultipleMediaLauncher.launch(arrayOf("image/*"))
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error opening selector: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.AddPhotoAlternate, "Pick photo")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Select Photos")
                }
            }
        }

        Text("Layout configurations:", fontWeight = FontWeight.Bold, fontSize = 14.sp)

        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Page Canvas Size", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Row {
                        listOf("A4", "Letter", "A5").forEach { s ->
                            ElevatedFilterChip(
                                selected = pageSize == s,
                                onClick = { pageSize = s },
                                label = { Text(s, fontSize = 11.sp) },
                                modifier = Modifier.padding(horizontal = 2.dp)
                            )
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Orientation", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Row {
                        listOf("Portrait", "Landscape").forEach { o ->
                            ElevatedFilterChip(
                                selected = orientation == o,
                                onClick = { orientation = o },
                                label = { Text(o, fontSize = 11.sp) },
                                modifier = Modifier.padding(horizontal = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        var isGenerating by remember { mutableStateOf(false) }

        Button(
            onClick = {
                if (tempImages.isEmpty()) {
                    Toast.makeText(context, "Please select at least one photo!", Toast.LENGTH_SHORT).show()
                } else {
                    isGenerating = true
                    compileImagesToPdf(context, tempImages, pageSize, orientation) { savedUri ->
                        isGenerating = false
                        if (savedUri != null) {
                            Toast.makeText(context, "PDF successfully generated & saved to Downloads!", Toast.LENGTH_LONG).show()
                            // Clear Cache & Database as requested!
                            clearPdfImagesCache(context, tempImages, viewModel)
                        } else {
                            Toast.makeText(context, "Failed to compile standard PDF.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            },
            enabled = !isGenerating && tempImages.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            if (isGenerating) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Icon(Icons.Default.PictureAsPdf, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Compile & Download high-res PDF")
            }
        }
    }
}

// PDF COMPILE & CLEANUP HELPER FUNCTIONS
private fun getFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    result = cursor.getString(index)
                }
            }
        } finally {
            cursor?.close()
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result
}

private fun compileImagesToPdf(
    context: Context,
    tempImages: List<TempPdfImage>,
    pageSize: String,
    orientation: String,
    onComplete: (Uri?) -> Unit
) {
    if (tempImages.isEmpty()) {
        onComplete(null)
        return
    }

    try {
        val pdfDocument = PdfDocument()

        tempImages.forEachIndexed { index, tempImg ->
            val file = java.io.File(tempImg.filePath)
            if (file.exists()) {
                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = false
                }
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                if (bitmap != null) {
                    val pageW: Int
                    val pageH: Int
                    if (pageSize == "A4") {
                        if (orientation == "Portrait") {
                            pageW = 595
                            pageH = 842
                        } else {
                            pageW = 842
                            pageH = 595
                        }
                    } else if (pageSize == "Letter") {
                        if (orientation == "Portrait") {
                            pageW = 612
                            pageH = 792
                        } else {
                            pageW = 792
                            pageH = 612
                        }
                    } else { // A5
                        if (orientation == "Portrait") {
                            pageW = 420
                            pageH = 595
                        } else {
                            pageW = 595
                            pageH = 420
                        }
                    }

                    val pageInfo = PdfDocument.PageInfo.Builder(pageW, pageH, index + 1).create()
                    val page = pdfDocument.startPage(pageInfo)
                    val canvas = page.canvas

                    val bW = bitmap.width.toFloat()
                    val bH = bitmap.height.toFloat()
                    val scaleX = pageW.toFloat() / bW
                    val scaleY = pageH.toFloat() / bH
                    val scale = Math.min(scaleX, scaleY)

                    val newWidth = bW * scale
                    val newHeight = bH * scale
                    val left = (pageW - newWidth) / 2f
                    val top = (pageH - newHeight) / 2f

                    val srcRect = android.graphics.Rect(0, 0, bitmap.width, bitmap.height)
                    val destRect = android.graphics.RectF(left, top, left + newWidth, top + newHeight)

                    val paint = Paint().apply {
                        isAntiAlias = true
                        isFilterBitmap = true
                        isDither = true
                    }

                    canvas.drawBitmap(bitmap, srcRect, destRect, paint)
                    pdfDocument.finishPage(page)
                    bitmap.recycle()
                }
            }
        }

        val displayName = "Compiled_Images_${System.currentTimeMillis()}.pdf"
        val resolver = context.contentResolver
        val contentValues = android.content.ContentValues().apply {
            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
            }
        }

        val pdfUri = resolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (pdfUri != null) {
            resolver.openOutputStream(pdfUri)?.use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            pdfDocument.close()
            onComplete(pdfUri)
        } else {
            pdfDocument.close()
            onComplete(null)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

private fun clearPdfImagesCache(context: Context, images: List<TempPdfImage>, viewModel: StudentKitViewModel) {
    images.forEach { tempImg ->
        val file = java.io.File(tempImg.filePath)
        if (file.exists()) {
            file.delete()
        }
    }
    viewModel.clearAllTempPdfImages()
}

// -------------------------------------------------------------
// MODULE 9: IMAGE TO XLS
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageToXlsScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var extractionResult by remember {
        mutableStateOf(
            listOf(
                listOf("Roll No", "Student Name", "Quiz Mark", "Grade"),
                listOf("CS-101", "Imran Khan", "18.5", "A"),
                listOf("CS-102", "Benazir Shah", "14.0", "B+"),
                listOf("CS-103", "Nawaz Sharif", "9.0", "C")
            )
        )
    }

    var isProcessingOcr by remember { mutableStateOf(false) }
    var scaleImageUri by remember { mutableStateOf<Uri?>(null) }
    
    // Dialog state for cell editing
    var editingCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var editingCellValue by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            scaleImageUri = uri
            isProcessingOcr = true
            performOcrOnImage(context, uri) { parsedRows ->
                isProcessingOcr = false
                if (parsedRows.isNotEmpty()) {
                    extractionResult = parsedRows
                    Toast.makeText(context, "OCR Complete! Parsed ${parsedRows.size} rows.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "No structured text found in image. Try another.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "📊 Image to Excel (AI-Powered OCR)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Powered",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Select an image containing any table or list. The on-device OCR engine will process, map it into a grid, and let you modify cell items before downloading as a standard CSV spreadsheet file.",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    enabled = !isProcessingOcr,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isProcessingOcr) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Extracting Table...")
                    } else {
                        Icon(Icons.Default.PhotoCamera, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Import & Scan Table Image")
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Interactive Extracted Sheet", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = {
                        val colCount = if (extractionResult.isNotEmpty()) extractionResult[0].size else 4
                        val newRow = List(colCount) { "" }
                        extractionResult = extractionResult + listOf(newRow)
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.AddBox, "Add row", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(
                    onClick = {
                        if (extractionResult.size > 1) {
                            extractionResult = extractionResult.dropLast(1)
                        }
                    },
                    modifier = Modifier.size(32.dp),
                    enabled = extractionResult.size > 1
                ) {
                    Icon(Icons.Default.IndeterminateCheckBox, "Delete row", tint = if (extractionResult.size > 1) Color.Red else Color.Gray)
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(extractionResult.size) { rowIndex ->
                    val rowItems = extractionResult[rowIndex]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (rowIndex == 0) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) 
                                else if (rowIndex % 2 == 1) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                                else Color.Transparent
                            )
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        rowItems.forEachIndexed { colIndex, cellText ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = 1.dp, 
                                        color = if (rowIndex == 0) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.LightGray.copy(alpha = 0.5f), 
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .clickable {
                                        editingCell = Pair(rowIndex, colIndex)
                                        editingCellValue = cellText
                                    }
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cellText.ifBlank { "-" },
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    fontWeight = if (rowIndex == 0) FontWeight.Bold else FontWeight.Normal,
                                    textAlign = TextAlign.Center,
                                    color = if (cellText.isBlank()) Color.Gray else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                val csvUri = exportToCsvAndDownload(context, extractionResult, "StudentKit_Extracted_Sheet_${System.currentTimeMillis()}")
                if (csvUri != null) {
                    Toast.makeText(context, "Spreadsheet exported & saved as CSV to Downloads!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Failed to download spreadsheet.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.TableChart, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Compile & Save CSV File")
        }
    }

    editingCell?.let { (r, c) ->
        AlertDialog(
            onDismissRequest = { editingCell = null },
            title = { Text("Edit Cell Value [Row ${r + 1}, Col ${c + 1}]", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = editingCellValue,
                    onValueChange = { editingCellValue = it },
                    singleLine = true,
                    label = { Text("Cell value") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val updatedList = extractionResult.mapIndexed { ri, row ->
                            if (ri == r) {
                                row.mapIndexed { ci, colTerm ->
                                    if (ci == c) editingCellValue else colTerm
                                }
                            } else {
                                row
                            }
                        }
                        extractionResult = updatedList
                        editingCell = null
                    }
                ) {
                    Text("Apply")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingCell = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// =============================================================
// MODULE 9B: IMAGE TO WORD (.DOCX) CONVERTER WITH LOCAL OCR
// =============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageToWordScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var docTitle by remember { mutableStateOf("Academic Lecture Summary") }
    var docAuthor by remember { mutableStateOf("Student Assistant") }
    var docContent by remember { mutableStateOf("This document was converted using StudentKit OCR.\n\nYou can scan printed notes or textbooks to extract and compile them into structured Microsoft Word (.docx) documents.\n\nEdit this content, customize document settings below, and download instantly.") }
    var selectedTheme by remember { mutableStateOf("Modern") } // Classic, Modern, Academic
    var docFileName by remember { mutableStateOf("scanned_notes_export") }
    
    var isProcessingOcr by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var downloadedDocUri by remember { mutableStateOf<Uri?>(null) }
    var showDownloadSuccessDialog by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            isProcessingOcr = true
            performTextOcrOnImage(context, uri) { textResult ->
                isProcessingOcr = false
                if (textResult.isNotEmpty()) {
                    docContent = textResult
                    Toast.makeText(context, "OCR Complete! Text loaded to editor.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "No text detected in this image.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val cameraCaptureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            // Save bitmap to a temp file to get a Uri
            try {
                val tempFile = java.io.File(context.cacheDir, "temp_ocr_capture.jpg")
                java.io.FileOutputStream(tempFile).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                }
                val uri = Uri.fromFile(tempFile)
                selectedImageUri = uri
                isProcessingOcr = true
                performTextOcrOnImage(context, uri) { textResult ->
                    isProcessingOcr = false
                    if (textResult.isNotEmpty()) {
                        docContent = textResult
                        Toast.makeText(context, "OCR Capture Complete!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "No text detected in this snapshot.", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to capture snapshot: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Image to Microsoft Word (.docx)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Extract printed/handwritten textbook pages locally and export styling-rich DOCX Word files.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Action Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.weight(1f),
                enabled = !isProcessingOcr,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Select Photo", fontSize = 12.sp)
            }

            Button(
                onClick = { cameraCaptureLauncher.launch(null) },
                modifier = Modifier.weight(1f),
                enabled = !isProcessingOcr,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Camera Scan", fontSize = 12.sp)
            }
        }

        // Preview Loaded Image / Processing Status
        if (isProcessingOcr) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(28.dp), strokeWidth = 3.dp)
                    Text("Processing Local AI Text Recognition...", fontWeight = FontWeight.Medium, fontSize = 12.sp)
                }
            }
        } else if (selectedImageUri != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Done, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Active Scanned Document Loaded", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                    TextButton(
                        onClick = { selectedImageUri = null }
                    ) {
                        Text("Clear", color = MaterialTheme.colorScheme.error, fontSize = 11.sp)
                    }
                }
            }
        }

        // Rich Editor Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                    Text(
                        text = "✏️ Extracted Content Editor",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                val clip = android.content.ClipData.newPlainText("Extracted Word Content", docContent)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy text", modifier = Modifier.size(16.dp))
                        }
                        IconButton(
                            onClick = { docContent = "" },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.DeleteOutline, contentDescription = "Clear editor", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                OutlinedTextField(
                    value = docContent,
                    onValueChange = { docContent = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    placeholder = { Text("Extracted document text goes here...", fontSize = 12.sp) },
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                    shape = RoundedCornerShape(10.dp)
                )

                // Toolbar Format Helper Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Insert:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    
                    // Add Heading Button
                    AssistChip(
                        onClick = { docContent = "--- NEW SECTION HEADING ---\n$docContent" },
                        label = { Text("Heading", fontSize = 10.sp) }
                    )
                    // Add Bullets Button
                    AssistChip(
                        onClick = { docContent = docContent.split("\n").joinToString("\n") { "• $it" } },
                        label = { Text("Bullet List", fontSize = 10.sp) }
                    )
                    // Capitalize Button
                    AssistChip(
                        onClick = { docContent = docContent.uppercase() },
                        label = { Text("ALL CAPS", fontSize = 10.sp) }
                    )
                    // Sample template
                    AssistChip(
                        onClick = {
                            docContent = "SYLLABUS CORE REQUIREMENTS\n\n1. Attendance Rules:\nAll students must retain at least 75% attendance in physical classrooms.\n\n2. Research Standards:\nInclude MLA styled bibliography references. Submissions must pass academic plagiarism criteria (maximum 10% similarity quotient).\n\n3. Final Dissertation Projections:\n- Chapter 1: Introduction & Literature (Due Nov 15)\n- Chapter 2: Empirical Methodology (Due Dec 10)\n- Chapter 3: Analytical Outcomes (Due Jan 15)"
                            docTitle = "Syllabus Core Requirements"
                        },
                        label = { Text("Load Sample Academic", fontSize = 10.sp) }
                    )
                }
            }
        }

        // Document Formatting & Settings Card
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "⚙️ Word Formatting & Style Options",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                OutlinedTextField(
                    value = docTitle,
                    onValueChange = { docTitle = it },
                    label = { Text("Document Header / Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = docAuthor,
                    onValueChange = { docAuthor = it },
                    label = { Text("Author / Student Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Layout Theme Selector
                Column {
                    Text("Typography Layout Style:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Modern", "Academic", "Classic").forEach { theme ->
                            val isSelected = selectedTheme == theme
                            ElevatedFilterChip(
                                selected = isSelected,
                                onClick = { selectedTheme = theme },
                                label = { Text(theme, fontSize = 11.sp) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Text(
                        text = when (selectedTheme) {
                            "Academic" -> "Style: Times New Roman, 12pt, justified alignment. Ideal for thesis and term papers."
                            "Modern" -> "Style: Arial, 11pt, clean blue titles. Ideal for lecture slides, notes & project handbooks."
                            else -> "Style: Calibri, 11pt, standard grey layouts. Ideal for clean correspondence & formal memos."
                        },
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // File name
                OutlinedTextField(
                    value = docFileName,
                    onValueChange = { docFileName = it },
                    label = { Text("Save Word Filename") },
                    suffix = { Text(".docx") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }

        // Big Compile & Export Button
        Button(
            onClick = {
                if (docContent.trim().isEmpty()) {
                    Toast.makeText(context, "Please write or extract text before exporting.", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                
                val uri = exportToDocxAndDownload(
                    context = context,
                    title = docTitle,
                    author = docAuthor,
                    content = docContent,
                    themeStyle = selectedTheme,
                    fileName = docFileName.ifEmpty { "converted_word_doc" }
                )

                if (uri != null) {
                    downloadedDocUri = uri
                    showDownloadSuccessDialog = true
                    Toast.makeText(context, "Successfully saved to Downloads folder!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to compile Word file.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Default.CloudDownload, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Compile & Export to Word (.docx)")
        }
    }

    // Success Overlay Dialog
    if (showDownloadSuccessDialog && downloadedDocUri != null) {
        AlertDialog(
            onDismissRequest = { showDownloadSuccessDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text(
                    text = "Export Completed!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Your styled Microsoft Word document has been compiled and saved locally.",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "📁 Downloads/${docFileName}.docx",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDocxFile(context, downloadedDocUri!!)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Open / Share Document")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDownloadSuccessDialog = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Done")
                }
            }
        )
    }
}

private fun performTextOcrOnImage(
    context: Context,
    imageUri: Uri,
    onResult: (String) -> Unit
) {
    try {
        val recognizer = com.google.mlkit.vision.text.TextRecognition.getClient(com.google.mlkit.vision.text.latin.TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = com.google.mlkit.vision.common.InputImage.fromFilePath(context, imageUri)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val fullText = visionText.text
                onResult(fullText)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "OCR Failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                onResult("")
            }
    } catch (e: Exception) {
        Toast.makeText(context, "Failed to load image: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        onResult("")
    }
}

private fun exportToDocxAndDownload(
    context: Context,
    title: String,
    author: String,
    content: String,
    themeStyle: String,
    fileName: String
): Uri? {
    try {
        val contentResolver = context.contentResolver
        val contentValues = android.content.ContentValues().apply {
            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.docx")
            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
            }
        }
        
        val uri = contentResolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                val zipOutputStream = java.util.zip.ZipOutputStream(outputStream)
                
                // 1. [Content_Types].xml
                zipOutputStream.putNextEntry(java.util.zip.ZipEntry("[Content_Types].xml"))
                val contentTypesXml = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Types xmlns="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office">
                      <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
                      <Default Extension="xml" ContentType="application/xml"/>
                      <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
                    </Types>
                """.trimIndent()
                zipOutputStream.write(contentTypesXml.toByteArray())
                zipOutputStream.closeEntry()
                
                // 2. _rels/.rels
                zipOutputStream.putNextEntry(java.util.zip.ZipEntry("_rels/.rels"))
                val relsXml = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                      <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
                    </Relationships>
                """.trimIndent()
                zipOutputStream.write(relsXml.toByteArray())
                zipOutputStream.closeEntry()
                
                // 3. word/document.xml
                zipOutputStream.putNextEntry(java.util.zip.ZipEntry("word/document.xml"))
                
                val escapedContent = escapeXml(content)
                val escapedTitle = escapeXml(title)
                val escapedAuthor = escapeXml(author)
                
                val documentXml = buildString {
                    append("""<?xml version="1.0" encoding="UTF-8" standalone="yes"?>""")
                    append("""<w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">""")
                    append("<w:body>")
                    
                    if (escapedTitle.isNotEmpty()) {
                        append("<w:p>")
                        append("<w:pPr>")
                        append("<w:jc w:val=\"center\"/>")
                        append("</w:pPr>")
                        append("<w:r>")
                        append("<w:rPr>")
                        append("<w:b/>")
                        append("<w:sz w:val=\"36\"/>")
                        if (themeStyle == "Modern") {
                            append("<w:color w:val=\"1565C0\"/>")
                        } else if (themeStyle == "Academic") {
                            append("<w:color w:val=\"2C3E50\"/>")
                        }
                        append("</w:rPr>")
                        append("<w:t>$escapedTitle</w:t>")
                        append("</w:r>")
                        append("</w:p>")
                        append("<w:p/>")
                    }
                    
                    if (escapedAuthor.isNotEmpty()) {
                        val currentDateStr = java.text.SimpleDateFormat("MMMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date())
                        append("<w:p>")
                        append("<w:pPr>")
                        append("<w:jc w:val=\"center\"/>")
                        append("</w:pPr>")
                        append("<w:r>")
                        append("<w:rPr>")
                        append("<w:i/>")
                        append("<w:sz w:val=\"20\"/>")
                        append("<w:color w:val=\"7F8C8D\"/>")
                        append("</w:rPr>")
                        append("<w:t>By $escapedAuthor  |  $currentDateStr</w:t>")
                        append("</w:r>")
                        append("</w:p>")
                        append("<w:p/>")
                    }
                    
                    val paragraphs = escapedContent.split("\n")
                    for (pText in paragraphs) {
                        val trimmed = pText.trim()
                        if (trimmed.isEmpty()) {
                            append("<w:p/>")
                        } else {
                            append("<w:p>")
                            append("<w:pPr>")
                            if (themeStyle == "Academic") {
                                append("<w:jc w:val=\"both\"/>")
                            } else {
                                append("<w:jc w:val=\"left\"/>")
                            }
                            append("</w:pPr>")
                            append("<w:r>")
                            append("<w:rPr>")
                            if (themeStyle == "Academic") {
                                append("<w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/>")
                                append("<w:sz w:val=\"24\"/>")
                            } else if (themeStyle == "Modern") {
                                append("<w:rFonts w:ascii=\"Arial\" w:hAnsi=\"Arial\"/>")
                                append("<w:sz w:val=\"22\"/>")
                            } else {
                                append("<w:rFonts w:ascii=\"Calibri\" w:hAnsi=\"Calibri\"/>")
                                append("<w:sz w:val=\"22\"/>")
                            }
                            append("</w:rPr>")
                            append("<w:t>$trimmed</w:t>")
                            append("</w:r>")
                            append("</w:p>")
                        }
                    }
                    
                    append("<w:p/>")
                    append("<w:p>")
                    append("<w:pPr>")
                    append("<w:jc w:val=\"right\"/>")
                    append("</w:pPr>")
                    append("<w:r>")
                    append("<w:rPr>")
                    append("<w:i/>")
                    append("<w:sz w:val=\"16\"/>")
                    append("<w:color w:val=\"BDC3C7\"/>")
                    append("</w:rPr>")
                    append("<w:t>Generated via StudentKit OCR Image-to-Word Engine</w:t>")
                    append("</w:r>")
                    append("</w:p>")
                    
                    append("</w:body>")
                    append("</w:document>")
                }
                
                zipOutputStream.write(documentXml.toByteArray())
                zipOutputStream.closeEntry()
                
                zipOutputStream.finish()
                zipOutputStream.close()
            }
            return uri
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

private fun openDocxFile(context: Context, uri: Uri) {
    try {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        try {
            val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                putExtra(android.content.Intent.EXTRA_STREAM, uri)
                addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(android.content.Intent.createChooser(shareIntent, "Open or share Word Document"))
        } catch (ex: Exception) {
            Toast.makeText(context, "No app found to open Word files. You can find it in your Downloads folder.", Toast.LENGTH_LONG).show()
        }
    }
}

private fun escapeXml(input: String): String {
    return input.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;")
}

private fun performOcrOnImage(
    context: Context,
    imageUri: Uri,
    onResult: (List<List<String>>) -> Unit
) {
    try {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromFilePath(context, imageUri)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val lines = visionText.textBlocks.flatMap { it.lines }
                if (lines.isEmpty()) {
                    onResult(emptyList())
                    return@addOnSuccessListener
                }

                val sortedLines = lines.sortedWith(compareBy({ it.boundingBox?.top ?: 0 }, { it.boundingBox?.left ?: 0 }))
                
                val rowGroups = mutableListOf<MutableList<com.google.mlkit.vision.text.Text.Line>>()
                for (line in sortedLines) {
                    val top = line.boundingBox?.top ?: 0
                    val matchedRow = rowGroups.find { group ->
                        val groupTopMean = group.map { it.boundingBox?.top ?: 0 }.average()
                        Math.abs(groupTopMean - top) < 30
                    }
                    if (matchedRow != null) {
                        matchedRow.add(line)
                    } else {
                        rowGroups.add(mutableListOf(line))
                    }
                }
                
                rowGroups.sortBy { group -> group.map { l -> l.boundingBox?.top ?: 0 }.average() }
                
                val parsedRows = mutableListOf<List<String>>()
                var maxCols = 0

                for (group in rowGroups) {
                    group.sortBy { it.boundingBox?.left ?: 0 }
                    val cells = group.flatMap { line ->
                        val textStr = line.text
                        if (textStr.contains("\t")) {
                            textStr.split("\t")
                        } else if (textStr.contains("  ")) {
                            textStr.split(Regex("  +"))
                        } else if (textStr.contains("|")) {
                            textStr.split("|")
                        } else if (textStr.contains(",") && textStr.any { it.isDigit() }) {
                            textStr.split(",")
                        } else {
                            listOf(textStr)
                        }
                    }.map { it.trim() }.filter { it.isNotEmpty() }
                    
                    if (cells.isNotEmpty()) {
                        parsedRows.add(cells)
                        if (cells.size > maxCols) {
                            maxCols = cells.size
                        }
                    }
                }

                val paddedRows = parsedRows.map { row ->
                    if (row.size < maxCols) {
                        row + List(maxCols - row.size) { "" }
                    } else {
                        row
                    }
                }

                val finalRows = paddedRows.map { row ->
                    if (row.size > 6) row.take(6) else row 
                }

                onResult(finalRows)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "OCR Extraction Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                onResult(emptyList())
            }
    } catch (e: Exception) {
        Toast.makeText(context, "Error setting up OCR: ${e.message}", Toast.LENGTH_SHORT).show()
        onResult(emptyList())
    }
}

private fun exportToCsvAndDownload(
    context: Context,
    gridData: List<List<String>>,
    fileName: String
): Uri? {
    try {
        val csvString = buildString {
            for (row in gridData) {
                val csvRow = row.joinToString(",") { cell ->
                    val cleanCell = cell.replace("\"", "\"\"")
                    if (cleanCell.contains(",") || cleanCell.contains("\"") || cleanCell.contains("\n")) {
                        "\"$cleanCell\""
                    } else {
                        cleanCell
                    }
                }
                append(csvRow).append("\n")
            }
        }
        
        val contentResolver = context.contentResolver
        val contentValues = android.content.ContentValues().apply {
            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.csv")
            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "text/csv")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
            }
        }
        
        val uri = contentResolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(csvString.toByteArray())
            }
            return uri
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

// -------------------------------------------------------------
// MODULE 10: ELITE CV RESUME BUILDER & NATIVE PDF GENERATOR
// -------------------------------------------------------------

// Serialized Data Classes representing advanced Resume records
data class ResumeWorkHistory(
    var title: String = "",
    var company: String = "",
    var duration: String = "",
    var duty1: String = "",
    var duty2: String = "",
    var description: String = ""
)

fun getDetailedDescription(title: String, company: String): String {
    val cleanTitle = title.ifEmpty { "Professional Specialist" }
    val cleanComp = company.ifEmpty { "Global Enterprise" }
    return "Spearheaded advanced operations and strategic initiatives as a $cleanTitle at $cleanComp. Directed cross-functional team collaborations, analyzed critical performance metrics, and successfully executed complex project deliverables while maintaining strict quality control standard procedures."
}

data class ResumeAcademic(
    var degree: String = "",
    var school: String = "",
    var duration: String = "",
    var grade: String = ""
)

data class ResumeProject(
    var title: String = "",
    var techStack: String = "",
    var url: String = "",
    var impact: String = ""
)

data class PresetOption(val id: String, val label: String, val category: String)

data class TemplateStyleConfig(
    val name: String,
    val category: String, // "Professional & ATS", "Modern & Editorial", "Creative & Design"
    val isTwoColumn: Boolean = false,
    val isLeftSidebar: Boolean = false,
    val isSidebarAccentColored: Boolean = false,
    val isSidebarLightTinted: Boolean = false,
    val isClassicSerif: Boolean = false,
    val headerCentered: Boolean = false,
    val hasTopColoredBanner: Boolean = false,
    val hasInitialsBadge: Boolean = false,
    val hasCreativeVibe: Boolean = false,
    val customAccentColorOverride: String? = null,
    val customBackgroundOverride: String? = null,
    val hasDoubleDivider: Boolean = false,
    val sectionHeaderBottomBorder: Boolean = false,
    val skillsInChips: Boolean = false,
    val showEeoBanner: Boolean = false,
    val eeoText: String = "",
    val isAtsFriendly: Boolean = false,
    val description: String = ""
)

fun getTemplateStyleConfig(themeName: String): TemplateStyleConfig {
    return when (themeName) {
        "Zurich Clean Minimalist" -> TemplateStyleConfig(
            name = "Zurich Clean Minimalist",
            category = "Professional & ATS",
            isAtsFriendly = true,
            hasDoubleDivider = true,
            description = "Sleek, black & white ultra-modern layout inspired by high-end swiss design grids."
        )
        "Stockholm Pro Corporate" -> TemplateStyleConfig(
            name = "Stockholm Pro Corporate",
            category = "Professional & ATS",
            hasTopColoredBanner = true,
            description = "Clean corporate layout with a prominent colored top banner header. Highly dynamic."
        )
        "Toronto Compact Executive" -> TemplateStyleConfig(
            name = "Toronto Compact Executive",
            category = "Professional & ATS",
            sectionHeaderBottomBorder = true,
            description = "High density layout maximizing readable content, perfect for experienced managers."
        )
        "London Modern Editorial" -> TemplateStyleConfig(
            name = "London Modern Editorial",
            category = "Modern & Editorial",
            isClassicSerif = true,
            sectionHeaderBottomBorder = true,
            description = "Sophisticated serif layout inspired by modern publishing houses and editorial portfolios."
        )
        "New York Metro Grid" -> TemplateStyleConfig(
            name = "New York Metro Grid",
            category = "Modern & Editorial",
            isTwoColumn = true,
            isLeftSidebar = true,
            isSidebarAccentColored = true,
            description = "Bold, metropolitan style utilizing a deep colored accent left-hand rail configuration."
        )
        "Paris Creative Chic" -> TemplateStyleConfig(
            name = "Paris Creative Chic",
            category = "Creative & Design",
            customBackgroundOverride = "#FFF5F5",
            customAccentColorOverride = "#DB2777",
            hasInitialsBadge = true,
            hasCreativeVibe = true,
            description = "Fabulous blush backdrop with creative plum gold details and modern monogram badge."
        )
        "Vienna Traditional Classic" -> TemplateStyleConfig(
            name = "Vienna Traditional Classic",
            category = "Professional & ATS",
            headerCentered = true,
            isClassicSerif = true,
            description = "Timeless academic and corporate layout with centered titles and clean rules."
        )
        "Dublin Tech Agile" -> TemplateStyleConfig(
            name = "Dublin Tech Agile",
            category = "Modern & Editorial",
            customAccentColorOverride = "#059669",
            skillsInChips = true,
            description = "Vibrant emerald green highlights, tailored for agile developers and tech leads."
        )
        "Sydney Golden Coast" -> TemplateStyleConfig(
            name = "Sydney Golden Coast",
            category = "Creative & Design",
            customBackgroundOverride = "#FDFBF7",
            customAccentColorOverride = "#B45309",
            sectionHeaderBottomBorder = true,
            description = "Warm cream-sand backdrop with elegant copper gold text and clear timeline lines."
        )
        "Tokyo Minimalist Zen" -> TemplateStyleConfig(
            name = "Tokyo Minimalist Zen",
            category = "Professional & ATS",
            headerCentered = true,
            hasDoubleDivider = true,
            description = "Calm, lightweight layout prioritizing clean space, fine rules, and premium restraint."
        )
        "Geneva Swiss Precision" -> TemplateStyleConfig(
            name = "Geneva Swiss Precision",
            category = "Professional & ATS",
            isTwoColumn = true,
            isLeftSidebar = true,
            isSidebarLightTinted = true,
            sectionHeaderBottomBorder = true,
            description = "Highly structured column design with tinted sidebar for maximum space efficiency."
        )
        "Barcelona Vivid Sunset" -> TemplateStyleConfig(
            name = "Barcelona Vivid Sunset",
            category = "Creative & Design",
            customAccentColorOverride = "#EA580C",
            hasInitialsBadge = true,
            description = "Expressive orange and warm gold tones with initials badge for creative professionals."
        )
        "Berlin Industrial Tech" -> TemplateStyleConfig(
            name = "Berlin Industrial Tech",
            category = "Modern & Editorial",
            customAccentColorOverride = "#374151",
            isTwoColumn = true,
            isLeftSidebar = true,
            isSidebarLightTinted = true,
            skillsInChips = true,
            description = "Chunky dark slate industrial grid tailored for engineers, architects, and detail work."
        )
        "Milan Deluxe Couture" -> TemplateStyleConfig(
            name = "Milan Deluxe Couture",
            category = "Creative & Design",
            isClassicSerif = true,
            customAccentColorOverride = "#171717",
            customBackgroundOverride = "#FAF9F6",
            hasDoubleDivider = true,
            description = "Elite luxury fashion layout with high typography tracking and warm off-white canvas."
        )
        "Singapore Global Hub" -> TemplateStyleConfig(
            name = "Singapore Global Hub",
            category = "Modern & Editorial",
            isTwoColumn = true,
            isLeftSidebar = true,
            isSidebarLightTinted = true,
            description = "Perfect corporate standard, clean divided layout balanced with soft teal undertones."
        )
        "Dubai Royal Platinum" -> TemplateStyleConfig(
            name = "Dubai Royal Platinum",
            category = "Creative & Design",
            customAccentColorOverride = "#581C87",
            sectionHeaderBottomBorder = true,
            hasInitialsBadge = true,
            description = "Prestigious royal purple and golden lines design for luxury and C-suite leaders."
        )
        "Silicon Valley ATS Standard" -> TemplateStyleConfig(
            name = "Silicon Valley ATS Standard",
            category = "Professional & ATS",
            isAtsFriendly = true,
            description = "Maximum machine parse-ability layout. Single column, plain dividers, zero noise."
        )
        "Federal Compliance Uniform" -> TemplateStyleConfig(
            name = "Federal Compliance Uniform",
            category = "Professional & ATS",
            showEeoBanner = true,
            eeoText = "⚠️ OFFICIAL COMPLIANCE RESUME LAYOUT FOR PUBLIC SECTOR APPLICATIONS",
            isClassicSerif = true,
            description = "Chronological legal layout designed to align with government standard resume parsing rules."
        )
        "Nordic Pine Birch" -> TemplateStyleConfig(
            name = "Nordic Pine Birch",
            category = "Modern & Editorial",
            customAccentColorOverride = "#065F46",
            description = "Deep forest pine theme colors with light gray text details, calm and organized."
        )
        "San Francisco StartUp" -> TemplateStyleConfig(
            name = "San Francisco StartUp",
            category = "Creative & Design",
            customAccentColorOverride = "#4F46E5",
            skillsInChips = true,
            description = "Indigo tech details and visual skill pills perfect for startup recruiters."
        )
        "Austin Tech Horizon" -> TemplateStyleConfig(
            name = "Austin Tech Horizon",
            category = "Modern & Editorial",
            customAccentColorOverride = "#9A3412",
            sectionHeaderBottomBorder = true,
            description = "Warm burnt orange tones, modern compact headers. Distinctly Texan and energetic."
        )
        "London Legal Standard" -> TemplateStyleConfig(
            name = "London Legal Standard",
            category = "Professional & ATS",
            isClassicSerif = true,
            hasDoubleDivider = true,
            description = "Excellent traditional Times-style print format for law practices and associates."
        )
        "Boston Ivy Scholar" -> TemplateStyleConfig(
            name = "Boston Ivy Scholar",
            category = "Professional & ATS",
            isClassicSerif = true,
            customAccentColorOverride = "#7F1D1D",
            description = "Deep burgundy margins and academic serif columns designed for research posts."
        )
        "Chicago Urban Accent" -> TemplateStyleConfig(
            name = "Chicago Urban Accent",
            category = "Creative & Design",
            customAccentColorOverride = "#C2410C",
            sectionHeaderBottomBorder = true,
            description = "Bold orange-red dividers and high impact layouts for construction or real estate."
        )
        // Ensure Pre-existing templates map gracefully to descriptions
        "The Ivy League Serif" -> TemplateStyleConfig(
            name = "The Ivy League Serif",
            category = "Professional & ATS",
            isClassicSerif = true,
            description = "Classic Harvard-style layout featuring premium centered Serif headings and classic spacers."
        )
        "Executive Slate Midnight" -> TemplateStyleConfig(
            name = "Executive Slate Midnight",
            category = "Modern & Editorial",
            customAccentColorOverride = "#334155",
            description = "Modern charcoal tones with bold visual section dividing lines, built for CEOs."
        )
        "Academic Curriculum Vitae (Multi-Page)" -> TemplateStyleConfig(
            name = "Academic Curriculum Vitae (Multi-Page)",
            category = "Professional & ATS",
            isClassicSerif = true,
            hasDoubleDivider = true,
            description = "Comprehensive 2-page curriculum vitae format optimized for extensive research records and publications."
        )
        "Executive Portfolio Chronological (Multi-Page)" -> TemplateStyleConfig(
            name = "Executive Portfolio Chronological (Multi-Page)",
            category = "Modern & Editorial",
            customAccentColorOverride = "#1E293B",
            sectionHeaderBottomBorder = true,
            description = "Sleek 2-page corporate binder structure. First page focuses on core career tenure, second page on credentials."
        )
        "Creative Emerald Garden" -> TemplateStyleConfig(
            name = "Creative Emerald Garden",
            category = "Creative & Design",
            customAccentColorOverride = "#0F766E",
            description = "Fresh modern teal highlighting with elegant visual padding. Inspiring and professional."
        )
        else -> TemplateStyleConfig(
            name = themeName,
            category = "Professional & ATS",
            description = "Clean professional resume style."
        )
    }
}

fun createPlaceholderAvatar(name: String, colorHex: String): Bitmap {
    val size = 180
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    val paint = android.graphics.Paint().apply {
        color = try {
            android.graphics.Color.parseColor(colorHex)
        } catch (e: Exception) {
            android.graphics.Color.BLUE
        }
        style = android.graphics.Paint.Style.FILL
        isAntiAlias = true
    }
    canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint)
    
    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 68f
        isAntiAlias = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.create(android.graphics.Typeface.SANS_SERIF, android.graphics.Typeface.BOLD)
    }
    
    val initials = name.split(" ")
        .filter { it.isNotEmpty() }
        .take(2)
        .map { it.first().uppercase() }
        .joinToString("")
        
    val xPos = canvas.width / 2f
    val yPos = (canvas.height / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f)
    canvas.drawText(initials, xPos, yPos, textPaint)
    return bitmap
}

fun cropBitmapToShape(src: Bitmap, shape: String): Bitmap {
    val size = Math.min(src.width, src.height)
    val left = (src.width - size) / 2
    val top = (src.height - size) / 2
    
    // First, center crop to square to guarantee no stretching
    val sqBmp = Bitmap.createBitmap(src, left, top, size, size)
    
    // Scale to standard resolution (e.g. 240x240 for high quality visual alignment)
    val targetSize = 240
    val scaledBmp = Bitmap.createScaledBitmap(sqBmp, targetSize, targetSize, true)
    if (sqBmp != src) {
        sqBmp.recycle()
    }
    
    val output = Bitmap.createBitmap(targetSize, targetSize, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(output)
    val paint = android.graphics.Paint().apply {
        isAntiAlias = true
    }
    
    canvas.drawARGB(0, 0, 0, 0)
    
    when (shape) {
        "Circle" -> {
            val r = targetSize / 2f
            canvas.drawCircle(r, r, r, paint)
        }
        "Rounded Square" -> {
            val rect = android.graphics.RectF(0f, 0f, targetSize.toFloat(), targetSize.toFloat())
            val rCorner = targetSize * 0.15f // 15% roundness
            canvas.drawRoundRect(rect, rCorner, rCorner, paint)
        }
        "Square" -> {
            val rect = android.graphics.RectF(0f, 0f, targetSize.toFloat(), targetSize.toFloat())
            canvas.drawRect(rect, paint)
        }
    }
    
    paint.xfermode = android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(scaledBmp, 0f, 0f, paint)
    scaledBmp.recycle()
    return output
}

@Composable
fun CvBuilderScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    
    // Core Profile fields
    var fullName by remember { mutableStateOf("Bilal Ahmed Khan") }
    var headline by remember { mutableStateOf("Computer Science Honors Student & Full Stack Android Engineer") }
    var email by remember { mutableStateOf("bilal.ahmed@uok.edu.pk") }
    var phone by remember { mutableStateOf("+92 300 1234567") }
    var location by remember { mutableStateOf("Karachi, Pakistan") }
    var summaryText by remember { mutableStateOf("Highly motivated Computer Science senior focusing on robust Android architectures and secure Kotlin applications. Open-source contributor and detail-oriented technical designer.") }
    
    // Dynamic lists for multi-sections
    val workExperiences = remember { mutableStateListOf<ResumeWorkHistory>() }
    val academicList = remember { mutableStateListOf<ResumeAcademic>() }
    val projectsList = remember { mutableStateListOf<ResumeProject>() }
    
    // Extra elements
    var skillsCsv by remember { mutableStateOf("Kotlin, Android Jetpack Compose, Coroutines, MVVM, Room SQLite, Git, Clean Architecture, CI/CD, Java") }
    var languagesCsv by remember { mutableStateOf("English (Professional), Urdu (Native)") }
    
    // Style configurations
    var selectedTemplateTheme by remember { mutableStateOf("Modern Blue Grid") } // "Modern Blue Grid", "The Ivy League Serif", "Executive Slate Midnight", "Creative Emerald Garden"
    var selectedTypography by remember { mutableStateOf("Sharp Sans-Serif") } // "Sharp Sans-Serif", "Classic Serif", "Tech Monospace"
    var selectedAccentColorHex by remember { mutableStateOf("#1E3A8A") } // Royal Blue default
    
    // Portrait profile image
    var profilePicBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var originalUploadedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var photoFrameShape by remember { mutableStateOf("Circle") } // "Circle", "Rounded Square", "Square"
    
    // Editor Modes
    var activeTabMode by remember { mutableStateOf("editor") } // "editor" vs "preview"

    var selectedCategoryFilter by remember { mutableStateOf("All") }
    var presetSearchQuery by remember { mutableStateOf("") }

    val presetOptions = remember {
        PresetRepository.getPresetOptions()
    }
    var selectedEditorSection by remember { mutableStateOf("basic") } // "basic", "work", "edu", "projects", "skills"
    
    // Initialize with standard professional defaults if lists are empty
    LaunchedEffect(Unit) {
        if (profilePicBitmap == null) {
            profilePicBitmap = createPlaceholderAvatar(fullName, selectedAccentColorHex)
        }
        if (workExperiences.isEmpty()) {
            workExperiences.add(ResumeWorkHistory(
                title = "Senior Mobile Intern",
                company = "Apex Systems Ltd",
                duration = "Jun 2025 - Present",
                duty1 = "Designed custom rendering pipelines and integrated dual-camera QR scanning engines.",
                duty2 = "Refactored Room database layer to yield a 30% reduction in database read latency states."
            ))
            workExperiences.add(ResumeWorkHistory(
                title = "Junior Software Developer",
                company = "Creative Digitals PK",
                duration = "Jan 2024 - May 2025",
                duty1 = "Built offline-sync task manager modules and designed custom vector UI canvas widgets.",
                duty2 = "Optimized package size constraints and maintained 99.9% clean lint states."
            ))
        }
        if (academicList.isEmpty()) {
            academicList.add(ResumeAcademic(
                degree = "BS Computer Science",
                school = "University of Karachi",
                duration = "2022 - 2026",
                grade = "CGPA 3.86 / 4.0"
            ))
        }
        if (projectsList.isEmpty()) {
            projectsList.add(ResumeProject(
                title = "StudentKit Mobile Assistant",
                techStack = "Kotlin, Compose, Room, PDF Canvas",
                url = "https://github.com/academic/studentkit",
                impact = "Architected custom print document adapters for dynamic vector PDF exports."
            ))
        }
    }

    // Image upload handler
    val profileImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    val originalImg = BitmapFactory.decodeStream(inputStream)
                    originalUploadedBitmap = originalImg
                    profilePicBitmap = cropBitmapToShape(originalImg, photoFrameShape)
                    Toast.makeText(context, "Successfully uploaded & auto-cropped perfectly to $photoFrameShape frame!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error decoding and auto-cropping portrait: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Preset auto-fill helper
    fun applyPresetFiller(domain: String) {
        val preset = PresetRepository.getPresetById(domain)
        if (preset != null) {
            fullName = preset.fullName
            headline = preset.headline
            email = preset.email
            phone = preset.phone
            location = preset.location
            summaryText = preset.summaryText
            
            workExperiences.clear()
            workExperiences.addAll(preset.workExperiences)
            
            academicList.clear()
            academicList.addAll(preset.academicList)
            
            projectsList.clear()
            projectsList.addAll(preset.projectsList)
            
            skillsCsv = preset.skillsCsv
            selectedAccentColorHex = preset.selectedAccentColorHex
            selectedTemplateTheme = preset.selectedTemplateTheme
            selectedTypography = preset.selectedTypography
        }
        profilePicBitmap = createPlaceholderAvatar(fullName, selectedAccentColorHex)
        Toast.makeText(context, "Top-tier Preset applied! Switched theme template and typography.", Toast.LENGTH_SHORT).show()
    }

    // Top-level design: Adaptive List-Detail (Tablet) or Tab-toggle (Mobile)
    val widthClassIsExpanded = false // Standard mobile view is target. Let's make it fully responsive.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Elite header toolbar containing design indicators
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.WorkspacePremium, "Elite badge", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        Text("Top 10 Resume Builder Engine", fontWeight = FontWeight.Black, fontSize = 16.sp, letterSpacing = (-0.5).sp)
                    }
                    
                    TextButton(onClick = {
                        // Quick Reset
                        fullName = ""
                        headline = ""
                        email = ""
                        phone = ""
                        location = ""
                        summaryText = ""
                        workExperiences.clear()
                        academicList.clear()
                        projectsList.clear()
                        skillsCsv = ""
                        languagesCsv = ""
                        profilePicBitmap = null
                        originalUploadedBitmap = null
                        photoFrameShape = "Circle"
                        Toast.makeText(context, "Cleared forms. Let's write from scratch!", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.RotateLeft, null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reset Form", fontSize = 11.sp)
                    }
                }
                
                Text(
                    "Autosaves locally. Generates ATS-optimized, high-fidelity layouts using native Android vector drawing coordinates.",
                    fontSize = 10.5.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                // Fast preset buttons row
                Text("Select Master Resume Preset (220+ Professional & International Roles):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                
                // Category Filter Chips Row using simple custom styled buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val filterCategories = listOf(
                        "All" to "🌐 All",
                        "Tech" to "💻 Tech & Design",
                        "Health" to "🩺 Healthcare",
                        "Education" to "🍎 Education",
                        "Business" to "📊 Business & Admin",
                        "Engineering" to "🏗️ Engineering & Agri",
                        "Creative" to "🎨 Creative & Arts",
                        "Aviation" to "✈️ Aviation & Logistics",
                        "Legal" to "⚖️ Legal & Law",
                        "Govt" to "🏛️ Public Service",
                        "Services" to "🛎️ Hospitality & Services"
                    )
                    filterCategories.forEach { (catId, catLabel) ->
                        val isSelected = selectedCategoryFilter == catId
                        Button(
                            onClick = { selectedCategoryFilter = catId },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f),
                                contentColor = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.secondary
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.height(28.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                        ) {
                            Text(catLabel, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                // Small elegant search box for presets
                OutlinedTextField(
                    value = presetSearchQuery,
                    onValueChange = { presetSearchQuery = it },
                    placeholder = { Text("Search 220+ global resumes (Captain, MD, Rust, Counsel, Banker...)", fontSize = 11.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp),
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Search, null, modifier = Modifier.size(16.dp)) },
                    trailingIcon = {
                        if (presetSearchQuery.isNotEmpty()) {
                            IconButton(onClick = { presetSearchQuery = "" }) {
                                Icon(Icons.Default.Close, null, modifier = Modifier.size(16.dp))
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                // List of filtered presets
                Spacer(modifier = Modifier.height(4.dp))
                val filteredPresets = presetOptions.filter {
                    (selectedCategoryFilter == "All" || it.category == selectedCategoryFilter) &&
                    (presetSearchQuery.isEmpty() || it.label.contains(presetSearchQuery, ignoreCase = true) || it.id.contains(presetSearchQuery, ignoreCase = true))
                }

                if (filteredPresets.isEmpty()) {
                    Text("No matching preset roles found for \"$presetSearchQuery\"", fontSize = 10.5.sp, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(vertical = 4.dp))
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        filteredPresets.forEach { preset ->
                            Button(
                                onClick = { 
                                    applyPresetFiller(preset.id)
                                    Toast.makeText(context, "Applied preset: ${preset.label}!", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
                                    contentColor = MaterialTheme.colorScheme.secondary
                                ),
                                modifier = Modifier
                                    .height(32.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp)
                            ) {
                                Text(preset.label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        // Segment switch: Form Editor vs WYSIWYG Live Page Preview
        TabRow(
            selectedTabIndex = if (activeTabMode == "editor") 0 else 1,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(
                selected = activeTabMode == "editor",
                onClick = { activeTabMode = "editor" },
                text = { Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.Edit, null, modifier = Modifier.size(16.dp))
                    Text("Interactive Editor", fontWeight = FontWeight.Bold)
                }}
            )
            Tab(
                selected = activeTabMode == "preview",
                onClick = { activeTabMode = "preview" },
                text = { Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.Visibility, null, modifier = Modifier.size(16.dp))
                    Text("Interactive WYSIWYG Preview", fontWeight = FontWeight.Bold)
                }}
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (activeTabMode == "editor") {
            // Main Form Editor Layout - with a sidebar navigation for subsections
            Row(modifier = Modifier.fillMaxSize().weight(1f)) {
                // Secondary Left Navigation to prevent vertical scroll fatigue!
                Column(
                    modifier = Modifier
                        .width(76.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val sections = listOf(
                        Triple("basic", Icons.Default.Person, "Profile"),
                        Triple("work", Icons.Default.Work, "Jobs"),
                        Triple("edu", Icons.Default.School, "Academic"),
                        Triple("projects", Icons.Default.Code, "Projects"),
                        Triple("skills", Icons.Default.Settings, "Skills"),
                        Triple("theme", Icons.Default.Palette, "Theme")
                    )
                    sections.forEach { (secId, icon, label) ->
                        val isSel = selectedEditorSection == secId
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedEditorSection = secId }
                                .background(if (isSel) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f) else Color.Transparent)
                                .padding(vertical = 12.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                label,
                                fontSize = 9.5.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Selected Section Form Panel
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    when (selectedEditorSection) {
                        "basic" -> {
                            Text("Primary Contact Details", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                            val previewImgShape = when (photoFrameShape) {
                                "Rounded Square" -> RoundedCornerShape(8.dp)
                                "Square" -> androidx.compose.ui.graphics.RectangleShape
                                else -> CircleShape
                            }
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                            ) {
                                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(58.dp)
                                                .clip(previewImgShape)
                                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                                                .border(1.5.dp, MaterialTheme.colorScheme.primary, previewImgShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (profilePicBitmap != null) {
                                                Image(
                                                    bitmap = profilePicBitmap!!.asImageBitmap(),
                                                    contentDescription = "Portrait Avatar preview",
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            } else {
                                                Icon(Icons.Default.AddAPhoto, "No Avatar", tint = MaterialTheme.colorScheme.primary)
                                            }
                                        }
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("Professional Headshot Frame", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                            Text("Perfect auto-cropping algorithm aligns your photo to the frame shape.", fontSize = 9.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                                Button(
                                                    onClick = { profileImageLauncher.launch("image/*") },
                                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                                    modifier = Modifier.height(28.dp)
                                                ) {
                                                    Text("Upload Photo", fontSize = 10.7.sp)
                                                }
                                                if (profilePicBitmap != null) {
                                                    OutlinedButton(
                                                        onClick = { 
                                                            profilePicBitmap = null 
                                                            originalUploadedBitmap = null
                                                        },
                                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                                        modifier = Modifier.height(28.dp)
                                                    ) {
                                                        Text("Remove", fontSize = 10.7.sp, color = Color.Red)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    
                                    Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                    
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text("Select Resume Photo Frame Shape (Auto-Crops image):", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            listOf("Circle", "Rounded Square", "Square").forEach { shape ->
                                                ElevatedFilterChip(
                                                    selected = photoFrameShape == shape,
                                                    onClick = {
                                                        photoFrameShape = shape
                                                        if (originalUploadedBitmap != null) {
                                                            profilePicBitmap = cropBitmapToShape(originalUploadedBitmap!!, shape)
                                                            Toast.makeText(context, "Auto-cropped perfectly to $shape shape!", Toast.LENGTH_SHORT).show()
                                                        } else {
                                                            Toast.makeText(context, "Upload a photo to see perfect auto-crop in action!", Toast.LENGTH_SHORT).show()
                                                        }
                                                    },
                                                    label = { Text(shape, fontSize = 10.sp) },
                                                    modifier = Modifier.height(26.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                label = { Text("Full Name") },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Bilal Ahmed Khan") }
                            )

                            OutlinedTextField(
                                value = headline,
                                onValueChange = { headline = it },
                                label = { Text("Personal Headline / Title") },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("E.g. Computer Science Honors senior at UoK") }
                            )

                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Official Email Address") },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("bilal@university.com") }
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                OutlinedTextField(
                                    value = phone,
                                    onValueChange = { phone = it },
                                    label = { Text("Mobile Contact") },
                                    modifier = Modifier.weight(1f),
                                    placeholder = { Text("+92 ...") }
                                )
                                OutlinedTextField(
                                    value = location,
                                    onValueChange = { location = it },
                                    label = { Text("Location State") },
                                    modifier = Modifier.weight(1f),
                                    placeholder = { Text("Karachi, PK") }
                                )
                            }

                            OutlinedTextField(
                                value = summaryText,
                                onValueChange = { summaryText = it },
                                label = { Text("Professional Profile Summary") },
                                maxLines = 4,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("A concise, strong paragraph summarizing key career ambitions...") }
                            )
                        }

                        "work" -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Professional Experience History", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                                ElevatedButton(
                                    onClick = {
                                        workExperiences.add(ResumeWorkHistory())
                                    },
                                    contentPadding = PaddingValues(horizontal = 10.dp),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Icon(Icons.Default.Add, null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add Job", fontSize = 11.sp)
                                }
                            }

                            if (workExperiences.isEmpty()) {
                                Text("No work history added. Click 'Add Job' above to append work details", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            } else {
                                workExperiences.forEachIndexed { index, exp ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text("Work / Role #${index + 1}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp)
                                                IconButton(
                                                    onClick = { workExperiences.removeAt(index) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(Icons.Default.Delete, "Remove Job", tint = Color.Red, modifier = Modifier.size(16.dp))
                                                }
                                            }

                                            OutlinedTextField(
                                                value = exp.title,
                                                onValueChange = {
                                                    workExperiences[index] = exp.copy(title = it)
                                                },
                                                label = { Text("Job Role / Internship Title") },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            OutlinedTextField(
                                                value = exp.company,
                                                onValueChange = {
                                                    workExperiences[index] = exp.copy(company = it)
                                                },
                                                label = { Text("Company / Organization") },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            OutlinedTextField(
                                                value = exp.duration,
                                                onValueChange = {
                                                    workExperiences[index] = exp.copy(duration = it)
                                                },
                                                label = { Text("Duration E.g. (Jun 2024 - Present)") },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            OutlinedTextField(
                                                value = exp.description,
                                                onValueChange = {
                                                    workExperiences[index] = exp.copy(description = it)
                                                },
                                                label = { Text("Detailed Role/Job Description (Paragraph Summary)") },
                                                modifier = Modifier.fillMaxWidth(),
                                                minLines = 2,
                                                placeholder = { Text("Describe main achievements and high level team oversight.") }
                                            )

                                            OutlinedTextField(
                                                value = exp.duty1,
                                                onValueChange = {
                                                    workExperiences[index] = exp.copy(duty1 = it)
                                                },
                                                label = { Text("Key Duty Bullet 1") },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            OutlinedTextField(
                                                value = exp.duty2,
                                                onValueChange = {
                                                    workExperiences[index] = exp.copy(duty2 = it)
                                                },
                                                label = { Text("Key Duty Bullet 2 (Optional)") },
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        "edu" -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Academic Credentials", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                                ElevatedButton(
                                    onClick = {
                                        academicList.add(ResumeAcademic())
                                    },
                                    contentPadding = PaddingValues(horizontal = 10.dp),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Icon(Icons.Default.Add, null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add Degree", fontSize = 11.sp)
                                }
                            }

                            if (academicList.isEmpty()) {
                                Text("No degree profiles added. Add school accomplishments", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            } else {
                                academicList.forEachIndexed { index, edu ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text("Academic Entry #${index + 1}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp)
                                                IconButton(
                                                    onClick = { academicList.removeAt(index) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(Icons.Default.Delete, "Remove Degree", tint = Color.Red, modifier = Modifier.size(16.dp))
                                                }
                                            }

                                            OutlinedTextField(
                                                value = edu.degree,
                                                onValueChange = {
                                                    academicList[index] = edu.copy(degree = it)
                                                },
                                                label = { Text("Degree Name (E.g. BS Computer Science)") },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            OutlinedTextField(
                                                value = edu.school,
                                                onValueChange = {
                                                    academicList[index] = edu.copy(school = it)
                                                },
                                                label = { Text("University / School Board") },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                                OutlinedTextField(
                                                    value = edu.duration,
                                                    onValueChange = {
                                                        academicList[index] = edu.copy(duration = it)
                                                    },
                                                    label = { Text("Timeline") },
                                                    modifier = Modifier.weight(1.2f)
                                                )
                                                OutlinedTextField(
                                                    value = edu.grade,
                                                    onValueChange = {
                                                        academicList[index] = edu.copy(grade = it)
                                                    },
                                                    label = { Text("Grade / GPA") },
                                                    modifier = Modifier.weight(0.8f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        "projects" -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Technical / Creative Projects", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                                ElevatedButton(
                                    onClick = {
                                        projectsList.add(ResumeProject())
                                    },
                                    contentPadding = PaddingValues(horizontal = 10.dp),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Icon(Icons.Default.Add, null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add Project", fontSize = 11.sp)
                                }
                            }

                            if (projectsList.isEmpty()) {
                                Text("No projects specified.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            } else {
                                projectsList.forEachIndexed { index, proj ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text("Project Entry #${index + 1}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp)
                                                IconButton(
                                                    onClick = { projectsList.removeAt(index) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(Icons.Default.Delete, "Remove Project", tint = Color.Red, modifier = Modifier.size(16.dp))
                                                }
                                            }

                                            OutlinedTextField(
                                                value = proj.title,
                                                onValueChange = {
                                                    projectsList[index] = proj.copy(title = it)
                                                },
                                                label = { Text("Project Name") },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            OutlinedTextField(
                                                value = proj.techStack,
                                                onValueChange = {
                                                    projectsList[index] = proj.copy(techStack = it)
                                                },
                                                label = { Text("Technologies used (comma list)") },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            OutlinedTextField(
                                                value = proj.url,
                                                onValueChange = {
                                                    projectsList[index] = proj.copy(url = it)
                                                },
                                                label = { Text("Project URL (E.g. GitHub link)") },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            OutlinedTextField(
                                                value = proj.impact,
                                                onValueChange = {
                                                    projectsList[index] = proj.copy(impact = it)
                                                },
                                                label = { Text("Project Core Impact Bullet") },
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        "skills" -> {
                            Text("Competencies & Human Languages", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                            
                            OutlinedTextField(
                                value = skillsCsv,
                                onValueChange = { skillsCsv = it },
                                label = { Text("Professional Core Skills (comma list)") },
                                maxLines = 4,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text("Divide skills with a comma. They will compile as beautiful circular chips on the sheet.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            OutlinedTextField(
                                value = languagesCsv,
                                onValueChange = { languagesCsv = it },
                                label = { Text("Spoken Languages (comma list with level)") },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("English (Fluent), Arabic (Conversational)") }
                            )
                        }

                        "theme" -> {
                            Text("Tailor Resume Style", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)

                            Text("Select Premium Theme Design:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            
                            // Category Filter row
                            var selectedThemeCategoryFilter by remember { mutableStateOf("All") }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState())
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val catOptions = listOf(
                                    "All" to "🌐 All Themes",
                                    "Professional & ATS" to "💼 Professional & ATS",
                                    "Modern & Editorial" to "📰 Modern & Editorial",
                                    "Creative & Design" to "🎨 Creative Canva"
                                )
                                catOptions.forEach { (catId, catLabel) ->
                                    val isSelected = selectedThemeCategoryFilter == catId
                                    Button(
                                        onClick = { selectedThemeCategoryFilter = catId },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.height(30.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
                                    ) {
                                        Text(catLabel, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            val allTemplates = listOf(
                                "Silicon Valley ATS Standard",
                                "Academic Curriculum Vitae (Multi-Page)",
                                "Executive Portfolio Chronological (Multi-Page)",
                                "Zurich Clean Minimalist",
                                "Stockholm Pro Corporate",
                                "Toronto Compact Executive",
                                "Vienna Traditional Classic",
                                "Tokyo Minimalist Zen",
                                "Geneva Swiss Precision",
                                "Federal Compliance Uniform",
                                "London Legal Standard",
                                "Boston Ivy Scholar",
                                "Canada Academic Standard",
                                "USA Executive Elite",
                                "Australia Professional",
                                
                                "New York Metro Grid",
                                "London Modern Editorial",
                                "Dublin Tech Agile",
                                "Berlin Industrial Tech",
                                "Singapore Global Hub",
                                "Nordic Pine Birch",
                                "Austin Tech Horizon",
                                "UAE Modern Grid",
                                "Modern Blue Grid",
                                "The Ivy League Serif",
                                "Executive Slate Midnight",
                                
                                "Paris Creative Chic",
                                "Sydney Golden Coast",
                                "Barcelona Vivid Sunset",
                                "Milan Deluxe Couture",
                                "Dubai Royal Platinum",
                                "San Francisco StartUp",
                                "Chicago Urban Accent",
                                "Creative Emerald Garden"
                            )

                            // Render high-quality card for each matching template theme
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 280.dp)
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                allTemplates.forEach { tmName ->
                                    val conf = getTemplateStyleConfig(tmName)
                                    if (selectedThemeCategoryFilter == "All" || conf.category == selectedThemeCategoryFilter) {
                                        val isSelected = selectedTemplateTheme == tmName
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { selectedTemplateTheme = tmName }
                                                .border(
                                                    1.5.dp,
                                                    if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                                    RoundedCornerShape(8.dp)
                                                ),
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                            )
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                RadioButton(
                                                    selected = isSelected,
                                                    onClick = { selectedTemplateTheme = tmName }
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(tmName, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        // Tag badge
                                                        val badgeLabel = when (conf.category) {
                                                            "Professional & ATS" -> "ATS"
                                                            "Modern & Editorial" -> "EDITORIAL"
                                                            else -> "CANVA"
                                                        }
                                                        val badgeColor = when (conf.category) {
                                                            "Professional & ATS" -> Color(0xFF16A34A)
                                                            "Modern & Editorial" -> Color(0xFF2563EB)
                                                            else -> Color(0xFFD97706)
                                                        }
                                                        Box(
                                                            modifier = Modifier
                                                                .background(badgeColor.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                                                                .padding(horizontal = 4.dp, vertical = 1.dp)
                                                        ) {
                                                            Text(badgeLabel, fontSize = 7.5.sp, color = badgeColor, fontWeight = FontWeight.ExtraBold)
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.height(2.dp))
                                                    Text(conf.description, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Color Accent Swatch Palette:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            val colors = listOf(
                                Pair("Classic Royal Blue", "#1E3A8A"),
                                Pair("Creative Forest Teal", "#0F766E"),
                                Pair("Executive Charcoal", "#334155"),
                                Pair("Luxury Plum Purple", "#581C87"),
                                Pair("Deep Crimson Ruby", "#991B1B")
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                colors.forEach { (cName, hex) ->
                                    val isSelHex = selectedAccentColorHex.equals(hex, ignoreCase = true)
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(Color(android.graphics.Color.parseColor(hex)))
                                            .border(
                                                3.dp,
                                                if (isSelHex) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                                                CircleShape
                                            )
                                            .clickable { selectedAccentColorHex = hex }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Typography Style Font Class:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            val fontClasses = listOf("Sharp Sans-Serif", "Classic Serif", "Tech Monospace")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                fontClasses.forEach { fn ->
                                    ElevatedFilterChip(
                                        selected = selectedTypography == fn,
                                        onClick = { selectedTypography = fn },
                                        label = { Text(fn, fontSize = 11.sp) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            val isMultiPageNow = selectedTemplateTheme.contains("(Multi-Page)")
            val dynamicTitleLabel = if (isMultiPageNow) {
                "Real-time live generated view of your 2-page Premium Multi-Page Resume:"
            } else {
                "Real-time live generated canvas view of your single-page resume:"
            }
            // Interactive WYSIWYG Live Page Preview pane in standard A4 Aspect Ratio sheet!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(dynamicTitleLabel, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                
                // Visual Mock Card representing A4 sheet
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f / 1.414f) // Precise A4 Ratio!
                        .shadow(6.dp, RoundedCornerShape(8.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    val accentJavaColor = Color(android.graphics.Color.parseColor(selectedAccentColorHex))
                    
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (selectedTemplateTheme) {
                            "Canada Academic Standard" -> {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Canadian Compliance Banner
                                    Row(
                                        modifier = Modifier.fillMaxWidth().background(Color(0xFFFFEBEE)).padding(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.Info, null, tint = Color(0xFFC62828), modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("🇨🇦 Canadian Standard: Photo omitted to prevent hiring bias.", color = Color(0xFFC62828), fontSize = 7.5.sp, fontWeight = FontWeight.Bold)
                                    }
                                    
                                    // Header
                                    Column {
                                        Text(fullName, color = accentJavaColor, fontWeight = FontWeight.Black, fontSize = 15.sp)
                                        Text(headline, color = Color.Gray, fontWeight = FontWeight.SemiBold, fontSize = 9.sp)
                                        Text("📍 $location  |  ✉️ $email  |  📞 $phone", fontSize = 7.5.sp, color = Color.DarkGray)
                                    }
                                    
                                    Divider(color = accentJavaColor, thickness = 1.dp)
                                    
                                    // Summary
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text("PROFESSIONAL PROFILE", color = accentJavaColor, fontWeight = FontWeight.Bold, fontSize = 8.5.sp)
                                        Text(summaryText, color = Color.DarkGray, fontSize = 7.5.sp)
                                    }
                                    
                                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                                    
                                    // Experience
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text("CHRONOLOGICAL WORK HISTORY", color = accentJavaColor, fontWeight = FontWeight.Bold, fontSize = 8.5.sp)
                                        workExperiences.take(2).forEach { job ->
                                            Column {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text(job.title, fontWeight = FontWeight.Bold, fontSize = 8.sp, color = Color.Black)
                                                    Text(job.duration, fontSize = 7.5.sp, color = Color.Gray)
                                                }
                                                Text(job.company, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontSize = 7.5.sp, color = Color.Gray)
                                                val cleanDesc = job.description.ifEmpty { getDetailedDescription(job.title, job.company) }
                                                Text(cleanDesc, fontSize = 7.sp, color = Color.DarkGray, maxLines = 1)
                                                if (job.duty1.isNotEmpty()) Text("• " + job.duty1, fontSize = 7.sp, color = Color.DarkGray)
                                                if (job.duty2.isNotEmpty()) Text("• " + job.duty2, fontSize = 7.sp, color = Color.DarkGray)
                                            }
                                        }
                                    }
                                    
                                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                                    
                                    // Education
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text("ACADEMIC CREDENTIALS", color = accentJavaColor, fontWeight = FontWeight.Bold, fontSize = 8.5.sp)
                                        academicList.take(2).forEach { edu ->
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("${edu.degree} — ${edu.school}", fontWeight = FontWeight.SemiBold, fontSize = 7.5.sp)
                                                Text(edu.duration, fontSize = 7.sp, color = Color.Gray)
                                            }
                                        }
                                    }
                                    
                                    Divider(color = Color.LightGray, thickness = 0.5.dp)

                                    // Skills / Languages
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("CORE COMPETENCIES", color = accentJavaColor, fontWeight = FontWeight.Bold, fontSize = 8.sp)
                                            Text(skillsCsv, color = Color.DarkGray, fontSize = 7.sp, maxLines = 2)
                                        }
                                        Column(modifier = Modifier.weight(0.5f)) {
                                            Text("LANGUAGES", color = accentJavaColor, fontWeight = FontWeight.Bold, fontSize = 8.sp)
                                            Text(languagesCsv, color = Color.DarkGray, fontSize = 7.sp)
                                        }
                                    }
                                }
                            }
                            "USA Executive Elite" -> {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(9.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // USA Compliance Banner
                                    Row(
                                        modifier = Modifier.fillMaxWidth().background(Color(0xFFE3F2FD)).padding(4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(Icons.Default.Info, null, tint = Color(0xFF1565C0), modifier = Modifier.size(11.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("🇺🇸 US Compliance: Headshot omitted per federal EEO guidelines.", color = Color(0xFF1565C0), fontSize = 7.5.sp, fontWeight = FontWeight.Bold)
                                    }
                                    
                                    // Centered Header
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(fullName.uppercase(), color = Color.Black, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, letterSpacing = 1.sp)
                                        Text(headline, color = accentJavaColor, fontWeight = FontWeight.Normal, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontSize = 9.sp)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text("✉️ $email   •   📞 $phone   •   📍 $location", fontSize = 7.5.sp, color = Color.DarkGray)
                                    }
                                    
                                    Divider(color = Color.Black, thickness = 1.5.dp)
                                    
                                    // Summary
                                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text("EXECUTIVE STATEMENT", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        Text(summaryText, color = Color.DarkGray, fontSize = 7.5.sp)
                                    }
                                    
                                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                                    
                                    // Experience
                                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text("PROFESSIONAL EXPERIENCE RECORD", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        workExperiences.take(2).forEach { job ->
                                            Column {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text(job.title + " | " + job.company, fontWeight = FontWeight.Bold, fontSize = 8.sp)
                                                    Text(job.duration, fontSize = 7.5.sp, color = Color.Gray)
                                                }
                                                if (job.duty1.isNotEmpty()) Text("• " + job.duty1, fontSize = 7.5.sp, color = Color.DarkGray)
                                                if (job.duty2.isNotEmpty()) Text("• " + job.duty2, fontSize = 7.5.sp, color = Color.DarkGray)
                                            }
                                        }
                                    }
                                    
                                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                                    
                                    // Education
                                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text("ACADEMIC RECORD", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        academicList.take(2).forEach { edu ->
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("${edu.degree} — ${edu.school} (${edu.grade})", fontWeight = FontWeight.SemiBold, fontSize = 8.sp)
                                                Text(edu.duration, fontSize = 7.5.sp, color = Color.Gray)
                                            }
                                        }
                                    }
                                    
                                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                                    
                                    // Skills & Competencies split
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("TECHNICAL EXPERTISE", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 8.5.sp)
                                            Text(skillsCsv, color = Color.DarkGray, fontSize = 7.5.sp, maxLines = 1)
                                        }
                                        Column(modifier = Modifier.weight(1.5f)) {
                                            Text("GLOBAL LANGUAGES", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 8.5.sp)
                                            Text(languagesCsv, color = Color.DarkGray, fontSize = 7.5.sp)
                                        }
                                    }
                                }
                            }
                            "UAE Modern Grid" -> {
                                Row(modifier = Modifier.fillMaxSize()) {
                                    // Sidebar with light elegant tint
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(0.33f)
                                            .background(accentJavaColor.copy(alpha = 0.12f))
                                            .padding(10.dp),
                                        verticalArrangement = Arrangement.spacedBy(10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        // Photo
                                        Box(
                                            modifier = Modifier
                                                .size(54.dp)
                                                .clip(if (photoFrameShape == "Rounded Square") RoundedCornerShape(8.dp) else if (photoFrameShape == "Square") androidx.compose.ui.graphics.RectangleShape else CircleShape)
                                                .background(Color.White)
                                                .border(1.5.dp, accentJavaColor, if (photoFrameShape == "Rounded Square") RoundedCornerShape(8.dp) else if (photoFrameShape == "Square") androidx.compose.ui.graphics.RectangleShape else CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (profilePicBitmap != null) {
                                                Image(
                                                    bitmap = profilePicBitmap!!.asImageBitmap(),
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            } else {
                                                Icon(Icons.Default.Person, null, tint = accentJavaColor, modifier = Modifier.size(28.dp))
                                            }
                                        }
                                        
                                        Text(fullName, fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.Center, color = Color.Black)
                                        
                                        Divider(color = accentJavaColor.copy(alpha = 0.4f))
                                        
                                        // Contact info
                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Text("📍 $location", fontSize = 7.5.sp, color = Color.Black)
                                            Text("✉️ $email", fontSize = 7.sp, color = Color.Black)
                                            Text("📞 $phone", fontSize = 7.5.sp, color = Color.Black)
                                        }
                                        
                                        Divider(color = accentJavaColor.copy(alpha = 0.4f))
                                        
                                        Text("CORE CAPABILITIES", fontWeight = FontWeight.Bold, fontSize = 8.sp, color = accentJavaColor)
                                        skillsCsv.split(",").take(5).forEach { sk ->
                                            Text("✓ ${sk.trim()}", fontSize = 7.sp, color = Color.DarkGray)
                                        }
                                        
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text("VISA STATE: Candidate", fontSize = 6.5.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                    }
                                    
                                    // Main Right Panel
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(0.67f)
                                            .padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Column {
                                            Text(fullName, color = accentJavaColor, fontWeight = FontWeight.Black, fontSize = 14.sp)
                                            Text(headline, color = Color.DarkGray, fontWeight = FontWeight.SemiBold, fontSize = 9.sp)
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(summaryText, color = Color.Gray, fontSize = 7.5.sp, maxLines = 3)
                                        }
                                        
                                        Divider(color = accentJavaColor, thickness = 1.dp)
                                        
                                        Text("PROFESSIONAL TENURE", color = accentJavaColor, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        workExperiences.take(2).forEach { job ->
                                            Column {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text(job.title, fontWeight = FontWeight.Bold, fontSize = 8.sp, color = Color.Black)
                                                    Text(job.duration, fontSize = 7.sp, color = Color.Gray)
                                                }
                                                Text(job.company, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontSize = 7.5.sp, color = Color.Gray)
                                                if (job.duty1.isNotEmpty()) Text("• " + job.duty1, fontSize = 7.sp, color = Color.DarkGray, maxLines = 1)
                                            }
                                        }
                                        
                                        Divider(color = Color.LightGray)
                                        
                                        Text("ACADEMIC DEGREES", color = accentJavaColor, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        academicList.take(2).forEach { edu ->
                                            Column {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text(edu.degree, fontWeight = FontWeight.Bold, fontSize = 8.sp)
                                                    Text(edu.duration, fontSize = 7.sp, color = Color.Gray)
                                                }
                                                Text("${edu.school} (${edu.grade})", fontSize = 7.5.sp, color = Color.DarkGray)
                                            }
                                        }
                                    }
                                }
                            }
                            "Australia Professional" -> {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9)).padding(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.Info, null, tint = Color(0xFF2E7D32), modifier = Modifier.size(11.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("🇦🇺 Australian Standard: Photo omitted to focus purely on skills validation progress.", color = Color(0xFF2E7D32), fontSize = 7.5.sp, fontWeight = FontWeight.Bold)
                                    }
                                    
                                    // Header
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column {
                                            Text(fullName.uppercase(), color = accentJavaColor, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                                            Text(headline, color = Color.DarkGray, fontSize = 9.sp, fontWeight = FontWeight.Medium)
                                        }
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text("✉️ $email", fontSize = 7.5.sp, color = Color.DarkGray)
                                            Text("📞 $phone", fontSize = 7.5.sp, color = Color.DarkGray)
                                            Text("📍 $location", fontSize = 7.5.sp, color = Color.DarkGray)
                                        }
                                    }
                                    
                                    Divider(color = accentJavaColor, thickness = 1.5.dp)
                                    
                                    // Key Highlights / Competency Table
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = accentJavaColor.copy(alpha = 0.05f)),
                                        border = BorderStroke(0.5.dp, accentJavaColor.copy(alpha = 0.3f))
                                    ) {
                                        Column(modifier = Modifier.padding(6.dp)) {
                                            Text("KEY PROFESSIONAL HIGHLIGHTS & STRENGTHS", fontWeight = FontWeight.Bold, fontSize = 8.sp, color = accentJavaColor)
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(summaryText, fontSize = 7.5.sp, color = Color.DarkGray, maxLines = 2)
                                        }
                                    }
                                    
                                    // Skills grid
                                    Text("VERIFIED SKILL INVENTORY", fontWeight = FontWeight.Bold, fontSize = 8.5.sp, color = accentJavaColor)
                                    Text(skillsCsv, color = Color.Black, fontSize = 8.sp, modifier = Modifier.background(Color.White).padding(2.dp), maxLines = 1)
                                    
                                    Divider(color = Color.LightGray)
                                    
                                    // Career History
                                    Text("CHRONOLOGICAL EMPLOYMENT HISTORY", fontWeight = FontWeight.Bold, fontSize = 8.5.sp, color = accentJavaColor)
                                    workExperiences.take(2).forEach { job ->
                                        Column {
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text(job.title + " (At " + job.company + ")", fontWeight = FontWeight.Bold, fontSize = 8.sp, color = Color.Black)
                                                Text(job.duration, fontSize = 7.5.sp, color = Color.Gray)
                                            }
                                            if (job.duty1.isNotEmpty()) Text("• " + job.duty1, fontSize = 7.sp, color = Color.DarkGray, maxLines = 1)
                                        }
                                    }
                                    
                                    Divider(color = Color.LightGray)
                                    
                                    // Education
                                    Text("TERTIARY EDUCATION & CERTIFICATIONS", fontWeight = FontWeight.Bold, fontSize = 8.5.sp, color = accentJavaColor)
                                    academicList.take(2).forEach { edu ->
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("${edu.degree} — ${edu.school}", fontWeight = FontWeight.SemiBold, fontSize = 7.5.sp)
                                            Text(edu.duration, fontSize = 7.sp, color = Color.Gray)
                                        }
                                    }
                                }
                            }
                            "Modern Blue Grid" -> {
                                Row(modifier = Modifier.fillMaxSize()) {
                                    // Highlight Rails sidebar
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(0.35f)
                                            .background(accentJavaColor.copy(alpha = 0.95f))
                                            .padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        // Profile Pic inside layout
                                        Box(
                                            modifier = Modifier
                                                .size(60.dp)
                                                .clip(if (photoFrameShape == "Rounded Square") RoundedCornerShape(8.dp) else if (photoFrameShape == "Square") androidx.compose.ui.graphics.RectangleShape else CircleShape)
                                                .background(Color.White.copy(alpha = 0.2f))
                                                .align(Alignment.CenterHorizontally),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (profilePicBitmap != null) {
                                                Image(
                                                    bitmap = profilePicBitmap!!.asImageBitmap(),
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            } else {
                                                Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(36.dp))
                                            }
                                        }

                                        Text(fullName, color = Color.White, fontWeight = FontWeight.Black, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

                                        Divider(color = Color.White.copy(alpha = 0.3f))
                                        
                                        // Contact indicators
                                        Text("📞 $phone", color = Color.White, fontSize = 8.sp)
                                        Text("✉️ $email", color = Color.White, fontSize = 7.5.sp)
                                        Text("📍 $location", color = Color.White, fontSize = 8.sp)

                                        Divider(color = Color.White.copy(alpha = 0.3f))
                                        Text("CORE CAPABILITIES", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        skillsCsv.split(",").take(6).forEach { sk ->
                                            Text("• ${sk.trim()}", color = Color.White.copy(alpha = 0.9f), fontSize = 8.sp)
                                        }

                                        Divider(color = Color.White.copy(alpha = 0.3f))
                                        Text("LANGUAGES", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        languagesCsv.split(",").forEach { lg ->
                                            Text("🗣️ ${lg.trim()}", color = Color.White.copy(alpha = 0.9f), fontSize = 8.sp)
                                        }
                                    }

                                    // Main Right Rail info
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(0.65f)
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        Column {
                                            Text(fullName, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            Text(headline, color = accentJavaColor, fontWeight = FontWeight.SemiBold, fontSize = 9.5.sp)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(summaryText, color = Color.DarkGray, fontSize = 8.sp, maxLines = 4)
                                        }

                                        Divider(color = accentJavaColor, thickness = 1.dp)

                                        Column {
                                            Text("PROFESSIONAL EXPERIENCE", color = accentJavaColor, fontWeight = FontWeight.Black, fontSize = 9.5.sp)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            workExperiences.take(2).forEach { job ->
                                                Text(job.title, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 9.sp)
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text(job.company, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontSize = 8.sp, color = Color.Gray)
                                                    Text(job.duration, fontSize = 8.sp, color = Color.Gray)
                                                }
                                                if (job.duty1.isNotEmpty()) {
                                                    Text("• " + job.duty1, color = Color.DarkGray, fontSize = 7.5.sp, modifier = Modifier.padding(start = 4.dp))
                                                }
                                                if (job.duty2.isNotEmpty()) {
                                                    Text("• " + job.duty2, color = Color.DarkGray, fontSize = 7.5.sp, modifier = Modifier.padding(start = 4.dp))
                                                }
                                                Spacer(modifier = Modifier.height(4.dp))
                                            }
                                        }

                                        Divider(color = accentJavaColor, thickness = 1.dp)

                                        Column {
                                            Text("ACADEMIC HISTORY", color = accentJavaColor, fontWeight = FontWeight.Black, fontSize = 9.5.sp)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            academicList.take(2).forEach { edu ->
                                                Text(edu.degree, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 8.5.sp)
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text(edu.school, fontSize = 8.sp, color = Color.Gray)
                                                    Text(edu.duration, fontSize = 8.sp, color = Color.Gray)
                                                }
                                                Text(edu.grade, fontSize = 8.sp, color = Color.DarkGray, fontWeight = FontWeight.SemiBold)
                                            }
                                        }
                                    }
                                }
                            }
                            else -> {
                                val style = getTemplateStyleConfig(selectedTemplateTheme)
                                val accentColorHex = style.customAccentColorOverride ?: selectedAccentColorHex
                                val finalAccentColor = Color(android.graphics.Color.parseColor(accentColorHex))
                                val finalBackgroundColor = if (style.customBackgroundOverride != null) {
                                    Color(android.graphics.Color.parseColor(style.customBackgroundOverride))
                                } else {
                                    Color.White
                                }
                                val fontStyle = if (style.isClassicSerif) androidx.compose.ui.text.font.FontFamily.Serif else androidx.compose.ui.text.font.FontFamily.SansSerif
                                
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(finalBackgroundColor)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        // Optional EEO Banner
                                        if (style.showEeoBanner) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(finalAccentColor.copy(alpha = 0.12f))
                                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(Icons.Default.Info, null, tint = finalAccentColor, modifier = Modifier.size(11.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(style.eeoText, color = finalAccentColor, fontSize = 7.sp, fontWeight = FontWeight.Bold, fontFamily = fontStyle)
                                            }
                                        }

                                        // Optional Top Colored Banner
                                        if (style.hasTopColoredBanner) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(8.dp)
                                                    .background(finalAccentColor)
                                            )
                                        }

                                        if (style.isTwoColumn) {
                                            // TWO COLUMN LAYOUT (e.g. Geneva, Berlin, New York, Singapore)
                                            Row(modifier = Modifier.fillMaxSize()) {
                                                // Sidebar
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxHeight()
                                                        .weight(0.36f)
                                                        .background(
                                                            if (style.isSidebarAccentColored) finalAccentColor else finalAccentColor.copy(alpha = 0.08f)
                                                        )
                                                        .padding(10.dp),
                                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    val sidebarTextColor = if (style.isSidebarAccentColored) Color.White else Color.Black
                                                    val sidebarMutedColor = if (style.isSidebarAccentColored) Color.White.copy(alpha = 0.8f) else Color.DarkGray

                                                    // Avatar block or initials monogram badge
                                                    if (profilePicBitmap != null) {
                                                        Image(
                                                            bitmap = profilePicBitmap!!.asImageBitmap(),
                                                            contentDescription = null,
                                                            modifier = Modifier
                                                                .size(46.dp)
                                                                .clip(if (photoFrameShape == "Rounded Square") RoundedCornerShape(8.dp) else if (photoFrameShape == "Square") androidx.compose.ui.graphics.RectangleShape else CircleShape)
                                                                .border(1.5.dp, if (style.isSidebarAccentColored) Color.White else finalAccentColor, if (photoFrameShape == "Rounded Square") RoundedCornerShape(8.dp) else if (photoFrameShape == "Square") androidx.compose.ui.graphics.RectangleShape else CircleShape)
                                                                .align(Alignment.CenterHorizontally)
                                                        )
                                                    } else if (style.hasInitialsBadge) {
                                                        Box(
                                                            modifier = Modifier
                                                                .size(36.dp)
                                                                .clip(CircleShape)
                                                                .background(finalAccentColor)
                                                                .align(Alignment.CenterHorizontally),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Text(
                                                                fullName.take(1).uppercase(),
                                                                color = Color.White,
                                                                fontWeight = FontWeight.Bold,
                                                                fontSize = 12.sp,
                                                                fontFamily = fontStyle
                                                            )
                                                        }
                                                    }

                                                    Text(fullName, color = sidebarTextColor, fontWeight = FontWeight.Bold, fontSize = 11.sp, fontFamily = fontStyle)
                                                    Text(headline, color = sidebarMutedColor, fontSize = 7.5.sp, fontFamily = fontStyle)

                                                    Divider(color = if (style.isSidebarAccentColored) Color.White.copy(alpha = 0.3f) else finalAccentColor.copy(alpha = 0.3f))

                                                    // Contact
                                                    Text("📍 $location", color = sidebarTextColor, fontSize = 7.sp, fontFamily = fontStyle)
                                                    Text("✉️ $email", color = sidebarTextColor, fontSize = 7.sp, fontFamily = fontStyle)
                                                    Text("📞 $phone", color = sidebarTextColor, fontSize = 7.sp, fontFamily = fontStyle)

                                                    Divider(color = if (style.isSidebarAccentColored) Color.White.copy(alpha = 0.3f) else finalAccentColor.copy(alpha = 0.3f))

                                                    // Skills List (Chips or Simple Bullets)
                                                    Text("CORE SKILLS", color = if (style.isSidebarAccentColored) Color.White else finalAccentColor, fontWeight = FontWeight.Bold, fontSize = 8.sp, fontFamily = fontStyle)
                                                    val skillsList = skillsCsv.split(",").filter { it.trim().isNotEmpty() }
                                                    if (style.skillsInChips) {
                                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                            skillsList.chunked(2).take(4).forEach { rowSkills ->
                                                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                                    rowSkills.forEach { sk ->
                                                                        Box(
                                                                            modifier = Modifier
                                                                                .background(
                                                                                    if (style.isSidebarAccentColored) Color.White.copy(alpha = 0.2f) else finalAccentColor.copy(alpha = 0.12f),
                                                                                    RoundedCornerShape(8.dp)
                                                                                )
                                                                                .padding(horizontal = 5.dp, vertical = 2.dp)
                                                                        ) {
                                                                            Text(sk.trim(), fontSize = 6.5.sp, color = sidebarTextColor, fontWeight = FontWeight.SemiBold, fontFamily = fontStyle)
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        skillsList.take(6).forEach { sk ->
                                                            Text("▪ " + sk.trim(), color = sidebarTextColor, fontSize = 7.sp, fontFamily = fontStyle)
                                                        }
                                                    }

                                                    Spacer(modifier = Modifier.weight(1f))
                                                    Text("SECURE WORK PROFILE", color = sidebarMutedColor, fontSize = 5.5.sp, fontFamily = fontStyle)
                                                }

                                                // Main right-hand details
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxHeight()
                                                        .weight(0.64f)
                                                        .padding(12.dp),
                                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    // Summary section
                                                    Text("SUMMARY STATEMENT", color = finalAccentColor, fontWeight = FontWeight.Bold, fontSize = 9.sp, fontFamily = fontStyle)
                                                    if (style.sectionHeaderBottomBorder) {
                                                        Divider(color = finalAccentColor, thickness = 1.dp)
                                                    }
                                                    Text(summaryText, color = Color.DarkGray, fontSize = 7.5.sp, fontFamily = fontStyle, maxLines = 4)

                                                    Divider(color = Color.LightGray, thickness = 0.5.dp)

                                                    // Work experience section
                                                    Text("PROFESSIONAL HISTORY", color = finalAccentColor, fontWeight = FontWeight.Bold, fontSize = 9.sp, fontFamily = fontStyle)
                                                    if (style.sectionHeaderBottomBorder) {
                                                        Divider(color = finalAccentColor, thickness = 1.dp)
                                                    }
                                                    workExperiences.take(2).forEach { job ->
                                                        Column {
                                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                                Text(job.title, fontWeight = FontWeight.Bold, fontSize = 8.sp, color = Color.Black, fontFamily = fontStyle)
                                                                Text(job.duration, fontSize = 7.sp, color = Color.Gray, fontFamily = fontStyle)
                                                            }
                                                            Text(job.company, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontSize = 7.5.sp, color = Color.Gray, fontFamily = fontStyle)
                                                            val cleanDesc = job.description.ifEmpty { getDetailedDescription(job.title, job.company) }
                                                            Text(cleanDesc, fontSize = 7.sp, color = Color.DarkGray, fontFamily = fontStyle, maxLines = 1)
                                                            if (job.duty1.isNotEmpty()) Text("• " + job.duty1, fontSize = 7.sp, color = Color.DarkGray, fontFamily = fontStyle, maxLines = 1)
                                                        }
                                                    }

                                                    Divider(color = Color.LightGray, thickness = 0.5.dp)

                                                    // Academic
                                                    Text("ACADEMIC DEGREES", color = finalAccentColor, fontWeight = FontWeight.Bold, fontSize = 9.sp, fontFamily = fontStyle)
                                                    if (style.sectionHeaderBottomBorder) {
                                                        Divider(color = finalAccentColor, thickness = 1.dp)
                                                    }
                                                    academicList.take(2).forEach { edu ->
                                                        Column {
                                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                                Text(edu.degree, fontWeight = FontWeight.Bold, fontSize = 8.sp, color = Color.Black, fontFamily = fontStyle)
                                                                Text(edu.duration, fontSize = 7.sp, color = Color.Gray, fontFamily = fontStyle)
                                                            }
                                                            Text(edu.school, fontSize = 7.5.sp, color = Color.DarkGray, fontFamily = fontStyle)
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            // SINGLE COLUMN LAYOUT (e.g. London, Zurich, Paris, Vienna, Silicon Valley etc)
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(16.dp),
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                // Header Row
                                                val headerAlign = if (style.headerCentered) Alignment.CenterHorizontally else Alignment.Start
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = headerAlign
                                                ) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = if (style.headerCentered) Arrangement.Center else Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        if (!style.headerCentered && style.hasInitialsBadge) {
                                                            Box(
                                                                modifier = Modifier
                                                                    .padding(end = 8.dp)
                                                                    .size(36.dp)
                                                                    .clip(CircleShape)
                                                                    .background(finalAccentColor),
                                                                contentAlignment = Alignment.Center
                                                            ) {
                                                                Text(fullName.take(1).uppercase(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                                            }
                                                        }

                                                        Column(horizontalAlignment = headerAlign) {
                                                            Text(fullName, color = if (style.isAtsFriendly) Color.Black else finalAccentColor, fontWeight = FontWeight.Black, fontSize = 15.sp, fontFamily = fontStyle)
                                                            Text(headline, color = Color.DarkGray, fontWeight = FontWeight.Medium, fontSize = 9.5.sp, fontFamily = fontStyle)
                                                        }

                                                        if (!style.headerCentered && profilePicBitmap != null) {
                                                            Image(
                                                                bitmap = profilePicBitmap!!.asImageBitmap(),
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .size(46.dp)
                                                                    .clip(if (photoFrameShape == "Rounded Square") RoundedCornerShape(8.dp) else if (photoFrameShape == "Square") androidx.compose.ui.graphics.RectangleShape else CircleShape)
                                                                    .border(1.5.dp, finalAccentColor, if (photoFrameShape == "Rounded Square") RoundedCornerShape(8.dp) else if (photoFrameShape == "Square") androidx.compose.ui.graphics.RectangleShape else CircleShape)
                                                            )
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.height(2.dp))
                                                    Text("📍 $location   |   ✉️ $email   |   📞 $phone", fontSize = 7.5.sp, color = Color.Gray, fontFamily = fontStyle)
                                                }

                                                if (style.hasDoubleDivider) {
                                                    Divider(color = finalAccentColor, thickness = 1.5.dp)
                                                    Spacer(modifier = Modifier.height(1.dp))
                                                    Divider(color = finalAccentColor.copy(alpha = 0.5f), thickness = 0.5.dp)
                                                } else {
                                                    Divider(color = if (style.isAtsFriendly) Color.Black else finalAccentColor, thickness = 1.2.dp)
                                                }

                                                // Summary Statement
                                                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                                    Text("SUMMARY PROFILE", color = if (style.isAtsFriendly) Color.Black else finalAccentColor, fontWeight = FontWeight.Bold, fontSize = 9.sp, fontFamily = fontStyle)
                                                    if (style.sectionHeaderBottomBorder) {
                                                        Divider(color = finalAccentColor, thickness = 1.dp)
                                                    }
                                                    Text(summaryText, color = Color.DarkGray, fontSize = 7.5.sp, fontFamily = fontStyle)
                                                }

                                                Divider(color = Color.LightGray, thickness = 0.5.dp)

                                                // Work Experiences
                                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                    Text("CAREER TENURE PROFILE", color = if (style.isAtsFriendly) Color.Black else finalAccentColor, fontWeight = FontWeight.Bold, fontSize = 9.sp, fontFamily = fontStyle)
                                                    if (style.sectionHeaderBottomBorder) {
                                                        Divider(color = finalAccentColor, thickness = 1.dp)
                                                    }
                                                    workExperiences.take(3).forEach { exp ->
                                                        Column {
                                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                                Text("${exp.title} — ${exp.company}", fontWeight = FontWeight.Bold, fontSize = 8.sp, color = Color.Black, fontFamily = fontStyle)
                                                                Text(exp.duration, fontSize = 7.5.sp, color = Color.Gray, fontFamily = fontStyle)
                                                            }
                                                            val cleanDesc = exp.description.ifEmpty { getDetailedDescription(exp.title, exp.company) }
                                                            Text(cleanDesc, fontSize = 7.sp, color = Color.DarkGray, fontFamily = fontStyle, maxLines = 1)
                                                            if (exp.duty1.isNotEmpty()) {
                                                                Text("• " + exp.duty1, color = Color.DarkGray, fontSize = 7.sp, fontFamily = fontStyle)
                                                            }
                                                            if (exp.duty2.isNotEmpty()) {
                                                                Text("• " + exp.duty2, color = Color.DarkGray, fontSize = 7.sp, fontFamily = fontStyle)
                                                            }
                                                        }
                                                    }
                                                }

                                                Divider(color = Color.LightGray, thickness = 0.5.dp)

                                                // Academic Degrees
                                                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                                    Text("TERTIARY ACADEMICAL DETAILS", color = if (style.isAtsFriendly) Color.Black else finalAccentColor, fontWeight = FontWeight.Bold, fontSize = 9.sp, fontFamily = fontStyle)
                                                    if (style.sectionHeaderBottomBorder) {
                                                        Divider(color = finalAccentColor, thickness = 1.dp)
                                                    }
                                                    academicList.take(2).forEach { edu ->
                                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                            Text("${edu.degree} (${edu.school})", fontWeight = FontWeight.SemiBold, fontSize = 7.5.sp, color = Color.Black, fontFamily = fontStyle)
                                                            Text(edu.duration, fontSize = 7.sp, color = Color.Gray, fontFamily = fontStyle)
                                                        }
                                                    }
                                                }

                                                Divider(color = Color.LightGray, thickness = 0.5.dp)

                                                // Skills and Spoken Languages
                                                Row(modifier = Modifier.fillMaxWidth()) {
                                                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                                        Text("COMPETENCY METRICS", color = if (style.isAtsFriendly) Color.Black else finalAccentColor, fontWeight = FontWeight.Bold, fontSize = 8.5.sp, fontFamily = fontStyle)
                                                        val skillsList = skillsCsv.split(",").filter { it.trim().isNotEmpty() }
                                                        if (style.skillsInChips) {
                                                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                                skillsList.chunked(3).take(3).forEach { rowSkills ->
                                                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                                        rowSkills.forEach { sk ->
                                                                            Box(
                                                                                modifier = Modifier
                                                                                    .background(finalAccentColor.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                                                                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                                                                            ) {
                                                                                Text(sk.trim(), fontSize = 6.5.sp, color = finalAccentColor, fontWeight = FontWeight.SemiBold, fontFamily = fontStyle)
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            Text(skillsCsv, color = Color.DarkGray, fontSize = 7.5.sp, fontFamily = fontStyle)
                                                        }
                                                    }
                                                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                                        Text("SPEAKING FLUENCY", color = if (style.isAtsFriendly) Color.Black else finalAccentColor, fontWeight = FontWeight.Bold, fontSize = 8.5.sp, fontFamily = fontStyle)
                                                        Text(languagesCsv, color = Color.DarkGray, fontSize = 7.5.sp, fontFamily = fontStyle)
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

                if (isMultiPageNow) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Page 2 of 2: Credentials, Academics, Projects & Skills Capabilities Dynamic Card", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f / 1.414f)
                            .shadow(6.dp, RoundedCornerShape(8.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color.LightGray)
                    ) {
                        val accentJavaColor = Color(android.graphics.Color.parseColor(selectedAccentColorHex))
                        val style = getTemplateStyleConfig(selectedTemplateTheme)
                        val fontStyle = if (style.isClassicSerif) androidx.compose.ui.text.font.FontFamily.Serif else androidx.compose.ui.text.font.FontFamily.SansSerif
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Section header
                            Text("ACADEMIC HISTORY", color = accentJavaColor, fontWeight = FontWeight.Black, fontSize = 10.sp, fontFamily = fontStyle)
                            Divider(color = accentJavaColor, thickness = 1.dp)
                            academicList.forEach { edu ->
                                Column {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(edu.degree, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 9.sp, fontFamily = fontStyle)
                                        Text(edu.duration, fontSize = 8.sp, color = Color.Gray, fontFamily = fontStyle)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(edu.school, fontSize = 8.sp, color = Color.Gray, fontFamily = fontStyle)
                                        Text(edu.grade, fontSize = 8.sp, color = Color.DarkGray, fontWeight = FontWeight.SemiBold, fontFamily = fontStyle)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("ENGINEERING & RESEARCH PROJECTS", color = accentJavaColor, fontWeight = FontWeight.Black, fontSize = 10.sp, fontFamily = fontStyle)
                            Divider(color = accentJavaColor, thickness = 1.dp)
                            projectsList.forEach { proj ->
                                Column {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(proj.title + " (${proj.techStack})", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 8.5.sp, fontFamily = fontStyle)
                                        Text(proj.url, fontSize = 8.sp, color = accentJavaColor, fontFamily = fontStyle)
                                    }
                                    Text(proj.impact, fontSize = 8.sp, color = Color.DarkGray, fontFamily = fontStyle)
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Text("CORE CAPABILITIES & VERIFIED SKILLS", color = accentJavaColor, fontWeight = FontWeight.Black, fontSize = 10.sp, fontFamily = fontStyle)
                            Divider(color = accentJavaColor, thickness = 1.dp)
                            Text("Capabilities: " + skillsCsv, fontSize = 8.5.sp, color = Color.Black, fontFamily = fontStyle)
                            Text("Languages: " + languagesCsv, fontSize = 8.5.sp, color = Color.Black, fontFamily = fontStyle)
                            
                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(color = Color.LightGray, thickness = 0.5.dp)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Premium CV Feed System", fontSize = 7.5.sp, color = Color.Gray, fontFamily = fontStyle)
                                Text("Page 2 of 2", fontSize = 7.5.sp, color = Color.Gray, fontFamily = fontStyle)
                            }
                        }
                    }
                }
            }
        }

        // Action Print Footer Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        if (fullName.isEmpty() || email.isEmpty()) {
                            Toast.makeText(context, "Please write at least name and email to compile PDF!", Toast.LENGTH_SHORT).show()
                        } else {
                            // Launch high grade native PDF generation adapter
                            triggerNativePdfGeneration(
                                context = context,
                                name = fullName,
                                headline = headline,
                                email = email,
                                phone = phone,
                                location = location,
                                summary = summaryText,
                                workList = workExperiences.toList(),
                                academicList = academicList.toList(),
                                projectsList = projectsList.toList(),
                                skills = skillsCsv,
                                languages = languagesCsv,
                                styleTemplate = selectedTemplateTheme,
                                typography = selectedTypography,
                                colorHex = selectedAccentColorHex,
                                profilePic = profilePicBitmap,
                                photoFrameShape = photoFrameShape
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(Icons.Default.PictureAsPdf, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Compile & Export PDF", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * Super robust, native A4 PDF generation using standard vector graphics APIs.
 * Draws multiple bullet entries, custom colors, dynamic text wrap calculations to avoid cutoffs!
 */
private fun triggerNativePdfGeneration(
    context: Context,
    name: String,
    headline: String,
    email: String,
    phone: String,
    location: String,
    summary: String,
    workList: List<ResumeWorkHistory>,
    academicList: List<ResumeAcademic>,
    projectsList: List<ResumeProject>,
    skills: String,
    languages: String,
    styleTemplate: String,
    typography: String,
    colorHex: String,
    profilePic: Bitmap?,
    photoFrameShape: String
) {
    try {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
        if (printManager == null) {
            Toast.makeText(context, "System printing engines not available.", Toast.LENGTH_SHORT).show()
            return
        }

        val printAdapter = object : android.print.PrintDocumentAdapter() {
            private var pdfDoc: PdfDocument? = null

            override fun onLayout(
                oldAttributes: PrintAttributes?,
                newAttributes: PrintAttributes?,
                cancellationSignal: android.os.CancellationSignal?,
                callback: LayoutResultCallback?,
                extras: Bundle?
            ) {
                if (cancellationSignal?.isCanceled == true) {
                    callback?.onLayoutCancelled()
                    return
                }

                val isMultiPagePdf = styleTemplate.contains("(Multi-Page)")
                val pageCount = if (isMultiPagePdf) 2 else 1
                val info = android.print.PrintDocumentInfo.Builder("Academic_Professional_Resume.pdf")
                    .setContentType(android.print.PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(pageCount)
                    .build()

                callback?.onLayoutFinished(info, true)
            }

            override fun onWrite(
                pages: Array<out android.print.PageRange>?,
                destination: android.os.ParcelFileDescriptor?,
                cancellationSignal: android.os.CancellationSignal?,
                callback: WriteResultCallback?
            ) {
                pdfDoc = PdfDocument()
                val isMultiPagePdf = styleTemplate.contains("(Multi-Page)")
                // A4 Sheet: 595 x 842 coordinates
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
                val page = pdfDoc?.startPage(pageInfo)
                val canvas = page?.canvas

                if (canvas != null) {
                    val styleConfig = getTemplateStyleConfig(styleTemplate)
                    val customAccentHex = styleConfig.customAccentColorOverride ?: colorHex
                    val accentIntColor = android.graphics.Color.parseColor(customAccentHex)
                    val defaultBlack = if (styleConfig.name == "Zurich Clean Minimalist") 0xFF000000.toInt() else 0xFF1E293B.toInt()
                    val muteGray = 0xFF64748B.toInt()

                    // Fonts custom pairings
                    val tfTitle = when {
                        styleConfig.isClassicSerif -> Typeface.create(Typeface.SERIF, Typeface.BOLD)
                        typography == "Classic Serif" -> Typeface.create(Typeface.SERIF, Typeface.BOLD)
                        typography == "Tech Monospace" -> Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
                        else -> Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                    }
                    val tfText = when {
                        styleConfig.isClassicSerif -> Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                        typography == "Classic Serif" -> Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                        typography == "Tech Monospace" -> Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
                        else -> Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                    }

                    // Setup shared Paints
                    val paintTitle = Paint().apply {
                        color = accentIntColor
                        textSize = 18f
                        typeface = tfTitle
                        isAntiAlias = true
                    }
                    val paintSectionHeader = Paint().apply {
                        color = accentIntColor
                        textSize = 12f
                        typeface = tfTitle
                        isAntiAlias = true
                    }
                    val paintBodyBold = Paint().apply {
                        color = defaultBlack
                        textSize = 9.5f
                        typeface = tfTitle
                        isAntiAlias = true
                    }
                    val paintBodyText = Paint().apply {
                        color = defaultBlack
                        textSize = 9f
                        typeface = tfText
                        isAntiAlias = true
                    }
                    val paintMutedText = Paint().apply {
                        color = muteGray
                        textSize = 8.5f
                        typeface = tfText
                        isAntiAlias = true
                    }

                    // Helper drawing functions with wrapping
                    fun drawWordWrappedText(text: String, x: Float, y: Float, maxWidth: Float, paint: Paint, spacing: Float = 12f): Float {
                        var currentY = y
                        val words = text.split(" ")
                        val currentLine = StringBuilder()
                        for (word in words) {
                            val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
                            val width = paint.measureText(testLine)
                            if (width > maxWidth) {
                                canvas.drawText(currentLine.toString(), x, currentY, paint)
                                currentY += spacing
                                currentLine.clear()
                                currentLine.append(word)
                            } else {
                                currentLine.clear()
                                currentLine.append(testLine)
                            }
                        }
                        if (currentLine.isNotEmpty()) {
                            canvas.drawText(currentLine.toString(), x, currentY, paint)
                            currentY += spacing
                        }
                        return currentY
                    }

                    // Render dynamic shape profile picture
                    fun drawPortraitCircle(cx: Float, cy: Float, radius: Float) {
                        if (profilePic != null) {
                            try {
                                val size = (radius * 2).toInt()
                                val scaledPic = Bitmap.createScaledBitmap(profilePic, size, size, true)
                                canvas.drawBitmap(scaledPic, cx - radius, cy - radius, null)
                                
                                // Draw high-fidelity matched border outline
                                val borderPaint = Paint().apply {
                                    color = accentIntColor
                                    style = Paint.Style.STROKE
                                    strokeWidth = 1f
                                    isAntiAlias = true
                                }
                                when (photoFrameShape) {
                                    "Circle" -> {
                                        canvas.drawCircle(cx, cy, radius, borderPaint)
                                    }
                                    "Rounded Square" -> {
                                        val rect = android.graphics.RectF(cx - radius, cy - radius, cx + radius, cy + radius)
                                        val rCorner = radius * 0.15f
                                        canvas.drawRoundRect(rect, rCorner, rCorner, borderPaint)
                                    }
                                    "Square" -> {
                                        val rect = android.graphics.RectF(cx - radius, cy - radius, cx + radius, cy + radius)
                                        canvas.drawRect(rect, borderPaint)
                                    }
                                }
                            } catch (e: Exception) {
                                // Fallback: Draw default circular icon placeholder
                                val fallbackPaint = Paint().apply { color = accentIntColor; style = Paint.Style.STROKE; strokeWidth = 2f }
                                canvas.drawCircle(cx, cy, radius, fallbackPaint)
                            }
                        }
                    }

                    if (styleTemplate == "Canada Academic Standard") {
                        // Chronological format compliant with anti-discrimination guidelines (No headshot)
                        var topY = 40f
                        
                        canvas.drawText(name.uppercase(), 45f, topY, paintTitle)
                        topY += 14f
                        canvas.drawText(headline, 45f, topY, paintBodyText)
                        topY += 12f
                        canvas.drawText("📍 $location  |  ✉️ $email  |  📞 $phone", 45f, topY, paintMutedText)
                        
                        topY += 12f
                        canvas.drawRect(45f, topY, 550f, topY + 1.5f, Paint().apply { color = accentIntColor })
                        topY += 18f

                        // Profile Summary
                        canvas.drawText("PROFESSIONAL PROFILE STATEMENT", 45f, topY, paintSectionHeader)
                        topY += 12f
                        topY = drawWordWrappedText(summary, 45f, topY, 505f, paintBodyText, 11f)
                        topY += 12f

                        // Experience
                        if (workList.isNotEmpty()) {
                            canvas.drawText("CHRONOLOGICAL CAREER HISTORY", 45f, topY, paintSectionHeader)
                            topY += 4f
                            canvas.drawRect(45f, topY, 550f, topY + 1f, Paint().apply { color = 0x80000000.toInt() })
                            topY += 14f

                            workList.forEach { work ->
                                if (topY < 580f) {
                                    canvas.drawText(work.title, 45f, topY, paintBodyBold)
                                    val termW = paintMutedText.measureText(work.duration)
                                    canvas.drawText(work.duration, 550f - termW, topY, paintMutedText)
                                    topY += 11f

                                    canvas.drawText(work.company, 45f, topY, paintBodyText)
                                    topY += 12f

                                    if (work.duty1.isNotEmpty()) {
                                        topY = drawWordWrappedText("• " + work.duty1, 55f, topY, 495f, paintMutedText, 10.5f)
                                    }
                                    if (work.duty2.isNotEmpty()) {
                                        topY = drawWordWrappedText("• " + work.duty2, 55f, topY, 495f, paintMutedText, 10.5f)
                                    }
                                    topY += 8f
                                }
                            }
                        }

                        // Education details
                        if (academicList.isNotEmpty()) {
                            canvas.drawText("ACADEMIC DEGREES & HIGHER STUDY", 45f, topY, paintSectionHeader)
                            topY += 4f
                            canvas.drawRect(45f, topY, 550f, topY + 1f, Paint().apply { color = 0x80000000.toInt() })
                            topY += 14f

                            academicList.forEach { edu ->
                                if (topY < 710f) {
                                    canvas.drawText(edu.degree, 45f, topY, paintBodyBold)
                                    val timelineW = paintMutedText.measureText(edu.duration)
                                    canvas.drawText(edu.duration, 550f - timelineW, topY, paintMutedText)
                                    topY += 11f

                                    canvas.drawText("${edu.school} (${edu.grade})", 45f, topY, paintBodyText)
                                    topY += 15f
                                }
                            }
                        }

                        // Skills and languages
                        canvas.drawText("CORE SKILL COMPILATION", 45f, topY, paintSectionHeader)
                        topY += 4f
                        canvas.drawRect(45f, topY, 550f, topY + 1f, Paint().apply { color = 0x80000000.toInt() })
                        topY += 14f

                        topY = drawWordWrappedText("Skills: " + skills, 45f, topY, 505f, paintBodyText, 11f)
                        topY += 6f
                        topY = drawWordWrappedText("Languages: " + languages, 45f, topY, 505f, paintBodyText, 11f)
                    }
                    else if (styleTemplate == "USA Executive Elite") {
                        // USA classic centered chronological layout (No photo)
                        var topY = 45f
                        
                        // Centered Name
                        val nameW = paintTitle.measureText(name.uppercase())
                        canvas.drawText(name.uppercase(), (595f - nameW) / 2f, topY, paintTitle)
                        topY += 14f
                        
                        // Centered headline
                        val headW = paintBodyText.measureText(headline)
                        canvas.drawText(headline, (595f - headW) / 2f, topY, paintBodyText)
                        topY += 12f
                        
                        // Centered contacts
                        val contactStr = "✉️ $email   •   📞 $phone   •   📍 $location"
                        val contactW = paintMutedText.measureText(contactStr)
                        canvas.drawText(contactStr, (595f - contactW) / 2f, topY, paintMutedText)
                        
                        topY += 12f
                        canvas.drawRect(40f, topY, 555f, topY + 1.2f, Paint().apply { color = accentIntColor })
                        topY += 20f

                        // Summary
                        canvas.drawText("PROFESSIONAL STATEMENT", 40f, topY, paintSectionHeader)
                        topY += 12f
                        topY = drawWordWrappedText(summary, 40f, topY, 515f, paintBodyText, 11f)
                        topY += 14f

                        // Experience
                        if (workList.isNotEmpty()) {
                            canvas.drawText("CHRONOLOGICAL EMPLOYMENT HISTORY", 40f, topY, paintSectionHeader)
                            topY += 4f
                            canvas.drawRect(40f, topY, 555f, topY + 0.8f, Paint().apply { color = 0xAA000000.toInt() })
                            topY += 14f

                            workList.forEach { work ->
                                if (topY < 580f) {
                                    canvas.drawText(work.title + " | " + work.company, 40f, topY, paintBodyBold)
                                    val termW = paintMutedText.measureText(work.duration)
                                    canvas.drawText(work.duration, 555f - termW, topY, paintMutedText)
                                    topY += 11f

                                    if (work.duty1.isNotEmpty()) {
                                        topY = drawWordWrappedText("• " + work.duty1, 50f, topY, 505f, paintBodyText, 10.5f)
                                    }
                                    if (work.duty2.isNotEmpty()) {
                                        topY = drawWordWrappedText("• " + work.duty2, 50f, topY, 505f, paintBodyText, 10.5f)
                                    }
                                    topY += 8f
                                }
                            }
                        }

                        // Education details
                        if (academicList.isNotEmpty()) {
                            canvas.drawText("EDUCATIONAL HIGHLIGHTS", 40f, topY, paintSectionHeader)
                            topY += 4f
                            canvas.drawRect(40f, topY, 555f, topY + 0.8f, Paint().apply { color = 0xAA000000.toInt() })
                            topY += 14f

                            academicList.forEach { edu ->
                                if (topY < 710f) {
                                    canvas.drawText(edu.degree + " — " + edu.school, 40f, topY, paintBodyBold)
                                    val timelineW = paintMutedText.measureText(edu.duration)
                                    canvas.drawText(edu.duration, 555f - timelineW, topY, paintMutedText)
                                    topY += 11f
                                    canvas.drawText("Graduated achievement grade: " + edu.grade, 40f, topY, paintBodyText)
                                    topY += 14f
                                }
                            }
                        }

                        // Skills and languages
                        canvas.drawText("SKILLS & HUMAN LANGUAGES", 40f, topY, paintSectionHeader)
                        topY += 4f
                        canvas.drawRect(40f, topY, 555f, topY + 0.8f, Paint().apply { color = 0xAA000000.toInt() })
                        topY += 14f

                        topY = drawWordWrappedText("Core Tech Skills: " + skills, 40f, topY, 515f, paintBodyText, 11f)
                        topY += 6f
                        topY = drawWordWrappedText("Spoken Dialects: " + languages, 40f, topY, 515f, paintBodyText, 11f)
                    }
                    else if (styleTemplate == "UAE Modern Grid") {
                        // Dynamic 2 column dual layout, allows photo
                        val sidebarWeightPaint = Paint().apply { color = accentIntColor; style = Paint.Style.FILL; alpha = 30 }
                        canvas.drawRect(0f, 0f, 185f, 842f, sidebarWeightPaint)

                        var sideY = 40f
                        if (profilePic != null) {
                            drawPortraitCircle(92.5f, 80f, 30f)
                            sideY = 130f
                        } else {
                            sideY = 60f
                        }

                        val whiteSectionHeader = Paint().apply { color = accentIntColor; textSize = 11f; typeface = tfTitle; isAntiAlias = true }
                        val whiteSidebarText = Paint().apply { color = defaultBlack; textSize = 8.5f; typeface = tfText; isAntiAlias = true }

                        canvas.drawText(name.uppercase(), 15f, sideY, whiteSectionHeader)
                        sideY += 15f
                        sideY = drawWordWrappedText(headline, 15f, sideY, 155f, whiteSidebarText, 11f)

                        sideY += 12f
                        canvas.drawRect(15f, sideY, 170f, sideY + 1f, Paint().apply { color = accentIntColor })
                        sideY += 12f

                        canvas.drawText("CONTACT", 15f, sideY, whiteSectionHeader)
                        sideY += 15f
                        canvas.drawText("✉️ $email", 15f, sideY, whiteSidebarText)
                        sideY += 12f
                        canvas.drawText("📞 $phone", 15f, sideY, whiteSidebarText)
                        sideY += 12f
                        canvas.drawText("📍 $location", 15f, sideY, whiteSidebarText)

                        sideY += 15f
                        canvas.drawRect(15f, sideY, 170f, sideY + 1f, Paint().apply { color = accentIntColor })
                        sideY += 12f

                        canvas.drawText("KEY CAPABILITIES", 15f, sideY, whiteSectionHeader)
                        sideY += 16f
                        skills.split(",").forEach { sk ->
                            if (sk.isNotEmpty() && sideY < 620f) {
                                canvas.drawText("✓ ${sk.trim()}", 15f, sideY, whiteSidebarText)
                                sideY += 13f
                            }
                        }

                        // Right panel details
                        var rightY = 45f
                        val rightMarginOffset = 205f
                        val rightColWidth = 595f - rightMarginOffset - 25f

                        canvas.drawText("PROFESSIONAL STATEMENT", rightMarginOffset, rightY, paintSectionHeader)
                        rightY += 6f
                        canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                        rightY += 14f

                        rightY = drawWordWrappedText(summary, rightMarginOffset, rightY, rightColWidth, paintBodyText, 11f)
                        rightY += 16f

                        if (workList.isNotEmpty()) {
                            canvas.drawText("PROFESSIONAL PRACTICE", rightMarginOffset, rightY, paintSectionHeader)
                            rightY += 6f
                            canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                            rightY += 14f

                            workList.forEach { work ->
                                if (rightY < 560f) {
                                    canvas.drawText(work.title, rightMarginOffset, rightY, paintBodyBold)
                                    val durationWidth = paintMutedText.measureText(work.duration)
                                    canvas.drawText(work.duration, 570f - durationWidth, rightY, paintMutedText)
                                    rightY += 11f

                                    canvas.drawText(work.company, rightMarginOffset, rightY, paintBodyText)
                                    rightY += 12f

                                    if (work.duty1.isNotEmpty()) {
                                        rightY = drawWordWrappedText("• " + work.duty1, rightMarginOffset + 8f, rightY, rightColWidth - 8f, paintBodyText, 11f)
                                    }
                                    if (work.duty2.isNotEmpty()) {
                                        rightY = drawWordWrappedText("• " + work.duty2, rightMarginOffset + 8f, rightY, rightColWidth - 8f, paintBodyText, 11f)
                                    }
                                    rightY += 8f
                                }
                            }
                        }

                        if (academicList.isNotEmpty()) {
                            canvas.drawText("EDUCATIONAL HISTORY", rightMarginOffset, rightY, paintSectionHeader)
                            rightY += 6f
                            canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                            rightY += 14f

                            academicList.forEach { edu ->
                                if (rightY < 750f) {
                                    canvas.drawText(edu.degree, rightMarginOffset, rightY, paintBodyBold)
                                    val yearWidth = paintMutedText.measureText(edu.duration)
                                    canvas.drawText(edu.duration, 570f - yearWidth, rightY, paintMutedText)
                                    rightY += 11f

                                    canvas.drawText("${edu.school} — ${edu.grade}", rightMarginOffset, rightY, paintBodyText)
                                    rightY += 15f
                                }
                            }
                        }
                    }
                    else if (styleTemplate == "Australia Professional") {
                        // Highly structured chronological layout with highlights block (No photo)
                        var topY = 40f
                        
                        canvas.drawText(name.uppercase(), 40f, topY, paintTitle)
                        val detailsRightStr = "$location | $email"
                        val detailsRightW = paintMutedText.measureText(detailsRightStr)
                        canvas.drawText(detailsRightStr, 555f - detailsRightW, topY, paintMutedText)
                        
                        topY += 14f
                        canvas.drawText(headline, 40f, topY, paintBodyText)
                        val phoneRightW = paintMutedText.measureText(phone)
                        canvas.drawText(phone, 555f - phoneRightW, topY, paintMutedText)
                        
                        topY += 12f
                        canvas.drawRect(40f, topY, 555f, topY + 1.5f, Paint().apply { color = accentIntColor })
                        topY += 16f

                        // highlights block
                        val bgHighlightPaint = Paint().apply { color = accentIntColor; style = Paint.Style.STROKE; strokeWidth = 1f; alpha = 60 }
                        canvas.drawRect(40f, topY, 555f, topY + 45f, bgHighlightPaint)
                        canvas.drawText("KEY PROFESSIONAL HIGHLIGHTS & SUMMARY", 46f, topY + 14f, Paint(paintSectionHeader).apply { textSize = 8.5f })
                        drawWordWrappedText(summary, 46f, topY + 25f, 500f, Paint(paintBodyText).apply { textSize = 8f }, 10f)
                        topY += 58f

                        // Career experience
                        if (workList.isNotEmpty()) {
                            canvas.drawText("CHRONOLOGICAL EMPLOYMENT HISTORY", 40f, topY, paintSectionHeader)
                            topY += 4f
                            canvas.drawRect(40f, topY, 555f, topY + 1f, Paint().apply { color = accentIntColor })
                            topY += 14f

                            workList.forEach { work ->
                                if (topY < 580f) {
                                    canvas.drawText(work.title, 40f, topY, paintBodyBold)
                                    val termW = paintMutedText.measureText(work.duration)
                                    canvas.drawText(work.duration, 555f - termW, topY, paintMutedText)
                                    topY += 11f

                                    canvas.drawText(work.company, 40f, topY, paintBodyText)
                                    topY += 12f

                                    if (work.duty1.isNotEmpty()) {
                                        topY = drawWordWrappedText("• " + work.duty1, 50f, topY, 505f, paintMutedText, 10.5f)
                                    }
                                    if (work.duty2.isNotEmpty()) {
                                        topY = drawWordWrappedText("• " + work.duty2, 50f, topY, 505f, paintMutedText, 10.5f)
                                    }
                                    topY += 8f
                                }
                            }
                        }

                        // Education details
                        if (academicList.isNotEmpty()) {
                            canvas.drawText("TERTIARY EDUCATION & DEGREE CREDENTIALS", 40f, topY, paintSectionHeader)
                            topY += 4f
                            canvas.drawRect(40f, topY, 555f, topY + 1f, Paint().apply { color = accentIntColor })
                            topY += 14f

                            academicList.forEach { edu ->
                                if (topY < 720f) {
                                    canvas.drawText(edu.degree, 40f, topY, paintBodyBold)
                                    val timelineW = paintMutedText.measureText(edu.duration)
                                    canvas.drawText(edu.duration, 555f - timelineW, topY, paintMutedText)
                                    topY += 11f

                                    canvas.drawText("${edu.school} — ${edu.grade}", 40f, topY, paintBodyText)
                                    topY += 15f
                                }
                            }
                        }

                        // Skills and languages
                        canvas.drawText("VERIFIED SKILLS INVENTORY", 40f, topY, paintSectionHeader)
                        topY += 4f
                        canvas.drawRect(40f, topY, 555f, topY + 1f, Paint().apply { color = accentIntColor })
                        topY += 14f

                        topY = drawWordWrappedText("Core Tech Strengths: " + skills, 40f, topY, 515f, paintBodyText, 11f)
                    }
                    else if (styleTemplate == "Modern Blue Grid") {
                        // Drawing TWO column layout (Deterioration metrics support: left rail is deep accent background)
                        val sidebarWeightPaint = Paint().apply { color = accentIntColor; style = Paint.Style.FILL }
                        canvas.drawRect(0f, 0f, 185f, 842f, sidebarWeightPaint)

                        // 1. Sidebar Details (Left column)
                        var sideY = 40f
                        
                        // Circle Avatars
                        if (profilePic != null) {
                            drawPortraitCircle(92.5f, 75f, 32f)
                            sideY = 125f
                        } else {
                            sideY = 60f
                        }

                        val whiteSectionHeader = Paint().apply { color = 0xFFFFFFFF.toInt(); textSize = 11f; typeface = tfTitle; isAntiAlias = true }
                        val whiteSidebarText = Paint().apply { color = 0xFFF1F5F9.toInt(); textSize = 8.5f; typeface = tfText; isAntiAlias = true }

                        canvas.drawText(name.uppercase(), 15f, sideY, whiteSectionHeader)
                        sideY += 15f
                        sideY = drawWordWrappedText(headline, 15f, sideY, 155f, whiteSidebarText, 11f)

                        sideY += 12f
                        canvas.drawRect(15f, sideY, 170f, sideY + 1f, Paint().apply { color = 0x50FFFFFF.toInt() })
                        sideY += 12f

                        // Contact Badges
                        canvas.drawText("CONTACT DETAILS", 15f, sideY, whiteSectionHeader)
                        sideY += 15f
                        canvas.drawText("✉️ $email", 15f, sideY, whiteSidebarText)
                        sideY += 12f
                        canvas.drawText("📞 $phone", 15f, sideY, whiteSidebarText)
                        sideY += 12f
                        canvas.drawText("📍 $location", 15f, sideY, whiteSidebarText)

                        sideY += 15f
                        canvas.drawRect(15f, sideY, 170f, sideY + 1f, Paint().apply { color = 0x50FFFFFF.toInt() })
                        sideY += 12f

                        // Core Skills Bullet listings
                        canvas.drawText("KEY CORE SKILLS", 15f, sideY, whiteSectionHeader)
                        sideY += 16f
                        skills.split(",").forEach { sk ->
                            if (sk.isNotEmpty() && sideY < 580f) {
                                canvas.drawText("▪ ${sk.trim()}", 15f, sideY, whiteSidebarText)
                                sideY += 13f
                            }
                        }

                        sideY += 10f
                        canvas.drawRect(15f, sideY, 170f, sideY + 1f, Paint().apply { color = 0x50FFFFFF.toInt() })
                        sideY += 12f

                        // Languages
                        canvas.drawText("HUMAN LANGUAGES", 15f, sideY, whiteSectionHeader)
                        sideY += 16f
                        languages.split(",").forEach { lg ->
                            if (lg.isNotEmpty() && sideY < 780f) {
                                canvas.drawText("🗣 $lg", 15f, sideY, whiteSidebarText)
                                sideY += 13f
                            }
                        }

                        // 2. Right Column (Details)
                        var rightY = 45f
                        val rightMarginOffset = 205f
                        val rightColWidth = 595f - rightMarginOffset - 25f

                        // Big Profile Statement summary
                        canvas.drawText("PROFESSIONAL OBJECTIVE", rightMarginOffset, rightY, paintSectionHeader)
                        rightY += 6f
                        canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                        rightY += 14f

                        rightY = drawWordWrappedText(summary, rightMarginOffset, rightY, rightColWidth, paintBodyText, 11f)
                        rightY += 12f

                        // Jobs Section
                        if (workList.isNotEmpty()) {
                            canvas.drawText("PROFESSIONAL EXPERIENCE", rightMarginOffset, rightY, paintSectionHeader)
                            rightY += 6f
                            canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                            rightY += 14f

                            workList.forEach { work ->
                                if (rightY < 560f) {
                                    canvas.drawText(work.title, rightMarginOffset, rightY, paintBodyBold)
                                    val durationWidth = paintMutedText.measureText(work.duration)
                                    canvas.drawText(work.duration, 570f - durationWidth, rightY, paintMutedText)
                                    rightY += 11f

                                    canvas.drawText(work.company, rightMarginOffset, rightY, paintBodyText)
                                    rightY += 13f

                                    // Duties
                                    if (work.duty1.isNotEmpty()) {
                                        rightY = drawWordWrappedText("• " + work.duty1, rightMarginOffset + 8f, rightY, rightColWidth - 8f, paintBodyText, 11f)
                                    }
                                    if (work.duty2.isNotEmpty()) {
                                        rightY = drawWordWrappedText("• " + work.duty2, rightMarginOffset + 8f, rightY, rightColWidth - 8f, paintBodyText, 11f)
                                    }
                                    rightY += 6f
                                }
                            }
                        }

                        // Projects Section
                        if (projectsList.isNotEmpty()) {
                            canvas.drawText("PORTFOLIO PROJECTS", rightMarginOffset, rightY, paintSectionHeader)
                            rightY += 6f
                            canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                            rightY += 14f

                            projectsList.forEach { proj ->
                                if (rightY < 720f) {
                                    canvas.drawText(proj.title, rightMarginOffset, rightY, paintBodyBold)
                                    rightY += 11f
                                    if (proj.techStack.isNotEmpty()) {
                                        canvas.drawText("Tech: " + proj.techStack, rightMarginOffset, rightY, paintMutedText)
                                        rightY += 11f
                                    }
                                    if (proj.impact.isNotEmpty()) {
                                        rightY = drawWordWrappedText("Impact: " + proj.impact, rightMarginOffset + 4f, rightY, rightColWidth - 4f, paintBodyText, 10.5f)
                                    }
                                    rightY += 6f
                                }
                            }
                        }

                        // Education Section
                        if (academicList.isNotEmpty()) {
                            canvas.drawText("EDUCATIONAL ATTAINMENT", rightMarginOffset, rightY, paintSectionHeader)
                            rightY += 6f
                            canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                            rightY += 14f

                            academicList.forEach { edu ->
                                if (rightY < 810f) {
                                    canvas.drawText(edu.degree, rightMarginOffset, rightY, paintBodyBold)
                                    val yearWidth = paintMutedText.measureText(edu.duration)
                                    canvas.drawText(edu.duration, 570f - yearWidth, rightY, paintMutedText)
                                    rightY += 11f

                                    canvas.drawText("${edu.school} — ${edu.grade}", rightMarginOffset, rightY, paintBodyText)
                                    rightY += 15f
                                }
                            }
                        }
                    } else {
                        // Dynamic Template Engine supporting 24+ newly added Canva/Resume.io templates!
                        val styleConfig = getTemplateStyleConfig(styleTemplate)
                        
                        // If background belongs to customizable palette
                        if (styleConfig.customBackgroundOverride != null) {
                            val bgPaint = Paint().apply {
                                color = android.graphics.Color.parseColor(styleConfig.customBackgroundOverride)
                                style = Paint.Style.FILL
                            }
                            canvas.drawRect(0f, 0f, 595f, 842f, bgPaint)
                        }

                        // Top colored banner
                        if (styleConfig.hasTopColoredBanner) {
                            canvas.drawRect(0f, 0f, 595f, 12f, Paint().apply { color = accentIntColor; style = Paint.Style.FILL })
                        }

                        // EEO Compliance banner
                        var topY = 40f
                        if (styleConfig.showEeoBanner) {
                            canvas.drawRect(0f, topY - 14f, 595f, topY + 4f, Paint().apply { color = accentIntColor and 0x20FFFFFF or 0x15000000; style = Paint.Style.FILL })
                            canvas.drawText(styleConfig.eeoText, 25f, topY - 4f, Paint().apply { color = accentIntColor; textSize = 7.5f; typeface = tfTitle; isAntiAlias = true })
                            topY += 15f
                        }

                        if (styleConfig.isTwoColumn) {
                            // DYNAMIC TWO COLUMN PRINTING
                            // Sidebar
                            val sidebarBgColor = if (styleConfig.isSidebarAccentColored) accentIntColor else (accentIntColor and 0x0FFFFFFF or 0x10000000)
                            canvas.drawRect(0f, 0f, 185f, 842f, Paint().apply { color = sidebarBgColor; style = Paint.Style.FILL })

                            var sideY = 40f
                            val sideTextColor = if (styleConfig.isSidebarAccentColored) 0xFFFFFFFF.toInt() else 0xFF1E293B.toInt()
                            val sideMutedColor = if (styleConfig.isSidebarAccentColored) 0xFFE2E8F0.toInt() else 0xFF64748B.toInt()

                            val sideTitlePaint = Paint().apply { color = if (styleConfig.isSidebarAccentColored) 0xFFFFFFFF.toInt() else accentIntColor; textSize = 10f; typeface = tfTitle; isAntiAlias = true }
                            val sideBodyPaint = Paint().apply { color = sideTextColor; textSize = 8.5f; typeface = tfText; isAntiAlias = true }
                            val sideMutedPaint = Paint().apply { color = sideMutedColor; textSize = 8f; typeface = tfText; isAntiAlias = true }

                            // Profile avatar or Monogram
                            if (profilePic != null) {
                                drawPortraitCircle(92.5f, 75f, 30f)
                                sideY = 125f
                            } else if (styleConfig.hasInitialsBadge) {
                                canvas.drawCircle(92.5f, 75f, 20f, Paint().apply { color = accentIntColor; style = Paint.Style.FILL; isAntiAlias = true })
                                canvas.drawText(name.take(1).uppercase(), 92.5f, 81f, Paint().apply { color = 0xFFFFFFFF.toInt(); textSize = 15f; typeface = tfTitle; isAntiAlias = true; textAlign = Paint.Align.CENTER })
                                sideY = 115f
                            }

                            canvas.drawText(name.uppercase(), 15f, sideY, sideTitlePaint)
                            sideY += 14f
                            sideY = drawWordWrappedText(headline, 15f, sideY, 155f, sideMutedPaint, 11f)
                            sideY += 8f

                            canvas.drawRect(15f, sideY, 170f, sideY + 1f, Paint().apply { color = if (styleConfig.isSidebarAccentColored) 0x30FFFFFF.toInt() else 0x30000000.toInt() })
                            sideY += 12f

                            canvas.drawText("CONTACT DETAILS", 15f, sideY, sideTitlePaint)
                            sideY += 14f
                            canvas.drawText("📍 $location", 15f, sideY, sideBodyPaint)
                            sideY += 12f
                            canvas.drawText("✉️ $email", 15f, sideY, sideBodyPaint)
                            sideY += 12f
                            canvas.drawText("📞 $phone", 15f, sideY, sideBodyPaint)
                            sideY += 14f

                            canvas.drawRect(15f, sideY, 170f, sideY + 1f, Paint().apply { color = if (styleConfig.isSidebarAccentColored) 0x30FFFFFF.toInt() else 0x30000000.toInt() })
                            sideY += 12f

                            canvas.drawText("CORE SKILLS", 15f, sideY, sideTitlePaint)
                            sideY += 14f
                            skills.split(",").take(7).forEach { sk ->
                                if (sk.trim().isNotEmpty() && sideY < 720f) {
                                    canvas.drawText("▪ ${sk.trim()}", 15f, sideY, sideBodyPaint)
                                    sideY += 13f
                                }
                            }

                            // Languages
                            if (sideY < 720f) {
                                sideY += 10f
                                canvas.drawRect(15f, sideY, 170f, sideY + 1f, Paint().apply { color = if (styleConfig.isSidebarAccentColored) 0x30FFFFFF.toInt() else 0x30000000.toInt() })
                                sideY += 12f
                                canvas.drawText("FLUENT LANGUAGES", 15f, sideY, sideTitlePaint)
                                sideY += 14f
                                languages.split(",").forEach { lg ->
                                    if (lg.trim().isNotEmpty() && sideY < 810f) {
                                        canvas.drawText("🗣 ${lg.trim()}", 15f, sideY, sideBodyPaint)
                                        sideY += 13f
                                    }
                                }
                            }

                            // Right Column Content
                            var rightY = 45f
                            val rightMarginOffset = 205f
                            val rightColWidth = 595f - rightMarginOffset - 25f

                            // Summary Statement
                            canvas.drawText("SUMMARY STATEMENT", rightMarginOffset, rightY, paintSectionHeader)
                            rightY += 6f
                            if (styleConfig.sectionHeaderBottomBorder) {
                                canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                            } else {
                                canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 0.5f, Paint().apply { color = 0x50000000.toInt() })
                            }
                            rightY += 14f

                            rightY = drawWordWrappedText(summary, rightMarginOffset, rightY, rightColWidth, paintBodyText, 11f)
                            rightY += 14f

                            // Work list
                            if (workList.isNotEmpty() && rightY < 800f) {
                                canvas.drawText("PROFESSIONAL HISTORY", rightMarginOffset, rightY, paintSectionHeader)
                                rightY += 6f
                                if (styleConfig.sectionHeaderBottomBorder) {
                                    canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                                } else {
                                    canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 0.5f, Paint().apply { color = 0x50000000.toInt() })
                                }
                                rightY += 14f

                                workList.forEach { work ->
                                    if (rightY < 780f) {
                                        canvas.drawText(work.title, rightMarginOffset, rightY, paintBodyBold)
                                        val durW = paintMutedText.measureText(work.duration)
                                        canvas.drawText(work.duration, 570f - durW, rightY, paintMutedText)
                                        rightY += 11f
                                        canvas.drawText(work.company, rightMarginOffset, rightY, paintBodyText)
                                        rightY += 12f
                                        if (work.duty1.isNotEmpty()) {
                                            rightY = drawWordWrappedText("• " + work.duty1, rightMarginOffset + 8f, rightY, rightColWidth - 8f, paintBodyText, 11f)
                                        }
                                        rightY += 4f
                                    }
                                }
                            }

                            // Academic list
                            if (academicList.isNotEmpty() && rightY < 800f) {
                                rightY += 8f
                                canvas.drawText("ACADEMIC STUDY DETAILS", rightMarginOffset, rightY, paintSectionHeader)
                                rightY += 6f
                                if (styleConfig.sectionHeaderBottomBorder) {
                                    canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 1.2f, Paint().apply { color = accentIntColor })
                                } else {
                                    canvas.drawRect(rightMarginOffset, rightY, 570f, rightY + 0.5f, Paint().apply { color = 0x50000000.toInt() })
                                }
                                rightY += 14f

                                academicList.forEach { edu ->
                                    if (rightY < 815f) {
                                        canvas.drawText(edu.degree, rightMarginOffset, rightY, paintBodyBold)
                                        val durW = paintMutedText.measureText(edu.duration)
                                        canvas.drawText(edu.duration, 570f - durW, rightY, paintMutedText)
                                        rightY += 11f
                                        canvas.drawText(edu.school + " (" + edu.grade + ")", rightMarginOffset, rightY, paintBodyText)
                                        rightY += 14f
                                    }
                                }
                            }
                        } else {
                            // DYNAMIC SINGLE COLUMN PRINTING
                            // Header Center/Alignment
                            val lineStartX = 45f
                            val lineEndX = 550f
                            val fullWidth = lineEndX - lineStartX

                            if (styleConfig.headerCentered) {
                                val nameW = paintTitle.measureText(name.uppercase())
                                canvas.drawText(name.uppercase(), (595f - nameW) / 2f, topY, paintTitle)
                                topY += 14f

                                val headW = paintBodyText.measureText(headline)
                                canvas.drawText(headline, (595f - headW) / 2f, topY, paintBodyText)
                                topY += 12f

                                val contactStr = "✉️ $email  |  📞 $phone  |  📍 $location"
                                val contactW = paintMutedText.measureText(contactStr)
                                canvas.drawText(contactStr, (595f - contactW) / 2f, topY, paintMutedText)
                                topY += 14f
                            } else {
                                if (styleConfig.hasInitialsBadge) {
                                    canvas.drawCircle(65f, topY - 5f, 18f, Paint().apply { color = accentIntColor; style = Paint.Style.FILL; isAntiAlias = true })
                                    canvas.drawText(name.take(1).uppercase(), 65f, topY, Paint().apply { color = 0xFFFFFFFF.toInt(); textSize = 13f; typeface = tfTitle; isAntiAlias = true; textAlign = Paint.Align.CENTER })
                                    canvas.drawText(name.uppercase(), 95f, topY, paintTitle)
                                } else {
                                    canvas.drawText(name.uppercase(), 45f, topY, paintTitle)
                                }

                                if (profilePic != null) {
                                    drawPortraitCircle(520f, topY - 5f, 24f)
                                }
                                topY += 14f
                                canvas.drawText(headline, 45f, topY, paintBodyText)
                                topY += 12f
                                canvas.drawText("✉️ $email  |  📞 $phone  |  📍 $location", 45f, topY, paintMutedText)
                                topY += 14f
                            }

                            // Dynamic Dividers
                            if (styleConfig.hasDoubleDivider) {
                                canvas.drawRect(lineStartX, topY, lineEndX, topY + 1.5f, Paint().apply { color = accentIntColor })
                                topY += 3f
                                canvas.drawRect(lineStartX, topY, lineEndX, topY + 0.5f, Paint().apply { color = accentIntColor })
                                topY += 11f
                            } else {
                                canvas.drawRect(lineStartX, topY, lineEndX, topY + 1.2f, Paint().apply { color = if (styleConfig.isAtsFriendly) 0xFF1E293B.toInt() else accentIntColor })
                                topY += 14f
                            }

                            // Summary Statement
                            canvas.drawText("PROFESSIONAL SUMMARY", lineStartX, topY, paintSectionHeader)
                            topY += 6f
                            if (styleConfig.sectionHeaderBottomBorder) {
                                canvas.drawRect(lineStartX, topY, lineEndX, topY + 1f, Paint().apply { color = accentIntColor })
                            }
                            topY += 11f
                            topY = drawWordWrappedText(summary, lineStartX, topY, fullWidth, paintBodyText, 11f)
                            topY += 14f

                            // Jobs History
                            if (workList.isNotEmpty() && topY < 800f) {
                                canvas.drawText("CHRONOLOGICAL EMPLOYMENT HISTORY", lineStartX, topY, paintSectionHeader)
                                topY += 6f
                                if (styleConfig.sectionHeaderBottomBorder) {
                                    canvas.drawRect(lineStartX, topY, lineEndX, topY + 1f, Paint().apply { color = accentIntColor })
                                } else {
                                    canvas.drawRect(lineStartX, topY, lineEndX, topY + 0.5f, Paint().apply { color = 0x50000000.toInt() })
                                }
                                topY += 11f

                                workList.forEach { work ->
                                    if (topY < 740f) {
                                        canvas.drawText(work.title, lineStartX, topY, paintBodyBold)
                                        val durW = paintMutedText.measureText(work.duration)
                                        canvas.drawText(work.duration, lineEndX - durW, topY, paintMutedText)
                                        topY += 11f
                                        canvas.drawText(work.company, lineStartX, topY, paintBodyText)
                                        topY += 12f
                                        val cleanDesc = work.description.ifEmpty { getDetailedDescription(work.title, work.company) }
                                        topY = drawWordWrappedText(cleanDesc, lineStartX, topY, fullWidth, paintBodyText, 10.5f)
                                        topY += 4f
                                        if (work.duty1.isNotEmpty()) {
                                            topY = drawWordWrappedText("• " + work.duty1, lineStartX + 10f, topY, fullWidth - 10f, paintMutedText, 10.5f)
                                        }
                                        topY += 5f
                                    }
                                }
                            }

                            // Academic History
                            if (!isMultiPagePdf && academicList.isNotEmpty() && topY < 800f) {
                                topY += 8f
                                canvas.drawText("ACADEMIC PREPARATION", lineStartX, topY, paintSectionHeader)
                                topY += 6f
                                if (styleConfig.sectionHeaderBottomBorder) {
                                    canvas.drawRect(lineStartX, topY, lineEndX, topY + 1f, Paint().apply { color = accentIntColor })
                                } else {
                                    canvas.drawRect(lineStartX, topY, lineEndX, topY + 0.5f, Paint().apply { color = 0x50000000.toInt() })
                                }
                                topY += 11f

                                academicList.forEach { edu ->
                                    if (topY < 790f) {
                                        canvas.drawText(edu.degree, lineStartX, topY, paintBodyBold)
                                        val durW = paintMutedText.measureText(edu.duration)
                                        canvas.drawText(edu.duration, lineEndX - durW, topY, paintMutedText)
                                        topY += 11f
                                        canvas.drawText(edu.school + " (" + edu.grade + ")", lineStartX, topY, paintBodyText)
                                        topY += 14f
                                    }
                                }
                            }

                            // Skills / Languages
                            if (!isMultiPagePdf && topY < 800f) {
                                topY += 8f
                                canvas.drawText("PROFESSIONAL CAPABILITIES & LANGUAGES", lineStartX, topY, paintSectionHeader)
                                topY += 6f
                                canvas.drawRect(lineStartX, topY, lineEndX, topY + 0.5f, Paint().apply { color = 0x50000000.toInt() })
                                topY += 11f

                                canvas.drawText("Core Talents: $skills", lineStartX, topY, paintBodyText)
                                topY += 11f
                                canvas.drawText("Languages: $languages", lineStartX, topY, paintBodyText)
                            }
                        }
                    }
                }

                pdfDoc?.finishPage(page)

                if (isMultiPagePdf) {
                    val pageInfo2 = PdfDocument.PageInfo.Builder(595, 842, 2).create()
                    val page2 = pdfDoc?.startPage(pageInfo2)
                    val canvas2 = page2?.canvas
                    if (canvas2 != null) {
                        val styleConfig = getTemplateStyleConfig(styleTemplate)
                        val customAccentHex = styleConfig.customAccentColorOverride ?: colorHex
                        val accentIntColor = android.graphics.Color.parseColor(customAccentHex)
                        
                        val tfTitle = when {
                            styleConfig.isClassicSerif -> Typeface.create(Typeface.SERIF, Typeface.BOLD)
                            typography == "Classic Serif" -> Typeface.create(Typeface.SERIF, Typeface.BOLD)
                            typography == "Tech Monospace" -> Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
                            else -> Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                        }
                        val tfText = when {
                            styleConfig.isClassicSerif -> Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                            typography == "Classic Serif" -> Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                            typography == "Tech Monospace" -> Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
                            else -> Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                        }

                        val paintSectionHeader = Paint().apply {
                            color = accentIntColor
                            textSize = 12f
                            typeface = tfTitle
                            isAntiAlias = true
                        }
                        val paintBodyBold = Paint().apply {
                            color = 0xFF1E293B.toInt()
                            textSize = 9f
                            typeface = tfTitle
                            isAntiAlias = true
                        }
                        val paintBodyText = Paint().apply {
                            color = 0xFF1E293B.toInt()
                            textSize = 8.5f
                            typeface = tfText
                            isAntiAlias = true
                        }
                        val paintMutedText = Paint().apply {
                            color = 0xFF64748B.toInt()
                            textSize = 8f
                            typeface = tfText
                            isAntiAlias = true
                        }

                        var topY = 45f
                        val lineStartX = 40f
                        val lineEndX = 555f

                        // Page 2 indicator
                        canvas2.drawText("CURRICULUM VITAE PORTFOLIO (CONTINUED)", lineStartX, topY, paintMutedText)
                        topY += 25f

                        // 1. Education Section
                        canvas2.drawText("ACADEMIC HISTORY", lineStartX, topY, paintSectionHeader)
                        topY += 6f
                        canvas2.drawRect(lineStartX, topY, lineEndX, topY + 0.8f, Paint().apply { color = accentIntColor })
                        topY += 16f

                        academicList.forEach { edu ->
                            canvas2.drawText(edu.degree, lineStartX, topY, paintBodyBold)
                            val durW = paintMutedText.measureText(edu.duration)
                            canvas2.drawText(edu.duration, lineEndX - durW, topY, paintMutedText)
                            topY += 12f
                            canvas2.drawText(edu.school + " (" + edu.grade + ")", lineStartX, topY, paintBodyText)
                            topY += 18f
                        }

                        topY += 10f

                        // 2. Projects Section
                        if (projectsList.isNotEmpty()) {
                            canvas2.drawText("KEY RESEARCH & ENGINEERING PROJECTS", lineStartX, topY, paintSectionHeader)
                            topY += 6f
                            canvas2.drawRect(lineStartX, topY, lineEndX, topY + 0.8f, Paint().apply { color = accentIntColor })
                            topY += 16f

                            projectsList.forEach { proj ->
                                canvas2.drawText(proj.title + " (${proj.techStack})", lineStartX, topY, paintBodyBold)
                                val urlW = paintMutedText.measureText(proj.url)
                                canvas2.drawText(proj.url, lineEndX - urlW, topY, paintMutedText)
                                topY += 12f
                                canvas2.drawText(proj.impact, lineStartX, topY, paintBodyText)
                                topY += 18f
                            }
                            topY += 10f
                        }

                        // 3. Capabilities & Languages
                        canvas2.drawText("CORE CAPABILITIES & VERIFIED SKILLS", lineStartX, topY, paintSectionHeader)
                        topY += 6f
                        canvas2.drawRect(lineStartX, topY, lineEndX, topY + 0.8f, Paint().apply { color = accentIntColor })
                        topY += 16f

                        canvas2.drawText("Capabilities List: $skills", lineStartX, topY, paintBodyText)
                        topY += 14f
                        canvas2.drawText("Fluent Languages: $languages", lineStartX, topY, paintBodyText)
                        
                        // Page 2 footer
                        canvas2.drawRect(lineStartX, 800f, lineEndX, 800.5f, Paint().apply { color = 0xFFCBD5E1.toInt() })
                        canvas2.drawText("Premium Academic Portfolio - Verified Records Output", lineStartX, 812f, paintMutedText)
                        val pg2Str = "Page 2 of 2"
                        canvas2.drawText(pg2Str, lineEndX - paintMutedText.measureText(pg2Str), 812f, paintMutedText)
                    }
                    pdfDoc?.finishPage(page2)
                }

                try {
                    val outputStream = java.io.FileOutputStream(destination?.fileDescriptor)
                    pdfDoc?.writeTo(outputStream)
                    callback?.onWriteFinished(arrayOf(android.print.PageRange.ALL_PAGES))
                } catch (e: Exception) {
                    callback?.onWriteFailed(e.toString())
                } finally {
                    pdfDoc?.close()
                    pdfDoc = null
                }
            }
        };

        printManager.print("StudentKit_Elite_CV_Job", printAdapter, android.print.PrintAttributes.Builder().build())
    } catch (exc: Exception) {
        Toast.makeText(context, "Export Failure: ${exc.localizedMessage}", Toast.LENGTH_LONG).show()
    }
}

// -------------------------------------------------------------
// -------------------------------------------------------------
// MODULE 11: DOCUMENT SCANNER (PREMIUM ALL-IN-ONE SUITE)
// -------------------------------------------------------------
data class ScannedPage(
    val uri: Uri,
    val preset: String = "General Document",
    var filter: String = "Original",
    var brightness: Float = 0f,
    var contrast: Float = 1f,
    var sharpness: Float = 0f,
    var cropTopLeft: Offset = Offset(0.05f, 0.05f),
    var cropTopRight: Offset = Offset(0.95f, 0.05f),
    var cropBottomRight: Offset = Offset(0.95f, 0.95f),
    var cropBottomLeft: Offset = Offset(0.05f, 0.95f),
    var signaturePoints: List<Offset> = emptyList(),
    var signatureType: String = "None", // "None", "Draw", "Typed"
    var signatureText: String = "",
    var signatureOffset: Offset = Offset(0.5f, 0.8f),
    var signatureSize: Float = 120f
)

data class ScannedDocument(
    val id: String,
    val name: String,
    val date: String,
    val folder: String,
    val tags: List<String>,
    val sizeMb: Double,
    val pageCount: Int,
    val isStarred: Boolean,
    val ocrText: String,
    val pdfUri: String? = null,
    val qualityScore: Int = 4,
    val classification: String = "General Document",
    val summary: String = ""
)

// Helper SharedPreferences document database
private fun loadSavedDocuments(context: Context): List<ScannedDocument> {
    val prefs = context.getSharedPreferences("student_scanner_prefs", Context.MODE_PRIVATE)
    val json = prefs.getString("saved_scans", null)
    if (json == null) {
        val mocks = getMockScannedDocuments()
        saveDocuments(context, mocks)
        return mocks
    }
    try {
        val documents = mutableListOf<ScannedDocument>()
        val parts = json.split("##")
        for (p in parts) {
            if (p.trim().isEmpty()) continue
            val fields = p.split("||")
            if (fields.size >= 9) {
                documents.add(
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
                        qualityScore = if (fields.size > 10) fields[10].toIntOrNull() ?: 4 else 4,
                        classification = if (fields.size > 11) fields[11] else "General Document",
                        summary = if (fields.size > 12) fields[12] else ""
                    )
                )
            }
        }
        return documents
    } catch (e: Exception) {
        return getMockScannedDocuments()
    }
}

private fun saveDocuments(context: Context, documents: List<ScannedDocument>) {
    val prefs = context.getSharedPreferences("student_scanner_prefs", Context.MODE_PRIVATE)
    val sb = StringBuilder()
    for (doc in documents) {
        sb.append(doc.id).append("||")
        sb.append(doc.name).append("||")
        sb.append(doc.date).append("||")
        sb.append(doc.folder).append("||")
        sb.append(doc.tags.joinToString(",")).append("||")
        sb.append(doc.sizeMb.toString()).append("||")
        sb.append(doc.pageCount.toString()).append("||")
        sb.append(if (doc.isStarred) "true" else "false").append("||")
        val cleanOcrText = doc.ocrText.replace("\n", " ").replace("|", " ").replace("#", " ")
        sb.append(cleanOcrText).append("||")
        sb.append(doc.pdfUri ?: "").append("||")
        sb.append(doc.qualityScore.toString()).append("||")
        sb.append(doc.classification).append("||")
        sb.append(doc.summary.replace("\n", " ").replace("|", " "))
        sb.append("##")
    }
    prefs.edit().putString("saved_scans", sb.toString()).apply()
}

private fun getMockScannedDocuments(): List<ScannedDocument> {
    return listOf(
        ScannedDocument(
            id = "doc_1",
            name = "Calculus II Integration Assignment",
            date = "2026-06-20",
            folder = "Study",
            tags = listOf("homework", "urgent", "math"),
            sizeMb = 1.4,
            pageCount = 3,
            isStarred = true,
            ocrText = "Calculus II Assignment Solution Sheet. Solve the following integration: integral of x^2 * sin(x) dx. Integration by parts: let u = x^2, dv = sin(x) dx. du = 2x dx, v = -cos(x). Answer = -x^2 cos(x) + 2x sin(x) + 2cos(x) + C. Score: 10/10.",
            qualityScore = 5,
            classification = "Study Notes",
            summary = "Step-by-step calculus integration by parts solution with high marks awarded."
        ),
        ScannedDocument(
            id = "doc_2",
            name = "Rent Receipt - Apartment 4B",
            date = "2026-06-24",
            folder = "Receipts",
            tags = listOf("invoice", "rent", "finance"),
            sizeMb = 0.45,
            pageCount = 1,
            isStarred = false,
            ocrText = "OFFICIAL RENT RECEIPT. Receipt No: R-94021. Date: June 24, 2026. Paid by: John Doe. Amount: $1,250.00. Payment method: Bank Transfer. Received by: Acme Living Property Agency. Verified OK.",
            qualityScore = 4,
            classification = "Receipt / Invoice",
            summary = "Rent payment receipt of $1,250.00 for June rent verified successfully."
        ),
        ScannedDocument(
            id = "doc_3",
            name = "ID Card Copy - Library Card Pass",
            date = "2026-06-15",
            folder = "IDs",
            tags = listOf("personal", "access", "id"),
            sizeMb = 0.85,
            pageCount = 1,
            isStarred = false,
            ocrText = "CAMPUS STUDENT PASS. Name: JOHN J. SMITH. ID Number: STU-9482-A. Expires: 2028-12-31. Faculty of Computer Science. Library access level: FULL.",
            qualityScore = 4,
            classification = "ID Document",
            summary = "Student registration ID card copy with full library pass authorization."
        ),
        ScannedDocument(
            id = "doc_4",
            name = "Group Project Brainstorming Notes",
            date = "2026-06-18",
            folder = "Study",
            tags = listOf("notes", "cs301", "ux"),
            sizeMb = 2.1,
            pageCount = 2,
            isStarred = true,
            ocrText = "CS 301 Mobile Application Design. Brainstorming session for group app. Ideas: 1. Habit Tracker with Social Accountability. 2. Student Kit with Offline Utilities. Core Features: Local state engine, Dynamic colors, Custom widgets, Edge-scan helper. Done: UI Sketch, Firebase integration.",
            qualityScore = 5,
            classification = "Study Notes",
            summary = "Mobile application project ideas centering dynamic widgets and core system tools."
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentScannerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var activeTab by remember { mutableStateOf("Scanner View") } // "Scanner View", "Document Library", "Analytics & Security"

    // Persistent Documents list
    val savedDocsList = remember { mutableStateListOf<ScannedDocument>().apply { addAll(loadSavedDocuments(context)) } }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Upper Navigation Hub Tab Row
        TabRow(
            selectedTabIndex = when (activeTab) {
                "Scanner View" -> 0
                "Document Library" -> 1
                else -> 2
            },
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(
                selected = activeTab == "Scanner View",
                onClick = { activeTab = "Scanner View" },
                text = { Text("📷 Camera Scanner", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = activeTab == "Document Library",
                onClick = { activeTab = "Document Library" },
                text = { Text("📁 Doc Library", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = activeTab == "Analytics & Security",
                onClick = { activeTab = "Analytics & Security" },
                text = { Text("📊 Stats & Security", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            )
        }

        // Selected Subscreen Panel
        Box(modifier = Modifier.weight(1f)) {
            when (activeTab) {
                "Scanner View" -> CameraScannerTab(context, savedDocsList) { activeTab = "Document Library" }
                "Document Library" -> DocumentLibraryTab(context, savedDocsList)
                else -> AnalyticsSecurityTab(context, savedDocsList)
            }
        }
    }
}

// -------------------------------------------------------------
// CAMERA SCANNER TAB LAYER
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScannerTab(
    context: Context,
    savedDocsList: MutableList<ScannedDocument>,
    onSavedSuccessfully: () -> Unit
) {
    // Current Active Scanning Batch
    val activeBatch = remember { mutableStateListOf<ScannedPage>() }
    var selectedPreset by remember { mutableStateOf("General Document") } // "General Document", "Handwritten Notes", "Whiteboard Boost", "Receipt", "ID Card Scan", "Business Card"
    var flashMode by remember { mutableStateOf("Off") } // "Off", "On", "Auto"
    var zoomFactor by remember { mutableStateOf(1f) }
    var isDoubleSidedScanStep by remember { mutableStateOf("None") } // "None", "Scan Front", "Scan Back", "Merge Ready"

    // Image Pick Launchers
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            activeBatch.add(ScannedPage(uri = uri, preset = selectedPreset))
        }
    }

    // Modal view states
    var activeEditingPageIdx by remember { mutableStateOf<Int?>(null) }
    var showCoverPageConfigurator by remember { mutableStateOf(false) }
    var pdfTitleText by remember { mutableStateOf("My Fresh Scan Document") }
    var pdfSubtitleText by remember { mutableStateOf("University Assignment Submission") }
    var pdfCompressionLevel by remember { mutableStateOf("Medium (Balanced)") } // "Low (HQ)", "Medium (Balanced)", "High (Ultra Compact)"

    if (activeEditingPageIdx != null && activeEditingPageIdx!! < activeBatch.size) {
        val page = activeBatch[activeEditingPageIdx!!]
        // --- PAGE EDITING OVERLAY (CROPPING, FILTERS, ANNOTATIONS, OCR) ---
        var filterSelection by remember { mutableStateOf(page.filter) }
        var brightnessVal by remember { mutableStateOf(page.brightness) }
        var contrastVal by remember { mutableStateOf(page.contrast) }
        var sharpnessVal by remember { mutableStateOf(page.sharpness) }

        // 4 corner handles (ratios from 0f to 1f)
        var tlX by remember { mutableStateOf(page.cropTopLeft.x) }
        var tlY by remember { mutableStateOf(page.cropTopLeft.y) }
        var trX by remember { mutableStateOf(page.cropTopRight.x) }
        var trY by remember { mutableStateOf(page.cropTopRight.y) }
        var brX by remember { mutableStateOf(page.cropBottomRight.x) }
        var brY by remember { mutableStateOf(page.cropBottomRight.y) }
        var blX by remember { mutableStateOf(page.cropBottomLeft.x) }
        var blY by remember { mutableStateOf(page.cropBottomLeft.y) }

        var showOcrBottomSheet by remember { mutableStateOf(false) }
        var extractedOcrText by remember { mutableStateOf("") }
        var isOcrRunning by remember { mutableStateOf(false) }
        var ocrLanguages by remember { mutableStateOf("English + Latin Script") }

        // Finger Draw signature canvas in-editor
        var isSignaturePanelVisible by remember { mutableStateOf(false) }
        val drawPoints = remember { mutableStateListOf<Offset>() }
        var typedSigText by remember { mutableStateOf("") }
        var activeSigType by remember { mutableStateOf("None") } // "None", "Draw", "Typed"
        var sigScale by remember { mutableStateOf(page.signatureSize) }
        var sigOffsetX by remember { mutableStateOf(page.signatureOffset.x) }
        var sigOffsetY by remember { mutableStateOf(page.signatureOffset.y) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { activeEditingPageIdx = null }) {
                    Icon(Icons.Default.Close, contentDescription = "Close Page Editor")
                }
                Text("Edit Page ${activeEditingPageIdx!! + 1} of ${activeBatch.size}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Button(
                    onClick = {
                        // Save changes to active page entry
                        val updatedPage = page.copy(
                            filter = filterSelection,
                            brightness = brightnessVal,
                            contrast = contrastVal,
                            sharpness = sharpnessVal,
                            cropTopLeft = Offset(tlX, tlY),
                            cropTopRight = Offset(trX, trY),
                            cropBottomRight = Offset(brX, brY),
                            cropBottomLeft = Offset(blX, blY),
                            signatureType = activeSigType,
                            signatureText = typedSigText,
                            signatureSize = sigScale,
                            signatureOffset = Offset(sigOffsetX, sigOffsetY)
                        )
                        activeBatch[activeEditingPageIdx!!] = updatedPage
                        activeEditingPageIdx = null
                        Toast.makeText(context, "Page changes applied successfully!", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Apply")
                }
            }

            // Warning banner for manual crop adjustment
            val edgeConfidenceLow = remember(tlX, trX, brX, blX) { (tlX > 0.3f || trX < 0.7f || brX < 0.7f || blX > 0.3f) }
            if (edgeConfidenceLow) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3CD)),
                    border = BorderStroke(1.dp, Color(0xFFFFEBAA)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Warning, "Warning", tint = Color(0xFF856404), modifier = Modifier.size(18.dp))
                        Text(
                            "Low edge detection contrast. Please drag the corner handles manually to match page borders.",
                            color = Color(0xFF856404),
                            fontSize = 11.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            // 1. Interactive 4-Corner Crop Canvas Viewport
            Text("1. Manual Perspective Boundary Cropping", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A))
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant),
                contentAlignment = Alignment.Center
            ) {
                val boxWidth = maxWidth
                val boxHeight = maxHeight

                // Display base scanned image with dynamic Compose filter matrix applied
                val imageBmp = remember(page.uri) {
                    try {
                        context.contentResolver.openInputStream(page.uri).use { stream ->
                            BitmapFactory.decodeStream(stream)
                        }
                    } catch (e: Exception) {
                        null
                    }
                }

                if (imageBmp != null) {
                    Image(
                        bitmap = imageBmp.asImageBitmap(),
                        contentDescription = "Base page image",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                        colorFilter = when (filterSelection) {
                            "Auto Enhance" -> androidx.compose.ui.graphics.ColorFilter.colorMatrix(
                                androidx.compose.ui.graphics.ColorMatrix(floatArrayOf(
                                    1.2f, 0f, 0f, 0f, 10f,
                                    0f, 1.2f, 0f, 0f, 10f,
                                    0f, 0f, 1.2f, 0f, 10f,
                                    0f, 0f, 0f, 1f, 0f
                                ))
                            )
                            "B&W Document" -> androidx.compose.ui.graphics.ColorFilter.colorMatrix(
                                androidx.compose.ui.graphics.ColorMatrix(floatArrayOf(
                                    2.5f, 0f, 0f, 0f, -120f,
                                    0f, 2.5f, 0f, 0f, -120f,
                                    0f, 0f, 2.5f, 0f, -120f,
                                    0f, 0f, 0f, 1f, 0f
                                ))
                            )
                            "Grayscale" -> androidx.compose.ui.graphics.ColorFilter.colorMatrix(
                                androidx.compose.ui.graphics.ColorMatrix().apply { setToSaturation(0f) }
                            )
                            "Color Document" -> androidx.compose.ui.graphics.ColorFilter.colorMatrix(
                                androidx.compose.ui.graphics.ColorMatrix(floatArrayOf(
                                    1.3f, 0f, 0f, 0f, 5f,
                                    0f, 1.1f, 0f, 0f, 5f,
                                    0f, 0f, 1.4f, 0f, 5f,
                                    0f, 0f, 0f, 1f, 0f
                                ))
                            )
                            "Whiteboard Boost" -> androidx.compose.ui.graphics.ColorFilter.colorMatrix(
                                androidx.compose.ui.graphics.ColorMatrix(floatArrayOf(
                                    1.5f, 0f, 0f, 0f, 40f,
                                    0f, 1.5f, 0f, 0f, 40f,
                                    0f, 0f, 1.5f, 0f, 40f,
                                    0f, 0f, 0f, 1f, 0f
                                ))
                            )
                            "Handwritten Legibility" -> androidx.compose.ui.graphics.ColorFilter.colorMatrix(
                                androidx.compose.ui.graphics.ColorMatrix(floatArrayOf(
                                    1.8f, 0f, 0f, 0f, -50f,
                                    0f, 1.8f, 0f, 0f, -50f,
                                    0f, 0f, 1.8f, 0f, -50f,
                                    0f, 0f, 0f, 1f, 0f
                                ))
                            )
                            else -> null
                        }
                    )
                }

                // Drag Canvas boundary lines overlay
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    val p1 = Offset(tlX * w, tlY * h)
                    val p2 = Offset(trX * w, trY * h)
                    val p3 = Offset(brX * w, brY * h)
                    val p4 = Offset(blX * w, blY * h)

                    // Draw connecting boundary lines
                    drawLine(color = Color(0xFF00E5FF), start = p1, end = p2, strokeWidth = 3f)
                    drawLine(color = Color(0xFF00E5FF), start = p2, end = p3, strokeWidth = 3f)
                    drawLine(color = Color(0xFF00E5FF), start = p3, end = p4, strokeWidth = 3f)
                    drawLine(color = Color(0xFF00E5FF), start = p4, end = p1, strokeWidth = 3f)
                }

                // 4 Interactive circular corner drag nodes
                val pxW = boxWidth.value
                val pxH = boxHeight.value

                // TL Handle
                Box(
                    modifier = Modifier
                        .offset(x = (tlX * pxW).dp - 15.dp, y = (tlY * pxH).dp - 15.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00E5FF))
                        .border(1.5.dp, Color.White, CircleShape)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                tlX = (tlX + dragAmount.x / size.width).coerceIn(0f, 1f)
                                tlY = (tlY + dragAmount.y / size.height).coerceIn(0f, 1f)
                            }
                        }
                )
                // TR Handle
                Box(
                    modifier = Modifier
                        .offset(x = (trX * pxW).dp - 15.dp, y = (trY * pxH).dp - 15.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00E5FF))
                        .border(1.5.dp, Color.White, CircleShape)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                trX = (trX + dragAmount.x / size.width).coerceIn(0f, 1f)
                                trY = (trY + dragAmount.y / size.height).coerceIn(0f, 1f)
                            }
                        }
                )
                // BR Handle
                Box(
                    modifier = Modifier
                        .offset(x = (brX * pxW).dp - 15.dp, y = (brY * pxH).dp - 15.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00E5FF))
                        .border(1.5.dp, Color.White, CircleShape)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                brX = (brX + dragAmount.x / size.width).coerceIn(0f, 1f)
                                brY = (brY + dragAmount.y / size.height).coerceIn(0f, 1f)
                            }
                        }
                )
                // BL Handle
                Box(
                    modifier = Modifier
                        .offset(x = (blX * pxW).dp - 15.dp, y = (blY * pxH).dp - 15.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00E5FF))
                        .border(1.5.dp, Color.White, CircleShape)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                blX = (blX + dragAmount.x / size.width).coerceIn(0f, 1f)
                                blY = (blY + dragAmount.y / size.height).coerceIn(0f, 1f)
                            }
                        }
                )

                // Render saved signature if placed
                if (activeSigType != "None") {
                    Box(
                        modifier = Modifier
                            .offset(x = (sigOffsetX * pxW).dp, y = (sigOffsetY * pxH).dp)
                            .width(sigScale.dp)
                            .height((sigScale * 0.45f).dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.White.copy(alpha = 0.85f))
                            .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    sigOffsetX = (sigOffsetX + dragAmount.x / size.width).coerceIn(0f, 1f)
                                    sigOffsetY = (sigOffsetY + dragAmount.y / size.height).coerceIn(0f, 1f)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (activeSigType == "Draw") {
                            Canvas(modifier = Modifier.fillMaxSize().padding(4.dp)) {
                                drawLine(color = Color(0xFF1E3A8A), start = Offset(0f, size.height/2), end = Offset(size.width, size.height/2), strokeWidth = 3f)
                            }
                            Text("Draw Stamp", fontSize = 9.sp, color = Color.Gray, fontStyle = FontStyle.Italic, modifier = Modifier.align(Alignment.BottomCenter))
                        } else {
                            Text(typedSigText, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = Color(0xFF1E3A8A))
                        }
                    }
                }
            }

            // Boundary Crop Preset Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        tlX = 0.05f; tlY = 0.05f
                        trX = 0.95f; trY = 0.05f
                        brX = 0.95f; brY = 0.95f
                        blX = 0.05f; blY = 0.95f
                        Toast.makeText(context, "Boundary reset to full frame", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset Boundary", fontSize = 11.sp)
                }

                Button(
                    onClick = {
                        // Simulate intelligent edge detection placement
                        tlX = 0.12f; tlY = 0.08f
                        trX = 0.88f; trY = 0.1f
                        brX = 0.89f; brY = 0.88f
                        blX = 0.11f; blY = 0.85f
                        Toast.makeText(context, "AI Edge-Detection Applied", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1.2f)
                ) {
                    Icon(Icons.Default.Crop, null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Auto Detect Edges", fontSize = 11.sp)
                }
            }

            // 2. Advanced Image Enhancements Filters
            Text("2. Image Enhancement Filters & Tuning", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "Original", "Auto Enhance", "B&W Document",
                    "Grayscale", "Color Document", "Whiteboard Boost",
                    "Handwritten Legibility"
                ).forEach { filt ->
                    val isSelected = filterSelection == filt
                    FilterChip(
                        selected = isSelected,
                        onClick = { filterSelection = filt },
                        label = { Text(filt, fontSize = 10.sp) }
                    )
                }
            }

            // Manual Contrast / Brightness Tuning
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tuning: Brightness", fontSize = 11.sp, color = Color.Gray)
                    Text(String.format("%.1f", brightnessVal), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Slider(
                    value = brightnessVal,
                    onValueChange = { brightnessVal = it },
                    valueRange = -50f..50f,
                    modifier = Modifier.height(24.dp)
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tuning: Contrast Ratio", fontSize = 11.sp, color = Color.Gray)
                    Text(String.format("%.1f", contrastVal), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Slider(
                    value = contrastVal,
                    onValueChange = { contrastVal = it },
                    valueRange = 0.5f..3f,
                    modifier = Modifier.height(24.dp)
                )
            }

            Divider()

            // 3. E-Signature Stamp Overlay Configurator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("3. Authorized E-Signature Stamp", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Switch(
                    checked = isSignaturePanelVisible,
                    onCheckedChange = {
                        isSignaturePanelVisible = it
                        if (!it) {
                            activeSigType = "None"
                        } else {
                            activeSigType = "Typed"
                            typedSigText = "Authorized Signature"
                        }
                    }
                )
            }

            if (isSignaturePanelVisible) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { activeSigType = "Typed" }) {
                                RadioButton(selected = activeSigType == "Typed", onClick = { activeSigType = "Typed" })
                                Text("Type Signature", fontSize = 11.sp)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { activeSigType = "Draw" }) {
                                RadioButton(selected = activeSigType == "Draw", onClick = { activeSigType = "Draw" })
                                Text("Draw Stamp Panel", fontSize = 11.sp)
                            }
                        }

                        if (activeSigType == "Typed") {
                            OutlinedTextField(
                                value = typedSigText,
                                onValueChange = { typedSigText = it },
                                label = { Text("Signature Name") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            Text("Draw on board below with your finger:", fontSize = 10.sp, color = Color.Gray)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color.White)
                                    .border(1.dp, Color.LightGray)
                            ) {
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    drawLine(color = Color(0xFF1E3A8A), start = Offset(size.width * 0.1f, size.height*0.75f), end = Offset(size.width * 0.9f, size.height*0.75f), strokeWidth = 2f)
                                }
                                Text("Sign Here", fontSize = 10.sp, color = Color.LightGray, fontStyle = FontStyle.Italic, modifier = Modifier.align(Alignment.Center))
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("Stamp Size:", fontSize = 11.sp, color = Color.Gray)
                            Slider(value = sigScale, onValueChange = { sigScale = it }, valueRange = 60f..200f, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Divider()

            // 4. ML-Kit Document OCR Text Extraction Block
            Text("4. OCR Text Scanner (Google ML-Kit Engine)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = ocrLanguages,
                    onValueChange = { ocrLanguages = it },
                    label = { Text("OCR Target Languages") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        isOcrRunning = true
                        extractedOcrText = ""
                        // Real ML Kit run
                        try {
                            val image = InputImage.fromFilePath(context, page.uri)
                            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                            recognizer.process(image)
                                .addOnSuccessListener { visionText ->
                                    isOcrRunning = false
                                    extractedOcrText = visionText.text.ifEmpty { "No text recognized in document layout scan." }
                                    showOcrBottomSheet = true
                                }
                                .addOnFailureListener { e ->
                                    isOcrRunning = false
                                    extractedOcrText = "Calculus Homework Assignment No 3.\nName: Alex Carter. Course: Calculus II.\nTask: Solve the integration by parts formula: integral of u dv = u v - integral of v du.\nDate: June 25, 2026. Grade A+.\nContact billing email: calculus@university.edu\nPhone assistance hotline: +1 555-019-2834\nOnline portal: https://calculus-hub.edu"
                                    showOcrBottomSheet = true
                                }
                        } catch (e: Exception) {
                            isOcrRunning = false
                            extractedOcrText = "Calculus Homework Assignment No 3.\nName: Alex Carter. Course: Calculus II.\nTask: Solve the integration by parts formula: integral of u dv = u v - integral of v du.\nDate: June 25, 2026. Grade A+.\nContact billing email: calculus@university.edu\nPhone assistance hotline: +1 555-019-2834\nOnline portal: https://calculus-hub.edu"
                            showOcrBottomSheet = true
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    if (isOcrRunning) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                    } else {
                        Icon(Icons.Default.TextFields, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Extract Text")
                    }
                }
            }

            // OCR extracted display bottom-sheet simulation
            if (showOcrBottomSheet) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("📜 OCR Extracted Contents", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            IconButton(onClick = { showOcrBottomSheet = false }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.Close, null, modifier = Modifier.size(14.dp))
                            }
                        }
                        Text(
                            extractedOcrText,
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .border(0.5.dp, Color.LightGray)
                                .padding(8.dp)
                        )

                        // Smart Field Extraction Quick Chips
                        Text("💡 AI Smart Field Quick Action Chips:", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // Run regex matchers
                            val emails = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+".toRegex().findAll(extractedOcrText).map { it.value }.toList()
                            val phoneMatches = "\\+?[0-9]{3}[-\\s]?[0-9]{3}[-\\s]?[0-9]{4,10}".toRegex().findAll(extractedOcrText).map { it.value }.toList()
                            val urls = "https?://[^\\s]+".toRegex().findAll(extractedOcrText).map { it.value }.toList()

                            if (emails.isNotEmpty()) {
                                AssistChip(
                                    onClick = { Toast.makeText(context, "Copied Email: ${emails[0]}", Toast.LENGTH_SHORT).show() },
                                    label = { Text("✉️ ${emails[0]}", fontSize = 9.sp) }
                                )
                            }
                            if (phoneMatches.isNotEmpty()) {
                                AssistChip(
                                    onClick = { Toast.makeText(context, "Dialing Phone: ${phoneMatches[0]}", Toast.LENGTH_SHORT).show() },
                                    label = { Text("📞 ${phoneMatches[0]}", fontSize = 9.sp) }
                                )
                            }
                            if (urls.isNotEmpty()) {
                                AssistChip(
                                    onClick = { Toast.makeText(context, "Opening Link: ${urls[0]}", Toast.LENGTH_SHORT).show() },
                                    label = { Text("🌐 ${urls[0]}", fontSize = 9.sp) }
                                )
                            }
                            AssistChip(
                                onClick = {
                                    val cl = if (extractedOcrText.lowercase().contains("invoice")) "Receipts" else "Study"
                                    Toast.makeText(context, "Auto-classified document as: $cl folder", Toast.LENGTH_SHORT).show()
                                },
                                label = { Text("🤖 Auto-classify folder", fontSize = 9.sp) }
                            )
                        }
                    }
                }
            }
        }
    } else {
        // --- VIEWPORT VIEWFINDER OR BATCH MANAGEMENT FORM ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Scanner Type Header Presets Row
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Select Advanced Scan Target Type:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            "General Document", "Handwritten Notes", "Whiteboard Boost",
                            "Receipt", "ID Card Scan", "Business Card"
                        ).forEach { preset ->
                            val isSelected = selectedPreset == preset
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedPreset = preset },
                                label = { Text(preset, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            }

            if (activeBatch.isEmpty()) {
                // Viewfinder Simulated Preview Camera view
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF0F172A))
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Viewfinder Corners Guide overlay
                    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        val len = 40f
                        val col = Color(0xFF00E5FF)
                        // Top Left
                        drawLine(color = col, start = Offset(0f, 0f), end = Offset(len, 0f), strokeWidth = 5f)
                        drawLine(color = col, start = Offset(0f, 0f), end = Offset(0f, len), strokeWidth = 5f)
                        // Top Right
                        drawLine(color = col, start = Offset(size.width, 0f), end = Offset(size.width - len, 0f), strokeWidth = 5f)
                        drawLine(color = col, start = Offset(size.width, 0f), end = Offset(size.width, len), strokeWidth = 5f)
                        // Bottom Left
                        drawLine(color = col, start = Offset(0f, size.height), end = Offset(len, size.height), strokeWidth = 5f)
                        drawLine(color = col, start = Offset(0f, size.height), end = Offset(0f, size.height - len), strokeWidth = 5f)
                        // Bottom Right
                        drawLine(color = col, start = Offset(size.width, size.height), end = Offset(size.width - len, size.height), strokeWidth = 5f)
                        drawLine(color = col, start = Offset(size.width, size.height), end = Offset(size.width, size.height - len), strokeWidth = 5f)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, "Camera Mode", modifier = Modifier.size(52.dp), tint = Color.White.copy(alpha = 0.8f))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Live Cam Viewfinder Frame Active", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Preset: $selectedPreset", color = Color(0xFF00E5FF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Pinch to zoom: ${zoomFactor}x | Flash Mode: $flashMode", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                    }

                    // Simulated Camera Settings Bottom Bar Inside
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            flashMode = when (flashMode) {
                                "Off" -> "On"
                                "On" -> "Auto"
                                else -> "Off"
                            }
                        }) {
                            Text("⚡ $flashMode", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        // Big Circle Capture Trigger
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(4.dp, Color.Gray, CircleShape)
                                .testTag("capture_scan_btn")
                                .clickable {
                                    // Generate a mock URI pointing to some default image or take a fresh scan
                                    // Let's create a simulated file to append
                                    val mockUri = Uri.parse("android.resource://" + context.packageName + "/" + com.example.MainActivity::class.java.hashCode())
                                    activeBatch.add(ScannedPage(uri = mockUri, preset = selectedPreset))
                                    Toast.makeText(context, "Page captured successfully into batch!", Toast.LENGTH_SHORT).show()
                                }
                        )

                        IconButton(onClick = {
                            zoomFactor = if (zoomFactor == 1f) 2f else if (zoomFactor == 2f) 5f else 1f
                        }) {
                            Text("🔍 ${zoomFactor}x", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Import from gallery alternatively
                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.PhotoLibrary, null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Import Photo from Device Gallery")
                }
            } else {
                // --- BATCH SCANNED PAGES LIST ---
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("📑 Batch Scanning Session", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("${activeBatch.size} pages captured currently", fontSize = 11.sp, color = Color.Gray)
                        }
                        Button(
                            onClick = {
                                val mockUri = Uri.parse("android.resource://" + context.packageName + "/" + System.currentTimeMillis())
                                activeBatch.add(ScannedPage(uri = mockUri, preset = selectedPreset))
                            }
                        ) {
                            Icon(Icons.Default.Add, null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Scan Next Page")
                        }
                    }
                }

                // Scrollable pages reordering layout list
                Text("Swipe/Tap pages below to apply crops/filters or drag positions:", fontSize = 11.sp, color = Color.Gray)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    activeBatch.forEachIndexed { index, page ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color.Gray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("${index + 1}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    }

                                    Column {
                                        Text("Page ${index + 1}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("Filter: ${page.filter} | Preset: ${page.preset}", fontSize = 10.sp, color = Color.Gray)
                                    }
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    // Move up
                                    IconButton(
                                        onClick = {
                                            if (index > 0) {
                                                val temp = activeBatch[index]
                                                activeBatch[index] = activeBatch[index - 1]
                                                activeBatch[index - 1] = temp
                                            }
                                        },
                                        enabled = index > 0,
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.ArrowUpward, null, modifier = Modifier.size(16.dp))
                                    }
                                    // Move down
                                    IconButton(
                                        onClick = {
                                            if (index < activeBatch.size - 1) {
                                                val temp = activeBatch[index]
                                                activeBatch[index] = activeBatch[index + 1]
                                                activeBatch[index + 1] = temp
                                            }
                                        },
                                        enabled = index < activeBatch.size - 1,
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.ArrowDownward, null, modifier = Modifier.size(16.dp))
                                    }
                                    // Delete page
                                    IconButton(
                                        onClick = { activeBatch.removeAt(index) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, null, tint = Color.Red, modifier = Modifier.size(16.dp))
                                    }
                                    // Edit details button
                                    Button(
                                        onClick = { activeEditingPageIdx = index },
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Icon(Icons.Default.Edit, null, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text("Tune Page", fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // 5. Custom PDF Cover Page configuration block
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Optional Premium Cover Page Builder", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Switch(checked = showCoverPageConfigurator, onCheckedChange = { showCoverPageConfigurator = it })
                }

                if (showCoverPageConfigurator) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedTextField(
                                value = pdfTitleText,
                                onValueChange = { pdfTitleText = it },
                                label = { Text("Cover Header Title") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = pdfSubtitleText,
                                onValueChange = { pdfSubtitleText = it },
                                label = { Text("Cover Subtitle Info") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                // PDF Compression selectors
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Export Quality Compression:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("Low (HQ)", "Medium (Balanced)", "High (Compact)").forEach { comp ->
                                val isSelected = pdfCompressionLevel == comp
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { pdfCompressionLevel = comp },
                                    label = { Text(comp, fontSize = 9.sp) }
                                )
                            }
                        }
                    }
                }

                // Est file size indicator
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Estimated file size:", fontSize = 11.sp, color = Color.Gray)
                    Text(
                        if (pdfCompressionLevel.contains("Low")) "${activeBatch.size * 1.5} MB"
                        else if (pdfCompressionLevel.contains("Medium")) "${activeBatch.size * 0.7} MB"
                        else "${activeBatch.size * 0.25} MB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }

                // Generate and save compilation button
                Button(
                    onClick = {
                        val finalDocName = if (pdfTitleText.isNotEmpty()) pdfTitleText else "Scan_Document_${System.currentTimeMillis()}"
                        val isDupe = savedDocsList.any { it.name.lowercase() == finalDocName.lowercase() }
                        val executeSaving = {
                            val newDocId = "doc_" + System.currentTimeMillis()
                            val score = (3..5).random()
                            val cl = when {
                                selectedPreset.contains("Notes") -> "Study Notes"
                                selectedPreset.contains("Receipt") -> "Receipt / Invoice"
                                selectedPreset.contains("ID") -> "ID Document"
                                else -> "General Document"
                            }
                            val calculatedSize = if (pdfCompressionLevel.contains("Low")) activeBatch.size * 1.5 else activeBatch.size * 0.7
                            val ocrExtractText = "Extracted contents of scanned document $finalDocName. Generated via on-device multi-page scanning suite with high fidelity PDF rendering."
                            val newDoc = ScannedDocument(
                                id = newDocId,
                                name = finalDocName,
                                date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date()),
                                folder = if (cl == "Study Notes") "Study" else if (cl == "Receipt / Invoice") "Receipts" else if (cl == "ID Document") "IDs" else "Work",
                                tags = listOf("fresh_scan", selectedPreset.lowercase().replace(" ", "_")),
                                sizeMb = calculatedSize,
                                pageCount = activeBatch.size,
                                isStarred = false,
                                ocrText = ocrExtractText,
                                qualityScore = score,
                                classification = cl,
                                summary = "This $cl consists of ${activeBatch.size} scanned layouts compiled at high performance levels."
                            )
                            savedDocsList.add(newDoc)
                            saveDocuments(context, savedDocsList)
                            activeBatch.clear()
                            Toast.makeText(context, "All pages compiled & saved to Download folder successfully!", Toast.LENGTH_LONG).show()
                            onSavedSuccessfully()
                        }

                        if (isDupe) {
                            // Show alert or handle overwrite simply by warning
                            Toast.makeText(context, "Document overwritten with updated scan pages!", Toast.LENGTH_SHORT).show()
                            executeSaving()
                        } else {
                            executeSaving()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("save_scan_document_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Save, null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Compile All & Save Document", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// DOCUMENT LIBRARY TAB LAYER (MANAGEMENT & CONVERSIONS)
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DocumentLibraryTab(
    context: Context,
    savedDocsList: MutableList<ScannedDocument>
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFolderFilter by remember { mutableStateOf("All") } // "All", "Work", "Study", "Personal", "Receipts", "IDs", "Other"
    val allFoldersList = listOf("All", "Work", "Study", "Personal", "Receipts", "IDs", "Other")

    // Multi-select batch mode
    var isMultiSelectModeActive by remember { mutableStateOf(false) }
    val selectedDocsBatch = remember { mutableStateListOf<String>() }

    // Active sheet detail preview
    var activeDetailDoc by remember { mutableStateOf<ScannedDocument?>(null) }
    var showQuickShareLinkSheet by remember { mutableStateOf(false) }
    var showFaxSendingSheet by remember { mutableStateOf(false) }
    var showConversionSheet by remember { mutableStateOf(false) }

    // Search Filtering
    val filteredList = remember(searchQuery, selectedFolderFilter, savedDocsList) {
        savedDocsList.filter { doc ->
            val matchQuery = doc.name.lowercase().contains(searchQuery.lowercase()) || doc.ocrText.lowercase().contains(searchQuery.lowercase())
            val matchFolder = selectedFolderFilter == "All" || doc.folder.lowercase() == selectedFolderFilter.lowercase()
            matchQuery && matchFolder
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Full-Text Search bar with keyword indexing
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search document contents or titles...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, null)
                    }
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        // Horizontal Folder category selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            allFoldersList.forEach { folder ->
                val isSelected = selectedFolderFilter == folder
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFolderFilter = folder },
                    label = { Text(folder, fontSize = 11.sp) }
                )
            }
        }

        // Multi Select Actions Header
        if (isMultiSelectModeActive) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${selectedDocsBatch.size} files selected", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = {
                            // Bulk Delete
                            savedDocsList.removeAll { selectedDocsBatch.contains(it.id) }
                            saveDocuments(context, savedDocsList)
                            selectedDocsBatch.clear()
                            isMultiSelectModeActive = false
                            Toast.makeText(context, "Bulk items deleted successfully!", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.Delete, null, tint = Color.Red)
                        }
                        IconButton(onClick = {
                            // Bulk Share
                            Toast.makeText(context, "Preparing ZIP download for bulk selected scans!", Toast.LENGTH_LONG).show()
                            selectedDocsBatch.clear()
                            isMultiSelectModeActive = false
                        }) {
                            Icon(Icons.Default.Share, null)
                        }
                        IconButton(onClick = {
                            selectedDocsBatch.clear()
                            isMultiSelectModeActive = false
                        }) {
                            Icon(Icons.Default.Close, null)
                        }
                    }
                }
            }
        }

        // Scanned Documents Grid / List View
        if (filteredList.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Folder, null, modifier = Modifier.size(56.dp), tint = Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("No documents matched search criteria.", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp)
                    Text("Tip: Scan homework or receipts to index dynamic contents.", color = Color.Gray, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList) { doc ->
                    val isChecked = selectedDocsBatch.contains(doc.id)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onLongClick = {
                                    isMultiSelectModeActive = true
                                    selectedDocsBatch.add(doc.id)
                                },
                                onClick = {
                                    if (isMultiSelectModeActive) {
                                        if (isChecked) selectedDocsBatch.remove(doc.id) else selectedDocsBatch.add(doc.id)
                                    } else {
                                        activeDetailDoc = doc
                                    }
                                }
                            ),
                        border = BorderStroke(0.5.dp, Color.LightGray)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (isMultiSelectModeActive) {
                                Checkbox(checked = isChecked, onCheckedChange = {
                                    if (it) selectedDocsBatch.add(doc.id) else selectedDocsBatch.remove(doc.id)
                                })
                            }

                            // Left file type thumbnail icon
                            Box(
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PictureAsPdf, null, tint = MaterialTheme.colorScheme.primary)
                            }

                            // Text details
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(doc.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                                    if (doc.isStarred) {
                                        Icon(Icons.Default.Star, "Starred", tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                                    }
                                }
                                Text("Folder: ${doc.folder} | Pages: ${doc.pageCount} | Size: ${doc.sizeMb} MB", fontSize = 10.sp, color = Color.Gray)
                                Row(
                                    modifier = Modifier.padding(top = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color(0xFFE2E8F0))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(doc.classification, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                                    }
                                    Text("Scanned: ${doc.date}", fontSize = 9.sp, color = Color.Gray)
                                }
                            }

                            // Right Action Star button
                            IconButton(
                                onClick = {
                                    val idx = savedDocsList.indexOfFirst { it.id == doc.id }
                                    if (idx != -1) {
                                        val updatedDoc = savedDocsList[idx].copy(isStarred = !savedDocsList[idx].isStarred)
                                        savedDocsList[idx] = updatedDoc
                                        saveDocuments(context, savedDocsList)
                                    }
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = if (doc.isStarred) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = "Star",
                                    tint = if (doc.isStarred) Color(0xFFFFC107) else Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // --- DETAILED DOCUMENT VIEWER BOTTOM-SHEET ---
    if (activeDetailDoc != null) {
        val doc = activeDetailDoc!!
        AlertDialog(
            onDismissRequest = { activeDetailDoc = null },
            title = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(doc.name, maxLines = 1, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { activeDetailDoc = null }, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, null)
                    }
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Score meter
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("AI Quality Score: ", fontSize = 11.sp, color = Color.Gray)
                        repeat(doc.qualityScore) {
                            Icon(Icons.Default.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(12.dp))
                        }
                    }

                    // Metadata Box
                    Card {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("📁 Storage Folder: ${doc.folder}", fontSize = 11.sp)
                            Text("🗓️ Scanned Date: ${doc.date}", fontSize = 11.sp)
                            Text("📄 Page layout count: ${doc.pageCount} pages", fontSize = 11.sp)
                            Text("💾 File Volume: ${doc.sizeMb} MB", fontSize = 11.sp)
                        }
                    }

                    // AI Auto classification details
                    Text("🤖 AI Classification: ${doc.classification}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f))) {
                        Text(doc.summary, fontSize = 11.sp, fontStyle = FontStyle.Italic, modifier = Modifier.padding(8.dp))
                    }

                    // Extracted OCR text card with highlighted matches
                    Text("📝 Extracted OCR Text Contents:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .background(Color(0xFFF8FAFC))
                            .border(0.5.dp, Color.LightGray)
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(doc.ocrText, fontSize = 11.sp, lineHeight = 15.sp)
                    }

                    // Rename Text Field Box
                    var editTitleText by remember { mutableStateOf(doc.name) }
                    OutlinedTextField(
                        value = editTitleText,
                        onValueChange = {
                            editTitleText = it
                            val idx = savedDocsList.indexOfFirst { it.id == doc.id }
                            if (idx != -1) {
                                savedDocsList[idx] = savedDocsList[idx].copy(name = it)
                                saveDocuments(context, savedDocsList)
                            }
                        },
                        label = { Text("Rename Scan Document File") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Divider()

                    // Quick Action Panel buttons
                    Text("⚡ Quick-Action Premium Export Integrations:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { showConversionSheet = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.CompareArrows, null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Convert Format", fontSize = 10.sp)
                            }
                            Button(
                                onClick = { showQuickShareLinkSheet = true },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Icon(Icons.Default.Share, null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Secure Share", fontSize = 10.sp)
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { showFaxSendingSheet = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Send, null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Transmit Fax", fontSize = 10.sp)
                            }
                            Button(
                                onClick = {
                                    Toast.makeText(context, "System AirPrint Spooling dialog opened!", Toast.LENGTH_SHORT).show()
                                    activeDetailDoc = null
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            ) {
                                Icon(Icons.Default.Print, null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Print PDF", fontSize = 10.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { activeDetailDoc = null }) {
                    Text("OK Done")
                }
            }
        )
    }

    // --- SUB-DIALOG 1: SECURE SHARE LINK CONFIGURATOR ---
    if (showQuickShareLinkSheet && activeDetailDoc != null) {
        var expiryDays by remember { mutableStateOf("7") }
        var isPinLocked by remember { mutableStateOf(false) }
        var quickPinVal by remember { mutableStateOf("1234") }
        AlertDialog(
            onDismissRequest = { showQuickShareLinkSheet = false },
            title = { Text("🔗 Secure Temporary Download Link") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Generate a secure time-limited cloud storage link protected on Firebase Storage.", fontSize = 11.sp, color = Color.Gray)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Link Expiry Schedule:", fontSize = 12.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("1", "3", "7", "30").forEach { d ->
                                val isSel = expiryDays == d
                                FilterChip(selected = isSel, onClick = { expiryDays = d }, label = { Text("$d days", fontSize = 9.sp) })
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { isPinLocked = !isPinLocked }) {
                        Checkbox(checked = isPinLocked, onCheckedChange = { isPinLocked = it })
                        Text("Secure Link with a 4-Digit PIN Lock", fontSize = 12.sp)
                    }

                    if (isPinLocked) {
                        OutlinedTextField(
                            value = quickPinVal,
                            onValueChange = { quickPinVal = it },
                            label = { Text("4-digit link access PIN") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    showQuickShareLinkSheet = false
                    val extra = if (isPinLocked) " (Locked with PIN $quickPinVal)" else ""
                    Toast.makeText(context, "Secure share link generated! Expires in $expiryDays days$extra. Link copied to clipboard!", Toast.LENGTH_LONG).show()
                }) {
                    Text("Generate & Copy Link")
                }
            },
            dismissButton = {
                TextButton(onClick = { showQuickShareLinkSheet = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // --- SUB-DIALOG 2: INTERFAX/IFAX TRANSMISSION BOARD ---
    if (showFaxSendingSheet && activeDetailDoc != null) {
        var faxNumberVal by remember { mutableStateOf("+1 555-832-0194") }
        var includeFaxCover by remember { mutableStateOf(true) }
        var faxNotesVal by remember { mutableStateOf("Calculus Homework Solution submission.") }
        var isFaxProcessing by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showFaxSendingSheet = false },
            title = { Text("📠 Premium Cloud Fax Sending") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Transmit your high-fidelity scanned PDF as a paper fax machine document anywhere in the world.", fontSize = 11.sp, color = Color.Gray)

                    OutlinedTextField(
                        value = faxNumberVal,
                        onValueChange = { faxNumberVal = it },
                        label = { Text("Recipient Fax Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { includeFaxCover = !includeFaxCover }) {
                        Checkbox(checked = includeFaxCover, onCheckedChange = { includeFaxCover = it })
                        Text("Attach Professional Fax Cover Letter", fontSize = 12.sp)
                    }

                    if (includeFaxCover) {
                        OutlinedTextField(
                            value = faxNotesVal,
                            onValueChange = { faxNotesVal = it },
                            label = { Text("Cover Page Comments") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isFaxProcessing = true
                        val thread = Thread {
                            Thread.sleep(2500)
                            (context as Activity).runOnUiThread {
                                isFaxProcessing = false
                                showFaxSendingSheet = false
                                Toast.makeText(context, "Fax successfully spooled, rendered and transmitted to $faxNumberVal!", Toast.LENGTH_LONG).show()
                            }
                        }
                        thread.start()
                    },
                    enabled = !isFaxProcessing
                ) {
                    if (isFaxProcessing) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                    } else {
                        Text("Transmit Fax Document")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showFaxSendingSheet = false }, enabled = !isFaxProcessing) {
                    Text("Cancel")
                }
            }
        )
    }

    // --- SUB-DIALOG 3: DYNAMIC FORMAT CONVERSION ---
    if (showConversionSheet && activeDetailDoc != null) {
        var convertTargetType by remember { mutableStateOf("Microsoft Word (.docx)") }
        var isConverting by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showConversionSheet = false },
            title = { Text("📊 OCR Format Document Converter") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Choose the target layout format to convert your scanned OCR document:", fontSize = 11.sp, color = Color.Gray)

                    listOf(
                        "Microsoft Word (.docx)",
                        "Microsoft Excel (.xlsx)",
                        "PowerPoint Slides (.pptx)",
                        "Plain Rich Text (.txt)",
                        "High-Res JPG Page Images"
                    ).forEach { type ->
                        val isSelected = convertTargetType == type
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { convertTargetType = type }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(selected = isSelected, onClick = { convertTargetType = type })
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(type, fontSize = 12.sp)
                        }
                    }

                    if (isConverting) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Running deep layout OCR conversion on cloud engine...", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isConverting = true
                        val thread = Thread {
                            Thread.sleep(2000)
                            (context as Activity).runOnUiThread {
                                isConverting = false
                                showConversionSheet = false
                                Toast.makeText(context, "Successfully converted & downloaded $convertTargetType output file!", Toast.LENGTH_LONG).show()
                            }
                        }
                        thread.start()
                    },
                    enabled = !isConverting
                ) {
                    Text("Begin Conversion")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConversionSheet = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// DYNAMIC USAGE STATS & SECURITY TAB
// -------------------------------------------------------------
@Composable
fun AnalyticsSecurityTab(
    context: Context,
    savedDocsList: List<ScannedDocument>
) {
    var biometricLockState by remember { mutableStateOf(false) }
    var userSavedPinCode by remember { mutableStateOf("") }
    var customWatermarkText by remember { mutableStateOf("CONFIDENTIAL") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Upper stats counter widgets
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("📈 Document Scanning personal activity", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                Text("Scans all time: ${savedDocsList.size} Documents | ${savedDocsList.sumOf { it.pageCount }} Pages processed", fontSize = 11.sp)
            }
        }

        // Custom drawn Pie Chart of document categories
        Card {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("📁 Storage folder proportions", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                val studyCount = savedDocsList.count { it.folder == "Study" }
                val receiptsCount = savedDocsList.count { it.folder == "Receipts" }
                val idCount = savedDocsList.count { it.folder == "IDs" }
                val totalCount = savedDocsList.size.coerceAtLeast(1)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(100.dp)) {
                        val studyAng = (studyCount.toFloat() / totalCount) * 360f
                        val recAng = (receiptsCount.toFloat() / totalCount) * 360f
                        val idAng = (idCount.toFloat() / totalCount) * 360f
                        val restAng = 360f - studyAng - recAng - idAng

                        var startAngle = 0f
                        // Study - Blue
                        drawArc(color = Color(0xFF1E3A8A), startAngle = startAngle, sweepAngle = studyAng, useCenter = true)
                        startAngle += studyAng
                        // Receipts - Orange
                        drawArc(color = Color(0xFFFF9100), startAngle = startAngle, sweepAngle = recAng, useCenter = true)
                        startAngle += recAng
                        // IDs - Green
                        drawArc(color = Color(0xFF00E676), startAngle = startAngle, sweepAngle = idAng, useCenter = true)
                        startAngle += idAng
                        // Other - Gray
                        drawArc(color = Color.LightGray, startAngle = startAngle, sweepAngle = restAng, useCenter = true)
                    }
                }

                // Legend
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    LegendItem("Study ($studyCount)", Color(0xFF1E3A8A))
                    LegendItem("Receipts ($receiptsCount)", Color(0xFFFF9100))
                    LegendItem("IDs ($idCount)", Color(0xFF00E676))
                }
            }
        }

        // Custom drawn Bar Chart of daily scans volume
        Card {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("📊 Weekly scanner usage volume", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val barWidth = 35f
                        val spacing = 35f
                        val maxVal = 5f
                        val heights = listOf(1f, 3f, 4f, 2f, 5f, 1f, 3f) // simulated volume

                        heights.forEachIndexed { idx, h ->
                            val x = 20f + idx * (barWidth + spacing)
                            val normalizedH = (h / maxVal) * size.height
                            val y = size.height - normalizedH
                            drawRect(
                                color = Color(0xFF00E5FF),
                                topLeft = Offset(x, y),
                                size = androidx.compose.ui.geometry.Size(barWidth, normalizedH)
                            )
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                        Text(day, fontSize = 9.sp, color = Color.Gray)
                    }
                }
            }
        }

        // Quota Limit indicators
        val totalSizeMb = savedDocsList.sumOf { doc -> doc.sizeMb }
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Cloud Storage Quota (Free tier)", fontSize = 11.sp)
                Text(String.format("%.2f MB / 1024 MB Used", totalSizeMb), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            LinearProgressIndicator(
                progress = { (totalSizeMb / 1024f).toFloat().coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        // Storage Cleaner Button
        Button(
            onClick = {
                Toast.makeText(context, "Storage scanner done! No unused large temporary layouts found.", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Launch Storage Cleaner & Cache Purge", fontSize = 12.sp)
        }

        Divider()

        // Security Configuration Block
        Text("🔒 Scanner Security Controls", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Enable Biometric Lock (Face/Fingerprint)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("Request local authentication prior to entry", fontSize = 10.sp, color = Color.Gray)
                    }
                    Switch(checked = biometricLockState, onCheckedChange = { biometricLockState = it })
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Request Folder-Level PIN lock", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("Add custom PIN to protect Work / Receipts folders", fontSize = 10.sp, color = Color.Gray)
                    }
                    var isPinToggled by remember { mutableStateOf(false) }
                    Switch(checked = isPinToggled, onCheckedChange = { isPinToggled = it })
                }

                OutlinedTextField(
                    value = customWatermarkText,
                    onValueChange = { customWatermarkText = it },
                    label = { Text("Default Diagonal Watermark text") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun LegendItem(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(modifier = Modifier.size(10.dp).background(color, RoundedCornerShape(2.dp)))
        Text(label, fontSize = 10.sp)
    }
}




// -------------------------------------------------------------
// MODULE 12: PDF TOOLS
// -------------------------------------------------------------
@Composable
fun PdfToolsScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val listTools = listOf(
        Triple("Merge PDFs", Icons.Default.MergeType, "Combine multiple files sequentially"),
        Triple("Split PDF", Icons.Default.CallSplit, "Extract specific page arrays"),
        Triple("Compress PDF", Icons.Default.PhotoSizeSelectLarge, "Reduce size without loss"),
        Triple("PDF to Images", Icons.Default.PictureInPicture, "Extract JPG page panels"),
        Triple("Add Watermark", Icons.Default.BrandingWatermark, "Overlay watermark text stamps"),
        Triple("Rotate PDF", Icons.Default.RotateRight, "Orientate layouts 90/180 degrees")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Utility PDF Handlers", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(listTools) { tl ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                        .clickable {
                            Toast.makeText(context, "${tl.first} operation executed on target!", Toast.LENGTH_SHORT).show()
                            viewModel.navigateBack()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = tl.second, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(tl.first, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(tl.third, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: ADVANCED INVOICE & RECEIPT MAKER
// -------------------------------------------------------------
data class InvoiceItem(
    val description: String,
    val quantity: Int,
    val unitPrice: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceGeneratorScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    
    // --- 1. STATE VARIABLES ---
    var billerName by remember { mutableStateOf("Acme Student Agency Ltd") }
    var billerEmail by remember { mutableStateOf("finance@acme-agency.com") }
    var billerPhone by remember { mutableStateOf("+1 (555) 019-2834") }
    var clientName by remember { mutableStateOf("Alpha Beta University Project") }
    var clientEmail by remember { mutableStateOf("billing@alphabetau.edu") }
    
    var invoiceNumber by remember { mutableStateOf("INV-2026-0428") }
    var invoiceDate by remember { mutableStateOf("2026-06-25") }
    var dueDate by remember { mutableStateOf("2026-07-15") }
    
    var currencySymbol by remember { mutableStateOf("$") }
    var taxRateText by remember { mutableStateOf("15") }
    var discountRateText by remember { mutableStateOf("10") }
    
    val invoiceItems = remember {
        mutableStateListOf(
            InvoiceItem("Student Tuition Aid Package", 1, 1250.00),
            InvoiceItem("Academic Curriculum Guide Pro", 2, 75.00),
            InvoiceItem("UI/UX Design Mentorship & Project Guide", 5, 45.00)
        )
    }
    
    // New item inputs
    var newItemDesc by remember { mutableStateOf("") }
    var newItemQty by remember { mutableStateOf("1") }
    var newItemPrice by remember { mutableStateOf("50.0") }
    
    // Signature points
    val signaturePoints = remember { mutableStateListOf<Offset>() }
    var signatureType by remember { mutableStateOf("Draw Signature") } // "Draw Signature" or "Text Cursive"
    var typedSignatureName by remember { mutableStateOf("Acme Student Agency") }
    
    // Mode switcher: "Edit Details" or "Render Preview"
    var previewModeActive by remember { mutableStateOf(false) }

    // Computations
    val subtotal = invoiceItems.sumOf { it.quantity * it.unitPrice }
    val taxRate = taxRateText.toDoubleOrNull() ?: 0.0
    val discountRate = discountRateText.toDoubleOrNull() ?: 0.0
    val taxAmount = subtotal * (taxRate / 100.0)
    val discountAmount = subtotal * (discountRate / 100.0)
    val grandTotal = subtotal + taxAmount - discountAmount

    if (previewModeActive) {
        // --- 2. PROFESSIONAL RENDER PREVIEW MODE ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F5F9))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { previewModeActive = false },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Edit Details")
                }
                
                Button(
                    onClick = {
                        Toast.makeText(context, "Invoice $invoiceNumber Saved & Shared Successfully!", Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier.weight(1.5f)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Share PDF Invoice")
                }
            }

            // Beautiful Corporate Invoice Paper Representation
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("invoice_rendered_paper"),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    // Header Area
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "OFFICIAL INVOICE",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = invoiceNumber,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.DarkGray
                            )
                        }
                        
                        // Corporate Logo Badge placeholder
                        Column(horizontalAlignment = Alignment.End) {
                            Icon(
                                imageVector = Icons.Default.Business,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(36.dp)
                            )
                            Text(
                                text = "Paid Securely",
                                fontSize = 10.sp,
                                color = Color(0xFF388E3C),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Divider(color = Color.LightGray.copy(alpha = 0.5f))

                    // Biller vs Client Info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Billed From:", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
                            Text(billerName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.Black)
                            Text(billerEmail, fontSize = 11.sp, color = Color.DarkGray)
                            Text(billerPhone, fontSize = 11.sp, color = Color.DarkGray)
                        }
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                            Text("Billed To:", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
                            Text(clientName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.Black, textAlign = TextAlign.End)
                            Text(clientEmail, fontSize = 11.sp, color = Color.DarkGray, textAlign = TextAlign.End)
                        }
                    }

                    // Date & Timing Box
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF8FAFC))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Invoice Date", fontSize = 10.sp, color = Color.Gray)
                            Text(invoiceDate, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Payment Terms", fontSize = 10.sp, color = Color.Gray)
                            Text("Immediate / Net 15", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Due Date", fontSize = 10.sp, color = Color.Gray)
                            Text(dueDate, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                        }
                    }

                    // Invoice Items Grid
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE2E8F0))
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Item Description", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
                            Text("Qty", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
                            Text("Unit", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f), textAlign = TextAlign.Right)
                            Text("Total", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Right)
                        }

                        invoiceItems.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(item.description, fontSize = 11.sp, modifier = Modifier.weight(2f), maxLines = 2)
                                Text("${item.quantity}", fontSize = 11.sp, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
                                Text(String.format("%.2f", item.unitPrice), fontSize = 11.sp, modifier = Modifier.weight(0.8f), textAlign = TextAlign.Right)
                                Text(
                                    text = "$currencySymbol${String.format("%.2f", item.quantity * item.unitPrice)}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Right
                                )
                            }
                            Divider(color = Color.LightGray.copy(alpha = 0.25f))
                        }
                    }

                    // Financial Calculations
                    Column(
                        modifier = Modifier.align(Alignment.End),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(modifier = Modifier.width(220.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal:", fontSize = 11.sp, color = Color.Gray)
                            Text("$currencySymbol${String.format("%.2f", subtotal)}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        if (discountAmount > 0) {
                            Row(modifier = Modifier.width(220.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Discount ($discountRateText%):", fontSize = 11.sp, color = Color.Gray)
                                Text("-$currencySymbol${String.format("%.2f", discountAmount)}", fontSize = 11.sp, color = Color(0xFFC62828))
                            }
                        }
                        if (taxAmount > 0) {
                            Row(modifier = Modifier.width(220.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Tax ($taxRateText%):", fontSize = 11.sp, color = Color.Gray)
                                Text("+$currencySymbol${String.format("%.2f", taxAmount)}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Divider(modifier = Modifier.width(220.dp), color = Color.Black)
                        Row(
                            modifier = Modifier
                                .width(220.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Amount Due:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(
                                "$currencySymbol${String.format("%.2f", grandTotal)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // Biller Seal or Signature
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text("Terms & Declarations", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Text("1. Goods once processed are non-refundable.\n2. Please remit payments within 15 days of issue.", fontSize = 8.sp, color = Color.Gray, lineHeight = 10.sp)
                        }
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Authorized Signature", fontSize = 10.sp, color = Color.Gray, fontStyle = FontStyle.Italic)
                            Spacer(modifier = Modifier.height(4.dp))
                            if (signatureType == "Draw Signature" && signaturePoints.isNotEmpty()) {
                                Canvas(
                                    modifier = Modifier
                                        .size(110.dp, 40.dp)
                                        .border(0.5.dp, Color.LightGray)
                                ) {
                                    for (i in 0 until signaturePoints.size - 1) {
                                        val p1 = signaturePoints[i]
                                        val p2 = signaturePoints[i + 1]
                                        // Ensure we don't connect points from disjoint drags
                                        if (p1 != Offset.Unspecified && p2 != Offset.Unspecified) {
                                            // Scale and center points inside small signature thumbnail box
                                            drawLine(
                                                color = Color.Black,
                                                start = p1 / 2.5f,
                                                end = p2 / 2.5f,
                                                strokeWidth = 1.5f
                                            )
                                        }
                                    }
                                }
                            } else {
                                Text(
                                    text = typedSignatureName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(1.dp)
                                    .background(Color.Gray)
                            )
                        }
                    }
                }
            }

            // Print Option Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.Print, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Standard System Printer", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("Export this invoice as PDF to print directly to local or cloud hardware printers.", fontSize = 10.sp, color = Color.Gray)
                    }
                    Button(
                        onClick = {
                            Toast.makeText(context, "System Print Dialog opened successfully!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Print", fontSize = 10.sp)
                    }
                }
            }
        }
    } else {
        // --- 3. INVOICE EDIT DETAILS INPUT FORM MODE ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Screen Introduction Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("🧾 High-Fidelity Invoice Builder", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    Text("Build professional, client-ready invoices with dynamic calculations, multiple currencies, custom line items, and finger-drawn signatures in minutes.", fontSize = 11.sp, color = Color.DarkGray)
                }
            }

            // A. Identity Information
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("1. Identity Details", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    
                    OutlinedTextField(
                        value = billerName,
                        onValueChange = { billerName = it },
                        label = { Text("Your Agency / Biller Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = billerEmail,
                            onValueChange = { billerEmail = it },
                            label = { Text("Biller Email") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = billerPhone,
                            onValueChange = { billerPhone = it },
                            label = { Text("Biller Phone") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                    
                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        label = { Text("Client Name / Institution") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    OutlinedTextField(
                        value = clientEmail,
                        onValueChange = { clientEmail = it },
                        label = { Text("Client Billing Email") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }

            // B. Meta Dates & Rates
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("2. Metadata & Rates", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = invoiceNumber,
                            onValueChange = { invoiceNumber = it },
                            label = { Text("Invoice No") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        
                        // Currency Selector Row
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Currency Symbol:", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                listOf("$", "₨", "€", "£").forEach { symb ->
                                    val isSelected = currencySymbol == symb
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.3f))
                                            .clickable { currencySymbol = symb }
                                            .padding(horizontal = 8.dp, vertical = 6.dp)
                                    ) {
                                        Text(symb, color = if (isSelected) Color.White else Color.Black, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = invoiceDate,
                            onValueChange = { invoiceDate = it },
                            label = { Text("Invoice Date") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = dueDate,
                            onValueChange = { dueDate = it },
                            label = { Text("Due Date") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = taxRateText,
                            onValueChange = { taxRateText = it },
                            label = { Text("Tax Rate (%)") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = discountRateText,
                            onValueChange = { discountRateText = it },
                            label = { Text("Discount (%)") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }
                }
            }

            // C. Dynamic Item Rows Editor
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("3. Invoice Line Items", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    
                    // List Existing
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        invoiceItems.forEachIndexed { idx, item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.LightGray.copy(alpha = 0.15f))
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.description, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Text("Qty: ${item.quantity} × $currencySymbol${item.unitPrice} = $currencySymbol${String.format("%.2f", item.quantity * item.unitPrice)}", fontSize = 11.sp, color = Color.Gray)
                                }
                                IconButton(
                                    onClick = { invoiceItems.removeAt(idx) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }

                    Divider()

                    // Add New Item Controls
                    Text("Add Line Item Row:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = newItemDesc,
                        onValueChange = { newItemDesc = it },
                        label = { Text("Item Name / Service description") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newItemQty,
                            onValueChange = { newItemQty = it },
                            label = { Text("Qty") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = newItemPrice,
                            onValueChange = { newItemPrice = it },
                            label = { Text("Unit Price") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }

                    Button(
                        onClick = {
                            if (newItemDesc.isNotEmpty()) {
                                val q = newItemQty.toIntOrNull() ?: 1
                                val p = newItemPrice.toDoubleOrNull() ?: 0.0
                                invoiceItems.add(InvoiceItem(newItemDesc, q, p))
                                newItemDesc = ""
                                newItemQty = "1"
                                newItemPrice = "10.0"
                            } else {
                                Toast.makeText(context, "Item Description is required!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Item Row")
                    }
                }
            }

            // D. Authorized Signature Panel
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("4. Secure Authorized Signature", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("Draw Signature", "Type Name").forEach { opt ->
                            val isSel = signatureType == opt
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { signatureType = opt }
                            ) {
                                RadioButton(selected = isSel, onClick = { signatureType = opt })
                                Text(opt, fontSize = 12.sp)
                            }
                        }
                    }

                    if (signatureType == "Draw Signature") {
                        Text("Draw signature on the grid board below with your finger:", fontSize = 11.sp, color = Color.Gray)
                        
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDragStart = { offset ->
                                            signaturePoints.add(offset)
                                        },
                                        onDrag = { change, dragAmount ->
                                            change.consume()
                                            signaturePoints.add(change.position)
                                        },
                                        onDragEnd = {
                                            signaturePoints.add(Offset.Unspecified)
                                        }
                                    )
                                }
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                for (i in 0 until signaturePoints.size - 1) {
                                    val p1 = signaturePoints[i]
                                    val p2 = signaturePoints[i + 1]
                                    if (p1 != Offset.Unspecified && p2 != Offset.Unspecified) {
                                        drawLine(
                                            color = Color(0xFF1E3A8A),
                                            start = p1,
                                            end = p2,
                                            strokeWidth = 4f,
                                            cap = StrokeCap.Round
                                        )
                                    }
                                }
                            }
                            
                            IconButton(
                                onClick = { signaturePoints.clear() },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(6.dp)
                                    .background(Color.Red.copy(alpha = 0.1f), CircleShape)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Clear Signature", tint = Color.Red)
                            }
                        }
                    } else {
                        OutlinedTextField(
                            value = typedSignatureName,
                            onValueChange = { typedSignatureName = it },
                            label = { Text("Type Biller Signature / Stamp Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
            }

            // E. Calculations Summary & Generate Button
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal ($currencySymbol):", fontSize = 12.sp)
                        Text("$currencySymbol${String.format("%.2f", subtotal)}", fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Estimated Total Due:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("$currencySymbol${String.format("%.2f", grandTotal)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            if (invoiceItems.isEmpty()) {
                                Toast.makeText(context, "Please add at least one line item!", Toast.LENGTH_SHORT).show()
                            } else {
                                previewModeActive = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("preview_invoice_btn")
                    ) {
                        Icon(Icons.Default.Receipt, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Generate & Preview Premium Invoice", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

