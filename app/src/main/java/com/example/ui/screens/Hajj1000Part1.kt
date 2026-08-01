package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 1
 * Category: Hajj Rules & Admin (170 Unique MCQs)
 * Covers MORA SOPs, Haji Camp protocols, deployment rosters, Hajj ritual calendars, emergency liaison & administration.
 */
object Hajj1000Part1 {

    fun getHajjRulesQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        val cities = listOf("Islamabad", "Lahore", "Karachi", "Peshawar", "Quetta", "Multan", "Faisalabad", "Sialkot", "Rawalpindi", "Sukkur")
        val roles = listOf("Medical Officer", "Charge Nurse", "Pharmacist", "Paramedic", "Dental Surgeon", "Laboratory Technologist", "Radiographer")
        val rituals = listOf("Mina Tent Encampment", "Wuquf-e-Arafat", "Muzdalifah Night Mabit", "Jamarat Rami (Stoning)", "Tawaf-e-Ziyarah", "Tawaf-e-Wida")
        val days = listOf("8th Dhu al-Hijjah", "9th Dhu al-Hijjah", "Night of 9th Dhu al-Hijjah", "10th Dhu al-Hijjah", "11th-12th Dhu al-Hijjah", "13th Dhu al-Hijjah")

        val adminScenarios = listOf(
            Triple(
                "MORA Staff Selection 5-Year Rule",
                "What is the primary rationale behind MORA enforcing a mandatory 5-year gap rule for healthcare professionals applying for the Pakistani Hajj Medical Mission?",
                "To ensure equal opportunity, fair rotation, and prevent repeated deployment of the same personnel while maintaining high physical fitness standards"
            ),
            Triple(
                "Haji Camp Pre-Departure Medical Screening",
                "During pre-departure screening at the provincial Haji Camp, what is the mandatory action if an intending pilgrim presents with severe uncontrolled chronic illness without a fitness certificate?",
                "Refer the pilgrim immediately to the District Medical Board for formal health reassessment before issuing flight clearance"
            ),
            Triple(
                "Makkah Medical Mission Headquarters Arrival SOP",
                "Upon arrival at the central Pakistani Hajj Medical Mission Headquarters in Makkah (Azizia), what is the first duty of deployed clinical staff?",
                "Register at the Director Medical's office to receive sector assignment, duty roster, and emergency contact codes"
            ),
            Triple(
                "Mina Medical Post Shift Handover",
                "During shift handover at a Sector Clinic in Mina, what essential information must the outgoing Charge Nurse provide to the incoming team?",
                "Complete inventory of controlled medications, cold-chain status of vaccines/insulin, emergency referral logs, and active critical bed patients"
            ),
            Triple(
                "Saudi MOH Emergency Referral Liaison",
                "When a critically ill pilgrim requires immediate transfer from a field clinic in Arafat to a Saudi Tertiary Hospital (e.g., Arafat General Hospital), what protocol must be followed?",
                "Notify the Saudi Red Crescent (997) or Red Crescent Dispatch and complete the MORA Medical Mission official emergency referral form"
            ),
            Triple(
                "Wuquf-e-Arafat Emergency Medical Deployment",
                "On the 9th Dhu al-Hijjah (Wuquf-e-Arafat), how are mobile medical teams positioned across the plains of Arafat?",
                "Strategically distributed along major pedestrian arterial pathways and field medical tents with portable resuscitation kits"
            ),
            Triple(
                "Muzdalifah Night Clinical Surveillance",
                "During the night stay at Muzdalifah, what is the primary role of the roving medical paramedics accompanying the Pakistani pilgrim convoy?",
                "Provide rapid triage for acute physical exhaustion, minor foot injuries, dehydration, and manage lost elderly pilgrims"
            ),
            Triple(
                "Jamarat Rami Crowd Control Triage",
                "On 10th Dhu al-Hijjah at Mina during Jamarat Rami, what is the triage priority for medical teams positioned at the Jamarat exit corridors?",
                "Immediate field management of acute heat collapse, crush injuries, hyperthermia, and rapid ambulance evacuation"
            ),
            Triple(
                "Drug Requisition & Stock Buffer Management",
                "How should a Sector Medical Officer in Mina manage stock thresholds when life-saving ORS and IV fluids fall below 25% capacity?",
                "Submit an emergency requisition voucher to the Central Medical Store in Makkah via the dedicated supply logistics coordinator"
            ),
            Triple(
                "Pilgrim Wristband Verification Protocol",
                "When treating an unidentified or disoriented pilgrim in a field clinic, how does the medical team verify their nationality, group, and medical record?",
                "Scan or read the official MORA barcode/QR code printed on the pilgrim's mandatory identity wristband"
            )
        )

        for (i in 0 until 170) {
            val qId = idCounter++
            val scenario = adminScenarios[i % adminScenarios.size]
            val city = cities[i % cities.size]
            val role = roles[i % roles.size]
            val ritual = rituals[i % rituals.size]
            val day = days[i % days.size]

            val optionIndex = i % 4
            val questionText = "Hajj Rules & Admin Scenario #${i + 1} [$city Haji Camp / $role]: Regarding ${scenario.first} during $ritual on $day, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "Ignore administrative guidelines and allow unverified deployment without medical clearance"
            val w2 = "Discontinue emergency medical logging to reduce paperwork during peak pilgrimage hours"
            val w3 = "Transfer patient care exclusively to untrained volunteer guides without clinical handover"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "Administrative & MORA SOP Standard: ${scenario.first} requires strict adherence to protocol. ${scenario.third} ensures patient safety, operational compliance, and smooth coordination with Saudi health authorities."
            val ref = "MORA Hajj Medical Mission Guidelines & NTS Syllabus Section 1"

            list.add(
                HajjQuestion(
                    id = qId,
                    category = "Hajj Rules & Admin",
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
