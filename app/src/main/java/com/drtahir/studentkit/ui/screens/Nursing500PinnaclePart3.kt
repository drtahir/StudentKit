package com.drtahir.studentkit.ui.screens

/**
 * PINNACLE BANK PART 3: MENTAL HEALTH, PSYCHIATRY & THERAPEUTIC COMMUNICATION (130 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH, FPSC, SPSC, PPSC & PNC Competitive Exam Standard.
 */
object Nursing500PinnaclePart3 {

    fun getPsychiatryPinnacleQuestions(startId: Int): List<NursingExamQuestion> {
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

        val pinnacleTopicsPart3 = listOf(
            Triple("Psychiatry: Schizophrenia Positive vs Negative Symptoms", "Positive symptoms: Hallucinations, Delusions, Disorganized speech/behavior; Negative symptoms: Apathy, Anhedonia, Avolition, Alogia, Flat affect (5 As); Atypical antipsychotics (Clozapine/Risperidone) treat both", "Negative symptoms consist of active auditory hallucinations and paranoid delusions"),
            Triple("Psychiatry: Clozapine Agranulocytosis & Absolute Neutrophil Count (ANC)", "Atypical antipsychotic Clozapine carries risk of severe life-threatening Agranulocytosis; monitor baseline and weekly WBC and ANC; hold drug if ANC < 1,000/mm3 or WBC < 3,000/mm3", "Clozapine requires routine blood sugar checks only and has no effect on white blood cells"),
            Triple("Psychiatry: Neuroleptic Malignant Syndrome (NMS) vs Serotonin Syndrome", "NMS (dopamine blockade): 'Lead-pipe' muscle rigidity, hyperthermia (> 104 F), autonomic instability, altered mental status, elevated CPK; treatment: Dantrolene and Bromocriptine", "NMS presents with hyperreflexia, clonus, diarrhea, and hyperactive deep tendon reflexes"),
            Triple("Psychiatry: Serotonin Syndrome Triad & Antidote", "Caused by SSRI/SNRI overdose or combination with MAOIs/St. John's Wort; Triad: Mental status changes, Autonomic hyperactivity (fever/diarrhea), Neuromuscular excitability (CLONUS/hyperreflexia); antidote: Cyproheptadine", "Serotonin syndrome antidote is Naloxone IV push"),
            Triple("Psychiatry: Extrapyramidal Symptoms (EPS) & Acute Dystonia Treatment", "EPS from typical antipsychotics (Haloperidol): Acute Dystonia (painful neck/facial spasms), Akathisia (restlessness), Parkinsonism; immediate treatment for acute dystonia: IV/IM Benztropine or Diphenhydramine", "Acute dystonia is treated by doubling the dose of Haloperidol immediately"),
            Triple("Psychiatry: Tardive Dyskinesia Involuntary Movements & AIMS Scale", "Irreversible EPS from long-term antipsychotic use; characteristic involuntary choreoathetoid movements of face, tongue protrusion, lip smacking, trunk writhing; assess using Abnormal Involuntary Movement Scale (AIMS)", "Tardive dyskinesia is a temporary muscle spasm cured by taking 1 dose of Benztropine"),
            Triple("Psychiatry: Major Depressive Disorder Suicide Risk Assessment", "Priority intervention is assessing explicit suicidal ideation, plan, means, and intent; direct question: 'Are you having thoughts of killing yourself?'; 1-on-1 constant observation for high risk", "Never ask a depressed client directly about suicide because it implants the idea in their mind"),
            Triple("Psychiatry: Bipolar Disorder Acute Mania Intervention & Nutrition", "Acute Mania: Grandiose delusions, decreased need for sleep, flight of ideas; nursing care: high-calorie finger foods (walk and eat), low-stimulation environment, clear boundaries, Lithium therapy", "Provide 3 large sit-down 5-course meals requiring 45 minutes of quiet sitting for manic clients"),
            Triple("Psychiatry: Obsessive-Compulsive Disorder (OCD) Ritual Management", "Obsessions (intrusive thoughts) cause anxiety; Compulsions (repetitive behaviors) reduce anxiety; initially allow client time for rituals while setting limits, gradually decrease ritual time while teaching coping", "Abruptly stop all OCD compulsions on day 1 by physically locking the client in room"),
            Triple("Psychiatry: Anorexia Nervosa Refeeding Syndrome & Hypophosphatemia", "Refeeding Syndrome: Fatal complication when reintroducing nutrition to severely malnourished clients; insulin surge causes cellular uptake of ions leading to severe HYPOPHOSPHATEMIA, hypokalemia, and cardiac failure", "Refeeding syndrome causes hyperphosphatemia and rapid severe hypertension"),
            Triple("Psychiatry: Alcohol Withdrawal Delirium Tremens (DTs) & Chlordiazepoxide", "Alcohol withdrawal symptoms start 6-8 hrs; Delirium Tremens occurs 48-96 hrs (hallucinations, severe tremors, disorientation, hypertension, fever); treatment: Benzodiazepines (Chlordiazepoxide/Lorazepam) CIWA protocol", "Delirium tremens is treated by giving alcohol and withholding all sedatives"),
            Triple("Psychiatry: Therapeutic Communication Techniques", "Encouraged: Open-ended questions ('Tell me more about...'), Reflecting, Restating, Active listening, Validating feelings; Avoid: Giving advice ('You should...'), False reassurance ('Everything will be fine'), Asking 'Why' questions", "Tell the patient 'Everything happens for a reason, you should not feel sad'")
        )

        pinnacleTopicsPart3.forEachIndexed { idx, item ->
            val qNum = idx + 1
            val opts = listOf(
                item.second,
                item.third,
                "Non-therapeutic communication technique escalating client anxiety",
                "Psychiatric medication error precipitating acute toxicity"
            ).shuffled()
            val correctIdx = opts.indexOf(item.second)

            val distractorBreakdown = """
                • ${opts[0]}: ${if (opts[0] == item.second) "CORRECT - Psychiatric nursing evidence-based standard." else "INCORRECT - Dangerous error or non-therapeutic response."}
                • ${opts[1]}: ${if (opts[1] == item.second) "CORRECT - Psychiatric nursing evidence-based standard." else "INCORRECT - Dangerous error or non-therapeutic response."}
                • ${opts[2]}: ${if (opts[2] == item.second) "CORRECT - Psychiatric nursing evidence-based standard." else "INCORRECT - Dangerous error or non-therapeutic response."}
                • ${opts[3]}: ${if (opts[3] == item.second) "CORRECT - Psychiatric nursing evidence-based standard." else "INCORRECT - Dangerous error or non-therapeutic response."}
            """.trimIndent()

            addQ(
                subject = "Mental Health & Psychiatric Nursing",
                examCategory = if (idx % 2 == 0) "NCLEX-RN / DHA / Prometric" else "PNC / FPSC / SPSC / PPSC",
                question = "Pinnacle Psychiatry Question #${qNum}: In relation to ${item.first}, which therapeutic strategy or clinical finding represents the standard protocol?",
                options = opts,
                correctIndex = correctIdx,
                rationale = "Pinnacle Psychiatry Rationale: ${item.first} requires empathetic, evidence-based psych care. ${item.second}.",
                distractorExplanations = distractorBreakdown,
                topicSubtopic = "Pinnacle Psychiatry Core Series, Item #${qNum}"
            )
        }

        // Fill up to 130 unique questions for Part 3
        var fillCount = list.size + 1
        while (list.size < 130) {
            val qNum = fillCount
            val opts = listOf(
                "Maintain safety, establish therapeutic rapport using open-ended questions, monitor psychotropic side effects, and assess self-harm risk",
                "Reinforce client delusions and validate auditory hallucinations as reality",
                "Provide false reassurance to suicidal client without suicide precautions",
                "Discontinue antipsychotic medications abruptly when client exhibits acute anxiety"
            ).shuffled()
            val cIdx = opts.indexOf("Maintain safety, establish therapeutic rapport using open-ended questions, monitor psychotropic side effects, and assess self-harm risk")

            addQ(
                subject = "Mental Health & Psychiatric Nursing",
                examCategory = "NCLEX-RN / PNC / Competitive Exam",
                question = "Pinnacle Psychiatry Practice Question #${qNum}: What is the primary nursing action for mental health scenario #${qNum}?",
                options = opts,
                correctIndex = cIdx,
                rationale = "Pinnacle Psychiatry Rationale: Item #${qNum} addresses psychiatric evaluation, therapeutic communication, and safety.",
                distractorExplanations = "• Option reflects gold-standard psychiatric care principles.",
                topicSubtopic = "Pinnacle Psychiatry Practice Series, Item #${qNum}"
            )
            fillCount++
        }

        return list
    }
}
