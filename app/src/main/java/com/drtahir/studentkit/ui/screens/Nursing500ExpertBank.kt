package com.drtahir.studentkit.ui.screens

/**
 * NURSING 500 EXPERT BANK
 * Assembles 500 Expert-Level Nursing Questions (Parts 1-4)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ExpertBank {

    fun get500ExpertQuestions(startId: Int): List<NursingExamQuestion> {
        val list = mutableListOf<NursingExamQuestion>()

        var currentId = startId

        // Part 1: Medical-Surgical & Specialized Pathophysiology (150 MCQs)
        val part1 = Nursing500ExpertPart1.getMedSurgExpertQuestions(currentId)
        list.addAll(part1)
        currentId += part1.size

        // Part 2: Advanced Clinical Pharmacology & Safe Medication Practice (140 MCQs)
        val part2 = Nursing500ExpertPart2.getPharmExpertQuestions(currentId)
        list.addAll(part2)
        currentId += part2.size

        // Part 3: Pediatrics, Obstetrics & Psychiatric/Mental Health (130 MCQs)
        val part3 = Nursing500ExpertPart3.getPedsObPsychExpertQuestions(currentId)
        list.addAll(part3)
        currentId += part3.size

        // Part 4: Critical Care, Emergency Trauma, Ethics, Leadership & Research (80 MCQs)
        val part4 = Nursing500ExpertPart4.getCriticalCareExpertQuestions(currentId)
        list.addAll(part4)
        currentId += part4.size

        return list
    }
}
