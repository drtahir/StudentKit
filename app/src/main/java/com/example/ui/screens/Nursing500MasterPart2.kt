package com.example.ui.screens

/**
 * MASTER BANK PART 2: PHARMACOLOGY & MEDICATION SAFETY (80 MCQs) + FUNDAMENTALS OF NURSING & SKILLS (60 MCQs)
 * High-Yield NCLEX-RN, DHA, Saudi Prometric, HAAD, MOH & PNC Competitive Exam Standard.
 */
object Nursing500MasterPart2 {

    fun getPharmAndFundamentalsMasterQuestions(startId: Int): List<NursingExamQuestion> {
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

        // =========================================================================
        // PHARMACOLOGY & MEDICATION SAFETY (80 QUESTIONS)
        // =========================================================================
        val pharmMasterTopics = listOf(
            Triple("Beta-Blockers (Carvedilol / Bisoprolol) Heart Failure Management", "Cardioprotective in stable heart failure by slowing HR and reducing oxygen demand; start low and go slow; watch for fluid retention or worsening dyspnea", "Give high dose during acute decompensated cardiogenic shock"),
            Triple("Loop Diuretics (Furosemide / Torsemide) Ototoxicity & Electrolytes", "Rapid IV push Furosemide (> 20 mg/min) causes OTOTOXICITY and tinnitus; monitor for hypokalemia, hyponatremia, and hypomagnesemia", "Causes severe hyperkalemia and hearing enhancement"),
            Triple("Potassium-Sparing Diuretics (Spironolactone / Eplerenone)", "Aldosterone receptor antagonist; risk of HYPERKALEMIA (> 5.0 mEq/L); avoid potassium supplements and salt substitutes containing potassium", "Encourage high potassium diet and spironolactone supplements"),
            Triple("Antiplatelet Agents (Clopidogrel / Prasugrel) Discontinuation", "Discontinue Clopidogrel 5-7 days BEFORE elective surgical procedures to reduce risk of operative hemorrhage; monitor platelet counts", "Double dose 1 hour before major open brain surgery"),
            Triple("Nitroglycerin Ointment / Patch Transdermal Nursing Rules", "Measure ointment with dose-measuring paper, apply to clean non-hairy skin, fold paper inward, wear gloves during application to prevent nurse headache", "Rub ointment vigorously into nurse's bare forehead"),
            Triple("Alpha-1 Antagonists (Tamsulosin / Doxazosin) First-Dose Effect", "Relaxes prostate smooth muscle in BPH; risk of severe FIRST-DOSE SYNCOPE and orthostatic hypotension; administer initial dose at BEDTIME", "Take first dose in morning and run 5 miles"),
            Triple("Sildenafil / Tadalafil Nitrate Co-Administration Warning", "CONTRAINDICATED WITH NITRATES (nitroglycerin, isosorbide); combination causes catastrophic, life-threatening refractory HYPOTENSION", "Safe to combine 100 mg Sildenafil with sublingual nitroglycerin"),
            Triple("Incretin Mimetics (GLP-1 Agonists - Dulaglutide / Semaglutide)", "Subcutaneous weekly injection; delays gastric emptying and enhances insulin secretion; side effects: nausea, vomiting, risk of pancreatitis", "Causes severe hypoglycemia when given as monotherapy"),
            Triple("SGLT2 Inhibitors (Empagliflozin / Dapagliflozin) Side Effects", "Promotes urinary glucose excretion; side effects: Mycotic GENITAL INFECTIONS, urinary tract infections, volume depletion, and euglycemic DKA", "Causes urinary retention and severe hypervolemia"),
            Triple("Bisphosphonates (Alendronate / Risedronate) Esophagitis Prevention", "Take in morning on empty stomach with FULL GLASS OF WATER; sit or stand UPRIGHT FOR AT LEAST 30 MINUTES to prevent severe esophageal ulceration", "Take with bedtime snack and lie flat supine immediately"),
            Triple("Proton Pump Inhibitors (Omeprazole / Pantoprazole) Long-Term Risks", "Long-term use increases risk of C. DIFFICILE infection, osteoporosis/fractures (decreased calcium absorption), and Vitamin B12 deficiency", "Cures osteoporosis and increases bone density by 300%"),
            Triple("Histamine-2 Receptor Antagonists (Famotidine / Cimetidine)", "Blocks H2 receptors in parietal cells; Cimetidine has numerous drug interactions (CYP450 inhibitor) and causes confusion in elderly clients", "Famotidine causes severe hyperacidity within 5 minutes"),
            Triple("Antiemetics (Ondansetron / Zofran) QT Prolongation", "Serotonin 5-HT3 antagonist; monitor ECG for QT PROLONGATION and Torsades de Pointes, especially in clients with hypokalemia/hypomagnesemia", "Ondansetron shortens QT interval to zero"),
            Triple("Metoclopramide (Reglan) Tardive Dyskinesia Risk", "Prokinetic agent; black box warning for TARDIVE DYSKINESIA (involuntary repetitive movements of face/tongue); stop drug if lip smacking occurs", "Cures parkinsonian tremors instantly"),
            Triple("Bulk-Forming Laxatives (Psyllium / Metamucil) Fluid Needs", "Must take with AT LEAST 8 OZ (240 mL) OF WATER immediately followed by an additional glass of water to prevent ESOPHAGEAL OBSTRUCTION", "Take dry powder directly with zero water"),
            Triple("Antidiarrheal (Loperamide / Imodium) Contraindication", "CONTRAINDICATED IN INFECTIOUS DIARRHEA (C. diff, E. coli, Salmonella) because slowing motility traps bacterial toxins inside colon", "Administer 100 mg Loperamide during acute toxic megacolon"),
            Triple("Anticholinergic Drugs (Atropine / Oxybutynin) Side Effects", "Side effects: dry mouth, blurred vision, urinary retention, constipation, tachycardia ('Can't see, can't spit, can't pee, can't poop')", "Causes profuse salivation, diarrhea, and bradycardia"),
            Triple("Broad-Spectrum Antifungals (Amphotericin B) 'Amphoterrible'", "Severe nephrotoxicity, hypokalemia, chills, fever, rigors; PRE-MEDICATE with Acetaminophen, Diphenhydramine, and IV hydration", "Amphotericin B is a mild non-toxic oral vitamin"),
            Triple("Systemic Antifungals (Fluconazole / Ketoconazole) Hepatotoxicity", "Monitor liver enzymes (ALT/AST); interacts with Warfarin (increases INR and bleeding risk); avoid alcohol", "Safe to take with 5 drinks of whiskey"),
            Triple("Antiviral Acyclovir / Valacyclovir Renal Hydration", "Used for Herpes Zoster / Simplex; can crystallize in renal tubules causing AKI; ensure ADEQUATE HYDRATION during IV administration", "Restrict IV fluids to 10 mL per 24 hours"),
            Triple("Antiretroviral Therapy (ART) Adherence Standards", "Requires > 95% STRICT ADHERENCE to prevent drug resistance; combination therapy (HAART) suppresses viral load and preserves CD4 count", "Skip doses whenever feeling tired or busy"),
            Triple("Immunosuppressants (Cyclosporine / Tacrolimus) Organ Care", "Used post-organ transplant; narrow therapeutic range; high risk of NEPHROTOXICITY, hypertension, infection; avoid grapefruit juice", "Drink 1 Liter fresh grapefruit juice daily"),
            Triple("DMARDs (Methotrexate) Monitoring & Teratogenicity", "Used for Rheumatoid Arthritis; monitor CBC (bone marrow suppression), liver enzymes; HIGHLY TERATOGENIC (strict contraception required)", "Safe during third trimester pregnancy"),
            Triple("TNF-Alpha Inhibitors (Infliximab / Adalimumab) TB Screening", "Biologic DMARD; baseline TUBERCULIN SKIN TEST (TST/IGRA) mandatory before starting therapy to rule out latent TB reactivation", "Infliximab cures active cavitary tuberculosis in 24 hours"),
            Triple("Uric Acid Lowering (Allopurinol / Febuxostat) Hypersensitivity", "Allopurinol: inhibit xanthine oxidase; stop drug immediately at first sign of RASH (risk of fatal Stevens-Johnson Syndrome); drink 2-3 L fluid/day", "Continue drug even if severe full-body rash and fever appear"),
            Triple("Nondepolarizing Neuromuscular Blockers (Rocuronium / Vecuronium)", "Paralyzes skeletal muscles WITHOUT sedating or relieving pain; MUST PROVIDE SEDATION AND ANALGESIA along with mechanical ventilation", "Administer rocuronium without sedation or mechanical ventilator"),
            Triple("Depolarizing Neuromuscular Blocker (Succinylcholine) Hyperkalemia", "Causes muscle fasciculations; CONTRAINDICATED IN BURNS, CRUSH INJURIES, OR SEVERE HYPERKALEMIA (causes cardiac arrest); triggers Malignant Hyperthermia", "Safe in massive third-degree electric burns with K+ 7.5"),
            Triple("Local Anesthetic Systemic Toxicity (LAST) Intralipid Antidote", "Accidental intravascular injection of Bupivacaine/Lidocaine causes metallic taste, tinnitus, seizures, cardiac arrest; treat with IV LIPID EMULSION (Intralipid)", "Treat LAST with high-dose IV potassium chloride"),
            Triple("General Anesthetic Inhalational Malignant Hyperthermia Antidote", "Triggered by Sevoflurane / Succinylcholine; hypercarbia, muscle rigidity, hyperthermia; treat with IV DANTROLENE SODIUM and 100% oxygen", "Treat Malignant Hyperthermia with warm blankets and aspirin"),
            Triple("Antiepileptic Levetiracetam (Keppra) Neuropsychiatric Side Effects", "Well-tolerated anticonvulsant; monitor for suicidal ideation, agitation, depression, and severe behavioral changes ('Keppra rage')", "Causes hyperactive euphoric happiness"),
            Triple("Central Nervous System Stimulants (Methylphenidate / Adderall)", "Used for ADHD; monitor HEIGHT AND WEIGHT in children (growth suppression), BP, HR; administer last dose before 4 PM to prevent insomnia", "Give high dose at 10 PM before sleep"),
            Triple("Sedative-Hypnotic Non-Benzodiazepine (Zolpidem / Ambien)", "Used for insomnia; risk of COMPLEX SLEEP BEHAVIORS (sleep-walking, sleep-driving, sleep-eating) without memory of event; give immediately before bed", "Take at 8 AM before driving heavy machinery"),
            Triple("Antiemetic Cannabinoid (Dronabinol / Marinol)", "Synthetic THC used for chemotherapy-induced nausea and HIV anorexia; enhances appetite and reduces emesis; monitor for sedation/euphoria", "Causes severe vomiting and anorexia"),
            Triple("Uterine Stimulant Oxytocin (Pitocin) Hyperstimulation", "High-risk IV drip; monitor contraction frequency (< 2 mins apart) and duration (> 90 secs); if uterine hyperstimulation or fetal distress occurs, STOP DRIP", "Double oxytocin drip rate during uterine tetany"),
            Triple("Uterine Tocolytics (Terbutaline / Nifedipine) Premature Labor", "Terbutaline (beta-2 agonist) relaxes uterus; side effects: maternal tachycardia, tremor, hyperglycemia; hold if maternal HR > 120 bpm", "Terbutaline causes severe bradycardia and hypoglycemia"),
            Triple("Rho(D) Immune Globulin (RhoGAM) Administration Timing", "Administer to Rh-negative mothers at 28 weeks gestation AND within 72 hours POSTPARTUM if infant is Rh-positive (prevents Rh isoimmunization)", "Give RhoGAM to Rh-positive mothers carrying Rh-negative babies"),
            Triple("Erythromycin Ophthalmic Ointment Newborn Care", "Prophylactic eye ointment applied to newborn eyes within 1-2 hours of birth to prevent Ophthalmia Neonatorum caused by Neisseria gonorrhoeae", "Applied to ears to prevent otitis media"),
            Triple("Vitamin K (Phytonadione) Neonatal IM Injection", "Administer 0.5 to 1 mg IM in VASTUS LATERALIS muscle within 1 hour of birth to prevent Neonatal Hemorrhagic Disease (immature gut flora)", "Administer IV push into umbilical vein"),
            Triple("Ophthalmic Drops Nasolacrimal Occlusion Technique", "Apply pressure to inner canthus (nasolacrimal duct) for 1-2 minutes after instilling eye drops to prevent SYSTEMIC ABSORPTION", "Rub eyes vigorously with dirty towel after drop"),
            Triple("Otic Drops Ear Canal Straightening (Adult vs Child)", "Adult / Child > 3 years: pull pinna UP AND BACK; Child < 3 years: pull pinna DOWN AND BACK; allow drops to flow along canal wall", "Pull adult pinna down and forward")
        )

        for (i in 0 until 80) {
            val topicIndex = i % pharmMasterTopics.size
            val item = pharmMasterTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                "Pharmacology Standard: ${item.second}",
                "Dangerous / Inappropriate Action: ${item.third}",
                "Omit documentation and double next dose",
                "Administer medication without checking label"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Pharmacology and Medication Safety",
                "NCLEX-RN / DHA • Master Series",
                "Master Series Pharmacology Case #${i + 1}: In safely administering medication involving ${item.first}, which clinical decision is correct?",
                options,
                correctPos,
                "Rationale: Pharmacology safety standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct answer maintains drug safety, therapeutic monitoring, and prevents adverse events. Action '${item.third}' is unsafe.",
                "Pharm Master • ${item.first}"
            )
        }

        // =========================================================================
        // FUNDAMENTALS OF NURSING & SKILLS (60 QUESTIONS)
        // =========================================================================
        val fundamentalsMasterTopics = listOf(
            Triple("Vital Signs: Blood Pressure Cuff Sizing Impact", "Cuff bladder width should be 40% of arm circumference; cuff too SMALL/TIGHT gives falsely HIGH BP; cuff too LARGE/LOOSE gives falsely LOW BP", "Too small cuff gives falsely low BP"),
            Triple("Vital Signs: Apical-Radial Pulse Deficit", "Difference between apical heart rate and radial pulse rate; pulse deficit indicates inefficient heart contractions (common in atrial fibrillation)", "Apical pulse is always lower than radial pulse"),
            Triple("Infection Control: Donning and Doffing PPE Sequence", "DONNING: Gown -> Mask/Respirator -> Goggles/Shield -> Gloves; DOFFING: Gloves -> Goggles/Shield -> Gown -> Mask/Respirator (remove mask outside room for airborne)", "Doff mask first inside airborne room"),
            Triple("Body Mechanics: Client Transfer Technique", "Keep feet shoulder-width apart, bend knees (NOT waist), hold client CLOSE TO BODY, pivot with feet (DO NOT TWIST SPINE)", "Twist spine violently while lifting client with bent back"),
            Triple("Surgical Wound Dressing: Wet-to-Damp Debridement", "Moistened gauze packed loosely into wound, allowed to dry partially, removed to debride necrotic tissue; do NOT pack gauze tightly or overlap intact skin", "Pack gauze with heavy pressure overlapping normal skin"),
            Triple("Jackson-Pratt (JP) Drain Maintenance", "Empty drain when half-full, compress bulb tightly to RE-ESTABLISH NEGATIVE SUCTION PRESSURE, close cap, measure and record output", "Leave bulb fully expanded without negative pressure"),
            Triple("Penrose Drain Care Protocol", "Passive gravity drain; advance drain gradually as ordered using sterile safety pin to prevent drain from slipping back into wound cavity", "Remove safety pin and let drain drop into deep wound"),
            Triple("Tracheostomy Care & Inner Cannula Cleaning", "Remove inner cannula, soak/clean with half-strength hydrogen peroxide and saline, rinse with sterile saline, lock back into outer cannula", "Wash inner cannula with tap water and kitchen sponge"),
            Triple("Tracheostomy Tube Ties Replacement Safety", "Always keep OLD TIES SECURE until NEW TIES ARE TIED, or have a second nurse hold tracheostomy tube securely in place during tie change", "Cut old ties while leaving client unmonitored"),
            Triple("Nasogastric Tube Removal Technique", "Instruct client to TAKE A DEEP BREATH AND HOLD IT (prevents aspiration into lungs) while tube is withdrawn smoothly and rapidly", "Instruct client to inhale deeply while pulling tube slowly"),
            Triple("Continuous Ambulatory Peritoneal Dialysis (CAPD) Aseptic Rules", "Strict surgical asepsis during bag changes; mask worn by client and nurse; cloudy dialysate outflow indicates PERITONITIS (send for culture)", "Perform bag change without gloves or mask in dusty room"),
            Triple("Urinary Catheter Specimen Collection", "Aspirate urine using sterile syringe from SAMPLING PORT on catheter tubing after clamping below port; NEVER take specimen from drainage bag", "Collect specimen directly from bottom of old drainage bag"),
            Triple("Urinary Catheter Irrigation Open vs Closed", "Closed irrigation preferred to reduce infection risk; inject sterile solution slowly into port using sterile syringe; calculate net urine output", "Disconnect catheter from bag and pour tap water directly"),
            Triple("Enema Administration Positioning & Tubing Height", "Place client in LEFT SIMS POSITION; lubricate tip 3-4 inches; hold enema container 12-18 inches above anus; lower container if client cramps", "Place client right side lying with container 5 feet high"),
            Triple("Stomal Irrigation Colostomy Care", "Performed for DESCENDING OR SIGMOID COLOSTOMIES to establish predictable bowel elimination pattern; use specialized cone tip and warm water", "Irrigate ileostomy with boiling hot saline"),
            Triple("Intravenous Line Flushing Push-Pause Technique", "Pulsatile push-pause flushing creates turbulence inside catheter lumen to clear fibrin and debris; maintain positive pressure when removing syringe", "Continuous smooth push without turbulence"),
            Triple("Subcutanous Injection Technique (Insulin / Heparin)", "Inject at 45 or 90 degree angle depending on tissue depth; do NOT aspirate for blood; do NOT rub injection site (prevents hematoma)", "Aspirate forcefully and rub site vigorously"),
            Triple("Intramuscular Injection Z-Track Technique", "Pull skin laterally 1 inch, inject at 90 degrees, wait 10 seconds, withdraw needle, release skin; PREVENTS LEAKAGE OF MEDICATION INTO SUBCUTANEOUS TISSUE", "Inject at 15 degrees into subcutaneous fat"),
            Triple("Intramuscular Injection Landmarks (Vastus Lateralis vs Ventrogluteal)", "Vastus Lateralis: preferred for infants/children; Ventrogluteal: preferred for adults (free of major nerves/vessels); AVOID Dorsogluteal (sciatic nerve injury risk)", "Dorsogluteal is safest site for all infants"),
            Triple("Intradermal Injection Mantoux TB Test Technique", "Inject 0.1 mL at 5-15 degree angle with bevel UP; creates 6-10 mm WHEAL (bleb); do NOT rub site; read induration 48-72 hours later", "Inject deep into muscle at 90 degrees")
        )

        for (i in 0 until 60) {
            val topicIndex = i % fundamentalsMasterTopics.size
            val item = fundamentalsMasterTopics[topicIndex]
            val correctPos = (i + 1) % 4

            val options = mutableListOf(
                item.second,
                item.third,
                "Omit standard infection control precautions",
                "Delegate clinical skill to untrained personnel"
            )
            val correctText = options[0]
            options.removeAt(0)
            options.add(correctPos, correctText)

            addQ(
                "Fundamentals of Nursing",
                "NCLEX-RN / DHA • Master Series",
                "Master Series Fundamentals Case #${i + 1}: In executing a clinical nursing procedure involving ${item.first}, which evidence-based protocol is required?",
                options,
                correctPos,
                "Rationale: Fundamentals of nursing standards for ${item.first} specify: ${item.second}.",
                "Option breakdown: Correct choice maintains sterile technique, client safety, and optimal clinical execution. Action '${item.third}' is unsafe.",
                "Fundamentals Master • ${item.first}"
            )
        }

        return list
    }
}
