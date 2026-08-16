package com.drtahir.studentkit.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object IslamicCalendarUtils {

    val HIJRI_MONTHS_EN = arrayOf(
        "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani",
        "Jumada al-Awwal", "Jumada al-Thani", "Rajab", "Sha'ban",
        "Ramadan", "Shawwal", "Dhul-Qi'dah", "Dhul-Hijjah"
    )

    val HIJRI_MONTHS_AR = arrayOf(
        "مُحَرَّم", "صَفَر", "رَبِيع الأَوَّل", "رَبِيع الثَّانِي",
        "جُمَادَى الأُولَى", "جُمَادَى الآخِرَة", "رَجَب", "شَعْبَان",
        "رَمَضَان", "شَوَّال", "ذُو القَعْدَة", "ذُو الحِجَّة"
    )

    data class HijriDateResult(
        val day: Int,
        val month: Int, // 1 to 12
        val year: Int,  // e.g. 1448
        val monthNameEn: String,
        val monthNameAr: String,
        val formattedEn: String,
        val formattedAr: String
    )

    data class IslamicEvent(
        val title: String,
        val titleAr: String,
        val hijriDay: Int,
        val hijriMonth: Int,
        val description: String,
        val tag: String
    )

    val MAJOR_EVENTS = listOf(
        IslamicEvent("Islamic New Year", "رأس السنة الهجرية", 1, 1, "First day of Muharram (1448 AH)", "Holiday"),
        IslamicEvent("Day of Ashura", "يوم عاشوراء", 10, 1, "10th Muharram fasting & remembrance", "Sacred Day"),
        IslamicEvent("Mawlid an-Nabi", "مولد النبي", 12, 3, "12th Rabi' al-Awwal - Birth of Prophet Muhammad (ﷺ)", "Celebration"),
        IslamicEvent("Isra & Mi'raj", "الإسراء والمعراج", 27, 7, "27th Rajab - The Night Journey", "Spiritual"),
        IslamicEvent("Shab-e-Barat", "ليلة النصف من شعبان", 15, 8, "15th Sha'ban - Night of Forgiveness", "Spiritual"),
        IslamicEvent("First Day of Ramadan", "أول أيام رمضان", 1, 9, "Beginning of Holy Month of Fasting", "Fasting"),
        IslamicEvent("Laylat al-Qadr", "ليلة القدر", 27, 9, "27th Ramadan - Night of Power", "Sacred Night"),
        IslamicEvent("Eid-ul-Fitr", "عيد الفطر المبارك", 1, 10, "1st Shawwal - Islamic Feast of Fast-Breaking", "Major Festival"),
        IslamicEvent("Start of Hajj Season", "بداية موسم الحج", 1, 12, "1st Dhul-Hijjah - Hajj pilgrimage month", "Pilgrimage"),
        IslamicEvent("Day of Arafah", "يوم عرفة", 9, 12, "9th Dhul-Hijjah - Peak day of Hajj at Mount Arafat", "Sacred Day"),
        IslamicEvent("Eid-ul-Adha", "عيد الأضحى المبارك", 10, 12, "10th Dhul-Hijjah - Feast of Sacrifice", "Major Festival")
    )

    fun getHijriDate(calendar: Calendar = Calendar.getInstance(), dayOffset: Int = 0): HijriDateResult {
        val calCopy = calendar.clone() as Calendar
        if (dayOffset != 0) {
            calCopy.add(Calendar.DAY_OF_MONTH, dayOffset)
        }

        // Try android.icu.util.IslamicCalendar on Android API 24+ for standard ICU Islamic conversion
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            try {
                val icuCal = android.icu.util.IslamicCalendar()
                icuCal.time = calCopy.time
                val hDay = icuCal.get(android.icu.util.IslamicCalendar.DAY_OF_MONTH)
                val hMonth = icuCal.get(android.icu.util.IslamicCalendar.MONTH) + 1 // ICU months are 0-based
                val hYear = icuCal.get(android.icu.util.IslamicCalendar.YEAR)

                val validMonth = hMonth.coerceIn(1, 12)
                val mEn = HIJRI_MONTHS_EN[validMonth - 1]
                val mAr = HIJRI_MONTHS_AR[validMonth - 1]

                return HijriDateResult(
                    day = hDay,
                    month = validMonth,
                    year = hYear,
                    monthNameEn = mEn,
                    monthNameAr = mAr,
                    formattedEn = "$hDay $mEn $hYear AH",
                    formattedAr = "$hDay $mAr $hYear هـ"
                )
            } catch (e: Exception) {
                // Fallback to tabular astronomical algorithm below
            }
        }

        var gy = calCopy.get(Calendar.YEAR)
        var gm = calCopy.get(Calendar.MONTH) + 1 // 1-12
        val gd = calCopy.get(Calendar.DAY_OF_MONTH)

        if (gm < 3) {
            gy -= 1
            gm += 12
        }

        val a = gy / 100
        val b = 2 - a + (a / 4)
        val jd = (365.25 * (gy + 4716)).toInt() + (30.6001 * (gm + 1)).toInt() + gd + b - 1524

        var l = jd - 1948440 + 10632
        val n = (l - 1) / 10631
        l = l - 10631 * n + 354
        val j = ((10985 - l) / 5316) * ((50 * l) / 17719) + (l / 5670) * ((43 * l) / 15238)
        l = l - ((30 - j) / 15) * ((17719 * j) / 50) - (j / 16) * ((15238 * j) / 43) + 29
        val hMonth = (24 * l) / 709
        val hDay = l - (709 * hMonth) / 24
        val hYear = 30 * n + j - 30

        val validMonth = hMonth.coerceIn(1, 12)
        val mEn = HIJRI_MONTHS_EN[validMonth - 1]
        val mAr = HIJRI_MONTHS_AR[validMonth - 1]

        return HijriDateResult(
            day = hDay,
            month = validMonth,
            year = hYear,
            monthNameEn = mEn,
            monthNameAr = mAr,
            formattedEn = "$hDay $mEn $hYear AH",
            formattedAr = "$hDay $mAr $hYear هـ"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IslamicCalendarDialog(
    viewModel: StudentKitViewModel,
    onDismiss: () -> Unit
) {
    val hijriOffset by viewModel.hijriOffset.collectAsState()
    val todayCal = remember { Calendar.getInstance() }
    val todayHijri = remember(hijriOffset) { IslamicCalendarUtils.getHijriDate(todayCal, hijriOffset) }
    val gregDateStr = remember { SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault()).format(todayCal.time) }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Today & Calibrate, 1: Events Calendar, 2: Monthly Grid

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                // Dialog Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF198754).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = Color(0xFF198754),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Islamic Hijri Calendar",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF198754)
                            )
                            Text(
                                text = "Hijri Date Calibrator & Islamic Events",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Navigation Tabs
                PrimaryTabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Today & Calibrate", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Islamic Events", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Monthly Grid", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // TAB 0: TODAY & CALIBRATION
                if (selectedTab == 0) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Big Today Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F5132))
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(18.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "TODAY'S HIJRI DATE",
                                    fontSize = 11.sp,
                                    color = Color(0xFFFFD54F),
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = todayHijri.formattedAr,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = todayHijri.formattedEn,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFFFD54F),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Event,
                                        contentDescription = null,
                                        tint = Color.White.copy(alpha = 0.8f),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Gregorian: $gregDateStr",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                }
                            }
                        }

                        // Calibration Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Tune, contentDescription = null, tint = Color(0xFF198754), modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Moon Sighting Calibration", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                                Text(
                                    text = "If local Hilal sight differs in your region, adjust by -2 to +2 days:",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    listOf(-2 to "-2 Days", -1 to "-1 Day", 0 to "Standard", 1 to "+1 Day", 2 to "+2 Days").forEach { (offset, label) ->
                                        OutlinedButton(
                                            onClick = { viewModel.setHijriOffset(offset) },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(2.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = if (hijriOffset == offset) Color(0xFF198754) else Color.Transparent,
                                                contentColor = if (hijriOffset == offset) Color.White else MaterialTheme.colorScheme.onSurface
                                            ),
                                            border = BorderStroke(
                                                1.dp,
                                                if (hijriOffset == offset) Color(0xFF198754) else MaterialTheme.colorScheme.outlineVariant
                                            )
                                        ) {
                                            Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                        }
                                    }
                                }
                            }
                        }

                        // Info Box
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF198754))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Your selected Hijri calibration offset is automatically applied across the entire app dashboard, Islamic Library, and prayer schedules.",
                                    fontSize = 11.5.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // TAB 1: ISLAMIC EVENTS
                else if (selectedTab == 1) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(IslamicCalendarUtils.MAJOR_EVENTS) { event ->
                            val isCurrentMonth = event.hijriMonth == todayHijri.month
                            val isCurrentDay = isCurrentMonth && event.hijriDay == todayHijri.day

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isCurrentDay) Color(0xFF198754).copy(alpha = 0.15f)
                                    else if (isCurrentMonth) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                                    else MaterialTheme.colorScheme.surface
                                ),
                                border = if (isCurrentDay) BorderStroke(1.5.dp, Color(0xFF198754)) else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(
                                                if (isCurrentDay) Color(0xFF198754) else Color(0xFF198754).copy(alpha = 0.12f)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "${event.hijriDay}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp,
                                                color = if (isCurrentDay) Color.White else Color(0xFF198754)
                                            )
                                            Text(
                                                text = IslamicCalendarUtils.HIJRI_MONTHS_EN[event.hijriMonth - 1].take(3),
                                                fontSize = 9.sp,
                                                color = if (isCurrentDay) Color(0xFFFFD54F) else Color.Gray
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(event.title, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                                            Text(event.titleAr, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF198754))
                                        }
                                        Text(event.description, fontSize = 11.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }

                // TAB 2: MONTHLY GRID
                else if (selectedTab == 2) {
                    var displayedMonthCal by remember { mutableStateOf((todayCal.clone() as Calendar)) }

                    val monthNameGreg = remember(displayedMonthCal) {
                        SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(displayedMonthCal.time)
                    }

                    val firstDayHijri = remember(displayedMonthCal, hijriOffset) {
                        val temp = displayedMonthCal.clone() as Calendar
                        temp.set(Calendar.DAY_OF_MONTH, 1)
                        IslamicCalendarUtils.getHijriDate(temp, hijriOffset)
                    }

                    val lastDayHijri = remember(displayedMonthCal, hijriOffset) {
                        val temp = displayedMonthCal.clone() as Calendar
                        temp.set(Calendar.DAY_OF_MONTH, temp.getActualMaximum(Calendar.DAY_OF_MONTH))
                        IslamicCalendarUtils.getHijriDate(temp, hijriOffset)
                    }

                    val daysInMonth = remember(displayedMonthCal) {
                        displayedMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        // Month Header Navigation
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                val next = displayedMonthCal.clone() as Calendar
                                next.add(Calendar.MONTH, -1)
                                displayedMonthCal = next
                            }) {
                                Icon(Icons.Default.ChevronLeft, contentDescription = "Prev Month")
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(monthNameGreg, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(
                                    text = "${firstDayHijri.monthNameEn} - ${lastDayHijri.monthNameEn} ${firstDayHijri.year} AH",
                                    fontSize = 11.sp,
                                    color = Color(0xFF198754),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            IconButton(onClick = {
                                val next = displayedMonthCal.clone() as Calendar
                                next.add(Calendar.MONTH, 1)
                                displayedMonthCal = next
                            }) {
                                Icon(Icons.Default.ChevronRight, contentDescription = "Next Month")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Weekday headers
                        Row(modifier = Modifier.fillMaxWidth()) {
                            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                                Text(
                                    text = day,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = if (day == "Fri") Color(0xFF198754) else Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Days grid
                        val firstDayOfWeek = remember(displayedMonthCal) {
                            val temp = displayedMonthCal.clone() as Calendar
                            temp.set(Calendar.DAY_OF_MONTH, 1)
                            temp.get(Calendar.DAY_OF_WEEK) - 1 // 0-based
                        }

                        val totalGridItems = firstDayOfWeek + daysInMonth

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items((0 until totalGridItems).toList()) { index ->
                                if (index < firstDayOfWeek) {
                                    Box(modifier = Modifier.size(38.dp))
                                } else {
                                    val dayNum = index - firstDayOfWeek + 1
                                    val cellCal = (displayedMonthCal.clone() as Calendar).apply { set(Calendar.DAY_OF_MONTH, dayNum) }
                                    val hijriCell = IslamicCalendarUtils.getHijriDate(cellCal, hijriOffset)

                                    val isToday = cellCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) &&
                                            cellCal.get(Calendar.DAY_OF_YEAR) == todayCal.get(Calendar.DAY_OF_YEAR)

                                    Box(
                                        modifier = Modifier
                                            .height(42.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                if (isToday) Color(0xFF198754) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "$dayNum",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = if (isToday) Color.White else MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = "${hijriCell.day}",
                                                fontSize = 9.sp,
                                                color = if (isToday) Color(0xFFFFD54F) else Color(0xFF198754),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
