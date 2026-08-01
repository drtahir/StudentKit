package com.example.ui.screens

/**
 * SUPREME BANK PART 2: ADVANCED CLINICAL PHARMACOLOGY, HIGH-ALERT DRUGS & CALCULATIONS (140 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500SupremePart2 {

    fun getPharmSupremeQuestions(startId: Int): List<NursingExamQuestion> {
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

        val supremeTopicsPart2 = listOf(
            Triple("High-Alert: Digoxin Toxicity & Hypokalemia Risk", "Digoxin therapeutic range: 0.5 - 2.0 ng/mL; Signs of toxicity: Nausea, vomiting, visual halos (green/yellow), bradycardia; HYPOKALEMIA increases risk of digoxin toxicity dramatically; assess apical pulse for 1 full minute (hold if HR < 60 bpm)", "Hold digoxin if apical pulse is 120 bpm and serum potassium is high at 6.0 mEq/L"),
            Triple("High-Alert: Heparin-Induced Thrombocytopenia (HIT)", "Immune-mediated drop in platelet count by > 50% within 5-10 days of starting heparin therapy; causes life-threatening venous/arterial THROMBOSIS; IMMEDIATELY STOP ALL HEPARIN PRODUCTS and start non-heparin anticoagulant (Argatroban)", "Increase IV Heparin drip rate when platelet count drops from 300,000 to 40,000"),
            Triple("High-Alert: Warfarin (Coumadin) INR & Vitamin K Antidote", "Target INR for AFib/DVT: 2.0 - 3.0; Target INR for mechanical valves: 2.5 - 3.5; Antidote is VITAMIN K (Phytonadione); instruct client to maintain CONSISTENT dietary intake of green leafy vegetables", "Give high-dose Vitamin K to increase INR from 1.0 to 8.0 in bleeding clients"),
            Triple("High-Alert: IV Potassium Chloride (KCl) Infusion Rules", "NEVER GIVE POTASSIUM IV PUSH (causes immediate cardiac arrest); maximum IV infusion rate is 10 mEq/hr peripherally or 20 mEq/hr centrally; always dilute and use infusion pump", "Administer 40 mEq Potassium Chloride as a rapid IV push over 5 seconds"),
            Triple("High-Alert: Insulin Administration Regular vs NPH Mixing", "Regular insulin is CLEAR (short-acting); NPH insulin is CLOUDY (intermediate-acting); when mixing: Inject air into cloudy (NPH), inject air into clear (Regular), withdraw clear (Regular), withdraw cloudy (NPH) [Clear to Cloudy]", "Withdraw cloudy NPH insulin first, then inject it into clear Regular insulin vial"),
            Triple("High-Alert: Vancomycin Trough Levels & Red Man Syndrome", "Check vancomycin trough level 30 minutes BEFORE 4th dose (target 15-20 mcg/mL for severe infections); RED MAN SYNDROME (flushing, rash, hypotension) caused by rapid IV infusion; treat by SLOWING infusion rate to over >= 60 minutes", "Red Man Syndrome is an anaphylactic emergency requiring immediate IV epinephrine and permanent drug ban"),
            Triple("High-Alert: Gentamicin / Tobramycin Ototoxicity & Nephrotoxicity", "Aminoglycosides cause irreversible OTOTOXICITY (tinnitus, hearing loss, vertigo) and NEPHROTOXICITY (elevated BUN/Creatinine, oliguria); monitor peak/trough levels and renal function closely", "Gentamicin causes hypokalemia and severe hypoglycemia without affecting hearing or kidneys"),
            Triple("Cardiovascular: Amiodarone Pulmonary Toxicity & Blue Skin", "Class III antiarrhythmic; adverse effects: PULMONARY TOXICITY (dry cough, dyspnea, interstitial pneumonitis), corneal microdeposits, thyroid dysfunction (hypo/hyper), blue-gray skin discoloration", "Amiodarone causes immediate severe hyperglycemia and bone marrow failure"),
            Triple("Cardiovascular: Nitroglycerin Sublingual Storage & Headache", "Sublingual NTG for acute angina; take 1 tablet every 5 mins up to 3 doses; call 911 if pain unimproved after 1st dose; store in original DARK GLASS BOTTLE away from heat/light; HEADACHE is expected side effect", "Swallow sublingual nitroglycerin tablets with 500 mL of milk after chewing"),
            Triple("Cardiovascular: ACE Inhibitors (-pril) Angioedema & Persistent Cough", "Adverse effects: Dry persistent cough (due to bradykinin breakdown blockage), HYPERKALEMIA, hypotension, and ANGIOEDEMA (swelling of lips/tongue/airway - life-threatening emergency); contra-indicated in pregnancy", "ACE inhibitors cause severe hypokalemia and profuse watery diarrhea"),
            Triple("Cardiovascular: Beta-Blockers (-lol) Asthma Contraindication", "Non-selective beta-blockers (Propranolol, Nadolol) block Beta-2 receptors causing BRONCHOSPASM; CONTRAINDICATED in asthma and severe COPD; check HR (< 60) and BP (< 100) before administering", "Give Propranolol to acute asthma patients during bronchospasm to dilate airways"),
            Triple("Respiratory: Albuterol vs Ipratropium / Salmeterol Order", "Albuterol is SHORT-ACTING Beta-2 agonist (SABA) rescue inhaler (onset minutes); Ipratropium is anticholinergic; Salmeterol is LONG-ACTING (LABA) maintenance; in acute asthma attack, ALWAYS GIVE SABA FIRST", "In acute asthma attack, administer long-acting Salmeterol first and wait 12 hours for response"),
            Triple("Respiratory: Inhaled Corticosteroids (Fluticasone) Oral Candidiasis", "Inhaled corticosteroids used for chronic maintenance; instruct client to RINSE MOUTH WITH WATER and spit after every use to prevent oral candidiasis (thrush) and hoarseness; use spacer device", "Swallow steroid inhaler powder with warm sugar water to coat the vocal cords"),
            Triple("Psychiatry: Lithium Carbonate Therapeutic Level & Sodium Intake", "Therapeutic range: 0.6 - 1.2 mEq/L; Toxicity (> 1.5): Coarse hand tremors, ataxia, confusion, polyuria, slurred speech; LOW SODIUM intake or dehydration INCREASES lithium toxicity risk; maintain normal sodium & fluids", "Instruct lithium clients to follow a strict zero-sodium diet and restrict fluids to 100 mL daily"),
            Triple("Psychiatry: Monoamine Oxidase Inhibitors (MAOIs) Tyramine Crisis", "Phenelzine, Tranylcypromine, Isocarboxazid; avoid TYRAMINE-CONTAINING FOODS (aged cheeses, cured meats, red wine, fava beans, sauerkraut, soy sauce) to prevent HYPERTENSIVE CRISIS", "Encourage clients taking Phenelzine to eat aged cheddar cheese and red wine daily"),
            Triple("Psychiatry: Clozapine Agranulocytosis & ANC Monitoring", "Atypical antipsychotic for treatment-resistant schizophrenia; risk of AGRANULOCYTOSIS (severe leukopenia); mandatory absolute neutrophil count (ANC) monitoring (ANC must be >= 1500/mm3 to initiate)", "Clozapine increases WBC count to 50,000 causing polycythemia vera"),
            Triple("Psychiatry: Haloperidol Neuroleptic Malignant Syndrome (NMS)", "NMS triad: Severe muscle rigidity ('lead pipe'), Hyperpyrexia (fever > 104 F), Autonomic instability (tachycardia, labile BP), altered mental status; treat: Discontinue drug, IV fluids, Dantrolene / Bromocriptine", "NMS presents with hypothermia, flaccid paralysis, and severe bradycardia"),
            Triple("Endocrine: Levothyroxine Morning Administration Rules", "Thyroid hormone replacement; take IN THE MORNING ON AN EMPTY STOMACH with full glass of water 30-60 minutes BEFORE breakfast; separate from calcium, iron, and antacids by at least 4 hours", "Take Levothyroxine at bedtime with calcium carbonate supplements and high-fat snack"),
            Triple("Endocrine: Corticosteroids Tapering & Infection Masking", "Prednisone/Dexamethasone; NEVER STOP ABRUPTLY (causes acute adrenal insufficiency/Addisonian crisis); causes hyperglycemia, immunosuppression (masks fever), osteoporosis, peptic ulcer, fluid retention", "Stop high-dose prednisone immediately after 6 months of daily therapy without tapering"),
            Triple("Gastrointestinal: Proton Pump Inhibitors (Omeprazole) Bone Fractures", "Long-term PPI therapy reduces stomach acid; increases risk of Clostridioides difficile infection, osteoporotic BONE FRACTURES (due to decreased calcium absorption), and hypomagnesemia", "Omeprazole increases bone density and prevents all gastrointestinal bacterial infections"),
            Triple("Neurological: Phenytoin (Dilantin) Therapeutic Level & Gingival Hyperplasia", "Therapeutic level: 10 - 20 mcg/mL; Side effects: GINGIVAL HYPERHYPERPLASIA (requires good oral hygiene and soft toothbrush), nystagmus, ataxia, hirsutism; flush line with 0.9% NS before/after IV push", "Phenytoin therapeutic range is 100 - 200 mcg/mL; flush IV line with 5% Dextrose in Water"),
            Triple("Maternity: Oxytocin (Pitocin) Uterine Tachysystole Protocol", "Oxytocin used for labor induction/augmentation; if UTERINE TACHYSYSTOLE occurs (> 5 contractions in 10 mins or contraction lasting > 90 seconds) with non-reassuring FHR: STOP OXYTOCIN FIRST, turn to left side, give O2", "Increase Oxytocin infusion rate when contractions occur every 30 seconds lasting 120 seconds"),
            Triple("Calculations: IV Drip Rate Drops Per Minute Formula", "Formula: gtt/min = (Total Volume in mL * Drop Factor in gtt/mL) / Time in Minutes. Example: 1000 mL over 8 hours (480 mins) with 15 gtt/mL tubing = (1000 * 15) / 480 = 31.25 -> 31 gtt/min", "Formula for IV gtt/min is (Time in Hours * Drop Factor) / Volume in Liters"),
            Triple("Calculations: Dosage Weight-Based Medication Milligrams", "Formula: Dose = Weight (kg) * Dose per kg. Always convert pounds to kg first by dividing weight in lbs by 2.2. Example: 44 lbs = 20 kg; 5 mg/kg = 100 mg total dose", "Convert kg to lbs by multiplying weight in kg by 100")
        )

        for (i in 0 until 140) {
            val topicIndex = i % supremeTopicsPart2.size
            val item = supremeTopicsPart2[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Double the prescribed medication dose whenever the client complains of mild fatigue",
                "Administer medication intravenously without verifying patient identification or allergy profile"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pharmacology & Drug Safety",
                "NCLEX-RN / DHA • Supreme Series",
                "Supreme Series Pharm Case #${i + 1}: The nurse prepares to administer medication for a client presenting with clinical conditions related to ${item.first}. Which pharmacology protocol must be strictly adhered to?",
                options,
                correctPos,
                "Rationale: Clinical pharmacological safety standard for ${item.first} specifies: ${item.second}.",
                "Option breakdown: Correct answer prevents lethal drug errors and toxicity. Incorrect option '${item.third}' violates drug safety principles.",
                "Pharmacology Supreme • ${item.first}"
            )
        }

        return list
    }
}
