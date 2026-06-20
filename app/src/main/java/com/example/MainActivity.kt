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
            MyApplicationTheme {
                val viewModel: StudentKitViewModel = viewModel()
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

    // Handle standard android system back navigation press
    BackHandler(enabled = currentScreen != Screen.Dashboard) {
        viewModel.navigateBack()
    }

    Scaffold(
        bottomBar = {
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
                    is Screen.CvBuilder -> DocumentHubScreen(viewModel = viewModel, title = "CV Resume Builder") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "CV Builder") {
                            CvBuilderScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.DocumentScanner -> DocumentHubScreen(viewModel = viewModel, title = "Document Edge Scanner") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "Edge Scan") {
                            DocumentScannerScreen(viewModel = viewModel)
                        }
                    }
                    is Screen.PdfTools -> DocumentHubScreen(viewModel = viewModel, title = "PDF Tool Handlers") {
                        DocumentSubChoicesHub(viewModel = viewModel, activeSelection = "PDF Tools") {
                            PdfToolsScreen(viewModel = viewModel)
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
        Pair("Edge Scan", Screen.DocumentScanner),
        Pair("PDF Tools", Screen.PdfTools)
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
        Pair("Image Edits", Screen.ImageTools)
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
        Pair("BMI Checker", Screen.BmiCalculator)
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
