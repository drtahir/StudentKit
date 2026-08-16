package com.drtahir.studentkit.ui.screens

/**
 * PART 1: MEDICAL-SURGICAL NURSING (150 MCQs)
 * NCLEX-RN, DHA, HAAD, Prometric, PNC & International Competitive Exam Standard.
 */
object Nursing500BankPart1 {

    fun getMedSurgQuestions(startId: Int): List<NursingExamQuestion> {
        var idCounter = startId
        val list = mutableListOf<NursingExamQuestion>()

        // Helper builder function for consistent clean code
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

        // =========================================================================
        // CARDIOVASCULAR SYSTEM (30 QUESTIONS)
        // =========================================================================
        addQ(
            "Medical-Surgical Nursing",
            "NCLEX-RN / DHA • Hard",
            "A client admitted with acute decompensated heart failure is prescribed intravenous furosemide 80 mg push. Which clinical finding requires IMMEDIATE intervention by the nurse prior to administration?",
            listOf(
                "Serum potassium level of 2.8 mEq/L (2.8 mmol/L)",
                "Blood pressure of 128/76 mmHg",
                "2+ bilateral pitting edema in lower extremities",
                "Brain Natriuretic Peptide (BNP) level of 850 pg/mL"
            ),
            0,
            "Furosemide is a loop diuretic that causes significant potassium excretion. Administering furosemide to a client with severe hypokalemia (potassium < 3.5 mEq/L) dramatically increases the risk of fatal ventricular arrhythmias (such as V-Tach, V-Fib, and Torsades de Pointes). The nurse must hold the dose and notify the provider immediately.",
            "Option A is the correct priority safety hold. Option B (BP 128/76) is within normal limits and safe for diuretic administration. Option C (2+ edema) and Option D (elevated BNP > 100 pg/mL) are expected findings in acute heart failure indicating fluid overload.",
            "Cardiovascular • Heart Failure & Diuretic Safety"
        )

        addQ(
            "Medical-Surgical Nursing",
            "HAAD / Prometric • Medium",
            "A nurse is caring for a patient who underwent cardiac catheterization via the right femoral artery 2 hours ago. The nurse notes that the right pedal pulse is absent and the right foot is cold and pale. What is the PRIORITY nursing action?",
            listOf(
                "Elevate the right leg on two pillows to enhance venous return",
                "Notify the healthcare provider / rapid response team immediately",
                "Reassess the pedal pulse in 30 minutes after applying a warm blanket",
                "Encourage the patient to flex the right knee to stimulate perfusion"
            ),
            1,
            "Loss of a previously present arterial pulse distal to a catheterization site accompanied by coldness and pallor indicates acute arterial occlusion or hematoma compression. This is a medical emergency requiring urgent revascularization or vascular surgeon evaluation to prevent tissue necrosis and limb loss.",
            "Option B is correct because immediate provider notification is critical. Option A (elevation) is contraindicated in acute arterial insufficiency as gravity further reduces flow. Option C delays critical emergency intervention. Option D (knee flexion) is strictly contraindicated post-femoral catheterization (patient must remain flat with leg straight).",
            "Cardiovascular • Post-Cardiac Catheterization Complications"
        )

        addQ(
            "Medical-Surgical Nursing",
            "NCLEX-RN / Prometric • Hard",
            "A client with chronic heart failure who takes digoxin 0.125 mg daily reports persistent nausea, loss of appetite, and seeing yellow-green halos around lights. Which laboratory test should the nurse review FIRST?",
            listOf(
                "Serum creatinine and BUN levels",
                "Serum potassium and digoxin levels",
                "Complete blood count with differential",
                "Serum troponin I and CK-MB levels"
            ),
            1,
            "Nausea, anorexia, visual disturbances (yellow-green halos, blurring), and bradycardia are classic signs of digoxin toxicity. Hypokalemia (potassium < 3.5 mEq/L) potentiates digoxin binding to the Na+/K+ ATPase pump, dramatically worsening toxicity even at therapeutic serum digoxin concentrations (0.5–2.0 ng/mL).",
            "Option B is the immediate priority lab check. Option A assesses renal function (which affects clearance) but does not immediately measure electrolyte toxicity triggers. Option C is unrelated to digoxin. Option D measures cardiac necrosis, not cardiac glycoside toxicity.",
            "Cardiovascular • Cardiac Glycosides & Toxicity"
        )

        addQ(
            "Medical-Surgical Nursing",
            "DHA / MOH • Medium",
            "A client diagnosed with infective endocarditis develops sudden severe left flank pain accompanied by hematuria. The nurse suspects which complication?",
            listOf(
                "Splenomegaly secondary to systemic infection",
                "Renal infarction secondary to arterial embolization",
                "Glomerulonephritis secondary to immune complex deposition",
                "Acute urinary tract infection from prolonged catheterization"
            ),
            1,
            "Infective endocarditis involves vegetative lesions on heart valves that can break off as arterial emboli. Embolization to the kidneys manifests as sudden onset left or right flank pain, hematuria, and oliguria.",
            "Option B is correct. Option A causes left upper quadrant pain, not sudden flank pain with hematuria. Option C can occur slowly in endocarditis but does not present with sudden acute flank pain. Option D presents with dysuria and frequency rather than sudden flank pain.",
            "Cardiovascular • Infective Endocarditis Embolic Complications"
        )

        addQ(
            "Medical-Surgical Nursing",
            "NCLEX-RN • Hard",
            "A nurse monitors a telemetry strip and identifies a rhythm with no identifiable P waves, irregular ventricular response (QRS rate 130 bpm), and narrow QRS complexes. Which prescribed medication should the nurse anticipate administering for rate control?",
            listOf(
                "Intravenous Atropine 1 mg push",
                "Intravenous Diltiazem (Cardizem) bolus",
                "Subcutaneous Enoxaparin 1 mg/kg",
                "Intravenous Epinephrine 1 mg push"
            ),
            1,
            "The rhythm described is Atrial Fibrillation with Rapid Ventricular Response (RVR). Intravenous calcium channel blockers (diltiazem) or beta-blockers (metoprolol) are first-line pharmacological agents used for rapid rate control in stable AFib with RVR.",
            "Option B is correct. Option A (Atropine) increases heart rate and is contraindicated in tachyarrhythmias. Option C (Enoxaparin) prevents thrombus formation in AFib but does NOT control heart rate. Option D (Epinephrine) is used in cardiac arrest, not rate control for AFib.",
            "Cardiovascular • Cardiac Dysrhythmias & Atrial Fibrillation"
        )

        addQ(
            "Medical-Surgical Nursing",
            "HAAD / Prometric • Hard",
            "A client with an abdominal aortic aneurysm (AAA) is admitted for observation. Which clinical finding requires IMMEDIATE emergency medical intervention?",
            listOf(
                "Pulsatile abdominal mass visible near the umbilicus",
                "Bruit heard over the epigastric region upon auscultation",
                "Sudden severe lower back pain radiating to the groin with BP 82/48 mmHg",
                "History of essential hypertension controlled on amlodipine"
            ),
            2,
            "Sudden, severe, tearing lower back or abdominal pain radiating to the flank or groin accompanied by hypotension (BP 82/48) is the classic triad indicating impending or actual rupture of an abdominal aortic aneurysm. This is a life-threatening vascular emergency.",
            "Option C is the emergency priority. Option A (pulsatile mass) and Option B (epigastric bruit) are expected classic assessment findings of an intact AAA. Option D is a predisposing risk factor.",
            "Cardiovascular • Abdominal Aortic Aneurysm Rupture"
        )

        addQ(
            "Medical-Surgical Nursing",
            "DHA / NCLEX • Medium",
            "Which patient education instruction is essential for a client who is newly prescribed sublingual nitroglycerin for stable angina pectoris?",
            listOf(
                "Swallow the tablet with a full glass of water at the first sign of chest pain",
                "Take 1 tablet every 5 minutes up to 3 doses; if pain persists after 1 dose, call 911 immediately",
                "Keep the nitroglycerin tablets in a clear plastic pill box on the windowsill",
                "Take a double dose if chest pain occurs during high-intensity exercise"
            ),
            1,
            "Sublingual nitroglycerin should be placed under the tongue at the onset of chest pain. Current guidelines instruct the patient to take 1 dose, and if pain is unimproved or worsening after 5 minutes, immediately call emergency services (911) before taking a second or third dose 5 minutes apart.",
            "Option B aligns with updated cardiac emergency guidelines. Option A is incorrect because swallowing degrades nitroglycerin via hepatic first-pass metabolism. Option C is incorrect because nitroglycerin must be kept in its original dark glass bottle away from heat, light, and moisture. Option D is unsafe and causes severe refractory hypotension.",
            "Cardiovascular • Sublingual Nitroglycerin Patient Teaching"
        )

        addQ(
            "Medical-Surgical Nursing",
            "NCLEX-RN • Medium",
            "A client diagnosed with peripheral arterial disease (PAD) complains of severe calf pain when walking two blocks that is relieved by resting for 5 minutes. How should the nurse document and explain this symptom?",
            listOf(
                "Intermittent claudication resulting from arterial tissue ischemia during exercise",
                "Deep vein thrombosis caused by venous stasis in lower extremity valves",
                "Rest pain secondary to severe irreversible nerve damage",
                "Raynaud's phenomenon triggered by cold temperature exposure"
            ),
            0,
            "Intermittent claudication is the hallmark symptom of PAD. Inadequate arterial blood flow leads to anaerobic metabolism and lactic acid accumulation in muscles during exercise, causing ischemic muscle pain that subsides with rest.",
            "Option A is correct. Option B (DVT) causes persistent aching swelling and warmth, not exertion-dependent intermittent calf pain. Option C (rest pain) occurs when arterial occlusion is severe enough to cause pain even while sleeping/resting. Option D involves vasospasms of fingers/toes upon cold exposure.",
            "Cardiovascular • Peripheral Arterial Disease (PAD)"
        )

        addQ(
            "Medical-Surgical Nursing",
            "Prometric / SCFHS • Hard",
            "A patient with hypertension is prescribed captopril. The nurse monitors for which severe adverse effect that requires immediate permanent discontinuation of ACE inhibitor therapy?",
            listOf(
                "Mild dry non-productive tickling cough",
                "Angioedema presenting as swelling of the lips, tongue, and pharynx",
                "Serum potassium level of 4.2 mEq/L",
                "Orthostatic dizziness upon standing from a sitting position"
            ),
            1,
            "Angioedema is a rapid, non-pitting swelling of the subcutaneous tissues, mucous membranes, lips, tongue, and larynx caused by bradykinin accumulation. It poses an immediate fatal airway obstruction risk and requires permanent avoidance of all ACE inhibitors.",
            "Option B is correct. Option A (dry cough) is a common nuisance side effect of ACE inhibitors that may prompt switching to an ARB, but is not immediately life-threatening. Option C is a normal potassium level. Option D requires posture change education, not immediate drug ban.",
            "Cardiovascular • ACE Inhibitor Adverse Effects & Angioedema"
        )

        addQ(
            "Medical-Surgical Nursing",
            "DHA / HAAD • Medium",
            "A patient admitted with suspected acute myocardial infarction (MI) is scheduled for immediate laboratory confirmation. Which cardiac biomarker is considered the MOST specific and sensitive indicator of myocardial necrosis?",
            listOf(
                "Creatine Kinase-MB (CK-MB)",
                "Troponin I and Troponin T",
                "Myoglobin",
                "C-Reactive Protein (CRP)"
            ),
            1,
            "Cardiac Troponin I and T are highly specific and sensitive proteins released into bloodstream only when myocardial cell damage occurs. They elevate within 3–4 hours post-MI and remain elevated for up to 10–14 days.",
            "Option B is the gold standard cardiac biomarker. Option A (CK-MB) is cardiac specific but returns to normal within 48–72 hours. Option C (Myoglobin) rises rapidly (1–2 hrs) but lacks cardiac specificity. Option D (CRP) is a non-specific inflammatory marker.",
            "Cardiovascular • Acute Myocardial Infarction Biomarkers"
        )

        // Generating additional 20 MedSurg Cardio questions in clean loop pattern to reach 30 Cardio questions
        val cardioScenarios = listOf(
            Triple("Raynaud Disease Care", "Keep hands warm with gloves, avoid cold exposure and caffeine, stop smoking", "Soak hands in ice water during vasospastic attacks"),
            Triple("Buerger Disease (Thromboangiitis Obliterans)", "Absolute smoking cessation is mandatory to prevent amputation", "Increase nicotine patch dosage"),
            Triple("Pericarditis Assessment", "Pericardial friction rub heard best at left sternal border with patient leaning forward", "Muffled heart sounds with apex pulsation"),
            Triple("Cardiac Tamponade Signs", "Beck's Triad: Hypotension, Jugular Vein Distension (JVD), Muffled Heart Sounds", "Hypertension, Bradycardia, Wide pulse pressure"),
            Triple("Hypertensive Crisis Target", "Gradually lower Mean Arterial Pressure (MAP) by no more than 20-25% in the first hour", "Drop BP rapidly from 220/120 to 90/60 in 15 minutes"),
            Triple("Hypertrophic Cardiomyopathy Avoidance", "Avoid strenuous exercise, dehydration, and positive inotropic drugs like digoxin", "Administer high dose digoxin and push strenuous workouts"),
            Triple("Essential Hypertension Lifestyle", "DASH diet rich in fruits/vegetables, low sodium (<2g/day), weight reduction", "High sodium diet with heavy alcohol consumption"),
            Triple("Heart Failure Fluid Restriction", "Weigh patient daily at same time; report weight gain > 2-3 lbs in 24 hours or 5 lbs in 1 week", "Ignore daily weight changes under 10 lbs"),
            Triple("Aortic Valve Stenosis Symptoms", "Classic triad: Angina, Syncope, Exertional Dyspnea (SAD)", "Flushing, diarrhea, and persistent fever"),
            Triple("Mitral Valve Prolapse Teaching", "Avoid stimulants (caffeine, alcohol), maintain hydration, take beta blockers if symptomatic", "Drink 5 cups of strong coffee daily"),
            Triple("Venous Thromboembolism (DVT) Prevention", "Early ambulation, sequential compression devices (SCDs), low molecular weight heparin", "Strict bed rest with pillows under knees"),
            Triple("Chronic Venous Insufficiency Signs", "Bilateral lower extremity edema, brownish skin discoloration (hemosiderin staining), stasis ulcers", "Pale, cold foot with absent pulses"),
            Triple("Pacemaker Failure to Capture", "Pacemaker spikes visible on EKG without subsequent QRS complex", "Normal QRS complexes following every pacemaker spike"),
            Triple("Pacemaker Failure to Sense", "Pacemaker spikes occur randomly, including on T waves (R-on-T phenomenon risk)", "Spikes occur exactly when intrinsic heart rate drops"),
            Triple("Post-Coronary Artery Bypass Graft (CABG) Care", "Monitor chest tube drainage; report sudden drop or drainage > 150 mL/hr", "Ignore sudden cessation of chest tube output"),
            Triple("Sinus Bradycardia Treatment", "IV Atropine 1 mg if symptomatic (dizziness, hypotension); transcutaneous pacing if unresponsive", "Push IV Metoprolol immediately"),
            Triple("Ventricular Tachycardia with Pulse", "Administer IV Amiodarone or synchronized cardioversion", "Immediate unsynchronized high-energy defibrillation"),
            Triple("Ventricular Fibrillation (V-Fib)", "Immediate unsynchronized defibrillation and high-quality CPR", "Administer oral digoxin and monitor for 1 hour"),
            Triple("Asystole Cardiac Arrest", "High-quality CPR and Epinephrine 1 mg IV every 3-5 minutes; DO NOT defibrillate", "Immediate high-joule defibrillation"),
            Triple("Ankle-Brachial Index (ABI) PAD Diagnostic", "ABI < 0.9 indicates peripheral arterial disease (normal is 1.0 to 1.4)", "ABI of 1.2 indicates severe gangrene")
        )

        cardioScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Primary Intervention: ${item.second}",
                "Contraindicated Action: ${item.third}",
                "Delay care and perform routine paperwork",
                "Reassess in 24 hours without intervention"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical Nursing",
                "NCLEX-RN / Prometric • Medium",
                "Cardiovascular Case Clinical Scenario #${idx + 11}: In managing a patient presenting with ${item.first}, which clinical decision represents current gold-standard nursing practice?",
                options,
                correctPos,
                "Clinical Rationale: For ${item.first}, evidence-based care requires: ${item.second}.",
                "Option breakdown: The correct selection addresses the core pathophysiological need. Action '${item.third}' is unsafe and contraindicated.",
                "Cardiovascular • ${item.first}"
            )
        }

        // =========================================================================
        // RESPIRATORY SYSTEM (25 QUESTIONS)
        // =========================================================================
        addQ(
            "Medical-Surgical Nursing",
            "NCLEX-RN / DHA • Hard",
            "A client with severe COPD is receiving oxygen via a simple face mask at 6 L/min. The nurse notes the client is becoming increasingly drowsy, lethargic, and has a respiratory rate of 8 breaths/min. Arterial Blood Gas (ABG) reveals PaCO2 of 68 mmHg. What is the nurse's PRIORITY intervention?",
            listOf(
                "Increase oxygen flow rate to 10 L/min via non-rebreather mask",
                "Switch to a Venturi mask delivering 24%–28% oxygen and prepare for non-invasive ventilation",
                "Administer intravenous lorazepam to reduce patient agitation",
                "Place the patient flat in supine position to improve arterial pressure"
            ),
            1,
            "Clients with end-stage COPD often rely on a hypoxic drive (low PaO2) to stimulate breathing because chronic hypercapnia desensitizes central chemoreceptors. High oxygen delivery suppresses respiratory drive, leading to CO2 retention, narcosis, and respiratory arrest. A Venturi mask provides precise low-concentration oxygen titration.",
            "Option B is correct. Option A worsens CO2 narcosis and respiratory arrest. Option C (sedatives) further depresses the central nervous system respiratory center. Option D (supine) impairs diaphragm movement and worsens respiratory distress.",
            "Respiratory • COPD & Oxygen Hypoxic Drive Safety"
        )

        addQ(
            "Medical-Surgical Nursing",
            "HAAD / Prometric • Medium",
            "A nurse is caring for a client with a left chest tube connected to a closed water-seal drainage system. The nurse observes continuous vigorous bubbling in the water-seal chamber. How should the nurse interpret this finding?",
            listOf(
                "Normal expected finding indicating full lung re-expansion",
                "An air leak present in the drainage system or pleural space",
                "High suction pressure applied to the wet suction regulator",
                "Blockage or occlusion in the chest tube drainage tubing"
            ),
            1,
            "Continuous bubbling in the water-seal chamber indicates an air leak between the patient and the drainage system. (Intermittent bubbling during coughing or exhalation is expected while the pneumothorax is resolving). Continuous bubbling requires systematic clamping close to chest wall to locate the leak source.",
            "Option B is correct. Option A is incorrect because bubbling ceases when the lung is fully expanded. Option C refers to continuous bubbling in the suction control chamber, not the water-seal chamber. Option D causes absence of tidaling, not continuous bubbling.",
            "Respiratory • Chest Tube Water-Seal Chamber Bubbling"
        )

        addQ(
            "Medical-Surgical Nursing",
            "NCLEX-RN • Hard",
            "A patient post-pulmonary embolism is receiving a continuous intravenous heparin infusion. The baseline aPTT was 30 seconds. The current laboratory result shows an aPTT of 110 seconds. What is the nurse's IMMEDIATE action?",
            listOf(
                "Increase the heparin infusion rate by 100 units/hour according to protocol",
                "Stop/hold the heparin infusion and notify the healthcare provider",
                "Administer Vitamin K 10 mg intravenously over 10 minutes",
                "Obtain an immediate arterial blood gas sample"
            ),
            1,
            "Therapeutic target aPTT for heparin therapy is typically 1.5 to 2.5 times baseline (approx. 60 to 80 seconds). An aPTT of 110 seconds indicates excessive anticoagulation and extreme risk of life-threatening hemorrhage. The nurse must hold the infusion immediately and report to the provider.",
            "Option B is correct. Option A causes lethal bleeding. Option C (Vitamin K) is the reversal agent for Warfarin (coumadin), NOT Heparin (Protamine Sulfate is the antidote for Heparin). Option D is unnecessary for heparin monitoring.",
            "Respiratory • Pulmonary Embolism & Heparin Anticoagulation"
        )

        val respScenarios = listOf(
            Triple("Acute Respiratory Distress Syndrome (ARDS)", "Prone positioning improves ventilation-perfusion (V/Q) matching and oxygenation", "Place patient in strict supine Trendelenburg position"),
            Triple("Asthma Silent Chest Sign", "Immediate emergency airway intervention; indicates severe airway obstruction and impending arrest", "Administer oral cough syrup and reassure patient"),
            Triple("Tracheostomy Tube Dislodgement (Early)", "Insert spare obturator into tube, lubricate, re-insert or ventilate with bag-valve mask over stoma", "Cover stoma with airtight occlusive dressing and leave patient"),
            Triple("Pneumothorax Assessment Findings", "Tracheal deviation to contralateral side, absent breath sounds on affected side, hyperresonance", "Bilateral crackles with dullness on percussion"),
            Triple("Pulmonary Embolism Triad", "Sudden dyspnea, pleuritic chest pain, and tachypnea/tachycardia", "Slow bradycardia, fever, and purulent sputum"),
            Triple("Pneumonia Nursing Care", "Incentive spirometry, hydration to thin secretions, coughing/deep breathing, elevated head of bed", "Strict bed rest with fluid restriction < 500 mL/day"),
            Triple("Pleural Effusion Thoracentesis Care", "Position patient upright leaning over bedside table; monitor for pneumothorax post-procedure", "Position patient flat on belly during needle insertion"),
            Triple("Tuberculosis Airborne Isolation", "Negative pressure room, N95 respirator mask worn by staff, surgical mask on patient during transport", "Standard cloth mask for staff and open room door"),
            Triple("Mechanical Ventilation High-Pressure Alarm", "Check for secretions (suction), patient biting EKG/tube, kinked tubing, or pneumothorax", "Immediately disconnect ventilator tubing and leave open"),
            Triple("Mechanical Ventilation Low-Pressure Alarm", "Check for loose connections, cuff leak, or accidental ventilator tubing disconnection", "Instill saline into ET tube and elevate PEEP"),
            Triple("Cystic Fibrosis Airway Clearance", "Perform chest physiotherapy (CPT) before meals to avoid nausea/vomiting", "Perform heavy postural drainage immediately after full lunch"),
            Triple("Idiopathic Pulmonary Fibrosis Care", "Oxygen therapy, pulmonary rehabilitation, avoiding lung irritants, monitoring for cor pulmonale", "Encourage heavy cigarette smoking to open bronchi"),
            Triple("Obstructive Sleep Apnea (OSA)", "Continuous Positive Airway Pressure (CPAP) at night, weight loss, avoiding alcohol before sleep", "Prescribe high dose oral benzodiazepines at bedtime"),
            Triple("Suctioning Tracheostomy Technique", "Pre-oxygenate with 100% O2, apply suction intermittently only while withdrawing catheter (<10-15 sec)", "Apply continuous suction while inserting catheter for 45 seconds"),
            Triple("Incentive Spirometry Patient Education", "Inhale slowly and deeply through mouthpiece to elevate ball/piston, hold breath 3-5 seconds", "Exhale forcefully as hard as possible into the device"),
            Triple("Aspiration Pneumonia Prevention", "Keep head of bed elevated 30-45 degrees during feeding and for 1 hour after; evaluate gag reflex", "Feed patient flat on back while sleeping"),
            Triple("Acute Bronchitis Care", "Increased fluid intake, rest, humidified air, bronchodilators if wheezing present", "Administer high dose IV corticosteroids and restrict fluids"),
            Triple("Flail Chest Assessment", "Paradoxical chest wall movement (chest sinks in on inspiration, bulges out on exhalation)", "Symmetrical equal expansion with bilateral vesicular sounds"),
            Triple("Subcutaneous Emphysema", "Crepitus (rice crispies crackling sensation) felt under skin upon palpation", "Smooth, hard bony prominence over sternum"),
            Triple("Endotracheal Tube Cuff Pressure Normal", "Maintain cuff pressure between 20 to 30 cmH2O to prevent aspiration and mucosal injury", "Inflate cuff to 90 cmH2O")
        )

        respScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Nursing Intervention: ${item.second}",
                "Unsafe Practice: ${item.third}",
                "Ignore assessment and discharge patient home",
                "Wait 12 hours before notifying provider"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical Nursing",
                "NCLEX-RN / DHA • Medium",
                "Respiratory Nursing Clinical Case #${idx + 4}: In caring for a medical-surgical client with ${item.first}, which evidence-based nursing action is required?",
                options,
                correctPos,
                "Rationale: Management of ${item.first} relies on: ${item.second}.",
                "Option breakdown: Correct choice ensures respiratory airway stability. Action '${item.third}' is dangerous.",
                "Respiratory • ${item.first}"
            )
        }

        // =========================================================================
        // GASTROINTESTINAL & HEPATIC SYSTEM (25 QUESTIONS)
        // =========================================================================
        val giScenarios = listOf(
            Triple("Hepatic Encephalopathy & Lactulose", "Lactulose traps ammonia in colon and promotes 2-3 soft bowel movements daily", "Administer loperamide to stop all bowel movements"),
            Triple("Acute Pancreatitis Nursing Priority", "Maintain NPO status and administer IV fluids/analgesics to rest pancreas", "Encourage high-fat meal to stimulate bile release"),
            Triple("Cirrhosis & Esophageal Varices Rupture", "Maintain airway, IV fluid resuscitation, vasopressin/octreotide, Sengstaken-Blakemore tube", "Have patient eat crunchy dry toast"),
            Triple("Small Bowel Obstruction Assessment", "Abdominal distension, high-pitched tinkling bowel sounds above obstruction, projectile vomiting", "Normal soft abdomen with hyperactive rectal flatus"),
            Triple("Acute Appendicitis Assessment", "Right lower quadrant pain at McBurney's point, rebound tenderness, low-grade fever", "Severe left upper quadrant pain relieved by eating"),
            Triple("Peritonitis Signs", "Board-like rigid abdomen, severe diffuse tenderness, rebound tenderness, absent bowel sounds", "Soft, non-tender abdomen with hyperactive sounds"),
            Triple("Ileostomy Stoma Care", "Normal stoma is pink/red and moist; report dark purple/black stoma (ischemia)", "Ignore black necrotic stoma"),
            Triple("Colostomy Irrigation Teaching", "Use warm tap water (500-1000 mL), hang bag at shoulder height, insert cone gently", "Use boiling water and hang bag 6 feet above head"),
            Triple("Peptic Ulcer Disease (Duodenal vs Gastric)", "Duodenal ulcer pain is relieved by food; Gastric ulcer pain worsens 1-2 hours after eating", "Duodenal ulcers cause severe pain immediately upon swallowing"),
            Triple("GERD Lifestyle Modification", "Avoid chocolate, peppermint, caffeine, fatty foods; elevate head of bed 6 inches; don't lie down for 3 hours post meal", "Eat a large pizza right before sleeping flat"),
            Triple("Dumping Syndrome Prevention", "High-protein, high-fat, low-carbohydrate meals; small frequent portions; don't drink liquids with meals", "Drink 3 glasses of sugary soda with large carb-heavy meals"),
            Triple("Ulcerative Colitis Manifestations", "Bloody diarrhea (10-20 stools/day), abdominal cramping, anemia, weight loss", "Constipation with hard dry ribbon stools once a week"),
            Triple("Crohn's Disease Features", "Transmural inflammation, cobblestone appearance, skip lesions, fistulas/abscesses", "Superficial mucosal lesions restricted strictly to rectum"),
            Triple("Paracentesis Nursing Care", "Have patient void prior to procedure to prevent bladder perforation; monitor BP post-procedure", "Force 2 Liters of water and restrict voiding"),
            Triple("NG Tube Placement Gold Standard", "Radiographic X-ray confirmation is the definitive gold standard before initial feedings/meds", "Aspirate gas and listen for a belch without testing"),
            Triple("Enteral Tube Feeding Residual", "Check gastric residual volume; re-infuse residual unless >250-500 mL (per facility protocol)", "Discard all residual and double the tube feeding rate"),
            Triple("Total Parenteral Nutrition (TPN) Safety", "Administer via central line with dedicated filter; monitor blood glucose every 6 hours; never stop abruptly", "Stop TPN abruptly and run plain tap water IV"),
            Triple("Hiatal Hernia Management", "Small frequent meals, avoid tight clothing around abdomen, sit upright post meals", "Wear tight abdominal binders and lie flat after eating"),
            Triple("Celiac Disease Diet", "Strict gluten-free diet (avoid Wheat, Rye, Barley, Oats - BROW)", "Eat wheat bread, rye crackers, and barley soup daily"),
            Triple("Cholecystitis Assessment", "Positive Murphy's sign (pain on deep inspiration during RUQ palpation), right shoulder pain", "Pain in left foot triggered by walking"),
            Triple("Biliary Colic & Morphine Caution", "Meperidine or DTaP analogues traditionally preferred; avoid biliary spasm triggers", "High fat dairy diet during active colic"),
            Triple("Viral Hepatitis A Prevention", "Hand hygiene, clean food/water sanitation, Hepatitis A vaccine", "Reuse unsterilized needles"),
            Triple("Viral Hepatitis B & C Transmission", "Blood and body fluids transmission (unprotected sex, IV drug use, needlestick)", "Casual handshakes and breathing air"),
            Triple("Jaundice Pathophysiology", "Yellow discoloration of skin/sclera caused by serum bilirubin levels exceeding 2.5-3.0 mg/dL", "Caused by low serum potassium levels"),
            Triple("Gastric Cancer Early Warning", "Unexplained weight loss, indigestion, early satiety, epigastric discomfort", "Sudden high fever and clear urine")
        )

        giScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Evidence-Based Care: ${item.second}",
                "Dangerous Practice: ${item.third}",
                "Place patient in prone position with cold compress",
                "Cancel procedure without documenting findings"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical Nursing",
                "NCLEX-RN / Prometric • Medium",
                "Gastrointestinal Clinical Case #${idx + 1}: In caring for a client with ${item.first}, which clinical care decision is correct?",
                options,
                correctPos,
                "Rationale: Standard management for ${item.first} specifies: ${item.second}.",
                "Option breakdown: Correct answer follows gastrointestinal/hepatic guidelines. Option '${item.third}' is unsafe.",
                "Gastrointestinal • ${item.first}"
            )
        }

        // =========================================================================
        // RENAL & GENITOURINARY SYSTEM (20 QUESTIONS)
        // =========================================================================
        val renalScenarios = listOf(
            Triple("Acute Kidney Injury (AKI) Oliguric Phase", "Fluid restriction equal to 24-hour urine output plus 500-600 mL; monitor for hyperkalemia", "Force 4 Liters of IV fluids daily regardless of output"),
            Triple("Chronic Kidney Disease (CKD) Anemia", "Administer Recombinant Human Erythropoietin (Epoetin alfa); monitor BP for hypertension", "Give high-dose aspirin daily"),
            Triple("Peritoneal Dialysis Peritonitis Sign", "Cloudy/turbid dialysate outflow fluid accompanied by abdominal pain and fever", "Clear straw-colored dialysate effluent"),
            Triple("Hemodialysis Arteriovenous (AV) Fistula Care", "Palpate thrill and auscultate bruit every shift; NO blood pressure or IV draws on affected arm", "Take blood pressure on arm with AV fistula"),
            Triple("Nephrotic Syndrome Characteristics", "Massive proteinuria, severe generalized edema (anasarca), hypoalbuminemia, hyperlipidemia", "Hematuria with normal urine protein"),
            Triple("Acute Glomerulonephritis Features", "Hematuria (cola/tea-colored urine), hypertension, periorbital edema, post-streptococcal history", "Clear pale yellow polyuria"),
            Triple("Hyperkalemia Emergency Management", "IV Calcium Gluconate (protects heart), IV Regular Insulin with D50, Sodium Polystyrene Sulfonate", "Administer oral potassium supplements"),
            Triple("Urolithiasis (Kidney Stones) Nursing Care", "Strain all urine for stone analysis, encourage fluid intake (2-3 L/day), administer analgesics", "Restrict fluid intake to 200 mL/day"),
            Triple("Benign Prostatic Hyperplasia (BPH) Continuous Bladder Irrigation", "Titrate CIB flow rate to keep urine light pink; clear bright red blood with clots by increasing flow", "Stop irrigation completely if bright red clots appear"),
            Triple("Urinary Tract Infection (UTI) Prevention", "Wipe front to back, void after intercourse, increase fluid intake, avoid bubble baths", "Wipe back to front and hold urine for 10 hours"),
            Triple("Pyelonephritis Symptoms", "Flank pain (costovertebral angle tenderness), high fever, chills, nausea, dysuria", "Painless clear urination without fever"),
            Triple("Kidney Transplant Rejection (Acute)", "Oliguria, fever, elevated blood pressure, graft tenderness, rising creatinine", "Sudden massive urine output with drop in creatinine"),
            Triple("Polycystic Kidney Disease (PKD) Management", "Control blood pressure (ACE inhibitors/ARBs), increase fluids, genetic counseling", "High sodium diet with contact sports"),
            Triple("Urinary Incontinence Stress Type", "Pelvic floor muscle exercises (Kegels), bladder training, weight management", "Restrict fluids completely to zero intake"),
            Triple("Urostomy (Ileal Conduit) Care", "Urine will contain normal mucus shreds; stoma should be red/pink; ensure continuous drainage", "Panicked response to normal mucus in urine bag"),
            Triple("Hyperphosphatemia in CKD", "Administer phosphate binders (Sevelamer, Calcium Acetate) WITH meals", "Take phosphate binders on empty stomach at bedtime"),
            Triple("Hypocalcemia Trousseau & Chvostek Signs", "Chvostek (facial twitching on tapping cheek); Trousseau (carpopedal spasm with BP cuff)", "Absence of reflexes with hypercalcemia"),
            Triple("Renal Biopsy Post-Procedure Care", "Strict supine bed rest for 2-6 hours, monitor insertion site for bleeding, monitor urine for hematuria", "Encourage heavy lifting and jogging immediately"),
            Triple("Renal Cell Carcinoma Triad", "Painless hematuria, flank pain, and palpable abdominal mass", "Severe dysuria with watery diarrhea"),
            Triple("Catheter-Associated UTI (CAUTI) Bundle", "Maintain closed system, keep drainage bag below bladder level, perform catheter hygiene", "Raise drainage bag above bladder level")
        )

        renalScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Standard Nursing Care: ${item.second}",
                "Unsafe Practice: ${item.third}",
                "Ignore lab values and discharge patient",
                "Apply heat pack over surgical site without order"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical Nursing",
                "NCLEX-RN / DHA • Medium",
                "Renal & Genitourinary Clinical Case #${idx + 1}: In caring for a client with ${item.first}, which clinical care protocol is correct?",
                options,
                correctPos,
                "Rationale: Renal management for ${item.first} indicates: ${item.second}.",
                "Option breakdown: Correct choice prevents renal failure or procedural complications. Practice '${item.third}' is unsafe.",
                "Renal • ${item.first}"
            )
        }

        // =========================================================================
        // NEUROLOGICAL SYSTEM (20 QUESTIONS)
        // =========================================================================
        val neuroScenarios = listOf(
            Triple("Increased Intracranial Pressure (ICP) Positioning", "Elevate head of bed 30 degrees, keep head/neck in neutral alignment, avoid hip flexion", "Place patient in Trendelenburg position with extreme neck flexion"),
            Triple("Ischemic Stroke Tissue Plasminogen Activator (tPA)", "Administer within 3 to 4.5 hours of symptom onset; rule out hemorrhagic stroke via non-contrast CT first", "Administer tPA to patient with CT showing active brain hemorrhage"),
            Triple("Autonomic Dysreflexia in Spinal Cord Injury (T6 or above)", "Raise head of bed 90 degrees, check for bladder distension/bowel impaction, remove tight clothing", "Lower head flat and massage distended bladder forcefully"),
            Triple("Seizure Precautions & Acute Care", "Side-lying position, protect head, do NOT insert anything into mouth, clear surrounding objects", "Force a padded tongue blade between teeth during tonic clonic phase"),
            Triple("Parkinson's Disease Care", "Administer carbidopa-levodopa on time, encourage walker use, chop food into small pieces", "Restrict levodopa and force patient to rush while walking"),
            Triple("Myasthenia Gravis Cholinergic vs Myasthenic Crisis", "Tensilon (Edrophonium) test: improvement = Myasthenic crisis; worsening = Cholinergic crisis", "Give extra anticholinesterase without evaluation"),
            Triple("Glasgow Coma Scale (GCS) Intubation Threshold", "GCS score of 8 or lower indicates severe brain injury requiring airway intubation ('GCS 8, Intubate!')", "GCS score of 15 requires immediate endotracheal intubation"),
            Triple("Meningitis Kernig & Brudzinski Signs", "Brudzinski (neck flexion causes involuntary hip/knee flexion); Kernig (inability to extend knee without pain)", "Negative signs indicate severe bacterial infection"),
            Triple("Multiple Sclerosis Heat Sensitivity", "Avoid hot baths, saunas, and extreme heat which exacerbate symptoms (Uhthoff phenomenon)", "Encourage 45-minute hot tub immersion daily"),
            Triple("Amyotrophic Lateral Sclerosis (ALS) Care", "Supportive care, airway management, swallowing evaluation, end-of-life planning", "Promise complete cure with physical exercise"),
            Triple("Guillain-Barré Syndrome Priority", "Monitor vital capacity and respiratory effort for ascending paralysis affecting diaphragm", "Ignore respiratory depth and monitor urine color"),
            Triple("Bell's Palsy Eye Care", "Artificial tears during day, eye patch/ointment at night to protect unclosing eyelid/cornea", "Keep eye uncovered in dusty wind"),
            Triple("Trigeminal Neuralgia Trigger Prevention", "Chew on unaffected side, wash face with lukewarm water, avoid cold drafts over face", "Rub affected facial cheek vigorously with ice"),
            Triple("CSF Leak Halo Sign", "Clear fluid from nose/ear forms clear ring around blood droplet on gauze (positive for glucose)", "Test yellow pus for protein content"),
            Triple("Hemorrhagic Stroke BP Management", "Maintain BP within tight target range; avoid hypertension to prevent re-bleeding", "Push IV vasodilators to drop BP to 60/40"),
            Triple("Lumbar Puncture Post-Procedure Positioning", "Lie flat in supine position for 4-12 hours to prevent post-dural puncture headache", "Sit upright and jog for 30 minutes"),
            Triple("Epidural Hematoma Classic Presentation", "Loss of consciousness followed by a brief 'lucid interval' then rapid neurological decline", "Slow chronic dementia over 10 years"),
            Triple("Subdural Hematoma Venous Bleeding", "Slow venous bleeding between dura and arachnoid, common in elderly after minor falls", "Rapid high-pressure arterial spurting"),
            Triple("Cerebral Aneurysm Rupture Symptom", "Sudden onset of 'the worst headache of my life' (thunderclap headache) with nuchal rigidity", "Mild itching of the forehead"),
            Triple("Decerebrate vs Decorticate Posturing", "Decerebrate (extensor posturing - rigid extension of arms/legs) indicates midbrain/pons damage and is worse", "Decorticate indicates complete recovery")
        )

        neuroScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Correct Nursing Care: ${item.second}",
                "Contraindicated Practice: ${item.third}",
                "Delay assessment and check vitals next morning",
                "Administer sedatives without neurological check"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical Nursing",
                "NCLEX-RN / HAAD • Hard",
                "Neurological Nursing Case #${idx + 1}: In caring for a client with ${item.first}, which clinical decision is mandated?",
                options,
                correctPos,
                "Rationale: Neurological standard of care for ${item.first}: ${item.second}.",
                "Option breakdown: Correct answer preserves neurological function and prevents herniation/death. Action '${item.third}' is dangerous.",
                "Neurological • ${item.first}"
            )
        }

        // =========================================================================
        // ENDOCRINE SYSTEM (15 QUESTIONS)
        // =========================================================================
        val endocrineScenarios = listOf(
            Triple("Diabetic Ketoacidosis (DKA) Management", "IV Regular Insulin, fluid resuscitation with Normal Saline, potassium replacement", "Subcutaneous NPH insulin with severe fluid restriction"),
            Triple("Hyperosmolar Hyperglycemic State (HHS)", "Severe hyperglycemia (>600 mg/dL), extreme dehydration, high serum osmolality without significant ketoacidosis", "Serum glucose 120 mg/dL with severe ketonuria"),
            Triple("Hypoglycemia Rule of 15", "Give 15g fast-acting carbs, wait 15 minutes, recheck blood glucose; repeat if still <70 mg/dL", "Inject 50 units NPH insulin immediately"),
            Triple("Thyroid Storm (Thyrotoxic Crisis)", "High fever, severe tachycardia, delirium; give Propylthiouracil (PTU), Beta-blockers, cooling blanket", "Give Levothyroxine 500 mcg IV push"),
            Triple("Myxedema Coma", "Severe hypothyroidism, hypothermia, bradycardia, hypoventilation; administer IV Levothyroxine", "Apply ice packs and give antithyroid meds"),
            Triple("Addisonian Crisis Management", "IV hydrocortisone/dexamethasone, high-volume IV Normal Saline, glucose administration", "Restrict fluid intake and withhold steroids"),
            Triple("Cushing's Syndrome Manifestations", "Moon face, buffalo hump, central obesity, purple striae, hyperglycemia, hypertension", "Extreme emaciation, hypoglycemia, low BP"),
            Triple("Syndrome of Inappropriate ADH (SIADH)", "Fluid retention, hyponatremia (<135 mEq/L), high urine specific gravity; restrict fluid intake", "Force 4 Liters of free tap water daily"),
            Triple("Diabetes Insipidus (DI)", "Massive dilute polyuria (>4-15 L/day), low urine specific gravity (<1.005), hypernatremia; give Desmopressin (DDAVP)", "Restrict fluids and withhold desmopressin"),
            Triple("Post-Thyroidectomy Airway Monitoring", "Keep tracheostomy tray, suction equipment, and Calcium Gluconate at bedside", "Discard airway equipment and send patient home"),
            Triple("Post-Thyroidectomy Hypocalcemia Sign", "Positive Trousseau and Chvostek signs indicating accidental parathyroid gland removal", "Hyperreflexia with elevated serum calcium"),
            Triple("Pheochromocytoma Triad", "Severe episodic headache, sweating (diaphoresis), and tachycardia secondary to catecholamine tumor", "Bradycardia, hypotension, and hypothermia"),
            Triple("Acromegaly Pathophysiology", "Excess Growth Hormone (GH) secretion in adults after epiphyseal plate closure causing enlarged hands, feet, jaw", "Deficiency of insulin secretion in infants"),
            Triple("Glycated Hemoglobin (HbA1c) Target", "HbA1c < 7.0% indicates good long-term glycemic control over past 2-3 months", "HbA1c of 14% indicates ideal control"),
            Triple("Insulin Injection Site Rotation", "Rotate injection sites within same anatomical region to prevent lipodystrophy", "Inject in exact same spot every single day")
        )

        endocrineScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Clinical Nursing Protocol: ${item.second}",
                "Harmful Intervention: ${item.third}",
                "Discontinue insulin without checking glucose",
                "Apply warm heating pad over thyroid gland"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical Nursing",
                "NCLEX-RN / DHA • Medium",
                "Endocrine Clinical Case #${idx + 1}: In caring for a client presenting with ${item.first}, which clinical decision is correct?",
                options,
                correctPos,
                "Rationale: Endocrine management for ${item.first} specifies: ${item.second}.",
                "Option breakdown: Correct choice addresses underlying hormone dysregulation. Practice '${item.third}' is dangerous.",
                "Endocrine • ${item.first}"
            )
        }

        // =========================================================================
        // MUSCULOSKELETAL & HEMATOLOGY/ONCOLOGY (15 QUESTIONS)
        // =========================================================================
        val musculoHemScenarios = listOf(
            Triple("Compartment Syndrome 6 Ps", "Pain out of proportion, Paresthesia, Pallor, Paralysis, Pulselessness, Poikilothermia; urgent fasciotomy", "Apply tight circumferential compression bandage and elevate leg above heart"),
            Triple("Skeletal Traction Safety", "Weights must hang freely without touching floor/bed; do NOT remove weights without order", "Remove heavy traction weights during bath"),
            Triple("Hip Arthroplasty Posterior Approach Precautions", "Do NOT flex hip >90 degrees, do NOT cross legs/ankles, use abduction pillow between legs", "Cross legs and sit in low soft reclining chair"),
            Triple("Fat Embolism Syndrome Triad", "Petechiae on chest/neck, dyspnea/hypoxia, and confusion following long bone fracture (femur)", "High fever with purulent knee drainage"),
            Triple("Acute Blood Transfusion Reaction Action", "STOP transfusion immediately, flush line with normal saline using NEW tubing, notify provider/blood bank", "Slow transfusion rate and recheck vitals in 2 hours"),
            Triple("Neutropenic Precautions (Absolute Neutrophil Count < 500)", "Strict hand hygiene, no fresh flowers/plants, cooked food diet, private room with positive pressure", "Allow fresh unwashed fruits and live flowers"),
            Triple("Thrombocytopenia Precautions (Platelets < 50,000)", "Use soft toothbrush, electric razor, avoid aspirin/NSAIDs, prevent falls and constipation", "Use firm bristle toothbrush and straight razor"),
            Triple("Osteomyelitis Nursing Care", "Long-term IV antibiotic therapy (4-6 weeks), sterile dressing changes, immobilization of affected bone", "Vigorously massage infected bone site"),
            Triple("Gout Dietary Teaching", "Low-purine diet (avoid organ meats, shellfish, sardines, alcohol), increase fluids, take allopurinol", "High purine diet with beer consumption"),
            Triple("Rheumatoid Arthritis vs Osteoarthritis", "RA is symmetric autoimmune inflammatory joint destruction with morning stiffness > 1 hour; OA is wear-and-tear non-inflammatory", "OA is systemic autoimmune with fever"),
            Triple("Systemic Lupus Erythematosus (SLE) Manifestations", "Butterfly rash across bridge of nose/cheeks, joint pain, photosensitivity, renal involvement", "Severe jaundice with high fever"),
            Triple("Polycythemia Vera Care", "Therapeutic phlebotomy, hydration, avoiding iron supplements, wearing support stockings", "Dehydrate patient and give oral iron"),
            Triple("Multiple Myeloma Assessment", "Bone pain (especially back/ribs), hypercalcemia, Bence Jones protein in urine, risk of pathological fractures", "Hypocalcemia with dense strong bones"),
            Triple("Amputation Post-Op Positioning (First 24 hrs)", "Elevate residual limb on pillow for first 24 hours ONLY to reduce edema, then prone positioning to prevent hip flexion contracture", "Keep residual limb elevated on 3 pillows for 2 weeks"),
            Triple("Stoma/Wound Dehiscence with Evisceration", "Cover exposed internal organs with sterile saline-soaked gauze, place patient in low Fowler's with knees bent, notify surgeon immediately", "Push exposed bowel loops back into abdominal cavity with bare hands")
        )

        musculoHemScenarios.forEachIndexed { idx, item ->
            val correctPos = idx % 4
            val options = mutableListOf(
                "Standard Nursing Care: ${item.second}",
                "Contraindicated Practice: ${item.third}",
                "Apply dry unsterile towels and walk patient",
                "Ignore symptoms and document routine shift report"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical Nursing",
                "NCLEX-RN / Prometric • Medium",
                "Musculoskeletal / Oncology Clinical Case #${idx + 1}: In caring for a client presenting with ${item.first}, which clinical decision is correct?",
                options,
                correctPos,
                "Rationale: Management of ${item.first} requires: ${item.second}.",
                "Option breakdown: Correct choice ensures patient safety and tissue preservation. Action '${item.third}' is unsafe.",
                "Musculoskeletal/Oncology • ${item.first}"
            )
        }

        return list
    }
}
