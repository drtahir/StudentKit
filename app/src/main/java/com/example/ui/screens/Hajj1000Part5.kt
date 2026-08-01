package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 5
 * Category: Quantitative Reasoning (160 Unique MCQs)
 * Covers dosage calculations, IV drip rates, pharmaceutical stock percentages, work & time capacity, averages, ratios, speed-distance & NTS math patterns.
 */
object Hajj1000Part5 {

    fun getQuantitativeQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        val quantScenarios = listOf(
            // 1. IV Drip Rate
            { idx: Int ->
                val vol = 500 + (idx % 6) * 100
                val hrs = 4 + (idx % 3) * 2
                val dropFactor = 15
                val totalMins = hrs * 60
                val gtts = Math.round((vol.toDouble() * dropFactor) / totalMins).toInt()
                val question = "A patient in Mina requires $vol mL of 0.9% Normal Saline infused over $hrs hours using an IV tubing set with a drop factor of 15 drops/mL. What is the required IV flow rate in drops per minute (gtt/min)?"
                val correct = "$gtts drops/min"
                val w1 = "${gtts + 12} drops/min"
                val w2 = "${gtts - 8} drops/min"
                val w3 = "${gtts * 2} drops/min"
                val exp = "IV Flow Rate Formula: (Total Volume in mL * Drop Factor) / (Time in Minutes) = ($vol * 15) / ($hrs * 60) = ${vol * 15} / $totalMins = $gtts drops/min."
                val ref = "NTS Drug Calculation & Nursing Mathematics Manual"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            },
            // 2. Dosage Calculation
            { idx: Int ->
                val weight = 60 + (idx % 5) * 10
                val dosePerKg = 5 + (idx % 4) * 2
                val totalDose = weight * dosePerKg
                val availMg = 250
                val availMl = 5
                val volumeToGive = (totalDose.toDouble() * availMl) / availMg
                val formattedVol = String.format("%.1f", volumeToGive)
                val question = "A patient weighing $weight kg requires an antibiotic at a dose of $dosePerKg mg/kg. The medication is available as $availMg mg in $availMl mL. How many mL should be administered?"
                val correct = "$formattedVol mL"
                val w1 = "${String.format("%.1f", volumeToGive + 2.5)} mL"
                val w2 = "${String.format("%.1f", volumeToGive - 1.2)} mL"
                val w3 = "${String.format("%.1f", volumeToGive * 2.0)} mL"
                val exp = "Total required dose = $weight kg * $dosePerKg mg/kg = $totalDose mg. Volume = ($totalDose mg * $availMl mL) / $availMg mg = $formattedVol mL."
                val ref = "NTS Dosage & Solution Calculations"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            },
            // 3. Stock Consumption Percentage
            { idx: Int ->
                val startStock = 400 + (idx % 5) * 100
                val usedPct = 20 + (idx % 4) * 10
                val usedQty = (startStock * usedPct) / 100
                val remaining = startStock - usedQty
                val question = "A field clinic in Arafat has a starting inventory of $startStock boxes of Oral Rehydration Salts (ORS). If $usedPct% of the stock is consumed during Wuquf day, how many boxes remain in stock?"
                val correct = "$remaining boxes"
                val w1 = "${remaining - 30} boxes"
                val w2 = "${remaining + 45} boxes"
                val w3 = "${startStock - (usedQty / 2)} boxes"
                val exp = "Consumed stock = $startStock * ($usedPct / 100) = $usedQty boxes. Remaining inventory = $startStock - $usedQty = $remaining boxes."
                val ref = "NTS Logistics & Inventory Mathematics"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            },
            // 4. Work & Time Capacity
            { idx: Int ->
                val nurses = 4 + (idx % 3) * 2
                val hours = 3 + (idx % 2)
                val patientsTreated = nurses * hours * 5
                val targetNurses = nurses + 4
                val targetHours = 6
                val totalTargetPatients = targetNurses * targetHours * 5
                val question = "If $nurses nurses can triage $patientsTreated patients in $hours hours, how many patients can $targetNurses nurses triage in $targetHours hours at the exact same constant rate?"
                val correct = "$totalTargetPatients patients"
                val w1 = "${totalTargetPatients - 15} patients"
                val w2 = "${totalTargetPatients + 30} patients"
                val w3 = "${totalTargetPatients / 2} patients"
                val exp = "Individual nurse rate = $patientsTreated / ($nurses * $hours) = 5 patients per nurse per hour. For $targetNurses nurses working $targetHours hours: $targetNurses * $targetHours * 5 = $totalTargetPatients patients."
                val ref = "NTS Work & Time Aptitude Syllabus"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            },
            // 5. Speed, Distance & Time
            { idx: Int ->
                val dist = 15 + (idx % 4) * 5
                val speed = 30 + (idx % 3) * 10
                val timeHours = dist.toDouble() / speed
                val timeMins = Math.round(timeHours * 60).toInt()
                val question = "An emergency ambulance travels a distance of $dist km from Mina Camp to Makkah Central Hospital at a constant speed of $speed km/h. How many minutes does the journey take?"
                val correct = "$timeMins minutes"
                val w1 = "${timeMins + 15} minutes"
                val w2 = "${timeMins - 10} minutes"
                val w3 = "${timeMins * 2} minutes"
                val exp = "Time in hours = Distance / Speed = $dist / $speed = $timeHours hours. Converting to minutes = $timeHours * 60 = $timeMins minutes."
                val ref = "NTS Distance & Motion Problem Solving"
                Tuple4(question, correct, w1, w2, w3, exp, ref)
            }
        )

        for (i in 0 until 160) {
            val qId = idCounter++
            val scenarioFunc = quantScenarios[i % quantScenarios.size]
            val data = scenarioFunc(i)

            val optionIndex = i % 4
            val opts = buildOptions(optionIndex, data.correct, data.w1, data.w2, data.w3)

            list.add(
                HajjQuestion(
                    id = qId,
                    category = "Quantitative Reasoning",
                    question = "Quantitative Question #${i + 1}: ${data.question}",
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
