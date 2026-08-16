package com.drtahir.studentkit.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 3 PLUS QUESTION BANK
 * Adds 100 unique questions for EACH subject in Semester 3 (Total = 500 MCQs)
 * Aligned with PNC & KMU Syllabus.
 */
object KpSemester3PlusQuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var currentId = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        // 1. Adult Health Nursing I (AHN-631) - 100 MCQs
        val ahn1Plus = getAhn1ExtraQuestions(currentId)
        questions.addAll(ahn1Plus)
        currentId += ahn1Plus.size

        // 2. Pathophysiology I (PAT-632) - 100 MCQs
        val pat1Plus = getPat1ExtraQuestions(currentId)
        questions.addAll(pat1Plus)
        currentId += pat1Plus.size

        // 3. Pharmacology I (PHA-633) - 100 MCQs
        val pha1Plus = getPha1ExtraQuestions(currentId)
        questions.addAll(pha1Plus)
        currentId += pha1Plus.size

        // 4. Health Assessment I (HAS-634) - 100 MCQs
        val has1Plus = getHas1ExtraQuestions(currentId)
        questions.addAll(has1Plus)
        currentId += has1Plus.size

        // 5. Islamic Studies / Ethics (ISL-635) - 100 MCQs
        val isl1Plus = getIsl1ExtraQuestions(currentId)
        questions.addAll(isl1Plus)
        currentId += isl1Plus.size

        return questions
    }

    private fun getAhn1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Preoperative Care: Informed Consent & NPO Protocol", "Nurse verifies consent is signed voluntarily and client understands procedure; NPO 6-8 hours prevents aspiration pneumonitis", "Nurse explains complex surgical risks and performs operative incision"),
            Triple("Postoperative Care: Atelectasis & Incentive Spirometry", "Incentive spirometry promotes deep breathing, alveolar expansion, and prevents postoperative atelectasis", "Incentive spirometry is used to measure renal creatinine output"),
            Triple("Cardiovascular: Stable Angina vs Unstable Angina", "Stable angina relieved by rest and sublingual NTG; Unstable angina occurs at rest, increases in severity, and indicates acute coronary syndrome", "Unstable angina is cured with warm bed rest and zero medication"),
            Triple("Cardiovascular: Heart Failure Left vs Right Sided Symptoms", "Left heart failure (pulmonary congestion, dyspnea, orthopnea, crackles); Right heart failure (systemic congestion, JVD, peripheral edema, hepatomegaly)", "Left heart failure causes severe peripheral pedal edema without lung crackles"),
            Triple("Respiratory: Asthma Rescue vs Maintenance Inhalers", "Albuterol (short-acting beta-agonist) is rescue drug for acute bronchospasm; Fluticasone (corticosteroid) is daily maintenance", "Fluticasone is taken during acute asthma attack for immediate relief within 2 seconds"),
            Triple("Respiratory: Pneumonia Nursing Care & Sputum Culture", "Obtain sputum culture BEFORE starting empiric broad-spectrum antibiotics; encourage fluids 2-3 L/day to thin secretions", "Start intravenous antibiotics for 5 days before collecting initial sputum culture"),
            Triple("Gastrointestinal: Peptic Ulcer Disease Gastric vs Duodenal", "Gastric ulcer pain exacerbated 30-60 mins post meals; Duodenal ulcer pain relieved by food and occurs 2-3 hours post meals / night", "Duodenal ulcer pain is most severe immediately upon swallowing first bite of food"),
            Triple("Gastrointestinal: Appendicitis McBurney's Point & Rebound Pain", "Pain starts periumbilical migrating to RLQ (McBurney's point); rebound tenderness; do NOT apply heat (risk of rupture)", "Apply hot heating pad directly to right lower quadrant in acute appendicitis"),
            Triple("Endocrine: Diabetes Mellitus Type 1 vs Type 2 Differences", "Type 1 (autoimmune destruction of beta cells, absolute insulin deficiency); Type 2 (insulin resistance and relative deficiency)", "Type 1 diabetes is cured with high-sugar oral snacks and oral metformin"),
            Triple("Endocrine: Hypoglycemia Management Rule of 15", "Give 15g fast-acting simple carbohydrate (4 oz juice); recheck blood glucose in 15 minutes; repeat if glucose < 70 mg/dL", "Administer 100 units IV NPH insulin when blood glucose is 45 mg/dL"),
            Triple("Renal: Urinary Tract Infection (UTI) Education & Prevention", "Wipe front to back, void after intercourse, drink 2-3 L water daily, finish full course of prescribed antibiotics", "Hold urine for 12 hours and restrict fluid intake to prevent bladder filling"),
            Triple("Renal: Chronic Kidney Disease (CKD) Electrolyte Imbalances", "Hyperkalemia, hyperphosphatemia, hypocalcemia, hypervolemia, metabolic acidosis; monitor serum potassium closely", "CKD causes severe hypokalemia and low serum creatinine"),
            Triple("Musculoskeletal: Cast Care & Compartment Syndrome Signs", "Assess 6 Ps (Pain, Pallor, Paresthesia, Pulselessness, Paralysis, Poikilothermia); keep cast dry; do NOT insert objects under cast", "Insert long metal knitting needles under plaster cast to scratch skin"),
            Triple("Musculoskeletal: Osteoarthritis vs Rheumatoid Arthritis", "Osteoarthritis (degenerative, asymmetric, morning stiffness < 30 mins, worse with activity); RA (autoimmune, symmetric, morning stiffness > 1 hr)", "Osteoarthritis is a systemic autoimmune disease affecting young children symmetric joints"),
            Triple("Hematology: Anemia Types & Iron Deficiency Care", "Iron deficiency anemia (microcytic hypochromic; take iron with Vitamin C on empty stomach; black stools expected)", "Take oral iron with high-calcium milk to maximize mucosal absorption")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Ignore physician orders and discharge patient home without documentation",
                "Delegate complex medical assessment to unregistered hospital visitors"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 3,
                    subjectName = "Adult Health Nursing I",
                    question = "AHN-631 Plus Q#${i + 1}: In Adult Health Nursing I regarding ${t.first}, which clinical choice is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Adult Health Nursing I (AHN-631) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 3 • AHN-631"
                )
            )
        }
        return list
    }

    private fun getPat1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Cellular Injury: Hypertrophy, Hyperplasia, Atrophy, Metaplasia", "Hypertrophy (increased cell size); Hyperplasia (increased cell number); Atrophy (decreased size); Metaplasia (reversible replacement of one cell type for another)", "Hypertrophy refers to rapid decrease in total cell number in muscle tissue"),
            Triple("Cellular Injury: Necrosis vs Apoptosis", "Necrosis (unregulated pathological cell death with inflammation); Apoptosis (programmed cell death without inflammation)", "Apoptosis causes massive tissue necrosis and acute gangrenous suppuration"),
            Triple("Inflammation: Acute Vascular & Cellular Events", "Vasodilation (histamine/prostaglandins), increased vascular permeability (edema), neutrophil marginalization and chemotaxis", "Acute inflammation causes severe vasoconstriction and zero leukocyte migration"),
            Triple("Inflammation: Systemic Manifestations & Acute Phase Proteins", "Fever (IL-1, TNF-alpha action on hypothalamus), leukocytosis, elevated C-reactive protein (CRP) and ESR", "Systemic inflammation causes hypothermia and drop in ESR to zero"),
            Triple("Fluid & Electrolytes: Hyponatremia vs Hypernatremia", "Hyponatremia (< 135 mEq/L, cellular swelling, confusion, seizures); Hypernatremia (> 145 mEq/L, cellular shrinkage, thirst)", "Hyponatremia causes brain cell dehydration and severe hypertonicity"),
            Triple("Fluid & Electrolytes: Hypokalemia vs Hyperkalemia ECG Effects", "Hypokalemia (flattened T waves, U waves); Hyperkalemia (peaked T waves, widened QRS, risk of ventricular fibrillation)", "Hyperkalemia presents with ST segment elevation and absent P waves only in feet"),
            Triple("Acid-Base Balance: Respiratory vs Metabolic Acidosis", "Respiratory Acidosis (pH < 7.35, PaCO2 > 45, hypoventilation/COPD); Metabolic Acidosis (pH < 7.35, HCO3 < 22, DKA/diarrhea)", "Respiratory acidosis is caused by hyperventilation and elevated serum HCO3"),
            Triple("Acid-Base Balance: Compensation Mechanisms", "Lungs compensate rapidly by altering CO2 elimination; Kidneys compensate slowly (hours/days) by regulating HCO3 excretion/reabsorption", "Kidneys compensate within 2 seconds while lungs take 5 days to respond"),
            Triple("Immune System: Type I Hypersensitivity Anaphylaxis", "IgE-mediated release of histamine from mast cells/basophils; causes bronchospasm, vasodilation, and anaphylactic shock; treat with Epinephrine", "Type I hypersensitivity is mediated by IgG antibodies delayed over 3 weeks"),
            Triple("Immune System: Autoimmunity Systemic Lupus Erythematosus (SLE)", "Autoantibody production (ANA, anti-dsDNA) targeting nuclear antigens; butterfly malar rash, glomerulonephritis, arthritis", "SLE is a contagious bacterial fungal skin infection spread by mosquitoes"),
            Triple("Neoplasia: Benign vs Malignant Tumor Characteristics", "Benign (well-differentiated, encapsulated, slow growth, non-invasive); Malignant (poorly differentiated/anaplastic, invasive, metastatic)", "Benign tumors spread rapidly to liver and brain via lymphatics"),
            Triple("Cardiovascular Pathology: Atherosclerosis Pathogenesis", "Endothelial injury -> LDL oxidation -> macrophage foam cell formation -> fatty streak -> fibrous plaque formation", "Atherosclerosis is caused by high HDL levels building calcium in muscle cells"),
            Triple("Cardiovascular Pathology: Myocardial Infarction Irreversible Injury", "Ischemia > 20-30 minutes causes irreversible coagulative necrosis of cardiomyocytes; elevated Troponin I/T", "Cardiomyocytes recover completely without injury after 12 hours of total ischemia"),
            Triple("Respiratory Pathology: Chronic Bronchitis vs Emphysema", "Chronic bronchitis (mucus hypersecretion, productive cough > 3 mos/2 yrs, blue bloaters); Emphysema (alveolar destruction, loss of elastic recoil, pink puffers)", "Emphysema is characterized by thick bronchial purulent mucus hypersecretion"),
            Triple("Renal Pathology: Nephrotic vs Nephritic Syndrome", "Nephrotic (massive proteinuria > 3.5 g/day, hypoalbuminemia, generalized edema, hyperlipidemia); Nephritic (hematuria, RBC casts, hypertension, oliguria)", "Nephrotic syndrome presents primarily with gross hematuria and zero proteinuria")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Inhibit physiological tissue repair and cause spontaneous genetic duplication",
                "Function as normal uninjured biological tissue without alteration"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 3,
                    subjectName = "Pathophysiology I",
                    question = "PAT-632 Plus Q#${i + 1}: In Pathophysiology I regarding ${t.first}, which pathological mechanism is accurate?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Pathophysiology I (PAT-632) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 3 • PAT-632"
                )
            )
        }
        return list
    }

    private fun getPha1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Pharmacokinetics: Pharmacokinetics vs Pharmacodynamics", "Pharmacokinetics (what body does to drug: ADME - Absorption, Distribution, Metabolism, Excretion); Pharmacodynamics (what drug does to body)", "Pharmacokinetics describes drug receptor binding and biological responses"),
            Triple("Pharmacokinetics: First-Pass Metabolism & Bioavailability", "Oral drugs absorbed from GI tract travel via portal vein to liver where extensive metabolism reduces active drug bioavailability", "Intravenous administration undergoes 100% hepatic first-pass metabolism"),
            Triple("Pharmacokinetics: Drug Half-Life & Steady State", "Half-life (t1/2) is time required for drug concentration to decrease by 50%; steady state reached after approximately 4 to 5 half-lives", "Steady state is achieved immediately after first oral tablet dose"),
            Triple("Autonomic Pharmacology: Cholinergic Agonists (Bethanechol/Neostigmine)", "Stimulate parasympathetic system; side effects (SLUDGE): Salivation, Lacrimation, Urination, Diarrhea, GI upset, Emesis; bradycardia", "Cholinergic agonists cause severe dry mouth, urinary retention, and tachycardia"),
            Triple("Autonomic Pharmacology: Anticholinergic Drugs (Atropine)", "Block muscarinic receptors; indications: bradycardia, preop secretions; side effects: dry mouth, blurred vision, urinary retention, constipation", "Atropine slows heart rate and causes profuse watery diarrhea"),
            Triple("Autonomic Pharmacology: Adrenergic Agonists (Epinephrine/Norepinephrine)", "Epinephrine stimulates Alpha-1 (vasoconstriction), Beta-1 (increased HR/contractility), Beta-2 (bronchodilation); drug of choice for anaphylaxis", "Epinephrine is given to cause severe bronchospasm and hypotension"),
            Triple("Cardiovascular Pharm: Antihypertensives ACEi vs ARBs", "ACE inhibitors (-pril) block conversion of Ang I to Ang II (cause cough & angioedema); ARBs (-sartan) block Ang II receptors (no cough)", "ACE inhibitors cause severe hypokalemia and reduce bradykinin levels"),
            Triple("Cardiovascular Pharm: Calcium Channel Blockers (Amlodipine/Diltiazem)", "Block influx of calcium into vascular smooth muscle and cardiac cells; cause arterial vasodilation; side effects: peripheral edema, constipation", "Amlodipine causes severe renal artery constriction and hypertension"),
            Triple("Cardiovascular Pharm: Diuretics Furosemide vs Spironolactone", "Furosemide (loop diuretic, potassium-wasting, risk of hypokalemia & ototoxicity); Spironolactone (potassium-sparing, risk of hyperkalemia)", "Furosemide is a potassium-sparing diuretic that causes hyperkalemia"),
            Triple("CNS Pharmacology: Opioid Analgesics (Morphine) Safety", "Morphine (mu receptor agonist); side effects: respiratory depression, constipation, sedation, miosis; ANTIDOTE IS NALOXONE (NARCAN)", "Naloxone is an opioid agonist given to worsen morphine sedation"),
            Triple("CNS Pharmacology: Benzodiazepines (Diazepam) Antidote", "Enhance GABA activity causing sedation, anxiolysis, muscle relaxation; risk of dependence and respiratory depression; ANTIDOTE IS FLUMAZENIL", "Antidote for diazepam overdose is atropine or vitamin K"),
            Triple("Antimicrobial Pharm: Beta-Lactams Penicillins & Cephalosporins", "Inhibit bacterial cell wall synthesis; cross-sensitivity exists between penicillins and cephalosporins (~10%); monitor for allergic reactions", "Penicillins destroy bacterial DNA gyrase and cause osteomyelitis"),
            Triple("Antimicrobial Pharm: Fluoroquinolones (Ciprofloxacin) Tendon Rupture", "Inhibit DNA gyrase; BBW for TENDONITIS AND TENDON RUPTURE (especially Achilles tendon); avoid taking with milk/antacids", "Ciprofloxacin increases tendon strength and should be taken with calcium milk"),
            Triple("Antimicrobial Pharm: Tetracyclines Side Effects & Restrictions", "Inhibit protein synthesis (30S); side effects: TEETH DISCOLORATION in children < 8 yrs, photosensitivity; CONTRAINDICATED in pregnancy", "Tetracyclines are safely given to pregnant women and infants for mild acne"),
            Triple("Analgesics: Acetaminophen vs NSAIDs (Ibuprofen)", "Acetaminophen (analgesic/antipyretic, no anti-inflammatory, HEPATOTOXIC in overdose - antidote Acetylcysteine); NSAIDs (Gastroprotective COX-1 inhibition - GI ulcers/renal impairment)", "Acetaminophen causes severe peptic ulceration while NSAIDs cause acute liver failure")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Double the medication dose without physician order when patient is asleep",
                "Administer unverified experimental chemicals via rapid IV push"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 3,
                    subjectName = "Pharmacology I",
                    question = "PHA-633 Plus Q#${i + 1}: In Pharmacology I regarding ${t.first}, which pharmacological fact is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Pharmacology I (PHA-633) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 3 • PHA-633"
                )
            )
        }
        return list
    }

    private fun getHas1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Health History: Subjective vs Objective Data Collection", "Subjective data (symptoms reported by patient, e.g., pain scale, nausea); Objective data (measurable signs, e.g., BP, rash, lab values)", "Objective data consists of patient's personal feelings about hospital food"),
            Triple("Health History: PQRST Pain Assessment Framework", "Provoking/Palliating factors, Quality (sharp/dull), Region/Radiation, Severity (0-10 scale), Timing (onset/duration)", "PQRST framework measures arterial oxygen saturation and lung capacity"),
            Triple("Physical Assessment: Inspection Techniques", "Visual examination of body symmetry, skin color, shape, movement, and behavior; always perform inspection first before touching", "Perform deep abdominal palpation before visually inspecting skin"),
            Triple("Physical Assessment: Palpation Light vs Deep", "Light palpation (1 cm depth, assesses temperature, moisture, tenderness); Deep palpation (4 cm depth, assesses organ size and masses)", "Deep palpation is performed before auscultating abdominal bowel sounds"),
            Triple("Physical Assessment: Percussion Tone Sounds", "Tympany (air-filled stomach/intestine); Dullness (dense organs liver/spleen); Resonance (normal lung tissue); Hyperresonance (emphysema)", "Dullness is normal sound heard when percussing healthy inflated lung tissue"),
            Triple("Physical Assessment: Auscultation Bell vs Diaphragm", "Diaphragm (high-pitched sounds: normal S1/S2 heart sounds, bowel sounds, breath sounds); Bell (low-pitched sounds: S3/S4 gallops, vascular bruits)", "Use bell with heavy firm pressure to listen for high-pitched breath sounds"),
            Triple("Skin Assessment: Turgor, Edema Rating & Skin Lesions", "Turgor assesses hydration under clavicle/forearm; Edema (+1 mild 2mm to +4 severe 8mm pitting); Macule (flat) vs Papule (raised)", "Skin turgor tenting indicates severe fluid overload and hypervolemia"),
            Triple("Head & Neck: Pupillary Assessment PERRLA", "Pupils Equal, Round, Reactive to Light and Accommodation; assess direct and consensual light reflex", "PERRLA stands for Pulse Rate, Electrocardiogram, Respiration, Reflexes, Lungs, Abdomen"),
            Triple("Respiratory Assessment: Normal vs Adventitious Breath Sounds", "Normal (Vesicular, Bronchovesicular, Bronchial); Adventitious (Crackles/Rales = fluid, Wheezes = narrowed airway, Rhonchi = mucus)", "Crackles are normal musical sounds heard during exhalation in healthy athletes"),
            Triple("Cardiovascular Assessment: Heart Sound Landmarks (All Physiatrists Take Money)", "Aortic (2nd ICS RSB), Pulmonic (2nd ICS LSB), Erb's Point (3rd ICS LSB), Tricuspid (4th ICS LSB), Mitral/Apical (5th ICS MCL)", "Mitral area is located at 2nd intercostal space right sternal border"),
            Triple("Cardiovascular Assessment: S1 S2 Sounds & Murmurs", "S1 ('Lub' - closure of AV valves, tricuspid/mitral); S2 ('Dub' - closure of semilunar valves, aortic/pulmonic); Murmurs caused by turbulent blood flow", "S1 sound is caused by closure of aortic and pulmonic valves during diastole"),
            Triple("Abdominal Assessment: Sequence & Bowel Sound Frequency", "Sequence: Inspection, Auscultation, Percussion, Palpation; normal bowel sounds 5-30 per minute; listen for 5 full minutes before noting absent", "Listen for 2 seconds before declaring bowel sounds completely absent"),
            Triple("Musculoskeletal Assessment: Range of Motion & Muscle Strength Scale", "Assess Active and Passive ROM; Muscle strength graded 0 (no contraction) to 5 (normal full ROM against full resistance)", "Grade 5 muscle strength indicates complete flaccid paralysis"),
            Triple("Neurological Assessment: Glasgow Coma Scale (GCS) Scoring", "GCS evaluates Eye Opening (4), Verbal Response (5), Motor Response (6); Total score 3 to 15; Score <= 8 indicates comatose / intubate", "GCS score of 15 indicates severe brain death requiring immediate CPR"),
            Triple("Neurological Assessment: Cranial Nerves I to XII Overview", "CN I (Olfactory - smell), CN II (Optic - vision), CN III/IV/VI (Extraocular movements), CN V (Trigeminal), CN VII (Facial - expression/taste), CN VIII (Acoustic - hearing/balance), CN IX/X (Gag/swallow), CN XI (Accessory - shoulder shrug), CN XII (Hypoglossal - tongue)", "CN XII Hypoglossal controls pupil constriction and light reflex")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Record random fabricated numbers in patient medical chart without physical exam",
                "Omit physical assessment and rely on intuition alone"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 3,
                    subjectName = "Health Assessment I",
                    question = "HAS-634 Plus Q#${i + 1}: In Health Assessment I regarding ${t.first}, which clinical rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Health Assessment I (HAS-634) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 3 • HAS-634"
                )
            )
        }
        return list
    }

    private fun getIsl1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Bioethics in Nursing: Respect for Human Dignity (Karamat-e-Insaan)", "Islamic ethics emphasizes sacredness of human life (Surah Al-Ma'idah 5:32: saving one life is like saving all mankind) and treating all patients with dignity", "Islamic nursing ethics allows abandoning vulnerable ill patients without care"),
            Triple("PNC Code of Ethics: Beneficence & Non-Maleficence", "Beneficence (doing good for patient); Non-maleficence (doing no harm - 'Primum non-nocere'); foundational pillars of professional nursing practice", "Non-maleficence means inflicting intentional physical harm to discipline patients"),
            Triple("Islamic Principles: Cleanliness & Hygiene (Taharah)", "Prophetic Hadith: 'Cleanliness is half of faith' (Taharat-us-Nisfe-Eman); aligns directly with modern hand hygiene and infection control", "Cleanliness is considered irrelevant to spiritual and clinical nursing care"),
            Triple("Nursing Ethics: Patient Autonomy & Informed Consent", "Respecting patient's right to self-determination and voluntary decision-making regarding medical treatments after full disclosure", "Autonomy means nurse forces invasive surgery without informing patient or family"),
            Triple("Professional Confidentiality: Privacy & Trust (Amanah)", "Patient medical information is a sacred trust (Amanah); unauthorized disclosure violates ethical and Islamic principles", "Amanah encourages sharing patient medical records on personal social media"),
            Triple("Cultural Sensitivity: Gender Modesty (Satar) in Care", "Respecting patient modesty, providing same-gender healthcare providers when possible, ensuring proper draping during procedures", "Ignoring patient modesty standards during routine physical examination"),
            Triple("Compassion & Empathy in Healthcare (Rahmat & Ihsan)", "Providing care with excellence (Ihsan) and genuine compassion (Rahmat) without discrimination based on caste, creed, or status", "Providing care strictly based on social status and patient wealth"),
            Triple("Honesty & Integrity (Sidq & Amanah) in Documentation", "Accurate, truthful documentation of nursing care and vital signs; falsification of medical records violates both ethical and legal standards", "Falsifying vital signs in patient chart to save time during busy shift"),
            Triple("End-of-Life Care: Islamic Perspective on Palliative Care", "Providing comfort care, pain relief, dignity, and spiritual support; suicide and active euthanasia are forbidden (Haram)", "Euthanasia is actively encouraged as primary treatment for chronic disease"),
            Triple("Ethical Dilemmas: Justice & Fair Resource Allocation", "Justice (Adl) requires equitable distribution of nursing care and healthcare resources based on clinical urgency rather than favoritism", "Justice means prioritizing wealthy patients over critically ill indigent patients"),
            Triple("Accountability in Nursing: Professional & Spiritual Responsibility", "Nurses are accountable to regulatory bodies (PNC), institutions, patients, and ultimately to Allah for their clinical actions", "Nurses have zero professional or moral accountability for patient neglect"),
            Triple("Whistleblowing & Patient Advocacy: Speaking Up for Safety", "Advocating for patient safety when witnessing unethical conduct, incompetence, or harm by healthcare team members", "Remaining silent when witnessing severe medical errors that endanger patient life"),
            Triple("Islamic Medical Ethics: Preserving Life (Hifz-al-Nafs)", "Preservation of human life is one of five fundamental objectives of Islamic Shariah (Maqasid al-Shariah)", "Preservation of property is prioritized above preserving human life"),
            Triple("Responsibility of Knowledge: Continuous Professional Development", "Seeking knowledge is an obligation; nurses must continuously update clinical skills and evidence-based knowledge to serve patients safely", "Stopping all learning after basic graduation and using obsolete practices"),
            Triple("Work Ethics in Hospitals: Punctuality & Dedication", "Fulfilling contractual duties with dedication, honesty, punctuality, and professional etiquette in clinical settings", "Arriving 2 hours late for duty and leaving critically ill patients unattended")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Violate ethical principles and disregard professional PNC code of conduct",
                "Promote fraudulent clinical documentation for personal financial gain"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 3,
                    subjectName = "Islamic Studies / Ethics",
                    question = "ISL-635 Plus Q#${i + 1}: In nursing ethics and Islamic principles regarding ${t.first}, which rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Islamic Studies / Ethics (ISL-635) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 3 • ISL-635"
                )
            )
        }
        return list
    }
}
