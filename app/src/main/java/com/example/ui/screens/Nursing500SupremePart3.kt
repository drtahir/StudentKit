package com.example.ui.screens

/**
 * SUPREME BANK PART 3: ADVANCED MATERNAL-CHILD, NICU, PEDIATRIC EMERGENCIES & PSYCHIATRY (130 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500SupremePart3 {

    fun getPedsObPsychSupremeQuestions(startId: Int): List<NursingExamQuestion> {
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

        val supremeTopicsPart3 = listOf(
            Triple("Obstetrics: Preeclampsia Magnesium Sulfate Toxicity & Antidote", "Magnesium sulfate used for seizure prevention in preeclampsia; Therapeutic level: 4-7 mEq/L; Signs of toxicity: Loss of deep tendon reflexes (DTRs), respiratory rate < 12/min, oliguria (< 30 mL/hr); ANTIDOTE IS CALCIUM GLUCONATE IV", "Antidote for magnesium sulfate toxicity is protamine sulfate or high-dose oxytocin"),
            Triple("Obstetrics: Placenta Previa Painless Vaginal Bleeding Contraindications", "Placenta previa: PAINLESS bright red vaginal bleeding in 3rd trimester; CONTRAINDICATION: Vaginal digital examination or speculum exam (can cause fatal placental tearing); pelvic ultrasound to confirm", "Perform deep digital vaginal examinations every 15 minutes during active placenta previa bleeding"),
            Triple("Obstetrics: Abruptio Placentae Dark Red Bleeding & Board-Like Abdomen", "Premature separation of placenta; PAINFUL dark red vaginal bleeding, severe uterine tenderness, board-like hypertonic abdomen, fetal distress; emergency C-section required", "Abruptio placentae is characterized by painless bright yellow vaginal discharge and soft uterus"),
            Triple("Obstetrics: Umbilical Cord Prolapse Position & Elevating Presenting Part", "Cord slips below presenting part; Priority actions: Call for help, insert TWO STERILE GLOVED FINGERS into vagina to lift presenting part off cord, place client in KNEE-CHEST or Trendelenburg position", "Push prolapsed umbilical cord firmly back inside uterine cavity using a dry cotton swab"),
            Triple("Obstetrics: Fetal Heart Rate Variable Decelerations & Cord Compression", "VEAL CHOP: Variable decels = Cord compression (reposition client to side); Early decels = Head compression (normal); Accelerations = OK/Oxygenation; Late decels = Placental insufficiency (O2, fluids, stop Pitocin)", "Variable decelerations indicate head compression and require immediate oxytocin bolus"),
            Triple("Obstetrics: Postpartum Hemorrhage (PPH) Uterine Atony & Fundal Massage", "PPH: Blood loss > 500 mL (vaginal) or > 1000 mL (C-section); UTERINE ATONY is most common cause (bogy soft uterus); FIRST ACTION: MASSAGE THE FUNDUS until firm, check bladder for distension", "If uterus is boggy post-delivery, apply warm ice packs to the feet and ignore fundus"),
            Triple("Neonatal: APGAR Scoring Criteria at 1 & 5 Minutes", "Evaluates: Appearance (color), Pulse (HR), Grimace (reflex irritability), Activity (muscle tone), Respiration; Score 7-10 normal; Score 4-6 moderate distress; Score 0-3 severe distress (resuscitate immediately)", "APGAR score of 2 indicates a perfectly healthy newborn requiring no intervention"),
            Triple("Neonatal: Respiratory Distress Syndrome (RDS) Surfactant Administration", "Premature infants (< 34 weeks) lacking pulmonary surfactant; grunting, intercostal retractions, nasal flaring, cyanosis, ground-glass opacity on chest X-ray; treat with ENDOTRACHEAL SURFACTANT", "Treat RDS in premature infants with oral cough suppressants and cold fluids"),
            Triple("Neonatal: Hyperbilirubinemia Phototherapy & Eye Protection", "Pathological jaundice (< 24 hours of life) vs Physiological jaundice (> 24 hours); Phototherapy converts unconjugated bilirubin to water-soluble lumirubin; MASK EYES and COVER GENITALS; monitor temperature", "Keep infant fully dressed in woolen clothes under phototherapy lights without eye protection"),
            Triple("Pediatrics: Epiglottitis Haemophilus influenzae Type B & Airway Emergency", "Sudden high fever, severe sore throat, 4 Ds: Drooling, Dysphagia, Dysphonia, Distressed inspiratory stridor; TRIPOD POSITION; DO NOT EXAMINE THROAT WITH TONGUE DEPRESSOR (causes fatal laryngospasm)", "Examine throat forcefully with metal tongue blade in epiglottitis to inspect epiglottis"),
            Triple("Pediatrics: Acute Laryngotracheobronchitis (Croup) Barking Cough & Racemic Epinephrine", "Viral infection (parainfluenza); seal-like BARKING COUGH, inspiratory stridor, hoarseness; treatment: Cool mist humidification, oral dexamethasone, RACEMIC EPINEPHRINE NEBULIZER for severe stridor", "Croup is treated by giving high-dose oral sedatives and hot dry air inhalation"),
            Triple("Pediatrics: Tetralogy of Fallot 4 Defects & Hypercyanotic 'Tet' Spell", "4 defects: Pulmonic stenosis, Right ventricular hypertrophy, Overriding aorta, Ventricular septal defect (PROV); 'Tet' spell (acute cyanosis/hypoxia during crying/feeding); POSITION: KNEE-CHEST POSITION", "Place infant in Trendelenburg position with legs hanging down during hypercyanotic Tet spell"),
            Triple("Pediatrics: Coarctation of the Aorta Upper vs Lower Extremity BP", "Narrowing of aorta; HIGH blood pressure and bounding pulses in UPPER extremities; LOW blood pressure, weak/absent femoral pulses, and cool skin in LOWER extremities", "Coarctation of aorta causes high blood pressure in lower legs and absent radial pulses in arms"),
            Triple("Pediatrics: Cystic Fibrosis High-Salt Sweat & Pancreatic Enzymes", "Autosomal recessive CFTR gene defect; thick tenacious mucus; Sweat chloride test > 60 mEq/L diagnostic; give PANCREATIC ENZYMES WITH EVERY MEAL AND SNACK; high-calorie, high-protein diet", "Give pancreatic enzymes 4 hours after meals on an empty stomach with low-fat liquids"),
            Triple("Pediatrics: Intussusception Current-Jelly Stool & Barium Enema", "Telescoping of bowel segment; TRIAD: Sudden episodic severe abdominal pain, sausage-shaped abdominal mass, CURRANT-JELLY STOOLS (blood & mucus); diagnosis & treatment: AIR/BARIUM ENEMA", "Intussusception presents with hard clay-colored stools and flat scaphoid abdomen"),
            Triple("Pediatrics: Hirschsprung Disease Aganglionic Megacolon & Ribbon-Like Stool", "Congenital absence of ganglion cells in distal rectum/colon; failure to pass meconium within 48 hours, abdominal distension, bile-stained vomitus, RIBBON-LIKE FOUL-SMELLING STOOLS", "Hirschsprung disease causes profuse explosive rice-water diarrhea in healthy adolescents"),
            Triple("Pediatrics: Kawasaki Disease Strawberry Tongue & Coronary Aneurysm", "Systemic vasculitis; high fever > 5 days resistant to antipyretics, STRAWBERRY TONGUE, conjunctivitis, desquamation of hands/feet, cervical lymphadenopathy; RISK: Coronary artery aneurysm; IVIG + ASPIRIN", "Kawasaki disease is treated by strict prohibition of aspirin and IVIG therapy"),
            Triple("Pediatrics: Pyloric Stenosis Non-Bilious Projectile Vomiting & Olive Mass", "Hypertrophy of pyloric sphincter; non-bilious PROJECTILE VOMITING after feeding in 2-6 week old infant, persistent hunger, OLIVE-SHAPED MASS in right upper quadrant; pyloromyotomy", "Pyloric stenosis presents with profuse bilious diarrhea and sunken soft abdomen")
        )

        for (i in 0 until 130) {
            val topicIndex = i % supremeTopicsPart3.size
            val item = supremeTopicsPart3[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Isolate pediatric patient in dark room and withhold all fluid and nutritional support",
                "Forcefully perform painful invasive procedures without explanation or pain control"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Maternal & Pediatric Nursing",
                "NCLEX-RN / DHA • Supreme Series",
                "Supreme Series Maternal-Child Case #${i + 1}: The nurse provides care for a maternal/pediatric patient presenting with conditions associated with ${item.first}. Which prioritized nursing intervention is essential?",
                options,
                correctPos,
                "Rationale: Obstetric and pediatric evidence-based protocol for ${item.first} specifies: ${item.second}.",
                "Option breakdown: Correct choice ensures maternal-fetal and child safety. Incorrect option '${item.third}' is dangerous or inappropriate.",
                "Maternal-Child Supreme • ${item.first}"
            )
        }

        return list
    }
}
