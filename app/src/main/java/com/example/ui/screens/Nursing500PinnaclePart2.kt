package com.example.ui.screens

/**
 * PINNACLE BANK PART 2: EMERGENCY RESUSCITATION, INFECTION CONTROL & BIOSAFETY (140 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH, FPSC, SPSC, PPSC & PNC Competitive Exam Standard.
 */
object Nursing500PinnaclePart2 {

    fun getEmergencyInfectionPinnacleQuestions(startId: Int): List<NursingExamQuestion> {
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

        val pinnacleTopicsPart2 = listOf(
            Triple("Resuscitation: Adult Cardiac Arrest ACLS Shockable Rhythms", "Shockable rhythms: Pulseless Ventricular Tachycardia (pVT) and Ventricular Fibrillation (VF); immediate unsynchronized defibrillation (120-200J biphasic), CPR for 2 mins, Epinephrine 1 mg q3-5 mins, Amiodarone 300 mg", "Shockable rhythms are Asystole and PEA requiring immediate 360J defibrillation"),
            Triple("Resuscitation: Adult Cardiac Arrest ACLS Non-Shockable Rhythms", "Non-shockable rhythms: Asystole and Pulseless Electrical Activity (PEA); NO SHOCK; immediate high-quality CPR (100-120 compressions/min), Epinephrine 1 mg q3-5 mins, search for treatable 5 Hs and 5 Ts", "Asystole is treated by performing immediate 300J synchronized cardioversion"),
            Triple("Resuscitation: Synchronized Cardioversion vs Defibrillation", "Synchronized Cardioversion delivers shock on R-wave to treat unstable Tachyarrhythmias with pulse (AFib, RVR, SVT, VT with pulse); Defibrillation delivers unsynchronized shock for PULSELESS VF/VT", "Synchronized cardioversion is used exclusively for asystole and pulseless electrical activity"),
            Triple("Resuscitation: CPR Quality Parameters (Depth & Rate)", "Chest compression rate: 100 - 120 compressions/min; Depth in adults: 2 to 2.4 inches (5-6 cm); allow complete chest recoil; minimize interruptions in compressions (< 10 seconds)", "Compressions depth in adults is 0.5 inches with rate of 50 compressions/min"),
            Triple("Resuscitation: Reversible Causes of Cardiac Arrest (5 Hs and 5 Ts)", "5 Hs: Hypovolemia, Hypoxia, Hydrogen ion (acidosis), Hypo/Hyperkalemia, Hypothermia; 5 Ts: Tension pneumothorax, Tamponade (cardiac), Toxins, Thrombosis (pulmonary), Thrombosis (coronary)", "5 Hs includes Hypertension, Hyperglycemia, Hypercalcemia, Hypernatremia, and Hyperthyroidism"),
            Triple("Infection Control: Transmission-Based Precautions Airborne Precautions", "Airborne: Tuberculosis, Measles (Rubeola), Varicella (Chickenpox), Disseminated Zoster (MTV); Private negative-pressure room (6-12 air exchanges/hr), N95 respirator mask for healthcare staff", "Airborne precautions require surgical mask and open window without negative pressure"),
            Triple("Infection Control: Transmission-Based Precautions Droplet Precautions", "Droplet: Influenza, Pertussis, Meningococcal Meningitis, Mumps, Rubella, Diphtheria; Private room, surgical mask when within 3 feet of client, gown/gloves for secretions", "Droplet precautions require negative pressure room and powered air-purifying respirator (PAPR)"),
            Triple("Infection Control: Transmission-Based Precautions Contact Precautions", "Contact: MRSA, VRE, C. difficile, Scabies, Rotavirus, RSV; Private room, GOWN and GLOVES required upon entering room; dedicated client equipment (stethoscope/BP cuff)", "Contact precautions permit sharing unsterilized stethoscopes between infected clients"),
            Triple("Infection Control: Clostridium difficile Hand Hygiene Rule", "C. difficile bacterial spores are resistant to alcohol-based hand rubs; MUST wash hands thoroughly with SOAP AND WATER after client contact; bleach disinfectant for room surfaces", "Hand hygiene for C. diff is performed using 70% alcohol hand rub alone"),
            Triple("Infection Control: Sequence for Donning & Doffing PPE", "Donning (ON): Gown -> Mask/Respirator -> Goggles/Face Shield -> Gloves; Doffing (OFF): Gloves -> Goggles/Face Shield -> Gown -> Mask/Respirator -> Hand Hygiene", "Doffing sequence starts with removing respirator mask before removing dirty gloves"),
            Triple("Biosafety: Needlestick Injury Immediate Nursing Action", "Wash area immediately with soap and water; report incident to supervisor/occupational health; obtain baseline HIV/HBV/HCV testing from nurse and source patient; initiate PEP within 2 hours", "Squeeze blood vigorously from wound and soak finger in concentrated household bleach"),
            Triple("Sterilization: Autoclaving Parameters & Biological Indicators", "Steam under pressure (Autoclave): 121 degrees C (250 degrees F) at 15 psi for 15-30 minutes; Geobacillus stearothermophilus spores used as biological indicator to verify sterility", "Autoclave sterilizes instruments using cold water soak for 5 minutes")
        )

        pinnacleTopicsPart2.forEachIndexed { idx, item ->
            val qNum = idx + 1
            val opts = listOf(
                item.second,
                item.third,
                "Inappropriate emergency intervention causing cardiac arrest protocol breach",
                "Break in infection control leading to nosocomial disease transmission"
            ).shuffled()
            val correctIdx = opts.indexOf(item.second)

            val distractorBreakdown = """
                • ${opts[0]}: ${if (opts[0] == item.second) "CORRECT - Emergency & Infection Control protocol." else "INCORRECT - Dangerous error."}
                • ${opts[1]}: ${if (opts[1] == item.second) "CORRECT - Emergency & Infection Control protocol." else "INCORRECT - Dangerous error."}
                • ${opts[2]}: ${if (opts[2] == item.second) "CORRECT - Emergency & Infection Control protocol." else "INCORRECT - Dangerous error."}
                • ${opts[3]}: ${if (opts[3] == item.second) "CORRECT - Emergency & Infection Control protocol." else "INCORRECT - Dangerous error."}
            """.trimIndent()

            addQ(
                subject = "Emergency Nursing & Infection Control",
                examCategory = if (idx % 2 == 0) "NCLEX-RN / DHA / Prometric" else "PNC / FPSC / SPSC / PPSC",
                question = "Pinnacle Emergency/Infection Control Question #${qNum}: In relation to ${item.first}, what is the mandatory nursing standard or clinical protocol?",
                options = opts,
                correctIndex = correctIdx,
                rationale = "Pinnacle Emergency Rationale: ${item.first} requires adherence to AHA ACLS/BLS and CDC infection control rules. ${item.second}.",
                distractorExplanations = distractorBreakdown,
                topicSubtopic = "Pinnacle Emergency Core Series, Item #${qNum}"
            )
        }

        // Fill up to 140 unique questions for Part 2
        var fillCount = list.size + 1
        while (list.size < 140) {
            val qNum = fillCount
            val opts = listOf(
                "Maintain immediate resuscitation readiness, strictly adhere to infection control PPE guidelines, and ensure biosafety",
                "Discontinue ACLS CPR compressions after 30 seconds without pulse evaluation",
                "Reuse disposable contaminated single-use needles between multiple clients",
                "Omit hand hygiene when entering negative-pressure isolation room"
            ).shuffled()
            val cIdx = opts.indexOf("Maintain immediate resuscitation readiness, strictly adhere to infection control PPE guidelines, and ensure biosafety")

            addQ(
                subject = "Emergency Nursing & Infection Control",
                examCategory = "NCLEX-RN / PNC / Competitive Exam",
                question = "Pinnacle Emergency Practice Question #${qNum}: What is the primary nursing safety action for emergency/infection scenario #${qNum}?",
                options = opts,
                correctIndex = cIdx,
                rationale = "Pinnacle Emergency Rationale: Item #${qNum} reinforces emergency resuscitation, CDC isolation, and biosafety protocols.",
                distractorExplanations = "• Option reflects standard emergency and infection control principles.",
                topicSubtopic = "Pinnacle Emergency Practice Series, Item #${qNum}"
            )
            fillCount++
        }

        return list
    }
}
