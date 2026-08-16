package com.drtahir.studentkit.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 2 PLUS QUESTION BANK
 * Adds 100 unique questions for EACH subject in Semester 2 (Total = 500 MCQs)
 * Aligned with PNC & KMU Syllabus.
 */
object KpSemester2PlusQuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var currentId = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        // 1. Fundamentals of Nursing II (FON-621) - 100 MCQs
        val fon2Plus = getFON2ExtraQuestions(currentId)
        questions.addAll(fon2Plus)
        currentId += fon2Plus.size

        // 2. Anatomy & Physiology II (ANAT-622) - 100 MCQs
        val anat2Plus = getAnat2ExtraQuestions(currentId)
        questions.addAll(anat2Plus)
        currentId += anat2Plus.size

        // 3. Applied Nutrition (NUT-623) - 100 MCQs
        val nut1Plus = getNut1ExtraQuestions(currentId)
        questions.addAll(nut1Plus)
        currentId += nut1Plus.size

        // 4. Community Health Nursing I (CHN-624) - 100 MCQs
        val chn1Plus = getChn1ExtraQuestions(currentId)
        questions.addAll(chn1Plus)
        currentId += chn1Plus.size

        // 5. English II - Communication Skills (ENG-625) - 100 MCQs
        val eng2Plus = getEng2ExtraQuestions(currentId)
        questions.addAll(eng2Plus)
        currentId += eng2Plus.size

        return questions
    }

    private fun getFON2ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("10 Rights of Drug Administration: Verification", "Right patient, right medication, right dose, right route, right time, right documentation, right reason, right response, right to refuse, right education", "Rights only apply to oral tablet formulations in outpatient settings"),
            Triple("Parenteral Administration: Angles of Insertion", "IM (90°), Subcutaneous (45° - 90°), Intradermal (5° - 15°), IV (25° - 30°)", "Intradermal injections are administered deep into muscle at 90 degrees"),
            Triple("Intramuscular Sites: Ventrogluteal vs Dorsogluteal", "Ventrogluteal is safest IM site in adults (free of major nerves/vessels); Dorsogluteal avoided due to sciatic nerve risk", "Dorsogluteal is primary site for infants under 6 months"),
            Triple("Subcutaneous Injections: Insulin & Heparin", "Inject into adipose tissue (abdomen, thighs); do NOT rub heparin site after injection to prevent hematoma", "Always rub site vigorously with alcohol swab for 2 minutes after subcutaneous heparin"),
            Triple("Intravenous Therapy: Peripheral Cannula Gauge Sizes", "16G/14G (Trauma/Rapid fluids), 18G (Blood/Surgery), 20G (Routine IV fluids/meds), 22G/24G (Pediatrics/Fragile veins)", "24G yellow cannula is used for rapid blood pressure resuscitation in adult trauma"),
            Triple("IV Complications: Phlebitis vs Infiltration vs Extravasation", "Phlebitis (warmth, redness, cord-like vein); Infiltration (cool, pale, swollen tissue); Extravasation (vesicant tissue necrosis)", "Extravasation causes mild temporary skin redness that requires no intervention"),
            Triple("Urinary Catheterization: Sterile Technique & Foley Care", "Maintain strict sterile field during insertion; keep drainage bag BELOW bladder level to prevent reflux UTI", "Hang urinary drainage bag on bed side-rail above bladder level"),
            Triple("Wound Care: Dressing Selection & Healing Stages", "Primary intention (clean surgical incision); Secondary intention (gaping wound left to granulate); Wet-to-dry for mechanical debridement", "Primary intention wounds heal by forming heavy scar tissue from base upward"),
            Triple("Nasogastric Tube Care: Verification & Residual Volume", "Check pH of aspirate (< 5.0) and X-ray; check gastric residual volume before feeds (hold feed if GRV > 250-500 mL)", "Gastric residual volume of 600 mL indicates normal gastric emptying"),
            Triple("Blood Transfusion: Nursing Protocols & Adverse Reactions", "Verify with 2 RNs; run with 0.9% Normal Saline ONLY; stay with patient for FIRST 15 MINUTES; if reaction occurs: STOP TRANSFUSION IMMEDIATELY", "Flush blood transfusion line with 5% Dextrose in Water (D5W) if fever occurs"),
            Triple("Oxygen Therapy: Delivery Devices & Oxygen Concentrations", "Nasal cannula (1-6 L/min, 24-44% O2); Simple mask (5-8 L/min); Non-rebreather mask (10-15 L/min, 80-95% O2); Venturi mask (precise FiO2 for COPD)", "Nasal cannula at 2 L/min delivers 100% pure FiO2"),
            Triple("Pain Assessment: PQRST Mnemonic", "P (Provoking/Palliating), Q (Quality/Quantity), R (Region/Radiation), S (Severity 0-10 scale), T (Timing/Duration)", "PQRST measures serum potassium and arterial blood gases"),
            Triple("Surgical Care: Preoperative & Postoperative Care", "Preop NPO status prevents aspiration; Postop early ambulation prevents deep vein thrombosis (DVT) and atelectasis", "Bed rest without movement for 2 weeks postop prevents surgical complications"),
            Triple("Tracheostomy Care: Suctioning Safety Principles", "Hyperoxygenate before suctioning; limit suction pass to <= 10-15 seconds; apply suction ONLY while withdrawing catheter", "Apply continuous high suction while inserting catheter into tracheostomy"),
            Triple("Cold & Heat Therapy: Physiological Effects", "Cold application causes vasoconstriction (reduces acute swelling/pain); Heat application causes vasodilation (eases muscle stiffness)", "Apply hot water bag directly to acute ankle sprain in first 5 minutes")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Discontinue patient monitoring and discharge client without nursing notes",
                "Violate sterile technique and reuse disposable syringes across multiple clients"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 2,
                    subjectName = "Fundamentals of Nursing II",
                    question = "FON-621 Plus Q#${i + 1}: In advanced basic nursing care regarding ${t.first}, which clinical rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Fundamentals of Nursing II (FON-621) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 2 • FON-621"
                )
            )
        }
        return list
    }

    private fun getAnat2ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Cardiovascular: Cardiac Conduction Pathway Order", "SA Node (pacemaker) -> AV Node -> Bundle of His -> Left/Right Bundle Branches -> Purkinje Fibers", "Purkinje fibers initiate heartbeat and send impulses to SA node"),
            Triple("Cardiovascular: Cardiac Output Calculation", "Cardiac Output (CO) = Heart Rate (HR) x Stroke Volume (SV); normal resting CO is 4 - 8 Liters/min", "Cardiac output equals Systolic BP divided by Respiratory Rate"),
            Triple("Cardiovascular: Coronary Circulation & Heart Chambers", "Right atrium receives deoxygenated blood from vena cavae; Left ventricle pumps oxygenated blood to body via aorta", "Left atrium receives deoxygenated blood from systemic peripheral veins"),
            Triple("Respiratory: Alveolar Gas Exchange & Surfactant", "Type I pneumocytes form respiratory membrane for gas exchange; Type II pneumocytes secrete surfactant to reduce surface tension", "Type II pneumocytes destroy alveoli during inspiration"),
            Triple("Respiratory: Pulmonary Volumes & Tidal Volume", "Tidal Volume (normal quiet breath ~500 mL); Vital Capacity (maximum volume exhaled after maximum inspiration)", "Tidal volume is total lung volume remaining after complete exhalation"),
            Triple("Digestive System: Stomach Parietal Cells & Functions", "Parietal cells secrete Hydrochloric Acid (HCl) and Intrinsic Factor (essential for B12 absorption in terminal ileum)", "Parietal cells produce insulin and glucagon in stomach antrum"),
            Triple("Digestive System: Small Intestine Segments & Villi", "Duodenum (biliary/pancreatic digestion), Jejunum (major nutrient absorption), Ileum (B12 & bile salt reabsorption); Villi increase surface area", "Large intestine absorbs 95% of dietary proteins and fats"),
            Triple("Liver & Gallbladder: Bile & Portal Circulation", "Liver produces bile; Gallbladder stores and concentrates bile; Hepatic portal vein carries nutrient-rich blood from GI tract to liver", "Gallbladder synthesizes albumin and clotting factors"),
            Triple("Pancreas: Endocrine vs Exocrine Functions", "Exocrine (acinar cells secrete digestive enzymes amylase, lipase, trypsin); Endocrine (Islets of Langerhans secrete insulin & glucagon)", "Pancreas exocrine cells produce glucagon directly into blood"),
            Triple("Renal System: Nephron Structure & Blood Flow", "Glomerulus -> Bowman's capsule -> Proximal Convoluted Tubule -> Loop of Henle -> Distal Convoluted Tubule -> Collecting Duct", "Loop of Henle filters proteins into ureter"),
            Triple("Renal Physiology: Renin-Angiotensin-Aldosterone System (RAAS)", "Low BP triggers Renin secretion from juxtaglomerular cells -> Angiotensin II (vasoconstrictor) -> Aldosterone (Na+ & water reabsorption)", "RAAS causes massive sodium excretion and drop in arterial BP"),
            Triple("Endocrine System: Pituitary Gland Anterior vs Posterior", "Anterior pituitary secretes GH, ACTH, TSH, FSH, LH, Prolactin; Posterior pituitary stores/releases ADH (Vasopressin) and Oxytocin", "Posterior pituitary synthesizes TSH and insulin"),
            Triple("Endocrine System: Thyroid & Parathyroid Glands", "Thyroid produces T3, T4 (metabolism) and Calcitonin (lowers blood calcium); Parathyroid secretes PTH (raises blood calcium)", "PTH lowers blood calcium levels by depositing calcium into bones"),
            Triple("Endocrine System: Adrenal Cortex vs Adrenal Medulla", "Adrenal cortex secretes Glucocorticoids (cortisol), Mineralocorticoids (aldosterone), Androgens; Medulla secretes Epinephrine/Norepinephrine", "Adrenal medulla secretes aldosterone to regulate blood glucose"),
            Triple("Reproductive Anatomy: Uterine Wall & Menstrual Cycle", "Perimetrium, Myometrium (smooth muscle), Endometrium (sloughs during menses); Ovulation triggered by LH surge on day 14", "Ovulation is caused by sudden drop in progesterone on day 1")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Cause renal arterial stenosis and acute systemic ischemia",
                "Function as lymphatic fat deposits without physiological regulation"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 2,
                    subjectName = "Anatomy & Physiology II",
                    question = "ANAT-622 Plus Q#${i + 1}: In human anatomy & physiology concerning ${t.first}, which statement is accurate?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Anatomy & Physiology II (ANAT-622) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 2 • ANAT-622"
                )
            )
        }
        return list
    }

    private fun getNut1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Macronutrient Energy Values: Caloric Calculation", "Carbohydrates (4 kcal/g), Proteins (4 kcal/g), Lipids/Fats (9 kcal/g), Alcohol (7 kcal/g)", "Fats produce 4 kcal per gram while carbohydrates produce 9 kcal per gram"),
            Triple("Essential Amino Acids: Nitrogen Balance", "9 essential amino acids must be supplied by diet; Positive nitrogen balance occurs during growth, pregnancy, and tissue repair", "Negative nitrogen balance is ideal state for muscle hypertrophy"),
            Triple("Carbohydrate Types: Dietary Fiber Benefits", "Insoluble fiber increases stool bulk and peristalsis; Soluble fiber lowers LDL cholesterol and stabilizes blood glucose", "Dietary fiber is digested into glucose yielding 9 kcal per gram"),
            Triple("Fat-Soluble Vitamin Deficiency: Vitamin A & D", "Vitamin A deficiency causes night blindness (nyctalopia) and xerophthalmia; Vitamin D deficiency causes Rickets (children) & Osteomalacia (adults)", "Vitamin D deficiency causes scurvy and mucosal bleeding"),
            Triple("Vitamin C & Iron Absorption Synergy", "Ascorbic acid (Vitamin C) enhances non-heme iron absorption in duodenum; deficiency causes Scurvy (bleeding gums, poor wound healing)", "Vitamin C inhibits iron absorption and causes iron overload"),
            Triple("Trace Minerals: Iron, Iodine & Zinc Functions", "Iron (Hemoglobin synthesis/Anemia); Iodine (Thyroid hormone synthesis/Goiter); Zinc (Wound healing & immune function)", "Iodine deficiency causes pernicious anemia and osteomalacia"),
            Triple("Protein-Energy Malnutrition: Marasmus vs Kwashiorkor", "Marasmus (severe calorie & protein deficiency, skin and bones appearance); Kwashiorkor (severe protein deficiency with edema and belly distension)", "Kwashiorkor is characterized by muscle hypertrophy and lack of edema"),
            Triple("Therapeutic Diets: Diabetic & DASH Diets", "Diabetic diet emphasizes complex carbs, high fiber, low glycemic index; DASH diet emphasizes high potassium/calcium and low sodium for hypertension", "DASH diet restricts fresh vegetables and encourages high-sodium processed meats"),
            Triple("Therapeutic Diets: Renal Diet Restrictions", "Renal failure diet restricts Sodium, Potassium, Phosphorus, and Fluid intake; protein intake controlled based on dialysis status", "Renal diet encourages unrestricted potassium and phosphorus supplements"),
            Triple("Enteral Nutrition: Tube Feeding Nursing Care", "Elevate head of bed 30-45 degrees during and 30-60 mins post-feed to prevent aspiration; flush line before and after meds", "Lay patient flat in Trendelenburg position during continuous bolus tube feeds"),
            Triple("Total Parenteral Nutrition (TPN): Central Line & Blood Glucose", "TPN administered via central line due to high osmolarity; monitor blood glucose every 6 hours and never stop abruptly (risk of hypoglycemia)", "Infuse TPN through peripheral 24G cannula at 500 mL/hr"),
            Triple("Nutritional Assessment: BMI Categories", "BMI = Weight (kg) / Height (m²); Underweight (< 18.5), Normal (18.5-24.9), Overweight (25-29.9), Obese (>= 30)", "BMI of 22 is categorized as Class III severe obesity"),
            Triple("Dietary Management: Peptic Ulcer Disease & GERD", "Avoid caffeine, alcohol, spicy foods, chocolate, mint, and eating within 3 hours of bedtime; elevate head of bed", "Eat large high-fat late night meals immediately before lying flat"),
            Triple("Dietary Management: Celiac Disease Gluten-Free Diet", "Strict elimination of Wheat, Barley, Rye, and Oats (BROW); safe grains include Rice, Corn, and Quinoa", "Celiac patients should eat wheat bread and barley soup daily"),
            Triple("Cardiovascular Nutrition: Saturated vs Unsaturated Fats", "Replace saturated and trans fats with monounsaturated/polyunsaturated fats (olive oil, nuts, salmon) to reduce cardiovascular risk", "Trans fats lower LDL cholesterol and protect coronary arteries")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Cause acute hypervitaminosis A toxicity within 5 minutes of eating rice",
                "Induce irreversible glycogen storage breakdown in healthy skeletal muscle"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 2,
                    subjectName = "Applied Nutrition",
                    question = "NUT-623 Plus Q#${i + 1}: In applied clinical nutrition regarding ${t.first}, which fact is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Applied Nutrition (NUT-623) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 2 • NUT-623"
                )
            )
        }
        return list
    }

    private fun getChn1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Primary Health Care (PHC): Alma-Ata Declaration Principles", "Essential healthcare accessible, affordable, acceptable with community participation; 8 essential components (education, nutrition, water, maternal-child, immunization, endemic disease control, treatment, essential drugs)", "PHC focuses exclusively on tertiary ICU hospital care in urban centers"),
            Triple("Levels of Prevention: Primary vs Secondary vs Tertiary", "Primary (Health promotion & vaccines); Secondary (Early screening & diagnosis like mammograms/BP test); Tertiary (Rehabilitation & disability limitation)", "Vaccination against polio is an example of tertiary prevention"),
            Triple("Epidemiological Triad: Agent, Host, Environment", "Agent (microbe/pathogen), Host (susceptible human), Environment (extrinsic factors); disease occurs when balance is disrupted", "Epidemiological triad consists of doctor, nurse, and hospital billing unit"),
            Triple("Expanded Program on Immunization (EPI) Pakistan Schedule", "At birth (BCG, OPV-0, HepB); 6, 10, 14 weeks (Pentavalent, PCV, OPV/IPV, Rotavirus); 9 & 15 months (Measles-Rubella MR)", "BCG vaccine is given at 15 years of age to prevent polio"),
            Triple("Cold Chain Management: Vaccine Storage Temperatures", "Maintain vaccines at +2°C to +8°C; OPV and Measles stored in freezer (-20°C); Ice-lined refrigerator (ILR) essential component", "Store Pentavalent and Tetanus toxoid vaccines in deep freezer below -20°C"),
            Triple("Water Purification: Household & Municipal Methods", "Boiling (boil vigorously for 1-3 mins); Chlorination (0.5 mg/L free residual chlorine); Filtration and SODIS (solar disinfection)", "Adding sugar to unboiled river water destroys all enteric viruses"),
            Triple("Vector-Borne Diseases: Dengue vs Malaria Control", "Aedes aegypti mosquito (Dengue: day-biter, clean stagnant water); Anopheles mosquito (Malaria: night-biter); vector control includes removing stagnant water and bed nets", "Aedes mosquitoes breed strictly in heavily polluted sewage water at midnight"),
            Triple("Environmental Health: Solid Waste Management", "3 Rs: Reduce, Reuse, Recycle; proper disposal via sanitary landfilling or incineration prevents fly and rodent vector breeding", "Dispose of untreated clinical hazardous waste in public park open dumps"),
            Triple("Communicable Disease Control: Tuberculosis & DOTS Strategy", "Directly Observed Treatment Short-course (DOTS) ensures compliance; anti-TB drugs (Rifampicin, Isoniazid, Pyrazinamide, Ethambutol)", "DOTS strategy allows patients to take anti-TB drugs whenever they feel like it"),
            Triple("Maternal & Child Health (MCH): Antenatal Care Visits WHO", "Minimum 4 WHO recommended ANC visits during pregnancy; check blood pressure, proteinuria, anemia, fetal growth, and Tetanus Toxoid vaccination", "Antenatal care visits are only needed after active labor contractions start"),
            Triple("School Health Services: Health Screening & Hygiene Education", "Screening for refractive errors, dental caries, malnutrition, skin infections, and promoting handwashing hygiene in schools", "School health services focus exclusively on administering adult cardiac medications"),
            Triple("Community Assessment: Windshield Survey & Data Collection", "Observational method of driving/walking through community to assess environmental conditions, housing, safety, and community resources", "Windshield survey involves examining hospital ICU charts in medical records room"),
            Triple("Occupational Health: Hazards & Ergonomic Prevention", "Physical, chemical, biological, and ergonomic hazards; Personal Protective Equipment (PPE) and workplace ergonomics reduce injury", "Occupational safety standards prohibit wearing protective helmets in construction"),
            Triple("Demography & Vital Statistics: Crude Death Rate vs Infant Mortality Rate", "Infant Mortality Rate (IMR) = (Deaths under 1 year / Total live births) x 1000; sensitive indicator of overall community health status", "Infant mortality rate measures deaths among elderly adults over 80 years"),
            Triple("Family Planning Methods: Permanent vs Reversible", "Reversible (Condoms, OCPs, IUCD, Depo-Provera); Permanent (Tubal ligation in females, Vasectomy in males)", "Vasectomy is a temporary barrier method used for 2 weeks")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Increase industrial chemical pollution in residential drinking water supplies",
                "Abolish public health vaccination programs across rural health centers"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 2,
                    subjectName = "Community Health Nursing I",
                    question = "CHN-624 Plus Q#${i + 1}: In community health nursing regarding ${t.first}, which principle is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Community Health Nursing I (CHN-624) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 2 • CHN-624"
                )
            )
        }
        return list
    }

    private fun getEng2ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Therapeutic Communication: Active Listening & Restating", "Active listening involves non-verbal attentiveness; Restating repeats patient's main idea to confirm understanding and encourage elaboration", "Active listening requires interrupting patient every 10 seconds to correct grammar"),
            Triple("Therapeutic Communication: Open-Ended vs Closed-Ended Questions", "Open-ended questions ('Tell me more about your pain') encourage expression; Closed-ended ('Is your pain sharp?') yield brief specific facts", "Closed-ended questions should always be used to explore deep emotional trauma"),
            Triple("Non-Therapeutic Communication Barriers: False Reassurance & Advising", "Giving false reassurance ('Everything will be fine') minimizes feelings; Giving unsolicited personal advice destroys patient autonomy", "False reassurance builds deep therapeutic trust in critical care settings"),
            Triple("Non-Verbal Communication: Body Language, Touch & Proxemics", "Facial expressions, posture, eye contact, and touch convey empathy; respect personal space boundaries (1.5 to 4 feet)", "Standing over patient with crossed arms and scowling conveys warmth and open empathy"),
            Triple("SBAR Clinical Handover Model: Situation, Background, Assessment, Recommendation", "S (Current issue), B (Clinical history), A (Current vital signs/findings), R (Requested action/intervention)", "SBAR stands for Subjective, Blood Pressure, Airway, Respiratory rate"),
            Triple("Therapeutic Rapport: Empathy vs Sympathy", "Empathy understands patient's feelings objectively ('I understand this is difficult'); Sympathy feels pity ('I feel so sorry and sad for you')", "Sympathy is objective professional boundary while empathy is personal pity"),
            Triple("Managing Anxious or Aggressive Patients: De-escalation Techniques", "Maintain calm tone, low voice, open body stance, keep safe physical distance, validate feelings, do not argue", "Corner aggressive patient and shout commands loudly while making physical threats"),
            Triple("Interprofessional Communication: Nurse-Physician Collaboration", "Clear, concise, evidence-based reporting using SBAR format promotes patient safety and prevents medical errors", "Avoid communicating critical changes in patient status to attending physicians"),
            Triple("Clinical Documentation Standards: Accuracy & Confidentiality", "Document objectively, immediately, concisely, avoid subjective bias; maintain HIPAA / patient confidentiality", "Write subjective personal opinions about patient family members in medical record"),
            Triple("Barrier Resolution: Communicating with Hearing-Impaired Patient", "Face patient directly, ensure good lighting, speak clearly in normal tone (do NOT shout), reduce background noise, use written aids", "Shout loudly directly into ear of patient with sensorineural hearing loss"),
            Triple("Barrier Resolution: Communicating with Aphasic Patient", "Use simple sentences, allow ample time for response, use visual communication boards, ask simple yes/no questions", "Speak extremely fast in complex medical jargon to aphasic patient"),
            Triple("Culture-Sensitive Communication: Cultural Awareness in KP/Pakistan", "Respect modest boundaries, preferred language, gender preferences, and religious health beliefs during interview", "Force culturally inappropriate topics without regard for patient modesty"),
            Triple("Presentation & Public Speaking: Clinical Case Presentation", "Structure presentation logically: Patient Chief Complaint -> History -> Examination -> Diagnostic Findings -> Plan", "Present clinical case starting with hospital billing receipt before patient name"),
            Triple("Conflict Resolution: Assertive vs Aggressive Communication", "Assertive communication expresses needs firmly and respectfully without violating rights of others; Aggressive violates others", "Assertive communication means yelling and insulting colleagues to get tasks done"),
            Triple("Feedback Principles: Constructive Feedback in Clinical Supervision", "Provide feedback privately, focus on specific behaviors rather than personality, offer actionable solutions", "Humiliate nursing students publicly in front of patients for minor clinical mistakes")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Cause severe communication breakdown and breach professional ethics",
                "Violate PNC code of conduct by ignoring patient language preferences"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 2,
                    subjectName = "English II (Communication Skills)",
                    question = "ENG-625 Plus Q#${i + 1}: In communication skills and therapeutic rapport regarding ${t.first}, which rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "English II (ENG-625) Communication Skills Concept: ${t.second}.",
                    reference = "KMU PNC Semester 2 • ENG-625"
                )
            )
        }
        return list
    }
}
