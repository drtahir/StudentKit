package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.StudentKitViewModel
import android.content.Intent
import android.widget.Toast

// Modern colors for Hadith themes
private val BurgundyDark = Color(0xFF4A1521)
private val BurgundyLight = Color(0xFF880E4F)
private val NavyDark = Color(0xFF0D1B2A)
private val NavyLight = Color(0xFF1B263B)
private val GoldAccent = Color(0xFFD4AF37)
private val WarmBeige = Color(0xFFFAF6F0)

// Data Models
data class HadithNawawiItem(
    val number: Int,
    val titleArabic: String,
    val titleEnglish: String,
    val titleUrdu: String,
    val arabicText: String,
    val englishText: String,
    val urduText: String,
    val narrator: String,
    val source: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NawawiHadithReaderScreen(
    viewModel: StudentKitViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val clipboard = LocalClipboardManager.current
    val hadiths = remember { getNawawiHadiths() }
    
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) } // 0: Browse, 1: Bookmarks, 2: Daily Hadith
    var bookmarkedIds by remember { mutableStateOf(setOf<Int>()) }
    
    // Accessibility: Dynamic Font Scaling
    var arabicFontSize by remember { mutableStateOf(20f) }
    var translationFontSize by remember { mutableStateOf(14f) }
    
    // Random Daily Hadith
    val dailyHadith = remember { hadiths.random() }
    
    val filteredHadiths = remember(searchQuery, hadiths) {
        if (searchQuery.trim().isEmpty()) {
            hadiths
        } else {
            hadiths.filter {
                it.titleEnglish.contains(searchQuery, ignoreCase = true) ||
                it.titleUrdu.contains(searchQuery, ignoreCase = true) ||
                it.englishText.contains(searchQuery, ignoreCase = true) ||
                it.urduText.contains(searchQuery, ignoreCase = true) ||
                it.arabicText.contains(searchQuery) ||
                it.number.toString() == searchQuery.trim()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "40 Hadith Nawawi",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "الـأربعون النووية • ${hadiths.size} Hadiths • Trilingual",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = BurgundyDark)
                    }
                },
                actions = {
                    // Font scaling actions for senior/low vision accessibility
                    IconButton(onClick = {
                        if (arabicFontSize < 28) {
                            arabicFontSize += 2f
                            translationFontSize += 1f
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase Text Size", tint = BurgundyDark)
                    }
                    IconButton(onClick = {
                        if (arabicFontSize > 16) {
                            arabicFontSize -= 2f
                            translationFontSize -= 1f
                        }
                    }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease Text Size", tint = BurgundyDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WarmBeige)
                .padding(paddingValues)
        ) {
            // Screen Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = BurgundyDark,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = GoldAccent
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = {
                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        selectedTab = 0
                    },
                    text = { Text("Browse (${hadiths.size})", fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = null) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = {
                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        selectedTab = 1
                    },
                    text = { Text("Bookmarks (${bookmarkedIds.size})", fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = null) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = {
                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        selectedTab = 2
                    },
                    text = { Text("Daily Reflection", fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.Star, contentDescription = null) }
                )
            }

            when (selectedTab) {
                0 -> {
                    // Search & Browse View
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .testTag("nawawi_search_input"),
                        placeholder = { Text("Search Arabic, English, Urdu or No...", fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = BurgundyLight) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray)
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BurgundyLight,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (filteredHadiths.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("No Hadiths found matching your query.", color = Color.Gray)
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (searchQuery.trim().isEmpty()) {
                                item {
                                    Card(
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp, bottom = 4.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "40 Hadith Nawawi",
                                                    fontSize = 15.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                                )
                                                Text(
                                                    text = "الأربعون النووية",
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = "A comprehensive compilation of 40 foundational sayings of the Prophet Muhammad (PBUH) compiled by Imam an-Nawawi, covering the essential core of Islamic faith, ethics, and jurisprudence with full trilingual support.",
                                                fontSize = 11.sp,
                                                lineHeight = 15.sp,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                                            )
                                        }
                                    }
                                }
                            }
                            
                            itemsIndexed(filteredHadiths) { _, hadith ->
                                HadithNawawiCard(
                                    hadith = hadith,
                                    arabicFontSize = arabicFontSize,
                                    translationFontSize = translationFontSize,
                                    isBookmarked = bookmarkedIds.contains(hadith.number),
                                    onBookmarkToggle = {
                                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                        bookmarkedIds = if (bookmarkedIds.contains(hadith.number)) {
                                            bookmarkedIds - hadith.number
                                        } else {
                                            bookmarkedIds + hadith.number
                                        }
                                    },
                                    onCopy = {
                                        val copyText = "Hadith ${hadith.number}: ${hadith.titleEnglish}\n\nArabic: ${hadith.arabicText}\n\nEnglish: ${hadith.englishText}\n\nUrdu: ${hadith.urduText}\n\nNarrated by: ${hadith.narrator} - ${hadith.source}"
                                        clipboard.setText(AnnotatedString(copyText))
                                        Toast.makeText(context, "Hadith content copied to clipboard!", Toast.LENGTH_SHORT).show()
                                    },
                                    onShare = {
                                        val shareText = "Hadith ${hadith.number}: ${hadith.titleEnglish}\n\nArabic: ${hadith.arabicText}\n\nEnglish: ${hadith.englishText}\n\nUrdu: ${hadith.urduText}\n\nNarrated by: ${hadith.narrator} - ${hadith.source}"
                                        val intent = Intent(Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(Intent.EXTRA_TEXT, shareText)
                                        }
                                        context.startActivity(Intent.createChooser(intent, "Share Hadith"))
                                    }
                                )
                            }
                        }
                    }
                }
                1 -> {
                    // Bookmarked View
                    val bookmarkedList = hadiths.filter { bookmarkedIds.contains(it.number) }
                    if (bookmarkedList.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = null,
                                    modifier = Modifier.size(72.dp),
                                    tint = Color.Gray.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "No Bookmarks Yet",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Tap the bookmark icon on any Hadith to save it here for fast retrieval offline.",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            itemsIndexed(bookmarkedList) { _, hadith ->
                                HadithNawawiCard(
                                    hadith = hadith,
                                    arabicFontSize = arabicFontSize,
                                    translationFontSize = translationFontSize,
                                    isBookmarked = true,
                                    onBookmarkToggle = {
                                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                        bookmarkedIds = bookmarkedIds - hadith.number
                                    },
                                    onCopy = {
                                        val copyText = "Hadith ${hadith.number}: ${hadith.titleEnglish}\n\nArabic: ${hadith.arabicText}\n\nEnglish: ${hadith.englishText}\n\nUrdu: ${hadith.urduText}\n\nNarrated by: ${hadith.narrator} - ${hadith.source}"
                                        clipboard.setText(AnnotatedString(copyText))
                                        Toast.makeText(context, "Hadith content copied!", Toast.LENGTH_SHORT).show()
                                    },
                                    onShare = {
                                        val shareText = "Hadith ${hadith.number}: ${hadith.titleEnglish}\n\nArabic: ${hadith.arabicText}\n\nEnglish: ${hadith.englishText}\n\nUrdu: ${hadith.urduText}\n\nNarrated by: ${hadith.narrator} - ${hadith.source}"
                                        val intent = Intent(Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(Intent.EXTRA_TEXT, shareText)
                                        }
                                        context.startActivity(Intent.createChooser(intent, "Share Hadith"))
                                    }
                                )
                            }
                        }
                    }
                }
                2 -> {
                    // Daily Reflection Card (Randomized static daily selection)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(GoldAccent.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "DAILY SPIRITUAL SELECTION",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BurgundyLight,
                                        letterSpacing = 1.sp
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                HadithNawawiCard(
                                    hadith = dailyHadith,
                                    arabicFontSize = arabicFontSize,
                                    translationFontSize = translationFontSize,
                                    isBookmarked = bookmarkedIds.contains(dailyHadith.number),
                                    onBookmarkToggle = {
                                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                        bookmarkedIds = if (bookmarkedIds.contains(dailyHadith.number)) {
                                            bookmarkedIds - dailyHadith.number
                                        } else {
                                            bookmarkedIds + dailyHadith.number
                                        }
                                    },
                                    onCopy = {
                                        val copyText = "Hadith ${dailyHadith.number}: ${dailyHadith.titleEnglish}\n\nArabic: ${dailyHadith.arabicText}\n\nEnglish: ${dailyHadith.englishText}\n\nUrdu: ${dailyHadith.urduText}\n\nNarrated by: ${dailyHadith.narrator} - ${dailyHadith.source}"
                                        clipboard.setText(AnnotatedString(copyText))
                                        Toast.makeText(context, "Hadith content copied!", Toast.LENGTH_SHORT).show()
                                    },
                                    onShare = {
                                        val shareText = "Hadith ${dailyHadith.number}: ${dailyHadith.titleEnglish}\n\nArabic: ${dailyHadith.arabicText}\n\nEnglish: ${dailyHadith.englishText}\n\nUrdu: ${dailyHadith.urduText}\n\nNarrated by: ${dailyHadith.narrator} - ${dailyHadith.source}"
                                        val intent = Intent(Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(Intent.EXTRA_TEXT, shareText)
                                        }
                                        context.startActivity(Intent.createChooser(intent, "Share Hadith"))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HadithNawawiCard(
    hadith: HadithNawawiItem,
    arabicFontSize: Float,
    translationFontSize: Float,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit,
    onCopy: () -> Unit,
    onShare: () -> Unit
) {
    var viewMode by remember { mutableStateOf(0) } // 0: Combined, 1: English, 2: Urdu
    
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Hadith Header Row (Hadith number & Bookmarking)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(GoldAccent.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = hadith.number.toString(),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = hadith.titleEnglish,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = hadith.titleUrdu,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Row {
                    IconButton(onClick = onCopy) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy Hadith", tint = Color.Gray)
                    }
                    IconButton(onClick = onShare) {
                        Icon(Icons.Default.Share, contentDescription = "Share Hadith", tint = Color.Gray)
                    }
                    IconButton(onClick = onBookmarkToggle) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) GoldAccent else Color.Gray
                        )
                    }
                }
            }
            
            Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.5f))
            
            // Sub-Tabs for Translation modes (Combined vs separate)
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                SegmentedButton(
                    selected = viewMode == 0,
                    onClick = { viewMode = 0 },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3)
                ) {
                    Text("Combined", fontSize = 11.sp)
                }
                SegmentedButton(
                    selected = viewMode == 1,
                    onClick = { viewMode = 1 },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3)
                ) {
                    Text("English", fontSize = 11.sp)
                }
                SegmentedButton(
                    selected = viewMode == 2,
                    onClick = { viewMode = 2 },
                    shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3)
                ) {
                    Text("Urdu", fontSize = 11.sp)
                }
            }

            // Narrator Line
            Text(
                text = "Narrated by ${hadith.narrator}:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Arabic Text (Always shown on top for maximum visual dignity)
            Text(
                text = hadith.arabicText,
                fontSize = arabicFontSize.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = NavyDark,
                textAlign = TextAlign.Right,
                lineHeight = (arabicFontSize * 1.5).sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFBF8F4), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Translations based on viewMode
            if (viewMode == 0 || viewMode == 1) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "English translation:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = hadith.englishText,
                        fontSize = translationFontSize.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = (translationFontSize * 1.4).sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                }
            }
            
            if (viewMode == 0 || viewMode == 2) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "اردو ترجمہ:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark,
                        modifier = Modifier.align(Alignment.End)
                    )
                    Text(
                        text = hadith.urduText,
                        fontSize = (translationFontSize + 1f).sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Right,
                        lineHeight = (translationFontSize * 1.6).sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Source / Reference
            Box(
                modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                    .padding(6.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    text = hadith.source,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }
    }
}


// Complete Authentic 40 Hadith Nawawi Reference Data (Trilingual)
fun getNawawiHadiths(): List<HadithNawawiItem> {
    return listOf(
        HadithNawawiItem(
            number = 1,
            titleArabic = "إنما الأعمال بالنيات",
            titleEnglish = "Actions are by Intentions",
            titleUrdu = "اعمال کا دارومدار نیتوں پر ہے",
            arabicText = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى، فَمَنْ كَانَتْ هِجْرَتُهُ إِلَى اللَّهِ وَرَسُولِهِ فَهِجْرَتُهُ إِلَى اللَّهِ وَرَسُولِهِ، وَمَنْ كَانَتْ هِجْرَتُهُ لِدُنْيَا يُصِيبُهَا أَوْ امْرَأَةٍ يَتَزَوَّجُهَا فَهِجْرَتُهُ إِلَى مَا هَاجَرَ إِلَيْهِ.",
            englishText = "Actions are but by intentions and every man shall have only that which he intended. Thus he whose migration was for Allah and His Messenger, his migration was for Allah and His Messenger, and he whose migration was for a worldly benefit or for a woman to marry, his migration was for that which he migrated.",
            urduText = "اعمال کا دارومدار صرف نیتوں پر ہے اور ہر شخص کے لیے وہی ہے جس کی اس نے نیت کی۔ پس جس کی ہجرت اللہ اور اس کے رسول کی طرف ہو تو اس کی ہجرت اللہ اور اس کے رسول کی طرف مانی جائے گی، اور جس کی ہجرت دنیا کے لیے ہو جسے وہ حاصل کرنا چاہتا ہے یا کسی عورت کے لیے ہو جس سے وہ شادی کرنا چاہتا ہے، تو اس کی ہجرت اسی کی طرف ہے جس کی طرف اس نے ہجرت کی۔",
            narrator = "Amir al-Mu'minin, Abu Hafs Umar bin al-Khattab (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 2,
            titleArabic = "حديث جبريل عليه السلام",
            titleEnglish = "The Hadith of Jibreel",
            titleUrdu = "حدیثِ جبرائیل علیہ السلام",
            arabicText = "قَالَ: فَأَخْبِرْنِي عَنِ الإِيسْلاَمِ. فَقَالَ رَسُولُ اللَّهِ صلى الله عليه وسلم: \"الإِيسْلاَمُ أَنْ تَشْهَدَ أَنْ لاَ إِلَهَ إِلاَّ اللَّهُ وَأَنَّ مُحَمَّدًا رَسُولُ اللَّهِ، وَتُقِيمَ الصَّلاَةَ، وَتُؤْتِيَ الزَّكَاةَ، وَتَصُومَ رَمَضَانَ، وَتَحُجَّ الْبَيْتَ إِنِ اسْتَطَعْتَ إِلَيْهِ سَبِيلاً\". قَالَ: صَدَقْتَ. قَالَ: فَأَخْبِرْنِي عَنِ الإِيمَانِ. قَالَ: \"أَنْ تُؤْمِنَ بِاللَّهِ، وَمَلاَئِكَتِهِ، وَكُتُبِهِ، وَرُسُلِهِ، وَالْيَوْمِ الآخِرِ، وَتُؤْمِنَ بِالْقَدَرِ خَيْرِهِ وَشَرِّهِ\". قَالَ: صَدَقْتَ. قَالَ: فَأَخْبِرْنِي عَنِ الإِحْسَانِ. قَالَ: \"أَنْ تَعْبُدَ اللَّهِ كَأَنَّكَ تَرَاهُ فَإِنْ لَمْ تَكُنْ تَرَاهُ فَإِنَّهُ يَرَاكَ\".",
            englishText = "The Messenger of Allah (PBUH) said: 'Islam is that you witness that there is no deity worthy of worship except Allah and that Muhammad is the Messenger of Allah, and you establish prayer, pay Zakat, fast Ramadan, and perform Hajj if you are able.' He said: 'Tell me about Iman.' The Prophet said: 'That you believe in Allah, His angels, His books, His messengers, the Last Day, and believe in Al-Qadar (divine decree), both its good and bad.' He said: 'Tell me about Ihsan.' The Prophet said: 'That you worship Allah as if you see Him, for if you do not see Him, He surely sees you.'",
            urduText = "رسول اللہ صلی اللہ علیہ وسلم نے فرمایا: ”اسلام یہ ہے کہ تم گواہی دو کہ اللہ کے سوا کوئی معبود نہیں اور محمد (صلی اللہ علیہ وسلم) اللہ کے رسول ہیں، نماز قائم کرو، زکوٰۃ ادا کرو، رمضان کے روزے رکھو، اور اگر استطاعت ہو تو بیت اللہ کا حج کرو۔“ انہوں نے پوچھا: ”مجھے ایمان کے بارے میں بتائیں۔“ فرمایا: ”یہ کہ تم ایمان لاؤ اللہ پر، اس کے فرشتوں پر، اس کی کتابوں پر، اس کے رسولوں پر، یومِ آخرت پر، اور اچھی اور بری تقدیر پر۔“ پوچھا: ”احسان کے بارے میں بتائیں۔“ فرمایا: ”یہ کہ تم اللہ کی عبادت اس طرح کرو گویا تم اسے دیکھ رہے ہو، اور اگر تم اسے نہیں دیکھ رہے تو یقیناً وہ تمہیں دیکھ رہا ہے۔“",
            narrator = "Umar bin al-Khattab (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 3,
            titleArabic = "أركان الإسلام",
            titleEnglish = "The Pillars of Islam",
            titleUrdu = "اسلام کے ارکان",
            arabicText = "بُنِيَ الإِسْلاَمُ عَلَى خَمْسٍ: شَهَادَةِ أَنْ لاَ إِلَهَ إِلاَّ اللَّهُ وَأَنَّ مُحَمَّدًا رَسُولُ اللَّهِ، وَإِقَامِ الصَّلاَةِ، وَإِيتَاءِ الزَّكَاةِ، وَصَوْمِ رَمَضَانَ، وَحَجِّ الْبَيْتِ لمن استطاع إليه سبيلا.",
            englishText = "Islam has been built upon five: testifying that there is no deity worthy of worship except Allah and that Muhammad is the Messenger of Allah, establishing prayer, paying Zakat, making Hajj to the House, and fasting in Ramadan.",
            urduText = "اسلام کی بنیاد پانچ چیزوں پر رکھی گئی ہے: اس بات کی گواہی دینا کہ اللہ کے سوا کوئی سچا معبود نہیں اور محمد صلی اللہ علیہ وسلم اللہ کے رسول ہیں، نماز قائم کرنا، زکوٰۃ ادا کرنا، بیت اللہ کا حج کرنا، اور رمضان کے روزے رکھنا۔",
            narrator = "Abu Abdur-Rahman Abdullah bin Umar (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 4,
            titleArabic = "خلق الإنسان وتحديد رزقه وأجله",
            titleEnglish = "Creation of Human & Destiny",
            titleUrdu = "انسان کی تخلیق اور تقدیر کا فیصلہ",
            arabicText = "إِنَّ أَحَدَكُمْ يُجْمَعُ خَلْقُهُ فِي بَطْنِ أُمِّهِ أَرْبَعِينَ يَوْمًا نُطْفَةً، ثُمَّ يَكُونُ عَلَقَةً مِثْلَ ذَلِكَ، ثُمَّ يَكُونُ مُضْغَةً مِثْلَ ذَلِكَ، ثُمَّ يُرْسَلُ إِلَيْهِ الْمَلَكُ فَيَنْفُخُ فِيهِ الرُّوحَ، وَيُؤْمَرُ بِأَرْبَعِ كَلِمَاتٍ: بِكَتْبِ رِزْقِهِ، وَأَجَلِهِ، وَعَمَلِهِ، وَشَقِيٌّ أَوْ سَعِيدٌ.",
            englishText = "Verily the creation of each one of you is brought together in his mother’s womb for forty days in the form of a drop of fluid, then he is a clinging clot for a like period, then a lump of flesh for a like period, then there is sent to him the angel who blows the breath of life into him and who is commanded with four matters: to write down his sustenance, his life span, his actions, and whether he will be happy or miserable.",
            urduText = "بلاشبہ تم میں سے ہر ایک کی پیدائش کی تیاری اس کی ماں کے پیٹ میں چالیس دن نطفہ کی شکل میں جمع کی جاتی ہے، پھر اتنے ہی دن وہ خون کا لوتھڑا رہتا ہے، پھر اتنے ہی دن وہ بوٹی بنتا ہے، پھر اللہ تعالیٰ ایک فرشتہ بھیجتا ہے جو اس میں روح پھونکتا ہے اور اسے چار چیزیں لکھنے کا حکم دیا جاتا ہے: اس کا رزق، اس کی عمر، اس کے اعمال، اور یہ کہ وہ بدبخت ہوگا یا نیک بخت۔",
            narrator = "Abu Abdur-Rahman Abdullah bin Mas'ud (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 5,
            titleArabic = "رد البدع والمحدثات",
            titleEnglish = "Rejection of Religious Innovation",
            titleUrdu = "دین میں بدعت کی تردید",
            arabicText = "مَنْ أَحْدَثَ فِي أَمْرِنَا هَذَا مَا لَيْسَ فِيهِ فَهُوَ رَدٌّ.",
            englishText = "He who innovates something in this matter of ours (Islam) that which is not of it, will have it rejected.",
            urduText = "جس نے ہمارے اس دین کے معاملے میں کوئی ایسی نئی چیز ایجاد کی جو اس میں سے نہیں ہے، تو وہ مردود (ناقابلِ قبول) ہے۔",
            narrator = "Umm al-Mu'minin, Aisha (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 6,
            titleArabic = "اتقاء الشبهات",
            titleEnglish = "Avoiding Doubtful Matters",
            titleUrdu = "شتبہات سے بچنا",
            arabicText = "إِنَّ الْحَلالَ بَيِّنٌ وَإِنَّ الْحَرَامَ بَيِّنٌ وَبَيْنَهُمَا أُمُورٌ مُشْتَبِهَاتٌ لا يَعْلَمُهُنَّ كَثِيرٌ مِنَّ النَّاسِ فَمَنِ اتَّقَى الشُّبُهَاتِ اسْتَبْرَأَ لِدِينِهِ وَعِرْضِهِ وَمَنْ وَقَعَ فِي الشُّبُهَاتِ وَقَعَ فِي الْحَرَامِ... أَلا وَإِنَّ فِي الْجَسَدِ مُضْغَةً إِذَا صَلَحَتْ صَلَحَ الْجَسَدُ كُلُّهُ وَإِذَا فَسَدَتْ فَسَدَ الْجَسَدُ كُلُّهُ أَلا وَهِيَ الْقَلْبُ.",
            englishText = "That which is lawful is clear and that which is unlawful is clear, and between the two of them are doubtful matters about which many people do not know. Thus he who avoids doubtful matters clears himself in regard to his religion and his honor... Beware, in the body there is a piece of flesh; if it is sound, the whole body is sound, and if it is corrupt, the whole body is corrupt. Behold, it is the heart.",
            urduText = "حلال بالکل واضح ہے اور حرام بھی واضح ہے، اور ان دونوں کے درمیان مشتبہ چیزیں ہیں جنہیں بہت سے لوگ نہیں جانتے۔ پس جس نے مشتبہ چیزوں سے پرہیز کیا، اس نے اپنے دین اور اپنی عزت کو محفوظ کر لیا... سن لو! جسم میں ایک گوشت کا ٹکڑا ہے؛ جب وہ ٹھیک ہوتا ہے تو سارا جسم ٹھیک رہتا ہے، اور جب وہ خراب ہو جاتا ہے تو سارا جسم خراب ہو جاتا ہے۔ یاد رکھو، وہ دل ہے۔",
            narrator = "Abu Abdullah an-Nu'man bin Bashir (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 7,
            titleArabic = "الدين النصيحة",
            titleEnglish = "Religion is Sincerity & Advice",
            titleUrdu = "دین خیرخواہی کا نام ہے",
            arabicText = "الدِّينُ النَّصِيحَةُ. قُلْنَا: لِمَنْ؟ قَالَ: لِلَّهِ وَلِكِتَابِهِ وَلِرَسُولِهِ وَلأَئِمَّةِ الْمُسْلِمِينَ وَعَامَّتِهِمْ.",
            englishText = "The Prophet (PBUH) said: 'The religion is sincerity/well-wishing.' We said: 'To whom?' He said: 'To Allah, His Book, His Messenger, and to the leaders of the Muslims and their common folk.'",
            urduText = "نبی اکرم صلی اللہ علیہ وسلم نے فرمایا: ”دین سراسر خیرخواہی کا نام ہے۔“ ہم نے عرض کیا: ”کس کے لیے؟“ فرمایا: ”اللہ کے لیے، اس کی کتاب کے لیے، اس کے رسول کے لیے، مسلمانوں کے ائمہ (حکمرانوں) کے لیے، اور ان کے عام لوگوں کے لیے۔“",
            narrator = "Abu Ruqayyah Tamim bin Aus ad-Dari (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 8,
            titleArabic = "حرمة المسلم",
            titleEnglish = "The Sanctity of a Muslim",
            titleUrdu = "مسلمان کی جان و مال کی حرمت",
            arabicText = "أُمِرْتُ أَنْ أُقَاتِلَ النَّاسَ حَتَّى يَشْهَدُوا أَنْ لاَ إِلَهَ إِلاَّ اللَّهُ وَأَنَّ مُحَمَّدًا رَسُولُ اللَّهِ، وَيُقِيمُوا الصَّلاَةَ، وَيُؤْتُوا الزَّكَاةَ، فَإِذَا فَعَلُوا ذَلِكَ عَصَمُوا مِنِّي دِمَاءَهُمْ وَأَمْوَالَهُمْ إِلاَّ بِحَقِّ الإِسْلاَمِ، وَحِسَابُهُمْ عَلَى اللَّهِ.",
            englishText = "I have been commanded to fight against people so long as they do not bear witness that there is no deity worthy of worship except Allah and that Muhammad is the Messenger of Allah, and establish prayer and pay Zakat. If they do that, their blood and wealth are safe from me except by Islamic law, and their reckoning is with Allah.",
            urduText = "مجھے حکم دیا گیا ہے کہ میں لوگوں سے قتال کروں یہاں تک کہ وہ گواہی دیں کہ اللہ کے سوا کوئی سچا معبود نہیں اور محمد (صلی اللہ علیہ وسلم) اللہ کے رسول ہیں، نماز قائم کریں اور زکوٰۃ ادا کریں۔ جب وہ ایسا کر لیں تو انہوں نے مجھ سے اپنے خون اور اپنے مال کو محفوظ کر لیا سوائے اسلام کے حق کے، اور ان کا حساب اللہ کے ذمے ہے۔",
            narrator = "Abu Abdur-Rahman Abdullah bin Umar (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 9,
            titleArabic = "التكليف بما يستطاع",
            titleEnglish = "Duties are according to Ability",
            titleUrdu = "استطاعت کے مطابق احکامات پر عمل کرنا",
            arabicText = "مَا نَهَيْتُكُمْ عَنْهُ فَاجْتَنِبُوهُ، وَمَا أَمَرْتُكُمْ بِهِ فَأْتُوا مِنْهُ مَا اسْتَطَعْتُمْ، فَإِنَّمَا أَهْلَكَ الَّذِينَ مِنْ قَبْلِكُمْ كَثْرَةُ سُؤَالِهِمْ وَاخْتِلاَفُهُمْ عَلَى أَنْبِيَائِهِمْ.",
            englishText = "What I have forbidden for you, avoid. What I have ordered you to do, comply with as much as you can. For indeed, what destroyed those before you was their excessive questioning and disagreeing with their prophets.",
            urduText = "جس چیز سے میں نے تمہیں منع کیا ہے اس سے بچو، اور جس چیز کا میں نے تمہیں حکم دیا ہے اسے اپنی استطاعت کے مطابق کرو۔ بلاشبہ تم سے پہلے لوگوں کو ان کے کثرتِ سوالات اور ان کے انبیاء سے اختلاف نے ہی ہلاک کیا تھا۔",
            narrator = "Abu Hurayrah (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 10,
            titleArabic = "الاقتصار على الحلال الطيب",
            titleEnglish = "Restricting to the Pure & Lawful",
            titleUrdu = "صرف حلال و پاکیزہ کھانا اور قبولیتِ دعا",
            arabicText = "إِنَّ اللَّهَ طَيِّبٌ لاَ يَقْبَلُ إِلاَّ طَيِّبًا، وَإِنَّ اللَّهَ أَمَرَ الْمُؤْمِنِينَ بِمَا أَمَرَ بِهِ الْمُرْسَلِينَ... ثُمَّ ذَكَرَ الرَّجُلَ يُطِيلُ السَّفَرَ أَشْعَثَ أَغْبَرَمَدَّ يَدَيْهِ إِلَى السَّمَاءِ: يَا رَبِّ، يَا رَبِّ، وَمَطْعَمُهُ حَرَامٌ، وَمَشْرَبُهُ حَرَامٌ، وَمَلْبَسُهُ حَرَامٌ، وَغُذِيَ بِالْحَرَامِ، فَأَنَّى يُسْتَجَابُ لِذَلِكَ؟",
            englishText = "Allah the Almighty is Pure and accepts only that which is pure. And Allah has commanded the Believers as He commanded the Messengers... Then the Prophet mentioned a man who, having journeyed far, is disheveled and dusty, and who spreads out his hands to the sky saying 'O Lord! O Lord!', while his food is haram, his drink is haram, his clothing is haram, and he has been nourished with haram, so how can his supplication be answered?",
            urduText = "بلاشبہ اللہ تعالیٰ پاک ہے اور پاکیزہ چیز ہی قبول فرماتا ہے، اور اللہ نے مومنوں کو اسی بات کا حکم دیا ہے جس کا اپنے رسولوں کو دیا تھا... پھر آپ نے ایک شخص کا ذکر کیا جو طویل سفر کرتا ہے، پریشان حال اور گرد آلود ہے، اپنے ہاتھ آسمان کی طرف اٹھا کر پکارتا ہے: ”اے میرے رب! اے میرے رب!“ جبکہ اس کا کھانا حرام کا ہے، اس کا پینا حرام کا ہے، اس کا لباس حرام کا ہے، اور اس کی پرورش حرام سے ہوئی ہو، تو اس کی دعا کیسے قبول ہو سکتی ہے؟",
            narrator = "Abu Hurayrah (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 11,
            titleArabic = "الورع وترك الشبهات",
            titleEnglish = "Leaving Doubt for Certainty",
            titleUrdu = "شک کو چھوڑ کر یقین کو اختیار کرنا",
            arabicText = "دَعْ مَا يَرِيبُكَ إِلَى مَا لاَ يَرِيبُكَ.",
            englishText = "Leave that which makes you doubt for that which does not make you doubt.",
            urduText = "اس چیز کو چھوڑ دو جو تمہیں شک و شبہ میں ڈالے اور اس چیز کو اختیار کرو جو تمہیں شک میں نہ ڈالے۔",
            narrator = "Abu Muhammad al-Hasan bin Ali bin Abi Talib (RA)",
            source = "Sunan at-Tirmidhi & Sunan an-Nasa'i"
        ),
        HadithNawawiItem(
            number = 12,
            titleArabic = "ترك مالا يعني",
            titleEnglish = "Leaving What Does Not Concern Him",
            titleUrdu = "فضول چیزوں کو چھوڑنا",
            arabicText = "مِنْ حُسْنِ إِسْلامِ الْمَرْءِ تَرْكُهُ مَا لا يَعْنِيهِ.",
            englishText = "From the excellence of a person’s Islam is his leaving alone that which does not concern him.",
            urduText = "آدمی کے اسلام کی خوبصورتی یہ ہے کہ وہ ان چیزوں کو چھوڑ دے جو اس کے لیے غیر مفید اور لا یعنی (فضول) ہیں۔",
            narrator = "Abu Hurayrah (RA)",
            source = "Sunan at-Tirmidhi"
        ),
        HadithNawawiItem(
            number = 13,
            titleArabic = "حب الخير للآخرين",
            titleEnglish = "Loving for Others what you Love for Yourself",
            titleUrdu = "دوسروں کے لیے وہی پسند کرنا جو اپنے لیے ہو",
            arabicText = "لا يُؤْمِنُ أَحَدُكُمْ حَتَّى يُحِبَّ لأَخِيهِ مَا يُحِبُّ لِنَفْسِهِ.",
            englishText = "None of you [truly] believes until he loves for his brother that which he loves for himself.",
            urduText = "تم میں سے کوئی شخص اس وقت تک سچا مومن نہیں ہو سکتا جب تک وہ اپنے بھائی کے لیے بھی وہی چیز پسند نہ کرے جو اپنے لیے پسند کرتا ہے۔",
            narrator = "Abu Hamzah Anas bin Malik (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 14,
            titleArabic = "زوال العصمة وعقوبة القتل والزنى والردة",
            titleEnglish = "Prohibition of Adultery, Murder & Apostasy",
            titleUrdu = "مسلمان کی جان مباح ہونے کی تین صورتیں",
            arabicText = "لاَ يَحِلُّ دَمُ امْرِئٍ مُسْلِمٍ يَشْهَدُ أَنْ لاَ إِلَهَ إِلاَّ اللَّهُ وَأَنِّي رَسُولُ اللَّهِ إِلاَّ بِإِحْدَى ثَلاَثٍ: الثَّيِّبُ الزَّانِي، وَالنَّفْسُ بِالنَّفْسِ، وَالتَّارِكُ لِدِينِهِ الْمُفَارِقُ لِلْجَمَاعَةِ.",
            englishText = "It is not permissible to spill the blood of a Muslim who bears witness that there is no deity worthy of worship except Allah and that I am the Messenger of Allah, except in one of three cases: the married person who commits adultery, a life for a life, and the one who forsakes his religion and separates from the community.",
            urduText = "کسی مسلمان شخص کا خون بہانا حلال نہیں ہے جو اس بات کی گواہی دیتا ہو کہ اللہ کے سوا کوئی معبود نہیں اور میں اللہ کا رسول ہوں، سوائے تین صورتوں کے: شادی شدہ زانی، جان کے بدلے جان، اور اپنے دین کو چھوڑ کر مسلمانوں کی جماعت سے الگ ہونے والا۔",
            narrator = "Abu Mas'ud Abdullah bin Mas'ud (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 15,
            titleArabic = "آداب الكلام والضيافة والجوار",
            titleEnglish = "Ethics of Speech, Hospitality & Neighbors",
            titleUrdu = "کلام، مہمان نوازی اور پڑوسی کے حقوق",
            arabicText = "مَنْ كَانَ يُؤْمِنُ بِاللَّهِ وَالْيَوْمِ الآخِرِ فَلْيَقُلْ خَيْرًا أَوْ لِيَصْمُتْ، وَمَنْ كَانَ يُؤْمِنُ بِاللَّهِ وَالْيَوْمِ الآخِرِ فَلْيُكْرِمْ جَارَهُ، وَمَنْ كَانَ يُؤْمِنُ بِاللَّهِ وَالْيَوْمِ الآخِرِ فَلْيُكْرِمْ ضَيْفَهُ.",
            englishText = "Whosoever believes in Allah and the Last Day, let him speak good or remain silent; and whosoever believes in Allah and the Last Day, let him be generous to his neighbor; and whosoever believes in Allah and the Last Day, let him show hospitality to his guest.",
            urduText = "جو شخص اللہ اور یومِ آخرت پر ایمان رکھتا ہے اسے چاہیے کہ بھلی بات کہے یا خاموش رہے، اور جو شخص اللہ اور یومِ آخرت پر ایمان رکھتا ہے وہ اپنے پڑوسی کا احترام کرے، اور جو شخص اللہ اور یومِ آخرت پر ایمان رکھتا ہے وہ اپنے مہمان کی عزت کرے۔",
            narrator = "Abu Hurayrah (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 16,
            titleArabic = "النهي عن الغضب",
            titleEnglish = "Do Not Become Angry",
            titleUrdu = "غصہ نہ کرو",
            arabicText = "أَنَّ رَجُلاً قَالَ لِلنَّبِيِّ صلى الله عليه وسلم أَوْصِنِي. قَالَ: \"لا تَغْضَبْ\". فَرَدَّدَ مِرَارًا، قَالَ: \"لا تَغْضَبْ\".",
            englishText = "A man said to the Prophet (PBUH): 'Counsel me.' The Prophet said: 'Do not become angry.' The man repeated his request several times, and each time the Prophet said: 'Do not become angry.'",
            urduText = "ایک شخص نے نبی کریم صلی اللہ علیہ وسلم سے عرض کیا: ”مجھے وصیت فرمائیں۔“ آپ نے فرمایا: ”غصہ نہ کیا کرو۔“ اس نے بار بار اپنا سوال دہرایا اور آپ نے ہر بار یہی فرمایا: ”غصہ نہ کرو۔“",
            narrator = "Abu Hurayrah (RA)",
            source = "Sahih al-Bukhari"
        ),
        HadithNawawiItem(
            number = 17,
            titleArabic = "الأمر بالإحسان في كل شيء",
            titleEnglish = "Excellence in Slaughtering & All Deeds",
            titleUrdu = "ہر چیز میں احسان اور خوبصورتی کا حکم",
            arabicText = "إِنَّ اللَّهَ كَتَبَ الإِحْسَانَ عَلَى كُلِّ شَيْءٍ، فَإِذَا قَتَلْتُمْ فَأَحْسِنُوا الْقِتْلَةَ، وَإِذَا ذَبَحْتُمْ فَأَحْسِنُوا الذِّبْحَةَ، وَلْيُحِدَّ أَحَدُكُمْ شَفْرَتَهُ، وَلْيُرِحْ ذَبِيحَتَهُ.",
            englishText = "Verily Allah has prescribed proficiency and goodness (Ihsan) in all things. So if you kill, kill well; and if you slaughter, slaughter well. Let each one of you sharpen his blade and let him put his animal at ease.",
            urduText = "بلاشبہ اللہ تعالیٰ نے ہر چیز میں احسان (نیکی اور حسنِ کارکردگی) فرض کیا ہے۔ پس جب تم قتل کرو تو اچھے طریقے سے قتل کرو، اور جب تم ذبح کرو تو بہترین طریقے سے ذبح کرو۔ اور تم میں سے ہر ایک کو چاہیے کہ وہ اپنی چھری تیز کر لے اور اپنے ذبیحہ کو آرام پہنچائے۔",
            narrator = "Abu Ya'la Shaddad bin Aus (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 18,
            titleArabic = "اتقاء الله وحسن الخلق",
            titleEnglish = "Fear Allah & Maintain Good Character",
            titleUrdu = "تقویٰ اور حسنِ اخلاق",
            arabicText = "اتَّقِ اللَّهَ حَيْثُمَا كُنْتَ، وَأَتْبِعِ السَّيِّئَةَ الْحَسَنَةَ تَمْحُهَا، وَخَالِقِ النَّاسَ بِخُلُقٍ حَسَنٍ.",
            englishText = "Fear Allah wherever you are, and follow up a bad deed with a good deed which will wipe it out, and behave towards the people with a good character.",
            urduText = "تم جہاں کہیں بھی ہو اللہ کا تقویٰ اختیار کرو (ڈرتے رہو)، اور گناہ کے بعد نیکی کرو وہ اسے مٹا دے گی، اور لوگوں کے ساتھ اچھے اخلاق سے پیش آؤ۔",
            narrator = "Abu Dharr Jundub bin Junadah & Abu Abdur-Rahman Mu'adh bin Jabal (RA)",
            source = "Sunan at-Tirmidhi"
        ),
        HadithNawawiItem(
            number = 19,
            titleArabic = "حفظ الله وحدود الإيمان",
            titleEnglish = "The Preservation & Protection of Allah",
            titleUrdu = "اللہ کے حقوق کی حفاظت اور توکل",
            arabicText = "احْفَظِ اللَّهَ يَحْفَظْكَ، احْفَظِ اللَّهَ تَجِدْهُ تُجَاهَكَ، إِذَا سَأَلْتَ فَاسْأَلِ اللَّهَ، وَإِذَا اسْتَعَنْتَ فَاسْتَعِينْ بِاللَّهِ، وَاعْلَمْ أَنَّ الأُمَّةَ لَوْ اجْتَمَعَتْ عَلَى أَنْ يَنْفَعُوكَ بِشَيْءٍ لَمْ يَنْفَعُوكَ إِلاَّ بِشَيْءٍ قَدْ كَتَبَهُ اللَّهُ لَكَ...",
            englishText = "Be mindful of Allah, and He will protect you. Be mindful of Allah, and you will find Him in front of you. If you ask, ask of Allah; if you seek help, seek help from Allah. Know that if the whole nation were to gather to benefit you with anything, they would only benefit you with that which Allah has already written for you...",
            urduText = "تم اللہ کے احکام کی حفاظت کرو وہ تمہاری حفاظت فرمائے گا، تم اللہ کے حقوق کا خیال رکھو تم اسے اپنے سامنے پاؤ گے۔ جب بھی مانگنا ہو تو اللہ ہی سے مانگو، اور جب مدد چاہو تو اللہ ہی سے مدد چاہو۔ اور یہ بات جان لو کہ اگر تمام امت بھی تمہیں کوئی فائدہ پہنچانے کے لیے جمع ہو جائے تو وہ تمہیں کوئی فائدہ نہیں پہنچا سکتی سوائے اس کے جو اللہ نے تمہارے نصیب میں لکھ دیا ہے...",
            narrator = "Abu al-Abbas Abdullah bin Abbas (RA)",
            source = "Sunan at-Tirmidhi"
        ),
        HadithNawawiItem(
            number = 20,
            titleArabic = "الحياء من الإيمان",
            titleEnglish = "Modesty is part of Faith",
            titleUrdu = "حیا نبوت کا اولین پیغام ہے",
            arabicText = "إِنَّ مِمَّا أَدْرَكَ النَّاسُ مِنْ كَلاَمِ النُّبُوَّةِ الأُولَى: إِذَا لَمْ تَسْتَحْيِ فَاصْنَعْ مَا شِئْتَ.",
            englishText = "Among the words of the early prophets which have reached people is: 'If you feel no shame, then do as you wish.'",
            urduText = "لوگوں کو پہلی نبوت کے کلام سے جو نصیحت ملی ہے وہ یہ ہے: ”جب تم میں حیا ہی نہ رہے، تو جو جی چاہے کرو۔“",
            narrator = "Abu Mas'ud Uqbah bin Amr al-Ansari (RA)",
            source = "Sahih al-Bukhari"
        ),
        HadithNawawiItem(
            number = 21,
            titleArabic = "الاستقامة في الإسلام",
            titleEnglish = "Steadfastness & Faith",
            titleUrdu = "ایمان اور اس پر ثابت قدمی کا حکم",
            arabicText = "قُلْتُ: يَا رَسُولَ اللَّهِ، قُلْ لِي فِي الإِسْلاَمِ قَوْلاً لاَ أَسْأَلُ عَنْهُ أَحَدًا غَيْرَكَ. قَالَ: \"قُلْ: آمَنْتُ بِاللَّهِ ثُمَّ اسْتَقِمْ\".",
            englishText = "I said: 'O Messenger of Allah, tell me something about Islam which I can ask of no one but you.' He said: 'Say: I believe in Allah, and then stand firm (upon the truth).'",
            urduText = "میں نے عرض کیا: یا رسول اللہ! مجھے اسلام کے بارے میں کوئی ایسی بات بتائیں کہ مجھے آپ کے بعد کسی اور سے پوچھنے کی ضرورت نہ رہے۔ آپ صلی اللہ علیہ وسلم نے فرمایا: ”کہو کہ میں اللہ پر ایمان لایا، پھر اس پر ثابت قدم رہو۔“",
            narrator = "Abu Amr Sufyan bin Abdullah al-Thaqafi (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 22,
            titleArabic = "طريق الجنة",
            titleEnglish = "The Path to Paradise",
            titleUrdu = "فرائض کی پابندی اور دخولِ جنت",
            arabicText = "أَرَأَيْتَ إِذَا صَلَّيْتُ الْمَكْتُوبَاتِ، وَصُمْتُ رَمَضَانَ، وَأَحْلَلْتُ الْحَلاَلَ، وَحَرَّمْتُ الْحَرَامَ، وَلَمْ أَزِدْ عَلَى ذَلِكَ شَيْئًا، أَأَدْخُلُ الْجَنَّةَ؟ قَالَ: \"نَعَمْ\".",
            englishText = "Do you think that if I perform the obligatory prayers, fast in Ramadan, treat as lawful that which is lawful and forbid that which is forbidden, and do nothing further, I shall enter Paradise? The Prophet (PBUH) replied: 'Yes.'",
            urduText = "آپ کا کیا خیال ہے کہ اگر میں فرض نمازیں ادا کروں، رمضان کے روزے رکھوں، حلال کو حلال اور حرام کو حرام جانوں، اور اس پر کچھ بھی زیادہ نہ کروں، تو کیا میں جنت میں داخل ہو جاؤں گا؟ آپ نے فرمایا: ”ہاں۔“",
            narrator = "Abu Abdullah Jabir bin Abdullah al-Ansari (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 23,
            titleArabic = "السبق إلى الخيرات والطهور",
            titleEnglish = "Hastening to Goodness & Purity",
            titleUrdu = "پاکیزگی، نماز، صدقہ اور صبر کی فضیلت",
            arabicText = "الطُّهُورُ شَطْرُ الإِيمَانِ، وَالْحَمْدُ لِلَّهِ تَمْلأُ الْمِيزَانَ، وَسُبْحَانَ اللَّهِ وَالْحَمْدُ لِلَّهِ تَمْلآنِ أَوْ تَمْلأُ مَا بَيْنَ السَّمَاءِ وَالأَرْضِ، وَالصَّلاَةُ نُورٌ، وَالصَّدَقَةُ بُرْهَانٌ، وَالصَّبْرُ ضِيَاءٌ، وَالْقُرْآنُ حُجَّةٌ لَكَ أَوْ عَلَيْكَ...",
            englishText = "Purity is half of faith, 'Al-Hamdulillah' fills the scale, and 'SubhanAllah' and 'Al-Hamdulillah' fill the space between heavens and earth. Prayer is a light, charity is a proof, patience is a brightness, and the Qur'an is a proof for or against you...",
            urduText = "پاکیزگی آدھا ایمان ہے، اور الحمد للہ میزان کو بھر دیتا ہے، اور سبحان اللہ اور الحمد للہ آسمان اور زمین کے درمیانی خلا کو بھر دیتے ہیں۔ نماز ایک نور ہے، صدقہ دلیل ہے، اور صبر روشنی ہے، اور قرآن تمہارے حق میں یا تمہارے خلاف دلیل ہے...",
            narrator = "Abu Malik al-Harith bin Asim al-Ash'ari (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 24,
            titleArabic = "تحريم الظلم",
            titleEnglish = "Prohibition of Oppression",
            titleUrdu = "ظلم کی حرمت اور اللہ کی مغفرت و سخاوت",
            arabicText = "يَا عِبَادِي إِنِّي حَرَّمْتُ الظُّلْمَ عَلَى نَفْسِي وَجَعَلْتُهُ بَيْنَكُمْ مُحَرَّمًا فَلاَ تَظَالَمُوا... يَا عِبَادِي كُلُّكُمْ ضَالٌّ إِلاَّ مَنْ هَدَيْتُهُ فَاسْتَهْدُونِي أَهْدِكُمْ...",
            englishText = "Allah said: 'O My servants, I have forbidden oppression for Myself and have made it forbidden among you, so do not oppress one another. O My servants, all of you are astray except those I have guided, so seek guidance from Me and I will guide you...'",
            urduText = "اللہ تعالیٰ فرماتا ہے: ”اے میرے بندو! میں نے ظلم کو اپنے اوپر حرام کر لیا ہے اور اسے تمہارے درمیان بھی حرام قرار دیا ہے، پس ایک دوسرے پر ظلم نہ کرو۔ اے میرے بندو! تم سب گمراہ ہو سوائے اس کے جسے میں ہدایت دوں، پس مجھ سے ہدایت مانگو میں تمہیں ہدایت دوں گا...“",
            narrator = "Abu Dharr al-Ghifari (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 25,
            titleArabic = "أبواب الخير والصدقة",
            titleEnglish = "The Value of Charitable Deeds",
            titleUrdu = "ذکر الہی اور نیکی کی دعوت بھی صدقہ ہے",
            arabicText = "إِنَّ نَاسًا مِنْ أَصْحَابِ رَسُولِ اللَّهِ صلى الله عليه وسلم قَالُوا لِلنَّبِيِّ صلى الله عليه وسلم: يَا رَسُولُ اللَّهِ ذَهَبَ أَهْلُ الدُّثُورِ بِالأُجُورِ، يُصَلُّونَ كَمَا نُصَلِّي، وَيَصُومُونَ كَمَا نَصُومُ، وَيَتَصَدَّقُونَ بِفُضُولِ أَمْوَالِهِمْ. قَالَ: \"أَوَلَيْسَ قَدْ جَعَلَ اللَّهُ لَكُمْ مَا تَصَّدَّقُونَ؟ إِنَّ بِكُلِّ تَسْبِيحَةٍ صَدَقَةً، وَكُلِّ تَكْبِيرَةٍ صَدَقَةً...\"",
            englishText = "Some companions said: 'O Messenger of Allah, the wealthy have taken all the rewards. They pray as we pray, they fast as we fast, and they give charity from their excess wealth.' The Prophet replied: 'Has Allah not made for you that which you can give in charity? Verily every glorification (SubhanAllah) is charity, every praise (Alhamdulillah) is charity...'",
            urduText = "کچھ صحابہ نے عرض کیا: یا رسول اللہ! مالدار لوگ سارے ثواب لے گئے، وہ ہماری طرح نماز پڑھتے ہیں، ہماری طرح روزہ رکھتے ہیں اور اپنے زائد اموال سے صدقہ دیتے ہیں۔ آپ صلی اللہ علیہ وسلم نے فرمایا: ”کیا اللہ نے تمہارے لیے صدقہ کا سامان نہیں بنایا؟ بلاشبہ ہر بار سبحان اللہ کہنا صدقہ ہے، ہر بار اللہ اکبر کہنا صدقہ ہے...“",
            narrator = "Abu Dharr al-Ghifari (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 26,
            titleArabic = "فضل الإصلاح والعدل",
            titleEnglish = "Reconciliation & Justice is Charity",
            titleUrdu = "صلح کروانا اور نیکی کے راستے پر چلنا صدقہ ہے",
            arabicText = "كُلُّ سُلاَمَى مِنَ النَّاسِ عَلَيْهِ صَدَقَةٌ كُلَّ يَوْمٍ تَطْلُعُ فِيهِ الشَّمْسُ: تَعْدِلُ بَيْنَ الاِثْنَيْنِ صَدَقَةٌ، وَتُعِينُ الرَّجُلَ فِي دَابَّتِهِ فَتَحْمِلُهُ عَلَيْهَا أَوْ تَرْفَعُ لَهُ عَلَيْهَا مَتَاعَهُ صَدَقَةٌ، وَالْكَلِمَةُ الطَّيِّبَةُ صَدَقَةٌ...",
            englishText = "Every joint of a person must perform charity each day the sun rises: to judge justly between two people is charity, to help a man with his mount by helping him ride or loading his belongings is charity, a good word is charity...",
            urduText = "ہر روز جس میں سورج طلوع ہوتا ہے، انسان کے ہر جوڑ پر صدقہ لازم ہے: دو لوگوں کے درمیان انصاف و صلح کروانا صدقہ ہے، کسی آدمی کو اس کی سواری پر سوار ہونے میں یا اس کا سامان لادنے میں مدد کرنا صدقہ ہے، اور اچھی بات کہنا صدقہ ہے...",
            narrator = "Abu Hurayrah (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 27,
            titleArabic = "البر والإثم",
            titleEnglish = "Righteousness & Sin",
            titleUrdu = "نیکی اور گناہ کی تعریف اور پہچان",
            arabicText = "الْبِرُّ حُسْنُ الْخُلُقِ، وَالإِثْمُ مَا حَاكَ فِي نَفْسِكَ وَكَرِهْتَ أَنْ يَطَّلِعَ عَلَيْهِ النَّاسُ... اسْتَفْتِ قَلْبَكَ، الْبِرُّ مَا اطْمَأَنَّتْ إِلَيْهِ النَّفْسُ وَاطْمَأَنَّ إِلَيْهِ الْقَلْبُ، وَالإِثْمُ مَا حَاكَ فِي النَّفْسِ وَتَرَدَّدَ فِي الصَّدْرِ...",
            englishText = "Righteousness is good character, and sin is that which wavers in your heart and which you dislike that people should find out about... Consult your heart. Righteousness is that which the soul feels at ease with and the heart feels tranquil with, and sin is that which wavers in the soul and hesitates in the chest...",
            urduText = "نیکی اچھے اخلاق کا نام ہے، اور گناہ وہ ہے جو تمہارے دل میں کھٹکے اور تم ناپسند کرو کہ لوگوں کو اس کا علم ہو... اپنے دل سے فتویٰ لو۔ نیکی وہ ہے جس پر نفس مطمئن ہو اور دل کو تسلی ہو، اور گناہ وہ ہے جو نفس میں کھٹکے اور سینے میں تر دد پیدا کرے خواہ لوگ تمہیں اس کا جواز پیش کرتے رہیں...",
            narrator = "An-Nawwas bin Sam'an & Wabisah bin Ma'bad (RA)",
            source = "Sahih Muslim & Musnad Ahmad"
        ),
        HadithNawawiItem(
            number = 28,
            titleArabic = "لزوم السنة والسمع والطاعة",
            titleEnglish = "Adherence to the Sunnah",
            titleUrdu = "سنت نبوی اور خلفائے راشدین کی سنت کی پیروی",
            arabicText = "أُوصِيكُمْ بِتَقْوَى اللَّهِ وَالسَّمْعِ وَالطَّاعَةِ وَإِنْ تَأَمَّرَ عَلَيْكُمْ عَبْدٌ، فَإِنَّهُ مَنْ يَعِشْ مِنْكُمْ فَسَيَرَى اخْتِلاَفًا كَثِيرًا، فَعَلَيْكُمْ بِسُنَّتِي وَسُنَّةِ الْخُلَفَاءِ الرَّاشِدِينَ الْمَهْدِيِّينَ، عَضُّوا عَلَيْهَا بِالنَّوَاجِذِ...",
            englishText = "I counsel you to fear Allah and to hear and obey, even if a slave is appointed over you. Whosoever lives among you will see many differences, so adhere to my Sunnah and the Sunnah of the rightly-guided Caliphs. Bite down on it with your molars...",
            urduText = "میں تمہیں اللہ کا تقویٰ اختیار کرنے اور سننے اور اطاعت کرنے کی وصیت کرتا ہوں، خواہ تمہارا حاکم کوئی حبشی غلام ہی کیوں نہ ہو۔ بلاشبہ تم میں سے جو میرے بعد زندہ رہے گا وہ بہت سے اختلافات دیکھے گا، پس تم میری سنت اور میرے ہدایت یافتہ خلفائے راشدین کی سنت کو لازم پکڑنا اور اسے داڑھوں سے مضبوطی سے پکڑ لینا...",
            narrator = "Abu Najih al-Irbad bin Sariyah (RA)",
            source = "Abu Dawud & At-Tirmidhi"
        ),
        HadithNawawiItem(
            number = 29,
            titleArabic = "أبواب الخير والشر",
            titleEnglish = "The Doors of Goodness & Salvation",
            titleUrdu = "جنت میں لے جانے والے اعمال اور دین کا ستون",
            arabicText = "قُلْتُ: يَا رَسُولَ اللَّهِ، أَخْبِرْنِي بِعَمَلٍ يُدْخِلُنِي الْجَنَّةَ وَيُبَاعِدُنِي عَنِ النَّارِ. قَالَ: لَقَدْ سَأَلْتَ عَنْ عَظِيمٍ... أَلاَ أَدُلُّكَ عَلَى أَبْوَابِ الْخَيْرِ؟ الصَّوْمُ جُنَّةٌ، وَالصَّدَقَةُ تُطْفِئُ الْخَطِيئَةَ... رَأْسُ الأَمْرِ الإِسْلاَمُ، وَعَمُودُهُ الصَّلاَةُ، وَذِرْوَةُ سَنَامِهِ الْجِهَادُ...",
            englishText = "I said: 'O Messenger of Allah, tell me of an act which will enter me into Paradise and keep me away from Hellfire.' He said: 'You have asked about a great matter... Shall I not guide you to the gates of goodness? Fasting is a shield, charity extinguishes sin... The peak of the matter is Islam, its pillar is prayer, and its topmost part is Jihad...'",
            urduText = "میں نے عرض کیا: یا رسول اللہ! مجھے کوئی ایسا عمل بتائیں جو مجھے جنت میں داخل کر دے اور جہنم سے دور رکھے۔ آپ صلی اللہ علیہ وسلم نے فرمایا: ”تم نے بہت بڑی بات پوچھی ہے... کیا میں تمہیں بھلائی کے دروازے نہ بتاؤں؟ روزہ ڈھال ہے، اور صدقہ گناہوں کو ایسے مٹاتا ہے جیسے پانی آگ کو... دین کی اصل اسلام ہے، اس کا ستون نماز ہے، اور اس کی چوٹی جہاد ہے...“",
            narrator = "Abu Abdur-Rahman Mu'adh bin Jabal (RA)",
            source = "Sunan at-Tirmidhi"
        ),
        HadithNawawiItem(
            number = 30,
            titleArabic = "حدود الله وفرائضه",
            titleEnglish = "Limits Set by Allah",
            titleUrdu = "فرائض الہی، حدود اللہ اور خاموشی کی حکمت",
            arabicText = "إِنَّ اللَّهَ تَعَالَى فَرَضَ فَرَائِضَ فَلاَ تُضَيِّعُوهَا، وَحَدَّ حُدُودًا فَلاَ تَعْتَدُوهَا، وَحَرَّمَ أَشْيَاءَ فَلاَ تَنْتَهِكُوهَا، وَسَكَتَ عَنْ أَشْيَاءَ رَحْمَةً لَكُمْ غَيْرَ نِسْيَانٍ فَلاَ تَبْحَثُوا عَنْهَا.",
            englishText = "Verily Allah the Almighty has prescribed obligatory duties, so do not neglect them; He has set limits, so do not transgress them; He has forbidden things, so do not violate them; and He has remained silent about some things out of mercy for you, not forgetfulness, so do not search after them.",
            urduText = "بلاشبہ اللہ تعالیٰ نے کچھ فرائض مقرر کیے ہیں، انہیں ضائع نہ کرو؛ اور کچھ حدیں مقرر کی ہیں، ان سے آگے نہ بڑھو؛ اور کچھ چیزیں حرام کی ہیں، ان کی پامالی نہ کرو؛ اور کچھ چیزوں کے بارے میں بھولے بغیر محض تمہاری رحمت کے لیے خاموشی اختیار کی ہے، پس ان کی کھوج نہ لگاؤ۔",
            narrator = "Abu Tha'labah al-Khushani Jurthum bin Nashir (RA)",
            source = "Al-Daraqutni & Al-Hakim"
        ),
        HadithNawawiItem(
            number = 31,
            titleArabic = "الزهد في الدنيا",
            titleEnglish = "True Asceticism & Zuhd",
            titleUrdu = "دنیا سے بے رغبتی اور لوگوں سے بے نیازی",
            arabicText = "أَتَى النَّبِيَّ صلى الله عليه وسلم رَجُلٌ فَقَالَ: يَا رَسُولَ اللَّهِ دُلَّنِي عَلَى عَمَلٍ إِذَا عَمِلْتُهُ أَحَبَّنِي اللَّهُ وَأَحَبَّنِي النَّاسُ. فَقَالَ: \"ازْهَدْ فِي الدُّنْيَا يُحِبَّكَ اللَّهُ، وَازْهَدْ فِيمَا فِي أَيْدِي النَّاسِ يُحِبَّكَ النَّاسُ\".",
            englishText = "A man came to the Prophet and said: 'O Messenger of Allah, direct me to a deed which, if I perform it, Allah will love me and the people will love me.' The Prophet (PBUH) replied: 'Renounce the world (Zuhd) and Allah will love you; and renounce what people possess and the people will love you.'",
            urduText = "ایک شخص نبی کریم صلی اللہ علیہ وسلم کی خدمت میں حاضر ہوا اور عرض کیا: یا رسول اللہ! مجھے کوئی ایسا عمل بتائیں جسے میں کروں تو اللہ بھی مجھ سے محبت کرے اور لوگ بھی مجھ سے محبت کریں۔ آپ نے فرمایا: ”دنیا سے بے رغبتی (زہد) اختیار کرو اللہ تم سے محبت کرے گا، اور جو کچھ لوگوں کے پاس ہے اس سے بے نیازی اختیار کرو لوگ تم سے محبت کریں گے۔“",
            narrator = "Abu al-Abbas Sahl bin Sa'd al-Sa'idi (RA)",
            source = "Sunan Ibn Majah"
        ),
        HadithNawawiItem(
            number = 32,
            titleArabic = "لا ضرر ولا ضرار",
            titleEnglish = "No Harming nor Reciprocating Harm",
            titleUrdu = "نہ خود نقصان اٹھاؤ اور نہ دوسروں کو دو",
            arabicText = "لاَ ضَرَرَ وَلاَ ضِرَارَ.",
            englishText = "There should be neither harming [of oneself or others] nor reciprocating harm.",
            urduText = "اسلام میں نہ نقصان اٹھانا جائز ہے اور نہ ہی کسی دوسرے کو نقصان پہنچانا جائز ہے۔",
            narrator = "Abu Sa'eed Sa'd bin Malik bin Sinan al-Khudri (RA)",
            source = "Sunan Ibn Majah & Sunan al-Daraqutni"
        ),
        HadithNawawiItem(
            number = 33,
            titleArabic = "البينة على المدعي واليمين على من أنكر",
            titleEnglish = "The Burden of Proof",
            titleUrdu = "دعوے دار کے ذمہ ثبوت اور منکر کے ذمے قسم",
            arabicText = "لَوْ يُعْطَى النَّاسُ بِدَعْوَاهُمْ، لادَّعَى رِجَالٌ أَمْوَالَ قَوْمٍ وَدِمَاءَهُمْ، وَلَكِنَّ الْبَيِّنَةُ عَلَى الْمُدَّعِي، وَالْيَمِينُ عَلَى مَنْ أَنْكَرَ.",
            englishText = "Were people to be given everything they claimed, men would claim the wealth and lives of other people. But the burden of proof is upon the claimant, and the taking of an oath is upon the one who denies.",
            urduText = "اگر لوگوں کو ان کے محض دعوے کی بنیاد پر سب کچھ دے دیا جائے تو لوگ دوسروں کے اموال اور خون کا دعویٰ کرنے لگیں گے۔ لیکن قاعدہ یہ ہے کہ ثبوت پیش کرنا مدعی (دعوے دار) کے ذمے ہے، اور قسم کھانا اس کے ذمے ہے جو انکار کرے۔",
            narrator = "Abu al-Abbas Abdullah bin Abbas (RA)",
            source = "Al-Baihaqi & Al-Bukhari"
        ),
        HadithNawawiItem(
            number = 34,
            titleArabic = "تغيير المنكر باليد واللسان والقلب",
            titleEnglish = "Changing Evil with Hand, Tongue or Heart",
            titleUrdu = "برائی کو ہاتھ, زبان یا دل سے روکنا",
            arabicText = "مَنْ رَأَى مِنْكُمْ مُنْكَرًا فَلْيُغَيِّرْهُ بِيَدِهِ، فَإِنْ لَمْ يَسْتَطِعْ فَبِلِسَانِهِ، فَإِنْ لَمْ يَسْتَطِعْ فَبِقَلْبِهِ، وَذَلِكَ أَضْعَفُ الإِيمَانِ.",
            englishText = "Whosoever of you sees an evil, let him change it with his hand; and if he is not able to do so, then with his tongue; and if he is not able to do so, then with his heart — and that is the weakest of faith.",
            urduText = "تم میں سے جو کوئی برائی دیکھے تو اسے اپنے ہاتھ سے بدل دے (روک دے)، اگر اس کی طاقت نہ ہو تو اپنی زبان سے روکے، اور اگر اس کی بھی طاقت نہ ہو تو اپنے دل میں برا جانے، اور یہ ایمان کا سب سے کمزور درجہ ہے۔",
            narrator = "Abu Sa'eed al-Khudri (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 35,
            titleArabic = "حقوق الأخوة الإسلامية",
            titleEnglish = "Brotherhood & Mutual Rights in Islam",
            titleUrdu = "مسلمانوں کے باہمی حقوق اور حسد و بغض کی ممانعت",
            arabicText = "لاَ تَحَاسَدُوا، وَلاَ تَنَاجَشُوا، وَلاَ تَبَاغَضُوا، وَلاَ تَدَابَرُوا، وَلاَ يَبِعْ بَعْضُكُمْ عَلَى بَيْعِ بَعْضٍ، وَكُونُوا عِبَادَ اللَّهِ إِخْوَانًا. الْمُسْلِمُ أَخُو الْمُسْلِمِ لاَ يَظْلِمُهُ وَلاَ يَخْذُلُهُ وَلاَ يَحْقِرُهُ...",
            englishText = "Do not envy one another, do not inflate prices on one another, do not hate one another, do not turn your backs on one another, and do not undercut one another in business. Be servants of Allah as brothers. A Muslim is a brother of another Muslim: he does not oppress him, mock him, or look down upon him...",
            urduText = "ایک دوسرے سے حسد نہ کرو، سودے پر بولی بڑھا کر دھوکہ نہ دو، ایک دوسرے سے بغض نہ رکھو، ایک دوسرے سے پیٹھ نہ پھیرو، اور تم میں سے کوئی دوسرے کے سودے پر سودا نہ کرے، اور اللہ کے بندے بھائی بھائی بن کر رہو۔ مسلمان مسلمان کا بھائی ہے: وہ نہ اس پر ظلم کرتا ہے، نہ اسے بے یار و مددگار چھوڑتا ہے، اور نہ ہی اسے حقیر سمجھتا ہے...",
            narrator = "Abu Hurayrah (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 36,
            titleArabic = "قضاء حوائج المسلمين وفضل العلم",
            titleEnglish = "Relieving Believers & Seeking Knowledge",
            titleUrdu = "مسلمانوں کی مدد کرنا، پردہ پوشی اور حصول علم کی فضیلت",
            arabicText = "مَنْ نَفَّسَ عَنْ مُؤْمِنٍ كُرْبَةً مِنْ كُرَبِ الدُّنْيَا، نَفَّسَ اللَّهُ عَنْهُ كُرْبَةً مِنْ كُرَبِ يَوْمِ الْقِيَامَةِ، وَمَنْ يَسَّرَ عَلَى مُعْسِرٍ، يَسَّرَ اللَّهُ عَلَيْهِ فِي الدُّنْيَا وَالآخِرَةِ، وَمَنْ سَتَرَ مُسْلِمًا، سَتَرَهُ اللَّهُ فِي الدُّنْيَا وَالآخِرَةِ...",
            englishText = "Whosoever relieves a believer from a worldly distress, Allah will relieve him from a distress on the Day of Resurrection. Whosoever makes things easy for one in difficulty, Allah will make things easy for him in this world and the Hereafter. Whosoever shields a Muslim, Allah will shield him in this world and the Hereafter...",
            urduText = "جس نے کسی مومن کی دنیا کی تکلیفوں میں سے کوئی تکلیف دور کی، اللہ تعالیٰ قیامت کے دن اس کی تکلیفوں میں سے کوئی تکلیف دور فرمائے گا۔ جس نے کسی تنگ دست پر آسانی کی، اللہ تعالیٰ اس پر دنیا اور آخرت میں آسانی فرمائے گا۔ جس نے کسی مسلمان کی پردہ پوشی کی، اللہ تعالیٰ دنیا اور آخرت میں اس کی پردہ پوشی فرمائے گا...",
            narrator = "Abu Hurayrah (RA)",
            source = "Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 37,
            titleArabic = "فضل الله وعدله وكتابة الحسنات والسيئات",
            titleEnglish = "The Grace & Justice of Allah",
            titleUrdu = "نیکیوں اور برائیوں کے لکھے جانے کی تفصیل",
            arabicText = "إِنَّ اللَّهَ كَتَبَ الْحَسَنَاتِ وَالسَّيِّئَاتِ ثُمَّ بَيَّنَ ذَلِكَ: فَمَنْ هَمَّ بِحَسَنَةٍ فَلَمْ يَعْمَلْهَا كَتَبَهَا اللَّهُ عِنْدَهُ حَسَنَةً كَامِلَةً، وَإِنْ هَمَّ بِهَا فَعَمِلَهَا كَتَبَهَا اللَّهُ عِنْدَهُ عَشْرَ حَسَنَاتٍ إِلَى سَبْعِمِائَةِ ضِعْفٍ إِلَى أَضْعَافٍ كَثِيرَةٍ...",
            englishText = "Verily Allah has written down the good deeds and the evil deeds, and then explained it: whosoever intended to do a good deed but did not do it, Allah writes it down with Him as a full good deed; and if he intended it and actually performed it, Allah writes it down with Him as ten good deeds up to seven hundred times or much more...",
            urduText = "بلاشبہ اللہ تعالیٰ نے نیکیوں اور برائیوں کو لکھ دیا ہے اور پھر ان کی وضاحت فرمائی: پس جس نے کسی نیکی کا ارادہ کیا لیکن اس پر عمل نہ کر سکا، تو اللہ اپنے ہاں اس کے لیے ایک مکمل نیکی لکھ دیتا ہے؛ اور اگر اس نے نیکی کا ارادہ کر کے اس پر عمل بھی کر لیا، تو اللہ اپنے ہاں دس گنا سے لے کر سات سو گنا تک یا اس سے بھی کئی گنا زیادہ نیکیاں لکھ دیتا ہے...",
            narrator = "Abu al-Abbas Abdullah bin Abbas bin Abd al-Muttalib (RA)",
            source = "Sahih al-Bukhari & Sahih Muslim"
        ),
        HadithNawawiItem(
            number = 38,
            titleArabic = "أداء الفرائض والنوافل وتحبيب الله للعبد",
            titleEnglish = "Attaining Nearness to Allah",
            titleUrdu = "فرائض اور نوافل کے ذریعے اللہ کا قرب حاصل کرنا",
            arabicText = "إِنَّ اللَّهَ تَعَالَى قَالَ: مَنْ عَادَى لِي وَلِيًّا فَقَدْ آذَنْتُهُ بِالْحَرْبِ، وَمَا تَقَرَّبَ إِلَيَّ عَبْدِي بِشَيْءٍ أَحَبَّ إِلَيَّ مِمَّا افْتَرَضْتُ عَلَيْهِ، وَمَا يَزَالُ عَبْدِي يَتَقَرَّبُ إِلَيَّ بِالنَّوَافِلِ حَتَّى أُحِبَّهُ...",
            englishText = "Allah the Almighty said: 'Whosoever shows enmity to a friend (wali) of Mine, I declare war against him. My servant does not draw near to Me with anything more beloved to Me than the religious duties I have obligated upon him. And My servant continues to draw near to Me with supererogatory prayers until I love him...'",
            urduText = "اللہ تعالیٰ فرماتا ہے: ”جس نے میرے کسی ولی سے دشمنی کی، میں اس کے خلاف جنگ کا اعلان کرتا ہوں۔ اور میرا بندہ کسی ایسی چیز کے ذریعے میرا قرب حاصل نہیں کرتا جو مجھے ان فرائض سے زیادہ محبوب ہو جو میں نے اس پر فرض کیے ہیں۔ اور میرا بندہ نوافل کے ذریعے مسلسل میرا قرب حاصل کرتا رہتا ہے یہاں تک کہ میں اس سے محبت کرنے لگتا ہوں...“",
            narrator = "Abu Hurayrah (RA)",
            source = "Sahih al-Bukhari"
        ),
        HadithNawawiItem(
            number = 39,
            titleArabic = "التجاوز عن الخطأ والنسيان والإكراه",
            titleEnglish = "Mistakes, Forgetfulness & Coercion",
            titleUrdu = "خطا، بھول چوک اور مجبوری کی حالت میں درگزر",
            arabicText = "إِنَّ اللَّهَ تَجَاوَزَ لِي عَنْ أُمَّتِي الْخَطَأَ وَالنِّسْيَانَ وَمَا اسْتُكْرِهُوا عَلَيْهِ.",
            englishText = "Verily Allah has pardoned and excused for my Ummah mistakes, forgetfulness, and that which they are coerced (forced) into doing under duress.",
            urduText = "بلاشبہ اللہ تعالیٰ نے میری خاطر میری امت سے خطا (ناقص فہم کی غلطی)، بھول چوک، اور اس چیز کو معاف فرما دیا ہے جس پر انہیں زبردستی مجبور کیا جائے۔",
            narrator = "Abu al-Abbas Abdullah bin Abbas (RA)",
            source = "Sunan Ibn Majah & Sunan al-Baihaqi"
        ),
        HadithNawawiItem(
            number = 40,
            titleArabic = "كن في الدنيا كأنك غريب",
            titleEnglish = "Be in this World as a Stranger",
            titleUrdu = "دنیا میں اجنبی کی طرح رہو",
            arabicText = "كُنْ فِي الدُّنْيَا كَأَنَّكَ غَرِيبٌ أَوْ عَابِرُ سَبِيلٍ. وَكَانَ ابْنُ عُمَرَ يَقُولُ: إِذَا أَمْسَيْتَ فَلا تَنْتَظِرِ الصَّبَاحَ، وَإِذَا أَصْبَحْتَ فَلا تَنْتَظِرِ الْمَسَاءَ، وَخُذْ مِنْ صِحَّتِكَ لِمَرَضِكَ، وَمِنْ حَيَاتِكَ لِمَوْتِكَ.",
            englishText = "The Messenger of Allah (PBUH) took me by the shoulder and said: 'Be in this world as though you were a stranger or a wayfarer.' Ibn Umar used to say: 'In the evening do not expect to live until the morning, and in the morning do not expect to live until the evening, and take from your health for your illness, and from your life for your death.'",
            urduText = "رسول اللہ صلی اللہ علیہ وسلم نے میرے کندھے پر ہاتھ رکھ کر فرمایا: ”دنیا میں ایسے رہو گویا تم مسافر یا راہ چلتے راہگیر ہو۔“ اور ابن عمر رضی اللہ عنہ فرمایا کرتے تھے: ”جب شام ہو جائے تو صبح کا انتظار نہ کرو، اور جب صبح ہو جائے تو شام کا انتظار نہ کرو، اپنی تندرستی کی حالت میں بیماری کے لیے کچھ کر لو، اور اپنی زندگی میں موت کے لیے تیاری کر لو۔“",
            narrator = "Abu Abdur-Rahman Abdullah bin Umar (RA)",
            source = "Sahih al-Bukhari"
        )
    )
}


