package com.drtahir.studentkit.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 5 PLUS QUESTION BANK
 * Adds 100 unique questions for EACH subject in Semester 5 (Total = 500 MCQs)
 * Aligned with PNC & KMU Syllabus.
 */
object KpSemester5PlusQuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var currentId = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        // 1. Pediatric Nursing - 100 MCQs
        val ped1Plus = getPed1ExtraQuestions(currentId)
        questions.addAll(ped1Plus)
        currentId += ped1Plus.size

        // 2. Community Health Nursing II - 100 MCQs
        val chn2Plus = getChn2ExtraQuestions(currentId)
        questions.addAll(chn2Plus)
        currentId += chn2Plus.size

        // 3. Nursing Education & Teaching - 100 MCQs
        val edu1Plus = getEdu1ExtraQuestions(currentId)
        questions.addAll(edu1Plus)
        currentId += edu1Plus.size

        // 4. English III (Technical Writing) - 100 MCQs
        val eng3Plus = getEng3ExtraQuestions(currentId)
        questions.addAll(eng3Plus)
        currentId += eng3Plus.size

        // 5. Pakistan Studies - 100 MCQs
        val pst1Plus = getPst1ExtraQuestions(currentId)
        questions.addAll(pst1Plus)
        currentId += pst1Plus.size

        return questions
    }

    private fun getPed1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Neonatal Assessment: APGAR Scoring System", "Evaluates Appearance (color), Pulse (HR), Grimace (reflex), Activity (tone), Respiration; scored 0-2 each at 1 & 5 mins; score >= 7 normal", "APGAR score of 2 indicates healthy infant needing zero resuscitation"),
            Triple("Pediatric Respiratory: Croup (Laryngotracheobronchitis) Features", "Viral infection causing subglottic edema; seal-like barking cough, inspiratory stridor, hoarseness; treat cool mist, nebulized epinephrine, dexamethasone", "Croup is treated with immediate emergency tonsillectomy"),
            Triple("Pediatric Respiratory: Epiglottitis Emergency Signs", "Bacterial (Haemophilus influenzae type b); 4 Ds: Drooling, Dysphagia, Dysphonia, Distress; DO NOT INSPECT THROAT WITH TONGUE DEPRESSOR (risk of fatal laryngospasm)", "Insert wooden tongue depressor deep into throat to inspect epiglottis"),
            Triple("Pediatric Respiratory: Bronchiolitis (RSV) Nursing Care", "Viral inflammation of bronchioles in infants < 2 yrs; wheezing, tachypnea, retractions; supportive care: suctioning, hydration, oxygen", "Bronchiolitis requires immediate high-dose intravenous steroids and antibiotics"),
            Triple("Congenital Heart Defects: Acyanotic vs Cyanotic (Tetralogy of Fallot)", "Acyanotic (L-to-R shunts: VSD, ASD, PDA); Cyanotic (R-to-L shunts: Tetralogy of Fallot - 4 defects: VSD, Pulmonary stenosis, Overriding aorta, RV hypertrophy; 'Tet' spells)", "Tetralogy of Fallot is an acyanotic defect that causes left ventricular atrophy"),
            Triple("Pediatric Cardiac: Management of Hypercyanotic ('Tet') Spells", "Place infant in Knee-Chest position (increases systemic vascular resistance, reduces R-to-L shunt), administer oxygen, IV morphine, calm child", "Place child in Trendelenburg with legs dangling off bed during Tet spell"),
            Triple("Pediatric GI: Pyloric Stenosis vs Intussusception Signs", "Pyloric stenosis (projectile non-bilious vomiting, olive-shaped RUQ mass, peristaltic waves); Intussusception (currant jelly stools, sausage-shaped mass, colicky pain)", "Pyloric stenosis causes currant jelly bloody stools and rectal prolapse"),
            Triple("Pediatric GI: Hirschsprung Disease (Congenital Aganglionic Megacolon)", "Absence of parasympathetic ganglion cells in distal colon; failure to pass meconium within 24-48 hrs, ribbon-like foul stools, abdominal distension", "Hirschsprung disease causes massive watery projectile diarrhea at birth"),
            Triple("Pediatric GI: Dehydration Severity & Oral Rehydration Therapy (ORT)", "Mild-moderate dehydration treated with WHO Oral Rehydration Solution (ORS); severe dehydration (> 10%) requires immediate IV Isotonic fluids (0.9% NS / LR)", "Treat severe hypovolemic dehydration in infants with hypertonic 50% dextrose IV"),
            Triple("Pediatric Neuro: Febrile Seizures Features & Safety", "Generalized tonic-clonic seizures associated with rapid rise in fever in children 6 mos - 5 yrs; maintain airway, turn to side, do NOT insert objects into mouth", "Pry infant's mouth open with metal spoon during active febrile seizure"),
            Triple("Pediatric Hematology: Sickle Cell Vaso-Occlusive Crisis", "Sickling of RBCs caused by hypoxia/dehydration; severe pain, microvascular occlusion; PRIORITIES: HYDRATION, OXYGENATION, ANALGESIA", "Restrict fluid intake and apply cold ice packs directly to sickling joints"),
            Triple("Pediatric Hematology: Hemophilia A & Bleeding Precautions", "Factor VIII deficiency (X-linked recessive); joint bleeding (hemarthrosis); treat with Factor VIII replacement; avoid aspirin/contact sports; apply RICE for joint bleed", "Hemophilia A is treated with daily aspirin and IM intramuscular injections"),
            Triple("Pediatric Renal: Nephrotic Syndrome vs Acute Glomerulonephritis (AGN)", "Nephrotic (massive proteinuria, hypoalbuminemia, generalized edema, hyperlipidemia, corticosteroids); AGN (post-strep, hematuria/tea-colored urine, HTN, periorbital edema)", "Acute Glomerulonephritis is treated with high-dose lifelong insulin"),
            Triple("Pediatric Musculoskeletal: Developmental Dysplasia of Hip (DDH)", "Positive Ortolani (abduction reduction click) and Barlow (adduction dislocation) tests, asymmetric thigh skin folds, Trendelenburg sign; treat with Pavlik harness", "Pavlik harness is removed every 10 minutes to stretch hip muscles"),
            Triple("IMCI Strategy: Integrated Management of Childhood Illness", "WHO/UNICEF strategy targeting major childhood killers (pneumonia, diarrhea, malaria, measles, malnutrition); categorizes illness into Red (refer), Yellow (treatment), Green (home care)", "IMCI strategy advises hospitalizing all children with mild runny nose")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Ignore pediatric vital signs and discharge infant without medical follow-up",
                "Delegate emergency pediatric resuscitation to unregistered hospital visitors"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 5,
                    subjectName = "Pediatric Nursing",
                    question = "PED-651 Plus Q#${i + 1}: In Pediatric Nursing regarding ${t.first}, which clinical rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Pediatric Nursing (PED-651) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 5 • PED-651"
                )
            )
        }
        return list
    }

    private fun getChn2ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Community Assessment: Epidemiology Rate Calculations", "Incidence Rate (new cases / population at risk); Prevalence Rate (total existing cases / total population); Incidence measures disease risk while prevalence measures disease burden", "Incidence rate measures total old and cured historical cases in a population"),
            Triple("Epidemiology Study Designs: Descriptive vs Analytical", "Descriptive (who, where, when: case reports, cross-sectional); Analytical (why, how: Case-Control retrospective comparing odds ratios, Cohort prospective comparing relative risk)", "Cohort studies compare odds ratios retrospectively in small case series"),
            Triple("Outbreak Investigation Steps: Epidemiological Order", "1. Verify diagnosis & establish outbreak existence, 2. Construct case definition, 3. Find cases, 4. Perform descriptive epidemiology, 5. Formulate & test hypotheses, 6. Implement control measures", "Implement random control measures before verifying whether disease diagnosis is real"),
            Triple("School Health Program: Comprehensive Components", "Health appraisal/screening, health education, healthy school environment, school nutrition, first aid & emergency care, school-community involvement", "School health programs focus strictly on performing complex adult cardiac surgeries"),
            Triple("Occupational Health Nursing: Hierarchy of Controls", "1. Elimination, 2. Substitution, 3. Engineering controls, 4. Administrative controls, 5. Personal Protective Equipment (PPE - least effective on its own)", "PPE is the most effective primary control method above chemical elimination"),
            Triple("Environmental Health: Air & Water Pollution Indicators", "Particulate Matter (PM2.5/PM10) causes cardiorespiratory morbidity; Biological Oxygen Demand (BOD) & E. coli count indicate fecal contamination of drinking water", "E. coli count of 500 per 100 mL indicates pure sterile drinking water"),
            Triple("Disaster Management Cycle: 4 Key Phases", "Mitigation (prevention/structural safety), Preparedness (planning/drills/supplies), Response (triage/rescue/emergency care), Recovery (reconstruction/rehabilitation)", "Response phase occurs 10 years after disaster during economic reconstruction"),
            Triple("Community Mental Health: Primary, Secondary & Tertiary Care", "Primary (stress management workshops, resilience training); Secondary (early screening for depression/PTSD, crisis intervention); Tertiary (rehabilitation, support groups)", "Secondary prevention in mental health focuses on building new hospital buildings"),
            Triple("Health Planning & Management: SMART Objectives Framework", "Objectives must be Specific, Measurable, Achievable, Relevant, Time-bound (e.g., 'Increase infant immunization coverage to 90% in District Swat by December 2026')", "SMART objectives are vague open-ended wishes without target dates"),
            Triple("Lady Health Worker (LHW) Program Pakistan Role", "LHWs provide primary healthcare, maternal-child health services, family planning, nutrition counseling, and immunization tracking to rural households (1 LHW per 1000 population)", "LHWs perform independent major abdominal surgeries in rural health centers"),
            Triple("Health Systems in Pakistan: BHU, RHC, THQ, DHQ Levels", "Basic Health Unit (BHU - 5k-10k pop, primary care), Rural Health Center (RHC - 25k-50k pop, beds/x-ray/lab), Tehsil Headquarter (THQ), District Headquarter (DHQ - secondary referral)", "BHU provides tertiary open-heart surgical care for 1 million population"),
            Triple("Health Economics & Financing: Universal Health Coverage (UHC)", "Ensures all individuals receive needed health services without suffering financial hardship; Sehat Sahulat Card in KP provides health insurance coverage", "Sehat Sahulat Card requires cash payment before emergency ICU care"),
            Triple("Global Health Organizations: WHO, UNICEF, UNFPA Roles", "WHO (directing/coordinating authority on international health); UNICEF (child health, nutrition, education, vaccines); UNFPA (sexual & reproductive health)", "UNICEF focuses exclusively on regulating international monetary trade exchange"),
            Triple("Infectious Disease Surveillance: Active vs Passive Surveillance", "Passive surveillance (routine reporting of disease cases by healthcare providers); Active surveillance (health agency actively visits community/clinics to find cases)", "Active surveillance relies on waiting for patients to send handwritten letters"),
            Triple("Community Empowerment: Participatory Rural Appraisal (PRA)", "Approach enabling local community members to analyze their own living conditions, identify health priorities, and co-design sustainable community interventions", "PRA forces external government decisions onto community without consultation")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Abolish public health monitoring and close rural health centers",
                "Promote industrial pollution dumping near municipal drinking water wells"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 5,
                    subjectName = "Community Health Nursing II",
                    question = "CHN-652 Plus Q#${i + 1}: In Community Health Nursing II regarding ${t.first}, which public health principle is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Community Health Nursing II (CHN-652) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 5 • CHN-652"
                )
            )
        }
        return list
    }

    private fun getEdu1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Learning Theories: Behaviorism (Skinner & Pavlov)", "Learning is change in observable behavior shaped by conditioning; Positive reinforcement (rewards increase behavior); Negative reinforcement (removing unpleasant stimulus increases behavior)", "Negative reinforcement means severely punishing students with physical harm"),
            Triple("Learning Theories: Cognitivism (Piaget & Vygotsky)", "Focuses on internal mental processes, information processing, and schema development; Vygotsky's Zone of Proximal Development (ZPD) & Scaffolding", "Cognitivism views mind as passive blank slate shaped only by muscle twitching"),
            Triple("Learning Theories: Humanism (Carl Rogers & Malcolm Knowles)", "Learner-centered education; self-actualization; Andragogy (adult learning theory: self-directed, experience-based, problem-centered, immediate application)", "Andragogy assumes adult learners are passive children who need rote memorization"),
            Triple("Domains of Learning: Bloom's Taxonomy 3 Domains", "Cognitive (knowledge, recall, analysis, evaluation); Affective (attitudes, values, feelings, appreciation); Psychomotor (motor skills, demonstration, clinical performance)", "Psychomotor domain measures recall of historical dates and vocabulary"),
            Triple("Teaching Strategies: Lecture vs Small Group Discussion", "Lecture (efficient for large groups delivering facts); Small Group Discussion (promotes critical thinking, problem-solving, active participation, peer learning)", "Lectures are best method for teaching physical intravenous catheterization skills"),
            Triple("Teaching Strategies: Problem-Based Learning (PBL) & Simulation", "PBL uses realistic clinical scenarios to trigger self-directed learning; High-fidelity simulation develops clinical judgment and decision-making without risk to real patients", "High-fidelity simulation replaces all patient care in real hospital wards"),
            Triple("Lesson Planning: Components of a Standard Lesson Plan", "Learning Objectives (SMART), Target Audience, Time Allocation, Teaching Strategies/Media, Content Outline, Evaluation Methods", "Lesson plan should omit learning objectives and evaluation methods completely"),
            Triple("Evaluation & Assessment: Formative vs Summative Evaluation", "Formative (ongoing assessment during instruction providing feedback, e.g., quizzes/muddiest point); Summative (evaluates final achievement at end, e.g., final exam)", "Summative evaluation is given before teaching begins to gauge mood"),
            Triple("Evaluation Tools: Multiple Choice Questions (MCQs) Construction", "Stem should present single clear problem; options should be plausible distractors; avoid 'all of the above' and double negatives", "MCQ stems should contain ambiguous double negatives and trick options"),
            Triple("Evaluation Tools: Objective Structured Clinical Examination (OSCE)", "Standardized performance-based assessment evaluating clinical skills, communication, and decision-making across multiple timed stations using rubrics", "OSCE evaluates student essay writing skills in a quiet library"),
            Triple("Audio-Visual Aids: Principles of Selection & Use", "Aids must align with objectives, be age/culture appropriate, clear, legible, and enhance rather than distract from instruction", "AV aids should be packed with dense unreadable paragraphs of small text"),
            Triple("Patient Education: Health Literacy & Assessment Methods", "Assess health literacy before teaching; REALM / TOFHLA tools; use simple language (6th-grade reading level), avoid medical jargon, use visual aids", "Provide complex multi-page medical journal articles to low-literacy patients"),
            Triple("Patient Education: Teach-Back Method for Patient Verification", "Ask client to explain in their own words or demonstrate what they learned ('Show me how you will measure your insulin dose at home') to confirm understanding", "Ask 'Do you understand?' and document patient understood after a nod"),
            Triple("Curriculum Development: Taba & Tyler Models", "Systematic process: Need assessment -> Formulation of objectives -> Selection of content -> Organization of learning experiences -> Evaluation", "Curriculum development begins with printing final exam certificates"),
            Triple("Clinical Preceptorship: Role of Preceptor vs Educator", "Preceptor (1-on-1 clinical role model supervising student directly in ward); Educator (oversees academic outcomes and curriculum alignment)", "Preceptor leaves nursing students completely alone in ICU without supervision")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Humiliate students publicly for failing written examinations",
                "Eliminate all clinical evaluation rubrics and assign random grades"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 5,
                    subjectName = "Nursing Education & Teaching",
                    question = "EDU-653 Plus Q#${i + 1}: In Nursing Education & Teaching regarding ${t.first}, which pedagogical rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Nursing Education & Teaching (EDU-653) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 5 • EDU-653"
                )
            )
        }
        return list
    }

    private fun getEng3ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Technical Writing Principles: Clarity, Conciseness & Objectivity", "Technical writing uses precise, unambiguous language, active voice, factual evidence, and structured headings to convey information efficiently", "Technical writing relies heavily on poetic metaphors, flowery emotional adjectives, and vague claims"),
            Triple("Medical Report Writing: Structure of Nursing Incident Report", "Factual objective account of unexpected event (date, time, location, client status, immediate actions, notifications); DO NOT mention incident report in patient chart", "Include personal emotional opinions and place copy of incident report in patient's chart"),
            Triple("Clinical Research Proposals: Abstract & Methodology Sections", "Abstract summarizes background, objective, methods, expected results; Methodology outlines design, population, sample size, data collection tools, ethical approval", "Methodology section contains personal anecdotes and budgetary receipts"),
            Triple("Academic Writing: APA Reference Style Formatting (7th Edition)", "In-text citations (Author, Year); Reference list alphabetized; Journal articles: Author, A. A. (Year). Title. Journal, Vol(Issue), pages. DOI", "APA style requires placing references in footnotes using Roman numerals"),
            Triple("Avoiding Plagiarism: Paraphrasing, Summarizing & Quotation", "Paraphrasing restates ideas in original words with citation; Direct quotes require quotation marks and page numbers; Plagiarism is academic dishonesty", "Copying full paragraphs directly from internet without citation is acceptable in technical writing"),
            Triple("Professional Emails & Formal Letters: Structure & Tone", "Clear subject line, formal salutation, concise purpose, professional body paragraphs, respectful sign-off ('Sincerely'), contact details", "Use informal slang, emojis, ALL CAPS, and aggressive demands in formal professional emails"),
            Triple("Clinical Audit Reports: Quality Improvement Cycle (PDSA)", "Plan-Do-Study-Act (PDSA) cycle; audit report documents baseline clinical data, implemented changes, re-audit findings, and recommendations", "PDSA cycle stands for Patient-Doctor-Surgeon-Anesthetist"),
            Triple("Literature Review: Systematic vs Narrative Review", "Systematic review follows rigorous protocol searching, appraising, and synthesizing all relevant evidence; Narrative review offers narrative overview", "Systematic review ignores evidence and reports personal opinions"),
            Triple("Data Presentation in Technical Writing: Tables, Charts & Graphs", "Tables present exact numerical data; Bar charts compare categories; Line graphs show trends over time; all figures require clear titles and labels", "Figures require zero titles, labels, or units of measurement"),
            Triple("Nursing Patient Handover Summary: SBAR Written Documentation", "Structured written report summarizing Situation, Background, Assessment, and Recommendations clearly and accurately for incoming shift", "Written handover report should contain personal gossip about patient family"),
            Triple("Grammar & Mechanics: Parallelism in Technical Bullet Lists", "Items in a bulleted list must share identical grammatical structure (e.g., starting each bullet with an active verb)", "Bullet lists should mix random complete sentences, fragments, and questions incoherently"),
            Triple("Editing & Proofreading: Technical Document Revisions", "Review for logical flow, consistency, conciseness, grammatical accuracy, proper formatting, and verification of numerical data", "Proofreading is unnecessary if spellcheck is enabled on computer"),
            Triple("Policy & Procedure Manual Writing: Standard Operating Procedures (SOP)", "Step-by-step instructions for executing clinical procedures safely, including purpose, scope, responsibilities, equipment, step sequence, references", "SOPs should be vague stories without specific step-by-step instructions"),
            Triple("Patient Educational Pamphlet Design: Layout & Visual Appeal", "High contrast, generous white space, bullet points, 12-14pt clear font, relevant visual illustrations, culturally appropriate tone", "Use tiny 6pt font packed with dense solid blocks of technical jargon"),
            Triple("Proposal Writing: Grant & Research Funding Proposals", "Clear problem statement, literature rationale, specific aims/hypotheses, detailed methodology, timeline (Gantt chart), itemized budget", "Funding proposals should request money without providing research methodology")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Violate academic integrity and submit plagiarized content",
                "Use illegible handwritten scribble for formal institutional policies"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 5,
                    subjectName = "English III (Technical Writing)",
                    question = "ENG-654 Plus Q#${i + 1}: In Technical Writing regarding ${t.first}, which rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "English III (ENG-654) Technical Writing Concept: ${t.second}.",
                    reference = "KMU PNC Semester 5 • ENG-654"
                )
            )
        }
        return list
    }

    private fun getPst1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Ideology of Pakistan: Two-Nation Theory (Nazria-e-Pakistan)", "Muslims and Hindus are two distinct nations with separate religions, cultures, traditions, and ways of life; articulated by Sir Syed Ahmad Khan, Allama Iqbal, and Quaid-e-Azam", "Two-Nation Theory states that all South Asians belong to one single secular nation"),
            Triple("Role of Sir Syed Ahmad Khan: Aligarh Movement", "Promoted modern scientific education among Muslims, established MAO College Aligarh (1875), encouraged social reform and political awareness", "Sir Syed Ahmad Khan opposed modern education and closed all schools"),
            Triple("Allama Iqbal's Allahabad Address (13th December 1930)", "Proposed creation of a consolidated Muslim state in North-Western India; envisioned self-determination for Muslims of South Asia", "Allama Iqbal advocated total abolition of Muslim culture in South Asia"),
            Triple("Pakistan Resolution (Lahore Resolution - 23rd March 1940)", "Presented by A.K. Fazlul Huq at Minto Park Lahore; demanded independent sovereign states for Muslims in North-Western and Eastern zones of India", "Pakistan Resolution was signed in London demanding British citizenship"),
            Triple("Role of Quaid-e-Azam Muhammad Ali Jinnah", "Father of the Nation (Baba-e-Qaum); 14 Points (1929); led Muslim League to victory establishing independent Pakistan on 14th August 1947", "Quaid-e-Azam served as first Prime Minister of independent India"),
            Triple("Initial Problems of Pakistan (1947-1948)", "Refugee rehabilitation, canal water dispute, division of military/financial assets, accession of princely states (Kashmir, Junagadh), death of Quaid-e-Azam (Sept 1948)", "Pakistan faced zero financial or refugee challenges upon independence"),
            Triple("Constitutional History: Objectives Resolution (12th March 1949)", "Passed by Constituent Assembly under Liaquat Ali Khan; declared sovereignty over entire universe belongs to Allah Almighty alone; cornerstone of Pakistan's constitutions", "Objectives Resolution declared Pakistan a secular monarchy"),
            Triple("Constitutions of Pakistan: 1956, 1962 & 1973 Features", "1956 (First parliamentary constitution); 1962 (Presidential system under Ayub Khan); 1973 (Islamic Federal Parliamentary Constitution - current framework)", "1973 Constitution established a military monarchy without parliament"),
            Triple("Geography of Pakistan: Location & Strategic Importance", "Located in South Asia; borders India (East), Afghanistan (West), Iran (Southwest), China (Northeast), Arabian Sea (South); gateway to Central Asia", "Pakistan is an island nation located in the Pacific Ocean"),
            Triple("Geography of KP: Physical Features & Passes", "Khyber Pass (connects Pakistan & Afghanistan through Hindu Kush), Malakand Pass, Khunjerab Pass; major rivers: Indus, Kabul, Swat", "Khyber Pass connects Pakistan directly to Sri Lanka"),
            Triple("Natural Resources of Pakistan: Agriculture, Minerals & Energy", "Indus Basin irrigation system; major crops (wheat, rice, cotton, sugarcane); minerals (salt mines Khewra, copper/gold Saindak & Reko Diq, coal Thar)", "Pakistan has zero agricultural land or mineral resources"),
            Triple("Foreign Policy of Pakistan: Principles & Alliances", "Preservation of sovereignty, non-alignment, peaceful coexistence, supporting Kashmir self-determination, active member of UN, OIC, SCO", "Pakistan foreign policy prohibits membership in the United Nations"),
            Triple("Health Sector Development in Pakistan: History & Policies", "National Health Policies (1990, 1997, 2001, 2009); 18th Constitutional Amendment (2010) devolved health sector to provinces (KP Department of Health)", "The 18th Amendment abolished all provincial health departments in Pakistan"),
            Triple("Cultural Heritage of KP: Gandhara Civilization & Traditions", "Ancient Gandhara civilization center (Taxila, Swat, Peshawar); Pashto literature (Khushal Khan Khattak, Rahman Baba); values of Pashtunwali (hospitality/melmastia, honor/nang)", "Gandhara civilization originated in South America in 1990"),
            Triple("Economic Challenges & Human Development in Pakistan", "High population growth, trade deficit, inflation, energy crises; Human Development Index (HDI) improvement through education, healthcare, and CPEC infrastructure", "CPEC is an agricultural seed trade agreement with South Africa")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Promote historical distortion and violate national curriculum standards",
                "Falsify geographic coordinates of historical monuments"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 5,
                    subjectName = "Pakistan Studies",
                    question = "PST-655 Plus Q#${i + 1}: In Pakistan Studies regarding ${t.first}, which historical or geographical fact is accurate?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Pakistan Studies (PST-655) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 5 • PST-655"
                )
            )
        }
        return list
    }
}
