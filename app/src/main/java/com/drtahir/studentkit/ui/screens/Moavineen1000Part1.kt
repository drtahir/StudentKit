package com.drtahir.studentkit.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 1
 * Subject: Hajj Rules, Rituals & Arkan (30 100% Unique MCQs)
 * Covers Ihram prohibitions, Miqat boundaries, Tawaf, Sa'i, Wuquf-e-Arafat, Muzdalifah, Jamarat, Dam/Qurbani, and Fiqhi rulings.
 */
object Moavineen1000Part1 {

    fun getHajjRulesQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the designated Miqat boundary for Pakistani pilgrims arriving directly by air into Jeddah or Makkah from Pakistan?",
                options = listOf(
                    "Dhul Hulaifah (Abyar Ali)",
                    "Yalamlam (or passing parallel to Yalamlam / Qarn al-Manazil in-flight)",
                    "Juhfah",
                    "Dhat Irq"
                ),
                correctIndex = 1,
                explanation = "Pakistani pilgrims flying directly to Jeddah or Makkah pass over or parallel to Miqat Yalamlam (or Qarn al-Manazil) and must enter Ihram before or upon reaching this boundary.",
                reference = "MORA Moavineen-e-Hujjaj Training Manual & Fiqh-e-Hajj Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "If a Pakistani pilgrim travels first to Madinah Munawwarah, from where must they enter the state of Ihram when proceeding to Makkah for Umrah or Hajj?",
                options = listOf(
                    "Jeddah Hajj Terminal",
                    "Dhul Hulaifah (Abyar Ali / Masjid Shajarah)",
                    "Masjid-e-Ayesha (Tan'im)",
                    "Mina Boundary"
                ),
                correctIndex = 1,
                explanation = "Pilgrims leaving Madinah for Makkah must assume Ihram at Dhul Hulaifah (commonly known as Abyar Ali).",
                reference = "Fiqh-e-Hajj Standard Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "A male pilgrim in Ihram accidentally covers his head with a standard cloth blanket for a full 24-hour period due to illness. What Fiqhi expiation (Dam) is required according to Hanafi Fiqh?",
                options = listOf(
                    "No expiation is required",
                    "Sacrifice of one sheep/goat (Dam) in Makkah, OR feeding 6 needy persons (Sadaqah), OR fasting 3 days",
                    "Immediate cancellation of Hajj pilgrimage",
                    "Payment of a transportation fine to the bus company"
                ),
                correctIndex = 1,
                explanation = "Covering the head or face (for men) for a full day/night in Ihram due to a valid excuse requires a choice of expiation: slaughtering a sheep (Dam), feeding 6 poor persons, or fasting 3 days.",
                reference = "MORA Fiqh-e-Hajj Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What are the three essential Fard (mandatory pillars) of Hajj without any of which Hajj is rendered invalid?",
                options = listOf(
                    "Ihram with Niyyah, Wuquf-e-Arafat, and Tawaf-e-Ziyarah (Tawaf al-Ifadah)",
                    "Sa'i between Safa and Marwah, Rami of Jamarat, and Halq/Taqseer",
                    "Staying in Muzdalifah, Tawaf-e-Wida, and Qurbani",
                    "Visiting Madinah, drinking Zamzam, and touching the Black Stone"
                ),
                correctIndex = 0,
                explanation = "The core Fard pillars of Hajj are: 1. Ihram (with Niyyah), 2. Wuquf-e-Arafat (standing at Arafat), and 3. Tawaf-e-Ziyarah. Omitting any of these invalidates Hajj.",
                reference = "Manasik al-Hajj Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "During Wuquf-e-Arafat on 9th Dhu al-Hijjah, what is the exact permissible time window for fulfilling this supreme pillar of Hajj?",
                options = listOf(
                    "From Fajr prayer on 8th Dhu al-Hijjah to Maghrib on 8th Dhu al-Hijjah",
                    "From Zawal (solar noon) on 9th Dhu al-Hijjah until the dawn (Fajr) of 10th Dhu al-Hijjah",
                    "Only between Asr and Maghrib on 9th Dhu al-Hijjah",
                    "From midnight of 10th Dhu al-Hijjah until 12th Dhu al-Hijjah"
                ),
                correctIndex = 1,
                explanation = "The mandatory time for Wuquf-e-Arafat begins at Zawal (noon) on 9th Dhu al-Hijjah and extends until true dawn (Fajr) on 10th Dhu al-Hijjah.",
                reference = "MORA Hajj Ritual Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "When departing Arafat for Muzdalifah after sunset on 9th Dhu al-Hijjah, how should pilgrims perform Maghrib and Isha prayers according to Sunnah?",
                options = listOf(
                    "Perform Maghrib in Arafat before leaving the tent",
                    "Combine Maghrib and Isha prayers at Muzdalifah during Isha time (Jam' Ta'kheer)",
                    "Perform Maghrib on the bus while stuck in traffic",
                    "Skip Maghrib prayer entirely and perform only Isha at Mina"
                ),
                correctIndex = 1,
                explanation = "On the evening of 9th Dhu al-Hijjah, Maghrib is delayed and combined with Isha at Muzdalifah during Isha time with one Adhan and two Iqamahs.",
                reference = "MORA Moavineen Training Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "On the 10th of Dhu al-Hijjah (Yaum-un-Nahr), which specific Jamarah is stoned, and how many pebbles are thrown?",
                options = listOf(
                    "All three Jamarat with 7 pebbles each (21 total)",
                    "Only Jamarah al-Aqba (Big Jamarah / Badi Jamarah) with exactly 7 pebbles",
                    "Jamarah al-Ula and Jamarah al-Wusta with 14 pebbles",
                    "Only Jamarah al-Wusta with 5 pebbles"
                ),
                correctIndex = 1,
                explanation = "On 10th Dhu al-Hijjah, stoning is performed ONLY at Jamarah al-Aqba (the largest pillar) using 7 pebbles.",
                reference = "Fiqh-e-Hajj Standard Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "On the Days of Tashreeq (11th and 12th Dhu al-Hijjah), what is the correct sequence for performing Rami (stoning) at the Jamarat?",
                options = listOf(
                    "Jamarah al-Aqba -> Jamarah al-Wusta -> Jamarah al-Ula",
                    "Jamarah al-Ula (Small) -> Jamarah al-Wusta (Middle) -> Jamarah al-Aqba (Large), 7 pebbles each (21 total per day)",
                    "Any order preferred by the pilgrim",
                    "Stone only Jamarah al-Aqba on all three days"
                ),
                correctIndex = 1,
                explanation = "On 11th and 12th Dhu al-Hijjah, Rami must follow the sequence starting from Jamarah al-Ula (Small), then Jamarah al-Wusta (Middle), and lastly Jamarah al-Aqba (Large), throwing 7 pebbles at each.",
                reference = "MORA Hajj Ritual Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What distinguishes Hajj-e-Tamattu (performed by most Pakistani pilgrims) from Hajj-e-Ifrad?",
                options = listOf(
                    "Hajj-e-Tamattu combines Umrah and Hajj in the same Hajj season with exit from Ihram in between, requiring mandatory Dam-e-Shukr (Qurbani)",
                    "Hajj-e-Ifrad requires performing Umrah twice before 8th Dhu al-Hijjah",
                    "Hajj-e-Tamattu does not require Wuquf-e-Arafat",
                    "Hajj-e-Ifrad requires flying to Madinah first"
                ),
                correctIndex = 0,
                explanation = "In Hajj-e-Tamattu, the pilgrim performs Umrah during Hajj months, shaves/trims hair to exit Ihram, and re-enters Ihram for Hajj on 8th Dhu al-Hijjah. A sacrificial offering (Dam-e-Shukr) is mandatory.",
                reference = "MORA Hajj Policy & Fiqh Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "How many total laps (Shawt) constitute a complete Sa'i between Safa and Marwah, and where does the Sa'i start and finish?",
                options = listOf(
                    "14 laps starting at Marwah and ending at Safa",
                    "7 laps starting at Safa and ending at Marwah (Safa to Marwah is 1 lap, Marwah to Safa is 2nd lap)",
                    "7 round trips (14 total directional walks)",
                    "5 laps starting at the Black Stone"
                ),
                correctIndex = 1,
                explanation = "Sa'i consists of 7 laps starting at Mount Safa and concluding at Mount Marwah.",
                reference = "Manasik-e-Hajj Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "Who is obligated to perform Tawaf-e-Wida (Farewell Tawaf) before leaving Makkah to return home or travel to Madinah?",
                options = listOf(
                    "Only citizens residing permanently in Makkah",
                    "All non-resident (Afaqi) pilgrims, except menstruating women or women with post-postpartum bleeding",
                    "Only pilgrims who performed Hajj-e-Qiran",
                    "Only pilgrim group supervisors"
                ),
                correctIndex = 1,
                explanation = "Tawaf-e-Wida is Wajib for all Afaqi (out-of-station) pilgrims prior to departure from Makkah, but waived for women in state of Haidh or Nifas without penalty.",
                reference = "MORA Fiqhi Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "If an elderly pilgrim cannot walk to the Jamarat due to severe paralysis or unconsciousness, what is the Fiqhi ruling on appointing a proxy (Niyabat) for Rami?",
                options = listOf(
                    "Proxy stoning is strictly prohibited under all circumstances",
                    "Another pilgrim or Moavin can perform Rami on their behalf after completing their own 7 pebbles at that Jamarah",
                    "The invalid pilgrim must pay a cash penalty to Saudi police",
                    "The invalid pilgrim's Hajj is automatically cancelled"
                ),
                correctIndex = 1,
                explanation = "If a pilgrim is genuinely incapable due to severe illness or disability, another pilgrim can act as proxy after throwing their own pebbles first.",
                reference = "MORA Fiqh Rulings for Disabled Hujjaj"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the rule regarding Ramal (brisk walking with shoulders shaking) during Tawaf?",
                options = listOf(
                    "Performed in all 7 laps of every Tawaf",
                    "Sunnah for men only during the first 3 laps of Tawaf al-Qudum or Umrah Tawaf where Sa'i follows",
                    "Mandatory for female pilgrims during Tawaf-e-Ziyarah",
                    "Prohibited in modern times due to crowds"
                ),
                correctIndex = 1,
                explanation = "Ramal is Sunnah for men in the first 3 rounds of Tawaf accompanied by Sa'i (such as Umrah Tawaf or Tawaf al-Qudum).",
                reference = "Fiqh-e-Hajj Standard Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "If a pilgrim clips their fingernails or shaves hair before performing Qurbani on 10th Dhu al-Hijjah in Hajj-e-Tamattu, what is the consequence?",
                options = listOf(
                    "No consequence at all",
                    "Violation of Ihram sequence requiring a Dam (sacrificial offering) or Sadaqah depending on extent",
                    "Must restart Umrah from Jeddah airport",
                    "Flight ticket home is forfeited"
                ),
                correctIndex = 1,
                explanation = "Exiting Ihram restrictions (Halq/Taqseer) before Qurbani in Hajj-e-Tamattu violates Tartib (correct sequence) according to Hanafi Fiqh, incurring Dam/Sadaqah.",
                reference = "MORA Fiqh Guidance"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is Idtiba (exposing the right shoulder in Ihram)?",
                options = listOf(
                    "A mandatory state worn during all 5 days of Mina stay",
                    "Sunnah for male pilgrims only during Tawaf where Ramal is performed (passing upper Ihram sheet under right armpit)",
                    "A requirement for female pilgrims during Sa'i",
                    "A ritual performed inside Masjid-e-Nabawi"
                ),
                correctIndex = 1,
                explanation = "Idtiba is placing the middle of the upper Ihram sheet under the right armpit and draping both ends over the left shoulder, exposing the right shoulder during Tawaf.",
                reference = "MORA Moavineen Fiqh Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the significance of 8th Dhu al-Hijjah (Yaum-at-Tarwiyah) in the Hajj itinerary?",
                options = listOf(
                    "Pilgrims perform Wuquf at Arafat",
                    "Pilgrims assume Ihram for Hajj and proceed from Makkah to Mina before Zuhr prayer",
                    "Pilgrims perform Tawaf-e-Wida and depart Saudi Arabia",
                    "Pilgrims stone Jamarah al-Aqba"
                ),
                correctIndex = 1,
                explanation = "8th Dhu al-Hijjah marks the start of Hajj days when pilgrims proceed to Mina in Ihram to offer Zuhr, Asr, Maghrib, Isha, and Fajr prayers.",
                reference = "MORA Hajj Timeline Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "At what location do pilgrims perform Halq (shaving head) or Taqseer (trimming hair) to exit Ihram after 10th Dhu al-Hijjah stoning and Qurbani?",
                options = listOf(
                    "Must be done strictly inside Masjid al-Haram",
                    "Anywhere in Makkah, Mina, or Haram boundaries after Qurbani is confirmed",
                    "Only at Madinah Airport",
                    "Inside the airline aircraft"
                ),
                correctIndex = 1,
                explanation = "Halq or Taqseer is performed within Haram boundaries (Mina or Makkah) after completing Jamarat al-Aqba stoning and Qurbani.",
                reference = "Manasik-e-Hajj Standards"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the Fiqhi status of staying overnight at Muzdalifah (Mabit) on the night of 10th Dhu al-Hijjah?",
                options = listOf(
                    "Fard (Pillar)",
                    "Wajib (Compulsory; missing without valid excuse requires Dam)",
                    "Mustahabb (Optional)",
                    "Makruh (Disliked)"
                ),
                correctIndex = 1,
                explanation = "Staying at Muzdalifah and performing Wuquf-e-Muzdalifah after Fajr is Wajib. Leaving early without valid medical/crowd reasons requires Dam.",
                reference = "MORA Fiqh Rulings"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "Where is the Green Light zone (Meelabain al-Akhdarain) located during Sa'i, and what should male pilgrims do there?",
                options = listOf(
                    "On the Jamarat Bridge; throw pebbles faster",
                    "Between two green lights on the Sa'i gallery between Safa and Marwah; male pilgrims run lightly (Saby)",
                    "In Muzdalifah plain; collect pebbles",
                    "At Mina camp gate; show identity badge"
                ),
                correctIndex = 1,
                explanation = "Between the two green markers along the Sa'i path, it is Sunnah for men to jog lightly while women maintain normal pace.",
                reference = "MORA Hajj Rituals Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What happens if a pilgrim leaves Mina before sunset on 12th Dhu al-Hijjah after completing Jamarat stoning?",
                options = listOf(
                    "They must pay a heavy fine to Saudi immigration",
                    "Their departure is valid, and they are excused from stoning on 13th Dhu al-Hijjah",
                    "Their Hajj is rendered invalid",
                    "They must repeat Tawaf-e-Ziyarah"
                ),
                correctIndex = 1,
                explanation = "If a pilgrim departs Mina before sunset on 12th Dhu al-Hijjah, stoning on 13th Dhu al-Hijjah is waived without any penalty.",
                reference = "MORA Hajj Fiqh Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "If a pilgrim remains in Mina past sunset on 12th Dhu al-Hijjah, what is the requirement for 13th Dhu al-Hijjah?",
                options = listOf(
                    "They must stay overnight and stone all 3 Jamarat on 13th Dhu al-Hijjah before leaving Mina",
                    "They must sacrifice an extra camel",
                    "They must return to Arafat immediately",
                    "No requirement; they can sleep and leave without stoning"
                ),
                correctIndex = 0,
                explanation = "If the sun sets on 12th Dhu al-Hijjah while the pilgrim is still in Mina, staying overnight and performing Rami on 13th Dhu al-Hijjah becomes Wajib (or highly emphasized).",
                reference = "MORA Manasik Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "During Tawaf al-Qudum or Tawaf-e-Ziyarah, if a pilgrim touches or kisses the Black Stone (Hajar al-Aswad) in extreme crowds, what caution must Moavineen advise regarding fragrance on the stone?",
                options = listOf(
                    "Touching fragrance on the Kaaba while in Ihram may incur a penalty (Sadaqah/Dam) if scented oil transfers to hands/clothing",
                    "Fragrance on Kaaba automatically cancels Ihram",
                    "No caution needed as Kaaba perfume is exempt for everyone",
                    "Touching the stone is forbidden during Hajj"
                ),
                correctIndex = 0,
                explanation = "Since the Kaaba and Hajar al-Aswad are frequently perfumed, pilgrims in Ihram should be cautious because applying scent while in Ihram requires expiation.",
                reference = "MORA Fiqh Caution Notice"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the mandatory intention (Niyyah) statement recited when assuming Ihram for Umrah under Hajj-e-Tamattu?",
                options = listOf(
                    "'Labbayk Allahumma Hajjan'",
                    "'Allahumma innee ureedu al-'Umrata fa-yassirha lee wa taqabbalha minnee'",
                    "'Bismillahi Allahu Akbar' only",
                    "'Subhanallah wa Bihamdihi'"
                ),
                correctIndex = 1,
                explanation = "For Umrah, the Niyyah translates to: 'O Allah, I intend to perform Umrah, so make it easy for me and accept it from me.'",
                reference = "MORA Moavineen Training Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the ruling if a female pilgrim experiences menstrual bleeding upon arrival in Makkah before performing Umrah Tawaf?",
                options = listOf(
                    "She must perform Tawaf immediately despite the state",
                    "She must delay her Tawaf and Sa'i until she attains purity, remaining in Ihram without incurring any penalty",
                    "She must cancel her Hajj and fly back to Pakistan immediately",
                    "Her male relative performs Tawaf on her behalf while she waits in hotel"
                ),
                correctIndex = 1,
                explanation = "A female pilgrim delays Tawaf until clean. She stays in Ihram and performs all other non-masjid rituals (like Mina/Arafat) if Hajj days begin.",
                reference = "Fiqh-e-Hajj Guidance for Women"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the legal boundary of Makkah Haram (Haram Limits) within which Qurbani meat for Dam must be slaughtered?",
                options = listOf(
                    "Any slaughterhouse in Pakistan or Jeddah",
                    "Strictly within the defined geographic boundaries of the Makkah Haram precinct (e.g. Mina / Muaisem slaughterhouses)",
                    "Inside Madinah Munawwarah",
                    "At airport cargo bays"
                ),
                correctIndex = 1,
                explanation = "All sacrificial offerings for Dam, Qurbani, or Fidyah must be slaughtered within the geographical limits of the Makkah Haram.",
                reference = "MORA Qurbani & Fiqh Regulations"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "In Hanafi Fiqh, what is the penalty if a pilgrim misses Tawaf-e-Ziyarah during the prescribed days (10th-12th Dhu al-Hijjah) without a valid excuse?",
                options = listOf(
                    "A Dam (sacrificial slaughter) becomes Wajib due to delay beyond the prescribed time",
                    "Hajj becomes invalid completely",
                    "Pay 50 Riyals to the hotel reception",
                    "Fast for 30 consecutive days"
                ),
                correctIndex = 0,
                explanation = "Delaying Tawaf-e-Ziyarah beyond sunset on 12th Dhu al-Hijjah without valid excuse incurs a Dam for delay according to Hanafi Fiqh, though the Fard Tawaf must still be completed.",
                reference = "MORA Fiqh Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the recommended size of pebbles (Hasa) collected for Jamarat stoning?",
                options = listOf(
                    "Size of large bricks",
                    "Size of small date stones or chickpeas (approximately 1 to 1.5 cm)",
                    "Fine beach sand particles",
                    "Heavy iron balls"
                ),
                correctIndex = 1,
                explanation = "Pebbles for Rami should be approximately the size of small beans or date stones (chickpea size). Throwing large stones or shoes is strictly contrary to Sunnah.",
                reference = "MORA Training Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "From where are pebbles for Jamarat stoning traditionally collected according to Sunnah?",
                options = listOf(
                    "Strictly from Pakistan prior to flight departure",
                    "Muzdalifah (or anywhere within the Holy Sites like Mina)",
                    "Only inside Masjid al-Haram courtyard",
                    "Purchased from local retail shops"
                ),
                correctIndex = 1,
                explanation = "It is Sunnah to pick pebbles at Muzdalifah, though pebbles gathered anywhere within Mina or the sacred area are equally valid.",
                reference = "MORA Hajj Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the ruling regarding wearing stitched leather socks (Khuffain) or closed shoes for men in Ihram?",
                options = listOf(
                    "Fully permitted at all times",
                    "Prohibited for men if the footwear covers the upper ankle bone (K'ab); footwear must expose the ankle joint",
                    "Mandatory during Sa'i",
                    "Required during Arafat standing"
                ),
                correctIndex = 1,
                explanation = "Men in Ihram must wear footwear that leaves the ankles exposed. Fully enclosed shoes or boots covering the ankle bone are prohibited in Ihram.",
                reference = "Fiqh-e-Hajj Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "When a group of pilgrims completes Tawaf around the Holy Kaaba, where is it Sunnah to perform two Raka'at of Salat-ut-Tawaf?",
                options = listOf(
                    "Behind Maqam-e-Ibrahim (Station of Abraham) or anywhere within Masjid al-Haram if crowded",
                    "Outside Makkah city on the highway",
                    "At Mina camp tents",
                    "At Jeddah Hajj Terminal"
                ),
                correctIndex = 0,
                explanation = "It is Sunnah to pray 2 Raka'at Salat-ut-Tawaf behind Maqam-e-Ibrahim, or anywhere inside the Haram if the Mataf is congested.",
                reference = "MORA Fiqh-e-Hajj Manual"
            )
        )

        return list
    }
}
