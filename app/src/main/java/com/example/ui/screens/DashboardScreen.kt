package com.drtahir.studentkit.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import com.drtahir.studentkit.data.*
import com.drtahir.studentkit.viewmodel.Screen
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.drtahir.studentkit.R
import kotlinx.coroutines.launch

data class UtilityTool(
    val id: String,
    val label: String,
    val description: String,
    val category: String,
    val icon: ImageVector,
    val color: Color,
    val badge: String?,
    val screen: Screen
)

val allToolsList = listOf(
    // 🕌 Finance
    UtilityTool("expense", "Expense Tracker", "Log & categorize daily cash outflows", "Finance", Icons.Default.TrendingDown, Color(0xFFE53935), "SQLITE", Screen.ExpenseTracker),
    UtilityTool("income", "Income Tracker", "Monitor income streams & earnings", "Finance", Icons.Default.TrendingUp, Color(0xFF43A047), "SQLITE", Screen.IncomeTracker),
    UtilityTool("bills", "Utility Bills", "Reminders and logs for utility bills", "Finance", Icons.Default.ReceiptLong, Color(0xFF1E88E5), "ALERTS", Screen.UtilityBills),
    UtilityTool("zakat", "Zakat Calculator", "Calculate wealth Zakat accurately", "Finance", Icons.Default.AccountBalance, Color(0xFF00897B), "ISLAMIC", Screen.ZakatCalculator),
    UtilityTool("committee", "BC Kommittees", "Kommittee lucky draw & cycle logs", "Finance", Icons.Default.Groups, Color(0xFF8E24AA), "DRAWS", Screen.BcCommittee),
    UtilityTool("loans", "Loan Ledger", "Track borrowings & lent payments", "Finance", Icons.Default.SwapHoriz, Color(0xFFD81B60), "TRACK", Screen.LoanTracker),
    UtilityTool("savings", "Savings Goals", "Target savings & deposit logs", "Finance", Icons.Default.Star, Color(0xFFF4511E), "SAVINGS", Screen.SavingsGoals),

    // 📄 Documents
    UtilityTool("cv", "CV Resume Builder", "Create custom print-ready A4 PDF resumes", "Documents", Icons.Default.Badge, Color(0xFF00C853), "A4 PRINT", Screen.CvBuilder),
    UtilityTool("id_scanner", "ID Card Scanner", "Scan front & back of ID on single page", "Documents", Icons.Default.ContactPage, Color(0xFF1E88E5), "SINGLE PAGE", Screen.IdCardScanner),
    UtilityTool("passport_scanner", "Passport Scanner", "Full photo passport page scan utility", "Documents", Icons.Default.AssignmentInd, Color(0xFF00ACC1), "GOVT DOC", Screen.PassportScanner),
    UtilityTool("img2pdf", "Image to PDF", "Compile images into single PDF file", "Documents", Icons.Default.PictureAsPdf, Color(0xFFE53935), "PDF CONV", Screen.ImageToPdf),
    UtilityTool("img2xls", "Image to Excel", "Convert tables to spreadsheet via OCR", "Documents", Icons.Default.TableChart, Color(0xFF2E7D32), "OCR AI", Screen.ImageToXls),
    UtilityTool("img2word", "Image to Word", "Convert images to DOCX files via OCR", "Documents", Icons.Default.Description, Color(0xFF1565C0), "DOCX CONV", Screen.ImageToWord),
    UtilityTool("scanner", "Edge Scanner", "Scan physical doc pages via camera", "Documents", Icons.Default.DocumentScanner, Color(0xFF673AB7), "HD SCAN", Screen.DocumentScanner),
    UtilityTool("pdftools", "PDF Handlers", "Compress, merge, split or lock PDFs", "Documents", Icons.Default.Compress, Color(0xFFEF6C00), "EDIT", Screen.PdfTools),
    UtilityTool("invoice", "Invoice Maker", "Create professional PDF invoices", "Documents", Icons.Default.Receipt, Color(0xFF00838F), "INVOICES", Screen.InvoiceGenerator),
    UtilityTool("stamp_sign", "Stamp & Sign", "Freehand draw signature & stamp docs", "Documents", Icons.Default.Gesture, Color(0xFF1976D2), "STAMP", Screen.SignaturePad),

    // ⚙️ Utilities
    UtilityTool("calc", "Scientific Calc", "Advance mathematical formula solver", "Utilities", Icons.Default.Calculate, Color(0xFFE91E63), "MATH ENGINE", Screen.Calculator),
    UtilityTool("converter", "Unit Converter", "Convert data, length, weight, speeds", "Utilities", Icons.Default.SwapVert, Color(0xFF00ACC1), "CONVERT", Screen.UnitConverter),
    UtilityTool("qr_gen", "QR Generator", "Generate secure colored QR codes", "Utilities", Icons.Default.QrCode, Color(0xFF3949AB), "VECTOR", Screen.QrGenerator),
    UtilityTool("qr_scan", "QR Scanner", "Scan bar codes & check web links", "Utilities", Icons.Default.QrCodeScanner, Color(0xFF00897B), "CAMERA", Screen.QrScanner),
    UtilityTool("passwords", "Password Vault", "Local encrypted credentials keeper", "Utilities", Icons.Default.Lock, Color(0xFF2E7D32), "CRYPT", Screen.PasswordManager),
    UtilityTool("img_tools", "Image Compress", "Compress, resize & optimize images", "Utilities", Icons.Default.AddPhotoAlternate, Color(0xFFC2185B), "BATCH", Screen.ImageTools),
    UtilityTool("age", "Age Calculator", "Exact age in years, months & days", "Utilities", Icons.Default.Cake, Color(0xFFE91E63), "AGE FINDER", Screen.AgeCalculator),
    UtilityTool("intruder_guard", "Intruder Guard", "Silent background selfie & siren alarm", "Utilities", Icons.Default.Security, Color(0xFFD32F2F), "SECURITY", Screen.IntruderGuard),
    UtilityTool("file_encryptor", "File Encryptor", "Hardware AES-256 GCM locker", "Utilities", Icons.Default.EnhancedEncryption, Color(0xFF1E88E5), "KEYSTORE", Screen.FileEncryptor),
    UtilityTool("hidden_locker", "Hidden Locker", "Secure sandbox file/photo vault", "Utilities", Icons.Default.FolderSpecial, Color(0xFFEC407A), "ENCRYPTEDFILE", Screen.HiddenLocker),
    UtilityTool("steganography", "Steganography", "Hide secret message in image pixels", "Utilities", Icons.Default.Image, Color(0xFF43A047), "LSB BIT", Screen.Steganography),
    UtilityTool("steganalysis", "Steganalysis", "Detect hidden data, LSB entropy & overlays", "Utilities", Icons.Default.Analytics, Color(0xFFE65100), "FORENSICS", Screen.Steganalysis),
    UtilityTool("ai_enhancer", "AI Enhancer", "Offline AI face & photo restorer", "Utilities", Icons.Default.AutoAwesome, Color(0xFF00ACC1), "REMINI", Screen.ImageEnhancer),
    UtilityTool("watermark_studio", "Watermark Studio", "Add text watermarks & photo filter matrix", "Utilities", Icons.Default.Brush, Color(0xFF7B1FA2), "STUDIO", Screen.WatermarkStudio),
    UtilityTool("bg_eraser", "Background Eraser", "AI background remover with precise brush refine", "Utilities", Icons.Default.FilterFrames, Color(0xFFE91E63), "AI SEGMENT", Screen.BackgroundEraser),
    UtilityTool("teleprompter", "Teleprompter Pro", "World #1 Camera video prompter with AI", "Utilities", Icons.Default.Videocam, Color(0xFF6366F1), "PRO STUDIO", Screen.Teleprompter),

    // 🎓 Study & Health
    UtilityTool("notes", "Lecture Notes", "Organize lecture notes & study notes", "Study", Icons.Default.Book, Color(0xFFF57C00), "OFFLINE", Screen.Notes),
    UtilityTool("timer", "Pomodoro Timer", "Focus study timer sessions & analytics", "Study", Icons.Default.Timer, Color(0xFFE64A19), "FOCUS", Screen.StudyTimer),
    UtilityTool("timetable", "Lesson Calendar", "Track school subjects & timetables", "Study", Icons.Default.Schedule, Color(0xFF1976D2), "AGENDA", Screen.Timetable),
    UtilityTool("bmi", "BMI & Health", "Water logging & fitness calculator", "Study", Icons.Default.FitnessCenter, Color(0xFF43A047), "WELLNESS", Screen.BmiCalculator),
    UtilityTool("gpa", "GPA Calculator", "Calculate Semester GPA & CGPA instantly", "Study", Icons.Default.School, Color(0xFF9C27B0), "GPA CALC", Screen.GpaCalculator),
    UtilityTool("islamic", "Islamic Library", "Read Manzil Arabic & other books in HD", "Study", Icons.Default.Book, Color(0xFF198754), "MANZIL HD", Screen.IslamicHub),

    // 🩺 Medical / Clinical
    UtilityTool("iv_calc", "IV Infusion Rate", "IV fluid flow & drop rates tracker", "Medical", Icons.Default.WaterDrop, Color(0xFF0288D1), "NURSING", Screen.IvCalculator),
    UtilityTool("dose_calc", "Drug Dosage Calc", "Standard patient body-weight dosages", "Medical", Icons.Default.MedicalServices, Color(0xFF00C853), "PHARMACY", Screen.DosageCalculator),
    UtilityTool("gfr_calc", "GFR Renal Solver", "Cockcroft-Gault kidney clearance", "Medical", Icons.Default.Science, Color(0xFFFF9100), "CLINICAL", Screen.GfrCalculator),
    UtilityTool("anatomy", "Anatomy Atlas", "Human systems, colorful study charts & quiz", "Medical", Icons.Default.AccessibilityNew, Color(0xFFE53935), "STUDY ATLAS", Screen.AnatomyAtlas),
    UtilityTool("pharmacy_exam", "Pharmacy Exam", "Pakistan Category B pharmacy assistant prep", "Medical", Icons.Default.Quiz, Color(0xFF9C27B0), "EXAM PREP", Screen.PharmacyExam),
    UtilityTool("nursing_exam", "Nursing Exam Kit", "DHA, Saudi Prometric, NCLEX-RN, HAAD, MOH prep", "Medical", Icons.Default.MedicalServices, Color(0xFF00695C), "12000+ Qs & BOOK", Screen.NursingExam),
    UtilityTool("hajj_prep", "Hajj Mission Prep", "Hajj Medical Mission NTS exam preparation", "Medical", Icons.Default.MedicalInformation, Color(0xFF009688), "NTS EXAM", Screen.HajjMedicalPrep)
)

@Composable
fun HDDesignToolButton(
    tool: UtilityTool,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .testTag("hd_tool_${tool.id}")
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, tool.color.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            tool.color.copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    )
                )
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Colored icon box
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(tool.color.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = tool.icon,
                        contentDescription = tool.label,
                        tint = tool.color,
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                // Cute technical badge
                if (tool.badge != null) {
                    Box(
                        modifier = Modifier
                            .background(tool.color.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = tool.badge,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = tool.color,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(10.dp))
            
            Text(
                text = tool.label,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            
            Spacer(modifier = Modifier.height(2.dp))
            
            Text(
                text = tool.description,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                maxLines = 2,
                lineHeight = 13.sp,
                modifier = Modifier.height(26.dp)
            )
        }
    }
}

@Composable
fun IslamicLibraryHeroButton(
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "islamic_pulse")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F5132)),
        border = BorderStroke(1.5.dp, Color(0xFFD4AF37)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF0F5132),
                            Color(0xFF198754),
                            Color(0xFF0F5132)
                        )
                    )
                )
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0f),
                        Color.White.copy(alpha = 0.08f),
                        Color.White.copy(alpha = 0f)
                    ),
                    start = androidx.compose.ui.geometry.Offset(shimmerTranslate - 300f, 0f),
                    end = androidx.compose.ui.geometry.Offset(shimmerTranslate, size.height)
                )
                drawRect(brush = brush)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFFD4AF37).copy(alpha = 0.15f), CircleShape)
                            .border(1.5.dp, Color(0xFFD4AF37), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = "Quran Icon",
                            tint = Color(0xFFD4AF37),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "Islamic Library & Books",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Color(0xFFD4AF37),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "MANZIL HD",
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F5132),
                                    maxLines = 1,
                                    softWrap = false,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Complete Protection Verses & Ruqyah",
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Open Library",
                    tint = Color(0xFFD4AF37),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun Nursing12kMcqHeroButton(
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "nursing_pulse")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(onClick = onClick)
            .testTag("nursing_12k_hero_button"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF004D40)),
        border = BorderStroke(1.5.dp, Color(0xFF00E5FF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF00363A),
                            Color(0xFF006064),
                            Color(0xFF004D40)
                        )
                    )
                )
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0f),
                        Color.White.copy(alpha = 0.12f),
                        Color.White.copy(alpha = 0f)
                    ),
                    start = Offset(shimmerTranslate - 300f, 0f),
                    end = Offset(shimmerTranslate, size.height)
                )
                drawRect(brush = brush)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFF00E5FF).copy(alpha = 0.15f), CircleShape)
                            .border(1.5.dp, Color(0xFF00E5FF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = "Nursing MCQs",
                            tint = Color(0xFF00E5FF),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "Nursing 12,000+ MCQs",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Color(0xFF00E5FF),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "NCLEX & DHA",
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00363A),
                                    maxLines = 1,
                                    softWrap = false,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "DHA, Saudi Prometric, NCLEX-RN & PNC Master",
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Open Nursing Exam Kit",
                    tint = Color(0xFF00E5FF),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun MoavineenHujjajHeroButton(
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "moavineen_pulse")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(onClick = onClick)
            .testTag("moavineen_hujjaj_hero_button"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D5C3A)),
        border = BorderStroke(1.5.dp, Color(0xFFDAA520)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF073822),
                            Color(0xFF0D5C3A),
                            Color(0xFF073822)
                        )
                    )
                )
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0f),
                        Color.White.copy(alpha = 0.15f),
                        Color.White.copy(alpha = 0f)
                    ),
                    start = Offset(shimmerTranslate - 300f, 0f),
                    end = Offset(shimmerTranslate, size.height)
                )
                drawRect(brush = brush)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFFDAA520).copy(alpha = 0.2f), CircleShape)
                            .border(1.5.dp, Color(0xFFDAA520), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mosque,
                            contentDescription = "Moavineen Hujjaj",
                            tint = Color(0xFFDAA520),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "Moavineen-e-Hujjaj Prep",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Color(0xFFDAA520),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "MORA NTS",
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF073822),
                                    maxLines = 1,
                                    softWrap = false,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "1000+ Quizzes • Supervisor & Supporting Staff",
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Open Moavineen Prep",
                    tint = Color(0xFFDAA520),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun OmniPosHeroButton(
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "omnipos_pulse")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(2900, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(onClick = onClick)
            .testTag("omnipos_hero_button"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF311B92)),
        border = BorderStroke(1.5.dp, Color(0xFFFF9800)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1A237E),
                            Color(0xFF311B92),
                            Color(0xFF4A148C)
                        )
                    )
                )
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0f),
                        Color.White.copy(alpha = 0.15f),
                        Color.White.copy(alpha = 0f)
                    ),
                    start = Offset(shimmerTranslate - 300f, 0f),
                    end = Offset(shimmerTranslate, size.height)
                )
                drawRect(brush = brush)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFFFF9800).copy(alpha = 0.18f), CircleShape)
                            .border(1.5.dp, Color(0xFFFF9800), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PointOfSale,
                            contentDescription = "OmniPOS Enterprise",
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "OmniPOS Enterprise System",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Color(0xFFFF9800),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "360° POS",
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A237E),
                                    maxLines = 1,
                                    softWrap = false,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Multi-Service POS, Stock, Invoices & Ledger",
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Open OmniPOS",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: StudentKitViewModel,
    modifier: Modifier = Modifier
) {
    val expenses by viewModel.expenses.collectAsState()
    val income by viewModel.income.collectAsState()
    val bills by viewModel.unpaidBills.collectAsState()
    val allBills by viewModel.bills.collectAsState()
    val committees by viewModel.committees.collectAsState()
    val loans by viewModel.loans.collectAsState()
    val savingsGoals by viewModel.savingsGoals.collectAsState()
    val timetable by viewModel.timetableClasses.collectAsState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // SAF Launchers for Home Screen Footer JSON Backup
    val exportJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json"),
        onResult = { uri ->
            if (uri != null) {
                viewModel.exportFinanceJsonData { jsonString ->
                    if (jsonString != null) {
                        coroutineScope.launch {
                            try {
                                context.contentResolver.openOutputStream(uri)?.use { output ->
                                    output.write(jsonString.toByteArray(Charsets.UTF_8))
                                }
                                Toast.makeText(context, "Finance JSON Export Completed!", Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Export error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Failed to build JSON export data.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )

    val importJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                coroutineScope.launch {
                    try {
                        val content = context.contentResolver.openInputStream(uri)?.use { input ->
                            input.bufferedReader().use { it.readText() }
                        }
                        if (content != null) {
                            viewModel.importFinanceJsonData(content) { success, msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(context, "Failed to read JSON content.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Import failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    )

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var showAllToolsDialog by remember { mutableStateOf(false) }

    val isDarkThemeSetting by viewModel.isDarkTheme.collectAsState()
    var showThemeMenu by remember { mutableStateOf(false) }

    val userName by viewModel.userName.collectAsState()
    val userOccupation by viewModel.userOccupation.collectAsState()

    // Welcomes, dates, Islamic dates
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val greetingTime = when {
        hour < 12 -> "Good Morning"
        hour < 17 -> "Good Afternoon"
        else -> "Good Evening"
    }
    val timeOfDayTag = when {
        hour < 12 -> "GOOD MORNING"
        hour < 17 -> "GOOD AFTERNOON"
        else -> "GOOD EVENING"
    }
    val timeOfDayIcon = when {
        hour < 12 -> Icons.Default.WbSunny
        hour < 17 -> Icons.Default.WbTwilight
        else -> Icons.Default.NightsStay
    }
    val timeOfDayIconTint = when {
        hour < 12 -> Color(0xFFFFD54F)
        hour < 17 -> Color(0xFFFFB74D)
        else -> Color(0xFFC5CAE9)
    }
    val cardGradientColors = when {
        hour < 12 -> listOf(Color(0xFF0F5132), Color(0xFF198754), Color(0xFF0D6EFD))
        hour < 17 -> listOf(Color(0xFF133B5C), Color(0xFF1E5E8C), Color(0xFF0F5132))
        else -> listOf(Color(0xFF0D1B2A), Color(0xFF1B263B), Color(0xFF0F5132))
    }
    val greeting = if (userName.isNotBlank()) {
        "$greetingTime, ${userName.trim()}!"
    } else {
        "$greetingTime!"
    }

    val df = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
    val formattedDate = df.format(Date())

    val hijriOffset by viewModel.hijriOffset.collectAsState()
    var showIslamicCalendarDialog by remember { mutableStateOf(false) }

    val hijriDateObj = remember(hijriOffset) {
        IslamicCalendarUtils.getHijriDate(Calendar.getInstance(), hijriOffset)
    }
    val hijriDate = hijriDateObj.formattedEn

    // Card totals calculations
    val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val thisMonthStr = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

    val todayExpense = expenses.filter { it.date.startsWith(todayStr) }.sumOf { it.amount }
    val totalMonthIncome = income.filter { it.date.startsWith(thisMonthStr) }.sumOf { it.amount }
    val totalMonthExpense = expenses.filter { it.date.startsWith(thisMonthStr) }.sumOf { it.amount }
    val netBalance = totalMonthIncome - totalMonthExpense

    val activeGoalsCount = savingsGoals.count { it.currentAmount < it.targetAmount }

    // Animated logo gradient sweep & scale pulse for "Hikmah"
    val infiniteTransition = rememberInfiniteTransition(label = "hikmah_header_anim")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_offset"
    )

    val logoPulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.025f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_pulse"
    )

    val brandPrimary = MaterialTheme.colorScheme.primary
    val emeraldGreen = Color(0xFF10B981)
    val goldYellow = Color(0xFFF59E0B)
    val cyanBlue = Color(0xFF0288D1)

    val hikmahAnimatedBrush = Brush.linearGradient(
        colors = listOf(
            brandPrimary,
            goldYellow,
            emeraldGreen,
            cyanBlue,
            brandPrimary
        ),
        start = Offset(shimmerOffset - 600f, 0f),
        end = Offset(shimmerOffset, 200f)
    )

    var showMoreMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            // Custom Brand Logo Emblem with Subtle Glow Ring
                            Surface(
                                shape = CircleShape,
                                shadowElevation = 4.dp,
                                border = BorderStroke(
                                    1.2.dp,
                                    Brush.linearGradient(
                                        listOf(Color(0xFFFFD54F), Color(0xFF26A69A), Color(0xFFFFE082))
                                    )
                                ),
                                modifier = Modifier
                                    .size(34.dp)
                                    .graphicsLayer(
                                        scaleX = logoPulseScale,
                                        scaleY = logoPulseScale
                                    )
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_brand_logo),
                                    contentDescription = "Hikmah Brand Logo",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // BIGGER animated logo font for "Hikmah"
                            Text(
                                text = "Hikmah",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                style = TextStyle(
                                    brush = hikmahAnimatedBrush,
                                    letterSpacing = 0.5.sp
                                ),
                                modifier = Modifier.graphicsLayer(
                                    scaleX = logoPulseScale,
                                    scaleY = logoPulseScale
                                )
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            // SMALL NORMAL font for "Omni Suite"
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Text(
                                    text = "Omni Suite",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Normal,
                                    letterSpacing = 0.4.sp,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.5.dp)
                                )
                            }
                        }

                        Text(
                            text = "Comprehensive Professional Platform",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Normal,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showAllToolsDialog = true },
                        modifier = Modifier.testTag("notes_shortcut")
                    ) {
                        Icon(Icons.Default.Notes, contentDescription = "View All Services")
                    }
                    IconButton(
                        onClick = { viewModel.navigateTo(Screen.IslamicHub) }
                    ) {
                        Icon(Icons.Default.MenuBook, contentDescription = "Islamic Library", tint = Color(0xFF198754))
                    }
                    Box {
                        IconButton(
                            onClick = { showThemeMenu = true },
                            modifier = Modifier.testTag("theme_toggle_button")
                        ) {
                            val themeIcon = when (isDarkThemeSetting) {
                                true -> Icons.Default.DarkMode
                                false -> Icons.Default.LightMode
                                null -> Icons.Default.BrightnessAuto
                            }
                            val themeTint = when (isDarkThemeSetting) {
                                true -> Color(0xFFFFD54F)
                                false -> Color(0xFFFFB300)
                                null -> MaterialTheme.colorScheme.primary
                            }
                            Icon(
                                imageVector = themeIcon,
                                contentDescription = "Change Theme",
                                tint = themeTint
                            )
                        }
                        DropdownMenu(
                            expanded = showThemeMenu,
                            onDismissRequest = { showThemeMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Light Mode") },
                                onClick = {
                                    viewModel.setDarkTheme(false)
                                    showThemeMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.LightMode,
                                        contentDescription = null,
                                        tint = Color(0xFFFFB300)
                                    )
                                },
                                trailingIcon = {
                                    if (isDarkThemeSetting == false) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Dark Mode") },
                                onClick = {
                                    viewModel.setDarkTheme(true)
                                    showThemeMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.DarkMode,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD54F)
                                    )
                                },
                                trailingIcon = {
                                    if (isDarkThemeSetting == true) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("System Default") },
                                onClick = {
                                    viewModel.setDarkTheme(null)
                                    showThemeMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.BrightnessAuto,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                trailingIcon = {
                                    if (isDarkThemeSetting == null) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            )
                        }
                    }
                    Box {
                        IconButton(
                            onClick = { showMoreMenu = true },
                            modifier = Modifier.testTag("settings_app_shortcut")
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More Options",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        DropdownMenu(
                            expanded = showMoreMenu,
                            onDismissRequest = { showMoreMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Settings & Profile") },
                                onClick = {
                                    showMoreMenu = false
                                    viewModel.navigateTo(Screen.Settings)
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Focus Study Timer") },
                                onClick = {
                                    showMoreMenu = false
                                    viewModel.navigateTo(Screen.StudyTimer)
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Timer, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("About Hikmah Omni Suite") },
                                onClick = {
                                    showMoreMenu = false
                                    viewModel.navigateTo(Screen.About)
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (showAllToolsDialog) {
            AllServicesDrawerDialog(
                viewModel = viewModel,
                onDismiss = { showAllToolsDialog = false }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header Welcome Greeting
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = cardGradientColors
                        )
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.35f),
                                Color.White.copy(alpha = 0.10f)
                            )
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp)
            ) {
                // Subtle glassmorphic background depth accents
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 30.dp, y = (-30).dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.08f))
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.BottomStart)
                        .offset(x = (-20).dp, y = 20.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.05f))
                )

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            // Avatar container with subtle ring
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White.copy(alpha = 0.20f))
                                    .border(
                                        width = 1.dp,
                                        color = Color.White.copy(alpha = 0.40f),
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (userName.isNotBlank()) {
                                    Text(
                                        text = userName.trim().take(1).uppercase(),
                                        color = Color.White,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                } else {
                                    Text(
                                        text = "N",
                                        color = Color.White,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                // Time of day tag pill
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.20f),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = timeOfDayIcon,
                                            contentDescription = null,
                                            tint = timeOfDayIconTint,
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Text(
                                            text = timeOfDayTag,
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                }

                                Text(
                                    text = if (userName.isNotBlank()) userName.trim() else "Welcome",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    maxLines = 1
                                )

                                if (userOccupation.isNotBlank()) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 2.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.WorkOutline,
                                            contentDescription = null,
                                            tint = Color.White.copy(alpha = 0.85f),
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = userOccupation,
                                            color = Color.White.copy(alpha = 0.90f),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }

                        // Glass Action Button to Edit Profile / Settings
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color.White.copy(alpha = 0.20f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.35f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .clickable { viewModel.navigateTo(Screen.Settings) }
                                .testTag("welcome_card_settings_btn")
                        ) {
                            Box(
                                modifier = Modifier.padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Profile",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(
                        color = Color.White.copy(alpha = 0.20f),
                        thickness = 1.dp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Gregorian Date Pill
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.18f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.90f),
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = formattedDate,
                                    color = Color.White,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // Hijri Date Pill
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.18f),
                            modifier = Modifier.clickable { showIslamicCalendarDialog = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD54F),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = hijriDate,
                                    color = Color.White,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Calibrate Date",
                                    tint = Color.White.copy(alpha = 0.8f),
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (showIslamicCalendarDialog) {
                IslamicCalendarDialog(
                    viewModel = viewModel,
                    onDismiss = { showIslamicCalendarDialog = false }
                )
            }

            // Quick Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Today Expense
                StatCard(
                    title = "Today's Cost",
                    value = "Rs. ${String.format("%.0f", todayExpense)}",
                    icon = Icons.Default.TrendingDown,
                    iconColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                )

                // Month Balance
                StatCard(
                    title = "Month Savings",
                    value = "Rs. ${String.format("%.0f", netBalance)}",
                    icon = if (netBalance >= 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    iconColor = if (netBalance >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1.2f)
                )

                // Active Goals
                StatCard(
                    title = "Active Goals",
                    value = "$activeGoalsCount",
                    icon = Icons.Default.Flag,
                    iconColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(0.9f)
                )
            }

            // Beautiful Animated Islamic Library CTA
            IslamicLibraryHeroButton(
                onClick = { viewModel.navigateTo(Screen.IslamicHub) }
            )

            // Beautiful Animated Nursing 12000+ MCQs Hero CTA
            Nursing12kMcqHeroButton(
                onClick = { viewModel.navigateTo(Screen.NursingExam) }
            )

            // Beautiful Animated Moavineen-e-Hujjaj Prep Hero CTA
            MoavineenHujjajHeroButton(
                onClick = { viewModel.navigateTo(Screen.MoavineenHujjajPrep) }
            )

            // Beautiful Animated OmniPOS Enterprise Suite Hero CTA
            OmniPosHeroButton(
                onClick = { viewModel.navigateTo(Screen.InvoiceGenerator) }
            )

            // Modern Search & Utilities Portal (Replaces old Quick Action Grid)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Smart Utilities Portal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("utility_search_bar"),
                    placeholder = { Text("Search 23+ real tools (e.g., Zakat, CV, OCR)...", fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                    )
                )

                // Category Chips Selector
                val categoryIcons = mapOf(
                    "All" to "📱 All",
                    "Finance" to "🕌 Finance",
                    "Documents" to "📄 Docs",
                    "Utilities" to "⚙️ Tools",
                    "Study" to "🎓 Study",
                    "Medical" to "🩺 Medical"
                )
                val categoriesList = listOf("All", "Finance", "Documents", "Utilities", "Study", "Medical")

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    categoriesList.forEach { category ->
                        val isSelected = selectedCategory == category
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = category },
                            label = {
                                Text(
                                    text = categoryIcons[category] ?: category,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                // Filtering of tools in real-time
                val filteredTools = allToolsList.filter { tool ->
                    (selectedCategory == "All" || tool.category == selectedCategory) &&
                    (tool.label.contains(searchQuery, ignoreCase = true) ||
                     tool.description.contains(searchQuery, ignoreCase = true) ||
                     tool.badge?.contains(searchQuery, ignoreCase = true) == true)
                }

                if (filteredTools.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(36.dp)
                            )
                            Text(
                                text = "No matching utilities found",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Try typing another keyword (e.g., PDF, Zakat, Cameti, Timer)",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    val columns = 2
                    val chunkedTools = filteredTools.chunked(columns)

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        chunkedTools.forEach { rowTools ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowTools.forEach { tool ->
                                    HDDesignToolButton(
                                        tool = tool,
                                        onClick = { viewModel.navigateTo(tool.screen) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (rowTools.size < columns) {
                                    repeat(columns - rowTools.size) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Timetable Snippet (Today's Next Class)
            val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1=Sun, 2=Mon...
            // Map Sunday to 7, Monday to 1, etc.
            val adjustedDayNum = when (currentDayOfWeek) {
                Calendar.MONDAY -> 1
                Calendar.TUESDAY -> 2
                Calendar.WEDNESDAY -> 3
                Calendar.THURSDAY -> 4
                Calendar.FRIDAY -> 5
                Calendar.SATURDAY -> 6
                Calendar.SUNDAY -> 7
                else -> 1
            }

            val todaysClasses = timetable.filter { it.dayOfWeek == adjustedDayNum }
            Text(
                text = "Today's Classes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (todaysClasses.isEmpty()) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Weekend, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "No classes scheduled for today! Enjoy your day.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(todaysClasses) { classItem ->
                        Card(
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (!classItem.color.isNullOrEmpty()) {
                                    try { Color(android.graphics.Color.parseColor(classItem.color)) } catch (e: Exception) { MaterialTheme.colorScheme.surface }
                                } else {
                                    MaterialTheme.colorScheme.secondaryContainer
                                }.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier
                                .width(220.dp)
                                .clickable { viewModel.navigateTo(Screen.Timetable) }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = classItem.subject,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${classItem.startTime} - ${classItem.endTime}",
                                        fontSize = 12.sp
                                    )
                                }
                                if (!classItem.teacher.isNullOrEmpty() || !classItem.room.isNullOrEmpty()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = classItem.teacher ?: "", fontSize = 11.sp, maxLines = 1)
                                        Text(
                                            text = "Room: ${classItem.room ?: "N/A"}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Upcoming Bills
            val upcomingBills = bills.filter { it.isPaid == 0 }.take(3)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Upcoming Bills",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = { viewModel.navigateTo(Screen.UtilityBills) }) {
                    Text("View All", fontSize = 12.sp)
                }
            }

            if (upcomingBills.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "🥳 Standard alert: All clear! No unsettled utility bills.",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    upcomingBills.forEach { bill ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                .clickable { viewModel.navigateTo(Screen.UtilityBills) }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Constants.getCategoryIcon(bill.category),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(text = bill.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "Due: ${bill.dueDate}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                                }
                            }
                            Text(text = "Rs. ${bill.amount}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Goals progress
            if (savingsGoals.isNotEmpty()) {
                Text(
                    text = "Savings Progress",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                savingsGoals.take(2).forEach { goal ->
                    val progress = if (goal.targetAmount > 0) (goal.currentAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f) else 0f
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = goal.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(
                                    text = "${String.format("%.0f", goal.currentAmount)} / ${String.format("%.0f", goal.targetAmount)} Rs.",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = progress,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // -----------------------------------------------------------------------------
            // HOME SCREEN FOOTER UI - Data Portability & About Developer
            // -----------------------------------------------------------------------------

            // Data Portability & PDF Print Footer Card
            Card(
                modifier = Modifier.fillMaxWidth().testTag("home_footer_tools_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CloudSync,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Data Backup & PDF Report",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Instant 1-click access to print your master financial PDF statement or export/import full modular database JSON backups.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Master PDF Print Button
                        Button(
                            onClick = {
                                val totalInc = income.sumOf { it.amount }
                                val totalExp = expenses.sumOf { it.amount }
                                val net = totalInc - totalExp
                                val totalUnpaid = allBills.filter { it.isPaid == 0 }.sumOf { it.amount }
                                val totalBorrowed = loans.filter { it.type == "Borrowed" && it.isSettled == 0 }.sumOf { it.amount }
                                val totalLent = loans.filter { it.type == "Lent" && it.isSettled == 0 }.sumOf { it.amount }
                                val totalSaved = savingsGoals.sumOf { it.currentAmount }
                                val totalTarget = savingsGoals.sumOf { it.targetAmount }

                                triggerFinanceMasterPrint(
                                    context = context,
                                    expenses = expenses,
                                    income = income,
                                    bills = allBills,
                                    committees = committees,
                                    loans = loans,
                                    savingsGoals = savingsGoals,
                                    totalIncome = totalInc,
                                    totalExpense = totalExp,
                                    netBalance = net,
                                    totalUnpaid = totalUnpaid,
                                    totalBorrowed = totalBorrowed,
                                    totalLent = totalLent,
                                    totalSaved = totalSaved,
                                    totalTarget = totalTarget
                                )
                            },
                            modifier = Modifier.weight(1f).testTag("home_pdf_print_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("PDF Print", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        // Export JSON Button
                        Button(
                            onClick = {
                                val dateStr = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
                                exportJsonLauncher.launch("Hikmah_Omni_Suite_Backup_$dateStr.json")
                            },
                            modifier = Modifier.weight(1f).testTag("home_export_json_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CloudDownload, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Export", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        // Import JSON Button
                        Button(
                            onClick = {
                                importJsonLauncher.launch("application/json")
                            },
                            modifier = Modifier.weight(1f).testTag("home_import_json_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Import", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Developer & About App Footer Card
            Card(
                modifier = Modifier.fillMaxWidth().testTag("home_footer_about_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Code,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Developer Tahir Buneri",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "WhatsApp: +923465552678",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Hikmah Omni Suite is an unrivaled all-in-one platform unifying Islamic knowledge resources, financial management, document scanning & OCR, military-grade security vaults, medical tools, and study systems.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // WhatsApp Contact
                        OutlinedButton(
                            onClick = {
                                try {
                                    val devMsg = Uri.encode("Assalamualaikum Tahir Buneri, I have explored your application and I'm highly interested in purchasing the application license. Please provide details regarding licensing terms, pricing, and onboarding support. Thank you!")
                                    val url = "https://wa.me/923465552678?text=$devMsg"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "WhatsApp: +923465552678", Toast.LENGTH_LONG).show()
                                }
                            },
                            modifier = Modifier.weight(1f).testTag("home_whatsapp_button"),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFF25D366))
                        ) {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = Color(0xFF25D366), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("WhatsApp", color = Color(0xFF25D366), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        // About App & Modules Button
                        Button(
                            onClick = { viewModel.navigateTo(Screen.About) },
                            modifier = Modifier.weight(1.2f).testTag("home_about_app_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("About App", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun QuickActionItem(
    label: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    width: androidx.compose.ui.unit.Dp,
    testTag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(width)
            .clickable(onClick = onClick)
            .testTag(testTag)
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
