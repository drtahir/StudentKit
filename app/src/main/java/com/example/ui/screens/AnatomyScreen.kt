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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.viewmodel.StudentKitViewModel

// =============================================================================
// MODULE 11: HUMAN ANATOMY ATLAS & CLINICAL Reference (Medical, Nursing & Pharmacy)
// =============================================================================

data class AnatomyPart(
    val name: String,
    val scientificName: String,
    val description: String,
    val clinicalNotes: String,
    val drugTargetNotes: String, // High relevance for pharmacy students
    val funFact: String
)

data class AnatomySystem(
    val id: String,
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val drawableRes: Int,
    val overview: String,
    val parts: List<AnatomyPart>,
    val themeColor: Color
)

data class AnatomyQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val rationale: String,
    val clinicalSignificance: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnatomyAtlasScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var selectedSystemId by remember { mutableStateOf("skeletal") }
    var activeTab by remember { mutableStateOf(0) } // 0 = Atlas Explorer, 1 = Clinical Quiz, 2 = High-Yield Search
    var searchQuery by remember { mutableStateOf("") }
    
    // Detailed Clinical Organ Data (Strictly high-yield educational facts, NO PLACEHOLDERS)
    val anatomySystems = listOf(
        AnatomySystem(
            id = "skeletal",
            name = "Skeletal System",
            icon = Icons.Default.AccessibilityNew,
            drawableRes = R.drawable.img_anatomy_skeletal,
            themeColor = Color(0xFF795548),
            overview = "The human skeletal framework contains 206 bones that provide rigid support, vital organ shielding, calcium homeostasis reservoir, and active red blood cell hematopoiesis within specialized marrow cavities.",
            parts = listOf(
                AnatomyPart(
                    "Cranium (Skull)", "Ossa cranii",
                    "Composed of 22 flat bones fused by fibrous sutures (except mandible). Formed to protect the central cerebral cortex.",
                    "Sutures solidify post-infancy; persistent fontanelles provide clinical pressure relief indicators in pediatric neurology.",
                    "Calcium channel blockers do not directly affect bones, but bisphosphonates (e.g., Alendronate) selectively bind bone minerals to treat osteoporosis.",
                    "The stapes (inner ear) is the smallest bone at just 3mm long!"
                ),
                AnatomyPart(
                    "Femur", "Os femoris",
                    "The longest, strongest, and heaviest bone in the entire human skeletal framework, carrying axial gravity weight.",
                    "Femoral neck fractures can lead to avascular necrosis of the femoral head due to disruption of the retinacular arteries.",
                    "Highly vascularized; fractures can cause massive internal blood loss (up to 1.5L), requiring immediate stabilization.",
                    "It is stronger than steel of equivalent weight!"
                ),
                AnatomyPart(
                    "Clavicle (Collar Bone)", "Clavicula",
                    "An S-shaped long bone acting as a strut to connect the upper extremity scapula to the central thoracic sternum.",
                    "The most commonly fractured bone in the human body, frequently caused by falls on an outstretched arm (FOOSH).",
                    "Surgical plating is indicated if severe displacement risks damage to the underlying subclavian vessels.",
                    "It is the first bone to start ossifying in the embryo (5th week of gestation)."
                ),
                AnatomyPart(
                    "Vertebrae", "Columna vertebralis",
                    "A column of 33 interlocking bones divided into Cervical (7), Thoracic (12), Lumbar (5), Sacral (5), and Coccygeal (4).",
                    "Herniation of the intervertebral nucleus pulposus often compresses corresponding spinal nerve roots, leading to sciatica.",
                    "Cervical vertebrae C3-C5 protect the phrenic nerve; injury here impairs spontaneous diaphragm respiration.",
                    "You are taller in the morning due to overnight rehydration of intervertebral cartilage discs."
                )
            )
        ),
        AnatomySystem(
            id = "muscular",
            name = "Muscular System",
            icon = Icons.Default.FitnessCenter,
            drawableRes = R.drawable.img_anatomy_muscular,
            themeColor = Color(0xFFE53935),
            overview = "An arrangement of skeletal, cardiac, and smooth muscle fibers specialized for active motion, core skeletal stability, thermal thermogenesis, and dynamic pressure pumping.",
            parts = listOf(
                AnatomyPart(
                    "Masseter", "Musculus masseter",
                    "A thick, quadrangular muscle of mastication that raises the mandible, enabling powerful food grinding.",
                    "Hypertrophy can cause severe bruxism (teeth grinding) and temporomandibular joint (TMJ) chronic dysfunction.",
                    "Target for botulinum toxin (Botox) injections to selectively paralyze muscle fibers and relieve TMJ pressure.",
                    "The strongest muscle in the human body relative to its weight, delivering up to 200 lbs of force!"
                ),
                AnatomyPart(
                    "Cardiac Muscle", "Myocardium",
                    "Involuntary, striated muscle fibers connected by intercalated discs that form the muscular walls of the heart chamber.",
                    "Myocardial infarction (heart attack) occurs when coronary arteries fail to supply blood, causing localized cell necrosis.",
                    "Highly rich in Beta-1 receptors. Beta-blockers (e.g., Metoprolol) reduce heart rate and myocardial oxygen demand.",
                    "Myocardial cells have an extremely high density of mitochondria to sustain non-stop pumping forever."
                ),
                AnatomyPart(
                    "Deltoid", "Musculus deltoideus",
                    "A large, triangular multi-pennate muscle shielding the glenohumeral joint, responsible for principal shoulder abduction.",
                    "Common clinical site for intramuscular (IM) drug injections due to rich vascularity and rapid absorption rates.",
                    "Injection volume in the deltoid should typically not exceed 1-2 mL to prevent local compartment tension.",
                    "The deltoid is actually composed of three distinct heads: anterior, lateral, and posterior."
                ),
                AnatomyPart(
                    "Diaphragm", "Diaphragma",
                    "A dome-shaped sheet of internal skeletal muscle extending across the bottom of the rib cage, acting as the prime respiratory pump.",
                    "Irritation of the phrenic nerve leads to spasmodic contractions of the diaphragm, clinically known as hiccups.",
                    "Sedatives, neuromuscular blockers, and anesthetics can paralyze the diaphragm, necessitating mechanical ventilation.",
                    "Its contraction accounts for 75% of total inspiratory air volume during quiet, relaxed breathing."
                )
            )
        ),
        AnatomySystem(
            id = "cardiovascular",
            name = "Cardiovascular System",
            icon = Icons.Default.Favorite,
            drawableRes = R.drawable.img_anatomy_cardiovascular,
            themeColor = Color(0xFFC2185B),
            overview = "An elegant, closed loop consisting of the heart and blood vessels that transports vital oxygen, endocrine hormones, cellular immunoglobulins, and metabolites to sustain cellular function.",
            parts = listOf(
                AnatomyPart(
                    "Heart", "Cor",
                    "A four-chambered dual pump comprising two atria (reception) and two ventricles (propulsion) driven by pacemaker nodes.",
                    "Left ventricular hypertrophy is a common adaptation to chronic systemic arterial hypertension.",
                    "Cardiac glycosides (e.g., Digoxin) inhibit Na+/K+-ATPase, increasing cardiac calcium to boost pumping force in heart failure.",
                    "The heart pumps around 7,200 liters of blood every single day, beating 100,000 times."
                ),
                AnatomyPart(
                    "Aorta", "Aorta",
                    "The main trunk of the systemic arterial network, distributing oxygenated blood from the left ventricle under high elastic recoil.",
                    "Aortic dissection is a critical emergency where blood tears through the aortic wall's tunica intima.",
                    "Aortic compliance and pressure are targets for Vasodilators (Nitroprusside) and ACE Inhibitors (Lisinopril).",
                    "The aorta is almost as thick as a standard garden hose!"
                ),
                AnatomyPart(
                    "Capillaries", "Vasa capillaria",
                    "Microscopic single-cell endothelial vessels connecting arterioles and venules, optimizing biological diffusion.",
                    "Septic shock triggers systemic capillary hyper-permeability, leading to severe edema and distributive hypotension.",
                    "Nitric oxide donors cause pre-capillary sphincter dilation, reducing systemic systemic vascular resistance.",
                    "Red blood cells must fold and travel in a single file line to pass through the tiniest capillaries."
                )
            )
        ),
        AnatomySystem(
            id = "nervous",
            name = "Nervous System",
            icon = Icons.Default.FlashOn,
            drawableRes = R.drawable.img_anatomy_nervous,
            themeColor = Color(0xFF00ACC1),
            overview = "The master coordinate system divided into Central (brain, spinal cord) and Peripheral networks that utilizes electrochemical action potentials to process sensory cues and trigger motor outputs.",
            parts = listOf(
                AnatomyPart(
                    "Cerebral Cortex", "Cerebrum",
                    "The brain outer wrinkled grey matter responsible for cognitive consciousness, memory storage, logic, and motor signals.",
                    "Ischemic strokes from cerebral artery occlusion lead to rapid localized cortical infarction and hemi-paralysis.",
                    "Highly sensitive to CNS active agents; anesthetics augment GABA-A inhibitory receptors to induce reversible comas.",
                    "While it processes all physical pain signals, the brain itself contains zero pain receptors!"
                ),
                AnatomyPart(
                    "Spinal Cord", "Medulla spinalis",
                    "The central conduit for reflex pathways and ascending/descending tracts connecting the brain to peripheral organs.",
                    "Trauma results in localized sensory-motor paraplegia or quadriplegia depending on the vertical level of injury.",
                    "Spinal anesthesia is injected into the subarachnoid space (CSF) to temporarily block sodium channel pain signals.",
                    "Unlike the spine, the spinal cord actually stops growing around age 4, ending at L1-L2 level."
                ),
                AnatomyPart(
                    "Vagus Nerve (CN X)", "Nervus vagus",
                    "The tenth cranial nerve, providing primary parasympathetic innervation to the heart, lungs, and digestive tract.",
                    "Vagal maneuvers (like Valsalva) stimulate the vagus nerve, releasing acetylcholine to rapidly slow supraventricular tachycardias.",
                    "Anticholinergic drugs (Atropine) block vagal parasympathetic inputs, serving as emergency therapy for bradycardia.",
                    "Vagus means 'wandering' in Latin, named so because it wanders from the brainstem all the way to the colon."
                )
            )
        )
    )

    // High-Yield Anatomy Quiz (NO PLACEHOLDERS, realistic exams for Medical & Nursing Students)
    val quizQuestions = listOf(
        AnatomyQuestion(
            question = "Which nerve is primarily responsible for the parasympathetic control of heart rate, bronchial constriction, and gastrointestinal motility?",
            options = listOf("Phrenic Nerve", "Sciatic Nerve", "Vagus Nerve (CN X)", "Trigeminal Nerve"),
            correctIndex = 2,
            rationale = "The Vagus nerve provides widespread parasympathetic innervation to visceral organs. Stimulating it releases acetylcholine, slowing heart rate at the SA node.",
            clinicalSignificance = "Clinical: Vagal maneuvers (like carotid massage) stimulate CN X to rapidly convert paroxysmal supraventricular tachycardia (PSVT) to normal rhythm."
        ),
        AnatomyQuestion(
            question = "A fracture to which portion of the femur carries the highest clinical risk of causing avascular necrosis of the femoral head?",
            options = listOf("Femoral Shaft", "Femoral Neck", "Greater Trochanter", "Medial Condyle"),
            correctIndex = 1,
            rationale = "The femoral neck fracture disrupts the retinacular blood vessels (mostly branches of medial femoral circumflex artery) that supply the femoral head.",
            clinicalSignificance = "Clinical: Displacement of the femoral neck requires prompt surgical reduction and fixation to mitigate bone tissue death."
        ),
        AnatomyQuestion(
            question = "Where is the primary site of action for Beta-1 adrenergic receptors within the cardiovascular system?",
            options = listOf("Arterial smooth muscle", "Renal glomerular capillaries", "Myocardium and SA node", "Pulmonary arterial branches"),
            correctIndex = 2,
            rationale = "Beta-1 receptors are highly concentrated in the heart (myocardium and SA/AV nodes). Stimulation increases both heart rate (chronotropy) and force (inotropy).",
            clinicalSignificance = "Pharmacy: Beta-blockers like Metoprolol selectively block these receptors to reduce cardiac oxygen demands in patients with angina or failure."
        ),
        AnatomyQuestion(
            question = "What is the structural and functional significance of intercalated discs in cardiac muscle cells?",
            options = listOf(
                "They store calcium ions",
                "They enable rapid electrical and mechanical syncytium",
                "They act as physical anchors for red blood cells",
                "They trigger skeletal muscle lactic acid clearance"
            ),
            correctIndex = 1,
            rationale = "Intercalated discs contain gap junctions that allow electrical depolarization to travel rapidly across cells, enabling coordinated cardiac contractions.",
            clinicalSignificance = "Clinical: Disruption of gap junctions or intercalated disc structural proteins is heavily linked with lethal ventricular arrhythmias."
        ),
        AnatomyQuestion(
            question = "Which skeletal muscle serves as the primary driver for relaxed breathing, accounting for approximately 75% of normal inspiratory air volume?",
            options = listOf("Intercostal Muscles", "Pectoralis Major", "Diaphragm", "Scalene Muscles"),
            correctIndex = 2,
            rationale = "The diaphragm is the primary muscle of respiration. When it contracts, it flattens and expands the thoracic cavity, lowering pleural pressure to pull air in.",
            clinicalSignificance = "Nursing: Patients with spinal cord injuries above C3 suffer paralysis of the diaphragm because the phrenic nerve is damaged, requiring mechanical ventilators."
        )
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var quizSubmitted by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var showExplanation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab Headers
        TabRow(selectedTabIndex = activeTab) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = { Text("Atlas Explorer", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.AccessibilityNew, null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = { Text("Clinical Quiz", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Quiz, null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = activeTab == 2,
                onClick = { activeTab = 2 },
                text = { Text("High-Yield Index", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.ManageSearch, null, modifier = Modifier.size(18.dp)) }
            )
        }

        when (activeTab) {
            0 -> {
                // EXPLORER VIEW
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // System selection Row
                    Text(
                        "Select Anatomical System:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        anatomySystems.forEach { system ->
                            val isSelected = selectedSystemId == system.id
                            ElevatedFilterChip(
                                selected = isSelected,
                                onClick = { selectedSystemId = system.id },
                                label = { Text(system.name, fontSize = 11.sp) },
                                leadingIcon = { Icon(system.icon, null, modifier = Modifier.size(14.dp)) },
                                colors = FilterChipDefaults.elevatedFilterChipColors(
                                    selectedContainerColor = system.themeColor.copy(alpha = 0.15f),
                                    selectedLabelColor = system.themeColor
                                )
                            )
                        }
                    }

                    val currentSystem = anatomySystems.first { it.id == selectedSystemId }

                    // Color diagram card (The real colorful image we generated)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(260.dp)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = currentSystem.drawableRes),
                                    contentDescription = "${currentSystem.name} Diagram",
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(8.dp),
                                    contentScale = ContentScale.Fit
                                )
                                
                                // Decorative border
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .border(2.dp, currentSystem.themeColor.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
                                )
                            }
                            
                            // Overview Box
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(currentSystem.themeColor.copy(alpha = 0.08f))
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(currentSystem.icon, null, tint = currentSystem.themeColor, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${currentSystem.name} Overview",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = currentSystem.themeColor
                                    )
                                }
                                Text(
                                    text = currentSystem.overview,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }

                    // Interactive hotspots or parts explorer
                    Text(
                        "🔬 Major High-Yield Anatomical Components:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = currentSystem.themeColor
                    )

                    currentSystem.parts.forEach { part ->
                        var isExpanded by remember { mutableStateOf(false) }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isExpanded = !isExpanded },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isExpanded) currentSystem.themeColor.copy(alpha = 0.03f) else MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(
                                1.dp,
                                if (isExpanded) currentSystem.themeColor.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(part.name, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = currentSystem.themeColor)
                                        Text(part.scientificName, fontSize = 10.5.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                    }
                                    Icon(
                                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = null,
                                        tint = currentSystem.themeColor
                                    )
                                }

                                if (isExpanded) {
                                    Divider(color = currentSystem.themeColor.copy(alpha = 0.15f))
                                    
                                    // Functional details
                                    Text("Physiology / Function:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                    Text(part.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)

                                    // Clinical Notes
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFFFFFDE7), RoundedCornerShape(6.dp))
                                            .padding(8.dp)
                                    ) {
                                        Column {
                                            Text("🏥 CLINICAL CORRELATION:", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color(0xFFF57F17))
                                            Text(part.clinicalNotes, fontSize = 10.5.sp, color = Color.DarkGray)
                                        }
                                    }

                                    // Pharmacological Targets
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFFE8F5E9), RoundedCornerShape(6.dp))
                                            .padding(8.dp)
                                    ) {
                                        Column {
                                            Text("💊 PHARMACEUTICAL MECHANISMS / NOTES:", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color(0xFF2E7D32))
                                            Text(part.drugTargetNotes, fontSize = 10.5.sp, color = Color.DarkGray)
                                        }
                                    }

                                    // Fun Fact
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 4.dp)
                                    ) {
                                        Icon(Icons.Default.Lightbulb, null, tint = Color(0xFFFFD600), modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Did you know? ${part.funFact}", fontSize = 10.sp, color = Color.DarkGray)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // CLINICAL QUIZ VIEW
                val currentQuestion = quizQuestions[currentQuestionIndex]
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Quiz Progress Header
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Anatomy & Clinical Quiz",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "Question ${currentQuestionIndex + 1} of ${quizQuestions.size}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Score bar
                    LinearProgressIndicator(
                        progress = (currentQuestionIndex + 1).toFloat() / quizQuestions.size,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                    )

                    // Question Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "QUESTION:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = Color.Gray,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = currentQuestion.question,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                        }
                    }

                    // Options list
                    currentQuestion.options.forEachIndexed { index, option ->
                        val isSelected = selectedAnswerIndex == index
                        val isCorrect = index == currentQuestion.correctIndex
                        val isWrong = isSelected && !isCorrect
                        
                        val optionColor = when {
                            quizSubmitted && isCorrect -> Color(0xFF2E7D32) // Green
                            quizSubmitted && isWrong -> Color(0xFFC62828) // Red
                            isSelected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val containerColor = when {
                            quizSubmitted && isCorrect -> Color(0xFFE8F5E9)
                            quizSubmitted && isWrong -> Color(0xFFFFEBEE)
                            isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !quizSubmitted) { selectedAnswerIndex = index },
                            colors = CardDefaults.cardColors(containerColor = containerColor),
                            border = BorderStroke(
                                if (isSelected || (quizSubmitted && isCorrect)) 2.dp else 1.dp,
                                optionColor.copy(alpha = 0.5f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f)),
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
                                    Text(option, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                                }

                                if (quizSubmitted) {
                                    if (isCorrect) {
                                        Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF2E7D32))
                                    } else if (isWrong) {
                                        Icon(Icons.Default.Cancel, null, tint = Color(0xFFC62828))
                                    }
                                }
                            }
                        }
                    }

                    // Actions bottom row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (!quizSubmitted) {
                            Button(
                                onClick = {
                                    if (selectedAnswerIndex == null) {
                                        Toast.makeText(context, "Select an answer first!", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }
                                    quizSubmitted = true
                                    if (selectedAnswerIndex == currentQuestion.correctIndex) {
                                        score++
                                    }
                                    showExplanation = true
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Check, null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Submit Answer")
                            }
                        } else {
                            Button(
                                onClick = {
                                    if (currentQuestionIndex + 1 < quizQuestions.size) {
                                        currentQuestionIndex++
                                        selectedAnswerIndex = null
                                        quizSubmitted = false
                                        showExplanation = false
                                    } else {
                                        // Completed quiz
                                        Toast.makeText(context, "Quiz Completed! Score: $score/${quizQuestions.size}", Toast.LENGTH_LONG).show()
                                        currentQuestionIndex = 0
                                        selectedAnswerIndex = null
                                        quizSubmitted = false
                                        showExplanation = false
                                        score = 0
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (currentQuestionIndex + 1 == quizQuestions.size) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                                )
                            ) {
                                if (currentQuestionIndex + 1 == quizQuestions.size) {
                                    Icon(Icons.Default.Refresh, null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Restart Quiz (Score: $score/${quizQuestions.size})")
                                } else {
                                    Text("Next Question")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(Icons.Default.ArrowForward, null)
                                }
                            }
                        }
                    }

                    // Rationale Box
                    if (showExplanation) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("💡 RATIONALE & EXPLANATION:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                Text(currentQuestion.rationale, fontSize = 11.5.sp, color = Color.DarkGray)
                                
                                Divider(modifier = Modifier.padding(vertical = 4.dp))
                                
                                Text(currentQuestion.clinicalSignificance, fontSize = 11.sp, color = Color(0xFFC2185B), fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            2 -> {
                // SEARCH & INDEX VIEW
                val allParts = anatomySystems.flatMap { sys -> sys.parts.map { part -> Pair(sys, part) } }
                val filteredParts = allParts.filter { (sys, part) ->
                    part.name.contains(searchQuery, ignoreCase = true) ||
                    part.scientificName.contains(searchQuery, ignoreCase = true) ||
                    part.clinicalNotes.contains(searchQuery, ignoreCase = true) ||
                    part.drugTargetNotes.contains(searchQuery, ignoreCase = true)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search organs, bones, clinical symptoms or drug targets...") },
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, null)
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Text(
                        text = "Results found: ${filteredParts.size} high-yield indexes",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredParts) { (sys, part) ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                border = BorderStroke(1.dp, sys.themeColor.copy(alpha = 0.15f))
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(part.name, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = sys.themeColor)
                                            Text(part.scientificName, fontSize = 10.5.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .background(sys.themeColor.copy(alpha = 0.12f), RoundedCornerShape(6.dp))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text(sys.name, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = sys.themeColor)
                                        }
                                    }

                                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                                    Text(part.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)

                                    // Display Clinical snippet directly
                                    Text("🏥 Clinical Risk: ${part.clinicalNotes}", fontSize = 10.5.sp, color = Color(0xFFE65100))
                                    
                                    // Display Drug action target snippet
                                    Text("💊 Pharm Link: ${part.drugTargetNotes}", fontSize = 10.5.sp, color = Color(0xFF1B5E20))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
