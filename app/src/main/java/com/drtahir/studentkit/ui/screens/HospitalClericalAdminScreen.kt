package com.drtahir.studentkit.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

// =============================================================
// HEALTH CLERICAL DATA MODELS & PRESETS
// =============================================================

enum class HospitalClerkCategory(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    EXPLANATION("Explanations & Show Cause", Icons.Default.Warning),
    OFFICE_ORDER("Office Orders & Transfers", Icons.Default.Assignment),
    ROSTER("Hospital Duty Rosters", Icons.Default.CalendarMonth),
    CORRESPONDENCE("Notes & Certificates", Icons.Default.Description),
    CUSTOM_MEMO("Official Letterhead Memo", Icons.Default.EditNote),
    SAVED_DRAFTS("Saved & Dispatched", Icons.Default.Folder)
}

data class HospitalTemplate(
    val id: String,
    val title: String,
    val category: HospitalClerkCategory,
    val subject: String,
    val description: String,
    val defaultBody: String,
    val defaultCopies: List<String>,
    val defaultType: String = "Formal"
)

data class HospitalHospitalProfile(
    val hospitalName: String = "GOVERNMENT CATEGORY-D HOSPITAL PACHA KALAY BUNER",
    val officeTitle: String = "OFFICE OF THE MEDICAL SUPERINTENDENT",
    val departmentTitle: String = "HEALTH DEPARTMENT, GOVERNMENT OF KHYBER PAKHTUNKHWA",
    val phoneFax: String = "Phone: 0939-510000 | Email: ms.catdpachakalay@healthkp.gov.pk",
    val location: String = "Pacha Kalay, District Buner",
    val signatoryName: String = "Dr. Tahir Khan",
    val signatoryDesignation: String = "Medical Superintendent (MS)",
    val signatoryInstitution: String = "Govt Category-D Hospital Pacha Kalay Buner"
)

data class HospitalLetterDraft(
    val id: String = UUID.randomUUID().toString(),
    val templateId: String,
    val title: String,
    val dispatchNo: String,
    val date: String,
    val recipient: String,
    val employeeName: String,
    val employeeDesignation: String,
    val employeeBps: String,
    val employeeWard: String,
    val subject: String,
    val body: String,
    val copiesTo: List<String>,
    val signatoryName: String,
    val signatoryTitle: String,
    val hospitalProfile: HospitalHospitalProfile,
    val timestamp: Long = System.currentTimeMillis()
)

data class RosterEntry(
    val employeeName: String,
    val designation: String,
    val ward: String,
    val morningShift: String = "08:00 AM - 02:00 PM",
    val eveningShift: String = "02:00 PM - 08:00 PM",
    val nightShift: String = "08:00 PM - 08:00 AM",
    val offDay: String = "Sunday",
    val remarks: String = "On Duty"
)

val defaultHospitalTemplates = listOf(
    // --- EXPLANATION CALLS ---
    HospitalTemplate(
        id = "exp_absent",
        title = "Explanation: Unauthorized Absence from Duty",
        category = HospitalClerkCategory.EXPLANATION,
        subject = "EXPLANATION CALL REGARDING UNAUTHORIZED ABSENCE FROM GOVERNMENT DUTY",
        description = "Formal explanation call for missing official hospital shift without sanctioned leave.",
        defaultBody = """It has been observed with grave concern during the administrative round / biometric attendance verification that you were found willfully absent from your official duty at the {WARD} without prior permission or sanctioned leave from the competent authority on {DATE}.

Such uncalled-for behavior on your part amounts to severe dereliction of duty, misconduct, and breach of discipline under the Khyber Pakhtunkhwa Government Servants (Efficiency & Discipline) Rules.

You are hereby directed to submit your written explanation to this office within three (03) days of the receipt of this communication as to why disciplinary action should not be initiated against you.

In case of failure to submit an explanation within the stipulated timeframe, it will be presumed that you have no defense to offer, and ex-parte proceedings will be initiated against you.""",
        defaultCopies = listOf(
            "The Director General Health Services, Khyber Pakhtunkhwa, Peshawar.",
            "The District Health Officer (DHO), Buner at Daggar.",
            "The In-Charge / Head of {WARD}, Govt Cat-D Hospital Pacha Kalay.",
            "Personal file of the official concerned.",
            "Office copy / Notice Board."
        )
    ),

    HospitalTemplate(
        id = "exp_negligence",
        title = "Explanation: Patient Care Negligence & Misconduct",
        category = HospitalClerkCategory.EXPLANATION,
        subject = "EXPLANATION CALL REGARDING NEGLIGENCE IN PATIENT CARE AND CLINICAL DUTIES",
        description = "Disciplinary explanation for negligence in emergency/ward patient management.",
        defaultBody = """A serious complaint / report has been received against you from the Casualty / Emergency Unit regarding sheer negligence and careless attitude in handling patient care on {DATE}.

Your failure to provide prompt medical attention / nursing care to the admitted emergency patient is intolerable and in direct violation of the standard clinical protocols and code of conduct governing healthcare staff.

You are hereby called upon to explain your position in writing within forty-eight (48) hours of receipt of this notice, failing which strict departmental disciplinary action will be initiated against you as per KP Health Department rules.""",
        defaultCopies = listOf(
            "The District Health Officer (DHO), Buner.",
            "The Nursing Superintendent / Clinical Incharge, Govt Cat-D Hospital Pacha Kalay.",
            "Personal file of the official concerned.",
            "Office Record."
        )
    ),

    HospitalTemplate(
        id = "exp_late",
        title = "Explanation: Habitual Late Arrival & Biometric Evasion",
        category = HospitalClerkCategory.EXPLANATION,
        subject = "EXPLANATION REGARDING HABITUAL LATE COMING AND EVASION OF ATTENDANCE SYSTEM",
        description = "Explanation for persistent late reporting and non-compliance with biometric time clock.",
        defaultBody = """Scrutiny of the official biometric attendance reports and physical movement registers reveals that you are persistently reporting late to your duty station at {WARD}.

Despite repeated verbal warnings, you have failed to improve your punctuality, which adversely affects public healthcare delivery and OPD/Emergency services.

You are directed to explain in writing within three (03) days why disciplinary proceedings should not be instituted against you under the relevant Efficiency & Discipline Rules.""",
        defaultCopies = listOf(
            "The District Health Officer (DHO), Buner.",
            "The Biometric System Administrator / Incharge Attendance.",
            "Personal file of the official concerned.",
            "Office copy."
        )
    ),

    // --- OFFICE ORDERS & TRANSFERS ---
    HospitalTemplate(
        id = "order_transfer",
        title = "Office Order: Internal Ward Posting / Transfer",
        category = HospitalClerkCategory.OFFICE_ORDER,
        subject = "OFFICE ORDER - INTERNAL TRANSFER AND POSTING",
        description = "Internal relocation order of staff (Nursing/Paramedics/Ward Orderly) between hospital units.",
        defaultBody = """In the interest of smooth public healthcare delivery and administrative necessity, the following internal transfer / posting of staff is hereby ordered with immediate effect until further orders:

1. {EMPLOYEE_NAME}, {DESIGNATION} (BPS-{BPS}) is hereby transferred from {SOURCE_WARD} and posted to {TARGET_WARD}.

The official concerned is directed to hand over his/her current charge and report for duty to the In-charge of the new duty station immediately without availing any joining time.

Compliance report must reach this office within twenty-four (24) hours.""",
        defaultCopies = listOf(
            "The District Health Officer (DHO), Buner at Daggar.",
            "The District Accounts Officer, Buner.",
            "The In-Charge (Emergency / ICU / OT / Wards), Govt Cat-D Hospital Pacha Kalay.",
            "The Nursing Superintendent, Govt Cat-D Hospital Pacha Kalay.",
            "The Official Concerned.",
            "Office Order File / Personal File."
        )
    ),

    HospitalTemplate(
        id = "order_leave",
        title = "Office Order: Sanction of Casual / Medical Leave",
        category = HospitalClerkCategory.OFFICE_ORDER,
        subject = "OFFICE ORDER - SANCTION OF CASUAL / MEDICAL LEAVE",
        description = "Sanction order of casual leave or medical rest with alternate duty arrangement.",
        defaultBody = """Sanction is hereby accorded to the grant of {LEAVE_DAYS} days Casual Leave / Medical Rest with effect from {START_DATE} to {END_DATE} (both days inclusive) in respect of {EMPLOYEE_NAME}, {DESIGNATION} (BPS-{BPS}), Govt Category-D Hospital Pacha Kalay Buner on domestic / medical grounds.

During the leave period, {RELIEVER_NAME}, {RELIEVER_DESIGNATION} will look after the routine work of {WARD} in addition to his/her own duties without any extra remuneration.

On expiry of the leave, the official is directed to resume duty at the same station.""",
        defaultCopies = listOf(
            "The District Accounts Officer, Buner.",
            "The In-Charge {WARD}, Govt Cat-D Hospital Pacha Kalay.",
            "The Official Concerned.",
            "Leave Record / Personal File."
        )
    ),

    HospitalTemplate(
        id = "order_relieving",
        title = "Office Order: Relieving Order on Deputation / Transfer",
        category = HospitalClerkCategory.OFFICE_ORDER,
        subject = "RELIEVING ORDER - DEPARTURE UPON TRANSFER",
        description = "Formal relieving order after transfer notification from Director General / DHO.",
        defaultBody = """In pursuance of Directorate General Health Services, Khyber Pakhtunkhwa / DHO Buner notification No. {GOVT_NOTIF_NO}, dated {NOTIF_DATE}, {EMPLOYEE_NAME}, {DESIGNATION} (BPS-{BPS}) is hereby relieved from his/her duties at Govt Category-D Hospital Pacha Kalay Buner on {RELIEVING_DATE} (A.N.) with direction to report to his/her new place of posting.

It is certified that nothing is outstanding against the official regarding hospital inventory, medications, surgical instruments, or government property.""",
        defaultCopies = listOf(
            "The Director General Health Services, Khyber Pakhtunkhwa, Peshawar.",
            "The District Health Officer (DHO), Buner at Daggar.",
            "The District Accounts Officer, Buner.",
            "The In-Charge / MS of the receiving hospital / institution.",
            "The Official Concerned.",
            "Office record."
        )
    ),

    HospitalTemplate(
        id = "order_joining",
        title = "Office Order: Assumption of Charge / Joining Report",
        category = HospitalClerkCategory.OFFICE_ORDER,
        subject = "ASSUMPTION OF CHARGE / JOINING REPORT",
        description = "Formal recording of employee arrival and assignment of duty station.",
        defaultBody = """In compliance with Health Department KP / DHO Buner Order No. {ORDER_NO}, dated {ORDER_DATE}, {EMPLOYEE_NAME}, {DESIGNATION} (BPS-{BPS}) has reported for duty at Government Category-D Hospital Pacha Kalay Buner on {JOINING_DATE} (F.N.).

The arrival of the official is hereby formally accepted on the rolls of this hospital, and he/she is placed at the disposal of {WARD} for operational duty with immediate effect.""",
        defaultCopies = listOf(
            "The District Health Officer (DHO), Buner at Daggar.",
            "The District Accounts Officer, Buner.",
            "The In-Charge {WARD}, Govt Cat-D Hospital Pacha Kalay.",
            "The Official Concerned.",
            "Personal File / Service Book."
        )
    ),

    // --- CORRESPONDENCE & CERTIFICATES ---
    HospitalTemplate(
        id = "cert_inquiry",
        title = "Office Order: Constitution of Inquiry Committee",
        category = HospitalClerkCategory.CORRESPONDENCE,
        subject = "CONSTITUTION OF INQUIRY COMMITTEE",
        description = "Formal constitution of 3-member administrative inquiry committee with TORs.",
        defaultBody = """A high-level three-member Inquiry Committee comprising the following officers is hereby constituted to probe into the incident of {INCIDENT_DETAILS} occurred on {INCIDENT_DATE} at Government Category-D Hospital Pacha Kalay Buner:

1. {CHAIRMAN_NAME} ({CHAIRMAN_DESIG}) - Chairman
2. {MEMBER1_NAME} ({MEMBER1_DESIG}) - Member
3. {MEMBER2_NAME} ({MEMBER2_DESIG}) - Member / Secretary

Terms of Reference (TORs):
a) To investigate the facts, circumstances, and root cause of the reported matter.
b) To record statements of all concerned staff, eye-witnesses, and complainants.
c) To fix responsibility on the delinquent staff, if any.
d) To submit a comprehensive inquiry report with concrete findings and recommendations to the undersigned within seven (07) days positively.""",
        defaultCopies = listOf(
            "The District Health Officer (DHO), Buner at Daggar.",
            "All Members of the Inquiry Committee.",
            "The Complainant / Relevant Ward Incharge.",
            "Office Record."
        )
    ),

    HospitalTemplate(
        id = "cert_service",
        title = "Service & Experience Certificate",
        category = HospitalClerkCategory.CORRESPONDENCE,
        subject = "EXPERIENCE AND SERVICE CERTIFICATE",
        description = "Official character and clinical service experience certificate for employees.",
        defaultBody = """TO WHOM IT MAY CONCERN

This is to certify that {EMPLOYEE_NAME}, S/D/W of {FATHER_NAME}, bearing CNIC No. {CNIC_NO}, has been serving in Government Category-D Hospital Pacha Kalay Buner as {DESIGNATION} (BPS-{BPS}) on regular / contract basis from {START_DATE} to {END_DATE}.

During his/her tenure of service at this institution, he/she performed duties in various units including {WARD_LIST}. His/her work, conduct, and clinical competence have been found satisfactory, dedicated, and up to the mark.

To the best of our knowledge, he/she bears good moral character. This certificate is issued upon his/her own request for employment / verification purposes without any legal liability on this office.""",
        defaultCopies = listOf(
            "The Official Concerned.",
            "Office Record."
        )
    ),

    HospitalTemplate(
        id = "cert_noc",
        title = "No Objection Certificate (NOC) for Higher Studies",
        category = HospitalClerkCategory.CORRESPONDENCE,
        subject = "NO OBJECTION CERTIFICATE (NOC) FOR HIGHER QUALIFICATION",
        description = "NOC for staff applying for Post-RN, BSN, FCPS, MCPS or Diploma courses.",
        defaultBody = """TO WHOM IT MAY CONCERN

This office has NO OBJECTION if {EMPLOYEE_NAME}, {DESIGNATION} (BPS-{BPS}), currently posted at Government Category-D Hospital Pacha Kalay Buner, applies for and appears in the examination / interview for {COURSE_NAME} at {INSTITUTE_NAME}.

This certificate is issued subject to the condition that his/her studies will not disrupt the routine hospital functioning, and formal study leave / deputation will be processed through the proper channel via Director General Health Services KP.""",
        defaultCopies = listOf(
            "The District Health Officer (DHO), Buner.",
            "The Principal / Registrar, {INSTITUTE_NAME}.",
            "The Official Concerned.",
            "Personal File."
        )
    ),

    HospitalTemplate(
        id = "cert_req",
        title = "Emergency Stock / Medicine Requisition & LP Note",
        category = HospitalClerkCategory.CORRESPONDENCE,
        subject = "REQUISITION FOR EMERGENCY MEDICINES AND SURGICAL CONSUMABLES (LP)",
        description = "Administrative note for stock purchase and emergency dispensary replenishment.",
        defaultBody = """MEMORANDUM:

The stock of vital emergency medicines, IV fluids, and surgical consumables in the Casualty / OT Main Store has depleted below minimum buffer levels due to heavy patient influx at Govt Category-D Hospital Pacha Kalay Buner.

In order to ensure uninterrupted patient care and emergency clinical services, sanction is hereby requested for urgent Local Purchase (LP) / replenishment of the items listed in the attached indent.

The expenditure will be debited under the relevant budget head of Medicines / Medical Consumables for the financial year 2025-2026.""",
        defaultCopies = listOf(
            "The District Health Officer (DHO), Buner at Daggar.",
            "The Hospital Pharmacist / Store Keeper, Govt Cat-D Hospital Pacha Kalay.",
            "The Accounts Clerk / Budget Branch.",
            "Office File."
        )
    )
)

// =============================================================
// MAIN COMPOSABLE SCREEN
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalClericalAdminScreen(
    viewModel: StudentKitViewModel
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val prefs = remember { context.getSharedPreferences("hospital_clerk_admin_prefs", Context.MODE_PRIVATE) }

    var selectedCategory by remember { mutableStateOf(HospitalClerkCategory.EXPLANATION) }
    var activeDraft by remember { mutableStateOf<HospitalLetterDraft?>(null) }
    var isEditingHospitalProfile by remember { mutableStateOf(false) }
    var hospitalProfile by remember {
        mutableStateOf(
            HospitalHospitalProfile(
                hospitalName = prefs.getString("h_name", "GOVERNMENT CATEGORY-D HOSPITAL PACHA KALAY BUNER") ?: "GOVERNMENT CATEGORY-D HOSPITAL PACHA KALAY BUNER",
                officeTitle = prefs.getString("h_office", "OFFICE OF THE MEDICAL SUPERINTENDENT") ?: "OFFICE OF THE MEDICAL SUPERINTENDENT",
                departmentTitle = prefs.getString("h_dept", "HEALTH DEPARTMENT, GOVERNMENT OF KHYBER PAKHTUNKHWA") ?: "HEALTH DEPARTMENT, GOVERNMENT OF KHYBER PAKHTUNKHWA",
                phoneFax = prefs.getString("h_phone", "Phone: 0939-510000 | Email: ms.catdpachakalay@healthkp.gov.pk") ?: "Phone: 0939-510000 | Email: ms.catdpachakalay@healthkp.gov.pk",
                location = prefs.getString("h_loc", "Pacha Kalay, District Buner") ?: "Pacha Kalay, District Buner",
                signatoryName = prefs.getString("h_sig_name", "Dr. Tahir Khan") ?: "Dr. Tahir Khan",
                signatoryDesignation = prefs.getString("h_sig_desig", "Medical Superintendent (MS)") ?: "Medical Superintendent (MS)",
                signatoryInstitution = prefs.getString("h_sig_inst", "Govt Category-D Hospital Pacha Kalay Buner") ?: "Govt Category-D Hospital Pacha Kalay Buner"
            )
        )
    }

    var savedDraftsList by remember { mutableStateOf<List<HospitalLetterDraft>>(loadSavedDrafts(prefs, hospitalProfile)) }
    var showPreviewModal by remember { mutableStateOf(false) }

    // Roster builder states
    var rosterMonthYear by remember { mutableStateOf(SimpleDateFormat("MMMM yyyy", Locale.US).format(Date())) }
    var rosterWard by remember { mutableStateOf("Emergency / Casualty Unit") }
    var rosterEntries by remember {
        mutableStateOf(
            listOf(
                RosterEntry("Dr. Ahmad Ali", "Medical Officer (MO)", "Emergency", "08:00 AM - 02:00 PM", "Off", "Off", "Sunday", "Morning Shift Incharge"),
                RosterEntry("Dr. Saima Noor", "Women Medical Officer (WMO)", "Gynae / LR", "Off", "02:00 PM - 08:00 PM", "Off", "Friday", "Evening Shift Incharge"),
                RosterEntry("Farman Ullah", "Head Nurse", "ICU / Emergency", "Off", "Off", "08:00 PM - 08:00 AM", "Monday", "Night Emergency Incharge"),
                RosterEntry("Rashid Khan", "Pharmacy Technician", "Main Pharmacy", "08:00 AM - 02:00 PM", "Off", "Off", "Sunday", "General OPD Dispensary"),
                RosterEntry("Zahid Hussain", "Lab Technician", "Pathology Lab", "08:00 AM - 04:00 PM", "On Call", "Off", "Friday", "Emergency Blood Banking")
            )
        )
    }

    // Save profile helper
    fun saveHospitalProfile(newProfile: HospitalHospitalProfile) {
        hospitalProfile = newProfile
        prefs.edit()
            .putString("h_name", newProfile.hospitalName)
            .putString("h_office", newProfile.officeTitle)
            .putString("h_dept", newProfile.departmentTitle)
            .putString("h_phone", newProfile.phoneFax)
            .putString("h_loc", newProfile.location)
            .putString("h_sig_name", newProfile.signatoryName)
            .putString("h_sig_desig", newProfile.signatoryDesignation)
            .putString("h_sig_inst", newProfile.signatoryInstitution)
            .apply()
        Toast.makeText(context, "Hospital Header & MS Profile updated!", Toast.LENGTH_SHORT).show()
    }

    // Save draft helper
    fun saveDraft(draft: HospitalLetterDraft) {
        val updated = listOf(draft) + savedDraftsList.filter { it.id != draft.id }
        savedDraftsList = updated
        persistDrafts(prefs, updated)
        Toast.makeText(context, "Document saved to Dispatched Archives!", Toast.LENGTH_SHORT).show()
    }

    fun deleteDraft(draftId: String) {
        val updated = savedDraftsList.filter { it.id != draftId }
        savedDraftsList = updated
        persistDrafts(prefs, updated)
        Toast.makeText(context, "Document deleted", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // TOP HEADER CARD (Hospital Identity)
        HospitalBannerHeader(
            profile = hospitalProfile,
            onEditHeader = { isEditingHospitalProfile = true }
        )

        // CATEGORY CHIPS
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(HospitalClerkCategory.values()) { cat ->
                val isSelected = selectedCategory == cat
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedCategory = cat
                        if (cat == HospitalClerkCategory.CUSTOM_MEMO && activeDraft == null) {
                            activeDraft = createInitialCustomDraft(hospitalProfile)
                        }
                    },
                    leadingIcon = {
                        Icon(cat.icon, contentDescription = null, modifier = Modifier.size(16.dp))
                    },
                    label = {
                        Text(cat.label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF00796B),
                        selectedLabelColor = Color.White,
                        selectedLeadingIconColor = Color.White
                    )
                )
            }
        }

        // MAIN CONTENT BODY BASED ON SELECTED CATEGORY
        Box(modifier = Modifier.weight(1f)) {
            when (selectedCategory) {
                HospitalClerkCategory.EXPLANATION,
                HospitalClerkCategory.OFFICE_ORDER,
                HospitalClerkCategory.CORRESPONDENCE -> {
                    TemplateSelectionListView(
                        category = selectedCategory,
                        onSelectTemplate = { template ->
                            activeDraft = createDraftFromTemplate(template, hospitalProfile)
                        }
                    )
                }

                HospitalClerkCategory.ROSTER -> {
                    HospitalRosterBuilderView(
                        profile = hospitalProfile,
                        monthYear = rosterMonthYear,
                        onMonthYearChange = { rosterMonthYear = it },
                        ward = rosterWard,
                        onWardChange = { rosterWard = it },
                        entries = rosterEntries,
                        onUpdateEntries = { rosterEntries = it },
                        onPrintRoster = {
                            val rosterDraft = generateRosterDraft(hospitalProfile, rosterMonthYear, rosterWard, rosterEntries)
                            printHospitalA4Document(context, rosterDraft)
                        },
                        onShareRoster = {
                            val text = buildRosterText(hospitalProfile, rosterMonthYear, rosterWard, rosterEntries)
                            shareText(context, text, "Duty Roster - $rosterWard")
                        }
                    )
                }

                HospitalClerkCategory.CUSTOM_MEMO -> {
                    val currentDraft = activeDraft ?: createInitialCustomDraft(hospitalProfile)
                    LetterDraftEditorView(
                        draft = currentDraft,
                        onDraftChange = { activeDraft = it },
                        onSave = { saveDraft(it) },
                        onPrint = { printHospitalA4Document(context, it) },
                        onShare = { shareHospitalLetter(context, it) },
                        onPreview = { showPreviewModal = true }
                    )
                }

                HospitalClerkCategory.SAVED_DRAFTS -> {
                    SavedDraftsListView(
                        drafts = savedDraftsList,
                        onOpenDraft = { draft ->
                            activeDraft = draft
                            selectedCategory = HospitalClerkCategory.CUSTOM_MEMO
                        },
                        onPrintDraft = { printHospitalA4Document(context, it) },
                        onDeleteDraft = { deleteDraft(it.id) }
                    )
                }
            }
        }
    }

    // MODAL DIALOGS
    // 1. Hospital Profile Editor
    if (isEditingHospitalProfile) {
        HospitalProfileEditDialog(
            currentProfile = hospitalProfile,
            onDismiss = { isEditingHospitalProfile = false },
            onSave = { updated ->
                saveHospitalProfile(updated)
                isEditingHospitalProfile = false
            }
        )
    }

    // 2. Full A4 Live Preview Modal
    if (showPreviewModal && activeDraft != null) {
        LetterLivePreviewDialog(
            draft = activeDraft!!,
            onDismiss = { showPreviewModal = false },
            onPrint = {
                printHospitalA4Document(context, activeDraft!!)
                showPreviewModal = false
            },
            onShare = {
                shareHospitalLetter(context, activeDraft!!)
                showPreviewModal = false
            }
        )
    }

    // 3. Editor when template clicked from list
    if (activeDraft != null && selectedCategory != HospitalClerkCategory.CUSTOM_MEMO && selectedCategory != HospitalClerkCategory.ROSTER) {
        ModalBottomSheet(
            onDismissRequest = { activeDraft = null },
            dragHandle = { BottomSheetDefaults.DragHandle() },
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Box(modifier = Modifier.fillMaxHeight(0.92f)) {
                LetterDraftEditorView(
                    draft = activeDraft!!,
                    onDraftChange = { activeDraft = it },
                    onSave = {
                        saveDraft(it)
                        activeDraft = null
                    },
                    onPrint = { printHospitalA4Document(context, it) },
                    onShare = { shareHospitalLetter(context, it) },
                    onPreview = { showPreviewModal = true }
                )
            }
        }
    }
}

// =============================================================
// SUB-VIEWS & COMPONENTS
// =============================================================

@Composable
fun HospitalBannerHeader(
    profile: HospitalHospitalProfile,
    onEditHeader: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF004D40)
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFF00796B), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.LocalHospital,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = profile.officeTitle,
                    color = Color(0xFF80CBC4),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = profile.hospitalName,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Signatory: ${profile.signatoryName} (${profile.signatoryDesignation})",
                    color = Color(0xFFE0F2F1),
                    fontSize = 10.sp
                )
            }
            IconButton(
                onClick = onEditHeader,
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Edit Header", tint = Color.White, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun TemplateSelectionListView(
    category: HospitalClerkCategory,
    onSelectTemplate: (HospitalTemplate) -> Unit
) {
    val templates = remember(category) {
        defaultHospitalTemplates.filter { it.category == category }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
    ) {
        item {
            Text(
                text = "${templates.size} Official Government Templates Available",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(templates) { template ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectTemplate(template) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, Color(0xFF00796B).copy(alpha = 0.25f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF00796B).copy(alpha = 0.12f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    category.icon,
                                    contentDescription = null,
                                    tint = Color(0xFF00796B),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = template.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = template.subject,
                                fontSize = 11.sp,
                                color = Color(0xFF00796B),
                                maxLines = 1
                            )
                        }
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = template.description,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${template.defaultCopies.size} Endorsement Copies",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }

                        Button(
                            onClick = { onSelectTemplate(template) },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("Use & Edit Template", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// =============================================================
// LETTER DRAFT & MEMO EDITOR
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LetterDraftEditorView(
    draft: HospitalLetterDraft,
    onDraftChange: (HospitalLetterDraft) -> Unit,
    onSave: (HospitalLetterDraft) -> Unit,
    onPrint: (HospitalLetterDraft) -> Unit,
    onShare: (HospitalLetterDraft) -> Unit,
    onPreview: () -> Unit
) {
    var dispatchNo by remember(draft) { mutableStateOf(draft.dispatchNo) }
    var letterDate by remember(draft) { mutableStateOf(draft.date) }
    var recipient by remember(draft) { mutableStateOf(draft.recipient) }
    var employeeName by remember(draft) { mutableStateOf(draft.employeeName) }
    var employeeDesig by remember(draft) { mutableStateOf(draft.employeeDesignation) }
    var employeeBps by remember(draft) { mutableStateOf(draft.employeeBps) }
    var employeeWard by remember(draft) { mutableStateOf(draft.employeeWard) }
    var subject by remember(draft) { mutableStateOf(draft.subject) }
    var body by remember(draft) { mutableStateOf(draft.body) }
    var copiesInput by remember(draft) { mutableStateOf(draft.copiesTo.joinToString("\n")) }

    fun buildCurrentDraft(): HospitalLetterDraft {
        return draft.copy(
            dispatchNo = dispatchNo,
            date = letterDate,
            recipient = recipient,
            employeeName = employeeName,
            employeeDesignation = employeeDesig,
            employeeBps = employeeBps,
            employeeWard = employeeWard,
            subject = subject,
            body = body,
            copiesTo = copiesInput.split("\n").map { it.trim() }.filter { it.isNotBlank() }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp)
    ) {
        item {
            // ACTION TOOLBAR (Print, Share, Save, Live A4 Preview)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        val current = buildCurrentDraft()
                        onDraftChange(current)
                        onPrint(current)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Print A4", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                FilledTonalButton(
                    onClick = onPreview,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Preview", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val current = buildCurrentDraft()
                        onDraftChange(current)
                        onShare(current)
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                }

                IconButton(
                    onClick = {
                        val current = buildCurrentDraft()
                        onDraftChange(current)
                        onSave(current)
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFF00796B).copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Save Draft", tint = Color(0xFF00796B))
                }
            }
        }

        item {
            // HEADER DISPATCH & DATE FIELDS
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Dispatch Info & Reference", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF00796B))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = dispatchNo,
                            onValueChange = { dispatchNo = it },
                            label = { Text("Memo / Dispatch No", fontSize = 11.sp) },
                            singleLine = true,
                            modifier = Modifier.weight(1.4f)
                        )
                        OutlinedTextField(
                            value = letterDate,
                            onValueChange = { letterDate = it },
                            label = { Text("Dated", fontSize = 11.sp) },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        item {
            // EMPLOYEE & WARD DETAILS CARD
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Employee / Addressee Info", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF00796B))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = employeeName,
                            onValueChange = { employeeName = it },
                            label = { Text("Employee Name", fontSize = 11.sp) },
                            placeholder = { Text("e.g. Farman Ullah") },
                            singleLine = true,
                            modifier = Modifier.weight(1.3f)
                        )
                        OutlinedTextField(
                            value = employeeBps,
                            onValueChange = { employeeBps = it },
                            label = { Text("BPS", fontSize = 11.sp) },
                            placeholder = { Text("16") },
                            singleLine = true,
                            modifier = Modifier.weight(0.7f)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = employeeDesig,
                            onValueChange = { employeeDesig = it },
                            label = { Text("Designation", fontSize = 11.sp) },
                            placeholder = { Text("Charge Nurse / MO / Tech") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = employeeWard,
                            onValueChange = { employeeWard = it },
                            label = { Text("Ward / Station", fontSize = 11.sp) },
                            placeholder = { Text("Emergency / ICU / OT") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        item {
            // SUBJECT & BODY
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Subject & Letter Body", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF00796B))
                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text("Subject Line (Capitalized)", fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = body,
                        onValueChange = { body = it },
                        label = { Text("Official Body Paragraphs", fontSize = 11.sp) },
                        minLines = 8,
                        maxLines = 16,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            // ENDORSEMENT COPIES (Forwarded To)
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Copies Forwarded to (One per line):", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF00796B))
                        TextButton(
                            onClick = {
                                copiesInput = """1. The Director General Health Services, Khyber Pakhtunkhwa, Peshawar.
2. The District Health Officer (DHO), Buner at Daggar.
3. The District Accounts Officer, Buner.
4. The Nursing Superintendent, Govt Cat-D Hospital Pacha Kalay.
5. Incharge ${employeeWard.ifBlank { "Emergency" }}, Govt Cat-D Hospital Pacha Kalay.
6. The Official Concerned.
7. Personal File / Office Record."""
                            }
                        ) {
                            Text("Standard KP Copies", fontSize = 10.sp)
                        }
                    }

                    OutlinedTextField(
                        value = copiesInput,
                        onValueChange = { copiesInput = it },
                        minLines = 4,
                        maxLines = 8,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// =============================================================
// HOSPITAL MULTI-WARD ROSTER BUILDER VIEW
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalRosterBuilderView(
    profile: HospitalHospitalProfile,
    monthYear: String,
    onMonthYearChange: (String) -> Unit,
    ward: String,
    onWardChange: (String) -> Unit,
    entries: List<RosterEntry>,
    onUpdateEntries: (List<RosterEntry>) -> Unit,
    onPrintRoster: () -> Unit,
    onShareRoster: () -> Unit
) {
    var showAddStaffDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp)
    ) {
        item {
            // ACTION BUTTONS (Print & Share)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onPrintRoster,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Print Roster A4", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                FilledTonalButton(
                    onClick = onShareRoster,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share Roster", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            // ROSTER HEADER CONTROLS
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Roster Month & Hospital Unit", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF00796B))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = monthYear,
                            onValueChange = onMonthYearChange,
                            label = { Text("Month & Year", fontSize = 11.sp) },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = ward,
                            onValueChange = onWardChange,
                            label = { Text("Unit / Ward", fontSize = 11.sp) },
                            singleLine = true,
                            modifier = Modifier.weight(1.3f)
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Staff Duty Allocation (${entries.size} Staff)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Button(
                    onClick = { showAddStaffDialog = true },
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Staff", fontSize = 11.sp)
                }
            }
        }

        items(entries) { entry ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, Color(0xFF00796B).copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(entry.employeeName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("${entry.designation} - ${entry.ward}", fontSize = 11.sp, color = Color(0xFF00796B))
                        }
                        IconButton(
                            onClick = {
                                val updated = entries.filter { it != entry }
                                onUpdateEntries(updated)
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.DeleteOutline, contentDescription = "Remove", tint = Color.Red, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ShiftBadge("M: ${entry.morningShift}", Color(0xFF1E88E5))
                        ShiftBadge("E: ${entry.eveningShift}", Color(0xFFFB8C00))
                        ShiftBadge("N: ${entry.nightShift}", Color(0xFF5E35B1))
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Off: ${entry.offDay}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Role: ${entry.remarks}", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF00796B))
                    }
                }
            }
        }
    }

    if (showAddStaffDialog) {
        AddStaffToRosterDialog(
            defaultWard = ward,
            onDismiss = { showAddStaffDialog = false },
            onAdd = { newEntry ->
                onUpdateEntries(entries + newEntry)
                showAddStaffDialog = false
            }
        )
    }
}

@Composable
fun ShiftBadge(text: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = color.copy(alpha = 0.12f),
        border = BorderStroke(0.5.dp, color.copy(alpha = 0.4f))
    ) {
        Text(
            text = text,
            fontSize = 9.sp,
            color = color,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}

// =============================================================
// SAVED DRAFTS & ARCHIVES VIEW
// =============================================================

@Composable
fun SavedDraftsListView(
    drafts: List<HospitalLetterDraft>,
    onOpenDraft: (HospitalLetterDraft) -> Unit,
    onPrintDraft: (HospitalLetterDraft) -> Unit,
    onDeleteDraft: (HospitalLetterDraft) -> Unit
) {
    if (drafts.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.FolderOpen,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "No Saved Drafts Yet",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Any letters, office orders, or explanations you save will appear here for 1-click reprinting and editing.",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
        ) {
            item {
                Text(
                    text = "${drafts.size} Saved Documents in Office Archive",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(drafts) { draft ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenDraft(draft) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFF00796B).copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = draft.dispatchNo.ifBlank { "Memo No. Draft" },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00796B)
                            )
                            Text(
                                text = draft.date,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = draft.subject.ifBlank { draft.title },
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            maxLines = 2
                        )

                        if (draft.employeeName.isNotBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Official: ${draft.employeeName} (${draft.employeeDesignation})",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { onDeleteDraft(draft) },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Delete", fontSize = 10.sp, color = Color.Red)
                            }

                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { onPrintDraft(draft) },
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Print A4", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// =============================================================
// LIVE A4 PREVIEW DIALOG
// =============================================================

@Composable
fun LetterLivePreviewDialog(
    draft: HospitalLetterDraft,
    onDismiss: () -> Unit,
    onPrint: () -> Unit,
    onShare: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("A4 Official Preview", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Row {
                    IconButton(onClick = onShare, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color(0xFF00796B))
                    }
                    IconButton(onClick = onPrint, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Print, contentDescription = "Print", tint = Color(0xFF00796B))
                    }
                }
            }
        },
        text = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .verticalScroll(rememberScrollState()),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // GOVERNMENT HEADER
                    Text(
                        text = draft.hospitalProfile.departmentTitle,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = draft.hospitalProfile.officeTitle,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = draft.hospitalProfile.hospitalName,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF004D40),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = draft.hospitalProfile.phoneFax,
                        fontSize = 7.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Divider(
                        color = Color.Black,
                        thickness = 1.5.dp,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )

                    // DISPATCH & DATE
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = draft.dispatchNo.ifBlank { "No. Cat-D/PKB/Admin/2026/" },
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Dated: ${draft.date}",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // RECIPIENT
                    if (draft.employeeName.isNotBlank() || draft.recipient.isNotBlank()) {
                        Text(
                            text = "To:",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "${draft.employeeName.ifBlank { draft.recipient }}",
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        if (draft.employeeDesignation.isNotBlank()) {
                            Text(
                                text = "${draft.employeeDesignation} (BPS-${draft.employeeBps.ifBlank { "16" }})",
                                fontSize = 8.5.sp,
                                color = Color.Black
                            )
                        }
                        if (draft.employeeWard.isNotBlank()) {
                            Text(
                                text = "${draft.employeeWard}, ${draft.hospitalProfile.hospitalName}",
                                fontSize = 8.5.sp,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // SUBJECT
                    Text(
                        text = "Subject:   ${draft.subject}",
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Divider(color = Color.DarkGray, thickness = 0.5.dp, modifier = Modifier.padding(bottom = 6.dp))

                    // BODY
                    Text(
                        text = resolveDraftBody(draft),
                        fontSize = 8.5.sp,
                        color = Color.Black,
                        lineHeight = 13.sp,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // SIGNATURE
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = draft.hospitalProfile.signatoryDesignation,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black,
                            textAlign = TextAlign.Right
                        )
                        Text(
                            text = draft.hospitalProfile.signatoryInstitution,
                            fontSize = 8.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Right
                        )
                    }

                    // COPIES FORWARDED
                    if (draft.copiesTo.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Endst: No. & Date Even:",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Copy forwarded for information and necessary action to:",
                            fontSize = 8.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = Color.Black
                        )
                        draft.copiesTo.forEachIndexed { idx, cp ->
                            Text(
                                text = "${idx + 1}. $cp",
                                fontSize = 7.5.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 1.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = draft.hospitalProfile.signatoryDesignation,
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Right
                            )
                            Text(
                                text = draft.hospitalProfile.hospitalName,
                                fontSize = 7.5.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Right
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onPrint,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text("Print Document")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

// =============================================================
// HOSPITAL PROFILE & ROSTER DIALOGS
// =============================================================

@Composable
fun HospitalProfileEditDialog(
    currentProfile: HospitalHospitalProfile,
    onDismiss: () -> Unit,
    onSave: (HospitalHospitalProfile) -> Unit
) {
    var hospitalName by remember { mutableStateOf(currentProfile.hospitalName) }
    var officeTitle by remember { mutableStateOf(currentProfile.officeTitle) }
    var deptTitle by remember { mutableStateOf(currentProfile.departmentTitle) }
    var phoneFax by remember { mutableStateOf(currentProfile.phoneFax) }
    var location by remember { mutableStateOf(currentProfile.location) }
    var sigName by remember { mutableStateOf(currentProfile.signatoryName) }
    var sigDesig by remember { mutableStateOf(currentProfile.signatoryDesignation) }
    var sigInst by remember { mutableStateOf(currentProfile.signatoryInstitution) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Hospital Header & MS Settings", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Configure your institution headers and default Medical Superintendent signature info:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                OutlinedTextField(value = hospitalName, onValueChange = { hospitalName = it }, label = { Text("Hospital Name", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = officeTitle, onValueChange = { officeTitle = it }, label = { Text("Office Title", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = deptTitle, onValueChange = { deptTitle = it }, label = { Text("Department", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = phoneFax, onValueChange = { phoneFax = it }, label = { Text("Phone / Email", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = sigName, onValueChange = { sigName = it }, label = { Text("MS Name", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = sigDesig, onValueChange = { sigDesig = it }, label = { Text("MS Designation", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = sigInst, onValueChange = { sigInst = it }, label = { Text("MS Institution Label", fontSize = 11.sp) }, singleLine = true)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        HospitalHospitalProfile(
                            hospitalName = hospitalName.trim(),
                            officeTitle = officeTitle.trim(),
                            departmentTitle = deptTitle.trim(),
                            phoneFax = phoneFax.trim(),
                            location = location.trim(),
                            signatoryName = sigName.trim(),
                            signatoryDesignation = sigDesig.trim(),
                            signatoryInstitution = sigInst.trim()
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text("Save Header")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddStaffToRosterDialog(
    defaultWard: String,
    onDismiss: () -> Unit,
    onAdd: (RosterEntry) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var desig by remember { mutableStateOf("Charge Nurse") }
    var ward by remember { mutableStateOf(defaultWard) }
    var morning by remember { mutableStateOf("08:00 AM - 02:00 PM") }
    var evening by remember { mutableStateOf("02:00 PM - 08:00 PM") }
    var night by remember { mutableStateOf("08:00 PM - 08:00 AM") }
    var offDay by remember { mutableStateOf("Sunday") }
    var remarks by remember { mutableStateOf("On Duty") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Staff to Shift Roster", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Staff Name", fontSize = 11.sp) }, placeholder = { Text("e.g. Asad Khan") }, singleLine = true)
                OutlinedTextField(value = desig, onValueChange = { desig = it }, label = { Text("Designation", fontSize = 11.sp) }, placeholder = { Text("MO / Nurse / Tech") }, singleLine = true)
                OutlinedTextField(value = ward, onValueChange = { ward = it }, label = { Text("Assigned Unit / Ward", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = morning, onValueChange = { morning = it }, label = { Text("Morning Shift", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = evening, onValueChange = { evening = it }, label = { Text("Evening Shift", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = night, onValueChange = { night = it }, label = { Text("Night Shift", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = offDay, onValueChange = { offDay = it }, label = { Text("Weekly Off Day", fontSize = 11.sp) }, singleLine = true)
                OutlinedTextField(value = remarks, onValueChange = { remarks = it }, label = { Text("Role / Remarks", fontSize = 11.sp) }, singleLine = true)
            }
        },
        confirmButton = {
            Button(
                enabled = name.isNotBlank(),
                onClick = {
                    onAdd(
                        RosterEntry(
                            employeeName = name.trim(),
                            designation = desig.trim(),
                            ward = ward.trim(),
                            morningShift = morning.trim(),
                            eveningShift = evening.trim(),
                            nightShift = night.trim(),
                            offDay = offDay.trim(),
                            remarks = remarks.trim()
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text("Add to Roster")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

// =============================================================
// PRINTING & PDF GENERATION ENGINE (A4 Standard)
// =============================================================

fun printHospitalA4Document(context: Context, draft: HospitalLetterDraft) {
    val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
    if (printManager == null) {
        Toast.makeText(context, "Printing service unavailable", Toast.LENGTH_SHORT).show()
        return
    }

    val jobName = "${draft.title}_${System.currentTimeMillis()}"
    val printAdapter = object : PrintDocumentAdapter() {
        private var pdfDocument: PdfDocument? = null

        override fun onLayout(
            oldAttributes: PrintAttributes?,
            newAttributes: PrintAttributes?,
            cancellationSignal: CancellationSignal?,
            callback: LayoutResultCallback?,
            extras: Bundle?
        ) {
            if (cancellationSignal?.isCanceled == true) {
                callback?.onLayoutCancelled()
                return
            }

            val info = PrintDocumentInfo.Builder("$jobName.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(1)
                .build()
            callback?.onLayoutFinished(info, true)
        }

        override fun onWrite(
            pages: Array<out PageRange>?,
            destination: ParcelFileDescriptor?,
            cancellationSignal: CancellationSignal?,
            callback: WriteResultCallback?
        ) {
            if (cancellationSignal?.isCanceled == true) {
                callback?.onWriteCancelled()
                return
            }

            try {
                val pdf = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Standard A4 points
                val page = pdf.startPage(pageInfo)

                renderA4GovernmentPage(page.canvas, draft)
                pdf.finishPage(page)

                destination?.let { pfd ->
                    FileOutputStream(pfd.fileDescriptor).use { out ->
                        pdf.writeTo(out)
                    }
                }
                pdf.close()

                callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            } catch (e: Exception) {
                e.printStackTrace()
                callback?.onWriteFailed(e.message)
            }
        }
    }

    val printAttributes = PrintAttributes.Builder()
        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
        .setResolution(PrintAttributes.Resolution("res1", "Hospital Document", 300, 300))
        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
        .build()

    printManager.print(jobName, printAdapter, printAttributes)
}

fun renderA4GovernmentPage(canvas: Canvas, draft: HospitalLetterDraft) {
    val pageW = 595f
    val pageH = 842f
    val marginX = 40f
    var currentY = 45f

    val paint = Paint().apply {
        isAntiAlias = true
        color = AndroidColor.BLACK
    }

    // 1. TOP OFFICIAL HEADER
    paint.textAlign = Paint.Align.CENTER
    paint.textSize = 9f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText(draft.hospitalProfile.departmentTitle, pageW / 2f, currentY, paint)
    currentY += 14f

    paint.textSize = 12f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText(draft.hospitalProfile.officeTitle, pageW / 2f, currentY, paint)
    currentY += 15f

    paint.textSize = 11f
    canvas.drawText(draft.hospitalProfile.hospitalName, pageW / 2f, currentY, paint)
    currentY += 13f

    paint.textSize = 7.5f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText(draft.hospitalProfile.phoneFax, pageW / 2f, currentY, paint)
    currentY += 10f

    // Header dividing line
    paint.strokeWidth = 1.5f
    canvas.drawLine(marginX, currentY, pageW - marginX, currentY, paint)
    currentY += 18f

    // 2. DISPATCH NUMBER & DATE
    paint.textAlign = Paint.Align.LEFT
    paint.textSize = 9.5f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText(draft.dispatchNo.ifBlank { "No. Cat-D/PKB/Admin/2026/" }, marginX, currentY, paint)

    paint.textAlign = Paint.Align.RIGHT
    canvas.drawText("Dated Pacha Kalay, the ${draft.date}", pageW - marginX, currentY, paint)
    currentY += 20f

    // 3. RECIPIENT
    paint.textAlign = Paint.Align.LEFT
    paint.textSize = 9.5f
    if (draft.employeeName.isNotBlank() || draft.recipient.isNotBlank()) {
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("To,", marginX, currentY, paint)
        currentY += 13f

        canvas.drawText(draft.employeeName.ifBlank { draft.recipient }, marginX + 15f, currentY, paint)
        currentY += 12f

        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        if (draft.employeeDesignation.isNotBlank()) {
            canvas.drawText("${draft.employeeDesignation} (BPS-${draft.employeeBps.ifBlank { "16" }})", marginX + 15f, currentY, paint)
            currentY += 12f
        }
        if (draft.employeeWard.isNotBlank()) {
            canvas.drawText("${draft.employeeWard}, ${draft.hospitalProfile.hospitalName}", marginX + 15f, currentY, paint)
            currentY += 12f
        }
        currentY += 6f
    }

    // 4. SUBJECT
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    paint.textSize = 10f
    val subjectLine = "Subject:    ${draft.subject}"
    canvas.drawText(subjectLine, marginX, currentY, paint)
    currentY += 6f

    paint.strokeWidth = 0.5f
    canvas.drawLine(marginX, currentY, pageW - marginX, currentY, paint)
    currentY += 18f

    // 5. BODY PARAGRAPHS (Auto wrapped)
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    paint.textSize = 9.5f
    val maxTextWidth = pageW - (marginX * 2)

    val resolvedBody = resolveDraftBody(draft)
    val bodyLines = wrapTextLines(resolvedBody, paint, maxTextWidth)

    for (line in bodyLines) {
        if (line.isBlank()) {
            currentY += 8f
        } else {
            canvas.drawText(line, marginX, currentY, paint)
            currentY += 14f
        }
    }

    currentY += 24f

    // 6. SIGNATURE BLOCK (Right aligned)
    paint.textAlign = Paint.Align.RIGHT
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    paint.textSize = 10.5f
    canvas.drawText(draft.hospitalProfile.signatoryDesignation, pageW - marginX, currentY, paint)
    currentY += 13f

    paint.textSize = 9f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText(draft.hospitalProfile.signatoryInstitution, pageW - marginX, currentY, paint)
    currentY += 22f

    // 7. ENDORSEMENT COPIES (Left aligned)
    if (draft.copiesTo.isNotEmpty()) {
        paint.textAlign = Paint.Align.LEFT
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 9f
        canvas.drawText("Endst: No. & Date Even:", marginX, currentY, paint)
        currentY += 12f

        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        paint.textSize = 8.5f
        canvas.drawText("Copy forwarded for information and necessary action to the:", marginX, currentY, paint)
        currentY += 14f

        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 8.5f
        for ((idx, cp) in draft.copiesTo.withIndex()) {
            val copyLine = "${idx + 1}. $cp"
            val copyWrapped = wrapTextLines(copyLine, paint, maxTextWidth - 10f)
            for (cLine in copyWrapped) {
                canvas.drawText(cLine, marginX + 10f, currentY, paint)
                currentY += 12f
            }
        }

        currentY += 16f
        paint.textAlign = Paint.Align.RIGHT
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 9f
        canvas.drawText(draft.hospitalProfile.signatoryDesignation, pageW - marginX, currentY, paint)
        currentY += 11f
        paint.textSize = 8f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText(draft.hospitalProfile.hospitalName, pageW - marginX, currentY, paint)
    }
}

fun wrapTextLines(text: String, paint: Paint, maxWidth: Float): List<String> {
    val lines = mutableListOf<String>()
    val rawParagraphs = text.split("\n")

    for (para in rawParagraphs) {
        if (para.isBlank()) {
            lines.add("")
            continue
        }

        val words = para.split(" ")
        var currentLine = StringBuilder()

        for (word in words) {
            val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
            val measuredWidth = paint.measureText(testLine)
            if (measuredWidth <= maxWidth) {
                currentLine = StringBuilder(testLine)
            } else {
                if (currentLine.isNotEmpty()) {
                    lines.add(currentLine.toString())
                }
                currentLine = StringBuilder(word)
            }
        }

        if (currentLine.isNotEmpty()) {
            lines.add(currentLine.toString())
        }
    }
    return lines
}

fun resolveDraftBody(draft: HospitalLetterDraft): String {
    return draft.body
        .replace("{EMPLOYEE_NAME}", draft.employeeName.ifBlank { "[Employee Name]" })
        .replace("{DESIGNATION}", draft.employeeDesignation.ifBlank { "[Designation]" })
        .replace("{BPS}", draft.employeeBps.ifBlank { "16" })
        .replace("{WARD}", draft.employeeWard.ifBlank { "Emergency / Casualty" })
        .replace("{DATE}", draft.date)
        .replace("{LEAVE_DAYS}", "03")
        .replace("{START_DATE}", draft.date)
        .replace("{END_DATE}", draft.date)
        .replace("{RELIEVER_NAME}", "Charge Nurse Incharge")
        .replace("{RELIEVER_DESIGNATION}", "Charge Nurse")
        .replace("{GOVT_NOTIF_NO}", "DGHS/Admin/HR/2026/102")
        .replace("{NOTIF_DATE}", draft.date)
        .replace("{RELIEVING_DATE}", draft.date)
        .replace("{ORDER_NO}", "DHO/BNR/Admin/2026/89")
        .replace("{ORDER_DATE}", draft.date)
        .replace("{JOINING_DATE}", draft.date)
        .replace("{SOURCE_WARD}", "Male General Ward")
        .replace("{TARGET_WARD}", draft.employeeWard.ifBlank { "Emergency / ICU Unit" })
        .replace("{INCIDENT_DETAILS}", "patient care complaint and procedural lapse")
        .replace("{INCIDENT_DATE}", draft.date)
        .replace("{CHAIRMAN_NAME}", "Dr. Muhammad Tahir")
        .replace("{CHAIRMAN_DESIG}", "Chief Medical Officer (CMO)")
        .replace("{MEMBER1_NAME}", "Farman Ali")
        .replace("{MEMBER1_DESIG}", "Nursing Superintendent")
        .replace("{MEMBER2_NAME}", "Zahid Khan")
        .replace("{MEMBER2_DESIG}", "Administrative Officer")
        .replace("{FATHER_NAME}", "Khan Muhammad")
        .replace("{CNIC_NO}", "15101-XXXXXXX-1")
        .replace("{WARD_LIST}", "Emergency, ICU, and Operation Theater")
        .replace("{COURSE_NAME}", "Post-RN / BSN Degree Program")
        .replace("{INSTITUTE_NAME}", "Khyber Medical University (KMU) Peshawar")
}

// =============================================================
// UTILITIES & PERSISTENCE
// =============================================================

fun createDraftFromTemplate(template: HospitalTemplate, profile: HospitalHospitalProfile): HospitalLetterDraft {
    val dateStr = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Date())
    return HospitalLetterDraft(
        templateId = template.id,
        title = template.title,
        dispatchNo = "No. Cat-D/PKB/Admin/2026/",
        date = dateStr,
        recipient = "Official Concerned",
        employeeName = "Farman Ullah",
        employeeDesignation = "Charge Nurse",
        employeeBps = "16",
        employeeWard = "Emergency / ICU",
        subject = template.subject,
        body = template.defaultBody,
        copiesTo = template.defaultCopies,
        signatoryName = profile.signatoryName,
        signatoryTitle = profile.signatoryDesignation,
        hospitalProfile = profile
    )
}

fun createInitialCustomDraft(profile: HospitalHospitalProfile): HospitalLetterDraft {
    val dateStr = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Date())
    return HospitalLetterDraft(
        templateId = "custom_memo",
        title = "Official Office Order / Memo",
        dispatchNo = "No. Cat-D/PKB/Admin/2026/",
        date = dateStr,
        recipient = "All Incharges / Unit Heads",
        employeeName = "",
        employeeDesignation = "",
        employeeBps = "",
        employeeWard = "",
        subject = "OFFICE CIRCULAR REGARDING HOSPITAL DUTY DISCIPLINE",
        body = """It has been observed that official instructions regarding duty timings, uniform dress code, and clinical handover protocols are not being strictly adhered to in certain units.

All Doctors, Nursing Staff, Paramedics, and Ward Support Staff of Government Category-D Hospital Pacha Kalay Buner are hereby directed to strictly observe official duty rosters and biometric attendance.

Strict disciplinary proceedings will be initiated against any official found absent or leaving their duty station without prior written permission.""",
        copiesTo = listOf(
            "The District Health Officer (DHO), Buner at Daggar.",
            "All Section In-Charges (Casualty / ICU / OT / Wards / Pharmacy / Lab).",
            "The Nursing Superintendent, Govt Cat-D Hospital Pacha Kalay.",
            "Office Notice Board / Order File."
        ),
        signatoryName = profile.signatoryName,
        signatoryTitle = profile.signatoryDesignation,
        hospitalProfile = profile
    )
}

fun generateRosterDraft(
    profile: HospitalHospitalProfile,
    monthYear: String,
    ward: String,
    entries: List<RosterEntry>
): HospitalLetterDraft {
    val dateStr = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Date())
    val sb = StringBuilder()
    sb.append("DUTY ROSTER FOR THE MONTH OF $monthYear - $ward\n\n")
    sb.append("The following staff of Government Category-D Hospital Pacha Kalay Buner is hereby assigned shifts and duty schedules for $monthYear:\n\n")

    for ((i, entry) in entries.withIndex()) {
        sb.append("${i + 1}. ${entry.employeeName} (${entry.designation})\n")
        sb.append("   - Morning Shift: ${entry.morningShift}\n")
        sb.append("   - Evening Shift: ${entry.eveningShift}\n")
        sb.append("   - Night Shift: ${entry.nightShift}\n")
        sb.append("   - Weekly Off: ${entry.offDay} | Remarks: ${entry.remarks}\n\n")
    }

    sb.append("NOTE: No staff member is allowed to change shift or exchange duty without prior written approval of the Medical Superintendent / Unit Incharge.")

    return HospitalLetterDraft(
        templateId = "roster_$ward",
        title = "Duty Roster - $ward ($monthYear)",
        dispatchNo = "No. Cat-D/PKB/Roster/2026/",
        date = dateStr,
        recipient = "All Concerned Staff ($ward)",
        employeeName = "",
        employeeDesignation = "",
        employeeBps = "",
        employeeWard = ward,
        subject = "MONTHLY DUTY ROSTER FOR $ward - $monthYear",
        body = sb.toString(),
        copiesTo = listOf(
            "The District Health Officer (DHO), Buner at Daggar.",
            "The Incharge $ward, Govt Cat-D Hospital Pacha Kalay.",
            "The Nursing Superintendent, Govt Cat-D Hospital Pacha Kalay.",
            "Official Notice Board / Office Record."
        ),
        signatoryName = profile.signatoryName,
        signatoryTitle = profile.signatoryDesignation,
        hospitalProfile = profile
    )
}

fun buildRosterText(
    profile: HospitalHospitalProfile,
    monthYear: String,
    ward: String,
    entries: List<RosterEntry>
): String {
    val sb = StringBuilder()
    sb.append("${profile.officeTitle}\n")
    sb.append("${profile.hospitalName}\n")
    sb.append("${profile.departmentTitle}\n\n")
    sb.append("DUTY ROSTER - $ward ($monthYear)\n")
    sb.append("========================================\n\n")
    for ((i, e) in entries.withIndex()) {
        sb.append("${i + 1}. ${e.employeeName} - ${e.designation}\n")
        sb.append("   Morning: ${e.morningShift}\n")
        sb.append("   Evening: ${e.eveningShift}\n")
        sb.append("   Night: ${e.nightShift}\n")
        sb.append("   Off Day: ${e.offDay} (${e.remarks})\n\n")
    }
    sb.append("Medical Superintendent\n${profile.hospitalName}")
    return sb.toString()
}

fun shareHospitalLetter(context: Context, draft: HospitalLetterDraft) {
    val sb = StringBuilder()
    sb.append("${draft.hospitalProfile.departmentTitle}\n")
    sb.append("${draft.hospitalProfile.officeTitle}\n")
    sb.append("${draft.hospitalProfile.hospitalName}\n")
    sb.append("${draft.hospitalProfile.phoneFax}\n\n")
    sb.append("${draft.dispatchNo.ifBlank { "No. Cat-D/PKB/Admin/2026/" }}       Dated: ${draft.date}\n\n")
    if (draft.employeeName.isNotBlank() || draft.recipient.isNotBlank()) {
        sb.append("To:\n${draft.employeeName.ifBlank { draft.recipient }}\n")
        if (draft.employeeDesignation.isNotBlank()) sb.append("${draft.employeeDesignation} (BPS-${draft.employeeBps.ifBlank { "16" }})\n")
        if (draft.employeeWard.isNotBlank()) sb.append("${draft.employeeWard}, ${draft.hospitalProfile.hospitalName}\n")
        sb.append("\n")
    }
    sb.append("Subject: ${draft.subject}\n\n")
    sb.append("${resolveDraftBody(draft)}\n\n")
    sb.append("Medical Superintendent\n${draft.hospitalProfile.hospitalName}\n\n")
    if (draft.copiesTo.isNotEmpty()) {
        sb.append("Endst: No. & Date Even:\nCopy forwarded to:\n")
        draft.copiesTo.forEachIndexed { i, c -> sb.append("${i + 1}. $c\n") }
        sb.append("\nMedical Superintendent\n${draft.hospitalProfile.hospitalName}")
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, draft.subject)
        putExtra(Intent.EXTRA_TEXT, sb.toString())
    }
    context.startActivity(Intent.createChooser(intent, "Share Official Letter via"))
}

fun shareText(context: Context, text: String, title: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, title)
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}

fun persistDrafts(prefs: android.content.SharedPreferences, drafts: List<HospitalLetterDraft>) {
    try {
        val arr = JSONArray()
        for (d in drafts) {
            val obj = JSONObject().apply {
                put("id", d.id)
                put("templateId", d.templateId)
                put("title", d.title)
                put("dispatchNo", d.dispatchNo)
                put("date", d.date)
                put("recipient", d.recipient)
                put("employeeName", d.employeeName)
                put("employeeDesignation", d.employeeDesignation)
                put("employeeBps", d.employeeBps)
                put("employeeWard", d.employeeWard)
                put("subject", d.subject)
                put("body", d.body)
                val copiesArr = JSONArray()
                d.copiesTo.forEach { copiesArr.put(it) }
                put("copiesTo", copiesArr)
                put("timestamp", d.timestamp)
            }
            arr.put(obj)
        }
        prefs.edit().putString("saved_hospital_drafts", arr.toString()).apply()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun loadSavedDrafts(prefs: android.content.SharedPreferences, profile: HospitalHospitalProfile): List<HospitalLetterDraft> {
    val list = mutableListOf<HospitalLetterDraft>()
    val jsonStr = prefs.getString("saved_hospital_drafts", null) ?: return emptyList()
    try {
        val arr = JSONArray(jsonStr)
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            val copiesList = mutableListOf<String>()
            val cArr = obj.optJSONArray("copiesTo")
            if (cArr != null) {
                for (j in 0 until cArr.length()) {
                    copiesList.add(cArr.getString(j))
                }
            }
            list.add(
                HospitalLetterDraft(
                    id = obj.optString("id", UUID.randomUUID().toString()),
                    templateId = obj.optString("templateId", "custom"),
                    title = obj.optString("title", "Saved Draft"),
                    dispatchNo = obj.optString("dispatchNo", ""),
                    date = obj.optString("date", ""),
                    recipient = obj.optString("recipient", ""),
                    employeeName = obj.optString("employeeName", ""),
                    employeeDesignation = obj.optString("employeeDesignation", ""),
                    employeeBps = obj.optString("employeeBps", ""),
                    employeeWard = obj.optString("employeeWard", ""),
                    subject = obj.optString("subject", ""),
                    body = obj.optString("body", ""),
                    copiesTo = copiesList,
                    signatoryName = profile.signatoryName,
                    signatoryTitle = profile.signatoryDesignation,
                    hospitalProfile = profile,
                    timestamp = obj.optLong("timestamp", System.currentTimeMillis())
                )
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return list
}
