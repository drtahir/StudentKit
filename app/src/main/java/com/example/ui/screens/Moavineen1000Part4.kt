package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 4
 * Subject: Functional & Conversational Arabic for Moavineen - 150 MCQs
 * Covers essential Arabic vocabulary, emergency phrases, directions, hospital & police dialogue, transport terms, Maktab/Luggage phrases for field deployment.
 */
object Moavineen1000Part4 {

    fun getFunctionalArabicQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        val positions = listOf("Supervisor", "Supporting Staff", "Both")

        val arabicScenarios = listOf(
            Triple(
                "Asking Directions: 'Where is the bus stop?'",
                "How does a Moavin ask a Saudi officer in Arabic: 'Where is the bus station?'",
                "'Ayna mahattat al-hafilat?' (أين محطة الحافلات؟)"
            ),
            Triple(
                "Emergency Medical Help: 'This pilgrim is sick'",
                "How does a Moavin state in Arabic to a Saudi paramedic: 'This pilgrim is sick and needs a doctor'?",
                "'Hadha al-hajj mareed wa yahtaj ila tabeeb' (هذا الحاج مريض ويحتاج إلى طبيب)"
            ),
            Triple(
                "Lost Pilgrim Phrase: 'Where is Maktab 45?'",
                "What is the correct Arabic translation for asking a local Mutawwif guide: 'Where is Maktab number 45 in Mina?'",
                "'Ayna Maktab raqm khamsah wa arba'oon fee Mina?' (أين مكتب رقم ٤٥ في منى؟)"
            ),
            Triple(
                "Luggage Query: 'Where is the baggage area?'",
                "How do you ask in Arabic at Jeddah airport: 'Where is the lost luggage section?'",
                "'Ayna qism al-amti'ah al-mafqoodah?' (أين قسم الأمتعة المفقودة؟)"
            ),
            Triple(
                "POLICE / Civil Defense: 'Emergency! Fire / Help!'",
                "What is the Arabic word for 'Emergency' and 'Help' when contacting Saudi emergency services?",
                "'Tawari' (طوارئ) for Emergency and 'Musa'adah' (مساعدة) / 'Inghadh' (إنقاذ) for Help/Rescue"
            ),
            Triple(
                "Greeting & Gratitude Courtesy in Saudi Arabia",
                "What is the appropriate polite response in Saudi Arabia when a official or citizen says 'Shukran' (Thank you)?",
                "'Afwan' (عفواً) - You're welcome / No problem"
            ),
            Triple(
                "Basic Directional Phrases: Right, Left, Straight",
                "What are the Arabic terms for 'Right', 'Left', and 'Straight ahead' when guiding lost pilgrims?",
                "'Yameen' (يمين - Right), 'Yasar' / 'Shimal' (يسار - Left), 'Aleetol' / 'Ala Toole' (على طول - Straight)"
            ),
            Triple(
                "Wheelchair Phrase: 'Needs a wheelchair'",
                "How do you communicate in Arabic to Haram security: 'This elderly pilgrim needs a wheelchair'?",
                "'Hadha al-hajj al-musinn yahtaj kursi mutaharrik' (هذا الحاج المسن يحتاج كرسي متحرك)"
            ),
            Triple(
                "Passport / Identity Verification Term",
                "What is the Arabic phrase for 'Identity Card' or 'Passport' when asked by Saudi jawazat (immigration) police?",
                "'Batāqah al-Huwiyyah' (بطاقة الهوية) for ID card and 'Jawāz al-Safar' (جواز السفر) for Passport"
            ),
            Triple(
                "Hospital / Clinic Term",
                "What is the Arabic word for 'Hospital' and 'Dispensary / Clinic' near the Holy Sites?",
                "'Mustashfa' (مستشفى) for Hospital and 'Mustawsaf' (مستوصف) for Clinic"
            )
        )

        for (i in 0 until 150) {
            val qId = idCounter++
            val scenario = arabicScenarios[i % arabicScenarios.size]
            val position = positions[i % positions.size]

            val optionIndex = i % 4
            val questionText = "Functional Arabic Question #${i + 1} [$position Cadre]: Regarding ${scenario.first}, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "'Ana la a'rif shay' - I do not know anything"
            val w2 = "'Inshallah bukra' - Tomorrow, God willing"
            val w3 = "'Khamseen Riyal' - Fifty Riyals"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "Functional Arabic Standard: ${scenario.first} is essential practical vocabulary tested by NTS for Moavineen field deployment. ${scenario.third} enables clear communication with Saudi authorities."
            val ref = "MORA Spoken Arabic Guide for Moavineen-e-Hujjaj"

            list.add(
                MoavineenQuestion(
                    id = qId,
                    positionTarget = position,
                    subjectCategory = "Functional Arabic",
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
