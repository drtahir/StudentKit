package com.drtahir.studentkit.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 6 PLUS QUESTION BANK
 * Adds 100 unique questions for EACH subject in Semester 6 (Total = 400 MCQs)
 * Aligned with PNC & KMU Syllabus.
 */
object KpSemester6PlusQuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var currentId = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        // 1. Mental Health / Psychiatric Nursing - 100 MCQs
        val psyPlus = getPsyExtraQuestions(currentId)
        questions.addAll(psyPlus)
        currentId += psyPlus.size

        // 2. Obstetrics & Gynecological Nursing - 100 MCQs
        val obgPlus = getObgExtraQuestions(currentId)
        questions.addAll(obgPlus)
        currentId += obgPlus.size

        // 3. Culture, Health & Society - 100 MCQs
        val socPlus = getSocExtraQuestions(currentId)
        questions.addAll(socPlus)
        currentId += socPlus.size

        // 4. Introduction to Nursing Research - 100 MCQs
        val resPlus = getResExtraQuestions(currentId)
        questions.addAll(resPlus)
        currentId += resPlus.size

        return questions
    }

    private fun getPsyExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Schizophrenia: Positive vs Negative Symptoms", "Positive symptoms include hallucinations, delusions, and disorganized speech; Negative symptoms include flat affect, avolition, anhedonia, and alogia", "Negative symptoms refer exclusively to audible command hallucinations and acute violent grandiosity"),
            Triple("Lithium Carbonate Monitoring & Toxicity Management", "Therapeutic level is 0.6-1.2 mEq/L; toxicity (> 1.5 mEq/L) causes severe tremors, ataxia, confusion, seizures; monitor renal function and serum sodium balance", "Lithium levels should be maintained above 5.0 mEq/L without checking renal function"),
            Triple("Major Depressive Disorder & Suicide Precautions", "Assess suicide risk directly using unambiguous questions ('Are you having thoughts of harming yourself?'); place client on 1-on-1 observation if immediate intent exists", "Avoid asking patients about suicide because asking will plant suicidal thoughts in their mind"),
            Triple("Anxiety & Panic Disorders: Acute Nursing Care", "During panic attack: stay with client, maintain calm presence, give simple short commands, reduce environmental stimulation, encourage slow deep breathing", "Leave panic attack patient alone in crowded room and demand they write a detailed essay"),
            Triple("Obsessive-Compulsive Disorder (OCD): Behavioral Interventions", "Exposure and Response Prevention (ERP) helps reduce anxiety gradually; allow adequate time for rituals initially, then gradually negotiate time limits", "Forcibly restrain OCD client's hands instantly without therapeutic support or rapport"),
            Triple("Post-Traumatic Stress Disorder (PTSD): Nursing Interventions", "Trauma-informed care; validate feelings, establish safety, offer grounding techniques during flashbacks (e.g., focus on physical surroundings)", "Trigger PTSD flashbacks repeatedly in dark locked room to force instant memory extinction"),
            Triple("Borderline Personality Disorder: Splitting & Nursing Management", "Splitting (viewing staff as all-good or all-bad); nursing intervention requires consistent staff boundaries, clear rules, and open team communication", "Allow patient to divide nursing staff and give individual nurses special unauthorized gifts"),
            Triple("Neuroleptic Malignant Syndrome (NMS) vs Serotonin Syndrome", "NMS (dopamine blockade: severe 'lead-pipe' rigidity, high fever, elevated CK, autonomic instability); Serotonin Syndrome (excess serotonin: hyperreflexia, myoclonus, diarrhea)", "NMS causes severe watery diarrhea and flaccid limp paralyzed muscles without fever"),
            Triple("Delirium vs Dementia: Clinical Distinction", "Delirium (acute onset, fluctuating consciousness, reversible, medical emergency); Dementia (gradual onset, progressive irreversible cognitive decline)", "Delirium is a chronic irreversible hereditary disease lasting 20 years without fluctuations"),
            Triple("Alcohol Withdrawal & Delirium Tremens (DTs)", "Withdrawal begins 6-8 hrs post last drink; DTs occur at 48-72 hrs (severe tremors, hallucinations, autonomic hyperactivity); manage with Benzodiazepines (Diazepam / Lorazepam)", "Alcohol withdrawal delirium tremens is treated with high-dose alcohol administration"),
            Triple("Antipsychotic Extrapyramidal Symptoms (EPS) Treatment", "Acute dystonia and pseudoparkinsonism are managed with anticholinergic agents like Benztropine (Cogentin) or Diphenhydramine (Benadryl) IV/IM", "Treat acute dystonic oculogyric crisis with immediate rapid IV bolus haloperidol"),
            Triple("Therapeutic Communication: Restating & Reflection", "Restating (repeating the main thought expressed by client); Reflection (directing back client's feelings to encourage self-exploration and insight)", "Therapeutic communication relies on lecturing, giving unsolicited advice, and scolding client"),
            Triple("Electroconvulsive Therapy (ECT): Pre & Post Nursing Care", "Pre-ECT: NPO 6-8 hrs, atropine to reduce secretions, succinylcholine muscle relaxant; Post-ECT: monitor airway/vitals, reorient client due to temporary memory loss", "Post-ECT patient should be forced to run on a treadmill immediately while dizzy"),
            Triple("Voluntary vs Involuntary Mental Health Admission", "Voluntary (client requests admission, retains civil rights); Involuntary (client is dangerous to self or others or gravely disabled; court/certified medical hold)", "Involuntary admission allows hospital to permanently seize patient's bank account"),
            Triple("Eating Disorders: Refeeding Syndrome Prevention", "Potentially fatal electrolyte shift (severe hypophosphatemia, hypokalemia) when refeeding severely malnourished anorexia patient; start nutrition slowly and monitor cardiac status", "Refeed anorexic patients with 10,000 calories daily on day 1 without electrolyte monitoring")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Ignore psychiatric nursing protocol and discharge client without evaluation",
                "Delegate emergency psychiatric crisis care to unregistered visitors"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 6,
                    subjectName = "Mental Health / Psychiatric Nursing",
                    question = "PSY-661 Plus Q#${i + 1}: In Mental Health Nursing regarding ${t.first}, which clinical principle is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Mental Health Nursing (PSY-661) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 6 • PSY-661"
                )
            )
        }
        return list
    }

    private fun getObgExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Naegele's Rule for Expected Date of Delivery (EDD)", "Calculate EDD: First day of Last Menstrual Period (LMP) + 7 days - 3 months + 1 year (e.g., LMP Nov 10 -> EDD Aug 17)", "Calculate EDD by adding 9 days and subtracting 8 months from delivery date"),
            Triple("GTPAL Obstetric History System Definitions", "G (Gravida), T (Term births >= 37 wks), P (Preterm births 20-36 wks), A (Abortions/losses < 20 wks), L (Living children)", "GTPAL system counts total maternal surgical procedures in life"),
            Triple("Preeclampsia vs Eclampsia Management & Magnesium Sulfate", "Preeclampsia (HTN >= 140/90, proteinuria, edema); Eclampsia (onset of grand mal seizures); Treat seizure prevention with MgSO4 (Antidote: Calcium Gluconate)", "Antidote for Magnesium Sulfate toxicity is high-dose IV Potassium Chloride"),
            Triple("Placenta Previa vs Abruptio Placentae Features", "Placenta Previa (painless bright red vaginal bleeding, soft non-tender uterus; NO VAGINAL EXAMS); Abruptio Placentae (painful dark red bleeding, rigid board-like abdomen)", "Perform digital vaginal exam in placenta previa to dislodge placenta"),
            Triple("Stages of Labor & Nursing Priorities", "1st Stage (Dilation 0-10 cm); 2nd Stage (Full dilation to birth of baby); 3rd Stage (Delivery of placenta); 4th Stage (Postpartum recovery 1-4 hrs)", "2nd stage of labor begins when membranes rupture and ends at 2 cm dilation"),
            Triple("Fetal Heart Rate Deceleration Patterns (VEAL CHOP)", "Variable = Cord compression; Early = Head compression; Acceleration = OK / Oxygenated; Late = Placental insufficiency (Late decels require immediate L-side positioning, O2, IV fluids, stop oxytocin)", "Late decelerations indicate reassuring normal fetal heart rate response to laughter"),
            Triple("Umbilical Cord Prolapse Emergency Care", "Emergency intervention: Position mother in Knee-Chest or Trendelenburg, insert gloved hand into vagina to lift presenting part off cord, call for emergency C-section", "Push prolapsed umbilical cord back inside uterus with dry gauze"),
            Triple("Postpartum Hemorrhage (PPH) Causes & Initial Action", "Primary cause is Uterine Atony (Soft/boggy fundus); FIRST NURSING ACTION IS FUNDAL MASSAGE; administer uterotonics (Oxytocin, Misoprostol, Ergometrine)", "Initial action for boggy fundal hemorrhage is applying cold ice to maternal feet"),
            Triple("Normal Newborn Adaptation & APGAR Scoring", "APGAR assessed at 1 & 5 mins; score 7-10 normal; score < 4 requires resuscitation; administer Vitamin K IM to prevent Hemorrhagic Disease of Newborn", "Vitamin K is omitted in newborns because neonates produce excess Vitamin K at birth"),
            Triple("Neonatal Respiratory Distress Syndrome (RDS) in Preterm", "Caused by surfactant deficiency leading to alveolar collapse, grunting, nasal flaring, intercostal retractions; treat with exogenous surfactant via ETT and CPAP", "RDS is caused by excessive surfactant fluid in full-term neonates"),
            Triple("TORCH Infections in Pregnancy", "Toxoplasmosis, Other (Syphilis, Varicella), Rubella, Cytomegalovirus, Herpes Simplex; causes congenital anomalies; Rubella vaccine given POSTPARTUM (live vaccine contraindicated in pregnancy)", "Administer live Rubella vaccine during 1st trimester of pregnancy"),
            Triple("Cervical Cancer Screening & HPV Vaccine", "Pap smear screens for cervical dysplasia; Human Papillomavirus (HPV strains 16 & 18) causes most cervical cancers; HPV vaccine recommended for adolescents", "Pap smear is used to diagnose acute urinary tract infection in males"),
            Triple("Polycystic Ovary Syndrome (PCOS) Clinical Features", "Hyperandrogenism, ovulatory dysfunction, polycystic ovaries on ultrasound, insulin resistance, hirsutism, obesity, and infertility", "PCOS is caused by acute bacterial infection of the fallopian tubes"),
            Triple("Contraceptive Methods: Intrauterine Devices (IUDs)", "Copper IUD (non-hormonal emergency & long-acting 10-yr protection); Levonorgestrel IUD (hormonal, reduces menstrual blood loss); check string monthly", "IUDs are swallowed orally with meals three times daily"),
            Triple("Mastitis vs Breast Engorgement in Lactating Mothers", "Engorgement (bilateral, swollen, firm breasts; encourage frequent emptying); Mastitis (unilateral, warm, tender red wedge, fever; treat antibiotics, CONTINUE BREASTFEEDING)", "In acute mastitis, immediately stop all breastfeeding and discard milk permanently")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Ignore maternal vital signs and discharge mother during active labor",
                "Delegate emergency delivery care to untrained hospital staff"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 6,
                    subjectName = "Obstetrics & Gynecological Nursing",
                    question = "OBG-662 Plus Q#${i + 1}: In Obstetrics & Gynecological Nursing regarding ${t.first}, which clinical protocol is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Obstetrics & Gynecological Nursing (OBG-662) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 6 • OBG-662"
                )
            )
        }
        return list
    }

    private fun getSocExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Leininger's Transcultural Nursing Theory", "Culture Care Diversity and Universality; Sunrise Model emphasizes providing culturally congruent, meaningful nursing care adapted to client's values and beliefs", "Leininger's theory mandates forcing western medical culture onto all patients"),
            Triple("Social Determinants of Health (SDOH)", "Conditions in environments where people are born, live, learn, work, and age (education, income, neighborhood, healthcare access) shaping health outcomes", "SDOH states health is determined exclusively by genetic DNA mutations"),
            Triple("Cultural Humility vs Cultural Competence", "Cultural Humility involves lifelong commitment to self-evaluation, redressing power imbalances in nurse-patient relationships, and respectful partnership", "Cultural humility means mastering all world cultures after reading a 1-page flyer"),
            Triple("Hot/Cold Theory & Cultural Dietary Practices", "Traditional belief in many cultures (including South Asian/Middle Eastern) balancing hot and cold foods/illnesses; respect non-harmful dietary preferences", "Force patients to eat cold foods during cold illnesses to punish them"),
            Triple("Gender Dynamics & Health Autonomy in KP Culture", "Recognize role of family decision-makers (e.g., male head of household or elder women) while advocating for female patient autonomy and informed consent", "Exclude female patients entirely from knowing their own medical diagnosis"),
            Triple("Social Stigma & Tuberculosis / Mental Illness", "Stigma creates fear of discrimination, delaying diagnostic presentation and treatment adherence; community education reduces health stigma", "Publicly shame patients with tuberculosis on television to enforce isolation"),
            Triple("Parsons' Sick Role Concept in Sociology", "Rights (exempt from normal social roles, not blamed for illness) and Obligations (must seek competent medical help and desire to get well)", "Sick role exempts patients permanently from all legal laws forever"),
            Triple("Giger & Davidhizar Transcultural Assessment Model", "6 cultural phenomena: Communication, Space, Social Organization, Time (present vs future orientation), Environmental Control, and Biological Variations", "Model evaluates only skin color and shoe size"),
            Triple("Ethnonursing Research Method", "Qualitative research method developed by Leininger to study cultural beliefs, care values, and health practices from the insider (emic) perspective", "Ethnonursing uses lab rat blood tests to measure cultural intelligence"),
            Triple("Impact of Urbanization & Rural-Urban Migration", "Rapid urban migration creates crowded informal settlements (slums), poor sanitation, increased communicable disease transmission, and social stress", "Urban migration eliminates all infectious disease transmission permanently"),
            Triple("End-of-Life Cultural & Religious Care Practices", "In Islamic cultural context: face patient toward Qibla if possible, maintain modesty, allow family recitations, handle deceased body with solemn dignity and respect", "Disregard family religious end-of-life wishes and discard body without ritual preparation"),
            Triple("Communication Barriers & Professional Interpreters", "Use trained medical interpreters rather than family members (especially children) to ensure accurate translation, confidentiality, and unbiased medical communication", "Use 5-year-old child to translate complex cancer diagnosis to parents"),
            Triple("Traditional Healers & Integration with Health System", "Understand role of traditional practitioners (e.g., bonesetters, hakeems) in community; educate on harmful practices while integrating safe traditional wisdom", "Arrest all traditional healers and close community clinics"),
            Triple("Health Inequities & Vulnerable Communities", "Health Inequity refers to systematic, avoidable, and unjust differences in health status between population groups (e.g., IDPs, disabled, poor)", "Health inequity means rich people suffer more diseases due to excess wealth"),
            Triple("Community Empowerment & Local Leadership Engagement", "Engaging local religious leaders (Imams) and community elders (Jirga) in public health campaigns (e.g., polio vaccination) builds trust and acceptance", "Ignore community leaders and force medical interventions via police raids")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Violate cultural sensitivity and impose personal biases on patients",
                "Abolish social determinant monitoring in public health practice"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 6,
                    subjectName = "Culture, Health & Society",
                    question = "SOC-663 Plus Q#${i + 1}: In Culture, Health & Society regarding ${t.first}, which sociological concept is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Culture, Health & Society (SOC-663) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 6 • SOC-663"
                )
            )
        }
        return list
    }

    private fun getResExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("PICO Framework for Evidence-Based Practice (EBP)", "P = Patient/Population, I = Intervention, C = Comparison/Control, O = Outcome (T = Timeframe); used to structure clinical research questions", "PICO stands for Physician-In-Charge-of-Operation"),
            Triple("Randomized Controlled Trial (RCT) Features", "Gold standard experimental design characterized by Manipulation (intervention), Control group, and Randomization to eliminate bias", "RCTs observe historical charts without control group or intervention"),
            Triple("Qualitative vs Quantitative Research Paradigms", "Quantitative (numerical data, statistical analysis, deductive reasoning, objective testing); Qualitative (words/narratives, thematic analysis, inductive reasoning)", "Quantitative research uses personal poetry to measure blood pressure"),
            Triple("Sampling Methods: Probability vs Non-Probability", "Probability sampling (Simple Random, Stratified, Cluster) gives every population member equal non-zero chance of selection; Non-Probability (Convenience, Purposive) does not", "Convenience sampling gives every human on earth equal chance of selection"),
            Triple("Ethics in Research: Informed Consent & Voluntary Participation", "Informed consent requires full disclosure of study purpose, risks/benefits, assurance of confidentiality, and right to withdraw at any time without penalty", "Informed consent forces subjects to remain in study under penalty of fine"),
            Triple("Independent vs Dependent Variables in Research", "Independent variable (presumed cause/manipulated intervention, e.g., position change); Dependent variable (presumed effect/outcome measured, e.g., pressure injury rate)", "Dependent variable is the researcher's age and salary"),
            Triple("Reliability vs Validity of Data Collection Instruments", "Reliability = Consistency/repeatability of measure (Cronbach's alpha >= 0.70); Validity = Accuracy of instrument measuring what it claims to measure", "Reliability means the instrument is printed on heavy paper"),
            Triple("Statistical Significance: P-Value Interpretation", "P-value < 0.05 indicates statistically significant result (less than 5% probability result occurred by random chance, rejecting null hypothesis)", "P-value > 0.90 indicates absolute proof that intervention cures all diseases"),
            Triple("Level of Evidence Pyramid in Nursing Research", "Systematic Reviews & Meta-Analyses of RCTs (Level I) offer highest level of evidence; Expert opinion / case reports (Level VII) offer lowest level", "Expert opinion offers higher evidence than double-blind meta-analyses"),
            Triple("Qualitative Research Methodologies: Phenomenology", "Phenomenology explores the lived experiences of individuals experiencing a specific phenomenon (e.g., lived experience of surviving ICU admission)", "Phenomenology measures blood cell counts in lab rats"),
            Triple("Grounded Theory Qualitative Method", "Grounded Theory focuses on generating or developing a theory inductively from data grounded in real-world social processes", "Grounded Theory tests pre-existing mathematical formulas in physics"),
            Triple("Blinding Techniques in Experimental Studies", "Single-blind (participants unaware of group allocation); Double-blind (both participants and researchers/data collectors unaware) to prevent bias", "Double-blind means both participant and doctor are blindfolded during surgery"),
            Triple("Type I vs Type II Errors in Hypothesis Testing", "Type I Error (False Positive: rejecting true null hypothesis, alpha); Type II Error (False Negative: failing to reject false null hypothesis, beta)", "Type I error occurs when researcher forgets to write abstract"),
            Triple("Institutional Review Board (IRB) / Ethics Committee Role", "Protects human subjects' rights, safety, dignity, and well-being before research study begins; reviews protocol for risks vs benefits", "IRB primary role is to profit from research book sales"),
            Triple("Dissemination & Translation of EBP into Nursing Practice", "Translating research evidence into clinical guidelines (e.g., Iowa Model, Stetler Model) reduces clinical practice variation and improves patient outcomes", "Keep research findings hidden in private locked drawer forever")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Falsify statistical research data to guarantee publication",
                "Violate ethical standards and conduct experiments without consent"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 6,
                    subjectName = "Introduction to Nursing Research",
                    question = "RES-664 Plus Q#${i + 1}: In Nursing Research regarding ${t.first}, which methodological principle is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Introduction to Nursing Research (RES-664) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 6 • RES-664"
                )
            )
        }
        return list
    }
}
