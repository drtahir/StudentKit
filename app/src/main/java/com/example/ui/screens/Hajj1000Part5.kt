package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 5
 * Category: Quantitative Reasoning (25 100% Unique MCQs)
 * Covers pharmaceutical dosing, flow rates, inventory depletion, efficiency ratios, statistics & quantitative problem solving.
 */
object Hajj1000Part5 {

    fun getQuantitativeQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "A Hajj field dispensary in Mina starts with a stock of 400 vials of regular insulin. If the clinical team consumes 35% of the total stock during the first 3 days, how many vials of insulin remain in stock?",
                options = listOf(
                    "140 vials",
                    "260 vials",
                    "300 vials",
                    "220 vials"
                ),
                correctIndex = 1,
                explanation = "Calculation: Used vials = 35% of 400 = 0.35 * 400 = 140 vials. Remaining stock = 400 - 140 = 260 vials.",
                reference = "NTS Quantitative Aptitude & Logistics"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "An intravenous fluid bag containing 1,000 mL of 0.9% Normal Saline is to be infused over 8 hours using a macrodrip IV set with a drip factor of 15 drops/mL. What is the required drip rate in drops per minute (gtt/min)?",
                options = listOf(
                    "21 gtt/min",
                    "31 gtt/min (specifically 31.25 drops/min)",
                    "42 gtt/min",
                    "60 gtt/min"
                ),
                correctIndex = 1,
                explanation = "Formula: Drip Rate = (Total Volume in mL * Drip Factor) / Total Time in Minutes. Total time = 8 hours * 60 = 480 minutes. Drip Rate = (1000 * 15) / 480 = 15000 / 480 = 31.25 gtt/min.",
                reference = "Clinical Pharmaceutical Calculations"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "If 4 medical officers can treat 24 patients in 3 hours at a constant rate, how many patients can 6 medical officers treat in 5 hours at the same constant rate of treatment?",
                options = listOf(
                    "36 patients",
                    "60 patients",
                    "48 patients",
                    "72 patients"
                ),
                correctIndex = 1,
                explanation = "Rate per doctor per hour = 24 patients / (4 doctors * 3 hours) = 2 patients per doctor per hour. Total patients treated by 6 doctors working 5 hours = 6 * 5 * 2 = 60 patients.",
                reference = "NTS Work & Rate Word Problems"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "The average age of 8 clinical nurses stationed at a sector clinic is 35 years. If a senior nursing supervisor aged 44 joins the group, what is the new average age of the 9 nurses?",
                options = listOf(
                    "36.0 years",
                    "37.5 years",
                    "38.2 years",
                    "39.0 years"
                ),
                correctIndex = 0,
                explanation = "Sum of ages of 8 nurses = 8 * 35 = 280 years. New total sum = 280 + 44 = 324 years. New total number of nurses = 9. New average = 324 / 9 = 36.0 years.",
                reference = "NTS Statistics & Averages Past Papers"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "An emergency medical vehicle travels from Makkah HQ to a Mina station at an average speed of 30 km/h and returns along the exact same route at an average speed of 60 km/h. What is the average speed for the round trip?",
                options = listOf(
                    "45.0 km/h",
                    "40.0 km/h",
                    "50.0 km/h",
                    "35.0 km/h"
                ),
                correctIndex = 1,
                explanation = "Since distance is equal in both directions, the average speed is the Harmonic Mean: Average Speed = (2 * S1 * S2) / (S1 + S2) = (2 * 30 * 60) / (30 + 60) = 3600 / 90 = 40.0 km/h.",
                reference = "NTS Speed, Distance & Harmonic Mean Syllabus"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "A pharmacist needs to prepare 250 mL of a 10% Dextrose solution by diluting a stock solution of 50% Dextrose with sterile water. How many mL of the 50% Dextrose stock solution are required?",
                options = listOf(
                    "25 mL",
                    "50 mL",
                    "100 mL",
                    "125 mL"
                ),
                correctIndex = 1,
                explanation = "Dilution formula: C1 * V1 = C2 * V2. 50% * V1 = 10% * 250 mL. V1 = (10 * 250) / 50 = 2500 / 50 = 50 mL of 50% Dextrose (plus 200 mL sterile water).",
                reference = "Pharmaceutical Calculations & Solution Compounding"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "In a field clinic, patient turnout increased from 120 patients on Day 1 to 150 patients on Day 2. What is the percentage increase in patient turnout?",
                options = listOf(
                    "20%",
                    "25%",
                    "30%",
                    "35%"
                ),
                correctIndex = 1,
                explanation = "Percentage Increase = [(New Value - Original Value) / Original Value] * 100 = [(150 - 120) / 120] * 100 = (30 / 120) * 100 = 0.25 * 100 = 25%.",
                reference = "NTS Percentage & Ratio Problem Solving"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "A pediatric liquid Paracetamol suspension contains 120 mg of Paracetamol per 5 mL. If a child weighing 16 kg requires a dose of 15 mg/kg, how many mL of suspension should be administered?",
                options = listOf(
                    "5 mL",
                    "10 mL",
                    "15 mL",
                    "8 mL"
                ),
                correctIndex = 1,
                explanation = "Total dose required = 16 kg * 15 mg/kg = 240 mg. Concentration = 120 mg / 5 mL = 24 mg/mL. Volume required = 240 mg / 24 mg/mL = 10 mL.",
                reference = "Clinical Pediatric Dosage Calculations"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "What is the next logical term in this arithmetic progression sequence: 7, 14, 21, 28, 35, ...?",
                options = listOf(
                    "40",
                    "42",
                    "49",
                    "45"
                ),
                correctIndex = 1,
                explanation = "The sequence has a constant common difference of +7 (multiples of 7). Next term = 35 + 7 = 42.",
                reference = "NTS Quantitative Sequences & Series"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "A medical store contains 12 boxes of Ciprofloxacin, 8 boxes of Amoxicillin, and 5 boxes of Azithromycin. If a box is selected at random, what is the probability that it contains Ciprofloxacin?",
                options = listOf(
                    "0.32",
                    "0.48 (12 / 25)",
                    "0.50",
                    "0.60"
                ),
                correctIndex = 1,
                explanation = "Total boxes = 12 + 8 + 5 = 25 boxes. Favorable outcomes = 12 Ciprofloxacin boxes. Probability = 12 / 25 = 0.48 (48%).",
                reference = "NTS Probability & Quantitative Analysis"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "A portable medical oxygen cylinder contains 600 Liters of compressed oxygen gas. If a dyspneic pilgrim receives oxygen at a continuous flow rate of 5 Liters/minute, how long will the cylinder last?",
                options = listOf(
                    "60 minutes (1 hour)",
                    "120 minutes (2 hours)",
                    "180 minutes (3 hours)",
                    "240 minutes (4 hours)"
                ),
                correctIndex = 1,
                explanation = "Duration = Total Volume / Flow Rate = 600 Liters / 5 Liters/min = 120 minutes = 2 hours.",
                reference = "Respiratory Care Equipment Duration Math"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "Convert a core body temperature of 40°C into degrees Fahrenheit (°F).",
                options = listOf(
                    "102.0°F",
                    "104.0°F",
                    "105.8°F",
                    "106.2°F"
                ),
                correctIndex = 1,
                explanation = "Formula: °F = (°C * 9/5) + 32. (°C * 1.8) + 32 = (40 * 1.8) + 32 = 72 + 32 = 104.0°F.",
                reference = "Clinical Temperature Conversion Formulas"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "A medical mission purchases $8,000 worth of pharmaceutical supplies with a trade discount of 15%. What is the net price paid by the mission?",
                options = listOf(
                    "$1,200",
                    "$6,800",
                    "$7,200",
                    "$6,500"
                ),
                correctIndex = 1,
                explanation = "Discount amount = 15% of $8,000 = 0.15 * 8,000 = $1,200. Net price = $8,000 - $1,200 = $6,800.",
                reference = "NTS Financial & Business Math"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "The ratio of medical doctors to charge nurses in a field tent is 2 : 5. If there are 20 charge nurses deployed in the tent, how many medical doctors are present?",
                options = listOf(
                    "4 doctors",
                    "8 doctors",
                    "10 doctors",
                    "12 doctors"
                ),
                correctIndex = 1,
                explanation = "Ratio: Doctors / Nurses = 2 / 5. Doctors / 20 = 2 / 5. Doctors = (2 * 20) / 5 = 40 / 5 = 8 doctors.",
                reference = "NTS Ratio & Proportion Past Papers"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "An ambulance travels at a constant speed of 80 km/h. How many kilometers will it cover in 45 minutes?",
                options = listOf(
                    "40 km",
                    "60 km",
                    "50 km",
                    "70 km"
                ),
                correctIndex = 1,
                explanation = "Time = 45 minutes = 45/60 hours = 0.75 hours. Distance = Speed * Time = 80 km/h * 0.75 hours = 60 km.",
                reference = "NTS Distance & Time Problem Solving"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "What is the next term in this geometric progression sequence: 3, 6, 12, 24, 48, ...?",
                options = listOf(
                    "72",
                    "96",
                    "84",
                    "108"
                ),
                correctIndex = 1,
                explanation = "Each term is multiplied by 2 (common ratio r = 2). Next term = 48 * 2 = 96.",
                reference = "NTS Geometric Progression Syllabus"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "Calculate the Body Mass Index (BMI) of a pilgrim weighing 80 kg with a height of 1.75 meters (BMI = weight (kg) / [height (m)]²).",
                options = listOf(
                    "22.5 kg/m²",
                    "26.1 kg/m² (rounded to 1 decimal place)",
                    "31.2 kg/m²",
                    "18.4 kg/m²"
                ),
                correctIndex = 1,
                explanation = "Height squared = 1.75 * 1.75 = 3.0625 m². BMI = 80 / 3.0625 = 26.12 kg/m² (Overweight range).",
                reference = "Clinical Anthropometric Metrics"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "What is the median value of the following recorded systolic blood pressure readings (in mmHg): 110, 140, 125, 160, 130?",
                options = listOf(
                    "125 mmHg",
                    "130 mmHg",
                    "133 mmHg",
                    "140 mmHg"
                ),
                correctIndex = 1,
                explanation = "Order the values in ascending order: 110, 125, 130, 140, 160. The middle (3rd) value is 130 mmHg.",
                reference = "NTS Statistical Measures of Central Tendency"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "If Nurse A can complete patient triage logging in 6 hours alone, and Nurse B can complete the same task in 3 hours alone, how many hours will it take them to complete the task working together?",
                options = listOf(
                    "1.5 hours",
                    "2.0 hours",
                    "2.5 hours",
                    "4.5 hours"
                ),
                correctIndex = 1,
                explanation = "Work rate equation: 1/Total Time = 1/6 + 1/3 = 1/6 + 2/6 = 3/6 = 1/2. Total Time = 2 hours.",
                reference = "NTS Time & Work Equations"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "A dry powder vial containing 500 mg of Ceftriaxone is reconstituted with 10 mL of sterile water. What is the final concentration in mg/mL?",
                options = listOf(
                    "25 mg/mL",
                    "50 mg/mL",
                    "100 mg/mL",
                    "5 mg/mL"
                ),
                correctIndex = 1,
                explanation = "Concentration = Total Mass / Total Volume = 500 mg / 10 mL = 50 mg/mL.",
                reference = "Clinical Reconstitution Math"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "In a clinical trial of 200 patients, 160 achieved complete recovery. What is the success rate expressed as a fraction in lowest terms?",
                options = listOf(
                    "3/4",
                    "4/5",
                    "5/6",
                    "7/10"
                ),
                correctIndex = 1,
                explanation = "Fraction = 160 / 200. Divide numerator and denominator by 40 = 4 / 5.",
                reference = "NTS Fractions & Ratios"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "A clinic budget of $12,000 is allocated among Pharmaceuticals, Equipment, and Personnel in a ratio of 5 : 3 : 2. How much money is allocated for Equipment?",
                options = listOf(
                    "$2,400",
                    "$3,600",
                    "$6,000",
                    "$4,800"
                ),
                correctIndex = 1,
                explanation = "Sum of ratio parts = 5 + 3 + 2 = 10 parts. Value per part = $12,000 / 10 = $1,200. Equipment allocation (3 parts) = 3 * $1,200 = $3,600.",
                reference = "NTS Ratio Allocation Problems"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "If a patient receives a drug with an elimination half-life of 4 hours, what percentage of the initial dose remains in the bloodstream after 12 hours (3 half-lives)?",
                options = listOf(
                    "25%",
                    "12.5%",
                    "6.25%",
                    "50%"
                ),
                correctIndex = 1,
                explanation = "Number of half-lives = 12 / 4 = 3 half-lives. Fraction remaining = (1/2)^3 = 1/8 = 0.125 = 12.5%.",
                reference = "Pharmacokinetics & Exponential Decay Math"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "Solve for x in this proportion equation: 5 / 12 = x / 60.",
                options = listOf(
                    "20",
                    "25",
                    "30",
                    "15"
                ),
                correctIndex = 1,
                explanation = "Cross-multiply: 12 * x = 5 * 60 -> 12x = 300 -> x = 300 / 12 = 25.",
                reference = "NTS Algebra & Proportion Equations"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Quantitative Reasoning",
                question = "A hospital ward has 50 beds. If 42 beds are occupied, what is the bed occupancy percentage?",
                options = listOf(
                    "78%",
                    "84%",
                    "88%",
                    "92%"
                ),
                correctIndex = 1,
                explanation = "Occupancy = (42 / 50) * 100 = 0.84 * 100 = 84%.",
                reference = "Hospital Statistics & Metrics"
            )
        )

        return list
    }
}
