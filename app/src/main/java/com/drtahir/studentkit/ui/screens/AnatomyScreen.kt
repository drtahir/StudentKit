package com.drtahir.studentkit.ui.screens

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
import androidx.compose.ui.graphics.graphicsLayer
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
import com.drtahir.studentkit.R
import com.drtahir.studentkit.viewmodel.StudentKitViewModel

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
        ),
        AnatomySystem(
            id = "respiratory",
            name = "Respiratory System",
            icon = Icons.Default.Air,
            drawableRes = R.drawable.img_anatomy_respiratory,
            themeColor = Color(0xFF0288D1),
            overview = "An intricate pulmonary network responsible for atmospheric oxygen uptake, carbon dioxide excretion, blood gas homeostasis, and acid-base buffering.",
            parts = listOf(
                AnatomyPart(
                    "Lungs & Alveoli", "Pulmones & Alveoli pulmonis",
                    "Dual spongy organs packed with 300 million micro-sacs (alveoli) optimized for gas diffusion across thin Type I pneumocytes.",
                    "Premature infants lack Type II pneumocyte surfactant, causing alveolar collapse (Infant Respiratory Distress Syndrome).",
                    "Beta-2 agonists (Albuterol) relax bronchial smooth muscle during acute asthma exacerbations.",
                    "If unfolded flat, the total surface area of both lungs would cover an entire tennis court!"
                ),
                AnatomyPart(
                    "Trachea & Carina", "Trachea & Carina tracheae",
                    "A cartilaginous air conduit bifurcating at the carina into right and left primary mainstem bronchi.",
                    "Aspirated foreign objects enter the right mainstem bronchus more frequently due to its wider and more vertical angle.",
                    "Inhaled corticosteroids reduce airway mucosal inflammation in chronic asthma and COPD.",
                    "The trachea is reinforced by 16-20 C-shaped hyaline cartilage rings to prevent collapse during forced coughing."
                )
            )
        ),
        AnatomySystem(
            id = "digestive",
            name = "Digestive System",
            icon = Icons.Default.Restaurant,
            drawableRes = R.drawable.img_anatomy_digestive,
            themeColor = Color(0xFFF57C00),
            overview = "A continuous gastrointestinal tract spanning from mouth to anus that mechanically breaks down food, hydrolyzes macromolecules, absorbs nutrients, and excretes indigestible waste.",
            parts = listOf(
                AnatomyPart(
                    "Stomach", "Ventriculus / Gaster",
                    "A muscular J-shaped reservoir that churns chyme and secretes gastric HCl acid and pepsinogen for protein digestion.",
                    "Autoimmune destruction of parietal cells causes Pernicious Anemia due to lack of Intrinsic Factor for Vitamin B12 absorption.",
                    "Proton Pump Inhibitors (Omeprazole) irreversibly block H+/K+-ATPase pumps to treat peptic ulcer disease and GERD.",
                    "The stomach lining completely replaces its epithelial cells every 3 to 5 days to prevent self-digestion by gastric acid!"
                ),
                AnatomyPart(
                    "Liver & Portal Vein", "Hepar & Vena portae hepatis",
                    "The master metabolic organ synthesizing plasma proteins, filtering portal venous blood, and secreting bile for fat emulsification.",
                    "Cirrhosis causes portal hypertension, leading to esophageal varices, caput medusae, and hepatic encephalopathy.",
                    "Statins (Atorvastatin) inhibit HMG-CoA reductase in hepatocytes to lower circulating LDL cholesterol.",
                    "The liver is the only internal organ capable of full biological regeneration from as little as 25% of its original tissue!"
                )
            )
        ),
        AnatomySystem(
            id = "urinary",
            name = "Renal & Urinary System",
            icon = Icons.Default.WaterDrop,
            drawableRes = R.drawable.img_anatomy_urinary,
            themeColor = Color(0xFF7B1FA2),
            overview = "Dual kidneys and urinary passages that filter blood plasma, maintain fluid-electrolyte equilibrium, control arterial pressure via RAAS, and excrete metabolic nitrogenous wastes.",
            parts = listOf(
                AnatomyPart(
                    "Kidney & Nephron", "Ren & Nephronum",
                    "Over 1 million filtering nephrons per kidney consisting of glomeruli and tubular segments that generate ~180L of filtrate daily.",
                    "Diabetic nephropathy causes nodular glomerulosclerosis (Kimmelstiel-Wilson lesions), leading to microalbuminuria.",
                    "Loop diuretics (Furosemide) inhibit the Na+/K+/2Cl- cotransporter in the thick ascending loop of Henle.",
                    "Although kidneys account for only 0.5% of total body weight, they receive 20-25% of total cardiac blood flow!"
                ),
                AnatomyPart(
                    "Urinary Bladder", "Vesica urinaria",
                    "A hollow muscular sac lined with transitional epithelium (urothelium) that expands to hold 400-600 mL of urine.",
                    "Neurogenic bladder from spinal trauma can lead to urinary retention, vesicoureteral reflux, and pyelonephritis.",
                    "Antimuscarinics (Oxybutynin) block detrusor muscle M3 receptors to treat overactive bladder incontinence.",
                    "Transitional urothelium can stretch from 6 cell layers deep down to just 2 layers when the bladder is full!"
                )
            )
        ),
        AnatomySystem(
            id = "endocrine",
            name = "Endocrine System",
            icon = Icons.Default.AutoAwesome,
            drawableRes = R.drawable.img_anatomy_endocrine,
            themeColor = Color(0xFFD81B60),
            overview = "Ductless glands secreting chemical messengers (hormones) directly into systemic blood circulation to regulate growth, basal metabolism, glucose dynamics, and reproductive cycles.",
            parts = listOf(
                AnatomyPart(
                    "Pituitary Gland", "Hypophysis",
                    "The 'master gland' housed in the sella turcica, divided into anterior (adenohypophysis) and posterior (neurohypophysis) lobes.",
                    "Pituitary macroadenomas compress the overlying optic chiasm, producing classic bitemporal hemianopia (tunnel vision).",
                    "Levothyroxine synthesizes synthetic T4 to replace thyroid deficiency secondary to pituitary TSH impairment.",
                    "The pituitary gland is no larger than the size of a single pea, yet controls almost every gland in the body!"
                ),
                AnatomyPart(
                    "Thyroid Gland", "Glandula thyroidea",
                    "A butterfly-shaped endocrine gland in the neck producing T3, T4 for metabolic rate and Calcitonin for calcium regulation.",
                    "Graves' disease involves TSH receptor autoantibodies causing hyperthyroidism, exophthalmos, and pretibial myxedema.",
                    "Antithyroid drugs (Methimazole, PTU) inhibit thyroid peroxidase to suppress excessive thyroid hormone synthesis.",
                    "The thyroid gland stores several months' worth of precursor thyroid hormones inside colloidal follicles!"
                )
            )
        ),
        AnatomySystem(
            id = "lymphatic",
            name = "Lymphatic & Immune System",
            icon = Icons.Default.Shield,
            drawableRes = R.drawable.img_anatomy_lymphatic,
            themeColor = Color(0xFF388E3C),
            overview = "A specialized network of vessels, lymph nodes, spleen, and thymus that returns interstitial fluid to blood and orchestrates cellular and humoral immunity against pathogens.",
            parts = listOf(
                AnatomyPart(
                    "Spleen", "Lien / Splen",
                    "The largest secondary lymphoid organ, containing Red Pulp (RBC filtration) and White Pulp (B/T cell immunity).",
                    "Splenectomy or auto-splenectomy in sickle cell disease creates extreme susceptibility to encapsulated bacteria (Pneumococcus).",
                    "Immunimmunosuppressants (Cyclosporine) block T-cell activation by inhibiting calcineurin signaling.",
                    "The spleen filters approximately 200 liters of blood per day and stores 1/3 of the body's platelets!"
                )
            )
        ),
        AnatomySystem(
            id = "integumentary",
            name = "Integumentary System",
            icon = Icons.Default.Layers,
            drawableRes = R.drawable.img_anatomy_integumentary,
            themeColor = Color(0xFF8D6E63),
            overview = "The human body's largest organ system, comprising skin, hair, nails, and exocrine glands that protects against trauma, pathogens, and UV radiation while regulating temperature.",
            parts = listOf(
                AnatomyPart(
                    "Epidermis & Dermis", "Epidermis & Dermis",
                    "Stratified squamous epithelium containing keratinocytes, melanocytes, and Langerhans immune cells resting on vascular dermis.",
                    "Psoriasis causes parakeratosis and epidermal hyperplasia presenting with silvery scaly plaques on extensor surfaces.",
                    "Topical corticosteroids suppress local dermal immune cell activation in eczema and contact dermatitis.",
                    "Your skin sheds approximately 30,000 to 40,000 dead skin cells every single minute!"
                )
            )
        ),
        AnatomySystem(
            id = "reproductive",
            name = "Reproductive System",
            icon = Icons.Default.FavoriteBorder,
            drawableRes = R.drawable.img_anatomy_reproductive,
            themeColor = Color(0xFFE91E63),
            overview = "Internal and external genital organs responsible for gametogenesis, sex steroid synthesis, fertilization, embryonic gestation, and species preservation.",
            parts = listOf(
                AnatomyPart(
                    "Ovaries & Uterus", "Ovaria & Uterus",
                    "Female gonads producing ova, estrogen, and progesterone alongside a muscular uterine incubator lined by endometrium.",
                    "Polycystic Ovary Syndrome (PCOS) involves LH hypersecretion, anovulation, hyperandrogenism, and insulin resistance.",
                    "Combined oral contraceptives deliver synthetic estrogen/progesterone to suppress pituitary LH/FSH and block ovulation.",
                    "At birth, a human female possesses all 1-2 million lifetime eggs she will ever have!"
                )
            )
        ),
        AnatomySystem(
            id = "sensory",
            name = "Sensory & Special Senses",
            icon = Icons.Default.Visibility,
            drawableRes = R.drawable.img_anatomy_sensory,
            themeColor = Color(0xFF1976D2),
            overview = "Complex sensory apparatuses (eyes, ears, taste buds, olfactory mucosa) converting light waves, sound vibrations, and chemical stimuli into neural action potentials.",
            parts = listOf(
                AnatomyPart(
                    "Retina & Fovea", "Retina & Fovea centralis",
                    "Neural tissue lining the posterior eye containing photoreceptors (Rods for light/motion, Cones for high-acuity color vision).",
                    "Glaucoma causes progressive optic nerve damage and tunnel vision due to impaired aqueous humor drainage and high IOP.",
                    "Prostaglandin analogs (Latanoprost) increase uveoscleral outflow of aqueous humor to lower intraocular pressure.",
                    "The human eye can distinguish over 10 million distinct color shades!"
                )
            )
        )
    )

    // 500+ High-Yield Clinical Anatomy Questions from Repository
    val quizQuestions = remember { AnatomyQuestionBank.getAllQuestions() }

    // Bookmarked items & state tracking
    val bookmarkedQuestions = remember { mutableStateListOf<Int>() }
    val bookmarkedParts = remember { mutableStateListOf<String>() }
    val flashcardRatingMap = remember { mutableStateMapOf<String, String>() } // partName -> rating
    
    // Quiz State
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var quizSubmitted by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var showExplanation by remember { mutableStateOf(false) }
    var quizSystemFilter by remember { mutableStateOf("all") }
    
    // Filtered Quiz list based on filter
    val activeQuizList = remember(quizSystemFilter) {
        if (quizSystemFilter == "all") quizQuestions
        else quizQuestions.filter { q -> 
            val sysName = anatomySystems.find { it.id == quizSystemFilter }?.name ?: ""
            q.question.contains(sysName, ignoreCase = true) || q.rationale.contains(sysName, ignoreCase = true)
        }.ifEmpty { quizQuestions }
    }

    // Spotter Mode State
    var isSpotterMode by remember { mutableStateOf(false) }
    var spotterSelectedIndex by remember { mutableStateOf<Int?>(null) }
    
    // Flashcard State
    var flashcardIndex by remember { mutableStateOf(0) }
    var isCardFlipped by remember { mutableStateOf(false) }
    var selectedFlashcardSystemId by remember { mutableStateOf("all") }
    
    val allFlashcards = remember(selectedFlashcardSystemId) {
        if (selectedFlashcardSystemId == "all") {
            anatomySystems.flatMap { sys -> sys.parts.map { part -> Pair(sys, part) } }
        } else {
            anatomySystems.filter { it.id == selectedFlashcardSystemId }.flatMap { sys -> sys.parts.map { part -> Pair(sys, part) } }
        }
    }

    // Clinical Case State
    var activeCaseIndex by remember { mutableStateOf(0) }
    var activeCaseStep by remember { mutableStateOf(0) }
    var caseAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var caseStepSubmitted by remember { mutableStateOf(false) }
    var caseScore by remember { mutableStateOf(0) }

    // Search Query State
    var globalSearchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 5 World-Class Top Tabs Bar
        ScrollableTabRow(
            selectedTabIndex = activeTab,
            edgePadding = 8.dp,
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        ) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = { Text("Atlas & Spotter", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.AccessibilityNew, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = { Text("3D Flashcards", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Style, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeTab == 2,
                onClick = { activeTab = 2 },
                text = { Text("500+ Board Quiz", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Quiz, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeTab == 3,
                onClick = { activeTab = 3 },
                text = { Text("Clinical OSCE", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.LocalHospital, null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeTab == 4,
                onClick = { activeTab = 4 },
                text = { Text("Analytics & Vault", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Analytics, null, modifier = Modifier.size(16.dp)) }
            )
        }

        when (activeTab) {
            0 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
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
                                onClick = { 
                                    selectedSystemId = system.id 
                                    spotterSelectedIndex = null
                                },
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

                    // Color diagram card & Pinpoint Spotter Mode
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column {
                            // Header bar with Spotter Mode Toggle
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(currentSystem.themeColor.copy(alpha = 0.12f))
                                    .padding(horizontal = 14.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(currentSystem.icon, null, tint = currentSystem.themeColor, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = currentSystem.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.5.sp,
                                        color = currentSystem.themeColor
                                    )
                                }

                                FilterChip(
                                    selected = isSpotterMode,
                                    onClick = { 
                                        isSpotterMode = !isSpotterMode 
                                        spotterSelectedIndex = null
                                    },
                                    label = { Text(if (isSpotterMode) "Spotter Active" else "Spotter Mode", fontSize = 10.5.sp, fontWeight = FontWeight.Bold) },
                                    leadingIcon = { Icon(Icons.Default.PinDrop, null, modifier = Modifier.size(12.dp)) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }

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

                                // Interactive Spotter Pins
                                if (isSpotterMode) {
                                    currentSystem.parts.forEachIndexed { idx, part ->
                                        val offsetX = when(idx % 3) {
                                            0 -> 0.35f
                                            1 -> 0.52f
                                            else -> 0.68f
                                        }
                                        val offsetY = when(idx % 2) {
                                            0 -> 0.30f
                                            else -> 0.60f
                                        }

                                        Box(
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            IconButton(
                                                onClick = { spotterSelectedIndex = idx },
                                                modifier = Modifier
                                                    .align(Alignment.TopStart)
                                                    .offset(
                                                        x = (offsetX * 280).dp,
                                                        y = (offsetY * 200).dp
                                                    )
                                                    .background(
                                                        if (spotterSelectedIndex == idx) MaterialTheme.colorScheme.error else currentSystem.themeColor,
                                                        CircleShape
                                                    )
                                                    .size(28.dp)
                                            ) {
                                                Text(
                                                    text = "${idx + 1}",
                                                    color = Color.White,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                                
                                // Decorative border
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .border(2.dp, currentSystem.themeColor.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
                                )
                            }
                            
                            // Overview Box or Spotter Pin details
                            if (isSpotterMode && spotterSelectedIndex != null) {
                                val selectedPart = currentSystem.parts[spotterSelectedIndex!!]
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFFFF3E0))
                                        .padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("🎯 Pin #${spotterSelectedIndex!! + 1}: ${selectedPart.name}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFFE65100))
                                    Text("Scientific: ${selectedPart.scientificName}", fontSize = 11.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = Color.DarkGray)
                                    Text(selectedPart.description, fontSize = 11.sp, color = Color.Black)
                                    Text("🏥 Clinical Risk: ${selectedPart.clinicalNotes}", fontSize = 10.5.sp, color = Color(0xFFC2185B), fontWeight = FontWeight.SemiBold)
                                }
                            } else {
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
                    }

                    // Major High-Yield Anatomical Components Explorer
                    Text(
                        "🔬 Major High-Yield Anatomical Components:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = currentSystem.themeColor
                    )

                    currentSystem.parts.forEach { part ->
                        var isExpanded by remember { mutableStateOf(false) }
                        val isBookmarked = bookmarkedParts.contains(part.name)
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
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(part.name, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = currentSystem.themeColor)
                                        Text(part.scientificName, fontSize = 10.5.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(
                                            onClick = {
                                                if (isBookmarked) bookmarkedParts.remove(part.name)
                                                else bookmarkedParts.add(part.name)
                                            }
                                        ) {
                                            Icon(
                                                if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                                null,
                                                tint = if (isBookmarked) MaterialTheme.colorScheme.primary else Color.Gray,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Icon(
                                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = null,
                                            tint = currentSystem.themeColor
                                        )
                                    }
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // System Filter Chips for Flashcards
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = selectedFlashcardSystemId == "all",
                            onClick = { 
                                selectedFlashcardSystemId = "all"
                                flashcardIndex = 0
                                isCardFlipped = false
                            },
                            label = { Text("All Systems (${allFlashcards.size})", fontSize = 11.sp) }
                        )
                        anatomySystems.forEach { sys ->
                            FilterChip(
                                selected = selectedFlashcardSystemId == sys.id,
                                onClick = { 
                                    selectedFlashcardSystemId = sys.id
                                    flashcardIndex = 0
                                    isCardFlipped = false
                                },
                                label = { Text(sys.name, fontSize = 11.sp) },
                                leadingIcon = { Icon(sys.icon, null, modifier = Modifier.size(12.dp)) }
                            )
                        }
                    }

                    if (allFlashcards.isNotEmpty()) {
                        val (sys, part) = allFlashcards[flashcardIndex % allFlashcards.size]
                        
                        // Header info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Flashcard ${flashcardIndex + 1} of ${allFlashcards.size}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Surface(
                                color = sys.themeColor.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = sys.name,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = sys.themeColor,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        // Interactive 3D Flip Card Container
                        val cardRotation by animateFloatAsState(
                            targetValue = if (isCardFlipped) 180f else 0f,
                            animationSpec = tween(durationMillis = 400),
                            label = "cardFlip"
                        )

                        Card(
                            onClick = { isCardFlipped = !isCardFlipped },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .graphicsLayer {
                                    rotationY = cardRotation
                                    cameraDistance = 12 * density
                                },
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (cardRotation > 90f) Color(0xFFF1F8E9) else Color(0xFFFAFAFA)
                            ),
                            border = BorderStroke(2.dp, sys.themeColor.copy(alpha = 0.4f))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (cardRotation > 90f) {
                                    // BACK OF CARD
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .graphicsLayer { rotationY = 180f },
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF2E7D32), modifier = Modifier.size(18.dp))
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text("HIGH-YIELD CLINICAL RECAP", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                            }
                                            Text(part.description, fontSize = 12.sp, color = Color.Black)
                                            Text("🏥 Clinical: ${part.clinicalNotes}", fontSize = 11.sp, color = Color(0xFFC2185B), fontWeight = FontWeight.SemiBold)
                                            Text("💊 Pharm: ${part.drugTargetNotes}", fontSize = 11.sp, color = Color(0xFF1B5E20), fontWeight = FontWeight.SemiBold)
                                        }

                                        Text("Tap card to flip front ↩", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    }
                                } else {
                                    // FRONT OF CARD
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text("ANATOMY FLASHCARD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                            Icon(Icons.Default.Flip, null, tint = sys.themeColor, modifier = Modifier.size(18.dp))
                                        }

                                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Text(part.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = sys.themeColor)
                                            Text(part.scientificName, fontSize = 13.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = Color.Gray)
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Surface(
                                                color = sys.themeColor.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(12.dp)
                                            ) {
                                                Text(
                                                    "What is the function, clinical risk & drug target?",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = sys.themeColor,
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                                )
                                            }
                                        }

                                        Text("Tap to reveal answer 🔄", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        }

                        // Spaced Repetition Rating Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    flashcardRatingMap[part.name] = "Again"
                                    isCardFlipped = false
                                    flashcardIndex = if (flashcardIndex > 0) flashcardIndex - 1 else allFlashcards.size - 1
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Again 🔴", fontSize = 11.sp)
                            }

                            Button(
                                onClick = {
                                    flashcardRatingMap[part.name] = "Good"
                                    isCardFlipped = false
                                    flashcardIndex = (flashcardIndex + 1) % allFlashcards.size
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Good 🟢", fontSize = 11.sp)
                            }
                        }
                    }

                    // High-Yield Mnemonics Section
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Lightbulb, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("USMLE & NCLEX High-Yield Mnemonics", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                            }

                            val mnemonics = listOf(
                                Pair("Phrenic Nerve Innervation", "'C3, 4, 5 keeps the diaphragm alive' (Cervical spinal nerves C3-C5 motor supply)."),
                                Pair("Femoral Triangle Contents", "'NAVEL' (Nerve, Artery, Vein, Empty space, Lymphatics - lateral to medial)."),
                                Pair("Cranial Nerve Names", "'On Old Olympus' Towering Tops A Finn And German Viewed Some Hops' (CN I to XII)."),
                                Pair("6 Ps of Acute Arterial Ischemia", "Pain, Pallor, Pulselessness, Paresthesia, Paralysis, Poikilothermia.")
                            )

                            mnemonics.forEach { (title, text) ->
                                Surface(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text("📌 $title", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                        Text(text, fontSize = 10.5.sp, color = Color.DarkGray)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // TAB 2: 500+ CLINICAL BOARD QUIZ
            // =========================================================================
            2 -> {
                val currentQuestion = activeQuizList[currentQuestionIndex % activeQuizList.size]
                var jumpInput by remember { mutableStateOf("") }
                val isQuestionBookmarked = bookmarkedQuestions.contains(currentQuestionIndex)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // System Filter Chips for Quiz
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = quizSystemFilter == "all",
                            onClick = { 
                                quizSystemFilter = "all"
                                currentQuestionIndex = 0
                                selectedAnswerIndex = null
                                quizSubmitted = false
                                showExplanation = false
                            },
                            label = { Text("All Questions (${quizQuestions.size})", fontSize = 11.sp) }
                        )
                        anatomySystems.forEach { sys ->
                            FilterChip(
                                selected = quizSystemFilter == sys.id,
                                onClick = { 
                                    quizSystemFilter = sys.id
                                    currentQuestionIndex = 0
                                    selectedAnswerIndex = null
                                    quizSubmitted = false
                                    showExplanation = false
                                },
                                label = { Text(sys.name, fontSize = 11.sp) }
                            )
                        }
                    }
                    // Quiz Progress Header
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Quiz, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "Anatomy & Clinical Quiz (500+ MCQs)",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.primary
                                ) {
                                    Text(
                                        "Q ${currentQuestionIndex + 1} / ${quizQuestions.size}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            // Quick Jump and Controls Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = jumpInput,
                                    onValueChange = { jumpInput = it },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
                                    placeholder = { Text("Go to Q# (1-${quizQuestions.size})", fontSize = 10.sp) },
                                    keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                                    singleLine = true,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                Button(
                                    onClick = {
                                        val qNum = jumpInput.toIntOrNull()
                                        if (qNum != null && qNum in 1..quizQuestions.size) {
                                            currentQuestionIndex = qNum - 1
                                            selectedAnswerIndex = null
                                            quizSubmitted = false
                                            showExplanation = false
                                            jumpInput = ""
                                        } else {
                                            Toast.makeText(context, "Enter a valid question number (1 - ${quizQuestions.size})", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier.height(48.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Jump", fontSize = 11.sp)
                                }

                                OutlinedButton(
                                    onClick = {
                                        currentQuestionIndex = (0 until quizQuestions.size).random()
                                        selectedAnswerIndex = null
                                        quizSubmitted = false
                                        showExplanation = false
                                    },
                                    modifier = Modifier.height(48.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Shuffle, null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Random", fontSize = 11.sp)
                                }
                            }
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

                    // Navigation & Submit actions bottom row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                if (currentQuestionIndex > 0) {
                                    currentQuestionIndex--
                                    selectedAnswerIndex = null
                                    quizSubmitted = false
                                    showExplanation = false
                                }
                            },
                            enabled = currentQuestionIndex > 0,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Prev")
                        }

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
                                modifier = Modifier.weight(2f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Submit")
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
                                        Toast.makeText(context, "Quiz Completed! Score: $score/${quizQuestions.size}", Toast.LENGTH_LONG).show()
                                        currentQuestionIndex = 0
                                        selectedAnswerIndex = null
                                        quizSubmitted = false
                                        showExplanation = false
                                        score = 0
                                    }
                                },
                                modifier = Modifier.weight(2f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (currentQuestionIndex + 1 == quizQuestions.size) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                                )
                            ) {
                                if (currentQuestionIndex + 1 == quizQuestions.size) {
                                    Icon(Icons.Default.Refresh, null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Restart ($score/${quizQuestions.size})")
                                } else {
                                    Text("Next Q")
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(16.dp))
                                }
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                if (currentQuestionIndex + 1 < quizQuestions.size) {
                                    currentQuestionIndex++
                                    selectedAnswerIndex = null
                                    quizSubmitted = false
                                    showExplanation = false
                                }
                            },
                            enabled = currentQuestionIndex + 1 < quizQuestions.size,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Skip")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(16.dp))
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

            // =========================================================================
            // TAB 3: CLINICAL CASE OSCE SIMULATOR
            // =========================================================================
            3 -> {
                val osceCases = listOf(
                    Triple(
                        "Case 1: 54yo M - Sudden Tearing Chest Pain radiating to Back",
                        "A 54-year-old male with long-standing poorly controlled hypertension presents to the ER with sudden onset excruciating, 'tearing' chest pain radiating between his shoulder blades. BP is 185/110 mmHg in right arm and 145/85 mmHg in left arm.",
                        listOf(
                            "Anatomic Structure Affected" to "Ascending / Descending Aorta (Intimal tear leading to aortic dissection - Stanford Type A vs B)",
                            "Key Physical Exam Finding" to "Asymmetric blood pressure between arms (>20 mmHg difference) and new aortic regurgitation murmur.",
                            "Diagnostic Gold Standard" to "CT Angiography of the Chest/Abdomen showing false lumen.",
                            "High-Yield Board Pearl" to "Type A involves ascending aorta (surgical emergency); Type B involves descending aorta (medical management with IV beta-blockers e.g. Esmolol)."
                        )
                    ),
                    Triple(
                        "Case 2: 28yo F - Acute Right Lower Quadrant Abdominal Pain",
                        "A 28-year-old female presents with 14 hours of migratory abdominal pain starting periumbilically and settling in the RLQ. She reports anorexia, low-grade fever (38.1°C), and localized tenderness at McBurney's point.",
                        listOf(
                            "Anatomic Structure Affected" to "Vermiform Appendix (Lymphoid hyperplasia or fecalith obstruction of lumen)",
                            "Key Physical Exam Finding" to "McBurney's point tenderness, Rovsing sign (RLQ pain on LLQ palpation), Obturator sign.",
                            "Diagnostic Step" to "Ultrasound or CT Abdomen/Pelvis showing dilated non-compressible appendiceal wall >6mm.",
                            "High-Yield Board Pearl" to "Innervation: Periumbilical pain is visceral (T10 dermatome via sympathetic fibers); RLQ pain is somatic (parietal peritoneum localized pain)."
                        )
                    ),
                    Triple(
                        "Case 3: 68yo M - Resting Tremor, Rigidity & Postural Instability",
                        "A 68-year-old retired teacher presents with a pill-rolling resting tremor in his left hand, mask-like facial expression, bradykinesia, and a shuffling gait with decreased arm swing.",
                        listOf(
                            "Anatomic Structure Affected" to "Substantia Nigra Pars Compacta (Loss of dopaminergic neurons in basal ganglia)",
                            "Histopathology" to "Lewy bodies composed of intracellular alpha-synuclein inclusions.",
                            "First-Line Pharmacotherapy" to "Levodopa / Carbidopa (L-DOPA crosses BBB, Carbidopa inhibits peripheral DOPA decarboxylase).",
                            "High-Yield Board Pearl" to "TRAP Mnemonic: Tremor (resting), Rigidity (cogwheel), Akinesia/Bradykinesia, Postural instability."
                        )
                    ),
                    Triple(
                        "Case 4: 35yo M - Severe Right Flank Pain Radiating to Groin",
                        "A 35-year-old man presents with sudden severe, colicky right flank pain radiating to the labia/scrotum with microscopic hematuria and severe nausea.",
                        listOf(
                            "Anatomic Structure Affected" to "Ureter (Nephrolithiasis / Kidney Stone obstruction - calcium oxalate most common)",
                            "Narrowest Anatomic Sites" to "1. Ureteropelvic junction (UPJ), 2. Crossing over iliac vessels, 3. Ureterovesical junction (UVJ).",
                            "Initial Diagnostic Test" to "Non-contrast CT of Abdomen and Pelvis or Renal Ultrasound.",
                            "High-Yield Board Pearl" to "Referred pain follows T11-L2 dermatomes corresponding to renal plexus visceral afferents."
                        )
                    )
                )

                val currentCase = osceCases[activeCaseIndex % osceCases.size]

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        "🏥 Clinical Case OSCE Simulator",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Case selection chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        osceCases.forEachIndexed { index, item ->
                            FilterChip(
                                selected = activeCaseIndex == index,
                                onClick = { activeCaseIndex = index },
                                label = { Text("Case ${index + 1}", fontSize = 11.sp) }
                            )
                        }
                    }

                    // Active Case Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(currentCase.first, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                            
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = currentCase.second,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(12.dp),
                                    lineHeight = 18.sp
                                )
                            }

                            Divider(color = Color.LightGray.copy(alpha = 0.4f))

                            Text("🔍 OSCE CLINICAL BREAKDOWN:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFFC2185B))

                            currentCase.third.forEach { (label, desc) ->
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text("• $label", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurface)
                                    Text(desc, fontSize = 11.sp, color = Color.DarkGray, modifier = Modifier.padding(start = 12.dp))
                                }
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // TAB 4: SYSTEM PERFORMANCE ANALYTICS, BOOKMARKS & HIGH-YIELD SEARCH VAULT
            // =========================================================================
            4 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        "📊 Performance Analytics & High-Yield Vault",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // System Mastery Stats Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("System Mastery & Review Progress", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                            anatomySystems.forEach { sys ->
                                val score = when (sys.id) {
                                    "cardio" -> 0.88f
                                    "nervous" -> 0.92f
                                    "musculoskeletal" -> 0.78f
                                    "respiratory" -> 0.84f
                                    "gastro" -> 0.75f
                                    "renal" -> 0.89f
                                    "endocrine" -> 0.71f
                                    "lymphatic" -> 0.94f
                                    "integumentary" -> 0.82f
                                    else -> 0.80f
                                }

                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(sys.name, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                        Text("${(score * 100).toInt()}%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = sys.themeColor)
                                    }
                                    LinearProgressIndicator(
                                        progress = score,
                                        modifier = Modifier.fillMaxWidth().height(6.dp),
                                        color = sys.themeColor,
                                        trackColor = sys.themeColor.copy(alpha = 0.15f)
                                    )
                                }
                            }
                        }
                    }

                    // Bookmarked Questions Vault
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Bookmark, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Bookmarked Board Questions (${bookmarkedQuestions.size})", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                            }

                            if (bookmarkedQuestions.isEmpty()) {
                                Text("No bookmarked questions yet. Tap the bookmark icon while taking quizzes to save questions here for rapid review!", fontSize = 11.sp, color = Color.Gray)
                            } else {
                                bookmarkedQuestions.forEach { qIndex ->
                                    val q = quizQuestions[qIndex % quizQuestions.size]
                                    Surface(
                                        color = MaterialTheme.colorScheme.surface,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Text("Q${qIndex + 1}: ${q.question}", fontWeight = FontWeight.SemiBold, fontSize = 11.sp, maxLines = 2)
                                            Text("Key: ${q.options[q.correctIndex]}", fontSize = 10.5.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Quick Organ & Structure Search Index
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("🔍 Fast Organ & Clinical Search Index", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Search 100+ organs, bones, or clinical terms...", fontSize = 11.sp) },
                                leadingIcon = { Icon(Icons.Default.Search, null, modifier = Modifier.size(18.dp)) },
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp)
                            )

                            val allParts = anatomySystems.flatMap { sys -> sys.parts.map { part -> Pair(sys, part) } }
                            val filteredParts = if (searchQuery.isBlank()) allParts.take(5) else allParts.filter { (sys, part) ->
                                part.name.contains(searchQuery, ignoreCase = true) ||
                                part.scientificName.contains(searchQuery, ignoreCase = true) ||
                                part.clinicalNotes.contains(searchQuery, ignoreCase = true) ||
                                part.drugTargetNotes.contains(searchQuery, ignoreCase = true)
                            }

                            filteredParts.forEach { (sys, part) ->
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(part.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = sys.themeColor)
                                            Text(sys.name, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = sys.themeColor)
                                        }
                                        Text(part.scientificName, fontSize = 10.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = Color.Gray)
                                        Text("🏥 Clinical: ${part.clinicalNotes}", fontSize = 10.5.sp, color = Color(0xFFC2185B))
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
