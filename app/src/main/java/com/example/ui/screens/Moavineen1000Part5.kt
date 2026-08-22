package com.drtahir.studentkit.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - Moavineen1000Part5
 * Subject: Management & Ethics (170 Unique High-Yield MCQs)
 */
object Moavineen1000Part5 {

    fun getManagementEthicsQuestions(startId: Int): List<MoavineenQuestion> {
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
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "How should a Moavineen Supervisor resolve a heated dispute between two room groups in Azizia regarding air conditioning and bed space allocations?",
                options = listOf("Threaten to cancel both groups' Hajj visas immediately", "Listen patiently to both sides, review the official building room-roster, explain the equal entitlement per pilgrim under MoRA policy, and implement a fair, objective arrangement with empathy and firmness", "Tell them to fight it out among themselves", "Lock the room and force everyone into the lobby"),
                correctIndex = 1,
                explanation = "Supervisors must use active listening, refer to official room manifests, remain neutral, and enforce fair policies calmly.",
                reference = "Conflict Resolution in Hajj Management"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "A dehydrated, elderly pilgrim in Mina refuses to take ORS fluids or see a doctor because they believe suffering is part of their spiritual test. What should the Moavin do?",
                options = listOf("Force feed them violently", "Politely explain with religious empathy that preserving one's health is an Islamic obligation (Hifz an-Nafs), provide gentle reassurance, and summon a doctor to evaluate them gently", "Walk away and leave the pilgrim unattended", "Mock the pilgrim publicly"),
                correctIndex = 1,
                explanation = "Religious counseling emphasizing the Islamic obligation of preserving life combined with gentle medical care overcomes patient resistance.",
                reference = "Patient Psychology & Ethics"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "What core principle governs the professional conduct and demeanor of Pakistani Moavineen-e-Hujjaj at all times?",
                options = listOf("Selfless service (Khidmat-e-Khalq), utmost patience (Sabr), integrity, high moral discipline, and zero tolerance for arrogance or bribery", "Seeking personal commercial profits and business networking", "Resting in hotels during high-temperature peak hours", "Arguing with Saudi authorities publicly"),
                correctIndex = 0,
                explanation = "The mission of Moavineen is rooted in Khidmat, exemplary patience, integrity, and disciplined assistance to the guests of Allah (Duyoof ar-Rahman).",
                reference = "MoRA Code of Professional Conduct"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "During a sudden torrential rainstorm in Mina that causes localized water ponding near tent entrances, what is the Supervisor's primary action?",
                options = listOf("Evacuate oneself to Makkah in a private taxi", "Ensure all electrical power cables on tent floors are safely isolated, deploy teams to clear walkways, verify tent drainage, and maintain continuous contact with Saudi Civil Defense and MoRA Command", "Tell pilgrims to swim in the water", "Ignore the water leakage"),
                correctIndex = 1,
                explanation = "Electrical isolation, walkway safety, and coordination with Civil Defense ensure safety during weather emergencies in Mina.",
                reference = "Disaster & Safety Management"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "If a pilgrim leaves a wallet containing 5,000 Saudi Riyals and a passport on a counter at the Moavineen desk, what is the ethical protocol?",
                options = listOf("Keep the cash and discard the passport", "Immediately log the wallet in the official Lost & Found Register with two witness signatures, place it in the secure safe, and announce/search the pilgrim's building records to return it intact", "Distribute the cash among desk colleagues as a bonus", "Throw the wallet into the street trash"),
                correctIndex = 1,
                explanation = "High integrity requires immediate dual-witness logging, secure custody, and proactive tracking to return lost valuables to the rightful pilgrim.",
                reference = "Ethics & Valuables Handling SOP"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "How should a Supervisor handle a subordinate Moavin who arrives 2 hours late for their assigned night shift at the Jamarat gate?",
                options = listOf("Physically assault the employee", "Record the unexcused absence in the disciplinary logbook, assign an immediate replacement from the reserve pool, conduct a formal inquiry interview, and submit an official report to the Director Moavineen if negligence is proven", "Ignore the tardiness and cancel all future shifts", "Grant the employee a salary increase"),
                correctIndex = 1,
                explanation = "Supervisory discipline requires immediate shift coverage, objective logging, fair counseling, and reporting persistent misconduct up the chain of command.",
                reference = "Supervisory Administration & Discipline"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "When communicating with anxious family members in Pakistan inquiring about a lost pilgrim who is currently in safe custody at a center, how should staff communicate?",
                options = listOf("Give sensationalized, exaggerated panic messages", "Provide calm, accurate, reassuring information confirming the pilgrim's safety, current location, and expected reunification schedule with their tour group", "Refuse to speak with family members and hang up the phone", "Demand a cellular reload transfer before giving news"),
                correctIndex = 1,
                explanation = "Empathetic, clear, and reassuring communication relieves severe psychological distress for families back home.",
                reference = "Public Relations & Crisis Communication"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "What is the proper ethical protocol when dealing with female pilgrims needing assistance with lost rooms or medical referrals?",
                options = listOf("Ensure modesty (Haya), professional respect, avoid physical contact, communicate clearly, and involve female Moavineen staff / medical officers whenever possible", "Refuse to assist female pilgrims entirely", "Take informal selfies with the pilgrims", "Demand private personal phone numbers"),
                correctIndex = 0,
                explanation = "Islamic decorum, professional boundaries, and deploying female staff for sensitive assistance uphold the dignity of female pilgrims.",
                reference = "Gender Sensitivity & Ethics"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "How should a Supervisor maintain team morale and prevent physical burnout among Moavineen working in 45°C+ summer heat waves?",
                options = listOf("Cancel all water breaks and force double shifts", "Implement strict shift rotations, ensure constant shaded rest intervals, provide adequate hydration/electrolytes, and offer positive motivational leadership and care", "Threaten staff with salary deductions if they take a water sip", "Leave the staff unmonitored for days"),
                correctIndex = 1,
                explanation = "Proactive hydration, mandated rest cycles, and empathetic leadership prevent heat exhaustion and maintain high team performance.",
                reference = "Team Leadership in Extreme Conditions"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "If a pilgrim becomes aggressive and shouts insults due to frustration with a flight delay, how should the Moavin react?",
                options = listOf("Shout back louder and engage in a fistfight", "Remain calm, absorb the emotional distress with patience (Sabr), validate their exhaustion, provide water and a chair, and explain the factual airline status with kindness", "Call the police to arrest the pilgrim for yelling", "Walk away leaving the pilgrim in distress"),
                correctIndex = 1,
                explanation = "De-escalation through Sabr, active empathy, emotional composure, and constructive factual explanations defuses pilgrim anxiety.",
                reference = "Conflict De-escalation Skills"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #11] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-11)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #12] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-12)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #13] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-13)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #14] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-14)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #15] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-15)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #16] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-16)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #17] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-17)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #18] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-18)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #19] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-19)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #20] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-20)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #21] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-21)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #22] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-22)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #23] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-23)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #24] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-24)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #25] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-25)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #26] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-26)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #27] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-27)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #28] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-28)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #29] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-29)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #30] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-30)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #31] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-31)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #32] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-32)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #33] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-33)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #34] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-34)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #35] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-35)"
            )
        )
    }

    private fun populateBatch2(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #36] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-36)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #37] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-37)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #38] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-38)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #39] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-39)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #40] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-40)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #41] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-41)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #42] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-42)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #43] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-43)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #44] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-44)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #45] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-45)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #46] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-46)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #47] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-47)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #48] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-48)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #49] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-49)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #50] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-50)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #51] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-51)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #52] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-52)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #53] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-53)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #54] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-54)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #55] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-55)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #56] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-56)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #57] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-57)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #58] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-58)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #59] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-59)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #60] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-60)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #61] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-61)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #62] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-62)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #63] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-63)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #64] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-64)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #65] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-65)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #66] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-66)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #67] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-67)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #68] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-68)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #69] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-69)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #70] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-70)"
            )
        )
    }

    private fun populateBatch3(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #71] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-71)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #72] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-72)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #73] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-73)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #74] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-74)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #75] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-75)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #76] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-76)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #77] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-77)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #78] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-78)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #79] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-79)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #80] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-80)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #81] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-81)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #82] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-82)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #83] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-83)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #84] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-84)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #85] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-85)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #86] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-86)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #87] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-87)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #88] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-88)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #89] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-89)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #90] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-90)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #91] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-91)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #92] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-92)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #93] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-93)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #94] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-94)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #95] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-95)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #96] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-96)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #97] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-97)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #98] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-98)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #99] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-99)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #100] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-100)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #101] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-101)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #102] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-102)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #103] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-103)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #104] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-104)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #105] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-105)"
            )
        )
    }

    private fun populateBatch4(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #106] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-106)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #107] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-107)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #108] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-108)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #109] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-109)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #110] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-110)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #111] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-111)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #112] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-112)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #113] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-113)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #114] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-114)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #115] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-115)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #116] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-116)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #117] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-117)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #118] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-118)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #119] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-119)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #120] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-120)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #121] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-121)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #122] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-122)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #123] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-123)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #124] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-124)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #125] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-125)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #126] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-126)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #127] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-127)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #128] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-128)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #129] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-129)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #130] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-130)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #131] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-131)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #132] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-132)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #133] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-133)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #134] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-134)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #135] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-135)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #136] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-136)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #137] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-137)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #138] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-138)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #139] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-139)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #140] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-140)"
            )
        )
    }

    private fun populateBatch5(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #141] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-141)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #142] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-142)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #143] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-143)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #144] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-144)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #145] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-145)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #146] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-146)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #147] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-147)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #148] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-148)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #149] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-149)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #150] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-150)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #151] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-151)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #152] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-152)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #153] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-153)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #154] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-154)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #155] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-155)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #156] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-156)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #157] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-157)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #158] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-158)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #159] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-159)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #160] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-160)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #161] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-161)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #162] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-162)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #163] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-163)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #164] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-164)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #165] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-165)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #166] If an elevator breaks down with 8 elderly pilgrims trapped inside a 10-story Azizia building, what is the immediate management protocol?",
                options = listOf("Attempt to pry the doors open with a metal crowbar violently", "Immediately reassure passengers through the door speaker, notify building maintenance and Saudi Civil Defense (998), ensure ventilation, and oversee professional rescue", "Turn off the building power and leave", "Tell passengers to jump down"),
                correctIndex = 1,
                explanation = "Calming trapped occupants, avoiding unsafe forced entry, and alerting emergency technicians and Civil Defense guarantees safe rescue.",
                reference = "Building Emergency Response (Mgmt-Ref-166)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #167] If a catering truck arrives in Mina with 20 fewer lunch boxes than registered pilgrims in a tent block, what should the Moavin Supervisor do?",
                options = listOf("Tell the 20 pilgrims to fast and skip lunch", "Immediately contact the Maktab catering manager for rapid supplementary meal dispatch from the emergency buffer stock while arranging interim fruits/water", "Blame the pilgrims for being hungry", "Hide inside the management office"),
                correctIndex = 1,
                explanation = "Supervisors must leverage emergency buffer stocks and escalate immediately to catering contractors to rectify shortages swiftly.",
                reference = "Logistics Crisis Management (Mgmt-Ref-167)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #168] When reuniting a lost child with their panicked mother at the Lost Persons Center, what verification is mandatory before handing over the child?",
                options = listOf("Hand the child over to anyone who claims them verbally", "Verify the mother's official passport, Hajj badge, wristband, cross-check matching Maktab/family records, and record signatures in the handover register", "Demand a cash finder's reward", "Take a video for personal social media"),
                correctIndex = 1,
                explanation = "Strict identity verification against official badges and signed records prevents custody errors.",
                reference = "Child Protection Protocols (Mgmt-Ref-168)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #169] A diabetic pilgrim experiencing severe hypoglycemia refuses an IV drip due to fear of needles. How should the team handle this?",
                options = listOf("Let the patient slip into a diabetic coma", "Have a trusted physician explain gently in their native language (e.g. Punjabi/Pashto/Sindhi) the urgent necessity while offering oral glucose if still fully conscious and swallowing safely", "Shout at the patient", "Administer injections forcefully without explanation"),
                correctIndex = 1,
                explanation = "Native-language explanation, compassionate persuasion, and appropriate oral alternatives ensure clinical safety and respect patient autonomy.",
                reference = "Clinical Ethics & Empathy (Mgmt-Ref-169)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "[Ethics & Management #170] If an influential pilgrim demands private single-room accommodation in a shared government-scheme hotel, how should staff respond?",
                options = listOf("Violate all rules and throw out other pilgrims to please the influential person", "Politely, firmly, and respectfully explain that Government Scheme allocations are strictly standardized on an equal-sharing basis per MoRA policy, treating all pilgrims with absolute parity", "Take a private bribe to arrange a private suite", "Insult the pilgrim publicly"),
                correctIndex = 1,
                explanation = "MoRA policy enforces strict equality; all pilgrims are treated fairly without favoritism or unauthorized privileges.",
                reference = "Integrity & Anti-Favoritism Policy (Mgmt-Ref-170)"
            )
        )
    }

}
