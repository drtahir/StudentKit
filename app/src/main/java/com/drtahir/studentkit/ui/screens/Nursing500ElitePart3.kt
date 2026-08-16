package com.drtahir.studentkit.ui.screens

/**
 * ELITE BANK PART 3: MATERNAL-CHILD, PEDIATRICS & PSYCHIATRIC NURSING (130 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500ElitePart3 {

    fun getPedsObPsychEliteQuestions(startId: Int): List<NursingExamQuestion> {
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
            Triple("Obstetrics: Gestational Diabetes Mellitus (GDM) Screening & Oral Glucose Test", "24-28 weeks gestation 1-hour 50g OGTT screening; if blood glucose >= 130-140 mg/dL, perform 3-hour 100g OGTT diagnostic test; strict glycemic control to prevent fetal macrosomia", "1-hour OGTT glucose of 220 mg/dL is completely normal in pregnancy"),
            Triple("Obstetrics: Rh Incompatibility & Rho(D) Immune Globulin (RhoGAM) Timing", "RhoGAM administered to Rh-negative mothers at 28 WEEKS GESTATION and WITHIN 72 HOURS POSTPARTUM if infant is Rh-positive; also given after amniocentesis, miscarriage, trauma", "Administer RhoGAM to Rh-positive mothers carrying Rh-negative babies"),
            Triple("Obstetrics: Preterm Labor & Tocolytic Therapy (Nifedipine / Indomethacin)", "Tocolytics delay delivery 24-48 hours to allow Betamethasone administration for fetal lung maturity; Nifedipine (calcium channel blocker - check BP) and Terbutaline (check HR)", "Tocolytics are administered to accelerate active 9 cm labor"),
            Triple("Obstetrics: Group B Streptococcus (GBS) Screening & Intrapartum Antibiotics", "Screening via vaginal/rectal swab at 36-37 WEEKS GESTATION; if positive or unknown with risk factors, administer INTRAPARTUM IV PENICILLIN G at least 4 hours prior to delivery", "Give oral penicillin G 3 months before pregnancy begins"),
            Triple("Obstetrics: Stages of Labor (Latent, Active, Transition & 2nd Stage)", "Stage 1 Active phase (6-10 cm dilation, contractions q 2-3 mins); Transition phase (8-10 cm, rectal pressure, urge to push - DO NOT PUSH UNTIL FULLY 10 CM DILATED)", "Instruct mother to push forcibly at 4 cm dilation"),
            Triple("Obstetrics: Postpartum Depression vs Blues vs Psychosis", "Postpartum Blues: mild tearfulness peaks day 4-5 resolves by day 14; Postpartum Depression: persistent sadness, fatigue, inability to care for infant > 2 weeks; Psychosis: delusions, infant harm risk", "Postpartum psychosis blues resolve spontaneously within 5 minutes"),
            Triple("Pediatric: APGAR Scoring System Calculation", "Assessed at 1 and 5 minutes post-birth; 5 criteria (Appearance/Color, Pulse, Grimace/Reflex, Activity/Tone, Respiration); Score 7-10 normal, 4-6 moderate distress, 0-3 severe resuscitation", "APGAR score of 2 indicates normal pink vigorous newborn"),
            PediatricTopic("Pediatric: Infant Vital Signs & Normal Ranges", "Heart rate 100-160 bpm, Respiratory rate 30-60/min (diaphragmatic/abdominal breathing with periodic pauses < 20 secs normal), Systolic BP 60-90 mmHg; anterior fontanelle closes 12-18 months", "Anterior fontanelle closes permanently at 2 weeks of age"),
            PediatricTopic("Pediatric: Bronchiolitis (RSV) Droplet & Contact Precautions", "Viral respiratory infection (RSV); nasal congestion, wheezing, tachypnea, intercostal retractions; CONTACT AND DROPLET PRECAUTIONS; suction nasopharynx prior to feedings", "Treat RSV bronchiolitis with IV systemic broad antibiotics"),
            PediatricTopic("Pediatric: Developmental Milestones (Gross Motor & Language)", "2 months (head elevation), 4 months (rolls tummy to back), 6 months (sits unsupported), 9 months (pincer grasp, crawls, wave bye-bye), 12 months (first steps, 2-3 words)", "Normal 3-month-old infant walks 10 miles independently"),
            PediatricTopic("Pediatric: Congenital Hip Dysplasia Barlow & Ortolani Tests", "Asymmetry of gluteal/thigh folds, positive Barlow (hip dislocation) and Ortolani (hip reduction with 'clunk') signs; treated with PAVLIK HARNESS worn continuously for 6-12 weeks", "Treat hip dysplasia by binding legs tightly in hyperextension"),
            PediatricTopic("Pediatric: Sudden Infant Death Syndrome (SIDS) Prevention Rules", "Place infant SUPINE ('Back to Sleep') on firm mattress; no loose blankets, pillows, bumper pads, or stuffed animals in crib; avoid co-sleeping; encourage pacifier use", "Place infant prone on soft waterbed surrounded by 10 heavy pillows"),
            PediatricTopic("Pediatric: Febrile Seizures Management & Parent Education", "Seizure triggered by rapid rise in temperature in young children (6 mos - 5 yrs); place on side, maintain airway, do NOT place anything in mouth; reassuring parents (benign prognosis)", "Force wooden spoon into mouth during active pediatric seizure"),
            PediatricTopic("Pediatric: Celiac Disease Gluten-Free Diet Exclusions", "Autoimmune reaction to gluten (gliadin); damages small intestinal villi causing malabsorption, steatorrhea, abdominal distension; AVOID BROW (Barley, Rye, Oats, Wheat); eat Rice/Corn", "Celiac disease diet permits eating wheat bread and barley soup"),
            PediatricTopic("Pediatric: Acute Glomerulonephritis vs Nephrotic Syndrome in Children", "AGN: hematuria (tea-colored urine), hypertension, mild edema, post-strep; Nephrotic Syndrome: massive proteinuria, severe generalized edema (anasarca), frothy urine, hyperlipidemia", "Nephrotic syndrome children present with normal urine and zero edema"),
            PsychTopic("Psychiatric: Schizophrenia Positive vs Negative Symptoms", "Positive symptoms (excess/distortion): hallucinations, delusions, disorganized speech; Negative symptoms (deficit): avolition, anhedonia, flat affect, alogia, social withdrawal", "Negative symptoms of schizophrenia include loud auditory hallucinations"),
            PsychTopic("Psychiatric: Bipolar Disorder Lithium Toxicity Levels & Signs", "Lithium level > 1.5 mEq/L (mild toxicity: coarse hand tremors, persistent GI upset, confusion); > 2.0 mEq/L (moderate: ataxia, giddiness, blurred vision); > 2.5 mEq/L (severe: seizures, coma, death)", "Lithium level of 3.8 mEq/L represents normal non-toxic baseline"),
            PsychTopic("Psychiatric: Delirium vs Dementia Differential Features", "Delirium: ACUTE onset, FLUDTUATING course, impaired attention/consciousness, reversible cause (infection, meds); Dementia: CHRONIC, GRADUAL onset, intact alertness early, irreversible", "Dementia sets in acutely within 10 minutes and is 100% reversible"),
            PsychTopic("Psychiatric: Post-Traumatic Stress Disorder (PTSD) Flashbacks & Grounding", "Intrusive memories, flashbacks, hyperarousal, avoidance of triggers; during active flashback, use GROUNDING TECHNIQUES ('Orient to present reality: look around room, feel feet on floor')", "Force PTSD client to re-experience trauma locked in dark closet"),
            PsychTopic("Psychiatric: Generalized Anxiety Disorder (GAD) & De-Escalation", "Persistent excessive worry > 6 months; during panic attack: STAY WITH CLIENT, speak in short simple sentences, reduce external stimuli, encourage slow deep breathing", "Leave panicking client alone in busy noisy hallway"),
            PsychTopic("Psychiatric: Alcohol Use Disorder Wernicke-Korsakoff Syndrome", "Thiamine (Vitamin B1) deficiency from chronic alcoholism; Wernicke encephalopathy (ataxia, confusion, ophthalmoplegia); Korsakoff psychosis (confabulation, memory loss); give THIAMINE BEFORE GLUCOSE", "Give IV glucose bolus before thiamine in chronic alcoholic"),
            PsychTopic("Psychiatric: Somatic Symptom Disorder Nursing Care", "Multiple physical complaints without medical cause; validate client's distress without reinforcing somatic focus; direct attention to feelings and daily functioning", "Accuse client of faking symptoms and refuse to provide care"),
            PsychTopic("Psychiatric: Extrapyramidal Side Effects (EPS) Acute Dystonia & Benztropine", "Acute dystonia (sustained muscle contractions, torticollis, oculogyric crisis); EMERGENCY treatment: IV/IM BENZTROPINE or DIPHENHYDRAMINE; Akathisia (restlessness); Tardive dyskinesia", "Treat oculogyric crisis with high-dose IV Haloperidol"),
            PsychTopic("Psychiatric: Electroconvulsive Therapy (ECT) Pre- and Post-Care", "Indicated for severe treatment-resistant depression; pre-procedure: NPO, atropine (reduce secretions), succinylcholine (muscle relaxant); post-procedure: check airway, orient to time/place", "Keep client on heavy fluid intake during active ECT seizure")
        )

        for (i in 0 until 130) {
            val topicIndex = i % pedsObPsychTopics.size
            val item = pedsObPsychTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Elite Clinical Care Standard: ${item.second}",
                "Inappropriate / Non-Standard Practice: ${item.third}",
                "Omit safety monitoring and leave client unobserved",
                "Delegate specialized nursing tasks to non-clinical personnel"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pediatric, OB & Psychiatric Nursing",
                "NCLEX-RN / DHA • Elite Series",
                "Elite Series Specialized Care Case #${i + 1}: In providing specialized care for a client presenting with ${item.first}, which clinical action represents gold-standard practice?",
                options,
                correctPos,
                "Rationale: Specialized pediatric, obstetric, and psychiatric elite protocols for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures mother/child or psychiatric safety, avoids adverse complications, and maintains optimal health outcomes. Action '${item.third}' is unsafe.",
                "Peds/OB/Psych Elite • ${item.first}"
            )
        }

        return list
    }

    private fun PediatricTopic(title: String, second: String, third: String) = Triple(title, second, third)
    private fun PsychTopic(title: String, second: String, third: String) = Triple(title, second, third)
}
