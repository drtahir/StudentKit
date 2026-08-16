package com.drtahir.studentkit.ui.screens

data class SurahMetadata(
    val number: Int,
    val englishName: String,
    val englishNameTranslation: String,
    val arabicName: String,
    val numberOfAyahs: Int,
    val revelationType: String,
    val startPage: Int
)

data class JuzMetadata(
    val number: Int,
    val englishName: String,
    val arabicName: String,
    val startPage: Int
)

fun getSurahList(): List<SurahMetadata> {
    return listOf(
        SurahMetadata(1, "Al-Fatihah", "The Opening", "الفاتحة", 7, "meccan", 1),
        SurahMetadata(2, "Al-Baqarah", "The Cow", "البقرة", 286, "medinan", 2),
        SurahMetadata(3, "Ali 'Imran", "Family of Imran", "آل عمران", 200, "medinan", 50),
        SurahMetadata(4, "An-Nisa", "The Women", "النساء", 176, "medinan", 77),
        SurahMetadata(5, "Al-Ma'idah", "The Table Spread", "المائدة", 120, "medinan", 106),
        SurahMetadata(6, "Al-An'am", "The Cattle", "الأنعام", 165, "meccan", 128),
        SurahMetadata(7, "Al-A'raf", "The Heights", "الأعراف", 206, "meccan", 151),
        SurahMetadata(8, "Al-Anfal", "The Spoils of War", "الأنفال", 75, "medinan", 177),
        SurahMetadata(9, "At-Tawbah", "The Repentance", "التوبة", 129, "medinan", 187),
        SurahMetadata(10, "Yunus", "Jonah", "يونس", 109, "meccan", 208),
        SurahMetadata(11, "Hud", "Hud", "هود", 123, "meccan", 221),
        SurahMetadata(12, "Yusuf", "Joseph", "يوسف", 111, "meccan", 235),
        SurahMetadata(13, "Ar-Ra'd", "The Thunder", "الرعد", 43, "medinan", 249),
        SurahMetadata(14, "Ibrahim", "Abraham", "إبراهيم", 52, "meccan", 255),
        SurahMetadata(15, "Al-Hijr", "The Rocky Tract", "الحجر", 99, "meccan", 262),
        SurahMetadata(16, "An-Nahl", "The Bee", "النحل", 128, "meccan", 267),
        SurahMetadata(17, "Al-Isra", "The Night Journey", "الإسراء", 111, "meccan", 282),
        SurahMetadata(18, "Al-Kahf", "The Cave", "الكهف", 110, "meccan", 293),
        SurahMetadata(19, "Maryam", "Mary", "مريم", 98, "meccan", 305),
        SurahMetadata(20, "Ta-Ha", "Ta-Ha", "طه", 135, "meccan", 312),
        SurahMetadata(21, "Al-Anbiya", "The Prophets", "الأنبياء", 112, "meccan", 322),
        SurahMetadata(22, "Al-Hajj", "The Pilgrimage", "الحج", 78, "medinan", 332),
        SurahMetadata(23, "Al-Mu'minun", "The Believers", "المؤمنون", 118, "meccan", 342),
        SurahMetadata(24, "An-Nur", "The Light", "النور", 64, "medinan", 350),
        SurahMetadata(25, "Al-Furqan", "The Criterion", "الفرقان", 77, "meccan", 359),
        SurahMetadata(26, "Ash-Shu'ara", "The Poets", "الشعراء", 227, "meccan", 367),
        SurahMetadata(27, "An-Naml", "The Ant", "النمل", 93, "meccan", 377),
        SurahMetadata(28, "Al-Qasas", "The Stories", "القصص", 88, "meccan", 385),
        SurahMetadata(29, "Al-Ankabut", "The Spider", "العنكبوت", 69, "meccan", 396),
        SurahMetadata(30, "Ar-Rum", "The Romans", "الروم", 60, "meccan", 404),
        SurahMetadata(31, "Luqman", "Luqman", "لقمان", 34, "meccan", 411),
        SurahMetadata(32, "As-Sajdah", "The Prostration", "السجدة", 30, "meccan", 415),
        SurahMetadata(33, "Al-Ahzab", "The Combined Forces", "الأحزاب", 73, "medinan", 418),
        SurahMetadata(34, "Saba", "Sheba", "سبأ", 54, "meccan", 428),
        SurahMetadata(35, "Fatir", "Originator", "فاطر", 45, "meccan", 434),
        SurahMetadata(36, "Ya-Sin", "Ya-Sin", "يس", 83, "meccan", 440),
        SurahMetadata(37, "As-Saffat", "Those who set the Ranks", "الصافات", 182, "meccan", 446),
        SurahMetadata(38, "Sad", "The Letter Sad", "ص", 88, "meccan", 453),
        SurahMetadata(39, "Az-Zumar", "The Troops", "الزمر", 75, "meccan", 458),
        SurahMetadata(40, "Ghafir", "The Forgiver", "غافر", 85, "meccan", 467),
        SurahMetadata(41, "Fussilat", "Explained in Detail", "فصلت", 54, "meccan", 477),
        SurahMetadata(42, "Ash-Shura", "The Consultation", "الشورى", 53, "meccan", 483),
        SurahMetadata(43, "Az-Zukhruf", "The Ornaments of Gold", "الزخرف", 89, "meccan", 489),
        SurahMetadata(44, "Ad-Dukhan", "The Smoke", "الدخان", 59, "meccan", 496),
        SurahMetadata(45, "Al-Jathiyah", "The Crouching", "الجاثية", 37, "meccan", 499),
        SurahMetadata(46, "Al-Ahqaf", "The Wind-Curved Sandhills", "الأحقاف", 35, "meccan", 502),
        SurahMetadata(47, "Muhammad", "Muhammad", "محمد", 38, "medinan", 507),
        SurahMetadata(48, "Al-Fath", "The Victory", "الفتح", 29, "medinan", 511),
        SurahMetadata(49, "Al-Hujurat", "The Dwellings", "الحجرات", 18, "medinan", 515),
        SurahMetadata(50, "Qaf", "The Letter Qaf", "ق", 45, "meccan", 518),
        SurahMetadata(51, "Adh-Dhariyat", "The Winnowing Winds", "الذاريات", 60, "meccan", 520),
        SurahMetadata(52, "At-Tur", "The Mount", "الطور", 49, "meccan", 523),
        SurahMetadata(53, "An-Najm", "The Star", "النجم", 62, "meccan", 526),
        SurahMetadata(54, "Al-Qamar", "The Moon", "القمر", 55, "meccan", 528),
        SurahMetadata(55, "Ar-Rahman", "The Beneficent", "الرحمن", 78, "medinan", 531),
        SurahMetadata(56, "Al-Waqi'ah", "The Inevitable", "الواقعة", 96, "meccan", 534),
        SurahMetadata(57, "Al-Hadid", "The Iron", "الحديد", 29, "medinan", 537),
        SurahMetadata(58, "Al-Mujadilah", "The Pleading Woman", "المجادلة", 22, "medinan", 542),
        SurahMetadata(59, "Al-Hashr", "The Exile", "الحشر", 24, "medinan", 545),
        SurahMetadata(60, "Al-Mumtahanah", "She that is to be examined", "الممتحنة", 13, "medinan", 549),
        SurahMetadata(61, "As-Saff", "The Ranks", "الصف", 14, "medinan", 551),
        SurahMetadata(62, "Al-Jumu'ah", "The Congregation", "الجمعة", 11, "medinan", 553),
        SurahMetadata(63, "Al-Munafiqun", "The Hypocrites", "المنافقون", 11, "medinan", 554),
        SurahMetadata(64, "At-Taghabun", "The Mutual Disillusion", "التغابن", 18, "medinan", 556),
        SurahMetadata(65, "At-Talaq", "The Divorce", "الطلاق", 12, "medinan", 558),
        SurahMetadata(66, "At-Tahrim", "The Prohibition", "التحريم", 12, "medinan", 560),
        SurahMetadata(67, "Al-Mulk", "The Sovereignty", "الملك", 30, "meccan", 562),
        SurahMetadata(68, "Al-Qalam", "The Pen", "القلم", 52, "meccan", 564),
        SurahMetadata(69, "Al-Haqqah", "The Reality", "الحاقة", 52, "meccan", 566),
        SurahMetadata(70, "Al-Ma'arij", "The Ascending Stairways", "المعارج", 44, "meccan", 568),
        SurahMetadata(71, "Nuh", "Noah", "نوح", 28, "meccan", 570),
        SurahMetadata(72, "Al-Jinn", "The Jinn", "الجن", 28, "meccan", 572),
        SurahMetadata(73, "Al-Muzzammil", "The Enshrouded One", "المزمل", 20, "meccan", 574),
        SurahMetadata(74, "Al-Muddaththir", "The Cloaked One", "المدثر", 56, "meccan", 575),
        SurahMetadata(75, "Al-Qiyamah", "The Resurrection", "القيامة", 40, "meccan", 577),
        SurahMetadata(76, "Al-Insan", "The Man", "الإنسان", 31, "medinan", 578),
        SurahMetadata(77, "Al-Mursalat", "Those Sent Forth", "المرسلات", 50, "meccan", 580),
        SurahMetadata(78, "An-Naba", "The Great News", "النبأ", 40, "meccan", 582),
        SurahMetadata(79, "An-Nazi'at", "Those who Drag Forth", "النازعات", 46, "meccan", 583),
        SurahMetadata(80, "Abasa", "He Frowned", "عبس", 42, "meccan", 585),
        SurahMetadata(81, "At-Takwir", "The Overthrowing", "التكوير", 29, "meccan", 586),
        SurahMetadata(82, "Al-Infitar", "The Cleaving", "الانفطار", 19, "meccan", 587),
        SurahMetadata(83, "Al-Mutaffifin", "The Defrauders", "المطففين", 36, "meccan", 587),
        SurahMetadata(84, "Al-Inshiqaq", "The Sundering", "الانشقاق", 25, "meccan", 589),
        SurahMetadata(85, "Al-Buruj", "The Mansions of the Stars", "البروج", 22, "meccan", 590),
        SurahMetadata(86, "At-Tariq", "The Nightcomer", "الطارق", 17, "meccan", 591),
        SurahMetadata(87, "Al-A'la", "The Most High", "الأعلى", 19, "meccan", 591),
        SurahMetadata(88, "Al-Ghashiyah", "The Overwhelming", "الغاشية", 26, "meccan", 592),
        SurahMetadata(89, "Al-Fajr", "The Dawn", "الفجر", 30, "meccan", 593),
        SurahMetadata(90, "Al-Balad", "The City", "البلد", 20, "meccan", 594),
        SurahMetadata(91, "Ash-Shams", "The Sun", "الشمس", 15, "meccan", 595),
        SurahMetadata(92, "Al-Lail", "The Night", "الليل", 21, "meccan", 595),
        SurahMetadata(93, "Ad-Duha", "The Morning Hours", "الضحى", 11, "meccan", 596),
        SurahMetadata(94, "Ash-Sharh", "The Relief", "الشرح", 8, "meccan", 596),
        SurahMetadata(95, "At-Tin", "The Fig", "التين", 8, "meccan", 597),
        SurahMetadata(96, "Al-Alaq", "The Clot", "العلق", 19, "meccan", 597),
        SurahMetadata(97, "Al-Qadr", "The Power", "القدر", 5, "meccan", 598),
        SurahMetadata(98, "Al-Bayyinah", "The Clear Proof", "البينة", 8, "medinan", 598),
        SurahMetadata(99, "Az-Zalzalah", "The Earthquake", "الزلزلة", 8, "medinan", 599),
        SurahMetadata(100, "Al-Adiyat", "The Courser", "العاديات", 11, "meccan", 599),
        SurahMetadata(101, "Al-Qari'ah", "The Calamity", "القارعة", 11, "meccan", 600),
        SurahMetadata(102, "At-Takathur", "The Rivalry in World Increase", "التكاثر", 8, "meccan", 600),
        SurahMetadata(103, "Al-Asr", "The Declining Day", "العصر", 3, "meccan", 601),
        SurahMetadata(104, "Al-Humazah", "The Traducer", "الهمزة", 9, "meccan", 601),
        SurahMetadata(105, "Al-Fil", "The Elephant", "الفيل", 5, "meccan", 601),
        SurahMetadata(106, "Quraish", "Quraish", "قريش", 4, "meccan", 602),
        SurahMetadata(107, "Al-Ma'un", "The Small Kindnesses", "الماعون", 7, "meccan", 602),
        SurahMetadata(108, "Al-Kauthar", "The Abundance", "الكوثر", 3, "meccan", 602),
        SurahMetadata(109, "Al-Kafirun", "The Disbelievers", "الكافرون", 6, "meccan", 603),
        SurahMetadata(110, "An-Nasr", "The Divine Support", "النصر", 3, "medinan", 603),
        SurahMetadata(111, "Al-Masad", "The Palm Fiber", "المسد", 5, "meccan", 603),
        SurahMetadata(112, "Al-Ikhlas", "The Sincerity", "الإخلاص", 4, "meccan", 604),
        SurahMetadata(113, "Al-Falaq", "The Daybreak", "الفلق", 5, "meccan", 604),
        SurahMetadata(114, "An-Nas", "Mankind", "الناس", 6, "meccan", 604)
    )
}

fun getJuzList(): List<JuzMetadata> {
    return listOf(
        JuzMetadata(1, "Alif Lam Meem", "آلم", 1),
        JuzMetadata(2, "Sayaqool", "سيقول", 22),
        JuzMetadata(3, "Tilkal Rusul", "تلك الرسل", 42),
        JuzMetadata(4, "Lan Tanaloo", "لن تنالوا", 62),
        JuzMetadata(5, "Wal Muhsanat", "والمحصنات", 82),
        JuzMetadata(6, "La Yuhibbullah", "لا يحب الله", 102),
        JuzMetadata(7, "Wa Iza Sami'oo", "وإذا سمعوا", 121),
        JuzMetadata(8, "Wa Lau Annana", "ولو أننا", 142),
        JuzMetadata(9, "Qal Al-Mala'u", "قال الملأ", 162),
        JuzMetadata(10, "Wa'lamoo", "واعلموا", 182),
        JuzMetadata(11, "Ya'tazirun", "يعتذرون", 201),
        JuzMetadata(12, "Wa Ma Min Dabbah", "وما من دابة", 222),
        JuzMetadata(13, "Wa Ma Ubarri'u", "وما أبرئ", 242),
        JuzMetadata(14, "Rubama", "ربما", 262),
        JuzMetadata(15, "Subhan Alladhi", "سبحان الذي", 282),
        JuzMetadata(16, "Qala Alam", "قال ألم", 302),
        JuzMetadata(17, "Aqtaraba", "اقترب", 322),
        JuzMetadata(18, "Qad Aflaha", "قد أفلح", 342),
        JuzMetadata(19, "Wa Qalalladhina", "وقال الذين", 362),
        JuzMetadata(20, "Aman Khalaqa", "أمن خلق", 382),
        JuzMetadata(21, "Utlu Ma Oohiya", "اتل ما أوحي", 402),
        JuzMetadata(22, "Wa Man Yaqnut", "ومن يقنت", 422),
        JuzMetadata(23, "Wa Maliya", "ومالي", 442),
        JuzMetadata(24, "Faman Azlamu", "فمن أظلم", 462),
        JuzMetadata(25, "Ilayhi Yuraddu", "إليه يرد", 482),
        JuzMetadata(26, "Ha Meem", "حم", 502),
        JuzMetadata(27, "Qala Fama Khatbukum", "قال فما خطبكم", 522),
        JuzMetadata(28, "Qad Sami'allahu", "قد سمع الله", 542),
        JuzMetadata(29, "Tabarakalladhi", "تبارك الذي", 562),
        JuzMetadata(30, "Amma", "عم", 582)
    )
}

fun getPageForSurah(surahNum: Int): Int {
    return getSurahList().firstOrNull { it.number == surahNum }?.startPage ?: 1
}

fun getSurahForPage(pageNum: Int): Int {
    val surahs = getSurahList().sortedByDescending { it.startPage }
    val surah = surahs.firstOrNull { pageNum >= it.startPage }
    return surah?.number ?: 1
}

fun toArabicNumerals(num: Int): String {
    val digits = num.toString()
    val builder = StringBuilder()
    for (char in digits) {
        val arabicChar = when (char) {
            '0' -> '٠'
            '1' -> '١'
            '2' -> '٢'
            '3' -> '٣'
            '4' -> '٤'
            '5' -> '٥'
            '6' -> '٦'
            '7' -> '٧'
            '8' -> '٨'
            '9' -> '٩'
            else -> char
        }
        builder.append(arabicChar)
    }
    return builder.toString()
}

fun getJuzNameForPage(pageNum: Int): String {
    val juzs = getJuzList().sortedByDescending { it.startPage }
    val juz = juzs.firstOrNull { pageNum >= it.startPage }
    val num = juz?.number ?: 1
    return "الجزء ${toArabicNumerals(num)}"
}
