package com.drtahir.studentkit.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 3
 * Subject: Geography, Places & Holy Sites (30 100% Unique MCQs)
 * Covers Makkah accommodation sectors, Haram gates, Mina tent streets, Jamarat layout, Arafat, Muzdalifah, Madinah Markazia, historical sites, and airports.
 */
object Moavineen1000Part3 {

    fun getGeographyQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Which primary gate of Masjid al-Haram provides direct pedestrian access to the King Abdulaziz Expansion area and Ajyad bus transport hub?",
                options = listOf(
                    "Bab King Abdul Aziz (Gate 1)",
                    "Bab-us-Salam (Gate 19)",
                    "Bab-e-Umrah (Gate 62)",
                    "Bab King Fahd (Gate 79)"
                ),
                correctIndex = 0,
                explanation = "Bab King Abdul Aziz (Gate 1) is located at the southern front of Masjid al-Haram, directly facing the Ajyad street and major bus terminals.",
                reference = "MORA Geography & Map Guide for Holy Sites"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "How are the tent camps in Mina geographically structured to help Moavineen navigate and locate Pakistani pilgrim Maktabs?",
                options = listOf(
                    "Randomly scattered without street names",
                    "Divided into numerical color-coded sectors, major numbered streets (e.g., Street 204, Street 502), and specific Maktab numbers",
                    "Sorted by alphabet letters only",
                    "Organized according to pilgrim height"
                ),
                correctIndex = 1,
                explanation = "Mina is mapped systematically using color codes, numbered sectors, major arterial streets (e.g., Main Street 204, 502, King Abdulaziz Road), and Maktab IDs.",
                reference = "MORA Mina Navigation & Sector Map Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "How many operational levels does the modern Jamarat Bridge complex contain, and how is crowd flow controlled across these levels?",
                options = listOf(
                    "Single ground level only",
                    "5 levels (Ground floor + 4 upper levels) with one-way pedestrian ramps to ensure unidirectional crowd flow",
                    "10 underground tunnels",
                    "3 floating helipads"
                ),
                correctIndex = 1,
                explanation = "The modern Jamarat Bridge features 5 levels (ground + 4 floors) designed with air conditioning, emergency exits, and strict one-way pedestrian ramps.",
                reference = "Saudi Ministry of Hajj - Jamarat Complex Geography"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "Where is Masjid-e-Namirah located on the plains of Arafat, and what geographical boundary precaution must Moavineen inform pilgrims about?",
                options = listOf(
                    "Entirely inside Muzdalifah boundaries",
                    "Located on the western boundary of Arafat; the front portion of the mosque lies in Wadi Urana (outside Arafat), while the rear lies within Arafat",
                    "Located in central Mina",
                    "Situated on top of Mount Uhud"
                ),
                correctIndex = 1,
                explanation = "Masjid-e-Namirah straddles the boundary of Arafat. The front section is in Wadi Urana (outside Arafat). Pilgrims standing inside the front portion during Wuquf must step back into the Arafat section.",
                reference = "MORA Arafat Boundary Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "What sacred historical monument and hill is situated at Muzdalifah where pilgrims gather for supplication after Fajr prayer on 10th Dhu al-Hijjah?",
                options = listOf(
                    "Jabal-e-Noor (Cave Hira)",
                    "Mash'ar al-Haram (The Sacred Monument)",
                    "Jabal-e-Thawr",
                    "Jabal-e-Rahmat"
                ),
                correctIndex = 1,
                explanation = "Mash'ar al-Haram is the prominent mosque/hill monument in Muzdalifah where Wuquf-e-Muzdalifah and Zikr are performed before proceeding to Mina.",
                reference = "MORA Holy Sites Landmark Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "Why are the Azizia and Shisha districts of Makkah predominantly chosen for housing Government Hajj Scheme Pakistani pilgrims?",
                options = listOf(
                    "Because they are located in Madinah city",
                    "Because of their strategic proximity to Mina (enabling walking access during Hajj days) and direct SAPTCO shuttle connectivity to Masjid al-Haram",
                    "Because no other buildings exist in Saudi Arabia",
                    "Because they are situated next to Jeddah Seaport"
                ),
                correctIndex = 1,
                explanation = "Azizia and Shisha lie between Makkah center and Mina, providing optimal access to Mina during Hajj days and continuous bus links to Haram.",
                reference = "MORA Accommodation Sector Profile"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Which main historical gate of Masjid an-Nabawi in Madinah Munawwarah is the entry point for presenting Salam at the Rawdah Mubarak (Sacred Chamber)?",
                options = listOf(
                    "Bab-us-Salam (Gate 1)",
                    "Bab-e-Majidi (Gate 18)",
                    "Bab King Fahd (Gate 21)",
                    "Bab-e-Nisa (Gate 39)"
                ),
                correctIndex = 0,
                explanation = "Bab-us-Salam (Gate 1) on the western side of Masjid an-Nabawi is the traditional entrance for men entering to present Salam at the Rawdah of Prophet Muhammad (PBUH).",
                reference = "Madinah Sacred Landmarks Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "Where is the sacred area of Riaz-ul-Jannah ('Garden of Paradise') located inside Masjid an-Nabawi?",
                options = listOf(
                    "Between the Sacred Chamber (Rawdah / Tomb of Prophet Muhammad PBUH) and the Pulpit (Minbar)",
                    "Outside in the open marble courtyard near Jannat al-Baqi",
                    "On the roof of the mosque expansion",
                    "Inside the underground parking level"
                ),
                correctIndex = 0,
                explanation = "Riaz-ul-Jannah is carpeted in distinct green carpet, situated between the Minbar and the Rawdah Mubarak.",
                reference = "MORA Madinah Guide for Moavineen"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Which historic cemetery is located immediately adjacent to the eastern enclosure of Masjid an-Nabawi in Madinah?",
                options = listOf(
                    "Jannat al-Mu'alla (Makkah)",
                    "Jannat al-Baqi (Baqi al-Gharqad)",
                    "Shuhada-e-Uhud Cemetery",
                    "Arafat Central Graveyard"
                ),
                correctIndex = 1,
                explanation = "Jannat al-Baqi lies right outside the eastern gates of Masjid an-Nabawi, holding the graves of many Ahl al-Bayt, Companions (Sahabah), and Mothers of the Believers.",
                reference = "Madinah Historical Places Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "Which historic mosque in Madinah is distinguished as the first mosque built in Islamic history?",
                options = listOf(
                    "Masjid al-Qiblatain",
                    "Masjid Quba",
                    "Masjid al-Ghamama",
                    "Masjid al-Jumu'ah"
                ),
                correctIndex = 1,
                explanation = "Masjid Quba is the first mosque constructed by Prophet Muhammad (PBUH) upon arrival in Madinah during Hijrah. Offering two Raka'at there carries the reward of an Umrah.",
                reference = "Madinah Ziyarat Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the historical significance of Masjid al-Qiblatain in Madinah?",
                options = listOf(
                    "It is where the first Hajj sermon was delivered",
                    "It is the mosque where the Qibla direction was divine revelationally shifted from Bait-ul-Muqaddas (Jerusalem) to the Holy Kaaba (Makkah) during prayer",
                    "It is located on top of Mount Arafat",
                    "It is the main office of Saudi immigration"
                ),
                correctIndex = 1,
                explanation = "During a Dhuhr/Asr prayer at Masjid al-Qiblatain, Allah commanded the Prophet (PBUH) to turn from Jerusalem toward the Kaaba in Makkah.",
                reference = "Madinah Historical Landmarks Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "Where are the graves of Sayyidush-Shuhada Hazrat Hamzah (RA) and the martyrs of the Battle of Uhud situated?",
                options = listOf(
                    "At the foot of Mount Uhud, approximately 5 km north of Masjid an-Nabawi",
                    "Inside Jannat al-Mu'alla in Makkah",
                    "On the plains of Muzdalifah",
                    "Inside King Abdulaziz Airport"
                ),
                correctIndex = 0,
                explanation = "The Shuhada Uhud cemetery and Mount Uhud are located in the northern sector of Madinah Munawwarah.",
                reference = "Madinah Ziyarat Map Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Where is the dedicated Hajj Terminal located where Pakistani Hajj charter flights land in Jeddah?",
                options = listOf(
                    "At King Fahd Seaport",
                    "At King Abdulaziz International Airport (KAIA) Hajj Terminal - distinctive white fiberglass canopy plaza area",
                    "Inside Riyadh Diplomatic Quarter",
                    "At Taif Airport"
                ),
                correctIndex = 1,
                explanation = "Pakistani charter flights arrive at the massive teflon-canopied Hajj Terminal at King Abdulaziz International Airport in Jeddah.",
                reference = "MORA Airport Operations Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "Which mountain in Makkah houses Cave Hira (Ghar-e-Hira) where the first Quranic revelation (Surah Al-Alaq) descended?",
                options = listOf(
                    "Jabal al-Thawr",
                    "Jabal al-Noor (Mountain of Light)",
                    "Jabal Abu Qubays",
                    "Jabal al-Rahmah"
                ),
                correctIndex = 1,
                explanation = "Jabal al-Noor is situated northeast of Makkah, containing Cave Hira near its summit.",
                reference = "Makkah Sacred Geography"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Which cave on Jabal Thawr served as the refuge for Prophet Muhammad (PBUH) and Hazrat Abu Bakr Siddique (RA) during the Migration (Hijrah) to Madinah?",
                options = listOf(
                    "Ghar-e-Hira",
                    "Ghar-e-Thawr",
                    "Cave of Kahf",
                    "Ghar-e-Safa"
                ),
                correctIndex = 1,
                explanation = "Ghar-e-Thawr is located on Mount Thawr south of Makkah Mukarramah.",
                reference = "Makkah Historical Sites Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "What prominent mountain stands in the middle of the plains of Arafat where Prophet Muhammad (PBUH) delivered the Farewell Sermon (Khutbat-ul-Wida)?",
                options = listOf(
                    "Jabal-e-Rahmat (Mount of Mercy)",
                    "Jabal-e-Noor",
                    "Jabal-e-Uhud",
                    "Jabal-e-Arafat"
                ),
                correctIndex = 0,
                explanation = "Jabal-e-Rahmat is the small granite hill located in Arafat where the Prophet (PBUH) stood during the Farewell Hajj.",
                reference = "MORA Arafat Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Which historical cemetery in Makkah contains the grave of Hazrat Khadijah-tul-Kubra (RA)?",
                options = listOf(
                    "Jannat al-Baqi",
                    "Jannat al-Mu'alla (Al-Hajun Cemetery)",
                    "Mina Central Graveyard",
                    "Arafat Valley Cemetery"
                ),
                correctIndex = 1,
                explanation = "Jannat al-Mu'alla is located near Al-Hajun in Makkah, where Hazrat Khadijah (RA) and ancestors of the Prophet (PBUH) are buried.",
                reference = "Makkah Ziyarat Reference"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the function of the Kudai bus terminal and parking complex in Makkah?",
                options = listOf(
                    "It is a cargo shipping dock",
                    "It is a major transportation hub and shuttle bus staging yard connecting outlying Makkah hotel sectors directly to Masjid al-Haram",
                    "It is the main hospital in Mina",
                    "It is the slaughterhouse district"
                ),
                correctIndex = 1,
                explanation = "Kudai is a primary public transit node on the southern side of Makkah housing large parking structures and SAPTCO bus terminals.",
                reference = "Makkah Transport Infrastructure Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "Where is the Miqat mosque Masjid-e-Ayesha (Tan'im) located relative to Makkah Mukarramah?",
                options = listOf(
                    "Inside Madinah airport",
                    "Approximately 7.5 km north of Masjid al-Haram, serving as the nearest Miqat for residents/pilgrims in Makkah wishing to assume Ihram for a secondary Umrah",
                    "100 km away in Taif",
                    "Inside the Mina tent encampment"
                ),
                correctIndex = 1,
                explanation = "Masjid-e-Ayesha at Tan'im marks the boundary of Hill (outside Haram) nearest to Makkah for assuming Umrah Ihram.",
                reference = "MORA Miqat Geography Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the name of the high-speed electric railway connecting Makkah, Jeddah KAIA Airport, King Abdullah Economic City, and Madinah?",
                options = listOf(
                    "Khyber Express",
                    "Haramain High Speed Railway (HHR)",
                    "Riyadh Metro Line",
                    "SAPTCO Highway Bus"
                ),
                correctIndex = 1,
                explanation = "The Haramain High Speed Railway operates 300 km/h bullet trains linking Makkah, Jeddah Airport, and Madinah.",
                reference = "Saudi Transport & Infrastructure Profile"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "Which district of Makkah houses the main headquarters of the Pakistan Hajj Medical Mission Hospital and MORA Main Control Office?",
                options = listOf(
                    "Batha",
                    "Azizia (Southern / Northern Azizia)",
                    "Jeddah Corniche",
                    "Madinah Markazia"
                ),
                correctIndex = 1,
                explanation = "Azizia houses the main Pakistan Hajj Mission HQ, central hospital facility, and main administrative offices.",
                reference = "MORA Mission Directory"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Where is the Muaisem slaughterhouse complex (Masa'lakh Muaisem) located where Qurbani for Hujjaj is officially performed?",
                options = listOf(
                    "Near the northern perimeter of Mina valley",
                    "Inside Jeddah Airport lounge",
                    "On top of Mount Safa",
                    "In Madinah Markazia"
                ),
                correctIndex = 0,
                explanation = "The automated Muaisem Slaughterhouses are situated near Mina for processing Hajj Qurbani under Islamic Development Bank (IDB) oversight.",
                reference = "MORA Qurbani Infrastructure Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "What geographic boundary signboards mark the entry and exit perimeter of Makkah Haram (Holy Sanctuary)?",
                options = listOf(
                    "Red traffic lights",
                    "Large archway structures and roadside boundary signboards clearly marked in Arabic and English ('Haram Boundary / Bidayat al-Haram')",
                    "No markers exist",
                    "Yellow sea buoys"
                ),
                correctIndex = 1,
                explanation = "Distinct landmark arches and signposts mark the exact boundaries surrounding Makkah beyond which non-Muslim entry is prohibited.",
                reference = "MORA Boundary Geography Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "Which gate of Masjid al-Haram faces Mount Safa where Sa'i begins?",
                options = listOf(
                    "Bab-us-Safa (Gate 11)",
                    "Bab-e-Umrah",
                    "Bab King Fahd",
                    "Bab-e-Fath"
                ),
                correctIndex = 0,
                explanation = "Bab-us-Safa leads directly into the start of the Mas'a (Sa'i walking gallery) at Mount Safa.",
                reference = "Masjid al-Haram Gate Directory"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the name of the valley located between Arafat and Muzdalifah where the army of Abraha was destroyed (Surah Al-Fil)?",
                options = listOf(
                    "Wadi Urana",
                    "Wadi Muhassir",
                    "Wadi Fatima",
                    "Wadi Aqeeq"
                ),
                correctIndex = 1,
                explanation = "Wadi Muhassir lies between Muzdalifah and Mina. Pilgrims pass through quickly without lingering as it is a place where divine punishment descended.",
                reference = "MORA Sacred History Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the 'Markazia' area in Madinah Munawwarah?",
                options = listOf(
                    "The central commercial and hotel district immediately surrounding Masjid an-Nabawi within the ring road",
                    "An industrial agricultural zone outside the city",
                    "The airport runway",
                    "A mountainous hiking trail"
                ),
                correctIndex = 0,
                explanation = "Markazia refers to the inner pedestrian ring surrounding Masjid an-Nabawi housing high-density pilgrim hotels.",
                reference = "Madinah Infrastructure Map"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "Which gate of Masjid an-Nabawi is specifically designated for female pilgrims entering the Rawdah Riaz-ul-Jannah during reserved women's visiting hours?",
                options = listOf(
                    "Bab-us-Salam (Gate 1)",
                    "Bab-e-Usman / Bab-e-Nisa / Gates 24-25 (Northern/Eastern expansion gates)",
                    "Bab-e-Baqi",
                    "Bab King Abdul Aziz"
                ),
                correctIndex = 1,
                explanation = "Female access to Riaz-ul-Jannah is managed through designated northern/eastern gates (e.g. Gate 24/25 / Usman Gate) during schedule windows.",
                reference = "Madinah Female Courtyard SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Where is the Bir Ali Miqat station located relative to Madinah Munawwarah city center?",
                options = listOf(
                    "Approximately 9 km southwest of Masjid an-Nabawi along the highway to Makkah",
                    "Inside Madinah Airport lounge",
                    "50 km north towards Syria",
                    "Adjacent to Jannat al-Baqi"
                ),
                correctIndex = 0,
                explanation = "Bir Ali (Dhul Hulaifah) is situated 9 km southwest of Madinah along the main Makkah road.",
                reference = "MORA Miqat Directory"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "What key feature distinguishes the King Fahd Expansion of Masjid al-Haram?",
                options = listOf(
                    "The massive western wing extension featuring multi-story prayer halls, escalators, air-conditioned basements, and Gate 79",
                    "An open camel racing track",
                    "A cargo train station inside Mataf",
                    "An outdoor camping ground"
                ),
                correctIndex = 0,
                explanation = "The King Fahd Expansion forms the large western structure of Haram entered through Bab King Fahd (Gate 79).",
                reference = "Masjid al-Haram Architecture Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the historical location of Al-Ghamama Mosque (Masjid al-Ghamama) in Madinah?",
                options = listOf(
                    "Located about 500 meters southwest of Masjid an-Nabawi where Prophet Muhammad (PBUH) performed Eid and Rain (Istisqa) prayers",
                    "On top of Mount Arafat",
                    "Inside Jeddah Seaport",
                    "Near Mina Jamarat Bridge"
                ),
                correctIndex = 0,
                explanation = "Masjid al-Ghamama is near the southwest perimeter of Masjid an-Nabawi plaza, marking where cloud shade covered the Prophet (PBUH) during outdoor prayers.",
                reference = "Madinah Historical Places Guide"
            )
        )

        return list
    }
}
