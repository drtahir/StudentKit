package com.example

import android.os.Bundle
import android.app.Activity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.Screen
import com.example.viewmodel.StudentKitViewModel

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()
        setContent {
            val viewModel: StudentKitViewModel = viewModel()
            val isDarkThemeSetting by viewModel.isDarkTheme.collectAsState()
            val darkTheme = when (isDarkThemeSetting) {
                true -> true
                false -> false
                null -> isSystemInDarkTheme()
            }
            MyApplicationTheme(darkTheme = darkTheme) {
                MainAppContainer(viewModel = viewModel)
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    SideEffect {
        val window = (context as? Activity)?.window
        if (window != null) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val controller = WindowCompat.getInsetsController(window, window.decorView)
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.systemBars())
        }
    }

    val currentScreen by viewModel.currentScreen.collectAsState()
    var selectedTabItem by remember { mutableStateOf(0) } // 0=Home, 1=Finance, 2=Docs, 3=Tools, 4=Study, 5=Security
    var activeCategorySheet by remember { mutableStateOf<CategorySheetType?>(null) }

    // Sync selected bottom tab item when currentScreen changes
    LaunchedEffect(currentScreen) {
        selectedTabItem = when (currentScreen) {
            is Screen.Dashboard, is Screen.About, is Screen.Settings -> 0
            is Screen.ExpenseTracker, is Screen.IncomeTracker, is Screen.UtilityBills,
            is Screen.ZakatCalculator, is Screen.BcCommittee, is Screen.BcCommitteeDetails,
            is Screen.LoanTracker, is Screen.SavingsGoals, is Screen.FinanceReportAndBackup -> 1
            is Screen.CvBuilder, is Screen.ImageToPdf, is Screen.ImageToXls, is Screen.ImageToWord,
            is Screen.DocumentScanner, is Screen.IdCardScanner, is Screen.PassportScanner,
            is Screen.PdfTools, is Screen.InvoiceGenerator, is Screen.SignaturePad -> 2
            is Screen.Calculator, is Screen.UnitConverter, is Screen.QrGenerator,
            is Screen.QrScanner, is Screen.PasswordManager,
            is Screen.ImageTools, is Screen.AgeCalculator, is Screen.IntruderGuard, is Screen.WatermarkStudio, is Screen.BackgroundEraser,
            is Screen.FileEncryptor, is Screen.HiddenLocker, is Screen.Steganography, is Screen.Steganalysis, is Screen.ImageEnhancer, is Screen.Teleprompter -> 3
            is Screen.Notes, is Screen.StudyTimer, is Screen.Timetable, is Screen.BmiCalculator,
            is Screen.GpaCalculator, is Screen.IvCalculator, is Screen.DosageCalculator,
            is Screen.GfrCalculator, is Screen.AnatomyAtlas, is Screen.PharmacyExam, is Screen.NursingExam, is Screen.HajjMedicalPrep, is Screen.MoavineenHujjajPrep,
            is Screen.IslamicHub -> 4
            is Screen.SecurityHub, is Screen.PinVault, is Screen.AppLock, is Screen.CalculatorVault,
            is Screen.PhotoVault, is Screen.PrivateNotes, is Screen.SecureDelete, is Screen.PermissionAuditor,
            is Screen.WifiScanner, is Screen.UssdCheck, is Screen.ThermalPrinterManager, is Screen.BiometricManagerScreen -> 5
            else -> 0
        }
    }

    // Handle standard android system back navigation press
    BackHandler(enabled = currentScreen != Screen.Dashboard || activeCategorySheet != null) {
        if (activeCategorySheet != null) {
            activeCategorySheet = null
        } else {
            viewModel.navigateBack()
        }
    }

    Scaffold(
        bottomBar = {
            if (currentScreen != Screen.IslamicHub) {
                NavigationBar(
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    NavigationBarItem(
                        selected = selectedTabItem == 0 && activeCategorySheet == null,
                        onClick = {
                            selectedTabItem = 0
                            activeCategorySheet = null
                            viewModel.navigateTo(Screen.Dashboard)
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home", fontSize = 11.sp) },
                        modifier = Modifier.testTag("nav_home")
                    )
                    NavigationBarItem(
                        selected = selectedTabItem == 1 || activeCategorySheet == CategorySheetType.FINANCE,
                        onClick = {
                            selectedTabItem = 1
                            activeCategorySheet = CategorySheetType.FINANCE
                        },
                        icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = "Finance") },
                        label = { Text("Finance", fontSize = 11.sp) },
                        modifier = Modifier.testTag("nav_finance")
                    )
                    NavigationBarItem(
                        selected = selectedTabItem == 2 || activeCategorySheet == CategorySheetType.DOCS,
                        onClick = {
                            selectedTabItem = 2
                            activeCategorySheet = CategorySheetType.DOCS
                        },
                        icon = { Icon(Icons.Default.Folder, contentDescription = "Documents") },
                        label = { Text("Docs", fontSize = 11.sp) },
                        modifier = Modifier.testTag("nav_docs")
                    )
                    NavigationBarItem(
                        selected = selectedTabItem == 3 || activeCategorySheet == CategorySheetType.TOOLS,
                        onClick = {
                            selectedTabItem = 3
                            activeCategorySheet = CategorySheetType.TOOLS
                        },
                        icon = { Icon(Icons.Default.Build, contentDescription = "Tools") },
                        label = { Text("Tools", fontSize = 11.sp) },
                        modifier = Modifier.testTag("nav_tools")
                    )
                    NavigationBarItem(
                        selected = selectedTabItem == 4 || activeCategorySheet == CategorySheetType.STUDY,
                        onClick = {
                            selectedTabItem = 4
                            activeCategorySheet = CategorySheetType.STUDY
                        },
                        icon = { Icon(Icons.Default.Book, contentDescription = "Study & Health") },
                        label = { Text("Study", fontSize = 11.sp) },
                        modifier = Modifier.testTag("nav_study")
                    )
                    NavigationBarItem(
                        selected = selectedTabItem == 5 || activeCategorySheet == CategorySheetType.SECURITY,
                        onClick = {
                            selectedTabItem = 5
                            activeCategorySheet = CategorySheetType.SECURITY
                        },
                        icon = { Icon(Icons.Default.Security, contentDescription = "Security") },
                        label = { Text("Security", fontSize = 11.sp) },
                        modifier = Modifier.testTag("nav_security")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    slideInHorizontally { width -> width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> -width } + fadeOut()
                },
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    is Screen.Dashboard -> DashboardScreen(viewModel = viewModel)

                    // --- FINANCE MODULES ---
                    is Screen.ExpenseTracker -> FinanceHubScreen(viewModel = viewModel, title = "Expense Tracker") {
                        ExpenseTrackerScreen(viewModel = viewModel)
                    }
                    is Screen.IncomeTracker -> FinanceHubScreen(viewModel = viewModel, title = "Income Tracker") {
                        IncomeTrackerScreen(viewModel = viewModel)
                    }
                    is Screen.UtilityBills -> FinanceHubScreen(viewModel = viewModel, title = "Utility Bills & Subscriptions") {
                        UtilityBillsScreen(viewModel = viewModel)
                    }
                    is Screen.ZakatCalculator -> FinanceHubScreen(viewModel = viewModel, title = "Zakat Calculator") {
                        ZakatCalculatorScreen(viewModel = viewModel)
                    }
                    is Screen.BcCommittee -> FinanceHubScreen(viewModel = viewModel, title = "BC Kommittees") {
                        BcCommitteeScreen(viewModel = viewModel)
                    }
                    is Screen.BcCommitteeDetails -> FinanceHubScreen(viewModel = viewModel, title = "Kommittee Details") {
                        BcCommitteeDetailsScreen(viewModel = viewModel, committeeId = screen.committeeId)
                    }
                    is Screen.LoanTracker -> FinanceHubScreen(viewModel = viewModel, title = "Loan & Debt Manager") {
                        LoanTrackerScreen(viewModel = viewModel)
                    }
                    is Screen.SavingsGoals -> FinanceHubScreen(viewModel = viewModel, title = "Savings & Target Goals") {
                        SavingsGoalsScreen(viewModel = viewModel)
                    }
                    is Screen.FinanceReportAndBackup -> FinanceHubScreen(viewModel = viewModel, title = "Finance Report & Backup") {
                        FinanceReportAndBackupScreen(viewModel = viewModel)
                    }

                    // --- DOCUMENTS MODULES ---
                    is Screen.ImageToPdf -> DocumentHubScreen(viewModel = viewModel, title = "Image to PDF Converter") {
                        ImageToPdfScreen(viewModel = viewModel)
                    }
                    is Screen.ImageToXls -> DocumentHubScreen(viewModel = viewModel, title = "Image to Excel OCR") {
                        ImageToXlsScreen(viewModel = viewModel)
                    }
                    is Screen.ImageToWord -> DocumentHubScreen(viewModel = viewModel, title = "Image to Word OCR") {
                        ImageToWordScreen(viewModel = viewModel)
                    }
                    is Screen.CvBuilder -> DocumentHubScreen(viewModel = viewModel, title = "CV & Resume Builder") {
                        CvBuilderScreen(viewModel = viewModel)
                    }
                    is Screen.DocumentScanner -> DocumentHubScreen(viewModel = viewModel, title = "Document Edge Scanner") {
                        DocumentScannerScreen(viewModel = viewModel)
                    }
                    is Screen.IdCardScanner -> DocumentHubScreen(viewModel = viewModel, title = "ID Card Dual Scanner") {
                        IdCardScannerScreen(viewModel = viewModel)
                    }
                    is Screen.PassportScanner -> DocumentHubScreen(viewModel = viewModel, title = "Passport & Document Scanner") {
                        PassportScannerScreen(viewModel = viewModel)
                    }
                    is Screen.PdfTools -> DocumentHubScreen(viewModel = viewModel, title = "PDF Tool Suite") {
                        PdfToolsScreen(viewModel = viewModel)
                    }
                    is Screen.InvoiceGenerator -> DocumentHubScreen(viewModel = viewModel, title = "OmniPOS Invoice Suite") {
                        InvoiceGeneratorScreen(viewModel = viewModel)
                    }
                    is Screen.SignaturePad -> DocumentHubScreen(viewModel = viewModel, title = "Digital Stamp & Sign") {
                        SignaturePadScreen(viewModel = viewModel)
                    }

                    // --- TOOLS MODULES ---
                    is Screen.QrGenerator -> ToolsHubScreen(viewModel = viewModel, title = "QR Code Studio") {
                        QrGeneratorScreen(viewModel = viewModel)
                    }
                    is Screen.QrScanner -> ToolsHubScreen(viewModel = viewModel, title = "QR Code Scanner") {
                        QrScannerScreen(viewModel = viewModel)
                    }
                    is Screen.Calculator -> ToolsHubScreen(viewModel = viewModel, title = "Scientific Calculator") {
                        ScientificCalculatorScreen(viewModel = viewModel)
                    }
                    is Screen.UnitConverter -> ToolsHubScreen(viewModel = viewModel, title = "Unit Dimension Converter") {
                        UnitConverterScreen(viewModel = viewModel)
                    }
                    is Screen.PasswordManager -> ToolsHubScreen(viewModel = viewModel, title = "Password Vault Manager") {
                        PasswordManagerScreen(viewModel = viewModel)
                    }
                    is Screen.ImageTools -> ToolsHubScreen(viewModel = viewModel, title = "Image Compressor & Tools") {
                        ImageToolsScreen(viewModel = viewModel)
                    }
                    is Screen.IntruderGuard -> ToolsHubScreen(viewModel = viewModel, title = "Intruder Guard & Alarm") {
                        IntruderGuardScreen(viewModel = viewModel)
                    }
                    is Screen.WatermarkStudio -> ToolsHubScreen(viewModel = viewModel, title = "Watermark & Filter Studio") {
                        WatermarkStudioScreen(viewModel = viewModel)
                    }
                    is Screen.BackgroundEraser -> ToolsHubScreen(viewModel = viewModel, title = "Background Eraser Studio") {
                        BackgroundEraserScreen(viewModel = viewModel)
                    }
                    is Screen.FileEncryptor -> ToolsHubScreen(viewModel = viewModel, title = "File Encryptor Vault") {
                        FileEncryptorScreen(viewModel = viewModel)
                    }
                    is Screen.HiddenLocker -> ToolsHubScreen(viewModel = viewModel, title = "Hidden File Locker") {
                        HiddenLockerScreen(viewModel = viewModel)
                    }
                    is Screen.Steganography -> ToolsHubScreen(viewModel = viewModel, title = "Steganography Image Shield") {
                        SteganographyScreen(viewModel = viewModel)
                    }
                    is Screen.Steganalysis -> ToolsHubScreen(viewModel = viewModel, title = "Steganalysis Security Engine") {
                        SteganalysisScreen(viewModel = viewModel)
                    }
                    is Screen.ImageEnhancer -> ToolsHubScreen(viewModel = viewModel, title = "AI Image Enhancer") {
                        ImageEnhancerScreen()
                    }
                    is Screen.Teleprompter -> ToolsHubScreen(viewModel = viewModel, title = "Teleprompter Pro Studio") {
                        TeleprompterScreen(viewModel = viewModel)
                    }

                    // --- STUDY & HEALTH MODULES ---
                    is Screen.Notes -> ToolsHubScreen(viewModel = viewModel, title = "Study Lecture Notes") {
                        StudyNotesScreen(viewModel = viewModel)
                    }
                    is Screen.StudyTimer -> ToolsHubScreen(viewModel = viewModel, title = "Focus Pomodoro Timer") {
                        StudyTimerScreen(viewModel = viewModel)
                    }
                    is Screen.Timetable -> ToolsHubScreen(viewModel = viewModel, title = "Weekly Lesson Agenda") {
                        TimetableScreen(viewModel = viewModel)
                    }
                    is Screen.BmiCalculator -> ToolsHubScreen(viewModel = viewModel, title = "BMI Fitness Wellness") {
                        BmiCalculatorScreen(viewModel = viewModel)
                    }
                    is Screen.GpaCalculator -> ToolsHubScreen(viewModel = viewModel, title = "GPA Calculator") {
                        GpaCalculatorScreen(viewModel = viewModel)
                    }
                    is Screen.IvCalculator -> ToolsHubScreen(viewModel = viewModel, title = "IV Infusion Rate Solver") {
                        IvCalculatorScreen(viewModel = viewModel)
                    }
                    is Screen.DosageCalculator -> ToolsHubScreen(viewModel = viewModel, title = "Drug Dosage Solver") {
                        DosageCalculatorScreen(viewModel = viewModel)
                    }
                    is Screen.GfrCalculator -> ToolsHubScreen(viewModel = viewModel, title = "Renal Clearance GFR Solver") {
                        GfrCalculatorScreen(viewModel = viewModel)
                    }
                    is Screen.AnatomyAtlas -> ToolsHubScreen(viewModel = viewModel, title = "Human Anatomy Atlas") {
                        AnatomyAtlasScreen(viewModel = viewModel)
                    }
                    is Screen.PharmacyExam -> ToolsHubScreen(viewModel = viewModel, title = "Pharmacy Category B Exam Prep") {
                        PharmacyExamScreen(viewModel = viewModel)
                    }
                    is Screen.NursingExam -> ToolsHubScreen(viewModel = viewModel, title = "Nursing International Exam Kit") {
                        NursingExamScreen(viewModel = viewModel)
                    }
                    is Screen.HajjMedicalPrep -> ToolsHubScreen(viewModel = viewModel, title = "Hajj Medical Mission Prep") {
                        HajjMedicalPrepScreen(viewModel = viewModel)
                    }
                    is Screen.MoavineenHujjajPrep -> ToolsHubScreen(viewModel = viewModel, title = "Moavineen-e-Hujjaj NTS Prep") {
                        MoavineenHujjajPrepScreen(viewModel = viewModel)
                    }
                    is Screen.IslamicHub -> {
                        IslamicHubScreen(viewModel = viewModel)
                    }
                    is Screen.AgeCalculator -> ToolsHubScreen(viewModel = viewModel, title = "Age Calculator") {
                        AgeCalculatorScreen(viewModel = viewModel)
                    }

                    // --- SECURITY SUITE ---
                    is Screen.SecurityHub -> ToolsHubScreen(viewModel = viewModel, title = "Security Shield Suite") {
                        SecurityHubScreen(viewModel = viewModel)
                    }
                    is Screen.PinVault -> ToolsHubScreen(viewModel = viewModel, title = "PIN & Pattern Vault") {
                        PinVaultScreen(viewModel = viewModel)
                    }
                    is Screen.AppLock -> ToolsHubScreen(viewModel = viewModel, title = "App Lock Protector") {
                        AppLockScreen(viewModel = viewModel)
                    }
                    is Screen.CalculatorVault -> ToolsHubScreen(viewModel = viewModel, title = "Calculator Secret Vault") {
                        CalculatorVaultScreen(viewModel = viewModel)
                    }
                    is Screen.PhotoVault -> ToolsHubScreen(viewModel = viewModel, title = "Secret Photo Vault") {
                        PhotoVaultScreen(viewModel = viewModel)
                    }
                    is Screen.PrivateNotes -> ToolsHubScreen(viewModel = viewModel, title = "Encrypted Private Notes") {
                        PrivateNotesScreen(viewModel = viewModel)
                    }
                    is Screen.SecureDelete -> ToolsHubScreen(viewModel = viewModel, title = "Shredder & Secure Delete") {
                        SecureDeleteScreen(viewModel = viewModel)
                    }
                    is Screen.PermissionAuditor -> ToolsHubScreen(viewModel = viewModel, title = "App Permission Auditor") {
                        PermissionAuditorScreen(viewModel = viewModel)
                    }
                    is Screen.WifiScanner -> ToolsHubScreen(viewModel = viewModel, title = "Wi-Fi Security Inspector") {
                        WifiScannerScreen(viewModel = viewModel)
                    }
                    is Screen.UssdCheck -> ToolsHubScreen(viewModel = viewModel, title = "USSD Security Auditor") {
                        UssdCheckScreen(viewModel = viewModel)
                    }
                    is Screen.ThermalPrinterManager -> ToolsHubScreen(viewModel = viewModel, title = "Thermal Bluetooth Printer") {
                        ThermalPrinterManagerScreen(viewModel = viewModel)
                    }
                    is Screen.BiometricManagerScreen -> ToolsHubScreen(viewModel = viewModel, title = "Biometric Security Hardware") {
                        BiometricManagerScreen(viewModel = viewModel)
                    }
                    is Screen.About -> ToolsHubScreen(viewModel = viewModel, title = "About StudentKit Pro") {
                        AboutScreen(viewModel = viewModel)
                    }
                    is Screen.Settings -> ToolsHubScreen(viewModel = viewModel, title = "App Settings") {
                        SettingsScreen(viewModel = viewModel)
                    }
                    else -> {}
                }
            }
        }

        // Active Category Bottom Sheet Modal
        activeCategorySheet?.let { sheetType ->
            CategoryModuleSheet(
                categoryType = sheetType,
                onDismiss = { activeCategorySheet = null },
                onSelectModule = { targetScreen ->
                    activeCategorySheet = null
                    viewModel.navigateTo(targetScreen)
                }
            )
        }
    }
}

enum class CategorySheetType {
    FINANCE,
    DOCS,
    TOOLS,
    STUDY,
    SECURITY
}

data class CategoryModuleItem(
    val title: String,
    val description: String,
    val badge: String,
    val icon: ImageVector,
    val color: Color,
    val screen: Screen
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryModuleSheet(
    categoryType: CategorySheetType,
    onDismiss: () -> Unit,
    onSelectModule: (Screen) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val (title, icon, headerColor, modulesList) = when (categoryType) {
        CategorySheetType.FINANCE -> Quadruple(
            "Finance Suite",
            Icons.Default.AccountBalanceWallet,
            Color(0xFF00897B),
            listOf(
                CategoryModuleItem("Expense Tracker", "Log & categorize daily cash outflows", "SQLITE", Icons.Default.TrendingDown, Color(0xFFE53935), Screen.ExpenseTracker),
                CategoryModuleItem("Income Tracker", "Monitor income streams & earnings", "SQLITE", Icons.Default.TrendingUp, Color(0xFF43A047), Screen.IncomeTracker),
                CategoryModuleItem("Utility Bills", "Reminders and logs for utility bills", "ALERTS", Icons.Default.ReceiptLong, Color(0xFF1E88E5), Screen.UtilityBills),
                CategoryModuleItem("Zakat Calculator", "Calculate wealth Zakat accurately", "ISLAMIC", Icons.Default.AccountBalance, Color(0xFF00897B), Screen.ZakatCalculator),
                CategoryModuleItem("BC Kommittees", "Kommittee lucky draw & cycle logs", "DRAWS", Icons.Default.Groups, Color(0xFF8E24AA), Screen.BcCommittee),
                CategoryModuleItem("Loan Ledger", "Track borrowings & lent payments", "TRACK", Icons.Default.SwapHoriz, Color(0xFFD81B60), Screen.LoanTracker),
                CategoryModuleItem("Savings Goals", "Target savings & deposit logs", "SAVINGS", Icons.Default.Star, Color(0xFFF4511E), Screen.SavingsGoals),
                CategoryModuleItem("Finance Report", "Statement & full JSON backup", "PDF DATA", Icons.Default.Assessment, Color(0xFF3F51B5), Screen.FinanceReportAndBackup)
            )
        )
        CategorySheetType.DOCS -> Quadruple(
            "Document & OCR Suite",
            Icons.Default.Folder,
            Color(0xFF1565C0),
            listOf(
                CategoryModuleItem("Image to PDF", "Compile images into single PDF file", "PDF CONV", Icons.Default.PictureAsPdf, Color(0xFFE53935), Screen.ImageToPdf),
                CategoryModuleItem("Image to Excel", "Convert tables to spreadsheet via OCR", "OCR AI", Icons.Default.TableChart, Color(0xFF2E7D32), Screen.ImageToXls),
                CategoryModuleItem("Image to Word", "Convert images to DOCX files via OCR", "DOCX", Icons.Default.Description, Color(0xFF1565C0), Screen.ImageToWord),
                CategoryModuleItem("CV Resume Builder", "Create custom print-ready A4 PDF resumes", "A4 PRINT", Icons.Default.Badge, Color(0xFF00C853), Screen.CvBuilder),
                CategoryModuleItem("Edge Scanner", "Scan physical doc pages via camera", "HD SCAN", Icons.Default.DocumentScanner, Color(0xFF673AB7), Screen.DocumentScanner),
                CategoryModuleItem("ID Card Scanner", "Scan front & back of ID on single page", "SINGLE PAGE", Icons.Default.ContactPage, Color(0xFF1E88E5), Screen.IdCardScanner),
                CategoryModuleItem("Passport Scanner", "Full photo passport page scan utility", "GOVT DOC", Icons.Default.AssignmentInd, Color(0xFF00ACC1), Screen.PassportScanner),
                CategoryModuleItem("PDF Tool Suite", "Compress, merge, split or lock PDFs", "EDIT", Icons.Default.Compress, Color(0xFFEF6C00), Screen.PdfTools),
                CategoryModuleItem("OmniPOS Invoice", "Create professional PDF invoices", "INVOICES", Icons.Default.Receipt, Color(0xFF00838F), Screen.InvoiceGenerator),
                CategoryModuleItem("Stamp & Sign", "Freehand draw signature & stamp docs", "STAMP", Icons.Default.Gesture, Color(0xFF1976D2), Screen.SignaturePad)
            )
        )
        CategorySheetType.TOOLS -> Quadruple(
            "Smart Utilities Suite",
            Icons.Default.Build,
            Color(0xFF673AB7),
            listOf(
                CategoryModuleItem("Scientific Calc", "Advance mathematical formula solver", "MATH", Icons.Default.Calculate, Color(0xFFE91E63), Screen.Calculator),
                CategoryModuleItem("Unit Converter", "Convert data, length, weight, speeds", "CONVERT", Icons.Default.SwapVert, Color(0xFF00ACC1), Screen.UnitConverter),
                CategoryModuleItem("QR Generator", "Generate secure colored QR codes", "VECTOR", Icons.Default.QrCode, Color(0xFF3949AB), Screen.QrGenerator),
                CategoryModuleItem("QR Scanner", "Scan bar codes & check web links", "CAMERA", Icons.Default.QrCodeScanner, Color(0xFF00897B), Screen.QrScanner),
                CategoryModuleItem("Password Vault", "Local encrypted credentials keeper", "CRYPT", Icons.Default.Lock, Color(0xFF2E7D32), Screen.PasswordManager),
                CategoryModuleItem("Image Compress", "Compress, resize & optimize images", "BATCH", Icons.Default.AddPhotoAlternate, Color(0xFFC2185B), Screen.ImageTools),
                CategoryModuleItem("Age Calculator", "Exact age in years, months & days", "AGE", Icons.Default.Cake, Color(0xFFE91E63), Screen.AgeCalculator),
                CategoryModuleItem("Intruder Guard", "Silent background selfie & siren alarm", "SECURITY", Icons.Default.Security, Color(0xFFD32F2F), Screen.IntruderGuard),
                CategoryModuleItem("Watermark Studio", "Add text watermarks & photo filters", "STUDIO", Icons.Default.Brush, Color(0xFF7B1FA2), Screen.WatermarkStudio),
                CategoryModuleItem("Background Eraser", "AI background remover with refine", "AI SEGMENT", Icons.Default.FilterFrames, Color(0xFFE91E63), Screen.BackgroundEraser),
                CategoryModuleItem("File Encryptor", "Hardware AES-256 GCM locker", "KEYSTORE", Icons.Default.EnhancedEncryption, Color(0xFF1E88E5), Screen.FileEncryptor),
                CategoryModuleItem("Hidden Locker", "Secure sandbox file/photo vault", "ENCRYPTED", Icons.Default.FolderSpecial, Color(0xFFEC407A), Screen.HiddenLocker),
                CategoryModuleItem("Steganography", "Hide secret message in image pixels", "LSB BIT", Icons.Default.Image, Color(0xFF43A047), Screen.Steganography),
                CategoryModuleItem("Steganalysis", "Detect hidden data, LSB entropy", "FORENSICS", Icons.Default.Analytics, Color(0xFFE65100), Screen.Steganalysis),
                CategoryModuleItem("AI Enhancer", "Offline AI face & photo restorer", "REMINI", Icons.Default.AutoAwesome, Color(0xFF00ACC1), Screen.ImageEnhancer),
                CategoryModuleItem("Teleprompter Pro", "Camera video prompter with AI script generator", "PRO STUDIO", Icons.Default.Videocam, Color(0xFF6366F1), Screen.Teleprompter)
            )
        )
        CategorySheetType.STUDY -> Quadruple(
            "Study & Health Suite",
            Icons.Default.Book,
            Color(0xFFF57C00),
            listOf(
                CategoryModuleItem("Lecture Notes", "Organize lecture notes & study notes", "OFFLINE", Icons.Default.Book, Color(0xFFF57C00), Screen.Notes),
                CategoryModuleItem("Pomodoro Timer", "Focus study timer sessions & analytics", "FOCUS", Icons.Default.Timer, Color(0xFFE64A19), Screen.StudyTimer),
                CategoryModuleItem("Lesson Calendar", "Track school subjects & timetables", "AGENDA", Icons.Default.Schedule, Color(0xFF1976D2), Screen.Timetable),
                CategoryModuleItem("BMI & Health", "Water logging & fitness calculator", "WELLNESS", Icons.Default.FitnessCenter, Color(0xFF43A047), Screen.BmiCalculator),
                CategoryModuleItem("GPA Calculator", "Calculate Semester GPA & CGPA", "GPA CALC", Icons.Default.School, Color(0xFF9C27B0), Screen.GpaCalculator),
                CategoryModuleItem("IV Infusion Rate", "IV fluid flow & drop rates tracker", "NURSING", Icons.Default.WaterDrop, Color(0xFF0288D1), Screen.IvCalculator),
                CategoryModuleItem("Drug Dosage Calc", "Standard patient body-weight dosages", "PHARMACY", Icons.Default.MedicalServices, Color(0xFF00C853), Screen.DosageCalculator),
                CategoryModuleItem("GFR Renal Solver", "Cockcroft-Gault kidney clearance", "CLINICAL", Icons.Default.Science, Color(0xFFFF9100), Screen.GfrCalculator),
                CategoryModuleItem("Anatomy Atlas", "Human systems, colorful study charts", "ATLAS", Icons.Default.AccessibilityNew, Color(0xFFE53935), Screen.AnatomyAtlas),
                CategoryModuleItem("Pharmacy Exam", "Pakistan Category B pharmacy prep", "EXAM PREP", Icons.Default.Quiz, Color(0xFF9C27B0), Screen.PharmacyExam),
                CategoryModuleItem("Nursing Exam Kit", "NCLEX-RN, DHA, Saudi Prometric prep", "12000+ Qs", Icons.Default.MedicalServices, Color(0xFF00695C), Screen.NursingExam),
                CategoryModuleItem("Hajj Mission Prep", "Hajj Medical Mission NTS exam prep", "NTS EXAM", Icons.Default.MedicalInformation, Color(0xFF009688), Screen.HajjMedicalPrep),
                CategoryModuleItem("Moavineen Prep", "Moavineen-e-Hujjaj guide & test Qs", "NTS EXAM", Icons.Default.GroupWork, Color(0xFF3F51B5), Screen.MoavineenHujjajPrep),
                CategoryModuleItem("Islamic Library", "Read Manzil Arabic & Quran books", "MANZIL HD", Icons.Default.MenuBook, Color(0xFF198754), Screen.IslamicHub)
            )
        )
        CategorySheetType.SECURITY -> Quadruple(
            "Security Shield Suite",
            Icons.Default.Security,
            Color(0xFF10B981),
            listOf(
                CategoryModuleItem("Security Hub", "Overall security status & shield hub", "SHIELD", Icons.Default.Shield, Color(0xFF10B981), Screen.SecurityHub),
                CategoryModuleItem("PIN & Pattern Vault", "Master passcode & pattern lock setup", "SECURITY", Icons.Default.Pin, Color(0xFF6366F1), Screen.PinVault),
                CategoryModuleItem("App Lock Protector", "Protect sensitive apps with PIN/Bio", "APPLOCK", Icons.Default.Lock, Color(0xFF8B5CF6), Screen.AppLock),
                CategoryModuleItem("Calculator Vault", "Disguised calculator vault for media", "VAULT", Icons.Default.Calculate, Color(0xFFEC407A), Screen.CalculatorVault),
                CategoryModuleItem("Secret Photo Vault", "AES-256 encrypted hidden gallery", "MEDIA", Icons.Default.PhotoLibrary, Color(0xFF3B82F6), Screen.PhotoVault),
                CategoryModuleItem("Encrypted Notes", "Locked journal & secure note vault", "NOTES", Icons.Default.Note, Color(0xFF14B8A6), Screen.PrivateNotes),
                CategoryModuleItem("Shredder & Delete", "Permanent multi-pass destruction", "SHREDDER", Icons.Default.DeleteForever, Color(0xFFEF4444), Screen.SecureDelete),
                CategoryModuleItem("Permission Auditor", "Inspect privacy risk & camera permissions", "AUDIT", Icons.Default.Policy, Color(0xFFF59E0B), Screen.PermissionAuditor),
                CategoryModuleItem("Wi-Fi Inspector", "Scan connected devices & router safety", "NETWORK", Icons.Default.WifiTethering, Color(0xFF06B6D4), Screen.WifiScanner),
                CategoryModuleItem("USSD Auditor", "Scan call forwarding & MMI codes", "TELECOM", Icons.Default.Dialpad, Color(0xFF8B5CF6), Screen.UssdCheck),
                CategoryModuleItem("Thermal Printer", "ESC/POS receipt printing manager", "PRINTER", Icons.Default.Print, Color(0xFF64748B), Screen.ThermalPrinterManager),
                CategoryModuleItem("Biometric Hardware", "Fingerprint & FaceID sensor config", "HARDWARE", Icons.Default.Fingerprint, Color(0xFF10B981), Screen.BiometricManagerScreen)
            )
        )
    }

    val filteredList = remember(searchQuery, modulesList) {
        if (searchQuery.isBlank()) modulesList
        else modulesList.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.description.contains(searchQuery, ignoreCase = true) ||
                    it.badge.contains(searchQuery, ignoreCase = true)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Header Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = headerColor.copy(alpha = 0.15f),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, contentDescription = null, tint = headerColor, modifier = Modifier.size(24.dp))
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${modulesList.size} modules available • Tap to open",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search Filter Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search ${title}...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp)) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", modifier = Modifier.size(16.dp))
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Modules Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 460.dp)
            ) {
                items(filteredList) { item ->
                    Card(
                        onClick = { onSelectModule(item.screen) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = item.color.copy(alpha = 0.15f),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = null,
                                            tint = item.color,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = item.color.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = item.badge,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = item.color,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.title,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = item.description,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                lineHeight = 12.sp,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

// -----------------------------------------------------------------------------
// NAVIGATION CHOICE GRIDS (SubChoicesHub)
// -----------------------------------------------------------------------------

@Composable
fun FinanceSubChoicesHub(
    viewModel: StudentKitViewModel,
    activeSelection: String,
    content: @Composable () -> Unit
) {
    val options = listOf(
        Pair("Expenses", Screen.ExpenseTracker),
        Pair("Income", Screen.IncomeTracker),
        Pair("Bills", Screen.UtilityBills),
        Pair("Zakat", Screen.ZakatCalculator),
        Pair("Kommittees", Screen.BcCommittee),
        Pair("Loans", Screen.LoanTracker),
        Pair("Savings", Screen.SavingsGoals),
        Pair("Report & Backup", Screen.FinanceReportAndBackup)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { (label, targetScreen) ->
                val selected = activeSelection == label
                ElevatedFilterChip(
                    selected = selected,
                    onClick = { viewModel.navigateTo(targetScreen) },
                    label = { Text(label, fontSize = 12.sp) }
                )
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}

@Composable
fun DocumentSubChoicesHub(
    viewModel: StudentKitViewModel,
    activeSelection: String,
    content: @Composable () -> Unit
) {
    val options = listOf(
        Pair("CV Builder", Screen.CvBuilder),
        Pair("Image to PDF", Screen.ImageToPdf),
        Pair("To XLS", Screen.ImageToXls),
        Pair("To Word", Screen.ImageToWord),
        Pair("Edge Scan", Screen.DocumentScanner),
        Pair("ID Card Scan", Screen.IdCardScanner),
        Pair("Passport Scan", Screen.PassportScanner),
        Pair("PDF Tools", Screen.PdfTools),
        Pair("OmniPOS Hub", Screen.InvoiceGenerator),
        Pair("Stamp & Sign", Screen.SignaturePad)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { (label, targetScreen) ->
                val selected = activeSelection == label
                ElevatedFilterChip(
                    selected = selected,
                    onClick = { viewModel.navigateTo(targetScreen) },
                    label = { Text(label, fontSize = 12.sp) }
                )
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}

@Composable
fun ToolsSubChoicesHub(
    viewModel: StudentKitViewModel,
    activeSelection: String,
    content: @Composable () -> Unit
) {
    val options = listOf(
        Pair("Calculator", Screen.Calculator),
        Pair("Converter", Screen.UnitConverter),
        Pair("QR Generator", Screen.QrGenerator),
        Pair("QR Scanner", Screen.QrScanner),
        Pair("Passwords", Screen.PasswordManager),
        Pair("Image Edits", Screen.ImageTools),
        Pair("Age Calc", Screen.AgeCalculator),
        Pair("Intruder Guard", Screen.IntruderGuard),
        Pair("File Encryptor", Screen.FileEncryptor),
        Pair("Hidden Locker", Screen.HiddenLocker),
        Pair("Steganography", Screen.Steganography),
        Pair("Steganalysis", Screen.Steganalysis),
        Pair("AI Enhancer", Screen.ImageEnhancer),
        Pair("Watermark", Screen.WatermarkStudio),
        Pair("Background Eraser", Screen.BackgroundEraser)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { (label, targetScreen) ->
                val selected = activeSelection == label
                ElevatedFilterChip(
                    selected = selected,
                    onClick = { viewModel.navigateTo(targetScreen) },
                    label = { Text(label, fontSize = 12.sp) }
                )
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}

@Composable
fun StudySubChoicesHub(
    viewModel: StudentKitViewModel,
    activeSelection: String,
    content: @Composable () -> Unit
) {
    val options = listOf(
        Pair("Lecture Notes", Screen.Notes),
        Pair("Focus Timer", Screen.StudyTimer),
        Pair("Lesson Agenda", Screen.Timetable),
        Pair("BMI Checker", Screen.BmiCalculator),
        Pair("GPA Calc", Screen.GpaCalculator),
        Pair("IV Flow", Screen.IvCalculator),
        Pair("Dosage", Screen.DosageCalculator),
        Pair("Kidney GFR", Screen.GfrCalculator),
        Pair("Anatomy", Screen.AnatomyAtlas),
        Pair("Pharm Exam", Screen.PharmacyExam),
        Pair("Nursing Exam", Screen.NursingExam),
        Pair("Hajj Medical", Screen.HajjMedicalPrep),
        Pair("Moavineen Prep", Screen.MoavineenHujjajPrep),
        Pair("Islamic Library", Screen.IslamicHub)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { (label, targetScreen) ->
                val selected = activeSelection == label
                ElevatedFilterChip(
                    selected = selected,
                    onClick = { viewModel.navigateTo(targetScreen) },
                    label = { Text(label, fontSize = 12.sp) }
                )
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}
