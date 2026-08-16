package com.drtahir.studentkit.ui.screens

/**
 * EXPERT BANK PART 2: ADVANCED CLINICAL PHARMACOLOGY & SAFE MEDICATION PRACTICE (140 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ExpertPart2 {

    fun getPharmExpertQuestions(startId: Int): List<NursingExamQuestion> {
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

        val pharmExpertTopics = listOf(
            Triple("Cardiac Glycosides: Digoxin Toxicity & Hypokalemia Link", "Digoxin therapeutic level: 0.5-2.0 ng/mL; HYPOKALEMIA (< 3.5 mEq/L) dramatically increases digoxin toxicity risk; signs: yellow-green halos, visual blurs, nausea, bradycardia; check apical pulse for 1 full minute", "High potassium levels increase digoxin toxicity"),
            Triple("Direct Thrombin Inhibitors: Dabigatran Storage & Bleeding Rules", "Keep Dabigatran in ORIGINAL BOTTLE or blister pack with desiccant to prevent moisture degradation; do NOT put in pill organizer; antidote: Idarucizumab", "Store dabigatran pills unsealed in damp bathroom drawer"),
            Triple("Factor Xa Inhibitors: Rivaroxaban / Apixaban Dosing Rules", "Oral anticoagulants; take Rivaroxaban with EVENING MEAL to maximize bioavailability; monitor for signs of occult GI bleeding; Antidote: Andexanet alfa", "Take rivaroxaban on empty stomach with grapefruit juice"),
            Triple("Continuous Vasopressor: Norepinephrine Extravasation & Phentolamine", "Inotropic vasopressor; if extravasation occurs (severe tissue ischemia/necrosis), infiltrate site immediately with PHENTOLAMINE (phentolamine mesylate)", "Flush extravasation site with IV regular insulin"),
            Triple("Class III Antiarrhythmic: Amiodarone Organ Toxicities", "Black box warnings for PULMONARY TOXICITY (pulmonary fibrosis), HEPATOTOXICITY, and THYROID DYSFUNCTION (hypo/hyperthyroidism); baseline chest X-ray and LFTs required", "Amiodarone is completely free of pulmonary or thyroid side effects"),
            Triple("Direct-Acting Vasodilator: Sodium Nitroprusside Cyanide Toxicity", "Nitroprusside breaks down into cyanide; light-sensitive solution (wrap bag in opaque shield); toxicity signs: almond breath odor, metabolic acidosis, confusion", "Expose nitroprusside bag to direct sunlight for 24 hours"),
            Triple("Dopamine Inotropic Dosing Range & Hemodynamic Effects", "Low dose (1-5 mcg/kg/min): renal perfusion; Medium dose (5-10 mcg/kg/min): cardiac contractility (beta-1); High dose (> 10 mcg/kg/min): vasoconstriction (alpha-1)", "High dose dopamine causes profound vasodilation and shock"),
            Triple("Short-Acting Insulin: Regular Insulin IV Administration Rules", "Regular insulin is the ONLY insulin that can be administered via INTRAVENOUS (IV) ROUTE; used for hyperkalemia and DKA emergencies", "Glargine and Detemir can be given IV push"),
            Triple("Rapid-Acting Insulins: Lispro / Aspart / Glulisine Timing", "Onset: 15 minutes, Peak: 1-2 hours; MUST ADMINISTER WITHIN 15 MINUTES BEFORE A MEAL or immediately after meal to prevent severe hypoglycemia", "Give 2 hours before meal when client is fasting"),
            Triple("Long-Acting Insulins: Glargine / Detemir Non-Mixing Rule", "Glargine has no peak and provides 24-hour basal coverage; NEVER MIX GLARGIINE OR DETEMIR WITH ANY OTHER INSULIN in the same syringe", "Mix Glargine with Regular insulin in same syringe and shake hard"),
            Triple("Oral Hypoglycemic: Metformin Lactic Acidosis & Contrast Dye", "HOLD METFORMIN FOR 48 HOURS AFTER CONTRAST DYE PROCEDURES to prevent AKI and fatal LACTIC ACIDOSIS; check serum creatinine prior to resuming", "Take double dose metformin right before IV contrast scan"),
            Triple("Sulfonylureas: Glipizide / Glyburide Elderly Hypoglycemia Risk", "Stimulates pancreatic beta cells; high risk of PROLONGED HYPOGLYCEMIA in elderly clients; avoid Glyburide in renal impairment (Beers Criteria)", "Glyburide causes severe hyperglycemia in elderly"),
            Triple("Glucagon Emergency Administration for Severe Hypoglycemia", "Administer 1 mg IM or SC when unconscious diabetic client has no IV access; turn client on SIDE to prevent aspiration from vomiting", "Force feed unconscious client whole apples"),
            Triple("Aminoglycosides: Gentamicin / Tobramycin Peak & Trough Levels", "Nephrotoxic and Ototoxic; draw TROUGH LEVEL immediately before next dose (30 mins before); draw PEAK LEVEL 30 mins after 30-min infusion ends", "Draw trough level 2 hours after infusion finishes"),
            Triple("Glycopeptide Antibiotics: Vancomycin Red Man Syndrome Care", "Rapid IV infusion causes RED MAN SYNDROME (flushing, rash, hypotension from histamine release); SLOW INFUSION RATE to over at least 60-90 minutes", "Red man syndrome is a severe IgE allergy requiring immediate intubation"),
            Triple("Macrolide Antibiotics: Azithromycin / Erythromycin Hepatotoxicity", "Inhibits bacterial protein synthesis; risk of QT PROLONGATION and HEPATOTOXICITY; monitor ALT/AST and baseline ECG", "Causes immediate kidney hypertrophy and hyperkalemia"),
            Triple("Fluoroquinolones: Ciprofloxacin / Levofloxacin Tendon Rupture", "Black box warning for TENDONITIS AND TENDON RUPTURE (Achilles tendon); stop drug immediately if joint/tendon pain develops; avoid in pregnancy", "Encourage heavy marathon running while taking Ciprofloxacin"),
            Triple("Tetracyclines: Doxycycline Tooth Discoloration & Sun Rules", "CONTRAINDICATED IN PREGNANCY AND CHILDREN < 8 YEARS (causes permanent tooth discoloration and bone growth inhibition); causes severe photosensitivity", "Administer to 3-year-olds with full glass of whole milk"),
            Triple("Sulfonamides: Trimethoprim-Sulfamethoxazole (TMP-SMX) Crystalluria", "Maintain high fluid intake (2-3 L/day) to prevent CRYSTALLURIA and kidney damage; monitor for Stevens-Johnson syndrome and severe rash", "Restrict fluid intake to 200 mL/day"),
            Triple("Antitubercular: Isoniazid (INH) Peripheral Neuropathy & Vitamin B6", "INH causes PERIPHERAL NEUROPATHY (numbness/tingling); co-administer PYRIDOXINE (VITAMIN B6) as prophylaxis; monitor liver function tests", "INH cures peripheral neuropathy without supplements"),
            Triple("Antitubercular: Rifampin Red-Orange Bodily Secretions", "Informs client that Rifampin causes HARMLESS RED-ORANGE DISCOLORATION of urine, sweat, tears, and saliva; soft contact lenses may be permanently stained", "Red urine from Rifampin indicates massive renal rupture"),
            Triple("Antitubercular: Ethambutol Visual Acuity & Red-Green Color Blindness", "Ethambutol causes OPTIC NEURITIS; baseline and monthly assessment of VISUAL ACUITY AND RED-GREEN COLOR DISCRIMINATION required", "Causes severe hearing loss and tinnitus"),
            Triple("Benzodiazepine Antidote: Flumazenil Seizure Risk", "Flumazenil reverses benzodiazepine sedation; WARNING: can precipitate ACUTE WITHDRAWAL SEIZURES in chronic benzodiazepine users", "Flumazenil is used as antidote for Opioid respiratory arrest"),
            Triple("Opioid Reversal: Naloxone (Narcan) Short Half-Life Warning", "Naloxone half-life (30-90 mins) is SHORTER than most opioids; monitor client for RE-SEDATION AND RESPIRATORY DEPRESSION; repeat doses often required", "One dose of Naloxone permanently cures all opioid toxicity"),
            Triple("Tricyclic Antidepressants (TCAs): Amitriptyline Cardiotoxicity", "High risk of fatal overdose; causes CARDIOTOXICITY (widened QRS interval, lethal dysrhythmias), anticholinergic side effects, severe sedation", "TCAs have zero cardiotoxicity or arrhythmia risks"),
            Triple("MAOIs: Phenelzine / Tranylcypromine Tyramine Hypertensive Crisis", "Strict TYRAMINE-RESTRICTED DIET required (avoid aged cheese, cured meats, red wine, fava beans, draft beer); tyramine intake triggers fatal HYPERTENSIVE CRISIS", "Encourage eating aged blue cheese and salami pizza"),
            Triple("Selective Serotonin Reuptake Inhibitors (SSRIs): Serotonin Syndrome", "Serotonin toxicity triad: Mental status changes (agitation), Autonomic hyperactivity (fever, diaphoresis, HTN), Neuromuscular abnormalities (clonus, hyperreflexia)", "Serotonin syndrome causes hypothermia and hyporeflexia"),
            Triple("Mood Stabilizer: Lithium Carbonate Toxicity & Sodium Link", "Therapeutic range: 0.6-1.2 mEq/L; toxicity (> 1.5 mEq/L) causes tremor, ataxia, confusion, seizures; SODIUM DEPLETION (hyponatremia) increases lithium toxicity", "Instruct client to consume zero sodium and restrict water"),
            Triple("First-Generation Antipsychotics: Haloperidol Neuroleptic Malignant Syndrome (NMS)", "NMS: Hyperthermia (fever > 104°F), muscle 'lead-pipe' rigidity, autonomic instability, altered consciousness; STOP DRUG and give Dantrolene / Bromocriptine", "NMS is treated with warm heating blankets and Haloperidol bolus"),
            Triple("Atypical Antipsychotics: Clozapine Agranulocytosis Monitoring", "Risk of severe AGRANULOCYTOSIS (neutropenia); MANDATORY WEEKLY ABSOLUTE NEUTROPHIL COUNT (ANC) monitoring; stop drug if ANC < 1000/mm3", "Clozapine ANC monitoring is optional once a year"),
            Triple("Anticonvulsant: Phenytoin (Dilantin) Therapeutic Range & Gingival Hyperplasia", "Therapeutic level: 10-20 mcg/mL; side effects: GINGIVAL HYPERPASIA (frequent dental hygiene required), nystagmus, ataxia; inject IV with Normal Saline ONLY", "Mix Phenytoin IV with 5% Dextrose in Water (causes precipitation)"),
            Triple("Anticonvulsant: Valproic Acid Hepatotoxicity & Pancreatitis", "Black box warnings for FATAL HEPATOTOXICITY, PANCREATITIS (severe abdominal pain), and TERATOGENICITY (neural tube defects)", "Valproic acid is safest drug during first trimester pregnancy"),
            Triple("Anticoagulant: Unfractionated Heparin Protamine Sulfate Antidote", "Monitor aPTT (therapeutic range 1.5 - 2.5 x control, ~ 60-80 seconds); ANTIDOTE IS PROTAMINE SULFATE for severe bleeding", "Therapeutic aPTT for Heparin is 300 seconds"),
            Triple("Anticoagulant: Warfarin (Coumadin) Vitamin K Antidote & INR", "Monitor PT/INR (target INR 2.0-3.0 for DVT/PE/AFib; 2.5-3.5 for mechanical heart valves); ANTIDOTE IS VITAMIN K (Phytonadione) / Kcentra", "Maintain steady high intake of fluctuating green leafy vegetables"),
            Triple("Thrombolytic Therapy: Alteplase (tPA) Inclusion/Exclusion Criteria", "Administer within 3.5 - 4.5 hours of ischemic stroke onset; CONTRAINDICATED if active bleeding, recent major surgery, BP > 185/110 mmHg, or stroke/head trauma within 3 months", "Give tPA to client with severe active hemorrhagic brain bleed"),
            Triple("Statins: Atorvastatin / Simvastatin Rhabdomyolysis Risk", "HMG-CoA reductase inhibitor; side effect: RHABDOMYOLYSIS AND MYOPATHY; instruct client to report unexplained muscle pain/weakness immediately; monitor LFTs", "Statins increase cholesterol synthesis in myocardium"),
            Triple("ACE Inhibitors: Enalapril / Lisinopril Angioedema & Cough", "Inhibits ACE; side effects: DRY HACKING COUGH, HYPERKALEMIA, ANGIOEDEMA (swelling of lips/tongue/airway - life-threatening emergency)", "ACE inhibitors cause severe hypokalemia"),
            Triple("ARBs: Losartan / Valsartan Hyperkalemia & Renal Function", "Blocks angiotensin II receptors; used when client develops ACE inhibitor cough; risk of HYPERKALEMIA and reduced renal function; contraindicated in pregnancy", "ARBs cause dry hacking cough in 100% of clients"),
            Triple("Calcium Channel Blockers: Diltiazem / Verapamil vs Amlodipine", "Diltiazem/Verapamil reduce HR and BP (non-dihydropyridines); Amlodipine affects vascular smooth muscle (dihydropyridine) causing peripheral edema", "Amlodipine causes severe bradycardia of 20 bpm"),
            Triple("Potassium Replacement: IV Potassium Chloride Administration Rules", "NEVER ADMINISTER IV PURIFIED POTASSIUM PUSH (causes fatal cardiac arrest); maximum infusion rate is 10 mEq/hr peripherally or 20 mEq/hr centrally via infusion pump", "Give 40 mEq IV push potassium over 2 seconds")
        )

        for (i in 0 until 140) {
            val topicIndex = i % pharmExpertTopics.size
            val item = pharmExpertTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Pharmacology Expert Standard: ${item.second}",
                "Dangerous / Inappropriate Action: ${item.third}",
                "Skip documentation and double dose next cycle",
                "Administer medication without verifying rights"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Advanced Clinical Pharmacology",
                "NCLEX-RN / DHA • Expert Series",
                "Expert Series Pharmacology Case #${i + 1}: In safely managing a high-alert medication protocol involving ${item.first}, which clinical decision represents evidence-based nursing care?",
                options,
                correctPos,
                "Rationale: Advanced clinical pharmacology standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice prevents drug toxicity, maintains therapeutic range, and safeguards client safety. Action '${item.third}' is unsafe.",
                "Pharm Expert • ${item.first}"
            )
        }

        return list
    }
}
