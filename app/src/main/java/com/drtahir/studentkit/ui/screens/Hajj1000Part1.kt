package com.drtahir.studentkit.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 1
 * Category: Hajj Rules & Admin (30 100% Unique MCQs)
 * Covers MORA SOPs, Haji Camp protocols, deployment rosters, Hajj ritual calendars, emergency liaison & administration.
 */
object Hajj1000Part1 {

    fun getHajjRulesQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What is the primary administrative rationale behind the Ministry of Religious Affairs (MoRA) enforcing a mandatory 5-year gap rule for healthcare professionals applying for the Pakistani Hajj Medical Mission?",
                options = listOf(
                    "To restrict medical services exclusively to retired military personnel",
                    "To ensure equal opportunity, fair rotation, and prevent repeated deployment of the same personnel while maintaining high physical fitness",
                    "To reduce overall visa processing fees collected by Saudi authorities",
                    "To allow non-medical volunteer guides to replace clinical staff"
                ),
                correctIndex = 1,
                explanation = "The 5-year gap rule guarantees fair rotation among public sector health professionals across Pakistan, prevents monopoly by repeat applicants, and ensures physically capable, freshly screened staff are deployed.",
                reference = "MoRA Hajj Medical Mission Policy Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "During pre-departure medical screening at a provincial Haji Camp, what is the mandatory action if an intending pilgrim presents with severe uncontrolled chronic renal failure without a medical clearance certificate?",
                options = listOf(
                    "Issue immediate flight clearance and rely on Saudi dialysis centers",
                    "Refer the pilgrim immediately to the District Medical Board for formal health reassessment before issuing flight clearance",
                    "Cancel the pilgrim's passport permanently without appeal",
                    "Instruct the pilgrim to travel on a private tourist visa instead"
                ),
                correctIndex = 1,
                explanation = "Intending pilgrims with severe unstable chronic diseases must be formally evaluated by an authorized District Medical Board to determine fitness to travel under MoRA regulations.",
                reference = "Haji Camp Operational Health Screening Manual"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "Upon arrival at the central Pakistani Hajj Medical Mission Headquarters in Azizia (Makkah), what is the first operational obligation of newly arrived clinical staff?",
                options = listOf(
                    "Visit historical sites in Jeddah before reporting",
                    "Register at the Director Medical's office to receive sector duty assignments, shift rosters, and emergency communication protocols",
                    "Purchase local medical equipment from retail pharmacies",
                    "Establish a private outpatient consultation clinic"
                ),
                correctIndex = 1,
                explanation = "Immediate check-in at Mission HQ is mandatory to receive sector assignments (Makkah, Mina, Madinah), shift rotations, badge credentials, and emergency radio/phone channels.",
                reference = "MoRA Deployed Staff Standard Operating Procedures"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "During shift handover at a Sector Dispensary in Mina, what essential clinical information must the outgoing Charge Nurse document and convey?",
                options = listOf(
                    "A list of local commercial markets near the tent",
                    "Complete inventory of controlled medications, cold-chain temperature status of vaccines/insulin, emergency referral logs, and active admitted patients",
                    "Personal biographical notes of hotel cleaning staff",
                    "Unverified rumors about upcoming weather changes"
                ),
                correctIndex = 1,
                explanation = "Clinical shift handover requires strict auditing of controlled drugs, cold chain logs (2-8°C for insulin/vaccines), active patient transfer records, and emergency bed bed-occupancy.",
                reference = "Hajj Medical Mission Clinical Nursing Standards"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "When a critically ill pilgrim requires emergency transfer from a field clinic in Arafat to a Saudi Tertiary Hospital (e.g., Arafat General Hospital), what protocol must be followed?",
                options = listOf(
                    "Transport the patient in a private taxi without documentation",
                    "Notify the Saudi Red Crescent (997) or Red Crescent Dispatch and complete the official MORA emergency referral form",
                    "Wait until the Hajj rituals conclude before initiating transport",
                    "Instruct family members to carry the patient on foot"
                ),
                correctIndex = 1,
                explanation = "Emergency transfers require calling the Saudi Red Crescent (997) for ambulance dispatch and filling out the standard MORA Referral Form to ensure seamless hospital admission.",
                reference = "Saudi MOH & MoRA Emergency Referral Protocols"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "On the 9th of Dhu al-Hijjah (Wuquf-e-Arafat), how are mobile medical teams positioned across the plains of Arafat?",
                options = listOf(
                    "Stationed exclusively inside air-conditioned main headquarters",
                    "Strategically distributed along major pedestrian arterial walkways and field medical tents equipped with portable resuscitation kits",
                    "Stationed at Jeddah airport arrivals terminal",
                    "Retained inside buses until evening departure"
                ),
                correctIndex = 1,
                explanation = "Mobile teams are positioned along primary pedestrian routes in Arafat to deliver immediate field resuscitation, ORS, and cooling for pilgrims during peak sun hours.",
                reference = "Arafat Field Operations Deployment Manual"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "During the overnight stay at Muzdalifah (night of 9th Dhu al-Hijjah), what is the primary duty of roving medical paramedics?",
                options = listOf(
                    "Conducting routine elective surgical procedures",
                    "Providing rapid field triage for physical exhaustion, foot trauma, dehydration, and assisting disoriented elderly pilgrims",
                    "Distributing commercial marketing brochures",
                    "Administering general intravenous anesthesia"
                ),
                correctIndex = 1,
                explanation = "At Muzdalifah, pilgrims rest in open areas after traveling from Arafat. Paramedics focus on managing acute exhaustion, blistered feet, heat stress, and lost elderly pilgrims.",
                reference = "Muzdalifah Field Medical Surveillance Guide"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "On 10th Dhu al-Hijjah at Mina during the Jamarat Rami (stoning) ritual, what is the triage priority for medical teams stationed at Jamarat exit corridors?",
                options = listOf(
                    "Routine blood cholesterol screening",
                    "Immediate field management of heat collapse, crowd crush injuries, acute hyperthermia, and rapid ambulance dispatch",
                    "Issuing long-term chronic disease prescriptions",
                    "Elective dental scaling"
                ),
                correctIndex = 1,
                explanation = "Jamarat exit corridors experience extreme crowd density and thermal strain. Triage teams prioritize crush injuries, hyperthermia, cardiac events, and swift evacuation.",
                reference = "Jamarat Mass Gathering Triage SOP"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "How should a Sector Medical Officer in Mina manage stock thresholds when life-saving ORS and IV normal saline fall below 25% capacity?",
                options = listOf(
                    "Close the clinic until the next calendar day",
                    "Submit an urgent emergency drug requisition voucher to the Central Medical Depot in Makkah via the dedicated logistics coordinator",
                    "Ask pilgrims to purchase their own IV fluids from local retail stores",
                    "Dilute remaining IV bags with plain tap water"
                ),
                correctIndex = 1,
                explanation = "When critical supplies drop below the 25% buffer threshold, an emergency voucher triggers priority dispatch from the Central Pharmacy Depot to maintain continuous clinical capability.",
                reference = "Hajj Medical Mission Logistics & Supply Chain Manual"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "When treating an unidentified or disoriented pilgrim in a field dispensary, how does the medical team verify their identity, group, and medical history?",
                options = listOf(
                    "Rely solely on verbal claims from bystanders",
                    "Scan or read the official MORA QR/barcode printed on the pilgrim's mandatory identity wristband",
                    "Check local social media posts",
                    "Wait for hotel management to send a physical file"
                ),
                correctIndex = 1,
                explanation = "The mandatory MORA pilgrim wristband contains an encoded QR/barcode linked to nationality, Hajj group (Maktab), emergency contacts, and baseline health records.",
                reference = "MoRA Pilgrim Identification & Health IT Policy"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What is the official protocol when a Pakistani pilgrim passes away in a Hajj Medical Mission clinic?",
                options = listOf(
                    "Issue a private death note and hand the body to hotel staff",
                    "Immediately inform the Clinical Director, complete official Form-H death notification, and coordinate with Saudi MOH mortuary authorities",
                    "Retain the deceased pilgrim's passport without reporting",
                    "Bury the deceased immediately without local police or health authority clearance"
                ),
                correctIndex = 1,
                explanation = "Deceased pilgrims require official documentation on Form-H, notification to the Pakistan Hajj Welfare Office, local police clearance, and Saudi MOH mortuary processing.",
                reference = "MoRA Death Handling & Legal Protocol"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "How are hospitalized, non-ambulatory pilgrims enabled to complete the mandatory Wuquf-e-Arafat ritual on 9th Dhu al-Hijjah?",
                options = listOf(
                    "They are granted a religious exemption from Wuquf by hospital staff",
                    "They are transported in specialized Saudi MOH & MoRA 'Medical Convoy Buses' equipped with monitors and medical staff to Arafat",
                    "They are discharged to walk independently to Arafat",
                    "They perform Wuquf via video conference from their hospital bed"
                ),
                correctIndex = 1,
                explanation = "Since Wuquf at Arafat is an indispensable pillar of Hajj, Saudi MOH and MoRA operate equipped 'Medical Convoys' to carry bedridden patients to Arafat for the afternoon Wuquf.",
                reference = "Saudi MOH Special Pilgrim Medical Convoy Policy"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What is the primary function of the Central Pharmacy Depot established by the Hajj Medical Mission in Makkah?",
                options = listOf(
                    "Selling pharmaceutical items for profit to local retail stores",
                    "Bulk inventory control, temperature-monitored cold chain storage, and scheduled supply distribution to sector field clinics",
                    "Compounding unapproved herbal medicine formulas",
                    "Storing personal luggage for returning pilgrims"
                ),
                correctIndex = 1,
                explanation = "The Central Depot maintains cold-chain integrity for vaccines/insulin, manages bulk pharmaceutical reserves, and supplies sector clinics across Makkah, Mina, and Madinah.",
                reference = "Hajj Medical Mission Pharmacy Operations Manual"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "A deployed medical officer is invited by a private Hajj group operator to conduct paid evening private consultations. What is the MoRA regulatory rule?",
                options = listOf(
                    "Allowed if the fee is shared with the local clinic team",
                    "Strictly prohibited; mission staff are bound by contract to serve exclusively in official mission facilities without collecting fees",
                    "Allowed only on non-ritual days",
                    "Permitted if approved verbally by a hotel manager"
                ),
                correctIndex = 1,
                explanation = "All mission staff sign a Code of Conduct strictly prohibiting private clinical practice, fee collection, or commercial engagement during their deployment.",
                reference = "MoRA Deployed Staff Code of Ethics"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "Under Saudi Arabia health regulations, what is the mandatory minimum time window prior to arrival in Saudi Arabia for receiving the Quadrivalent Meningococcal vaccine?",
                options = listOf(
                    "At least 24 hours prior to travel",
                    "At least 10 days prior to arrival (and not more than 3 to 5 years depending on vaccine type)",
                    "Exactly 6 months prior to travel",
                    "Vaccination is performed upon landing at Jeddah airport"
                ),
                correctIndex = 1,
                explanation = "Saudi MOH mandates that the quadrivalent meningococcal (ACYW) vaccine certificate be issued at least 10 days before arrival to ensure protective antibody titers.",
                reference = "Saudi MOH Hajj Health Entry Requirements"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What is the total expected deployment duration for clinical members of the Pakistani Hajj Medical Mission in Saudi Arabia?",
                options = listOf(
                    "10 to 14 Days",
                    "Approximately 40 to 45 Days (covering pre-Hajj setup, active Hajj, and post-Hajj medical support)",
                    "90 Days",
                    "6 Months"
                ),
                correctIndex = 1,
                explanation = "Staff deployment spans 40 to 45 days, structured into early setup, peak 5-day Hajj management, and post-Hajj medical coverage in Makkah and Madinah.",
                reference = "MoRA Deployment Schedule Regulations"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What is the role of 'Mawawin' (welfare assistants) working alongside the Hajj Medical Mission?",
                options = listOf(
                    "Performing invasive surgical operations in field tents",
                    "Assisting with pilgrim guidance, translation, wheel-chair transport, lost-and-found coordination, and clinical triage support",
                    "Managing flight air traffic control at Jeddah airport",
                    "Auditing Saudi hospital financial accounts"
                ),
                correctIndex = 1,
                explanation = "Mawawin are welfare support staff who assist medical teams with translation, guiding disoriented pilgrims, wheelchair transport, and clinic crowd management.",
                reference = "MoRA Mawawin Operational Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "If a deployed nurse experiences acute severe illness requiring hospitalization during Hajj duties, what administrative action is initiated?",
                options = listOf(
                    "The nurse is immediately terminated without medical care",
                    "Formal evaluation by the Medical Mission Board for official sick leave, treatment in a designated hospital, and roster substitution",
                    "The nurse must hire a replacement from local tourist groups",
                    "Duties must be continued regardless of medical condition"
                ),
                correctIndex = 1,
                explanation = "Ill mission staff are evaluated by the Medical Mission Board to receive care in Saudi/Mission hospitals and ensure formal shift roster substitution.",
                reference = "MoRA Staff Health & Welfare Regulations"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What is the official procedure for handling controlled narcotic analgesics in a Hajj Medical Mission hospital ward?",
                options = listOf(
                    "Stored in open drawer counters for quick access",
                    "Kept in a locked double-key cabinet with double-signature administration logs and shift-by-shift physical count audits",
                    "Distributed freely to any pilgrim requesting pain relief",
                    "Disposed of in general trash bins after use"
                ),
                correctIndex = 1,
                explanation = "Controlled drugs require locked double-key storage, physical register logs, dual-nurse signature upon dispensing, and mandatory count verification at every shift change.",
                reference = "Hajj Medical Mission Controlled Substances SOP"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "During peak crowd movement in Mina, what is the standing instruction regarding mobile emergency medical kits carried by roving paramedics?",
                options = listOf(
                    "Include only administrative stationery",
                    "Equipped with basic airway management, IV crystalloids, ORS, automated external defibrillator (AED), epinephrine, and trauma dressings",
                    "Equipped exclusively with oral vitamins",
                    "Left locked inside camp storage rooms"
                ),
                correctIndex = 1,
                explanation = "Roving paramedic kits must be fully equipped for immediate life support: AED, airway devices, Epinephrine, IV fluids, ORS, and hemorrhage control supplies.",
                reference = "Mina Field Resuscitation Standards"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What authority conducts routine sanitary and hygiene inspections of Pakistani Hajj Medical Mission sector dispensaries in Makkah and Mina?",
                options = listOf(
                    "Local commercial restaurant managers",
                    "Saudi Ministry of Health (MOH) Environmental Health & Public Health Inspection Teams",
                    "Private airline travel agencies",
                    "Unaffiliated international tour operators"
                ),
                correctIndex = 1,
                explanation = "Saudi MOH Public Health Inspectors perform rigorous inspections of hygiene, bio-waste disposal, water safety, and cold chain logs in all medical mission facilities.",
                reference = "Saudi MOH Public Health Inspection Standards"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "When a pilgrim loses their physical passport and medical records while admitted to a mission dispensary, what is the protocol?",
                options = listOf(
                    "Discharge the pilgrim immediately without treatment",
                    "Notify the Hajj Welfare Office (MoRA) to issue an emergency Pilgrim Identity Certificate and coordinate record retrieval via wristband scanning",
                    "Require the pilgrim to pay a monetary fine to the clinic",
                    "Refuse to provide medical discharge until the original passport is located"
                ),
                correctIndex = 1,
                explanation = "The Hajj Welfare Office issues official identity certificates for lost documents, while wristband scanning recovers clinical data from the central database.",
                reference = "MoRA Lost Document & Pilgrim Welfare SOP"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What is the maximum allowable continuous shift length recommended for clinical staff during the 5 core Hajj days in Mina and Arafat?",
                options = listOf(
                    "36 continuous hours without rest",
                    "12 hours per shift (with mandatory rest rotation to prevent clinical fatigue and errors)",
                    "2 hours per day",
                    "48 continuous hours"
                ),
                correctIndex = 1,
                explanation = "To avoid medical errors and extreme exhaustion, shifts are capped at 12 hours with structured rest rotations during peak operational days.",
                reference = "Hajj Medical Mission Shift Roster Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "How should biohazardous medical waste (used needles, blood-stained dressings) be disposed of in field clinics in Mina?",
                options = listOf(
                    "Discarded into open public municipal trash bins outside tents",
                    "Segregated into color-coded biohazard containers (yellow bags for infectious waste, puncture-proof sharps boxes) for certified Saudi bio-waste collection",
                    "Buried in sandy ground near the tent area",
                    "Burned in open bonfires inside pilgrim camps"
                ),
                correctIndex = 1,
                explanation = "Biohazardous waste must be segregated into sharps boxes and yellow biohazard bags for safe removal by licensed Saudi medical waste management services.",
                reference = "Saudi MOH Biohazardous Waste Management Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What is the primary objective of establishing heat-relief misting showers and hydration stations along the Mina pedestrian tunnels?",
                options = listOf(
                    "Commercial promotion of bottled beverage brands",
                    "Rapid reduction of ambient temperature and prevention of exertion-induced hyperthermia among walking pilgrims",
                    "Providing bathing facilities for hotel staff",
                    "Cleaning pedestrian road asphalt"
                ),
                correctIndex = 1,
                explanation = "Misting showers reduce ambient temperatures in high-density walking corridors, providing essential evaporative cooling to prevent heat exhaustion and heat stroke.",
                reference = "Saudi Public Health Mass Gathering Climate Mitigation"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "If a disease outbreak (e.g., suspected acute gastroenteritis) occurs in a specific Maktab (pilgrim camp) in Mina, what is the immediate administrative action?",
                options = listOf(
                    "Conceal the outbreak to prevent public alarm",
                    "Isolate symptomatic cases, deploy an epidemiological investigation team, sample drinking water, and notify Saudi Public Health authorities",
                    "Evacuate all pilgrims back to Pakistan immediately",
                    "Administer prophylactic antibiotics to all citizens in Makkah city"
                ),
                correctIndex = 1,
                explanation = "Outbreak containment requires case isolation, rapid epidemiological field investigation, water/food sampling, and reporting to Saudi MOH Public Health units.",
                reference = "WHO & Saudi MOH Outbreak Response Protocol"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What clinical support is provided at the Madinah branch of the Pakistani Hajj Medical Mission?",
                options = listOf(
                    "Only administrative visa processing",
                    "Full outpatient clinics, emergency stabilization, pharmacy, and ambulance referral services for pilgrims visiting Prophet's Mosque",
                    "Exclusive cosmetic surgery consultations",
                    "Commercial hotel booking agency"
                ),
                correctIndex = 1,
                explanation = "The Madinah Medical Center provides 24/7 outpatient clinics, emergency care, pharmacy services, and hospital referral for pilgrims in Madinah.",
                reference = "MoRA Madinah Sector Operations Manual"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "What is the required protocol for managing language barriers when communicating with non-Urdu speaking pilgrims or Saudi hospital staff?",
                options = listOf(
                    "Refuse treatment until an English translator is hired",
                    "Utilize bilingual Mawawin staff, official translation cards, or digital translation tools to ensure accurate clinical history and consent",
                    "Rely entirely on non-verbal hand gestures for complex medical decisions",
                    "Discharge the patient without communication"
                ),
                correctIndex = 1,
                explanation = "Effective care requires leveraging bilingual support staff (Mawawin), medical translation cards, and digital tools to ensure accurate diagnosis and informed consent.",
                reference = "Hajj Medical Mission Communication & Cultural Competency Guide"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "When discharging a stabilized pilgrim from a mission dispensary back to their camp, what documentation must be provided?",
                options = listOf(
                    "No paperwork is necessary",
                    "A formal discharge summary detailing diagnosis, administered treatment, prescribed medications, and follow-up instructions for camp guides",
                    "A verbal message sent via a passing bystander",
                    "A commercial billing invoice"
                ),
                correctIndex = 1,
                explanation = "A Discharge Summary ensures continuity of care, informs camp guides of required rest or medication schedules, and updates central electronic records.",
                reference = "Hajj Medical Mission Clinical Documentation Policy"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Hajj Rules & Admin",
                question = "Under MoRA regulations, how long must clinical encounter registers and pharmacy dispensing logs be preserved following the conclusion of Hajj?",
                options = listOf(
                    "Destroyed immediately after Hajj ends",
                    "Archived securely for at least 5 years for official audit, medico-legal review, and public health reporting",
                    "Kept for 7 days only",
                    "Handed over to local hotel owners"
                ),
                correctIndex = 1,
                explanation = "Official medical logs and pharmacy registers are legal records retained for at least 5 years for statutory auditing, morbidity analysis, and medico-legal reference.",
                reference = "MoRA Medical Records Management Policy"
            )
        )

        return list
    }
}
