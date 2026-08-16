package com.drtahir.studentkit.ui.screens

/**
 * ULTRA BANK PART 3: ADVANCED MATERNAL-CHILD, OBSTETRICS, PEDIATRICS & PSYCHIATRY (130 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500UltraPart3 {

    fun getPedsObPsychUltraQuestions(startId: Int): List<NursingExamQuestion> {
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

        val ultraTopicsPart3 = listOf(
            Triple("Obstetrics: Placenta Previa vs Abruptio Placentae", "Placenta Previa: PAINLESS, bright red vaginal bleeding, soft non-tender uterus; NO VAGINAL EXAMS; Abruptio Placentae: PAINFUL, dark red bleeding, rigid board-like abdomen", "Placenta previa presents with severe abdominal rigidity and severe uterine tenderness"),
            Triple("Obstetrics: HELLP Syndrome Laboratory Findings", "HELLP: Hemolysis (schistocytes, elevated bilirubin), Elevated Liver enzymes (AST/ALT), Low Platelets (< 100,000/mm3); severe variant of preeclampsia; delivery is definitive cure", "HELLP syndrome presents with hyperthrombocytosis and low liver enzymes"),
            Triple("Obstetrics: Fetal Heart Rate Variable Decelerations & Cord Compression", "Variable decelerations (abrupt drop in FHR) caused by UMBILICAL CORD COMPRESSION; Nursing actions: Reposition mother to LATERAL position, give O2 by non-rebreather, stop Oxytocin", "Variable decelerations are caused by head compression and require no intervention"),
            Triple("Obstetrics: Shoulder Dystocia McRoberts Maneuver & Suprapubic Pressure", "Inability to deliver anterior shoulder; McRoberts Maneuver (sharp flexion of maternal thighs against abdomen) + SUPRAPUBIC PRESSURE; NEVER APPLY FUNDAL PRESSURE", "Apply strong fundal pressure to push the impacted shoulder down"),
            Triple("Obstetrics: Postpartum Hemorrhage 4 Ts & Uterine Atony", "4 Ts: Atony (most common), Trauma, Tissue (retained placenta), Thrombin; FIRST ACTION for uterine atony: MASSAGE THE FUNDUS until firm; give Oxytocin/Methylergonovine", "First action for uterine atony is immediate emergency hysterectomy without massaging fundus"),
            Triple("Pediatrics: Tetralogy of Fallot 4 Defects & 'Tet' Spell Management", "4 Defects: VSD, Pulmonary Stenosis, Overriding Aorta, RV Hypertrophy; 'Tet' Spell (hypercyanotic spell): Place child in KNEE-CHEST POSITION to increase systemic vascular resistance", "In 'Tet' spell, place child flat in Trendelenburg position"),
            Triple("Pediatrics: Epiglottitis 4 Ds & Airway Precautions", "Caused by Haemophilus influenzae type b; 4 Ds: Drooling, Dysphagia, Dyspnea, Distressed (tripod positioning); DO NOT INSPECT THROAT WITH TONGUE DEPRESSOR (triggers laryngospasm)", "Inspect epiglottitis throat vigorously with tongue blade and cotton swab"),
            Triple("Pediatrics: Kawasaki Disease Strawberry Tongue & Coronary Aneurysms", "Acute systemic vasculitis; Symptoms: High fever > 5 days, strawberry tongue, desquamation of hands/feet, cervical lymphadenopathy; Risk: CORONARY ARTERY ANEURYSMS; Treat: IVIG + Aspirin", "Kawasaki disease is treated with high-dose penicillin and systemic anticoagulants"),
            Triple("Pediatrics: Hirschsprung Disease Ribbon-Like Stools & Aganglionic Megacolon", "Congenital absence of ganglion cells in distal colon; failure to pass meconium within 24-48 hrs, abdominal distension, RIBBON-LIKE FOUL-SMELLING STOOLS; surgical resection", "Hirschsprung disease causes massive profuse watery diarrhea in newborns"),
            Triple("Pediatrics: Pyloric Stenosis Olive-Shaped Mass & Non-Bilious Vomiting", "Hypertrophy of pyloric sphincter; PROJECTILE NON-BILIOUS VOMITING after feedings, OLIVE-SHAPED MASS in right upper quadrant, visible peristaltic waves; surgical pyloromyotomy", "Pyloric stenosis presents with bilious green vomiting and severe diarrhea"),
            Triple("Pediatrics: Cystic Fibrosis Sweat Chloride Test & Pancreatic Enzymes", "Autosomal recessive; defect in CFTR gene; Sweat Chloride test > 60 mEq/L is diagnostic; high-calorie high-protein diet, administer PANCREATIC ENZYMES WITH ALL MEALS AND SNACKS", "Give pancreatic enzymes 3 hours after meals on an empty stomach"),
            Triple("Pediatrics: Croup (Laryngotracheobronchitis) Barking Cough & Racemic Epinephrine", "Viral airway inflammation; 'BARKING' SEAL-LIKE COUGH, inspiratory stridor, steeple sign on X-ray; Treat: Nebulized RACEMIC EPINEPHRINE and dexamethasone", "Croup is treated by immediate emergency tonsillectomy"),
            Triple("Pediatrics: Intussusception Currant Jelly Stools & Air Enema Reduction", "Telescoping of bowel segment; Sudden severe episodic abdominal pain, sausage-shaped RUQ mass, CURRANT JELLY STOOLS (blood + mucus); DIAGNOSTIC & THERAPEUTIC: AIR/BARIUM ENEMA", "Intussusception causes thin pencil-like dry stools; treated with laxatives"),
            Triple("Psychiatry: Schizophrenia Hallucinations vs Delusions Nursing Communication", "Hallucination = false sensory perception; Delusion = false fixed belief; Nursing action: Validate client's feelings, DO NOT REINFORCE OR AGREE WITH DELUSION/HALLUCINATION, present reality gently", "Argue intensely with client's delusions and confirm hearing voices"),
            Triple("Psychiatry: Major Depressive Disorder Suicide Risk & Sudden Energy Improvement", "Highest risk of suicide occurs when client's mood/energy SUDDENLY IMPROVES after starting antidepressants (has energy to execute plan); implement strict suicide precautions", "Sudden mood improvement on day 3 of antidepressants indicates complete permanent cure"),
            Triple("Psychiatry: Anorexia Nervosa Refeeding Syndrome & Phosphate Levels", "Refeeding Syndrome: Severe hypophosphatemia, hypokalemia, cardiac dysrhythmias caused by rapid re-feeding in severely malnourished client; monitor serum PHOSPHATE closely", "Refeeding syndrome causes hyperphosphatemia and massive hypertension"),
            Triple("Psychiatry: Neuroleptic Malignant Syndrome vs Serotonin Syndrome Features", "NMS: Muscle RIGIDITY ('lead pipe'), hyporeflexia, slow onset; Serotonin Syndrome: HYPERREFLEXIA, MYOCLONUS, tremor, rapid onset; both feature hyperthermia and autonomic instability", "NMS features extreme hyperreflexia and clonus, whereas Serotonin Syndrome causes lead-pipe rigidity"),
            Triple("Psychiatry: Bipolar I Mania Safety & High-Calorie Finger Foods", "Manic episode: Hyperactivity, distractibility, flight of ideas, decreased need for sleep; Priority: Client safety; DIET: Provide HIGH-CALORIE HIGH-PROTEIN FINGER FOODS (sandwiches, smoothies)", "Serve manic clients elaborate 5-course sit-down hot soups requiring 2 hours to eat"),
            Triple("Psychiatry: Borderline Personality Disorder Splitting & Consistent Boundaries", "Splitting = primitive defense mechanism viewing people as 'all good' or 'all bad'; Management: Maintain CONSISTENT FIRM BOUNDARIES and clear staff communication to prevent manipulation", "Allow client to choose favorite staff member and change rules daily"),
            Triple("Pediatrics: SIDS (Sudden Infant Death Syndrome) Back to Sleep Campaign", "Prevention: Place infant SUPINE ('BACK TO SLEEP') on firm mattress, avoid soft bedding/toys, avoid overheating, room-share without bed-sharing, encourage pacifier use", "Place infant prone on soft feather pillows with heavy quilts")
        )

        for (i in 0 until 130) {
            val topicIndex = i % ultraTopicsPart3.size
            val item = ultraTopicsPart3[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Isolate the pediatric/maternal client without nursing supervision or monitoring",
                "Delegate complex maternal-fetal triage decisions to non-clinical security staff"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Maternal-Child & Psychiatric Nursing",
                "NCLEX-RN / DHA • Ultra Series",
                "Ultra Series Maternal-Child/Psych Case #${i + 1}: The specialized nurse evaluates a client presenting with clinical indicators of ${item.first}. Which prioritized intervention accurately aligns with board standards?",
                options,
                correctPos,
                "Rationale: Specialized clinical standard for ${item.first} specifies: ${item.second}.",
                "Option breakdown: Correct choice ensures optimal maternal-fetal or pediatric safety and stabilization. Option '${item.third}' is inappropriate.",
                "Maternal-Child Ultra • ${item.first}"
            )
        }

        return list
    }
}
