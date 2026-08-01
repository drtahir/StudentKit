package com.example.ui.screens

/**
 * ELITE BANK PART 1: ADVANCED MEDICAL-SURGICAL, PATHOPHYSIOLOGY & CLINICAL TRIAGE (150 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ElitePart1 {

    fun getMedSurgEliteQuestions(startId: Int): List<NursingExamQuestion> {
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

        val medSurgEliteTopics = listOf(
            Triple("Cardiovascular: Hypertensive Crisis (Urgency vs Emergency)", "Hypertensive Emergency: BP > 180/120 with target organ damage (papilledema, stroke, AKI, acute MI); requires immediate IV Nicardipine/Labetalol to lower MAP by 20-25% in first hour", "Lower BP rapidly to 90/60 within 5 minutes causing cerebral ischemia"),
            Triple("Cardiovascular: Peripheral Venous Insufficiency vs PAD", "Venous insufficiency: stasis dermatitis, hyperpigmentation (hemosiderin staining), edema, painful ulcers at medial malleolus; elevate legs above heart level; compression stockings", "Keep legs dangling in dependent position continuously"),
            Triple("Cardiovascular: Post-CABG Graft Tamponade & Chest Tube Stripping", "Sudden cessation of mediastinal chest tube drainage followed by tachycardia, hypotension, and elevated CVP suggests cardiac tamponade; NEVER STRIP chest tubes (causes extreme negative pressure)", "Vigorously strip chest tubes using metal forceps"),
            Triple("Cardiovascular: Buerger's Disease (Thromboangiitis Obliterans)", "Inflammatory occlusive vascular disease of small/medium arteries; strongly linked to TOBACCO USE; severe digital ischemia, pain at rest, gangrene; absolute smoking cessation required", "Treat Buerger's disease by encouraging smoking 2 packs a day"),
            Triple("Respiratory: Chronic Obstructive Pulmonary Disease (COPD) Hypoxic Drive", "Chronic hypercapnia relies on hypoxic drive for breathing; maintain SpO2 88-92% with low-flow oxygen (Venturi mask); high FiO2 suppresses respiratory drive causing narcosis", "Administer 100% FiO2 non-rebreather mask to stable COPD client"),
            Triple("Respiratory: Chest Tube Disconnection & Water Seal Chamber Bubbling", "Continuous bubbling in water seal chamber indicates AIR LEAK; intermittent bubbling during expiration is normal; if disconnected from suction, submerge tubing 1-2 inches in sterile water", "Clamp chest tube immediately near client's chest wall forever"),
            Triple("Respiratory: Tracheostomy Care & Tube Dislodgement Protocol", "If tracheostomy tube is accidentally dislodged within first 72 hours, call for emergency help, position client in semi-Fowler's, extend neck, and ventilate via bag-valve-mask over stoma/mouth", "Forcefully reinsert dirty tube with high pressure without obturator"),
            Triple("Respiratory: Idiopathic Pulmonary Fibrosis & Honeycombing", "Progressive restrictive lung disease; exertional dyspnea, dry cough, fine end-inspiratory crackles, finger clubbing; chest CT shows 'honeycombing' pattern", "Honeycombing pattern on CT indicates acute bronchial asthma"),
            Triple("Neurological: Ischemic Stroke tPA Administration Window", "Thrombolytic therapy (Alteplase) within 3-4.5 hours of last known normal; strict BP control (< 185/110 mmHg); continuous neuro assessments every 15 mins during infusion", "Administer tPA when blood pressure is 220/130 mmHg"),
            Triple("Neurological: Myasthenia Gravis Ptosis & Pyridostigmine Timing", "Autoimmune antibodies against acetylcholine receptors; fluctuating muscle weakness, ptosis, diplopia; administer Pyridostigmine 30-45 mins BEFORE MEALS to enhance swallowing", "Give cholinesterase inhibitors 2 hours after meals when client sleeps"),
            Triple("Neurological: Amyotrophic Lateral Sclerosis (ALS) Bulbar Involvement", "Progressive neurodegenerative disease affecting upper and lower motor neurons; muscle atrophy, fasciculations, dysarthria, dysphagia; respiratory muscle failure is eventual cause of death", "ALS spares respiratory muscles completely"),
            Triple("Neurological: Multiple Sclerosis (MS) Lhermitte Sign & Uhthoff Phenomenon", "Demyelinating CNS autoimmune disorder; Uhthoff phenomenon (worsening of neuro symptoms in heat/hot baths); Lhermitte sign (electric shock down spine on neck flexion)", "Recommend daily hot sauna and steam baths"),
            Triple("Gastrointestinal: Cirrhosis Spontaneous Bacterial Peritonitis (SBP)", "Infection of ascitic fluid without an intra-abdominal surgical source; fever, abdominal pain, altered mental status; paracentesis shows neutrophil count >= 250/mm3", "SBP is treated with immediate surgical bowel resection"),
            Triple("Gastrointestinal: Small Bowel Obstruction (SBO) vs Large Bowel", "SBO: rapid onset, profuse bilious vomiting, colicky upper abdominal pain, hyperactive high-pitched bowel sounds initially; NPO, NG tube decompression, IV fluids", "SBO presents with zero vomiting and chronic diarrhea for 5 years"),
            Triple("Gastrointestinal: Dumping Syndrome Post-Gastrectomy Nutrition", "Rapid gastric emptying into jejunum causing diaphoresis, tachycardia, abdominal cramping; lie down for 30 mins after meals, HIGH PROTEIN/FAT, LOW CARB, DRY MEALS (no liquids with food)", "Drink 3 glasses of iced sweet tea with meals while running"),
            Triple("Gastrointestinal: Gastroesophageal Reflux Disease (GERD) Barrett's Esophagus", "Chronic acid reflux causes intestinal metaplasia of esophageal mucosa (Barrett's esophagus), a premalignant lesion for esophageal adenocarcinoma; PPI therapy and surveillance endoscopy", "Barrett's esophagus is a benign fungal skin infection"),
            Triple("Endocrine: Cushing's Syndrome vs Addison's Disease Laboratory Findings", "Cushing's: hypercortisolism, hyperglycemia, hypernatremia, hypokalemia, moon face, central obesity; Addison's: hypocortisolism, hypoglycemia, hyponatremia, hyperkalemia", "Cushing's disease presents with severe hypoglycemia and hyponatremia"),
            Triple("Endocrine: Hypoparathyroidism Chvostek & Trousseau Signs", "Hypocalcemia (< 8.5 mg/dL) from accidental parathyroid removal during thyroidectomy; Chvostek sign (facial twitching) and Trousseau sign (carpal spasm with BP cuff); keep Calcium Gluconate at bedside", "Hypercalcemia causes immediate Trousseau carpal spasm"),
            Triple("Endocrine: Pheochromocytoma 5 Ps & Paroxysmal Hypertension", "Adrenal medulla tumor secreting catecholamines; 5 Ps: Pressure (severe HTN), Pain (headache), Perspiration, Palpitations, Pallor; DO NOT PALPATE ABDOMEN (precipitates hypertensive crisis)", "Palpate upper abdomen vigorously to assess mass size"),
            Triple("Endocrine: Acromegaly Growth Hormone & Cardiac Hypertrophy", "Hypersecretion of Growth Hormone post-epiphyseal closure; enlarged hands, feet, jaw, coarse facial features, hypertension, left ventricular hypertrophy, sleep apnea", "Acromegaly causes severe dwarfism and small hands"),
            Triple("Renal: Nephrotic Syndrome Triad & Albuminuria", "Glomerular permeability defect; Triad: Massive proteinuria (> 3.5 g/day), Hypoalbuminemia, Generalized Anasarca/Edema; elevated serum lipids; hypercoagulable state", "Nephrotic syndrome is characterized by severe hematuria and normal protein"),
            Triple("Renal: Glomerulonephritis Hematuria & Periorbital Edema", "Post-streptococcal immune complex deposition; cola-colored/smoky urine, periorbital edema, hypertension, oliguria; restrict fluid and sodium", "Post-streptococcal glomerulonephritis produces clear yellow polyuria"),
            Triple("Renal: End-Stage Renal Disease (ESRD) Hyperkalemia Emergency", "Serum Potassium > 6.5 mEq/L with ECG changes (peaked T waves, widened QRS); immediate treatment: IV Calcium Gluconate (cardioprotection), IV Regular Insulin + D50, Kayexalate / Lokelma", "Treat hyperkalemia with oral potassium chloride supplements"),
            Triple("Musculoskeletal: Osteomyelitis Bone Infection & Antibiotic Duration", "Severe bone infection (Staph aureus); localized bone pain, fever, erythema, elevated ESR/CRP; requires long-term (4-6 weeks) IV antibiotic therapy via PICC line", "Treat acute osteomyelitis with 3 days of oral vitamins"),
            Triple("Musculoskeletal: Rheumatoid Arthritis vs Osteoarthritis Features", "RA: systemic autoimmune, bilateral symmetric joint involvement, morning stiffness > 1 hour, subcutaneous nodules, elevated ESR/RF; OA: wear-and-tear, unilateral, morning stiffness < 30 mins", "Osteoarthritis is a systemic autoimmune disease affecting lungs"),
            Triple("Musculoskeletal: Total Hip Arthroplasty (THA) Posterior Approach Rules", "Prevent posterior dislocation: maintain abduction (use pillow), DO NOT FLEX HIP > 90 DEGREES, do NOT cross legs or internally rotate hip; use elevated toilet seat", "Flex hip to 120 degrees and cross legs tight post-surgery"),
            Triple("Hematology: Multiple Myeloma CRAB Criteria & Bence Jones Protein", "Plasma cell malignancy; CRAB: HyperCalcemia, Renal insufficiency, Anemia, Bone lytic lesions (pathologic fractures); Bence Jones proteins in urine; high risk of vertebral collapse", "Multiple myeloma is a benign muscle skin tag"),
            Triple("Hematology: Polycythemia Vera Hyperviscosity & Therapeutic Phlebotomy", "Overproduction of RBCs causing hyperviscosity; severe pruritus after warm bath, plethoric face, gout; treated with periodic THERAPEUTIC PHLEBOTOMY to maintain Hct < 45%", "Treat polycythemia vera with daily iron infusions"),
            Triple("Dermatology: Pressure Injury Staging System (Stage 1 to Deep Tissue)", "Stage 1: non-blanchable erythema; Stage 2: partial-thickness skin loss with exposed dermis; Stage 3: full-thickness skin loss; Stage 4: full-thickness skin/tissue loss with exposed bone/muscle", "Stage 1 pressure ulcer shows exposed femoral bone"),
            Triple("Immunology: Toxic Epidermal Necrolysis (TEN) Nikolsky Sign", "Severe life-threatening dermatologic reaction; > 30% body surface area detachment; positive Nikolsky sign (skin sloughs off with gentle pressure); treat like severe burn", "Nikolsky sign is a normal finding in healthy newborns")
        )

        for (i in 0 until 150) {
            val topicIndex = i % medSurgEliteTopics.size
            val item = medSurgEliteTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Discontinue vital monitoring and discharge client",
                "Delegate specialized nursing assessment to unlicensed assistive personnel"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical & Pathophysiology",
                "NCLEX-RN / DHA • Elite Series",
                "Elite Series Med-Surg Case #${i + 1}: A client admitted to the clinical unit exhibits clinical features associated with ${item.first}. Which nursing assessment or intervention priority is evidence-based?",
                options,
                correctPos,
                "Rationale: Medical-surgical elite care standards for ${item.first} mandate: ${item.second}.",
                "Option breakdown: Correct choice prevents tissue destruction, reduces complication rates, and maintains clinical stability. Action '${item.third}' is unsafe.",
                "Med-Surg Elite • ${item.first}"
            )
        }

        return list
    }
}
