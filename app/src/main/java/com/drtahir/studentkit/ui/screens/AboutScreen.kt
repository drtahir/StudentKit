package com.drtahir.studentkit.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drtahir.studentkit.viewmodel.Screen
import com.drtahir.studentkit.viewmodel.StudentKitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    viewModel: StudentKitViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var feedbackText by remember { mutableStateOf("") }

    val devName = "Tahir Buneri"
    val devWhatsApp = "+923465552678"
    val devWhatsAppRaw = "923465552678"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "About Hikmah Omni Suite",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Developer Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                ),
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedBrandLogo(
                        size = 80.dp,
                        showRings = true,
                        isInteractive = false
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Developed by $devName",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "Lead Developer & Software Engineer",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "For Official Licensing, Custom Features & Queries:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            try {
                                val devMsg = Uri.encode("Assalam-o-Alaikum Tahir Buneri,\nI am using Hikmah Omni Suite App and would like to get in touch regarding licensing, feedback, or custom development. Thank you!")
                                val url = "https://wa.me/$devWhatsAppRaw?text=$devMsg"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Contact WhatsApp: $devWhatsApp", Toast.LENGTH_LONG).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.testTag("whatsapp_dev_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "WhatsApp",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "WhatsApp: $devWhatsApp",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Friendly Introduction in Simple Pakistani English
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Assalam-o-Alaikum & Welcome!",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Hikmah Omni Suite is Pakistan's most powerful all-in-one offline super app. We built this app to solve everyday problems faced by Pakistani students, medical professionals, business owners, shopkeepers, and families — all in one single place.\n\n" +
                                "🌟 100% Offline & Private: No internet required! Your data, CNIC scans, and financial records never leave your phone.\n" +
                                "🌟 Zero Ads & Subscriptions: Free from annoying popup advertisements or monthly fees.\n" +
                                "🌟 Tailored for Pakistan: Built specifically for local needs like Kameti (BC), CNIC scanning, Gold/Silver Zakat, NTS/Prometric exams, and Khata bookkeeping.",
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.95f)
                    )
                }
            }

            // Section Header
            Text(
                text = "Detailed Guide to Every Module & Tool",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Module 1: Financial Khata & Money Management
            AboutModuleCard(
                title = "1. Financial Management & Daily Khata (مالیاتی حساب کتاب)",
                icon = Icons.Default.AccountBalance,
                iconColor = Color(0xFF43A047),
                description = "Complete bookkeeping for everyday personal, hostel, family, and shopkeeper expenses:",
                features = listOf(
                    "Expense Tracker — Keep an eye on where every single rupee goes. Easily log daily chai, meals, hostel dues, transport, fuel, and shopping with neat category graphs.",
                    "Income Tracker — Monitor all incoming cash including monthly salaries, pocket money, student stipends, freelance payments, and business profits.",
                    "Utility Bills Manager — Never forget a due date! Save Bijli (WAPDA/K-Electric), Sui Gas, Water, and PTCL/Wi-Fi bills with reference numbers and payment receipts.",
                    "Islamic Zakat Calculator — Calculate your exact Zakat according to Shariah rules. Just enter your Gold (Tolas/Grams), Silver, Cash in hand/bank, and business trade goods — it deducts debts and tells you the exact payable amount.",
                    "BC Committees (Kameti / کمیٹی) — Smoothly manage your family or mohalla committee. Track monthly installments, see who took the pot, and run fair lucky draws without any quarrel.",
                    "Loan & Udhar Ledger (ادھار کھاتہ) — Clear records of money you lent to friends or borrowed from relatives, with instant 'Paid' or 'Unpaid' status markers.",
                    "Savings Goals — Set targets for a new laptop, motorcycle, or emergency fund with motivating milestone progress bars.",
                    "Master PDF Financial Statement — Generate a clean, printable PDF statement of your entire wealth and cashflow at any time.",
                    "JSON Backup & Restore — Export all your financial records into a safe JSON file on your storage so your data is always secure."
                )
            )

            // Module 2: Documents & 13-in-1 Master PDF Suite
            AboutModuleCard(
                title = "2. Documents, Scanning & 13-in-1 Master PDF Suite (دستاویزات اور پی ڈی ایف)",
                icon = Icons.Default.PictureAsPdf,
                iconColor = Color(0xFF1E88E5),
                description = "High-definition mobile scanning, resume building, and 13 offline PDF power tools:",
                features = listOf(
                    "13-in-1 Master PDF Suite Handlers:\n" +
                    "  • Merge PDFs: Combine multiple chapters, slides, or admission challans into one single PDF.\n" +
                    "  • Split & Extract: Pick specific pages or ranges and save them into a separate document.\n" +
                    "  • Compress PDF: Reduce large PDF files from MBs to small KBs for easy sharing on WhatsApp or email.\n" +
                    "  • PDF to Images: Convert PDF pages into high-resolution JPG or PNG photos.\n" +
                    "  • Watermark Stamp: Stamp 'CONFIDENTIAL', 'ORIGINAL', 'SAMPLE', or your custom name across all pages.\n" +
                    "  • Rotate PDF: Fix sideways or upside-down scanned pages (90°, 180°, 270°).\n" +
                    "  • Add Page Numbers & Headers: Stamp neat page numbers (e.g., 'Page 1 of 20') and running subject titles.\n" +
                    "  • Grayscale & B/W Optimizer: Convert colored PDFs to photocopy black & white to save printer ink and toner.\n" +
                    "  • Night Dark Mode Invert: Invert bright white pages into a dark reading theme for comfortable night study.\n" +
                    "  • 2-Up & 4-Up Booklet Grid: Fit 2 or 4 pages per sheet with cutting lines for compact cheat-sheets and handouts.\n" +
                    "  • Margin Cropper: Trim unnecessary white borders and slide margins.\n" +
                    "  • Password Protect & Metadata: Lock PDFs with a password and customize author/title tags.",
                    "CV Resume Builder — Create professional, job-winning A4 PDF resumes with your photo, education, skills, and work history ready for printing or emailing.",
                    "ID Card (CNIC) Scanner — Snap the front and back of your CNIC or driving license; it places both sides side-by-side on a single print-ready A4 page.",
                    "Passport Page Scanner — Specialized high-resolution scanner for international passport bio-data pages.",
                    "Image to PDF / Word / Excel OCR — Turn textbook photos and paper tables into editable Word (.docx), Excel (.xls), or searchable PDF files.",
                    "Edge Document Scanner — Camera scanner that auto-detects paper edges with crisp black & white and color enhancements.",
                    "Digital Stamp & Signature Pad — Draw your signature with your finger on screen and save it with a transparent background to sign documents.",
                    "OmniPOS & Invoice Maker — Make professional client invoices and thermal cash receipts with your shop name, item barcodes, taxes, and instant PDF download."
                )
            )

            // Module 3: Security & Stealth Vault
            AboutModuleCard(
                title = "3. Military-Grade Security & Stealth Vault (سیکیورٹی اور پرائیویسی والٹ)",
                icon = Icons.Default.Security,
                iconColor = Color(0xFFD32F2F),
                description = "Maximum privacy and hardware-level encryption to safeguard your private files:",
                features = listOf(
                    "Hardware File Encryptor — Lock any document, image, or audio using Android Keystore AES-256 GCM encryption. Without your password, nobody can open the file even on a computer.",
                    "Hidden Sandbox Locker — A private secret vault for personal photos, videos, and private notes that completely hides them from your phone's normal gallery.",
                    "LSB Image Steganography — Secretly hide confidential text messages inside the pixels of normal photos. The photo looks completely untouched to the human eye!",
                    "Steganalysis Forensics — Advanced analysis tool to inspect suspicious images and uncover hidden secret text or data payloads.",
                    "Intruder Guard — If anyone tries to open your vault with the wrong passcode, it secretly snaps their photo using the front camera and sounds a loud alarm siren.",
                    "Encrypted Password Manager — Store all your bank PINs, ATM cards, Gmail, and social media passwords safely with an offline password strength generator."
                )
            )

            // Module 4: Smart Utilities & AI Studio
            AboutModuleCard(
                title = "4. Smart Daily Utilities & AI Studio (روزمرہ کے سمارٹ ٹولز)",
                icon = Icons.Default.AutoAwesome,
                iconColor = Color(0xFF8E24AA),
                description = "Everyday life-saver utilities and on-device photo processing tools:",
                features = listOf(
                    "Smart QR & Barcode Studio — Generate stylish colored QR codes with your business logo, WhatsApp direct links, and WiFi instant-connect codes, plus a fast camera scanner.",
                    "Offline AI Photo Enhancer — Fix blurry or low-quality photos and restore old family memories right on your phone without sending photos to any cloud.",
                    "AI Background Eraser — Automatically remove backgrounds from portraits and product pictures with a precision brush for clean transparent stickers.",
                    "Teleprompter Pro Studio — Record professional camera videos while your presentation script smoothly scrolls on the screen — ideal for teachers, students, and content creators.",
                    "Scientific Math Calculator — Powerful algebraic calculator with trigonometry, square roots, logarithms, powers, and calculation history.",
                    "Unit Converter — Quickly convert units for Length, Weight, Temperature, Area, Speeds, and Digital Data storage (MB, GB, TB).",
                    "Age Calculator — Calculate your exact age in years, months, days, minutes, and see how many days are left until your next birthday.",
                    "Batch Image Compressor — Shrink heavy camera photos down to small KBs while keeping great picture clarity."
                )
            )

            // Module 5: Study Productivity & Islamic Hub
            AboutModuleCard(
                title = "5. Study Productivity & Islamic Spiritual Hub (تعلیم اور اسلامی معلومات)",
                icon = Icons.Default.School,
                iconColor = Color(0xFFF57C00),
                description = "Academic excellence tools and comprehensive Islamic spiritual guidance:",
                features = listOf(
                    "Lecture Notes Keeper — Keep structured, organized study notes and revision summaries for every course subject.",
                    "Pomodoro Focus Timer — Study in scientifically-proven 25-minute focus intervals with short breaks and daily productivity analytics.",
                    "Class Timetable & Agenda — Keep your weekly university/college lecture schedule and classroom room numbers right at your fingertips.",
                    "University GPA & CGPA Calculator — Calculate your Semester GPA and cumulative CGPA instantly with custom course credit hours and grading scales.",
                    "BMI & Daily Water Intake Log — Stay healthy during exam days by tracking your body mass index and daily drinking water glasses.",
                    "HD Quran Majeed & Manzil — Read the Holy Quran with crystal-clear Indo-Pak script, protection Manzil Dua, morning/evening Azkar, digital Tasbeeh counter, and Qibla compass."
                )
            )

            // Module 6: Medical Clinical Tools & Pakistan Exam Kits
            AboutModuleCard(
                title = "6. Medical Clinical Tools & Pakistan Exam Kits (طبی ٹولز اور امتحانی تیاری)",
                icon = Icons.Default.MedicalServices,
                iconColor = Color(0xFF009688),
                description = "Crucial clinical calculators for healthcare staff and comprehensive exam prep for Pakistani professionals:",
                features = listOf(
                    "Nursing 12,000+ Exam Master Kit — Massive question bank with detailed rationales for NCLEX-RN, Saudi Prometric, DHA Dubai, HAAD Abu Dhabi, MOH, Qatar Prometric, and PNC nursing licensing exams.",
                    "Moavineen-e-Hujjaj MORA NTS Prep — Comprehensive preparation for the Ministry of Religious Affairs (MORA) supervisor and supporting staff Hajj operations test with 1,000+ past paper questions.",
                    "Hajj Medical Mission NTS Prep — Specialized medical questions and clinical scenarios for Pakistani doctors, nurses, and dispensers applying for the Hajj Medical Mission.",
                    "Pharmacy Assistant Category-B Exam — Complete model papers and syllabus coverage for the Pakistan Pharmacy Council apprentice exam (Pharmaceutics, Pharmacology, Forensic Law).",
                    "IV Infusion Flow & Drop Rate Solver — Instant calculation of IV drip rates (drops per minute) for micro and macro infusion sets.",
                    "Pediatric & Adult Drug Dosage Solver — Accurate weight-based medication dosage calculator to prevent clinical dosing errors.",
                    "Cockcroft-Gault GFR Kidney Solver — Quick creatinine clearance calculator for renal function dosage adjustments.",
                    "Interactive Human Anatomy Atlas — Visual anatomy systems with colorful reference diagrams and quick-learning revision quizzes."
                )
            )

            // Suggestions & Feedback Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = Color(0xFFFFB300),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Suggestions & Feedback",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "We value your ideas! Have a suggestion, feature request, or feedback? Type your thought below and send it directly to Tahir Buneri via WhatsApp.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = feedbackText,
                        onValueChange = { feedbackText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("feedback_text_field"),
                        placeholder = { Text("Write your suggestion or feature idea here...", fontSize = 13.sp) },
                        minLines = 3,
                        maxLines = 5,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (feedbackText.trim().isEmpty()) {
                                Toast.makeText(context, "Please enter your suggestion first.", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            try {
                                val message = Uri.encode("Hello Tahir Buneri,\n\nI have a suggestion for Hikmah Omni Suite App:\n${feedbackText.trim()}")
                                val url = "https://wa.me/$devWhatsAppRaw?text=$message"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                                feedbackText = ""
                            } catch (e: Exception) {
                                Toast.makeText(context, "Could not open WhatsApp. Send to $devWhatsApp", Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("send_feedback_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Send Suggestion via WhatsApp", fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun AboutModuleCard(
    title: String,
    icon: ImageVector,
    iconColor: Color,
    description: String,
    features: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(iconColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            features.forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "• ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = iconColor
                    )
                    Text(
                        text = feature,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}
