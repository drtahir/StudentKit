package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 4
 * Subject: Functional & Conversational Arabic for Moavineen (30 100% Unique MCQs)
 * Covers emergency phrases, directions, hospital & police dialogue, transport terms, Maktab/Luggage phrases, courtesy, and numbers in spoken Saudi Arabic.
 */
object Moavineen1000Part4 {

    fun getFunctionalArabicQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "How does a Moavin ask a Saudi transport officer in spoken Arabic: 'Where is the bus station?'",
                options = listOf(
                    "'Ayna mahattat al-hafilat?' (أين محطة الحافلات؟)",
                    "'Ayna al-matār?'",
                    "'Kam al-tha'man?'",
                    "'Mada tureed?'"
                ),
                correctIndex = 0,
                explanation = "'Ayna' means 'Where', 'mahattat' means 'station', and 'al-hafilat' means 'buses'. Together it translates to 'Where is the bus station?'.",
                reference = "MORA Spoken Arabic Guide for Moavineen"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "How do you state in Arabic to a Saudi Red Crescent paramedic: 'This pilgrim is sick and needs a doctor'?",
                options = listOf(
                    "'Ana a'rif al-tareeq'",
                    "'Hadha al-hajj mareed wa yahtaj ila tabeeb' (هذا الحاج مريض ويحتاج إلى طبيب)",
                    "'Ayna ghurfat al-naum?'",
                    "'Shukran jazeelan'"
                ),
                correctIndex = 1,
                explanation = "'Hadha' (This), 'al-hajj' (pilgrim), 'mareed' (sick), 'yahtaj' (needs), 'tabeeb' (doctor).",
                reference = "MORA Emergency Spoken Arabic Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What is the correct Arabic translation for asking a Mutawwif guide in Mina: 'Where is Maktab number 45?'",
                options = listOf(
                    "'Ayna Maktab raqm khamsah wa arba'oon fee Mina?' (أين مكتب رقم ٤٥ في منى؟)",
                    "'Ayna al-mustashfa al-kabeer?'",
                    "'Kayfa haluka ya akhi?'",
                    "'Hadha ghali jiddan'"
                ),
                correctIndex = 0,
                explanation = "'Ayna' (Where is), 'Maktab' (Office/Maktab), 'raqm' (number), 'khamsah wa arba'oon' (45), 'fee Mina' (in Mina).",
                reference = "MORA Practical Field Arabic Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "How do you inquire at Jeddah airport lost property desk: 'Where is the lost luggage section?'",
                options = listOf(
                    "'Ayna qism al-amti'ah al-mafqoodah?' (أين قسم الأمتعة المفقودة؟)",
                    "'Ayna al-futoor?'",
                    "'Kam al-sa'ah al-an?'",
                    "'Man anta ya sayyidi?'"
                ),
                correctIndex = 0,
                explanation = "'qism' means 'section/department', 'al-amti'ah' means 'luggage/baggage', and 'al-mafqoodah' means 'lost/missing'.",
                reference = "MORA Airport Conversational Arabic Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What are the essential Arabic terms for calling 'Emergency' and asking for 'Help/Rescue' when addressing Saudi police or Civil Defense?",
                options = listOf(
                    "'Tawari' (طوارئ) for Emergency and 'Musa'adah' (مساعدة) / 'Inghadh' (إنقاذ) for Help/Rescue",
                    "'Futoor' and 'Ghada'",
                    "'Kabeer' and 'Sagheer'",
                    "'Yameen' and 'Yasar'"
                ),
                correctIndex = 0,
                explanation = "'Tawari' is Emergency; 'Musa'adah' is assistance/help; 'Inghadh' is rescue.",
                reference = "MORA Emergency Terminology Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "What is the polite Arabic response when a Saudi official or pilgrim thanks you by saying 'Shukran' (Thank you)?",
                options = listOf(
                    "'Afwan' (عفواً) or 'Ahlain wa Sahlain' (Welcome / You're welcome)",
                    "'La' (No)",
                    "'Khalas' (Finished)",
                    "'Insa' (Forget it)"
                ),
                correctIndex = 0,
                explanation = "'Afwan' is the standard polite reply to 'Shukran', meaning 'You are welcome'.",
                reference = "MORA Conversational Courtesy Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What are the spoken Arabic words for 'Right', 'Left', and 'Straight ahead' when giving route directions to pilgrims?",
                options = listOf(
                    "'Yameen' (يمين - Right), 'Yasar' / 'Shimal' (يسار - Left), 'Aleetol' / 'Ala Toole' (على طول - Straight)",
                    "'Fawq', 'Taht', 'Khalf'",
                    "'Wahid', 'Ithnan', 'Thalathah'",
                    "'Qareeb', 'Ba'eed', 'Jadeed'"
                ),
                correctIndex = 0,
                explanation = "'Yameen' = Right, 'Yasar/Shimal' = Left, 'Ala Tool / Aleetol' = Straight ahead.",
                reference = "MORA Directional Vocabulary Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "How do you convey to Haram security in Arabic: 'This elderly pilgrim needs a wheelchair'?",
                options = listOf(
                    "'Hadha al-hajj al-musinn yahtaj kursi mutaharrik' (هذا الحاج المسن يحتاج كرسي متحرك)",
                    "'Ana ureedu al-shai'",
                    "'Ayna al-sayyarah?'",
                    "'Al-ta'am tayyib'"
                ),
                correctIndex = 0,
                explanation = "'musinn' (elderly), 'yahtaj' (needs), 'kursi mutaharrik' (wheelchair / moving chair).",
                reference = "MORA Field Service Arabic Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What are the Arabic terms for 'Identity Card' and 'Passport' when communicating with Saudi Jawazat (Immigration) officials?",
                options = listOf(
                    "'Batāqah al-Huwiyyah' (بطاقة الهوية) for ID card and 'Jawāz al-Safar' (جواز السفر) for Passport",
                    "'Kitab' and 'Qalam'",
                    "'Bāb' and 'Nāfidhah'",
                    "'Tazkirah' and 'Fatūrah'"
                ),
                correctIndex = 0,
                explanation = "'Batāqah Huwiyyah' is National/Official Identity Card; 'Jawāz al-Safar' is Passport.",
                reference = "MORA Official Documents Arabic Vocabulary"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "What is the difference between 'Mustashfa' and 'Mustawsaf' in Saudi Arabic medical terminology?",
                options = listOf(
                    "'Mustashfa' means Hospital (major facility); 'Mustawsaf' means Clinic / Dispensary (local facility)",
                    "'Mustashfa' means Pharmacy; 'Mustawsaf' means Ambulance",
                    "'Mustashfa' means Police Station; 'Mustawsaf' means Hotel",
                    "They are identical words for airport lounge"
                ),
                correctIndex = 0,
                explanation = "'Mustashfa' (مستشفى) = Hospital; 'Mustawsaf' (مستوصف) = Clinic/Dispensary.",
                reference = "MORA Medical Arabic Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "How do you say in Arabic: 'Where is the entrance?' and 'Where is the exit?'",
                options = listOf(
                    "'Ayna al-madkhal?' (المدخل - Entrance) and 'Ayna al-makhraj?' (المخرج - Exit)",
                    "'Ayna al-suq?' and 'Ayna al-mat'am?'",
                    "'Kam al-sa'ah?' and 'Kam al-thaman?'",
                    "'Man huna?' and 'Man hunak?'"
                ),
                correctIndex = 0,
                explanation = "'madkhal' = entrance; 'makhraj' = exit.",
                reference = "MORA Directional Field Arabic"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What does the Saudi Arabic phrase 'Ma'aleesh' (معليش) commonly express in conversation?",
                options = listOf(
                    "'Never mind / It's okay / Excuse me / Sorry'",
                    "'Hurry up immediately'",
                    "'Give me money now'",
                    "'Stop talking'"
                ),
                correctIndex = 0,
                explanation = "'Ma'aleesh' is a very common Saudi colloquial phrase meaning 'Don't worry', 'Excuse me', or 'It's alright'.",
                reference = "Saudi Colloquial Expressions Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "How do you ask a driver or passerby: 'How far is Masjid al-Haram from here?' in spoken Arabic?",
                options = listOf(
                    "'Kam yab'ud Masjid al-Haram min huna?' (كم يبعد مسجد الحرام من هنا؟) or 'Kam kilo ila al-Haram?'",
                    "'Mada ta'mal?'",
                    "'Hal takallam al-Urdu?'",
                    "'Ayna al-ma'a?'"
                ),
                correctIndex = 0,
                explanation = "'Kam yab'ud' = How far is it; 'min huna' = from here.",
                reference = "MORA Spoken Arabic Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What is the Arabic word for 'Water' and 'Drinking Water' essential during heat mitigation duties?",
                options = listOf(
                    "'Mā'' (ماء) or 'Moyah' (موية in Saudi dialect)",
                    "'Aseer' (Juice)",
                    "'Haleeb' (Milk)",
                    "'Shai' (Tea)"
                ),
                correctIndex = 0,
                explanation = "'Mā'' (standard) / 'Moyah' (Saudi dialect) = Water.",
                reference = "MORA Everyday Field Vocabulary"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "How does a Moavineen Supervisor state to a Saudi Mutawwif official: 'We need 5 additional buses for our pilgrims'?",
                options = listOf(
                    "'Nahtaj khams hafilat idafiyyah li-hujjajina' (نحتاج خمس حافلات إضافية لحجاجنا)",
                    "'Al-ta'am Ghali jiddan'",
                    "'Ana mas'ool al-ghurfah'",
                    "'Laisa 'indana waqt'"
                ),
                correctIndex = 0,
                explanation = "'Nahtaj' (We need), 'khams' (5), 'hafilat' (buses), 'idafiyyah' (additional), 'li-hujjajina' (for our pilgrims).",
                reference = "MORA Supervisory Transport Arabic"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "What are the spoken Arabic numbers 1 to 5 used when counting lost items or group members?",
                options = listOf(
                    "Wahid (1), Ithnan (2), Thalathah (3), Arba'ah (4), Khamsah (5)",
                    "Sittah (6), Sab'ah (7), Thamaniyah (8), Tis'ah (9), 'Asharah (10)",
                    "Alif, Ba, Ta, Tha, Jeem",
                    "Awwal, Thani, Thalith, Rabi, Khamis"
                ),
                correctIndex = 0,
                explanation = "1 = Wahid, 2 = Ithnan, 3 = Thalathah, 4 = Arba'ah, 5 = Khamsah.",
                reference = "MORA Arabic Numbers Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What are the Arabic numbers 6 to 10?",
                options = listOf(
                    "Sittah (6), Sab'ah (7), Thamaniyah (8), Tis'ah (9), 'Asharah (10)",
                    "Wahid, Ithnan, Thalathah, Arba'ah, Khamsah",
                    "Khamseen, Sittin, Sab'in, Thamanin, Tis'in",
                    "Ahad, Ithna, Thalath, Arba, Khams"
                ),
                correctIndex = 0,
                explanation = "6 = Sittah, 7 = Sab'ah, 8 = Thamaniyah, 9 = Tis'ah, 10 = 'Asharah.",
                reference = "MORA Numerals Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "How do you ask a hotel receptionist in Arabic: 'Where is room number 302?'",
                options = listOf(
                    "'Ayna ghurfah raqm thalath-mi'ah wa ithnan?' (أين غرفة رقم ٣٠٢؟)",
                    "'Ayna al-matbakh?'",
                    "'Kam al-tha'man?'",
                    "'Man fee al-bab?'"
                ),
                correctIndex = 0,
                explanation = "'ghurfah' (room), 'raqm' (number), 'thalath-mi'ah wa ithnan' (302).",
                reference = "MORA Hotel Dialogue Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What does the word 'Tayyib' (طيب) mean in common Saudi everyday speech?",
                options = listOf(
                    "'Good / Okay / Fine / Understood'",
                    "'Bad / Terrible'",
                    "'Expensive'",
                    "'Far away'"
                ),
                correctIndex = 0,
                explanation = "'Tayyib' is widely used in Saudi dialect to mean 'Alright', 'Okay', or 'Good'.",
                reference = "Saudi Dialect Terms"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "How do you say in Arabic: 'Slow down' or 'Wait a moment' to a bus driver or pilgrim?",
                options = listOf(
                    "'Shwayyah shwayyah' (شوية شوية) or 'Intazir lahzah' (انتظر لحظة)",
                    "'Sur'ah sur'ah' (Hurry up)",
                    "'Imshi al-an' (Go now)",
                    "'Ijliss huna' (Sit here)"
                ),
                correctIndex = 0,
                explanation = "'Shwayyah shwayyah' means 'slowly/take it easy'; 'Intazir lahzah' means 'wait a moment'.",
                reference = "MORA Colloquial Dialogue Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "How do you explain to Saudi security: 'I am an official Pakistani Hajj Mission volunteer / officer'?",
                options = listOf(
                    "'Ana mu'āwin / mas'ool min ba'that al-hajj al-pakistaniyyah' (أنا معاون / مسؤول من بعثة الحج الباكستانية)",
                    "'Ana sa'ih min Europa'",
                    "'Ana a'mal fee al-tarikh'",
                    "'La atakallam al-arabiyyah'"
                ),
                correctIndex = 0,
                explanation = "'mu'āwin' (assistant/helper), 'mas'ool' (officer/in-charge), 'ba'that al-hajj al-pakistaniyyah' (Pakistan Hajj Mission).",
                reference = "MORA Security Identification Dialogue"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "What is the Arabic word for 'Ambulance' when requesting rapid transport for a casualty?",
                options = listOf(
                    "'Sayyārat Is'āf' (سيارة إسعاف) or simply 'Is'āf'",
                    "'Hāfilah'",
                    "'Shāhinah'",
                    "'Darrājah'"
                ),
                correctIndex = 0,
                explanation = "'Is'āf' (إسعاف) means Medical First Aid / Ambulance.",
                reference = "MORA Emergency Vocabulary"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What does 'Khalas' (خلاص) signify in conversation with Saudi personnel?",
                options = listOf(
                    "'Finished / Done / Enough / Settled'",
                    "'Start again'",
                    "'I am hungry'",
                    "'Where is the luggage?'"
                ),
                correctIndex = 0,
                explanation = "'Khalas' is universal in colloquial Arabic meaning 'Finished', 'Completed', or 'That's all'.",
                reference = "Saudi Common Terms Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "How do you ask a local vendor or official: 'How much does this cost?' in spoken Arabic?",
                options = listOf(
                    "'Kam hādha?' (كم هذا؟) or 'Kam al-thaman?' (كم الثمن؟)",
                    "'Ayna al-masjid?'",
                    "'Man anta?'",
                    "'Mada ta'mal?'"
                ),
                correctIndex = 0,
                explanation = "'Kam hādha?' or 'Kam al-thaman?' = How much is this?",
                reference = "MORA Marketplace Conversational Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What is the Arabic word for 'Key' when dealing with hotel room key problems?",
                options = listOf(
                    "'Miftāh' (مفتاح)",
                    "'Qufl'",
                    "'Bāb'",
                    "'Ghurfah'"
                ),
                correctIndex = 0,
                explanation = "'Miftāh' means Key.",
                reference = "MORA Hotel Terms"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "How do you tell a Saudi officer: 'Please wait, our supervisor is coming'?",
                options = listOf(
                    "'Min fadlak intazir, al-mushrif qādim' (من فضلك انتظر، المشرف قادم)",
                    "'Imshi sur'ah'",
                    "'La a'rif al-ism'",
                    "'Hadha mamnoo'"
                ),
                correctIndex = 0,
                explanation = "'Min fadlak' (Please), 'intazir' (wait), 'al-mushrif' (the supervisor), 'qādim' (is coming).",
                reference = "MORA Official Liaison Dialogue"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "What does 'Mamnoo'' (ممنوع) mean on Saudi safety signboards and security instructions?",
                options = listOf(
                    "'Forbidden / Prohibited / Not Allowed'",
                    "'Welcome / Free Entrance'",
                    "'Open 24 Hours'",
                    "'Drinking Water Station'"
                ),
                correctIndex = 0,
                explanation = "'Mamnoo'' means Prohibited or Forbidden.",
                reference = "Saudi Signage & Safety Vocabulary"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "How do you say 'Today', 'Tomorrow', and 'Yesterday' in spoken Saudi Arabic?",
                options = listOf(
                    "'Al-Yaum' (Today), 'Bukra' (Tomorrow), 'Ams' (Yesterday)",
                    "'Sabah', 'Masa', 'Lail'",
                    "'Awwal', 'Thani', 'Thalith'",
                    "'Huna', 'Hunak', 'Fawq'"
                ),
                correctIndex = 0,
                explanation = "'Al-Yaum' = Today; 'Bukra' = Tomorrow; 'Ams' = Yesterday.",
                reference = "MORA Time Expressions Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "What is the Arabic expression for 'God willing' used continuously in Saudi Arabia when discussing future tasks?",
                options = listOf(
                    "'Insha'Allah' (إن شاء الله)",
                    "'Masha'Allah'",
                    "'Alhamdulillah'",
                    "'Subhanallah'"
                ),
                correctIndex = 0,
                explanation = "'Insha'Allah' means 'If God wills', used for future plans.",
                reference = "MORA Common Phrases Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "How do you ask in Arabic: 'Do you speak Urdu or English?' when seeking an interpreter?",
                options = listOf(
                    "'Hal tatakallam al-Urdu aw al-Ingleziyyah?' (هل تتكلم اردو أو انجليزية؟)",
                    "'Ayna al-hajj?'",
                    "'Kam raqm al-hafilah?'",
                    "'Hadha mushkilah kabeerah'"
                ),
                correctIndex = 0,
                explanation = "'Hal tatakallam' = Do you speak; 'al-Urdu' = Urdu; 'al-Ingleziyyah' = English.",
                reference = "MORA Conversational Language Guide"
            )
        )

        return list
    }
}
