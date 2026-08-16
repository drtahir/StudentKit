package com.drtahir.studentkit.ui.screens

/**
 * KHYBER PAKHTUNKHWA (KP) BSN SEMESTER 4 QUESTION BANK
 * 50+ Questions per subject (250+ total questions)
 * Subjects:
 * 1. Adult Health Nursing II (Med-Surg II) - 50 Qs
 * 2. Pathophysiology II - 50 Qs
 * 3. Pharmacology II - 50 Qs
 * 4. Health Assessment II - 50 Qs
 * 5. Developmental Psychology - 50 Qs
 */
object KpSemester4QuestionBank {

    fun getQuestions(startId: Int): List<KpSemesterQuestion> {
        var idCounter = startId
        val questions = mutableListOf<KpSemesterQuestion>()

        fun addSubjectQs(
            sem: Int,
            subj: String,
            ref: String,
            rawList: List<Triple<String, List<String>, Int>>
        ) {
            for (item in rawList) {
                val correctOpt = item.second[item.third]
                questions.add(
                    KpSemesterQuestion(
                        id = idCounter++,
                        semesterNumber = sem,
                        subjectName = subj,
                        question = item.first,
                        options = item.second,
                        correctIndex = item.third,
                        explanation = "Correct answer: $correctOpt. Aligned with PNC / KMU Semester 4 curriculum.",
                        reference = ref
                    )
                )
            }
        }

        // ==========================================
        // 1. ADULT HEALTH NURSING II (MED-SURG II) - 50 Qs
        // ==========================================
        val ahn2 = listOf(
            Triple("When managing a patient with increased Intracranial Pressure (ICP), the nurse should elevate the head of the bed to:", listOf("Flat (0 degrees)", "15-30 degrees", "60-90 degrees", "90 degrees with legs elevated"), 1),
            Triple("Which classic triad (Cushing's Triad) indicates late-stage elevated ICP?", listOf("Tachycardia, hypotension, tachypnea", "Bradycardia, systolic hypertension with widening pulse pressure, irregular respirations", "Tachycardia, hypertension, dyspnea", "Bradycardia, hypotension, hypothermia"), 1),
            Triple("A Glasgow Coma Scale (GCS) score of 7 or lower generally indicates:", listOf("Mild brain injury", "Moderate brain injury", "Severe brain injury / Coma", "Normal neurological function"), 2),
            Triple("Which sign is elicited by flexing the patient's neck and observing involuntary flexion of the hips and knees in meningitis?", listOf("Kernig's sign", "Brudzinski's sign", "Chvostek's sign", "Trousseau's sign"), 1),
            Triple("In ischemic stroke management, tissue plasminogen activator (tPA) must ideally be administered within how many hours of symptom onset?", listOf("1 hour", "3 to 4.5 hours", "12 hours", "24 hours"), 1),
            Triple("Which electrolyte imbalance is the primary cause of cardiac arrhythmias in Acute Kidney Injury (AKI)?", listOf("Hyponatremia", "Hyperkalemia", "Hypocalcemia", "Hypermagnesemia"), 1),
            Triple("What is the most accurate indicator of fluid balance in a renal failure patient?", listOf("Daily weight measurement", "Skin turgor assessment", "Measuring intake and output", "24-hour urine dipstick"), 0),
            Triple("In Chronic Kidney Disease (CKD), phosphate binders should be administered:", listOf("1 hour before meals", "With or immediately after meals", "At bedtime on an empty stomach", "Only during hemodialysis"), 1),
            Triple("The Parkland formula for fluid resuscitation in burn patients uses which crystalloid solution?", listOf("0.9% Normal Saline", "Lactated Ringer's (LR)", "5% Dextrose in Water (D5W)", "3% Hypertonic Saline"), 1),
            Triple("According to the Rule of Nines, a burn involving the entire anterior trunk accounts for what percentage of total body surface area (TBSA)?", listOf("9%", "18%", "27%", "36%"), 1),
            Triple("In spinal cord injury, autonomic dysreflexia typically occurs in injuries at or above which spinal level?", listOf("C3", "T6", "L1", "S2"), 1),
            Triple("What is the priority intervention for a patient experiencing Autonomic Dysreflexia?", listOf("Place patient supine", "Elevate head of bed to 90 degrees and remove noxious stimuli", "Administer IV atropine", "Apply cold compress to neck"), 1),
            Triple("In a patient with a fractured femur, sudden dyspnea, confusion, and petechiae over the chest suggest:", listOf("Pulmonary embolism", "Fat embolism syndrome", "Pneumothorax", "Compartment syndrome"), 1),
            Triple("The hallmark symptom of Compartment Syndrome is:", listOf("Mild numbness", "Severe, deep, throbbing pain unrelieved by analgesics and aggravated by passive stretch", "Warm pink skin", "Hyperactive reflexes"), 1),
            Triple("A patient with Guillain-Barré Syndrome (GBS) must be monitored closely for:", listOf("Renal failure", "Ascending paralysis affecting respiratory muscles", "Hyperglycemia", "Liver cirrhosis"), 1),
            Triple("In Myasthenia Gravis, an Improvement in muscle strength following Tensilon (Edrophonium) test confirms:", listOf("Cholinergic crisis", "Myasthenic crisis", "Addisonian crisis", "Thyroid storm"), 1),
            Triple("Which nursing measure prevents contractures in a hemiplegic stroke patient?", listOf("Keeping the limb in adduction", "Proper positioning and passive range-of-motion (ROM) exercises", "Immobilizing joints in flexion", "High-dose sedatives"), 1),
            Triple("An early sign of renal transplant rejection is:", listOf("Hypotension", "Fever, oliguria, graft tenderness, and elevated serum creatinine", "Polyuria", "Weight loss"), 1),
            Triple("When caring for a patient with peritoneal dialysis, cloudy dialysate outflow indicates:", listOf("Normal finding", "Peritonitis", "Bladder perforation", "Hypokalemia"), 1),
            Triple("Which diagnostic test is definitive for diagnosing Leukemia?", listOf("Complete Blood Count (CBC)", "Bone marrow aspiration and biopsy", "Serum ferritin", "Chest X-ray"), 1),
            Triple("For a patient undergoing hemodialysis via AV fistula, the nurse should check for vascular patency by:", listOf("Measuring blood pressure on that arm", "Palpating a thrill and auscultating a bruit", "Performing Allen test", "Injecting heparin"), 1),
            Triple("In multiple trauma, the initial primary survey follows the order of:", listOf("A-B-C-D-E (Airway, Breathing, Circulation, Disability, Exposure)", "D-C-B-A-E", "B-A-C-E-D", "C-A-B-D-E"), 0),
            Triple("A client with continuous bladder irrigation (CBI) post-transurethral resection of prostate (TURP) exhibits bright red blood with clots. The nurse should:", listOf("Stop irrigation immediately", "Increase irrigation rate to prevent clot retention and flush catheter if obstructed", "Remove Foley catheter", "Administer diuretics"), 1),
            Triple("Pain in Multiple Sclerosis is often exacerbated by:", listOf("Cold temperatures", "Heat and fever (Uhthoff's phenomenon)", "Bed rest", "High protein diet"), 1),
            Triple("Which position is recommended post-lumbar puncture to reduce risk of headache?", listOf("High Fowler's", "Prone or supine flat for 4 to 8 hours", "Trendelenburg", "Side-lying with knees flexed to chest"), 1),
            Triple("In acute pancreatitis, Turner's sign refers to ecchymosis located in:", listOf("Periumbilical region", "Flank area", "Lower extremities", "Upper chest"), 1),
            Triple("Cullen's sign (bluish discoloration around the umbilicus) indicates:", listOf("Intraperitoneal hemorrhage", "Acute appendicitis", "Gallbladder perforation", "Renal calculus"), 0),
            Triple("Which intervention reduces risk of ventilator-associated pneumonia (VAP)?", listOf("Keeping HOB flat", "Elevating HOB 30-45 degrees and providing regular oral hygiene with chlorhexidine", "Changing ventilator tubing every 2 hours", "Routine endotracheal suctioning every 15 minutes"), 1),
            Triple("In a patient with traction for a femur fracture, weights should be:", listOf("Resting on the floor", "Hanging freely without touching the bed or floor", "Removed every 4 hours for skin care", "Doubled at night"), 1),
            Triple("The Parkland formula calculates 24-hour total fluid volume as:", listOf("2 mL x kg x %TBSA", "4 mL x kg x %TBSA burn", "6 mL x kg x %TBSA", "10 mL x kg x %TBSA"), 1),
            Triple("Half of the Parkland formula calculated volume is administered during the first:", listOf("4 hours", "8 hours", "12 hours", "16 hours"), 1),
            Triple("Which urinary finding is expected in acute glomerulonephritis?", listOf("Clear watery urine", "Cola-colored / tea-colored urine with hematuria and proteinuria", "Large volumes of dilute urine", "Foul-smelling purulent urine"), 1),
            Triple("In Parkinson's disease, the primary neurochemical deficiency is:", listOf("Acetylcholine", "Dopamine in the substantia nigra", "Serotonin", "GABA"), 1),
            Triple("Carbidopa-Levodopa mechanism of action in Parkinsonism is:", listOf("Carbidopa inhibits peripheral breakdown of Levodopa, allowing more Levodopa to cross blood-brain barrier", "Levodopa blocks acetylcholine receptors", "Carbidopa stimulates dopamine reuptake", "Direct dopamine antagonist"), 0),
            Triple("Which diet is recommended for a patient with Chronic Kidney Disease NOT on dialysis?", listOf("High protein, high potassium", "Low protein, restricted sodium, potassium, and phosphorus", "High sodium, low calorie", "High fat, high protein"), 1),
            Triple("Trousseau's sign (carpopedal spasm when BP cuff is inflated) indicates:", listOf("Hypercalcemia", "Hypocalcemia", "Hyperkalemia", "Hyponatremia"), 1),
            Triple("Chvostek's sign (facial twitching upon tapping facial nerve) indicates:", listOf("Hypocalcemia", "Hypercalcemia", "Hypokalemia", "Hypernatremia"), 0),
            Triple("A chest tube bubbling continuously in the water-seal chamber indicates:", listOf("Normal lung expansion", "An air leak in the system or patient's pleural space", "High suction pressure", "System blockage"), 1),
            Triple("If a chest tube becomes accidentally disconnected from the drainage unit, the nurse's immediate action is to:", listOf("Clamp the tube near chest wall", "Submerge the end of the chest tube in a bottle of sterile saline/water", "Cover with dry gauze", "Reattach immediately without cleaning"), 1),
            Triple("In status epilepticus, the drug of choice for immediate seizure cessation is:", listOf("Oral Phenytoin", "IV Diazepam or Lorazepam", "Subcutaneous Insulin", "IV Acetaminophen"), 1),
            Triple("Which cranial nerve damage causes facial drooping and inability to close the eye (Bell's Palsy)?", listOf("Cranial Nerve V (Trigeminal)", "Cranial Nerve VII (Facial)", "Cranial Nerve IX (Glossopharyngeal)", "Cranial Nerve XII (Hypoglossal)"), 1),
            Triple("A patient with Amyotrophic Lateral Sclerosis (ALS) retains intact:", listOf("Motor neuron function", "Cognitive function and sensory perception", "Sphincter control in end-stage", "Diaphragmatic endurance"), 1),
            Triple("For a patient with a pelvic fracture, the nurse must assess closely for signs of:", listOf("Bladder or urethral injury and retroperitoneal hemorrhage", "Cardiopulmonary bypass", "Hyperthyroidism", "Otitis media"), 0),
            Triple("In Osteomyelitis, the most common causative organism is:", listOf("Escherichia coli", "Staphylococcus aureus", "Pseudomonas aeruginosa", "Streptococcus pneumoniae"), 1),
            Triple("Which diagnostic laboratory value is elevated in Gout?", listOf("Serum bilirubin", "Serum uric acid", "Serum amylase", "Blood urea nitrogen"), 1),
            Triple("Colchicine is administered in acute gouty arthritis to:", listOf("Decrease uric acid synthesis", "Inhibit leukotriene-mediated inflammation in joints", "Increase renal uric acid excretion", "Dissolve tophi"), 1),
            Triple("Allopurinol mechanism of action in chronic gout management is:", listOf("Xanthine oxidase inhibition to reduce uric acid production", "Promoting renal clearance of sodium", "Blocking calcium channels", "Binding histamine"), 0),
            Triple("In Systemic Lupus Erythematosus (SLE), a characteristic cutaneous finding is:", listOf("Target lesion", "Butterfly rash across the bridge of nose and cheeks", "Jaundice", "Spider angioma"), 1),
            Triple("In a severe burn patient, curled ulcer (Stress Ulcer) in stomach is prevented by administering:", listOf("Proton pump inhibitors (PPIs) / H2 receptor antagonists", "NSAIDs", "Potassium supplements", "Steroids"), 0),
            Triple("When evaluating a patient with a head injury, rhinorrhea or otorrhea positive for glucose indicates:", listOf("Sinus infection", "Cerebrospinal fluid (CSF) leak from basilar skull fracture", "Nasal polyps", "Normal mucus discharge"), 1)
        )
        addSubjectQs(4, "Adult Health Nursing II (Med-Surg II)", "KMU AHN-641 / Brunner & Suddarth Med-Surg Nursing", ahn2)

        // ==========================================
        // 2. PATHOPHYSIOLOGY II - 50 Qs
        // ==========================================
        val pat2 = listOf(
            Triple("Glomerulonephritis is primarily caused by:", listOf("Direct bacterial invasion of renal parenchyma", "Type III immune-complex deposition following Group A Streptococcal infection", "IgE-mediated immediate hypersensitivity", "Direct trauma"), 1),
            Triple("In Nephrotic Syndrome, the hallmark triad consists of:", listOf("Massive proteinuria, hypoalbuminemia, and generalized edema", "Hematuria, hypertension, oliguria", "Fever, flank pain, dysuria", "Glycosuria, ketonuria, polyuria"), 0),
            Triple("The pathogenesis of Multiple Sclerosis involves:", listOf("Loss of acetylcholine receptors at neuromuscular junction", "Autoimmune demyelination of central nervous system (CNS) axons", "Degeneration of dopamine neurons in substantia nigra", "Cerebral amyloid plaque deposition"), 1),
            Triple("In Myasthenia Gravis, autoantibodies target and destroy:", listOf("Voltage-gated calcium channels", "Nicotinic Acetylcholine (ACh) receptors at the neuromuscular junction", "Myelin sheath of peripheral nerves", "Beta-2 adrenergic receptors"), 1),
            Triple("Parkinson's disease pathology is characterized cellularly by presence of:", listOf("Neurofibrillary tangles", "Lewy bodies composed of alpha-synuclein", "Aschoff bodies", "Reed-Sternberg cells"), 1),
            Triple("The main pathophysiology of Alzheimer's Disease includes:", listOf("Extracellular beta-amyloid plaques and intracellular neurofibrillary tangles", "Demyelination of peripheral nerves", "Loss of upper motor neurons only", "Ischemic necrosis of basal ganglia"), 0),
            Triple("Aplastic Anemia is characterized pathophysiologically by:", listOf("Hemolysis of RBCs in spleen", "Pancytopenia resulting from bone marrow failure / aplasia", "Vitamin B12 deficiency", "Defective hemoglobin S synthesis"), 1),
            Triple("Pernicious Anemia results from a lack of Intrinsic Factor, which leads to malabsorption of:", listOf("Iron", "Vitamin B12 (Cobalamin)", "Folic Acid", "Vitamin C"), 1),
            Triple("Sickle Cell Anemia is caused by a point mutation substituting valine for glutamic acid in the:", listOf("Alpha-globin chain", "Beta-globin chain of hemoglobin", "Platelet membrane", "Fibrinogen molecule"), 1),
            Triple("Sickle cell crisis and sickling of RBCs are precipitated by:", listOf("Hyperoxia", "Hypoxia, acidosis, dehydration, and cold exposure", "Alkalosis", "High fluid intake"), 1),
            Triple("In Disseminated Intravascular Coagulation (DIC), the fundamental paradox is:", listOf("Uncontrolled clotting and systemic bleeding occurring simultaneously", "Excessive red blood cell production", "Isolated hypercoagulability without hemorrhage", "Platelet count over 1,000,000/uL"), 0),
            Triple("Hemophilia A is an X-linked recessive bleeding disorder caused by deficiency of:", listOf("Factor VII", "Factor VIII", "Factor IX", "Factor XI"), 1),
            Triple("Hemophilia B (Christmas Disease) is caused by deficiency of:", listOf("Factor VIII", "Factor IX", "Factor X", "Factor XII"), 1),
            Triple("In Hypovolemic Shock, the initial compensatory mechanism involves:", listOf("Parasympathetic activation", "Sympathetic Nervous System (SNS) activation causing tachycardia and vasoconstriction", "Decreased renin release", "Vasodilation"), 1),
            Triple("Cardiogenic shock is characterized by:", listOf("Massive systemic vasodilation", "Inability of the heart to pump adequate blood despite normal intravascular volume", "Loss of sympathetic tone", "Severe allergic reaction"), 1),
            Triple("Septic shock differs from hypovolemic shock in the early stage by presenting with:", listOf("Cold clammy extremities", "Warm flushed skin and high cardiac output (warm shock)", "Severe bradycardia", "Hypothermia only"), 1),
            Triple("Anaphylactic shock is mediated primarily by:", listOf("IgG antibodies", "IgE-mediated release of histamine from mast cells and basophils", "Cell-mediated T-cell response", "Immune complex deposition"), 1),
            Triple("Neurogenic shock results from loss of sympathetic vascular tone, classically presenting with:", listOf("Tachycardia and hypertension", "Hypotension and bradycardia with warm dry skin", "Severe fever and rigors", "High systemic vascular resistance"), 1),
            Triple("Acute Respiratory Distress Syndrome (ARDS) pathophysiology features:", listOf("Increased pulmonary capillary permeability leading to non-cardiogenic pulmonary edema and refractory hypoxemia", "Bronchospasm only", "Hypoventilation from narcotic overdose", "Pulmonary embolism"), 0),
            Triple("In Osteoarthritis, the primary pathological process is:", listOf("Systemic autoimmune joint inflammation", "Progressive loss and degradation of articular cartilage", "Uric acid crystal deposition", "Bacterial infection of synovial fluid"), 1),
            Triple("Rheumatoid Arthritis pathology is characterized by formation of abnormal synovial tissue called:", listOf("Pannus", "Tophi", "Osteophyte", "Callus"), 0),
            Triple("Osteoporosis is characterized pathophysiologically by:", listOf("Impaired mineralization of bone matrix due to Vitamin D deficiency", "Bone resorption exceeding bone formation, leading to decreased bone mineral density", "Excessive bone mass accumulation", "Subperiosteal abscess"), 1),
            Triple("Osteomalacia / Rickets is caused by deficiency of:", listOf("Calcium only", "Vitamin D, leading to unmineralized bone matrix", "Vitamin C", "Parathyroid hormone"), 1),
            Triple("In Acute Renal Failure, prerenal etiology is caused by:", listOf("Renal tubular necrosis", "Hypoperfusion of the kidney (e.g. hypovolemia, shock, heart failure)", "Kidney stones obstructing ureter", "Glomerulonephritis"), 1),
            Triple("Intrarenal AKI is commonly triggered by:", listOf("Prostatic hypertrophy", "Acute Tubular Necrosis (ATN) secondary to ischemia or nephrotoxic agents", "Dehydration", "Renal artery stenosis"), 1),
            Triple("Postrenal AKI is caused by:", listOf("Glomerular basement membrane injury", "Obstruction of urinary outflow (e.g. BPH, bilateral renal calculi)", "Severe blood loss", "Aminoglycoside toxicity"), 1),
            Triple("In Benign Prostatic Hyperplasia (BPH), nodular hyperplasia occurs primarily in the:", listOf("Peripheral zone of prostate", "Transition zone surrounding the urethra", "Testicular parenchyma", "Seminal vesicles"), 1),
            Triple("Prostate Cancer most commonly originates in which zone of the prostate?", listOf("Transition zone", "Peripheral zone", "Central zone", "Anterior fibromuscular stroma"), 1),
            Triple("Endometriosis is characterized pathophysiologically by:", listOf("Infection of the fallopian tubes", "Presence of functioning endometrial tissue outside the uterine cavity", "Uterine fibroid development", "Ovarian cyst formation"), 1),
            Triple("Polycystic Ovary Syndrome (PCOS) core hormonal abnormality includes:", listOf("Hypoinsulinemia and low LH", "Hyperinsulinemia, insulin resistance, and elevated androgen levels", "High progesterone", "Low estrogen"), 1),
            Triple("In acute pancreatitis, autodigestion of pancreatic tissue is initiated by premature activation of:", listOf("Amylase", "Trypsinogen into trypsin", "Lipase", "Insulin"), 1),
            Triple("In Cirrhosis, portal hypertension leads to collateral circulation development manifesting as:", listOf("Esophageal varices, caput medusae, and hemorrhoids", "Peptic ulcer disease", "Renal artery stenosis", "Pulmonary fibrosis"), 0),
            Triple("Hepatic Encephalopathy pathophysiology involves accumulation of which toxic substance in the blood?", listOf("Bilirubin", "Ammonia (NH3)", "Uric acid", "Urea"), 1),
            Triple("In Jaundice, unconjugated (indirect) hyperbilirubinemia is typical of:", listOf("Biliary obstruction", "Hemolytic anemia / excessive RBC breakdown", "Gallstones", "Pancreatic head tumor"), 1),
            Triple("Conjugated (direct) hyperbilirubinemia occurs primarily in:", listOf("Hemolysis", "Biliary tract obstruction or hepatocellular disease", "Gilbert's syndrome", "Glucose-6-phosphate dehydrogenase deficiency"), 1),
            Triple("In Crohn's disease, granulomatous lesions extend through:", listOf("Mucosa only", "Transmural (all layers of intestinal wall)", "Serosa only", "Submucosa only"), 1),
            Triple("In Celiac Disease, ingestion of gluten triggers immune-mediated damage to:", listOf("Gastric parietal cells", "Small intestinal villi causing villous atrophy and malabsorption", "Colonic haustra", "Esophageal mucosa"), 1),
            Triple("In Type 1 Diabetes Mellitus, the fundamental defect is:", listOf("Insulin resistance in peripheral tissues", "Autoimmune destruction of pancreatic beta cells leading to absolute insulin deficiency", "Glucagon deficiency", "Impaired renal glucose reabsorption"), 1),
            Triple("In Type 2 Diabetes Mellitus, the primary pathophysiology involves:", listOf("Absolute lack of insulin", "Peripheral insulin resistance combined with progressive secretory defect of beta cells", "Anti-insulin antibodies", "Destruction of alpha cells"), 1),
            Triple("Diabetic Ketoacidosis (DKA) is characterized by hyperketonemia, metabolic acidosis, and hyperglycemia due to:", listOf("Excess insulin", "Severe insulin deficiency promoting lipolysis and free fatty acid oxidation", "Glucagon suppression", "Renal failure"), 1),
            Triple("Hyperosmolar Hyperglycemic State (HHS) differs from DKA because HHS features:", listOf("Severe ketoacidosis", "Extremely high blood glucose (>600 mg/dL) with minimal or no ketoacidosis", "Kussmaul breathing", "Fruity breath odor"), 1),
            Triple("In Cushing's Syndrome, clinical features result from chronic excess of:", listOf("Aldosterone", "Cortisol / Glucocorticoids", "Thyroid hormone", "Growth hormone"), 1),
            Triple("Addison's Disease (Primary Adrenal Insufficiency) pathophysiology involves deficiency of:", listOf("Cortisol and Aldosterone due to adrenal cortical destruction", "Thyroxine", "Parathyroid hormone", "Epinephrine"), 0),
            Triple("Grave's Disease hyperthyroidism is caused by autoantibodies that:", listOf("Destroy thyroid follicular cells", "Stimulate TSH receptors (Thyroid Stimulating Immunoglobulins)", "Block T4 to T3 conversion", "Inhibit iodine uptake"), 1),
            Triple("Hashimoto's Thyroiditis is an autoimmune disorder leading to:", listOf("Hyperthyroidism", "Hypothyroidism due to lymphocytic destruction of thyroid gland", "Thyroid storm", "Hypoparathyroidism"), 1),
            Triple("Pheochromocytoma is a tumor of the adrenal medulla secreting excessive:", listOf("Cortisol", "Catecholamines (Epinephrine and Norepinephrine)", "Aldosterone", "Renin"), 1),
            Triple("In Acromegaly, hypersecretion of Growth Hormone occurs:", listOf("Before epiphyseal plate closure in childhood", "After epiphyseal plate closure in adulthood", "During fetal development", "In old age only"), 1),
            Triple("Gigantism occurs when Growth Hormone hypersecretion occurs:", listOf("In adulthood", "In children prior to epiphyseal fusion", "During pregnancy", "In postmenopausal women"), 1),
            Triple("Diabetes Insipidus (Neurogenic) is caused by deficiency of:", listOf("Insulin", "Antidiuretic Hormone (ADH / Vasopressin)", "Aldosterone", "Oxytocin"), 1),
            Triple("Syndrome of Inappropriate Antidiuretic Hormone (SIADH) results in:", listOf("Profuse polyuria", "Water retention, hyponatremia, and concentrated urine", "Hypernatremia and dehydration", "Hyperglycemia"), 1)
        )
        addSubjectQs(4, "Pathophysiology II", "KMU PAT-642 / Porth's Pathophysiology", pat2)

        // ==========================================
        // 3. PHARMACOLOGY II - 50 Qs
        // ==========================================
        val pha2 = listOf(
            Triple("Opioid analgesics like Morphine act primarily as agonists at which opioid receptor subtype?", listOf("Kappa receptors", "Mu (µ) receptors", "Delta receptors", "Sigma receptors"), 1),
            Triple("The classic triad of Opioid Overdose consists of:", listOf("Hypertension, tachycardia, mydriasis", "Coma / CNS depression, respiratory depression, and pinpoint pupils (miosis)", "Fever, seizures, diarrhea", "Agitation, tachypnea, dilated pupils"), 1),
            Triple("Which drug is the specific antagonist used to reverse Opioid-induced respiratory depression?", listOf("Flumazenil", "Naloxone (Narcan)", "Protamine Sulfate", "Atropine"), 1),
            Triple("Benzodiazepine overdose reversal agent is:", listOf("Naloxone", "Flumazenil", "Physostigmine", "Pralidoxime"), 1),
            Triple("Loop diuretics such as Furosemide (Lasix) act by inhibiting the Na+/K+/2Cl- cotransporter in the:", listOf("Proximal convoluted tubule", "Thick ascending limb of Loop of Henle", "Distal convoluted tubule", "Collecting duct"), 1),
            Triple("A major adverse effect of Furosemide administration requiring routine monitoring is:", listOf("Hyperkalemia", "Hypokalemia and ototoxicity", "Hypercalcemia", "Hypertension"), 1),
            Triple("Spironolactone is classified as a:", listOf("Loop diuretic", "Thiazide diuretic", "Potassium-sparing diuretic (Aldosterone antagonist)", "Osmotic diuretic"), 2),
            Triple("Mannitol is an osmotic diuretic primarily indicated for reducing:", listOf("Systemic blood pressure", "Intracranial Pressure (ICP) and Intraocular Pressure (IOP)", "Pulmonary edema in heart failure", "Serum potassium"), 1),
            Triple("Thiazide diuretics (e.g. Hydrochlorothiazide) exert their effect in the:", listOf("Loop of Henle", "Early distal convoluted tubule", "Glomerulus", "Collecting tubule"), 1),
            Triple("Rapid-acting insulin (e.g. Insulin Lispro, Aspart) onset of action is approximately:", listOf("15 minutes", "30 to 60 minutes", "2 hours", "4 hours"), 0),
            Triple("Regular Insulin (Short-acting) is unique because it is the only insulin that can be administered:", listOf("Subcutaneously only", "Intravenously (IV)", "Intramuscularly only", "Orally"), 1),
            Triple("Intermediate-acting insulin (NPH) peak effect occurs at approximately:", listOf("1 to 2 hours", "4 to 12 hours", "18 to 24 hours", "24 to 36 hours"), 1),
            Triple("Long-acting insulin analogs like Insulin Glargine (Lantus) are characterized by:", listOf("Sharp peak at 2 hours", "Peakless 24-hour baseline plateau", "Onset in 5 minutes", "3-day duration"), 1),
            Triple("Metformin (Biguanide) first-line mechanism of action in Type 2 Diabetes is:", listOf("Stimulating pancreatic insulin secretion", "Decreasing hepatic gluconeogenesis and increasing peripheral insulin sensitivity", "Slowing carbohydrate digestion in gut", "Increasing renal glucose excretion"), 1),
            Triple("A rare but life-threatening black box risk associated with Metformin therapy is:", listOf("Hypoglycemia", "Lactic Acidosis", "Agranulocytosis", "Pulmonary fibrosis"), 1),
            Triple("Sulfonylureas (e.g. Glimepiride, Gliclazide) primary mechanism of action is:", listOf("Inhibiting alpha-glucosidase", "Stimulating insulin release from pancreatic beta cells", "Enhancing renal glucose excretion", "Decreasing glucagon"), 1),
            Triple("SGLT2 inhibitors (e.g. Empagliflozin, Dapagliflozin) lower blood glucose by:", listOf("Inhibiting sodium-glucose cotransporter 2 in proximal renal tubules to increase urinary glucose excretion", "Increasing insulin sensitivity", "Stimulating GLP-1", "Slowing gastric emptying"), 0),
            Triple("Epinephrine administration during cardiac arrest resuscitation works primarily via:", listOf("Alpha-1 mediated vasoconstriction to improve coronary perfusion and Beta-1 cardiac stimulation", "Beta-2 mediated bronchodilation only", "Direct vagal inhibition", "Histamine blockade"), 0),
            Triple("Atropine Sulfate is indicated in emergency cardiac care for:", listOf("Symptomatic Bradycardia", "Supraventricular Tachycardia", "Ventricular Fibrillation", "Hypertensive emergency"), 0),
            Triple("Amiodarone is an antiarrhythmic drug requiring baseline and periodic evaluation of:", listOf("Thyroid function and chest X-ray / pulmonary function tests", "Renal clearance only", "Serum amylase", "Blood glucose"), 0),
            Triple("Digoxin toxicity visual sign classically involves:", listOf("Tunnel vision", "Xanthopsia (yellow-green halos around light objects)", "Loss of peripheral vision", "Diplopia"), 1),
            Triple("The specific antidote for Digoxin toxicity is:", listOf("Protamine Sulfate", "Digoxin Immune Fab (Digibind)", "Vitamin K", "Deferoxamine"), 1),
            Triple("Prior to administering Digoxin, the nurse must assess the apical pulse for 1 full minute and hold the dose if HR is:", listOf("Below 60 beats per minute in adults", "Below 90 beats per minute in adults", "Above 100 beats per minute", "Above 120 beats per minute"), 0),
            Triple("Systemic Corticosteroid therapy (e.g. Prednisone) must never be stopped abruptly because of risk of:", listOf("Thyroid storm", "Acute Adrenal Crisis (Addisonian Crisis)", "Hypertensive crisis", "Hyperkalemia"), 1),
            Triple("Long-term high-dose corticosteroid therapy adverse effects include:", listOf("Weight loss and hypotension", "Cushingoid features, hyperglycemia, osteoporosis, and increased infection susceptibility", "Hypoglycemia and hyperkalemia", "Alopecia and diarrhea"), 1),
            Triple("Levothyroxine (Synthroid) patient teaching regarding administration:", listOf("Take with high-fiber evening meal", "Take in the morning on an empty stomach 30 to 60 minutes before breakfast with full glass of water", "Take with milk at bedtime", "Take with antacids"), 1),
            Triple("Propylthiouracil (PTU) and Methimazole are antithyroid medications used in hyperthyroidism to:", listOf("Inhibit thyroid hormone synthesis", "Destroy thyroid gland tissue", "Increase T4 to T3 conversion", "Stimulate TSH secretion"), 0),
            Triple("Which drug is administered intravenously during status epilepticus to quickly arrest seizure activity?", listOf("Phenobarbital", "Lorazepam or Diazepam", "Valproic acid", "Carbamazepine"), 1),
            Triple("Phenytoin (Dilantin) chronic administration requires special oral care education due to risk of:", listOf("Oral candidiasis", "Gingival hyperplasia", "Tooth discoloration", "Dry mouth only"), 1),
            Triple("Valproic Acid therapy requires monitoring of liver function tests because of risk of:", listOf("Hepatotoxicity and pancreatitis", "Nephrotic syndrome", "Aplastic anemia", "Pulmonary edema"), 0),
            Triple("Haloperidol (First-generation antipsychotic) high extrapyramidal side effect (EPS) risk includes:", listOf("Acute dystonia, akathisia, and tardive dyskinesia", "Agranulocytosis", "Hypertension", "Hyperthyroidism"), 0),
            Triple("Neuroleptic Malignant Syndrome (NMS) secondary to antipsychotics manifests as:", listOf("Hypothermia and flaccidity", "Severe muscle rigidity, hyperthermia, autonomic instability, and altered mental status", "Hypotension and rash", "Hyperglycemia"), 1),
            Triple("Selective Serotonin Reuptake Inhibitors (SSRIs) like Fluoxetine carry a Black Box warning for:", listOf("Hepatotoxicity", "Increased risk of suicidal thoughts and behaviors in children, adolescents, and young adults", "Renal failure", "Aplastic anemia"), 1),
            Triple("Serotonin Syndrome symptoms resulting from SSRI overdosage or interaction include:", listOf("Hypothermia and hyporeflexia", "Agitation, hyperreflexia, tremor, hyperthermia, and clonus", "Bradycardia and constipation", "Dry mouth and urinary retention"), 1),
            Triple("Monoamine Oxidase Inhibitors (MAOIs) like Phenelzine require strict dietary restriction of:", listOf("Vitamin K rich foods", "Tyramine-rich foods (aged cheese, cured meats, red wine) to prevent hypertensive crisis", "High protein foods", "Citrus fruits"), 1),
            Triple("Lithium Carbonate therapeutic blood concentration range for maintenance therapy is:", listOf("0.1 to 0.4 mEq/L", "0.6 to 1.2 mEq/L", "2.0 to 3.5 mEq/L", "5.0 to 10.0 mEq/L"), 1),
            Triple("Early sign of Lithium toxicity includes:", listOf("Coarse hand tremor, persistent diarrhea, vomiting, muscle weakness, and ataxia", "Hypertension", "Constipation", "Weight gain"), 0),
            Triple("Warfarin (Coumadin) anticoagulant activity is monitored using:", listOf("aPTT", "Prothrombin Time (PT) and International Normalized Ratio (INR)", "Bleeding time", "Platelet count"), 1),
            Triple("The antidote for Warfarin overdose is:", listOf("Protamine Sulfate", "Vitamin K1 (Phytonadione)", "Aminocaproic Acid", "Calcium Gluconate"), 1),
            Triple("Target INR therapeutic range for a patient on Warfarin for atrial fibrillation is generally:", listOf("0.5 - 1.0", "2.0 - 3.0", "4.0 - 5.0", "6.0 - 8.0"), 1),
            Triple("Unfractionated Heparin therapy is monitored using which laboratory test?", listOf("PT/INR", "activated Partial Thromboplastin Time (aPTT)", "Platelet factor 4", "Serum fibrinogen"), 1),
            Triple("Thrombolytic agent Alteplase (tPA) primary mechanism of action is:", listOf("Converting plasminogen to plasmin to dissolve fibrin clots", "Blocking Factor Xa", "Inhibiting thrombin", "Inhibiting platelet aggregation"), 0),
            Triple("Short-Acting Beta-2 Agonist (SABA) Albuterol primary indication is:", listOf("Long-term asthma prophylaxis", "Acute relief of bronchospasm in asthma and COPD", "Reducing mucus viscosity", "Inhaled anti-inflammatory"), 1),
            Triple("Inhaled Corticosteroids (e.g. Fluticasone, Beclomethasone) patient education following inhalation:", listOf("Rinse mouth with water and spit out to prevent oral candidiasis (thrush)", "Swallow remaining medication", "Take immediately before exercise", "Use as rescue inhaler"), 0),
            Triple("Ipratropium Bromide is an inhaled anticholinergic drug that works by:", listOf("Blocking muscarinic receptors to cause bronchodilation", "Stabilizing mast cells", "Inhibiting leukotrienes", "Stimulating beta receptors"), 0),
            Triple("Theophylline bronchodilator toxic signs include:", listOf("Bradycardia", "Nausea, vomiting, tachycardia, dysrhythmias, and seizures", "Hypoglycemia", "Constipation"), 1),
            Triple("Which drug is administered IV to protect the myocardium in severe hyperkalemia?", listOf("Sodium Bicarbonate", "Calcium Gluconate or Calcium Chloride", "Regular Insulin", "Furosemide"), 1),
            Triple("Regular Insulin administered with IV Dextrose 50% in hyperkalemia works by:", listOf("Excreting potassium in urine", "Shifting potassium ions into intracellular fluid compartment", "Binding potassium in gut", "Neutralizing serum potassium"), 1),
            Triple("Sodium Polystyrene Sulfonate (Kayexalate) lowers potassium by:", listOf("Exchanging sodium for potassium ions in the large intestine for fecal elimination", "Increasing renal clearance", "Causing emesis", "Shifting potassium into cells"), 0),
            Triple("Proton Pump Inhibitors (e.g. Omeprazole) act by:", listOf("Neutralizing stomach acid directly", "Irreversibly inhibiting the H+/K+ ATPase pump in gastric parietal cells", "Blocking histamine H2 receptors", "Coating ulcer base"), 1)
        )
        addSubjectQs(4, "Pharmacology II", "KMU PHA-643 / Katzung Pharmacology", pha2)

        // ==========================================
        // 4. HEALTH ASSESSMENT II - 50 Qs
        // ==========================================
        val has2 = listOf(
            Triple("Glasgow Coma Scale (GCS) evaluates which three clinical response categories?", listOf("Pupil size, blood pressure, heart rate", "Eye opening, verbal response, and motor response", "Cranial nerves, deep tendon reflexes, sensation", "Respiration, muscle tone, color"), 1),
            Triple("A patient who opens eyes to verbal command, is confused, and localizes pain has a GCS score of:", listOf("E3 + V4 + M5 = 12", "E4 + V5 + M6 = 15", "E2 + V2 + M3 = 7", "E1 + V1 + M1 = 3"), 0),
            Triple("The maximum achievable total score on the Glasgow Coma Scale is:", listOf("10", "12", "15", "20"), 2),
            Triple("In a neurological examination, testing the pupillary light reflex assesses function of which Cranial Nerves?", listOf("CN I and CN II", "CN II (Optic - afferent) and CN III (Oculomotor - efferent)", "CN IV and CN VI", "CN V and CN VII"), 1),
            Triple("Which Cranial Nerve is evaluated by asking the patient to shrug shoulders against resistance?", listOf("CN IX (Glossopharyngeal)", "CN XI (Accessory Nerve)", "CN XII (Hypoglossal)", "CN VII (Facial)"), 1),
            Triple("Cranial Nerve XII (Hypoglossal) assessment is conducted by observing:", listOf("Gag reflex", "Tongue protrusion and movement for symmetry", "Facial symmetry during smiling", "Visual acuity"), 1),
            Triple("To perform the Romberg test for cerebellar ataxia and balance, the examiner instructs the patient to:", listOf("Walk heel-to-toe in a straight line", "Stand with feet together, arms at side, and close eyes for 20 seconds", "Hop on one foot", "Touch finger to nose rapidly"), 1),
            Triple("A positive Babinski reflex in an adult (dorsiflexion of big toe with fanning of other toes) indicates:", listOf("Normal intact motor function", "Upper Motor Neuron (UMN) lesion / corticospinal tract lesion", "Lower Motor Neuron (LMN) injury", "Normal finding in adult"), 1),
            Triple("Grade 2+ on the standard Deep Tendon Reflex (DTR) scale represents:", listOf("Absent reflex", "Hypoactive reflex", "Normal average response", "Hyperactive with clonus"), 2),
            Triple("Assess peripheral vascular circulation in the lower extremity by palpating which pulse on the dorsum of the foot?", listOf("Posterior tibial pulse", "Dorsalis pedis pulse", "Popliteal pulse", "Femoral pulse"), 1),
            Triple("Ankle-Brachial Index (ABI) value below 0.9 is diagnostic for:", listOf("Deep Vein Thrombosis", "Peripheral Artery Disease (PAD)", "Venous insufficiency", "Lymphedema"), 1),
            Triple("The Allen test is performed prior to radial artery puncture to ensure collateral blood flow via the:", listOf("Brachial artery", "Ulnar artery", "Subclavian artery", "Axillary artery"), 1),
            Triple("Pitting edema that leaves a deep pit (6 mm) that remains for over a minute is graded as:", listOf("1+ edema", "2+ edema", "3+ edema", "4+ edema"), 2),
            Triple("Phalen's test and Tinnel's sign are physical examination maneuvers used to assess for:", listOf("Thoracic outlet syndrome", "Carpal Tunnel Syndrome (Median nerve compression)", "Rotator cuff tear", "Sciatica"), 1),
            Triple("Lachman test and Anterior Drawer test assess the integrity of which knee ligament?", listOf("Posterior Cruciate Ligament (PCL)", "Anterior Cruciate Ligament (ACL)", "Medial Collateral Ligament (MCL)", "Lateral Collateral Ligament (LCL)"), 1),
            Triple("McMurray test is performed during knee examination to detect:", listOf("Patellar dislocation", "Meniscal tear", "ACL rupture", "Gouty arthritis"), 1),
            Triple("Straight Leg Raise (SLR) test / Lasègue's sign elicits radicular pain indicating:", listOf("Hip osteoarthritis", "Herniated lumbar disc / Sciatic nerve irritation", "Trochanteric bursitis", "Femoral neck fracture"), 1),
            Triple("In a musculoskeletal exam, active Range of Motion (ROM) means motion performed:", listOf("Entirely by the examiner without patient effort", "By the patient unassisted using their own muscle contraction", "With weights", "In water"), 1),
            Triple("Scoliosis spinal inspection reveals lateral curvature of the spine, best observed during:", listOf("Standing upright with arms overhead", "Adam's Forward Bend Test", "Hyperextending the back", "Side bending"), 1),
            Triple("Which heart sound (S3) heard in early diastole is normal in children/young adults but indicates fluid overload/heart failure in older adults?", listOf("S1 (lub)", "S2 (dub)", "S3 Ventricular Gallop", "S4 Atrial Gallop"), 2),
            Triple("Fourth heart sound (S4) occurring late in diastole just before S1 is caused by:", listOf("Rapid ventricular filling", "Atrial contraction against a stiff non-compliant ventricle", "Mitral valve prolapse", "Aortic stenosis"), 1),
            Triple("A loud blowing holosystolic murmur heard best at the apex radiating to the axilla indicates:", listOf("Aortic regurgitation", "Mitral Regurgitation", "Pulmonary stenosis", "Tricuspid stenosis"), 1),
            Triple("Pericardial friction rub is a high-pitched scratchy sound best auscultated with the patient:", listOf("Supine", "Leaning forward sitting up at end-expiration using diaphragm of stethoscope", "In left lateral recumbent position", "In Prone position"), 1),
            Triple("When auscultating carotid arteries for bruits in an elderly patient, the nurse should instruct the patient to:", listOf("Breathe deeply in and out", "Hold breath briefly while nurse auscultates with bell of stethoscope", "Cough forcefully", "Swallow saliva"), 1),
            Triple("To measure Jugular Venous Pressure (JVP), the patient is positioned in bed at what angle?", listOf("0 degrees flat", "30 to 45 degrees elevation", "90 degrees high Fowler's", "Trendelenburg position"), 1),
            Triple("A normal JVP vertical height above the sternal angle of Louis is less than:", listOf("1 cm", "3 cm", "5 cm", "8 cm"), 1),
            Triple("Tactile Fremitus is INCREASED over lung areas affected by:", listOf("Pneumothorax", "Consolidation (e.g., Lobar Pneumonia)", "Pleural effusion", "Emphysema"), 1),
            Triple("Percussion over a pneumothorax or emphysematous lung yields which characteristic note?", listOf("Dullness", "Hyperresonance", "Flatness", "Tympany"), 1),
            Triple("Bronchophony assessment finding where spoken 'ninety-nine' sounds clear and loud over a lung zone indicates:", listOf("Normal lung parenchyma", "Consolidation", "Atelectasis", "Normal trachea"), 1),
            Triple("Egophony test where spoken letter 'E' sounds like an 'A' over the chest wall signifies:", listOf("Lung tissue consolidation", "Pneumothorax", "Normal breath sound", "Asthma attack"), 0),
            Triple("Murphy's sign (abrupt cessation of deep inspiration during palpation of right upper quadrant) indicates:", listOf("Acute appendicitis", "Acute cholecystitis", "Acute pancreatitis", "Renal colic"), 1),
            Triple("McBurney's point tenderness located one-third the distance from anterior superior iliac spine to umbilicus indicates:", listOf("Acute appendicitis", "Cholecystitis", "Diverticulitis", "Ovarian cyst"), 0),
            Triple("Rovsing's sign is positive for appendicitis when deep palpation of the LEFT lower quadrant causes pain in the:", listOf("Left upper quadrant", "Right Lower Quadrant (RLQ)", "Periumbilical area", "Epigastrium"), 1),
            Triple("Psoas sign (pain on passive hyperextension of right hip) suggests inflammation of appendiceal tissue near the:", listOf("Iliopsoas muscle", "Obturator internus", "Rectus abdominis", "Quadratus lumborum"), 0),
            Triple("Obturator sign for appendicitis is elicited by:", listOf("Internal rotation of flexed right hip", "External rotation of left hip", "Passive knee extension", "Palpation of spleen"), 0),
            Triple("Rebound tenderness (Blumberg's sign) on abdominal release indicates:", listOf("Paralytic ileus", "Peritoneal irritation / Peritonitis", "Constipation", "Ascites"), 1),
            Triple("Fluid wave test and Shifting Dullness percussion test evaluate the presence of:", listOf("Intestinal gas", "Ascites in abdominal cavity", "Splenomegaly", "Renal mass"), 1),
            Triple("Costovertebral Angle (CVA) tenderness elicited by fist percussion over 12th rib posteriorly indicates:", listOf("Lumbar muscle strain", "Pyelonephritis / Acute renal inflammation", "Cholecystitis", "Splenic rupture"), 1),
            Triple("Weber hearing test where sound lateralizes to the IMPAIRED ear indicates:", listOf("Sensorineural hearing loss in that ear", "Conductive hearing loss in that ear", "Normal bilateral hearing", "Cerebellar lesion"), 1),
            Triple("Rinne test finding where Bone Conduction is greater than Air Conduction (BC > AC) indicates:", listOf("Normal hearing", "Conductive hearing loss in tested ear", "Sensorineural hearing loss", "Acoustic neuroma"), 1),
            Triple("Snellen chart visual acuity reading of 20/40 means the patient:", listOf("Can read at 40 feet what a normal person reads at 20 feet", "Can read at 20 feet what a person with normal vision reads at 40 feet", "Is legally blind", "Has hyperopia"), 1),
            Triple("Direct and consensual pupillary responses demonstrate intact visual pathways via which cranial nerve?", listOf("CN II and CN III", "CN III and CN IV", "CN V and CN VII", "CN VIII and CN IX"), 0),
            Triple("Nystagmus observed during extraocular muscle movement (EOM) test refers to:", listOf("Inability to elevate eyelid", "Involuntary oscillating rhythmic movement of the eyes", "Unequal pupil size", "Crossed eyes"), 1),
            Triple("Ptosis (drooping of the upper eyelid) can result from weakness of muscle innervated by:", listOf("CN III (Oculomotor nerve)", "CN VI (Abducens nerve)", "CN II (Optic nerve)", "CN VIII (Vestibulocochlear nerve)"), 0),
            Triple("Anisocoria refers to physical finding of:", listOf("Cloudy lens", "Unequal pupil sizes", "Inability to focus near objects", "Loss of peripheral vision"), 1),
            Triple("Lymph node assessment finding described as hard, non-tender, fixed, and enlarged strongly suggests:", listOf("Acute infection", "Malignancy", "Normal lymph node", "Allergic reaction"), 1),
            Triple("Tender, mobile, soft, enlarged lymph nodes are typically associated with:", listOf("Acute infection / inflammation", "Metastatic carcinoma", "Lymphoma", "Fibrosis"), 0),
            Triple("Thyroid gland palpation is performed from which approach relative to the seated patient?", listOf("Frontal approach only", "Posterior approach standing behind the patient", "Side approach lying down", "Trendelenburg position"), 1),
            Triple("Bruit auscultated over an enlarged thyroid gland using the bell of stethoscope indicates:", listOf("Hypothyroidism", "Increased vascularity associated with Hyperthyroidism (Grave's disease)", "Thyroid cyst", "Normal thyroid gland"), 1),
            Triple("Capillary refill time greater than 3 seconds in peripheral nail beds indicates:", listOf("Normal arterial flow", "Sluggish peripheral perfusion or tissue hypoxia", "Venous insufficiency", "Hyperthermia"), 1)
        )
        addSubjectQs(4, "Health Assessment II", "KMU HAS-644 / Jarvis Physical Examination & Health Assessment", has2)

        // ==========================================
        // 5. DEVELOPMENTAL PSYCHOLOGY - 50 Qs
        // ==========================================
        val psy2 = listOf(
            Triple("According to Erik Erikson's Psychosocial Stages, the primary developmental task during Infancy (0-18 months) is:", listOf("Autonomy vs. Shame and Doubt", "Trust vs. Mistrust", "Initiative vs. Guilt", "Industry vs. Inferiority"), 1),
            Triple("Erikson's stage corresponding to Early Childhood / Toddlerhood (18 months - 3 years) is:", listOf("Trust vs. Mistrust", "Autonomy vs. Shame and Doubt", "Identity vs. Role Confusion", "Generativity vs. Stagnation"), 1),
            Triple("The psychosocial conflict facing Adolescents (12-18 years) according to Erikson is:", listOf("Intimacy vs. Isolation", "Identity vs. Role Confusion", "Industry vs. Inferiority", "Integrity vs. Despair"), 1),
            Triple("Erikson's stage characteristic of Young Adulthood (19-40 years) is:", listOf("Intimacy vs. Isolation", "Generativity vs. Stagnation", "Ego Integrity vs. Despair", "Autonomy vs. Doubt"), 0),
            Triple("Middle Adulthood (40-65 years) primary Eriksonian developmental task is:", listOf("Identity vs. Role Confusion", "Generativity vs. Stagnation", "Integrity vs. Despair", "Industry vs. Inferiority"), 1),
            Triple("Late Adulthood (65+ years) psychosocial conflict according to Erikson is:", listOf("Generativity vs. Stagnation", "Ego Integrity vs. Despair", "Intimacy vs. Isolation", "Autonomy vs. Shame"), 1),
            Triple("In Jean Piaget's Cognitive Development theory, the Sensorimotor Stage spans from birth to approximately:", listOf("2 years", "7 years", "11 years", "15 years"), 0),
            Triple("An infant realizing that an object continues to exist even when hidden out of sight demonstrates:", listOf("Centration", "Object Permanence", "Egocentrism", "Conservation"), 1),
            Triple("Piaget's Preoperational Stage (2-7 years) is characterized by:", listOf("Abstract deductive reasoning", "Egocentrism, symbolic play, and lack of conservation", "Reversibility", "Hypothetical thinking"), 1),
            Triple("A child understanding that pouring liquid into a tall thin glass does not change the amount of liquid demonstrates Piaget's concept of:", listOf("Object permanence", "Conservation", "Assimilation", "Animism"), 1),
            Triple("Piaget's Concrete Operational Stage (7-11 years) enables children to:", listOf("Think logically about concrete objects and events and master conservation and classification", "Perform abstract hypothetical reasoning", "Operate purely through reflexes", "Display egocentric thinking"), 0),
            Triple("Formal Operational Stage (12 years and older) in Piaget's theory introduces:", listOf("Symbolic thinking", "Abstract, hypothetical, and deductive reasoning", "Egocentrism", "Irreversibility"), 1),
            Triple("According to Sigmund Freud's Psychoanalytic theory, the personality component operating on the Pleasure Principle is:", listOf("Ego", "Id", "Superego", "Ideal Self"), 1),
            Triple("Freud defined the Ego as operating on which principle?", listOf("Pleasure Principle", "Reality Principle", "Moral Principle", "Nirvana Principle"), 1),
            Triple("The Superego in Freud's structure of personality represents the:", listOf("Instinctual drives", "Moral conscience and internalized societal standards", "Rational decision maker", "Unconscious reflexes"), 1),
            Triple("Freud's Psychosexual stage associated with toilet training is the:", listOf("Oral Stage", "Anal Stage", "Phallic Stage", "Latency Stage"), 1),
            Triple("Freud's Phallic Stage (3-6 years) features which psychological dynamic in boys?", listOf("Electra complex", "Oedipus complex", "Identity crisis", "Castration anxiety resolution"), 1),
            Triple("Lawrence Kohlberg's theory of Moral Development classifies moral reasoning into how many major levels?", listOf("Two levels", "Three levels (Pre-conventional, Conventional, Post-conventional)", "Five levels", "Eight levels"), 1),
            Triple("In Kohlberg's Pre-conventional Level, moral behavior is guided primarily by:", listOf("Avoidance of punishment and personal gain / reward", "Social approval and law-and-order", "Universal ethical principles", "Social contract"), 0),
            Triple("Kohlberg's Conventional Level of moral development centers around:", listOf("Fear of physical punishment", "Conforming to societal norms, rules, and law-and-order", "Self-chosen ethical principles", "Biological instincts"), 1),
            Triple("Kohlberg's Post-conventional Level is characterized by moral judgment based on:", listOf("Fear of authority", "Universal human rights, justice, and social contract", "Peer pressure", "Obedience to written law"), 1),
            Triple("Unconscious mental strategies used by the Ego to protect against anxiety are called:", listOf("Cognitive schemas", "Defense Mechanisms", "Developmental milestones", "Conditioned responses"), 1),
            Triple("Refusing to accept or acknowledge an unpleasant reality is the defense mechanism known as:", listOf("Repression", "Denial", "Projection", "Rationalization"), 1),
            Triple("Attributing one's own unacceptable feelings or impulses onto another person is called:", listOf("Displacement", "Projection", "Sublimation", "Reaction Formation"), 1),
            Triple("Redirecting aggressive or unacceptable impulses into socially acceptable activities (e.g. sports, art) is called:", listOf("Sublimation", "Displacement", "Regression", "Compensation"), 0),
            Triple("Reverting to an earlier developmental stage during stress (e.g., bedwetting in a hospitalized child) is:", listOf("Repression", "Regression", "Suppression", "Intellectualization"), 1),
            Triple("Redirecting emotional anger from the original source onto a safer substitute target is called:", listOf("Projection", "Displacement", "Rationalization", "Introjection"), 1),
            Triple("Justifying unacceptable behavior with logical or socially acceptable excuses is:", listOf("Rationalization", "Denial", "Reaction Formation", "Sublimation"), 0),
            Triple("Expressing the exact opposite of one's true unconscious feelings is the defense mechanism of:", listOf("Reaction Formation", "Projection", "Undo", "Identification"), 0),
            Triple("Consciously pushing painful thoughts out of awareness is termed:", listOf("Repression", "Suppression", "Denial", "Regression"), 1),
            Triple("Involuntary, unconscious pushing of anxiety-provoking thoughts into the unconscious mind is:", listOf("Repression", "Suppression", "Displacement", "Projection"), 0),
            Triple("Lev Vygotsky's Sociocultural Theory emphasizes the role of social interaction and:", listOf("Biological maturation only", "Zone of Proximal Development (ZPD) and Scaffolding", "Psychosexual stages", "Operant conditioning"), 1),
            Triple("Zone of Proximal Development (ZPD) represents the distance between:", listOf("Infancy and adulthood", "What a learner can do independently and what they can do with guidance", "Cognitive and emotional intelligence", "Id and Superego"), 1),
            Triple("Temporary instructional support provided by a teacher or peer to help a child master a task is called:", listOf("Assimilation", "Scaffolding", "Fixation", "Conditioning"), 1),
            Triple("In John Bowlby's Attachment Theory, secure attachment in infancy promotes:", listOf("Anxiety and clinginess", "Healthy emotional regulation, trust, and exploration", "Emotional detachment", "Egocentrism"), 1),
            Triple("In Mary Ainsworth's Strange Situation experiment, an infant who ignores the mother upon reunion exhibits:", listOf("Secure attachment", "Anxious-Avoidant attachment", "Anxious-Ambivalent attachment", "Disorganized attachment"), 1),
            Triple("An infant showing extreme distress upon separation and ambivalence/anger upon reunion exhibits:", listOf("Anxious-Ambivalent / Resistant attachment", "Secure attachment", "Avoidant attachment", "Disorganized attachment"), 0),
            Triple("Diana Baumrind's parenting style characterized by high warmth and firm, reasonable boundaries is:", listOf("Authoritarian", "Authoritative", "Permissive", "Uninvolved / Neglectful"), 1),
            Triple("Parenting style featuring strict obedience, low warmth, and heavy punishment is:", listOf("Authoritative", "Authoritarian", "Permissive", "Democratic"), 1),
            Triple("Permissive parenting style is characterized by:", listOf("High demands, low responsiveness", "High warmth, low control / boundaries", "Low warmth, low responsiveness", "Strict enforcement of rules"), 1),
            Triple("Ivan Pavlov's Classical Conditioning involves learning through:", listOf("Consequences and rewards", "Association between an unconditioned stimulus and a neutral stimulus", "Observational imitation", "Insight"), 1),
            Triple("B.F. Skinner's Operant Conditioning modifies behavior through:", listOf("Unconditioned reflexes", "Reinforcement (positive/negative) and punishment", "Cognitive restructuring", "Free association"), 1),
            Triple("Adding a desirable stimulus to increase the frequency of a behavior is called:", listOf("Negative reinforcement", "Positive reinforcement", "Positive punishment", "Extinction"), 1),
            Triple("Removing an unpleasant stimulus to increase the frequency of a target behavior is:", listOf("Positive punishment", "Negative reinforcement", "Extinction", "Negative punishment"), 1),
            Triple("Albert Bandura's Social Learning Theory emphasizes that learning occurs through:", listOf("Direct physical reward only", "Observation, imitation, and modeling (Bobo Doll experiment)", "Unconscious drives", "Operant conditioning"), 1),
            Triple("Bandura's concept of 'Self-Efficacy' refers to an individual's belief in:", listOf("Their inherent superiority", "Their ability to succeed and accomplish specific tasks", "Their physical intelligence", "Unconscious motivation"), 1),
            Triple("Elisabeth Kübler-Ross five stages of grief in order are:", listOf("Denial, Anger, Bargaining, Depression, Acceptance (DABDA)", "Anger, Denial, Depression, Bargaining, Acceptance", "Bargaining, Anger, Denial, Acceptance, Depression", "Depression, Denial, Anger, Acceptance, Bargaining"), 0),
            Triple("A patient newly diagnosed with terminal illness who says 'The lab results must be mixed up' is in which stage of grief?", listOf("Anger", "Denial", "Bargaining", "Depression"), 1),
            Triple("A dying patient praying 'God, if you let me live to see my grandchild born, I will go to mosque every day' is expressing:", listOf("Denial", "Bargaining", "Anger", "Acceptance"), 1),
            Triple("Hans Selye's General Adaptation Syndrome (GAS) model of stress consists of three stages in sequence:", listOf("Alarm reaction, Resistance, and Exhaustion", "Resistance, Alarm, Exhaustion", "Exhaustion, Resistance, Alarm", "Shock, Countershock, Recovery"), 0)
        )
        addSubjectQs(4, "Developmental Psychology", "KMU PSY-645 / Santrock Life-Span Development", psy2)

        // Add 100 extra questions for EACH subject in Semester 4 (500 extra questions)
        questions.addAll(KpSemester4PlusQuestionBank.getQuestions(idCounter))

        return questions
    }
}
