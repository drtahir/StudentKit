package com.example.ui.screens

/**
 * MOAVINEEN-E-HUJJAJ QUESTION BANK - PART 2
 * Subject: Moavineen Operational SOPs & Duty Rules (30 100% Unique MCQs)
 * Covers Tayeena (Lost & Found) Centers, Maktab coordination, luggage tracking, airport reception, bus transport, supervisor dispatch, and field duty SOPs.
 */
object Moavineen1000Part2 {

    fun getOperationalSopQuestions(startId: Int): List<MoavineenQuestion> {
        val list = mutableListOf<MoavineenQuestion>()
        var idCounter = startId

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "When a Supporting Staff Moavin finds an elderly Pakistani pilgrim wandering lost in Mina without a tent card, what is the mandatory immediate SOP step?",
                options = listOf(
                    "Instruct the pilgrim to sit on the roadside until someone claims them",
                    "Check the pilgrim's MORA wristband/locket barcode, scan via Pak Hajj App, contact sector dispatch, and escort them to the nearest Tayeena (Lost & Found) Center",
                    "Hand the pilgrim over to a commercial taxi driver",
                    "Take the pilgrim's personal cash for safe keeping without issuing a receipt"
                ),
                correctIndex = 1,
                explanation = "The standard SOP requires inspecting the MORA identity wristband/locket, scanning barcode/details, contacting dispatch, and guiding/escorting the pilgrim to the Tayeena Center.",
                reference = "MORA Moavineen Operational SOP Manual - Section 4.1"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What is the primary responsibility of a Moavineen Sector Supervisor regarding field staff attendance during the 5 peak days of Hajj (8th to 12th Dhu al-Hijjah)?",
                options = listOf(
                    "Allow staff to leave duty posts at will without substitute coverage",
                    "Maintain shift rosters, conduct physical roll-call at designated Maktab points every 2 hours, and report real-time strength to Control HQ",
                    "Delegate all supervisor duties to private tour operators",
                    "Close the sector office during afternoon peak hours"
                ),
                correctIndex = 1,
                explanation = "Supervisors must enforce shift discipline, verify staff presence at critical field deployment points every 2 hours, and submit attendance logs to Director Moavineen.",
                reference = "MoRA Field Supervisory Guidelines"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "A pilgrim's registered green bag is missing upon arrival at the Jeddah Hajj Terminal luggage lounge. What procedure must the airport Moavin follow?",
                options = listOf(
                    "Tell the pilgrim that lost luggage is not the responsibility of MORA",
                    "Log a lost luggage voucher with flight number, MORA serial number, and baggage description, issue a claim receipt, and alert Makkah Main Luggage Cell",
                    "Advise the pilgrim to buy new clothing from local markets without filing a report",
                    "Confiscate baggage receipts from other pilgrims"
                ),
                correctIndex = 1,
                explanation = "Airport Moavineen must immediately record flight/baggage details on the official MORA Luggage Voucher, issue a receipt to the pilgrim, and forward tracking to Makkah Cell.",
                reference = "MORA Airport Reception SOPs"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "When a convoy of Pakistani pilgrims arrives at their designated Maktab in Mina on 8th Dhu al-Hijjah, how should Moavineen manage tent entries?",
                options = listOf(
                    "Allow pilgrims to enter any tent on a first-come, first-served basis",
                    "Verify Maktab numbers on pilgrim wristbands, guide male and female groups to pre-allocated partitioned tents, and ensure bed capacities are strictly met",
                    "Lock all tents until evening",
                    "Require pilgrims to pay extra cash for bed assignments"
                ),
                correctIndex = 1,
                explanation = "Moavineen must cross-check Maktab tags against tent allocation lists, separate male and female quarters orderly, and prevent bed hoarding or overcrowding.",
                reference = "MORA Mina Accommodation SOPs"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "During the movement from Mina to Arafat on 9th Dhu al-Hijjah, what is the duty of Moavineen stationed at Mashair Railway stations / SAPTCO bus stops?",
                options = listOf(
                    "Board the first train/bus themselves and leave pilgrims behind",
                    "Maintain organized queues, prioritize elderly, female, and disabled pilgrims, prevent platform overcrowding, and coordinate vehicle departures with supervisors",
                    "Charge entrance fees for train platforms",
                    "Direct pilgrims to walk 20 kilometers on foot without guidance"
                ),
                correctIndex = 1,
                explanation = "Station Moavineen control pedestrian traffic, enforce priority queue lines for vulnerable pilgrims, and prevent hazardous crowd stampedes at transit gates.",
                reference = "MORA Transport & Crowd Management SOPs"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "If a Moavin finds a lost wallet or purse containing money in the Masjid al-Haram courtyard or Mina pathways, what is the mandatory financial SOP?",
                options = listOf(
                    "Distribute the cash among fellow Moavineen as a reward",
                    "Hand over the wallet immediately to the MORA Main Lost & Found Committee with two witnessing staff signatures and obtain an official deposit voucher",
                    "Keep the wallet in personal possession until the end of Hajj deployment",
                    "Deposit the money into a private bank account"
                ),
                correctIndex = 1,
                explanation = "All recovered cash/valuables must be immediately handed over to MORA's official Lost & Found Deposit Committee under dual-witness signatures.",
                reference = "MORA Integrity & Financial Handling Regulations"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "A pilgrim collapses due to severe heat exhaustion inside a Mina tent. What is the Moavin's duty before the medical team arrives?",
                options = listOf(
                    "Move the pilgrim out into direct sunlight",
                    "Move the pilgrim to a cool shaded area, loosen tight Ihram garments, apply cool water/ice packs, offer ORS if conscious, and call the Pakistani Hajj Medical Mission dispensary",
                    "Give solid food and hot tea immediately",
                    "Leave the tent and search for the pilgrim's family members first"
                ),
                correctIndex = 1,
                explanation = "First-aid SOP for heat stroke includes shading, active cooling with water/ice, hydration if conscious, and immediate alert to the Medical Mission dispatch.",
                reference = "MORA Health First Aid SOP Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What daily shift log records must a Moavineen Field Supervisor submit to the Main Control Room at the conclusion of every 8-hour shift?",
                options = listOf(
                    "Personal shopping list and hotel meal menus",
                    "Total lost pilgrims reunited, medical referrals dispatched, missing luggage claims processed, staff attendance roster, and unresolved Maktab issues",
                    "Political opinions of pilgrims",
                    "Private vehicle rental agreements"
                ),
                correctIndex = 1,
                explanation = "Shift handover reports must detail operational metrics: lost/reunited cases, medical incidents, luggage logs, manpower status, and pending grievances.",
                reference = "MORA Supervisory Reporting Standards"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "How are Moavineen assigned to assist wheelchair-bound pilgrims attending Tawaf or Sa'i at Masjid al-Haram?",
                options = listOf(
                    "Charge pilgrims a commercial rate per hour",
                    "Utilize official MORA/Saudi approved wheelchairs, guide pilgrims through designated accessible ramps (e.g., Ajyad or King Abdulaziz gates), and maintain contact with supervisor",
                    "Push wheelchairs through restricted emergency lanes during prayer calls",
                    "Leave wheelchairs unattended on Mataf floor"
                ),
                correctIndex = 1,
                explanation = "Wheelchair duties are performed free of charge through authorized accessible gates and ramps, coordinating with Haram security personnel.",
                reference = "MORA Pilgrim Care Services SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "In the event of a localized fire alarm or electrical failure in a Mina camp sector, how does the Moavineen Supervisor coordinate evacuation?",
                options = listOf(
                    "Tell pilgrims to stay inside tents and wait",
                    "Immediately contact Saudi Civil Defense (998) / Red Crescent (997), alert Mutawwif authorities, and direct pilgrims through marked emergency escape corridors",
                    "Flee the camp without alerting pilgrims",
                    "Attempt to repair electrical wiring personally"
                ),
                correctIndex = 1,
                explanation = "The Supervisor triggers emergency response protocols by notifying Civil Defense (998) and leading systematic evacuation along clear safety paths.",
                reference = "MORA Emergency Disaster Response SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What is the mandatory dress code and identity badge requirement for Moavineen-e-Hujjaj while on field duty in Makkah, Mina, and Madinah?",
                options = listOf(
                    "Casual civilian clothing without identity tags",
                    "Official MORA Moavineen uniform jacket/vest, clearly displaying official laminated identity card and Pakistan badge",
                    "Traditional regional dress without registration tags",
                    "Full medical doctor scrub suit"
                ),
                correctIndex = 1,
                explanation = "Moavineen must strictly wear their designated official uniform jacket/vest with visible identity credentials to allow instant identification by pilgrims and Saudi police.",
                reference = "MORA Staff Uniform & ID Policy"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "When receiving pilgrim flights at Prince Mohammad bin Abdulaziz Airport in Madinah, what is the primary role of the Moavineen reception team?",
                options = listOf(
                    "Inspect custom duties on commercial cargo",
                    "Welcome pilgrims upon lounge exit, verify bus allocation vouchers, assist elderly with hand luggage, and ensure organized loading onto hotel buses",
                    "Sell local SIM cards for personal profit",
                    "Conduct passport visa interviews"
                ),
                correctIndex = 1,
                explanation = "Airport reception staff manage pilgrim arrival logistics: guidance, luggage handling assistance, bus boarding verification, and dispatch to Madinah hotel sectors.",
                reference = "MORA Madinah Airport SOP Manual"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "If a bus carrying 45 Pakistani pilgrims breaks down on the Makkah-Madinah highway, what is the Supervisor's immediate action plan?",
                options = listOf(
                    "Instruct pilgrims to hitchhike with passing private cars",
                    "Notify MORA Transport Cell and SAPTCO Control immediately to dispatch a replacement bus, send roadside water supplies, and stay with the vehicle until transfer is complete",
                    "Abandon the bus and proceed to the destination hotel alone",
                    "Order pilgrims to push the bus on the highway"
                ),
                correctIndex = 1,
                explanation = "Vehicle breakdowns trigger emergency bus replacement protocols via MORA Transport HQ, requiring staff to remain with pilgrims and ensure safety/hydration.",
                reference = "MORA Inter-City Highway Transport SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "At Tayeena (Lost & Found) Centers in Mina, what is the protocol when a lost child or non-verbal confused elderly pilgrim is brought in?",
                options = listOf(
                    "Announce over public speakers, log biometric barcode/photo into Pak Hajj Portal, provide food/water, and contact sector supervisors and Mutawwif office",
                    "Lock them in a storage room until evening",
                    "Send them alone onto the street to look for their tent",
                    "Transfer them to a commercial hotel reception"
                ),
                correctIndex = 0,
                explanation = "Tayeena SOP requires logging details into the central Pak Hajj app/database, making localized announcements, providing care, and coordinating with Mutawwif links.",
                reference = "MORA Lost & Found Protocol"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "How are lost luggage bags reunited with pilgrims housed in Azizia or Shisha residential buildings?",
                options = listOf(
                    "Dump all recovered bags in the street outside the building",
                    "Match baggage tag numbers with pilgrim passport/building registration databases, transport bags via MORA utility vans, and require signed delivery receipts",
                    "Sell unclaimed bags at public auction after 2 hours",
                    "Require pilgrims to walk to Jeddah airport to collect bags personally"
                ),
                correctIndex = 1,
                explanation = "Recovered luggage is tagged against MORA database records, dispatched directly to the pilgrim's residence via official utility transport, and signed off.",
                reference = "MORA Luggage Management SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What is the SOP when a Pakistani pilgrim passes away (Inna Lillahi wa Inna Ilayhi Raji'un) in a hotel or tent during Hajj?",
                options = listOf(
                    "Bury the deceased immediately without informing authorities",
                    "Report immediately to MORA Death & Welfare Cell, notify Saudi Police (999) / Mortuary, obtain official medical death report, assist family with Janazah at Haram, and manage burial documentation",
                    "Transport the deceased back to Pakistan on a commercial flight without documentation",
                    "Close the hotel building and evacuate all residents"
                ),
                correctIndex = 1,
                explanation = "Death incidents require formal procedures: MORA Welfare Cell reporting, police/hospital documentation, consular clearance, Janazah at Haram, and burial in Makkah/Madinah cemeteries.",
                reference = "MORA Death SOP & Welfare Regulations"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "When operating shuttle buses between Azizia residential sectors and Masjid al-Haram, what duty do Moavineen perform at bus terminals?",
                options = listOf(
                    "Collect cash fares from pilgrims",
                    "Manage queue discipline at SAPTCO stop points, prevent bus overloading beyond passenger capacity, and assist elderly pilgrims during boarding and alighting",
                    "Drive the bus vehicles personally",
                    "Prohibit female pilgrims from using shuttle buses"
                ),
                correctIndex = 1,
                explanation = "Terminal Moavineen enforce orderly queue management, safeguard against boarding overcrowding, and provide physical support to elderly/disabled commuters.",
                reference = "MORA Intra-City Shuttle SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What authority does a Moavineen Supervisor hold if a supporting staff member exhibits gross negligence or insubordination on duty?",
                options = listOf(
                    "Issue a formal written field warning, reassign duty post immediately, and report the infraction to Director Moavineen for disciplinary action",
                    "Physically assault the staff member",
                    "Cancel the staff member's passport personally",
                    "Ignore the incident completely"
                ),
                correctIndex = 0,
                explanation = "Supervisors maintain administrative control by issuing field warnings, managing immediate duty reassignments, and reporting conduct breaches to Director Moavineen.",
                reference = "MORA Disciplinary Code for Moavineen"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "During the night stay at Muzdalifah, what specific guidance should Moavineen communicate to pilgrims regarding sleeping areas?",
                options = listOf(
                    "Instruct pilgrims to sleep in the middle of active bus roadways",
                    "Direct pilgrims to designated open pedestrian grounds, keep clear of marked emergency vehicle lanes, and remain grouped by Maktab",
                    "Order pilgrims to set up canvas tents in Muzdalifah",
                    "Advise pilgrims to walk back to Makkah before midnight"
                ),
                correctIndex = 1,
                explanation = "In Muzdalifah, Moavineen ensure pilgrims settle in safe pedestrian zones, leaving asphalt roadways completely clear for emergency transport vehicles.",
                reference = "MORA Muzdalifah Field Management SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What protocol must be followed when a pilgrim lodges a formal complaint regarding unhygienic food or lack of water supply in a Mina tent?",
                options = listOf(
                    "Dismiss the complaint as trivial",
                    "Inspect the tent immediately, log the complaint in MORA Portal, notify the designated Saudi Mutawwif catering representative, and verify corrective action within 1 hour",
                    "Tell the pilgrim to buy commercial food at their own expense",
                    "Advise the pilgrim to stage a public protest"
                ),
                correctIndex = 1,
                explanation = "Supervisors must investigate caterer/water complaints on-site, register grievance logs on the portal, and coordinate rapid resolution with the Mutawwif office.",
                reference = "MORA Pilgrim Grievance Redressal SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What is the primary function of the MORA Information & Guidance Desks established inside Masjid al-Haram outer courtyards?",
                options = listOf(
                    "Sell religious souvenirs and books",
                    "Provide real-time directional guidance, help lost pilgrims find shuttle bus gates, offer wheelchair assistance, and issue lost identity tags",
                    "Process flight ticket cancellations",
                    "Exchange foreign currencies"
                ),
                correctIndex = 1,
                explanation = "Haram Guidance Desks serve as immediate field help hubs for lost pilgrims, shuttle gate identification, wheelchair distribution, and assistance.",
                reference = "MORA Haram Guidance Cell SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "How should Moavineen manage crowd movement at the entry gates of Mina camps during peak return hours from Jamarat?",
                options = listOf(
                    "Block the main gates completely",
                    "Establish clear one-way ingress and egress channels, direct returning groups to their specific street numbers, and keep gate passageways unobstructed",
                    "Allow crowd surging without guidance",
                    "Turn off street lights in the camp"
                ),
                correctIndex = 1,
                explanation = "Gate management requires designated entry/exit streams to avoid counter-flow bottlenecks when large contingents return from Jamarat.",
                reference = "MORA Camp Flow Management SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "How often must sector dispatch radios and mobile communication channels be checked for operational readiness by duty supervisors?",
                options = listOf(
                    "Once a month",
                    "At the beginning of every shift change and prior to major operational movements (e.g. Arafat transfer)",
                    "Only after an emergency occurs",
                    "Never; radios are not required"
                ),
                correctIndex = 1,
                explanation = "Communication equipment tests must occur at every shift change to guarantee uninterrupted radio/cellular linkages during field deployments.",
                reference = "MORA Telecommunication SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "If a Moavin discovers an unregistered private individual sleeping inside a Government Scheme Mina tent, what action is required?",
                options = listOf(
                    "Allow them to stay if they pay a private fee",
                    "Politely verify their credentials; if unauthorized, inform the Sector Supervisor and Maktab manager to ensure tent beds remain reserved for valid pilgrims",
                    "Physically push them out onto the street without inquiry",
                    "Give them an official MORA uniform"
                ),
                correctIndex = 1,
                explanation = "Unauthorized occupants reduce available facilities for legitimate pilgrims. Verification and reporting to Maktab managers prevents tent overcrowding.",
                reference = "MORA Tent Security SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What assistance do Moavineen offer to illiterate or elderly pilgrims who struggle to operate electronic key cards at Madinah hotel rooms?",
                options = listOf(
                    "Tell them to stay in the hotel lobby permanently",
                    "Demonstrate key card usage patiently, request hotel reception to assist or replace faulty cards, and inform building Moavineen floor in-charge",
                    "Charge a fee to open the door each time",
                    "Break the door locks"
                ),
                correctIndex = 1,
                explanation = "Moavineen provide empathetic technical assistance to elderly pilgrims, guiding them on key card operation or coordinating hotel reception support.",
                reference = "MORA Accommodation Welfare Guide"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "How should a Moavineen Supervisor handle shift scheduling during extreme weather conditions such as torrential rain or severe sandstorms in Mina?",
                options = listOf(
                    "Cancel all staff duties and evacuate staff to hotels",
                    "Deploy emergency weather response teams, equip staff with rain gear/flashlights, secure tent anchor ropes, and establish continuous radio contact with Control HQ",
                    "Ignore the weather warning completely",
                    "Order pilgrims to leave their tents and stand in open areas"
                ),
                correctIndex = 1,
                explanation = "Severe weather protocols involve deploying gear-equipped emergency response staff, securing infrastructure, and maintaining live command center connectivity.",
                reference = "MORA Weather Contingency Plan"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What action should a Moavin take if a pilgrim drops their passport or locket inside an escalator mechanism at Jamarat or Haram?",
                options = listOf(
                    "Attempt to stick hands into the moving escalator mechanism to retrieve it",
                    "Immediately hit the escalator Emergency Stop button if safety allows, alert Saudi facility engineers / Haram police, and report the lost document to MORA Cell",
                    "Ignore the incident and keep walking",
                    "Force other pilgrims to stop using all escalators"
                ),
                correctIndex = 1,
                explanation = "Safety comes first: press emergency stop if immediate danger exists, report to facility engineering/police, and file a lost document recovery report.",
                reference = "MORA Safety & Facility Coordination SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Both",
                subjectCategory = "Moavineen Operational SOPs",
                question = "When returning from Mina to Makkah after completing Hajj on 12th or 13th Dhu al-Hijjah, how is luggage movement coordinated for pilgrims?",
                options = listOf(
                    "Pilgrims must carry all heavy steel trunks on their heads while walking",
                    "Luggage is collected by Maktab trucks, tagged with building numbers, and transported centrally to residential buildings under Moavineen supervision",
                    "Luggage is dumped at Muzdalifah",
                    "Pilgrims must mail their luggage through commercial post offices"
                ),
                correctIndex = 1,
                explanation = "Post-Hajj luggage transfer is conducted centrally via Maktab transport trucks supervised by Moavineen to ensure safe delivery to Makkah residences.",
                reference = "MORA Luggage Transport SOP"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supervisor",
                subjectCategory = "Moavineen Operational SOPs",
                question = "What is the policy regarding Moavineen performing their own personal Hajj rituals while assigned to full-time field duty?",
                options = listOf(
                    "Moavineen are permitted to abandon duty posts at any time to perform personal Umrah/Hajj rituals",
                    "Official duty responsibilities and pilgrim facilitation take absolute priority; supervisors regulate duty rosters to allow staff to complete mandatory Fard rituals during designated off-shifts without compromising pilgrim care",
                    "Moavineen are forbidden from wearing Ihram under any circumstances",
                    "Moavineen must perform Hajj 5 times during a single deployment"
                ),
                correctIndex = 1,
                explanation = "Duty to pilgrims is the primary mandate. Supervisors coordinate off-shift rotations so staff can perform compulsory rituals without leaving duty posts unstaffed.",
                reference = "MORA Moavineen Code of Deployment"
            )
        )

        list.add(
            MoavineenQuestion(
                id = idCounter++,
                positionTarget = "Supporting Staff",
                subjectCategory = "Moavineen Operational SOPs",
                question = "How should a Moavin respond if approached by a foreign non-Pakistani pilgrim requesting directions or emergency aid?",
                options = listOf(
                    "Refuse assistance because they are not Pakistani",
                    "Offer polite assistance, provide direction or emergency first aid, and if needed guide them to their respective country's Hajj mission desk or Saudi police post",
                    "Demand payment in US Dollars for giving directions",
                    "Pretend not to understand any language"
                ),
                correctIndex = 1,
                explanation = "Moavineen embody Islamic hospitality (Khidmat al-Hujjaj) and assist any pilgrim in distress regardless of nationality, guiding them to appropriate channels.",
                reference = "MORA Universal Service Ethics"
            )
        )

        return list
    }
}
