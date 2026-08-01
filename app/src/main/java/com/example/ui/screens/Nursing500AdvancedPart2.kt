package com.example.ui.screens

/**
 * ADVANCED BANK PART 2: PHARMACOLOGY & MEDICATION SAFETY (80 MCQs) + FUNDAMENTALS OF NURSING & SKILLS (60 MCQs)
 * NCLEX-RN, DHA, HAAD, Prometric, PNC & International Competitive Exam Standard.
 */
object Nursing500AdvancedPart2 {

    fun getPharmAndFundamentalsAdvancedQuestions(startId: Int): List<NursingExamQuestion> {
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

        // =========================================================================
        // PHARMACOLOGY & MEDICATION SAFETY (80 QUESTIONS)
        // =========================================================================
        val pharmTopics = listOf(
            Triple("Digoxin Cardiac Glycoside Toxicity", "Therapeutic range 0.5-2.0 ng/mL; signs of toxicity include anorexia, nausea, visual disturbances (yellow-green halos), bradycardia; check apical pulse for 1 full minute (hold if < 60 bpm)", "Give double dose if pulse is 40 bpm"),
            Triple("Digoxin Toxicity Precipitating Factors", "HYPOKALEMIA (< 3.5 mEq/L) significantly increases the risk of Digoxin toxicity; monitor potassium levels closely in clients on loop diuretics (Furosemide)", "Hyperkalemia decreases digoxin action to zero"),
            Triple("Digoxin Toxicity Antidote", "Digoxin Immune Fab (Digibind) is administered for severe life-threatening digoxin toxicity with arrhythmias or hyperkalemia", "Administer high-dose IV Potassium Chloride bolus"),
            Triple("Warfarin (Coumadin) Monitoring & Target INR", "Monitor Prothrombin Time (PT) / International Normalized Ratio (INR); target INR is 2.0 to 3.0 (2.5 to 3.5 for mechanical heart valves); Antidote is VITAMIN K (Phytonadione)", "Target INR is 10.0 to 15.0; Antidote is Protamine Sulfate"),
            Triple("Unfractionated Heparin Monitoring & Antidote", "Monitor activated Partial Thromboplastin Time (aPTT); target aPTT is 1.5 to 2.5 times baseline (approx 60-80 seconds); Antidote is PROTAMINE SULFATE", "Monitor PT/INR; Antidote is Vitamin K"),
            Triple("Low-Molecular-Weight Heparin (Enoxaparin / Lovenox)", "Administer subcutaneously in anterolateral or posterolateral abdominal wall ('love handles'); do NOT expel air bubble from prefilled syringe, do NOT rub injection site", "Expel air bubble and rub site vigorously"),
            Triple("Direct Oral Anticoagulants (DOACs - Rivaroxaban / Apixaban)", "Factor Xa inhibitors; do NOT require routine INR/aPTT monitoring; educate client on signs of occult bleeding; Antidote for Factor Xa inhibitors is Andexanet alfa", "Requires daily INR draws and high Vitamin K intake"),
            Triple("Beta-Blockers (Metoprolol / Atenolol) Precautions", "Monitor blood pressure and HR; hold if HR < 60 bpm or SBP < 100 mmHg; non-selective beta-blockers (Propranolol) CONTRAINDICATED in asthma/COPD due to bronchospasm risk", "Administer Propranolol during acute severe asthma attack"),
            Triple("ACE Inhibitors (Lisinopril / Enalapril) Adverse Effects", "Dry persistent cough (due to bradykinin accumulation), hyperkalemia, angioedema (swelling of lips/tongue/airway - emergency); switch to ARB (Losartan) if cough occurs", "Causes severe hypokalemia and diarrhea"),
            Triple("Calcium Channel Blockers (Amlodipine / Diltiazem)", "Causes peripheral edema, dizziness, constipation, and bradycardia; instruct client to avoid GRAPEFRUIT JUICE (inhibits metabolism, increases toxicity)", "Drink 2 Liters grapefruit juice with each dose"),
            Triple("Nitroglycerin Sublingual Administration Protocol", "Take 1 tablet sublingually at onset of chest pain; if pain unimproved after 5 minutes, call 911 and take a 2nd tablet; maximum 3 tablets in 15 minutes; store in original dark glass bottle", "Swallow 5 tablets at once with hot coffee"),
            Triple("Transdermal Nitroglycerin Patch Guidelines", "Apply patch once daily to hairless skin site; remove patch at night for 10-12 hours to prevent NITROGLYCERIN TOLERANCE; wear gloves when applying", "Leave patch on same spot for 30 consecutive days"),
            Triple("Statins (Atorvastatin / Simvastatin) Side Effects", "Monitor liver enzymes and instruct client to report unexplained MUSCLE PAIN or weakness immediately (risk of Rhabdomyolysis); take at bedtime", "Causes severe hyperlipidemia and hair growth"),
            Triple("Amiodarone Antiarrhythmic Monitoring", "Adverse effects include PULMONARY TOXICITY (cough, dyspnea), thyroid dysfunction (hypo/hyperthyroidism), corneal microdeposits, and blue-gray skin discoloration", "Causes instant cure with zero side effects"),
            Triple("Insulin Onset, Peak & Duration (Rapid vs Short vs NPH vs Long)", "Rapid (Lispro/Aspart): peak 0.5-1.5 hr; Short (Regular): peak 2-3 hr (ONLY insulin given IV); NPH: peak 4-12 hr (cloudy); Long (Glargine/Detemir): NO PEAK (do NOT mix)", "Mix Glargine with Regular insulin in same syringe"),
            Triple("InsulinSyringe Mixing Technique (Clear before Cloudy)", "Draw UP REGULAR (clear) insulin BEFORE NPH (cloudy) insulin to prevent contaminating short-acting insulin vial with NPH", "Draw NPH cloudy insulin first then inject into Regular vial"),
            Triple("Metformin (Glucophage) Lactic Acidosis & Contrast Dye", "HOLD Metformin 48 hours BEFORE and AFTER intravenous radiocontrast media procedures to prevent acute renal failure and severe lactic acidosis", "Double Metformin dose immediately before CT contrast scan"),
            Triple("Sulfonylureas (Glipizide / Glyburide) Risk", "Stimulates pancreatic insulin secretion; high risk of severe HYPOGLYCEMIA, especially in elderly clients or those skipping meals", "Causes severe ketoacidosis and hyperglycemia"),
            Triple("Levothyroxine (Synthroid) Administration Guidelines", "Take ONCE DAILY in the MORNING on an EMPTY STOMACH with a full glass of water, 30-60 minutes before breakfast; lifelong therapy", "Take at bedtime with heavy calcium-rich milk shake"),
            Triple("Corticosteroids (Prednisone / Dexamethasone) Tapering", "MUST taper dose gradually to prevent acute ADRENAL CRISIS; side effects include hyperglycemia, fluid retention, peptic ulcer, infection risk, osteoporosis", "Stop 60 mg Prednisone abruptly after 6 months of daily use"),
            Triple("Inhaled Corticosteroids (Fluticasone) Oral Candidiasis Prevention", "Instruct client to RINSE MOUTH WITH WATER and spit after each inhalation to prevent oral thrush (candidiasis) and hoarseness", "Swallow rinse water and eat sweet chocolate"),
            Triple("Short-Acting Beta-Agonists (Albuterol) Rescue Inhaler", "Rescue bronchodilator for acute asthma exacerbation; side effects include tachycardia, tremors, palpitations, and hypokalemia; use Albuterol FIRST before inhaled corticosteroid", "Use inhaled corticosteroid first during acute respiratory arrest"),
            Triple("Theophylline Bronchodilator Therapeutic Range", "Therapeutic range 10-20 mcg/mL; toxicity (> 20 mcg/mL) causes severe arrhythmias, seizures, nausea, vomiting; avoid caffeine", "Therapeutic range 100-200 mcg/mL"),
            Triple("Gentamicin / Tobramycin Aminoglycoside Toxicity", "OTOTOXICITY (tinnitus, hearing loss, vertigo) and NEPHROTOXICITY (elevated BUN/creatinine); monitor serum PEAK and TROUGH levels", "Causes acute hypokalemia and tooth discoloration"),
            Triple("Vancomycin Red Man Syndrome vs Anaphylaxis", "Red Man Syndrome: flushing/erythema of face/neck caused by RAPID IV INFUSION; slow infusion rate to over 60-90 minutes; Antihistamines help", "Red Man Syndrome requires immediate CPR and intubation"),
            Triple("Vancomycin Therapeutic Trough Monitoring", "Measure TROUGH level immediately before (15-30 mins) the next scheduled dose; target trough 10-20 mcg/mL depending on infection severity", "Measure trough level 5 minutes after IV infusion ends"),
            Triple("Tetracycline Administration Contraindications", "Do NOT give to pregnant women or children < 8 years (causes permanent TOOTH DISCOLORATION and bone growth retardation); take on empty stomach, avoid dairy/antacids", "Take with large glass of whole milk and antacids"),
            Triple("Fluoroquinolones (Ciprofloxacin / Levofloxacin) Tendon Rupture", "Black box warning for TENDONITIS and TENDON RUPTURE (especially Achilles tendon); instruct client to report tendon pain immediately and avoid exercise", "Encourage heavy weightlifting and marathon running"),
            Triple("Metronidazole (Flagyl) Disulfiram-Like Reaction", "STRICTLY AVOID ALCOHOL during therapy and for 48 hours after completion; combination causes severe nausea, vomiting, flushing, tachycardia", "Consume 3 glasses of wine with each Flagyl dose"),
            Triple("Isoniazid (INH) Tuberculosis Pyridoxine (Vit B6) Supplement", "INH can cause PERIPHERAL NEUROPATHY (numbness/tingling); co-administer PYRIDOXINE (Vitamin B6) to prevent nerve damage; monitor liver enzymes", "Administer Vitamin C to prevent ototoxicity"),
            Triple("Rifampin Tuberculosis Side Effects", "Causes benign ORANGE-RED DISCOLORATION of bodily fluids (urine, sweat, tears, saliva); warn client that soft contact lenses may be permanently stained", "Orange urine indicates severe hyperacute renal failure"),
            Triple("Phenytoin (Dilantin) Therapeutic Range & Side Effects", "Therapeutic range 10-20 mcg/mL; adverse effects include GINGIVAL HYPERPLASIA (frequent dental hygiene required), nystagmus, ataxia; tube feeds decrease absorption", "Therapeutic range 80-100 mcg/mL"),
            Triple("Carbamazepine (Tegretol) Blood Dyscrasias", "Causes agranulocytosis and aplastic anemia; instruct client to report fever, sore throat, or unusual bruising/bleeding immediately", "Causes massive weight gain and hyperglycemia"),
            Triple("Valproic Acid (Depakote) Organ Toxicity", "Monitor LIVER FUNCTION TESTS (hepatotoxicity) and PLATELET COUNTS (thrombocytopenia); black box warning for pancreatitis", "Causes total kidney destruction in 5 minutes"),
            Triple("Lithium Carbonate Therapeutic Range & Toxicity", "Therapeutic range 0.6-1.2 mEq/L; toxicity (> 1.5 mEq/L) causes tremor, ataxia, confusion, seizures; MAINTAIN SODIUM AND FLUID INTAKE (hyponatremia causes lithium toxicity)", "Strict zero-sodium diet with fluid restriction"),
            Triple("Selective Serotonin Reuptake Inhibitors (SSRIs) Serotonin Syndrome", "Agitation, confusion, hyperreflexia, clonus, tremors, hyperthermia, diaphoresis; occurs when combining SSRIs with MAOIs or St. John's Wort", "Causes severe bradycardia and hypothermia"),
            Triple("Monoamine Oxidase Inhibitors (MAOIs) Tyramine Reaction", "Avoid TYRAMINE-RICH FOODS (aged cheese, cured meats, red wine, draft beer, fermented soy); causes life-threatening HYPERTENSIVE CRISIS", "Eat aged parmesan cheese and red wine daily"),
            Triple("Typical Antipsychotics (Haloperidol) Extrapyramidal Side Effects (EPS)", "Acute dystonia (muscle spasms), akathisia (restlessness), parkinsonism; treat with BENZTROPINE (Cogentin) or Diphenhydramine IV/IM", "Treat EPS with high-dose haloperidol bolus"),
            Triple("Neuroleptic Malignant Syndrome (NMS) vs Serotonin Syndrome", "NMS: high fever, severe lead-pipe MUSCLE RIGIDITY, autonomic instability, elevated CK; stop antipsychotic immediately and administer DANTROLENE or Bromocriptine", "NMS causes hyperreflexia and diarrhea treated with aspirin"),
            Triple("Atypical Antipsychotics (Clozapine) Agranulocytosis", "Clozapine carries black box warning for AGRANULOCYTOSIS; mandatory baseline and ongoing absolute neutrophil count (ANC) monitoring", "Requires daily serum troponin draws"),
            Triple("Opioid Analgesics (Morphine) Respiratory Depression", "Monitor respiratory rate; hold dose if RR < 12 breaths/min; Antidote is NALOXONE (Narcan) IV push; monitor for re-sedation as Naloxone duration is shorter than Morphine", "Antidote is Flumazenil IV push"),
            Triple("Benzodiazepine (Diazepam / Lorazepam) Antidote", "Antidote is FLUMAZENIL IV; monitor for withdrawal seizures in chronic benzodiazepine users following Flumazenil administration", "Antidote is Naloxone IV push"),
            Triple("Acetaminophen (Tylenol) Toxicity & Antidote", "Maximum daily dose 4000 mg (3000 mg in elderly/liver disease); causes severe HEPATOTOXICITY; Antidote is N-ACETYLCYSTEINE (Mucomyst)", "Antidote is Vitamin K IV push"),
            Triple("Aspirin Toxicity Salicylism Signs", "Tinnitus (ringing in ears), hyperventilation (metabolic acidosis / respiratory alkalosis), GI bleeding, hyperthermia", "Causes hyperkalemia and acute vision loss"),
            Triple("High-Alert Medications ISMP Safeguards", "High-alert meds (Insulin, Heparin, Potassium Chloride concentrate, Chemotherapy, Opioids) require INDEPENDENT DOUBLE-CHECK by two licensed nurses", "Delegate double-check to nursing assistant"),
            Triple("Medication Administration 6 Rights", "Right Client, Right Medication, Right Dose, Right Route, Right Time, Right Documentation", "Right Speed, Right Price, Right Guess, Right Store")
        )

        for (i in 0 until 80) {
            val topicIndex = i % pharmTopics.size
            val item = pharmTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Evidence-Based Pharmacology Standard: ${item.second}",
                "Dangerous / Contraindicated Medication Practice: ${item.third}",
                "Omit documentation and double dose next shift",
                "Administer medication without checking physician order"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pharmacology and Medication Safety",
                "NCLEX-RN / DHA • Advanced",
                "Advanced Pharmacology Case #${i + 1}: In safely administering pharmacological therapy involving ${item.first}, which clinical action is correct?",
                options,
                correctPos,
                "Rationale: Medication safety and clinical pharmacology standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice prevents drug toxicity, adverse drug events, and maintains therapeutic efficacy. Action '${item.third}' is unsafe.",
                "Pharmacology Advanced • ${item.first}"
            )
        }

        // =========================================================================
        // FUNDAMENTALS OF NURSING & SKILLS (60 QUESTIONS)
        // =========================================================================
        val fundamentalsTopics = listOf(
            Triple("Peripheral Intravenous Line Infiltration vs Phlebitis", "Infiltration: cool, pale, swollen tissue around IV site; Phlebitis: warm, erythematous, tender vein with red streak; stop infusion immediately for both", "Infiltration causes red hot skin with purulent drainage"),
            Triple("Extravasation of Vesicant Drugs Action", "STOP INFUSION IMMEDIATELY, disconnect tubing, attempt to aspirate residual drug with syringe, check specific antidote protocol, elevate extremity", "Increase infusion rate to flush drug into tissue"),
            Triple("Blood Transfusion Reaction Initial Action", "STOP THE TRANSFUSION IMMEDIATELY, maintain IV access with 0.9% Normal Saline using NEW tubing, notify provider and blood bank, return blood bag", "Slow infusion rate and give oral aspirin"),
            Triple("Central Venous Catheter Dressing Change Technique", "Sterile technique; client and nurse wear MASKS; client turns head away; clean site with Chlorhexidine using friction; apply transparent occlusive dressing", "Clean site with tap water and unsterile paper towel"),
            Triple("Central Venous Catheter Air Embolism Positioning", "Place client in LEFT LATERAL TRENDELENBURG position (traps air in right ventricle preventing pulmonary artery occlusion) and administer oxygen", "Place client upright in High Fowler's position"),
            Triple("Enteral Feeding Tube Residual Volume Protocol", "Check Gastric Residual Volume (GRV) before each feeding; if GRV > 250-500 mL, hold feeding, re-infuse residual, and notify provider (prevents aspiration)", "Discard 500 mL residual into trash and flush with iced coffee"),
            Triple("Enteral Feeding Tube Placement Verification", "CONFIRM TUBE LOCATION VIA X-RAY before first feeding/medication administration; pH testing of gastric aspirate (pH < 5.5) for ongoing checks", "Inject 50 mL air and listen with stethoscope over ankle"),
            Triple("Total Parenteral Nutrition (TPN) Discontinuation Sudden Hypoglycemia", "If TPN bag empties before next bag arrives, infuse 10% DEXTROSE IN WATER (D10W) at same rate to prevent sudden severe hypoglycemia", "Infuse 0.9% Normal Saline at high speed"),
            Triple("Fluid & Electrolytes: Hyponatremia Neurological Signs", "Serum sodium < 135 mEq/L; cellular swelling causes confusion, seizures, headache, muscle cramps; institute seizure precautions", "Causes hyperactive reflexes and hypertension"),
            Triple("Fluid & Electrolytes: Hyperkalemia ECG Changes", "Peaked T waves, widened QRS complex, prolonged PR interval; severe risk of fatal ventricular fibrillation; treat with IV Calcium and Insulin", "Inverted U waves and ST segment elevation"),
            Triple("Fluid & Electrolytes: Hypocalcemia Trousseau's & Chvostek's Signs", "Trousseau's sign (carpal spasm with BP cuff inflation) and Chvostek's sign (facial twitching with facial nerve tapping); serum Ca < 8.5 mg/dL", "Hyperactive calcium causes loss of bone density"),
            Triple("Fluid & Electrolytes: Hypomagnesemia Torsades de Pointes", "Serum magnesium < 1.5 mEq/L; causes neuromuscular hyperexcitability, hyperreflexia, and lethal Torsades de Pointes arrhythmia; treat with IV Magnesium Sulfate", "Serum Mg < 1.5 causes severe constipation"),
            Triple("Acid-Base Imbalances: Respiratory Acidosis Causes & ABG", "Uncompensated: pH < 7.35, PaCO2 > 45 mmHg, HCO3 normal; caused by hypoventilation (COPD, opioid overdose, chest trauma); improve ventilation", "pH > 7.45, PaCO2 < 35 mmHg caused by hyperventilation"),
            Triple("Acid-Base Imbalances: Metabolic Acidosis Kussmaul Respirations", "Uncompensated: pH < 7.35, PaCO2 normal, HCO3 < 22 mEq/L; caused by DKA, renal failure, severe diarrhea; deep rapid Kussmaul breathing compensates", "Shallow slow breathing with pH 7.55"),
            Triple("Acid-Base Imbalances: Metabolic Alkalosis Causes", "pH > 7.45, HCO3 > 26 mEq/L; caused by severe vomiting, prolonged nasogastric suctioning, or excessive antacid ingestion", "Caused by profuse watery diarrhea"),
            Triple("Preoperative Informed Consent Nursing Role", "Nurse WITNESSES client signature, verifies client competence, and confirms client understood explanation; surgeon is responsible for explaining procedure", "Nurse explains surgical risks and obtains consent"),
            Triple("Preoperative Malignant Hyperthermia Family History", "Screen client for personal or family history of unexplained fever or death during anesthesia; succinylcholine and volatile anesthetics trigger MH", "Malignant hyperthermia is caused by oral acetaminophen"),
            Triple("Postoperative Wound Evisceration Action", "Cover exposed abdominal viscera with STERILE GAUZE MOISTENED WITH STERILE NORMAL SALINE; place client in Low Fowler's with knees flexed; notify surgeon", "Push protruding intestines back into abdomen with bare hands"),
            Triple("Surgical Hand Scrub Standard", "Scrub hands and forearms for 3-5 minutes with approved antimicrobial agent; hold hands HIGHER than elbows to allow water to flow off elbows", "Hold hands lower than waist allowing water to drip off fingers"),
            Triple("Sterile Field Rules & Boundaries", "Only sterile items placed on field; 1-inch border is considered UNSTERILE; items below waist level or out of sight are considered UNSTERILE", "Touch sterile field with unsterile gloved hand"),
            Triple("Nasal Cannula vs Non-Rebreather Oxygen Delivery", "Nasal Cannula: 1-6 L/min (24-44% O2); Non-Rebreather Mask: 10-15 L/min (80-95% O2) with reservoir bag inflated 2/3 full prior to placement", "Nasal cannula delivers 100% oxygen at 20 L/min"),
            Triple("Tracheostomy Suctioning Technique", "Hyperoxygenate 100% O2 before suctioning; apply intermittent suction while withdrawing tube; limit suction pass to 10-15 SECONDS max; sterile technique", "Suction continuously for 3 minutes while inserting tube"),
            Triple("Urinary Catheterization Insertion Technique", "Sterile technique; inflate balloon ONLY after observing urine return in tubing and advancing catheter an additional 1-2 inches", "Inflate balloon in urethra before entering bladder"),
            Triple("Infection Control Isolation Precautions Summary", "Airborne: N95 mask, negative pressure room (TB, Measles, Varicella); Droplet: Surgical mask (Influenza, Pertussis, Meningitis); Contact: Gown/gloves (C. diff, MRSA)", "Airborne precautions require cloth mask only"),
            Triple("Fall Prevention Guidelines", "Keep bed in lowest position with wheels locked, call light within reach, non-slip footwear, clear pathway, perform Morse fall risk assessment", "Leave bed elevated in highest position with wet floor"),
            Triple("Physical Restraints Safety Standards", "Requires provider order within 1 hour; renew order every 24 hours; check quick-release knot tied to bed frame (NOT side rail); assess neurovascular every 2 hours", "Tie tight knot to side rail and leave unmonitored for 24 hours")
        )

        for (i in 0 until 60) {
            val topicIndex = i % fundamentalsTopics.size
            val item = fundamentalsTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Delay procedure and delegate to unauthorized person",
                "Omit standard infection prevention measures"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Fundamentals of Nursing",
                "NCLEX-RN / DHA • Advanced",
                "Advanced Nursing Skills Case #${i + 1}: In executing a fundamental clinical nursing skill involving ${item.first}, which evidence-based protocol is required?",
                options,
                correctPos,
                "Rationale: Fundamental nursing practice standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct answer maintains sterile technique, client safety, and optimal physiological outcomes. Action '${item.third}' is unsafe.",
                "Fundamentals Advanced • ${item.first}"
            )
        }

        return list
    }
}
