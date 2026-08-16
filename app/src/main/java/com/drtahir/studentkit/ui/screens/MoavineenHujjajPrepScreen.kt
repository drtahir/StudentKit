package com.drtahir.studentkit.ui.screens

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drtahir.studentkit.viewmodel.StudentKitViewModel

/**
 * MOAVINEEN-E-HUJJAJ PREP SCREEN
 * - High-Contrast & Low Light / Night Reading Mode for perfect visibility
 * - SharedPreferences Auto-Save / Resume from exact last left-off question position
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoavineenHujjajPrepScreen(
    viewModel: StudentKitViewModel
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("moavineen_hujjaj_prefs", Context.MODE_PRIVATE) }

    var activeTab by remember { mutableIntStateOf(0) } // 0: Quiz Simulator, 1: Curriculum & Info, 2: Field Guide & Flashcards, 3: Spoken Arabic, 4: My Stats
    var isNightMode by remember { mutableStateOf(prefs.getBoolean("is_night_mode", false)) }

    // Color Palette based on High-Contrast Low-Light Mode vs Light Mode
    val bgColor = if (isNightMode) Color(0xFF0B0F19) else Color(0xFFF3F4F6)
    val cardBgColor = if (isNightMode) Color(0xFF161F30) else Color.White
    val cardBorderColor = if (isNightMode) Color(0xFF2D3B55) else Color(0xFFE5E7EB)
    val primaryTextColor = if (isNightMode) Color(0xFFF9FAFB) else Color(0xFF0F172A) // Pure ultra-contrast text
    val secondaryTextColor = if (isNightMode) Color(0xFFD1D5DB) else Color(0xFF374151)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .testTag("moavineen_hujjaj_screen")
    ) {
        // Hero Header Banner - Deep Emerald & Gold Accents with Low Light Mode Toggle
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            if (isNightMode) Color(0xFF052C1C) else Color(0xFF0D5C3A),
                            if (isNightMode) Color(0xFF021910) else Color(0xFF073822)
                        )
                    )
                )
                .padding(horizontal = 18.dp, vertical = 20.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFDAA520).copy(alpha = 0.25f),
                            border = BorderStroke(1.5.dp, Color(0xFFDAA520)),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Mosque,
                                    contentDescription = "Moavineen Hujjaj",
                                    tint = Color(0xFFDAA520),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Moavineen-e-Hujjaj Prep",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Ministry of Religious Affairs • NTS Portal",
                                fontSize = 11.sp,
                                color = Color(0xFFFEF08A)
                            )
                        }
                    }

                    // Low-Light Mode Switch & 1040 MCQ Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isNightMode) Color(0xFF374151) else Color.White.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, Color(0xFFDAA520)),
                            modifier = Modifier.clickable {
                                isNightMode = !isNightMode
                                prefs.edit().putBoolean("is_night_mode", isNightMode).apply()
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Icon(
                                    imageVector = if (isNightMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = "Toggle Night Mode",
                                    tint = Color(0xFFFEF08A),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isNightMode) "Night" else "Light",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFDAA520),
                            shadowElevation = 2.dp
                        ) {
                            Text(
                                text = "1,040 MCQs",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF073822),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Scrollable Sub-tabs
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val tabs = listOf(
                        Triple("1040+ Quizzes", Icons.Default.Quiz, 0),
                        Triple("Full Curriculum", Icons.AutoMirrored.Filled.MenuBook, 1),
                        Triple("Field Flashcards", Icons.Default.Style, 2),
                        Triple("Spoken Arabic", Icons.AutoMirrored.Filled.VolumeUp, 3),
                        Triple("My Stats", Icons.Default.Assessment, 4)
                    )
                    items(tabs) { (title, icon, index) ->
                        val isSelected = activeTab == index
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) Color(0xFFDAA520) else Color.White.copy(alpha = 0.15f),
                            contentColor = if (isSelected) Color(0xFF073822) else Color.White,
                            modifier = Modifier.clickable { activeTab = index }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Tab Screen Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (activeTab) {
                0 -> MoavineenQuizSimulatorView(
                    isNightMode = isNightMode,
                    cardBgColor = cardBgColor,
                    cardBorderColor = cardBorderColor,
                    primaryTextColor = primaryTextColor,
                    secondaryTextColor = secondaryTextColor
                )
                1 -> MoavineenCurriculumView(
                    isNightMode = isNightMode,
                    cardBgColor = cardBgColor,
                    primaryTextColor = primaryTextColor,
                    secondaryTextColor = secondaryTextColor
                )
                2 -> MoavineenFieldFlashcardsView(
                    isNightMode = isNightMode,
                    cardBgColor = cardBgColor,
                    primaryTextColor = primaryTextColor,
                    secondaryTextColor = secondaryTextColor
                )
                3 -> MoavineenSpokenArabicView(
                    isNightMode = isNightMode,
                    cardBgColor = cardBgColor,
                    primaryTextColor = primaryTextColor,
                    secondaryTextColor = secondaryTextColor
                )
                4 -> MoavineenStatsView(
                    isNightMode = isNightMode,
                    cardBgColor = cardBgColor,
                    primaryTextColor = primaryTextColor,
                    secondaryTextColor = secondaryTextColor,
                    onNavigateToQuiz = { activeTab = 0 }
                )
            }
        }
    }
}

/**
 * 🎯 TAB 0: INTERACTIVE NTS QUIZ SIMULATOR WITH AUTO-RESUME & HIGH CONTRAST
 */
@Composable
fun MoavineenQuizSimulatorView(
    isNightMode: Boolean,
    cardBgColor: Color,
    cardBorderColor: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("moavineen_hujjaj_prefs", Context.MODE_PRIVATE) }

    // Restore saved session or set defaults
    var selectedPosition by remember { mutableStateOf(prefs.getString("saved_position", "All") ?: "All") }
    var selectedCategory by remember { mutableStateOf(prefs.getString("saved_category", "All") ?: "All") }

    val categories = listOf(
        "All",
        "Hajj Rules & Arkan",
        "Moavineen Operational SOPs",
        "Geography & Holy Sites",
        "Functional Arabic",
        "Management & Ethics",
        "Hajj Policy & Tech"
    )

    val questionPool = remember(selectedPosition, selectedCategory) {
        Moavineen1000QuestionBank.getQuestionsByPositionAndCategory(selectedPosition, selectedCategory)
    }

    var currentIndex by remember { mutableIntStateOf(prefs.getInt("saved_index", 0).coerceIn(0, (questionPool.size - 1).coerceAtLeast(0))) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var showExplanation by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(prefs.getInt("saved_score", 0)) }
    var attemptedCount by remember { mutableIntStateOf(prefs.getInt("saved_attempted", 0)) }
    var wasResumed by remember { mutableStateOf(prefs.getInt("saved_index", 0) > 0) }

    // Auto-Save progress whenever key states change
    LaunchedEffect(currentIndex, selectedPosition, selectedCategory, score, attemptedCount) {
        prefs.edit()
            .putString("saved_position", selectedPosition)
            .putString("saved_category", selectedCategory)
            .putInt("saved_index", currentIndex)
            .putInt("saved_score", score)
            .putInt("saved_attempted", attemptedCount)
            .apply()
    }

    val currentQuestion = questionPool.getOrNull(currentIndex)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Auto-Resume Status Banner
        if (wasResumed && currentIndex > 0) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isNightMode) Color(0xFF1E3A8A).copy(alpha = 0.5f) else Color(0xFFEFF6FF),
                    border = BorderStroke(1.dp, Color(0xFF3B82F6)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Resumed",
                                tint = Color(0xFF60A5FA),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Resumed where you left off (Question ${currentIndex + 1})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isNightMode) Color(0xFF93C5FD) else Color(0xFF1D4ED8)
                            )
                        }

                        TextButton(
                            onClick = {
                                currentIndex = 0
                                score = 0
                                attemptedCount = 0
                                selectedAnswer = null
                                showExplanation = false
                                wasResumed = false
                                prefs.edit().putInt("saved_index", 0).putInt("saved_score", 0).putInt("saved_attempted", 0).apply()
                            },
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                        ) {
                            Text("↺ Restart", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))
                        }
                    }
                }
            }
        }

        // Filter Controls Card (Position Cadre & Category)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                border = BorderStroke(1.dp, cardBorderColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "1. Target Cadre Filter:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val positions = listOf("All" to "All Cadres", "Supervisor" to "Supervisor", "Supporting Staff" to "Support Staff")
                        positions.forEach { (posKey, posLabel) ->
                            val isSel = selectedPosition == posKey
                            FilterChip(
                                selected = isSel,
                                onClick = {
                                    selectedPosition = posKey
                                    currentIndex = 0
                                    selectedAnswer = null
                                    showExplanation = false
                                },
                                label = { Text(posLabel, fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = if (isNightMode) Color(0xFF059669) else Color(0xFF0D5C3A),
                                    selectedLabelColor = Color.White,
                                    containerColor = if (isNightMode) Color(0xFF1E293B) else Color(0xFFF3F4F6),
                                    labelColor = primaryTextColor
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "2. Subject Category Filter:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isNightMode) Color(0xFFFBBF24) else Color(0xFF8B6508)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(categories) { cat ->
                            val isSel = selectedCategory == cat
                            FilterChip(
                                selected = isSel,
                                onClick = {
                                    selectedCategory = cat
                                    currentIndex = 0
                                    selectedAnswer = null
                                    showExplanation = false
                                },
                                label = { Text(cat, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFDAA520),
                                    selectedLabelColor = Color(0xFF073822),
                                    containerColor = if (isNightMode) Color(0xFF1E293B) else Color(0xFFF3F4F6),
                                    labelColor = primaryTextColor
                                )
                            )
                        }
                    }
                }
            }
        }

        // Live Question Display Card
        item {
            if (currentQuestion != null) {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBgColor),
                    border = BorderStroke(1.dp, cardBorderColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        // Header Bar: Question Number & Tags
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Question ${currentIndex + 1} of ${questionPool.size}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A)
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isNightMode) Color(0xFF065F46) else Color(0xFF0D5C3A).copy(alpha = 0.1f)
                                ) {
                                    Text(
                                        text = currentQuestion.positionTarget,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isNightMode) Color(0xFFA7F3D0) else Color(0xFF0D5C3A),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isNightMode) Color(0xFF78350F) else Color(0xFFDAA520).copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = currentQuestion.subjectCategory,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isNightMode) Color(0xFFFDE68A) else Color(0xFF8B6508),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // High-Contrast Question Prompt
                        Text(
                            text = currentQuestion.question,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryTextColor,
                            lineHeight = 23.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Four Option Choices (High-Contrast for Low Light)
                        currentQuestion.options.forEachIndexed { optIdx, optionText ->
                            val isCorrect = optIdx == currentQuestion.correctIndex
                            val isSelected = selectedAnswer == optIdx

                            val optionBg = when {
                                showExplanation && isCorrect -> if (isNightMode) Color(0xFF064E3B) else Color(0xFFDCFCE7)
                                showExplanation && isSelected && !isCorrect -> if (isNightMode) Color(0xFF7F1D1D) else Color(0xFFFEE2E2)
                                isSelected -> if (isNightMode) Color(0xFF1E3A8A) else Color(0xFFE0F2FE)
                                else -> if (isNightMode) Color(0xFF1E293B) else Color(0xFFF8FAFC)
                            }

                            val optionBorder = when {
                                showExplanation && isCorrect -> if (isNightMode) Color(0xFF34D399) else Color(0xFF16A34A)
                                showExplanation && isSelected && !isCorrect -> if (isNightMode) Color(0xFFF87171) else Color(0xFFDC2626)
                                isSelected -> if (isNightMode) Color(0xFF60A5FA) else Color(0xFF0284C7)
                                else -> if (isNightMode) Color(0xFF374151) else Color(0xFFCBD5E1)
                            }

                            val optionTextColor = when {
                                showExplanation && isCorrect -> if (isNightMode) Color(0xFFECFDF5) else Color(0xFF065F46)
                                showExplanation && isSelected && !isCorrect -> if (isNightMode) Color(0xFFFEF2F2) else Color(0xFF991B1B)
                                isSelected -> if (isNightMode) Color.White else Color(0xFF0369A1)
                                else -> primaryTextColor
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = optionBg,
                                border = BorderStroke(1.5.dp, optionBorder),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable(enabled = !showExplanation) {
                                        selectedAnswer = optIdx
                                        showExplanation = true
                                        attemptedCount++
                                        if (isCorrect) score++
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(14.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = if (isSelected || (showExplanation && isCorrect)) optionBorder else Color.Transparent,
                                        border = BorderStroke(1.5.dp, optionBorder),
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = ('A' + optIdx).toString(),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = if (isSelected || (showExplanation && isCorrect)) Color.White else secondaryTextColor
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = optionText,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected || (showExplanation && isCorrect)) FontWeight.Bold else FontWeight.Medium,
                                        color = optionTextColor,
                                        modifier = Modifier.weight(1f),
                                        lineHeight = 18.sp
                                    )

                                    if (showExplanation && isCorrect) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Correct",
                                            tint = if (isNightMode) Color(0xFF34D399) else Color(0xFF16A34A),
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Detailed Answer Explanation & Rationale
                        AnimatedVisibility(visible = showExplanation) {
                            Column(modifier = Modifier.padding(top = 14.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            if (isNightMode) Color(0xFF064E3B).copy(alpha = 0.6f) else Color(0xFFF0FDF4),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .border(
                                            1.dp,
                                            if (isNightMode) Color(0xFF059669) else Color(0xFF86EFAC),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .padding(14.dp)
                                ) {
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.Help,
                                                contentDescription = "Explanation",
                                                tint = if (isNightMode) Color(0xFF34D399) else Color(0xFF15803D),
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "NTS Official Rationale & Guidelines:",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isNightMode) Color(0xFF34D399) else Color(0xFF15803D)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = currentQuestion.explanation,
                                            fontSize = 12.sp,
                                            color = if (isNightMode) Color(0xFFECFDF5) else Color(0xFF166534),
                                            lineHeight = 18.sp
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Reference: ${currentQuestion.reference}",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isNightMode) Color(0xFF6EE7B7) else Color(0xFF047857)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Previous / Next Navigation Controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    if (currentIndex > 0) {
                                        currentIndex--
                                        selectedAnswer = null
                                        showExplanation = false
                                    }
                                },
                                enabled = currentIndex > 0,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isNightMode) Color(0xFF374151) else Color(0xFF6B7280)
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Previous", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    if (currentIndex < questionPool.size - 1) {
                                        currentIndex++
                                        selectedAnswer = null
                                        showExplanation = false
                                    }
                                },
                                enabled = currentIndex < questionPool.size - 1,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isNightMode) Color(0xFF059669) else Color(0xFF0D5C3A)
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Next Question ▶", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBgColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.padding(30.dp), contentAlignment = Alignment.Center) {
                        Text("No questions found for the selected filter combination.", color = secondaryTextColor)
                    }
                }
            }
        }
    }
}

/**
 * 📘 TAB 1: FULL CURRICULUM & POSITIONS INFORMATION
 */
@Composable
fun MoavineenCurriculumView(
    isNightMode: Boolean,
    cardBgColor: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Supervisor Cadre Details
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SupervisorAccount,
                            contentDescription = "Supervisor",
                            tint = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "1. Supervisor Position (BPS-16 / BPS-17+)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A)
                            )
                            Text(
                                text = "Team Leadership, Sector Operations & Dispatch Management",
                                fontSize = 11.sp,
                                color = secondaryTextColor
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Text(
                        text = "📋 Roles & Key Responsibilities:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    val roles = listOf(
                        "Supervise Moavineen supporting staff field rotations across Makkah sectors & Mina camps.",
                        "Coordinate directly with Saudi Tawafa Establishments (Mutawwif) for tent allotments.",
                        "Manage Lost & Found (Tayeena) deposits, currency reconciliation, and reporting.",
                        "Direct emergency dispatch for medical referrals to Saudi Hospitals & Pakistani Medical Mission.",
                        "Log daily shift reports to Director Hajj Makkah/Madinah."
                    )
                    roles.forEach { r ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Text("• ", fontWeight = FontWeight.Bold, color = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A))
                            Text(r, fontSize = 12.sp, color = primaryTextColor)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "📚 NTS Written Test Syllabus & Weightage:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val syllabus = listOf(
                        "Moavineen Operational SOPs & Sector Management" to "25%",
                        "Hajj Rules, Rituals & Arkan (Masail-e-Hajj)" to "20%",
                        "Geography & Holy Sites (Makkah, Mina, Madinah)" to "15%",
                        "Functional Arabic & Saudi Communication" to "15%",
                        "Management, Leadership & Situational Judgment" to "15%",
                        "General Knowledge, Pakistan Hajj Policy & Tech Portal" to "10%"
                    )

                    syllabus.forEach { (sub, pct) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(sub, fontSize = 12.sp, color = primaryTextColor)
                            Text(pct, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A))
                        }
                    }
                }
            }
        }

        // Supporting Staff Cadre Details
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DirectionsRun,
                            contentDescription = "Supporting Staff",
                            tint = Color(0xFFDAA520),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "2. Supporting Staff Position (BPS-05 to BPS-15)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isNightMode) Color(0xFFFBBF24) else Color(0xFF8B6508)
                            )
                            Text(
                                text = "Ground Assistance, Luggage Handling & Pilgrim Guidance",
                                fontSize = 11.sp,
                                color = secondaryTextColor
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Text(
                        text = "📋 Roles & Key Responsibilities:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    val rolesSupport = listOf(
                        "Receive arriving pilgrim flights at Jeddah Hajj Terminal and assist with green luggage.",
                        "Escort lost elderly pilgrims to Tayeena Centers and Maktab tents in Mina/Arafat.",
                        "Provide wheelchair push support for frail pilgrims visiting Masjid al-Haram.",
                        "Maintain queue discipline at Jamarat complex entry/exit corridors and Haram bus stations.",
                        "Assist medical teams with stretcher movement during heat emergencies."
                    )
                    rolesSupport.forEach { r ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Text("• ", fontWeight = FontWeight.Bold, color = Color(0xFFDAA520))
                            Text(r, fontSize = 12.sp, color = primaryTextColor)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "📚 NTS Written Test Syllabus & Weightage:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val syllabusSupport = listOf(
                        "Supporting Staff Field SOPs & Duty Rules" to "30%",
                        "Basic Hajj Rules & Ritual Awareness" to "25%",
                        "Geography & Directions in Makkah & Mina" to "20%",
                        "Basic Conversational Arabic Phrases" to "15%",
                        "Patience, Ethics & Pilgrim Service (Husn-e-Akhlaq)" to "10%"
                    )

                    syllabusSupport.forEach { (sub, pct) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(sub, fontSize = 12.sp, color = primaryTextColor)
                            Text(pct, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFDAA520))
                        }
                    }
                }
            }
        }
    }
}

/**
 * 🕋 TAB 2: FIELD GUIDE & FLASHCARDS
 */
@Composable
fun MoavineenFieldFlashcardsView(
    isNightMode: Boolean,
    cardBgColor: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color
) {
    val flashcards = listOf(
        Triple("Tayeena (Lost & Found) SOP", "Check pilgrim wristband -> Scan QR code -> Bring to nearest Tayeena tent -> Contact sector supervisor -> Notify Maktab guide.", "MORA Field SOP"),
        Triple("Jamarat Stoning Guidance", "10th Dhu al-Hijjah: Big Jamarah only (7 pebbles). 11th & 12th: Small -> Middle -> Big (21 pebbles daily). Always follow one-way ramps.", "Mina Management"),
        Triple("Heat Exhaustion Field First Aid", "Move pilgrim to shade -> Remove heavy Ihram layers -> Spray tepid water + high-speed fan -> Give ORS fluids -> Alert medical mission if disoriented.", "Emergency Protocol"),
        Triple("Green Luggage Tracking", "Every pilgrim bag has a MORA green label with barcode and flight serial. Lost bags deposit at Makkah Luggage Cell.", "Airport & Transport")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(flashcards) { (title, body, tag) ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A))
                        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFDAA520).copy(alpha = 0.25f)) {
                            Text(tag, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (isNightMode) Color(0xFFFDE68A) else Color(0xFF8B6508), modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(body, fontSize = 12.sp, color = primaryTextColor, lineHeight = 19.sp)
                }
            }
        }
    }
}

/**
 * 🇸🇦 TAB 3: SPOKEN ARABIC SURVIVAL KIT
 */
@Composable
fun MoavineenSpokenArabicView(
    isNightMode: Boolean,
    cardBgColor: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color
) {
    val phrases = listOf(
        Triple("Where is the bus station?", "Ayna mahattat al-hafilat?", "أين محطة الحافلات؟"),
        Triple("This pilgrim is sick and needs a doctor.", "Hadha al-hajj mareed wa yahtaj ila tabeeb.", "هذا الحاج مريض ويحتاج إلى طبيب."),
        Triple("Where is Maktab number 42?", "Ayna Maktab raqm ithnani wa arba'oon?", "أين مكتب رقم ٤٢؟"),
        Triple("Emergency! Please help us!", "Tawari! Arju al-musa'adah!", "طوارئ! أرجو المساعدة!"),
        Triple("Where is the lost luggage section?", "Ayna qism al-amti'ah al-mafqoodah?", "أين قسم الأمتعة المفقودة؟"),
        Triple("Go straight, then turn right.", "Imshi ala toole, thumma in'atif yameen.", "إمشِ على طول، ثم انعطف يمين.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(phrases) { (english, phonetic, arabic) ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(english, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(phonetic, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = primaryTextColor)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = arabic,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isNightMode) Color(0xFFFDE68A) else Color(0xFF073822),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * 📊 TAB 4: MY PERFORMANCE STATS & RESUME LAUNCHER
 */
@Composable
fun MoavineenStatsView(
    isNightMode: Boolean,
    cardBgColor: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    onNavigateToQuiz: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("moavineen_hujjaj_prefs", Context.MODE_PRIVATE) }

    val savedIndex = prefs.getInt("saved_index", 0)
    val savedScore = prefs.getInt("saved_score", 0)
    val savedAttempted = prefs.getInt("saved_attempted", 0)
    val accuracy = if (savedAttempted > 0) (savedScore * 100 / savedAttempted) else 0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("📊 NTS Test Readiness Scorecard", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${savedIndex + 1} / 1,040", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = if (isNightMode) Color(0xFF34D399) else Color(0xFF0D5C3A))
                            Text("Current Position", fontSize = 11.sp, color = secondaryTextColor)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("$accuracy%", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFDAA520))
                            Text("Accuracy Rate", fontSize = 11.sp, color = secondaryTextColor)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("$savedScore", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF3B82F6))
                            Text("Correct Answers", fontSize = 11.sp, color = secondaryTextColor)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onNavigateToQuiz,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isNightMode) Color(0xFF059669) else Color(0xFF0D5C3A)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("▶ Continue Quiz Session (Question ${savedIndex + 1})", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
