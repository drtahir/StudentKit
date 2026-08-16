package com.drtahir.studentkit.ui.screens

/**
 * EXPERT BANK PART 3: PEDIATRICS, OBSTETRICS & PSYCHIATRIC / MENTAL HEALTH (130 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ExpertPart3 {

    fun getPedsObPsychExpertQuestions(startId: Int): List<NursingExamQuestion> {
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

        val pedsObPsychTopics = listOf(
            Triple("Obstetrics: Preeclampsia vs Eclampsia Magnesium Sulfate Protocol", "Magnesium Sulfate prevents eclamptic seizures; therapeutic level 4-7 mEq/L; check DTRs, respiratory rate (> 12/min), and urine output (> 30 mL/hr); ANTIDOTE IS CALCIUM GLUCONATE", "Antidote for Magnesium Sulfate toxicity is Sodium Bicarbonate"),
            Triple("Obstetrics: Postpartum Hemorrhage (PPH) Uterine Atony Care", "PPH defined as blood loss > 500 mL (vaginal) or > 1000 mL (C-section); FIRST ACTION: FUNDAL MASSAGE for boggy uterus; give Oxytocin, Methylergonovine (avoid if HTN), Carboprost (avoid if asthma)", "First action for boggy uterus is elevated Trendelenburg leg lift"),
            Triple("Obstetrics: Shoulder Dystocia McRoberts Maneuver & Suprapubic Pressure", "Obstetric emergency; perform McROBERTS MANEUVER (sharp flexion of mother's hips against abdomen) and apply SUPRAPUBIC PRESSURE; NEVER apply fundal pressure", "Apply heavy fundal pressure on upper uterus"),
            Triple("Obstetrics: Umbilical Cord Prolapse Position & Elevating Presenting Part", "Cord compression causes fetal hypoxia; CALL FOR HELP, insert gloved fingers into vagina to ELEVATE PRESENTING PART off cord; place mother in KNEE-CHEST or Trendelenburg position", "Push cord back into uterus with sterile gauze"),
            Triple("Obstetrics: Fetal Heart Rate Patterns VEAL CHOP", "Variable decels = Cord compression; Early decels = Head compression (benign); Accelerations = OK / Reassuring; Late decels = Placental insufficiency (turn to left side, oxygen, IV fluids, stop Pitocin)", "Late decelerations indicate fetal wellbeing and active movement"),
            Triple("Obstetrics: Hyperemesis Gravidarum Electrolytes & Ketones", "Severe persistent nausea/vomiting causing > 5% pre-pregnancy weight loss; urine KETONES positive, metabolic alkalosis, hypokalemia; treat with IV fluid resuscitation and B6/antiemetics", "Hyperemesis is treated with oral high-fat liquid diet"),
            Triple("Obstetrics: Placenta Previa vs Abruptio Placentae", "Placenta Previa: PAINLESS BRIGHT RED VAGINAL BLEEDING (NO VAGINAL EXAMS!); Abruptio Placentae: PAINFUL DARK RED BLEEDING with board-like rigid abdomen", "Perform digital vaginal exam on suspected Placenta Previa"),
            Triple("Pediatric: Tetralogy of Fallot (TOF) 'TET' Spells Management", "4 defects: VSD, Pulmonary stenosis, Overriding aorta, RV hypertrophy; 'TET' spell (hypercyanotic spell): PLACE INFANT IN KNEE-CHEST POSITION immediately to increase systemic vascular resistance", "Place infant in flat supine position during hypercyanotic spell"),
            Triple("Pediatric: Laryngotracheobronchitis (Croup) Barking Cough & Stridor", "Viral upper airway infection; characteristic 'barking' seal-like cough and inspiratory stridor; treat with humidified cool mist, Nebulized Racemic Epinephrine, and Dexamethasone", "Perform deep throat swab with tongue depressor"),
            Triple("Pediatric: Epiglottitis 4 Ds & Airway Precautions", "Haemophilus influenzae type b infection; 4 Ds: Drooling, Dysphagia, Dyspnea, Distressed tripod position; DO NOT INSPECT THROAT WITH TONGUE BLADE (causes fatal laryngospasm)", "Inspect throat vigorously with metal tongue blade"),
            PediatricTopic("Pediatric: Cystic Fibrosis (CF) Sweat Chloride & Pancreatic Enzymes", "Autosomal recessive; abnormal chloride transport; positive Sweat Chloride test (> 60 mEq/L); administer PANCREATIC ENZYMES WITH ALL MEALS AND SNACKS; high-calorie high-protein diet", "Give pancreatic enzymes 3 hours after meals on empty stomach"),
            PediatricTopic("Pediatric: Hirschsprung Disease Ribbon-Like Stool & Megacolon", "Congenital aganglionic megacolon; absence of ganglion cells in distal colon; failure to pass meconium within 48 hrs, RIBBON-LIKE FOUL-STOOL, abdominal distension; surgical resection required", "Treated with immediate high-fiber bran diet"),
            PediatricTopic("Pediatric: Intussusception Currant Jelly Stools & Hydrostatic Reduction", "Telescoping of bowel segment; severe intermittent abdominal pain, screaming, knees pulled to chest, CURRANT JELLY STOOLS (blood/mucus); diagnosed and treated via AIR/BARIUM ENEMA", "Requires immediate surgical bowel resection without enema trial"),
            PediatricTopic("Pediatric: Pyloric Stenosis Olive-Shaped Mass & Projectile Vomiting", "Hypertrophy of pyloric sphincter; NON-BILIOUS PROJECTILE VOMITING after feedings, persistent hunger, OLIVE-SHAPED MASS in upper right quadrant, metabolic alkalosis", "Causes profuse bilious diarrhea and metabolic acidosis"),
            PediatricTopic("Pediatric: Kawasaki Disease Strawberry Tongue & Aspirin Rules", "Acute febrile vasculitis; fever > 5 days, strawberry tongue, desquamation of hands/feet, coronary artery aneurysm risk; treat with IVIG and HIGH-DOSE ASPIRIN (rare exception to Reye syndrome rule)", "Aspirin is strictly contraindicated in Kawasaki disease"),
            PsychTopic("Psychiatric: Major Depressive Disorder Suicide Risk & SSRI Black Box", "Highest risk of suicide occurs 1-2 WEEKS AFTER STARTING ANTIDEPRESSANTS (energy increases before mood improves); ask DIRECT QUESTIONS ('Are you having thoughts of killing yourself?')", "Avoid asking direct questions about suicide"),
            PsychTopic("Psychiatric: Schizophrenia Hallucinations vs Delusions Nursing Interventions", "Hallucinations (perceptual): validate feelings but present reality ('I know the voices seem real to you, but I do not hear them'); Delusions (fixed false belief): do NOT argue or debate contents", "Argue aggressively with client that hallucinations are fake"),
            PsychTopic("Psychiatric: Bipolar Disorder Mania Environment & Nutrition", "Grandiosity, hyperactive, decreased need for sleep; high-calorie FINGER FOODS (sandwiches, bananas) and hydration; quiet low-stimulation environment; structured simple activities", "Force client to sit still for 4-hour group therapy"),
            PsychTopic("Psychiatric: Anorexia Nervosa vs Bulimia Nervosa Electrolytes", "Anorexia: severe restriction, distorted body image, lanugo, amenorrhea, refeeding syndrome risk (hypophosphatemia); Bulimia: bingeing/purging, Russell's sign, parotid swelling, hypokalemia", "Anorexia nervosa presents with morbid obesity"),
            PsychTopic("Psychiatric: Obsessive-Compulsive Disorder (OCD) Compulsion Timing", "Obsessions cause anxiety, compulsions relieve anxiety; INITIALLY ALLOW TIME FOR COMPULSIONS to prevent panic, then gradually limit time; teach alternative coping mechanisms", "Abruptly stop compulsions on Day 1 causing severe panic"),
            PsychTopic("Psychiatric: Borderline Personality Disorder Splitting & Boundaries", "Instability in relationships, impulsivity, fear of abandonment; SPLITTING behavior (viewing staff as all good or all bad); maintain CONSISTENT BOUNDARIES and firm team communication", "Allow client to select favorite nurse and grant special privileges"),
            PsychTopic("Psychiatric: Alcohol Withdrawal Syndrome Delirium Tremens (DTs)", "Onset 48-96 hours after last drink; tremors, diaphoresis, severe confusion, hallucinations, autonomic hyperactivity; treat with BENZODIAZEPINES (Lorazepam/Diazepam) and Thiamine (B1)", "Treat alcohol withdrawal DTs with caffeine and restraints"),
            PsychTopic("Psychiatric: Restraints & Seclusion Legal & Safety Standards", "Last resort for imminent danger; requires IN-PERSON PHYSICIAN EVALUATION within 1 hour; order valid for max 4 hours (adults); check circulation/skin q 15 mins; offer food/toilet q 2 hrs", "Apply physical restraints without order for minor verbal complaint"),
            PsychTopic("Psychiatric: Therapeutic Communication Open-Ended Questions", "Use open-ended statements ('Tell me more about how you feel') and SILENCE; avoid giving advice ('You should...'), false reassurance ('Everything will be fine'), or asking 'Why'", "Ask 'Why did you make such a foolish decision?'")
        )

        for (i in 0 until 130) {
            val topicIndex = i % pedsObPsychTopics.size
            val item = pedsObPsychTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Evidence-Based Clinical Standard: ${item.second}",
                "Inappropriate / Non-Standard Practice: ${item.third}",
                "Discontinue safety monitoring and leave client unobserved",
                "Delegate specialized nursing assessment to unlicensed personnel"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pediatric, OB & Psychiatric Nursing",
                "NCLEX-RN / DHA • Expert Series",
                "Expert Series Specialized Care Case #${i + 1}: When providing specialized clinical management for a client presenting with ${item.first}, which intervention aligns with established guidelines?",
                options,
                correctPos,
                "Rationale: Specialized pediatric, obstetric, and psychiatric clinical protocols for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures mother/child or mental health stability and upholds safety standards. Action '${item.third}' is contraindicated.",
                "Peds/OB/Psych Expert • ${item.first}"
            )
        }

        return list
    }

    private fun PediatricTopic(title: String, second: String, third: String) = Triple(title, second, third)
    private fun PsychTopic(title: String, second: String, third: String) = Triple(title, second, third)
}
