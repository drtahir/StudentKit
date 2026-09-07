import re

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'r') as f:
    c = f.read()

imports_to_add = '''import androidx.compose.ui.text.font.Font
import androidx.compose.ui.layout.ContentScale
import com.drtahir.studentkit.R
import androidx.compose.ui.res.painterResource
'''

if 'import androidx.compose.ui.text.font.Font\n' not in c:
    c = c.replace('import androidx.compose.ui.text.font.FontFamily', imports_to_add + 'import androidx.compose.ui.text.font.FontFamily')

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'w') as f:
    f.write(c)
