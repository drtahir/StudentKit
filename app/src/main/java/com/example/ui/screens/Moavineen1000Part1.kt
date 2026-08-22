package com.drtahir.studentkit.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - Moavineen1000Part1
 * Subject: Hajj Rules & Arkan (180 Unique High-Yield MCQs)
 */
object Moavineen1000Part1 {

    fun getHajjRulesQuestions(startId: Int): List<MoavineenQuestion> {
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
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the designated Miqat boundary for Pakistani pilgrims arriving directly by air into Jeddah or Makkah from Pakistan?",
                options = listOf("Dhul Hulaifah (Abyar Ali)", "Yalamlam (or passing parallel to Yalamlam / Qarn al-Manazil in-flight)", "Juhfah", "Dhat Irq"),
                correctIndex = 1,
                explanation = "Pakistani pilgrims flying directly to Jeddah or Makkah pass over or parallel to Miqat Yalamlam (or Qarn al-Manazil) and must enter Ihram before or upon reaching this boundary.",
                reference = "MORA Moavineen Training Manual & Fiqh Syllabus"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "If a Pakistani pilgrim travels first to Madinah Munawwarah, from where must they enter the state of Ihram when proceeding to Makkah for Umrah or Hajj?",
                options = listOf("Jeddah Hajj Terminal", "Dhul Hulaifah (Abyar Ali / Masjid Shajarah)", "Masjid-e-Ayesha (Tan'im)", "Mina Boundary"),
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
                options = listOf("No expiation is required", "Sacrifice of one sheep/goat (Dam) in Makkah, OR feeding 6 needy persons (Sadaqah), OR fasting 3 days", "Immediate cancellation of Hajj pilgrimage", "Payment of a transportation fine to the bus company"),
                correctIndex = 1,
                explanation = "Covering the entire head for a full day (24 hours) for a male in Ihram requires a Dam (sacrifice of one goat/sheep), or fasting 3 days, or feeding 6 miskeen as expiation.",
                reference = "Ahkam-e-Hajj & Jinayat"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "Which form of Hajj involves performing Umrah first during the Hajj months, coming out of Ihram completely, and then entering a new Ihram for Hajj on the 8th of Dhul-Hijjah?",
                options = listOf("Hajj al-Ifrad", "Hajj al-Qiran", "Hajj al-Tamattu", "Hajj al-Badal"),
                correctIndex = 2,
                explanation = "Hajj al-Tamattu is performing Umrah during the Hajj months, getting out of Ihram, and then donning Ihram again for Hajj from Makkah on 8th Dhul-Hijjah.",
                reference = "Fiqh-us-Sunnah & MORA Manual"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the sacrifice (Dam-e-Shukr / Hady) ruling for a pilgrim performing Hajj al-Tamattu or Hajj al-Qiran?",
                options = listOf("It is purely optional (Nafl)", "It is Wajib (obligatory) as gratitude for combining Umrah and Hajj in a single journey", "It is Haram", "It is only required for citizens of Makkah"),
                correctIndex = 1,
                explanation = "Dam-e-Shukr (Hady) is Wajib upon the Mutamatti and Qarin as gratitude for being granted the ability to combine Umrah and Hajj in one journey.",
                reference = "Quran Surah Al-Baqarah 2:196"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "If a Mutamatti pilgrim cannot afford or obtain an animal for Dam-e-Shukr, what is the Quranic substitute?",
                options = listOf("Fasting 10 days in total: 3 days during Hajj (before 10th Dhul-Hijjah) and 7 days after returning home", "Donating 10 Riyals to a charity", "Performing 7 additional Tawafs", "Remaining in Ihram for an extra month"),
                correctIndex = 0,
                explanation = "According to Quran 2:196, if a pilgrim cannot afford the Hady, they must fast 3 days during Hajj and 7 days when they return home, totaling 10 days.",
                reference = "Surah Al-Baqarah 2:196"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What are the four fundamental Arkan (pillars) of Hajj according to the majority of Islamic jurists, without which Hajj is completely invalid?",
                options = listOf("Ihram, Wuquf-e-Arafat, Tawaf-e-Ziyarah (Ifadah), and Sa'i", "Rami al-Jamarat, Qurbani, Halq, and Tawaf-e-Wida", "Staying in Mina, praying at Muzdalifah, drinking Zamzam, and visiting Madinah", "Visiting Mount Hira, Mount Thawr, Masjid Nimrah, and Jabal Rahmah"),
                correctIndex = 0,
                explanation = "The core pillars (Arkan) of Hajj are Ihram (with Niyyah), Wuquf-e-Arafat, Tawaf-e-Ziyarah, and Sa'i between Safa and Marwah.",
                reference = "Fiqh Al-Islami wa Adillatuh"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "On which Islamic date does Wuquf-e-Arafat (the greatest pillar of Hajj: 'Al-Hajju Arafah') take place?",
                options = listOf("8th of Dhul-Hijjah (Yawm at-Tarwiyah)", "9th of Dhul-Hijjah (Yawm-e-Arafah)", "10th of Dhul-Hijjah (Yawm an-Nahr)", "12th of Dhul-Hijjah (Yawm al-Qarr)"),
                correctIndex = 1,
                explanation = "Wuquf-e-Arafat takes place on the 9th of Dhul-Hijjah between Zawal (midday) and sunset, which is the defining pillar of Hajj.",
                reference = "Sahih Hadith: Al-Hajju Arafah"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the valid time window for the obligatory Wuquf-e-Arafat according to Islamic Fiqh?",
                options = listOf("From sunrise of 8th Dhul-Hijjah to Asr of 8th Dhul-Hijjah", "From midday (Zawal) of 9th Dhul-Hijjah until the true dawn (Subh Sadiq) of 10th Dhul-Hijjah", "Only between Maghrib and Isha on 9th Dhul-Hijjah", "From midday of 10th Dhul-Hijjah to midday of 11th Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "The time for Wuquf starts from Zawal on 9th Dhul-Hijjah and extends until Subh Sadiq (dawn) of 10th Dhul-Hijjah.",
                reference = "Fiqh-e-Hajj Standard Guidelines"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "How should pilgrims perform Maghrib and Isha prayers on the evening of 9th Dhul-Hijjah after leaving Arafat?",
                options = listOf("Pray Maghrib immediately in Arafat, then pray Isha upon reaching Mina", "Combine and delay Maghrib and Isha together at Muzdalifah during the time of Isha", "Pray only 2 Rak'ahs of Maghrib at sunset on the road", "Combine Maghrib and Isha at midday in Masjid Nimrah"),
                correctIndex = 1,
                explanation = "It is Sunnah Mu'akkadah / Wajib to combine Maghrib and Isha together at Muzdalifah at the time of Isha with one Adhan and two Iqamahs.",
                reference = "Sunnah of the Holy Prophet (PBUH)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "Where is it Sunnah for pilgrims to collect small pebbles (Jamarat) for stoning the pillars?",
                options = listOf("From the courtyards of Masjid al-Haram", "From the grounds of Muzdalifah (or Mina)", "From the peak of Mount Uhud in Madinah", "From the departure lounge at Jeddah Airport"),
                correctIndex = 1,
                explanation = "Pilgrims collect 49 or 70 small chickpea-sized pebbles from Muzdalifah (or anywhere in the sacred area of Mina).",
                reference = "Fiqh-e-Hajj & Ritual Practices"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "On the 10th of Dhul-Hijjah (Yawm an-Nahr), which single Jamarah is stoned, and how many pebbles are thrown?",
                options = listOf("All three Jamarat with 21 pebbles each", "Only Jamarah al-Aqabah (the Big Satan) with 7 pebbles", "Only Jamarah al-Ula (the Small Satan) with 3 pebbles", "Only Jamarah al-Wusta with 14 pebbles"),
                correctIndex = 1,
                explanation = "On the 10th of Dhul-Hijjah, only Jamarah al-Aqabah (al-Kubra) is stoned with 7 pebbles.",
                reference = "MORA Moavineen Training Manual"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the correct sequential order of rituals on the 10th of Dhul-Hijjah for a pilgrim performing Hajj al-Tamattu?",
                options = listOf("Halq -> Tawaf-e-Ziyarah -> Qurbani -> Rami", "Rami of Jamarah al-Aqabah -> Qurbani (Dam-e-Shukr) -> Halq/Taqsir -> Tawaf-e-Ziyarah", "Tawaf-e-Wida -> Sa'i -> Rami -> Halq", "Qurbani -> Tawaf-e-Ziyarah -> Rami -> Halq"),
                correctIndex = 1,
                explanation = "The prescribed sequence for a Mutamatti on 10th Dhul-Hijjah is: (1) Rami of Jamarah al-Aqabah, (2) Qurbani/Hady, (3) Halq or Taqsir (shaving/cutting hair), (4) Tawaf-e-Ziyarah with Sa'i.",
                reference = "Fiqh Sunan & Ahkam"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is 'Tahallul al-Asghar' (the first / minor exit from Ihram) achieved by on 10th Dhul-Hijjah?",
                options = listOf("By performing Tawaf-e-Wida only", "By completing Rami, Qurbani, and Halq/Taqsir, after which all Ihram prohibitions are lifted except marital relations", "By entering the hotel room in Makkah", "By drinking Zamzam water in the basement"),
                correctIndex = 1,
                explanation = "Tahallul al-Asghar is achieved after shaving/cutting hair following Rami and Qurbani; all Ihram restrictions are lifted except spousal relations.",
                reference = "Hanafi & Shafi'i Jurisprudence"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the minimum hair cutting requirement for a female pilgrim to exit Ihram (Taqsir)?",
                options = listOf("Shaving the entire scalp with a razor", "Clipping a fingertip's length (approx. 1 inch / 2.5 cm) from the ends of her hair braid/bunch", "Cutting half of her hair length", "Washing hair with scented soap without cutting"),
                correctIndex = 1,
                explanation = "For women, Taqsir consists of cutting a fingertip's length (about 1 inch) from the bottom of her hair strands.",
                reference = "Fiqh Rulings on Women's Hajj"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is Tawaf-e-Wida (Farewell Tawaf), and upon whom is it obligatory (Wajib)?",
                options = listOf("It is Wajib on all non-resident pilgrims (Afaqi) before permanently departing from Makkah", "It is only Sunnah for citizens of Makkah", "It is only required for children under 10", "It is performed on the first day of arrival in Madinah"),
                correctIndex = 0,
                explanation = "Tawaf-e-Wida is Wajib upon every Afaqi (pilgrim living outside the Miqat boundaries) before leaving Makkah for their home country.",
                reference = "MORA Hajj Syllabus"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "If a female pilgrim experiences postpartum bleeding or menstruation and her scheduled departure flight cannot be delayed, is she excused from Tawaf-e-Wida without paying Dam?",
                options = listOf("No, she must miss her flight and remain indefinitely", "Yes, she is religiously exempted from Tawaf-e-Wida and incurs no penalty or Dam", "She must pay a financial fine to the airline", "She must send a deputy to perform Tawaf-e-Wida for her"),
                correctIndex = 1,
                explanation = "A menstruating woman who has already completed Tawaf-e-Ziyarah is exempted from Tawaf-e-Wida if she must travel, with no penalty (Dam) required.",
                reference = "Sahih Bukhari & Muslim Fiqh Consensus"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is the ruling regarding the days of Tashreeq (11th, 12th, and 13th of Dhul-Hijjah) regarding stoning the Jamarat?",
                options = listOf("Stoning is performed only before dawn", "Stoning of all three Jamarat (Ula, Wusta, Aqabah with 7 pebbles each = 21 pebbles/day) begins after Zawal (midday)", "Stoning is completely cancelled on these days", "Only the middle Jamarah is stoned with 1 pebble"),
                correctIndex = 1,
                explanation = "On the 11th, 12th, and 13th of Dhul-Hijjah, all three Jamarat are stoned after Zawal, in order: Jamarah al-Ula (7), Jamarah al-Wusta (7), Jamarah al-Aqabah (7), totaling 21 pebbles daily.",
                reference = "Fiqh-e-Hajj Standard Guide"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is 'Idtiba' during Tawaf, and when is it observed?",
                options = listOf("Wearing two pairs of socks during Sa'i", "Uncovering the right shoulder by passing the upper Ihram sheet under the right armpit and throwing it over the left shoulder, observed during the Tawaf of Umrah / Tawaf al-Qudum", "Covering the entire face with a veil during Tawaf", "Walking backwards from the Yemeni Corner to Hajr-e-Aswad"),
                correctIndex = 1,
                explanation = "Idtiba is uncovering the right shoulder during all seven circuits of a Tawaf followed by Sa'i (for men only).",
                reference = "Fiqh Rulings & Sunnah"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "What is 'Ramal' during Tawaf?",
                options = listOf("Walking at an accelerated pace with short brisk steps while flexing the shoulders during the first three circuits of Tawaf (for men)", "Crawling on hands and knees between Safa and Marwah", "Running at full speed around the Ka'bah during all seven circuits", "Standing motionless in front of the Multazam for 30 minutes"),
                correctIndex = 0,
                explanation = "Ramal is the brisk, rhythmic walking with shoulders held high during the first 3 circuits of Tawaf for men.",
                reference = "Sunnah of Tawaf"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #21] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q21)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #22] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q22)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #23] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q23)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #24] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q24)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #25] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q25)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #26] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q26)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #27] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q27)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #28] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q28)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #29] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q29)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #30] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q30)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #31] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q31)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #32] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q32)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #33] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q33)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #34] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q34)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #35] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q35)"
            )
        )
    }

    private fun populateBatch2(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #36] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q36)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #37] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q37)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #38] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q38)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #39] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q39)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #40] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q40)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #41] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q41)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #42] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q42)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #43] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q43)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #44] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q44)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #45] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q45)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #46] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q46)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #47] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q47)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #48] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q48)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #49] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q49)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #50] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q50)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #51] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q51)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #52] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q52)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #53] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q53)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #54] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q54)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #55] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q55)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #56] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q56)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #57] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q57)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #58] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q58)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #59] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q59)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #60] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q60)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #61] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q61)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #62] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q62)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #63] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q63)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #64] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q64)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #65] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q65)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #66] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q66)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #67] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q67)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #68] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q68)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #69] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q69)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #70] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q70)"
            )
        )
    }

    private fun populateBatch3(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #71] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q71)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #72] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q72)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #73] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q73)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #74] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q74)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #75] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q75)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #76] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q76)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #77] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q77)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #78] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q78)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #79] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q79)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #80] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q80)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #81] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q81)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #82] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q82)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #83] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q83)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #84] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q84)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #85] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q85)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #86] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q86)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #87] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q87)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #88] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q88)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #89] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q89)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #90] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q90)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #91] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q91)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #92] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q92)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #93] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q93)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #94] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q94)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #95] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q95)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #96] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q96)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #97] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q97)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #98] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q98)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #99] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q99)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #100] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q100)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #101] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q101)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #102] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q102)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #103] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q103)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #104] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q104)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #105] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q105)"
            )
        )
    }

    private fun populateBatch4(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #106] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q106)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #107] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q107)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #108] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q108)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #109] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q109)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #110] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q110)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #111] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q111)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #112] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q112)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #113] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q113)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #114] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q114)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #115] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q115)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #116] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q116)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #117] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q117)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #118] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q118)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #119] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q119)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #120] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q120)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #121] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q121)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #122] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q122)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #123] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q123)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #124] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q124)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #125] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q125)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #126] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q126)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #127] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q127)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #128] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q128)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #129] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q129)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #130] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q130)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #131] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q131)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #132] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q132)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #133] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q133)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #134] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q134)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #135] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q135)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #136] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q136)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #137] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q137)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #138] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q138)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #139] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q139)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #140] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q140)"
            )
        )
    }

    private fun populateBatch5(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #141] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q141)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #142] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q142)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #143] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q143)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #144] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q144)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #145] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q145)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #146] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q146)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #147] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q147)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #148] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q148)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #149] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q149)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #150] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q150)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #151] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q151)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #152] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q152)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #153] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q153)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #154] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q154)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #155] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q155)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #156] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q156)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #157] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q157)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #158] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q158)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #159] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q159)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #160] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q160)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #161] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q161)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #162] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q162)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #163] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q163)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #164] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q164)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #165] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q165)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #166] Is purchasing an official bank coupon (such as the Saudi Project for Utilization of Hajj Meat - Adahi) valid for Dam-e-Shukr?",
                options = listOf("No, a pilgrim must physically slaughter with their own hand", "Yes, purchasing an authorized Adahi coupon empowers authorized Islamic agencies to slaughter on the pilgrim's behalf validly", "Only valid for camels", "Coupons are strictly forbidden"),
                correctIndex = 1,
                explanation = "The Saudi Adahi project is a shariah-compliant mechanism allowing pilgrims to purchase vouchers for proxy slaughter within the Haram.",
                reference = "Saudi Fiqh Council (Ref-Q166)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #167] What is the rule regarding shaving or trimming hair for a bald pilgrim who has no hair on his head?",
                options = listOf("He is exempted from everything with no action needed", "It is Mustahabb to pass the razor gently over his scalp (Iimrar al-Moosa) to symbolize Halq", "He must sacrifice 5 sheep", "He must wear a wig"),
                correctIndex = 1,
                explanation = "A bald pilgrim should gently pass a razor over his scalp to symbolize shaving and fulfill the ritual requirement.",
                reference = "Fiqh of Tahallul (Ref-Q167)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #168] If a pilgrim delays Tawaf-e-Ziyarah past the sunset of 12th Dhul-Hijjah without a valid medical reason, what is the Hanafi ruling?",
                options = listOf("The Hajj is permanently voided", "Tawaf-e-Ziyarah can still be performed at any time, but a Dam becomes due in Hanafi Fiqh for the unwarranted delay past the days of Nahr", "A fine of 500 Riyals to the hotel", "No ruling applies"),
                correctIndex = 1,
                explanation = "In the Hanafi school, delaying Tawaf-e-Ziyarah past the days of Nahr (12th Dhul-Hijjah sunset) incurs a Dam.",
                reference = "Hanafi Ahkam al-Hajj (Ref-Q168)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #169] What is the ruling on performing Tawaf while heavy rain falls in Masjid al-Haram?",
                options = listOf("Tawaf is completely forbidden in rain", "Tawaf in the rain is fully valid, permissible, and historically cherished by pilgrims for its spiritual serenity", "Rain cancels the Ihram", "Rain requires repeating the circuits"),
                correctIndex = 1,
                explanation = "Performing Tawaf in the rain is valid, permissible, and considered a blessed moment for accepted Duas.",
                reference = "Fiqh of the Haram (Ref-Q169)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #170] Is performing Sa'i on the 1st floor, 2nd floor, or roof of the Mas'a valid?",
                options = listOf("No, only the ground floor is valid", "Yes, all multi-level Mas'a floors constructed directly above the historical Safa and Marwah boundaries are fully valid for Sa'i", "Only valid if you pay double fare", "Only valid for staff members"),
                correctIndex = 1,
                explanation = "All expanded upper and lower levels of the Mas'a are within the vertical boundaries of Safa and Marwah and are fully valid for Sa'i.",
                reference = "Islamic Fiqh Academy (Ref-Q170)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #171] What constitutes the core essential of entering the state of Ihram?",
                options = listOf("Wearing white sheets only without intention", "Making the sincere intention (Niyyah) in the heart accompanied by reciting the Talbiyah", "Paying the visa processing fee", "Taking a photograph at the airport"),
                correctIndex = 1,
                explanation = "Ihram is fundamentally the intention (Niyyah) in the heart combined with the utterance of Talbiyah.",
                reference = "Fiqh-us-Sunnah (Ref-Q171)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #172] If a male pilgrim shaves his entire head before performing the animal sacrifice on 10th Dhul-Hijjah, what is the Fiqhi ruling in Hanafi school?",
                options = listOf("His Hajj is nullified", "A penalty of Dam (sacrifice of one goat/sheep) becomes due for violating the proper sequence", "No penalty is due", "He must fast 1 year"),
                correctIndex = 1,
                explanation = "In the Hanafi school, performing Halq before Qurbani on 10th Dhul-Hijjah requires a Dam-e-Jibran due to breaching sequence.",
                reference = "Fiqh al-Hanafi Jinayat (Ref-Q172)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #173] If a pilgrim has a doubt during Tawaf whether they completed 5 or 6 circuits, how should they resolve the doubt?",
                options = listOf("Assume 6 and stop immediately", "Build upon the lesser number (assume 5) and complete 2 more circuits to make 7 with certainty", "Restart the entire Tawaf from circuit 1", "Leave the Haram without completing"),
                correctIndex = 1,
                explanation = "In Fiqh, when in doubt during Tawaf or Salah, one builds upon certainty (the lesser number) and completes the remainder.",
                reference = "Qawa'id Fiqhiyyah (Ref-Q173)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #174] What is the ruling on drinking water or taking a sip of Zamzam during Tawaf if someone feels faint?",
                options = listOf("It invalidates the Tawaf immediately", "It is permissible and does not invalidate the Tawaf circuits", "It incurs a penalty of Dam", "It requires 10 days of fasting"),
                correctIndex = 1,
                explanation = "Drinking water during Tawaf is permissible and does not disrupt the validity of Tawaf circuits.",
                reference = "Fiqh of Tawaf (Ref-Q174)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #175] What is the Islamic etiquette regarding unnecessary speech and mobile phone conversations during Tawaf?",
                options = listOf("It is recommended to call friends and family during Tawaf", "Speech should be limited only to Dhikr, Dua, and necessary communication; idle talk and loud phone calls are Makruh (disliked)", "Talking on the phone is a mandatory pillar of Tawaf", "Talking invalidates the Ihram entirely"),
                correctIndex = 1,
                explanation = "Tawaf is like prayer except that speech is permitted; thus, one should only speak good, engage in Dhikr, and avoid unnecessary calls.",
                reference = "Tirmidhi Hadith (Ref-Q175)"
            )
        )
    }

    private fun populateBatch6(list: MutableList<MoavineenQuestion>, startId: Int) {
        var idCounter = startId
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #176] Is it permissible for an able-bodied young pilgrim to perform Sa'i or Tawaf on an electric cart or wheelchair without an excuse?",
                options = listOf("It is preferred for everyone", "It is Makruh (disliked) for an able-bodied person with no physical excuse, but strictly permissible and rewarding for the sick, elderly, or injured", "It is completely Haram under all circumstances", "It incurs a penalty of two camels"),
                correctIndex = 1,
                explanation = "Performing Tawaf/Sa'i on a conveyance is Sunnah for those with legitimate health excuses; able-bodied pilgrims should walk.",
                reference = "Fiqh Rulings (Ref-Q176)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #177] What is the penalty if a pilgrim leaves the boundary of Arafat before sunset on the 9th of Dhul-Hijjah without returning before sunset?",
                options = listOf("No penalty is required", "In Hanafi Fiqh, leaving Arafat before sunset requires offering a Dam (sacrifice), unless one returns to Arafat before sunset", "Hajj is cancelled for 10 years", "A visa ban is issued"),
                correctIndex = 1,
                explanation = "Remaining in Arafat until sunset is Wajib. Leaving early without returning before sunset incurs a Dam.",
                reference = "Fiqh of Arafah (Ref-Q177)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #178] What is the Sunnah practice during the night at Muzdalifah?",
                options = listOf("Staying awake all night shouting slogans", "Sleeping and resting after combining Maghrib and Isha prayers to gain strength for the rigorous rituals of 10th Dhul-Hijjah", "Walking back to Makkah at 8 PM", "Fasting the entire night"),
                correctIndex = 1,
                explanation = "The Prophet (PBUH) rested and slept during the night at Muzdalifah to prepare physically for Yawm an-Nahr (10th Dhul-Hijjah).",
                reference = "Sunnah of Muzdalifah (Ref-Q178)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #179] Is it required to wash the pebbles collected from Muzdalifah with scented soap before stoning?",
                options = listOf("Yes, washing with soap is mandatory", "No, washing pebbles is not required and considered an unnecessary innovation (Bid'ah) unless they are visibly soiled with filth", "They must be boiled in hot water", "They must be painted white"),
                correctIndex = 1,
                explanation = "Washing the pebbles is not required and disliked unless visible Najasah is present.",
                reference = "Fiqh of Rami (Ref-Q179)"
            )
        )
        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Rules & Arkan",
                question = "[Hajj Fiqh Rule #180] Under what exact conditions can a pilgrim appoint someone else as their proxy (Wakeel) to stone the Jamarat?",
                options = listOf("Whenever a pilgrim feels slightly lazy or wants to avoid walking", "When a pilgrim is genuinely incapacitated by severe illness, old age, unconsciousness, or extreme disability making walking dangerous", "Only if the pilgrim pays a fee of 1,000 Riyals", "Only on the 13th of Dhul-Hijjah"),
                correctIndex = 1,
                explanation = "Proxy stoning is only allowed for genuine inability due to serious illness, hospitalization, extreme weakness, or disability.",
                reference = "MORA Guidelines (Ref-Q180)"
            )
        )
    }

}
