package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 QUESTION BANK
 * Assembles 1000 High-Yield NTS Hajj Medical Mission Questions across 6 Core Subjects:
 * 1. Hajj Rules & Admin (170 MCQs)
 * 2. Heat Stroke & Hydration (170 MCQs)
 * 3. Vaccine & Outbreaks (170 MCQs)
 * 4. CPR, Trauma & Clinics (170 MCQs)
 * 5. Quantitative Reasoning (160 MCQs)
 * 6. Analytical Reasoning (160 MCQs)
 */
object Hajj1000QuestionBank {

    fun get1000HajjQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var currentId = startId

        // Part 1: Hajj Rules & Admin (170 MCQs)
        val p1 = Hajj1000Part1.getHajjRulesQuestions(currentId)
        list.addAll(p1)
        currentId += p1.size

        // Part 2: Heat Stroke & Hydration (170 MCQs)
        val p2 = Hajj1000Part2.getHeatStrokeQuestions(currentId)
        list.addAll(p2)
        currentId += p2.size

        // Part 3: Vaccine & Outbreaks (170 MCQs)
        val p3 = Hajj1000Part3.getVaccineOutbreakQuestions(currentId)
        list.addAll(p3)
        currentId += p3.size

        // Part 4: CPR, Trauma & Clinics (170 MCQs)
        val p4 = Hajj1000Part4.getCprTraumaQuestions(currentId)
        list.addAll(p4)
        currentId += p4.size

        // Part 5: Quantitative Reasoning (160 MCQs)
        val p5 = Hajj1000Part5.getQuantitativeQuestions(currentId)
        list.addAll(p5)
        currentId += p5.size

        // Part 6: Analytical Reasoning (160 MCQs)
        val p6 = Hajj1000Part6.getAnalyticalQuestions(currentId)
        list.addAll(p6)
        currentId += p6.size

        return list
    }
}
