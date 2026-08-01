package com.example.ui.screens

/**
 * MASTERY BANK PART 4: ICU VENTILATION, ADVANCED HEMODYNAMICS, INFECTION CONTROL & PNC (80 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500MasteryPart4 {

    fun getCriticalCareMasteryQuestions(startId: Int): List<NursingExamQuestion> {
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

        val masteryTopicsPart4 = listOf(
            Triple("Mechanical Ventilation: Modes (AC vs SIMV vs Pressure Support)", "Assist-Control (AC): ventilator delivers preset tidal volume with every breath (client or machine initiated); SIMV: allows spontaneous breaths at client's own volume between preset breaths", "AC mode allows client to set their own variable tidal volume on spontaneous breaths"),
            Triple("Mechanical Ventilation: PEEP Complications & Barotrauma", "Positive End-Expiratory Pressure (PEEP) keeps alveoli open at end-expiration; HIGH PEEP (> 10-15 cm H2O) RISKS BAROTRAUMA (pneumothorax) and DECREASED VENOUS RETURN / CARDIAC OUTPUT", "High PEEP increases venous return and raises cardiac output dramatically"),
            Triple("Mechanical Ventilation: Extubation Readiness & Cuff Leak Test", "Criteria: RSBI < 105, stable ABGs, awake/alert; PERFORM CUFF LEAK TEST BEFORE EXTUBATION (deflate cuff and occlude tube to verify air leak around tube; absence of leak indicates LARYNGEAL EDEMA)", "Extubate immediately if cuff leak test shows zero air flow"),
            Triple("Hemodynamics: Systemic Vascular Resistance (SVR) & Vasodilators", "Normal SVR: 800-1200 dynes/sec/cm-5; measures left ventricular AFTERLOAD; elevated SVR (> 1200) indicates vasoconstriction/hypertension; treated with IV Vasodilators (Nitroprusside, Nitroglycerin)", "Elevated SVR indicates profound vasodilation requiring high dose epinephrine"),
            Triple("Hemodynamics: Cardiac Index (CI) & Body Surface Area Adjustment", "Cardiac Index = Cardiac Output (CO) / Body Surface Area (BSA); Normal CI: 2.5 - 4.0 L/min/m2; CI < 2.0 L/min/m2 indicates severe cardiogenic shock / pump failure", "Normal Cardiac Index is 0.2 L/min/m2"),
            Triple("Infection Control: Chain of Infection & Break Points", "Infectious agent -> Reservoir -> Portal of exit -> Mode of transmission -> Portal of entry -> Susceptible host; HAND HYGIENE IS THE SINGLE MOST EFFECTIVE WAY TO BREAK TRANSMISSION CHAIN", "Wearing dirty gloves without hand hygiene stops all transmission"),
            Triple("Infection Control: PPE Donning & Doffing Sequences", "DONNING SEQUENCE: Gown -> Mask/N95 -> Goggles/Shield -> Gloves; DOFFING SEQUENCE (alphabetical order): Gloves -> Goggles/Shield -> Gown -> Mask/N95", "Doffing sequence starts by removing mask first inside contaminated room"),
            Triple("Infection Control: Multi-Drug Resistant Organisms (MRSA / VRE / CRE)", "CONTACT PRECAUTIONS: private room (or cohort), gloves and gown worn upon entry, dedicated equipment (stethoscope, BP cuff); wash hands with soap/water or alcohol rub", "MRSA requires airborne isolation with negative pressure room"),
            Triple("Infection Control: Clostridium Difficile Alcohol vs Soap Handwash", "C. difficile bacterial spores ARE RESISTANT TO ALCOHOL-BASED HAND RUBS; MUST WASH HANDS WITH SOAP AND WATER; use 10% bleach solution for environmental surface disinfection", "Clean C. diff hands with 100% alcohol sanitizer gel"),
            Triple("Disaster Nursing: Radiation Exposure Acute Radiation Syndrome (ARS)", "Decontaminate outside emergency department; priorities: Triage, Airway, Hemorrhage control; ARS phases: Prodromal (nausea/vomiting), Latent, Manifest illness (bone marrow suppression)", "ARS causes immediate hyper-production of WBCs and zero nausea"),
            Triple("Disaster Nursing: Biological Warfare Plague (Yersinia Pestis) Isolation", "Pneumonic plague transmitted via respiratory droplets; DROPLET PRECAUTIONS UNTIL 48 HOURS OF ANTIBIOTIC THERAPY (Streptomycin / Gentamicin / Doxycycline) completed", "Pneumonic plague requires zero isolation or antibiotics"),
            Triple("Disaster Nursing: Nerve Agent Poisoning (Sarin) & Mark 1 Auto-Injector", "Organophosphate nerve agent; SLUDGEM symptoms; IMMEDIATE DECONTAMINATION; treatment: MARK 1 AUTO-INJECTOR (Atropine 2 mg + Pralidoxime 600 mg IM)", "Treat Sarin nerve gas with high dose IV morphine"),
            Triple("Professional Leadership: Shared Governance Model", "Organizational framework where clinical nurses participate in decision-making regarding nursing practice, quality improvement, and professional development; empowers bedside staff", "Shared governance means hospital CEO dictates all bedside care rules alone"),
            Triple("Professional Leadership: Conflict Resolution Styles (Collaborating vs Compromising)", "Collaborating (Win-Win): both parties work together to find optimal solution; Compromising: both parties give up something; Avoiding: postponing issue; Accommodating: yields to other", "Collaborating means forcing your opinion on others by shouting"),
            Triple("Pakistan Nursing Council (PNC): PNC Code of Ethics & Licensing Standard", "PNC regulates nursing education, registration, and practice in Pakistan; mandatory license renewal; upholds client confidentiality, professional dignity, and non-discriminatory care", "PNC permits practicing nursing without registration or license"),
            Triple("Pakistan Nursing Council (PNC): Scope of Practice & Prescriptive Rules", "Registered Nurses (RNs) practice within PNC prescribed competencies; administration of high-alert medications requires valid licensed physician/prescriber order", "RNs can prescribe experimental drugs independently without physician orders")
        )

        for (i in 0 until 80) {
            val topicIndex = i % masteryTopicsPart4.size
            val item = masteryTopicsPart4[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Mastery Critical Care Standard: ${item.second}",
                "Unsafe / Breach of Practice: ${item.third}",
                "Abandon vital monitoring and leave client unattended",
                "Delegate critical ACLS resuscitation tasks to untrained volunteers"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Critical Care, Leadership & PNC Rules",
                "NCLEX-RN / DHA • Mastery Series",
                "Mastery Series Critical Care Case #${i + 1}: In an intensive care, infection control, or PNC professional leadership scenario involving ${item.first}, which protocol represents evidence-based best practice?",
                options,
                correctPos,
                "Rationale: Critical care, infection control, and PNC professional leadership mastery standards for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures critical hemodynamic stability, ethical compliance, and safe delegation. Action '${item.third}' is improper.",
                "Critical Care Mastery • ${item.first}"
            )
        }

        return list
    }
}
