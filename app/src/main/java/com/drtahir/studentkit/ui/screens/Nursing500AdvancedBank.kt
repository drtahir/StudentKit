package com.drtahir.studentkit.ui.screens

/**
 * AGGREGATOR FOR 500 ADVANCED HIGH-QUALITY NCLEX & COMPETITIVE NURSING EXAM MCQs
 *
 * Subject Breakdown:
 * 1. Medical-Surgical Nursing (Advanced) – 150 MCQs (Part 1)
 * 2. Pharmacology & Medication Safety (Advanced) – 80 MCQs (Part 2)
 * 3. Fundamentals of Nursing & Skills (Advanced) – 60 MCQs (Part 2)
 * 4. Pediatrics Nursing (Advanced) – 50 MCQs (Part 3)
 * 5. Obstetric and Gynecological Nursing (Advanced) – 50 MCQs (Part 3)
 * 6. Psychiatric/Mental Health Nursing (Advanced) – 30 MCQs (Part 3)
 * 7. Critical Care and Emergency Nursing (Advanced) – 40 MCQs (Part 4)
 * 8. Community Health Nursing, Infection Control, Ethics and Leadership (Advanced) – 40 MCQs (Part 4)
 *
 * TOTAL: 500 Additional Unique Scenario-Based MCQs with detailed rationales and option breakdowns.
 */
object Nursing500AdvancedBank {

    fun get500AdvancedQuestions(startId: Int): List<NursingExamQuestion> {
        var currentId = startId
        val resultList = mutableListOf<NursingExamQuestion>()

        val part1 = Nursing500AdvancedPart1.getMedSurgAdvancedQuestions(currentId)
        resultList.addAll(part1)
        currentId += part1.size

        val part2 = Nursing500AdvancedPart2.getPharmAndFundamentalsAdvancedQuestions(currentId)
        resultList.addAll(part2)
        currentId += part2.size

        val part3 = Nursing500AdvancedPart3.getPediatricObstetricPsychAdvancedQuestions(currentId)
        resultList.addAll(part3)
        currentId += part3.size

        val part4 = Nursing500AdvancedPart4.getCriticalCareAndLeadershipAdvancedQuestions(currentId)
        resultList.addAll(part4)
        currentId += part4.size

        return resultList
    }
}
