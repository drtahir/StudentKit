package com.example.ui.screens

/**
 * ELITE BANK PART 2: ADVANCED PHARMACOLOGY, DOSING CALCULATIONS & HIGH-ALERT MED SAFETY (140 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ElitePart2 {

    fun getPharmEliteQuestions(startId: Int): List<NursingExamQuestion> {
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

        val pharmEliteTopics = listOf(
            Triple("Beta-Blockers: Metoprolol / Carvedilol Bronchospasm & Masked Hypoglycemia", "Non-selective beta-blockers (Propranolol) can induce BRONCHOSPASM in asthma/COPD; all beta-blockers MASK HYPOGLYCEMIC SYMPTOMS (tachycardia, tremors) except sweating; check HR and BP prior to administration", "Administer Propranolol to severe active asthmatics during attack"),
            Triple("Loop Diuretics: Furosemide / Torsemide Ototoxicity & Hypokalemia", "Furosemide rapid IV push (> 20 mg/min) causes OTOTOXICITY (tinnitus, hearing loss); monitor Potassium (< 3.5 mEq/L risk of dysrhythmias); monitor blood pressure and BUN/Creatinine", "Administer furosemide 100 mg IV push in 2 seconds"),
            Triple("Thiazide Diuretics: Hydrochlorothiazide Calcium & Uric Acid", "Increases calcium reabsorption (causes HYPERCALCEMIA); increases serum uric acid (can precipitate GOUT attacks); monitor blood glucose (causes mild hyperglycemia)", "HCTZ causes severe hypocalcemia and hyperpotassemia"),
            Triple("Potassium-Sparing Diuretics: Spironolactone Hyperkalemia & Gynecomastia", "Aldosterone antagonist; retains potassium while excreting sodium/water; side effects: HYPERKALEMIA, GYNECOMASTIA, menstrual irregularities; avoid potassium supplements/salt substitutes", "Encourage eating 5 bananas and taking potassium pills with spironolactone"),
            Triple("Nitrates: Nitroglycerin Storage, Slating & PDE-5 Inhibitors Contraindication", "Sublingual NTG: 1 tablet every 5 mins up to 3 doses; store in original DARK GLASS BOTTLE; CONTRAINDICATED WITH PDE-5 INHIBITORS (Sildenafil, Tadalafil - severe fatal hypotension)", "Administer sublingual nitroglycerin along with Sildenafil IV"),
            Triple("Antiplatelet: Clopidogrel / Prasugrel Black Box Warning & Surgery", "Inhibits ADP-induced platelet aggregation; HOLD 5-7 DAYS PRIOR TO ELECTIVE SURGERY to reduce bleeding risk; black box warning for poor metabolizers (CYP2C19 allele)", "Double clopidogrel dose morning of open brain surgery"),
            Triple("Anticoagulant: Fondaparinux Epidural Anesthesia Hematoma Risk", "Synthetic Factor Xa inhibitor; DO NOT ADMINISTER WHILE EPIDURAL CATHETER IS IN PLACE (risk of epidural hematoma and permanent paralysis); wait at least 6 hours post-removal", "Administer fondaparinux 1 minute before epidural catheter insertion"),
            Triple("Non-Opioid Analgesic: Acetaminophen Maximum Daily Dose & Acetylcysteine", "Maximum daily limit: 4000 mg/day (3000 mg/day in elderly/hepatic impairment); toxic metabolite causes fatal CENTRILOBULAR HEPATIC NECROSIS; ANTIDOTE IS N-ACETYLTRYSTEINE (Mucomyst)", "Antidote for acetaminophen liver toxicity is Protamine sulfate"),
            Triple("NSAIDs: Ibuprofen / Ketorolac Renal Toxicity & Peptic Ulcers", "Inhibits COX-1 and COX-2; causes gastric mucosal ulceration, GI bleeding, AKI, fluid retention; KETOROLAC MAXIMUM DURATION IS 5 DAYS due to severe renal/GI toxicity", "Administer IV Ketorolac for 30 consecutive days without stopping"),
            Triple("Inhaled Corticosteroids: Fluticasone Oral Candidiasis Prevention", "Anti-inflammatory for asthma/COPD; INSTRUCT CLIENT TO RINSE MOUTH AND SPIT WATER after each use to prevent ORAL CANDIDIASIS (thrush) and hoarseness; use spacer device", "Swallow rinse water forcefully to coat esophagus with steroid"),
            Triple("Short-Acting Beta-Agonist: Albuterol Rescue Inhaler Side Effects", "SABA for acute bronchospasm; side effects: TACHYCARDIA, TREMORS, PALPITATIONS, HYPOKALEMIA; instruct client to use Albuterol FIRST before steroid inhaler (dilates airways)", "Inhale steroid inhaler first before albuterol rescue inhaler"),
            Triple("Anticholinergic Bronchodilator: Ipratropium / Tiotropium Side Effects", "Blocks muscarinic receptors causing bronchodilation; side effects: DRY MOUTH, URINARY RETENTION, CONSTIPATION, BLURRED VISION; use with caution in narrow-angle glaucoma/BPH", "Ipratropium causes profuse salivation and diarrhea"),
            Triple("Leukotriene Receptor Antagonist: Montelukast Neuropsychiatric Events", "Maintenance therapy for asthma/allergies; Black Box Warning for NEUROPSYCHIATRIC EVENTS (agitation, depression, suicidal thoughts/behavior); monitor mood changes", "Montelukast is used as acute rescue inhaler for status asthmaticus"),
            Triple("Gastroprotective Agent: Sucralfate Administration Timing Rules", "Forms protective viscous barrier over gastric ulcer bed; MUST ADMINISTER ON EMPTY STOMACH 1 HOUR BEFORE MEALS and at bedtime; space from antacids by 30 mins", "Give sucralfate with heavy fatty meals and antacids simultaneously"),
            Triple("Proton Pump Inhibitors: Omeprazole Long-Term Risks", "Suppresses gastric acid secretion; long-term use risks: OSTEOPOROSIS/FRACTURES (decreased calcium absorption), CLOSTRIDIUM DIFFICILE INFECTION, HYPOMAGNESEMIA", "Omeprazole increases bone density and prevents osteoporosis"),
            Triple("H2-Receptor Antagonists: Cimetidine Drug Interactions & Confusion", "Inhibits hepatic CYP450 enzymes causing multiple drug interactions (increases Warfarin, Theophylline, Phenytoin levels); side effect: CNS CONFUSION in elderly", "Cimetidine has zero drug interactions or hepatic clearance effects"),
            Triple("Antiemetic: Ondansetron (Zofran) QT Prolongation Risk", "Serotonin 5-HT3 receptor antagonist; side effect: QT PROLONGATION AND TORSADES DE POINTES; monitor baseline ECG in cardiac clients and electrolyte levels", "Ondansetron causes severe shortening of QT interval and hypertension"),
            Triple("Prokinetic Agent: Metoclopramide Tardive Dyskinesia Risk", "Enhances gastric emptying; Black Box Warning for TARDIVE DYSKINESIA (extrapyramidal symptoms, involuntary facial grimacing, lip smacking, tongue protrusion); stop drug if EPS occurs", "Metoclopramide tardive dyskinesia is cured by doubling the dose"),
            Triple("Bulk-Forming Laxative: Psyllium Administration Rules", "Absorbs water in intestines; MUST BE TAKEN WITH FULL GLASS (8 oz) OF WATER immediately followed by second glass of liquid to prevent ESOPHAGEAL OBSTRUCTION/IMPACTION", "Swallow dry psyllium powder without any liquid"),
            Triple("Antidiarrheal: Loperamide Mechanism & Toxic Megacolon Risk", "Opioid-receptor agonist that slows gut motility; CONTRAINDICATED IN INFECTIOUS DIARRHEA (C. difficile, E. coli) due to risk of TOXIC MEGACOLON and systemic toxin retention", "Give Loperamide to clients with bloody invasive C. difficile fever"),
            Triple("Bisphosphonates: Alendronate Esophagitis Prevention Rules", "Inhibits bone resorption; MUST TAKE IN MORNING WITH FULL GLASS OF PLAIN WATER 30 MINS BEFORE FOOD/DRINK; REMAIN UPRIGHT FOR AT LEAST 30 MINS to prevent severe ESOPHAGITIS", "Take alendronate at bedtime with hot chocolate and lie down immediately"),
            Triple("Disease-Modifying Antirheumatic Drug: Methotrexate Bone Marrow Suppression", "Folic acid antagonist; risks: BONE MARROW SUPPRESSION (leukopenia, anemia, thrombocytopenia), HEPATOTOXICITY, TERATOGENICITY; give Folic Acid supplement to reduce toxicity", "Methotrexate is safe for usage during all trimesters of pregnancy"),
            Triple("TNF-Alpha Inhibitors: Etanercept / Infliximab Latent TB Reactivation", "Immunosuppressive biologic; MANDATORY TUBERCULIN SKIN TEST / IGRA BEFORE STARTING THERAPY to screen for LATENT TUBERCULOSIS; contraindicated in active infection/sepsis", "Administer TNF-inhibitors to clients with active purulent sepsis"),
            Triple("Gout Uric Acid Inhibitor: Allopurinol Stevens-Johnson Syndrome", "Inhibits xanthine oxidase; instruct client to DRINK 2-3 LITERS WATER/DAY to prevent kidney stones; STOP DRUG IMMEDIATELY AT FIRST SIGN OF RASH (Stevens-Johnson syndrome risk)", "Restrict fluids to 200 mL/day while taking allopurinol"),
            Triple("Acute Gout Anti-Inflammatory: Colchicine Toxicity Signs", "Inhibits neutrophil migration; narrow therapeutic index; toxicity signs: NAUSEA, VOMITING, DIARRHEA, ABDOMINAL PAIN (indicates gastrointestinal toxicity; stop drug)", "Colchicine toxicity is characterized by severe constipation and weight gain"),
            Triple("BPH Alpha-1 Blocker: Tamsulosin Intraoperative Floppy Iris Syndrome", "Relaxes smooth muscle in prostate neck; side effect: ORTHOSTATIC HYPOTENSION; warning: INTRAOPERATIVE FLOPPY IRIS SYNDROME during cataract surgery (inform ophthalmologist)", "Tamsulosin causes severe systemic hypertension and glaucoma"),
            Triple("5-Alpha Reductase Inhibitor: Finasteride Pregnant Female Handling", "Inhibits conversion of testosterone to DHT; TERATOGENIC TO MALE FETUS; PREGNANT FEMALES MUST NOT HANDLE CRUSHED OR BROKEN TABLETS (transdermal absorption)", "Pregnant nurses should crush finasteride tablets with bare hands"),
            Triple("Overactive Bladder Anticholinergic: Oxybutynin Heat Stroke Risk", "Blocks muscarinic receptors in detrusor muscle; side effects: dry mouth, constipation, urinary retention, DECREASED SWEATING (RISK OF HEAT STROKE / HYPERTHERMIA in hot weather)", "Oxybutynin causes profuse sweating and hypothermia in summer"),
            Triple("Urate Lowering / Muscle Relaxant: Baclofen Sudden Withdrawal Seizures", "GABA-B agonist for spasticity; DO NOT DISCONTINUE ABRUPTLY (risk of severe withdrawal: REBOUND SPASTICITY, HALLUCINATIONS, SEIZURES, Rhabdomyolysis); taper slowly", "Abruptly stop high-dose Baclofen pump infusion instantly"),
            Triple("Skeletal Muscle Relaxant: Dantrolene Malignant Hyperthermia Antidote", "Direct-acting muscle relaxant; DRUG OF CHOICE FOR MALIGNANT HYPERTHERMIA (triggered by succinylcholine/volatile anesthetics); monitor LFTs for hepatotoxicity", "Dantrolene triggers malignant hyperthermia when administered")
        )

        for (i in 0 until 140) {
            val topicIndex = i % pharmEliteTopics.size
            val item = pharmEliteTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Elite Pharmacology Standard: ${item.second}",
                "Dangerous / Inappropriate Action: ${item.third}",
                "Omit documentation and double dose next shift",
                "Administer drug without checking client identity"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Advanced Clinical Pharmacology",
                "NCLEX-RN / DHA • Elite Series",
                "Elite Series Pharmacology Case #${i + 1}: While managing a pharmacological regimen involving ${item.first}, which clinical decision demonstrates gold-standard evidence-based care?",
                options,
                correctPos,
                "Rationale: Advanced clinical pharmacology elite guidelines for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice ensures therapeutic efficacy, prevents drug toxicities, and maintains medication safety. Action '${item.third}' is unsafe.",
                "Pharm Elite • ${item.first}"
            )
        }

        return list
    }
}
