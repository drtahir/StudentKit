package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer(viewModel: StudentKitViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    var selectedTabItem by remember { mutableStateOf(0) } // 0=Home, 1=Finance, 2=Docs, 3=Tools, 4=Study

    // Sync selected bottom tab item when currentScreen changes (e.g. from Home screen HD buttons)
    LaunchedEffect(currentScreen) {
        selectedTabItem = when (currentScreen) {
            is Screen.Dashboard -> 0
            is Screen.ExpenseTracker, is Screen.IncomeTracker, is Screen.UtilityBills,
            is Screen.ZakatCalculator, is Screen.BcCommittee, is Screen.BcCommitteeDetails,
            is Screen.LoanTracker, is Screen.SavingsGoals -> 1
            is Screen.CvBuilder, is Screen.ImageToPdf, is Screen.ImageToXls, is Screen.ImageToWord,
            is Screen.DocumentScanner, is Screen.IdCardScanner, is Screen.PassportScanner,
            is Screen.PdfTools, is Screen.InvoiceGenerator, is Screen.SignaturePad -> 2
            is Screen.Calculator, is Screen.UnitConverter, is Screen.QrGenerator,
            is Screen.QrScanner, is Screen.WifiQrGenerator, is Screen.PasswordManager,
            is Screen.ImageTools, is Screen.AgeCalculator, is Screen.IntruderGuard, is Screen.WatermarkStudio, is Screen.BackgroundEraser,
            is Screen.FileEncryptor, is Screen.HiddenLocker, is Screen.Steganography, is Screen.ImageEnhancer -> 3
            is Screen.Notes, is Screen.StudyTimer, is Screen.Timetable, is Screen.BmiCalculator,
            is Screen.GpaCalculator, is Screen.IvCalculator, is Screen.DosageCalculator,
            is Screen.GfrCalculator, is Screen.AnatomyAtlas, is Screen.PharmacyExam, is Screen.HajjMedicalPrep,
            is Screen.IslamicHub -> 4
            is Screen.SecurityHub, is Screen.PinVault, is Screen.AppLock, is Screen.CalculatorVault,
            is Screen.PhotoVault, is Screen.PrivateNotes, is Screen.SecureDelete, is Screen.PermissionAuditor,
            is Screen.WifiScanner, is Screen.UssdCheck -> 5
        }
    }

    // Handle standard android system back navigation press
    BackHandler(enabled = currentScreen != Screen.Dashboard) {
        viewModel.navigateBack()
    }

    Scaffold(
        bottomBar = {
            if (currentScreen != Screen.IslamicHub) {
                NavigationBar(
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                NavigationBarItem(
                    selected = selectedTabItem == 0,
                    onClick = {
                        selectedTabItem = 0
                        viewModel.navigateTo(Screen.Dashboard)
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_home")
                )
                NavigationBarItem(
                    selected = selectedTabItem == 1,
                    onClick = {
                        selectedTabItem = 1
                        viewModel.navigateTo(Screen.ExpenseTracker) // Default start sub-screen for Finance
                    },
                    icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = "Finance") },
                    label = { Text("Finance", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_finance")
                )
                NavigationBarItem(
                    selected = selectedTabItem == 2,
                    onClick = {
                        selectedTabItem = 2
                        viewModel.navigateTo(Screen.CvBuilder) // Default Docs screen
                    },
                    icon = { Icon(Icons.Default.Folder, contentDescription = "Documents") },
                    label = { Text("Docs", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_docs")
                )
                NavigationBarItem(
                    selected = selectedTabItem == 3,
                    onClick = {
                        selectedTabItem = 3
                        viewModel.navigateTo(Screen.Calculator) // Default Tools screen
                    },
                    icon = { Icon(Icons.Default.Build, contentDescription = "Tools") },
                    label = { Text("Tools", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_tools")
                )
                NavigationBarItem(
                    selected = selectedTabItem == 4,
                    onClick = {
                        selectedTabItem = 4
                        viewModel.navigateTo(Screen.Notes) // Default Study screen
                    },
                    icon = { Icon(Icons.Default.Book, contentDescription = "Study & Health") },
                    label = { Text("Study", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_study")
                )
                NavigationBarItem(
                    selected = selectedTabItem == 5,
                    onClick = {
                        selectedTabItem = 5
                        viewModel.navigateTo(Screen.SecurityHub)
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
                    is Screen.ExpenseTracker -> FinanceHubScreen(viewModel = viewModel) {
                        FinanceSubChoicesHub(viewModel = viewModel, activeSelection = "Expenses") {
                            ExpenseTrackerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.IncomeTracker -> FinanceHubScreen(viewModel = viewModel) {
                        FinanceSubChoicesHub(viewModel = viewModel, activeSelection = "Income") {
                            IncomeTrackerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.UtilityBills -> FinanceHubScreen(viewModel = viewModel) {
                        FinanceSubChoicesHub(viewModel = viewModel, activeSelection = "Bills") {
                            UtilityBillsScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.ZakatCalculator -> FinanceHubScreen(viewModel = viewModel) {
                        FinanceSubChoicesHub(viewModel = viewModel, activeSelection = "Zakat") {
                            ZakatCalculatorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.BcCommittee -> FinanceHubScreen(viewModel = viewModel) {
                        FinanceSubChoicesHub(viewModel = viewModel, activeSelection = "Committees") {
                            BcCommitteeScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.BcCommitteeDetails -> FinanceHubScreen(viewModel = viewModel) {
                        BcCommitteeDetailsScreen(viewModel = viewModel, committeeId = screen.committeeId)
                    }
                    is Screen.LoanTracker -> FinanceHubScreen(viewModel = viewModel) {
                        FinanceSubChoicesHub(viewModel = viewModel, activeSelection = "Loans") {
                            LoanTrackerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.SavingsGoals -> FinanceHubScreen(viewModel = viewModel) {
                        FinanceSubChoicesHub(viewModel = viewModel, activeSelection = "Savings") {
                            SavingsGoalsScreen(viewModel = viewModel)
                        }
                    }

                    // --- DOCUMENTS MODULES ---
                    is Screen.ImageToPdf -> DocumentHubScreen(viewModel = viewModel, title = "Image to PDF") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "Image to PDF") {
                            ImageToPdfScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.ImageToXls -> DocumentHubScreen(viewModel = viewModel, title = "Image to Excel") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "To XLS") {
                            ImageToXlsScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.ImageToWord -> DocumentHubScreen(viewModel = viewModel, title = "Image to Word") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "To Word") {
                            ImageToWordScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.CvBuilder -> DocumentHubScreen(viewModel = viewModel, title = "CV Resume Builder") {
                        CvBuilderScreen(viewModel = viewModel)
                    }
                    is Screen.DocumentScanner -> DocumentHubScreen(viewModel = viewModel, title = "Document Edge Scanner") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "Edge Scan") {
                            DocumentScannerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.IdCardScanner -> DocumentHubScreen(viewModel = viewModel, title = "ID Card Scanner") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "ID Card Scan") {
                            IdCardScannerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.PassportScanner -> DocumentHubScreen(viewModel = viewModel, title = "Passport & Document Scanner") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "Passport Scan") {
                            PassportScannerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.PdfTools -> DocumentHubScreen(viewModel = viewModel, title = "PDF Tool Handlers") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "PDF Tools") {
                            PdfToolsScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.InvoiceGenerator -> DocumentHubScreen(viewModel = viewModel, title = "Invoice & Receipt Maker") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "Invoice Maker") {
                            InvoiceGeneratorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.SignaturePad -> DocumentHubScreen(viewModel = viewModel, title = "Stamp & Sign Documents") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "Stamp & Sign") {
                            SignaturePadScreen(viewModel = viewModel)
                        }
                    }

                    // --- TOOLS MODULES ---
                    is Screen.QrGenerator -> ToolsHubScreen(viewModel = viewModel, title = "QR Generator Code") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "QR Generator") {
                            QrGeneratorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.QrScanner -> ToolsHubScreen(viewModel = viewModel, title = "QR Code Scanner") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "QR Scanner") {
                            QrScannerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.WifiQrGenerator -> ToolsHubScreen(viewModel = viewModel, title = "Wi-Fi QR Code Generator") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Wi-Fi QR") {
                            WifiQrGeneratorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.Calculator -> ToolsHubScreen(viewModel = viewModel, title = "Scientific Calculator") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Calculator") {
                            ScientificCalculatorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.UnitConverter -> ToolsHubScreen(viewModel = viewModel, title = "Unit Dimension Converter") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Converter") {
                            UnitConverterScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.PasswordManager -> ToolsHubScreen(viewModel = viewModel, title = "Passwords Vault Manager") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Passwords") {
                            PasswordManagerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.ImageTools -> ToolsHubScreen(viewModel = viewModel, title = "Image Compression Tools") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Image Edits") {
                            ImageToolsScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.IntruderGuard -> ToolsHubScreen(viewModel = viewModel, title = "Intruder Guard & Alarm") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Intruder Guard") {
                            IntruderGuardScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.WatermarkStudio -> ToolsHubScreen(viewModel = viewModel, title = "Watermark & Filter Studio") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Watermark") {
                            WatermarkStudioScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.BackgroundEraser -> ToolsHubScreen(viewModel = viewModel, title = "Background Eraser Studio") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Background Eraser") {
                            BackgroundEraserScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.FileEncryptor -> ToolsHubScreen(viewModel = viewModel, title = "File Encryptor Vault") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "File Encryptor") {
                            FileEncryptorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.HiddenLocker -> ToolsHubScreen(viewModel = viewModel, title = "Hidden File Locker") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Hidden Locker") {
                            HiddenLockerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.Steganography -> ToolsHubScreen(viewModel = viewModel, title = "Steganography Image Shield") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Steganography") {
                            SteganographyScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.ImageEnhancer -> ToolsHubScreen(viewModel = viewModel, title = "Offline AI Enhancer") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "AI Enhancer") {
                            ImageEnhancerScreen()
                        }
                    }

                    // --- STUDY & HEALTH MODULES ---
                    is Screen.Notes -> ToolsHubScreen(viewModel = viewModel, title = "Study Lecture Notes") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "Lecture Notes") {
                            StudyNotesScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.StudyTimer -> ToolsHubScreen(viewModel = viewModel, title = "Focus Pomodoro Timer") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "Focus Timer") {
                            StudyTimerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.Timetable -> ToolsHubScreen(viewModel = viewModel, title = "Weekly Lesson Agenda") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "Lesson Agenda") {
                            TimetableScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.BmiCalculator -> ToolsHubScreen(viewModel = viewModel, title = "BMI Fitness Wellness") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "BMI Checker") {
                            BmiCalculatorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.GpaCalculator -> ToolsHubScreen(viewModel = viewModel, title = "GPA Calculator") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "GPA Calc") {
                            GpaCalculatorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.IvCalculator -> ToolsHubScreen(viewModel = viewModel, title = "IV Infusion Rate Solver") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "IV Flow") {
                            IvCalculatorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.DosageCalculator -> ToolsHubScreen(viewModel = viewModel, title = "Drug Dosage Solver") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "Dosage") {
                            DosageCalculatorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.GfrCalculator -> ToolsHubScreen(viewModel = viewModel, title = "Renal Clearance (CG CrCl)") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "Kidney GFR") {
                            GfrCalculatorScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.AnatomyAtlas -> ToolsHubScreen(viewModel = viewModel, title = "Human Anatomy Atlas") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "Anatomy") {
                            AnatomyAtlasScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.PharmacyExam -> ToolsHubScreen(viewModel = viewModel, title = "Pharmacy Category B Exam Prep") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "Pharm Exam") {
                            PharmacyExamScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.HajjMedicalPrep -> ToolsHubScreen(viewModel = viewModel, title = "Hajj Medical Mission NTS Prep") {
                        StudySubChoicesHub(viewModel = viewModel, activeSelection = "Hajj Prep") {
                            HajjMedicalPrepScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.IslamicHub -> {
                        IslamicHubScreen(viewModel = viewModel)
                    }
                    is Screen.AgeCalculator -> ToolsHubScreen(viewModel = viewModel, title = "Age Calculator") {
                        ToolsSubChoicesHub(viewModel = viewModel, activeSelection = "Age Calc") {
                            AgeCalculatorScreen(viewModel = viewModel)
                        }
                    }

                    // --- SECURITY SUITE ---
                    is Screen.SecurityHub -> SecurityHubScreen(viewModel = viewModel)
                    is Screen.PinVault -> PinVaultScreen(viewModel = viewModel)
                    is Screen.AppLock -> AppLockScreen(viewModel = viewModel)
                    is Screen.CalculatorVault -> CalculatorVaultScreen(viewModel = viewModel)
                    is Screen.PhotoVault -> PhotoVaultScreen(viewModel = viewModel)
                    is Screen.PrivateNotes -> PrivateNotesScreen(viewModel = viewModel)
                    is Screen.SecureDelete -> SecureDeleteScreen(viewModel = viewModel)
                    is Screen.PermissionAuditor -> PermissionAuditorScreen(viewModel = viewModel)
                    is Screen.WifiScanner -> WifiScannerScreen(viewModel = viewModel)
                    is Screen.UssdCheck -> UssdCheckScreen(viewModel = viewModel)
                }
            }
        }
    }
}

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
        Pair("Committees", Screen.BcCommittee),
        Pair("Loans", Screen.LoanTracker),
        Pair("Savings", Screen.SavingsGoals)
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
        Pair("Invoice Maker", Screen.InvoiceGenerator),
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
        Pair("Wi-Fi QR", Screen.WifiQrGenerator),
        Pair("Passwords", Screen.PasswordManager),
        Pair("Image Edits", Screen.ImageTools),
        Pair("Age Calc", Screen.AgeCalculator),
        Pair("Intruder Guard", Screen.IntruderGuard),
        Pair("File Encryptor", Screen.FileEncryptor),
        Pair("Hidden Locker", Screen.HiddenLocker),
        Pair("Steganography", Screen.Steganography),
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
        Pair("Hajj Prep", Screen.HajjMedicalPrep),
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
