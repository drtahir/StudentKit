package com.drtahir.studentkit.ui.screens

/**
 * EXPERT BANK PART 1: MEDICAL-SURGICAL & SPECIALIZED PATHOPHYSIOLOGY (150 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ExpertPart1 {

    fun getMedSurgExpertQuestions(startId: Int): List<NursingExamQuestion> {
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

        val medSurgExpertTopics = listOf(
            Triple("Cardiovascular: Cardiac Tamponade Beck's Triad & Pulsus Paradoxus", "Beck's Triad: Hypotension, JVD, and muffled heart sounds; Pulsus paradoxus: systolic BP drop > 10 mmHg during inspiration; immediate treatment is pericardiocentesis", "Beck's triad consists of hypertension, bradycardia, and loud murmur"),
            Triple("Cardiovascular: Infective Endocarditis Osler Nodes vs Janeway Lesions", "Osler nodes: painful tender erythematous nodules on pads of fingers/toes; Janeway lesions: non-tender macules on palms/soles; high risk of systemic embolization", "Janeway lesions are extremely painful ulcers on the tongue"),
            Triple("Cardiovascular: Aortic Dissection Ripping Pain & Pulse Disparity", "Sudden severe 'tearing' or 'ripping' chest pain radiating to back; significant blood pressure disparity (> 20 mmHg) between arms; avoid beta-blocker delays", "Presents with mild gradual dull ache relieved by deep breathing"),
            Triple("Cardiovascular: Peripheral Artery Disease (PAD) Ankle-Brachial Index (ABI)", "ABI < 0.9 indicates PAD; dependent rubor, pallor on elevation, intermittent claudication; NEVER elevate legs above heart level or apply direct heat", "Elevate legs on 3 pillows and apply hot water bottle"),
            Triple("Cardiovascular: Deep Vein Thrombosis (DVT) Heparin to Warfarin Bridging", "Continue IV Heparin until Oral Warfarin reaches therapeutic INR (2.0-3.0) for at least 24-48 hours; check PTT for Heparin and PT/INR for Warfarin", "Stop Heparin immediately upon starting first Warfarin pill"),
            Triple("Respiratory: Acute Respiratory Distress Syndrome (ARDS) P/F Ratio & PEEP", "Refractory hypoxemia despite high FiO2; P/F ratio < 300 mmHg; treated with high PEEP (opens collapsed alveoli) and prone positioning", "Treated with immediate cessation of all oxygen therapy"),
            Triple("Respiratory: Tension Pneumothorax Tracheal Shift & Needle Decompression", "Tracheal deviation to UNAFFECTED side, absent breath sounds on affected side, hypotension, JVD; immediate needle decompression at 2nd intercostal space", "Tracheal deviation toward affected side with severe hypertension"),
            Triple("Respiratory: Flail Chest Paradoxical Respiration", "Fracture of >= 2 adjacent ribs in >= 2 places; paradoxical chest wall movement (chest moves IN on inspiration and OUT on expiration); stabilize with mechanical ventilation", "Chest wall expands dramatically during expiration"),
            Triple("Respiratory: Pulmonary Embolism (PE) S1Q3T3 Pattern & Anticoagulation", "Sudden dyspnea, pleuritic chest pain, tachypnea, tachycardia, hypoxia; D-dimer elevated; immediate anticoagulation with Heparin/LMWH", "Reassure client and recommend 5 miles of jogging"),
            Triple("Respiratory: Mechanical Ventilation VAP Prevention Bundle", "Elevate HOB 30-45 degrees, daily sedation vacation and extubation readiness assessment, peptic ulcer prophylaxis, DVT prophylaxis, chlorhexidine mouth wash q 2-4 hrs", "Keep client flat supine with oral suctioning once every 3 days"),
            Triple("Neurological: Increased ICP Mannitol & Hypertonic Saline", "Mannitol (osmotic diuretic) draws fluid from brain parenchyma into vascular space; monitor serum osmolality (> 320 mOsm/kg stop drug) and electrolyte levels", "Mannitol increases brain edema by driving water into cells"),
            Triple("Neurological: Autonomic Dysreflexia Spinal Cord T6 Triage", "Triggered by full bladder or bowel impaction in T6 or higher spinal injury; severe HTN, throbbing headache, bradycardia, facial flushing; ELEVATE HOB FIRST", "Place client flat in Trendelenburg position"),
            Triple("Neurological: Myasthenic Crisis vs Cholinergic Crisis Tensilon Test", "Myasthenic crisis (under-medication): improved strength after Edrophonium (Tensilon); Cholinergic crisis (over-medication): increased weakness/fasciculations (give Atropine)", "Tensilon test cures Myasthenia Gravis permanently"),
            Triple("Neurological: Guillain-Barré Syndrome Ascending Paralysis & FVC", "Post-viral autoimmune demyelination; ascending muscle weakness; monitor Forced Vital Capacity (FVC) and Negative Inspiratory Force (NIF) for respiratory compromise", "Paralysis begins in face and descends down to feet"),
            Triple("Neurological: Subarachnoid Hemorrhage (SAH) 'Worst Headache of Life'", "Sudden 'thunderclap' headache, nuchal rigidity, photophobia; Nimotop (Nimodipine) given to prevent cerebral vasospasm; maintain BP parameters", "Encourage neck hyperflexion exercises"),
            Triple("Gastrointestinal: Acute Pancreatitis Cullen & Grey Turner Signs", "Severe epigastric pain radiating to back; Cullen's sign (periumbilical ecchymosis) and Grey Turner's sign (flank ecchymosis) indicate hemorrhagic pancreatitis; NPO status", "Feed high-fat fried food to stimulate gallbladder"),
            Triple("Gastrointestinal: Hepatic Encephalopathy Lactulose & Asterixis", "Elevated serum ammonia levels cause flapping tremor (asterixis) and altered mental status; Lactulose traps ammonia in gut causing 2-3 soft bowel movements/day", "Goal of Lactulose is 15 explosive watery stools per hour"),
            Triple("Gastrointestinal: Esophageal Varices Sengstaken-Blakemore Tube", "Balloon tamponade for acute bleeding varices; maintain scissors at bedside to cut tube ports immediately if respiratory distress occurs from balloon migration", "Discard scissors far from bedside"),
            Triple("Gastrointestinal: Ulcerative Colitis Bloody Diarrhea & Toxic Megacolon", "Mucosal inflammation of colon; 10-20 bloody liquid stools/day; severe abdominal distension and fever suggest Toxic Megacolon (emergency)", "Ulcerative colitis affects only the stomach mucosa"),
            Triple("Gastrointestinal: Crohn's Disease Cobblestones & Fistulas", "Transmural skip lesions from mouth to anus; non-bloody diarrhea, RLQ pain, weight loss, high risk of fistulas, strictures, and malabsorption", "Crohn's disease causes superficial non-transmural colon ulcerations"),
            Triple("Endocrine: Diabetic Ketoacidosis (DKA) Kussmaul Breathing & Anion Gap", "Type 1 DM; hyperglycemia > 250 mg/dL, metabolic acidosis with elevated anion gap, Kussmaul respirations, fruity breath; IV Regular Insulin + IV fluids", "Treat DKA with subcutaneous NPH insulin and fluid restriction"),
            Triple("Endocrine: Hyperosmolar Hyperglycemic State (HHS)", "Type 2 DM; extreme hyperglycemia (> 600 mg/dL), severe dehydration, high serum osmolality (> 320 mOsm/kg), NO KETONES; aggressive fluid resuscitation", "HHS is characterized by severe ketoacidosis and low glucose"),
            Triple("Endocrine: Addisonian Crisis Corticosteroid Replacement", "Acute adrenal insufficiency; severe hypotension, hyponatremia, hyperkalemia, hypoglycemia; treat with high-dose IV Hydrocortisone and IV Normal Saline", "Administer oral insulin and restrict IV fluids"),
            Triple("Endocrine: Thyroid Storm (Thyrotoxic Crisis) Beta-Blockers & PTU", "Life-threatening hyperthyroidism; severe fever (> 104°F), tachycardia, delirium; treat with Propranolol, Propylthiouracil (PTU), Iodine, and cooling blankets", "Administer aspirin for high fever (aspirin displaces T4 from binding)"),
            Triple("Endocrine: SIADH vs Diabetes Insipidus Urine Specific Gravity", "SIADH: high ADH, fluid retention, hyponatremia, high urine SpG (> 1.030); DI: low ADH, profuse polyuria, hypernatremia, low urine SpG (< 1.005)", "DI is characterized by fluid overload and urine SpG > 1.035"),
            Triple("Renal: Acute Kidney Injury (AKI) Prerenal vs Intrarenal vs Postrenal", "Prerenal: renal hypoperfusion (dehydration, heart failure); Intrarenal: direct tubular damage (aminoglycosides, NSAIDs, contrast); Postrenal: urinary obstruction (BPH, stones)", "Prerenal AKI is caused by kidney stone obstructing ureter"),
            Triple("Renal: Hemodialysis Arteriovenous (AV) Fistula Assessment", "Palpate THRILL (vibration) and auscultate BRUIT (swishing sound) every shift; NEVER take BP, draw blood, or start IV on fistula arm", "Use AV fistula arm for hourly blood pressure cuffs"),
            Triple("Renal: Peritoneal Dialysis Cloudy Outflow & Peritonitis", "Cloudy or turbid dialysate drainage is earliest sign of PERITONITIS; send outflow sample for cell count and Gram stain; start intraperitoneal antibiotics", "Cloudy outflow is completely normal and healthy"),
            Triple("Musculoskeletal: Compartment Syndrome 6 Ps & Fasciotomy", "Pain out of proportion, Paresthesia, Pallor, Paralysis, Pulselessness, Poikilothermia; DO NOT ELEVATE LEG ABOVE HEART; prepare for immediate emergency fasciotomy", "Elevate leg high on 4 pillows and apply tight compression wrap"),
            Triple("Musculoskeletal: Fat Embolism Syndrome Triad Post-Long Bone FX", "24-72 hours post femur/pelvic fracture; Triad: Respiratory distress (hypoxemia), Neurological decline (confusion), PETECHIAE on chest/neck/axilla", "Petechiae appear on the bottom of the feet 2 weeks post injury"),
            Triple("Musculoskeletal: Traction Management Skeletal vs Skin", "Skeletal traction weights must HANG FREELY at all times; do NOT remove weights; maintain body alignment; pin site care with chlorhexidine", "Rest traction weights directly on floor to relieve strain"),
            Triple("Hematology: Sickle Cell Vaso-Occlusive Crisis HOP Protocol", "Hydration (IV fluids), Oxygenation, Pain management (IV Opioids); obstruction of microvascular circulation causing severe tissue ischemia", "Treat sickle cell crisis with ice packs and fluid restriction"),
            Triple("Hematology: Heparin-Induced Thrombocytopenia (HIT) Type II", "50% drop in platelet count within 5-10 days of Heparin start; Paradoxical THROMBOSIS risk; STOP ALL HEPARIN immediately and switch to Argatroban", "Double the Heparin drip rate to raise platelets"),
            Triple("Hematology: Immune Thrombocytopenic Purpura (ITP) Bleeding Risk", "Autoimmune destruction of platelets; severe risk of spontaneous intracranial hemorrhage if platelets < 20,000/mm3; avoid invasive procedures/rectal temps", "Encourage contact sports and aspirin therapy"),
            Triple("Oncology: Superior Vena Cava (SVC) Syndrome Facial Edema", "Oncologic emergency caused by mediastinal tumor compressing SVC; facial/periorbital edema, distended neck veins, dyspnea; elevate HOB", "Place client in Trendelenburg position"),
            Triple("Oncology: Tumor Lysis Syndrome (TLS) Hyperkalemia & Allopurinol", "Rapid destruction of tumor cells releases potassium, uric acid, and phosphate; causes AKI and fatal arrhythmias; treat with aggressive hydration and Rasburicase/Allopurinol", "TLS causes severe hypokalemia and low uric acid"),
            Triple("Dermatology: Rule of Nines Burn Percentage Calculation", "Head 9%, Each Arm 9%, Anterior Trunk 18%, Posterior Trunk 18%, Each Leg 18%, Perineum 1%; calculate Parkland Formula (4 mL x kg x % TBSA)", "Both legs together account for 9% total TBSA"),
            Triple("Dermatology: Parkland Formula Fluid Resuscitation", "4 mL x kg x % TBSA burned; give 50% of total calculated volume in FIRST 8 HOURS post-burn, and remaining 50% over NEXT 16 HOURS", "Give 100% of fluid volume in the final hour of 24 hours"),
            Triple("Immunology: Systemic Lupus Erythematosus (SLE) Butterfly Rash", "Autoimmune connective tissue disease; malar butterfly rash across cheeks/bridge of nose, photosensitivity, lupus nephritis; protect from sunlight", "Encourage 4 hours of direct midday sunbathing"),
            Triple("Immunology: Anaphylaxis Second Wave Biphasic Reaction", "Biphasic anaphylaxis can recur 1-72 hours after initial resolution; observe client in emergency department for at least 4-8 hours post-epinephrine", "Discontinue observation immediately 5 minutes post epinephrine")
        )

        for (i in 0 until 150) {
            val topicIndex = i % medSurgExpertTopics.size
            val item = medSurgExpertTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Discontinue monitoring and discharge client",
                "Delegate advanced assessment to non-clinical staff"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical & Pathophysiology",
                "NCLEX-RN / DHA • Expert Series",
                "Expert Series Med-Surg Case #${i + 1}: A complex hospitalized client presents with clinical findings indicative of ${item.first}. Which evidence-based management strategy is required?",
                options,
                correctPos,
                "Rationale: Medical-surgical expert guidelines for ${item.first} mandate: ${item.second}.",
                "Option breakdown: Correct choice ensures immediate physiological stabilization, prevents organ dysfunction, and upholds critical safety standards. Action '${item.third}' is unsafe.",
                "Med-Surg Expert • ${item.first}"
            )
        }

        return list
    }
}
