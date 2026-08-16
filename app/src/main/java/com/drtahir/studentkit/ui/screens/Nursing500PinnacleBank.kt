package com.drtahir.studentkit.ui.screens

/**
 * NURSING 500 PINNACLE BANK
 * Assembles 500 Pinnacle-Level Nursing Questions (Parts 1-4)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH, FPSC, SPSC, PPSC, BPSC, KPPSC & PNC Competitive Exam Standard.
 */
object Nursing500PinnacleBank {

    fun get500PinnacleQuestions(startId: Int): List<NursingExamQuestion> {
        val list = mutableListOf<NursingExamQuestion>()

        var currentId = startId

        // Part 1: Advanced Med-Surg, Nephrology, Oncology, Hematology & Burns (150 MCQs)
        val part1 = Nursing500PinnaclePart1.getMedSurgPinnacleQuestions(currentId)
        list.addAll(part1)
        currentId += part1.size

        // Part 2: Emergency Resuscitation, Infection Control & Biosafety (140 MCQs)
        val part2 = Nursing500PinnaclePart2.getEmergencyInfectionPinnacleQuestions(currentId)
        list.addAll(part2)
        currentId += part2.size

        // Part 3: Mental Health, Psychiatry & Therapeutic Communication (130 MCQs)
        val part3 = Nursing500PinnaclePart3.getPsychiatryPinnacleQuestions(currentId)
        list.addAll(part3)
        currentId += part3.size

        // Part 4: Community Health, Epidemiology & Research (80 MCQs)
        val part4 = Nursing500PinnaclePart4.getCommunityResearchPinnacleQuestions(currentId)
        list.addAll(part4)
        currentId += part4.size

        return list
    }
}
