package com.example.ui.screens

/**
 * NURSING 500 TITAN BANK
 * Assembles 500 Titan-Level Nursing Questions (Parts 1-4)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH, FPSC, SPSC, PPSC, BPSC, KPPSC & PNC Competitive Exam Standard.
 */
object Nursing500TitanBank {

    fun get500TitanQuestions(startId: Int): List<NursingExamQuestion> {
        val list = mutableListOf<NursingExamQuestion>()

        var currentId = startId

        // Part 1: Advanced Med-Surg, Cardiology, Pulmonology & Neurology (150 MCQs)
        val part1 = Nursing500TitanPart1.getMedSurgTitanQuestions(currentId)
        list.addAll(part1)
        currentId += part1.size

        // Part 2: Clinical Pharmacology, High-Alert Drugs & Calculations (140 MCQs)
        val part2 = Nursing500TitanPart2.getPharmTitanQuestions(currentId)
        list.addAll(part2)
        currentId += part2.size

        // Part 3: Maternal & Newborn, Obstetrics, Pediatrics & Neonatal (130 MCQs)
        val part3 = Nursing500TitanPart3.getPedsObPsychTitanQuestions(currentId)
        list.addAll(part3)
        currentId += part3.size

        // Part 4: Critical Care, Disaster Management & Leadership (80 MCQs)
        val part4 = Nursing500TitanPart4.getCriticalCareTitanQuestions(currentId)
        list.addAll(part4)
        currentId += part4.size

        return list
    }
}
