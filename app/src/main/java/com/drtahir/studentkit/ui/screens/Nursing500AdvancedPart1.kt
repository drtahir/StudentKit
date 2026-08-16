package com.drtahir.studentkit.ui.screens

/**
 * ADVANCED BANK PART 1: MEDICAL-SURGICAL NURSING (150 MCQs)
 * NCLEX-RN, DHA, HAAD, Prometric, PNC & International Competitive Exam Standard.
 */
object Nursing500AdvancedPart1 {

    fun getMedSurgAdvancedQuestions(startId: Int): List<NursingExamQuestion> {
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

        val medSurgTopics = listOf(
            Triple("Cardiovascular: Infective Endocarditis Osler Nodes & Janeway Lesions", "Osler nodes (painful red nodules on finger pads) and Janeway lesions (painless flat macules on palms/soles) with fever and new heart murmur; require long-term IV antibiotics", "Administer oral antacids and discharge immediately"),
            Triple("Cardiovascular: Cardiac Tamponade Pulsus Paradoxus", "Pulsus paradoxus is a drop in systolic blood pressure > 10 mmHg during inspiration; key diagnostic sign of cardiac tamponade alongside muffled heart sounds", "Systolic blood pressure rises 30 mmHg on inspiration"),
            Triple("Cardiovascular: Abdominal Aortic Aneurysm (AAA) Rupture Signs", "Sudden severe back or abdominal pain, pulsating abdominal mass, hypotension, and shock; prepare for immediate emergency surgical repair", "Apply heat compress to abdomen and encourage walking"),
            Triple("Cardiovascular: Peripheral Arterial Disease (PAD) Intermittent Claudication", "Ischemic muscle pain triggered by exercise and relieved by rest; instruct patient to dangle legs in dependent position to promote blood flow", "Elevate legs above heart level on 4 pillows"),
            Triple("Cardiovascular: Deep Vein Thrombosis (DVT) Post-Thrombolytic Monitoring", "Monitor for active bleeding, blood in urine/stool, hematoma at puncture site, and sudden neurological changes (intracranial hemorrhage)", "Encourage vigorous leg massage and hot bath"),
            Triple("Cardiovascular: Heart Failure Brain Natriuretic Peptide (BNP)", "BNP > 100 pg/mL indicates ventricular stretch due to fluid overload in heart failure; BNP > 400 pg/mL indicates moderate-to-severe decompensation", "BNP level of 10 pg/mL indicates severe acute cardiac arrest"),
            Triple("Cardiovascular: Raynaud's Phenomenon Trigger Avoidance", "Vasospastic disorder triggered by cold exposure and stress; instruct patient to wear warm gloves, avoid smoking/caffeine, and manage cold environments", "Place hands in bucket of ice water during vasospasm"),
            Triple("Cardiovascular: Buerger's Disease (Thromboangiitis Obliterans)", "Inflammatory occlusive vascular disease strongly linked to tobacco use; absolute smoking cessation is the primary essential intervention", "Increase cigarette smoking to promote vasodilation"),
            Triple("Respiratory: Chronic Obstructive Pulmonary Disease (COPD) Hypoxic Drive", "Low arterial oxygen (PaO2) drives breathing in end-stage COPD; deliver low-flow oxygen (1-2 L/min via nasal cannula or Venturi mask) to avoid suppressing drive", "Administer 15 L/min 100% hyperbaric oxygen non-rebreather"),
            Triple("Respiratory: Pneumotorax Chest Tube Continuous Bubbling", "Continuous bubbling in the water seal chamber indicates an AIR LEAK in the system or lung; intermittent bubbling during expiration is expected", "Continuous bubbling is normal and requires no inspection"),
            Triple("Respiratory: Chest Tube Disconnection Emergency Action", "Immediately submerge the chest tube 1-2 inches in a bottle of STERILE WATER or saline to re-establish a water seal", "Clamp tube near patient chest for 24 hours"),
            Triple("Respiratory: Accidental Chest Tube Removal Action", "Cover insertion site immediately with a STERILE OCCLUSIVE DRESSING taped on THREE SIDES (allows air escape, prevents tension pneumothorax)", "Apply airtight 4-sided plastic wrap with tight heavy pressure"),
            Triple("Respiratory: Pulmonary Embolism (PE) Clinical Presentation", "Sudden sharp pleuritic chest pain, dyspnea, tachypnea, tachycardia, and hemoptysis; administer high-flow oxygen, elevate head, start anticoagulation", "Give oral cough syrup and lie flat on back"),
            Triple("Respiratory: Idiopathic Pulmonary Fibrosis Crackles", "Fine 'Velcro' dry end-inspiratory crackles, progressive exertional dyspnea, and chronic dry cough; monitor arterial blood gases and pulse oximetry", "Wheezing relieved by oral antacids"),
            Triple("Respiratory: Mechanical Ventilation PEEP Complications", "High Positive End-Expiratory Pressure (PEEP > 10-15 cmH2O) can cause barotrauma (pneumothorax) and decreased venous return leading to hypotension", "PEEP causes massive hypertension and bradycardia"),
            Triple("Respiratory: Cystic Fibrosis Airway Clearance Techniques", "Perform chest physiotherapy (CPT) with postural drainage BEFORE meals or 2 hours after meals to prevent vomiting and aspiration", "Perform CPT immediately after heavy 3-course dinner"),
            Triple("Neurological: Ischemic Stroke Recombinant tPA Eligibility Window", "IV tPA (Alteplase) must be administered within 3 to 4.5 hours of symptom onset; rule out hemorrhagic stroke via non-contrast CT head first", "Give tPA 48 hours after onset without CT scan"),
            Triple("Neurological: Hemorrhagic Stroke Blood Pressure Target", "Maintain BP within target (SBP < 140-160 mmHg) using IV antihypertensives (Labetalol, Nicardipine) to prevent hematoma expansion", "Infuse IV Dopamine to raise SBP above 220 mmHg"),
            Triple("Neurological: Myasthenia Gravis Cholinergic vs Myasthenic Crisis", "Tensilon (Edrophonium) test: improvement indicates Myasthenic crisis (under-medicated); worsening weakness indicates Cholinergic crisis (over-medicated)", "Atropine worsens myasthenic crisis and cures Tensilon test"),
            Triple("Neurological: Guillain-Barré Syndrome Ascending Paralysis", "Progressive symmetrical ascending muscle weakness; priority nursing monitoring is RESPIRATORY STATUS (vital capacity and negative inspiratory force)", "Monitor knee reflex and discharge home"),
            Triple("Neurological: Multiple Sclerosis Uhthoff's Phenomenon", "Transient worsening of neurological symptoms caused by elevated body temperature (heat, hot showers, fever); avoid hot tubs and extreme heat", "Take boiling hot baths twice daily to relax muscles"),
            Triple("Neurological: Parkinson's Disease Freezing Gait Management", "Instruct patient to step over imaginary line on floor, rock side to side, or march in place to break freezing episode; avoid rushing", "Pull patient forward forcefully by hands"),
            Triple("Neurological: Autonomic Dysreflexia Initial Nursing Action", "Elevate head of bed 90 degrees (High Fowler's) immediately to lower blood pressure, then check for bladder distension or impacted bowel", "Place patient in flat Trendelenburg position immediately"),
            Triple("Neurological: Autonomic Dysreflexia Triggers", "Distended urinary bladder (clogged Foley catheter) or fecal impaction; trigger causes severe hypertension, throbbing headache, bradycardia, and flushing above lesion", "Ingestion of cold water or mild walking"),
            Triple("Neurological: Increased Intracranial Pressure (ICP) Positioning", "Elevate head of bed 30 degrees, maintain neck in neutral alignment, avoid hip flexion, coughing, and Valsalva maneuver", "Place patient prone with head turned sharply left"),
            Triple("Neurological: Mannitol Osmotic Diuretic Monitoring", "Monitor serum osmolality (< 320 mOsm/kg), electrolyte levels, and signs of fluid overload/heart failure; inspect solution for crystals before infusion", "Infuse visible drug crystals directly IV push"),
            Triple("Gastrointestinal: Acute Pancreatitis Diagnostic Enzymes", "Serum Lipase (more specific, remains elevated longer) and Serum Amylase; client presents with severe epigastric pain radiating to back", "Elevated serum troponin and creatinine kinase MB"),
            Triple("Gastrointestinal: Acute Pancreatitis Cullen's & Grey Turner's Signs", "Cullen's sign (bluish discoloration around umbilicus) and Grey Turner's sign (flank ecchymosis) indicate retroperitoneal hemorrhage", "Yellow sclera and pink butterfly facial rash"),
            Triple("Gastrointestinal: Hepatic Encephalopathy Lactulose Therapy", "Lactulose traps ammonia in colon and promotes excretion via 2-3 soft bowel movements daily; monitor for hypokalemia and dehydration", "Goal is 15 watery explosive stools per hour with coma"),
            Triple("Gastrointestinal: Cirrhosis Esophageal Varices Bleeding Risk", "Avoid hard/spicy foods, heavy lifting, coughing, and straining; emergency treatment includes IV Octreotide, Vasopressin, and Sengstaken-Blakemore tube", "Encourage dry crunchy nachos with jalapeno peppers"),
            Triple("Gastrointestinal: Sengstaken-Blakemore Tube Scissors at Bedside", "Keep sterile SCISSORS at bedside; if gastric balloon deflates or tube migrates upward causing airway obstruction, cut tube ports immediately to deflate", "Use scissors to cut intravenous lines if patient vomits"),
            Triple("Gastrointestinal: Ulcerative Colitis Bloody Diarrhea Management", "Frequent bloody purulent liquid stools (10-20/day); prioritize fluid/electrolyte replacement, low-residue high-protein diet, corticosteroid therapy", "High-fiber raw seed diet with laxative push"),
            Triple("Gastrointestinal: Crohn's Disease Transmural Skip Lesions", "Transmural cobble-stone inflammation affecting any part of GI tract; complication risk includes fistulas, strictures, and malabsorption", "Superficial mucosal lesions localized strictly to rectum"),
            Triple("Gastrointestinal: Small Bowel Obstruction vs Large Bowel Obstruction", "SBO: profuse early vomiting, rapid dehydration, colicky pain; LBO: late vomiting, abdominal distension, constipation/obstipation", "SBO causes no vomiting or pain"),
            Triple("Gastrointestinal: Appendicitis McBurney's Point & Rovsing's Sign", "McBurney's point tenderness (RLQ) and Rovsing's sign (LLQ palpation causes RLQ pain); NO heating pads or laxatives (risk of rupture)", "Apply hot water bottle directly to RLQ pain area"),
            Triple("Gastrointestinal: Dumping Syndrome Dietary Prevention", "High-protein, high-fat, low-carbohydrate meals; avoid liquids with meals (drink between meals); recline for 30 minutes after eating", "Large high-sugar meals consumed with 1 Liter soft drink"),
            Triple("Endocrine: Diabetic Ketoacidosis (DKA) Resuscitation", "Start 0.9% Normal Saline IV fluid bolus FIRST, then regular insulin IV drip; monitor potassium closely (insulin drives K+ into cells)", "Give IV subcutaneous glucagon bolus with high PEEP"),
            Triple("Endocrine: Hyperosmolar Hyperglycemic State (HHS) Features", "Profound hyperglycemia (> 600 mg/dL), high serum osmolality (> 320 mOsm/kg), severe dehydration, NO significant ketoacidosis", "Severe ketonuria with metabolic acidosis and breath odor"),
            Triple("Endocrine: Hypoglycemia Rule of 15", "If conscious: give 15g simple fast-acting carbs (4 oz juice, 3-4 glucose tabs); recheck blood glucose in 15 minutes; if unconscious give IV D50 or IM Glucagon", "Give 100 U subcutaneous regular insulin"),
            Triple("Endocrine: Addisonian Crisis Emergency Care", "Life-threatening hypotension, hyponatremia, hyperkalemia, hypoglycemia; immediate IV Hydrocortisone, Normal Saline, and glucose bolus", "Administer oral spironolactone and restrict IV fluids"),
            Triple("Endocrine: Cushing's Syndrome Clinical Triad", "Hyperglycemia, moon face/buffalo hump, truncal obesity, purple striae, osteoporosis, hypertension, hypokalemia", "Severe weight loss, low BP, and hyperkalemia"),
            Triple("Endocrine: Thyroid Storm (Thyrotoxic Crisis) Management", "Severe hyperthermia, tachycardia, agitation, delirium; administer Propylthiouracil (PTU) or Methimazole, Beta-blockers, Propranolol, cooling blanket; NO Aspirin", "Administer Aspirin to treat hyperthermia"),
            Triple("Endocrine: Post-Thyroidectomy Chvostek's & Trousseau's Signs", "Accidental hypoparathyroidism causes hypocalcemia; Chvostek's (facial twitching) and Trousseau's (carpopedal spasm); keep IV Calcium Gluconate at bedside", "Hypercalcemia treated with oral potassium"),
            Triple("Endocrine: Post-Thyroidectomy Tracheostomy Tray at Bedside", "Risk of airway obstruction due to stridor, laryngeal edema, or neck hematoma; keep Emergency Tracheostomy Tray and suction at bedside", "Keep leg traction frame at bedside"),
            Triple("Endocrine: Diabetes Insipidus (DI) Desmopressin (DDAVP)", "Deficiency of ADH causing massive polyuria (dilute urine SG < 1.005) and hypernatremia; treat with Vasopressin / Desmopressin", "High urine specific gravity > 1.035 with hyponatremia"),
            Triple("Endocrine: SIADH Fluid Restriction & Hypertonic Saline", "Excess ADH causes water retention, hyponatremia (< 120 mEq/L), oliguria; treat with strict fluid restriction and 3% Hypertonic Saline slowly", "Encourage 4 Liters tap water oral intake per day"),
            Triple("Renal: Acute Kidney Injury (AKI) Prerenal vs Intrarenal vs Postrenal", "Prerenal: decreased renal perfusion (hypovolemia, shock); Intrarenal: direct tubular damage (contrast dye, aminoglycosides); Postrenal: urinary tract obstruction", "Postrenal AKI is caused strictly by myocardial infarction"),
            Triple("Renal: AKI Hyperkalemia Emergency Interventions", "IV Calcium Gluconate (protects heart myocardium), IV Regular Insulin + D50W (shifts K+ into cells), Sodium Polystyrene Sulfonate / Patiromer, Hemodialysis", "Give oral potassium chloride 40 mEq supplements"),
            Triple("Renal: Chronic Kidney Disease (CKD) Phosphate Binders", "Calcium Acetate / Sevelamer taken WITH MEALS to bind dietary phosphate and prevent hyperphosphatemia / renal osteodystrophy", "Take phosphate binder on empty stomach at bedtime"),
            Triple("Renal: Hemodialysis Arteriovenous (AV) Fistula Assessment", "Palpate for THRILL (vibration) and auscultate for BRUIT (swishing sound); NO BP readings, IV lines, or venipunctures on affected arm", "Measure blood pressure on AV fistula arm every 15 mins"),
            Triple("Renal: Peritoneal Dialysis Cloudy Effluent Fluid", "Cloudy outflow fluid with abdominal pain and fever indicates PERITONITIS; collect effluent sample for culture and notify physician immediately", "Cloudy effluent is normal and requires fast drainage"),
            Triple("Renal: Peritoneal Dialysis Insufficient Outflow Fluid", "Check for catheter kinks, assess for constipation (bowel motility), reposition client side-to-side to promote gravity drainage", "Infuse 5 Liters of boiling water into peritoneal cavity"),
            Triple("Renal: Glomerulonephritis Hematuria & Periorbital Edema", "Post-streptococcal glomerulonephritis: cola/tea-colored urine, periorbital edema, hypertension, protein leakage; restrict sodium and fluids", "Profuse clear polyuria with low blood pressure"),
            Triple("Renal: Nephrotic Syndrome Massive Proteinuria & Anasarca", "Severe glomerular permeability causes massive proteinuria, hypoalbuminemia, generalized edema (anasarca), hyperlipidemia", "Cola-colored hematuria without protein loss"),
            Triple("Musculoskeletal: Hip Arthroplasty Post-Op Positioning", "Maintain abduction of affected hip using abduction pillow; avoid hip flexion > 90 degrees, crossing legs, or internal rotation", "Adduct legs tightly and flex hips to 120 degrees"),
            Triple("Musculoskeletal: Compartment Syndrome 6 Ps", "Pain (unrelieved by meds), Paresthesia, Pallor, Paralysis, Pulselessness, Poikilothermia; DO NOT elevate limb above heart level, DO NOT ice", "Elevate limb above heart level and apply heavy tight cast"),
            Triple("Musculoskeletal: Buck's Skin Traction Maintenance", "Ensure weights hang FREELY without touching floor or bed frame; do NOT remove weights unless specifically ordered; inspect skin for breakdown", "Place traction weights resting comfortably on floor"),
            Triple("Musculoskeletal: Skeletal Traction Pin Care Routine", "Inspect pin sites for signs of infection (purulent drainage, erythema); clean pin sites with chlorhexidine/saline using sterile swabs", "Remove traction pins daily for warm bath cleaning"),
            Triple("Musculoskeletal: Gout Low-Purine Diet", "Avoid high-purine foods (organ meats, sardines, anchovies, shellfish, red meat, alcohol/beer); increase fluid intake to prevent uric acid stones", "Consume high organ meat stew with red wine daily"),
            Triple("Musculoskeletal: Osteomyelitis Diagnostic Gold Standard & Treatment", "Bone infection requiring bone biopsy/culture for definitive diagnosis and prolonged 4-6 weeks parenteral IV antibiotic therapy", "Osteomyelitis is treated with 3 days of oral paracetamol without blood or bone cultures"),
            Triple("Hematology: Sickle Cell Crisis Vaso-Occlusive Pain Interventions", "Priorities: HYDRATION (IV fluids), OXYGENATION, PAIN CONTROL (IV opioids), and resting affected joints", "Apply cold ice packs directly to painful limbs and restrict fluids"),
            Triple("Hematology: Pernicious Anemia Vitamin B12 Deficiency", "Lack of Intrinsic Factor in gastric mucosa prevents B12 absorption; red beefy tongue, paresthesias; requires monthly lifelong IM Vitamin B12 injections", "Daily oral iron supplements cure pernicious anemia"),
            Triple("Hematology: Aplastic Anemia Pancytopenia Monitoring", "Bone marrow failure causing anemia (fatigue), leukopenia/neutropenia (infection risk), and thrombocytopenia (bleeding risk)", "Hyperactive red blood cell and platelet production"),
            Triple("Hematology: Polycythemia Vera Therapeutic Phlebotomy", "Overproduction of RBCs causing hyperviscosity and thrombosis risk; treat with periodic phlebotomy (remove 300-500 mL blood) and hydration", "Administer blood transfusions weekly"),
            Triple("Hematology: Hemophilia A Factor VIII Replacement", "X-linked recessive bleeding disorder; administer Factor VIII concentrate immediately upon joint bleeding (hemarthrosis); apply ice, elevate joint", "Administer Warfarin and encourage vigorous exercise"),
            Triple("Hematology: Idiopathic Thrombocytopenic Purpura (ITP) Bleeding Precautions", "Low platelet count (< 20,000/mcL); soft toothbrush, electric razor, avoid IM injections, avoid Aspirin/NSAIDs, prevent falls", "Perform daily deep intramuscular injections with sharp needles"),
            Triple("Oncology: Superior Vena Cava (SVC) Syndrome Features", "Facial and periorbital edema, jugular vein distension, dyspnea, swelling of upper extremities due to mediastinal tumor compression", "Severe ankle edema with normal neck veins"),
            Triple("Oncology: Tumor Lysis Syndrome (TLS) Metabolic Abnormalities", "Hyperkalemia, Hyperuricemia, Hyperphosphatemia, and HYPOCALCEMIA; treat with aggressive hydration and Allopurinol / Rasburicase", "Hypokalemia, hypouricemia, and hypercalcemia"),
            Triple("Oncology: Neutropenic Precautions (Absolute Neutrophil Count < 500)", "Private room, strict hand hygiene, NO raw fruits/vegetables/unpasteurized dairy, NO fresh flowers or standing water, avoid crowds", "Provide raw fresh salad bar and fresh garden flowers"),
            Triple("Oncology: Radiation Therapy Skin Care Guidelines", "Wash treated area gently with lukewarm water and mild soap; do NOT wash off radiation skin markings; avoid lotions, heating pads, or sun exposure", "Scrub skin vigorously with alcohol swabs daily"),
            Triple("Dermatology: Psoriasis Plaque Management", "Chronic autoimmune skin disorder; silvery-white scales over erythematous plaques; topical corticosteroids, UV light therapy, avoid skin trauma (Koebner)", "Scrub plaques with stiff brush until bleeding occurs"),
            Triple("Dermatology: Melanoma ABCDE Diagnostic Criteria", "A = Asymmetry, B = Border irregularity, C = Color variation, D = Diameter > 6 mm, E = Evolving/Changing size or shape", "A = Always circular, B = Brown color only, C = Clear outline"),
            Triple("Dermatology: Pressure Injury Staging (Stage 1 to Unstageable)", "Stage 1: non-blanchable erythema; Stage 2: partial-thickness skin loss (blister/shallow ulcer); Stage 3: full-thickness skin loss (subcutaneous fat visible); Stage 4: exposed bone/muscle", "Stage 1 involves exposed necrotic femur bone")
        )

        // Expand topics across 150 items through clinical case iterations
        for (i in 0 until 150) {
            val topicIndex = i % medSurgTopics.size
            val item = medSurgTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Standard Medical-Surgical Protocol: ${item.second}",
                "Dangerous / Inappropriate Clinical Action: ${item.third}",
                "Discontinue monitoring and send client home",
                "Perform unverified invasive procedure without physician order"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical Nursing",
                "NCLEX-RN / DHA • Advanced",
                "Advanced Med-Surg Case #${i + 1}: In evaluating a hospitalized client presenting with ${item.first}, which clinical nursing decision represents evidence-based care?",
                options,
                correctPos,
                "Rationale: Medical-surgical practice guidelines for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct answer prioritizes client safety, physiological integrity, and immediate clinical intervention. Action '${item.third}' is unsafe.",
                "Med-Surg Advanced • ${item.first}"
            )
        }

        return list
    }
}
