package com.example.ui.screens

data class KpSemesterSubject(
    val code: String,
    val name: String,
    val creditHours: String,
    val description: String,
    val keyTopics: List<String>
)

data class KpSemesterInfo(
    val semesterNumber: Int,
    val title: String,
    val description: String,
    val totalCreditHours: Int,
    val subjects: List<KpSemesterSubject>
)

data class KpSemesterQuestion(
    val id: Int,
    val semesterNumber: Int,
    val subjectName: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val reference: String
)

/**
 * KHYBER PAKHTUNKHWA (KP) BSN NURSING CURRICULUM & SEMESTER-WISE QUIZ REPOSITORY
 * Aligned with Khyber Medical University (KMU) Peshawar & PNC (Pakistan Nursing Council) guidelines.
 * Covers 4-Year Generic BS Nursing (BSN) Semesters 1 through 8.
 */
object KpNursingCurriculumRepository {

    fun getSemesters(): List<KpSemesterInfo> {
        return listOf(
            KpSemesterInfo(
                semesterNumber = 1,
                title = "Semester 1 (BSN First Year)",
                description = "Foundational nursing skills, cell biology, human structure & functional chemistry.",
                totalCreditHours = 17,
                subjects = listOf(
                    KpSemesterSubject("FON-611", "Fundamentals of Nursing I", "4 CH", "Concepts of nursing, history, vital signs, bedmaking, personal hygiene, and patient safety.", listOf("Vital Signs", "Asepsis & Infection Control", "Patient Positioning", "Nursing Process ADPIE")),
                    KpSemesterSubject("ANAT-612", "Anatomy & Physiology I", "4 CH", "Structural layout of cells, tissues, skeletal, muscular, and nervous systems.", listOf("Osteology & Arthrology", "Myology", "Nervous System", "Cell Organelles")),
                    KpSemesterSubject("BIO-613", "Biochemistry for Nurses", "3 CH", "Structure and metabolism of carbohydrates, lipids, proteins, enzymes, and clinical electrolytes.", listOf("Enzymology", "Carbohydrate Metabolism", "Lipid & Protein Chemistry", "Buffer Systems")),
                    KpSemesterSubject("MIC-614", "Microbiology", "3 CH", "Bacterial taxonomy, viral replication, sterilization techniques, and nosocomial infection prevention.", listOf("Gram Positive vs Gram Negative", "Sterilization & Autoclaving", "Host Immunity", "Pathogenic Fungi & Parasites")),
                    KpSemesterSubject("ENG-615", "English I (Functional English)", "3 CH", "Grammar, medical vocabulary, reading comprehension, and professional sentence structure.", listOf("Medical Terminology", "Active & Passive Voice", "Paragraph Writing", "Comprehension"))
                )
            ),
            KpSemesterInfo(
                semesterNumber = 2,
                title = "Semester 2 (BSN First Year)",
                description = "Advanced basic nursing procedures, internal organ systems, nutrition & community wellness.",
                totalCreditHours = 18,
                subjects = listOf(
                    KpSemesterSubject("FON-621", "Fundamentals of Nursing II", "4 CH", "Medication administration, IV cannulation, catheterization, wound care & pressure sore management.", listOf("10 Rights of Drug Admin", "Urinary Catheterization", "Wound Dressing", "Intravenous Lines")),
                    KpSemesterSubject("ANAT-622", "Anatomy & Physiology II", "4 CH", "Cardiovascular, respiratory, digestive, renal, endocrine, and reproductive anatomy & physiology.", listOf("Cardiac Conduction System", "Pulmonary Ventilation", "Renal Nephron Function", "Endocrine Glands")),
                    KpSemesterSubject("NUT-623", "Applied Nutrition", "3 CH", "Macronutrients, micronutrients, therapeutic diets (diabetic, renal, low-salt), and nutritional assessment.", listOf("Caloric Requirements", "Rickets & Scurvy", "Enteral & Parenteral Feeds", "Therapeutic Diets")),
                    KpSemesterSubject("CHN-624", "Community Health Nursing I", "4 CH", "Primary Health Care (PHC), environmental health, water sanitation, immunization & cold chain.", listOf("EPI Immunization Schedule", "Water Purification", "Level of Prevention", "Primary Health Care")),
                    KpSemesterSubject("ENG-625", "English II (Communication Skills)", "3 CH", "Interpersonal communication, therapeutic patient rapport, clinical handovers & presentation skills.", listOf("Therapeutic Communication", "SBAR Handover", "Active Listening", "Barrier Resolution"))
                )
            ),
            KpSemesterInfo(
                semesterNumber = 3,
                title = "Semester 3 (BSN Second Year)",
                description = "Pathophysiology, pharmacology, health assessment, and medical-surgical nursing core.",
                totalCreditHours = 17,
                subjects = listOf(
                    KpSemesterSubject("AHN-631", "Adult Health Nursing I (Med-Surg I)", "5 CH", "Management of cardiovascular, respiratory, gastrointestinal, and endocrine medical-surgical conditions.", listOf("Hypertension & CAD", "COPD & Asthma", "Peptic Ulcer & Diabetes", "Pre & Post Op Care")),
                    KpSemesterSubject("PAT-632", "Pathophysiology I", "3 CH", "Cellular adaptation, inflammation, neoplasia, tissue repair, and hemodynamics.", listOf("Atrophy & Hypertrophy", "Acute Inflammation", "Benign vs Malignant", "Edema Mechanisms")),
                    KpSemesterSubject("PHA-633", "Pharmacology I", "3 CH", "Pharmacokinetics, pharmacodynamics, autonomic drugs, antibiotics, and cardiovascular drugs.", listOf("Half-Life & Bioavailability", "Beta Blockers & ACEi", "Penicillins & Cephalosporins", "Antihypertensives")),
                    KpSemesterSubject("HAS-634", "Health Assessment I", "3 CH", "Head-to-toe physical assessment techniques: inspection, palpation, percussion, and auscultation.", listOf("Physical Examination", "Lungs & Heart Sounds", "Cranial Nerves Test", "Abdominal Quadrants")),
                    KpSemesterSubject("ISL-635", "Islamic Studies / Ethics", "3 CH", "Islamic principles in health care, medical ethics, patient dignity, and Islamic bioethics.", listOf("Bioethics in Islam", "Patient Confidentiality", "Nurse-Patient Duties", "Islamic Values"))
                )
            ),
            KpSemesterInfo(
                semesterNumber = 4,
                title = "Semester 4 (BSN Second Year)",
                description = "Advanced medical-surgical conditions, neuro-renal care, developmental psychology & pharmacology II.",
                totalCreditHours = 18,
                subjects = listOf(
                    KpSemesterSubject("AHN-641", "Adult Health Nursing II (Med-Surg II)", "5 CH", "Neurological disorders, renal failure, musculoskeletal trauma, burns, and oncological nursing.", listOf("Stroke & Increased ICP", "Acute & Chronic Kidney Injury", "Fractures & Traction", "Burn Staging & Fluid Resuscitation")),
                    KpSemesterSubject("PAT-642", "Pathophysiology II", "3 CH", "Pathogenesis of neurological, renal, musculoskeletal, immunological, and hematological diseases.", listOf("Glomerulonephritis", "Multiple Sclerosis", "Anemias & Leukemia", "Shock Physiology")),
                    KpSemesterSubject("PHA-643", "Pharmacology II", "3 CH", "CNS depressants, analgesics, diuretics, endocrine therapies, and emergency resuscitation drugs.", listOf("Opioids & Naloxone", "Furosemide & Spironolactone", "Insulins & Oral Hypoglycemics", "Epinephrine & Atropine")),
                    KpSemesterSubject("HAS-644", "Health Assessment II", "3 CH", "Specialized physical assessment, neurological assessment (GCS), musculoskeletal & vascular exam.", listOf("Glasgow Coma Scale", "Peripheral Vascular Exam", "Reflexes Testing", "Musculoskeletal Range of Motion")),
                    KpSemesterSubject("PSY-645", "Developmental Psychology", "4 CH", "Human development theories (Erikson, Piaget), coping mechanisms, and behavioral dynamics.", listOf("Erikson's Psychosocial Stages", "Piaget Cognitive Theory", "Defense Mechanisms", "Stress Coping Strategies"))
                )
            ),
            KpSemesterInfo(
                semesterNumber = 5,
                title = "Semester 5 (BSN Third Year)",
                description = "Pediatric nursing care, community health management, and nursing education principles.",
                totalCreditHours = 17,
                subjects = listOf(
                    KpSemesterSubject("PED-651", "Pediatric Nursing", "5 CH", "Child growth & development, pediatric respiratory illnesses, IMNCI guidelines, congenital defects.", listOf("Developmental Milestones", "Croup & Epiglottitis", "Tetralogy of Fallot", "IMNCI Protocols")),
                    KpSemesterSubject("CHN-652", "Community Health Nursing II", "5 CH", "Epidemiological triad, communicable disease control (Polio, TB, Dengue), school health & family planning.", listOf("TB DOTS Therapy", "Dengue Fever Control", "Contraceptive Methods", "School Health Screening")),
                    KpSemesterSubject("EDU-653", "Nursing Education & Teaching", "3 CH", "Principles of teaching and learning, lesson planning, clinical instruction, and evaluation methods.", listOf("Bloom's Taxonomy", "Lesson Plan Structure", "Clinical Mentorship", "OSCE Evaluation")),
                    KpSemesterSubject("ENG-654", "English III (Technical Writing)", "2 CH", "Research proposal writing, clinical case reporting, literature review, and academic referencing.", listOf("Abstract Writing", "APA Referencing", "Case Study Formatting", "Literature Search")),
                    KpSemesterSubject("PAK-655", "Pakistan Studies", "2 CH", "History, constitution, healthcare policy in Pakistan, KP health system, and Sehat Sahulat Card.", listOf("KP Health Infrastructure", "Constitution & Rights", "Sehat Sahulat Card Program", "PNC Regulations"))
                )
            ),
            KpSemesterInfo(
                semesterNumber = 6,
                title = "Semester 6 (BSN Third Year)",
                description = "Psychiatric mental health, obstetrics & gynecology, sociology, and research methodology.",
                totalCreditHours = 17,
                subjects = listOf(
                    KpSemesterSubject("PSY-661", "Mental Health / Psychiatric Nursing", "5 CH", "Schizophrenia, mood disorders, anxiety, psychotropic drugs, lithium toxicity, crisis intervention.", listOf("Schizophrenia Care", "Lithium Toxicity", "Therapeutic Communication", "Depression & Suicide Guard")),
                    KpSemesterSubject("OBG-662", "Obstetrics & Gynecological Nursing", "5 CH", "Antenatal care, labor stages, high-risk pregnancy (preeclampsia, placenta previa), and postpartum care.", listOf("Labor Stages", "Magnesium Sulfate Protocol", "PPH Prevention", "APGAR Scoring")),
                    KpSemesterSubject("SOC-663", "Culture, Health & Society", "3 CH", "Sociological determinants of health, cultural beliefs in KP/Pakistan, gender roles & community dynamics.", listOf("Social Determinants of Health", "KP Cultural Beliefs", "Stigma in Disease", "Community Dynamics")),
                    KpSemesterSubject("RES-664", "Introduction to Nursing Research", "4 CH", "Research designs (quantitative vs qualitative), sampling techniques, ethical approval, data collection.", listOf("Randomized Controlled Trial", "Purposive Sampling", "Informed Consent", "Data Collection Tools"))
                )
            ),
            KpSemesterInfo(
                semesterNumber = 7,
                title = "Semester 7 (BSN Fourth Year)",
                description = "Critical care ICU management, nursing leadership, epidemiology & research project.",
                totalCreditHours = 16,
                subjects = listOf(
                    KpSemesterSubject("CCN-671", "Critical Care Nursing", "5 CH", "Mechanical ventilation, arterial blood gas analysis, shock management, cardiac monitoring, ACLS.", listOf("Mechanical Vent Modes", "ABG Interpretation", "Hypovolemic vs Septic Shock", "ACLS Cardiac Arrest")),
                    KpSemesterSubject("MGT-672", "Leadership & Management in Nursing", "4 CH", "Leadership styles, conflict resolution, staffing, budgeting, quality assurance, delegation rules.", listOf("Transformational Leadership", "5 Rights of Delegation", "Root Cause Analysis", "Conflict Management")),
                    KpSemesterSubject("EPI-673", "Epidemiology & Public Health Nursing", "4 CH", "Incidence, prevalence, odds ratio, relative risk, outbreak investigation, screening & cold chain.", listOf("Incidence vs Prevalence", "EPI Vaccines & Cold Chain", "Vector-Borne Disease Control", "Odds Ratio & Relative Risk")),
                    KpSemesterSubject("RES-674", "Nursing Research Project & Biostatistics", "3 CH", "Execution of clinical research study, data analysis, statistical tests (t-test, ANOVA), APA style.", listOf("Parametric vs Non-Parametric", "t-test & Chi-Square", "APA Referencing", "Qualitative Trustworthiness"))
                )
            ),
            KpSemesterInfo(
                semesterNumber = 8,
                title = "Semester 8 (BSN Fourth Year)",
                description = "Senior clinical internship practicum, community health III, economics, disaster care & current nursing trends.",
                totalCreditHours = 16,
                subjects = listOf(
                    KpSemesterSubject("CHN-681", "Community Health Nursing III", "4 CH", "Primary Health Care, SDGs, IMNCI guidelines, water sanitation, occupational health & health policy.", listOf("Primary Health Care Principles", "SDG 3 Goals", "IMNCI Protocols", "Occupational Health Hazards")),
                    KpSemesterSubject("SEM-682", "Nursing Seminar & Contemporary Issues", "4 CH", "PNC regulations, evidence-based practice, bioethics, palliative care, health informatics & professional identity.", listOf("Evidence-Based Practice", "PNC Code & Licensure", "Palliative Care & Euthanasia", "Benner's Novice to Expert")),
                    KpSemesterSubject("ECO-683", "Health Care Economics & Policy", "4 CH", "Healthcare financing, Sehat Sahulat Card KP, insurance moral hazard, cost-effectiveness analysis & policy cycle.", listOf("Sehat Card Plus KP", "Cost-Effectiveness & QALY", "Fee-for-Service vs Capitation", "Policy Making Cycle")),
                    KpSemesterSubject("DIS-684", "Professional Elective / Disaster Management", "4 CH", "Disaster management cycle, START triage algorithm, mass casualty response, CBRN decontamination & Rescue 1122.", listOf("START Triage Algorithm", "Mass Casualty Incident", "Disaster Management Cycle", "CBRN Decontamination"))
                )
            )
        )
    }

    fun getSemesterQuestions(): List<KpSemesterQuestion> {
        val questions = mutableListOf<KpSemesterQuestion>()

        // 700+ Questions for Semester 1 (710 Qs)
        questions.addAll(KpSemester1QuestionBank.getQuestions(1001))

        // 700+ Questions for Semester 2 (710 Qs)
        questions.addAll(KpSemester2QuestionBank.getQuestions(2001))

        // 700+ Questions for Semester 3 (710 Qs)
        questions.addAll(KpSemester3QuestionBank.getQuestions(3001))

        // 750+ Questions for Semester 4 (150+ Qs per subject)
        questions.addAll(KpSemester4QuestionBank.getQuestions(4001))

        // 750+ Questions for Semester 5 (150+ Qs per subject)
        questions.addAll(KpSemester5QuestionBank.getQuestions(5001))

        // 600+ Questions for Semester 6 (150+ Qs per subject)
        questions.addAll(KpSemester6QuestionBank.getQuestions(6001))

        // 600+ Questions for Semester 7 (150+ Qs per subject)
        questions.addAll(KpSemester7QuestionBank.getQuestions(7001))

        // 600+ Questions for Semester 8 (150+ Qs per subject)
        questions.addAll(KpSemester8QuestionBank.getQuestions(8001))

        return questions
    }
}
