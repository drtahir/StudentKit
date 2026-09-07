with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
in_row = False
for line in lines:
    if 'listOf("Beige", "Green", "White", "Dark", "Gold", "Multi").forEach { th ->' in line:
        line = line.replace('listOf("Beige", "Green", "White", "Dark", "Gold", "Multi").forEach { th ->', 
                            'listOf("Beige", "Green", "White", "Dark", "Gold", "Multi").chunked(3).forEach { rowThemes ->\n                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)) {\n                            rowThemes.forEach { th ->')
    
    # We need to add the closing bracket for the inner row. This is tricky.
    
    new_lines.append(line)

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'w') as f:
    f.writelines(new_lines)
