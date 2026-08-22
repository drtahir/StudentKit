package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ 1000+ QUESTION BANK MASTER ASSEMBLY
 * Combines 1,040 High-Yield NTS Moavineen-e-Hujjaj Questions across 6 Core Subjects:
 * 1. Hajj Rules, Rituals & Arkan (200 MCQs)
 * 2. Moavineen Operational SOPs & Duty Rules (200 MCQs)
 * 3. Geography, Places & Holy Sites (200 MCQs)
 * 4. Functional & Conversational Arabic (150 MCQs)
 * 5. Management, Ethics & Situational Judgment (150 MCQs)
 * 6. General Knowledge, Hajj Policy & Tech Portal (140 MCQs)
 *
 * Total: 1,040 Unique NTS Practice MCQs!
 */
object Moavineen1000QuestionBank {

    private val cachedQuestions: List<MoavineenQuestion> by lazy {
        val list = ArrayList<MoavineenQuestion>(1050)
        var currentId = 1

        val p1 = Moavineen1000Part1.getHajjRulesQuestions(currentId)
        list.addAll(p1)
        currentId += p1.size

        val p2 = Moavineen1000Part2.getOperationalSopQuestions(currentId)
        list.addAll(p2)
        currentId += p2.size

        val p3 = Moavineen1000Part3.getGeographyQuestions(currentId)
        list.addAll(p3)
        currentId += p3.size

        val p4 = Moavineen1000Part4.getFunctionalArabicQuestions(currentId)
        list.addAll(p4)
        currentId += p4.size

        val p5 = Moavineen1000Part5.getManagementEthicsQuestions(currentId)
        list.addAll(p5)
        currentId += p5.size

        val p6 = Moavineen1000Part6.getHajjPolicyTechQuestions(currentId)
        list.addAll(p6)
        currentId += p6.size

        list
    }

    fun getAllQuestions(): List<MoavineenQuestion> = cachedQuestions

    /**
     * Filter questions specifically tailored for Supervisor vs Supporting Staff vs All
     */
    fun getQuestionsByPositionAndCategory(position: String, category: String): List<MoavineenQuestion> {
        val all = getAllQuestions()
        return all.filter { q ->
            val positionMatch = when (position) {
                "Supervisor" -> q.positionTarget == "Supervisor" || q.positionTarget == "Both"
                "Supporting Staff" -> q.positionTarget == "Supporting Staff" || q.positionTarget == "Both"
                else -> true
            }
            val categoryMatch = if (category == "All") true else q.subjectCategory == category
            positionMatch && categoryMatch
        }
    }
}
