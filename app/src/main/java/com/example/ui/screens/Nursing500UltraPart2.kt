package com.example.ui.screens

/**
 * ULTRA BANK PART 2: ADVANCED CLINICAL PHARMACOLOGY, HIGH-ALERT DRUGS & CALCULATIONS (140 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500UltraPart2 {

    fun getPharmUltraQuestions(startId: Int): List<NursingExamQuestion> {
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

        val ultraTopicsPart2 = listOf(
            Triple("Pharmacology: Digoxin Toxicity Risk Factors & Antidote", "Therapeutic level: 0.5 - 2.0 ng/mL; Signs of toxicity: Nausea, yellow-green visual halos, bradycardia; HYPOKALEMIA increases risk of digoxin toxicity; Antidote: DIGOXIN IMMUNE FAB (Digibind)", "Hyperkalemia increases digoxin toxicity; antidote is Vitamin K"),
            Triple("Pharmacology: Heparin vs Warfarin Monitoring & Antidotes", "Heparin monitored by aPTT (target 1.5 - 2.5x control); Antidote: PROTAMINE SULFATE; Warfarin monitored by PT/INR (target 2.0 - 3.0); Antidote: VITAMIN K (Phytonadione)", "Warfarin antidote is Protamine Sulfate; Heparin antidote is Vitamin K"),
            Triple("Pharmacology: Lithium Carbonate Toxicity & Sodium Levels", "Therapeutic level: 0.6 - 1.2 mEq/L; Toxicity (> 1.5): Coarse hand tremors, ataxia, confusion, seizures; HYPONATREMIA or dehydration causes lithium retention and toxicity", "High sodium diet causes severe lithium toxicity; target therapeutic level is 10 mEq/L"),
            Triple("Pharmacology: Aminoglycosides (Gentamicin/Tobramycin) Toxicities", "Black Box Warnings: OTOTOXICITY (tinnitus, irreversible hearing loss, vertigo) and NEPHROTOXICITY; monitor Peak and Trough levels (draw trough immediately before next dose)", "Gentamicin causes severe peripheral hepatotoxicity and hypoglycemia"),
            Triple("Pharmacology: Vancomycin Red Man Syndrome vs Anaphylaxis", "Red Man Syndrome: Flushing, erythema, pruritus of chest/neck caused by TOO RAPID IV INFUSION (histamine release); treat by SLOWING INFUSION RATE over at least 60 minutes", "Red Man Syndrome is severe IgE anaphylaxis; treat by increasing infusion speed"),
            Triple("Pharmacology: Monoamine Oxidase Inhibitors (MAOIs) Tyramine Reaction", "Phenelzine, Tranylcypromine, Isocarboxazid; avoid TYRAMINE-RICH FOODS (aged cheese, cured meats, red wine, fava beans); tyramine ingestion triggers severe HYPERTENSIVE CRISIS", "MAOI patients should eat aged cheddar cheese and pepperoni pizza daily"),
            Triple("Pharmacology: Phenytoin (Dilantin) Therapeutic Range & Side Effects", "Therapeutic range: 10 - 20 mcg/mL; Side effects: GINGIVAL HYPERPLASIA (encourage dental hygiene), nystagmus, ataxia; IV administration must use 0.9% Normal Saline ONLY to prevent precipitation", "Infuse Phenytoin with 5% Dextrose in Water to increase solubility"),
            Triple("Pharmacology: Haloperidol & Neuroleptic Malignant Syndrome (NMS)", "NMS: Life-threatening reaction to antipsychotics; Symptoms: FEVER (hyperthermia), muscle rigidity ('lead pipe'), altered mental status, autonomic instability; Antidote: DANTROLENE or Bromocriptine", "NMS presents with hypothermia and flaccid paralysis; treat with Haloperidol bolus"),
            Triple("Pharmacology: Morphine Sulfate & Opioid Toxicity Triad", "Opioid Triad: Coma, Pinpoint Pupils (miosis), Respiratory Depression (< 10 breaths/min); Antidote: NALOXONE (Evzio/Narcan); monitor for opioid withdrawal and re-sedation", "Opioid overdose causes severely dilated pupils and hyperventilation; antidote is Atropine"),
            Triple("Pharmacology: Adenosine Administration Protocol for SVT", "Used for Supraventricular Tachycardia (SVT); dose 6 mg rapid IV push followed immediately by 20 mL Normal Saline flush; brief period of ASYSTOLE on monitor is expected", "Infuse Adenosine via slow IV drip over 4 hours"),
            Triple("Pharmacology: Magnesium Sulfate Toxicity in Preeclampsia", "Therapeutic range: 4 - 7 mEq/L; Signs of toxicity: LOSS OF DEEP TENDON REFLEXES (patellar), respiratory depression (< 12/min), oliguria; Antidote: CALCIUM GLUCONATE", "First sign of magnesium toxicity is hyper-reflexia; antidote is protamine sulfate"),
            Triple("Pharmacology: Isocarboxazid / Selegiline & Serotonin Syndrome", "Combining MAOIs with SSRIs/SNRIs triggers Serotonin Syndrome: Tremor, hyperreflexia, clonus, diaphoresis, hyperthermia; allow a 14-day WASHOUT PERIOD between drugs", "Mix SSRIs with MAOIs immediately for additive antidepressant efficacy"),
            Triple("Pharmacology: Potassium Chloride (KCl) IV Safety Rules", "NEVER GIVE KCL IV PUSH (causes immediate fatal cardiac arrest); maximum IV infusion rate is 10 mEq/hr on general unit or 20 mEq/hr in ICU with cardiac monitoring", "Administer 40 mEq KCl via direct rapid IV push over 10 seconds"),
            Triple("Pharmacology: Infliximab / Adalimumab (TNF-Inhibitors) Infection Risk", "Immunosuppressive biologics for RA/IBD; MANDATORY PRE-TREATMENT SCREENING FOR LATENT TUBERCULOSIS (PPD/IGRA); hold drug if client has active infection or fever", "Administer live attenuated vaccines to clients receiving Infliximab"),
            Triple("Pharmacology: Methotrexate Toxicity & Leucovorin Rescue", "Folate antagonist antineoplastic; causes bone marrow suppression, hepatotoxicity, stomatitis; LEUCOVORIN (folinic acid) administered as rescue agent to prevent severe toxicity", "Leucovorin is given to enhance methotrexate toxicity and stop cell growth"),
            Triple("Pharmacology: Atropine Sulfate Mechanisms & Indications", "Anticholinergic agent; increases heart rate in SYMPTOMATIC BRADYCARDIA; antidote for organophosphate poisoning and cholinergic crisis; causes dry mouth, urinary retention, blurred vision", "Atropine causes severe profuse salivation and bradycardia"),
            Triple("Pharmacology: Acetaminophen Toxicity & N-Acetylcysteine (NAC)", "Maximum daily dose: 4,000 mg (4 g/day); acute overdose causes severe HEPATOTOXICITY; Antidote: N-ACETYLCYSTEINE (Mucomyst); most effective within 8 hours of ingestion", "Acetaminophen antidote is Naloxone; maximum dose is 20 grams/day"),
            Triple("Pharmacology: Nitroglycerin Storage & Sublingual Administration", "Store in dark glass bottle away from light/heat; take 1 tablet SL every 5 min for up to 3 doses; if chest pain persists after FIRST dose, call 911; expect headache/flushing", "Swallow nitroglycerin tablets with 2 glasses of hot water"),
            Triple("Pharmacology: Amiodarone Black Box Warnings & Monitoring", "Antiarrhythmic; Black Box Warnings: PULMONARY TOXICITY (cough, dyspnea, pulmonary infiltrates), hepatotoxicity, thyroid dysfunction (hypo/hyperthyroidism), corneal microdeposits", "Amiodarone is completely free of pulmonary or thyroid side effects"),
            Triple("Pharmacology: Dosage Calculation: IV Flow Rate (gtt/min)", "Formula: (Total Volume in mL * Drop Factor in gtt/mL) / Time in Minutes = Flow Rate in gtt/min; Example: 1000 mL / 8 hours (480 min) * 15 gtt/mL = 31.25 -> 31 gtt/min", "Flow rate = Total Volume * 1000 / Time in Seconds")
        )

        for (i in 0 until 140) {
            val topicIndex = i % ultraTopicsPart2.size
            val item = ultraTopicsPart2[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Double the medication dose without physician consultation if client complains",
                "Instruct non-licensed staff to calculate and administer high-alert IV vasoactive drips"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pharmacology & Dosage Calculations",
                "NCLEX-RN / DHA • Ultra Series",
                "Ultra Series Pharmacology Case #${i + 1}: The nurse is preparing to administer or monitor therapy involving ${item.first}. Which evidence-based pharmacological standard must be enforced?",
                options,
                correctPos,
                "Rationale: High-alert pharmacology principles for ${item.first} require: ${item.second}.",
                "Option breakdown: Correct choice ensures drug safety and avoids toxic adverse drug events. Option '${item.third}' is unsafe.",
                "Pharmacology Ultra • ${item.first}"
            )
        }

        return list
    }
}
