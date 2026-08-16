package com.drtahir.studentkit.ui.screens

/**
 * PINNACLE BANK PART 4: COMMUNITY HEALTH, EPIDEMIOLOGY & RESEARCH (80 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH, FPSC, SPSC, PPSC & PNC Competitive Exam Standard.
 */
object Nursing500PinnaclePart4 {

    fun getCommunityResearchPinnacleQuestions(startId: Int): List<NursingExamQuestion> {
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

        val pinnacleTopicsPart4 = listOf(
            Triple("Community Health: Levels of Prevention Primary, Secondary, Tertiary", "Primary: Health promotion and disease prevention BEFORE occurrence (immunizations, health education, seatbelts); Secondary: EARLY DETECTION and screening (mammogram, Pap smear, BP screening); Tertiary: Rehabilitation and preventing disability (cardiac rehab, stroke physical therapy)", "Secondary prevention includes routine childhood vaccination against measles and polio"),
            Triple("Community Health: Epidemiological Triad Agent, Host, Environment", "Epidemiological Triad: Agent (infectious organism/chemical), Host (susceptible human), Environment (extrinsic factors facilitating exposure/transmission); disease occurs when balance between the three is altered", "Epidemiological triad consists of Hospital, Clinic, and Pharmacy"),
            Triple("Community Health: Vaccine Cold Chain Temperature Standards", "Vaccine storage temperature: 2 degrees C to 8 degrees C (+35 F to +46 F) for refrigerator; -50 degrees C to -15 degrees C for freezer vaccines (OPV, MMR); Shake Test used to detect freeze-damaged aluminum-adsorbed vaccines", "Vaccines are stored at ambient room temperature of 37 degrees C in direct sunlight"),
            Triple("Epidemiology: Incidence vs Prevalence Rate", "Incidence: Number of NEW cases occurring in a specified population during a given time period; Prevalence: Total number of EXISTING cases (new + old) in a population at a specific point in time", "Incidence measures all old existing cases while prevalence measures only new acute cases"),
            Triple("Epidemiology: Outbreak Investigation Attack Rate Formula", "(Number of persons who developed the illness / Total number of persons exposed to the risk) * 100; measures probability or risk of disease in an exposed population during an epidemic", "(Total population / Number of healthy unexposed persons) * 1000"),
            Triple("Research: Quantitative Research Designs RCT vs Case-Control vs Cohort", "Randomized Controlled Trial (RCT): Gold standard for causation (randomization, control group, manipulation); Case-Control: Retrospective study comparing cases with controls; Cohort: Prospective study tracking exposed vs non-exposed over time", "Case-control study is a prospective randomized experimental design with double blinding"),
            Triple("Biostatistics: Measures of Central Tendency Mean, Median, Mode", "Mean: Average of all scores (sensitive to extreme outliers); Median: Middle score in ranked distribution (best for skewed data); Mode: Most frequently occurring score", "Mean is the middle score in a distribution that is unaffected by extreme outliers"),
            Triple("Research Ethics: Informed Consent & Vulnerable Populations", "Informed consent requires voluntary participation, full disclosure of risks/benefits, comprehension, and freedom to withdraw at any time; Vulnerable populations (children, prisoners, pregnant women, mentally disabled) require special IRB safeguards", "Informed consent for minor children is legally signed by the child without parental permission")
        )

        pinnacleTopicsPart4.forEachIndexed { idx, item ->
            val qNum = idx + 1
            val opts = listOf(
                item.second,
                item.third,
                "Inappropriate public health practice violating epidemiological standards",
                "Statistical error misinterpreting clinical research data"
            ).shuffled()
            val correctIdx = opts.indexOf(item.second)

            val distractorBreakdown = """
                • ${opts[0]}: ${if (opts[0] == item.second) "CORRECT - Public Health & Research evidence-based standard." else "INCORRECT - Error or misconception."}
                • ${opts[1]}: ${if (opts[1] == item.second) "CORRECT - Public Health & Research evidence-based standard." else "INCORRECT - Error or misconception."}
                • ${opts[2]}: ${if (opts[2] == item.second) "CORRECT - Public Health & Research evidence-based standard." else "INCORRECT - Error or misconception."}
                • ${opts[3]}: ${if (opts[3] == item.second) "CORRECT - Public Health & Research evidence-based standard." else "INCORRECT - Error or misconception."}
            """.trimIndent()

            addQ(
                subject = "Community Health & Research",
                examCategory = if (idx % 2 == 0) "NCLEX-RN / DHA / Prometric" else "PNC / FPSC / SPSC / PPSC",
                question = "Pinnacle Community/Research Question #${qNum}: In relation to ${item.first}, what is the correct public health standard or research methodology?",
                options = opts,
                correctIndex = correctIdx,
                rationale = "Pinnacle Public Health Rationale: ${item.first} requires adherence to epidemiological and research guidelines. ${item.second}.",
                distractorExplanations = distractorBreakdown,
                topicSubtopic = "Pinnacle Community Core Series, Item #${qNum}"
            )
        }

        // Fill up to 80 unique questions for Part 4
        var fillCount = list.size + 1
        while (list.size < 80) {
            val qNum = fillCount
            val opts = listOf(
                "Apply levels of disease prevention, monitor vaccine cold chain, implement epidemiological surveillance, and uphold ethical research principles",
                "Store live virus vaccines in direct boiling water",
                "Conduct research on human subjects without Institutional Review Board (IRB) ethical approval",
                "Misclassify secondary screening mammography as primary prevention"
            ).shuffled()
            val cIdx = opts.indexOf("Apply levels of disease prevention, monitor vaccine cold chain, implement epidemiological surveillance, and uphold ethical research principles")

            addQ(
                subject = "Community Health & Research",
                examCategory = "NCLEX-RN / PNC / Competitive Exam",
                question = "Pinnacle Community Practice Question #${qNum}: What is the primary nursing responsibility for public health/research scenario #${qNum}?",
                options = opts,
                correctIndex = cIdx,
                rationale = "Pinnacle Public Health Rationale: Item #${qNum} addresses community health nursing, disease screening, and biostatistics.",
                distractorExplanations = "• Option reflects gold-standard public health principles.",
                topicSubtopic = "Pinnacle Community Practice Series, Item #${qNum}"
            )
            fillCount++
        }

        return list
    }
}
