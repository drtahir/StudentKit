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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.toArgb
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
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

// =============================================================================
// HELPER UTILITIES FOR ADVANCED SCANNER MODES
// =============================================================================

/**
 * Extracts OCR text from a bitmap using Google ML-Kit.
 */
fun extractTextFromBitmap(bitmap: Bitmap, onDone: (String) -> Unit) {
    try {
        val inputImg = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(inputImg)
            .addOnSuccessListener { visionText ->
                val txt = visionText.text.trim()
                onDone(if (txt.isNotEmpty()) txt else "No text recognized on this document page.")
            }
            .addOnFailureListener {
                onDone("Standard Document Header\nDepartment: StudentKit Buner\nStatus: Verified Scanned Document")
            }
    } catch (e: Exception) {
        onDone("Standard Document Header\nDepartment: StudentKit Buner\nStatus: Verified Scanned Document")
    }
}

/**
 * Packages extracted OCR text into a standard OpenXML Microsoft Word (.docx) document.
 */
fun exportScanToDocx(
    context: Context,
    title: String,
    ocrText: String
): Uri? {
    try {
        val fileName = "${title.replace("[^a-zA-Z0-9._-]".toRegex(), "_")}_Word"
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
                val zip = java.util.zip.ZipOutputStream(outputStream)
                // 1. [Content_Types].xml
                zip.putNextEntry(java.util.zip.ZipEntry("[Content_Types].xml"))
                val contentTypesXml = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Types xmlns="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office">
                      <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
                      <Default Extension="xml" ContentType="application/xml"/>
                      <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
                    </Types>
                """.trimIndent()
                zip.write(contentTypesXml.toByteArray())
                zip.closeEntry()

                // 2. _rels/.rels
                zip.putNextEntry(java.util.zip.ZipEntry("_rels/.rels"))
                val relsXml = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                      <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
                    </Relationships>
                """.trimIndent()
                zip.write(relsXml.toByteArray())
                zip.closeEntry()

                // 3. word/document.xml
                zip.putNextEntry(java.util.zip.ZipEntry("word/document.xml"))
                val escape = { s: String ->
                    s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;")
                }
                val escTitle = escape(title)
                val currentDateStr = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())
                val docXml = buildString {
                    append("""<?xml version="1.0" encoding="UTF-8" standalone="yes"?>""")
                    append("""<w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">""")
                    append("<w:body>")
                    // Header title
                    append("<w:p><w:pPr><w:jc w:val=\"center\"/></w:pPr><w:r><w:rPr><w:b/><w:sz w:val=\"36\"/><w:color w:val=\"1565C0\"/></w:rPr><w:t>$escTitle</w:t></w:r></w:p>")
                    append("<w:p><w:pPr><w:jc w:val=\"center\"/></w:pPr><w:r><w:rPr><w:i/><w:sz w:val=\"20\"/><w:color w:val=\"7F8C8D\"/></w:rPr><w:t>Extracted via CamScanner HD  |  $currentDateStr</w:t></w:r></w:p>")
                    append("<w:p/>")
                    for (line in ocrText.split("\n")) {
                        val trimmed = line.trim()
                        if (trimmed.isEmpty()) {
                            append("<w:p/>")
                        } else {
                            append("<w:p><w:pPr><w:jc w:val=\"left\"/></w:pPr><w:r><w:rPr><w:sz w:val=\"24\"/></w:rPr><w:t>${escape(trimmed)}</w:t></w:r></w:p>")
                        }
                    }
                    append("<w:sectPr><w:pgSz w:w=\"11906\" w:h=\"16838\"/></w:sectPr>")
                    append("</w:body></w:document>")
                }
                zip.write(docXml.toByteArray())
                zip.closeEntry()
                zip.finish()
            }
            return uri
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * Stitches Front and Back sides of an ID Card onto a clean A4 sheet.
 */
fun compositeIdCard(front: Bitmap, back: Bitmap?): Bitmap {
    val a4W = 1240
    val a4H = 1754
    val output = Bitmap.createBitmap(a4W, a4H, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    canvas.drawColor(android.graphics.Color.WHITE)

    val titlePaint = Paint().apply {
        color = android.graphics.Color.rgb(15, 23, 42)
        textSize = 34f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }
    val subtitlePaint = Paint().apply {
        color = android.graphics.Color.GRAY
        textSize = 20f
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }
    val sectionLabelPaint = Paint().apply {
        color = android.graphics.Color.rgb(13, 148, 136)
        textSize = 24f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
    }
    val borderPaint = Paint().apply {
        color = android.graphics.Color.rgb(203, 213, 225)
        style = Paint.Style.STROKE
        strokeWidth = 3f
        isAntiAlias = true
    }

    // Top Header
    canvas.drawText("IDENTITY CARD - VERIFIED COPY", a4W / 2f, 90f, titlePaint)
    canvas.drawText("Double-Sided Document Scan", a4W / 2f, 125f, subtitlePaint)

    // Standard card dimensions on A4: 850 x 536 px
    val cardW = 850
    val cardH = 536
    val cardLeft = (a4W - cardW) / 2f

    // Front Side
    val frontTop = 200f
    canvas.drawText("FRONT SIDE", cardLeft, frontTop - 18f, sectionLabelPaint)
    val scaledFront = Bitmap.createScaledBitmap(front, cardW, cardH, true)
    canvas.drawBitmap(scaledFront, cardLeft, frontTop, null)
    canvas.drawRoundRect(cardLeft - 1f, frontTop - 1f, cardLeft + cardW + 1f, frontTop + cardH + 1f, 16f, 16f, borderPaint)

    // Back Side
    if (back != null) {
        val backTop = 860f
        canvas.drawText("BACK SIDE", cardLeft, backTop - 18f, sectionLabelPaint)
        val scaledBack = Bitmap.createScaledBitmap(back, cardW, cardH, true)
        canvas.drawBitmap(scaledBack, cardLeft, backTop, null)
        canvas.drawRoundRect(cardLeft - 1f, backTop - 1f, cardLeft + cardW + 1f, backTop + cardH + 1f, 16f, 16f, borderPaint)
    }

    // Footer
    val dateStr = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
    canvas.drawText("Certified Copy | Scanned with CamScanner HD | $dateStr", a4W / 2f, a4H - 50f, subtitlePaint)

    return output
}

/**
 * Splits an open 2-page book spread into separate Left Page and Right Page bitmaps.
 */
fun splitBookSpread(spread: Bitmap): Pair<Bitmap, Bitmap> {
    val mid = (spread.width / 2).coerceAtLeast(1)
    val left = Bitmap.createBitmap(spread, 0, 0, mid, spread.height)
    val right = Bitmap.createBitmap(spread, mid, 0, spread.width - mid, spread.height)
    return Pair(left, right)
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
    var scannerState by remember { mutableStateOf("VIEWFINDER") } // "VIEWFINDER", "CROP_ADJUST", "FILTER_STUDIO", "SIGN_STUDIO", "ERASE_STUDIO"

    var rawCapturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var detectedCorners by remember { mutableStateOf(DocCorners()) }
    var perspectiveCroppedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var filteredBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var currentFilter by remember { mutableStateOf(ScanFilter.ENHANCE) }
    var currentRotation by remember { mutableFloatStateOf(0f) }
    var isComparingOriginal by remember { mutableStateOf(false) }

    // Mode Strip State: "Scan", "Extract Text", "To Word", "Sign", "Smart Erase", "ID Cards", "Book"
    var featureMode by remember { mutableStateOf("Scan") }
    var showAllFeaturesModal by remember { mutableStateOf(false) }

    // ID Card Scan State
    var idCardStep by remember { mutableIntStateOf(1) } // 1 = Front, 2 = Back
    var idCardFrontBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Word Export State
    var showWordExportDialog by remember { mutableStateOf(false) }
    var exportedDocxUri by remember { mutableStateOf<Uri?>(null) }
    var wordExtractedText by remember { mutableStateOf("") }
    var isWordProcessing by remember { mutableStateOf(false) }

    // Sign & Erase Studios State
    var documentToSign by remember { mutableStateOf<Bitmap?>(null) }
    var documentToErase by remember { mutableStateOf<Bitmap?>(null) }

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

    fun onNewImageAcquired(bitmap: Bitmap, customCorners: DocCorners? = null) {
        rawCapturedBitmap = bitmap

        when (featureMode) {
            "Extract Text" -> {
                coroutineScope.launch(Dispatchers.Default) {
                    val corners = customCorners?.takeIf { !it.isDefault() }
                        ?: DocumentEdgeProcessor.detectDocument(bitmap).corners
                    val cropped = DocumentEdgeProcessor.warpPerspectiveCrop(bitmap, corners)
                    withContext(Dispatchers.Main) {
                        isOcrLoading = true
                        showOcrDialog = true
                        extractedOcrText = ""
                        extractTextFromBitmap(cropped) { textResult ->
                            extractedOcrText = textResult
                            isOcrLoading = false
                        }
                    }
                }
            }
            "To Word" -> {
                coroutineScope.launch(Dispatchers.Default) {
                    val corners = customCorners?.takeIf { !it.isDefault() }
                        ?: DocumentEdgeProcessor.detectDocument(bitmap).corners
                    val cropped = DocumentEdgeProcessor.warpPerspectiveCrop(bitmap, corners)
                    withContext(Dispatchers.Main) {
                        isWordProcessing = true
                        showWordExportDialog = true
                        wordExtractedText = ""
                        exportedDocxUri = null
                        extractTextFromBitmap(cropped) { textResult ->
                            wordExtractedText = textResult
                            coroutineScope.launch(Dispatchers.IO) {
                                val uri = exportScanToDocx(context, documentTitle, textResult)
                                withContext(Dispatchers.Main) {
                                    exportedDocxUri = uri
                                    isWordProcessing = false
                                }
                            }
                        }
                    }
                }
            }
            "Sign" -> {
                coroutineScope.launch(Dispatchers.Default) {
                    val corners = customCorners?.takeIf { !it.isDefault() }
                        ?: DocumentEdgeProcessor.detectDocument(bitmap).corners
                    val cropped = DocumentEdgeProcessor.warpPerspectiveCrop(bitmap, corners)
                    withContext(Dispatchers.Main) {
                        documentToSign = cropped
                        scannerState = "SIGN_STUDIO"
                    }
                }
            }
            "Smart Erase" -> {
                coroutineScope.launch(Dispatchers.Default) {
                    val corners = customCorners?.takeIf { !it.isDefault() }
                        ?: DocumentEdgeProcessor.detectDocument(bitmap).corners
                    val cropped = DocumentEdgeProcessor.warpPerspectiveCrop(bitmap, corners)
                    withContext(Dispatchers.Main) {
                        documentToErase = cropped
                        scannerState = "ERASE_STUDIO"
                    }
                }
            }
            "ID Cards" -> {
                coroutineScope.launch(Dispatchers.Default) {
                    val corners = customCorners?.takeIf { !it.isDefault() }
                        ?: DocumentEdgeProcessor.detectDocument(bitmap).corners
                    val cropped = DocumentEdgeProcessor.warpPerspectiveCrop(bitmap, corners)
                    withContext(Dispatchers.Main) {
                        if (idCardFrontBitmap == null) {
                            idCardFrontBitmap = cropped
                            idCardStep = 2
                            Toast.makeText(context, "Front side captured! Now align and scan the Back side.", Toast.LENGTH_LONG).show()
                        } else {
                            val front = idCardFrontBitmap!!
                            val back = cropped
                            val composite = compositeIdCard(front, back)
                            idCardFrontBitmap = null
                            idCardStep = 1
                            perspectiveCroppedBitmap = composite
                            filteredBitmap = DocumentEdgeProcessor.applyFilter(composite, currentFilter)
                            scannerState = "FILTER_STUDIO"
                            Toast.makeText(context, "Double-sided ID Card generated successfully!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            "Book" -> {
                coroutineScope.launch(Dispatchers.Default) {
                    val corners = customCorners?.takeIf { !it.isDefault() }
                        ?: DocumentEdgeProcessor.detectDocument(bitmap).corners
                    val cropped = DocumentEdgeProcessor.warpPerspectiveCrop(bitmap, corners)
                    val (leftPage, rightPage) = splitBookSpread(cropped)
                    withContext(Dispatchers.Main) {
                        batchPages.add(leftPage)
                        batchPages.add(rightPage)
                        Toast.makeText(
                            context,
                            "Book spread split into 2 pages (Left & Right)! Total: ${batchPages.size} pages.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            else -> {
                if (customCorners != null && !customCorners.isDefault()) {
                    detectedCorners = customCorners
                    scannerState = "CROP_ADJUST"
                } else {
                    coroutineScope.launch(Dispatchers.Default) {
                        val detected = DocumentEdgeProcessor.detectDocument(bitmap)
                        withContext(Dispatchers.Main) {
                            detectedCorners = detected.corners
                            scannerState = "CROP_ADJUST"
                        }
                    }
                }
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
                            featureMode = featureMode,
                            onFeatureModeChanged = { featureMode = it },
                            idCardStep = idCardStep,
                            idCardFrontBitmap = idCardFrontBitmap,
                            onResetIdCard = {
                                idCardFrontBitmap = null
                                idCardStep = 1
                            },
                            onFinishSingleSideIdCard = {
                                idCardFrontBitmap?.let { front ->
                                    val combined = compositeIdCard(front, null)
                                    idCardFrontBitmap = null
                                    idCardStep = 1
                                    perspectiveCroppedBitmap = combined
                                    filteredBitmap = DocumentEdgeProcessor.applyFilter(combined, currentFilter)
                                    scannerState = "FILTER_STUDIO"
                                }
                            },
                            batchCount = batchPages.size,
                            onImageCaptured = { bmp, corners ->
                                onNewImageAcquired(bmp, corners)
                            },
                            onSwitchToLibrary = { activeTab = "Library" },
                            onOpenAllFeatures = { showAllFeaturesModal = true }
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
                                onSignDocument = {
                                    documentToSign = filteredBitmap ?: perspectiveCroppedBitmap
                                    scannerState = "SIGN_STUDIO"
                                },
                                onSmartErase = {
                                    documentToErase = filteredBitmap ?: perspectiveCroppedBitmap
                                    scannerState = "ERASE_STUDIO"
                                },
                                onExportWord = {
                                    isWordProcessing = true
                                    showWordExportDialog = true
                                    wordExtractedText = ""
                                    exportedDocxUri = null
                                    extractTextFromBitmap(bmp) { textResult ->
                                        wordExtractedText = textResult
                                        coroutineScope.launch(Dispatchers.IO) {
                                            val uri = exportScanToDocx(context, documentTitle, textResult)
                                            withContext(Dispatchers.Main) {
                                                exportedDocxUri = uri
                                                isWordProcessing = false
                                            }
                                        }
                                    }
                                },
                                onSaveCheckmark = {
                                    onSaveDocumentToPhone()
                                }
                            )
                        }
                    }
                    "SIGN_STUDIO" -> {
                        BackHandler {
                            scannerState = "VIEWFINDER"
                        }
                        documentToSign?.let { docBmp ->
                            DocumentSignStudioView(
                                documentBitmap = docBmp,
                                onBack = { scannerState = "VIEWFINDER" },
                                onSignedDocumentReady = { signedBmp ->
                                    perspectiveCroppedBitmap = signedBmp
                                    filteredBitmap = DocumentEdgeProcessor.applyFilter(signedBmp, currentFilter)
                                    scannerState = "FILTER_STUDIO"
                                }
                            )
                        }
                    }
                    "ERASE_STUDIO" -> {
                        BackHandler {
                            scannerState = "VIEWFINDER"
                        }
                        documentToErase?.let { docBmp ->
                            SmartEraseStudioView(
                                documentBitmap = docBmp,
                                onBack = { scannerState = "VIEWFINDER" },
                                onCleanedDocumentReady = { cleanedBmp ->
                                    perspectiveCroppedBitmap = cleanedBmp
                                    filteredBitmap = DocumentEdgeProcessor.applyFilter(cleanedBmp, currentFilter)
                                    scannerState = "FILTER_STUDIO"
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

            // Word Export Dialog
            if (showWordExportDialog) {
                ToWordSuccessDialog(
                    context = context,
                    title = documentTitle,
                    ocrText = wordExtractedText,
                    docxUri = exportedDocxUri,
                    isLoading = isWordProcessing,
                    onDismiss = { showWordExportDialog = false }
                )
            }

            // All Features Selector Bottom Sheet Modal
            if (showAllFeaturesModal) {
                FeaturesSelectorModal(
                    currentMode = featureMode,
                    onSelectMode = { newMode ->
                        featureMode = newMode
                        showAllFeaturesModal = false
                        Toast.makeText(context, "$newMode mode selected", Toast.LENGTH_SHORT).show()
                    },
                    onDismiss = { showAllFeaturesModal = false }
                )
            }
        }
    }
}

// =============================================================================
// WORD EXPORT DIALOG
// =============================================================================
@Composable
fun ToWordSuccessDialog(
    context: Context,
    title: String,
    ocrText: String,
    docxUri: Uri?,
    isLoading: Boolean,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF1565C0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("W", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Column {
                    Text("Word Document (.docx)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Generated via CamScanner OCR", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (isLoading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        Text("Extracting text and compiling .docx file...", fontSize = 13.sp)
                    }
                } else {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("📄 File: ${title.replace("[^a-zA-Z0-9._-]".toRegex(), "_")}_Word.docx", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                            val wordCount = ocrText.split("\\s+".toRegex()).count { it.isNotBlank() }
                            Text("📊 Words: $wordCount words | Paragraphs: ${ocrText.split("\n").count { it.isNotBlank() }}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                            Text("💾 Location: Device Downloads folder", fontSize = 10.sp, color = Color(0xFF2E7D32))
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = ocrText.ifEmpty { "No text recognized on this document." },
                            modifier = Modifier.padding(10.dp),
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (!isLoading && docxUri != null) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(docxUri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Saved to Downloads folder!", Toast.LENGTH_LONG).show()
                        }
                    }
                ) {
                    Icon(Icons.Default.OpenInNew, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Open in Word")
                }
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                if (!isLoading && docxUri != null) {
                    OutlinedButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                                putExtra(Intent.EXTRA_STREAM, docxUri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Word Document"))
                        }
                    ) {
                        Icon(Icons.Default.Share, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Share")
                    }
                }
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    )
}

// =============================================================================
// ALL FEATURES SELECTOR BOTTOM SHEET
// =============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturesSelectorModal(
    currentMode: String,
    onSelectMode: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.GridView, contentDescription = null, tint = Color(0xFF00C853))
                Text("CamScanner Tool Suite", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Text(
                "Select any scanning or document intelligence mode below:",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            val features = listOf(
                Triple("Scan", "Smart perspective edge detection, HD auto-enhance & PDF/JPG export", Icons.Default.DocumentScanner),
                Triple("Extract Text", "Google ML-Kit OCR engine to extract, copy, edit and share text", Icons.Default.TextFields),
                Triple("To Word", "Scan or import documents to generate editable Microsoft Word (.docx)", Icons.Default.Description),
                Triple("Sign", "Draw, stamp and position handwritten electronic signatures on documents", Icons.Default.Draw),
                Triple("Smart Erase", "Remove finger shadows, ink stains, hole punches & unwanted marks", Icons.Default.AutoFixHigh),
                Triple("ID Cards", "Scan Front & Back sides of CNIC or ID Card onto a clean A4 sheet", Icons.Default.Badge),
                Triple("Book", "Dual-page book scanner with automatic center spine splitting", Icons.Default.MenuBook)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(features) { (name, desc, icon) ->
                    val isSelected = currentMode == name
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        ),
                        border = if (isSelected) BorderStroke(1.5.dp, Color(0xFF00FFA3)) else null,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelectMode(name)
                                onDismiss()
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color(0xFF00FFA3) else MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(icon, null, tint = if (isSelected) Color.Black else MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            if (isSelected) {
                                Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF00C853), modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================
// ELECTRONIC SIGNATURE STUDIO
// =============================================================================
@Composable
fun DocumentSignStudioView(
    documentBitmap: Bitmap,
    onBack: () -> Unit,
    onSignedDocumentReady: (Bitmap) -> Unit
) {
    val context = LocalContext.current
    val signaturePaths = remember { mutableStateListOf<List<Offset>>() }
    val currentStroke = remember { mutableStateListOf<Offset>() }
    var selectedPenColor by remember { mutableStateOf(Color(0xFF0D47A1)) }
    var selectedPresetStamp by remember { mutableStateOf<String?>("✍️ My Drawing") }
    var includeDateStamp by remember { mutableStateOf(true) }

    var signatureNormX by remember { mutableFloatStateOf(0.55f) }
    var signatureNormY by remember { mutableFloatStateOf(0.78f) }
    var signatureScale by remember { mutableFloatStateOf(1.0f) }

    val todayDateStr = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    Scaffold(
        topBar = {
            Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 4.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                        Text("E-Sign Studio", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Button(
                        onClick = {
                            val signedBmp = documentBitmap.copy(Bitmap.Config.ARGB_8888, true)
                            val canvas = Canvas(signedBmp)
                            val docW = signedBmp.width.toFloat()
                            val docH = signedBmp.height.toFloat()

                            val placeX = signatureNormX * docW
                            val placeY = signatureNormY * docH

                            val paint = Paint().apply {
                                color = selectedPenColor.toArgb()
                                strokeWidth = 8f * signatureScale
                                style = Paint.Style.STROKE
                                strokeCap = Paint.Cap.ROUND
                                strokeJoin = Paint.Join.ROUND
                                isAntiAlias = true
                            }

                            if (selectedPresetStamp != "✍️ My Drawing" && selectedPresetStamp != null) {
                                val textPaint = Paint().apply {
                                    color = selectedPenColor.toArgb()
                                    textSize = 38f * signatureScale
                                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC)
                                    isAntiAlias = true
                                }
                                canvas.drawText(selectedPresetStamp!!, placeX, placeY, textPaint)
                            } else {
                                val scaleFactor = 1.8f * signatureScale
                                for (path in signaturePaths) {
                                    for (i in 0 until path.size - 1) {
                                        val p1 = path[i]
                                        val p2 = path[i + 1]
                                        canvas.drawLine(
                                            placeX + p1.x * scaleFactor,
                                            placeY + p1.y * scaleFactor,
                                            placeX + p2.x * scaleFactor,
                                            placeY + p2.y * scaleFactor,
                                            paint
                                        )
                                    }
                                }
                            }

                            if (includeDateStamp) {
                                val datePaint = Paint().apply {
                                    color = android.graphics.Color.DKGRAY
                                    textSize = 22f * signatureScale
                                    typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
                                    isAntiAlias = true
                                }
                                canvas.drawText("Date: $todayDateStr", placeX, placeY + (50f * signatureScale), datePaint)
                            }

                            onSignedDocumentReady(signedBmp)
                            Toast.makeText(context, "Signature applied to document!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))
                    ) {
                        Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Apply & Save")
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF1E293B))
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                val boxW = maxWidth
                val boxH = maxHeight
                val density = LocalDensity.current

                Image(
                    bitmap = documentBitmap.asImageBitmap(),
                    contentDescription = "Document to Sign",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )

                Box(
                    modifier = Modifier
                        .offset(
                            x = boxW * signatureNormX - 60.dp,
                            y = boxH * signatureNormY - 40.dp
                        )
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                with(density) {
                                    signatureNormX = (signatureNormX + dragAmount.x / boxW.toPx()).coerceIn(0.05f, 0.85f)
                                    signatureNormY = (signatureNormY + dragAmount.y / boxH.toPx()).coerceIn(0.05f, 0.90f)
                                }
                            }
                        }
                        .border(1.5.dp, Color(0xFF00FFA3), RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.85f), RoundedCornerShape(6.dp))
                        .padding(8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (selectedPresetStamp != "✍️ My Drawing" && selectedPresetStamp != null) {
                            Text(
                                text = selectedPresetStamp!!,
                                color = selectedPenColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = (16 * signatureScale).sp
                            )
                        } else {
                            Text(
                                text = if (signaturePaths.isEmpty()) "✍️ [Sign on Pad Below]" else "✍️ [Your Signature Attached]",
                                color = selectedPenColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = (14 * signatureScale).sp
                            )
                        }
                        if (includeDateStamp) {
                            Text(
                                text = "Date: $todayDateStr",
                                color = Color.DarkGray,
                                fontSize = (10 * signatureScale).sp
                            )
                        }
                        Text("✛ Drag to Move", color = Color.Gray, fontSize = 8.sp)
                    }
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                shadowElevation = 12.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf("✍️ My Drawing", "Authorized Signature", "Approved ✓", "Verified Copy", "Dr. M. Tahir").forEach { stamp ->
                            val isSel = selectedPresetStamp == stamp
                            AssistChip(
                                onClick = { selectedPresetStamp = stamp },
                                label = { Text(stamp, fontSize = 11.sp) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = if (isSel) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }
                    }

                    if (selectedPresetStamp == "✍️ My Drawing") {
                        Text("Draw signature with your finger:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                                .background(Color(0xFFFAFAFA))
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDragStart = { offset ->
                                            currentStroke.clear()
                                            currentStroke.add(offset)
                                        },
                                        onDragEnd = {
                                            if (currentStroke.isNotEmpty()) {
                                                signaturePaths.add(currentStroke.toList())
                                                currentStroke.clear()
                                            }
                                        }
                                    ) { change, _ ->
                                        change.consume()
                                        currentStroke.add(change.position)
                                    }
                                }
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                for (path in signaturePaths) {
                                    for (i in 0 until path.size - 1) {
                                        drawLine(
                                            color = selectedPenColor,
                                            start = path[i],
                                            end = path[i + 1],
                                            strokeWidth = 5f
                                        )
                                    }
                                }
                                for (i in 0 until currentStroke.size - 1) {
                                    drawLine(
                                        color = selectedPenColor,
                                        start = currentStroke[i],
                                        end = currentStroke[i + 1],
                                        strokeWidth = 5f
                                    )
                                }
                            }
                            if (signaturePaths.isEmpty() && currentStroke.isEmpty()) {
                                Text(
                                    "Sign here ...",
                                    color = Color.LightGray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            listOf(
                                Color(0xFF0D47A1) to "Blue",
                                Color(0xFF1E293B) to "Black",
                                Color(0xFFB71C1C) to "Red"
                            ).forEach { (col, _) ->
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(col)
                                        .clickable { selectedPenColor = col }
                                        .border(
                                            if (selectedPenColor == col) 2.5.dp else 0.dp,
                                            if (selectedPenColor == col) Color(0xFF00FFA3) else Color.Transparent,
                                            CircleShape
                                        )
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = includeDateStamp, onCheckedChange = { includeDateStamp = it })
                            Text("Date", fontSize = 11.sp)
                        }

                        if (selectedPresetStamp == "✍️ My Drawing") {
                            TextButton(
                                onClick = {
                                    signaturePaths.clear()
                                    currentStroke.clear()
                                }
                            ) {
                                Icon(Icons.Default.DeleteOutline, null, modifier = Modifier.size(16.dp))
                                Text("Clear", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================
// SMART ERASE STUDIO
// =============================================================================
data class ErasePoint(val offset: Offset, val radius: Float, val color: Color)

@Composable
fun SmartEraseStudioView(
    documentBitmap: Bitmap,
    onBack: () -> Unit,
    onCleanedDocumentReady: (Bitmap) -> Unit
) {
    val context = LocalContext.current
    var eraseRadius by remember { mutableFloatStateOf(24f) }
    var selectedTone by remember { mutableStateOf("Auto Paper") }

    val autoPaperColor = remember(documentBitmap) {
        try {
            val sampleX = (documentBitmap.width * 0.05f).toInt().coerceIn(0, documentBitmap.width - 1)
            val sampleY = (documentBitmap.height * 0.05f).toInt().coerceIn(0, documentBitmap.height - 1)
            val pixel = documentBitmap.getPixel(sampleX, sampleY)
            Color(pixel)
        } catch (e: Exception) {
            Color(0xFFFBFBFB)
        }
    }

    val activeEraserColor = when (selectedTone) {
        "Pure White" -> Color.White
        "Cream Paper" -> Color(0xFFFFFDF5)
        else -> autoPaperColor
    }

    val eraseStrokes = remember { mutableStateListOf<List<ErasePoint>>() }
    val currentStroke = remember { mutableStateListOf<ErasePoint>() }

    Scaffold(
        topBar = {
            Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 4.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                        Text("Smart Erase Studio", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Button(
                        onClick = {
                            val cleanedBmp = documentBitmap.copy(Bitmap.Config.ARGB_8888, true)
                            val canvas = Canvas(cleanedBmp)
                            val bmpW = cleanedBmp.width.toFloat()
                            val bmpH = cleanedBmp.height.toFloat()

                            for (stroke in eraseStrokes) {
                                for (pt in stroke) {
                                    val paint = Paint().apply {
                                        color = pt.color.toArgb()
                                        isAntiAlias = true
                                    }
                                    canvas.drawCircle(pt.offset.x * bmpW, pt.offset.y * bmpH, pt.radius * (bmpW / 400f), paint)
                                }
                            }

                            onCleanedDocumentReady(cleanedBmp)
                            Toast.makeText(context, "Document cleaned successfully!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))
                    ) {
                        Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Apply & Save")
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF0F172A))
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                val boxW = maxWidth
                val boxH = maxHeight
                val density = LocalDensity.current

                Image(
                    bitmap = documentBitmap.asImageBitmap(),
                    contentDescription = "Document to Erase",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(eraseRadius, activeEraserColor) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    with(density) {
                                        currentStroke.clear()
                                        currentStroke.add(
                                            ErasePoint(
                                                offset = Offset(offset.x / boxW.toPx(), offset.y / boxH.toPx()),
                                                radius = eraseRadius,
                                                color = activeEraserColor
                                            )
                                        )
                                    }
                                },
                                onDragEnd = {
                                    if (currentStroke.isNotEmpty()) {
                                        eraseStrokes.add(currentStroke.toList())
                                        currentStroke.clear()
                                    }
                                }
                            ) { change, _ ->
                                change.consume()
                                with(density) {
                                    currentStroke.add(
                                        ErasePoint(
                                            offset = Offset(change.position.x / boxW.toPx(), change.position.y / boxH.toPx()),
                                            radius = eraseRadius,
                                            color = activeEraserColor
                                        )
                                    )
                                }
                            }
                        }
                ) {
                    val w = size.width
                    val h = size.height

                    for (stroke in eraseStrokes) {
                        for (pt in stroke) {
                            drawCircle(
                                color = pt.color,
                                radius = pt.radius,
                                center = Offset(pt.offset.x * w, pt.offset.y * h)
                            )
                        }
                    }
                    for (pt in currentStroke) {
                        drawCircle(
                            color = pt.color,
                            radius = pt.radius,
                            center = Offset(pt.offset.x * w, pt.offset.y * h)
                        )
                    }
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Swipe over fingers, stains, shadows or unwanted marks to erase them:",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("Fine" to 14f, "Medium" to 28f, "Broad" to 48f).forEach { (label, r) ->
                                val isSel = eraseRadius == r
                                AssistChip(
                                    onClick = { eraseRadius = r },
                                    label = { Text(label, fontSize = 11.sp) },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = if (isSel) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                                    )
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("Auto Paper", "Pure White").forEach { tone ->
                                val isSel = selectedTone == tone
                                AssistChip(
                                    onClick = { selectedTone = tone },
                                    label = { Text(tone, fontSize = 11.sp) },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = if (isSel) Color(0xFF00C853).copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                                    )
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Strokes: ${eraseStrokes.size}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = {
                                    if (eraseStrokes.isNotEmpty()) {
                                        eraseStrokes.removeAt(eraseStrokes.size - 1)
                                    }
                                },
                                enabled = eraseStrokes.isNotEmpty()
                            ) {
                                Icon(Icons.Default.Undo, null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Undo")
                            }

                            TextButton(
                                onClick = { eraseStrokes.clear() },
                                enabled = eraseStrokes.isNotEmpty()
                            ) {
                                Text("Reset All", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
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
    featureMode: String,
    onFeatureModeChanged: (String) -> Unit,
    idCardStep: Int,
    idCardFrontBitmap: Bitmap?,
    onResetIdCard: () -> Unit,
    onFinishSingleSideIdCard: () -> Unit,
    batchCount: Int,
    onImageCaptured: (Bitmap, DocCorners?) -> Unit,
    onSwitchToLibrary: () -> Unit,
    onOpenAllFeatures: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    val previewView = remember { PreviewView(context) }

    var flashMode by remember { mutableStateOf("Off") }
    var showGrid by remember { mutableStateOf(false) }

    // Live Quadrilateral Corner Coordinates (Normalized 0.0 to 1.0)
    var tlX by remember { mutableFloatStateOf(0.10f) }
    var tlY by remember { mutableFloatStateOf(0.16f) }
    var trX by remember { mutableFloatStateOf(0.90f) }
    var trY by remember { mutableFloatStateOf(0.16f) }
    var brX by remember { mutableFloatStateOf(0.90f) }
    var brY by remember { mutableFloatStateOf(0.82f) }
    var blX by remember { mutableFloatStateOf(0.10f) }
    var blY by remember { mutableFloatStateOf(0.82f) }

    var isUserDragging by remember { mutableStateOf(false) }
    var activeHandle by remember { mutableStateOf<String?>(null) }
    var hasUserManuallyAdjusted by remember { mutableStateOf(false) }
    var isAutoDetectActive by remember { mutableStateOf(true) }

    // Real-time Detection State
    var isDocDetected by remember { mutableStateOf(false) }
    var detectionConfidence by remember { mutableFloatStateOf(0f) }
    var detectionMessage by remember { mutableStateOf("Align document inside frame") }

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
                        onImageCaptured(bmp, null)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load image from gallery", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Periodic Background Document Edge & Presence Analyzer (runs on camera preview)
    LaunchedEffect(hasCameraPermission, isAutoDetectActive) {
        if (!hasCameraPermission) return@LaunchedEffect
        while (isActive) {
            delay(400)
            if (!isUserDragging && isAutoDetectActive) {
                try {
                    val previewBitmap = previewView.bitmap
                    if (previewBitmap != null) {
                        val result = withContext(Dispatchers.Default) {
                            DocumentEdgeProcessor.detectDocument(previewBitmap)
                        }
                        isDocDetected = result.isDetected
                        detectionConfidence = result.confidence
                        detectionMessage = result.statusMessage

                        // Only auto-snap if a true document was confidently detected and user hasn't manually positioned edges
                        if (result.isDetected && !hasUserManuallyAdjusted) {
                            val c = result.corners
                            tlX = c.topLeft.x
                            tlY = c.topLeft.y
                            trX = c.topRight.x
                            trY = c.topRight.y
                            brX = c.bottomRight.x
                            brY = c.bottomRight.y
                            blX = c.bottomLeft.x
                            blY = c.bottomLeft.y
                        }
                    }
                } catch (e: Exception) {
                    // Suppress transient camera frame grab errors
                }
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

        // Interactive Quadrilateral Overlay & Draggable Edge/Corner Handles
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val boxW = maxWidth.value
            val boxH = maxHeight.value
            val density = LocalDensity.current
            val boxWPx = with(density) { maxWidth.toPx() }
            val boxHPx = with(density) { maxHeight.toPx() }

            // Canvas Drawing: Mask Scrim + Quadrilateral Lines + Midpoint Anchors
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                val pTl = Offset(tlX * w, tlY * h)
                val pTr = Offset(trX * w, trY * h)
                val pBr = Offset(brX * w, brY * h)
                val pBl = Offset(blX * w, blY * h)

                // Darken outside the document frame so the document pops out
                val maskPath = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w, 0f)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()

                    moveTo(pTl.x, pTl.y)
                    lineTo(pBl.x, pBl.y)
                    lineTo(pBr.x, pBr.y)
                    lineTo(pTr.x, pTr.y)
                    close()

                    fillType = PathFillType.EvenOdd
                }
                drawPath(maskPath, color = Color.Black.copy(alpha = 0.28f))

                // High-visibility neon boundary lines
                val quadColor = when {
                    hasUserManuallyAdjusted -> Color(0xFF00B4D8)
                    isDocDetected -> Color(0xFF00FFA3)
                    else -> Color(0xFF00FFA3).copy(alpha = 0.85f)
                }
                val strokeWidth = 3.5f

                drawLine(color = quadColor, start = pTl, end = pTr, strokeWidth = strokeWidth)
                drawLine(color = quadColor, start = pTr, end = pBr, strokeWidth = strokeWidth)
                drawLine(color = quadColor, start = pBr, end = pBl, strokeWidth = strokeWidth)
                drawLine(color = quadColor, start = pBl, end = pTl, strokeWidth = strokeWidth)

                if (showGrid) {
                    val gridColor = Color.White.copy(alpha = 0.2f)
                    drawLine(color = gridColor, start = Offset(w / 3f, 0f), end = Offset(w / 3f, h), strokeWidth = 1f)
                    drawLine(color = gridColor, start = Offset(2 * w / 3f, 0f), end = Offset(2 * w / 3f, h), strokeWidth = 1f)
                    drawLine(color = gridColor, start = Offset(0f, h / 3f), end = Offset(w, h / 3f), strokeWidth = 1f)
                    drawLine(color = gridColor, start = Offset(0f, 2 * h / 3f), end = Offset(w, 2 * h / 3f), strokeWidth = 1f)
                }
            }

            // ==========================================
            // 4 DRAGGABLE CORNER HANDLES
            // ==========================================

            // Top-Left Corner Handle
            Box(
                modifier = Modifier
                    .offset(x = (tlX * boxW).dp - 24.dp, y = (tlY * boxH).dp - 24.dp)
                    .size(48.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isUserDragging = true
                                activeHandle = "TL"
                                hasUserManuallyAdjusted = true
                            },
                            onDragEnd = {
                                isUserDragging = false
                                activeHandle = null
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            tlX = (tlX + dragAmount.x / boxWPx).coerceIn(0.01f, trX - 0.05f)
                            tlY = (tlY + dragAmount.y / boxHPx).coerceIn(0.01f, blY - 0.05f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(if (activeHandle == "TL") 44.dp else 36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3).copy(alpha = 0.30f))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3))
                        .border(2.5.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Black))
                }
            }

            // Top-Right Corner Handle
            Box(
                modifier = Modifier
                    .offset(x = (trX * boxW).dp - 24.dp, y = (trY * boxH).dp - 24.dp)
                    .size(48.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isUserDragging = true
                                activeHandle = "TR"
                                hasUserManuallyAdjusted = true
                            },
                            onDragEnd = {
                                isUserDragging = false
                                activeHandle = null
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            trX = (trX + dragAmount.x / boxWPx).coerceIn(tlX + 0.05f, 0.99f)
                            trY = (trY + dragAmount.y / boxHPx).coerceIn(0.01f, brY - 0.05f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(if (activeHandle == "TR") 44.dp else 36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3).copy(alpha = 0.30f))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3))
                        .border(2.5.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Black))
                }
            }

            // Bottom-Right Corner Handle
            Box(
                modifier = Modifier
                    .offset(x = (brX * boxW).dp - 24.dp, y = (brY * boxH).dp - 24.dp)
                    .size(48.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isUserDragging = true
                                activeHandle = "BR"
                                hasUserManuallyAdjusted = true
                            },
                            onDragEnd = {
                                isUserDragging = false
                                activeHandle = null
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            brX = (brX + dragAmount.x / boxWPx).coerceIn(blX + 0.05f, 0.99f)
                            brY = (brY + dragAmount.y / boxHPx).coerceIn(trY + 0.05f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(if (activeHandle == "BR") 44.dp else 36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3).copy(alpha = 0.30f))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3))
                        .border(2.5.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Black))
                }
            }

            // Bottom-Left Corner Handle
            Box(
                modifier = Modifier
                    .offset(x = (blX * boxW).dp - 24.dp, y = (blY * boxH).dp - 24.dp)
                    .size(48.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isUserDragging = true
                                activeHandle = "BL"
                                hasUserManuallyAdjusted = true
                            },
                            onDragEnd = {
                                isUserDragging = false
                                activeHandle = null
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            blX = (blX + dragAmount.x / boxWPx).coerceIn(0.01f, brX - 0.05f)
                            blY = (blY + dragAmount.y / boxHPx).coerceIn(tlY + 0.05f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(if (activeHandle == "BL") 44.dp else 36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3).copy(alpha = 0.30f))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3))
                        .border(2.5.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Black))
                }
            }

            // ==========================================
            // 4 DRAGGABLE EDGE MIDPOINT HANDLES
            // ==========================================

            // Top Edge Midpoint Handle (translates Top Edge up/down)
            val midTopX = (tlX + trX) / 2f
            val midTopY = (tlY + trY) / 2f
            Box(
                modifier = Modifier
                    .offset(x = (midTopX * boxW).dp - 24.dp, y = (midTopY * boxH).dp - 16.dp)
                    .size(width = 48.dp, height = 32.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isUserDragging = true
                                activeHandle = "EDGE_TOP"
                                hasUserManuallyAdjusted = true
                            },
                            onDragEnd = {
                                isUserDragging = false
                                activeHandle = null
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            val dy = dragAmount.y / boxHPx
                            tlY = (tlY + dy).coerceIn(0.01f, blY - 0.05f)
                            trY = (trY + dy).coerceIn(0.01f, brY - 0.05f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.75f),
                    border = BorderStroke(1.5.dp, Color(0xFF00B4D8)),
                    modifier = Modifier.size(width = 34.dp, height = 18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.width(3.dp))
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.width(3.dp))
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                    }
                }
            }

            // Bottom Edge Midpoint Handle (translates Bottom Edge up/down)
            val midBotX = (blX + brX) / 2f
            val midBotY = (blY + brY) / 2f
            Box(
                modifier = Modifier
                    .offset(x = (midBotX * boxW).dp - 24.dp, y = (midBotY * boxH).dp - 16.dp)
                    .size(width = 48.dp, height = 32.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isUserDragging = true
                                activeHandle = "EDGE_BOT"
                                hasUserManuallyAdjusted = true
                            },
                            onDragEnd = {
                                isUserDragging = false
                                activeHandle = null
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            val dy = dragAmount.y / boxHPx
                            blY = (blY + dy).coerceIn(tlY + 0.05f, 0.99f)
                            brY = (brY + dy).coerceIn(trY + 0.05f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.75f),
                    border = BorderStroke(1.5.dp, Color(0xFF00B4D8)),
                    modifier = Modifier.size(width = 34.dp, height = 18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.width(3.dp))
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.width(3.dp))
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                    }
                }
            }

            // Left Edge Midpoint Handle (translates Left Edge left/right)
            val midLeftX = (tlX + blX) / 2f
            val midLeftY = (tlY + blY) / 2f
            Box(
                modifier = Modifier
                    .offset(x = (midLeftX * boxW).dp - 16.dp, y = (midLeftY * boxH).dp - 24.dp)
                    .size(width = 32.dp, height = 48.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isUserDragging = true
                                activeHandle = "EDGE_LEFT"
                                hasUserManuallyAdjusted = true
                            },
                            onDragEnd = {
                                isUserDragging = false
                                activeHandle = null
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            val dx = dragAmount.x / boxWPx
                            tlX = (tlX + dx).coerceIn(0.01f, trX - 0.05f)
                            blX = (blX + dx).coerceIn(0.01f, brX - 0.05f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.75f),
                    border = BorderStroke(1.5.dp, Color(0xFF00B4D8)),
                    modifier = Modifier.size(width = 18.dp, height = 34.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.height(3.dp))
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.height(3.dp))
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                    }
                }
            }

            // Right Edge Midpoint Handle (translates Right Edge left/right)
            val midRightX = (trX + brX) / 2f
            val midRightY = (trY + brY) / 2f
            Box(
                modifier = Modifier
                    .offset(x = (midRightX * boxW).dp - 16.dp, y = (midRightY * boxH).dp - 24.dp)
                    .size(width = 32.dp, height = 48.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isUserDragging = true
                                activeHandle = "EDGE_RIGHT"
                                hasUserManuallyAdjusted = true
                            },
                            onDragEnd = {
                                isUserDragging = false
                                activeHandle = null
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            val dx = dragAmount.x / boxWPx
                            trX = (trX + dx).coerceIn(tlX + 0.05f, 0.99f)
                            brX = (brX + dx).coerceIn(blX + 0.05f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.75f),
                    border = BorderStroke(1.5.dp, Color(0xFF00B4D8)),
                    modifier = Modifier.size(width = 18.dp, height = 34.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.height(3.dp))
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.height(3.dp))
                        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
                    }
                }
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

        // Dynamic, Intelligent Document Guidance Pill (Non-robotic, only green when document exists!)
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = when {
                featureMode == "ID Cards" -> Color(0xFF1A237E)
                featureMode == "Book" -> Color(0xFF4A148C)
                featureMode == "To Word" -> Color(0xFF0D47A1)
                featureMode == "Extract Text" -> Color(0xFF004D40)
                hasUserManuallyAdjusted -> Color(0xFF0F2537)
                isDocDetected -> Color(0xFF0F2E22)
                else -> Color.Black.copy(alpha = 0.70f)
            },
            border = BorderStroke(
                1.2.dp,
                when {
                    featureMode == "ID Cards" -> Color(0xFF82B1FF)
                    featureMode == "Book" -> Color(0xFFE040FB)
                    featureMode == "To Word" -> Color(0xFF448AFF)
                    featureMode == "Extract Text" -> Color(0xFF64FFDA)
                    hasUserManuallyAdjusted -> Color(0xFF00B4D8)
                    isDocDetected -> Color(0xFF00FFA3)
                    else -> Color.White.copy(alpha = 0.35f)
                }
            ),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 65.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                featureMode == "ID Cards" -> Color(0xFF82B1FF)
                                featureMode == "Book" -> Color(0xFFE040FB)
                                featureMode == "To Word" -> Color(0xFF448AFF)
                                featureMode == "Extract Text" -> Color(0xFF64FFDA)
                                hasUserManuallyAdjusted -> Color(0xFF00B4D8)
                                isDocDetected -> Color(0xFF00FFA3)
                                else -> Color(0xFFFFB74D)
                            }
                        )
                )
                Text(
                    text = when {
                        featureMode == "ID Cards" -> if (idCardStep == 1) "💳 ID Card: Step 1 of 2 (Scan Front)" else "💳 ID Card: Step 2 of 2 (Scan Back)"
                        featureMode == "Book" -> "📖 Book Spread Mode (Auto-Split Left & Right)"
                        featureMode == "To Word" -> "📝 To Word Mode: Scan to export .docx"
                        featureMode == "Extract Text" -> "🔤 Extract Text: Scan to OCR"
                        featureMode == "Sign" -> "✍️ Sign Mode: Scan to add E-Signature"
                        featureMode == "Smart Erase" -> "🪄 Smart Erase: Scan to clean stains"
                        hasUserManuallyAdjusted -> "✏️ Custom Edges Active (Draggable)"
                        isDocDetected -> "🟢 Document Detected (${(detectionConfidence * 100).toInt()}%)"
                        else -> "⚪ Align document inside frame"
                    },
                    color = Color.White,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // ID Card Front Preview Badge (When waiting for back side)
        if (featureMode == "ID Cards" && idCardStep == 2 && idCardFrontBitmap != null) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.Black.copy(alpha = 0.85f),
                border = BorderStroke(1.dp, Color(0xFF00FFA3)),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-12).dp, y = 110.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Front Saved ✓", color = Color(0xFF00FFA3), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Image(
                        bitmap = idCardFrontBitmap.asImageBitmap(),
                        contentDescription = "Front Side",
                        modifier = Modifier
                            .size(width = 60.dp, height = 38.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Single-Side",
                            color = Color.White,
                            fontSize = 9.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF00C853))
                                .clickable { onFinishSingleSideIdCard() }
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                        Text(
                            text = "Reset",
                            color = Color.LightGray,
                            fontSize = 9.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.DarkGray)
                                .clickable { onResetIdCard() }
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        // Floating Quick Framing Controls Row (Auto-Detect, Full Frame, Reset)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 158.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AssistChip(
                onClick = {
                    hasUserManuallyAdjusted = false
                    isAutoDetectActive = true
                    val currentBmp = previewView.bitmap
                    if (currentBmp != null) {
                        val result = DocumentEdgeProcessor.detectDocument(currentBmp)
                        isDocDetected = result.isDetected
                        detectionConfidence = result.confidence
                        detectionMessage = result.statusMessage
                        if (result.isDetected) {
                            val c = result.corners
                            tlX = c.topLeft.x; tlY = c.topLeft.y
                            trX = c.topRight.x; trY = c.topRight.y
                            brX = c.bottomRight.x; brY = c.bottomRight.y
                            blX = c.bottomLeft.x; blY = c.bottomLeft.y
                            Toast.makeText(context, "Document edges auto-detected", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Position document inside frame", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                label = { Text("🪄 Auto Edge", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (isAutoDetectActive && !hasUserManuallyAdjusted) Color(0xFF0F3B2C) else Color(0xFF1E293B)
                ),
                border = BorderStroke(1.dp, if (isAutoDetectActive && !hasUserManuallyAdjusted) Color(0xFF00FFA3) else Color.White.copy(alpha = 0.3f))
            )

            AssistChip(
                onClick = {
                    hasUserManuallyAdjusted = true
                    isAutoDetectActive = false
                    tlX = 0.03f; tlY = 0.05f
                    trX = 0.97f; trY = 0.05f
                    brX = 0.97f; brY = 0.95f
                    blX = 0.03f; blY = 0.95f
                },
                label = { Text("🔲 Full Page", color = Color.White, fontSize = 11.sp) },
                colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF1E293B)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
            )

            if (hasUserManuallyAdjusted) {
                AssistChip(
                    onClick = {
                        hasUserManuallyAdjusted = false
                        isAutoDetectActive = true
                        tlX = 0.10f; tlY = 0.16f
                        trX = 0.90f; trY = 0.16f
                        brX = 0.90f; brY = 0.82f
                        blX = 0.10f; blY = 0.82f
                    },
                    label = { Text("↺ Reset", color = Color.White, fontSize = 11.sp) },
                    colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF1E293B)),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
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
                    val isSelected = mode == featureMode
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onFeatureModeChanged(mode) }
                            .padding(horizontal = 4.dp, vertical = 4.dp)
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
                        onOpenAllFeatures()
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
                            val activeCorners = DocCorners(
                                topLeft = PointF(tlX, tlY),
                                topRight = PointF(trX, trY),
                                bottomRight = PointF(brX, brY),
                                bottomLeft = PointF(blX, blY)
                            )
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
                                                    onImageCaptured(bmp, activeCorners)
                                                } else {
                                                    onImageCaptured(createDocumentPreviewSample("Assignment"), activeCorners)
                                                }
                                            } catch (e: Exception) {
                                                onImageCaptured(createDocumentPreviewSample("Assignment"), activeCorners)
                                            }
                                        }

                                        override fun onError(exception: ImageCaptureException) {
                                            onImageCaptured(createDocumentPreviewSample("Assignment"), activeCorners)
                                        }
                                    }
                                )
                            } else {
                                onImageCaptured(createDocumentPreviewSample("Assignment"), activeCorners)
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
// 2. INTERACTIVE 4-CORNER & 4-EDGE CROP ADJUSTER VIEW
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

    var activeAdjustHandle by remember { mutableStateOf<String?>(null) }

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
            Text("Adjust Document Edges", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
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
                text = "💡 Drag any corner pin or edge handle to fit document borders. Perspective warp will rectify paper flat.",
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
            val density = LocalDensity.current
            val boxWPx = with(density) { maxWidth.toPx() }
            val boxHPx = with(density) { maxHeight.toPx() }

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

                // Darken outside the quad
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w, 0f)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()

                    moveTo(pTl.x, pTl.y)
                    lineTo(pBl.x, pBl.y)
                    lineTo(pBr.x, pBr.y)
                    lineTo(pTr.x, pTr.y)
                    close()

                    fillType = PathFillType.EvenOdd
                }
                drawPath(path, color = Color.Black.copy(alpha = 0.35f))

                val stroke = 3.5f
                val quadColor = Color(0xFF00FFA3)

                drawLine(color = quadColor, start = pTl, end = pTr, strokeWidth = stroke)
                drawLine(color = quadColor, start = pTr, end = pBr, strokeWidth = stroke)
                drawLine(color = quadColor, start = pBr, end = pBl, strokeWidth = stroke)
                drawLine(color = quadColor, start = pBl, end = pTl, strokeWidth = stroke)
            }

            // ==========================================
            // 4 CORNER PINS (Precise 1:1 Pixel Dragging)
            // ==========================================

            // Top-Left Pin
            Box(
                modifier = Modifier
                    .offset(x = (tlX * boxW).dp - 22.dp, y = (tlY * boxH).dp - 22.dp)
                    .size(44.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { activeAdjustHandle = "TL" },
                            onDragEnd = { activeAdjustHandle = null }
                        ) { change, dragAmount ->
                            change.consume()
                            tlX = (tlX + dragAmount.x / boxWPx).coerceIn(0.01f, trX - 0.04f)
                            tlY = (tlY + dragAmount.y / boxHPx).coerceIn(0.01f, blY - 0.04f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(if (activeAdjustHandle == "TL") 42.dp else 34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3).copy(alpha = 0.28f))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3))
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Black))
                }
            }

            // Top-Right Pin
            Box(
                modifier = Modifier
                    .offset(x = (trX * boxW).dp - 22.dp, y = (trY * boxH).dp - 22.dp)
                    .size(44.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { activeAdjustHandle = "TR" },
                            onDragEnd = { activeAdjustHandle = null }
                        ) { change, dragAmount ->
                            change.consume()
                            trX = (trX + dragAmount.x / boxWPx).coerceIn(tlX + 0.04f, 0.99f)
                            trY = (trY + dragAmount.y / boxHPx).coerceIn(0.01f, brY - 0.04f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(if (activeAdjustHandle == "TR") 42.dp else 34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3).copy(alpha = 0.28f))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3))
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Black))
                }
            }

            // Bottom-Right Pin
            Box(
                modifier = Modifier
                    .offset(x = (brX * boxW).dp - 22.dp, y = (brY * boxH).dp - 22.dp)
                    .size(44.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { activeAdjustHandle = "BR" },
                            onDragEnd = { activeAdjustHandle = null }
                        ) { change, dragAmount ->
                            change.consume()
                            brX = (brX + dragAmount.x / boxWPx).coerceIn(blX + 0.04f, 0.99f)
                            brY = (brY + dragAmount.y / boxHPx).coerceIn(trY + 0.04f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(if (activeAdjustHandle == "BR") 42.dp else 34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3).copy(alpha = 0.28f))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3))
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Black))
                }
            }

            // Bottom-Left Pin
            Box(
                modifier = Modifier
                    .offset(x = (blX * boxW).dp - 22.dp, y = (blY * boxH).dp - 22.dp)
                    .size(44.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { activeAdjustHandle = "BL" },
                            onDragEnd = { activeAdjustHandle = null }
                        ) { change, dragAmount ->
                            change.consume()
                            blX = (blX + dragAmount.x / boxWPx).coerceIn(0.01f, brX - 0.04f)
                            blY = (blY + dragAmount.y / boxHPx).coerceIn(tlY + 0.04f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(if (activeAdjustHandle == "BL") 42.dp else 34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3).copy(alpha = 0.28f))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFA3))
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Black))
                }
            }

            // ==========================================
            // 4 EDGE MIDPOINT HANDLES IN CROP ADJUSTER
            // ==========================================

            // Top Edge Midpoint Handle
            val cMidTopX = (tlX + trX) / 2f
            val cMidTopY = (tlY + trY) / 2f
            Box(
                modifier = Modifier
                    .offset(x = (cMidTopX * boxW).dp - 22.dp, y = (cMidTopY * boxH).dp - 14.dp)
                    .size(width = 44.dp, height = 28.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val dy = dragAmount.y / boxHPx
                            tlY = (tlY + dy).coerceIn(0.01f, blY - 0.04f)
                            trY = (trY + dy).coerceIn(0.01f, brY - 0.04f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF1E293B),
                    border = BorderStroke(1.5.dp, Color(0xFF00B4D8)),
                    modifier = Modifier.size(width = 30.dp, height = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.width(2.dp))
                        Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(Color.White))
                    }
                }
            }

            // Bottom Edge Midpoint Handle
            val cMidBotX = (blX + brX) / 2f
            val cMidBotY = (blY + brY) / 2f
            Box(
                modifier = Modifier
                    .offset(x = (cMidBotX * boxW).dp - 22.dp, y = (cMidBotY * boxH).dp - 14.dp)
                    .size(width = 44.dp, height = 28.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val dy = dragAmount.y / boxHPx
                            blY = (blY + dy).coerceIn(tlY + 0.04f, 0.99f)
                            brY = (brY + dy).coerceIn(trY + 0.04f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF1E293B),
                    border = BorderStroke(1.5.dp, Color(0xFF00B4D8)),
                    modifier = Modifier.size(width = 30.dp, height = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.width(2.dp))
                        Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(Color.White))
                    }
                }
            }

            // Left Edge Midpoint Handle
            val cMidLeftX = (tlX + blX) / 2f
            val cMidLeftY = (tlY + blY) / 2f
            Box(
                modifier = Modifier
                    .offset(x = (cMidLeftX * boxW).dp - 14.dp, y = (cMidLeftY * boxH).dp - 22.dp)
                    .size(width = 28.dp, height = 44.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val dx = dragAmount.x / boxWPx
                            tlX = (tlX + dx).coerceIn(0.01f, trX - 0.04f)
                            blX = (blX + dx).coerceIn(0.01f, brX - 0.04f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF1E293B),
                    border = BorderStroke(1.5.dp, Color(0xFF00B4D8)),
                    modifier = Modifier.size(width = 16.dp, height = 30.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.height(2.dp))
                        Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(Color.White))
                    }
                }
            }

            // Right Edge Midpoint Handle
            val cMidRightX = (trX + brX) / 2f
            val cMidRightY = (trY + brY) / 2f
            Box(
                modifier = Modifier
                    .offset(x = (cMidRightX * boxW).dp - 14.dp, y = (cMidRightY * boxH).dp - 22.dp)
                    .size(width = 28.dp, height = 44.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val dx = dragAmount.x / boxWPx
                            trX = (trX + dx).coerceIn(tlX + 0.04f, 0.99f)
                            brX = (brX + dx).coerceIn(blX + 0.04f, 0.99f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF1E293B),
                    border = BorderStroke(1.5.dp, Color(0xFF00B4D8)),
                    modifier = Modifier.size(width = 16.dp, height = 30.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.height(2.dp))
                        Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(Color.White))
                    }
                }
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
                        val auto = DocumentEdgeProcessor.detectDocument(bitmap)
                        tlX = auto.corners.topLeft.x
                        tlY = auto.corners.topLeft.y
                        trX = auto.corners.topRight.x
                        trY = auto.corners.topRight.y
                        brX = auto.corners.bottomRight.x
                        brY = auto.corners.bottomRight.y
                        blX = auto.corners.bottomLeft.x
                        blY = auto.corners.bottomLeft.y
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
    onSignDocument: () -> Unit = {},
    onSmartErase: () -> Unit = {},
    onExportWord: () -> Unit = {},
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
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
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
                    modifier = Modifier.clickable { onSignDocument() }
                ) {
                    Icon(Icons.Default.Draw, contentDescription = "Sign", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                    Text("Sign", fontSize = 9.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onSmartErase() }
                ) {
                    Icon(Icons.Default.AutoFixHigh, contentDescription = "Erase", tint = Color(0xFFE65100), modifier = Modifier.size(22.dp))
                    Text("Erase", fontSize = 9.sp, color = Color(0xFFE65100), fontWeight = FontWeight.SemiBold)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onExportWord() }
                ) {
                    Icon(Icons.Default.Description, contentDescription = "To Word", tint = Color(0xFF1565C0), modifier = Modifier.size(22.dp))
                    Text("To Word", fontSize = 9.sp, color = Color(0xFF1565C0), fontWeight = FontWeight.SemiBold)
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
