package com.drtahir.studentkit.ui.screens

/**
 * MASTERY BANK PART 1: SPECIALIZED MED-SURG, DIAGNOSTIC PROCEDURES & PERIOPERATIVE CARE (150 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500MasteryPart1 {

    fun getMedSurgMasteryQuestions(startId: Int): List<NursingExamQuestion> {
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

        val masteryTopicsPart1 = listOf(
            Triple("Cardiovascular: Infective Endocarditis Duke Criteria & Blood Cultures", "Blood cultures MUST BE DRAWN FROM 2 SEPARATE VENIPUNCTURE SITES at least 30-60 mins apart BEFORE starting empirical antibiotic therapy; monitor for petechiae, Roth spots, and splinter hemorrhages", "Draw blood cultures from a single IV line 5 seconds after starting IV Vancomycin"),
            Triple("Cardiovascular: Peripheral Arterial Disease Doppler Ultrasonography & Walking Protocol", "Encourage progressive walking exercise program (walk until pain occurs, rest until pain resolves, then resume); inspect feet daily for breakdown; avoid heating pads and tight socks", "Apply hot heating pads directly to ischemic toes to promote circulation"),
            Triple("Cardiovascular: Coronary Artery Bypass Graft (CABG) Leg Donor Site Care", "Monitor saphenous vein donor site for edema, infection, and poor wound healing; elevate leg when sitting in chair; avoid crossing legs or wearing tight elastic bands", "Soak leg donor site in hot tub water for 2 hours daily"),
            Triple("Cardiovascular: Pacemaker Spike Assessment & Failure to Capture / Sense", "Failure to capture: pacemaker spike is present but NOT followed by a QRS complex or P wave; check battery, turn client to left side, check cable connections, adjust threshold", "Failure to capture means pacemaker is pacing at 300 bpm automatically"),
            Triple("Respiratory: Bronchoscopy Pre- and Post-Procedure Gag Reflex Care", "NPO 6-8 hours pre-procedure; post-procedure: MAINTAIN NPO STATUS UNTIL GAG REFLEX RETURNS (test with tongue blade or small water sip) to prevent aspiration pneumonia", "Feed client solid steak dinner immediately upon waking from bronchoscopy"),
            Triple("Respiratory: Thoracentesis Positioning & Post-Procedure Pneumothorax Watch", "Position client sitting upright leaning forward over bedside table (orthopneic); monitor post-procedure for PNEUMOTHORAX (sudden dyspnea, asymmetric chest expansion, tachypnea)", "Place client flat supine during thoracentesis needle insertion"),
            Triple("Respiratory: Pulmonary Tuberculosis (TB) Negative Pressure Isolation", "Airborne precautions: AIRBORNE INFECTION ISOLATION ROOM (AIIR) with negative pressure (6-12 air exchanges/hr), N95 RESPIRATOR for staff; client wears surgical mask when transported", "Place TB client in positive pressure room with doors wide open"),
            Triple("Respiratory: Acute Pulmonary Edema 'MAD DOG' Protocol", "Acute decompensated HF; pink frothy sputum, orthopnea, crackles; protocol: Morphine (decreases preload/anxiety), Aminophylline, Digitalis, Nitroglycerin, Oxygen, Diuretics (Furosemide)", "Lay client flat supine and infuse 3 Liters Normal Saline bolus"),
            Triple("Neurological: Lumbar Puncture Post-Procedure Headache Prevention", "Client placed in lateral recumbent position for procedure; post-LP: KEEP CLIENT FLAT SUPINE FOR 4-8 HOURS to prevent post-dural puncture headache from CSF leak; encourage fluids", "Instruct client to jump up and down aggressively post lumbar puncture"),
            Triple("Neurological: Stroke Dysphagia Assessment & Swallowing Precautions", "NPO until bedside dysphagia screen completed by speech therapist; swallow precautions: SIT UPRIGHT 90 DEGREES, tuck chin down to chest when swallowing, use thickened liquids", "Head tilted back with thin liquids poured rapidly down throat"),
            Triple("Neurological: Cranial Nerve Assessment CN VII (Facial) vs CN XII (Hypoglossal)", "CN VII (Facial): test facial symmetry (smile, frown, puff cheeks, raise eyebrows); CN XII (Hypoglossal): test tongue movement (stick out tongue midline without deviation)", "CN VII is tested by flashing penlight into eyes for pupil constriction"),
            Triple("Neurological: Autonomic Dysreflexia Initial Nursing Priority", "Noxious stimulus below spinal lesion (T6 or higher) causes severe hypertension, bradycardia, headache; FIRST ACTION: ELEVATE HOB TO 90 DEGREES, then check bladder for urinary retention", "Place client in Trendelenburg position and apply ice to chest"),
            Triple("Gastrointestinal: Esophagogastroduodenoscopy (EGD) Post-Care & Perforation Signs", "NPO pre-procedure; post-EGD: verify gag reflex return before oral intake; monitor for PERFORATION SIGNS (sudden severe epigastric/chest pain, fever, abdominal rigidity, subcutaneous emphysema)", "Immediately feed solid food without testing swallowing function"),
            Triple("Gastrointestinal: Colostomy Stoma Assessment & Bag Care", "Normal stoma: PINK TO BEEFY RED and moist; pale stoma indicates anemia; PURPLE OR BLACK STOMA INDICATES ISCHEMIA/NECROSIS (immediate emergency); measure pouch opening 1/8 inch larger than stoma", "Black necrotic stoma is normal and expected for 6 months"),
            Triple("Gastrointestinal: TPN (Total Parenteral Nutrition) Central Line & Blood Glucose", "Infuse via CENTRAL LINE with dedicated filter; change tubing/bag every 24 hours; check blood glucose q 6 hrs (hyperglycemia risk); IF BAG RUNS OUT, INFUSE 10% DEXTROSE IN WATER (D10W)", "Abruptly stop TPN without dextrose replacement when bag runs dry"),
            Triple("Gastrointestinal: Liver Biopsy Positioning Pre- and Post-Procedure", "Pre-procedure: check PT/INR/platelets (bleeding risk), hold breath on expiration during puncture; Post-procedure: POSITION CLIENT ON RIGHT SIDE FOR 2-4 HOURS to compress puncture site", "Position client on left side with legs elevated above head"),
            Triple("Endocrine: Post-Thyroidectomy Tracheal Compression & Hypocalcemia", "Keep TRACHEOSTOMY TRAY AND CALCIUM GLUCONATE AT BEDSIDE; monitor for stridor, neck swelling, hoarseness, and hypocalcemia (Trousseau/Chvostek signs from parathyroid removal)", "Discard tracheostomy tray and ignore stridor sound"),
            Triple("Endocrine: Hypophysectomy (Transsphenoidal) Post-Op CSF Leak Watch", "Avoid coughing, sneezing, blowing nose, or bending forward; monitor nasal drainage for CSF LEAK (halo sign on dressing, positive for glucose); clear nasal drainage must be reported immediately", "Encourage vigorous nose blowing and coughing every 10 minutes"),
            Triple("Endocrine: Radioactive Iodine (I-131) Therapy Radiation Precautions", "For hyperthyroidism/thyroid cancer; radiation safety: private room, flush toilet 2-3 times after use, use separate eating utensils, avoid close contact with pregnant women/children for 3-7 days", "Share bath towels and sleep in same bed with pregnant spouse"),
            Triple("Renal: Continuous Ambulatory Peritoneal Dialysis (CAPD) Aseptic Technique", "Strict aseptic technique during bag exchanges; warm dialysate solution to body temperature using WARMER (never microwave); effluent drainage should be CLEAR STRAW-COLORED", "Heat dialysate solution in microwave oven on high for 5 minutes"),
            Triple("Renal: Cystoscopy Post-Procedure Expected vs Complication Findings", "Expected: mild burning on urination, pink-tinged urine for 1-2 days; COMPLICATIONS: bright red gross hematuria, blood clots, inability to void, fever, chills, severe flank pain", "Bright red gross hematuria with large clots is normal for 4 weeks"),
            Triple("Renal: Renal Calculi (Nephrolithiasis) Strain All Urine & Flank Pain", "Strain ALL urine through filter to catch stones for laboratory analysis; encourage fluid intake 2.5-3 L/day; analgesics for severe renal colic pain; ambulation promotes stone passage", "Throw away all voided urine without filtering or checking for stones"),
            Triple("Musculoskeletal: Cast Care & Compartment Syndrome Signs", "Keep cast dry; do NOT insert objects under cast to scratch; perform neurovascular checks (color, warmth, capillary refill, sensation, movement); report severe unrelieved pain immediately", "Stick long metal knitting needle deep inside cast to scratch itch"),
            Triple("Musculoskeletal: Hip Joint Aspiration & Septic Arthritis", "Septic arthritis is a medical emergency; purulent joint fluid, severe pain with motion, fever, elevated WBC/ESR; immediate joint aspiration, IV antibiotics, surgical drainage", "Septic arthritis is treated with immediate weight-bearing exercise"),
            Triple("Hematology: Blood Transfusion Reaction Protocols", "1st 15 mins: stay at bedside, infuse slowly (2 mL/min); IF REACTION OCCURS: STOP TRANSFUSION IMMEDIATELY, flush IV with Normal Saline via NEW TUBING, notify MD/blood bank, send bag/tubing to lab", "In a hemolytic reaction, speed up blood transfusion rate to finish bag"),
            Triple("Oncology: Extravasation of Vesicant Chemotherapy", "If vesicant leaks into tissue (pain, swelling, burning): STOP INFUSION IMMEDIATELY, leave catheter in place to aspirate residual drug, inject antidote if available, elevate extremity", "Increase IV speed to flush vesicant through tissue quickly"),
            Triple("Oncology: Neutropenic Precautions & Absolute Neutrophil Count (ANC)", "ANC < 500/mm3 indicates severe infection risk; private room, strict hand hygiene, NO FRESH FLOWERS/PLANTS, no raw fruits/veggies, report fever > 100.4°F (38.0°C) IMMEDIATELY", "Place fresh soil plants and unwashed raw strawberries in neutropenic room"),
            Triple("Dermatology: Herpes Zoster (Shingles) Lesions & Airborne Precautions", "Reactivation of varicella-zoster virus; painful unilateral vesicular rash along dermatome; DISSEMINATED SHINGLES REQUIRES AIRBORNE AND CONTACT PRECAUTIONS until lesions crust over", "Disseminated shingles client can share room with immunocompromised child"),
            Triple("Perioperative: Pre-Op Surgical Safety Checklist & Universal Protocol", "Perform TIME-OUT immediately before incision (verify client identity, surgical site, procedure, consent, equipment); verify NPO status and allergies; mark surgical site with surgeon signature", "Skip time-out verification to save 30 seconds in operating room"),
            Triple("Perioperative: Post-Op Malignant Hyperthermia Acute Crisis Care", "Triggered by volatile anesthetics/succinylcholine; sudden unexplained hypercapnia (rising end-tidal CO2 - EARLIEST SIGN), muscle rigidity, hyperthermia; STOP ANESTHETICS, GIVE DANTROLENE IV", "Administer hot blankets and increase volatile anesthetic gas")
        )

        for (i in 0 until 150) {
            val topicIndex = i % masteryTopicsPart1.size
            val item = masteryTopicsPart1[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Omit vital signs recording and leave unit unattended",
                "Delegate specialized nursing assessment to unlicensed assistive personnel"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Medical-Surgical & Diagnostic Nursing",
                "NCLEX-RN / DHA • Mastery Series",
                "Mastery Series Med-Surg Case #${i + 1}: In providing specialized care or managing a diagnostic/perioperative procedure involving ${item.first}, which clinical decision represents gold-standard practice?",
                options,
                correctPos,
                "Rationale: Specialized medical-surgical mastery protocols for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice prevents fatal complications, maintains surgical safety, and ensures rapid clinical stabilization. Action '${item.third}' is improper.",
                "Med-Surg Mastery • ${item.first}"
            )
        }

        return list
    }
}
