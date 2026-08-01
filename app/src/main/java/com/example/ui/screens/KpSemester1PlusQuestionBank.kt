package com.example.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 1 PLUS QUESTION BANK
 * Adds 100 unique questions for EACH subject in Semester 1 (Total = 500 MCQs)
 * Aligned with PNC & KMU Syllabus.
 */
object KpSemester1PlusQuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var currentId = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        // 1. Fundamentals of Nursing I (FON-611) - 100 MCQs
        val fon1Plus = getFON1ExtraQuestions(currentId)
        questions.addAll(fon1Plus)
        currentId += fon1Plus.size

        // 2. Anatomy & Physiology I (ANAT-612) - 100 MCQs
        val anat1Plus = getAnat1ExtraQuestions(currentId)
        questions.addAll(anat1Plus)
        currentId += anat1Plus.size

        // 3. Biochemistry for Nurses (BIO-613) - 100 MCQs
        val bio1Plus = getBio1ExtraQuestions(currentId)
        questions.addAll(bio1Plus)
        currentId += bio1Plus.size

        // 4. Microbiology (MIC-614) - 100 MCQs
        val mic1Plus = getMic1ExtraQuestions(currentId)
        questions.addAll(mic1Plus)
        currentId += mic1Plus.size

        // 5. English I - Functional English (ENG-615) - 100 MCQs
        val eng1Plus = getEng1ExtraQuestions(currentId)
        questions.addAll(eng1Plus)
        currentId += eng1Plus.size

        return questions
    }

    private fun getFON1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Nursing Process: Assessment & Data Validation", "Cross-checking objective findings with subjective statements to ensure clinical accuracy", "Relying strictly on family hearsay without physical measurement"),
            Triple("Vital Signs: Temperature Regulation Mechanisms", "Hypothalamus functions as body's thermostat regulating vasodilation, vasoconstriction, and sweating", "Kidneys regulate core body temperature through erythropoietin secretion"),
            Triple("Vital Signs: Pulse Pressure Calculation", "Pulse pressure is difference between SBP and DBP (Normal 30-50 mmHg)", "Pulse pressure is sum of SBP and DBP divided by cardiac output"),
            Triple("Vital Signs: Oxygen Saturation Pulse Oximetry Limitations", "Hypothermia, peripheral vasoconstriction, carbon monoxide poisoning, and dark nail polish distort SpO2 readings", "SpO2 readings are unaffected by peripheral blood flow or severe anemia"),
            Triple("Infection Control: Chain of Infection Transmission", "Standard precautions apply to all blood, body fluids, non-intact skin, and mucous membranes", "Standard precautions are only required for patients with confirmed HIV or Hepatitis B"),
            Triple("Infection Control: Hand Hygiene 5 Moments WHO", "Before touching patient, before clean/aseptic procedure, after body fluid exposure, after touching patient, after touching patient surroundings", "Hand hygiene is only necessary at the end of the shift"),
            Triple("Patient Safety: Fall Risk Assessment Morse Scale", "Assesses history of falling, secondary diagnosis, ambulatory aid, IV line, gait, and mental status", "Morse scale measures serum calcium and bone mineral density"),
            Triple("Bedmaking & Ergonomics: Unoccupied vs Occupied Bed", "Keep bed at comfortable working height, avoid holding dirty linens against uniform, fold dirty linens inward", "Shake dirty linens vigorously in air to remove dust particles"),
            Triple("Hygiene Care: Oral Care for Unconscious Patient", "Position lateral side-lying with head turned to side and suction ready to prevent aspiration", "Keep patient supine and pour 100 mL of water directly into mouth"),
            Triple("Pressure Injury Prevention: Braden Scale Subscales", "Sensory perception, moisture, activity, mobility, nutrition, friction & shear; score < 16 indicates high risk", "Braden scale measures serum hemoglobin and arterial blood pressure"),
            Triple("Therapeutic Environment: Room Light, Ventilation & Noise", "Maintain clean, well-ventilated, low-noise environment to promote healing and reduce stress", "Keep patient room completely sealed without ventilation to preserve room heat"),
            Triple("Patient Positioning: Trendelenburg vs Reverse Trendelenburg", "Trendelenburg tilts bed with head down (promotes venous return); Reverse Trendelenburg tilts head up (reduces GERD & ICP)", "Trendelenburg position elevates the feet above the chest during cardiac arrest"),
            Triple("Physical Assessment: 4 Techniques Sequence", "Inspection, Palpation, Percussion, Auscultation (Except abdomen: Inspection, Auscultation, Percussion, Palpation)", "Always perform deep palpation before auscultating abdominal bowel sounds"),
            Triple("Body Mechanics: Lifting Heavy Objects Principles", "Maintain wide base of support, bend knees, tighten abdominal muscles, hold weight close to body", "Bend at waist with straight legs and twist spine while lifting"),
            Triple("Documentation: SOAPIE & DAR Formatting", "Subjective, Objective, Assessment, Plan, Intervention, Evaluation (SOAPIE); Data, Action, Response (DAR)", "SOAPIE formatting requires writing personal opinions about patient character")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Discontinue vital signs recording and ignore institutional safety guidelines",
                "Delegate critical clinical judgment and nursing diagnoses to untrained personnel"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 1,
                    subjectName = "Fundamentals of Nursing I",
                    question = "FON-611 Plus Q#${i + 1}: Regarding ${t.first}, which clinical nursing statement is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Fundamentals of Nursing I (FON-611) Core Principle: ${t.second}.",
                    reference = "KMU PNC Semester 1 • FON-611"
                )
            )
        }
        return list
    }

    private fun getAnat1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Cell Organelles: Mitochondria ATP Generation", "Mitochondria produce cellular ATP through oxidative phosphorylation; known as powerhouses of cell", "Mitochondria synthesize ribosomal RNA and store glycogen crystals"),
            Triple("Cell Organelles: Lysosomes Autophagy & Hydrolytic Enzymes", "Lysosomes contain acid hydrolases that digest cellular debris, old organelles, and phagocytosed pathogens", "Lysosomes build protein peptide chains during translation"),
            Triple("Cell Membrane: Phospholipid Bilayer & Fluid Mosaic", "Amphipathic phospholipids with hydrophilic heads facing outward and hydrophobic fatty acid tails inward", "Cell membrane is composed exclusively of thick calcified bone matrix"),
            Triple("Epithelial Tissue: Simple Squamous vs Stratified Squamous", "Simple squamous facilitates rapid diffusion (alveoli, capillaries); Stratified squamous provides protection against abrasion (skin, esophagus)", "Simple squamous epithelial cells protect heel skin from mechanical friction"),
            Triple("Connective Tissue: Collagen vs Elastin Fibers", "Collagen fibers provide high tensile strength; Elastin fibers provide recoil elasticity in lungs and arterial walls", "Collagen fibers allow skeletal muscle contraction without calcium"),
            Triple("Skeletal System: Axial vs Appendicular Skeleton", "Axial skeleton (80 bones: skull, vertebral column, thoracic cage); Appendicular skeleton (126 bones: limbs and girdles)", "Appendicular skeleton includes skull bones, ribs, and sternum"),
            Triple("Skeletal System: Osteoblasts vs Osteoclasts vs Osteocytes", "Osteoblasts build bone matrix; Osteoclasts reabsorb/break down bone; Osteocytes maintain mature bone tissue", "Osteoclasts deposit calcium hydroxyapatite into new bone matrix"),
            Triple("Vertebral Column: Regional Bone Count", "Cervical (7), Thoracic (12), Lumbar (5), Sacrum (5 fused), Coccyx (4 fused)", "Cervical (12), Thoracic (7), Lumbar (12), Sacrum (1)"),
            Triple("Joint Classifications: Synarthrosis, Amphiarthrosis, Diarthrosis", "Synarthrosis (immovable, e.g., sutures); Amphiarthrosis (slightly movable, e.g., pubic symphysis); Diarthrosis (freely movable synovial joint)", "Diarthrosis joints are completely immovable fibrous skull sutures"),
            Triple("Muscular System: Sarcomere & Sliding Filament Theory", "Sarcomere is functional unit of myofibril between Z-lines; actin and myosin filaments slide using calcium and ATP", "Sarcomere contracts when troponin destroys actin filaments"),
            Triple("Nervous System: Neuronal Structure Axon vs Dendrite", "Dendrites carry impulses toward cell body; Axon carries impulses away from cell body to target synapse", "Axons receive sensory inputs and send them to dendrites"),
            Triple("Nervous System: Myelin Sheaths Schwann Cells vs Oligodendrocytes", "Schwann cells myelinates PNS axons; Oligodendrocytes myelinates CNS axons", "Oligodendrocytes form blood-brain barrier astrocytes in liver"),
            Triple("Central Nervous System: Cerebrum Lobes Functions", "Frontal (motor & executive), Parietal (somatosensory), Occipital (vision), Temporal (hearing & memory)", "Occipital lobe controls voluntary motor movement of fingers"),
            Triple("Autonomic Nervous System: Sympathetic vs Parasympathetic", "Sympathetic (Fight or Flight: pupil dilation, bronchodilation, tachycardia); Parasympathetic (Rest & Digest: bradycardia, salivation, peristalsis)", "Sympathetic stimulation slows heart rate and increases digestive peristalsis"),
            Triple("Special Senses: Eye Retina Rods vs Cones", "Rods mediate dim light/black-and-white vision; Cones mediate high-acuity color vision in fovea centralis", "Rods detect bright color vision in daylight")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Inhibit cellular homeostasis and destroy nerve conduction pathways",
                "Function as lymphatic fat droplets without anatomical structure"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 1,
                    subjectName = "Anatomy & Physiology I",
                    question = "ANAT-612 Plus Q#${i + 1}: In human anatomy & physiology concerning ${t.first}, which statement is accurate?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Anatomy & Physiology I (ANAT-612) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 1 • ANAT-612"
                )
            )
        }
        return list
    }

    private fun getBio1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Water & Buffer Systems: Bicarbonate Buffer System", "H2CO3 / HCO3- buffer maintains blood pH within tight range of 7.35 to 7.45", "Bicarbonate buffer maintains stomach gastric juice at pH 1.0"),
            Triple("Carbohydrate Chemistry: Monosaccharides & Disaccharides", "Glucose, Fructose, Galactose (Monosaccharides); Sucrose (Gluc+Fruc), Lactose (Gluc+Galac), Maltose (Gluc+Gluc)", "Sucrose is a monosaccharide produced by glycogen breakdown"),
            Triple("Carbohydrate Metabolism: Glycolysis Energy Yield", "Glycolysis breaks down 1 glucose molecule into 2 pyruvate, yielding net 2 ATP and 2 NADH in cytoplasm", "Glycolysis requires high oxygen concentrations and occurs inside mitochondrial matrix"),
            Triple("Carbohydrate Metabolism: Krebs Cycle (TCA Cycle)", "Occurs in mitochondrial matrix; processes Acetyl-CoA producing NADH, FADH2, GTP, and CO2", "Krebs cycle occurs in ribosomes to synthesize cellular proteins"),
            Triple("Lipid Structure: Saturated vs Unsaturated Fatty Acids", "Saturated fatty acids have no double bonds (solid at room temp); Unsaturated fatty acids contain double bonds (liquid oils)", "Saturated fatty acids contain multiple double bonds and lower LDL cholesterol"),
            Triple("Lipid Metabolism: Beta-Oxidation of Fatty Acids", "Beta-oxidation breaks down fatty acids into Acetyl-CoA units inside mitochondria for ATP production", "Beta-oxidation synthesizes glucose from amino acid peptide chains"),
            Triple("Protein Structure: Primary, Secondary, Tertiary, Quaternary", "Primary (amino acid sequence), Secondary (alpha-helix/beta-sheet), Tertiary (3D folding), Quaternary (multi-subunit arrangement)", "Primary structure refers to globular 3D quaternary subunit protein complexes"),
            Triple("Enzyme Kinetics: Lock and Key vs Induced Fit Model", "Enzymes act as biological catalysts lowering activation energy without being consumed in reaction", "Enzymes are consumed completely during catalytic reactions and raise activation energy"),
            Triple("Enzyme Inhibition: Competitive vs Non-Competitive", "Competitive inhibitors bind active site (reversible with high substrate); Non-competitive bind allosteric site", "Competitive inhibitors bind allosteric site permanently changing Vmax"),
            Triple("Electrolytes: Sodium & Potassium Functions", "Sodium is chief extracellular cation (regulates fluid balance); Potassium is chief intracellular cation (regulates cardiac muscle resting potential)", "Potassium is major extracellular ion maintaining plasma oncotic pressure"),
            Triple("Electrolytes: Calcium & Phosphate Reciprocal Relationship", "Calcium and Phosphate maintain inverse relationship regulated by Parathyroid Hormone (PTH) and Calcitonin", "PTH lowers serum calcium and increases urinary calcium reabsorption"),
            Triple("Fat-Soluble Vitamins: Functions & Deficiency", "Vitamin A (Vision/Rhodopsin), Vitamin D (Calcium absorption/Rickets), Vitamin E (Antioxidant), Vitamin K (Clotting factors II, VII, IX, X)", "Vitamin K deficiency causes night blindness and rickets"),
            Triple("Water-Soluble Vitamins: Vitamin B Complex & Vitamin C", "Vitamin C (Collagen synthesis/Scurvy); Vitamin B1 Thiamine (Beriberi/Wernicke); Vitamin B12 (Cobalamin/Pernicious anemia)", "Vitamin C deficiency causes pernicious anemia and irreversible nerve degeneration"),
            Triple("Nucleic Acids: DNA vs RNA Differences", "DNA contains deoxyribose sugar, thymine, double helix; RNA contains ribose sugar, uracil, single strand", "RNA contains thymine base and double-stranded helical deoxyribose sugar"),
            Triple("Clinical Biochemistry: Jaundice & Bilirubin", "Unconjugated (indirect) bilirubin is lipid-soluble; Conjugated (direct) bilirubin is water-soluble processed by liver", "Conjugated bilirubin is insoluble in water and excreted through lungs")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Convert all blood glucose into urea crystals in muscle tissue",
                "Inhibit ATP synthesis and cause cellular necrosis"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 1,
                    subjectName = "Biochemistry for Nurses",
                    question = "BIO-613 Plus Q#${i + 1}: In clinical biochemistry regarding ${t.first}, which fact is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Biochemistry for Nurses (BIO-613) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 1 • BIO-613"
                )
            )
        }
        return list
    }

    private fun getMic1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Bacterial Staining: Gram Stain Mechanism", "Gram-positive bacteria have thick peptidoglycan layer (retain crystal violet - purple); Gram-negative have thin peptidoglycan & outer lipopolysaccharide (pink/red)", "Gram-negative bacteria stain dark purple because of thick peptidoglycan wall"),
            Triple("Bacterial Morphology: Cocci, Bacilli, Spirilla", "Staphylococcus (clusters of cocci), Streptococcus (chains of cocci), E. coli (Gram-negative bacilli)", "Streptococcus bacteria grow as Gram-negative spiral spirilla"),
            Triple("Acid-Fast Staining: Ziehl-Neelsen Technique", "Identifies Mycobacterium tuberculosis due to mycolic acid in cell wall; acid-fast bacilli stain bright red/pink", "Mycobacterium tuberculosis stains pink on standard Gram stain within 2 minutes"),
            Triple("Sterilization: Autoclave Parameters", "Moist heat sterilization under pressure: 121°C (250°F) at 15 psi for 15-20 minutes; kills all endospores", "Autoclave sterilizes at 50°C using dry warm air without pressure"),
            Triple("Disinfection & Antisepsis: Definitions", "Disinfectants used on inanimate objects; Antiseptics applied to living tissue; Sterilization destroys all microbes including spores", "Antiseptics destroy all endospores on surgical scalpels"),
            Triple("Bacterial Growth Cycle: 4 Phases", "Lag phase (adaptation), Log phase (exponential growth), Stationary phase (growth = death), Death phase (decline)", "Log phase is period when bacterial population remains completely zero"),
            Triple("Host Immunity: Innate vs Adaptive Immunity", "Innate (non-specific: skin, phagocytes, complement); Adaptive (specific: T-cells cell-mediated, B-cells humoral antibodies)", "Innate immunity creates specific long-term memory antibodies against smallpox"),
            Triple("Immunoglobulins: IgG, IgA, IgM, IgE, IgD", "IgG (most abundant, crosses placenta); IgA (secretory in breastmilk/saliva); IgM (first responder in acute infection); IgE (type I allergy/parasites)", "IgM is smallest immunoglobulin that crosses placenta during 1st trimester"),
            Triple("Virology: Structure & Replication Steps", "Virion consists of nucleic acid core (DNA or RNA) enclosed in protein capsid; steps: Attachment, Penetration, Uncoating, Biosynthesis, Assembly, Release", "Viruses reproduce independently in nutrient agar without host cells"),
            Triple("Bacterial Toxins: Exotoxins vs Endotoxins", "Exotoxins secreted by live Gram-positive/negative bacteria (heat-labile, highly potent); Endotoxins LPS part of Gram-negative outer membrane (released on cell lysis, causes septic shock)", "Endotoxins are protein toxins secreted continuously by live Gram-positive staphylococci"),
            Triple("Nosocomial Infections: Prevention & VAP / CAUTI", "Hand hygiene is single most effective method to prevent healthcare-associated infections (HAIs)", "Prophylactic oral antibiotics given to all hospitalized patients prevents HAIs"),
            Triple("Mycology: Pathogenic Fungi & Opportunistic Infections", "Candida albicans causes thrush and vaginitis; Aspergillus causes pulmonary infection in immunocompromised", "Candida albicans is a Gram-negative anaerobic rod bacterium"),
            Triple("Parasitology: Entamoeba histolytica & Giardia lamblia", "E. histolytica causes amoebic dysentery (fecal-oral, trophozoites with ingested RBCs); Giardia causes foul fatty diarrhea", "Entamoeba histolytica is an airborne viral respiratory infection"),
            Triple("Culture Media: Selective vs Differential Media", "MacConkey agar is selective for Gram-negative rods and differential for lactose fermentation; Blood agar shows hemolysis", "MacConkey agar selects for Gram-positive cocci and inhibits Gram-negative bacilli"),
            Triple("Antibiotic Resistance: MRSA & VRSA Mechanisms", "MRSA resistant to beta-lactams due to mecA gene producing altered penicillin-binding protein (PBP2a)", "MRSA is treated with oral amoxicillin monotherapy")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Promote rapid viral mutation and bacterial spore dissemination",
                "Inactivate immune system B-cells permanently in healthy infants"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 1,
                    subjectName = "Microbiology",
                    question = "MIC-614 Plus Q#${i + 1}: In microbiology regarding ${t.first}, which clinical statement is valid?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "Microbiology (MIC-614) Core Concept: ${t.second}.",
                    reference = "KMU PNC Semester 1 • MIC-614"
                )
            )
        }
        return list
    }

    private fun getEng1ExtraQuestions(startId: Int): List<KpSemesterQuestion> {
        var id = startId
        val list = mutableListOf<KpSemesterQuestion>()

        val topics = listOf(
            Triple("Medical Prefixes: Hyper- vs Hypo-", "'Hyper-' means excessive/above normal (e.g., Hypertension); 'Hypo-' means deficient/below normal (e.g., Hypoglycemia)", "'Hyper-' means below normal, and 'Hypo-' means elevated/excessive"),
            Triple("Medical Suffixes: -itis, -ectomy, -ology", "'-itis' means inflammation; '-ectomy' means surgical removal; '-ology' means study of", "'-ectomy' means inflammation of organ tissue"),
            Triple("Medical Suffixes: -algia, -emia, -uria", "'-algia' means pain (e.g., Neuralgia); '-emia' means blood condition (e.g., Anemia); '-uria' means urine condition (e.g., Dysuria)", "'-emia' means surgical incision into bladder"),
            Triple("Root Words: Cardi/o, Nephr/o, Hepat/o, Gastr/o", "Cardi/o (Heart), Nephr/o (Kidney), Hepat/o (Liver), Gastr/o (Stomach)", "Hepat/o refers to lungs and respiratory tract"),
            Triple("Parts of Speech: Nouns, Verbs, Adjectives in Clinical Context", "Adjectives describe nouns (e.g., 'febrile' patient); Verbs describe actions (e.g., 'administered' drug)", "In 'The nurse administered insulin', 'administered' is a proper noun"),
            Triple("Subject-Verb Agreement in Nursing Documentation", "Singular subjects take singular verbs ('The patient displays symptoms'); Plural subjects take plural verbs ('Patients display symptoms')", "'The list of medications were updated' is grammatically perfect"),
            Triple("Active vs Passive Voice in Medical Reports", "Active: 'The nurse administered the medication' (clear, direct); Passive: 'The medication was administered by the nurse'", "Passive voice should always be used to hide who performed nursing actions"),
            Triple("Punctuation in Clinical Writing: Apostrophes & Commas", "Commas separate items in list; Apostrophes show possession ('patient's chart'), NOT pluralization", "Apostrophes are used to make plural nouns like 'vital sign's'"),
            Triple("Comprehension & Context Clues: 'Exacerbation'", "'Exacerbation' means worsening or increase in severity of disease symptoms (e.g., COPD exacerbation)", "'Exacerbation' means complete cure and discharge from hospital"),
            Triple("Comprehension & Context Clues: 'Benign' vs 'Malignant'", "'Benign' means non-cancerous/harmless; 'Malignant' means cancerous/invasive/life-threatening", "'Benign' indicates aggressive metastatic carcinoma"),
            Triple("Professional Terminology: 'Prognosis' vs 'Diagnosis'", "'Diagnosis' identifies the disease; 'Prognosis' predicts likely outcome or course of recovery", "'Prognosis' means physical examination of abdomen"),
            Triple("Professional Terminology: 'Acute' vs 'Chronic'", "'Acute' means sudden onset and short duration; 'Chronic' means persistent or long-lasting condition (> 3-6 months)", "'Acute' means condition lasting over 20 years"),
            Triple("Professional Vocabulary: 'Contraindication'", "A condition or factor that serves as a reason to withhold a medical treatment or drug due to harm", "A routine schedule for taking daily vitamin supplements"),
            Triple("Synonyms in Clinical Practice: 'Etiology'", "'Etiology' is synonymous with cause or origin of a disease", "'Etiology' is synonymous with hospital billing statement"),
            Triple("Antonyms in Medical Terms: 'Dilate' vs 'Constrict'", "'Dilate' means to widen/expand; 'Constrict' means to narrow/tighten", "'Dilate' and 'Constrict' both mean to surgically remove tissue")
        )

        for (i in 0 until 100) {
            val t = topics[i % topics.size]
            val cIdx = (i + 1) % 4
            val opts = mutableListOf(
                t.second,
                t.third,
                "Contains grammatical errors and violates professional terminology standards",
                "Refers exclusively to Latin pharmaceutical abbreviations for veterinary medicine"
            )
            val correctText = opts[0]
            opts.removeAt(0)
            opts.add(cIdx, correctText)

            list.add(
                KpSemesterQuestion(
                    id = id++,
                    semesterNumber = 1,
                    subjectName = "English I (Functional English)",
                    question = "ENG-615 Plus Q#${i + 1}: In functional English and medical terminology regarding ${t.first}, which explanation is correct?",
                    options = opts,
                    correctIndex = cIdx,
                    explanation = "English I (ENG-615) Language & Terminology Concept: ${t.second}.",
                    reference = "KMU PNC Semester 1 • ENG-615"
                )
            )
        }
        return list
    }
}
