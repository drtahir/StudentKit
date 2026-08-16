package com.drtahir.studentkit.ui.screens

/**
 * APEX BANK PART 4: TRAUMA SYSTEMS, ICU HEMODYNAMICS, LEADERSHIP & PNC COMPETENCIES (80 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ApexPart4 {

    fun getCriticalCareApexQuestions(startId: Int): List<NursingExamQuestion> {
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

        val apexTopicsPart4 = listOf(
            Triple("Trauma Nursing: Primary Survey ABCDE Sequence & Spinal Immobilization", "Airway (with cervical spine immobilization), Breathing, Circulation (hemorrhage control), Disability (neuro assessment - GCS), Exposure/Environmental control (prevent hypothermia)", "Start primary survey by dressing minor finger abrasions before checking airway"),
            Triple("Trauma Nursing: Massive Transfusion Protocol (MTP) 1:1:1 Ratio", "Indicated for severe hemorrhagic shock; ADMINISTER PACKED RBCs, FRESH FROZEN PLASMA (FFP), AND PLATELETS IN A 1:1:1 BALANCED RATIO to prevent dilutional coagulopathy", "Infuse 20 units of unbuffered Normal Saline without any blood products"),
            Triple("Trauma Nursing: Tension Pneumothorax Immediate Needle Decompression", "Severe dyspnea, tracheal deviation TO UNAFFECTED SIDE, absent breath sounds on affected side, hypotension, JVD; PRIORITY: IMMEDIATE NEEDLE DECOMPRESSION (2nd ICS midclavicular line)", "Wait 6 hours for chest X-ray confirmation before decompressing tension pneumothorax"),
            Triple("Trauma Nursing: Flail Chest Paradoxical Chest Wall Movement", "Fracture of >= 2 adjacent ribs in >= 2 places; PARADOXICAL CHEST MOVEMENT (chest wall moves INWARD during inspiration and OUTWARD during expiration); positive pressure ventilation", "Flail chest causes chest wall to remain completely rigid and still"),
            Triple("ICU Hemodynamics: Central Venous Pressure (CVP) Transducer Zeroing & Levelling", "Normal CVP: 2-8 mmHg; LEVEL TRANSDUCER TO PHLEBOSTATIC AXIS (4th intercostal space, mid-axillary line); CVP < 2 indicates hypovolemia; CVP > 8 indicates fluid overload / right HF", "Level transducer to client's big toe for accurate CVP reading"),
            Triple("ICU Hemodynamics: Pulmonary Artery Wedge Pressure (PAWP) & Left Ventricular Preload", "Normal PAWP: 6-12 mmHg; reflects LEFT VENTRICULAR END-DIASTOLIC PRESSURE (preload); PAWP > 18 mmHg indicates cardiogenic pulmonary edema; NEVER LEAVE PAWP BALLOON INFLATED > 15 SECS", "Inflate PAWP balloon continuously for 24 hours to monitor pressure"),
            Triple("ICU Hemodynamics: Arterial Line Square Wave Test & Overdamp vs Underdamp", "Perform Square Wave Test (fast flush test); OVERDAMPED WAVEFORM (sluggish rise, missing oscillations) underestimates SBP/overestimates DBP (check air bubbles, blood clots, loose tubing)", "Ignore flatline arterial line trace during active arterial bleeding"),
            Triple("ICU Hemodynamics: ScvO2 / SvO2 Tissue Oxygen Delivery & Extraction Ratio", "Normal SvO2 (mixed venous O2 saturation from PA catheter): 60-80%; ScvO2 (central venous O2 saturation): 65-85%; low SvO2 (< 60%) indicates decreased cardiac output or high tissue O2 demand", "SvO2 of 10% indicates optimal tissue oxygenation"),
            Triple("Nursing Leadership: Disaster Triage START Algorithm & Tag Colors", "Black (Deceased/Expectant - no breathing after airway opening); Red (Immediate - life-threatening, recoverable); Yellow (Delayed - serious, stable); Green (Minimal - walking wounded)", "Assign Red tag to deceased client with open cranial vault"),
            Triple("Nursing Leadership: Incident Command System (ICS) Roles & Public Information Officer", "Standardized management structure for emergency response; Incident Commander has overall responsibility; Public Information Officer handles media relations; Safety Officer monitors hazards", "Every bedside nurse gives independent press interviews during disaster"),
            Triple("Nursing Leadership: Professional Negligence Elements & Duty of Care", "4 legal elements required to prove malpractice: 1. Duty owed to client, 2. Breach of duty (failure to meet standard), 3. Foreseeability of harm, 4. Causation and direct damages/injury", "Malpractice is proven if client dislikes hospital food flavor"),
            Triple("Pakistan Nursing Council (PNC): PNC Code of Conduct & Patient Advocacy", "PNC mandates patient advocacy, confidentiality, non-discrimination, continuing professional development (CPD), and reporting unsafe practice; upholds professional integrity", "PNC permits sharing private patient medical charts on public social media"),
            Triple("Pakistan Nursing Council (PNC): Prescriptive Authority & Medication Safety", "RNs in Pakistan administer medications based on valid licensed medical practitioner orders; high-alert drugs require double-check verification; report medication errors immediately", "RNs alter insulin doses independently without doctor orders or protocols"),
            Triple("PNC Competencies: Infection Control Standards & Hospital Acquired Infections", "Adhere to PNC National Guidelines for Infection Control; standard precautions for all clients; compliance with hand hygiene, sharp disposal, and central line bundle to reduce CLABSI", "Recap used needles with both hands toward fingers"),
            Triple("PNC Competencies: Nursing Research & Evidence-Based Clinical Practice", "Incorporate PNC research standards into bedside practice; utilize evidence-based guidelines to reduce hospital stay, decrease complications, and improve patient outcomes", "Reject evidence-based research and rely purely on outdated myths"),
            Triple("PNC Competencies: Maternal & Child Health PNC Safe Delivery Protocols", "Ensure safe delivery practices, active management of third stage of labor (AMTSL), administration of Oxytocin, newborn resuscitation, and immediate skin-to-skin care", "Delay newborn resuscitation for 3 hours after birth")
        )

        for (i in 0 until 80) {
            val topicIndex = i % apexTopicsPart4.size
            val item = apexTopicsPart4[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Apex Critical Care Standard: ${item.second}",
                "Unsafe / Breach of Practice: ${item.third}",
                "Abandon vital monitoring and leave client unattended",
                "Delegate critical ACLS resuscitation tasks to untrained volunteers"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Critical Care, Leadership & PNC Rules",
                "NCLEX-RN / DHA • Apex Series",
                "Apex Series Critical Care Case #${i + 1}: In a trauma resuscitation, ICU hemodynamic monitoring, or PNC professional leadership scenario involving ${item.first}, which protocol represents evidence-based best practice?",
                options,
                correctPos,
                "Rationale: Critical care, trauma, and PNC professional leadership apex standards for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures hemodynamic stability, trauma survival, ethical compliance, and safe delegation. Action '${item.third}' is improper.",
                "Critical Care Apex • ${item.first}"
            )
        }

        return list
    }
}
