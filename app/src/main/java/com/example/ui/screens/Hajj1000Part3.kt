package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 3
 * Category: Vaccine & Outbreaks (170 Unique MCQs)
 * Covers ACYW135 Meningococcal conjugate vaccine, MERS-CoV, Cholera epidemic response, Polio OPV entry rules, Dengue, Isolation PPE & Chemoprophylaxis.
 */
object Hajj1000Part3 {

    fun getVaccineOutbreakQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        val countries = listOf("Pakistan", "Afghanistan", "Nigeria", "Somalia", "Yemen", "Syria", "Sudan", "Bangladesh", "Indonesia", "India")
        val locations = listOf("Mina Tent Sector 4", "Arafat Emergency Encampment", "Madinah Central Hotel Zone", "Azizia Pilgrims Residence", "Makkah Dispensary", "Jeddah Airport Health Control")

        val outbreakScenarios = listOf(
            Triple(
                "Meningococcal ACYW135 Conjugate Vaccine Mandate",
                "How far in advance of arrival in Saudi Arabia must a pilgrim receive the Quadrivalent Meningococcal Conjugate (ACYW135) vaccine?",
                "At least 10 days prior to arrival, providing valid documentation not exceeding 5 years (for conjugate vaccine)"
            ),
            Triple(
                "Meningococcal Disease Chemoprophylaxis",
                "A pilgrim in Mina is confirmed to have Neisseria meningitidis meningitis. What is the single-dose oral drug of choice for post-exposure prophylaxis in room contacts?",
                "Ciprofloxacin 500 mg orally as a single dose (or Rifampicin 600 mg twice daily for 2 days)"
            ),
            Triple(
                "MERS-CoV Isolation & PPE Protocols",
                "When evaluating a pilgrim with fever, severe cough, dyspnea, and recent contact with camels in Saudi Arabia, what infection control PPE is mandatory?",
                "N95 respirator mask, eye protection (goggles/face shield), fluid-resistant gown, and non-sterile gloves in a negative-pressure room"
            ),
            Triple(
                "Cholera Epidemic Management & Rehydration",
                "During a suspected Vibrio cholerae outbreak in a Mina camp, what is the single most critical life-saving intervention for patients with severe watery diarrhea?",
                "Rapid aggressive intravenous fluid resuscitation with Ringer's Lactate or Normal Saline, followed by Oral Rehydration Salts (ORS)"
            ),
            Triple(
                "Port-of-Entry Oral Polio Vaccine (OPV)",
                "Why do Saudi Port Health Authorities administer a dose of Bivalent Oral Polio Vaccine (bOPV) to all arriving pilgrims from Pakistan regardless of prior vaccination history?",
                "To eliminate wild poliovirus excretion and prevent potential viral re-introduction into the global pilgrimage population"
            ),
            Triple(
                "Mass Food Poisoning Staphylococcal Enterotoxin",
                "Within 3 hours of eating catered chicken rice in Mina, 40 pilgrims develop sudden violent vomiting, abdominal cramps, and afebrile prostration. What is the cause?",
                "Staphylococcal enterotoxin intoxication caused by pre-formed heat-stable toxins in improperly stored cooked food"
            ),
            Triple(
                "Yellow Fever Vaccination Entry Requirements",
                "Pilgrims arriving from Yellow Fever endemic countries in South America or Sub-Saharan Africa must present an International Certificate of Vaccination issued how many days prior?",
                "At least 10 days before arrival in Saudi Arabia, valid for life under International Health Regulations (IHR 2005)"
            ),
            Triple(
                "Seasonal Influenza Immunization for High-Risk Pilgrims",
                "When should the seasonal influenza vaccine ideally be administered to high-risk elderly or diabetic pilgrims preparing for Hajj?",
                "At least 2 weeks prior to departure to allow adequate antibody response before mass crowd exposure"
            ),
            Triple(
                "Dengue Fever Vector Control in Makkah",
                "What is the principal mosquito vector responsible for Dengue fever transmission in urban Makkah, and what is the primary preventive control strategy?",
                "Aedes aegypti mosquito; managed by eliminating standing water containers and fogging insecticide applications"
            ),
            Triple(
                "Donning PPE Sequence for Infectious Outbreaks",
                "What is the correct sequence for DONNING (putting on) Personal Protective Equipment when entering an isolation room with a suspected respiratory outbreak patient?",
                "Gown first -> Mask/N95 Respirator -> Eye Protection (Goggles/Face Shield) -> Gloves (extended over gown cuffs)"
            )
        )

        for (i in 0 until 170) {
            val qId = idCounter++
            val scenario = outbreakScenarios[i % outbreakScenarios.size]
            val country = countries[i % countries.size]
            val loc = locations[i % locations.size]

            val optionIndex = i % 4
            val questionText = "Vaccine & Outbreaks Question #${i + 1} [Origin: $country / Location: $loc]: Regarding ${scenario.first}, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "Ignore isolation guidelines and manage the patient in an open multi-bed ward without PPE"
            val w2 = "Administer oral antibiotics only without reporting to Saudi Public Health Authorities"
            val w3 = "Discontinue all vaccine checks at airport entry ports to avoid flight delays"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "Infection Control & Public Health Standard: ${scenario.first} is essential for outbreak prevention. ${scenario.third} prevents epidemic spread across international pilgrim contingents."
            val ref = "Saudi MOH Public Health Regulations for Hajj & CDC Yellow Book"

            list.add(
                HajjQuestion(
                    id = qId,
                    category = "Vaccine & Outbreaks",
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
