import re

with open('app/src/main/java/com/drtahir/studentkit/ui/screens/QuranMajeedScreen.kt', 'r') as f:
    c = f.read()

# 1. Add background graphic to the main reader Box
# The reader box starts at:
#         Box(
#            modifier = Modifier
#                .fillMaxSize()
#                .background(bgColor)
#                .padding(paddingValues)

box_pattern = '''        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .padding(paddingValues)'''

box_replacement = '''        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .padding(paddingValues)
        ) {
            // Draw ornate floral background graphic
            Image(
                painter = painterResource(id = R.drawable.quran_floral_border),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            
            Box(
                modifier = Modifier.fillMaxSize()'''

c = c.replace(box_pattern, box_replacement)

# Oh wait, the pointerInput modifiers are chained to that Box.
# Let's replace the whole block more carefully.

