package com.drtahir.studentkit.ui.screens

/**
 * PART 4: CRITICAL CARE & EMERGENCY NURSING (40 MCQs) + COMMUNITY HEALTH, INFECTION CONTROL, ETHICS & LEADERSHIP (40 MCQs)
 * NCLEX-RN, DHA, HAAD, Prometric, PNC & International Competitive Exam Standard.
 */
object Nursing500BankPart4 {

    fun getCriticalCareAndLeadershipQuestions(startId: Int): List<NursingExamQuestion> {
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
        val criticalScenarios = listOf(
            Triple("ACLS Cardiac Arrest Shockable Rhythms", "Ventricular Fibrillation (V-Fib) and Pulseless Ventricular Tachycardia (V-Tach); IMMEDIATE unsynchronized high-energy shock (Defibrillation)", "Asystole and Pulseless Electrical Activity require immediate shock"),
            Triple("ACLS Cardiac Arrest Non-Shockable Rhythms", "Asystole and Pulseless Electrical Activity (PEA); High-quality CPR and Epinephrine 1 mg IV every 3-5 minutes; DO NOT SHOCK", "Immediate high-energy shock for asystole"),
            Triple("Cardiopulmonary Resuscitation (CPR) Adult Standards", "Compressions 100-120/min, depth 2-2.4 inches (5-6 cm), full chest recoil, compression-to-ventilation ratio 30:2", "Compressions 40/min, depth 0.5 inches, ratio 15:1"),
            Triple("Synchronized Cardioversion Rhythms", "Unstable Ventricular Tachycardia with pulse, Unstable Atrial Fibrillation / Flutter, SVT; shock synchronized with R wave", "Synchronized shock on T wave during V-Fib"),
            Triple("Septic Shock Sepsis 1-Hour Bundle", "Measure lactate, obtain blood cultures BEFORE starting antibiotics, administer broad-spectrum IV antibiotics, infuse 30 mL/kg crystalloid for hypotension", "Delay blood cultures until day 3 of stay"),
            Triple("Anaphylactic Shock First-Line Emergency Drug", "IM Epinephrine 1:1000 into outer mid-thigh immediately; repeat every 5-15 minutes if needed; IV fluids, oxygen, diphenhydramine", "Oral acetaminophen with warm tea"),
            Triple("Hypovolemic Shock Clinical Triad & Resuscitation", "Hypotension, Tachycardia, Oliguria; rapid IV crystalloid resuscitation (Normal Saline / Lactated Ringer's) via two large-bore IVs (18G)", "Fluid restriction with beta-blockers"),
            Triple("Neurogenic Shock Clinical Triad", "Hypotension, Bradycardia, and Poikilothermia due to loss of sympathetic vascular tone following spinal cord injury above T6", "Hypertension, Tachycardia, and severe fever"),
            Triple("Cardiogenic Shock Interventions", "Inotropic support (Dobutamine, Dopamine), cautious fluid management, vasopressors (Norepinephrine), intra-aortic balloon pump (IABP)", "Infuse 5 Liters of Normal Saline rapidly"),
            Triple("Central Venous Pressure (CVP) Reference & Interpretation", "Normal CVP 2-6 mmHg (3-8 cmH2O); CVP < 2 indicates hypovolemia/dehydration; CVP > 6 indicates hypervolemia/heart failure", "Normal CVP is 20-30 mmHg"),
            Triple("Arterial Line Phlebostatic Axis Zeroing", "Zero and calibrate transducer at 4th intercostal space, mid-axillary line (level of right atrium)", "Zero transducer at top of intravenous IV pole"),
            Triple("Endotracheal Tube Cuff Pressure Standard", "Maintain cuff pressure between 20 to 30 cmH2O to prevent aspiration while avoiding tracheal mucosal ischemia", "Inflate cuff pressure to 80 cmH2O"),
            Triple("Mechanical Ventilation High-Pressure Alarm Troubleshooting", "Suction for secretions, check for tube biting (bite block), kinked tubing, pulmonary edema, or pneumothorax", "Disconnect tubing and leave open to room air"),
            Triple("Mechanical Ventilation Low-Pressure Alarm Troubleshooting", "Check for loose connections, cuff leak, or accidental ventilator tubing disconnection from ET tube", "Increase PEEP to 25 cmH2O immediately"),
            Triple("Acute Respiratory Distress Syndrome (ARDS) Prone Positioning", "Prone positioning improves oxygenation, recruits posterior lung alveoli, and reduces mortality in severe ARDS", "Place patient flat in supine Trendelenburg position"),
            Triple("Tension Pneumothorax Emergency Intervention", "Immediate needle decompression with 14G needle at 2nd intercostal space midclavicular line, followed by chest tube placement", "Wait 4 hours for non-contrast CT scan"),
            Triple("Cardiac Tamponade Beck's Triad", "Muffled heart sounds, Jugular Vein Distension (JVD), and Hypotension with narrowed pulse pressure; treat with pericardiocentesis", "Peaked T waves, bradycardia, and hypertension"),
            Triple("Parkland Formula for Burn Fluid Resuscitation", "4 mL x Body Weight (kg) x % Total Body Surface Area (TBSA) burned; give 1/2 in first 8 hours, remaining 1/2 over next 16 hours", "Give 100 mL total over 24 hours"),
            Triple("Rule of Nines Burn Surface Area Calculation", "Head = 9%, Each Arm = 9%, Chest/Abdomen = 18%, Back = 18%, Each Leg = 18%, Perineum = 1%", "Entire head is 50% and arm is 1%"),
            Triple("Superficial Partial-Thickness vs Full-Thickness Burns", "Partial: painful blisters, pink/red, blanching; Full-thickness: painless, leathery, charred/white, non-blanching", "Full-thickness burns cause extreme superficial pain"),
            Triple("Carbon Monoxide Poisoning Management", "Administer 100% High-Flow Oxygen via Non-Rebreather Mask (or hyperbaric oxygen); pulse oximetry readings are falsely normal", "Rely on standard pulse oximeter reading of 98% and give room air"),
            Triple("Heat Stroke Emergency Cooling", "Rapid cooling (ice bath immersion, cold saline lavage, evaporative misting) until core temp drops to 38°C (100.4°F)", "Wrap patient in heavy warm blankets"),
            Triple("Hypothermia Re-warming Safety Rule", "Re-warm CORE before shell to prevent 'Rewarming Shock' and fatal cardiac arrhythmias (Osborn J wave)", "Vigorously rub cold extremities with hot water"),
            Triple("Primary Survey Trauma Sequence (ABCDE)", "A = Airway with C-spine, B = Breathing, C = Circulation with hemorrhage control, D = Disability (GCS), E = Exposure/Environment", "E = Exposure first, then A = Airway"),
            Triple("Secondary Survey Trauma Components", "Head-to-toe detailed physical examination, full vital signs, history (AMPLE: Allergies, Meds, Past history, Last meal, Events), diagnostic tests", "Quick 10-second triage tag assignment"),
            Triple("Flail Chest Emergency Nursing Care", "Stabilize flail segment, administer oxygen/analgesics, prepare for mechanical ventilation if severe hypoxia occurs", "Push forcefully on loose chest wall segment"),
            Triple("Intracranial Pressure Monitoring CPP Target", "Cerebral Perfusion Pressure (CPP) = MAP - ICP; maintain CPP between 60 to 70 mmHg", "Normal CPP is 5 to 10 mmHg"),
            Triple("Cushing's Triad of Increased ICP", "Severe Bradycardia, Systolic Hypertension with widening pulse pressure, and Irregular respirations (Cheyne-Stokes)", "Tachycardia, hypotension, and rapid breathing"),
            Triple("Brain Death Diagnostic Criteria", "Coma, absence of brainstem reflexes (pupillary, corneal, gag, oculovestibular), and positive Apnea Test", "Patient opens eyes to speech and breathes independently"),
            Triple("Snake Bite Emergency First Aid", "Immobilize limb at or slightly below heart level, keep patient calm, do NOT apply tourniquet or suction wound", "Cut wound open and apply ice with tight arterial tourniquet"),
            Triple("Poisoning & Ingestions Activated Charcoal", "Administer within 1 hour of ingestion for adsorbable toxins; CONTRAINDICATED in corrosive/hydrocarbon ingestions", "Administer activated charcoal for drain cleaner ingestion"),
            Triple("Therapeutic Hypothermia Post-Cardiac Arrest", "Cool comatose patients post-ROSC to 32-36°C for 24 hours to preserve neurological function", "Heat patient to 42°C immediately"),
            Triple("Intra-Aortic Balloon Pump (IABP) Timing", "Inflates during DIASTOLE (augments coronary perfusion) and deflates during SYSTOLE (reduces afterload)", "Inflates during systole and deflates during diastole"),
            Triple("Extracorporeal Membrane Oxygenation (ECMO) Purpose", "Provides temporary artificial heart-lung support for severe refractory cardiac or respiratory failure", "Used as oral routine tablet for mild cough"),
            Triple("Disseminated Intravascular Coagulation (DIC) Triad", "Widespread microvascular thrombosis followed by consumption of clotting factors and severe systemic hemorrhage", "Hyperactive clotting without bleeding risk"),
            Triple("Transfusion-Related Acute Lung Injury (TRALI)", "Sudden non-cardiogenic pulmonary edema, hypoxia, and fever within 6 hours of blood transfusion; STOP transfusion", "Slow transfusion rate and give IV digoxin"),
            Triple("Transfusion-Associated Circulatory Overload (TACO)", "Cardiogenic pulmonary edema, hypertension, elevated CVP, JVD following rapid blood transfusion; treat with IV Furosemide", "Give 2 Liters normal saline bolus"),
            Triple("Malignant Hyperthermia Antidote & Signs", "Life-threatening reaction to volatile anesthetics/succinylcholine; rigidity, hypercapnia, fever; treat with IV DANTROLENE", "Treat malignant hyperthermia with succinylcholine bolus"),
            Triple("Massive Blood Transfusion Calcium Monitoring", "Citrate anticoagulant in stored blood binds serum calcium causing severe hypocalcemia; monitor ionized calcium and give Calcium Gluconate", "Causes severe hypercalcemia requiring diuretics"),
            Triple("Emergency Compartment Syndrome Fasciotomy", "Surgical incision of fascia to relieve pressure and restore tissue perfusion in limb compartment syndrome", "Apply tight circumferential plaster cast over swollen limb")
        )

        criticalScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Critical Care Protocol: ${item.second}",
                "Dangerous Emergency Action: ${item.third}",
                "Delay resuscitation and check insurance card",
                "Discontinue monitoring without medical order"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Critical Care and Emergency Nursing",
                "NCLEX-RN / DHA • Hard",
                "Emergency Critical Care Case #${idx + 1}: In managing a critical resuscitation scenario involving ${item.first}, which life-support action is required?",
                options,
                correctPos,
                "Rationale: Critical care and ACLS standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice ensures emergency hemodynamic stability and resuscitation success. Action '${item.third}' is unsafe.",
                "Critical Care • ${item.first}"
            )
        }

        // =========================================================================
        // COMMUNITY HEALTH, INFECTION CONTROL, ETHICS & LEADERSHIP (40 QUESTIONS)
        // =========================================================================
        val commLeadershipScenarios = listOf(
            Triple("START Disaster Triage Red Tag (Immediate)", "Life-threatening injuries with high probability of survival if treated immediately (airway obstruction, tension pneumothorax, open chest wound)", "Minor cuts and sprains in walking wounded"),
            Triple("START Disaster Triage Yellow Tag (Delayed)", "Serious major injuries requiring medical care within 30-120 minutes, but not immediately life-threatening (stable femur fracture, large wound)", "Massive head trauma with brain evisceration and zero respirations"),
            Triple("START Disaster Triage Green Tag (Minor)", "Minor walking wounded injuries (sprains, superficial lacerations); direct them away from primary triage area", "Unconscious patient with absent radial pulse"),
            Triple("START Disaster Triage Black Tag (Expectant)", "Deceased or catastrophic fatal injuries with no chance of survival given limited resources (apneic after opening airway, massive brain destruction)", "Stable simple ankle sprain"),
            Triple("Primary Level of Prevention", "Health promotion and specific protection BEFORE disease occurs (immunizations, wearing seatbelts, nutrition education)", "Mammogram screening for early tumor detection"),
            Triple("Secondary Level of Prevention", "Early detection and prompt intervention to limit disability (Pap smears, BP screening, mammograms, TB Mantoux test)", "Rehabilitation post-stroke"),
            Triple("Tertiary Level of Prevention", "Rehabilitation and restoration to optimal function AFTER disease is established (cardiac rehab, physical therapy, support groups)", "Administering measles vaccine to infants"),
            Triple("Chain of Infection Transmission Links", "Infectious Agent -> Reservoir -> Portal of Exit -> Mode of Transmission -> Portal of Entry -> Susceptible Host", "Infection occurs spontaneously without reservoir"),
            Triple("Tuberculosis Mantoux Tuberculin Skin Test (TST)", "Measure INDURATION (hard raised area), NOT erythema, at 48-72 hours; >=10 mm is positive for high-risk groups (healthcare workers)", "Measure red erythema circle at 5 minutes"),
            Triple("Ebola Virus Isolation & PPE Standards", "Strict Contact + Droplet precautions; full body suit, double gloves, PAPR/N95, boot covers; zero skin exposure", "Standard cloth gown with bare hands"),
            Triple("Clostridium difficile Infection Control", "Contact precautions; MUST WASH HANDS WITH SOAP AND WATER (alcohol gel does NOT kill C. diff spores); bleach for surface cleaning", "Decontaminate hands with 70% alcohol gel"),
            Triple("Methicillin-Resistant Staphylococcus aureus (MRSA) Precautions", "Contact precautions (gown and gloves); dedicated equipment (stethoscope, BP cuff) kept in patient's room", "Airborne isolation with negative pressure room"),
            Triple("Vancomycin-Resistant Enterococcus (VRE) Precautions", "Contact precautions; strict environmental cleaning; avoid overuse of vancomycin", "Standard airborne precautions"),
            Triple("Lyme Disease Vector & Prevention", "Transmitted by Deer Ticks (Borrelia burgdorferi); wear long pants tucked into socks, insect repellent; classic sign: Bull's-eye rash (erythema migrans)", "Transmitted by clean drinking tap water"),
            Triple("Malaria Vector & Prophylaxis", "Transmitted by female Anopheles mosquito; prophylaxis with Chloroquine/Mefloquine; bed nets, mosquito repellent", "Transmitted by casual handshakes"),
            Triple("Epidemiology Incidence vs Prevalence", "Incidence: number of NEW cases in a population over a specific time; Prevalence: TOTAL number of existing cases (new + old)", "Incidence measures total old cases"),
            Triple("Ethical Principle: Autonomy", "Respecting client's right to self-determination and independent healthcare decisions (informed consent, right to refuse treatment)", "Forcing treatment against competent client's written refusal"),
            Triple("Ethical Principle: Beneficence", "Duty to promote good, act in best interest of client, and prevent harm", "Withholding prescribed analgesics to punish patient"),
            Triple("Ethical Principle: Non-Maleficence", "Obligation to DO NO HARM and minimize risk of injury to client", "Administering wrong medication intentionally"),
            Triple("Ethical Principle: Justice", "Fairness, equity, and equal distribution of healthcare resources and care regardless of socioeconomic status", "Providing care only to wealthy patients"),
            Triple("Ethical Principle: Fidelity", "Loyalty, keeping promises, and fulfilling commitments made to client", "Breaking promises made to patients"),
            Triple("Ethical Principle: Veracity", "Obligation to tell the truth and refrain from deceiving clients", "Lying to patient about diagnosis"),
            Triple("Legal Concept: Negligence vs Malpractice", "Negligence: failure to act as a reasonably prudent person; Malpractice: professional negligence committed by a licensed practitioner causing harm", "Malpractice is a minor clerical error without harm"),
            Triple("Four Elements of Medical Malpractice", "1. Duty owed to patient, 2. Breach of duty, 3. Causation (proximate cause), 4. Damages/Injury occurred", "1. Written note, 2. Verbal agreement, 3. No harm, 4. Payment"),
            Triple("Five Rights of Delegation", "Right Task, Right Circumstance, Right Person, Right Direction/Communication, Right Supervision/Evaluation", "Right Task, Right Speed, Right Guess, Right Retaliation"),
            Triple("Delegation Rules to Unlicensed Assistive Personnel (UAP)", "Delegate STABLE routine tasks (ADLs, vital signs on stable clients, hygiene, ambulation, feeding); NO Assessment, Teaching, or Evaluation", "Delegate initial nursing assessment to UAP"),
            Triple("Delegation Rules to Licensed Practical Nurse (LPN/LVN)", "Can administer oral/SC/IM meds, perform sterile dressing changes, urinary catheterization, collect data on STABLE clients; NO initial assessment/teaching/IV push", "Delegate initial nursing care plan formulation to LPN"),
            Triple("Nurse Delegation Exclusion Rule (EAT)", "RN CANNOT delegate Evaluation, Assessment, or Teaching (EAT) to LPN or UAP", "RN delegates initial admission assessment to UAP"),
            Triple("Leadership Style: Transformational", "Inspires, motivates, and empowers staff through shared vision, innovation, and professional growth", "Controls every minor detail strictly without input"),
            Triple("Leadership Style: Democratic / Participative", "Encourages team participation in decision making, fosters staff autonomy and collaboration", "Makes all decisions in isolation without consulting team"),
            Triple("Leadership Style: Autocratic / Authoritarian", "Makes decisions independently with strict control, minimal staff input; effective in acute emergency crises", "Allows staff to do whatever they want without rules"),
            Triple("Leadership Style: Laissez-Faire", "Permissive hands-off approach with minimal guidance; effective only with highly experienced self-directed teams", "Micromanages every single second of shift"),
            Triple("Quality Improvement (QI) Root Cause Analysis (RCA)", "Structured retrospective analysis of a sentinel event to identify underlying system flaws rather than individual blame", "Firing the bedside nurse immediately without investigation"),
            Triple("Sentinel Event Definition", "Unexpected occurrence involving death or serious physical/psychological injury (e.g. wrong-site surgery, infant abduction, suicide in hospital)", "Routine minor medication delay of 5 minutes"),
            Triple("SBAR Communication Standard", "S = Situation, B = Background, A = Assessment, R = Recommendation; standardized tool for effective interprofessional handoff", "S = Secrets, B = Blame, A = Argument, R = Rejection"),
            Triple("Conflict Management: Collaboration (Win-Win)", "Both parties work together to find a mutually satisfying solution that addresses all concerns", "One party surrenders completely while harboring resentment"),
            Triple("Prioritization Framework: Maslow's Hierarchy", "Physiological needs (airway, breathing, circulation, fluid, pain) MUST be met before Safety, Belonging, or Self-esteem", "Address self-actualization before airway obstruction"),
            Triple("Prioritization Framework: ABCs", "Airway FIRST, Breathing second, Circulation third (unless in cardiac arrest where CAB sequence applies)", "Circulation first then Airway then Breathing"),
            Triple("Prioritization Framework: Safety & Risk Reduction", "Identify immediate life threats or high risk of injury before addressing chronic stable concerns", "Address chronic stable rash before acute severe shortness of breath"),
            Triple("Change Theory (Kurt Lewin 3-Step Model)", "Unfreezing (creating motivation for change), Moving/Changing (implementing new practice), Refreezing (establishing new standard as permanent habit)", "Forcing change overnight without unfreezing")
        )

        commLeadershipScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Evidence-Based Leadership Practice: ${item.second}",
                "Inappropriate / Violation Practice: ${item.third}",
                "Ignore policy and act without authorization",
                "Delegate task to unregistered visitor"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Community Health Nursing, Infection Control, Ethics and Leadership",
                "NCLEX-RN / DHA • Medium",
                "Community Health & Leadership Case #${idx + 1}: In addressing a professional nursing practice scenario involving ${item.first}, which clinical decision is correct?",
                options,
                correctPos,
                "Rationale: Community health, ethics, and leadership principles for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice upholds professional practice, patient rights, infection control, and legal safety. Action '${item.third}' is unsafe.",
                "Community & Leadership • ${item.first}"
            )
        }

        return list
    }
}
