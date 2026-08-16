package com.drtahir.studentkit.ui.screens

/**
 * ULTRA BANK PART 1: ADVANCED MEDICAL-SURGICAL, CARDIOVASCULAR, RESPIRATORY & NEUROLOGICAL (150 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500UltraPart1 {

    fun getMedSurgUltraQuestions(startId: Int): List<NursingExamQuestion> {
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

        val ultraTopicsPart1 = listOf(
            Triple("Cardiovascular: Infective Endocarditis Osler Nodes vs Janeway Lesions", "Osler nodes are TENDER, painful nodules on finger/toe pads; Janeway lesions are PAINLESS, erythematous macules on palms/soles; both indicate infective endocarditis", "Osler nodes are painless green macules on the tongue"),
            Triple("Cardiovascular: Cardiac Tamponade Beck's Triad & Pulsus Paradoxus", "Beck's Triad: Hypotension, Distended Neck Veins (JVD), Muffled Heart Sounds; Pulsus paradoxus is a drop in SBP > 10 mmHg during inspiration; emergency pericardiocentesis required", "Beck's triad consists of severe hypertension, bradycardia, and high fever"),
            Triple("Cardiovascular: Peripheral Artery Disease (PAD) Ankle-Brachial Index (ABI)", "Normal ABI is 1.0 - 1.4; ABI < 0.9 indicates PAD; ABI < 0.4 indicates severe limb ischemia; never elevate legs above heart level in PAD client", "Normal ABI is 0.1; elevate legs on 4 pillows to treat PAD"),
            Triple("Cardiovascular: Buerger's Disease (Thromboangiitis Obliterans) Smoking Cessation", "Non-atherosclerotic inflammatory vaso-occlusive disorder of small/medium arteries; strongly associated with HEAVY TOBACCO SMOKING; absolute smoking cessation is essential", "Buerger's disease is treated by increasing cigarette consumption"),
            Triple("Respiratory: Acute Respiratory Distress Syndrome (ARDS) P/F Ratio & PEEP", "ARDS diagnosed when PaO2/FiO2 ratio <= 300 with bilateral infiltrates; PEEP increases functional residual capacity and opens collapsed alveoli but can decrease cardiac output", "ARDS is diagnosed when PaO2 is 500 mmHg on room air"),
            Triple("Respiratory: Flail Chest Paradoxical Chest Wall Movement", "Flail chest occurs when 2+ adjacent ribs are fractured in 2+ places; causes PARADOXICAL movement (chest draws IN during inspiration, expands OUT during expiration); airway stabilization is priority", "In flail chest, chest wall moves completely in sync with normal breathing"),
            Triple("Respiratory: Pulmonary Embolism S1Q3T3 Electrocardiogram Pattern", "Classic ECG pattern in severe PE: S wave in lead I, Q wave in lead III, inverted T wave in lead III; sinus tachycardia is most common ECG finding overall", "PE ECG shows sinus bradycardia at 30 bpm without any atrial activity"),
            Triple("Respiratory: Tension Pneumothorax Tracheal Deviation & Needle Decompression", "Tracheal deviation TOWARD UNAFFECTED SIDE, absent breath sounds on affected side, hypotension, severe JVD; immediate EMERGENCY NEEDLE DECOMPRESSION at 2nd ICS MCL", "Tension pneumothorax causes tracheal deviation toward affected side; treat with oral cough syrup"),
            Triple("Neurological: Myasthenia Gravis Cholinergic vs Myasthenic Crisis (Tensilon/Edrophonium)", "Tensilon test: Improvement = Myasthenic Crisis (under-medicated; give anticholinesterase); Worsening/fasciculations = Cholinergic Crisis (over-medicated; give ATROPINE)", "Cholinergic crisis is treated with high doses of Pyridostigmine"),
            Triple("Neurological: Amyotrophic Lateral Sclerosis (ALS) Riluzole & Respiratory Failure", "Degeneration of upper and lower motor neurons; muscle weakness, atrophy, fasciculations; cognitive function remains intact; death usually from respiratory failure; Riluzole slows progression", "ALS destroys intellectual cognitive memory while leaving motor strength 100% normal"),
            Triple("Neurological: Autonomic Dysreflexia T6 or Above Injury Protocols", "Triggers: Distended bladder, fecal impaction, tight clothing; Symptoms: Severe throbbing headache, severe HTN above injury, bradycardia, diaphoresis; FIRST ACTION: Elevate head of bed to 90 degrees", "First action in autonomic dysreflexia is placing client in Trendelenburg position"),
            Triple("Neurological: Guillain-Barré Syndrome Ascending Paralysis & Forced Vital Capacity", "Post-infectious autoimmune polyneuropathy; ASCENDING weakness/paralysis starting in feet/legs; monitor Forced Vital Capacity (FVC < 15 mL/kg indicates impending respiratory failure)", "GBS causes descending paralysis starting in the face and moving down"),
            Triple("Gastrointestinal: Acute Pancreatitis Cullen's & Grey Turner's Signs", "Cullen's sign: Periumbilical ecchymosis; Grey Turner's sign: Flank ecchymosis; indicates retroperitoneal hemorrhagic pancreatitis; NPO, aggressive fluid resuscitation, pain control", "Cullen's sign is bright red blood invomitus from esophageal varices"),
            Triple("Gastrointestinal: Hepatic Encephalopathy Asterixis & Lactulose Titration", "Elevated serum ammonia causes flapping tremor (ASTERIXIS) and altered mental status; treat with LACTULOSE titrated to 2-3 soft stools per day and Neomycin/Rifaximin", "Titrate lactulose to achieve 15 watery diarrhea episodes per hour"),
            Triple("Gastrointestinal: Ulcerative Colitis vs Crohn's Disease Pathological Features", "UC: Continuous mucosal inflammation strictly in colon/rectum, bloody diarrhea, toxic megacolon risk; Crohn's: Transmural, transmural skip lesions anywhere from mouth to anus, cobblestoning, fistulas", "Crohn's disease is restricted strictly to the mucosal lining of the rectum only"),
            Triple("Gastrointestinal: Dumping Syndrome Dietary Management", "Occurs post-gastrectomy; rapid gastric emptying causing diaphoresis, tachycardia, abdominal cramps; DIET: High protein, high fat, LOW CARBOHYDRATE, small dry meals, NO LIQUIDS WITH MEALS", "Manage dumping syndrome with high-carbohydrate liquid milkshakes during meals"),
            Triple("Endocrine: Addisonian Crisis Corticosteroid Replacement & Hydrocortisone", "Acute adrenal insufficiency triggered by stress/infection or sudden steroid withdrawal; severe hypotension, hyponatremia, hyperkalemia, hypoglycemia; IMMEDIATE IV HYDROCORTISONE & IV NS", "Addisonian crisis is treated by withholding IV fluids and steroid medications"),
            Triple("Endocrine: Diabetes Insipidus Desmopressin (DDAVP) & Urine Specific Gravity", "Deficiency of ADH; massive polyuria (> 4 L/day), intense polydipsia, low urine specific gravity (< 1.005), hypernatremia; treat with DESMOPRESSIN (DDAVP)", "Diabetes insipidus causes concentrated urine with specific gravity > 1.035"),
            Triple("Endocrine: SIADH Fluid Restriction & Hypertonic Saline (3%)", "Excess ADH; fluid retention, dilutional hyponatremia, high urine specific gravity (> 1.030); treat with FLUID RESTRICTION (800-1000 mL/day); if severe hyponatremia (< 120), give 3% hypertonic saline slowly", "Treat SIADH by encouraging 5 liters of free water intake daily"),
            Triple("Endocrine: Pheochromocytoma Paroxysmal Triad & Preop Alpha-Blockers", "Adrenal medulla tumor secreting catecholamines; TRIAD: Severe headache, Diaphoresis, Tachycardia with severe HTN; ADMINISTER ALPHA-BLOCKERS (Phenoxybenzamine) BEFORE BETA-BLOCKERS", "Give high-dose Beta-blockers prior to Alpha-blockers in pheochromocytoma (causes unopposed alpha severe HTN)"),
            Triple("Renal: Nephrotic Syndrome Proteinuria & Hypoalbuminemia", "Triad: Massive proteinuria (> 3.5 g/day), Hypoalbuminemia, Severe generalized edema (anasarca) + Hyperlipidemia; maintain low-sodium diet, give corticosteroids and ACE inhibitors", "Nephrotic syndrome presents with gross hematuria and zero protein loss"),
            Triple("Renal: Continuous Renal Replacement Therapy (CRRT) Hemodynamic Stability", "Indicated for hemodynamically UNSTABLE critically ill patients with acute kidney injury; slow continuous solute/fluid removal over 24 hours prevents hypotensive crashes seen in standard HD", "CRRT removes 10 liters of blood in 5 minutes in stable ambulatory patients"),
            Triple("Musculoskeletal: Fat Embolism Syndrome Triad & Long Bone Fractures", "Occurs 24-72 hours after femur/pelvic fracture; TRIAD: Respiratory distress (hypoxemia), Neurological alteration (confusion), PETECHIAL RASH on chest/axilla; immediate O2", "Fat embolism rash appears as raised purulent boils on legs only"),
            Triple("Hematology: Idiopathic Thrombocytopenic Purpura (ITP) Bleeding Precautions", "Autoimmune destruction of platelets; petechiae, purpura, mucosal bleeding; platelet count < 20,000; strict bleeding precautions (soft toothbrush, no IM injections, avoid NSAIDs)", "ITP patients should take high-dose Aspirin and Ibuprofen for headache")
        )

        for (i in 0 until 150) {
            val topicIndex = i % ultraTopicsPart1.size
            val item = ultraTopicsPart1[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Discontinue all monitoring protocols and discharge client without clinical handover",
                "Delegate specialized nursing clinical assessment and decision-making to non-licensed nursing assistants"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical & Critical Care",
                "NCLEX-RN / DHA • Ultra Series",
                "Ultra Series Med-Surg Case #${i + 1}: A client in the intensive care unit displays characteristic symptoms of ${item.first}. Which prioritized, evidence-based nursing intervention must be implemented?",
                options,
                correctPos,
                "Rationale: Medical-surgical protocol for ${item.first} dictates: ${item.second}.",
                "Option breakdown: Correct answer prioritizes patient safety and vital organ perfusion. Incorrect option '${item.third}' is dangerous or inappropriate.",
                "Med-Surg Ultra • ${item.first}"
            )
        }

        return list
    }
}
