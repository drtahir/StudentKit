package com.drtahir.studentkit.ui.screens

/**
 * HAJJ MEDICAL MISSION 1000 BANK - PART 3
 * Category: Vaccine & Outbreaks (30 100% Unique MCQs)
 * Covers Saudi mandatory vaccines, MERS-CoV surveillance, Meningococcal meningitis, Polio OPV, Cholera control & respiratory isolation protocols.
 */
object Hajj1000Part3 {

    fun getVaccineOutbreakQuestions(startId: Int): List<HajjQuestion> {
        val list = mutableListOf<HajjQuestion>()
        var idCounter = startId

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Which vaccine is strictly mandatory for all international Hajj pilgrims, with a requirement that the certificate be issued at least 10 days prior to arrival in Saudi Arabia?",
                options = listOf(
                    "Injectable Typhoid booster",
                    "Quadrivalent Meningococcal Conjugate Vaccine (ACYW135)",
                    "Rabies pre-exposure prophylaxis",
                    "Bacillus Calmette–Guérin (BCG) vaccine"
                ),
                correctIndex = 1,
                explanation = "Saudi Arabia mandates proof of Quadrivalent Meningococcal (ACYW135) vaccination administered at least 10 days before arrival (and within 3-5 years) to protect against explosive meningococcal meningitis outbreaks.",
                reference = "Saudi MOH Hajj & Umrah Health Entry Requirements"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What is the recommended antibiotic chemoprophylaxis for close tent contacts of a pilgrim diagnosed with confirmed Neisseria meningitidis meningitis?",
                options = listOf(
                    "Single oral dose of Ciprofloxacin 500 mg (or single IM dose of Ceftriaxone 250 mg)",
                    "7-day course of oral Amoxicillin 500 mg thrice daily",
                    "Immediate booster vaccination with Meningococcal vaccine",
                    "High-dose oral Paracetamol for 10 days"
                ),
                correctIndex = 0,
                explanation = "Close contacts of meningococcal cases require immediate chemoprophylaxis to eradicate nasopharyngeal carriage. A single dose of oral Ciprofloxacin 500 mg or IM Ceftriaxone 250 mg is highly effective.",
                reference = "CDC & WHO Guidelines for Meningococcal Outbreak Control"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Which zoonotic respiratory viral pathogen, first identified in Saudi Arabia in 2012, requires strict screening, isolation, and avoidance of contact with camels?",
                options = listOf(
                    "Avian Influenza A (H5N1)",
                    "Middle East Respiratory Syndrome Coronavirus (MERS-CoV)",
                    "Severe Acute Respiratory Syndrome (SARS-CoV-1)",
                    "Respiratory Syncytial Virus (RSV)"
                ),
                correctIndex = 1,
                explanation = "MERS-CoV is a coronavirus endemic to the Arabian Peninsula linked to dromedary camels. It causes severe acute respiratory distress, fever, pneumonia, and renal failure in mass gatherings.",
                reference = "WHO MERS-CoV Mass Gathering Surveillance Manual"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Why are all travelers arriving in Saudi Arabia from wild poliovirus-endemic countries (such as Pakistan and Afghanistan) required to receive a dose of Oral Polio Vaccine (OPV) at Saudi entry ports?",
                options = listOf(
                    "To generate commercial revenue for airport health posts",
                    "To prevent any potential reintroduction or transmission of wild poliovirus into the global crowd during Hajj",
                    "OPV is used as a general immune stimulant against travel fatigue",
                    "To replace the requirement for meningococcal vaccination"
                ),
                correctIndex = 1,
                explanation = "Pilgrims from polio-endemic regions must receive a booster dose of Oral Polio Vaccine (OPV) upon landing at Saudi ports of entry, regardless of age or past vaccination history, to preserve global polio eradication.",
                reference = "Saudi Port Health & WHO Travel Health Regulations"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What is the classic clinical triad of acute bacterial (meningococcal) meningitis that alerts a Hajj medical officer to enforce immediate droplet isolation?",
                options = listOf(
                    "Chronic productive cough, night sweats, and weight loss",
                    "Sudden high fever, severe headache, and nuchal rigidity (stiff neck), often accompanied by a petechial/purpuric skin rash",
                    "Watery diarrhea, abdominal cramps, and jaundice",
                    "Bilateral joint swelling, wrist drop, and facial palsy"
                ),
                correctIndex = 1,
                explanation = "The classic clinical triad is fever, headache, and nuchal rigidity. The rapid emergence of a non-blanching petechial or purpuric rash indicates fulminant meningococcemia, requiring immediate isolation and IV antibiotics.",
                reference = "Infectious Diseases Society of America (IDSA) Meningitis Guide"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "If an outbreak of acute painless, highly voluminous watery diarrhea resembling 'rice-water' occurs in a Mina tent sector, what pathogen is suspected?",
                options = listOf(
                    "Salmonella enteritidis",
                    "Vibrio cholerae (Cholera)",
                    "Entamoeba histolytica",
                    "Clostridium difficile"
                ),
                correctIndex = 1,
                explanation = "Rapidly spreading painless, profuse watery diarrhea ('rice-water stools') causing severe dehydration and hypovolemic shock within hours is classic for Vibrio cholerae infection.",
                reference = "WHO Cholera Epidemic Outbreak Manual"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What is the single most critical immediate therapeutic intervention for a pilgrim suffering from suspected Cholera?",
                options = listOf(
                    "Immediate aggressive rehydration with Oral Rehydration Salts (ORS) and IV Ringer's Lactate to replace volume and electrolytes",
                    "High-dose oral antimotility agents like loperamide",
                    "Immediate surgical exploratory laparotomy",
                    "Administering intravenous high-dose corticosteroids exclusively"
                ),
                correctIndex = 0,
                explanation = "Cholera mortality is driven entirely by rapid hypovolemic shock. Aggressive rehydration using ORS for mild/moderate cases and IV Ringer's Lactate for severe dehydration drops mortality from 50% to under 1%.",
                reference = "WHO Guidelines for Cholera Case Management"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Which personal protective equipment (PPE) combination is mandatory for healthcare personnel entering an isolation ward containing a suspected MERS-CoV or severe viral pneumonia patient?",
                options = listOf(
                    "Standard surgical cloth mask and disposable apron",
                    "N95/FFP2 respirator mask, eye protection (goggles or face shield), fluid-resistant gown, and double gloves",
                    "Sterile surgical gloves only",
                    "No PPE is needed if the room window is kept open"
                ),
                correctIndex = 1,
                explanation = "Airborne and contact precautions for MERS-CoV mandate an N95 respirator, eye protection (goggles/face shield), fluid-impermeable gown, and gloves to prevent nosocomial transmission.",
                reference = "Saudi MOH Infection Control & MERS-CoV Isolation SOP"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "How long before traveling to Saudi Arabia should pilgrims receive their seasonal Influenza vaccine to ensure optimal antibody development?",
                options = listOf(
                    "At least 2 weeks prior to departure",
                    "24 hours before taking the flight",
                    "6 months before departure",
                    "Influenza vaccine is administered after returning home"
                ),
                correctIndex = 0,
                explanation = "Seasonal influenza vaccine requires approximately 10 to 14 days post-injection to induce protective serum IgG antibody titers against circulating influenza strains.",
                reference = "CDC Immunization Recommendations for Mass Gatherings"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Which high-risk pilgrim population is explicitly recommended to receive the Pneumococcal Conjugate (PCV13) or Polysaccharide (PPSV23) vaccine prior to Hajj?",
                options = listOf(
                    "Healthy teenagers under 18 years",
                    "Pilgrims aged 65 years and older, or those with chronic cardiac, pulmonary, renal, hepatic disease, or diabetes",
                    "All pregnant women in their first trimester only",
                    "Pilgrims with minor skin allergies"
                ),
                correctIndex = 1,
                explanation = "Elderly pilgrims and individuals with chronic comorbid conditions (COPD, heart failure, diabetes) are at severe risk for invasive pneumococcal pneumonia and should receive pneumococcal vaccination.",
                reference = "Saudi MOH Advisory Board on Hajj Vaccinations"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "An outbreak of sudden vomiting and watery diarrhea occurs in a pilgrim hotel 2 hours after a communal banquet containing un-refrigerated cream pastries. What toxin-mediated food poisoning is suspected?",
                options = listOf(
                    "Staphylococcus aureus enterotoxin food poisoning",
                    "Clostridium tetani infection",
                    "Mycobacterium tuberculosis",
                    "Giardia lamblia"
                ),
                correctIndex = 0,
                explanation = "Staphylococcus aureus produces heat-stable enterotoxins in contaminated dairy/pastries left at room temperature. The incubation period is characteristically short (1 to 6 hours), presenting with acute vomiting and nausea.",
                reference = "CDC Foodborne Pathogens & Toxin Illness Guide"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What is the primary transmission route of Hepatitis A virus in high-density pilgrim accommodations?",
                options = listOf(
                    "Airborne droplet nuclei generated during coughing",
                    "Fecal-oral route via ingestion of fecally contaminated food or drinking water",
                    "Direct blood-borne transfusion",
                    "Mosquito vector bites"
                ),
                correctIndex = 1,
                explanation = "Hepatitis A is transmitted via the fecal-oral route. Poor hand hygiene and contaminated communal water or raw food lead to outbreaks in dense crowd settings.",
                reference = "WHO Viral Hepatitis Prevention Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Yellow Fever vaccination certificates are mandatory for pilgrims arriving from which geographic regions?",
                options = listOf(
                    "All Western European countries",
                    "Yellow Fever endemic countries/zones in Sub-Saharan Africa and Central/South America",
                    "Central Asian republics",
                    "East Asian island nations"
                ),
                correctIndex = 1,
                explanation = "Saudi Arabia enforces mandatory Yellow Fever vaccination (administered >=10 days prior) for all travelers arriving from countries with active Yellow Fever transmission in Africa and South America.",
                reference = "International Health Regulations (IHR) & Saudi MOH Entry Rules"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "When evaluating a pilgrim with a 3-week history of productive cough, fever, night sweats, and weight loss, what immediate infection control precaution must be initiated?",
                options = listOf(
                    "Place in a negative-pressure isolation room (or well-ventilated single room) and provide an N95/surgical mask to evaluate for Pulmonary Tuberculosis",
                    "Admit to an open intensive care ward alongside immunocompromised patients",
                    "Prescribe oral antihistamines and discharge to communal tents",
                    "Perform immediate emergency tracheostomy"
                ),
                correctIndex = 0,
                explanation = "Chronic productive cough with constitutional B-symptoms suggests active Pulmonary Tuberculosis (*Mycobacterium tuberculosis*). Respiratory isolation prevents transmission in crowded pilgrim tents.",
                reference = "WHO Tuberculosis Control in Mass Gatherings"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What topical antiparasitic medication is the first-line treatment for a pilgrim diagnosed with Scabies in a crowded Mina tent?",
                options = listOf(
                    "Topical 5% Permethrin cream applied from neck down and washed off after 8 to 14 hours",
                    "Topical hydrocortisone 1% ointment",
                    "Oral amoxicillin tablets",
                    "Topical mupirocin nasal ointment"
                ),
                correctIndex = 0,
                explanation = "Permethrin 5% cream is the drug of choice for Scabies (*Sarcoptes scabiei*). Bedding and clothing must also be washed in hot water (>50°C) to prevent re-infestation in communal tents.",
                reference = "Dermatology Guidelines for Mass Encampments"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "A pilgrim sustains a deep, dirty puncture wound from a rusty metal stake in Mina. What is the correct immunoprophylaxis if their tetanus vaccination status is unknown?",
                options = listOf(
                    "Administer Tetanus Toxoid (TT/Td) vaccine AND Tetanus Immune Globulin (TIG) simultaneously at separate anatomical sites",
                    "Administer oral penicillin only",
                    "Clean the wound with cold water and issue no vaccine",
                    "Administer topical antifungal cream"
                ),
                correctIndex = 0,
                explanation = "For dirty, tetanus-prone wounds in patients with unknown or incomplete (<3 doses) tetanus immunization, both passive immunity (TIG 250 units) and active immunity (Tetanus Toxoid) are required.",
                reference = "CDC Tetanus Wound Management Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "If a stray animal bites a pilgrim in Makkah, what is the immediate first aid and Post-Exposure Prophylaxis (PEP) protocol for Rabies?",
                options = listOf(
                    "Suture the wound tightly immediately without irrigation",
                    "Thoroughly wash and flush the wound with soap and running water for at least 15 minutes, apply antiseptic, and initiate Rabies Vaccine + Rabies Immunoglobulin (RIG)",
                    "Apply topical steroid ointment and monitor for 30 days",
                    "Administer single dose oral ciprofloxacin"
                ),
                correctIndex = 1,
                explanation = "Immediate, vigorous wound washing with soap and water for 15 minutes dramatically reduces viral load. Post-Exposure Prophylaxis requires Rabies Immunoglobulin (RIG) infiltration and vaccine series (Days 0, 3, 7, 14).",
                reference = "WHO Rabies Post-Exposure Prophylaxis Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Which antibiotic is the empiric drug of choice for severe suspected Typhoid Fever (*Salmonella enterica* serovar Typhi) in travelers returning from South Asia?",
                options = listOf(
                    "Intravenous Ceftriaxone (or oral Azithromycin)",
                    "Oral Penicillin V",
                    "Intravenous Vancomycin",
                    "Oral Nystatin"
                ),
                correctIndex = 0,
                explanation = "Due to widespread fluoroquinolone resistance in South Asia, empiric treatment for suspected Typhoid Fever relies on IV Ceftriaxone or oral Azithromycin.",
                reference = "NTS Infectious Diseases & Travel Medicine"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What food poisoning pathogen is classically associated with improperly handled reheated fried rice served at large catered events?",
                options = listOf(
                    "Bacillus cereus (emetic toxin)",
                    "Streptococcus pyogenes",
                    "Treponema pallidum",
                    "Leishmania tropica"
                ),
                correctIndex = 0,
                explanation = "Bacillus cereus spores survive cooking and multiply in boiled/fried rice left at room temperature, producing a heat-stable emetic enterotoxin causing rapid vomiting within 1 to 5 hours.",
                reference = "Microbiology & Food Hygiene Standards"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What is the recommended minimum free chlorine residual concentration for municipal drinking water storage tanks in Mina pilgrim camps?",
                options = listOf(
                    "0.01 mg/L",
                    "0.2 to 0.5 mg/L (ppm) of free residual chlorine",
                    "10.0 mg/L",
                    "Chlorination is forbidden in drinking water"
                ),
                correctIndex = 1,
                explanation = "A free residual chlorine level of 0.2 to 0.5 mg/L ensures continuous disinfection against enteric waterborne bacterial pathogens in storage tanks.",
                reference = "WHO Water Sanitation & Health Guidelines in Crowds"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "If an unvaccinated pilgrim is exposed to a confirmed Measles case in a dormitory, within what timeframe can post-exposure MMR vaccination offer protection?",
                options = listOf(
                    "Within 72 hours of initial exposure",
                    "Within 30 days of exposure",
                    "Measles vaccine cannot be given post-exposure",
                    "Only after rash appears"
                ),
                correctIndex = 0,
                explanation = "MMR vaccine administered within 72 hours of exposure provides post-exposure protection. Alternatively, Measles Immunoglobulin (IG) can be given within 6 days for high-risk individuals.",
                reference = "CDC Measles Post-Exposure Prophylaxis Guidelines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What is the proper sequence for removing (doffing) Personal Protective Equipment (PPE) to prevent self-contamination after exiting an isolation ward?",
                options = listOf(
                    "Remove N95 respirator first, then gloves, then gown",
                    "Remove Gloves first, then Goggles/Face Shield, then Gown, then Mask/Respirator LAST (followed by immediate hand hygiene)",
                    "Remove Gown first, then Mask, then Gloves last",
                    "All PPE should be pulled off simultaneously in one motion"
                ),
                correctIndex = 1,
                explanation = "Gloves and gown front are the most contaminated. Doffing order is: Gloves -> Goggles/Face Shield -> Gown -> Mask/Respirator LAST (outside the patient room), followed by hand hygiene.",
                reference = "CDC & WHO Infection Control PPE Protocols"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Which arboviral infection transmitted by Aedes mosquitoes presents with sudden high fever, retro-orbital headache, severe joint/muscle pain ('breakbone fever'), and leukopenia?",
                options = listOf(
                    "Dengue Fever",
                    "Rabies",
                    "Pneumococcal pneumonia",
                    "Schistosomiasis"
                ),
                correctIndex = 0,
                explanation = "Dengue virus is transmitted by Aedes mosquitoes in urban environments. It causes classic 'breakbone' myalgias, retro-orbital pain, skin rash, thrombocytopenia, and leukopenia.",
                reference = "WHO Dengue Guidelines for Diagnosis and Treatment"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Which oral antimalarial chemoprophylaxis regimen is suitable for pilgrims traveling to Hajj from chloroquine-resistant Plasmodium falciparum endemic regions?",
                options = listOf(
                    "Atovaquone-Proguanil (Malarone) daily or Doxycycline 100 mg daily",
                    "Oral Aspirin 300 mg daily",
                    "Oral Metronidazole 400 mg",
                    "Oral Amoxicillin 500 mg"
                ),
                correctIndex = 0,
                explanation = "For regions with chloroquine-resistant P. falciparum, recommended prophylactic drugs are Atovaquone-Proguanil (started 1-2 days before travel) or Doxycycline 100 mg daily.",
                reference = "CDC Yellow Book - Malaria Chemoprophylaxis"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What is the single most effective, universally recommended infection control practice to reduce cross-contamination in field dispensaries?",
                options = listOf(
                    "Rigorous hand hygiene (alcohol-based hand rub or soap and water for 20 seconds before and after every patient contact)",
                    "Routine administration of daily prophylactic oral antibiotics to all doctors",
                    "Spraying aerosolized chlorine into occupied patient wards",
                    "Wearing cotton caps"
                ),
                correctIndex = 0,
                explanation = "Hand hygiene remains the gold standard for preventing healthcare-associated infections (HAIs) and stopping the spread of multidrug-resistant pathogens.",
                reference = "WHO 5 Moments for Hand Hygiene"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Under Saudi MOH public health regulations, within what timeframe must a confirmed case of Meningococcal Meningitis or MERS-CoV be reported to authorities?",
                options = listOf(
                    "Immediate notification within 24 hours (Category A mandatory notification)",
                    "Within 30 days after Hajj concludes",
                    "At the end of the calendar year",
                    "Notification is optional"
                ),
                correctIndex = 0,
                explanation = "High-priority epidemic threats (meningococcal disease, MERS-CoV, cholera, polio) require immediate notification within 24 hours to trigger public health ring containment.",
                reference = "Saudi MOH Communicable Disease Reporting Policy"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What is the mechanism of action of the Quadrivalent Meningococcal Conjugate vaccine (MCV4)?",
                options = listOf(
                    "Provides capsular polysaccharide antigens conjugated to a carrier protein (diphtheria toxoid) to induce T-cell dependent long-term immunological memory against serogroups A, C, Y, and W135",
                    "Supplies live attenuated viral particles to stimulate gut mucosal IgA",
                    "Contains inactivated bacterial exotoxins against Clostridium perfringens",
                    "Injects synthetic antibody proteins directly into circulation"
                ),
                correctIndex = 0,
                explanation = "MCV4 conjugates serogroups A, C, Y, W135 capsular polysaccharides to a protein carrier, converting it into a T-dependent antigen that triggers long-lasting mucosal and systemic immunity.",
                reference = "Immunology of Bacterial Conjugate Vaccines"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "Which pathogen is the primary causative agent of traveler's diarrhea in dense international crowd gatherings?",
                options = listOf(
                    "Enterotoxigenic Escherichia coli (ETEC)",
                    "Mycobacterium leprae",
                    "Borrelia burgdorferi",
                    "Corynebacterium diphtheriae"
                ),
                correctIndex = 0,
                explanation = "ETEC (Enterotoxigenic E. coli) produces heat-labile (LT) and heat-stable (ST) enterotoxins, making it the most common cause of acute watery traveler's diarrhea worldwide.",
                reference = "CDC Health Information for International Travel"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "A pilgrim presents with high fever, sore throat, severe fatigue, and a greyish-white pseudomembrane covering the tonsils and pharynx. What urgent diagnosis must be considered?",
                options = listOf(
                    "Respiratory Diphtheria (*Corynebacterium diphtheriae*)",
                    "Oral candidiasis",
                    "Mild viral pharyngitis",
                    "Allergic rhinitis"
                ),
                correctIndex = 0,
                explanation = "An adherent grey pseudomembrane over tonsils/pharynx that bleeds upon scraping is classic for Diphtheria. It requires immediate airway monitoring, Diphtheria Antitoxin, and Erythromycin/Penicillin.",
                reference = "CDC Diphtheria Clinical Management Guide"
            )
        )

        list.add(
            HajjQuestion(
                id = idCounter++,
                category = "Vaccine & Outbreaks",
                question = "What is the recommended isolation room pressure configuration for preventing airborne spread of active pulmonary tuberculosis or measles?",
                options = listOf(
                    "Negative Pressure Isolation Room (air flows into the room and is exhausted via HEPA filtration)",
                    "Positive Pressure Isolation Room",
                    "Sealed room with zero air ventilation",
                    "Open balcony corridor"
                ),
                correctIndex = 0,
                explanation = "Negative pressure isolation prevents airborne droplet nuclei from escaping into hallways by drawing air inward and venting it through HEPA filters.",
                reference = "CDC Isolation Facility Engineering Standards"
            )
        )

        return list
    }
}
