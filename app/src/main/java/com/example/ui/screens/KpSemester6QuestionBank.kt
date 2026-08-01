package com.example.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 6 QUESTION BANK
 * 50+ Questions per subject (200+ total questions)
 * Subjects:
 * 1. Mental Health / Psychiatric Nursing - 50 Qs
 * 2. Obstetrics & Gynecological Nursing - 50 Qs
 * 3. Culture, Health & Society - 50 Qs
 * 4. Introduction to Nursing Research - 50 Qs
 */
object KpSemester6QuestionBank {

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
                        explanation = "Correct answer: $correctOpt. Aligned with PNC / KMU Semester 6 curriculum.",
                        reference = ref
                    )
                )
            }
        }

        // ==========================================
        // 1. MENTAL HEALTH / PSYCHIATRIC NURSING - 50 Qs
        // ==========================================
        val psy = listOf(
            Triple("A patient experiencing auditory hallucinations hearing voices commanding self-harm is exhibiting what type of symptom in schizophrenia?", listOf("Negative symptom", "Positive symptom", "Cognitive deficit", "Catatonic stupor"), 1),
            Triple("False fixed beliefs that cannot be corrected by logic or evidence are defined as:", listOf("Hallucinations", "Delusions", "Illusions", "Obsessions"), 1),
            Triple("Misinterpretation of an actual external real sensory stimulus is termed an:", listOf("Hallucination", "Illusion", "Delusion", "Idea of reference"), 1),
            Triple("A patient believes that news anchors on television are sending special hidden messages specifically to him. This is a delusion of:", listOf("Grandeur", "Persecution / Paranoia", "Reference", "Control"), 2),
            Triple("The primary therapeutic blood level concentration range for Lithium Carbonate in acute mania is:", listOf("0.2 to 0.5 mEq/L", "0.8 to 1.2 mEq/L", "2.0 to 3.0 mEq/L", "4.0 to 5.0 mEq/L"), 1),
            Triple("Early clinical manifestations of Lithium toxicity include:", listOf("Tachycardia and hypertension", "Nausea, vomiting, diarrhea, coarse hand tremors, and ataxia", "Severe constipation", "Hyperreflexia"), 1),
            Triple("A patient on Lithium must maintain adequate daily intake of which electrolyte to prevent Lithium toxicity?", listOf("Calcium", "Sodium (and fluid balance)", "Potassium", "Magnesium"), 1),
            Triple("First-generation typical antipsychotics (e.g. Haloperidol, Chlorpromazine) cause Extrapyramidal Side Effects (EPS) primarily by blocking:", listOf("Serotonin 5-HT2A receptors", "Dopamine D2 receptors in the nigrostriatal pathway", "Muscarinic M1 receptors", "Histamine H1 receptors"), 1),
            Triple("Acute Dystonia resulting from antipsychotic therapy manifests as:", listOf("Restlessness and pacing", "Severe involuntary muscle spasms of neck (torticollis), jaw, or eyes (oculogyric crisis)", "Involuntary lip smacking and tongue protrusion", "Tremor at rest"), 1),
            Triple("Subjective feeling of intense inner restlessness driving the patient to pace constantly is termed:", listOf("Akathisia", "Tardive Dyskinesia", "Dystonia", "Ataxia"), 0),
            Triple("Irreversible neurological syndrome of involuntary choreoathetoid movements of facial muscles, tongue, and jaw post chronic antipsychotic use is:", listOf("Parkinsonism", "Tardive Dyskinesia", "Neuroleptic Malignant Syndrome", "Akathisia"), 1),
            Triple("Neuroleptic Malignant Syndrome (NMS) is a life-threatening reaction characterized by hyperpyrexia, autonomic instability, muscle rigidity, and elevated level of:", listOf("Serum amylase", "Creatine Kinase (CK / CPK)", "Bilirubin", "Hemoglobin"), 1),
            Triple("A second-generation atypical antipsychotic requiring weekly Complete Blood Count (CBC) monitoring for risk of agranulocytosis is:", listOf("Risperidone", "Clozapine", "Olanzapine", "Quetiapine"), 1),
            Triple("Clozapine therapy must be withheld immediately if the Absolute Neutrophil Count (ANC) drops below:", listOf("3,000/uL", "1,000/uL (or 1,500 depending on protocol)", "5,000/uL", "10,000/uL"), 1),
            Triple("Selective Serotonin Reuptake Inhibitors (SSRIs) like Sertraline and Escitalopram typically require how long to achieve full therapeutic antidepressant effect?", listOf("24 hours", "2 to 4 weeks (up to 6 weeks)", "3 months", "Immediately"), 1),
            Triple("When transitioning a patient from a Monoamine Oxidase Inhibitor (MAOI) to an SSRI, a washout period of at least how many weeks is mandatory?", listOf("3 days", "2 weeks", "6 weeks", "12 weeks"), 1),
            Triple("Drinking red wine or eating aged cheese while taking an MAOI antidepressant can trigger a fatal:", listOf("Serotonin syndrome", "Hypertensive crisis due to tyramine accumulation", "Aplastic anemia", "Hypoglycemic coma"), 1),
            Triple("Severe Serotonin Syndrome clinical presentation features:", listOf("Hypothermia and hyporeflexia", "Hyperthermia, agitation, hyperreflexia, myoclonus, and autonomic instability", "Bradycardia and constipation", "Polyuria"), 1),
            Triple("Electroconvulsive Therapy (ECT) is primarily indicated for patients with:", listOf("Mild social anxiety", "Severe, treatment-resistant major depression or acute suicidal catatonia", "Personality disorders", "Substance use disorder"), 1),
            Triple("Common expected short-term side effect following Electroconvulsive Therapy (ECT) is:", listOf("Permanent paralysis", "Temporary retrograde amnesia and transient confusion", "Hypertension", "Renal failure"), 1),
            Triple("Pre-operative administration of Atropine Sulfate before ECT is given to:", listOf("Induce sleep", "Reduce secretions and prevent vagal-induced bradycardia", "Paralyze skeletal muscles", "Prevent post-op pain"), 1),
            Triple("Succinylcholine is administered during ECT pre-procedure to:", listOf("Induce general anesthesia", "Provide short-acting muscle relaxation to prevent bone fractures during seizure", "Elevate seizure threshold", "Suppress nausea"), 1),
            Triple("During a panic attack, hyperventilation causes which acid-base disturbance?", listOf("Metabolic Acidosis", "Respiratory Alkalosis", "Respiratory Acidosis", "Metabolic Alkalosis"), 1),
            Triple("When caring for a patient experiencing a severe panic attack, the nurse's priority action is to:", listOf("Leave the patient alone to calm down", "Stay with the patient, maintain a calm presence, and use short simple directions", "Expose patient to crowds", "Order psychological testing"), 1),
            Triple("Agoraphobia is characterized by intense fear and avoidance of:", listOf("Heights", "Enclosed places", "Places or situations where escape might be difficult or embarrassing in event of panic", "Spiders"), 2),
            Triple("Obsessive-Compulsive Disorder (OCD) feature defined as repetitive, purposeful behaviors performed to neutralize anxiety from obsessions is:", listOf("Obsession", "Compulsion", "Phobia", "Delusion"), 1),
            Triple("When planning care for an OCD patient freshly admitted, the nurse should initially:", listOf("Immediately forbid all compulsive rituals", "Allow time for rituals while gradually setting boundaries and teaching alternative coping mechanisms", "Isolate patient", "Administer ECT"), 1),
            Triple("Post-Traumatic Stress Disorder (PTSD) diagnosis requires symptoms persisting for more than 1 month featuring intrusive flashbacks, hyperarousal, and:", listOf("Flight of ideas", "Avoidance of trauma-related stimuli and emotional numbing", "Manic grandiosity", "Auditory hallucinations"), 1),
            Triple("Wernicke-Korsakoff Syndrome in chronic alcoholism results from severe nutritional deficiency of:", listOf("Vitamin C", "Thiamine (Vitamin B1)", "Vitamin B12", "Niacin"), 1),
            Triple("Alcohol Withdrawal Delirium (Delirium Tremens) typically begins how many hours after the last drink?", listOf("2 to 4 hours", "48 to 72 hours (up to 90 hours)", "1 week", "2 weeks"), 1),
            Triple("Medication prescribed for alcohol deterrence that causes severe headache, flushing, and vomiting if alcohol is consumed is:", listOf("Naltrexone", "Disulfiram (Antabuse)", "Acamprosate", "Methadone"), 1),
            Triple("Methadone is an opioid agonist used in substance use rehabilitation for:", listOf("Reversing opioid overdose", "Opioid detoxification and maintenance maintenance therapy to reduce illicit heroin use", "Treating alcohol withdrawal", "Curing depression"), 1),
            Triple("Anorexia Nervosa features intense fear of weight gain, body image distortion, and severe restriction leading to:", listOf("Binge eating", "Significantly low body weight, amenorrhea, lanugo hair, and hypokalemia", "Obesity", "Hypercalcemia"), 1),
            Triple("Bulimia Nervosa is distinguished from Anorexia Nervosa by recurrent episodes of binge eating followed by:", listOf("Persistent refusal to eat", "Compensatory purging behaviors (vomiting, laxative abuse, excessive exercise) while maintaining normal/near-normal weight", "Weight gain over 100 kg", "Complete lack of concern for weight"), 1),
            Triple("A key life-threatening metabolic risk during initial nutritional refeeding of a severely emaciated Anorexia patient is:", listOf("Hyperkalemia", "Refeeding Syndrome characterized by severe hypophosphatemia and cardiac arrest", "Hypercalcemia", "Metabolic alkalosis"), 1),
            Triple("Borderline Personality Disorder (BPD) central defense mechanism involving viewing people as either 'all good' or 'all bad' is termed:", listOf("Projection", "Splitting", "Rationalization", "Sublimation"), 1),
            Triple("Antisocial Personality Disorder is characterized by a pervasive pattern of:", listOf("Social anxiety and shyness", "Disregard for and violation of the rights of others, deceitfulness, and lack of remorse", "Submissive clingy behavior", "Odd eccentric beliefs"), 1),
            Triple("A patient experiencing Bipolar I disorder in a manic state demonstrates 'Flight of Ideas', which means:", listOf("Inability to speak", "Rapid continuous shifting from one idea to another with loose tangential connections", "Repetition of single word", "Speaking in whispers"), 1),
            Triple("Therapeutic Nurse-Patient Relationship initial phase where boundaries, goals, and confidentiality are established is:", listOf("Pre-interaction phase", "Orientation phase", "Working phase", "Termination phase"), 1),
            Triple("In the Working Phase of therapeutic relationship, the nurse and patient focus on:", listOf("Setting contract terms", "Problem-solving, insight development, overcoming resistance, and behavioral change", "Saying goodbye", "Reviewing patient file before meeting"), 1),
            Triple("Transference in psychiatric nursing occurs when the patient:", listOf("Unconsciously displaces feelings and attitudes belonging to a past significant person onto the nurse", "Transfers money to hospital", "Refuses medication", "Copies nurse's accent"), 0),
            Triple("Countertransference occurs when:", listOf("Patient dislikes the nurse", "Nurse unconsciously projects emotional reactions and unresolved feelings onto the patient", "Doctor disagrees with nurse", "Patient transfers to another unit"), 1),
            Triple("When communicating with an actively paranoid patient, the nurse should:", listOf("Argue and prove delusions are false", "Maintain a neutral, clear, consistent attitude without whispering or laughing in sight", "Touch patient frequently", "Confirm delusions are real"), 1),
            Triple("A patient expressing direct suicidal intent ('I have a gun at home and plan to end it tonight') requires immediate nursing intervention of:", listOf("Assuring total secrecy", "Placing on continuous 1-on-1 suicide precautions and securing environment", "Discharging patient home", "Assigning self-study reading"), 1),
            Triple("Catatonic schizophrenia featuring 'Waxy Flexibility' means the patient:", listOf("Moves constantly", "Allows limbs to be placed in uncomfortable positions which are held rigidly for long periods", "Is highly aggressive", "Talks incessantly"), 1),
            Triple("Neologisms in psychiatric speech evaluation refer to:", listOf("Rhyming words", "Newly invented coining of words that have meaning only to the patient", "Repeating words spoken by examiner", "Complete mutism"), 1),
            Triple("Echolalia refers to pathological:", listOf("Involuntary repetition or parroting of words spoken by another person", "Imitation of movements", "Inability to swallow", "Excessive writing"), 0),
            Triple("Echopraxia refers to pathological:", listOf("Repetition of words", "Involuntary imitation or copying of another person's physical movements", "Loss of speech", "Somatic hallucination"), 1),
            Triple("Dementia differs from Delirium primarily because Dementia is:", listOf("Sudden reversible acute cognitive impairment", "Progressive, irreversible, chronic global decline in cognitive function", "High fever induced", "Transient 2-hour state"), 1),
            Triple("Delirium is characterized clinically by:", listOf("Slow onset over 10 years", "Sudden onset, fluctuating course, impaired consciousness, and altered attention span (reversible)", "Normal memory", "Fixed personality"), 1)
        )
        addSubjectQs(6, "Mental Health / Psychiatric Nursing", "KMU PSY-661 / Videbeck Psychiatric-Mental Health Nursing", psy)

        // ==========================================
        // 2. OBSTETRICS & GYNECOLOGICAL NURSING - 50 Qs
        // ==========================================
        val obg = listOf(
            Triple("Naegele's Rule for calculating Estimated Date of Delivery (EDD) from Last Menstrual Period (LMP) is:", listOf("Add 7 days to LMP, subtract 3 months, add 1 year", "Subtract 7 days from LMP, add 9 months", "Add 14 days to LMP, subtract 2 months", "Add 1 month to LMP"), 0),
            Triple("Using Naegele's rule, if a pregnant woman's LMP was July 10, her EDD is:", listOf("April 17 of next year", "April 17 of next year", "April 10 of next year", "October 17"), 0),
            Triple("In obstetric GTPAL notation, the letter 'P' stands for:", listOf("Past pregnancies", "Preterm births (number of births between 20 and 37 weeks)", "Parity total", "Placental abruption"), 1),
            Triple("Chadwick's sign is a probable sign of pregnancy characterized by:", listOf("Softening of the lower uterine segment", "Bluish-violet discoloration of cervix and vaginal mucosa due to increased vascularity", "Painless uterine contractions", "Rebound tenderness"), 1),
            Triple("Hegar's sign of pregnancy refers to:", listOf("Softening of the uterine isthmus / lower uterine segment", "Cervical cyanosis", "Fetal movement felt by examiner", "Positive urine hCG test"), 0),
            Triple("Goodell's sign of pregnancy refers to:", listOf("Softening of the cervical tip", "Bluish vagina", "Abdominal enlargement", "Auscultation of fetal heart rate"), 0),
            Triple("Positive (diagnostic) signs of pregnancy confirming presence of a fetus include:", listOf("Amenorrhea and morning sickness", "Auscultation of fetal heart tones, visualization on ultrasound, and fetal movement felt by examiner", "Positive pregnancy test", "Uterine enlargement"), 1),
            Triple("Normal baseline fetal heart rate (FHR) in a healthy term fetus ranges between:", listOf("80 to 110 bpm", "110 to 160 beats per minute", "160 to 200 bpm", "60 to 100 bpm"), 1),
            Triple("The First Stage of Labor spans from onset of true labor contractions to:", listOf("Delivery of the baby", "Complete cervical effacement and dilation to 10 cm", "Delivery of placenta", "2 hours postpartum"), 1),
            Triple("The Second Stage of Labor spans from full cervical dilation (10 cm) to:", listOf("Onset of regular contractions", "Complete expulsion / birth of the infant", "Delivery of placenta", "Uterine involution"), 1),
            Triple("The Third Stage of Labor involves:", listOf("Cervical dilation", "Expulsion and delivery of the placenta and membranes", "Fetal descent", "Repair of episiotomy"), 1),
            Triple("The Fourth Stage of Labor is defined as the:", listOf("Latent phase of dilation", "First 1 to 2 hours postpartum following placental delivery (recovery phase)", "Active pushing phase", "Third trimester"), 1),
            Triple("Uterine fundal height measurement in centimeters between 18 and 30 weeks gestation typically equals:", listOf("Gestational age in weeks (+/- 2 cm)", "Gestational age minus 10 cm", "Double the gestational age", "Constant 20 cm"), 0),
            Triple("At 20 weeks gestation, the uterine fundus is normally palpable at the level of the:", listOf("Symphysis pubis", "Umbilicus", "Xiphoid process", "Costal margin"), 1),
            Triple("At 36 weeks gestation, the uterine fundus reaches its highest level at the:", listOf("Umbilicus", "Xiphoid process", "Symphysis pubis", "Mid-abdomen"), 1),
            Triple("Leopold's First Maneuver (Fundal Grip) determines:", listOf("Fetal presentation and what fetal part occupies the uterine fundus", "Location of fetal back", "Degree of descent into pelvis", "Cervical dilation"), 0),
            Triple("Leopold's Second Maneuver (Umbilical Grip) identifies the location of the:", listOf("Fetal head", "Fetal back and small fetal limbs / extremities", "Pelvic inlet", "Placental site"), 1),
            Triple("Preeclampsia diagnosis requires new-onset hypertension (BP >= 140/90) after 20 weeks gestation combined with:", listOf("High fever", "Proteinuria or signs of end-organ dysfunction (e.g. thrombocytopenia, elevated liver enzymes)", "Glycosuria", "Severe anemia"), 1),
            Triple("Eclampsia is defined as preeclampsia accompanied by new onset of:", listOf("Generalized tonic-clonic seizures or coma", "Jaundice", "Polyuria", "Placenta previa"), 0),
            Triple("The drug of choice administered IV for prevention and treatment of eclamptic seizures is:", listOf("Phenytoin", "Magnesium Sulfate", "Diazepam", "Sodium Nitroprusside"), 1),
            Triple("The therapeutic serum Magnesium level for a preeclamptic patient receiving IV Magnesium Sulfate infusion is:", listOf("1.0 to 2.0 mEq/L", "4.0 to 7.0 mEq/L (or 4-8 mg/dL)", "10.0 to 15.0 mEq/L", "20.0 mEq/L"), 1),
            Triple("An essential clinical assessment prior to administering each dose of Magnesium Sulfate includes evaluating:", listOf("Blood glucose", "Patellar Deep Tendon Reflexes (DTRs), respiratory rate (>=12/min), and urine output (>=30 mL/hr)", "Serum amylase", "Auscultating bowel sounds"), 1),
            Triple("An early sign of Magnesium Sulfate toxicity in a preeclamptic patient is:", listOf("Hyperreflexia", "Loss / disappearance of deep tendon reflexes (patellar reflex)", "Tachycardia", "Hypertension"), 1),
            Triple("Antidote for Magnesium Sulfate toxicity that must be immediately available at bedside is:", listOf("Protamine Sulfate", "Calcium Gluconate 10% IV", "Naloxone", "Vitamin K"), 1),
            Triple("HELLP Syndrome in severe preeclampsia stands for:", listOf("Hypertension, Elevated Leukocytes, Low Platelets", "Hemolysis, Elevated Liver enzymes, and Low Platelet count", "Hyperglycemia, Edema, Liver Lesion, Proteinuria", "Hepatic Encephalopathy, Low Lipids, Pain"), 1),
            Triple("Placenta Previa is characterized by abnormal implantation of placenta over lower uterine segment near/covering cervical os presenting with:", listOf("Severe painful dark red bleeding", "Painless, bright red vaginal bleeding in 3rd trimester", "Severe abdominal rigidity", "High fever"), 1),
            Triple("Abruptio Placentae (premature separation of placenta) classically presents with:", listOf("Painless bright red bleeding", "Painful dark red vaginal bleeding, uterine tenderness, and board-like wooden abdomen", "Cervical dilation 5 cm", "Hypotension without pain"), 1),
            Triple("In Placenta Previa, which examination procedure is strictly CONTRAINDICATED?", listOf("Abdominal ultrasound", "Digital vaginal examination (bimanual exam)", "Non-stress test", "External fetal monitoring"), 1),
            Triple("Ectopic pregnancy most common site of abnormal implantation is the:", listOf("Ovary", "Ampulla of Fallopian Tube", "Cervix", "Peritoneal cavity"), 1),
            Triple("Classic clinical triad of ruptured Ectopic Pregnancy includes amenorrhea, vaginal spotting, and:", listOf("High fever", "Unilateral sharp lower abdominal / pelvic pain radiating to shoulder (Kehr's sign)", "Painless bleeding", "Bilateral pedal edema"), 1),
            Triple("Gestational Trophoblastic Disease (Hydatidiform Mole / Molar Pregnancy) diagnostic ultrasound appearance is described as:", listOf("Single live fetus", "Snowstorm appearance (grape-like vesicles) with absence of fetal heartbeat and markedly high hCG", "Bicornuate uterus", "Ectopic mass"), 1),
            Triple("Postpartum Hemorrhage (PPH) following vaginal birth is defined as blood loss exceeding:", listOf("200 mL", "500 mL (or >1000 mL for Cesarean section)", "1,000 mL for vaginal", "2,000 mL"), 1),
            Triple("The most common primary cause of early Postpartum Hemorrhage (accounting for 80% of cases) is:", listOf("Cervical laceration", "Uterine Atony (failure of uterus to contract firmly)", "Retained placenta", "Disseminated intravascular coagulation"), 1),
            Triple("Initial immediate nursing action for Postpartum Hemorrhage caused by uterine atony is to:", listOf("Notify physician", "Perform firm fundal massage until uterus becomes contracted and hard", "Administer blood transfusion", "Pack vagina with gauze"), 1),
            Triple("Uterotonic medication administered routinely IM/IV immediately following delivery of infant to prevent PPH is:", listOf("Magnesium Sulfate", "Oxytocin (Pitocin)", "Terbutaline", "Nifedipine"), 1),
            Triple("Ergot derivative Methylergonovine (Methergine) used for PPH is CONTRAINDICATED in patients with:", listOf("Asthma", "Hypertension or Preeclampsia", "Diabetes", "Anemia"), 1),
            Triple("Prostaglandin F2-alpha (Carboprost / Hemabate) used for severe PPH is CONTRAINDICATED in patients with history of:", listOf("Hypertension", "Asthma / Bronchospasm", "Penicillin allergy", "Renal stones"), 1),
            Triple("Lochia Rubra in normal postpartum recovery consists of dark red discharge lasting for:", listOf("1 to 3 days post-delivery", "10 to 14 days", "6 weeks", "24 hours only"), 0),
            Triple("Lochia Serosa appearing around day 4 to 10 postpartum is characterized by:", listOf("Bright red blood", "Pinkish-brown serosanguinous discharge", "Yellowish-white cream discharge", "Clear watery fluid"), 1),
            Triple("Lochia Alba appearing after day 10 up to 3 to 6 weeks postpartum is:", listOf("Dark red", "Yellowish-white creamy discharge", "Profuse blood clots", "Greenish purulent discharge"), 1),
            Triple("Postpartum Endometritis (uterine infection) characteristic clinical sign is:", listOf("Normal lochia without fever", "Fever (>38.0°C), uterine tenderness, and foul-smelling lochia", "Lower extremity edema", "Engorged breasts"), 1),
            Triple("Mastitis (breast tissue infection in lactating mothers) primary management includes antibiotics and instructing the mother to:", listOf("Stop breastfeeding immediately on affected side", "Continue frequent breastfeeding or pumping to empty affected breast completely", "Apply cold packs only", "Bind breasts tightly"), 1),
            Triple("APGAR score is evaluated at 1 minute and 5 minutes post-birth assessing 5 criteria: Appearance, Pulse, Grimace, Activity, and:", listOf("Appetite", "Respiration", "Alertness", "Weight"), 1),
            Triple("An APGAR score of 3 in a newborn indicates:", listOf("Normal vigorous infant", "Severe distress requiring immediate resuscitation", "Mild respiratory depression", "Transient cyanosis"), 1),
            Triple("Tocolytic medication Terbutaline (Beta-2 agonist) administered to stop preterm labor causes major maternal side effect of:", listOf("Bradycardia", "Maternal Tachycardia and palpitations", "Hypoglycemia", "Constipation"), 1),
            Triple("Antenatal Corticosteroid (e.g. Betamethasone) administered to mothers in preterm labor at 24-34 weeks functions to:", listOf("Stop labor contractions", "Accelerate fetal lung maturity and surfactant production to prevent Respiratory Distress Syndrome (RDS)", "Prevent infection", "Increase fetal weight"), 1),
            Triple("Variable decelerations on electronic fetal monitoring are caused by umbilical cord compression and are managed by:", listOf("Administering oxytocin", "Changing maternal position (to left lateral), stopping oxytocin, and giving oxygen", "High Fowler's position", "Immediate epidural"), 1),
            Triple("Late decelerations on fetal monitor tracings reflect uteroplacental insufficiency and require immediate intervention of:", listOf("Left lateral maternal positioning, IV fluid bolus, oxygen, and discontinuing oxytocin", "Encouraging pushing", "Trendelenburg position", "Increasing oxytocin rate"), 0),
            Triple("Early decelerations on fetal heart monitor are caused by:", listOf("Uteroplacental insufficiency", "Transient fetal head compression during contractions (benign finding)", "Umbilical cord prolapse", "Fetal hypoxia"), 1),
            Triple("Polycystic Ovarian Syndrome (PCOS) long-term endocrine health risk includes development of:", listOf("Type 2 Diabetes Mellitus and Endometrial Hyperplasia / Cancer", "Hypothyroidism", "Addison's disease", "Osteoporosis"), 0)
        )
        addSubjectQs(6, "Obstetrics & Gynecological Nursing", "KMU OBG-662 / DC Dutta's Textbook of Obstetrics", obg)

        // ==========================================
        // 3. CULTURE, HEALTH & SOCIETY - 50 Qs
        // ==========================================
        val soc = listOf(
            Triple("Sociology of health defines 'Illness' distinct from 'Disease' as:", listOf("Pathological cellular change confirmed by lab", "The subjective personal, social, and cultural experience of living with a health condition", "Hospital billing category", "Infectious outbreak"), 1),
            Triple("Talcott Parsons' sociological concept of the 'Sick Role' posits that a sick individual is:", listOf("Expected to perform normal work duties", "Exempted from normal social roles but obligated to seek competent professional medical help and desire to get well", "Punished by society", "Permanently isolated"), 1),
            Triple("Ethnocentrism in transcultural nursing refers to:", listOf("Respecting all cultures equally", "Belief that one's own cultural beliefs and practices are superior to those of other cultures", "Adapting to local customs", "Bilingual communication"), 1),
            Triple("Cultural Relativism is the sociological principle stating that:", listOf("One's culture is superior to all others", "Beliefs and health practices should be understood within the context of the individual's own culture", "Culture has no impact on health", "All cultures should merge into one"), 1),
            Triple("Cultural Competence in healthcare delivery requires nurses to:", listOf("Force patients to adopt hospital culture", "Develop self-awareness of biases, acquire cultural knowledge, and adapt care respectfully to patient's cultural values", "Ignore patient's background", "Speak 10 languages"), 1),
            Triple("Madeleine Leininger's Culture Care Theory (Sunrise Model) emphasizes providing care that is:", listOf("Standardized identical for all patients", "Culturally congruent, safe, and meaningful to people of diverse backgrounds", "Purely pharmacological", "Cost-effective only"), 1),
            Triple("Social Determinants of Health (SDOH) encompass conditions in which people are born, grow, live, work, and age, including:", listOf("DNA sequence only", "Socioeconomic status, education, physical environment, employment, and social support networks", "Cellular organelle structure", "Hospital room decor"), 1),
            Triple("In Khyber Pakhtunkhwa societal culture, 'Pashtunwali' unwritten ethical code includes the principle of hospitality termed:", listOf("Melmastia", "Nanawatai", "Badal", "Jirga"), 0),
            Triple("In Pashtun culture, 'Nanawatai' refers to the customary tradition of:", listOf("Hospitality to guests", "Asylum / seeking forgiveness and reconciliation to end a dispute", "Blood revenge", "Council of elders"), 1),
            Triple("In KP traditional community dispute resolution, a council of respected tribal elders convened to resolve conflict is called a:", listOf("Punchayet", "Jirga", "Shura", "Loya"), 1),
            Triple("In sociological terms, 'Stigma' associated with health conditions like Mental Illness or Leprosy results in:", listOf("Special financial bonuses", "Social devaluation, discrimination, marginalization, and reluctance to seek medical care", "Faster hospital discharge", "Higher social status"), 1),
            Triple("Health Disparity refers to preventable differences in burden of disease or opportunities to achieve optimal health experienced by:", listOf("Genetically identical twins", "Socially disadvantaged populations based on factors like income, gender, or geographic region", "All hospital patients equally", "Private clinic patients"), 1),
            Triple("In gender and health sociology, 'Gender' differs from 'Sex' because Gender represents:", listOf("Biological chromosomes and anatomy", "Socially constructed roles, behaviors, expectations, and identities associated with being male or female", "Legal birth certificate code", "Hormonal levels"), 1),
            Triple("The epidemiological transition describes a shift in population disease patterns from:", listOf("Chronic non-communicable diseases to acute trauma", "High prevalence of infectious communicable diseases to predominance of chronic non-communicable diseases (NCDs)", "Genetic diseases to nutritional deficiencies", "Mental illness to physical injury"), 1),
            Triple("Demographic Transition Model Phase 2 features:", listOf("High birth rate and high death rate", "High birth rate and rapidly falling death rate leading to rapid population growth", "Low birth rate and low death rate", "Negative population growth"), 1),
            Triple("A nuclear family structure consists of:", listOf("Grandparents, aunts, uncles, and cousins living together", "Parents and their biological/adopted children residing in one household", "Single parent only", "Blended step-family"), 1),
            Triple("In traditional joint family systems common in South Asia, healthcare decision-making is frequently dominated by the:", listOf("Youngest child", "Head of the family (elder patriarch/matriarch)", "Individual patient alone", "Neighbors"), 1),
            Triple("Medicalization in medical sociology refers to the process whereby:", listOf("All diseases are cured by drugs", "Non-medical human conditions and social problems become defined and treated as medical illnesses (e.g. childbirth, alcoholism)", "Hospitals build more rooms", "Doctors retire"), 1),
            Triple("Iatrogenesis refers to harm, illness, or adverse effect caused to a patient by:", listOf("Natural disease progression", "Medical treatment or healthcare provider intervention", "Patient non-compliance", "Environmental weather"), 1),
            Triple("Traditional Health Belief System 'Hot and Cold' theory of disease balance posits that health requires:", listOf("Fever control with ice", "Balancing hot and cold foods, environment, and bodily humors", "Drinking hot tea only", "Avoiding cold weather"), 1),
            Triple("In traditional rural KP, folk healers providing spiritual healing and prayers for illness are locally known as:", listOf("Hakeem", "Dam-Ghar / Peer / Amil", "Dai", "Jarrah"), 1),
            Triple("A traditional un-trained birth attendant in South Asian rural communities is termed a:", listOf("Lady Health Visitor (LHV)", "Traditional Birth Attendant (TBA) / Dai", "Community Midwife", "Lady Health Worker"), 1),
            Triple("Unani System of Medicine practiced in South Asia is historically derived from the teachings of ancient Greek physicians like:", listOf("Charaka", "Hippocrates and Galen (Ibn Sina / Avicenna)", "Osler", "Nightingale"), 1),
            Triple("In medical sociology, 'Compliance' / 'Adherence' to treatment plans is strongly influenced by patient's:", listOf("Blood group", "Health beliefs, cultural alignment, health literacy, and financial capacity", "Eye color", "Height"), 1),
            Triple("Health Literacy is defined as the degree to which individuals have the capacity to:", listOf("Read a medical textbook", "Obtain, process, understand, and act on basic health information needed to make appropriate health decisions", "Write a drug prescription", "Memorize anatomy"), 1),
            Triple("High out-of-pocket expenditure on healthcare in developing nations leads to 'Catastrophic Health Expenditure', pushing families into:", listOf("Poverty and financial insolvency", "Middle class", "Higher taxation", "Insurance ownership"), 0),
            Triple("In sociological research, 'Qualitative' methods like Focus Group Discussions (FGDs) are ideal for exploring:", listOf("Statistical incidence rates", "Community perceptions, cultural values, beliefs, and lived social experiences", "Drug dosage curves", "Mortality numbers"), 1),
            Triple("Participant Observation in anthropological health research involves the researcher:", listOf("Distributing 1,000 questionnaires", "Immersing themselves directly in the community's daily life and observing behaviors firsthand", "Testing blood samples in lab", "Conducting telephone surveys"), 1),
            Triple("Institutional Racism in healthcare systems manifests as:", listOf("Personal prejudice of one nurse", "Differential access to quality goods, services, and opportunities in healthcare embedded in policies and institutions", "Lack of hospital parking", "Language accents"), 1),
            Triple("Social Stratification refers to dividing a society into hierarchical layers based on:", listOf("Personality traits", "Socioeconomic status, wealth, power, occupation, and social prestige", "Age alone", "Blood pressure levels"), 1),
            Triple("Social Mobility refers to the movement of individuals or families between different levels of:", listOf("Physical fitness", "Social hierarchy / socioeconomic status over time", "Hospital wards", "Geographic locations"), 1),
            Triple("Acculturation is the process where an immigrant group:", listOf("Completely loses all original culture", "Adopts cultural patterns of the host society while retaining elements of their original culture", "Rejects all host laws", "Returns home"), 1),
            Triple("Assimilation differs from acculturation because assimilation occurs when a minority group:", listOf("Maintains distinct culture", "Fully absorbs and integrates into the dominant culture, losing distinct original cultural identity", "Lives in isolation", "Rejects host language"), 1),
            Triple("Enforced social isolation and discrimination against individuals diagnosed with HIV/AIDS in communities is an example of:", listOf("Primary prevention", "Social Stigmatization and Marginalization", "Cultural competence", "Epidemiological transition"), 1),
            Triple("Inverse Care Law stated by Julian Tudor Hart posits that:", listOf("Good medical care is cheapest for poor", "The availability of good medical care tends to vary inversely with the need for it in the population served", "Rich people get less medical care", "Medical care is equal everywhere"), 1),
            Triple("In transcultural nursing assessment, assessing space orientation includes recognizing that personal space boundaries are:", listOf("Identical across all world cultures", "Culturally determined and vary significantly between intimate, personal, and social distances", "Irrelevant to patient care", "Constant at 1 meter"), 1),
            Triple("Poverty cycle in health sociology demonstrates that ill-health leads to:", listOf("Increased savings", "Reduced productivity/earning capacity, triggering medical debt, poor nutrition, and further illness", "Improved housing", "Free education"), 1),
            Triple("Qualitative research technique 'In-Depth Interview' is chosen when investigating:", listOf("Large population statistical trends", "Sensitive personal health topics requiring deep exploration of individual narrative", "Blood sugar levels", "Vaccination coverage rates"), 1),
            Triple("Social capital in a community health context refers to:", listOf("Money in local banks", "Social networks, mutual trust, and cohesion that facilitate collective community health action", "City infrastructure", "Hospital equipment"), 1),
            Triple("Urbanization trends in Pakistan impact community health by creating overcrowded informal settlements (slums) characterized by:", listOf("Over-abundance of hospitals", "Inadequate water sanitation, poor housing, and increased communicable disease transmission", "High health literacy", "Clean air"), 1),
            Triple("Khyber Pakhtunkhwa's high burden of preventable childhood infectious disease is historically influenced by public mistrust and health misinformation regarding:", listOf("Surgical care", "Polio routine vaccination campaigns", "Dietary advice", "Eye screening"), 1),
            Triple("Role Conflict in nursing sociology occurs when a nurse experiences:", listOf("Conflict with hospital janitor", "Incompatible expectations arising from holding multiple social/professional roles simultaneously (e.g. nurse vs mother)", "Disagreement on drug dose", "Shift delay"), 1),
            Triple("Burnout syndrome among hospital healthcare providers features three dimensions: Emotional Exhaustion, Depersonalization, and:", listOf("High financial reward", "Reduced feeling of Personal Accomplishment", "Physical hypertrophy", "Increased empathy"), 1),
            Triple("Depersonalization (cynicism) in nursing burnout manifests as:", listOf("Over-involvement with patients", "Unfeeling, callous, or detached attitudes toward patients and colleagues", "Excessive energy", "Paranoid delusion"), 1),
            Triple("In health promotion sociology, the Health Belief Model (HBM) posits that health behavior change is driven by perceived susceptibility, severity, benefits, and:", listOf("Income level only", "Perceived barriers to action and self-efficacy", "Hospital location", "Marital status"), 1),
            Triple("Primary social institution responsible for initial socialization, health habits, and emotional support of individuals is the:", listOf("School", "Family unit", "Peer group", "Mass media"), 1),
            Triple("Sociological term 'Norms' refers to:", listOf("Statistical averages", "Explicit and implicit social rules and expectations that guide acceptable behavior in a group", "Legal statutes only", "Standardized test scores"), 1),
            Triple("Cultural Taboo refers to a behavior that is:", listOf("Encouraged by religion", "Strongly forbidden or restricted by social custom and cultural/religious beliefs", "Practiced daily", "Taught in nursing school"), 1),
            Triple("In South Asian patriarchal societies, female health-seeking behavior is often delayed due to lack of female autonomy and requirement of:", listOf("High education", "Male family member permission and financial dependency", "Hospital membership", "Car ownership"), 1),
            Triple("Community Empowerment in public health initiatives enables local communities to:", listOf("Depend permanently on foreign aid", "Gain mastery, ownership, and control over decisions and actions affecting their health conditions", "Build private hospitals", "Abolish government health departments"), 1)
        )
        addSubjectQs(6, "Culture, Health & Society", "KMU SOC-663 / Helman's Culture, Health and Illness", soc)

        // ==========================================
        // 4. INTRODUCTION TO NURSING RESEARCH - 50 Qs
        // ==========================================
        val res = listOf(
            Triple("The gold standard quantitative research design for establishing cause-and-effect relationships is the:", listOf("Descriptive cross-sectional survey", "Randomized Controlled Trial (RCT)", "Qualitative phenomenological study", "Case report"), 1),
            Triple("Essential elements defining a true experimental study (RCT) are Manipulation, Control Group, and:", listOf("Purposive sampling", "Random Assignment (Randomization) of participants", "Unstructured interviews", "Qualitative thematic coding"), 1),
            Triple("The Independent Variable in a research study is defined as the variable that is:", listOf("Measured as the outcome effect", "Manipulated or tested as the presumed cause by the researcher", "Kept constant", "Unmeasurable"), 1),
            Triple("The Dependent Variable represents the:", listOf("Cause", "Outcome or effect that is measured to assess impact of independent variable", "Confounding factor", "Intervention"), 1),
            Triple("A Null Hypothesis (H0) states that there is:", listOf("A significant positive relationship between variables", "NO significant difference or relationship between the study variables", "A negative correlation", "A proven cause"), 1),
            Triple("Type I Error (Alpha error) in statistical hypothesis testing occurs when the researcher:", listOf("Fails to reject a false null hypothesis", "Rejects a TRUE null hypothesis (false positive finding)", "Calculates wrong mean", "Uses small sample"), 1),
            Triple("Type II Error (Beta error) occurs when the researcher:", listOf("Rejects a true null hypothesis", "Fails to reject (accepts) a FALSE null hypothesis (false negative finding)", "Uses random sampling", "Sets p-value at 0.05"), 1),
            Triple("The standard probability value (p-value) threshold universally accepted for statistical significance in health research is:", listOf("p < 0.50", "p < 0.05", "p < 0.10", "p < 0.01 only"), 1),
            Triple("Statistical p-value less than 0.05 (p < 0.05) indicates that the observed study finding has less than 5% probability of occurring due to:", listOf("Intervention effect", "Random chance alone, thus findings are statistically significant", "Measurement bias", "Small sample size"), 1),
            Triple("Random Sampling (Probability Sampling) ensures that every individual in the target population has an:", listOf("Unequal chance of selection", "Equal and independent chance of being selected for the study", "Assigned position", "Exclusion guarantee"), 1),
            Triple("Simple Random Sampling technique can be performed using:", listOf("Selecting first 10 patients entering clinic", "A random number table or computer generator to draw from complete sampling frame", "Volunteer sign-up", "Purposive selection"), 1),
            Triple("Stratified Random Sampling involves dividing the target population into homogenous subgroups (strata) based on characteristics like gender or age, and then:", listOf("Selecting entire population", "Drawing random samples from EACH stratum proportionally", "Choosing only 1 stratum", "Interviewing experts"), 1),
            Triple("Convenience Sampling (Non-probability sampling) involves selecting participants based on:", listOf("Random computer generator", "Ease of accessibility and availability to the researcher", "Strict lottery system", "Proportional representation"), 1),
            Triple("Purposive Sampling (Judgmental sampling) in qualitative research selects participants who:", listOf("Are chosen purely at random", "Possess specific rich experience or expertise relevant to the phenomenon under study", "Are easiest to reach", "Are willing to take drug trial"), 1),
            Triple("Snowball Sampling (Network sampling) is particularly useful for recruiting study participants from:", listOf("General hospital walk-ins", "Hard-to-reach, marginalized, or hidden populations (e.g. drug users) through participant referrals", "Random phone directories", "Medical students"), 1),
            Triple("Sample size determination in quantitative studies to ensure adequate statistical power is calculated using:", listOf("Rule of thumb", "Power Analysis", "Syllabus length", "Budget total"), 1),
            Triple("In qualitative research, data collection continues until reaching the point of 'Data Saturation', which means:", listOf("Sample size reaches 1000", "No new information or themes are emerging from additional interviews", "Budget runs out", "Participant drops out"), 1),
            Triple("Ethical principle 'Beneficence' in nursing research mandates that researchers must:", listOf("Do no harm", "Maximize potential benefits while minimizing potential risks to study participants", "Pay participants cash", "Publish true names"), 1),
            Triple("Ethical principle 'Non-maleficence' means the researcher's obligation to:", listOf("Do good", "First, do no harm to participants", "Ensure equal distribution of care", "Provide full disclosure"), 1),
            Triple("Ethical principle 'Justice' in clinical research requires:", listOf("Paying all costs", "Fair and equitable selection of research subjects without exploitation", "Obtaining written consent", "Hiding results"), 1),
            Triple("Informed Consent in research must include voluntary participation, full disclosure of risks/benefits, participant comprehension, and:", listOf("Mandatory completion guarantee", "Freedom to withdraw from the study at any time without penalty or impact on care", "Financial payment promise", "Waiving legal rights"), 1),
            Triple("When conducting research involving vulnerable populations (e.g. children, mentally incapacitated), informed consent is obtained from legal guardian alongside the child's:", listOf("Power of attorney", "Assent (agreement in age-appropriate terms)", "Written contract", "School principal"), 1),
            Triple("Institutional Review Board (IRB) / Ethics Review Committee (ERC) primary mandate is to:", listOf("Check author's spelling", "Protect the rights, safety, and well-being of human research subjects", "Secure government funding", "Promote university reputation"), 1),
            Triple("Quantitative research design that examines relationships between two or more variables without manipulating them is a:", listOf("Experimental study", "Correlational study", "Phenomenological study", "Ground Theory"), 1),
            Triple("Correlation Coefficient (r) value ranges from:", listOf("0 to +100", "-1.0 to +1.0", "-0.05 to +0.05", "0 to 10"), 1),
            Triple("Correlation coefficient value of r = -0.85 indicates a:", listOf("Weak positive correlation", "Strong inverse (negative) linear relationship between variables", "No correlation", "Perfect positive correlation"), 1),
            Triple("Cross-Sectional research design collects data from study participants at:", listOf("Multiple follow-up time points over 10 years", "A single specific point in time", "Before and after intervention", "Retrospectively over 20 years"), 1),
            Triple("Longitudinal research design collects data from the same group of subjects:", listOf("At one single point in time", "Repeatedly over an extended period of time (follow-up points)", "In 1 day", "From secondary books"), 1),
            Triple("Phenomenology is a qualitative research approach aimed at understanding the:", listOf("Statistical frequency of disease", "Lived human experience of a concept or phenomenon from participant perspectives", "Historical timeline", "Social structure rules"), 1),
            Triple("Grounded Theory qualitative methodology aims to:", listOf("Test existing quantitative hypotheses", "Generate or develop a theory grounded directly in empirical data collected from participants", "Describe historical archives", "Evaluate hospital budget"), 1),
            Triple("Ethnography qualitative research tradition focuses on studying the:", listOf("Brain neural pathways", "Culture, shared patterns, and behaviors of a intact cultural group in natural setting", "Individual case report", "Laboratory specimens"), 1),
            Triple("Internal Validity of a quantitative study refers to the degree to which:", listOf("Findings can be generalized to outside populations", "The independent variable ACTUALLY produced the observed outcome without confounding bias", "Sample size is large", "Questionnaire is short"), 1),
            Triple("External Validity refers to the extent to which study findings can be:", listOf("Kept secret", "Generalized to other populations, settings, or real-world contexts", "Controlled in lab", "Calculated by SPSS"), 1),
            Triple("Hawthorne Effect poses a threat to internal validity because participants alter behavior due to:", listOf("Medication side effects", "Awareness of being observed in a research study", "Lack of sleep", "Poor instructions"), 1),
            Triple("Pilot Study in research methodology is defined as a:", listOf("Final published study", "Small-scale preliminary trial run of the research design to test feasibility, instruments, and procedures", "Computer simulation", "Literature review"), 1),
            Triple("Likert Scale is a widely used survey measurement tool where respondents rate agreement on a scale typically ranging from:", listOf("1 to 100", "5 points (e.g., Strongly Disagree to Strongly Agree)", "Yes or No", "True or False"), 1),
            Triple("Reliability test 'Cronbach's Alpha' evaluates the internal consistency of a multi-item questionnaire, where acceptable reliability is indicated by alpha value equal to or exceeding:", listOf("0.10", "0.70", "0.30", "0.50"), 1),
            Triple("Test-Retest Reliability measures an instrument's consistency over:", listOf("Different raters", "Time when administered to the same subjects on two separate occasions", "Different languages", "Different subjects"), 1),
            Triple("Inter-Rater Reliability evaluates consistency of measurement results obtained by:", listOf("Single researcher on different days", "Two or more independent observers evaluating the same phenomenon", "Computer vs human", "Pre-test vs post-test"), 1),
            Triple("In statistical data analysis, the 'Mean' is defined as the:", listOf("Most frequently occurring score", "Arithmetic average calculated by summing all values and dividing by total count", "Middle score in ordered dataset", "Difference between high and low"), 1),
            Triple("The 'Median' is defined as the:", listOf("Arithmetic average", "Middle score that divides a ranked dataset into two equal halves (50th percentile)", "Most frequent score", "Standard deviation"), 1),
            Triple("The 'Mode' in descriptive statistics represents the:", listOf("Average score", "Most frequently occurring value in a dataset", "Highest score", "Spread of data"), 1),
            Triple("Standard Deviation (SD) measures the:", listOf("Central tendency average", "Degree of dispersion or spread of data values around the mean", "Total sum of scores", "Sample error percentage"), 1),
            Triple("Parametric statistical tests (e.g. Student's t-test, ANOVA) require that the study data follow a:", listOf("Skewed non-normal distribution", "Normal bell-shaped distribution curve", "Qualitative narrative format", "Bimodal distribution"), 1),
            Triple("Independent Samples t-test is used to compare the means of:", listOf("One group pre and post test", "TWO independent / separate groups on a continuous dependent variable", "Three or more groups", "Categorical variables"), 1),
            Triple("Paired Samples t-test (Dependent t-test) is used to compare means of:", listOf("Two completely separate groups", "The SAME group at two different time points (e.g., Pre-test vs Post-test scores)", "Four independent groups", "Qualitative themes"), 1),
            Triple("One-Way Analysis of Variance (ANOVA) statistical test is used to compare the means of:", listOf("Two groups only", "THREE or more independent groups on a continuous outcome variable", "Qualitative categories", "Correlation pairs"), 1),
            Triple("Chi-Square Test (X2) is a non-parametric test used to analyze the association between two:", listOf("Continuous ratio variables", "Categorical (nominal / ordinal) variables", "Normalized means", "Qualitative quotes"), 1),
            Triple("Evidence-Based Practice (EBP) in nursing integrates best research evidence with clinical expertise and:", listOf("Hospital budget constraints", "Patient values, preferences, and clinical context", "Nurse's personal convenience", "Traditional habits"), 1),
            Triple("The highest level (Level 1) of evidence in the Evidence-Based Practice hierarchy is provided by:", listOf("Expert opinion reports", "Systematic Reviews and Meta-analyses of multiple Randomized Controlled Trials (RCTs)", "Single descriptive study", "Case reports"), 1)
        )
        addSubjectQs(6, "Introduction to Nursing Research", "KMU RES-664 / Polit & Beck Nursing Research", res)

        // Add 100 extra questions for EACH subject in Semester 6 (400 extra questions)
        questions.addAll(KpSemester6PlusQuestionBank.getQuestions(idCounter))

        return questions
    }
}
