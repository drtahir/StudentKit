package com.example.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint as AndroidPaint
import android.graphics.RectF
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Gesture
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.StudentKitViewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt

// Custom drawing model for Signature Pad
data class LinePath(
    val path: Path,
    val color: Color,
    val strokeWidth: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignaturePadScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var activeTab by remember { mutableStateOf("Create Signature") } // "Create Signature", "Sign Document"

    // Drawing states
    var paths = remember { mutableStateListOf<LinePath>() }
    var currentPath by remember { mutableStateOf<Path?>(null) }
    var penColor by remember { mutableStateOf(Color.Black) }
    var strokeWidth by remember { mutableStateOf(8f) }

    // Saved signature cache (transparent bitmaps)
    var savedSignatures = remember { mutableStateListOf<Bitmap>() }
    var selectedSignatureForStamping by remember { mutableStateOf<Bitmap?>(null) }

    // Background document selection
    var selectedDocUri by remember { mutableStateOf<Uri?>(null) }
    var documentBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Floating Signature Overlay transforms
    var signatureOffset by remember { mutableStateOf(Offset(200f, 200f)) }
    var signatureScale by remember { mutableStateOf(1.0f) }
    var signatureRotation by remember { mutableStateOf(0f) }

    val docPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedDocUri = uri
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val rawBitmap = BitmapFactory.decodeStream(inputStream)
                if (rawBitmap != null) {
                    // Copy to mutable bitmap for modification/compositing
                    documentBitmap = rawBitmap.copy(Bitmap.Config.ARGB_8888, true)
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error reading document file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Tabs Row
            TabRow(
                selectedTabIndex = if (activeTab == "Create Signature") 0 else 1,
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = activeTab == "Create Signature",
                    onClick = { activeTab = "Create Signature" },
                    text = { Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Gesture, contentDescription = "Draw", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Signature Pad", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }}
                )
                Tab(
                    selected = activeTab == "Sign Document",
                    onClick = { activeTab = "Sign Document" },
                    text = { Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DriveFileRenameOutline, contentDescription = "Sign", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Stamp & Sign Doc", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }}
                )
            }

            if (activeTab == "Create Signature") {
                // SIGNATURE CANVAS CREATOR VIEW
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "DRAW SIGNATURE BELOW",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Canvas Container
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.5.dp, Color.LightGray, RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = { startOffset ->
                                        val path = Path().apply {
                                            moveTo(startOffset.x, startOffset.y)
                                        }
                                        currentPath = path
                                    },
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        currentPath?.lineTo(change.position.x, change.position.y)
                                        // Force recomposition during drawing
                                        val temp = currentPath
                                        currentPath = null
                                        currentPath = temp
                                    },
                                    onDragEnd = {
                                        currentPath?.let {
                                            paths.add(LinePath(it, penColor, strokeWidth))
                                        }
                                        currentPath = null
                                    }
                                )
                            }
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            // Draw completed paths
                            paths.forEach { item ->
                                drawPath(
                                    path = item.path,
                                    color = item.color,
                                    style = Stroke(width = item.strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
                                )
                            }
                            // Draw active path
                            currentPath?.let { path ->
                                drawPath(
                                    path = path,
                                    color = penColor,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
                                )
                            }
                        }

                        if (paths.isEmpty() && currentPath == null) {
                            Text(
                                text = "Use your finger or stylus to sign here",
                                color = Color.LightGray,
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stroke & Color Customizations
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Pen Colors
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            val colors = listOf(Color.Black, Color(0xFF0F172A), Color(0xFF003366), Color(0xFFC51162))
                            colors.forEach { color ->
                                val isSelected = penColor == color
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(color, CircleShape)
                                        .border(
                                            width = if (isSelected) 3.dp else 1.dp,
                                            color = if (isSelected) Color.White else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color.LightGray,
                                            shape = CircleShape
                                        )
                                        .clickable { penColor = color }
                                )
                            }
                        }

                        // Pen Thickness Row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Size:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            listOf(4f, 8f, 12f, 16f).forEach { size ->
                                val isSelected = strokeWidth == size
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) Color.DarkGray else Color.LightGray.copy(alpha = 0.4f))
                                        .clickable { strokeWidth = size },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size((size * 0.8f).dp)
                                            .background(Color.White, CircleShape)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Buttons (Clear, Save)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                paths.clear()
                                currentPath = null
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color.Gray)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Clear")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Clear")
                        }

                        Button(
                            onClick = {
                                if (paths.isEmpty()) {
                                    Toast.makeText(context, "Please draw a signature first", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                // Render Signature Path to Transparent Bitmap
                                val signatureBitmap = renderSignatureToBitmap(paths, strokeWidth)
                                savedSignatures.add(signatureBitmap)
                                selectedSignatureForStamping = signatureBitmap
                                Toast.makeText(context, "Signature saved to Stamp Studio cache!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Outlined.Save, contentDescription = "Save")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Save Stamp")
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Saved Stamped List Cache
                    if (savedSignatures.isNotEmpty()) {
                        Text(
                            text = "ACTIVE SIGNATURE STAMPS CACHE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(savedSignatures) { sig ->
                                val isSelected = selectedSignatureForStamping == sig
                                Box(
                                    modifier = Modifier
                                        .size(100.dp, 60.dp)
                                        .border(
                                            width = if (isSelected) 2.5.dp else 1.dp,
                                            color = if (isSelected) Color(0xFF0F172A) else Color.LightGray,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .background(Color.White)
                                        .clickable {
                                            selectedSignatureForStamping = sig
                                            Toast.makeText(context, "Stamp selected for Document", Toast.LENGTH_SHORT).show()
                                        }
                                        .padding(4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    androidx.compose.foundation.Image(
                                        bitmap = sig.asImageBitmap(),
                                        contentDescription = "Signature Instance",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // DOCUMENT OVERLAY STAMPER / SIGNER SCREEN
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "1. SELECT BACKGROUND DOCUMENT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { docPickerLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.3f)
                        ) {
                            Icon(Icons.Default.CloudUpload, contentDescription = "Load")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Import Document File", fontSize = 12.sp)
                        }

                        // Load a dummy Letter layout as preset if no document is loaded
                        OutlinedButton(
                            onClick = {
                                val dummyDoc = createDummyContractPreset(context)
                                documentBitmap = dummyDoc
                                Toast.makeText(context, "Loaded Contract Document Template", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                            border = BorderStroke(1.dp, Color.LightGray),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.ReceiptLong, contentDescription = "Template")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Use Template", fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stamp Signature Select reminder
                    if (selectedSignatureForStamping == null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFEF3C7), RoundedCornerShape(8.dp))
                                .border(1.dp, Color(0xFFF59E0B), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, contentDescription = "Warn", tint = Color(0xFFD97706))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "No active Stamp. Draw a signature first in Tab 1, or tap above to create.",
                                    color = Color(0xFF92400E),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "2. DRAG / PINCH STAMP TO ADJUST AND COSIGN",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Document interactive workbench
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.5.dp, Color.LightGray, RoundedCornerShape(12.dp))
                            .background(Color.White)
                    ) {
                        val docBmp = documentBitmap
                        if (docBmp != null) {
                            // Render document background
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .pointerInput(Unit) {
                                            detectTransformGestures { _, pan, zoom, rotation ->
                                                signatureOffset += pan
                                                signatureScale = (signatureScale * zoom).coerceIn(0.2f, 4.0f)
                                                signatureRotation += rotation
                                            }
                                        }
                                ) {
                                    // Background image
                                    androidx.compose.foundation.Image(
                                        bitmap = docBmp.asImageBitmap(),
                                        contentDescription = "Document Workbench",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit
                                    )

                                    // Floating Signature overlay
                                    selectedSignatureForStamping?.let { sigBmp ->
                                        Box(
                                            modifier = Modifier
                                                .offset {
                                                    IntOffset(
                                                        signatureOffset.x.roundToInt(),
                                                        signatureOffset.y.roundToInt()
                                                    )
                                                }
                                                .size(
                                                    width = (180 * signatureScale).dp,
                                                    height = (90 * signatureScale).dp
                                                )
                                                .border(
                                                    1.5.dp,
                                                    Color(0xFF2563EB).copy(alpha = 0.6f),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .background(Color.White.copy(alpha = 0.1f))
                                        ) {
                                            androidx.compose.foundation.Image(
                                                bitmap = sigBmp.asImageBitmap(),
                                                contentDescription = "Stamping Instance",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            // Empty document placeholder
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.Assignment,
                                        contentDescription = "No document",
                                        tint = Color.LightGray,
                                        modifier = Modifier.size(54.dp)
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Import a letter, invoice, contract image\nor tap 'Use Template' to load a layout",
                                        color = Color.LightGray,
                                        textAlign = TextAlign.Center,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Actions (Compile, Reset)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                signatureOffset = Offset(200f, 200f)
                                signatureScale = 1.0f
                                signatureRotation = 0f
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                            border = BorderStroke(1.dp, Color.LightGray),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.RestartAlt, contentDescription = "Reset")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reset Stamp", fontSize = 11.sp)
                        }

                        Button(
                            onClick = {
                                val docBmp = documentBitmap
                                val sigBmp = selectedSignatureForStamping
                                if (docBmp == null) {
                                    Toast.makeText(context, "Please load a document first", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                if (sigBmp == null) {
                                    Toast.makeText(context, "Please select/save a signature stamp first", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                // Compose and save signature permanently onto document Bitmap!
                                val compositeBitmap = burnSignatureToDocument(
                                    document = docBmp,
                                    signature = sigBmp,
                                    offsetX = signatureOffset.x,
                                    offsetY = signatureOffset.y,
                                    scale = signatureScale
                                )

                                val savedFile = saveBitmapToGallery(context, compositeBitmap)
                                if (savedFile != null) {
                                    Toast.makeText(context, "Document signed and saved successfully! check Gallery", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "Failed to save file", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.5f)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Burn")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Compile & Sign File", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// Convert Compose drawing paths to transparent Bitmap
fun renderSignatureToBitmap(paths: List<LinePath>, thickness: Float): Bitmap {
    val bounds = RectF()
    // Define canvas boundaries for bounding box or standard envelope
    val width = 720
    val height = 360
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = AndroidCanvas(bitmap)

    // Fill background transparently
    canvas.drawColor(android.graphics.Color.TRANSPARENT)

    val paint = AndroidPaint().apply {
        isAntiAlias = true
        style = AndroidPaint.Style.STROKE
        strokeCap = AndroidPaint.Cap.ROUND
        strokeJoin = AndroidPaint.Join.ROUND
    }

    paths.forEach { item ->
        paint.color = item.color.toArgb()
        paint.strokeWidth = item.strokeWidth * 1.5f // scale factor

        val androidPath = item.path.asAndroidPath()
        canvas.drawPath(androidPath, paint)
    }

    return bitmap
}

// Create preset contracts letter structure
fun createDummyContractPreset(context: Context): Bitmap {
    val width = 1200
    val height = 1600
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = AndroidCanvas(bitmap)

    // Premium white cardstock look
    canvas.drawColor(android.graphics.Color.WHITE)

    val paint = AndroidPaint().apply {
        isAntiAlias = true
        color = android.graphics.Color.DKGRAY
    }

    // Border line
    paint.style = AndroidPaint.Style.STROKE
    paint.strokeWidth = 12f
    paint.color = android.graphics.Color.parseColor("#0F172A")
    canvas.drawRect(30f, 30f, width - 30f, height - 30f, paint)

    // Heading
    paint.style = AndroidPaint.Style.FILL
    paint.textSize = 54f
    paint.color = android.graphics.Color.parseColor("#0F172A")
    paint.isFakeBoldText = true
    canvas.drawText("MEMORANDUM OF AGREEMENT", 100f, 150f, paint)

    paint.textSize = 28f
    paint.isFakeBoldText = false
    paint.color = android.graphics.Color.parseColor("#475569")
    canvas.drawText("Project: Mobile Application Development & Licensing", 100f, 210f, paint)
    canvas.drawText("Date: " + SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()), 100f, 250f, paint)

    // Decorative divider rule
    paint.strokeWidth = 4f
    canvas.drawLine(100f, 280f, width - 100f, 280f, paint)

    // Standard contracts clauses
    paint.textSize = 26f
    paint.color = android.graphics.Color.BLACK
    val clauses = listOf(
        "1. GENERAL TERM: This license agreement is executed by and between the developer",
        "and client as a formal mutual commitment to absolute high-fidelity product engineering.",
        "",
        "2. SCOPE OF SERVICES: The engineer covenants to build all components requested",
        "with exceptional Material 3 responsive layouts, offline SQLite databases, and clean,",
        "zero-slop aesthetics as outlined in system guidelines.",
        "",
        "3. SIGNATURE INTENT: Stamping a signature below certifies that the file has been",
        "reviewed, authorized, and is legally ready for final release and APK packaging."
    )

    var currentY = 360f
    clauses.forEach { clause ->
        canvas.drawText(clause, 100f, currentY, paint)
        currentY += 45f
    }

    // Signatures slots
    paint.textSize = 26f
    paint.color = android.graphics.Color.GRAY
    canvas.drawLine(150f, 1300f, 450f, 1300f, paint)
    canvas.drawText("Developer Endorsement", 150f, 1340f, paint)

    canvas.drawLine(750f, 1300f, 1050f, 1300f, paint)
    canvas.drawText("Client Executive Signature", 750f, 1340f, paint)

    return bitmap
}

// Composite / Burn Signature onto background document
fun burnSignatureToDocument(
    document: Bitmap,
    signature: Bitmap,
    offsetX: Float,
    offsetY: Float,
    scale: Float
): Bitmap {
    val composite = document.copy(Bitmap.Config.ARGB_8888, true)
    val canvas = AndroidCanvas(composite)

    val scaledWidth = (signature.width * scale * 1.5f).toInt()
    val scaledHeight = (signature.height * scale * 1.5f).toInt()

    val scaledSignature = Bitmap.createScaledBitmap(signature, scaledWidth, scaledHeight, true)

    // Render scaled transparent signature at user specified position
    canvas.drawBitmap(scaledSignature, offsetX, offsetY, null)

    return composite
}

// Save output file in device external pictures folder
fun saveBitmapToGallery(context: Context, bitmap: Bitmap): File? {
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val file = File(storageDir, "Signed_Doc_${System.currentTimeMillis()}.jpg")

    return try {
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
        out.flush()
        out.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
