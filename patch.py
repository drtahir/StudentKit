import re

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'r') as f:
    c = f.read()

# 1. Add state variables
state_vars = '''    var readerModePage by remember { mutableStateOf<Int?>(null) } // if not null, reading page-by-page
    var ayahJumpLoading by remember { mutableStateOf(false) }
    var selectedTargetAyahKey by remember { mutableStateOf<String?>(null) }'''
c = c.replace('    var readerModePage by remember { mutableStateOf<Int?>(null) } // if not null, reading page-by-page', state_vars)

# 2. Add regex parsing inside the UI block
active_tab_0_replacement = '''                        0 -> {
                            // Ayah Jump Match
                            val ayahJumpMatch = remember(searchQuery) {
                                val regex1 = Regex("^(\\\\d+)\\\\s*:\\\\s*(\\\\d+)$")
                                val regex2 = Regex("^(?:surah|sura)\\\\s+(\\\\d+)\\\\s+(?:ayah|ayat|verse)\\\\s+(\\\\d+)$", RegexOption.IGNORE_CASE)
                                regex1.find(searchQuery.trim()) ?: regex2.find(searchQuery.trim())
                            }

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
                                if (ayahJumpMatch != null) {
                                    val sNum = ayahJumpMatch.groupValues[1].toIntOrNull() ?: 1
                                    val aNum = ayahJumpMatch.groupValues[2].toIntOrNull() ?: 1
                                    val targetSurah = surahs.find { it.number == sNum }
                                    
                                    if (targetSurah != null && aNum > 0 && aNum <= targetSurah.numberOfAyahs) {
                                        item {
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        if (ayahJumpLoading) return@clickable
                                                        ayahJumpLoading = true
                                                        coroutineScope.launch(Dispatchers.IO) {
                                                            try {
                                                                val url = java.net.URL("https://api.alquran.cloud/v1/ayah/$sNum:$aNum")
                                                                val conn = url.openConnection() as java.net.HttpURLConnection
                                                                conn.requestMethod = "GET"
                                                                conn.connectTimeout = 5000
                                                                conn.readTimeout = 5000
                                                                
                                                                if (conn.responseCode == 200) {
                                                                    val reader = java.io.BufferedReader(java.io.InputStreamReader(conn.inputStream))
                                                                    val sb = StringBuilder()
                                                                    var line: String?
                                                                    while (reader.readLine().also { line = it } != null) {
                                                                        sb.append(line)
                                                                    }
                                                                    reader.close()
                                                                    val obj = org.json.JSONObject(sb.toString())
                                                                    val data = obj.getJSONObject("data")
                                                                    val pageNum = data.getInt("page")
                                                                    
                                                                    withContext(Dispatchers.Main) {
                                                                        ayahJumpLoading = false
                                                                        selectedTargetAyahKey = "$sNum:$aNum"
                                                                        readerModePage = pageNum
                                                                        selectedSurah = targetSurah
                                                                    }
                                                                } else {
                                                                    withContext(Dispatchers.Main) {
                                                                        ayahJumpLoading = false
                                                                        Toast.makeText(context, "Could not find Ayah", Toast.LENGTH_SHORT).show()
                                                                    }
                                                                }
                                                            } catch (e: Exception) {
                                                                withContext(Dispatchers.Main) {
                                                                    ayahJumpLoading = false
                                                                    Toast.makeText(context, "Network error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                                                }
                                                            }
                                                        }
                                                    },
                                                colors = CardDefaults.cardColors(containerColor = indexThemeColors.decorationColor.copy(alpha = 0.1f)),
                                                border = BorderStroke(1.dp, indexThemeColors.decorationColor)
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Column {
                                                        Text("Jump to ${targetSurah.englishName}, Ayah $aNum", fontWeight = FontWeight.Bold, color = indexThemeColors.decorationColor)
                                                        Text("Direct navigation", fontSize = 12.sp, color = indexThemeColors.txtUrduColor)
                                                    }
                                                    if (ayahJumpLoading) {
                                                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = indexThemeColors.decorationColor, strokeWidth = 2.dp)
                                                    } else {
                                                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Go", tint = indexThemeColors.decorationColor)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                items(filteredSurahs) { surah ->'''

c = c.replace('''                        0 -> {
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
                                items(filteredSurahs) { surah ->''', active_tab_0_replacement)

# 3. Add targetAyahKey to the call of QuranPageReader
c = c.replace('''                QuranPageReader(
                    viewModel = viewModel,
                    surah = selectedSurah!!,
                    initialPageNum = readerModePage ?: 0,
                    onBack = { 
                        selectedSurah = null 
                        readerModePage = null
                    },''',
'''                QuranPageReader(
                    viewModel = viewModel,
                    surah = selectedSurah!!,
                    initialPageNum = readerModePage ?: 0,
                    targetAyahKey = selectedTargetAyahKey,
                    onBack = { 
                        selectedSurah = null 
                        readerModePage = null
                        selectedTargetAyahKey = null
                    },''')

c = c.replace('''                QuranPageReader(
                    viewModel = viewModel,
                    surah = dummySurah,
                    initialPageNum = readerModePage!!,
                    onBack = { readerModePage = null },''',
'''                QuranPageReader(
                    viewModel = viewModel,
                    surah = dummySurah,
                    initialPageNum = readerModePage!!,
                    targetAyahKey = selectedTargetAyahKey,
                    onBack = { 
                        readerModePage = null 
                        selectedTargetAyahKey = null
                    },''')

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'w') as f:
    f.write(c)

