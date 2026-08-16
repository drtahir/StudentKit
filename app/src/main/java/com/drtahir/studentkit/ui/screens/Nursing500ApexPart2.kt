package com.drtahir.studentkit.ui.screens

/**
 * APEX BANK PART 2: ADVANCED CLINICAL PHARMACOLOGY, HIGH-ALERT DRUGS & CALCULATIONS (140 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ApexPart2 {

    fun getPharmApexQuestions(startId: Int): List<NursingExamQuestion> {
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

        val apexTopicsPart2 = listOf(
            Triple("Cardiovascular Meds: Digoxin Toxicity & Hypokalemia Predisposition", "Therapeutic level: 0.5-2.0 ng/mL; Toxicity signs: anorexia, nausea, vomiting, visual halos (yellow-green), bradycardia; HYPOKALEMIA INCREASES DIGOXIN TOXICITY RISK; antidote: Digoxin Immune Fab (Digibind)", "Give digoxin bolus when client complains of yellow-green visual halos"),
            Triple("Cardiovascular Meds: Amiodarone Pulmonary Toxicity & Thyroid Dysfunction", "Antiarrhythmic for VT/VF; Black Box Warning for PULMONARY TOXICITY (cough, dyspnea, interstitial lung disease) and LIVER TOXICITY; contains iodine causing HYPO/HYPERTHYROIDISM", "Amiodarone is completely free of pulmonary or thyroid side effects"),
            Triple("Cardiovascular Meds: Adenosine Rapid IV Push & Brief Asystole", "Used for Paroxysmal Supraventricular Tachycardia (PSVT); MUST BE GIVEN AS RAPID IV PUSH (1-2 secs) through proximal IV line followed by 20 mL Normal Saline flush; EXPECT BRIEF PERIOD OF ASYSTOLE", "Infuse adenosine slowly over 8 hours in peripheral line"),
            Triple("Cardiovascular Meds: Sodium Nitroprusside Cyanide Toxicity & Light Sensitivity", "Potent arterial/venous vasodilator; protects bag/tubing from light exposure; risk of CYANIDE / THIOCYANATE TOXICITY with high doses or > 48 hr infusion (almond breath, metabolic acidosis)", "Nitroprusside must be exposed to direct sunlight during 10-day drip"),
            Triple("Anticoagulation: Warfarin INR Target & Vitamin K / FFP Reversal", "Inhibits Vitamin K-dependent clotting factors (II, VII, IX, X); monitor INR (target 2.0-3.0 for AFib/DVT, 2.5-3.5 for mechanical heart valves); REVERSAL AGENT IS VITAMIN K (Phytonadione) or FFP", "Target INR for mechanical valve is 0.1"),
            Triple("Anticoagulation: Heparin aPTT Target & Protamine Sulfate Antidote", "Unfractionated heparin; monitor aPTT (therapeutic target 1.5 - 2.0 times baseline control: 60-80 seconds); REVERSAL AGENT IS PROTAMINE SULFATE (1 mg neutralizes ~100 units heparin)", "Reversal agent for Heparin drip is oral Aspirin"),
            Triple("Inotropic Agents: Dobutamine / Dopamine Dose-Dependent Receptor Effects", "Dopamine low dose (1-5 mcg/kg/min = renal vasodilation), medium dose (5-10 = beta-1 inotropic/chronotropic), high dose (> 10 = alpha-1 vasoconstriction); Dobutamine is selective beta-1 inotrope", "Dopamine 20 mcg/kg/min causes massive vasodilation and hypotension"),
            Triple("Inotropic Agents: Milrinone Phosphodiesterase-3 Inhibitor Inodilator", "Inodilator (positive inotrope + systemic vasodilator); decreases SVR and pulmonary capillary wedge pressure while increasing cardiac output; monitor for HYPOTENSION and ventricular arrhythmias", "Milrinone is a potent vasoconstrictor that doubles SVR"),
            Triple("Psychotropics: MAOIs (Phenelzine) Tyramine Crisis & Hypertensive Emergency", "Monoamine oxidase inhibitors; AVOID HIGH-TYRAMINE FOODS (aged cheeses, cured meats, red wine, fava beans, draft beer) to prevent severe fatal HYPERTENSIVE CRISIS", "Eat aged cheddar cheese and salami daily while taking Phenelzine"),
            Triple("Psychotropics: Tricyclic Antidepressants (Amitriptyline) Cardiotoxicity & QRS Widening", "Lethal in overdose; anticholinergic, alpha-blocking, and quinidine-like cardiac membrane stabilizing effects; OVERDOSE CAUSES QRS WIDENING, LETHAL ARRHYTHMIAS, SEIZURES; treat with Sodium Bicarbonate", "Treat Amitriptyline cardiac overdose with high-dose Potassium"),
            Triple("Psychotropics: Clozapine Agranulocytosis & Absolute Neutrophil Count (ANC)", "Second-generation antipsychotic for refractory schizophrenia; Black Box Warning for AGRANULOCYTOSIS; mandatory ANC monitoring (ANC must be >= 1500/mm3 to initiate, >= 1000 to maintain)", "Discontinue blood tests for Clozapine after 1 week"),
            Triple("Psychotropics: Haloperidol QT Prolongation & Torsades de Pointes", "First-generation high-potency antipsychotic; Black Box Warning for QT PROLONGATION AND TORSADES DE POINTES (especially IV route); continuous telemetry monitoring recommended", "Haloperidol shortens QT interval and prevents all dysrhythmias"),
            Triple("Antiepileptics: Phenytoin (Dilantin) Therapeutic Range & Gingival Hyperplasia", "Therapeutic level: 10-20 mcg/mL; toxicity: nystagmus, ataxia, slurred speech; side effects: GINGIVAL HYPERPLASIA (meticulous oral hygiene needed), hirsutism, folate deficiency; IV requires filter", "Therapeutic level of Phenytoin is 200 mcg/mL"),
            Triple("Antiepileptics: Carbamazepine Aplastic Anemia & HLA-B*1502 Screening", "Used for trigeminal neuralgia and seizures; Black Box Warning for APLASTIC ANEMIA, AGRANULOCYTOSIS, and STEVENS-JOHNSON SYNDROME; screen Asian patients for HLA-B*1502 allele", "Carbamazepine stimulates massive white blood cell production"),
            Triple("Antiepileptics: Valproic Acid Hepatoxicity & Pancreatitis", "Black Box Warning for FATAL HEPATOTOXICITY, PANCREATITIS (abdominal pain, elevated amylase/lipase), and TERATOGENICITY (neural tube defects - spina bifida); monitor LFTs", "Valproic acid is completely safe during early pregnancy"),
            Triple("Antiepileptics: Levetiracetam (Keppra) Behavioral / Mood Side Effects", "Broad-spectrum anticonvulsant; minimal drug interactions; primary adverse effects: SOMNOLENCE, DIZZINESS, and BEHAVIORAL CHANGES (agitation, aggression, depression, suicidal ideation)", "Levetiracetam causes severe kidney stones and hypercalcemia"),
            Triple("Antiepileptics: Lamotrigine Stevens-Johnson Syndrome Risk", "Black Box Warning for SEVERE LIFE-THREATENING SKIN RASH (Stevens-Johnson syndrome / Toxic Epidermal Necrolysis); slow dose titration required; STOP DRUG AT FIRST SIGN OF RASH", "Double Lamotrigine dose immediately if body rash appears"),
            Triple("Analgesics: Fentanyl Transdermal Patch Application Rules", "Long-acting opioid for opioid-tolerant chronic pain; NOT FOR ACUTE PAIN; change patch every 72 hours; DO NOT APPLY DIRECT HEAT (heating pads increase drug absorption causing fatal overdose)", "Apply heating pad directly over Fentanyl patch to increase pain relief"),
            Triple("Analgesics: Morphine Histamine Release & Epidural Pruritus", "Opioid agonist; causes histamine release leading to PRURITUS, VASODILATION, BRONCHOSPASM; epidural morphine causes intense pruritus (treated with low-dose Naloxone or Diphenhydramine)", "Morphine causes massive hypertension and tachycardia"),
            Triple("Analgesics: Naloxone Short Half-Life & Re-Sedation Monitoring", "Opioid antagonist; HALF-LIFE IS SHORTER THAN MOST OPIOIDS (30-90 mins vs 4-6 hrs for morphine); RE-SEDATION AND RESPIRATORY DEPRESSION CAN RECUR; repeat doses or infusion may be required", "A single Naloxone dose permanently reverses all opioids forever"),
            Triple("Antihypertensives: Hydralazine Reflex Tachycardia & Lupus-Like Syndrome", "Direct arterial vasodilator; side effect: REFLEX TACHYCARDIA (often co-administered with beta-blocker), fluid retention, DRUG-INDUCED LUPUS ERYTHEMATOSUS (fever, joint pain, positive ANA)", "Hydralazine causes severe bradycardia and dry skin"),
            Triple("Antihypertensives: Methyldopa Coombs-Positive Hemolytic Anemia", "Centrally acting alpha-2 agonist; FIRST-LINE ANTIHYPERTENSIVE IN PREGNANCY; side effect: POSITIVE COOMBS TEST / AUTOIMMUNE HEMOLYTIC ANEMIA, hepatotoxicity, sedation", "Methyldopa is strictly contraindicated in pregnant women"),
            Triple("Antihypertensives: Minoxidil Severe Fluid Retention & Hypertrichosis", "Potent direct vasodilator for refractory HTN; Black Box Warning for SEVERE PERICARDIAL EFFUSION; side effects: profound fluid retention (requires loop diuretic) and HYPERTRICHOSIS (hair growth)", "Minoxidil causes complete alopecia and dehydration"),
            Triple("Diabetes Meds: Metformin Lactic Acidosis & Contrast Dye Rules", "Biguanide; Black Box Warning for LACTIC ACIDOSIS; HOLD METFORMIN FOR 48 HOURS AFTER IV IODINATED CONTRAST DYE procedures to prevent acute renal failure and lactic acidosis", "Administer double dose Metformin immediately before CT contrast dye scan"),
            Triple("Diabetes Meds: Sulfonylureas (Glipizide / Glyburide) Prolonged Hypoglycemia", "Stimulates insulin secretion from pancreatic beta cells; side effect: SEVERE PROLONGED HYPOGLYCEMIA (especially in elderly/renal impairment) and weight gain; take 30 mins before meals", "Glyburide causes severe hyperosmolar hyperglycemia"),
            Triple("Diabetes Meds: Thiazolidinediones (Pioglitazone) Fluid Retention & Heart Failure", "Increases insulin sensitivity; Black Box Warning: CONTRAINDICATED IN CLASS III/IV HEART FAILURE due to FLUID RETENTION AND EXACERBATION OF HF; risk of bladder cancer", "Pioglitazone cures severe NYHA class IV heart failure"),
            Triple("Diabetes Meds: SGLT2 Inhibitors (Empagliflozin) Euglycemic DKA & Mycotic Infections", "Inhibits renal glucose reabsorption; benefits in HF and CKD; side effects: EUGLYCEMIC DKA (DKA with normal BG < 250), GENITAL MYCOTIC INFECTIONS, UTIs, volume depletion", "SGLT2 inhibitors cause severe oliguria and renal stones"),
            Triple("Diabetes Meds: GLP-1 Receptor Agonists (Semaglutide) Pancreatitis & Thyroid Tumors", "Enhances glucose-dependent insulin secretion and delays gastric emptying; Black Box Warning: THYROID C-CELL TUMORS / MTC; risk of ACUTE PANCREATITIS (severe epigastric pain)", "Semaglutide accelerates gastric emptying by 500%"),
            Triple("Dosing Calculations: Pediatric Weight-Based Dosage Verification", "Always convert weight from lbs to kg (lbs / 2.2); verify dose in mg/kg/day against safe reference range; divide into prescribed dosing frequency; use calibrated oral syringe", "Round weight in lbs up to nearest 100 kg"),
            Triple("Dosing Calculations: IV Piggyback (IVPB) Rate Calculation (mL/hr)", "Formula: [Total Volume (mL) / Time in Minutes] x 60 = Infusion Rate in mL/hr; example: 100 mL IVPB over 30 mins = (100 / 30) x 60 = 200 mL/hr on infusion pump", "Set infusion pump rate to 5 mL/hr for 100 mL 30-minute IVPB")
        )

        for (i in 0 until 140) {
            val topicIndex = i % apexTopicsPart2.size
            val item = apexTopicsPart2[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Omit documentation and double the dose on the next shift",
                "Administer medication without verifying rights or client identity"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Advanced Clinical Pharmacology",
                "NCLEX-RN / DHA • Apex Series",
                "Apex Series Pharmacology Case #${i + 1}: While managing a complex pharmacological regimen or high-risk medication administration involving ${item.first}, which clinical action demonstrates evidence-based excellence?",
                options,
                correctPos,
                "Rationale: Clinical pharmacology apex guidelines for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice ensures drug efficacy, prevents fatal toxicities, and maintains medication safety. Action '${item.third}' is unsafe.",
                "Pharm Apex • ${item.first}"
            )
        }

        return list
    }
}
