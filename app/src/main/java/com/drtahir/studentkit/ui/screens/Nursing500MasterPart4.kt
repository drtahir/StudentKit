package com.drtahir.studentkit.ui.screens

/**
 * MASTER BANK PART 4: CRITICAL CARE & EMERGENCY NURSING (40 MCQs) + COMMUNITY HEALTH, INFECTION CONTROL, ETHICS & LEADERSHIP (40 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500MasterPart4 {

    fun getCriticalCareAndLeadershipMasterQuestions(startId: Int): List<NursingExamQuestion> {
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

        // =========================================================================
        // CRITICAL CARE & EMERGENCY NURSING (40 QUESTIONS)
        // =========================================================================
        val criticalMasterTopics = listOf(
            Triple("Arterial Blood Gas (ABG) Compensation Interpretation", "Uncompensated: pH abnormal, one parameter abnormal; Partially compensated: pH abnormal, BOTH PaCO2 and HCO3 abnormal in same direction; Fully compensated: pH NORMAL", "Uncompensated ABG has a completely normal pH and normal PaCO2"),
            Triple("Mechanical Ventilation High-Pressure Alarm Causes", "Triggered by INCREASED RESISTANCE: secretions in airway (suction), biting tube, kinked tubing, bronchospasm, pneumothorax, client fighting ventilator", "Caused by cuff leak or tubing disconnection"),
            Triple("Mechanical Ventilation Low-Pressure Alarm Causes", "Triggered by LOSS OF PRESSURE / DISCONNECTION: tubing disconnected, ET tube cuff leak, extubation, loose connections", "Caused by severe airway mucous plug"),
            Triple("Rapid Sequence Intubation (RSI) Pre-Oxygenation & Meds", "Administer 100% O2 for 3-5 mins, give induction agent (Etomidate/Propofol) followed immediately by neuromuscular blocker (Succinylcholine/Rocuronium)", "Paralyze client with Rocuronium 20 minutes before induction"),
            Triple("Intracranial Pressure (ICP) Cushing's Triad Warning", "LATE sign of increased ICP / impending brain herniation: BRADYCARDIA, SYSTOLIC HYPERTENSION WITH WIDENED PULSE PRESSURE, IRREGULAR RESPIRATIONS (Cheyne-Stokes)", "Tachycardia with hypotension and narrow pulse pressure"),
            Triple("Increased ICP Nursing Interventions & Positioning", "Elevate HOB 30 degrees, maintain head/neck in NEUTRAL ALIGNMENT, avoid hip flexion, hyperventilation (PaCO2 30-35 mmHg), avoid coughing/straining", "Place client in Trendelenburg position with neck flexed 90 degrees"),
            Triple("Central Venous Pressure (CVP) Waveform & Normal Range", "Normal CVP = 2-6 mmHg; measures right atrial pressure / fluid volume status; CVP < 2 indicates HYPOV OLEMIA; CVP > 6 indicates HYPERVOLEMIA", "Normal CVP is 25-30 mmHg"),
            Triple("Pulmonary Artery Catheter (Swan-Ganz) Wedge Pressure (PAWP)", "Normal PAWP = 6-12 mmHg; reflects LEFT VENTRICULAR END-DIASTOLIC PRESSURE; elevated PAWP (> 18 mmHg) indicates left ventricular failure / pulmonary edema", "PAWP of 2 mmHg indicates severe left heart failure"),
            Triple("Sepsis & Septic Shock 1-Hour Bundle Protocol", "Measure lactate level, obtain blood cultures BEFORE antibiotics, administer broad-spectrum antibiotics, give 30 mL/kg crystalloid for hypotension/lactate >= 4", "Delay antibiotics by 12 hours while waiting for wound culture"),
            Triple("Anaphylactic Shock First-Line Treatment", "Airway edema, bronchospasm, severe hypotension; FIRST-LINE TREATMENT IS INTRAMUSCULAR EPINEPHRINE (1:1000) into mid-outer thigh (repeat q 5-15 mins)", "Give oral antihistamine tablet and wait 4 hours"),
            Triple("Cardiogenic Shock Vasopressor & Inotrope Support", "Pump failure resulting in tissue hypoperfusion; treat with Inotropes (Dobutamine / Milrinone) to increase contractility and Vasopressors (Norepinephrine)", "Give 5 Liters Normal Saline bolus rapidly to fluid overloaded client"),
            Triple("Neurogenic Shock Clinical Features & Atropine Use", "Loss of sympathetic tone from T6 or higher spinal trauma; TRIAD: HYPOTENSION, BRADYCARDIA, POIKILOTHERMIA; treat hypotension with vasopressors, bradycardia with ATROPINE", "Causes severe tachycardia and hypertension"),
            Triple("Disseminated Intravascular Coagulation (DIC) Pathophysiology", "Widespread microvascular thrombosis depletes clotting factors and platelets, causing SIMULTANEOUS SEVERE BLEEDING; elevated D-dimer, low fibrinogen, prolonged PT/aPTT", "Causes super elevated platelets and zero bleeding risk"),
            Triple("Massive Blood Transfusion Lethal Triad (Hypothermia, Acidosis, Coagulopathy)", "Transfusing >= 10 units PRBCs in 24 hours causes Hypothermia, Dilutional Coagulopathy, Hypocalcemia (citrate toxicity), and Hyperkalemia; use blood warmer", "Massive transfusion causes severe hypercalcemia"),
            Triple("Tension Pneumothorax Needle Decompression Site", "Air trapped in pleural space under pressure causing mediastinal shift and obstructive shock; IMMEDIATE NEEDLE DECOMPRESSION at 2nd intercostal space midclavicular line", "Wait 6 hours for elective outpatient chest x-ray")
        )

        for (i in 0 until 40) {
            val topicIndex = i % criticalMasterTopics.size
            val item = criticalMasterTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Critical Care Protocol: ${item.second}",
                "Dangerous / Inappropriate Action: ${item.third}",
                "Delay critical resuscitation",
                "Omit airway evaluation"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Critical Care & Emergency Nursing",
                "NCLEX-RN / DHA • Master Series",
                "Master Series Critical Care Case #${i + 1}: In managing an unstable ICU/ED client presenting with ${item.first}, which life-saving nursing decision is required?",
                options,
                correctPos,
                "Rationale: Critical care and emergency nursing standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct answer maintains hemodynamic stability, tissue perfusion, and airway preservation. Action '${item.third}' is unsafe.",
                "Critical Care Master • ${item.first}"
            )
        }

        // =========================================================================
        // COMMUNITY HEALTH, INFECTION CONTROL, ETHICS & LEADERSHIP (40 QUESTIONS)
        // =========================================================================
        val leadershipMasterTopics = listOf(
            Triple("Levels of Prevention (Primary vs Secondary vs Tertiary)", "Primary: health promotion & immunization (prevent disease); Secondary: screening & early diagnosis (Pap smear, mammogram, BP screen); Tertiary: rehabilitation & disability limitation", "Primary prevention includes cardiac rehabilitation after MI"),
            Triple("Infection Control Precautions Transmission-Based Categories", "Airborne: N95, negative pressure (TB, Measles, Varicella); Droplet: surgical mask (Flu, Pertussis, Meningococcal); Contact: gown & gloves (C. diff, MRSA, VRE)", "Contact precautions require negative pressure isolation room"),
            Triple("Clostridium Difficile Hand Hygiene & Bleach Disinfection", "Spore-forming bacterium; MUST WASH HANDS WITH SOAP AND WATER (alcohol sanitizers do NOT kill spores); clean surfaces with BLEACH (sodium hypochlorite)", "Clean C. diff room with alcohol gel only"),
            Triple("Ethical Principles (Autonomy, Beneficence, Nonmaleficence, Justice)", "Autonomy: respecting client's self-determination; Beneficence: acting for client's good; Nonmaleficence: doing no harm; Justice: fair distribution of healthcare resources", "Autonomy means forcing treatment on a competent client"),
            Triple("Informed Consent Nursing Role & Verification", "Physician explains procedure/risks; NURSE WITNESSES CLIENT SIGNATURE, verifies competency, and confirms client voluntarily agrees; if client confused, contact provider", "Nurse explains surgical risks and obtains consent for brain surgery"),
            Triple("Delegation Principles Five Rights of Delegation", "Right Task, Right Circumstance, Right Person, Right Direction/Communication, Right Supervision/Evaluation; RN CANNOT DELEGATE Assessment, Teaching, Evaluation, or Nursing Judgement (EAT)", "RN delegates initial admission assessment to nursing assistant"),
            Triple("Unlicensed Assistive Personnel (UAP) Scope of Practice", "UAP can perform standard ADLs, vital signs on STABLE clients, ambulation, feeding (non-dysphagic), intake/output measurement; CANNOT administer medications or perform sterile care", "UAP administers IV push cardiac medications"),
            Triple("Licensed Practical / Vocational Nurse (LPN/LVN) Scope of Practice", "LPN can administer oral/subcut medications, perform sterile dressing changes, tracheostomy care, insert Foley catheters, gather data on STABLE clients; CANNOT give IV push meds or plan care", "LPN completes initial admission assessment on unstable client"),
            Triple("Triage Disaster Management System (START Color Tags)", "RED (Immediate): life-threatening but salvageable (airway obstruction, tension pneumothorax); YELLOW (Delayed): serious injuries (femur fracture); GREEN (Minimal): walking wounded; BLACK (Expectant): dead/palliative", "Black tag receives priority emergency surgery"),
            Triple("Incident / Variance Reporting Nursing Protocols", "Completed for unexpected events (falls, med errors, equipment failure); fill out within 24 hours; DO NOT MENTION INCIDENT REPORT IN CLIENT'S MEDICAL RECORD", "Document 'Incident report filed in chart' inside client progress note")
        )

        for (i in 0 until 40) {
            val topicIndex = i % leadershipMasterTopics.size
            val item = leadershipMasterTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Leadership / Ethics Standard: ${item.second}",
                "Inappropriate / Non-Compliant Action: ${item.third}",
                "Violate patient confidentiality laws",
                "Delegate assessment duties inappropriately"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Community Health & Nursing Leadership",
                "NCLEX-RN / DHA • Master Series",
                "Master Series Leadership & Ethics Case #${i + 1}: In resolving a complex nursing situation involving ${item.first}, which professional standards guidelines apply?",
                options,
                correctPos,
                "Rationale: Nursing leadership, ethics, and community health guidelines for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice adheres to scope of practice, legal obligations, and infection control standards. Action '${item.third}' is improper.",
                "Leadership Master • ${item.first}"
            )
        }

        return list
    }
}
