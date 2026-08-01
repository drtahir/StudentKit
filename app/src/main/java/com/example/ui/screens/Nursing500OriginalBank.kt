package com.example.ui.screens

/**
 * AGGREGATOR FOR THE 500 ORIGINAL HIGH-QUALITY NCLEX & COMPETITIVE NURSING EXAM MCQs
 *
 * Subject Breakdown:
 * 1. Medical-Surgical Nursing – 150 MCQs (Part 1)
 * 2. Pharmacology and Medication Safety – 80 MCQs (Part 2)
 * 3. Fundamentals of Nursing – 60 MCQs (Part 2)
 * 4. Pediatrics Nursing – 50 MCQs (Part 3)
 * 5. Obstetric and Gynecological Nursing – 50 MCQs (Part 3)
 * 6. Psychiatric/Mental Health Nursing – 30 MCQs (Part 3)
 * 7. Critical Care and Emergency Nursing – 40 MCQs (Part 4)
 * 8. Community Health Nursing, Infection Control, Ethics and Leadership – 40 MCQs (Part 4)
 *
 * TOTAL: 500 Original Scenario-Based MCQs with detailed rationales and option breakdowns.
 */
object Nursing500OriginalBank {

    fun get500OriginalQuestions(startId: Int): List<NursingExamQuestion> {
        var currentId = startId
        val resultList = mutableListOf<NursingExamQuestion>()

        val part1 = Nursing500BankPart1.getMedSurgQuestions(currentId)
        resultList.addAll(part1)
        currentId += part1.size

        val part2 = Nursing500BankPart2.getPharmAndFundamentalsQuestions(currentId)
        resultList.addAll(part2)
        currentId += part2.size

        val part3 = Nursing500BankPart3.getPediatricObstetricPsychQuestions(currentId)
        resultList.addAll(part3)
        currentId += part3.size

        val part4 = Nursing500BankPart4.getCriticalCareAndLeadershipQuestions(currentId)
        resultList.addAll(part4)
        currentId += part4.size

        return resultList
    }
}
