package com.drtahir.studentkit.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 5
 * Subject: Management, Ethics & Situational Judgment (30 100% Unique MCQs)
 * Covers Husn-e-Akhlaq (Islamic Ethics), Crowd Management & Stampede Avoidance, Conflict Resolution, Elderly & Disabled Care, Heat Stress, and Team Supervision.
 */
object Moavineen1000Part5 {

    fun getManagementEthicsQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "An exhausted and anxious elderly pilgrim angrily shouts at a Moavin regarding delayed bus transport under 45°C heat. What is the ethically mandatory response under MORA Husn-e-Akhlaq guidelines?",
                options = listOf(
                    "Shout back and refuse to assist the pilgrim further",
                    "Remain calm, polite, offer cold water and shade, listen empathetically, reassure them, and resolve the transport delay through dispatch",
                    "Abandon the duty post immediately to avoid confrontation",
                    "Demand a written apology from the elderly pilgrim before offering assistance"
                ),
                correctIndex = 1,
                explanation = "Husn-e-Akhlaq (exemplary moral conduct) requires patience, emotional self-control, empathy, providing immediate comfort (shade/water), and problem resolution.",
                reference = "MORA Husn-e-Akhlaq & Code of Conduct Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "When a sudden pedestrian crowd surge occurs at a narrow exit ramp near the Jamarat complex, what field intervention prevents panic and crush accidents?",
                options = listOf(
                    "Form human safety chains with fellow Moavineen, direct crowd flow gently into side dispersal lanes, keep crowds moving unidirectionally, and prevent stopping in choke points",
                    "Block the ramp completely using heavy metal barriers",
                    "Run away from the crowd while shouting warnings",
                    "Instruct pilgrims to turn back against the incoming crowd flow"
                ),
                correctIndex = 0,
                explanation = "Crowd safety requires maintaining continuous forward motion, forming safety corridors, preventing counter-flow, and guiding surges into wider relief zones.",
                reference = "MORA Crowd Control & Safety Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "A confused elderly pilgrim with memory loss is found wandering alone in the Arafat heat away from their tent. What is the ethical step for the Moavin?",
                options = listOf(
                    "Escort the pilgrim gently to a shaded tent, verify identity details via wristband/locket, provide hydration/ORS, and contact their Maktab guide",
                    "Leave the pilgrim alone assuming their family will find them",
                    "Hand the pilgrim over to a commercial street vendor",
                    "Lock the pilgrim inside a bus until evening"
                ),
                correctIndex = 0,
                explanation = "Vulnerable, confused pilgrims must be safeguarded from heat exhaustion, given fluids, identified via MORA wristbands, and safely reunited with Maktab leads.",
                reference = "MORA Elderly & Disabled Pilgrim Care SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "Two supporting staff Moavineen engage in a heated verbal disagreement regarding shift duty hours at the Makkah sector office. How should the Supervisor resolve it?",
                options = listOf(
                    "Ignore the argument and let staff resolve it physically",
                    "Intervene calmly, separate both staff members to a private room, review the official duty roster objectively, and enforce fair shift rotation without favoritism",
                    "Fire both staff members immediately without inquiry",
                    "Side with the staff member from their home province"
                ),
                correctIndex = 1,
                explanation = "Supervisory conflict resolution demands immediate neutral intervention, private mediation, adherence to published rosters, and objective fairness.",
                reference = "MORA Supervisory Leadership Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "How should a Moavin maintain personal physical fitness and prevent heat exhaustion during a 12-hour outdoor shift in Mina summer temperatures?",
                options = listOf(
                    "Drink oral rehydration solutions (ORS) and water regularly, wear sun protection/shades, utilize short scheduled breaks, and monitor signs of dizziness",
                    "Avoid drinking water during duty hours to save time",
                    "Stand continuously in direct sunlight without head protection",
                    "Consume heavy greasy meals before outdoor shifts"
                ),
                correctIndex = 0,
                explanation = "Outdoor endurance requires proactive self-care: electrolyte hydration, sun gear, resting in shade during off-duty slots, and heat illness self-monitoring.",
                reference = "MORA Health & Physical Fitness Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "A wealthy private pilgrim offers a cash tip (Saudi Riyals) to a Moavin for jumping ahead of elderly pilgrims in a bus queue. What is the mandated action under MORA Integrity rules?",
                options = listOf(
                    "Accept the money and place them at the front of the queue",
                    "Politely refuse the cash gift, explain MORA's strict policy of equal service to all Hujjaj, and maintain fair queue sequence",
                    "Accept the cash and share it with the bus driver",
                    "Demand double the cash amount offered"
                ),
                correctIndex = 1,
                explanation = "Moavineen are strictly prohibited from accepting cash tips or bribes. All pilgrims must receive equal, dignified service without commercial discrimination.",
                reference = "MORA Integrity & Anti-Corruption Regulations"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "When entering female-designated tent enclosures in Mina to deliver lost luggage or inspect water facilities, what decorum must male Moavineen observe?",
                options = listOf(
                    "Walk in unannounced without knocking",
                    "Announce presence clearly, seek entry permission from female camp coordinators, ensure a female volunteer or staff accompanies if possible, and maintain modesty",
                    "Require female pilgrims to vacate the tent completely before entering",
                    "Send lost luggage inside by throwing it over tent walls"
                ),
                correctIndex = 1,
                explanation = "Respecting Islamic modesty and female privacy requires clear verbal announcement, permission, female staff accompaniment when available, and respectful conduct.",
                reference = "MORA Code of Conduct for Female Pilgrim Areas"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "During the peak movement from Arafat to Muzdalifah, field staff experience severe physical fatigue. What leadership practice prevents team burnout and maintains high morale?",
                options = listOf(
                    "Cancel all rest breaks and mandate continuous 24-hour standing duty",
                    "Schedule short 15-minute staggered rest breaks in shade, ensure hydration supplies are distributed to staff, and offer positive verbal encouragement",
                    "Threaten staff with salary deductions for showing fatigue",
                    "Leave the field site to rest in a private hotel room"
                ),
                correctIndex = 1,
                explanation = "Effective field leadership under high stress relies on proactive break rotation, hydration monitoring, and positive team reinforcement.",
                reference = "MORA Leadership & Team Resilience Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "According to Islamic teachings and MORA policy, what is the spiritual status and reward of serving pilgrims (Dhuyuf-ur-Rahman / Guests of Allah)?",
                options = listOf(
                    "It is merely a commercial job without spiritual significance",
                    "It is considered a noble act of worship (Ibadah) and national service, earning immense divine reward, forgiveness, and blessings",
                    "It is a punishment assigned by government departments",
                    "It is purely an opportunity for international tourism"
                ),
                correctIndex = 1,
                explanation = "Khidmat al-Hujjaj is viewed as a high spiritual privilege and Ibadah in Islam, serving the Guests of Allah with devotion.",
                reference = "MORA Spiritual & Service Philosophy Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "A dispute breaks out between two pilgrim groups inside a Mina tent over bed allocation. How should a Moavin de-escalate the situation?",
                options = listOf(
                    "Take sides with the larger group and force the smaller group out",
                    "Listen calmly to both parties, refer to the official Maktab bed allotment chart, mediate a fair resolution, and involve the Sector Supervisor if necessary",
                    "Use physical force to eject all arguing pilgrims from the tent",
                    "Ignore the dispute completely"
                ),
                correctIndex = 1,
                explanation = "De-escalation requires neutral listening, referencing official allocation documents, mediating calmly, and escalating to supervisors if unresolved.",
                reference = "MORA Dispute Mediation & Conflict SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "How should a Moavineen Supervisor handle a catering contractor delivering undercooked or spoiled food to a pilgrim camp?",
                options = listOf(
                    "Force pilgrims to eat the food regardless",
                    "Reject the substandard food batch immediately, document the incident with photographs, order emergency replacement meals from approved caterers, and file a formal penalty report",
                    "Cover up the incident to avoid administrative paperwork",
                    "Advise pilgrims to fast for the day"
                ),
                correctIndex = 1,
                explanation = "Pilgrim health is non-negotiable. Substandard food must be rejected, documented, replaced rapidly through backup caterers, and penalized legally.",
                reference = "MORA Catering Inspection & Food Safety SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "What ethical responsibility does a Moavin have regarding the privacy and personal photographs of pilgrims taken during field duty?",
                options = listOf(
                    "Post private photos or videos of distressed pilgrims on public social media for personal views",
                    "Strictly protect pilgrim dignity and privacy; never record or post photographs of vulnerable, ill, or distressed pilgrims on personal social media",
                    "Sell pilgrim photographs to news agencies",
                    "Use pilgrim photos for commercial advertising"
                ),
                correctIndex = 1,
                explanation = "MORA strictly forbids recording or uploading sensitive/distressed pilgrim media on social platforms, preserving dignity and privacy.",
                reference = "MORA Social Media & Privacy Policy"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "When guiding a group of elderly pilgrims through heavy crowd traffic at Masjid al-Haram, what walking formation should Moavineen maintain?",
                options = listOf(
                    "Walk far ahead at rapid speed, leaving elderly pilgrims behind",
                    "Form a protective front-and-rear guard with fellow Moavineen, matching the pace of the slowest pilgrim and keeping the group tightly unified",
                    "Walk behind the group without paying attention",
                    "Instruct pilgrims to run through Mataf area"
                ),
                correctIndex = 1,
                explanation = "Unified group pace with front and rear Moavineen anchors ensures no elderly pilgrims get detached or crushed in crowd surges.",
                reference = "MORA Pedestrian Escort Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "How does a Supervisor evaluate and reward outstanding field performance by supporting staff Moavineen during the Hajj mission?",
                options = listOf(
                    "Distribute cash rewards from personal pockets",
                    "Document daily exemplary performance in official appraisal logs, submit commendation reports to Director Moavineen, and recommend formal certificates of appreciation",
                    "Grant staff permanent unauthorized leave",
                    "Promote staff without government authorization"
                ),
                correctIndex = 1,
                explanation = "Official commendation logs and formal performance certificates issued by MORA Director HQ motivate staff and recognize exemplary Khidmat.",
                reference = "MORA Staff Performance Appraisal Policy"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "If a Moavin suffers an unexpected emotional meltdown or acute anxiety due to extreme fatigue and heat, what is the proper administrative protocol?",
                options = listOf(
                    "Conceal the condition and continue working dangerously",
                    "Inform the duty supervisor immediately, step back to a shaded rest facility, receive medical/psychological evaluation from Hajj Mission doctors, and resume duty upon recovery clearance",
                    "Abandon the Hajj Mission and book an independent flight home",
                    "Scream at pilgrims in public"
                ),
                correctIndex = 1,
                explanation = "Mental and physical health safety mandates immediate reporting to supervisors, rest in medical quarters, and formal clearance before redeployment.",
                reference = "MORA Occupational Health & Safety Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "How should Moavineen assist pilgrims who suffer from speech impairments, hearing disabilities, or language barriers?",
                options = listOf(
                    "Ignore them because communication is difficult",
                    "Use clear visual signage, universal hand gestures, translation apps on mobile phones, or seek assistance from multi-lingual Moavineen colleagues",
                    "Speak loudly and aggressively in local regional dialects",
                    "Refuse to guide them"
                ),
                correctIndex = 1,
                explanation = "Accessibility requires creative, patient communication: visual aids, translation tools, gentle gestures, and leveraging multi-lingual team members.",
                reference = "MORA Inclusive Care Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "What action should a Supervisor take if a Moavin is found making unauthorized political statements or staging demonstrations while in Saudi Arabia?",
                options = listOf(
                    "Support the political activities publicly",
                    "Immediately relieve the staff member of duty, report the violation to MORA HQ and Pakistani Consulate, and initiate repatriation/disciplinary proceedings under Saudi & Pakistani laws",
                    "Ignore the incident",
                    "Grant them extra vacation days"
                ),
                correctIndex = 1,
                explanation = "Saudi Arabian law strictly prohibits political speeches, banners, or demonstrations during Hajj. Violations lead to immediate relief of duty and legal repatriation.",
                reference = "MORA Diplomatic & Security Code of Conduct"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "What is the ethical procedure when handling a lost pilgrim's prescription medication found in a hotel or transit bus?",
                options = listOf(
                    "Throw the medicine in the trash bin",
                    "Keep the medicine in cool temperature, log the drug name/patient details in MORA Medical Portal, and dispatch it urgently to the pilgrim via field ambulance",
                    "Consume the medication personally",
                    "Sell the medicine to local pharmacies"
                ),
                correctIndex = 1,
                explanation = "Prescription medicines (especially insulin or cardiac drugs) are vital. Safe storage, emergency logging, and urgent field dispatch are critical.",
                reference = "MORA Medical Logistics & Ethics Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "How should Moavineen maintain hygiene and cleanliness around pilgrim camp entrances in Mina?",
                options = listOf(
                    "Throw trash bags directly into pedestrian walkways",
                    "Promote waste disposal in designated Saudi Municipality dumpsters, report overflowing trash containers to camp sanitation teams, and set a personal example of cleanliness",
                    "Burn trash inside tents",
                    "Blame Saudi authorities without taking action"
                ),
                correctIndex = 1,
                explanation = "Environmental hygiene (Pākī) is part of faith and prevents disease outbreaks in camp sectors.",
                reference = "MORA Sanitation & Hygiene Ethics"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "What is the primary role of a Moavineen Supervisor during shift handover meetings at field control stations?",
                options = listOf(
                    "Deliver personal lectures unrelated to field work",
                    "Brief incoming staff on current sector challenges, update lost pilgrim counts, verify communication radio status, review safety alerts, and assign specific duty posts",
                    "Collect personal money from incoming staff",
                    "Dismiss the meeting in 10 seconds without briefing"
                ),
                correctIndex = 1,
                explanation = "Shift briefings ensure operational continuity: passing key data, safety advisories, active missing cases, and specific post assignments.",
                reference = "MORA Supervisory Handover SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "How should a Moavin handle an elderly pilgrim who insists on carrying heavy luggage manually while walking to Mina?",
                options = listOf(
                    "Watch them struggle without intervening",
                    "Politely offer to carry or transport their heavy luggage via MORA luggage vans, reassuring them that their belongings will reach their tent safely",
                    "Forbid them from taking any belongings to Mina",
                    "Charge them a fee to carry the bag"
                ),
                correctIndex = 1,
                explanation = "Assisting elderly pilgrims with physical strain embodies compassionate Khidmat and prevents physical exhaustion or heart stress.",
                reference = "MORA Pilgrim Assistance Ethics"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "What is the ethical stance regarding punctuality for field deployment shifts during Hajj operations?",
                options = listOf(
                    "Arrive 2 hours late without explanation",
                    "Strict punctuality is mandatory; staff must report 15 minutes prior to shift commencement for roll call and handover briefing to ensure posts are never left unmanned",
                    "Punctuality is optional for government employees",
                    "Staff can choose shift times at their convenience"
                ),
                correctIndex = 1,
                explanation = "Continuous operational coverage requires arriving 15 minutes before shift start to conduct seamless handovers without leaving vulnerable posts unmonitored.",
                reference = "MORA Duty Punctuality & Discipline Rules"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "How should a Supervisor support a Moavin who is experiencing severe grief or anxiety due to a personal emergency back home in Pakistan?",
                options = listOf(
                    "Reprimand the staff member harshly for being distracted",
                    "Provide empathetic counseling, temporarily reassign them to lighter administrative indoor duties, and facilitate communication with family through MORA Welfare Cell",
                    "Send them to perform heavy physical labor in Arafat heat",
                    "Ignore the personal emergency completely"
                ),
                correctIndex = 1,
                explanation = "Supervisory empathy involves supportive reassignments, psychological support, and utilizing official welfare communication channels.",
                reference = "MORA Staff Welfare Regulations"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "What decorum must Moavineen observe when interacting with Saudi security forces (Amn al-Haram, Police, Jawazat) at checkpoints?",
                options = listOf(
                    "Argue aggressively with Saudi officers when stopped",
                    "Display utmost professional respect, present official credentials promptly, speak politely in basic Arabic/English, and abide by security directives",
                    "Refuse to show identity cards",
                    "Attempt to bypass police security barricades forcibly"
                ),
                correctIndex = 1,
                explanation = "Inter-agency cooperation with host nation security forces requires respect, clear identification, and compliance with local laws.",
                reference = "MORA Saudi Security Liaison Code"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "When assisting a pilgrim who has lost all their cash and identification documents, what psychological reassurance should a Moavin offer?",
                options = listOf(
                    "Tell the pilgrim that their situation is hopeless and nothing can be done",
                    "Reassure them with calm words, provide immediate shelter/food, explain MORA's recovery procedures, and escort them to the Welfare Cell for document replacement",
                    "Lend them private funds charging interest",
                    "Inform the hotel to evict them immediately"
                ),
                correctIndex = 1,
                explanation = "Calming distressed pilgrims, offering emotional safety, and taking them to MORA Welfare Cell for duplicate document issuance is standard practice.",
                reference = "MORA Psychological Support & Welfare SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "What is the policy regarding transparent financial accounting for emergency relief funds placed at the disposal of Sector Supervisors?",
                options = listOf(
                    "Spend money without keeping receipts or logs",
                    "Maintain itemized receipt registers for every emergency expenditure (e.g. emergency water purchase, local transport), signed by two witnessing officers, and submit for audit",
                    "Use emergency funds for personal entertainment",
                    "Keep remaining cash at the end of Hajj deployment"
                ),
                correctIndex = 1,
                explanation = "All public funds require strict financial integrity: itemized receipts, dual-witness signatures, and post-operation audit compliance.",
                reference = "MORA Financial Audit & Accounting SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "How should Moavineen handle an elderly pilgrim who insists on leaving the tent during a peak thermal warning (50°C heat wave)?",
                options = listOf(
                    "Allow them to walk out into the dangerous heat alone",
                    "Gently explain the severe health risks of heat stroke, offer indoor hydration, engage them politely until peak heat subsides, or arrange shaded transport if movement is essential",
                    "Lock them inside a dark closet",
                    "Argue disrespectfully with the pilgrim"
                ),
                correctIndex = 1,
                explanation = "Preventing heat stroke requires persuasive explanation, offering indoor comfort, and delaying outdoor travel until cooler hours.",
                reference = "MORA Heat Safety Mitigation Protocol"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Management & Ethics",
                question = "What is the ethical duty of a Moavin regarding lost items belonging to non-Pakistani foreign pilgrims?",
                options = listOf(
                    "Discard the items because they do not belong to Pakistani pilgrims",
                    "Treat all lost items with equal integrity, handing them over to Saudi Lost & Found or the respective country's Hajj mission desk",
                    "Keep the items for personal use",
                    "Sell the items at local markets"
                ),
                correctIndex = 1,
                explanation = "Honesty (Amanah) applies universally to all Guests of Allah regardless of national origin.",
                reference = "MORA Universal Ethics Policy"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Management & Ethics",
                question = "How should a Moavineen Supervisor address rumor-mongering or panic among pilgrims regarding flight delays or camp relocations?",
                options = listOf(
                    "Spread unverified rumors further to prepare pilgrims",
                    "Address pilgrims directly via official camp announcements, provide verified facts from MORA Control Room, dispel false rumors, and maintain calm",
                    "Hide in the office to avoid pilgrim questions",
                    "Blame government officials publicly"
                ),
                correctIndex = 1,
                explanation = "Crisis leadership demands transparent communication, dispelling panic through verified information, and maintaining calm in camp sectors.",
                reference = "MORA Crisis Communication Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Management & Ethics",
                question = "What core value defines the ultimate mission objective of Moavineen-e-Hujjaj?",
                options = listOf(
                    "Personal financial gain and leisure travel",
                    "Selfless service (Khidmat), empathy, integrity, discipline, and ensuring every Pakistani pilgrim performs Hajj safely, correctly, and comfortably",
                    "Achieving high political status",
                    "Establishing private commercial businesses in Makkah"
                ),
                correctIndex = 1,
                explanation = "The core mission of Moavineen-e-Hujjaj is selfless Khidmat, pilgrim safety, empathy, and professional facilitation.",
                reference = "MORA Mission Statement & Core Values"
            )
        )

        return list
    }
}
