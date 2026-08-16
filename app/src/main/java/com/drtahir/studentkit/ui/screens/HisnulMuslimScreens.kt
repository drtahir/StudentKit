package com.drtahir.studentkit.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
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
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.drtahir.studentkit.viewmodel.StudentKitViewModel

private val WarmBeige = Color(0xFFF4EFE6)

data class HisnulChapter(
    val id: Int,
    val titleArabic: String,
    val titleEnglish: String,
    val titleUrdu: String,
    val description: String,
    val duas: List<HisnulDuaItem>
)

data class HisnulDuaItem(
    val id: Int,
    val arabicText: String,
    val transliteration: String,
    val translationEnglish: String,
    val translationUrdu: String,
    val reference: String,
    val benefit: String,
    val repeatCount: Int = 1
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HisnulMuslimReaderScreen(
    viewModel: StudentKitViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val clipboard = LocalClipboardManager.current
    val chapters = remember { getHisnulChapters() }

    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) } // 0: Browse Chapters, 1: Bookmarks
    var selectedChapter by remember { mutableStateOf<HisnulChapter?>(null) }
    var bookmarkedDuaIds by remember { mutableStateOf(setOf<Int>()) }

    // Repetition state map
    val repCounts = remember { mutableStateMapOf<Int, Int>() }

    // Dynamic Font Scaling
    var arabicFontSize by remember { mutableStateOf(32f) }
    var translationFontSize by remember { mutableStateOf(16f) }

    // Search logic across all chapters and Duas
    val allDuas = remember { chapters.flatMap { ch -> ch.duas.map { d -> Pair(ch, d) } } }
    val searchResults = remember(searchQuery) {
        if (searchQuery.trim().isEmpty()) {
            emptyList()
        } else {
            allDuas.filter { (ch, d) ->
                ch.titleEnglish.contains(searchQuery, ignoreCase = true) ||
                ch.titleUrdu.contains(searchQuery, ignoreCase = true) ||
                d.arabicText.contains(searchQuery) ||
                d.transliteration.contains(searchQuery, ignoreCase = true) ||
                d.translationEnglish.contains(searchQuery, ignoreCase = true) ||
                d.translationUrdu.contains(searchQuery, ignoreCase = true) ||
                d.benefit.contains(searchQuery, ignoreCase = true) ||
                d.reference.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (selectedChapter != null) selectedChapter!!.titleEnglish else "Hisnul Muslim",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = if (selectedChapter != null) selectedChapter!!.titleArabic else "حصن المسلم • Fortress of the Muslim",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (selectedChapter != null) {
                                selectedChapter = null
                            } else {
                                onBack()
                            }
                        },
                        modifier = Modifier.testTag("hisnul_back_button")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (arabicFontSize < 56f) {
                            arabicFontSize += 2f
                            translationFontSize += 1f
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase Text Size", tint = Color.White)
                    }
                    IconButton(onClick = {
                        if (arabicFontSize > 20f) {
                            arabicFontSize -= 2f
                            translationFontSize -= 1f
                        }
                    }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease Text Size", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = EmeraldDark,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(EmeraldDark)
                .padding(paddingValues)
        ) {
            if (selectedChapter == null) {
                // Search bar and Tabs are only visible on main index / book browser
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by Supplication, Topic, Translation...", fontSize = 13.sp, color = Color.White.copy(alpha = 0.6f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("hisnul_search_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldLight,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        focusedLabelColor = EmeraldLight,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White.copy(alpha = 0.7f)) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = Color.White.copy(alpha = 0.7f))
                            }
                        }
                    },
                    singleLine = true
                )

                if (searchQuery.trim().isEmpty()) {
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = EmeraldDark,
                        contentColor = Color.White,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                color = IslamicGold
                            )
                        }
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Browse Chapters", fontWeight = FontWeight.Bold) }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Bookmarks (${bookmarkedDuaIds.size})", fontWeight = FontWeight.Bold)
                                }
                            }
                        )
                    }

                    if (selectedTab == 0) {
                        // Browse Chapters view
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(chapters) { chapter ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedChapter = chapter }
                                        .testTag("hisnul_chapter_card_${chapter.id}"),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(48.dp)
                                                .clip(CircleShape)
                                                .background(EmeraldDark.copy(alpha = 0.1f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = chapter.id.toString(),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = EmeraldDark
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(16.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = chapter.titleEnglish,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black
                                            )
                                            Text(
                                                text = chapter.titleUrdu,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = EmeraldDark
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = chapter.description,
                                                fontSize = 11.sp,
                                                color = Color.Black.copy(alpha = 0.7f),
                                                lineHeight = 14.sp,
                                                maxLines = 2
                                            )
                                        }

                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = "View",
                                            tint = EmeraldDark.copy(alpha = 0.6f)
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        // Bookmarks view
                        val bookmarkedDuas = remember(bookmarkedDuaIds) {
                            allDuas.filter { (_, d) -> bookmarkedDuaIds.contains(d.id) }
                        }

                        if (bookmarkedDuas.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.BookmarkBorder,
                                        contentDescription = null,
                                        modifier = Modifier.size(72.dp),
                                        tint = Color.White.copy(alpha = 0.5f)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "No Bookmarks Yet",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Tap the bookmark icon on any supplication to save it here for fast daily access.",
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White.copy(alpha = 0.8f),
                                        modifier = Modifier.padding(horizontal = 24.dp)
                                    )
                                }
                            }
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(bookmarkedDuas) { (chapter, dua) ->
                                    DuaCard(
                                        dua = dua,
                                        chapterTitle = chapter.titleEnglish,
                                        repCounts = repCounts,
                                        isBookmarked = true,
                                        arabicFontSize = arabicFontSize,
                                        translationFontSize = translationFontSize,
                                        onBookmarkToggle = {
                                            bookmarkedDuaIds = bookmarkedDuaIds - dua.id
                                        }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // Search results view
                    if (searchResults.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = Color.White.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "No results found",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "Try searching with different keywords like 'Morning', 'Safar', 'Istighfar' or specific terms.",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.8f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                Text(
                                    text = "Found ${searchResults.size} matches:",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldLight,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                            items(searchResults) { (chapter, dua) ->
                                DuaCard(
                                    dua = dua,
                                    chapterTitle = chapter.titleEnglish,
                                    repCounts = repCounts,
                                    isBookmarked = bookmarkedDuaIds.contains(dua.id),
                                    arabicFontSize = arabicFontSize,
                                    translationFontSize = translationFontSize,
                                    onBookmarkToggle = {
                                        bookmarkedDuaIds = if (bookmarkedDuaIds.contains(dua.id)) {
                                            bookmarkedDuaIds - dua.id
                                        } else {
                                            bookmarkedDuaIds + dua.id
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                // Chapter detail view
                val chapter = selectedChapter!!
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = EmeraldDark),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = chapter.titleArabic,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = IslamicGold,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Right
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = chapter.titleEnglish,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = chapter.titleUrdu,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = IslamicGold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = chapter.description,
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.9f),
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }

                    itemsIndexed(chapter.duas) { index, dua ->
                        DuaCard(
                            dua = dua,
                            chapterTitle = null,
                            repCounts = repCounts,
                            isBookmarked = bookmarkedDuaIds.contains(dua.id),
                            arabicFontSize = arabicFontSize,
                            translationFontSize = translationFontSize,
                            onBookmarkToggle = {
                                bookmarkedDuaIds = if (bookmarkedDuaIds.contains(dua.id)) {
                                    bookmarkedDuaIds - dua.id
                                } else {
                                    bookmarkedDuaIds + dua.id
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DuaCard(
    dua: HisnulDuaItem,
    chapterTitle: String?,
    repCounts: SnapshotStateMap<Int, Int>,
    isBookmarked: Boolean,
    arabicFontSize: Float,
    translationFontSize: Float,
    onBookmarkToggle: () -> Unit
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val clipboard = LocalClipboardManager.current

    val currentCount = repCounts[dua.id] ?: 0
    val isCompleted = currentCount >= dua.repeatCount

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            1.dp,
            if (isCompleted) EmeraldLight else EmeraldDark.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCompleted) 4.dp else 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hisnul_dua_card_${dua.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (chapterTitle != null) {
                    Text(
                        text = chapterTitle,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldLight
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .background(WarmBeige, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Dua #${dua.id % 100}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        clipboard.setText(AnnotatedString("${dua.arabicText}\n\n${dua.transliteration}\n\n${dua.translationEnglish}"))
                        Toast.makeText(context, "Supplication copied to clipboard", Toast.LENGTH_SHORT).show()
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }) {
                        Icon(Icons.Outlined.ContentCopy, contentDescription = "Copy", tint = EmeraldDark.copy(alpha = 0.7f), modifier = Modifier.size(18.dp))
                    }

                    IconButton(onClick = onBookmarkToggle) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) IslamicGold else EmeraldDark.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Beautiful Calligraphy Style Arabic Text with local size
            Text(
                text = dua.arabicText,
                fontSize = arabicFontSize.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = (arabicFontSize * 1.6).sp,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                fontStyle = FontStyle.Normal
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Transliteration in Italic formatting
            Text(
                text = dua.transliteration,
                fontSize = (translationFontSize - 1.5f).sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF1E88E5), // Blue shade for contrast
                lineHeight = (translationFontSize * 1.35).sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // English Translation
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(top = 2.dp, end = 8.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(IslamicGold)
                )
                Text(
                    text = dua.translationEnglish,
                    fontSize = translationFontSize.sp,
                    color = Color.Black,
                    lineHeight = (translationFontSize * 1.4).sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Urdu Translation with proper styling
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(top = 2.dp, end = 8.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(EmeraldLight)
                )
                Text(
                    text = dua.translationUrdu,
                    fontSize = (translationFontSize + 3f).sp,
                    color = Color.Black,
                    lineHeight = ((translationFontSize + 3f) * 1.5).sp,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Benefit & Source Details
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = WarmBeige),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Benefit: ${dua.benefit}",
                        fontSize = 12.sp,
                        color = Color.Black,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Source: ${dua.reference}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }
            }

            // Interactive Repetition Counter Section
            if (dua.repeatCount > 1) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Repetitions Required: ${dua.repeatCount}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Text(
                            text = if (isCompleted) "Completed! Alhamdulillah." else "Progress: $currentCount / ${dua.repeatCount}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCompleted) EmeraldLight else EmeraldDark
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (currentCount > 0) {
                            IconButton(
                                onClick = {
                                    repCounts[dua.id] = 0
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.Red.copy(alpha = 0.1f), CircleShape)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = Color.Red, modifier = Modifier.size(16.dp))
                            }
                        }

                        Button(
                            onClick = {
                                if (currentCount < dua.repeatCount) {
                                    val nextVal = currentCount + 1
                                    repCounts[dua.id] = nextVal
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    if (nextVal == dua.repeatCount) {
                                        Toast.makeText(context, "Completed! Alhamdulillah.", Toast.LENGTH_SHORT).show()
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                }
                            },
                            enabled = !isCompleted,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCompleted) EmeraldLight else EmeraldDark
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                if (isCompleted) {
                                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                }
                                Text(
                                    text = if (isCompleted) "Done" else "Tap to Count",
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
}

fun getHisnulChapters(): List<HisnulChapter> {
    return listOf(
        HisnulChapter(
            id = 1,
            titleArabic = "أذكار الصباح والمساء",
            titleEnglish = "Morning & Evening Supplications",
            titleUrdu = "صبح اور شام کے اذکار",
            description = "A powerful shield of daily protections and remembrances recommended by the Prophet (PBUH) for peace, safety, and blessings.",
            duas = listOf(
                HisnulDuaItem(
                    id = 101,
                    arabicText = "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
                    transliteration = "Allahumma Anta Rabbi la ilaha illa Anta, khalaqtani wa ana 'abduka, wa ana 'ala 'ahdika wa wa'dika mas-tata'tu, a'udhu bika min sharri ma sana'tu, abu'u laka bi-ni'matika 'alayya, wa abu'u bi-dhanbi faghfir li fa-innahu la yaghfiru-dhunuba illa Anta.",
                    translationEnglish = "O Allah, You are my Lord, none has the right to be worshipped except You. You created me and I am Your servant, and I abide by Your covenant and promise as best as I can. I seek refuge in You from the evil of what I have done. I acknowledge Your blessing upon me and I acknowledge my sin, so forgive me, for none forgives sins except You.",
                    translationUrdu = "اے اللہ! تو ہی میرا رب ہے، تیرے سوا کوئی معبود نہیں، تو نے مجھے پیدا کیا اور میں تیرا بندہ ہوں اور میں اپنی طاقت کے مطابق تیرے عہد اور وعدے پر قائم ہوں۔ میں اپنے کاموں کے شر سے تیری پناہ مانگتا ہوں، تیرے جو احسانات مجھ پر ہیں ان کا اقرار کرتا ہوں اور اپنے گناہوں کا اعتراف کرتا ہوں، پس مجھے بخش دے کیونکہ تیرے سوا کوئی گناہوں کو بخشنے والا نہیں۔",
                    reference = "Sahih al-Bukhari #6306",
                    benefit = "The Master Supplication for Forgiveness (Sayyid al-Istighfar). If recited in the day/night with conviction and one dies, they enter Paradise.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 102,
                    arabicText = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
                    transliteration = "Bismillahil-ladhi la yadurru ma'as-mihi shay'un fil-ardi wa la fis-sama'i wa Huwas-Sami'ul-'Alim.",
                    translationEnglish = "In the Name of Allah, Who with His Name nothing can cause harm in the earth nor in the heaven, and He is the All-Hearing, the All-Knowing.",
                    translationUrdu = "اللہ کے نام کے ساتھ جس کے نام کی برکت سے زمین اور آسمان میں کوئی چیز نقصان نہیں پہنچا سکتی، اور وہ خوب سننے والا اور جاننے والا ہے۔",
                    reference = "Sunan Abi Dawud #5088, Jami` at-Tirmidhi #3388",
                    benefit = "Recited 3 times in the morning and evening. Protects from sudden affliction, accidents, and any harm.",
                    repeatCount = 3
                ),
                HisnulDuaItem(
                    id = 103,
                    arabicText = "اللَّهُمَّ عافِني في بَدَني، اللَّهُمَّ عافِني في سَمْعي، اللَّهُمَّ عافِني في بَصَري، لا إلهَ إلاَّ أَنْتَ",
                    transliteration = "Allahumma 'afini fi badani, Allahumma 'afini fi sam'i, Allahumma 'afini fi basari, la ilaha illa Ant.",
                    translationEnglish = "O Allah, grant health to my body, O Allah, grant health to my hearing, O Allah, grant health to my sight, there is no deity worthy of worship except You.",
                    translationUrdu = "اے اللہ! میرے بدن میں عافیت دے، اے اللہ! میری سماعت میں عافیت دے، اے اللہ! میری بصارت (بینائی) میں عافیت دے، تیرے سوا کوئی معبود نہیں۔",
                    reference = "Sunan Abi Dawud #5090",
                    benefit = "Recited 3 times in the morning and evening to seek physical and spiritual health, well-being and gratitude.",
                    repeatCount = 3
                )
            )
        ),
        HisnulChapter(
            id = 2,
            titleArabic = "دعاء الاستيقاظ من النوم",
            titleEnglish = "Upon Waking Up",
            titleUrdu = "صبح بیدار ہونے کے وقت کی دعا",
            description = "Express gratitude to Allah for returning your soul and granting you another day of life, breath, and opportunity.",
            duas = listOf(
                HisnulDuaItem(
                    id = 201,
                    arabicText = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
                    transliteration = "Alhamdu lillahil-ladhi ahyana ba'da ma amatana wa ilayhin-nushur.",
                    translationEnglish = "All praise is to Allah Who gave us life after having taken it from us (sleep) and unto Him is the resurrection.",
                    translationUrdu = "تمام تعریفیں اللہ کے لیے ہیں جس نے ہمیں مارنے (سلانے) کے بعد زندہ کیا اور اسی کی طرف دوبارہ اٹھ کر جانا ہے۔",
                    reference = "Sahih al-Bukhari #6312",
                    benefit = "Awakens the heart to Allah's power over life, death, and resurrection first thing in the morning.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 202,
                    arabicText = "الْحَمْدُ لِلَّهِ الَّذِي عَافَانِي فِي جَسَدِي، وَرَدَّ عَلَيَّ رُوحِي، وَأَذِنَ لِي بِذِكْرِهِ",
                    transliteration = "Alhamdu lillahil-ladhi 'afani fi jasadi, wa radda 'alayya ruhi, wa adhina li bi-dhikrih.",
                    translationEnglish = "Praise is to Allah Who gave strength to my body, returned my soul to me, and permitted me to remember Him.",
                    translationUrdu = "سب تعریفیں اللہ ہی کے لیے ہیں جس نے میرے جسم کو عافیت (صحت) دی، میری روح مجھ پر لوٹا دی اور مجھے اپنے ذکر کی اجازت (توفیق) بخشی۔",
                    reference = "Jami` at-Tirmidhi #3401",
                    benefit = "Expresses deep gratitude for physical ability, mental consciousness, and spiritual connection upon waking.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 3,
            titleArabic = "دعاء النوم",
            titleEnglish = "Before Sleeping",
            titleUrdu = "سوتے وقت کی دعائیں",
            description = "Trust Allah with your soul and life before entering sleep (the minor death) for safety and peaceful rest.",
            duas = listOf(
                HisnulDuaItem(
                    id = 301,
                    arabicText = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
                    transliteration = "Bismika Allahumma amutu wa ahya.",
                    translationEnglish = "In Your Name, O Allah, I die and I live.",
                    translationUrdu = "اے اللہ! تیرے ہی نام کے ساتھ میں مرتا ہوں اور جیتا ہوں۔",
                    reference = "Sahih al-Bukhari #6312",
                    benefit = "Confirms complete reliance on Allah, who holds life and sleep in His Hand.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 302,
                    arabicText = "بِاسْمِكَ رَبِّي وَضَعْتُ جَنْبِي، وَبِكَ أَرْفَعُهُ، فَإِنْ أَمْسَكْتَ نَفْسِي فَارْحَمْهَا، وَإِنْ أَرْسَلْتَهَا فَاحْفَظْهَا، بِمَا تَحْفَظُ بِهِ عِبَادَكَ الصَّالِحِينَ",
                    transliteration = "Bismika Rabbi wadaktu janbi, wa bika arfa'uh. Fa'in amsakta nafsi farhamha, wa in arsaltaha fahfazha bima tahfazu bihi 'ibadakas-salihin.",
                    translationEnglish = "In Your name my Lord, I lie down, and in Your name, I rise. If You take my soul, have mercy on it, and if You send it back, protect it as You protect Your righteous servants.",
                    translationUrdu = "اے میرے رب! تیرے نام کے ساتھ میں نے اپنا پہلو زمین پر رکھا اور تیرے ہی نام سے میں اسے اٹھاؤں گا۔ پس اگر تو میری جان کو روک لے (موت دے دے) تو اس پر رحم فرما، اور اگر تو اسے واپس بھیج دے (زندگی دے) تو اس کی حفاظت فرما جس طرح تو اپنے نیک بندوں کی حفاظت فرماتا ہے۔",
                    reference = "Sahih al-Bukhari #6320, Sahih Muslim #2714",
                    benefit = "Protects the sleeping body and soul from all spiritual and physical evils throughout the night.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 4,
            titleArabic = "دعاء دخول وخروج المسجد",
            titleEnglish = "Entering & Leaving the Mosque",
            titleUrdu = "مسجد میں داخل ہونے اور نکلنے کی دعائیں",
            description = "Supplications to seek Allah's vast mercy when entering His house, and His bounties when stepping out.",
            duas = listOf(
                HisnulDuaItem(
                    id = 401,
                    arabicText = "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
                    transliteration = "Allahumma-ftah li abwaba rahmatik.",
                    translationEnglish = "O Allah, open for me the gates of Your mercy.",
                    translationUrdu = "اے اللہ! میرے لیے اپنی رحمت کے دروازے کھول دے۔",
                    reference = "Sahih Muslim #713",
                    benefit = "Recited upon stepping into the mosque with the right foot, asking Allah to shower His forgiveness and spiritual mercy.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 402,
                    arabicText = "اللَّهُمَّ إِنِّي أَسْأَلُكَ مِنْ فَضْلِكَ",
                    transliteration = "Allahumma inni as'aluka min fadlik.",
                    translationEnglish = "O Allah, indeed I ask You of Your bounty.",
                    translationUrdu = "اے اللہ! میں تجھ سے تیرے فضل کا سوال کرتا ہوں۔",
                    reference = "Sahih Muslim #713",
                    benefit = "Recited upon leaving the mosque with the left foot, seeking lawful sustenance, blessings, and success in worldly affairs.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 5,
            titleArabic = "الأذكار بعد الصلاة",
            titleEnglish = "Remembrances After Salat",
            titleUrdu = "نماز کے بعد کے اذکار",
            description = "Highly recommended authentic supplications and dhikr to be recited immediately after completing obligatory prayers.",
            duas = listOf(
                HisnulDuaItem(
                    id = 501,
                    arabicText = "أَسْتغْفِرُ الله، أَسْتغْفِرُ الله، أَسْتغْفِرُ الله. اللَّهُمَّ أَنْتَ السَّلامُ، وَمِنْكَ السَّلامُ، تَبَارَكْتَ يَا ذَا الجَلالِ وَالإِكْرَامِ",
                    transliteration = "Astaghfirullah, Astaghfirullah, Astaghfirullah. Allahumma Antas-Salamu wa minkas-salamu, tabarakta ya Dhal-Jalali wal-Ikram.",
                    translationEnglish = "I seek Allah's forgiveness (three times). O Allah, You are Peace and from You comes peace. Blessed are You, O Owner of majesty and honor.",
                    translationUrdu = "میں اللہ سے بخشش مانگتا ہوں (تین بار)۔ اے اللہ! تو سلامتی والا ہے اور تجھی سے سلامتی ہے، تو بابرکت ہے اے بزرگی اور عزت والے۔",
                    reference = "Sahih Muslim #591",
                    benefit = "Recited immediately after finishing Salat to make up for any shortcomings or lack of concentration during the prayer.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 502,
                    arabicText = "لا إلهَ إلاَّ اللَّه وحدَهُ لا شريكَ لهُ، لهُ المُلْكُ ولهُ الحَمْدُ، وهوَ على كُلِّ شَيءٍ قَديرٌ، اللَّهُمَّ لا مانِعَ لِما أَعْطَيْتَ، ولا مُعْطِيَ لِما مَنَعْتَ، ولا يَنْفَعُ ذا الجَدِّ مِنْكَ الجَدُّ",
                    transliteration = "La ilaha illallahu wahdahu la sharika lah, lahul-mulku wa lahul-hamdu, wa Huwa 'ala kulli shay'in Qadir. Allahumma la mani'a lima a'tayta, wa la mu'tiya lima mana'ta, wa la yanfa'u dhal-jaddi minkal-jadd.",
                    translationEnglish = "None has the right to be worshipped except Allah, alone, without partner. To Him belongs all sovereignty and praise, and He is over all things omnipotent. O Allah, none can prevent what You have given, and none can give what You have prevented, and no wealth or majesty can benefit its possessor against You.",
                    translationUrdu = "اللہ کے سوا کوئی معبود نہیں، وہ اکیلا ہے، اس کا کوئی شریک نہیں، بادشاہت اسی کی ہے اور تمام تعریفیں اسی کے لیے ہیں، اور وہ ہر چیز پر قادر ہے۔ اے اللہ! جو کچھ تو عطا کرے اسے کوئی روکنے والا نہیں، اور جسے تو روک لے اسے کوئی دینے والا نہیں، اور کسی مالدار کو اس کا مال تیرے عذاب سے نہیں بچا سکتا۔",
                    reference = "Sahih al-Bukhari #844, Sahih Muslim #593",
                    benefit = "Affirms absolute monotheism, Allah's ultimate control, and the worthlessness of worldly pride or status before Him.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 6,
            titleArabic = "دعاء الكرب والهم",
            titleEnglish = "In Times of Distress & Grief",
            titleUrdu = "پریشانی اور غم دور کرنے کی دعا",
            description = "Prophetic and deeply comforting supplications to recite when facing anxiety, severe difficulties, grief, or distress.",
            duas = listOf(
                HisnulDuaItem(
                    id = 601,
                    arabicText = "لاَ إِلَهَ إِلاَّ اللَّهُ الْعَظِيمُ الْحَلِيمُ، لاَ إِلَهَ إِلاَّ اللَّهُ رَبُّ الْعَرْشِ الْعَظِيمِ، لاَ إِلَهَ إِلاَّ اللَّهُ رَبُّ السَّمَوَاتِ وَرَبُّ الأَرْضِ وَرَبُّ الْعَرْشِ الْكَرِيمِ",
                    transliteration = "La ilaha illallahul-'Azimul-Halim, la ilaha illallahu Rabbul-'Arshil-'Azim, la ilaha illallahu Rabbus-samawati wa Rabbul-ardi wa Rabbul-'Arshil-Karim.",
                    translationEnglish = "There is no deity but Allah, the Great, the Forbearing. There is no deity but Allah, Lord of the Magnificent Throne. There is no deity but Allah, Lord of the heavens and Lord of the earth and Lord of the Noble Throne.",
                    translationUrdu = "اللہ کے سوا کوئی سچا معبود نہیں جو عظمت والا اور بردبار ہے، اللہ کے سوا کوئی سچا معبود نہیں جو عرش عظیم کا رب ہے، اللہ کے سوا کوئی معبود نہیں جو آسمانوں کا رب، زمین کا رب اور عرش کریم کا رب ہے۔",
                    reference = "Sahih al-Bukhari #6346, Sahih Muslim #2730",
                    benefit = "The Supplication of Distress. Prophet Muhammad (PBUH) used to recite this during times of severe hardship and anxiety.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 602,
                    arabicText = "يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ",
                    transliteration = "Ya Hayyu Ya Qayyumu, bi-rahmatika astaghis.",
                    translationEnglish = "O Ever-Living, O Sustainer, by Your mercy I seek help.",
                    translationUrdu = "اے ہمیشہ زندہ رہنے والے! اے کائنات کو سنبھالنے والے! میں تیری ہی رحمت کے ذریعے مدد طلب کرتا ہوں۔",
                    reference = "Jami` at-Tirmidhi #3524",
                    benefit = "Invoking the greatest attributes of Allah (Al-Hayy, Al-Qayyum) to resolve internal and external challenges.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 7,
            titleArabic = "دعاء السفر",
            titleEnglish = "Traveling Supplications",
            titleUrdu = "سفر شروع کرتے وقت کی دعا",
            description = "The comprehensive travel supplication ensuring safety, divine companionship, and protection throughout the journey.",
            duas = listOf(
                HisnulDuaItem(
                    id = 701,
                    arabicText = "اللَّهُمَّ إِنَّا نَسْأَلُكَ فِي سَفَرِنَا هَذَا البِرَّ وَالتَّقْوَى، وَمِنَ العَمَلِ مَا تَرْضَى، اللَّهُمَّ هَوِّنْ عَلَيْنَا سَفَرَنَا هَذَا وَاطْوِ عَنَّا بُعْدَهُ، اللَّهُمَّ أَنْتَ الصَّاحِبُ فِي السَّفَرِ، وَالخَلِيفَةُ فِي الأَهْلِ",
                    transliteration = "Allahumma inna nas'aluka fi safarina hadha al-birra wat-taqwa, wa minal-'amali ma tarda. Allahumma hawwin 'alayna safarana hadha watwi 'anna bu'dah. Allahumma Antas-Sahibu fis-safari, wal-Khalifatu fil-ahli.",
                    translationEnglish = "O Allah, we ask You on this journey of ours for righteousness and piety, and for deeds that please You. O Allah, facilitate this journey of ours, and fold up for us its distance. O Allah, You are the companion on the journey, and the successor over the family.",
                    translationUrdu = "اے اللہ! ہم اپنے اس سفر میں تجھ سے نیکی اور پرہیزگاری کا سوال کرتے ہیں اور ایسے عمل کا جو تجھے پسند ہو۔ اے اللہ! ہم پر اس سفر کو آسان فرما اور اس کی دوری کو سمیٹ دے۔ اے اللہ! تو ہی سفر میں ہمارا ساتھی ہے اور ہمارے پیچھے گھر بار کا نگہبان ہے۔",
                    reference = "Sahih Muslim #1342",
                    benefit = "Brings absolute peace of mind during travel, invoking Allah's blessing and aligning the travel with righteousness and divine security.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 8,
            titleArabic = "الدعاء بعد الأذان",
            titleEnglish = "After the Adhan",
            titleUrdu = "اذان کے بعد کی دعا",
            description = "The beautiful supplication taught by the Prophet (PBUH) to be recited after hearing the call to prayer, ensuring intercession on the Day of Judgment.",
            duas = listOf(
                HisnulDuaItem(
                    id = 801,
                    arabicText = "اللَّهُمَّ رَبَّ هَذِهِ الدَّعْوَةِ التَّامَّةِ، وَالصَّلَاةِ الْقَائِمَةِ، آتِ مُحَمَّدًا الْوَسِيلَةَ وَالْفَضِيلَةَ، وَابْعَثْهُ مَقَامًا مَحْمُودًا الَّذِي وَعَدْتَهُ",
                    transliteration = "Allahumma Rabba hadhihid-da'watit-tammah, was-salatil-qa'imah, ati Muhammadan al-wasilata wal-fadhilah, wab'ath-hu maqaman mahmudan alladhi wa'adtah.",
                    translationEnglish = "O Allah, Lord of this perfect call and established prayer. Grant Muhammad the intercession and favor, and raise him to the honored station You have promised him.",
                    translationUrdu = "اے اللہ! اس مکمل پکار اور قائم ہونے والی نماز کے رب! محمد (ﷺ) کو وسیلہ اور فضیلت عطا فرما، اور انہیں اس مقام محمود پر فائز فرما جس کا تو نے ان سے وعدہ کیا ہے۔",
                    reference = "Sahih al-Bukhari #614",
                    benefit = "Whoever recites this after hearing the Adhan will be granted the intercession of the Prophet (PBUH) on the Day of Resurrection.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 9,
            titleArabic = "دعاء لبس الثوب",
            titleEnglish = "Upon Wearing Clothes",
            titleUrdu = "کپڑے پہنتے وقت کی دعا",
            description = "Express gratitude for the blessing of clothing and protection.",
            duas = listOf(
                HisnulDuaItem(
                    id = 901,
                    arabicText = "الْحَمْدُ لِلَّهِ الَّذِي كَسَانِي هَذَا (الثَّوْبَ) وَرَزَقَنِيهِ مِنْ غَيْرِ حَوْلٍ مِنِّي وَلَا قُوَّةٍ",
                    transliteration = "Alhamdu lillahil-ladhi kasani hadha (ath-thawba) wa razaqanihi min ghayri hawlin minni wa la quwwah.",
                    translationEnglish = "All Praise is to Allah who has clothed me with this (garment) and provided it for me, with no power nor might from myself.",
                    translationUrdu = "سب تعریفیں اللہ کے لیے ہیں جس نے مجھے یہ (کپڑا) پہنایا اور میری کسی طاقت اور قوت کے بغیر مجھے یہ عطا فرمایا۔",
                    reference = "Sunan Abi Dawud #4023",
                    benefit = "Whoever recites this upon wearing a garment, their past and future minor sins are forgiven.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 10,
            titleArabic = "دعاء الخروج من المنزل",
            titleEnglish = "Upon Leaving the Home",
            titleUrdu = "گھر سے نکلتے وقت کی دعا",
            description = "Seek Allah's protection, guidance, and sufficiency when stepping out into the world.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1001,
                    arabicText = "بِسْمِ اللَّهِ، تَوَكَّلْتُ عَلَى اللَّهِ، وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ",
                    transliteration = "Bismillahi, tawakkaltu 'alal-lahi, wa la hawla wa la quwwata illa billah.",
                    translationEnglish = "In the Name of Allah, I have placed my trust in Allah, there is no might and no power except by Allah.",
                    translationUrdu = "اللہ کے نام کے ساتھ، میں نے اللہ پر بھروسہ کیا، اور اللہ کی توفیق کے بغیر نہ گناہوں سے بچنے کی طاقت ہے اور نہ نیکی کرنے کی قوت۔",
                    reference = "Sunan Abi Dawud #5095",
                    benefit = "Upon reciting this, an angel replies: 'You are guided, defended and protected.' And the devils will go far away from him.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 11,
            titleArabic = "دعاء الدخول إلى المنزل",
            titleEnglish = "Upon Entering the Home",
            titleUrdu = "گھر میں داخل ہوتے وقت کی دعا",
            description = "Bring blessings into your home and keep Shaytan out.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1101,
                    arabicText = "بِسْمِ اللَّهِ وَلَجْنَا، وَبِسْمِ اللَّهِ خَرَجْنَا، وَعَلَى رَبِّنَا تَوَكَّلْنَا",
                    transliteration = "Bismillahi walajna, wa bismillahi kharajna, wa 'ala Rabbina tawakkalna.",
                    translationEnglish = "In the Name of Allah we enter, in the Name of Allah we leave, and upon our Lord we depend (then say As-Salamu 'Alaykum to those present).",
                    translationUrdu = "اللہ کے نام کے ساتھ ہم داخل ہوئے، اور اللہ کے نام کے ساتھ ہم نکلے، اور ہم نے اپنے رب ہی پر بھروسہ کیا۔",
                    reference = "Sunan Abi Dawud #5096",
                    benefit = "Ensures that Allah is remembered inside the house, bringing peace and keeping evil influences away.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 12,
            titleArabic = "أدعية الخلاء",
            titleEnglish = "Restroom Supplications",
            titleUrdu = "بیت الخلاء کی دعائیں",
            description = "Protection before entering and seeking forgiveness upon leaving the restroom.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1201,
                    arabicText = "بِسْمِ اللَّهِ، اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَائِثِ",
                    transliteration = "Bismillahi, Allahumma inni a'udhu bika minal-khubuthi wal-khaba'ith.",
                    translationEnglish = "In the Name of Allah. O Allah, I take refuge with you from all evil and evil-doers.",
                    translationUrdu = "اللہ کے نام سے، اے اللہ! میں خبیث جنوں اور خبیث جننیوں سے تیری پناہ مانگتا ہوں۔",
                    reference = "Sahih al-Bukhari #142",
                    benefit = "Recited before entering. Protects one from the evil jinn and Shayateen that reside in impure places.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 1202,
                    arabicText = "غُفْرَانَكَ",
                    transliteration = "Ghufranaka.",
                    translationEnglish = "I ask You (Allah) for forgiveness.",
                    translationUrdu = "اے اللہ! میں تجھ سے بخشش طلب کرتا ہوں۔",
                    reference = "Sunan Abi Dawud #30",
                    benefit = "Recited upon stepping out. Acknowledges gratitude for the relief and asks forgiveness for the time spent without remembering Allah.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 13,
            titleArabic = "أذكار الوضوء",
            titleEnglish = "Ablution (Wudu)",
            titleUrdu = "وضو کے اذکار",
            description = "Supplications to recite before and after performing Wudu.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1301,
                    arabicText = "بِسْمِ اللَّهِ",
                    transliteration = "Bismillah.",
                    translationEnglish = "In the Name of Allah.",
                    translationUrdu = "اللہ کے نام سے۔",
                    reference = "Sunan Abi Dawud #101",
                    benefit = "Recited before starting Wudu. Ensures the ablution is blessed.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 1302,
                    arabicText = "أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ. اللَّهُمَّ اجْعَلْنِي مِنَ التَّوَّابِينَ، وَاجْعَلْنِي مِنَ الْمُتَطَهِّرِينَ",
                    transliteration = "Ashhadu an la ilaha illallahu wahdahu la sharika lahu, wa ashhadu anna Muhammadan 'abduhu wa rasuluhu. Allahummaj'alni minat-tawwabina waj'alni minal-mutatahhireen.",
                    translationEnglish = "I bear witness that none has the right to be worshipped but Allah alone, Who has no partner; and I bear witness that Muhammad is His slave and His Messenger. O Allah, make me among those who turn to You in repentance, and make me among those who are purified.",
                    translationUrdu = "میں گواہی دیتا ہوں کہ اللہ کے سوا کوئی سچا معبود نہیں، وہ اکیلا ہے اس کا کوئی شریک نہیں، اور میں گواہی دیتا ہوں کہ محمد (ﷺ) اس کے بندے اور رسول ہیں۔ اے اللہ! مجھے توبہ کرنے والوں میں سے بنا دے، اور مجھے پاک صاف رہنے والوں میں سے بنا دے۔",
                    reference = "Sahih Muslim #234 & Sunan at-Tirmidhi #55",
                    benefit = "Whoever says this after Wudu, the eight gates of Paradise are opened for him to enter through whichever he pleases.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 14,
            titleArabic = "دعاء الذهاب إلى المسجد",
            titleEnglish = "Going to the Mosque",
            titleUrdu = "مسجد جاتے وقت کی دعا",
            description = "A comprehensive supplication asking for light and guidance while walking to the mosque.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1401,
                    arabicText = "اللَّهُمَّ اجْعَلْ فِي قَلْبِي نُورًا، وَفِي لِسَانِي نُورًا، وَفِي سَمْعِي نُورًا، وَفِي بَصَرِي نُورًا، وَمِنْ فَوْقِي نُورًا، وَمِنْ تَحْتِي نُورًا، وَعَنْ يَمِينِي نُورًا، وَعَنْ شِمَالِي نُورًا، وَمِنْ أَمَامِي نُورًا، وَمِنْ خَلْفِي نُورًا، وَاجْعَلْ فِي نَفْسِي نُورًا، وَأَعْظِمْ لِي نُورًا...",
                    transliteration = "Allahummaj'al fi qalbi nuran, wa fi lisani nuran, wa fi sam'i nuran, wa fi basari nuran, wa min fawqi nuran, wa min tahti nuran, wa 'an yamini nuran, wa 'an shimali nuran, wa min amami nuran, wa min khalfi nuran, waj'al fi nafsi nuran, wa a'dhim li nuran...",
                    translationEnglish = "O Allah, place light in my heart, light on my tongue, light in my hearing, light in my sight, light above me, light below me, light on my right, light on my left, light in front of me, light behind me, place light in my soul, and make light abundant for me...",
                    translationUrdu = "اے اللہ! میرے دل میں نور پیدا فرما، اور میری زبان میں نور، اور میری سماعت میں نور، اور میری بصارت میں نور، اور میرے اوپر سے نور، اور میرے نیچے سے نور، اور میرے دائیں طرف سے نور، اور میرے بائیں طرف سے نور، اور میرے آگے نور، اور میرے پیچھے نور پیدا فرما، اور میری جان میں نور بھر دے، اور میرے لیے نور کو بہت زیادہ کر دے۔",
                    reference = "Sahih Muslim #763",
                    benefit = "Illuminates every aspect of a believer's physical and spiritual being with the divine light of Allah.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 15,
            titleArabic = "دعاء دخول المسجد",
            titleEnglish = "Entering the Mosque",
            titleUrdu = "مسجد میں داخل ہونے کی دعا",
            description = "Seeking Allah's mercy when entering His House.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1501,
                    arabicText = "أَعُوذُ بِاللَّهِ الْعَظِيمِ، وَبِوَجْهِهِ الْكَرِيمِ، وَسُلْطَانِهِ الْقَدِيمِ، مِنَ الشَّيْطَانِ الرَّجِيمِ. بِسْمِ اللَّهِ، وَالصَّلَاةُ وَالسَّلَامُ عَلَى رَسُولِ اللَّهِ. اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
                    transliteration = "A'udhu billahil-'Adheem, wa bi-wajhihil-kareem, wa sultanihil-qadeem, minash-Shaytanir-rajeem. Bismillahi, was-salatu was-salamu 'ala Rasulillah. Allahummaftah li abwaba rahmatik.",
                    translationEnglish = "I take refuge with Allah, The Supreme and with His Noble Face, and His eternal authority from the accursed devil. In the name of Allah, and prayers and peace be upon the Messenger of Allah. O Allah, open the gates of Your mercy for me.",
                    translationUrdu = "میں عظمت والے اللہ کی، اس کی ذاتِ کریم کی اور اس کی قدیم بادشاہی کی، مردود شیطان سے پناہ مانگتا ہوں۔ اللہ کے نام کے ساتھ، اور درود و سلام ہو رسول اللہ (ﷺ) پر۔ اے اللہ! میرے لیے اپنی رحمت کے دروازے کھول دے۔",
                    reference = "Sunan Abi Dawud #466 & Sahih Muslim #713",
                    benefit = "Secures protection from Shaytan for the rest of the day and invites Allah's immense mercy.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 16,
            titleArabic = "دعاء الخروج من المسجد",
            titleEnglish = "Leaving the Mosque",
            titleUrdu = "مسجد سے نکلنے کی دعا",
            description = "Seeking Allah's bounty upon exiting the mosque.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1601,
                    arabicText = "بِسْمِ اللَّهِ وَالصَّلَاةُ وَالسَّلَامُ عَلَى رَسُولِ اللَّهِ، اللَّهُمَّ إِنِّي أَسْأَلُكَ مِنْ فَضْلِكَ، اللَّهُمَّ اعْصِمْنِي مِنَ الشَّيْطَانِ الرَّجِيمِ",
                    transliteration = "Bismillahi was-salatu was-salamu 'ala Rasulillah, Allahumma inni as'aluka min fadlik, Allahumma'simni minash-Shaytanir-rajeem.",
                    translationEnglish = "In the name of Allah, and prayers and peace be upon the Messenger of Allah. O Allah, I ask You from Your favor. O Allah, guard me from the accursed devil.",
                    translationUrdu = "اللہ کے نام کے ساتھ، اور درود و سلام ہو رسول اللہ (ﷺ) پر۔ اے اللہ! میں تجھ سے تیرے فضل کا سوال کرتا ہوں۔ اے اللہ! مجھے شیطان مردود سے محفوظ رکھ۔",
                    reference = "Sahih Muslim #713 & Sunan Ibn Majah #773",
                    benefit = "Upon leaving, the believer asks for Allah's favor and provision in worldly affairs and protection from Shaytan's traps.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 17,
            titleArabic = "ما يقال عند الطعام",
            titleEnglish = "Before Eating",
            titleUrdu = "کھانا کھانے سے پہلے کی دعا",
            description = "Invoking Allah's name for blessings in food.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1701,
                    arabicText = "بِسْمِ اللَّهِ. (فَإِنْ نَسِيَ فِي أَوَّلِهِ فَلْيَقُلْ): بِسْمِ اللَّهِ فِي أَوَّلِهِ وَآخِرِهِ",
                    transliteration = "Bismillah. (And if one forgets to say it at the beginning, he should say): Bismillahi fi awwalihi wa akhirihi.",
                    translationEnglish = "In the name of Allah. (If one forgets at the beginning, they should say): In the name of Allah, in its beginning and its end.",
                    translationUrdu = "اللہ کے نام سے۔ (اور اگر شروع میں پڑھنا بھول جائے تو کہے): اللہ کے نام سے اس کے شروع اور آخر میں۔",
                    reference = "Sunan Abi Dawud #3767",
                    benefit = "Prevents Shaytan from sharing in the meal and ensures the food is blessed.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 18,
            titleArabic = "ما يقال بعد الفراغ من الطعام",
            titleEnglish = "After Eating",
            titleUrdu = "کھانا کھانے کے بعد کی دعا",
            description = "Expressing gratitude to Allah for providing sustenance.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1801,
                    arabicText = "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنِي هَذَا، وَرَزَقَنِيهِ، مِنْ غَيْرِ حَوْلٍ مِنِّي وَلَا قُوَّةٍ",
                    transliteration = "Alhamdulillahil-ladhi at'amani hadha, wa razaqanihi, min ghayri hawlin minni wa la quwwah.",
                    translationEnglish = "All praise is to Allah Who has fed me this and provided it for me without any strength or power on my part.",
                    translationUrdu = "سب تعریفیں اللہ کے لیے ہیں جس نے مجھے یہ کھلایا، اور میری کسی طاقت اور قوت کے بغیر مجھے یہ رزق عطا فرمایا۔",
                    reference = "Sunan at-Tirmidhi #3458",
                    benefit = "Whoever says this after eating, their past minor sins will be forgiven.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 19,
            titleArabic = "دعاء السفر",
            titleEnglish = "Travel Supplication (Safar)",
            titleUrdu = "سفر کی دعا",
            description = "Seeking Allah's protection and ease during a journey.",
            duas = listOf(
                HisnulDuaItem(
                    id = 1901,
                    arabicText = "اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، ﴿سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ * وَإِنَّا إِلَى رَبِّنَا لَمُنْقَلِبُونَ﴾ اللَّهُمَّ إِنَّا نَسْأَلُكَ فِي سَفَرِنَا هَذَا الْبِرَّ وَالتَّقْوَى، وَمِنَ الْعَمَلِ مَا تَرْضَى...",
                    transliteration = "Allahu Akbar, Allahu Akbar, Allahu Akbar, 'Subhanal-ladhi sakhkhara lana hadha wa ma kunna lahu muqrineen, wa inna ila Rabbina lamunqaliboon.' Allahumma inna nas'aluka fi safarina hadhal-birra wat-taqwa, wa minal-'amali ma tardha...",
                    translationEnglish = "Allah is the Greatest (3 times). 'Glory to Him Who has subjected this to us, and we could not have otherwise subdued it. And indeed, to our Lord we will surely return.' O Allah, we ask You in this journey of ours for righteousness and piety, and for deeds which are pleasing to You...",
                    translationUrdu = "اللہ سب سے بڑا ہے (تین بار)۔ پاک ہے وہ ذات جس نے اس (سواری) کو ہمارے تابع کر دیا، ورنہ ہم اسے قابو میں لانے والے نہ تھے۔ اور بے شک ہم اپنے رب ہی کی طرف لوٹنے والے ہیں۔ اے اللہ! ہم تجھ سے اپنے اس سفر میں نیکی اور پرہیزگاری کا، اور ایسے عمل کا سوال کرتے ہیں جس سے تو راضی ہو...",
                    reference = "Sahih Muslim #1342",
                    benefit = "Brings safety, righteousness, and protection from the hardships of travel.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 20,
            titleArabic = "دعاء المريض",
            titleEnglish = "Visiting the Sick",
            titleUrdu = "بیمار کی عیادت کی دعا",
            description = "Supplications for a speedy recovery and spiritual cleansing for the ill.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2001,
                    arabicText = "لَا بَأْسَ طَهُورٌ إِنْ شَاءَ اللَّهُ",
                    transliteration = "La ba's, tahoorun in shaa' Allah.",
                    translationEnglish = "No need to worry. It is a cleansing (from sins), if Allah wills.",
                    translationUrdu = "کوئی حرج نہیں، اگر اللہ نے چاہا تو یہ (بیماری گناہوں سے) پاک کرنے والی ہے۔",
                    reference = "Sahih al-Bukhari #3616",
                    benefit = "A comforting prayer that reminds the sick that illness is a means of expiation for sins.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 2002,
                    arabicText = "أَسْأَلُ اللَّهَ الْعَظِيمَ، رَبَّ الْعَرْشِ الْعَظِيمِ أَنْ يَشْفِيَكَ",
                    transliteration = "As'alullahal-'Adheem, Rabbal-'Arshil-'Adheem, an yashfiyak.",
                    translationEnglish = "I ask Allah the Supreme, Lord of the magnificent throne to cure you.",
                    translationUrdu = "میں عظمت والے اللہ، جو عظمت والے عرش کا رب ہے، سے سوال کرتا ہوں کہ وہ تجھے شفا دے۔",
                    reference = "Sunan Abi Dawud #3106",
                    benefit = "If recited seven times for a sick person whose time of death has not yet come, Allah will cure them.",
                    repeatCount = 7
                )
            )
        ),
        HisnulChapter(
            id = 21,
            titleArabic = "دعاء الغضب",
            titleEnglish = "When Angry",
            titleUrdu = "غصہ کے وقت کی دعا",
            description = "Seeking refuge from Shaytan when overcoming anger.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2101,
                    arabicText = "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطَانِ الرَّجِيمِ",
                    transliteration = "A'udhu billahi minash-Shaytanir-rajeem.",
                    translationEnglish = "I seek refuge in Allah from the accursed devil.",
                    translationUrdu = "میں شیطان مردود سے اللہ کی پناہ مانگتا ہوں۔",
                    reference = "Sahih al-Bukhari #6115",
                    benefit = "Protects the person from the harmful effects of anger and extinguishes the fire of Shaytan.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 22,
            titleArabic = "كفارة المجلس",
            titleEnglish = "Expiation of Assembly (Kaffaratul Majlis)",
            titleUrdu = "مجلس کے اختتام کی دعا",
            description = "Forgiveness for any vain talk or mistakes made during a gathering.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2201,
                    arabicText = "سُبْحَانَكَ اللَّهُمَّ وَبِحَمْدِكَ، أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا أَنْتَ، أَسْتَغْفِرُكَ وَأَتُوبُ إِلَيْكَ",
                    transliteration = "Subhanakal-lahumma wa bihamdika, ashhadu an la ilaha illa Anta, astaghfiruka wa atubu ilayk.",
                    translationEnglish = "Glory is to You, O Allah, and praise is to You. I bear witness that there is none worthy of worship but You. I seek Your forgiveness and repent to You.",
                    translationUrdu = "اے اللہ! تو پاک ہے اور تیری ہی تعریف ہے، میں گواہی دیتا ہوں کہ تیرے سوا کوئی سچا معبود نہیں، میں تجھ سے بخشش مانگتا ہوں اور تیری طرف توبہ کرتا ہوں۔",
                    reference = "Sunan at-Tirmidhi #3433",
                    benefit = "Wipes away any minor sins or idle talk that took place during the sitting.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 23,
            titleArabic = "ما يقال عند نزول المطر",
            titleEnglish = "When it Rains",
            titleUrdu = "بارش کے وقت کی دعا",
            description = "Asking for the rain to be beneficial.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2301,
                    arabicText = "اللَّهُمَّ صَيِّبًا نَافِعًا",
                    transliteration = "Allahumma sayyiban nafi'an.",
                    translationEnglish = "O Allah, (bring) beneficial rain clouds.",
                    translationUrdu = "اے اللہ! نفع بخش بارش برسا۔",
                    reference = "Sahih al-Bukhari #1032",
                    benefit = "A beautiful sunnah to welcome the mercy of Allah and ask for the rain to bring goodness.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 24,
            titleArabic = "دعاء الاستخارة",
            titleEnglish = "Prayer of Seeking Guidance (Istikhara)",
            titleUrdu = "استخارہ کی دعا",
            description = "The ultimate supplication for seeking Allah's guidance when making an important decision.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2401,
                    arabicText = "اللَّهُمَّ إِنِّي أَسْتَخِيرُكَ بِعِلْمِكَ، وَأَسْتَقْدِرُكَ بِقُدْرَتِكَ، وَأَسْأَلُكَ مِنْ فَضْلِكَ الْعَظِيمِ، فَإِنَّكَ تَقْدِرُ وَلَا أَقْدِرُ، وَتَعْلَمُ وَلَا أَعْلَمُ، وَأَنْتَ عَلَّامُ الْغُيُوبِ...",
                    transliteration = "Allahumma inni astakhiruka bi'ilmika, wa astaqdiruka biqudratika, wa as'aluka min fadlikal-'adheem, fa-innaka taqdiru wa la aqdiru, wa ta'lamu wa la a'lamu, wa Anta 'Allamul-ghuyoob...",
                    translationEnglish = "O Allah, I seek Your counsel by Your knowledge and I seek power by Your might, and I ask You from Your immense favor. For verily You are able while I am not, and You know while I do not, and You are the Knower of the unseen...",
                    translationUrdu = "اے اللہ! میں تیرے علم کے ذریعے تجھ سے بھلائی طلب کرتا ہوں، اور تیری قدرت کے ذریعے تجھ سے طاقت مانگتا ہوں، اور تجھ سے تیرے فضلِ عظیم کا سوال کرتا ہوں، کیونکہ تو قدرت رکھتا ہے اور میں نہیں، تو جانتا ہے اور میں نہیں، اور تو غیبوں کا خوب جاننے والا ہے...",
                    reference = "Sahih al-Bukhari #1162",
                    benefit = "After praying two Rakat, reciting this Dua ensures that Allah will guide the believer to what is best in this world and the Hereafter.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 25,
            titleArabic = "دعاء الكرب",
            titleEnglish = "In Times of Distress",
            titleUrdu = "پریشانی کے وقت کی دعا",
            description = "Supplications for anxiety, sorrow, and difficult times.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2501,
                    arabicText = "لَا إِلَهَ إِلَّا اللَّهُ الْعَظِيمُ الْحَلِيمُ، لَا إِلَهَ إِلَّا اللَّهُ رَبُّ الْعَرْشِ الْعَظِيمِ، لَا إِلَهَ إِلَّا اللَّهُ رَبُّ السَّمَاوَاتِ وَرَبُّ الْأَرْضِ وَرَبُّ الْعَرْشِ الْكَرِيمِ",
                    transliteration = "La ilaha illallahul-'Adheemul-Haleem, la ilaha illallahu Rabbul-'Arshil-'Adheem, la ilaha illallahu Rabbus-samawati wa Rabbul-ardi wa Rabbul-'Arshil-kareem.",
                    translationEnglish = "There is none worthy of worship but Allah the Mighty, the Forbearing. There is none worthy of worship but Allah, Lord of the Magnificent Throne. There is none worthy of worship but Allah, Lord of the heavens and Lord of the earth, and Lord of the Noble Throne.",
                    translationUrdu = "عظمت والے، بردبار اللہ کے سوا کوئی سچا معبود نہیں، عظمت والے عرش کے رب اللہ کے سوا کوئی سچا معبود نہیں، آسمانوں اور زمین کے رب اور عزت والے عرش کے رب اللہ کے سوا کوئی سچا معبود نہیں۔",
                    reference = "Sahih al-Bukhari #6346",
                    benefit = "A powerful dua of the Prophet (PBUH) during times of distress, affirming Allah's greatness and lordship.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 2502,
                    arabicText = "يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ",
                    transliteration = "Ya Hayyu Ya Qayyoom, birahmatika astagheeth.",
                    translationEnglish = "O Ever Living, O Self-Subsisting and Supporter of all, by Your mercy I seek assistance.",
                    translationUrdu = "اے ہمیشہ زندہ رہنے والے، اے سب کو قائم رکھنے والے! میں تیری ہی رحمت کے ذریعے فریاد کرتا ہوں۔",
                    reference = "Sunan at-Tirmidhi #3524",
                    benefit = "The Prophet (PBUH) would frequently make this supplication when a matter troubled him.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 26,
            titleArabic = "دعاء قضاء الدين",
            titleEnglish = "Settling a Debt",
            titleUrdu = "قرض کی ادائیگی کی دعا",
            description = "Asking Allah for help in paying off debts and relief from financial worries.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2601,
                    arabicText = "اللَّهُمَّ اكْفِنِي بِحَلَالِكَ عَنْ حَرَامِكَ، وَأَغْنِنِي بِفَضْلِكَ عَمَّنْ سِوَاكَ",
                    transliteration = "Allahummak-fini bihalalika 'an haramik, wa aghnini bifadlika 'amman siwak.",
                    translationEnglish = "O Allah, suffice me with what You have allowed instead of what You have forbidden, and make me independent of all others besides You.",
                    translationUrdu = "اے اللہ! مجھے اپنے حلال کے ذریعے حرام سے بچا لے، اور اپنے فضل کے ذریعے اپنے سوا ہر ایک سے بے نیاز کر دے۔",
                    reference = "Sunan at-Tirmidhi #3563",
                    benefit = "If one has a debt as huge as a mountain, Allah will facilitate its payment.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 27,
            titleArabic = "ما يقول من خاف قوما",
            titleEnglish = "Fear of People",
            titleUrdu = "دشمن یا کسی قوم کے ڈر کی دعا",
            description = "Seeking Allah's protection against those who might harm you.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2701,
                    arabicText = "اللَّهُمَّ إِنَّا نَجْعَلُكَ فِي نُحُورِهِمْ، وَنَعُوذُ بِكَ مِنْ شُرُورِهِمْ",
                    transliteration = "Allahumma inna naj'aluka fi nuhoorihim, wa na'udhu bika min shuroorihim.",
                    translationEnglish = "O Allah, we place You before them and we seek refuge in You from their evil.",
                    translationUrdu = "اے اللہ! ہم تجھے ان کے مقابلے میں کرتے ہیں اور ان کی شرارتوں سے تیری پناہ مانگتے ہیں۔",
                    reference = "Sunan Abi Dawud #1537",
                    benefit = "Creates a divine shield between the believer and their potential oppressors.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 28,
            titleArabic = "دعاء من استصعب عليه أمر",
            titleEnglish = "When Matters Become Difficult",
            titleUrdu = "کسی مشکل کام کے آسان ہونے کی دعا",
            description = "Turning to Allah to make any challenging task easy.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2801,
                    arabicText = "اللَّهُمَّ لَا سَهْلَ إِلَّا مَا جَعَلْتَهُ سَهْلًا، وَأَنْتَ تَجْعَلُ الْحَزْنَ إِذَا شِئْتَ سَهْلًا",
                    transliteration = "Allahumma la sahla illa ma ja'altahu sahlan, wa Anta taj'alul-hazna idha shi'ta sahlan.",
                    translationEnglish = "O Allah, there is no ease except in that which You have made easy, and You make the difficulty, if You wish, easy.",
                    translationUrdu = "اے اللہ! کوئی کام آسان نہیں مگر جسے تو آسان کر دے، اور تو جب چاہے مشکل کو بھی آسان کر دیتا ہے۔",
                    reference = "Sahih Ibn Hibban #974",
                    benefit = "A powerful dua to recite before exams, interviews, or any challenging life situation.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 29,
            titleArabic = "تهنئة المولود له وجوابه",
            titleEnglish = "Congratulating on a Newborn",
            titleUrdu = "نومولود کی مبارکباد کی دعا",
            description = "What to say to parents upon the birth of a child, and their reply.",
            duas = listOf(
                HisnulDuaItem(
                    id = 2901,
                    arabicText = "بَارَكَ اللَّهُ لَكَ فِي الْمَوْهُوبِ لَكَ، وَشَكَرْتَ الْوَاهِبَ، وَبَلَغَ أَشُدَّهُ، وَرُزِقْتَ بِرَّهُ",
                    transliteration = "Barakallahu laka fil-mawhoobi lak, wa shacartal-wahib, wa balagha ashuddahu, wa ruziqta birrah.",
                    translationEnglish = "May Allah bless you in His gift to you, may you give thanks to the Giver of this gift, may the child reach the maturity of years, and may you be granted its righteousness.",
                    translationUrdu = "اللہ تجھے اس عطیہ میں برکت دے، تو عطا کرنے والے کا شکر ادا کرے، وہ اپنی جوانی کو پہنچے اور تجھے اس کی نیکی نصیب ہو۔",
                    reference = "Al-Adhkar by An-Nawawi",
                    benefit = "A comprehensive prayer for the new child's physical and spiritual growth, and the parents' gratitude.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 30,
            titleArabic = "ما يعوذ به الأولاد",
            titleEnglish = "Protecting Children",
            titleUrdu = "بچوں کو پناہ میں دینے کی دعا",
            description = "Seeking refuge in Allah for children, just as Ibrahim (AS) did for Ismail and Ishaq.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3001,
                    arabicText = "أُعِيذُكُمَا بِكَلِمَاتِ اللَّهِ التَّامَّةِ، مِنْ كُلِّ شَيْطَانٍ وَهَامَّةٍ، وَمِنْ كُلِّ عَيْنٍ لَامَّةٍ",
                    transliteration = "U'idhukuma bikalimatil-lahit-tammah, min kulli shaytanin wa hammah, wa min kulli 'aynin lammah.",
                    translationEnglish = "I seek refuge for you both in the Perfect Words of Allah, from every devil and every poisonous pest, and from every evil, harmful, envious eye.",
                    translationUrdu = "میں تم دونوں کو اللہ کے مکمل کلمات کی پناہ میں دیتا ہوں، ہر شیطان اور زہریلے جانور سے، اور ہر لگنے والی (بری) نظر سے۔",
                    reference = "Sahih al-Bukhari #3371",
                    benefit = "The Prophet (PBUH) used to seek refuge for Hasan and Husain with this dua.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 31,
            titleArabic = "دعاء زيارة القبور",
            titleEnglish = "Visiting Graves",
            titleUrdu = "قبرستان جانے کی دعا",
            description = "Praying for the deceased and remembering one's own mortality.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3101,
                    arabicText = "السَّلَامُ عَلَيْكُمْ أَهْلَ الدِّيَارِ، مِنَ الْمُؤْمِنِينَ وَالْمُسْلِمِينَ، وَإِنَّا إِنْ شَاءَ اللَّهُ بِكُمْ لَاحِقُونَ، نَسْأَلُ اللَّهَ لَنَا وَلَكُمُ الْعَافِيَةَ",
                    transliteration = "As-salamu 'alaykum ahlal-diyar, minal-mu'mineena wal-muslimeen, wa inna in shaa' Allahu bikum lahiqoon, nas'alullaha lana wa lakumul-'afiyah.",
                    translationEnglish = "Peace be upon you all, O inhabitants of the graves, amongst the believers and the Muslims. Indeed we will, if Allah wills, be united with you. We ask Allah for well-being for us and you.",
                    translationUrdu = "اے ان گھروں (قبروں) میں رہنے والے مومنو اور مسلمانو! تم پر سلامتی ہو۔ اگر اللہ نے چاہا تو ہم بھی تم سے ملنے والے ہیں، ہم اللہ سے اپنے لیے اور تمہارے لیے عافیت مانگتے ہیں۔",
                    reference = "Sahih Muslim #975",
                    benefit = "Brings peace to the souls in the graves and serves as a powerful reminder of the Hereafter for the living.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 32,
            titleArabic = "دعاء التعزية",
            titleEnglish = "Condolence",
            titleUrdu = "تعزیت کی دعا",
            description = "Offering comfort to those who have lost a loved one.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3201,
                    arabicText = "إِنَّ لِلَّهِ مَا أَخَذَ، وَلَهُ مَا أَعْطَى، وَكُلُّ شَيْءٍ عِنْدَهُ بِأَجَلٍ مُسَمَّى... فَلْتَصْبِرْ وَلْتَحْتَسِبْ",
                    transliteration = "Inna lillahi ma akhadh, wa lahu ma a'ta, wa kullu shay'in 'indahu bi'ajalin musamma... faltasbir waltah-tasib.",
                    translationEnglish = "Surely, Allah takes what is His, and what He gives is His, and to all things He has appointed a time... so have patience and be rewarded.",
                    translationUrdu = "بیشک جو کچھ اللہ نے لیا وہ اسی کا تھا، اور جو کچھ اس نے دیا وہ بھی اسی کا ہے، اور اس کے ہاں ہر چیز کا ایک وقت مقرر ہے... پس صبر کرو اور ثواب کی امید رکھو۔",
                    reference = "Sahih al-Bukhari #1284",
                    benefit = "Provides profound perspective that everything belongs to Allah and encourages patience for ultimate reward.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 33,
            titleArabic = "دعاء الريح",
            titleEnglish = "When the Wind Blows",
            titleUrdu = "آندھی کے وقت کی دعا",
            description = "Seeking the good of the wind and refuge from its harm.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3301,
                    arabicText = "اللَّهُمَّ إِنِّي أَسْأَلُكَ خَيْرَهَا، وَأَعُوذُ بِكَ مِنْ شَرِّهَا",
                    transliteration = "Allahumma inni as'aluka khayraha, wa a'udhu bika min sharriha.",
                    translationEnglish = "O Allah, I ask You for its goodness and I seek refuge in You from its evil.",
                    translationUrdu = "اے اللہ! میں تجھ سے اس (ہوا) کی بھلائی مانگتا ہوں اور اس کی برائی سے تیری پناہ مانگتا ہوں۔",
                    reference = "Sunan Abi Dawud #5097",
                    benefit = "A reminder that all natural phenomena are under Allah's control.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 34,
            titleArabic = "دعاء سماع الرعد",
            titleEnglish = "Hearing Thunder",
            titleUrdu = "بادل گرجنے کی دعا",
            description = "Glorifying Allah upon hearing the powerful sound of thunder.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3401,
                    arabicText = "سُبْحَانَ الَّذِي يُسَبِّحُ الرَّعْدُ بِحَمْدِهِ وَالْمَلَائِكَةُ مِنْ خِيفَتِهِ",
                    transliteration = "Subhanal-ladhi yusabbihur-ra'du bihamdihi wal-mala'ikatu min kheefatih.",
                    translationEnglish = "Glory is to Him Whom thunder and angels glorify due to fear of Him.",
                    translationUrdu = "پاک ہے وہ ذات جس کی تسبیح اور حمد بادل گرجتے ہوئے کرتا ہے، اور فرشتے اس کے خوف سے (اس کی تسبیح کرتے ہیں)۔",
                    reference = "Al-Muwatta 51/21",
                    benefit = "Abdullah bin Zubair (RA) used to stop talking and recite this when he heard thunder.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 35,
            titleArabic = "دعاء رؤية الهلال",
            titleEnglish = "Sighting the Crescent Moon",
            titleUrdu = "نیا چاند دیکھنے کی دعا",
            description = "Welcoming the new Islamic month with prayers for peace and faith.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3501,
                    arabicText = "اللَّهُمَّ أَهِلَّهُ عَلَيْنَا بِالْأَمْنِ وَالْإِيمَانِ، وَالسَّلَامَةِ وَالْإِسْلَامِ، رَبِّي وَرَبُّكَ اللَّهُ",
                    transliteration = "Allahumma ahillahu 'alayna bil-amni wal-iman, was-salamati wal-islam, Rabbi wa rabbuk-Allah.",
                    translationEnglish = "O Allah, bring it over us with blessing and faith, and security and Islam. My Lord and your Lord is Allah.",
                    translationUrdu = "اے اللہ! اس چاند کو ہم پر امن اور ایمان کے ساتھ، اور سلامتی اور اسلام کے ساتھ نکال، میرا اور تیرا رب اللہ ہے۔",
                    reference = "Sunan at-Tirmidhi #3451",
                    benefit = "A beautiful sunnah for the beginning of every lunar month.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 36,
            titleArabic = "دعاء الصائم إذا أفطر",
            titleEnglish = "Breaking the Fast",
            titleUrdu = "روزہ افطار کرنے کی دعا",
            description = "The moment of joy and answered prayers when breaking a fast.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3601,
                    arabicText = "ذَهَبَ الظَّمَأُ، وَابْتَلَّتِ الْعُرُوقُ، وَثَبَتَ الْأَجْرُ إِنْ شَاءَ اللَّهُ",
                    transliteration = "Dhahabadh-dhama'u, wabtallatil-'urooqu, wa thabatal-ajru in shaa' Allah.",
                    translationEnglish = "The thirst is gone, the veins are moistened, and the reward is confirmed, if Allah wills.",
                    translationUrdu = "پیاس بجھ گئی، رگیں تر ہو گئیں، اور ثواب ثابت ہو گیا اگر اللہ نے چاہا۔",
                    reference = "Sunan Abi Dawud #2357",
                    benefit = "Recited after taking the first sip of water or eating the first date.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 37,
            titleArabic = "ما يقول الصائم إذا سابه أحد",
            titleEnglish = "When Insulted while Fasting",
            titleUrdu = "روزے کی حالت میں کوئی گالی دے تو کیا کہے",
            description = "Maintaining the sanctity of the fast by responding to abuse with patience.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3701,
                    arabicText = "إِنِّي صَائِمٌ، إِنِّي صَائِمٌ",
                    transliteration = "Inni sa'imun, inni sa'im.",
                    translationEnglish = "I am fasting, I am fasting.",
                    translationUrdu = "بیشک میں روزے سے ہوں، بیشک میں روزے سے ہوں۔",
                    reference = "Sahih al-Bukhari #1894",
                    benefit = "Preserves the reward of the fast and diffuses conflict without retaliation.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 38,
            titleArabic = "ما يقال عند ذبح الأضحية",
            titleEnglish = "When Slaughtering an Animal",
            titleUrdu = "جانور ذبح کرتے وقت کی دعا",
            description = "Dedicate the sacrifice solely for the sake of Allah.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3801,
                    arabicText = "بِسْمِ اللَّهِ، وَاللَّهُ أَكْبَرُ، اللَّهُمَّ مِنْكَ وَلَكَ، اللَّهُمَّ تَقَبَّلْ مِنِّي",
                    transliteration = "Bismillahi, wallahu Akbar, Allahumma minka wa lak, Allahumma taqabbal minni.",
                    translationEnglish = "In the Name of Allah, Allah is the Most Great. O Allah, from You and to You. O Allah, accept it from me.",
                    translationUrdu = "اللہ کے نام سے، اور اللہ سب سے بڑا ہے۔ اے اللہ! یہ تیری ہی طرف سے ہے اور تیرے ہی لیے ہے۔ اے اللہ! اسے میری طرف سے قبول فرما۔",
                    reference = "Sahih Muslim #1966",
                    benefit = "Ensures the meat is halal and the sacrifice is accepted as an act of worship.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 39,
            titleArabic = "دعاء العطاس",
            titleEnglish = "When Sneezing",
            titleUrdu = "چھینک آنے کے وقت کی دعا",
            description = "Praising Allah and responding to those who sneeze.",
            duas = listOf(
                HisnulDuaItem(
                    id = 3901,
                    arabicText = "الْحَمْدُ لِلَّهِ",
                    transliteration = "Alhamdulillah.",
                    translationEnglish = "All praise is to Allah.",
                    translationUrdu = "سب تعریفیں اللہ کے لیے ہیں۔",
                    reference = "Sahih al-Bukhari #6224",
                    benefit = "Said by the person who sneezes.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 3902,
                    arabicText = "يَرْحَمُكَ اللَّهُ",
                    transliteration = "Yarhamukallah.",
                    translationEnglish = "May Allah have mercy upon you.",
                    translationUrdu = "اللہ تم پر رحم کرے۔",
                    reference = "Sahih al-Bukhari #6224",
                    benefit = "Said by the person who hears the sneezer say Alhamdulillah.",
                    repeatCount = 1
                ),
                HisnulDuaItem(
                    id = 3903,
                    arabicText = "يَهْدِيكُمُ اللَّهُ وَيُصْلِحُ بَالَكُمْ",
                    transliteration = "Yahdeekumullahu wa yuslihu balakum.",
                    translationEnglish = "May Allah guide you and rectify your condition.",
                    translationUrdu = "اللہ تمہیں ہدایت دے اور تمہارا حال درست کرے۔",
                    reference = "Sahih al-Bukhari #6224",
                    benefit = "Said by the sneezer in reply.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 40,
            titleArabic = "ما يقال للمتزوج",
            titleEnglish = "For the Newlywed",
            titleUrdu = "شادی کی مبارکباد کی دعا",
            description = "A beautiful prayer for barakah in a new marriage.",
            duas = listOf(
                HisnulDuaItem(
                    id = 4001,
                    arabicText = "بَارَكَ اللَّهُ لَكَ، وَبَارَكَ عَلَيْكَ، وَجَمَعَ بَيْنَكُمَا فِي خَيْرٍ",
                    transliteration = "Barakallahu lak, wa baraka 'alayk, wa jama'a baynakuma fi khayr.",
                    translationEnglish = "May Allah bless you, and shower His blessings upon you, and join you together in goodness.",
                    translationUrdu = "اللہ تمہارے لیے برکت دے، اور تم پر برکت نازل کرے، اور تم دونوں کو بھلائی میں اکٹھا کرے۔",
                    reference = "Sunan at-Tirmidhi #1091",
                    benefit = "The sunnah way of congratulating a newlywed couple.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 41,
            titleArabic = "دعاء المتزوج إذا دخلت عليه زوجته",
            titleEnglish = "Before Marital Relations",
            titleUrdu = "بیوی کے پاس جانے کی دعا",
            description = "Seeking protection for the offspring.",
            duas = listOf(
                HisnulDuaItem(
                    id = 4101,
                    arabicText = "بِسْمِ اللَّهِ اللَّهُمَّ جَنِّبْنَا الشَّيْطَانَ، وَجَنِّبِ الشَّيْطَانَ مَا رَزَقْتَنَا",
                    transliteration = "Bismillah, Allahumma jannibnash-Shaytan, wa jannibish-Shaytana ma razaqtana.",
                    translationEnglish = "In the Name of Allah. O Allah, keep the devil away from us and keep the devil away from that which You provide for us.",
                    translationUrdu = "اللہ کے نام سے، اے اللہ! ہمیں شیطان سے بچا، اور جو رزق (اولاد) تو ہمیں عطا کرے اسے بھی شیطان سے بچا۔",
                    reference = "Sahih al-Bukhari #141",
                    benefit = "If a child is conceived, Shaytan will never be able to harm him.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 42,
            titleArabic = "دعاء الركوب",
            titleEnglish = "Boarding a Vehicle",
            titleUrdu = "سواری پر بیٹھنے کی دعا",
            description = "Praising Allah who made transportation possible.",
            duas = listOf(
                HisnulDuaItem(
                    id = 4201,
                    arabicText = "بِسْمِ اللَّهِ، الْحَمْدُ لِلَّهِ، ﴿سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ * وَإِنَّا إِلَى رَبِّنَا لَمُنْقَلِبُونَ﴾ الْحَمْدُ لِلَّهِ، الْحَمْدُ لِلَّهِ، الْحَمْدُ لِلَّهِ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، سُبْحَانَكَ اللَّهُمَّ إِنِّي ظَلَمْتُ نَفْسِي فَاغْفِرْ لِي، فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
                    transliteration = "Bismillah, Alhamdulillah, 'Subhanal-ladhi sakhkhara lana hadha wa ma kunna lahu muqrineen, wa inna ila Rabbina lamunqaliboon.' Alhamdulillah (3 times), Allahu Akbar (3 times), Subhanakal-lahumma inni dhalamtu nafsi faghfir li, fa-innahu la yaghfirudh-dhunuba illa Anta.",
                    translationEnglish = "In the Name of Allah. Praise is to Allah. 'Glory is to Him Who has subjected this to us, and we could not have otherwise subdued it. And indeed we, to our Lord, will surely return.' Praise is to Allah (3 times). Allah is the Most Great (3 times). Glory is to You, O Allah, I have wronged my own soul, so forgive me, for surely none forgives sins but You.",
                    translationUrdu = "اللہ کے نام کے ساتھ، سب تعریفیں اللہ کے لیے ہیں۔ پاک ہے وہ ذات جس نے اس (سواری) کو ہمارے تابع کر دیا، ورنہ ہم اسے قابو میں لانے والے نہ تھے۔ اور بے شک ہم اپنے رب ہی کی طرف لوٹنے والے ہیں۔ الحمد للہ (تین بار)، اللہ اکبر (تین بار)، اے اللہ تو پاک ہے، بے شک میں نے اپنی جان پر ظلم کیا، پس مجھے بخش دے، کیونکہ تیرے سوا کوئی گناہوں کو نہیں بخشتا۔",
                    reference = "Sunan Abi Dawud #2602",
                    benefit = "A comprehensive expression of gratitude, humility, and seeking forgiveness.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 43,
            titleArabic = "دعاء دخول السوق",
            titleEnglish = "Entering the Market",
            titleUrdu = "بازار میں داخل ہونے کی دعا",
            description = "A highly rewarding supplication when entering places of commerce.",
            duas = listOf(
                HisnulDuaItem(
                    id = 4301,
                    arabicText = "لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، يُحْيِي وَيُمِيتُ، وَهُوَ حَيٌّ لَا يَمُوتُ، بِيَدِهِ الْخَيْرُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                    transliteration = "La ilaha illallahu wahdahu la sharika lahu, lahul-mulku wa lahul-hamdu, yuhyi wa yumeetu, wa huwa Hayyun la yamootu, biyadihil-khayru, wa huwa 'ala kulli shay'in Qadeer.",
                    translationEnglish = "None has the right to be worshipped but Allah alone, Who has no partner. His is the dominion and His is the praise. He brings to life and He causes death, and He is Ever Living and does not die. In His Hand is all good, and He is Able to do all things.",
                    translationUrdu = "اللہ کے سوا کوئی سچا معبود نہیں، وہ اکیلا ہے اس کا کوئی شریک نہیں، اسی کے لیے بادشاہی ہے اور اسی کے لیے تمام تعریفیں ہیں، وہ زندہ کرتا ہے اور وہ مارتا ہے، اور وہ ہمیشہ زندہ رہنے والا ہے اسے موت نہیں آئے گی، اسی کے ہاتھ میں بھلائی ہے، اور وہ ہر چیز پر کامل قدرت رکھتا ہے۔",
                    reference = "Sunan at-Tirmidhi #3428",
                    benefit = "Allah will record a million good deeds for him, wipe away a million bad deeds, and raise him a million ranks.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 44,
            titleArabic = "الدعاء لمن صنع إليك معروفا",
            titleEnglish = "Thanking Someone",
            titleUrdu = "احسان کرنے والے کے لیے دعا",
            description = "The best way to express gratitude to someone who does you a favor.",
            duas = listOf(
                HisnulDuaItem(
                    id = 4401,
                    arabicText = "جَزَاكَ اللَّهُ خَيْرًا",
                    transliteration = "Jazakallahu khayran.",
                    translationEnglish = "May Allah reward you with goodness.",
                    translationUrdu = "اللہ آپ کو بہترین جزا دے۔",
                    reference = "Sunan at-Tirmidhi #2035",
                    benefit = "The Prophet (PBUH) said whoever says this has fully expressed his appreciation.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 45,
            titleArabic = "الاستعاذة من الدجال",
            titleEnglish = "Refuge from Dajjal",
            titleUrdu = "دجال کے فتنے سے پناہ کی دعا",
            description = "Seeking protection from the greatest trial before the Day of Judgment.",
            duas = listOf(
                HisnulDuaItem(
                    id = 4501,
                    arabicText = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ عَذَابِ جَهَنَّمَ، وَمِنْ عَذَابِ الْقَبْرِ، وَمِنْ فِتْنَةِ الْمَحْيَا وَالْمَمَاتِ، وَمِنْ شَرِّ فِتْنَةِ الْمَسِيحِ الدَّجَّالِ",
                    transliteration = "Allahumma inni a'udhu bika min 'adhabi jahannam, wa min 'adhabil-qabr, wa min fitnatil-mahya wal-mamat, wa min sharri fitnatil-maseehid-dajjal.",
                    translationEnglish = "O Allah, I seek refuge in You from the punishment of Hellfire, from the punishment of the grave, from the trials of life and death, and from the evil of the trial of the False Messiah (Dajjal).",
                    translationUrdu = "اے اللہ! میں جہنم کے عذاب سے تیری پناہ مانگتا ہوں، اور قبر کے عذاب سے، اور زندگی اور موت کے فتنے سے، اور مسیح دجال کے فتنے کے شر سے۔",
                    reference = "Sahih Muslim #588",
                    benefit = "The Prophet (PBUH) commanded reciting this before the final Tasleem in every prayer.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 46,
            titleArabic = "دعاء الخوف من الشرك",
            titleEnglish = "Fear of Shirk",
            titleUrdu = "شرک سے بچنے کی دعا",
            description = "Seeking refuge from minor and major polytheism.",
            duas = listOf(
                HisnulDuaItem(
                    id = 4601,
                    arabicText = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ أَنْ أُشْرِكَ بِكَ وَأَنَا أَعْلَمُ، وَأَسْتَغْفِرُكَ لِمَا لَا أَعْلَمُ",
                    transliteration = "Allahumma inni a'udhu bika an ushrika bika wa ana a'lamu, wa astaghfiruka lima la a'lam.",
                    translationEnglish = "O Allah, I seek refuge in You from associating anything with You knowingly, and I seek Your forgiveness for what I do unknowingly.",
                    translationUrdu = "اے اللہ! میں اس بات سے تیری پناہ مانگتا ہوں کہ میں جانتے ہوئے کسی کو تیرا شریک ٹھہراؤں، اور میں تجھ سے اس بات کی بخشش مانگتا ہوں جو میں نہیں جانتا۔",
                    reference = "Ahmad 4/403",
                    benefit = "Shirk (hidden polytheism) is more inconspicuous than a black ant creeping on a black stone in the dark of night. This dua protects against it.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 47,
            titleArabic = "كيف يلبي المحرم",
            titleEnglish = "Talbiyah for Hajj and Umrah",
            titleUrdu = "حج اور عمرہ کا تلبیہ",
            description = "The chant of the pilgrim responding to the call of Allah.",
            duas = listOf(
                HisnulDuaItem(
                    id = 4701,
                    arabicText = "لَبَّيْكَ اللَّهُمَّ لَبَّيْكَ، لَبَّيْكَ لَا شَرِيكَ لَكَ لَبَّيْكَ، إِنَّ الْحَمْدَ وَالنِّعْمَةَ لَكَ وَالْمُلْكَ، لَا شَرِيكَ لَكَ",
                    transliteration = "Labbayk Allahumma labbayk, labbayk la sharika laka labbayk, innal-hamda wan-ni'mata laka wal-mulk, la sharika lak.",
                    translationEnglish = "Here I am, O Allah, here I am. Here I am, You have no partner, here I am. Verily all praise and blessings are Yours, and all sovereignty, You have no partner.",
                    translationUrdu = "میں حاضر ہوں اے اللہ میں حاضر ہوں، میں حاضر ہوں تیرا کوئی شریک نہیں میں حاضر ہوں، بیشک تمام تعریفیں اور نعمتیں تیرے ہی لیے ہیں اور بادشاہی بھی، تیرا کوئی شریک نہیں۔",
                    reference = "Sahih al-Bukhari #1549",
                    benefit = "Declares complete submission and monotheism during the sacred pilgrimage.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 48,
            titleArabic = "سيد الاستغفار",
            titleEnglish = "Chief of Forgiveness (Sayyidul Istighfar)",
            titleUrdu = "استغفار کا سردار",
            description = "The most superior way of asking for forgiveness from Allah.",
            duas = listOf(
                HisnulDuaItem(
                    id = 4801,
                    arabicText = "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
                    transliteration = "Allahumma Anta Rabbi la ilaha illa Anta, khalaqtani wa ana 'abduka, wa ana 'ala 'ahdika wa wa'dika mas-tata'tu, a'udhu bika min sharri ma sana'tu, abu'u laka bini'matika 'alayya, wa abu'u bidhanbi faghfir li, fa-innahu la yaghfirudh-dhunuba illa Anta.",
                    translationEnglish = "O Allah, You are my Lord, there is none worthy of worship but You. You created me and I am Your slave. I keep Your covenant, and my pledge to You so far as I am able. I seek refuge in You from the evil of what I have done. I admit to Your blessings upon me, and I admit to my misdeeds. Forgive me, for there is none who may forgive sins but You.",
                    translationUrdu = "اے اللہ! تو میرا رب ہے تیرے سوا کوئی سچا معبود نہیں، تو نے مجھے پیدا کیا اور میں تیرا بندہ ہوں، اور میں اپنی طاقت کے مطابق تیرے عہد اور وعدے پر قائم ہوں۔ میں اپنے کیے کے شر سے تیری پناہ مانگتا ہوں، میں اپنے اوپر تیری نعمتوں کا اقرار کرتا ہوں، اور میں اپنے گناہوں کا اعتراف کرتا ہوں، پس تو مجھے بخش دے کیونکہ تیرے سوا کوئی گناہوں کو نہیں بخشتا۔",
                    reference = "Sahih al-Bukhari #6306",
                    benefit = "Whoever recites this with conviction in the evening and dies during that night shall enter Paradise. The same applies for the morning.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 49,
            titleArabic = "الذكر عند الاستيقاظ من النوم",
            titleEnglish = "Waking Up",
            titleUrdu = "بیدار ہونے کے بعد",
            description = "Supplications regarding Waking Up",
            duas = listOf(
                HisnulDuaItem(
                    id = 4901,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for waking up.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 50,
            titleArabic = "دعاء لبس الثوب",
            titleEnglish = "Wearing a Garment",
            titleUrdu = "کپڑے پہننے کی دعا",
            description = "Supplications regarding Wearing a Garment",
            duas = listOf(
                HisnulDuaItem(
                    id = 5001,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for wearing a garment.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 51,
            titleArabic = "دعاء لبس الثوب الجديد",
            titleEnglish = "Wearing a New Garment",
            titleUrdu = "نئے کپڑے پہننے کی دعا",
            description = "Supplications regarding Wearing a New Garment",
            duas = listOf(
                HisnulDuaItem(
                    id = 5101,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for wearing a new garment.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 52,
            titleArabic = "الدعاء لمن لبس ثوبا جديدا",
            titleEnglish = "To Someone Wearing a New Garment",
            titleUrdu = "نیا کپڑا پہننے والے کے لیے دعا",
            description = "Supplications regarding To Someone Wearing a New Garment",
            duas = listOf(
                HisnulDuaItem(
                    id = 5201,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for to someone wearing a new garment.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 53,
            titleArabic = "ما يقول إذا وضع ثوبه",
            titleEnglish = "Undressing",
            titleUrdu = "کپڑے اتارتے وقت",
            description = "Supplications regarding Undressing",
            duas = listOf(
                HisnulDuaItem(
                    id = 5301,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for undressing.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 54,
            titleArabic = "دعاء دخول الخلاء",
            titleEnglish = "Entering the Restroom",
            titleUrdu = "بیت الخلاء جانے کی دعا",
            description = "Supplications regarding Entering the Restroom",
            duas = listOf(
                HisnulDuaItem(
                    id = 5401,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for entering the restroom.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 55,
            titleArabic = "دعاء الخروج من الخلاء",
            titleEnglish = "Leaving the Restroom",
            titleUrdu = "بیت الخلاء سے نکلنے کی دعا",
            description = "Supplications regarding Leaving the Restroom",
            duas = listOf(
                HisnulDuaItem(
                    id = 5501,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for leaving the restroom.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 56,
            titleArabic = "الذكر قبل الوضوء",
            titleEnglish = "Before Wudu",
            titleUrdu = "وضو سے پہلے",
            description = "Supplications regarding Before Wudu",
            duas = listOf(
                HisnulDuaItem(
                    id = 5601,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for before wudu.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 57,
            titleArabic = "الذكر بعد الفراغ من الوضوء",
            titleEnglish = "After Wudu",
            titleUrdu = "وضو کے بعد",
            description = "Supplications regarding After Wudu",
            duas = listOf(
                HisnulDuaItem(
                    id = 5701,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for after wudu.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 58,
            titleArabic = "الذكر عند الخروج من المنزل",
            titleEnglish = "Leaving the House",
            titleUrdu = "گھر سے نکلتے وقت",
            description = "Supplications regarding Leaving the House",
            duas = listOf(
                HisnulDuaItem(
                    id = 5801,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for leaving the house.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 59,
            titleArabic = "الذكر عند الدخول إلى المنزل",
            titleEnglish = "Entering the House",
            titleUrdu = "گھر میں داخل ہوتے وقت",
            description = "Supplications regarding Entering the House",
            duas = listOf(
                HisnulDuaItem(
                    id = 5901,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for entering the house.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 60,
            titleArabic = "دعاء الذهاب إلى المسجد",
            titleEnglish = "Going to the Mosque",
            titleUrdu = "مسجد جانے کی دعا",
            description = "Supplications regarding Going to the Mosque",
            duas = listOf(
                HisnulDuaItem(
                    id = 6001,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for going to the mosque.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 61,
            titleArabic = "دعاء دخول المسجد",
            titleEnglish = "Entering the Mosque",
            titleUrdu = "مسجد میں داخل ہونے کی دعا",
            description = "Supplications regarding Entering the Mosque",
            duas = listOf(
                HisnulDuaItem(
                    id = 6101,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for entering the mosque.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 62,
            titleArabic = "دعاء الخروج من المسجد",
            titleEnglish = "Leaving the Mosque",
            titleUrdu = "مسجد سے نکلنے کی دعا",
            description = "Supplications regarding Leaving the Mosque",
            duas = listOf(
                HisnulDuaItem(
                    id = 6201,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for leaving the mosque.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 63,
            titleArabic = "أذكار الأذان",
            titleEnglish = "During the Adhan",
            titleUrdu = "اذان کے اذکار",
            description = "Supplications regarding During the Adhan",
            duas = listOf(
                HisnulDuaItem(
                    id = 6301,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for during the adhan.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 64,
            titleArabic = "دعاء الاستفتاح",
            titleEnglish = "Start of Prayer",
            titleUrdu = "نماز شروع کرنے کی دعا",
            description = "Supplications regarding Start of Prayer",
            duas = listOf(
                HisnulDuaItem(
                    id = 6401,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for start of prayer.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 65,
            titleArabic = "دعاء الركوع",
            titleEnglish = "Bowing (Ruku)",
            titleUrdu = "رکوع کی دعا",
            description = "Supplications regarding Bowing (Ruku)",
            duas = listOf(
                HisnulDuaItem(
                    id = 6501,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for bowing (ruku).",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 66,
            titleArabic = "دعاء الرفع من الركوع",
            titleEnglish = "Rising from Ruku",
            titleUrdu = "رکوع سے اٹھنے کی دعا",
            description = "Supplications regarding Rising from Ruku",
            duas = listOf(
                HisnulDuaItem(
                    id = 6601,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for rising from ruku.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 67,
            titleArabic = "دعاء السجود",
            titleEnglish = "Prostration (Sujud)",
            titleUrdu = "سجدے کی دعا",
            description = "Supplications regarding Prostration (Sujud)",
            duas = listOf(
                HisnulDuaItem(
                    id = 6701,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for prostration (sujud).",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 68,
            titleArabic = "دعاء الجلسة بين السجدتين",
            titleEnglish = "Between Two Sujuds",
            titleUrdu = "دو سجدوں کے درمیان کی دعا",
            description = "Supplications regarding Between Two Sujuds",
            duas = listOf(
                HisnulDuaItem(
                    id = 6801,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for between two sujuds.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 69,
            titleArabic = "دعاء سجود التلاوة",
            titleEnglish = "Sujud of Recitation",
            titleUrdu = "سجدہ تلاوت کی دعا",
            description = "Supplications regarding Sujud of Recitation",
            duas = listOf(
                HisnulDuaItem(
                    id = 6901,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for sujud of recitation.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 70,
            titleArabic = "التشهد",
            titleEnglish = "Tashahhud",
            titleUrdu = "تشہد",
            description = "Supplications regarding Tashahhud",
            duas = listOf(
                HisnulDuaItem(
                    id = 7001,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for tashahhud.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 71,
            titleArabic = "الصلاة على النبي بعد التشهد",
            titleEnglish = "Salawat After Tashahhud",
            titleUrdu = "درود شریف",
            description = "Supplications regarding Salawat After Tashahhud",
            duas = listOf(
                HisnulDuaItem(
                    id = 7101,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for salawat after tashahhud.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 72,
            titleArabic = "الدعاء بعد التشهد الأخير",
            titleEnglish = "After the Final Tashahhud",
            titleUrdu = "آخری تشہد کے بعد کی دعا",
            description = "Supplications regarding After the Final Tashahhud",
            duas = listOf(
                HisnulDuaItem(
                    id = 7201,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for after the final tashahhud.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 73,
            titleArabic = "الأذكار بعد السلام من الصلاة",
            titleEnglish = "After the Prayer",
            titleUrdu = "نماز کے بعد کے اذکار",
            description = "Supplications regarding After the Prayer",
            duas = listOf(
                HisnulDuaItem(
                    id = 7301,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for after the prayer.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 74,
            titleArabic = "دعاء صلاة الاستخارة",
            titleEnglish = "Istikhara Prayer",
            titleUrdu = "استخارہ کی دعا",
            description = "Supplications regarding Istikhara Prayer",
            duas = listOf(
                HisnulDuaItem(
                    id = 7401,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for istikhara prayer.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 75,
            titleArabic = "أذكار الصباح والمساء",
            titleEnglish = "Morning and Evening",
            titleUrdu = "صبح و شام کے اذکار",
            description = "Supplications regarding Morning and Evening",
            duas = listOf(
                HisnulDuaItem(
                    id = 7501,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for morning and evening.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 76,
            titleArabic = "أذكار النوم",
            titleEnglish = "Before Sleeping",
            titleUrdu = "سونے کی دعائیں",
            description = "Supplications regarding Before Sleeping",
            duas = listOf(
                HisnulDuaItem(
                    id = 7601,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for before sleeping.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 77,
            titleArabic = "الدعاء إذا تقلب ليلا",
            titleEnglish = "Turning Over at Night",
            titleUrdu = "رات کو کروٹ بدلنے کی دعا",
            description = "Supplications regarding Turning Over at Night",
            duas = listOf(
                HisnulDuaItem(
                    id = 7701,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for turning over at night.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 78,
            titleArabic = "دعاء الفزع في النوم",
            titleEnglish = "Fright During Sleep",
            titleUrdu = "نیند میں ڈر جانے کی دعا",
            description = "Supplications regarding Fright During Sleep",
            duas = listOf(
                HisnulDuaItem(
                    id = 7801,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for fright during sleep.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 79,
            titleArabic = "ما يفعل من رأى الرؤيا",
            titleEnglish = "Seeing a Dream",
            titleUrdu = "خواب دیکھنے کے بعد",
            description = "Supplications regarding Seeing a Dream",
            duas = listOf(
                HisnulDuaItem(
                    id = 7901,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for seeing a dream.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 80,
            titleArabic = "دعاء قنوت الوتر",
            titleEnglish = "Witr Qunut",
            titleUrdu = "قنوت وتر کی دعا",
            description = "Supplications regarding Witr Qunut",
            duas = listOf(
                HisnulDuaItem(
                    id = 8001,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for witr qunut.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 81,
            titleArabic = "الذكر عقب السلام من الوتر",
            titleEnglish = "After Witr Prayer",
            titleUrdu = "وتر کے بعد",
            description = "Supplications regarding After Witr Prayer",
            duas = listOf(
                HisnulDuaItem(
                    id = 8101,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for after witr prayer.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 82,
            titleArabic = "دعاء الهم والحزن",
            titleEnglish = "Anxiety and Sorrow",
            titleUrdu = "پریشانی اور غم کی دعا",
            description = "Supplications regarding Anxiety and Sorrow",
            duas = listOf(
                HisnulDuaItem(
                    id = 8201,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for anxiety and sorrow.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 83,
            titleArabic = "دعاء الكرب",
            titleEnglish = "In Times of Distress",
            titleUrdu = "مصیبت کے وقت کی دعا",
            description = "Supplications regarding In Times of Distress",
            duas = listOf(
                HisnulDuaItem(
                    id = 8301,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for in times of distress.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 84,
            titleArabic = "دعاء لقاء العدو وذي السلطان",
            titleEnglish = "Meeting an Enemy",
            titleUrdu = "دشمن کا سامنا کرتے وقت",
            description = "Supplications regarding Meeting an Enemy",
            duas = listOf(
                HisnulDuaItem(
                    id = 8401,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for meeting an enemy.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 85,
            titleArabic = "دعاء من خاف ظلم السلطان",
            titleEnglish = "Fear of Oppression",
            titleUrdu = "حاکم کے ظلم کا ڈر",
            description = "Supplications regarding Fear of Oppression",
            duas = listOf(
                HisnulDuaItem(
                    id = 8501,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for fear of oppression.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 86,
            titleArabic = "الدعاء على العدو",
            titleEnglish = "Against the Enemy",
            titleUrdu = "دشمن کے خلاف دعا",
            description = "Supplications regarding Against the Enemy",
            duas = listOf(
                HisnulDuaItem(
                    id = 8601,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for against the enemy.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 87,
            titleArabic = "ما يقول من خاف قوما",
            titleEnglish = "Fear of a Group",
            titleUrdu = "کسی قوم سے ڈرنا",
            description = "Supplications regarding Fear of a Group",
            duas = listOf(
                HisnulDuaItem(
                    id = 8701,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for fear of a group.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 88,
            titleArabic = "دعاء من أصابه شك في الإيمان",
            titleEnglish = "Doubts in Faith",
            titleUrdu = "ایمان میں شک کی دعا",
            description = "Supplications regarding Doubts in Faith",
            duas = listOf(
                HisnulDuaItem(
                    id = 8801,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for doubts in faith.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 89,
            titleArabic = "دعاء قضاء الدين",
            titleEnglish = "Settling a Debt",
            titleUrdu = "قرض کی ادائیگی کی دعا",
            description = "Supplications regarding Settling a Debt",
            duas = listOf(
                HisnulDuaItem(
                    id = 8901,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for settling a debt.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 90,
            titleArabic = "دعاء الوسوسة في الصلاة والقراءة",
            titleEnglish = "Whispers in Prayer",
            titleUrdu = "نماز میں وسوسوں کی دعا",
            description = "Supplications regarding Whispers in Prayer",
            duas = listOf(
                HisnulDuaItem(
                    id = 9001,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for whispers in prayer.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 91,
            titleArabic = "دعاء من استصعب عليه أمر",
            titleEnglish = "When Things are Difficult",
            titleUrdu = "مشکل وقت کی دعا",
            description = "Supplications regarding When Things are Difficult",
            duas = listOf(
                HisnulDuaItem(
                    id = 9101,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for when things are difficult.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 92,
            titleArabic = "ما يقول ويفعل من أذنب ذنبا",
            titleEnglish = "Upon Committing a Sin",
            titleUrdu = "گناہ ہو جانے پر",
            description = "Supplications regarding Upon Committing a Sin",
            duas = listOf(
                HisnulDuaItem(
                    id = 9201,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for upon committing a sin.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 93,
            titleArabic = "دعاء طرد الشيطان ووساوسه",
            titleEnglish = "Expelling the Devil",
            titleUrdu = "شیطان کو بھگانے کی دعا",
            description = "Supplications regarding Expelling the Devil",
            duas = listOf(
                HisnulDuaItem(
                    id = 9301,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for expelling the devil.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 94,
            titleArabic = "الدعاء حينما يقع ما لا يرضاه",
            titleEnglish = "When Displeased",
            titleUrdu = "ناپسندیدہ بات پر",
            description = "Supplications regarding When Displeased",
            duas = listOf(
                HisnulDuaItem(
                    id = 9401,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for when displeased.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 95,
            titleArabic = "تهنئة المولود له وجوابه",
            titleEnglish = "Congratulating Newborn",
            titleUrdu = "نومولود کی مبارکباد",
            description = "Supplications regarding Congratulating Newborn",
            duas = listOf(
                HisnulDuaItem(
                    id = 9501,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for congratulating newborn.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 96,
            titleArabic = "ما يعوذ به الأولاد",
            titleEnglish = "Protecting Children",
            titleUrdu = "بچوں کی حفاظت کی دعا",
            description = "Supplications regarding Protecting Children",
            duas = listOf(
                HisnulDuaItem(
                    id = 9601,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for protecting children.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 97,
            titleArabic = "الدعاء للمريض في عيادته",
            titleEnglish = "Visiting the Sick",
            titleUrdu = "بیمار کی عیادت کی دعا",
            description = "Supplications regarding Visiting the Sick",
            duas = listOf(
                HisnulDuaItem(
                    id = 9701,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for visiting the sick.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 98,
            titleArabic = "فضل عيادة المريض",
            titleEnglish = "Excellence of Visiting Sick",
            titleUrdu = "عیادت کی فضیلت",
            description = "Supplications regarding Excellence of Visiting Sick",
            duas = listOf(
                HisnulDuaItem(
                    id = 9801,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for excellence of visiting sick.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 99,
            titleArabic = "دعاء المريض الذي يئس من حياته",
            titleEnglish = "Sick Nearing Death",
            titleUrdu = "مایوس بیمار کی دعا",
            description = "Supplications regarding Sick Nearing Death",
            duas = listOf(
                HisnulDuaItem(
                    id = 9901,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for sick nearing death.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 100,
            titleArabic = "تلقين المحتضر",
            titleEnglish = "Instruction for Dying Person",
            titleUrdu = "مرنے والے کی تلقین",
            description = "Supplications regarding Instruction for Dying Person",
            duas = listOf(
                HisnulDuaItem(
                    id = 10001,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for instruction for dying person.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 101,
            titleArabic = "دعاء من أصيب بمصيبة",
            titleEnglish = "Afflicted by a Calamity",
            titleUrdu = "مصیبت کے وقت کی دعا",
            description = "Supplications regarding Afflicted by a Calamity",
            duas = listOf(
                HisnulDuaItem(
                    id = 10101,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for afflicted by a calamity.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 102,
            titleArabic = "الدعاء عند إغماض الميت",
            titleEnglish = "Closing Eyes of the Deceased",
            titleUrdu = "میت کی آنکھیں بند کرنے کی دعا",
            description = "Supplications regarding Closing Eyes of the Deceased",
            duas = listOf(
                HisnulDuaItem(
                    id = 10201,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for closing eyes of the deceased.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 103,
            titleArabic = "الدعاء للميت في الصلاة عليه",
            titleEnglish = "Funeral Prayer (Janazah)",
            titleUrdu = "نماز جنازہ کی دعا",
            description = "Supplications regarding Funeral Prayer (Janazah)",
            duas = listOf(
                HisnulDuaItem(
                    id = 10301,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for funeral prayer (janazah).",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 104,
            titleArabic = "الدعاء للفرط في الصلاة عليه",
            titleEnglish = "Funeral Prayer for a Child",
            titleUrdu = "بچے کی نماز جنازہ کی دعا",
            description = "Supplications regarding Funeral Prayer for a Child",
            duas = listOf(
                HisnulDuaItem(
                    id = 10401,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for funeral prayer for a child.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 105,
            titleArabic = "دعاء التعزية",
            titleEnglish = "Condolences",
            titleUrdu = "تعزیت کی دعا",
            description = "Supplications regarding Condolences",
            duas = listOf(
                HisnulDuaItem(
                    id = 10501,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for condolences.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 106,
            titleArabic = "الدعاء عند إدخال الميت القبر",
            titleEnglish = "Placing the Deceased in Grave",
            titleUrdu = "میت کو قبر میں رکھنے کی دعا",
            description = "Supplications regarding Placing the Deceased in Grave",
            duas = listOf(
                HisnulDuaItem(
                    id = 10601,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for placing the deceased in grave.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 107,
            titleArabic = "الدعاء بعد دفن الميت",
            titleEnglish = "After Burying the Deceased",
            titleUrdu = "میت کو دفن کرنے کے بعد کی دعا",
            description = "Supplications regarding After Burying the Deceased",
            duas = listOf(
                HisnulDuaItem(
                    id = 10701,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for after burying the deceased.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 108,
            titleArabic = "دعاء زيارة القبور",
            titleEnglish = "Visiting the Graves",
            titleUrdu = "قبرستان جانے کی دعا",
            description = "Supplications regarding Visiting the Graves",
            duas = listOf(
                HisnulDuaItem(
                    id = 10801,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for visiting the graves.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 109,
            titleArabic = "دعاء الريح",
            titleEnglish = "When the Wind Blows",
            titleUrdu = "آندھی کے وقت کی دعا",
            description = "Supplications regarding When the Wind Blows",
            duas = listOf(
                HisnulDuaItem(
                    id = 10901,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for when the wind blows.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 110,
            titleArabic = "دعاء الرعد",
            titleEnglish = "Upon Hearing Thunder",
            titleUrdu = "بادل گرجنے کی دعا",
            description = "Supplications regarding Upon Hearing Thunder",
            duas = listOf(
                HisnulDuaItem(
                    id = 11001,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for upon hearing thunder.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 111,
            titleArabic = "من أدعية الاستسقاء",
            titleEnglish = "Praying for Rain",
            titleUrdu = "بارش طلب کرنے کی دعا",
            description = "Supplications regarding Praying for Rain",
            duas = listOf(
                HisnulDuaItem(
                    id = 11101,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for praying for rain.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 112,
            titleArabic = "الدعاء إذا نزل المطر",
            titleEnglish = "When it Rains",
            titleUrdu = "بارش کے وقت کی دعا",
            description = "Supplications regarding When it Rains",
            duas = listOf(
                HisnulDuaItem(
                    id = 11201,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for when it rains.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 113,
            titleArabic = "الذكر بعد نزول المطر",
            titleEnglish = "After the Rain",
            titleUrdu = "بارش کے بعد کی دعا",
            description = "Supplications regarding After the Rain",
            duas = listOf(
                HisnulDuaItem(
                    id = 11301,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for after the rain.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 114,
            titleArabic = "من أدعية الاستصحاء",
            titleEnglish = "Asking for Clear Skies",
            titleUrdu = "موسم صاف ہونے کی دعا",
            description = "Supplications regarding Asking for Clear Skies",
            duas = listOf(
                HisnulDuaItem(
                    id = 11401,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for asking for clear skies.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 115,
            titleArabic = "دعاء رؤية الهلال",
            titleEnglish = "Sighting the Crescent Moon",
            titleUrdu = "نیا چاند دیکھنے کی دعا",
            description = "Supplications regarding Sighting the Crescent Moon",
            duas = listOf(
                HisnulDuaItem(
                    id = 11501,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for sighting the crescent moon.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 116,
            titleArabic = "الدعاء عند إفطار الصائم",
            titleEnglish = "Breaking the Fast",
            titleUrdu = "روزہ افطار کرنے کی دعا",
            description = "Supplications regarding Breaking the Fast",
            duas = listOf(
                HisnulDuaItem(
                    id = 11601,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for breaking the fast.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 117,
            titleArabic = "الدعاء قبل الطعام",
            titleEnglish = "Before Eating",
            titleUrdu = "کھانا کھانے سے پہلے کی دعا",
            description = "Supplications regarding Before Eating",
            duas = listOf(
                HisnulDuaItem(
                    id = 11701,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for before eating.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 118,
            titleArabic = "الدعاء عند الفراغ من الطعام",
            titleEnglish = "After Eating",
            titleUrdu = "کھانا کھانے کے بعد کی دعا",
            description = "Supplications regarding After Eating",
            duas = listOf(
                HisnulDuaItem(
                    id = 11801,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for after eating.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 119,
            titleArabic = "دعاء الضيف لصاحب الطعام",
            titleEnglish = "Guest's Prayer for the Host",
            titleUrdu = "مہمان کی میزبان کے لیے دعا",
            description = "Supplications regarding Guest's Prayer for the Host",
            duas = listOf(
                HisnulDuaItem(
                    id = 11901,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for guest's prayer for the host.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 120,
            titleArabic = "التعريض بالدعاء لطلب الطعام",
            titleEnglish = "Asking for Food",
            titleUrdu = "کھانا مانگنے کی دعا",
            description = "Supplications regarding Asking for Food",
            duas = listOf(
                HisnulDuaItem(
                    id = 12001,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for asking for food.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 121,
            titleArabic = "الدعاء لمن سقاه أو أراد أن يسقيه",
            titleEnglish = "For Someone Who Gives You Drink",
            titleUrdu = "پانی پلانے والے کے لیے دعا",
            description = "Supplications regarding For Someone Who Gives You Drink",
            duas = listOf(
                HisnulDuaItem(
                    id = 12101,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for for someone who gives you drink.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 122,
            titleArabic = "دعاء الإفطار عند أهل البيت",
            titleEnglish = "Breaking Fast at Someone's House",
            titleUrdu = "کسی کے گھر افطار کرنے کی دعا",
            description = "Supplications regarding Breaking Fast at Someone's House",
            duas = listOf(
                HisnulDuaItem(
                    id = 12201,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for breaking fast at someone's house.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 123,
            titleArabic = "دعاء الصائم إذا حضر الطعام ولم يفطر",
            titleEnglish = "Fasting Person when Food is Served",
            titleUrdu = "روزے دار کی دعا جب کھانا سامنے ہو",
            description = "Supplications regarding Fasting Person when Food is Served",
            duas = listOf(
                HisnulDuaItem(
                    id = 12301,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for fasting person when food is served.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 124,
            titleArabic = "ما يقول الصائم إذا سابه أحد",
            titleEnglish = "When Insulted While Fasting",
            titleUrdu = "روزے میں کوئی گالی دے تو کیا کہے",
            description = "Supplications regarding When Insulted While Fasting",
            duas = listOf(
                HisnulDuaItem(
                    id = 12401,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for when insulted while fasting.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 125,
            titleArabic = "الدعاء عند رؤية باكورة الثمر",
            titleEnglish = "Upon Seeing Early Fruits",
            titleUrdu = "پہلا پھل دیکھنے کی دعا",
            description = "Supplications regarding Upon Seeing Early Fruits",
            duas = listOf(
                HisnulDuaItem(
                    id = 12501,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for upon seeing early fruits.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 126,
            titleArabic = "دعاء العطاس",
            titleEnglish = "When Sneezing",
            titleUrdu = "چھینک آنے پر",
            description = "Supplications regarding When Sneezing",
            duas = listOf(
                HisnulDuaItem(
                    id = 12601,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for when sneezing.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 127,
            titleArabic = "ما يقال للكافر إذا عطس فحمد الله",
            titleEnglish = "Non-Muslim Sneezing",
            titleUrdu = "کافر کے چھینکنے پر",
            description = "Supplications regarding Non-Muslim Sneezing",
            duas = listOf(
                HisnulDuaItem(
                    id = 12701,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for non-muslim sneezing.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 128,
            titleArabic = "الدعاء للمتزوج",
            titleEnglish = "To the Newlywed",
            titleUrdu = "شادی کی مبارکباد",
            description = "Supplications regarding To the Newlywed",
            duas = listOf(
                HisnulDuaItem(
                    id = 12801,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for to the newlywed.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 129,
            titleArabic = "دعاء المتزوج و شراء الدابة",
            titleEnglish = "Marriage and Buying an Animal",
            titleUrdu = "شادی اور سواری خریدنے کی دعا",
            description = "Supplications regarding Marriage and Buying an Animal",
            duas = listOf(
                HisnulDuaItem(
                    id = 12901,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for marriage and buying an animal.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 130,
            titleArabic = "الدعاء قبل إتيان الزوجة",
            titleEnglish = "Before Marital Relations",
            titleUrdu = "بیوی کے پاس جانے کی دعا",
            description = "Supplications regarding Before Marital Relations",
            duas = listOf(
                HisnulDuaItem(
                    id = 13001,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for before marital relations.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 131,
            titleArabic = "دعاء الغضب",
            titleEnglish = "When Angry",
            titleUrdu = "غصے کی دعا",
            description = "Supplications regarding When Angry",
            duas = listOf(
                HisnulDuaItem(
                    id = 13101,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for when angry.",
                    repeatCount = 1
                )
            )
        ),
        HisnulChapter(
            id = 132,
            titleArabic = "دعاء من رأى مبتلى",
            titleEnglish = "Seeing Someone Afflicted",
            titleUrdu = "کسی مصیبت زدہ کو دیکھ کر دعا",
            description = "Supplications regarding Seeing Someone Afflicted",
            duas = listOf(
                HisnulDuaItem(
                    id = 13201,
                    arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                    transliteration = "Subhanallahi wa bihamdihi.",
                    translationEnglish = "Glory and praise be to Allah.",
                    translationUrdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                    reference = "Sahih Muslim",
                    benefit = "A beautiful remembrance for seeing someone afflicted.",
                    repeatCount = 1
                )
            )
        )
    )
}
