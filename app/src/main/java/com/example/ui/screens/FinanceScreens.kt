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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.viewmodel.Screen
import com.example.viewmodel.StudentKitViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceHubScreen(
    viewModel: StudentKitViewModel,
    subScreen: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finance Kit") },
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
                text = "My Committees (BC Apportions)",
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
                        Text("No active committees. Create one to manage peer pots!", color = Color.Gray)
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
            title = { Text("Create Committee (BC)") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Committee Name") },
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
fun BcCommitteeDetailsScreen(viewModel: StudentKitViewModel, committeeId: String) {
    val committees by viewModel.committees.collectAsState()
    val committee = committees.find { it.id == committeeId }

    if (committee == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Committee details loading or removed.")
        }
        return
    }

    // Collect flows
    val members by viewModel.getMembersByCommittee(committeeId).collectAsState(initial = emptyList())
    val payments by viewModel.getPaymentsByCommittee(committeeId).collectAsState(initial = emptyList())

    var selectedTab by remember { mutableStateOf(0) } // 0=Members Schedule, 1=Payments Grid
    val context = LocalContext.current

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
            Column(modifier = Modifier.padding(16.dp)) {
                Text(committee.name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Total Pot Pool per Round: Rs. ${committee.amountPerHead * committee.totalMembers}", fontWeight = FontWeight.SemiBold)
                Text("Apportion contribute: Rs. ${committee.amountPerHead} per round", fontSize = 12.sp, color = Color.DarkGray)
            }
        }

        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text("Payout Schedule", modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text("Payments Sheet", modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
            }
        }

        if (selectedTab == 0) {
            // Member assigned indices which shows payout cycles
            Text("Payout cycles by month position:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(members) { mem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (mem.hasReceived == 1) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surface)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(mem.name, fontWeight = FontWeight.Bold)
                            Text("Payout Round: Position #${mem.payoutPosition}", fontSize = 11.sp, color = Color.Gray)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (mem.hasReceived == 1) "Pot Paid Out ✓" else "Pending Round",
                                fontSize = 11.sp,
                                color = if (mem.hasReceived == 1) Color(0xFF2E7D32) else Color.DarkGray,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Checkbox(
                                checked = mem.hasReceived == 1,
                                onCheckedChange = { viewModel.setMemberReceivedPayout(mem.id, it) }
                            )
                        }
                    }
                }
            }
        } else {
            // Collection spreadsheet lists
            Text("Receive and collect monthly head contributions:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(payments) { pt ->
                    val memberName = members.find { it.id == pt.memberId }?.name ?: "Member"
                    val isPaid = pt.isPaid == 1

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isPaid) Color(0xFFE3F2FD) else Color(0xFFFFF3E0))
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(memberName, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text("Cycle: ${pt.month}", fontSize = 11.sp, color = Color.Gray)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isPaid) "Received" else "Unpaid",
                                fontSize = 11.sp,
                                color = if (isPaid) MaterialTheme.colorScheme.primary else Color(0xFFE65100),
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

        // Action button to simulate scheduling output report
        Button(
            onClick = {
                // PDF Print simulate
                android.widget.Toast.makeText(context, "BC Schedule report exported as PDF!", android.widget.Toast.LENGTH_SHORT).show()
                viewModel.navigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.PictureAsPdf, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Download PDF Schedule Report")
        }
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
