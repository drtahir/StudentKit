package com.drtahir.studentkit.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 7 QUESTION BANK
 * 50+ Questions per subject (200+ total questions)
 * Subjects:
 * 1. Critical Care Nursing - 50 Qs
 * 2. Leadership & Management in Nursing - 50 Qs
 * 3. Epidemiology & Public Health Nursing - 50 Qs
 * 4. Nursing Research Project & Biostatistics - 50 Qs
 */
object KpSemester7QuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var idCounter = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        fun addSubjectQs(
            sem: Int,
            subj: String,
            ref: String,
            rawList: List<Triple<String, List<String>, Int>>
        ) {
            for (item in rawList) {
                val correctOpt = item.second[item.third]
                questions.add(
                    KpSemesterQuestion(
                        id = idCounter++,
                        semesterNumber = sem,
                        subjectName = subj,
                        question = item.first,
                        options = item.second,
                        correctIndex = item.third,
                        explanation = "Correct answer: $correctOpt. Aligned with PNC / KMU Semester 7 curriculum.",
                        reference = ref
                    )
                )
            }
        }

        // ==========================================
        // 1. CRITICAL CARE NURSING - 50 Qs
        // ==========================================
        val ccn = listOf(
            Triple("An arterial blood gas (ABG) report shows: pH 7.28, PaCO2 56 mmHg, HCO3 25 mEq/L. This acid-base disturbance is interpreted as:", listOf("Uncompensated Respiratory Acidosis", "Uncompensated Metabolic Acidosis", "Fully Compensated Respiratory Alkalosis", "Metabolic Alkalosis"), 0),
            Triple("A patient in the ICU post cardiac arrest exhibits arterial line waveform dampening. The nurse's first troubleshooting action should be to:", listOf("Flush line with heparinized saline and check for air bubbles / tubing kinks", "Calibrate transducer to room air", "Notify physician for arterial line removal", "Increase IV vasopressor rate"), 0),
            Triple("Central Venous Pressure (CVP) normal reference range in a critically ill adult patient is:", listOf("2 to 8 mmHg (or 3-8 cmH2O)", "12 to 20 mmHg", "25 to 30 mmHg", "0 to 1 mmHg"), 0),
            Triple("A CVP reading of 1 mmHg in an ICU patient accompanied by tachycardia and low urine output indicates:", listOf("Fluid volume overload", "Hypovolemia / Fluid volume deficit", "Right heart failure", "Cardiogenic shock"), 1),
            Triple("A CVP reading elevated at 16 mmHg in a critically ill patient indicates:", listOf("Dehydration", "Hypervolemia or Right Ventricular failure", "Septic shock", "Aortic stenosis"), 1),
            Triple("Positive End-Expiratory Pressure (PEEP) setting on a mechanical ventilator functions primarily to:", listOf("Increase tidal volume", "Prevent alveolar collapse at the end of expiration and improve oxygenation", "Lower peak airway pressure", "Reduce arterial PCO2"), 1),
            Triple("High-pressure ventilator alarm sounding continuously in an ICU patient is most commonly caused by:", listOf("Tubing disconnection", "Endotracheal tube bite, secretions needing suctioning, or bronchospasm", "Patient hypoventilation", "Cuff leak"), 1),
            Triple("Low-pressure ventilator alarm sounding in a intubated patient typically indicates:", listOf("Patient coughing", "Ventilator circuit disconnection or ETT cuff leak", "Kink in ventilator tubing", "Pulmonary edema"), 1),
            Triple("Ventilator-Associated Pneumonia (VAP) bundle prevention measures include maintaining head of bed elevated at:", listOf("Flat 0 degrees", "10 to 15 degrees", "30 to 45 degrees", "90 degrees High Fowler's"), 2),
            Triple("Richmond Agitation-Sedation Scale (RASS) score of -2 in a mechanically ventilated patient represents:", listOf("Combative and violent", "Alert and calm", "Light sedation (briefly awakens to voice with eye contact < 10 seconds)", "Deep sedation / unarousable"), 2),
            Triple("RASS score target for optimal light sedation in most mechanically ventilated ICU patients is:", listOf("+3 to +4", "0 to -2", "-4 to -5", "+1 only"), 1),
            Triple("In Glasgow Coma Scale (GCS) assessment, a patient who opens eyes to pain, utters incomprehensible sounds, and withdraws from pain receives a total score of:", listOf("Score 3", "Score 8", "Score 12", "Score 15"), 1),
            Triple("GCS score threshold indicative of severe brain injury requiring immediate endotracheal intubation for airway protection is:", listOf("GCS <= 12", "GCS <= 8", "GCS <= 14", "GCS <= 6"), 1),
            Triple("Mean Arterial Pressure (MAP) formula calculated from Systolic Blood Pressure (SBP) and Diastolic Blood Pressure (DBP) is:", listOf("MAP = (SBP + DBP) / 2", "MAP = DBP + 1/3 (SBP - DBP)", "MAP = SBP + 2(DBP)", "MAP = SBP - DBP"), 1),
            Triple("Minimum target MAP required to ensure adequate perfusion to vital organs (kidneys, brain) in septic shock is:", listOf("40 mmHg", "65 mmHg", "90 mmHg", "120 mmHg"), 1),
            Triple("First-line vasopressor of choice for restoring MAP in septic shock unresponsive to fluid resuscitation is:", listOf("Epinephrine", "Norepinephrine", "Dopamine low dose", "Atropine"), 1),
            Triple("Infusion of high-dose Norepinephrine requires monitoring for peripheral vascular complications such as:", listOf("Severe peripheral vasoconstriction and limb ischemia/necrosis", "Profuse diarrhea", "Hypoglycemia", "Fluid volume excess"), 0),
            Triple("Cardiogenic shock is characterized hemodynamically by low Cardiac Output, high Pulmonary Artery Wedge Pressure (PAWP), and:", listOf("Decreased systemic vascular resistance", "Elevated Systemic Vascular Resistance (SVR)", "Normal blood pressure", "Low CVP"), 1),
            Triple("An Intra-Aortic Balloon Pump (IABP) inflates during which phase of the cardiac cycle?", listOf("Systole", "Diastole (increasing coronary artery perfusion)", "Isovolumetric contraction", "Atrial kick"), 1),
            Triple("IABP catheter balloon deflates immediately prior to:", listOf("Ventricular diastole", "Ventricular systole (reducing left ventricular afterload)", "Atrial contraction", "T-wave completion"), 1),
            Triple("During chest tube suction management, continuous vigorous bubbling in the water seal chamber indicates:", listOf("Normal lung expansion", "An air leak in the chest tube system or pleural space", "Tension pneumothorax resolving", "Suction pressure is too high"), 1),
            Triple("If an ICU patient's chest tube accidentally becomes disconnected from the drainage unit, the immediate nursing priority is to:", listOf("Clamp the chest tube near patient chest", "Submerge the open end of chest tube in a bottle of sterile saline or water", "Cover with gauze", "Notify doctor"), 1),
            Triple("Sudden cessation of chest tube drainage in a patient with a hemothorax accompanied by tracheal deviation indicates possible:", listOf("Normal recovery", "Tension Pneumothorax or tube occlusion by blood clot", "Pleural effusion resolution", "Atelectasis"), 1),
            Triple("Infusion of Propofol (Diprivan) for sedation in intubated ICU patients requires changing lipid infusion tubing every:", listOf("72 hours", "12 hours (due to high risk of bacterial growth)", "24 hours", "7 days"), 1),
            Triple("Propofol Infusion Syndrome (PRIS) is a rare fatal complication marked by metabolic acidosis, rhabdomyolysis, hyperkalemia, and:", listOf("Hypertension", "Refractory cardiac failure / bradycardia", "Hyperglycemia", "Polycythemia"), 1),
            Triple("During Continuous Renal Replacement Therapy (CRRT) in hemodynamically unstable ICU patients, frequent monitoring of which electrolyte is essential?", listOf("Potassium and Ionized Calcium", "Sodium only", "Iron", "Bilirubin"), 0),
            Triple("In acute Respiratory Distress Syndrome (ARDS), Berlin definition includes PaO2/FiO2 ratio less than or equal to:", listOf("400 mmHg", "300 mmHg (with PEEP >= 5 cmH2O)", "500 mmHg", "100 mmHg only"), 1),
            Triple("Severe ARDS refractory hypoxemia intervention that improves ventilation-perfusion matching by placing patient face down is:", listOf("Trendelenburg positioning", "Prone positioning (for 16 consecutive hours)", "High Fowler's position", "Lithotomy position"), 1),
            Triple("In neuro-ICU patient with elevated intracranial pressure (ICP > 20 mmHg), Mannitol 20% IV is administered to:", listOf("Lower blood pressure", "Draw fluid from brain parenchyma into intravascular space via osmotic diuresis", "Sedate patient", "Increase ICP"), 1),
            Triple("When administering Mannitol IV, the nurse must inspect the vial for crystals and administer using a:", listOf("Micro-drip set", "In-line IV filter", "Blood tubing without filter", "Standard gravity set"), 1),
            Triple("Cushing's Triad signaling impending brainstem herniation due to severely increased ICP consists of:", listOf("Hypotension, tachycardia, and tachypnea", "Widening pulse pressure (hypertension), bradycardia, and irregular/Cheyne-Stokes respirations", "Fever, tachycardia, hypotension", "Pupillary constriction, normal BP, normal pulse"), 1),
            Triple("When suctioning an intubated ICU patient, each suction pass should be limited to maximum duration of:", listOf("30 seconds", "10 to 15 seconds (with pre-oxygenation 100% O2)", "45 seconds", "1 minute"), 1),
            Triple("Continuous ET tube cuff pressure should be maintained between:", listOf("5 to 10 cmH2O", "20 to 30 cmH2O (to prevent mucosal necrosis and aspiration)", "40 to 50 cmH2O", "60 cmH2O"), 1),
            Triple("In Therapeutic Hypothermia (Targeted Temperature Management) post cardiac arrest, target core body temperature is maintained at:", listOf("30°C to 32°C", "32°C to 36°C for 24 hours", "37°C to 38°C", "28°C"), 1),
            Triple("During rewarming phase following targeted temperature management, the nurse must monitor closely for:", listOf("Hyperkalemia and hypoglycemia", "Hypokalemia turning into rapid hyperkalemia and hypotension", "Severe shivering", "Hypertension"), 1),
            Triple("Defibrillation is indicated immediately for which cardiac arrest rhythms?", listOf("Asystole and PEA", "Pulseless Ventricular Tachycardia (pVT) and Ventricular Fibrillation (VF)", "Sinus Bradycardia", "Atrial Fibrillation"), 1),
            Triple("In Pulseless Electrical Activity (PEA) or Asystole, primary resuscitation medication given IV/IO every 3-5 minutes is:", listOf("Amiodarone 300 mg", "Epinephrine 1 mg", "Atropine 1 mg", "Lidocaine 100 mg"), 1),
            Triple("Reversible causes of cardiac arrest known as '5 Hs and 5 Ts' include Hypovolemia, Hypoxia, Hydrogen ion (acidosis), Hypo/Hyperkalemia, Hypothermia, and:", listOf("Tension pneumothorax, Tamponade, Toxins, Thrombosis (pulmonary & coronary)", "Trauma, Tumors, Typhoid, Tetanus", "Thyroid toxicosis", "Tuberculosis"), 0),
            Triple("Subarachnoid hemorrhage patient experiencing cerebral vasospasm prevention is treated with oral calcium channel blocker:", listOf("Amlodipine", "Nimodipine", "Verapamil", "Diltiazem"), 1),
            Triple("In neuro-ICU care, 'Triple-H Therapy' historically used to treat cerebral vasospasm includes Hypertension, Hypervolemia, and:", listOf("Hypothermia", "Hemodilution", "Hyperglycemia", "Hypoventilation"), 1),
            Triple("Surviving Sepsis Campaign 1-Hour Bundle initial management step includes measuring lactate, obtaining blood cultures prior to antibiotics, giving broad-spectrum antibiotics, and:", listOf("Administering 30 mL/kg crystalloid bolus for hypotension or lactate >= 4 mmol/L", "Immediate hemodialysis", "Giving IV steroids", "NPO for 48 hours"), 0),
            Triple("Normal Cardiac Index (CI) in adult ICU setting is:", listOf("1.0 to 1.5 L/min/m2", "2.5 to 4.0 L/min/m2", "5.0 to 8.0 L/min/m2", "10 L/min/m2"), 1),
            Triple("An ICU patient receiving parenteral nutrition (TPN) via central venous catheter develops sudden shortness of breath, chest pain, and cyanosis after line disconnection. The nurse suspects Air Embolism and places patient in:", listOf("High Fowler's right side", "Left Trendelenburg position (Durant's maneuver)", "Supine flat", "Prone position"), 1),
            Triple("In Disseminated Intravascular Coagulation (DIC), laboratory findings show:", listOf("Elevated platelets and low D-dimer", "Prolonged PT/aPTT, decreased fibrinogen, low platelets, and elevated D-dimer", "High fibrinogen", "Normal clotting time"), 1),
            Triple("Continuous Arterial Line blood pressure monitoring requires placing the transducer at the phlebostatic axis, located at the:", listOf("2nd intercostal space midclavicular line", "4th intercostal space midaxillary line (level of right atrium)", "5th intercostal space anterior axillary line", "Umbilicus level"), 1),
            Triple("Zeroing the arterial line transducer compensates for external atmospheric pressure and must be performed:", listOf("Once a month", "At the start of every shift and after patient position changes", "Only when line is inserted", "Never"), 1),
            Triple("In acute pancreatitis admitted to ICU, two physical signs of retroperitoneal and periumbilical hemorrhage are Grey Turner's sign and:", listOf("Chvostek's sign", "Cullen's sign", "Trousseau's sign", "Kernig's sign"), 1),
            Triple("A patient in hyperosmolar hyperglycemic state (HHS) differs from DKA primarily by having higher blood glucose (> 600 mg/dL) and absence of significant:", listOf("Dehydration", "Ketoacidosis / Serum Ketones", "Altered mental status", "Hyperosmolality"), 1),
            Triple("When weaning a patient from mechanical ventilator via Spontaneous Breathing Trial (SBT), signs of weaning failure requiring trial termination include:", listOf("RR 16/min, SpO2 98%", "RR > 35/min, HR > 120 bpm, SpO2 < 90%, agitation, or accessory muscle use", "Normal ABGs", "HR 75 bpm"), 1),
            Triple("Delirium in ICU patients (evaluated using CAM-ICU) is characterized by acute onset of fluctuating mental status, inattention, and:", listOf("Permanent dementia", "Altered level of consciousness or disorganized thinking", "Normal sleep cycle", "Hyperreflexia"), 1)
        )
        addSubjectQs(7, "Critical Care Nursing", "KMU CCN-671 / Urden Critical Care Nursing", ccn)

        // ==========================================
        // 2. LEADERSHIP & MANAGEMENT IN NURSING - 50 Qs
        // ==========================================
        val mgt = listOf(
            Triple("Autocratic leadership style in nursing management is characterized by:", listOf("Shared decision making with staff", "Centralized decision making where the leader maintains strict control and gives orders", "Total freedom to employees", "Situational adaptation"), 1),
            Triple("Democratic / Participative leadership style is best described as:", listOf("Leader making all decisions alone", "Encouraging group discussion, input, and consensus building in decision making", "Complete hands-off approach", "Dictatorial management"), 1),
            Triple("Laissez-Faire leadership style functions best when staff members are:", listOf("Inexperienced student nurses", "Highly qualified, self-motivated, autonomous professional experts", "Newly hired graduates", "In an emergency crisis"), 1),
            Triple("Transformational leadership in nursing focuses on:", listOf("Transactional rewards and punishments", "Inspiring, motivating, and empowering staff toward a shared vision and organizational growth", "Maintaining status quo", "Enforcing strict rules"), 1),
            Triple("Five Rights of Delegation defined by National Council of State Boards of Nursing (NCSBN) include Right Task, Right Circumstances, Right Person, Right Direction/Communication, and:", listOf("Right Salary", "Right Supervision / Evaluation", "Right Shift", "Right Hospital"), 1),
            Triple("Which task can a Registered Nurse (RN) safely delegate to a Licensed Practical Nurse / Auxiliary Nurse?", listOf("Formulating initial nursing diagnosis and care plan", "Administering routine oral medications to stable patients", "Initial admission assessment", "Evaluating complex IV drug response"), 1),
            Triple("A Registered Nurse CANNOT delegate which core function to assistive personnel?", listOf("Taking routine vital signs", "Assisting stable patient with feeding", "Nursing Assessment, Nursing Diagnosis, and Care Plan Evaluation", "Measuring intake and output"), 2),
            Triple("Conflict management strategy where both parties give up something to reach a mutually acceptable middle-ground solution is:", listOf("Competing", "Compromising", "Avoiding", "Accommodating"), 1),
            Triple("Conflict management strategy resulting in a true 'Win-Win' outcome where parties work together to solve root cause is:", listOf("Collaborating", "Avoiding", "Smoothing", "Compromising"), 0),
            Triple("In Thomas-Kilmann conflict mode model, 'Avoiding' strategy is appropriate when:", listOf("The issue is critical and urgent", "The issue is trivial or emotions are high and time is needed to cool down", "Immediate quick decision is needed", "Long-term relationship is priority"), 1),
            Triple("Lewin's Change Theory consists of three sequential phases:", listOf("Planning, Doing, Evaluation", "Unfreezing, Moving / Changing, and Refreezing", "Assessment, Intervention, Outcome", "Initiation, Action, Termination"), 1),
            Triple("During Lewin's 'Unfreezing' phase of planned change, the manager's priority action is to:", listOf("Institutionalize new habits", "Create motivation and awareness of the need for change by unfreezing status quo", "Reward old habits", "Ignore staff anxiety"), 1),
            Triple("Lewin's 'Refreezing' phase involves:", listOf("Introducing new ideas", "Stabilizing and integrating the new change into organizational culture and routine practice", "Creating discontent", "Conducting needs assessment"), 1),
            Triple("Magnet Hospital designation awarded by American Nurses Credentialing Center (ANCC) recognizes healthcare institutions demonstrating:", listOf("Lowest financial costs", "Excellence in nursing care, positive patient outcomes, and high nurse job satisfaction", "Maximum beds", "Private ownership"), 1),
            Triple("Primary Nursing care delivery model is structured so that:", listOf("One nurse performs all baths, another gives all meds", "A single primary nurse assumes 24-hour accountability for planning and evaluating patient care from admission to discharge", "Nurses rotate daily", "Patients care for themselves"), 1),
            Triple("Functional Nursing delivery model organizes nursing care by:", listOf("Task allocation (e.g. one nurse gives all IV meds, another checks all vitals)", "Assigning 1 nurse to 1 patient total care", "Team modular care", "Primary care nurse"), 0),
            Triple("Team Nursing care delivery model relies on:", listOf("Individual isolated nurses", "A Registered Nurse leading a team of RNs, LPNs, and aides responsible for a group of patients", "Doctor-led ward management", "Patient self-care"), 1),
            Triple("Patient-Centered Care core pillar emphasizes:", listOf("Hospital profits", "Respecting patient values, preferences, cultural needs, and involving family in decisions", "Physician dominance", "Standardized rules without exception"), 1),
            Triple("In nursing staffing calculations, Full-Time Equivalent (FTE) represents how many productive working hours per year?", listOf("1,000 hours", "2,080 hours (40 hours/week x 52 weeks)", "500 hours", "4,000 hours"), 1),
            Triple("Patient Acuity staffing system assigns nursing staff based on:", listOf("Number of beds only", "Individual patient severity of illness, care complexity, and required nursing intervention hours", "Nurse seniority", "Random shift drawing"), 1),
            Triple("Continuous Quality Improvement (CQI) cycle model commonly utilized in healthcare management is:", listOf("SWOT Analysis", "PDCA / PDSA Cycle (Plan-Do-Check/Study-Act)", "PERI model", "Maslow Hierarchy"), 1),
            Triple("In PDSA cycle, the 'Study / Check' phase involves:", listOf("Implementing trial change", "Analyzing collected data to evaluate if the change resulted in measurable improvement", "Brainstorming problem", "Writing policy"), 1),
            Triple("Root Cause Analysis (RCA) is a structured retrospective investigation technique conducted following:", listOf("Standard shift report", "A Sentinel Event or severe adverse medical outcome to identify underlying systemic flaws", "Routine staff meeting", "Patient discharge"), 1),
            Triple("A 'Sentinel Event' in hospital healthcare is defined as an unexpected occurrence involving:", listOf("Minor drug delay", "Death, severe physical/psychological injury, or risk thereof (e.g. wrong-site surgery)", "Bed shortage", "Late breakfast"), 1),
            Triple("SWOT Analysis strategic planning framework evaluates organizational Strengths, Weaknesses, Opportunities, and:", listOf("Tactics", "Threats", "Treatments", "Timelines"), 1),
            Triple("Which component of SWOT analysis represents INTERNAL organizational characteristics?", listOf("Opportunities and Threats", "Strengths and Weaknesses", "Threats and Strengths", "Opportunities and Weaknesses"), 1),
            Triple("Prioritizing patient care using Eisenhower Matrix categorizes tasks based on:", listOf("Difficulty and cost", "Urgency and Importance", "Popularity and ease", "Shift duration"), 1),
            Triple("First step in nursing performance appraisal process is to:", listOf("Conduct annual review interview", "Clearly establish and communicate job performance standards and expectations in advance", "File disciplinary letter", "Compare staff with each other"), 1),
            Triple("Halo Effect bias in employee performance evaluation occurs when an evaluator:", listOf("Rates an employee low based on one bad incident", "Allows one positive trait or recent accomplishment to overly influence the total overall evaluation rating", "Rates everyone average", "Evaluates strictly by numbers"), 1),
            Triple("Horn Effect evaluation bias occurs when:", listOf("One negative quality causes the manager to rate the employee low in all performance areas", "Ratings are perfectly balanced", "All staff receive high scores", "Manager ignores errors"), 0),
            Triple("Central Tendency bias in performance rating describes a manager who:", listOf("Rates all employees as average to avoid conflict or justification", "Rates everyone superior", "Fails to show up for review", "Fires underperformers"), 0),
            Triple("Incivility and lateral (horizontal) violence in nursing workplace refers to:", listOf("Physical violence from patients", "Aggressive, disrespectful, or disruptive behavior directed by one nurse toward a peer colleague", "Management pay cuts", "Equipment malfunction"), 1),
            Triple("Preceptor role in onboarding newly employed graduate nurses is to:", listOf("Act as permanent boss", "Provide 1-on-1 direct clinical instruction, role modeling, and evaluation during orientation period", "Audit hospital finances", "Conduct licensing exams"), 1),
            Triple("Mentor relationship differs from preceptors because mentoring is typically:", listOf("Short-term assigned orientation", "Long-term, voluntary professional guidance and career development relationship", "Paid hourly instruction", "Mandated by law"), 1),
            Triple("Shared Governance organizational model in nursing empowers clinical staff nurses to:", listOf("Set hospital drug prices", "Participate directly in clinical decision-making councils, policy development, and practice standards", "Fire administrators", "Work without license"), 1),
            Triple("Fiscal year operational budget line item covering staff salaries, benefits, and overtime is the:", listOf("Capital Budget", "Personnel / Operational Budget", "Revenue Budget", "Equipment Reserve"), 1),
            Triple("Capital Budget in hospital healthcare financial management accounts for major long-term purchases exceeding a specified threshold (e.g., > $5,000 / Rs. 500,000) such as:", listOf("Syringes and cotton", "MRI machines, mechanical ventilators, or facility renovation", "Nurse monthly salaries", "Paper printing"), 1),
            Triple("Variance Analysis in nursing financial management measures the difference between:", listOf("Two nurses' salaries", "Projected budgeted expenditure and actual incurred financial expense", "Drug prices", "Patient counts"), 1),
            Triple("Ethical principle 'Autonomy' in nursing management supports:", listOf("Manager dictating care", "Patient's right to self-determination and informed personal decision-making", "Enforcing hospital rules", "Fines for missed appointments"), 1),
            Triple("Fidelity ethical principle obligates the nurse manager to:", listOf("Tell the truth always", "Keep promises, fulfill professional commitments, and maintain loyalty to obligations", "Do good for all", "Treat everyone equally"), 1),
            Triple("Veracity ethical principle requires healthcare professionals to:", listOf("Respect privacy", "Practice complete truthfulness and honesty with patients and staff", "Do no harm", "Fairly distribute resources"), 1),
            Triple("SBAR standardized communication tool stands for:", listOf("Subjective, Body, Assessment, Result", "Situation, Background, Assessment, Recommendation", "Status, Bed, Action, Review", "Safety, Briefing, Analysis, Report"), 1),
            Triple("Using SBAR tool during physician notification, stating 'Patient's BP dropped to 80/40, HR 125, and surgical dressing is saturated with blood' represents:", listOf("Situation", "Background", "Assessment", "Recommendation"), 2),
            Triple("In SBAR communication, stating 'I recommend ordering a 500 mL Normal Saline bolus and STAT CBC' represents:", listOf("Situation", "Background", "Assessment", "Recommendation"), 3),
            Triple("Emotional Intelligence (EQ) in nursing leadership includes key dimensions of Self-Awareness, Self-Regulation, Motivation, Social Skills, and:", listOf("High IQ score", "Empathy", "Clinical technical speed", "Financial dominance"), 1),
            Triple("A nurse manager confronting a habitually tardy employee using progressive discipline starts with:", listOf("Immediate termination", "Informal verbal counsel / warning, followed by formal written warning if unimproved", "Pay reduction", "Public reprimand"), 1),
            Triple("Whistleblowing in nursing legal context refers to:", listOf("Calling code blue", "Reporting illegal, unethical, or unsafe institutional practices to authorities or oversight bodies", "Calling shift report", "Alarm sound"), 1),
            Triple("Under PNC guidelines, Risk Management program primary goal in hospital setting is to:", listOf("Hide medical errors", "Identify, analyze, and minimize clinical risks and liabilities to protect patients and institution", "Increase patient bills", "Fire staff"), 1),
            Triple("Just Culture framework in healthcare safety encourages reporting errors by distinguishing between human error, risky behavior, and:", listOf("Inexperience", "Reckless behavior (intentional disregard for safety protocols)", "Equipment failure", "Night shift fatigue"), 1),
            Triple("In Just Culture, response to honest Human Error (e.g. slip or lapse) should be:", listOf("Punitive termination", "Console and support the individual while examining system processes", "Public suspension", "Financial penalty"), 1)
        )
        addSubjectQs(7, "Leadership & Management in Nursing", "KMU MGT-672 / Marquis & Huston Leadership Roles", mgt)

        // ==========================================
        // 3. EPIDEMIOLOGY & PUBLIC HEALTH NURSING - 50 Qs
        // ==========================================
        val epi = listOf(
            Triple("Epidemiology is defined as the study of the distribution and determinants of health-related states or events in specified populations, and the:", listOf("Treatment of individual patients", "Application of this study to control health problems", "Calculation of hospital bills", "Manufacture of vaccines"), 1),
            Triple("Epidemiologic Triad model of disease causation consists of three interacting elements:", listOf("Person, Place, Time", "Agent, Host, and Environment", "Bacteria, Virus, Parasite", "Primary, Secondary, Tertiary"), 1),
            Triple("In Epidemiologic Triad, the biological organism, chemical substance, or physical force required to cause disease is the:", listOf("Host", "Agent", "Vector", "Reservoir"), 1),
            Triple("In Epidemiologic Triad, the human or animal that offers lodgment and subsist to an infectious agent is the:", listOf("Environment", "Host", "Fomite", "Vehicle"), 1),
            Triple("Incidence Rate measures:", listOf("Existing total cases in a population at a specific point in time", "Number of NEW cases of a disease developing in a susceptible population during a specified time period", "Total deaths", "Hospital bed occupancy"), 1),
            Triple("Prevalence Rate measures:", listOf("New cases only", "ALL existing cases (new + old) in a population at a given point or period in time", "Cure rate", "Birth rate"), 1),
            Triple("Prevalence is mathematically related to Incidence by the formula:", listOf("Prevalence = Incidence x Average Duration of Disease (P = I x D)", "Prevalence = Incidence / Mortality", "Prevalence = Births - Deaths", "Prevalence = New cases / Population"), 0),
            Triple("Epidemic is defined as the occurrence of disease in a community or region:", listOf("Constant presence within a given geographic area", "Clearly in excess of normal expected expectancy for that population", "Worldwide spread across continents", "Zero cases"), 1),
            Triple("Endemic status refers to a disease that is:", listOf("Rapidly spreading globally", "Constantly present at a baseline expected level within a specific geographic area or population", "Eradicated completely", "Unexplained death spike"), 1),
            Triple("Pandemic is defined as an epidemic that has:", listOf("Occurred in 1 village", "Spread across international boundaries, affecting multiple countries and continents with large scale impact", "Zero mortality", "Limited to animals"), 1),
            Triple("Sporadic disease occurrence refers to:", listOf("Continuous high prevalence", "Infrequent, irregular, and isolated disease cases occurring without spatial connection", "Global outbreak", "Seasonal epidemic"), 1),
            Triple("Infant Mortality Rate (IMR) calculation numerator is number of deaths under 1 year of age per:", listOf("1,000 live births in a given year", "10,000 population", "100,000 population", "100 live births"), 0),
            Triple("Maternal Mortality Ratio (MMR) calculation numerator is maternal deaths due to pregnancy/childbirth complications per:", listOf("1,000 live births", "100,000 live births", "10,000 women", "1,000,000 population"), 1),
            Triple("Under-5 Mortality Rate (U5MR) measures probability of dying between birth and exact age 5 years per:", listOf("100 live births", "1,000 live births", "10,000 children", "500 live births"), 1),
            Triple("Case Fatality Rate (CFR) measures proportion of individuals diagnosed with a specific disease who:", listOf("Are cured within 1 year", "Die from that specific disease (indicating disease severity)", "Spread disease to others", "Require hospitalization"), 1),
            Triple("Primary Prevention strategies aim to prevent disease onset by controlling risks, exemplified by:", listOf("Pap smear screening for cervical cancer", "Childhood Immunization and wearing seatbelts", "Physical therapy post stroke", "Insulin therapy for established diabetes"), 1),
            Triple("Secondary Prevention focuses on early disease detection and prompt intervention to halt progression, exemplified by:", listOf("Measles MMR vaccination", "Screening Mammography and BP check at community clinic", "Cardiac rehabilitation", "Health education on clean water"), 1),
            Triple("Tertiary Prevention measures aim to reduce complications, disability, and rehabilitate established disease, exemplified by:", listOf("Polio drops for infants", "Diabetic foot care clinic and post-stroke rehabilitation", "Routine blood pressure screening", "Sanitation improvements"), 1),
            Triple("Herd Immunity threshold needed to prevent sustained community transmission of highly contagious airborne measles is approximately:", listOf("30% to 40%", "92% to 95%", "50%", "70%"), 1),
            Triple("Vector-borne transmission of Dengue Fever virus to humans occurs through the bite of infected mosquito species:", listOf("Anopheles stephensi", "Aedes aegypti / Aedes albopictus", "Culex quinquefasciatus", "Phlebotomus sandfly"), 1),
            Triple("Malaria transmission to humans occurs via the bite of infected female mosquito species:", listOf("Aedes aegypti", "Anopheles female mosquito", "Culex", "Mansonia"), 1),
            Triple("Leishmaniasis (Cutaneous/Visceral) transmitted endemic in parts of KP is spread by the bite of infected:", listOf("Housefly", "Phlebotomus Sandfly", "Tick", "Flea"), 1),
            Triple("Typhoid Fever (Salmonella typhi) primary mode of transmission is:", listOf("Airborne droplet nuclei", "Fecal-oral route through contaminated food and water", "Direct blood transfusion", "Mosquito bite"), 1),
            Triple("Attributable Risk (AR) measures the proportion of disease incidence in an exposed group that is directly attributable to:", listOf("Random chance", "The specific risk factor / exposure", "Confounding variable", "Lack of treatment"), 1),
            Triple("Relative Risk (RR) calculated in Cohort studies is ratio of risk of disease in exposed group compared to:", listOf("Unexposed group", "Total population", "Deceased group", "Hospitalized group"), 0),
            Triple("Relative Risk value of RR = 1.0 indicates:", listOf("Risk in exposed is higher", "NO association between exposure and disease", "Protective factor", "Inverse risk"), 1),
            Triple("Relative Risk value of RR = 3.2 indicates that exposed individuals are:", listOf("3.2 times LESS likely to develop disease", "3.2 times MORE likely to develop disease compared to unexposed", "32% cured", "3.2% fatal"), 1),
            Triple("Odds Ratio (OR) is the primary measure of association calculated in which study design?", listOf("Randomized Controlled Trial", "Case-Control Study", "Cross-Sectional Survey", "Ecological study"), 1),
            Triple("Case-Control study design selects subjects based on:", listOf("Exposure status first", "Presence of disease (Cases) versus absence of disease (Controls)", "Random general population", "Future follow up"), 1),
            Triple("Cohort study design selects subjects based on:", listOf("Disease status first", "Exposure status (Exposed vs Unexposed) and follows them over time for disease development", "Hospital discharge", "Age strata only"), 1),
            Triple("Main limitation of Case-Control studies compared to Cohort studies is susceptibility to:", listOf("High loss to follow-up", "Recall Bias and Selection Bias", "Extremely high cost", "Inability to study rare diseases"), 1),
            Triple("Cross-Sectional study design measures exposure and outcome:", listOf("Over 20 prospective years", "Simultaneously at a single point in time (prevalence study)", "Retrospectively over lifetime", "In laboratory dishes"), 1),
            Triple("Confounding variable in epidemiology is a variable that is associated with both the exposure and outcome, leading to:", listOf("Exact true risk measurement", "Distortion / bias in the estimated association between exposure and disease", "Improved study validity", "Reduced sample size"), 1),
            Triple("Selection Bias occurs when study participants selected are:", listOf("Chosen purely at random", "Not representative of the target population, distorting study results", "Blinded to treatment", "Fully compliant"), 1),
            Triple("Blinding (Single / Double / Triple) in clinical trials is used to minimize:", listOf("Sample size requirement", "Observer / Researcher and Participant Bias", "Financial budget", "Data entry speed"), 1),
            Triple("In a Double-Blind trial:", listOf("Only participant is unaware", "BOTH participant and researcher/evaluator are unaware of treatment assignment", "Only doctor knows", "Everyone knows"), 1),
            Triple("Sensitivity of a diagnostic screening test measures the proportion of actual diseased individuals who test:", listOf("Negative (false negative)", "POSITIVE (True Positive Rate)", "Inconclusive", "Normal"), 1),
            Triple("Specificity of a screening test measures the proportion of non-diseased individuals who test:", listOf("Positive", "NEGATIVE (True Negative Rate)", "Abnormal", "High"), 1),
            Triple("A screening test with high Sensitivity is most valuable when used for:", listOf("Confirming diagnosis", "Initial screening to rule OUT disease (SNOUT: Sensitive test when Negative rules OUT)", "Determining cost", "Evaluating treatment"), 1),
            Triple("Positive Predictive Value (PPV) of a test is probability that a person with a positive test result:", listOf("Does NOT have disease", "ACTUALLY has the disease", "Will die", "Needs surgery"), 1),
            Triple("Disease surveillance system where healthcare providers routinely submit standardized reports to health authorities is:", listOf("Active Surveillance", "Passive Surveillance", "Sentinel Surveillance", "Syndromic Surveillance"), 1),
            Triple("Active Surveillance involves health department staff:", listOf("Waiting for weekly mail", "Directly contacting clinics, hospitals, and labs to seek out un-reported cases", "Reading news", "Conducting surveys every 10 years"), 1),
            Triple("Expanded Program on Immunization (EPI) Pakistan routine schedule protects infants against how many vaccine-preventable diseases?", listOf("5 diseases", "12 vaccine-preventable diseases (including Polio, TB, Measles, Hepatitis B, DTP, Hib, Pneumococcal, Rotavirus, Typhoid)", "3 diseases", "20 diseases"), 1),
            Triple("EPI vaccine stored at coldest temperature requirement (-15°C to -25°C in freezer chamber) is:", listOf("Tetanus Toxoid (TT)", "Oral Polio Vaccine (OPV)", "Hepatitis B", "Pentavalent"), 1),
            Triple("EPI cold chain temperature range required for main vaccine storage compartment (+2°C to +8°C) includes vaccines such as:", listOf("OPV only", "Pentavalent, Measles, Pneumococcal, BCG, Rotavirus, TT", "Dry ice only", "Room temp 25°C"), 1),
            Triple("Freezing must be strictly avoided for which cold chain sensitive vaccines to prevent destruction?", listOf("OPV", "Pentavalent, Hepatitis B, and Tetanus Toxoid (TT / Td)", "Measles", "BCG reconstituted"), 1),
            Triple("Cold Chain breach line item indicator on freeze-sensitive vaccine vials that permanently changes color if frozen is the:", listOf("Vaccine Vial Monitor (VVM)", "Freeze-Tag / Shake Test", "Thermometer dial", "Expiration label"), 1),
            Triple("Vaccine Vial Monitor (VVM) stage where inner square matches or is darker than outer circle indicates the vaccine is:", listOf("Usable and safe", "EXPIRED / heat-damaged and must NOT be used", "Coldest quality", "Ready for reconstitution"), 1),
            Triple("Quarantine period duration for an exposed individual is based on the maximum known:", listOf("Cure time", "Incubation Period of the infectious disease", "Duration of hospital stay", "Age of patient"), 1),
            Triple("Incubation Period is defined as time interval between initial exposure to infectious agent and:", listOf("Complete recovery", "First appearance of clinical signs and symptoms of disease", "Death of patient", "Positive lab test"), 1)
        )
        addSubjectQs(7, "Epidemiology & Public Health Nursing", "KMU EPI-673 / Park's Textbook of Preventive & Social Medicine", epi)

        // ==========================================
        // 4. NURSING RESEARCH PROJECT & BIOSTATISTICS - 50 Qs
        // ==========================================
        val res = listOf(
            Triple("First step in the formal Scientific Nursing Research process is:", listOf("Collecting data", "Identifying and formulating the Research Problem / Topic", "Analyzing results with SPSS", "Publishing article"), 1),
            Triple("FINER criteria for evaluating a good research question stands for Feasible, Interesting, Novel, Ethical, and:", listOf("Realistic", "Relevant", "Randomized", "Reliable"), 1),
            Triple("PICO framework used to construct structured clinical research questions stands for Patient/Population, Intervention, Comparison, and:", listOf("Operation", "Outcome", "Observation", "Option"), 1),
            Triple("In PICO framework, the 'C' represents:", listOf("Cause", "Comparison intervention or control group", "Cost", "Clinical setting"), 1),
            Triple("Literature Review primary purpose in nursing research is to:", listOf("Copy text word for word", "Synthesize existing knowledge, identify gaps, and establish theoretical framework for study", "Fill page count", "Avoid ethical review"), 1),
            Triple("Operational Definition of a variable specifies:", listOf("Dictionary definition", "How the variable will be precisely observed, measured, or manipulated in the study", "Historical meaning", "Author's personal view"), 1),
            Triple("Scale of measurement categorized into named groups without intrinsic numerical order or ranking is:", listOf("Ordinal scale", "Nominal scale (e.g. Gender, Blood Group, Marital Status)", "Interval scale", "Ratio scale"), 1),
            Triple("Ordinal scale of measurement differs from nominal because ordinal data has:", listOf("Equal intervals", "Logical ordered ranking (e.g. Pain intensity: Mild, Moderate, Severe)", "Absolute zero point", "Metric weight"), 1),
            Triple("Ratio scale of measurement possesses all properties of interval scale PLUS a true:", listOf("Negative value", "Absolute True Zero point (e.g. Weight, Height, Blood Pressure)", "Subjective rank", "Non-numeric name"), 1),
            Triple("Mean is defined as the:", listOf("Middlemost value in ordered dataset", "Arithmetic average calculated by dividing sum of values by total number of observations", "Most frequently occurring value", "Difference between high and low"), 1),
            Triple("Median is defined as the value that divides an ordered dataset into:", listOf("Ten equal parts", "Two equal halves (50th percentile / middle value)", "Four quarters", "Average score"), 1),
            Triple("Mode is defined as the value in a dataset that occurs with the:", listOf("Lowest frequency", "Highest frequency / most common score", "Average weight", "Middle position"), 1),
            Triple("In a skewed dataset with extreme outliers (e.g. income levels), the most appropriate measure of central tendency is the:", listOf("Mean", "Median (unaffected by extreme values)", "Mode", "Standard Deviation"), 1),
            Triple("Standard Deviation (SD) measures the extent of:", listOf("Central score", "Dispersion / variability of data values around the mean", "Sample size error", "P-value significance"), 1),
            Triple("In a Normal Distribution curve (bell-shaped curve), approximately what percentage of data falls within +/- 1 Standard Deviation from mean?", listOf("50%", "68.2%", "95.4%", "99.7%"), 1),
            Triple("Percentage of data falling within +/- 2 Standard Deviations from the mean in a normal distribution curve is approximately:", listOf("68%", "95.4%", "99.7%", "50%"), 1),
            Triple("Null Hypothesis (H0) rejection decision is based on comparing calculated p-value against preset significance level (alpha = 0.05). If p < 0.05, the decision is to:", listOf("Accept null hypothesis", "REJECT the Null Hypothesis (statistically significant difference exists)", "Invalidate study", "Increase sample size"), 1),
            Triple("Parametric statistical tests (e.g. t-test, ANOVA) require data that is:", listOf("Categorical nominal", "Normally distributed with interval/ratio scale", "Non-numeric qualitative", "Small sample < 5"), 1),
            Triple("Mann-Whitney U Test is the non-parametric equivalent of which parametric statistical test?", listOf("Paired Samples t-test", "Independent Samples t-test (when normality assumption is violated)", "One-Way ANOVA", "Pearson Correlation"), 1),
            Triple("Wilcoxon Signed-Rank Test is the non-parametric alternative used for comparing:", listOf("Three independent groups", "Two related/paired groups when data is non-normally distributed or ordinal", "Nominal contingency tables", "Survival curves"), 1),
            Triple("One-Way Analysis of Variance (ANOVA) is used to compare continuous means across:", listOf("Only 1 group", "THREE or more independent groups (e.g. Satisfaction scores across 4 hospitals)", "Two paired scores", "Nominal counts"), 1),
            Triple("Chi-Square Test (x2) of Independence is a non-parametric test used to evaluate association between two:", listOf("Continuous ratio variables", "Categorical / Nominal variables (e.g. Gender vs Smoking status)", "Means", "Standard deviations"), 1),
            Triple("Pearson Correlation Coefficient (r) measures the strength and direction of linear relationship between two:", listOf("Categorical variables", "Continuous quantitative variables", "Qualitative themes", "Median ranks"), 1),
            Triple("Cronbach's Alpha coefficient is used to assess the internal consistency reliability of a multi-item questionnaire. Acceptable minimum value for research is:", listOf("0.20", "0.70 (or 0.80 for high reliability)", "0.05", "1.50"), 1),
            Triple("Content Validity of a research questionnaire is established by submitting the instrument for evaluation to a panel of:", listOf("General public", "Subject Matter Experts (SME) in the research field", "Typists", "Students"), 1),
            Triple("Construct Validity evaluates whether a research instrument accurately measures the underlying:", listOf("Spelling correctness", "Theoretical construct or concept it claims to measure", "Page count", "Translation speed"), 1),
            Triple("In research ethics, Institutional Review Board (IRB) or Ethics Review Committee (ERC) approval is mandatory prior to:", listOf("Publishing the final paper", "Data collection involving human subjects or patient health records", "Formulating the initial research hypothesis", "Conducting a preliminary literature search"), 1),
            Triple("In qualitative research, 'Triangulation' refers to using:", listOf("Three researchers only", "Multiple data sources, methods, or investigators to enhance trustworthiness and validity of findings", "Triangular graphs", "Three questions"), 1),
            Triple("Qualitative research trustworthiness criterion corresponding to 'Internal Validity' in quantitative research is:", listOf("Transferability", "Credibility (member checking, prolonged engagement)", "Dependability", "Confirmability"), 1),
            Triple("Qualitative trustworthiness criterion corresponding to 'External Validity / Generalizability' is:", listOf("Credibility", "Transferability (thick description of context)", "Audit trail", "Reflexivity"), 1),
            Triple("Qualitative trustworthiness criterion corresponding to 'Reliability' is:", listOf("Dependability (detailed audit trail of research steps)", "P-value", "Sample power", "Blinding"), 0),
            Triple("Qualitative trustworthiness criterion 'Confirmability' ensures that study findings are rooted directly in participant data rather than researcher's:", listOf("SPSS code", "Personal bias or theoretical preferences", "Funding source", "Library sources"), 1),
            Triple("Reflexivity in qualitative nursing research requires the researcher to continually self-examine and record their own:", listOf("Financial expenses", "Biases, assumptions, and personal impact on the research process", "Working hours", "Typing speed"), 1),
            Triple("Plagiarism in academic research writing is defined as:", listOf("Citing authors accurately", "Using someone else's work, ideas, or words without proper acknowledgment or citation", "Translating languages", "Co-authoring paper"), 1),
            Triple("In APA 7th edition referencing style, an in-text citation for a work by two authors appears as:", listOf("(Khan & Ahmed, 2023)", "(Khan et al., 2023)", "(Khan and Ahmed 2023 no brackets)", "(Khan 2023)"), 0),
            Triple("In APA 7th edition, in-text citation for a work by THREE or more authors from first citation uses:", listOf("(Khan, Ahmed & Shah, 2023)", "(Khan et al., 2023)", "(Khan & co, 2023)", "(Khan et al. no year)"), 1),
            Triple("Systematic Literature Review differs from narrative review because systematic review follows a:", listOf("Random search pattern", "Rigorous, predefined, transparent protocol for searching, appraising, and synthesizing all relevant evidence", "Single book opinion", "Short 1-page summary"), 1),
            Triple("Meta-Analysis is a quantitative research synthesis method that combines statistical results from multiple independent studies to calculate an overall:", listOf("P-value only", "Pooled Effect Size", "Sample variance", "Word count"), 1),
            Triple("PRISMA statement flow diagram is used globally to report study selection steps in:", listOf("Randomized trials", "Systematic Reviews and Meta-Analyses", "Case reports", "Qualitative interviews"), 1),
            Triple("Hawthorne Effect in research participants occurs when subjects:", listOf("Drop out of study", "Alter or improve their behavior simply because they know they are being observed in a study", "Refuse consent", "Report false pain"), 1),
            Triple("Rosenthal Effect (Pygmalion Effect / Experimenter Bias) occurs when researcher expectations unconsciously:", listOf("Reduce sample size", "Influence participant responses or outcome measurements in direction of hypothesis", "Speed up publication", "Lower costs"), 1),
            Triple("Likert Scale commonly used in nursing research questionnaires measures participant attitudes on a scale ranging typically from:", listOf("1 to 100", "Strongly Disagree (1) to Strongly Agree (5)", "Yes / No only", "True / False"), 1),
            Triple("Frequency Distribution table organizes raw quantitative data by displaying values alongside their corresponding:", listOf("P-values", "Frequencies (counts) and percentages", "Standard errors", "Mean ranks"), 1),
            Triple("Bar Chart is best suited for visually displaying data that is:", listOf("Continuous interval/ratio", "Discrete Categorical / Nominal variables (with spaces between bars)", "Correlational linear", "Time-series continuous"), 1),
            Triple("Histogram is best suited for visually displaying distribution of data that is:", listOf("Discrete categories", "Continuous quantitative data (bars touch each other without spaces)", "Qualitative quotes", "References"), 1),
            Triple("Boxplot (Box and Whisker Plot) visually displays dataset summary including median, quartiles (IQR), and potential:", listOf("Sample size", "Outliers", "P-values", "Mean scores"), 1),
            Triple("Standard Error of the Mean (SEM) quantifies how much the sample mean is expected to vary from the true:", listOf("Sample median", "Population Mean", "Standard deviation", "Variance"), 1),
            Triple("Confidence Interval (95% CI) provides an estimated range of values likely to contain the true:", listOf("Sample size", "Population Parameter with 95% certainty", "P-value threshold", "Outlier count"), 1),
            Triple("In SPSS software, the variable view column specifying decimal places, labels, and measurement level (Scale, Ordinal, Nominal) is the:", listOf("Data View", "Variable View", "Output Window", "Syntax Window"), 1),
            Triple("Primary ethical requirement prior to submitting a nursing research proposal for funding or data collection is obtaining formal approval from the institution's:", listOf("Financial Bank", "Ethics Review Committee (ERC) / Institutional Review Board (IRB)", "Student Union", "Local Police Station"), 1)
        )
        addSubjectQs(7, "Nursing Research Project & Biostatistics", "KMU RES-674 / Polit & Beck Nursing Research", res)

        // Add 100 extra questions for EACH subject in Semester 7 (400 extra questions)
        questions.addAll(KpSemester7PlusQuestionBank.getQuestions(idCounter))

        return questions
    }
}
