package com.example.ui.screens

/**
 * NURSING 500 MASTERY BANK
 * Assembles 500 Mastery-Level Nursing Questions (Parts 1-4)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500MasteryBank {

    fun get500MasteryQuestions(startId: Int): List<NursingExamQuestion> {
        val list = mutableListOf<NursingExamQuestion>()

        var currentId = startId

        // Part 1: Specialized Med-Surg, Diagnostic Procedures & Perioperative Care (150 MCQs)
        val part1 = Nursing500MasteryPart1.getMedSurgMasteryQuestions(currentId)
        list.addAll(part1)
        currentId += part1.size

        // Part 2: Clinical Pharmacology, High-Alert Meds & Calculations (140 MCQs)
        val part2 = Nursing500MasteryPart2.getPharmMasteryQuestions(currentId)
        list.addAll(part2)
        currentId += part2.size

        // Part 3: NICU, Pediatric Emergencies, Obstetrics & Mental Health (130 MCQs)
        val part3 = Nursing500MasteryPart3.getPedsObPsychMasteryQuestions(currentId)
        list.addAll(part3)
        currentId += part3.size

        // Part 4: ICU Ventilation, Advanced Hemodynamics, Infection Control & PNC (80 MCQs)
        val part4 = Nursing500MasteryPart4.getCriticalCareMasteryQuestions(currentId)
        list.addAll(part4)
        currentId += part4.size

        return list
    }
}
