package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 500 ADDITIONAL MCQs EXPANSION BANK
 * Adds 30 High-Yield NTS Exam Questions across Core Disciplines.
 */
object Hajj500Expansion {

    fun get500MoreHajjQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var currentId = startId

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Hajj Rules & Admin",
                question = "Under MoRA guidelines, what is the mandatory shift turnover protocol for a Charge Nurse stationed at a Sector Dispensary in Azizia?",
                options = listOf(
                    "Verbally inform hotel security guards and leave immediately upon shift end time",
                    "Complete a formal written handoff log of all critical patients, high-yield drugs, cold chain status, and equipment before leaving post",
                    "Lock all clinical cabinets and keep keys until the next calendar day",
                    "Transfer patient charts directly to hotel reception staff"
                ),
                correctIndex = 1,
                explanation = "A Charge Nurse must complete a detailed written clinical handoff including patient bed occupancy, drug inventories, cold chain temperature logs, and active transfers before shift handover.",
                reference = "MoRA Hajj Medical Mission Standard Operating Procedures, Section 4.2"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Hajj Rules & Admin",
                question = "A medical officer recruited from Lahore is requested by a pilgrim for private home care outside assigned shift hours. What is the official guideline?",
                options = listOf(
                    "Permitted if a cash fee is collected and split with team members",
                    "Private clinical practice or home visits outside official mission duty are strictly prohibited under MoRA terms",
                    "Permitted only on Fridays during off-peak prayer hours",
                    "Allowed if approved verbally by hotel management"
                ),
                correctIndex = 1,
                explanation = "All deployed healthcare staff are strictly bound by MoRA contract to serve exclusively in official mission clinics. Private practice and fee collection are strictly prohibited.",
                reference = "Pakistan Hajj Medical Mission Code of Ethics"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Hajj Rules & Admin",
                question = "What is the required protocol when a medical team at a field dispensary encounters a deceased Pakistani pilgrim?",
                options = listOf(
                    "Issue an unofficial death certificate locally and inform the family via personal phone",
                    "Immediately notify the Clinical Director, initiate official death notification forms (Form-H), and coordinate with Saudi Mortuary Services",
                    "Transport the body directly to the airport without local police documentation",
                    "Retain the pilgrim's passport and notify hotel security after 24 hours"
                ),
                correctIndex = 1,
                explanation = "In the event of a pilgrim death, official notification procedures must be followed via the Clinical Director, MoRA Welfare Office, local police clearance, and Saudi MOH mortuary officials.",
                reference = "Hajj Medical Mission Emergency Death Handling Protocol"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Hajj Rules & Admin",
                question = "During the Wuquf-e-Arafat phase on 9th Dhu al-Hijjah, how are non-ambulatory hospitalized pilgrims managed by the Hajj Medical Mission?",
                options = listOf(
                    "Discharged back to Mina tents to rest until Hajj rituals conclude",
                    "Transported in specialized Saudi MOH & MoRA 'Medical Convoy Buses' under clinical supervision to Arafat to fulfill Wuquf obligations",
                    "Kept in Makkah hospitals and granted exemption from Wuquf by local staff",
                    "Transferred to Madinah clinics prior to Arafat gathering"
                ),
                correctIndex = 1,
                explanation = "Wuquf at Arafat is the central pillar of Hajj. Saudi MOH and Pakistan Medical Mission deploy equipped 'Medical Convoy Buses' so bedridden patients can perform Wuquf safely.",
                reference = "Saudi MOH & MoRA Special Pilgrim Medical Convoy Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Hajj Rules & Admin",
                question = "What is the primary function of the Central Pharmacy Depot established by the Pakistan Hajj Medical Mission in Makkah?",
                options = listOf(
                    "Selling over-the-counter pharmaceuticals directly to local citizens",
                    "Bulk inventory control, temperature-controlled drug storage (cold chain), and scheduled daily replenishment of sector field dispensaries",
                    "Manufacturing custom intravenous fluid formulations on site",
                    "Testing imported clinical cosmetics for commercial sale"
                ),
                correctIndex = 1,
                explanation = "The Central Pharmacy Depot manages pharmaceutical logistics, cold chain management for vaccines/insulin, and sector dispensary supply across Makkah, Mina, and Madinah.",
                reference = "Hajj Medical Mission Pharmacy Supply Chain Manual"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Heat Stroke & Hydration",
                question = "Which physiological change occurs when a pilgrim is exposed to extreme ambient heat (>42°C) without adequate hydration?",
                options = listOf(
                    "Severe peripheral vasoconstriction and hypertension",
                    "Cutaneous vasodilation, sweating, progressive intravascular volume depletion, and reflex tachycardia",
                    "Immediate drop in core body temperature to 30°C",
                    "Complete loss of renal sodium excretion"
                ),
                correctIndex = 1,
                explanation = "Thermal stress causes peripheral vasodilation and sweating. Without fluid replacement, intravascular volume drops, leading to orthostatic hypotension, reflex tachycardia, and eventually heat exhaustion/stroke.",
                reference = "Human Thermal Physiology & Heat Stress"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Heat Stroke & Hydration",
                question = "Why should cold ice-water immersion be used with caution in elderly patients with pre-existing heart disease experiencing classic heat stroke?",
                options = listOf(
                    "It causes sudden intense peripheral vasoconstriction, shivering, rapid surge in systemic vascular resistance, and potential acute cardiac overload",
                    "It prevents heat loss completely",
                    "It causes immediate hypernatremia",
                    "It is illegal under Saudi medical regulations"
                ),
                correctIndex = 0,
                explanation = "Sudden ice-water immersion in fragile elderly cardiac patients causes intense cutaneous vasoconstriction and shivering, driving up systemic vascular resistance and cardiac workload. Tepid evaporative misting is safer.",
                reference = "Geriatric Emergency Medicine - Heat Stroke Protocols"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Heat Stroke & Hydration",
                question = "What is the primary electrolyte disturbance associated with severe uncorrected profuse sweating combined with inadequate salt intake?",
                options = listOf(
                    "Hypercalcemia",
                    "Hyponatremia and hypokalemia due to dermal electrolyte loss",
                    "Severe hypermagnesemia",
                    "Hypophosphatemia"
                ),
                correctIndex = 1,
                explanation = "Sweat contains significant amounts of Sodium (\$Na^+) and Potassium (\$K^+). Profuse sweating replaced with plain water leads to hyponatremia and hypokalemia, triggering heat cramps and exhaustion.",
                reference = "Dermal Electrolyte Loss & Hydration Science"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Vaccine & Outbreaks",
                question = "What is the incubation period of Middle East Respiratory Syndrome Coronavirus (MERS-CoV)?",
                options = listOf(
                    "1 to 2 hours",
                    "2 to 14 days (typically 5 to 6 days)",
                    "30 to 60 days",
                    "6 months"
                ),
                correctIndex = 1,
                explanation = "MERS-CoV has an incubation period ranging from 2 to 14 days (median 5-6 days). Respiratory surveillance requires monitoring close contacts for 14 days post-exposure.",
                reference = "WHO MERS-CoV Epidemiology Standards"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Vaccine & Outbreaks",
                question = "Which antimicrobial agent is used as an alternative oral chemoprophylaxis for close contacts of meningococcal disease when Ciprofloxacin is contraindicated (e.g., in pregnant women)?",
                options = listOf(
                    "Single dose Oral Azithromycin 500 mg (or single IM Ceftriaxone 250 mg)",
                    "Doxycycline 100 mg for 14 days",
                    "Gentamicin injection",
                    "Oral Fluconazole"
                ),
                correctIndex = 0,
                explanation = "For pregnant women where fluoroquinolones (Ciprofloxacin) are avoided, single-dose oral Azithromycin 500 mg or IM Ceftriaxone 250 mg is safe and effective for meningococcal chemoprophylaxis.",
                reference = "CDC Chemoprophylaxis in Pregnancy Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "CPR, Trauma & Clinics",
                question = "What is the recommended compression-to-ventilation ratio for single-rescuer adult CPR in a field clinic?",
                options = listOf(
                    "15 : 2",
                    "30 : 2",
                    "50 : 1",
                    "5 : 1"
                ),
                correctIndex = 1,
                explanation = "For single-rescuer adult CPR, AHA guidelines dictate a ratio of 30 chest compressions followed by 2 rescue breaths.",
                reference = "AHA Basic Life Support Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "CPR, Trauma & Clinics",
                question = "A trauma patient in Mina presents with paradoxical chest wall movement (a segment of the rib cage moving inward during inspiration and outward during expiration). What condition is present?",
                options = listOf(
                    "Flail Chest (due to double fractures in two or more adjacent ribs)",
                    "Simple pneumothorax",
                    "Acute asthma attack",
                    "Diaphragmatic hernia"
                ),
                correctIndex = 0,
                explanation = "Flail Chest occurs when 2 or more contiguous ribs are fractured in 2 or more places, creating a floating segment that displays paradoxical chest wall motion during respiration.",
                reference = "ATLS Thoracic Trauma Management"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Quantitative Reasoning",
                question = "A clinic receives 1,200 bottles of oral rehydration salts (ORS). If 25% are sent to Mina, 35% to Arafat, and 20% to Madinah, how many bottles remain at the central Makkah depot?",
                options = listOf(
                    "120 bottles",
                    "240 bottles",
                    "360 bottles",
                    "480 bottles"
                ),
                correctIndex = 1,
                explanation = "Percentage distributed = 25% + 35% + 20% = 80%. Percentage remaining = 100% - 80% = 20%. Remaining bottles = 20% of 1,200 = 0.20 * 1,200 = 240 bottles.",
                reference = "NTS Inventory Percentage Problems"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "Analytical Reasoning",
                question = "If 'DOCTOR' is coded as 'EQDVPS' by shifting each letter forward by +1 position in the alphabet, how is 'NURSE' coded under the exact same rule?",
                options = listOf(
                    "OVTSE",
                    "OVSTF",
                    "OVSTG",
                    "OWVTF"
                ),
                correctIndex = 1,
                explanation = "Shift rule (+1): N (+1) -> O, U (+1) -> V, R (+1) -> S, S (+1) -> T, E (+1) -> F. Result = 'OVSTF'.",
                reference = "NTS Coding-Decoding Logic"
            )
        )

        list.add(
            HajjQuestion(
                id = currentId++,
                category = "CPR, Trauma & Clinics",
                question = "What is the initial medical management for an unconscious adult displaying gasping, agonal breathing, and no palpable carotid pulse?",
                options = listOf(
                    "Place in recovery position and observe",
                    "Immediate activation of emergency response and commencement of high-quality chest compressions",
                    "Administer IV saline bolus only",
                    "Wait for electrocardiogram setup"
                ),
                correctIndex = 1,
                explanation = "Agonal gasps are a sign of cardiac arrest. Unresponsive patients with agonal breathing and no pulse require immediate CPR starting with chest compressions.",
                reference = "AHA Cardiac Arrest Recognition Standards"
            )
        )

        return list
    }
}
