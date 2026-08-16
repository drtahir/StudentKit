package com.drtahir.studentkit.ui.screens

/**
 * MASTER BANK AGGREGATOR: 500 UNIQUE HIGH-QUALITY QUESTIONS
 * Combines Part 1 (150 Med Surg), Part 2 (140 Pharm & Fundamentals),
 * Part 3 (130 Peds/OB/Psych), and Part 4 (80 Critical Care & Leadership).
 */
object Nursing500MasterBank {

    fun get500MasterQuestions(startId: Int): List<NursingExamQuestion> {
        val masterList = mutableListOf<NursingExamQuestion>()
        var currentId = startId

        // Part 1: Medical-Surgical Nursing (150 questions)
        val part1 = Nursing500MasterPart1.getMedSurgMasterQuestions(currentId)
        masterList.addAll(part1)
        currentId += part1.size

        // Part 2: Pharmacology & Fundamentals (140 questions)
        val part2 = Nursing500MasterPart2.getPharmAndFundamentalsMasterQuestions(currentId)
        masterList.addAll(part2)
        currentId += part2.size

        // Part 3: Pediatric, Obstetric & Psychiatric (130 questions)
        val part3 = Nursing500MasterPart3.getPedsObPsychMasterQuestions(currentId)
        masterList.addAll(part3)
        currentId += part3.size

        // Part 4: Critical Care & Leadership (80 questions)
        val part4 = Nursing500MasterPart4.getCriticalCareAndLeadershipMasterQuestions(currentId)
        masterList.addAll(part4)
        currentId += part4.size

        return masterList
    }
}
