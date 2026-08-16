package com.drtahir.studentkit.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 4
 * Category: CPR, Trauma & Clinics (30 100% Unique MCQs)
 * Covers AHA BLS/ACLS protocols, acute trauma management, anaphylaxis, severe hypoglycemia, asthma, ACS & triage protocols.
 */
object Hajj1000Part4 {

    fun getCprTraumaQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the correct rate and compression depth for adult Cardiopulmonary Resuscitation (CPR) according to AHA Guidelines?",
                options = listOf(
                    "80 compressions per minute at a depth of 1 inch (2.5 cm)",
                    "100 to 120 compressions per minute at a depth of 2 to 2.4 inches (5 to 6 cm) with full chest recoil",
                    "150 compressions per minute at a depth of 3.5 inches (9 cm)",
                    "60 compressions per minute with continuous ventilation"
                ),
                correctIndex = 1,
                explanation = "AHA CPR Guidelines mandate a chest compression rate of 100-120 compressions/min and a depth of 2 to 2.4 inches (5 to 6 cm) to maintain optimal coronary and cerebral perfusion pressure during cardiac arrest.",
                reference = "AHA Guidelines for CPR and Emergency Cardiovascular Care"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the standard adult dose, concentration, and injection route for Epinephrine (Adrenaline) in severe acute anaphylaxis?",
                options = listOf(
                    "1.0 mg Intravenously (1:10,000 dilution)",
                    "0.3 mg Intramuscularly (1:1,000 dilution) injected into the anterolateral mid-thigh",
                    "5.0 mg Orally in warm water",
                    "0.5 mg Subcutaneously (1:100,000 dilution)"
                ),
                correctIndex = 1,
                explanation = "Anaphylactic shock requires immediate intramuscular (IM) Epinephrine 0.3 mg (1:1,000) into the anterolateral mid-thigh. IM administration achieves rapid, reliable peak plasma concentration compared to SC or unmonitored IV routes.",
                reference = "World Allergy Organization (WAO) Anaphylaxis Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "A pilgrim sustained a compound leg fracture with active, bright red spurting arterial bleeding in Mina. What is the immediate life-saving priority action?",
                options = listOf(
                    "Reduce and splint the bone fracture immediately before managing bleeding",
                    "Apply direct pressure to the wound with a sterile dressing; if bleeding is uncontrolled, immediately apply a tourniquet 2 to 3 inches proximal to the wound",
                    "Wash the bone ends thoroughly with tap water",
                    "Administer intramuscular analgesics and elevate the leg"
                ),
                correctIndex = 1,
                explanation = "Life-threatening arterial hemorrhage takes absolute precedence over fracture immobilization (Stop the Bleed protocol). Direct pressure is applied first; if hemorrhage persists, a commercial tourniquet is applied proximal to the wound.",
                reference = "ATLS & Stop the Bleed Hemorrhage Control Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "An elderly diabetic pilgrim is brought to a field clinic sweating profusely, trembling, and disoriented. Capillary glucose check shows 42 mg/dL. What is the priority intervention?",
                options = listOf(
                    "Administer 10 units of rapid-acting subcutaneous insulin",
                    "Administer 50 mL of 50% Dextrose (or 100 mL of 25% Dextrose) intravenously immediately",
                    "Give hot sugar-free tea and observe for 2 hours",
                    "Apply active evaporative cooling sprays for heat stroke"
                ),
                correctIndex = 1,
                explanation = "Severe symptomatic hypoglycemia (<50 mg/dL) with altered mental status requires rapid IV administration of concentrated dextrose (D50W or D25W) to restore blood glucose and prevent permanent neuroglycopenic brain injury.",
                reference = "American Diabetes Association (ADA) Emergency Care"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "A pilgrim with known asthma develops intense wheezing, tachypnea, and accessory muscle use during a sandstorm in Mina. What is the first-line medication of choice?",
                options = listOf(
                    "Inhaled short-acting beta-2 agonist (e.g., Salbutamol / Albuterol) via spacer or nebulizer",
                    "Intravenous Epinephrine 1.0 mg",
                    "Oral Amoxicillin 500 mg",
                    "Inhaled Fluticasone steroid exclusively"
                ),
                correctIndex = 0,
                explanation = "Acute bronchospasm (asthma exacerbation) is treated first-line with inhaled short-acting beta-2 agonists (SABA) like Salbutamol to achieve rapid bronchodilation.",
                reference = "Global Initiative for Asthma (GINA) Management Guide"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "An elderly pilgrim complains of crushing retrosternal chest pain radiating to the left jaw and diaphoresis. What immediate medication should be administered for the patient to chew?",
                options = listOf(
                    "Aspirin 300 mg (non-enteric coated, chewed)",
                    "Oral Amoxicillin 500 mg",
                    "Oral Diclofenac 50 mg",
                    "Oral Omeprazole 40 mg"
                ),
                correctIndex = 0,
                explanation = "In suspected Acute Coronary Syndrome (ACS), immediate chewing of non-enteric coated Aspirin (162-325 mg) rapidly inhibits platelet cyclooxygenase-1 (COX-1), arresting thrombus propagation and lowering mortality.",
                reference = "AHA/ACC Acute Coronary Syndrome Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "In mass casualty triage during a crowd surge emergency, how is a patient categorized under the START Triage system who is unconscious, has a respiratory rate of 34 breaths/min, and weak capillary refill (>2 seconds)?",
                options = listOf(
                    "Green (Minor / Walking Wounded)",
                    "Red (Immediate / Priority 1)",
                    "Yellow (Delayed / Priority 2)",
                    "Black (Deceased / Expectant)"
                ),
                correctIndex = 1,
                explanation = "Under START Triage, any patient with respiratory rate >30 breaths/min OR absent radial pulse/capillary refill >2s OR altered mental status is triaged RED (Immediate life-threatening).",
                reference = "START Mass Casualty Triage System SOP"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "When should sublingual Nitroglycerin be strictly avoided in a patient experiencing acute myocardial infarction?",
                options = listOf(
                    "When systolic blood pressure is below 90 mmHg or if Right Ventricular (RV) Infarction is present",
                    "When the patient has a history of mild heartburn",
                    "When the patient is over 50 years of age",
                    "When chest pain radiates to the jaw"
                ),
                correctIndex = 0,
                explanation = "Nitroglycerin reduces preload. It is contraindicated if systolic BP <90 mmHg, in Right Ventricular infarction (where cardiac output depends critically on preload), or if PDE-5 inhibitors (e.g., sildenafil) were used recently.",
                reference = "AHA ACLS Pharmacology Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What critical examination step must be performed BEFORE and AFTER applying a traction splint to a femur fracture?",
                options = listOf(
                    "Measuring body temperature",
                    "Assessing distal neurovascular status (dorsalis pedis/posterior tibial pulses, capillary refill, sensation, and motor function)",
                    "Performing a full neurological Glasgow Coma Scale audit",
                    "Checking capillary blood glucose"
                ),
                correctIndex = 1,
                explanation = "Documenting distal neurovascular status (pulses, sensation, motor function) before and after splinting confirms that the reduction or splint placement has not compromised nerve or vascular supply.",
                reference = "ATLS Orthopedic Trauma Assessment Rules"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "A trauma patient develops severe dyspnea, hypotension, distended neck veins, and absent breath sounds on the right side following chest impact. What life-saving procedure is indicated?",
                options = listOf(
                    "Immediate needle decompression (chest thoracostomy) in the 2nd intercostal space midclavicular line or 5th ICS anterior axillary line",
                    "Emergency pericardiocentesis",
                    "High-dose intravenous Furosemide",
                    "Endotracheal intubation without thoracostomy"
                ),
                correctIndex = 0,
                explanation = "Tension pneumothorax causes obstructive shock by trapping air under pressure in the pleural space, shifting the mediastinum. Immediate needle decompression converts it to an open pneumothorax, saving life.",
                reference = "ATLS Chest Trauma Management Protocols"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the recommended initial emergency dressing for an open penetrating chest wound ('sucking chest wound')?",
                options = listOf(
                    "Completely airtight circumferential occlusive tape wrapping",
                    "A 3-sided occlusive dressing (allowing air to escape during expiration while blocking entry during inspiration)",
                    "Absorbent cotton gauze without any dressing seal",
                    "Direct application of ice packs"
                ),
                correctIndex = 1,
                explanation = "A 3-sided occlusive dressing acts as a flutter valve: it vents trapped pleural air during expiration (preventing tension pneumothorax) and seals against atmospheric air entry during inspiration.",
                reference = "Prehospital Trauma Life Support (PHTLS) Guide"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "According to the Parkland formula, what is the fluid resuscitation requirement for the first 24 hours in an adult weighing 70 kg with 40% Total Body Surface Area (TBSA) partial-thickness burns?",
                options = listOf(
                    "2,800 mL of Normal Saline",
                    "11,200 mL of Ringer's Lactate (4 mL x 70 kg x 40% TBSA, with half given in the first 8 hours)",
                    "5,000 mL of 5% Dextrose",
                    "1,000 mL of Blood Transfusion"
                ),
                correctIndex = 1,
                explanation = "Parkland Formula: Total 24h Fluid = 4 mL x Weight (kg) x % TBSA. For 70 kg x 40%: 4 x 70 x 40 = 11,200 mL Ringer's Lactate (5,600 mL in first 8h, 5,600 mL over next 16h).",
                reference = "American Burn Association Resuscitation Formula"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the first step when an Automated External Defibrillator (AED) becomes available during cardiac arrest resuscitation?",
                options = listOf(
                    "Apply the electrode pads to the chest",
                    "Turn ON the AED",
                    "Press the shock button immediately",
                    "Check femoral pulse"
                ),
                correctIndex = 1,
                explanation = "The absolute first action when an AED arrives is to TURN IT ON. Turning on the AED initiates automated voice prompts that guide all subsequent steps (pad placement, rhythm analysis, shock safety).",
                reference = "AHA Basic Life Support AED Sequence"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the recommended emergency maneuver for a conscious adult who is clutching their neck and unable to speak or cough due to severe foreign body airway obstruction?",
                options = listOf(
                    "Perform abdominal thrusts (Heimlich maneuver) repeatedly until the object is expelled or the person becomes unconscious",
                    "Administer 5 back blows followed by blind finger sweeps",
                    "Instruct the person to lie down and rest",
                    "Give large gulps of water"
                ),
                correctIndex = 0,
                explanation = "For severe foreign body airway obstruction in a conscious adult, abdominal thrusts (Heimlich maneuver) generate subdiaphragmatic pressure to force the impacted foreign object out.",
                reference = "AHA Airway Obstruction Management Protocols"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "How should acute anterior epistaxis (nosebleed) be managed in a field clinic?",
                options = listOf(
                    "Tilt the patient's head backward and pack nostrils with dry cotton",
                    "Have the patient sit upright, lean slightly forward, and continuously pinch the soft lower part of the nose for 10 to 15 minutes",
                    "Instruct the patient to lie flat supine and blow their nose vigorously",
                    "Apply hot compresses to the forehead"
                ),
                correctIndex = 1,
                explanation = "Sitting upright and leaning forward prevents blood swallowing and airway aspiration. Pinching the soft cartilaginous ala applies direct pressure to Kiesselbach's plexus.",
                reference = "ENT Emergency Management Standards"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What intravenous medication is indicated for rapid reversal of respiratory depression caused by acute opioid overdose?",
                options = listOf(
                    "Naloxone (0.4 to 2.0 mg IV/IM/IN)",
                    "Flumazenil",
                    "Atropine sulfate",
                    "N-acetylcysteine"
                ),
                correctIndex = 0,
                explanation = "Naloxone is a competitive opioid receptor antagonist that rapidly restores spontaneous respiration and consciousness in opioid-induced central nervous system depression.",
                reference = "Emergency Toxicology & ACLS Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "Which clinical constellation represents the '5 Ps' of acute limb Compartment Syndrome following crush injury in a crowd stampede?",
                options = listOf(
                    "Pain out of proportion, Pallor, Paresthesia, Pulselessness, and Paralysis",
                    "Pyrexia, Papules, Petechiae, Pruritus, and Purpura",
                    "Polyuria, Polydipsia, Polyphagia, Psychosis, and Palpitations",
                    "Pallor, Papilledema, Ptosis, Polyps, and Pleurisy"
                ),
                correctIndex = 0,
                explanation = "Compartment syndrome (increased tissue pressure in a closed osteofascial compartment) manifests as Pain (especially on passive stretch), Pallor, Paresthesia, Pulselessness, and Paralysis.",
                reference = "Orthopedic Trauma Surgery Standards"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "A pilgrim presenting with acute severe dyspnea, orthopnea, bilateral crackles, pink frothy sputum, and blood pressure of 210/120 mmHg has acute pulmonary edema. What is the initial pharmacological strategy?",
                options = listOf(
                    "Sublingual Nitroglycerin, IV Furosemide, supplemental oxygen, and non-invasive positive pressure ventilation (NIV)",
                    "Rapid IV administration of 2 Liters Normal Saline bolus",
                    "Subcutaneous Epinephrine injection",
                    "Oral Propranolol 80 mg"
                ),
                correctIndex = 0,
                explanation = "Acute hypertensive pulmonary edema is managed by reducing preload and afterload (IV Nitroglycerin, IV Furosemide) and improving oxygenation with oxygen/NIV (CPAP/BiPAP).",
                reference = "AHA Hypertensive Crisis & Heart Failure Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the maximal time window from symptom onset for administering Intravenous Recombinant Tissue Plasminogen Activator (r-tPA) in acute ischemic stroke?",
                options = listOf(
                    "Within 4.5 hours of symptom onset (after ruling out intracranial hemorrhage via non-contrast CT)",
                    "Within 24 hours regardless of imaging",
                    "Up to 72 hours",
                    "Thrombolysis is given 1 week later"
                ),
                correctIndex = 0,
                explanation = "IV thrombolysis with Alteplase (r-tPA) improves neurological recovery in acute ischemic stroke if administered within 4.5 hours of well-documented symptom onset, provided non-contrast head CT excludes hemorrhage.",
                reference = "AHA/ASA Ischemic Stroke Management Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the emergency first-line pharmacological treatment for a patient experiencing prolonged (>5 minutes) active generalized convulsive Status Epilepticus?",
                options = listOf(
                    "Intravenous Benzodiazepines (e.g., Lorazepam 4 mg IV or Diazepam 10 mg IV)",
                    "Oral Carbamazepine tablets",
                    "Subcutaneous Insulin",
                    "Intravenous Furosemide"
                ),
                correctIndex = 0,
                explanation = "Active Status Epilepticus (>5 mins) requires rapid termination using IV Lorazepam 4 mg or IV Diazepam 10 mg (or IM Midazolam 10 mg if IV access is absent) to prevent permanent metabolic brain damage.",
                reference = "American Epilepsy Society Status Epilepticus Protocol"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "How does Hypertensive Urgency differ from Hypertensive Emergency?",
                options = listOf(
                    "Hypertensive Emergency presents with acute target organ damage (e.g., encephalopathy, aortic dissection, acute MI, pulmonary edema), whereas Hypertensive Urgency lacks acute organ damage",
                    "Hypertensive Urgency involves systolic BP >300 mmHg",
                    "Hypertensive Emergency is treated exclusively with oral vitamins",
                    "There is no clinical difference"
                ),
                correctIndex = 0,
                explanation = "Both involve severe BP elevation (typically >180/120 mmHg). Hypertensive Emergency is defined by the presence of acute target organ damage, requiring immediate IV antihypertensive therapy in an ICU.",
                reference = "ACC/AHA Hypertension Management Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "A pilgrim gets sand blown into their eye, causing acute pain, tearing, and foreign body sensation. What is the immediate field management?",
                options = listOf(
                    "Rub the cornea vigorously with a dry cotton swab",
                    "Copiously irrigate the affected eye with sterile 0.9% Normal Saline from inner to outer canthus",
                    "Apply topical steroid drops without examination",
                    "Instill concentrated alcohol solutions"
                ),
                correctIndex = 1,
                explanation = "Copious saline irrigation washes out superficial loose foreign bodies without causing corneal abrasion. Rubbing the eye is strictly avoided to prevent mechanical corneal epithelial trauma.",
                reference = "Ophthalmology Emergency First Aid Guide"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the correct electrode placement for an Automated External Defibrillator (AED) on an adult cardiac arrest patient?",
                options = listOf(
                    "One pad on upper right sternal border below clavicle; second pad on lower left anterolateral chest wall (apex)",
                    "Both pads on the posterior lumbar back",
                    "One pad on forehead; second pad on umbilical abdomen",
                    "Pads placed directly over active pacemaker units"
                ),
                correctIndex = 0,
                explanation = "Anterolateral placement (right upper chest below clavicle and left lower chest apex) directs the defibrillation current vector straight through the cardiac ventricles.",
                reference = "AHA ACLS Defibrillation Technique SOP"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "In a trauma victim with suspected cervical spine injury, which technique is used to open the airway during resuscitation?",
                options = listOf(
                    "Head-tilt chin-lift maneuver",
                    "Jaw-thrust maneuver (without neck hyperextension)",
                    "Hyper-extending the cervical neck backward",
                    "Rotating the head 90 degrees laterally"
                ),
                correctIndex = 1,
                explanation = "The Jaw-thrust maneuver opens the airway by lifting the mandible forward without tilting or hyperextending the cervical spine, preserving spinal cord safety in trauma.",
                reference = "ATLS Airway Management in Spinal Trauma"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "Which clinical tool is used to grade the depth of coma and level of consciousness in head trauma patients?",
                options = listOf(
                    "Apgar Score",
                    "Glasgow Coma Scale (GCS) evaluating Eye (1-4), Verbal (1-5), and Motor (1-6) responses",
                    "Framingham Score",
                    "Child-Pugh Score"
                ),
                correctIndex = 1,
                explanation = "Glasgow Coma Scale (GCS) measures level of consciousness from 3 to 15 based on Eye Opening (1-4), Verbal Response (1-5), and Motor Response (1-6). GCS <=8 indicates severe brain injury requiring endotracheal intubation.",
                reference = "Neurosurgery & ATLS Trauma Assessment"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the primary action when treating an elderly diabetic patient presenting with hypoglycemic coma who lacks intravenous access?",
                options = listOf(
                    "Administer Intramuscular (or Subcutaneous) Glucagon 1.0 mg (or Intranasal Glucagon 3 mg)",
                    "Force oral fruit juice into the mouth while unconscious",
                    "Inject subcutaneous rapid insulin",
                    "Wait 4 hours for natural recovery"
                ),
                correctIndex = 0,
                explanation = "When IV access is unavailable in an unconscious hypoglycemic patient, IM/SC Glucagon (1 mg) or Intranasal Glucagon (3 mg) stimulates hepatic glycogenolysis, raising blood glucose rapidly.",
                reference = "ADA Hypoglycemia Emergency Protocol"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What electrocardiographic (ECG) change is characteristic of acute transmural Myocardial Infarction (STEMI)?",
                options = listOf(
                    "ST-segment elevation >= 1 mm in two or more anatomically contiguous leads",
                    "Isolated PR interval shortening",
                    "Broad U waves in leads V1-V3",
                    "Complete disappearance of QRS complexes"
                ),
                correctIndex = 0,
                explanation = "ST-segment elevation in >=2 contiguous leads represents acute transmural myocardial ischemia (STEMI), requiring urgent reperfusion (Primary PCI or fibrinolysis).",
                reference = "AHA ECG Interpretation Criteria"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What is the initial management for an acute chemical alkali burn to the eye?",
                options = listOf(
                    "Immediate continuous flushing with copious amounts of water or normal saline for at least 15 to 30 minutes before any diagnostic delay",
                    "Neutralizing with concentrated acid solution",
                    "Applying eye ointment and patching immediately without washing",
                    "Instilling topical corticosteroid drops only"
                ),
                correctIndex = 0,
                explanation = "Alkali eye burns cause rapid liquefactive necrosis penetrating deep ocular tissues. Immediate, prolonged irrigation with saline/water for 15-30 minutes is the single most important determinant of visual outcome.",
                reference = "Ophthalmic Trauma Emergency Protocol"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "What pulse site should be palpated to assess circulation in an unconscious adult during basic life support?",
                options = listOf(
                    "Radial pulse",
                    "Carotid artery pulse (for 5 to 10 seconds)",
                    "Dorsalis pedis pulse",
                    "Brachial pulse"
                ),
                correctIndex = 1,
                explanation = "In unconscious adults, peripheral pulses may disappear during shock/arrest. The central Carotid artery pulse is palpated for 5-10 seconds to confirm cardiac arrest.",
                reference = "AHA BLS Pulse Check Standards"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "CPR, Trauma & Clinics",
                question = "How is an active, uncontrolled scalp laceration best managed initially in a field dispensary?",
                options = listOf(
                    "Apply firm direct pressure with a bulky sterile pressure dressing over the wound",
                    "Apply a neck tourniquet",
                    "Cauterize the entire skull with silver nitrate",
                    "Leave the wound open without pressure"
                ),
                correctIndex = 0,
                explanation = "Scalp vessels are rich and do not constrict readily. Firm, direct pressure with a sterile compression dressing controls profuse scalp hemorrhage safely.",
                reference = "ATLS Wound Care SOP"
            )
        )

        return list
    }
}
