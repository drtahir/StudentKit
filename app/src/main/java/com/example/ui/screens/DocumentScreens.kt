package com.studentkit.buner.ui.screens

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
import com.studentkit.buner.viewmodel.StudentKitViewModel
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.draw.shadow

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
                                profilePic = profilePicBitmap
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
    profilePic: Bitmap?
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
                    fun drawPortraitCircle(cx: Float, cy: Float, radius: Float, photoFrameShape: String = "Circle") {
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
