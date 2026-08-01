package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 5
 * Subject: Management, Ethics & Situational Judgment (Husn-e-Akhlaq & Crowd Control) - 150 MCQs
 * Covers Islamic Ethics of Hujjaj Khidmat (Husn-e-Akhlaq), Crowd Management & Stampede Avoidance, Conflict Resolution, Elderly & Disabled Care, Heat Stress & Team Supervision.
 */
object Moavineen1000Part5 {

    fun getManagementEthicsQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        val positions = listOf("Supervisor", "Supporting Staff", "Both")

        val ethicsScenarios = listOf(
            Triple(
                "Patience & High Moral Conduct (Husn-e-Akhlaq)",
                "An exhausted and anxious elderly pilgrim angrily complains to a Moavin about delayed bus transport under intense heat. What is the ethical Islamic response required of the Moavin?",
                "Remain calm and polite, offer shade/water, speak softly with empathy, reassure them, and resolve transport coordination through supervisor"
            ),
            Triple(
                "Crowd Surge & Panic Mitigation in Narrow Corridors",
                "When a sudden crowd bottleneck forms near Jamarat exit ramps, how should Moavineen field staff manage the situation to prevent panic?",
                "Form human safety chains, guide pilgrims firmly into side dispersal lanes, keep crowds moving in one continuous direction, and avoid standing still"
            ),
            Triple(
                "Handling Disoriented or Demented Elderly Pilgrims",
                "An elderly confused pilgrim with memory loss insists on walking alone in Arafat heat away from their camp. What is the correct staff intervention?",
                "Gently escort the pilgrim to a shaded resting tent, verify identity locket/wristband, provide hydration, and contact their Maktab guide"
            ),
            Triple(
                "Team Conflict Resolution for Supervisors",
                "Two supporting staff Moavineen get into a heated verbal argument regarding shift duty hours at Makkah main office. How should the Supervisor resolve it?",
                "Immediately intervene calmly, separate the staff to private room, review the official duty roster objectively, and enforce fair rotation without bias"
            ),
            Triple(
                "Physical Endurance & Self-Care under 48°C Heat",
                "How should a Moavin maintain personal physical fitness and hydration during 12-hour outdoor field duty in Mina summer heat?",
                "Drink oral rehydration solution (ORS) and water regularly, wear sun protection/shades, rest during off-shifts, and monitor heat stress signs"
            ),
            Triple(
                "Integrity & Anti-Bribery Code of Conduct",
                "A wealthy private pilgrim offers a cash tip (Riyals) to a Moavin for prioritizing their family's bus seating ahead of elderly pilgrims. What is the mandated action?",
                "Politely refuse the cash gift, explain MORA's strict equal service policy, and maintain fair queue sequence for all pilgrims"
            ),
            Triple(
                "Assisting Female Pilgrims (Hajjat) & Family Tents",
                "When entering female-designated tent areas in Mina to inspect water supplies or deliver lost luggage, what decorum must Moavineen observe?",
                "Knock and announce presence loudly, seek permission, ensure female staff/volunteers accompany if possible, and maintain modesty"
            ),
            Triple(
                "Stress Management during Peak Pilgrimage Days",
                "During 9th-10th Dhu al-Hijjah (Arafat to Muzdalifah movement), field staff experience extreme fatigue. What leadership practice prevents team burnout?",
                "Supervisors rotate short 15-minute rest breaks, ensure adequate drinking water availability, and maintain team encouragement and morale"
            ),
            Triple(
                "Handling Lost Luggage Disputes with Mutawwif",
                "A Mutawwif driver accidentally leaves a group's luggage in Arafat while transporting pilgrims to Muzdalifah. How should the Supervisor handle the complaint?",
                "Log the exact bus and Maktab numbers, alert MORA Transport Cell immediately, track the vehicle driver via dispatch, and keep pilgrims calm"
            ),
            Triple(
                "Service as Spiritual Responsibility (Khidmat al-Hujjaj)",
                "According to Islamic teachings, what is the spiritual status and reward of serving pilgrims (Guests of Allah / Dhuyuf-ur-Rahman)?",
                "It is considered a noble act of worship (Ibadah) and national duty, earning immense divine reward, forgiveness, and blessings"
            )
        )

        for (i in 0 until 150) {
            val qId = idCounter++
            val scenario = ethicsScenarios[i % ethicsScenarios.size]
            val position = positions[i % positions.size]

            val optionIndex = i % 4
            val questionText = "Ethics & Management Question #${i + 1} [$position Cadre]: Regarding ${scenario.first}, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "Argue loudly with the pilgrim and abandon the duty post immediately"
            val w2 = "Accept cash bribes to bypass elderly pilgrims in bus queues"
            val w3 = "Ignore disoriented pilgrims and leave them unassisted in the sun"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "Management & Ethics Standard: ${scenario.first} is a key evaluation area in MORA/NTS selection tests. ${scenario.third} reflects the highest standards of Islamic service and professional leadership."
            val ref = "MORA Code of Conduct & Ethics Manual for Moavineen-e-Hujjaj"

            list.add(
                MoavineenQuestion(
                    id = qId,
                    positionTarget = position,
                    subjectCategory = "Management & Ethics",
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
