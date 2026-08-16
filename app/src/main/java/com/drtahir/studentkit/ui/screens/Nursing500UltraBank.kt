package com.drtahir.studentkit.ui.screens

/**
 * NURSING 500 ULTRA BANK
 * Assembles 500 Ultra-Level Nursing Questions (Parts 1-4)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500UltraBank {

    fun get500UltraQuestions(startId: Int): List<NursingExamQuestion> {
        val list = mutableListOf<NursingExamQuestion>()

        var currentId = startId

        // Part 1: Advanced Med-Surg, Cardiovascular, Respiratory & Neurology (150 MCQs)
        val part1 = Nursing500UltraPart1.getMedSurgUltraQuestions(currentId)
        list.addAll(part1)
        currentId += part1.size

        // Part 2: Advanced Clinical Pharmacology, High-Alert Drugs & Calculations (140 MCQs)
        val part2 = Nursing500UltraPart2.getPharmUltraQuestions(currentId)
        list.addAll(part2)
        currentId += part2.size

        // Part 3: Advanced Maternal-Child, NICU, Pediatric Emergencies & Psychiatry (130 MCQs)
        val part3 = Nursing500UltraPart3.getPedsObPsychUltraQuestions(currentId)
        list.addAll(part3)
        currentId += part3.size

        // Part 4: Critical Care, Emergency Triage, Trauma Systems & Leadership (80 MCQs)
        val part4 = Nursing500UltraPart4.getCriticalCareUltraQuestions(currentId)
        list.addAll(part4)
        currentId += part4.size

        return list
    }
}
