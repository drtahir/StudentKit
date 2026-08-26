package com.drtahir.studentkit.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

// -------------------------------------------------------------
// DATA MODELS FOR HANDWRITING STUDIO
// -------------------------------------------------------------

data class PaperTemplateStyle(
    val id: String,
    val name: String,
    val description: String,
    val bgColor: Int,
    val lineColor: Int,
    val marginLineColor: Int,
    val hasLeftMargin: Boolean = true,
    val hasTopMargin: Boolean = true,
    val isGrid: Boolean = false,
    val hasHoles: Boolean = true
)

val availablePaperTemplates = listOf(
    PaperTemplateStyle(
        id = "standard_lined",
        name = "Student Ruled Sheet",
        description = "Standard Pakistani & International university ruled notebook paper with red left margin",
        bgColor = 0xFFFCFCF9.toInt(),
        lineColor = 0xFF90CAF9.toInt(),
        marginLineColor = 0xFFEF5350.toInt(),
        hasLeftMargin = true,
        hasTopMargin = true,
        isGrid = false,
        hasHoles = true
    ),
    PaperTemplateStyle(
        id = "exam_answer_sheet",
        name = "Board / Uni Exam Sheet",
        description = "Dual margin header sheet for BISE / University mid-term and final assignment submissions",
        bgColor = 0xFFFDFBF7.toInt(),
        lineColor = 0xFFB0BEC5.toInt(),
        marginLineColor = 0xFFE53935.toInt(),
        hasLeftMargin = true,
        hasTopMargin = true,
        isGrid = false,
        hasHoles = false
    ),
    PaperTemplateStyle(
        id = "yellow_legal",
        name = "Yellow Legal Pad",
        description = "Classic canary yellow legal pad with double-red left rule and punch holes",
        bgColor = 0xFFFFFDE7.toInt(),
        lineColor = 0xFF81D4FA.toInt(),
        marginLineColor = 0xFFE57373.toInt(),
        hasLeftMargin = true,
        hasTopMargin = true,
        isGrid = false,
        hasHoles = true
    ),
    PaperTemplateStyle(
        id = "math_grid",
        name = "Engineering Graph Sheet",
        description = "5mm squared grid paper for math calculations, physics notes, and engineering diagrams",
        bgColor = 0xFFF9FBE7.toInt(),
        lineColor = 0xFFC5E1A5.toInt(),
        marginLineColor = 0xFF7CB342.toInt(),
        hasLeftMargin = false,
        hasTopMargin = false,
        isGrid = true,
        hasHoles = false
    ),
    PaperTemplateStyle(
        id = "vintage_parchment",
        name = "Vintage Parchment",
        description = "Warm antique sepia paper texture for aesthetic assignments and poetry manuscripts",
        bgColor = 0xFFF7EEDD.toInt(),
        lineColor = 0xFFD7CCC8.toInt(),
        marginLineColor = 0xFF8D6E63.toInt(),
        hasLeftMargin = true,
        hasTopMargin = true,
        isGrid = false,
        hasHoles = false
    )
)

data class InkPenStyle(
    val id: String,
    val name: String,
    val colorInt: Int,
    val colorHex: String,
    val strokeWidth: Float,
    val inkBleed: Float,
    val isCursivePreferred: Boolean = false
)

val availableInkPens = listOf(
    InkPenStyle("blue_ballpoint", "Royal Blue Ballpoint", 0xFF0D47A1.toInt(), "#0D47A1", 2.2f, 0.4f),
    InkPenStyle("gel_dark_blue", "Dark Navy Gel Pen", 0xFF012B6B.toInt(), "#012B6B", 2.6f, 0.6f),
    InkPenStyle("fountain_black", "Jet Black Fountain Pen", 0xFF1A1A1A.toInt(), "#1A1A1A", 2.8f, 0.8f),
    InkPenStyle("pencil_graphite", "2B Graphite Pencil", 0xFF424242.toInt(), "#424242", 1.9f, 0.2f),
    InkPenStyle("red_teacher_pen", "Red Heading & Remark Pen", 0xFFC62828.toInt(), "#C62828", 2.5f, 0.5f),
    InkPenStyle("green_ink", "Emerald Green Ink", 0xFF1B5E20.toInt(), "#1B5E20", 2.4f, 0.5f)
)

enum class HandwritingFontPreset(val title: String, val description: String, val slant: Float, val letterSpacing: Float, val jitterAmount: Float) {
    NEAT_STUDENT("Neat Student Print", "Carefully written, legible handwritten print", 0.02f, 1.1f, 0.8f),
    FAST_CURSIVE("Fast Exam Cursive", "Natural running cursive with forward slant and connected letters", -0.22f, 1.05f, 1.4f),
    RELAXED_SCRIBBLE("Casual Lecture Notes", "Relaxed handwriting with slight baseline drift and natural wobble", 0.05f, 1.15f, 2.0f),
    ARCHITECT_DRAFT("Architect Clean Caps", "Sharp geometric strokes popular in engineering notes", 0.0f, 1.25f, 0.5f)
}

data class AssignmentStudentHeader(
    val studentName: String = "",
    val rollNumber: String = "",
    val subjectName: String = "",
    val assignmentTitle: String = "Assignment #1",
    val submissionDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date()),
    val teacherName: String = ""
)

// -------------------------------------------------------------
// MAIN ENTRY COMPOSABLE
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentOcrStudioScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Active Tab: 0 = Offline OCR Scanner, 1 = Handwritten Assignment Studio, 2 = Multi-Page Assignment PDF Exporter
    var selectedStudioTab by remember { mutableStateOf(0) }

    // Shared Text & OCR State
    var rawExtractedText by remember { mutableStateOf("") }
    var assignmentBodyText by remember {
        mutableStateOf(
            "Topic: Principles of Cellular Respiration\n\n" +
            "Question 1: Explain the role of ATP Synthase in the inner mitochondrial membrane.\n" +
            "Answer: ATP synthase is a multi-subunit protein complex that catalyzes the synthesis of ATP from ADP and inorganic phosphate (Pi). The proton gradient established by the electron transport chain drives the rotation of the F0 rotor, transferring conformational changes to the F1 catalytic head.\n\n" +
            "Question 2: What are the three stages of Glycolysis?\n" +
            "Answer: 1. Energy investment phase (phosphorylation of glucose)\n" +
            "2. Cleavage phase (splitting of Fructose-1,6-bisphosphate)\n" +
            "3. Energy payoff phase (production of NADH and net 2 ATP molecules)."
        )
    }

    // Handwriting Customization State
    var selectedPaperTemplate by remember { mutableStateOf(availablePaperTemplates[0]) }
    var selectedInkPen by remember { mutableStateOf(availableInkPens[0]) }
    var selectedFontPreset by remember { mutableStateOf(HandwritingFontPreset.NEAT_STUDENT) }
    var humanImperfectionLevel by remember { mutableStateOf(0.65f) }

    // Student Header State
    var studentHeader by remember {
        mutableStateOf(
            AssignmentStudentHeader(
                studentName = "Muhammad Tahir",
                rollNumber = "FA23-BCS-042",
                subjectName = "Biochemistry & Genetics",
                assignmentTitle = "Assignment #02: Cellular Energetics",
                submissionDate = SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Date()),
                teacherName = "Prof. Dr. Ahmed"
            )
        )
    }
    var includeHeaderOnFirstPage by remember { mutableStateOf(true) }

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
                    text = { Text("Photo-to-Text OCR", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    icon = { Icon(Icons.Default.DocumentScanner, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedStudioTab == 1,
                    onClick = { selectedStudioTab = 1 },
                    text = { Text("Text-to-Handwriting", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    icon = { Icon(Icons.Default.Draw, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedStudioTab == 2,
                    onClick = { selectedStudioTab = 2 },
                    text = { Text("Multi-Page PDF Sheet", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    icon = { Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
            }
        }

        // Active Tab Screen Body
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (selectedStudioTab) {
                0 -> OfflineOcrScannerSection(
                    context = context,
                    rawExtractedText = rawExtractedText,
                    onTextExtracted = { text ->
                        rawExtractedText = text
                        assignmentBodyText = text
                    },
                    onSendToHandwritingStudio = {
                        assignmentBodyText = rawExtractedText
                        selectedStudioTab = 1
                    }
                )
                1 -> TextToHandwritingStudioSection(
                    context = context,
                    assignmentBodyText = assignmentBodyText,
                    onTextChange = { assignmentBodyText = it },
                    selectedPaper = selectedPaperTemplate,
                    onPaperChange = { selectedPaperTemplate = it },
                    selectedPen = selectedInkPen,
                    onPenChange = { selectedInkPen = it },
                    selectedFontPreset = selectedFontPreset,
                    onFontPresetChange = { selectedFontPreset = it },
                    humanImperfectionLevel = humanImperfectionLevel,
                    onImperfectionChange = { humanImperfectionLevel = it },
                    studentHeader = studentHeader,
                    onHeaderChange = { studentHeader = it },
                    includeHeader = includeHeaderOnFirstPage,
                    onIncludeHeaderChange = { includeHeaderOnFirstPage = it },
                    onNavigateToPdfSheet = { selectedStudioTab = 2 }
                )
                2 -> MultiPageAssignmentPdfSection(
                    context = context,
                    assignmentBodyText = assignmentBodyText,
                    selectedPaper = selectedPaperTemplate,
                    selectedPen = selectedInkPen,
                    selectedFontPreset = selectedFontPreset,
                    humanImperfectionLevel = humanImperfectionLevel,
                    studentHeader = studentHeader,
                    includeHeader = includeHeaderOnFirstPage
                )
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 1: OFFLINE PHOTO-TO-TEXT OCR SCANNER
// -------------------------------------------------------------
@Composable
fun OfflineOcrScannerSection(
    context: Context,
    rawExtractedText: String,
    onTextExtracted: (String) -> Unit,
    onSendToHandwritingStudio: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var loadedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isOcrProcessing by remember { mutableStateOf(false) }
    var ocrConfidenceBlocksCount by remember { mutableStateOf(0) }
    var editableOcrText by remember { mutableStateOf(rawExtractedText) }

    LaunchedEffect(rawExtractedText) {
        editableOcrText = rawExtractedText
    }

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
                    withContext(Dispatchers.Main) {
                        loadedBitmap = bmp
                    }
                    if (bmp != null) {
                        runOfflineOcr(
                            context = context,
                            bitmap = bmp,
                            onStart = { isOcrProcessing = true },
                            onSuccess = { extracted, blockCount ->
                                isOcrProcessing = false
                                ocrConfidenceBlocksCount = blockCount
                                onTextExtracted(extracted)
                            },
                            onError = { error ->
                                isOcrProcessing = false
                                Toast.makeText(context, "OCR Error: $error", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    val speechToTextLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
        if (!spokenText.isNullOrBlank()) {
            val updated = if (editableOcrText.isBlank()) spokenText else "$editableOcrText\n$spokenText"
            editableOcrText = updated
            onTextExtracted(updated)
        }
    }

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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "📸 100% Offline Photo-to-Text OCR",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Extract text instantly from book pages, whiteboards & printed notes without internet",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        color = Color(0xFF00C853),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            "ON-DEVICE AI",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(46.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Pick Book / Note Photo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                                putExtra(RecognizerIntent.EXTRA_PROMPT, "Dictate your assignment notes...")
                            }
                            try {
                                speechToTextLauncher.launch(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Voice dictation not supported on this device", Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(46.dp)
                    ) {
                        Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Dictate", fontSize = 12.sp)
                    }
                }
            }
        }

        if (loadedBitmap != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
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
                        Text(
                            "Source Photo (${loadedBitmap!!.width}×${loadedBitmap!!.height} px)",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )

                        if (isOcrProcessing) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(modifier = Modifier.size(14.dp), color = Color(0xFF00E5FF), strokeWidth = 2.dp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Analyzing neural blocks...", color = Color(0xFF00E5FF), fontSize = 11.sp)
                            }
                        } else if (ocrConfidenceBlocksCount > 0) {
                            Surface(color = Color(0xFF00E676), shape = RoundedCornerShape(4.dp)) {
                                Text(
                                    "$ocrConfidenceBlocksCount Text Blocks Extracted",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Image(
                        bitmap = loadedBitmap!!.asImageBitmap(),
                        contentDescription = "Source Scan",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

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
                    Text(
                        "📝 Extracted / Typed Content",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("OCR Text", editableOcrText)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.ContentCopy, "Copy", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
                        }

                        IconButton(
                            onClick = {
                                editableOcrText = ""
                                onTextExtracted("")
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.DeleteSweep, "Clear", modifier = Modifier.size(18.dp), tint = Color.Gray)
                        }
                    }
                }

                OutlinedTextField(
                    value = editableOcrText,
                    onValueChange = {
                        editableOcrText = it
                        onTextExtracted(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 160.dp, max = 300.dp),
                    placeholder = { Text("Recognized text from your photo will appear here, or you can paste your assignment text...") },
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("⚡ Smart OCR Cleaners:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        item {
                            SuggestionChip(
                                onClick = {
                                    val cleaned = editableOcrText.replace(Regex("(?<!\n)\n(?!\n)"), " ")
                                    editableOcrText = cleaned
                                    onTextExtracted(cleaned)
                                },
                                label = { Text("Fix Broken Lines", fontSize = 11.sp) },
                                icon = { Icon(Icons.Default.WrapText, null, modifier = Modifier.size(14.dp)) }
                            )
                        }
                        item {
                            SuggestionChip(
                                onClick = {
                                    val cleaned = editableOcrText.split(". ").joinToString(". ") { sentence ->
                                        sentence.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.US) else it.toString() }
                                    }
                                    editableOcrText = cleaned
                                    onTextExtracted(cleaned)
                                },
                                label = { Text("Capitalize Sentences", fontSize = 11.sp) },
                                icon = { Icon(Icons.Default.FormatSize, null, modifier = Modifier.size(14.dp)) }
                            )
                        }
                        item {
                            SuggestionChip(
                                onClick = {
                                    val cleaned = editableOcrText
                                        .replace(Regex("^[•*\\-]\\s*", RegexOption.MULTILINE), "• ")
                                    editableOcrText = cleaned
                                    onTextExtracted(cleaned)
                                },
                                label = { Text("Format Bullets", fontSize = 11.sp) },
                                icon = { Icon(Icons.Default.FormatListBulleted, null, modifier = Modifier.size(14.dp)) }
                            )
                        }
                    }
                }

                val wordsCount = if (editableOcrText.isBlank()) 0 else editableOcrText.trim().split(Regex("\\s+")).size
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Words: $wordsCount", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Characters: ${editableOcrText.length}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Button(
            onClick = onSendToHandwritingStudio,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("send_to_handwriting_studio_btn"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Default.Draw, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Convert to Realistic Handwritten Assignment ✍️", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

// -------------------------------------------------------------
// MODULE 2: TEXT-TO-HANDWRITING ASSIGNMENT STUDIO
// -------------------------------------------------------------
@Composable
fun TextToHandwritingStudioSection(
    context: Context,
    assignmentBodyText: String,
    onTextChange: (String) -> Unit,
    selectedPaper: PaperTemplateStyle,
    onPaperChange: (PaperTemplateStyle) -> Unit,
    selectedPen: InkPenStyle,
    onPenChange: (InkPenStyle) -> Unit,
    selectedFontPreset: HandwritingFontPreset,
    onFontPresetChange: (HandwritingFontPreset) -> Unit,
    humanImperfectionLevel: Float,
    onImperfectionChange: (Float) -> Unit,
    studentHeader: AssignmentStudentHeader,
    onHeaderChange: (AssignmentStudentHeader) -> Unit,
    includeHeader: Boolean,
    onIncludeHeaderChange: (Boolean) -> Unit,
    onNavigateToPdfSheet: () -> Unit
) {
    var showHeaderEditorDialog by remember { mutableStateOf(false) }

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
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "🎓 Student Assignment Header",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            if (includeHeader) "${studentHeader.studentName} | ${studentHeader.rollNumber}" else "Header disabled",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Switch(
                            checked = includeHeader,
                            onCheckedChange = onIncludeHeaderChange
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(onClick = { showHeaderEditorDialog = true }) {
                            Icon(Icons.Default.Edit, "Edit Header", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(selectedPaper.bgColor)),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "📄 Live Page Preview (${selectedPaper.name})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFF37474F)
                    )

                    Surface(
                        color = Color(selectedPen.colorInt),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            selectedPen.name,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(selectedPaper.bgColor))
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawRealisticNotebookPage(
                            drawScope = this,
                            pageText = assignmentBodyText,
                            pageIndex = 1,
                            totalPages = calculateEstimatedPages(assignmentBodyText),
                            paperStyle = selectedPaper,
                            inkStyle = selectedPen,
                            fontPreset = selectedFontPreset,
                            humanImperfection = humanImperfectionLevel,
                            header = if (includeHeader) studentHeader else null
                        )
                    }
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                "📜 Select Ruled Paper Type:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(availablePaperTemplates) { paper ->
                    val isSelected = paper.id == selectedPaper.id
                    Card(
                        onClick = { onPaperChange(paper) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(paper.bgColor)
                        ),
                        border = BorderStroke(
                            width = if (isSelected) 2.5.dp else 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.4f)
                        ),
                        modifier = Modifier.width(135.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                paper.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                if (paper.isGrid) "Grid Ruled" else "Standard Ruled",
                                fontSize = 9.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                "✒️ Select Pen & Ink Style:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(availableInkPens) { pen ->
                    val isSelected = pen.id == selectedPen.id
                    FilterChip(
                        selected = isSelected,
                        onClick = { onPenChange(pen) },
                        label = { Text(pen.name, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color(pen.colorInt))
                            )
                        }
                    )
                }
            }
        }

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
                    "✍️ Handwriting Style & Realism Controls:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    HandwritingFontPreset.values().forEach { preset ->
                        val isSelected = preset == selectedFontPreset
                        FilterChip(
                            selected = isSelected,
                            onClick = { onFontPresetChange(preset) },
                            label = { Text(preset.title.split(" ").first(), fontSize = 10.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Human Hand Realism & Flaws", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        Text("${(humanImperfectionLevel * 100).toInt()}% Natural Drift", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Slider(
                        value = humanImperfectionLevel,
                        onValueChange = onImperfectionChange,
                        valueRange = 0.1f..1.0f,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "Adds micro-jitter to character heights, natural baseline tilt, and subtle ink pressure variations.",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

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
                    Text(
                        "📄 Assignment Body Text",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "~${calculateEstimatedPages(assignmentBodyText)} Page(s)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00897B)
                    )
                }

                OutlinedTextField(
                    value = assignmentBodyText,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 140.dp, max = 260.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        Button(
            onClick = onNavigateToPdfSheet,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("preview_assignment_pages_btn"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Default.PictureAsPdf, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Preview & Export Multi-Page A4 PDF 📄", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }

    if (showHeaderEditorDialog) {
        var tempName by remember { mutableStateOf(studentHeader.studentName) }
        var tempRoll by remember { mutableStateOf(studentHeader.rollNumber) }
        var tempSubject by remember { mutableStateOf(studentHeader.subjectName) }
        var tempTitle by remember { mutableStateOf(studentHeader.assignmentTitle) }
        var tempDate by remember { mutableStateOf(studentHeader.submissionDate) }
        var tempTeacher by remember { mutableStateOf(studentHeader.teacherName) }

        AlertDialog(
            onDismissRequest = { showHeaderEditorDialog = false },
            title = { Text("Edit Student Header Info", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(value = tempName, onValueChange = { tempName = it }, label = { Text("Student Full Name") }, singleLine = true)
                    OutlinedTextField(value = tempRoll, onValueChange = { tempRoll = it }, label = { Text("Roll Number / Registration No.") }, singleLine = true)
                    OutlinedTextField(value = tempSubject, onValueChange = { tempSubject = it }, label = { Text("Subject / Course Name") }, singleLine = true)
                    OutlinedTextField(value = tempTitle, onValueChange = { tempTitle = it }, label = { Text("Assignment Title / Number") }, singleLine = true)
                    OutlinedTextField(value = tempDate, onValueChange = { tempDate = it }, label = { Text("Submission Date") }, singleLine = true)
                    OutlinedTextField(value = tempTeacher, onValueChange = { tempTeacher = it }, label = { Text("Teacher / Professor Name") }, singleLine = true)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onHeaderChange(
                            AssignmentStudentHeader(
                                studentName = tempName,
                                rollNumber = tempRoll,
                                subjectName = tempSubject,
                                assignmentTitle = tempTitle,
                                submissionDate = tempDate,
                                teacherName = tempTeacher
                            )
                        )
                        showHeaderEditorDialog = false
                    }
                ) {
                    Text("Save Header")
                }
            },
            dismissButton = {
                TextButton(onClick = { showHeaderEditorDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 3: MULTI-PAGE ASSIGNMENT PDF & EXPORTER
// -------------------------------------------------------------
@Composable
fun MultiPageAssignmentPdfSection(
    context: Context,
    assignmentBodyText: String,
    selectedPaper: PaperTemplateStyle,
    selectedPen: InkPenStyle,
    selectedFontPreset: HandwritingFontPreset,
    humanImperfectionLevel: Float,
    studentHeader: AssignmentStudentHeader,
    includeHeader: Boolean
) {
    val coroutineScope = rememberCoroutineScope()
    var isExportingPdf by remember { mutableStateOf(false) }
    var isExportingImages by remember { mutableStateOf(false) }

    val pages = remember(assignmentBodyText, includeHeader) {
        paginateTextForAssignment(
            fullText = assignmentBodyText,
            hasHeaderOnFirstPage = includeHeader
        )
    }

    var selectedPreviewPageIndex by remember { mutableStateOf(0) }

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
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "📚 Multi-Page Assignment Document",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Total ${pages.size} A4 Handwritten Page(s) Generated",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "300 DPI A4",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(pages.indices.toList()) { pageIdx ->
                        val isSelected = pageIdx == selectedPreviewPageIndex
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedPreviewPageIndex = pageIdx },
                            label = { Text("Page ${pageIdx + 1}", fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            leadingIcon = {
                                Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp))
                            }
                        )
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(selectedPaper.bgColor)),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "📄 Page ${selectedPreviewPageIndex + 1} of ${pages.size}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.Black
                    )

                    Text(
                        "${selectedPaper.name} • ${selectedPen.name}",
                        fontSize = 11.sp,
                        color = Color.DarkGray
                    )
                }

                val activePageText = pages.getOrElse(selectedPreviewPageIndex) { "" }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(selectedPaper.bgColor))
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawRealisticNotebookPage(
                            drawScope = this,
                            pageText = activePageText,
                            pageIndex = selectedPreviewPageIndex + 1,
                            totalPages = pages.size,
                            paperStyle = selectedPaper,
                            inkStyle = selectedPen,
                            fontPreset = selectedFontPreset,
                            humanImperfection = humanImperfectionLevel,
                            header = if (includeHeader && selectedPreviewPageIndex == 0) studentHeader else null
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    isExportingPdf = true
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            val savedPath = exportAssignmentToPdf(
                                context = context,
                                pages = pages,
                                paperStyle = selectedPaper,
                                inkStyle = selectedPen,
                                fontPreset = selectedFontPreset,
                                humanImperfection = humanImperfectionLevel,
                                header = if (includeHeader) studentHeader else null
                            )
                            withContext(Dispatchers.Main) {
                                isExportingPdf = false
                                if (savedPath != null) {
                                    Toast.makeText(context, "Saved Multi-Page PDF to Downloads! ($savedPath)", Toast.LENGTH_LONG).show()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                isExportingPdf = false
                                Toast.makeText(context, "PDF Export error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("export_assignment_pdf_btn"),
                shape = RoundedCornerShape(12.dp),
                enabled = !isExportingPdf,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                if (isExportingPdf) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Generating PDF...", fontSize = 12.sp)
                } else {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save A4 PDF", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            OutlinedButton(
                onClick = {
                    isExportingImages = true
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            pages.forEachIndexed { idx, pageText ->
                                val bmp = renderSinglePageBitmap(
                                    pageText = pageText,
                                    pageIndex = idx + 1,
                                    totalPages = pages.size,
                                    paperStyle = selectedPaper,
                                    inkStyle = selectedPen,
                                    fontPreset = selectedFontPreset,
                                    humanImperfection = humanImperfectionLevel,
                                    header = if (includeHeader && idx == 0) studentHeader else null
                                )
                                saveAssignmentPageToGallery(
                                    context = context,
                                    bitmap = bmp,
                                    pageNumber = idx + 1
                                )
                            }

                            withContext(Dispatchers.Main) {
                                isExportingImages = false
                                Toast.makeText(context, "Saved ${pages.size} Handwritten Pages to Gallery!", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                isExportingImages = false
                                Toast.makeText(context, "Image Export error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("export_assignment_images_btn"),
                shape = RoundedCornerShape(12.dp),
                enabled = !isExportingImages
            ) {
                if (isExportingImages) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Saving Pages...", fontSize = 12.sp)
                } else {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save JPEGs", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// REALISTIC NOTEBOOK PAGE RENDERING ENGINE (JETPACK CANVAS)
// -------------------------------------------------------------

fun drawRealisticNotebookPage(
    drawScope: DrawScope,
    pageText: String,
    pageIndex: Int,
    totalPages: Int,
    paperStyle: PaperTemplateStyle,
    inkStyle: InkPenStyle,
    fontPreset: HandwritingFontPreset,
    humanImperfection: Float,
    header: AssignmentStudentHeader?
) {
    with(drawScope) {
        val w = size.width
        val h = size.height

        // 1. Draw Paper Base
        drawRect(
            color = Color(paperStyle.bgColor),
            topLeft = Offset.Zero,
            size = Size(w, h)
        )

        // 2. Draw Binder Hole Punches (Left edge)
        if (paperStyle.hasHoles) {
            val holeRadius = 5.dp.toPx()
            val holeX = 14.dp.toPx()
            val holeY1 = h * 0.18f
            val holeY2 = h * 0.50f
            val holeY3 = h * 0.82f

            listOf(holeY1, holeY2, holeY3).forEach { y ->
                drawCircle(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    radius = holeRadius,
                    center = Offset(holeX, y)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.9f),
                    radius = holeRadius * 0.85f,
                    center = Offset(holeX, y)
                )
            }
        }

        // 3. Draw Ruled Lines
        val leftMarginX = if (paperStyle.hasLeftMargin) w * 0.16f else w * 0.06f
        val rightMarginX = w * 0.94f
        val topMarginY = if (header != null) h * 0.22f else h * 0.10f
        val lineSpacingPx = 18.dp.toPx()

        if (paperStyle.isGrid) {
            val gridSize = 14.dp.toPx()
            var curX = 0f
            while (curX < w) {
                drawLine(
                    color = Color(paperStyle.lineColor).copy(alpha = 0.45f),
                    start = Offset(curX, 0f),
                    end = Offset(curX, h),
                    strokeWidth = 1f
                )
                curX += gridSize
            }
            var curY = 0f
            while (curY < h) {
                drawLine(
                    color = Color(paperStyle.lineColor).copy(alpha = 0.45f),
                    start = Offset(0f, curY),
                    end = Offset(w, curY),
                    strokeWidth = 1f
                )
                curY += gridSize
            }
        } else {
            var lineY = topMarginY
            while (lineY < h - 20.dp.toPx()) {
                drawLine(
                    color = Color(paperStyle.lineColor).copy(alpha = 0.65f),
                    start = Offset(0f, lineY),
                    end = Offset(w, lineY),
                    strokeWidth = 1.2f
                )
                lineY += lineSpacingPx
            }

            if (paperStyle.hasLeftMargin) {
                drawLine(
                    color = Color(paperStyle.marginLineColor).copy(alpha = 0.75f),
                    start = Offset(leftMarginX, 0f),
                    end = Offset(leftMarginX, h),
                    strokeWidth = 2f
                )
            }
        }

        // 4. Native Canvas for Realistic Handwritten Text
        drawContext.canvas.nativeCanvas.let { native ->
            if (header != null) {
                val headerPaint = Paint().apply {
                    color = inkStyle.colorInt
                    textSize = 10.sp.toPx()
                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    isAntiAlias = true
                }
                val valPaint = Paint().apply {
                    color = inkStyle.colorInt
                    textSize = 10.sp.toPx()
                    typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                    isAntiAlias = true
                }

                val headX = leftMarginX + 8.dp.toPx()
                var headY = h * 0.05f

                native.drawText("Name: ${header.studentName}", headX, headY, headerPaint)
                native.drawText("Roll No: ${header.rollNumber}", w * 0.60f, headY, headerPaint)
                headY += 12.dp.toPx()
                native.drawText("Subject: ${header.subjectName}", headX, headY, valPaint)
                native.drawText("Date: ${header.submissionDate}", w * 0.60f, headY, valPaint)
                headY += 12.dp.toPx()
                native.drawText("Topic: ${header.assignmentTitle}", headX, headY, headerPaint)

                val dividerPaint = Paint().apply {
                    color = paperStyle.lineColor
                    strokeWidth = 2f
                }
                native.drawLine(headX, headY + 6.dp.toPx(), rightMarginX, headY + 6.dp.toPx(), dividerPaint)
            }

            val pageNumPaint = Paint().apply {
                color = Color.Gray.toArgb()
                textSize = 9.sp.toPx()
                typeface = Typeface.DEFAULT
                isAntiAlias = true
            }
            native.drawText("Page $pageIndex of $totalPages", w * 0.78f, h - 8.dp.toPx(), pageNumPaint)

            val textPaint = Paint().apply {
                color = inkStyle.colorInt
                textSize = 12.sp.toPx()
                isAntiAlias = true
                strokeWidth = inkStyle.strokeWidth
                typeface = when (fontPreset) {
                    HandwritingFontPreset.NEAT_STUDENT -> Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                    HandwritingFontPreset.FAST_CURSIVE -> Typeface.create(Typeface.SERIF, Typeface.ITALIC)
                    HandwritingFontPreset.RELAXED_SCRIBBLE -> Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                    HandwritingFontPreset.ARCHITECT_DRAFT -> Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
                }
            }

            val random = Random(pageIndex * 1000L)
            val textStartX = leftMarginX + 8.dp.toPx()
            var currentY = topMarginY + lineSpacingPx * 0.82f
            val maxTextWidth = rightMarginX - textStartX

            val lines = pageText.split("\n")
            for (rawLine in lines) {
                if (rawLine.isBlank()) {
                    currentY += lineSpacingPx * 0.7f
                    continue
                }

                val words = rawLine.split(" ")
                var currentLineBuffer = StringBuilder()

                for (word in words) {
                    val testLine = if (currentLineBuffer.isEmpty()) word else "$currentLineBuffer $word"
                    val measuredWidth = textPaint.measureText(testLine)

                    if (measuredWidth > maxTextWidth && currentLineBuffer.isNotEmpty()) {
                        drawHandwrittenLineWithHumanFlaws(
                            canvas = native,
                            lineText = currentLineBuffer.toString(),
                            startX = textStartX,
                            baselineY = currentY,
                            paint = textPaint,
                            fontPreset = fontPreset,
                            humanImperfection = humanImperfection,
                            random = random
                        )
                        currentY += lineSpacingPx
                        currentLineBuffer = StringBuilder(word)
                    } else {
                        currentLineBuffer = StringBuilder(testLine)
                    }
                }

                if (currentLineBuffer.isNotEmpty()) {
                    drawHandwrittenLineWithHumanFlaws(
                        canvas = native,
                        lineText = currentLineBuffer.toString(),
                        startX = textStartX,
                        baselineY = currentY,
                        paint = textPaint,
                        fontPreset = fontPreset,
                        humanImperfection = humanImperfection,
                        random = random
                    )
                    currentY += lineSpacingPx
                }

                if (currentY > h - 25.dp.toPx()) break
            }
        }
    }
}

// -------------------------------------------------------------
// ORGANIC HUMAN FLAW & JITTER INJECTION FOR TEXT
// -------------------------------------------------------------

fun drawHandwrittenLineWithHumanFlaws(
    canvas: Canvas,
    lineText: String,
    startX: Float,
    baselineY: Float,
    paint: Paint,
    fontPreset: HandwritingFontPreset,
    humanImperfection: Float,
    random: Random
) {
    var curX = startX
    val lineTiltAngle = (random.nextFloat() - 0.5f) * 0.015f * humanImperfection

    canvas.save()
    canvas.rotate(lineTiltAngle * 57.29f, startX, baselineY)

    for (i in lineText.indices) {
        val charStr = lineText[i].toString()
        val charWidth = paint.measureText(charStr)

        val charJitterY = (random.nextFloat() - 0.5f) * 3f * humanImperfection
        val charJitterX = (random.nextFloat() - 0.5f) * 1.5f * humanImperfection

        canvas.drawText(charStr, curX + charJitterX, baselineY + charJitterY, paint)
        curX += charWidth + fontPreset.letterSpacing
    }

    canvas.restore()
}

// -------------------------------------------------------------
// TEXT PAGINATION UTILITY
// -------------------------------------------------------------

fun calculateEstimatedPages(text: String): Int {
    if (text.isBlank()) return 1
    val lines = text.split("\n")
    val totalEstimatedLines = lines.sumOf { if (it.isBlank()) 1 else max(1, it.length / 50) }
    return max(1, (totalEstimatedLines + 18) / 20)
}

fun paginateTextForAssignment(fullText: String, hasHeaderOnFirstPage: Boolean): List<String> {
    if (fullText.isBlank()) return listOf("Type or paste your assignment text here...")

    val pages = mutableListOf<String>()
    val lines = fullText.split("\n")

    val maxLinesFirstPage = if (hasHeaderOnFirstPage) 16 else 22
    val maxLinesOtherPages = 22

    var currentPageBuffer = mutableListOf<String>()
    var currentLinesCount = 0
    var isFirstPage = true

    for (line in lines) {
        val lineWeight = if (line.isBlank()) 1 else max(1, line.length / 50)
        val limit = if (isFirstPage) maxLinesFirstPage else maxLinesOtherPages

        if (currentLinesCount + lineWeight > limit && currentPageBuffer.isNotEmpty()) {
            pages.add(currentPageBuffer.joinToString("\n"))
            currentPageBuffer = mutableListOf(line)
            currentLinesCount = lineWeight
            isFirstPage = false
        } else {
            currentPageBuffer.add(line)
            currentLinesCount += lineWeight
        }
    }

    if (currentPageBuffer.isNotEmpty()) {
        pages.add(currentPageBuffer.joinToString("\n"))
    }

    return if (pages.isEmpty()) listOf(fullText) else pages
}

// -------------------------------------------------------------
// BITMAP & PDF EXPORT GENERATORS (A4 300 DPI)
// -------------------------------------------------------------

fun renderSinglePageBitmap(
    pageText: String,
    pageIndex: Int,
    totalPages: Int,
    paperStyle: PaperTemplateStyle,
    inkStyle: InkPenStyle,
    fontPreset: HandwritingFontPreset,
    humanImperfection: Float,
    header: AssignmentStudentHeader?
): Bitmap {
    val a4WidthPx = 1240
    val a4HeightPx = 1754

    val bitmap = Bitmap.createBitmap(a4WidthPx, a4HeightPx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val bgPaint = Paint().apply { color = paperStyle.bgColor }
    canvas.drawRect(0f, 0f, a4WidthPx.toFloat(), a4HeightPx.toFloat(), bgPaint)

    if (paperStyle.hasHoles) {
        val holeRadius = 24f
        val holeX = 60f
        val holePaint = Paint().apply {
            color = Color.LightGray.toArgb()
            isAntiAlias = true
        }
        val innerHolePaint = Paint().apply {
            color = Color.White.toArgb()
            isAntiAlias = true
        }
        listOf(a4HeightPx * 0.18f, a4HeightPx * 0.50f, a4HeightPx * 0.82f).forEach { y ->
            canvas.drawCircle(holeX, y, holeRadius, holePaint)
            canvas.drawCircle(holeX, y, holeRadius * 0.82f, innerHolePaint)
        }
    }

    val leftMarginX = if (paperStyle.hasLeftMargin) a4WidthPx * 0.15f else a4WidthPx * 0.06f
    val rightMarginX = a4WidthPx * 0.94f
    val topMarginY = if (header != null) a4HeightPx * 0.20f else a4HeightPx * 0.08f
    val lineSpacingPx = 68f

    val linePaint = Paint().apply {
        color = paperStyle.lineColor
        strokeWidth = 2f
        alpha = 180
    }
    val marginPaint = Paint().apply {
        color = paperStyle.marginLineColor
        strokeWidth = 4f
        alpha = 200
    }

    if (paperStyle.isGrid) {
        val gridSize = 45f
        var curX = 0f
        while (curX < a4WidthPx) {
            canvas.drawLine(curX, 0f, curX, a4HeightPx.toFloat(), linePaint)
            curX += gridSize
        }
        var curY = 0f
        while (curY < a4HeightPx) {
            canvas.drawLine(0f, curY, a4WidthPx.toFloat(), curY, linePaint)
            curY += gridSize
        }
    } else {
        var lineY = topMarginY
        while (lineY < a4HeightPx - 60f) {
            canvas.drawLine(0f, lineY, a4WidthPx.toFloat(), lineY, linePaint)
            lineY += lineSpacingPx
        }
        if (paperStyle.hasLeftMargin) {
            canvas.drawLine(leftMarginX, 0f, leftMarginX, a4HeightPx.toFloat(), marginPaint)
        }
    }

    if (header != null) {
        val headerTitlePaint = Paint().apply {
            color = inkStyle.colorInt
            textSize = 34f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            isAntiAlias = true
        }
        val headerSubPaint = Paint().apply {
            color = inkStyle.colorInt
            textSize = 30f
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }

        val headX = leftMarginX + 30f
        var headY = a4HeightPx * 0.05f

        canvas.drawText("Name: ${header.studentName}", headX, headY, headerTitlePaint)
        canvas.drawText("Roll No: ${header.rollNumber}", a4WidthPx * 0.60f, headY, headerTitlePaint)
        headY += 45f
        canvas.drawText("Subject: ${header.subjectName}", headX, headY, headerSubPaint)
        canvas.drawText("Date: ${header.submissionDate}", a4WidthPx * 0.60f, headY, headerSubPaint)
        headY += 45f
        canvas.drawText("Topic: ${header.assignmentTitle}", headX, headY, headerTitlePaint)

        canvas.drawLine(headX, headY + 20f, rightMarginX, headY + 20f, linePaint)
    }

    val pageNumPaint = Paint().apply {
        color = Color.Gray.toArgb()
        textSize = 28f
        isAntiAlias = true
    }
    canvas.drawText("Page $pageIndex of $totalPages", a4WidthPx * 0.80f, a4HeightPx - 35f, pageNumPaint)

    val bodyPaint = Paint().apply {
        color = inkStyle.colorInt
        textSize = 36f
        isAntiAlias = true
        strokeWidth = inkStyle.strokeWidth * 1.5f
        typeface = when (fontPreset) {
            HandwritingFontPreset.NEAT_STUDENT -> Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            HandwritingFontPreset.FAST_CURSIVE -> Typeface.create(Typeface.SERIF, Typeface.ITALIC)
            HandwritingFontPreset.RELAXED_SCRIBBLE -> Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            HandwritingFontPreset.ARCHITECT_DRAFT -> Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
        }
    }

    val random = Random(pageIndex * 1000L)
    val textStartX = leftMarginX + 30f
    var currentY = topMarginY + lineSpacingPx * 0.80f
    val maxTextWidth = rightMarginX - textStartX

    val lines = pageText.split("\n")
    for (rawLine in lines) {
        if (rawLine.isBlank()) {
            currentY += lineSpacingPx * 0.6f
            continue
        }

        val words = rawLine.split(" ")
        var currentLineBuffer = StringBuilder()

        for (word in words) {
            val testLine = if (currentLineBuffer.isEmpty()) word else "$currentLineBuffer $word"
            val measuredWidth = bodyPaint.measureText(testLine)

            if (measuredWidth > maxTextWidth && currentLineBuffer.isNotEmpty()) {
                drawHandwrittenLineWithHumanFlaws(
                    canvas = canvas,
                    lineText = currentLineBuffer.toString(),
                    startX = textStartX,
                    baselineY = currentY,
                    paint = bodyPaint,
                    fontPreset = fontPreset,
                    humanImperfection = humanImperfection,
                    random = random
                )
                currentY += lineSpacingPx
                currentLineBuffer = StringBuilder(word)
            } else {
                currentLineBuffer = StringBuilder(testLine)
            }
        }

        if (currentLineBuffer.isNotEmpty()) {
            drawHandwrittenLineWithHumanFlaws(
                canvas = canvas,
                lineText = currentLineBuffer.toString(),
                startX = textStartX,
                baselineY = currentY,
                paint = bodyPaint,
                fontPreset = fontPreset,
                humanImperfection = humanImperfection,
                random = random
            )
            currentY += lineSpacingPx
        }

        if (currentY > a4HeightPx - 80f) break
    }

    return bitmap
}

fun exportAssignmentToPdf(
    context: Context,
    pages: List<String>,
    paperStyle: PaperTemplateStyle,
    inkStyle: InkPenStyle,
    fontPreset: HandwritingFontPreset,
    humanImperfection: Float,
    header: AssignmentStudentHeader?
): String? {
    val pdfDocument = PdfDocument()
    val pageWidth = 595
    val pageHeight = 842

    pages.forEachIndexed { index, pageText ->
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, index + 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val pageBitmap = renderSinglePageBitmap(
            pageText = pageText,
            pageIndex = index + 1,
            totalPages = pages.size,
            paperStyle = paperStyle,
            inkStyle = inkStyle,
            fontPreset = fontPreset,
            humanImperfection = humanImperfection,
            header = if (index == 0) header else null
        )

        val dstRect = Rect(0, 0, pageWidth, pageHeight)
        page.canvas.drawBitmap(pageBitmap, null, dstRect, null)
        pdfDocument.finishPage(page)
    }

    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val fileName = "Handwritten_Assignment_${timeStamp}.pdf"

    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, fileName)
    val outputStream = FileOutputStream(file)
    pdfDocument.writeTo(outputStream)
    outputStream.flush()
    outputStream.close()
    pdfDocument.close()

    return file.absolutePath
}

fun saveAssignmentPageToGallery(
    context: Context,
    bitmap: Bitmap,
    pageNumber: Int
): String? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val fileName = "Handwritten_Page_${pageNumber}_${timeStamp}.jpg"
    val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val studentKitDir = File(picturesDir, "StudentKit_Assignments")
    if (!studentKitDir.exists()) studentKitDir.mkdirs()

    val file = File(studentKitDir, fileName)
    val out = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
    out.flush()
    out.close()

    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    intent.data = Uri.fromFile(file)
    context.sendBroadcast(intent)

    return file.absolutePath
}

// -------------------------------------------------------------
// OFFLINE ML KIT OCR RUNNER
// -------------------------------------------------------------

fun runOfflineOcr(
    context: Context,
    bitmap: Bitmap,
    onStart: () -> Unit,
    onSuccess: (extractedText: String, blockCount: Int) -> Unit,
    onError: (String) -> Unit
) {
    onStart()
    try {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val fullText = visionText.text
                val blockCount = visionText.textBlocks.size
                if (fullText.isBlank()) {
                    onSuccess("No readable text found in this photo. Please try a clearer scan.", 0)
                } else {
                    onSuccess(fullText, blockCount)
                }
            }
            .addOnFailureListener { e ->
                onError(e.localizedMessage ?: "Failed to recognize text")
            }
    } catch (e: Exception) {
        onError(e.localizedMessage ?: "OCR initialization error")
    }
}
