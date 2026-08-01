package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 1
 * Subject: Hajj Rules, Rituals & Arkan (Masail-e-Hajj & Umrah) - 200 MCQs
 * Covers Ihram restrictions, Miqat locations, Tawaf, Sa'i, Mina, Arafat, Muzdalifah, Jamarat, Dam/Qurbani, Fiqhi rulings for both Supervisor and Supporting Staff.
 */
object Moavineen1000Part1 {

    fun getHajjRulesQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        val miqats = listOf("Yalamlam (for Pakistani pilgrims arriving by sea/air via South)", "Dhul Hulaifah / Abyar Ali (for pilgrims from Madinah)", "Juhfah (for pilgrims arriving from West/Syria/Egypt)", "Qarn al-Manazil (for pilgrims from Najd/East)", "Dhat Irq (for pilgrims from Iraq)")
        val positions = listOf("Supervisor", "Supporting Staff", "Both")

        val fiqhScenarios = listOf(
            Triple(
                "Ihram Violation & Dam Penalty",
                "If a male pilgrim inadvertently wears stitched clothing or covers his head for a full 24 hours while in Ihram due to severe cold weather, what is the mandatory Fiqhi expiation (Dam)?",
                "Sacrifice of one sheep/goat (Dam) in Makkah, OR feeding 6 poor persons, OR fasting for 3 days"
            ),
            Triple(
                "Tawaf-e-Ziyarah (Tawaf al-Ifadah) Timing",
                "Tawaf-e-Ziyarah is one of the mandatory Fard pillars of Hajj. When does the permissible time for performing Tawaf-e-Ziyarah commence?",
                "After midnight on the 10th of Dhu al-Hijjah (Day of Nahr) after performing Jamarat Rami or Wuquf-e-Arafat"
            ),
            Triple(
                "Wuquf-e-Arafat Timing & Fard Status",
                "Wuquf-e-Arafat (standing at Arafat) is the supreme Fard pillar of Hajj. What is the valid time window for standing in Arafat on the 9th Dhu al-Hijjah?",
                "From Zawal (solar noon) on 9th Dhu al-Hijjah until the dawn (Fajr) of 10th Dhu al-Hijjah"
            ),
            Triple(
                "Muzdalifah Night Mabit & Pebble Collection",
                "What is the Sunnah duration for staying at Muzdalifah after leaving Arafat on the evening of 9th Dhu al-Hijjah?",
                "Staying overnight until Fajr prayer on 10th Dhu al-Hijjah, performing Maghrib and Isha combined at Isha time"
            ),
            Triple(
                "Jamarat Rami Sequence on 10th Dhu al-Hijjah",
                "On the 10th of Dhu al-Hijjah (Yaum-un-Nahr), which Jamarah is stoned, and how many pebbles are thrown?",
                "Only Jamarah al-Aqba (the Big Jamarah), throwing exactly 7 pebbles while reciting Takbeer"
            ),
            Triple(
                "Jamarat Rami Order on 11th and 12th Dhu al-Hijjah",
                "On the Days of Tashreeq (11th & 12th Dhu al-Hijjah), what is the correct chronological sequence for stoning the three Jamarat?",
                "Jamarah al-Ula (Small) -> Jamarah al-Wusta (Middle) -> Jamarah al-Aqba (Large), 7 pebbles each (21 total per day)"
            ),
            Triple(
                "Tawaf-e-Wida (Farewell Tawaf) Requirement",
                "Who among the pilgrims is obligated to perform Tawaf-e-Wida (Farewell Tawaf) before departing MakkahMukarramah?",
                "All non-resident (Afaqi) pilgrims, except women experiencing menstruation or post-postpartum bleeding"
            ),
            Triple(
                "Three Types of Hajj (Tamattu, Qiran, Ifrad)",
                "A Pakistani pilgrim performing Hajj-e-Tamattu enters Ihram for Umrah first during Hajj months, performs Umrah, trims hair, exits Ihram, and re-enters Ihram for Hajj on 8th Dhu al-Hijjah. Is Qurbani (Dam-e-Shukr) mandatory for them?",
                "Yes, Qurbani (Dam-e-Shukr) is strictly mandatory for Hajj-e-Tamattu and Hajj-e-Qiran"
            ),
            Triple(
                "Sa'i Between Safa and Marwah Rules",
                "How many total laps (Shawt) constitute a complete Sa'i between Safa and Marwah, and where does it begin and terminate?",
                "Exactly 7 laps; starting at Mount Safa and ending at Mount Marwah (Safa to Marwah is Shawt 1, Marwah to Safa is Shawt 2)"
            ),
            Triple(
                "Niyyat & Talbiyah for Ihram",
                "When entering the state of Ihram at Miqat, at what exact moment does the pilgrim formally enter Ihram restrictions?",
                "Immediately upon uttering the Niyyat (intention) and reciting the Talbiyah ('Labbayk Allahumma Labbayk')"
            )
        )

        for (i in 0 until 200) {
            val qId = idCounter++
            val scenario = fiqhScenarios[i % fiqhScenarios.size]
            val miqat = miqats[i % miqats.size]
            val position = positions[i % positions.size]

            val optionIndex = i % 4
            val questionText = "Hajj Rules & Fiqh Question #${i + 1} [$position Cadre / Miqat: $miqat]: Regarding ${scenario.first}, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "No action or expiation is required as all restrictions are automatically waived during crowd peaks"
            val w2 = "The entire Hajj pilgrimage is rendered void immediately requiring cancellation of flight ticket"
            val w3 = "Pay a penalty fine to the local driver without performing any ritual or religious expiation"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "Masail-e-Hajj Fiqh Standard: ${scenario.first} is a core requirement tested by MORA/NTS for Moavineen-e-Hujjaj. ${scenario.third} ensures Moavineen accurately guide pilgrims on correct rituals."
            val ref = "MORA Moavineen-e-Hujjaj Training Manual & Fiqh-e-Hajj Syllabus"

            list.add(
                MoavineenQuestion(
                    id = qId,
                    positionTarget = position,
                    subjectCategory = "Hajj Rules & Arkan",
                    question = questionText,
                    options = opts,
                    correctIndex = optionIndex,
                    explanation = explanation,
                    reference = ref
                )
            )
        }

        return list
    }
}
