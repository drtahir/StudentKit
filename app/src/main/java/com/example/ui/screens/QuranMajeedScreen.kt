package com.example.ui.screens

import android.content.Context
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
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import java.io.File
import java.io.FileOutputStream
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
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
    val decorationColor: Color,
    val multiColors: List<Color>? = null
)

// --- MULTI-QARI VOICE RECITER ENGINE ---
data class Qari(
    val id: String,
    val name: String,
    val arabicName: String,
    val folder: String,
    val style: String
)

val QARI_LIST = listOf(
    Qari("alafasy", "Mishary Rashid Alafasy", "مشاري راشد العفاسي", "Alafasy_128kbps", "Murattal"),
    Qari("abdulbasit", "Abdul Basit Abdul Samad", "عبد الباسط عبد الصمد", "Abdul_Basit_Murattal_192kbps", "Murattal"),
    Qari("maher", "Maher Al-Muaiqly", "ماهر المعيقلي", "Maher_AlMuaiqly_64kbps", "Murattal"),
    Qari("shatri", "Abu Bakr Al-Shatri", "أبو بكر الشاطري", "Abu_Bakr_Ash-Shaatree_128kbps", "Murattal"),
    Qari("shuraim", "Saud Al-Shuraim", "سعود الشريم", "Saood_ash-Shuraym_128kbps", "Murattal"),
    Qari("ghamdi", "Saad Al-Ghamdi", "سعد الغامدي", "Ghamadi_40kbps", "Murattal")
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
            borderColor = Color(0xFFCED4DA),
            decorationColor = Color(0xFF0F5132)
        )
        "Gold" -> QuranThemeColors(
            bgColor = Color(0xFFFCF8F2),
            cardColor = Color(0xFFFDFBF7),
            txtArabicColor = Color(0xFF7A5C1F),
            txtUrduColor = Color(0xFF5E4913),
            borderColor = Color(0xFFD4AF37),
            decorationColor = Color(0xFFB8860B)
        )
        "Multi" -> QuranThemeColors(
            bgColor = Color(0xFFF8F9FA),
            cardColor = Color.White,
            txtArabicColor = Color.Black,
            txtUrduColor = Color.DarkGray,
            borderColor = Color(0xFFE0E0E0),
            decorationColor = Color(0xFF673AB7),
            multiColors = listOf(
                Color(0xFF0F5132), // Green
                Color(0xFFB71C1C), // Deep Red
                Color(0xFF0D47A1), // Blue
                Color(0xFFE65100), // Orange
                Color(0xFF4A148C), // Purple
                Color(0xFF006064)  // Teal
            )
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

class QuranSettingsManager(context: Context) {
    private val prefs = context.getSharedPreferences("quran_display_settings_v3", Context.MODE_PRIVATE)

    var arabicFontSize: Float
        get() = prefs.getFloat("arabic_font_size", 28f)
        set(value) = prefs.edit().putFloat("arabic_font_size", value).apply()

    var urduFontSize: Float
        get() = prefs.getFloat("urdu_font_size", 16f)
        set(value) = prefs.edit().putFloat("urdu_font_size", value).apply()

    var isUrduEnabled: Boolean
        get() = prefs.getBoolean("is_urdu_enabled", true)
        set(value) = prefs.edit().putBoolean("is_urdu_enabled", value).apply()

    var isEnglishEnabled: Boolean
        get() = prefs.getBoolean("is_english_enabled", false)
        set(value) = prefs.edit().putBoolean("is_english_enabled", value).apply()

    var readerTheme: String
        get() = prefs.getString("reader_theme", "Beige") ?: "Beige"
        set(value) = prefs.edit().putString("reader_theme", value).apply()

    var quranFontFamily: String
        get() = prefs.getString("quran_font_family", "Serif") ?: "Serif"
        set(value) = prefs.edit().putString("quran_font_family", value).apply()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranMajeedScreen(
    viewModel: StudentKitViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val settingsManager = remember { QuranSettingsManager(context) }
    
    // UI State
    var activeTab by remember { mutableStateOf(0) } // 0 = Surahs, 1 = Juz, 2 = Bookmarks, 3 = Settings
    var searchQuery by remember { mutableStateOf("") }
    
    // Reader State
    var selectedSurah by remember { mutableStateOf<SurahMetadata?>(null) }
    var selectedJuz by remember { mutableStateOf<JuzMetadata?>(null) }
    var readerModePage by remember { mutableStateOf<Int?>(null) } // if not null, reading page-by-page
    
    // Settings state persisted in SharedPreferences
    var arabicFontSize by remember { mutableStateOf(settingsManager.arabicFontSize) }
    var urduFontSize by remember { mutableStateOf(settingsManager.urduFontSize) }
    var isUrduTranslationEnabled by remember { mutableStateOf(settingsManager.isUrduEnabled) }
    var isEnglishTranslationEnabled by remember { mutableStateOf(settingsManager.isEnglishEnabled) }
    var readerTheme by remember { mutableStateOf(settingsManager.readerTheme) }
    var isImmersiveMode by remember { mutableStateOf(false) }
    var quranFontFamily by remember { mutableStateOf(settingsManager.quranFontFamily) }
    var selectedQari by remember { mutableStateOf(QARI_LIST[0]) }
    var showQariSelectorDialog by remember { mutableStateOf(false) }

    val updateArabicFontSize = { newSize: Float ->
        arabicFontSize = newSize
        settingsManager.arabicFontSize = newSize
    }
    val updateUrduFontSize = { newSize: Float ->
        urduFontSize = newSize
        settingsManager.urduFontSize = newSize
    }
    val updateUrduToggle = { enabled: Boolean ->
        isUrduTranslationEnabled = enabled
        settingsManager.isUrduEnabled = enabled
    }
    val updateEnglishToggle = { enabled: Boolean ->
        isEnglishTranslationEnabled = enabled
        settingsManager.isEnglishEnabled = enabled
    }
    val updateTheme = { newTheme: String ->
        readerTheme = newTheme
        settingsManager.readerTheme = newTheme
    }
    val updateFontFamily = { newFont: String ->
        quranFontFamily = newFont
        settingsManager.quranFontFamily = newFont
    }
    
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
                                text = "114 Surahs • 6,236 Verses • Multi-Qari Voice Recitations",
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
                    onArabicFontSizeChange = updateArabicFontSize,
                    urduFontSize = urduFontSize,
                    onUrduFontSizeChange = updateUrduFontSize,
                    isUrduEnabled = isUrduTranslationEnabled,
                    onUrduToggle = updateUrduToggle,
                    isEnglishEnabled = isEnglishTranslationEnabled,
                    onEnglishToggle = updateEnglishToggle,
                    themeName = readerTheme,
                    onThemeChange = updateTheme,
                    immersiveMode = isImmersiveMode,
                    onToggleImmersive = { isImmersiveMode = !isImmersiveMode },
                    quranFontFamily = quranFontFamily,
                    onFontFamilyChange = updateFontFamily,
                    selectedQari = selectedQari,
                    onSelectQari = { selectedQari = it }
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
                    onArabicFontSizeChange = updateArabicFontSize,
                    urduFontSize = urduFontSize,
                    onUrduFontSizeChange = updateUrduFontSize,
                    isUrduEnabled = isUrduTranslationEnabled,
                    onUrduToggle = updateUrduToggle,
                    isEnglishEnabled = isEnglishTranslationEnabled,
                    onEnglishToggle = updateEnglishToggle,
                    themeName = readerTheme,
                    onThemeChange = updateTheme,
                    immersiveMode = isImmersiveMode,
                    onToggleImmersive = { isImmersiveMode = !isImmersiveMode },
                    quranFontFamily = quranFontFamily,
                    onFontFamilyChange = updateFontFamily,
                    selectedQari = selectedQari,
                    onSelectQari = { selectedQari = it }
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
                                onArabicFontSizeChange = updateArabicFontSize,
                                urduFontSize = urduFontSize,
                                onUrduFontSizeChange = updateUrduFontSize,
                                isUrduEnabled = isUrduTranslationEnabled,
                                onUrduToggle = updateUrduToggle,
                                isEnglishEnabled = isEnglishTranslationEnabled,
                                onEnglishToggle = updateEnglishToggle,
                                readerTheme = readerTheme,
                                onThemeChange = updateTheme,
                                quranFontFamily = quranFontFamily,
                                onFontFamilyChange = updateFontFamily,
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = surah.englishName,
                            fontSize = (urduFontSize * 0.95f).sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.decorationColor,
                            fontFamily = getFontFamily(quranFontFamily)
                        )
                        Surface(
                            color = themeColors.decorationColor.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "${surah.numberOfAyahs} Ayahs",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = themeColors.decorationColor,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${surah.revelationType.capitalize()} • Total ${surah.numberOfAyahs} Verses",
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

// --- BEAUTIFUL FULL SCREEN PAGE & SURAH READER COMPOSABLE ---

@Composable
fun QuranDisplaySettingsDialog(
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
    selectedQari: Qari = QARI_LIST[0],
    onOpenQariSelector: () -> Unit = {},
    themeColors: QuranThemeColors,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done", fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
            }
        },
        title = {
            Text(
                "Quran Display Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = themeColors.decorationColor
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Arabic Font Size
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Arabic Font Size", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = themeColors.txtUrduColor)
                        Text("${arabicFontSize.toInt()} sp", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
                    }
                    Slider(
                        value = arabicFontSize,
                        onValueChange = onArabicFontSizeChange,
                        valueRange = 20f..44f,
                        colors = SliderDefaults.colors(
                            thumbColor = themeColors.decorationColor,
                            activeTrackColor = themeColors.decorationColor,
                            inactiveTrackColor = themeColors.borderColor.copy(alpha = 0.3f)
                        )
                    )
                }

                // Translation Font Size
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Translation Font Size", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = themeColors.txtUrduColor)
                        Text("${urduFontSize.toInt()} sp", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
                    }
                    Slider(
                        value = urduFontSize,
                        onValueChange = onUrduFontSizeChange,
                        valueRange = 12f..26f,
                        colors = SliderDefaults.colors(
                            thumbColor = themeColors.decorationColor,
                            activeTrackColor = themeColors.decorationColor,
                            inactiveTrackColor = themeColors.borderColor.copy(alpha = 0.3f)
                        )
                    )
                }

                // Urdu & English Toggles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Urdu Translation", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = themeColors.txtUrduColor)
                    Switch(
                        checked = isUrduEnabled,
                        onCheckedChange = onUrduToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = themeColors.decorationColor,
                            checkedTrackColor = themeColors.decorationColor.copy(alpha = 0.4f)
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("English Translation", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = themeColors.txtUrduColor)
                        Text("(Coming Soon)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
                    }
                    Switch(
                        checked = isEnglishEnabled,
                        onCheckedChange = onEnglishToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = themeColors.decorationColor,
                            checkedTrackColor = themeColors.decorationColor.copy(alpha = 0.4f)
                        )
                    )
                }

                // Reader Theme Selector
                Column {
                    Text("Reader Theme Palette", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Beige", "Green", "White", "Dark").forEach { th ->
                            val isSelected = readerTheme == th
                            val boxColors = getQuranThemeColors(th)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(boxColors.bgColor)
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) themeColors.decorationColor else themeColors.borderColor.copy(alpha = 0.4f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { onThemeChange(th) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = th,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = boxColors.txtArabicColor
                                )
                            }
                        }
                    }
                }

                // Font Style Selector
                Column {
                    Text("Font Style", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Serif", "Sans-Serif", "Monospace", "Default").forEach { fn ->
                            val isSelected = quranFontFamily == fn
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) themeColors.decorationColor else themeColors.cardColor)
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) themeColors.decorationColor else themeColors.borderColor.copy(alpha = 0.4f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { onFontFamilyChange(fn) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = fn,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) themeColors.cardColor else themeColors.txtUrduColor,
                                    fontFamily = getFontFamily(fn)
                                )
                            }
                        }
                    }
                }

                // Reciter Voice Qari
                Column {
                    Text("Quran Voice Reciter (Qari)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedButton(
                        onClick = {
                            onDismiss()
                            onOpenQariSelector()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, themeColors.decorationColor)
                    ) {
                        Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = themeColors.decorationColor, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${selectedQari.name} (${selectedQari.style})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.decorationColor
                        )
                    }
                }
            }
        },
        containerColor = themeColors.cardColor,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun QariSelectionDialog(
    selectedQari: Qari,
    onSelectQari: (Qari) -> Unit,
    themeColors: QuranThemeColors,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done", fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = themeColors.decorationColor)
                Text(
                    "Select Qari (Reciter)",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.decorationColor
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Select your preferred world-famous Qari for streaming and offline audio downloads:",
                    fontSize = 11.sp,
                    color = themeColors.txtUrduColor.copy(alpha = 0.8f)
                )

                QARI_LIST.forEach { qari ->
                    val isSelected = selectedQari.id == qari.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelectQari(qari)
                                onDismiss()
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) themeColors.decorationColor.copy(alpha = 0.15f) else themeColors.cardColor
                        ),
                        border = BorderStroke(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) themeColors.decorationColor else themeColors.borderColor.copy(alpha = 0.4f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = qari.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = themeColors.decorationColor
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${qari.arabicName} • ${qari.style}",
                                    fontSize = 11.sp,
                                    color = themeColors.txtUrduColor.copy(alpha = 0.8f)
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = Color(0xFF059669),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        containerColor = themeColors.cardColor,
        shape = RoundedCornerShape(16.dp)
    )
}

suspend fun downloadQuranSurah(surahNum: Int): List<CachedQuranVerse> {
    val versesList = mutableListOf<CachedQuranVerse>()
    try {
        val urlStr = "https://api.alquran.cloud/v1/surah/$surahNum/editions/quran-uthmani,ur.jalandhry,en.transliteration"
        val url = URL(urlStr)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connectTimeout = 8000
        conn.readTimeout = 8000
        
        if (conn.responseCode == 200) {
            val reader = BufferedReader(InputStreamReader(conn.inputStream))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            reader.close()
            
            val jsonRoot = JSONObject(sb.toString())
            val dataArray = jsonRoot.optJSONArray("data")
            
            if (dataArray != null && dataArray.length() >= 2) {
                val arObj = dataArray.getJSONObject(0)
                val urObj = dataArray.getJSONObject(1)
                
                val ayahsAr = arObj.getJSONArray("ayahs")
                val ayahsUr = urObj.getJSONArray("ayahs")
                val ayahsEn = if (dataArray.length() >= 3) dataArray.getJSONObject(2).optJSONArray("ayahs") else null
                
                for (i in 0 until ayahsAr.length()) {
                    val aAr = ayahsAr.getJSONObject(i)
                    val aUr = if (i < ayahsUr.length()) ayahsUr.getJSONObject(i) else null
                    val aEn = if (ayahsEn != null && i < ayahsEn.length()) ayahsEn.getJSONObject(i) else null
                    
                    val verseNum = aAr.getInt("numberInSurah")
                    val key = "${surahNum}_${verseNum}"
                    val textArabic = aAr.getString("text")
                    val textUrdu = aUr?.optString("text") ?: "اردو ترجمہ دستیاب نہیں ہے۔"
                    val textEnglish = aEn?.optString("text") ?: "English translation."
                    val juz = aAr.optInt("juz", 1)
                    val page = aAr.optInt("page", 1)
                    
                    versesList.add(
                        CachedQuranVerse(
                            id = key,
                            surahNumber = surahNum,
                            verseNumber = verseNum,
                            juz = juz,
                            page = page,
                            textArabic = textArabic,
                            textUrdu = textUrdu,
                            textEnglish = textEnglish
                        )
                    )
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    
    // Fallback to page downloading if needed
    if (versesList.isEmpty()) {
        try {
            val startPage = getPageForSurah(surahNum)
            val endPage = if (surahNum < 114) getPageForSurah(surahNum + 1) else 604
            for (p in startPage..endPage) {
                val pageVerses = downloadQuranPage(p)
                val surahOnly = pageVerses.filter { it.surahNumber == surahNum }
                versesList.addAll(surahOnly)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    return versesList
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranPageReader(
    viewModel: StudentKitViewModel,
    surah: SurahMetadata,
    initialPageNum: Int = 0,
    onBack: () -> Unit,
    arabicFontSize: Float,
    onArabicFontSizeChange: (Float) -> Unit,
    urduFontSize: Float,
    onUrduFontSizeChange: (Float) -> Unit,
    isUrduEnabled: Boolean,
    onUrduToggle: (Boolean) -> Unit,
    isEnglishEnabled: Boolean,
    onEnglishToggle: (Boolean) -> Unit,
    themeName: String,
    onThemeChange: (String) -> Unit,
    immersiveMode: Boolean,
    onToggleImmersive: () -> Unit,
    quranFontFamily: String,
    onFontFamilyChange: (String) -> Unit,
    selectedQari: Qari = QARI_LIST[0],
    onSelectQari: (Qari) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    var showDisplaySettings by remember { mutableStateOf(false) }
    var showQariSelectorDialog by remember { mutableStateOf(false) }
    
    // Page state
    var currentPage by remember { mutableStateOf(if (initialPageNum > 0) initialPageNum else getPageForSurah(surah.number)) }
    
    // Loaded verses
    var versesForPage by remember { mutableStateOf<List<CachedQuranVerse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var networkError by remember { mutableStateOf<String?>(null) }
    
    // Auto-scroll and verse highlighting states
    var activeVerseId by remember { mutableStateOf<String?>(null) }
    var autoPlayNextPage by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    // Audio Playback State
    var activeRecitationUrl by remember { mutableStateOf<String?>(null) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlayingAudio by remember { mutableStateOf(false) }

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
        
        val index = versesForPage.indexOfFirst { it.id == verse.id }
        if (index >= 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(index)
            }
        }
        
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val surahFormatted = String.format("%03d", verse.surahNumber)
                val verseFormatted = String.format("%03d", verse.verseNumber)
                val qariFolder = selectedQari.folder
                val urlStr = "https://everyayah.com/data/$qariFolder/$surahFormatted$verseFormatted.mp3"
                val localQariFile = File(context.filesDir, "quran_audio/$qariFolder/$surahFormatted$verseFormatted.mp3")
                val legacyFile = File(context.filesDir, "quran_audio/$surahFormatted$verseFormatted.mp3")
                val dataSource = if (localQariFile.exists() && localQariFile.length() > 500) {
                    localQariFile.absolutePath
                } else if (legacyFile.exists() && legacyFile.length() > 500) {
                    legacyFile.absolutePath
                } else {
                    urlStr
                }
                activeRecitationUrl = dataSource
                
                val mp = MediaPlayer().apply {
                    setDataSource(dataSource)
                    prepare()
                    start()
                }
                withContext(Dispatchers.Main) {
                    mediaPlayer = mp
                    mp.setOnCompletionListener {
                        val nextIndex = index + 1
                        if (nextIndex >= 0 && nextIndex < versesForPage.size) {
                            playVerse(versesForPage[nextIndex])
                        } else {
                            if (currentPage < 604) {
                                autoPlayNextPage = true
                                currentPage++
                            } else {
                                activeVerseId = null
                                isPlayingAudio = false
                                Toast.makeText(context, "Completed Quran Recitation", Toast.LENGTH_SHORT).show()
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
    
    // Fetch Quran verses - Page Mode across whole Quran (pages 1 to 604)
    LaunchedEffect(currentPage) {
        isLoading = true
        networkError = null
        
        viewModel.getCachedVersesForPage(currentPage).first().let { cached ->
            val hasArabicPlaceholder = cached.isNotEmpty() && cached.any { it.textUrdu.isNotEmpty() && it.textUrdu.trim() == it.textArabic.trim() }
            if (cached.isNotEmpty() && !hasArabicPlaceholder) {
                versesForPage = cached
                isLoading = false
                if (autoPlayNextPage) {
                    autoPlayNextPage = false
                    if (versesForPage.isNotEmpty()) playVerse(versesForPage.first())
                }
            } else {
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        val downloaded = downloadQuranPage(currentPage)
                        if (downloaded.isNotEmpty()) {
                            viewModel.insertQuranVerses(downloaded)
                            withContext(Dispatchers.Main) {
                                versesForPage = downloaded
                                isLoading = false
                                if (autoPlayNextPage) {
                                    autoPlayNextPage = false
                                    if (versesForPage.isNotEmpty()) playVerse(versesForPage.first())
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                networkError = "Connection timed out. Check internet connection."
                                isLoading = false
                                autoPlayNextPage = false
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            networkError = "Error downloading page: ${e.localizedMessage}"
                            isLoading = false
                            autoPlayNextPage = false
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(currentPage, surah.number) {
        onDispose {
            stopAudio()
        }
    }

    // Theme Colors
    val themeColors = remember(themeName) { getQuranThemeColors(themeName) }
    val bgColor = themeColors.bgColor
    val txtArabicColor = themeColors.txtArabicColor
    val txtUrduColor = themeColors.txtUrduColor
    val borderColor = themeColors.borderColor
    val decorationColor = themeColors.decorationColor

    if (showDisplaySettings) {
        QuranDisplaySettingsDialog(
            arabicFontSize = arabicFontSize,
            onArabicFontSizeChange = onArabicFontSizeChange,
            urduFontSize = urduFontSize,
            onUrduFontSizeChange = onUrduFontSizeChange,
            isUrduEnabled = isUrduEnabled,
            onUrduToggle = onUrduToggle,
            isEnglishEnabled = isEnglishEnabled,
            onEnglishToggle = onEnglishToggle,
            readerTheme = themeName,
            onThemeChange = onThemeChange,
            quranFontFamily = quranFontFamily,
            onFontFamilyChange = onFontFamilyChange,
            selectedQari = selectedQari,
            onOpenQariSelector = { showQariSelectorDialog = true },
            themeColors = themeColors,
            onDismiss = { showDisplaySettings = false }
        )
    }

    if (showQariSelectorDialog) {
        QariSelectionDialog(
            selectedQari = selectedQari,
            onSelectQari = onSelectQari,
            themeColors = themeColors,
            onDismiss = { showQariSelectorDialog = false }
        )
    }

    Scaffold(
        topBar = {
            if (!immersiveMode) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Surah ${currentSurahMetadata.englishName} (${currentSurahMetadata.arabicName})",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = decorationColor
                            )
                            Text(
                                text = if (initialPageNum == 0) "Full Recitation • ${versesForPage.size} Ayahs" else "Page $currentPage of 604",
                                fontSize = 10.sp,
                                color = txtUrduColor.copy(alpha = 0.7f)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = decorationColor)
                        }
                    },
                    actions = {
                        IconButton(onClick = { showQariSelectorDialog = true }) {
                            Icon(Icons.Default.RecordVoiceOver, contentDescription = "Select Reciter", tint = decorationColor)
                        }
                        IconButton(onClick = { showDisplaySettings = true }) {
                            Icon(Icons.Default.Settings, contentDescription = "Display Settings", tint = decorationColor)
                        }
                        IconButton(onClick = onToggleImmersive) {
                            Icon(Icons.Default.Fullscreen, contentDescription = "Full Screen", tint = decorationColor)
                        }
                        IconButton(onClick = {
                            if (isPlayingAudio) {
                                stopAudio()
                            } else {
                                val firstVerse = versesForPage.firstOrNull()
                                if (firstVerse != null) playVerse(firstVerse)
                                else Toast.makeText(context, "No verses loaded to play", Toast.LENGTH_SHORT).show()
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
        var scale by remember { mutableFloatStateOf(1f) }
        var offsetX by remember { mutableFloatStateOf(0f) }
        var offsetY by remember { mutableFloatStateOf(0f) }

        // Reset zoom when page changes
        LaunchedEffect(currentPage) {
            scale = 1f
            offsetX = 0f
            offsetY = 0f
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .padding(paddingValues)
                .pointerInput(currentPage, scale) {
                    detectTapGestures(
                        onDoubleTap = {
                            if (scale > 1.1f) {
                                scale = 1f
                                offsetX = 0f
                                offsetY = 0f
                            } else {
                                scale = 1.8f
                                offsetX = 0f
                                offsetY = 0f
                            }
                        }
                    )
                }
                .pointerInput(currentPage) {
                    awaitEachGesture {
                        var accumulatedPanX = 0f
                        var isZoomGesture = false
                        
                        val down = awaitFirstDown(requireUnconsumed = false)
                        do {
                            val event = awaitPointerEvent()
                            val pointerCount = event.changes.size
                            if (pointerCount >= 2) {
                                isZoomGesture = true
                                val zoomChange = event.calculateZoom()
                                val panChange = event.calculatePan()
                                
                                val newScale = (scale * zoomChange).coerceIn(1f, 3.5f)
                                scale = newScale
                                
                                if (scale > 1.01f) {
                                    val maxX = 600f * (scale - 1f)
                                    val maxY = 1000f * (scale - 1f)
                                    offsetX = (offsetX + panChange.x).coerceIn(-maxX, maxX)
                                    offsetY = (offsetY + panChange.y).coerceIn(-maxY, maxY)
                                } else {
                                    scale = 1f
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                                event.changes.forEach { it.consume() }
                            } else if (pointerCount == 1) {
                                val change = event.changes.first()
                                val dragAmount = change.position - change.previousPosition
                                if (scale > 1.05f) {
                                    val maxX = 600f * (scale - 1f)
                                    val maxY = 1000f * (scale - 1f)
                                    offsetX = (offsetX + dragAmount.x).coerceIn(-maxX, maxX)
                                    offsetY = (offsetY + dragAmount.y).coerceIn(-maxY, maxY)
                                    change.consume()
                                } else if (!isZoomGesture) {
                                    accumulatedPanX += dragAmount.x
                                    change.consume()
                                }
                            }
                        } while (event.changes.any { it.pressed })
                        
                        // Finger released
                        if (!isZoomGesture && scale <= 1.05f) {
                            if (accumulatedPanX < -80f) {
                                if (currentPage < 604) {
                                    stopAudio()
                                    currentPage++
                                } else {
                                    Toast.makeText(context, "Last page of Quran (604)", Toast.LENGTH_SHORT).show()
                                }
                            } else if (accumulatedPanX > 80f) {
                                if (currentPage > 1) {
                                    stopAudio()
                                    currentPage--
                                } else {
                                    Toast.makeText(context, "First page of Quran (1)", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
        ) {
            // Maximized full-length viewport with high-resolution vector scaling & elegant framing
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offsetX
                        translationY = offsetY
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    drawRoundRect(
                        color = borderColor.copy(alpha = 0.8f),
                        topLeft = Offset(2.dp.toPx(), 2.dp.toPx()),
                        size = Size(w - 4.dp.toPx(), h - 4.dp.toPx()),
                        cornerRadius = CornerRadius(8.dp.toPx()),
                        style = Stroke(width = 1.2.dp.toPx())
                    )
                }
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Page / Surah Header Banner
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentSurahMetadata.arabicName,
                            fontSize = 14.sp,
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
                    
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(borderColor.copy(alpha = 0.4f))
                    )
                    
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(color = decorationColor)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Loading Quran Recitation...",
                                    fontSize = 12.sp,
                                    color = decorationColor
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = decorationColor
                                )
                            }
                        }
                    } else if (networkError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
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
                                    onClick = { currentPage = currentPage },
                                    colors = ButtonDefaults.buttonColors(containerColor = decorationColor)
                                ) {
                                    Text("Retry Loading")
                                }
                            }
                        }
                    } else {
                        // FULL SCREEN LENGTH LAZY COLUMN FOR CONTINUOUS RECITATION
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(versesForPage) { verse ->
                                val isActive = activeVerseId == verse.id
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp, horizontal = 2.dp)
                                        .clickable { playVerse(verse) },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isActive) decorationColor.copy(alpha = 0.12f) else themeColors.cardColor
                                    ),
                                    border = BorderStroke(
                                        width = if (isActive) 1.8.dp else 1.dp,
                                        color = if (isActive) decorationColor else borderColor.copy(alpha = 0.3f)
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = if (isActive) 2.dp else 0.5.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        // Verse Metadata Header Row
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .background(decorationColor.copy(alpha = 0.1f), CircleShape)
                                                    .border(1.dp, decorationColor, CircleShape)
                                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = "${verse.surahNumber}:${verse.verseNumber}",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = decorationColor
                                                )
                                            }

                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                IconButton(
                                                    onClick = { playVerse(verse) },
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = if (isActive && isPlayingAudio) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                                                        contentDescription = "Play Verse",
                                                        tint = decorationColor,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))

                                        // Arabic Calligraphy Text
                                        Text(
                                            text = verse.textArabic,
                                            fontSize = arabicFontSize.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = txtArabicColor,
                                            textAlign = TextAlign.Center,
                                            lineHeight = (arabicFontSize + 14).sp,
                                            fontFamily = getFontFamily(quranFontFamily),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            style = LocalTextStyle.current.copy(
                                                textDirection = TextDirection.ContentOrRtl
                                            )
                                        )
                                        
                                        // Urdu Translation
                                        if (isUrduEnabled) {
                                            Text(
                                                text = verse.textUrdu,
                                                fontSize = urduFontSize.sp,
                                                color = txtUrduColor,
                                                textAlign = TextAlign.Center,
                                                lineHeight = (urduFontSize + 7).sp,
                                                fontFamily = getFontFamily(quranFontFamily),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp),
                                                style = LocalTextStyle.current.copy(
                                                    textDirection = TextDirection.ContentOrRtl
                                                )
                                            )
                                        }
                                        
                                        // English Translation
                                        if (isEnglishEnabled) {
                                            val englishText = if (verse.textEnglish.isNotBlank() &&
                                                                  !verse.textEnglish.equals("English translation.", ignoreCase = true) &&
                                                                  !verse.textEnglish.contains("cached offline", ignoreCase = true)
                                                              ) verse.textEnglish else "English translation (Coming Soon)"
                                            Text(
                                                text = englishText,
                                                fontSize = (urduFontSize - 2).sp,
                                                color = txtUrduColor.copy(alpha = 0.85f),
                                                textAlign = TextAlign.Center,
                                                lineHeight = (urduFontSize + 5).sp,
                                                fontFamily = getFontFamily(quranFontFamily),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = 2.dp, bottom = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    // Footer pagination controls & swipe hint
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                if (currentPage > 1) {
                                    stopAudio()
                                    currentPage--
                                }
                            },
                            enabled = currentPage > 1,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = decorationColor,
                                contentColor = Color.White,
                                disabledContainerColor = decorationColor.copy(alpha = 0.35f),
                                disabledContentColor = Color.White.copy(alpha = 0.6f)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.ArrowBackIos, contentDescription = "Prev", tint = Color.White, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Prev", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "صفحة ${formatArabicNumber(currentPage)} من ٦٠٤",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = decorationColor
                            )
                            Text(
                                text = "Swipe ← / → • Pinch to zoom",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Medium,
                                color = txtUrduColor.copy(alpha = 0.7f)
                            )
                        }
                        
                        Button(
                            onClick = {
                                if (currentPage < 604) {
                                    stopAudio()
                                    currentPage++
                                }
                            },
                            enabled = currentPage < 604,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = decorationColor,
                                contentColor = Color.White,
                                disabledContainerColor = decorationColor.copy(alpha = 0.35f),
                                disabledContentColor = Color.White.copy(alpha = 0.6f)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text("Next", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForwardIos, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }

            if (scale > 1.05f) {
                Card(
                    onClick = {
                        scale = 1f
                        offsetX = 0f
                        offsetY = 0f
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = decorationColor, contentColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 12.dp, end = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ZoomIn, contentDescription = "Zoom", modifier = Modifier.size(16.dp), tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${(scale * 100).toInt()}% • Reset Zoom",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Animated Offline Download Card
        item {
            AnimatedQuranDownloadCard(
                cachedCount = cachedCount,
                viewModel = viewModel,
                themeColors = themeColors,
                onUpdateCache = onUpdateCache
            )
        }

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
                    Text("Arabic Text Font Size", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = themeColors.txtUrduColor)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Slider(
                            value = arabicFontSize,
                            onValueChange = onArabicFontSizeChange,
                            valueRange = 20f..44f,
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
                            valueRange = 12f..26f,
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
                        Column {
                            Text("English Translation", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = themeColors.txtUrduColor)
                            Text("(Coming Soon)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = themeColors.decorationColor)
                        }
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
    }
}

// --- OFFLINE AUDIO STORAGE & DOWNLOAD HELPERS ---

fun getDownloadedAudioCount(context: Context): Int {
    return try {
        val dir = File(context.filesDir, "quran_audio")
        if (dir.exists()) {
            var count = 0
            dir.walkTopDown().forEach { file ->
                if (file.isFile && file.extension == "mp3" && file.length() > 500) {
                    count++
                }
            }
            count
        } else 0
    } catch (e: Exception) {
        0
    }
}

fun clearDownloadedAudioFiles(context: Context) {
    try {
        val dir = File(context.filesDir, "quran_audio")
        if (dir.exists()) {
            dir.deleteRecursively()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun downloadVerseAudio(context: Context, surahNum: Int, verseNum: Int, qariFolder: String = "Alafasy_128kbps"): Boolean {
    val surahFormatted = String.format("%03d", surahNum)
    val verseFormatted = String.format("%03d", verseNum)
    val dir = File(context.filesDir, "quran_audio/$qariFolder")
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val file = File(dir, "$surahFormatted$verseFormatted.mp3")
    if (file.exists() && file.length() > 500) {
        return true
    }

    val urlStr = "https://everyayah.com/data/$qariFolder/$surahFormatted$verseFormatted.mp3"
    try {
        val url = URL(urlStr)
        val conn = url.openConnection() as HttpURLConnection
        conn.connectTimeout = 3000
        conn.readTimeout = 3000
        if (conn.responseCode == 200) {
            val inputStream = conn.inputStream
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
            return true
        }
    } catch (e: Exception) {
        // Individual audio download failure handled gracefully
    }
    return false
}

@Composable
fun AnimatedQuranDownloadCard(
    cachedCount: Int,
    viewModel: StudentKitViewModel,
    themeColors: QuranThemeColors,
    onUpdateCache: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var activeQari by remember { mutableStateOf(QARI_LIST[0]) }
    var showQariPicker by remember { mutableStateOf(false) }
    var isDownloading by remember { mutableStateOf(false) }
    var downloadProgressSurah by remember { mutableIntStateOf(0) }
    val totalSurahs = 114
    val totalVersesTarget = 6236
    var audioFilesCount by remember { mutableIntStateOf(getDownloadedAudioCount(context)) }
    var currentVerseCount by remember { mutableIntStateOf(cachedCount) }
    var showAlreadyDownloadedDialog by remember { mutableStateOf(false) }
    var statusMessage by remember { 
        mutableStateOf(
            if (cachedCount >= 6236) 
                "✅ Full Quran (6,236 Verses) & Voice Package Active in Phone Memory!" 
            else if (cachedCount > 0) 
                "Partial Offline Data: $cachedCount / 6,236 verses, $audioFilesCount audio files saved."
            else 
                "Ready to download complete 114 Surahs (6,236 Verses) & voice (${activeQari.name}) into phone memory."
        ) 
    }
    var downloadJob by remember { mutableStateOf<Job?>(null) }

    val startDownloadProcess: () -> Unit = {
        isDownloading = true
        statusMessage = "Starting download of all 114 Surahs (6,236 Verses) & ${activeQari.name} Voice..."
        downloadJob = coroutineScope.launch(Dispatchers.IO) {
            try {
                val surahList = getSurahList()
                
                for (s in 1..totalSurahs) {
                    if (!isActive) break
                    val meta = surahList.find { it.number == s }
                    val surahName = meta?.englishName ?: "Surah $s"
                    
                    withContext(Dispatchers.Main) {
                        downloadProgressSurah = s
                        statusMessage = "Downloading Surah $s/114 ($surahName) & Voice (${activeQari.name})..."
                    }

                    // Download Surah verses with up to 3 retries
                    var surahVerses = downloadQuranSurah(s)
                    var retries = 0
                    while (surahVerses.isEmpty() && retries < 3 && isActive) {
                        retries++
                        delay(500)
                        surahVerses = downloadQuranSurah(s)
                    }

                    if (surahVerses.isNotEmpty()) {
                        viewModel.insertQuranVerses(surahVerses)
                        
                        // Download voice audio files for every verse
                        for (verse in surahVerses) {
                            if (!isActive) break
                            downloadVerseAudio(context, verse.surahNumber, verse.verseNumber, activeQari.folder)
                        }
                    }

                    val curCount = viewModel.getCachedQuranVersesCount()
                    val curAud = getDownloadedAudioCount(context)
                    withContext(Dispatchers.Main) {
                        currentVerseCount = curCount
                        audioFilesCount = curAud
                        statusMessage = "Surah $s/114 ($surahName) | Verses: $curCount / 6,236 | Voice Audio: $curAud files"
                        onUpdateCache()
                    }
                }

                // Final verification sweep to ensure ALL 6,236 verses are saved
                var finalVerses = viewModel.getCachedQuranVersesCount()
                if (finalVerses < totalVersesTarget && isActive) {
                    withContext(Dispatchers.Main) {
                        statusMessage = "Verifying all Surahs ($finalVerses / 6,236 saved)..."
                    }
                    for (s in 1..totalSurahs) {
                        if (!isActive) break
                        val meta = surahList.find { it.number == s }
                        val existingSurah = downloadQuranSurah(s)
                        if (existingSurah.isNotEmpty()) {
                            viewModel.insertQuranVerses(existingSurah)
                            for (v in existingSurah) {
                                downloadVerseAudio(context, v.surahNumber, v.verseNumber, activeQari.folder)
                            }
                        }
                    }
                    finalVerses = viewModel.getCachedQuranVersesCount()
                }

                val finalAudio = getDownloadedAudioCount(context)
                withContext(Dispatchers.Main) {
                    isDownloading = false
                    downloadProgressSurah = totalSurahs
                    currentVerseCount = finalVerses
                    audioFilesCount = finalAudio
                    statusMessage = "✅ Completed! All $finalVerses Verses & $finalAudio Voice Audio Files stored in Phone Memory for offline use."
                    Toast.makeText(context, "Full Quran ($finalVerses Verses) & Voice Saved in Phone Storage!", Toast.LENGTH_LONG).show()
                    onUpdateCache()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isDownloading = false
                    statusMessage = "Download paused: ${e.localizedMessage}"
                }
            }
        }
    }

    // Pulse animation for border and glowing effects
    val infiniteTransition = rememberInfiniteTransition(label = "quran_download_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    val glowingAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowing_alpha"
    )
    val rotateAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotate_angle"
    )

    val progressAnim by animateFloatAsState(
        targetValue = if (totalVersesTarget > 0) (currentVerseCount.toFloat() / totalVersesTarget.toFloat()).coerceIn(0f, 1f) else 0f,
        label = "progress_anim"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(if (isDownloading) pulseScale else 1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = themeColors.cardColor),
        border = BorderStroke(
            width = if (isDownloading) 2.5.dp else 1.5.dp,
            color = if (isDownloading) Color(0xFF10B981).copy(alpha = glowingAlpha) else themeColors.decorationColor.copy(alpha = 0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isDownloading) 8.dp else 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Row with Glowing Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF059669), Color(0xFF10B981), Color(0xFF34D399))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isDownloading) Icons.Default.Refresh else Icons.Default.CloudDownload,
                            contentDescription = "Offline Storage Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(26.dp)
                                .then(if (isDownloading) Modifier.rotate(rotateAngle) else Modifier)
                        )
                    }
                    Column {
                        Text(
                            text = "Full Offline Quran & Voice Pack",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.decorationColor
                        )
                        Text(
                            text = "Store all 114 Surahs (6,236 Verses) & Audio in Phone Memory",
                            fontSize = 11.sp,
                            color = themeColors.txtUrduColor.copy(alpha = 0.8f)
                        )
                    }
                }

                // Storage Tag Badge
                Surface(
                    color = if (currentVerseCount >= 6236) Color(0xFF10B981).copy(alpha = 0.15f) else themeColors.borderColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (currentVerseCount >= 6236) Color(0xFF10B981) else themeColors.borderColor)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (currentVerseCount >= 6236) Color(0xFF10B981) else Color(0xFFF59E0B))
                        )
                        Text(
                            text = if (currentVerseCount >= 6236) "Offline Ready" else "Download Needed",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (currentVerseCount >= 6236) Color(0xFF047857) else themeColors.txtUrduColor
                        )
                    }
                }
            }

            // Memory Status Counter Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(themeColors.bgColor)
                    .border(1.dp, themeColors.borderColor.copy(alpha = 0.4f))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Verses Saved", fontSize = 10.sp, color = themeColors.txtUrduColor.copy(alpha = 0.7f))
                    Text(
                        text = "$currentVerseCount / 6,236",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.decorationColor
                    )
                }
                Divider(
                    modifier = Modifier
                        .height(28.dp)
                        .width(1.dp),
                    color = themeColors.borderColor.copy(alpha = 0.5f)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Voice Recitations", fontSize = 10.sp, color = themeColors.txtUrduColor.copy(alpha = 0.7f))
                    Text(
                        text = "$audioFilesCount Files",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF059669)
                    )
                }
                Divider(
                    modifier = Modifier
                        .height(28.dp)
                        .width(1.dp),
                    color = themeColors.borderColor.copy(alpha = 0.5f)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Phone Storage", fontSize = 10.sp, color = themeColors.txtUrduColor.copy(alpha = 0.7f))
                    Text(
                        text = "Device Memory",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.txtUrduColor
                    )
                }
            }

            // Animated Progress Bar if downloading or progress > 0
            if (isDownloading || downloadProgressSurah > 0) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Download Progress (Surah $downloadProgressSurah / $totalSurahs | $currentVerseCount / 6,236 Verses)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = themeColors.txtUrduColor
                        )
                        Text(
                            text = "${(progressAnim * 100).toInt()}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981)
                        )
                    }

                    LinearProgressIndicator(
                        progress = progressAnim,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF10B981),
                        trackColor = themeColors.borderColor.copy(alpha = 0.3f)
                    )
                }
            }

            // Live Log / Status Message Box
            Text(
                text = statusMessage,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color = if (isDownloading) Color(0xFF047857) else themeColors.txtUrduColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDownloading) Color(0xFFD1FAE5) else themeColors.bgColor.copy(alpha = 0.6f))
                    .padding(8.dp)
            )

            // Select Qari Voice Reciter Button
            OutlinedButton(
                onClick = { showQariPicker = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, themeColors.decorationColor)
            ) {
                Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = themeColors.decorationColor, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Selected Qari: ${activeQari.name} (${activeQari.style})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.decorationColor
                )
            }

            if (showQariPicker) {
                QariSelectionDialog(
                    selectedQari = activeQari,
                    onSelectQari = { activeQari = it },
                    themeColors = themeColors,
                    onDismiss = { showQariPicker = false }
                )
            }

            // Primary Animated Download Button
            Button(
                onClick = {
                    if (isDownloading) {
                        downloadJob?.cancel()
                        downloadJob = null
                        isDownloading = false
                        statusMessage = "⏸️ Download paused by user."
                    } else {
                        val isComplete = currentVerseCount >= 6236
                        if (isComplete) {
                            statusMessage = "✅ Quran with voice is already downloaded in phone memory!"
                            Toast.makeText(
                                context,
                                "The Holy Quran with voice is already downloaded!",
                                Toast.LENGTH_LONG
                            ).show()
                            showAlreadyDownloadedDialog = true
                        } else {
                            startDownloadProcess()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDownloading) Color(0xFFEF4444) else Color(0xFF059669)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (isDownloading) Icons.Default.Pause else Icons.Default.DownloadForOffline,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(22.dp)
                            .then(if (!isDownloading) Modifier.scale(pulseScale) else Modifier)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (isDownloading) "Pause Download" else "Download Full Quran & Voice (Phone Memory)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.White
                    )
                }
            }

            // Secondary Action Buttons (Verify & Clear)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            val count = viewModel.getCachedQuranVersesCount()
                            val aud = getDownloadedAudioCount(context)
                            withContext(Dispatchers.Main) {
                                currentVerseCount = count
                                audioFilesCount = aud
                                statusMessage = "Verified Phone Storage: $count / 6,236 Verses & $aud Audio Files present."
                                Toast.makeText(context, "Phone Memory Verified: $count / 6,236 Verses, $aud Audio Files", Toast.LENGTH_SHORT).show()
                                onUpdateCache()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, themeColors.decorationColor)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp), tint = themeColors.decorationColor)
                    Spacer(Modifier.width(4.dp))
                    Text("Verify Storage", fontSize = 11.sp, color = themeColors.decorationColor)
                }

                OutlinedButton(
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            viewModel.clearCachedQuran()
                            clearDownloadedAudioFiles(context)
                            savePreloadedSurahs(viewModel)
                            val count = viewModel.getCachedQuranVersesCount()
                            val aud = getDownloadedAudioCount(context)
                            withContext(Dispatchers.Main) {
                                currentVerseCount = count
                                audioFilesCount = aud
                                downloadProgressSurah = 0
                                statusMessage = "Phone memory reset. Core Surahs remain available offline."
                                Toast.makeText(context, "Cleared Offline Phone Storage", Toast.LENGTH_SHORT).show()
                                onUpdateCache()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444)),
                    border = BorderStroke(1.dp, Color(0xFFEF4444))
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Clear Storage", fontSize = 11.sp)
                }
            }
        }
    }

    // Professional Dialog when Quran & Voice is already downloaded
    if (showAlreadyDownloadedDialog) {
        AlertDialog(
            onDismissRequest = { showAlreadyDownloadedDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Already Downloaded",
                    tint = Color(0xFF10B981),
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = "Quran & Voice Already Downloaded",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = themeColors.decorationColor
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "The complete Holy Quran with full Arabic text, translations, and high-quality voice recitations is already safely stored in your phone memory.",
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        color = themeColors.txtUrduColor
                    )

                    Surface(
                        color = Color(0xFF10B981).copy(alpha = 0.12f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.TaskAlt, contentDescription = null, tint = Color(0xFF047857), modifier = Modifier.size(16.dp))
                                Text("6,236 Verses Saved in Phone Memory", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF047857))
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = null, tint = Color(0xFF047857), modifier = Modifier.size(16.dp))
                                Text("Mishary Rashid Alafasy Audio Active", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF047857))
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.WifiOff, contentDescription = null, tint = Color(0xFF047857), modifier = Modifier.size(16.dp))
                                Text("100% Offline Access (No Internet Needed)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF047857))
                            }
                        }
                    }

                    Text(
                        text = "You can immediately read and listen to all Surahs offline. Re-downloading is not necessary.",
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        color = themeColors.txtUrduColor.copy(alpha = 0.7f)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showAlreadyDownloadedDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("OK, Got It!", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAlreadyDownloadedDialog = false
                        startDownloadProcess()
                    }
                ) {
                    Text("Re-Download", color = themeColors.txtUrduColor.copy(alpha = 0.7f), fontSize = 12.sp)
                }
            },
            containerColor = themeColors.cardColor
        )
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
