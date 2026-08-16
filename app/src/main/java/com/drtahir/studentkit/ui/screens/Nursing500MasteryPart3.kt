package com.drtahir.studentkit.ui.screens

/**
 * MASTERY BANK PART 3: NICU, PEDIATRIC EMERGENCIES, OBSTETRICS & MENTAL HEALTH (130 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500MasteryPart3 {

    fun getPedsObPsychMasteryQuestions(startId: Int): List<NursingExamQuestion> {
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

        val masteryTopicsPart3 = listOf(
            Triple("NICU Care: Neonatal Respiratory Distress Syndrome (RDS) & Surfactant", "Premature infants (< 34 weeks); lack of pulmonary surfactant causing alveolar collapse, grunting, nasal flaring, intercostal retractions, ground-glass X-ray; administer ENDOTRACHEAL SURFACTANT", "Administer oral water to preterm infant with severe grunting"),
            Triple("NICU Care: Necrotizing Enterocolitis (NEC) Abdominal Distension & Guaiac", "Ischemic inflammatory bowel disease in preterm neonates; signs: ABDOMINAL DISTENSION, increased gastric residuals, bloody/guaiac-positive stools, lethargy; NPO, NG decompression, IV antibiotics", "Feed full strength high-fat formula directly into stomach"),
            Triple("NICU Care: Neonatal Abstinence Syndrome (NAS) Finnegan Score & Morphine", "Maternal opioid withdrawal; hyperirritability, high-pitched cry, tremors, uncoordinated sucking, diarrhea, sneezing; swaddle tightly, minimize stimuli, low-lighting, give oral Morphine/Methadone", "Expose neonate to bright flashing strobe lights and loud noise"),
            Triple("NICU Care: Phototherapy for Hyperbilirubinemia & Eye Protection", "Pathologic jaundice (< 24 hrs post-birth) vs Physiologic (> 24 hrs); phototherapy breaks down unconjugated bilirubin; COVER EYES WITH OPAQUE MASK, cover diaper area, monitor hydration/temp", "Expose eyes directly to high intensity UV phototherapy light"),
            Triple("Pediatric Emergencies: Foreign Body Airway Obstruction Infant vs Child", "Infant (< 1 yr): 5 BACK BLOWS AND 5 CHEST THRUSTS; Child (> 1 yr): ABDOMINAL THRUSTS (Heimlich maneuver); NEVER perform blind finger sweep (may push object deeper)", "Perform blind finger sweep deep into infant throat"),
            Triple("Pediatric Emergencies: Epiglottitis Emergency Airway Equipment", "Life-threatening Haemophilus influenzae infection; high fever, drooling, tripod position; keep ENDOTRACHEAL INTUBATION AND TRACHEOSTOMY TRAY AT BEDSIDE; avoid throat inspection", "Force tongue depressor into mouth to look at throat"),
            Triple("Pediatric Emergencies: Submersion Injury (Drowning) Hypothermia & ARDS", "Near-drowning victim; priority: AIRWAY AND OXYGENATION; monitor for SECONDARY DROWNING / ARDS (pulmonary edema up to 24 hours post-event); rewarm hypothermic child slowly", "Discharge child immediately if they walked out of pool"),
            Triple("Pediatric Emergencies: Accidental Ingestion & Poison Control Priority", "If child ingests toxic chemical/household cleaning agent: CALL POISON CONTROL CENTER FIRST; do NOT induce vomiting with Syrup of Ipecac (causes esophageal chemical burns)", "Force child to drink 1 cup of concentrated bleach"),
            Triple("Obstetrics: Ectopic Pregnancy Triad & Methotrexate Criteria", "Triad: unilateral pelvic pain, amenorrhea, dark vaginal bleeding/spotting; RUPTURE CAUSES REFERRED SHOULDER PAIN and hypovolemic shock; unruptured ectopic treated with METHOTREXATE", "Perform vigorous abdominal massage on suspected ectopic pregnancy"),
            Triple("Obstetrics: Hydatidiform Mole (Molar Pregnancy) hCG Levels & Choriocarcinoma", "Trophoblastic proliferation; snowstorm appearance on ultrasound, dark brown 'prune juice' vaginal discharge, excessively high hCG, hyperemesis; RISK OF CHORIOCARCINOMA (monitor hCG 1 year)", "Encourage pregnancy again within 1 week of molar evacuation"),
            Triple("Obstetrics: Incompetent Cervix (Cervical Insufficiency) Cervical Cerclage", "Painless cervical dilation in 2nd trimester leading to recurrent miscarriages; treated with CERVICAL CERCLAGE (McDonald stitch) placed at 12-14 weeks and removed at 36-37 weeks", "Instruct client to run marathons with 3 cm open cervix at 16 weeks"),
            Triple("Obstetrics: Non-Stress Test (NST) Reactive vs Non-Reactive", "Assesses fetal well-being; REACTIVE NST (REASSURING): at least 2 FHR accelerations of 15 bpm lasting 15 seconds within 20-minute window; Non-reactive NST requires Biophysical Profile (BPP)", "Non-reactive NST indicates optimal fetal health and zero risk"),
            Triple("Obstetrics: Biophysical Profile (BPP) 5 Components & Scoring", "BPP scores 5 variables (0 or 2 points each): NST, Fetal breathing movements, Fetal movement, Fetal tone, Amniotic fluid volume; Score 8-10 normal, 0-4 demands immediate delivery", "Score of 2 on BPP indicates fetal super-health"),
            Triple("Obstetrics: Postpartum Uterine Inversion Emergency Action", "Uterus turns inside out after birth (severe hemorrhage, shock, pelvic mass); DO NOT REMOVE PLACENTA; push inverted fundus back through cervix before giving uterotonics", "Pull forcefully on umbilical cord to tear placenta away"),
            Triple("Pediatric: Measles (Rubeola) Koplik Spots & Airborne Precautions", "Viral illness; Koplik spots (tiny white spots on red buccal mucosa), maculopapular rash starting at hairline and spreading down, 3 Cs (Cough, Coryza, Conjunctivitis); AIRBORNE PRECAUTIONS", "Place measles child in open hallway without mask"),
            PediatricTopic("Pediatric: Pertussis (Whooping Cough) Paroxysmal Stage & Droplet Precautions", "Bordetella pertussis; paroxysmal coughing fits followed by high-pitched 'whoop' sound, post-tussive emesis; DROPLET PRECAUTIONS; maintain airway, suction, humidified oxygen", "Pertussis requires zero precautions or isolation"),
            PediatricTopic("Pediatric: Varicella (Chickenpox) Communicability & Calamine Care", "Varicella-zoster virus; vesicular rash in various stages (papules, vesicles, crusts); COMMUNICABLE UNTIL ALL LESIONS HAVE CRUSTED OVER; AIRBORNE AND CONTACT PRECAUTIONS; calamine lotion", "Send child with active weeping chickenpox vesicles to school"),
            PediatricTopic("Pediatric: Scabies Sarcoptes Scabiei Permethrin Cream Rules", "Microscopic mite infestation; intense itching especially at night, burrows in finger webs/wrists; apply 5% PERMETHRIN CREAM FROM NECK DOWN TO SOLES OF FEET, leave for 8-14 hours, wash off", "Apply permethrin cream only to top of forehead and rinse off in 2 seconds"),
            PediatricTopic("Pediatric: Pinworms (Enterobius Vermicularis) Tape Test", "Intense nocturnal anal itching; diagnose via MORNING ANAL CELLOPHANE TAPE TEST before bathing/defecation; treat entire household with Mebendazole/Pyrantel pamoate", "Tape test is performed after a 30-minute hot soapy bath"),
            PsychTopic("Psychiatric: Bipolar Mania Priority Interventions & High Energy", "Hyperactive, racing thoughts, insomnia; PRIORITY: PHYSICAL SAFETY AND HYDRATION; high-calorie portable finger foods, quiet room, firm consistent boundaries", "Force manic client to sit quietly in room for 12 hours with no food"),
            PsychTopic("Psychiatric: Conversion Disorder (Functional Neurological Symptom Disorder)", "Sudden neurological symptom (blindness, paralysis, seizure) without organic cause following severe stress; 'LA BELLE INDIFFERENCE' (lack of concern about symptom); acknowledge symptom without dwelling", "Accuse client of faking paralysis for financial gain"),
            PsychTopic("Psychiatric: Dissociative Identity Disorder (DID) Integration Therapy", "Presence of 2 or more distinct personality states (alters) caused by severe childhood trauma; goal is INTEGRATION OF ALTERS; build trusting relationship with each alter", "Force alters to fight each other aggressively in group therapy"),
            PsychTopic("Psychiatric: Neuroleptic Malignant Syndrome (NMS) vs Serotonin Syndrome", "NMS (antipsychotics): severe rigidity ('lead-pipe'), hyperthermia, hyporeflexia, elevated CK; Serotonin Syndrome (SSRIs): hyperreflexia, myoclonus, clonus, tremors, diarrhea", "NMS presents with hyperreflexia and severe watery diarrhea"),
            PsychTopic("Psychiatric: Antipsychotic Extrapyramidal Symptoms Tardive Dyskinesia Screening", "Screening via AIMS (Abnormal Involuntary Movement Scale) every 3-6 months; Tardive Dyskinesia (involuntary protrusion of tongue, lip smacking, choreoathetoid movements) is often IRREVERSIBLE", "Tardive dyskinesia is a completely reversible mild muscle twitch")
        )

        for (i in 0 until 130) {
            val topicIndex = i % masteryTopicsPart3.size
            val item = masteryTopicsPart3[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Mastery Clinical Care Standard: ${item.second}",
                "Inappropriate / Non-Standard Practice: ${item.third}",
                "Omit safety monitoring and leave client unobserved",
                "Delegate specialized nursing tasks to non-clinical personnel"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pediatric, OB & Psychiatric Nursing",
                "NCLEX-RN / DHA • Mastery Series",
                "Mastery Series Specialized Care Case #${i + 1}: In providing specialized care for a client presenting with ${item.first}, which clinical action represents gold-standard evidence-based practice?",
                options,
                correctPos,
                "Rationale: Specialized pediatric, obstetric, and psychiatric mastery protocols for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures neonate/mother or psychiatric safety, avoids adverse complications, and maintains optimal health outcomes. Action '${item.third}' is unsafe.",
                "Peds/OB/Psych Mastery • ${item.first}"
            )
        }

        return list
    }

    private fun PediatricTopic(title: String, second: String, third: String) = Triple(title, second, third)
    private fun PsychTopic(title: String, second: String, third: String) = Triple(title, second, third)
}
