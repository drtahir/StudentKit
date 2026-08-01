package com.example.ui.screens

/**
 * MASTERY BANK PART 2: CLINICAL PHARMACOLOGY, HIGH-ALERT DRUGS & CALCULATIONS (140 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500MasteryPart2 {

    fun getPharmMasteryQuestions(startId: Int): List<NursingExamQuestion> {
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

        val masteryTopicsPart2 = listOf(
            Triple("High-Alert Antidotes: Anticholinergic Toxicity Antidote (Physostigmine)", "Anticholinergic syndrome ('Mad as a hatter, blind as a bat, red as a beet, hot as a hare, dry as a bone'); treated with PHYSOSTIGMINE SALICYLATE (acetylcholinesterase inhibitor)", "Antidote for anticholinergic toxicity is high-dose Atropine"),
            Triple("High-Alert Antidotes: Cyanide Poisoning Antidote Kit & Hydroxocobalamin", "Cyanide toxicity (almond breath odor, lactic acidosis, confusion); treated with HYDROXOCOBALAMIN (Cyanokit - binds cyanide to form Vitamin B12) or Sodium Thiosulfate", "Cyanide toxicity is treated with oral activated charcoal and milk"),
            Triple("High-Alert Antidotes: Iron Overdose & Deferoxamine Chelating Agent", "Iron toxicity (vomiting, bloody diarrhea, metabolic acidosis, hepatic necrosis); treated with DEFEROXAMINE (binds ferric iron excreted in urine as reddish-pink urine)", "Iron overload is treated with IV iron dextran bolus"),
            Triple("High-Alert Antidotes: Organophosphate Insecticide Toxicity & Pralidoxime/Atropine", "Cholinergic crisis ('SLUDGEM': Salivation, Lacrimation, Urination, Defecation, GI distress, Emesis, Miosis); treated with ATROPINE (blocks muscarinic) AND PRALIDOXIME (PAM-2)", "Treat organophosphate poisoning with IV Neostigmine"),
            Triple("High-Alert Antidotes: Methemoglobinemia & Methylene Blue", "Methemoglobinemia caused by benzocaine, nitrates, dapsone (chocolate-brown blood, severe cyanosis refractory to oxygen); treated with IV METHYLENE BLUE", "Methylene blue causes immediate severe methemoglobin formation"),
            Triple("High-Alert Meds: Continuous Insulin Infusion Protocol & Potassium Monitoring", "Regular Insulin IV drip for DKA/HHS; monitor blood glucose HOURLY; monitor serum POTASSIUM continuously (insulin drives potassium into cells causing hypokalemia); add dextrose when BG < 200", "Infuse insulin drip without checking potassium levels"),
            Triple("High-Alert Meds: Intravenous Magnesium Sulfate Toxicity Management", "Signs of toxicity: loss of Deep Tendon Reflexes (DTRs - EARLIEST SIGN), respiratory depression (< 12/min), oliguria (< 30 mL/hr); STOP INFUSION IMMEDIATELY, GIVE CALCIUM GLUCONATE IV", "Double magnesium drip when deep tendon reflexes disappear"),
            Triple("High-Alert Meds: Continuous Epidural Analgesia & Motor Blockade", "Monitor epidural site for hematoma/infection; test sensory block level with ice/pinprick; monitor for HYPOTENSION and MOTOR BLOCKADE (inability to bend knees); keep Naloxone nearby", "Client should have complete flaccid paralysis up to neck"),
            Triple("Intravenous Calculations: Drop Rate (gtt/min) Formula", "Drip Rate (gtt/min) = [Total Volume (mL) x Drip Factor (gtt/mL)] / Time in Minutes; example: 1000 mL over 8 hrs with 15 gtt/mL = (1000 x 15) / 480 = 31.25 -> 31 gtt/min", "Multiply volume by hours and divide by 100"),
            Triple("Intravenous Calculations: Weight-Based IV Drip (mcg/kg/min) Rules", "Dopamine / Dobutamine / Nitroprusside; convert weight to kg (lbs / 2.2); calculate total mcg/min needed = (mcg/kg/min x kg); convert to mL/hr on infusion pump", "Guess pump rate without converting weight to kg"),
            Triple("Blood Transfusion: Massive Transfusion Protocol & Hypocalcemia", "Massive transfusion (> 10 units packed RBCs in 24 hrs); risk of CITRATE TOXICITY causing HYPOCALCEMIA (citrate binds ionized calcium) and HYPOTHERMIA; give Calcium Chloride IV", "Massive transfusion causes severe hypercalcemia"),
            Triple("Blood Transfusion: Acute Hemolytic Transfusion Reaction Signs", "Immediate reaction from ABO incompatibility; symptoms: FEVER, CHILLS, FLANK/BACK PAIN, HYPOTENSION, DYSPNEA, HEMOGLOBINURIA; stop blood immediately and keep line open with NS", "Hemolytic reaction presents with mild euphoria"),
            Triple("Blood Transfusion: Transfusion-Related Acute Lung Injury (TRALI)", "Leading cause of transfusion-related mortality; acute non-cardiogenic pulmonary edema within 6 hours of transfusion; dyspnea, hypoxia, bilateral pulmonary infiltrates, fever", "TRALI is cured by giving 5 Liters of whole blood"),
            Triple("Blood Transfusion: Transfusion-Associated Circulatory Overload (TACO)", "Fluid overload from rapid transfusion; hypertension, distended neck veins (JVD), crackles, dyspnea; place client upright, slow/stop transfusion, administer IV Furosemide", "Lay client flat supine and speed up transfusion rate"),
            Triple("Antibiotics: Aminoglycoside Peak & Trough Drawing Schedule", "Trough level drawn IMMEDIATELY BEFORE next dose (within 30 mins); Peak level drawn 30 mins after end of 30-min IV infusion; prevents ototoxicity and nephrotoxicity", "Draw trough level 3 hours after IV dose finishes"),
            Triple("Antibiotics: Linezolid Serotonin Syndrome & Tyramine Interactions", "Oxazolidinone antibiotic for VRE/MRSA; weak MAOI inhibitor; avoid co-administration with SSRIs/pseudoephedrine and TYRAMINE-RICH FOODS to prevent SEROTONIN SYNDROME/HTN crisis", "Eat aged cheese and take Prozac with Linezolid"),
            Triple("Anticoagulation: Direct Oral Anticoagulants (DOACs) Antidotes", "Dabigatran antidote: IDARUCIZUMAB (Praxbind); Rivaroxaban / Apixaban antidote: ANDEXANET ALFA (Andexxa); no routine coagulation monitoring required", "Antidote for Dabigatran is Vitamin K IM"),
            Triple("Psychotropics: Lithium Toxicity & NSAID Drug Interaction", "NSAIDs (Ibuprofen, Naproxen, Indomethacin) DECREASE LITHIUM CLEARANCE causing fatal LITHIUM TOXICITY; use Acetaminophen for pain relief in clients taking Lithium", "Encourage taking high-dose Ibuprofen with Lithium"),
            Triple("Psychotropics: Selective Serotonin Reuptake Inhibitors (SSRIs) Discontinuation Syndrome", "Abrupt discontinuation of SSRIs causes 'FINISH' syndrome (Flu-like symptoms, Insomnia, Nausea, Imbalance, Sensory disturbances, Hyperarousal); taper off over weeks", "Stop SSRI abruptly on Day 1 without tapering"),
            Triple("Immunosuppressants: Cyclosporine / Tacrolimus Nephrotoxicity & Grapefruit", "Nephrotoxic and hepatotoxic; monitor blood trough levels; AVOID GRAPEFRUIT JUICE (inhibits CYP3A4 metabolism raising drug levels to toxic range); monitor blood pressure", "Drink 2 Liters of fresh grapefruit juice with Tacrolimus"),
            Triple("Cardiovascular: Antihypertensive Monotherapy vs Combination in Black Clients", "Thiazide diuretics and Calcium Channel Blockers (Amlodipine) are first-line monotherapy for hypertension in Black clients; ACE inhibitors/ARBs are less effective as monotherapy", "ACE inhibitor monotherapy is most effective in Black clients"),
            Triple("Endocrine: Desmopressin (DDAVP) Treatment for Diabetes Insipidus", "Synthetic ADH replacement; decreases urine output and increases urine specific gravity; monitor for WATER INTOXICATION / HYPONATremia (headache, confusion, drowsiness)", "Desmopressin increases urine output to 20 Liters/day"),
            Triple("Gastrointestinal: Pancrelipase Administration Rules for Cystic Fibrosis", "Pancreatic enzyme replacement; MUST BE TAKEN WITH EVERY MEAL AND SNACK; capsule contents can be sprinkled on acidic food (applesauce) but DO NOT CHEW or crush beads", "Chew pancreatic enzyme capsules thoroughly 2 hours after meals"),
            Triple("Antineoplastics: Cyclophosphamide Hemorrhagic Cystitis & Mesna", "Alkylating agent; causes HEMORRHAGIC CYSTITIS (bladder bleeding); encourage high fluid intake (2-3 L/day) and co-administer MESNA (uroprotective agent) to prevent bladder toxicity", "Restrict fluid intake and discard Mesna"),
            Triple("Antineoplastics: Doxorubicin (Adriamycin) Cardiotoxicity & Dexrazoxane", "Antitumor antibiotic ('Red Devil'); Black Box Warning for CARDIOTOXICITY / HEART FAILURE; baseline echocardiogram (LVEF assessment required); Dexrazoxane is cardioprotectant", "Doxorubicin improves cardiac ejection fraction"),
            Triple("Antineoplastics: Vincristine Peripheral Neuropathy & Fatal Intrathecal Warning", "Plant alkaloid; Black Box Warning: FOR INTRAVENOUS USE ONLY - FATAL IF GIVEN INTRATHECALLY; side effect: PERIPHERAL NEUROPATHY (numbness, paralytic ileus, foot drop)", "Administer Vincristine via intrathecal spinal tap"),
            Triple("Respiratory: Theophylline Therapeutic Range & Caffeine Avoidance", "Methylxanthine bronchodilator; therapeutic level: 10-20 mcg/mL; toxicity signs: anorexia, nausea, vomiting, SEIZURES, LETHAL ARRHYTHMIAS; avoid caffeine (additive stimulant effect)", "Therapeutic level of Theophylline is 80 mcg/mL"),
            Triple("Ophthalmic Meds: Beta-Blocker Eyedrops (Timolol) Nasolacrimal Occlusion", "Used for glaucoma; to prevent systemic absorption (bradycardia, bronchospasm), APPLY PRESSURE TO NASOLACRIMAL DUCT (inner canthus) for 1-2 minutes after instillation", "Rub eye vigorously and blink 500 times post drop"),
            Triple("Otology Meds: Otic Drops Administration Adult vs Child", "Adult and child > 3 years: PULL PINNA UP AND BACK; Child < 3 years: PULL PINNA DOWN AND BACK; client lies on uninfected side for 5 minutes after instillation", "Pull pinna down and back for 50-year-old adult"),
            Triple("Emergency Toxicology: Activated Charcoal Administration Window", "Administer within 1 HOUR OF INGESTION of toxic substance; binds toxins in gut preventing systemic absorption; contraindicated if altered mental status (aspiration risk) or hydrocarbons", "Give activated charcoal 24 hours after toxic ingestion")
        )

        for (i in 0 until 140) {
            val topicIndex = i % masteryTopicsPart2.size
            val item = masteryTopicsPart2[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Omit documentation and double dose next shift",
                "Administer medication without verifying rights or client identity"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Advanced Clinical Pharmacology",
                "NCLEX-RN / DHA • Mastery Series",
                "Mastery Series Pharmacology Case #${i + 1}: In managing a complex pharmacological intervention or high-alert medication involving ${item.first}, which clinical action represents gold-standard practice?",
                options,
                correctPos,
                "Rationale: Clinical pharmacology mastery protocols for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice ensures drug safety, prevents toxicities, and maintains therapeutic efficacy. Action '${item.third}' is unsafe.",
                "Pharm Mastery • ${item.first}"
            )
        }

        return list
    }
}
