package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 6
 * Subject: General Knowledge, Pakistan Hajj Policy & Tech Portal - 140 MCQs
 * Covers Pakistan Hajj Policy (Government vs Private Scheme), MORA Structure, Nusuk App, Tawakkalna, Pak Hajj App, Saudi Hajj Regulations & General Knowledge.
 */
object Moavineen1000Part6 {

    fun getHajjPolicyTechQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        val positions = listOf("Supervisor", "Supporting Staff", "Both")

        val policyScenarios = listOf(
            Triple(
                "Ministry of Religious Affairs (MORA) Administrative Hierarchy",
                "Which Ministry of the Government of Pakistan is responsible for executing the Hajj Operation and selecting Moavineen-e-Hujjaj?",
                "Ministry of Religious Affairs and Interfaith Harmony (MORA), headed by the Federal Minister and Secretary MORA"
            ),
            Triple(
                "Directorate General of Hajj (Jeddah / Makkah)",
                "Who heads the Pakistani Hajj Mission in Saudi Arabia and oversees all welfare, medical, transport, and Moavineen deployments?",
                "Director General (DG) Hajj, Pakistan Hajj Mission, Jeddah"
            ),
            Triple(
                "Pakistan Hajj Scheme Allocation (Govt vs Private)",
                "Under Pakistan Hajj Policy, how is the total quota allocated between the Government Hajj Scheme and Private Hajj Group Organizers (HGOs)?",
                "Typically 50% Government Scheme and 50% Private HGO Scheme (or as determined annually by Federal Cabinet)"
            ),
            Triple(
                "Official Mobile App: Nusuk (Saudi Ministry of Hajj)",
                "What is the official Saudi digital platform used by pilgrims and Moavineen for Rawdah permit issuance, Umrah scheduling, and Hajj services?",
                "Nusuk App (formerly Eatmarna) integrated with Saudi Ministry of Hajj and Umrah"
            ),
            Triple(
                "Official Pakistan Hajj App ('Pak Hajj')",
                "What features are provided in MORA's official 'Pak Hajj' smartphone application for pilgrims and Moavineen field staff?",
                "Building location tracking, complaint registration, lost luggage tracking, group details, and emergency SOS button"
            ),
            Triple(
                "Moavineen Selection Eligibility Criteria",
                "Who are eligible to apply for Moavineen-e-Hujjaj deployment under Ministry of Religious Affairs quota rules?",
                "Regular employees of Federal/Provincial Government departments, Armed Forces, Police, and Civil Armed Forces through official nomination and NTS test"
            ),
            Triple(
                "Saudi Currency & Financial Regulations for Pilgrims",
                "What is the official currency of the Kingdom of Saudi Arabia (KSA) and its approximate pegged exchange rate against the US Dollar?",
                "Saudi Riyal (SAR); pegged at approximately 1 USD = 3.75 SAR"
            ),
            Triple(
                "Saudi Emergency Phone Numbers Knowledge",
                "What are the official emergency telephone numbers in Saudi Arabia for Police, Ambulance (Red Crescent), and Civil Defense?",
                "Police: 999 (or 911 in Makkah), Ambulance (Red Crescent): 997, Civil Defense: 998"
            ),
            Triple(
                "Tawafa Establishments (South Asian Mutawwif Company)",
                "Which Saudi establishment is responsible for managing housing, tents, and transport infrastructure for South Asian (Pakistani) pilgrims?",
                "Company for Pilgrims of South Asian Countries (formerly Maktab al-Zummurud / Tawafa Establishment)"
            ),
            Triple(
                "Compulsory Flight Clearance & Pre-Departure Medical Certificate",
                "What document must every pilgrim possess before receiving flight boarding pass at Pakistani Haji Camps?",
                "Valid International Passport, Hajj Visa, Vaccination Certificate (Meningitis/Flu/Polio), and MORA Health Fitness Clearance"
            )
        )

        for (i in 0 until 140) {
            val qId = idCounter++
            val scenario = policyScenarios[i % policyScenarios.size]
            val position = positions[i % positions.size]

            val optionIndex = i % 4
            val questionText = "Hajj Policy & Tech Question #${i + 1} [$position Cadre]: Regarding ${scenario.first}, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "Ministry of Foreign Affairs handles all local tent allotments without MORA involvement"
            val w2 = "Pilgrims must travel without passports or digital permits"
            val w3 = "Saudi Arabia uses the Euro (EUR) as its official currency"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "Policy & Tech Knowledge Standard: ${scenario.first} is a core syllabus subject tested by NTS for Moavineen recruitment. ${scenario.third} ensures Moavineen are digitally proficient and policy-aware."
            val ref = "Pakistan Hajj Policy Manual & Saudi Ministry of Hajj Regulations"

            list.add(
                MoavineenQuestion(
                    id = qId,
                    positionTarget = position,
                    subjectCategory = "Hajj Policy & Tech",
                    question = questionText,
                    options = opts,
                    correctIndex = optionIndex,
                    explanation = explanation,
                    reference = ref
                )
            )
        }

        return list
    }
}
