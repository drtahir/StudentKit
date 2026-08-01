package com.example.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.StudentKitViewModel
import kotlinx.coroutines.delay

// =============================================================================
// MODULE 12: PAKISTAN PHARMACY CATEGORY B EXAM PREPARATION PORTAL
// =============================================================================

data class ExamQuestion(
    val id: Int,
    val subject: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val reference: String // Pakistani Syllabus/Drug Act 1976 reference
)

data class StudyTopic(
    val title: String,
    val paperSection: String,
    val content: String,
    val clinicalPearls: String,
    val typicalExamQuestion: String
)

data class CalcPracticeProblem(
    val id: Int,
    val title: String,
    val prompt: String,
    val correctAnswer: Double,
    val unit: String,
    val formula: String,
    val explanation: String,
    val parameterPlaceholder: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmacyExamScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var activeSubTab by remember { mutableStateOf(0) } // 0 = Exam Simulator, 1 = Syllabus Flashcards, 2 = Drug Law Hub, 3 = Calculations Trainer

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Upper Sub-tab navigation
        ScrollableTabRow(
            selectedTabIndex = activeSubTab,
            edgePadding = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = activeSubTab == 0,
                onClick = { activeSubTab = 0 },
                text = { Text("Mock Exam", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Timer, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeSubTab == 1,
                onClick = { activeSubTab = 1 },
                text = { Text("High-Yield Notes", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.MenuBook, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeSubTab == 2,
                onClick = { activeSubTab = 2 },
                text = { Text("Pakistan Drug Law", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Gavel, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeSubTab == 3,
                onClick = { activeSubTab = 3 },
                text = { Text("Calculations Lab", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Calculate, null, modifier = Modifier.size(16.dp)) }
            )
        }

        when (activeSubTab) {
            0 -> PharmacyExamSimulatorView()
            1 -> PharmacySyllabusExplorerView()
            2 -> PakistanDrugLawHubView()
            3 -> PharmaceuticalCalculationsTrainer()
        }
    }
}

/**
 * 1. INTERACTIVE EXAM SIMULATOR & 500+ QUESTION PRACTICE PORTAL
 */
@Composable
fun PharmacyExamSimulatorView() {
    val context = LocalContext.current
    
    // Load complete 500+ question bank covering all 7 Category B subjects
    val allQuestions = remember { PharmacyQuestionBank.getAllQuestions() }
    
    var selectedSubjectFilter by remember { mutableStateOf("All Subjects") }
    var searchQuery by remember { mutableStateOf("") }
    var instantLearnMode by remember { mutableStateOf(true) } // Instant Red/Green feedback on tap
    
    // Filter questions based on subject chip and search text
    val filteredQuestions = remember(selectedSubjectFilter, searchQuery, allQuestions) {
        allQuestions.filter { q ->
            val matchesSubject = (selectedSubjectFilter == "All Subjects") || q.subject.equals(selectedSubjectFilter, ignoreCase = true)
            val matchesSearch = searchQuery.isBlank() || 
                q.question.contains(searchQuery, ignoreCase = true) || 
                q.explanation.contains(searchQuery, ignoreCase = true) ||
                q.options.any { it.contains(searchQuery, ignoreCase = true) }
            matchesSubject && matchesSearch
        }
    }

    var currentQIndex by remember { mutableStateOf(0) }
    val selectedAnswers = remember { mutableStateMapOf<Int, Int>() } // questionId -> selectedOptionIndex
    var examSubmitted by remember { mutableStateOf(false) }
    var showGridDialog by remember { mutableStateOf(false) }
    var jumpInputText by remember { mutableStateOf("") }
    
    // Exam Timer State for Board Exam Mode
    var timerSeconds by remember { mutableStateOf(1800) } // 30 minutes for board exam mode
    var isTimerActive by remember { mutableStateOf(false) }

    // Clamp currentQIndex if filtered list shrinks
    LaunchedEffect(filteredQuestions.size) {
        if (currentQIndex >= filteredQuestions.size && filteredQuestions.isNotEmpty()) {
            currentQIndex = 0
        }
    }

    LaunchedEffect(isTimerActive, examSubmitted) {
        if (isTimerActive && !examSubmitted) {
            while (timerSeconds > 0) {
                delay(1000)
                timerSeconds--
            }
            if (timerSeconds == 0) {
                examSubmitted = true
                Toast.makeText(context, "Time is up! Exam submitted automatically.", Toast.LENGTH_LONG).show()
            }
        }
    }

    val totalAnsweredInFilter = filteredQuestions.count { selectedAnswers.containsKey(it.id) }
    val correctCountInFilter = filteredQuestions.count { selectedAnswers[it.id] == it.correctIndex }
    
    val activeQ = if (filteredQuestions.isNotEmpty() && currentQIndex < filteredQuestions.size) {
        filteredQuestions[currentQIndex]
    } else null

    val progress = if (filteredQuestions.isNotEmpty()) (currentQIndex + 1).toFloat() / filteredQuestions.size else 0f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Mode & Stats Header Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Quiz,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "500+ Category B Board Question Bank",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Total Bank: ${allQuestions.size} Questions | Active Filter: ${filteredQuestions.size} Qs",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF2E7D32).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "$correctCountInFilter / $totalAnsweredInFilter Correct",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    // Mode Selector Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FilterChip(
                                selected = instantLearnMode,
                                onClick = { 
                                    instantLearnMode = true 
                                    isTimerActive = false
                                },
                                label = { Text("Instant Learn Mode", fontSize = 11.sp) },
                                leadingIcon = {
                                    Icon(Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(14.dp))
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            FilterChip(
                                selected = !instantLearnMode,
                                onClick = { 
                                    instantLearnMode = false
                                    isTimerActive = true
                                    if (timerSeconds == 0) timerSeconds = 1800
                                },
                                label = { Text("Timed Board Exam", fontSize = 11.sp) },
                                leadingIcon = {
                                    Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(14.dp))
                                }
                            )
                        }

                        if (!instantLearnMode && !examSubmitted) {
                            val mins = timerSeconds / 60
                            val secs = timerSeconds % 60
                            Text(
                                text = String.format("%02d:%02d", mins, secs),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (timerSeconds < 300) Color.Red else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        // Subject Filter Chips
        item {
            val subjects = listOf("All Subjects", "Pharmaceutics", "Pharmacology", "Pharmacognosy", "Pharmacy Law & Ethics", "Anatomy & Physiology", "Microbiology", "Biochemistry")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(subjects.size) { idx ->
                    val subj = subjects[idx]
                    val isSel = selectedSubjectFilter == subj
                    val count = if (subj == "All Subjects") allQuestions.size else allQuestions.count { it.subject.equals(subj, ignoreCase = true) }
                    FilterChip(
                        selected = isSel,
                        onClick = {
                            selectedSubjectFilter = subj
                            currentQIndex = 0
                        },
                        label = { Text("$subj ($count)", fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.secondary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Search Bar & Question Jump Controls
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search question keywords...", fontSize = 12.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Grid View Launcher
                Button(
                    onClick = { showGridDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Icon(Icons.Default.GridView, contentDescription = "Question Grid", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Grid", fontSize = 12.sp)
                }
            }
        }

        // Question Navigation Bar
        item {
            if (filteredQuestions.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Subject: ${activeQ?.subject ?: ""}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Q ${currentQIndex + 1} of ${filteredQuestions.size}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            // Quick Jump Input
                            OutlinedTextField(
                                value = jumpInputText,
                                onValueChange = { input ->
                                    if (input.all { it.isDigit() }) {
                                        jumpInputText = input
                                        val num = input.toIntOrNull()
                                        if (num != null && num in 1..filteredQuestions.size) {
                                            currentQIndex = num - 1
                                        }
                                    }
                                },
                                placeholder = { Text("#", fontSize = 10.sp) },
                                modifier = Modifier.width(52.dp).height(40.dp),
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                    )
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = "No questions match your current search/subject filter. Clear search or pick another subject.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // Active Question Display & Selectable Options
        activeQ?.let { q ->
            val userSelected = selectedAnswers[q.id]
            val hasAnswered = userSelected != null

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = "Question #${q.id} • ${q.subject}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }

                            if (hasAnswered) {
                                val isRight = userSelected == q.correctIndex
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isRight) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (isRight) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                            contentDescription = null,
                                            tint = if (isRight) Color(0xFF2E7D32) else Color(0xFFC62828),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (isRight) "CORRECT" else "INCORRECT",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isRight) Color(0xFF2E7D32) else Color(0xFFC62828)
                                        )
                                    }
                                }
                            }
                        }

                        Text(
                            text = q.question,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Options list with Instant Red (Wrong) and Green (Correct) Highlighting
            items(q.options.size) { index ->
                val isThisOptionSelected = userSelected == index
                val isThisCorrectOption = index == q.correctIndex
                
                // Highlight rules:
                // Show colors if answered (in Instant Learn Mode or after Exam Submission)
                val showFeedbackColors = (instantLearnMode && hasAnswered) || examSubmitted

                val isDark = isSystemInDarkTheme()
                val correctBg = if (isDark) Color(0xFF1B3E20) else Color(0xFFE8F5E9)
                val correctBorder = if (isDark) Color(0xFF81C784) else Color(0xFF2E7D32)
                val correctText = if (isDark) Color(0xFFA5D6A7) else Color(0xFF1B5E20)

                val wrongBg = if (isDark) Color(0xFF3E1B1B) else Color(0xFFFFEBEE)
                val wrongBorder = if (isDark) Color(0xFFEF9A9A) else Color(0xFFC62828)
                val wrongText = if (isDark) Color(0xFFFFCDD2) else Color(0xFFB71C1C)

                val containerColor = when {
                    showFeedbackColors && isThisCorrectOption -> correctBg
                    showFeedbackColors && isThisOptionSelected && !isThisCorrectOption -> wrongBg
                    isThisOptionSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surface
                }

                val borderColor = when {
                    showFeedbackColors && isThisCorrectOption -> correctBorder
                    showFeedbackColors && isThisOptionSelected && !isThisCorrectOption -> wrongBorder
                    isThisOptionSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outlineVariant
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedAnswers[q.id] = index
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = containerColor),
                    border = BorderStroke(
                        width = if (isThisOptionSelected || (showFeedbackColors && isThisCorrectOption)) 2.dp else 1.dp,
                        color = borderColor
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            showFeedbackColors && isThisCorrectOption -> correctBorder
                                            showFeedbackColors && isThisOptionSelected -> wrongBorder
                                            isThisOptionSelected -> MaterialTheme.colorScheme.primary
                                            else -> MaterialTheme.colorScheme.surfaceVariant
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A'.code + index).toChar().toString(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isThisOptionSelected || (showFeedbackColors && isThisCorrectOption)) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = q.options[index],
                                fontSize = 13.sp,
                                fontWeight = if (showFeedbackColors && isThisCorrectOption) FontWeight.Bold else FontWeight.Normal,
                                color = when {
                                    showFeedbackColors && isThisCorrectOption -> correctText
                                    showFeedbackColors && isThisOptionSelected && !isThisCorrectOption -> wrongText
                                    else -> MaterialTheme.colorScheme.onSurface
                                }
                            )
                        }

                        if (showFeedbackColors) {
                            if (isThisCorrectOption) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("✓ Correct", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = Color(0xFF2E7D32), modifier = Modifier.size(20.dp))
                                }
                            } else if (isThisOptionSelected) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("✗ Wrong", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Default.Cancel, contentDescription = "Wrong", tint = Color(0xFFC62828), modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Instant Rationale & Syllabus Reference Box
            if ((instantLearnMode && hasAnswered) || examSubmitted) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Exam Rationale & Detailed Explanation:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = q.explanation,
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Divider(modifier = Modifier.padding(vertical = 4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Bookmark, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Reference: ${q.reference}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }

            // Previous / Next Question Navigation Bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { if (currentQIndex > 0) currentQIndex-- },
                        enabled = currentQIndex > 0,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Previous Q", fontSize = 12.sp)
                    }

                    // Reset / Clear Progress Button
                    IconButton(
                        onClick = {
                            selectedAnswers.clear()
                            examSubmitted = false
                            Toast.makeText(context, "Quiz progress reset.", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset Progress", tint = MaterialTheme.colorScheme.error)
                    }

                    Button(
                        onClick = { if (currentQIndex + 1 < filteredQuestions.size) currentQIndex++ },
                        enabled = currentQIndex + 1 < filteredQuestions.size,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Next Q", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }

    // 500+ Question Quick Navigation Grid Dialog
    if (showGridDialog) {
        AlertDialog(
            onDismissRequest = { showGridDialog = false },
            icon = { Icon(Icons.Default.GridView, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            title = {
                Text(
                    text = "Question Directory (${filteredQuestions.size} Qs)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color(0xFF2E7D32)))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Correct", fontSize = 10.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color(0xFFC62828)))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Wrong", fontSize = 10.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color.LightGray))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Unanswered", fontSize = 10.sp)
                        }
                    }

                    Divider()

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(filteredQuestions.size) { index ->
                            val q = filteredQuestions[index]
                            val userAns = selectedAnswers[q.id]
                            val isAns = userAns != null
                            val isCorrect = isAns && userAns == q.correctIndex

                            val btnBg = when {
                                isCorrect -> Color(0xFFE8F5E9)
                                isAns -> Color(0xFFFFEBEE)
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }

                            val txtColor = when {
                                isCorrect -> Color(0xFF2E7D32)
                                isAns -> Color(0xFFC62828)
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            }

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(btnBg)
                                    .border(
                                        width = if (index == currentQIndex) 2.dp else 0.5.dp,
                                        color = if (index == currentQIndex) MaterialTheme.colorScheme.primary else Color.Transparent
                                    )
                                    .clickable {
                                        currentQIndex = index
                                        showGridDialog = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = txtColor
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showGridDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

/**
 * 2. SYLLABUS HIGH-YIELD EXPLORER / STUDY NOTES
 */
@Composable
fun PharmacySyllabusExplorerView() {
    var selectedSyllabusCategory by remember { mutableStateOf("All") }
    val syllabusCategories = listOf("All", "Paper I", "Paper II")

    val notes = remember {
        listOf(
            StudyTopic(
                "Emulsions & Suspensions Formulation", "Paper II (Pharmaceutics)",
                "• Emulsions are biphasic liquid formulations where one liquid is dispersed as tiny droplets inside another (O/W or W/O). Emulsifying agents (e.g., Acacia, Tragacanth) reduce interfacial tension to prevent cracking.\n• Suspensions are biphasic systems containing insoluble solid particles dispersed in a liquid vehicle. Flocculating agents form loose network-like aggregates (flocs) that settle rapidly but are easily redispersed upon gentle shaking.",
                "Clinical Tip: Always place a 'SHAKE WELL BEFORE USE' auxiliary label on suspensions to ensure dose uniformity.",
                "Exam Question: State the primary difference between flocculated and deflocculated suspensions."
            ),
            StudyTopic(
                "Mechanism of Beta Adrenergic Blockers", "Paper II (Pharmacology)",
                "• Beta-1 blockers (Atenolol, Metoprolol) act selectively on heart receptors to decrease cardiac output, heart rate, and renin secretion.\n• Beta-2 receptors are in pulmonary bronchial smooth muscles. Non-selective beta-blockers (Propranolol) block both Beta-1 and Beta-2, which can cause severe, life-threatening bronchoconstriction in asthmatic patients.",
                "Clinical Tip: Propranolol is absolutely contraindicated in patients suffering from active Bronchial Asthma.",
                "Exam Question: Why are selective Beta-1 blockers preferred over non-selective ones in hypertensive asthmatic patients?"
            ),
            StudyTopic(
                "Alkaloids & Glycosides Natural Sources", "Paper II (Pharmacognosy)",
                "• Alkaloids: Basic nitrogenous compounds of plant origin (e.g. Quinine from Cinchona ledgeriana cortex, Atropine from Atropa belladonna leaves). They form precipitates with Mayer's and Dragendorff's chemical reagents.\n• Glycosides: Contain an organic sugar group (glycone) bound to a non-sugar active molecule (aglycone). E.g., Digitalis (cardiac glycoside from Digitalis purpurea leaves) used in cardiac congestive heart failure treatment.",
                "Clinical Tip: Atropine is a powerful physiological antidote used to treat poisoning from organophosphate insecticides.",
                "Exam Question: Name the botanical source and active alkaloid of Cinchona bark."
            ),
            StudyTopic(
                "Autoclave & Dry Heat Sterilization Mechanics", "Paper I (Microbiology)",
                "• Autoclave (Moist Heat): Uses saturated steam under high pressure. Standard settings are 121°C for 15-20 minutes. It coagulates and denatures essential bacterial cellular proteins.\n• Hot Air Oven (Dry Heat): Employs high temperatures without moisture. Standard protocol is 160°C for 2 hours. Destroys microbes by thermal oxidation of intracellular materials.",
                "Clinical Tip: Surgical dressings, syringes, and glass vials must be thoroughly sterilized to prevent systemic hospital-acquired infections.",
                "Exam Question: Why is moist heat sterilization more rapid and effective than dry heat at equivalent temperatures?"
            ),
            StudyTopic(
                "Systemic Circulations & Cardiac Valves", "Paper I (Anatomy & Physiology)",
                "• Systemic circulation carries oxygen-rich arterial blood from the left ventricle through the aorta to the body, returning deoxygenated venous blood to the right atrium.\n• Tricuspid valve guards the right atrium-ventricle opening; Bicuspid (Mitral) valve guards the left. Semilunar valves prevent backflow into the ventricles from the aorta and pulmonary artery.",
                "Clinical Tip: Damaged or stenotic mitral valves heavily restrict left ventricular ejection capacity, leading to pulmonary congestion.",
                "Exam Question: Trace the path of blood starting from the Superior Vena Cava up to the Ascending Aorta."
            )
        )
    }

    val filteredNotes = if (selectedSyllabusCategory == "All") {
        notes
    } else {
        notes.filter { it.paperSection.contains(selectedSyllabusCategory) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Filter Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            syllabusCategories.forEach { category ->
                val isSelected = selectedSyllabusCategory == category
                ElevatedFilterChip(
                    selected = isSelected,
                    onClick = { selectedSyllabusCategory = category },
                    label = { Text(category, fontSize = 12.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Notes List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredNotes) { topic ->
                var isExpanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = !isExpanded },
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = topic.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = topic.paperSection,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            }
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        if (isExpanded) {
                            Divider(modifier = Modifier.padding(vertical = 4.dp))
                            Text(
                                text = topic.content,
                                fontSize = 12.5.sp,
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            // Clinical Highlight box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = topic.clinicalPearls,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1B5E20)
                                )
                            }

                            // Typical Exam Question box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFFFFDE7), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Text(
                                        "❓ TYPICAL EXAM QUESTION:",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFFF57F17)
                                    )
                                    Text(
                                        text = topic.typicalExamQuestion,
                                        fontSize = 11.sp,
                                        color = Color.DarkGray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 3. PAKISTAN DRUG LAW & ETHICS HUB (Category B Specifics)
 */
@Composable
fun PakistanDrugLawHubView() {
    val laws = remember {
        listOf(
            Pair("Registration of Pharmacy Apprentices / Technicians", "Under Section 24 of the Pharmacy Act 1967, Category B registration is designated for 'Pharmacy Assistants'. This allows the holder to legally practice dispensing under standard rules set by the provincial Pharmacy Council (e.g., Punjab Pharmacy Council)."),
            Pair("Retail Pharmacy License Requirements", "According to Punjab/Federal Drug Rules, a retail pharmacy shop license (Form 9) requires a qualified person registered under Category A (Pharmacist) or Category B (Pharmacy Assistant) to be physically present at the premises during all operational hours to supervise compounding and dispensing of narcotic/controlled drugs."),
            Pair("Warranty Form 5 (Drug Act 1976)", "Every manufacturer or registered importer selling drug products to retailers must execute a warranty of quality under Form 5. This legally protects the retailer from prosecution if the drug is later found to be sub-standard or adulterated, provided it was stored properly."),
            Pair("Powers of Federal & Provincial Drug Inspectors", "Under Section 18 of the Drug Act 1976, inspectors have the authority to enter and search any licensed premises, seize samples of suspicious drug products, inspect manufacturing logs, and lock and seal premises violating drug schedules."),
            Pair("Schedules of the Drug Rules", "• Schedule B: Standard requirements for physical space, ventilation, hygienic standards, and laboratory equipment of pharmacies.\n• Schedule G: Lists dangerous and controlled narcotic drugs requiring a locked cabinet and double-receipt carbon-copy records with prescription details retained for a minimum of 2 years.")
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Pakistan Drug Act 1976 Study Hub",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "High-yield laws, rules, and licensing codes for the Paper-II Law portion.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        items(laws) { (title, description) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.VerifiedUser, null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Divider(color = Color.LightGray.copy(alpha = 0.4f))
                    Text(
                        text = description,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

/**
 * 4. PHARMACEUTICAL CALCULATIONS PRACTICE TRAINER
 */
@Composable
fun PharmaceuticalCalculationsTrainer() {
    val context = LocalContext.current

    val problems = remember {
        listOf(
            CalcPracticeProblem(
                1, "Pediatric Dose (Young's Rule)",
                "Calculate the dose for a child aged 4 years old, given that the standard adult dose of the drug is 150 mg. (Round to 1 decimal place)",
                37.5, "mg", "Young's Rule: Child Dose = [Age / (Age + 12)] * Adult Dose",
                "Using the formula: [4 / (4 + 12)] * 150 = [4 / 16] * 150 = 0.25 * 150 = 37.5 mg.",
                "Adult Dose (mg)"
            ),
            CalcPracticeProblem(
                2, "Percentage Solution Compounding",
                "How many grams of Dextrose powder are needed to prepare 250 mL of a 5% w/v Dextrose solution?",
                12.5, "g", "Weight (g) = [Percentage (%) * Volume (mL)] / 100",
                "5% w/v means 5 grams per 100 mL. For 250 mL: (5 * 250) / 100 = 12.5 grams.",
                "Target Volume (mL)"
            ),
            CalcPracticeProblem(
                3, "Pediatric Dose by Weight (Clark's Rule)",
                "Using Clark's Rule, calculate the pediatric dose for a child weighing 45 lbs, given that the average adult dose of the drug is 100 mg. (Round to 1 decimal place)",
                30.0, "mg", "Clark's Rule: Child Dose = [Weight (lbs) / 150] * Adult Dose",
                "Using Clark's formula: (45 / 150) * 100 = 0.3 * 100 = 30 mg.",
                "Adult Dose (mg)"
            )
        )
    }

    var selectedProblemId by remember { mutableStateOf(1) }
    var userInputText by remember { mutableStateOf("") }
    var hasAnswered by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf(false) }

    val activeProblem = problems.first { it.id == selectedProblemId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Selector Chips
        Text(
            "Select Calculation Formula:",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            problems.forEach { problem ->
                val isSelected = selectedProblemId == problem.id
                ElevatedFilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedProblemId = problem.id
                        userInputText = ""
                        hasAnswered = false
                    },
                    label = { Text(problem.title, fontSize = 11.sp) }
                )
            }
        }

        // Active Problem Description
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "✏️ PROBLEM STATEMENT:",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = activeProblem.prompt,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                Divider(color = Color.LightGray.copy(alpha = 0.5f))
                Text(
                    text = "🔧 Formula: ${activeProblem.formula}",
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        // User Input & Actions
        OutlinedTextField(
            value = userInputText,
            onValueChange = { userInputText = it.filter { char -> char.isDigit() || char == '.' } },
            label = { Text("Enter calculated value (${activeProblem.unit})") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            suffix = { Text(activeProblem.unit) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !hasAnswered
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (!hasAnswered) {
                Button(
                    onClick = {
                        val parsed = userInputText.toDoubleOrNull()
                        if (parsed == null) {
                            Toast.makeText(context, "Please enter a valid numeric value!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isAnswerCorrect = Math.abs(parsed - activeProblem.correctAnswer) < 0.15
                        hasAnswered = true
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.DoneOutline, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Verify Answer")
                }
            } else {
                Button(
                    onClick = {
                        userInputText = ""
                        hasAnswered = false
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Refresh, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Retry Problem")
                }
            }
        }

        // feedback block
        if (hasAnswered) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isAnswerCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                ),
                border = BorderStroke(1.dp, if (isAnswerCorrect) Color(0xFF4CAF50) else Color(0xFFE53935))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isAnswerCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                            contentDescription = null,
                            tint = if (isAnswerCorrect) Color(0xFF2E7D32) else Color(0xFFC62828),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isAnswerCorrect) "EXCELLENT! ANSWER CORRECT" else "INCORRECT CALCULATION",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = if (isAnswerCorrect) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                    }

                    Text(
                        text = "Correct Answer: ${activeProblem.correctAnswer} ${activeProblem.unit}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Divider(color = Color.LightGray.copy(alpha = 0.5f))

                    Text(
                        text = "Step-by-step Solution:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = activeProblem.explanation,
                        fontSize = 12.5.sp,
                        lineHeight = 18.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
