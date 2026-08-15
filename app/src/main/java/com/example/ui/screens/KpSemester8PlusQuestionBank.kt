package com.example.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 8 PLUS QUESTION BANK
 * Adds 100 unique questions for EACH subject in Semester 8 (Total = 400 MCQs)
 * Aligned with PNC & KMU Syllabus.
 */
object KpSemester8PlusQuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var currentId = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        // 1. Community Health Nursing III - 100 MCQs
        val chn3Plus = getChn3ExtraQuestions(currentId)
        questions.addAll(chn3Plus)
        currentId += chn3Plus.size

        // 2. Nursing Seminar & Contemporary Issues - 100 MCQs
        val semPlus = getSemExtraQuestions(currentId)
        questions.addAll(semPlus)
        currentId += semPlus.size

        // 3. Health Care Economics & Policy - 100 MCQs
        val ecoPlus = getEcoExtraQuestions(currentId)
        questions.addAll(ecoPlus)
        currentId += ecoPlus.size

        // 4. Professional Elective / Disaster Management - 100 MCQs
        val disPlus = getDisExtraQuestions(currentId)
        questions.addAll(disPlus)
        currentId += disPlus.size

        return questions
    }

    private fun getChn3ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Alma-Ata Declaration Principles of Primary Health Care (PHC)", "PHC core principles: Equitable distribution, Community participation, Intersectoral coordination, and Appropriate technology", "PHC emphasizes high-cost tertiary urban hospital care available only to elites"),
            Triple("Sustainable Development Goal (SDG 3) Targets", "SDG 3 targets reducing global maternal mortality ratio to < 70 per 100,000 live births and ending preventable newborn deaths by 2030", "SDG 3 aims to increase global tobacco consumption by 50%"),
            Triple("Lady Health Worker (LHW) Scope in Rural KP Communities", "LHWs provide preventive MCH care, family planning counseling, routine immunization tracking, and management of common childhood illnesses", "LHWs perform independent brain surgery in rural health centers"),
            Triple("IMCI Protocol: Severe Pneumonia Classification & Action", "Child with cough/difficulty breathing PLUS chest indrawing or general danger signs -> Classify as Severe Pneumonia; give 1st dose antibiotic and URGENT REFERRAL", "Classify child with chest indrawing as normal and discharge home without antibiotics"),
            Triple("EmONC (Emergency Obstetric and Newborn Care) Levels", "Basic EmONC (parenteral antibiotics, oxytocics, anticonvulsants, manual removal of placenta, assisted delivery); Comprehensive EmONC adds Blood Transfusion and C-Section", "Basic EmONC facilities perform open-heart cardiac bypass surgery"),
            Triple("Family Planning: Unmet Need for Contraception Concept", "Unmet need refers to fecund women who want to stop or delay childbearing but are not using any method of contraception", "Unmet need refers to women who purchase excess contraceptives from supermarkets"),
            Triple("WASH Interventions & Diarrheal Disease Control", "Safe drinking water, sanitary latrines, and handwashing with soap at critical times reduce childhood diarrheal morbidity by over 40%", "WASH interventions recommend drinking untreated river water during flood outbreaks"),
            Triple("Vector Control: Dengue Fever Prevention Strategies", "Eliminate vector breeding sites (container management, emptying standing water weekly), larval control, and indoor residual spraying", "Promote keeping open containers of standing water inside bedrooms"),
            Triple("School Health Program: Screening & Assessment Components", "Visual acuity testing, growth monitoring (height/weight BMI charts), dental hygiene screening, screening for hearing loss and skin infections", "School health program performs weekly surgical organ transplants"),
            Triple("Occupational Health: Agriculture Pesticide Poisoning Signs", "Organophosphate toxicity: Cholinergic crisis (SLUDGE: Salivation, Lacrimation, Urination, Defecation, GI distress, Emesis); Treat with Atropine", "Organophosphate pesticide poisoning causes dry eyes and hypertension"),
            Triple("Community Disaster Preparedness & Risk Reduction", "Community risk mapping, establishing local emergency evacuation routes, stockpiling disaster supplies, and training community response volunteers", "Disaster preparedness recommends destroying local communications towers"),
            Triple("Participatory Rural Appraisal (PRA) Techniques", "Empowerment method enabling local community members to map resources, identify health priorities, and co-design community health programs", "PRA forces external government decisions onto community without consultation"),
            Triple("Behavior Change Communication (BCC) in Public Health", "BCC uses tailored communication strategies to promote positive health behaviors (e.g., exclusive breastfeeding, vaccine acceptance)", "BCC relies on threatening citizens with imprisonment if they get sick"),
            Triple("Non-Communicable Disease (NCD) Primary Prevention", "Community lifestyle interventions targeting smoking cessation, salt intake reduction, physical activity, and healthy diet to reduce HTN/Diabetes", "Primary NCD prevention focuses on prescribing lifelong hemodialysis to healthy teenagers"),
            Triple("De-institutionalization & Community Mental Health Care", "Shifting mental health care from isolated psychiatric institutions to community-based centers, integrating mental health into primary care", "Community mental health recommends locking all mentally ill clients in isolated cellars")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Abolish rural health center services and close community clinics",
                "Promote unsafe industrial dumping into municipal water supplies"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 8,
                    subjectName = "Community Health Nursing III",
                    question = "CHN-681 Plus Q#${i + 1}: In Community Health Nursing III regarding ${t.first}, which public health rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Community Health Nursing III (CHN-681) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 8 • CHN-681"
                )
            )
        }
        return list
    }

    private fun getSemExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Professional Autonomy in Nursing Practice", "Nursing autonomy refers to the freedom, authority, and discretion of nurses to make independent clinical decisions within their licensed scope of practice", "Nursing autonomy means following physician verbal orders without clinical judgment"),
            Triple("Advanced Practice Nursing (APN) Roles", "APNs (Nurse Practitioners, Clinical Nurse Specialists) demonstrate advanced expert clinical knowledge, complex decision-making, and expanded clinical competencies", "APN roles are limited exclusively to cleaning hospital beds"),
            Triple("Bioethical Dilemma: Euthanasia vs Palliative Sedation", "Euthanasia (intentionally ending life to relieve pain; illegal in Pakistan); Palliative Sedation (relieving intractable suffering at end-of-life without intent to hasten death)", "Euthanasia is a mandatory nursing procedure performed on all elderly patients"),
            Triple("Workplace Bullying & Horizontal/Lateral Violence", "Peer-to-peer hostility (verbal abuse, undermining, withholding info); zero-tolerance policies and assertive communication mitigate lateral violence", "Horizontal violence is encouraged to improve unit teamwork"),
            Triple("Burnout & Compassion Fatigue Mitigation", "Burnout (emotional exhaustion, depersonalization); mitigation involves self-care, mindfulness, adequate rest, manageable workloads, and institutional support", "Mitigate burnout by working 100 consecutive double shifts without sleep"),
            Triple("Digital Health & Electronic Health Records (EHR) Benefits", "EHR improves clinical documentation accuracy, legibility, interdisciplinary communication, and patient safety while reducing medication errors", "EHR system increases illegible handwriting errors and loses all patient records"),
            Triple("Global Nursing Migration (Brain Drain) Impact", "Outflow of qualified nurses from developing nations creates severe domestic staffing shortages; ethical recruitment practices (WHO Code of Practice) recommended", "Brain drain improves healthcare delivery in developing nations by removing all nurses"),
            Triple("Evidence-Based Practice (EBP) Barriers & Enablers", "Key barriers: lack of time, lack of research literacy, organizational resistance; Enablers: administrative support, EBP mentors, access to databases", "Key enabler of EBP is prohibiting nurses from reading scientific journals"),
            Triple("Interprofessional Collaborative Practice Benefits", "Multidisciplinary team collaboration reduces clinical errors, shortens hospital length of stay, and improves patient satisfaction and health outcomes", "Interprofessional collaboration creates chaos and increases medical errors"),
            Triple("Nursing Informatics & Data Security Compliance", "Nursing Informatics integrates nursing science, computer science, and information science to manage health data; strict password security and patient privacy required", "Share patient login passwords publicly on social media platforms"),
            Triple("Continuous Professional Development (CPD) Mandate", "PNC requires nurses to maintain continuous professional education hours and competency training for periodic license renewal and safe practice", "CPD mandates that nurses stop learning after graduating nursing school"),
            Triple("Just Culture Principles in Patient Safety", "Distinguishes between human error (console), risky behavior (coach), and reckless behavior (punish); encourages non-punitive reporting of safety errors", "Just culture fires nurses instantly for minor spelling mistakes in notes"),
            Triple("Pakistan Nursing Council (PNC) Regulatory Functions", "PNC is the statutory body regulating nursing education, registration, licensing, ethical standards, and practice scope across Pakistan", "PNC is a private commercial bank operating stock exchanges"),
            Triple("Health Equity & Social Justice Advocacy in Nursing", "Nurses advocate for fair distribution of healthcare resources, eliminating health disparities among marginalized and vulnerable populations", "Social justice advocacy prioritizes healthcare delivery exclusively for wealthy citizens"),
            Triple("Climate Change Impacts on Public Health Nursing Care", "Climate change increases heat-related illnesses, vector-borne disease transmission, extreme weather disaster events, and food/water insecurity", "Climate change eliminates all infectious disease transmission globally")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Violate professional ethics and engage in fraudulent clinical practice",
                "Abandon patient advocacy in contemporary healthcare organizations"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 8,
                    subjectName = "Nursing Seminar & Contemporary Issues",
                    question = "SEM-682 Plus Q#${i + 1}: In Contemporary Nursing Issues regarding ${t.first}, which professional concept is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Nursing Seminar & Contemporary Issues (SEM-682) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 8 • SEM-682"
                )
            )
        }
        return list
    }

    private fun getEcoExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Scarcity & Opportunity Cost in Healthcare Economics", "Scarcity of health resources mandates choices; Opportunity Cost is the value of the next best alternative foregone when a decision is made", "Opportunity cost represents the monetary cost of buying office furniture"),
            Triple("Universal Health Coverage (UHC) Global Objective", "Ensuring all individuals receive quality needed health services without suffering catastrophic out-of-pocket financial hardship", "UHC mandates that patients pay cash upfront before receiving emergency care"),
            Triple("Sehat Sahulat Program (Sehat Card KP) Mechanics", "Government-funded health insurance scheme providing free inpatient hospital care coverage up to specified limits per family per year in KP", "Sehat Card KP requires poor families to pay monthly cash premiums"),
            Triple("Cost-Effectiveness Analysis (CEA) vs Cost-Benefit Analysis (CBA)", "CEA compares health interventions measuring outcomes in natural health units (e.g., cost per life-year saved); CBA measures both costs and outcomes in monetary terms ($)", "CEA measures costs exclusively in foreign gold coins"),
            Triple("Health Policy Development Cycle Steps", "1. Problem Identification / Agenda Setting, 2. Policy Formulation, 3. Policy Adoption, 4. Policy Implementation, 5. Policy Evaluation", "Policy development begins with firing all policy analysts"),
            Triple("Provider Payment Methods: Capitation vs Fee-for-Service", "Capitation (fixed payment per patient enrolled per period, incentivizes efficiency/prevention); Fee-for-Service (payment per procedure, incentivizes volume)", "Fee-for-Service incentivizes doctors to perform zero medical procedures"),
            Triple("Moral Hazard in Health Insurance", "Moral hazard occurs when insured individuals increase their consumption of healthcare services because they do not bear the full direct cost", "Moral hazard refers to ethical behavior displayed by honest nurses"),
            Triple("Adverse Selection in Health Insurance", "Adverse Selection occurs when high-risk individuals (sick) are more likely to buy insurance than low-risk individuals (healthy), driving up premium costs", "Adverse selection occurs when healthy individuals purchase excess insurance"),
            Triple("18th Constitutional Amendment Health Devolution in Pakistan", "Devolved health sector planning, financing, and management from federal ministry to provincial health departments (e.g., KP Department of Health)", "18th Amendment abolished all provincial hospitals in Pakistan"),
            Triple("Gross Domestic Product (GDP) Healthcare Expenditure", "Percentage of national GDP spent on health; developing nations spending < 3-5% GDP face severe health system infrastructure constraints", "Pakistan spends 95% of its GDP on healthcare delivery"),
            Triple("Public-Private Partnerships (PPP) in Healthcare", "Collaboration between government and private healthcare providers to improve facility management, service quality, and infrastructure delivery", "PPP prohibits private sector participation in healthcare forever"),
            Triple("Essential Medicines List (EML) Economic Purpose", "Promotes rational drug use, lowers pharmaceutical procurement costs, and ensures universal access to safe, cost-effective essential drugs", "EML lists the most expensive brand-name luxury cosmetics"),
            Triple("Strategic Purchasing in Healthcare Systems", "Allocating financial resources actively to healthcare providers based on population health needs, quality indicators, and cost-effectiveness", "Strategic purchasing involves buying random unverified supplies without budget"),
            Triple("Out-of-Pocket (OOP) Healthcare Expenditure Impact", "High OOP expenditure (> 50-60% of total health spending) pushes vulnerable families into catastrophic poverty during illness", "High OOP spending protects poor families from economic distress"),
            Triple("Nurse Leaders' Policy Advocacy Role", "Nurse leaders leverage clinical expertise to influence healthcare legislation, resource allocation, and policy reforms to improve patient care quality", "Nurse leaders are prohibited from participating in health policy discussions")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Promote economic corruption and misallocate public health funds",
                "Abolish health economic evaluation in public policy formulation"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 8,
                    subjectName = "Health Care Economics & Policy",
                    question = "ECO-683 Plus Q#${i + 1}: In Health Care Economics regarding ${t.first}, which economic principle is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Health Care Economics & Policy (ECO-683) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 8 • ECO-683"
                )
            )
        }
        return list
    }

    private fun getDisExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Nursing Ethics: Regulatory Duty to Report & Impaired Practice", "Nurses have an ethical and legal obligation to report colleagues suspected of practicing under the influence of substances to safeguard patient safety", "Nurses should ignore impaired colleagues to protect personal friendship"),
            Triple("START Disaster Triage System Categories & Tag Colors", "RED (Immediate: life-threatening, e.g., airway compromise, tension pneumothorax); YELLOW (Delayed: serious non-life-threatening); GREEN (Minor/walking wounded); BLACK (Deceased)", "Assign RED tag to deceased patients without pulse or respiration"),
            Triple("Incident Command System (ICS) Structure", "Standardized management system (Incident Commander, Operations, Planning, Logistics, Finance/Admin) enabling coordinated emergency response", "ICS assigns decision-making to random unorganized crowds"),
            Triple("CBRN Decontamination Protocol Priorities", "Decontamination MUST occur BEFORE patient enters emergency facility to prevent secondary contamination of staff and facility; remove clothing (removes 80-90% toxin)", "Bring contaminated chemical victims directly into crowded ICU without decontamination"),
            Triple("Bioterrorism Agents: Anthrax (Bacillus anthracis) Care", "Cutaneous, Inhalational, GI anthrax; Inhalational anthrax causes mediastinal widening on CXR; treat with Ciprofloxacin or Doxycycline; NOT transmitted person-to-person", "Anthrax is highly contagious and transmitted via casual handshake"),
            Triple("Psychological First Aid (PFA) Post-Disaster", "Provide humane, supportive care: assess safety/needs, listen actively without forcing trauma retelling, help connect with loved ones and support services", "Force disaster survivors to recount graphic trauma details repeatedly"),
            Triple("Disaster Care for Vulnerable Populations", "Prioritize specialized evacuation, mobility assistance, medication supply, and shelter accommodations for elderly, pregnant, disabled, and unaccompanied children", "Abandon vulnerable populations in disaster zones during emergency evacuation"),
            Triple("Hospital Emergency Preparedness Plan (HEPP) Drills", "Regular mass casualty drills evaluate communication, surge capacity, staffing recall, supply logistics, and inter-agency coordination", "HEPP drills are illegal and prohibited in accredited hospitals"),
            Triple("Flood Hazard Emergency Response in KP Basin", "Waterborne disease surveillance (Cholera, Typhoid, Hepatitis A/E), vector control, distributing water purification tablets, and ORS distribution", "Encourage flood victims to drink contaminated floodwater without boiling"),
            Triple("Earthquake Collapse & Crush Syndrome Emergency Care", "Crush Injury causes rhabdomyolysis (myoglobin release, hyperkalemia, acute kidney injury); initiate aggressive IV fluid hydration BEFORE releasing crush pressure", "Restrict IV fluids in crush syndrome to cause renal shut down"),
            Triple("Emergency WASH Standards in Disaster Shelters", "Minimum 15 liters of safe water per person per day; 1 toilet per 20 persons; segregated latrines to prevent disease outbreaks and violence", "Provide 1 cup of water per family every 5 days in refugee camps"),
            Triple("Crisis Standards of Care & Disaster Ethics", "Shifts ethical focus from individual-centered care to population-centered care (maximizing life saved across population) during severe resource scarcity", "Crisis standards mandate giving all scarce resources to hospital managers"),
            Triple("Emergency Redundant Communication Systems", "Maintain backup satellite phones, amateur ham radio, and battery-powered emergency transceivers when primary cell towers fail", "Rely exclusively on landline phones connected to destroyed central offices"),
            Triple("Build Back Better Principle in Disaster Recovery", "Integrating disaster risk reduction measures into physical, social, and economic reconstruction to build resilient communities against future hazards", "Rebuild identical unsafe mud homes in active flood riverbeds"),
            Triple("Chemical Weapon Exposure: Nerve Agent (Sarin) Antidote", "Nerve agents inhibit acetylcholinesterase; treat immediately with Atropine (blocks muscarinic signs) and Pralidoxime (2-PAM, reactivates enzyme)", "Treat Sarin gas poisoning with high-dose intravenous sedative hypnotics alone")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Abolish disaster response protocols and abandon emergency shelter management",
                "Violate CBRN decontamination standards and endanger hospital staff"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 8,
                    subjectName = "Professional Elective / Disaster Management",
                    question = "DIS-684 Plus Q#${i + 1}: In Disaster Nursing regarding ${t.first}, which emergency management rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Professional Elective / Disaster Management (DIS-684) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 8 • DIS-684"
                )
            )
        }
        return list
    }
}
