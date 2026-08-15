package com.example.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 6
 * Category: Analytical Reasoning (25 100% Unique MCQs)
 * Covers coding-decoding, direction/displacement geometry, shift scheduling, blood relations & syllogisms.
 */
object Hajj1000Part6 {

    fun getAnalyticalQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "In a standard alphabetical substitution code, if the word 'HEAL' is coded as 'JGCN' by shifting each letter forward by +2 positions in the English alphabet, how is the word 'CARE' coded under the exact same rule?",
                options = listOf(
                    "BAQD",
                    "EDTG",
                    "DBQF",
                    "FGUH"
                ),
                correctIndex = 1,
                explanation = "Shift rule (+2): C (+2) -> E, A (+2) -> C, R (+2) -> T, E (+2) -> G. Therefore 'CARE' becomes 'EDTG'.",
                reference = "NTS Verbal & Analytical Ability Past Papers"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "A medical paramedic leaves their Mina tent and walks 6 km North, then turns right and walks 8 km East. What is the direct straight-line distance (displacement) from the paramedic's current position to their starting tent?",
                options = listOf(
                    "14 km",
                    "10 km",
                    "12 km",
                    "7 km"
                ),
                correctIndex = 1,
                explanation = "Applying Pythagoras Theorem (c² = a² + b²): Displacement = √(6² + 8²) = √(36 + 64) = √100 = 10 km.",
                reference = "NTS Spatial Direction Puzzles"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Analytical Shift Rule: Seven nurses are assigned shifts from Monday through Sunday. Nurse Bilal must work immediately after Nurse Farhan. Nurse Farhan cannot work on Monday or Tuesday. If Nurse Farhan works on Wednesday, on which day must Nurse Bilal work?",
                options = listOf(
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Tuesday"
                ),
                correctIndex = 1,
                explanation = "Since Bilal works immediately after Farhan, and Farhan's shift is Wednesday, Bilal's shift is Thursday.",
                reference = "NTS Grouping & Sequencing Constraints"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Blood Relations Logic: Pointing to a senior surgeon in an old photograph, Ahmed says: 'His father is the only son of my paternal grandfather.' How is the surgeon in the photograph related to Ahmed?",
                options = listOf(
                    "Ahmed's cousin",
                    "Ahmed's father (or Ahmed himself)",
                    "Ahmed's nephew",
                    "Ahmed's maternal uncle"
                ),
                correctIndex = 1,
                explanation = "Ahmed's paternal grandfather's 'only son' is Ahmed's father. Thus, the surgeon's father is Ahmed's father, making the surgeon Ahmed's father (or Ahmed himself).",
                reference = "NTS Deductive Blood Relations"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Deductive Syllogisms: Read the statements carefully: (1) All medical doctors are registered graduates. (2) Some registered graduates are published authors. Which conclusion logically follows?",
                options = listOf(
                    "All registered graduates are medical doctors",
                    "Some registered graduates are medical doctors, and some doctors may be published authors",
                    "No published author can be a doctor",
                    "All published authors are registered doctors"
                ),
                correctIndex = 1,
                explanation = "Statement 1 implies Doctors is a subset of Registered Graduates. Thus, some graduates are doctors, and there is possible overlap with published authors.",
                reference = "NTS Syllogisms & Logical Deductions"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Seating Logic: Five doctors (A, B, C, D, E) sit in a row facing North. C sits in the exact middle. A sits immediately to the left of C. E sits on the extreme right end. D sits immediately to the right of C. Where does B sit?",
                options = listOf(
                    "In the exact middle",
                    "On the extreme left end",
                    "Immediately to the right of E",
                    "Between C and D"
                ),
                correctIndex = 1,
                explanation = "Row positions 1, 2, 3, 4, 5. C is at pos 3. A is left of C (pos 2). E is at pos 5. D is right of C (pos 4). Thus pos 1 is occupied by B (extreme left end).",
                reference = "NTS Linear Seating Arrangement Puzzles"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Ranking Logic: In a merit list of 25 medical candidates, Ali ranks 7th from the top. What is Ali's rank from the bottom?",
                options = listOf(
                    "18th",
                    "19th",
                    "20th",
                    "17th"
                ),
                correctIndex = 1,
                explanation = "Rank from bottom = Total candidates - Rank from top + 1 = 25 - 7 + 1 = 19th.",
                reference = "NTS Ranking & Order Reasoning"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Alphabetical Reversal Code: If the word 'NURSE' is coded as 'ESRUN' by reversing the order of all letters, how is the word 'DOCTOR' coded?",
                options = listOf(
                    "ROTDOC",
                    "ROTCOD",
                    "RDOTOC",
                    "CORDOT"
                ),
                correctIndex = 1,
                explanation = "Reversing the letters of 'DOCTOR' gives: D-O-C-T-O-R reversed = R-O-T-C-O-D.",
                reference = "NTS Coding & Decoding Syllabus"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Logical Modus Tollens: 'If a pilgrim has Heat Stroke, then the pilgrim has central nervous system dysfunction.' Pilgrim X does NOT have central nervous system dysfunction. What conclusion logically follows?",
                options = listOf(
                    "Pilgrim X definitely has Heat Stroke",
                    "Pilgrim X does NOT have Heat Stroke",
                    "Pilgrim X has cholera",
                    "Pilgrim X needs immediate intubation"
                ),
                correctIndex = 1,
                explanation = "Modus Tollens rule: If P implies Q, then Not-Q implies Not-P. Since Q (CNS dysfunction) is false, P (Heat Stroke) must be false.",
                reference = "NTS Conditional Logic & Deductive Proofs"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Analogy Logic: Complete the analogy: Stethoscope : Physician :: Scalpel : ?",
                options = listOf(
                    "Pharmacist",
                    "Surgeon",
                    "Radiographer",
                    "Pediatrician"
                ),
                correctIndex = 1,
                explanation = "A stethoscope is the primary instrument of a physician, just as a scalpel is the primary surgical instrument of a surgeon.",
                reference = "NTS Verbal Analogies"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "At 3:00 PM, what is the acute angle formed between the hour hand and the minute hand of an analog clock?",
                options = listOf(
                    "45 degrees",
                    "90 degrees",
                    "120 degrees",
                    "60 degrees"
                ),
                correctIndex = 1,
                explanation = "At 3:00, the minute hand points at 12 and the hour hand points at 3. The angle is 3 * 30 degrees = 90 degrees (right angle).",
                reference = "NTS Clock Geometry Puzzles"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Direction Logic: A doctor faces North. She turns 90 degrees clockwise, then 180 degrees counter-clockwise. In which direction is she facing now?",
                options = listOf(
                    "East",
                    "West",
                    "South",
                    "North"
                ),
                correctIndex = 1,
                explanation = "Facing North. 90 deg clockwise -> East. 180 deg counter-clockwise from East -> West. She faces West.",
                reference = "NTS Directional Reasoning"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Symbol Substitution: If '+' means multiplication, '-' means division, '*' means addition, and '/' means subtraction, what is the value of: 10 + 2 - 4 * 6 / 3?",
                options = listOf(
                    "5",
                    "8",
                    "10",
                    "12"
                ),
                correctIndex = 1,
                explanation = "Substitute symbols: 10 * 2 / 4 + 6 - 3. Solve using BODMAS: (10 * 2) / 4 = 20 / 4 = 5. Then 5 + 6 - 3 = 11 - 3 = 8.",
                reference = "NTS Mathematical Symbol Substitution"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Missing Letter Series: Find the missing letter in this alphabetical sequence: B, D, G, K, P, ?",
                options = listOf(
                    "U",
                    "V",
                    "W",
                    "T"
                ),
                correctIndex = 1,
                explanation = "Letter positions: B(2), D(4) [+2], G(7) [+3], K(11) [+4], P(16) [+5]. Next jump is +6: 16 + 6 = 22 = V.",
                reference = "NTS Alphabetical Series Puzzles"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "A wooden cube with a 3 cm side is painted red on all 6 faces and then cut into 27 small 1 cm cubes. How many small cubes have red paint on EXACTLY 3 faces?",
                options = listOf(
                    "4 cubes",
                    "8 cubes",
                    "12 cubes",
                    "6 cubes"
                ),
                correctIndex = 1,
                explanation = "Cubes with paint on exactly 3 faces are the corner cubes. Any cube has 8 corners, so exactly 8 small cubes have 3 painted faces.",
                reference = "NTS Cube & Geometry Reasoning"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Course of Action Logic: Statement: 'Several field clinics in Mina are experiencing sudden power outages during peak heat hours.' Course of Action: I. Deploy backup diesel generators immediately. II. Suspend all patient intake permanently.",
                options = listOf(
                    "Only I follows",
                    "Only II follows",
                    "Both I and II follow",
                    "Neither I nor II follows"
                ),
                correctIndex = 0,
                explanation = "Deploying emergency backup generators (Action I) directly addresses power loss. Permanently suspending patient care (Action II) is extreme and harmful. Thus, only I follows.",
                reference = "NTS Course of Action Analytical Evaluation"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Constraint Logic: A selection panel must choose 2 doctors from (A, B, C) and 2 nurses from (X, Y, Z). Constraint: If A is selected, Y CANNOT be selected. If X is selected, C MUST be selected. If Doctor A and Nurse Z are selected, who is the second doctor?",
                options = listOf(
                    "Doctor B",
                    "Doctor C",
                    "Either B or C",
                    "Doctor A cannot be selected"
                ),
                correctIndex = 2,
                explanation = "A is selected. The second doctor can be either B or C, since neither violates constraints (if C is selected, X isn't mandatory unless X was chosen). Either B or C can fill the second doctor slot.",
                reference = "NTS Grouping & Selection Constraints"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Truth Puzzle: Person A says: 'Person B is a liar.' Person B says: 'Person A and I are both liars.' What can be deduced?",
                options = listOf(
                    "A is a truth-teller and B is a liar",
                    "B is a truth-teller and A is a liar",
                    "Both are truth-tellers",
                    "Both are liars"
                ),
                correctIndex = 0,
                explanation = "If B were telling the truth, B's statement that B is a liar would be a contradiction. Thus B is a liar. Since B is a liar, A's statement that 'B is a liar' is true. Thus A is a truth-teller.",
                reference = "NTS Logic Puzzles & Deductions"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Venn Diagram Logic: In a group of 50 medical officers, 30 speak Urdu, 25 speak Arabic, and 10 speak BOTH languages. How many officers speak NEITHER language?",
                options = listOf(
                    "2 officers",
                    "5 officers",
                    "10 officers",
                    "8 officers"
                ),
                correctIndex = 1,
                explanation = "Total speaking at least one language = Urdu + Arabic - Both = 30 + 25 - 10 = 45. Speak neither = Total - 45 = 50 - 45 = 5 officers.",
                reference = "NTS Set Theory & Venn Diagrams"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Negative Syllogisms: Statement 1: 'No surgeons are radiographers.' Statement 2: 'All radiographers are hospital employees.' Which conclusion logically follows?",
                options = listOf(
                    "No hospital employees are surgeons",
                    "Some hospital employees are radiographers, and some hospital employees are NOT surgeons",
                    "All surgeons are hospital employees",
                    "All hospital employees are radiographers"
                ),
                correctIndex = 1,
                explanation = "Since radiographers are hospital employees and no surgeon is a radiographer, those hospital employees who are radiographers are definitely not surgeons.",
                reference = "NTS Syllogistic Deductions"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Number Series: What is the next number in this sequence: 2, 6, 12, 20, 30, 42, ...?",
                options = listOf(
                    "52",
                    "56",
                    "60",
                    "48"
                ),
                correctIndex = 1,
                explanation = "Differences between terms: 6-2=4, 12-6=6, 20-12=8, 30-20=10, 42-30=12. Next difference is +14: 42 + 14 = 56.",
                reference = "NTS Number Pattern Analysis"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Letter Code Shift: If 'HAJJ' is coded as 'KDKM' by shifting each letter forward by +3 positions, how is 'MINA' coded under the exact same rule?",
                options = listOf(
                    "PLQD",
                    "PLPD",
                    "OKQD",
                    "PLQC"
                ),
                correctIndex = 0,
                explanation = "Shift rule (+3): M (+3) -> P, I (+3) -> L, N (+3) -> Q, A (+3) -> D. Result = 'PLQD'.",
                reference = "NTS Alphabetical Substitution Code"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Assertion and Reason: Assertion (A): Evaporative cooling is ineffective in 100% relative humidity. Reason (R): High humidity prevents water from evaporating into the air. Choose the correct evaluation.",
                options = listOf(
                    "Both A and R are true, and R is the correct explanation of A",
                    "Both A and R are true, but R is NOT the correct explanation of A",
                    "A is true but R is false",
                    "A is false but R is true"
                ),
                correctIndex = 0,
                explanation = "Evaporation relies on a humidity gradient. At 100% relative humidity, air cannot absorb additional moisture, stopping evaporation. Both statements are true and R explains A.",
                reference = "NTS Assertion-Reason Scientific Logic"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Family Tree Deduction: A female doctor says, 'The mother-in-law of the wife of my father's only son is my mother.' What is the relationship between the speaker and 'my father's only son'?",
                options = listOf(
                    "They are brother and sister",
                    "They are mother and son",
                    "They are husband and wife",
                    "They are cousins"
                ),
                correctIndex = 0,
                explanation = "Father's 'only son' is the speaker's brother. The wife of the speaker's brother is the sister-in-law. Her mother-in-law is the speaker's mother. Thus, the speaker and 'father's only son' are brother and sister.",
                reference = "NTS Family Relationship Puzzles"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Analytical Reasoning",
                question = "Statement and Argument: Statement: 'Should all medical staff deployed on Hajj duty undergo compulsory cardiopulmonary resuscitation (CPR) recertification before departure?' Argument 1: Yes, because CPR protocols update frequently and resuscitation efficiency directly saves lives during mass crowd emergencies. Argument 2: No, because medical staff already graduated years ago. Which argument is strong?",
                options = listOf(
                    "Only Argument 1 is strong",
                    "Only Argument 2 is strong",
                    "Both Arguments 1 and 2 are strong",
                    "Neither Argument 1 nor 2 is strong"
                ),
                correctIndex = 0,
                explanation = "Argument 1 provides a logical, life-saving justification directly relevant to Hajj crowd emergencies. Argument 2 is weak as past graduation does not guarantee current skill retention.",
                reference = "NTS Logical Argument Strength Evaluation"
            )
        )

        return list
    }
}
