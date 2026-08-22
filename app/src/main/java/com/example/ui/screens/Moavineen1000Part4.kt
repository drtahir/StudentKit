package com.drtahir.studentkit.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - Moavineen1000Part4
 * Subject: Functional Arabic (170 Unique High-Yield MCQs)
 */
object Moavineen1000Part4 {

    fun getFunctionalArabicQuestions(startId: Int): List<MoavineenQuestion> {
        val list = ArrayList<MoavineenQuestion>(170)
        var idCounter = startId

        populateBatch1(list, idCounter)
        idCounter += 35
        populateBatch2(list, idCounter)
        idCounter += 35
        populateBatch3(list, idCounter)
        idCounter += 35
        populateBatch4(list, idCounter)
        idCounter += 35
        populateBatch5(list, idCounter)
        idCounter += 30

        return list
    }

    private fun populateBatch1(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What does the common Arabic phrase 'Ayna Maktab Raqam...?' mean?",
                options = listOf("Where is Office / Camp Number...?", "What is your passport number?", "How much is this meal?", "When is the flight departing?"),
                correctIndex = 0,
                explanation = "'Ayna' means 'Where is', and 'Maktab' means 'Office / Maktab Camp', followed by the number.",
                reference = "Spoken Arabic for Moavineen"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "What is the Arabic word for 'Hospital' used when directing emergency cases?",
                options = listOf("Mustashfa (مستشفى)", "Mahattah (محطة)", "Funduq (فندق)", "Mat'am (مطعم)"),
                correctIndex = 0,
                explanation = "'Mustashfa' means hospital. 'Funduq' is hotel, 'Mat'am' is restaurant, 'Mahattah' is station.",
                reference = "Medical Arabic Vocabulary"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What does the Arabic word 'Dhaa'i' (ضائع) or 'Taa'ih' (تائه) signify in Hajj operations?",
                options = listOf("A lost person / pilgrim who has lost their way", "A registered physician", "A bus driver", "A luxury tourist"),
                correctIndex = 0,
                explanation = "'Dhaa'i' or 'Taa'ih' refers to a lost person or lost pilgrim seeking direction.",
                reference = "Lost & Found Arabic Terms"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "How do you say 'Straight ahead / Go straight' in Saudi colloquial Arabic?",
                options = listOf("Ala Tool / Mustaqeem (على طول / مستقيم)", "Yameen (يمين)", "Yasaar (يسار)", "Wara (ورا)"),
                correctIndex = 0,
                explanation = "'Ala Tool' or 'Mustaqeem' means straight ahead; 'Yameen' is right, 'Yasaar' is left.",
                reference = "Directional Arabic Phrases"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "What is the Arabic phrase for 'Emergency Ambulance'?",
                options = listOf("Sayyarat al-Is'af (سيارة الإسعاف)", "Sayyarat al-Ujrah (سيارة الأجرة)", "Hafilah (حافلة)", "Darrajah (دراجة)"),
                correctIndex = 0,
                explanation = "'Is'af' refers to emergency medical ambulance services.",
                reference = "Emergency Services Arabic"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What does the question 'Kam Raqam al-Ghurfah?' (كم رقم الغرفة؟) mean?",
                options = listOf("What is the room number?", "What is your flight number?", "How many children do you have?", "Where is the hotel entrance?"),
                correctIndex = 0,
                explanation = "'Kam' means how much / what, and 'Raqam al-Ghurfah' means room number.",
                reference = "Hotel Inquiries Arabic"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "What is the Arabic word for 'Wristband' or 'Identity Bracelet' worn by pilgrims?",
                options = listOf("Iswarah (إسوارة) / Suwar", "Hizam (حزام)", "Qamis (قميص)", "Khatim (خاتم)"),
                correctIndex = 0,
                explanation = "'Iswarah' or 'Suwar' refers to the identification wristband worn around the wrist.",
                reference = "Pilgrim Identification Terms"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "How do you politely ask someone in Arabic 'Can you help me, please?'",
                options = listOf("Hal Yumkinuka Musa'adati, Min Fadlik? (هل يمكنك مساعدتي، من فضلك؟)", "Ayna Anta?", "Kam Hadha?", "Ma Ismuka?"),
                correctIndex = 0,
                explanation = "'Musa'adah' means help/assistance, and 'Min Fadlik' means please.",
                reference = "Conversational Etiquette"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "What is the term for 'Saudi Passport & Immigration Police'?",
                options = listOf("Al-Jawazat (الجوازات)", "Al-Baladiyyah (البلدية)", "Al-Murur (المرور)", "Al-Ittisalat (الاتصالات)"),
                correctIndex = 0,
                explanation = "'Al-Jawazat' is the General Directorate of Passports and Immigration in Saudi Arabia.",
                reference = "Saudi Administrative Terms"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "What does the phrase 'Ana Mareedh, Ahtaju Tabeeb' (أنا مريض، أحتاج طبيب) mean?",
                options = listOf("I am sick, I need a doctor", "I am hungry, I need food", "I am lost, I need a taxi", "I want to buy a ticket"),
                correctIndex = 0,
                explanation = "'Mareedh' means patient/sick, and 'Tabeeb' means doctor/physician.",
                reference = "Medical Spoken Arabic"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #11] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-11)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #12] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-12)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #13] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-13)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #14] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-14)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #15] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-15)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #16] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-16)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #17] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-17)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #18] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-18)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #19] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-19)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #20] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-20)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #21] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-21)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #22] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-22)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #23] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-23)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #24] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-24)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #25] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-25)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #26] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-26)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #27] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-27)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #28] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-28)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #29] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-29)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #30] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-30)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #31] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-31)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #32] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-32)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #33] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-33)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #34] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-34)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #35] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-35)"
            )
        )
    }

    private fun populateBatch2(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #36] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-36)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #37] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-37)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #38] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-38)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #39] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-39)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #40] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-40)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #41] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-41)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #42] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-42)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #43] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-43)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #44] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-44)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #45] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-45)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #46] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-46)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #47] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-47)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #48] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-48)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #49] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-49)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #50] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-50)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #51] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-51)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #52] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-52)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #53] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-53)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #54] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-54)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #55] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-55)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #56] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-56)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #57] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-57)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #58] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-58)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #59] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-59)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #60] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-60)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #61] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-61)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #62] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-62)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #63] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-63)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #64] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-64)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #65] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-65)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #66] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-66)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #67] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-67)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #68] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-68)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #69] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-69)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #70] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-70)"
            )
        )
    }

    private fun populateBatch3(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #71] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-71)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #72] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-72)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #73] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-73)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #74] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-74)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #75] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-75)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #76] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-76)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #77] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-77)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #78] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-78)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #79] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-79)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #80] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-80)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #81] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-81)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #82] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-82)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #83] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-83)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #84] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-84)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #85] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-85)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #86] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-86)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #87] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-87)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #88] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-88)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #89] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-89)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #90] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-90)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #91] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-91)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #92] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-92)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #93] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-93)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #94] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-94)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #95] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-95)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #96] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-96)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #97] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-97)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #98] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-98)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #99] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-99)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #100] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-100)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #101] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-101)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #102] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-102)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #103] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-103)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #104] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-104)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #105] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-105)"
            )
        )
    }

    private fun populateBatch4(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #106] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-106)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #107] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-107)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #108] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-108)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #109] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-109)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #110] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-110)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #111] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-111)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #112] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-112)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #113] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-113)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #114] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-114)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #115] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-115)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #116] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-116)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #117] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-117)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #118] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-118)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #119] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-119)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #120] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-120)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #121] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-121)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #122] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-122)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #123] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-123)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #124] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-124)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #125] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-125)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #126] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-126)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #127] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-127)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #128] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-128)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #129] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-129)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #130] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-130)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #131] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-131)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #132] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-132)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #133] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-133)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #134] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-134)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #135] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-135)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #136] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-136)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #137] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-137)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #138] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-138)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #139] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-139)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #140] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-140)"
            )
        )
    }

    private fun populateBatch5(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #141] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-141)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #142] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-142)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #143] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-143)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #144] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-144)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #145] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-145)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #146] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-146)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #147] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-147)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #148] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-148)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #149] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-149)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #150] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-150)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #151] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-151)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #152] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-152)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #153] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-153)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #154] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-154)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #155] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-155)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #156] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-156)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #157] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-157)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #158] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-158)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #159] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-159)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #160] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-160)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #161] What is the Arabic word for 'Fire' used when reporting an emergency to Civil Defense?",
                options = listOf("Hareeq (حريق) / Naar", "Maa (ماء)", "Hawa (هواء)", "Matar (مطر)"),
                correctIndex = 0,
                explanation = "'Hareeq' means fire or conflagration. 'Maa' is water, 'Matar' is rain.",
                reference = "Emergency Arabic Terms (Arab-Ref-161)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #162] What is the Arabic word for 'Police'?",
                options = listOf("Shurtah (شرطة)", "Tabeeb (طبيب)", "Mudaris (مدرس)", "Muhandis (مهندس)"),
                correctIndex = 0,
                explanation = "'Shurtah' means police force.",
                reference = "Security Terminology (Arab-Ref-162)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #163] What is the Arabic term for 'Official Hajj Permit'?",
                options = listOf("Tasreeh al-Hajj (تصريح الحج)", "Tazkirat Tayaran (تذكرة طيران)", "Fatoodah (فاتورة)", "Kitab (كتاب)"),
                correctIndex = 0,
                explanation = "'Tasreeh al-Hajj' is the mandatory official regulatory permit to perform Hajj.",
                reference = "Saudi Legal Terms (Arab-Ref-163)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #164] What is the Arabic word for the number '100'?",
                options = listOf("Mi'ah (مائة)", "Asharah (عشرة)", "Alf (ألف)", "Wahid (واحد)"),
                correctIndex = 0,
                explanation = "'Mi'ah' is 100, 'Asharah' is 10, 'Alf' is 1000.",
                reference = "Arabic Numerals (Arab-Ref-164)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #165] What is the Arabic word for the number '1000'?",
                options = listOf("Alf (ألف)", "Khamsah (خمسة)", "Mi'ah (مائة)", "Sittah (ستة)"),
                correctIndex = 0,
                explanation = "'Alf' is 1,000.",
                reference = "Arabic Numerals (Arab-Ref-165)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #166] What is the Arabic word for 'Tent' used when locating camps in Mina?",
                options = listOf("Khaimah (خيمة)", "Sayyarah (سيارة)", "Bayt (بيت)", "Qitar (قطار)"),
                correctIndex = 0,
                explanation = "'Khaimah' means tent; plural is 'Khiyam'.",
                reference = "Mina Camp Vocabulary (Arab-Ref-166)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #167] What is the Arabic phrase for 'Train Station'?",
                options = listOf("Mahattat al-Qitar (محطة القطار)", "Mawqif al-Hafilat (موقف الحافلات)", "Matar (مطار)", "Minaa (ميناء)"),
                correctIndex = 0,
                explanation = "'Mahattat al-Qitar' is train railway station.",
                reference = "Transportation Arabic (Arab-Ref-167)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #168] What is the Arabic term for 'Wheelchair' for disabled pilgrims?",
                options = listOf("Kursi Mutaharrik (كرسي متحرك)", "Sarir (سرير)", "Babil (باب)", "Nafidhah (نافذة)"),
                correctIndex = 0,
                explanation = "'Kursi Mutaharrik' literally translates to movable chair / wheelchair.",
                reference = "Disability Support Arabic (Arab-Ref-168)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #169] How do you ask for 'Cold Water' in Arabic?",
                options = listOf("Maa Barid (ماء بارد)", "Shai Sakhin (شاي ساخن)", "Qahwah (قهوة)", "Aseer (عصير)"),
                correctIndex = 0,
                explanation = "'Maa Barid' means cold water.",
                reference = "Daily Life Arabic (Arab-Ref-169)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Functional Arabic",
                question = "[Spoken Arabic #170] What is the medical term for 'Heat Stroke' in Arabic?",
                options = listOf("Darbat Shams (ضربة شمس)", "Zukam (زكام)", "Kasr (كسر)", "Jorh (جرح)"),
                correctIndex = 0,
                explanation = "'Darbat Shams' translates directly to sunstroke / heatstroke.",
                reference = "Clinical Arabic Terms (Arab-Ref-170)"
            )
        )
    }

}
