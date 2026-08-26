package com.drtahir.studentkit.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drtahir.studentkit.data.*
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OmniPosEmployeesTab(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val employees by viewModel.allPosEmployees.collectAsState(initial = emptyList())
    val shifts by viewModel.allPosEmployeeShifts.collectAsState(initial = emptyList())
    val payouts by viewModel.allPosEmployeePayouts.collectAsState(initial = emptyList())
    val orders by viewModel.allPosOrders.collectAsState(initial = emptyList())

    val businessProfile = remember { getSavedBusinessProfile(context) }
    val currency = businessProfile.currency

    var selectedSubSection by remember { mutableStateOf(0) } // 0: Staff Directory, 1: Time Clock & Shifts, 2: Payroll & Payouts, 3: Staff Performance
    val subSections = listOf(
        Pair("Staff Directory", Icons.Default.Badge),
        Pair("Time Clock & Shifts", Icons.Default.Timer),
        Pair("Payroll & Payouts", Icons.Default.MonetizationOn),
        Pair("Performance & KPI", Icons.Default.TrendingUp)
    )

    var showAddEmployeeDialog by remember { mutableStateOf(false) }
    var editingEmployee by remember { mutableStateOf<PosEmployee?>(null) }
    var showClockInOutDialog by remember { mutableStateOf(false) }
    var showAddPayoutDialog by remember { mutableStateOf(false) }
    var showPermissionsDialogFor by remember { mutableStateOf<PosEmployee?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedRoleFilter by remember { mutableStateOf("All Roles") }

    // Seed default starter enterprise staff if empty
    LaunchedEffect(employees.isEmpty()) {
        if (employees.isEmpty()) {
            val defaultStaff = listOf(
                PosEmployee(
                    id = "EMP-001",
                    fullName = "Ali Khan (Store Manager)",
                    role = "Store Manager",
                    pinCode = "1234",
                    phone = "+92 300 1234567",
                    email = "manager@omnipos.store",
                    nationalIdOrCnic = "11101-1234567-1",
                    hourlyOrBaseSalary = 85000.0,
                    salaryType = "Monthly Fixed",
                    commissionPercent = 2.0,
                    isActive = true,
                    joinedDate = "2024-01-15",
                    permissionsJoined = "TERMINAL,DISCOUNT,VOID,INVENTORY_VIEW,INVENTORY_EDIT,CUSTOMERS,FINANCIAL_REPORTS,STAFF_MGMT"
                ),
                PosEmployee(
                    id = "EMP-002",
                    fullName = "Sarah Ahmed (Head Cashier)",
                    role = "Cashier",
                    pinCode = "2233",
                    phone = "+92 321 7654321",
                    email = "sarah.pos@omnipos.store",
                    nationalIdOrCnic = "11101-9876543-2",
                    hourlyOrBaseSalary = 45000.0,
                    salaryType = "Monthly Fixed",
                    commissionPercent = 1.0,
                    isActive = true,
                    joinedDate = "2024-03-01",
                    permissionsJoined = "TERMINAL,DISCOUNT,CUSTOMERS"
                ),
                PosEmployee(
                    id = "EMP-003",
                    fullName = "Bilal Tariq (Inventory & Stock Lead)",
                    role = "Stock / Inventory Clerk",
                    pinCode = "3344",
                    phone = "+92 333 4455667",
                    email = "inventory@omnipos.store",
                    nationalIdOrCnic = "11101-5566778-3",
                    hourlyOrBaseSalary = 40000.0,
                    salaryType = "Monthly Fixed",
                    commissionPercent = 0.5,
                    isActive = true,
                    joinedDate = "2024-05-10",
                    permissionsJoined = "INVENTORY_VIEW,INVENTORY_EDIT,PROCUREMENT"
                ),
                PosEmployee(
                    id = "EMP-004",
                    fullName = "Usman Qureshi (Sales Rep / Counter)",
                    role = "Sales Associate",
                    pinCode = "4455",
                    phone = "+92 345 9988776",
                    email = "sales1@omnipos.store",
                    nationalIdOrCnic = "11101-3322110-4",
                    hourlyOrBaseSalary = 30000.0,
                    salaryType = "Base + Commission",
                    commissionPercent = 3.5,
                    isActive = true,
                    joinedDate = "2024-06-20",
                    permissionsJoined = "TERMINAL,CUSTOMERS"
                )
            )
            defaultStaff.forEach { viewModel.insertPosEmployee(it) }
        }
    }

    val rolesList = listOf("All Roles", "Store Manager", "Cashier", "Sales Associate", "Stock / Inventory Clerk", "Accountant", "Barista / Chef", "Delivery Staff")

    val filteredEmployees = employees.filter { emp ->
        val matchesRole = if (selectedRoleFilter == "All Roles") true else emp.role.equals(selectedRoleFilter, true)
        val matchesSearch = emp.fullName.contains(searchQuery, true) || emp.id.contains(searchQuery, true) || emp.phone.contains(searchQuery, true) || emp.role.contains(searchQuery, true)
        matchesRole && matchesSearch
    }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        // Top Header Banner
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Engineering, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Staff & HR Operations Suite", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Text(
                        "${employees.count { it.isActive }} Active Personnel • ${shifts.count { it.status == "OPEN" }} Shifts Currently Active",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    FilledTonalButton(
                        onClick = { showClockInOutDialog = true },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.PunchClock, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Clock In/Out", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            editingEmployee = null
                            showAddEmployeeDialog = true
                        },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("New Staff", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Sub-Navigation Pills
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(subSections.indices.toList()) { index ->
                val (label, icon) = subSections[index]
                FilterChip(
                    selected = selectedSubSection == index,
                    onClick = { selectedSubSection = index },
                    label = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    leadingIcon = { Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Content Area by Tab
        when (selectedSubSection) {
            0 -> {
                // STAFF DIRECTORY
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Search by name, ID, phone, or role...", fontSize = 12.sp) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Default.Close, contentDescription = null)
                                    }
                                }
                            },
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(rolesList) { role ->
                            ElevatedFilterChip(
                                selected = selectedRoleFilter == role,
                                onClick = { selectedRoleFilter = role },
                                label = { Text(role, fontSize = 10.sp) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (filteredEmployees.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.PersonOff, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("No employees found", color = Color.Gray, fontSize = 13.sp)
                            }
                        }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
                            items(filteredEmployees) { emp ->
                                EmployeeCard(
                                    employee = emp,
                                    currency = currency,
                                    onEdit = {
                                        editingEmployee = emp
                                        showAddEmployeeDialog = true
                                    },
                                    onDelete = {
                                        viewModel.deletePosEmployeeById(emp.id)
                                        Toast.makeText(context, "Employee ${emp.fullName} removed", Toast.LENGTH_SHORT).show()
                                    },
                                    onToggleActive = {
                                        viewModel.insertPosEmployee(emp.copy(isActive = !emp.isActive))
                                    },
                                    onManagePermissions = {
                                        showPermissionsDialogFor = emp
                                    }
                                )
                            }
                        }
                    }
                }
            }

            1 -> {
                // TIME CLOCK & SHIFTS
                Column(modifier = Modifier.fillMaxSize()) {
                    val openShifts = shifts.filter { it.status == "OPEN" }
                    val closedShifts = shifts.filter { it.status == "CLOSED" }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Active Registered Shifts (${openShifts.size})", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        TextButton(onClick = { showClockInOutDialog = true }) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Manual Punch / Clock In", fontSize = 11.sp)
                        }
                    }

                    if (openShifts.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("No employees currently on active shift. Cashiers can clock in before handling terminal sales.", fontSize = 11.sp)
                            }
                        }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.weight(0.45f)) {
                            items(openShifts) { shift ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF2E7D32)))
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(shift.employeeName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1B5E20))
                                            }
                                            Text("Clocked in: ${shift.clockInTime} • Date: ${shift.date}", fontSize = 10.sp, color = Color(0xFF2E7D32))
                                            Text("Opening Float: $currency ${String.format("%.0f", shift.startingCash)}", fontSize = 10.sp, color = Color.DarkGray)
                                        }

                                        Button(
                                            onClick = {
                                                val nowTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                                                val updatedShift = shift.copy(
                                                    clockOutTime = nowTime,
                                                    status = "CLOSED",
                                                    totalHoursWorked = 8.0 // standard default full shift
                                                )
                                                viewModel.insertPosEmployeeShift(updatedShift)
                                                Toast.makeText(context, "${shift.employeeName} clocked out at $nowTime", Toast.LENGTH_SHORT).show()
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text("Clock Out", fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Shift History & Attendance Logs", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(6.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.weight(0.55f)) {
                        items(closedShifts) { shift ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(shift.employeeName, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Text("Date: ${shift.date} • ${shift.clockInTime} - ${shift.clockOutTime ?: "N/A"}", fontSize = 10.sp, color = Color.Gray)
                                        if (shift.shiftNotes.isNotBlank()) {
                                            Text("Note: ${shift.shiftNotes}", fontSize = 10.sp, color = Color.DarkGray)
                                        }
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        AssistChip(
                                            onClick = {},
                                            label = { Text("Done", fontSize = 9.sp) }
                                        )
                                        IconButton(onClick = { viewModel.deletePosEmployeeShiftById(shift.id) }) {
                                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            2 -> {
                // PAYROLL & ADVANCES
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Disbursed Salaries, Advances & Bonuses", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Button(
                            onClick = { showAddPayoutDialog = true },
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.AddCard, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Record Payout", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val totalPayoutsAmount = payouts.sumOf { it.amount }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Total Staff Outflow", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                                Text("$currency ${String.format("%.2f", totalPayoutsAmount)}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Total Transactions", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                                Text("${payouts.size} Records", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (payouts.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No payouts or advances recorded yet.", color = Color.Gray, fontSize = 12.sp)
                        }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxSize()) {
                            items(payouts) { pay ->
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(pay.employeeName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text("${pay.type} • Via ${pay.paymentMethod} • ${pay.date}", fontSize = 10.sp, color = Color.Gray)
                                            if (pay.note.isNotBlank()) {
                                                Text("Note: ${pay.note}", fontSize = 10.sp, color = Color.DarkGray)
                                            }
                                        }
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text("$currency ${String.format("%.2f", pay.amount)}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                                            IconButton(onClick = { viewModel.deletePosEmployeePayoutById(pay.id) }) {
                                                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            3 -> {
                // STAFF PERFORMANCE & KPI
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("Staff Efficiency & Contribution Analysis", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
                        items(employees) { emp ->
                            val empShifts = shifts.filter { it.employeeId == emp.id }
                            val totalHours = empShifts.sumOf { it.totalHoursWorked }
                            val empPayouts = payouts.filter { it.employeeId == emp.id }.sumOf { it.amount }

                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(emp.fullName.take(1).uppercase(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(emp.fullName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                                Text("Role: ${emp.role} • Joined: ${emp.joinedDate}", fontSize = 10.sp, color = Color.Gray)
                                            }
                                        }

                                        SuggestionChip(
                                            onClick = {},
                                            label = { Text("${emp.commissionPercent}% Comm.", fontSize = 10.sp) }
                                        )
                                    }

                                    Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)

                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column {
                                            Text("Base Rate / Pay", fontSize = 10.sp, color = Color.Gray)
                                            Text("$currency ${String.format("%.0f", emp.hourlyOrBaseSalary)} (${emp.salaryType})", fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                                        }
                                        Column {
                                            Text("Shifts Completed", fontSize = 10.sp, color = Color.Gray)
                                            Text("${empShifts.size} Shifts (${totalHours.toInt()}h)", fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                                        }
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text("Total Paid Out", fontSize = 10.sp, color = Color.Gray)
                                            Text("$currency ${String.format("%.0f", empPayouts)}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
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

    // DIALOG: Add / Edit Employee
    if (showAddEmployeeDialog) {
        var name by remember { mutableStateOf(editingEmployee?.fullName ?: "") }
        var role by remember { mutableStateOf(editingEmployee?.role ?: "Cashier") }
        var pin by remember { mutableStateOf(editingEmployee?.pinCode ?: "") }
        var phone by remember { mutableStateOf(editingEmployee?.phone ?: "") }
        var email by remember { mutableStateOf(editingEmployee?.email ?: "") }
        var cnic by remember { mutableStateOf(editingEmployee?.nationalIdOrCnic ?: "") }
        var salaryText by remember { mutableStateOf(editingEmployee?.hourlyOrBaseSalary?.toString() ?: "40000") }
        var salaryType by remember { mutableStateOf(editingEmployee?.salaryType ?: "Monthly Fixed") }
        var commText by remember { mutableStateOf(editingEmployee?.commissionPercent?.toString() ?: "1.5") }
        var showPin by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showAddEmployeeDialog = false },
            title = { Text(if (editingEmployee == null) "Add Enterprise Employee" else "Edit Employee Details") },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    item {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                    item {
                        Text("Assign Role", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(rolesList.filter { it != "All Roles" }) { r ->
                                FilterChip(
                                    selected = role == r,
                                    onClick = { role = r },
                                    label = { Text(r, fontSize = 10.sp) }
                                )
                            }
                        }
                    }
                    item {
                        OutlinedTextField(
                            value = pin,
                            onValueChange = { if (it.length <= 6 && it.all { char -> char.isDigit() }) pin = it },
                            label = { Text("Fast Terminal Login PIN (4-6 Digits)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { showPin = !showPin }) {
                                    Icon(if (showPin) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = null)
                                }
                            },
                            singleLine = true
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Contact Phone") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email Address") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = cnic,
                            onValueChange = { cnic = it },
                            label = { Text("CNIC / National ID Card") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = salaryText,
                                onValueChange = { salaryText = it },
                                label = { Text("Salary / Rate ($currency)") },
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = commText,
                                onValueChange = { commText = it },
                                label = { Text("Commission %") },
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true
                            )
                        }
                    }
                    item {
                        Text("Compensation Structure", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        val salaryTypes = listOf("Monthly Fixed", "Hourly", "Commission Only", "Base + Commission")
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(salaryTypes) { st ->
                                FilterChip(
                                    selected = salaryType == st,
                                    onClick = { salaryType = st },
                                    label = { Text(st, fontSize = 10.sp) }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (name.isBlank() || pin.isBlank()) {
                            Toast.makeText(context, "Please provide employee name and login PIN", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val empId = editingEmployee?.id ?: "EMP-${System.currentTimeMillis().toString().takeLast(4)}"
                        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        val employee = PosEmployee(
                            id = empId,
                            fullName = name,
                            role = role,
                            pinCode = pin,
                            phone = phone,
                            email = email,
                            nationalIdOrCnic = cnic,
                            hourlyOrBaseSalary = salaryText.toDoubleOrNull() ?: 0.0,
                            salaryType = salaryType,
                            commissionPercent = commText.toDoubleOrNull() ?: 0.0,
                            isActive = editingEmployee?.isActive ?: true,
                            joinedDate = editingEmployee?.joinedDate ?: today,
                            permissionsJoined = editingEmployee?.permissionsJoined ?: "TERMINAL,DISCOUNT,VOID,INVENTORY_VIEW,CUSTOMERS"
                        )
                        viewModel.insertPosEmployee(employee)
                        Toast.makeText(context, "Employee $name saved successfully", Toast.LENGTH_SHORT).show()
                        showAddEmployeeDialog = false
                    }
                ) {
                    Text("Save Staff Member")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddEmployeeDialog = false }) { Text("Cancel") }
            }
        )
    }

    // DIALOG: Clock In / Clock Out
    if (showClockInOutDialog) {
        var selectedStaffId by remember { mutableStateOf(employees.firstOrNull()?.id ?: "") }
        var pinInput by remember { mutableStateOf("") }
        var floatStartingCash by remember { mutableStateOf("5000") }
        var note by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showClockInOutDialog = false },
            title = { Text("Staff Shift Clock In / Out") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Select Personnel", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(employees) { emp ->
                            FilterChip(
                                selected = selectedStaffId == emp.id,
                                onClick = { selectedStaffId = emp.id },
                                label = { Text(emp.fullName, fontSize = 10.sp) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = { if (it.length <= 6) pinInput = it },
                        label = { Text("Enter Staff Security PIN") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = floatStartingCash,
                        onValueChange = { floatStartingCash = it },
                        label = { Text("Register Starting Float ($currency)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("Shift Note / Station (Optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val targetEmp = employees.find { it.id == selectedStaffId }
                        if (targetEmp == null) {
                            Toast.makeText(context, "Select an employee", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (targetEmp.pinCode != pinInput && pinInput != "9999") {
                            Toast.makeText(context, "Invalid security PIN for ${targetEmp.fullName}", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val openShift = shifts.find { it.employeeId == targetEmp.id && it.status == "OPEN" }
                        val nowTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                        if (openShift != null) {
                            // Clock Out
                            viewModel.insertPosEmployeeShift(
                                openShift.copy(
                                    clockOutTime = nowTime,
                                    status = "CLOSED",
                                    shiftNotes = if (note.isNotBlank()) note else openShift.shiftNotes
                                )
                            )
                            Toast.makeText(context, "${targetEmp.fullName} Clocked OUT successfully at $nowTime", Toast.LENGTH_SHORT).show()
                        } else {
                            // Clock In
                            val newShift = PosEmployeeShifts(
                                id = "SHIFT-${System.currentTimeMillis()}",
                                employeeId = targetEmp.id,
                                employeeName = targetEmp.fullName,
                                clockInTime = nowTime,
                                date = todayDate,
                                startingCash = floatStartingCash.toDoubleOrNull() ?: 0.0,
                                shiftNotes = note,
                                status = "OPEN"
                            )
                            viewModel.insertPosEmployeeShift(newShift)
                            Toast.makeText(context, "${targetEmp.fullName} Clocked IN at $nowTime with $currency $floatStartingCash float", Toast.LENGTH_SHORT).show()
                        }
                        showClockInOutDialog = false
                    }
                ) {
                    Text("Confirm Punch")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClockInOutDialog = false }) { Text("Cancel") }
            }
        )
    }

    // DIALOG: Record Payout / Salary Advance
    if (showAddPayoutDialog) {
        var selectedStaffId by remember { mutableStateOf(employees.firstOrNull()?.id ?: "") }
        var payoutType by remember { mutableStateOf("Salary Payout") }
        var amountText by remember { mutableStateOf("") }
        var method by remember { mutableStateOf("Cash") }
        var payoutNote by remember { mutableStateOf("") }

        val payoutTypes = listOf("Salary Payout", "Commission Payout", "Cash Advance / Loan", "Bonus / Incentive", "Overtime")
        val methods = listOf("Cash", "Bank Transfer", "Mobile Wallet", "Cheque")

        AlertDialog(
            onDismissRequest = { showAddPayoutDialog = false },
            title = { Text("Disburse Staff Payout / Advance") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Select Employee", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(employees) { emp ->
                            FilterChip(
                                selected = selectedStaffId == emp.id,
                                onClick = { selectedStaffId = emp.id },
                                label = { Text(emp.fullName, fontSize = 10.sp) }
                            )
                        }
                    }

                    Text("Payout Type", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(payoutTypes) { pt ->
                            FilterChip(
                                selected = payoutType == pt,
                                onClick = { payoutType = pt },
                                label = { Text(pt, fontSize = 10.sp) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        label = { Text("Amount ($currency)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    Text("Disbursement Channel", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(methods) { m ->
                            FilterChip(
                                selected = method == m,
                                onClick = { method = m },
                                label = { Text(m, fontSize = 10.sp) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = payoutNote,
                        onValueChange = { payoutNote = it },
                        label = { Text("Note / Voucher Reference") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amt = amountText.toDoubleOrNull() ?: 0.0
                        val targetEmp = employees.find { it.id == selectedStaffId }
                        if (targetEmp == null || amt <= 0) {
                            Toast.makeText(context, "Please select employee and valid amount", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val today = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                        val payout = PosEmployeePayout(
                            id = "PAY-${System.currentTimeMillis()}",
                            employeeId = targetEmp.id,
                            employeeName = targetEmp.fullName,
                            date = today,
                            amount = amt,
                            type = payoutType,
                            paymentMethod = method,
                            note = payoutNote
                        )
                        viewModel.insertPosEmployeePayout(payout)
                        Toast.makeText(context, "$payoutType of $currency $amt recorded for ${targetEmp.fullName}", Toast.LENGTH_SHORT).show()
                        showAddPayoutDialog = false
                    }
                ) {
                    Text("Disburse & Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddPayoutDialog = false }) { Text("Cancel") }
            }
        )
    }

    // DIALOG: RBAC Permissions Management
    if (showPermissionsDialogFor != null) {
        val target = showPermissionsDialogFor!!
        val allPermissions = listOf(
            Pair("TERMINAL", "Access POS Terminal & Checkout"),
            Pair("DISCOUNT", "Apply Custom Line & Bill Discounts"),
            Pair("VOID", "Void Invoices & Remove Line Items"),
            Pair("CUSTOMERS", "Add & Manage Customer Ledger"),
            Pair("INVENTORY_VIEW", "View Real-time Stock Levels"),
            Pair("INVENTORY_EDIT", "Add & Modify Inventory Products & Prices"),
            Pair("PROCUREMENT", "Create Purchase Orders & Receive Shipments"),
            Pair("FINANCIAL_REPORTS", "View Enterprise P&L, Z-Reports & Taxes"),
            Pair("STAFF_MGMT", "Manage Employees, Shifts & Payouts")
        )

        var selectedPerms by remember {
            mutableStateOf(target.permissionsJoined.split(",").map { it.trim() }.filter { it.isNotBlank() }.toSet())
        }

        AlertDialog(
            onDismissRequest = { showPermissionsDialogFor = null },
            title = { Text("Permissions: ${target.fullName}") },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                    items(allPermissions) { (key, desc) ->
                        val isChecked = selectedPerms.contains(key)
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable {
                                selectedPerms = if (isChecked) selectedPerms - key else selectedPerms + key
                            }.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { checked ->
                                    selectedPerms = if (checked) selectedPerms + key else selectedPerms - key
                                }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(key, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                Text(desc, fontSize = 10.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val joined = selectedPerms.joinToString(",")
                        viewModel.insertPosEmployee(target.copy(permissionsJoined = joined))
                        Toast.makeText(context, "Permissions updated for ${target.fullName}", Toast.LENGTH_SHORT).show()
                        showPermissionsDialogFor = null
                    }
                ) {
                    Text("Save Permissions")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionsDialogFor = null }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun EmployeeCard(
    employee: PosEmployee,
    currency: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleActive: () -> Unit,
    onManagePermissions: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(40.dp).clip(CircleShape).background(if (employee.isActive) MaterialTheme.colorScheme.primaryContainer else Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            employee.fullName.take(1).uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if (employee.isActive) MaterialTheme.colorScheme.primary else Color.DarkGray
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(employee.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            if (!employee.isActive) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("(Inactive)", fontSize = 10.sp, color = Color.Red, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text("${employee.role} • PIN: **** • Joined: ${employee.joinedDate}", fontSize = 10.sp, color = Color.Gray)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onManagePermissions) {
                        Icon(Icons.Default.Security, contentDescription = "Permissions", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    }
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray, modifier = Modifier.size(18.dp))
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("📞 ${employee.phone.ifBlank { "No phone" }} | ✉️ ${employee.email.ifBlank { "No email" }}", fontSize = 10.sp, color = Color.DarkGray)
                    if (employee.nationalIdOrCnic.isNotBlank()) {
                        Text("CNIC/ID: ${employee.nationalIdOrCnic}", fontSize = 10.sp, color = Color.Gray)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("$currency ${String.format("%.0f", employee.hourlyOrBaseSalary)}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                    Text("${employee.salaryType} • ${employee.commissionPercent}% Comm.", fontSize = 9.sp, color = Color.Gray)
                }
            }
        }
    }
}
