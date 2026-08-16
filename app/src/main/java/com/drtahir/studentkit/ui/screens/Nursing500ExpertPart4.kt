package com.drtahir.studentkit.ui.screens

/**
 * EXPERT BANK PART 4: CRITICAL CARE, EMERGENCY TRAUMA, ETHICS, LEADERSHIP & RESEARCH (80 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ExpertPart4 {

    fun getCriticalCareExpertQuestions(startId: Int): List<NursingExamQuestion> {
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
            Triple("Hemodynamic Monitoring: Arterial Line Phlebostatic Axis & Zeroing", "Phlebostatic axis located at 4TH INTERCOSTAL SPACE, MIDAXILLARY LINE (level of right atrium); transducer zeroed and leveled at this spot every shift and after position changes", "Phlebostatic axis is located at 2nd intercostal space right sternal border"),
            Triple("Hemodynamic Monitoring: Central Venous Pressure (CVP) & Fluid Status", "Normal CVP: 2-8 mmHg; measures right ventricular end-diastolic pressure (preload); CVP < 2 mmHg indicates HYPOVOLEMIA (needs fluids); CVP > 8 mmHg indicates FLUID OVERLOAD", "CVP of 1 mmHg indicates fluid volume overload"),
            Triple("Hemodynamic Monitoring: Pulmonary Artery Catheter (Swan-Ganz) Wedge Pressure", "Normal PAWP (wedge pressure): 6-12 mmHg; measures left ventricular end-diastolic pressure; PAWP > 18 mmHg indicates LEFT HEART FAILURE / PULMONARY EDEMA", "PAWP measures right atrial mean pressure exclusively"),
            Triple("Emergency Trauma: Advanced Trauma Life Support (ATLS) Primary Survey ABCDE", "Airway with C-spine stabilization -> Breathing and ventilation -> Circulation with hemorrhage control -> Disability (neurological) -> Exposure/Environmental control", "Secondary survey is performed before establishing patent airway"),
            Triple("Emergency Trauma: Mass Casualty Triage START System Tags", "RED (Immediate): life-threatening, survivable with quick care (tension pneumo); YELLOW (Delayed): major injuries, can wait 1-2 hrs; GREEN (Minimal): walking wounded; BLACK (Expectant): deceased/non-survivable", "Assign Red tag to deceased clients with no brainstem reflexes"),
            Triple("Shock States: Septic Shock Surviving Sepsis Campaign Bundle", "Measure lactate level, obtain blood cultures BEFORE antibiotics, administer broad-spectrum antibiotics, infuse 30 mL/kg crystalloids for hypotension/lactate >= 4, give vasopressors (Norepinephrine)", "Delay antibiotics for 48 hours while awaiting blood culture results"),
            Triple("Shock States: Cardiogenic Shock Inotropes vs Fluids Restriction", "Pump failure (decreased cardiac output, high PAWP); treat with INOTROPES (Dobutamine) and vasopressors; RESTRICT FLUIDS; Intra-Aortic Balloon Pump (IABP) counterpulsation", "Infuse 5 Liters of Normal Saline rapidly to cardiogenic shock"),
            Triple("Shock States: Neurogenic Shock Bradycardia & Loss of Sympathetic Tone", "Loss of sympathetic tone from spinal cord injury (above T6); TRIAD: HYPOTENSION, BRADYCARDIA, POIKILOTHERMIA; treat with fluids, vasopressors (Atropine/Norepinephrine)", "Neurogenic shock presents with severe tachycardia and warm sweating feet"),
            Triple("Shock States: Anaphylactic Shock Intramuscular Epinephrine Dose", "EPINEPHRINE IM (0.3-0.5 mg 1:1000) IN ANTEROLATERAL THIGH is FIRST-LINE TREATMENT; repeat every 5-15 mins if needed; then IV fluids, antihistamines, steroids", "Administer oral diphenhydramine before giving Epinephrine IM"),
            Triple("Cardiac Arrest: ACLS Pulseless VTach & VFib Defibrillation", "Unsynchronized HIGH-ENERGY DEFIBRILLATION (120-200J biphasic); resume CPR IMMEDIATELY for 2 mins after shock before rhythm check; Epinephrine 1 mg q 3-5 mins; Amiodarone", "Perform synchronized cardioversion at 50 Joules for VFib"),
            Triple("Cardiac Arrest: ACLS PEA & Asystole Non-Shockable Rhythms", "PEA (Pulseless Electrical Activity) and Asystole are NON-SHOCKABLE; HIGH-QUALITY CPR + EPINEPHRINE 1 mg q 3-5 mins; treat 5 Hs and 5 Ts underlying causes", "Deliver 360J unsynchronized shock to Asystole"),
            Triple("Ethics & Professional Practice: Ethical Principles Principles in Nursing", "Autonomy: right to self-determination; Beneficence: doing good; Nonmaleficence: DO NO HARM; Justice: fairness in resource allocation; Veracity: telling the truth", "Beneficence means forcing treatments against client's explicit competent refusal"),
            Triple("Ethics & Professional Practice: Informed Consent Nursing Role", "Physician's responsibility to explain procedure, risks, and alternatives; Nurse's role is to WITNESS THE SIGNATURE, verify client competence, and ensure voluntariness", "Nurse explains all surgical complications and obtains legal consent alone"),
            Triple("Ethics & Professional Practice: Advanced Directives & Durable Power of Attorney", "Living Will specifies desired end-of-life care; Durable Power of Attorney for Healthcare designates a proxy decision-maker if client becomes incapacitated; DNR order requires MD signature", "Living will overrides competent living client's verbal refusal"),
            Triple("Leadership & Delegation: RN vs LPN/VN vs UAP Scope of Practice", "RN: Assessment, Initial Teaching, Nursing Diagnosis, Complex Care, Blood Transfusions, IV Push; LPN: Stable clients, routine meds, Foley insertion, ostomy care; UAP: ADLs, vital signs on stable clients, intake/output", "Delegate initial complex admission assessment to UAP"),
            Triple("Nursing Research & EBP: Hierarchy of Evidence Systemic Reviews", "Level I (Highest): Systematic Reviews and Meta-Analyses of Randomized Controlled Trials (RCTs); Level II: Single RCT; Level VII (Lowest): Expert opinion and clinical reports", "Expert opinion is highest level of clinical evidence")
        )

        for (i in 0 until 80) {
            val topicIndex = i % criticalCareTopics.size
            val item = criticalCareTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Critical Care & Leadership Standard: ${item.second}",
                "Unsafe / Breach of Standards: ${item.third}",
                "Discontinue vital monitoring and abandon client bedside",
                "Delegate critical ACLS resuscitation tasks to unregistered volunteers"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Critical Care, Leadership & Ethics",
                "NCLEX-RN / DHA • Expert Series",
                "Expert Series Critical Care Case #${i + 1}: In an intensive care or clinical leadership scenario involving ${item.first}, which protocol represents evidence-based practice?",
                options,
                correctPos,
                "Rationale: Critical care, emergency trauma, and professional leadership standards for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures critical hemodynamic stability, ethical integrity, and safe delegation. Action '${item.third}' is improper.",
                "Critical Care Expert • ${item.first}"
            )
        }

        return list
    }
}
