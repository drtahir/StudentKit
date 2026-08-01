package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 2
 * Subject: Moavineen Operational SOPs & Duty Rules - 200 MCQs
 * Covers Sector Management, Maktab coordination, Lost Pilgrim Centers (Tayeena/Ghumshuda Camp), Luggage tracking, Airport reception, Bus/Railway transport, Supervisor dispatch & Supporting Staff duties.
 */
object Moavineen1000Part2 {

    fun getOperationalSopQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        val positions = listOf("Supervisor", "Supporting Staff", "Both")
        val locations = listOf("Mina Sector 3 Maktab 42", "Arafat Camp Gate 5", "Muzdalifah Bus Drop-off", "Jeddah Hajj Terminal", "Azizia Building 104", "Madinah Markazia Control")

        val sops = listOf(
            Triple(
                "Lost Pilgrim Reception Protocol (Tayeena Center)",
                "When a supporting staff Moavin finds an elderly Pakistani pilgrim wandering lost in Mina without a tent card, what is the immediate first SOP step?",
                "Check the pilgrim's official MORA identity wristband/locket, scan barcode on phone, contact sector dispatch, and accompany them to the nearest Tayeena (Lost & Found) Center"
            ),
            Triple(
                "Supervisor Shift Rotation & Attendance Logging",
                "What is the duty of a Moavineen Sector Supervisor regarding field staff shift reporting during peak days (8th to 12th Dhu al-Hijjah)?",
                "Maintain physical shift rosters, verify staff presence at designated Maktab points every 2 hours, and submit real-time reports to Director Moavineen"
            ),
            Triple(
                "Luggage Misplacement & Tagging System",
                "A pilgrim's green bag is missing at Jeddah Hajj Terminal upon arrival. What procedure must the airport Supporting Staff Moavin follow?",
                "Log lost luggage voucher with flight details and MORA serial number, issue temporary claim receipt, and notify Makkah Main Luggage Cell"
            ),
            Triple(
                "Mina Tent Allotment & Maktab Coordination",
                "When a convoy of Pakistani pilgrims arrives at their allotted Maktab in Mina on 8th Dhu al-Hijjah, how should Moavineen manage tent entry?",
                "Verify Maktab numbers on pilgrim wristbands, guide male and female groups to designated partitioned tents, and ensure no unauthorized persons occupy beds"
            ),
            Triple(
                "Mashair Railway & Bus Boarding Queue Control",
                "During the movement from Mina to Arafat on 9th Dhu al-Hijjah, what is the primary role of Supporting Staff stationed at Mashair train stations / bus stops?",
                "Maintain disciplined queue lines, prioritize elderly and female pilgrims, prevent overcrowding at platform gates, and coordinate bus departure with supervisors"
            ),
            Triple(
                "Lost Cash & Currency Recovery Handover",
                "If a Moavin finds a lost bag containing Saudi Riyals/Pakistani Rupees in the Haram courtyard, what is the mandatory MORA accounting SOP?",
                "Hand over the wallet immediately to the MORA Main Lost & Found Committee with two witnessing staff signatures and log an official deposit voucher"
            ),
            Triple(
                "Medical Emergency Liaison with Pakistani Hajj Medical Mission",
                "When a pilgrim suffers acute heat exhaustion or injury in a Mina tent, what is the Moavin's duty before ambulance arrival?",
                "Provide shade/water, alert the nearest Pakistani Hajj Medical Mission dispensary, inform the Sector Supervisor, and assist paramedics with stretcher carry"
            ),
            Triple(
                "Wheelchair Assistance & Haram Transport Protocols",
                "How are Moavineen supporting staff assigned to assist physically disabled or frail elderly pilgrims visiting Masjid al-Haram for Tawaf?",
                "Provide approved MORA wheelchairs, assist through designated wheelchair ramps (e.g. Ajyad / King Abdulaziz gates), and remain in contact with sector team"
            ),
            Triple(
                "Daily Reporting & Incident Logbook Maintenance",
                "What critical records must a Moavineen Field Supervisor submit at the end of each 8-hour operational shift?",
                "Total lost pilgrims reunited, medical emergency referrals, luggage claims resolved, staff attendance, and unresolved Maktab complaints"
            ),
            Triple(
                "Inter-Agency Coordination with Saudi Civil Defense & Mutawwif",
                "In case of a localized tent fire alarm or power outage in a Mina sector, how does the Moavineen Supervisor coordinate evacuation?",
                "Contact Saudi Civil Defense (998) / Saudi Red Crescent (997), alert the Mutawwif (Tawafa office), and direct pilgrims through marked emergency exit lanes"
            )
        )

        for (i in 0 until 200) {
            val qId = idCounter++
            val scenario = sops[i % sops.size]
            val loc = locations[i % locations.size]
            val position = positions[i % positions.size]

            val optionIndex = i % 4
            val questionText = "Moavineen Operational SOP Question #${i + 1} [$position / Location: $loc]: Regarding ${scenario.first}, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "Instruct the pilgrim to walk alone back to Makkah without checking wristband details"
            val w2 = "Leave the duty post unannounced and hand over responsibility to passing tourists"
            val w3 = "Keep confiscated items personally without logging an official MORA receipt"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "MORA Operational SOP Standard: ${scenario.first} is a core duty requirement for Moavineen-e-Hujjaj. ${scenario.third} ensures pilgrim safety, accountability, and smooth field coordination."
            val ref = "Ministry of Religious Affairs (MORA) Moavineen Operational SOP Manual"

            list.add(
                MoavineenQuestion(
                    id = qId,
                    positionTarget = position,
                    subjectCategory = "Moavineen Operational SOPs",
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
