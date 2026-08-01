package com.example.ui.screens

/**
 * ADVANCED BANK PART 4: CRITICAL CARE & EMERGENCY NURSING (40 MCQs) + COMMUNITY HEALTH, INFECTION CONTROL, ETHICS & LEADERSHIP (40 MCQs)
 * NCLEX-RN, DHA, HAAD, Prometric, PNC & International Competitive Exam Standard.
 */
object Nursing500AdvancedPart4 {

    fun getCriticalCareAndLeadershipAdvancedQuestions(startId: Int): List<NursingExamQuestion> {
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
        val criticalTopics = listOf(
            Triple("Hemodynamic Monitoring Pulmonary Artery Wedge Pressure (PAWP)", "Normal PAWP is 6-12 mmHg; elevated PAWP (> 18 mmHg) indicates left ventricular failure / hypervolemia; low PAWP indicates hypovolemia", "Normal PAWP is 40-50 mmHg"),
            Triple("Hemodynamic Monitoring Systemic Vascular Resistance (SVR)", "Normal SVR is 800-1200 dynes/sec/cm^-5; SVR is decreased in septic, neurogenic, and anaphylactic shock (vasodilation); elevated in cardiogenic shock", "SVR is 0 in normal healthy adults"),
            Triple("Mechanical Ventilation Peak Inspiratory Pressure (PIP) Elevation", "High PIP indicates increased airway resistance (secretions, bronchospasm, kinking) or decreased lung compliance (ARDS, pneumothorax)", "High PIP indicates ventilator disconnection"),
            Triple("Mechanical Ventilation Auto-PEEP (Breath Stacking)", "Incomplete exhalation before next breath; common in asthma/COPD; increases intrathoracic pressure and risk of barotrauma; increase expiratory time (I:E ratio)", "Auto-PEEP is cured by increasing respiratory rate to 40 breaths/min"),
            Triple("Vasoactive Drips Norepinephrine (Levophed) Extravasation", "Norepinephrine extravasation causes severe alpha-1 ischemic skin necrosis; treat with immediate subcutaneous infiltration of PHENTOLAMINE (Regitine)", "Apply ice pack directly and elevate arm for 10 days"),
            Triple("Vasoactive Drips Epinephrine IV Infusion", "Inotropic and vasopressor agent used in severe anaphylactic shock and cardiac arrest; increases HR, SVR, and bronchodilation; monitor for severe arrhythmias", "Decreases blood pressure to zero"),
            Triple("Vasoactive Drips Dobutamine Inotropic Therapy", "Beta-1 agonist; increases myocardial contractility and cardiac output without significantly increasing HR; primary agent for cardiogenic shock", "Causes severe bronchospasm and reflex bradycardia"),
            Triple("Trauma Massive Blood Transfusion Protocol (MTP)", "Transfuse 1:1:1 ratio of Packed Red Blood Cells (PRBCs), Fresh Frozen Plasma (FFP), and Platelets to prevent trauma-induced coagulopathy", "Transfuse 20 units of pure saline without RBCs"),
            Triple("Trauma Lethal Triad (Hypothermia, Acidosis, Coagulopathy)", "Vicious cycle in massive trauma; hypothermia impairs clotting, acidosis inhibits coagulation factors, causing uncontrolled hemorrhage; warm client and fluids", "Hypertension, Hyperthermia, and Hypercoagulopathy"),
            Triple("Acute Organ Phosphate Poisoning Antidotes", "SLUDGEM signs (Salivation, Lacrimation, Urination, Defecation, GI distress, Emesis, Miosis); treat with ATROPINE (blocks muscarinic) and PRALIDOXIME (2-PAM)", "Treat organophosphate poisoning with beta-blockers"),
            Triple("Cyanide Poisoning Hydroxocobalamin (Cyanokit)", "Administer IV HYDROXOCOBALAMIN (binds cyanide to form non-toxic cyanocobalamin / Vit B12 excreted in urine) or Sodium Thiosulfate", "Administer high-dose IV insulin"),
            Triple("Subarachnoid Hemorrhage Nimodipine Therapy", "Nimodipine (calcium channel blocker) administered orally to prevent CEREBRAL VASOSPASM following subarachnoid hemorrhage; monitor BP", "Give Nimodipine IV push to lower heart rate to 20 bpm"),
            Triple("Intra-Aortic Balloon Pump (IABP) Limb Ischemia Monitoring", "Monitor peripheral pulses (dorsalis pedis, posterior tibial), skin color, and temperature in leg with IABP sheath every hour", "Amputate leg pre-emptively prior to insertion"),
            Triple("Continuous Renal Replacement Therapy (CRRT) Dialysate Care", "Used for hemodynamically unstable critically ill AKI clients; slow continuous fluid and solute removal over 24 hours; monitor ultrafiltration rate", "Rapid 1-hour dialysis at 5 Liters per minute"),
            Triple("Intracranial Pressure (ICP) Waves A Waves (Lundberg A)", "Steep plateau waves lasting 5-20 minutes with ICP spikes > 50 mmHg; indicates severe cerebral ischemia and impending brain herniation", "Normal benign ICP fluctuation during sleep"),
            Triple("Status Epilepticus First-Line Emergency Drug", "IV Lorazepam or Midazolam first-line to stop active seizure, followed by loading dose of IV Fosphenytoin / Levetiracetam", "Oral aspirin with lukewarm water"),
            Triple("Targeted Temperature Management (TTM) Rewarming Rate", "Rewarm post-cardiac arrest patient slowly at 0.25 to 0.5 °C per hour to prevent rapid electrolyte shifts (hyperkalemia) and cerebral edema", "Rewarm patient rapidly by 5 °C per minute"),
            Triple("Open Pneumothorax (Sucking Chest Wound) Emergency Dressing", "Apply 3-sided occlusive dressing (flutter valve effect: permits air to escape during expiration, prevents entry during inspiration)", "Apply 4-sided airtight seal with heavy tape"),
            Triple("Flail Chest Paradoxical Respiration", "Chest wall moves INWARD during inspiration and OUTWARD during expiration; mechanical ventilation with PEEP required if severe respiratory distress", "Chest wall expands outward on expiration and inward on inspiration"),
            Triple("Acute Respiratory Distress Syndrome (ARDS) Berlin Definition", "Acute onset within 1 week, bilateral pulmonary infiltrates on chest X-ray, PaO2/FiO2 ratio < 300 with PEEP >= 5 cmH2O, non-cardiogenic edema", "Cardiogenic edema with ejection fraction of 75%")
        )

        for (i in 0 until 40) {
            val topicIndex = i % criticalTopics.size
            val item = criticalTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Critical Care Protocol: ${item.second}",
                "Dangerous Emergency Action: ${item.third}",
                "Delay resuscitation and disconnect monitors",
                "Omit arterial line zeroing and calibration"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Critical Care and Emergency Nursing",
                "NCLEX-RN / DHA • Advanced",
                "Advanced Critical Care Case #${i + 1}: In managing a critically ill client presenting with ${item.first}, which evidence-based protocol is required?",
                options,
                correctPos,
                "Rationale: Critical care standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice stabilizes hemodynamics, preserves tissue oxygenation, and prevents fatal complications. Action '${item.third}' is unsafe.",
                "Critical Care Advanced • ${item.first}"
            )
        }

        // =========================================================================
        // COMMUNITY HEALTH, INFECTION CONTROL, ETHICS & LEADERSHIP (40 QUESTIONS)
        // =========================================================================
        val leadershipTopics = listOf(
            Triple("Disaster Triage START JumpSTART Pediatric Modifications", "5 rescue breaths given if pediatric victim is apneic with a pulse before assigning Black tag; respiratory rate 15-45 is Green/Yellow", "Assign Black tag immediately without checking pulse"),
            Triple("Disaster Response Incident Command System (ICS)", "Standardized management system; Incident Commander has overall responsibility; clear chain of command and unified communication", "Chaos without central leadership or roles"),
            Triple("Infection Control Central Line Bundle (CLABSI Prevention)", "Hand hygiene, maximal sterile barrier precautions, Chlorhexidine skin antisepsis, optimal catheter site selection (avoid femoral), daily review of line necessity", "Change central line dressing with bare unwashed hands"),
            Triple("Catheter-Associated Urinary Tract Infection (CAUTI) Bundle", "Insert catheter using sterile technique, maintain closed drainage system, keep bag below bladder level, avoid kinked tubing, REMOVE CATHETER ASAP", "Keep urine bag placed on top of chest"),
            Triple("Surgical Site Infection (SSI) Prevention Bundle", "Administer prophylactic IV antibiotic within 60 minutes BEFORE surgical incision, hair clipping (NO shaving with razor), maintain normothermia and glycemic control", "Shave surgical site with dull razor 24 hours prior"),
            Triple("Ventilator-Associated Pneumonia (VAP) Bundle", "Elevate head of bed 30-45 degrees, daily sedation vacation & readiness to extubate, oral care with Chlorhexidine, peptic ulcer & DVT prophylaxis", "Place client flat prone with zero oral care"),
            Triple("Informed Consent Emergency Exception Rule", "In life-threatening emergencies where client is unconscious and no surrogate is available, consent is IMPLIED legally to preserve life", "Wait 48 hours for court order while client bleeds out"),
            Triple("Advanced Directives Living Will vs Durable Power of Attorney", "Living will: written instructions regarding end-of-life care; Durable Power of Attorney for Healthcare: designates surrogate decision-maker when client loses capacity", "Durable Power of Attorney overrides competent client's living voice"),
            Triple("Do Not Resuscitate (DNR) Order Standard", "DNR order applies specifically to CPR and endotracheal intubation in cardiac/respiratory arrest; client STILL RECEIVES all other active medical care and pain management", "DNR order means withholding all food, fluids, and nursing care"),
            Triple("Good Samaritan Law Protections", "Protects healthcare professionals providing voluntary emergency care at accident scenes outside employment, provided care is given without gross negligence", "Requires payment before assisting car crash victim"),
            Triple("Nursing Delegation Scope UAP vs LPN vs RN", "RN performs initial assessment, complex teaching, clinical evaluation; LPN administers non-IV meds, sterile dressing changes on STABLE clients; UAP performs ADLs, vital signs", "Delegate initial complex ICU assessment to UAP"),
            Triple("Nursing Prioritization Time Management Matrix", "Prioritize urgent and important tasks (airway compromise, severe pain, unexpected clinical change) over non-urgent routine tasks", "Complete non-urgent paperwork before assisting suffocating client"),
            Triple("Quality Improvement PDSA Cycle (Plan-Do-Study-Act)", "Continuous process improvement methodology: Plan change, Do test run, Study results, Act on findings to implement standard practice", "Plan, Dismiss, Suppress, Abandon"),
            Triple("Evidence-Based Practice (EBP) Hierarchy of Evidence", "Level 1 (Highest): Systematic reviews & meta-analyses of Randomized Controlled Trials (RCTs); Level 7 (Lowest): Expert opinion / anecdotal reports", "Expert opinion is highest level of evidence above meta-analyses"),
            Triple("Research Ethics Institutional Review Board (IRB) Role", "Protects rights, safety, and welfare of human research subjects; ensures voluntary informed consent and risk-benefit balance", "Promotes financial profit for researchers regardless of subject harm"),
            Triple("Research Ethics Vulnerable Populations Protection", "Vulnerable groups (children, pregnant women, prisoners, cognitively impaired) require extra safeguards against coercion or undue influence", "Force prisoners to participate without consent"),
            Triple("Epidemiology Outbreak Attack Rate Calculation", "Attack Rate = (Number of new cases among exposed population / Total exposed population) x 100", "Attack Rate = Total population / Number of doctors"),
            Triple("Community Health Hazard Assessment Environmental Health", "Assess air quality, water supply, lead paint exposure, waste disposal, occupational hazards in community population", "Ignore environmental factors and focus only on genetics"),
            Triple("Occupational Safety Sharps Injury Prevention", "NEVER RECAP NEEDLES manually (use one-handed scoop technique if mandatory); dispose of used needles immediately in rigid puncture-resistant Biohazard container", "Recap needles with both hands and toss in open paper bin"),
            Triple("Interprofessional Collaboration Handoff Shift Report", "Standardized handoff (SBAR / IPASS) ensuring accurate continuity of care, safety risks, pending orders, and client goals at shift change", "Inform oncoming nurse that everything is fine without details")
        )

        for (i in 0 until 40) {
            val topicIndex = i % leadershipTopics.size
            val item = leadershipTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Leadership / Ethics Standard: ${item.second}",
                "Violation / Non-Compliant Action: ${item.third}",
                "Ignore policy and conceal clinical errors",
                "Falsify medical documentation"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Community Health Nursing, Infection Control, Ethics and Leadership",
                "NCLEX-RN / DHA • Advanced",
                "Advanced Leadership & Ethics Case #${i + 1}: In executing professional nursing practice involving ${item.first}, which clinical governance decision is correct?",
                options,
                correctPos,
                "Rationale: Community health, infection control, ethics, and leadership standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice upholds legal standards, infection bundles, nursing ethics, and client safety. Action '${item.third}' is unsafe.",
                "Leadership Advanced • ${item.first}"
            )
        }

        return list
    }
}
