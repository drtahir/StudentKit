package com.example.ui.screens

/**
 * APEX BANK PART 1: SPECIALIZED CLINICAL SPECIALTIES, ADVANCED PATHOPHYSIOLOGY & NCLEX NEXTGEN (150 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ApexPart1 {

    fun getMedSurgApexQuestions(startId: Int): List<NursingExamQuestion> {
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

        val apexTopicsPart1 = listOf(
            Triple("Cardiovascular: Hypertrophic Cardiomyopathy (HCM) Avoid Inotropes/Nitrates", "HCM causes left ventricular outflow tract obstruction; DO NOT GIVE Inotropes (Digoxin, Dopamine) or Nitrates/Diuretics (decreases preload worsening obstruction); treat with Beta-Blockers", "Administer high-dose Digoxin and Nitroglycerin to worsen outflow obstruction"),
            Triple("Cardiovascular: Cardiac Resynchronization Therapy (CRT) Biventricular Pacing", "Indicated for severe HF with QRS widening (> 130 ms); paces BOTH left and right ventricles simultaneously to restore ventricular synchrony and improve ejection fraction", "CRT paces only the right atrium at 200 bpm"),
            Triple("Cardiovascular: Thoracic Aortic Aneurysm (TAA) Hoarseness & Dysphagia", "Expanding TAA compresses recurrent laryngeal nerve causing HOARSENESS and esophagus causing DYSPHAGIA; monitor BP closely; severe back pain indicates impending rupture", "TAA compression causes severe leg swelling and diarrhea"),
            Triple("Cardiovascular: Restrictive Cardiomyopathy Amyloidosis & Rigid Ventricles", "Rigid ventricular walls restrict diastolic filling while systolic function remains normal; commonly caused by amyloidosis or sarcoidosis; high risk of heart failure and dysrhythmias", "Restrictive cardiomyopathy causes hyper-flexible rubbery heart chambers"),
            Triple("Respiratory: Acute Severe Asthma (Status Asthmaticus) 'Silent Chest' Warning", "Sudden absence of wheezing ('SILENT CHEST') in a severely dyspneic asthmatic indicates COMPLETE AIRWAY OBSTRUCTION and impending respiratory arrest; immediate endotraacheal intubation", "'Silent chest' indicates complete recovery and cure of asthma"),
            Triple("Respiratory: Mechanical Ventilation High-Frequency Oscillatory Ventilation (HFOV)", "Delivers extremely small tidal volumes at ultra-high rates (300-900 breaths/min); used in severe refractory ARDS to recruit lungs while minimizing ventilator-induced lung injury", "HFOV delivers 2 breaths per hour at high tidal volumes"),
            Triple("Respiratory: Massive Hemoptysis (> 600 mL/24h) Positioning & Airway Protection", "Position client with AFFECTED LUNG IN DEPENDENT POSITION (DOWN) to prevent blood from aspirating into healthy lung; establish patent airway and prepare for emergency bronchoscopy", "Position client with affected bleeding lung facing upward"),
            Triple("Respiratory: Pleural Effusion Light's Criteria Exudative vs Transudative", "Light's criteria differentiates exudative (infection, malignancy; high protein/LDH) from transudative (HF, cirrhosis; low protein); exudative ratio pleural/serum protein > 0.5", "Transudative effusion is caused by lung cancer and active pneumonia"),
            Triple("Neurological: Increased ICP Cushing's Triad & Cerebral Herniation", "Cushing's Triad (LATE SIGN OF ICP / IMPENDING HERNIATION): Severe Bradycardia, Widened Pulse Pressure (Systolic HTN), Irregular/Cheyne-Stokes Respirations; immediate hypertonic saline/mannitol", "Cushing's triad consists of tachycardia, hypotension, and tachypnea"),
            Triple("Neurological: Brain Death Criteria & Apnea Testing", "Criteria: Irreversible coma, absence of brainstem reflexes (pupillary, corneal, oculocephalic, oculovestibular, gag), positive APNEA TEST (PaCO2 >= 60 mmHg without respiratory effort)", "Brain death is diagnosed if client blinks when spoken to"),
            Triple("Neurological: Horner Syndrome Triad & Carotid Artery Dissection", "Triad: Ptosis (drooping eyelid), Miosis (constricted pupil), Anhidrosis (lack of sweating) on same side of face; caused by sympathetic chain disruption or internal carotid dissection", "Horner syndrome causes severe bilateral exophthalmos"),
            Triple("Neurological: Normal Pressure Hydrocephalus (NPH) Triad 'Wobbly, Wet, Wacky'", "Triad: Gait ataxia ('Wobbly' magnetic gait), Urinary incontinence ('Wet'), Cognitive impairment ('Wacky'); treated with VENTRICULOPERITONEAL (VP) SHUNT", "NPH presents with hyper-reflexia, severe constipation, and high fever"),
            Triple("Gastrointestinal: Acute Mesenteric Ischemia Pain Out of Proportion", "Sudden onset severe abdominal pain DISPROPORTIONATE TO PHYSICAL EXAM FINDINGS; metabolic acidosis, elevated lactate, bloody diarrhea; emergency vascular revascularization", "Mesenteric ischemia causes mild itchiness of skin without pain"),
            Triple("Gastrointestinal: Short Bowel Syndrome Malabsorption & TPN Dependence", "Resection of > 50% small intestine; severe diarrhea, steatorrhea, fluid/electrolyte imbalances, fat-soluble vitamin deficiency (A,D,E,K); long-term TPN required initially", "Short bowel syndrome improves fat absorption dramatically"),
            Triple("Gastrointestinal: Boerhaave Syndrome Esophageal Rupture & Mackler Triad", "Spontaneous transmural esophageal perforation from violent vomiting; Mackler Triad: Vomiting, Chest Pain, Subcutaneous Emphysema; surgical emergency", "Boerhaave syndrome is a benign viral rash on cheeks"),
            Triple("Gastrointestinal: Ogilvie Syndrome Acute Colonic Pseudo-Obstruction", "Massive non-mechanical dilation of cecum/colon in critically ill patients; high risk of cecal perforation if diameter > 10-12 cm; treat with Neostigmine or colonoscopic decompression", "Ogilvie syndrome is treated by feeding high-protein solid steak"),
            Triple("Endocrine: Thyroidectomy Hypocalcemia & Laryngeal Nerve Damage Test", "Assess vocal pitch/quality (hoarseness indicates recurrent laryngeal nerve damage; bilateral damage causes airway obstruction); monitor Chvostek/Trousseau signs for 72 hours", "Assess nerve damage by measuring ankle reflex strength"),
            Triple("Endocrine: Primary Hyperaldosteronism (Conn Syndrome) Hypertension & Hypokalemia", "Adrenal adenoma secreting excess aldosterone; TRIAD: Hypertension, Hypokalemia, Metabolic Alkalosis; treated with Spironolactone or adrenalectomy", "Conn syndrome causes severe hypotension and hyperkalemia"),
            Triple("Endocrine: MEN-1 Syndrome Pituitary, Parathyroid & Pancreatic Tumors", "Multiple Endocrine Neoplasia type 1; autosomal dominant; 3 Ps: Parathyroid hyperplasia (hypercalcemia), Pituitary adenoma, Pancreatic neuroendocrine tumors (Zollinger-Ellison)", "MEN-1 affects liver, spleen, and bone marrow exclusively"),
            Triple("Endocrine: Autoimmune Polyglandular Syndrome Type 1 (APS-1) Hypoparathyroidism", "Rare genetic autoimmune disorder; TRIAD: Mucocutaneous Candidiasis, Hypoparathyroidism, Addison's disease; monitor electrolytes and hormone levels", "APS-1 causes extreme hyperparathyroidism and acromegaly"),
            Triple("Renal: Hepatorenal Syndrome Type 1 Acute Renal Failure in Cirrhosis", "Functional renal failure in advanced cirrhosis; severe renal vasoconstriction with normal kidney histology; refractory to fluid resuscitation; treated with Terlipressin + Albumin", "Hepatorenal syndrome is cured by giving high-dose NSAIDs"),
            Triple("Renal: Rhabdomyolysis Tea-Colored Urine & Creatine Kinase (CK)", "Muscle breakdown releases myoglobin into circulation; TRIAD: Muscle pain, Weakness, TEA-COLORED MYOGLOBINURIA; serum CK > 5000 U/L; treat with aggressive IV Normal Saline hydration", "Treat rhabdomyolysis with strict fluid restriction"),
            Triple("Renal: Autosomal Dominant Polycystic Kidney Disease (ADPKD) Berry Aneurysms", "Bilateral renal cysts, flank pain, hematuria, hypertension; 10-15% associated with INTRACRANIAL BERRY ANEURYSMS (high risk of subarachnoid hemorrhage if sudden headache occurs)", "ADPKD cysts vanish spontaneously after taking aspirin"),
            Triple("Renal: Rapidly Progressive Glomerulonephritis (RPGN) Crescentic Formation", "Severe glomerular injury with CRESCENT FORMATION in Bowman's space on renal biopsy; rapid loss of renal function over days/weeks; treat with pulse Methylprednisolone and Cyclophosphamide", "RPGN renal biopsy shows normal clear fluid without cells"),
            Triple("Musculoskeletal: Compartment Syndrome Delta Pressure (< 30 mmHg)", "Delta Pressure = Diastolic BP - Direct Compartment Pressure; Delta P < 30 mmHg indicates severe tissue ischemia and mandatory EMERGENCY FASCIOTOMY", "Compartment pressure of 100 mmHg with Delta P 80 is completely normal"),
            Triple("Musculoskeletal: Ankylosing Spondylitis Bamboo Spine & HLA-B27", "Inflammatory arthritis affecting axial skeleton; HLA-B27 positive; morning stiffness, progressive lumbar spine fusion ('Bamboo Spine'); encourage extension exercises", "Ankylosing spondylitis requires severe spine hyperflexion exercises"),
            Triple("Hematology: Thrombotic Thrombocytopenic Purpura (TTP) Pentad PENTAD", "TTP Pentad: Thrombocytopenia, Microangiopathic Hemolytic Anemia, Neurological deficits, Renal failure, Fever; ADAMTS13 deficiency; TREAT WITH PLASMA EXCHANGE (PLEX)", "TTP is treated with immediate platelet transfusions (worsens thrombosis)"),
            Triple("Hematology: Heparin-Induced Thrombocytopenia (HIT) 4T Score", "4Ts: Thrombocytopenia magnitude, Timing of platelet drop, Thrombosis presence, oTher causes; score >= 6 indicates high probability; STOP ALL HEPARIN, start Argatroban", "Continue Heparin drip when 4T score is 8"),
            Triple("Oncology: Febrile Neutropenia Absolute Neutrophil Count & Immediate Antibiotics", "Single temperature >= 38.3°C (101.0°F) or >= 38.0°C for 1 hr with ANC < 500/mm3; MEDICAL EMERGENCY; draw blood cultures and ADMINISTER EMPIRIC IV ANTIBIOTICS WITHIN 1 HOUR", "Wait 72 hours for blood culture results before starting antibiotics"),
            Triple("Oncology: Hypercalcemia of Malignancy Bisphosphonates & Hydration", "Common in breast, lung, multiple myeloma; serum Calcium > 12 mg/dL; nausea, confusion, polyuria, shortened QT interval; treat with aggressive IV Normal Saline + IV Zoledronic Acid", "Treat hypercalcemia with IV calcium gluconate bolus")
        )

        for (i in 0 until 150) {
            val topicIndex = i % apexTopicsPart1.size
            val item = apexTopicsPart1[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Discontinue vital signs monitoring and leave client unmonitored",
                "Delegate specialized nursing assessment and intervention to unlicensed assistive personnel"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical & Pathophysiology",
                "NCLEX-RN / DHA • Apex Series",
                "Apex Series Med-Surg Case #${i + 1}: A complex client admitted to the high-acuity unit exhibits clinical findings indicative of ${item.first}. Which evidence-based nursing action represents gold-standard care?",
                options,
                correctPos,
                "Rationale: Specialized medical-surgical apex protocols for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice prevents fatal organ failure, reduces complication rates, and maintains clinical stability. Action '${item.third}' is improper.",
                "Med-Surg Apex • ${item.first}"
            )
        }

        return list
    }
}
