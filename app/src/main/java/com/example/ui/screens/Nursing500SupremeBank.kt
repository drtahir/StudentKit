package com.example.ui.screens

/**
 * NURSING 500 SUPREME BANK
 * Assembles 500 Supreme-Level Nursing Questions (Parts 1-4)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500SupremeBank {

    fun get500SupremeQuestions(startId: Int): List<NursingExamQuestion> {
        val list = mutableListOf<NursingExamQuestion>()

        var currentId = startId

        // Part 1: Advanced Med-Surg, Cardiovascular, Respiratory & Neurology (150 MCQs)
        val part1 = Nursing500SupremePart1.getMedSurgSupremeQuestions(currentId)
        list.addAll(part1)
        currentId += part1.size

        // Part 2: Advanced Clinical Pharmacology, High-Alert Drugs & Calculations (140 MCQs)
        val part2 = Nursing500SupremePart2.getPharmSupremeQuestions(currentId)
        list.addAll(part2)
        currentId += part2.size

        // Part 3: Advanced Maternal-Child, NICU, Pediatric Emergencies & Psychiatry (130 MCQs)
        val part3 = Nursing500SupremePart3.getPedsObPsychSupremeQuestions(currentId)
        list.addAll(part3)
        currentId += part3.size

        // Part 4: Critical Care, Emergency Triage, Trauma Systems & Leadership (80 MCQs)
        val part4 = Nursing500SupremePart4.getCriticalCareSupremeQuestions(currentId)
        list.addAll(part4)
        currentId += part4.size

        return list
    }
}
