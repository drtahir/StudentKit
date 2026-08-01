package com.example.ui.screens

/**
 * ULTRA BANK PART 4: TRAUMA, EMERGENCY TRIAGE, LEADERSHIP & PNC COMPETENCIES (80 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500UltraPart4 {

    fun getCriticalCareUltraQuestions(startId: Int): List<NursingExamQuestion> {
        var idCounter = startId
        val list = mutableListOf<NursingExamQuestion>()

        fun addQ(
            subject: String,
            examCategory: String,
            question: String,
            options: List<String>,
            correctIndex: Int,
            rationale: String,
            distractorExplanations: String,
            topicSubtopic: String
        ) {
            val fullExplanation = "$rationale\n\n📌 Option Breakdown:\n$distractorExplanations"
            list.add(
                NursingExamQuestion(
                    id = idCounter++,
                    subject = subject,
                    examCategory = examCategory,
                    question = question,
                    options = options,
                    correctIndex = correctIndex,
                    explanation = fullExplanation,
                    reference = topicSubtopic
                )
            )
        }

        val ultraTopicsPart4 = listOf(
            Triple("Emergency Triage: START Triage System Tagging Criteria", "RED (Immediate): Life-threatening injury but salvageable (e.g., tension pneumothorax, severe airway compromise, RR > 30, capillary refill > 2 sec); YELLOW (Delayed): Major injury non-life-threatening (fractures); GREEN (Minimal): Walking wounded; BLACK (Expectant/Deceased): Apneic even after opening airway, catastrophic head injury", "BLACK tag is assigned to minor ankle sprains who are walking"),
            Triple("Trauma Nursing: Primary Survey ABCDE Sequence", "Primary Survey: A (Airway with C-spine protection), B (Breathing & ventilation), C (Circulation with hemorrhage control), D (Disability / Neuro status - GCS), E (Exposure / Environmental control - prevent hypothermia)", "Sequence is Exposure first, then Disability, then Airway last"),
            Triple("Critical Care: Hemodynamic Monitoring Pulmonary Artery Occlusion Pressure (PAOP/PCWP)", "PAOP/PCWP measures LEFT VENTRICULAR END-DIASTOLIC PRESSURE (preload); Normal PAOP: 6 - 12 mmHg; Elevated PAOP (> 18 mmHg) indicates left heart failure or hypervolemia; Low PAOP indicates hypovolemia", "PAOP measures right atrial pressure exclusively; normal is 50-80 mmHg"),
            Triple("Critical Care: Intra-Aortic Balloon Pump (IABP) Inflation/Deflation Timing", "IABP Inflates at the onset of DIASTOLE (dicrotic notch) to increase coronary artery perfusion; Deflates immediately BEFORE SYSTOLE to decrease left ventricular afterload and workload", "IABP inflates during systole to obstruct left ventricular outflow"),
            Triple("Leadership: Delegation Rules for Licensed Practical Nurses (LPN/VN)", "LPN Scope: Administer oral/SC/IM meds, perform sterile dressing changes, urinary catheterization, monitor stable clients; CANNOT perform initial assessment, patient teaching, or IV push high-alert meds", "LPNs perform initial comprehensive health assessments on ICU admissions"),
            Triple("Leadership: Delegation Rules for Unlicensed Assistive Personnel (UAP)", "UAP Scope: Standard ADLs (bathing, feeding, ambulation), vital signs on STABLE clients, I&O measurement; CANNOT perform sterile procedures, medication administration, or triage", "UAPs evaluate breath sounds and titrate dopamine infusions"),
            Triple("Ethics & PNC/Prometric: Beneficence vs Nonmaleficence vs Autonomy", "Autonomy: Client's right to self-determination/refusal; Beneficence: Duty to do good; Nonmaleficence: Duty to DO NO HARM; Justice: Fair distribution of healthcare resources", "Autonomy means the nurse enforces unwanted surgery against client's explicit refusal"),
            Triple("Infection Control: Personal Protective Equipment (PPE) Donning & Doffing Sequence", "DONNING (putting on): Gown -> Mask/Respirator -> Goggles/Face shield -> Gloves; DOFFING (taking off): Gloves -> Goggles/Face shield -> Gown -> Mask/Respirator", "Doffing sequence starts with removing respirator mask first while gloves stay on"),
            Triple("Critical Care: Sepsis Bundle (1-Hour Bundle) Interventions", "1-Hour Sepsis Bundle: Measure lactate level, obtain blood cultures BEFORE antibiotics, administer broad-spectrum IV antibiotics, begin rapid 30 mL/kg crystalloid fluid bolus for hypotension/lactate >= 4, give vasopressors (Norepinephrine) if hypotensive after fluid resuscitation", "Delay blood cultures until 48 hours after starting IV broad-spectrum antibiotics"),
            Triple("Emergency Nursing: Carbon Monoxide Poisoning Carboxyhemoglobin & 100% NRB", "Carbon monoxide binds hemoglobin with 200x affinity of oxygen; pulse oximeter reads FALSELY HIGH (100%); Symptoms: Headache, cherry-red skin (late); Treat: 100% HIGH-FLOW OXYGEN VIA NON-REBREATHER MASK", "Treat carbon monoxide poisoning with room air and oxygen restriction"),
            Triple("Emergency Nursing: Snakebite Envenomation First Aid & Antivenom", "First Aid: Immobilize affected limb AT OR BELOW HEART LEVEL, remove tight jewelry; DO NOT apply tourniquet, DO NOT cut or suck wound, DO NOT apply ice; Administer Crotalidae Polyvalent Antivenom (CroFab)", "Apply ice packs and cut deep incisions over snakebite wound to suck out venom"),
            Triple("Leadership: Prioritization Framework Maslow's vs ABCs vs Stable/Unstable", "Priority rules: Airway -> Breathing -> Circulation -> Disability -> Pain; Unstable clients (acute changes, high risk) over stable chronic clients; Unexpected symptoms over expected symptoms", "Prioritize a chronic stable hypertension check over an acute sudden severe dyspnea client")
        )

        for (i in 0 until 80) {
            val topicIndex = i % ultraTopicsPart4.size
            val item = ultraTopicsPart4[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Abandon emergency trauma protocol and leave disaster scene without triage classification",
                "Delegate critical emergency triage decisions to untrained non-medical volunteers"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Critical Care, Emergency & Leadership",
                "NCLEX-RN / DHA • Ultra Series",
                "Ultra Series Emergency/Leadership Case #${i + 1}: In a high-acuity critical care or emergency trauma scenario involving ${item.first}, which intervention accurately embodies professional nursing standards?",
                options,
                correctPos,
                "Rationale: Critical care leadership and triage protocols for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures patient preservation, strict infection control, and legal safety. Action '${item.third}' is unsafe.",
                "Emergency Ultra • ${item.first}"
            )
        }

        return list
    }
}
