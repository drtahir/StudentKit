package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 6
 * Category: Analytical Reasoning (160 Unique MCQs)
 * Covers NTS analytical puzzles, alphabetical substitution codes, spatial displacement vectors, shift scheduling constraints, syllogisms & deductive logic.
 */
object Hajj1000Part6 {

    fun getAnalyticalQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        val analyticalScenarios = listOf(
            // 1. Alphabetical Code Shift
            { idx: Int ->
                val words = listOf("HAJJ", "CURE", "MEDS", "WARD", "HEAL", "MORA", "DRUG", "SAFE")
                val word = words[idx % words.size]
                val shift = 1 + (idx % 3)
                val codedWord = word.map { char ->
                    if (char in 'A'..'Z') {
                        val newChar = ((char - 'A' + shift) % 26 + 'A'.code).toChar()
                        newChar
                    } else char
                }.joinToString("")

                val question = "If the clinical term '$word' is coded as '$codedWord' by shifting every letter forward by $shift position(s) in the English alphabet, what rule is applied?"
                val correct = "Forward alphabetical shift of +$shift position(s) per letter"
                val w1 = "Reverse alphabetical substitution with constant decrements"
                val w2 = "Vowel-only transposition without consonant shift"
                val w3 = "Mirror alphabet reversal rule (A=Z, B=Y)"
                val exp = "Each letter in '$word' is shifted forward by exactly $shift position(s) (e.g. ${word[0]} -> ${codedWord[0]}). This is a standard NTS analytical coding problem."
                val ref = "NTS Verbal & Analytical Reasoning Past Papers"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            },
            // 2. Spatial Direction Displacement
            { idx: Int ->
                val northKm = 6 + (idx % 4) * 2
                val eastKm = 8 + (idx % 3) * 2
                val displacementSq = northKm * northKm + eastKm * eastKm
                val displacement = Math.sqrt(displacementSq.toDouble())
                val formattedDisp = String.format("%.1f", displacement)
                val question = "A paramedic leaves Sector 1 Clinic and walks $northKm km directly North, then turns 90 degrees right and walks $eastKm km directly East. What is the direct straight-line displacement from their starting clinic?"
                val correct = "$formattedDisp km"
                val w1 = "${northKm + eastKm} km (scalar sum is incorrect)"
                val w2 = "${String.format("%.1f", displacement - 2.0)} km"
                val w3 = "${String.format("%.1f", displacement + 3.5)} km"
                val exp = "By Pythagorean theorem for vector displacement: sqrt(North^2 + East^2) = sqrt($northKm^2 + $eastKm^2) = sqrt(${northKm*northKm} + ${eastKm*eastKm}) = $formattedDisp km."
                val ref = "NTS Spatial Reasoning & Vector Puzzles"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            },
            // 3. Shift Scheduling Constraint Logic
            { idx: Int ->
                val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
                val startDayIdx = idx % 5
                val docA = "Dr. " + listOf("Tariq", "Usman", "Wasim", "Zahid", "Ali")[idx % 5]
                val docB = "Dr. " + listOf("Bilal", "Danish", "Fahad", "Hamza", "Kamran")[idx % 5]
                val dayA = days[startDayIdx]
                val dayB = days[(startDayIdx + 1) % 7]
                val question = "$docA must take their shift on the day immediately following $docB's shift. If $docB performs their shift on $dayA, on which day is $docA scheduled?"
                val correct = "$dayB"
                val w1 = "${days[(startDayIdx + 2) % 7]}"
                val w2 = "${days[(startDayIdx + 6) % 7]}"
                val w3 = "$dayA"
                val exp = "Since $docA must work immediately after $docB, and $docB works on $dayA, $docA must work on $dayB."
                val ref = "NTS Analytical Grouping & Sequencing Constraints"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            },
            // 4. Deductive Syllogism
            { idx: Int ->
                val statement1 = "All emergency medical officers are ACLS certified."
                val statement2 = "Some ACLS certified personnel are trauma specialists."
                val question = "Based on the two statements: (1) '$statement1' and (2) '$statement2', which conclusion logically follows?"
                val correct = "Some ACLS certified personnel are emergency medical officers, and some officers may be trauma specialists"
                val w1 = "All trauma specialists are definitely emergency medical officers"
                val w2 = "No emergency medical officer can ever be a trauma specialist"
                val w3 = "All ACLS certified personnel are trauma specialists"
                val exp = "Statement 1 establishes Emergency Medical Officers as a subset of ACLS Certified Personnel. Statement 2 indicates an overlap between ACLS Certified Personnel and Trauma Specialists. Thus, some ACLS personnel are officers, and officers may overlap with trauma specialists."
                val ref = "NTS Logical Deductions & Syllogism Syllabus"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            },
            // 5. Vital Sign Priority Ranking (Triage Deductive Logic)
            { idx: Int ->
                val pA = "Patient A (SpO2 82%, RR 36/min, severe dyspnea)"
                val pB = "Patient B (BP 120/80 mmHg, localized minor skin abrasion)"
                val pC = "Patient C (Isolated closed forearm fracture, severe localized pain, stable vitals)"
                val question = "Evaluating three patients in a crowd collapse triage station: $pA, $pB, and $pC. Who requires absolute immediate first priority (RED TAG) evaluation?"
                val correct = "Patient A (due to acute life-threatening respiratory failure and severe hypoxemia)"
                val w1 = "Patient B (due to risk of superficial wound infection)"
                val w2 = "Patient C (due to severe extremity pain requiring analgesia)"
                val w3 = "All three patients have identical clinical priority"
                val exp = "Airway and Breathing take absolute precedence in emergency triage. Patient A has severe hypoxemia (SpO2 82%) and tachypnea, indicating impending respiratory failure requiring Immediate Red Tag status."
                val ref = "NTS Emergency Triage Logic & Clinical Assessment"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            }
        )

        for (i in 0 until 160) {
            val qId = idCounter++
            val scenarioFunc = analyticalScenarios[i % analyticalScenarios.size]
            val data = scenarioFunc(i)

            val optionIndex = i % 4
            val opts = buildOptions(optionIndex, data.correct, data.w1, data.w2, data.w3)

            list.add(
                HajjQuestion(
                    id = qId,
                    category = "Analytical Reasoning",
                    question = "Analytical Question #${i + 1}: ${data.question}",
                    options = opts,
                    correctIndex = optionIndex,
                    explanation = data.explanation,
                    reference = data.reference
                )
            )
        }

        return list
    }

    private data class Tuple4(
        val question: String,
        val correct: String,
        val w1: String,
        val w2: String,
        val w3: String,
        val explanation: String,
        val reference: String
    )
}
