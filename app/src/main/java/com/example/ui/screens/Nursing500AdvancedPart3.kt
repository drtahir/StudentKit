package com.example.ui.screens

/**
 * ADVANCED BANK PART 3: PEDIATRIC NURSING (50 MCQs) + OBSTETRIC & GYNECOLOGICAL NURSING (50 MCQs) + PSYCHIATRIC NURSING (30 MCQs)
 * NCLEX-RN, DHA, HAAD, Prometric, PNC & International Competitive Exam Standard.
 */
object Nursing500AdvancedPart3 {

    fun getPediatricObstetricPsychAdvancedQuestions(startId: Int): List<NursingExamQuestion> {
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
        val pediatricTopics = listOf(
            Triple("Tetralogy of Fallot Tet Spell Management", "Knee-to-chest position immediately (increases systemic vascular resistance, reduces right-to-left shunt); administer oxygen, morphine, and IV fluids", "Place infant flat on back and hyperventilate with room air"),
            Triple("Tetralogy of Fallot 4 Structural Defects", "1. Pulmonary stenosis, 2. Right ventricular hypertrophy, 3. Overriding aorta, 4. Ventricular septal defect (VSD); presents with cyanosis and clubbing", "1. Coarctation, 2. ASD, 3. PDA, 4. Transposition"),
            Triple("Coarctation of the Aorta Blood Pressure Signs", "Higher blood pressure and bounding pulses in UPPER extremities; lower blood pressure, weak/absent femoral pulses, and cool LOWER extremities", "Equal blood pressure in all four extremities"),
            Triple("Kawasaki Disease Coronary Artery Aneurysm Risk", "Acute systemic vasculitis; clinical features: high fever > 5 days, strawberry tongue, red palms/soles, peeling skin; treat with IVIG and HIGH-DOSE ASPIRIN", "Treat Kawasaki disease with systemic oral corticosteroids"),
            Triple("Laryngotracheobronchitis (Croup) Barking Cough", "Viral infection causing subglottic edema; seal-like barking cough, inspiratory stridor; treat with cool mist, Nebulized Racemic Epinephrine, and Dexamethasone", "Inspect throat with tongue blade and perform emergency intubation"),
            Triple("Acute Epiglottitis Emergency Action Rule", "DO NOT EXAMINE THROAT OR USE TONGUE DEPRESSOR (can trigger fatal laryngospasm); keep child calm, keep emergency tracheostomy/intubation kit ready", "Use tongue blade to visualize epiglottis under bright light"),
            Triple("Cystic Fibrosis Pancreatic Enzyme Administration", "Administer pancreatic lipase enzymes WITH ALL MEALS AND SNACKS to promote absorption of fat and protein; high-calorie high-protein diet", "Give enzymes 2 hours after meals with high fat restriction"),
            Triple("Cystic Fibrosis Sweat Chloride Test", "Sweat chloride concentration > 60 mEq/L is diagnostic for cystic fibrosis; parent reports child tastes salty when kissed", "Normal sweat chloride is 150-200 mEq/L"),
            Triple("Hirschsprung Disease Ribbon-Like Stools", "Congenital aganglionic megacolon; failure to pass meconium within 24-48 hours, foul-smelling ribbon-like stools, abdominal distension", "Frequent large watery projectile diarrhea"),
            Triple("Intussusception Red Currant Jelly Stools", "Telescoping of bowel segment; classic triad: sudden severe abdominal pain, sausage-shaped abdominal mass, RED CURRANT JELLY STOOLS; treat with air/barium enema", "Bright yellow liquid diarrhea with severe fever"),
            Triple("Pyloric Stenosis Olive-Shaped Mass & Projectile Vomiting", "Hypertrophy of pyloric sphincter; non-bilious PROJECTILE VOMITING after feeding, olive-shaped mass in right upper quadrant, severe dehydration", "Bilious green diarrhea with flat abdomen"),
            Triple("Wilms' Tumor (Nephroblastoma) Abdominal Palpation Rule", "DO NOT PALPATE ABDOMEN (risk of rupturing tumor capsule and disseminating cancer cells); place prominent 'Do Not Palpate' sign above bed", "Palpate abdomen forcefully twice per shift"),
            Triple("Developmental Milestones: Infant Motor & Speech", "2 months: smiles; 4 months: head control; 6 months: rolls over; 9 months: pincer grasp & sits unsupported; 12 months: walks & says simple words", "2 months: walks unsupported; 12 months: rides tricycle"),
            Triple("Developmental Milestones: Toddler Play Style", "Parallel play (plays alongside peers without direct interaction); toilet training readiness occurs around 2-3 years (sphincter control)", "Cooperative competitive team sports play"),
            Triple("Pediatric Dehydration Severity Signs", "Sunken fontanelle, absence of tears when crying, dry mucous membranes, decreased wet diapers (< 6 in 24 hours), lethargy; give oral rehydration solution", "Bulging fontanelle with profuse tears"),
            Triple("Febrile Seizures Parent Education", "Benign generalized seizures caused by rapid rise in temperature; keep child safe, turn on side, do NOT insert objects in mouth, administer antipyretics", "Hold child tightly and insert metal spoon into mouth"),
            Triple("Ottis Media Prevention Guidelines", "Avoid propping bottles, avoid secondhand smoke exposure, administer pneumococcal and influenza vaccines, encourage breastfeeding", "Prop milk bottle in bed during sleep"),
            Triple("Congenital Hip Dysplasia Ortolani & Barlow Signs", "Asymmetric thigh skin folds, positive Ortolani sign (hip click upon abduction), positive Barlow test; treat with PAVLIK HARNESS", "Keep hip flexed and adducted in plaster cast"),
            Triple("Cleft Lip & Palate Post-Op Care (Logan Bow)", "Cleft lip repair: protect suture line using Logan bow or elbow restraints, position on back/side (NO PRONE); feed with specialized feeder", "Place infant prone on abdomen immediately post-op"),
            Triple("Pediatric Asthma Inhaler Spacer Device", "Use spacer device with metered-dose inhaler (MDI) to optimize drug delivery to lungs and prevent oral deposition/thrush", "Inhale MDI directly without spacer at high speed")
        )

        for (i in 0 until 50) {
            val topicIndex = i % pediatricTopics.size
            val item = pediatricTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Pediatric Clinical Protocol: ${item.second}",
                "Dangerous / Inappropriate Pediatric Action: ${item.third}",
                "Delay care and discharge infant home",
                "Omit vital signs and physical assessment"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pediatric Nursing",
                "NCLEX-RN / DHA • Advanced",
                "Advanced Pediatric Case #${i + 1}: In providing specialized pediatric nursing care involving ${item.first}, which clinical action is correct?",
                options,
                correctPos,
                "Rationale: Pediatric nursing practice standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice ensures pediatric physiological stability, growth development, and emergency safety. Action '${item.third}' is unsafe.",
                "Pediatric Advanced • ${item.first}"
            )
        }

        // =========================================================================
        // OBSTETRIC & GYNECOLOGICAL NURSING (50 QUESTIONS)
        // =========================================================================
        val obstetricTopics = listOf(
            Triple("Preeclampsia Severe Features & Magnesium Sulfate", "BP > 160/110, severe headache, visual changes, proteinuria; IV Magnesium Sulfate given for SEIZURE PROPHYLAXIS; Antidote is CALCIUM GLUCONATE", "Antidote for Magnesium toxicity is Naloxone"),
            Triple("Magnesium Sulfate Toxicity Monitoring", "Loss of deep tendon reflexes (patellar reflex) is FIRST sign of toxicity, followed by respiratory depression (RR < 12) and oliguria (< 30 mL/hr)", "Hyperreflexia and severe hypertension indicate toxicity"),
            Triple("HELLP Syndrome Diagnostic Triad", "H = Hemolysis, EL = Elevated Liver enzymes, LP = Low Platelets (< 100,000/mcL); client presents with RUQ or epigastric pain", "Hyperglycemia, Elevated Leukocytes, Low Protein"),
            Triple("Eclampsia Emergency Management", "Protect airway, turn client onto LEFT SIDE, administer oxygen via non-rebreather, deliver IV Magnesium Sulfate bolus, prepare for delivery", "Hold client flat on back and insert padded tongue blade"),
            Triple("Ectopic Pregnancy Rupture Signs", "Unilateral sharp pelvic pain, amenorrhea, vaginal bleeding, and REFERRED SHOULDER PAIN (due to phrenic nerve irritation from hemoperitoneum)", "Bilateral painless profuse watery discharge"),
            Triple("Placenta Previa vs Abruptio Placentae Pain", "Placenta Previa: PAINLESS bright red vaginal bleeding, soft non-tender uterus; Abruptio Placentae: PAINFUL dark red bleeding, board-like rigid painful uterus", "Placenta Previa causes severe board-like uterine rigidity"),
            Triple("Abruptio Placentae Emergency Nursing Interventions", "Monitor fetal heart rate, establish 2 large-bore IV lines, administer oxygen, prepare for immediate emergency C-section, crossmatch blood", "Perform digital vaginal examination to feel placenta"),
            Triple("Digital Vaginal Exam Contraindication Rule", "DO NOT perform digital vaginal examination in clients with unexplained third-trimester vaginal bleeding until Placenta Previa is ruled out via ultrasound", "Perform vaginal exam vigorously with gloved hand"),
            Triple("Premature Rupture of Membranes (PROM) Nitrazine & Ferning Test", "Nitrazine paper turns BLUE (pH > 6.5) in amniotic fluid; microscopic examination shows FERNING PATTERN; monitor for chorioamnionitis (fever, foul discharge)", "Nitrazine paper turns bright red in amniotic fluid"),
            Triple("Propsed Umbilical Cord Care (Trendelenburg & Manual Elevation)", "Call for help, elevate presenting fetal part OFF THE CORD with gloved hand continuously, place mother in Trendelenburg or knee-chest position", "Push umbilical cord back into uterus with unsterile fingers"),
            Triple("Fetal Heart Rate Variable Decelerations (VEAL CHOP)", "Variable decelerations = Cord compression; reposition client to LEFT SIDE, administer oxygen, increase IV fluids, stop Oxytocin", "Variable decelerations indicate head compression requiring no action"),
            Triple("Fetal Heart Rate Late Decelerations (VEAL CHOP)", "Late decelerations = Uteroplacental insufficiency; immediate interventions: turn to LEFT side, 10 L O2 NRB, IV fluid bolus, STOP OXYTOCIN", "Late decelerations are normal fetal sleeping patterns"),
            Triple("Fetal Heart Rate Early Decelerations (VEAL CHOP)", "Early decelerations = Head compression; benign normal pattern during labor contractions; continue monitoring, no emergency intervention required", "Early decelerations require immediate emergency C-section"),
            Triple("Postpartum Hemorrhage 4 Ts (Tone, Trauma, Tissue, Thrombin)", "Most common cause is Uterine Atony (Tone); FIRST ACTION: MASSAGE THE FUNDUS until firm; administer IV Oxytocin / Methergine", "Administer high-dose subcutaneous heparin"),
            Triple("Methylergonovine (Methergine) Contraindications", "Causes vasoconstriction; CONTRAINDICATED in hypertension or preeclampsia; check blood pressure BEFORE administration", "Give Methergine IV push during hypertensive crisis"),
            Triple("Carboprost (Hemabate) Contraindications", "Prostaglandin F2-alpha; CONTRAINDICATED in ASTHMA due to severe bronchospasm risk; side effect: severe diarrhea", "Give Hemabate to asthmatic client with severe bronchospasm"),
            Triple("Postpartum Thrombophlebitis & DVT Risk", "Early ambulation is primary prevention; inspect legs for swelling, warmth, and tenderness; do NOT massage affected leg", "Vigorously massage swollen tender calf muscle"),
            Triple("Mastitis Breastfeeding Education", "Infection of breast tissue; CONTINUE BREASTFEEDING frequently to drain breast, apply warm compresses, take prescribed antibiotics", "Stop breastfeeding immediately and bind breasts tightly"),
            Triple("Neonatal APGAR Scoring Standard (1 & 5 Minutes)", "A = Appearance (color), P = Pulse (> 100 = 2), G = Grimace (reflex), A = Activity (tone), R = Respiration; score 7-10 is normal", "Score of 2 indicates excellent vigorous neonate"),
            Triple("Neonatal Hypoglycemia Clinical Signs & Threshold", "Blood glucose < 40 mg/dL in full-term infant; signs: jitteriness, high-pitched cry, lethargy, poor feeding, hypothermia; feed immediately", "Blood glucose of 120 mg/dL requires immediate IV glucagon")
        )

        for (i in 0 until 50) {
            val topicIndex = i % obstetricTopics.size
            val item = obstetricTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Obstetric Clinical Protocol: ${item.second}",
                "Dangerous / Inappropriate Obstetric Action: ${item.third}",
                "Delay care and discharge pregnant woman home",
                "Perform unauthorized digital vaginal exam"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Obstetric and Gynecological Nursing",
                "NCLEX-RN / DHA • Advanced",
                "Advanced Maternal & Gynecological Case #${i + 1}: In providing evidence-based obstetric nursing care involving ${item.first}, which clinical management step is required?",
                options,
                correctPos,
                "Rationale: Obstetric nursing practice standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice protects maternal-fetal safety, prevents hemorrhage/seizures, and maintains physiological stability. Action '${item.third}' is unsafe.",
                "Obstetrics Advanced • ${item.first}"
            )
        }

        // =========================================================================
        // PSYCHIATRIC & MENTAL HEALTH NURSING (30 QUESTIONS)
        // =========================================================================
        val psychTopics = listOf(
            Triple("Schizophrenia Command Hallucinations Priority", "Ask client directly: 'What are the voices telling you to do?'; assess for risk of violence or self-harm; establish safety precautions", "Tell client that voices are real and obey them"),
            Triple("Schizophrenia Delusions Communication Technique", "Acknowledge client's feelings without validating false belief; state reality calmly: 'I understand you believe the camera is watching, but I do not see it'", "Argue with client and prove their delusion is fake"),
            Triple("Bipolar Disorder Mania Acute Phase Interventions", "High-calorie, high-protein finger foods (chicken tenders, sandwiches) that client can eat on the move; low-stimulation quiet environment", "Serve 5-course sit-down dinner in noisy dining hall"),
            Triple("Major Depressive Disorder Suicide Assessment", "Ask DIRECT questions: 'Are you having thoughts of killing yourself? Do you have a plan? Do you have the means?'; institute 1-on-1 constant observation", "Avoid asking about suicide to prevent giving ideas"),
            Triple("Anxiety Disorder Panic Attack Nursing Care", "Stay with client, speak in simple short sentences, maintain calm quiet environment, guide slow deep breathing exercises", "Leave client alone in dark room and shout instructions"),
            Triple("Obsessive-Compulsive Disorder (OCD) Rituals Management", "Allow time for ritual initially while setting reasonable limits; assist client to learn alternative coping mechanisms; do NOT interrupt ritual abruptly early in treatment", "Forcibly stop client from performing ritual immediately"),
            Triple("Anorexia Nervosa Mealtime Monitoring & Refeeding", "Monitor client during meals and for 1 hour AFTER meals (prevents purging); monitor for Refeeding Syndrome (hypophosphatemia)", "Allow client to eat alone in bathroom with privacy"),
            Triple("Alcohol Withdrawal Syndrome Delirium Tremens (DTs)", "DTs occur 48-96 hours after last drink; autonomic hyperactivity, severe confusion, hallucinations, seizures; treat with IV BENZODIAZEPINES (Lorazepam)", "Treat alcohol delirium tremens with caffeine pills"),
            Triple("Opioid Withdrawal Clinical Presentation", "Dilated pupils (mydriasis), lacrimation, rhinorrhea, piloerection (goosebumps), diarrhea, abdominal cramping; non-life-threatening (unlike alcohol withdrawal)", "Severe seizures and life-threatening comatose collapse"),
            Triple("Borderline Personality Disorder Splitting Behavior", "Clients split staff into 'all good' or 'all bad'; maintain consistent team communication, strict boundaries, and uniform enforcement of unit rules", "Allow client to pick favorite nurse to break rules")
        )

        for (i in 0 until 30) {
            val topicIndex = i % psychTopics.size
            val item = psychTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Therapeutic Psychiatric Standard: ${item.second}",
                "Inappropriate / Non-Therapeutic Action: ${item.third}",
                "Argue aggressively with client and restrict fluids",
                "Leave client unmonitored during acute psychosis"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Psychiatric/Mental Health Nursing",
                "NCLEX-RN / DHA • Advanced",
                "Advanced Psychiatric Case #${i + 1}: In managing a psychiatric mental health clinical scenario involving ${item.first}, which therapeutic nursing action is appropriate?",
                options,
                correctPos,
                "Rationale: Mental health nursing standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct answer maintains client safety, therapeutic boundaries, and evidence-based psychiatric interventions. Action '${item.third}' is unsafe.",
                "Psychiatry Advanced • ${item.first}"
            )
        }

        return list
    }
}
