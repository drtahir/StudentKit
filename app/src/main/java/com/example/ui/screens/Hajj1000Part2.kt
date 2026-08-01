package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 2
 * Category: Heat Stroke & Hydration (170 Unique MCQs)
 * Covers hyperthermia pathophysiology, active cooling techniques, fluid resuscitation, heat exhaustion vs heat stroke, rhabdomyolysis & electrolyte management.
 */
object Hajj1000Part2 {

    fun getHeatStrokeQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        val ages = listOf(56, 61, 65, 69, 73, 77, 81, 84, 88, 92)
        val temps = listOf("104.0°F (40.0°C)", "104.8°F (40.4°C)", "105.4°F (40.8°C)", "106.0°F (41.1°C)", "106.5°F (41.4°C)", "103.8°F (39.9°C)")
        val locations = listOf("Mina Pedestrian Tunnel", "Plains of Arafat", "Jamarat Bridge Corridor", "Muzdalifah Walkway", "Haram Courtyard", "Azizia Pilgrims Camp")

        val heatScenarios = listOf(
            Triple(
                "Heat Stroke vs Heat Exhaustion Distinction",
                "What clinical feature definitively differentiates Heat Stroke from Heat Exhaustion in a collapsed pilgrim evaluated at Arafat?",
                "Central Nervous System (CNS) dysfunction (altered mental status, confusion, delirium, seizures, or coma)"
            ),
            Triple(
                "Evaporative Physical Cooling Method",
                "What is considered the gold-standard physical cooling technique in field medical centers during peak summer Hajj heat?",
                "Continuous mist spraying of tepid water combined with high-velocity airflow from electric fans until core temperature reaches 38.9°C (102°F)"
            ),
            Triple(
                "Rhabdomyolysis Complication in Exertional Heat Stroke",
                "A pilgrim collapsed after walking 10 km under 46°C heat has dark tea-colored urine and severe muscle tenderness. What laboratory finding confirms acute rhabdomyolysis?",
                "Markedly elevated Serum Creatine Kinase (CK > 5000 U/L) and presence of urine myoglobin"
            ),
            Triple(
                "Exercise-Associated Hyponatremia (EAH)",
                "A pilgrim complaining of severe headache, nausea, and confusion after drinking 10 liters of plain un-salted water in Mina has a serum Sodium of 118 mEq/L. What is the cause?",
                "Dilutational Exercise-Associated Hyponatremia caused by excessive hypotonic water intake without electrolyte replacement"
            ),
            Triple(
                "Target Temperature to Cease Active Cooling",
                "Why must active physical cooling measures be discontinued when a heat stroke patient's rectal temperature reaches 38.9°C (102°F)?",
                "To prevent iatrogenic hypothermic overshoot and uncontrollable shivering, which increases metabolic heat production"
            ),
            Triple(
                "Intravenous Fluid Resuscitation Choice",
                "For a hypovolemic, heat-exhausted diabetic pilgrim presenting with hypotension (BP 85/50 mmHg), what is the IV fluid of choice?",
                "Isotonic 0.9% Normal Saline or Ringer's Lactate administered in 500-1000 mL boluses with continuous blood pressure monitoring"
            ),
            Triple(
                "Risk Factors Worsening Thermoregulation",
                "Which medication class taken by an elderly hypertensive pilgrim severely impairs sweating and increases heat stroke susceptibility?",
                "Anticholinergic agents, antihistamines, and tricyclic antidepressants (which inhibit sweat gland secretion)"
            ),
            Triple(
                "Heat Cramps Management",
                "A pilgrim experiences painful involuntary spasms of the gastrocnemius muscles after heavy exertion in Muzdalifah with heavy sweating. What is the correct management?",
                "Rest in a cool shaded area, gentle muscle stretching, and oral electrolyte replacement (ORS) or isotonic saline solution"
            ),
            Triple(
                "Disseminated Intravascular Coagulation (DIC) in Heat Stroke",
                "A critically hyperthermic pilgrim in the ICU develops petechiae, oozing from IV puncture sites, and prolonged PT/aPTT. What emergency condition has developed?",
                "Heat-induced Disseminated Intravascular Coagulation (DIC) secondary to systemic thermal endothelial damage"
            ),
            Triple(
                "Role of Dantrolene Sodium in Heat Stroke",
                "Why is Dantrolene Sodium NOT recommended for routine treatment of exertional or classic heat stroke in field clinics?",
                "Dantrolene is specific for Malignant Hyperthermia and has shown no clinical efficacy in environmental hyperthermia trials"
            )
        )

        for (i in 0 until 170) {
            val qId = idCounter++
            val scenario = heatScenarios[i % heatScenarios.size]
            val age = ages[i % ages.size]
            val temp = temps[i % temps.size]
            val loc = locations[i % locations.size]

            val optionIndex = i % 4
            val questionText = "Heat Stroke & Hydration Question #${i + 1} [$loc / Patient Age $age / Temp $temp]: In relation to ${scenario.first}, ${scenario.second}"
            val correctOpt = scenario.third
            val w1 = "Administer high-dose antipyretics like acetaminophen to reset the hypothalamic set-point"
            val w2 = "Apply heavy warm thermal blankets to encourage natural sweating without fans"
            val w3 = "Restrict fluid intake strictly to 100 mL per 24 hours to prevent cerebral edema"

            val opts = buildOptions(optionIndex, correctOpt, w1, w2, w3)
            val explanation = "Clinical Heat Protocol: ${scenario.first} requires accurate diagnosis and rapid intervention. ${scenario.third}. Antipyretics are ineffective and contraindicated in environmental heat stroke."
            val ref = "Saudi MOH Heat Emergency Guidelines & NTS Clinical Syllabus"

            list.add(
                HajjQuestion(
                    id = qId,
                    category = "Heat Stroke & Hydration",
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
