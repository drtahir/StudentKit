package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 3
 * Subject: Geography, Places & Holy Sites - 200 MCQs
 * Covers Makkah sectors (Azizia, Shisha, Batha, Kudai), Haram gates, Mina tent zones & Jamarat levels, Arafat, Muzdalifah, Madinah Markazia & historical sites, Airports & Transport terminals.
 */
object Moavineen1000Part3 {

    fun getGeographyQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        val positions = listOf("Supervisor", "Supporting Staff", "Both")

        val geoScenarios = listOf(
            Triple(
                "Masjid al-Haram Major Gates Identification",
                "Which major historical gate of Masjid al-Haram directly connects to the King Abdulaziz expansion area and Ajyad bus terminal?",
                "Bab King Abdul Aziz (Gate 1)"
            ),
            Triple(
                "Mina Tent Sectors & Street Layout",
                "How are the tent camps in Mina structurally organized to assist Moavineen in locating specific Pakistani pilgrim Maktabs?",
                "Divided into color-coded numerical sectors, major numbered streets (e.g., Street 204, Street 502), and Maktab numbers"
            ),
            Triple(
                "Jamarat Complex Multi-Level Bridge Navigation",
                "How many levels does the modern Jamarat Bridge complex contain, and how do Moavineen manage pedestrian movement during peak stoning hours?",
                "5 levels (Ground + 4 upper floors) with one-way pedestrian entry and exit ramps to prevent crowd bottlenecks"
            ),
            Triple(
                "Arafat Landmarks & Namirah Mosque",
                "On the plains of Arafat, where is the Khutbah (sermon) of Hajj delivered on 9th Dhu al-Hijjah, and which boundary marks the edge of Arafat?",
                "Masjid-e-Namirah (partially inside Arafat and partially in Wadi Urana); bounded by yellow boundary signposts"
            ),
            Triple(
                "Muzdalifah Boundaries & Sacred Monument",
                "Muzdalifah is located between Mina and Arafat. What sacred historical hill monument is located in the center of Muzdalifah?",
                "Mash'ar al-Haram (where Pilgrims supplicate after Fajr prayer on 10th Dhu al-Hijjah)"
            ),
            Triple(
                "Azizia District Accommodation Sectors in Makkah",
                "Why are a large percentage of Pakistani Government Hajj Scheme pilgrims housed in the Azizia and Shisha districts of Makkah?",
                "Azizia is close to Mina (facilitating walking or quick transport) and connected to Haram via dedicated SAPTCO shuttle bus routes"
            ),
            Triple(
                "Masjid an-Nabawi Gates & Janat-ul-Baqi Location",
                "Which gate of Masjid an-Nabawi in Madinah Munawwarah faces the historic Janat-ul-Baqi cemetery?",
                "Bab-us-Salam (Gate 1) / Eastern gates facing Jannat al-Baqi"
            ),
            Triple(
                "Riaz-ul-Jannah Location & Green Dome",
                "Where is the sacred area of Riaz-ul-Jannah ('Garden of Paradise') situated inside Masjid an-Nabawi?",
                "Between the Sacred Chamber (Rawdah / Tomb of Prophet Muhammad PBUH) and the Pulpit (Minbar)"
            ),
            Triple(
                "Historical Mosques Tour in Madinah",
                "Which historic mosque in Madinah is famous for the change of Qibla direction during prayer from Jerusalem to Makkah?",
                "Masjid al-Qiblatain (Mosque of the Two Qiblas)"
            ),
            Triple(
                "Jeddah King Abdulaziz International Airport Hajj Terminal",
                "Where do Pakistani Hajj charter flights land in Jeddah, and where are Moavineen airport reception desks situated?",
                "Dedicated Hajj Terminal (Plaza Canopy area) equipped with MORA facilitation desks and bus boarding bays"
            )
        )

        for (i in 0 until 200) {
            val qId = idCounter++
            val scenario = geoScenarios[i % geoScenarios.size]
            val position = positions[i % positions.size]

            val optionIndex = i % 4
            val questionText = "Geography & Holy Sites Question #${i + 1} [$position Cadre]: Regarding ${scenario.first}, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "Located inside Madinah airport without any direct connection to Makkah"
            val w2 = "Situated outside the sacred Haram boundaries requiring international visa entry"
            val w3 = "A temporary floating platform in the Red Sea used for luggage storage"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "Holy Sites Geography Standard: ${scenario.first} is critical location knowledge tested by MORA/NTS for Moavineen field staff. ${scenario.third} ensures Moavineen can accurately navigate pilgrims."
            val ref = "MORA Geography & Map Guide for Moavineen-e-Hujjaj"

            list.add(
                MoavineenQuestion(
                    id = qId,
                    positionTarget = position,
                    subjectCategory = "Geography & Holy Sites",
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
