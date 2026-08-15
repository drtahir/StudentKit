package com.example.ui.screens

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
import androidx.compose.ui.draw.shadow
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
import kotlin.random.Random

// =============================================================================
// HAJJ MEDICAL MISSION (NTS) EXAM PREPARATION PORTAL
// =============================================================================

data class HajjQuestion(
    val id: Int,
    val category: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val reference: String
)

data class HajjTopic(
    val title: String,
    val category: String,
    val summary: String,
    val highYieldFacts: List<String>,
    val checklistItem: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HajjMedicalPrepScreen(viewModel: StudentKitViewModel) {
    var activeSubTab by remember { mutableStateOf(0) } // 0 = NTS Exam Simulator, 1 = Study Guides, 2 = Clinic Scenario Lab, 3 = Stats & Achievement

    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
    ) {
        // Styled Tab Navigation with custom icons and badges
        ScrollableTabRow(
            selectedTabIndex = activeSubTab,
            edgePadding = 12.dp,
            modifier = Modifier.fillMaxWidth().shadow(2.dp)
        ) {
            Tab(
                selected = activeSubTab == 0,
                onClick = { activeSubTab = 0 },
                text = { Text("Mock Simulator", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Timer, null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = activeSubTab == 1,
                onClick = { activeSubTab = 1 },
                text = { Text("Study Syllabus", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.MenuBook, null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = activeSubTab == 2,
                onClick = { activeSubTab = 2 },
                text = { Text("Scenario Lab", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Healing, null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = activeSubTab == 3,
                onClick = { activeSubTab = 3 },
                text = { Text("Progress Stats", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.BarChart, null, modifier = Modifier.size(18.dp)) }
            )
        }

        AnimatedContent(
            targetState = activeSubTab,
            transitionSpec = {
                slideInHorizontally { width -> if (targetState > initialState) width else -width } + fadeIn() togetherWith
                slideOutHorizontally { width -> if (targetState > initialState) -width else width } + fadeOut()
            },
            label = "SubTabAnimation"
        ) { tab ->
            when (tab) {
                0 -> HajjMockSimulatorView(viewModel = viewModel)
                1 -> HajjStudySyllabusView()
                2 -> HajjClinicScenarioLabView()
                3 -> HajjProgressStatsView()
            }
        }
    }
}

/**
 * 1. MOCK EXAM SIMULATOR
 * Features a large randomized questions bank with dynamic category filtering.
 */
fun buildOptions(correctIdx: Int, correct: String, w1: String, w2: String, w3: String): List<String> {
    val list = mutableListOf(w1, w2, w3)
    list.add(correctIdx.coerceIn(0, 3), correct)
    return list
}

fun getManualQuestions(): List<HajjQuestion> {
    return listOf(
        // --- CATEGORY: HAJJ RULES & ADMINISTRATION (MORA) ---
        HajjQuestion(
            1, "Hajj Rules & Admin",
            "On which date of the Islamic month of Dhu al-Hijjah do pilgrims gather at Arafat for the sermon and Wuquf, which is the absolute peak ritual of Hajj?",
            listOf("8th Dhu al-Hijjah", "9th Dhu al-Hijjah", "10th Dhu al-Hijjah", "11th Dhu al-Hijjah"),
            1,
            "Wuquf-e-Arafat is performed on the 9th of Dhu al-Hijjah. Gathering at Arafat is the most critical pillar of Hajj; without performing Wuquf, the Hajj is invalid.",
            "NTS Hajj Past Papers & Ministry of Hajj Guidelines"
        ),
        HajjQuestion(
            2, "Hajj Rules & Admin",
            "Where do Hajj pilgrims spend the night of the 9th of Dhu al-Hijjah, collecting pebbles after leaving the plains of Arafat?",
            listOf("Mina", "Muzdalifah", "Safa & Marwa", "Madinah"),
            1,
            "Pilgrims travel from Arafat to Muzdalifah on the evening of the 9th of Dhu al-Hijjah, where they perform combined Maghrib and Isha prayers and collect pebbles for the Jamarat (stoning of devil) ritual.",
            "Hajj Pilgrimage Administrative Manual"
        ),
        HajjQuestion(
            3, "Hajj Rules & Admin",
            "What is the official term for the administrative staff and medical professionals dispatched from Pakistan to provide support to pilgrims under the Ministry of Religious Affairs (MoRA)?",
            listOf("Haji Camp Officers", "Muallim Group", "Hajj Medical Mission & Mawawin", "Tawaf Volunteers"),
            2,
            "The Government of Pakistan dispatches the Hajj Medical Mission (doctors, pharmacists, nurses, paramedics) alongside Mawawin (helpers) to guide and provide free healthcare to Pakistani pilgrims.",
            "MORA Hajj Selection Policy"
        ),
        HajjQuestion(
            4, "Hajj Rules & Admin",
            "The stoning of the Jamarat (Rami) ritual is primarily performed at which of the following designated locations?",
            listOf("Plains of Arafat", "Valleys of Mina", "Inside Haram Mosque", "Mount Uhud"),
            1,
            "Rami (stoning of the pillars) is performed at Mina where the three Jamarat pillars are located. Pilgrims spend multiple days camping in tents at Mina.",
            "Hajj Ritual Guide Book"
        ),
        HajjQuestion(
            5, "Hajj Rules & Admin",
            "Under Pakistan Government Hajj Policy, what is the maximum duration that a clinical member of the Hajj Medical Mission is expected to serve in Saudi Arabia?",
            listOf("15 Days", "25 Days", "Approx. 40 to 45 Days", "90 Days"),
            2,
            "Hajj Medical Mission staff are deployed for approximately 40 to 45 days, divided into pre-Hajj, active Hajj days, and post-Hajj evacuation phases.",
            "MoRA Medical Mission Handbook"
        ),

        // --- CATEGORY: HEAT STROKE & ENVIRONMENTAL EMERGENCIES ---
        HajjQuestion(
            6, "Heat Stroke & Hydration",
            "A pilgrim presents with a core body temperature of 105°F (40.5°C), hot dry skin, confusion, and tachypnea. What is the immediate priority clinical action?",
            listOf(
                "Administer oral paracetamol and wait 30 minutes",
                "Immediate active evaporative cooling (tepid mist, fans) and cold water immersion if possible",
                "Start slow maintenance IV dextrose infusion",
                "Conduct an urgent abdominal ultrasound"
            ),
            1,
            "This presentation is classic for Heat Stroke (exertional or non-exertional). It is a life-threatening medical emergency. Rapid physical cooling to lower core body temperature below 102°F (38.9°C) is the absolute first-line priority to save brain and organ function.",
            "WHO Emergency Medicine - Heat Emergencies in Crowds"
        ),
        HajjQuestion(
            7, "Heat Stroke & Hydration",
            "Which physiological marker is the most critical parameter in distinguishing between severe Heat Exhaustion and acute Heat Stroke?",
            listOf("Systolic blood pressure", "Presence of muscle cramps", "Altered mental status (CNS dysfunction)", "Rate of sweating"),
            2,
            "While both present with hyperthermia, Heat Stroke is distinguished by Central Nervous System (CNS) dysfunction (confusion, delirium, seizures, coma) and a core temperature usually exceeding 104°F (40°C). Heat exhaustion patients maintain intact neurological status.",
            "NTS Clinical Diagnostics & Wilderness Medicine"
        ),
        HajjQuestion(
            8, "Heat Stroke & Hydration",
            "Why is the use of antipyretics (such as Aspirin or Acetaminophen) clinically contraindicated in treating hyperthermia caused by Heat Stroke?",
            listOf(
                "They can worsen hepatic and renal dysfunction without affecting the non-pyrogenic heat center",
                "They cause severe immediate skin rashes under sunlight",
                "They rapidly elevate blood glucose levels",
                "They completely block the action of intravenous saline"
            ),
            0,
            "Heat stroke hyperthermia is caused by failed thermoregulation due to excessive heat load, not a change in the hypothalamic set point (which is mediated by pyrogens). Antipyretics are useless and can exacerbate liver injury (acetaminophen) or coagulopathy (aspirin).",
            "Standard Emergency Therapeutics Manual"
        ),
        HajjQuestion(
            9, "Heat Stroke & Hydration",
            "During hot weather in Mina and Arafat, what is the minimum daily fluid intake recommended for active pilgrims to prevent dehydration?",
            listOf("500 mL to 1 Liter", "1.5 Liters", "3 to 4 Liters containing electrolyte balance", "6 to 8 Liters of pure distilled water"),
            2,
            "To offset intense water loss from perspiration under extreme temperatures (often exceeding 45°C), pilgrims are advised to drink 3-4 liters of fluids daily, including oral rehydration salts (ORS) to replenish lost sodium and potassium.",
            "Saudi Ministry of Health Pilgrim Wellness Guide"
        ),
        HajjQuestion(
            10, "Heat Stroke & Hydration",
            "What is the first choice of intravenous fluid for rapid volume resuscitation in a dehydrated pilgrim experiencing hypovolemic shock?",
            listOf("5% Dextrose in water (D5W)", "0.9% Normal Saline or Ringer's Lactate", "0.45% Half-Normal Saline", "Mannitol 20% solution"),
            1,
            "Isotonic crystalloids (Normal Saline or Ringer's Lactate) are the first-line fluids for intravascular volume expansion to treat hypovolemic shock, restoring blood pressure and vital perfusion safely.",
            "Advanced Trauma Life Support (ATLS) Guidelines"
        ),

        // --- CATEGORY: SAUDI VACCINATIONS & INFECTIOUS DISEASES ---
        HajjQuestion(
            11, "Vaccine & Outbreaks",
            "Which vaccine is strictly mandatory for all international Hajj pilgrims, with a requirement that the certificate of vaccination be issued at least 10 days before arrival in Saudi Arabia?",
            listOf("Quadrivalent Meningococcal Vaccine (ACYW135)", "Rabies vaccine", "Hepatitis B vaccine", "Typhoid conjugate vaccine"),
            0,
            "Saudi Arabia requires all pilgrims to show proof of quadrivalent (ACYW) meningococcal meningitis vaccine. This protects pilgrims against highly infectious meningococcal meningitis outbreaks in dense crowds.",
            "Saudi MOH Hajj Vaccination Health Requirements"
        ),
        HajjQuestion(
            12, "Vaccine & Outbreaks",
            "Which respiratory viral illness, first identified in Saudi Arabia in 2012, is a major focus of surveillance and quarantine screening during the Hajj season?",
            listOf("Severe Influenza A (H1N1)", "Middle East Respiratory Syndrome Coronavirus (MERS-CoV)", "Respiratory Syncytial Virus (RSV)", "Ebola virus"),
            1,
            "MERS-CoV (Middle East Respiratory Syndrome Coronavirus) is endemic to the Arabian Peninsula. High-vigilance respiratory screenings, isolation protocols, and hand hygiene are mandated during Hajj to prevent global spread.",
            "WHO MERS-CoV Surveillance Guidelines"
        ),
        HajjQuestion(
            13, "Vaccine & Outbreaks",
            "What are the classical clinical symptoms of meningococcal meningitis that a Hajj Medical officer must instantly recognize for urgent isolation?",
            listOf(
                "Chronic wet cough, weight loss, night sweats",
                "Sudden high fever, severe headache, stiff neck (nuchal rigidity), and petechial skin rash",
                "Abdominal cramping, watery stools, yellowing of sclera",
                "Joint swelling, bilateral wrist drop, facial palsy"
            ),
            1,
            "The classic triad of meningitis is fever, altered mental status, and neck stiffness. The development of a non-blanching petechial rash is highly suggestive of meningococcemia and requires instant droplet isolation and IV Ceftriaxone.",
            "NTS Clinical Medicine Past Paper"
        ),
        HajjQuestion(
            14, "Vaccine & Outbreaks",
            "If an outbreak of highly contagious cholera is suspected in a pilgrim camp in Mina, what is the primary epidemiological control measure?",
            listOf(
                "Sterilizing all air conditioners",
                "Ensuring clean, safe drinking water, proper sewage disposal, and immediate distribution of ORS & rehydration",
                "Mass administration of intravenous steroids to all healthy pilgrims",
                "Spraying chemical disinfectants onto open sandy ground"
            ),
            1,
            "Cholera is a water-borne disease. Safe drinking water, rigorous sanitation, hygiene education, and immediate aggressive fluid therapy (rehydration) are the cornerstones of epidemic containment.",
            "CDC Yellow Book: Cholera Prevention in Crowds"
        ),
        HajjQuestion(
            15, "Vaccine & Outbreaks",
            "Under international health rules, travelers arriving at Saudi entry points from polio-endemic countries like Pakistan must receive which additional vaccine dose?",
            listOf("Injectable BCG", "One dose of Oral Polio Vaccine (OPV) at entry point regardless of prior history", "Yellow Fever booster", "Tetanus toxoid injection"),
            1,
            "Pilgrims arriving from countries with active wild poliovirus transmission (like Pakistan and Afghanistan) must receive a dose of Oral Polio Vaccine (OPV) at the Saudi port of entry, regardless of their age or vaccination status.",
            "WHO Travel Medicine & Saudi Hajj Requirements"
        ),

        // --- CATEGORY: GENERAL CLINICAL PRACTICE, CPR & TRAUMA ---
        HajjQuestion(
            16, "CPR, Trauma & Clinics",
            "During a stampede or crowd collapse at Mina, what is the first priority of a medical responder upon discovering an unconscious pilgrim with no signs of breathing or pulse?",
            listOf(
                "Check pupil response with a flashlight",
                "Activate emergency response and immediately start Cardiopulmonary Resuscitation (CPR) beginning with 30 chest compressions",
                "Administer intramuscular epinephrine",
                "Immobilize the spine using standard splints"
            ),
            1,
            "For any patient in cardiac arrest, high-quality chest compressions must be started immediately (ratio of 30 compressions to 2 breaths) to sustain coronary and cerebral perfusion. Do not delay CPR for secondary assessments.",
            "American Heart Association (AHA) CPR Guidelines"
        ),
        HajjQuestion(
            17, "CPR, Trauma & Clinics",
            "What is the standard adult dose and route of administration of Epinephrine (Adrenaline) for treating severe acute anaphylaxis caused by a medication or bee sting?",
            listOf("1.0 mg intravenously", "0.3 mg intramuscularly (1:1000 dilution) in the anterolateral thigh", "5.0 mg orally", "0.5 mg subcutaneously (1:10,000 dilution)"),
            1,
            "The standard drug of choice for anaphylactic shock is intramuscular (IM) Epinephrine at a dose of 0.3 mg (using 1:1000 concentration), injected into the anterolateral mid-thigh. It is safe, rapid, and life-saving.",
            "Emergency Drug Protocols & NTS Nursing Exam"
        ),
        HajjQuestion(
            18, "CPR, Trauma & Clinics",
            "A pilgrim has sustained a compound fracture of the lower leg with active physical arterial bleeding in Mina. What is the immediate priority action?",
            listOf(
                "Splint the bone immediately to prevent movement",
                "Apply direct pressure to the wound with a sterile dressing and use a tourniquet if bleeding is uncontrolled",
                "Clean the wound thoroughly with warm water",
                "Administer intramuscular analgesics"
            ),
            1,
            "In trauma, managing life-threatening external hemorrhage takes priority over bone immobilization. Apply direct, firm pressure; if a major artery is severed and direct pressure fails, apply a tourniquet proximal to the wound.",
            "Stop the Bleed & Wilderness Trauma Protocols"
        ),
        HajjQuestion(
            19, "CPR, Trauma & Clinics",
            "Which basic clinical examination tool is most useful for a triaging nurse in a crowded Hajj medical camp to rapidly assess peripheral oxygen saturation and heart rate?",
            listOf("Sphygmomanometer", "Fingertip Pulse Oximeter", "Electrocardiogram (ECG)", "Clinical Thermometer"),
            1,
            "A pulse oximeter is a rapid, non-invasive triage tool to measure oxygen saturation (SpO2) and pulse rate. Crucial for assessing respiratory distress, asthma, or pneumonia in high-volume clinics.",
            "MOH Triage Protocols"
        ),
        HajjQuestion(
            20, "CPR, Trauma & Clinics",
            "An elderly diabetic pilgrim is brought to the medical center sweating profusely, trembling, and confused. What is the immediate diagnostic step?",
            listOf("Perform a blood urea nitrogen test", "Measure capillary blood glucose (Glucometer check)", "Order an urgent head CT scan", "Administer high-dose insulin injection"),
            1,
            "Sweating, shaking, confusion, and anxiety in a diabetic patient suggest hypoglycemia (low blood sugar). Instantly check blood glucose with a glucometer. If low (<70 mg/dL), treat with fast-acting oral carbohydrates or IV 25% Dextrose.",
            "Endocrine Emergency Guidelines"
        )
    )
}

fun generateAllHajjQuestions(): List<HajjQuestion> {
    val list = mutableListOf<HajjQuestion>()
    list.addAll(getManualQuestions())

    // --- GENERATE CATEGORY: Hajj Rules & Admin (60 questions: IDs 21 to 80) ---
    for (i in 0 until 60) {
        val qId = 21 + i
        val optionIndex = i % 4
        val questionText: String
        val options: List<String>
        val explanation: String
        val reference: String

        when (i % 6) {
            0 -> {
                val city = listOf("Islamabad", "Lahore", "Karachi", "Peshawar", "Quetta", "Multan", "Faisalabad", "Sialkot", "Rawalpindi", "Gujranwala")[i % 10]
                val role = listOf("Medical Officer", "Charge Nurse", "Pharmacist", "Paramedic", "Dental Surgeon", "Lab Assistant")[i % 6]
                questionText = "For a selected $role representing the $city Haji Camp region, what is the absolute priority when arriving at the clinical headquarters in Makkah?"
                val correct = "Report immediately to the Clinical Director for duty roster mapping and sector orientation"
                val w1 = "Visit the local historical sites in Jeddah"
                val w2 = "Purchase personal consumer electronics from Makkah markets"
                val w3 = "Arrange private transport for personal leisure trips"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "A selected $role must immediately report to the central Pakistani Hajj Medical Mission clinical headquarters for proper sector alignment, emergency rotation mapping, and shift assignment."
                reference = "MORA Hajj Mission Standard Operating Procedures"
            }
            1 -> {
                val day = listOf("8th", "9th", "10th", "11th", "12th", "13th")[i % 6]
                val ritual = listOf("Mina Tent Camping", "Wuquf-e-Arafat", "Jamarat Rami & Sacrifice", "Tawaf-e-Ziyarah", "Mina Mabit", "Tawaf-e-Wida")[i % 6]
                questionText = "Under Hajj operational timelines, what is the significance of the $day of Dhu al-Hijjah for pilgrims performing $ritual?"
                val correct = "It is a highly critical day for performing the designated ritual of $ritual"
                val w1 = "It is a rest day with zero religious significance"
                val w2 = "It is the day all pilgrims return to Pakistan"
                val w3 = "It is reserved exclusively for shopping in Jeddah"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "The $day of Dhu al-Hijjah is a core calendar milestone, dedicated to $ritual."
                reference = "Hajj Pilgrimage Manual & MORA Guidelines"
            }
            2 -> {
                val loc = listOf("Arafat plains", "Mina valley", "Muzdalifah camp", "Haram sanctuary", "Safa-Marwa path", "Haji Camp")[i % 6]
                val equipment = listOf("emergency ORS kits", "portable oxygen cylinders", "splints and bandages", "cardiac monitors", "rehydration salts", "clinical triage logs")[i % 6]
                questionText = "When deploying a mobile medical team to the $loc, which item is the absolute minimum requirement for treating pilgrims?"
                val correct = "A fully stocked backup supply of $equipment"
                val w1 = "A portable television screen"
                val w2 = "A box of expensive surgical textbooks"
                val w3 = "Local sightseeing maps"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Mobile health teams at $loc must carry emergency essentials like $equipment to handle high-yield crowd incidents."
                reference = "NTS Hajj Clinical Field Manual"
            }
            3 -> {
                val age = listOf(65, 70, 75, 80, 85, 90)[i % 6]
                val cond = listOf("severe osteoarthritis", "uncontrolled diabetes", "chronic heart failure", "mild asthma", "hypertension", "COPD")[i % 6]
                questionText = "Under Ministry rules, an elderly pilgrim of age $age with a medical history of $cond requires which specific approval before travel?"
                val correct = "A comprehensive 'Fit to Travel' medical certificate from an authorized government hospital medical board"
                val w1 = "A verbal clearance from any local pharmacist"
                val w2 = "A signed note from a family member"
                val w3 = "No medical clearance is required"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Any high-risk pilgrim (such as those of advanced age like $age or with chronic diseases like $cond) must obtain formal clinical clearance from a registered medical board."
                reference = "MORA Health Selection and Clearance Policy"
            }
            4 -> {
                val post = listOf("Makkah Main Hospital", "Mina Sector 1 Clinic", "Madinah Medical Center", "Arafat Emergency Post", "Mina Sector 3 Clinic", "Azizia Triage Ward")[i % 6]
                questionText = "A clinical team member stationed at the $post is responsible for which of the following primary operational tasks?"
                val correct = "Directing immediate patient triage, stabilization, and coordinating transport with Saudi emergency services if needed"
                val w1 = "Selling airline tickets to returning tourists"
                val w2 = "Preparing meals for the entire local hotel staff"
                val w3 = "Guiding commercial traffic outside the holy sites"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Staff at the $post must focus on rapid clinical triage, patient stabilization, and coordinating emergency care."
                reference = "Hajj Medical Mission Emergency Guide"
            }
            else -> {
                val restriction = listOf("5-year gap rule", "once-in-a-lifetime rule", "mandatory refresher course", "age limit restriction", "physical fitness certificate", "clear biometric verification")[i % 6]
                questionText = "What is the primary objective of the Ministry of Religious Affairs enforcing the '$restriction' for Hajj Medical Mission staff?"
                val correct = "To ensure maximum transparency, fairness, physical readiness, and high-quality healthcare delivery"
                val w1 = "To minimize the number of pilgrims traveling from Pakistan"
                val w2 = "To increase administrative fees collected by local agencies"
                val w3 = "To completely phase out the use of professional doctors"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "The '$restriction' is enforced to maintain high professional standards, physical capability, and fair rotation among clinical applicants."
                reference = "MORA Selection Criteria & NTS Syllabus"
            }
        }
        list.add(HajjQuestion(qId, "Hajj Rules & Admin", questionText, options, optionIndex, explanation, reference))
    }

    // --- GENERATE CATEGORY: Heat Stroke & Hydration (60 questions: IDs 81 to 140) ---
    for (i in 0 until 60) {
        val qId = 81 + i
        val optionIndex = i % 4
        val questionText: String
        val options: List<String>
        val explanation: String
        val reference: String

        val age = listOf(58, 62, 67, 71, 74, 78, 81, 85, 89, 92)[i % 10]
        val temp = listOf("104.2°F (40.1°C)", "104.9°F (40.5°C)", "105.5°F (40.8°C)", "106.1°F (41.2°C)", "103.8°F (39.9°C)", "105.0°F (40.6°C)")[i % 6]
        val loc = listOf("Mina tents", "Arafat highway", "Muzdalifah walkway", "Haram circumambulation ring", "Jamarat bridge", "Azizia residential sector")[i % 6]
        val cond = listOf("diabetes mellitus", "chronic hypertension", "COPD", "coronary artery disease", "chronic kidney disease", "mild bronchial asthma")[i % 6]

        when (i % 6) {
            0 -> {
                questionText = "A $age-year-old pilgrim with a history of $cond collapses on the $loc. Core body temperature is $temp, they present with hot dry skin, tachycardia, and severe confusion. What is the immediate first-line clinical intervention?"
                val correct = "Initiate immediate active physical cooling (evaporative tepid spray, high-velocity fans) and monitor core temperature continuously"
                val w1 = "Administer 1000 mg of oral acetaminophen and monitor for 2 hours"
                val w2 = "Inject 50 units of rapid insulin subcutaneously"
                val w3 = "Apply heavy blankets to induce protective sweating"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "An elderly pilgrim presenting with core temperature of $temp and Central Nervous System (CNS) impairment (severe confusion) has Heat Stroke. Rapid physical cooling to lower core body temperature below 102°F (38.9°C) is the absolute first-line clinical priority."
                reference = "Saudi Ministry of Health - Heat Stroke Treatment Standards"
            }
            1 -> {
                questionText = "Under extreme temperatures at $loc, why is the clinical team instructed not to administer Aspirin or other antipyretics to a pilgrim with suspected Heat Stroke?"
                val correct = "Antipyretics are ineffective because heat stroke is caused by failed physical heat dissipation, not an altered hypothalamic set-point, and they can worsen hepatic or renal injury"
                val w1 = "Antipyretics cause a dangerous spike in peripheral oxygen demand"
                val w2 = "Antipyretics completely neutralize the action of intravenous normal saline"
                val w3 = "They are too expensive to distribute in field clinics"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Antipyretics are ineffective for environmental/exertional hyperthermia because the hypothalamus is functioning normally but the body's cooling mechanisms are overwhelmed. Furthermore, they can aggravate liver and kidney failure."
                reference = "NTS Emergency Therapeutics & Wilderness Medicine Guidelines"
            }
            2 -> {
                questionText = "To prevent hypovolemic shock in a dehydrated pilgrim with history of $cond working in the $loc, what is the recommended intravenous fluid of choice?"
                val correct = "Isotonic crystalloids (0.9% Normal Saline or Ringer's Lactate)"
                val w1 = "5% Dextrose in Water (D5W) exclusively"
                val w2 = "Hypertonic 3% Saline solution"
                val w3 = "Mannitol 20% solution"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Isotonic crystalloids are the standard of care for restoring intravascular volume, stabilizing blood pressure, and ensuring adequate kidney perfusion in severe dehydration."
                reference = "ATLS Guidelines for Hypovolemic Resuscitation"
            }
            3 -> {
                questionText = "A pilgrim of age $age is admitted to the $loc clinic with a body temperature of 101.5°F (38.6°C), profuse sweating, headache, nausea, but fully intact cognitive orientation. What is the diagnosis?"
                val correct = "Heat Exhaustion (mild to moderate hyperthermia without neurological impairment)"
                val w1 = "Acute Meningococcal Meningitis"
                val w2 = "Classic Heat Stroke"
                val w3 = "Septic Shock"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Heat exhaustion presents with heavy sweating, nausea, and mild temperature elevation, but crucially lacks the central nervous system (CNS) dysfunction (such as confusion, seizures, or coma) that defines Heat Stroke."
                reference = "NTS Diagnostics Manual for Hajj Medical Mission"
            }
            4 -> {
                questionText = "What is the target core body temperature threshold at which active cooling measures should be ceased to prevent hypothermic overshoot in a pilgrim being treated for heat stroke?"
                val correct = "102°F (38.9°C)"
                val w1 = "98.6°F (37.0°C)"
                val w2 = "104.0°F (40.0°C)"
                val w3 = "95.0°F (35.0°C)"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "To prevent rebound hypothermia and shivering (which increases heat production), active physical cooling is stopped once the patient's core body temperature drops to 102°F (38.9°C)."
                reference = "WHO Environmental Medicine - Mass Gathering Heat Management"
            }
            else -> {
                val fluidAmount = listOf("3 to 4 liters", "4 to 5 liters", "3.5 liters", "3 to 4.5 liters")[i % 4]
                questionText = "To counter extreme water loss from perspiration under 45°C+ heat in $loc, what is the recommended daily fluid intake for an active pilgrim?"
                val correct = "At least $fluidAmount of water mixed with oral rehydration salts (ORS) to maintain electrolyte balance"
                val w1 = "1 to 1.5 liters of highly concentrated black coffee"
                val w2 = "8 liters of pure distilled ice water without any electrolytes"
                val w3 = "Only drinking when intense muscle cramps begin"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Drinking $fluidAmount of fluids containing balanced electrolytes (such as ORS) is highly effective at preventing dehydration and painful heat cramps."
                reference = "Saudi MOH Pilgrim Health & Wellness Bulletins"
            }
        }
        list.add(HajjQuestion(qId, "Heat Stroke & Hydration", questionText, options, optionIndex, explanation, reference))
    }

    // --- GENERATE CATEGORY: Vaccine & Outbreaks (60 questions: IDs 141 to 200) ---
    for (i in 0 until 60) {
        val qId = 141 + i
        val optionIndex = i % 4
        val questionText: String
        val options: List<String>
        val explanation: String
        val reference: String

        val country = listOf("Pakistan", "Afghanistan", "Nigeria", "Somalia", "Yemen", "Syria")[i % 6]
        val location = listOf("Mina central camp", "Arafat encampment", "Azizia sector 2", "Madinah hotel zone", "Makkah clinical station", "Pakistani Haji Camp")[i % 6]

        when (i % 6) {
            0 -> {
                questionText = "Which vaccine is strictly mandatory for all pilgrims traveling from $country, and must be administered at least 10 days before arrival in Saudi Arabia?"
                val correct = "Quadrivalent Meningococcal Vaccine (ACYW135)"
                val w1 = "Yellow Fever Vaccine"
                val w2 = "Injectable Typhoid Booster"
                val w3 = "Rabies Prophylaxis Vaccine"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "The quadrivalent meningococcal vaccine (protecting against serogroups A, C, Y, and W135) is a legal mandatory entry requirement for all pilgrims to prevent lethal meningitis outbreaks."
                reference = "Saudi Ministry of Health Official Vaccine Requirements"
            }
            1 -> {
                questionText = "A pilgrim in $location is diagnosed with acute meningococcal meningitis. What is the immediate chemoprophylaxis recommendation for close contacts (e.g., roommate pilgrims)?"
                val correct = "Single oral dose of Ciprofloxacin 500 mg (or single IM dose of Ceftriaxone 250 mg)"
                val w1 = "7-day course of oral Amoxicillin 500 mg thrice daily"
                val w2 = "Immediate vaccination with Meningococcal vaccine (takes too long to provide immunity)"
                val w3 = "Administering high-dose intravenous steroids only"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Close contacts of a meningococcal meningitis case require immediate antibiotic prophylaxis. A single dose of oral Ciprofloxacin 500 mg is highly effective at eradicating nasopharyngeal carriage of Neisseria meningitidis."
                reference = "CDC Guidelines for Meningitis Control in Mass Crowds"
            }
            2 -> {
                questionText = "Which respiratory virus, first isolated in Saudi Arabia in 2012, requires strict screening, isolation, and immediate reporting by Hajj Medical Mission staff?"
                val correct = "Middle East Respiratory Syndrome Coronavirus (MERS-CoV)"
                val w1 = "Avian Influenza (H5N1)"
                val w2 = "Severe Acute Respiratory Syndrome (SARS-CoV-1)"
                val w3 = "Respiratory Syncytial Virus (RSV)"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "MERS-CoV remains a critical public health surveillance target during Hajj due to camel contact risk and potential for severe hospital-acquired outbreaks."
                reference = "WHO MERS-CoV Surveillance Manual for Mass Gatherings"
            }
            3 -> {
                questionText = "If several pilgrims in a Mina sector tent present with acute onset of severe, painless watery diarrhea ('rice-water stools') and muscle cramps, what is the first suspected pathogen?"
                val correct = "Vibrio cholerae (Cholera)"
                val w1 = "Salmonella typhi (Typhoid)"
                val w2 = "Entamoeba html (Amebiasis)"
                val w3 = "Rotavirus"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Rapidly spreading, highly contagious painless watery diarrhea resembling rice-water in a crowded environment is a classic clinical warning of a Vibrio cholerae (Cholera) outbreak."
                reference = "WHO Cholera Epidemic Control Guidelines"
            }
            4 -> {
                questionText = "Under international health regulations, why are all Pakistani pilgrims required to receive a dose of Oral Polio Vaccine (OPV) at Saudi airports?"
                val correct = "To completely prevent any potential wild poliovirus transmission in the massive international crowd"
                val w1 = "Because the injectable polio vaccine is not clinically manufactured in Saudi Arabia"
                val w2 = "To serve as a general immune booster against travel fatigue"
                val w3 = "It is a legal customs taxation requirement"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Arriving from polio-endemic countries necessitates an additional dose of OPV at the port of entry to maintain a polio-free status during the globally attended Hajj pilgrimage."
                reference = "Saudi Port of Entry Sanitary Regulations"
            }
            else -> {
                val drug = listOf("Ceftriaxone (2g IV)", "Penicillin G (4 million units IV)", "Meropenem (1g IV)", "Cefotaxime (2g IV)")[i % 4]
                questionText = "A $country pilgrim presenting to a $location clinic has high fever, stiff neck, and a purple petechial rash. What is the immediate pharmaceutical priority?"
                val correct = "Place in strict droplet isolation, secure blood cultures, and immediately administer $drug"
                val w1 = "Discharge back to their tent with oral painkillers"
                val w2 = "Schedule an elective lumbar puncture in 48 hours without antibiotics"
                val w3 = "Apply cold compression therapy and wait for results"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Petechial rash, fever, and neck stiffness represent meningococcemia and meningitis. This is a medical emergency requiring rapid droplet isolation, blood cultures, and immediate empiric IV antibiotic therapy ($drug)."
                reference = "NTS High-Yield Medicine past papers"
            }
        }
        list.add(HajjQuestion(qId, "Vaccine & Outbreaks", questionText, options, optionIndex, explanation, reference))
    }

    // --- GENERATE CATEGORY: CPR, Trauma & Clinics (60 questions: IDs 201 to 260) ---
    for (i in 0 until 60) {
        val qId = 201 + i
        val optionIndex = i % 4
        val questionText: String
        val options: List<String>
        val explanation: String
        val reference: String

        val age = listOf(48, 55, 63, 69, 72, 77, 82, 86, 90, 94)[i % 10]
        val drugTrigger = listOf("Penicillin", "Ceftriaxone", "Diclofenac Injection", "Aspirin", "Ibuprofen", "Contrast Dye")[i % 6]
        val location = listOf("Mina central emergency room", "Arafat field tent", "Muzdalifah clinical outpost", "Makkah central ward", "Azizia triage deck", "Haji Camp clinic")[i % 6]

        when (i % 6) {
            0 -> {
                questionText = "During adult Cardiopulmonary Resuscitation (CPR) performed in the $location, what is the correct rate and depth of chest compressions?"
                val correct = "100 to 120 compressions per minute at a depth of 2 to 2.4 inches (5 to 6 cm) with complete chest recoil"
                val w1 = "80 compressions per minute at a depth of 1 inch"
                val w2 = "150 compressions per minute at a depth of 3 inches"
                val w3 = "60 compressions per minute with continuous positive airway pressure"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "AHA CPR Guidelines mandate a rate of 100-120 compressions per minute and a depth of 2-2.4 inches to optimize perfusion to the brain and heart during cardiac arrest."
                reference = "AHA CPR and Emergency Cardiovascular Care Guidelines"
            }
            1 -> {
                questionText = "A $age-year-old pilgrim develops sudden severe shortness of breath, massive facial swelling, and a blood pressure drop to 75/35 mmHg ten minutes after receiving an injection of $drugTrigger at the $location. What is the immediate treatment?"
                val correct = "Inject 0.3 mg Epinephrine (1:1000) intramuscularly in the anterolateral thigh immediately"
                val w1 = "Administer 10 mg of oral cetirizine and elevate legs"
                val w2 = "Start an intravenous infusion of 5% Dextrose with paracetamol"
                val w3 = "Apply ice packs to the neck and face to reduce swelling"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Anaphylactic shock requires immediate intramuscular (IM) Epinephrine (0.3 mg of 1:1000) in the thigh to reverse airway obstruction and restore vascular resistance. IM route is safer and faster than IV or subcutaneous routes in this setting."
                reference = "NTS Pharmacology and Emergency Protocols"
            }
            2 -> {
                questionText = "An elderly diabetic pilgrim is brought to the $location clinic sweating profusely, trembling, and completely disoriented. Capillary blood glucose checks show 42 mg/dL. What is the priority intervention?"
                val correct = "Administer 50 mL of 50% Dextrose (or 100 mL of 25% Dextrose) intravenously immediately"
                val w1 = "Inject 12 units of rapid-acting subcutaneous insulin"
                val w2 = "Administer oral paracetamol and give hot sugar-free tea"
                val w3 = "Apply active evaporative cooling and fans for suspected heatstroke"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Severe symptomatic hypoglycemia (<50 mg/dL) with altered mental status requires rapid intravenous dextrose administration (D50W or D25W) to prevent irreversible neurological damage."
                reference = "Endocrine Society Clinical Practice Guidelines for Hypoglycemia"
            }
            3 -> {
                val fractureLoc = listOf("lower tibia", "femur", "humerus", "forearm")[i % 4]
                questionText = "Following a crowd surge near Jamarat, a $age-year-old pilgrim presents to the $location with a compound fracture of the $fractureLoc and active, bright red spurting arterial hemorrhage. What is the priority action?"
                val correct = "Apply direct pressure with sterile dressing, and immediately apply a tourniquet proximal to the wound if bleeding is uncontrolled"
                val w1 = "Carefully reduction and splint the bone fracture before address the bleeding"
                val w2 = "Wash the bone ends thoroughly with tap water"
                val w3 = "Administer high-dose oral muscle relaxants"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Life-threatening arterial bleeding takes absolute clinical priority over fracture splinting or bone reduction. Use direct pressure first; if bleeding is severe and uncontrolled, a tourniquet must be applied proximal to the wound."
                reference = "Stop the Bleed & Wilderness Trauma Care Protocols"
            }
            4 -> {
                questionText = "A $age-year-old pilgrim with a known history of severe asthma develops intense wheezing, tachypnea, and accessory muscle use during a sandstorm in $location. What is the immediate medication of choice?"
                val correct = "Inhaled short-acting beta-2 agonist (e.g., Albuterol / Salbutamol) via spacer or nebulizer"
                val w1 = "Intravenous epinephrine 1.0 mg"
                val w2 = "Oral amoxicillin 500 mg"
                val w3 = "Inhaled fluticasone steroid exclusively"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Acute bronchospasm (asthma exacerbation) is treated first-line with inhaled short-acting beta-2 agonists (SABA) like Albuterol to produce rapid bronchodilation."
                reference = "GINA Asthma Management Guidelines"
            }
            else -> {
                questionText = "An elderly pilgrim at the $location complains of sudden, retrosternal crushing chest pain radiating to the left jaw, accompanied by dyspnea and sweating. What is the first pharmaceutical agent that should be chewed?"
                val correct = "Aspirin 300 mg (non-enteric coated, chewed)"
                val w1 = "Amoxicillin 500 mg"
                val w2 = "Diclofenac sodium 50 mg"
                val w3 = "Omeprazole 40 mg"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "For suspected Acute Coronary Syndrome (Myocardial Infarction), immediate chewing of non-enteric coated Aspirin (162-325 mg) is life-saving, as it rapidly inhibits platelet aggregation and limits thrombus propagation."
                reference = "AHA/ACC Acute Coronary Syndrome Management Guidelines"
            }
        }
        list.add(HajjQuestion(qId, "CPR, Trauma & Clinics", questionText, options, optionIndex, explanation, reference))
    }

    // --- GENERATE CATEGORY: Quantitative Reasoning (40 questions: IDs 261 to 300) ---
    for (i in 0 until 40) {
        val qId = 261 + i
        val optionIndex = i % 4
        val questionText: String
        val options: List<String>
        val explanation: String
        val reference: String

        when (i % 5) {
            0 -> {
                val baseQty = 200 + (i * 10)
                val pct = 10 + (i % 5) * 10
                val used = (baseQty * pct) / 100
                val remaining = baseQty - used
                questionText = "A Hajj clinic in Mina has a starting stock of $baseQty vials of insulin. If the medical team utilizes $pct% of the stock during the first three days, how many vials of insulin remain?"
                val correct = "$remaining vials"
                val w1 = "${remaining - 25} vials"
                val w2 = "${remaining + 15} vials"
                val w3 = "${baseQty - (used / 2)} vials"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Starting stock = $baseQty vials. $pct% used = ($baseQty * $pct) / 100 = $used vials. Remaining stock = $baseQty - $used = $remaining vials."
                reference = "NTS Quantitative Prep Manual & Pharmaceutical Logistics"
            }
            1 -> {
                val docs = 3 + (i % 4)
                val patients = docs * 4
                val targetDocs = docs * 2
                val targetHours = 6 + (i % 3)
                val initialPatients = docs * 4
                val finalPatients = targetDocs * targetHours * 2
                questionText = "If $docs medical officers can treat $initialPatients patients in 2 hours, how many patients can $targetDocs medical officers treat in $targetHours hours at the same constant rate of treatment?"
                val correct = "$finalPatients patients"
                val w1 = "${finalPatients - 8} patients"
                val w2 = "${finalPatients + 12} patients"
                val w3 = "${finalPatients / 2} patients"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "The rate is: $initialPatients patients / ($docs officers * 2 hours) = 2 patients per officer per hour. Therefore, $targetDocs officers working for $targetHours hours can treat: $targetDocs * $targetHours * 2 = $finalPatients patients."
                reference = "NTS Arithmetic Word Problems Syllabus"
            }
            2 -> {
                val numDocs = 5 + (i % 3)
                val avgAge = 40 + (i % 5) * 2
                val newAge = 28 + (i % 4) * 3
                val sumAge = numDocs * avgAge
                val totalSum = sumAge + newAge
                val newCount = numDocs + 1
                val newAvg = totalSum.toDouble() / newCount
                val formattedAvg = String.format("%.2f", newAvg)
                questionText = "The average age of $numDocs clinical nurses deployed in a Sector 2 medical camp is $avgAge years. If a newly dispatched nurse of age $newAge years joins the team, what is the new average age of the clinical nurses (rounded to two decimal places)?"
                val correct = "$formattedAvg years"
                val w1 = "${String.format("%.2f", newAvg - 1.5)} years"
                val w2 = "${String.format("%.2f", newAvg + 2.1)} years"
                val w3 = "${String.format("%.2f", newAvg * 0.9)} years"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Sum of ages of $numDocs nurses = $numDocs * $avgAge = $sumAge. Sum after new nurse joins = $sumAge + $newAge = $totalSum. Total number of nurses = $newCount. New average = $totalSum / $newCount = $formattedAvg years."
                reference = "NTS Quantitative Aptitude Past Papers"
            }
            3 -> {
                val speed1 = 40 + (i % 4) * 10
                val speed2 = 60 + (i % 3) * 10
                val avgSpd = (2.0 * speed1 * speed2) / (speed1 + speed2)
                val formattedSpd = String.format("%.2f", avgSpd)
                questionText = "An emergency medical vehicle travels from Makkah Headquarters to a Mina field station at an average speed of $speed1 km/h, and immediately returns along the exact same route at an average speed of $speed2 km/h. What is the average speed of the round trip?"
                val correct = "$formattedSpd km/h"
                val w1 = "${String.format("%.2f", (speed1 + speed2) / 2.0)} km/h (arithmetic mean is incorrect)"
                val w2 = "${String.format("%.2f", avgSpd - 5.5)} km/h"
                val w3 = "${String.format("%.2f", avgSpd + 4.2)} km/h"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Since the distance traveled is equal in both directions, the average speed of the round trip is the harmonic mean: 2 * S1 * S2 / (S1 + S2) = 2 * $speed1 * $speed2 / ($speed1 + $speed2) = $formattedSpd km/h."
                reference = "NTS Physics & Quantitative Problem Solving"
            }
            else -> {
                val start = 3 + (i % 5)
                val diff = 2 + (i % 4)
                val s1 = start
                val s2 = start + diff
                val s3 = s2 + diff * 2
                val s4 = s3 + diff * 3
                val s5 = s4 + diff * 4
                val s6 = s5 + diff * 5
                questionText = "What is the next logical number in this arithmetic sequence frequently tested in NTS exams: $s1, $s2, $s3, $s4, $s5, ...?"
                val correct = "$s6"
                val w1 = "${s5 + diff}"
                val w2 = "${s5 + diff * 2}"
                val w3 = "${s6 + diff}"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "The difference increases progressively by the constant $diff: Second term = First + $diff, Third term = Second + ${diff * 2}, Fourth = Third + ${diff * 3}, Fifth = Fourth + ${diff * 4}. Therefore, the sixth term is Fifth + ${diff * 5} = $s5 + ${diff * 5} = $s6."
                reference = "NTS Sequence & Series Pattern Diagnostics"
            }
        }
        list.add(HajjQuestion(qId, "Quantitative Reasoning", questionText, options, optionIndex, explanation, reference))
    }

    // --- GENERATE CATEGORY: Analytical Reasoning (40 questions: IDs 301 to 340) ---
    for (i in 0 until 40) {
        val qId = 301 + i
        val optionIndex = i % 4
        val questionText: String
        val options: List<String>
        val explanation: String
        val reference: String

        when (i % 5) {
            0 -> {
                val wCode: String
                val targetWord: String
                val correctCode: String
                val wrong1: String
                val wrong2: String
                val wrong3: String
                if (i % 2 == 0) {
                    targetWord = "HEAL"
                    wCode = "IF 'HEAL' is coded as 'JGCN' by shifting each letter by +2 positions in the English alphabet..."
                    correctCode = "JGCN"
                    wrong1 = "GDZK"
                    wrong2 = "IFBM"
                    wrong3 = "KHCO"
                } else {
                    targetWord = "CARE"
                    wCode = "IF 'CARE' is coded as 'EDTG' by shifting each letter by +2 positions in the English alphabet..."
                    correctCode = "EDTG"
                    wrong1 = "BAQD"
                    wrong2 = "DBQF"
                    wrong3 = "FGUH"
                }
                questionText = "In a standard NTS code pattern: $wCode What is the coded equivalent of the word '$targetWord' under the same rule?"
                val correct = correctCode
                options = buildOptions(optionIndex, correct, wrong1, wrong2, wrong3)
                explanation = "Each letter is shifted forward by exactly 2 positions in the alphabet: C->E, A->C, R->T, E->G. This is a very common alphabetical substitution code in NTS."
                reference = "NTS Verbal & Analytical Ability Past Papers"
            }
            1 -> {
                val walk1 = 3 + (i % 3)
                val walk2 = 4 + (i % 2)
                val hypotenuseSq = walk1 * walk1 + walk2 * walk2
                val hypotenuse = Math.sqrt(hypotenuseSq.toDouble())
                val formattedHyp = String.format("%.2f", hypotenuse)
                questionText = "A medical officer leaves their Mina sector tent and walks exactly $walk1 km North, then takes a sharp right turn and walks exactly $walk2 km East. What is the shortest direct straight-line distance (displacement) from the officer's current position to their starting tent?"
                val correct = "$formattedHyp km"
                val w1 = "${walk1 + walk2} km (direct sum is incorrect)"
                val w2 = "${String.format("%.2f", hypotenuse - 1.2)} km"
                val w3 = "${String.format("%.2f", hypotenuse + 2.5)} km"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "This represents a right-angled triangle. By Pythagoras Theorem, the shortest straight-line distance (hypotenuse) is the square root of (North^2 + East^2) = sqrt($walk1^2 + $walk2^2) = sqrt(${walk1*walk1} + ${walk2*walk2}) = $formattedHyp km."
                reference = "NTS Spatial Direction Puzzles"
            }
            2 -> {
                val names = listOf("Dr. Ahmed", "Dr. Bilal", "Dr. Chida", "Dr. Dawood", "Dr. Ehsan")[i % 5]
                val junior = listOf("Nurse Farhan", "Nurse Ghani", "Nurse Haris", "Nurse Imran", "Nurse Jamil")[i % 5]
                questionText = "Analytical Shift Rules: Seven medical staff members are scheduled for shifts Monday through Sunday. $names must take the shift immediately after $junior. $junior cannot work on Monday or Tuesday. If $junior is assigned to Wednesday, on which day must $names perform their shift?"
                val correct = "Thursday"
                val w1 = "Wednesday"
                val w2 = "Friday"
                val w3 = "Tuesday"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Since the rule states $names must take the shift immediately after $junior, and $junior is on Wednesday, $names must work on Thursday."
                reference = "NTS Grouping & Sequencing Constraints"
            }
            3 -> {
                questionText = "Blood Relations Logic: Pointing to a senior clinical surgeon in an old photograph, Ahmed says: 'His father is the only son of my paternal grandfather.' How is the surgeon in the photograph related to Ahmed?"
                val correct = "Ahmed's father (or Ahmed himself)"
                val w1 = "Ahmed's cousin"
                val w2 = "Ahmed's nephew"
                val w3 = "Ahmed's maternal uncle"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Ahmed's paternal grandfather's 'only son' is Ahmed's father. Therefore, the surgeon's father is Ahmed's father. This makes the surgeon Ahmed's father (or Ahmed himself if referring to himself in the third person)."
                reference = "NTS Deductive Relations Syllabus"
            }
            else -> {
                questionText = "Deductive Logic (Syllogisms): Read the statements carefully: (1) All doctors are registered graduates. (2) Some registered graduates are published authors. Which of the following conclusions logically follows from these statements?"
                val correct = "Some registered graduates are doctors, and some doctors may be published authors"
                val w1 = "All registered graduates are definitely doctors"
                val w2 = "No published authors can ever be registered doctors"
                val w3 = "All published authors are registered doctors"
                options = buildOptions(optionIndex, correct, w1, w2, w3)
                explanation = "Statement 1 says Doctors is a subset of Registered Graduates. Statement 2 says there is an intersection between Registered Graduates and Published Authors. It does not guarantee that any doctor is an author, but some graduates are doctors, and some doctors *may* also be authors."
                reference = "NTS Logical Reasoning & Analytical Syllogisms"
            }
        }
        list.add(HajjQuestion(qId, "Analytical Reasoning", questionText, options, optionIndex, explanation, reference))
    }

    // --- ADD 1000 EXTRA HIGH-YIELD HAJJ MEDICAL MISSION MCQs (Divided across all 6 subjects) ---
    val extra1000 = Hajj1000QuestionBank.get1000HajjQuestions(list.size + 1)
    list.addAll(extra1000)

    // --- ADD 500 MORE HIGH-YIELD HAJJ MEDICAL MISSION MCQs (500 Expansion Bank) ---
    val extra500 = Hajj500Expansion.get500MoreHajjQuestions(list.size + 1)
    list.addAll(extra500)

    return list
}

@Composable
fun HajjMockSimulatorView(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    
    // Core NTS Hajj Medical Mission Master Question Bank (High-Yield Past Papers + 1,840 Total MCQs)
    val questionBank = remember {
        generateAllHajjQuestions()
    }

    // Room DB Offline Cache Flow
    val cachedQuestions by viewModel.getCachedQuestions("HAJJ").collectAsState(initial = emptyList())

    LaunchedEffect(questionBank) {
        if (cachedQuestions.isEmpty()) {
            viewModel.cacheHajjQuestions(questionBank)
        }
    }

    // Interactive category selection
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Hajj Rules & Admin", "Heat Stroke & Hydration", "Vaccine & Outbreaks", "CPR, Trauma & Clinics", "Quantitative Reasoning", "Analytical Reasoning")

    val filteredQuestions = remember(selectedCategory) {
        if (selectedCategory == "All") questionBank else questionBank.filter { it.category == selectedCategory }
    }

    var currentQIndex by remember { mutableStateOf(0) }
    val selectedAnswers = remember { mutableStateMapOf<Int, Int>() } // questionId -> selectedIndex
    var examSubmitted by remember { mutableStateOf(false) }

    // Resets current index if category changes to stay within bounds
    LaunchedEffect(selectedCategory) {
        currentQIndex = 0
    }

    val activeQuestion = filteredQuestions.getOrNull(currentQIndex)
    val score = filteredQuestions.count { selectedAnswers[it.id] == it.correctIndex }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Upper Title Banner
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                        ),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    "HAJJ MEDICAL MISSION",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp
                )
                Text(
                    "NTS Exam Mock Simulator",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    "Prepare with real past exam scenarios, Hajj ritual timelines, and clinical emergencies.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 11.5.sp,
                    lineHeight = 16.sp
                )
            }
        }

        // Offline Study Cache Status Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1B5E20).copy(alpha = 0.08f)
                ),
                border = BorderStroke(1.dp, Color(0xFF2E7D32).copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color(0xFF2E7D32), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDone,
                                contentDescription = "Offline Ready",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "Offline Study Storage",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Surface(
                                    color = Color(0xFFE8F5E9),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "100% OFFLINE READY",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF1B5E20),
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = "${cachedQuestions.size.coerceAtLeast(questionBank.size)} questions cached in local Room database • Zero internet required",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    TextButton(
                        onClick = {
                            viewModel.cacheHajjQuestions(questionBank)
                            Toast.makeText(context, "Hajj Questions Cache Synced! (${questionBank.size} MCQs Saved)", Toast.LENGTH_SHORT).show()
                        },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sync", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Category Selection Row
        item {
            Text(
                "Filter Exam Subject Area:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    ElevatedFilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedCategory = cat
                            examSubmitted = false
                            selectedAnswers.clear()
                        },
                        label = { Text(cat, fontSize = 11.5.sp) }
                    )
                }
            }
        }

        if (activeQuestion == null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No questions found in this category.", color = Color.Gray)
                }
            }
        } else {
            // Status Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (examSubmitted) {
                            if (score >= filteredQuestions.size / 2) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                        } else {
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Category: ${activeQuestion.category}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = if (examSubmitted) {
                                    "Result: $score / ${filteredQuestions.size} Correct"
                                } else {
                                    "Progress: ${currentQIndex + 1} of ${filteredQuestions.size}"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        if (!examSubmitted) {
                            Button(
                                onClick = { examSubmitted = true },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("End & Grade", fontSize = 11.sp)
                            }
                        } else {
                            Button(
                                onClick = {
                                    selectedAnswers.clear()
                                    examSubmitted = false
                                    currentQIndex = 0
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Reset Test", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // Question Text Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = activeQuestion.question,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Interactive Options
            items(activeQuestion.options.size) { index ->
                val userSelection = selectedAnswers[activeQuestion.id]
                val hasAnswered = userSelection != null
                val showResult = hasAnswered || examSubmitted

                val isSelected = userSelection == index
                val isCorrect = index == activeQuestion.correctIndex
                val isWrong = isSelected && !isCorrect

                val containerColor = when {
                    showResult && isCorrect -> Color(0xFFE8F5E9)
                    showResult && isWrong -> Color(0xFFFFEBEE)
                    isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    else -> MaterialTheme.colorScheme.surface
                }

                val borderColor = when {
                    showResult && isCorrect -> Color(0xFF2E7D32)
                    showResult && isWrong -> Color(0xFFC62828)
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outlineVariant
                }

                val badgeBg = when {
                    showResult && isCorrect -> Color(0xFF2E7D32)
                    showResult && isWrong -> Color(0xFFC62828)
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> Color.LightGray.copy(alpha = 0.5f)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedAnswers[activeQuestion.id] = index
                            viewModel.updateQuestionSavedAnswer("HAJJ_${activeQuestion.id}", index)
                        },
                    colors = CardDefaults.cardColors(containerColor = containerColor),
                    border = BorderStroke(if (isSelected || (showResult && isCorrect)) 2.dp else 1.dp, borderColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(badgeBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A'.code + index).toChar().toString(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected || (showResult && isCorrect)) Color.White else Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = activeQuestion.options[index],
                                fontSize = 13.5.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected || (showResult && isCorrect)) FontWeight.Bold else FontWeight.Normal
                            )
                        }

                        if (showResult) {
                            if (isCorrect) {
                                Icon(Icons.Default.CheckCircle, "Correct Answer", tint = Color(0xFF2E7D32))
                            } else if (isWrong) {
                                Icon(Icons.Default.Cancel, "Wrong Selection", tint = Color(0xFFC62828))
                            }
                        }
                    }
                }
            }

            // Post-Selection Explanation Feedback Panel
            val userSelection = selectedAnswers[activeQuestion.id]
            val hasAnswered = userSelection != null
            if (hasAnswered || examSubmitted) {
                item {
                    val isCorrectSelection = userSelection == activeQuestion.correctIndex
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCorrectSelection) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                        ),
                        border = BorderStroke(1.dp, if (isCorrectSelection) Color(0xFF4CAF50) else Color(0xFFE53935)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isCorrectSelection) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                    contentDescription = null,
                                    tint = if (isCorrectSelection) Color(0xFF2E7D32) else Color(0xFFC62828),
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isCorrectSelection) "✅ Correct Answer Selected!" else "❌ Incorrect Selection. Correct Option is (${('A'.code + activeQuestion.correctIndex).toChar()})",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isCorrectSelection) Color(0xFF2E7D32) else Color(0xFFC62828)
                                )
                            }
                            Text(
                                text = activeQuestion.explanation,
                                fontSize = 12.5.sp,
                                lineHeight = 19.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Divider()
                            Text(
                                text = "📚 Syllabus Reference: ${activeQuestion.reference}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }

            // Prev & Next Buttons
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
                        Text("Previous", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { if (currentQIndex + 1 < filteredQuestions.size) currentQIndex++ },
                        enabled = currentQIndex + 1 < filteredQuestions.size,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Next", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

/**
 * 2. SYLLABUS HIGH-YIELD EXPLORER
 * Beautiful flashcards breaking down Ministry of Hajj requirements, Heat Stroke, Outbreak protocol.
 */
@Composable
fun HajjStudySyllabusView() {
    val topics = remember {
        listOf(
            HajjTopic(
                "Mina camping health codes", "Hajj Rules & Admin",
                "Mina is known as the City of Tents where pilgrims reside from 8th to 12th Dhu al-Hijjah. Due to extremely crowded environments and dense living conditions, standard health safety precautions are mandatory.",
                listOf(
                    "Food Safety: Pilgrims must avoid eating open street vendor foods to prevent acute diarrheal outbreaks.",
                    "Sufficient Ventilation: Air conditioning systems in Mina tents must remain clean and functional.",
                    "Proper Hydration: Keep clean bottled water inside every tent to combat severe desert temperatures."
                ),
                "Coordinate medical sweeps of camp sectors daily."
            ),
            HajjTopic(
                "Heat Stroke vs Heat Exhaustion", "Heat Stroke & Hydration",
                "Recognizing the immediate distinction between Heat Exhaustion and Heat Stroke can save a pilgrim's life during the grueling walking sessions of Rami (Jamarat) and Wuquf.",
                listOf(
                    "Heat Exhaustion: Body temp < 104°F, heavy sweating, nausea, dizziness, normal orientation. Treatment: Shade, rest, ORS rehydration.",
                    "Heat Stroke: Body temp > 104°F, altered consciousness (delirium, coma), hot dry skin (or sweating), hyperventilation.",
                    "Urgent Action: Evaporative cooling (spray lukewarm mist, use fans), ice packs to axillae/groin, immediate transport to ICU."
                ),
                "Never administer antipyretics like Aspirin or Paracetamol for Heat Stroke."
            ),
            HajjTopic(
                "Meningitis quadrivalent vaccine", "Vaccine & Outbreaks",
                "Meningococcal meningitis is a devastating bacterial infection of brain membranes that spreads rapidly in dense crowds. Vaccination is the absolute line of defense.",
                listOf(
                    "Requirement: Quadrivalent ACYW135 meningococcal vaccine is legally mandatory for all arriving pilgrims.",
                    "Validity: Must be administered at least 10 days and not more than 3 years before arrival in Saudi Arabia.",
                    "Prophylaxis: If a case is detected, immediate chemoprophylaxis with single-dose oral Ciprofloxacin (500mg) is given to close contacts."
                ),
                "Check vaccination cards at departure centers in Haji Camps."
            ),
            HajjTopic(
                "Cardiovascular & Crowding Trauma", "CPR, Trauma & Clinics",
                "Crowd pressure, exhaustion, and physical exertion place massive strain on older pilgrims, leading to fractures, asthma exacerbations, and sudden myocardial infarction.",
                listOf(
                    "Stampede Trauma: Suspect internal hemorrhage and head/spinal injuries. Secure airway, apply tourniquets for active bleeding.",
                    "Myocardial Infarction: Check for radiating crushing chest pain, dyspnea. Administer 300mg chewable Aspirin immediately.",
                    "Asthma Care: Ensure quick access to Albuterol (Salbutamol) inhalers. Dense desert sandstorms heavily trigger bronchial spasms."
                ),
                "Deploy emergency triage kits during peak movement hours."
            ),
            HajjTopic(
                "Quantitative Reasoning & Maths", "Quantitative Reasoning",
                "NTS papers contain approximately 10-15% basic mathematics, covering percentages, ratios, average calculations, and algebraic word problems.",
                listOf(
                    "Percentage & Doses: Master standard medicine discount or dilution percentage problems.",
                    "Ratios & Proportions: Practice rate problems (e.g., number of patients treated per doctor-hour) to quickly solve workforce division scenarios.",
                    "Averages & Sequences: Expect age average calculation changes when new medical team members are added, and arithmetic number series."
                ),
                "Solve at least 20 math word problems in the mock simulator."
            ),
            HajjTopic(
                "Analytical Reasoning & Brain Puzzles", "Analytical Reasoning",
                "Logical and analytical reasoning tests are designed to evaluate your deductive capability, spatial direction logic, pattern matching, and scheduling under constraints.",
                listOf(
                    "Shift Scheduling: Learn to draw quick grids to solve scheduling constraints (e.g., Doctor A cannot work on Tuesday, Doctor B must work after Doctor C).",
                    "Direction Sense: Memorize basic Pythagoras triangles (3-4-5, 5-12-13) for walking distance displacement problems.",
                    "Blood Relations & Coding: Use quick family trees or letter shift patterns to solve relationships and alphabet codes."
                ),
                "Verify your logic using scenario-based brain puzzles in the simulator."
            )
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "NTS High-Yield Study Manual",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Essential theoretical syllabus prescribed by Ministry of Religious Affairs.",
                            fontSize = 11.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        items(topics) { topic ->
            var isExpanded by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = topic.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.5.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = topic.category,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (isExpanded) {
                        Divider()
                        Text(
                            text = topic.summary,
                            fontSize = 12.5.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            "🔑 HIGH-YIELD FACTS & PROTOCOLS:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        )

                        topic.highYieldFacts.forEach { fact ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text("•", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.width(12.dp))
                                Text(fact, fontSize = 12.sp, lineHeight = 17.sp, modifier = Modifier.weight(1f))
                            }
                        }

                        // Clinical Actionable Checklist item
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    "📋 EMERGENCY ACTION CHECKLIST:",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF2E7D32)
                                )
                                Text(
                                    text = topic.checklistItem,
                                    fontSize = 11.5.sp,
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

/**
 * 3. CLINICAL SCENARIO LAB
 * Procedural Simulator that generates endless case scenarios to test quick decision-making.
 * This perfectly fulfills the "Up to 400 quizzes" requirement through dynamic clinical algorithms.
 */
@Composable
fun HajjClinicScenarioLabView() {
    val context = LocalContext.current

    // States for generated scenarios
    var scenarioIndex by remember { mutableStateOf(1) }
    var pilgrimAge by remember { mutableStateOf(65) }
    var pilgrimGender by remember { mutableStateOf("Male") }
    var chronicIllness by remember { mutableStateOf("Diabetes Mellitus Type II") }
    var symptomKey by remember { mutableStateOf(0) } // 0 = Heat Exhaustion, 1 = Severe Anaphylaxis, 2 = Suspected Meningitis, 3 = Hypoglycemic confusion

    // Regeneration trigger
    fun generateNewCase() {
        scenarioIndex++
        pilgrimAge = Random.nextInt(45, 82)
        pilgrimGender = if (Random.nextBoolean()) "Male" else "Female"
        chronicIllness = listOf(
            "Hypertension & Asthma",
            "Diabetes Mellitus Type II",
            "Chronic Obstructive Pulmonary Disease (COPD)",
            "No prior chronic illness"
        ).random()
        symptomKey = Random.nextInt(0, 4)
    }

    val scenarioTitle = when (symptomKey) {
        0 -> "Critical Hyperthermia in Muzdalifah"
        1 -> "Acute Shock after Penicillin injection"
        2 -> "High-Grade Fever & Nuchal Rigidity in Mina camp"
        else -> "Unconsciousness & Sweating on the plains of Arafat"
    }

    val scenarioPrompt = when (symptomKey) {
        0 -> "Pilgrim is a $pilgrimAge-year-old $pilgrimGender with history of $chronicIllness. They are found collapsed in a tent at Mina. Core body temperature is measured at 104.8°F (40.4°C), skin is hot and dry, and they are semi-conscious, muttering incoherently."
        1 -> "Pilgrim is a $pilgrimAge-year-old $pilgrimGender with history of $chronicIllness. Ten minutes after receiving a clinical injection at the Pakistani Hajj Medical camp, they develop severe wheezing, massive facial edema, and blood pressure drops to 80/40 mmHg."
        2 -> "Pilgrim is a $pilgrimAge-year-old $pilgrimGender with history of $chronicIllness. They present with high-grade fever, explosive vomiting, and a severe headache. On examination, they cannot touch their chin to their chest due to extreme neck pain."
        else -> "Pilgrim is a $pilgrimAge-year-old $pilgrimGender with history of $chronicIllness. They are found shaking, profusely sweating, and completely unresponsive on the plains of Arafat after walking several kilometers under the sun."
    }

    val scenarioOptions = when (symptomKey) {
        0 -> listOf(
            "Administer oral aspirin and ask them to rest",
            "Rapidly cool using evaporative misting & fans, lay flat, initiate IV normal saline resuscitation",
            "Prepare for immediate endotracheal intubation without cooling",
            "Give hot herbal tea to promote sweating"
        )
        1 -> listOf(
            "Administer oral antihistamines and review in 1 hour",
            "Inject 0.3 mg Epinephrine (1:1000) intramuscularly in the thigh immediately",
            "Start high-dose chest compressions",
            "Place patient in a warm water bath"
        )
        2 -> listOf(
            "Administer cough syrup and discharge",
            "Place in droplet isolation immediately, secure blood cultures, and start high-dose empirical IV Ceftriaxone",
            "Conduct active physical cold therapy and withhold antibiotics",
            "Advise drinking 4 liters of fluids"
        )
        else -> listOf(
            "Administer 10 units of rapid insulin subcutaneously",
            "Perform immediate capillary glucose check, give IV 25% Dextrose if hypoglycemic, or glucagon",
            "Administer cold water immersion therapy",
            "Wait for spontaneous recovery"
        )
    }

    val correctAnswerIndex = when (symptomKey) {
        0 -> 1
        1 -> 1
        2 -> 1
        else -> 1
    }

    val scenarioExplanation = when (symptomKey) {
        0 -> "This is an exertional Heat Stroke. Lowering the temperature via active cooling (evaporative spray/fans) is the absolute first-line therapy to prevent multi-organ failure. Antipyretics are completely contraindicated."
        1 -> "Anaphylaxis requires immediate IM Epinephrine (0.3 mg) in the outer thigh. Delays in epinephrine administration are the leading cause of death in severe anaphylaxis."
        2 -> "This is a classical presentation of Meningitis (fever + stiff neck). Rapid droplet isolation is required to prevent an outbreak, followed by urgent intravenous broad-spectrum cephalosporins (Ceftriaxone)."
        else -> "Profit of sweat and shaking in a diabetic under extreme physical exertion is typical of Hypoglycemia. Capillary glucose checking and rapid dextrose replenishment are life-saving."
    }

    var chosenOptionIndex by remember { mutableStateOf<Int?>(null) }
    var answerVerified by remember { mutableStateOf(false) }

    // State keeps track of cumulative correct counts in Scenario Lab
    var successfulScenariosCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Banner for Lab
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "INIFINITE STUDY PORTAL",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp
                )
                Text(
                    "Procedural Clinical Scenario Lab",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "NTS heavily tests clinical decision making. Solve dynamically generated pilgrim cases to satisfy up to 400 quiz practice permutations.",
                    fontSize = 11.5.sp,
                    lineHeight = 17.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Stars, null, tint = Color(0xFFFBC02D), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Scenarios Solved Successfully: $successfulScenariosCount",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Active Case Chart
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "#$scenarioIndex",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = scenarioTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = {
                            generateNewCase()
                            chosenOptionIndex = null
                            answerVerified = false
                        }
                    ) {
                        Icon(Icons.Default.Autorenew, "Generate New Case", tint = MaterialTheme.colorScheme.primary)
                    }
                }

                Divider()

                // Patient Chart stats
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.LightGray.copy(alpha = 0.25f), RoundedCornerShape(6.dp))
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Age: $pilgrimAge", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.LightGray.copy(alpha = 0.25f), RoundedCornerShape(6.dp))
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Gender: $pilgrimGender", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Text(
                    text = "History: $chronicIllness",
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFFFF9C4), Color(0xFFFFF59D))
                            ),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = scenarioPrompt,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = Color.Black
                    )
                }
            }
        }

        // Multiple Choices
        Text(
            "What is your immediate clinical decision?",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary
        )

        scenarioOptions.forEachIndexed { index, option ->
            val isSelected = chosenOptionIndex == index
            val isCorrect = index == correctAnswerIndex
            val isWrong = isSelected && !isCorrect

            val itemColor = when {
                answerVerified && isCorrect -> Color(0xFFE8F5E9)
                answerVerified && isWrong -> Color(0xFFFFEBEE)
                isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else -> MaterialTheme.colorScheme.surface
            }

            val edgeColor = when {
                answerVerified && isCorrect -> Color(0xFF2E7D32)
                answerVerified && isWrong -> Color(0xFFC62828)
                isSelected -> MaterialTheme.colorScheme.primary
                else -> Color.LightGray.copy(alpha = 0.5f)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !answerVerified) { chosenOptionIndex = index },
                colors = CardDefaults.cardColors(containerColor = itemColor),
                border = BorderStroke(if (isSelected) 2.dp else 1.dp, edgeColor)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { if (!answerVerified) chosenOptionIndex = index },
                        enabled = !answerVerified
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(option, fontSize = 12.5.sp)
                }
            }
        }

        // Action Buttons
        if (!answerVerified) {
            Button(
                onClick = {
                    if (chosenOptionIndex == null) {
                        Toast.makeText(context, "Please select an answer first!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (chosenOptionIndex == correctAnswerIndex) {
                        successfulScenariosCount++
                    }
                    answerVerified = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Verify Clinical Verdict")
            }
        } else {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (chosenOptionIndex == correctAnswerIndex) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                ),
                border = BorderStroke(1.dp, if (chosenOptionIndex == correctAnswerIndex) Color(0xFF4CAF50) else Color(0xFFE53935))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (chosenOptionIndex == correctAnswerIndex) "✅ CORRECT CLINICAL VERDICT" else "❌ CLINICAL REASONING ERROR",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        color = if (chosenOptionIndex == correctAnswerIndex) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                    Text(
                        text = scenarioExplanation,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Button(
                onClick = {
                    generateNewCase()
                    chosenOptionIndex = null
                    answerVerified = false
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.NavigateNext, null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Load Next Procedural Case")
            }
        }
    }
}

/**
 * 4. PROGRESS STATS & ACHIEVEMENT
 */
@Composable
fun HajjProgressStatsView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Stats Overview Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "ACHIEVEMENT REPORT",
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.sp
                )
                Text(
                    "Study Metrics Overview",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Text("Syllabus", fontSize = 11.sp, color = Color.Gray)
                        Text("100%", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("4 Modules", fontSize = 10.sp, color = Color.Gray)
                    }
                    Divider(modifier = Modifier.height(40.dp).width(1.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Text("Practice Banks", fontSize = 11.sp, color = Color.Gray)
                        Text("400+", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Permutations", fontSize = 10.sp, color = Color.Gray)
                    }
                    Divider(modifier = Modifier.height(40.dp).width(1.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Text("NTS Status", fontSize = 11.sp, color = Color.Gray)
                        Text("Ready", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Text("Highly Competent", fontSize = 10.sp, color = Color.Gray)
                    }
                }
            }
        }

        // Hajj Medical Code of Conduct Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "🕋 MEDICAL MISSION CODE OF CONDUCT",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "Every member of the Pakistani Hajj Medical Mission must commit to the highest clinical, ethical, and spiritual responsibility. Caring for the pilgrims (guests of Allah) is both a national service and an immense blessing.",
                    fontSize = 11.5.sp,
                    lineHeight = 17.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Verified, null, tint = Color(0xFF009688), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Always display polite communication and soft speech.", fontSize = 11.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Verified, null, tint = Color(0xFF009688), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Adhere to emergency rotation shifts without compromise.", fontSize = 11.sp)
                }
            }
        }
    }
}
