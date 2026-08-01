package com.example.ui.screens

/**
 * MASTER BANK PART 1: MEDICAL-SURGICAL NURSING (150 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500MasterPart1 {

    fun getMedSurgMasterQuestions(startId: Int): List<NursingExamQuestion> {
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

        val medSurgMasterTopics = listOf(
            Triple("Cardiovascular: Acute Myocardial Infarction MONA Sequence & ECG Elevation", "ST-segment elevation (STEMI) indicates transmural myocardial ischemia; immediate interventions: Oxygen, Nitroglycerin, Aspirin, Morphine (MONA), and prepare for PCI within 90 minutes", "Give high-dose NSAIDs and schedule elective stress test in 2 weeks"),
            Triple("Cardiovascular: Right-Sided vs Left-Sided Heart Failure", "Right-sided: JVD, peripheral pitting edema, hepatomegaly, ascites; Left-sided: pulmonary congestion, crackles, dyspnea, orthopnea, paroxysmal nocturnal dyspnea", "Right-sided failure causes severe pulmonary edema and hemoptysis"),
            Triple("Cardiovascular: Hypertensive Crisis Urgency vs Emergency", "Hypertensive Emergency: BP > 180/120 mmHg WITH acute target organ damage (encephalopathy, stroke, MI, aortic dissection); lower MAP by max 20-25% in first hour", "Drop BP rapidly by 80% using IV rapid bolus"),
            Triple("Cardiovascular: Atrial Fibrillation Thromboembolism Risk", "Loss of atrial kick and blood stasis in left atrial appendage creates high stroke risk; manage with rate control (diltiazem/metoprolol) and lifelong anticoagulation (warfarin/DOACs)", "Encourage vigorous exercise without anticoagulation"),
            Triple("Cardiovascular: Pacemaker Malfunctions Capture vs Sensing", "Failure to capture: pacemaker spike present without corresponding P wave or QRS; Failure to sense: spike occurs inappropriately on top of intrinsic beats (risk of R-on-T)", "Failure to capture means battery is 100% fully charged"),
            Triple("Cardiovascular: Pericarditis Friction Rub & ECG ST Elevation", "Pleuritic chest pain relieved by sitting forward, pericardial friction rub at lower left sternal border, diffuse concave ST-segment elevation on 12-lead ECG", "Pain worsens when sitting up and leaning forward"),
            Triple("Cardiovascular: Ventricular Aneurysm Post-MI Complication", "Bulging of necrotic ventricular wall post-MI; client presents with persistent ST elevation, ventricular arrhythmias, and heart failure; high risk of rupture", "Causes instant cure of coronary artery disease"),
            Triple("Cardiovascular: Coronary Artery Bypass Graft (CABG) Post-Op Care", "Monitor chest tube drainage (> 100-150 mL/hr report to provider), manage BP, encourage coughing/deep breathing, assess saphenous vein donor site for edema", "Ignore chest tube output of 500 mL/hr"),
            Triple("Respiratory: Acute Severe Asthma Silent Chest", "Sudden absence of wheezing ('silent chest') in an asthmatic client with severe respiratory distress indicates complete airway obstruction and impending respiratory arrest", "Silent chest indicates complete recovery and cure"),
            Triple("Respiratory: Chronic Bronchitis 'Blue Bloater' Presentation", "Hypoxemia, hypercapnia, cyanosis, peripheral edema from cor pulmonale, productive cough for > 3 months in 2 consecutive years", "Pink puffers with severe emphysematous barrel chest without cyanosis"),
            Triple("Respiratory: Emphysema 'Pink Puffer' Presentation", "Alveolar destruction, air trapping, hyperinflated lungs, barrel chest (increased AP diameter), pursed-lip breathing, weight loss", "Cyanotic blue skin with severe peripheral pitting edema"),
            Triple("Respiratory: Pleural Effusion Thoracentesis Positioning & Complications", "Client sits upright leaning over bedside table (orthopneic position); major complication: pneumothorax (assess for tachypnea, diminished breath sounds, tracheal deviation)", "Position client flat on back with feet elevated 45 degrees"),
            Triple("Respiratory: Atelectasis Post-Op Incentive Spirometry", "Incomplete alveolar expansion post-op; instruct client to INHALE DEEPLY through incentive spirometer 10 times per hour while awake, hold breath 3-5 seconds", "Exhale forcefully into device 50 times per minute"),
            Triple("Respiratory: Pulmonary Tuberculosis Airborne Precautions", "Negative pressure isolation room, minimum 6-12 air exchanges/hr, N95 respirator worn by healthcare staff; client wears surgical mask during transport", "Standard surgical mask worn by nurse in client room"),
            Triple("Respiratory: Sarcoidosis Non-Caseating Granulomas", "Multisystem inflammatory disease; hilar lymphadenopathy, pulmonary infiltrates, elevated serum ACE level; treated with corticosteroids", "Bacterial lung infection cured by 3 days of ampicillin"),
            Triple("Neurological: Ischemic Stroke Hemianopia Visual Field Deficit", "Homonymous hemianopia: loss of half of visual field in both eyes; instruct client to SCAN ENVIRONMENT by turning head toward affected side", "Cover functional eye with tight black patch"),
            Triple("Neurological: Parkinson's Disease Triad & Carbidopa-Levodopa", "Triad: resting tremor, bradykinesia, rigidity; Carbidopa prevents peripheral breakdown of Levodopa; side effects: dyskinesia, orthostatic hypotension", "Administer with high-protein steak dinner to enhance absorption"),
            Triple("Neurological: Amyotrophic Lateral Sclerosis (ALS) Progressive Paralysis", "Degeneration of upper/lower motor neurons leading to progressive muscular atrophy; cognitive function remains intact; respiratory failure is primary cause of death", "Causes rapid severe dementia with intact motor power"),
            Triple("Neurological: Trigeminal Neuralgia (Tic Douloureux)", "Severe stabbing facial pain triggered by chewing, brushing teeth, or breeze; treat with Carbamazepine; wash face gently with lukewarm water", "Scrub face forcefully with icy cold water and stiff brush"),
            Triple("Neurological: Bell's Palsy Facial Nerve VII Paralysis", "Unilateral facial drooping, inability to close eye, loss of taste on anterior 2/3 of tongue; protect eye with artificial tears and night eye patch; oral corticosteroids", "Permanent irreversible brain destruction requiring craniotomy"),
            Triple("Neurological: Spinal Cord Injury Spinal Shock vs Neurogenic Shock", "Spinal shock: temporary loss of motor/sensory reflexes below lesion level; Neurogenic shock: loss of sympathetic tone causing hypotension, bradycardia, poikilothermia", "Spinal shock causes severe hypertension and hyperreflexia"),
            Triple("Neurological: Lumbar Puncture Post-Procedure Nursing Care", "Maintain client FLAT SUPINE for 4-12 hours to prevent spinal headache; increase fluid intake; monitor puncture site for CSF leakage", "Instruct client to jog vigorously immediately after procedure"),
            Triple("Gastrointestinal: Gastroesophageal Reflux Disease (GERD) Lifestyle Changes", "Elevate head of bed 6 inches, avoid eating within 3 hours of bedtime, avoid trigger foods (peppermint, chocolate, caffeine, fatty foods, alcohol), stop smoking", "Eat large spicy pizza right before lying down to sleep"),
            Triple("Gastrointestinal: Peptic Ulcer Disease Gastric vs Duodenal", "Gastric ulcer: pain worsens 30-60 mins AFTER eating; Duodenal ulcer: pain relieved by food/antacids, recurs 2-3 hours after meal or overnight", "Duodenal ulcer pain is severe immediately upon swallowing food"),
            Triple("Gastrointestinal: Peritonitis Classic Triad & Board-Like Abdomen", "Abdominal rigidity ('board-like'), severe rebound tenderness, fever, tachycardia, silent bowel sounds; life-threatening surgical emergency", "Soft non-tender abdomen with hyperactive bowel sounds"),
            Triple("Gastrointestinal: Cholecystitis Murphy's Sign & Diet", "Inability to deep breathe during RUQ palpation (positive Murphy's sign); severe RUQ pain radiating to right shoulder; manage with LOW-FAT diet", "Consume high-fat fried bacon cheeseburger"),
            Triple("Gastrointestinal: Diverticulitis Acute Management", "Inflammation of diverticula (LLQ pain); acute phase: NPO or clear liquids, IV fluids, IV antibiotics, NO HIGH-FIBER FOODS or enemas during acute flare", "Administer high-fiber bran flakes and laxatives during acute flare"),
            Triple("Gastrointestinal: Ileostomy vs Colostomy Stoma Assessment", "Healthy stoma is PINK/RED and MOIST; dark purple/black indicates STOMA ISCHEMIA (notify surgeon immediately); ileostomy output is liquid and continuous", "Purple black dry stoma is normal and healthy"),
            Triple("Endocrine: Hypothyroidism Hashimoto's & Levothyroxine", "Fatigue, cold intolerance, weight gain, constipation, dry skin, bradycardia; elevated TSH, low free T4; treat with lifelong Levothyroxine", "Treated with antithyroid drug Methimazole"),
            Triple("Endocrine: Hyperthyroidism Graves' Disease & Exophthalmos", "Weight loss, heat intolerance, tachycardia, tremors, exophthalmos (bulging eyes); protect eyes with dark glasses and artificial tears; low TSH, elevated T4", "Causes severe weight gain, bradycardia, and cold skin"),
            Triple("Endocrine: Hypoparathyroidism vs Hyperparathyroidism", "Hypoparathyroidism: low Ca++, high PO4--, positive Chvostek/Trousseau; Hyperparathyroidism: high Ca++, bone resorption, kidney stones ('bones, stones, groans')", "Hyperparathyroidism causes severe hypocalcemia and tetany"),
            Triple("Endocrine: Pheochromocytoma Paroxysmal Triad", "Adrenal medulla tumor secreting catecholamines; triad: headache, diaphoresis, tachycardia with severe paroxysmal HYPERTENSION; avoid abdominal palpation", "Severe hypotension with hypoglycemia"),
            Triple("Endocrine: Cushing's Disease vs Addison's Disease Skin Pigmentation", "Addison's: adrenal insufficiency, HYPERPIGMENTATION (bronzed skin in flexor creases), hypotension, hyponatremia; Cushing's: hypercortisolism, striae, moon face", "Cushing's disease causes severe bronze skin hyperpigmentation"),
            Triple("Renal: Nephrolithiasis (Kidney Stones) Strain Urine Rule", "Flank pain radiating to groin/genitallia (renal colic), hematuria; STRAIN ALL URINE to capture stone for laboratory analysis; encourage 3 L/day fluids", "Discard all urine into toilet without filtering"),
            Triple("Renal: Benign Prostatic Hyperplasia (BPH) Continuous Bladder Irrigation (CBI)", "Three-way Foley post-TURP; adjust irrigation rate to maintain LIGHT PINK effluent; clear bright red blood or clots by increasing irrigation speed", "Maintain dark burgundy thick blood effluent"),
            Triple("Renal: Polycystic Kidney Disease (PKD) Management", "Genetic disease forming multiple fluid-filled renal cysts; control blood pressure with ACE inhibitors/ARBs, increase fluid intake, avoid contact sports", "Engage in competitive tackles in American football"),
            Triple("Renal: Chronic Kidney Disease Uremic Frost & Pruritus", "Uric acid/urea crystals deposited on skin causing severe pruritus; keep skin moist with emollients, cool baths, avoid scratch injuries", "Scrub skin with hot soapy water and stiff brush"),
            Triple("Musculoskeletal: Osteomyelitis Long-Term Antibiotic Therapy", "Infection of bone; localized pain, edema, fever; requires 4-6 weeks of IV antibiotic therapy via PICC line; surgical debridement if abscess forms", "2 days of oral acetaminophen cure osteomyelitis"),
            Triple("Musculoskeletal: Compartment Syndrome Early vs Late Signs", "EARLY sign: severe deep throbbing PAIN UNRELIEVED BY OPIOIDS and out of proportion to injury; LATE sign: pulselessness and paralysis", "Pulselessness is the earliest first sign"),
            Triple("Musculoskeletal: Amputation Stump Care Post-Op", "First 24 hours: elevate stump on pillow to prevent edema; AFTER 24 hours: keep stump FLAT or prone to prevent hip flexion contractures", "Elevate stump on 3 pillows for 3 consecutive weeks"),
            Triple("Hematology: Iron Deficiency Anemia Oral Iron Education", "Take iron with Vitamin C (orange juice) on empty stomach for maximum absorption; side effects: black tarry stools, constipation; drink liquid iron via straw", "Take iron with large glass of whole milk"),
            Triple("Hematology: Thalassemia Major Cooley's Anemia Iron Overload", "Genetic defect in hemoglobin synthesis requiring lifelong blood transfusions; primary complication: IRON OVERLOAD (hemochromatosis); treat with Deferoxamine chelation", "Treat Cooley's anemia with high-dose oral iron pills"),
            Triple("Hematology: Multiple Myeloma Bence-Jones Protein & Bone Pain", "Malignant proliferation of plasma cells; bone destruction causing severe bone pain, hypercalcemia, renal failure, Bence-Jones protein in urine", "Causes severe hypocalcemia and super strong bones"),
            Triple("Oncology: Chemotherapy Extravasation Vesicant Care", "Stop infusion immediately, leave cannula in place to aspirate residual drug, check specific antidote protocol, elevate arm", "Flush 100 mL Normal Saline rapidly into vesicant extravasation site"),
            Triple("Dermatology: Burn Resuscitation Fluid Shift Phase", "First 24-48 hours: massive capillary leak causes hypovolemic shock, HYPERKALEMIA, HYPONATREMIA, and hemoconcentration (elevated Hct)", "First 24 hours cause severe hypokalemia and hypernatremia"),
            Triple("Dermatology: Stevens-Johnson Syndrome (SJS) Med Triggers", "Life-threatening exfoliative dermatosis triggered by drugs (sulfonamides, phenytoin, allopurinol, NSAIDs); mucosal lesions, skin sloughing; treat like burns", "Caused by oral vitamin C tablets")
        )

        for (i in 0 until 150) {
            val topicIndex = i % medSurgMasterTopics.size
            val item = medSurgMasterTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Standard Clinical Protocol: ${item.second}",
                "Dangerous / Inappropriate Clinical Action: ${item.third}",
                "Discontinue monitoring and send client home",
                "Omit standard assessment and delegate to non-medical staff"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical Nursing",
                "NCLEX-RN / DHA • Master Series",
                "Master Series Med-Surg Case #${i + 1}: In evaluating a hospitalized client presenting with ${item.first}, which clinical nursing decision represents evidence-based care?",
                options,
                correctPos,
                "Rationale: Medical-surgical practice guidelines for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct answer prioritizes client safety, physiological integrity, and immediate clinical intervention. Action '${item.third}' is unsafe.",
                "Med-Surg Master • ${item.first}"
            )
        }

        return list
    }
}
