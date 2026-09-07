import re

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'r') as f:
    c = f.read()

# Modify QuranPageReader signature
sig_find = '''fun QuranPageReader(
    viewModel: StudentKitViewModel,
    surah: SurahMetadata,
    initialPageNum: Int = 0,
    onBack: () -> Unit,
    arabicFontSize: Float,'''

sig_replace = '''fun QuranPageReader(
    viewModel: StudentKitViewModel,
    surah: SurahMetadata,
    initialPageNum: Int = 0,
    targetAyahKey: String? = null,
    onBack: () -> Unit,
    arabicFontSize: Float,'''

c = c.replace(sig_find, sig_replace)

# Modify the auto-scroll logic inside QuranPageReader
auto_scroll_find = '''    // Auto-scroll and verse highlighting states
    var activeVerseId by remember { mutableStateOf<String?>(null) }
    var autoPlayNextPage by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()'''

auto_scroll_replace = '''    // Auto-scroll and verse highlighting states
    var activeVerseId by remember { mutableStateOf<String?>(null) }
    var autoPlayNextPage by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    
    var hasScrolledToTarget by remember(targetAyahKey) { mutableStateOf(false) }

    LaunchedEffect(versesForPage, targetAyahKey) {
        if (targetAyahKey != null && !hasScrolledToTarget && versesForPage.isNotEmpty()) {
            val index = versesForPage.indexOfFirst { "${it.surahNumber}:${it.verseNumber}" == targetAyahKey }
            if (index != -1) {
                // Short delay to ensure layout is ready
                kotlinx.coroutines.delay(200)
                lazyListState.animateScrollToItem(index)
                activeVerseId = targetAyahKey // Highlight it
                hasScrolledToTarget = true
            }
        }
    }'''

c = c.replace(auto_scroll_find, auto_scroll_replace)

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'w') as f:
    f.write(c)

