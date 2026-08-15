package com.example.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 5 QUESTION BANK
 * 50+ Questions per subject (250+ total questions)
 * Subjects:
 * 1. Pediatric Nursing - 50 Qs
 * 2. Community Health Nursing II - 50 Qs
 * 3. Nursing Education & Teaching - 50 Qs
 * 4. English III (Technical Writing) - 50 Qs
 * 5. Pakistan Studies - 50 Qs
 */
object KpSemester5QuestionBank {

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
                        explanation = "Correct answer: $correctOpt. Aligned with PNC / KMU Semester 5 curriculum.",
                        reference = ref
                    )
                )
            }
        }

        // ==========================================
        // 1. PEDIATRIC NURSING - 50 Qs
        // ==========================================
        val ped = listOf(
            Triple("An infant typically doubles their birth weight by what age?", listOf("2 months", "5 to 6 months", "12 months", "24 months"), 1),
            Triple("An infant typically triples their birth weight by what age?", listOf("6 months", "12 months (1 year)", "18 months", "24 months"), 1),
            Triple("The anterior fontanelle in an infant normally closes between what ages?", listOf("2 to 3 months", "12 to 18 months", "24 to 36 months", "At birth"), 1),
            Triple("The posterior fontanelle in an infant normally closes by what age?", listOf("2 to 3 months", "6 to 8 months", "12 months", "18 months"), 0),
            Triple("According to WHO IMNCI guidelines, fast breathing in an infant aged 2 to 12 months is defined as a respiratory rate equal to or exceeding:", listOf("30 breaths/min", "40 breaths/min", "50 breaths/min", "60 breaths/min"), 2),
            Triple("IMNCI fast breathing classification threshold for a child aged 12 months to 5 years is:", listOf("30 breaths/min", "40 breaths/min", "50 breaths/min", "60 breaths/min"), 1),
            Triple("The signature clinical manifestation of Croup (Acute Laryngotracheobronchitis) is:", listOf("High fever with drooling", "Barking seal-like cough and inspiratory stridor", "Wheezing with prolonged expiration", "Silent chest"), 1),
            Triple("Acute Epiglottitis is a medical emergency caused primarily by Haemophilus influenzae type B (Hib). Classic presentation includes:", listOf("Barking cough and clear sputum", "High fever, tripod positioning, drooling, and absence of cough", "Low grade fever and diarrhea", "Paroxysmal coughing spells"), 1),
            Triple("If Acute Epiglottitis is suspected in a child, the nurse should strictly AVOID:", listOf("Administering humidified oxygen", "Visual inspection of the throat using a tongue depressor", "Keeping the child calm", "Inserting an IV line"), 1),
            Triple("Tetralogy of Fallot (TOF) consists of four cardiac defects: Pulmonic Stenosis, Overriding Aorta, Right Ventricular Hypertrophy, and:", listOf("Atrial Septal Defect", "Ventricular Septal Defect (VSD)", "Patent Ductus Arteriosus", "Coarctation of Aorta"), 1),
            Triple("In a toddler experiencing a hypercyanotic 'Tet' spell during TOF, the immediate nursing positioning action is to place the child in:", listOf("Supine position", "Knee-chest or squatting position", "High Fowler's position", "Prone position"), 1),
            Triple("Patent Ductus Arteriosus (PDA) is characterized on cardiac auscultation by a:", listOf("Systolic ejection murmur", "Continuous machine-like murmur heard best at upper left sternal border", "Diastolic rumble", "Friction rub"), 1),
            Triple("The drug of choice administered to close a patent ductus arteriosus in premature infants is:", listOf("Prostaglandin E1", "Indomethacin or IV Ibuprofen", "Digoxin", "Furosemide"), 1),
            Triple("Coarctation of the Aorta produces a classic physical finding of:", listOf("Equal blood pressure in all limbs", "High blood pressure and bounding pulses in upper extremities with weak or absent femoral pulses and lower BP in legs", "Cyanosis of head only", "Hypotension in arms"), 1),
            Triple("Hirschsprung Disease (Congenital Aganglionic Megacolon) pathophysiology involves absence of parasympathetic ganglion cells in the:", listOf("Stomach wall", "Distal colon / rectum causing obstruction", "Small intestine", "Esophagus"), 1),
            Triple("A classic diagnostic clinical sign of Hirschsprung Disease in infants is:", listOf("Currant jelly stools", "Failure to pass meconium within 24 to 48 hours of birth and ribbon-like foul-smelling stools", "Steatorrhea", "Projectively vomiting bile"), 1),
            Triple("Intussusception clinical triad includes severe intermittent abdominal pain, palpable sausage-shaped mass, and passage of:", listOf("Ribbon-like stool", "Currant jelly-like stool containing blood and mucus", "Rice water stool", "Clay-colored stool"), 1),
            Triple("Non-surgical reduction of Intussusception is commonly attempted using:", listOf("Air or barium hydrostatic enema", "Nasogastric suctioning", "Oral laxatives", "High-dose antibiotics"), 0),
            Triple("Hypertrophic Pyloric Stenosis classically presents in an infant aged 2 to 6 weeks with:", listOf("Bilious vomiting and diarrhea", "Non-bilious projectile vomiting after feeding and palpable olive-shaped mass in epigastrium", "High fever and bloody stool", "Failure to pass meconium"), 1),
            Triple("Celiac disease intestinal damage is caused by hypersensitivity to gluten protein found in wheat, rye, barley, and:", listOf("Rice", "Oats", "Corn", "Soy"), 1),
            Triple("Acute Rheumatic Fever is a systemic inflammatory disease occurring weeks after an untreated upper respiratory infection caused by:", listOf("Staphylococcus aureus", "Group A Beta-Hemolytic Streptococcus (GABHS / Streptococcus pyogenes)", "Haemophilus influenzae", "Escherichia coli"), 1),
            Triple("Jones Criteria major manifestations of Acute Rheumatic Fever include Carditis, Polyarthritis, Erythema marginatum, Subcutaneous nodules, and:", listOf("Chorea (Sydenham's chorea)", "Glomerulonephritis", "Jaundice", "Alopecia"), 0),
            Triple("Kawasaki Disease primary complication threatening pediatric health is the development of:", listOf("Glomerulonephritis", "Coronary artery aneurysms and thrombosis", "Pulmonary fibrosis", "Cerebral palsy"), 1),
            Triple("Treatment of Kawasaki Disease in the acute phase involves intravenous administration of:", listOf("High-dose Penicillin", "Intravenous Immunoglobulin (IVIG) and high-dose Aspirin", "Corticosteroids alone", "Acyclovir"), 1),
            Triple("Measles (Rubeola) pathognomonic oral physical examination finding is:", listOf("Koplik's spots (small blue-white spots on red cheek mucosa)", "Strawberry tongue", "Thrush", "Gingival hyperplasia"), 0),
            Triple("Strawberry tongue is a classic physical sign seen in Kawasaki disease and:", listOf("Measles", "Scarlet Fever", "Chickenpox", "Mumps"), 1),
            Triple("Pertussis (Whooping Cough) catarrhal stage is followed by the paroxysmal stage featuring:", listOf("Barking cough", "Repetitive violent coughing fits ending with a high-pitched inspiratory 'whoop'", "Silent chest", "Dry hacking cough"), 1),
            Triple("The vaccine protecting against Diphtheria, Tetanus, and Pertussis administered in the EPI schedule is:", listOf("BCG", "Pentavalent vaccine (DTaP-HepB-Hib)", "OPV", "Measles vaccine"), 1),
            Triple("Infant colic is defined by Wessel's Rule of Threes as crying for more than:", listOf("1 hour a day, 2 days a week, for 1 week", "3 hours a day, 3 days a week, for 3 weeks or more in an otherwise healthy infant", "5 hours a day, 5 days a week", "12 hours continuous"), 1),
            Triple("Failure to Thrive (FTT) in pediatrics is defined as weight persistently falling below which growth chart percentile?", listOf("50th percentile", "25th percentile", "5th percentile", "75th percentile"), 2),
            Triple("Spina Bifida Cystica featuring sac-like protrusion containing meninges and spinal cord elements is termed:", listOf("Spina Bifida Occulta", "Meningocele", "Myelomeningocele", "Anencephaly"), 2),
            Triple("Myelomeningocele sac pre-operative nursing care requires keeping the infant in which position?", listOf("Supine", "Prone with sterile moist saline dressing over the sac", "High Fowler's", "Side-lying on hard surface"), 1),
            Triple("Hydrocephalus classic clinical sign in infants includes sunset eyes sign, bulging anterior fontanelle, and Macewen's sign (cracked-pot sound), accompanied by:", listOf("Decreased head circumference", "Abnormally rapid increase in head circumference and Macewen sign", "Microcephaly", "Sunken fontanelle"), 1),
            Triple("Surgical treatment of Hydrocephalus involves placement of a shunt that drains excess CSF from ventricles into the:", listOf("Pleural cavity", "Peritoneal cavity (Ventriculoperitoneal / VP shunt)", "Right ventricle of heart", "Subarachnoid space"), 1),
            Triple("Febrile Seizures in children typically occur between ages 6 months and 5 years associated with:", listOf("Central nervous system infection", "Rapid rise in body temperature (>38.0°C / 100.4°F) without underlying CNS infection", "Electrolyte imbalance", "Brain tumor"), 1),
            Triple("Cerebrospinal fluid (CSF) analysis in bacterial meningitis characteristically shows:", listOf("Elevated protein, decreased glucose, and cloudy appearance with neutrophils", "Normal protein, high glucose, clear", "Low WBC count, low protein", "High glucose, high RBC"), 0),
            Triple("Kangaroo Mother Care (KMC) for low birth weight infants emphasizes:", listOf("Continuous incubator isolation", "Continuous skin-to-skin contact between mother and infant and exclusive breastfeeding", "Phototherapy", "Formula feeding"), 1),
            Triple("Severe Acute Malnutrition (SAM) child with bilateral pitting edema is clinically diagnosed with:", listOf("Marasmus", "Kwashiorkor", "Rickets", "Scurvy"), 1),
            Triple("Severe Acute Malnutrition characterized by severe muscle wasting, loss of subcutaneous fat, and 'old man' facial appearance without edema is:", listOf("Kwashiorkor", "Marasmus", "Pellagra", "Beri-beri"), 1),
            Triple("According to WHO guidelines for SAM management, the initial refeeding phase uses which formula to prevent refeeding syndrome?", listOf("F-100", "F-75", "Infant formula", "Full cream milk"), 1),
            Triple("Phototherapy for neonatal hyperbilirubinemia assists in converting unconjugated bilirubin into water-soluble photoisomers via process of:", listOf("Conjugation in liver", "Structural photoisomerization excreted in bile and urine without liver conjugation", "Hemolysis", "Phagocytosis"), 1),
            Triple("During phototherapy, essential protective nursing measures for the neonate include:", listOf("Covering entire body with blanket", "Applying eye patches and covering genitalia while monitoring temperature and hydration", "Restricting oral feeds", "Applying skin lotion"), 1),
            Triple("Kernicterus is a neurological complication resulting from deposition of unconjugated bilirubin in the:", listOf("Kidney glomeruli", "Basal ganglia and brainstem nuclei", "Pulmonary alveoli", "Myocardium"), 1),
            Triple("In acute otitis media in young children, Eustachian tube anatomy predisposes them to infection because it is:", listOf("Longer, narrower, and more vertical", "Shorter, wider, and more horizontal", "Completely occluded", "Highly muscular"), 1),
            Triple("Reye's Syndrome risk in children recovering from viral illness (influenza/chickenpox) is strongly linked to administration of:", listOf("Acetaminophen", "Aspirin (Acetylsalicylic acid)", "Ibuprofen", "Amoxicillin"), 1),
            Triple("Scoliosis brace wearing protocol (e.g. Milwaukee / Boston brace) typically requires wearing the brace for how many hours per day?", listOf("2 to 4 hours", "8 to 10 hours", "22 to 23 hours a day", "Only during sleep"), 2),
            Triple("Developmental Dysplasia of the Hip (DDH) diagnostic test in infants producing a palpable click upon hip abduction is:", listOf("Barlow test", "Ortolani maneuver", "Trendelenburg test", "Thomas test"), 1),
            Triple("Barlow test for DDH involves flexed hip adduction while pushing posterior to detect if hip can be:", listOf("Abducted", "Dislocated out of acetabulum", "Rotated internally", "Extended"), 1),
            Triple("Pavlik Harness used for DDH in infants under 6 months maintains the hips in:", listOf("Extension and adduction", "Flexion and Abduction", "Hyper-extension", "Internal rotation"), 1),
            Triple("Asthma controller medication administered daily in pediatric asthma to reduce airway inflammation is:", listOf("Inhaled Short-Acting Beta Agonist (Albuterol)", "Inhaled Corticosteroid (e.g. Fluticasone)", "Oral Epinephrine", "Subcutaneous Atropine"), 1)
        )
        addSubjectQs(5, "Pediatric Nursing", "KMU PED-651 / Wong's Essentials of Pediatric Nursing", ped)

        // ==========================================
        // 2. COMMUNITY HEALTH NURSING II - 50 Qs
        // ==========================================
        val chn2 = listOf(
            Triple("In Community Health Nursing, the Herd Immunity threshold required to interrupt transmission of highly contagious airborne diseases like Measles is approximately:", listOf("50% to 60%", "92% to 95%", "30% to 40%", "70% to 75%"), 1),
            Triple("In epidemiology, the proportion of a population that has a specific disease at a given point in time is known as:", listOf("Incidence rate", "Prevalence rate", "Mortality rate", "Attack rate"), 1),
            Triple("Incidence rate measures the number of:", listOf("Existing cases in a population", "NEW cases of a disease arising in a population during a specified time period", "Total deaths", "Cured cases"), 1),
            Triple("In National Immunization Schedule of Pakistan (EPI), BCG vaccine is administered at birth to protect against:", listOf("Poliomyelitis", "Tuberculosis", "Hepatitis B", "Measles"), 1),
            Triple("Route and dose of BCG vaccine administration in neonates is:", listOf("0.5 mL Intramuscularly", "0.05 mL Intradermally in upper left arm", "2 drops Orally", "0.5 mL Subcutaneously"), 1),
            Triple("Oral Polio Vaccine (OPV) administered in routine EPI schedule consists of:", listOf("Inactivated virus (Salk)", "Live attenuated virus (Sabin) given as 2 drops orally", "Recombinant protein", "Toxoid"), 1),
            Triple("Pentavalent vaccine protects against five childhood diseases: Diphtheria, Tetanus, Pertussis, Hepatitis B, and:", listOf("Haemophilus influenzae type B (Hib)", "Measles", "Rotavirus", "Pneumococcus"), 0),
            Triple("Cold Chain system temperature maintenance requirement for storing vaccines at health center level is:", listOf("-20°C to -40°C", "+2°C to +8°C", "+15°C to +25°C", "0°C exactly"), 1),
            Triple("Which vaccine in the EPI schedule is heat-sensitive and placed in the coolest part of ice-lined refrigerator?", listOf("Tetanus toxoid", "OPV and Measles vaccines", "Hepatitis B", "Pentavalent"), 1),
            Triple("Vaccines easily damaged by freezing (+0°C or below) and must never be frozen include:", listOf("OPV", "Pentavalent, Hepatitis B, and Tetanus Toxoid vaccines", "Measles vaccine", "BCG reconstituted"), 1),
            Triple("Directly Observed Treatment, Short-course (DOTS) strategy for Tuberculosis control requires:", listOf("Patient taking medications at home unmonitored", "Healthcare provider or trained supervisor observing patient swallow every dose of anti-TB drugs", "Surgical resection of lung", "Isolation for 2 years"), 1),
            Triple("First-line anti-tuberculosis continuation phase regimen typically lasts for how many months?", listOf("2 months", "4 months", "9 months", "12 months"), 1),
            Triple("Dengue Fever vector mosquito transmitting Dengue virus in Pakistan is:", listOf("Anopheles stephensi", "Aedes aegypti", "Culex quinquefasciatus", "Phlebotomus sandfly"), 1),
            Triple("Aedes mosquito feeding and biting habit is characteristically:", listOf("Night-biting indoors", "Daytime biting (early morning and late afternoon)", "Midnight biting in forest", "Underground biting"), 1),
            Triple("Severe Dengue (Dengue Hemorrhagic Fever) key laboratory indicator demonstrating plasma leakage is:", listOf("Leukocytosis", "Rapid drop in platelet count (<100,000/uL) with concurrent rise in Hematocrit (>20%)", "Hypoglycemia", "Elevated serum urea"), 1),
            Triple("Malaria parasite species causing malignant tertian malaria and cerebral malaria in Pakistan is:", listOf("Plasmodium vivax", "Plasmodium falciparum", "Plasmodium malariae", "Plasmodium ovale"), 1),
            Triple("Primary vector transmitting Malaria in rural Pakistan is the female mosquito of genus:", listOf("Aedes", "Anopheles", "Culex", "Mansonia"), 1),
            Triple("Polio eradication campaign strategy in Pakistan uses supplementary immunization activities consisting of:", listOf("Injected IPV only in hospitals", "Door-to-door National Immunization Days (NIDs) administering 2 drops of bOPV to all children under 5 years", "School screening", "Water chlorination"), 1),
            Triple("Primary Health Care (PHC) declaration of Alma-Ata (1978) defined PHC as:", listOf("High-tech tertiary hospital care", "Essential health care made universally accessible to individuals and families in the community at cost they can afford", "Private insurance coverage", "Specialized surgical care"), 1),
            Triple("Basic Health Unit (BHU) in KP health infrastructure serves a target population of approximately:", listOf("1,000 to 2,000", "10,000 to 25,000 population", "100,000 population", "500,000 population"), 1),
            Triple("Rural Health Center (RHC) in Pakistan provides secondary level PHC support serving a population of:", listOf("5,000", "25,000 to 50,000 population with 10-20 beds", "200,000", "1,000,000"), 1),
            Triple("Lady Health Worker (LHW) program in Pakistan targets community health at grassroot level by covering approximately how many households per LHW?", listOf("10 households", "100 to 150 households (1,000 population)", "1,000 households", "5,000 households"), 1),
            Triple("Sehat Sahulat Card program in Khyber Pakhtunkhwa provides health insurance coverage for:", listOf("Outpatient Panadol prescriptions", "Free inpatient hospital treatment coverage up to 1 Million PKR per family per year in empanelled hospitals", "Cosmetic surgery", "Overseas medical treatment"), 1),
            Triple("Water purification method suitable for emergency household level disinfection in flood-affected areas is:", listOf("Boiling water vigorously for 1 minute or adding chlorine / NaDCC tablets", "Sedimentation only", "Filtration through cotton cloth", "Exposing to moonlight"), 0),
            Triple("Biomedical Waste Color Coding in hospitals: Red bin/bag is designated for:", listOf("General non-infectious waste", "Infectious plastic waste (catheters, IV tubing, syringes without needles)", "Sharps (needles, blades)", "Anatomical waste"), 1),
            Triple("Biomedical Waste: Yellow bin/bag is designated for:", listOf("General paper waste", "Anatomical, pathological, and soiled infectious cotton waste", "Recyclable glass", "Sharps container"), 1),
            Triple("Puncture-proof yellow or white safety container is designated exclusively for disposal of:", listOf("Empty IV bottles", "Sharps waste (used needles, scalpel blades, lancets)", "Food remnants", "Soiled linen"), 1),
            Triple("Modern Contraceptive method offering dual protection against unintended pregnancy and Sexually Transmitted Infections (STIs/HIV) is:", listOf("Oral Contraceptive Pills (OCPs)", "Male / Female Condoms (Barrier method)", "Intrauterine Device (IUD)", "Depo-Provera injection"), 1),
            Triple("Intrauterine Contraceptive Device (IUCD / Copper-T) mechanism of action is:", listOf("Suppressing ovulation", "Creating sterile localized inflammatory response in endometrium toxic to sperm and blastocyst", "Blocking fallopian tubes surgically", "Thickening cervical mucus only"), 1),
            Triple("Depo-Provera (DMPA) injectable contraceptive is administered by deep intramuscular injection every:", listOf("1 month", "3 months (12 weeks)", "6 months", "12 months"), 1),
            Triple("Progestin-only emergency contraceptive pill (Levonorgestrel 1.5 mg) should ideally be taken within how many hours of unprotected intercourse?", listOf("12 hours", "72 hours (3 days)", "7 days", "14 days"), 1),
            Triple("Maternal Mortality Ratio (MMR) is expressed as the number of maternal deaths per:", listOf("1,000 live births", "100,000 live births", "10,000 live births", "1,000 population"), 1),
            Triple("Infant Mortality Rate (IMR) measures the number of deaths of infants under 1 year of age per:", listOf("100 live births", "1,000 live births", "10,000 live births", "100,000 live births"), 1),
            Triple("Neonatal Mortality Rate measures infant deaths occurring within the first:", listOf("7 days of life", "28 days of life", "60 days of life", "1 year of life"), 1),
            Triple("School Health Services routine screening activity includes assessing students for:", listOf("Visual acuity, dental caries, growth milestones, and immunization status", "Blood pressure Holter monitoring", "Genetic karyotyping", "Bone marrow biopsy"), 0),
            Triple("Occupational health hazard Pneumoconiosis caused by inhalation of silica dust in quarry workers is termed:", listOf("Asbestosis", "Silicosis", "Bagassosis", "Byssinosis"), 1),
            Triple("Byssinosis is an occupational lung disease caused by inhalation of dust from:", listOf("Coal dust", "Cotton fiber dust in textile mills", "Asbestos fiber", "Sugarcane bagasse"), 1),
            Triple("Bagassosis occupational lung disease occurs due to inhalation of dust from:", listOf("Cotton dust", "Stored sugarcane fiber (bagasse)", "Silica dust", "Iron ore"), 1),
            Triple("In disaster management, the Triage color tag RED designates a victim requiring:", listOf("Immediate life-saving medical care / high priority transport", "Delayed care (can wait 2 hours)", "Minor injuries (walking wounded)", "Deceased / unsalvageable"), 0),
            Triple("Disaster Triage color tag BLACK represents victims who are:", listOf("Immediate priority", "Deceased or mortally injured with no chance of survival", "Minor injuries", "Delayed care"), 1),
            Triple("Disaster Triage tag GREEN represents casualties who are:", listOf("Immediate transport", "Walking wounded with minor injuries", "Delayed transport", "Dead"), 1),
            Triple("In infectious disease control, Quarantining refers to restricting movement of:", listOf("Confirmed sick individuals displaying active symptoms", "WELL or asymptomatic persons who were EXPOSED to a communicable disease during incubation period", "Healthcare workers only", "Cured patients"), 1),
            Triple("Isolation differs from Quarantine because Isolation applies to restricting movement of:", listOf("Asymptomatic exposed individuals", "CONFIRMED infected/sick individuals shedding pathogen to prevent transmission", "General population", "Non-exposed travellers"), 1),
            Triple("Herd Immunity threshold needed to prevent outbreak of highly contagious diseases like Measles requires population coverage of approximately:", listOf("50%", "70%", "92% to 95%", "100%"), 2),
            Triple("Subclinical / Inapparent infection refers to an infection where the host:", listOf("Displays severe life-threatening symptoms", "Harbors pathogen and generates immune response without demonstrating overt clinical signs", "Is completely immune", "Dies rapidly"), 1),
            Triple("Fomite in disease transmission is defined as an:", listOf("Infected living animal vector", "Inanimate object (e.g. towel, door handle) contaminated with infectious agent", "Airborne droplet nucleus", "Infected human carrier"), 1),
            Triple("Nosocomial infection (Hospital-Acquired Infection) is defined as an infection acquired:", listOf("Before hospital admission", "48 hours or more AFTER hospital admission not present at time of entry", "At birth", "In community market"), 1),
            Triple("Most effective single intervention to break the chain of infection in health facilities is:", listOf("Prophylactic oral antibiotics", "Consistent hand hygiene before and after patient contact", "Wearing gown constantly", "Fumigating wards daily"), 1),
            Triple("Fly vector Musca domestica (Housefly) transmits enteric pathogens primarily via:", listOf("Direct blood sucking", "Mechanical transmission on feet, proboscis, and vomitus onto food", "Transovarial transmission", "Aerosolized droplets"), 1),
            Triple("Scabies skin infestation is caused by the microscopic itch mite named:", listOf("Pediculus humanus", "Sarcoptes scabiei var. hominis", "Phthirus pubis", "Demodex folliculorum"), 1)
        )
        addSubjectQs(5, "Community Health Nursing II", "KMU CHN-652 / Park's Textbook of Preventive & Social Medicine", chn2)

        // ==========================================
        // 3. NURSING EDUCATION & TEACHING - 50 Qs
        // ==========================================
        val edu = listOf(
            Triple("Bloom's Taxonomy of Educational Objectives classifies learning into three main domains:", listOf("Visual, Auditory, Kinesthetic", "Cognitive, Affective, and Psychomotor", "Basic, Intermediate, Advanced", "Memory, Comprehension, Application"), 1),
            Triple("In Bloom's Cognitive Domain (revised), the highest level of cognitive thinking is:", listOf("Understanding", "Applying", "Creating", "Analyzing"), 2),
            Triple("Which domain of Bloom's taxonomy addresses attitudes, values, feelings, and professional ethics?", listOf("Cognitive Domain", "Affective Domain", "Psychomotor Domain", "Sensory Domain"), 1),
            Triple("Psychomotor domain in nursing education focuses on development of:", listOf("Theoretical concepts", "Physical manual clinical skills and motor coordination", "Ethical values", "Writing articles"), 1),
            Triple("In lesson planning, 'SMART' learning objectives acronym stands for:", listOf("Simple, Measurable, Actionable, Relevant, Timely", "Specific, Measurable, Achievable, Relevant, and Time-bound", "Systematic, Mastered, Approved, Rational, Tested", "Standardized, Methodical, Accurate, Rigorous, Targeted"), 1),
            Triple("A formative evaluation in nursing education is conducted:", listOf("Only at the end of a 4-year degree program", "Ongoing DURING the instructional process to provide immediate feedback and improve learning", "For final grading only", "Before starting a course"), 1),
            Triple("Summative evaluation differs from formative evaluation because summative evaluation is performed:", listOf("At the beginning of a class", "At the END of an instructional unit or course to measure final achievement and assign grades", "Daily during clinical practice", "Informally during breaks"), 1),
            Triple("Objective Structured Clinical Examination (OSCE) evaluates nursing students primarily on:", listOf("Essay writing ability", "Standardized clinical competencies, communication, and practical skills across timed stations", "Multiple choice recognition", "Library research"), 1),
            Triple("In clinical instruction, the Preceptor model involves:", listOf("One instructor teaching 50 students in a lecture hall", "An experienced staff nurse paired 1-on-1 with a student nurse in the clinical area for mentorship", "Self-study online", "Peer tutoring only"), 1),
            Triple("Microteaching is a teacher training technique featuring:", listOf("Teaching 100 students for 2 hours", "Scaled-down teaching encounter (5-10 minutes with small peer group) focusing on specific teaching skill", "Online video watching", "Grading final exams"), 1),
            Triple("Which teaching method is most effective for psychomotor skill acquisition (e.g. IV catheter insertion)?", listOf("Traditional lecture", "Demonstration and Return Demonstration", "Group discussion", "Reading textbook"), 1),
            Triple("Problem-Based Learning (PBL) in nursing education centers around:", listOf("Teacher-centered lectures", "Student-led analysis and self-directed learning triggered by real-world clinical patient scenarios", "Rote memorization of flashcards", "Multiple choice drill"), 1),
            Triple("Constructivist theory of learning posits that learners:", listOf("Are passive slates filled by teacher's knowledge", "Actively construct new knowledge and meaning based on prior experiences and social interaction", "Learn purely by reflex conditioning", "Require physical punishment"), 1),
            Triple("Malcolm Knowles' theory of Adult Learning is known as:", listOf("Pedagogy", "Andragogy", "Geragogy", "Heutagogy"), 1),
            Triple("A key assumption of Andragogy (Adult Learning Theory) is that adult learners are:", listOf("Dependent on teacher direction", "Self-directed, problem-centered, and motivated by internal factors and practical relevance", "Subject-centered without clinical goals", "Passive receivers"), 1),
            Triple("In lecture presentation, the '10-2 rule' recommends:", listOf("10 hours lecture followed by 2 hours break", "10 minutes of instruction followed by 2 minutes of student processing / interaction", "10 slides with 2 words each", "10 students per 2 teachers"), 1),
            Triple("Item analysis of a multiple-choice question exam evaluating 'Difficulty Index' defines an optimal question item range as:", listOf("0.0 to 0.1 (extremely hard)", "0.3 to 0.7 (moderate difficulty)", "0.9 to 1.0 (everyone gets it right)", "Negative value"), 1),
            Triple("Discrimination Index of an exam item measures the question's ability to:", listOf("Differentiate between high-performing students and low-performing students", "Determine test duration", "Differentiate between male and female students", "Assess reading speed"), 0),
            Triple("Which type of test question is least prone to guessing?", listOf("True/False question", "Multiple Choice Question with 4 options", "Short Answer / Essay Question", "Matching item"), 2),
            Triple("Rubric in clinical assignment assessment serves as a:", listOf("Class attendance sheet", "Scoring tool setting explicit criteria and performance level standards for evaluation", "List of required textbooks", "Student feedback complaint form"), 1),
            Triple("Visual, Auditory, Read/write, Kinesthetic sensory learning preferences model is known as:", listOf("ADPIE model", "VARK model", "SWOT analysis", "Gagne's events"), 1),
            Triple("In Gagne's Nine Events of Instruction, the mandatory FIRST event to begin a lesson is:", listOf("Presenting stimulus content", "Gaining student attention", "Providing feedback", "Assessing performance"), 1),
            Triple("Reflective Journaling in nursing clinical education encourages students to:", listOf("Copy medical dictionary definitions", "Critically analyze their own clinical experiences, emotions, decision-making, and areas for improvement", "Record patient names and contact details", "Write fictional stories"), 1),
            Triple("Simulation-Based Learning in nursing allows students to:", listOf("Practice high-risk clinical skills on real vulnerable patients", "Practice complex clinical scenarios in a safe environment using high-fidelity mannequins without patient risk", "Skip clinical rotations", "Learn without instructor guidance"), 1),
            Triple("In simulation education, Debriefing phase occurring immediately post-simulation is designed for:", listOf("Assigning letter grades", "Reflective discussion, analyzing performance, emotional processing, and reinforcing learning points", "Lecturing on new theory", "Cleaning equipment"), 1),
            Triple("A syllabus in a nursing course functions as a:", listOf("Daily clinical attendance log", "Contract and master instructional roadmap between educator and students outlining outcomes, policies, and schedules", "Hospital policy manual", "Board exam paper"), 1),
            Triple("Standardized Patient (SP) in nursing education refers to an:", listOf("Academically failing student", "Actor trained to consistently portray a real patient with specific medical history and physical signs for exam/teaching", "High-fidelity mannequin", "ICU patient"), 1),
            Triple("Dynamic learning strategy 'Flipped Classroom' reorganizes traditional teaching by requiring students to:", listOf("Listen to lecture in class and read textbook at home", "Review lecture content/videos BEFORE class at home, reserving class time for active problem solving and application", "Skip all home study", "Take exams at home"), 1),
            Triple("Clinical Judgement Measurement Model (NCJMM) key cognitive step where nurse interprets clinical cues is:", listOf("Recognizing cues", "Analyzing cues and Formulating hypotheses", "Taking action", "Evaluating outcomes"), 1),
            Triple("Curriculum evaluation model 'CIPP' developed by Stufflebeam stands for:", listOf("Content, Implementation, Pedagogy, Practice", "Context, Input, Process, and Product evaluation", "Cognitive, Individual, Professional, Practical", "Classroom, Instructor, Program, Performance"), 1),
            Triple("In test blueprinting (Table of Specifications), test items are distributed based on:", listOf("Random allocation", "Course objectives, topic content weightage, and level of cognitive taxonomy", "Number of textbook pages", "Student preference"), 1),
            Triple("Halo Effect in clinical performance rating occurs when an evaluator:", listOf("Grades strictly based on objective rubric", "Allows a single general positive impression of a student to unconsciously bias all clinical ratings favorably", "Grades everyone failing", "Grades based on alphabetical order"), 1),
            Triple("Hawthorne Effect in educational research refers to subjects changing behavior because they:", listOf("Are given money", "Are aware that they are being observed and evaluated", "Are tired", "Dislike the instructor"), 1),
            Triple("Role-playing as an educational method is particularly effective for teaching:", listOf("Complex drug dosages", "Interpersonal communication, therapeutic empathy, and conflict resolution skills", "Surgical anatomy", "Statistical calculations"), 1),
            Triple("Concept Mapping in nursing education helps students to:", listOf("Memorize list of drugs", "Visually organize, connect, and synthesize relationships between disease pathophysiology, symptoms, and nursing interventions", "Draw human organs", "Type faster"), 1),
            Triple("Brainstorming session guidelines mandate that during the idea-generation phase:", listOf("Every idea is immediately criticized and analyzed", "Judgement and criticism of ideas are strictly suspended to maximize creative input", "Only the instructor speaks", "Ideas must be written in formal essay"), 1),
            Triple("Cognitive Load Theory suggests instructional design must minimize working memory strain caused by:", listOf("Germane cognitive load", "Extraneous cognitive load (irrelevant distractors in teaching material)", "Intrinsic cognitive load", "Clinical practice"), 1),
            Triple("The Pygmalion Effect (Teacher Expectation Effect) demonstrates that:", listOf("High expectations by educators lead to improved student performance", "Low expectations improve test scores", "Students learn best without teachers", "Grades depend on physical room temperature"), 0),
            Triple("Reliability of an assessment tool refers to its ability to:", listOf("Measure what it intends to measure", "Yield consistent and stable results across repeated measurements or evaluators", "Be completed in 5 minutes", "Include 100 questions"), 1),
            Triple("Validity of an assessment tool refers to:", listOf("Consistency over time", "Degree to which an instrument actually measures what it purports to measure", "Ease of printing", "Cost of administration"), 1),
            Triple("Face Validity assesses whether an exam instrument:", listOf("Is mathematically proved by statistics", "Appears on the surface to measure what it claims to measure", "Correlates with future success", "Contains no typos"), 1),
            Triple("Content Validity of a nursing licensing examination ensures:", listOf("All candidates pass", "The test adequately covers the entire domain of knowledge and skills required for professional practice", "Questions are written in multiple languages", "Exam takes less than 1 hour"), 1),
            Triple("A mentor in professional nursing education functions primarily as a:", listOf("Strict exam invigilator", "Trusted long-term guide, advocate, and career role model supporting holistic development", "Hospital administrator", "Peer rival"), 1),
            Triple("Incidental teaching in clinical nursing education occurs when:", listOf("A formal lecture is scheduled in auditorium", "An unplanned real-life clinical situation is capitalized on by instructor for immediate teachable moment", "Students take online quiz", "A textbook chapter is assigned"), 1),
            Triple("Feedback provided to nursing students is most effective when it is:", listOf("Delayed by 3 months", "Specific, constructive, timely, and focused on actionable behavior rather than personality", "Vague and general", "Delivered publicly to embarrass"), 1),
            Triple("Criterion-Referenced Assessment evaluates student performance against:", listOf("Performance of other peers in the class (norm-referenced)", "Fixed, predetermined standards or learning criteria regardless of peer scores", "Historical averages", "Teacher's personal mood"), 1),
            Triple("Norm-Referenced Assessment compares a student's score against:", listOf("Absolute clinical benchmark", "The relative performance profile of a peer reference group (e.g. percentiles)", "Their own previous score", "International standard"), 1),
            Triple("Curriculum horizontal alignment ensures:", listOf("Alignment between year 1 and year 4 courses", "Integration and consistency across subjects taught concurrently within the same semester", "Matching national guidelines only", "Online and offline sync"), 1),
            Triple("Curriculum vertical alignment ensures:", listOf("Integration between subjects in same semester", "Logical progression of knowledge and skills building from lower to higher semesters across years", "Parallel teacher schedules", "Uniform textbook publisher"), 1),
            Triple("Micro-learning instructional strategy delivers educational content in:", listOf("3-hour continuous lectures", "Bite-sized, focused learning units (3-5 minutes) targeting a single specific learning outcome", "Full-day workshops", "Semester-long projects"), 1)
        )
        addSubjectQs(5, "Nursing Education & Teaching", "KMU EDU-653 / Bastable Nurse as Educator", edu)

        // ==========================================
        // 4. ENGLISH III (TECHNICAL WRITING) - 50 Qs
        // ==========================================
        val eng3 = listOf(
            Triple("In APA (7th Edition) referencing, an in-text citation for a single author work includes:", listOf("Author's full first name and book title", "Author's last name and publication year (e.g., Smith, 2021)", "Page number only", "URL link only"), 1),
            Triple("APA 7th edition direct quotation citation requires including author last name, year, and:", listOf("Publisher name", "Specific page number or paragraph number (e.g., p. 45)", "City of publication", "Journal volume"), 1),
            Triple("An Abstract in a technical research manuscript or proposal is defined as a:", listOf("Detailed 20-page introduction", "Concise summary (typically 150-250 words) outlining background, objective, methods, results, and conclusion", "List of references", "Raw data appendix"), 1),
            Triple("The standard structural acronym 'IMRAD' for technical scientific research papers stands for:", listOf("Information, Method, Review, Analysis, Discussion", "Introduction, Methods, Results, And Discussion", "Investigation, Measurement, Results, Assessment, Diagnosis", "Index, Manuscript, Report, Abstract, Data"), 1),
            Triple("In clinical case report writing, patient confidentiality is strictly protected according to ethics guidelines by:", listOf("Using patient's actual government ID", "Removing all direct identifiers (e.g., name, address, specific date of birth) and using pseudonyms/initials", "Publishing patient photograph without consent", "Including phone number"), 1),
            Triple("A Literature Review section in a research proposal serves to:", listOf("Provide a personal opinion diary", "Synthesize existing published research to identify knowledge gaps and justify the study", "List all books ever published on medicine", "Present final study data"), 1),
            Triple("The primary purpose of a Research Proposal is to:", listOf("Report completed results", "Convince institutional review boards / reviewers that the proposed study is significant, ethically sound, and methodologically feasible", "Sell a pharmaceutical product", "Write a textbook chapter"), 1),
            Triple("In technical writing, passive voice should generally be replaced with active voice to enhance:", listOf("Ambiguity", "Clarity, conciseness, and direct responsibility", "Length of sentences", "Complexity"), 1),
            Triple("Which sentence demonstrates correct active voice suitable for technical reporting?", listOf("The medication was administered by the nurse to the patient.", "The nurse administered the medication to the patient.", "To the patient, medication administration was performed.", "Medication had been given."), 1),
            Triple("Plagiarism in academic technical writing is defined as:", listOf("Citing multiple sources", "Presenting someone else's ideas, text, or work as your own without proper attribution", "Translating text", "Writing a original summary"), 1),
            Triple("Paraphrasing effectively involves:", listOf("Changing every third word with a synonym while keeping original sentence structure", "Restating the author's ideas completely in your own words and sentence structure while retaining original meaning and citing source", "Copying text into quotation marks", "Deleting half the paragraph"), 1),
            Triple("In technical writing, 'Jargon' refers to:", listOf("Grammatical errors", "Specialized terminology used by a specific profession that may confuse lay readers", "Slang words", "Punctuation marks"), 1),
            Triple("A 'PICO' question framework in evidence-based practice research stands for:", listOf("Patient/Problem, Intervention, Comparison, Outcome", "Protocol, Investigation, Care, Observation", "Population, Inspection, Cause, Operation", "Patient, Inquiry, Clinical, Option"), 0),
            Triple("In APA reference list entries, book titles and journal names should be formatted in:", listOf("ALL CAPS", "Italics", "Bold underline", "Quotation marks"), 1),
            Triple("In APA 7th edition, journal article titles in reference lists use sentence case capitalization, which means capitalizing:", listOf("Every word in the title", "Only the first word of title, first word after colon, and proper nouns", "No words at all", "Only nouns"), 1),
            Triple("A clear hypothesis in a technical research proposal must be:", listOf("Vague and untestable", "Testable, concise statement predicting the relationship between independent and dependent variables", "A long 5-page narrative", "A question ending with mark"), 1),
            Triple("In technical medical writing, the term 'Etiology' means:", listOf("Study of epidemics", "Cause or origin of a disease or condition", "Surgical removal", "Diagnostic test"), 1),
            Triple("The term 'Prognosis' in clinical documentation describes the:", listOf("Past medical history", "Predicted probable outcome or course of a disease", "Hospital billing code", "Surgical procedure"), 1),
            Triple("An operational definition in research methodology defines a variable in terms of:", listOf("Dictionary meaning", "Specific concrete procedures or measurements used to observe and quantify it", "Theoretical philosophy", "Poetic imagery"), 1),
            Triple("Qualitative research manuscript reporting relies primarily on narrative descriptions and:", listOf("Statistical p-values", "Thematic analysis and participant quotes", "Bar charts", "Regression formulas"), 1),
            Triple("Quantitative research reports present findings primarily using:", listOf("Unstructured stories", "Numerical data, statistical tests, tables, and graphs", "Personal impressions", "Historical poems"), 1),
            Triple("In technical writing, a 'Callout' in a text refers to:", listOf("An insult to author", "A short visual box or excerpt highlighting crucial information or key warning", "Footnote number", "Margin typo"), 1),
            Triple("In a formal clinical audit report, the 'Executive Summary' is located:", listOf("At the very end of appendix", "At the beginning of report providing key highlights for quick review by leadership", "Inside table of contents", "In references"), 1),
            Triple("Which transition word indicates cause and effect in academic technical prose?", listOf("Furthermore", "Consequently / Therefore", "Similarly", "In contrast"), 1),
            Triple("Which transition word signals contrast between two scientific findings?", listOf("However / On the other hand", "In addition", "For example", "Namely"), 0),
            Triple("A technical document's 'Scope' defines:", listOf("The total cost of printing", "The boundaries, limits, and extent of topics covered in the study or document", "Author's background", "Font size"), 1),
            Triple("In a research proposal, the 'Delimitations' refer to:", listOf("Uncontrollable study flaws", "Specific choices made by researcher to narrow the focus and boundaries of study", "Ethical violations", "Errors in software"), 1),
            Triple("Research study 'Limitations' refer to:", listOf("Deliberate scope narrowing", "Potential weaknesses or uncontrollable factors that may affect study results or generalizability", "Budget surplus", "Author qualifications"), 1),
            Triple("MeSH terms in literature searching stands for:", listOf("Medical Subject Headings", "Medical System Health", "Measurement Standard Headings", "Methodological Survey Index"), 0),
            Triple("Which Boolean operator narrows a research database literature search?", listOf("OR", "AND", "NOT only", "XOR"), 1),
            Triple("Using the Boolean operator 'OR' in a literature database search acts to:", listOf("Narrow search results", "Broaden search results by including synonyms or related terms", "Exclude terms", "Cancel search"), 1),
            Triple("Truncation symbol (asterisk *) in literature database searching (e.g. nurs*) retrieves:", listOf("Exact word nurs only", "All word variations with that root (e.g., nurse, nurses, nursing)", "Spelling mistakes", "Synonyms"), 1),
            Triple("A peer-reviewed journal article is one that has been:", listOf("Checked for spelling by author's friend", "Evaluated and vetted by independent experts/scholars in the same field before publication", "Approved by government official", "Self-published online"), 1),
            Triple("Systematic Literature Review differs from a traditional narrative review because it:", listOf("Uses informal search methods", "Employs rigorous, explicit, reproducible methodology to identify, appraise, and synthesize all relevant studies", "Is written in 1 day", "Contains no tables"), 1),
            Triple("Meta-analysis in technical research is defined as a:", listOf("Single case description", "Statistical combination and pooling of quantitative results from multiple independent clinical studies", "Qualitative interview analysis", "Book review"), 1),
            Triple("PRISMA statement guideline is used internationally for reporting:", listOf("Randomized controlled trials", "Systematic reviews and Meta-analyses", "Qualitative interviews", "Case reports"), 1),
            Triple("STROBE statement guideline is used for reporting:", listOf("Observational studies (Cohort, Case-control, Cross-sectional)", "Clinical trials", "Animal studies", "Qualitative research"), 0),
            Triple("CONSORT statement guidelines govern the reporting of:", listOf("Qualitative studies", "Parallel group Randomized Controlled Trials (RCTs)", "Systematic reviews", "Surveys"), 1),
            Triple("In technical writing, 'Brevity' means:", listOf("Using long complex words", "Expressing ideas concisely without unnecessary words", "Writing in poetic meter", "Using technical slang"), 1),
            Triple("Dangling modifier in grammar occurs when a modifying phrase:", listOf("Correctly modifies the subject", "Lacks a clear logical subject to attach to in the sentence", "Ends with a period", "Contains a verb"), 1),
            Triple("Identify the sentence free of grammatical error:", listOf("After completing the assessment, the patient was given medication by the nurse.", "Having assessed the patient, the nurse administered the medication.", "The nurse given medication after assessment.", "Medication administered after patient assessed."), 1),
            Triple("A 'Doi' (Digital Object Identifier) in academic referencing provides a:", listOf("Temporary website link", "Permanent, persistent digital link pointing directly to an online scholarly article", "Journal volume number", "Author email"), 1),
            Triple("In APA 7th edition referencing format for journal articles, volume numbers are formatted in:", listOf("Plain text", "Italics (with issue number in parentheses non-italicized)", "Underline", "Bold"), 1),
            Triple("Which reference format is correct for an APA 7th journal article?", listOf("Khan, A. (2020). Diabetes management. Nursing Journal, 12(3), 45-50.", "Khan A., Diabetes management, 2020.", "Khan (2020) Diabetes management, Nursing Journal.", "2020. Khan A. Nursing Journal."), 0),
            Triple("In technical proposal writing, a 'Gantt Chart' is used to visually display:", listOf("Financial budget breakdown", "Project implementation timeline, task schedules, and milestones over time", "Organizational hierarchy", "Sample size calculations"), 1),
            Triple("In research ethics documentation, Informed Consent forms must be written at what reading grade level for general public comprehension?", listOf("12th grade / University level", "6th to 8th grade reading level in simple plain language", "Medical doctor level", "Kindergarten level"), 1),
            Triple("In technical report writing, 'Tone' should be:", listOf("Emotional and subjective", "Objective, professional, neutral, and respectful", "Sarcastic", "Informal conversational"), 1),
            Triple("Plagiarism detection software (e.g., Turnitin) calculates a:", listOf("Grade point average", "Similarity Index percentage comparing document text against database publications", "Spelling error count", "Readability index"), 1),
            Triple("When compiling a bibliography vs reference list, a Reference List includes:", listOf("All books ever read by author", "ONLY sources explicitly cited within the body of the manuscript", "Future reading recommendations", "Dictionary terms"), 1),
            Triple("A research 'Conflict of Interest' disclosure statement declares:", listOf("Arguments between researchers", "Any financial, personal, or professional relationships that could potentially bias study findings", "Copyright ownership", "Participant addresses"), 1)
        )
        addSubjectQs(5, "English III (Technical Writing)", "KMU ENG-654 / APA 7th Manual & Medical Writing Guidelines", eng3)

        // ==========================================
        // 5. PAKISTAN STUDIES - 50 Qs
        // ==========================================
        val pak = listOf(
            Triple("The Two-Nation Theory, which formed the ideological basis of Pakistan, was articulated prominently by:", listOf("Allama Muhammad Iqbal and Sir Syed Ahmad Khan", "Lord Mountbatten", "Mahatma Gandhi", "Jawaharlal Nehru"), 0),
            Triple("Sir Syed Ahmad Khan founded which famous educational institution in 1875 that later became Aligarh Muslim University?", listOf("Islamia College Peshawar", "Muhammadan Anglo-Oriental (MAO) College", "Government College Lahore", "Nadwatul Ulama"), 1),
            Triple("The historic Lahore Resolution (Pakistan Resolution) demanding independent states for Muslims was passed on:", listOf("14th August 1947", "23rd March 1940", "14th March 1930", "25th December 1925"), 1),
            Triple("Who presided over the Lahore session of All-India Muslim League on 23rd March 1940?", listOf("Allama Iqbal", "Quaid-e-Azam Muhammad Ali Jinnah", "Liaquat Ali Khan", "Maulana Muhammad Ali Johar"), 1),
            Triple("Allama Muhammad Iqbal delivered his famous presidential address outlining the concept of a separate Muslim state in 1930 at:", listOf("Lahore", "Allahabad", "Karachi", "Dhaka"), 1),
            Triple("The All-India Muslim League was founded in 1906 at:", listOf("Aligarh", "Dhaka", "Lucknow", "Simla"), 1),
            Triple("The First Constituent Assembly of Pakistan was inaugurated in August 1947, and its first President was:", listOf("Liaquat Ali Khan", "Quaid-e-Azam Muhammad Ali Jinnah", "Khawaja Nazimuddin", "Malik Ghulam Muhammad"), 1),
            Triple("The Objectives Resolution, which laid down the foundational principles for the constitution of Pakistan, was passed in:", listOf("1947", "March 1949", "1956", "1973"), 1),
            Triple("The Objectives Resolution was moved in the Constituent Assembly by Pakistan's first Prime Minister:", listOf("Quaid-e-Azam Muhammad Ali Jinnah", "Liaquat Ali Khan", "Husyn Shaheed Suhrawardy", "I.I. Chundrigar"), 1),
            Triple("Pakistan's first Constitution was promulgated on March 23 in the year:", listOf("1949", "1956", "1962", "1973"), 1),
            Triple("The current enforceable Constitution of the Islamic Republic of Pakistan was enacted in:", listOf("1956", "1962", "1973", "1985"), 2),
            Triple("Under the 1973 Constitution of Pakistan, the system of government established is a:", listOf("Presidential system", "Federal Parliamentary system", "Unitary system", "Monarchy"), 1),
            Triple("The 18th Constitutional Amendment passed in 2010 significantly impacted governance in Pakistan by:", listOf("Abolishing Parliament", "Devolving powers and key subjects (including Health and Education) to the Provinces", "Creating new provinces", "Extending presidential terms"), 1),
            Triple("According to the Constitution of Pakistan, the Supreme Commander of the Armed Forces is the:", listOf("Prime Minister", "President of Pakistan", "Chief of Army Staff", "Defence Minister"), 1),
            Triple("The bicameral federal legislature of Pakistan consists of two houses: National Assembly and:", listOf("Senate", "Provincial Assembly", "Majlis-e-Shura Supreme Council", "House of Lords"), 0),
            Triple("Members of the Senate of Pakistan represent equal representation from each:", listOf("District", "Province", "Tehsil", "Division"), 1),
            Triple("In Khyber Pakhtunkhwa, the provincial flagship universal health insurance program is known as:", listOf("Benazir Income Support", "Sehat Sahulat Card Program", "Kamyab Jawan Program", "Ehsas Emergency Cash"), 1),
            Triple("The Sehat Sahulat Card program in KP provides free inpatient medical coverage up to PKR:", listOf("100,000 per family/year", "1,000,000 (10 Lakhs) per family per year", "5,000,000 per family", "50,000 per family"), 1),
            Triple("The regulatory statutory body governing Nursing education and practice registration in Pakistan is the:", listOf("Pakistan Medical and Dental Council (PMDC)", "Pakistan Nursing and Midwifery Council (PNMC / PNC)", "Higher Education Commission (HEC)", "College of Physicians and Surgeons Pakistan (CPSP)"), 1),
            Triple("Pakistan Nursing Council (PNC) was established under the PNC Act of:", listOf("1947", "1973", "1952 / 1973", "2000"), 2),
            Triple("Khyber Medical University (KMU) main campus is situated in which city of Khyber Pakhtunkhwa?", listOf("Abbottabad", "Peshawar", "Mardan", "Swat"), 1),
            Triple("The highest mountain peak in Pakistan and second highest in the world is:", listOf("Nanga Parbat", "K2 (Mount Godwin-Austen)", "Tirich Mir", "Broad Peak"), 1),
            Triple("K2 is situated in which mountain range of Pakistan?", listOf("Himalayas", "Karakoram Range", "Hindu Kush", "Sulaiman Range"), 1),
            Triple("Tirich Mir is the highest peak of which mountain range in Pakistan?", listOf("Karakoram", "Hindu Kush", "Himalayas", "Salt Range"), 1),
            Triple("The major river system that forms the backbone of Pakistan's agricultural irrigation network is the:", listOf("Jhelum River", "Indus River System", "Chenab River", "Kabul River"), 1),
            Triple("The Indus Waters Treaty was signed between Pakistan and India in 1960 under the mediation of the:", listOf("United Nations", "World Bank", "International Monetary Fund", "Asian Development Bank"), 1),
            Triple("Under the Indus Waters Treaty, the exclusive rights to the waters of three Western Rivers (Indus, Jhelum, Chenab) were allocated to:", listOf("India", "Pakistan", "China", "Shared 50/50"), 1),
            Triple("Tarbela Dam, one of the largest earth-filled dams in the world, is constructed on which river?", listOf("Jhelum River", "Indus River", "Kabul River", "Chenab River"), 1),
            Triple("Mangla Dam is constructed on which river in Pakistan?", listOf("Indus River", "Jhelum River", "Ravi River", "Sutlej River"), 1),
            Triple("The National Flower of Pakistan is:", listOf("Rose", "Jasmine (Chambeli)", "Tulip", "Sunflower"), 1),
            Triple("The National Animal of Pakistan is the:", listOf("Snow Leopard", "Markhor", "Bengal Tiger", "Chinkara Gazelle"), 1),
            Triple("The National Poet of Pakistan is:", listOf("Faiz Ahmed Faiz", "Allama Muhammad Iqbal", "Mirza Ghalib", "Ahmed Faraz"), 1),
            Triple("The design of the National Flag of Pakistan was prepared by:", listOf("Amir-ud-din Kidwai", "Hafeez Jalandhari", "Choudhry Rahmat Ali", "Abdul Rab Nishtar"), 0),
            Triple("The white crescent and five-pointed star on the Pakistani flag represent:", listOf("Wealth and power", "Progress and Light / Knowledge", "Peace and purity", "Agriculture and industry"), 1),
            Triple("The green field on the National Flag of Pakistan represents:", listOf("Minorities", "Muslim majority population of Pakistan", "Forests", "Agriculture"), 1),
            Triple("The vertical white stripe on the left side of the Pakistani flag represents:", listOf("Peace", "Religious minorities and non-Muslim citizens of Pakistan", "Purity", "Glaciers"), 1),
            Triple("The National Anthem of Pakistan (Qaumi Taranah) lyrics were written by:", listOf("Allama Iqbal", "Hafeez Jalandhari", "Josh Malihabadi", "Habib Jalib"), 1),
            Triple("The music tune of Pakistan's National Anthem was composed by:", listOf("Ahmed Ghulamali Chagla (A.G. Chagla)", "Nisar Bazmi", "Sohail Rana", "Khwaja Khurshid Anwar"), 0),
            Triple("The term 'Pakistan' was coined in 1933 in the pamphlet 'Now or Never' by:", listOf("Allama Iqbal", "Choudhry Rahmat Ali", "Sir Syed Ahmad Khan", "Liaquat Ali Khan"), 1),
            Triple("The Durand Line forms the international border between Pakistan and:", listOf("India", "Afghanistan", "Iran", "China"), 1),
            Triple("The historic Khyber Pass connects Peshawar in Pakistan with:", listOf("Kabul (via Torkham border)", "Tashkent", "Tehran", "Quetta"), 0),
            Triple("The former Federally Administered Tribal Areas (FATA) were merged into Khyber Pakhtunkhwa province under which Constitutional Amendment?", listOf("18th Amendment", "25th Constitutional Amendment (2018)", "21st Amendment", "19th Amendment"), 1),
            Triple("The ancient UNESCO World Heritage archaeological site of the Indus Valley Civilization located in Sindh is:", listOf("Taxila", "Mohenjo-Daro", "Harappa", "Takht-i-Bahi"), 1),
            Triple("Harappa, another major city of the ancient Indus Valley Civilization, is located in which province?", listOf("Sindh", "Punjab (Sahiwal District)", "Khyber Pakhtunkhwa", "Balochistan"), 1),
            Triple("The Buddhist monastic complex and UNESCO World Heritage site Takht-i-Bahi is located in which district of KP?", listOf("Swat", "Mardan", "Peshawar", "Abbottabad"), 1),
            Triple("Pakistan became a nuclear power following successful underground nuclear tests at Chagai on:", listOf("14th August 1947", "28th May 1998 (Youm-e-Takbeer)", "23rd March 1940", "6th September 1965"), 1),
            Triple("The China-Pakistan Economic Corridor (CPEC) connects Kashgar in Xinjiang, China, with which deep-sea port in Balochistan, Pakistan?", listOf("Karachi Port", "Gwadar Port", "Bin Qasim Port", "Pasni Port"), 1),
            Triple("Pakistan shares its longest land border with which neighboring country?", listOf("Iran", "India", "Afghanistan (Durand Line ~2,640 km)", "China"), 2),
            Triple("The Karakoram Highway (KKH), also known as the China-Pakistan Friendship Highway, crosses the international border at which high altitude pass?", listOf("Babu Sar Pass", "Khunjerab Pass (4,693 meters)", "Lowari Pass", "Khyber Pass"), 1),
            Triple("Under the Constitution of Pakistan, the Council of Common Interests (CCI) is responsible for resolving disputes between:", listOf("District courts", "The Federal Government and Provincial Governments", "Political parties", "Private corporations"), 1)
        )
        addSubjectQs(5, "Pakistan Studies", "KMU PAK-655 / Kazimi Pakistan Studies & KP Health Policy", pak)

        // Add 100 extra questions for EACH subject in Semester 5 (500 extra questions)
        questions.addAll(KpSemester5PlusQuestionBank.getQuestions(idCounter))

        return questions
    }
}
