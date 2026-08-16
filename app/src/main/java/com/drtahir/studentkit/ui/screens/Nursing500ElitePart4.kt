package com.drtahir.studentkit.ui.screens

/**
 * ELITE BANK PART 4: CRITICAL CARE, DISASTER EMERGENCY, ETHICS & LEADERSHIP (80 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ElitePart4 {

    fun getCriticalCareEliteQuestions(startId: Int): List<NursingExamQuestion> {
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

        val criticalCareTopics = listOf(
            Triple("Critical Care: Intracranial Pressure (ICP) Positioning & MAP Calculation", "Normal ICP: 5-15 mmHg; Mean Arterial Pressure (MAP) = [SBP + 2(DBP)] / 3; Cerebral Perfusion Pressure (CPP) = MAP - ICP (keep CPP 60-80 mmHg); elevate HOB 30 degrees, head in neutral midline", "Place head in extreme rotation with HOB flat supine"),
            Triple("Critical Care: Mechanical Ventilation Alarm Troubleshooting (High vs Low Pressure)", "HIGH PRESSURE ALARM: secretions/plugging (suction), biting tube, kinking, pneumothorax, bronchospasm; LOW PRESSURE ALARM: disconnection, cuff leak, tubing leak; if unresolvable, BAG-VALVE VENTILATE MANUALLY", "Ignore low pressure alarm and disconnect oxygen tank"),
            Triple("Critical Care: Arterial Blood Gas (ABG) Interpretation Rules (ROME)", "Respiratory Opposite (pH up, PaCO2 down = Alkalosis; pH down, PaCO2 up = Acidosis); Metabolic Equal (pH up, HCO3 up = Alkalosis; pH down, HCO3 down = Acidosis); check compensation status", "ABG with pH 7.15 and PaCO2 80 mmHg represents metabolic alkalosis"),
            Triple("Critical Care: Intra-Aortic Balloon Pump (IABP) Counterpulsation Timing", "Inflates during DIASTOLE (at dicrotic notch) to increase coronary artery perfusion; deflates immediately prior to SYSTOLE to decrease left ventricular afterload", "Inflates during peak systole to increase cardiac afterload"),
            Triple("Critical Care: Continuous Renal Replacement Therapy (CRRT) Dialysate Leakage", "Used in hemodynamically unstable AKI/sepsis clients; monitor filter for clotting (high transmembrane pressure), ultrafiltrate rate, serum electrolytes, fluid balance", "CRRT is performed at 500 mL/min rapid blood pump in stable outpatients"),
            Triple("Emergency Trauma: Thermal Burn Airway Assessment & Inhalation Injury", "Signs of carbon monoxide / smoke inhalation: facial burns, singed nasal hairs, hoarseness, carbonaceous sputum, stridor; IMMEDIATE ENDOTRACHEAL INTUBATION before airway edema closes glottis", "Delay intubation until complete respiratory arrest occurs"),
            Triple("Disaster Nursing: Chemical Decontamination & Personal Protective Equipment (PPE)", "Decontaminate clients OUTSIDE THE FACILITY before entry; remove contaminated clothing (removes 80-90% of contaminant), wash skin with copious water/soap; Level A PPE for responders", "Bring unwashed chemically contaminated victims directly into ICU"),
            Triple("Disaster Nursing: Biological Agents Anthrax & Smallpox Infection Control", "Anthrax (Bacillus anthracis): standard precautions, Ciprofloxacin/Doxycycline treatment; Smallpox (Variola virus): AIRBORNE AND CONTACT PRECAUTIONS, papular to vesicular rash", "Smallpox clients require zero isolation or precautions"),
            Triple("Ethics & Legal: Negligence vs Malpractice Requirements", "4 elements of malpractice: Duty of care, Breach of duty, Causation (proximate cause), and Injury/Damages; failure to meet standard of care resulting in client harm", "Malpractice requires zero injury or damage to the client"),
            Triple("Ethics & Legal: Mandatory Reporting Child/Elder Abuse & Communicable Diseases", "Nurses are MANDATORY REPORTERS for suspected child abuse, elder abuse, and reportable communicable diseases (TB, Syphilis, Measles); report to appropriate state agency without delay", "Refrain from reporting abuse to protect the abuser's privacy"),
            Triple("Ethics & Legal: Good Samaritan Laws Standard of Care", "Protects nurses rendering emergency assistance outside workplace; MUST ACT WITHIN SCOPE OF PRACTICE and without gross negligence; cannot accept financial reward", "Good Samaritan laws cover performing open heart surgery on a sidewalk"),
            Triple("Leadership: Delegation 5 Rights of Delegation", "Right Task, Right Circumstance, Right Person, Right Direction/Communication, Right Supervision/Evaluation; RN retains ultimate accountability for delegation outcomes", "RN delegates initial clinical assessment to hospital security guards"),
            Triple("Leadership: Quality Improvement (QI) Root Cause Analysis (RCA)", "RCA performed after sentinel event (unexpected death or severe injury); structured retrospective method to identify underlying systemic processes and prevention", "RCA focuses on placing personal blame on bedside nurse"),
            Triple("Leadership: Change Theory Lewin's Unfreezing, Changing, Refreezing", "Lewin 3-stage change model: Unfreezing (creating awareness/readiness for change), Changing/Moving (implementing new processes), Refreezing (establishing new habits as standard)", "Refreezing occurs prior to unfreezing in change management"),
            Triple("Evidence-Based Practice: PICOT Question Format", "PICOT: Patient/Population, Intervention, Comparison, Outcome, Timeframe; structured clinical search question for locating best evidence", "PICOT format stands for Physician Prescription In Case Of Treatment"),
            Triple("Nursing Research: Quantitative vs Qualitative Research Designs", "Quantitative: numerical data, statistical analysis, hypothesis testing (RCT, cohort, case-control); Qualitative: narrative data, lived experiences, themes (phenomenology, grounded theory)", "Qualitative research uses randomized controlled statistical trials")
        )

        for (i in 0 until 80) {
            val topicIndex = i % criticalCareTopics.size
            val item = criticalCareTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Elite Critical Care Standard: ${item.second}",
                "Unsafe / Breach of Practice: ${item.third}",
                "Abandon vital monitoring and leave client unattended",
                "Delegate critical ACLS resuscitation tasks to untrained volunteers"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Critical Care, Leadership & Ethics",
                "NCLEX-RN / DHA • Elite Series",
                "Elite Series Critical Care Case #${i + 1}: In an intensive care, emergency trauma, or nursing leadership scenario involving ${item.first}, which protocol represents evidence-based best practice?",
                options,
                correctPos,
                "Rationale: Critical care, emergency trauma, and professional leadership elite standards for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures critical hemodynamic stability, ethical compliance, and safe delegation. Action '${item.third}' is improper.",
                "Critical Care Elite • ${item.first}"
            )
        }

        return list
    }
}
