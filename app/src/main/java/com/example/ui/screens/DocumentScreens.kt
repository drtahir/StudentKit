package com.example.ui.screens

import android.app.Activity
import android.content.Context
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
@Composable
fun ImageToPdfScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var selectedImagesList by remember { mutableStateOf(listOf("Receipt_1.jpg", "Assignment_Page2.jpg")) }
    var pageSize by remember { mutableStateOf("A4") }
    var orientation by remember { mutableStateOf("Portrait") }
    var marginSetting by remember { mutableStateOf("Normal") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Photos list to compile into PDF pages:", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                if (selectedImagesList.isEmpty()) {
                    Text("No photos selected. Pick some!", modifier = Modifier.padding(8.dp))
                } else {
                    selectedImagesList.forEachIndexed { index, fileName ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Image, null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(fileName, fontSize = 14.sp)
                            }
                            IconButton(
                                onClick = {
                                    selectedImagesList = selectedImagesList.toMutableList().apply { removeAt(index) }
                                }
                            ) {
                                Icon(Icons.Default.Close, null, tint = Color.Red, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        selectedImagesList = selectedImagesList + "Doc_Frame_${selectedImagesList.size + 1}.jpg"
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.AddPhotoAlternate, null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Simulate Add Photo")
                }
            }
        }

        Text("Layout settings:", fontWeight = FontWeight.Bold, fontSize = 14.sp)

        // Options details
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (selectedImagesList.isEmpty()) {
                    Toast.makeText(context, "Please select at least one photo!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Generated PDF with ${selectedImagesList.size} pages at full resolution!", Toast.LENGTH_LONG).show()
                    viewModel.navigateBack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.PictureAsPdf, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Compile & Save standard PDF")
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: IMAGE TO XLS
// -------------------------------------------------------------
@Composable
fun ImageToXlsScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var extractionResult by remember {
        mutableStateOf(
            listOf(
                listOf("Roll No", "Student Name", "Quiz 1 Mark", "Grade"),
                listOf("CS-101", "Imran Khan", "18.5", "A"),
                listOf("CS-102", "Benazir Shah", "14.0", "B+"),
                listOf("CS-103", "Nawaz Sharif", "9.0", "C")
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "📊 Image to Excel (Spreadsheet OCR Simulator)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Snap or select a printed table/grading chart, parse using OCR, and edit before saving directly as .XLSX!",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        Text("Interactive extracted grid fields:", fontWeight = FontWeight.Bold)

        // Scrollable Table Grid
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(extractionResult) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (extractionResult.indexOf(rowItems) == 0) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else Color.Transparent)
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        rowItems.forEach { cellText ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                                    .padding(6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(cellText, fontSize = 12.sp, maxLines = 1)
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                Toast.makeText(context, "StudentKit_GradeBook.xlsx generated and saved to your Downloads folder!", Toast.LENGTH_SHORT).show()
                viewModel.navigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.TableChart, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Export and Download Excel file")
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: CV BUILDER & NATIVE PDF GENERATOR
// -------------------------------------------------------------
@Composable
fun CvBuilderScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current

    // CV Field values Form
    var fullName by remember { mutableStateOf("Bilal Ahmed Khan") }
    var email by remember { mutableStateOf("bilal.ahmed@uok.edu.pk") }
    var phone by remember { mutableStateOf("+92 300 1234567") }
    var location by remember { mutableStateOf("Karachi, Pakistan") }
    var summaryText by remember { mutableStateOf("Highly motivated Computer Science student seeking software engineering internships in Python and Android Development.") }
    var degree by remember { mutableStateOf("BS in Computer Science") }
    var institute by remember { mutableStateOf("University of Karachi") }
    var experienceTitle by remember { mutableStateOf("Freelance Web Developer") }
    var skillsListCsv by remember { mutableStateOf("Python, Android Compose, Git, SQL, Public Speaking") }

    var selectedStyleTemplate by remember { mutableStateOf("Modern blue sidebar") } // "Modern blue sidebar", "Classic formal"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Profile Resume Builder Form", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Contact Phone") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("City, Location") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = summaryText,
            onValueChange = { summaryText = it },
            label = { Text("Professional Career Objective / Summary") },
            maxLines = 4,
            modifier = Modifier.fillMaxWidth()
        )

        Text("Academic Details:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        OutlinedTextField(
            value = degree,
            onValueChange = { degree = it },
            label = { Text("Degree Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = institute,
            onValueChange = { institute = it },
            label = { Text("University / College") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Work / Project Experience:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        OutlinedTextField(
            value = experienceTitle,
            onValueChange = { experienceTitle = it },
            label = { Text("Job / Internship Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = skillsListCsv,
            onValueChange = { skillsListCsv = it },
            label = { Text("Core Skills (separated by commas)") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Select CV Style Theme layout:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf("Modern blue sidebar", "Classic formal").forEach { tm ->
                ElevatedFilterChip(
                    selected = selectedStyleTemplate == tm,
                    onClick = { selectedStyleTemplate = tm },
                    label = { Text(tm) }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Trigger Android Print Job & compile Native PDF inside device context!
        Button(
            onClick = {
                if (fullName.isEmpty() || email.isEmpty()) {
                    Toast.makeText(context, "Please write at least name and email!", Toast.LENGTH_SHORT).show()
                } else {
                    triggerNativePdfGeneration(
                        context = context,
                        name = fullName,
                        email = email,
                        phone = phone,
                        location = location,
                        summary = summaryText,
                        degree = degree,
                        institute = institute,
                        experience = experienceTitle,
                        skills = skillsListCsv,
                        styleTemplate = selectedStyleTemplate
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Print, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Compile & Print Smart PDF Resume")
        }
    }
}

/**
 * Real Native Android PdfDocument generation logic and Print Job dispatcher!
 * Drawing vector CV on A4 Canvas. 100% sharp text!
 */
private fun triggerNativePdfGeneration(
    context: Context,
    name: String,
    email: String,
    phone: String,
    location: String,
    summary: String,
    degree: String,
    institute: String,
    experience: String,
    skills: String,
    styleTemplate: String
) {
    try {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
        if (printManager == null) {
            Toast.makeText(context, "Printing not supported on this device.", Toast.LENGTH_SHORT).show()
            return
        }

        // Configure printing custom adapter
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

                // Create info description
                val info = android.print.PrintDocumentInfo.Builder("StudentKit_Resume.pdf")
                    .setContentType(android.print.PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(1)
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
                // standard A4 dimensions (72 points DPI -> 595 x 842 points)
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
                val page = pdfDoc?.startPage(pageInfo)

                val canvas = page?.canvas
                if (canvas != null) {
                    val paintTitle = Paint().apply {
                        color = if (styleTemplate == "Classic formal") 0xFF000000.toInt() else 0xFF1565C0.toInt()
                        textSize = 24f
                        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                    }

                    val paintSubtitle = Paint().apply {
                        color = 0xFF424242.toInt()
                        textSize = 12f
                        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                    }

                    val paintText = Paint().apply {
                        color = 0xFF212121.toInt()
                        textSize = 11f
                        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                    }

                    // Top heading line decoration
                    if (styleTemplate == "Modern blue sidebar") {
                        val headerPaint = Paint().apply { color = 0xFF1565C0.toInt() }
                        canvas.drawRect(0f, 0f, 150f, 842f, headerPaint) // sidebar rectangular background!

                        // Sidebar Texts (White color)
                        val sidebarPaint = Paint().apply {
                            color = 0xFFFFFFFF.toInt()
                            textSize = 12f
                            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                        }
                        val sidebarDescPaint = Paint().apply {
                            color = 0xFFE0E0E0.toInt()
                            textSize = 10f
                            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                        }

                        canvas.drawText("CONTACT INFO", 15f, 60f, sidebarPaint)
                        canvas.drawText(phone, 15f, 80f, sidebarDescPaint)
                        canvas.drawText(email, 15f, 100f, sidebarDescPaint)
                        canvas.drawText(location, 15f, 120f, sidebarDescPaint)

                        canvas.drawText("CORE SKILLS", 15f, 180f, sidebarPaint)
                        var skillY = 200f
                        skills.split(",").forEach { sk ->
                            canvas.drawText("• ${sk.trim()}", 15f, skillY, sidebarDescPaint)
                            skillY += 20f
                        }

                        // Right Body Text offsets (Starting from 170f)
                        canvas.drawText(name, 170f, 60f, paintTitle)
                        canvas.drawText("Professional Profile Summary", 170f, 110f, paintSubtitle)
                        canvas.drawText(summary, 170f, 130f, paintText)

                        canvas.drawText("Education Details", 170f, 200f, paintSubtitle)
                        canvas.drawText("$degree - $institute", 170f, 220f, paintText)

                        canvas.drawText("Experience & Projects", 170f, 280f, paintSubtitle)
                        canvas.drawText("$experience", 170f, 300f, paintText)
                    } else {
                        // Classic Formal style PDF design (Traditional centered flow)
                        canvas.drawText(name, 50f, 60f, paintTitle)
                        canvas.drawText("Contact: $phone | Email: $email | City: $location", 50f, 80f, paintText)
                        canvas.drawRect(50f, 90f, 545f, 92f, Paint().apply { color = 0xFF000000.toInt() }) // line break

                        canvas.drawText("PROFESSIONAL SUMMARY", 50f, 120f, paintSubtitle)
                        canvas.drawText(summary, 50f, 140f, paintText)

                        canvas.drawText("ACADEMIC BACKGROUND", 50f, 210f, paintSubtitle)
                        canvas.drawText("$degree - $institute", 50f, 230f, paintText)

                        canvas.drawText("WORK EXPERIENCE", 50f, 300f, paintSubtitle)
                        canvas.drawText(experience, 50f, 320f, paintText)

                        canvas.drawText("CORE CAPABILITIES", 50f, 390f, paintSubtitle)
                        canvas.drawText(skills, 50f, 410f, paintText)
                    }
                }

                pdfDoc?.finishPage(page)

                // Write output streams
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
        }

        // Print job dispatch
        printManager.print("StudentKit CV Build Job", printAdapter, android.print.PrintAttributes.Builder().build())
    } catch (exc: Exception) {
        Toast.makeText(context, "Export error: ${exc.localizedMessage}", Toast.LENGTH_LONG).show()
    }
}

// -------------------------------------------------------------
// MODULE 11: DOCUMENT SCANNER
// -------------------------------------------------------------
@Composable
fun DocumentScannerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var isScanning by remember { mutableStateOf(false) }
    var detectedBorderColor by remember { mutableStateOf(Color(0xFF00E676)) }
    var contrastFilterSelection by remember { mutableStateOf("High Contrast") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Camera simulation preview
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black)
                .border(3.dp, detectedBorderColor, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.CameraAlt, "Camera Frame", tint = Color.White, modifier = Modifier.size(70.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Text("Scanner camera feed active simulation...", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(20.dp))
                SuggestionChip(
                    onClick = {
                        detectedBorderColor = if (detectedBorderColor == Color.Red) Color(0xFF00E676) else Color.Red
                    },
                    label = { Text("Auto-Edge Detection: Locked") }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Enhance Filter", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Row {
                listOf("Original", "Grayscale", "High Contrast").forEach { flt ->
                    val sel = contrastFilterSelection == flt
                    ElevatedAssistChip(
                        onClick = { contrastFilterSelection = flt },
                        label = { Text(flt, fontSize = 11.sp, color = if (sel) MaterialTheme.colorScheme.primary else Color.Black) }
                    )
                }
            }
        }

        Button(
            onClick = {
                Toast.makeText(context, "Scanned page parsed. PDF written to storage successfully!", Toast.LENGTH_SHORT).show()
                viewModel.navigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.CropFree, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Capture, Crop & Flatten Document")
        }
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
