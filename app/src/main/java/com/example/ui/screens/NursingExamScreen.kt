package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.StudentKitViewModel
import kotlinx.coroutines.delay

@Composable
private fun getBrandTeal(): Color {
    return if (isSystemInDarkTheme()) Color(0xFF4DB6AC) else Color(0xFF00695C)
}

@Composable
private fun getHeaderBannerColor(): Color {
    return if (isSystemInDarkTheme()) Color(0xFF00332C) else Color(0xFF00695C)
}

@Composable
private fun getCorrectContainerColor(): Color {
    return if (isSystemInDarkTheme()) Color(0xFF1B3E20) else Color(0xFFE8F5E9)
}

@Composable
private fun getCorrectBorderColor(): Color {
    return if (isSystemInDarkTheme()) Color(0xFF81C784) else Color(0xFF2E7D32)
}

@Composable
private fun getCorrectTextColor(): Color {
    return if (isSystemInDarkTheme()) Color(0xFFA5D6A7) else Color(0xFF1B5E20)
}

@Composable
private fun getWrongContainerColor(): Color {
    return if (isSystemInDarkTheme()) Color(0xFF3E1B1B) else Color(0xFFFFEBEE)
}

@Composable
private fun getWrongBorderColor(): Color {
    return if (isSystemInDarkTheme()) Color(0xFFEF9A9A) else Color(0xFFC62828)
}

@Composable
private fun getWrongTextColor(): Color {
    return if (isSystemInDarkTheme()) Color(0xFFFFCDD2) else Color(0xFFB71C1C)
}

/**
 * COMPREHENSIVE NURSING STUDENTS KIT
 * Dedicated exam simulator and 12000+ Nursing Question Bank & textbook for DHA Dubai, Saudi Prometric (SCFHS),
 * HAAD/DoH Abu Dhabi, MOH Gulf, Qatar QCHP, Oman OMSB, NCLEX-RN, and PNC exams.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NursingExamScreen(
    viewModel: StudentKitViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Quiz Bank, 1: Exam Info & DataFlow, 2: Dosage & Lab Calculators, 3: KP BSN Curriculum
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()
    val brandTeal = getBrandTeal()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Clean Top Navigation Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = brandTeal,
            edgePadding = 12.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("12000+ Quiz Bank", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) },
                icon = { Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Exam Guides", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) },
                icon = { Icon(Icons.Default.VerifiedUser, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Dosage & ABG", fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) },
                icon = { Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                text = { Text("KP BSN Curriculum", fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) },
                icon = { Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        // Screen Tab Views
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> NursingQuizBankView()
                1 -> NursingExamGuidesView()
                2 -> NursingClinicalCalculatorsView()
                3 -> KpNursingSemesterCurriculumView()
            }
        }
    }
}

/**
 * 1. 12000+ NURSING EXAM SIMULATOR & QUIZ BANK VIEW
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NursingQuizBankView() {
    val allQuestions = remember { NursingQuestionBank.getAllQuestions() }
    val selectedAnswers = remember { mutableStateMapOf<Int, Int>() }
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()
    val brandTeal = getBrandTeal()

    val prefs = remember { context.getSharedPreferences("nursing_quiz_progress_prefs_v2", android.content.Context.MODE_PRIVATE) }

    var selectedSubjectFilter by remember {
        mutableStateOf(prefs.getString("last_selected_subject_filter", "All Subjects") ?: "All Subjects")
    }
    var searchQuery by remember { mutableStateOf("") }
    var currentQIndex by remember { mutableStateOf(0) }
    var jumpInputText by remember { mutableStateOf("") }
    var instantLearnMode by remember { mutableStateOf(true) }
    var timedMode by remember { mutableStateOf(false) }
    var timerSeconds by remember { mutableStateOf(1800) } // 30 mins
    var isTimerActive by remember { mutableStateOf(false) }
    var examSubmitted by remember { mutableStateOf(false) }
    var showGridDialog by remember { mutableStateOf(false) }
    var showSubjectAnalyticsDialog by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    // Load saved answers from SharedPreferences efficiently
    LaunchedEffect(Unit) {
        val allPrefs = prefs.all
        allPrefs.forEach { (key, value) ->
            if (key.startsWith("ans_") && value is Int && value >= 0) {
                val qId = key.removePrefix("ans_").toIntOrNull()
                if (qId != null) {
                    selectedAnswers[qId] = value
                }
            }
        }
    }

    // Precompute Subject Counts Map for O(1) Chip Rendering
    val subjectCountsMap = remember(allQuestions) {
        val map = mutableMapOf<String, Int>()
        allQuestions.forEach { q ->
            val s = q.subject.trim()
            if (s.isNotEmpty()) {
                map[s] = (map[s] ?: 0) + 1
            }
        }
        map
    }

    val answeredCountsMap = remember(allQuestions, selectedAnswers.size) {
        val map = mutableMapOf<String, Int>()
        allQuestions.forEach { q ->
            if (selectedAnswers.containsKey(q.id)) {
                val s = q.subject.trim()
                if (s.isNotEmpty()) {
                    map[s] = (map[s] ?: 0) + 1
                }
            }
        }
        map
    }

    // Subject Filter Categories
    val subjectsList = remember(allQuestions) {
        listOf("All Subjects") + allQuestions.map { it.subject.trim() }.filter { it.isNotEmpty() }.distinct().sorted()
    }

    // Filtered Question List with Fallback
    val filteredQuestions = remember(selectedSubjectFilter, searchQuery, allQuestions) {
        val filtered = allQuestions.filter { q ->
            val matchSubj = (selectedSubjectFilter == "All Subjects") || q.subject.equals(selectedSubjectFilter, ignoreCase = true)
            val matchSearch = searchQuery.isEmpty() || q.question.contains(searchQuery, ignoreCase = true) || q.options.any { it.contains(searchQuery, ignoreCase = true) }
            matchSubj && matchSearch
        }
        if (filtered.isEmpty() && selectedSubjectFilter != "All Subjects" && searchQuery.isEmpty()) {
            allQuestions
        } else {
            filtered
        }
    }

    // Restore saved question position when filter changes
    LaunchedEffect(selectedSubjectFilter) {
        prefs.edit().putString("last_selected_subject_filter", selectedSubjectFilter).apply()
        val savedIndex = prefs.getInt("subj_index_$selectedSubjectFilter", 0)
        val validIndex = savedIndex.coerceIn(0, maxOf(0, filteredQuestions.size - 1))
        currentQIndex = validIndex
    }

    // Save position whenever currentQIndex changes
    LaunchedEffect(currentQIndex, selectedSubjectFilter) {
        if (filteredQuestions.isNotEmpty()) {
            prefs.edit().putInt("subj_index_$selectedSubjectFilter", currentQIndex).apply()
        }
    }

    // Timer coroutine
    LaunchedEffect(isTimerActive, timerSeconds) {
        if (isTimerActive && timerSeconds > 0) {
            delay(1000L)
            timerSeconds--
        } else if (isTimerActive && timerSeconds == 0) {
            examSubmitted = true
            isTimerActive = false
            Toast.makeText(context, "Time limit reached! Exam submitted automatically.", Toast.LENGTH_LONG).show()
        }
    }

    val activeQ = filteredQuestions.getOrNull(currentQIndex.coerceIn(0, maxOf(0, filteredQuestions.size - 1)))
    val progress = if (filteredQuestions.isNotEmpty()) (currentQIndex + 1).toFloat() / filteredQuestions.size else 0f

    // Active Subject Metrics
    val totalInFilter = filteredQuestions.size
    val answeredInFilter = filteredQuestions.filter { selectedAnswers.containsKey(it.id) }
    val completedCountInFilter = answeredInFilter.size
    val completedPercentInFilter = if (totalInFilter > 0) (completedCountInFilter.toFloat() / totalInFilter) * 100f else 0f

    val correctCountInFilter = filteredQuestions.count { q -> selectedAnswers[q.id] == q.correctIndex }
    val incorrectCountInFilter = completedCountInFilter - correctCountInFilter
    val correctPercentInFilter = if (completedCountInFilter > 0) (correctCountInFilter.toFloat() / completedCountInFilter) * 100f else 0f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Hero Header & Target Exam Badges
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) Color(0xFF00332C) else Color(0xFF00695C)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.2f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.MedicalServices,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Nursing Exam & Clinical Kit",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "DHA, Saudi Prometric, NCLEX-RN, HAAD, MOH & PNC",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "12000+ Qs",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    // Exam Badges Row
                    val examBadges = listOf("DHA Dubai", "Saudi Prometric", "NCLEX-RN", "HAAD / DoH", "MOH Gulf", "PNC Pakistan")
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(examBadges.size) { index ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White.copy(alpha = 0.15f),
                                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.3f))
                            ) {
                                Text(
                                    text = "✓ ${examBadges[index]}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Control Dashboard Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) Color(0xFF1B2826) else brandTeal.copy(alpha = 0.08f)
                ),
                border = BorderStroke(1.dp, brandTeal.copy(alpha = 0.25f))
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Quiz, contentDescription = null, tint = brandTeal, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "12000+ Nursing Question Bank",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = brandTeal
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isDark) Color(0xFF1E3A20) else Color(0xFF2E7D32).copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${String.format(java.util.Locale.US, "%.1f", correctPercentInFilter)}% Correct",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDark) Color(0xFF81C784) else Color(0xFF2E7D32),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }

                            IconButton(
                                onClick = { showSubjectAnalyticsDialog = true },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Assessment, contentDescription = "Subject Analytics", tint = brandTeal)
                            }
                        }
                    }

                    // Progress Overview Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Filter: $selectedSubjectFilter",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Completed: $completedCountInFilter / $totalInFilter Qs (${String.format(java.util.Locale.US, "%.1f", completedPercentInFilter)}%)",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = brandTeal.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Bookmark, contentDescription = null, tint = brandTeal, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Resumed Q#${currentQIndex + 1}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = brandTeal
                                )
                            }
                        }
                    }

                    LinearProgressIndicator(
                        progress = if (totalInFilter > 0) completedCountInFilter.toFloat() / totalInFilter else 0f,
                        color = Color(0xFF10B981),
                        trackColor = brandTeal.copy(alpha = 0.2f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                    )

                    // Mode Toggles: Instant Learn Mode vs Timed Board Exam
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            FilterChip(
                                selected = instantLearnMode,
                                onClick = {
                                    instantLearnMode = true
                                    timedMode = false
                                    isTimerActive = false
                                },
                                label = { Text("Instant Learn", fontSize = 11.sp) },
                                leadingIcon = {
                                    Icon(Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(14.dp))
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = brandTeal,
                                    selectedLabelColor = Color.White
                                )
                            )

                            FilterChip(
                                selected = timedMode,
                                onClick = {
                                    timedMode = true
                                    instantLearnMode = false
                                    isTimerActive = true
                                    if (timerSeconds == 0) timerSeconds = 1800
                                },
                                label = { Text("Timed Exam", fontSize = 11.sp) },
                                leadingIcon = {
                                    Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(14.dp))
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = brandTeal,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }

                        if (timedMode) {
                            val mins = timerSeconds / 60
                            val secs = timerSeconds % 60
                            Text(
                                text = String.format(java.util.Locale.US, "%02d:%02d", mins, secs),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }

        // Subject Filter Chip Row
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(subjectsList.size) { index ->
                    val subj = subjectsList[index]
                    val isSel = selectedSubjectFilter == subj
                    val count = if (subj == "All Subjects") allQuestions.size else (subjectCountsMap[subj] ?: 0)
                    val answeredCountSubj = if (subj == "All Subjects") selectedAnswers.size else (answeredCountsMap[subj] ?: 0)

                    FilterChip(
                        selected = isSel,
                        onClick = {
                            selectedSubjectFilter = subj
                        },
                        label = {
                            Text(
                                text = "$subj ($answeredCountSubj/$count)",
                                fontSize = 11.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = brandTeal,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Search Keyword Field & Directory Grid Button
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

                Button(
                    onClick = { showGridDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = brandTeal, contentColor = Color.White),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Icon(Icons.Default.GridView, contentDescription = "Question Directory Grid", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Grid", fontSize = 12.sp)
                }
            }
        }

        // Progress & Quick Number Jump
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
                            color = brandTeal
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Q ${currentQIndex + 1} of ${filteredQuestions.size}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(8.dp))
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
                        color = brandTeal,
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
                        text = "No nursing questions match your current search or subject filter.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // Active Question Display & Options
        activeQ?.let { q ->
            val userSelected = selectedAnswers[q.id]
            val hasAnswered = userSelected != null

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                                color = brandTeal.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "Question #${q.id} • ${q.examCategory}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = brandTeal,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }

                            if (hasAnswered) {
                                val isRight = userSelected == q.correctIndex
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isRight) getCorrectContainerColor() else getWrongContainerColor()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (isRight) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                            contentDescription = null,
                                            tint = if (isRight) getCorrectBorderColor() else getWrongBorderColor(),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (isRight) "CORRECT" else "INCORRECT",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isRight) getCorrectBorderColor() else getWrongBorderColor()
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

            // Options List with Theme-Aware Highlighting
            items(q.options.size) { index ->
                val isThisOptionSelected = userSelected == index
                val isThisCorrectOption = index == q.correctIndex

                val showFeedbackColors = (instantLearnMode && hasAnswered) || examSubmitted

                val containerColor = when {
                    showFeedbackColors && isThisCorrectOption -> getCorrectContainerColor()
                    showFeedbackColors && isThisOptionSelected && !isThisCorrectOption -> getWrongContainerColor()
                    isThisOptionSelected -> brandTeal.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surface
                }

                val borderColor = when {
                    showFeedbackColors && isThisCorrectOption -> getCorrectBorderColor()
                    showFeedbackColors && isThisOptionSelected && !isThisCorrectOption -> getWrongBorderColor()
                    isThisOptionSelected -> brandTeal
                    else -> MaterialTheme.colorScheme.outlineVariant
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedAnswers[q.id] = index
                            prefs.edit().putInt("ans_${q.id}", index).apply()
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
                                            showFeedbackColors && isThisCorrectOption -> getCorrectBorderColor()
                                            showFeedbackColors && isThisOptionSelected -> getWrongBorderColor()
                                            isThisOptionSelected -> brandTeal
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
                                    showFeedbackColors && isThisCorrectOption -> getCorrectTextColor()
                                    showFeedbackColors && isThisOptionSelected && !isThisCorrectOption -> getWrongTextColor()
                                    else -> MaterialTheme.colorScheme.onSurface
                                }
                            )
                        }

                        if (showFeedbackColors) {
                            if (isThisCorrectOption) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("✓ Correct", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = getCorrectBorderColor())
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = getCorrectBorderColor(), modifier = Modifier.size(20.dp))
                                }
                            } else if (isThisOptionSelected) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("✗ Wrong", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = getWrongBorderColor())
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Default.Cancel, contentDescription = "Wrong", tint = getWrongBorderColor(), modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Rationale & Reference Box
            if ((instantLearnMode && hasAnswered) || examSubmitted) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDark) Color(0xFF1B2826) else Color(0xFFE0F2F1).copy(alpha = 0.5f)
                        ),
                        border = BorderStroke(1.dp, brandTeal.copy(alpha = 0.3f))
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
                                    color = brandTeal
                                )
                            }
                            Text(
                                text = q.explanation,
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Divider(modifier = Modifier.padding(vertical = 4.dp), color = brandTeal.copy(alpha = 0.2f))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Bookmark, contentDescription = null, tint = brandTeal, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Reference: ${q.reference}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = brandTeal
                                )
                            }
                        }
                    }
                }
            }

            // Next / Prev Nav Buttons & Reset Action
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

                    IconButton(
                        onClick = { showResetConfirmDialog = true }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset Progress", tint = MaterialTheme.colorScheme.error)
                    }

                    Button(
                        onClick = { if (currentQIndex + 1 < filteredQuestions.size) currentQIndex++ },
                        enabled = currentQIndex + 1 < filteredQuestions.size,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = brandTeal, contentColor = Color.White)
                    ) {
                        Text("Next Q", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }

    // Question Grid Quick Directory Dialog
    if (showGridDialog) {
        AlertDialog(
            onDismissRequest = { showGridDialog = false },
            icon = { Icon(Icons.Default.GridView, contentDescription = null, tint = brandTeal) },
            title = {
                Text(
                    text = "Question Directory (${filteredQuestions.size} Qs)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
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
                            Text("Correct", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color(0xFFC62828)))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Incorrect", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Unanswered", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    Box(modifier = Modifier.height(280.dp)) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(5),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(filteredQuestions.size) { index ->
                                val q = filteredQuestions[index]
                                val userSel = selectedAnswers[q.id]
                                val isAnswered = userSel != null
                                val isCorrect = userSel == q.correctIndex

                                val gridColor = when {
                                    !isAnswered -> MaterialTheme.colorScheme.surfaceVariant
                                    isCorrect -> Color(0xFF2E7D32)
                                    else -> Color(0xFFC62828)
                                }

                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(gridColor)
                                        .clickable {
                                            currentQIndex = index
                                            showGridDialog = false
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${index + 1}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isAnswered) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showGridDialog = false }) {
                    Text("Close", color = brandTeal, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // Subject Breakdown & Analytics Dialog
    if (showSubjectAnalyticsDialog) {
        AlertDialog(
            onDismissRequest = { showSubjectAnalyticsDialog = false },
            icon = { Icon(Icons.Default.Assessment, contentDescription = null, tint = brandTeal) },
            title = {
                Text(
                    text = "Subject-Wise Completion & Accuracy",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 380.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Detailed progress and Correct % breakdown for every subject in the 12000+ bank:",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(subjectsList.size) { idx ->
                            val subjName = subjectsList[idx]
                            val subjQs = if (subjName == "All Subjects") allQuestions else allQuestions.filter { it.subject.equals(subjName, ignoreCase = true) }
                            val totalSubj = subjQs.size
                            val answeredSubj = subjQs.count { selectedAnswers.containsKey(it.id) }
                            val correctSubj = subjQs.count { selectedAnswers[it.id] == it.correctIndex }
                            val compPct = if (totalSubj > 0) (answeredSubj.toFloat() / totalSubj) * 100f else 0f
                            val accPct = if (answeredSubj > 0) (correctSubj.toFloat() / answeredSubj) * 100f else 0f
                            val savedSubjIdx = prefs.getInt("subj_index_$subjName", 0) + 1

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = subjName,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = brandTeal
                                        )
                                        Text(
                                            text = "Completed: $answeredSubj / $totalSubj (${String.format("%.1f", compPct)}%)",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "Accuracy: ${String.format("%.1f", accPct)}% Correct ($correctSubj / $answeredSubj)",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = if (accPct >= 70f) Color(0xFF2E7D32) else if (accPct >= 50f) Color(0xFFE65100) else Color(0xFFC62828)
                                        )
                                        Text(
                                            text = "Last position: Q#$savedSubjIdx",
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Button(
                                        onClick = {
                                            selectedSubjectFilter = subjName
                                            showSubjectAnalyticsDialog = false
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = brandTeal),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text("Resume", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSubjectAnalyticsDialog = false }) {
                    Text("Close", color = brandTeal, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // Reset Confirmation Dialog
    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            icon = { Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("Reset Quiz Progress") },
            text = {
                Text("Do you want to reset your saved answers for $selectedSubjectFilter or clear all subjects in the 12000+ bank?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedSubjectFilter == "All Subjects") {
                            selectedAnswers.clear()
                            allQuestions.forEach { q -> prefs.edit().remove("ans_${q.id}").apply() }
                            subjectsList.forEach { s -> prefs.edit().putInt("subj_index_$s", 0).apply() }
                        } else {
                            val subjQs = allQuestions.filter { it.subject.equals(selectedSubjectFilter, ignoreCase = true) }
                            subjQs.forEach { q ->
                                selectedAnswers.remove(q.id)
                                prefs.edit().remove("ans_${q.id}").apply()
                            }
                            prefs.edit().putInt("subj_index_$selectedSubjectFilter", 0).apply()
                        }
                        currentQIndex = 0
                        showResetConfirmDialog = false
                        Toast.makeText(context, "Progress reset for $selectedSubjectFilter", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Reset Current Filter")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}



/**
 * 3. EXAM INFORMATION, REGISTRATION & DATAFLOW GUIDES
 */
@Composable
fun NursingExamGuidesView() {
    val isDark = isSystemInDarkTheme()
    val brandTeal = getBrandTeal()

    val examInfos = listOf(
        Pair("DHA Dubai (UAE)", "150 MCQs in 3 hours. Passing score is 60%. Requirements: Nursing degree/diploma, PNC/NMC license, 2 years post-registration hospital experience. Primary Source Verification via DataFlow."),
        Pair("Saudi Prometric (SCFHS)", "150 MCQs in 3 hours. Passing score is 50-60%. Nursing license, 2 years acute care hospital experience, valid CGS. Prometric test centers available worldwide."),
        Pair("HAAD / DoH Abu Dhabi", "150 MCQs in 3 hours. Passing score is 60%. Full credentialing via DataFlow. Work experience in hospital setting mandatory."),
        Pair("MOH UAE / Gulf", "100-150 questions covering Fundamentals, Med-Surg, Maternity, Pediatrics, Infection Control. Exam valid for Ministry of Health hospitals."),
        Pair("NCLEX-RN (USA / International)", "Computerized Adaptive Testing (CAT) with 85 to 150 questions. Evaluates safe, effective care environment, health promotion, psychosocial integrity, and physiological integrity."),
        Pair("PNC (Pakistan Nursing Council)", "National licensure exam for BScN and Post-RN nursing graduates in Pakistan. Covers core clinical subjects according to HEC/PNC curriculum.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) Color(0xFF1B2826) else brandTeal.copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = brandTeal, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Official Exam Information & DataFlow Guide", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = brandTeal)
                        Text("Official blueprints, eligibility criteria, and credentialing rules.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        items(examInfos.size) { idx ->
            val (title, info) = examInfos[idx]
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AssignmentTurnedIn, contentDescription = null, tint = brandTeal, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = brandTeal)
                    }
                    Text(text = info, fontSize = 12.sp, lineHeight = 18.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

/**
 * 4. DOSAGE & ARTERIAL BLOOD GAS (ABG) POCKET CALCULATORS
 */
@Composable
fun NursingClinicalCalculatorsView() {
    val isDark = isSystemInDarkTheme()
    val brandTeal = getBrandTeal()

    var volumeMl by remember { mutableStateOf("1000") }
    var timeHours by remember { mutableStateOf("8") }
    var dropFactor by remember { mutableStateOf("15") }

    val vol = volumeMl.toDoubleOrNull() ?: 0.0
    val hrs = timeHours.toDoubleOrNull() ?: 0.0
    val df = dropFactor.toDoubleOrNull() ?: 0.0

    val totalMins = hrs * 60.0
    val gttMinResult = if (totalMins > 0) (vol * df) / totalMins else 0.0
    val mlHrResult = if (hrs > 0) vol / hrs else 0.0

    // ABG Solver State
    var phText by remember { mutableStateOf("7.30") }
    var paco2Text by remember { mutableStateOf("50") }
    var hco3Text by remember { mutableStateOf("24") }

    val ph = phText.toDoubleOrNull() ?: 7.40
    val paco2 = paco2Text.toDoubleOrNull() ?: 40.0
    val hco3 = hco3Text.toDoubleOrNull() ?: 24.0

    val abgResult = remember(ph, paco2, hco3) {
        val statePh = when {
            ph < 7.35 -> "ACIDOSIS"
            ph > 7.45 -> "ALKALOSIS"
            else -> "NORMAL pH"
        }

        val primaryEtio = when {
            ph < 7.35 && paco2 > 45 -> "Respiratory Acidosis (High CO2)"
            ph < 7.35 && hco3 < 22 -> "Metabolic Acidosis (Low HCO3)"
            ph > 7.45 && paco2 < 35 -> "Respiratory Alkalosis (Low CO2)"
            ph > 7.45 && hco3 > 26 -> "Metabolic Alkalosis (High HCO3)"
            else -> "Normal ABG or Compensated State"
        }

        "$statePh • $primaryEtio"
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // IV Drop Rate Calculator Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, brandTeal.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.WaterDrop, contentDescription = null, tint = brandTeal)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("IV Flow & Drop Rate Solver (gtt/min)", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = brandTeal)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = volumeMl,
                            onValueChange = { volumeMl = it },
                            label = { Text("Volume (mL)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = timeHours,
                            onValueChange = { timeHours = it },
                            label = { Text("Time (Hours)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = dropFactor,
                            onValueChange = { dropFactor = it },
                            label = { Text("Factor (gtt/mL)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isDark) Color(0xFF1B2826) else brandTeal.copy(alpha = 0.12f)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Text("Calculated Flow Rate:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                            Text(
                                text = "${gttMinResult.toInt()} gtt/min  (${mlHrResult.toInt()} mL/hr)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = brandTeal
                            )
                        }
                    }
                }
            }
        }

        // ABG Analyzer Card
        item {
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Science, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Arterial Blood Gas (ABG) Interpreter", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = phText,
                            onValueChange = { phText = it },
                            label = { Text("pH (7.35-7.45)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = paco2Text,
                            onValueChange = { paco2Text = it },
                            label = { Text("PaCO2 (35-45)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = hco3Text,
                            onValueChange = { hco3Text = it },
                            label = { Text("HCO3 (22-26)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Text("ABG Interpretation:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(
                                text = abgResult,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 5. KHYBER PAKHTUNKHWA (KP) BSN 4-YEAR CURRICULUM & SEMESTER-WISE QUIZZES
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KpNursingSemesterCurriculumView() {
    val semesters = remember { KpNursingCurriculumRepository.getSemesters() }
    val allQuestions = remember { KpNursingCurriculumRepository.getSemesterQuestions() }
    val selectedAnswers = remember { mutableStateMapOf<Int, Int>() }
    val isDark = isSystemInDarkTheme()
    val brandTeal = getBrandTeal()

    var selectedSemesterNumber by remember { mutableStateOf(1) }
    var selectedSubjectFilter by remember { mutableStateOf("All Subjects") }
    var activeSubTab by remember { mutableStateOf(0) } // 0: Curriculum Subjects, 1: Semester Quiz Bank, 2: Research

    val activeSemester = semesters.firstOrNull { it.semesterNumber == selectedSemesterNumber } ?: semesters[0]

    // Questions filtered for active semester & subject
    val semesterQuestions = remember(selectedSemesterNumber, selectedSubjectFilter, allQuestions) {
        allQuestions.filter { q ->
            val matchSem = q.semesterNumber == selectedSemesterNumber
            val matchSub = (selectedSubjectFilter == "All Subjects") || q.subjectName.equals(selectedSubjectFilter, ignoreCase = true)
            matchSem && matchSub
        }
    }

    var currentQIndex by remember { mutableStateOf(0) }

    LaunchedEffect(selectedSemesterNumber, selectedSubjectFilter) {
        currentQIndex = 0
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header Banner Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = getHeaderBannerColor()),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.School, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "KP Nursing BSN Curriculum",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "KMU / PNC KP",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(
                        text = "Official 4-Year Generic BS Nursing (BSN) Semester-wise subjects, credit hours & exam quizzes for Khyber Pakhtunkhwa.",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Semester Selector Horizontal Scroll Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(semesters.size) { idx ->
                    val sem = semesters[idx]
                    val isSelected = sem.semesterNumber == selectedSemesterNumber
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedSemesterNumber = sem.semesterNumber
                            selectedSubjectFilter = "All Subjects"
                        },
                        label = {
                            Text(
                                text = "Semester ${sem.semesterNumber}",
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        leadingIcon = {
                            if (isSelected) Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = brandTeal,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Active Semester Overview Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) Color(0xFF1B2826) else Color(0xFFE0F2F1).copy(alpha = 0.6f)
                ),
                border = BorderStroke(1.dp, brandTeal.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = activeSemester.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = brandTeal
                        )
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = brandTeal
                        ) {
                            Text(
                                text = "${activeSemester.totalCreditHours} Credit Hours",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(
                        text = activeSemester.description,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Divider(modifier = Modifier.padding(vertical = 4.dp), color = brandTeal.copy(alpha = 0.2f))

                    // Sub-tab switcher: 0 = Curriculum Outline, 1 = Semester Quiz Bank, 2 = Research Topics Examples
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = { activeSubTab = 0 },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (activeSubTab == 0) brandTeal else MaterialTheme.colorScheme.surface,
                                contentColor = if (activeSubTab == 0) Color.White else brandTeal
                            ),
                            border = BorderStroke(1.dp, brandTeal)
                        ) {
                            Icon(Icons.Default.ListAlt, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("Subjects", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { activeSubTab = 1 },
                            modifier = Modifier.weight(1.2f),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (activeSubTab == 1) brandTeal else MaterialTheme.colorScheme.surface,
                                contentColor = if (activeSubTab == 1) Color.White else brandTeal
                            ),
                            border = BorderStroke(1.dp, brandTeal)
                        ) {
                            Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("Quiz (${semesterQuestions.size})", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        val researchTabLabel = when (selectedSemesterNumber) {
                            6 -> "Synopsis (RES-664)"
                            7 -> "Thesis (RES-674)"
                            8 -> "Research Status"
                            else -> "Research Guide"
                        }

                        Button(
                            onClick = { activeSubTab = 2 },
                            modifier = Modifier.weight(1.3f),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (activeSubTab == 2) brandTeal else MaterialTheme.colorScheme.surface,
                                contentColor = if (activeSubTab == 2) Color.White else brandTeal
                            ),
                            border = BorderStroke(1.dp, brandTeal)
                        ) {
                            Icon(Icons.Default.Biotech, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(researchTabLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // CONTENT DISPLAY: TAB 0 (SUBJECTS) OR TAB 1 (QUIZ BANK) OR TAB 2 (RESEARCH)
        if (activeSubTab == 0) {
            item {
                Text(
                    text = "Core Subjects in Semester ${activeSemester.semesterNumber}:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = brandTeal
                )
            }

            items(activeSemester.subjects.size) { index ->
                val subj = activeSemester.subjects[index]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = brandTeal.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = subj.code,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = brandTeal,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = subj.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Text(
                                text = subj.creditHours,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = brandTeal
                            )
                        }

                        Text(
                            text = subj.description,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Key Topic Chips
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(subj.keyTopics.size) { tIdx ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = "• ${subj.keyTopics[tIdx]}",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else if (activeSubTab == 1) {
            // SEMESTER QUIZ BANK VIEW
            item {
                // Filter by Subject Chip Row
                val subjectFilterList = remember(activeSemester) {
                    listOf("All Subjects") + activeSemester.subjects.map { it.name }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(subjectFilterList.size) { sIdx ->
                        val subName = subjectFilterList[sIdx]
                        val isSubSel = selectedSubjectFilter == subName
                        FilterChip(
                            selected = isSubSel,
                            onClick = { selectedSubjectFilter = subName },
                            label = { Text(subName, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = brandTeal,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            if (semesterQuestions.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Text(
                            text = "No questions found for the selected subject in Semester $selectedSemesterNumber.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                val safeIndex = currentQIndex.coerceIn(0, semesterQuestions.size - 1)
                val q = semesterQuestions[safeIndex]
                val userSelected = selectedAnswers[q.id]
                val hasAnswered = userSelected != null

                item {
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
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = brandTeal.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "Semester $selectedSemesterNumber • ${q.subjectName}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = brandTeal,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }

                                Text(
                                    text = "Q ${safeIndex + 1} of ${semesterQuestions.size}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Text(
                                text = q.question,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 21.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                // Options with Instant Highlighting
                items(q.options.size) { optIdx ->
                    val isSelected = userSelected == optIdx
                    val isCorrect = optIdx == q.correctIndex

                    val containerColor = when {
                        hasAnswered && isCorrect -> getCorrectContainerColor()
                        hasAnswered && isSelected && !isCorrect -> getWrongContainerColor()
                        isSelected -> brandTeal.copy(alpha = 0.15f)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val borderColor = when {
                        hasAnswered && isCorrect -> getCorrectBorderColor()
                        hasAnswered && isSelected && !isCorrect -> getWrongBorderColor()
                        isSelected -> brandTeal
                        else -> MaterialTheme.colorScheme.outlineVariant
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedAnswers[q.id] = optIdx
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = containerColor),
                        border = BorderStroke(if (isSelected || (hasAnswered && isCorrect)) 2.dp else 1.dp, borderColor)
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
                                        .size(26.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                hasAnswered && isCorrect -> getCorrectBorderColor()
                                                hasAnswered && isSelected -> getWrongBorderColor()
                                                isSelected -> brandTeal
                                                else -> MaterialTheme.colorScheme.surfaceVariant
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = ('A'.code + optIdx).toChar().toString(),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected || (hasAnswered && isCorrect)) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = q.options[optIdx],
                                    fontSize = 13.sp,
                                    fontWeight = if (hasAnswered && isCorrect) FontWeight.Bold else FontWeight.Normal,
                                    color = when {
                                        hasAnswered && isCorrect -> getCorrectTextColor()
                                        hasAnswered && isSelected && !isCorrect -> getWrongTextColor()
                                        else -> MaterialTheme.colorScheme.onSurface
                                    }
                                )
                            }

                            if (hasAnswered) {
                                if (isCorrect) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = getCorrectBorderColor(), modifier = Modifier.size(20.dp))
                                } else if (isSelected) {
                                    Icon(Icons.Default.Cancel, contentDescription = "Wrong", tint = getWrongBorderColor(), modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }

                // Explanation Box
                if (hasAnswered) {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDark) Color(0xFF1B2826) else Color(0xFFE0F2F1).copy(alpha = 0.5f)
                            ),
                            border = BorderStroke(1.dp, brandTeal.copy(alpha = 0.3f))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Explanation & Rationale:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                                }
                                Text(text = q.explanation, fontSize = 12.sp, lineHeight = 18.sp, color = MaterialTheme.colorScheme.onSurface)
                                Divider(modifier = Modifier.padding(vertical = 2.dp), color = brandTeal.copy(alpha = 0.2f))
                                Text(text = "Reference: ${q.reference}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                            }
                        }
                    }
                }

                // Navigation Prev / Next
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { if (currentQIndex > 0) currentQIndex-- },
                            enabled = currentQIndex > 0,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous", fontSize = 12.sp)
                        }

                        Button(
                            onClick = { if (currentQIndex + 1 < semesterQuestions.size) currentQIndex++ },
                            enabled = currentQIndex + 1 < semesterQuestions.size,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = brandTeal, contentColor = Color.White)
                        ) {
                            Text("Next Q", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        } else {
            // REGIONAL KP NURSING RESEARCH EXAMPLES & THESIS PROPOSALS VIEW
            item {
                val researchTopics = remember { KpResearchExamplesRepository.getResearchExamples() }
                var searchQuery by remember { mutableStateOf("") }
                var selectedRegionFilter by remember { mutableStateOf("All KP Regions") }
                val regionsList = remember {
                    listOf("All KP Regions", "Buner", "Swat", "Dir", "Mardan", "Malakand", "Peshawar", "Hazara", "Kohat", "Charsadda", "Swabi", "Nowshera", "Bannu / DI Khan")
                }

                val filteredTopics = remember(selectedRegionFilter, searchQuery) {
                    researchTopics.filter { topic ->
                        val matchesRegion = selectedRegionFilter == "All KP Regions" || 
                                topic.regionTag.contains(selectedRegionFilter, ignoreCase = true) || 
                                topic.location.contains(selectedRegionFilter, ignoreCase = true)
                        
                        val matchesQuery = searchQuery.isBlank() ||
                                topic.title.contains(searchQuery, ignoreCase = true) ||
                                topic.location.contains(searchQuery, ignoreCase = true) ||
                                topic.category.contains(searchQuery, ignoreCase = true) ||
                                topic.statisticalAnalysisTool.contains(searchQuery, ignoreCase = true)
                        
                        matchesRegion && matchesQuery
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // DYNAMIC CURRICULUM CRITERIA BANNER BASED ON SELECTED SEMESTER
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDark) Color(0xFF00332C) else Color(0xFF004D40)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFFFB300)
                                ) {
                                    Text(
                                        text = when (selectedSemesterNumber) {
                                            7 -> "OFFICIAL FINAL THESIS SEMESTER"
                                            6 -> "RESEARCH PROPOSAL SEMESTER"
                                            8 -> "CLINICAL INTERNSHIP PRACTICUM"
                                            else -> "BSN RESEARCH ROADMAP"
                                        },
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.White.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "PNC / KMU Standards",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Text(
                                text = when (selectedSemesterNumber) {
                                    7 -> "Semester 7: Nursing Research Project & Biostatistics (RES-674, 3 CH)"
                                    6 -> "Semester 6: Introduction to Nursing Research (RES-664, 4 CH)"
                                    8 -> "Semester 8: Senior Internship Practicum (Research Defended in Sem 7)"
                                    else -> "BSN 4-Year Research & Thesis Progression Guidelines"
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Text(
                                text = when (selectedSemesterNumber) {
                                    7 -> "In accordance with Pakistan Nursing Council (PNC) and Khyber Medical University (KMU) degree criteria, Semester 7 (RES-674) is the MANDATORY semester for executing your hospital research study, performing SPSS biostatistical analysis, writing the 5-chapter thesis, and undergoing oral viva defense."
                                    6 -> "In Semester 6 (RES-664), students learn quantitative & qualitative methodology and prepare their Research Proposal / Synopsis for IRB ethics approval prior to Semester 7 field data collection. Explore the 12 model proposals below!"
                                    8 -> "Curriculum Notice: The official BSN Research Thesis (RES-674) is completed and defended in Semester 7. Semester 8 focuses on Senior Internship (CHN-681), Healthcare Economics (ECO-683), Contemporary Seminars, and Disaster Management. Below is the thesis archive for reference and publication."
                                    else -> "PNC Progression Roadmap: ENG-654 Technical Writing (Sem 5) ➔ RES-664 Research Proposal (Sem 6) ➔ RES-674 Final Thesis Execution & Viva Defense (Sem 7) ➔ Senior Internship Practicum (Sem 8)."
                                },
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.92f),
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // SEARCH BOX
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search thesis title, hospital, SPSS tool, or keyword...", fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = brandTeal) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = brandTeal)
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = brandTeal,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        )
                    )

                    // Region Filter Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Filter by KP District / Hospital Network:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                        Text("${filteredTopics.size} Topics", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(regionsList.size) { rIdx ->
                            val rName = regionsList[rIdx]
                            val isRegSel = selectedRegionFilter == rName
                            FilterChip(
                                selected = isRegSel,
                                onClick = { selectedRegionFilter = rName },
                                label = { Text(rName, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = brandTeal,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    if (filteredTopics.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Text(
                                text = "No research topics match your search criteria. Try clearing the search filter or choosing another KP district.",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Render Topic Cards
                    filteredTopics.forEach { topic ->
                        var isExpanded by remember { mutableStateOf(false) }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isExpanded = !isExpanded },
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = brandTeal
                                        ) {
                                            Text(
                                                text = topic.regionTag,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                            )
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = brandTeal.copy(alpha = 0.15f)
                                        ) {
                                            Text(
                                                text = topic.semesterRequirement,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = brandTeal,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                            )
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color(0xFFFF6F00).copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = topic.category,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isDark) Color(0xFFFFB74D) else Color(0xFFD84315),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = topic.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 18.sp
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = brandTeal, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = topic.location, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${topic.studyDesign} • ${topic.sampleSize}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = brandTeal
                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = if (isExpanded) "Hide Thesis" else "View Thesis Structure",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = brandTeal
                                        )
                                        Icon(
                                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = null,
                                            tint = brandTeal,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                if (isExpanded) {
                                    Divider(modifier = Modifier.padding(vertical = 4.dp), color = brandTeal.copy(alpha = 0.2f))

                                    Text("Target Population:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                                    Text(topic.targetPopulation, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)

                                    Text("Key Research Objectives:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                                    topic.keyObjectives.forEach { obj ->
                                        Text("• $obj", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 6.dp))
                                    }

                                    Text("Methodology & Data Collection Tool:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                                    Text("${topic.methodology}\nTool: ${topic.dataCollectionTool}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)

                                    Text("Biostatistical Analysis Software & Tests:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                                    Text(topic.statisticalAnalysisTool, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)

                                    Text("5-Chapter Thesis Outline (PNC Standard):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                                    topic.chapterOutline.forEach { ch ->
                                        Text("• $ch", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 6.dp))
                                    }

                                    Text("Expected Findings & Outcomes:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                                    topic.expectedFindings.forEach { find ->
                                        Text("• $find", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 6.dp))
                                    }

                                    Text("Clinical Recommendations for District Hospital:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = brandTeal)
                                    Text(topic.clinicalRecommendations, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isDark) Color(0xFF1B2826) else Color(0xFFE0F2F1),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Academic Supervisor Viva Note: ${topic.supervisorNote}",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isDark) Color(0xFF80CBC4) else Color(0xFF004D40),
                                            modifier = Modifier.padding(8.dp)
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
}
