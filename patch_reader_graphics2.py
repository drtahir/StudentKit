import re

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'r') as f:
    c = f.read()

# Instead of modifying the Box, we can just insert the Image inside the Box, before the Canvas.
# It starts like this:
#        ) {
#            // Maximized full-length viewport with high-resolution vector scaling & elegant framing
#            Box(
#                modifier = Modifier
#                    .fillMaxSize()
#                    .padding(2.dp)
#                    .graphicsLayer {

find_str = '''        ) {
            // Maximized full-length viewport with high-resolution vector scaling & elegant framing
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
                    .graphicsLayer {'''

replace_str = '''        ) {
            // Maximized full-length viewport with high-resolution vector scaling & elegant framing
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
                    .graphicsLayer {'''

# Wait, if I insert the Image, it will be scaled by the user's pinch-zoom.
# If the user pinch-zooms, the background frame shouldn't zoom, or should it?
# Actually, the frame zooming with the text is fine and more immersive.
# So I'll insert it inside the scaling Box, instead of the Canvas.

find_str2 = '''            Box(
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
                }'''

replace_str2 = '''            Box(
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
                // Indo-Pak ornate floral page frame
                Image(
                    painter = painterResource(id = R.drawable.quran_floral_border),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )'''

c = c.replace(find_str2, replace_str2)

# Now, we also want to modify how the verses are rendered.
# We want to render a Surah header and Bismillah if verseNumber == 1.
# First, let's remove the Bismillah text from the verse if it's there.

verse_block_find = '''                            items(versesForPage) { verse ->
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
                                            .padding(14.dp)
                                    ) {
                                        // Arabic Text
                                        Text(
                                            text = verse.textArabic + " ﴿${toArabicNumerals(verse.verseNumber)}﴾",'''

verse_block_replace = '''                            items(versesForPage) { verse ->
                                val isActive = activeVerseId == verse.id
                                
                                // Surah Header and Bismillah for Verse 1
                                if (verse.verseNumber == 1) {
                                    val surahMeta = getSurahList().find { it.number == verse.surahNumber } ?: surah
                                    
                                    // Surah Header Banner
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 24.dp, vertical = 8.dp)
                                            .height(60.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.quran_surah_header_banner),
                                            contentDescription = null,
                                            contentScale = ContentScale.FillBounds,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Ayahs: ${surahMeta.numberOfAyahs}",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black
                                            )
                                            Text(
                                                text = "سُورَةُ ${surahMeta.arabicName}",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily(Font(R.font.me_quran)),
                                                color = Color.Black
                                            )
                                            Text(
                                                text = surahMeta.revelationType.replaceFirstChar { it.uppercase() },
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                    
                                    // Bismillah Banner (except Surah 9)
                                    if (verse.surahNumber != 9) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 32.dp, vertical = 4.dp)
                                                .height(45.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.quran_bismillah_banner),
                                                contentDescription = null,
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                    }
                                }
                                
                                // Clean up Bismillah from Verse 1 text (it is already shown in the banner)
                                val bismillahPrefix1 = "بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ "
                                val bismillahPrefix2 = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ "
                                var cleanArabicText = verse.textArabic
                                if (verse.verseNumber == 1 && verse.surahNumber != 1 && verse.surahNumber != 9) {
                                    if (cleanArabicText.startsWith(bismillahPrefix1)) {
                                        cleanArabicText = cleanArabicText.removePrefix(bismillahPrefix1)
                                    } else if (cleanArabicText.startsWith(bismillahPrefix2)) {
                                        cleanArabicText = cleanArabicText.removePrefix(bismillahPrefix2)
                                    } else {
                                        // Attempt to strip first 4 words heuristically if it looks like Bismillah
                                        val words = cleanArabicText.split(" ")
                                        if (words.size > 4 && cleanArabicText.contains("رَّحِیمِ") || cleanArabicText.contains("رَّحِيمِ")) {
                                            cleanArabicText = words.drop(4).joinToString(" ")
                                        }
                                    }
                                }
                                
                                // Display Verse without card borders for seamless Indo-Pak style
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp, vertical = 2.dp)
                                        .background(if (isActive) decorationColor.copy(alpha = 0.15f) else Color.Transparent)
                                        .clickable { playVerse(verse) }
                                        .padding(8.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        // Arabic Text
                                        Text(
                                            text = cleanArabicText + " ﴿${toArabicNumerals(verse.verseNumber)}﴾",'''

c = c.replace(verse_block_find, verse_block_replace)

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'w') as f:
    f.write(c)

