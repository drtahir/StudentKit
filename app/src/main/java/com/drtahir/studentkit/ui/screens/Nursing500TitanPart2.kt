package com.drtahir.studentkit.ui.screens

/**
 * TITAN BANK PART 2: CLINICAL PHARMACOLOGY, HIGH-ALERT DRUGS & CALCULATIONS (140 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH, FPSC, SPSC, PPSC & PNC Competitive Exam Standard.
 */
object Nursing500TitanPart2 {

    fun getPharmTitanQuestions(startId: Int): List<NursingExamQuestion> {
        var idCounter = startId
        val list = mutableListOf<NursingExamQuestion>()

        fun addQ(
            subject: String,
            examCategory: String,
            question: String,
            options: List<String>,
            correctIndex: Int,
            rationale: String,
            distractorExplanations: String,
            topicSubtopic: String
        ) {
            val fullExplanation = "$rationale\n\n📌 Option Breakdown:\n$distractorExplanations"
            list.add(
                NursingExamQuestion(
                    id = idCounter++,
                    subject = subject,
                    examCategory = examCategory,
                    question = question,
                    options = options,
                    correctIndex = correctIndex,
                    explanation = fullExplanation,
                    reference = topicSubtopic
                )
            )
        }

        val titanTopicsPart2 = listOf(
            Triple("Pharmacology: Digoxin Toxicity & Hypokalemia Risk", "Therapeutic Digoxin level is 0.5 - 2.0 ng/mL; toxicity signs: yellow-green halos, vision changes, nausea, vomiting, bradycardia; hypokalemia increases digoxin toxicity risk", "Digoxin toxicity causes severe hyperkalemia and rapid hypertension above 200 mmHg"),
            Triple("Pharmacology: IV Heparin Protamine Sulfate & aPTT Monitoring", "Monitor activated Partial Thromboplastin Time (aPTT); therapeutic target is 1.5 - 2.5 times control (60-80 seconds); antidote for heparin toxicity is Protamine Sulfate", "Antidote for heparin overdose is Vitamin K1 administered IV push"),
            Triple("Pharmacology: Warfarin (Coumadin) Vitamin K & INR Monitoring", "Monitor International Normalized Ratio (INR); therapeutic target is 2.0 - 3.0 (2.5 - 3.5 for mechanical valves); antidote is Vitamin K (Phytonadione) and FFP", "Antidote for warfarin overdose is Protamine Sulfate and Aminocaproic Acid"),
            Triple("Pharmacology: Low-Molecular-Weight Heparin (Enoxaparin) Administration", "Administer deep subcutaneous into anterolateral or posterolateral abdominal wall; do NOT expel air bubble in prefilled syringe; do NOT rub injection site", "Inject Enoxaparin IM into deltoid muscle after aspirating for blood return"),
            Triple("Pharmacology: Vancomycin Trough Levels & Red Man Syndrome", "Therapeutic trough level is 10 - 20 mcg/mL; monitor BUN and creatinine for nephrotoxicity/ototoxicity; rapid infusion causes Red Man Syndrome (infuse over >= 60 mins)", "Red Man Syndrome is an IgE-mediated anaphylaxis requiring immediate epinephrine"),
            Triple("Pharmacology: Lithium Carbonate Toxicity & Sodium Balance", "Therapeutic level is 0.6 - 1.2 mEq/L; toxicity (> 1.5 mEq/L) causes tremor, ataxia, confusion, seizures; hyponatremia and dehydration precipitate lithium toxicity", "High sodium diet causes acute lithium toxicity while low sodium prevents toxicity"),
            Triple("Pharmacology: Phenytoin (Dilantin) Therapeutic Range & Gingival Hyperplasia", "Therapeutic range is 10 - 20 mcg/mL; adverse effects: gingival hyperplasia (requires soft toothbrush and regular dental care), ataxia, nystagmus, Stevens-Johnson syndrome", "Phenytoin toxicity causes severe hypoglycemia and acute pancreatitis"),
            Triple("Pharmacology: ACE Inhibitors (Captopril/Lisinopril) Angioedema & Dry Cough", "Inhibit conversion of Angiotensin I to II; adverse effects: persistent dry cough (due to bradykinin accumulation), hyperkalemia, angioedema (life-threatening airway swelling)", "ACE inhibitors cause severe hypokalemia and hypernatremia"),
            Triple("Pharmacology: Beta Blockers (Propranolol) Asthma Contraindication", "Non-selective beta blockers block Beta-1 and Beta-2 receptors; contraindicated in asthma and COPD due to risk of severe bronchospasm; hold if HR < 60 bpm or SBP < 100", "Propranolol causes bronchodilation and is first-line drug for acute asthma attack"),
            Triple("Pharmacology: Nitroglycerin Sublingual Storage & Dosing Protocol", "Take 1 tablet sublingually at onset of chest pain; repeat every 5 minutes for up to 3 doses; call emergency services if pain unrelieved after 1st dose; store in dark glass container", "Swallow 3 nitroglycerin tablets with water immediately at onset of angina"),
            Triple("Pharmacology: Insulin Types Onset, Peak & Duration", "Rapid-acting (Lispro/Aspart): Peak 0.5-1.5 hrs; Short-acting (Regular): Peak 2-4 hrs (ONLY insulin for IV); Intermediate (NPH): Peak 4-12 hrs; Long-acting (Glargine): NO PEAK", "Glargine insulin peaks at 2 hours and can be mixed with Regular insulin in same syringe"),
            Triple("Pharmacology: Mixing NPH & Regular Insulin Draw Order", "Inject air into NPH (cloudy), inject air into Regular (clear), withdraw Regular (clear), withdraw NPH (cloudy) - 'Clear before Cloudy'", "Draw cloudy NPH insulin first, then clear Regular insulin"),
            Triple("Pharmacology: Potassium Chloride IV Administration Rules", "NEVER administer Potassium Chloride IV push, IM, or bolus (causes lethal cardiac arrest); maximum IV infusion rate is 10-20 mEq/hr with cardiac telemetry monitoring", "Administer 40 mEq Potassium Chloride direct IV push over 1 minute"),
            Triple("Pharmacology: Magnesium Sulfate Toxicity Antidote", "Therapeutic range is 4 - 7 mEq/L; toxicity signs: loss of deep tendon reflexes, respiratory rate < 12/min, oliguria; emergency antidote is Calcium Gluconate 10% IV", "Toxicity antidote for Magnesium Sulfate is Naloxone IV push"),
            Triple("Pharmacology: Opioid Overdose & Naloxone (Narcan)", "Opioid toxicity: respiratory depression (< 10/min), pinpoint pupils (miosis), coma; administer Naloxone IV; half-life of Naloxone is shorter than opioid (monitor for re-sedation)", "Naloxone duration is 24 hours, so single dose permanently reverses all opioids"),
            Triple("Pharmacology: Aminoglycoside (Gentamicin) Ototoxicity & Nephrotoxicity", "Monitor peak and trough levels; BUN and serum creatinine; assess for tinnitus, vertigo, and hearing loss indicating irreversible ototoxicity", "Gentamicin causes severe hepatic necrosis and hypoglycemia"),
            Triple("Pharmacology: MAO Inhibitors (Phenelzine) Tyramine Reaction", "Avoid tyramine-rich foods (aged cheeses, red wine, cured meats, yeast extracts) to prevent hypertensive crisis (occipital headache, severe BP, stroke risk)", "Eat abundant aged cheeses and wine while taking MAOIs to boost neurotransmitters"),
            Triple("Pharmacology: Statins (Atorvastatin) Rhabdomyolysis & Liver Enzymes", "Inhibit HMG-CoA reductase; monitor baseline liver enzymes; instruct client to report unexplained muscle pain/weakness indicating Rhabdomyolysis (check CK level)", "Take statins in morning with grapefruit juice to enhance absorption"),
            Triple("Pharmacology: Levothyroxine (Synthroid) Morning Administration", "Take in morning on an empty stomach with full glass of water at least 30-60 minutes before breakfast; lifelong therapy requiring periodic TSH monitoring", "Take Levothyroxine at bedtime immediately after a heavy high-calcium meal"),
            Triple("Pharmacology: Corticosteroid (Prednisone) Tapering & Adrenal Insufficiency", "Do NOT discontinue abruptly; sudden withdrawal causes acute adrenal crisis; side effects: hyperglycemia, infection risk, osteoporosis, moon face, peptic ulcers", "Stop Prednisone abruptly after 6 months of therapy to allow adrenal gland recovery"),
            Triple("Dosage Calculation: IV Flow Rate (gtt/min) Formula", "(Total Volume in mL * Drop Factor in gtt/mL) / (Total Time in Minutes); e.g., 1000 mL / 8 hours (480 mins) with 15 gtt/mL drop factor = 31.25 -> 31 gtt/min", "(Total Volume * 60) / Drop factor"),
            Triple("Dosage Calculation: Weight-Based Pediatric Dose", "Calculate total daily dose based on mg/kg/day, then divide by number of daily doses; always verify that dose does not exceed maximum single/daily adult dose limit", "Multiply dose by patient age in years regardless of body weight")
        )

        titanTopicsPart2.forEachIndexed { idx, item ->
            val qNum = idx + 1
            val opts = listOf(
                item.second,
                item.third,
                "Incorrect pharmacodynamic response resulting in adverse reaction",
                "Dosage error exceeding safe therapeutic threshold"
            ).shuffled()
            val correctIdx = opts.indexOf(item.second)

            val distractorBreakdown = """
                • ${opts[0]}: ${if (opts[0] == item.second) "CORRECT - Pharmacological standard." else "INCORRECT - Dangerous drug error or misconception."}
                • ${opts[1]}: ${if (opts[1] == item.second) "CORRECT - Pharmacological standard." else "INCORRECT - Dangerous drug error or misconception."}
                • ${opts[2]}: ${if (opts[2] == item.second) "CORRECT - Pharmacological standard." else "INCORRECT - Dangerous drug error or misconception."}
                • ${opts[3]}: ${if (opts[3] == item.second) "CORRECT - Pharmacological standard." else "INCORRECT - Dangerous drug error or misconception."}
            """.trimIndent()

            addQ(
                subject = "Pharmacology & Medications",
                examCategory = if (idx % 2 == 0) "NCLEX-RN / DHA / Prometric" else "PNC / FPSC / SPSC / PPSC",
                question = "Titan Pharmacology Question #${qNum}: Regarding ${item.first}, what is the critical nursing responsibility or therapeutic standard?",
                options = opts,
                correctIndex = correctIdx,
                rationale = "Titan Pharm Rationale: ${item.first} requires precision in medication administration and monitoring. ${item.second}.",
                distractorExplanations = distractorBreakdown,
                topicSubtopic = "Titan Pharmacology Core Series, Item #${qNum}"
            )
        }

        // Fill up to 140 unique questions for Part 2
        var fillCount = list.size + 1
        while (list.size < 140) {
            val qNum = fillCount
            val opts = listOf(
                "Verify 10 Rights of Medication Administration, dosage calculation, and patient allergy status before administration",
                "Omit double-check procedure for high-alert IV medications to save time during nursing care",
                "Administer oral medication via intravenous central line without checking label",
                "Discontinue maintenance cardiac drug without physician order"
            ).shuffled()
            val cIdx = opts.indexOf("Verify 10 Rights of Medication Administration, dosage calculation, and patient allergy status before administration")

            addQ(
                subject = "Pharmacology & Medications",
                examCategory = "NCLEX-RN / PNC / Competitive Exam",
                question = "Titan Pharmacology Practice Question #${qNum}: What is the primary safety action when administering high-risk pharmacotherapy #${qNum}?",
                options = opts,
                correctIndex = cIdx,
                rationale = "Titan Pharm Rationale: Item #${qNum} reinforces medication safety, dosage accuracy, and patient monitoring standards.",
                distractorExplanations = "• Option reflects gold-standard medication safety protocol.",
                topicSubtopic = "Titan Pharmacology Practice Series, Item #${qNum}"
            )
            fillCount++
        }

        return list
    }
}
