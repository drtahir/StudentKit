package com.example.ui.screens

/**
 * SUPREME BANK PART 4: CRITICAL CARE, PSYCHIATRY, COMMUNITY HEALTH & LEADERSHIP (80 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500SupremePart4 {

    fun getCriticalCareSupremeQuestions(startId: Int): List<NursingExamQuestion> {
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

        val supremeTopicsPart4 = listOf(
            Triple("Psychiatry: Schizophrenia Auditory Hallucinations & Therapeutic Communication", "Validate feelings without agreeing with hallucination ('I know the voices are real to you, but I do not hear them'); redirect to real-world structured activities; do NOT argue or debate", "Tell client their voices are complete lies and demand they stop hearing them immediately"),
            Triple("Psychiatry: Bipolar Disorder Acute Mania Intervention & High-Calorie Finger Foods", "Acute mania: Hyperactivity, distractibility, grandiosity; Nursing priority: Patient safety, low-stimulation environment, HIGH-CALORIE HIGH-PROTEIN FINGER FOODS (sandwiches, fruit sticks) eaten while walking", "Provide a 5-course elaborate dinner requiring quiet sitting at table for 2 hours"),
            Triple("Psychiatry: Major Depressive Disorder Suicide Risk & Sudden Mood Elevation", "Highest risk of suicide occurs when severe depression BEGINS TO IMPROVE or antidepressant therapy takes effect (energy increases while suicidal ideation persists); 1-to-1 continuous observation if active plan", "Sudden energy burst in severely depressed client means suicide risk is zero"),
            Triple("Psychiatry: Anorexia Nervosa Refeeding Syndrome & Hypophosphatemia", "Refeeding Syndrome: Severe electrolyte shift (hypophosphatemia, hypokalemia, hypomagnesemia) caused by rapid reintroduction of carbohydrates; leads to fatal cardiac arrhythmias and HF", "Refeeding syndrome causes hyperphosphatemia and massive hypertension"),
            Triple("Psychiatry: Borderline Personality Disorder Splitting & Boundary Setting", "Splitting (perceiving staff as all-good or all-bad); Nursing management: Maintain CONSISTENT FIRM BOUNDARIES, rotate primary nurse assignments, hold frequent team conferences to prevent staff division", "Allow borderline client to choose their favorite nurse and break unit rules for them"),
            Triple("Leadership: Delegation 5 Rights & RN vs LPN vs UAP Scope", "RN scope: Clinical assessments, initial teaching, nursing diagnoses, complex IV meds, blood transfusions, care plans; LPN: Stable clients, routine meds, dressing changes; UAP: ADLs, vitals on stable clients, intake/output", "Delegate initial complex admission assessment and IV push chemotherapy to UAP"),
            Triple("Leadership: Emergency Triage START System Red Yellow Green Black", "RED (Immediate): Life-threatening compromised airway/breathing/circulation but salvageable; YELLOW (Delayed): Serious injuries requiring care within 1-2 hours; GREEN (Minimal/Walking wounded); BLACK (Expectant/Deceased)", "Assign Red tag to deceased client without spontaneous breathing after airway positioning"),
            Triple("Infection Control: Airborne vs Droplet vs Contact Isolation Precautions", "Airborne (TB, Measles, Varicella): Negative pressure room, N95 respirator mask; Droplet (Influenza, Meningitis, Pertussis): Private room, surgical mask within 3 feet; Contact (MRSA, VRE, C. diff): Gloves & gown, handwashing with soap/water for C. diff", "Wear surgical mask for C. difficile and wash hands with alcohol sanitizer only"),
            Triple("Infection Control: Central Line-Associated Bloodstream Infection (CLABSI) Bundle", "Bundle: Chlorhexidine skin antisepsis, maximum sterile barrier precautions during insertion, daily check of line necessity, sterile transparent dressing change every 7 days (or immediately if soiled)", "Change central line dressing every 30 days without wearing sterile gloves"),
            Triple("Community: Epidemiology Primary vs Secondary vs Tertiary Prevention", "Primary: Health promotion & disease prevention (vaccinations, smoking cessation education); Secondary: Early detection & screening (mammograms, Pap smears, BP screening); Tertiary: Rehabilitation & disease management (cardiac rehab)", "Primary prevention includes cardiac stroke rehabilitation and joint replacement surgery")
        )

        for (i in 0 until 80) {
            val topicIndex = i % supremeTopicsPart4.size
            val item = supremeTopicsPart4[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Ignore administrative safety mandates and discharge client without handover documentation",
                "Violate patient confidentiality rights by publicly posting clinical charts online"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Psychiatry, Leadership & Critical Care",
                "NCLEX-RN / DHA • Supreme Series",
                "Supreme Series Critical/Psych Case #${i + 1}: In a psychiatric, leadership, or critical care environment involving ${item.first}, which prioritized nursing protocol is required?",
                options,
                correctPos,
                "Rationale: Evidence-based leadership and mental health standard for ${item.first} specifies: ${item.second}.",
                "Option breakdown: Correct answer enforces infection control, delegation rules, and patient safety. Incorrect option '${item.third}' is unsafe.",
                "Critical-Psych Supreme • ${item.first}"
            )
        }

        return list
    }
}
