package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 6
 * Subject: General Knowledge, Pakistan Hajj Policy & Tech Portal (30 100% Unique MCQs)
 * Covers Pakistan Hajj Policy, MORA structure, Nusuk App, Pak Hajj App, Saudi Hajj & Umrah Ministry regulations, emergency phone numbers, and selection criteria.
 */
object Moavineen1000Part6 {

    fun getHajjPolicyTechQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "Which Federal Ministry of the Government of Pakistan is responsible for formulating the national Hajj Policy and deploying Moavineen-e-Hujjaj?",
                options = listOf(
                    "Ministry of Foreign Affairs",
                    "Ministry of Religious Affairs and Interfaith Harmony (MORA)",
                    "Ministry of Interior",
                    "Ministry of Overseas Pakistanis"
                ),
                correctIndex = 1,
                explanation = "MORA is the federal ministry mandated with Hajj policy formulation, pilgrim quota management, and selection/deployment of welfare staff.",
                reference = "Pakistan Hajj Policy Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "Who heads the Pakistan Hajj Mission in Saudi Arabia and exercises overall administrative and operational command over all field operations in Makkah, Madinah, and Jeddah?",
                options = listOf(
                    "Director General (DG) Hajj, Pakistan Hajj Mission, Jeddah",
                    "Federal Minister of Commerce",
                    "Chairman Higher Education Commission",
                    "Governor State Bank of Pakistan"
                ),
                correctIndex = 0,
                explanation = "The Director General (DG) Hajj in Jeddah is the executive head of the Pakistan Hajj Mission in the Kingdom of Saudi Arabia.",
                reference = "MORA Organizational Administrative Structure"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "Under the annual Pakistan Hajj Policy, how is the total national pilgrim quota generally allocated between the Government Hajj Scheme and Private Hajj Group Organizers (HGOs)?",
                options = listOf(
                    "100% Government Scheme, 0% Private Scheme",
                    "Typically 50% Government Hajj Scheme and 50% Private HGO Scheme (or as determined annually by the Federal Cabinet)",
                    "10% Government Scheme, 90% Private Scheme",
                    "Random lottery without quota ratios"
                ),
                correctIndex = 1,
                explanation = "The national Hajj quota approved by Saudi Arabia is traditionally split 50:50 between the Government Hajj Scheme and registered Private HGOs.",
                reference = "Pakistan Hajj Policy Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the official Saudi digital smartphone application required for issuing permits to visit Riaz-ul-Jannah in Masjid an-Nabawi and scheduling Umrah?",
                options = listOf(
                    "Nusuk App (formerly Eatmarna)",
                    "Pak Hajj App",
                    "Whatsapp Business",
                    "SAPTCO Bus App"
                ),
                correctIndex = 0,
                explanation = "The Nusuk platform (under the Saudi Ministry of Hajj and Umrah) is the official digital portal for issuing Riaz-ul-Jannah permits and scheduling Umrah.",
                reference = "Saudi Ministry of Hajj Digital Mandate"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What key digital features are provided in MORA's official 'Pak Hajj' mobile application for pilgrims and Moavineen field staff?",
                options = listOf(
                    "Commercial online shopping and hotel booking deals",
                    "Building location GPS tracking, complaint registration, lost luggage tracking, group details, and emergency SOS button",
                    "Flight simulator games",
                    "Foreign currency trading platform"
                ),
                correctIndex = 1,
                explanation = "The Pak Hajj App integrates GPS accommodation maps, complaint lodging, real-time luggage tracking, group details, and a direct emergency SOS link to MORA HQ.",
                reference = "Pak Hajj Mobile Application User Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "Who are eligible to apply for deployment as Moavineen-e-Hujjaj under the Ministry of Religious Affairs selection rules?",
                options = listOf(
                    "Private commercial contractors and businessmen",
                    "Regular employees of Federal/Provincial Government departments, Armed Forces, Police, and Civil Armed Forces selected through official nomination and NTS screening",
                    "Foreign tourists visiting Pakistan",
                    "Unemployed high school students"
                ),
                correctIndex = 1,
                explanation = "Moavineen are selected strictly from public sector regular government employees, armed forces, police, and civil security forces through official channels and NTS exams.",
                reference = "MORA Moavineen Recruitment Rules"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the official currency of the Kingdom of Saudi Arabia (KSA) and its fixed peg against the US Dollar?",
                options = listOf(
                    "Saudi Dinar (SAD); pegged at 1 USD = 1.00 SAD",
                    "Saudi Riyal (SAR); pegged at approximately 1 USD = 3.75 SAR",
                    "Saudi Dirham (AED); pegged at 1 USD = 5.00 AED",
                    "Saudi Rupee (PKR); floating exchange rate"
                ),
                correctIndex = 1,
                explanation = "The official currency is the Saudi Riyal (SAR), legally pegged to the US Dollar at 3.75 SAR per 1 USD.",
                reference = "Saudi Central Bank (SAMA) Currency Standards"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What are the official emergency telephone numbers in Saudi Arabia for Unified Emergency (Makkah), Police, Red Crescent Ambulance, and Civil Defense?",
                options = listOf(
                    "Unified Emergency (Makkah): 911, Police: 999, Red Crescent Ambulance: 997, Civil Defense: 998",
                    "Police: 15, Ambulance: 1122, Civil Defense: 16",
                    "All services: 000",
                    "Police: 100, Ambulance: 200, Civil Defense: 300"
                ),
                correctIndex = 0,
                explanation = "Emergency contacts in KSA: 911 (Unified Emergency Makkah/Riyadh), 999 (Police), 997 (Saudi Red Crescent), 998 (Civil Defense), 937 (Health Ministry).",
                reference = "Saudi Public Safety Directory"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "Which Saudi commercial establishment manages accommodation, tents in Mina/Arafat, and transport logistics for South Asian (Pakistani) pilgrims?",
                options = listOf(
                    "Company for Pilgrims of South Asian Countries (formerly Tawafa Establishment / Mutawwif Office)",
                    "Saudi Aramco Oil Company",
                    "SAPTCO Highway Corporation",
                    "Riyadh Commercial Chamber"
                ),
                correctIndex = 0,
                explanation = "The Company for Pilgrims of South Asian Countries (South Asian Mutawwif Company) handles housing, Maktab tent allocations, and Mashair services for Pakistani Hujjaj.",
                reference = "Saudi Ministry of Hajj Establishment Directory"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What mandatory pre-departure medical certifications must every Pakistani pilgrim present before obtaining flight clearance at Haji Camps?",
                options = listOf(
                    "Only a blood group card",
                    "Valid International Passport, Hajj Visa, MORA Health Fitness Certificate, and mandatory vaccination cards (Meningococcal Meningitis, Quadrivalent, Influenza, Polio)",
                    "No medical certificate is required",
                    "A private dental X-ray report"
                ),
                correctIndex = 1,
                explanation = "Pilgrims must possess valid passports, visas, fitness certificates, and compulsory immunizations (Meningitis, Polio, Seasonal Flu) verified at Haji Camps.",
                reference = "MORA Pre-Departure Health Regulations"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the function of provincial Haji Camps operated by MORA across major cities in Pakistan (e.g. Islamabad, Karachi, Lahore, Peshawar, Quetta, Multan)?",
                options = listOf(
                    "To sell commercial airline tickets to the general public",
                    "To provide pre-departure briefing, medical vaccination, passport/visa distribution, currency exchange guidance, flight boarding clearance, and luggage tagging for intending pilgrims",
                    "To conduct university entry examinations",
                    "To process permanent immigration visas"
                ),
                correctIndex = 1,
                explanation = "Haji Camps serve as centralized pre-departure staging hubs providing vaccination, documentation, training, and flight processing for pilgrims.",
                reference = "MORA Haji Camp Operations Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What biometric verification system is required by Saudi Arabia before Hajj visas are stamped for Pakistani pilgrims?",
                options = listOf(
                    "Saudi Visa Bio Smartphone Application (fingerprint and facial recognition scan)",
                    "Manual ink thumbprint on paper",
                    "Retina scan at local post offices",
                    "No biometric verification is needed"
                ),
                correctIndex = 0,
                explanation = "The Saudi Ministry of Foreign Affairs mandates enrollment via the 'Saudi Visa Bio' mobile application for biometrics (facial and fingerprint recognition) prior to visa issuance.",
                reference = "Saudi Visa Biometric Protocol"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the 'Hardship Scheme' or 'Sponsorship Scheme' introduced in Pakistan's Hajj Policy?",
                options = listOf(
                    "A scheme allowing overseas Pakistanis or foreign remittance sponsors to pay Hajj dues in foreign currency (US Dollars) with exemptions from the general public lottery ballot",
                    "A scheme for mountain climbers",
                    "A penalty scheme for delayed flights",
                    "A free travel pass for tourists"
                ),
                correctIndex = 0,
                explanation = "The Sponsorship Scheme reserves a designated quota for applicants depositing foreign exchange (USD) remitted from abroad, granting direct allocation without balloting.",
                reference = "Pakistan Hajj Policy Remittance Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the 'Tawakkalna' app used for in Saudi Arabia?",
                options = listOf(
                    "The official Saudi digital identity and health status platform used for entry verification into public facilities and holy sites",
                    "A food delivery service app in Mina",
                    "An online gaming app",
                    "A airline booking portal"
                ),
                correctIndex = 0,
                explanation = "Tawakkalna is the official Saudi digital ID and health verification portal managed by the Saudi Data and AI Authority (SDAIA).",
                reference = "Saudi Digital Governance Profile"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "How are complaints lodged by pilgrims on the 'Pak Hajj' app monitored and resolved by Moavineen field staff?",
                options = listOf(
                    "Complaints are deleted automatically after 24 hours",
                    "Complaints auto-generate a unique ticket number, route via GPS to the nearest Sector Supervisor/Moavin, require on-site field verification, and close only upon pilgrim satisfaction confirmation",
                    "Complaints are printed on paper and stored in archives",
                    "Complaints require payment of a filing fee by the pilgrim"
                ),
                correctIndex = 1,
                explanation = "The Pak Hajj portal uses real-time ticket tracking, routing grievances to sector Moavineen who resolve issues on-site and obtain digital pilgrim sign-off.",
                reference = "MORA Citizen Grievance Portal SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "Under MORA rules, what age limit and physical fitness criteria apply to applicants seeking Moavineen-e-Hujjaj selection?",
                options = listOf(
                    "Any age up to 80 years without health checks",
                    "Maximum age limits (typically 25 to 45/50 years), mandatory physical fitness screening certificate from authorized government medical boards, and passing NTS written tests",
                    "Exclusively retired individuals over 65 years",
                    "No age limit or medical check"
                ),
                correctIndex = 1,
                explanation = "Due to the demanding physical nature of Hajj field duties, MORA enforces strict age caps, NTS competitive exams, and rigorous medical fitness certification.",
                reference = "MORA Selection Criteria Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the toll-free helpline number operated by MORA in Makkah for Pakistani pilgrims seeking emergency guidance?",
                options = listOf(
                    "MORA Makkah Main Control Toll-Free Helpline (e.g. 800-4300000 / Pak Hajj Call Center)",
                    "1122",
                    "15",
                    "911"
                ),
                correctIndex = 0,
                explanation = "MORA operates dedicated 24/7 toll-free call centers in Makkah and Madinah to assist Pakistani pilgrims in Urdu and regional languages.",
                reference = "MORA Communication & Call Center Directory"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "What maximum baggage weight allowance is generally permitted per pilgrim on Hajj charter flights returning to Pakistan?",
                options = listOf(
                    "100 kg total baggage",
                    "Standard airline allowance (typically two bags of 23 kg each = 46 kg total checked baggage, plus 7 kg hand carry, plus 5 liters Zamzam bottle)",
                    "No checked baggage permitted",
                    "10 kg total"
                ),
                correctIndex = 1,
                explanation = "Hajj charter flight baggage policies traditionally permit 30-46 kg checked baggage, 7 kg hand carry, and 1 officially sealed 5-liter Zamzam bottle.",
                reference = "MORA Flight Logistics Policy"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "How is Zamzam water distributed to Government Scheme Pakistani pilgrims returning home from Saudi Arabia?",
                options = listOf(
                    "Pilgrims must carry unsealed buckets of water on their flight seats",
                    "Every pilgrim receives one officially sealed 5-liter bottle of Zamzam provided by the Saudi Kudai factory upon arrival at Pakistani airports",
                    "Zamzam distribution is strictly forbidden",
                    "Zamzam water is mailed through commercial postal services"
                ),
                correctIndex = 1,
                explanation = "To prevent airplane leaks, 5-liter factory-sealed Zamzam cans are transported in cargo and distributed directly to returning pilgrims at Pakistani debarkation airports.",
                reference = "MORA Zamzam Distribution SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the function of the 'Monitoring & Inspection Cell' within the Ministry of Religious Affairs during Hajj operations?",
                options = listOf(
                    "To audit financial records of private banks",
                    "To conduct surprise field inspections of accommodation buildings, food quality, transport fleets, and Moavineen performance in Saudi Arabia to ensure compliance with Hajj Policy",
                    "To organize tourist excursions in Jeddah",
                    "To recruit foreign citizens for government jobs"
                ),
                correctIndex = 1,
                explanation = "The Monitoring Cell conducts independent inspections of hotel standards, caterers, transport, and staff discipline, submitting daily compliance reports to Secretary MORA.",
                reference = "MORA Quality Assurance & Inspection Policy"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the official Saudi Ministry of Health emergency consultation call center telephone number accessible 24/7?",
                options = listOf(
                    "937",
                    "911",
                    "999",
                    "998"
                ),
                correctIndex = 0,
                explanation = "'937' is the Saudi Ministry of Health 24/7 telephone hotline providing medical advice, locating hospitals, and reporting health emergencies.",
                reference = "Saudi Ministry of Health Emergency Directory"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "What color is the official identity lanyard / locket issued by MORA to all Pakistani Government Scheme pilgrims?",
                options = listOf(
                    "Bright Green lanyard displaying the Pakistan Flag, pilgrim photo, QR barcode, registration number, and emergency contact numbers",
                    "Plain black string without text",
                    "Yellow ribbon without card",
                    "Red badge displaying commercial advertisements"
                ),
                correctIndex = 0,
                explanation = "The official green MORA locket contains high-visibility credentials, QR barcodes, passport numbers, building IDs, and emergency phone channels.",
                reference = "MORA Pilgrim Credentials Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "What authority enforces regulations regarding Private Hajj Group Organizers (HGOs) and penalizes non-compliant operators?",
                options = listOf(
                    "MORA HGO Enrollment & Monitoring Wing in coordination with Saudi Ministry of Hajj",
                    "Local traffic police in Islamabad",
                    "Commercial banks",
                    "Private travel agency associations"
                ),
                correctIndex = 0,
                explanation = "MORA's HGO Wing monitors private operators, enforces service level agreements (SLAs), handles pilgrim complaints, and forfeits performance guarantees of defaulting HGOs.",
                reference = "MORA Private HGO Service Regulations"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What digital dashboard does the Director Moavineen use at Makkah Mission Control to track field staff deployment in real time?",
                options = listOf(
                    "MORA Moavineen Field Duty Monitoring System (GIS map dashboard with active staff locations, shift check-ins, and task assignments)",
                    "Standard social media messaging app",
                    "Manual paper whiteboard only",
                    "Commercial stock exchange ticker"
                ),
                correctIndex = 0,
                explanation = "Director Moavineen utilizes MORA's GIS-integrated duty monitoring portal tracking staff check-ins, sector strength, and response times across Holy Sites.",
                reference = "MORA Tech Portal Specifications"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "How are lost passports recovered and replaced for Pakistani pilgrims while in Saudi Arabia?",
                options = listOf(
                    "Pilgrims are stranded permanently in Saudi Arabia",
                    "The Pakistan Hajj Mission Welfare Cell collaborates with the Consulate General of Pakistan in Jeddah to issue Emergency Travel Documents (ETD) / duplicate passports",
                    "Pilgrims must purchase a foreign passport from local vendors",
                    "Passports cannot be replaced under any circumstance"
                ),
                correctIndex = 1,
                explanation = "Lost passport cases are processed through the Hajj Mission Welfare Cell and Pakistani Consulate in Jeddah to issue Emergency Travel Documents (ETD) for return travel.",
                reference = "MORA Consular Services SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "What document governs the official agreement between MORA and Saudi bus transport syndicates (Naqaba) for pilgrim transportation?",
                options = listOf(
                    "Saudi General Cars Syndicate (Naqaba al-Sayyarat) Master Transport Contract",
                    "Private taxi lease receipt",
                    "Airline ticket voucher",
                    "Local city bus ticket"
                ),
                correctIndex = 0,
                explanation = "Inter-city and Mashair transport is contracted centrally through the General Cars Syndicate (Naqaba al-Sayyarat) under Saudi Ministry of Hajj supervision.",
                reference = "Saudi Naqaba Transport Contract Syllabus"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What is the role of the 'Lost & Found Currency & Valuables Vault' operated at the Pakistan Hajj Mission HQ in Makkah?",
                options = listOf(
                    "To store commercial merchandise for trade",
                    "To securely store recovered cash, jewelry, and passports in locked safes under dual audit supervision until claimed by rightful owners or deposited in national treasury",
                    "To exchange foreign currencies for profit",
                    "To store frozen food items"
                ),
                correctIndex = 1,
                explanation = "The Mission HQ Vault provides audited, dual-custody safe storage for recovered high-value assets and cash returned to pilgrims.",
                reference = "MORA Valuables Custody SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Hajj Policy & Tech",
                question = "When does the Hajj flight operation typically commence from Pakistan to Saudi Arabia each year?",
                options = listOf(
                    "On 1st Dhu al-Hijjah",
                    "Approximately 1 month prior to Hajj (starting 1st Dhu al-Qadah)",
                    "In the month of Ramadan",
                    "On the day of Eid-ul-Adha"
                ),
                correctIndex = 1,
                explanation = "Hajj flight operations begin around 1st Dhu al-Qadah (about 4 weeks before core Hajj rituals) to transport thousands of pilgrims systematically.",
                reference = "MORA Flight Operation Calendar"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Hajj Policy & Tech",
                question = "What operational debriefing report must the Director Moavineen compile upon completion of the Hajj Mission deployment?",
                options = listOf(
                    "A personal travel diary",
                    "Comprehensive Post-Hajj Performance & Recommendations Report detailing operational statistics, lost/reunited data, transport performance, staff evaluations, and policy suggestions for next year",
                    "Commercial financial audit of Saudi businesses",
                    "No report is required"
                ),
                correctIndex = 1,
                explanation = "A formal Post-Hajj Performance Report analyzing field metrics and suggesting operational improvements is submitted to the Federal Cabinet and Secretary MORA.",
                reference = "MORA Mission Debriefing Regulations"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Hajj Policy & Tech",
                question = "What key slogan encapsulates the ethos of Pakistan Moavineen-e-Hujjaj?",
                options = listOf(
                    "'Khidmat-e-Hujjaj, Hamari Sahadat' (Serving Pilgrims is Our Divine Privilege)",
                    "'Travel for Leisure'",
                    "'Business First'",
                    "'Strict Authority'"
                ),
                correctIndex = 0,
                explanation = "'Khidmat-e-Hujjaj, Hamari Sahadat' reflects the spirit of dedication, honor, and selfless service defining Moavineen-e-Hujjaj.",
                reference = "MORA Moavineen Motto & Vision"
            )
        )

        return list
    }
}
