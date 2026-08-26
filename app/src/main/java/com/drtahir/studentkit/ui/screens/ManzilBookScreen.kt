package com.drtahir.studentkit.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drtahir.studentkit.R
import com.drtahir.studentkit.viewmodel.Screen
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import java.util.Calendar
import kotlinx.coroutines.launch

// Beautiful Islamic theme colors
val EmeraldDark = Color(0xFF0F5132)
val EmeraldLight = Color(0xFF198754)
val IslamicGold = Color(0xFFD4AF37)
val GoldenBeige = Color(0xFFF9F6F0)
val SoftGold = Color(0xFFF0E6D2)
val CharcoalDark = Color(0xFF212529)

data class IslamicBook(
    val id: String,
    val title: String,
    val arabicTitle: String,
    val subtitle: String,
    val description: String,
    val pages: Int,
    val isAvailable: Boolean,
    val accentColor: Color,
    val type: String
)

data class ManzilVerse(
    val id: Int,
    val surah: String,
    val verseRange: String,
    val arabicText: String,
    val transliteration: String,
    val translation: String,
    val benefit: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IslamicHubScreen(
    viewModel: StudentKitViewModel,
    modifier: Modifier = Modifier
) {
    var showManzilReader by remember { mutableStateOf(false) }
    var showNamazReader by remember { mutableStateOf(false) }
    var showJanazaReader by remember { mutableStateOf(false) }
    var showTaziyatReader by remember { mutableStateOf(false) }
    var showNawawiReader by remember { mutableStateOf(false) }
    var showHisnulReader by remember { mutableStateOf(false) }
    var showQuranMajeedReader by remember { mutableStateOf(false) }

    if (showQuranMajeedReader) {
        QuranMajeedScreen(viewModel = viewModel, onBack = { showQuranMajeedReader = false })
    } else if (showManzilReader) {
        ManzilReaderScreen(viewModel = viewModel, onBack = { showManzilReader = false })
    } else if (showNamazReader) {
        NamazReaderScreen(viewModel = viewModel, onBack = { showNamazReader = false })
    } else if (showJanazaReader) {
        JanazaReaderScreen(viewModel = viewModel, onBack = { showJanazaReader = false })
    } else if (showTaziyatReader) {
        TaziyatReaderScreen(viewModel = viewModel, onBack = { showTaziyatReader = false })
    } else if (showNawawiReader) {
        NawawiHadithReaderScreen(viewModel = viewModel, onBack = { showNawawiReader = false })
    } else if (showHisnulReader) {
        HisnulMuslimReaderScreen(viewModel = viewModel, onBack = { showHisnulReader = false })
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Islamic Library",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                            Text(
                                text = "Authentic Islamic books & protection supplications",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.navigateTo(Screen.Dashboard) }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back to Home", tint = EmeraldDark)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        ) { paddingValues ->
        val books = listOf(
            IslamicBook(
                id = "manzil",
                title = "Manzil",
                arabicTitle = "المنزل",
                subtitle = "Verses of Protection & Ruqyah",
                description = "A collection of 33 verses from the Holy Qur'an compiled for spiritual protection against negative influences, evil eye, and ailments. Complete Arabic, transliteration, and high-quality visuals.",
                pages = 33,
                isAvailable = true,
                accentColor = EmeraldLight,
                type = "Protection & Ruqyah"
            ),
            IslamicBook(
                id = "namaz",
                title = "Namaz Guide (نماز گائیڈ)",
                arabicTitle = "الصلوة",
                subtitle = "Complete Prayer Guide with Urdu Translation",
                description = "Learn and practice the daily Salah (Namaz) with word-by-word Arabic, transliteration, beautiful visuals, and complete Urdu translation. Swipe to navigate step by step.",
                pages = 17,
                isAvailable = true,
                accentColor = IslamicGold,
                type = "Daily Prayer"
            ),
            IslamicBook(
                id = "namaz_janaza",
                title = "Namaz-e-Janaza (نماز جنازہ)",
                arabicTitle = "صلاة الجنازة",
                subtitle = "Funeral Prayer Guide with Urdu Translation",
                description = "Learn the complete step-by-step procedure of Namaz-e-Janaza, including all four Takbeers, Sana, Durood-e-Ibrahim, and specific prayers for adults and children with comprehensive Urdu translation.",
                pages = 6,
                isAvailable = true,
                accentColor = Color(0xFF795548),
                type = "Funeral Prayer"
            ),
            IslamicBook(
                id = "dua_taziyat",
                title = "Dua-e-Taziyat (دعائے تعزیت)",
                arabicTitle = "دعاء التعزية",
                subtitle = "Condolence & Solace Supplications",
                description = "Prophetic and Sunnah supplications for offering condolences and visiting the bereaved, bringing emotional solace and spiritual patience (Sabr) with Urdu and English translations.",
                pages = 4,
                isAvailable = true,
                accentColor = Color(0xFF607D8B),
                type = "Condolences"
            ),
            IslamicBook(
                id = "nawawi",
                title = "40 Hadith Nawawi",
                arabicTitle = "الأربعون النووية",
                subtitle = "Arabic-English-Urdu combined",
                description = "Forty foundational sayings of the Prophet (PBUH) compiled by Imam an-Nawawi, containing essential guides for faith and practice in full trilingual formatting.",
                pages = 42,
                isAvailable = true,
                accentColor = Color(0xFFE65100),
                type = "Hadith Collection"
            ),
            IslamicBook(
                id = "quran",
                title = "Quran Majeed",
                arabicTitle = "القرآن الكريم",
                subtitle = "Complete Holy Scripture",
                description = "Read, search, and study the complete 114 Surahs of the Qur'an with authentic Urdu and English translations. High-fidelity calligraphic scripts.",
                pages = 604,
                isAvailable = true,
                accentColor = Color(0xFF1E88E5),
                type = "Scripture"
            ),
            IslamicBook(
                id = "bukhari",
                title = "Sahih al-Bukhari",
                arabicTitle = "صحيح البخاري",
                subtitle = "Collection of Authentic Hadiths",
                description = "The most authentic compilation of the sayings and actions of Prophet Muhammad (PBUH) organized by topic and chapter guides.",
                pages = 97,
                isAvailable = false,
                accentColor = Color(0xFF8E24AA),
                type = "Hadith Collection"
            ),
            IslamicBook(
                id = "hisnul_muslim",
                title = "Hisnul Muslim",
                arabicTitle = "حصن المسلم",
                subtitle = "Fortress of the Muslim",
                description = "Daily supplications (Duas) and remembrances (Azkar) for every occasion compiled from the Sunnah of Prophet Muhammad (PBUH).",
                pages = 132,
                isAvailable = true,
                accentColor = Color(0xFF00897B),
                type = "Supplications & Duas"
            )
        )

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // High Quality Islamic Header Image & Graphic
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Background image using the newly generated high-quality asset
                        Image(
                            painter = painterResource(id = R.drawable.img_islamic_header_1782555935997),
                            contentDescription = "Islamic Library Header Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Gradient Overlay for readability
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                                    )
                                )
                        )
                        // Text and content on top of image
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(IslamicGold, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "ISLAMIC STUDY HUB",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    letterSpacing = 1.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Islamic Library & Supplications",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Authentic Islamic books, translations, and guides for students & professionals.",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.85f),
                                maxLines = 2,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }

            // Dynamic Hijri Date & Moon Calibration Banner
            item {
                val hijriOffset by viewModel.hijriOffset.collectAsState()
                var showIslamicCalendarDialog by remember { mutableStateOf(false) }
                val todayHijri = remember(hijriOffset) { IslamicCalendarUtils.getHijriDate(Calendar.getInstance(), hijriOffset) }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F5132)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showIslamicCalendarDialog = true }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color(0xFFFFD54F))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = todayHijri.formattedAr,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = todayHijri.formattedEn,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = Color(0xFFFFD54F)
                                )
                            }
                        }

                        Button(
                            onClick = { showIslamicCalendarDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF198754)),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Tune, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Calendar / Calibrate", fontSize = 11.sp, color = Color.White)
                        }
                    }
                }

                if (showIslamicCalendarDialog) {
                    IslamicCalendarDialog(viewModel = viewModel, onDismiss = { showIslamicCalendarDialog = false })
                }
            }

            // Quick Supplication Stats / Tasbih Tracker Snippet
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
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
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(IslamicGold.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = IslamicGold
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Daily Spiritual Reflection",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Read Manzil daily for comprehensive protection.",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Book Selection Section
            item {
                Text(
                    text = "Featured Books & Modules",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            itemsIndexed(books) { _, book ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (book.isAvailable) {
                                if (book.id == "manzil") {
                                    showManzilReader = true
                                } else if (book.id == "namaz") {
                                    showNamazReader = true
                                } else if (book.id == "namaz_janaza") {
                                    showJanazaReader = true
                               } else if (book.id == "dua_taziyat") {
                                    showTaziyatReader = true
                                } else if (book.id == "nawawi") {
                                    showNawawiReader = true
                                } else if (book.id == "hisnul_muslim") {
                                    showHisnulReader = true
                                } else if (book.id == "quran") {
                                    showQuranMajeedReader = true
                                }
                            }
                        }
                        .testTag("islamic_book_${book.id}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Book Cover Style Box
                        Box(
                            modifier = Modifier
                                .size(width = 64.dp, height = 88.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = if (book.isAvailable) {
                                            listOf(EmeraldDark, EmeraldLight)
                                        } else {
                                            listOf(Color.Gray, Color.LightGray)
                                        }
                                    )
                                )
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = book.arabicTitle,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 16.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Icon(
                                    imageVector = if (book.isAvailable) Icons.Default.Book else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (book.isAvailable) IslamicGold else Color.White.copy(alpha = 0.7f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Book Metadata Details
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            if (book.isAvailable) EmeraldLight.copy(alpha = 0.15f) else Color.Gray.copy(alpha = 0.15f),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = book.type,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (book.isAvailable) EmeraldLight else Color.Gray
                                    )
                                }

                                if (!book.isAvailable) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = "Coming Soon",
                                            tint = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = "Coming Soon",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = book.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = book.subtitle,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = book.description,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                lineHeight = 13.sp
                            )
                        }
                    }
                }
            }
        }
        }
    }
}

// -----------------------------------------------------------------------------
// MANZIL FULL BOOK READER SCREEN (33 Verses complete)
// -----------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManzilReaderScreen(
    viewModel: StudentKitViewModel,
    onBack: () -> Unit
) {
    val verses = remember { getManzilVerses() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var activeVerseIndex by remember { mutableStateOf(0) }
    var completedVersesCount by remember { mutableStateOf(0) }
    var isRecitationCompleted by remember { mutableStateOf(false) }
    var currentDayStreak by remember { mutableStateOf(5) }

    // Quick Index Dialog
    var showIndexDialog by remember { mutableStateOf(false) }

    if (isRecitationCompleted) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(EmeraldDark, EmeraldLight)))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Calligraphy success icon
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(IslamicGold.copy(alpha = 0.2f), CircleShape)
                        .border(2.dp, IslamicGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = IslamicGold,
                        modifier = Modifier.size(64.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "تقبل الله منكم",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = IslamicGold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Recitation Completed Successfully",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "May Allah accept your supplications and grant you protection, peace, and blessings. Ameen.",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Button(
                    onClick = {
                        // Navigate directly back to Home (Dashboard)
                        viewModel.navigateTo(Screen.Dashboard)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Go Back to Home 🔙",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }
            }
        }
    } else {
        Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Manzil Al-Qur'an (المنزل)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Text(
                            text = "33 protection verses for morning & evening supplications",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back to Library")
                    }
                },
                actions = {
                    IconButton(onClick = { showIndexDialog = true }) {
                        Icon(Icons.Default.List, contentDescription = "Quick Index Finder")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        if (showIndexDialog) {
            AlertDialog(
                onDismissRequest = { showIndexDialog = false },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Manzil Chapter Index", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { showIndexDialog = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        verses.forEachIndexed { index, verse ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        showIndexDialog = false
                                        activeVerseIndex = index
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(index)
                                        }
                                    }
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "${index + 1}. ${verse.surah}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Verses: ${verse.verseRange}",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .background(EmeraldLight.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "Go To",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldLight
                                    )
                                }
                            }
                            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        }
                    }
                },
                confirmButton = {}
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Recitation & Streak Stats Row
            Card(
                colors = CardDefaults.cardColors(containerColor = GoldenBeige),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Recitation Streak",
                            tint = Color(0xFFFF5722),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$currentDayStreak Day Streak",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = CharcoalDark
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(EmeraldDark, RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Verse ${activeVerseIndex + 1} of 17",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = EmeraldLight,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "$completedVersesCount Completed",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    }
                }
            }

            // Central Reading Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(16.dp)
                        .pointerInput(Unit) {
                            var totalDragAmount = 0f
                            detectHorizontalDragGestures(
                                onDragStart = { totalDragAmount = 0f },
                                onDragEnd = {
                                    if (totalDragAmount < -150f) { // Swipe Left (Next)
                                        if (activeVerseIndex < verses.size - 1) {
                                            activeVerseIndex++
                                            completedVersesCount = (completedVersesCount + 1).coerceAtMost(verses.size)
                                            coroutineScope.launch {
                                                listState.animateScrollToItem(activeVerseIndex)
                                            }
                                        } else {
                                            completedVersesCount = verses.size
                                            isRecitationCompleted = true
                                        }
                                    } else if (totalDragAmount > 150f) { // Swipe Right (Prev)
                                        if (activeVerseIndex > 0) {
                                            activeVerseIndex--
                                            coroutineScope.launch {
                                                listState.animateScrollToItem(activeVerseIndex)
                                            }
                                        }
                                    }
                                },
                                onHorizontalDrag = { change, dragAmount ->
                                    change.consume()
                                    totalDragAmount += dragAmount
                                }
                            )
                        },
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    itemsIndexed(verses) { index, verse ->
                        val isActive = index == activeVerseIndex

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(
                                width = if (isActive) 2.dp else 1.dp,
                                color = if (isActive) IslamicGold else MaterialTheme.colorScheme.outlineVariant
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isActive) GoldenBeige else Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    activeVerseIndex = index
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                // Verse Header / Title & Metadata
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .clip(CircleShape)
                                                .background(EmeraldLight),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "${index + 1}",
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = verse.surah,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = CharcoalDark
                                            )
                                            Text(
                                                text = "Verse Range: ${verse.verseRange}",
                                                fontSize = 10.sp,
                                                color = Color.Gray
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .background(SoftGold, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "Ruqyah Protection",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldDark
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                // Arabic Calligraphy Container (Centered, Large text size)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(Color.Transparent, GoldenBeige.copy(alpha = 0.5f))
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = verse.arabicText,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CharcoalDark,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 44.sp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Transliteration
                                Text(
                                    text = "Transliteration:",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldLight,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = verse.transliteration,
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = CharcoalDark,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // English Translation
                                Text(
                                    text = "Translation:",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = IslamicGold,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = verse.translation,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = CharcoalDark,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )

                                // Benefit banner if expanded/active
                                if (isActive && verse.benefit.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = SoftGold.copy(alpha = 0.5f)),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Info,
                                                contentDescription = "Benefit of Recitation",
                                                tint = EmeraldDark,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = verse.benefit,
                                                fontSize = 11.sp,
                                                color = EmeraldDark,
                                                fontWeight = FontWeight.Medium,
                                                lineHeight = 14.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Beautiful, simplified, immersive navigation controls bar
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous Button
                    OutlinedButton(
                        onClick = {
                            if (activeVerseIndex > 0) {
                                activeVerseIndex--
                                coroutineScope.launch {
                                    listState.animateScrollToItem(activeVerseIndex)
                                }
                            }
                        },
                        enabled = activeVerseIndex > 0,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous", modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Previous", fontSize = 12.sp)
                    }

                    // Verse indicator
                    Text(
                        text = "Verse ${activeVerseIndex + 1} of ${verses.size}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )

                    // Next / Complete Button
                    Button(
                        onClick = {
                            if (activeVerseIndex < verses.size - 1) {
                                activeVerseIndex++
                                completedVersesCount = (completedVersesCount + 1).coerceAtMost(verses.size)
                                coroutineScope.launch {
                                    listState.animateScrollToItem(activeVerseIndex)
                                }
                            } else {
                                completedVersesCount = verses.size
                                isRecitationCompleted = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldLight),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (activeVerseIndex < verses.size - 1) "Next" else "Complete",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = if (activeVerseIndex < verses.size - 1) Icons.Default.ArrowForwardIos else Icons.Default.CheckCircle,
                            contentDescription = "Next/Complete",
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}
}

// -----------------------------------------------------------------------------
// COMPLETE AUTHENTIC MANZIL VERSES REFERENCE DATA
// -----------------------------------------------------------------------------

fun getManzilVerses(): List<ManzilVerse> {
    return listOf(
        ManzilVerse(
            id = 1,
            surah = "Surah Al-Fatihah",
            verseRange = "1 - 7",
            arabicText = "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِیْمِ\nاَلْحَمْدُ لِلّٰهِ رَبِّ الْعٰلَمِیْنَۙ\nالرَّحْمٰنِ الرَّحِیْمِۙ\nمٰلِكِ یَوْمِ الدِّیْنِؕ\nاِیَّاكَ نَعْبُدُ وَاِیَّاكَ نَسْتَعِیْنُؕ\nاِهْدِنَا الصِّرَاطَ الْمُسْتَقِیْمَۙ\nصِرَاطَ الَّذِیْنَ اَنْعَمْتَ عَلَیْهِمْ ۙ غَیْرِ الْمَغْضُوْبِ عَلَیْهِمْ وَلَا الضَّآلِّیْنَؒ",
            transliteration = "Bismillaahir Rahmaanir Raheem. Alhamdu lillaahi Rabbil 'aalameen. Ar-Rahmaanir-Raheem. Maaliki Yawmid-Deen. Iyyaaka na'budu wa iyyaaka nasta'een. Ihdinas-Siraatal-Mustaqeem. Siraatal-lazeena an'amta 'alaihim ghairil-maghdoobi 'alaihim wa lad-daalleen.",
            translation = "In the name of Allah, the Entirely Merciful, the Especially Merciful. [All] praise is [due] to Allah, Lord of the worlds. The Entirely Merciful, the Especially Merciful. Sovereign of the Day of Recompense. It is You we worship and You we ask for help. Guide us to the straight path. The path of those upon whom You have bestowed favor, not of those who have evoked [Your] anger or of those who are astray.",
            benefit = "Al-Fatihah is known as the Shifa (Cure) and is the ultimate protection opening."
        ),
        ManzilVerse(
            id = 2,
            surah = "Surah Al-Baqarah",
            verseRange = "1 - 5",
            arabicText = "الٓمٓۚ\nذٰلِكَ الْكِتٰبُ لَا رَیْبَ ۛۚ فِیْهِ ۛۚ هُدًى لِّلْمُتَّقِیْنَۙ\nالَّذِیْنَ یُؤْمِنُوْنَ بِالْغَیْبِ وَیُقِیْمُوْنَ الصَّلٰوةَ وَمِمَّا رَزَقْنٰهُمْ یُنْفِقُوْنَۙ\nوَالَّذِیْنَ یُؤْمِنُوْنَ بِمَآ اُنْزِلَ اِلَیْكَ وَمَآ اُنْزِلَ مِنْ قَبْلِكَ ۚ وَبِالْاٰخِرَةِ هُمْ یُوْقِنُوْنَؕ\nاُولٰٓىِٕكَ عَلٰى هُدًى مِّنْ رَّبِّهِمْ ۙ وَاُولٰٓىِٕكَ هُمُ الْمُفْلِحُوْنَؒ",
            transliteration = "Alif-Laam-Meem. Zaalikal Kitaabu laa raiba feeh; hudal lilmuttaqeen. Allazeena yu'minoona bilghaibi wa yuqeemoonas Salaata wa mimmaa razaqnaahum yunfiqoon. Wallazeena yu'minoona bimaaa unzila ilaika wa maaa unzila min qablika wa bil Aakhirati hum yooqinoon. Ulaaa'ika 'alaa hudam mir Rabbihim wa ulaaa'ika humul muflihoon.",
            translation = "Alif, Lam, Meem. This is the Book about which there is no doubt, a guidance for those conscious of Allah. Who believe in the unseen, establish prayer, and spend out of what We have provided for them. And who believe in what has been revealed to you, [O Muhammad], and what was revealed before you, and of the Hereafter they are certain [in faith]. Those are upon [right] guidance from their Lord, and it is those who are the successful.",
            benefit = "Recitation of the first 5 verses of Al-Baqarah safeguards the home from negative entities."
        ),
        ManzilVerse(
            id = 3,
            surah = "Surah Al-Baqarah",
            verseRange = "163",
            arabicText = "وَاِلٰهُكُمْ اِلٰهٌ وَّاحِدٌ ۚ لَآ اِلٰهَ اِلَّا هُوَ الرَّحْمٰنُ الرَّحِیْمُؒ",
            transliteration = "Wa ilaahukum ilaahun waahid; laaa ilaaha illaa Huwar-Rahmaanur-Raheem.",
            translation = "And your god is one God. There is no deity [worthy of worship] except Him, the Entirely Merciful, the Especially Merciful.",
            benefit = "Establishes absolute Monotheism (Tawheed), nullifying magical delusions."
        ),
        ManzilVerse(
            id = 4,
            surah = "Surah Al-Baqarah (Ayat-ul-Kursi)",
            verseRange = "255",
            arabicText = "اَللّٰهُ لَآ اِلٰهَ اِلَّا هُوَ ۚ اَلْحَیُّ الْقَیُّوْمُ ۚ لَا تَاْخُذُهٗ سِنَةٌ وَّلَا نَوْمٌ ؕ لَهٗ مَا فِی السَّمٰوٰتِ وَمَا فِی الْاَرْضِ ؕ مَنْ ذَا الَّذِیْ یَشْفَعُ عِنْدَهٗٓ اِلَّا بِاِذْنِهٖ ؕ یَعْلَمُ مَا بَیْنَ اَیْدِیْهِمْ وَمَا خَلْفَهُمْ ۚ وَلَا یُحِیْطُوْنَ بِشَیْءٍ مِّنْ عِلْمِهٖٓ اِلَّا بِمَا شَآءَ ۚ وَسِعَ كُرْسِیُّهُ السَّمٰوٰتِ وَالْاَرْضَ ۚ وَلَا یَـُٔوْدُهٗ حِفْظُهُمَا ۚ وَهُوَ الْعَلِیُّ الْعَظِیْمُؕ",
            transliteration = "Allahu laaa ilaaha illaa Huwal Hayyul Qayyeem; laa ta'khuzuhu sinatun wa laa nawm; lahu maa fis-samaawaati wa maa fil-ard; man zallazee yashfa'u 'indahuuu illaa bi-iznih; ya'lamu maa baina aydeehim wa maa khalfahum wa laa yuheetoona bishai'im-min 'ilmihiee illaa bimaa shaaa'; wasi'a Kursiyyuhus-samaawaati wal-arda wa laa ya'ooduhu hifzuhumaa; wa Huwal 'Aliyyul 'Azeem.",
            translation = "Allah - there is no deity except Him, the Ever-Living, the Sustainer of [all] existence. Neither drowsiness overtakes Him nor sleep. To Him belongs whatever is in the heavens and whatever is on the earth. Who is it that can intercede with Him except by His permission? He knows what is [presently] before them and what will be after them, and they encompass not a thing of His knowledge except for what He wills. His Kursi extends over the heavens and the earth, and their preservation tires Him not. And He is the Most High, the Most Great.",
            benefit = "The greatest verse in the Qur'an. Protects against all physical and metaphysical harms."
        ),
        ManzilVerse(
            id = 5,
            surah = "Surah Al-Baqarah",
            verseRange = "256 - 257",
            arabicText = "لَآ اِكْرَاهَ فِی الدِّیْنِ ۙ قَد تَّبَیَّنَ الرُّشْدُ مِنَ الْغَیِّ ۚ فَمَنْ یَّكْفُرْ بِالطَّاغُوْتِ وَیُؤْمِنْۢ بِاللّٰهِ فَقَدِ اسْتَمْسَكَ بِالْعُرْوَةِ الْوُثْقٰى ٭ لَا انْفِصَامَ لَهَا ؕ وَاللّٰهُ سَمِیْعٌ عَلِیْمٌ\nاَللّٰهُ وَلِیُّ الَّذِیْنَ اٰمَنُوْا ۙ یُخْرِجُهُمْ مِّنَ الظُّلُمٰتِ اِلَى النُّوْرِ ؕ وَالَّذِیْنَ كَفَرُوْٓا اَوْلِیٰٓـُٔهُمُ الطَّاغُوْتُ ۙ یُخْرِجُوْنَهُمْ مِّنَ النُّوْرِ اِلَى الظُّلُمٰتِ ؕ اُولٰٓىِٕكَ اَصْحٰبُ النَّارِ ۚ هُمْ فِیْهَا خٰلِدُوْنَؒ",
            transliteration = "Laaa ikraaha fid-deeni qat tabaiyanar-rushdu minal-ghayy; famany-yakfur bit-Taaghooti wa yu'mim-billaahi faqadistamsaka bil-'urwatil-wuthqaa lanfisaama lahaa; wallahu Samee'un 'Aleem. Allahu Waliyyul-lazeena aamanoo yukhrijuhum-minaz-zulumaati ilan-noor; wallazeena kafarooo awliyaaa'uhumut-Taaghootu yukhrijoonahum-minan-noori ilaz-zulumaat; ulaaa'ika Ashaabun-Naari hum feehaa khaalidoon.",
            translation = "There is no compulsion in religion. The right direction has become distinct from the wrong. So whoever disbelieves in Taghut and believes in Allah has grasped the most trustworthy handhold with no break in it. And Allah is Hearing and Knowing. Allah is the ally of those who believe. He brings them out from darknesses into the light. And those who disbelieve - their allies are Taghut. They take them out of the light into darknesses. Those are the companions of the Fire; they will abide eternally therein.",
            benefit = "Establishes spiritual light over darkness."
        ),
        ManzilVerse(
            id = 6,
            surah = "Surah Al-Baqarah",
            verseRange = "284 - 286",
            arabicText = "لِلّٰهِ مَا فِی السَّمٰوٰتِ وَمَا فِی الْاَرْضِ ؕ وَاِنْ تُبْدُوْا مَا فِیْٓ اَنْفُسِكُمْ اَوْ تُخْفُوْهُ یُحَاسِبْكُمْ بِهِ اللّٰهُ ؕ فَیَغْفِرُ لِمَنْ یَّشَآءُ وَیُعَذِّبُ مَنْ یَّشَآءُ ؕ وَاللّٰهُ عَلٰى كُلِّ شَیْءٍ قَدِیْرٌ\nاٰمَنَ الرَّسُوْلُ بِمَآ اُنْزِلَ اِلَیْهِ مِنْ رَّبِّهٖ وَالْمُؤْمِنُوْنَ ؕ كُلٌّ اٰمَنَ بِاللّٰهِ وَمَلٰٓىِٕكَتِهٖ وَكُتُبِهٖ وَرُسُلِهٖ ۫ لَا نُفَرِّقُ بَیْنَ اَحَدٍ مِّنْ رُّسُلِهٖ ۫ وَقَالُوْا سَمِعْنَا وَاَطَعْنَا ۫ غُفْرَانَكَ رَبَّنَا وَاِلَیْكَ الْمَصِیْرُ\nلَا یُكَلِّفُ اللّٰهُ نَفْسًا اِلَّا وُسْعَهَا ؕ لَهَا مَا كَسَبَتْ وَعَلَیْهَا مَا اكْتَسَبَتْ ؕ رَبَّنَا لَا تُؤَاخِذْنَآ اِنْ نَّسِیْنَآ اَوْ اَخْطَاْنَا ۚ رَبَّنَا وَلَا تَحْمِلْ عَلَیْنَآ اِصْرًا كَمَا حَمَلْتَهٗ عَلَى الَّذِیْنَ مِنْ قَبْلِنَا ۚ رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهٖ ۚ وَاعْفُ عَنَّا ۪ وَاغْفِرْ لَنَا ۪ وَارْحَمْنَا ۪ اَنْتَ مَوْلٰىنَا فَانْصُرْنَا عَلَى الْقَوْمِ الْكٰفِرِیْنَؒ",
            transliteration = "Lillaahi maa fis-samaawaati wa maa fil-ard; wa in tubdoo maa feee anfusikum aw tukhfoohu yuhaasibkum bihillaah; fayaghfiru limany-yashaaa'u wa yu'azzibu many-yashaaa'; wallahu 'alaa kulli shai'in Qadeer. Aamanar-Rasoolu bimaaa unzila ilaihi mir-Rabbihee wal-mo'minoon; kullun aamana billaahi wa Malaaa'ikatihee wa Kutubihee wa Rusulih; laa nufarriqu baina ahadim-mir-rusulih; wa qaaloo sami'naa wa ata'naa ghufraanaka Rabbanaa wa ilaikal-maseer. Laa yukalliful-lahu nafsan illaa wus'ahaa; lahaa maa kasabat wa 'alaihaa maktasabat; Rabbanaa laa tu'aakhiznaaa in-naseenaaa aw akhta'naa; Rabbanaa wa laa tahmil 'alainaaa isran kamaa hamaltahoo 'alal-lazeena min qablinaa; Rabbanaa wa laa tuhammilnaa maa laa taaqata lanaa bih; wa'fu 'annaa waghfir lanaa warhamnaa; Anta mawlaanaa fansurnaa 'alal-qawmil-kaafireen.",
            translation = "To Allah belongs whatever is in the heavens and whatever is in the earth. Whether you show what is within yourselves or conceal it, Allah will bring you to account for it. Then He will forgive whom He wills and punish whom He wills, and Allah is over all things competent. The Messenger has believed in what was revealed to him from his Lord, and [so have] the believers. All of them have believed in Allah and His angels and His books and His messengers, [saying], 'We make no distinction between any of His messengers.' And they say, 'We hear and we obey. [We seek] Your forgiveness, our Lord, and to You is the [final] destination.' Allah does not charge a soul except [with that within] its capacity. It will have [the consequence of] what [good] it has gained, and it will bear [the consequence of] what [evil] it has earned. 'Our Lord, do not impose blame upon us if we have forgotten or erred. Our Lord, and lay not upon us a burden like that which You laid upon those before us. Our Lord, and burden us not with that which we have no ability to bear. And pardon us; and forgive us; and have mercy upon us. You are our protector, so give us victory over the disbelieving people.'",
            benefit = "Whoever recites these three verses at night, they suffice him against all harms."
        ),
        ManzilVerse(
            id = 7,
            surah = "Surah Al-A'raf",
            verseRange = "54 - 56",
            arabicText = "اِنَّ رَبَّكُمُ اللّٰهُ الَّذِیْ خَلَقَ السَّمٰوٰتِ وَالْاَرْضَ فِیْ سِتَّةِ اَیَّامٍ ثُمَّ اسْتَوٰى عَلَى الْعَرْشِ ۫ یُغْشِی الَّیْلَ النَّهَارَ یَطْلُبُهٗ حَثِیْثًا ۙ وَّالشَّمْسَ وَالْقَمَرَ وَالنُّجُوْمَ مُسَخَّرٰتٍۭ بِاَمْرِهٖ ؕ اَلَا لَهُ الْخَلْقُ وَالْاَمْرُ ؕ تَبٰرَكَ اللّٰهُ رَبُّ الْعٰلَمِیْنَ\nاُدْعُوْا رَبَّكُمْ تَضَرُّعًا وَّخُفْیَةً ؕ اِنَّهٗ لَا یُحِبُّ الْمُعْتَدِیْنَ\nوَلَا تُفْسِدُوْا فِی الْاَرْضِ بَعْدَ اِصْلَاحِهَا وَادْعُوْهُ خَوْفًا وَّطَمَعًا ؕ اِنَّ رَحْمَتَ اللّٰهِ قَرِیْبٌ مِّنَ الْمُحْسِنِیْنَ",
            transliteration = "Inna Rabbakumullaahullazee khalaqas-samaawaati wal-arda fee sittati aiyaamin thummastawaa 'alal-'Arshi yughshil-lailan-nahaara yatlubuhoo hatheethan wash-shamsa wal-qamara wan-nujooma musakh-kharaatim-bi-amrih; alaa lahul-khalqu wal-amr; tabaarakallahu Rabbul-'aalameen. Ud'oo Rabbakum tadarru'anw-wa khufyah; innahoo laa yuhibbul-mu'tadeen. Wa laa tufsidoo fil-ardi ba'da islaahihaa wad'oohoo khawfanw-wa tama'aa; inna rahmatallaahi qareebum-minal-muhsineen.",
            translation = "Indeed, your Lord is Allah, who created the heavens and the earth in six days and then established Himself above the Throne. He covers the night with the day, [another night] chasing it rapidly; and [He created] the sun, the moon, and the stars, subjected by His command. Unquestionably, His is the creation and the command; blessed is Allah, Lord of the worlds. Call upon your Lord in humility and privately; indeed, He does not like transgressors. And cause not corruption upon the earth after its reformation. And call upon Him in fear and aspiration. Indeed, the mercy of Allah is near to the doers of good.",
            benefit = "Removes anxiety and brings peace to the household."
        ),
        ManzilVerse(
            id = 8,
            surah = "Surah Al-Isra",
            verseRange = "110 - 111",
            arabicText = "قُلِ ادْعُوا اللّٰهَ اَوِ ادْعُوا الرَّحْمٰنَ ؕ اَیًّا مَّا تَدْعُوْا فَلَهُ الْاَسْمَآءُ الْحُسْنٰى ۚ وَلَا تَجْهَرْ بِصَلَاتِكَ وَلَا تُخَافِتْ بِهَا وَابْتَغِ بَیْنَ ذٰلِكَ سَبِیْلًا\nوَقُلِ الْحَمْدُ لِلّٰهِ الَّذِیْ لَمْ یَتَّخِذْ وَلَدًا وَّلَمْ یَكُنْ لَّهٗ شَرِیْكٌ فِی الْمُلْكِ وَلَمْ یَكُنْ لَّهٗ وَلِیٌّ مِّنَ الذُّلِّ وَكَبِّرْهُ تَكْبِیْرًاؒ",
            transliteration = "Qulid'ullaaha awid'ur-Rahmaana aiyam-maa tad'oo falahul-asmaaa'ul husnaa; wa laa tajhar bi-Salaatika wa laa tukhaafit bihaa wabtaghi baina zaalika sabeela. Wa qulil-hamdu lillaahillazee lam yattakhiz waladanw-wa lam yakul-lahoo shareekun fil-mulki wa lam yakul-lahoo waliyyum-minaz-zulli wa kabbirhu takbeera.",
            translation = "Say, 'Call upon Allah or call upon the Most Merciful. Whichever [name] you call - to Him belong the best names.' And do not recite your prayer [too] loudly or [too] quietly, but seek between that a [middle] way. And say, 'Praise to Allah, who has not taken a son and has had no partner in [His] kingdom and has no need of a protector to protect Him from humiliation. And glorify Him with [great] glorification.'",
            benefit = "Enhances connection with Divine Names."
        ),
        ManzilVerse(
            id = 9,
            surah = "Surah Al-Mu'minun",
            verseRange = "115 - 118",
            arabicText = "اَفَحَسِبْتُمْ اَنَّمَا خَلَقْنٰكُمْ عَبَثًا وَّاَنَّكُمْ اِلَیْنَا لَا تُرْجَعُوْنَ\nفَتَعٰلَى اللّٰهُ الْمَلِكُ الْحَقُّ ۚ لَآ اِلٰهَ اِلَّا هُوَ ۚ رَبُّ الْعَرْشِ الْكَرِیْمِ\nوَمَنْ یَّدْعُ مَعَ اللّٰهِ اِلٰهًا اٰخَرَ ۙ لَا بُرْهَانَ لَهٗ بِهٖ ۙ فَاِنَّمَا حِسَابُهٗ عِنْدَ رَبِّهٖ ؕ اِنَّهٗ لَا یُفْلِحُ الْكٰفِرُوْنَ\nوَقُلْ رَّبِّ اغْفِرْ وَارْحَمْ وَاَنْتَ خَیْرُ الرّٰحِمِیْنَؒ",
            transliteration = "Afahasibtum annamaa khalaqnaakum 'abasahn-wa annakum ilainaa laa turja'oon. Fata'aalallahul-Malikul-Haqqu laaa ilaaha illaa Huwa Rabbul-'Arshil-Kareem. Wa many-yad'u ma'allaahi ilaahan aakhara laa burhaana lahoo bihee fainnamaa hisaabuhoo 'inda Rabbih; innahoo laa yuflihul-kaafiroon. Wa qur-Rabbighfir warham wa Anta khairur-raahimeen.",
            translation = "Then did you think that We created you uselessly and that to Us you would not be returned? So exalted is Allah, the Sovereign, the Truth; there is no deity except Him, Lord of the Noble Throne. And whoever invokes besides Allah another deity for which he has no proof - then his account is only with his Lord. Indeed, the disbelievers will not succeed. And, [O Muhammad], say, 'My Lord, forgive and have mercy, and You are the best of the merciful.'",
            benefit = "Protects from despair, depression, and mental fatigue."
        ),
        ManzilVerse(
            id = 10,
            surah = "Surah Al-Saffat",
            verseRange = "1 - 11",
            arabicText = "وَالصّٰٓفّٰتِ صَفًّاۙ\nفَالزّٰجِرٰتِ زَجْرًاۙ\nفَالتّٰلِیٰتِ ذِكْرًاۙ\nاِنَّ اِلٰهَكُمْ لَوَاحِدٌؕ\nرَبُّ السَّمٰوٰتِ وَالْاَرْضِ وَمَا بَیْنَهُمَا وَرَبُّ الْمَشَارِقِؕ\nاِنَّا زَیَّنَّا السَّمَآءَ الدُّنْیَا بِزِیْنَةِ ۣالْكَوَاكِبِۙ\nوَحِفْظًا مِّنْ كُلِّ شَیْطٰنٍ مَّارِدٍۚ\nلَا یَسَّمَّعُوْنَ اِلَى الْمَلَاِ الْاَعْلٰى وَیُقْذَفُوْنَ مِنْ كُلِّ جَانِبٍ\nدُحُوْرًا وَّلَهُمْ عَذَابٌ وَّاصِبٌۙ\nاِلَّا مَنْ خَطِفَ الْخَطْفَةَ فَاَتْبَعَهٗ شِهَابٌ ثَاقِبٌ\nفَاسْتَفْتِهِمْ اَهُمْ اَشَدُّ خَلْقًا اَمْ مَّنْ خَلَقْنَا ؕ اِنَّا خَلَقْنٰهُمْ مِّنْ طِیْنٍ لَّازِبٍ",
            transliteration = "Was-saaffaati saffaa. Faz-zaajiraati zajraa. Fat-taaliyaati zikraa. Inna Ilaahakum la-Waahid. Rabbus-samaawaati wal-ardi wa maa bainahumaa wa Rabbul-mashaariq. Innaa zaiyannas-samaaa'ad-dunyaa bizeenatinil-kawaakib. Wa hifzam-min kulli Shaitaanim-maarid. Laa yassamma'oona ilal-Mala'il-A'laa wa yuqzafoona min kulli jaanib. Duhooranw-wa lahum 'azaabunw-waasib. Illaa man khatifal-khatfata fa-atba'ahoo shihaabun thaaqib. Fastaftihim ahum ashaddu khalqan am-man khalaqnaa; innaa khalaqnaahum-min teenil-laazib.",
            translation = "By those [angels] lined up in rows. And those who drive [the clouds] and nudge them. And those who recite the message. Indeed, your God is One. Lord of the heavens and the earth and whatever is between them and Lord of the sunrises. Indeed, We have adorned the nearest heaven with an adornment of stars. And as protection against every rebellious devil. [So] they may not listen to the exalted assembly [of angels] and are pelted from every side. Repelled; and for them is a constant punishment. Except one who snatches [a hearing] by theft, and is pursued by a burning flame of piercing brightness. Then inquire of them, [O Muhammad], are they a stronger [or more difficult] creation or those We have created? Indeed, We created them from sticky clay.",
            benefit = "Powerful protection against spiritual blocks and severe evil."
        ),
        ManzilVerse(
            id = 11,
            surah = "Surah Al-Rahman",
            verseRange = "33 - 36",
            arabicText = "یٰمَعْشَرَ الْجِنِّ وَالْاِنْسِ اِنِ اسْتَطَعْتُمْ اَنْ تَنْفُذُوْا مِنْ اَقْطَارِ السَّمٰوٰتِ وَالْاَرْضِ فَانْفُذُوْا ؕ لَا تَنْفُذُوْنَ اِلَّا بِسُلْطٰنٍۚ\nفَبِاَیِّ اٰلَآءِ رَبِّكُمَا تُكَذِّبٰنِ\nیُرْسَلُ عَلَیْكُمَا شُوَاظٌ مِّنْ نَّارٍ ۬ وَّنُحَاسٌ فَلَا تَنْتَصِرٰنِۚ\nفَبِاَیِّ اٰلَآءِ رَبِّكُمَا تُكَذِّبٰنِ",
            transliteration = "Yaa ma'sharal-Jinni wal-Insi inistata'tum an tanfuzoo min aqtaaris-samaawaati wal-ardi fanfuzoo; laa tanfuzoona illaa bi-sultaan. Fabiaiyi aalaaa'i Rabbikumaa tukazzibaan. Yursalu 'alaikumaa shuwaazum-min naarinw-wa nuhaasun falaa tantasiraan. Fabiaiyi aalaaa'i Rabbikumaa tukazzibaan.",
            translation = "O company of jinn and mankind, if you are able to pass beyond the regions of the heavens and the earth, then pass. You will not pass except by authority [from Allah]. So which of the favors of your Lord would you deny? There will be sent upon you a flame of fire and smoke, and you will not defend yourselves. So which of the favors of your Lord would you deny?",
            benefit = "Guarantees that nothing can override divine boundary protection."
        ),
        ManzilVerse(
            id = 12,
            surah = "Surah Al-Hashr",
            verseRange = "21 - 24",
            arabicText = "لَوْ اَنْزَلْنَا هٰذَا الْقُرْاٰنَ عَلٰى جَبَلٍ لَّرَاَیْتَهٗ خَاشِعًا مُّتَصَدِّعًا مِّنْ خَشْیَةِ اللّٰهِ ؕ وَتِلْكَ الْاَمْثَالُ نَضْرِبُهَا لِلنَّاسِ لَعَلَّهُمْ یَتَفَكَّرُوْنَ\nهُوَ اللّٰهُ الَّذِیْ لَآ اِلٰهَ اِلَّا هُوَ ۚ عَالِمُ الْغَیْبِ وَالشَّهَادَةِ ۚ هُوَ الرَّحْمٰنُ الرَّحِیْمُ\nهُوَ اللّٰهُ الَّذِیْ لَآ اِلٰهَ اِلَّا هُوَ ۚ اَلْمَلِكُ الْقُدُّوْسُ السَّلٰمُ الْمُؤْمِنُ الْمُهَیْمِنُ الْعَزِیْزُ الْجَبَّارُ الْمُتَكَبِّرُ ؕ سُبْحٰنَ اللّٰهِ عَمَّا یُشْرِكُوْنَ\nهُوَ اللّٰهُ الْخَالِقُ الْبَارِئُ الْمُصَوِّرُ لَهُ الْاَسْمَآءُ الْحُسْنٰى ؕ یُسَبِّحُ لَهٗ مَا فِی السَّمٰوٰتِ وَالْاَرْضِ ۚ وَهُوَ الْعَزِیْزُ الْحَكِیْمُؒ",
            transliteration = "Law anzalnaa haazal-Quraana 'alaa jabalil-lara'aitahoo khaashi'am-mutasaddi'am-min khashyatillaah; wa tilkal-amsaalu nadribuhaa linnaasi la'allahum yatafakkaroon. Huwallaahullazee laaa ilaaha illaa Huwa 'Aalimul-Ghaibi wash-Shahaadah; Huwar-Rahmaanur-Raheem. Huwallaahullazee laaa ilaaha illaa Huwal-Malikul-Quddoosus-Salaamul-Mo'minul-Muhayminul-'Azeezul-Jabbaarul-Mutakabbir; Subhaanallaahi 'ammaa yushrikoon. Huwallaahul-Khaaliqul-Baari'ul-Musawwiru lahul-Asmaaa'ul Husnaa; yusabbihu lahoo maa fis-samaawaati wal-ardi wa Huwal-'Azeezul-Hakeem.",
            translation = "If We had sent down this Qur'an upon a mountain, you would have seen it humbled and coming apart from fear of Allah. And these examples We present to the people that perhaps they will give thought. He is Allah, other than whom there is no deity, Knower of the unseen and the witnessed. He is the Entirely Merciful, the Especially Merciful. He is Allah, other than whom there is no deity, the Sovereign, the Pure, the Perfection, the Bestower of Faith, the Overseer, the Exalted in Might, the Compeller, the Superior. Exalted is Allah above whatever they associate with Him. He is Allah, the Creator, the Inventor, the Fashioner; to Him belong the best names. Whatever is in the heavens and earth is exalting Him. And He is the Exalted in Might, the Wise.",
            benefit = "Brings absolute humility, healing, and direct spiritual alignment with Allah's Supreme Names."
        ),
        ManzilVerse(
            id = 13,
            surah = "Surah Al-Jinn",
            verseRange = "1 - 4",
            arabicText = "قُلْ اُوْحِیَ اِلَیَّ اَنَّهُ اسْتَمَعَ نَفَرٌ مِّنَ الْجِنِّ فَقَالُوْٓا اِنَّا سَمِعْنَا قُرْاٰنًا عَجَبًاۙ\nیَّهْدِیْٓ اِلَى الرُّشْدِ فَاٰمَنَّا بِهٖ ؕ وَلَنْ نُّشْرِكَ بِرَبِّنَآ اَحَدًاۙ\nوَّاَنَّهٗ تَعٰلٰى جَدُّ رَبِّنَا مَا اتَّخَذَ صَاحِبَةً وَّلَا وَلَدًاۙ\nوَّاَنَّهٗ كَانَ یَقُوْلُ سَفِیْهُنَا عَلَى اللّٰهِ شَطَطًاۙ",
            transliteration = "Qul oohiya ilaiya annahustama'a nafarum-minal-Jinni faqaaloo innaa sami'naa Quraanan 'ajabaa. Yahdeee ilar-rushdi fa-aamannaa bih; walan-nushrika bi-Rabbinaaa ahadaa. Wa annahoo Ta'aalaa jaddu Rabbinaa mattakhaza saahibatanw-wa laa waladaa. Wa annahoo kaana yaqoolu safeehunaa 'alallaahi shatataa.",
            translation = "Say, [O Muhammad], 'It has been revealed to me that a group of the jinn listened and said, \"Indeed, we have heard an amazing Qur'an. It guides to the right course, and we have believed in it. And we will never associate with our Lord anyone. And [it teaches] that exalted is the nobleness of our Lord; He has not taken a companion or a son. And that our foolish one has been saying about Allah an excess.\" '",
            benefit = "Guards against invisible threats."
        ),
        ManzilVerse(
            id = 14,
            surah = "Surah Al-Kafirun",
            verseRange = "1 - 6",
            arabicText = "قُلْ یٰٓاَیُّهَا الْكٰفِرُوْنَۙ\nلَآ اَعْبُدُ مَا تَعْبُدُوْنَۙ\nوَلَآ اَنْتُمْ عٰبِدُوْنَ مَآ اَعْبُدُۚ\nوَلَآ اَنَا عَابِدٌ مَّا عَبَدْتُّمْۙ\nوَلَآ اَنْتُمْ عٰبِدُوْنَ مَآ اَعْبُدُؕ\nلَكُمْ دِیْنُكُمْ وَلِیَ دِیْنِؒ",
            transliteration = "Qul yaaa-aiyuhal kaafiroon. Laaa a'budu maa ta'budoon. Wa laaa antum 'aabidoona maaa a'bud. Wa laaa ana 'aabidum-maa 'abattum. Wa laaa antum 'aabidoona maaa a'bud. Lakum deenukum wa liya deen.",
            translation = "Say, 'O disbelievers, I do not worship what you worship. Nor are you worshippers of what I worship. Nor will I be a worshipper of what you worship. Nor will you be worshippers of what I worship. For you is your religion, and for me is my religion.'",
            benefit = "Equivalent to reciting one-quarter of the Holy Qur'an, cleansing the heart."
        ),
        ManzilVerse(
            id = 15,
            surah = "Surah Al-Ikhlas",
            verseRange = "1 - 4",
            arabicText = "قُلْ هُوَ اللّٰهُ اَحَدٌۚ\nاَللّٰهُ الصَّمَدُۚ\nلَمْ یَلِدْ ۙ وَلَمْ یُوْلَدْۙ\nوَلَمْ یَكُنْ لَّهٗ كُفُوًا اَحَدٌؒ",
            transliteration = "Qul Huwal-laahu Ahad. Allaahush-Samad. Lam yalid wa lam yoolad. Wa lam yakul-lahoo kufuwan ahad.",
            translation = "Say, 'He is Allah, [who is] One. Allah, the Eternal Refuge. He neither begets nor is born. Nor is there to Him any equivalent.'",
            benefit = "Pure declaration of Tawheed, equivalent to one-third of the Qur'an."
        ),
        ManzilVerse(
            id = 16,
            surah = "Surah Al-Falaq",
            verseRange = "1 - 5",
            arabicText = "قُلْ اَعُوْذُ بِرَبِّ الْفَلَقِۙ\nمِنْ شَرِّ مَا خَلَقَۙ\nوَمِنْ شَرِّ غَاسِقٍ اِذَا وَقَبَۙ\nوَمِنْ شَرِّ النَّفّٰثٰتِ فِی الْعُقَدِۙ\nوَمِنْ شَرِّ حَاسِدٍ اِذَا حَسَدَؒ",
            transliteration = "Qul a'oozu bi-Rabbil-Falaq. Min sharri maa khalaq. Wa min sharri ghaasiqin izaa waqab. Wa min sharri-naffaasaati fil 'uqad. Wa min sharri haasidin izaa hasad.",
            translation = "Say, 'I seek refuge in the Lord of daybreak. From the evil of that which He created. And from the evil of darkness when it settles. And from the evil of the blowers in knots. And from the evil of an envier when he envies.'",
            benefit = "Directly shields against evil eye, envy, and witchcraft."
        ),
        ManzilVerse(
            id = 17,
            surah = "Surah Al-Nas",
            verseRange = "1 - 6",
            arabicText = "قُلْ اَعُوْذُ بِرَبِّ النَّاسِۙ\nمَلِكِ النَّاسِۙ\nاِلٰهِ النَّاسِۙ\nمِنْ شَرِّ الْوَسْوَاسِ ەۙ الْخَنَّاسِۙ\nالَّذِیْ یُوَسْوِسُ فِیْ صُدُوْرِ النَّاسِۙ\nمِنَ الْجِنَّةِ وَالنَّاسِؒ",
            transliteration = "Qul a'oozu bi-Rabbin-Naas. Malikin-Naas. Ilaahin-Naas. Min sharril waswaasil khannaas. Allazee yuwaswisu fee sudoorin-Naas. Minal jinnati wan-Naas.",
            translation = "Say, 'I seek refuge in the Lord of mankind. The Sovereign of mankind. The God of mankind. From the evil of the retreating whisperer - Who whispers [evil] into the breasts of mankind - From among the jinn and mankind.'",
            benefit = "Shields against negative internal whispering and external evil entities."
        )
    )
}

// -----------------------------------------------------------------------------
// NAMAZ INTERACTIVE GUIDE READER SCREEN
// -----------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NamazReaderScreen(
    viewModel: StudentKitViewModel,
    onBack: () -> Unit
) {
    val steps = remember { getNamazSteps() }
    val coroutineScope = rememberCoroutineScope()

    var activeStepIndex by remember { mutableStateOf(0) }
    var isNamazCompleted by remember { mutableStateOf(false) }

    if (isNamazCompleted) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(EmeraldDark, EmeraldLight)))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Success Icon
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(IslamicGold.copy(alpha = 0.2f), CircleShape)
                        .border(2.dp, IslamicGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = IslamicGold,
                        modifier = Modifier.size(64.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "تقبل الله منكم",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = IslamicGold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Namaz Guide Completed",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "May Allah accept your prayers, reward you for your devotion, and keep you steadfast on the straight path. Ameen.",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Dashboard)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Go Back to Home 🔙",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Namaz Guide (نماز گائیڈ)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                            Text(
                                text = "Step-by-step Daily Prayer with Urdu Translation",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back to Library")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        ) { paddingValues ->
            val step = steps[activeStepIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(GoldenBeige)
            ) {
                // Progress Bar
                LinearProgressIndicator(
                    progress = (activeStepIndex + 1).toFloat() / steps.size,
                    color = EmeraldLight,
                    trackColor = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth().height(4.dp)
                )

                // Top Step Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Step ${activeStepIndex + 1} of ${steps.size}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                    Box(
                        modifier = Modifier
                            .background(IslamicGold.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = step.titleUrdu,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    }
                }

                // Central Reading Area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, SoftGold, RoundedCornerShape(16.dp))
                        .pointerInput(Unit) {
                            var totalDragAmount = 0f
                            detectHorizontalDragGestures(
                                onDragStart = { totalDragAmount = 0f },
                                onDragEnd = {
                                    if (totalDragAmount < -150f) { // Swipe Left (Next)
                                        if (activeStepIndex < steps.size - 1) {
                                            activeStepIndex++
                                        } else {
                                            isNamazCompleted = true
                                        }
                                    } else if (totalDragAmount > 150f) { // Swipe Right (Prev)
                                        if (activeStepIndex > 0) {
                                            activeStepIndex--
                                        }
                                    }
                                },
                                onHorizontalDrag = { change, dragAmount ->
                                    change.consume()
                                    totalDragAmount += dragAmount
                                }
                            )
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Title in English
                        Text(
                            text = step.titleEnglish,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Physical action box (English and Urdu)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SoftGold.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(12.dp),
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
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Action Guide",
                                        tint = EmeraldLight,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Physical Action / طریقہ کار:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                }
                                Text(
                                    text = step.actionDescription,
                                    fontSize = 12.sp,
                                    color = CharcoalDark,
                                    lineHeight = 16.sp
                                )
                                Divider(color = SoftGold, modifier = Modifier.padding(vertical = 4.dp))
                                Text(
                                    text = step.actionDescriptionUrdu,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark,
                                    textAlign = TextAlign.Right,
                                    modifier = Modifier.fillMaxWidth(),
                                    lineHeight = 20.sp
                                )
                            }
                        }

                        if (step.arabicText.isNotEmpty()) {
                            // Arabic Box with elegant calligraphy style
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(GoldenBeige.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    .border(1.dp, SoftGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = step.arabicText,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 42.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            // Transliteration Box
                            if (step.transliteration.isNotEmpty()) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "TRANSLITERATION",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = step.transliteration,
                                        fontSize = 12.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = CharcoalDark,
                                        lineHeight = 16.sp
                                    )
                                }
                            }

                            // Urdu Translation Box
                            if (step.translationUrdu.isNotEmpty()) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = EmeraldLight.copy(alpha = 0.08f)),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, EmeraldLight.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "اردو ترجمہ:",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldDark,
                                            textAlign = TextAlign.Right,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = step.translationUrdu,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = CharcoalDark,
                                            textAlign = TextAlign.Right,
                                            modifier = Modifier.fillMaxWidth(),
                                            lineHeight = 24.sp
                                        )
                                    }
                                }
                            }

                            // English Translation Box
                            if (step.translationEnglish.isNotEmpty()) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "ENGLISH TRANSLATION",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = step.translationEnglish,
                                        fontSize = 12.sp,
                                        color = CharcoalDark,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }

                        // Benefit/Significance section
                        if (step.benefit.isNotEmpty()) {
                            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = IslamicGold,
                                    modifier = Modifier.size(16.dp).padding(top = 2.dp)
                                )
                                Column {
                                    Text(
                                        text = "Spiritual Significance:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                    Text(
                                        text = step.benefit,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Simplified, immersive navigation controls bar
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Previous Button
                        OutlinedButton(
                            onClick = {
                                if (activeStepIndex > 0) {
                                    activeStepIndex--
                                }
                            },
                            enabled = activeStepIndex > 0,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous", fontSize = 12.sp)
                        }

                        // Step indicator
                        Text(
                            text = "Step ${activeStepIndex + 1} of ${steps.size}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )

                        // Next / Complete Button
                        Button(
                            onClick = {
                                if (activeStepIndex < steps.size - 1) {
                                    activeStepIndex++
                                } else {
                                    isNamazCompleted = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldLight),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (activeStepIndex < steps.size - 1) "Next" else "Complete",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = if (activeStepIndex < steps.size - 1) Icons.Default.ArrowForwardIos else Icons.Default.CheckCircle,
                                contentDescription = "Next/Complete",
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// NAMAZ STEP DATA CLASS & REFS WITH URDU TRANSLATION
// -----------------------------------------------------------------------------

data class NamazStep(
    val id: Int,
    val titleEnglish: String,
    val titleUrdu: String,
    val actionDescription: String,
    val actionDescriptionUrdu: String,
    val arabicText: String,
    val transliteration: String,
    val translationUrdu: String,
    val translationEnglish: String,
    val benefit: String
)

fun getNamazSteps(): List<NamazStep> {
    return listOf(
        NamazStep(
            id = 1,
            titleEnglish = "Niyyah (Intention)",
            titleUrdu = "نیت",
            actionDescription = "Formulate a sincere intention in your heart to perform the prayer for Allah. Stand facing the Qiblah with your feet comfortably spaced.",
            actionDescriptionUrdu = "دل میں نماز کی پختہ نیت کریں کہ آپ صرف اور صرف اللہ تعالیٰ کے لیے نماز ادا کر رہے ہیں۔ قبلہ رخ ہو کر کھڑے ہوں اور دونوں پاؤں کے درمیان مناسب فاصلہ رکھیں۔",
            arabicText = "نَوَيْتُ أَنْ أُصَلِّيَ لِلَّهِ تَعَالَى مُتَوَجِّهًا إِلَى جِهَةِ الْكَعْبَةِ الشَّرِيفَةِ",
            transliteration = "Nawaytu an usalliya lillahi ta'ala mutawajjihan ila jihatil Ka'batish-sharifah",
            translationUrdu = "میں نے اللہ تعالیٰ کے لیے نماز کی نیت کی، قبلہ رخ ہو کر کعبہ شریف کی طرف۔",
            translationEnglish = "I intend to perform prayer for Allah, facing towards the Holy Ka'bah.",
            benefit = "Intention (Niyyah) is the foundation of every deed in Islam, focusing your heart completely on Allah."
        ),
        NamazStep(
            id = 2,
            titleEnglish = "Takbeer-e-Tahreema (Opening Takbeer)",
            titleUrdu = "تکبیرِ تحریمہ",
            actionDescription = "Raise both hands to your ear lobes (for men) or to your shoulders (for women) while saying 'Allahu Akbar'. Keep your gaze fixed at the place of prostration.",
            actionDescriptionUrdu = "دونوں ہاتھ کانوں کے لو تک اٹھائیں (مردوں کے لیے) یا اپنے کندھوں تک (عورتوں کے لیے) اور کہتے ہوئے: اللہ اکبر، ہاتھ باندھ لیں۔ نگاہ سجدہ کی جگہ پر رکھیں۔",
            arabicText = "اللَّهُ أَكْبَرُ",
            transliteration = "Allahu Akbar",
            translationUrdu = "اللہ سب سے بڑا ہے۔",
            translationEnglish = "Allah is the Greatest.",
            benefit = "By saying 'Allahu Akbar', you declare that Allah is greater than everything, pushing away worldly thoughts."
        ),
        NamazStep(
            id = 3,
            titleEnglish = "Sana (Opening Supplication)",
            titleUrdu = "ثناء",
            actionDescription = "Fold your hands (right hand over left hand below the navel for men, or on the chest for women) and recite the opening supplication.",
            actionDescriptionUrdu = "ہاتھ باندھ لیں (سیدھا ہاتھ الٹے ہاتھ کے اوپر ناف کے نیچے مردوں کے لیے، یا سینے پر عورتوں کے لیے) اور ثناء پڑھیں۔",
            arabicText = "سُبْحَانَكَ اللَّهُمَّ وَبِحَمْدِكَ وَتَبَارَكَ اسْمُكَ وَتَعَالَى جَدُّكَ وَلَا إِلَهَ غَيْرُكَ",
            transliteration = "Subhanaka Allahumma wa bihamdika wa tabaraka-smuka wa ta'ala jadduka wa la ilaha ghayruk",
            translationUrdu = "پاک ہے تو اے اللہ! اور اپنی تعریفوں کے ساتھ، اور برکت والا ہے تیرا نام، اور بلند ہے تیری شان، اور تیرے سوا کوئی معبود نہیں ہے۔",
            translationEnglish = "Glory be to You, O Allah, and all praise is Yours. Blessed is Your name and exalted is Your majesty. There is no deity worthy of worship besides You.",
            benefit = "Sana directly praises the pure essence of Allah, establishing humility at the very start of prayer."
        ),
        NamazStep(
            id = 4,
            titleEnglish = "Ta'awwudh & Tasmiyah",
            titleUrdu = "تعوذ اور تسمیہ",
            actionDescription = "Seek refuge in Allah from Satan the outcast and begin in the name of Allah.",
            actionDescriptionUrdu = "شیطان مردود سے اللہ کی پناہ مانگیں اور اللہ کے نام سے شروع کریں۔",
            arabicText = "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطَانِ الرَّجِيمِ\nبِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ",
            transliteration = "A'udhu billahi minash-shaytanir-rajim. Bismillahir-rahmanir-rahim.",
            translationUrdu = "میں اللہ کی پناہ مانگتا ہوں شیطان مردود سے۔ اللہ کے نام سے شروع جو بڑا مہربان نہایت رحم کرنے والا ہے۔",
            translationEnglish = "I seek refuge in Allah from Satan the rejected. In the name of Allah, the Most Gracious, the Most Merciful.",
            benefit = "Seeking refuge purifies the mind from evil whispers, and starting in Allah's name brings infinite blessings."
        ),
        NamazStep(
            id = 5,
            titleEnglish = "Surah Al-Fatiha",
            titleUrdu = "سورۃ الفاتحہ",
            actionDescription = "Recite the obligatory opening chapter of the Holy Quran.",
            actionDescriptionUrdu = "قرآن پاک کی لازمی افتتاحی سورت کی تلاوت کریں۔",
            arabicText = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ ۝ الرَّحْمَنِ الرَّحِيمِ ۝ مَالِكِ يَوْمِ الدِّينِ ۝ إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ ۝ اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ ۝ صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
            transliteration = "Alhamdu lillahi rabbil 'alamin. Ar-Rahmanir-Rahim. Maliki yawmid-din. Iyyaka na'budu wa iyyaka nasta'in. Ihdinas-siratal-mustaqim. Siratalladhina an'amta 'alayhim ghayril-maghdubi 'alayhim wa lad-dallin.",
            translationUrdu = "سب تعریفیں اللہ ہی کے لیے ہیں جو تمام جہانوں کا پالنے والا ہے، بڑا مہربان نہایت رحم والا ہے، انصاف کے دن کا مالک ہے۔ ہم تیری ہی عبادت کرتے ہیں اور تجھ ہی سے مدد مانگتے ہیں۔ ہمیں سیدھے راستے پر چلا، ان لوگوں کے راستے پر جن پر تو نے انعام کیا، نہ کہ ان پر جن پر غضب کیا گیا اور نہ گمراہوں کے۔",
            translationEnglish = "All praise is [due] to Allah, Lord of the worlds. The Entirely Merciful, the Especially Merciful. Sovereign of the Day of Recompense. It is You we worship and You we ask for help. Guide us to the straight path - The path of those upon whom You have bestowed favor, not of those who have earned [Your] anger or of those who are astray.",
            benefit = "Surah Al-Fatiha is the core of Islamic prayer (Salah). No prayer is valid without its recitation."
        ),
        NamazStep(
            id = 6,
            titleEnglish = "Recitation of Surah (e.g., Surah Al-Ikhlas)",
            titleUrdu = "سورۃ ملانا (سورۃ الاخلاص)",
            actionDescription = "Recite another short Surah or verses of the Quran (such as Surah Al-Ikhlas).",
            actionDescriptionUrdu = "قرآن پاک کی کوئی دوسری چھوٹی سورت یا کچھ آیات تلاوت کریں (مثلاً سورۃ الاخلاص)۔",
            arabicText = "قُلْ هُوَ اللَّهُ أَحَدٌ ۝ اللَّهُ الصَّمَدُ ۝ لَمْ يَلِدْ وَلَمْ يُولَدْ ۝ وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
            transliteration = "Qul huwallahu ahad. Allahus-samad. Lam yalid wa lam yulad. Wa lam yakullahu kufuwan ahad.",
            translationUrdu = "آپ کہہ دیجیے کہ وہ اللہ ایک ہے، اللہ بے نیاز ہے، نہ اس کی کوئی اولاد ہے اور نہ وہ کسی کی اولاد ہے، اور اس کے جوڑ کا کوئی نہیں ہے۔",
            translationEnglish = "Say, 'He is Allah, [who is] One. Allah, the Eternal Refuge. He neither begets nor is born, Nor is there to Him any equivalent.'",
            benefit = "Surah Al-Ikhlas represents one-third of the Quran, affirming the absolute Oneness of Allah (Tawheed)."
        ),
        NamazStep(
            id = 7,
            titleEnglish = "Ruku (Bowing Down)",
            titleUrdu = "رکوع",
            actionDescription = "Say 'Allahu Akbar' and bow down. Place your hands firmly on your knees, fingers spread, keeping your back and head completely straight and horizontal. Recite the glorification at least 3 times.",
            actionDescriptionUrdu = "کہتے ہوئے: اللہ اکبر، رکوع میں جھک جائیں۔ اپنے ہاتھوں کو گھٹنوں پر مضبوطی سے رکھیں، انگلیاں پھیلی ہوئی ہوں، اور اپنی کمر اور سر کو بالکل برابر اور سیدھا رکھیں۔ ۳ بار تسبیح پڑھیں۔",
            arabicText = "سُبْحَانَ رَبِّيَ الْعَظِيمِ",
            transliteration = "Subhana Rabbiyal Azeem (3 times)",
            translationUrdu = "پاک ہے میرا پروردگار عظمت والا۔",
            translationEnglish = "Glory be to my Lord, the Most Magnificent.",
            benefit = "Ruku represents physical submission, bowing your pride and mind to the supreme Creator."
        ),
        NamazStep(
            id = 8,
            titleEnglish = "Qauma (Standing after Ruku)",
            titleUrdu = "قومہ",
            actionDescription = "Rise up to a straight standing position. While rising, recite the praise of Allah, then say the response when fully standing with hands by your sides.",
            actionDescriptionUrdu = "رکوع سے اٹھ کر بالکل سیدھے کھڑے ہو جائیں۔ اٹھتے ہوئے اللہ کی حمد پڑھیں، اور جب سیدھے کھڑے ہو جائیں تو جواباً یہ الفاظ پڑھیں۔ ہاتھ کھلے چھوڑ دیں۔",
            arabicText = "سَمِعَ اللَّهُ لِمَنْ حَمِدَهُ\nرَبَّنَا وَلَكَ الْحَمْدُ",
            transliteration = "Sami'a Allahu liman hamidah. Rabbana wa lakal hamd.",
            translationUrdu = "اللہ نے سن لی اس کی جس نے اس کی تعریف کی۔ اے ہمارے رب! تمام تعریفیں تیرے ہی لیے ہیں۔",
            translationEnglish = "Allah hears those who praise Him. Our Lord, all praise is Yours.",
            benefit = "Standing in gratitude (Qauma) shows trust and thankfulness to Allah for raising you after bowing."
        ),
        NamazStep(
            id = 9,
            titleEnglish = "First Sajdah (Prostration)",
            titleUrdu = "سجدہ",
            actionDescription = "Say 'Allahu Akbar' and go down into prostration. Ensure your knees touch first, then palms, nose, and forehead. Toes must point forward towards Qiblah. Recite 3 times.",
            actionDescriptionUrdu = "کہتے ہوئے: اللہ اکبر، سجدے میں جائیں۔ پہلے گھٹنے زمین پر رکھیں، پھر ہاتھ، پھر ناک اور پیشانی۔ پاؤں کی انگلیاں قبلہ رخ مڑی ہوں۔ ۳ بار تسبیح پڑھیں۔",
            arabicText = "سُبْحَانَ رَبِّيَ الْأَعْلَى",
            transliteration = "Subhana Rabbiyal A'la (3 times)",
            translationUrdu = "پاک ہے میرا پروردگار سب سے بلند تر۔",
            translationEnglish = "Glory be to my Lord, the Most High.",
            benefit = "Sajdah is the closest a servant gets to Allah. It heals the soul and eliminates arrogance."
        ),
        NamazStep(
            id = 10,
            titleEnglish = "Jalsa (Sitting between Sajdahs)",
            titleUrdu = "جلسہ",
            actionDescription = "Say 'Allahu Akbar' and sit up straight on your left foot while keeping your right foot upright, toes pointing Qiblah. Place palms on thighs. Recite the prayer for forgiveness.",
            actionDescriptionUrdu = "کہتے ہوئے: اللہ اکبر، سجدے سے اٹھ کر بائیں پاؤں پر بیٹھ جائیں اور دایاں پاؤں کھڑا رکھیں، دونوں ہاتھ رانوں پر رکھیں۔ بخشش کی یہ دعا پڑھیں۔",
            arabicText = "اللَّهُمَّ اغْفِرْ لِي وَارْحَمْنِي وَاجْبُرْنِي وَاهْدِنِي وَارْزُقْنِي",
            transliteration = "Allahumma-ghfirli warhamni wajburni wahdini warzuqni",
            translationUrdu = "اے اللہ! مجھے بخش دے، مجھ پر رحم کر، میرے نقصان کی تلافی کر، مجھے ہدایت دے اور مجھے رزق عطا فرما۔",
            translationEnglish = "O Allah, forgive me, have mercy on me, mend my shortcomings, guide me and provide for me.",
            benefit = "Sitting calmly between prostrations reflects poise and provides a precious moment to ask Allah for total well-being."
        ),
        NamazStep(
            id = 11,
            titleEnglish = "Second Sajdah",
            titleUrdu = "دوسرا سجدہ",
            actionDescription = "Say 'Allahu Akbar' and perform the second prostration exactly like the first one, reciting the glorification 3 times.",
            actionDescriptionUrdu = "کہتے ہوئے: اللہ اکبر، پہلے سجدے کی طرح دوسرا سجدہ کریں اور عاجزی کے ساتھ ۳ بار تسبیح پڑھیں۔",
            arabicText = "سُبْحَانَ رَبِّيَ الْأَعْلَى",
            transliteration = "Subhana Rabbiyal A'la (3 times)",
            translationUrdu = "پاک ہے میرا پروردگار سب سے بلند تر۔",
            translationEnglish = "Glory be to my Lord, the Most High.",
            benefit = "Completes the prostration cycle, rooting the believer's absolute humility to the Creator twice."
        ),
        NamazStep(
            id = 12,
            titleEnglish = "Tashahhud (Sitting Testimony)",
            titleUrdu = "تشہد",
            actionDescription = "In the second or final Rak'ah, sit calmly and recite Tashahhud. Raise your right index finger as you say 'Ash-hadu alla ilaha' and lower it back.",
            actionDescriptionUrdu = "دوسری یا آخری رکعت میں بیٹھ کر التحیات پڑھیں اور 'اشھد ان لا الہ' پر شہادت کی انگلی اٹھائیں اور 'الا اللہ' پر واپس گرا دیں۔",
            arabicText = "التَّحِيَّاتُ لِلَّهِ وَالصَّلَوَاتُ وَالطَّيِّبَاتُ، السَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وَعَلَى عِبَادِ اللَّهِ الصَّالِحِينَ، أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا اللَّهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ",
            transliteration = "Attahiyyatu lillahi was-salawatu wat-tayyibatu, as-salamu 'alayka ayyuhan-nabiyyu wa rahmatullahi wa barakatuhu, as-salamu 'alayna wa 'ala 'ibadillahis-salihin. Ashhadu alla ilaha illallahu wa ashhadu anna Muhammadan 'abduhu wa rasuluhu.",
            translationUrdu = "تمام قولی، بدنی اور مالی عبادتیں اللہ ہی کے لیے ہیں۔ سلام ہو آپ پر اے نبی اور اللہ کی رحمت اور اس کی برکتیں ہوں۔ سلام ہو ہم پر اور اللہ کے نیک بندوں پر۔ میں گواہی دیتا ہوں کہ اللہ کے سوا کوئی معبود نہیں اور میں گواہی دیتا ہوں کہ محمد اللہ کے بندے اور اس کے رسول ہیں۔",
            translationEnglish = "All verbal, physical and monetary worship is due to Allah. Peace be upon you, O Prophet, and the mercy of Allah and His blessings. Peace be upon us and upon the righteous servants of Allah. I bear witness that there is no deity worthy of worship except Allah, and I bear witness that Muhammad is His servant and His Messenger.",
            benefit = "Tashahhud commemorates the sacred ascension (Mi'raj) greeting between Allah and Prophet Muhammad (PBUH)."
        ),
        NamazStep(
            id = 13,
            titleEnglish = "Durood-e-Ibrahim (Salutations)",
            titleUrdu = "درودِ ابراہیمی",
            actionDescription = "In the final Rak'ah, recite Durood-e-Ibrahim to send peace and blessings upon Prophet Muhammad (PBUH) and Prophet Ibrahim (A.S).",
            actionDescriptionUrdu = "آخری رکعت میں تشہد کے بعد نبی کریم صلی اللہ علیہ وسلم اور حضرت ابراہیم علیہ السلام پر درود بھیجیں۔",
            arabicText = "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا صَلَّيْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ إِنَّكَ حَمِيدٌ مَجِيدٌ ۝ اللَّهُمَّ بَارِكْ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا بَارَكْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ إِنَّكَ حَمِيدٌ مَجِيدٌ",
            transliteration = "Allahumma salli 'ala Muhammadin wa 'ala ali Muhammadin kama sallayta 'ala Ibrahima wa 'ala ali Ibrahima innaka Hamidum Majid. Allahumma barik 'ala Muhammadin wa 'ala ali Muhammadin kama barakta 'ala Ibrahima wa 'ala ali Ibrahima innaka Hamidum Majid.",
            translationUrdu = "اے اللہ! رحمتیں نازل فرما محمدؐ پر اور محمدؐ کی آل پر، جس طرح تو نے رحمتیں نازل فرمائیں ابراہیمؑ پر اور ابراہیمؑ کی آل پر، بے شک تو قابلِ تعریف اور بڑی شان والا ہے۔ اے اللہ! برکت نازل فرما محمدؐ پر اور محمدؐ کی آل پر، جس طرح تو نے برکت نازل فرمائی ابراہیمؑ پر اور ابراہیمؑ کی آل پر، بے شک تو قابلِ تعریف اور بڑی شان والا ہے۔",
            translationEnglish = "O Allah, send prayers upon Muhammad and upon the family of Muhammad, as You sent prayers upon Abraham and upon the family of Abraham; indeed, You are Praiseworthy and Glorious. O Allah, bless Muhammad and the family of Muhammad, as You blessed Abraham and the family of Abraham; indeed, You are Praiseworthy and Glorious.",
            benefit = "Reciting Durood brings immense peace and is a source of obtaining ten blessings from Allah in return."
        ),
        NamazStep(
            id = 14,
            titleEnglish = "Dua-e-Masoorah (Final Supplication)",
            titleUrdu = "دعائے ماثورہ",
            actionDescription = "Before finishing the prayer, recite a beautiful Quranic or Sunnah supplication asking for forgiveness and steadfastness.",
            actionDescriptionUrdu = "سلام پھیرنے اور نماز مکمل کرنے سے پہلے قرآن و حدیث سے منقولہ کوئی خوبصورت دعا پڑھیں۔",
            arabicText = "رَبِّ اجْعَلْنِي مُقِيمَ الصَّلَاةِ وَمِنْ ذُرِّيَّتِي رَبَّنَا وَتَقَبَّلْ دُعَاءِ ۝ رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ وَلِلْمُؤْمِنِينَ يَوْمَ يَقُومُ الْحِسَابُ",
            transliteration = "Rabbij'alni muqimas-salati wa min dhurriyyati Rabbana wa taqabbal du'a. Rabbanagh-fir li wa li-walidayya wa lil-mu'minina yawma yaqumul-hisab.",
            translationUrdu = "اے میرے رب! مجھے اور میری اولاد کو نماز قائم کرنے والا بنا دے، اے ہمارے رب! اور میری دعا قبول فرما۔ اے ہمارے رب! مجھے، میرے والدین کو اور سب ایمان والوں کو اس دن بخش دینا جس دن حساب قائم ہو گا۔",
            translationEnglish = "My Lord, make me an establisher of prayer, and [many] from my descendants. Our Lord, and accept my supplication. Our Lord, forgive me and my parents and the believers the Day the account is established.",
            benefit = "A comprehensive supplication for parental forgiveness and spiritual protection."
        ),
        NamazStep(
            id = 15,
            titleEnglish = "Witr: Extra Takbeer (وتر: تکبیر)",
            titleUrdu = "وتر کی زائد تکبیر",
            actionDescription = "In the third Rak'ah of Witr prayer, after reciting Surah Al-Fatiha and another Surah, say 'Allahu Akbar' while raising your hands to your ear lobes (or shoulders), then fold them again as in Qiyam.",
            actionDescriptionUrdu = "وتر کی تیسری رکعت میں سورۃ الفاتحہ اور کوئی سورت پڑھنے کے بعد، رکوع میں جانے سے پہلے 'اللہ اکبر' کہتے ہوئے اپنے ہاتھ کانوں (یا کندھوں) تک اٹھائیں اور پھر دوبارہ ناف کے نیچے (یا سینے پر) باندھ لیں۔",
            arabicText = "اللَّهُ أَكْبَرُ",
            transliteration = "Allahu Akbar",
            translationUrdu = "اللہ سب سے بڑا ہے۔",
            translationEnglish = "Allah is the Greatest.",
            benefit = "This extra Takbeer is unique to the Witr prayer, signaling the special status of this intimate night prayer and the transition to the Qunoot supplication."
        ),
        NamazStep(
            id = 16,
            titleEnglish = "Dua-e-Qunoot (Supplication of Witr)",
            titleUrdu = "دعائے قنوت",
            actionDescription = "Recite the Dua-e-Qunoot silently with hands folded. Keep your eyes focused on the place of prostration.",
            actionDescriptionUrdu = "ہاتھ باندھی ہوئی حالت میں دعائے قنوت خشوع و خضوع کے ساتھ پڑھیں۔ اپنی نظر سجدے کی جگہ پر رکھیں۔",
            arabicText = "اَللَّهُمَّ إِنَّا نَسْتَعِينُكَ وَنَسْتَغْفِرُكَ وَنُؤْمِنُ بِكَ وَنَتَوَكَّلُ عَلَيْكَ وَنُثْنِيْ عَلَيْكَ الْخَيْرَ وَنَشْکُرُكَ وَلَا نَکْفُرُكَ وَنَخْلَعُ وَنَتْرُكُ مَنْ يَّفْجُرُكَ۔ اَللَّهُمَّ إِيَّاكَ نَعْبُدُ وَلَكَ نُصَلِّيْ وَنَسْجُدُ وَإِلَيْكَ نَسْعَىٰ وَنَحْفِدُ وَنَرْجُوْ رَحْمَتَكَ وَنَخْشَىٰ عَذَابَكَ إِنَّ عَذَابَكَ بِالْكُفَّارِ مُلْحِقٌ",
            transliteration = "Allahumma inna nasta'inuka wa nastaghfiruka wa nu'minu bika wa natawakkalu 'alayka wa nuthni 'alaykal-khayra wa nashkuruka wa la nakfuruka wa nakhla'u wa natruku may-yafjuruk. Allahumma iyyaka na'budu wa laka nusalli wa nasjudu wa ilayka nas'a wa nahfidu wa narju rahmataka wa nakhsha 'adhabaka inna 'adhabaka bil-kuffari mulhiq.",
            translationUrdu = "اے اللہ! ہم تجھ سے مدد چاہتے ہیں اور تجھ سے معافی مانگتے ہیں اور تجھ پر ایمان رکھتے ہیں اور تجھ پر بھروسہ کرتے ہیں اور تیری بہت اچھی تعریف کرتے ہیں اور تیرا شکر ادا کرتے ہیں اور تیری ناشکری نہیں کرتے اور الگ کرتے ہیں اور چھوڑتے ہیں اس شخص کو جو تیری نافرمانی کرے۔ اے اللہ! ہم تیری ہی عبادت کرتے ہیں اور تیرے ہی لیے نماز پڑھتے ہیں اور سجدہ کرتے ہیں اور تیری ہی طرف دوڑتے ہیں اور خدمت کے لیے حاضر ہوتے ہیں اور تیری رحمت کے امیدوار ہیں اور تیرے عذاب سے ڈرتے ہیں، بے شک تیرا عذاب کافروں کو پہنچنے والا ہے۔",
            translationEnglish = "O Allah! We implore You for help and beg for Your forgiveness, and believe in You and rely on You and praise You in the best way and we thank You and we are not ungrateful to You, and we turn away from and leave him who disobeys You. O Allah! You alone we worship and for You we pray and prostrate, and towards You we hasten and we serve, and we hope for Your mercy and fear Your punishment. Verily, Your punishment is bound to overtake the disbelievers.",
            benefit = "Dua-e-Qunoot is a comprehensive supplication of praise, submission, and absolute reliance on Allah, usually recited in the last Rak'ah of Witr prayer before bowing."
        ),
        NamazStep(
            id = 17,
            titleEnglish = "Salaam (Closing Greeting)",
            titleUrdu = "سلام",
            actionDescription = "Turn your face to the right, saying the Salaam to greet the angels and fellow believers, then turn your face to the left repeating the same.",
            actionDescriptionUrdu = "سب سے پہلے اپنے دائیں کندھے کی طرف رخ کر کے سلام کہیں، پھر بائیں کندھے کی طرف رخ کر کے سلام کہیں۔ اس کے ساتھ ہی آپ کی نماز مکمل ہو جائے گی۔",
            arabicText = "السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ",
            transliteration = "Assalamu Alaykum wa Rahmatullah",
            translationUrdu = "تم پر سلامتی ہو اور اللہ کی رحمت ہو۔",
            translationEnglish = "Peace and blessings of Allah be upon you.",
            benefit = "Salah ends with a beautiful declaration of peace and blessings for humanity and angels."
        )
    )
}

// -----------------------------------------------------------------------------
// NAMAZ-E-JANAZA (FUNERAL PRAYER) INTERACTIVE GUIDE READER SCREEN
// -----------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JanazaReaderScreen(
    viewModel: StudentKitViewModel,
    onBack: () -> Unit
) {
    val steps = remember { getJanazaSteps() }
    var activeStepIndex by remember { mutableStateOf(0) }
    var isJanazaCompleted by remember { mutableStateOf(false) }

    if (isJanazaCompleted) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(EmeraldDark, EmeraldLight)))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(IslamicGold.copy(alpha = 0.2f), CircleShape)
                        .border(2.dp, IslamicGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = IslamicGold,
                        modifier = Modifier.size(64.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "جزاک اللہ خیراً",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = IslamicGold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Namaz-e-Janaza Guide Completed",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "You have learned the complete method and supplications for Namaz-e-Janaza. May Allah grant patience to the grieving families and bless our deceased with Jannah. Ameen.",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Dashboard)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Go Back to Home 🔙",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Namaz-e-Janaza (نماز جنازہ)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                            Text(
                                text = "Step-by-step Funeral Prayer with Urdu Translation",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back to Library")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        ) { paddingValues ->
            val step = steps[activeStepIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(GoldenBeige)
            ) {
                // Progress Bar
                LinearProgressIndicator(
                    progress = (activeStepIndex + 1).toFloat() / steps.size,
                    color = EmeraldLight,
                    trackColor = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth().height(4.dp)
                )

                // Top Step Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Step ${activeStepIndex + 1} of ${steps.size}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                    Box(
                        modifier = Modifier
                            .background(IslamicGold.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = step.titleUrdu,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    }
                }

                // Central Reading Area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, SoftGold, RoundedCornerShape(16.dp))
                        .pointerInput(Unit) {
                            var totalDragAmount = 0f
                            detectHorizontalDragGestures(
                                onDragStart = { totalDragAmount = 0f },
                                onDragEnd = {
                                    if (totalDragAmount < -150f) { // Swipe Left (Next)
                                        if (activeStepIndex < steps.size - 1) {
                                            activeStepIndex++
                                        } else {
                                            isJanazaCompleted = true
                                        }
                                    } else if (totalDragAmount > 150f) { // Swipe Right (Prev)
                                        if (activeStepIndex > 0) {
                                            activeStepIndex--
                                        }
                                    }
                                },
                                onHorizontalDrag = { change, dragAmount ->
                                    change.consume()
                                    totalDragAmount += dragAmount
                                }
                            )
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Title in English
                        Text(
                            text = step.titleEnglish,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Physical action box (English and Urdu)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SoftGold.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(12.dp),
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
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Action Guide",
                                        tint = EmeraldLight,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Physical Action / طریقہ کار:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                }
                                Text(
                                    text = step.actionDescription,
                                    fontSize = 12.sp,
                                    color = CharcoalDark,
                                    lineHeight = 16.sp
                                )
                                Divider(color = SoftGold, modifier = Modifier.padding(vertical = 4.dp))
                                Text(
                                    text = step.actionDescriptionUrdu,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark,
                                    textAlign = TextAlign.Right,
                                    modifier = Modifier.fillMaxWidth(),
                                    lineHeight = 20.sp
                                )
                            }
                        }

                        if (step.arabicText.isNotEmpty()) {
                            // Arabic Box with elegant calligraphy style
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(GoldenBeige.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    .border(1.dp, SoftGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = step.arabicText,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 42.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            // Transliteration Box
                            if (step.transliteration.isNotEmpty()) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "TRANSLITERATION",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = step.transliteration,
                                        fontSize = 12.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = CharcoalDark,
                                        lineHeight = 16.sp
                                    )
                                }
                            }

                            // Urdu Translation Box
                            if (step.translationUrdu.isNotEmpty()) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = EmeraldLight.copy(alpha = 0.08f)),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, EmeraldLight.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "اردو ترجمہ:",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldDark,
                                            textAlign = TextAlign.Right,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = step.translationUrdu,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = CharcoalDark,
                                            textAlign = TextAlign.Right,
                                            modifier = Modifier.fillMaxWidth(),
                                            lineHeight = 24.sp
                                        )
                                    }
                                }
                            }

                            // English Translation Box
                            if (step.translationEnglish.isNotEmpty()) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "ENGLISH TRANSLATION",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = step.translationEnglish,
                                        fontSize = 12.sp,
                                        color = CharcoalDark,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }

                        // Benefit/Significance section
                        if (step.benefit.isNotEmpty()) {
                            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = IslamicGold,
                                    modifier = Modifier.size(16.dp).padding(top = 2.dp)
                                )
                                Column {
                                    Text(
                                        text = "Spiritual Significance & Wisdom:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                    Text(
                                        text = step.benefit,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Bottom navigation controls bar
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Previous Button
                        OutlinedButton(
                            onClick = {
                                if (activeStepIndex > 0) {
                                    activeStepIndex--
                                }
                            },
                            enabled = activeStepIndex > 0,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous", fontSize = 12.sp)
                        }

                        // Step indicator
                        Text(
                            text = "Step ${activeStepIndex + 1} of ${steps.size}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )

                        // Next / Complete Button
                        Button(
                            onClick = {
                                if (activeStepIndex < steps.size - 1) {
                                    activeStepIndex++
                                } else {
                                    isJanazaCompleted = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldLight),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (activeStepIndex < steps.size - 1) "Next" else "Complete",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = if (activeStepIndex < steps.size - 1) Icons.Default.ArrowForwardIos else Icons.Default.CheckCircle,
                                contentDescription = "Next/Complete",
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// DUA-E-TAZIYAT (CONDOLENCE) INTERACTIVE GUIDE READER SCREEN
// -----------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaziyatReaderScreen(
    viewModel: StudentKitViewModel,
    onBack: () -> Unit
) {
    val steps = remember { getTaziyatSteps() }
    var activeStepIndex by remember { mutableStateOf(0) }
    var isTaziyatCompleted by remember { mutableStateOf(false) }

    if (isTaziyatCompleted) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(EmeraldDark, EmeraldLight)))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(IslamicGold.copy(alpha = 0.2f), CircleShape)
                        .border(2.dp, IslamicGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = IslamicGold,
                        modifier = Modifier.size(64.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "الحمد لله",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = IslamicGold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Dua-e-Taziyat Guide Completed",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "May Allah reward you for practicing the Sunnah of visiting and comforting the bereaved. May He grant patience (Sabr) and infinite blessings to all of us. Ameen.",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Dashboard)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Go Back to Home 🔙",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Dua-e-Taziyat (دعائے تعزیت)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                            Text(
                                text = "Condolence Sunnah, Duas, and Sabr Guidelines",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back to Library")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        ) { paddingValues ->
            val step = steps[activeStepIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(GoldenBeige)
            ) {
                // Progress Bar
                LinearProgressIndicator(
                    progress = (activeStepIndex + 1).toFloat() / steps.size,
                    color = EmeraldLight,
                    trackColor = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth().height(4.dp)
                )

                // Top Step Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Step ${activeStepIndex + 1} of ${steps.size}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                    Box(
                        modifier = Modifier
                            .background(IslamicGold.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = step.titleUrdu,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    }
                }

                // Central Reading Area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, SoftGold, RoundedCornerShape(16.dp))
                        .pointerInput(Unit) {
                            var totalDragAmount = 0f
                            detectHorizontalDragGestures(
                                onDragStart = { totalDragAmount = 0f },
                                onDragEnd = {
                                    if (totalDragAmount < -150f) { // Swipe Left (Next)
                                        if (activeStepIndex < steps.size - 1) {
                                            activeStepIndex++
                                        } else {
                                            isTaziyatCompleted = true
                                        }
                                    } else if (totalDragAmount > 150f) { // Swipe Right (Prev)
                                        if (activeStepIndex > 0) {
                                            activeStepIndex--
                                        }
                                    }
                                },
                                onHorizontalDrag = { change, dragAmount ->
                                    change.consume()
                                    totalDragAmount += dragAmount
                                }
                            )
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Title in English
                        Text(
                            text = step.titleEnglish,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Physical action box (English and Urdu)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SoftGold.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(12.dp),
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
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Guideline Indicator",
                                        tint = EmeraldLight,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Sunnah Guideline / سنت مبارکہ:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                }
                                Text(
                                    text = step.actionDescription,
                                    fontSize = 12.sp,
                                    color = CharcoalDark,
                                    lineHeight = 16.sp
                                )
                                Divider(color = SoftGold, modifier = Modifier.padding(vertical = 4.dp))
                                Text(
                                    text = step.actionDescriptionUrdu,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark,
                                    textAlign = TextAlign.Right,
                                    modifier = Modifier.fillMaxWidth(),
                                    lineHeight = 20.sp
                                )
                            }
                        }

                        if (step.arabicText.isNotEmpty()) {
                            // Arabic Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(GoldenBeige.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    .border(1.dp, SoftGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = step.arabicText,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 42.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            // Transliteration Box
                            if (step.transliteration.isNotEmpty()) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "TRANSLITERATION",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = step.transliteration,
                                        fontSize = 12.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = CharcoalDark,
                                        lineHeight = 16.sp
                                    )
                                }
                            }

                            // Urdu Translation Box
                            if (step.translationUrdu.isNotEmpty()) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = EmeraldLight.copy(alpha = 0.08f)),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, EmeraldLight.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "اردو ترجمہ:",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldDark,
                                            textAlign = TextAlign.Right,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = step.translationUrdu,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = CharcoalDark,
                                            textAlign = TextAlign.Right,
                                            modifier = Modifier.fillMaxWidth(),
                                            lineHeight = 24.sp
                                        )
                                    }
                                }
                            }

                            // English Translation Box
                            if (step.translationEnglish.isNotEmpty()) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "ENGLISH TRANSLATION",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = step.translationEnglish,
                                        fontSize = 12.sp,
                                        color = CharcoalDark,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }

                        // Benefit/Significance section
                        if (step.benefit.isNotEmpty()) {
                            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = IslamicGold,
                                    modifier = Modifier.size(16.dp).padding(top = 2.dp)
                                )
                                Column {
                                    Text(
                                        text = "Spiritual Significance & Wisom:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                    Text(
                                        text = step.benefit,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Bottom navigation controls bar
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Previous Button
                        OutlinedButton(
                            onClick = {
                                if (activeStepIndex > 0) {
                                    activeStepIndex--
                                }
                            },
                            enabled = activeStepIndex > 0,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous", fontSize = 12.sp)
                        }

                        // Step indicator
                        Text(
                            text = "Step ${activeStepIndex + 1} of ${steps.size}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )

                        // Next / Complete Button
                        Button(
                            onClick = {
                                if (activeStepIndex < steps.size - 1) {
                                    activeStepIndex++
                                } else {
                                    isTaziyatCompleted = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldLight),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (activeStepIndex < steps.size - 1) "Next" else "Complete",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = if (activeStepIndex < steps.size - 1) Icons.Default.ArrowForwardIos else Icons.Default.CheckCircle,
                                contentDescription = "Next/Complete",
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// NAMAZ-E-JANAZA & DUA-E-TAZIYAT DATA CLASS & STEP GENERATORS
// -----------------------------------------------------------------------------

data class JanazaStep(
    val id: Int,
    val titleEnglish: String,
    val titleUrdu: String,
    val actionDescription: String,
    val actionDescriptionUrdu: String,
    val arabicText: String,
    val transliteration: String,
    val translationUrdu: String,
    val translationEnglish: String,
    val benefit: String
)

data class TaziyatStep(
    val id: Int,
    val titleEnglish: String,
    val titleUrdu: String,
    val actionDescription: String,
    val actionDescriptionUrdu: String,
    val arabicText: String,
    val transliteration: String,
    val translationUrdu: String,
    val translationEnglish: String,
    val benefit: String
)

fun getJanazaSteps(): List<JanazaStep> {
    return listOf(
        JanazaStep(
            id = 1,
            titleEnglish = "Niyyah (Intention) & First Takbeer",
            titleUrdu = "نیت اور پہلی تکبیر",
            actionDescription = "Make intention for Namaz-e-Janaza facing the Qiblah. Raise both hands to ears while saying 'Allahu Akbar' and fold them under the navel (for men) or on the chest (for women). Recite the opening Sana with the special Janaza phrase.",
            actionDescriptionUrdu = "قبلہ رخ کھڑے ہو کر نماز جنازہ کی نیت کریں۔ اپنے ہاتھ کانوں (یا کندھوں) تک اٹھا کر 'اللہ اکبر' کہتے ہوئے باندھ لیں، اور پھر خاص الفاظ کے ساتھ ثناء پڑھیں۔",
            arabicText = "سُبْحَانَكَ اللَّهُمَّ وَبِحَمْدِكَ وَتَبَارَكَ اسْمُكَ وَتَعَالَى جَدُّكَ وَجَلَّ ثَنَاؤُكَ وَلَا إِلَهَ غَيْرُكَ",
            transliteration = "Subhanaka Allahumma wa bihamdika wa tabarakasmuka wa ta'ala jadduka wa jalla thana'uka wa la ilaha ghayruk.",
            translationUrdu = "پاک ہے تو اے اللہ! اور اپنی تعریفوں کے ساتھ، اور برکت والا ہے تیرا نام، اور بلند ہے تیری شان، اور تیری تعریف بہت برتر ہے، اور تیرے سوا کوئی معبود نہیں ہے۔",
            translationEnglish = "Glory be to You, O Allah, and all praise is Yours. Blessed is Your name, and exalted is Your majesty, and glorious is Your praise, and there is no deity worthy of worship besides You.",
            benefit = "Adding 'Wa Jalla Thana'uka' is a specific praise reserved for funerals, acknowledging Allah's majestic greatness during times of loss."
        ),
        JanazaStep(
            id = 2,
            titleEnglish = "Second Takbeer (Durood-e-Ibrahim)",
            titleUrdu = "دوسری تکبیر (درودِ ابراہیمی)",
            actionDescription = "Without raising hands, say the second 'Allahu Akbar' and recite Durood-e-Ibrahim to send blessings on Prophet Muhammad (PBUH).",
            actionDescriptionUrdu = "ہاتھ اٹھائے بغیر دوسری تکبیر 'اللہ اکبر' کہیں اور نبی کریم صلی اللہ علیہ وسلم پر درودِ ابراہیمی بھیجیں۔",
            arabicText = "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا صَلَّيْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ إِنَّكَ حَمِيدٌ مَجِيدٌ ۝ اللَّهُمَّ بَارِكْ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا بَارَكْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ إِنَّكَ حَمِيدٌ مَجِيدٌ",
            transliteration = "Allahumma salli 'ala Muhammadin wa 'ala ali Muhammadin kama sallayta 'ala Ibrahima wa 'ala ali Ibrahima innaka Hamidum Majid. Allahumma barik 'ala Muhammadin wa 'ala ali Muhammadin kama barakta 'ala Ibrahima wa 'ala ali Ibrahima innaka Hamidum Majid.",
            translationUrdu = "اے اللہ! رحمتیں نازل فرما محمدؐ پر اور محمدؐ کی آل پر، جس طرح تو نے رحمتیں نازل فرمائیں ابراہیمؑ پر اور ابراہیمؑ کی آل پر، بے شک تو قابلِ تعریف اور بڑی شان والا ہے۔ اے اللہ! برکت نازل فرما محمدؐ پر اور محمدؐ کی آل پر، جس طرح تو نے برکت نازل فرمائی ابراہیمؑ پر اور ابراہیمؑ کی آل پر، بے شک تو قابلِ تعریف اور بڑی شان والا ہے۔",
            translationEnglish = "O Allah, send prayers upon Muhammad and upon the family of Muhammad, as You sent prayers upon Abraham and upon the family of Abraham; indeed, You are Praiseworthy and Glorious. O Allah, bless Muhammad and the family of Muhammad, as You blessed Abraham and the family of Abraham; indeed, You are Praiseworthy and Glorious.",
            benefit = "Sending blessings on the Prophet is a means of acceptance for the subsequent intercessory prayers."
        ),
        JanazaStep(
            id = 3,
            titleEnglish = "Third Takbeer: Prayer for Adults",
            titleUrdu = "تیسری تکبیر: بالغوں کی دعا",
            actionDescription = "Say the third 'Allahu Akbar' (without raising hands). If the deceased is an adult male or female, recite the following supplication for their forgiveness and the entire community.",
            actionDescriptionUrdu = "ہاتھ اٹھائے بغیر تیسری تکبیر 'اللہ اکبر' کہیں۔ اگر مرحوم بالغ مرد یا عورت ہو تو مغفرت اور بخشش کی یہ جامع دعا پڑھیں۔",
            arabicText = "اللَّهُمَّ اغْفِرْ لِحَيِّنَا وَمَيِّتِنَا وَشَاهِدِنَا وَغَائِبِنَا وَصَغِيرِنَا وَكَبِيرِنَا وَذَكَرِنَا وَأُنْثَانَا اللَّهُمَّ مَنْ أَحْيَيْتَهُ مِنَّا فَأَحْيِهِ عَلَى الْإِسْلَامِ وَمَنْ تَوَفَّيْتَهُ مِنَّا فَتَوَفَّهُ عَلَى الْإِيمَانِ",
            transliteration = "Allahummagh-fir lihayyina wa mayyitina wa shahidina wa gha'ibina wa saghirina wa kabirina wa dhakarina wa unthana. Allahumma man ahyaytahu minna fa-ahyihi 'alal-Islam, wa man tawaffaytahu minna fatawaffahu 'alal-Iman.",
            translationUrdu = "اے اللہ! ہمارے زندہ اور مردہ کو، ہمارے حاضر اور غائب کو، ہمارے چھوٹے اور بڑے کو، اور ہمارے مردوں اور عورتوں کو بخش دے۔ اے اللہ! ہم میں سے جسے تو زندہ رکھے تو اسے اسلام پر زندہ رکھ، اور جسے تو موت دے تو اسے ایمان پر موت دے۔",
            translationEnglish = "O Allah, forgive our living and our dead, those who are present and those who are absent, our young and our old, our males and our females. O Allah, whomever You keep alive among us, keep him alive upon Islam, and whomever You cause to die, let him die upon faith.",
            benefit = "This beautiful prayer is a universal call for forgiveness, linking the destiny of all believers, living and deceased, to Islam and Iman."
        ),
        JanazaStep(
            id = 4,
            titleEnglish = "Third Takbeer Alternative: For a Male Child",
            titleUrdu = "تیسری تکبیر: نابالغ لڑکے کی دعا",
            actionDescription = "Say the third 'Allahu Akbar' (without raising hands). If the deceased is a young minor boy, recite the following specific supplication.",
            actionDescriptionUrdu = "اگر فوت ہونے والا نابالغ بچہ (لڑکا) ہو تو تیسری تکبیر کے بعد یہ دعا پڑھی جاتی ہے۔",
            arabicText = "اللَّهُمَّ اجْعَلْهُ لَنَا فَرَطًا وَاجْعَلْهُ لَنَا أَجْرًا وَذُخْرًا وَاجْعَلْهُ لَنَا شَافِعًا وَمُشَفَّعًا",
            transliteration = "Allahummaj'alhu lana faratan waj'alhu lana ajran wa dhukhran waj'alhu lana shafi'an wa mushaffa'a.",
            translationUrdu = "اے اللہ! اس بچے کو ہمارے لیے آگے پہنچ کر سامانِ راحت بنانے والا بنا دے، اور اسے ہمارے لیے اجر اور ذخیرہ بنا دے، اور اسے ہمارے لیے ایسا سفارشی بنا دے جس کی سفارش قبول کی جائے۔",
            translationEnglish = "O Allah, make him for us a precursor, and make him for us a source of reward and a treasure, and make him for us an intercessor whose intercession is accepted.",
            benefit = "A minor child who passes away is considered a spiritual treasure and a direct intercessor for their parents on the Day of Judgment."
        ),
        JanazaStep(
            id = 5,
            titleEnglish = "Third Takbeer Alternative: For a Female Child",
            titleUrdu = "تیسری تکبیر: نابالغ لڑکی کی دعا",
            actionDescription = "Say the third 'Allahu Akbar' (without raising hands). If the deceased is a young minor girl, recite the following specific supplication.",
            actionDescriptionUrdu = "اگر فوت ہونے والی نابالغ بچی (لڑکی) ہو تو تیسری تکبیر کے بعد مؤنث کے صیغوں کے ساتھ یہ دعا پڑھی جاتی ہے۔",
            arabicText = "اللَّهُمَّ اجْعَلْهَا لَنَا فَرَطًا وَاجْعَلْهَا لَنَا أَجْرًا وَذُخْرًا وَاجْعَلْهَا لَنَا شَافِعَةً وَمُشَفَّعَةً",
            transliteration = "Allahummaj'alha lana faratan waj'alha lana ajran wa dhukhran waj'alha lana shafi'atan wa mushaffa'ah.",
            translationUrdu = "اے اللہ! اس بچی کو ہمارے لیے آگے پہنچ کر سامانِ راحت بنانے والی بنا دے، اور اسے ہمارے لیے اجر اور ذخیرہ بنا دے، اور اسے ہمارے لیے ایسی سفارشی بنا دے جس کی سفارش قبول کی جائے۔",
            translationEnglish = "O Allah, make her for us a precursor, and make her for us a source of reward and a treasure, and make her for us an intercessor whose intercession is accepted.",
            benefit = "This minor female child's prayer is identical to the boy's prayer with subtle modifications in Arabic pronouns for proper gender alignment."
        ),
        JanazaStep(
            id = 6,
            titleEnglish = "Fourth Takbeer & Salaam",
            titleUrdu = "چوتھی تکبیر اور سلام",
            actionDescription = "Say the fourth 'Allahu Akbar' (without raising hands). Remain standing in silence for a brief moment, then turn your head to the right and left to say Salaam, completing the prayer.",
            actionDescriptionUrdu = "ہاتھ اٹھائے بغیر چوتھی تکبیر 'اللہ اکبر' کہیں۔ ایک لمحے کے لیے خاموش کھڑے رہیں، پھر دائیں اور بائیں جانب سلام پھیر کر نماز مکمل کریں۔",
            arabicText = "السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ",
            transliteration = "Assalamu Alaykum wa Rahmatullah",
            translationUrdu = "تم پر سلامتی ہو اور اللہ کی رحمت ہو۔",
            translationEnglish = "Peace and blessings of Allah be upon you.",
            benefit = "Completes the funeral prayer. Unlike daily prayers, Namaz-e-Janaza contains no bowing (Ruku) or prostration (Sajdah)."
        )
    )
}

fun getTaziyatSteps(): List<TaziyatStep> {
    return listOf(
        TaziyatStep(
            id = 1,
            titleEnglish = "The Prophetic Condolence (Dua-e-Taziyat)",
            titleUrdu = "تعزیت کی نبوی دعا",
            actionDescription = "When visiting the grieving family, offer sympathy using the authentic words of Prophet Muhammad (PBUH) to soothe their hearts.",
            actionDescriptionUrdu = "لواحقین سے ملاقات کے وقت انہیں تسلی دیتے ہوئے رسول اللہ صلی اللہ علیہ وسلم کے سکھائے ہوئے یہ مبارک اور دلاسے بھرے الفاظ کہیں۔",
            arabicText = "إِنَّ لِلَّهِ مَا أَخَذَ وَلَهُ مَا أَعْطَى وَكُلُّ شَيْءٍ عِنْدَهُ بِأَجَلٍ مُسَمًّى فَلْتَصْبِرْ وَلْتَحْتَسِبْ",
            transliteration = "Inna lillahi ma akhadha wa lahu ma a'ta, wa kullu shay'in 'indahu bi-ajalin musamma. Faltasbir wal-tahtasib.",
            translationUrdu = "بلاشبہ اللہ ہی کا ہے جو اس نے لے لیا اور اسی کا ہے جو اس نے دیا، اور ہر چیز اس کے ہاں ایک وقت مقرر تک ہے۔ پس صبر کیجیے اور اللہ سے ثواب کی امید رکھیے۔",
            translationEnglish = "Verily, to Allah belongs what He has taken, and to Him belongs what He has given. For everything, He has set a predetermined time. So be patient and seek reward from Allah.",
            benefit = "This beautiful statement frames death as returning a borrowed gift to its true owner, providing direct emotional and mental relief."
        ),
        TaziyatStep(
            id = 2,
            titleEnglish = "Consolation Supplication for the Family",
            titleUrdu = "صبر اور دلاسے کی دعا",
            actionDescription = "Supplicate for the grieving relatives, asking Allah to multiply their patience, grant them ease, and forgive their loved one.",
            actionDescriptionUrdu = "لواحقین کے لیے دعا کریں کہ اللہ ان کے اجر کو عظیم کرے، انہیں صبر جمیل عطا فرمائے اور ان کے مرحوم کی مغفرت فرمائے۔",
            arabicText = "أَعْظَمَ اللَّهُ أَجْرَكَ وَأَحْسَنَ عَزَاءَكَ وَغَفَرَ لِمَيِّتِكَ",
            transliteration = "A'zama Allahu ajraka wa ahsana 'aza'aka wa ghafara limayyitika.",
            translationUrdu = "اللہ تعالیٰ آپ کے اجر کو بڑھائے، آپ کو بہترین صبر اور دلاسہ عطا فرمائے اور آپ کے مرحوم کی مغفرت فرمائے۔",
            translationEnglish = "May Allah increase your reward, grant you the best consolation, and forgive your deceased.",
            benefit = "Visiting the bereaved and making this dua builds brotherhood and helps them feel supported in their hour of intense trial."
        ),
        TaziyatStep(
            id = 3,
            titleEnglish = "Quranic Guidance on Patience (Sabr)",
            titleUrdu = "صبر کی قرآنی تعلیم",
            actionDescription = "Recall and recite the foundational verse of patience to remind oneself and others of our true purpose and return.",
            actionDescriptionUrdu = "صبر کی قرآنی آیات تلاوت کریں تاکہ لواحقین کو یہ احساس دلایا جا سکے کہ ہم سب کا حقیقی ٹھکانہ اور واپسی اللہ کی طرف ہے۔",
            arabicText = "الَّذِينَ إِذَا أَصَابَتْهُمْ مُصِيبَةٌ قَالُوا إِنَّا لِلَّهِ وَإِنَّا إِلَيْهِ رَاجِعُونَ",
            transliteration = "Alladhina idha asabathum musibatun qalu inna lillahi wa inna ilayhi raji'un.",
            translationUrdu = "جن پر کوئی مصیبت پڑتی ہے تو کہتے ہیں: ہم تو اللہ ہی کے ہیں اور ہم اسی کی طرف لوٹ کر جانے والے ہیں۔",
            translationEnglish = "Who, when disaster strikes them, say, 'Indeed we belong to Allah, and indeed to Him we will return.'",
            benefit = "Affirming 'Inna Lillahi' shifts focus from a temporal earthly separation to the eternal reunion in the hereafter."
        ),
        TaziyatStep(
            id = 4,
            titleEnglish = "Sunnah Etiquettes of Visiting the Bereaved",
            titleUrdu = "تعزیت کے مسنون آداب",
            actionDescription = "Observe these crucial Sunnah guidelines during condolences to ensure respect, empathy, and comfort for the mourning family.",
            actionDescriptionUrdu = "تعزیت اور غمخواری کے دوران ان اہم مسنون آداب کا خاص خیال رکھیں:",
            arabicText = "صَنَعَ لأَهْلِ جَعْفَرٍ طَعَامًا فَإِنَّهُ قَدْ أَتَاهُمْ أَمْرٌ شَغَلَهُمْ",
            transliteration = "Sana'a li-ahli Ja'farin ta'aman, fa'innahu qad atahum amrun shaghalahum.",
            translationUrdu = "جعفر (طیار) کے گھر والوں کے لیے کھانا تیار کرو کیونکہ ان پر ایسا معاملہ آ پڑا ہے جس نے انہیں مشغول کر دیا ہے۔",
            translationEnglish = "Prepare food for the family of Ja'far, for there has come to them that which occupies them.",
            benefit = "Condolences should be done within 3 days. Friends and neighbors should prepare food for the grieving family. Keep conversations calm and respectful, avoiding questions that aggravate grief."
        )
    )
}

