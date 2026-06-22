package com.studentkit.buner.ui.screens

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studentkit.buner.data.*
import com.studentkit.buner.viewmodel.Screen
import com.studentkit.buner.viewmodel.StudentKitViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: StudentKitViewModel,
    modifier: Modifier = Modifier
) {
    val expenses by viewModel.expenses.collectAsState()
    val income by viewModel.income.collectAsState()
    val bills by viewModel.unpaidBills.collectAsState()
    val savingsGoals by viewModel.savingsGoals.collectAsState()
    val timetable by viewModel.timetableClasses.collectAsState()

    // Welcomes, dates, Islamic dates
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val greeting = when {
        hour < 12 -> "Good Morning, Student!"
        hour < 17 -> "Good Afternoon, Student!"
        else -> "Good Evening, Student!"
    }

    val df = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
    val formattedDate = df.format(Date())

    // Simulated Hijri Date (Pakistani Students standard calibration)
    val hijriDate = "27 Dhul-Hijjah 1447 AH"

    // Card totals calculations
    val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val thisMonthStr = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

    val todayExpense = expenses.filter { it.date.startsWith(todayStr) }.sumOf { it.amount }
    val totalMonthIncome = income.filter { it.date.startsWith(thisMonthStr) }.sumOf { it.amount }
    val totalMonthExpense = expenses.filter { it.date.startsWith(thisMonthStr) }.sumOf { it.amount }
    val netBalance = totalMonthIncome - totalMonthExpense

    val activeGoalsCount = savingsGoals.count { it.currentAmount < it.targetAmount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "StudentKit",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "All-in-One Utility App",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.navigateTo(Screen.Notes) },
                        modifier = Modifier.testTag("notes_shortcut")
                    ) {
                        Icon(Icons.Default.Notes, contentDescription = "View Notes")
                    }
                    IconButton(
                        onClick = { viewModel.navigateTo(Screen.StudyTimer) },
                        modifier = Modifier.testTag("timer_shortcut")
                    ) {
                        Icon(Icons.Default.Timer, contentDescription = "Focus Timer")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header Welcome Greeting
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = greeting,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = formattedDate,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "🕌 $hijriDate",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Quick Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Today Expense
                StatCard(
                    title = "Today's Cost",
                    value = "Rs. ${String.format("%.0f", todayExpense)}",
                    icon = Icons.Default.TrendingDown,
                    iconColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                )

                // Month Balance
                StatCard(
                    title = "Month Savings",
                    value = "Rs. ${String.format("%.0f", netBalance)}",
                    icon = if (netBalance >= 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    iconColor = if (netBalance >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1.2f)
                )

                // Active Goals
                StatCard(
                    title = "Active Goals",
                    value = "$activeGoalsCount",
                    icon = Icons.Default.Flag,
                    iconColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(0.9f)
                )
            }

            // Quick Action Grid (2x4)
            Text(
                text = "Quick Actions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val itemWidth = (maxWidth - 24.dp) / 4
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        QuickActionItem(
                            label = "Add Expense",
                            icon = Icons.Default.TrendingDown,
                            color = MaterialTheme.colorScheme.primary,
                            onClick = { viewModel.navigateTo(Screen.ExpenseTracker) },
                            width = itemWidth,
                            testTag = "action_expense"
                        )
                        QuickActionItem(
                            label = "Add Income",
                            icon = Icons.Default.TrendingUp,
                            color = MaterialTheme.colorScheme.secondary,
                            onClick = { viewModel.navigateTo(Screen.IncomeTracker) },
                            width = itemWidth,
                            testTag = "action_income"
                        )
                        QuickActionItem(
                            label = "Scan QR",
                            icon = Icons.Default.QrCodeScanner,
                            color = MaterialTheme.colorScheme.tertiary,
                            onClick = { viewModel.navigateTo(Screen.QrScanner) },
                            width = itemWidth,
                            testTag = "action_qr_scan"
                        )
                        QuickActionItem(
                            label = "Scan Doc",
                            icon = Icons.Default.DocumentScanner,
                            color = Color(0xFF673AB7),
                            onClick = { viewModel.navigateTo(Screen.DocumentScanner) },
                            width = itemWidth,
                            testTag = "action_doc_scan"
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        QuickActionItem(
                            label = "Calculator",
                            icon = Icons.Default.Calculate,
                            color = Color(0xFFE91E63),
                            onClick = { viewModel.navigateTo(Screen.Calculator) },
                            width = itemWidth,
                            testTag = "action_calc"
                        )
                        QuickActionItem(
                            label = "CV Builder",
                            icon = Icons.Default.Badge,
                            color = Color(0xFF00C853),
                            onClick = { viewModel.navigateTo(Screen.CvBuilder) },
                            width = itemWidth,
                            testTag = "action_cv"
                        )
                        QuickActionItem(
                            label = "Study Timer",
                            icon = Icons.Default.Timer,
                            color = Color(0xFFFF9100),
                            onClick = { viewModel.navigateTo(Screen.StudyTimer) },
                            width = itemWidth,
                            testTag = "action_timer"
                        )
                        QuickActionItem(
                            label = "Bills Due",
                            icon = Icons.Default.ReceiptLong,
                            color = Color(0xFF00B0FF),
                            onClick = { viewModel.navigateTo(Screen.UtilityBills) },
                            width = itemWidth,
                            testTag = "action_bills"
                        )
                    }
                }
            }

            // Timetable Snippet (Today's Next Class)
            val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1=Sun, 2=Mon...
            // Map Sunday to 7, Monday to 1, etc.
            val adjustedDayNum = when (currentDayOfWeek) {
                Calendar.MONDAY -> 1
                Calendar.TUESDAY -> 2
                Calendar.WEDNESDAY -> 3
                Calendar.THURSDAY -> 4
                Calendar.FRIDAY -> 5
                Calendar.SATURDAY -> 6
                Calendar.SUNDAY -> 7
                else -> 1
            }

            val todaysClasses = timetable.filter { it.dayOfWeek == adjustedDayNum }
            Text(
                text = "Today's Classes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (todaysClasses.isEmpty()) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Weekend, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "No classes scheduled for today! Enjoy your day.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(todaysClasses) { classItem ->
                        Card(
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (!classItem.color.isNullOrEmpty()) {
                                    try { Color(android.graphics.Color.parseColor(classItem.color)) } catch (e: Exception) { MaterialTheme.colorScheme.surface }
                                } else {
                                    MaterialTheme.colorScheme.secondaryContainer
                                }.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier
                                .width(220.dp)
                                .clickable { viewModel.navigateTo(Screen.Timetable) }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = classItem.subject,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${classItem.startTime} - ${classItem.endTime}",
                                        fontSize = 12.sp
                                    )
                                }
                                if (!classItem.teacher.isNullOrEmpty() || !classItem.room.isNullOrEmpty()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = classItem.teacher ?: "", fontSize = 11.sp, maxLines = 1)
                                        Text(
                                            text = "Room: ${classItem.room ?: "N/A"}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Upcoming Bills
            val upcomingBills = bills.filter { it.isPaid == 0 }.take(3)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Upcoming Bills",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = { viewModel.navigateTo(Screen.UtilityBills) }) {
                    Text("View All", fontSize = 12.sp)
                }
            }

            if (upcomingBills.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "🥳 Standard alert: All clear! No unsettled utility bills.",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    upcomingBills.forEach { bill ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                .clickable { viewModel.navigateTo(Screen.UtilityBills) }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Constants.getCategoryIcon(bill.category),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(text = bill.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "Due: ${bill.dueDate}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                                }
                            }
                            Text(text = "Rs. ${bill.amount}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Goals progress
            if (savingsGoals.isNotEmpty()) {
                Text(
                    text = "Savings Progress",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                savingsGoals.take(2).forEach { goal ->
                    val progress = if (goal.targetAmount > 0) (goal.currentAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f) else 0f
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = goal.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(
                                    text = "${String.format("%.0f", goal.currentAmount)} / ${String.format("%.0f", goal.targetAmount)} Rs.",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = progress,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun QuickActionItem(
    label: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    width: androidx.compose.ui.unit.Dp,
    testTag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(width)
            .clickable(onClick = onClick)
            .testTag(testTag)
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
