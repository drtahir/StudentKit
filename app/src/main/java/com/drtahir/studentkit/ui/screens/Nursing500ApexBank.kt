package com.drtahir.studentkit.ui.screens

/**
 * NURSING 500 APEX BANK
 * Assembles 500 Apex-Level Nursing Questions (Parts 1-4)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ApexBank {

    fun get500ApexQuestions(startId: Int): List<NursingExamQuestion> {
        val list = mutableListOf<NursingExamQuestion>()

        var currentId = startId

        // Part 1: Specialized Clinical Specialties, Advanced Pathophysiology & NCLEX NextGen (150 MCQs)
        val part1 = Nursing500ApexPart1.getMedSurgApexQuestions(currentId)
        list.addAll(part1)
        currentId += part1.size

        // Part 2: Advanced Clinical Pharmacology, High-Alert Drugs & Calculations (140 MCQs)
        val part2 = Nursing500ApexPart2.getPharmApexQuestions(currentId)
        list.addAll(part2)
        currentId += part2.size

        // Part 3: Advanced Maternal-Child, NICU, Pediatric Emergencies & Psychiatric Nursing (130 MCQs)
        val part3 = Nursing500ApexPart3.getPedsObPsychApexQuestions(currentId)
        list.addAll(part3)
        currentId += part3.size

        // Part 4: Trauma Systems, ICU Hemodynamics, Leadership & PNC Competencies (80 MCQs)
        val part4 = Nursing500ApexPart4.getCriticalCareApexQuestions(currentId)
        list.addAll(part4)
        currentId += part4.size

        return list
    }
}
