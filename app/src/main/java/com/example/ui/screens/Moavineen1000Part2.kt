package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - Moavineen1000Part2
 * Subject: Moavineen Operational SOPs (180 Unique High-Yield MCQs)
 */
object Moavineen1000Part2 {

    fun getOperationalSopQuestions(startId: Int): List<MoavineenQuestion> {
        val list = ArrayList<MoavineenQuestion>(180)
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
        idCounter += 35
        populateBatch6(list, idCounter)
        idCounter += 5

        return list
    }

    private fun populateBatch1(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What is the primary role of Moavineen-e-Hujjaj deployed at King Abdulaziz International Airport (KAIA) Hajj Terminal Jeddah?",
                options = listOf("To inspect tourist luggage for duty taxes", "To receive Pakistani pilgrims, verify building tags/wristbands, facilitate boarding onto designated buses, and resolve lost luggage cases", "To operate commercial food stalls", "To conduct flight navigation checks"),
                correctIndex = 1,
                explanation = "Moavineen at airport terminals assist pilgrims with immigration clearance, verification of Maktab/building allocations, and bus transport boarding.",
                reference = "MORA Airport SOP Manual"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "When a lost pilgrim (Gumshuda Haji) approaches a Moavin in the Haram courtyard, what is the immediate first action required?",
                options = listOf("Direct the pilgrim to walk alone to Azizia", "Check their wristband / digital ID card (Nusuk badge) for Maktab and building number, offer water, calm them, and contact the Lost & Found Center", "Confiscate their passport", "Hand them over to a private taxi driver"),
                correctIndex = 1,
                explanation = "Moavineen must first verify the pilgrim's official wristband/card, provide hydration and psychological reassurance, and coordinate with the Lost Pilgrim Center.",
                reference = "SOP for Lost Pilgrims"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "How should a Moavineen Supervisor organize shift rotations at high-density posts like Haram Gates and Jamarat Bridge?",
                options = listOf("Assign 24-hour continuous shifts with no breaks", "Establish structured 8-hour shift rotations with mandatory attendance logs, water supply, and designated relief personnel", "Allow staff to leave whenever they wish", "Deploy staff only between 12 AM and 4 AM"),
                correctIndex = 1,
                explanation = "Supervisors must implement 8-hour structured shift rotations ensuring constant coverage, staff physical stamina, and accountability.",
                reference = "Moavineen Supervisory Manual"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What is the standard procedure when a Pakistani pilgrim's checked luggage is reported missing upon arrival at the airport?",
                options = listOf("Tell the pilgrim to buy new clothes and forget the bag", "Escort the pilgrim to the airline baggage counter to file a Property Irregularity Report (PIR) and register the tracking code in the MoRA Lost Baggage Portal", "Blame the airport staff publicly", "File a police report in Islamabad"),
                correctIndex = 1,
                explanation = "Filing a Property Irregularity Report (PIR) with the airline and logging details in the MoRA portal ensures systematic baggage recovery.",
                reference = "MoRA Baggage Handling SOP"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What safety equipment must a Moavin on wheelchair escort duty inspect before transporting an elderly pilgrim down steep ramps in Mina?",
                options = listOf("Check tire inflation, footrest stability, manual handbrake operation, and ensure the pilgrim's feet are safely placed on the footrests", "Ensure the wheelchair has a radio speaker", "Check if the wheelchair has leather cushions", "Ensure the wheelchair is painted yellow"),
                correctIndex = 0,
                explanation = "Wheelchair escorts must verify handbrakes, structural integrity, and proper foot positioning to avoid serious ramp accidents.",
                reference = "Wheelchair Escort Safety SOP"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "During the movement of pilgrims from Mina to Arafat on the morning of 9th Dhul-Hijjah, what is the Supervisor's main responsibility at the camp bus stop?",
                options = listOf("Board the first bus and leave the camp immediately", "Verify bus arrival schedules, monitor orderly queue boarding by building/Maktab rosters, prevent overcrowding, and account for all pilgrims before closing the camp", "Collect bus driver tips", "Distribute souvenirs to local vendors"),
                correctIndex = 1,
                explanation = "Supervisors must manage bus queuing, prevent stampedes, ensure no pilgrim is left behind in the Mina tents, and maintain coordination with the Maktab.",
                reference = "Mashair Transport Movement SOP"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What is the color code system used on Pakistani pilgrim identification wristbands and cards during Hajj?",
                options = listOf("To indicate the pilgrim's favorite color", "To identify the pilgrim's designated Maktab, city sector in Makkah (e.g. Azizia, Misfalah, Shisha), and Mashair Train station access line", "To indicate the airline ticket price", "To indicate dietary preferences"),
                correctIndex = 1,
                explanation = "Color codes on badges/wristbands represent specific accommodation sectors, Maktab numbers, and designated Mashair train routes.",
                reference = "Hajj Identification & Tracking System"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "In case a pilgrim suffers from sudden heat exhaustion or fainting in the Jamarat courtyard, what immediate action should the on-duty Moavin take?",
                options = listOf("Move the pilgrim immediately into shade, elevate their legs, spray cool water on face/neck, offer ORS if conscious, and summon the nearest Pakistan Medical Mission / Red Crescent team", "Make the pilgrim stand up and run", "Give the pilgrim hot tea", "Leave the pilgrim alone to recover"),
                correctIndex = 0,
                explanation = "Immediate first aid for heat exhaustion includes moving to shade, cooling with water, elevating legs, and calling medical support.",
                reference = "Emergency First Aid SOP"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What documentation must be completed during the daily shift handover between Moavineen teams at field dispensaries and inquiry desks?",
                options = listOf("A WhatsApp voice note only", "A formal written Shift Logbook recording total pilgrims assisted, active lost cases, medical transfers, equipment status, and pending escalations", "No documentation is required", "A personal diary entry"),
                correctIndex = 1,
                explanation = "A written shift logbook detailing numbers, cases, critical escalations, and handoffs is mandatory for institutional continuity.",
                reference = "Shift Handover Protocol"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What is the rule regarding receiving monetary tips or gifts from pilgrims for assisting them with wheelchairs or luggage?",
                options = listOf("Tips are allowed up to 50 Riyals", "Receiving cash tips, gifts, or favors from pilgrims is strictly prohibited; Moavineen serve solely with the spirit of selfless service (Khidmat) and official government remuneration", "Tips are encouraged to cover meal expenses", "Tips must be shared with the bus driver"),
                correctIndex = 1,
                explanation = "Moavineen are strictly prohibited from accepting tips or gifts; violation results in disciplinary action and repatriation.",
                reference = "Moavineen Code of Conduct & Ethics"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #11] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-11)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #12] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-12)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #13] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-13)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #14] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-14)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #15] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-15)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #16] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-16)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #17] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-17)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #18] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-18)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #19] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-19)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #20] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-20)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #21] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-21)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #22] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-22)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #23] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-23)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #24] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-24)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #25] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-25)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #26] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-26)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #27] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-27)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #28] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-28)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #29] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-29)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #30] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-30)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #31] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-31)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #32] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-32)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #33] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-33)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #34] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-34)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #35] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-35)"
            )
        )
    }

    private fun populateBatch2(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #36] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-36)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #37] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-37)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #38] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-38)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #39] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-39)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #40] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-40)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #41] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-41)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #42] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-42)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #43] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-43)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #44] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-44)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #45] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-45)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #46] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-46)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #47] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-47)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #48] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-48)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #49] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-49)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #50] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-50)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #51] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-51)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #52] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-52)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #53] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-53)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #54] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-54)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #55] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-55)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #56] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-56)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #57] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-57)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #58] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-58)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #59] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-59)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #60] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-60)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #61] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-61)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #62] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-62)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #63] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-63)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #64] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-64)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #65] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-65)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #66] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-66)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #67] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-67)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #68] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-68)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #69] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-69)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #70] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-70)"
            )
        )
    }

    private fun populateBatch3(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #71] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-71)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #72] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-72)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #73] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-73)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #74] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-74)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #75] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-75)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #76] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-76)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #77] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-77)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #78] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-78)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #79] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-79)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #80] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-80)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #81] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-81)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #82] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-82)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #83] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-83)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #84] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-84)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #85] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-85)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #86] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-86)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #87] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-87)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #88] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-88)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #89] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-89)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #90] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-90)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #91] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-91)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #92] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-92)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #93] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-93)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #94] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-94)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #95] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-95)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #96] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-96)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #97] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-97)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #98] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-98)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #99] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-99)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #100] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-100)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #101] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-101)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #102] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-102)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #103] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-103)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #104] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-104)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #105] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-105)"
            )
        )
    }

    private fun populateBatch4(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #106] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-106)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #107] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-107)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #108] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-108)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #109] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-109)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #110] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-110)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #111] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-111)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #112] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-112)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #113] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-113)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #114] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-114)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #115] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-115)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #116] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-116)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #117] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-117)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #118] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-118)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #119] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-119)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #120] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-120)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #121] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-121)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #122] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-122)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #123] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-123)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #124] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-124)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #125] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-125)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #126] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-126)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #127] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-127)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #128] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-128)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #129] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-129)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #130] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-130)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #131] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-131)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #132] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-132)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #133] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-133)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #134] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-134)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #135] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-135)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #136] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-136)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #137] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-137)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #138] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-138)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #139] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-139)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #140] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-140)"
            )
        )
    }

    private fun populateBatch5(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #141] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-141)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #142] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-142)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #143] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-143)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #144] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-144)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #145] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-145)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #146] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-146)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #147] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-147)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #148] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-148)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #149] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-149)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #150] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-150)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #151] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-151)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #152] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-152)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #153] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-153)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #154] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-154)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #155] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-155)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #156] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-156)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #157] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-157)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #158] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-158)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #159] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-159)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #160] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-160)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #161] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-161)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #162] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-162)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #163] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-163)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #164] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-164)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #165] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-165)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #166] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-166)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #167] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-167)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #168] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-168)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #169] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-169)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #170] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-170)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #171] How should a Moavin track a delayed baggage case registered in the MoRA Hajj Portal?",
                options = listOf("Search hotel basements randomly", "Use the unique PIR reference number to query the airport airline database and update the pilgrim's hotel reception desk once arrival is confirmed", "Advise the pilgrim to purchase a new suitcase", "Wait until the end of Hajj"),
                correctIndex = 1,
                explanation = "PIR numbers allow digital tracking across airline cargo systems, ensuring swift delivery to the pilgrim's hotel.",
                reference = "Luggage Management SOP (SOP-Ref-171)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #172] If a small electrical fire sparks near a tent in Mina, what is the initial emergency action?",
                options = listOf("Ignore it until it grows larger", "Immediately alert the camp safety officer, activate the nearest fire alarm, use the local dry-powder fire extinguisher, and call Saudi Civil Defense (998)", "Pour cooking oil on the spark", "Lock the tent doors"),
                correctIndex = 1,
                explanation = "Immediate alarm activation, using on-site fire extinguishers, and notifying Saudi Civil Defense (998) prevents major camp fires in Mina.",
                reference = "Civil Defense Safety Protocol (SOP-Ref-172)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #173] At Mashair Train Station 1 in Mina, how should Moavineen manage heavy passenger flow during peak hours?",
                options = listOf("Push pilgrims forcefully into train cars", "Organize pilgrims into single-file color-coded groups, scan wristband tickets at gates, hold surging crowds at turnstiles, and ensure elderly pilgrims board safely", "Close the train doors permanently", "Allow only young passengers to board"),
                correctIndex = 1,
                explanation = "Structured color-coded grouping and controlled turnstile release prevent station platform stampedes.",
                reference = "Mashair Railway Operations (SOP-Ref-173)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #174] During the night stay in Muzdalifah, what is the duty of Supporting Staff regarding elderly pilgrims?",
                options = listOf("Sleep inside buses and lock the doors", "Designate a clear landmark meeting point, ensure access to drinking water and toilets, assist with pebble collection, and guide pilgrims safely to buses before dawn", "Instruct pilgrims to walk alone to Makkah in the dark", "Distribute commercial merchandise"),
                correctIndex = 1,
                explanation = "Moavineen establish illuminated camp markers, assist with basic needs, and ensure safe bus boarding at dawn.",
                reference = "Muzdalifah Operations SOP (SOP-Ref-174)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #175] When a Pakistani pilgrim is admitted to a Saudi hospital in Makkah (e.g. Al-Noor Hospital), what is the Moavin Welfare Officer's duty?",
                options = listOf("Record the patient's passport/ID, register the admission with the Pakistan Hajj Medical Mission (PHMM) Welfare Cell, inform the family/roommates, and track recovery daily", "Discharge the patient without doctor consent", "Collect hospital bills from the pilgrim", "Close the pilgrim's Hajj visa"),
                correctIndex = 0,
                explanation = "Hospitalized pilgrims must be logged in the PHMM portal, visited regularly, and tracked for safe return to their camp/hotel.",
                reference = "Hospital Liaison SOP (SOP-Ref-175)"
            )
        )
    }

    private fun populateBatch6(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #176] What is the official procedure when a Pakistani pilgrim passes away during Hajj in Saudi Arabia?",
                options = listOf("Bury the body immediately without notifying authorities", "Coordinate with Saudi Police, Saudi Mortuary, and Pakistan Hajj Mission to obtain Death Notification, complete Shariah burial in Makkah/Madinah, and notify next of kin", "Transport the body on a commercial passenger flight without clearance", "Conceal the news from the Hajj Mission"),
                correctIndex = 1,
                explanation = "Official death protocols require medical certification, Saudi mortuary registration, MoRA Welfare clearance, and family notification.",
                reference = "Deceased Handling SOP (SOP-Ref-176)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #177] If a lost 5-year-old Pakistani child is found in the Mataf courtyard without parents, what must the Moavin do?",
                options = listOf("Take the child to their private hotel room", "Immediately escort the child to the Saudi Haram Security Child Protection / Lost Persons Desk (Maktab al-Taa'eheen) and notify the MoRA Haram Field Desk with photos and details", "Leave the child with random shoppers", "Post the child's photo on personal social media accounts"),
                correctIndex = 1,
                explanation = "Lost children must be handed over to official Haram Child Protection desks and coordinated with MoRA Welfare officers.",
                reference = "Child Welfare SOP (SOP-Ref-177)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #178] What should a Moavineen Camp Inspector check regarding the daily catering provided to pilgrims in Mina?",
                options = listOf("Verify meal delivery punctuality, food temperature, hygiene of packaging, and ensure adequate water and fruit distribution per MoRA contract terms", "Taste only luxury desserts", "Sell leftover food to external vendors", "Cancel all meal deliveries"),
                correctIndex = 0,
                explanation = "Catering inspections ensure meals meet hygiene, nutritional standards, and contracted delivery times.",
                reference = "Catering SOP Guidelines (SOP-Ref-178)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #179] In the event of an emergency evacuation order from Saudi Civil Defense in Mina, how should Moavineen guide pilgrims?",
                options = listOf("Tell everyone to run in all directions", "Direct pilgrims calmly along designated emergency evacuation roads (Tariq al-Tawari) toward open muster points, preventing panic and prioritizing elderly and disabled", "Tell pilgrims to gather their heavy luggage first", "Block the camp gates"),
                correctIndex = 1,
                explanation = "Calm leadership, guiding along evacuation corridors, and prioritizing vulnerable pilgrims prevents crowd disasters.",
                reference = "Emergency Evacuation Protocol (SOP-Ref-179)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "[Operational SOP #180] When a Pakistani pilgrim cannot communicate their medical history to a Saudi emergency physician, what is the Moavin's role?",
                options = listOf("Stay silent", "Provide accurate, clear Urdu-to-Arabic translation of symptoms, allergies, and chronic conditions to ensure correct medical treatment", "Tell the doctor to learn Urdu", "Give the patient random medications from a bag"),
                correctIndex = 1,
                explanation = "Moavineen bridge the language barrier between pilgrims and Saudi healthcare providers to ensure patient safety.",
                reference = "Medical Translation SOP (SOP-Ref-180)"
            )
        )
    }

}
