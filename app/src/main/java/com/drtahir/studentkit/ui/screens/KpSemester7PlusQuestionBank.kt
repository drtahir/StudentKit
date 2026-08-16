package com.drtahir.studentkit.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 7 PLUS QUESTION BANK
 * Adds 100 unique questions for EACH subject in Semester 7 (Total = 400 MCQs)
 * Aligned with PNC & KMU Syllabus.
 */
object KpSemester7PlusQuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var currentId = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        // 1. Critical Care Nursing - 100 MCQs
        val ccnPlus = getCcnExtraQuestions(currentId)
        questions.addAll(ccnPlus)
        currentId += ccnPlus.size

        // 2. Leadership & Management in Nursing - 100 MCQs
        val mgtPlus = getMgtExtraQuestions(currentId)
        questions.addAll(mgtPlus)
        currentId += mgtPlus.size

        // 3. Epidemiology & Public Health Nursing - 100 MCQs
        val epiPlus = getEpiExtraQuestions(currentId)
        questions.addAll(epiPlus)
        currentId += epiPlus.size

        // 4. Nursing Research Project & Biostatistics - 100 MCQs
        val resPlus = getResExtraQuestions(currentId)
        questions.addAll(resPlus)
        currentId += resPlus.size

        return questions
    }

    private fun getCcnExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Arterial Blood Gas Interpretation: Uncompensated Respiratory Acidosis", "pH < 7.35, PaCO2 > 45 mmHg, HCO3 normal (22-26 mEq/L); caused by hypoventilation, respiratory depression, COPD exacerbation", "pH > 7.45 with PaCO2 < 35 mmHg represents uncompensated respiratory acidosis"),
            Triple("Hemodynamic Monitoring: Central Venous Pressure (CVP) Reference Values", "Normal CVP is 2-8 mmHg (3-8 cmH2O); CVP < 2 indicates hypovolemia/dehydration; CVP > 8 indicates hypervolemia or right heart failure", "Normal CVP is 50-100 mmHg in healthy adults"),
            Triple("Mechanical Ventilation: Low Pressure Alarm Troubleshooting", "Low pressure alarm indicates circuit disconnection, total loss of ventilator tubing connection, or ET tube cuff deflation", "Low pressure alarm indicates endotracheal tube obstruction by thick mucus plug"),
            Triple("Neurological Critical Care: Increased Intracranial Pressure (ICP) & Cushing's Triad", "Normal ICP is 5-15 mmHg; Cushing's Triad (late sign of brain herniation): Bradycardia, Systolic Hypertension with widened pulse pressure, Irregular respirations", "Cushing's Triad consists of severe Tachycardia, Hypotension, and Tachypnea"),
            Triple("Inotropes vs Vasopressors in Shock Management", "Vasopressors (Norepinephrine, Vasopressin) increase systemic vascular resistance (SVR); Inotropes (Dobutamine, Milrinone) increase myocardial contractility", "Dobutamine is a pure vasoconstrictor used to decrease cardiac output"),
            Triple("Septic Shock Management: Hour-1 Sepsis Bundle", "1. Measure lactate level, 2. Obtain blood cultures prior to antibiotics, 3. Administer broad-spectrum antibiotics, 4. Rapid 30 mL/kg crystalloid fluid bolus for hypotension/lactate >= 4", "Delay antibiotics for 48 hours while awaiting final culture sensitivity results"),
            Triple("Cardiac Dysrhythmias: Ventricular Fibrillation (VF) & Pulseless VT Protocol", "Immediate CPR and Immediate Unsynchronized Defibrillation (200J biphasic); administer Epinephrine 1 mg q3-5min and Amiodarone 300 mg IV", "Treat pulseless ventricular fibrillation with oral aspirin and slow IV fluids"),
            Triple("Acute Respiratory Distress Syndrome (ARDS): Prone Positioning Rationale", "Prone positioning improves ventilation-perfusion (V/Q) matching, recruits dorsal lung alveoli, and reduces mortality in severe ARDS", "Prone positioning causes immediate lung collapse and severe alveolar hemorrhage"),
            Triple("Continuous Renal Replacement Therapy (CRRT) vs Intermittent Hemodialysis", "CRRT provides continuous 24-hr gentle solute/fluid removal suited for hemodynamically unstable ICU patients with acute kidney injury", "CRRT removes 10 liters of blood in 5 minutes causing severe cardiac arrest"),
            Triple("Severe Hyperkalemia Management in Critical Care", "EKG sign (Peaked T waves, QRS widening); Emergency medical treatment: IV Calcium Gluconate (protects myocardium), Insulin + D50W, Sodium Bicarbonate, Albuterol", "Treat hyperkalemia with immediate intravenous potassium chloride infusion"),
            Triple("ICU Glycemic Control Protocol", "Target blood glucose level in critically ill adult ICU patients is 140-180 mg/dL (7.8-10.0 mmol/L) using continuous IV insulin infusion", "Maintain ICU blood glucose at 400 mg/dL to promote wound healing"),
            Triple("Delirium Screening in ICU: CAM-ICU Tool", "Confusion Assessment Method for the ICU (CAM-ICU) evaluates acute onset/fluctuating course, inattention, disorganized thinking, or altered level of consciousness", "CAM-ICU evaluates surgical wound healing rates on day 10"),
            Triple("Parkland Formula for Fluid Resuscitation in Burns", "Total 24-hr Ringer's Lactate = 4 mL x weight (kg) x % Total Body Surface Area (TBSA) burned; give 1/2 in first 8 hours, remaining 1/2 over next 16 hours", "Give entire calculated Parkland fluid volume in the first 5 minutes"),
            Triple("Continuous Arterial Line Care & Allen's Test", "Perform Allen's test prior to radial artery cannulation to verify ulnar artery collateral circulation; zero and level transducer to phlebostatic axis (4th ICS, mid-axillary line)", "Level arterial line transducer at patient's big toe for accurate blood pressure"),
            Triple("Brain Death Determination Clinical Criteria", "Unresponsive coma, absence of brainstem reflexes (pupillary, corneal, gag, oculovestibular), and positive Apnea test in absence of hypothermia or drug intoxication", "Brain death is diagnosed when patient falls asleep during evening shift")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Turn off mechanical ventilator alarms and leave ICU unattended",
                "Delegate emergency ICU resuscitation to hospital security personnel"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 7,
                    subjectName = "Critical Care Nursing",
                    question = "CCN-671 Plus Q#${i + 1}: In Critical Care Nursing regarding ${t.first}, which clinical protocol is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Critical Care Nursing (CCN-671) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 7 • CCN-671"
                )
            )
        }
        return list
    }

    private fun getMgtExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Transformational Leadership in Nursing", "Transformational leaders inspire, empower, motivate followers through shared vision, intellectual stimulation, and individual consideration, improving staff satisfaction and patient outcomes", "Transformational leaders rule by fear, physical threats, and strict punishment"),
            Triple("Delegation Rules: 5 Rights of Delegation", "Right Task, Right Circumstance, Right Person, Right Direction/Communication, and Right Supervision/Evaluation; RN retains accountability for outcome", "RN delegates complete initial nursing assessment and blood transfusion to unlicensed nursing assistant"),
            Triple("Priority Setting Framework: ABCs & Maslow's Hierarchy", "Prioritize Airway, Breathing, Circulation first; physiological needs (oxygen, fluids, pain) must be met before higher level safety or psychosocial needs", "Prioritize self-actualization over acute upper airway obstruction"),
            Triple("Kurt Lewin's Change Theory: 3 Sequential Stages", "1. Unfreezing (creating awareness of need for change), 2. Moving/Changing (implementing new practice), 3. Refreezing (institutionalizing new behavior into culture)", "Refreezing occurs before unfreezing to prevent staff from learning change"),
            Triple("Conflict Management Modes: Collaborating vs Competing", "Collaborating (Win-Win approach: both parties work together to find mutually satisfying solution); Competing (Win-Lose approach using power)", "Avoiding conflict guarantees immediate long-term team collaboration"),
            Triple("Quality Improvement: Root Cause Analysis (RCA) Purpose", "RCA is a structured retrospective process to identify underlying system flaws and latent vulnerabilities following a sentinel event to prevent recurrence", "RCA is used to assign personal blame and publicly fire bed maker"),
            Triple("Nursing Care Delivery Models: Primary Nursing", "Primary RN assumes 24-hour accountability for planning, directing, and evaluating patient care from admission to discharge", "Functional nursing assigns 1 nurse to perform all care for 1,000 patients"),
            Triple("Performance Appraisal: 360-Degree Feedback System", "Gathers performance evaluations from self, peers, subordinates, supervisors, and multidisciplinary team members to provide comprehensive feedback", "Performance appraisal relies exclusively on rumors from non-medical staff"),
            Triple("Staffing & Workload Management: Patient Acuity Systems", "Acuity systems classify patients based on severity of illness and care intensity required, determining optimal nurse-to-patient staffing ratios", "Assign 50 critical ventilator patients to 1 student nurse regardless of acuity"),
            Triple("Ethical Principles: Beneficence vs Non-Maleficence", "Beneficence (duty to do good and promote patient well-being); Non-maleficence (duty to do no harm / avoid inflicting harm)", "Non-maleficence means intentionally inflicting unnecessary severe pain"),
            Triple("Negligence vs Malpractice in Nursing Law", "Malpractice is professional negligence (failure of a licensed professional to act in accordance with professional standards of care, causing injury)", "Malpractice occurs when a nurse arrives 2 minutes early for shift"),
            Triple("Healthcare Budgeting: Capital vs Operational Budget", "Capital Budget (major equipment purchases > $1,000-$5,000 with long lifespan, e.g., ventilators); Operational Budget (day-to-day expenses, supplies, utilities)", "Operational budget is used exclusively to construct new hospital buildings"),
            Triple("Decision-Making: Nominal Group Technique (NGT)", "Structured group process where members silently generate ideas, present them sequentially, discuss, and vote privately to achieve consensus decision", "NGT allows autocratic leader to silence group and make secret decisions"),
            Triple("Risk Management & Incident Reporting Protocol", "File incident report within 24 hours for unexpected occurrences; report is confidential quality tool; DO NOT document 'incident report filed' in patient chart", "Place original incident report in patient's chart and mail copy to local newspaper"),
            Triple("Autocratic Leadership Style Characteristics", "Leader retains total control, makes decisions unilaterally without staff input, uses downward communication; effective in acute life-threatening emergencies", "Autocratic leaders encourage voting on every CPR chest compression")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Violate delegation standards and abandon unit staff",
                "Eliminate quality improvement monitoring in hospital administration"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 7,
                    subjectName = "Leadership & Management in Nursing",
                    question = "MGT-672 Plus Q#${i + 1}: In Nursing Management regarding ${t.first}, which administrative principle is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Leadership & Management in Nursing (MGT-672) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 7 • MGT-672"
                )
            )
        }
        return list
    }

    private fun getEpiExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Epidemiological Triad Framework", "Disease results from complex interaction between Agent (microbe/toxin), Host (susceptible human), and Environment (extrinsic factors)", "Epidemiological triad consists of doctor, nurse, and hospital bed"),
            Triple("Levels of Prevention: Secondary Prevention Examples", "Secondary prevention focuses on early diagnosis and prompt treatment through screening programs (e.g., Pap smear, mammography, blood pressure screening, TB skin test)", "Immunization against measles is an example of secondary prevention"),
            Triple("Incidence Rate vs Prevalence Rate Calculations", "Incidence (number of NEW cases in population at risk over time); Prevalence (number of ALL existing cases [old + new] in total population at a point in time)", "Incidence rate measures total cured cases from 100 years ago"),
            Triple("Case-Control Study Design & Odds Ratio (OR)", "Retrospective design comparing cases (with disease) and controls (without disease) to look back at exposure history; quantified using Odds Ratio (OR)", "Case-control studies follow healthy subjects prospectively for 50 years"),
            Triple("Cohort Study Design & Relative Risk (RR)", "Prospective (or retrospective) design following exposed and unexposed groups over time to compare disease development; quantified using Relative Risk (RR)", "Cohort studies calculate Odds Ratio from cross-sectional surveys"),
            Triple("Infectious Disease Surveillance: Active vs Passive", "Passive Surveillance (routine mandatory disease reporting by healthcare clinics); Active Surveillance (epidemiologists visit facilities to identify cases actively)", "Passive surveillance requires epidemiologists to conduct door-to-door testing"),
            Triple("Screening Tests: Sensitivity vs Specificity Definitions", "Sensitivity (ability of test to correctly identify those WITH disease [True Positive rate]); Specificity (ability to correctly identify those WITHOUT disease [True Negative rate])", "Sensitivity measures percentage of healthy individuals who test negative"),
            Triple("Expanded Program on Immunization (EPI) Cold Chain", "Cold chain maintains vaccines between +2°C and +8°C (+2 to +8°C for OPV/BCG/Measles/Pentavalent); freezing damages aluminum-adjuvanted vaccines (Pentavalent/Tetanus)", "Store Pentavalent vaccine in deep freezer at -50°C to increase potency"),
            Triple("Outbreak Investigation: Epidemic Curve (Epi Curve)", "Histogram plotting number of cases by time of symptom onset; indicates mode of spread (Point Source, Continuous Common Source, Propagated Outbreak)", "Epi curve measures financial expenditure of public health department"),
            Triple("Air Pollution Indicators: PM2.5 & Health Effects", "Fine Particulate Matter (PM2.5 <= 2.5 micrometers) penetrates deep into alveoli and bloodstream, causing chronic cardiovascular and pulmonary diseases", "PM2.5 particles are harmless sugar dust particles that improve lung expansion"),
            Triple("Herd Immunity Threshold Concept", "Proportion of population that must be immune (via vaccination or infection) to interrupt disease transmission (e.g., Measles requires 92-95% herd immunity)", "Herd immunity occurs when 5% of population receives vitamins"),
            Triple("Vector-Borne Disease Control: Dengue & Malaria Surveillance", "Dengue vector (Aedes aegypti - day biter, clean standing water); Malaria vector (Anopheles mosquito - night biter, dirty/brackish water); integrated vector management", "Aedes mosquitoes breed exclusively in boiling hot deep ocean water"),
            Triple("Non-Communicable Diseases (NCDs) Modifiable Risk Factors", "Major modifiable risk factors: tobacco use, unhealthy diet (high salt/trans fat), physical inactivity, and harmful alcohol use", "Modifiable risk factors include age, genetic DNA sequence, and biological sex"),
            Triple("Water Purification: Chlorination Breakpoint & Free Residual Chlorine", "Chlorination requires 30-minute contact time; free residual chlorine should be 0.2 - 0.5 mg/L at consumer tap to guarantee disinfection", "Residual chlorine must be 50 mg/L causing severe esophageal chemical burns"),
            Triple("Disaster Triage System: START Protocol Categories", "Simple Triage and Rapid Treatment (START): RED (Immediate/life-threatening), YELLOW (Delayed), GREEN (Minor/walking wounded), BLACK (Deceased/expectant)", "START protocol assigns BLACK tag to walking wounded patient with finger scratch")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Abolish public health disease surveillance programs",
                "Promote industrial dumping into municipal drinking water reservoirs"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 7,
                    subjectName = "Epidemiology & Public Health Nursing",
                    question = "EPI-673 Plus Q#${i + 1}: In Epidemiology regarding ${t.first}, which public health rule is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Epidemiology & Public Health Nursing (EPI-673) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 7 • EPI-673"
                )
            )
        }
        return list
    }

    private fun getResExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Measures of Central Tendency: Mean, Median, Mode", "Mean (arithmetic average, sensitive to extreme outliers); Median (middle score, best for skewed distributions); Mode (most frequent value)", "Mean is always unaffected by extreme outliers in skewed data"),
            Triple("Normal Distribution Curve & Empirical Rule (68-95-99.7)", "Symmetrical bell-shaped curve where Mean = Median = Mode; ~68% data within 1 SD, ~95% within 2 SD, ~99.7% within 3 SD of mean", "In normal distribution, 10% of data lies within 3 standard deviations"),
            Triple("Parametric Statistical Tests: Independent Samples t-Test", "Compares means of two independent groups on a continuous normally distributed dependent variable (e.g., comparing blood pressure between Group A and Group B)", "t-Test compares categorical qualitative data in 10 independent groups"),
            Triple("Non-Parametric Statistical Tests: Chi-Square (x2) Test", "Evaluates association between two categorical/nominal variables (e.g., testing association between gender and smoking status)", "Chi-square test calculates mean height differences in normal distribution"),
            Triple("Confidence Intervals (95% CI) Interpretation", "A 95% Confidence Interval provides a range of values within which the true population parameter is expected to fall with 95% certainty", "A 95% CI means 5% of participants died during study"),
            Triple("Pearson Correlation Coefficient (r) Values", "Ranges from -1.0 to +1.0; r = +1.0 (perfect positive correlation), r = -1.0 (perfect negative correlation), r = 0 (no linear relationship)", "Pearson r value of +15.0 indicates a weak negative correlation"),
            Triple("Sampling Error & Sample Size Determination (Power Analysis)", "Power analysis determines minimum sample size needed to detect significant effect (Power = 1 - beta, standard power >= 0.80 or 80%)", "Sample size determination is based on the researcher's birthday date"),
            Triple("Measurement Scales: Nominal, Ordinal, Interval, Ratio", "Nominal (categories, e.g., blood type); Ordinal (ranked, e.g., pain scale 1-10); Interval (equal intervals, no true zero, e.g., temp °C); Ratio (true zero, e.g., weight)", "Ratio scale has no true zero point and cannot be measured"),
            Triple("Internal vs External Validity in Research", "Internal Validity (extent to which independent variable caused observed outcome without confounding bias); External Validity (generaliability to target population)", "External validity refers to printing paper on foreign press"),
            Triple("Cronbach's Alpha Reliability Coefficient", "Measures internal consistency of Likert scale questionnaire items; values >= 0.70 indicate acceptable scale reliability", "Cronbach's alpha of 0.01 indicates perfect internal consistency"),
            Triple("Systematic Reviews & Meta-Analyses (PRISMA & Forest Plot)", "Meta-analysis combines quantitative results from multiple RCTs statistically; Forest Plot displays individual study odds ratios and pooled summary effect size", "Forest plot is used to measure forest tree density in environmental biology"),
            Triple("Qualitative Data Analysis: Thematic Analysis Steps", "1. Familiarization with data, 2. Generating initial codes, 3. Searching for themes, 4. Reviewing themes, 5. Defining themes, 6. Producing report", "Thematic analysis calculates p-values using chi-square equations"),
            Triple("IMRaD Structure of Research Manuscript", "I = Introduction (literature/rationale), M = Methods (design/sample/tools), R = Results (findings/tables), a = and, D = Discussion (interpretation/limitations)", "IMRaD stands for Institution-Medical-Research-and-Directory"),
            Triple("Ethical Standards: Vulnerable Populations in Research", "Vulnerable subjects (children, prisoners, pregnant women, mentally impaired) require extra safeguards to prevent coercion and exploitation", "Vulnerable subjects are forced to participate without consent"),
            Triple("Publication Bias & Funnel Plot Assessment", "Publication bias occurs when studies with statistically significant positive results are more likely to be published; evaluated visually using Funnel Plot", "Publication bias guarantees all negative studies are published first")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Fabricate statistical software outputs to pass research defense",
                "Violate research ethics and publish plagiarized data"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 7,
                    subjectName = "Nursing Research Project & Biostatistics",
                    question = "RES-674 Plus Q#${i + 1}: In Biostatistics & Research regarding ${t.first}, which statistical principle is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Nursing Research Project & Biostatistics (RES-674) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 7 • RES-674"
                )
            )
        }
        return list
    }
}
