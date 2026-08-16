package com.drtahir.studentkit.ui.screens

/**
 * NURSING 500 ELITE BANK
 * Assembles 500 Elite-Level Nursing Questions (Parts 1-4)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500EliteBank {

    fun get500EliteQuestions(startId: Int): List<NursingExamQuestion> {
        val list = mutableListOf<NursingExamQuestion>()

        var currentId = startId

        // Part 1: Advanced Medical-Surgical, Pathophysiology & Clinical Triage (150 MCQs)
        val part1 = Nursing500ElitePart1.getMedSurgEliteQuestions(currentId)
        list.addAll(part1)
        currentId += part1.size

        // Part 2: Advanced Pharmacology, Dosing Calculations & High-Alert Med Safety (140 MCQs)
        val part2 = Nursing500ElitePart2.getPharmEliteQuestions(currentId)
        list.addAll(part2)
        currentId += part2.size

        // Part 3: Maternal-Child, Pediatrics & Psychiatric Nursing (130 MCQs)
        val part3 = Nursing500ElitePart3.getPedsObPsychEliteQuestions(currentId)
        list.addAll(part3)
        currentId += part3.size

        // Part 4: Critical Care, Disaster Emergency, Ethics & Leadership (80 MCQs)
        val part4 = Nursing500ElitePart4.getCriticalCareEliteQuestions(currentId)
        list.addAll(part4)
        currentId += part4.size

        return list
    }
}
