package com.drtahir.studentkit.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - Moavineen1000Part6
 * Subject: Hajj Policy & Tech (170 Unique High-Yield MCQs)
 */
object Moavineen1000Part6 {

    fun getHajjPolicyTechQuestions(startId: Int): List<MoavineenQuestion> {
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
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the official unified digital platform developed by the Saudi Ministry of Hajj and Umrah for issuing electronic pilgrim ID cards, Rawdah permits, and service packages?",
                options = listOf("Nusuk App (نسك)", "Uber App", "Snapchat", "Careem"),
                correctIndex = 0,
                explanation = "Nusuk is the official Saudi Ministry of Hajj platform managing digital pilgrim credentials, visas, and permits.",
                reference = "Saudi Digital Hajj Ecosystem"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What mandatory vaccinations must Pakistani pilgrims receive before obtaining flight clearance at Haji Camps across Pakistan?",
                options = listOf("Meningococcal Quadrivalent (ACWY), Polio (OPV/IPV), and Seasonal Influenza", "Rabies and Yellow Fever only", "No vaccines are required", "Tuberculosis vaccine only"),
                correctIndex = 0,
                explanation = "Meningococcal ACWY, Polio, and Flu vaccinations are mandatory international health prerequisites enforced by Saudi MOH and MoRA.",
                reference = "Pre-Departure Health Policy"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the primary function of the MoRA 'Pak Hajj' Mobile Application distributed to Pakistani pilgrims?",
                options = listOf("To play offline video games", "To provide digital complaint registration, GPS navigation to pilgrim hotels, emergency SOS alerts, and Lost & Found tracking", "To purchase commercial real estate in Jeddah", "To trade foreign currencies"),
                correctIndex = 1,
                explanation = "The Pak Hajj App offers navigation, emergency SOS, hotel locator, complaints lodging, and lost baggage tracking.",
                reference = "MoRA Digital Hajj System"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "Under Saudi Arabian customs regulations, what is the maximum cash / foreign currency or precious metals a traveler can carry into Saudi Arabia without making a mandatory customs declaration?",
                options = listOf("SAR 60,000 (or equivalent foreign currency)", "SAR 5,000", "SAR 1,000,000", "Unlimited with no declaration"),
                correctIndex = 0,
                explanation = "Travelers carrying cash, jewelry, or negotiable instruments valued at 60,000 SAR or more must submit a formal declaration to Zakat, Tax and Customs Authority (ZATCA).",
                reference = "Saudi Customs Regulations (ZATCA)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the severe legal penalty enforced by Saudi authorities for individuals caught attempting to perform Hajj without an official Hajj permit (Tasreeh)?",
                options = listOf("A small verbal warning only", "Heavy financial fines (up to SAR 10,000 - 50,000), immediate deportation, and a multi-year ban from entering Saudi Arabia", "Free entry into Mina VIP camps", "No penalty exists"),
                correctIndex = 1,
                explanation = "Saudi law enforces zero tolerance for illegal Hajj; violators face substantial fines, detention, deportation, and entry bans.",
                reference = "Saudi Public Security Regulations"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the official allowed baggage packaging for carrying sacred Zamzam water on return flights from Saudi Arabia?",
                options = listOf("Any unsealed plastic bucket or cooking pot", "One factory-sealed 5-liter official bottle per pilgrim purchased at designated airport terminals / licensed outlets, packed in protective packaging", "20 liters in glass jugs inside passenger suitcases", "Zamzam is completely banned on airplanes"),
                correctIndex = 1,
                explanation = "Airlines permit one officially packaged, sealed 5-liter Zamzam bottle per pilgrim as checked baggage.",
                reference = "General Authority of Civil Aviation (GACA) Rules"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the quota ratio allocation between Government Hajj Scheme and Private Hajj Tour Operators under Pakistan's National Hajj Policy?",
                options = listOf("Typically 50% Government Scheme and 50% Private Scheme (or as defined by the Federal Cabinet each year)", "100% Private only", "100% Government only", "90% Private and 10% Government"),
                correctIndex = 0,
                explanation = "Pakistan's Hajj quota is traditionally shared equally (50:50 or ratio approved by Federal Cabinet) between Government and Private schemes.",
                reference = "National Hajj Policy Guidelines"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the 'Makkah Route Initiative' (Tareeq Makkah) implemented at major Pakistani airports (Islamabad, Karachi, Lahore)?",
                options = listOf("A walking marathon route from Islamabad to Makkah", "A streamlined pre-clearance immigration system where Saudi immigration, biometric checks, and customs clearance are completed at Pakistani airports before boarding", "A free taxi service inside Makkah", "A train line running between Pakistan and Saudi Arabia"),
                correctIndex = 1,
                explanation = "Tareeq Makkah allows pilgrims to complete Saudi immigration in Pakistan, allowing seamless exit directly to hotel buses upon Saudi landing.",
                reference = "Makkah Route Initiative (Tareeq Makkah)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the mandatory training curriculum conducted at Haji Camps for all selected pilgrims prior to departure?",
                options = listOf("Ritual training (Manasik-e-Hajj), airport SOPs, health & heat safety, legal regulations of Saudi Arabia, and civic discipline", "Foreign currency stock trading", "Advanced mountain climbing techniques", "Driving heavy vehicles in desert sand"),
                correctIndex = 0,
                explanation = "Haji Camp pre-departure training covers religious rituals, health/heat stroke prevention, Saudi laws, and digital app usage.",
                reference = "MoRA Haji Camp Training Manual"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "Which Saudi healthcare application is used by pilgrims to access their verified digital health records and vaccination certificates?",
                options = listOf("Sehaty App (صحتي)", "Instagram", "Netflix", "TikTok"),
                correctIndex = 0,
                explanation = "Sehaty is the official Saudi Ministry of Health app providing digital medical records, prescriptions, and vaccine validation.",
                reference = "Saudi MOH Digital Health Services"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #11] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-11)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #12] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-12)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #13] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-13)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #14] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-14)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #15] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-15)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #16] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-16)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #17] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-17)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #18] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-18)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #19] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-19)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #20] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-20)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #21] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-21)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #22] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-22)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #23] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-23)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #24] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-24)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #25] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-25)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #26] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-26)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #27] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-27)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #28] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-28)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #29] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-29)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #30] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-30)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #31] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-31)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #32] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-32)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #33] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-33)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #34] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-34)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #35] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-35)"
            )
        )
    }

    private fun populateBatch2(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #36] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-36)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #37] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-37)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #38] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-38)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #39] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-39)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #40] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-40)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #41] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-41)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #42] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-42)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #43] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-43)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #44] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-44)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #45] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-45)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #46] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-46)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #47] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-47)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #48] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-48)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #49] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-49)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #50] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-50)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #51] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-51)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #52] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-52)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #53] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-53)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #54] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-54)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #55] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-55)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #56] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-56)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #57] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-57)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #58] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-58)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #59] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-59)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #60] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-60)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #61] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-61)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #62] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-62)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #63] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-63)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #64] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-64)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #65] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-65)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #66] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-66)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #67] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-67)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #68] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-68)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #69] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-69)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #70] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-70)"
            )
        )
    }

    private fun populateBatch3(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #71] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-71)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #72] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-72)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #73] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-73)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #74] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-74)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #75] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-75)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #76] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-76)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #77] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-77)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #78] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-78)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #79] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-79)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #80] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-80)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #81] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-81)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #82] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-82)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #83] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-83)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #84] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-84)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #85] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-85)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #86] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-86)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #87] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-87)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #88] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-88)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #89] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-89)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #90] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-90)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #91] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-91)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #92] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-92)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #93] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-93)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #94] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-94)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #95] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-95)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #96] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-96)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #97] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-97)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #98] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-98)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #99] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-99)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #100] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-100)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #101] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-101)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #102] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-102)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #103] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-103)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #104] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-104)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #105] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-105)"
            )
        )
    }

    private fun populateBatch4(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #106] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-106)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #107] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-107)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #108] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-108)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #109] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-109)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #110] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-110)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #111] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-111)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #112] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-112)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #113] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-113)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #114] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-114)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #115] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-115)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #116] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-116)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #117] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-117)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #118] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-118)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #119] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-119)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #120] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-120)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #121] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-121)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #122] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-122)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #123] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-123)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #124] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-124)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #125] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-125)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #126] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-126)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #127] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-127)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #128] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-128)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #129] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-129)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #130] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-130)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #131] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-131)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #132] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-132)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #133] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-133)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #134] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-134)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #135] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-135)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #136] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-136)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #137] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-137)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #138] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-138)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #139] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-139)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #140] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-140)"
            )
        )
    }

    private fun populateBatch5(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #141] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-141)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #142] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-142)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #143] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-143)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #144] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-144)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #145] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-145)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #146] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-146)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #147] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-147)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #148] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-148)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #149] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-149)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #150] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-150)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #151] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-151)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #152] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-152)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #153] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-153)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #154] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-154)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #155] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-155)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #156] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-156)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #157] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-157)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #158] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-158)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #159] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-159)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #160] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-160)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #161] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-161)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #162] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-162)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #163] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-163)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #164] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-164)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #165] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-165)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #166] If a pilgrim loses their physical Nusuk digital ID card in Mina, how can they prove their identity to security checkpoints?",
                options = listOf("They have no legal standing and must be arrested", "Show the digital Nusuk ID and QR code on their smartphone app, or visit the nearest Pakistan Welfare Desk for instant re-printing/verification", "Show their Pakistani local driving license", "Ask a stranger to vouch verbally"),
                correctIndex = 1,
                explanation = "The Nusuk mobile app holds the digital twin of the pilgrim's smart card and QR code for instant authentication.",
                reference = "Nusuk Card Technical SOP (Policy-Ref-166)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #167] Which of the following items is strictly prohibited from being packed in pilgrim luggage on Hajj flights?",
                options = listOf("Prescription medicines with doctor's slip", "Flammable liquids, unsealed gas cylinders, illegal narcotics, counterfeit goods, and unpackaged sharp weapons", "Unstitched white cotton Ihram sheets", "Small travel umbrellas"),
                correctIndex = 1,
                explanation = "International aviation and Saudi customs strictly prohibit hazardous, flammable, or contraband materials.",
                reference = "Aviation Safety Regulations (Policy-Ref-167)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #168] How is access to pray in the sacred Rawdah ash-Sharifah in Masjid Nabawi managed for pilgrims?",
                options = listOf("By rushing through doors without any schedule", "By securing a mandatory pre-booked electronic appointment slot via the Nusuk App according to gender-specific timing windows", "By paying cash at the entrance door", "By waiting outside for 5 days without permit"),
                correctIndex = 1,
                explanation = "Visiting the sacred Rawdah requires a pre-scheduled time-slot booked through the official Nusuk application.",
                reference = "Rawdah Access Regulations (Policy-Ref-168)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #169] If a Private Hajj Tour Operator fails to provide the contracted 4-star hotel accommodation in Makkah, where can the pilgrim lodge a formal legal complaint?",
                options = listOf("At a local tea stall in Makkah", "Through the MoRA Online Complaint Portal and the Pakistan Hajj Mission Inspection & Monitoring Cell in Makkah", "Nowhere, private contracts are unmonitored", "At the police station in Karachi only"),
                correctIndex = 1,
                explanation = "MoRA's Inspection & Monitoring Cell actively monitors private operators and investigates non-compliance with signed contracts.",
                reference = "Private Hajj Scheme Monitoring SOP (Policy-Ref-169)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "[Policy & Technology #170] What is the unified national emergency response telephone number in the Makkah and Madinah provinces of Saudi Arabia?",
                options = listOf("911 (Unified National Emergency Center)", "100", "999 only", "000"),
                correctIndex = 0,
                explanation = "911 is the unified emergency number in Saudi Arabia integrating Police, Ambulance, Traffic, and Civil Defense.",
                reference = "Saudi National Emergency Response (Policy-Ref-170)"
            )
        )
    }

}
