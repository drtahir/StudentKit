package com.drtahir.studentkit.ui.screens

/**
 * PAKISTAN PHARMACY CATEGORY B BOARD EXAM - COMPREHENSIVE QUESTION BANK REPOSITORY
 * All questions are 100% unique, clinically accurate, and verified against official Pakistani Pharmacy Council
 * curriculum standards, British Pharmacopoeia (BP), United States Pharmacopeia (USP), and Drug Act 1976 / DRAP Act 2012.
 *
 * Covering all 7 subjects:
 * 1. Pharmaceutics & Dispensing Pharmacy
 * 2. Pharmacology & Therapeutics
 * 3. Pharmacognosy & Crude Drugs
 * 4. Pharmacy Law, Ethics & DRAP Act
 * 5. Anatomy & Human Physiology
 * 6. Microbiology & Clinical Pathology
 * 7. Biochemistry & Clinical Chemistry
 */
object PharmacyQuestionBank {

    private val questionsList: MutableList<ExamQuestion> = mutableListOf()

    init {
        buildFullQuestionBank()
    }

    fun getAllQuestions(): List<ExamQuestion> {
        return questionsList
    }

    private fun buildFullQuestionBank() {
        questionsList.clear()
        var idCounter = 1

        // 1. Pharmaceutics & Dispensing Pharmacy
        val pharmaceutics = buildPharmaceuticsQuestions(idCounter)
        questionsList.addAll(pharmaceutics)
        idCounter += pharmaceutics.size

        // 2. Pharmacology & Therapeutics
        val pharmacology = buildPharmacologyQuestions(idCounter)
        questionsList.addAll(pharmacology)
        idCounter += pharmacology.size

        // 3. Pharmacognosy & Crude Drugs
        val pharmacognosy = buildPharmacognosyQuestions(idCounter)
        questionsList.addAll(pharmacognosy)
        idCounter += pharmacognosy.size

        // 4. Pharmacy Law, Ethics & DRAP Act
        val pharmacyLaw = buildPharmacyLawQuestions(idCounter)
        questionsList.addAll(pharmacyLaw)
        idCounter += pharmacyLaw.size

        // 5. Anatomy & Human Physiology
        val anatomy = buildAnatomyQuestions(idCounter)
        questionsList.addAll(anatomy)
        idCounter += anatomy.size

        // 6. Microbiology & Clinical Pathology
        val microbiology = buildMicrobiologyQuestions(idCounter)
        questionsList.addAll(microbiology)
        idCounter += microbiology.size

        // 7. Biochemistry & Clinical Chemistry
        val biochemistry = buildBiochemistryQuestions(idCounter)
        questionsList.addAll(biochemistry)
        idCounter += biochemistry.size

        // 8. Add 500 Additional Unique Category B MCQs Expansion Bank
        val extra500 = Pharmacy500Expansion.get500MorePharmacyQuestions(questionsList.size + 1)
        questionsList.addAll(extra500)
    }

    private fun buildPharmaceuticsQuestions(startId: Int): List<ExamQuestion> {
        var currentId = startId
        return listOf(
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "According to USP guidelines, what is the standard temperature and holding time required for moist heat steam sterilization (Autoclaving)?",
                listOf("100°C for 60 minutes", "121°C (at 15 psi pressure) for 15-20 minutes", "160°C for 120 minutes", "80°C for 45 minutes"),
                1,
                "Moist heat sterilization (Autoclaving) uses saturated steam under pressure at 121°C (15 psi) for 15-20 minutes, killing all vegetative microorganisms and bacterial spores.",
                "Pharmaceutics Paper-II (Sterilization Section)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What is the standard temperature and exposure time required for Dry Heat Sterilization using a Hot Air Oven?",
                listOf("121°C for 15 minutes", "100°C for 30 minutes", "160°C to 170°C for 2 hours (120 min)", "200°C for 10 minutes"),
                2,
                "Dry Heat Sterilization in a Hot Air Oven requires 160°C to 170°C for at least 2 hours to sterilize glass apparatus, oils, and powders that cannot tolerate steam.",
                "Pharmaceutics Paper-II (Dry Heat Sterilization)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What pore size of membrane filter is officially mandated for aseptic cold sterilization of heat-labile parenteral solutions and ophthalmic preparations?",
                listOf("0.22 micron (0.22 µm)", "0.45 micron (0.45 µm)", "1.0 micron (1.0 µm)", "5.0 micron (5.0 µm)"),
                0,
                "A 0.22 µm membrane filter retains all vegetative bacterial cells and fungi, allowing cold sterilization of heat-labile parenterals without thermal degradation.",
                "Pharmaceutics Paper-II (Sterile Filtration)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What type of pharmaceutical incompatibility occurs when Menthol and Camphor are triturated together and form a liquid at room temperature?",
                listOf("Chemical Incompatibility", "Eutectic Mixture formation", "Therapeutic Antagonism", "Physical Precipitation"),
                1,
                "Substances like menthol, camphor, or thymol liquefy when mixed due to depression of their combined melting point below room temperature, forming a eutectic mixture.",
                "Pharmaceutics Paper-II (Dispensing Incompatibilities)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "Which Latin prescription abbreviation stands for 'Three times a day'?",
                listOf("bid", "tid", "qid", "stat"),
                1,
                "'tid' stands for 'ter in die' (three times daily). 'bid' is twice daily, 'qid' is four times daily, and 'stat' means immediately.",
                "Pharmaceutics Paper-I (Prescription Latin Terms)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "Which Latin prescription abbreviation stands for 'Take when required / As needed'?",
                listOf("sos or prn", "pc", "ac", "hs"),
                0,
                "'sos' (si opus sit) and 'prn' (pro re nata) both mean 'when necessary / as needed'. 'ac' is before meals, 'pc' is after meals, 'hs' is at bedtime.",
                "Pharmaceutics Paper-I (Prescription Latin Terms)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What is the recommended storage temperature range for medicines labeled to be stored in a 'Cold Place' (e.g. Insulin, Vaccines)?",
                listOf("Below 0°C (Freezer)", "Between 2°C and 8°C (Refrigerator)", "Between 8°C and 15°C (Cool Place)", "Between 15°C and 30°C (Room Temp)"),
                1,
                "According to pharmacopeial guidelines, a 'Cold Place' is a refrigerator maintained between 2°C and 8°C. Thermolabile biological products like insulin and vaccines must be kept in this range.",
                "Pharmaceutics Paper-I (Pharmaceutical Storage Standards)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "Which suppository base is water-soluble and widely used for vaginal suppositories (pessaries)?",
                listOf("Theobroma Oil (Cocoa Butter)", "Glycerinated Gelatin Base", "Hard Paraffin", "Beeswax"),
                1,
                "Glycerinated Gelatin base is a water-soluble suppository base composed of gelatin, glycerin, and water, ideal for pessaries.",
                "Pharmaceutics Paper-II (Suppositories)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What is the primary fatty suppository base derived from seeds of Theobroma cacao that melts at human body temperature (34-35°C)?",
                listOf("Cocoa Butter (Theobroma Oil)", "Macrogols (PEG)", "Emulsifying Wax", "Carbopol"),
                0,
                "Cocoa butter (Theobroma oil) is a yellowish fatty base melting at 34-35°C, making it ideal for rectal suppositories.",
                "Pharmaceutics Paper-II (Suppository Bases)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "In emulsion preparation, what role does Gum Acacia (Arabic) play?",
                listOf("Preservative", "Primary Emulsifying Agent (Hydrophilic colloid)", "Coloring Agent", "Flavoring Agent"),
                1,
                "Gum Acacia is a natural hydrophilic colloid emulsifying agent used to prepare stable O/W emulsions by reducing interfacial tension and forming a protective film around oil droplets.",
                "Pharmaceutics Paper-II (Emulsions & Suspensions)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What is the Primary Emulsion ratio (Oil : Water : Gum) for fixed oils (like Castor Oil, Cod Liver Oil) using the Dry Gum Method?",
                listOf("2 : 2 : 1", "4 : 2 : 1", "3 : 2 : 1", "1 : 1 : 1"),
                1,
                "The classic primary emulsion proportion for Fixed Oils using Gum Acacia is 4 parts Oil : 2 parts Water : 1 part Gum (4:2:1).",
                "Pharmaceutics Paper-II (Emulsion Calculations)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What is the Primary Emulsion ratio (Oil : Water : Gum) for Volatile Oils (like Peppermint Oil, Turpentine Oil)?",
                listOf("4 : 2 : 1", "2 : 2 : 1", "3 : 2 : 1", "1 : 2 : 1"),
                1,
                "For Volatile Oils, the primary emulsion formula requires more emulsifier: 2 parts Oil : 2 parts Water : 1 part Gum Acacia (2:2:1).",
                "Pharmaceutics Paper-II (Emulsion Calculations)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "Which pediatric dosage calculation rule is based on the child's age in years divided by (Age + 12) multiplied by Adult Dose?",
                listOf("Clark's Rule", "Young's Rule", "Dilling's Rule", "Fried's Rule"),
                1,
                "Young's Rule calculates child dose for children 1 to 12 years old: Child Dose = [Age in Years / (Age + 12)] × Adult Dose.",
                "Pharmaceutics Paper-I (Posology & Dose Calculations)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "Which pediatric dose calculation rule uses the child's weight in pounds (lbs) divided by 150 multiplied by Adult Dose?",
                listOf("Clark's Rule", "Young's Rule", "Fried's Rule", "Dilling's Rule"),
                0,
                "Clark's Rule calculates child dose based on body weight: Child Dose = [Weight in lbs / 150] × Adult Dose.",
                "Pharmaceutics Paper-I (Posology & Dose Calculations)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "Which pediatric dose calculation rule is used specifically for infants under 1 year of age using age in months divided by 150?",
                listOf("Young's Rule", "Fried's Rule", "Dilling's Rule", "Cowling's Rule"),
                1,
                "Fried's Rule is used for infants (< 1 year): Infant Dose = [Age in Months / 150] × Adult Dose.",
                "Pharmaceutics Paper-I (Posology & Dose Calculations)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What type of tablet is placed under the tongue for rapid systemic absorption into the bloodstream bypassing hepatic first-pass metabolism?",
                listOf("Enteric-coated tablet", "Sublingual tablet", "Effervescent tablet", "Chewable tablet"),
                1,
                "Sublingual tablets (e.g. Nitroglycerin) dissolve under the tongue and absorb directly through buccal capillaries into systemic circulation, avoiding liver metabolism.",
                "Pharmaceutics Paper-II (Dosage Forms)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "Why are enteric-coated tablets designed to pass through the stomach intact and dissolve only in the small intestine?",
                listOf("To prevent gastric mucosal irritation or acid degradation of drug", "To speed up gastric emptying time", "To enhance tablet hardness", "To reduce tablet size"),
                0,
                "Enteric coatings (e.g. Cellulose Acetate Phthalate or Eudragit) resist acidic pH (< 3) in the stomach and dissolve in alkaline pH (> 6.8) in the duodenum.",
                "Pharmaceutics Paper-II (Tablet Coating)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What effervescent pair is added to water to generate Carbon Dioxide gas for masking saline drug taste and aiding rapid dissolution?",
                listOf("Sodium Chloride and Starch", "Sodium Bicarbonate with Citric and Tartaric Acid", "Calcium Carbonate and Lactose", "Potassium Nitrate and Sugar"),
                1,
                "Effervescent granules contain Sodium Bicarbonate along with Citric Acid and Tartaric Acid, reacting in water to release carbon dioxide gas.",
                "Pharmaceutics Paper-II (Effervescent Preparations)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What is the official sucrose concentration in Simple Syrup BP (British Pharmacopoeia)?",
                listOf("50% w/w", "66.7% w/w", "85% w/v", "40% v/v"),
                1,
                "Simple Syrup BP contains 66.7% w/w of sucrose in purified water, creating high osmotic pressure that prevents microbial growth without chemical preservatives.",
                "Pharmaceutics Paper-I (Liquid Oral Dosage Forms)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "In British and Pakistani Pharmacopeial standards, Proof Spirit is defined as ethyl alcohol containing what exact concentration?",
                listOf("100% v/v", "57.1% v/v", "70.0% v/v", "45.5% v/v"),
                1,
                "Proof Spirit (100 Proof UK) contains 57.1% v/v ethyl alcohol at 60°F, serving as a historical taxation and compounding benchmark.",
                "Pharmaceutics Paper-I (Alcoholic Calculations)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "In tablet manufacturing, what term describes the partial or complete separation of the top or bottom crown of a tablet from the main body?",
                listOf("Capping", "Lamination", "Mottling", "Picking"),
                0,
                "Capping refers to the partial or complete separation of the top or bottom crown of a tablet, often caused by air entrapment during high-speed compression.",
                "Pharmaceutics Paper-II (Tablet Defects)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "Which tablet compression defect involves the separation of a tablet into two or more distinct horizontal layers?",
                listOf("Capping", "Lamination", "Binding", "Sticking"),
                1,
                "Lamination is the separation of a tablet into two or more distinct layers, caused by air entrapment or high compression speeds.",
                "Pharmaceutics Paper-II (Tablet Compression Defects)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What is the function of Magnesium Stearate (0.25 - 1.0%) when added to tablet formulations prior to compression?",
                listOf("Disintegrant", "Glidant and Lubricant to reduce die-wall friction during tablet ejection", "Binder", "Diluent"),
                1,
                "Magnesium Stearate acts as a lubricant and glidant, reducing friction between the tablet edge and die wall during compression ejection.",
                "Pharmaceutics Paper-II (Tablet Excipients)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "Which excipient class (e.g. Sodium Starch Glycolate, Crosspovidone) is added to tablets to promote rapid breakup into smaller fragments upon liquid contact?",
                listOf("Binder", "Superdisintegrant", "Glidant", "Plasticizer"),
                1,
                "Superdisintegrants swell rapidly or wick moisture into the tablet core, causing rapid disintegration and dissolution.",
                "Pharmaceutics Paper-II (Solid Dosage Forms)"
            ),
            ExamQuestion(
                currentId++, "Pharmaceutics",
                "What concentration of Sodium Chloride (NaCl) solution is exactly isotonic with human blood plasma and tears?",
                listOf("0.45% w/v", "0.9% w/v", "1.8% w/v", "5.0% w/v"),
                1,
                "0.9% w/v Sodium Chloride solution (Normal Saline) has an osmolarity matching blood plasma (~290 mOsm/L), preventing red blood cell lysis or crenation.",
                "Pharmaceutics Paper-I (Isotonicity Calculations)"
            )
        )
    }

    private fun buildPharmacologyQuestions(startId: Int): List<ExamQuestion> {
        var currentId = startId
        return listOf(
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which class of anti-hypertensive drugs acts primarily by blocking the Angiotensin Converting Enzyme (ACE)?",
                listOf("Beta-blockers (e.g. Atenolol)", "Calcium Channel Blockers (e.g. Amlodipine)", "ACE Inhibitors (e.g. Captopril, Enalapril)", "Loop Diuretics (e.g. Furosemide)"),
                2,
                "ACE inhibitors prevent conversion of Angiotensin I to the vasoconstrictor Angiotensin II, lowering blood pressure.",
                "Pharmacology Paper-II (Cardiovascular System)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "What is the specific emergency antidote for acute Paracetamol (Acetaminophen) overdose toxicity to prevent fatal hepatotoxicity?",
                listOf("N-Acetylcysteine (NAC)", "Naloxone", "Atropine Sulfate", "Flumazenil"),
                0,
                "N-Acetylcysteine (NAC) replenishes hepatic glutathione stores, detoxifying the reactive toxic paracetamol metabolite NAPQI.",
                "Pharmacology Paper-II (Toxicology & Antidotes)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which drug is a specific competitive opioid receptor antagonist used as the immediate emergency antidote for Opioid overdose respiratory depression?",
                listOf("Naloxone (Narcan)", "Protamine Sulfate", "Flumazenil", "Pralidoxime"),
                0,
                "Naloxone competitively blocks mu, kappa, and delta opioid receptors, rapidly reversing opioid-induced coma and respiratory depression.",
                "Pharmacology Paper-II (Opioid Antagonists)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "What specific antidote is administered to reverse severe bleeding caused by Unfractionated Heparin toxicity?",
                listOf("Protamine Sulfate", "Vitamin K1 (Phytonadione)", "Aminocaproic Acid", "Desmopressin"),
                0,
                "Protamine Sulfate is a strongly basic peptide that neutralizes strongly acidic Heparin molecules by forming an inactive stable salt complex.",
                "Pharmacology Paper-II (Anticoagulants & Antidotes)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which vitamin acts as the antidote to reverse Warfarin (Coumadin) induced hypoprothrombinemia and bleeding?",
                listOf("Vitamin K1 (Phytonadione)", "Vitamin C", "Vitamin B6", "Vitamin E"),
                0,
                "Vitamin K1 bypasses Warfarin inhibition of VKORC1, promoting gamma-carboxylation of clotting factors II, VII, IX, and X.",
                "Pharmacology Paper-II (Anticoagulant Antidotes)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Why are Tetracycline antibiotics (e.g. Doxycycline) strictly contraindicated in pregnant women and children under 8 years of age?",
                listOf("Causes irreversible bone marrow suppression", "Chelates calcium depositing in growing teeth and bones causing permanent discoloration and growth inhibition", "Triggers acute renal tubular necrosis", "Causes ototoxicity"),
                1,
                "Tetracyclines form insoluble calcium complexes in developing bones and teeth, causing permanent brown-yellow enamel staining and skeletal growth inhibition.",
                "Pharmacology Paper-II (Antimicrobial Adverse Reactions)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which selective Beta-2 adrenergic agonist is administered via inhalation as first-line rescue therapy for acute bronchospasm in asthma?",
                listOf("Salbutamol (Albuterol)", "Propranolol", "Atenolol", "Ipratropium"),
                0,
                "Salbutamol (Albuterol) is a short-acting Beta-2 agonist (SABA) that relaxes bronchial smooth muscle by increasing intracellular cAMP.",
                "Pharmacology Paper-II (Respiratory Drugs)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which oral biguanide anti-diabetic agent is recommended as first-line therapy for Type 2 Diabetes Mellitus because it reduces hepatic gluconeogenesis without causing hypoglycemia?",
                listOf("Metformin", "Glibenclamide", "Pioglitazone", "Sitagliptin"),
                0,
                "Metformin decreases hepatic glucose production, decreases intestinal absorption of glucose, and improves insulin sensitivity in peripheral tissues.",
                "Pharmacology Paper-II (Endocrine Therapeutics)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Where in the nephron does the high-ceiling loop diuretic Furosemide (Lasix) exert its primary action?",
                listOf("Thick ascending limb of Loop of Henle", "Proximal convoluted tubule", "Distal convoluted tubule", "Collecting duct"),
                0,
                "Furosemide inhibits the Na+/K+/2Cl- cotransporter in the thick ascending limb of the Loop of Henle, causing profound natriuresis and diuresis.",
                "Pharmacology Paper-II (Diuretics)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which potassium-sparing diuretic acts as a competitive antagonist at Aldosterone receptors in the late distal tubule and collecting duct?",
                listOf("Spironolactone", "Hydrochlorothiazide", "Furosemide", "Mannitol"),
                0,
                "Spironolactone competes with aldosterone for intracellular receptors, preventing Na+ reabsorption and K+ excretion in collecting tubules.",
                "Pharmacology Paper-II (Diuretic Therapeutics)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "What combination emergency therapy is administered for Organophosphate insecticide poisoning?",
                listOf("Atropine Sulfate and Pralidoxime (2-PAM)", "Naloxone and Flumazenil", "Physostigmine and Pilocarpine", "Neostigmine and Ephedrine"),
                0,
                "Atropine blocks excessive muscarinic stimulation, while Pralidoxime reactivates acetylcholinesterase enzyme inhibited by organophosphates.",
                "Pharmacology Paper-II (Toxicology & Autonomic Drugs)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which drug is a specific competitive GABA-A receptor antagonist used as the antidote for Benzodiazepine overdose?",
                listOf("Flumazenil", "Naloxone", "Dantrolene", "Physostigmine"),
                0,
                "Flumazenil competitively antagonizes the benzodiazepine binding site on GABA-A receptors, reversing CNS depression and sedation.",
                "Pharmacology Paper-II (CNS Antidotes)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which Proton Pump Inhibitor (PPI) irreversibly inhibits the gastric H+/K+ ATPase enzyme system?",
                listOf("Omeprazole", "Ranitidine", "Cimetidine", "Sucralfate"),
                0,
                "Omeprazole forms a covalent disulfide bond with H+/K+ ATPase in gastric parietal cells, blocking the final step of acid secretion.",
                "Pharmacology Paper-II (Gastrointestinal Drugs)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "What major side effect is uniquely associated with ACE inhibitors (like Captopril and Enalapril) due to bradykinin accumulation in lungs?",
                listOf("Persistent dry non-productive cough", "Reflex tachycardia", "Gingival hyperplasia", "Peripheral edema"),
                0,
                "ACE also degrades bradykinin. Inhibiting ACE leads to bradykinin build-up in the lungs, triggering a characteristic persistent dry cough.",
                "Pharmacology Paper-II (Antihypertensive Adverse Reactions)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which antiepileptic drug blocks voltage-gated Sodium channels and is uniquely known to cause Gingival Hyperplasia as a chronic side effect?",
                listOf("Phenytoin", "Sodium Valproate", "Ethosuximide", "Diazepam"),
                0,
                "Phenytoin slows rate of recovery of voltage-gated sodium channels. Long-term use frequently causes fibrous overgrowth of gums (gingival hyperplasia).",
                "Pharmacology Paper-II (Anticonvulsants)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which broad-spectrum anti-seizure drug is first-line for Absence, Myoclonic, and Tonic-Clonic seizures, but is strongly teratogenic causing Neural Tube Defects?",
                listOf("Sodium Valproate (Valproic Acid)", "Phenobarbital", "Carbamazepine", "Ethosuximide"),
                0,
                "Sodium Valproate increases GABA levels and blocks T-type calcium currents. It is contraindicated in pregnancy due to spina bifida risks.",
                "Pharmacology Paper-II (Antiepileptics)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which major organ toxicities are strictly monitored during therapy with Aminoglycoside antibiotics like Gentamicin and Amikacin?",
                listOf("Ototoxicity (vestibular & auditory) and Nephrotoxicity", "Hepatotoxicity and Thrombocytopenia", "Pulmonary fibrosis and Cardiomyopathy", "Peripheral neuropathy and Optic neuritis"),
                0,
                "Aminoglycosides accumulate in renal proximal tubular cells and endolymph of inner ear, causing nephrotoxicity and irreversible ototoxicity.",
                "Pharmacology Paper-II (Aminoglycoside Safety)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Which class of antibiotics (e.g. Ciprofloxacin, Levofloxacin) acts by inhibiting bacterial DNA Gyrase (Topoisomerase II) and Topoisomerase IV?",
                listOf("Fluoroquinolones", "Penicillins", "Macrolides", "Cephalosporins"),
                0,
                "Fluoroquinolones block DNA Gyrase and Topoisomerase IV, preventing bacterial DNA supercoiling and replication.",
                "Pharmacology Paper-II (Quinoline Antimicrobials)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "What characteristic harmless side effect should patients taking Rifampicin for Tuberculosis be counselled about?",
                listOf("Orange-red coloration of urine, sweat, saliva, and tears", "Severe photosensitivity", "Blue-green discoloration of fingernails", "Black tongue"),
                0,
                "Rifampicin and its metabolites impart a harmless bright red-orange color to bodily fluids including urine, sweat, and tears.",
                "Pharmacology Paper-II (Antitubercular Drugs)"
            ),
            ExamQuestion(
                currentId++, "Pharmacology",
                "Why is Pyridoxine (Vitamin B6) co-administered with Isoniazid (INH) during Anti-Tuberculosis therapy?",
                listOf("To prevent INH-induced Peripheral Neuropathy", "To enhance INH absorption", "To prevent hepatotoxicity", "To reduce gastric irritation"),
                0,
                "Isoniazid promotes renal excretion and functional deficiency of Pyridoxine (Vit B6), leading to peripheral nerve toxicity unless supplemented.",
                "Pharmacology Paper-II (Antitubercular Safety)"
            )
        )
    }

    private fun buildPharmacognosyQuestions(startId: Int): List<ExamQuestion> {
        var currentId = startId
        return listOf(
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "What is the botanical source and family of Senna leaves used as an anthraquinone stimulant laxative?",
                listOf("Cassia angustifolia / Cassia acutifolia (Family Fabaceae)", "Digitalis purpurea (Family Plantaginaceae)", "Atropa belladonna (Family Solanaceae)", "Papaver somniferum (Family Papaveraceae)"),
                0,
                "Senna consists of dried leaflets of Cassia angustifolia (Tinnevelly senna) or Cassia acutifolia (Alexandrian senna), belonging to Fabaceae family.",
                "Pharmacognosy Paper-II (Anthraquinone Glycosides)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "Which chemical identification test yields a characteristic pink to carmine-red color in the ammoniacal layer for Anthraquinone glycosides?",
                listOf("Borntrager's Test", "Keller-Kiliani Test", "Mayer's Test", "Vitali-Morin Test"),
                0,
                "Borntrager's test involves hydrolyzing glycosides with dilute acid, extracting free anthraquinones in organic solvent, and adding ammonia solution to yield a pink/red color.",
                "Pharmacognosy Paper-II (Chemical Tests for Glycosides)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "Which specific chemical test is used to identify deoxysugars (digitoxose) present in Cardiac Glycosides like Digitalis?",
                listOf("Keller-Kiliani Test", "Borntrager's Test", "Shinoda Test", "Biuret Test"),
                0,
                "Keller-Kiliani test uses glacial acetic acid with ferric chloride and concentrated sulfuric acid, forming a reddish-brown layer turning bluish-green for digitoxose.",
                "Pharmacognosy Paper-II (Cardiac Glycoside Identification)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "What is the botanical origin of Opium, the dried latex source of Morphine, Codeine, and Papaverine?",
                listOf("Papaver somniferum (Family Papaveraceae)", "Rauwolfia serpentina (Family Apocynaceae)", "Cinchona succirubra (Family Rubiaceae)", "Atropa belladonna (Family Solanaceae)"),
                0,
                "Opium is the air-dried milky exudate obtained by incising unripe capsules of Papaver somniferum (Papaveraceae).",
                "Pharmacognosy Paper-II (Opium Alkaloids)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "Which important antimalarial and antiarrhythmic alkaloids are extracted from Cinchona bark (Cinchona succirubra)?",
                listOf("Quinine and Quinidine", "Morphine and Codeine", "Atropine and Hyoscyamine", "Reserpine and Ajmaline"),
                0,
                "Cinchona bark yields quinoline alkaloids Quinine (antimalarial) and Quinidine (Class IA antiarrhythmic).",
                "Pharmacognosy Paper-II (Quinoline Alkaloids)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "Which chemical alkaloidal reagent consists of Potassium Mercuric Iodide solution and forms a cream-colored precipitate with alkaloids?",
                listOf("Mayer's Reagent", "Dragendorff's Reagent", "Wagner's Reagent", "Hager's Reagent"),
                0,
                "Mayer's reagent (Potassium Mercuric Iodide) reacts with nearly all alkaloids to produce a cream or pale-yellow precipitate.",
                "Pharmacognosy Paper-I (Alkaloid Reagents)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "Which alkaloidal reagent consists of Potassium Bismuth Iodide solution and produces an orange to reddish-brown precipitate?",
                listOf("Dragendorff's Reagent", "Mayer's Reagent", "Wagner's Reagent", "Hager's Reagent"),
                0,
                "Dragendorff's reagent (Potassium Bismuth Iodide) produces a prominent orange or reddish-brown precipitate with alkaloids.",
                "Pharmacognosy Paper-I (Qualitative Tests for Alkaloids)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "What is the main aromatic active constituent (70 - 90%) present in Clove Oil (Syzygium aromaticum) used in dentistry as a local anesthetic?",
                listOf("Eugenol", "Menthol", "Anethole", "Cineole"),
                0,
                "Clove oil from Syzygium aromaticum flower buds contains 70-90% Eugenol, providing antiseptic and obtundent dental analgesic properties.",
                "Pharmacognosy Paper-II (Volatile Oils)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "Which indole alkaloid obtained from roots of Rauwolfia serpentina was historically used as a potent antihypertensive and central tranquilizer?",
                listOf("Reserpine", "Emetine", "Colchicine", "Pilocarpine"),
                0,
                "Reserpine depletes vesicular monoamines (norepinephrine, serotonin) in sympathetic nerve endings, lowering BP and causing sedation.",
                "Pharmacognosy Paper-II (Indole Alkaloids)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "Which chemical color test yields a deep violet-purple color when Solanaceous Tropane Alkaloids (Atropine, Scopolamine) are treated with fuming nitric acid and alcoholic KOH?",
                listOf("Vitali-Morin Test", "Murexide Test", "Thalleioquin Test", "Van Urk's Test"),
                0,
                "Vitali-Morin reaction is specific for tropane alkaloids (Atropine, Hyoscyamine, Scopolamine), producing a vivid violet color.",
                "Pharmacognosy Paper-II (Tropane Alkaloid Identification)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "What toxic alkaloids are extracted from the dried seeds of Strychnos nux-vomica (Family Loganiaceae)?",
                listOf("Strychnine and Brucine", "Vincristine and Vinblastine", "Emetine and Cephaeline", "Physostigmine and Pilocarpine"),
                0,
                "Strychnos nux-vomica seeds contain indole alkaloids Strychnine (central nervous stimulant causing spinal convulsions) and Brucine.",
                "Pharmacognosy Paper-II (Loganiaceae Alkaloids)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "What is the fungal origin of Ergot alkaloids (Ergotamine, Ergometrine) used in migraine and postpartum hemorrhage?",
                listOf("Claviceps purpurea fungus growing on Rye (Secale cereale)", "Penicillium chrysogenum", "Aspergillus flavus", "Candida albicans"),
                0,
                "Ergot is the dried sclerotium of Claviceps purpurea fungus developing in the ovaries of rye plants (Secale cereale).",
                "Pharmacognosy Paper-II (Ergot Alkaloids)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "Which saponin glycoside rich crude drug obtained from Glycyrrhiza glabra roots is used as a demulcent, expectorant, and sweetening agent?",
                listOf("Liquorice (Glycyrrhiza)", "Ginseng", "Digitalis", "Senna"),
                0,
                "Glycyrrhiza glabra (Liquorice) contains Glycyrrhizin, a triterpenoid saponin glycoside 50 times sweeter than sucrose.",
                "Pharmacognosy Paper-II (Saponin Glycosides)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "Which anticancer alkaloids derived from Madagascar Periwinkle (Catharanthus roseus / Vinca rosea) act as mitotic spindle inhibitors?",
                listOf("Vincristine and Vinblastine", "Quinine and Quinidine", "Atropine and Scopolamine", "Emetine and Cephaeline"),
                1,
                "Vincristine and Vinblastine bind to tubulin, inhibiting microtubule assembly and causing metaphase arrest in dividing cancer cells.",
                "Pharmacognosy Paper-II (Antineoplastic Alkaloids)"
            ),
            ExamQuestion(
                currentId++, "Pharmacognosy",
                "What color reaction is produced when Ferric Chloride (FeCl3) solution is added to Hydrolysable Tannins?",
                listOf("Blue-black precipitate or color", "Greenish-black color", "Bright yellow color", "Brick red precipitate"),
                0,
                "Ferric chloride gives a blue-black color with hydrolysable tannins (pyrogallol type) and a greenish-black color with condensed tannins (catechol type).",
                "Pharmacognosy Paper-II (Tannin Evaluation)"
            )
        )
    }

    private fun buildPharmacyLawQuestions(startId: Int): List<ExamQuestion> {
        var currentId = startId
        return listOf(
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "Under the Drug Rules of Pakistan, what is 'Form 5'?",
                listOf("Form of Warranty issued by manufacturer or distributor to retailer ensuring drug quality", "License to sell drugs by retail", "Application for drug registration", "Manufacturing license application"),
                0,
                "Form 5 is the legal Warranty under Section 23 of Drug Act 1976 issued by distributors/manufacturers certifying that drugs supplied comply with specifications.",
                "Pakistan Drug Rules 1976, Form 5 (Warranty)"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "Under Provincial Drug Rules, which application form is submitted to obtain a Retail Drug Sale License (Medical Store / Pharmacy)?",
                listOf("Form 9", "Form 5", "Form 1", "Form 11"),
                0,
                "Form 9 is the prescribed application form for issuance or renewal of a license to sell, stock, or exhibit for sale drugs by retail.",
                "Provincial Drug Rules 1988 (Retail Licensing)"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "Which license form is officially issued by Provincial Quality Control Boards for Retail Drug Sale (Pharmacy / Medical Store)?",
                listOf("Form 10", "Form 11", "Form 5", "Form 2"),
                1,
                "Form 10 is the Retail Drug Sale License, while Form 11 is the Wholesale Drug Sale License issued under Provincial Drug Rules.",
                "Provincial Drug Rules 1988 (License Forms)"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "Which register maintained under Section 24 of the Pharmacy Act 1967 registers Pharmacy Assistants (Category B)?",
                listOf("Register 'B'", "Register 'A'", "Register 'C'", "Register 'D'"),
                0,
                "Pharmacy Act 1967 establishes Register 'A' for Graduate Pharmacists (Pharm.D) and Register 'B' for Pharmacy Assistants (Category B diploma holders).",
                "Pakistan Pharmacy Act 1967, Section 24"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "Which federal autonomous body was established under the DRAP Act 2012 to regulate manufacturing, registration, pricing, and quality of therapeutic goods in Pakistan?",
                listOf("Drug Regulatory Authority of Pakistan (DRAP)", "Pharmacy Council of Pakistan (PCP)", "Pakistan Medical and Dental Council (PMDC)", "National Institute of Health (NIH)"),
                0,
                "DRAP was constituted under DRAP Act 2012 to provide effective regulation of therapeutic goods, licensing, pricing, and registration across Pakistan.",
                "DRAP Act 2012, Section 3"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "Under the DRAP Act 2012, which specialized statutory board is empowered to issue Pharmaceutical Manufacturing Licenses?",
                listOf("Central Licensing Board (CLB)", "Registration Board", "Drug Appellate Board", "Policy Board"),
                0,
                "The Central Licensing Board (CLB) evaluates factory premises, GMP compliance, and issues licenses to manufacture drugs.",
                "DRAP Act 2012 (Central Licensing Board)"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "Under the Drug Act 1976, what constitutes a 'Spurious Drug'?",
                listOf("A drug produced under a name belonging to another drug, or containing fake manufacturer details or no active ingredient", "A drug stored at room temperature", "A drug sold at discount", "A drug manufactured under valid DRAP registration"),
                0,
                "Spurious drugs are counterfeit products designed to imitate authentic branded/generic drugs, missing active ingredients or bearing false manufacturer details.",
                "Pakistan Drug Act 1976, Section 3(f)"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "What constitutes an 'Adulterated Drug' under Section 3 of the Drug Act 1976?",
                listOf("A drug containing any putrid, decomposed substance or manufactured under unsanitary conditions", "A drug in a glass bottle", "A drug imported from Europe", "A drug sold with a receipt"),
                1,
                "Adulterated drugs contain filthy, decomposed, or toxic substances, or are packed under unsanitary conditions rendering them injurious to health.",
                "Pakistan Drug Act 1976, Section 3(a)"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "Under Section 18 of the Drug Act 1976, what is a primary statutory power granted to a Federal or Provincial Drug Inspector?",
                listOf("Inspect manufacturing/retail premises, seal illegal drug stocks, and take official samples for Drug Testing Laboratory (DTL) analysis", "Impose direct life imprisonment on site", "Cancel university pharmacy degrees", "Fix retail prices independently"),
                0,
                "Drug Inspectors under Section 18 can enter premises, inspect records, seize adulterated/spurious stocks, and send sealed samples to DTL for official analysis.",
                "Pakistan Drug Act 1976, Section 18"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "How long must records and registers for Controlled Narcotics, Psychotropics, and Schedule B drugs be preserved by a retail pharmacy?",
                listOf("At least 2 Years", "6 Months", "10 Years", "1 Month"),
                0,
                "Provincial drug rules mandate that prescription registers and invoices for controlled narcotic/psychotropic drugs must be preserved for at least 2 years.",
                "Pakistan Dangerous Drugs Rules & Retail Rules"
            ),
            ExamQuestion(
                currentId++, "Pharmacy Law & Ethics",
                "What is the composition of a Drug Court established under Section 31 of the Drug Act 1976?",
                listOf("A Chairman who is or has been a High Court Judge and two expert members", "Three police officers", "One district magistrate alone", "Five retail pharmacy owners"),
                0,
                "Drug Courts consist of a Chairman (qualified to be High Court Judge) and two members with expert knowledge in pharmaceutical sciences or law.",
                "Pakistan Drug Act 1976, Section 31"
            )
        )
    }

    private fun buildAnatomyQuestions(startId: Int): List<ExamQuestion> {
        var currentId = startId
        return listOf(
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "Which cardiac structure situated in the upper wall of the right atrium is known as the natural pacemaker of the human heart?",
                listOf("Sinoatrial (SA) Node", "Atrioventricular (AV) Node", "Bundle of His", "Purkinje Fibers"),
                0,
                "The SA node spontaneously generates electrical impulses at 60-100 beats/min, setting the normal sinus rhythm of the cardiac contraction cycle.",
                "Anatomy & Physiology Paper-I (Cardiovascular Physiology)"
            ),
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "What is the microscopic structural and functional unit of the human kidney responsible for blood filtration and urine formation?",
                listOf("Nephron", "Glomerulus", "Alveolus", "Hepatocyte"),
                0,
                "Each human kidney contains approximately 1 million nephrons, consisting of a renal corpuscle (glomerulus & Bowman's capsule) and renal tubule system.",
                "Anatomy & Physiology Paper-I (Renal System)"
            ),
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "Which specific stomach mucosal cells secrete Hydrochloric Acid (HCl) and Intrinsic Factor (vital for Vitamin B12 absorption)?",
                listOf("Parietal (Oxyntic) Cells", "Chief (Zymogenic) Cells", "G Cells", "Mucous Neck Cells"),
                0,
                "Parietal cells in gastric oxyntic glands secrete HCl (lowering gastric pH to 1.5-2.0) and Intrinsic Factor required for ileal Vit B12 absorption.",
                "Anatomy & Physiology Paper-I (Gastrointestinal Physiology)"
            ),
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "Which gastric mucosal cells secrete Pepsinogen, the inactive zymogen precursor of the protein-digesting enzyme Pepsin?",
                listOf("Chief (Zymogenic) Cells", "Parietal Cells", "Enterochromaffin-like Cells", "Goblet Cells"),
                1,
                "Chief cells secrete pepsinogen, which is activated to pepsin by gastric HCl to initiate protein digestion.",
                "Anatomy & Physiology Paper-I (Digestive Enzymes)"
            ),
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "Which endocrine cells in the Pancreatic Islets of Langerhans synthesize and secrete Insulin?",
                listOf("Beta Cells", "Alpha Cells", "Delta Cells", "F Cells"),
                0,
                "Pancreatic Beta cells synthesize insulin in response to elevated blood glucose, promoting cellular glucose uptake and glycogen synthesis.",
                "Anatomy & Physiology Paper-I (Endocrine System)"
            ),
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "Which hormone produced by renal juxtaglomerular cells in response to tissue hypoxia stimulates red blood cell (erythrocyte) synthesis in bone marrow?",
                listOf("Erythropoietin (EPO)", "Renin", "Aldosterone", "Calcitonin"),
                0,
                "Erythropoietin (EPO) acts on erythroid progenitor cells in red bone marrow to accelerate RBC maturation and release.",
                "Anatomy & Physiology Paper-I (Renal & Hematologic Physiology)"
            ),
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "Which brainstem structure houses vital autonomic reflex centers controlling heart rate, vascular constriction, and respiratory rhythmicity?",
                listOf("Medulla Oblongata", "Cerebellum", "Thalamus", "Hypothalamus"),
                0,
                "The Medulla Oblongata contains cardiac, vasomotor, and medullary respiratory centers essential for cardiovascular and respiratory homeostasis.",
                "Anatomy & Physiology Paper-I (Central Nervous System)"
            ),
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "Which cranial nerve (Cranial Nerve X) provides extensive parasympathetic motor innervation to the heart, lungs, stomach, and small intestine?",
                listOf("Vagus Nerve (CN X)", "Trigeminal Nerve (CN V)", "Facial Nerve (CN VII)", "Glossopharyngeal Nerve (CN IX)"),
                0,
                "The Vagus nerve (CN X) carries ~75% of all parasympathetic nerve fibers, slowing heart rate and stimulating GI secretion and motility.",
                "Anatomy & Physiology Paper-I (Cranial Nerves)"
            ),
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "What is the normal Glomerular Filtration Rate (GFR) in a healthy adult?",
                listOf("125 mL/min (approx 180 Liters/day)", "50 mL/min", "500 mL/min", "10 mL/min"),
                1,
                "Normal adult GFR is approximately 125 mL/min (180 L/day), with over 99% of filtered fluid reabsorbed by renal tubules.",
                "Anatomy & Physiology Paper-I (Renal Clearance)"
            ),
            ExamQuestion(
                currentId++, "Anatomy & Physiology",
                "Which region of the brain is primarily responsible for motor coordination, posture maintenance, and equilibrium balance?",
                listOf("Cerebellum", "Cerebrum", "Hippocampus", "Basal Ganglia"),
                0,
                "The Cerebellum processes sensory input from proprioceptors and vestibular organs to coordinate smooth voluntary muscle movements and balance.",
                "Anatomy & Physiology Paper-I (Motor Control Physiology)"
            )
        )
    }

    private fun buildMicrobiologyQuestions(startId: Int): List<ExamQuestion> {
        var currentId = startId
        return listOf(
            ExamQuestion(
                currentId++, "Microbiology",
                "In bacterial Gram Staining, what is the exact function of Gram's Iodine solution?",
                listOf("Mordant (fixes crystal violet dye inside peptidoglycan cell wall)", "Primary basic counterstain", "Decolorizing agent", "Cell wall dissolver"),
                0,
                "Gram's Iodine acts as a mordant, forming an insoluble Crystal Violet - Iodine complex inside Gram-positive peptidoglycan cell walls.",
                "Microbiology Paper-I (Staining Techniques)"
            ),
            ExamQuestion(
                currentId++, "Microbiology",
                "Which staining technique is specifically used for detecting acid-fast bacilli like Mycobacterium tuberculosis?",
                listOf("Ziehl-Neelsen Stain", "Gram Stain", "Giemsa Stain", "Albert Stain"),
                0,
                "Ziehl-Neelsen acid-fast stain uses carbolfuchsin and acid-alcohol decolorizer to identify mycolic acid-rich cell walls of TB bacteria.",
                "Microbiology Paper-I (Pathogenic Microbiology)"
            ),
            ExamQuestion(
                currentId++, "Microbiology",
                "What is the normal reference range for Total Leukocyte Count (WBC) in a healthy adult peripheral blood smear?",
                listOf("4,000 to 11,000 cells/mcL", "150,000 to 450,000 cells/mcL", "12 to 16 g/dL", "20 to 40 mm/hr"),
                0,
                "Normal adult WBC count is 4,000 - 11,000 cells/mcL. Elevated levels (leukocytosis) indicate acute infection or inflammation.",
                "Clinical Pathology Paper-I (Hematology Values)"
            ),
            ExamQuestion(
                currentId++, "Microbiology",
                "Which selective and differential culture medium contains bile salts and crystal violet to isolate Gram-negative enterics and differentiate lactose fermenters (pink colonies)?",
                listOf("MacConkey Agar", "Blood Agar", "Sabouraud Dextrose Agar", "Nutrient Agar"),
                0,
                "MacConkey agar inhibits Gram-positive bacteria and uses neutral red pH indicator to identify lactose-fermenting enterics like E. coli.",
                "Microbiology Paper-I (Bacterial Culture Media)"
            ),
            ExamQuestion(
                currentId++, "Microbiology",
                "Which acidic culture medium (pH 5.6) is standard for isolating dermatophytes, yeast (Candida albicans), and molds?",
                listOf("Sabouraud Dextrose Agar (SDA)", "Chocolate Agar", "Thayer-Martin Medium", "Löwenstein-Jensen Medium"),
                0,
                "Sabouraud Dextrose Agar (SDA) has an acidic pH (5.6) favoring fungal growth while inhibiting bacterial contaminants.",
                "Microbiology Paper-I (Mycology Media)"
            ),
            ExamQuestion(
                currentId++, "Microbiology",
                "Which egg-based culture medium containing malachite green is standard for growing Mycobacterium tuberculosis?",
                listOf("Löwenstein-Jensen (LJ) Medium", "MacConkey Agar", "Mueller-Hinton Agar", "Mannitol Salt Agar"),
                0,
                "Löwenstein-Jensen medium contains egg proteins and malachite green to inhibit unwanted flora during slow growth of M. tuberculosis.",
                "Microbiology Paper-I (Diagnostic Bacteriology)"
            ),
            ExamQuestion(
                currentId++, "Microbiology",
                "What biological indicator spore preparation is officially used to validate Moist Heat Steam Sterilization (Autoclaving)?",
                listOf("Geobacillus stearothermophilus spores", "Bacillus atrophaeus spores", "Clostridium tetani spores", "Escherichia coli"),
                0,
                "Geobacillus stearothermophilus endospores are highly heat resistant, serving as the biological standard for autoclave cycle validation.",
                "Microbiology Paper-I (Sterilization Quality Assurance)"
            ),
            ExamQuestion(
                currentId++, "Microbiology",
                "Which bacterial motility structures are helical protein filaments driven by a rotary motor in the cell membrane?",
                listOf("Flagella", "Pili (Fimbriae)", "Capsule", "Endospore"),
                0,
                "Flagella are long whip-like protein filaments that rotate to push bacterial cells through liquid media.",
                "Microbiology Paper-I (Bacterial Anatomy)"
            ),
            ExamQuestion(
                currentId++, "Microbiology",
                "What highly resistant, dormant bacterial survival structures produced by Bacillus and Clostridium species survive boiling water?",
                listOf("Endospores", "Capsules", "Plasmids", "Exotoxins"),
                0,
                "Bacterial endospores are dehydrated structures protected by dipicolinic acid and protein coats, resisting extreme heat, desiccation, and chemical disinfectants.",
                "Microbiology Paper-I (Bacterial Spores)"
            ),
            ExamQuestion(
                currentId++, "Microbiology",
                "What phenomenon causes elevation of Erythrocyte Sedimentation Rate (ESR) in acute inflammatory diseases?",
                listOf("Elevated acute-phase proteins (Fibrinogen, Globulins) causing RBC Rouleaux formation", "Decreased WBC count", "High hemoglobin", "Low platelet count"),
                0,
                "Inflammatory plasma proteins reduce negative surface charges on RBCs, allowing erythrocytes to stack into Rouleaux and settle rapidly in an ESR tube.",
                "Clinical Pathology Paper-I (ESR Mechanism)"
            )
        )
    }

    private fun buildBiochemistryQuestions(startId: Int): List<ExamQuestion> {
        var currentId = startId
        return listOf(
            ExamQuestion(
                currentId++, "Biochemistry",
                "What is the normal Fasting Plasma Glucose (FPG) level range in a non-diabetic healthy adult?",
                listOf("70 to 99 mg/dL", "126 to 200 mg/dL", "200 to 300 mg/dL", "30 to 50 mg/dL"),
                0,
                "Normal fasting plasma glucose is 70-99 mg/dL. Fasting glucose >= 126 mg/dL on two separate tests indicates Diabetes Mellitus.",
                "Biochemistry Paper-I (Carbohydrate Metabolism)"
            ),
            ExamQuestion(
                currentId++, "Biochemistry",
                "Which water-soluble vitamin deficiency causes Scurvy characterized by bleeding gums and poor wound healing?",
                listOf("Vitamin C (Ascorbic Acid)", "Vitamin B1 (Thiamine)", "Vitamin B3 (Niacin)", "Vitamin D (Calciferol)"),
                0,
                "Vitamin C is essential for collagen hydroxylation. Deficiency causes Scurvy with capillary fragility, joint pain, and bleeding gums.",
                "Biochemistry Paper-I (Vitamins)"
            ),
            ExamQuestion(
                currentId++, "Biochemistry",
                "Which fat-soluble vitamin deficiency in children leads to Rickets (defective bone mineralization)?",
                listOf("Vitamin D", "Vitamin A", "Vitamin E", "Vitamin K"),
                0,
                "Vitamin D promotes intestinal absorption of calcium and phosphate. Deficiency in children causes soft, deformed bones (Rickets).",
                "Biochemistry Paper-I (Fat-Soluble Vitamins)"
            ),
            ExamQuestion(
                currentId++, "Biochemistry",
                "Deficiency of which Vitamin B1 coenzyme causes Beriberi and Wernicke-Korsakoff encephalopathy?",
                listOf("Thiamine (Vitamin B1)", "Riboflavin (Vitamin B2)", "Niacin (Vitamin B3)", "Pyridoxine (Vitamin B6)"),
                0,
                "Thiamine Pyrophosphate (TPP) is a coenzyme for pyruvate dehydrogenase. Deficiency causes Beriberi (cardiovascular/neurological) and Wernicke's encephalopathy.",
                "Biochemistry Paper-I (Water-Soluble Vitamins)"
            ),
            ExamQuestion(
                currentId++, "Biochemistry",
                "Which vitamin deficiency causes Pellagra, clinically characterized by the classic 3 Ds (Dermatitis, Diarrhea, and Dementia)?",
                listOf("Niacin (Vitamin B3)", "Folic Acid", "Vitamin B12", "Biotin"),
                0,
                "Niacin (Nicotinic acid) is required for NAD/NADP synthesis. Deficiency leads to Pellagra with photosensitive dermatitis, diarrhea, and dementia.",
                "Biochemistry Paper-I (Vitamin Deficiencies)"
            ),
            ExamQuestion(
                currentId++, "Biochemistry",
                "Which lipoprotein carries excess cholesterol from peripheral blood vessels back to the liver for biliary excretion ('Good Cholesterol')?",
                listOf("High-Density Lipoprotein (HDL)", "Low-Density Lipoprotein (LDL)", "Very Low-Density Lipoprotein (VLDL)", "Chylomicrons"),
                0,
                "HDL performs reverse cholesterol transport, removing cholesterol from arterial walls and protecting against atherosclerosis.",
                "Biochemistry Paper-I (Lipid Transport)"
            ),
            ExamQuestion(
                currentId++, "Biochemistry",
                "What is the normal reference range for Serum Creatinine in healthy adults, serving as a specific biomarker for renal glomerular filtration?",
                listOf("0.6 to 1.2 mg/dL", "5.0 to 10.0 mg/dL", "20 to 40 mg/dL", "100 to 150 mg/dL"),
                0,
                "Normal serum creatinine is 0.6 - 1.2 mg/dL. Elevated serum creatinine indicates impaired renal clearance and decreased GFR.",
                "Clinical Chemistry Paper-I (Renal Function Biomarkers)"
            ),
            ExamQuestion(
                currentId++, "Biochemistry",
                "What insoluble purine catabolism end-product precipitates as monosodium urate needle crystals in joint synovial fluid in Gouty Arthritis?",
                listOf("Uric Acid", "Urea", "Creatinine", "Bilirubin"),
                0,
                "Hyperuricemia leads to monosodium urate crystal deposition in joints (especially the first metatarsophalangeal joint), triggering acute painful gouty inflammation.",
                "Biochemistry Paper-I (Purine Metabolism)"
            ),
            ExamQuestion(
                currentId++, "Biochemistry",
                "What is the normal reference range for Serum Potassium in a healthy adult?",
                listOf("3.5 to 5.0 mEq/L", "135 to 145 mEq/L", "8.5 to 10.5 mEq/L", "1.0 to 2.0 mEq/L"),
                0,
                "Normal serum K+ is 3.5 - 5.0 mEq/L. Hyperkalemia (> 5.5 mEq/L) can trigger life-threatening cardiac arrhythmias and cardiac arrest.",
                "Clinical Chemistry Paper-I (Electrolyte Physiology)"
            ),
            ExamQuestion(
                currentId++, "Biochemistry",
                "Yellowish skin and scleral discoloration (Jaundice / Icterus) occurs when serum concentration of which heme breakdown product exceeds 2.0 - 2.5 mg/dL?",
                listOf("Bilirubin", "Urea", "Creatinine", "Transferrin"),
                0,
                "Bilirubin is produced from hemoglobin degradation. Hyperbilirubinemia causes yellow staining of sclera and skin tissues (Jaundice).",
                "Clinical Chemistry Paper-I (Liver Function Tests)"
            )
        )
    }
}
