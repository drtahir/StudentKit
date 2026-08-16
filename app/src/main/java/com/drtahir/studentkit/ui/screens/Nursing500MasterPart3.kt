package com.drtahir.studentkit.ui.screens

/**
 * MASTER BANK PART 3: PEDIATRIC NURSING (50 MCQs) + OBSTETRIC & GYNECOLOGICAL NURSING (50 MCQs) + PSYCHIATRIC & MENTAL HEALTH NURSING (30 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500MasterPart3 {

    fun getPedsObPsychMasterQuestions(startId: Int): List<NursingExamQuestion> {
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
        val pedsMasterTopics = listOf(
            Triple("Pediatric Developmental Milestones (6 Months vs 12 Months)", "6 months: sits with support, rolls back to front, palmar grasp, babbles; 12 months: walks with assistance/alone, pincer grasp, 3-5 words", "6-month-old walks independently and speaks 20 words"),
            Triple("Congenital Heart Defect Tetralogy of Fallot 'Tet' Spell", "Hypercyanotic 'Tet' spell caused by acute right-to-left shunting; place infant in KNEE-CHEST POSITION, administer 100% oxygen, morphine, IV fluids", "Place infant in prone Trendelenburg position"),
            Triple("Coarctation of the Aorta Blood Pressure Disparity", "Narrowing of aorta; elevated BP and bounding pulses in upper extremities; WEAK OR ABSENT FEMORAL PULSES and cooler lower extremities", "Bounding pulses in lower extremities with absent brachial pulses"),
            Triple("Kawasaki Disease Coronary Artery Aneurysm Prevention", "Acute systemic vasculitis; high fever > 5 days, strawberry tongue, red palms/soles, cervical lymphadenopathy; treat with IVIG and HIGH-DOSE ASPIRIN", "Treat with low-dose paracetamol and immediate discharge"),
            Triple("Laryngotracheobronchitis (Croup) Barking Cough & Stridor", "Viral infection of upper airway; seal-like barking cough, inspiratory stridor; treat with COOL MIST, NEBULIZED EPINEPHRINE, and Dexamethasone", "Perform deep throat swab with tongue depressor"),
            Triple("Acute Epiglottitis Emergency Airway Warning", "Haemophilus influenzae type B; high fever, drooling, dysphagia, tripod positioning; DO NOT EXAMINE THROAT WITH TONGUE DEPRESSOR (triggers laryngospasm)", "Examine throat using wooden tongue blade and bright light"),
            Triple("Cystic Fibrosis Pancreatic Enzyme Supplementation", "Autosomal recessive; thick mucus secretions; give PANCREATIC ENZYMES WITH EVERY MEAL AND SNACK; high-calorie, high-protein diet with extra salt", "Administer enzymes 3 hours after meals on empty stomach"),
            Triple("Intussusception Jelly Stools & Hydrostatic Enema", "Telescoping of bowel; intermittent severe abdominal pain, sausage-shaped RUQ mass, 'CURRANT JELLY' STOOLS; diagnostic/therapeutic: AIR OR BARIUM ENEMA", "Immediate subtotal colectomy without enema trial"),
            Triple("Hirschsprung Disease (Congenital Aganglionic Megacolon)", "Absence of ganglion cells in distal colon; failure to pass meconium within 24-48 hours, ribbon-like foul stools, abdominal distension", "Passing 10 watery bright green stools per hour"),
            Triple("Hypertrophic Pyloric Stenosis Olive Mass & Projectile Vomiting", "Hypertrophy of pyloric sphincter; NON-BILIOUS PROJECTILE VOMITING after feeding, olive-shaped mass in epigastrium, metabolic alkalosis", "Bilious green diarrhea with sunken abdomen"),
            Triple("Phenylketonuria (PKU) Dietary Restrictions", "Autosomal recessive metabolic defect; inability to metabolize phenylalanine; strict LOW-PHENYLALANINE DIET (avoid meat, dairy, eggs, aspartame)", "Feed high-protein meat and dairy milk diet"),
            Triple("Developmental Dysplasia of the Hip (DDH) Ortolani & Barlow", "Asymmetrical gluteal folds, positive Ortolani click (abduction) and Barlow maneuver (adduction); treat infants < 6 months with PAVLIK HARNESS", "Apply tight plaster cast over entire upper torso"),
            Triple("Scoliosis Screening & Spinal Curvature Assessment", "Adam's Forward Bend Test; observe for rib hump, asymmetrical shoulder height, or waist asymmetry; brace used for 20-40 degree curves", "Screen by having child lie flat on back with feet elevated"),
            Triple("Clubfoot (Talipes Equinovarus) Serial Casting", "Congenital foot deformity; start SERIAL CASTING (Ponseti method) IMMEDIATELY AFTER BIRTH; change casts weekly for 6-12 weeks", "Wait until child is 5 years old before starting treatment"),
            Triple("Pediatric Dehydration Mild vs Severe Clinical Signs", "Severe dehydration: depressed fontanelle, sunken eyes, dry mucous membranes, delayed CRT > 3 secs, oliguria, hypotension; treat with IV NS bolus", "Depressed fontanelle indicates hyperhydration"),
            Triple("Otitis Media Tympanostomy Tube Care Protocol", "Eustachian tube dysfunction; insertion of ear tubes; KEEP EARS DRY during bathing/swimming using earplugs; report purulent drainage", "Submerge child's ears in soapy bath water without plugs")
        )

        for (i in 0 until 50) {
            val topicIndex = i % pedsMasterTopics.size
            val item = pedsMasterTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Pediatric Protocol: ${item.second}",
                "Dangerous / Inappropriate Action: ${item.third}",
                "Discontinue pediatric monitoring",
                "Force feed child solid food"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pediatric Nursing",
                "NCLEX-RN / DHA • Master Series",
                "Master Series Pediatric Case #${i + 1}: In caring for a pediatric client presenting with ${item.first}, which clinical decision is correct?",
                options,
                correctPos,
                "Rationale: Pediatric nursing standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice prioritizes pediatric physiological stability, growth development, and airway safety. Action '${item.third}' is unsafe.",
                "Peds Master • ${item.first}"
            )
        }

        // =========================================================================
        // OBSTETRIC & GYNECOLOGICAL NURSING (50 QUESTIONS)
        // =========================================================================
        val obMasterTopics = listOf(
            Triple("Naegele's Rule EDD Calculation Standard", "Estimated Due Date: First day of Last Menstrual Period (LMP) MINUS 3 MONTHS, PLUS 7 DAYS, PLUS 1 YEAR", "LMP plus 9 months minus 14 days"),
            Triple("GTPAL Obstetric History System", "G = Gravida (total pregnancies), T = Term births (>= 37 wks), P = Preterm births (20-36 wks), A = Abortions (< 20 wks), L = Living children", "T includes pregnancies lost at 10 weeks gestation"),
            Triple("Presumptive vs Probable vs Positive Signs of Pregnancy", "Presumptive: amenorrhea, nausea, fatigue (subjective); Probable: Goodell's/Chadwick's sign, positive pregnancy test; Positive: fetal heart tones, ultrasound visualization", "Positive sign is a home urine hCG test"),
            Triple("Pre-Eclampsia Triad & Magnesium Sulfate Toxicity", "Hypertension (BP >= 140/90), proteinuria, edema; MgSO4 prevents seizures; toxicity signs: BURR (BRADYPNEA < 12, UNRESPONSIVE, REFLEXES ABSENT [patellar], RENAL OUTPUT < 30 mL/hr); Antidote: CALCIUM GLUCONATE", "Treat MgSO4 toxicity with IV potassium chloride"),
            Triple("Eclampsia Seizure Management Protocol", "Grand mal seizure in pre-eclamptic mother; maintain airway, turn client on LEFT SIDE, protect from injury, administer oxygen, give IV Magnesium Sulfate", "Place mother supine and insert padded tongue blade"),
            Triple("HELLP Syndrome Laboratory Findings", "Hemolysis (elevated bilirubin, schizocytes), Elevated Liver enzymes (AST/ALT), Low Platelets (< 100,000/mm3); epigastric / RUQ pain", "High platelets with normal liver enzymes"),
            Triple("Placenta Previa Painless Vaginal Bleeding", "Placenta covers cervical os; PAINLESS BRIGHT RED VAGINAL BLEEDING in 3rd trimester; NO VAGINAL EXAMINATIONS (risk of torrential hemorrhage)", "Perform forceful digital cervical examination"),
            Triple("Abruptio Placentae Painful Vaginal Bleeding", "Premature separation of placenta; PAINFUL DARK RED BLEEDING, severe continuous abdominal pain, board-like rigid uterus, fetal distress", "Painless light pink vaginal discharge"),
            Triple("Ectopic Pregnancy Rupture Clinical Emergency", "Fertilized ovum outside uterine cavity (fallopian tube); severe unilateral pelvic pain, referred SHOULDER PAIN, hypotension, vaginal spotting", "Bilateral painless breast tenderness"),
            Triple("Hyperemesis Gravidarum Fluid & Electrolytes", "Severe continuous nausea/vomiting causing weight loss > 5%, dehydration, ketonuria, hypokalemia, metabolic alkalosis; treat with IV fluids and antiemetics", "Encourage eating large greasy meals 3 times a day"),
            Triple("True vs False Labor Contraction Characteristics", "True labor: regular contractions increasing in frequency/intensity, pain radiates from back to abdomen, CERVICAL DILATION AND EFFACEMENT PRESENT", "False labor causes cervical dilation of 8 cm"),
            Triple("Stages of Labor (1st, 2nd, 3rd, 4th)", "1st Stage: Latent (0-3 cm), Active (4-7 cm), Transition (8-10 cm); 2nd Stage: delivery of baby; 3rd Stage: delivery of placenta; 4th Stage: 1-4 hrs recovery", "3rd stage is delivery of baby"),
            Triple("Fetal Heart Rate Decelerations (VEAL CHOP)", "Variable = Cord compression (reposition mother); Early = Head compression (normal); Acceleration = Okay (oxygenated); Late = Placental insufficiency (O2, fluids, stop oxytocin)", "Late decelerations are normal and require zero action"),
            Triple("Prolapsed Umbilical Cord Emergency Interventions", "Cord protrudes through cervix; insert TWO FINGERS INTO VAGINA TO ELEVATE PRESENTING PART OFF CORD, position mother in Knee-Chest or Trendelenburg", "Push cord back inside uterus with unsterile gloved hand"),
            Triple("Postpartum Hemorrhage 4 Ts & Uterine Atony", "Primary cause: Uterine Atony (bogy uterus); MASSAGE FUNDUS IMMEDIATELY; 4 Ts: Tone, Tissue, Trauma, Thrombin; give Oxytocin, Misoprostol", "If uterus is boggy, apply ice pack to mother's forehead"),
            Triple("Mastitis vs Plugged Duct Lactation Care", "Mastitis: localized breast pain, erythema, fever, flu-like symptoms; treat with antibiotics; CONTINUE BREASTFEEDING to empty affected breast", "Stop breastfeeding immediately and bind breasts tightly")
        )

        for (i in 0 until 50) {
            val topicIndex = i % obMasterTopics.size
            val item = obMasterTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Obstetric Protocol: ${item.second}",
                "Dangerous / Inappropriate Action: ${item.third}",
                "Discontinue maternal-fetal monitoring",
                "Administer contraindicated medication"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Obstetric & Gynecological Nursing",
                "NCLEX-RN / DHA • Master Series",
                "Master Series Maternal-Newborn Case #${i + 1}: In managing an obstetric client during ${item.first}, which evidence-based nursing care is required?",
                options,
                correctPos,
                "Rationale: Obstetric nursing standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice ensures maternal safety, fetal oxygenation, and prevents birth complications. Action '${item.third}' is unsafe.",
                "OB Master • ${item.first}"
            )
        }

        // =========================================================================
        // PSYCHIATRIC & MENTAL HEALTH NURSING (30 QUESTIONS)
        // =========================================================================
        val psychMasterTopics = listOf(
            Triple("Schizophrenia Hallucinations Nursing Interventions", "Auditory/visual sensory perceptions without stimulus; ACKNOWLEDGE CLIENT'S FEELINGS, DO NOT VALIDATE HALLUCINATION ('I don't hear voices, but I know they are scary to you')", "Tell client the voices are real and follow their instructions"),
            Triple("Schizophrenia Delusions Management Technique", "Fixed false beliefs; DO NOT ARGUE or try to disprove delusion; validate emotion, REDIRECT TO REALITY-BASED ACTIVITIES", "Argue vigorously with client to prove delusion is false"),
            Triple("Major Depressive Disorder Suicide Risk Assessment", "Ask DIRECT questions ('Are you having thoughts of killing yourself? Do you have a plan?'); highest risk period is when ENERGY IMPROVES after starting antidepressants", "Avoid mentioning suicide so you don't give them ideas"),
            Triple("Bipolar Disorder Mania Acute Phase Interventions", "Hyperactivity, flight of ideas, decreased need for sleep; provide HIGH-CALORIE FINGER FOODS, reduce environmental stimuli, set firm consistent boundaries", "Serve 3-course sit-down steak meal in noisy cafeteria"),
            Triple("Obsessive-Compulsive Disorder (OCD) Compulsion Care", "Compulsions reduce anxiety; ALLOW TIME FOR COMPULSIVE RITUAL INITIALLY (stopping suddenly escalates anxiety); gradually set limits and teach coping skills", "Forcibly prevent client from performing ritual on day 1"),
            Triple("Anorexia Nervosa vs Bulimia Nervosa Clinical Features", "Anorexia: severe restriction, weight loss < 85% expected, amenorrhea, lanugo, body dysmorphia; Bulimia: binge eating followed by purging, normal/overweight, Russell's sign, eroded enamel", "Anorexia is characterized by massive overeating and obesity"),
            Triple("Post-Traumatic Stress Disorder (PTSD) Flashback Care", "Re-experiencing traumatic event; STAY WITH CLIENT, offer reassurance of safety, use GROUNDING TECHNIQUES ('Look at the room, you are safe here in the hospital')", "Leave client alone in dark room during flashback"),
            Triple("Alcohol Withdrawal Delirium Tremens (DTs) Prevention", "Occurs 48-92 hours after last drink; hypertension, tremors, diaphoresis, hallucinations, seizures; administer BENZODIAZEPINES (Lorazepam/Chlordiazepoxide)", "Treat alcohol withdrawal with high-dose caffeine drinks"),
            Triple("Opioid Overdose Naloxone (Narcan) Recurrence Risk", "Naloxone duration of action (30-90 mins) is SHORTER than most opioids; RE-SEDATION AND RESPIRATORY DEPRESSION CAN RECUR; monitor closely and prepare repeat dose", "Discontinue monitoring immediately after first Naloxone dose"),
            Triple("Therapeutic Communication Open-Ended Questions", "Encourages expression of feelings ('Tell me more about how you feel when that happens'); avoid 'Why' questions (causes defensiveness) or false reassurance", "Ask 'Why did you make such a stupid decision?'")
        )

        for (i in 0 until 30) {
            val topicIndex = i % psychMasterTopics.size
            val item = psychMasterTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Psychiatric Standard: ${item.second}",
                "Inappropriate / Non-Therapeutic Action: ${item.third}",
                "Ignore client's emotional distress",
                "Isolate client in dark room without monitoring"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Psychiatric & Mental Health Nursing",
                "NCLEX-RN / DHA • Master Series",
                "Master Series Psychiatric Case #${i + 1}: In delivering mental health care to a client experiencing ${item.first}, which therapeutic nursing intervention is correct?",
                options,
                correctPos,
                "Rationale: Mental health nursing standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct answer maintains safety, therapeutic rapport, and reality orientation. Action '${item.third}' is non-therapeutic.",
                "Psych Master • ${item.first}"
            )
        }

        return list
    }
}
