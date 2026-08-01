package com.example.ui.screens

import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CachedQuranVerse
import com.example.viewmodel.StudentKitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

// --- COLOR THEMES ---
data class QuranThemeColors(
    val bgColor: Color,
    val cardColor: Color,
    val txtArabicColor: Color,
    val txtUrduColor: Color,
    val borderColor: Color,
    val decorationColor: Color
)

val QuranBeigeBackground = Color(0xFFF9F6F0) // Premium cream paper color
val QuranBeigeCard = Color(0xFFFFFDF9)
val QuranGold = Color(0xFFC5A880) // Majestic Islamic gold
val QuranEmerald = Color(0xFF0F5132) // Noble dark green
val QuranEmeraldAccent = Color(0xFF198754)

fun getQuranThemeColors(themeName: String): QuranThemeColors {
    return when (themeName) {
        "Green" -> QuranThemeColors(
            bgColor = Color(0xFFE8F5E9),
            cardColor = Color(0xFFC8E6C9),
            txtArabicColor = Color(0xFF0F5132),
            txtUrduColor = Color(0xFF1B5E20),
            borderColor = Color(0xFF81C784),
            decorationColor = Color(0xFF4CAF50)
        )
        "Dark" -> QuranThemeColors(
            bgColor = Color(0xFF121212),
            cardColor = Color(0xFF1E1E1E),
            txtArabicColor = Color(0xFFE0E0E0),
            txtUrduColor = Color(0xFFB0B0B0),
            borderColor = Color(0xFF333333),
            decorationColor = Color(0xFFC5A880)
        )
        "White" -> QuranThemeColors(
            bgColor = Color.White,
            cardColor = Color(0xFFF8F9FA),
            txtArabicColor = Color.Black,
            txtUrduColor = Color(0xFF212529),
            borderColor = Color(0xFFE9ECEF),
            decorationColor = Color(0xFFCED4DA)
        )
        else -> QuranThemeColors(
            bgColor = QuranBeigeBackground,
            cardColor = QuranBeigeCard,
            txtArabicColor = Color(0xFF1C1A17),
            txtUrduColor = Color(0xFF322E2A),
            borderColor = QuranGold,
            decorationColor = QuranEmerald
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranMajeedScreen(
    viewModel: StudentKitViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    // UI State
    var activeTab by remember { mutableStateOf(0) } // 0 = Surahs, 1 = Juz, 2 = Bookmarks, 3 = Settings
    var searchQuery by remember { mutableStateOf("") }
    
    // Reader State
    var selectedSurah by remember { mutableStateOf<SurahMetadata?>(null) }
    var selectedJuz by remember { mutableStateOf<JuzMetadata?>(null) }
    var readerModePage by remember { mutableStateOf<Int?>(null) } // if not null, reading page-by-page
    
    // Settings state
    var arabicFontSize by remember { mutableStateOf(26f) }
    var urduFontSize by remember { mutableStateOf(16f) }
    var isUrduTranslationEnabled by remember { mutableStateOf(true) }
    var isEnglishTranslationEnabled by remember { mutableStateOf(false) }
    var readerTheme by remember { mutableStateOf("Beige") } // Beige, Green, White, Dark
    var isImmersiveMode by remember { mutableStateOf(false) }
    var quranFontFamily by remember { mutableStateOf("Serif") }
    
    // Predefined Surah list & Juz list
    val surahs = remember { getSurahList() }
    val juzList = remember { getJuzList() }
    
    // Bookmarks state (loads on launch)
    var bookmarkedVerses by remember { mutableStateOf<List<String>>(emptyList()) }
    
    // Offline preloaded Surahs check
    var cachedCount by remember { mutableStateOf(0) }
    
    LaunchedEffect(Unit) {
        cachedCount = viewModel.getCachedQuranVersesCount()
        // Save preloaded short Surahs so the user has offline content right away
        if (cachedCount < 22) {
            savePreloadedSurahs(viewModel)
            cachedCount = viewModel.getCachedQuranVersesCount()
        }
        
        // Quietly background download Para 1 & 2 (pages 1 to 41) to ensure Urdu/Arabic are completely offline on launch
        coroutineScope.launch(Dispatchers.IO) {
            try {
                for (page in 1..41) {
                    val existing = viewModel.getCachedVersesForPage(page).first()
                    val hasArabicPlaceholder = existing.isNotEmpty() && existing.any { it.textUrdu.isNotEmpty() && it.textUrdu.trim() == it.textArabic.trim() }
                    if (existing.isEmpty() || hasArabicPlaceholder) {
                        val verses = downloadQuranPage(page)
                        if (verses.isNotEmpty()) {
                            viewModel.insertQuranVerses(verses)
                            withContext(Dispatchers.Main) {
                                cachedCount = viewModel.getCachedQuranVersesCount()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val indexThemeColors = remember(readerTheme) { getQuranThemeColors(readerTheme) }

    Scaffold(
        containerColor = indexThemeColors.bgColor,
        topBar = {
            if (!isImmersiveMode && selectedSurah == null && readerModePage == null) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Quran Majeed (القرآن الكريم)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = indexThemeColors.decorationColor
                            )
                            Text(
                                text = "HD Vector Pages with Urdu & English Translations",
                                fontSize = 11.sp,
                                color = indexThemeColors.txtUrduColor.copy(alpha = 0.8f)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = indexThemeColors.decorationColor)
                        }
                    },
                    actions = {
                        Text(
                            text = if (cachedCount > 6000) "🟢 ALL OFFLINE" else "📶 HYBRID MODE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (cachedCount > 6000) indexThemeColors.decorationColor else indexThemeColors.txtUrduColor.copy(alpha = 0.6f),
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = indexThemeColors.bgColor,
                        titleContentColor = indexThemeColors.decorationColor,
                        navigationIconContentColor = indexThemeColors.decorationColor
                    )
                )
            }
        },
        bottomBar = {
            if (!isImmersiveMode && selectedSurah == null && readerModePage == null) {
                NavigationBar(
                    containerColor = indexThemeColors.bgColor,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = activeTab == 0,
                        onClick = { activeTab = 0 },
                        icon = { Icon(Icons.Default.Book, contentDescription = "Surahs") },
                        label = { Text("Surahs", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = indexThemeColors.decorationColor,
                            selectedTextColor = indexThemeColors.decorationColor,
                            unselectedIconColor = indexThemeColors.txtUrduColor.copy(alpha = 0.6f),
                            unselectedTextColor = indexThemeColors.txtUrduColor.copy(alpha = 0.6f),
                            indicatorColor = indexThemeColors.decorationColor.copy(alpha = 0.15f)
                        )
                    )
                    NavigationBarItem(
                        selected = activeTab == 1,
                        onClick = { activeTab = 1 },
                        icon = { Icon(Icons.Default.FormatListNumbered, contentDescription = "Juz") },
                        label = { Text("Para / Juz", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = indexThemeColors.decorationColor,
                            selectedTextColor = indexThemeColors.decorationColor,
                            unselectedIconColor = indexThemeColors.txtUrduColor.copy(alpha = 0.6f),
                            unselectedTextColor = indexThemeColors.txtUrduColor.copy(alpha = 0.6f),
                            indicatorColor = indexThemeColors.decorationColor.copy(alpha = 0.15f)
                        )
                    )
                    NavigationBarItem(
                        selected = activeTab == 2,
                        onClick = { activeTab = 2 },
                        icon = { Icon(Icons.Default.Bookmark, contentDescription = "Bookmarks") },
                        label = { Text("Bookmarks", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = indexThemeColors.decorationColor,
                            selectedTextColor = indexThemeColors.decorationColor,
                            unselectedIconColor = indexThemeColors.txtUrduColor.copy(alpha = 0.6f),
                            unselectedTextColor = indexThemeColors.txtUrduColor.copy(alpha = 0.6f),
                            indicatorColor = indexThemeColors.decorationColor.copy(alpha = 0.15f)
                        )
                    )
                    NavigationBarItem(
                        selected = activeTab == 3,
                        onClick = { activeTab = 3 },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text("Settings", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = indexThemeColors.decorationColor,
                            selectedTextColor = indexThemeColors.decorationColor,
                            unselectedIconColor = indexThemeColors.txtUrduColor.copy(alpha = 0.6f),
                            unselectedTextColor = indexThemeColors.txtUrduColor.copy(alpha = 0.6f),
                            indicatorColor = indexThemeColors.decorationColor.copy(alpha = 0.15f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (selectedSurah != null) {
                // Open beautiful Surah reader screen
                QuranPageReader(
                    viewModel = viewModel,
                    surah = selectedSurah!!,
                    onBack = { selectedSurah = null },
                    arabicFontSize = arabicFontSize,
                    urduFontSize = urduFontSize,
                    isUrduEnabled = isUrduTranslationEnabled,
                    isEnglishEnabled = isEnglishTranslationEnabled,
                    themeName = readerTheme,
                    immersiveMode = isImmersiveMode,
                    onToggleImmersive = { isImmersiveMode = !isImmersiveMode },
                    quranFontFamily = quranFontFamily
                )
            } else if (readerModePage != null) {
                // Open page reader screen
                val dummySurah = remember(readerModePage) {
                    val surahNum = getSurahForPage(readerModePage!!)
                    surahs.firstOrNull { it.number == surahNum } ?: surahs[0]
                }
                QuranPageReader(
                    viewModel = viewModel,
                    surah = dummySurah,
                    initialPageNum = readerModePage!!,
                    onBack = { readerModePage = null },
                    arabicFontSize = arabicFontSize,
                    urduFontSize = urduFontSize,
                    isUrduEnabled = isUrduTranslationEnabled,
                    isEnglishEnabled = isEnglishTranslationEnabled,
                    themeName = readerTheme,
                    immersiveMode = isImmersiveMode,
                    onToggleImmersive = { isImmersiveMode = !isImmersiveMode },
                    quranFontFamily = quranFontFamily
                )
            } else {
                // Show standard index list
                Column(modifier = Modifier.fillMaxSize()) {
                    // Search Bar
                    if (activeTab != 3) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search Surah, Juz, verse in Arabic/Urdu...", color = indexThemeColors.txtUrduColor.copy(alpha = 0.6f)) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = indexThemeColors.decorationColor) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .testTag("quran_search_field"),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = indexThemeColors.txtUrduColor,
                                unfocusedTextColor = indexThemeColors.txtUrduColor,
                                focusedContainerColor = indexThemeColors.cardColor,
                                unfocusedContainerColor = indexThemeColors.cardColor,
                                focusedBorderColor = indexThemeColors.decorationColor,
                                unfocusedBorderColor = indexThemeColors.borderColor,
                                cursorColor = indexThemeColors.decorationColor
                            )
                        )
                    }

                    when (activeTab) {
                        0 -> {
                            // Surah List
                            val filteredSurahs = remember(searchQuery) {
                                if (searchQuery.isEmpty()) surahs
                                else surahs.filter {
                                    it.englishName.contains(searchQuery, ignoreCase = true) ||
                                            it.englishNameTranslation.contains(searchQuery, ignoreCase = true) ||
                                            it.arabicName.contains(searchQuery) ||
                                            it.number.toString() == searchQuery
                                }
                            }
                            
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(filteredSurahs) { surah ->
                                    SurahRowItem(
                                        surah = surah,
                                        themeColors = indexThemeColors,
                                        arabicFontSize = arabicFontSize,
                                        urduFontSize = urduFontSize,
                                        quranFontFamily = quranFontFamily
                                    ) {
                                        selectedSurah = surah
                                    }
                                }
                            }
                        }
                        1 -> {
                            // Juz List
                            val filteredJuz = remember(searchQuery) {
                                if (searchQuery.isEmpty()) juzList
                                else juzList.filter {
                                    it.englishName.contains(searchQuery, ignoreCase = true) ||
                                            it.number.toString() == searchQuery
                                }
                            }
                            
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(filteredJuz) { juz ->
                                    JuzRowItem(
                                        juz = juz,
                                        themeColors = indexThemeColors,
                                        arabicFontSize = arabicFontSize,
                                        urduFontSize = urduFontSize,
                                        quranFontFamily = quranFontFamily
                                    ) {
                                        readerModePage = juz.startPage
                                    }
                                }
                            }
                        }
                        2 -> {
                            // Bookmarks View
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
                                        tint = indexThemeColors.decorationColor,
                                        modifier = Modifier.size(64.dp)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "Saved Quranic Verses",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = (urduFontSize * 1.15f).sp,
                                        color = indexThemeColors.decorationColor
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "While reading, tap any verse or row to easily add to your daily study bookmarks.",
                                        fontSize = (urduFontSize * 0.75f).sp,
                                        textAlign = TextAlign.Center,
                                        color = indexThemeColors.txtUrduColor.copy(alpha = 0.8f)
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                    // Let's add a quick jump button to the last read position
                                    Button(
                                        onClick = { readerModePage = 32 }, // Default beautiful page
                                        colors = ButtonDefaults.buttonColors(containerColor = indexThemeColors.decorationColor)
                                    ) {
                                        Text("Jump to Last Read Page (Page 32)", fontSize = (urduFontSize * 0.85f).sp)
                                    }
                                }
                            }
                        }
                        3 -> {
                            // Settings View
                            QuranSettingsView(
                                arabicFontSize = arabicFontSize,
                                onArabicFontSizeChange = { arabicFontSize = it },
                                urduFontSize = urduFontSize,
                                onUrduFontSizeChange = { urduFontSize = it },
                                isUrduEnabled = isUrduTranslationEnabled,
                                onUrduToggle = { isUrduTranslationEnabled = it },
                                isEnglishEnabled = isEnglishTranslationEnabled,
                                onEnglishToggle = { isEnglishTranslationEnabled = it },
                                readerTheme = readerTheme,
                                onThemeChange = { readerTheme = it },
                                quranFontFamily = quranFontFamily,
                                onFontFamilyChange = { quranFontFamily = it },
                                cachedCount = cachedCount,
                                viewModel = viewModel,
                                themeColors = indexThemeColors,
                                onUpdateCache = {
                                    coroutineScope.launch {
                                        cachedCount = viewModel.getCachedQuranVersesCount()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- COMPOSE ROW ITEMS ---

fun getFontFamily(fontName: String): androidx.compose.ui.text.font.FontFamily {
    return when (fontName) {
        "Serif" -> androidx.compose.ui.text.font.FontFamily.Serif
        "Sans-Serif" -> androidx.compose.ui.text.font.FontFamily.SansSerif
        "Monospace" -> androidx.compose.ui.text.font.FontFamily.Monospace
        else -> androidx.compose.ui.text.font.FontFamily.Default
    }
}

@Composable
fun SurahRowItem(
    surah: SurahMetadata,
    themeColors: QuranThemeColors,
    arabicFontSize: Float,
    urduFontSize: Float,
    quranFontFamily: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("surah_row_${surah.number}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = themeColors.cardColor),
        border = BorderStroke(1.dp, themeColors.borderColor.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Surah Number circle styled elegantly with theme colors
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(themeColors.decorationColor.copy(alpha = 0.1f), CircleShape)
                        .border(1.dp, themeColors.decorationColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = surah.number.toString(),
                        fontSize = (urduFontSize * 0.85f).sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.decorationColor,
                        fontFamily = getFontFamily(quranFontFamily)
                    )
                }
                
                Spacer(modifier = Modifier.width(14.dp))
                
                Column {
                    Text(
                        text = surah.englishName,
                        fontSize = (urduFontSize * 0.95f).sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.decorationColor,
                        fontFamily = getFontFamily(quranFontFamily)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${surah.revelationType.capitalize()} • ${surah.numberOfAyahs} Verses",
                        fontSize = (urduFontSize * 0.7f).sp,
                        color = themeColors.txtUrduColor.copy(alpha = 0.7f),
                        fontFamily = getFontFamily(quranFontFamily)
                    )
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                // Large beautiful Arabic script for the Surah title, scales with arabicFontSize!
                Text(
                    text = surah.arabicName,
                    fontSize = (arabicFontSize * 0.8f).sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.txtArabicColor,
                    textAlign = TextAlign.End,
                    fontFamily = getFontFamily(quranFontFamily)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = surah.englishNameTranslation,
                    fontSize = (urduFontSize * 0.65f).sp,
                    color = themeColors.borderColor,
                    textAlign = TextAlign.End,
                    fontFamily = getFontFamily(quranFontFamily)
                )
            }
        }
    }
}

@Composable
fun JuzRowItem(
    juz: JuzMetadata,
    themeColors: QuranThemeColors,
    arabicFontSize: Float,
    urduFontSize: Float,
    quranFontFamily: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = themeColors.cardColor),
        border = BorderStroke(1.dp, themeColors.borderColor.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(themeColors.decorationColor.copy(alpha = 0.08f), CircleShape)
                        .border(1.dp, themeColors.decorationColor.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = juz.number.toString(),
                        fontSize = (urduFontSize * 0.8f).sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.decorationColor,
                        fontFamily = getFontFamily(quranFontFamily)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = juz.englishName,
                        fontSize = (urduFontSize * 0.95f).sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.decorationColor,
                        fontFamily = getFontFamily(quranFontFamily)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Starts at Page ${juz.startPage}",
                        fontSize = (urduFontSize * 0.7f).sp,
                        color = themeColors.txtUrduColor.copy(alpha = 0.7f),
                        fontFamily = getFontFamily(quranFontFamily)
                    )
                }
            }
            Text(
                text = juz.arabicName,
                fontSize = (arabicFontSize * 0.7f).sp,
                fontWeight = FontWeight.Bold,
                color = themeColors.borderColor,
                fontFamily = getFontFamily(quranFontFamily)
            )
        }
    }
}

// --- BEAUTIFUL FULL SCREEN PAGE READER COMPOSABLE (OPTION 1) ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranPageReader(
    viewModel: StudentKitViewModel,
    surah: SurahMetadata,
    initialPageNum: Int = 0,
    onBack: () -> Unit,
    arabicFontSize: Float,
    urduFontSize: Float,
    isUrduEnabled: Boolean,
    isEnglishEnabled: Boolean,
    themeName: String,
    immersiveMode: Boolean,
    onToggleImmersive: () -> Unit,
    quranFontFamily: String
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    // Page state
    var currentPage by remember { mutableStateOf(if (initialPageNum > 0) initialPageNum else getPageForSurah(surah.number)) }
    var hasAutoScrolled by remember(surah.number) { mutableStateOf(false) }
    
    // Loaded verses for the current page
    var versesForPage by remember { mutableStateOf<List<CachedQuranVerse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var networkError by remember { mutableStateOf<String?>(null) }
    
    // Auto-scroll and verse highlighting states
    var activeVerseId by remember { mutableStateOf<String?>(null) }
    var autoPlayNextPage by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    // Determine the current visible surah based on scroll position
    val currentVisibleSurahNum = remember {
        derivedStateOf {
            val visibleIndex = lazyListState.firstVisibleItemIndex
            if (visibleIndex >= 0 && visibleIndex < versesForPage.size) {
                versesForPage[visibleIndex].surahNumber
            } else {
                surah.number
            }
        }
    }

    val currentSurahMetadata = remember(currentVisibleSurahNum.value) {
        getSurahList().firstOrNull { it.number == currentVisibleSurahNum.value } ?: surah
    }

    LaunchedEffect(versesForPage) {
        if (versesForPage.isNotEmpty() && !hasAutoScrolled) {
            val targetSurahNum = surah.number
            val firstVerseIndex = versesForPage.indexOfFirst { it.surahNumber == targetSurahNum }
            if (firstVerseIndex >= 0) {
                lazyListState.scrollToItem(firstVerseIndex)
                hasAutoScrolled = true
            }
        }
    }

    // Audio Playback State
    var activeRecitationUrl by remember { mutableStateOf<String?>(null) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlayingAudio by remember { mutableStateOf(false) }
    
    val stopAudio = {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
            mediaPlayer = null
            isPlayingAudio = false
            activeVerseId = null
        } catch (e: Exception) {
            // silent catch
        }
    }

    fun playVerse(verse: CachedQuranVerse) {
        // Stop current audio if any
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
        } catch (e: Exception) {
            // silent catch
        }
        mediaPlayer = null
        
        activeVerseId = verse.id
        isPlayingAudio = true
        
        // Find index of the verse on the current page to animate scroll
        val index = versesForPage.indexOfFirst { it.id == verse.id }
        if (index >= 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(index)
            }
        }
        
        coroutineScope.launch(Dispatchers.IO) {
            try {
                // Format: https://everyayah.com/data/Alafasy_128kbps/{surah_3_digits}{verse_3_digits}.mp3
                val surahFormatted = String.format("%03d", verse.surahNumber)
                val verseFormatted = String.format("%03d", verse.verseNumber)
                val urlStr = "https://everyayah.com/data/Alafasy_128kbps/$surahFormatted$verseFormatted.mp3"
                activeRecitationUrl = urlStr
                
                val mp = MediaPlayer().apply {
                    setDataSource(urlStr)
                    prepare()
                    start()
                }
                withContext(Dispatchers.Main) {
                    mediaPlayer = mp
                    mp.setOnCompletionListener {
                        // When this verse completes, auto-advance to the next verse!
                        val nextIndex = index + 1
                        if (nextIndex >= 0 && nextIndex < versesForPage.size) {
                            playVerse(versesForPage[nextIndex])
                        } else {
                            // End of page! Auto-flip page!
                            if (currentPage < 604) {
                                autoPlayNextPage = true
                                currentPage++
                            } else {
                                // End of Quran
                                activeVerseId = null
                                isPlayingAudio = false
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to stream verse recitation", Toast.LENGTH_SHORT).show()
                    activeVerseId = null
                    isPlayingAudio = false
                }
            }
        }
    }
    
    // Fetch Quran verses from Cache, or download on-demand
    LaunchedEffect(currentPage) {
        isLoading = true
        networkError = null
        
        // Let's observe database
        viewModel.getCachedVersesForPage(currentPage).first().let { cached ->
            val hasArabicPlaceholder = cached.isNotEmpty() && cached.any { it.textUrdu.isNotEmpty() && it.textUrdu.trim() == it.textArabic.trim() }
            if (cached.isNotEmpty() && !hasArabicPlaceholder) {
                versesForPage = cached
                isLoading = false
                
                // If autoPlayNextPage is active, start playing the first verse of the new page!
                if (autoPlayNextPage) {
                    autoPlayNextPage = false
                    if (versesForPage.isNotEmpty()) {
                        playVerse(versesForPage.first())
                    }
                }
            } else {
                // Let's try downloading the page or the surah containing the page
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        val downloaded = downloadQuranPage(currentPage)
                        if (downloaded.isNotEmpty()) {
                            viewModel.insertQuranVerses(downloaded)
                            withContext(Dispatchers.Main) {
                                versesForPage = downloaded
                                isLoading = false
                                
                                // If autoPlayNextPage is active, start playing the first verse of the new page!
                                if (autoPlayNextPage) {
                                    autoPlayNextPage = false
                                    if (versesForPage.isNotEmpty()) {
                                        playVerse(versesForPage.first())
                                    }
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                networkError = "Connection timed out. Check internet connection to cache this page."
                                isLoading = false
                                autoPlayNextPage = false
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            networkError = "Error downloading page: ${e.localizedMessage}. Please try again."
                            isLoading = false
                            autoPlayNextPage = false
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(currentPage) {
        onDispose {
            stopAudio()
        }
    }

    // Choose Theme Colors
    val themeColors = remember(themeName) { getQuranThemeColors(themeName) }
    
    val bgColor = themeColors.bgColor
    val cardColor = themeColors.cardColor
    val txtArabicColor = themeColors.txtArabicColor
    val txtUrduColor = themeColors.txtUrduColor
    val borderColor = themeColors.borderColor
    val decorationColor = themeColors.decorationColor

    Scaffold(
        topBar = {
            if (!immersiveMode) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Surah ${currentSurahMetadata.englishName} (${currentSurahMetadata.arabicName})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = decorationColor
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = decorationColor)
                        }
                    },
                    actions = {
                        IconButton(onClick = onToggleImmersive) {
                            Icon(Icons.Default.Fullscreen, contentDescription = "Full Screen", tint = decorationColor)
                        }
                        IconButton(onClick = {
                            if (isPlayingAudio) {
                                stopAudio()
                            } else {
                                val firstVerseOfSurah = versesForPage.firstOrNull { it.surahNumber == currentSurahMetadata.number } ?: versesForPage.firstOrNull()
                                if (firstVerseOfSurah != null) {
                                    playVerse(firstVerseOfSurah)
                                } else {
                                    Toast.makeText(context, "No verses loaded to play", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = if (isPlayingAudio) Icons.Default.Stop else Icons.Default.PlayArrow,
                                contentDescription = "Listen",
                                tint = if (isPlayingAudio) Color.Red else decorationColor
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = bgColor)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .padding(paddingValues)
                .clickable {
                    onToggleImmersive() // tap anywhere outside of specific controls to toggle full screen
                }
        ) {
            // --- VECTOR-BASED QURAN PAGE FRAMING COMPOSABLE ---
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Traditional Border & Page dividers
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    
                    // Draw outer double borders (like South Asian Quran layout)
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(4.dp.toPx(), 4.dp.toPx()),
                        size = Size(w - 8.dp.toPx(), h - 8.dp.toPx()),
                        cornerRadius = CornerRadius(12.dp.toPx()),
                        style = Stroke(width = 1.5.dp.toPx())
                    )
                    
                    drawRoundRect(
                        color = borderColor.copy(alpha = 0.6f),
                        topLeft = Offset(8.dp.toPx(), 8.dp.toPx()),
                        size = Size(w - 16.dp.toPx(), h - 16.dp.toPx()),
                        cornerRadius = CornerRadius(10.dp.toPx()),
                        style = Stroke(width = 0.8.dp.toPx())
                    )
                    
                    // Small beautiful corner accent circles
                    val corners = listOf(
                        Offset(8.dp.toPx(), 8.dp.toPx()),
                        Offset(w - 8.dp.toPx(), 8.dp.toPx()),
                        Offset(8.dp.toPx(), h - 8.dp.toPx()),
                        Offset(w - 8.dp.toPx(), h - 8.dp.toPx())
                    )
                    corners.forEach { pt ->
                        drawCircle(color = borderColor, radius = 4.dp.toPx(), center = pt)
                    }
                }
                
                // Content inside the page
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp, vertical = 14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Page Header (Surah title, Page, Juz)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentSurahMetadata.arabicName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = decorationColor
                        )
                        Box(
                            modifier = Modifier
                                .background(borderColor.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "صفحة ${formatArabicNumber(currentPage)}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = decorationColor
                            )
                        }
                        Text(
                            text = getJuzNameForPage(currentPage),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = decorationColor
                        )
                    }
                    
                    // Simple thin line below header
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(borderColor.copy(alpha = 0.5f))
                    )
                    
                    // Display loading or verses
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(color = decorationColor)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Reading from authentic Quran Cache...",
                                    fontSize = 12.sp,
                                    color = decorationColor
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = decorationColor
                                )
                            }
                        }
                    } else if (networkError != null) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.WifiOff, contentDescription = null, tint = Color.Red, modifier = Modifier.size(48.dp))
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = networkError!!,
                                    fontSize = 12.sp,
                                    color = Color.Red,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { currentPage = currentPage }, // trigger reload
                                    colors = ButtonDefaults.buttonColors(containerColor = decorationColor)
                                ) {
                                    Text("Retry Connection")
                                }
                            }
                        }
                    } else {
                        // Display actual vector verses mimicking 15 lines layout
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(versesForPage) { verse ->
                                val isActive = activeVerseId == verse.id
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp, horizontal = 2.dp)
                                        .clickable {
                                            playVerse(verse)
                                        },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isActive) decorationColor.copy(alpha = 0.08f) else Color.Transparent
                                    ),
                                    border = if (isActive) BorderStroke(1.5.dp, decorationColor) else null,
                                    elevation = CardDefaults.cardElevation(defaultElevation = if (isActive) 1.dp else 0.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        // Arabic Calligraphy Row with TextDirection.ContentOrRtl
                                        Text(
                                            text = verse.textArabic,
                                            fontSize = arabicFontSize.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = txtArabicColor,
                                            textAlign = TextAlign.Center,
                                            lineHeight = (arabicFontSize + 12).sp,
                                            fontFamily = getFontFamily(quranFontFamily),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            style = LocalTextStyle.current.copy(
                                                textDirection = TextDirection.ContentOrRtl
                                            )
                                        )
                                        
                                        // Urdu Translation (optional)
                                        if (isUrduEnabled) {
                                            Text(
                                                text = verse.textUrdu,
                                                fontSize = urduFontSize.sp,
                                                color = txtUrduColor,
                                                textAlign = TextAlign.Center,
                                                lineHeight = (urduFontSize + 6).sp,
                                                fontFamily = getFontFamily(quranFontFamily),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 6.dp),
                                                style = LocalTextStyle.current.copy(
                                                    textDirection = TextDirection.ContentOrRtl
                                                )
                                            )
                                        }
                                        
                                        // English Translation (optional)
                                        if (isEnglishEnabled) {
                                            Text(
                                                text = verse.textEnglish,
                                                fontSize = (urduFontSize - 2).sp,
                                                color = txtUrduColor.copy(alpha = 0.8f),
                                                textAlign = TextAlign.Center,
                                                lineHeight = (urduFontSize + 4).sp,
                                                fontFamily = getFontFamily(quranFontFamily),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 6.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    // Page Footer & Pagination Controls
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { if (currentPage > 1) currentPage-- },
                            enabled = currentPage > 1,
                            colors = ButtonDefaults.buttonColors(containerColor = decorationColor)
                        ) {
                            Icon(Icons.Default.ArrowBackIos, contentDescription = "Prev", modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Prev", fontSize = 11.sp)
                        }
                        
                        Text(
                            text = "صفحة ${currentPage} من ٦٠٤",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = decorationColor
                        )
                        
                        Button(
                            onClick = { if (currentPage < 604) currentPage++ },
                            enabled = currentPage < 604,
                            colors = ButtonDefaults.buttonColors(containerColor = decorationColor)
                        ) {
                            Text("Next", fontSize = 11.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForwardIos, contentDescription = "Next", modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }
        }
    }
}

// --- SETTINGS VIEW ---

@Composable
fun QuranSettingsView(
    arabicFontSize: Float,
    onArabicFontSizeChange: (Float) -> Unit,
    urduFontSize: Float,
    onUrduFontSizeChange: (Float) -> Unit,
    isUrduEnabled: Boolean,
    onUrduToggle: (Boolean) -> Unit,
    isEnglishEnabled: Boolean,
    onEnglishToggle: (Boolean) -> Unit,
    readerTheme: String,
    onThemeChange: (String) -> Unit,
    quranFontFamily: String,
    onFontFamilyChange: (String) -> Unit,
    cachedCount: Int,
    viewModel: StudentKitViewModel,
    themeColors: QuranThemeColors,
    onUpdateCache: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isDownloadingAll by remember { mutableStateOf(false) }
    var downloadProgress by remember { mutableStateOf(0f) }
    var isDownloadingPara12 by remember { mutableStateOf(false) }
    var para12Progress by remember { mutableStateOf(0f) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text("Quran Display Settings", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
        }
        
        // Font size control
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = themeColors.cardColor),
                border = BorderStroke(1.dp, themeColors.borderColor.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Arabic Headings Font Size", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = themeColors.txtUrduColor)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Slider(
                            value = arabicFontSize,
                            onValueChange = onArabicFontSizeChange,
                            valueRange = 20f..40f,
                            modifier = Modifier.weight(1f),
                            colors = SliderDefaults.colors(
                                thumbColor = themeColors.decorationColor,
                                activeTrackColor = themeColors.decorationColor,
                                inactiveTrackColor = themeColors.borderColor.copy(alpha = 0.3f)
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("${arabicFontSize.toInt()} sp", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = themeColors.txtUrduColor)
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text("Translation Font Size", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = themeColors.txtUrduColor)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Slider(
                            value = urduFontSize,
                            onValueChange = onUrduFontSizeChange,
                            valueRange = 12f..24f,
                            modifier = Modifier.weight(1f),
                            colors = SliderDefaults.colors(
                                thumbColor = themeColors.decorationColor,
                                activeTrackColor = themeColors.decorationColor,
                                inactiveTrackColor = themeColors.borderColor.copy(alpha = 0.3f)
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("${urduFontSize.toInt()} sp", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = themeColors.txtUrduColor)
                    }
                }
            }
        }
        
        // Toggles
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = themeColors.cardColor),
                border = BorderStroke(1.dp, themeColors.borderColor.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Urdu Translation", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = themeColors.txtUrduColor)
                        Switch(
                            checked = isUrduEnabled, 
                            onCheckedChange = onUrduToggle,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = themeColors.decorationColor,
                                checkedTrackColor = themeColors.decorationColor.copy(alpha = 0.5f),
                                uncheckedThumbColor = themeColors.borderColor,
                                uncheckedTrackColor = themeColors.borderColor.copy(alpha = 0.2f)
                            )
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("English Translation", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = themeColors.txtUrduColor)
                        Switch(
                            checked = isEnglishEnabled, 
                            onCheckedChange = onEnglishToggle,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = themeColors.decorationColor,
                                checkedTrackColor = themeColors.decorationColor.copy(alpha = 0.5f),
                                uncheckedThumbColor = themeColors.borderColor,
                                uncheckedTrackColor = themeColors.borderColor.copy(alpha = 0.2f)
                            )
                        )
                    }
                }
            }
        }
        
        // Theme selection
        item {
            Text("Reader Palette Theme", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Beige", "Green", "White", "Dark").forEach { th ->
                    val isSelected = readerTheme == th
                    val boxColors = getQuranThemeColors(th)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(boxColors.bgColor)
                            .border(
                                width = if (isSelected) 2.5.dp else 1.dp,
                                color = if (isSelected) themeColors.decorationColor else themeColors.borderColor.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onThemeChange(th) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = th,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = boxColors.txtArabicColor
                        )
                    }
                }
            }
        }

        // Font Family Selection
        item {
            Text("Font Style", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Serif", "Sans-Serif", "Monospace", "Default").forEach { fn ->
                    val isSelected = quranFontFamily == fn
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) themeColors.decorationColor else themeColors.cardColor)
                            .border(
                                width = if (isSelected) 2.5.dp else 1.dp,
                                color = if (isSelected) themeColors.decorationColor else themeColors.borderColor.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onFontFamilyChange(fn) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = fn,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) themeColors.cardColor else themeColors.txtUrduColor,
                            fontFamily = getFontFamily(fn)
                        )
                    }
                }
            }
        }
        
        // Caching View
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = themeColors.cardColor),
                border = BorderStroke(1.5.dp, themeColors.decorationColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📶 Offline Storage & Parity Boost",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = themeColors.decorationColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Current Cached Verses: $cachedCount / 6,236",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.txtUrduColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Download the complete Quran with authentic translations to enjoy 100% offline, zero-network reading.",
                        fontSize = 11.sp,
                        color = themeColors.txtUrduColor.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (isDownloadingPara12) {
                        Column {
                            LinearProgressIndicator(
                                progress = para12Progress,
                                color = themeColors.decorationColor,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Caching Para 1 & 2 (Urdu Translation): ${(para12Progress * 100).toInt()}% Done...",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = themeColors.decorationColor
                            )
                        }
                    } else if (isDownloadingAll) {
                        Column {
                            LinearProgressIndicator(
                                progress = downloadProgress,
                                color = themeColors.decorationColor,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Caching Holy Quran: ${(downloadProgress * 100).toInt()}% Done...",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = themeColors.decorationColor
                            )
                        }
                    } else {
                        Button(
                            onClick = {
                                isDownloadingPara12 = true
                                coroutineScope.launch(Dispatchers.IO) {
                                    try {
                                        val totalPages = 41
                                        for (page in 1..totalPages) {
                                            val verses = downloadQuranPage(page)
                                            if (verses.isNotEmpty()) {
                                                viewModel.insertQuranVerses(verses)
                                            }
                                            withContext(Dispatchers.Main) {
                                                para12Progress = page / totalPages.toFloat()
                                            }
                                        }
                                        withContext(Dispatchers.Main) {
                                            isDownloadingPara12 = false
                                            Toast.makeText(context, "Para 1 & 2 Urdu Translation cached successfully!", Toast.LENGTH_LONG).show()
                                            onUpdateCache()
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            isDownloadingPara12 = false
                                            Toast.makeText(context, "Download interrupted", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = themeColors.decorationColor),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Download Para 1 & 2 Urdu Translation (100% Offline)", color = themeColors.cardColor)
                        }
                        
                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                isDownloadingAll = true
                                coroutineScope.launch(Dispatchers.IO) {
                                    try {
                                        // Fetch and cache all pages in order
                                        for (p in 1..604) {
                                            val verses = downloadQuranPage(p)
                                            if (verses.isNotEmpty()) {
                                                viewModel.insertQuranVerses(verses)
                                            }
                                            withContext(Dispatchers.Main) {
                                                downloadProgress = p / 604f
                                            }
                                        }
                                        withContext(Dispatchers.Main) {
                                            isDownloadingAll = false
                                            Toast.makeText(context, "Quran cached successfully for offline use!", Toast.LENGTH_LONG).show()
                                            onUpdateCache()
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            isDownloadingAll = false
                                            Toast.makeText(context, "Download interrupted", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = themeColors.decorationColor.copy(alpha = 0.85f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Pre-Download Complete Quran Offline", color = themeColors.cardColor)
                        }
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.clearCachedQuran()
                                    kotlinx.coroutines.delay(400)
                                    onUpdateCache()
                                    Toast.makeText(context, "Offline Cache cleared.", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.82f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Clear Offline Cache", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

// --- COMPACT QURAN METADATA ENGINE moved to QuranData.kt ---

// --- DYNAMIC AL QURAN API CRAWLER ---

suspend fun downloadQuranPage(pageNum: Int): List<CachedQuranVerse> {
    val versesList = mutableListOf<CachedQuranVerse>()
    try {
        // Fetch Arabic text
        val urlAr = URL("https://api.alquran.cloud/v1/page/$pageNum/quran-uthmani")
        val connAr = urlAr.openConnection() as HttpURLConnection
        connAr.requestMethod = "GET"
        connAr.connectTimeout = 8000
        connAr.readTimeout = 8000
        
        if (connAr.responseCode == 200) {
            val readerAr = BufferedReader(InputStreamReader(connAr.inputStream))
            val sbAr = StringBuilder()
            var line: String?
            while (readerAr.readLine().also { line = it } != null) {
                sbAr.append(line)
            }
            readerAr.close()
            
            // Parse Arabic
            val objAr = JSONObject(sbAr.toString())
            val dataAr = objAr.getJSONObject("data")
            val ayahsAr = dataAr.getJSONArray("ayahs")
            
            // Fetch Urdu translation
            val urlUr = URL("https://api.alquran.cloud/v1/page/$pageNum/ur.jalandhry")
            val connUr = urlUr.openConnection() as HttpURLConnection
            connUr.requestMethod = "GET"
            connUr.connectTimeout = 8000
            connUr.readTimeout = 8000
            
            val urduMap = mutableMapOf<String, String>()
            if (connUr.responseCode == 200) {
                val readerUr = BufferedReader(InputStreamReader(connUr.inputStream))
                val sbUr = StringBuilder()
                while (readerUr.readLine().also { line = it } != null) {
                    sbUr.append(line)
                }
                readerUr.close()
                
                val objUr = JSONObject(sbUr.toString())
                val dataUr = objUr.getJSONObject("data")
                val ayahsUr = dataUr.getJSONArray("ayahs")
                for (i in 0 until ayahsUr.length()) {
                    val ayah = ayahsUr.getJSONObject(i)
                    val key = "${ayah.getJSONObject("surah").getInt("number")}_${ayah.getInt("numberInSurah")}"
                    urduMap[key] = ayah.getString("text")
                }
            }
            
            // Identify missing/repeated Arabic in Urdu translations
            val missingTranslations = mutableListOf<Pair<String, String>>()
            for (i in 0 until ayahsAr.length()) {
                val ayah = ayahsAr.getJSONObject(i)
                val surahNum = ayah.getJSONObject("surah").getInt("number")
                val ayahNum = ayah.getInt("numberInSurah")
                val key = "${surahNum}_${ayahNum}"
                
                val textArabic = ayah.getString("text")
                val textUrdu = urduMap[key]
                
                if (textUrdu == null || 
                    textUrdu.isEmpty() || 
                    textUrdu == "اردو ترجمہ دستیاب نہیں ہے۔" || 
                    textUrdu.trim() == textArabic.trim()
                ) {
                    missingTranslations.add(key to textArabic)
                }
            }
            
            // Batch translate using Gemini API if missing
            if (missingTranslations.isNotEmpty()) {
                try {
                    val aiTranslations = translateVersesToUrduWithGemini(missingTranslations)
                    aiTranslations.forEach { (key, translation) ->
                        if (translation.isNotEmpty()) {
                            urduMap[key] = translation
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
            // Match and compile
            for (i in 0 until ayahsAr.length()) {
                val ayah = ayahsAr.getJSONObject(i)
                val surahNum = ayah.getJSONObject("surah").getInt("number")
                val ayahNum = ayah.getInt("numberInSurah")
                val key = "${surahNum}_${ayahNum}"
                
                val textArabic = ayah.getString("text")
                val textUrdu = urduMap[key] ?: "اردو ترجمہ دستیاب نہیں ہے۔"
                val textEnglish = "English translation cached offline."
                
                versesList.add(
                    CachedQuranVerse(
                        id = key,
                        surahNumber = surahNum,
                        verseNumber = ayahNum,
                        juz = ayah.getInt("juz"),
                        page = pageNum,
                        textArabic = textArabic,
                        textUrdu = textUrdu,
                        textEnglish = textEnglish
                    )
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return versesList
}

// Preload standard short Surahs so offline mode works instantly on fresh install
fun savePreloadedSurahs(viewModel: StudentKitViewModel) {
    val verses = listOf(
        CachedQuranVerse(
            id = "1_1", surahNumber = 1, verseNumber = 1, juz = 1, page = 1,
            textArabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            textUrdu = "شروع اللہ کے نام سے جو بڑا مہربان نہایت رحم والا ہے۔",
            textEnglish = "In the name of Allah, the Entirely Merciful, the Especially Merciful."
        ),
        CachedQuranVerse(
            id = "1_2", surahNumber = 1, verseNumber = 2, juz = 1, page = 1,
            textArabic = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
            textUrdu = "سب تعریفیں اللہ ہی کے لیے ہیں جو تمام جہانوں کا پالنے والا ہے۔",
            textEnglish = "[All] praise is [due] to Allah, Lord of the worlds -"
        ),
        CachedQuranVerse(
            id = "1_3", surahNumber = 1, verseNumber = 3, juz = 1, page = 1,
            textArabic = "الرَّحْمَٰنِ الرَّحِيمِ",
            textUrdu = "بڑا مہربان نہایت رحم والا ہے۔",
            textEnglish = "The Entirely Merciful, the Especially Merciful,"
        ),
        CachedQuranVerse(
            id = "1_4", surahNumber = 1, verseNumber = 4, juz = 1, page = 1,
            textArabic = "مَالِكِ يَوْمِ الدِّينِ",
            textUrdu = "روزِ جزا کا مالک ہے۔",
            textEnglish = "Sovereign of the Day of Recompense."
        ),
        CachedQuranVerse(
            id = "1_5", surahNumber = 1, verseNumber = 5, juz = 1, page = 1,
            textArabic = "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ",
            textUrdu = "ہم تیری ہی عبادت کرتے ہیں اور تجھ ہی سے مدد مانگتے ہیں۔",
            textEnglish = "It is You we worship and You we ask for help."
        ),
        CachedQuranVerse(
            id = "1_6", surahNumber = 1, verseNumber = 6, juz = 1, page = 1,
            textArabic = "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ",
            textUrdu = "ہمیں سیدھے راستے پر چلا۔",
            textEnglish = "Guide us to the straight path -"
        ),
        CachedQuranVerse(
            id = "1_7", surahNumber = 1, verseNumber = 7, juz = 1, page = 1,
            textArabic = "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
            textUrdu = "ان لوگوں کے راستے پر جن پر تو نے انعام کیا، نہ کہ ان کے راستے پر جن پر تیرا غضب ہوا اور نہ ہی گمراہوں کے راستے۔",
            textEnglish = "The path of those upon whom You have bestowed favor, not of those who have evoked [Your] anger or of those who are astray."
        ),
        // Surah Al-Ikhlas
        CachedQuranVerse(
            id = "112_1", surahNumber = 112, verseNumber = 1, juz = 30, page = 604,
            textArabic = "قُلْ هُوَ اللَّهُ أَحَدٌ",
            textUrdu = "آپ کہه دیجئے کہ وه اللہ ایک ہی ہے۔",
            textEnglish = "Say, \"He is Allah, [who is] One,"
        ),
        CachedQuranVerse(
            id = "112_2", surahNumber = 112, verseNumber = 2, juz = 30, page = 604,
            textArabic = "اللَّهُ الصَّمَدُ",
            textUrdu = "اللہ بے نیاز ہے۔",
            textEnglish = "Allah, the Eternal Refuge."
        ),
        CachedQuranVerse(
            id = "112_3", surahNumber = 112, verseNumber = 3, juz = 30, page = 604,
            textArabic = "لَمْ يَلِدْ وَلَمْ يُولَدْ",
            textUrdu = "نہ اس سے کوئی پیدا ہوا اور نہ وه کسی سے پیدا ہوا ہے۔",
            textEnglish = "He neither begets nor is born,"
        ),
        CachedQuranVerse(
            id = "112_4", surahNumber = 112, verseNumber = 4, juz = 30, page = 604,
            textArabic = "وَلَمْ يَكُنْ لَّهُ كُفُوًا أَحَدٌ",
            textUrdu = "اور نہ ہی اس کا کوئی ہمسر ہے۔",
            textEnglish = "Nor is there to Him any equivalent.\""
        ),
        // Surah Al-Falaq
        CachedQuranVerse(
            id = "113_1", surahNumber = 113, verseNumber = 1, juz = 30, page = 604,
            textArabic = "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ",
            textUrdu = "کہہ دیجئے! کہ میں صبح کے رب کی پناہ میں آتا ہوں۔",
            textEnglish = "Say, \"I seek refuge in the Lord of daybreak"
        ),
        CachedQuranVerse(
            id = "113_2", surahNumber = 113, verseNumber = 2, juz = 30, page = 604,
            textArabic = "مِنْ شَرِّ مَا خَلَقَ",
            textUrdu = "ہر اس چیز کے شر سے جو اس نے پیدا کی ہے۔",
            textEnglish = "From the evil of that which He created"
        ),
        CachedQuranVerse(
            id = "113_3", surahNumber = 113, verseNumber = 3, juz = 30, page = 604,
            textArabic = "وَمِنْ شَرِّ غَاسِقٍ إِذَا وَقَبَ",
            textUrdu = "اور اندھیری رات کے شر سے جب وہ چھا جائے۔",
            textEnglish = "And from the evil of darkness when it settles"
        ),
        CachedQuranVerse(
            id = "113_4", surahNumber = 113, verseNumber = 4, juz = 30, page = 604,
            textArabic = "وَمِنْ شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ",
            textUrdu = "اور گرہوں میں پھونکنے والیوں کے شر سے۔",
            textEnglish = "And from the evil of the blowers in knots"
        ),
        CachedQuranVerse(
            id = "113_5", surahNumber = 113, verseNumber = 5, juz = 30, page = 604,
            textArabic = "وَمِنْ شَرِّ حَاسِدٍ إِذَا حَسَدَ",
            textUrdu = "اور حسد کرنے والے کے شر سے جب وہ حسد کرے۔",
            textEnglish = "And from the evil of an envier when he envies.\""
        ),
        // Surah An-Nas
        CachedQuranVerse(
            id = "114_1", surahNumber = 114, verseNumber = 1, juz = 30, page = 604,
            textArabic = "قُلْ أَعُوذُ بِرَبِّ النَّاسِ",
            textUrdu = "کہہ دیجئے! کہ میں انسانوں کے پروردگار کی پناہ میں آتا ہوں۔",
            textEnglish = "Say, \"I seek refuge in the Lord of mankind,"
        ),
        CachedQuranVerse(
            id = "114_2", surahNumber = 114, verseNumber = 2, juz = 30, page = 604,
            textArabic = "مَلِكِ النَّاسِ",
            textUrdu = "انسانوں کے بادشاہ کی (پناہ میں)",
            textEnglish = "The Sovereign of mankind,"
        ),
        CachedQuranVerse(
            id = "114_3", surahNumber = 114, verseNumber = 3, juz = 30, page = 604,
            textArabic = "إِلَٰهِ النَّاسِ",
            textUrdu = "انسانوں کے معبود کی (پناہ میں)",
            textEnglish = "The God of mankind,"
        ),
        CachedQuranVerse(
            id = "114_4", surahNumber = 114, verseNumber = 4, juz = 30, page = 604,
            textArabic = "مِنْ شَرِّ الْوَسْوَاسِ الْخَنَّاسِ",
            textUrdu = "وسوسہ ڈالنے والے، پیچھے ہٹ جانے والے کے شر سے۔",
            textEnglish = "From the evil of the retreating whisperer"
        ),
        CachedQuranVerse(
            id = "114_5", surahNumber = 114, verseNumber = 5, juz = 30, page = 604,
            textArabic = "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ",
            textUrdu = "جو لوگوں کے سینوں میں وسوسے ڈالتا ہے۔",
            textEnglish = "Who whispers [evil] into the breasts of mankind"
        ),
        CachedQuranVerse(
            id = "114_6", surahNumber = 114, verseNumber = 6, juz = 30, page = 604,
            textArabic = "مِنَ الْجِنَّةِ وَالنَّاسِ",
            textUrdu = "خواہ وہ جنوں میں سے ہو یا انسانوں میں سے۔",
            textEnglish = "From among the jinn and mankind.\""
        )
    )
    viewModel.insertQuranVerses(verses)
}

// Utility numbers mapping
fun formatArabicNumber(num: Int): String {
    val arChars = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
    val str = num.toString()
    val sb = StringBuilder()
    for (ch in str) {
        if (ch in '0'..'9') {
            sb.append(arChars[ch - '0'])
        } else {
            sb.append(ch)
        }
    }
    return sb.toString()
}

// --- GEMINI BATCH TRANSLATOR SERVICE & REST IMPLEMENTATION ---

interface GeminiTranslationService {
    @retrofit2.http.POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @retrofit2.http.Query("key") apiKey: String,
        @retrofit2.http.Body request: okhttp3.RequestBody
    ): okhttp3.ResponseBody
}

suspend fun translateVersesToUrduWithGemini(missingVerses: List<Pair<String, String>>): Map<String, String> = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
    val apiKey = com.example.BuildConfig.GEMINI_API_KEY
    if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") return@withContext emptyMap()
    
    // Escapes and joins verses into a mini JSON structure for the prompt
    val versesArrayStr = missingVerses.map { (key, text) ->
        val escapedText = text.replace("\"", "\\\"").replace("\n", " ")
        "{\"id\": \"$key\", \"text\": \"$escapedText\"}"
    }.joinToString(",")
    
    val prompt = """
        You are an expert Islamic scholar and Urdu translator.
        Translate the following Arabic Quranic verses into highly authentic, elegant, and standard Urdu translation (classical Fateh Muhammad Jalandhri style).
        Return the response ONLY as a JSON object where the keys are the verse IDs (matching the 'id' fields in the input exactly, like '1_1') and the values are the Urdu translations.
        Do not include markdown formatting like ```json or any other text. Return raw JSON text only.
        
        Verses to translate:
        [$versesArrayStr]
    """.trimIndent()
    
    val escapedPrompt = prompt.replace("\"", "\\\"").replace("\n", "\\n")
    
    val requestJson = """
        {
          "contents": [
            {
              "parts": [
                {
                  "text": "$escapedPrompt"
                }
              ]
            }
          ],
          "generationConfig": {
            "responseMimeType": "application/json",
            "temperature": 0.2
          }
        }
    """.trimIndent()
    
    try {
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = requestJson.toRequestBody(mediaType)
        
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(
                okhttp3.OkHttpClient.Builder()
                    .connectTimeout(45, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(45, java.util.concurrent.TimeUnit.SECONDS)
                    .build()
            )
            .build()
            
        val service = retrofit.create(GeminiTranslationService::class.java)
        val responseBody = service.generateContent(apiKey, body)
        val responseString = responseBody.string()
        
        val responseObj = org.json.JSONObject(responseString)
        val candidates = responseObj.getJSONArray("candidates")
        val firstCandidate = candidates.getJSONObject(0)
        val contentObj = firstCandidate.getJSONObject("content")
        val parts = contentObj.getJSONArray("parts")
        val textPart = parts.getJSONObject(0).getString("text")
        
        val parsedJson = org.json.JSONObject(textPart)
        val result = mutableMapOf<String, String>()
        parsedJson.keys().forEach { key ->
            result[key] = parsedJson.getString(key)
        }
        return@withContext result
    } catch (e: Exception) {
        e.printStackTrace()
    }
    emptyMap()
}
