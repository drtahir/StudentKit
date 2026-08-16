package com.drtahir.studentkit.ui.screens

/**
 * PART 3: PEDIATRICS (50 MCQs) + OBSTETRICS & GYNECOLOGY (50 MCQs) + PSYCHIATRIC NURSING (30 MCQs)
 * NCLEX-RN, DHA, HAAD, Prometric, PNC & International Competitive Exam Standard.
 */
object Nursing500BankPart3 {

    fun getPediatricObstetricPsychQuestions(startId: Int): List<NursingExamQuestion> {
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
        // PEDIATRIC NURSING (50 QUESTIONS)
        // =========================================================================
        val pedsScenarios = listOf(
            Triple("Acute Epiglottitis Emergency Contraindication", "NEVER examine throat with tongue blade or swab (causes fatal laryngospasm); keep child calm, prepare for emergency intubation/tracheostomy", "Insert tongue blade firmly to inspect posterior pharynx"),
            Triple("Croup (Laryngotracheobronchitis) Manifestations", "Barking seal-like cough, inspiratory stridor, suprasternal retractions; treat with cool mist, nebulized racemic epinephrine, dexamethasone", "Purulent sputum with wheezing relieved by chest percussion"),
            Triple("Bronchiolitis (RSV) Isolation & Care", "Contact precautions; cool humidified oxygen, suctioning nasal passages before feedings, hydration; palivizumab for prevention", "Airborne isolation with immediate oral antibiotics"),
            Triple("Cystic Fibrosis Dietary & Enzyme Care", "Administer pancreatic enzymes WITH all meals and snacks; high-calorie, high-protein, high-fat diet with fat-soluble vitamins (ADEK)", "Administer enzymes 3 hours after meals on empty stomach"),
            Triple("Tetralogy of Fallot Hypercyanotic ('Tet') Spell Action", "Place infant in knee-chest position (increases systemic vascular resistance, reduces right-to-left shunt); give oxygen, morphine", "Place infant flat on back and stretch legs outward"),
            Triple("Coarctation of the Aorta Assessment Findings", "High blood pressure and bounding pulses in upper extremities; low/absent blood pressure and cool feet in lower extremities", "Bounding pulses in feet with low upper extremity BP"),
            Triple("Kawasaki Disease Diagnostic Criteria & Complications", "High fever > 5 days, strawberry tongue, red cracked lips, peeling skin, swollen lymph nodes; complication: coronary artery aneurysms; give IVIG + Aspirin", "Low fever with vesicular rash on back"),
            Triple("Rheumatic Fever Jones Criteria", "Follows Group A Strep pharyngitis; Carditis, Polyarthritis, Chorea, Erythema marginatum, Subcutaneous nodules; major cause of mitral valve damage", "Follows viral hepatitis with severe liver enlargement"),
            Triple("Wilms Tumor (Nephroblastoma) Abdominal Caution", "DO NOT PALPATE THE ABDOMEN (risk of rupturing tumor capsule and disseminating cancer cells); place sign over bed", "Vigorously palpate abdomen every 2 hours to measure tumor size"),
            Triple("Hirschsprung Disease Manifestations", "Congenital aganglionic megacolon; failure to pass meconium in 24-48 hours, ribbon-like foul-smelling stools, abdominal distension", "Profuse watery bright green diarrhea"),
            Triple("Intussusception Classic Triad", "Sudden acute episodic abdominal pain, sausage-shaped abdominal mass, 'currant jelly' stools (blood and mucus); treat with air/barium enema", "Continuous dull flank pain with clear urine"),
            Triple("Hypertrophic Pyloric Stenosis Features", "Olive-shaped mass in epigastrium, non-bilious projectile vomiting after feedings, visible gastric peristaltic waves, metabolic alkalosis", "Bilious green diarrhea with sunken abdomen"),
            Triple("Sickle Cell Vaso-Occlusive Crisis Management", "HYDRATION (IV fluids priority), Oxygenation, Pain management (opioids), blood transfusions; avoid cold temperatures", "Cold ice packs applied over painful joints with fluid restriction"),
            Triple("Hemophilia A Deficiency & Care", "Factor VIII deficiency; X-linked recessive; hemarthrosis (bleeding into joint spaces); treat with Factor VIII concentrate; avoid aspirin", "Factor IX deficiency treated with vitamin K and aspirin"),
            Triple("Acute Lymphoblastic Leukemia (ALL) Assessment", "Anemia (fatigue), Thrombocytopenia (petechiae/bleeding), Neutropenia (fever/infection), bone pain, hepatosplenomegaly", "Massive polycythemia with thick blood"),
            Triple("Cerebral Palsy Physical Assessment", "Persistent primitive reflexes (Moro, tonic neck) past 6 months, exaggerated deep tendon reflexes, scissoring leg posture, spasticity", "Hyporeflexia with normal milestone progression"),
            Triple("Spina Bifida (Myelomeningocele) Pre-Op Care", "Cover sac with sterile saline-soaked non-adherent dressing, place infant in PRONE position, measure head circumference for hydrocephalus", "Place infant in supine position directly on the sac"),
            Triple("Hydrocephalus Signs in Infants", "Bulging fontanelles, increased head circumference, 'setting-sun' eyes, high-pitched cry, dilated scalp veins", "Depressed fontanelle with normal head size"),
            Triple("Cleft Lip & Palate Post-Op Care", "Cleft Lip: position on back or side (NOT prone); Cleft Palate: avoid objects in mouth (suction catheters, spoons, straws); use elbow restraints", "Place cleft lip infant prone on face immediately post-op"),
            Triple("Infant Developmental Milestones (4-6 Months)", "Rolls front to back (4 mos), rolls back to front (6 mos), holds head steady, palmar grasp, responds to name", "Walks independently and speaks 20 words"),
            Triple("Infant Developmental Milestones (9-12 Months)", "Pincer grasp (9 mos), sits unsupported, crawls, stands holding furniture, says 'mama/dada', walks with help (12 mos)", "Rides a tricycle and builds 10-cube tower"),
            Triple("Toddler Developmental Milestones (18-24 Months)", "Walks up stairs, kicks ball, builds tower of 4-6 blocks, 2-3 word phrases, parallel play, temper tantrums", "Skips on alternate feet and ties shoe laces"),
            Triple("Preschooler Developmental Milestones (3-5 Years)", "Rides tricycle (3 yrs), uses scissors, dresses independently, 4-5 word sentences, associative/cooperative play, imaginary friends", "Reads complex medical textbooks"),
            Triple("Erikson Stage: Infant (0-18 Months)", "Trust vs. Mistrust (faith and security developed through consistent caregiver responsiveness)", "Autonomy vs. Shame and Doubt"),
            Triple("Erikson Stage: Toddler (18 Months - 3 Years)", "Autonomy vs. Shame and Doubt (independence achieved through choice and control over bodily functions)", "Industry vs. Inferiority"),
            Triple("Erikson Stage: Preschooler (3-5 Years)", "Initiative vs. Guilt (exploring environment and taking on new activities)", "Identity vs. Role Confusion"),
            Triple("Erikson Stage: School-Age (6-12 Years)", "Industry vs. Inferiority (mastery of social and academic skills; pride in accomplishments)", "Generativity vs. Stagnation"),
            Triple("Piaget Stage: Sensorimotor (0-2 Years)", "Learns through sensory impressions and motor activities; develops Object Permanence around 8 months", "Formal Operational abstract reasoning"),
            Triple("Sudden Infant Death Syndrome (SIDS) Prevention", "Place infant on BACK to sleep ('Back to Sleep'), firm mattress, no loose bedding/stuffed animals, pacifier use, avoid overheating", "Place infant prone on soft feather bed with heavy blankets"),
            Triple("Foreign Body Aspiration Infant vs Child Action", "Infant (<1 yr): 5 back blows followed by 5 chest thrusts; Child (>1 yr): abdominal thrusts (Heimlich maneuver); DO NOT perform blind finger sweeps", "Perform deep blind finger sweep in infant throat"),
            Triple("Otitis Media Risk Factors & Instillation", "Eustachian tube short, wide, horizontal; instillation: pull pinna DOWN and BACK for <3 yrs; pull pinna UP and BACK for >3 yrs", "Pull pinna up and back for 6-month-old infant"),
            Triple("Pediatrics: Hypertrophic Pyloric Stenosis Olive-Shaped Mass", "Palpable olive-shaped mass in right upper quadrant, non-bilious projectile vomiting, and hypokalemic hypochloremic metabolic alkalosis", "Biliary projectile vomiting with sunken abdomen and hyperkalemia"),
            Triple("Pediatric Dehydration Oral Rehydration Therapy", "Commercial Oral Rehydration Solution (Pedialyte); give 50-100 mL/kg over 4 hours for mild-moderate dehydration; avoid plain water/soda", "Give undiluted apple juice and boiled tap water"),
            Triple("Reye Syndrome Associated Risk", "Encephalopathy and fatty liver failure following viral illness (flu/chickenpox) in children given ASPIRIN; give acetaminophen for fever instead", "Caused by giving amoxicillin for ear infection"),
            Triple("Atopic Dermatitis (Eczema) Care", "Hydrate skin with emollient creams immediately after lukewarm bath, wear cotton clothing, cut fingernails short, avoid harsh soaps", "Bathe in hot water with scented antibacterial soap"),
            Triple("Impetigo Characteristics & Contagion", "Honey-colored crusted lesions around mouth/nose caused by Strep/Staph; highly contagious; wash with warm soapy water and apply topical mupirocin", "Non-contagious dry scaling over scalp"),
            Triple("Pediculosis Capitis (Head Lice) Treatment", "Permethrin 1% shampoo; comb out nits with fine-tooth comb; wash bedding in hot water and dry on high heat; seal unwashable items for 2 weeks", "Shave child's head completely and burn furniture"),
            Triple("Pinworm (Enterobius vermicularis) Diagnosis", "Perianal itching at night; diagnosis via 'Tape Test' (transparent tape applied to perianal area first thing in morning before bathing)", "Diagnosis via sterile sputum culture"),
            Triple("Scoliosis Screening Assessment", "Adam's Forward Bend Test: observe child bending forward at waist for asymmetrical shoulder height, rib hump, or lateral curvature", "Observe child jumping on one foot"),
            Triple("Developmental Dysplasia of the Hip (DDH) Signs", "Asymmetrical thigh/gluteal skin folds, positive Ortolani (hip abduction click) and Barlow tests, limited hip abduction; treat with Pavlik harness", "Symmetrical skin folds with full hip abduction"),
            Triple("Pavlik Harness Parent Education", "Worn continuously for 23-24 hours/day; do NOT adjust straps; check skin under straps daily for redness; place shirt/socks under harness", "Remove harness for 12 hours every night"),
            Triple("Clubfoot (Talipes Equinovarus) Treatment", "Serial casting (Ponseti method) started immediately after birth; cast changed weekly for 6-12 weeks", "Encourage child to walk in wooden shoes"),
            Triple("Osteogenesis Imperfecta (Brittle Bone Disease)", "Genetic defect in collagen synthesis; fragile bones, blue sclera, hypermobile joints, hearing loss; handle child gently without pulling limbs", "Pull infant by arms and legs during diaper changes"),
            Triple("Juvenile Idiopathic Arthritis (JIA) Care", "Moist heat/warm bath in morning to relieve morning stiffness, regular active ROM exercises, NSAIDs, routine eye exams (uveitis)", "Strict bed rest with joint immobilization"),
            Triple("Type 1 Diabetes Mellitus in Children", "Autoimmune destruction of pancreatic beta cells; triad of Polyuria, Polydipsia, Polyphagia with weight loss; requires lifelong insulin", "Managed strictly with oral metformin and diet"),
            Triple("Pediatric Hypoglycemia Management", "10-15g simple fast-acting carbohydrate (4 oz juice, 6 oz soda, 3-4 glucose tablets); recheck blood glucose in 15 minutes", "Inject 20 units regular insulin subcutaneously"),
            Triple("Attention-Deficit/Hyperactivity Disorder (ADHD)", "Inattention, hyperactivity, impulsivity; administer methylphenidate (Ritalin) in morning AFTER breakfast to prevent appetite suppression/insomnia", "Give methylphenidate at bedtime before sleeping"),
            Triple("Autism Spectrum Disorder (ASD) Environment", "Maintain structured routine, minimize sensory stimulation, provide predictable environment, use clear visual schedules", "Frequent unexpected changes in daily schedule"),
            Triple("Child Abuse (Non-Accidental Trauma) Red Flags", "Inconsistent story, injury incompatible with developmental stage, multiple fractures in various stages of healing, retinal hemorrhages; MANDATORY REPORTING", "Accidental scraped knee compatible with bike fall"),
            Triple("Lead Poisoning (Plumbism) Chelation Threshold", "Blood lead level >= 45 mcg/dL requires chelation therapy (Succimer/EDTA); monitor renal function and hydration", "Blood lead level of 2 mcg/dL requires immediate emergency chelation")
        )

        pedsScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Pediatric Nursing Protocol: ${item.second}",
                "Dangerous Practice: ${item.third}",
                "Delay care until patient reaches adulthood",
                "Discontinue monitoring without reporting"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pediatric Nursing",
                "NCLEX-RN / DHA • Medium",
                "Pediatric Clinical Case #${idx + 1}: In providing specialized nursing care for a pediatric client with ${item.first}, which clinical decision is correct?",
                options,
                correctPos,
                "Rationale: Pediatric nursing practice for ${item.first} specifies: ${item.second}.",
                "Option breakdown: Correct choice ensures pediatric safety and developmental support. Practice '${item.third}' is dangerous.",
                "Pediatrics • ${item.first}"
            )
        }

        // =========================================================================
        // OBSTETRIC & GYNECOLOGICAL NURSING (50 QUESTIONS)
        // =========================================================================
        val obScenarios = listOf(
            Triple("Preeclampsia Signs & Magnesium Sulfate", "Hypertension (BP >= 140/90), proteinuria, edema; Magnesium Sulfate prevents seizures; antidote: Calcium Gluconate", "Low BP with hyporeflexia treated with sodium push"),
            Triple("Magnesium Sulfate Toxicity Warning Signs", "Loss of deep tendon reflexes (patellar), respiratory rate < 12/min, urine output < 30 mL/hr, serum mag > 8 mEq/L", "Hyperreflexia with respiratory rate of 28/min"),
            Triple("Placenta Previa vs Abruptio Placentae", "Placenta Previa: PAINLESS bright red vaginal bleeding; Abruptio Placentae: PAINFUL dark red bleeding with board-like rigid abdomen", "Placenta Previa causes severe dark painful rigid abdomen"),
            Triple("Placenta Previa Digital Exam Contraindication", "NO DIGITAL VAGINAL EXAMINATIONS (can puncture placenta causing fatal hemorrhage); ultrasound confirmation first", "Perform aggressive digital cervical exam"),
            Triple("Ectopic Pregnancy Rupture Warning Signs", "Unilateral lower quadrant pelvic pain, missed menses, referred shoulder pain, severe hypotension/shock", "Bilateral painless leg swelling with high fever"),
            Triple("Intrapartum Fetal Heart Rate Late Decelerations", "Caused by Uteroplacental Insufficiency; Turn client on LEFT side, stop Oxytocin, give O2 via non-rebreather, increase IV fluids", "Increase oxytocin and place patient in supine position"),
            Triple("Intrapartum Fetal Heart Rate Variable Decelerations", "Caused by Cord Compression; Reposition client to side or knee-chest position to relieve pressure on umbilical cord", "Give IV push methergine"),
            Triple("Intrapartum Fetal Heart Rate Early Decelerations", "Caused by Head Compression during contractions; normal harmless finding requiring no intervention", "Emergency C-section for early decelerations"),
            Triple("Umbilical Cord Prolapse Priority Action", "Call for help, elevate presenting fetal part off cord with sterile gloved hand, position in knee-chest/Trendelenburg", "Push umbilical cord back up into uterus with bare hand"),
            Triple("Shoulder Dystocia Emergency Maneuvers", "McRoberts maneuver (hyperflexing mother's legs onto abdomen) + Suprapubic pressure (NOT fundal pressure)", "Apply heavy fundal pressure on top of uterus"),
            Triple("Postpartum Hemorrhage Priority Action", "FUNDAL MASSAGE for uterine atony (soft boggy uterus); administer uterotonics (Oxytocin, Methergine, Cytotec)", "Apply heating pad to belly and elevate head"),
            Triple("Obstetrics: Magnesium Sulfate Toxicity S/S & Calcium Gluconate Antidote", "Loss of deep tendon reflexes (patellar), respiratory depression (< 12 bpm), decreased urine output (< 30 mL/hr); Antidote is Calcium Gluconate IV push", "Antidote for magnesium sulfate toxicity is IV naloxone or protamine sulfate"),
            Triple("Carboprost (Hemabate) Contraindication", "CONTRAINDICATED in clients with Asthma (causes severe bronchospasm); side effect: profuse diarrhea", "Give carboprost to mother with acute severe asthma"),
            Triple("Postpartum Lochia Normal Progression", "Lochia rubra (dark red, days 1-3), Lochia serosa (pink/brown, days 4-10), Lochia alba (yellow/white, days 11-21)", "Lochia alba on day 1 followed by heavy bright red rubra on day 14"),
            Triple("Postpartum Uterine Fundal Involution", "At umbilicus on day 1; descends 1 cm (1 fingerbreadth) per day; unpalpable by day 10-14", "Fundus rises 3 cm into upper chest daily"),
            Triple("Mastitis vs Breast Engorgement", "Mastitis: unilateral hot red painful breast with fever/chills; continue breastfeeding on both breasts to empty; give antibiotics", "Stop breastfeeding completely and bind breasts tightly"),
            Triple("APGAR Score Components & Timing", "Evaluated at 1 and 5 minutes post-birth; Appearance, Pulse, Grimace, Activity, Respiration (score 7-10 normal)", "Evaluated at 30 minutes; score < 3 is perfect"),
            Triple("Newborn APGAR Heart Rate Scoring", "Pulse > 100 bpm = 2 points; Pulse < 100 bpm = 1 point; Absent pulse = 0 points", "Pulse > 100 bpm = 0 points"),
            Triple("Newborn Thermoregulation & Evaporation Heat Loss", "Evaporation occurs when newborn is wet with amniotic fluid; DRY NEWBORN IMMEDIATELY and place skin-to-skin", "Leave newborn wet in cold room air"),
            Triple("Newborn Conduction vs Convection Heat Loss", "Conduction: contact with cold surface (weighing scale); Convection: cold air drafts; Radiation: near cold window", "Conduction occurs from warm skin-to-skin contact"),
            Triple("Newborn Hyperbilirubinemia & Phototherapy", "Phototherapy breaks down unconjugated bilirubin; cover eyes with eye patches, wear diaper ONLY, monitor hydration and temperature", "Cover entire body with dark heavy blankets under light"),
            Triple("Newborn Hypoglycemia Threshold & Signs", "Blood glucose < 40 mg/dL; signs: jitteriness, lethargy, high-pitched cry, poor feeding, hypotonia; feed infant immediately", "Blood glucose of 90 mg/dL indicates severe hypoglycemia"),
            Triple("Rho(D) Immune Globulin (RhoGAM) Administration", "Given to Rh-negative mothers carrying Rh-positive baby at 28 weeks and within 72 hours post-delivery to prevent isoimmunization", "Given to Rh-positive mothers"),
            Triple("Positive Signs of Pregnancy", "Fetal heart tones heard, fetal movement felt by examiner, ultrasound visualization of fetus", "Positive pregnancy test, missed period, morning sickness (presumptive)"),
            Triple("Probable Signs of Pregnancy", "Goodell's sign (softening of cervix), Chadwick's sign (bluish cervix), Hegar's sign (softening of uterine isthmus), positive hCG test", "Visualization of fetus on ultrasound"),
            Triple("Naegele's Rule for Estimated Date of Delivery (EDD)", "First day of Last Menstrual Period (LMP) MINUS 3 months PLUS 7 days PLUS 1 year", "Add 9 months and subtract 20 days from LMP"),
            Triple("GTPAL Pregnancy History Documentation", "Gravida (total pregnancies), Term births (>=37 wks), Preterm births (20-36 wks), Abortions (<20 wks), Living children", "Gravida means total living children"),
            Triple("True vs False Labor Signs", "True labor: regular contractions increasing in frequency/intensity, cervical dilation/effacement, pain radiating from back to abdomen", "False labor: irregular contractions relieved by walking"),
            Triple("Stages of Labor Overview", "Stage 1: Cervical dilation (0-10 cm); Stage 2: Pushing and expulsion of fetus; Stage 3: Delivery of placenta; Stage 4: Postpartum recovery", "Stage 1 is delivery of placenta"),
            Triple("Non-Stress Test (NST) Reactive Result", "Reactive (Reassuring): 2 or more FHR accelerations of >= 15 bpm lasting >= 15 seconds over a 20-minute period", "Non-reactive result with zero accelerations is normal"),
            Triple("Contraction Stress Test (CST) Negative Result", "Negative (Reassuring): NO late decelerations observed with 3 contractions in 10 minutes", "Positive CST with late decelerations is normal"),
            Triple("Amniotomy (Artificial Rupture of Membranes) Priority", "Assess FETAL HEART RATE immediately post-rupture to rule out umbilical cord prolapse; check fluid color/odor", "Give oral sleeping pill"),
            Triple("Group B Streptococcus (GBS) Screening & Care", "Vaginal/rectal swab at 35-37 weeks; if positive, administer IV Penicillin G during labor (at least 4 hours before birth)", "Give oral ampicillin after baby is 1 month old"),
            Triple("Gestational Diabetes Mellitus (GDM) Oral Glucose Tolerance Test", "1-hour 50g OGTT at 24-28 weeks (if >=130-140 mg/dL, proceed to 3-hour 100g diagnostic OGTT)", "Fast for 3 days and take 500g glucose"),
            Triple("TORCH Infections Overview", "Toxoplasmosis (cat litter/raw meat), Other (Syphilis, Varicella), Rubella, Cytomegalovirus, Herpes Simplex; teratogenic risks", "Toxoplasmosis is transmitted by clean bottled water"),
            Triple("Maternal Rubella Vaccine Timing", "Rubella vaccine (MMR) is a live virus: give POSTPARTUM; client must NOT get pregnant for at least 28 days post-vaccination", "Give live MMR vaccine during 1st trimester of pregnancy"),
            Triple("Postpartum Blues vs Postpartum Depression", "Blues: self-limiting mood swings lasting < 2 weeks; Depression: persistent sadness, guilt, inability to care for baby lasting > 2 weeks", "Blues lasts 5 years and requires electroconvulsive therapy"),
            Triple("Cervical Cerclage Purpose & Indication", "Suture placed around cervix at 12-14 weeks for Cervical Insufficiency (incompetent cervix) to prevent premature birth", "Used to accelerate labor at 20 weeks"),
            Triple("Eclampsia Seizure Emergency Care", "Turn client to side, maintain open airway, suction mouth, give O2, administer IV Magnesium Sulfate bolus", "Place tongue blade in mouth and restrain extremities"),
            Triple("Hyperemesis Gravidarum Management", "Severe persistent nausea/vomiting causing weight loss, ketonuria, electrolyte imbalance; IV fluid resuscitation, vitamin B6, antiemetics", "Encourage high-fat spicy meals"),
            Triple("Hydatidiform Mole (Molar Pregnancy) Care", "Benign gestational trophoblastic disease; painless brownish vaginal bleeding, high hCG levels, grape-like vesicles; monitor hCG for 1 year (choriocarcinoma risk)", "Encourage immediate pregnancy 1 week post-evacuation"),
            Triple("Polycystic Ovary Syndrome (PCOS) Features", "Amenorrhea/oligomenorrhea, hirsutism, acne, obesity, insulin resistance, bilateral polycystic ovaries; risk of infertility/endometrial cancer", "Massive estrogen deficiency with early menopause"),
            Triple("Pelvic Inflammatory Disease (PID) Risks", "Ascending infection (Chlamydia/Gonorrhea); risk of ectopic pregnancy, chronic pelvic pain, and tubal infertility", "Protects against ectopic pregnancy"),
            Triple("Cervical Cancer Screening (Pap Smear) Guidelines", "Begin Pap smear screening at age 21 regardless of sexual history; screens for HPV-induced cervical dysplasia", "Begin Pap smear at age 50 every 10 years"),
            Triple("Breast Self-Examination (BSE) Timing", "Perform monthly 3 to 5 days AFTER menses ceases (when breasts are least tender and swollen)", "Perform daily during active menses"),
            Triple("Oral Contraceptive Pill Contraindications", "History of thromboembolism, stroke, CAD, breast cancer, smoking over age 35, severe liver disease", "Safe for 40-year-old heavy smoker with DVT history"),
            Triple("Intrauterine Device (IUD) Warning Signs (PAINS)", "Period late, Abdominal pain, Infection/discharge, Not feeling well, String length change/missing", "String length doubling is expected"),
            Triple("Emergency Contraception (Levonorgestrel / Plan B) Timing", "Take as soon as possible within 72 hours (up to 120 hours) after unprotected intercourse", "Must be taken 24 hours BEFORE intercourse"),
            Triple("Menopause Hormone Replacement Therapy (HRT) Risks", "Estrogen + Progestin HRT increases risk of breast cancer, DVT, stroke, pulmonary embolism; use lowest dose for shortest time", "Eliminates all cancer risk permanently"),
            Triple("Toxic Shock Syndrome (TSS) Prevention", "Change tampons every 4-8 hours, avoid high-absorbency tampons, use sanitary pads at night; caused by Staph aureus toxin", "Leave single super-absorbent tampon in place for 3 days")
        )

        obScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Obstetric Nursing Protocol: ${item.second}",
                "Dangerous Unsafe Practice: ${item.third}",
                "Discontinue vital sign checks",
                "Perform routine paperwork without patient assessment"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Obstetric and Gynecological Nursing",
                "NCLEX-RN / DHA • Medium",
                "Maternal & Gynecological Clinical Case #${idx + 1}: In providing specialized care for an obstetric client presenting with ${item.first}, which clinical decision is correct?",
                options,
                correctPos,
                "Rationale: Obstetric standards of care for ${item.first} dictate: ${item.second}.",
                "Option breakdown: Correct choice prevents maternal/fetal mortality or severe complications. Practice '${item.third}' is unsafe.",
                "Obstetrics • ${item.first}"
            )
        }

        // =========================================================================
        // PSYCHIATRIC / MENTAL HEALTH NURSING (30 QUESTIONS)
        // =========================================================================
        val psychScenarios = listOf(
            Triple("Auditory Hallucinations Intervention", "Acknowledge patient's feeling, state reality gently ('I don't hear the voices, but I know they are real to you'), assess content for command hallucinations", "Argue with patient and prove voices do not exist"),
            Triple("Command Hallucinations Safety Priority", "Assess Content of command ('Are the voices telling you to hurt yourself or others?'); implement direct safety precautions", "Ignore command hallucinations and leave patient alone"),
            Triple("Delusion Management Communication", "Validate emotional feeling behind delusion, present reality without arguing or reinforcing the false belief, redirect to structured activity", "Validate and agree that the FBI is hiding in the ceiling"),
            Triple("Suicide Risk Assessment & Precautions", "Ask DIRECTLY ('Are you thinking of killing yourself?'); remove dangerous items (belts, glass, razors), 1-on-1 constant observation for high risk", "Avoid asking about suicide so as not to put ideas in head"),
            Triple("Sudden Elevation in Mood After Severe Depression", "WARNING SIGN: Patient may have resolved ambivalence and developed a definite suicide plan; implement immediate 1-on-1 observation", "Discharge patient immediately because they are cured"),
            Triple("Bipolar Mania Environmental & Dietary Care", "Low-stimulation quiet room, high-calorie finger foods (sandwich, fruit), clear concise boundaries, structured physical activities", "Place in loud crowded game room with 3-course sit-down meal"),
            Triple("Major Depressive Disorder Nursing Care", "Assist with ADLs without overwhelming, encourage participation in 1-on-1 activities before groups, monitor food/fluid intake", "Force patient to lead a 50-person group presentation"),
            Triple("Anorexia Nervosa Refeeding Syndrome Prevention", "Monitor serum phosphate, potassium, and magnesium during nutritional restoration; observe patient for 1-2 hours POST meals", "Allow patient to go to bathroom alone immediately after eating"),
            Triple("Bulimia Nervosa Assessment Findings", "Russell sign (calluses on knuckles from induced vomiting), dental enamel erosion, parotid gland enlargement, hypokalemia", "Hyperkalemia with dense tooth enamel"),
            Triple("Panic Attack Acute Emergency Action", "Stay with patient, use SHORT SIMPLE calm sentences, reduce environmental stimuli, encourage slow deep breathing", "Leave patient alone in room to figure it out"),
            Triple("Generalized Anxiety Disorder (GAD) Care", "Reassurance, coping mechanisms (deep breathing, progressive muscle relaxation), cognitive behavioral therapy", "Encourage excessive caffeine consumption"),
            Triple("Obsessive-Compulsive Disorder (OCD) Rituals Care", "Initially ALLOW time for ritual to prevent severe panic, then gradually restrict time allocated; teach alternate coping strategies", "Abruptly stop compulsion on day 1 with physical restraint"),
            Triple("Post-Traumatic Stress Disorder (PTSD) Flashback Action", "Grounding techniques (bring attention to present environment: 'You are safe in the hospital, I am your nurse'), stay with patient", "Shake patient forcefully and yell at them"),
            Triple("Schizophrenia Negative Symptoms", "Affective flattening, Anhedonia, Avolition, Alogia, Asociality (5 As)", "Auditory hallucinations and persecutory delusions"),
            Triple("Schizophrenia Positive Symptoms", "Hallucinations, Delusions, Disorganized speech (word salad, neologisms), Catatonia", "Apathy and social withdrawal"),
            Triple("Psychiatry: Lithium Carbonate Level Monitoring & Toxicity", "Therapeutic level 0.6-1.2 mEq/L; toxic level (>1.5 mEq/L) presents with coarse hand tremor, vomiting, confusion, ataxia", "Therapeutic level is 5.0-10.0 mEq/L with no need for blood draws"),
            Triple("Alcohol Withdrawal Delirium Tremens (DTs) Timing & Care", "Occurs 48-72 hours after last drink; autonomic hyperactivity, severe confusion, hallucinations; treat with Chlordiazepoxide (Librium), seizure precautions", "Occurs 10 minutes after drinking vodka"),
            Triple("Wernicke-Korsakoff Syndrome Cause & Prevention", "Thiamine (Vitamin B1) deficiency secondary to chronic alcoholism; Wernicke encephalopathy (ataxia, confusion, ophthalmoplegia); give IV Thiamine BEFORE glucose", "Vitamin C deficiency treated with ascorbic acid"),
            Triple("Opioid Withdrawal Symptoms", "Rhinorrhea, lacrimation, yawning, piloerection (goosebumps), severe abdominal cramping, diarrhea, dilated pupils (NOT life-threatening)", "Fatal seizures with pinpoint pupils"),
            Triple("Psychiatry: Postpartum Psychosis Emergency Care", "Medical emergency characterized by delusions, auditory command hallucinations to harm infant, and severe confusion; requires immediate hospitalization and 1-on-1 infant safety supervision", "Leave mother alone in room to bond with infant during acute psychosis"),
            Triple("Antisocial Personality Disorder Boundary Setting", "Set firm clear limits on manipulative behavior, enforce consequences consistently, focus on patient's actions", "Sympathize with patient and grant special privileges"),
            Triple("Delirium vs Dementia Onset & Reversibility", "Delirium: ACUTE onset, fluctuating consciousness, REVERSIBLE, caused by infection/drugs; Dementia: CHRONIC, progressive, IRREVERSIBLE", "Delirium is permanent chronic progressive memory loss"),
            Triple("Dementia Sundowning Management", "Maintain predictable routine, adequate lighting in evening, soft music, reduce noise, close blinds before dusk", "Turn off all lights and blast loud heavy metal music"),
            Triple("Therapeutic Communication Active Listening", "Open-ended questions ('Tell me more about...'), reflecting feelings, offering self, broad openings, maintaining eye contact", "Giving advice ('You should...'), false reassurance ('Everything will be fine')"),
            Triple("Therapeutic Communication Restating & Clarifying", "Restating ('You feel overwhelmed when...'), Seeking clarification ('I'm not sure I understand, could you explain...')", "Changing subject abruptly and judging patient"),
            Triple("Non-Therapeutic Communication Barriers", "Asking 'Why' questions (implies criticism), giving false reassurance, approving/disapproving, minimizing feelings", "Using open-ended reflection techniques"),
            Triple("Involuntary Psychiatric Admission Criteria", "Danger to self (suicidal), danger to others (homicidal), or gravely disabled (unable to meet basic survival needs)", "Admitted because family dislikes patient's hobby"),
            Triple("Psychiatric Patient Rights", "Right to refuse treatment/medications (unless court ordered or emergency danger), right to informed consent, right to confidentiality", "Loss of all civil rights upon hospital admission"),
            Triple("Grief Stages (Kübler-Ross)", "Denial, Anger, Bargaining, Depression, Acceptance (DABDA); non-linear progression", "Joy, Euphoria, Mania, Complete Cure"),
            Triple("Electroconvulsive Therapy (ECT) Pre/Post-Op Care", "NPO 6-8 hrs pre-op, administer atropine (reduce secretions), muscle relaxant (succinylcholine); post-op: reorient patient, monitor vitals (transient memory loss expected)", "Encourage heavy meal 10 minutes before ECT shock")
        )

        psychScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Therapeutic Psychiatric Care: ${item.second}",
                "Non-Therapeutic / Unsafe Action: ${item.third}",
                "Ignore behavior and discharge without evaluation",
                "Lock patient in closet without documentation"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Psychiatric/Mental Health Nursing",
                "NCLEX-RN / DHA • Medium",
                "Mental Health Clinical Case #${idx + 1}: In caring for a psychiatric client presenting with ${item.first}, which clinical decision is correct?",
                options,
                correctPos,
                "Rationale: Psychiatric nursing principles for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice ensures psychiatric safety, therapeutic communication, and patient rights. Action '${item.third}' is harmful.",
                "Psychiatric Nursing • ${item.first}"
            )
        }

        return list
    }
}
