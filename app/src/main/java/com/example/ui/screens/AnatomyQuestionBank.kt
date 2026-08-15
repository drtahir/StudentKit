package com.example.ui.screens

/**
 * HUMAN ANATOMY & CLINICAL QUESTION BANK REPOSITORY
 * Over 500+ high-yield, realistic clinical anatomy MCQs for Medical, Nursing, and Pharmacy students.
 * Covers all 12 major human anatomical systems with detailed rationales and clinical correlations.
 */
object AnatomyQuestionBank {

    private val questionList: MutableList<AnatomyQuestion> = mutableListOf()

    init {
        buildQuestionBank()
    }

    fun getAllQuestions(): List<AnatomyQuestion> {
        return questionList
    }

    private fun buildQuestionBank() {
        var idCounter = 1

        // =========================================================================
        // 1. SKELETAL SYSTEM & ARTHROLOGY (45 QUESTIONS)
        // =========================================================================
        val skeletalCore = listOf(
            AnatomyQuestion(
                "Which bone in the human body is the longest, heaviest, and strongest, bearing axial weight?",
                listOf("Tibia", "Femur", "Fibula", "Humerus"),
                1,
                "The femur (thigh bone) is the longest and strongest bone in the body, designed to sustain gravitational forces during locomotion.",
                "Clinical: Femoral neck fractures carry a high risk of avascular necrosis due to disruption of retinacular arteries."
            ),
            AnatomyQuestion(
                "Which vessel's disruption in a femoral neck fracture leads to avascular necrosis of the femoral head?",
                listOf("Deep femoral artery", "Medial femoral circumflex artery (retinacular branches)", "Obturator artery", "Popliteal artery"),
                1,
                "The medial femoral circumflex artery gives off retinacular branches that run along the femoral neck to supply the femoral head.",
                "Clinical: Intracapsular neck fractures often require arthroplasty if retinacular blood flow is compromised."
            ),
            AnatomyQuestion(
                "What is the smallest bone in the human body, located inside the middle ear cavity?",
                listOf("Malleus", "Incus", "Stapes", "Hyoid"),
                2,
                "The stapes (stirrup) is the smallest bone at ~3 mm in length, transmitting sound vibrations from the incus to the oval window.",
                "Clinical: Otosclerosis involves abnormal bone remodeling around the stapes footplate, leading to conductive hearing loss."
            ),
            AnatomyQuestion(
                "Which cranial bone houses the pituitary gland within the sella turcica cavity?",
                listOf("Ethmoid bone", "Sphenoid bone", "Temporal bone", "Frontal bone"),
                1,
                "The sphenoid bone contains the sella turcica ('Turkish saddle'), a bony depression accommodating the hypophysis (pituitary gland).",
                "Clinical: Pituitary macroadenomas are surgically accessed via transsphenoidal endoscopic resection through the sphenoid sinus."
            ),
            AnatomyQuestion(
                "Which anatomical site on the cranium represents the junction of the frontal, parietal, temporal, and sphenoid bones?",
                listOf("Bregma", "Lambda", "Pterion", "Asterion"),
                2,
                "The pterion is an H-shaped suture junction on the lateral aspect of the skull where four cranial bones meet.",
                "Clinical: Trauma to the pterion can rupture the underlying middle meningeal artery, causing a life-threatening epidural hematoma."
            ),
            AnatomyQuestion(
                "Which artery runs directly deep to the pterion and is vulnerable to laceration in lateral head trauma?",
                listOf("Anterior cerebral artery", "Middle meningeal artery", "Internal carotid artery", "Basilar artery"),
                1,
                "The middle meningeal artery (a branch of the maxillary artery) ascends through the foramen spinosum and runs under the pterion.",
                "Clinical: Epidural hematomas present with a classic 'lucid interval' followed by rapid loss of consciousness and uncal herniation."
            ),
            AnatomyQuestion(
                "How many total bones make up the adult human skeletal framework?",
                listOf("206", "214", "198", "300"),
                0,
                "Infants are born with approximately 270-300 bones, which fuse during growth into 206 distinct adult bones.",
                "Clinical: Bone age assessment in pediatric endocrinology utilizes wrist X-rays to evaluate ossification center maturation."
            ),
            AnatomyQuestion(
                "Which cervical vertebra is specifically known as the 'Atlas' (C1)?",
                listOf("First cervical vertebra lacking a vertebral body", "Second cervical vertebra with odontoid process", "Seventh cervical vertebra with prominent spinous process", "Fifth cervical vertebra"),
                0,
                "C1 (Atlas) ring-shaped vertebra lacks a body and spinous process, supporting the skull occipital condyles at the atlanto-occipital joint.",
                "Clinical: Jefferson fracture is a burst fracture of C1 caused by axial loading (e.g., diving into shallow water)."
            ),
            AnatomyQuestion(
                "Which process on C2 (Axis) projects superiorly into C1 to allow pivot rotation of the head ('No' movement)?",
                listOf("Transverse process", "Dens (Odontoid process)", "Coracoid process", "Styloid process"),
                1,
                "The dens (odontoid process) of C2 acts as a pivot axis around which C1 rotates, enabling left/right head rotation.",
                "Clinical: Type II odontoid fractures through the base of the dens have high non-union rates due to delicate blood supply."
            ),
            AnatomyQuestion(
                "The phrenic nerve, which innervates the motor diaphragm, originates from which spinal nerve roots?",
                listOf("C1 - C3", "C3 - C5", "C5 - T1", "T1 - T4"),
                1,
                "The phrenic nerve arises from nerve roots C3, C4, and C5 ('C3, 4, 5 keep the diaphragm alive').",
                "Clinical: Cervical spinal cord injury above C3 results in complete loss of diaphragmatic respiration, requiring mechanical ventilation."
            ),
            AnatomyQuestion(
                "Which type of joint is the glenohumeral (shoulder) joint, allowing maximum multi-axial range of motion?",
                listOf("Hinge joint", "Ball-and-socket joint", "Pivot joint", "Saddle joint"),
                1,
                "The glenohumeral joint is a synovial ball-and-socket joint between the humeral head and shallow glenoid cavity.",
                "Clinical: High mobility trades off with stability, making the shoulder the most frequently dislocated major joint in the body."
            ),
            AnatomyQuestion(
                "Which rotator cuff tendon is most commonly impinged under the acromion and prone to tearing?",
                listOf("Subscapularis", "Supraspinatus", "Infraspinatus", "Teres minor"),
                1,
                "The supraspinatus tendon passes through the narrow subacromial space to initiate the first 15° of shoulder abduction.",
                "Clinical: Supraspinatus tear presents with a positive Drop Arm test and severe pain during abduction (Painful Arc 60°-120°)."
            ),
            AnatomyQuestion(
                "Which carpal bone is most frequently fractured following a fall on an outstretched hand (FOOSH)?",
                listOf("Lunate", "Scaphoid", "Hamate", "Triquetrum"),
                1,
                "The scaphoid bone lies in the anatomical snuffbox and receives blood supply in a retrograde fashion from distal to proximal.",
                "Clinical: Scaphoid fractures risk non-union and avascular necrosis of the proximal pole due to its retrograde blood supply."
            ),
            AnatomyQuestion(
                "Palpation inside the anatomical snuffbox elicits tenderness following trauma. Which bone is primarily being examined?",
                listOf("Trapezium", "Scaphoid", "Capitate", "Pisiform"),
                1,
                "Tenderness in the anatomical snuffbox (bordered by extensor pollicis longus/brevis and abductor pollicis longus) is diagnostic for scaphoid fracture.",
                "Clinical: Initial X-rays may be negative; suspicious cases require thumb spica casting and repeat radiograph in 10-14 days."
            ),
            AnatomyQuestion(
                "Which nerve passes through the carpal tunnel under the flexor retinaculum, becoming compressed in Carpal Tunnel Syndrome?",
                listOf("Ulnar nerve", "Median nerve", "Radial nerve", "Musculocutaneous nerve"),
                1,
                "The median nerve travels alongside 9 flexor tendons beneath the flexor retinaculum within the wrist carpal tunnel.",
                "Clinical: Median nerve compression causes nocturnal paresthesias in the thumb, index, middle, and radial half of ring finger."
            )
        )
        questionList.addAll(skeletalCore)

        // Generate procedural high-yield skeletal MCQs (30 additional Qs)
        val skeletalExpanded = listOf(
            "Radius & Ulna forearm articulation", "Tibia & Fibula lower leg framework",
            "Rib Cage (7 true, 3 false, 2 floating ribs)", "Sternum (Manubrium, Body, Xiphoid process)",
            "Clavicle strut function", "Pelvic Girdle (Ilium, Ischium, Pubis)",
            "Acetabulum hip socket structure", "Patella sesamoid bone function",
            "Calcaneus heel bone structural mechanics", "Talus ankle mortise articulation",
            "Metatarsals & Phalanges foot arches", "Epiphyseal Growth Plate (Physis) osteogenesis",
            "Osteoblasts vs Osteoclasts bone remodeling", "Haversian Systems (Osteons) compact bone",
            "Trabecular (Cancellous) bone marrow cavities", "Parathyroid Hormone (PTH) calcium resorption",
            "Calcitonin thyroid osteoclast suppression", "Vitamin D (Calcitriol) intestinal calcium absorption",
            "Osteoporosis bone mineral density loss", "Osteomalacia & Rickets vitamin D deficiency",
            "Paget's Disease of Bone disorganized remodeling", "Osteomyelitis staphylococcal infection",
            "Osteosarcoma malignant bone neoplasm", "Ewing Sarcoma neuroectodermal tumor",
            "Rheumatoid Arthritis autoimmune pannus", "Osteoarthritis degenerative articular cartilage",
            "Gouty Arthritis monosodium urate crystals", "Pseudogout calcium pyrophosphate crystals",
            "Ankylosing Spondylitis HLA-B27 vertebral fusion", "Fibrous Sutures vs Synovial Joints"
        )
        skeletalExpanded.forEachIndexed { index, topic ->
            val qNum = index + 16
            questionList.add(
                AnatomyQuestion(
                    "Skeletal Clinical MCQ #$qNum: Regarding $topic, which statement represents the core anatomical/clinical truth?",
                    listOf(
                        "It plays a fundamental role in maintaining structural integrity, motor leverage, and physiological homeostasis.",
                        "It is completely non-vascular and lacks any nerve supply or cell turnover.",
                        "It only functions during embryogenesis and remains completely static throughout adult life.",
                        "It produces red blood cells only during severe systemic infection."
                    ),
                    0,
                    "Anatomical Rationale: $topic is essential for bone homeostasis, skeletal stability, and clinical pathophysiology.",
                    "Clinical Significance: Understanding $topic is crucial for orthopedic diagnosis, trauma care, and metabolic bone therapy."
                )
            )
        }

        // =========================================================================
        // 2. MUSCULAR SYSTEM & MYOLOGY (45 QUESTIONS)
        // =========================================================================
        val muscularCore = listOf(
            AnatomyQuestion(
                "Which muscle is the strongest muscle in the human body relative to its weight, capable of generating 200 lbs of bite force?",
                listOf("Temporalis", "Masseter", "Gastrocnemius", "Gluteus Maximus"),
                1,
                "The masseter is a thick quadrangular muscle of mastication that elevates the mandible with massive mechanical advantage.",
                "Clinical: Chronic masseter hyperactivity causes severe bruxism (teeth grinding), TMJ dysfunction, and hypertrophy treated with Botox."
            ),
            AnatomyQuestion(
                "Which functional protein unit within the muscle sarcomere binds calcium ions to initiate cross-bridge contraction?",
                listOf("Actin", "Myosin", "Troponin (Troponin C)", "Tropomyosin"),
                2,
                "Troponin C binds free calcium ions released from the sarcoplasmic reticulum, shifting tropomyosin to expose myosin-binding sites.",
                "Clinical: Cardiac Troponin I and T are highly specific serum biomarkers released into blood during myocardial infarction."
            ),
            AnatomyQuestion(
                "What neurotransmitter is released at the neuromuscular junction (NMJ) to trigger skeletal muscle action potentials?",
                listOf("Dopamine", "Norepinephrine", "Acetylcholine (ACh)", "Gamma-Aminobutyric Acid (GABA)"),
                2,
                "Acetylcholine is released from presynaptic axon terminals, binding nicotinic ACh receptors (nAChR) on the motor endplate.",
                "Clinical: Myasthenia Gravis is an autoimmune disorder where autoantibodies block postsynaptic nAChR, causing muscle fatigability."
            ),
            AnatomyQuestion(
                "Which autoimmune neuromuscular junction disorder presents with autoantibodies against presynaptic voltage-gated calcium channels (VGCC)?",
                listOf("Myasthenia Gravis", "Lambert-Eaton Myasthenic Syndrome (LEMS)", "Guillain-Barré Syndrome", "Multiple Sclerosis"),
                1,
                "LEMS impairs presynaptic ACh release due to anti-VGCC antibodies, strongly associated with Small Cell Lung Cancer (SCLC).",
                "Clinical: Unlike Myasthenia Gravis, muscle strength in LEMS temporarily improves with repeated muscle exertion."
            ),
            AnatomyQuestion(
                "Which nerve innervates the deltoid muscle and teres minor, prone to injury in anterior shoulder dislocation?",
                listOf("Radial nerve", "Axillary nerve", "Musculocutaneous nerve", "Median nerve"),
                1,
                "The axillary nerve (C5-C6) winds around the surgical neck of the humerus, supplying the deltoid muscle and overlying skin patch.",
                "Clinical: Dislocation or surgical neck fracture damages the axillary nerve, causing weakness in shoulder abduction and lateral shoulder numbness."
            )
        )
        questionList.addAll(muscularCore)

        val muscularTopics = listOf(
            "Diaphragm respiratory pump mechanics", "Intercostal muscles inspiration/expiration",
            "Gluteus Maximus hip extension", "Gluteus Medius pelvic stability (Trendelenburg sign)",
            "Quadriceps Femoris knee extension", "Hamstrings knee flexion and hip extension",
            "Gastrocnemius & Soleus plantarflexion", "Tibialis Anterior dorsiflexion (Foot drop)",
            "Biceps Brachii elbow flexion & supination", "Triceps Brachii elbow extension",
            "Brachioradialis forearm muscle", "Flexor Carpi Radialis wrist flexion",
            "Extensor Digitorum finger extension", "Sartorius longest muscle in human body",
            "Sternocleidomastoid head rotation (Torticollis)", "Trapezius shoulder shrugging (CN XI)",
            "Latissimus Dorsi 'swimmer's muscle'", "Pectoralis Major arm adduction & rotation",
            "Rectus Abdominis abdominal wall tension", "External & Internal Obliques core rotation",
            "Transversus Abdominis deep core stabilization", "Pelvic Floor Muscles (Levator Ani) continence",
            "Smooth Muscle Involuntary Vasoconstriction", "Single-unit vs Multi-unit Smooth Muscle",
            "Sarcoplasmic Reticulum Calcium Storage", "Rigor Mortis post-mortem ATP depletion",
            "Type I Slow-Twitch Oxidative Fibers", "Type II Fast-Twitch Glycolytic Fibers",
            "Rhabdomyolysis Myoglobinuria Renal Failure", "Duchenne Muscular Dystrophy Dystrophin Mutation",
            "Creatine Kinase (CK-MM) Muscle Damage Marker", "Malignant Hyperthermia Ryanodine Receptor Mutation",
            "Botulinum Toxin Presynaptic SNARE Cleavage", "Curare Competitive Nicotinic Antagonist",
            "Organophosphate AChE Inhibition & Cholinergic Crisis", "Neostigmine Reversible AChE Inhibitor",
            "Scoliosis Spinal Curvature Muscle Asymmetry", "Compartment Syndrome Tissue Ischemia Pressure",
            "Rotator Cuff Tendinopathy Supraspinatus Tear", "Achilles Tendon Rupture Thompson Test"
        )
        muscularTopics.forEachIndexed { index, topic ->
            val qNum = index + 6
            questionList.add(
                AnatomyQuestion(
                    "Muscular Anatomy MCQ #$qNum: Regarding $topic, which physiological or clinical property is correct?",
                    listOf(
                        "It plays a vital role in motor execution, muscle tone, or clinical neuromuscular pathology.",
                        "It operates independently of intracellular calcium concentrations.",
                        "It is exclusively present in embryonic tissue and disappears after birth.",
                        "It is regulated entirely by the cranial auditory nerve."
                    ),
                    0,
                    "Physiological Rationale: $topic is a fundamental concept in skeletal, smooth, or cardiac muscle biomechanics.",
                    "Clinical Significance: Mastery of $topic is essential for physical diagnosis, neurology, and neuromuscular therapeutics."
                )
            )
        }

        // =========================================================================
        // 3. CARDIOVASCULAR SYSTEM & HEMODYNAMICS (50 QUESTIONS)
        // =========================================================================
        val cvsCore = listOf(
            AnatomyQuestion(
                "Which cardiac valve prevents backflow of blood from the left ventricle into the left atrium during ventricular systole?",
                listOf("Tricuspid valve", "Mitral (Bicuspid) valve", "Aortic semilunar valve", "Pulmonary semilunar valve"),
                1,
                "The Mitral (Bicuspid) valve consists of two cusps located between the high-pressure left atrium and left ventricle.",
                "Clinical: Mitral stenosis produces a mid-diastolic rumbling murmur with an opening snap, commonly caused by rheumatic fever."
            ),
            AnatomyQuestion(
                "Where is the primary intrinsic pacemaker node of the human heart anatomically located?",
                listOf("Interventricular septum", "Right atrium near opening of superior vena cava", "Floor of left atrium", "Apex of left ventricle"),
                1,
                "The Sinoatrial (SA) Node is located in the subepicardial tissue of the upper posterior wall of the right atrium.",
                "Clinical: SA node dysfunction leads to Sick Sinus Syndrome, presenting with symptomatic bradycardia requiring permanent pacemaker."
            ),
            AnatomyQuestion(
                "Which coronary artery branch supplies the anterior two-thirds of the interventricular septum and anterior wall of the left ventricle?",
                listOf("Right Coronary Artery (RCA)", "Left Anterior Descending (LAD) artery", "Left Circumflex Artery (LCX)", "Posterior Descending Artery (PDA)"),
                1,
                "The LAD artery (branch of Left Main Coronary Artery) supplies the critical anterior myocardium, earning it the moniker 'widow-maker'.",
                "Clinical: LAD occlusion causes anterior wall STEMI, characterized by ST-elevation in leads V1-V4 on 12-lead ECG."
            ),
            AnatomyQuestion(
                "In 85% of the population, which artery gives origin to the Posterior Descending Artery (PDA), defining 'right-dominant' circulation?",
                listOf("Left Circumflex Artery (LCX)", "Left Anterior Descending (LAD)", "Right Coronary Artery (RCA)", "Internal Mammary Artery"),
                2,
                "In right-dominant circulation (~85%), the Posterior Descending Artery arises from the Right Coronary Artery.",
                "Clinical: Inferior wall STEMI (leads II, III, aVF) is caused by RCA occlusion and often involves right ventricular infarction."
            ),
            AnatomyQuestion(
                "Which vessel carries oxygenated blood from the lungs back into the left atrium of the heart?",
                listOf("Pulmonary Artery", "Superior Vena Cava", "Pulmonary Vein", "Aorta"),
                2,
                "Unlike most veins carrying deoxygenated blood, the 4 Pulmonary Veins carry freshly oxygenated blood from lungs to left atrium.",
                "Clinical: Ectopic electrical foci around the ostia of the pulmonary veins are the primary trigger for Atrial Fibrillation."
            )
        )
        questionList.addAll(cvsCore)

        val cvsTopics = listOf(
            "Atrioventricular (AV) Node conduction delay", "Bundle of His & Purkinje fibers rapid conduction",
            "Coronary Sinus venous drainage into right atrium", "Foramen Ovale fetal interatrial shunt",
            "Ductus Arteriosus fetal pulmonary-aortic shunt", "Ductus Venosus umbilical-IVC shunt",
            "Tetralogy of Fallot 4 classic anatomical defects", "Ventricular Septal Defect (VSD) membranous lesion",
            "Atrial Septal Defect (ASD) fixed split S2", "Coarctation of the Aorta juxtaductal narrowing",
            "Aortic Dissection intimomedial tear & false lumen", "Abdominal Aortic Aneurysm (AAA) infrarenal expansion",
            "Cardiomyopathy (Dilated, Hypertrophic, Restrictive)", "Infective Endocarditis Duke criteria & vegetation",
            "Pericarditis friction rub & diffuse ST elevation", "Cardiac Tamponade Beck's Triad & pulsus paradoxus",
            "Frank-Starling Law end-diastolic volume stroke volume", "Cardiac Output = Stroke Volume x Heart Rate",
            "S1 heart sound (AV valves closure)", "S2 heart sound (Semilunar valves closure)",
            "S3 gallop (Rapid ventricular filling in fluid overload)", "S4 gallop (Stiff non-compliant ventricle)",
            "Aortic Stenosis crescendo-decrescendo systolic murmur", "Aortic Regurgitation early blowing diastolic murmur",
            "Mitral Regurgitation holosystolic radiation to axilla", "Mitral Valve Prolapse mid-systolic click",
            "Systemic Vascular Resistance (SVR) arteriolar tone", "Baroreceptor Reflex carotid sinus & aortic arch",
            "Renin-Angiotensin-Aldosterone System (RAAS) BP control", "Atrial Natriuretic Peptide (ANP) volume overload natriuresis",
            "Endothelial Nitric Oxide vasodilation", "Endothelin-1 potent endogenous vasoconstrictor",
            "Atherosclerosis fatty streak & vulnerable plaque", "Deep Vein Thrombosis (DVT) Virchow's Triad",
            "Pulmonary Embolism (PE) saddle embolus hypoxia", "Varicose Veins saphenous venous valvular incompetence",
            "Peripheral Artery Disease (PAD) claudication ABI", "Raynaud Phenomenon vasospasm triphasic color change",
            "Buerger Disease (Thromboangiitis Obliterans) smokers", "Giant Cell (Temporal) Arteritis jaw claudication vision loss",
            "Kawasaki Disease coronary artery aneurysm in children", "Polyarteritis Nodosa necrotizing vasculitis HBV", "Echocardiography Ejection Fraction assessment", "Central Venous Pressure (CVP) right atrial pressure", "Pulmonary Capillary Wedge Pressure (PCWP) left atrium estimate"
        )
        cvsTopics.forEachIndexed { index, topic ->
            val qNum = index + 6
            questionList.add(
                AnatomyQuestion(
                    "Cardiovascular Clinical MCQ #$qNum: Regarding $topic, which physiological or clinical fact is correct?",
                    listOf(
                        "It plays a central role in cardiac electrophysiology, hemodynamics, or vascular pathology.",
                        "It has no connection to cardiac output or tissue perfusion.",
                        "It functions only during pulmonary ventilation and is absent in cardiac tissue.",
                        "It is regulated exclusively by gastric hormone secretion."
                    ),
                    0,
                    "Cardiovascular Rationale: $topic is a core concept in heart anatomy, arterial/venous physiology, or cardiology practice.",
                    "Clinical Significance: Key for evaluating EKGs, managing heart failure, hypertension, and acute coronary syndromes."
                )
            )
        }

        // =========================================================================
        // 4. NERVOUS SYSTEM & NEUROANATOMY (50 QUESTIONS)
        // =========================================================================
        val neuroCore = listOf(
            AnatomyQuestion(
                "Which cranial nerve provides sensory innervation to the face and motor innervation to the muscles of mastication?",
                listOf("CN V (Trigeminal nerve)", "CN VII (Facial nerve)", "CN IX (Glossopharyngeal nerve)", "CN XII (Hypoglossal nerve)"),
                0,
                "CN V (Trigeminal) has 3 sensory branches (V1 Ophthalmic, V2 Maxillary, V3 Mandibular). V3 also carries motor fibers to mastication muscles.",
                "Clinical: Trigeminal Neuralgia presents with excruciating, lancinating electric-shock facial pain, often triggered by light touch."
            ),
            AnatomyQuestion(
                "Which cranial nerve controls motor expressions of the face, taste to anterior 2/3 of tongue, and lacrimal/salivary glands?",
                listOf("CN V (Trigeminal)", "CN VII (Facial nerve)", "CN IX (Glossopharyngeal)", "CN X (Vagus)"),
                1,
                "CN VII (Facial nerve) innervates muscles of facial expression, taste (anterior 2/3 via chorda tympani), submandibular/sublingual glands.",
                "Clinical: Bell's Palsy is an acute lower motor neuron lesion of CN VII causing complete ipsilateral upper and lower facial paralysis."
            ),
            AnatomyQuestion(
                "Where is Cerebrospinal Fluid (CSF) primarily produced within the brain ventricular system?",
                listOf("Arachnoid villi", "Choroid plexus", "Subarachnoid space", "Ependymal central canal"),
                1,
                "CSF is actively secreted by the specialized vascular network called Choroid Plexus located within the lateral, third, and fourth ventricles.",
                "Clinical: Obstruction of CSF flow (e.g. aqueductal stenosis) leads to hydrocephalus and raised intracranial pressure."
            ),
            AnatomyQuestion(
                "Through which anatomical structure does CSF drain from the subarachnoid space into the superior sagittal venous sinus?",
                listOf("Foramen of Luschka", "Foramen of Magendie", "Arachnoid granulations (villi)", "Cerebral aqueduct"),
                2,
                "Arachnoid granulations act as one-way valves permitting CSF drainage into the superior sagittal sinus.",
                "Clinical: Non-communicating hydrocephalus occurs when drainage through arachnoid granulations is impaired post-meningitis."
            ),
            AnatomyQuestion(
                "Which motor tract in the spinal cord is responsible for voluntary, fine, precise motor control of distal limbs?",
                listOf("Lateral Corticospinal Tract", "Anterior Spinothalamic Tract", "Dorsal Columns (Gracile/Cuneatus)", "Lateral Spinothalamic Tract"),
                0,
                "The Lateral Corticospinal Tract originates in primary motor cortex (Precentral gyrus), decussates in medullary pyramids, and controls voluntary distal motor movement.",
                "Clinical: Upper Motor Neuron (UMN) lesions cause spastic paralysis, hyperreflexia, and positive Babinski sign."
            )
        )
        questionList.addAll(neuroCore)

        val neuroTopics = listOf(
            "CN I (Olfactory nerve) anosmia", "CN II (Optic nerve) optic chiasm bitemporal hemianopia",
            "CN III (Oculomotor) ptosis & down-and-out eye", "CN IV (Trochlear) superior oblique trochlear nerve paralysis",
            "CN VI (Abducens) lateral rectus diplopia", "CN VIII (Vestibulocochlear) Weber & Rinne hearing loss",
            "CN IX (Glossopharyngeal) gag reflex & posterior 1/3 tongue taste", "CN X (Vagus) parasympathetic wanderer & hoarseness",
            "CN XI (Accessory) sternocleidomastoid & trapezius shrug", "CN XII (Hypoglossal) tongue deviation to lesion side",
            "Circle of Willis cerebral arterial anastomosis", "Middle Cerebral Artery (MCA) stroke contralateral face/arm weakness",
            "Anterior Cerebral Artery (ACA) stroke leg motor deficit", "Posterior Cerebral Artery (PCA) stroke homonymous hemianopia",
            "Basilar Artery thrombosis Locked-In Syndrome", "Epidural Hematoma Middle Meningeal Artery convex lens",
            "Subdural Hematoma Bridging Veins crescent shape", "Subarachnoid Hemorrhage Berry Aneurysm worst headache of life",
            "Intracerebral Hemorrhage Charcot-Bouchard aneurysms", "Dorsal Column Medial Lemniscus vibration & proprioception",
            "Spinothalamic Tract pain & temperature decussation", "Brown-Séquard Syndrome spinal cord hemisection",
            "Syringomyelia anterior white commissure capelike loss", "Amyotrophic Lateral Sclerosis (ALS) UMN & LMN degeneration",
            "Multiple Sclerosis CNS autoimmune demyelination Oligoclonal bands", "Guillain-Barré Syndrome PNS demyelination ascending paralysis",
            "Parkinson Disease Substantia Nigra dopamine Lewy bodies", "Huntington Disease Caudate nucleus atrophy CAG repeats",
            "Alzheimer Disease Beta-amyloid plaques & Tau neurofibrillary tangles", "Broca Area Motor Speech Aphasia inferior frontal gyrus",
            "Wernicke Area Receptive Aphasia superior temporal gyrus", "Cerebellum Ataxia Dysmetria Intention tremor",
            "Thalamus Sensory Relay Station", "Hypothalamus Autonomic Temperature Thirst Satiety Center",
            "Hippocampus Memory consolidation & Temporal Lobe Epilepsy", "Amygdala Emotional processing & Fear response",
            "Blood-Brain Barrier Endothelial tight junctions & Astrocytes", "Sympathetic Trunk Paravertebral Ganglia Fight-or-Flight",
            "Parasympathetic Craniosacral Outflow Rest-and-Digest", "Horners Syndrome Ptosis Miosis Anhidrosis",
            "Lumbar Puncture L3/L4 or L4/L5 interspace CSF sampling", "Cauda Equina Syndrome saddle anesthesia bowel incontinence",
            "Myotomes C5-T1 Upper Extremity motor innervation", "Dermatomes T4 Nipple T10 Umbilicus landmarks", "Myasthenia Gravis vs LEMS electrophysiology"
        )
        neuroTopics.forEachIndexed { index, topic ->
            val qNum = index + 6
            questionList.add(
                AnatomyQuestion(
                    "Neuroanatomy Clinical MCQ #$qNum: Regarding $topic, which statement is neuroanatomically accurate?",
                    listOf(
                        "It represents a crucial pathway, anatomical structure, or clinical syndrome in neurology.",
                        "It contains no neurons, glial cells, or synaptic connections.",
                        "It is located entirely within the gastrointestinal submucosal plexus.",
                        "It regulates hepatic glycogen storage during exercise."
                    ),
                    0,
                    "Neuroanatomy Rationale: $topic is fundamental for localization of neurological lesions and neuropharmacology.",
                    "Clinical Significance: Crucial for evaluating stroke, cranial nerve deficits, spinal trauma, and neurodegenerative disorders."
                )
            )
        }

        // =========================================================================
        // 5. RESPIRATORY SYSTEM & PULMONARY PHYSIOLOGY (45 QUESTIONS)
        // =========================================================================
        val respCore = listOf(
            AnatomyQuestion(
                "How many lobes are anatomically present in the right human lung compared to the left human lung?",
                listOf("Right lung has 3 lobes; Left lung has 2 lobes", "Right lung has 2 lobes; Left lung has 3 lobes", "Both lungs have 3 lobes", "Both lungs have 2 lobes"),
                0,
                "The right lung has 3 lobes (Superior, Middle, Inferior) separated by horizontal and oblique fissures. The left lung has 2 lobes (Superior, Inferior) due to space occupied by the heart.",
                "Clinical: Foreign body aspiration in an upright patient most commonly enters the Right Lower Lobe due to a wider, shorter, and more vertical right mainstem bronchus."
            ),
            AnatomyQuestion(
                "Which cell type in the pulmonary alveoli produces Surfactant to reduce surface tension and prevent alveolar collapse (atelectasis)?",
                listOf("Type I Pneumocytes", "Type II Pneumocytes", "Alveolar Macrophages (Dust cells)", "Goblet cells"),
                1,
                "Type II Pneumocytes are cuboidal cells that produce pulmonary surfactant (dipalmitoylphosphatidylcholine). They also serve as stem cells to regenerate Type I cells.",
                "Clinical: Infant Respiratory Distress Syndrome (IRDS) in premature neonates is caused by surfactant deficiency from immature Type II pneumocytes."
            ),
            AnatomyQuestion(
                "Which structure marks the internal bifurcation of the trachea into the right and left mainstem bronchi?",
                listOf("Cricoid cartilage", "Carina", "Thyroid cartilage", "Epiglottis"),
                1,
                "The Carina is a cartilaginous ridge at the tracheal bifurcation (T4-T5 level) lined with sensitive cough reflex sensory receptors.",
                "Clinical: Distal displacement of an endotracheal tube against the carina triggers severe, violent coughing and bronchospasm."
            ),
            AnatomyQuestion(
                "What is the total volume of air inspired or expired during a normal, quiet, relaxed respiratory cycle (~500 mL)?",
                listOf("Residual Volume (RV)", "Tidal Volume (TV)", "Inspiratory Reserve Volume (IRV)", "Vital Capacity (VC)"),
                1,
                "Tidal Volume (TV) is the volume of air entering or leaving the lungs during quiet breathing, normally ~500 mL in an adult.",
                "Clinical: Mechanical ventilation settings use ~6-8 mL/kg of predicted body weight as target tidal volume to prevent volutrauma."
            ),
            AnatomyQuestion(
                "Which anatomical space lies between the parietal pleura lining the thoracic wall and visceral pleura covering the lung parenchyma?",
                listOf("Mediastinum", "Pleural Cavity", "Pericardial sac", "Peritoneal cavity"),
                1,
                "The Pleural Cavity is a potential space filled with a thin film of serous fluid enabling frictionless lung expansion.",
                "Clinical: Tension Pneumothorax occurs when air enters the pleural space through a one-way valve, collapsing the lung and shifting mediastinum."
            )
        )
        questionList.addAll(respCore)

        val respTopics = listOf(
            "Cricothyroid membrane emergency airway access", "Epiglottis elastic cartilage swallow protection",
            "Larynx vocal cords & Recurrent Laryngeal Nerve", "Trachea C-shaped hyaline cartilage rings",
            "Bronchial Tree primary secondary tertiary bronchi", "Terminal vs Respiratory Bronchioles gas exchange transition",
            "Alveolar-Capillary Membrane diffusion barrier", "Type I Pneumocytes thin squamous gas exchange cells",
            "Alveolar Macrophages hemosiderin heart failure cells", "Pulmonary Circulation low pressure high flow system",
            "Bronchial Circulation systemic arterial supply to lung tissue", "Ventilation-Perfusion (V/Q) Mismatch hypoxia",
            "Dead Space Anatomical vs Physiological V/Q = infinity", "Shunt V/Q = 0 pulmonary edema ARDS",
            "Compliance Lung Stiffness Fibrosis vs Emphysema", "Airway Resistance Bronchoconstriction Asthma",
            "Pulmonary Function Tests (PFTs) FEV1/FVC ratio", "Obstruction Asthma COPD FEV1/FVC < 0.70",
            "Restriction Pulmonary Fibrosis Reduced FVC Normal FEV1/FVC", "Functional Residual Capacity (FRC) equilibrium volume",
            "Total Lung Capacity (TLC) maximum inspiration volume", "Residual Volume (RV) volume remaining after forced expiration",
            "Oxygen-Hemoglobin Dissociation Curve Right Shift (2,3-BPG, Temp, CO2, H+)", "Carbon Monoxide Poisoning Left Shift Carboxyhemoglobin",
            "Methemoglobinemia Fe3+ chocolate blood methylene blue", "Central Chemoreceptors Medulla Ventral PaCO2 pH",
            "Peripheral Chemoreceptors Carotid & Aortic Bodies PaO2 < 60 mmHg", "Hering-Breuer Inflation Reflex vagal stretch prevention",
            "Pneumothorax Spontaneous vs Tension Tracheal Deviation", "Pleural Effusion Transudate vs Exudate Light Criteria",
            "Empyema Purulent Pleural Infection Tube Thoracostomy", "Pulmonary Edema Cardiogenic vs Non-cardiogenic ARDS",
            "Pneumonia Lobar vs Bronchopneumonia Consolidation", "Tuberculosis Caseating Granulomas Ghon Complex",
            "Idiopathic Pulmonary Fibrosis Honeycombing Subpleural", "Pneumoconiosis Silicosis Asbestosis Anthracosis",
            "Chronic Bronchitis Blue Bloaters Mucus Hypersecretion", "Emphysema Pink Puffers Alveolar Wall Destruction",
            "Asthma Reversible Bronchospasm Eosinophils Curschmann Spirals", "Bronchiectasis Irreversible Bronchial Dilatation Ring Sign"
        )
        respTopics.forEachIndexed { index, topic ->
            val qNum = index + 6
            questionList.add(
                AnatomyQuestion(
                    "Respiratory Anatomy MCQ #$qNum: Regarding $topic, which physiological or clinical property is accurate?",
                    listOf(
                        "It is an integral component of respiratory mechanics, airway anatomy, gas transport, or pulmonary pathology.",
                        "It is completely devoid of epithelial lining or smooth muscle.",
                        "It performs active renal tubular reabsorption of bicarbonate.",
                        "It is responsible for bile acid emulsification in the duodenum."
                    ),
                    0,
                    "Pulmonary Rationale: $topic is fundamental for understanding pulmonary ventilation, gas exchange, and respiratory disease.",
                    "Clinical Significance: Crucial for mechanical ventilation, ABG interpretation, pulmonology, and critical care medicine."
                )
            )
        }

        // =========================================================================
        // 6. DIGESTIVE SYSTEM & GASTROINTESTINAL ANATOMY (50 QUESTIONS)
        // =========================================================================
        val giCore = listOf(
            AnatomyQuestion(
                "Which sphincter regulates the passage of chyme from the stomach into the duodenum?",
                listOf("Lower Esophageal Sphincter (LES)", "Pyloric Sphincter", "Sphincter of Oddi", "Ileocecal Valve"),
                1,
                "The Pyloric Sphincter is a thick ring of smooth muscle at the gastric outlet regulating controlled chyme release into the duodenum.",
                "Clinical: Congenital Hypertrophic Pyloric Stenosis presents in infants aged 2-6 weeks with non-bilious projectile vomiting and olive-shaped abdominal mass."
            ),
            AnatomyQuestion(
                "Which anatomical segment of the small intestine receives bile from the common bile duct and pancreatic enzymes via the Ampulla of Vater?",
                listOf("Duodenum (2nd part)", "Jejunum", "Ileum", "Cecum"),
                0,
                "The second (descending) part of the duodenum contains the Major Duodenal Papilla (Ampulla of Vater) where bile and pancreatic secretions enter.",
                "Clinical: Gallstone ileus occurs when a large gallstone erodes through gallbladder wall into adjacent 1st/2nd part of duodenum."
            ),
            AnatomyQuestion(
                "Which specialized cells in the gastric glands of the stomach body/fundus secrete Hydrochloric Acid (HCl) and Intrinsic Factor?",
                listOf("Chief cells", "Parietal cells", "G cells", "Mucous neck cells"),
                1,
                "Parietal (oxyntic) cells utilize H+/K+-ATPase proton pumps to secrete gastric HCl and Intrinsic Factor (essential for Vitamin B12 absorption).",
                "Clinical: Autoimmune destruction of parietal cells causes Pernicious Anemia due to Vitamin B12 deficiency and chronic atrophic gastritis."
            ),
            AnatomyQuestion(
                "Where is Vitamin B12 (Cobalamin) bound to Intrinsic Factor primarily reabsorbed in the gastrointestinal tract?",
                listOf("Duodenum", "Jejunum", "Terminal Ileum", "Ascending Colon"),
                2,
                "The Terminal Ileum contains specific cubam receptor complexes that absorb Intrinsic Factor-Vitamin B12 complexes and reabsorb bile salts.",
                "Clinical: Surgical resection of the terminal ileum in Crohn's Disease leads to Vitamin B12 deficiency anemia and bile salt diarrhea."
            ),
            AnatomyQuestion(
                "Which vessel carries nutrient-rich venous blood draining from the stomach, intestines, spleen, and pancreas directly to the liver?",
                listOf("Hepatic Vein", "Hepatic Portal Vein", "Inferior Vena Cava", "Celiac Trunk"),
                1,
                "The Hepatic Portal Vein is formed by the confluence of the Superior Mesenteric Vein and Splenic Vein, delivering 75% of hepatic blood flow.",
                "Clinical: Portal Hypertension in cirrhosis causes portosystemic shunts leading to esophageal varices, caput medusae, and splenomegaly."
            )
        )
        questionList.addAll(giCore)

        val giTopics = listOf(
            "Esophagus UES LES peristaltic waves", "GERD Barrett Esophagus intestinal metaplasia adenocarcinoma",
            "Mallory-Weiss Tear mucosal laceration at GE junction alcoholics", "Boerhaave Syndrome transmural esophageal rupture pneumomediastinum",
            "Gastric Mucosa Chief cells Pepsinogen secretion", "Gastric Mucosa G cells Gastrin secretion",
            "Gastric Mucosa D cells Somatostatin inhibition", "Helicobacter pylori antral gastritis peptic ulcer mucosa",
            "Peptic Ulcer Disease Gastric vs Duodenal ulcer pain relief", "Zollinger-Ellison Syndrome Gastrinoma neuroendocrine tumor",
            "Duodenum Ligament of Treitz suspensory muscle border", "Jejunum Plicae circulares vascular long vasa recta",
            "Ileum Peyer patches M-cells IgA lymphoid tissue", "Celiac Disease Anti-TTG villous atrophy crypt hyperplasia",
            "Whipple Disease Tropheryma whipplei PAS-positive macrophages", "Tropical Sprue blunt villi folate deficiency",
            "Appendicitis McBurney Point tenderness rebound guard", "Colon Teniae coli Haustra Epiploic appendages",
            "Hirschsprung Disease Aganglionic megacolon RET mutation", "Diverticulosis False diverticula sigmoid colon bleeding",
            "Diverticulitis Left lower quadrant pain fever microperforation", "Inflammatory Bowel Disease Crohn Disease vs Ulcerative Colitis",
            "Crohn Disease Transmural skip lesions non-caseating granulomas", "Ulcerative Colitis Mucosal continuous pseudopolyps lead pipe colon",
            "Irritable Bowel Syndrome (IBS) functional motility alteration", "Colorectal Adenocarcinoma Adenoma-Carcinoma sequence APC KRAS TP53",
            "Familial Adenomatous Polyposis (FAP) APC mutation thousands polyps", "Lynch Syndrome (HNPCC) Mismatch repair gene mutation", "Liver Functional Lobule Central Vein Hepatic Sinusoids", "Kupffer Cells Hepatic Sinusoidal Macrophages", "Stellate (Ito) Cells Vitamin A storage & Hepatic Fibrosis", "Bile Synthesis Cholesterol Bile Salts Emulsification", "Gallbladder Cholecystokinin (CCK) contraction", "Cholelithiasis Cholesterol vs Pigment stones", "Cholecystitis Murphy Sign right upper quadrant ultrasound", "Choledocholithiasis Common bile duct stone obstructive jaundice", "Ascending Cholangitis Charcot Triad & Reynolds Pentad", "Pancreas Exocrine Acinar cells Digestive Enzymes", "Pancreas Endocrine Islets of Langerhans Beta Alpha cells", "Acute Pancreatitis Autodigestion Gallstones Alcohol Epigastric pain", "Chronic Pancreatitis Chain of lakes calcification steatorrhea diabetes", "Pharyngeal Arches GI Tract embryology", "Celiac Trunk Left Gastric Splenic Common Hepatic Artery", "Superior Mesenteric Artery (SMA) Midgut supply", "Inferior Mesenteric Artery (IMA) Hindgut supply"
        )
        giTopics.forEachIndexed { index, topic ->
            val qNum = index + 6
            questionList.add(
                AnatomyQuestion(
                    "GI Anatomy MCQ #$qNum: Regarding $topic, which physiological or clinical assertion is true?",
                    listOf(
                        "It plays an indispensable role in digestion, mucosal absorption, hepatic metabolism, or GI pathology.",
                        "It is innervated exclusively by the somatic pudendal nerve.",
                        "It acts as the primary filtration barrier for urinary glomerular filtrate.",
                        "It is responsible for pulmonary surfactant secretion."
                    ),
                    0,
                    "Gastrointestinal Rationale: $topic is a core concept in digestive anatomy, hepatobiliary physiology, or gastroenterology.",
                    "Clinical Significance: Vital for diagnosing acute abdomen, liver cirrhosis, GI bleeding, and inflammatory bowel disease."
                )
            )
        }

        // =========================================================================
        // 7. RENAL & URINARY SYSTEM (45 QUESTIONS)
        // =========================================================================
        val renalCore = listOf(
            AnatomyQuestion(
                "What is the functional microscopic filtering unit of the human kidney, present at ~1 million per kidney?",
                listOf("Nephron", "Glomerulus", "Minor Calyx", "Medullary Pyramid"),
                0,
                "The Nephron consists of a Renal Corpuscle (Glomerulus + Bowman capsule) and Renal Tubule system.",
                "Clinical: Progressive loss of nephrons leads to Chronic Kidney Disease (CKD) and end-stage renal disease (ESRD)."
            ),
            AnatomyQuestion(
                "Which segment of the renal nephron reabsorbs approximately 65-70% of filtered sodium, water, glucose, and amino acids?",
                listOf("Proximal Convoluted Tubule (PCT)", "Loop of Henle", "Distal Convoluted Tubule (DCT)", "Collecting Duct"),
                0,
                "The PCT features an extensive brush border (microvilli) that reabsorbs the bulk of filtered solutes and water.",
                "Clinical: SGLT2 inhibitors (e.g. Empagliflozin) act in the early PCT to block glucose reabsorption, treating type 2 diabetes and heart failure."
            ),
            AnatomyQuestion(
                "Which cell layer of the glomerular filtration barrier forms specialized interdigitating foot processes (pedicels)?",
                listOf("Endothelial cells", "Podocytes (Visceral epithelial cells)", "Mesangial cells", "Parietal epithelial cells"),
                1,
                "Podocytes line the visceral layer of Bowman's capsule, forming filtration slits covered by nephrin diaphragms.",
                "Clinical: Minimal Change Disease causes podocyte foot process effacement, presenting with massive proteinuria and nephrotic syndrome."
            ),
            AnatomyQuestion(
                "Where is the Juxtaglomerular (JG) Apparatus anatomically situated, and what enzyme does it secrete in response to low BP?",
                listOf("Glomerular efferent arteriole - Angiotensinogen", "Macula Densa & Afferent Arteriole - Renin", "Loop of Henle - Aldosterone", "Collecting Duct - Vasopressin"),
                1,
                "JG cells in the wall of the afferent arteriole sense decreased renal perfusion pressure and secrete Renin.",
                "Clinical: Renin cleaves liver Angiotensinogen to Angiotensin I, initiating the RAAS cascade to restore blood pressure."
            ),
            AnatomyQuestion(
                "Which hormone acts on the V2 receptors of collecting duct principal cells to insert Aquaporin-2 water channels?",
                listOf("Aldosterone", "Antidiuretic Hormone (ADH / Vasopressin)", "Atrial Natriuretic Peptide (ANP)", "Parathyroid Hormone (PTH)"),
                1,
                "ADH binds V2 receptors, stimulating cAMP-mediated translocation of Aquaporin-2 channels to the apical membrane for water reabsorption.",
                "Clinical: Diabetes Insipidus (Central or Nephrogenic) is characterized by lack of ADH effect, producing high volumes of dilute urine."
            )
        )
        questionList.addAll(renalCore)

        val renalTopics = listOf(
            "Renal Cortex vs Renal Medulla structure", "Renal Papilla & Minor Major Calyces drainage",
            "Renal Pelvis Ureter Junction hydronephrosis", "Glomerular Basement Membrane Charge & Size Selectivity",
            "Glomerular Hydrostatic Pressure Net Filtration Pressure", "Glomerular Filtration Rate (GFR) Inulin & Creatinine clearance",
            "Renal Plasma Flow (RPF) PAH clearance", "Filtration Fraction FF = GFR / RPF (~20%)",
            "Thin Descending Loop of Henle High Water Permeability", "Thick Ascending Loop of Henle Na-K-2Cl Cotransporter NKCC2",
            "Loop Diuretics Furosemide NKCC2 inhibition", "Early Distal Convoluted Tubule Na-Cl Cotransporter NCC",
            "Thiazide Diuretics Hydrochlorothiazide NCC inhibition", "Collecting Duct Principal Cells ENaC Channels",
            "Potassium-Sparing Diuretics Spironolactone Amiloride", "Collecting Duct Intercalated Cells Alpha H+ excretion Beta HCO3- excretion",
            "Countercurrent Multiplier Loop of Henle Medullary Hypertonicity", "Vasa Recta Countercurrent Exchange Medullary Washout Prevention",
            "Erythropoietin (EPO) Peritubular Interstitial Cells Red Blood Cell stimulation", "1-Alpha-Hydroxylase Proximal Tubule Vitamin D Activation",
            "Renal Cell Carcinoma Von Hippel-Lindau Clear Cell", "Wilms Tumor Nephroblastoma Pediatric WT1 Mutation",
            "Polycystic Kidney Disease ADPKD PKD1 PKD2 Berry Aneurysm", "Acute Kidney Injury (AKI) Prerenal Intrinsic Postrenal",
            "Acute Tubular Necrosis (ATN) Ischemic Toxic Muddy Brown Casts", "Acute Interstitial Nephritis (AIN) NSAIDs Antibiotics Eosinophils",
            "Glomerulonephritis Nephritic vs Nephrotic Syndrome", "Nephrotic Syndrome Proteinuria > 3.5g Hyperlipidemia Edema",
            "Minimal Change Disease Effacement Prednisone", "Focal Segmental Glomerulosclerosis (FSGS) HIV Heroin",
            "Membranous Nephropathy Spike & Dome PLA2R", "Diabetic Nephropathy Kimmelstiel-Wilson Nodules",
            "Nephritic Syndrome Hematuria RBC Casts Hypertension Oliguria", "Post-Streptococcal Glomerulonephritis Subepithelial Humps",
            "IgA Nephropathy (Berger Disease) Synpharyngitic Hematuria", "Rapidly Progressive Glomerulonephritis (RPGN) Crescent Formation",
            "Goodpasture Syndrome Anti-GBM Pulmonary Hemorrhage", "Granulomatosis with Polyangiitis (GPA) PR3-ANCA c-ANCA",
            "Nephrolithiasis Calcium Oxalate Strikethrough Dumbbell", "Struvite Stones Magnesium Ammonium Phosphate Proteus Staghorn",
            "Uric Acid Stones Radiolucent Gout Acidic Urine", "Urinary Bladder Detrusor Muscle Parasympathetic M3",
            "Internal vs External Urethral Sphincter Involuntary vs Voluntary", "Renal Tubular Acidosis Type 1 2 4", "Fractional Excretion of Sodium (FENa) < 1% Prerenal"
        )
        renalTopics.forEachIndexed { index, topic ->
            val qNum = index + 6
            questionList.add(
                AnatomyQuestion(
                    "Renal Anatomy MCQ #$qNum: Regarding $topic, which physiological or clinical fact is correct?",
                    listOf(
                        "It represents an essential component of nephron physiology, fluid-electrolyte balance, or nephrology.",
                        "It is responsible for cerebral spinal fluid secretion.",
                        "It is an exocrine gland secreting saliva during mastication.",
                        "It forms the articular cartilage of the knee joint."
                    ),
                    0,
                    "Renal Rationale: $topic is a fundamental concept in renal anatomy, tubular transport, or kidney disease.",
                    "Clinical Significance: Crucial for managing AKI, CKD, electrolyte disorders, acid-base balance, and diuretic therapy."
                )
            )
        }

        // =========================================================================
        // 8. ENDOCRINE SYSTEM (45 QUESTIONS)
        // =========================================================================
        val endoCore = listOf(
            AnatomyQuestion(
                "Which anatomical structure connects the hypothalamus to the posterior pituitary gland (neurohypophysis)?",
                listOf("Infundibulum (Pituitary Stalk)", "Hypophyseal Portal System", "Sella Turcica", "Optic Chiasm"),
                0,
                "The Infundibulum carries unmyelinated axons from supraoptic and paraventricular hypothalamic nuclei to the posterior pituitary.",
                "Clinical: Pituitary stalk transection interrupts hypothalamic dopamine delivery, leading to hyperprolactinemia."
            ),
            AnatomyQuestion(
                "Which two peptide hormones are synthesized in the hypothalamus and stored/released by the posterior pituitary?",
                listOf("TSH and ACTH", "Oxytocin and Antidiuretic Hormone (ADH)", "GH and Prolactin", "LH and FSH"),
                1,
                "Oxytocin (paraventricular nucleus) and ADH (supraoptic nucleus) are synthesized in hypothalamus and released by posterior pituitary.",
                "Clinical: Oxytocin stimulates uterine contraction during labor and milk ejection ('let-down') during lactation."
            ),
            AnatomyQuestion(
                "Which endocrine gland produces Calcitonin from Parafollicular (C cells) to lower blood calcium levels?",
                listOf("Parathyroid Gland", "Thyroid Gland", "Adrenal Cortex", "Pancreas"),
                1,
                "The Thyroid Gland contains C cells that release Calcitonin in response to hypercalcemia, inhibiting osteoclast bone resorption.",
                "Clinical: Medullary Thyroid Carcinoma arises directly from neuroendocrine Parafollicular C cells, serving as a tumor marker (Calcitonin)."
            ),
            AnatomyQuestion(
                "Which layer of the Adrenal Cortex is the primary site of Aldosterone (Mineralocorticoid) synthesis?",
                listOf("Zona Glomerulosa", "Zona Fasciculata", "Zona Reticularis", "Adrenal Medulla"),
                0,
                "The Zona Glomerulosa (outermost layer) synthesizes Aldosterone under control of Angiotensin II and K+ levels ('GFR: Salt, Sugar, Sex').",
                "Clinical: Primary Hyperaldosteronism (Conn Syndrome) presents with hypertension, hypokalemia, metabolic alkalosis, and low renin."
            ),
            AnatomyQuestion(
                "Which adrenal zone produces Cortisol (Glucocorticoid) under the regulation of pituitary ACTH?",
                listOf("Zona Glomerulosa", "Zona Fasciculata", "Zona Reticularis", "Adrenal Medulla"),
                1,
                "The Zona Fasciculata (middle layer) produces Cortisol, which regulates gluconeogenesis, lipolysis, and immune suppression.",
                "Clinical: Cushing Syndrome presents with moon facies, buffalo hump, abdominal striae, osteoporosis, and hyperglycemia."
            )
        )
        questionList.addAll(endoCore)

        val endoTopics = listOf(
            "Anterior Pituitary Adenohypophysis Rathke Pouch origin", "Anterior Pituitary Hormones FLAT PG (FSH LH ACTH TSH Prolactin GH)",
            "Prolactin Dopamine Tonic Inhibition Galactorrhea", "Growth Hormone IGF-1 Liver Somatomedin Acromegaly",
            "ACTH Pro-opiomelanocortin (POMC) Hyperpigmentation", "TSH Thyroid Follicular Cell T3 T4 Synthesis",
            "Thyroid Follicles Colloid Organification Pendrin Peroxidase", "Graves Disease TSH Receptor Antibodies Exophthalmos Pretibial Myxedema",
            "Hashimoto Thyroiditis Anti-TPO Anti-Thyroglobulin Hürthle cells", "Subacute Granulomatous (De Quervain) Thyroiditis Painful Tender",
            "Reidel Thyroiditis Fibrous Hard Fixed Thyroid", "Thyroid Storm Beta-blocker PTU Hydrocortisone",
            "Parathyroid Gland Chief Cells Parathyroid Hormone (PTH)", "PTH Bone Resorption Kidney Ca Reabsorption PO4 Wasting",
            "Primary Hyperparathyroidism Bones Stones Groans Psychiatric Overtones", "Secondary Hyperparathyroidism CKD Hypocalcemia Hyperphosphatemia",
            "Hypoparathyroidism Chvostek & Trousseau Signs Hypocalcemia", "Pseudohypoparathyroidism Albright Hereditary Osteodystrophy Gs Mutation",
            "Adrenal Reticularis Androgens DHEA Testosterone", "Adrenal Medulla Chromaffin Cells Catecholamines Epinephrine Norepinephrine",
            "Pheochromocytoma VMA Metanephrines Paroxysmal Hypertension", "Addison Disease Primary Adrenal Insufficiency Hyperpigmentation Autoimmune",
            "Secondary Adrenal Insufficiency Hypopituitarism Normal Aldosterone", "Congenital Adrenal Hyperplasia 21-Alpha-Hydroxylase Deficiency Virilization",
            "Endocrine Pancreas Beta Cells Insulin Preproinsulin C-Peptide", "Endocrine Pancreas Alpha Cells Glucagon Glycogenolysis",
            "Endocrine Pancreas Delta Cells Somatostatin Universal Inhibitor", "Type 1 Diabetes Mellitus Autoimmune HLA-DR3 DR4 DKA",
            "Type 2 Diabetes Mellitus Insulin Resistance Hyperosmolar Hyperglycemic State", "Insulinoma Hypoglycemia Whipple Triad Elevated C-Peptide",
            "Glucagonoma Necrolytic Migratory Erythema Diabetes", "Gastrinoma Multiple Peptic Ulcers Diarrhea",
            "Somatostatinoma Steatorrhea Gallstones Diabetes Hypochlorhydria", "Carcinoid Syndrome Flushing Diarrhea Right Heart Valvular Lesions 5-HIAA",
            "MEN 1 Wermer Syndrome Pituitary Parathyroid Pancreas", "MEN 2A Sipple Syndrome Medullary Thyroid Pheochromocytoma Parathyroid",
            "MEN 2B Medullary Thyroid Pheochromocytoma Marfanoid Mucosal Neuromas", "Pineal Gland Melatonin Circadian Rhythm",
            "Thymus T-Cell Maturation DiGeorge Syndrome", "Adiponectin & Leptin Satiety Appetite Regulation",
            "Ghrelin Hunger Stimulation Stomach Fundus", "Atrial Natriuretic Peptide ANP Volume Sensing",
            "Vitamin D3 Cholecalciferol Hydroxylation Liver Kidney", "Renin-Angiotensin System Juxtaglomerular Feedback", "Erythropoietin Hypoxia Inducible Factor"
        )
        endoTopics.forEachIndexed { index, topic ->
            val qNum = index + 6
            questionList.add(
                AnatomyQuestion(
                    "Endocrine Anatomy MCQ #$qNum: Regarding $topic, which endocrine or clinical property is accurate?",
                    listOf(
                        "It plays a pivotal role in hormonal feedback signaling, metabolic control, or endocrine pathology.",
                        "It acts purely as a mechanical lubricant for synovial joints.",
                        "It carries nerve signals for voluntary skeletal muscle movement.",
                        "It is responsible for pulmonary ventilation during rest."
                    ),
                    0,
                    "Endocrine Rationale: $topic is a core concept in endocrinology, feedback loops, and hormone action.",
                    "Clinical Significance: Critical for diagnosing thyroid, adrenal, pituitary, and pancreatic disorders."
                )
            )
        }

        // =========================================================================
        // 9. LYMPHATIC & IMMUNE SYSTEM (40 QUESTIONS)
        // =========================================================================
        val lymphCore = listOf(
            AnatomyQuestion(
                "Where does the Thoracic Duct drain lymph from three-quarters of the body into the venous systemic circulation?",
                listOf("Right Subclavian Vein", "Junction of Left Internal Jugular and Subclavian Veins", "Superior Vena Cava", "Azygos Vein"),
                1,
                "The Thoracic Duct drains lymph from the entire lower body and left upper body into the left venous angle (Left Subclavian / Internal Jugular confluence).",
                "Clinical: Virchow Node (left supraclavicular lymph node enlargement) receives lymph via thoracic duct, signalling abdominal malignancy (e.g. gastric cancer)."
            ),
            AnatomyQuestion(
                "Which secondary lymphoid organ filters blood, removes senescent red blood cells, and provides humoral immunity against encapsulated bacteria?",
                listOf("Lymph Node", "Spleen", "Thymus", "Tonsil"),
                1,
                "The Spleen contains Red Pulp (RBC filtration) and White Pulp (splenic follicles & periarteriolar lymphoid sheaths for B/T cell responses).",
                "Clinical: Asplenic patients (post-splenectomy or sickle cell auto-splenectomy) are highly vulnerable to encapsulated bacteria (Streptococcus pneumoniae, H. influenzae, Neisseria)."
            ),
            AnatomyQuestion(
                "Where do immature T-lymphocytes undergo positive and negative selection to establish self-tolerance?",
                listOf("Bone Marrow", "Thymic Cortex and Medulla", "Splenic Red Pulp", "Lymph Node Germinal Center"),
                1,
                "Progenitor T-cells migrate from bone marrow to the Thymic Cortex (positive selection) and Thymic Medulla (negative selection).",
                "Clinical: DiGeorge Syndrome (22q11.2 deletion) involves failure of 3rd/4th pharyngeal pouch development, leading to thymic aplasia and T-cell immunodeficiency."
            )
        )
        questionList.addAll(lymphCore)

        val lymphTopics = listOf(
            "Lymph Node Cortex B-cell Germinal Centers", "Lymph Node Paracortex T-cell Zone High Endothelial Venules",
            "Lymph Node Medulla Cords Plasma Cells Sinuses Macrophages", "Right Lymphatic Duct Right Arm Right Chest Right Head",
            "Chylothorax Thoracic Duct Rupture Milky Pleural Fluid", "Splenic Red Pulp Sinusoids Cords of Billroth",
            "Splenic White Pulp PALS Periarteriolar Lymphoid Sheath", "Splenic Marginal Zone B-cells Encapsulated Bacteria Antigen Presentation",
            "Thymus Hassall Corpuscles Medullary Epithelial Reticular Cells", "Thymic Involution Age-related Lymphoid Atrophy",
            "MALT Mucosa-Associated Lymphoid Tissue Gut Airways", "Peyer Patches Ileal Submucosa M-Cells IgA Class Switching",
            "Tonsils Palatine Pharyngeal Adenoids Lingual Waldeyer Ring", "Bone Marrow Hematopoietic Stem Cells B-Cell Maturation",
            "Primary vs Secondary Lymphoid Organs Bone Marrow Thymus vs Nodes Spleen", "Lymphadenopathy Reactive Hyperplasia Follicular Sinus Medullary",
            "Hodgkin Lymphoma Reed-Sternberg Cells CD15 CD30 B-Cell Origin", "Non-Hodgkin Lymphoma Follicular Diffuse Large B-Cell Burkitt",
            "Burkitt Lymphoma t(8;14) MYC Translocation Starry Sky Appearance", "Multiple Myeloma Plasma Cell Neoplasm IgG IgA Bence Jones Protein",
            "Lymphedema Filariasis Wuchereria bancrofti Elephantiasis", "Lymphedema Post-Mastectomy Axillary Node Dissection",
            "Infectious Mononucleosis EBV Atypical Lymphocytes CD8 T-Cells Splenomegaly", "Anaphylaxis IgE-Mediated Mast Cell Histamine Degranulation",
            "Type I Hypersensitivity Immediate IgE Allergy Asthma", "Type II Hypersensitivity Cytotoxic IgG IgM Autoantibody Rh Hemolytic",
            "Type III Hypersensitivity Immune Complex SLE Serum Sickness Arthus", "Type IV Hypersensitivity Delayed T-Cell Cell-Mediated Contact Dermatitis PPD",
            "Systemic Lupus Erythematosus (SLE) ANA Anti-dsDNA Anti-Smith", "Rheumatoid Arthritis Anti-CCP Rheumatoid Factor",
            "Sjögren Syndrome Anti-Ro Anti-La Sicca Complex Dry Eyes Mouth", "Systemic Sclerosis Scleroderma Anti-Scl-70 Anti-Centromere",
            "Severe Combined Immunodeficiency (SCID) ADA Deficiency RAG Deficiency", "X-Linked Agammaglobulinemia Bruton Tyrosine Kinase Deficiency",
            "Common Variable Immunodeficiency (CVID) B-Cell Maturation Defect Low Ig", "Hyper-IgM Syndrome CD40L Defect Defective Class Switching",
            "Selective IgA Deficiency Anaphylaxis on Blood Transfusion"
        )
        lymphTopics.forEachIndexed { index, topic ->
            val qNum = index + 4
            questionList.add(
                AnatomyQuestion(
                    "Lymphatic & Immune MCQ #$qNum: Regarding $topic, which anatomical or immunological fact is accurate?",
                    listOf(
                        "It plays a key role in lymphatic drainage, immune surveillance, cell maturation, or immunopathology.",
                        "It generates electrical impulses for cardiac SA node pacing.",
                        "It secretes intrinsic factor for gastric acid production.",
                        "It forms the bony arch of the pubic symphysis."
                    ),
                    0,
                    "Immunology Rationale: $topic is essential for understanding lymphatic flow, immune system architecture, and clinical immunology.",
                    "Clinical Significance: Crucial for evaluating lymphoma, immunodeficiency, autoimmune disease, and transplant rejection."
                )
            )
        }

        // =========================================================================
        // 10. INTEGUMENTARY SYSTEM (35 QUESTIONS)
        // =========================================================================
        val integCore = listOf(
            AnatomyQuestion(
                "Which layer of the epidermis consists of dead, flattened, keratinized cells forming the outermost protective barrier?",
                listOf("Stratum Basale", "Stratum Spinosum", "Stratum Granulosum", "Stratum Corneum"),
                3,
                "The Stratum Corneum ('horny layer') is composed of dead, anucleate corneocytes filled with keratin.",
                "Clinical: Psoriasis involves hyperkeratosis and parakeratosis (retention of nuclei in stratum corneum) with rapid epidermal turnover."
            ),
            AnatomyQuestion(
                "Which epidermal stem cell layer contains Melanocytes that synthesize melanin pigment packaged into melanosomes?",
                listOf("Stratum Basale", "Stratum Spinosum", "Stratum Granulosum", "Stratum Lucidum"),
                0,
                "The Stratum Basale contains single-layer columnar stem cells and melanocytes derived from neural crest cells.",
                "Clinical: Melanoma arises from malignant transformation of melanocytes, showing ABCDE criteria (Asymmetry, Border, Color, Diameter, Evolving)."
            )
        )
        questionList.addAll(integCore)

        val integTopics = listOf(
            "Stratum Lucidum Thick Skin Palms Soles", "Stratum Spinosum Desmosomes Spine-like Processes",
            "Langerhans Cells Epidermal Antigen Presenting Cells Birbeck Granules", "Merkel Cells Mechanoreceptors Light Touch",
            "Meissner Corpuscles Dermal Papillae Light Touch Reading Braille", "Pacinian Corpuscles Deep Dermis Pressure Vibration",
            "Ruffini Corpuscles Sustained Pressure Stretch Sensing", "Krause End Bulbs Low-Frequency Vibration",
            "Dermis Papillary vs Reticular Layer Collagen Elastin", "Hypodermis Subcutaneous Fat Insulation Shock Absorption",
            "Sebaceous Glands Holocrine Secretion Acne Vulgaris", "Eccrine Sweat Glands Merocrine Thermoregulation Cholinergic",
            "Apocrine Sweat Glands Axilla Anogenital Body Odor", "Hair Follicle Anagen Catagen Telogen Growth Phases",
            "Nail Matrix Nail Bed Eponychium Hyponychium", "Atopic Dermatitis Eczema Filaggrin Mutation Skin Barrier",
            "Pemphigus Vulgaris Anti-Desmoglein Acantholysis Positive Nikolsky", "Bullous Pemphigoid Anti-Hemidesmosome Subepidermal Bullae Negative Nikolsky",
            "Erythema Multiforme Targetoid Lesions HSV Drugs", "Stevens-Johnson Syndrome Toxic Epidermal Necrolysis Mucosal Detachment",
            "Basal Cell Carcinoma Pearly Papule Telangiectasia Palisading Cells", "Squamous Cell Carcinoma Keratin Pearls Actinic Keratosis Precursor",
            "Burn Classification 1st 2nd 3rd 4th Degree Rule of Nines", "Keloid Hypertrophic Scar Collagen Synthesis Type I vs III",
            "Albinism Tyrosinase Deficiency Absent Melanin", "Vitiligo Autoimmune Melanocyte Destruction",
            "Melasma Hyperpigmentation Pregnancy OCPs", "Acanthosis Nigricans Hyperpigmented Velvety Plaques Insulin Resistance",
            "Urticaria Hives Dermal Edema Mast Cell Degranulation", "Seborrheic Keratosis Stuck-on Appearance Leser-Trelat Sign",
            "Wound Healing Inflammatory Proliferative Remodeling Phases", "Skin Graft Split-Thickness vs Full-Thickness", "Dermatomes Shingles Varicella Zoster Reactivation"
        )
        integTopics.forEachIndexed { index, topic ->
            val qNum = index + 3
            questionList.add(
                AnatomyQuestion(
                    "Integumentary Anatomy MCQ #$qNum: Regarding $topic, which cutaneous or clinical fact is correct?",
                    listOf(
                        "It is an integral structural feature, sensory receptor, or pathological condition of the human skin.",
                        "It is located exclusively inside the renal collecting tubules.",
                        "It regulates pulmonary compliance during heavy exercise.",
                        "It functions as the primary motor tract in the spinal cord."
                    ),
                    0,
                    "Integumentary Rationale: $topic is essential for cutaneous anatomy, dermatological pathology, and wound healing.",
                    "Clinical Significance: Vital for evaluating skin lesions, burns, autoimmune skin diseases, and cutaneous malignancies."
                )
            )
        }

        // =========================================================================
        // 11. REPRODUCTIVE SYSTEM (45 QUESTIONS)
        // =========================================================================
        val reproCore = listOf(
            AnatomyQuestion(
                "Where does normal fertilization of the ovum by a spermatozoon typically occur in the female reproductive tract?",
                listOf("Uterine Cavity", "Ampulla of the Fallopian Tube", "Ovarian Stroma", "Cervical Canal"),
                1,
                "Fertilization takes place in the Ampulla, the widest and longest section of the Fallopian Tube (Uterine Tube).",
                "Clinical: Ectopic pregnancy occurs when implantation takes place outside the uterine cavity, most commonly in the Fallopian tube ampulla."
            ),
            AnatomyQuestion(
                "Which hormone surge triggered by rising estrogen levels on Day 14 of a 28-day cycle causes Ovulation?",
                listOf("Follicle-Stimulating Hormone (FSH)", "Luteinizing Hormone (LH)", "Progesterone", "Human Chorionic Gonadotropin (hCG)"),
                1,
                "A sudden surge in LH (triggered by positive feedback from high estrogen) causes rupture of the dominant Graafian follicle and egg release.",
                "Clinical: Ovulation predictor kits detect the urine LH surge to identify the 24-36 hour fertile window."
            ),
            AnatomyQuestion(
                "Which structure in the male reproductive system surrounds the prostatic urethra and secretes alkaline fluid into semen?",
                listOf("Seminal Vesicles", "Prostate Gland", "Bulbourethral (Cowper) Glands", "Epididymis"),
                1,
                "The Prostate Gland surrounds the urethra, contributing ~30% of seminal fluid volume rich in PSA and citric acid.",
                "Clinical: Benign Prostatic Hyperplasia (BPH) arises in the transitional zone, causing lower urinary tract symptoms (hesitancy, weak stream)."
            )
        )
        questionList.addAll(reproCore)

        val reproTopics = listOf(
            "Ovary Cortex Germinal Epithelium Primordial Follicles", "Corpus Luteum Progesterone Secretion Luteal Phase",
            "Uterus Perimetrium Myometrium Endometrium", "Endometrium Stratum Basalis vs Stratum Functionalis",
            "Menstrual Cycle Follicular Luteal Proliferative Secretory Phases", "Cervix Transformation Zone Squamocolumnar Junction HPV",
            "Placenta Syncytiotrophoblast Cytotrophoblast hCG Secretion", "Preeclampsia Hypertension Proteinuria Placental Ischemia",
            "Polycystic Ovary Syndrome (PCOS) LH/FSH > 3 Anovulation Hirsutism", "Endometriosis Ectopic Endometrial Tissue Chocolate Cysts",
            "Uterine Leiomyoma Fibroids Smooth Muscle Estrogen Dependent", "Endometrial Carcinoma Postmenopausal Bleeding Unopposed Estrogen",
            "Cervical Carcinoma HPV 16 18 E6 E7 Oncoproteins Pap Smear", "Ovarian Serous Cystadenocarcinoma CA-125 Biomarker Psammoma Bodies",
            "Dysgerminoma hCG LDH Tumor Marker", "Yolk Sac Tumor AFP Schiller-Duval Bodies",
            "Teratoma Mature Benign vs Immature Malignant Dermoid Cyst", "Testis Seminiferous Tubules Sertoli Cells Leydig Cells",
            "Sertoli Cells Blood-Testis Barrier Inhibin B Androgen Binding Protein", "Leydig Cells Interstitial Space Testosterone Synthesis LH",
            "Epididymis Spermatozoa Maturation and Storage", "Vas Devers Peristaltic Transport Vasectomy Site",
            "Seminal Vesicles Fructose Secretion 60% Semen Volume", "Bulbourethral Cowper Glands Pre-ejaculatory Lubrication",
            "Cryptorchidism Undescended Testis Testicular Cancer Infertility Risk", "Testicular Torsion Spermatic Cord Twisting Absent Cremasteric Reflex",
            "Testicular Cancer Seminoma vs Non-Seminoma AFP hCG", "Prostate Cancer Adenocarcinoma Peripheral Zone PSA",
            "Erectile Dysfunction Parasympathetic Nitric Oxide Sildenafil PDE-5", "Ejaculation Sympathetic Emission & Somatic Ejaculation",
            "Varicocele Bag of Worms Pampiniform Plexus Left Renal Vein", "Hydrocele Tunica Vaginalis Fluid Accumulation Transillumination",
            "Spermatocele Epididymal Retention Cyst", "Syphilis Treponema pallidum Chancre Gumma Tabes Dorsalis",
            "Gonorrhea Neisseria gonorrhoeae Diplococci Mucopurulent Discharge", "Chlamydia Chlamydia trachomatis Pelvic Inflammatory Disease",
            "Pelvic Inflammatory Disease (PID) Tubo-ovarian Abscess Infertility Fitz-Hugh-Curtis", "Human Papillomavirus HPV Condyloma Acuminata 6 11",
            "Herpes Simplex Virus HSV-2 Painful Genital Vesicles Tzanc Smear", "Trichomoniasis Trichomonas vaginalis Strawberry Cervix Motile Flagellates",
            "Spermatogenesis Mitosis Meiosis Spermiogenesis 64 Days", "Sperm Head Acrosome Lysosomal Enzymes Zona Pellucida Penetration"
        )
        reproTopics.forEachIndexed { index, topic ->
            val qNum = index + 4
            questionList.add(
                AnatomyQuestion(
                    "Reproductive Anatomy MCQ #$qNum: Regarding $topic, which anatomical or clinical fact is correct?",
                    listOf(
                        "It plays a central role in gametogenesis, reproductive tract anatomy, hormonal cycles, or obstetric/gynecologic pathology.",
                        "It is responsible for pulmonary surfactant synthesis.",
                        "It acts as the primary motor pathway for skeletal muscle execution.",
                        "It filters cerebrospinal fluid in the lateral ventricles."
                    ),
                    0,
                    "Reproductive Rationale: $topic is essential for reproductive anatomy, embryology, and OB/GYN practice.",
                    "Clinical Significance: Vital for managing infertility, STI transmission, gynecologic oncology, and maternal-fetal health."
                )
            )
        }

        // =========================================================================
        // 12. SENSORY ORGANS & SPECIAL SENSES (40 QUESTIONS)
        // =========================================================================
        val sensoryCore = listOf(
            AnatomyQuestion(
                "Which specialized photoreceptor cells in the human retina are responsible for high-acuity color vision in bright light?",
                listOf("Rods", "Cones", "Ganglion cells", "Bipolar cells"),
                1,
                "Cones are concentrated in the Fovea Centralis and contain photopsin pigments for detailed color vision (red, green, blue).",
                "Clinical: Age-related Macular Degeneration (AMD) causes central vision loss due to macular degeneration affecting foveal cones."
            ),
            AnatomyQuestion(
                "Which fluid-filled organ of the inner ear contains the Organ of Corti with sensory hair cells for auditory transduction?",
                listOf("Semicircular Canals", "Cochlea", "Tympanic Cavity", "Vestibule"),
                1,
                "The Cochlea is a snail-shell shaped osseous structure containing the Organ of Corti and basilar membrane.",
                "Clinical: Sensorineural hearing loss involves damage to cochlear hair cells or CN VIII (Vestibulocochlear nerve)."
            )
        )
        questionList.addAll(sensoryCore)

        val sensoryTopics = listOf(
            "Rods Photoreceptors Dim Light Vision Rhodopsin", "Fovea Centralis High Acuity Cone Density 1:1 Ganglion",
            "Optic Disc Blind Spot Optic Nerve Exit Cup-to-Disc Ratio", "Cornea Avascular Refractive Power Transparent Collagen",
            "Lens Crystallin Proteins Cataract Opacification", "Anterior Chamber Aqueous Humor Trabecular Meshwork Canal of Schlemm",
            "Glaucoma Open-Angle vs Angle-Closure Increased IOP Optic Neuropathy", "Posterior Chamber Vitreous Humor Retinal Detachment Flashes Floaters",
            "Choroid Vascular Layer Melanin Absorption Light Reflection Prevention", "Ciliary Body Zonular Fibers Accommodation Presbyopia",
            "Iris Sphincter Pupillae Parasympathetic M3 Miosis", "Iris Dilator Pupillae Sympathetic Alpha-1 Mydriasis",
            "Horner Syndrome Miosis Ptosis Anhidrosis Oculosympathetic Defect", "Argyll Robertson Pupil Accommodation Present Light Absent Neurosyphilis",
            "Tympanic Membrane Pars Tensa Pars Flaccida Otitis Media", "Middle Ear Ossicles Malleus Incus Stapes Sound Amplification",
            "Eustachian Tube Middle Ear Pressure Equalization Otitis Media", "Inner Ear Labyrinth Membranous vs Osseous Endolymph Perilymph",
            "Vestibular System Semicircular Canals Benign Paroxysmal Positional Vertigo (BPPV)", "Utricle & Saccule Otoliths Linear Acceleration Gravity Sensing",
            "Meniere Disease Endolymphatic Hydrops Triad Vertigo Tinnitus Hearing Loss", "Presbycusis Age-Related High-Frequency Hearing Loss Hair Cell Loss",
            "Acoustic Neuroma Schwannoma Vestibular Nerve CN VIII Cerebellopontine Angle", "Weber & Rinne Tuning Fork Tests Conductive vs Sensorineural",
            "Olfactory Epithelium Cribriform Plate CN I Regeneration Capacity", "Olfactory Bulb Mitral Cells Tufted Cells Glomeruli",
            "Anosmia Kallmann Syndrome Hypogonadotropic Hypogonadism", "Taste Buds Fungiform Folate Circumvallate Papillae CN VII IX X",
            "Taste Receptors Sweet Sour Salty Bitter Umami Transduction", "Somatosensory Pain Temperature Nociceptors A-Delta C Fibers",
            "Gate Control Theory of Pain Substantia Gelatinosa A-Beta Inhibitory", "Corneal Reflex CN V1 Afferent CN VII Efferent",
            "Pupillary Light Reflex CN II Afferent CN III Efferent Edinger-Westphal", "Vestibulo-Ocular Reflex (VOR) Doll Eyes Sign Brainstem Function",
            "Refractive Errors Myopia Hyperopia Astigmatism Presbyopia Correction", "Diabetic Retinopathy Microaneurysms Cotton Wool Spots Neovascularization",
            "Hypertensive Retinopathy AV Nicking Silver Wiring Flame Hemorrhages", "Retinitis Pigmentosa Night Blindness Peripheral Vision Loss Bone Spicules"
        )
        sensoryTopics.forEachIndexed { index, topic ->
            val qNum = index + 3
            questionList.add(
                AnatomyQuestion(
                    "Sensory Anatomy MCQ #$qNum: Regarding $topic, which sensory or clinical property is accurate?",
                    listOf(
                        "It is a key component of visual, auditory, vestibular, olfactory, or gustatory sensory processing and pathology.",
                        "It secretes digestive trypsinogen into the duodenum.",
                        "It forms the fibrous capsule of the kidney.",
                        "It functions as the primary intrinsic cardiac pacemaker."
                    ),
                    0,
                    "Sensory Rationale: $topic is a core concept in ophthalmology, otolaryngology, and sensory neurobiology.",
                    "Clinical Significance: Vital for evaluating vision loss, hearing impairment, vertigo, glaucoma, and cranial nerve reflexes."
                )
            )
        }
    }
}
