package com.drtahir.studentkit.ui.screens

/**
 * PART 2: PHARMACOLOGY & MEDICATION SAFETY (80 MCQs) + FUNDAMENTALS OF NURSING (60 MCQs)
 * NCLEX-RN, DHA, HAAD, Prometric, PNC & International Competitive Exam Standard.
 */
object Nursing500BankPart2 {

    fun getPharmAndFundamentalsQuestions(startId: Int): List<NursingExamQuestion> {
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
        // PHARMACOLOGY & MEDICATION SAFETY (80 QUESTIONS)
        // =========================================================================
        val pharmScenarios = listOf(
            Triple("High-Alert Potassium Chloride (KCl) IV Safety", "NEVER give KCl via IV push/bolus (causes fatal cardiac arrest); must dilate and infuse via IV pump slowly (<10-20 mEq/hr)", "Give 40 mEq KCl via direct rapid IV push"),
            Triple("Warfarin (Coumadin) Antidote & Monitoring", "Monitor INR (target 2.0-3.0); Vitamin K (Phytonadione) is the reversal antidote", "Monitor aPTT and give Protamine Sulfate for Warfarin toxicity"),
            Triple("Heparin Antidote & Monitoring", "Monitor aPTT (therapeutic 60-80 sec); Protamine Sulfate is the reversal antidote", "Monitor INR and give Vitamin K for Heparin toxicity"),
            Triple("Opioid Overdose Antidote", "Naloxone (Narcan) IV/Intranasal reverses opioid respiratory depression; monitor for re-sedation as naloxone duration is shorter than opioid", "Administer flumazenil for morphine toxicity"),
            Triple("Benzodiazepine Overdose Antidote", "Flumazenil is the specific antidote for benzodiazepine overdose (lorazepam, diazepam, midazolam)", "Administer naloxone for diazepam overdose"),
            Triple("Acetaminophen (Tylenol) Toxicity Antidote", "Acetylcysteine (Mucomyst) is the antidote for acetaminophen hepatotoxicity; give within 8 hours", "Administer calcium gluconate for acetaminophen poisoning"),
            Triple("Digoxin Toxicity Antidote & Signs", "Digoxin Immune Fab (Digibind) is the antidote; signs include visual green-yellow halos, bradycardia, nausea", "Give IV atropine as digoxin antidote"),
            Triple("Insulin Administration Peak Times", "Rapid-acting (Lispro/Aspart: peak 30-90 min); Short-acting (Regular: peak 2-4 hr); Intermediate (NPH: peak 4-12 hr); Long-acting (Glargine: NO peak)", "NPH insulin has no peak and acts in 1 minute"),
            Triple("Mixing Regular and NPH Insulin Technique", "Inject air into NPH, inject air into Regular, draw up Regular (clear), draw up NPH (cloudy) - 'Clear before Cloudy'", "Draw up NPH cloudy insulin first then Regular clear insulin"),
            Triple("Pharmacology: Aminoglycoside (Gentamicin) Ototoxicity & Nephrotoxicity Trough Monitoring", "Monitor serum trough level immediately before next dose (target trough < 2 mcg/mL); report tinnitus, vertigo, or rising serum creatinine", "Gentamicin trough is drawn 30 minutes after completion of IV infusion"),
            Triple("Vancomycin Trough Level Monitoring", "Draw trough blood sample 30 minutes BEFORE the next scheduled dose (therapeutic trough 10-20 mcg/mL)", "Draw trough level 2 hours after completing the infusion"),
            Triple("Gentamicin & Aminoglycoside Toxicity", "Ototoxicity (tinnitus, hearing loss, vertigo) and Nephrotoxicity (rising creatinine, oliguria)", "Severe hepatotoxicity with esophageal ulceration"),
            Triple("Monoamine Oxidase Inhibitor (MAOI) Tyramine Restrictions", "Avoid tyramine-rich foods (aged cheeses, cured meats, red wine, fava beans) to prevent hypertensive crisis", "Encourage daily consumption of aged cheddar cheese and salami"),
            Triple("Lithium Toxicity & Sodium Relationship", "Therapeutic level 0.6-1.2 mEq/L; toxicity (>1.5) causes coarse tremors, ataxia, confusion; low sodium increases lithium toxicity", "Restrict sodium intake completely to zero to prevent toxicity"),
            Triple("Selective Serotonin Reuptake Inhibitor (SSRI) Serotonin Syndrome", "High fever, hyperreflexia, clonus, agitation, diaphoresis; occurs when combining SSRIs with MAOIs or St. John's Wort", "Hypothermia with severe flaccid paralysis"),
            Triple("Phenytoin (Dilantin) Gingival Hyperplasia & Level", "Therapeutic range 10-20 mcg/mL; adverse effect: gingival hyperplasia (requires soft toothbrush and regular dental care)", "Therapeutic range 100-200 mcg/mL; mandatory tooth extraction"),
            Triple("Sublingual Nitroglycerin Patient Education", "Take 1 tablet under tongue for chest pain; call 911 if pain unimproved after 5 minutes; store in original dark glass bottle", "Swallow tablet with hot water and keep in clear plastic box"),
            Triple("Inhaled Corticosteroid Patient Education", "Rinse mouth and gargle with water after using steroid inhaler to prevent oral candidiasis (thrush)", "Swallow the mouth rinse after using steroid inhaler"),
            Triple("Beta-Blocker Administration Safety", "Hold medication if apical heart rate < 60 bpm or systolic BP < 100 mmHg; caution in asthma (bronchospasm)", "Administer metoprolol when heart rate is 38 bpm"),
            Triple("ACE Inhibitor Adverse Effects", "Dry non-productive cough, hyperkalemia, angioedema (life-threatening lip/tongue swelling)", "Hypokalemia with severe diarrhea"),
            Triple("Calcium Channel Blocker (Amlodipine) Side Effect", "Peripheral lower extremity edema, flushing, dizziness, constipation", "Severe dry hacking cough"),
            Triple("Statins (HMG-CoA Reductase Inhibitors) Safety", "Report unexplained muscle pain/weakness (rhabdomyolysis); monitor liver function tests (ALT/AST)", "Take statin with grapefruit juice every morning"),
            Triple("Spironolactone (Aldactone) Potassium Safety", "Potassium-sparing diuretic; risk of hyperkalemia; avoid salt substitutes containing potassium", "Encourage eating 5 bananas daily with potassium supplements"),
            Triple("Loop Diuretic (Furosemide) Side Effects", "Hypokalemia, hyponatremia, ototoxicity (if pushed too fast IV), orthostatic hypotension", "Hyperkalemia with dense bone growth"),
            Triple("Pharmacology: Digoxin Toxicity S/S & Digoxin Immune Fab Antidote", "Anorexia, nausea, vomiting, confusion, and visual disturbances (yellow-green halos); antidote is Digoxin Immune Fab (DigiFab) for severe toxicity", "Digoxin toxicity antidote is IV protamine sulfate"),
            Triple("Levothyroxine (Synthroid) Administration", "Take in morning on empty stomach with full glass of water, 30-60 minutes before breakfast", "Take at bedtime with a high-calcium milk shake"),
            Triple("Proton Pump Inhibitor (Omeprazole) Long-Term Risks", "Increased risk of Osteoporosis/fractures, Clostridium difficile diarrhea, and Vitamin B12 deficiency", "Reduces risk of bone fractures and increases stomach acid"),
            Triple("Sucralfate (Carafate) Administration Timing", "Administer on empty stomach 1 hour BEFORE meals and at bedtime; forms protective slurry over ulcer", "Take with meals alongside antacids"),
            Triple("Antacid Drug Interaction Timing", "Take antacids 1 hour before or 2 hours after other oral medications to avoid altering drug absorption", "Take antacids simultaneously with all prescription medications"),
            Triple("Metoclopramide (Reglan) Tardive Dyskinesia Risk", "Black box warning for extrapyramidal symptoms and irreversible tardive dyskinesia (protruding tongue, lip smacking)", "Causes rapid hair growth and euphoria"),
            Triple("Ondansetron (Zofran) Adverse Effect", "Headache, constipation, QT interval prolongation (monitor EKG in cardiac patients)", "Severe respiratory depression requiring naloxone"),
            Triple("Aspirin Reye Syndrome Contraindication", "Do NOT give aspirin to children/adolescents with viral illness (flu, chickenpox) due to fatal Reye Syndrome", "Administer aspirin to 4-year-old with active chickenpox fever"),
            Triple("NSAID (Ibuprofen) Adverse Effects", "Gastrointestinal ulceration/bleeding, nephrotoxicity, fluid retention, hypertension", "Protects gastric mucosa and enhances renal perfusion"),
            Triple("Transdermal Fentanyl Patch Application", "Apply to clean, dry, hairless skin; replace every 72 hours; fold used patch in half and flush or dispose in sharps box; do NOT apply direct heat", "Apply heating pad over fentanyl patch to increase absorption"),
            Triple("Isotretinoin (Accutane) IPLEDGE Program", "Requires two negative pregnancy tests before starting, 2 forms of contraception; highly teratogenic", "Safe during pregnancy and breast feeding"),
            Triple("Methotrexate Safety & Folic Acid", "Immunosuppressant/chemotherapy; risk of bone marrow suppression; folic acid supplementation reduces toxicity; teratogenic", "Encourage pregnancy while on methotrexate"),
            Triple("Cyclosporine Organ Transplant Rejection Prevention", "Immunosuppressant; risk of nephrotoxicity and infection; avoid grapefruit juice which increases drug concentration", "Drink 2 glasses of grapefruit juice with cyclosporine"),
            Triple("Epoetin Alfa (Epogen) Hemoglobin Target", "Stimulates erythropoiesis in CKD/chemo; hold if Hemoglobin > 11 g/dL to avoid stroke/thromboembolism", "Continue giving epoetin when hemoglobin is 16 g/dL"),
            Triple("Filgrastim (Neupogen) Purpose", "Granulocyte colony-stimulating factor that increases neutrophil count in neutropenic patients; adverse effect: bone pain", "Increases platelet count and causes severe diarrhea"),
            Triple("Oprelvekin (Neumega) Purpose", "Thrombopoietic growth factor that stimulates platelet production; adverse effect: fluid retention/edema", "Destroys platelets and causes dry mouth"),
            Triple("Bisphosphonates (Alendronate) Patient Education", "Take with full glass of plain water in morning; remain sitting/standing upright for 30-60 minutes to prevent esophagitis", "Take at bedtime and lie down flat immediately"),
            Triple("Allopurinol Gout Prophylaxis", "Inhibits uric acid production; drink 2-3 Liters fluid daily to prevent renal calculi; report rash immediately (Stevens-Johnson syndrome)", "Restrict fluid intake and ignore skin rash"),
            Triple("Colchicine Acute Gout Management", "Decreases uric acid deposit inflammation in acute attacks; stop medication if severe nausea/vomiting/diarrhea occurs", "Continue taking colchicine despite profuse bloody diarrhea"),
            Triple("EpiPen (Epinephrine Auto-Injector) Teaching", "Inject intramuscularly into outer mid-thigh at 90-degree angle; hold for 3-10 seconds; seek emergency care immediately", "Inject subcutaneously into abdominal fat"),
            Triple("Theophylline Bronchodilator Toxicity", "Therapeutic level 10-20 mcg/mL; toxicity (>20) causes seizures, fatal ventricular dysrhythmias, severe vomiting", "Therapeutic level 100-200 mcg/mL"),
            Triple("Albuterol vs Ipratropium Inhaler Sequence", "Inhale short-acting beta agonist (Albuterol) FIRST to open airways, then anticholinergic (Ipratropium) or steroid", "Inhale steroid first then albuterol 1 second later"),
            Triple("Montelukast (Singulair) Leukotriene Modifier", "Used for asthma prophylaxis and maintenance (NOT for acute asthma attack); take in evening", "Use montelukast rescue inhaler during acute severe asthma attack"),
            Triple("Isoniazid (INH) Tuberculosis & Vitamin B6", "Anti-TB drug; adverse effect: peripheral neuropathy (prevented by Pyridoxine / Vitamin B6 supplementation) and hepatotoxicity", "Causes acute hypokalemia prevented by Vitamin C"),
            Triple("Rifampin Anti-TB Drug Side Effect", "Red-orange discoloration of bodily fluids (tears, sweat, urine, saliva) is an expected harmless side effect", "Panicked emergency response to orange urine"),
            Triple("Ethambutol Anti-TB Drug Adverse Effect", "Optic neuritis (decreased visual acuity, red-green color blindness); requires baseline and periodic eye exams", "Severe ototoxicity with irreversible deafness"),
            Triple("Pyrazinamide Anti-TB Drug Adverse Effect", "Hyperuricemia (gout attacks) and hepatotoxicity; monitor uric acid and liver enzymes", "Causes severe hyperglycemia"),
            Triple("Amphotericin B Systemic Antifungal Safety", "High risk of nephrotoxicity and infusion reactions ('shake and bake'); pre-medicate with diphenhydramine and acetaminophen; hydrate", "Infuse via rapid IV push without premedication"),
            Triple("Fluconazole (Diflucan) Antifungal Drug Interactions", "Inhibits CYP450 enzymes; increases blood levels of warfarin, phenytoin, and sulfonylureas", "Decreases warfarin levels causing rapid clotting"),
            Triple("Acyclovir (Zovirax) Hydration Requirement", "Antiviral for Herpes/Shingles; ensure high IV fluid hydration to prevent renal tubular crystallization/nephrotoxicity", "Restrict IV fluids during acyclovir infusion"),
            Triple("HAART HIV Medication Adherence", "Requires >95% strict dose adherence to prevent viral resistance and treatment failure", "Take HIV medications only when feeling sick"),
            Triple("Haloperidol (Haldol) Neuroleptic Malignant Syndrome (NMS)", "Life-threatening reaction: high fever (104°F), muscle rigidity ('lead pipe'), autonomic instability, elevated CK", "Mild hypothermia with muscle flaccidity"),
            Triple("Extrapyramidal Symptoms (EPS) & Benztropine", "Acute dystonia (muscle spasms of face/neck), akathisia (restlessness), parkinsonism; treat with IM Benztropine or Diphenhydramine", "Treat EPS with extra doses of haloperidol"),
            Triple("Tardive Dyskinesia Manifestations", "Involuntary repetitive movements of face, lip smacking, tongue protrusion, grimacing from long-term antipsychotic use", "Sudden high fever with chest pain"),
            Triple("Clozapine (Clozaril) Agranulocytosis Monitoring", "Second-generation antipsychotic; mandatory WBC and ANC monitoring due to risk of fatal agranulocytosis (ANC < 500)", "Monitor platelet count for thrombocytosis"),
            Triple("Valproic Acid (Depakote) Lab Monitoring", "Anticonvulsant/mood stabilizer; monitor liver function tests (hepatotoxicity), platelets (thrombocytopenia), and lipase (pancreatitis)", "Monitor serum calcium and troponin"),
            Triple("Carbamazepine (Tegretol) Adverse Effect", "Agranulocytosis/leukopenia, Stevens-Johnson syndrome, hyponatremia (SIADH-like effect); report fever/sore throat", "Causes severe hypernatremia"),
            Triple("Disulfiram (Antabuse) Alcohol Abstinence Teaching", "Avoid ALL forms of alcohol (aftershave, mouthwash, cough syrups, vinegars); severe reaction: flushing, throbbing headache, vomiting, hypotension", "Drink 1 glass of wine to test disulfiram efficacy"),
            Triple("Varenicline (Chantix) Smoking Cessation Risk", "Monitor for neuropsychiatric events (severe depression, suicidal ideation, vivid unusual dreams)", "Causes rapid weight gain and hypercalcemia"),
            Triple("Methadone Maintenance Purpose", "Synthetic opioid used for opioid detoxification and maintenance to prevent withdrawal without euphoria", "Causes immediate severe withdrawal symptoms"),
            Triple("Buprenorphine/Naloxone (Suboxone) Purpose", "Partial opioid agonist combined with antagonist to treat opioid use disorder and deter IV misuse", "Pure short-acting opioid with high abuse potential"),
            Triple("Sildenafil (Viagra) Nitrate Contraindication", "STRICTLY CONTRAINDICATED with nitrates (nitroglycerin, isosorbide) due to fatal refractory hypotension", "Take sildenafil together with sublingual nitroglycerin"),
            Triple("Oxytocin (Pitocin) Infusion Safety", "Titrate IV infusion via pump; hold/discontinue if uterine tachysystole occurs (>5 contractions in 10 min or contraction lasting >90 sec)", "Increase oxytocin when contractions occur every 30 seconds"),
            Triple("Terbutaline Tocolytic Side Effect", "Beta-2 agonist used to stop preterm labor; side effects: maternal tachycardia, palpitations, tremors, hyperglycemia", "Maternal bradycardia and severe constipation"),
            Triple("Magnesium Sulfate Toxicity Antidote", "Calcium Gluconate is the antidote; signs of toxicity: loss of deep tendon reflexes, respiratory depression (<12), oliguria (<30 mL/hr)", "Give protamine sulfate for magnesium toxicity"),
            Triple("Methylergonovine (Methergine) Contraindication", "Uterotonic for postpartum hemorrhage; CONTRAINDICATED in patients with Hypertension or Preeclampsia (causes severe vasoconstriction)", "Give methergine to patient with BP 180/110 mmHg"),
            Triple("Carboprost (Hemabate) Postpartum Hemorrhage Caution", "Prostaglandin uterotonic; CONTRAINDICATED in Asthma patients (causes severe bronchospasm); side effect: profuse diarrhea", "Give carboprost to patient with severe acute asthma"),
            Triple("Misoprostol (Cytotec) Purpose", "Prostaglandin E1 analogue used for cervical ripening, labor induction, and postpartum hemorrhage control", "Used to treat acute hypertension"),
            Triple("Rho(D) Immune Globulin (RhoGAM) Timing", "Administer to Rh-negative mothers at 28 weeks gestation and within 72 hours post-delivery of Rh-positive infant", "Administer to Rh-positive mothers carrying Rh-negative infant"),
            Triple("Phytonadione (Vitamin K) Newborn Rationale", "Administered IM to neonates within 1 hour of birth to prevent hemorrhagic disease due to sterile gut flora", "Administered to induce immediate voiding"),
            Triple("Erythromycin Ophthalmic Ointment Newborn Purpose", "Applied to neonate eyes within 1-2 hours of birth to prevent ophthalmia neonatorum caused by Neisseria gonorrhoeae / Chlamydia", "Applied to treat congenital cataracts"),
            Triple("Surfactant (Beractant) Intratracheal Purpose", "Administered via endotracheal tube to premature infants with Respiratory Distress Syndrome to lower alveolar surface tension", "Given orally to treat infant colic"),
            Triple("Palivizumab (Synagis) Indication", "Monoclonal antibody given monthly IM during winter to high-risk infants to prevent severe Respiratory Syncytial Virus (RSV)", "Oral vaccine for rotavirus diarrhea"),
            Triple("Pediatric Medication Dose Calculation Basis", "Pediatric doses are calculated strictly based on weight in kilograms (mg/kg) or Body Surface Area (BSA in m2)", "Calculate pediatric doses using adult age formulas"),
            Triple("Geriatric Pharmacokinetics Changes", "Decreased renal clearance, decreased hepatic metabolism, decreased gastric acid, increased body fat percentage", "Increased renal clearance and hyperactive liver metabolism"),
            Triple("Rights of Medication Administration", "Right Patient, Right Drug, Right Dose, Right Route, Right Time, Right Documentation, Right Reason, Right Refusal", "Right Patient, Right Drug, Right Guess, Right Speed"),
            Triple("IV Medication Reconstitution Safety", "Verify compatibility, sterility, dilution volume, infusion rate, and inspect for particulate matter/precipitation", "Mix incompatible IV drugs until cloudy crystals form")
        )

        pharmScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Pharmacology Safety Protocol: ${item.second}",
                "Dangerous Unsafe Practice: ${item.third}",
                "Discontinue drug without documentation",
                "Double the dosage if patient forgets"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pharmacology and Medication Safety",
                "NCLEX-RN / DHA • Hard",
                "Pharmacology Clinical Case #${idx + 1}: In administering and monitoring medication therapy involving ${item.first}, which clinical safety decision is correct?",
                options,
                correctPos,
                "Rationale: Medication safety principles for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice prevents drug toxicity, fatal adverse effects, or medication errors. Action '${item.third}' is unsafe.",
                "Pharmacology • ${item.first}"
            )
        }

        // =========================================================================
        // FUNDAMENTALS OF NURSING (60 QUESTIONS)
        // =========================================================================
        val fundScenarios = listOf(
            Triple("Hand Hygiene Gold Standard", "Alcohol-based hand rub is preferred for routine decontamination; use SOAP AND WATER when hands are visibly soiled or caring for C. difficile", "Use alcohol gel for C. difficile spores"),
            Triple("Airborne Precautions Diseases & Equipment", "Tuberculosis, Measles (Rubeola), Varicella (Chickenpox), Disseminated Zoster (MTV); negative pressure room, N95 respirator mask", "Surgical mask with open room door for TB"),
            Triple("Droplet Precautions Diseases & Equipment", "Influenza, Pertussis, Meningococcal Meningitis, Mumps, Rubella; private room, surgical mask within 3 feet", "N95 mask with positive pressure room for Mumps"),
            Triple("Contact Precautions Diseases & Equipment", "MRSA, VRE, C. difficile, Scabies, RSV; gown and gloves required for all contact", "Surgical mask only without gloves"),
            Triple("Donning PPE Correct Order", "Gown FIRST, Mask/Respirator second, Goggles/Face Shield third, Gloves LAST", "Gloves first then Gown and Mask"),
            Triple("Doffing PPE Correct Order", "Gloves FIRST, Goggles/Face Shield second, Gown third, Mask/Respirator LAST (at doorway)", "Mask first inside room then Gloves"),
            Triple("Sterile Field Integrity Rules", "Keep sterile items above waist level, never turn back on sterile field, 1-inch border is non-sterile", "Hold sterile gloves below knees and reach over field"),
            Triple("Chain of Infection Breaking", "Hand hygiene is the single most effective way to break the chain of infection transmission", "Wearing same unwashed gloves for 10 patients"),
            Triple("Surgical Asepsis vs Medical Asepsis", "Surgical asepsis (sterile technique) eliminates ALL microorganisms; Medical asepsis (clean technique) reduces number", "Medical asepsis eliminates all bacterial spores"),
            Triple("Blood Pressure Measurement Error (Cuff Too Small)", "Using a BP cuff that is too narrow/small yields a falsely HIGH blood pressure reading", "Yields a falsely LOW blood pressure reading"),
            Triple("Blood Pressure Measurement Error (Cuff Too Large)", "Using a BP cuff that is too wide/large yields a falsely LOW blood pressure reading", "Yields a falsely HIGH blood pressure reading"),
            Triple("Orthostatic Hypotension Assessment", "Drop in Systolic BP >= 20 mmHg or Diastolic BP >= 10 mmHg within 3 minutes of standing", "Increase in BP of 40 mmHg upon standing"),
            Triple("Pulse Deficit Definition", "Difference between apical pulse rate and radial pulse rate evaluated simultaneously by two nurses", "Difference between heart rate and respiratory rate"),
            Triple("Cheyne-Stokes Respiratory Pattern", "Rhythmic waxing and waning of breathing depth with recurring periods of apnea; seen in end-of-life/brain injury", "Rapid deep sighing breathing in DKA"),
            Triple("Kussmaul Breathing Pattern", "Deep, rapid, labored respiration characteristic of severe metabolic acidosis (DKA)", "Shallow slow breathing with prolonged apnea"),
            Triple("Pain Assessment Scale Selection", "Numeric 0-10 for alert adults; FACES scale for children >= 3 yrs; FLACC scale for non-verbal/infants", "Numeric 0-10 scale for 2-month-old infant"),
            Triple("Pressure Injury Stage 1", "Non-blanchable erythema of intact skin over a bony prominence", "Full thickness skin loss with exposed bone"),
            Triple("Pressure Injury Stage 2", "Partial-thickness loss of skin with exposed dermis (pink/red wound bed, serum blister)", "Intact skin with non-blanchable redness"),
            Triple("Pressure Injury Stage 3", "Full-thickness loss of skin with visible adipose (fat) tissue, epibole (rolled edges)", "Exposed bone, tendon, or muscle"),
            Triple("Pressure Injury Stage 4", "Full-thickness skin and tissue loss with exposed bone, tendon, cartilage, or muscle", "Intact blister filled with clear fluid"),
            Triple("Unstageable Pressure Injury", "Full-thickness skin loss where base is completely covered by slough (yellow/tan) or eschar (brown/black)", "Stage 1 reddened area over heels"),
            Triple("Deep Tissue Pressure Injury (DTPI)", "Persistent non-blanchable deep red, maroon, or purple discoloration or blood-filled blister", "Shallow pink ulcer bed without slough"),
            Triple("Braden Scale for Pressure Injury Risk", "Scores range 6 to 23; LOWER score indicates HIGHER risk of pressure injury (score < 18 is at-risk)", "Score of 23 indicates severe high risk"),
            Triple("Patient Repositioning Schedule", "Reposition bedbound clients at least every 2 hours; chairbound clients every 1 hour", "Reposition bedbound clients once every 12 hours"),
            Triple("Logrolling Technique Indication", "Used for clients with spinal cord injury/surgery to maintain straight spinal alignment during transfer", "Flex hips and twist spine during turning"),
            Triple("Transfer Belt (Gait Belt) Safety", "Apply securely around client's waist over clothing; hold belt from underneath (supinated grip)", "Hold client by arms or clothes without gait belt"),
            Triple("High Fowler's Position Angle", "Head of bed elevated to 60-90 degrees; optimal for eating, NG tube insertion, and severe dyspnea", "Head of bed flat at 0 degrees"),
            Triple("Semi-Fowler's Position Angle", "Head of bed elevated to 30-45 degrees; standard for mechanical ventilation and tube feeding", "Head of bed elevated to 90 degrees"),
            Triple("Trendelenburg Position", "Entire bed tilted with head down and feet elevated; used for severe hypotension or pelvic surgery", "Head elevated 90 degrees with legs dangling"),
            Triple("Reverse Trendelenburg Position", "Entire bed tilted with head elevated and feet down; promotes gastric emptying and reduces GERD", "Head lower than feet during gastric feeding"),
            Triple("Oral Care in Unconscious Patient", "Position patient in side-lying (lateral) position with head turned to side to prevent aspiration", "Place unconscious patient flat on back and pour water into mouth"),
            Triple("NG Tube Placement Verification Gold Standard", "Radiographic X-ray is mandatory gold standard before initiating initial tube feedings or meds", "Injecting air and listening with stethoscope over epigastrium"),
            Triple("NG Tube pH Measurement Verification", "Gastric aspirate pH should be <= 5.0 (acidic); pH > 6 suggests respiratory tract or intestinal placement", "Gastric pH of 8.5 confirms stomach placement"),
            Triple("Enteral Tube Feeding Gastric Residual Volume", "Check residual before feedings; re-infuse aspirate; hold feeding if residual > 250-500 mL (per protocol)", "Discard 400 mL residual and double feeding rate"),
            Triple("TPN Central Line Dressing Change", "Sterile technique, mask worn by nurse and patient (turned away), chlorhexidine skin prep, transparent occlusive dressing", "Clean technique with unsterile gauze changed weekly"),
            Triple("TPN Abrupt Discontinuation Risk", "Abrupt stopping causes severe rebound hypoglycemia; if TPN bag empties before new bag arrives, infuse 10% Dextrose in Water (D10W)", "Infuse normal saline at 10 mL/hr and ignore glucose"),
            Triple("Peripheral Intravenous Infiltration Signs", "Coolness, swelling, pallor, and pain at IV site; stop infusion, remove IV, elevate limb, apply warm/cold compress", "Warmth, redness, and red streak along vein"),
            Triple("Peripheral Intravenous Phlebitis Signs", "Warmth, redness, swelling, tenderness, and palpable cord along vein; stop infusion, remove IV, apply warm moist compress", "Coolness and severe blanching"),
            Triple("Intravenous Extravasation Management", "Infiltration of vesicant drug (dopamine, chemo); STOP infusion immediately, aspirate residual drug, check for antidote before removing IV", "Flush IV forcefully with 20 mL saline"),
            Triple("Fluid Volume Deficit (Hypovolemia) Signs", "Tachycardia, hypotension, dry mucous membranes, poor skin turgor (tenting), oliguria, elevated hematocrit/BUN", "Bradycardia, hypertension, distended neck veins"),
            Triple("Fluid Volume Excess (Hypervolemia) Signs", "Bounding pulse, hypertension, jugular vein distension (JVD), crackles in lungs, S3 heart sound, peripheral edema", "Tachycardia, hypotension, flat neck veins"),
            Triple("Serum Sodium Normal Range & Imbalance", "Normal 135-145 mEq/L; Hyponatremia causes confusion, seizures; Hypernatremia causes intense thirst, dry sticky mucous membranes", "Normal sodium is 10-20 mEq/L"),
            Triple("Serum Potassium Normal Range & Imbalance", "Normal 3.5-5.0 mEq/L; Hypokalemia causes U waves, muscle weakness; Hyperkalemia causes peaked T waves, fatal cardiac arrest", "Normal potassium is 12-15 mEq/L"),
            Triple("Serum Calcium Normal Range & Imbalance", "Normal 8.5-10.5 mg/dL; Hypocalcemia causes Trousseau/Chvostek signs; Hypercalcemia causes bone pain, kidney stones", "Normal calcium is 1.0-2.0 mg/dL"),
            Triple("Serum Magnesium Normal Range & Imbalance", "Normal 1.5-2.5 mEq/L; Hypomagnesemia causes hyperreflexia, Torsades de Pointes; Hypermagnesemia causes loss of DTRs", "Normal magnesium is 15-25 mEq/L"),
            Triple("Arterial Blood Gas Normal Values", "pH 7.35-7.45; PaCO2 35-45 mmHg; HCO3 22-26 mEq/L; PaO2 80-100 mmHg", "pH 6.50; PaCO2 10 mmHg; HCO3 80 mEq/L"),
            Triple("Respiratory Acidosis ABG Profile", "pH < 7.35, PaCO2 > 45 mmHg; caused by hypoventilation (COPD, opioid overdose, respiratory depression)", "pH > 7.45, PaCO2 < 35 mmHg"),
            Triple("Respiratory Alkalosis ABG Profile", "pH > 7.45, PaCO2 < 35 mmHg; caused by hyperventilation (anxiety, panic attack, pulmonary embolism)", "pH < 7.35, PaCO2 > 45 mmHg"),
            Triple("Metabolic Acidosis ABG Profile", "pH < 7.35, HCO3 < 22 mEq/L; caused by DKA, severe diarrhea, renal failure, shock", "pH > 7.45, HCO3 > 26 mEq/L"),
            Triple("Metabolic Alkalosis ABG Profile", "pH > 7.45, HCO3 > 26 mEq/L; caused by severe vomiting, nasogastric suctioning, diuretic overuse", "pH < 7.35, HCO3 < 22 mEq/L"),
            Triple("Informed Consent Nursing Role", "Nurse WITNESSES patient signature, confirms patient is competent, and verifies patient received explanation from provider", "Nurse explains surgical risks and obtains consent for surgeon"),
            Triple("Advance Directives (Living Will vs Power of Attorney)", "Living will specifies desired medical treatments; Durable Power of Attorney designates healthcare proxy decision maker", "Power of Attorney allows proxy to change patient's last will and testament"),
            Triple("Incident/Variance Report Guidelines", "Complete immediately after event, state objective facts ONLY, do NOT record mention of incident report in patient's medical record", "Document 'Incident report filled out' in nurse's clinical note"),
            Triple("HIPAA Client Privacy Violation", "Discussing patient information in public areas (hallway, elevator), accessing records of patients not assigned to nurse", "Sharing clinical data with assigned covering physician"),
            Triple("Restraint Safety Standards", "Requires physician order within 1 hour, quick-release knot, tie to bed frame (NOT side rails), check circulation/skin every 15-30 min", "Tie restraints to side rails with tight double knot"),
            Triple("Fall Risk Safety Bundle", "Call light within reach, bed in lowest position locked, non-skid footwear, clear clutter, yellow fall risk band", "Keep bed elevated to top height with side rails down"),
            Triple("Fire Safety RACE Protocol", "R = Rescue clients in immediate danger; A = Activate fire alarm; C = Contain fire by closing doors; E = Extinguish/Evacuate", "E = Extinguish first before rescuing clients"),
            Triple("Fire Extinguisher PASS Protocol", "P = Pull pin; A = Aim at base of fire; S = Squeeze handle; S = Sweep side to side", "A = Aim at top of flames"),
            Triple("Foley Urinary Catheter Insertion Technique", "Sterile gloves/drapes, clean urinary meatus, inflate balloon with sterile water ONLY (never air or saline)", "Inflate balloon with 10 mL room air"),
            Triple("Enema Administration Positioning & Height", "Position patient in Left Sims (left side-lying with right knee flexed); hold container 12-18 inches above anus", "Position patient flat on right side and hold bag 5 feet high")
        )

        fundScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Standard Nursing Care: ${item.second}",
                "Unsafe Practice: ${item.third}",
                "Document action without performing assessment",
                "Request family member to perform procedure"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Fundamentals of Nursing",
                "NCLEX-RN / Prometric • Medium",
                "Fundamentals Clinical Case #${idx + 1}: In performing essential nursing care involving ${item.first}, which clinical decision represents standard evidence-based practice?",
                options,
                correctPos,
                "Rationale: Core nursing fundamentals for ${item.first} dictate: ${item.second}.",
                "Option breakdown: Correct answer adheres to basic care, infection control, and safety protocols. Action '${item.third}' is unsafe.",
                "Fundamentals • ${item.first}"
            )
        }

        return list
    }
}
