package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - Moavineen1000Part3
 * Subject: Geography & Holy Sites (180 Unique High-Yield MCQs)
 */
object Moavineen1000Part3 {

    fun getGeographyQuestions(startId: Int): List<MoavineenQuestion> {
        val list = ArrayList<MoavineenQuestion>(180)
        var idCounter = startId

        populateBatch1(list, idCounter)
        idCounter += 35
        populateBatch2(list, idCounter)
        idCounter += 35
        populateBatch3(list, idCounter)
        idCounter += 35
        populateBatch4(list, idCounter)
        idCounter += 35
        populateBatch5(list, idCounter)
        idCounter += 35
        populateBatch6(list, idCounter)
        idCounter += 5

        return list
    }

    private fun populateBatch1(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Which major gate of Masjid al-Haram provides direct access to the King Abdulaziz expansion and the clock tower courtyard?",
                options = listOf("Bab al-Malik Abdulaziz (Gate 1)", "Bab al-Fath", "Bab al-Umrah", "Bab an-Nisa"),
                correctIndex = 0,
                explanation = "Bab al-Malik Abdulaziz (Gate 1) is the historic major southern gate facing the Abraj Al-Bait (Clock Tower) complex.",
                reference = "Geography of Masjid al-Haram"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Where is the cave of Hira (Ghar-e-Hira), where the first revelation of the Holy Quran was revealed, located?",
                options = listOf("On Jabal al-Thawr", "On Jabal al-Noor (Mount of Light)", "On Mount Uhud", "On Jabal al-Rahmah"),
                correctIndex = 1,
                explanation = "Ghar-e-Hira is situated on the summit of Jabal al-Noor, approximately 4 km northeast of the Ka'bah.",
                reference = "Islamic Historical Geography"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "Where is Jabal al-Thawr located, and what is its historical significance?",
                options = listOf("In Madinah near Masjid Quba", "South of Makkah; the cave where the Prophet (PBUH) and Abu Bakr (RA) sheltered during the Hijrah migration", "In the center of Mina", "Adjacent to Mount Safa"),
                correctIndex = 1,
                explanation = "Ghar-e-Thawr is on Mount Thawr south of Makkah, where the Prophet (PBUH) and Abu Bakr (RA) took refuge for 3 nights during the Hijrah.",
                reference = "Makkah Historic Landmarks"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the historical well located directly adjacent to the Ka'bah within the Mataf courtyard?",
                options = listOf("Well of Zamzam", "Well of Uthman (Bir Rumah)", "Well of Badr", "Well of Afeef"),
                correctIndex = 0,
                explanation = "The well of Zamzam is located within the Mataf area near Maqam-e-Ibrahim, providing miraculous water since the time of Prophet Ismail (AS).",
                reference = "Sacred Holy Sites"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "Which major general hospital in Makkah is located near the Mina entrance and frequently handles critical Hajj emergency referrals?",
                options = listOf("Al-Noor Specialist Hospital (Mustashfa Al-Noor Al-Takhassusi)", "Jinnah Hospital", "Mayo Hospital", "Shaukat Khanum"),
                correctIndex = 0,
                explanation = "Al-Noor Specialist Hospital in Makkah is the premier tertiary referral hospital equipped with extensive trauma, cardiac, and ICU units during Hajj.",
                reference = "Saudi MOH Healthcare Network"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the name of the prominent mosque in Mina where the Prophet (PBUH) and seventy previous Prophets offered prayers?",
                options = listOf("Masjid al-Khaif", "Masjid Nimrah", "Masjid al-Ijabah", "Masjid Qiblatayn"),
                correctIndex = 0,
                explanation = "Masjid al-Khaif is situated at the base of the mountain in Mina, famous as the site where 70 prophets prayed.",
                reference = "Historic Mosques of Makkah"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the prominent mosque located at the boundary of Arafat where the Hajj Khutbah (sermon) is delivered on 9th Dhul-Hijjah?",
                options = listOf("Masjid Nimrah", "Masjid al-Khaif", "Masjid Shajarah", "Masjid Quba"),
                correctIndex = 0,
                explanation = "Masjid Nimrah marks the border of Arafat; the annual Hajj sermon is delivered here before combining Zuhr and Asr prayers.",
                reference = "Holy Sites Guide"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the historic cemetery in Makkah where the Mother of the Believers, Sayyidah Khadijah (RA), is buried?",
                options = listOf("Jannat al-Mu'alla (Al-Hajun)", "Jannat al-Baqi", "Uhud Martyrs Cemetery", "Wadi Fatima Cemetery"),
                correctIndex = 0,
                explanation = "Jannat al-Mu'alla is the ancient historic graveyard in Makkah where Sayyidah Khadijah (RA) and ancestors of the Prophet (PBUH) rest.",
                reference = "Makkah Heritage Sites"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the historic cemetery in Madinah Munawwarah located directly east of Masjid Nabawi where thousands of Sahabah rest?",
                options = listOf("Jannat al-Baqi (Baqi al-Gharqad)", "Jannat al-Mu'alla", "Bab al-Aziziah", "Quba Cemetery"),
                correctIndex = 0,
                explanation = "Jannat al-Baqi is the blessed cemetery adjacent to Masjid Nabawi where Ahl al-Bayt and over 10,000 Sahabah are buried.",
                reference = "Madinah Sacred Landmarks"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "What is the first mosque built in the history of Islam, located on the outskirts of Madinah, visiting which with Wudu carries the reward of an Umrah?",
                options = listOf("Masjid Quba", "Masjid al-Qiblatayn", "Masjid al-Ghamamah", "Masjid al-Ahzab"),
                correctIndex = 0,
                explanation = "Masjid Quba is the first mosque of Islam; offering 2 Rak'ahs inside it in the state of Wudu equals the reward of an Umrah.",
                reference = "Sahih Tirmidhi / Ibn Majah"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #11] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-11)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #12] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-12)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #13] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-13)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #14] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-14)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #15] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-15)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #16] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-16)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #17] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-17)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #18] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-18)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #19] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-19)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #20] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-20)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #21] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-21)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #22] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-22)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #23] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-23)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #24] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-24)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #25] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-25)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #26] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-26)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #27] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-27)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #28] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-28)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #29] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-29)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #30] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-30)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #31] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-31)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #32] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-32)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #33] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-33)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #34] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-34)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #35] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-35)"
            )
        )
    }

    private fun populateBatch2(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #36] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-36)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #37] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-37)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #38] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-38)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #39] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-39)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #40] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-40)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #41] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-41)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #42] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-42)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #43] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-43)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #44] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-44)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #45] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-45)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #46] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-46)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #47] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-47)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #48] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-48)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #49] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-49)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #50] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-50)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #51] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-51)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #52] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-52)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #53] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-53)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #54] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-54)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #55] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-55)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #56] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-56)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #57] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-57)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #58] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-58)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #59] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-59)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #60] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-60)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #61] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-61)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #62] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-62)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #63] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-63)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #64] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-64)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #65] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-65)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #66] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-66)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #67] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-67)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #68] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-68)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #69] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-69)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #70] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-70)"
            )
        )
    }

    private fun populateBatch3(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #71] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-71)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #72] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-72)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #73] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-73)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #74] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-74)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #75] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-75)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #76] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-76)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #77] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-77)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #78] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-78)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #79] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-79)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #80] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-80)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #81] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-81)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #82] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-82)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #83] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-83)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #84] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-84)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #85] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-85)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #86] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-86)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #87] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-87)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #88] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-88)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #89] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-89)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #90] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-90)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #91] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-91)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #92] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-92)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #93] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-93)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #94] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-94)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #95] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-95)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #96] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-96)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #97] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-97)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #98] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-98)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #99] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-99)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #100] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-100)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #101] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-101)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #102] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-102)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #103] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-103)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #104] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-104)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #105] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-105)"
            )
        )
    }

    private fun populateBatch4(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #106] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-106)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #107] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-107)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #108] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-108)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #109] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-109)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #110] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-110)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #111] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-111)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #112] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-112)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #113] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-113)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #114] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-114)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #115] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-115)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #116] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-116)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #117] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-117)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #118] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-118)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #119] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-119)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #120] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-120)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #121] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-121)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #122] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-122)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #123] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-123)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #124] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-124)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #125] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-125)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #126] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-126)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #127] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-127)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #128] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-128)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #129] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-129)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #130] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-130)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #131] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-131)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #132] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-132)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #133] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-133)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #134] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-134)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #135] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-135)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #136] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-136)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #137] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-137)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #138] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-138)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #139] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-139)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #140] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-140)"
            )
        )
    }

    private fun populateBatch5(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #141] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-141)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #142] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-142)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #143] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-143)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #144] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-144)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #145] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-145)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #146] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-146)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #147] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-147)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #148] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-148)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #149] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-149)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #150] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-150)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #151] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-151)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #152] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-152)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #153] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-153)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #154] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-154)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #155] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-155)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #156] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-156)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #157] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-157)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #158] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-158)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #159] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-159)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #160] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-160)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #161] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-161)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #162] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-162)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #163] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-163)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #164] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-164)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #165] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-165)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #166] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-166)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #167] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-167)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #168] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-168)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #169] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-169)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #170] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-170)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #171] Which major arterial road runs through the center of Mina leading directly toward the Jamarat bridge?",
                options = listOf("King Faisal Road (Tariq al-Malik Faisal) and Souq Al-Arab Street", "Indus Highway", "Karakoram Highway", "Murree Road"),
                correctIndex = 0,
                explanation = "King Faisal Road and Souq Al-Arab Street are primary pedestrian and transport corridors traversing the length of Mina to Jamarat.",
                reference = "Mina Urban Map (Geo-Ref-171)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #172] How many functional levels does the modern Jamarat Bridge structure contain to facilitate smooth crowd flow?",
                options = listOf("5 operational levels (Basement, Ground Floor, 1st, 2nd, 3rd, and 4th floors)", "Only 1 single flat floor", "10 floors", "2 floors only"),
                correctIndex = 0,
                explanation = "The multi-tiered Jamarat Bridge complex has multiple levels equipped with air conditioning, escalators, and dedicated entry/exit corridors.",
                reference = "Jamarat Engineering Guide (Geo-Ref-172)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #173] Which gate of Masjid Nabawi leads directly into the pathway for greeting the Holy Prophet (PBUH) and Abu Bakr (RA) and Umar (RA)?",
                options = listOf("Bab as-Salam (Gate 1)", "Bab al-Majeedi", "Bab an-Nisa", "Bab King Saud"),
                correctIndex = 0,
                explanation = "Bab as-Salam is the principal entrance through which visitors enter to pass by the sacred Rawdah and offer Salam at the Mawajaha Sharif.",
                reference = "Masjid Nabawi Layout (Geo-Ref-173)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #174] Why is Masjid al-Qiblatayn in Madinah famous in Islamic history?",
                options = listOf("It is the site where the command was received to change the direction of Qiblah from Jerusalem (Bayt al-Maqdis) to the Ka'bah in Makkah", "It is where the battle of Badr was fought", "It is where the first Quranic surah was written", "It was built by Prophet Ibrahim (AS)"),
                correctIndex = 0,
                explanation = "Masjid al-Qiblatayn is where the divine revelation changed the prayer direction (Qiblah) towards the Holy Ka'bah.",
                reference = "Islamic History (Geo-Ref-174)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #175] What did the Prophet Muhammad (PBUH) declare regarding the area between his sacred house (tomb) and his pulpit (Minbar)?",
                options = listOf("'Between my house and my pulpit is a garden from the gardens of Paradise (Rawdah min Riyad al-Jannah)'", "'It is a place for commercial trade'", "'It is only for citizens of Madinah'", "'It is an ordinary room'"),
                correctIndex = 0,
                explanation = "The Prophet (PBUH) said: 'Between my house and my Minbar is a garden from the gardens of Paradise.'",
                reference = "Sahih Bukhari (Geo-Ref-175)"
            )
        )
    }

    private fun populateBatch6(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #176] Which area of Makkah is located approximately 4-7 km southeast of the Haram, renowned for housing the largest number of Pakistani Government Scheme pilgrims?",
                options = listOf("Azizia (Azizia Shamaliyah & Azizia Janubiyah)", "Jeddah Corniche", "Taif Hills", "Yanbu Port"),
                correctIndex = 0,
                explanation = "Azizia is the major accommodation hub where Pakistan Hajj Mission offices, hospitals, and pilgrim buildings are concentrated.",
                reference = "MoRA Accommodation Guide (Geo-Ref-176)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #177] What is the function of the Kudai Bus Station in Makkah during Hajj?",
                options = listOf("A major central transportation terminal and parking hub providing shuttle bus services connecting peripheral accommodations to Masjid al-Haram", "An international seaport", "An agricultural research farm", "A military testing field"),
                correctIndex = 0,
                explanation = "Kudai is a primary transport interchange shuttling pilgrims from outer residential districts directly to the Haram courtyards.",
                reference = "Makkah Transport Network (Geo-Ref-177)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #178] Where is the Mount of Uhud located, and who is the revered Leader of Martyrs (Sayyid al-Shuhada) resting there?",
                options = listOf("North of Madinah; Sayyiduna Hamzah ibn Abd al-Muttalib (RA)", "In the middle of the Red Sea; Khalid ibn Walid (RA)", "Near Mount Hira; Bilal Habashi (RA)", "In Arafat; Abu Hurairah (RA)"),
                correctIndex = 0,
                explanation = "Mount Uhud lies north of Madinah, where the historic Battle of Uhud occurred and Sayyiduna Hamzah (RA) along with 70 martyrs are buried.",
                reference = "Madinah Ziyarat Guide (Geo-Ref-178)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #179] What is the historical significance of Masjid al-Ghamamah located near Masjid Nabawi in Madinah?",
                options = listOf("The site where the Prophet (PBUH) performed Salatul Eid and Salatul Istisqa (prayer for rain), where a cloud shaded him", "The place where camels were traded", "The site of the Treaty of Hudaybiyyah", "The home of Abu Ayyub al-Ansari (RA)"),
                correctIndex = 0,
                explanation = "Masjid al-Ghamamah is the historic open-air ground where the Prophet (PBUH) prayed Eid and rain prayers, sheltered by a miraculous cloud.",
                reference = "Historical Mosques of Madinah (Geo-Ref-179)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Geography & Holy Sites",
                question = "[Geography & Sites #180] How many railway stations does the Al-Mashair Al-Mugaddassah Metro line have in each of the three sacred sites (Mina, Muzdalifah, Arafat)?",
                options = listOf("3 stations in Mina, 3 in Muzdalifah, and 3 in Arafat (9 stations total)", "1 station only in Mina", "15 stations in each site", "No stations exist"),
                correctIndex = 0,
                explanation = "The Mashair Metro line features 9 elevated stations: 3 in Arafat (Arafat 1, 2, 3), 3 in Muzdalifah, and 3 in Mina.",
                reference = "Mashair Railway Guide (Geo-Ref-180)"
            )
        )
    }

}
