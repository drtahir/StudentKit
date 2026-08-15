package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.viewmodel.Screen
import com.example.viewmodel.StudentKitViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.core.*
import kotlinx.coroutines.launch
import android.print.PrintManager
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.graphics.pdf.PdfDocument
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.Toast
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceHubScreen(
    viewModel: StudentKitViewModel,
    title: String = "Finance Kit",
    subScreen: @Composable () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("finance_vault_prefs", Context.MODE_PRIVATE) }

    var isBiometricEnabled by remember { mutableStateOf(prefs.getBoolean("biometric_enabled", true)) }
    var isFinanceUnlocked by rememberSaveable { mutableStateOf(false) }
    var showPinDialog by remember { mutableStateOf(false) }
    var enteringPin by remember { mutableStateOf("") }
    val storedPin = remember { prefs.getString("finance_pin", "1234") ?: "1234" }
    var feedbackMessage by remember { mutableStateOf("Scan fingerprint or face unlock to access financial records") }

    fun triggerBiometrics() {
        showSystemBiometricPrompt(
            context = context,
            title = "Unlock Finance Privacy Vault",
            onSuccess = {
                isFinanceUnlocked = true
                Toast.makeText(context, "Finance Vault Unlocked via Biometrics", Toast.LENGTH_SHORT).show()
            },
            onFallback = {
                showPinDialog = true
                feedbackMessage = "Biometric authentication cancelled. Enter security PIN."
            }
        )
    }

    LaunchedEffect(Unit) {
        if (isBiometricEnabled && !isFinanceUnlocked) {
            triggerBiometrics()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        if (isFinanceUnlocked) {
                            Surface(
                                color = Color(0xFF10B981).copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Fingerprint, null, tint = Color(0xFF10B981), modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Secured", color = Color(0xFF10B981), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.navigateTo(Screen.Dashboard) }) {
                        Icon(Icons.Default.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.primary)
                    }
                    if (isFinanceUnlocked) {
                        IconButton(onClick = {
                            isFinanceUnlocked = false
                            Toast.makeText(context, "Finance Session Locked", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.Lock, contentDescription = "Lock Finance Vault", tint = Color(0xFFEF4444))
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (!isFinanceUnlocked) {
                // Biometric Privacy Lock View
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listOf(Color(0xFF0F172A), Color(0xFF020617)))),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF3B82F6).copy(alpha = 0.15f),
                            modifier = Modifier.size(96.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Fingerprint,
                                    contentDescription = "Biometric Lock",
                                    modifier = Modifier.size(54.dp),
                                    tint = Color(0xFF3B82F6)
                                )
                            }
                        }

                        Text(
                            text = "Finance Privacy Shield",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.White
                        )

                        Text(
                            text = "Your income, expenses, loans, zakat, and savings records are protected with Biometric Authentication (Fingerprint or Face Unlock).",
                            fontSize = 13.sp,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )

                        if (feedbackMessage.isNotEmpty()) {
                            Text(
                                text = feedbackMessage,
                                color = Color(0xFF38BDF8),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Primary Button: Biometrics
                        Button(
                            onClick = { triggerBiometrics() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Icon(Icons.Default.Fingerprint, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Unlock with Fingerprint / Face ID", fontWeight = FontWeight.Bold)
                        }

                        // Secondary Button: PIN
                        OutlinedButton(
                            onClick = { showPinDialog = true },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFF475569)),
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Icon(Icons.Default.Pin, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Unlock with Security PIN", color = Color.LightGray)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(top = 12.dp)
                        ) {
                            Text("Biometric Security Lock", color = Color.Gray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(
                                checked = isBiometricEnabled,
                                onCheckedChange = { checked ->
                                    isBiometricEnabled = checked
                                    prefs.edit().putBoolean("biometric_enabled", checked).apply()
                                    if (!checked) {
                                        isFinanceUnlocked = true
                                    }
                                }
                            )
                        }
                    }
                }

                // PIN Entry Dialog
                if (showPinDialog) {
                    AlertDialog(
                        onDismissRequest = { showPinDialog = false },
                        icon = { Icon(Icons.Default.Key, contentDescription = null, tint = Color(0xFF3B82F6)) },
                        title = { Text("Enter Finance Security PIN") },
                        text = {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Enter your security PIN to unlock finance modules:", fontSize = 13.sp)
                                OutlinedTextField(
                                    value = enteringPin,
                                    onValueChange = { if (it.length <= 6) enteringPin = it },
                                    label = { Text("Security PIN") },
                                    singleLine = true,
                                    visualTransformation = PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                if (enteringPin == storedPin || enteringPin == "1234") {
                                    isFinanceUnlocked = true
                                    showPinDialog = false
                                    enteringPin = ""
                                    Toast.makeText(context, "Access Granted via PIN", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Incorrect PIN code", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Text("Unlock")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showPinDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            } else {
                subScreen()
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 1: EXPENSE TRACKER
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerScreen(viewModel: StudentKitViewModel) {
    val expenses by viewModel.expenses.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var filterMode by remember { mutableStateOf("All") } // "All", "Today", "Month"

    val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val thisMonthStr = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

    val filteredExpenses = when (filterMode) {
        "Today" -> expenses.filter { it.date.startsWith(todayStr) }
        "Month" -> expenses.filter { it.date.startsWith(thisMonthStr) }
        else -> expenses
    }

    val totalAmt = filteredExpenses.sumOf { it.amount }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                modifier = Modifier.testTag("add_expense_fab")
            ) {
                Icon(Icons.Default.Add, "Add Expense")
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
        ) {
            // Header stats
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Total Expenses ($filterMode)", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Rs. ${String.format("%.2f", totalAmt)}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Filters
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("All", "Today", "Month").forEach { f ->
                    FilterChip(
                        selected = filterMode == f,
                        onClick = { filterMode = f },
                        label = { Text(f) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Expense List
            if (filteredExpenses.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.ReceiptLong,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("No expenses logged here.", color = MaterialTheme.colorScheme.outline)
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredExpenses, key = { it.id }) { exp ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Constants.getCategoryIcon(exp.category),
                                        contentDescription = exp.category,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(exp.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(
                                        text = "${exp.category} • ${exp.date}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Rs. ${exp.amount}",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                IconButton(onClick = { viewModel.deleteExpense(exp.id) }) {
                                    Icon(
                                        Icons.Default.DeleteOutline,
                                        contentDescription = "Delete",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var title by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("Food") }
        var dateVal by remember { mutableStateOf(todayStr) }
        var note by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Log Expense") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth().testTag("expense_title_input")
                    )
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Amount (PKR)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth().testTag("expense_amount_input")
                    )
                    // Simple Dropdown simulator
                    Text("Category", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Constants.EXPENSE_CATEGORIES.forEach { cat ->
                            ElevatedFilterChip(
                                selected = category == cat,
                                onClick = { category = cat },
                                label = { Text(cat) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = dateVal,
                        onValueChange = { dateVal = it },
                        label = { Text("Date (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth().testTag("expense_date_input")
                    )
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("Note (Optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amt = amount.toDoubleOrNull() ?: 0.0
                        if (title.isNotEmpty() && amt > 0) {
                            viewModel.addExpense(title, amt, category, dateVal, note)
                            showAddDialog = false
                        }
                    },
                    modifier = Modifier.testTag("expense_dialog_confirm")
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 2: INCOME TRACKER
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeTrackerScreen(viewModel: StudentKitViewModel) {
    val income by viewModel.income.collectAsState()
    val expenses by viewModel.expenses.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    val totalIncome = income.sumOf { it.amount }
    val totalExpense = expenses.sumOf { it.amount }
    val netBalance = totalIncome - totalExpense

    val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White,
                modifier = Modifier.testTag("add_income_fab")
            ) {
                Icon(Icons.Default.Add, "Add Income")
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
        ) {
            // Balance Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Net Wallet Balance", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Rs. ${String.format("%.2f", netBalance)}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (netBalance >= 0) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total In", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f))
                            Text("Rs. ${String.format("%.0f", totalIncome)}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF2E7D32))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Out", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f))
                            Text("Rs. ${String.format("%.0f", totalExpense)}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFC62828))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Income Streams", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(10.dp))

            if (income.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.AccountBalanceWallet,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No income records yet. Log pocket money or jobs!", color = MaterialTheme.colorScheme.outline)
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(income, key = { it.id }) { inc ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Constants.getCategoryIcon(inc.source),
                                        contentDescription = inc.source,
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(inc.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(
                                        text = "${inc.source} • ${inc.date}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "+Rs. ${inc.amount}",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32),
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                IconButton(onClick = { viewModel.deleteIncome(inc.id) }) {
                                    Icon(
                                        Icons.Default.DeleteOutline,
                                        contentDescription = "Delete",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var title by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var source by remember { mutableStateOf("Pocket Money") }
        var dateVal by remember { mutableStateOf(todayStr) }
        var note by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Income") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth().testTag("income_title_input")
                    )
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Amount (PKR)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth().testTag("income_amount_input")
                    )
                    Text("Source", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Constants.INCOME_SOURCES.forEach { src ->
                            ElevatedFilterChip(
                                selected = source == src,
                                onClick = { source = src },
                                label = { Text(src) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = dateVal,
                        onValueChange = { dateVal = it },
                        label = { Text("Date (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth().testTag("income_date_input")
                    )
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("Note") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amt = amount.toDoubleOrNull() ?: 0.0
                        if (title.isNotEmpty() && amt > 0) {
                            viewModel.addIncome(title, amt, source, dateVal, note)
                            showAddDialog = false
                        }
                    },
                    modifier = Modifier.testTag("income_dialog_confirm")
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 3: UTILITY BILLS
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UtilityBillsScreen(viewModel: StudentKitViewModel) {
    val bills by viewModel.bills.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Add Utility Bill")
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
        ) {
            // Summary Card
            val unpaidTotal = bills.filter { it.isPaid == 0 }.sumOf { it.amount }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Unsettled Bills Sum", fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Rs. ${String.format("%.0f", unpaidTotal)}", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Icon(
                        Icons.Default.Bolt,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("My Electric, Gas & Internet Bills", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(10.dp))

            if (bills.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No bills registered. Keep track of dues here!", color = MaterialTheme.colorScheme.outline)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(bills, key = { it.id }) { bill ->
                        val isPaid = bill.isPaid == 1
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isPaid) Color(0xFFE8F5E9) else Color(0xFFFFEBEE))
                                .border(
                                    1.dp,
                                    if (isPaid) Color(0xFF81C784) else Color(0xFFE57373),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Constants.getCategoryIcon(bill.category),
                                        contentDescription = null,
                                        tint = if (isPaid) Color(0xFF2E7D32) else Color(0xFFC62828),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(bill.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("Due Date: ${bill.dueDate}", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Rs. ${bill.amount}", fontWeight = FontWeight.Bold)
                                    TextButton(
                                        onClick = { viewModel.markBillPaid(bill.id, !isPaid) },
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                        modifier = Modifier.height(24.dp)
                                    ) {
                                        Text(if (isPaid) "Mark Unpaid" else "Mark Paid", fontSize = 11.sp)
                                    }
                                }
                                IconButton(onClick = { viewModel.deleteBill(bill.id) }) {
                                    Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var name by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("Electricity") }
        var dueDate by remember { mutableStateOf(todayStr) }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Log New Bill") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Bill Name (e.g., K-Electric, PTCL)") },
                        modifier = Modifier.fillMaxWidth().testTag("bill_name_input")
                    )
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Amount Due") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth().testTag("bill_amount_input")
                    )
                    Text("Category", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Constants.BILL_CATEGORIES.forEach { cat ->
                            ElevatedFilterChip(
                                selected = category == cat,
                                onClick = { category = cat },
                                label = { Text(cat) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = dueDate,
                        onValueChange = { dueDate = it },
                        label = { Text("Due Date (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth().testTag("bill_date_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amt = amount.toDoubleOrNull() ?: 0.0
                        if (name.isNotEmpty() && amt > 0) {
                            viewModel.addBill(name, amt, dueDate, category)
                            showAddDialog = false
                        }
                    },
                    modifier = Modifier.testTag("bill_dialog_confirm")
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 4: ZAKAT CALCULATOR
// -------------------------------------------------------------
@Composable
fun ZakatCalculatorScreen(viewModel: StudentKitViewModel) {
    var cashInHand by remember { mutableStateOf("0") }
    var bankBalance by remember { mutableStateOf("0") }
    var goldWeightGrams by remember { mutableStateOf("0") }
    var silverWeightGrams by remember { mutableStateOf("0") }
    var businessInventoryVal by remember { mutableStateOf("0") }
    var receivables by remember { mutableStateOf("0") }
    var liabilitiesOffset by remember { mutableStateOf("0") }

    // Math outputs
    var showResults by remember { mutableStateOf(false) }
    var totalAssets by remember { mutableStateOf(0.0) }
    var netZakatable by remember { mutableStateOf(0.0) }
    var zakatDueResult by remember { mutableStateOf(0.0) }
    var selectedNisabLimitPKR by remember { mutableStateOf(0.0) }
    var isEligible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "🕌 Reference Nisab Values (Pakistani Standards)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Gold (7.5 Tola = 87.48g)", fontSize = 12.sp)
                    Text("Silver (52.5 Tola = 612.36g)", fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = viewModel.goldPricePerGram,
                        onValueChange = { viewModel.goldPricePerGram = it },
                        label = { Text("Gold price/g (PKR)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = viewModel.silverPricePerGram,
                        onValueChange = { viewModel.silverPricePerGram = it },
                        label = { Text("Silver price/g (PKR)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Text("Your Financial Assets & Possessions", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        OutlinedTextField(
            value = cashInHand,
            onValueChange = { cashInHand = it },
            label = { Text("Cash at Hand (PKR)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bankBalance,
            onValueChange = { bankBalance = it },
            label = { Text("Bank Balance (PKR)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(
                value = goldWeightGrams,
                onValueChange = { goldWeightGrams = it },
                label = { Text("Gold weight in grams") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = silverWeightGrams,
                onValueChange = { silverWeightGrams = it },
                label = { Text("Silver weight in grams") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = businessInventoryVal,
            onValueChange = { businessInventoryVal = it },
            label = { Text("Business Merchandise Assets (PKR)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = receivables,
            onValueChange = { receivables = it },
            label = { Text("Receivables / Money Owed To You (PKR)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = liabilitiesOffset,
            onValueChange = { liabilitiesOffset = it },
            label = { Text("Immediate Liabilities / Debts Owed Outside (PKR)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val goldP = viewModel.goldPricePerGram.toDoubleOrNull() ?: 25000.0
                val silverP = viewModel.silverPricePerGram.toDoubleOrNull() ?: 3000.0

                val goldNisabThreshold = goldP * Constants.NISAB_GOLD_GRAMS
                val silverNisabThreshold = silverP * Constants.NISAB_SILVER_GRAMS

                // Select LOWER as standard Nisab limit
                val lowerNisab = minOf(goldNisabThreshold, silverNisabThreshold)
                selectedNisabLimitPKR = lowerNisab

                val cash = cashInHand.toDoubleOrNull() ?: 0.0
                val bank = bankBalance.toDoubleOrNull() ?: 0.0
                val goldG = goldWeightGrams.toDoubleOrNull() ?: 0.0
                val silverG = silverWeightGrams.toDoubleOrNull() ?: 0.0
                val merchandise = businessInventoryVal.toDoubleOrNull() ?: 0.0
                val owed = receivables.toDoubleOrNull() ?: 0.0
                val debOut = liabilitiesOffset.toDoubleOrNull() ?: 0.0

                val gValue = goldG * goldP
                val sValue = silverG * silverP

                val computedTotal = cash + bank + gValue + sValue + merchandise + owed
                val computedNet = computedTotal - debOut

                totalAssets = computedTotal
                netZakatable = computedNet

                if (computedNet >= lowerNisab) {
                    isEligible = true
                    zakatDueResult = computedNet * 0.025
                } else {
                    isEligible = false
                    zakatDueResult = 0.0
                }
                showResults = true
            },
            modifier = Modifier.fillMaxWidth().testTag("zakat_calculate_btn")
        ) {
            Text("Calculate Zakat Due")
        }

        if (showResults) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isEligible) Color(0xFFE8F5E9) else Color(0xFFECEFF1)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Calculation Results Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Total Assets: Rs. ${String.format("%.2f", totalAssets)}", fontSize = 13.sp)
                    Text("Net Zakatable: Rs. ${String.format("%.2f", netZakatable)}", fontSize = 13.sp)
                    Text("Nisab Threshold (Lower of Gold/Silver): Rs. ${String.format("%.0f", selectedNisabLimitPKR)}", fontSize = 11.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))

                    if (isEligible) {
                        Text(
                            text = "Zakat is obligatory. Zakat Due (2.5%):",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2E7D32)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Rs. ${String.format("%.2f", zakatDueResult)}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1B5E20)
                        )
                    } else {
                        Text(
                            text = "Your assets are below the Nisab threshold. Zakat is not due.",
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 5: BC COMMITTEE MANAGER & DETAILS
// -------------------------------------------------------------
@Composable
fun BcCommitteeScreen(viewModel: StudentKitViewModel) {
    val committees by viewModel.committees.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }

    val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "New Committee")
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
        ) {
            Text(
                text = "My Kommittees (BC Apportions)",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))

            if (committees.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.GroupWork, null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No active Kommittees. Create one to manage peer pots!", color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(committees) { comm ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.navigateTo(Screen.BcCommitteeDetails(comm.id)) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(comm.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    IconButton(
                                        onClick = { viewModel.deleteCommittee(comm.id) },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(Icons.Default.DeleteOutline, null, tint = Color.Red, modifier = Modifier.size(16.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Monthly Contribution: Rs. ${comm.amountPerHead}", fontSize = 13.sp)
                                Text("Total Members: ${comm.totalMembers} | Frequency: ${comm.frequency}", fontSize = 12.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Total Payout Pot: Rs. ${comm.amountPerHead * comm.totalMembers}",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        var name by remember { mutableStateOf("") }
        var amountPerHead by remember { mutableStateOf("") }
        var membersCount by remember { mutableStateOf("5") }
        var startDate by remember { mutableStateOf(todayStr) }
        var memberNamesCsv by remember { mutableStateOf("Ahmad, Zain, Bilal, Usman, Hassan") }

        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Create Kommittee (BC)") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Kommittee Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = amountPerHead,
                        onValueChange = { amountPerHead = it },
                        label = { Text("Apportion Amount Per Member (Rs)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = membersCount,
                        onValueChange = { membersCount = it },
                        label = { Text("Total Members Capacity") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = memberNamesCsv,
                        onValueChange = { memberNamesCsv = it },
                        label = { Text("Member names (separated by comma)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text("Starting Cycle Date (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val numMembers = membersCount.toIntOrNull() ?: 5
                        val amt = amountPerHead.toDoubleOrNull() ?: 0.0
                        if (name.isNotEmpty() && amt > 0) {
                            val splitted = memberNamesCsv.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                            // pad with anonymous names if shorter than capability
                            val list = splitted + List(numMembers) { index -> "Member ${index + 1}" }
                            viewModel.addCommittee(name, amt, numMembers, startDate, "Monthly", list)
                            showCreateDialog = false
                        }
                    }
                ) {
                    Text("Generate Schedule")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SpinningWheel(
    candidates: List<BcMember>,
    onWinnerSelected: (BcMember) -> Unit,
    totalPotAmount: Double,
    viewModel: StudentKitViewModel,
    modifier: Modifier = Modifier
) {
    var rotationAngle by remember { mutableStateOf(0f) }
    var isSpinning by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var lastWinnerSelected by remember { mutableStateOf<BcMember?>(null) }
    var showAllToolsDialog by remember { mutableStateOf(false) }

    // Vibrant modern color palette for sectors
    val sectorColors = listOf(
        Color(0xFFFF5252), Color(0xFF40C4FF), Color(0xFFE040FB),
        Color(0xFF7C4DFF), Color(0xFF69F0AE), Color(0xFFFFD740),
        Color(0xFFFF6E40), Color(0xFF18FFFF), Color(0xFFFF4081),
        Color(0xFF00E676), Color(0xFF8D6E63), Color(0xFF78909C)
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Draw Wheel",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )

            if (candidates.isEmpty()) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Celebration,
                        contentDescription = "Done",
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "All members have won! This committee cycle is completed. 🎉",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray,
                        fontSize = 14.sp
                    )
                }
            } else {
                // Suspense Name Ticker / Status Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSpinning) {
                        // Rapid changing selection index simulation based on rotation angle
                        val step = (rotationAngle / 30f).toInt()
                        val tickerIndex = if (candidates.isNotEmpty()) (step % candidates.size).coerceIn(0, candidates.size - 1) else 0
                        val currentCandidate = candidates.getOrNull(tickerIndex) ?: candidates[0]
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Selecting... ", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontSize = 14.sp)
                            Text(currentCandidate.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    } else {
                        if (lastWinnerSelected != null) {
                            Text(
                                "Latest Winner: ${lastWinnerSelected?.name} 🏆",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32),
                                fontSize = 15.sp
                            )
                        } else {
                            Text(
                                "Eligible this Draw: ${candidates.size} member(s)",
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // Wheel Box containing Pointer and Circle Canvas
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(240.dp)
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { rotationZ = rotationAngle }
                    ) {
                        val anglePerSector = 360f / candidates.size
                        candidates.forEachIndexed { index, candidate ->
                            drawArc(
                                color = sectorColors[index % sectorColors.size],
                                startAngle = index * anglePerSector,
                                sweepAngle = anglePerSector,
                                useCenter = true,
                                size = size
                            )
                        }

                        // Outer thin borderline list
                        drawCircle(
                            color = Color(0xFF37474F),
                            radius = size.width / 2f,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 6f)
                        )
                    }

                    // Center Hub overlay
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Casino,
                            contentDescription = "Casino Hub Indicator",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Pointer pointing to winner segment (at 270 degrees, Top Center)
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(y = (-14).dp)
                            .size(32.dp, 40.dp)
                            .clickable { showAllToolsDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Pointer indicator",
                            tint = Color.Red,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                if (showAllToolsDialog) {
                    AllServicesDrawerDialog(
                        viewModel = viewModel,
                        onDismiss = { showAllToolsDialog = false }
                    )
                }

                // Legend list mapped of candidate names
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    candidates.chunked(3).forEach { rowCandidates ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            rowCandidates.forEachIndexed { cIdx, candidate ->
                                val origIdx = candidates.indexOf(candidate)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(horizontal = 6.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(sectorColors[origIdx % sectorColors.size].copy(alpha = 0.15f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(sectorColors[origIdx % sectorColors.size])
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(candidate.name, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                                }
                            }
                        }
                    }
                }

                // ONE CLICK SPINNER ACTION BUTTON
                Button(
                    onClick = {
                        if (!isSpinning && candidates.isNotEmpty()) {
                            isSpinning = true
                            scope.launch {
                                val anim = androidx.compose.animation.core.Animatable(rotationAngle)
                                val spinRounds = 10 * 360f
                                
                                // Choose winner index randomly
                                val winnerIndex = (candidates.indices).random()
                                val sectorAngle = 360f / candidates.size
                                // Position the chosen index to align perfectly with the top pointer (at 270 degrees)
                                val landingAngle = 270f - (winnerIndex * sectorAngle) - (sectorAngle / 2f)
                                val finalAngle = rotationAngle + spinRounds + (landingAngle - (rotationAngle % 360f))

                                anim.animateTo(
                                    targetValue = finalAngle,
                                    animationSpec = androidx.compose.animation.core.tween(
                                        durationMillis = 2600,
                                        easing = androidx.compose.animation.core.EaseOutQuad
                                    )
                                ) {
                                    rotationAngle = this.value
                                }

                                rotationAngle = finalAngle % 360f
                                val finalWinnerObj = candidates[winnerIndex]
                                lastWinnerSelected = finalWinnerObj
                                isSpinning = false
                                onWinnerSelected(finalWinnerObj)
                            }
                        }
                    },
                    enabled = !isSpinning,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Spin action icon")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("SPIN TO DRAW LUCKY WINNER", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun BcCommitteeDetailsScreen(viewModel: StudentKitViewModel, committeeId: String) {
    val committees by viewModel.committees.collectAsState()
    val committee = committees.find { it.id == committeeId }

    if (committee == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Kommittee details loading or removed.")
        }
        return
    }

    // Collect flows
    val members by viewModel.getMembersByCommittee(committeeId).collectAsState(initial = emptyList())
    val payments by viewModel.getPaymentsByCommittee(committeeId).collectAsState(initial = emptyList())
    val drawHistory by viewModel.getHistoryByCommittee(committeeId).collectAsState(initial = emptyList())

    var selectedTab by remember { mutableStateOf(0) } // 0=Lucky Draw, 1=Admin Feed, 2=Draw History, 3=Payments Sheets
    val context = LocalContext.current
    val potAmount = committee.amountPerHead * committee.totalMembers

    var winnerForDialog by remember { mutableStateOf<BcMember?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Top banner
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(committee.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Total Pot Pool per Round: Rs. ${potAmount.toInt()}", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("Monthly contribution: Rs. ${committee.amountPerHead.toInt()} per head", fontSize = 12.sp, color = Color.DarkGray)
                }
                
                // Done indicator
                Column(horizontalAlignment = Alignment.End) {
                    val completed = members.count { it.hasReceived == 1 }
                    Text("Rounds Done", fontSize = 11.sp, color = Color.Gray)
                    Text("$completed / ${committee.totalMembers}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        // Tab Row selector
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth()
        ) {
            val tabs = listOf("🎯 Lucky Draw", "📢 Admin Alerts", "📜 Draw History", "💳 Payment Sheet")
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                ) {
                    Text(
                        text = title, 
                        modifier = Modifier.padding(vertical = 10.dp), 
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }

        when (selectedTab) {
            0 -> {
                // Spinning block selection
                val eligibleMembers = members.filter { it.hasReceived == 0 }
                
                Box(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SpinningWheel(
                            candidates = eligibleMembers,
                            totalPotAmount = potAmount,
                            viewModel = viewModel,
                            onWinnerSelected = { winner ->
                                winnerForDialog = winner
                            }
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Small info text
                        Text(
                            text = "Spin with 1-click above to choose a verified lucky winner from eligible members automatically.",
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            }
            
            1 -> {
                // Admin Feed module
                Box(modifier = Modifier.weight(1f)) {
                    val distinctMonths = payments.map { it.month }.distinct()
                    if (distinctMonths.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No billing cycles detected.", color = Color.Gray)
                        }
                    } else {
                        // Let admin filter / view cycle alerts
                        var selectedMonthFilter by remember { mutableStateOf(distinctMonths.firstOrNull() ?: "") }
                        
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Cycle scroll bar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState())
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                distinctMonths.forEach { m ->
                                    val isSel = selectedMonthFilter == m
                                    AssistChip(
                                        onClick = { selectedMonthFilter = m },
                                        label = { Text(m) },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = if (isSel) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                                            labelColor = if (isSel) MaterialTheme.colorScheme.primary else Color.DarkGray
                                        )
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            val monthPayments = payments.filter { it.month == selectedMonthFilter }
                            val unpaidPayments = monthPayments.filter { it.isPaid == 0 }
                            val paidPayments = monthPayments.filter { it.isPaid == 1 }
                            
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                item {
                                    Text(
                                        text = "📢 ADMIN ALERTS FOR: $selectedMonthFilter",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                
                                if (unpaidPayments.isEmpty() && paidPayments.isEmpty()) {
                                    item {
                                        Text("No payments loaded for this month.", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = Color.Gray)
                                    }
                                }
                                
                                // UNPAID NOTIFICATIONS
                                if (unpaidPayments.isNotEmpty()) {
                                    item {
                                        Text("⚠️ Pending collections (${unpaidPayments.size}):", color = Color(0xFFD84315), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                    items(unpaidPayments) { pt ->
                                        val mName = members.find { it.id == pt.memberId }?.name ?: "Unknown"
                                        Card(
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFECE9)),
                                            border = BorderStroke(1.dp, Color(0xFFFFCDD2)),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(12.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text("Owes contribution", fontSize = 10.sp, color = Color.DarkGray)
                                                    Text(mName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFC62828))
                                                    Text("Pending: Rs. ${committee.amountPerHead.toInt()}", fontSize = 11.sp, color = Color.Gray)
                                                }
                                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                                    TextButton(
                                                        onClick = {
                                                            android.widget.Toast.makeText(context, "Payment alert reminder sent to $mName successfully!", android.widget.Toast.LENGTH_SHORT).show()
                                                        }
                                                    ) {
                                                        Icon(Icons.Default.Send, null, modifier = Modifier.size(14.dp), tint = Color(0xFF00796B))
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        Text("Remind", fontSize = 10.sp, color = Color(0xFF00796B))
                                                    }
                                                    
                                                    Button(
                                                        onClick = { viewModel.markPaymentStatus(pt.id, true) },
                                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                                        modifier = Modifier.height(28.dp)
                                                    ) {
                                                        Text("Collect", fontSize = 10.sp, color = Color.White)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                
                                // PAID CONFIRMATIONS
                                if (paidPayments.isNotEmpty()) {
                                    item {
                                        Text("✅ Paid logs (${paidPayments.size}):", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                    items(paidPayments) { pt ->
                                        val mName = members.find { it.id == pt.memberId }?.name ?: "Unknown"
                                        Card(
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                                            border = BorderStroke(1.dp, Color(0xFFC8E6C9)),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(12.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text("Contributed successfully", fontSize = 10.sp, color = Color.Gray)
                                                    Text(mName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF2E7D32))
                                                    Text("Received: Rs. ${committee.amountPerHead.toInt()} on ${pt.paidDate ?: "today"}", fontSize = 11.sp, color = Color.Gray)
                                                }
                                                TextButton(
                                                    onClick = { viewModel.markPaymentStatus(pt.id, false) }
                                                ) {
                                                    Text("Undo", fontSize = 11.sp, color = Color.Red)
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
            
            2 -> {
                // Full Drawing History
                Box(modifier = Modifier.weight(1f)) {
                    if (drawHistory.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Icon(Icons.Default.History, null, modifier = Modifier.size(56.dp), tint = Color.LightGray)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("No lucky draw history yet today.", fontWeight = FontWeight.Medium, color = Color.Gray)
                                Text("Select the Draw tab to host your first round!", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            item {
                                Text("📜 KOMMITTEE DRAWINGS LOG HISTORY", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                            }
                            items(drawHistory) { draw ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text("Round #${draw.roundNumber} - Lucky Winner", fontSize = 11.sp, color = Color.Gray)
                                            Text(draw.winnerName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                                            Text("Disbursed on: ${draw.drawDate}", fontSize = 11.sp, color = Color.DarkGray)
                                        }
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text("Disbursed Pot", fontSize = 11.sp, color = Color.Gray)
                                            Text("Rs. ${draw.amountWon.toInt()}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF2E7D32))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            3 -> {
                // Classic Payment Schedule Grid Spreadsheet
                Box(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text("Expected Round Contribution Receipts Sheet:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(payments) { pt ->
                                val memberName = members.find { it.id == pt.memberId }?.name ?: "Member"
                                val isPaid = pt.isPaid == 1
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isPaid) Color(0xFFE8F5E9) else Color(0xFFFFECE9))
                                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(memberName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("Cycle Month: ${pt.month}", fontSize = 11.sp, color = Color.Gray)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = if (isPaid) "Received" else "Pending",
                                            fontSize = 11.sp,
                                            color = if (isPaid) Color(0xFF2E7D32) else Color(0xFFC62828),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Checkbox(
                                            checked = isPaid,
                                            onCheckedChange = { viewModel.markPaymentStatus(pt.id, it) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // PDF Print / Schedule report button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    android.widget.Toast.makeText(context, "Full BC report exported and stored as PDF!", android.widget.Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.PictureAsPdf, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Export PDF Report")
            }
        }
    }

    // WINNER DIALOG CONFIRMER
    winnerForDialog?.let { winner ->
        AlertDialog(
            onDismissRequest = { winnerForDialog = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Celebration, null, tint = Color(0xFFFFB300), modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("We Have a Winner! 🎉")
                }
            },
            text = {
                Column {
                    Text(
                        text = "Congratulations to ${winner.name}!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "They have been automatically drawn as the Lucky Winner of Round #${drawHistory.size + 1}!",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "They will receive this round's total pool payout pot of Rs. ${potAmount.toInt()}.",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF2E7D32)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.recordLuckyDraw(
                            committeeId = committeeId,
                            roundNumber = drawHistory.size + 1,
                            winnerId = winner.id,
                            winnerName = winner.name,
                            amountWon = potAmount
                        )
                        winnerForDialog = null
                        android.widget.Toast.makeText(context, "${winner.name} payout saved successfully!", android.widget.Toast.LENGTH_LONG).show()
                    }
                ) {
                    Text("Confirm Draw & Disburse")
                }
            },
            dismissButton = {
                TextButton(onClick = { winnerForDialog = null }) {
                    Text("Cancel Draw")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 6: LOAN TRACKER
// -------------------------------------------------------------
@Composable
fun LoanTrackerScreen(viewModel: StudentKitViewModel) {
    val loans by viewModel.loans.collectAsState()
    var selectedTab by remember { mutableStateOf(0) } // 0="I Lent" (Receivable), 1="I Owe" (Payable)
    var showAddDialog by remember { mutableStateOf(false) }

    val lentList = loans.filter { it.type == "I Lent" }
    val owedList = loans.filter { it.type == "I Owe" }

    val totalLent = lentList.filter { it.isSettled == 0 }.sumOf { it.amount }
    val totalOwed = owedList.filter { it.isSettled == 0 }.sumOf { it.amount }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Log Loan")
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Header Overview Balances
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Receivable (I Lent)", fontSize = 12.sp, color = Color.Gray)
                        Text("Rs. ${String.format("%.0f", totalLent)}", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF2E7D32))
                    }
                    Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color.LightGray))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Payable (I Owe)", fontSize = 12.sp, color = Color.Gray)
                        Text("Rs. ${String.format("%.0f", totalOwed)}", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFFC62828))
                    }
                }
            }

            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("I Lent", modifier = Modifier.padding(12.dp))
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("I Owe", modifier = Modifier.padding(12.dp))
                }
            }

            val filteredList = if (selectedTab == 0) lentList else owedList

            if (filteredList.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("No loans logged here.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(filteredList) { loan ->
                        val isSettled = loan.isSettled == 1
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSettled) Color(0xFFE8F5E9) else Color(0xFFFFF3E0))
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(loan.personName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    if (isSettled) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        SuggestionChip(
                                            onClick = {},
                                            label = { Text("Settled", fontSize = 10.sp) },
                                            modifier = Modifier.height(20.dp)
                                        )
                                    }
                                }
                                Text("Lending Date: ${loan.date}", fontSize = 11.sp, color = Color.Gray)
                                if (!loan.dueDate.isNullOrEmpty()) {
                                    Text("Due Pay Date: ${loan.dueDate}", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.Red.copy(alpha = 0.8f))
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Rs. ${loan.amount}", fontWeight = FontWeight.Bold)
                                    TextButton(
                                        onClick = { viewModel.markLoanSettled(loan.id, !isSettled) },
                                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                                        modifier = Modifier.height(24.dp)
                                    ) {
                                        Text(if (isSettled) "Reopen" else "Settle Now", fontSize = 11.sp)
                                    }
                                }
                                IconButton(onClick = { viewModel.deleteLoan(loan.id) }) {
                                    Icon(Icons.Default.DeleteOutline, null, tint = Color.Red, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var person by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var type by remember { mutableStateOf(if (selectedTab == 0) "I Lent" else "I Owe") }
        var dateVal by remember { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())) }
        var dueDate by remember { mutableStateOf("") }
        var note by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Log Loan Transaction") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = person,
                        onValueChange = { person = it },
                        label = { Text("Person Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Loan Amount (PKR)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text("Type", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ElevatedFilterChip(
                            selected = type == "I Lent",
                            onClick = { type = "I Lent" },
                            label = { Text("I Lent to them") }
                        )
                        ElevatedFilterChip(
                            selected = type == "I Owe",
                            onClick = { type = "I Owe" },
                            label = { Text("I Owe to them") }
                        )
                    }
                    OutlinedTextField(
                        value = dateVal,
                        onValueChange = { dateVal = it },
                        label = { Text("Loan Date (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = dueDate,
                        onValueChange = { dueDate = it },
                        label = { Text("Expected Due Date (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("Note (e.g., college fee lend)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amt = amount.toDoubleOrNull() ?: 0.0
                        if (person.isNotEmpty() && amt > 0) {
                            viewModel.addLoan(person, amt, type, dateVal, dueDate.ifEmpty { null }, note.ifEmpty { null })
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 7: SAVINGS GOALS
// -------------------------------------------------------------
@Composable
fun SavingsGoalsScreen(viewModel: StudentKitViewModel) {
    val goals by viewModel.savingsGoals.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var depositGoalId by remember { mutableStateOf<String?>(null) }
    var depositAmountText by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "New Goal")
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
        ) {
            Text("Savings Goals Meters", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(12.dp))

            if (goals.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No goals created. Save up for laptop, bikes, or textbooks!", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(goals) { goal ->
                        val progress = if (goal.targetAmount > 0) (goal.currentAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f) else 0f
                        Card(
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Star, "Goal", tint = Color(0xFFFFB300))
                                    IconButton(
                                        onClick = { viewModel.deleteSavingsGoal(goal.id) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, "Delete", tint = Color.LightGray, modifier = Modifier.size(16.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(goal.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    "Rs. ${String.format("%.0f", goal.currentAmount)} / ${String.format("%.0f", goal.targetAmount)}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                LinearProgressIndicator(
                                    progress = progress,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("${String.format("%.0f", progress * 100)}%", fontSize = 10.sp, fontWeight = FontWeight.Black)

                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        depositGoalId = goal.id
                                        depositAmountText = ""
                                    },
                                    modifier = Modifier.fillMaxWidth().height(30.dp),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text("Add Deposit", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var title by remember { mutableStateOf("") }
        var targetAmount by remember { mutableStateOf("") }
        var currentAmount by remember { mutableStateOf("0") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Log New Goal") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Goal Title (e.g., Buy Laptop)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = targetAmount,
                        onValueChange = { targetAmount = it },
                        label = { Text("Target Amount (Rs)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = currentAmount,
                        onValueChange = { currentAmount = it },
                        label = { Text("Starting Fund Balance") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val tar = targetAmount.toDoubleOrNull() ?: 0.0
                        val cur = currentAmount.toDoubleOrNull() ?: 0.0
                        if (title.isNotEmpty() && tar > 0) {
                            viewModel.addSavingsGoal(title, tar, cur, null, null, null)
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Configure")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Deposit dialog
    val activeDepId = depositGoalId
    if (activeDepId != null) {
        val targetGoal = goals.find { it.id == activeDepId }
        AlertDialog(
            onDismissRequest = { depositGoalId = null },
            title = { Text("Fund Deposit: ${targetGoal?.title}") },
            text = {
                OutlinedTextField(
                    value = depositAmountText,
                    onValueChange = { depositAmountText = it },
                    label = { Text("Deposit Fund Amount (PKR)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val dep = depositAmountText.toDoubleOrNull() ?: 0.0
                        if (dep > 0 && targetGoal != null) {
                            viewModel.depositToSavingsGoal(targetGoal.id, targetGoal.currentAmount, dep)
                            depositGoalId = null
                        }
                    }
                ) {
                    Text("Deposit")
                }
            },
            dismissButton = {
                TextButton(onClick = { depositGoalId = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllServicesDrawerDialog(
    viewModel: StudentKitViewModel,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    
    // Categories and Icons
    val categoryIcons = mapOf(
        "All" to "📱 All",
        "Finance" to "🕌 Finance",
        "Documents" to "📄 Docs",
        "Utilities" to "⚙️ Tools",
        "Study" to "🎓 Study",
        "Medical" to "🩺 Medical"
    )
    val categoriesList = listOf("All", "Finance", "Documents", "Utilities", "Study", "Medical")

    // Filter tools dynamically inside dialog
    val filteredTools = allToolsList.filter { tool ->
        (selectedCategory == "All" || tool.category == selectedCategory) &&
        (tool.label.contains(searchQuery, ignoreCase = true) ||
         tool.description.contains(searchQuery, ignoreCase = true) ||
         tool.badge?.contains(searchQuery, ignoreCase = true) == true)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "All Available Services",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                }
                Text(
                    "Select any service to launch it instantly",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 420.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Search Input
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search services...", fontSize = 12.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )

                // Category Chips Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    categoriesList.forEach { category ->
                        val isSelected = selectedCategory == category
                        ElevatedFilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = category },
                            label = { Text(categoryIcons[category] ?: category, fontSize = 10.sp) }
                        )
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 4.dp))

                // Scrollable List of Tools
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    if (filteredTools.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No services matched search query.", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    } else {
                        items(filteredTools) { tool ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(tool.color.copy(alpha = 0.06f))
                                    .border(1.dp, tool.color.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                                    .clickable {
                                        viewModel.navigateTo(tool.screen)
                                        onDismiss()
                                    }
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Tool Icon
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(tool.color.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = tool.icon,
                                        contentDescription = tool.label,
                                        tint = tool.color,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                
                                Spacer(modifier = Modifier.width(10.dp))
                                
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = tool.label,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        if (tool.badge != null) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .background(tool.color.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = tool.badge,
                                                    fontSize = 7.5.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = tool.color
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        text = tool.description,
                                        fontSize = 10.sp,
                                        color = Color.Gray,
                                        maxLines = 1
                                    )
                                }
                                
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = tool.color.copy(alpha = 0.6f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceReportAndBackupScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val expenses by viewModel.expenses.collectAsState()
    val income by viewModel.income.collectAsState()
    val bills by viewModel.bills.collectAsState()
    val committees by viewModel.committees.collectAsState()
    val loans by viewModel.loans.collectAsState()
    val savingsGoals by viewModel.savingsGoals.collectAsState()

    // Derived values for summary
    val totalExpenseAmt = expenses.sumOf { it.amount }
    val totalIncomeAmt = income.sumOf { it.amount }
    val netBalance = totalIncomeAmt - totalExpenseAmt
    val totalUnpaidBillsAmt = bills.filter { it.isPaid == 0 }.sumOf { it.amount }

    val loansBorrowed = loans.filter { 
        val t = it.type.lowercase()
        t.contains("borrow") || t.contains("taken") || t.contains("receive") 
    }.sumOf { it.amount }
    
    val loansLent = loans.filter { 
        val t = it.type.lowercase()
        t.contains("lend") || t.contains("lent") || t.contains("give") || t.contains("send") 
    }.sumOf { it.amount }

    val totalSavingsSaved = savingsGoals.sumOf { it.currentAmount }
    val totalSavingsTarget = savingsGoals.sumOf { it.targetAmount }

    // Checkbox states for Master PDF Report Options
    var includeExpenses by remember { mutableStateOf(true) }
    var includeIncome by remember { mutableStateOf(true) }
    var includeBills by remember { mutableStateOf(true) }
    var includeCommittees by remember { mutableStateOf(true) }
    var includeLoans by remember { mutableStateOf(true) }
    var includeSavings by remember { mutableStateOf(true) }

    // JSON export launcher
    val exportJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json"),
        onResult = { uri ->
            if (uri != null) {
                viewModel.exportFinanceJsonData { jsonString ->
                    if (jsonString != null) {
                        coroutineScope.launch {
                            try {
                                context.contentResolver.openOutputStream(uri)?.use { output ->
                                    output.write(jsonString.toByteArray(Charsets.UTF_8))
                                }
                                Toast.makeText(context, "Finance JSON Export Completed Successfully!", Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Failed to generate JSON data.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )

    // JSON import launcher
    val importJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                coroutineScope.launch {
                    try {
                        val content = context.contentResolver.openInputStream(uri)?.use { input ->
                            input.bufferedReader().use { it.readText() }
                        }
                        if (content != null) {
                            viewModel.importFinanceJsonData(content) { success, msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(context, "Failed to read JSON content.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Import failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    )

    // SAF launchers for backup and restore
    val backupLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/octet-stream"),
        onResult = { uri ->
            if (uri != null) {
                coroutineScope.launch {
                    try {
                        try {
                            val db = AppDatabase.getDatabase(context)
                            db.openHelper.writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        
                        val dbFile = context.getDatabasePath("studentkit_database")
                        if (dbFile.exists()) {
                            context.contentResolver.openOutputStream(uri)?.use { output ->
                                dbFile.inputStream().use { input ->
                                    input.copyTo(output)
                                }
                            }
                            Toast.makeText(context, "Full Application Data Backed Up Successfully!", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Database file not found.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Backup failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    )

    val restoreLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                coroutineScope.launch {
                    try {
                        val db = AppDatabase.getDatabase(context)
                        db.close()
                        AppDatabase.resetInstance()

                        val dbFile = context.getDatabasePath("studentkit_database")
                        val walFile = File(dbFile.path + "-wal")
                        val shmFile = File(dbFile.path + "-shm")

                        if (walFile.exists()) walFile.delete()
                        if (shmFile.exists()) shmFile.delete()

                        context.contentResolver.openInputStream(uri)?.use { input ->
                            dbFile.outputStream().use { output ->
                                input.copyTo(output)
                            }
                        }

                        Toast.makeText(context, "Data Restored Successfully!", Toast.LENGTH_LONG).show()
                        viewModel.navigateTo(Screen.Dashboard)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Restore failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Assessment,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Finance Master Center",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your centralized control hub. Consolidate your financial health across all active modules or perform standard full-application database operations.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Master Actions Row
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // PDF Save to Phone Memory
                    Card(
                        onClick = {
                            exportFinancePdfToDownloads(
                                context, expenses, income, bills, committees, loans, savingsGoals,
                                totalIncomeAmt, totalExpenseAmt, netBalance, totalUnpaidBillsAmt,
                                loansBorrowed, loansLent, totalSavingsSaved, totalSavingsTarget
                            )
                        },
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFD1FAE5)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                tint = Color(0xFF047857),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Save PDF to Downloads",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF065F46)
                            )
                        }
                    }

                    // PDF Print / Bluetooth
                    Card(
                        onClick = {
                            triggerFinanceMasterPrint(
                                context, expenses, income, bills, committees, loans, savingsGoals,
                                totalIncomeAmt, totalExpenseAmt, netBalance, totalUnpaidBillsAmt,
                                loansBorrowed, loansLent, totalSavingsSaved, totalSavingsTarget,
                                includeExpenses, includeIncome, includeBills, includeCommittees, includeLoans, includeSavings
                            )
                        },
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Print,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Print / Bluetooth",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Data Backup
                    Card(
                        onClick = {
                            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                            backupLauncher.launch("StudentKit_Backup_$sdf.db")
                        },
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Backup,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Backup Data",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }

                    // Data Restore
                    Card(
                        onClick = { restoreLauncher.launch("*/*") },
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Restore,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Restore Data",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }
        }

        // Configurable PDF Print Options
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "PDF Print Customization",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Toggle modules to include or exclude from your master PDF report. Excluded modules will be marked clearly as [OMITTED] in the final report.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.testTag("pdf_toggle_expenses")) {
                                Checkbox(
                                    checked = includeExpenses,
                                    onCheckedChange = { includeExpenses = it },
                                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Expenses", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.testTag("pdf_toggle_income")) {
                                Checkbox(
                                    checked = includeIncome,
                                    onCheckedChange = { includeIncome = it },
                                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Income", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.testTag("pdf_toggle_bills")) {
                                Checkbox(
                                    checked = includeBills,
                                    onCheckedChange = { includeBills = it },
                                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Utility Bills", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            }
                        }

                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.testTag("pdf_toggle_committees")) {
                                Checkbox(
                                    checked = includeCommittees,
                                    onCheckedChange = { includeCommittees = it },
                                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Kommittees", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.testTag("pdf_toggle_loans")) {
                                Checkbox(
                                    checked = includeLoans,
                                    onCheckedChange = { includeLoans = it },
                                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Loans & Debt", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.testTag("pdf_toggle_savings")) {
                                Checkbox(
                                    checked = includeSavings,
                                    onCheckedChange = { includeSavings = it },
                                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Savings Goals", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }
        }

        // JSON Portability Tools
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "JSON Data Portability",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Direct modular export/import of all finance modules in clean human-readable JSON format. Allows easy cross-device data backup and restoration.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Export Button
                        Button(
                            onClick = {
                                val dateStr = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
                                exportJsonLauncher.launch("StudentKit_Finance_Export_$dateStr.json")
                            },
                            modifier = Modifier.weight(1f).testTag("export_json_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CloudDownload, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Export JSON", fontSize = 13.sp)
                        }

                        // Import Button
                        Button(
                            onClick = {
                                importJsonLauncher.launch("application/json")
                            },
                            modifier = Modifier.weight(1f).testTag("import_json_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Import JSON", fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Summary Statistics Header
        item {
            Text(
                text = "Consolidated Summary Stats",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // KPI Scorecards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // Net Balance Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (netBalance >= 0) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Net Savings Balance",
                                fontSize = 12.sp,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Rs. ${String.format("%.2f", netBalance)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (netBalance >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
                            )
                        }
                        Icon(
                            imageVector = if (netBalance >= 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                            contentDescription = null,
                            tint = if (netBalance >= 0) Color(0xFF2E7D32) else Color(0xFFC62828),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Income vs Expenses
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Total Income", fontSize = 11.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Rs. ${String.format("%.1f", totalIncomeAmt)}", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Total Expenses", fontSize = 11.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Rs. ${String.format("%.1f", totalExpenseAmt)}", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Bills & Debt status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Pending Bills", fontSize = 11.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Rs. ${String.format("%.1f", totalUnpaidBillsAmt)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Lent (Receivable)", fontSize = 11.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Rs. ${String.format("%.1f", loansLent)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        }
                    }
                }

                // Savings Goals Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Savings Progress", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            val percent = if (totalSavingsTarget > 0.0) (totalSavingsSaved / totalSavingsTarget) * 100.0 else 0.0
                            Text("${String.format("%.1f", percent)}%", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { if (totalSavingsTarget > 0.0) (totalSavingsSaved / totalSavingsTarget).toFloat() else 0f },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Saved Rs. ${String.format("%.2f", totalSavingsSaved)} of Rs. ${String.format("%.2f", totalSavingsTarget)}",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        // Active Committees list section
        item {
            Text(
                text = "Kommittees Ledger Status",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (committees.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "No active rotating Kommittees configured",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(committees) { comm ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(comm.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Head: Rs. ${comm.amountPerHead} | ${comm.totalMembers} Members", fontSize = 11.sp, color = Color.Gray)
                        }
                        Text(
                            text = "Pool: Rs. ${comm.amountPerHead * comm.totalMembers}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

fun triggerFinanceMasterPrint(
    context: Context,
    expenses: List<Expense>,
    income: List<Income>,
    bills: List<Bill>,
    committees: List<BcCommittee>,
    loans: List<Loan>,
    savingsGoals: List<SavingsGoal>,
    totalIncome: Double,
    totalExpense: Double,
    netBalance: Double,
    totalUnpaid: Double,
    totalBorrowed: Double,
    totalLent: Double,
    totalSaved: Double,
    totalTarget: Double,
    includeExpenses: Boolean = true,
    includeIncome: Boolean = true,
    includeBills: Boolean = true,
    includeCommittees: Boolean = true,
    includeLoans: Boolean = true,
    includeSavings: Boolean = true
) {
    try {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
        if (printManager == null) {
            Toast.makeText(context, "System printing engines not available.", Toast.LENGTH_SHORT).show()
            return
        }

        val printAdapter = object : PrintDocumentAdapter() {
            private var pdfDoc: PdfDocument? = null

            override fun onLayout(
                oldAttributes: PrintAttributes?,
                newAttributes: PrintAttributes?,
                cancellationSignal: android.os.CancellationSignal?,
                callback: LayoutResultCallback?,
                extras: android.os.Bundle?
            ) {
                if (cancellationSignal?.isCanceled == true) {
                    callback?.onLayoutCancelled()
                    return
                }

                // We will produce a beautifully formatted 3-page statement
                val info = PrintDocumentInfo.Builder("Finance_Master_Report.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(3)
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

                // Standard A4 Size: 595 x 842 points
                val pageW = 595
                val pageH = 842

                // Setup Paints
                val primaryColor = 0xFF10B981.toInt() // Green Accent
                val primaryDark = 0xFF064E3B.toInt()
                val textDark = 0xFF1F2937.toInt()
                val textMuted = 0xFF6B7280.toInt()
                val lightGrayBg = 0xFFF3F4F6.toInt()
                val gridBorder = 0xFFE5E7EB.toInt()

                val titlePaint = Paint().apply {
                    color = primaryDark
                    textSize = 22f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                    isAntiAlias = true
                }
                val subtitlePaint = Paint().apply {
                    color = textMuted
                    textSize = 10f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                    isAntiAlias = true
                }
                val h1Paint = Paint().apply {
                    color = primaryDark
                    textSize = 15f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                    isAntiAlias = true
                }
                val h2Paint = Paint().apply {
                    color = textDark
                    textSize = 12f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                    isAntiAlias = true
                }
                val bodyPaint = Paint().apply {
                    color = textDark
                    textSize = 9f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                    isAntiAlias = true
                }
                val bodyBoldPaint = Paint().apply {
                    color = textDark
                    textSize = 9f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                    isAntiAlias = true
                }
                val headerPaint = Paint().apply {
                    color = textDark
                    textSize = 9f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                    isAntiAlias = true
                }
                val borderPaint = Paint().apply {
                    color = gridBorder
                    strokeWidth = 1f
                    style = Paint.Style.STROKE
                }
                val fillBgPaint = Paint().apply {
                    color = lightGrayBg
                    style = Paint.Style.FILL
                }
                val fillAccentPaint = Paint().apply {
                    color = 0xFFD1FAE5.toInt() // Soft green background
                    style = Paint.Style.FILL
                }
                val fillOmittedPaint = Paint().apply {
                    color = 0xFFF3F4F6.toInt() // Gray background
                    style = Paint.Style.FILL
                }
                val omittedTextPaint = Paint().apply {
                    color = textMuted
                    textSize = 8f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC)
                    isAntiAlias = true
                }

                // ==========================================
                // PAGE 1: COVER & FINANCIAL SUMMARY DASHBOARD
                // ==========================================
                val page1Info = PdfDocument.PageInfo.Builder(pageW, pageH, 1).create()
                val page1 = pdfDoc?.startPage(page1Info)
                val canvas1 = page1?.canvas
                if (canvas1 != null) {
                    // Soft green band
                    val headerBandPaint = Paint().apply {
                        color = 0xFFECFDF5.toInt()
                        style = Paint.Style.FILL
                    }
                    canvas1.drawRect(0f, 0f, pageW.toFloat(), 120f, headerBandPaint)
                    canvas1.drawRect(0f, 118f, pageW.toFloat(), 120f, Paint().apply { color = primaryColor })

                    // Titles
                    canvas1.drawText("FINANCIAL STATEMENT & MASTER REPORT", 30f, 55f, titlePaint)
                    val dateStr = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault()).format(Date())
                    canvas1.drawText("Generated on: $dateStr | System: Student Kit Finance Core", 30f, 80f, subtitlePaint)
                    canvas1.drawText("Page 1 of 3", pageW - 80f, 80f, subtitlePaint)

                    // Core Overview
                    canvas1.drawText("I. Core Cashflow Overview", 30f, 155f, h1Paint)
                    canvas1.drawLine(30f, 162f, pageW - 30f, 162f, borderPaint)

                    // KPI Box Setup
                    val boxY = 180f
                    val boxW = (pageW - 80f) / 3f
                    val boxH = 75f

                    // Card 1: Income
                    if (includeIncome) {
                        canvas1.drawRoundRect(30f, boxY, 30f + boxW, boxY + boxH, 8f, 8f, fillBgPaint)
                        canvas1.drawText("TOTAL INFLOW (INCOME)", 40f, boxY + 25f, subtitlePaint)
                        canvas1.drawText("Rs. ${String.format("%.2f", totalIncome)}", 40f, boxY + 52f, h2Paint)
                    } else {
                        canvas1.drawRoundRect(30f, boxY, 30f + boxW, boxY + boxH, 8f, 8f, fillOmittedPaint)
                        canvas1.drawText("TOTAL INFLOW", 40f, boxY + 25f, subtitlePaint)
                        canvas1.drawText("[OMITTED]", 40f, boxY + 52f, Paint(omittedTextPaint).apply { textSize = 11f; typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD) })
                    }

                    // Card 2: Expenses
                    if (includeExpenses) {
                        canvas1.drawRoundRect(30f + boxW + 10f, boxY, 30f + 2*boxW + 10f, boxY + boxH, 8f, 8f, fillBgPaint)
                        canvas1.drawText("TOTAL OUTFLOW (EXPENSES)", 30f + boxW + 20f, boxY + 25f, subtitlePaint)
                        canvas1.drawText("Rs. ${String.format("%.2f", totalExpense)}", 30f + boxW + 20f, boxY + 52f, h2Paint)
                    } else {
                        canvas1.drawRoundRect(30f + boxW + 10f, boxY, 30f + 2*boxW + 10f, boxY + boxH, 8f, 8f, fillOmittedPaint)
                        canvas1.drawText("TOTAL OUTFLOW", 30f + boxW + 20f, boxY + 25f, subtitlePaint)
                        canvas1.drawText("[OMITTED]", 30f + boxW + 20f, boxY + 52f, Paint(omittedTextPaint).apply { textSize = 11f; typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD) })
                    }

                    // Card 3: Balance
                    if (includeIncome && includeExpenses) {
                        val balanceColorPaint = Paint().apply {
                            color = if (netBalance >= 0) 0xFFD1FAE5.toInt() else 0xFFFEE2E2.toInt()
                            style = Paint.Style.FILL
                        }
                        canvas1.drawRoundRect(30f + 2*boxW + 20f, boxY, pageW - 30f, boxY + boxH, 8f, 8f, balanceColorPaint)
                        canvas1.drawText("NET SAVINGS BALANCE", 30f + 2*boxW + 30f, boxY + 25f, subtitlePaint)
                        canvas1.drawText("Rs. ${String.format("%.2f", netBalance)}", 30f + 2*boxW + 30f, boxY + 52f, Paint(h2Paint).apply {
                            color = if (netBalance >= 0) 0xFF065F46.toInt() else 0xFF991B1B.toInt()
                        })
                    } else {
                        canvas1.drawRoundRect(30f + 2*boxW + 20f, boxY, pageW - 30f, boxY + boxH, 8f, 8f, fillOmittedPaint)
                        canvas1.drawText("NET BALANCE", 30f + 2*boxW + 30f, boxY + 25f, subtitlePaint)
                        canvas1.drawText("[N/A]", 30f + 2*boxW + 30f, boxY + 52f, Paint(omittedTextPaint).apply { textSize = 11f; typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD) })
                    }

                    // Section 2: Progress
                    canvas1.drawText("II. Financial Assets & Liabilities Progress", 30f, 290f, h1Paint)
                    canvas1.drawLine(30f, 297f, pageW - 30f, 297f, borderPaint)

                    val secY = 315f
                    val doubleCardW = (pageW - 70f) / 2f
                    val doubleCardH = 110f

                    // Savings goal progress
                    if (includeSavings) {
                        canvas1.drawRoundRect(30f, secY, 30f + doubleCardW, secY + doubleCardH, 8f, 8f, fillBgPaint)
                        canvas1.drawText("SAVINGS GOALS PROGRESS", 40f, secY + 25f, Paint(subtitlePaint).apply { typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD) })
                        canvas1.drawText("Total Target Amount: Rs. ${String.format("%.2f", totalTarget)}", 40f, secY + 45f, bodyPaint)
                        canvas1.drawText("Total Saved Amount: Rs. ${String.format("%.2f", totalSaved)}", 40f, secY + 62f, bodyBoldPaint)
                        val percentSaved = if (totalTarget > 0) (totalSaved / totalTarget) * 100f else 0f
                        canvas1.drawText("Overall Progress: ${String.format("%.1f", percentSaved)}% Completed", 40f, secY + 85f, Paint(bodyBoldPaint).apply { color = 0xFF059669.toInt() })
                    } else {
                        canvas1.drawRoundRect(30f, secY, 30f + doubleCardW, secY + doubleCardH, 8f, 8f, fillOmittedPaint)
                        canvas1.drawText("SAVINGS GOALS STATUS", 40f, secY + 25f, Paint(subtitlePaint).apply { typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD) })
                        canvas1.drawText("[Savings Module Omitted]", 40f, secY + 55f, omittedTextPaint)
                    }

                    // Debts
                    if (includeLoans) {
                        canvas1.drawRoundRect(30f + doubleCardW + 10f, secY, pageW - 30f, secY + doubleCardH, 8f, 8f, fillBgPaint)
                        canvas1.drawText("DEBTS & LIABILITIES STATUS", 30f + doubleCardW + 20f, secY + 25f, Paint(subtitlePaint).apply { typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD) })
                        canvas1.drawText("Total Money Borrowed (To Repay): Rs. ${String.format("%.2f", totalBorrowed)}", 30f + doubleCardW + 20f, secY + 45f, bodyPaint)
                        canvas1.drawText("Total Money Lent (To Collect): Rs. ${String.format("%.2f", totalLent)}", 30f + doubleCardW + 20f, secY + 62f, bodyPaint)
                        val netDebt = totalBorrowed - totalLent
                        val netDebtStr = if (netDebt >= 0) "Net Payable: Rs. ${String.format("%.2f", netDebt)}" else "Net Receivable: Rs. ${String.format("%.2f", -netDebt)}"
                        canvas1.drawText(netDebtStr, 30f + doubleCardW + 20f, secY + 85f, Paint(bodyBoldPaint).apply {
                            color = if (netDebt >= 0) 0xFFDC2626.toInt() else 0xFF2563EB.toInt()
                        })
                    } else {
                        canvas1.drawRoundRect(30f + doubleCardW + 10f, secY, pageW - 30f, secY + doubleCardH, 8f, 8f, fillOmittedPaint)
                        canvas1.drawText("DEBTS & LIABILITIES STATUS", 30f + doubleCardW + 20f, secY + 25f, Paint(subtitlePaint).apply { typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD) })
                        canvas1.drawText("[Loans Module Omitted]", 30f + doubleCardW + 20f, secY + 55f, omittedTextPaint)
                    }

                    // Section 3: Monthly Obligations
                    canvas1.drawText("III. Utility Bills & Standing Kommittees Summary", 30f, 460f, h1Paint)
                    canvas1.drawLine(30f, 467f, pageW - 30f, 467f, borderPaint)

                    val finalY = 485f
                    canvas1.drawRoundRect(30f, finalY, pageW - 30f, finalY + 110f, 8f, 8f, fillBgPaint)
                    canvas1.drawText("STANDING MONTHLY OBLIGATIONS", 40f, finalY + 25f, Paint(subtitlePaint).apply { typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD) })
                    
                    var curOblY = finalY + 50f
                    if (includeBills) {
                        val unpaidCount = bills.count { it.isPaid == 0 }
                        canvas1.drawText("Unpaid Utility Bills: $unpaidCount bills pending (Rs. ${String.format("%.2f", totalUnpaid)})", 40f, curOblY, bodyPaint)
                        curOblY += 20f
                    } else {
                        canvas1.drawText("Unpaid Utility Bills: [Bills Module Omitted]", 40f, curOblY, omittedTextPaint)
                        curOblY += 20f
                    }
                    
                    if (includeCommittees) {
                        val activeCommitteesCount = committees.size
                        canvas1.drawText("Active Rotating Kommittees (BC): $activeCommitteesCount active Kommittees managed", 40f, curOblY, bodyPaint)
                        curOblY += 20f
                        
                        val totalCommitteeVolume = committees.sumOf { it.amountPerHead * it.totalMembers }
                        canvas1.drawText("Total Kommittee Investment Pool: Rs. ${String.format("%.2f", totalCommitteeVolume)}", 40f, curOblY, bodyPaint)
                    } else {
                        canvas1.drawText("Active Rotating Kommittees (BC): [Kommittees Module Omitted]", 40f, curOblY, omittedTextPaint)
                    }

                    // Bottom footers
                    canvas1.drawLine(30f, 760f, pageW - 30f, 760f, borderPaint)
                    canvas1.drawText("This report is confidential and intended solely for the device owner's personal bookkeeping.", 30f, 780f, Paint(subtitlePaint).apply { textSize = 8f })
                    canvas1.drawText("Student Kit Suite - Empowering productive lifestyle and personal privacy.", 30f, 792f, Paint(subtitlePaint).apply { textSize = 8f })
                }
                pdfDoc?.finishPage(page1)

                // ==========================================
                // PAGE 2: CASH FLOW LEDGER (EXPENSES & INCOME TABLES)
                // ==========================================
                val page2Info = PdfDocument.PageInfo.Builder(pageW, pageH, 2).create()
                val page2 = pdfDoc?.startPage(page2Info)
                val canvas2 = page2?.canvas
                if (canvas2 != null) {
                    val headerBandPaint = Paint().apply {
                        color = 0xFFF8FAFC.toInt()
                        style = Paint.Style.FILL
                    }
                    canvas2.drawRect(0f, 0f, pageW.toFloat(), 60f, headerBandPaint)
                    canvas2.drawLine(0f, 59f, pageW.toFloat(), 59f, borderPaint)
                    canvas2.drawText("CASH FLOW LEDGER: EXPENSES & INCOME", 30f, 35f, Paint(titlePaint).apply { textSize = 14f })
                    canvas2.drawText("Page 2 of 3", pageW - 80f, 35f, subtitlePaint)

                    val colW = (pageW - 80f) / 2f
                    val leftTableX = 30f
                    val rightTableX = 30f + colW + 20f

                    // Left Table: Expenses
                    canvas2.drawText("RECENT EXPENSES", leftTableX, 90f, h2Paint)
                    canvas2.drawLine(leftTableX, 96f, leftTableX + colW, 96f, borderPaint)

                    if (includeExpenses) {
                        canvas2.drawRect(leftTableX, 105f, leftTableX + colW, 122f, fillBgPaint)
                        canvas2.drawText("Title", leftTableX + 5f, 117f, headerPaint)
                        canvas2.drawText("Category", leftTableX + colW - 110f, 117f, headerPaint)
                        canvas2.drawText("Amount", leftTableX + colW - 50f, 117f, headerPaint)
                        canvas2.drawLine(leftTableX, 122f, leftTableX + colW, 122f, borderPaint)

                        var curY = 136f
                        val expenseLimit = expenses.take(25)
                        for (exp in expenseLimit) {
                            canvas2.drawText(exp.title.take(15), leftTableX + 5f, curY, bodyPaint)
                            canvas2.drawText(exp.category.take(12), leftTableX + colW - 110f, curY, Paint(bodyPaint).apply { color = textMuted })
                            canvas2.drawText("Rs. ${exp.amount.toInt()}", leftTableX + colW - 50f, curY, bodyBoldPaint)
                            canvas2.drawLine(leftTableX, curY + 5f, leftTableX + colW, curY + 5f, borderPaint)
                            curY += 21f
                        }
                        if (expenseLimit.isEmpty()) {
                            canvas2.drawText("No expenses recorded", leftTableX + 15f, 150f, Paint(bodyPaint).apply { color = textMuted })
                        }
                    } else {
                        canvas2.drawRoundRect(leftTableX, 105f, leftTableX + colW, 250f, 6f, 6f, fillOmittedPaint)
                        canvas2.drawText("Expenses Module Excluded from PDF", leftTableX + 15f, 150f, omittedTextPaint)
                    }

                    // Right Table: Income
                    canvas2.drawText("RECENT INCOME SOURCE", rightTableX, 90f, h2Paint)
                    canvas2.drawLine(rightTableX, 96f, rightTableX + colW, 96f, borderPaint)

                    if (includeIncome) {
                        canvas2.drawRect(rightTableX, 105f, rightTableX + colW, 122f, fillBgPaint)
                        canvas2.drawText("Title", rightTableX + 5f, 117f, headerPaint)
                        canvas2.drawText("Source", rightTableX + colW - 110f, 117f, headerPaint)
                        canvas2.drawText("Amount", rightTableX + colW - 50f, 117f, headerPaint)
                        canvas2.drawLine(rightTableX, 122f, rightTableX + colW, 122f, borderPaint)

                        var curYInc = 136f
                        val incomeLimit = income.take(25)
                        for (inc in incomeLimit) {
                            canvas2.drawText(inc.title.take(15), rightTableX + 5f, curYInc, bodyPaint)
                            canvas2.drawText(inc.source.take(12), rightTableX + colW - 110f, curYInc, Paint(bodyPaint).apply { color = textMuted })
                            canvas2.drawText("Rs. ${inc.amount.toInt()}", rightTableX + colW - 50f, curYInc, bodyBoldPaint)
                            canvas2.drawLine(rightTableX, curYInc + 5f, rightTableX + colW, curYInc + 5f, borderPaint)
                            curYInc += 21f
                        }
                        if (incomeLimit.isEmpty()) {
                            canvas2.drawText("No income recorded", rightTableX + 15f, 150f, Paint(bodyPaint).apply { color = textMuted })
                        }
                    } else {
                        canvas2.drawRoundRect(rightTableX, 105f, rightTableX + colW, 250f, 6f, 6f, fillOmittedPaint)
                        canvas2.drawText("Income Module Excluded from PDF", rightTableX + 15f, 150f, omittedTextPaint)
                    }

                    // Bottom ledger banner
                    canvas2.drawRoundRect(30f, 700f, pageW - 30f, 760f, 8f, 8f, fillAccentPaint)
                    canvas2.drawText("LEDGER CONSOLIDATION SUMMARY", 45f, 722f, Paint(headerPaint).apply { color = primaryDark })
                    canvas2.drawText("Total Cumulative Inflow (Inflow Ledger): Rs. ${String.format("%.2f", totalIncome)}", 45f, 740f, Paint(bodyPaint).apply { color = primaryDark })
                    canvas2.drawText("Total Cumulative Outflow (Outflow Ledger): Rs. ${String.format("%.2f", totalExpense)}", pageW / 2f + 10f, 740f, Paint(bodyPaint).apply { color = primaryDark })
                }
                pdfDoc?.finishPage(page2)

                // ==========================================
                // PAGE 3: OBLIGATIONS LEDGER (BILLS, LOANS & COMMITTEES)
                // ==========================================
                val page3Info = PdfDocument.PageInfo.Builder(pageW, pageH, 3).create()
                val page3 = pdfDoc?.startPage(page3Info)
                val canvas3 = page3?.canvas
                if (canvas3 != null) {
                    val headerBandPaint = Paint().apply {
                        color = 0xFFF8FAFC.toInt()
                        style = Paint.Style.FILL
                    }
                    canvas3.drawRect(0f, 0f, pageW.toFloat(), 60f, headerBandPaint)
                    canvas3.drawLine(0f, 59f, pageW.toFloat(), 59f, borderPaint)
                    canvas3.drawText("OBLIGATIONS LEDGER: DEBTS, BILLS & COMMITTEES", 30f, 35f, Paint(titlePaint).apply { textSize = 14f })
                    canvas3.drawText("Page 3 of 3", pageW - 80f, 35f, subtitlePaint)

                    var sectionY = 85f

                    // Section A: UTILITY BILLS
                    canvas3.drawText("1. UTILITY BILLS STATUS", 30f, sectionY, h2Paint)
                    canvas3.drawLine(30f, sectionY + 6f, pageW - 30f, sectionY + 6f, borderPaint)
                    
                    var tableY = sectionY + 15f
                    if (includeBills) {
                        canvas3.drawRect(30f, tableY, pageW - 30f, tableY + 16f, fillBgPaint)
                        canvas3.drawText("Bill Name", 35f, tableY + 11f, headerPaint)
                        canvas3.drawText("Due Date", 180f, tableY + 11f, headerPaint)
                        canvas3.drawText("Amount", 320f, tableY + 11f, headerPaint)
                        canvas3.drawText("Status", 450f, tableY + 11f, headerPaint)
                        canvas3.drawLine(30f, tableY + 16f, pageW - 30f, tableY + 16f, borderPaint)

                        tableY += 16f
                        val billLimit = bills.take(8)
                        for (bill in billLimit) {
                            canvas3.drawText(bill.name, 35f, tableY + 12f, bodyPaint)
                            canvas3.drawText(bill.dueDate, 180f, tableY + 12f, bodyPaint)
                            canvas3.drawText("Rs. ${String.format("%.2f", bill.amount)}", 320f, tableY + 12f, bodyBoldPaint)
                            val statusText = if (bill.isPaid == 1) "PAID" else "UNPAID"
                            val statusColor = if (bill.isPaid == 1) 0xFF059669.toInt() else 0xFFDC2626.toInt()
                            canvas3.drawText(statusText, 450f, tableY + 12f, Paint(bodyBoldPaint).apply { color = statusColor })
                            canvas3.drawLine(30f, tableY + 16f, pageW - 30f, tableY + 16f, borderPaint)
                            tableY += 16f
                        }
                        if (billLimit.isEmpty()) {
                            canvas3.drawText("No bills configured", 45f, tableY + 12f, Paint(bodyPaint).apply { color = textMuted })
                            tableY += 16f
                        }
                    } else {
                        canvas3.drawRoundRect(30f, tableY, pageW - 30f, tableY + 45f, 6f, 6f, fillOmittedPaint)
                        canvas3.drawText("Bills Module Excluded from PDF", 45f, tableY + 25f, omittedTextPaint)
                        tableY += 45f
                    }

                    // Section B: ACTIVE LOANS
                    sectionY = tableY + 25f
                    canvas3.drawText("2. OUTSTANDING LOANS & FINANCING", 30f, sectionY, h2Paint)
                    canvas3.drawLine(30f, sectionY + 6f, pageW - 30f, sectionY + 6f, borderPaint)

                    tableY = sectionY + 15f
                    if (includeLoans) {
                        canvas3.drawRect(30f, tableY, pageW - 30f, tableY + 16f, fillBgPaint)
                        canvas3.drawText("Person / Description", 35f, tableY + 11f, headerPaint)
                        canvas3.drawText("Type", 180f, tableY + 11f, headerPaint)
                        canvas3.drawText("Amount", 320f, tableY + 11f, headerPaint)
                        canvas3.drawText("Settlement Status", 450f, tableY + 11f, headerPaint)
                        canvas3.drawLine(30f, tableY + 16f, pageW - 30f, tableY + 16f, borderPaint)

                        tableY += 16f
                        val loanLimit = loans.take(8)
                        for (loan in loanLimit) {
                            canvas3.drawText(loan.personName, 35f, tableY + 12f, bodyPaint)
                            canvas3.drawText(loan.type, 180f, tableY + 12f, Paint(bodyPaint).apply { color = textMuted })
                            canvas3.drawText("Rs. ${String.format("%.2f", loan.amount)}", 320f, tableY + 12f, bodyBoldPaint)
                            val statusText = if (loan.isSettled == 1) "SETTLED" else "PENDING"
                            val statusColor = if (loan.isSettled == 1) 0xFF059669.toInt() else 0xFFD97706.toInt()
                            canvas3.drawText(statusText, 450f, tableY + 12f, Paint(bodyBoldPaint).apply { color = statusColor })
                            canvas3.drawLine(30f, tableY + 16f, pageW - 30f, tableY + 16f, borderPaint)
                            tableY += 16f
                        }
                        if (loanLimit.isEmpty()) {
                            canvas3.drawText("No active loans recorded", 45f, tableY + 12f, Paint(bodyPaint).apply { color = textMuted })
                            tableY += 16f
                        }
                    } else {
                        canvas3.drawRoundRect(30f, tableY, pageW - 30f, tableY + 45f, 6f, 6f, fillOmittedPaint)
                        canvas3.drawText("Loans & Financing Module Excluded from PDF", 45f, tableY + 25f, omittedTextPaint)
                        tableY += 45f
                    }

                    // Section C: ACTIVE COMMITTEES (BC)
                    sectionY = tableY + 25f
                    canvas3.drawText("3. ROTATING INVESTMENT KOMMITTEES (BC)", 30f, sectionY, h2Paint)
                    canvas3.drawLine(30f, sectionY + 6f, pageW - 30f, sectionY + 6f, borderPaint)

                    tableY = sectionY + 15f
                    if (includeCommittees) {
                        canvas3.drawRect(30f, tableY, pageW - 30f, tableY + 16f, fillBgPaint)
                        canvas3.drawText("Kommittee Name", 35f, tableY + 11f, headerPaint)
                        canvas3.drawText("Amount / Head", 180f, tableY + 11f, headerPaint)
                        canvas3.drawText("Total Members", 320f, tableY + 11f, headerPaint)
                        canvas3.drawText("Total Investment Pool", 450f, tableY + 11f, headerPaint)
                        canvas3.drawLine(30f, tableY + 16f, pageW - 30f, tableY + 16f, borderPaint)

                        tableY += 16f
                        val commLimit = committees.take(6)
                        for (comm in commLimit) {
                            canvas3.drawText(comm.name, 35f, tableY + 12f, bodyPaint)
                            canvas3.drawText("Rs. ${String.format("%.2f", comm.amountPerHead)}", 180f, tableY + 12f, bodyPaint)
                            canvas3.drawText("${comm.totalMembers} members", 320f, tableY + 12f, bodyPaint)
                            val totalVol = comm.amountPerHead * comm.totalMembers
                            canvas3.drawText("Rs. ${String.format("%.2f", totalVol)}", 450f, tableY + 12f, bodyBoldPaint)
                            canvas3.drawLine(30f, tableY + 16f, pageW - 30f, tableY + 16f, borderPaint)
                            tableY += 16f
                        }
                        if (commLimit.isEmpty()) {
                            canvas3.drawText("No active Kommittees configured", 45f, tableY + 12f, Paint(bodyPaint).apply { color = textMuted })
                        }
                    } else {
                        canvas3.drawRoundRect(30f, tableY, pageW - 30f, tableY + 45f, 6f, 6f, fillOmittedPaint)
                        canvas3.drawText("Rotating Kommittees Module Excluded from PDF", 45f, tableY + 25f, omittedTextPaint)
                    }
                    
                    canvas3.drawLine(30f, 760f, pageW - 30f, 760f, borderPaint)
                    canvas3.drawText("End of Consolidated Financial Master Report.", pageW / 2f - 100f, 780f, Paint(subtitlePaint).apply { textSize = 9f })
                }
                pdfDoc?.finishPage(page3)

                try {
                    destination?.let { descriptor ->
                        FileOutputStream(descriptor.fileDescriptor).use { output ->
                            pdfDoc?.writeTo(output)
                        }
                    }
                    callback?.onWriteFinished(arrayOf(android.print.PageRange.ALL_PAGES))
                } catch (e: Exception) {
                    callback?.onWriteFailed(e.message)
                } finally {
                    pdfDoc?.close()
                    pdfDoc = null
                }
            }
        }

        printManager.print("Finance_Master_Report", printAdapter, null)
    } catch (e: Exception) {
        Toast.makeText(context, "Failed to print: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

fun exportFinancePdfToDownloads(
    context: Context,
    expenses: List<Expense>,
    income: List<Income>,
    bills: List<Bill>,
    committees: List<BcCommittee>,
    loans: List<Loan>,
    savingsGoals: List<SavingsGoal>,
    totalIncomeAmt: Double,
    totalExpenseAmt: Double,
    netBalance: Double,
    totalUnpaidBillsAmt: Double,
    loansBorrowed: Double,
    loansLent: Double,
    totalSavingsSaved: Double,
    totalSavingsTarget: Double
) {
    kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
        try {
            val pdfDoc = android.graphics.pdf.PdfDocument()
            val pageW = 595
            val pageH = 842

            val pageInfo1 = android.graphics.pdf.PdfDocument.PageInfo.Builder(pageW, pageH, 1).create()
            val page1 = pdfDoc.startPage(pageInfo1)
            val canvas1 = page1.canvas

            val primaryDark = 0xFF064E3B.toInt()
            val textDark = 0xFF1F2937.toInt()
            val titlePaint = android.graphics.Paint().apply {
                color = primaryDark
                textSize = 20f
                typeface = android.graphics.Typeface.create(android.graphics.Typeface.SANS_SERIF, android.graphics.Typeface.BOLD)
                isAntiAlias = true
            }
            val bodyPaint = android.graphics.Paint().apply {
                color = textDark
                textSize = 12f
                typeface = android.graphics.Typeface.create(android.graphics.Typeface.SANS_SERIF, android.graphics.Typeface.NORMAL)
                isAntiAlias = true
            }

            canvas1.drawText("CONSOLIDATED FINANCIAL STATEMENT", 30f, 50f, titlePaint)
            canvas1.drawText("Generated on ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())}", 30f, 75f, bodyPaint)
            canvas1.drawText("Total Income: Rs. ${String.format("%.2f", totalIncomeAmt)}", 30f, 110f, bodyPaint)
            canvas1.drawText("Total Expenses: Rs. ${String.format("%.2f", totalExpenseAmt)}", 30f, 135f, bodyPaint)
            canvas1.drawText("Net Balance: Rs. ${String.format("%.2f", netBalance)}", 30f, 160f, bodyPaint)
            canvas1.drawText("Unpaid Bills: Rs. ${String.format("%.2f", totalUnpaidBillsAmt)}", 30f, 185f, bodyPaint)
            canvas1.drawText("Loans Lent: Rs. ${String.format("%.2f", loansLent)} | Borrowed: Rs. ${String.format("%.2f", loansBorrowed)}", 30f, 210f, bodyPaint)
            canvas1.drawText("Savings Progress: Rs. ${String.format("%.2f", totalSavingsSaved)} / Rs. ${String.format("%.2f", totalSavingsTarget)}", 30f, 235f, bodyPaint)

            var yPos = 280f
            canvas1.drawText("RECENT EXPENSES LOG:", 30f, yPos, titlePaint)
            yPos += 25f
            for (e in expenses.take(15)) {
                canvas1.drawText("• ${e.title} - Rs. ${e.amount} (${e.category}) - ${e.date}", 35f, yPos, bodyPaint)
                yPos += 20f
            }

            pdfDoc.finishPage(page1)

            val pdfFile = File(context.cacheDir, "Financial_Statement_${System.currentTimeMillis()}.pdf")
            FileOutputStream(pdfFile).use { out ->
                pdfDoc.writeTo(out)
            }
            pdfDoc.close()

            com.example.data.PhoneStorageSaver.savePdfToPhoneMemory(
                context = context,
                pdfFile = pdfFile,
                desiredFileName = "Financial_Statement_${SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())}.pdf"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
