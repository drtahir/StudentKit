package com.drtahir.studentkit.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 2
 * Category: Heat Stroke & Hydration (30 100% Unique MCQs)
 * Covers hyperthermia pathophysiology, active cooling techniques, fluid resuscitation, heat exhaustion vs heat stroke, rhabdomyolysis & electrolyte management.
 */
object Hajj1000Part2 {

    fun getHeatStrokeQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Which clinical finding definitively distinguishes Heat Stroke from severe Heat Exhaustion in a collapsed pilgrim evaluated at Arafat?",
                options = listOf(
                    "Presence of heavy sweating",
                    "Systolic blood pressure below 100 mmHg",
                    "Central Nervous System (CNS) dysfunction (confusion, delirium, ataxia, seizures, or coma)",
                    "Core body temperature of 38.0°C (100.4°F)"
                ),
                correctIndex = 2,
                explanation = "Heat Stroke is a medical emergency defined by severe hyperthermia (usually >40°C/104°F) AND central nervous system (CNS) dysfunction. Heat exhaustion patients may have fever and sweating but retain intact mental status.",
                reference = "Saudi MOH Heat Emergency Management Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "What is considered the gold-standard physical cooling technique for heat stroke in field medical centers during peak summer Hajj?",
                options = listOf(
                    "Applying heavy ice packs exclusively to the extremities",
                    "Continuous mist spraying of tepid water combined with high-velocity airflow from electric fans (evaporative cooling)",
                    "Subcutaneous injection of cold normal saline",
                    "Wrapping the patient in tight thermal plastic blankets"
                ),
                correctIndex = 1,
                explanation = "Evaporative cooling (tepid mist spray with high-speed fan airflow) is the safest and most effective method in field conditions. It maximizes heat dissipation via evaporation while avoiding shivering and peripheral vasoconstriction caused by ice water.",
                reference = "WHO & Saudi MOH Heat Stroke Evaporative Cooling SOP"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "At what target core body temperature threshold must active physical cooling measures be discontinued to prevent iatrogenic hypothermia and shivering?",
                options = listOf(
                    "37.0°C (98.6°F)",
                    "38.9°C (102.0°F)",
                    "40.0°C (104.0°F)",
                    "35.0°C (95.0°F)"
                ),
                correctIndex = 1,
                explanation = "Active physical cooling is stopped once core (rectal) temperature drops to 38.9°C (102°F) to prevent rebound hypothermia and uncontrollable shivering, which generates internal metabolic heat.",
                reference = "Wilderness Medical Society Heat Illness Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Why is the use of antipyretics (e.g., Paracetamol, Aspirin, Ibuprofen) clinically contraindicated in treating Heat Stroke?",
                options = listOf(
                    "They cause sudden severe hypoglycemia",
                    "Hyperthermia in heat stroke is caused by failed physical heat dissipation, not an altered hypothalamic set-point; antipyretics are ineffective and exacerbate hepatic/renal injury and coagulopathy",
                    "They neutralize intravenous normal saline solution",
                    "They trigger immediate acute bronchospasm in all patients"
                ),
                correctIndex = 1,
                explanation = "Heat stroke hyperthermia is non-pyrogenic (thermoregulatory failure). Antipyretics act on pyrogen-induced hypothalamic set-point changes, so they do not lower temperature in heat stroke and increase the risk of liver damage (acetaminophen) and bleeding (aspirin).",
                reference = "Emergency Medicine Therapeutics Manual"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "A pilgrim who walked 12 km under 45°C heat develops dark tea-colored urine, muscle swelling, and acute oliguria. Which laboratory finding confirms acute rhabdomyolysis?",
                options = listOf(
                    "Markedly elevated Serum Creatine Kinase (CK > 5,000 U/L) and positive urine myoglobin",
                    "Elevated serum lipase level",
                    "Low blood urea nitrogen (BUN < 5 mg/dL)",
                    "High serum Calcium level"
                ),
                correctIndex = 0,
                explanation = "Exertional heat stroke causes severe skeletal muscle breakdown (rhabdomyolysis), releasing myoglobin into urine (tea-colored) and driving Serum Creatine Kinase (CK) to extreme levels (>5000 U/L), posing a high risk for Acute Kidney Injury (AKI).",
                reference = "NTS Nephrology & Critical Care Past Papers"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "A pilgrim presenting with confusion and nausea after drinking 10 liters of plain un-salted bottled water in Mina has a serum sodium of 118 mEq/L. What condition has developed?",
                options = listOf(
                    "Hypernatremic dehydration",
                    "Dilutational Exercise-Associated Hyponatremia (EAH) caused by excessive hypotonic fluid intake without electrolyte replacement",
                    "Acute bacterial meningitis",
                    "Hyperglycemic hyperosmolar state"
                ),
                correctIndex = 1,
                explanation = "Excessive intake of plain hypotonic water during prolonged physical exertion leads to Exercise-Associated Hyponatremia (EAH). Water shifts into brain cells causing cerebral edema, headache, confusion, and seizures.",
                reference = "Consensus Guidelines on Exercise-Associated Hyponatremia"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "What is the initial intravenous fluid of choice for rapid volume resuscitation in a dehydrated, hypotensive pilgrim experiencing hypovolemic shock?",
                options = listOf(
                    "5% Dextrose in Water (D5W)",
                    "Isotonic crystalloids (0.9% Normal Saline or Ringer's Lactate)",
                    "Hypertonic 3% Sodium Chloride solution",
                    "20% Mannitol solution"
                ),
                correctIndex = 1,
                explanation = "Isotonic crystalloids (0.9% NS or Ringer's Lactate) expand intravascular volume effectively without causing rapid intracellular osmotic fluid shifts, restoring blood pressure and tissue perfusion.",
                reference = "ATLS Fluid Resuscitation Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Which medication class taken by an elderly hypertensive pilgrim severely impairs sweating and dramatically increases susceptibility to heat stroke?",
                options = listOf(
                    "Anticholinergic agents and tricyclic antidepressants (which inhibit sweat gland stimulation)",
                    "Oral vitamin D supplements",
                    "Statins (cholesterol-lowering drugs)",
                    "Proton pump inhibitors (Omeprazole)"
                ),
                correctIndex = 0,
                explanation = "Anticholinergics, antihistamines, and TCAs block muscarinic receptors on sweat glands, preventing sweating (anhidrosis) and severely compromising thermoregulation in hot environments.",
                reference = "Clinical Pharmacology & Heat Risk Review"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "A pilgrim experiences painful involuntary contractions of the calf muscles (gastrocnemius) after heavy walking in Muzdalifah with heavy sweating. What is the appropriate immediate care?",
                options = listOf(
                    "Vigorous deep tissue massage with ice packs",
                    "Rest in a cool shaded area, gentle muscle stretching, and oral rehydration salts (ORS) or electrolyte solution",
                    "Intravenous injection of calcium gluconate",
                    "Immediate administration of oral muscle relaxants and continuous walking"
                ),
                correctIndex = 1,
                explanation = "Heat cramps are caused by sodium and water loss from heavy sweating. Treatment consists of rest in a cool area, gentle passive stretching, and oral electrolyte replacement (ORS).",
                reference = "Wilderness Medical Society Heat Cramps Protocol"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "A critically hyperthermic pilgrim in the ICU develops generalized petechiae, oozing from IV puncture sites, and prolonged PT/aPTT. What life-threatening complication has occurred?",
                options = listOf(
                    "Disseminated Intravascular Coagulation (DIC) secondary to systemic thermal endothelial injury",
                    "Acute viral hepatitis B infection",
                    "Anaphylactic drug reaction",
                    "Idiopathic thrombocytopenic purpura"
                ),
                correctIndex = 0,
                explanation = "Extreme hyperthermia directly damages vascular endothelium, triggering systemic coagulation cascade activation, consumption of clotting factors and platelets, leading to DIC and severe bleeding.",
                reference = "Critical Care Medicine - Heat Stroke Complications"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Why is Dantrolene Sodium NOT recommended for the routine treatment of exertional or environmental heat stroke?",
                options = listOf(
                    "It causes immediate cardiac arrest in all adults",
                    "Dantrolene is specific for Malignant Hyperthermia (ryanodine receptor defect) and has shown no clinical benefit in environmental hyperthermia trials",
                    "It is unavailable in liquid intravenous form",
                    "It raises core temperature further"
                ),
                correctIndex = 1,
                explanation = "Dantrolene targets skeletal muscle ryanodine receptors in Malignant Hyperthermia. Clinical trials demonstrate it does not accelerate cooling or improve outcomes in environmental/exertional heat stroke.",
                reference = "Cochrane Database Review - Heat Stroke Pharmacotherapy"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "What is the recommended daily fluid intake for active pilgrims during hot Hajj weather (ambient temperature >42°C) to prevent dehydration?",
                options = listOf(
                    "1.0 to 1.5 Liters of plain water",
                    "3.0 to 4.0 Liters containing balanced electrolytes (ORS)",
                    "8.0 to 10.0 Liters of distilled water",
                    "Fluid intake should be restricted to under 500 mL"
                ),
                correctIndex = 1,
                explanation = "Under intense heat and physical exertion, sweat loss exceeds 1 L/hour. Drinking 3-4 L/day with electrolyte supplementation (ORS) maintains fluid balance and prevents hyponatremia.",
                reference = "Saudi MOH Pilgrim Health Bulletins"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Which physiological change occurs as an early compensatory response to environmental heat stress and mild dehydration?",
                options = listOf(
                    "Bradycardia and vasoconstriction",
                    "Reflex tachycardia, peripheral vasodilation, and increased cutaneous blood flow",
                    "Decreased respiratory rate and hypertension",
                    "Complete cessation of sweat gland activity"
                ),
                correctIndex = 1,
                explanation = "To dissipate heat, the sympathetic nervous system triggers cutaneous vasodilation and reflex tachycardia, diverting blood to the skin for evaporative cooling.",
                reference = "Guyton Medical Physiology - Thermoregulation"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "In an elderly patient with chronic heart failure presenting with classic heat stroke, what is a key risk during rapid intravenous fluid resuscitation?",
                options = listOf(
                    "Acute pulmonary edema and fluid overload",
                    "Rapid development of hypothermia",
                    "Immediate bone marrow suppression",
                    "Severe arterial hypertension"
                ),
                correctIndex = 0,
                explanation = "Elderly patients with underlying heart failure have reduced cardiac reserve; overly aggressive IV crystalloid boluses can precipitate acute pulmonary edema. Careful hemodynamic monitoring is required.",
                reference = "Cardiology & Geriatric Emergency Management"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "What severe electrolyte abnormality is commonly caused by extensive muscle cell necrosis in rhabdomyolysis following exertional heat stroke?",
                options = listOf(
                    "Severe Hyperkalemia (high serum Potassium)",
                    "Severe Hypokalemia",
                    "Hypophosphatemia",
                    "Hypernatremia"
                ),
                correctIndex = 0,
                explanation = "Damaged muscle cells release large quantities of intracellular Potassium (\$K^+) into the bloodstream, resulting in hyperkalemia, which poses an immediate threat of lethal cardiac arrhythmias.",
                reference = "Emergency Medicine Electrolyte Disturbances"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "How does high environmental relative humidity affect body cooling during physical exertion in Mina?",
                options = listOf(
                    "Increases sweat evaporation rate significantly",
                    "Impairs sweat evaporation from the skin, drastically reducing thermoregulatory cooling efficiency",
                    "Has zero impact on human thermoregulation",
                    "Eliminates the risk of heat stroke completely"
                ),
                correctIndex = 1,
                explanation = "Evaporation depends on the water vapor pressure gradient between skin and air. High ambient humidity prevents sweat from evaporating, causing heat to accumulate rapidly in the body.",
                reference = "Environmental Physiology & Mass Gathering Safety"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "If a heat stroke patient develops severe violent shivering during active cold mist spraying, how should the clinical team manage the shivering?",
                options = listOf(
                    "Stop cooling completely and apply warm heating blankets",
                    "Administer a small dose of intravenous Benzodiazepine (e.g., Diazepam or Lorazepam) to suppress shivering while continuing cooling",
                    "Administer high-dose oral antipyretics",
                    "Immobilize the patient with tight mechanical restraints"
                ),
                correctIndex = 1,
                explanation = "Shivering generates substantial endogenous metabolic heat, counteracting physical cooling. Low-dose IV Benzodiazepines effectively suppress shivering without blunting cardiovascular stability.",
                reference = "Critical Care Management of Hyperthermic Emergencies"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "What is the target hourly urine output indicating adequate renal perfusion during fluid resuscitation for rhabdomyolysis and dehydration?",
                options = listOf(
                    "10 to 15 mL/hour",
                    "At least 0.5 to 1.0 mL/kg/hour (or 200 to 300 mL/hour in active rhabdomyolysis protocol)",
                    "500 mL/hour continuously",
                    "Urine output does not need to be measured"
                ),
                correctIndex = 1,
                explanation = "To prevent myoglobin precipitation in renal tubules during rhabdomyolysis, aggressive IV hydration aims for a high urine output of 200-300 mL/hr (or >0.5-1.0 mL/kg/hr generally).",
                reference = "Nephrology Protocols for Rhabdomyolysis"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Which neurological manifestation is commonly observed during the early acute phase of classic heat stroke?",
                options = listOf(
                    "Isolated loss of hearing",
                    "Ataxia, dysarthria, confusion, agitation, or generalized tonic-clonic seizures",
                    "Isolated bilateral wrist drop",
                    "Hyper-reflexia without mental changes"
                ),
                correctIndex = 1,
                explanation = "The cerebellum and cerebral cortex are highly sensitive to thermal injury. Early signs include cerebellar ataxia, slurred speech (dysarthria), confusion, delirium, seizures, and coma.",
                reference = "Neurology of Environmental Hyperthermia"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Which personal measure is most effective for pilgrims to minimize direct solar radiation absorption during daytime rituals in Arafat?",
                options = listOf(
                    "Wearing heavy dark-colored wool coats",
                    "Using light-colored umbrellas, wearing lightweight breathable white Ihram garments, and seeking shade during peak hours (10 AM to 4 PM)",
                    "Applying heavy petroleum jelly over the entire body",
                    "Drinking hot caffeinated tea continuously"
                ),
                correctIndex = 1,
                explanation = "White/light clothing reflects solar radiation, while umbrellas and shaded rest prevent direct heat absorption and reduce total heat load.",
                reference = "Saudi MOH Pilgrim Heat Protection Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "How does Heat Syncope differ from Heat Stroke?",
                options = listOf(
                    "Heat Syncope involves core temperature >41°C and permanent coma",
                    "Heat Syncope is a brief loss of consciousness caused by peripheral venous pooling and transient cerebral hypotension, with rapid recovery upon assuming a supine position and normal core temperature",
                    "Heat Syncope is caused by acute bacterial brain abscess",
                    "Heat Syncope requires emergency craniotomy"
                ),
                correctIndex = 1,
                explanation = "Heat Syncope occurs when prolonged standing in heat causes peripheral vasodilation and venous pooling. Patients faint briefly, but rapidly regain normal consciousness when laid flat.",
                reference = "Emergency Medicine Diagnostics - Heat Illness"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Why does hyperventilation induced by extreme thermal stress cause carpopedal spasms and muscle tingling?",
                options = listOf(
                    "Respiratory alkalosis lowers ionized serum Calcium levels, increasing neuromuscular excitability",
                    "Hyperventilation causes massive intravascular potassium release",
                    "Thermal stress destroys peripheral motor nerve sheaths instantly",
                    "Carbon dioxide accumulation causes acute muscle necrosis"
                ),
                correctIndex = 0,
                explanation = "Thermal hyperventilation blows off \$CO_2, raising arterial pH (respiratory alkalosis). Elevated pH increases Calcium binding to albumin, reducing free ionized \$Ca^{2+} and triggering carpopedal tetany.",
                reference = "Acid-Base & Electrolyte Physiology"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "What is the earliest clinical symptom reported by pilgrims developing exertional heat exhaustion?",
                options = listOf(
                    "Deep coma",
                    "Profuse sweating, fatigue, lightheadedness, nausea, and thirst",
                    "Petechial skin rash",
                    "Severe jaundice"
                ),
                correctIndex = 1,
                explanation = "Heat exhaustion begins with heavy sweating, weakness, fatigue, dizziness, nausea, headache, and thirst as cardiovascular compensation struggles with fluid depletion.",
                reference = "Wilderness & Environmental Medicine Review"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "What is the primary cause of Acute Tubular Necrosis (ATN) in patients with uncorrected exertional heat stroke and rhabdomyolysis?",
                options = listOf(
                    "Excessive oral intake of vitamin C",
                    "Renal vasoconstriction, renal ischemia, and direct toxic tubular precipitation of myoglobin cast proteins",
                    "High intake of oral rehydration salts",
                    "Bacterial urinary tract infection"
                ),
                correctIndex = 1,
                explanation = "Myoglobin released from broken muscle cells precipitates in renal tubules under acidic, dehydrated conditions, causing direct tubular toxicity, oxidative stress, and ATN.",
                reference = "Renal Pathology of Heat Stroke & Trauma"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "How long does full physiological heat acclimatization typically take for an individual traveling from a cool climate to Saudi Arabia?",
                options = listOf(
                    "24 hours",
                    "7 to 14 days of gradual, progressive exertion in the heat",
                    "6 months",
                    "Acclimatization never occurs in humans"
                ),
                correctIndex = 1,
                explanation = "Heat acclimatization requires 7-14 days. Physiological adaptations include earlier onset of sweating, higher sweat volume with lower salt concentration, and expanded plasma volume.",
                reference = "Physiology of Adaptation to Extreme Heat"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "In a patient presenting with heat stroke, what liver function abnormality is universally observed within 24 to 72 hours of admission?",
                options = listOf(
                    "Isolated drop in serum bilirubin",
                    "Dramatic elevation of serum transaminases (AST and ALT > 1000 U/L) secondary to acute thermal hepatocyte necrosis",
                    "Complete disappearance of alkaline phosphatase",
                    "Transient increase in clotting factor production"
                ),
                correctIndex = 1,
                explanation = "Thermal damage and ischemia cause severe hepatocyte injury, manifesting as extreme elevations of AST and ALT (often several thousand U/L) within 1-3 days.",
                reference = "Hepatology & Critical Care in Environmental Trauma"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Which fluid option should be avoided for primary fluid resuscitation in heat stroke due to risk of worsening hyperthermia-induced cerebral edema?",
                options = listOf(
                    "0.9% Normal Saline",
                    "Large volumes of hypotonic solutions (e.g., 5% Dextrose in Water - D5W)",
                    "Ringer's Lactate",
                    "Plasmalyte"
                ),
                correctIndex = 1,
                explanation = "Hypotonic fluids (like D5W) rapidly distribute into intracellular spaces, exacerbating cerebral edema caused by thermal breakdown of the blood-brain barrier.",
                reference = "Neurocritical Care for Acute Heat Stroke"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "What is the primary mechanism by which evaporative cooling lowers body temperature?",
                options = listOf(
                    "Conduction of heat into ambient air without moisture",
                    "Latent heat of vaporization absorbed from skin surface as liquid water transforms into water vapor",
                    "Infrared radiation reflection",
                    "Chemical neutralization of metabolic toxins"
                ),
                correctIndex = 1,
                explanation = "Evaporation utilizes the latent heat of vaporization (~0.58 kcal per gram of evaporated water), drawing heat directly off the cutaneous capillary bed.",
                reference = "Biophysics of Thermal Physiology"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "Which clinical sign indicates that a dehydrated heat exhaustion patient is successfully responding to fluid replacement?",
                options = listOf(
                    "Increasing tachycardia and reduced pulse pressure",
                    "Normalization of heart rate and blood pressure, return of skin turgor, and clear adequate urine output (>0.5 mL/kg/hr)",
                    "Onset of acute confusion and tachypnea",
                    "Development of petechial skin rash"
                ),
                correctIndex = 1,
                explanation = "Successful rehydration resolves tachycardia/hypotension, restores microvascular skin perfusion, and produces pale, adequate urine volume.",
                reference = "Clinical Assessment of Rehydration Efficacy"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Heat Stroke & Hydration",
                question = "What simple field measurement provides the most accurate continuously updated core body temperature reading during active cooling?",
                options = listOf(
                    "Axillary mercury thermometer",
                    "Continuous Indwelling Rectal or Esophageal Temperature Probe",
                    "Infrared forehead skin thermometer",
                    "Tactile palpation of the forehead"
                ),
                correctIndex = 1,
                explanation = "Axillary and forehead skin sensors are heavily confounded by skin cooling mist and airflow. Indwelling rectal or esophageal probes reflect true core visceral temperature.",
                reference = "Saudi MOH Critical Care Monitoring Standards"
            )
        )

        return list
    }
}
