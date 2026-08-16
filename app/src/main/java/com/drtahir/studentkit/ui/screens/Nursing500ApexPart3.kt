package com.drtahir.studentkit.ui.screens

/**
 * APEX BANK PART 3: ADVANCED MATERNAL-CHILD, NICU, PEDIATRIC EMERGENCIES & PSYCHIATRIC NURSING (130 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ApexPart3 {

    fun getPedsObPsychApexQuestions(startId: Int): List<NursingExamQuestion> {
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

        val apexTopicsPart3 = listOf(
            Triple("NICU Care: Retinopathy of Prematurity (ROP) Oxygen Saturation Targets", "ROP is caused by hyperoxia causing retinal neovascularization and detachment in preterm infants; MAINTAIN SPO2 TARGETS 90-95% (avoid prolonged SpO2 > 95%); routine ophthalmology exams", "Keep SpO2 at 100% continuously for 6 months in preterm infant"),
            Triple("NICU Care: Intraventricular Hemorrhage (IVH) Head Elevation & Minimal Handling", "Common in infants < 32 weeks; fragile germinal matrix capillaries bleed into ventricles; MINIMIZE HANDLING, KEEP HEAD MIDLINE ELEVATED 15-30 DEGREES, avoid rapid IV fluid pushes", "Place infant in Trendelenburg position and flush central line with rapid bolus"),
            Triple("NICU Care: Persistent Pulmonary Hypertension of the Newborn (PPHN) SNOOP", "Failure of pulmonary vascular resistance to drop at birth; right-to-left shunting across PDA/PFO, severe cyanosis refractory to 100% O2; treat with INHALED NITRIC OXIDE (iNO) and ECMO", "PPHN is treated with oral beta-blockers and fluid restriction"),
            Triple("NICU Care: Neonatal Hypoglycemia Glucose Protocol (< 40 mg/dL)", "Blood glucose < 40 mg/dL in full-term neonate; signs: jitteriness, hypotonia, lethargy, high-pitched cry, hypothermia; FEED IMMEDIATELY (breastmilk/formula); if symptomatic or < 25 mg/dL, IV D10W bolus", "Ignore glucose level of 10 mg/dL and keep neonate NPO"),
            Triple("Pediatric Emergencies: Pediatric Cardiac Arrest & CPR Depth / Compression Ratio", "Single rescuer (30:2 ratio), 2-rescuer (15:2 ratio); infant compressions with 2 thumbs-encircling hands technique; DEPTH: 1.5 INCHES (4 CM) IN INFANTS, 2 INCHES (5 CM) IN CHILDREN", "Compress infant chest 5 inches deep with two feet"),
            Triple("Pediatric Emergencies: Anaphylaxis Intramuscular Epinephrine Dose & Auto-Injector", "FIRST-LINE TREATMENT IS INTRAMUSCULAR EPINEPHRINE (1:1000) in anterolateral thigh (EpiPen Jr 0.15 mg for 7.5-25 kg, EpiPen 0.3 mg for > 25 kg); repeat every 5-15 mins if needed", "Give oral antihistamine syrup and delay epinephrine for 3 hours"),
            Triple("Pediatric Emergencies: Status Epilepticus IV Lorazepam / Buccal Midazolam", "Seizure lasting > 5 minutes or recurrent without recovery; FIRST-LINE: IV LORAZEPAM (0.1 mg/kg) or BUCCAL MIDAZOLAM / RECTAL DIAZEPAM if no IV access; second-line Fosphenytoin", "Give high-dose oral antacid tablets during active seizure"),
            Triple("Pediatric Emergencies: Pediatric Sepsis Bundle & 1-Hour Resuscitation", "Within 1 hour: measure lactate, obtain blood cultures, ADMINISTER EMPIRIC BROAD-SPECTRUM ANTIBIOTICS, and INFUSE 20 ML/KG ISOTONIC CRYSTALLOID BOLUSES for hypotension/hypoperfusion", "Give 2 mL total fluid bolus over 48 hours"),
            Triple("Obstetrics: Uterine Rupture Signs & Immediate Emergency C-Section", "Sudden catastrophic event; RISK FACTORS: prior C-section / uterine scar; SIGNS: sudden sharp abdominal pain, CESSATION OF CONTRACTIONS, LOSS OF FETAL STATION, severe fetal bradycardia", "Uterine rupture is treated by encouraging home water birth"),
            Triple("Obstetrics: Amniotic Fluid Embolism (AFE) Anaphylactoid Syndrome of Pregnancy", "Amniotic fluid enters maternal circulation; TRIAD: Sudden hypoxia, Hypotension, DIC / Severe coagulopathy; high maternal mortality; immediate CPR, intubation, blood products", "AFE presents with mild localized ankle edema"),
            Triple("Obstetrics: Cord Prolapse Positioning & Manual Elevation of Presenting Part", "Emergency; umbilical cord slipped below presenting part compressing fetal O2; PRIORITY: ELEVATE PRESENTING PART OFF CORD WITH GLOVED HAND, place mother in KNEE-CHEST or Trendelenburg", "Push cord back inside uterus with unsterile bare hands"),
            Triple("Obstetrics: Shoulder Dystocia McRoberts Maneuver & Suprapubic Pressure", "Inability to deliver anterior shoulder; 'TURTLE SIGN' (fetal head retracts against perineum); PERFORM MCROBERTS MANEUVER (hyperflex mother's legs to abdomen) AND SUPRAPUBIC PRESSURE", "Apply fundal pressure forcefully (CONTRAINDICATED - worsens impaction)"),
            Triple("Obstetrics: Postpartum Hemorrhage 4 Ts & Oxytocin First-Line", "4 Ts: Tone (uterine atony - 70%), Trauma, Tissue (retained placenta), Thrombin; MASSAGE FUNDUS FIRST; administer OXYTOCIN IV, Methergine (avoid in HTN), Hemabate (avoid in asthma)", "Give Methergine IV push to client with BP 210/120 mmHg"),
            Triple("Obstetrics: HELLP Syndrome Features & Immediate Delivery", "Severe variant of preeclampsia; HELLP: Hemolysis (schistocytes), Elevated Liver enzymes (ALT/AST), Low Platelets (< 100,000); epigastric / RUQ pain; CURE IS IMMEDIATE DELIVERY", "HELLP syndrome is treated with 6 months of outpatient rest"),
            Triple("Pediatric: Kawasaki Disease Coronary Artery Aneurysms & High-Dose Aspirin", "Systemic vasculitis in young children; CRASH and Burn: Conjunctivitis, Rash, Adenopathy, Strawberry tongue, Hands/feet edema, Fever > 5 days; TREAT WITH IVIG + HIGH-DOSE ASPIRIN", "Kawasaki disease is treated with high-dose penicillin alone"),
            PediatricTopic("Pediatric: Henoch-Schönlein Purpura (HSP) IgA Vasculitis & Palpable Purpura", "IgA vasculitis; TRIAD: Palpable purpura on buttocks/lower extremities, Arthralgia, Abdominal pain (intussusception risk), Hematuria (IgA nephropathy); supportive care", "HSP purpura is caused by severe hemophilia B"),
            PediatricTopic("Pediatric: Cystic Fibrosis Sweat Chloride Test & Pancreatic Replacement", "Autosomal recessive CFTR gene defect; SWEAT CHLORIDE TEST > 60 mEq/L IS DIAGNOSTIC; thick viscous mucus, recurrent lung infections, steatorrhea; pancreatic enzyme replacement with meals", "Sweat chloride test < 5 mEq/L diagnoses Cystic Fibrosis"),
            PediatricTopic("Pediatric: Intussusception Currant Jelly Stools & Air Enema Reduction", "Telescoping of bowel segment; TRIAD: Sudden intermittent colicky abdominal pain, Sausage-shaped abdominal mass, CURRANT JELLY STOOLS (blood/mucus); DIAGNOSED AND REDUCED BY AIR/BARIUM ENEMA", "Intussusception requires immediate total colectomy in 100% of cases"),
            PediatricTopic("Pediatric: Pyloric Stenosis Non-Bilious Projectile Vomiting & Olive Mass", "Hypertrophy of pyloric sphincter in 2-12 week old infants; NON-BILIOUS PROJECTILE VOMITING after feedings, OLIVE-SHAPED MASS in RUQ, peristaltic waves, hypochloremic metabolic alkalosis", "Pyloric stenosis presents with profuse bilious green diarrhea"),
            PsychTopic("Psychiatric: Clozapine Refractory Schizophrenia & Myocarditis Warning", "Second-generation antipsychotic; Black Box Warnings: AGRANULOCYTOSIS, MYOCARDITIS (chest pain, dyspnea, fever, elevated troponin - stop drug), severe constipation, seizures", "Clozapine myocarditis is cured by adding haloperidol"),
            PsychTopic("Psychiatric: Anorexia Nervosa Refeeding Syndrome & Hypophosphatemia", "Severe restriction of food intake; Refeeding Syndrome occurs upon reintroducing carbohydrates: surge in insulin drives phosphate into cells causing FATAL HYPOPHOSPHATEMIA, cardiac arrest", "Refeeding syndrome causes massive hyperphosphatemia"),
            PsychTopic("Psychiatric: Borderline Personality Disorder Splitting & Dialectical Behavior Therapy (DBT)", "Instability in relationships, self-image, affects; defense mechanism: SPLITTING (viewing people as all-good or all-bad); TREATMENT OF CHOICE IS DIALECTICAL BEHAVIOR THERAPY (DBT)", "DBT is contraindicated in personality disorders"),
            PsychTopic("Psychiatric: Serotonin Syndrome Hunter Criteria & Cyproheptadine Antidote", "Excess serotonin from SSRIs, MAOIs, Tramadol; SIGNS: Hyperreflexia, Clonus (spontaneous/inducible), Agitation, Diaphoresis, Tremor, Mydriasis; ANTIDOTE IS CYPROHEPTADINE (serotonin antagonist)", "Antidote for Serotonin Syndrome is continuous IV Prozac infusion"),
            PsychTopic("Psychiatric: Obsessive-Compulsive Disorder (OCD) Exposure and Response Prevention (ERP)", "Obsessions (intrusive thoughts) cause anxiety relieved by Compulsions (repetitive behaviors); DO NOT ABRUPTLY INTERRUPT RITUAL INITIALLY; treatment: EXPOSURE AND RESPONSE PREVENTION (ERP)", "Forcefully lock OCD client in closet to break compulsion on Day 1")
        )

        for (i in 0 until 130) {
            val topicIndex = i % apexTopicsPart3.size
            val item = apexTopicsPart3[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Apex Clinical Care Standard: ${item.second}",
                "Inappropriate / Non-Standard Practice: ${item.third}",
                "Omit safety monitoring and leave client unobserved",
                "Delegate specialized nursing tasks to non-clinical personnel"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pediatric, OB & Psychiatric Nursing",
                "NCLEX-RN / DHA • Apex Series",
                "Apex Series Specialized Care Case #${i + 1}: In providing high-acuity care for a client presenting with ${item.first}, which clinical intervention represents evidence-based gold-standard practice?",
                options,
                correctPos,
                "Rationale: Specialized pediatric, obstetric, and psychiatric apex protocols for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures mother/neonate or psychiatric safety, avoids adverse complications, and maintains optimal health outcomes. Action '${item.third}' is unsafe.",
                "Peds/OB/Psych Apex • ${item.first}"
            )
        }

        return list
    }

    private fun PediatricTopic(title: String, second: String, third: String) = Triple(title, second, third)
    private fun PsychTopic(title: String, second: String, third: String) = Triple(title, second, third)
}
