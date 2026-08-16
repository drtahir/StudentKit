package com.drtahir.studentkit.ui.screens

/**
 * REGIONAL KHYBER PAKHTUNKHWA (KP) BSN NURSING RESEARCH EXAMPLES & THESIS PROPOSALS
 * Real-world BSN student research proposals & thesis topics tailored for hospitals in
 * Buner (DHQ Daggar), Swat (SGTH), Dir (Timergara), Mardan (MMC), Malakand (Batkhela), etc.
 * 
 * PNC & KMU Curriculum Alignment:
 * - Semester 6 (RES-664): Introduction to Nursing Research - Proposal / Synopsis Writing (4 CH)
 * - Semester 7 (RES-674): Nursing Research Project & Biostatistics - Official Thesis Execution & Defense (3 CH)
 * - Semester 8: Senior Clinical Internship Practicum (No separate research course; project completed in Sem 7).
 */
data class KpNursingResearchTopic(
    val id: Int,
    val title: String,
    val location: String, // Hospital & District setting
    val regionTag: String, // e.g. "Buner", "Swat", "Dir", "Mardan", "Malakand", "Peshawar"
    val category: String, // e.g. "Infection Control", "Maternal Health", "Pediatrics", "Critical Care", "Public Health"
    val semesterRequirement: String = "Semester 7 (RES-674 Final Thesis)", // PNC/KMU Curriculum designation
    val targetPopulation: String,
    val sampleSize: String,
    val studyDesign: String,
    val keyObjectives: List<String>,
    val methodology: String,
    val dataCollectionTool: String,
    val statisticalAnalysisTool: String = "IBM SPSS Statistics v26 (Chi-Square test & Descriptive Frequencies, p < 0.05)",
    val chapterOutline: List<String> = listOf(
        "Chapter 1: Introduction, Problem Statement & Study Rationale",
        "Chapter 2: Review of Literature & Conceptual Framework",
        "Chapter 3: Research Methodology, Sampling & Tool Validation",
        "Chapter 4: Data Analysis, SPSS Tables & Graphical Outcomes",
        "Chapter 5: Discussion, Clinical Recommendations & Viva Q&A"
    ),
    val expectedFindings: List<String>,
    val clinicalRecommendations: String,
    val supervisorNote: String
)

object KpResearchExamplesRepository {

    fun getResearchExamples(): List<KpNursingResearchTopic> {
        return listOf(
            KpNursingResearchTopic(
                id = 1,
                title = "Assessment of Infection Control Practices & Hand Hygiene Compliance Among Nurses at DHQ Hospital Daggar, District Buner",
                location = "DHQ Hospital Daggar, District Buner (Secondary Healthcare Facility)",
                regionTag = "Buner",
                category = "Infection Control & Patient Safety",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Staff Nurses, ICU Nurses, and Surgical Ward Nursing Staff at DHQ Daggar",
                sampleSize = "N = 85 Registered Nurses",
                studyDesign = "Hospital-based Quantitative Cross-Sectional Descriptive Study",
                keyObjectives = listOf(
                    "Evaluate baseline hand hygiene compliance rates according to WHO 5 Moments for Hand Hygiene.",
                    "Assess nurse knowledge regarding standard precautions, aseptic technique, and sharp disposal.",
                    "Identify institutional barriers to infection control (water availability, hand rub supplies, nurse workload)."
                ),
                methodology = "Combination of structured self-administered knowledge questionnaire and direct covert observational audits during medication administration and wound dressing changes across medical, surgical, and ICU wards.",
                dataCollectionTool = "WHO Hand Hygiene Observation Tool & Standardized Infection Control Knowledge Questionnaire (25 items).",
                statisticalAnalysisTool = "IBM SPSS v26: Chi-Square (χ²) test to analyze association between nurse experience and compliance; Fisher's exact test for ward-wise comparisons (p < 0.05).",
                expectedFindings = listOf(
                    "Overall hand hygiene compliance rate observed was 52.4%, significantly higher before sterile procedures than after touching patient surroundings.",
                    "Knowledge score averaged 78%, showing strong theoretical awareness but gap in practical compliance during high workload shifts.",
                    "Primary reported barriers included lack of alcohol-based hand rub at bedside (64%) and heavy patient-to-nurse ratio (1:18 in general wards)."
                ),
                clinicalRecommendations = "Install wall-mounted alcohol hand rub dispensers at every bed in DHQ Daggar, conduct bi-monthly infection control refresher workshops, and designate ward-level Infection Control Champion nurses.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). High clinical impact for District Buner hospital safety standards."
            ),
            KpNursingResearchTopic(
                id = 2,
                title = "Prevalence and Predictors of Occupational Burnout and Compassion Fatigue Among Critical Care Nurses at Saidu Group of Teaching Hospitals (SGTH), Swat",
                location = "Saidu Group of Teaching Hospitals (SGTH) Saidu Sharif, District Swat (Tertiary Care Teaching Hospital)",
                regionTag = "Swat",
                category = "Nursing Management & Mental Health",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "ICU, CCU, Emergency, and Pediatric ICU Staff Nurses at SGTH Swat",
                sampleSize = "N = 120 Critical Care Nurses",
                studyDesign = "Analytical Cross-Sectional Quantitative Study",
                keyObjectives = listOf(
                    "Determine the prevalence of Emotional Exhaustion, Depersonalization, and reduced Personal Accomplishment among ICU nurses.",
                    "Examine correlation between weekly working hours, night shifts, nurse-patient ratios, and burnout scores.",
                    "Identify coping mechanisms utilized by nurses in high-stress tertiary care environments in Malakand Division."
                ),
                methodology = "Purposive sampling of staff nurses working in SGTH critical care units for > 6 months. Self-administered standardized survey distributed during shift handovers.",
                dataCollectionTool = "Maslach Burnout Inventory - Human Services Survey (MBI-HSS) & Professional Quality of Life Scale (ProQOL).",
                statisticalAnalysisTool = "IBM SPSS v26: Pearson Correlation (r) & Multiple Linear Regression to determine predictors of Emotional Exhaustion (p < 0.01).",
                expectedFindings = listOf(
                    "High level of Emotional Exhaustion detected in 61.5% of critical care nurses, with 48% reporting moderate-to-severe Compassion Fatigue.",
                    "Significant positive correlation (p < 0.01) between consecutive 12-hour night shifts and high depersonalization scores.",
                    "Nurses caring for 3+ ventilated patients simultaneously demonstrated double the burnout risk compared to 1:1 staffing."
                ),
                clinicalRecommendations = "Implement mandatory shift rotation limits (max 3 consecutive night shifts), establish psychological debriefing rooms at SGTH Swat, and advocate for PNC nurse staffing ratio compliance.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Crucial research for SGTH Swat critical care administration."
            ),
            KpNursingResearchTopic(
                id = 3,
                title = "Knowledge, Attitudes, and Clinical Management Barriers Regarding Postpartum Hemorrhage (PPH) Among Midwives and Maternity Nurses in DHQ Hospital Timergara, District Dir",
                location = "DHQ Hospital Timergara, Lower Dir & DHQ Hospital Upper Dir (Maternity & Labor Wards)",
                regionTag = "Dir",
                category = "Obstetrics & Gynecological Nursing",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Staff Nurses, Community Midwives (CMWs), and LHVs working in Labor Wards and Postnatal Wards in District Dir",
                sampleSize = "N = 95 Labor Ward Nursing Staff",
                studyDesign = "Descriptive Cross-Sectional Mixed-Methods Study",
                keyObjectives = listOf(
                    "Assess nurse knowledge regarding early PPH identification, fundal massage, and active management of third stage of labor (AMTSL).",
                    "Evaluate correct drug dosage and administration protocol for Oxytocin, Misoprostol, and Tranexamic Acid (TXA).",
                    "Identify facility-level delays in emergency blood transfusion and referral transport in rural Dir."
                ),
                methodology = "Quantitative knowledge questionnaire combined with 3 qualitative Focus Group Discussions (FGDs) with labor ward charge nurses.",
                dataCollectionTool = "WHO PPH Clinical Knowledge Survey & Qualitative FGD Topic Guide.",
                statisticalAnalysisTool = "IBM SPSS v26 for quantitative KAP data; Thematic Analysis using NVivo 12 for qualitative transcripts.",
                expectedFindings = listOf(
                    "88% of nurses correctly identified Oxytocin 10 IU IM as 1st-line drug for AMTSL, but only 42% knew correct IV Tranexamic Acid (1g within 3 hours) timing.",
                    "73% reported delays in obtaining cross-matched blood from blood banks during emergency night shifts.",
                    "Qualitative themes highlighted severe shortage of non-pneumatic anti-shock garments (NASG) in peripheral rural clinics."
                ),
                clinicalRecommendations = "Conduct simulation-based PPH drills in DHQ Timergara labor wards, establish emergency blood donor registries, and ensure 24/7 availability of TXA and Misoprostol in all maternity kits.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Directly addresses maternal mortality prevention in District Dir."
            ),
            KpNursingResearchTopic(
                id = 4,
                title = "Impact of Nurse-Led Diabetes Self-Management Education (DSME) on Glycemic Control (HbA1c) Among Type 2 Diabetes Patients at Mardan Medical Complex (MMC)",
                location = "Mardan Medical Complex (MMC) Teaching Hospital, District Mardan (Outpatient Endocrinology Clinic)",
                regionTag = "Mardan",
                category = "Adult Health Nursing & Chronic Care",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Adult Type 2 Diabetes Mellitus patients registered at MMC Mardan Outpatient Clinic",
                sampleSize = "N = 100 Patients (50 Intervention Group vs 50 Control Group)",
                studyDesign = "Quasi-Experimental Pre-Test / Post-Test Study with Control Group",
                keyObjectives = listOf(
                    "Develop and deliver a Pashto-language nurse-led Diabetes Self-Management Education (DSME) module.",
                    "Compare baseline and 3-month post-intervention HbA1c levels, fasting blood glucose, and BMI between groups.",
                    "Evaluate improvements in insulin injection technique, foot care practices, and dietary compliance."
                ),
                methodology = "Intervention group received 4 weekly 45-minute interactive Pashto education sessions on dietary management, insulin administration, hypoglycemia recognition, and daily foot checks. Control group received standard brief doctor consultation.",
                dataCollectionTool = "Summary of Diabetes Self-Care Activities (SDSCA) Questionnaire & Laboratory HbA1c blood test measurements at baseline and 12 weeks.",
                statisticalAnalysisTool = "IBM SPSS v26: Paired t-test (within group baseline vs 12 weeks) and Independent Samples t-test (Intervention vs Control group, p < 0.001).",
                expectedFindings = listOf(
                    "Intervention group achieved a statistically significant mean reduction in HbA1c of 1.2% (p < 0.001) at 12 weeks compared to 0.1% in control group.",
                    "Diabetic foot care compliance increased from 24% to 81% in the educated patient group.",
                    "Hypoglycemic episode awareness improved markedly, reducing emergency ER visits at MMC."
                ),
                clinicalRecommendations = "Establish a permanent Nurse-Led Diabetes Educator Clinic at MMC Mardan and integrate Pashto visual pictorial brochures for low-literacy rural patients.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Exemplary quasi-experimental clinical research model for MMC Mardan."
            ),
            KpNursingResearchTopic(
                id = 5,
                title = "Evaluation of Pediatric Malnutrition Screening and Nurse-Led Nutritional Counseling in Rural Primary Healthcare Centers of District Malakand",
                location = "DHQ Hospital Batkhela, Dargai Tehsil Hospital & Rural BHUs in District Malakand",
                regionTag = "Malakand",
                category = "Pediatric & Community Health Nursing",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Mothers of children aged 6 to 59 months visiting Outpatient Pediatric & Immunization Clinics in Malakand",
                sampleSize = "N = 150 Mother-Child Pairs",
                studyDesign = "Community-based Cross-Sectional Survey & Observational Practice Study",
                keyObjectives = listOf(
                    "Determine prevalence of Severe Acute Malnutrition (SAM) and Moderate Acute Malnutrition (MAM) using Mid-Upper Arm Circumference (MUAC) and Z-scores.",
                    "Evaluate maternal complementary feeding practices, hygiene, and weaning age in rural Malakand.",
                    "Assess pediatric triage nurse proficiency in screening with MUAC tapes and administering Ready-to-Use Therapeutic Food (RUTF)."
                ),
                methodology = "Nurses measured MUAC, weight, and height of presenting children, categorized nutritional status, and administered a structured interview questionnaire to mothers on infant feeding practices.",
                dataCollectionTool = "WHO Anthropometric Growth Standard Charts, MUAC Tapes, and Infant & Young Child Feeding (IYCF) Survey Tool.",
                statisticalAnalysisTool = "IBM SPSS v26 & WHO Anthro Software v3.2.2: Mann-Whitney U test & Binary Logistic Regression for malnutrition risk factors.",
                expectedFindings = listOf(
                    "Prevalence of wasting (SAM + MAM) detected was 18.6%, with highest rate in infants aged 6-12 months during weaning initiation.",
                    "Only 38% of mothers practiced exclusive breastfeeding for 6 months due to early introduction of buffalo milk or tea.",
                    "Nurses accurately identified SAM cases but faced RUTF supply stockouts at 42% of peripheral health units."
                ),
                clinicalRecommendations = "Strengthen Malakand district RUTF supply chain management, establish Community Stabilization Centers at DHQ Batkhela, and conduct village-level nurse-led IYCF counseling sessions.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Directly addresses child health SDGs in District Malakand."
            ),
            KpNursingResearchTopic(
                id = 6,
                title = "Assessment of Triage Accuracy and Workload Stressors Among Emergency Department Nurses in Tertiary Care Hospitals of Peshawar",
                location = "Lady Reading Hospital (LRH) & Khyber Teaching Hospital (KTH), Peshawar (Emergency Wards)",
                regionTag = "Peshawar",
                category = "Emergency & Critical Care Nursing",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Triage Nurses and Emergency Room Charge Nurses at LRH and KTH Peshawar",
                sampleSize = "N = 110 ER Nursing Staff",
                studyDesign = "Descriptive Cross-Sectional Study with Observational Simulation",
                keyObjectives = listOf(
                    "Assess nurse accuracy in assigning correct Emergency Severity Index (ESI Level 1 to 5) triage levels to standardized clinical patient vignettes.",
                    "Measure average patient door-to-triage assessment time in high-volume ERs.",
                    "Identify impact of ER overcrowding and patient attendant overcrowding on triage decision accuracy."
                ),
                methodology = "10 standardized emergency clinical case scenarios administered to ER nurses to test triage acuity assignment accuracy, combined with observational timing of live patient entry.",
                dataCollectionTool = "Emergency Severity Index (ESI) Triage Algorithm Validation Tool & ER Workload Stress Index Questionnaire.",
                statisticalAnalysisTool = "IBM SPSS v26: One-Way ANOVA to compare triage accuracy across nurse shifts; Cohen's Kappa (κ = 0.72) for inter-rater reliability.",
                expectedFindings = listOf(
                    "Overall correct ESI triage category assignment accuracy was 74%, with under-triage occurring in 18% of pediatric respiratory distress vignettes.",
                    "Average door-to-triage time was 4.2 minutes during morning shifts but exceeded 14 minutes during peak evening crowd hours.",
                    "92% of nurses reported verbal aggression from patient attendants as the major factor causing cognitive distraction during triage."
                ),
                clinicalRecommendations = "Implement mandatory ESI Triage Certification workshops for all LRH/KTH ER staff, enforce strict '1 Attendant Per Patient' ER gate security policies, and deploy electronic triage scoring kiosks.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Essential operational research for Peshawar tertiary emergency services."
            ),
            KpNursingResearchTopic(
                id = 7,
                title = "Knowledge, Compliance, and Under-Reporting of Needle-Stick and Sharps Injuries Among Surgical Ward Nurses at Ayub Teaching Hospital (ATH), Abbottabad",
                location = "Ayub Teaching Hospital (ATH), Abbottabad, Hazara Division",
                regionTag = "Hazara / Abbottabad",
                category = "Occupational Health & Nursing Safety",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Staff Nurses and Nursing Interns in Operating Theaters and Surgical Wards at ATH Abbottabad",
                sampleSize = "N = 130 Surgical Wards Nursing Staff",
                studyDesign = "Descriptive Cross-Sectional Epidemiological Survey",
                keyObjectives = listOf(
                    "Determine 12-month incidence rate of needle-stick injuries (NSIs) and sharp exposures among surgical nurses.",
                    "Evaluate needle recapping habits and compliance with Post-Exposure Prophylaxis (PEP) protocols.",
                    "Analyze reasons for under-reporting NSIs to the hospital administration."
                ),
                methodology = "Self-administered anonymous questionnaire inquiring about past 12 months NSI events, context of injury (recapping, disposal, surgical assistance), Hepatitis B vaccination status, and reporting history.",
                dataCollectionTool = "CDC Sharps Injury Surveillance Tool & EPINet Occupational Exposure Questionnaire.",
                statisticalAnalysisTool = "IBM SPSS v26: Chi-Square test & Odds Ratios (OR = 3.4) for two-handed recapping vs safety disposal box proximity.",
                expectedFindings = listOf(
                    "41.5% of nurses experienced at least one needle-stick injury in the preceding 12 months, with highest frequency occurring during two-handed needle recapping and IV cannulation.",
                    "64% of injured nurses did NOT report the injury to hospital employee health services due to fear of reprimand or belief that reporting was cumbersome.",
                    "89% had received complete 3-dose Hepatitis B vaccination, but only 32% had post-vaccination anti-HBs titer verification."
                ),
                clinicalRecommendations = "Enforce hands-free neutral zone sharps passing in ATH operating rooms, transition to safety-engineered IV cannulas with retractable needles, and establish a confidential 1-click NSI reporting hotline.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Crucial occupational safety research for Hazara Division nursing staff."
            ),
            KpNursingResearchTopic(
                id = 8,
                title = "Evaluation of Neonatal Hypothermia Prevention Practices in Labor Wards and NICU at DHQ Hospital KDA Kohat",
                location = "DHQ Hospital KDA Kohat & District Headquarter Hospital Kohat (Maternity & NICU)",
                regionTag = "Kohat",
                category = "Neonatal & Pediatric Nursing",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Neonatal Staff Nurses, Midwives, and Labor Room Nursing In-Charges in Kohat",
                sampleSize = "N = 75 Neonatal & Labor Ward Nurses",
                studyDesign = "Observational Descriptive & Knowledge Assessment Study",
                keyObjectives = listOf(
                    "Measure admission temperature of newborns arriving in NICU and calculate prevalence of neonatal hypothermia (< 36.5°C).",
                    "Audit nursing adherence to WHO Warm Chain recommendations (immediate drying, skin-to-skin contact, delayed bathing).",
                    "Assess nurse competency in Kangaroo Mother Care (KMC) for low birth weight infants."
                ),
                methodology = "Digital axillary temperature recorded for all neonates within 30 minutes of birth and upon NICU admission over 8 weeks, coupled with nursing checklist audits during delivery.",
                dataCollectionTool = "WHO Warm Chain Assessment Checklist & Neonatal Hypothermia Clinical Data Sheet.",
                statisticalAnalysisTool = "IBM SPSS v26: Independent t-test comparing NICU admission temperatures between newborns receiving immediate skin-to-skin vs standard care (p < 0.005).",
                expectedFindings = listOf(
                    "Incidence of neonatal hypothermia upon NICU arrival was 53.2%, higher in winter months and among preterm/low birth weight infants.",
                    "Primary gap identified was delayed drying of newborn (taking > 2 minutes) and early bathing within first 6 hours post-birth due to maternal family insistence.",
                    "Nurses demonstrated high knowledge of KMC (82%), but practical KMC implementation was hindered by lack of dedicated private KMC beds."
                ),
                clinicalRecommendations = "Establish radiant warmers in every delivery bay at DHQ Kohat, enforce strict 24-hour delayed bathing policy, create a 4-bed Kangaroo Mother Care unit, and educate families on thermal protection.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Actionable research for improving neonatal survival in District Kohat."
            ),
            KpNursingResearchTopic(
                id = 9,
                title = "Community Knowledge, Beliefs, and Acceptance of Childhood Immunization in Rural Union Councils of District Charsadda: A Nurse-Led Public Health Survey",
                location = "Rural Basic Health Units (BHUs) & Community Households in District Charsadda",
                regionTag = "Charsadda",
                category = "Community Health & Public Health Nursing",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Parents / Primary Caregivers of infants aged 0-23 months in District Charsadda",
                sampleSize = "N = 200 Households",
                studyDesign = "Community-based Quantitative Cross-Sectional Survey",
                keyObjectives = listOf(
                    "Measure fully immunized child (FIC) coverage rate according to EPI schedule in rural Charsadda.",
                    "Identify major socio-cultural factors and vaccine hesitancy reasons among parents.",
                    "Evaluate the impact of Lady Health Worker (LHW) home visits on vaccine completion rates."
                ),
                methodology = "Cluster sampling across 10 rural Union Councils of Charsadda. Public Health Nursing team visited homes, verified EPI child immunization cards, and conducted structured interviews with mothers and elders.",
                dataCollectionTool = "WHO Expanded Program on Immunization (EPI) Cluster Survey Tool & Vaccine Hesitancy Scale (VHS).",
                statisticalAnalysisTool = "IBM SPSS v26: Multivariable Logistic Regression analyzing predictors of vaccine drop-out (Adjusted Odds Ratio AOR = 2.4).",
                expectedFindings = listOf(
                    "Fully Immunized Child (FIC) rate was 68.5%, with drop-out rates highest for Measles-2 dose.",
                    "Primary reported reasons for missing vaccine doses included fear of post-vaccination fever (42%), false rumors/misconceptions (28%), and distance to BHU (18%).",
                    "Households visited regularly by LHWs demonstrated a 2.4-fold higher likelihood of full immunization compliance."
                ),
                clinicalRecommendations = "Deploy mobile nursing immunization vans to remote Charsadda villages, provide Paracetamol drops alongside vaccines to manage post-vaccination fever fears, and engage local community elders/imams in awareness campaigns.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Directly aligns with provincial EPI public health nursing targets."
            ),
            KpNursingResearchTopic(
                id = 10,
                title = "Assessment of Wound Care Protocols and Post-Operative Surgical Site Infection (SSI) Rates in General Surgical Wards at Bacha Khan Medical Complex (BKMC), Swabi",
                location = "Bacha Khan Medical Complex (BKMC) / Gajju Khan Medical College, Swabi",
                regionTag = "Swabi",
                category = "Surgical Nursing & Infection Control",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Post-operative surgical ward patients and surgical ward nursing staff at BKMC Swabi",
                sampleSize = "N = 120 Post-Operative Surgical Patients tracked for 30 days",
                studyDesign = "Prospective Observational Cohort Study",
                keyObjectives = listOf(
                    "Determine 30-day Post-Operative Surgical Site Infection (SSI) incidence rate in elective vs emergency abdominal surgeries.",
                    "Evaluate nursing aseptic compliance during surgical wound dressing changes.",
                    "Identify patient risk factors associated with SSI (diabetes, malnutrition, wound drain care)."
                ),
                methodology = "Prospective daily ward monitoring of post-op surgical wounds using CDC National Healthcare Safety Network (NHSN) SSI criteria during hospitalization and follow-up phone calls at day 14 and day 30 post-discharge.",
                dataCollectionTool = "CDC NHSN Surgical Site Infection Criteria Tool & Surgical Wound Dressing Compliance Checklist.",
                statisticalAnalysisTool = "IBM SPSS v26: Kaplan-Meier Survival Analysis & Cox Proportional Hazards Model for SSI incidence over 30 days post-op (p < 0.01).",
                expectedFindings = listOf(
                    "Overall 30-day SSI rate observed was 8.3% (3.2% in elective vs 14.8% in emergency laparotomies).",
                    "Frequent aseptic non-compliance observed included touch contamination of sterile dressing gauze and delayed replacement of saturated dressings.",
                    "Patients with preoperative blood glucose > 180 mg/dL had 3.1 times higher SSI incidence."
                ),
                clinicalRecommendations = "Adopt standardized No-Touch Wound Dressing Technique at BKMC Swabi, establish a Surgical Wound Care Nursing Team, and enforce strict preoperative glycemic control protocols.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Strong prospective clinical study model for BKMC Swabi."
            ),
            KpNursingResearchTopic(
                id = 11,
                title = "Knowledge, Attitude, and Practice of Postpartum Family Planning Counseling Among Community Midwives and LHVs in District Nowshera",
                location = "Qazi Hussain Ahmad Medical Complex (QHAMC) & BHUs across District Nowshera",
                regionTag = "Nowshera",
                category = "Maternal & Reproductive Health Nursing",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Community Midwives (CMWs), Lady Health Visitors (LHVs), and Postnatal Ward Nurses in Nowshera",
                sampleSize = "N = 100 Family Planning Healthcare Providers",
                studyDesign = "Descriptive Knowledge, Attitude, and Practice (KAP) Survey",
                keyObjectives = listOf(
                    "Assess healthcare provider knowledge regarding Immediate Postpartum Intrauterine Contraceptive Device (PPIUCD) insertion and Lactational Amenorrhea Method (LAM).",
                    "Evaluate frequency of routine family planning counseling provided to pregnant mothers during 3rd trimester ANC visits.",
                    "Identify cultural and religious misconceptions regarding birth spacing in District Nowshera."
                ),
                methodology = "Structured self-administered KAP questionnaire distributed during monthly district health review meetings in Nowshera.",
                dataCollectionTool = "Validated Family Planning KAP Questionnaire & WHO Birth Spacing Guidelines Assessment.",
                statisticalAnalysisTool = "IBM SPSS v26: Descriptive percentages & Kruskal-Wallis test comparing KAP scores across CMWs vs Hospital Nurses (p < 0.05).",
                expectedFindings = listOf(
                    "Provider knowledge score on short-acting contraceptives was high (86%), but knowledge on PPIUCD eligibility criteria was low (41%).",
                    "Only 29% of mothers received structured birth spacing counseling prior to delivery.",
                    "Primary reported barrier was provider hesitancy due to male partner disapproval and religious misconceptions in rural communities."
                ),
                clinicalRecommendations = "Conduct practical PPIUCD skill training for all CMWs and LHVs at QHAMC Nowshera, supply Pashto family planning counseling flipcharts, and promote couple-centered counseling.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Supports reproductive health policy and maternal-child survival in KP."
            ),
            KpNursingResearchTopic(
                id = 12,
                title = "Assessment of Palliative Care Knowledge and Pain Management Attitudes Among Oncology and Medical Nurses in Southern KP Hospitals (Bannu & DI Khan)",
                location = "DHQ Hospital Bannu & Mufti Mahmood Memorial Teaching Hospital / DHQ Hospital Dera Ismail Khan",
                regionTag = "Bannu / DI Khan",
                category = "Palliative Care & Pain Management",
                semesterRequirement = "Semester 7 (RES-674 Mandatory Final Thesis)",
                targetPopulation = "Staff Nurses working in Oncology, Medical Wards, and Intensive Care Units in Bannu and DI Khan",
                sampleSize = "N = 110 Registered Nurses",
                studyDesign = "Descriptive Quantitative Cross-Sectional Study",
                keyObjectives = listOf(
                    "Assess nurse knowledge regarding WHO Analgesic Ladder and opioid pain management in terminal cancer patients.",
                    "Evaluate nurse attitudes and fears regarding addiction and respiratory depression when administering Morphine.",
                    "Identify availability of palliative care services and oral Morphine in Southern KP hospitals."
                ),
                methodology = "Self-administered survey distributed to nurses in medical and oncology units across DHQ Bannu and Mufti Mahmood Hospital DI Khan.",
                dataCollectionTool = "Palliative Care Quiz for Nurses (PCQN) & Nurses' Knowledge and Attitudes Survey Regarding Pain (NKASRP).",
                statisticalAnalysisTool = "IBM SPSS v26: Independent t-test comparing PCQN scores between oncology nurses vs general medical ward nurses (p < 0.01).",
                expectedFindings = listOf(
                    "Mean score on PCQN was 48.2%, indicating significant gaps in palliative care principles and symptom management.",
                    "72% of nurses expressed unwarranted fear of causing addiction ('opiophobia') when administering prescribed Morphine for severe terminal pain.",
                    "Oral Morphine tablets faced stockout issues at 65% of regional health facilities."
                ),
                clinicalRecommendations = "Introduce WHO Palliative Care & Pain Management short-courses in southern KP nursing colleges, establish Palliative Care Consult Teams at DHQ Bannu and DI Khan, and streamline medical opioid availability.",
                supervisorNote = "Official BSN 7th Semester Thesis Project (RES-674). Crucial research addressing palliative care gaps in Southern KP healthcare."
            )
        )
    }
}

