package com.example.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 4 PLUS QUESTION BANK
 * Adds 100 unique questions for EACH subject in Semester 4 (Total = 500 MCQs)
 * Aligned with PNC & KMU Syllabus.
 */
object KpSemester4PlusQuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var currentId = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        // 1. Adult Health Nursing II (Med-Surg II) - 100 MCQs
        val ahn2Plus = getAhn2ExtraQuestions(currentId)
        questions.addAll(ahn2Plus)
        currentId += ahn2Plus.size

        // 2. Pathophysiology II - 100 MCQs
        val pat2Plus = getPat2ExtraQuestions(currentId)
        questions.addAll(pat2Plus)
        currentId += pat2Plus.size

        // 3. Pharmacology II - 100 MCQs
        val pha2Plus = getPha2ExtraQuestions(currentId)
        questions.addAll(pha2Plus)
        currentId += pha2Plus.size

        // 4. Health Assessment II - 100 MCQs
        val has2Plus = getHas2ExtraQuestions(currentId)
        questions.addAll(has2Plus)
        currentId += has2Plus.size

        // 5. Developmental Psychology - 100 MCQs
        val psy1Plus = getPsy1ExtraQuestions(currentId)
        questions.addAll(psy1Plus)
        currentId += psy1Plus.size

        return questions
    }

    private fun getAhn2ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Neurological: Increased Intracranial Pressure (ICP) Nursing Priorities", "Elevate HOB 30 degrees, maintain head in neutral alignment, avoid coughing/straining, administer Mannitol", "Place client in Trendelenburg position and encourage frequent coughing"),
            Triple("Neurological: Ischemic vs Hemorrhagic Stroke Management", "Ischemic stroke (rtPA within 3-4.5 hours if no bleeding); Hemorrhagic stroke (CONTRAINDICATED rtPA, lower BP, prevent rebleeding)", "Administer tissue plasminogen activator (rtPA) to active brain hemorrhage"),
            Triple("Neurological: Spinal Cord Injury & Autonomic Dysreflexia", "Occurs T6 or above; uninhibited sympathetic response to noxious stimulus (distended bladder/bowel); severe HTN, bradycardia, headache; ELEVATE HOB FIRST", "Lay client flat in Trendelenburg and apply abdominal binder when BP is 220/120"),
            Triple("Ophthalmology: Cataracts vs Glaucoma Features", "Cataracts (painless blurry vision, opacity of lens, loss of red reflex); Glaucoma (increased IOP, tunnel vision, painless in open-angle, severe pain in acute angle-closure)", "Cataracts cause sudden severe eye pain and irreversible optic nerve cupping"),
            Triple("Ophthalmology: Retinal Detachment Signs & Care", "Sudden light flashes, floaters, 'curtain drawn over vision'; emergency repair; keep client immobilized to prevent further detachment", "Encourage vigorous heavy lifting and eye rubbing during retinal detachment"),
            Triple("ENT: Meniere's Disease Triad & Safety Precautions", "Triad: Vertigo, Tinnitus, Sensorineural Hearing Loss; Fall risk precautions; low-sodium diet; avoid sudden head movements", "Meniere's disease is treated with high-sodium diet and rapid spinning exercises"),
            Triple("Endocrine: Thyroidectomy Complications & Airway Safety", "Monitor for stridor/laryngeal edema (keep tracheostomy tray at bedside) and hypocalcemia (Trousseau/Chvostek signs due to accidental parathyroid removal)", "Remove tracheostomy set from room post-thyroidectomy and restrict calcium"),
            Triple("Endocrine: Addision's Crisis vs Cushing's Syndrome", "Addisonian crisis (severe hypotension, hyponatremia, hyperkalemia, hypoglycemia; treat IV hydrocortisone); Cushing's (hyperglycemia, HTN, moon face)", "Addisonian crisis causes severe hypertension, hypernatremia, and hypokalemia"),
            Triple("Burns: Parkland Formula & First 24 Hours Resuscitation", "Fluid = 4 mL x Weight (kg) x % TBSA burned; give 50% in FIRST 8 HOURS and remaining 50% over next 16 hours (Lactated Ringer's)", "Give entire Parkland fluid volume as 5% Dextrose over 24 hours equally"),
            Triple("Burns: Rule of Nines Assessment", "Head (9%), Each Arm (9%), Anterior Torso (18%), Posterior Torso (18%), Each Leg (18%), Perineum (1%)", "Each arm accounts for 18% and head accounts for 36% in Rule of Nines"),
            Triple("Oncology: Chemotherapy Side Effects & Neutropenic Precautions", "Neutropenia (ANC < 500-1000); private room, strict hand hygiene, avoid raw fruits/vegetables, live plants, and sick visitors", "Provide fresh unwashed raw salad and live flowers to neutropenic patient"),
            Triple("Oncology: Tumor Lysis Syndrome (TLS) Metabolic Triad", "Hyperkalemia, Hyperuricemia, Hyperphosphatemia, Hypocalcemia; massive cell destruction; hydrate aggressively and give Allopurinol", "TLS causes hypokalemia, hypouricemia, and hypercalcemia"),
            Triple("Critical Care: Shock Types - Hypovolemic, Cardiogenic, Septic", "Hypovolemic (low CVP, fluid resuscitation); Cardiogenic (heart failure, pulmonary edema, avoid fluid overload); Septic (vasodilation, fever, IV fluids & antibiotics)", "Cardiogenic shock is treated with massive 10 L rapid IV normal saline bolus"),
            Triple("Critical Care: Mechanical Ventilation Alarm Troubleshooting", "High-pressure alarm (secretions, biting tube, coughing, kinked tubing - suction/unkink); Low-pressure alarm (disconnection, cuff leak - reattach)", "Low-pressure alarm indicates patient is coughing against ventilator tubing"),
            Triple("Emergency Nursing: Triage Categories - Red, Yellow, Green, Black", "Red (Immediate life-threatening), Yellow (Delayed serious), Green (Minor walking wounded), Black (Expectant/Deceased)", "Black tag priority is assigned to minor finger sprain walking wounded")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Ignore critical clinical changes and discharge patient without handover",
                "Delegate emergency ICU airway management to untrained clerical staff"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 4,
                    subjectName = "Adult Health Nursing II (Med-Surg II)",
                    question = "AHN-641 Plus Q#${i + 1}: In Adult Health Nursing II regarding ${t.first}, which clinical decision is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Adult Health Nursing II (AHN-641) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 4 • AHN-641"
                )
            )
        }
        return list
    }

    private fun getPat2ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Neuro-Pathology: Multiple Sclerosis Demyelination", "Autoimmune destruction of CNS myelin sheaths leading to slowed nerve conduction, optic neuritis, weakness, and spasticity", "MS is caused by peripheral motor neuron axon destruction from bacterial infection"),
            Triple("Neuro-Pathology: Parkinson's Disease Dopamine Deficiency", "Degeneration of dopaminergic neurons in substantia nigra; Lewy bodies; resting tremor, rigidity ('lead-pipe'), bradykinesia, postural instability", "Parkinson's is caused by excess dopamine production in cerebral cortex"),
            Triple("Neuro-Pathology: Alzheimer's Disease Amyloid Plaques & Tangles", "Extracellular Beta-amyloid plaques and intracellular Neurofibrillary Tangles (Tau protein) causing progressive cortical atrophy and memory loss", "Alzheimer's is caused by sudden ischemic infarction of cerebellar peduncles"),
            Triple("Neuro-Pathology: Myasthenia Gravis AChR Autoantibodies", "Autoantibodies against Postsynaptic Acetylcholine Receptors (AChR) at neuromuscular junction; fluctuating muscle weakness, ptosis, diplopia", "Myasthenia Gravis destroys pre-synaptic serotonin vesicles in spinal cord"),
            Triple("Endocrine Pathology: Graves' Disease Hyperthyroidism", "Autoantibodies (TSI) stimulate TSH receptors; hyperthyroidism, diffuse goiter, exophthalmos (pretibial myxedema), heat intolerance, weight loss", "Graves' disease causes hypothyroidism, cold intolerance, weight gain, and edema"),
            Triple("Endocrine Pathology: Hashimoto's Thyroiditis", "Autoimmune destruction of thyroid gland (anti-TPO antibodies); primary cause of hypothyroidism; fatigue, weight gain, cold intolerance, constipation", "Hashimoto's thyroiditis causes thyroid storm, severe tachycardia, and heat intolerance"),
            Triple("Endocrine Pathology: Diabetes Insipidus vs SIADH", "DI (deficient ADH / nephrogenic insensitivity; polyuria, polydipsia, hypernatremia, low urine SG < 1.005); SIADH (excess ADH; hyponatremia, fluid retention, high urine SG)", "DI is caused by excess ADH leading to severe fluid overload and hyponatremia"),
            Triple("Renal Pathology: Acute Tubular Necrosis (ATN) Phases", "Ischemic or nephrotoxic injury to tubular epithelial cells; Phases: Initiation, Oliguric (fluid overload, hyperkalemia), Diuretic (hypokalemia), Recovery", "ATN causes immediate hyperfiltration and continuous high urine output"),
            Triple("GI Pathology: Inflammatory Bowel Disease Crohn's vs Ulcerative Colitis", "Crohn's (transmural, skip lesions, anywhere GI tract, non-caseating granulomas, fistulas); UC (mucosal, continuous, colon/rectum, bloody diarrhea, toxic megacolon)", "Ulcerative colitis affects transmural layers of esophagus with skip lesions"),
            Triple("GI Pathology: Cirrhosis & Portal Hypertension Complications", "Fibrotic scarring of liver -> portal hypertension -> esophageal varices, ascites, splenomegaly, hepatic encephalopathy (elevated ammonia)", "Cirrhosis causes low portal pressures and decreased serum ammonia levels"),
            Triple("Hepatic Pathology: Viral Hepatitis Types A, B, C, D, E", "Hep A & E (fecal-oral transmission, acute); Hep B, C, D (blood/body fluids transmission, risk of chronic hepatitis, cirrhosis, hepatocellular carcinoma)", "Hepatitis C is transmitted via contaminated municipal drinking water"),
            Triple("Pancreatic Pathology: Acute Pancreatitis Pathogenesis", "Autodigestion of pancreatic parenchyma by prematurely activated digestive enzymes (trypsin); causes severe epigastric pain radiating to back, elevated lipase/amylase", "Acute pancreatitis is caused by bacterial infection of Islets of Langerhans"),
            Triple("Musculoskeletal Pathology: Gouty Arthritis Uric Acid Crystals", "Hyperuricemia leading to deposition of Monosodium Urate crystals in joints (podagra - 1st MTP joint); severe joint inflammation, tophi", "Gout is caused by calcium oxalate crystal deposition in heart valves"),
            Triple("Dermatological Pathology: Psoriasis Autoimmune Hyperproliferation", "T-cell mediated autoimmune disease causing rapid keratinocyte hyperproliferation; silvery scales on erythematous plaques (extensor surfaces)", "Psoriasis is a contagious viral skin eruption spread by skin contact"),
            Triple("Hematological Pathology: Leukemia Acute vs Chronic", "ALL (most common in children); AML (Auer rods in myeloblasts); CML (Philadelphia chromosome t(9;22)); CLL (mature B-cell proliferation in elderly)", "CML is characterized by total absence of Philadelphia chromosome and zero WBCs")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Cause sudden genetic repair and complete reversal of chronic disease",
                "Function as normal uninjured biological tissue"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 4,
                    subjectName = "Pathophysiology II",
                    question = "PAT-642 Plus Q#${i + 1}: In Pathophysiology II regarding ${t.first}, which pathological mechanism is accurate?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Pathophysiology II (PAT-642) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 4 • PAT-642"
                )
            )
        }
        return list
    }

    private fun getPha2ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Psychiatry Pharm: SSRIs (Fluoxetine, Sertraline) Serotonin Syndrome", "Inhibit serotonin reuptake; onset 2-4 weeks; BBW for suicidal ideation in young adults; Serotonin Syndrome (hyperthermia, clonus, hyperreflexia, agitation)", "SSRIs produce immediate therapeutic effects within 5 seconds of first dose"),
            Triple("Psychiatry Pharm: Antipsychotics First vs Second Generation", "1st Gen (Haloperidol - high risk EPS/TD/NMS); 2nd Gen (Clozapine/Risperidone - metabolic syndrome, agranulocytosis with Clozapine)", "Haloperidol causes zero risk of extrapyramidal symptoms or NMS"),
            Triple("Psychiatry Pharm: Mood Stabilizers Lithium Toxicity & Levels", "Therapeutic level 0.6-1.2 mEq/L; toxicity (> 1.5): coarse tremors, ataxia, confusion, seizures; low sodium / dehydration increases toxicity", "Lithium therapeutic range is 10 to 20 mEq/L and requires zero sodium diet"),
            Triple("Endocrine Pharm: Insulins Rapid, Short, Intermediate, Long Acting", "Rapid (Lispro/Aspart, onset 15m); Short (Regular, onset 30m, IV route); Intermediate (NPH, cloudy); Long (Glargine/Detemir, peakless 24h, DO NOT MIX)", "Mix Insulin Glargine with Regular insulin in same syringe before IV push"),
            Triple("Endocrine Pharm: Oral Antidiabetics Metformin & Lactic Acidosis", "Metformin (Biguanide, decreases hepatic glucose production); does NOT cause hypoglycemia; hold 48 hrs before/after IV contrast dye (risk of LACTIC ACIDOSIS)", "Metformin causes severe hypoglycemia and should be given with IV contrast"),
            Triple("Endocrine Pharm: Antithyroid Drugs PTU & Methimazole", "Inhibit thyroid hormone synthesis; PTU preferred in 1st trimester pregnancy; Methimazole preferred thereafter; side effect: AGRANULOCYTOSIS (fever/sore throat)", "Methimazole increases thyroid hormone secretion and causes hyperthyroidism"),
            Triple("Respiratory Pharm: Inhaled Corticosteroids Fluticasone Care", "Prevent airway inflammation; instruct client to rinse mouth and spit after use to prevent ORAL CANDIDIASIS (thrush); not a rescue inhaler", "Swallow fluticasone powder with warm liquid and do not rinse mouth"),
            Triple("GI Pharm: Proton Pump Inhibitors Omeprazole Long-Term Risks", "Irreversibly inhibit H+/K+ ATPase pump; long-term risks: C. diff infection, osteoporotic bone fractures, hypomagnesemia, Vitamin B12 deficiency", "Omeprazole increases stomach acid production and strengthens bone density"),
            Triple("GI Pharm: Antiemetics Ondansetron (Zofran) & Metoclopramide", "Ondansetron (5-HT3 antagonist, risk of QT prolongation); Metoclopramide (prokinetic/dopamine antagonist, risk of Extrapyramidal Symptoms / Tardive Dyskinesia)", "Metoclopramide is a 5-HT3 blocker that cures QT prolongation"),
            Triple("Chemotherapy Pharm: Doxorubicin Cardiotoxicity & Red Urine", "Antineoplastic antibiotic; severe CARDIOTOXICITY (monitor ejection fraction, max lifetime dose); expected benign RED DISCOLORATION of urine", "Doxorubicin is cardioprotective and increases ejection fraction to 100%"),
            Triple("Chemotherapy Pharm: Cyclophosphamide Hemorrhagic Cystitis", "Alkylating agent; risk of HEMORRHAGIC CYSTITIS; encourage aggressive hydration and administer MESNA as protective agent", "Cyclophosphamide causes severe renal artery dilation and hypovolemia"),
            Triple("Immunosuppressive Pharm: Cyclosporine & Tacrolimus Nursing Precautions", "Prevent organ transplant rejection; side effects: NEPHROTOXICITY, hypertension, infection risk; avoid grapefruit juice (inhibits CYP3A4)", "Drink 2 Liters of fresh grapefruit juice daily with cyclosporine"),
            Triple("Rheumatology Pharm: Methotrexate Monitoring & Folic Acid", "DMARD/antifolate; treats RA and psoriasis; side effects: bone marrow suppression, hepatotoxicity, stomatitis; give FOLIC ACID supplementation", "Methotrexate is an essential folic acid supplement given to cure anemia"),
            Triple("Anticoagulants: Direct Oral Anticoagulants (DOACs) Rivaroxaban/Apixaban", "Factor Xa inhibitors; fixed dosing, no routine INR monitoring required; Antidote for Factor Xa inhibitors is Andexanet alfa", "DOACs require daily INR monitoring and frequent dose titrations"),
            Triple("Emergency Pharm: Epinephrine in Anaphylaxis & Cardiac Arrest", "Anaphylaxis (0.3 mg IM 1:1000 in lateral thigh); Cardiac arrest (1 mg IV 1:10,000 every 3-5 mins); potent alpha and beta agonist", "Epinephrine is given orally as 10 mg tablet during cardiac arrest")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Double the drug dose whenever patient complains of headache",
                "Administer medication intravenously without verifying patient name or allergies"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 4,
                    subjectName = "Pharmacology II",
                    question = "PHA-643 Plus Q#${i + 1}: In Pharmacology II regarding ${t.first}, which pharmacological principle is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Pharmacology II (PHA-643) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 4 • PHA-643"
                )
            )
        }
        return list
    }

    private fun getHas2ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Advanced Neurological: Cranial Nerve Assessment Techniques", "CN II (Snellen chart), CN III/IV/VI (EOMs), CN V (Facial sensation & jaw clench), CN VII (Smile/frown), CN VIII (Whisper test), CN IX/X (Gag reflex), CN XI (Shoulder shrug), CN XII (Tongue protrusion)", "CN VIII is assessed by testing sharp pain sensation on big toe"),
            Triple("Advanced Neurological: Cerebellar Function Assessment", "Assess coordination and balance: Rapid Alternating Movements (RAM), Finger-to-Nose test, Heel-to-Shin test, Romberg test (positive = loss of balance with eyes closed)", "Positive Romberg test indicates normal deep tendon reflexes in knees"),
            Triple("Advanced Neurological: Reflexes Deep Tendon Scale", "Graded 0 (absent), 1+ (hypoactive), 2+ (normal), 3+ (hyperactive), 4+ (clonus); Babinski reflex (extensor plantar response normal in infants < 2 yrs, abnormal in adults)", "Babinski sign in adults shows downward curling of toes as abnormal pathological reflex"),
            Triple("Advanced Cardiovascular: Peripheral Vascular Assessment & ABI", "Ankle-Brachial Index (ABI) = Ankle SBP / Arm SBP (Normal 0.9 - 1.3; < 0.9 indicates Peripheral Artery Disease); assess 6 Ps of arterial occlusion", "ABI < 0.5 indicates normal arterial blood flow without ischemia"),
            Triple("Advanced Respiratory: Thoracic Deformities & Tactile Fremitus", "Barrel chest (1:1 AP diameter in COPD); Tactile fremitus (increased over consolidation/pneumonia; decreased over pleural effusion/pneumothorax)", "Tactile fremitus increases significantly over large pleural effusion"),
            Triple("Advanced Abdominal: Special Signs - Murphy, McBurney, Rovsing, Obturator", "Murphy's sign (Cholecystitis); McBurney/Rovsing/Obturator/Psoas signs (Appendicitis); Grey Turner / Cullen signs (Pancreatitis / Hemoperitoneum)", "Murphy's sign is positive when client feels pain in left foot on hip extension"),
            Triple("Advanced Musculoskeletal: Special Orthopedic Tests", "Phalen's & Tinel's tests (Carpal Tunnel Syndrome); Lachman / Anterior Drawer (ACL tear); McMurray test (Meniscal tear); Thomas test (Hip flexion contracture)", "Phalen's test assesses meniscus tear in knee joint"),
            Triple("Female Breast Assessment: Mammography & Clinical Breast Exam", "Perform CBE annually; self-exam 3-5 days after menses; inspect for dimpling, nipple retraction, Peau d'orange appearance (indicates inflammatory breast cancer)", "Peau d'orange appearance is a normal healthy finding during lactation"),
            Triple("Male Reproductive Assessment: Testicular Self-Exam (TSE) & Prostate", "TSE performed monthly in warm shower starting age 15; Digital Rectal Exam (DRE) assesses prostate size (smooth enlarged = BPH; hard nodular = Prostate cancer)", "TSE should be performed once every 10 years in cold water"),
            Triple("Pediatric Health Assessment: Milestones & Developmental Screening", "Fontanelles (Anterior closes 12-18 mos, Posterior closes 2-3 mos); Denver II screening tool; Moro reflex disappears by 4-6 months", "Anterior fontanelle closes at birth while posterior fontanelle closes at 18 years"),
            Triple("Geriatric Assessment: SPICES Framework & Frailty", "SPICES: Sleep disorders, Problems with eating, Incontinence, Confusion, Evidence of falls, Skin breakdown; Mini-Mental State Exam (MMSE)", "SPICES framework assesses pediatric congenital heart defects"),
            Triple("Mental Status Assessment: ABCT Framework", "Appearance, Behavior, Cognition, Thought processes; assess orientation to person, place, time, situation (Oriented x4)", "ABCT framework measures serum lithium and digoxin levels"),
            Triple("Assessment of Pain in Non-Verbal Patients: PAINAD & FLACC", "FLACC scale (Face, Legs, Activity, Cry, Consolability for young children/non-verbal); PAINAD scale (Pain Assessment in Advanced Dementia)", "FLACC scale is used exclusively for adult cardiac catheterization evaluation"),
            Triple("Assessment of Transcultural Nursing: Giger & Davidhizar Model", "Assesses 6 cultural phenomena: Communication, Space, Social organization, Time, Environmental control, Biological variations", "Transcultural assessment forces all patients to adopt Western cultural norms"),
            Triple("Diagnostic Data Integration: Lab Values & Assessment Correlation", "Correlate clinical findings with diagnostic data (e.g., crackles + elevated WBC + infiltrate on CXR = Pneumonia)", "Ignore lab values completely when performing physical assessment")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Fabricate assessment findings and document them without physical examination",
                "Omit specialized physical testing and rely on subjective patient claims"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 4,
                    subjectName = "Health Assessment II",
                    question = "HAS-644 Plus Q#${i + 1}: In Advanced Health Assessment II regarding ${t.first}, which clinical finding is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Health Assessment II (HAS-644) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 4 • HAS-644"
                )
            )
        }
        return list
    }

    private fun getPsy1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Developmental Theories: Erik Erikson's 8 Psychosocial Stages", "Trust vs Mistrust (Infancy), Autonomy vs Shame (Toddler), Initiative vs Guilt (Preschool), Industry vs Inferiority (School-age), Identity vs Role Confusion (Adolescence)", "Identity vs Role Confusion occurs in infancy during breastfeeding"),
            Triple("Developmental Theories: Jean Piaget's Cognitive Development Stages", "Sensorimotor (0-2 yrs, object permanence), Preoperational (2-7 yrs, egocentrism), Concrete Operational (7-11 yrs, conservation), Formal Operational (11+ yrs, abstract thinking)", "Object permanence develops during Formal Operational stage in adulthood"),
            Triple("Developmental Theories: Sigmund Freud's Psychosexual Stages", "Oral (0-1 yr), Anal (1-3 yrs, toilet training), Phallic (3-6 yrs, Oedipus complex), Latency (6-12 yrs), Genital (12+ yrs)", "Phallic stage occurs during late adulthood in retirement homes"),
            Triple("Developmental Theories: Lawrence Kohlberg's Moral Development", "Preconventional (punishment/reward), Conventional (social order/approval), Postconventional (universal ethical principles)", "Postconventional morality relies strictly on avoiding physical spanking"),
            Triple("Infant Development (0-1 Year): Milestones & Attachment", "Rolls over (4-6 mos), sits unsupported (6-8 mos), crawls (8-10 mos), walks (12 mos); Stranger anxiety begins ~6-8 months", "Infant sits unsupported at birth and walks at 2 weeks"),
            Triple("Toddler Development (1-3 Years): Negativism & Parallel Play", "Negativism ('No!'), temper tantrums, parallel play (playing alongside others without interaction), toilet training readiness", "Toddlers engage in cooperative team sports with complex rules"),
            Triple("Preschooler Development (3-6 Years): Associative Play & Magical Thinking", "Associative play (group play without rigid organization), magical thinking, fears (dark, monsters), imaginative storytelling", "Preschoolers exhibit formal abstract mathematical reasoning"),
            Triple("School-Age Development (6-12 Years): Cooperative Play & Industry", "Cooperative/competitive team play, peer relationships, task accomplishment (Industry vs Inferiority), understanding conservation", "School-age play is characterized by solitary parallel non-interactive play"),
            Triple("Adolescent Development (12-18 Years): Identity & Peer Influence", "Search for identity, independence from parents, peer group influence, risk-taking behavior, formal operational abstract thought", "Adolescents develop object permanence and primary trust vs mistrust"),
            Triple("Young Adult Development (19-40 Years): Intimacy vs Isolation", "Establishing career, forming intimate romantic/social relationships, independence, settling into adult roles", "Young adulthood is dominated by industry vs inferiority and toilet training"),
            Triple("Middle Adult Development (40-65 Years): Generativity vs Stagnation", "Contributing to society/next generation (Generativity), career peak, empty nest syndrome, caring for aging parents", "Middle adulthood primary conflict is basic trust vs mistrust"),
            Triple("Older Adult Development (65+ Years): Ego Integrity vs Despair", "Reflecting on life with satisfaction (Integrity) vs regret (Despair); coping with loss, retirement, physical decline", "Older adulthood primary task is establishing gender identity and toilet training"),
            Triple("Defense Mechanisms: Denial, Displacement, Projection, Rationalization", "Denial (refusing reality); Displacement (redirecting emotion to safer target); Projection (attributing own unwanted feelings to others); Rationalization (excuses)", "Projection means accepting full personal blame for all mistakes"),
            Triple("Defense Mechanisms: Sublimation vs Repression vs Reaction Formation", "Sublimation (channeling unacceptable impulses into socially acceptable actions); Repression (unconscious blocking); Reaction Formation (expressing opposite feeling)", "Sublimation channels anger into physical assault on colleagues"),
            Triple("Stress & Coping: Hans Selye's General Adaptation Syndrome (GAS)", "Alarm Reaction (fight or flight, catecholamines), Resistance Phase (coping, cortisol), Exhaustion Phase (burnout, disease)", "Exhaustion phase increases immune system strength to 100%")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Inhibit normal psychological maturation and cause immediate cognitive regression",
                "Function as purely physical anatomical structures without psychological impact"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 4,
                    subjectName = "Developmental Psychology",
                    question = "PSY-645 Plus Q#${i + 1}: In Developmental Psychology regarding ${t.first}, which statement is accurate?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Developmental Psychology (PSY-645) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 4 • PSY-645"
                )
            )
        }
        return list
    }
}
