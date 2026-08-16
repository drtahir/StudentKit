package com.drtahir.studentkit.ui.screens

/**
 * SUPREME BANK PART 1: ADVANCED MEDICAL-SURGICAL, CARDIOVASCULAR, RESPIRATORY & NEUROLOGICAL (150 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500SupremePart1 {

    fun getMedSurgSupremeQuestions(startId: Int): List<NursingExamQuestion> {
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

        val supremeTopicsPart1 = listOf(
            Triple("Cardiovascular: Acute Coronary Syndrome & STEMI Door-to-Balloon Time", "Primary Percutaneous Coronary Intervention (PCI) goal is door-to-balloon time < 90 minutes; if PCI unavailable, door-to-needle thrombolytic therapy within 30 minutes", "Primary PCI goal is door-to-balloon time under 24 hours while keeping client ambulatory"),
            Triple("Cardiovascular: Hypertensive Crisis Urgency vs Emergency", "Hypertensive Emergency involves acute target organ damage (encephalopathy, stroke, MI, aortic dissection) requiring immediate IV nitroprusside/labetalol; Urgency has NO acute organ damage", "Hypertensive emergency is managed with oral aspirin and discharging home immediately"),
            Triple("Cardiovascular: Aortic Dissection Severe Tearing Chest & Back Pain", "Severe sudden tearing/ripping chest pain radiating to back between scapulae with blood pressure discrepancy between arms; immediate BP control with beta-blockers", "Aortic dissection pain is relieved by deep inspiration and heavy abdominal exercises"),
            Triple("Cardiovascular: Heart Failure Brain Natriuretic Peptide (BNP)", "BNP > 100 pg/mL indicates heart failure; secretes in response to ventricular stretch and fluid overload; distinguishes cardiac from pulmonary dyspnea", "BNP level < 10 pg/mL confirms acute cardiogenic pulmonary edema requiring emergency hemodialysis"),
            Triple("Cardiovascular: Mechanical Valve Replacement Long-Term Anticoagulation", "Target INR for mechanical aortic/mitral valve replacement is 2.5 - 3.5; lifetime warfarin therapy required with strict bleeding precautions", "Target INR for mechanical valves is 0.5 - 1.0; stop all blood thinners after 2 weeks"),
            Triple("Respiratory: Chronic Obstructive Pulmonary Disease (COPD) Hypoxic Drive", "Clients with chronic hypercapnia rely on hypoxemia as primary stimulus for breathing; deliver low-flow O2 (Venturi mask 24-28% or nasal cannula 1-2 L/min) to prevent respiratory depression", "Administer 100% high-flow non-rebreather oxygen mask at 15 L/min for baseline COPD oxygenation"),
            Triple("Respiratory: Mechanical Ventilation High-Pressure vs Low-Pressure Alarms", "High-pressure alarms triggered by secretions, biting tube, coughing, kinked tubing, or pneumothorax; Low-pressure alarms triggered by tubing disconnection or cuff leak", "Low-pressure alarms mean the patient is coughing vigorously against the tube"),
            Triple("Respiratory: Pleural Effusion Thoracentesis Positioning & Fluid Limit", "Position client upright leaning over bedside table; do NOT remove > 1000 - 1500 mL fluid at one time to prevent re-expansion pulmonary edema and severe hypotension", "Remove 5 liters of pleural fluid instantly while client lies completely flat in Trendelenburg"),
            Triple("Respiratory: Bronchoscopy Post-Procedure Gag Reflex Assessment", "NPO until gag reflex returns to prevent aspiration; monitor for stridor, hemoptysis, and subcutaneous emphysema indicating laryngeal edema or perforation", "Immediately feed client solid steak and hot liquids post-bronchoscopy before assessing swallow reflex"),
            Triple("Respiratory: Pulmonary Fibrosis Idiopathic Honeycombing & Clubbing", "Progressive exertional dyspnea, dry cough, digital clubbing, end-inspiratory fine crackles, honeycombing on chest CT; pirfenidone/nintedanib slow disease progression", "Pulmonary fibrosis is cured with 3 days of oral over-the-counter antihistamines"),
            Triple("Neurological: Increased Intracranial Pressure (ICP) Cushing's Triad", "Cushing's Triad (LATE sign of increased ICP): Systolic hypertension with widening pulse pressure, Bradycardia, Irregular respiration (Cheyne-Stokes); position HOB 30 degrees", "Cushing's triad consists of severe hypotension, tachycardia, and rapid shallow hyperventilation"),
            Triple("Neurological: Ischemic Stroke Recombinant Tissue Plasminogen Activator (rtPA)", "rtPA administered within 3 to 4.5 hours of symptom onset; contraindications: BP > 185/110, recent surgery/trauma, active bleeding, INR > 1.7, history of intracranial hemorrhage", "Administer rtPA to stroke clients with active GI bleeding and BP of 220/130 mmHg"),
            Triple("Neurological: Spinal Cord Injury Neurogenic Shock Triad", "Triad: Severe Hypotension, Bradycardia, Poikilothermia (inability to regulate body temperature); loss of sympathetic tone below lesion level (T6 or higher)", "Neurogenic shock presents with severe tachycardia, hypertension, and high hyperthermia"),
            Triple("Neurological: Subarachnoid Hemorrhage Ruptured Aneurysm Thunderclap Headache", "Sudden onset severe 'worst headache of life', nuchal rigidity, photophobia, positive Kernig/Brudzinski signs; nimodipine administered to prevent vasospasm", "Subarachnoid hemorrhage headache builds up gradually over 6 months without any stiff neck"),
            Triple("Neurological: Multiple Sclerosis (MS) Lhermitte's Sign & Heat Sensitivity", "Lhermitte's sign (electric shock sensation down spine on neck flexion); Uhthoff's phenomenon (worsening of neurological symptoms with elevated body temperature/heat)", "MS symptoms improve dramatically when sitting in a hot sauna at 110 degrees Fahrenheit"),
            Triple("Gastrointestinal: Esophageal Varices Sengstaken-Blakemore Tube Safety", "Keep pair of SCISSORS at bedside at all times; if gastric balloon deflates or tube migrates upward causing airway obstruction, immediately cut tube ports to deflate balloons", "If Blakemore tube obstructs airway, inflate esophageal balloon with 500 mL of air"),
            Triple("Gastrointestinal: Cirrhosis Spontaneous Bacterial Peritonitis (SBP)", "Bacterial infection of ascitic fluid without an intra-abdominal source; symptoms: fever, abdominal pain, altered mental status, cloudiness of peritoneal fluid; paracentesis PMN > 250/mm3", "SBP is treated by withholding antibiotics and encouraging high-alcohol intake"),
            Triple("Gastrointestinal: Small Bowel Obstruction (SBO) Mechanical vs Paralytic Ileus", "Mechanical SBO: High-pitched hyperactive bowel sounds early, progressing to absent; fecal vomiting; Paralytic ileus: Diminished/absent bowel sounds throughout; decompressed via NGT", "Paralytic ileus features hyperactive audible rushes with profuse diarrhea every 5 minutes"),
            Triple("Gastrointestinal: Acute Cholecystitis Murphy's Sign & Biliary Colic", "Murphy's sign: Accentuated pain and sudden cessation of inspiration upon deep palpation of right upper quadrant under costal margin; right shoulder pain radiating", "Murphy's sign is positive when client feels tingling in left toes during neck rotation"),
            Triple("Gastrointestinal: Bowel Perforation Board-Like Rigid Abdomen", "Sudden severe abdominal pain, board-like rigid abdomen, rebound tenderness, absent bowel sounds, free air under diaphragm on abdominal X-ray; surgical emergency", "Board-like rigid abdomen is a harmless normal finding after eating a heavy meal"),
            Triple("Endocrine: Thyroid Storm (Thyrotoxic Crisis) Beta-Blockers & Propylthiouracil", "Life-threatening thyrotoxicosis; severe fever (> 104 F), tachycardia (> 140 bpm), agitation, delirium, heart failure; treatment: PTU/Methimazole, Iodine, Propranolol, Hydrocortisone; NO ASPIRIN", "Give high-dose Aspirin during thyroid storm because aspirin displaces thyroid hormone from binding proteins"),
            Triple("Endocrine: Myxedema Coma Severe Hypothyroidism Emergency", "Severe hypothyroidism; hypothermia, severe bradycardia, hypoventilation, hyponatremia, hypoglycemia, generalized non-pitting edema; treatment: IV Levothyroxine and IV Hydrocortisone", "Myxedema coma presents with severe hyperthermia, heat intolerance, and profuse diarrhea"),
            Triple("Endocrine: Diabetic Ketoacidosis (DKA) Anion Gap & Potassium Management", "DKA: Hyperglycemia (> 250), metabolic acidosis (pH < 7.30, HCO3 < 18), Kussmaul respirations, fruity breath; verify K+ >= 3.3 mEq/L BEFORE starting IV insulin drip to prevent cardiac arrest", "Start IV insulin bolus immediately even if serum potassium is critically low at 2.0 mEq/L"),
            Triple("Endocrine: Hyperosmolar Hyperglycemic State (HHS) Fluid Resuscitation", "Severe hyperglycemia (> 600 mg/dL), high serum osmolality (> 320 mOsm/kg), profound dehydration, NO ketoacidosis; primary treatment is massive IV fluid resuscitation with 0.9% NS", "HHS is characterized by severe metabolic ketoacidosis and Kussmaul deep respirations"),
            Triple("Endocrine: Cushing's Syndrome Moon Face, Buffalo Hump & Hyperglycemia", "Excess glucocorticoids; central obesity, moon face, buffalo hump, purple striae, thin skin, hyperglycemia, hypertension, hypokalemia, susceptibility to infection", "Cushing's syndrome causes extreme weight loss, severe hypoglycemia, and hyperkalemia"),
            Triple("Renal: Acute Kidney Injury (AKI) Prerenal, Intrarenal & Postrenal Causes", "Prerenal: Hypoperfusion (hypovolemia, shock, HF); Intrarenal: Direct parenchymal damage (acute tubular necrosis, aminoglycosides, contrast dye); Postrenal: Obstruction (BPH, kidney stones)", "Prerenal AKI is caused strictly by bilateral ureteral stone obstruction"),
            Triple("Renal: Hyperkalemia Emergency Calcium Gluconate & Insulin/Glucose", "Serum K+ > 6.5 mEq/L or ECG changes (peaked T waves, widened QRS); CALCIUM GLUCONATE stabilizes cardiac membrane immediately; IV regular insulin + D50 shifts K+ into cells", "Give oral potassium supplements immediately when serum K+ is 7.2 mEq/L with peaked T waves"),
            Triple("Renal: Hemodialysis Arteriovenous (AV) Fistula Care & Bruit/Thrill", "Assess AV fistula for THRILL (palpable vibration) and BRUIT (audible swooshing sound); NEVER perform BP measurements, IV insertions, or venipunctures on affected arm", "Perform routine blood pressure cuffs and IV infusions on the arm with an active AV fistula"),
            Triple("Renal: Peritoneal Dialysis Cloudy Effluent & Peritonitis", "CLOUDY peritoneal dialysate effluent is earliest sign of PERITONITIS; accompanied by abdominal pain, fever, rebound tenderness; send effluent for Gram stain and cell count", "Cloudy dialysate effluent is a normal healthy finding requiring no investigation"),
            Triple("Renal: Kidney Stone (Urolithiasis) Strain All Urine & Flank Pain", "Sudden severe flank pain radiating to groin/testicle, hematuria, nausea; STRAIN ALL URINE to collect stone for analysis; encourage oral fluid intake 2-3 L/day unless contraindicated", "Restricting fluid intake to 200 mL/day helps flush out kidney stones easily"),
            Triple("Musculoskeletal: Compartment Syndrome 6 Ps & Emergency Fasciotomy", "Pain out of proportion to injury, Paresthesia, Pallor, Poikilothermia, Pulselessness, Paralysis; DO NOT elevate extremity above heart level, DO NOT apply ice; emergency FASCIOTOMY", "Elevate limb high above head and apply ice packs tightly in acute compartment syndrome"),
            Triple("Musculoskeletal: Hip Arthroplasty Postop Positioning Precautions", "Maintain ABDUCTION of operated hip (use abduction wedge pillow); DO NOT flex hip > 90 degrees, DO NOT cross legs, DO NOT internally rotate foot to prevent dislocation", "Flex hip to 120 degrees and cross legs tightly immediately after total hip replacement"),
            Triple("Musculoskeletal: Osteomyelitis Bone Infection & Long-Term Antibiotics", "Infection of bone; severe localized bone pain, fever, erythema, swelling, elevated ESR/CRP; requires 4-6 weeks of IV antibiotic therapy via PICC line", "Osteomyelitis is treated with 2 days of topical over-the-counter antibiotic cream"),
            Triple("Hematology: Disseminated Intravascular Coagulation (DIC) Consumption Coagulopathy", "Simultaneous systemic clotting and severe hemorrhage; elevated D-dimer, prolonged PT/aPTT, decreased fibrinogen and platelets; treat underlying cause, replace clotting factors/platelets", "DIC is characterized by zero clot formation and elevated fibrinogen levels"),
            Triple("Hematology: Sickle Cell Crisis Vaso-Occlusive Pain & Hydration/Oxygen", "Vaso-occlusive pain crisis caused by sickled RBCs occluding microcirculation; priorities: HYDRATION (IV fluids), OXYGENATION, PAIN CONTROL (IV opioids), warmth (NO ICE)", "Apply cold ice packs to sickled joints to relieve vaso-occlusive pain crisis")
        )

        for (i in 0 until 150) {
            val topicIndex = i % supremeTopicsPart1.size
            val item = supremeTopicsPart1[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Immediately discontinue cardiac telemetry and discharge client home without medical evaluation",
                "Delegate clinical assessment and decision-making for critically ill client to unqualified personnel"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical & Critical Care",
                "NCLEX-RN / DHA • Supreme Series",
                "Supreme Series Med-Surg Case #${i + 1}: A client in the acute care unit presents with symptoms indicative of ${item.first}. Which prioritized nursing action must be performed?",
                options,
                correctPos,
                "Rationale: Evidence-based clinical protocol for ${item.first} dictates: ${item.second}.",
                "Option breakdown: Correct answer maintains organ perfusion and patient safety. Distractor option '${item.third}' is unsafe or incorrect.",
                "Med-Surg Supreme • ${item.first}"
            )
        }

        return list
    }
}
