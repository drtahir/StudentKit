import re

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'r') as f:
    c = f.read()

find_str = '''                QuranPageReader(
                    viewModel = viewModel,
                    surah = selectedSurah!!,
                    onBack = { selectedSurah = null },'''

replace_str = '''                QuranPageReader(
                    viewModel = viewModel,
                    surah = selectedSurah!!,
                    initialPageNum = readerModePage ?: 0,
                    targetAyahKey = selectedTargetAyahKey,
                    onBack = { 
                        selectedSurah = null 
                        readerModePage = null
                        selectedTargetAyahKey = null
                    },'''

c = c.replace(find_str, replace_str)

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'w') as f:
    f.write(c)

