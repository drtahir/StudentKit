package com.example.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.StudentKitViewModel
import kotlinx.coroutines.delay

// =============================================================================
// MODULE 12: PAKISTAN PHARMACY CATEGORY B EXAM PREPARATION PORTAL
// =============================================================================

data class ExamQuestion(
    val id: Int,
    val subject: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val reference: String // Pakistani Syllabus/Drug Act 1976 reference
)

data class StudyTopic(
    val title: String,
    val paperSection: String,
    val content: String,
    val clinicalPearls: String,
    val typicalExamQuestion: String
)

data class CalcPracticeProblem(
    val id: Int,
    val title: String,
    val prompt: String,
    val correctAnswer: Double,
    val unit: String,
    val formula: String,
    val explanation: String,
    val parameterPlaceholder: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmacyExamScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var activeSubTab by remember { mutableStateOf(0) } // 0 = Exam Simulator, 1 = Syllabus Flashcards, 2 = Drug Law Hub, 3 = Calculations Trainer

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Upper Sub-tab navigation
        ScrollableTabRow(
            selectedTabIndex = activeSubTab,
            edgePadding = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = activeSubTab == 0,
                onClick = { activeSubTab = 0 },
                text = { Text("Mock Exam", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Timer, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeSubTab == 1,
                onClick = { activeSubTab = 1 },
                text = { Text("High-Yield Notes", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.MenuBook, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeSubTab == 2,
                onClick = { activeSubTab = 2 },
                text = { Text("Pakistan Drug Law", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Gavel, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeSubTab == 3,
                onClick = { activeSubTab = 3 },
                text = { Text("Calculations Lab", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Calculate, null, modifier = Modifier.size(16.dp)) }
            )
        }

        when (activeSubTab) {
            0 -> PharmacyExamSimulatorView()
            1 -> PharmacySyllabusExplorerView()
            2 -> PakistanDrugLawHubView()
            3 -> PharmaceuticalCalculationsTrainer()
        }
    }
}

/**
 * 1. INTERACTIVE EXAM SIMULATOR
 */
@Composable
fun PharmacyExamSimulatorView() {
    val context = LocalContext.current
    
    // Comprehensive high-yield Pakistan Pharmacy Category B Exam questions
    val questions = remember {
        listOf(
            ExamQuestion(
                1, "Pharmaceutics",
                "According to USP guidelines, what is the standard temperature and holding time required for dynamic steam sterilization (Autoclaving) of medical utensils?",
                listOf("100°C for 60 minutes", "121°C (at 15 psi pressure) for 15-20 minutes", "160°C for 120 minutes", "80°C for 45 minutes"),
                1,
                "Moist heat sterilization (Autoclaving) uses saturated steam under pressure. The standard protocol is 121°C at 15 pounds per square inch (psi) of pressure for at least 15 minutes, which destroys all highly-resistant bacterial endospores.",
                "Pharmaceutics Paper-II (Sterilization Section)"
            ),
            ExamQuestion(
                2, "Pharmacy Law & Ethics",
                "Under the Pakistan Drug Act 1976, which form is officially designated as the 'Form of Warranty' that a licensed manufacturer issues to a pharmacy retailer?",
                listOf("Form 2-A", "Form 5", "Form 12-B", "Form 9"),
                1,
                "Under the Drug Act 1976 and rules thereunder, Form 5 is the standard legal form used for prescribing warranty of drug quality by a distributor/manufacturer to a dispensing chemist/retailer.",
                "Pakistan Drug Act 1976, Section 23(1)(i)"
            ),
            ExamQuestion(
                3, "Pharmacology",
                "Which class of anti-hypertensive drugs acts primarily by blocking the conversion of Angiotensin I to Angiotensin II in pulmonary capillaries?",
                listOf("Beta-blockers (e.g. Propranolol)", "Calcium Channel Blockers (e.g. Amlodipine)", "ACE Inhibitors (e.g. Captopril, Enalapril)", "Loop Diuretics (e.g. Furosemide)"),
                2,
                "Angiotensin-Converting Enzyme (ACE) Inhibitors block the conversion of Angiotensin I into the potent vasoconstrictor Angiotensin II. This reduces peripheral arterial resistance and lowers blood pressure.",
                "Pharmacology Paper-II (Cardiovascular Drugs)"
            ),
            ExamQuestion(
                4, "Pharmacognosy",
                "What is the botanical source of 'Senna' leaves, a highly common crude purgative stimulant laxative widely sold in Pakistani herbal pharmacies?",
                listOf("Cassia angustifolia (or Cassia acutifolia)", "Digitalis purpurea", "Cinchona officinalis", "Zingiber officinale"),
                0,
                "Senna consists of the dried leaflets of Cassia angustifolia (known as Indian Senna) or Cassia acutifolia (Alexandrian Senna), belonging to the family Fabaceae. Its active chemical constituents are sennosides A and B.",
                "Pharmacognosy Paper-II (Glycosides & Laxatives)"
            ),
            ExamQuestion(
                5, "Microbiology",
                "In bacterial Gram staining, what is the exact function of 'Gram's Iodine' solution?",
                listOf("Primary basic counterstain", "Decolorizing agent", "Mordant (fixes the crystal violet dye)", "Acid-fast cellular dissolver"),
                2,
                "Gram's Iodine acts as a mordant. It forms a chemical complex with the primary crystal violet dye within the thick peptidoglycan cell walls of Gram-positive bacteria, preventing it from being washed out easily by alcohol.",
                "Microbiology Paper-I (Staining Techniques)"
            ),
            ExamQuestion(
                6, "Pharmaceutics",
                "What type of pharmaceutical incompatibility occurs when two solid drugs (like Menthol and Camphor) are mixed and form a liquid due to depression of their melting points?",
                listOf("Chemical Incompatibility", "Eutectic Mixture formation", "Therapeutic Antagonism", "Physical Precipitation"),
                1,
                "When substances like menthol, camphor, or thymol are mixed together, they form a eutectic mixture—liquefying at room temperature because their combined melting point is lower than room temperature.",
                "Pharmaceutics Paper-II (Dispensing Incompatibilities)"
            ),
            ExamQuestion(
                7, "Pharmacy Law & Ethics",
                "Which authority in Pakistan is responsible for registering drug products and issuing Manufacturing/Retail licenses under federal drug safety mandates?",
                listOf("Pakistan Medical Commission (PMC)", "Drug Regulatory Authority of Pakistan (DRAP)", "Provincial Health Department", "National Institute of Health (NIH)"),
                1,
                "The Drug Regulatory Authority of Pakistan (DRAP), established under the DRAP Act 2012, is the supreme federal agency responsible for drug registrations, manufacturing licenses, quality control, and pricing.",
                "DRAP Act 2012 & Drug Act 1976"
            ),
            ExamQuestion(
                8, "Pharmacology",
                "A patient taking warfarin (oral anticoagulant) starts taking high doses of Aspirin (NSAID). Why is this combination clinically contraindicated?",
                listOf(
                    "Aspirin speeds up renal clearance of warfarin",
                    "Aspirin causes additive antiplatelet effects and displaces warfarin from plasma albumin, significantly increasing hemorrhage risk",
                    "Aspirin directly decomposes the warfarin compound in the stomach",
                    "Warfarin completely neutralizes the analgesic efficacy of Aspirin"
                ),
                1,
                "Aspirin has antiplatelet activity and can cause gastric mucosal damage. Furthermore, NSAIDs can displace warfarin from its plasma protein binding sites (albumin), leading to elevated free warfarin levels and severe bleeding.",
                "Pharmacology Paper-II (Drug Interactions)"
            )
        )
    }

    var currentQIndex by remember { mutableStateOf(0) }
    val selectedAnswers = remember { mutableStateMapOf<Int, Int>() } // questionId -> selectedOptionIndex
    var examSubmitted by remember { mutableStateOf(false) }
    
    // Exam Timer State
    var timerSeconds by remember { mutableStateOf(600) } // 10 minutes
    var isTimerActive by remember { mutableStateOf(true) }

    LaunchedEffect(isTimerActive, examSubmitted) {
        if (isTimerActive && !examSubmitted) {
            while (timerSeconds > 0) {
                delay(1000)
                timerSeconds--
            }
            if (timerSeconds == 0) {
                examSubmitted = true
                Toast.makeText(context, "Time is up! Exam submitted automatically.", Toast.LENGTH_LONG).show()
            }
        }
    }

    val score = questions.count { selectedAnswers[it.id] == it.correctIndex }
    val progress = (currentQIndex + 1).toFloat() / questions.size

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Timer and Score Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (examSubmitted) {
                        if (score >= questions.size / 2) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                    } else {
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    }
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (examSubmitted) "EXAM COMPLETED" else "SIMULATED EXAM ACTIVE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (examSubmitted) {
                                "Score: $score / ${questions.size} (${(score.toDouble() / questions.size * 100).toInt()}% Pass)"
                            } else {
                                val mins = timerSeconds / 60
                                val secs = timerSeconds % 60
                                "Time Remaining: ${String.format("%02d:%02d", mins, secs)}"
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (examSubmitted) {
                                if (score >= questions.size / 2) Color(0xFF2E7D32) else Color(0xFFC62828)
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }

                    if (!examSubmitted) {
                        Button(
                            onClick = {
                                examSubmitted = true
                                isTimerActive = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Submit Exam", fontSize = 11.sp)
                        }
                    } else {
                        OutlinedButton(
                            onClick = {
                                selectedAnswers.clear()
                                examSubmitted = false
                                currentQIndex = 0
                                timerSeconds = 600
                                isTimerActive = true
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Refresh, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Retake", fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Question Progress Bar
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Subject: ${questions[currentQIndex].subject}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Question ${currentQIndex + 1} of ${questions.size}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape)
                )
            }
        }

        // Active Question Card
        val activeQ = questions[currentQIndex]
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = activeQ.question,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Options
        items(activeQ.options.size) { index ->
            val isSelected = selectedAnswers[activeQ.id] == index
            val isCorrect = index == activeQ.correctIndex
            val isWrong = isSelected && !isCorrect

            val containerColor = when {
                examSubmitted && isCorrect -> Color(0xFFE8F5E9) // Success green
                examSubmitted && isWrong -> Color(0xFFFFEBEE) // Error red
                isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                else -> MaterialTheme.colorScheme.surface
            }

            val borderColor = when {
                examSubmitted && isCorrect -> Color(0xFF4CAF50)
                examSubmitted && isWrong -> Color(0xFFE53935)
                isSelected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outlineVariant
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !examSubmitted) {
                        selectedAnswers[activeQ.id] = index
                    },
                colors = CardDefaults.cardColors(containerColor = containerColor),
                border = BorderStroke(if (isSelected || (examSubmitted && isCorrect)) 2.dp else 1.dp, borderColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.4f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ('A'.code + index).toChar().toString(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(activeQ.options[index], fontSize = 13.sp)
                    }

                    if (examSubmitted) {
                        if (isCorrect) {
                            Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF2E7D32))
                        } else if (isWrong) {
                            Icon(Icons.Default.Cancel, null, tint = Color(0xFFC62828))
                        }
                    }
                }
            }
        }

        // Explanation / Rationales (Post-Submission)
        if (examSubmitted) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.15f)),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lightbulb, null, tint = Color(0xFFFBC02D), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Exam Rationale & Reference:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(activeQ.explanation, fontSize = 12.sp, lineHeight = 18.sp, color = Color.DarkGray)
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(
                            text = "📚 Reference: ${activeQ.reference}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }

        // Navigation Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { if (currentQIndex > 0) currentQIndex-- },
                    enabled = currentQIndex > 0,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Prev", fontSize = 11.sp)
                }

                OutlinedButton(
                    onClick = { if (currentQIndex + 1 < questions.size) currentQIndex++ },
                    enabled = currentQIndex + 1 < questions.size,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Next", fontSize = 11.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

/**
 * 2. SYLLABUS HIGH-YIELD EXPLORER / STUDY NOTES
 */
@Composable
fun PharmacySyllabusExplorerView() {
    var selectedSyllabusCategory by remember { mutableStateOf("All") }
    val syllabusCategories = listOf("All", "Paper I", "Paper II")

    val notes = remember {
        listOf(
            StudyTopic(
                "Emulsions & Suspensions Formulation", "Paper II (Pharmaceutics)",
                "• Emulsions are biphasic liquid formulations where one liquid is dispersed as tiny droplets inside another (O/W or W/O). Emulsifying agents (e.g., Acacia, Tragacanth) reduce interfacial tension to prevent cracking.\n• Suspensions are biphasic systems containing insoluble solid particles dispersed in a liquid vehicle. Flocculating agents form loose network-like aggregates (flocs) that settle rapidly but are easily redispersed upon gentle shaking.",
                "Clinical Tip: Always place a 'SHAKE WELL BEFORE USE' auxiliary label on suspensions to ensure dose uniformity.",
                "Exam Question: State the primary difference between flocculated and deflocculated suspensions."
            ),
            StudyTopic(
                "Mechanism of Beta Adrenergic Blockers", "Paper II (Pharmacology)",
                "• Beta-1 blockers (Atenolol, Metoprolol) act selectively on heart receptors to decrease cardiac output, heart rate, and renin secretion.\n• Beta-2 receptors are in pulmonary bronchial smooth muscles. Non-selective beta-blockers (Propranolol) block both Beta-1 and Beta-2, which can cause severe, life-threatening bronchoconstriction in asthmatic patients.",
                "Clinical Tip: Propranolol is absolutely contraindicated in patients suffering from active Bronchial Asthma.",
                "Exam Question: Why are selective Beta-1 blockers preferred over non-selective ones in hypertensive asthmatic patients?"
            ),
            StudyTopic(
                "Alkaloids & Glycosides Natural Sources", "Paper II (Pharmacognosy)",
                "• Alkaloids: Basic nitrogenous compounds of plant origin (e.g. Quinine from Cinchona ledgeriana cortex, Atropine from Atropa belladonna leaves). They form precipitates with Mayer's and Dragendorff's chemical reagents.\n• Glycosides: Contain an organic sugar group (glycone) bound to a non-sugar active molecule (aglycone). E.g., Digitalis (cardiac glycoside from Digitalis purpurea leaves) used in cardiac congestive heart failure treatment.",
                "Clinical Tip: Atropine is a powerful physiological antidote used to treat poisoning from organophosphate insecticides.",
                "Exam Question: Name the botanical source and active alkaloid of Cinchona bark."
            ),
            StudyTopic(
                "Autoclave & Dry Heat Sterilization Mechanics", "Paper I (Microbiology)",
                "• Autoclave (Moist Heat): Uses saturated steam under high pressure. Standard settings are 121°C for 15-20 minutes. It coagulates and denatures essential bacterial cellular proteins.\n• Hot Air Oven (Dry Heat): Employs high temperatures without moisture. Standard protocol is 160°C for 2 hours. Destroys microbes by thermal oxidation of intracellular materials.",
                "Clinical Tip: Surgical dressings, syringes, and glass vials must be thoroughly sterilized to prevent systemic hospital-acquired infections.",
                "Exam Question: Why is moist heat sterilization more rapid and effective than dry heat at equivalent temperatures?"
            ),
            StudyTopic(
                "Systemic Circulations & Cardiac Valves", "Paper I (Anatomy & Physiology)",
                "• Systemic circulation carries oxygen-rich arterial blood from the left ventricle through the aorta to the body, returning deoxygenated venous blood to the right atrium.\n• Tricuspid valve guards the right atrium-ventricle opening; Bicuspid (Mitral) valve guards the left. Semilunar valves prevent backflow into the ventricles from the aorta and pulmonary artery.",
                "Clinical Tip: Damaged or stenotic mitral valves heavily restrict left ventricular ejection capacity, leading to pulmonary congestion.",
                "Exam Question: Trace the path of blood starting from the Superior Vena Cava up to the Ascending Aorta."
            )
        )
    }

    val filteredNotes = if (selectedSyllabusCategory == "All") {
        notes
    } else {
        notes.filter { it.paperSection.contains(selectedSyllabusCategory) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Filter Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            syllabusCategories.forEach { category ->
                val isSelected = selectedSyllabusCategory == category
                ElevatedFilterChip(
                    selected = isSelected,
                    onClick = { selectedSyllabusCategory = category },
                    label = { Text(category, fontSize = 12.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Notes List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredNotes) { topic ->
                var isExpanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = !isExpanded },
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = topic.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = topic.paperSection,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            }
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        if (isExpanded) {
                            Divider(modifier = Modifier.padding(vertical = 4.dp))
                            Text(
                                text = topic.content,
                                fontSize = 12.5.sp,
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            // Clinical Highlight box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = topic.clinicalPearls,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1B5E20)
                                )
                            }

                            // Typical Exam Question box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFFFFDE7), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Text(
                                        "❓ TYPICAL EXAM QUESTION:",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFFF57F17)
                                    )
                                    Text(
                                        text = topic.typicalExamQuestion,
                                        fontSize = 11.sp,
                                        color = Color.DarkGray
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

/**
 * 3. PAKISTAN DRUG LAW & ETHICS HUB (Category B Specifics)
 */
@Composable
fun PakistanDrugLawHubView() {
    val laws = remember {
        listOf(
            Pair("Registration of Pharmacy Apprentices / Technicians", "Under Section 24 of the Pharmacy Act 1967, Category B registration is designated for 'Pharmacy Assistants'. This allows the holder to legally practice dispensing under standard rules set by the provincial Pharmacy Council (e.g., Punjab Pharmacy Council)."),
            Pair("Retail Pharmacy License Requirements", "According to Punjab/Federal Drug Rules, a retail pharmacy shop license (Form 9) requires a qualified person registered under Category A (Pharmacist) or Category B (Pharmacy Assistant) to be physically present at the premises during all operational hours to supervise compounding and dispensing of narcotic/controlled drugs."),
            Pair("Warranty Form 5 (Drug Act 1976)", "Every manufacturer or registered importer selling drug products to retailers must execute a warranty of quality under Form 5. This legally protects the retailer from prosecution if the drug is later found to be sub-standard or adulterated, provided it was stored properly."),
            Pair("Powers of Federal & Provincial Drug Inspectors", "Under Section 18 of the Drug Act 1976, inspectors have the authority to enter and search any licensed premises, seize samples of suspicious drug products, inspect manufacturing logs, and lock and seal premises violating drug schedules."),
            Pair("Schedules of the Drug Rules", "• Schedule B: Standard requirements for physical space, ventilation, hygienic standards, and laboratory equipment of pharmacies.\n• Schedule G: Lists dangerous and controlled narcotic drugs requiring a locked cabinet and double-receipt carbon-copy records with prescription details retained for a minimum of 2 years.")
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Pakistan Drug Act 1976 Study Hub",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "High-yield laws, rules, and licensing codes for the Paper-II Law portion.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        items(laws) { (title, description) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.VerifiedUser, null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Divider(color = Color.LightGray.copy(alpha = 0.4f))
                    Text(
                        text = description,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

/**
 * 4. PHARMACEUTICAL CALCULATIONS PRACTICE TRAINER
 */
@Composable
fun PharmaceuticalCalculationsTrainer() {
    val context = LocalContext.current

    val problems = remember {
        listOf(
            CalcPracticeProblem(
                1, "Pediatric Dose (Young's Rule)",
                "Calculate the dose for a child aged 4 years old, given that the standard adult dose of the drug is 150 mg. (Round to 1 decimal place)",
                37.5, "mg", "Young's Rule: Child Dose = [Age / (Age + 12)] * Adult Dose",
                "Using the formula: [4 / (4 + 12)] * 150 = [4 / 16] * 150 = 0.25 * 150 = 37.5 mg.",
                "Adult Dose (mg)"
            ),
            CalcPracticeProblem(
                2, "Percentage Solution Compounding",
                "How many grams of Dextrose powder are needed to prepare 250 mL of a 5% w/v Dextrose solution?",
                12.5, "g", "Weight (g) = [Percentage (%) * Volume (mL)] / 100",
                "5% w/v means 5 grams per 100 mL. For 250 mL: (5 * 250) / 100 = 12.5 grams.",
                "Target Volume (mL)"
            ),
            CalcPracticeProblem(
                3, "Pediatric Dose by Weight (Clark's Rule)",
                "Using Clark's Rule, calculate the pediatric dose for a child weighing 45 lbs, given that the average adult dose of the drug is 100 mg. (Round to 1 decimal place)",
                30.0, "mg", "Clark's Rule: Child Dose = [Weight (lbs) / 150] * Adult Dose",
                "Using Clark's formula: (45 / 150) * 100 = 0.3 * 100 = 30 mg.",
                "Adult Dose (mg)"
            )
        )
    }

    var selectedProblemId by remember { mutableStateOf(1) }
    var userInputText by remember { mutableStateOf("") }
    var hasAnswered by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf(false) }

    val activeProblem = problems.first { it.id == selectedProblemId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Selector Chips
        Text(
            "Select Calculation Formula:",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            problems.forEach { problem ->
                val isSelected = selectedProblemId == problem.id
                ElevatedFilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedProblemId = problem.id
                        userInputText = ""
                        hasAnswered = false
                    },
                    label = { Text(problem.title, fontSize = 11.sp) }
                )
            }
        }

        // Active Problem Description
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "✏️ PROBLEM STATEMENT:",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = activeProblem.prompt,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                Divider(color = Color.LightGray.copy(alpha = 0.5f))
                Text(
                    text = "🔧 Formula: ${activeProblem.formula}",
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        // User Input & Actions
        OutlinedTextField(
            value = userInputText,
            onValueChange = { userInputText = it.filter { char -> char.isDigit() || char == '.' } },
            label = { Text("Enter calculated value (${activeProblem.unit})") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            suffix = { Text(activeProblem.unit) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !hasAnswered
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (!hasAnswered) {
                Button(
                    onClick = {
                        val parsed = userInputText.toDoubleOrNull()
                        if (parsed == null) {
                            Toast.makeText(context, "Please enter a valid numeric value!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isAnswerCorrect = Math.abs(parsed - activeProblem.correctAnswer) < 0.15
                        hasAnswered = true
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.DoneOutline, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Verify Answer")
                }
            } else {
                Button(
                    onClick = {
                        userInputText = ""
                        hasAnswered = false
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Refresh, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Retry Problem")
                }
            }
        }

        // feedback block
        if (hasAnswered) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isAnswerCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                ),
                border = BorderStroke(1.dp, if (isAnswerCorrect) Color(0xFF4CAF50) else Color(0xFFE53935))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isAnswerCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                            contentDescription = null,
                            tint = if (isAnswerCorrect) Color(0xFF2E7D32) else Color(0xFFC62828),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isAnswerCorrect) "EXCELLENT! ANSWER CORRECT" else "INCORRECT CALCULATION",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = if (isAnswerCorrect) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                    }

                    Text(
                        text = "Correct Answer: ${activeProblem.correctAnswer} ${activeProblem.unit}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Divider(color = Color.LightGray.copy(alpha = 0.5f))

                    Text(
                        text = "Step-by-step Solution:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = activeProblem.explanation,
                        fontSize = 12.5.sp,
                        lineHeight = 18.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
