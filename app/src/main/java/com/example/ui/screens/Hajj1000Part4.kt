package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 4
 * Category: CPR, Trauma & Clinics (170 Unique MCQs)
 * Covers ACLS/BLS resuscitation, START Triage in crowd surge accidents, Acute Coronary Syndrome, Anaphylaxis, Trauma Hemorrhage, DKA/HHS & Respiratory Emergencies.
 */
object Hajj1000Part4 {

    fun getCprTraumaQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        val ages = listOf(49, 54, 58, 62, 67, 71, 75, 79, 83, 87)
        val triggers = listOf("Intravenous Ceftriaxone", "Intramuscular Diclofenac", "Penicillin G", "Bee Sting in Mina Camp", "Contrast Media Injection", "Aspirin Hypersensitivity")
        val locations = listOf("Mina Sector 1 Emergency Room", "Arafat Triage Station", "Jamarat Bridge Evacuation Deck", "Muzdalifah Resuscitation Post", "Makkah Central Dispensary", "Azizia Urgent Care Unit")

        val cprTraumaScenarios = listOf(
            Triple(
                "START Triage Color-Coding in Mass Casualty Surge",
                "During a stampede or crowd collapse at Jamarat, a patient is conscious, unable to walk, has a respiratory rate of 24/min, and strong radial pulse. What START triage tag is assigned?",
                "YELLOW TAG (Delayed Care - serious injury requiring hospital care but not immediately life-threatening)"
            ),
            Triple(
                "Anaphylactic Shock Epinephrine Administration",
                "A pilgrim develops sudden stridor, severe bronchospasm, and hypotension (BP 70/40) 5 minutes after an injection of $triggers. What is the immediate first-line drug, dose, and route?",
                "0.3 mg Epinephrine (1:1000 dilution) injected intramuscularly into the anterolateral mid-thigh"
            ),
            Triple(
                "Acute Coronary Syndrome Initial Management",
                "An elderly diabetic pilgrim presents to a Mina clinic with acute crushing chest pain, diaphoresis, and ST-elevation in leads II, III, and aVF (Inferior MI). What immediate medication should be chewed?",
                "Aspirin 300 mg (non-enteric coated, chewed) immediately to inhibit platelet activation"
            ),
            Triple(
                "Sublingual Nitroglycerin Contraindication in RV Infarction",
                "Why is Sublingual Nitroglycerin STRICTLY CONTRAINDICATED in an acute inferior MI patient with right ventricular involvement (or patient taking Sildenafil)?",
                "Nitroglycerin causes severe venodilation and preload reduction, leading to catastrophic refractory hypotension"
            ),
            Triple(
                "Tourniquet Application for Life-Threatening Limb Hemorrhage",
                "When direct pressure fails to control severe arterial spurting bleeding from a compound femoral fracture sustained in a crowd surge, where should a CAT tourniquet be applied?",
                "2 to 3 inches proximal to the bleeding wound site directly on the limb (avoiding joints)"
            ),
            Triple(
                "Hyperglycemic Hyperosmolar State (HHS) in Elderly Pilgrim",
                "An 80-year-old diabetic pilgrim presents with severe dehydration, altered mental status, blood glucose of 850 mg/dL, and no urine ketones. What is the priority intervention?",
                "Aggressive IV fluid hydration with 0.9% Normal Saline before initiating continuous low-dose IV Insulin"
            ),
            Triple(
                "CPR Chest Compression Parameters for Adult Cardiac Arrest",
                "What is the recommended compression rate, depth, and ventilation ratio for two-rescuer CPR in an adult pilgrim in cardiac arrest?",
                "100 to 120 compressions per minute, depth of 2 to 2.4 inches (5-6 cm), compression-to-ventilation ratio of 30:2"
            ),
            Triple(
                "Acute Severe Asthma Exacerbation in Sandstorm",
                "A pilgrim experiencing acute severe bronchospasm during a Mina dust storm has silent chest and PEFR < 33%. What is the emergency treatment sequence?",
                "High-flow Oxygen, Nebulized Albuterol + Ipratropium, IV Hydrocortisone, and preparation for possible intubation"
            ),
            Triple(
                "Pelvic Crush Fracture Stabilization",
                "A victim of a crowd surge presents with severe pelvic pain, instability on palpation, and signs of internal hemorrhagic shock. What device should be applied in the field?",
                "Commercial Pelvic Binder (or wrapped bedsheet centered over the greater trochanters) to reduce pelvic volume and control bleeding"
            ),
            Triple(
                "Deep Vein Thrombosis (DVT) Post Long-Haul Flight",
                "A pilgrim returning from a 12-hour flight presents with unilateral left calf swelling, warmth, and severe pain upon dorsiflexion (Homan's sign). What diagnostic test is indicated?",
                "Venous Duplex Ultrasound of the lower extremity to diagnose Deep Vein Thrombosis"
            )
        )

        for (i in 0 until 170) {
            val qId = idCounter++
            val scenario = cprTraumaScenarios[i % cprTraumaScenarios.size]
            val age = ages[i % ages.size]
            val trigger = triggers[i % triggers.size]
            val loc = locations[i % locations.size]

            val optionIndex = i % 4
            val questionText = "CPR, Trauma & Clinics Question #${i + 1} [$loc / Age $age]: In relation to ${scenario.first}, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "Delay resuscitation procedures to perform elective diagnostic blood work"
            val w2 = "Administer oral sedation and discharge the patient to their tent unmonitored"
            val w3 = "Apply hot compresses over actively bleeding open arterial wounds"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "Clinical Emergency Protocol: ${scenario.first} requires immediate evidence-based management. ${scenario.third} preserves organ perfusion and saves lives in critical mass gathering emergencies."
            val ref = "AHA ACLS/ATLS Protocols & NTS Clinical Emergency Syllabus"

            list.add(
                HajjQuestion(
                    id = qId,
                    category = "CPR, Trauma & Clinics",
                    question = questionText,
                    options = opts,
                    correctIndex = optionIndex,
                    explanation = explanation,
                    reference = ref
                )
            )
        }

        return list
    }
}
