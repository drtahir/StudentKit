package com.studentkit.buner.ui.screens

data class ResumePresetDetail(
    val id: String,
    val label: String,
    val category: String,
    val fullName: String,
    val headline: String,
    val email: String,
    val phone: String,
    val location: String,
    val summaryText: String,
    val workExperiences: List<ResumeWorkHistory>,
    val academicList: List<ResumeAcademic>,
    val projectsList: List<ResumeProject>,
    val skillsCsv: String,
    val selectedAccentColorHex: String = "#1E3A8A",
    val selectedTemplateTheme: String = "Modern Blue Grid",
    val selectedTypography: String = "Sharp Sans-Serif"
)

object PresetRepository {

    private val techRoles = listOf(
        "Principal Architect", "Machine Learning Scientist", "DevOps Platform Engineer",
        "Cybersecurity Analyst", "iOS Swift Specialist", "Full Stack Practitioner",
        "SaaS Product Designer", "Embedded Rust Engineer", "Data Analytics Strategist",
        "Blockchain Core Developer", "Cloud Operations Specialist", "QA Automation Lead",
        "IoT Solutions Architect", "Scrum Agile Coach", "Virtual Reality Specialist",
        "Site Reliability Master", "Frontend React Guru", "API Gateway Designer",
        "Database Clustering Engineer", "AI Alignment Researcher", "Ethical Hacking Consultant",
        "Quantum Computing Assistant"
    )

    private val healthRoles = listOf(
        "Consultant Cardiologist", "Clinical Trial Lead", "Neonatal ICU Specialist",
        "Orthopedic Surgeon", "Psychopharmacology Specialist", "Epidemiology Investigator",
        "Senior Occupational Therapist", "Radiological Technologist", "Consulting Clinical Geneticist",
        "Healthcare Operations Analyst", "Pediatric Medicine Specialist", "Geriatric Care Coordinator",
        "Licensed Dental Practitioner", "Physical Medicine Therapist", "Audiology Clinician",
        "Health Bioinformatician", "Licensed Clinical Socialworker", "Emergency Air Ambulance Physician",
        "Immunology Researcher", "Nutrition Clinical Specialist", "Veterinary Research Pathologist",
        "Speech Language Pathologist"
    )

    private val educationRoles = listOf(
        "Early Childhood IB Educator", "Special Needs Curriculum Lead", "Higher Ed Policy Advisor",
        "Secondary Physics Academic", "Montessori School Director", "E-Learning Instructional Designer",
        "Language Immersion Specialist", "Educational Psychometrician", "STEM Laboratory Director",
        "Historical Research Fellow", "Professor of Fine Arts", "Literacy Intervention Coach",
        "Secondary Algebra Teacher", "Astronomy Lecturer", "Adult Literacy Coordinator",
        "Director of Academic Success", "Clinical Nursing Instructor", "Bilingual Special Ed Associate",
        "Music Conservatory Director", "University Registrar Officer", "Childhood Educational App Designer",
        "K-12 Physical Education Lead"
    )

    private val businessRoles = listOf(
        "M&A Strategy Advisor", "Agile Product Manager", "Corporate Finance Principal",
        "SaaS Sales Executive", "Risk Assessment Lead", "Investment Portfolio Analyst",
        "Brand Identity Director", "Supply Chain Controller", "Human Capital Strategist",
        "Business Operations Planner", "Management Consultant", "SEO Growth Lead",
        "Real Estate Fund Advisor", "Customer CRM Architect", "Venture Capital Associate",
        "Global Procurement Officer", "Corporate Governance Auditor", "Tax Compliance Specialist",
        "Digital Franchise Consultant", "Corporate Treasury Lead", "ESG Sustainability Director",
        "Office General Secretary"
    )

    private val engineeringRoles = listOf(
        "Renewable Solar Grid Lead", "BIM Structural Consultant", "Automotive Robotics Specialist",
        "Bioprocess Chemical Engineer", "Hydroelectric Grid Architect", "Industrial Safety Inspector",
        "Aerospace Avionics Analyst", "Water Sanitation Designer", "Agricultural Extension Officer",
        "Materials Testing Metallurgist", "Geotechnical Site Investigator", "Power Distribution Supervisor",
        "Precision Manufacturing Planner", "Mechatronics Development Lead", "Telecom Optical Fiber Engineer",
        "Marine Propulsion Architect", "HVAC Energy Auditor", "Environmental Remediation Designer",
        "Petroleum Subsurface Planner", "Mine Safety Superintendent", "Agri-Tech Hydrologist",
        "Traffic Network Modeler"
    )

    private val creativeRoles = listOf(
        "VFX Compositing Specialist", "Lead Animation Director", "Cinematography Editor",
        "UI/UX Creative Director", "Bespoke Jewelry Visualizer", "Apparel Textile Designer",
        "Exhibition Space Curator", "Interactive Game Writer", "Sound Synthesis Designer",
        "Corporate Identity Architect", "Fine Art Portrait Sculptor", "Industrial Package Designer",
        "Bilingual Public Relations Lead", "Creative Advertising Copywriter", "Digital Matte Painter",
        "Fashion Brand Visual Designer", "Architectural Interior Designer", "Concept Sketch Illustrator",
        "Broadcast Audio Engineer", "Typography Font Designer", "Museum Interactive Tech Creator",
        "Podcast Production Lead"
    )

    private val aviationRoles = listOf(
        "B777 Airline Captain", "Air Traffic Systems Lead", "Aero Engine Quality Auditor",
        "Airport Ground Safety Chief", "Harbor Freight Logistics Lead", "Marine Customs Inspector",
        "Flight Operations Planner", "Charter Yacht Skipper", "In-Flight Safety Director",
        "Dredging Operations Supervisor", "Aviation Cargo Superintendent", "Aerodrome Master Planner",
        "Avionics System Specialist", "Drone Fleet Operator", "Offshore Supply Vessel Captain",
        "Railway Network Controller", "Cold Chain Fleet Logistician", "Port Terminal Coordinator",
        "Aircraft Maintenance Specialist", "Commercial Helicopter Pilot", "Subsea Robotic Operator",
        "Dangerous Cargo Specialist"
    )

    private val legalRoles = listOf(
        "Corporate Mergers Counsel", "IP Patent Prosecution Lead", "Regulatory Compliance Principal",
        "Arbitration Conflict Mediator", "Workforce Relations Advocate", "Data Privacy Legal Expert",
        "Tax Audit Defense Counsel", "Maritime Admiralty Attorney", "Environmental Impact Advisor",
        "Bilingual Legal Drafter", "Digital Contract Analyst", "Real Estate Legal Title Officer",
        "Hospital Risk Legal Counsel", "Aviation Regulatory Specialist", "ESG Corporate Integrity Inspector",
        "Patent Classification Agent", "Civil Rights Policy Counsel", "Immigration Client Consultant",
        "Employment Standards Investigator", "IP Licensing Negotiator", "Govt Public Records Officer",
        "Financial Crime Investigator"
    )

    private val govtRoles = listOf(
        "Senior Public Policy Expert", "Urban Redevelopment Director", "Bilingual Diplomatic Analyst",
        "Emergency Crisis Manager", "Director of Statistics", "Municipal Waste Program Lead",
        "Park Ranger Lead Superintendent", "Healthcare Subsidy Program Officer", "Digitization E-Gov Architect",
        "Elections Audit Coordinator", "Intergovernmental Liaison", "Housing Allocation Advisor",
        "Agricultural Subsidy Coordinator", "Immigration Border Advisor", "Regional Watershed Conservator",
        "Public Transit System Planner", "Cyber Threat Defence Liaison", "Culture Heritage Site Manager",
        "Veterans Welfare Director", "National Security Policy Writer", "Fisheries Protection Advisor",
        "Social Safety Net Auditor"
    )

    private val servicesRoles = listOf(
        "Luxury Resort Guest Specialist", "Michelin Restaurant Director", "Exotic Cruise Cruise Host",
        "Enterprise Customer Success Lead", "Global Corporate Event Lead", "Bilingual Flight Cabin Chef",
        "Fine Art Auctioneer", "Wellness Center Director", "International Retail Planner",
        "Luxury Tailor Apparel Fitter", "Casino Operations Shift Chief", "Eco-Tourism Expeditions Leader",
        "Elite Concierge Coordinator", "Visual Merchandising Director", "Catering Event Producer",
        "Sommelier & Beverage Curator", "Pet Care Facility Director", "Commercial Property Facilities Chief",
        "Amusement Park Safety Supervisor", "International Shipping Specialist", "Corporate Wellness Concierge",
        "Funeral Home Care Director"
    )

    private val intlNames = listOf(
        "John Smith", "Chloe Dupont", "Hiroshi Tanaka", "Mateo Silva", "Sven Johansson",
        "Emma Taylor", "Sophia Patel", "Naomi Mwangi", "Fatima Al-Kaabi", "Diego Rodriguez",
        "Lucas Müller", "Anna Kowalski", "Yusuf Demir", "Chen Wei", "Ji-won Kim",
        "Elena Rossi", "Olav Hansen", "Alexander Petrov", "Carlos Gomez", "Aria Singh",
        "Oliver Jones", "Zara Phillips", "Liam O'Connor", "Nina Ricci", "Isabella Santos"
    )

    private val intlLocs = listOf(
        Pair("New York, USA", "+1 (555) 321-4567"),
        Pair("London, UK", "+44 20 7946 0958"),
        Pair("Toronto, Canada", "+1 (416) 555-0192"),
        Pair("Sydney, Australia", "+61 2 9876 5432"),
        Pair("Berlin, Germany", "+49 30 123456"),
        Pair("Singapore", "+65 6123 4567"),
        Pair("Tokyo, Japan", "+81 3 5555 1212"),
        Pair("Dubai, UAE", "+971 4 123 4567"),
        Pair("Cape Town, South Africa", "+27 21 555 4321"),
        Pair("Paris, France", "+33 1 42 27 78 54"),
        Pair("Dublin, Ireland", "+353 1 496 0123"),
        Pair("Amsterdam, Netherlands", "+31 20 555 0199")
    )

    private val intlUnis = listOf(
        "Stanford University", "Oxford University", "University of Toronto", "University of Melbourne",
        "Technical University of Munich", "National University of Singapore", "University of Tokyo",
        "Khalifa University", "University of Cape Town", "Sorbonne University", "Trinity College Dublin",
        "Delft University of Technology"
    )

    // Maintaining 29 original presets of the application keeping 100% features intact
    private val manualPresets = listOf(
        ResumePresetDetail(
            id = "cs",
            label = "🚀 Software Intern",
            category = "Tech",
            fullName = "Daniyal Ahmed Dani",
            headline = "AI Research Intern & Jetpack Compose Engineer",
            email = "daniyal@office.edu.pk",
            phone = "+92 312 9876543",
            location = "Karachi, PK",
            summaryText = "CS Graduate skilled in writing custom canvas layers, ML Kit camera integrations, and predictive data visualizations. Passionate optimizer.",
            workExperiences = listOf(
                ResumeWorkHistory("AI Software Intern", "Google Developers Group CL", "2025 - Present", "Integrated server-side Gemini multi-turn APIs and engineered automated context windows.", "Maintained 99.8% crash-free sessions across Android 12+ device streams."),
                ResumeWorkHistory("Mobile App QA Assistant", "Sindh Tech Labs", "2023 - 2025", "Built automated mock environments and recorded UI tests using local JUnit frameworks.", "Collaborated in visual theme refactoring and compiled 14 custom user-interface variants.")
            ),
            academicList = listOf(
                ResumeAcademic("BS in Software Engineering", "FAST NUCES Karachi", "2021 - 2025", "Gold Medalist | 3.94 GPA")
            ),
            projectsList = listOf(
                ResumeProject("AI Resume Optimizer Hub", "Compose, Retrofit, Kotlinx", "github.com/daniyal/resume-ai", "Scraped ATS keywords to automatically grade uploaded resume assets on compile streams.")
            ),
            skillsCsv = "Kotlin, Jetpack Compose, Gemini API, PyTorch, Ktor Server, Room database, Git",
            selectedAccentColorHex = "#0D9488",
            selectedTemplateTheme = "Creative Emerald Garden"
        ),
        ResumePresetDetail(
            id = "medical",
            label = "🩺 Clinical MBBS",
            category = "Health",
            fullName = "Dr. Ayesha Siddiqua",
            headline = "Clinical Intern & Academic Healthcare Writer",
            email = "ayesha.sidd@jinnah-hosp.org",
            phone = "+92 331 4455882",
            location = "Sindh, Pakistan",
            summaryText = "Licensed Medical Student with extensive rotations in emergency intensive care fields. Committed to leveraging data science diagnostics for patient outcomes.",
            workExperiences = listOf(
                ResumeWorkHistory("Emergency Duty Intern", "Jinnah Post Graduate Medical Center", "Jul 2024 - Present", "Managed trauma intakes and optimized clinical charting practices under senior supervisors.", "Conducted primary research on pediatric triage guidelines, reducing patient wait times by 15%."),
                ResumeWorkHistory("Clinical Care Volunteer", "Red Crescent Society Pakistan", "2022 - 2024", "Assisted in public health awareness webinars and recorded patient triage questionnaires.", "Organized community health diagnostic records and standardized offline patient data caches.")
            ),
            academicList = listOf(
                ResumeAcademic("Bachelor of Medicine, Bachelor of Surgery (MBBS)", "Dow University of Health Sciences", "2019 - 2024", "Top 5% Graduate | Academic Distinction")
            ),
            projectsList = listOf(
                ResumeProject("Rural Tele-Health Diagnostics Pilot", "E-Medicine Module", "telehealth.org/pioneer", "Implemented offline-sync digital medical charts for communities lacking network access.")
            ),
            skillsCsv = "Clinical Diagnosis, Pediatric Care, Patient Triage, Electronic Health Records (EHR), Medical Research",
            selectedAccentColorHex = "#881337",
            selectedTemplateTheme = "The Ivy League Serif",
            selectedTypography = "Classic Serif"
        ),
        ResumePresetDetail(
            id = "business",
            label = "📊 Product Analyst",
            category = "Business",
            fullName = "Zainab Fatima Ali",
            headline = "Product Management Associate & Business Analyst",
            email = "zainab.ali@iba.edu.pk",
            phone = "+92 301 2233990",
            location = "Karachi, Pakistan",
            summaryText = "Business graduate focusing on interactive SaaS financial charts and customer acquisition models. Experienced leader of cross-functional student bodies.",
            workExperiences = listOf(
                ResumeWorkHistory("Associate Product Intern", "FinTech Spark", "Sep 2025 - Dec 2025", "Coordinated user studies of budget planners, boosting daily active engagement by 22%.", "Synthesized market requirements into structured product backlog epics."),
                ResumeWorkHistory("Business Strategy Lead", "IBA Entrepreneurs Club", "2024 - 2025", "Pioneered offline student financial literacy workshops, aligning 150+ candidates.", "Drafted competitive market grids and presented detailed risk assessment spreadsheets.")
            ),
            academicList = listOf(
                ResumeAcademic("BBA Marketing & Data Science", "Institute of Business Administration", "2022 - 2026", "Deans List Scholar | 3.78 GPA")
            ),
            projectsList = listOf(
                ResumeProject("Micro-Savings Growth Calculator", "Financial Engine", "github.com/zainab/saver", "Built customizable interactive models projecting interest rates across multiple credit committees.")
            ),
            skillsCsv = "Product Backlogs, User Cohort Analytics, Agile Scrum, Excel Modeling, Wireframing, Public Speaking",
            selectedAccentColorHex = "#4C1D95",
            selectedTemplateTheme = "Executive Slate Midnight"
        ),
        ResumePresetDetail(
            id = "civil",
            label = "🏗️ Civil / BIM Eng",
            category = "Engineering",
            fullName = "Amir Khan Al-Balushi",
            headline = "Lead Structural Design Engineer & BIM Consultant",
            email = "amir.balushi@eng-uae.ae",
            phone = "+971 50 1234567",
            location = "Dubai, UAE",
            summaryText = "Meticulous Civil Engineering honors graduate with specialized hands-on expertise in Building Information Modeling (BIM), finite element concrete analysis, and earthquake-resistant design methodologies.",
            workExperiences = listOf(
                ResumeWorkHistory("Junior BIM Analyst", "Emaar Properties PJSC", "2024 - Present", "Automated clash detection workflows across 4 commercial structural plans, saving $50K in refitting costs.", "Authored structural calculation spreadsheets compliant with BS & Eurocode specifications."),
                ResumeWorkHistory("Structural Detailing Intern", "Dubai Engineering Council", "2022 - 2024", "Assisted in drafting 15 custom CAD schematics for modern underground pipeline projects.", "Coordinated on-site material inspections to guarantee Eurocode-compliant tensile strength standards.")
            ),
            academicList = listOf(
                ResumeAcademic("Bachelor of Civil Engineering", "American University of Sharjah", "2020 - 2024", "GPA 3.82 / 4.0 | Magna Cum Laude")
            ),
            projectsList = listOf(
                ResumeProject("High-Rise Wind Shear Simulation", "ETABS & SAP2000 Pro", "emaar.ae/shear-results", "Modeled dynamic lateral shear behaviors of a 45-story concrete deck under 140km/h wind loads.")
            ),
            skillsCsv = "BIM Revit, ETABS, SAP2000, AutoCAD, Concrete Detailing, Project Scheduling, Eurocode compliance",
            selectedAccentColorHex = "#D97706",
            selectedTemplateTheme = "UAE Modern Grid",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "educator",
            label = "🍎 Specialist Educator",
            category = "Education",
            fullName = "Sarah Jenkins, OCT",
            headline = "Ontario Certified Educator & Spec-Ed Curriculum Coordinator",
            email = "sarah.jenkins@tdsb.on.ca",
            phone = "+1 (416) 555-0192",
            location = "Toronto, ON, Canada",
            summaryText = "Innovative K-12 and special education teacher dedicated to developmental literacy and universal learning design. Experienced in designing individualized education plans (IEPs) and student progress trackers.",
            workExperiences = listOf(
                ResumeWorkHistory("Special Education Teacher", "Toronto District School Board", "2023 - Present", "Implemented multisensory structured literacy interventions, advancing reading levels by 1.8 years for a neurodiverse cohort.", "Coordinated team-building sessions to align 12 classroom aid specialists with IEP accommodations."),
                ResumeWorkHistory("Elementary Classroom Assistant", "Toronto Montessori Academy", "2021 - 2023", "Developed universal digital progress logs for 25+ students, enhancing class report workflows.", "Conducted classroom interactive drills to promote social integration and adaptive language skills.")
            ),
            academicList = listOf(
                ResumeAcademic("Master of Teaching (Elementary/Secondary)", "University of Toronto (OISE)", "2021 - 2023", "Ontario Certified Teacher (OCT)")
            ),
            projectsList = listOf(
                ResumeProject("Digitized Universal Reading Trackers", "Google Sheets App Script", "github.com/sarah/learning-track", "Engineered lightweight visual progress dashboards sharing direct literacy metrics with student parents daily.")
            ),
            skillsCsv = "Individual Education Plans (IEPs), Special Ed (Universal Design), Educational Tech, Literacy Interventions, Parent Communication",
            selectedAccentColorHex = "#DC2626",
            selectedTemplateTheme = "Canada Academic Standard",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "australia_it",
            label = "☁️ Cloud Security Architect",
            category = "Tech",
            fullName = "Lachlan Mitchell",
            headline = "Senior Cloud Security Solutions Architect (AWS/Azure)",
            email = "lachlan.mitchell@melbourne-sec.edu.au",
            phone = "+61 3 9555 4321",
            location = "Melbourne, VIC, Australia",
            summaryText = "Certified AWS/Azure Solutions Architect with 5+ years of infrastructure design, focusing on zero-trust architectures, sovereign cloud protection compliance, and container security.",
            workExperiences = listOf(
                ResumeWorkHistory("Associate Cloud Security Engineer", "Aconex Security Corp", "2023 - Present", "Architected IAM permission structures across 80+ enterprise AWS accounts, eliminating 90% of legacy permissions.", "Designed automated Terraform modules scanning live subnets against ASD Essential Eight security standards."),
                ResumeWorkHistory("Cloud Support Intern", "Telstra Sovereign Security", "2021 - 2023", "Assisted in monitoring zero-trust network endpoints and deploying security patches.", "Documented disaster recovery playbooks and optimized automated IAM permission audits.")
            ),
            academicList = listOf(
                ResumeAcademic("Bachelor of Computer Science (CYBER)", "RMIT University", "2020 - 2023", "High Distinction (Dean's List)")
            ),
            projectsList = listOf(
                ResumeProject("Automation Essential Eight Auditer", "Python & AWS Lambda", "github.com/lachlan/asd-auditor", "Synthesized Australian Cyber Security Centre guidelines into live automated REST alert endpoints.")
            ),
            skillsCsv = "AWS IAM, Azure Security, Terraform Infrastructure, Kubernetes Hardening, ASD Essential 8, Threat Modeling",
            selectedAccentColorHex = "#334155",
            selectedTemplateTheme = "Australia Professional",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "pharmacy_tech",
            label = "💊 Pharmacy Tech",
            category = "Health",
            fullName = "Muhammad Bilal Raza",
            headline = "Certified Pharmacy Technician (RPhT)",
            email = "bilal.raza@shifa-pharmacy.com",
            phone = "+92 321 4455221",
            location = "Islamabad, PK",
            summaryText = "Detail-oriented Certified Pharmacy Technician with 3+ years of clinical and retail pharmacy experience. Expert in prescription fulfillment, dynamic inventory control, and patient medication counseling.",
            workExperiences = listOf(
                ResumeWorkHistory("Senior Lead Dispenser", "Shifa International Hospital", "2024 - Present", "Dispensed 150+ prescription orders daily with zero dosage errors under direct pharmacological supervision.", "Managed automated drug inventory ledger systems, reducing medication replenishment delays by 25%."),
                ResumeWorkHistory("Junior Pharmacy Technician", "D-Watson Chemists", "2022 - 2024", "Maintained sterile compound preparation zones and organized over-the-counter wellness categories recursively.", "Handled patient insurance claims documentation and resolved billing discrepancies efficiently.")
            ),
            academicList = listOf(
                ResumeAcademic("Diploma in Pharmacy Technician", "Punjab Board of Technical Education", "2020 - 2022", "Grade: A Grade | Distinction in Pharmacology")
            ),
            projectsList = listOf(
                ResumeProject("Digital Barcode Inventory Tracker", "Local POS Suite", "github.com/bilal/pharmacy-pos", "Created manual catalog schemas and custom barcode templates to accelerate item-specific audits.")
            ),
            skillsCsv = "Prescription Fulfillment, Pharmacology Basics, Sterile Compounding, Patient Counseling, Stock Ledger Management",
            selectedAccentColorHex = "#14B8A6",
            selectedTemplateTheme = "Creative Emerald Garden",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "nurse",
            label = "🏥 Registered ICU Nurse",
            category = "Health",
            fullName = "Anum Khurram, RN",
            headline = "Registered ICU Nurse & Pediatric Coordinator",
            email = "anum.khurram@aku.edu",
            phone = "+92 333 1234567",
            location = "Karachi, Pakistan",
            summaryText = "Compassionate Registered Nurse (RN) with 4+ years of intensive care and emergency ward experience. Skilled in advanced patient monitoring, clinical trial protocols, and pediatric emergency procedures.",
            workExperiences = listOf(
                ResumeWorkHistory("Registered ICU Nurse", "Aga Khan University Hospital (AKUH)", "2023 - Present", "Delivered comprehensive critical care for post-operative adult and pediatric patients with multi-organ challenges.", "Administered complex drug therapies and coordinated patient rehabilitation paths with multidisciplinary doctors."),
                ResumeWorkHistory("Staff General Nurse", "Indus Hospital & Health Network", "2021 - 2023", "Managed daily triage intake charts across the fast-paced pediatric emergency wing safely.", "Mentored 10+ junior nursing interns on medical safety hand hygiene and sanitation compliance codes.")
            ),
            academicList = listOf(
                ResumeAcademic("BSc in Nursing (BScN)", "Aga Khan University", "2017 - 2021", "CGPA 3.81 / 4.0 | Valedictorian")
            ),
            projectsList = listOf(
                ResumeProject("Clinical Hand Hygiene Audit", "Quality Improvement", "aku.edu/hand-hygiene-project", "Spearheaded local ward assessments yielding a 95% compliance rate shift within six months.")
            ),
            skillsCsv = "Critical Care, Patient Vital Monitoring, Pediatric Triage, Emergency IV Access, Nursing Care Plans",
            selectedAccentColorHex = "#0EA5E9",
            selectedTemplateTheme = "The Ivy League Serif",
            selectedTypography = "Classic Serif"
        ),
        ResumePresetDetail(
            id = "medical_lab",
            label = "🔬 Medical Lab Technologist",
            category = "Health",
            fullName = "Hamza Yousuf",
            headline = "Senior Medical Laboratory Technologist",
            email = "hamza.yousuf@chughtailab.com",
            phone = "+92 345 9876543",
            location = "Lahore, Pakistan",
            summaryText = "Licensed Lab Technologist with specialized competence in molecular pathology, PCR diagnostic suites, and automated hematology testing. Committed to ISO 15189 lab quality standards.",
            workExperiences = listOf(
                ResumeWorkHistory("Pathology Technologist", "Chughtai Lab Headquarters", "2024 - Present", "Executed 400+ high-precision diagnostic blood analyses, chemistry profiles, and PCR assays daily.", "Maintained automated clinical chemistry analyzers and calibrated hematology devices daily."),
                ResumeWorkHistory("Lab Assistant Technician", "Shaukat Khanum Memorial Hospital", "2022 - 2024", "Processed clinical tissue specimens and prepared microscopic slides under consulting pathologists.", "Enforced strict biohazard containment procedures and standardized specimen logging workflows.")
            ),
            academicList = listOf(
                ResumeAcademic("BS in Medical Laboratory Technology (MLT)", "University of Health Sciences", "2018 - 2022", "First Division | Top 10 rank")
            ),
            projectsList = listOf(
                ResumeProject("Lab ISO Compliance Blueprint", "System Audit", "github.com/hamza/iso-15189-prep", "Designed internal laboratory checklist scripts tracking calibration intervals across critical incubators.")
            ),
            skillsCsv = "Molecular Pathology, PCR Diagnostic Assays, Hematology, Laboratory Quality Control, Biohazard Handling",
            selectedAccentColorHex = "#4F46E5",
            selectedTemplateTheme = "Executive Slate Midnight",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "pst_teacher",
            label = "✏️ PST (Primary Teacher PK)",
            category = "Education",
            fullName = "Sajida Parveen",
            headline = "PST (Primary School Teacher) - Education Dept",
            email = "sajida.p@sed.punjab.gov.pk",
            phone = "+92 300 8765432",
            location = "Multan, Pakistan",
            summaryText = "Certified PST with 5+ years of experience fostering primary literacy, basic math, and foundational science. Dedicated to implementing inclusive play-based learning and activity-centric pedagogy.",
            workExperiences = listOf(
                ResumeWorkHistory("Primary School Teacher (PST - Grade 14)", "Government Girls Primary School (GGPS)", "2022 - Present", "Formulated lesson structures for Urdu, Math, and English, benefiting 120+ early childhood students.", "Conducted monthly parent-teacher diagnostic circles to target and improve student drop-out rates."),
                ResumeWorkHistory("Contract Elementary Educator", "The Beaconhouse School System", "2020 - 2022", "Managed early developmental reading circles and structured interactive science craft classes.", "Organized physical student assemblies and designed hand-drawn academic flashcards recursively.")
            ),
            academicList = listOf(
                ResumeAcademic("M.A. in Education (MEd)", "Bahauddin Zakariya University", "2018 - 2020", "First Division | 3.65 GPA")
            ),
            projectsList = listOf(
                ResumeProject("Urdu Phonetic Literacy Kit", "Interactive Learning", "ggps-multan.edu/phonics-kit", "Conceived custom audio and flashcard templates that boosted classroom reading evaluation times.")
            ),
            skillsCsv = "Primary Pedagogy, Classroom Management, Lesson Planning, Early Childhood Phonics, Student Evaluation",
            selectedAccentColorHex = "#059669",
            selectedTemplateTheme = "Canada Academic Standard",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "est_teacher",
            label = "📐 EST (Elementary Science PK)",
            category = "Education",
            fullName = "Naheed Akhtar",
            headline = "EST (Elementary School Teacher) - Science & Math",
            email = "naheed.akhtar@sed.punjab.gov.pk",
            phone = "+92 315 7654321",
            location = "Gujranwala, Pakistan",
            summaryText = "Resourceful EST with 6 years of experience teaching General Science, Mathematics, and English to elementary classes. Direct focus on digital literacy integrations and activity-based learning checklists.",
            workExperiences = listOf(
                ResumeWorkHistory("Elementary School Teacher (EST)", "Government Boys High School (GBHS)", "2021 - Present", "Instructed 80+ senior elementary candidates in General Science, achieving a 98% annual exam pass rate.", "Formulated board-level mock examinations and maintained student progress grids securely."),
                ResumeWorkHistory("Science Instructor", "The City School", "2019 - 2021", "Co-designed introductory physics and chemistry lab practical guidelines for Grade 6-8 categories.", "Organized regional science competition fairs, training 14 award-winning student teams.")
            ),
            academicList = listOf(
                ResumeAcademic("BS in Mathematics & Education", "University of the Punjab", "2015 - 2019", "First Division | Deans List Scholar")
            ),
            projectsList = listOf(
                ResumeProject("Interactive Science Demo Manual", "Teacher Resources", "gbhs-guj.edu/science-demos", "Compiled 50 cheap, local-material science demonstration blueprints for resource-constrained classrooms.")
            ),
            skillsCsv = "Elementary Pedagogy, STEM Demonstrations, Board Syllabus Coaching, Interactive Mathematics, Progress Tracking",
            selectedAccentColorHex = "#D97706",
            selectedTemplateTheme = "Canada Academic Standard",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "sst_teacher",
            label = "🔬 SST (Secondary Physics PK)",
            category = "Education",
            fullName = "Tariq Mahmood",
            headline = "SST (Secondary School Teacher) - Physics & IT",
            email = "tariq.mahmood@sed.punjab.gov.pk",
            phone = "+92 322 3456789",
            location = "Faisalabad, Pakistan",
            summaryText = "Dedicated Secondary School Teacher (SST - Science) with over 8 years of instruction in physics and computer science. Skilled in BISE board exams preparation, exam hall invigilation, and computer lab setup.",
            workExperiences = listOf(
                ResumeWorkHistory("Secondary School Teacher (SST - Physics)", "Government High School Faisalabad", "2018 - Present", "Delivered daily advanced Physics and Comp-Sci curriculum to secondary matric and F.Sc cohorts.", "Supervised the setup of modern 30-workstation computer laboratory nodes under the Prime Minister's IT Initiative."),
                ResumeWorkHistory("Physics Tutor", "KIPS College System", "2015 - 2018", "Conducted exhaustive entry test coaching classes and drafted mock tests for MDCAT and ECAT tracks.", "Provided individualized physics problem-solving sessions, raising mean scores by 20% across sections.")
            ),
            academicList = listOf(
                ResumeAcademic("MSc in Physics", "Government College University Faisalabad", "2013 - 2015", "Top Division Honors")
            ),
            projectsList = listOf(
                ResumeProject("Digital Physics Mock Bank", "BISE Exam Prep", "github.com/tariq/physics-mcqs-pk", "Programmed local database engines categorizing past paper numeric solutions for offline student study.")
            ),
            skillsCsv = "BISE Board Coaching, Physics Laboratory Safety, ECAT/MDCAT Prep, Computer Lab Operations, Student Assessment",
            selectedAccentColorHex = "#2563EB",
            selectedTemplateTheme = "The Ivy League Serif",
            selectedTypography = "Classic Serif"
        ),
        ResumePresetDetail(
            id = "lecturer",
            label = "🎓 HEC Assistant Professor",
            category = "Education",
            fullName = "Prof. Dr. Fahad Hashmi",
            headline = "University Assistant Professor & IT Lecturer",
            email = "fahad.hashmi@uok.edu.pk",
            phone = "+92 300 1122334",
            location = "Karachi, Pakistan",
            summaryText = "HEC-Recognized Assistant Professor with 7+ years of experience teaching Computer Science and Information Technology. Expert in curricular development, journal research, and student thesis review.",
            workExperiences = listOf(
                ResumeWorkHistory("Assistant Professor (IT Dept)", "University of Karachi", "2022 - Present", "Taught Advanced Mobile Engineering and Machine Learning structures to 200+ undergraduate majors annually.", "Published 4 research manuscripts in HEC-recognized high-impact medical-AI diagnostics journals."),
                ResumeWorkHistory("Lecturer / Senior Instructor", "NED University of Engineering", "2019 - 2022", "Supervised 12 final year project groups focused on web-based POS caches and regional Android applications.", "Managed departmental course compliance documents and standardized laboratory assignments.")
            ),
            academicList = listOf(
                ResumeAcademic("PhD in Computer Science", "NUST Islamabad", "2015 - 2018", "Distinguished Research Scholar Award")
            ),
            projectsList = listOf(
                ResumeProject("Academic Thesis Evaluator", "Django ML Hub", "github.com/fahad/thesis-eval", "Constructed automated lexical validation engines screening student resumes against local thesis templates.")
            ),
            skillsCsv = "Curriculum Alignment, Academic Publishing, Thesis Supervision, Machine Learning, Jetpack Compose UI",
            selectedAccentColorHex = "#7C3AED",
            selectedTemplateTheme = "The Ivy League Serif",
            selectedTypography = "Classic Serif"
        ),
        ResumePresetDetail(
            id = "css_officer",
            label = "🏛️ CSS / PMS AC Officer",
            category = "Business",
            fullName = "Muhammad Usman, PMS / CSS",
            headline = "Assistant Commissioner / Executive Civil Servant",
            email = "usman.css@dgpr.punjab.gov.pk",
            phone = "+92 312 3344556",
            location = "Lahore, Pakistan",
            summaryText = "Accomplished CSS/PMS Officer with active competence in public administration, municipal finance control, law and order maintenance, and rural development execution. Strong advocate for digital e-governance.",
            workExperiences = listOf(
                ResumeWorkHistory("Assistant Commissioner (AC / UT)", "District Administration Lahore", "2023 - Present", "Managed administrative and judicial oversight of local municipal subdivisions, addressing citizen grievances.", "Spearheaded digitizing land revenue verification cycles across 14 village registries."),
                ResumeWorkHistory("Section Officer (Establishment)", "Punjab Services & General Admin", "2021 - 2023", "Coordinated departmental civil service record clearances and audited local procurement expenditures.", "Formulated policy drafts for online district-level emergency medical response systems.")
            ),
            academicList = listOf(
                ResumeAcademic("Master in Public Administration (MPA)", "Quaid-e-Azam University", "2018 - 2020", "First Division Honors")
            ),
            projectsList = listOf(
                ResumeProject("Digital District Portal", "E-Gov Initiative", "lahore.punjab.gov.pk", "Implemented dashboard reporting loops updating regional fertilizer distribution quotas transparently.")
            ),
            skillsCsv = "Public Administration, Municipal Budget Auditing, Land Revenue Code, Civic Relations, E-Governance Systems",
            selectedAccentColorHex = "#1E293B",
            selectedTemplateTheme = "Australia Professional",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "bank_cashier",
            label = "🎟️ Bank Operations Officer",
            category = "Business",
            fullName = "Kashif Bilal Sheikh",
            headline = "Branch Operations Officer & Retail Cashier",
            email = "kashif.sheikh@hbl-branch.com",
            phone = "+92 321 8899776",
            location = "Peshawar, Pakistan",
            summaryText = "Certified Bank Cashier and Branch Operations specialist with 4+ years of retail banking experience. Highly skilled in high-volume cash disbursement, multi-currency ledger matching, and state compliance laws.",
            workExperiences = listOf(
                ResumeWorkHistory("Branch Operations Officer", "Habib Bank Limited (HBL)", "2022 - Present", "Balanced branch safe margins and supervised daily ATM liquid cash replenishments with 100% precision.", "Led foreign remittance validations and resolved commercial customer account discrepancies."),
                ResumeWorkHistory("Cash Officer / Cashier", "National Bank of Pakistan (NBP)", "2020 - 2022", "Handled customer draft creations, utility tax collection registries, and general check verification balances.", "Achieved 'Zero Teller Variance' recognition across consecutive quarters handling complex cash transactions.")
            ),
            academicList = listOf(
                ResumeAcademic("Bachelor of Commerce (BCom)", "University of Peshawar", "2016 - 2020", "First Class Division")
            ),
            projectsList = listOf(
                ResumeProject("ATM Flow Optimization", "Internal Analysis", "hbl-pesh.com/atm-study", "Drafted queue management models that reduced branch check-out wait-times from 12 mins to 5.5 mins.")
            ),
            skillsCsv = "Cash Operations, Core Banking Systems, AML Validation, Customer Conflict Resolution, Financial Ledger Auditing",
            selectedAccentColorHex = "#0D9488",
            selectedTemplateTheme = "Executive Slate Midnight",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "pk_software_eng",
            label = "📱 Senior Android (Arfa STP)",
            category = "Tech",
            fullName = "Arslan Siddique",
            headline = "Senior Android Engineer - Arfa Software Tech Park",
            email = "arslan.sidd@systems-ltd.com",
            phone = "+92 334 5566778",
            location = "Lahore, Pakistan",
            summaryText = "Experienced Android developer centered at Arfa Software Technology Park. Specializes in building modular Jetpack Compose applications, Room database offline sync, and REST client-server engineering.",
            workExperiences = listOf(
                ResumeWorkHistory("Senior Android Developer", "Systems Limited - Lahore", "2023 - Present", "Engineered the core offline synchronization module for a major telecom application, serving 5M+ subscribers.", "Integrated local Room database layers to cache customer billing histories, reducing network calls by 40%."),
                ResumeWorkHistory("Junior Android Engineer", "NetSol Technologies", "2021 - 2023", "Maintained 99.9% crash-free sessions by writing extensive automated Unit Tests using Robolectric.", "Refactored legacy XML layouts into modern, fluid Material 3 Compose screens.")
            ),
            academicList = listOf(
                ResumeAcademic("BS in Computer Science", "FAST-NUCES Lahore", "2017 - 2021", "Dean's List Scholar | 3.68 GPA")
            ),
            projectsList = listOf(
                ResumeProject("Offline Micro-Finance Tracker", "App Showcase", "github.com/arslan/micro-finance", "Built an offline-first financial logging application for rural farmers utilizing local SQLite persistence.")
            ),
            skillsCsv = "Android SDK, Jetpack Compose, Kotlin Coroutines, Room DB, Git, Multi-Threading, REST APIs",
            selectedAccentColorHex = "#059669",
            selectedTemplateTheme = "Creative Emerald Garden",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "graphic_designer",
            label = "🎨 UI/UX Freelance Designer",
            category = "Tech",
            fullName = "Zohaib Hassan",
            headline = "Top-Rated Plus UI/UX Freelance Designer",
            email = "zohaib.designs@fiverr-top.com",
            phone = "+92 316 1122445",
            location = "Faisalabad, Pakistan",
            summaryText = "Highly innovative Freelance Designer and UI developer. Delivered 300+ custom brand assets, mobile prototypes, and scalable vector packages with 100% positive client feedback globally.",
            workExperiences = listOf(
                ResumeWorkHistory("Top-Rated UI/UX Freelancer", "Upwork & Fiverr Platforms", "2022 - Present", "Designed high-converting landing pages and vector UI mockups for 120+ international startup SaaS companies.", "Collaborated directly with client product managers to define consistent design system coordinates."),
                ResumeWorkHistory("Visual Designer", "Arbisoft - Lahore Office", "2020 - 2022", "Crafted cross-platform vector marketing kits and designed bespoke launcher vector icon matrices.", "Ensured consistent dynamic accessibility contrast colors across web portals and mobile templates.")
            ),
            academicList = listOf(
                ResumeAcademic("Bachelor of Fine Arts (BFA in Design)", "National College of Arts (NCA) Lahore", "2016 - 2020", "Distinct Merit Award")
            ),
            projectsList = listOf(
                ResumeProject("Material-3 Infinite Icon Library", "Figma Asset Hub", "figma.com/@zohaib-infinite", "Published custom responsive vector icons downloaded over 15K times in global builder design lists.")
            ),
            skillsCsv = "UI/UX Prototyping, Figma Design Systems, Vector Brand Assets, Material-3 Theme Design, Adobe Creative Cloud",
            selectedAccentColorHex = "#DB2777",
            selectedTemplateTheme = "Creative Emerald Garden",
            selectedTypography = "Creative Elegant"
        ),
        ResumePresetDetail(
            id = "content_writer",
            label = "✍️ SEO Content Specialist",
            category = "Tech",
            fullName = "Amina Yousuf Malik",
            headline = "Lead SEO Content Specialist & AI Prompt Writer",
            email = "amina.yousuf@wordcraft.pk",
            phone = "+92 323 4455667",
            location = "Rawalpindi, Pakistan",
            summaryText = "Expert Content Writer specializing in technical search engine optimization (SEO), data-driven copywriting, and tuning machine learning context prompts to generate engaging marketing materials.",
            workExperiences = listOf(
                ResumeWorkHistory("Lead SEO Copywriter", "Symmetry Digital - Karachi Office", "2023 - Present", "Engineered and optimized 200+ blog articles, ranking 45% of target keywords on Page 1 Google searches.", "Configured AI automated content brief builders, saving 20 writing team hours weekly."),
                ResumeWorkHistory("Freelance Ghostwriter", "Fiverr / Upwork Platform", "2021 - 2023", "Authored 30+ technical White Papers on blockchain and financial security setups for overseas startups.", "Conducted comprehensive keyword research audits using SEMrush and Ahrefs platforms.")
            ),
            academicList = listOf(
                ResumeAcademic("M.A. in English Literature", "Fatima Jinnah Women University", "2019 - 2021", "First Division | CGPA 3.74")
            ),
            projectsList = listOf(
                ResumeProject("AI Blog Keyword Injector", "Python & Streamlit", "github.com/amina/writer-ai", "Coded interactive UI panels that analyze textual draft fields to recommend context-relevant LSI terms.")
            ),
            skillsCsv = "SEO Content Strategy, Technical Writing, Keyword Analysis, Ahrefs & SEMrush, Prompt Engineering",
            selectedAccentColorHex = "#4B5563",
            selectedTemplateTheme = "The Ivy League Serif",
            selectedTypography = "Classic Serif"
        ),
        ResumePresetDetail(
            id = "call_center",
            label = "🛎️ Customer Call Support",
            category = "Services",
            fullName = "Sufyan Ahmed Hashmi",
            headline = "Senior Customer Support Executive - Ibex Pakistan",
            email = "sufyan.hashmi@ibex.co",
            phone = "+92 301 9898776",
            location = "Karachi, Pakistan",
            summaryText = "Performance-driven Senior Call Center Agent with 5+ years of experience managing high-tier international telecom accounts. Consistent top-scorer in First Contact Resolution (FCR) metrics.",
            workExperiences = listOf(
                ResumeWorkHistory("Customer Operations Lead", "Ibex Global Pakistan", "2023 - Present", "Resolved 80+ customer phone escalations daily, maintaining a constant 98% customer satisfaction (CSAT) rating.", "Coached a team of 15 new hire agents on telephonic outbound compliance guidelines and active listening."),
                ResumeWorkHistory("BPO Support Agent", "Mindbridge Private Ltd", "2020 - 2023", "Handled live customer chats and billing tickets, minimizing average handling times from 8 to 4 minutes.", "Logged user bugs securely into CRM platforms and coordinated follow-ups with technical teams.")
            ),
            academicList = listOf(
                ResumeAcademic("Bachelor of Science (General Science)", "University of Karachi", "2017 - 2020", "First Division")
            ),
            projectsList = listOf(
                ResumeProject("Interactive Customer Response Flow", "CRM Optimizer", "github.com/sufyan/crm-flow", "Authored standard operating scripts for handling angry customer responses, adopted branch-wide.")
            ),
            skillsCsv = "International Client Support, FCR Optimization, CSAT Improvement, Active Listening, CRM Salesforce",
            selectedAccentColorHex = "#2563EB",
            selectedTemplateTheme = "Executive Slate Midnight",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "sub_engineer",
            label = "🛣️ Civil Sub-Engineer",
            category = "Engineering",
            fullName = "Nabeel Raza Abbasi",
            headline = "Sub-Engineer (Civil) - Communication & Works Dept",
            email = "nabeel.abbasi@cw.punjab.gov.pk",
            phone = "+92 322 9900881",
            location = "Rawalpindi, Pakistan",
            summaryText = "Certified Sub-Engineer with 5+ years of experience overseeing site grading, government highway layout supervision, and strict material standard certifications across public projects.",
            workExperiences = listOf(
                ResumeWorkHistory("Sub-Engineer (Civil - Grade 14)", "Communication & Works (C&W) Punjab", "2022 - Present", "Supervised on-site concrete casting and paving quality check routines for a 12KM rural development road sector.", "Audited weekly material inventory ledger balances to guarantee procurement compliance codes."),
                ResumeWorkHistory("Civil Site Supervisor", "NESPAK Private Limited", "2020 - 2022", "Inspected steel rebar structural dimensions against CAD blueprints for urban drainage utility projects.", "Drafted structural status spreadsheets and logged hourly machinery utilization logs.")
            ),
            academicList = listOf(
                ResumeAcademic("Diploma of Associate Engineering (DAE Civil)", "Punjab Board of Technical Education", "2017 - 2020", "Distinction Grade | Gold Medalist")
            ),
            projectsList = listOf(
                ResumeProject("Silt Volume Calculation Grids", "Field Excel Engine", "github.com/nabeel/silt-estimator", "Developed automated spreadsheets to calculate volume displacements of concrete and aggregate on site.")
            ),
            skillsCsv = "Site Layout Surveying, Material Verification, CAD Drafting, Road Construction, Cost Bill Preparation",
            selectedAccentColorHex = "#854D0E",
            selectedTemplateTheme = "UAE Modern Grid",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "electrical_eng",
            label = "⚡ Grid Operations",
            category = "Engineering",
            fullName = "Irshad Ahmad",
            headline = "Distribution Operations Engineer - K-Electric",
            email = "irshad.ahmad@ke.com.pk",
            phone = "+92 334 1122998",
            location = "Karachi, Pakistan",
            summaryText = "Electrical Distribution Engineer with 6+ years of expertise in grid operations, high-voltage equipment layouts, and executing reactive maintenance plans under high-stress environments.",
            workExperiences = listOf(
                ResumeWorkHistory("Distribution Operations Engineer", "K-Electric Limited", "2022 - Present", "Supervised restoration schedules of 11KV distribution feeders during monsoon emergencies, minimizing downtime.", "Coordinated thermal imaging scanning of substation bus-bars to preempt structural equipment failure points."),
                ResumeWorkHistory("Sub-Station Maintenance Engineer", "WAPDA / LESCO - Lahore", "2019 - 2022", "Conducted weekly testing of high-voltage circuit breakers and checked transformer liquid cooling margins.", "Enforced strict high-voltage rubber glove and safety harness protocols, retaining a zero-incident safety record.")
            ),
            academicList = listOf(
                ResumeAcademic("BE in Electrical Engineering", "NED University of Engineering", "2015 - 2019", "CGPA 3.78 | PEC Registered")
            ),
            projectsList = listOf(
                ResumeProject("Feeder Smart Grid Telemetry", "MATLAB System", "github.com/irshad/feeder-telemetry", "Simulated responsive reactive power-factor controllers in MATLAB to optimize grid line transmission losses.")
            ),
            skillsCsv = "High Voltage Operations, Radial Feeder Maintenance, SCADA Dashboards, PEC Electrical Code, Safe Isolation Plans",
            selectedAccentColorHex = "#991B1B",
            selectedTemplateTheme = "Australia Professional",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "medical_officer",
            label = "⚕️ Registrar Medical Officer",
            category = "Health",
            fullName = "Dr. Faisal Farooq, MBBS",
            headline = "Registrar Medical Officer & Primary Care Doctor",
            email = "faisal.farooq@jinnah-hosp.org",
            phone = "+92 312 4455661",
            location = "Lahore, Pakistan",
            summaryText = "PMDC licensed Medical Officer with 5+ years of practical emergency room and internal medicine rotation history. Committed to preventative medicine and clinical workflow optimization.",
            workExperiences = listOf(
                ResumeWorkHistory("Registrar Medical Officer (MO)", "Jinnah Hospital Lahore", "2023 - Present", "Diagnosed and formulated patient treatment plans for 60+ daily walk-ins in the general outpatient wing.", "Managed emergency cardiac support teams and standardized local patient diagnostic histories."),
                ResumeWorkHistory("Emergency Medical Intern", "Mayo Hospital Lahore", "2021 - 2023", "Administered acute critical care, managed fluid resuscitations, and executed minor surgical suturing in trauma bays.", "Spearheaded basic medical safety training sessions for 40+ medical college interns.")
            ),
            academicList = listOf(
                ResumeAcademic("Bachelor of Medicine, Bachelor of Surgery (MBBS)", "King Edward Medical University", "2015 - 2021", "Honors in Pharmacology | PMDC Registered")
            ),
            projectsList = listOf(
                ResumeProject("Outpatient Triage Redesign", "Workflow Study", "kemcolian.edu/triage-study", "Restructured OPD patient queuing schemas to reduce average referral delay times by 20%.")
            ),
            skillsCsv = "OPD Medical Assessment, Acute Resuscitation, Cardiac Life Support, Electronic Health Records, Minor Surgery",
            selectedAccentColorHex = "#9F1239",
            selectedTemplateTheme = "The Ivy League Serif",
            selectedTypography = "Classic Serif"
        ),
        ResumePresetDetail(
            id = "pharmacist",
            label = "🧪 Fazal Din Retail Pharmacist",
            category = "Health",
            fullName = "Sarmad Siddiqui, PharmD",
            headline = "Consultant Clinical Pharmacist & Retail Manager",
            email = "sarmad.sidd@fazaldin.com",
            phone = "+92 345 1122334",
            location = "Lahore, Pakistan",
            summaryText = "Licensed Doctor of Pharmacy (PharmD) with 6 years of expertise in retail pharmacy management. Proven track record in drug interaction audits, FDA/DRAP regulations, and wellness coaching.",
            workExperiences = listOf(
                ResumeWorkHistory("Retail Pharmacy Manager", "Fazal Din & Sons Chemists", "2022 - Present", "Supervised drug inventory operations valued at $120K, ensuring total compliance with DRAP vaccine storage standards.", "Analyzed patient dosage frequencies and counselled customers on chronic drug interaction side-effects."),
                ResumeWorkHistory("Clinical Ward Pharmacist", "Shalamar Hospital", "2019 - 2022", "Reviewed inpatient drug prescription files in the cardiac ward to prevent double-dosing and high risk interactions.", "Collaborated in formulating hospital formulary updates and presented clinical trials summaries.")
            ),
            academicList = listOf(
                ResumeAcademic("Doctor of Pharmacy (PharmD)", "University of Lahore", "2014 - 2019", "First Division | Top 5% Rank")
            ),
            projectsList = listOf(
                ResumeProject("DRAP Storage Calibration", "Compliance Auditing", "fazaldin.com/drap-standards", "Standardized computerized temperature monitors across remote storage refrigerators, safeguarding vaccine stock.")
            ),
            skillsCsv = "DRAP Pharmacy Regulations, Medication Counseling, Vaccine Cold Chain, Drug Interaction Screening, POS Operations",
            selectedAccentColorHex = "#059669",
            selectedTemplateTheme = "Creative Emerald Garden",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "audit_associate",
            label = "📝 Audit Associate",
            category = "Business",
            fullName = "Tayyaba Naeem, ACCA",
            headline = "Senior Audit Associate - EY Pakistan",
            email = "tayyaba.naeem@pk.ey.com",
            phone = "+92 316 7788990",
            location = "Islamabad, Pakistan",
            summaryText = "Qualified ACCA Affiliate with 4+ years of external auditing experience across national banking portfolios. Expert in dynamic financial Excel models, IFRS standards, and internal risk metrics.",
            workExperiences = listOf(
                ResumeWorkHistory("Senior Audit Associate", "EY Ford Rhodes - Islamabad Office", "2023 - Present", "Executed comprehensive balance sheet and tax auditing routines for major telecom and manufacturing clients.", "Identified internal control vulnerabilities on asset records, yielding cost savings of $20K."),
                ResumeWorkHistory("Audit Intern", "A.F. Ferguson & Co. (PwC PK)", "2021 - 2023", "Vouched transaction listings against cash vouchers and verified bank reconciliation statements.", "Prepared draft financial reports and audited state-level procurement receipts.")
            ),
            academicList = listOf(
                ResumeAcademic("ACCA Affiliate Qualification", "Association of Chartered Certified Accountants", "2018 - 2022", "NBP Academic Excellence Sponsor")
            ),
            projectsList = listOf(
                ResumeProject("IFRS-9 Local Implementation", "Spreadsheet Kit", "github.com/tayyaba/ifrs9-tool", "Engineered parameterized spreadsheets calculating expected credit loss patterns across credit cards.")
            ),
            skillsCsv = "External Financial Auditing, IFRS Standards, Corporate Taxation, Excel Macro Systems, Risk Assessment",
            selectedAccentColorHex = "#475569",
            selectedTemplateTheme = "Executive Slate Midnight",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "marketing_exec",
            label = "📈 Growth Marketer",
            category = "Business",
            fullName = "Hamza Ali Zuberi",
            headline = "Growth Marketing Lead - Daraz PK",
            email = "hamza.zuberi@daraz.com",
            phone = "+92 333 4455112",
            location = "Karachi, Pakistan",
            summaryText = "Data-driven Growth Marketer specializing in organic user acquisition, localized digital ad funnels, and managing high-tier e-commerce marketing lists. Direct executor of budget plans.",
            workExperiences = listOf(
                ResumeWorkHistory("Growth Marketing Executive", "Daraz Pakistan", "2023 - Present", "Coordinated 11.11 localized digital ad campaigns, improving average CTR levels by 24% on social media funnels.", "Analyzed post-campaign buyer cohorts, formulating structured email retention guidelines."),
                ResumeWorkHistory("Digital Associate", "Foodpanda Pakistan", "2021 - 2023", "Optimized push notifications copy vectors across localized cities, boosting food delivery orders by 15%.", "Managed weekly marketing budgets and compiled performance metrics grids for regional management panels.")
            ),
            academicList = listOf(
                ResumeAcademic("BS in Business & Media Science", "SZABIST Karachi", "2017 - 2021", "First Division | Gold Medalist")
            ),
            projectsList = listOf(
                ResumeProject("Daraz Banner Heatmap Pilot", "Figma Tracking Study", "daraz.com/growth/banner-study", "Used custom interactive heatmaps to study banner placement click metrics across desktop interfaces.")
            ),
            skillsCsv = "Digital Growth Funnels, Google Adwords, Cohort Email Retention, Budget Management, Copywriting Vectors",
            selectedAccentColorHex = "#EA580C",
            selectedTemplateTheme = "Creative Emerald Garden",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "logistics_lead",
            label = "📦 Logistics Lead",
            category = "Business",
            fullName = "Taimoor Shah Malik",
            headline = "Regional Logistics Fleet Coordinator - TCS",
            email = "taimoor.shah@tcs.com.pk",
            phone = "+92 321 4455883",
            location = "Peshawar, Pakistan",
            summaryText = "Supply Chain and Logistics professional with years of expert experience managing regional fleet route logistics, package sorting centers, and custom warehouse dispatch ledger systems.",
            workExperiences = listOf(
                ResumeWorkHistory("Regional Fleet Coordinator", "TCS Express Private Limited", "2023 - Present", "Optimized hub-to-hub delivery schedules across KP province, lowering average fuel consumption costs by 18%.", "Supervised a regional team of 45 delivery captains and solved dispatch routing errors in real time."),
                ResumeWorkHistory("Dispatch Officer", "M&P Express Logistics", "2021 - 2023", "Managed computerized parcel classification ledgers and standardized shipment scanning logs.", "Resolved local customer parcel claims and investigated transit delay issues.")
            ),
            academicList = listOf(
                ResumeAcademic("BBA in Supply Chain Management", "FAST-NUCES Peshawar", "2017 - 2021", "CGPA 3.62")
            ),
            projectsList = listOf(
                ResumeProject("Route Consolidation Model", "GIS Mapping", "github.com/taimoor/gis-routes", "Programmed GIS coordinate scripts in Python that cluster delivery coordinates to recommend short route paths.")
            ),
            skillsCsv = "Supply Chain Logistics, Fleet Fleet Operations, Hub Operations, Route Optimization, Excel Dispatch Modeling",
            selectedAccentColorHex = "#D97706",
            selectedTemplateTheme = "UAE Modern Grid",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "solar_technician",
            label = "☀️ Solar Grid Lead Installer",
            category = "Engineering",
            fullName = "Adeel Mukhtar",
            headline = "Lead Solar Energy System Installer",
            email = "adeel.solar@sunpower.pk",
            phone = "+92 315 2233445",
            location = "Multan, Pakistan",
            summaryText = "Solar Grid specialist with comprehensive field experience deploying industrial and residential solar systems, configuring smart inverters, and conducting net metering compliance reviews.",
            workExperiences = listOf(
                ResumeWorkHistory("Lead Solar Installer", "Sunpower Technologies Multan", "2022 - Present", "Completed 80+ high-capacity residential and industrial solar module installations, totaling 4MW of pure green energy.", "Configured hybrid and off-grid smart inverters and resolved voltage imbalance fault errors."),
                ResumeWorkHistory("PV Field Technician", "Reon Energy Limited", "2020 - 2022", "Assembled solar array mounting brackets on complex industrial rooftops and conducted wiring checks.", "Maintained comprehensive safety logsheets, avoiding electric shock accidents across 100% of sites.")
            ),
            academicList = listOf(
                ResumeAcademic("Diploma in Electrical Technology", "GCT Multan", "2017 - 2020", "First Division honors")
            ),
            projectsList = listOf(
                ResumeProject("Net Metering Blueprint Kit", "Grid Compliance Guide", "sunpower.pk/net-metering-docs", "Designed a step-by-step documentation packet for residential client net metering submissions to regional DISCOs.")
            ),
            skillsCsv = "Photovoltaic (PV) Assemblies, Smart Inverter Configuration, Voltmeter Fault Analysis, Net Metering Compliance, Safety Rules",
            selectedAccentColorHex = "#B35300",
            selectedTemplateTheme = "UAE Modern Grid",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "agri_officer",
            label = "🌱 Agricultural Extension Officer",
            category = "Engineering",
            fullName = "Rana Amjad Shahzad",
            headline = "Agricultural Extension Officer - Punjab Agri Dept",
            email = "rana.amjad@punjab-agri.gov.pk",
            phone = "+92 300 4455881",
            location = "Faisalabad, Pakistan",
            summaryText = "Dedicated Agronomist and extension officer with extensive field experience coordinating soil nutritional diagnostic tours, crop pest modeling, and training rural farming communities.",
            workExperiences = listOf(
                ResumeWorkHistory("Agricultural Officer (Extension)", "Punjab Agriculture Department", "2021 - Present", "Delivered 120+ rural farmer advisory workshops regarding optimized crop fertilizer and watering schedules.", "Managed district-level wheat seed subsidies lists and resolved farmer program disputes."),
                ResumeWorkHistory("Crop Health Consultant", "ICI Pakistan Agrochemicals", "2019 - 2021", "Diagnosed cotton crop pest infestations and designed fertilizer mixture prescriptions for commercial farms.", "Compiled weekly crop health status reports and monitored regional soil test benchmarks.")
            ),
            academicList = listOf(
                ResumeAcademic("BSc (Hons) in Agriculture (Agronomy)", "University of Agriculture Faisalabad", "2015 - 2019", "CGPA 3.84 | Gold Medalist")
            ),
            projectsList = listOf(
                ResumeProject("Alkali Soil Remediation Kit", "Extension Program", "uaf.edu.pk/soil-remedy-rana", "Structured low-cost saline soil rehabilitation guidelines, elevating crop yield by 30% for 50 participating farms.")
            ),
            skillsCsv = "Soil Nutrition Diagnostics, Crop Protection Chemistry, Pest Management, Agraria Extensions, Farm Budgeting",
            selectedAccentColorHex = "#15803D",
            selectedTemplateTheme = "Canada Academic Standard",
            selectedTypography = "Sharp Sans-Serif"
        ),
        ResumePresetDetail(
            id = "real_estate",
            label = "🏡 Property Sales",
            category = "Business",
            fullName = "Chaudhary Shahnawaz",
            headline = "Prime Property Sales Consultant - DHA & Bahria",
            email = "shahnawaz.estate@dha-invest.com",
            phone = "+92 321 1122335",
            location = "Lahore, Pakistan",
            summaryText = "Elite Real Estate Consultant with 8+ years of expertise in prime residential and commercial property investments across DHA, Bahria Town, and LDA layouts. Strong sales closer and portfolio manager.",
            workExperiences = listOf(
                ResumeWorkHistory("Senior Sales Consultant", "DHA Property Advisors Lahore", "2021 - Present", "Closed $4.5M in high-value residential plot transactions, securing consistent top sales rankings.", "Formulated comprehensive property investment risk analysis slides for overseas clientele."),
                ResumeWorkHistory("Sales Representative", "Bahria Town Realtors", "2018 - 2021", "Prepared legal booking folders and managed commercial property tours for 300+ potential investors.", "Compiled property valuation catalog sheets and negotiated optimal purchasing rates.")
            ),
            academicList = listOf(
                ResumeAcademic("MBA in Sales & Marketing", "LUMS Lahore", "2015 - 2017", "First Class Honors")
            ),
            projectsList = listOf(
                ResumeProject("Overseas Investment Webinar Portal", "Digital Sales Campaign", "dha-invest.com/overseas-webinar", "Organized digital property investment seminars that secured 45 new direct client registrations.")
            ),
            skillsCsv = "Property Valuation, High-Value Sales, DHA Corporate Transfer, Customer Relationship Management, Negotiations",
            selectedAccentColorHex = "#1E293B",
            selectedTemplateTheme = "Australia Professional",
            selectedTypography = "Sharp Sans-Serif"
        )
    )

    val allPresets: List<ResumePresetDetail> by lazy {
        val list = mutableListOf<ResumePresetDetail>()
        list.addAll(manualPresets)

        val categories = listOf("Tech", "Health", "Education", "Business", "Engineering", "Creative", "Aviation", "Legal", "Govt", "Services")

        for (cat in categories) {
            val roles = when (cat) {
                "Tech" -> techRoles
                "Health" -> healthRoles
                "Education" -> educationRoles
                "Business" -> businessRoles
                "Engineering" -> engineeringRoles
                "Creative" -> creativeRoles
                "Aviation" -> aviationRoles
                "Legal" -> legalRoles
                "Govt" -> govtRoles
                "Services" -> servicesRoles
                else -> emptyList()
            }

            roles.forEachIndexed { idx, role ->
                val id = "${cat.lowercase()}_intl_${idx}"
                list.add(generateIntlPreset(id, role, cat, idx))
            }
        }
        list
    }

    fun getPresetOptions(): List<PresetOption> {
        return allPresets.map { PresetOption(it.id, it.label, it.category) }
    }

    fun getPresetById(id: String): ResumePresetDetail? {
        return allPresets.find { it.id == id }
    }

    private fun generateIntlPreset(id: String, role: String, cat: String, idx: Int): ResumePresetDetail {
        val name = intlNames[idx % intlNames.size]
        val (loc, phone) = intlLocs[idx % intlLocs.size]
        val uni = intlUnis[idx % intlUnis.size]
        val email = "${name.lowercase().replace(" ", ".")}@intl-hubs.org"

        val (accent, theme, typo) = when (cat) {
            "Tech" -> Triple("#0D9488", "Creative Emerald Garden", "Sharp Sans-Serif")
            "Health" -> Triple("#9F1239", "The Ivy League Serif", "Classic Serif")
            "Education" -> Triple("#D97706", "Canada Academic Standard", "Sharp Sans-Serif")
            "Business" -> Triple("#4C1D95", "Executive Slate Midnight", "Sharp Sans-Serif")
            "Engineering" -> Triple("#1E293B", "UAE Modern Grid", "Sharp Sans-Serif")
            "Creative" -> Triple("#DB2777", "Creative Emerald Garden", "Creative Elegant")
            "Aviation" -> Triple("#1E3A8A", "Australia Professional", "Sharp Sans-Serif")
            "Legal" -> Triple("#1E293B", "The Ivy League Serif", "Classic Serif")
            "Govt" -> Triple("#1E3A8A", "USA Executive Elite", "Sharp Sans-Serif")
            "Services" -> Triple("#0D9488", "Modern Blue Grid", "Sharp Sans-Serif")
            else -> Triple("#1E3A8A", "Modern Blue Grid", "Sharp Sans-Serif")
        }

        val summary = when (cat) {
            "Tech" -> "Meticulous $role with extensive expertise designing secure distributed networks, custom API systems, and robust client interfaces. Committed to code quality, high scalability, and clean modular designs."
            "Health" -> "Dedicated $role with clinical rotation experience in leading regional hospitals. Focused on medical diagnostics accuracy, patient-centric healthcare operations, and implementing ISO-certified biosafety standards."
            "Education" -> "Compassionate $role specializing in early childhood learning progress, structured curriculum design, and universal physical/digital instruction configurations. Passionate about developmental education."
            "Business" -> "Strategic $role with a proven history of managing high-value asset portfolios, customer retention funnels, and corporate budget plans. Skilled in collaborative cross-functional leadership."
            "Engineering" -> "Certified $role with field experience analyzing complex structural blueprints, deploying grid electrical lines, and verifying material tensile safety ratios compliant with BS/Eurocodes."
            "Creative" -> "Award-winning $role specializing in drafting vector branding assets, custom layouts, and interactive media pipelines. Expert in Adobe Creative Cloud and standard 3D rendering engines."
            "Aviation" -> "Highly disciplined $role with deep competency coordinating fleet route scheduling, cargo ground safety logistics, and strict international compliance standards under regional civil aviation authorities."
            "Legal" -> "Detail-oriented $role with specialized practical knowledge in drafting commercial contracts, auditing regulatory filings, and managing intellectual property patent catalogs cleanly."
            "Govt" -> "Experienced public servant working as a $role to execute administrative policies, coordinate civic outreach programs, and implement digital e-governance systems to benefit citizens."
            "Services" -> "Service-driven $role specializing in configuring luxury accommodation checklists, organizing event layouts, and maximizing guest satisfaction ratings across global networks."
            else -> "Accomplished $role with a successful record of high performance, collaboration, and continuous professional development."
        }

        val comp1 = when (cat) {
            "Tech" -> "Nexus Software Global"
            "Health" -> "Metropolitan Medical Center"
            "Education" -> "Beacon International School"
            "Business" -> "Apex Advisory Partners"
            "Engineering" -> "Vanguard Infrastructure Corp"
            "Creative" -> "Stellar Visuals Agency"
            "Aviation" -> "Emirates Transit Hub"
            "Legal" -> "Lexington Law Associates"
            "Govt" -> "Department of Regional Administration"
            "Services" -> "Hilton Luxury Group"
            else -> "Standard Global Enterprise"
        }

        val comp2 = when (cat) {
            "Tech" -> "Quantum Tech Labs"
            "Health" -> "St. Jude Clinic Sector"
            "Education" -> "Universal Learning Academy"
            "Business" -> "Vertex Product Incubators"
            "Engineering" -> "Pioneer Engineering Consult"
            "Creative" -> "Prism Media Solutions"
            "Aviation" -> "Sovereign Maritime Agency"
            "Legal" -> "Alliance Corporate Counsel"
            "Govt" -> "Municipal Service Committee"
            "Services" -> "Serene Hospitality Group"
            else -> "Altis Local Services"
        }

        val duty1_1 = when (cat) {
            "Tech" -> "Architected highly scalable cloud clusters and reduced REST backend latencies by 35%."
            "Health" -> "Diagnosed and formulated patient recovery charts, processing 50+ cases daily with zero triage errors."
            "Education" -> "Designed adaptive lesson plans, integrating interactive digital metrics to trace early development."
            "Business" -> "Steered market analytics research portfolios, increasing quarterly customer acquisition rates by 18%."
            "Engineering" -> "Drafted concrete finite-element blueprints and supervised on-site civil structural pours."
            "Creative" -> "Produced rich vector marketing systems, securing consistent 100% brand consistency ratings."
            "Aviation" -> "Coordinated international flight schedules and ground crew deployments with strict punctuality levels."
            "Legal" -> "Drafted watertight commercial agreements and verified regulatory filing disclosures for audit teams."
            "Govt" -> "Administered municipal budget expenditures, migrating community filings to automated e-governance models."
            "Services" -> "Standardized custom front-desk service plans, boosting average guest satisfaction scores by 15.5%."
            else -> "Led team operational activities and executed project schedules on time and within budget allocations."
        }

        val duty1_2 = when (cat) {
            "Tech" -> "Integrated local SQLite database managers and authored automated unit tests to yield 99.9% crash-free sessions."
            "Health" -> "Supervised medical specimens pathology logging and calibrated sterile laboratory testing chambers."
            "Education" -> "Conducted weekly parent academic advisory circles and organized developmental science craft workshops."
            "Business" -> "Authored project feasibility grids and presented risk assessment plans directly to managing directors."
            "Engineering" -> "Conducted strict structural safety inspections conforming with regional civil construction regulations."
            "Creative" -> "Collaborated directly with client product managers to build fluid custom interactive UI wireframes."
            "Aviation" -> "Enforced strict cargo biohazard and weight isolation protocols under civil aviation rule mandates."
            "Legal" -> "Audited corporate asset records to prevent double licensing and conducted trademark overlap scans."
            "Govt" -> "Pioneered rural clean water resource tracking boards, coordinating directly with regional agencies."
            "Services" -> "Managed regional supplier booking invoices and coordinated custom menu/guest events."
            else -> "Collaborated with cross-functional partners to improve daily productivity benchmarks by 12%."
        }

        val duty2_1 = when (cat) {
            "Tech" -> "Assisted in code refactoring, converting legacy systems to modern Jetpack Compose layouts."
            "Health" -> "Administered patient wellness interviews and updated clinical tracking databases."
            "Education" -> "Instructed early year students in literacy and mathematics, receiving top coordinator ratings."
            "Business" -> "Compiled weekly ledger records and resolved client accounting discrepancies."
            "Engineering" -> "Maintained structural CAD archives and documented weekly site machinery checklogs."
            "Creative" -> "Designed promotional sketches and social media graphic assets for startup brands."
            "Aviation" -> "Monitored container security logs and updated daily dispatch charts."
            "Legal" -> "Researched legal precedents and archived local trial case history transcripts."
            "Govt" -> "Addressed civic complaints at public desks, resolving 90% of issues within initial cycles."
            "Services" -> "Handled VIP guest bookings and scheduled shift rotas for junior service teams."
            else -> "Assisted in daily operations, updating record files and compiling weekly spreadsheets."
        }

        val degree = when (cat) {
            "Tech" -> "Bachelor of Computer Science"
            "Health" -> "Bachelor of Medicine, Bachelor of Surgery"
            "Education" -> "Master of Arts in Education"
            "Business" -> "Master of Business Administration"
            "Engineering" -> "Bachelor of Engineering (Civil/Mech)"
            "Creative" -> "Bachelor of Fine Arts (Design)"
            "Aviation" -> "BSc in Logistics & Transport"
            "Legal" -> "Bachelor of Laws (LLB)"
            "Govt" -> "BSc in Public Administration"
            "Services" -> "Diploma in Hotel Management"
            else -> "Bachelor of Science"
        }

        val projTitle = when (cat) {
            "Tech" -> "Core REST Proxy Hub"
            "Health" -> "Triage Redesign Study"
            "Education" -> "Mobile Phonics App Kit"
            "Business" -> "Growth Campaign Flow"
            "Engineering" -> "Lateral Wind Shear Design"
            "Creative" -> "Responsive Icon Package"
            "Aviation" -> "Route Cluster Mapping"
            "Legal" -> "Asset Clearance System"
            "Govt" -> "E-Gov Citizen Portal"
            "Services" -> "Guest Feedback Engine"
            else -> "Efficiency Optimization Study"
        }

        val projStack = when (cat) {
            "Tech" -> "Kotlin, Ktor, Coroutines"
            "Health" -> "Clinical Data Module"
            "Education" -> "Interactive Flashcards"
            "Business" -> "Excel, CRM Salesforce"
            "Engineering" -> "ETABS, SAP2000 Pro"
            "Creative" -> "Adobe Creative Suite"
            "Aviation" -> "GIS Tracking Data"
            "Legal" -> "DocuSign, Legal DB"
            "Govt" -> "Secure Cloud Ledger"
            "Services" -> "CRM, POS Solutions"
            else -> "Data Analytics Spreadsheets"
        }

        val projImpact = when (cat) {
            "Tech" -> "Programmed secure routing servers handling 100K requests daily, reducing server timeouts."
            "Health" -> "Restructured emergency bay patient routing formulas, cutting average refer delays by 22%."
            "Education" -> "Created hand-drawn visual templates that raised child recognition scores in field studies."
            "Business" -> "Configured automated email retention flows, recovering 15% of cart dropouts."
            "Engineering" -> "Modeled shear dynamics of 40-story building columns against 140km/h stress limits."
            "Creative" -> "Developed consistent dynamic dark mode styling modules with high popularity indexes."
            "Aviation" -> "Plotted path clustering algorithms using relative coordinates, lowering fuel spend on routes."
            "Legal" -> "Archived legacy corporate filing indices, simplifying compliance verification times."
            "Govt" -> "Published interactive fertilizer distribution portals, ensuring transparent crop subsidies."
            "Services" -> "Led room inspection digitization pilots, accelerating guest check-in speeds by 30%."
            else -> "Successfully implemented procedural updates that saved 8 desk hours weekly."
        }

        val skills = when (cat) {
            "Tech" -> "Kotlin, Cloud Security, System Architectures, REST APIs, Git, Agile, Database Clustering"
            "Health" -> "Clinical Trials, Patient Diagnostics, EHR Systems, Pathology Specimens, Lab Biohazards"
            "Education" -> "Universal Lesson Planning, Development Phonics, Special Ed IEPs, STEM Instruction"
            "Business" -> "Agile PM, SaaS Sales, Corporate Finance, Risk Assessments, Brand Development, CRM"
            "Engineering" -> "BIM Structural, CAD, Eurocode Standards, Concrete Detailing, SCADA, Environmental Safety"
            "Creative" -> "UI/UX Figma Design, VFX Compositing, Adobe Suite, Vector Assets, 3D Rendering, Copywriting"
            "Aviation" -> "Flight Operations, Logistics Routing, Dangerous Cargo Codes, Air Ground Safety, GIS Mapping"
            "Legal" -> "Corporate Mergers, Patent Prosecution, Regulatory Filings, Trial Research, IFRS, Asset Escrow"
            "Govt" -> "Public Service, Municipal Budgets, Policy Drafting, Civic Relations, E-Gov Portal Systems"
            "Services" -> "Guest Relations Management, Event Catering, Hotel Operations, Luxury Concierge, POS Bookings"
            else -> "Problem Solving, Collaboration, Teamwork, Microsoft Office, Customer Service, Document Control"
        }

        val detailedJobDesc1 = when (cat) {
            "Tech" -> "Directed a high-performance agile engineering squad tasked with redesigning the primary cloud-native backend cluster, optimizing SQL indexing paths, and establishing automated CI/CD safety checks."
            "Health" -> "Managed inpatient diagnostics, directed emergency triage protocols, led multidisciplinary medical team alignment rounds, and calibrated clinical biosafety instruments."
            "Education" -> "Created progressive interactive science and technology lesson modules, oversaw parent-teacher academic review boards, and mentored junior educators in student-centered teaching."
            "Business" -> "Formulated critical brand expansion matrices, conducted extensive target market sizing audits, supervised client onboarding, and presented quarter-end growth data patterns to executive leaders."
            "Engineering" -> "Supervised complex multi-tier foundational site preparation, inspected finite-element tensile integrity metrics under code specifications, and signed off on quality audits."
            "Creative" -> "Oversaw creative execution of multi-platform branding catalogs, directed team designers on user experience layouts, and refined core vector icon libraries for major products."
            "Aviation" -> "Managed regional flight dispatch operations, supervised hangar compliance procedures, validated dangerous goods load sheets, and coordinated ground fuel optimization crews."
            "Legal" -> "Oversaw complex corporate restructuring contracts, analyzed intellectual property risks, drafted commercial purchase agreements, and conducted discovery research for complex trials."
            "Govt" -> "Coordinated regional community infrastructure allocations, administered public outreach programs, and drafted standard operating guides for newly integrated digital files platforms."
            "Services" -> "Supervised luxury reception teams, optimized VIP guest booking layouts, oversaw partner supplier billing chains, and resolved escalate service issues directly with management."
            else -> "Formulated and executed core operational schedules, oversaw team project assignments, tracked performance benchmarks, and streamlined resource distribution procedures."
        }

        val detailedJobDesc2 = when (cat) {
            "Tech" -> "Refactored legacy application features into modern clean-architecture Jetpack Compose modules, achieving substantial memory reductions and eliminating redundant thread queries."
            "Health" -> "Conducted comprehensive initial health examinations, updated digital clinical record databases, and supported laboratory pathology specimens packaging protocols."
            "Education" -> "Instructed fundamental math and literacy courses, configured interactive learning apps for children, and organized extracurricular cultural exhibitions."
            "Business" -> "Compiled weekly account reconciliation sheets, managed customer query pipelines, and resolved client contract issues."
            "Engineering" -> "Drafted high-precision structural CAD prints, updated daily maintenance checklists, and supervised site steel reinforcements spacing compliance."
            "Creative" -> "Created eye-catching social media marketing layouts, drafted user stories wireframe flows, and rendered custom 3D products visuals."
            "Aviation" -> "Logged strict flight manifest files, verified ground weight balances, and updated regional control tower dispatch logs."
            "Legal" -> "Conducted intellectual property trademarks risk checks, researched legal precedents, and categorized trial case history archives."
            "Govt" -> "Maintained community feedback registers, assisted citizens with administrative registration files, and supervised document validation queues."
            "Services" -> "Managed front desk service rotas, organized corporate dining banquets, and conducted daily hotel standard audits."
            else -> "Assisted in daily division workflows, updated team documentation materials, and prepared executive summary slides."
        }

        return ResumePresetDetail(
            id = id,
            label = "🌐 $role ($loc)",
            category = cat,
            fullName = name,
            headline = "$role | Professional Specialist",
            email = email,
            phone = phone,
            location = loc,
            summaryText = summary,
            workExperiences = listOf(
                ResumeWorkHistory("Lead $role", comp1, "2024 - Present", duty1_1, duty1_2, description = detailedJobDesc1),
                ResumeWorkHistory("Senior $role", comp2, "2021 - 2024", duty2_1, "Ensured operational alignment with international safety standards.", description = detailedJobDesc2)
            ),
            academicList = listOf(
                ResumeAcademic(degree, uni, "2017 - 2021", "CGPA 3.82 / 4.0 | Honors")
            ),
            projectsList = listOf(
                ResumeProject(projTitle, projStack, "github.com/${name.lowercase().replace(" ", "")}/$projTitle", projImpact)
            ),
            skillsCsv = skills,
            selectedAccentColorHex = accent,
            selectedTemplateTheme = theme,
            selectedTypography = typo
        )
    }
}
