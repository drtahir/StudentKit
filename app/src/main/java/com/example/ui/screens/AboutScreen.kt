package com.example.ui.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.R
import com.example.viewmodel.StudentKitViewModel

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
            // Official Brand Identity Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        shadowElevation = 6.dp,
                        border = BorderStroke(
                            1.5.dp,
                            Brush.linearGradient(listOf(Color(0xFFFFD54F), Color(0xFF26A69A)))
                        ),
                        modifier = Modifier.size(64.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_brand_logo),
                            contentDescription = "Hikmah Omni Suite Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Hikmah",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
                            ) {
                                Text(
                                    text = "Omni Suite",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "Version 3.5.0 • Official Production Build",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = "Intelligence • Knowledge • Productivity",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        )
                    }
                }
            }

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
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Developer Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }

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
                        text = "Direct WhatsApp Contact:",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(
                        onClick = {
                            try {
                                val devMsg = Uri.encode("Assalamualaikum Tahir Buneri, I have explored your application and I'm highly interested in purchasing the application license. Please provide details regarding licensing terms, pricing, and onboarding support. Thank you!")
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

            // Unrivaled Service Statement Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Unmatched All-in-One Service Platform",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No other single application exists in the world with similar services unified into a single offline-first solution. Hikmah Omni Suite seamlessly integrates complete Islamic knowledge resources, financial management, document scanning & OCR, hardware-level encryption vaults, clinical/medical calculators, and study productivity systems.",
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.9f)
                    )
                }
            }

            // Comprehensive App Modules Breakdown
            Text(
                text = "Complete Module Directory",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Module 1: Finance Suite
            AboutModuleCard(
                title = "1. Financial Management Suite",
                icon = Icons.Default.AccountBalance,
                iconColor = Color(0xFF43A047),
                description = "Comprehensive multi-ledger financial tracking for everyday student and personal life:",
                features = listOf(
                    "Expense Tracker — Categorized expense logs with daily/monthly spending analytics.",
                    "Income Tracker — Monitor wages, allowances, stipends & recurring cash inputs.",
                    "Utility Bills Manager — Bill reminders, due date alerts & payment receipt logs.",
                    "Zakat Calculator — Precise Islamic wealth Zakat solver according to Nisab.",
                    "BC Committees (Kameti) — Full rotating lucky draw, member payout tracking & payout cycle history.",
                    "Loan & Debt Ledger — Track money borrowed or lent to friends with settlement statuses.",
                    "Savings Goals — Visual target progress bars and deposit milestone logs.",
                    "Master PDF Financial Statement — Printable multi-module summary PDF with customizable toggles.",
                    "JSON Data Portability — Direct export and import of full database backups in clean JSON format."
                )
            )

            // Module 2: Document & Scan Suite
            AboutModuleCard(
                title = "2. Documents & Camera Scan Suite",
                icon = Icons.Default.Scanner,
                iconColor = Color(0xFF1E88E5),
                description = "Professional document creator and high-definition mobile scanning tools:",
                features = listOf(
                    "CV Resume Builder — A4 print-ready professional PDF resume creator with custom sections.",
                    "ID Card Scanner — Scan front and back of physical ID cards onto a single print-ready page.",
                    "Passport Page Scanner — Specialized high-resolution passport page capture utility.",
                    "Image to PDF/Excel/Word — OCR image converter for text extraction into PDF, XLS, or DOCX.",
                    "HD Edge Document Scanner — Real-time edge detection document page scanner.",
                    "PDF Tool Handlers — Compress, merge, split, encrypt, and unlock PDF files locally.",
                    "Invoice Maker — Custom business invoice and client receipt generator with PDF export.",
                    "Stamp & Signature Pad — Freehand digital signature pad and digital document stamper."
                )
            )

            // Module 3: Security & Vault Suite
            AboutModuleCard(
                title = "3. Hardware Security & Stealth Vault",
                icon = Icons.Default.Security,
                iconColor = Color(0xFFD32F2F),
                description = "Military-grade encryption and privacy protection tools:",
                features = listOf(
                    "File Encryptor — Keystore hardware AES-256 GCM file encryption and decryption.",
                    "Hidden File Locker — Local sandbox vault for sensitive media and documents.",
                    "LSB Steganography — Hide secret text messages inside RGB pixels of standard images.",
                    "Intruder Guard — Silent background photo selfie capture & siren alarm on unauthorized access attempts.",
                    "PIN & Photo Vault — Encrypted local vault for banking PINs and private photo galleries.",
                    "Decoy Private Notes — Password-protected note locker with decoy passcodes.",
                    "App Locker & Calculator Vault — Stealth calculator disguise launcher for hidden utilities."
                )
            )

            // Module 4: Smart Utilities
            AboutModuleCard(
                title = "4. Smart Daily Utilities & AI Tools",
                icon = Icons.Default.Build,
                iconColor = Color(0xFF8E24AA),
                description = "Essential utilities and offline AI photo processing studio:",
                features = listOf(
                    "Scientific Calculator — Advanced mathematical formula solver.",
                    "Unit Converter — Convert data, length, weight, speeds, and dimensions.",
                    "QR Code Suite — Vector QR generator with custom colors and fast camera QR scanner.",
                    "Password Manager — Encrypted credentials vault with strength check.",
                    "AI Image Enhancer — Offline face restorer and image quality enhancer.",
                    "AI Background Eraser — Automatic background remover with manual refine brush.",
                    "Watermark Studio — Custom text watermarks and visual filters.",
                    "Age Calculator & Image Batch Compressor — Exact age finder and file optimizer."
                )
            )

            // Module 5: Study & Health
            AboutModuleCard(
                title = "5. Study, Wellness & Islamic Hub",
                icon = Icons.Default.School,
                iconColor = Color(0xFFF57C00),
                description = "Academic aids, wellness trackers, and Islamic spiritual resources:",
                features = listOf(
                    "Lecture Notes — Structured study notes and lecture logs.",
                    "Pomodoro Study Timer — Focus session timer with productivity analytics.",
                    "Lesson Agenda Timetable — Weekly class timetable and schedule organizer.",
                    "BMI & Hydration Tracker — Health wellness calculator and water intake log.",
                    "GPA Calculator — Instant Semester GPA and cumulative CGPA solver.",
                    "HD Quran Majeed & Islamic Hub — Complete Quran text, audio recitations, and Manzil."
                )
            )

            // Module 6: Clinical & Exam Prep
            AboutModuleCard(
                title = "6. Medical Clinical Tools & Exam Prep",
                icon = Icons.Default.MedicalServices,
                iconColor = Color(0xFF009688),
                description = "Specialized clinical tools and competitive examination preparation:",
                features = listOf(
                    "IV Infusion Rate Solver — Fluid drop rate and flow rate calculator.",
                    "Drug Dosage Calculator — Body-weight patient medication dosage solver.",
                    "GFR Renal Function Solver — Cockcroft-Gault kidney creatinine clearance calculator.",
                    "Human Anatomy Atlas — Visual anatomical systems atlas and study quizzes.",
                    "Pharmacy Assistant Exam Prep — Pakistan Category B pharmacy exam prep quizzes.",
                    "Hajj Medical Mission Exam Prep — NTS medical mission exam preparation model papers."
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
