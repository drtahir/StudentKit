package com.drtahir.studentkit.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.drtahir.studentkit.data.DutyRota
import com.drtahir.studentkit.data.DutyRotaDocxExporter
import com.drtahir.studentkit.data.DutyRotaEmblemGenerator
import com.drtahir.studentkit.viewmodel.Screen
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DutyRotaCreatorScreen(
    viewModel: StudentKitViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Preferences key for duty rota persistence
    val prefs = remember { context.getSharedPreferences("duty_rota_prefs", Context.MODE_PRIVATE) }

    var rota by remember {
        val savedJson = prefs.getString("saved_rota_json", null)
        mutableStateOf(if (savedJson != null) DutyRota.fromJson(savedJson) else DutyRota.createCatDBunerSample())
    }

    // Auto-save whenever rota changes
    LaunchedEffect(rota) {
        prefs.edit().putString("saved_rota_json", rota.toJson()).apply()
    }

    var selectedTab by remember { mutableStateOf(0) } // 0: Live Preview, 1: Duty Matrix, 2: Logo & Watermark, 3: Header & Footer
    var isExporting by remember { mutableStateOf(false) }
    var exportResultDialog by remember { mutableStateOf<Pair<String, Uri?>?>(null) } // "WORD" / "PDF" to Uri

    // Cell editing dialog state
    var editingCell by remember { mutableStateOf<Pair<String, Int>?>(null) } // Pair(Day, colIndex)
    var cellEditText by remember { mutableStateOf("") }

    // Column manager dialog state
    var showColumnDialog by remember { mutableStateOf(false) }
    var newColName by remember { mutableStateOf("") }

    // Staff manager dialog state
    var showStaffDialog by remember { mutableStateOf(false) }
    var newStaffName by remember { mutableStateOf("") }

    // Photo picker for custom logo
    val logoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                // Copy to local app internal files
                val inputStream = context.contentResolver.openInputStream(uri)
                val destFile = File(context.filesDir, "custom_rota_logo_${System.currentTimeMillis()}.png")
                val outStream = FileOutputStream(destFile)
                inputStream?.copyTo(outStream)
                inputStream?.close()
                outStream.close()
                rota = rota.copy(
                    customLogoPath = destFile.absolutePath,
                    logoPreset = "CUSTOM"
                )
                Toast.makeText(context, "Custom logo applied successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error saving logo: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Photo picker for custom stamp
    val stampPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val destFile = File(context.filesDir, "custom_rota_stamp_${System.currentTimeMillis()}.png")
                val outStream = FileOutputStream(destFile)
                inputStream?.copyTo(outStream)
                inputStream?.close()
                outStream.close()
                rota = rota.copy(
                    customStampPath = destFile.absolutePath,
                    stampPreset = "CUSTOM"
                )
                Toast.makeText(context, "Custom stamp applied successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error saving stamp: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Generate Bitmaps for Preview
    val logoBitmap = remember(rota.logoPreset, rota.customLogoPath) {
        DutyRotaDocxExporter.resolveLogoBitmap(context, rota)
    }
    val stampBitmap = remember(rota.stampPreset, rota.customStampPath, rota.hospitalName, rota.signatoryDesignation) {
        DutyRotaDocxExporter.resolveStampBitmap(context, rota)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Duty Rota & Roster Creator",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Word (.docx) & High-Res PDF Generator",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateTo(Screen.Dashboard) },
                        modifier = Modifier.testTag("rota_back_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Export Word (.docx) one-click action
                    FilledTonalButton(
                        onClick = {
                            coroutineScope.launch {
                                isExporting = true
                                val uri = withContext(Dispatchers.IO) {
                                    DutyRotaDocxExporter.exportToWordDocx(context, rota)
                                }
                                isExporting = false
                                if (uri != null) {
                                    exportResultDialog = Pair("WORD", uri)
                                } else {
                                    Toast.makeText(context, "Export to Word failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = Color(0xFF1565C0),
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("export_word_button")
                    ) {
                        Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Word (.docx)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    // Presets menu
                    var showPresetsMenu by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { showPresetsMenu = true },
                        modifier = Modifier.testTag("rota_presets_menu_button")
                    ) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Presets")
                    }

                    DropdownMenu(
                        expanded = showPresetsMenu,
                        onDismissRequest = { showPresetsMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Load Cat-D Hospital Buner OPD Sample") },
                            leadingIcon = { Icon(Icons.Default.LocalHospital, null) },
                            onClick = {
                                showPresetsMenu = false
                                rota = DutyRota.createCatDBunerSample()
                                Toast.makeText(context, "Loaded Cat-D Buner Sample Rota", Toast.LENGTH_SHORT).show()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Load 24/7 Casualty & Shift Roster") },
                            leadingIcon = { Icon(Icons.Default.Schedule, null) },
                            onClick = {
                                showPresetsMenu = false
                                rota = DutyRota.createEmergencyShiftSample()
                                Toast.makeText(context, "Loaded Emergency Shift Roster", Toast.LENGTH_SHORT).show()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Clear All Cell Assignments") },
                            leadingIcon = { Icon(Icons.Default.ClearAll, null) },
                            onClick = {
                                showPresetsMenu = false
                                rota = rota.copy(cells = emptyMap())
                                Toast.makeText(context, "Roster cleared", Toast.LENGTH_SHORT).show()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Export & Print PDF") },
                            leadingIcon = { Icon(Icons.Default.PictureAsPdf, null) },
                            onClick = {
                                showPresetsMenu = false
                                coroutineScope.launch {
                                    isExporting = true
                                    val pdfFile = withContext(Dispatchers.IO) {
                                        DutyRotaDocxExporter.exportToPdf(context, rota)
                                    }
                                    isExporting = false
                                    if (pdfFile != null) {
                                        DutyRotaDocxExporter.sharePdf(context, pdfFile)
                                    } else {
                                        Toast.makeText(context, "PDF generation failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(tonalElevation = 6.dp) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Visibility, contentDescription = "Preview") },
                    label = { Text("Preview", fontSize = 11.sp) },
                    modifier = Modifier.testTag("tab_preview")
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.TableChart, contentDescription = "Matrix") },
                    label = { Text("Roster Grid", fontSize = 11.sp) },
                    modifier = Modifier.testTag("tab_matrix")
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.FilterFrames, contentDescription = "Logo & Watermark") },
                    label = { Text("Logo & Mark", fontSize = 11.sp) },
                    modifier = Modifier.testTag("tab_logo")
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.EditNote, contentDescription = "Header & Footer") },
                    label = { Text("Head/Footer", fontSize = 11.sp) },
                    modifier = Modifier.testTag("tab_header")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> LivePreviewTab(
                    rota = rota,
                    logoBitmap = logoBitmap,
                    stampBitmap = stampBitmap,
                    onCellClick = { day, cIdx ->
                        editingCell = Pair(day, cIdx)
                        cellEditText = rota.getCell(day, cIdx)
                    },
                    onExportWord = {
                        coroutineScope.launch {
                            isExporting = true
                            val uri = withContext(Dispatchers.IO) {
                                DutyRotaDocxExporter.exportToWordDocx(context, rota)
                            }
                            isExporting = false
                            if (uri != null) {
                                exportResultDialog = Pair("WORD", uri)
                            } else {
                                Toast.makeText(context, "Export to Word failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onExportPdf = {
                        coroutineScope.launch {
                            isExporting = true
                            val pdfFile = withContext(Dispatchers.IO) {
                                DutyRotaDocxExporter.exportToPdf(context, rota)
                            }
                            isExporting = false
                            if (pdfFile != null) {
                                DutyRotaDocxExporter.sharePdf(context, pdfFile)
                            } else {
                                Toast.makeText(context, "PDF generation failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
                1 -> RosterMatrixTab(
                    rota = rota,
                    onCellClick = { day, cIdx ->
                        editingCell = Pair(day, cIdx)
                        cellEditText = rota.getCell(day, cIdx)
                    },
                    onManageColumns = { showColumnDialog = true },
                    onManageStaff = { showStaffDialog = true },
                    onAddDay = {
                        val allDays = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
                        val nextDay = allDays.firstOrNull { !rota.days.contains(it) } ?: "Day ${rota.days.size + 1}"
                        rota = rota.copy(days = rota.days + nextDay)
                    },
                    onRemoveDay = { day ->
                        if (rota.days.size > 1) {
                            rota = rota.copy(days = rota.days.filter { it != day })
                        } else {
                            Toast.makeText(context, "Roster must have at least 1 day", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                2 -> LogoAndWatermarkTab(
                    rota = rota,
                    logoBitmap = logoBitmap,
                    onUpdateRota = { rota = it },
                    onPickLogo = {
                        logoPicker.launch(
                            androidx.activity.result.PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
                3 -> HeaderAndFooterTab(
                    rota = rota,
                    stampBitmap = stampBitmap,
                    onUpdateRota = { rota = it },
                    onPickStamp = {
                        stampPicker.launch(
                            androidx.activity.result.PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
            }

            // Exporting loader indicator
            if (isExporting) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black.copy(alpha = 0.4f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier.padding(24.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                                Column {
                                    Text("Generating High-Quality Rota...", fontWeight = FontWeight.Bold)
                                    Text("Packaging OpenXML / Document tables", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Cell Editing Dialog
    editingCell?.let { (day, colIdx) ->
        val colName = rota.columns.getOrNull(colIdx) ?: "Duty"
        AlertDialog(
            onDismissRequest = { editingCell = null },
            title = {
                Text("Assign: $day - ${colName.replace("\n", " ")}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = cellEditText,
                        onValueChange = { cellEditText = it },
                        label = { Text("Assigned Doctor(s) / Staff") },
                        placeholder = { Text("e.g. Dr SaadUllah\nDr Harinder Kumar") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { cellEditText = "OFF" },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Mark OFF")
                        }
                        OutlinedButton(
                            onClick = { cellEditText = "" },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Clear")
                        }
                    }

                    Text("Quick Pick from Staff Pool (Tap to add/toggle):", fontSize = 12.sp, fontWeight = FontWeight.Bold)

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        for (staff in rota.staffPool) {
                            val isPresent = cellEditText.contains(staff)
                            FilterChip(
                                selected = isPresent,
                                onClick = {
                                    cellEditText = if (isPresent) {
                                        cellEditText.replace(staff, "").replace("\n\n", "\n").trim()
                                    } else {
                                        if (cellEditText.isBlank() || cellEditText == "OFF") staff else "$cellEditText\n$staff"
                                    }
                                },
                                label = { Text(staff, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        rota = rota.withCell(day, colIdx, cellEditText)
                        editingCell = null
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingCell = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Manage Columns Dialog
    if (showColumnDialog) {
        AlertDialog(
            onDismissRequest = { showColumnDialog = false },
            title = { Text("Manage Duty Columns (Departments / Wards)", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Current Columns (${rota.columns.size}):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                    rota.columns.forEachIndexed { index, col ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${index + 1}. ${col.replace("\n", " ")}",
                                modifier = Modifier.weight(1f),
                                fontSize = 13.sp
                            )
                            IconButton(
                                onClick = {
                                    if (rota.columns.size > 1) {
                                        rota = rota.copy(columns = rota.columns.filterIndexed { i, _ -> i != index })
                                    } else {
                                        Toast.makeText(context, "At least 1 column required", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    Text("Add New Column / Department:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = newColName,
                        onValueChange = { newColName = it },
                        label = { Text("Column Title (e.g. ICU, Dialysis)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            if (newColName.isNotBlank()) {
                                rota = rota.copy(columns = rota.columns + newColName.trim())
                                newColName = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add Column")
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showColumnDialog = false }) {
                    Text("Done")
                }
            }
        )
    }

    // Manage Staff Pool Dialog
    if (showStaffDialog) {
        AlertDialog(
            onDismissRequest = { showStaffDialog = false },
            title = { Text("Manage Staff / Doctor Pool", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Staff List (${rota.staffPool.size}):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 240.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(rota.staffPool) { staff ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(staff, modifier = Modifier.weight(1f), fontSize = 13.sp)
                                IconButton(
                                    onClick = {
                                        rota = rota.copy(staffPool = rota.staffPool.filter { it != staff })
                                    }
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    Text("Add Doctor / Staff:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = newStaffName,
                        onValueChange = { newStaffName = it },
                        label = { Text("Name (e.g. Dr. Tahir Khan)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            if (newStaffName.isNotBlank()) {
                                rota = rota.copy(staffPool = rota.staffPool + newStaffName.trim())
                                newStaffName = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add to Pool")
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showStaffDialog = false }) {
                    Text("Done")
                }
            }
        )
    }

    // Export Success Dialog
    exportResultDialog?.let { (type, uri) ->
        AlertDialog(
            onDismissRequest = { exportResultDialog = null },
            icon = {
                Icon(
                    imageVector = if (type == "WORD") Icons.Default.Description else Icons.Default.PictureAsPdf,
                    contentDescription = null,
                    tint = if (type == "WORD") Color(0xFF1565C0) else Color(0xFFD32F2F),
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = if (type == "WORD") "Word Roster Created!" else "PDF Roster Exported!",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        if (type == "WORD")
                            "Your editable Word document (.docx) was generated with headers, tables, official emblem logo, and stamp."
                        else
                            "High-resolution printable PDF was compiled with background watermark and official seal."
                    )
                    Text("Saved to device: Downloads folder", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (uri != null && type == "WORD") {
                        Button(
                            onClick = {
                                DutyRotaDocxExporter.openInWord(context, uri)
                                exportResultDialog = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                        ) {
                            Text("Open in Word")
                        }
                        OutlinedButton(
                            onClick = {
                                DutyRotaDocxExporter.shareDocx(context, uri)
                                exportResultDialog = null
                            }
                        ) {
                            Text("Share")
                        }
                    } else {
                        Button(onClick = { exportResultDialog = null }) {
                            Text("OK")
                        }
                    }
                }
            }
        )
    }
}

/**
 * Tab 1: Live Interactive WYSIWYG Preview
 */
@Composable
private fun LivePreviewTab(
    rota: DutyRota,
    logoBitmap: Bitmap,
    stampBitmap: Bitmap,
    onCellClick: (String, Int) -> Unit,
    onExportWord: () -> Unit,
    onExportPdf: () -> Unit
) {
    val horizontalScroll = rememberScrollState()
    val verticalScroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
    ) {
        // Quick Action Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.TouchApp, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Tap any cell to edit doctor / staff", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = onExportPdf,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Icon(Icons.Default.PictureAsPdf, null, modifier = Modifier.size(14.dp), tint = Color(0xFFD32F2F))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("PDF Print", fontSize = 11.sp)
                }
                Button(
                    onClick = onExportWord,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                    modifier = Modifier.height(34.dp)
                ) {
                    Icon(Icons.Default.Download, null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Save .docx", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Paper Container (White paper with shadow, landscape proportions)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(verticalScroll),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(horizontalScroll)
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(min = 900.dp)
                        .padding(24.dp)
                ) {
                    // 1. Centered Background Watermark
                    if (rota.showWatermark && rota.logoPreset != "NONE") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                bitmap = logoBitmap.asImageBitmap(),
                                contentDescription = "Watermark",
                                modifier = Modifier
                                    .size(rota.watermarkSizeDp.dp)
                                    .alpha(rota.watermarkOpacity)
                            )
                        }
                    }

                    // 2. Foreground Content
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Header section with Logo
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Left spacer for balance
                            Spacer(modifier = Modifier.width(rota.logoSizeDp.dp))

                            // Center Titles
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = rota.hospitalName.uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Serif,
                                    color = Color(0xFF1A202C),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = rota.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Serif,
                                    color = Color(0xFF2D3748),
                                    textAlign = TextAlign.Center,
                                    textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                                )
                                if (rota.subHeader.isNotBlank() || rota.referenceNumber.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = listOfNotNull(
                                            rota.subHeader.takeIf { it.isNotBlank() },
                                            rota.referenceNumber.takeIf { it.isNotBlank() },
                                            ("Dated: ${rota.issueDate}").takeIf { rota.issueDate.isNotBlank() }
                                        ).joinToString("  |  "),
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily.Serif,
                                        color = Color(0xFF718096),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            // Right Logo
                            if (rota.logoPreset != "NONE") {
                                Image(
                                    bitmap = logoBitmap.asImageBitmap(),
                                    contentDescription = "Emblem Logo",
                                    modifier = Modifier
                                        .size(rota.logoSizeDp.dp)
                                        .alpha(rota.logoOpacity)
                                )
                            } else {
                                Spacer(modifier = Modifier.width(rota.logoSizeDp.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Table Grid
                        TablePreviewGrid(
                            rota = rota,
                            onCellClick = onCellClick
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Footer Section: Copies to (left) and Stamp & Signatory (right)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            // Left: Distribution List
                            Column(modifier = Modifier.weight(1f)) {
                                if (rota.copiesTo.isNotEmpty()) {
                                    Text(
                                        text = "Copy forwarded for information to:",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Serif,
                                        color = Color(0xFF2D3748),
                                        textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    rota.copiesTo.forEach { cp ->
                                        Text(
                                            text = cp,
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily.Serif,
                                            color = Color(0xFF4A5568)
                                        )
                                    }
                                }
                            }

                            // Right: Stamp & Signature Authority
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                if (rota.stampPreset != "NONE") {
                                    Image(
                                        bitmap = stampBitmap.asImageBitmap(),
                                        contentDescription = "Official Stamp",
                                        modifier = Modifier
                                            .size(rota.stampSizeDp.dp)
                                            .alpha(rota.stampOpacity)
                                    )
                                }
                                Text(
                                    text = rota.signatoryDesignation,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Serif,
                                    color = Color(0xFF1A202C)
                                )
                                Text(
                                    text = rota.signatoryInstitution,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Serif,
                                    color = Color(0xFF4A5568)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Grid Component for Live Paper Preview
 */
@Composable
private fun TablePreviewGrid(
    rota: DutyRota,
    onCellClick: (String, Int) -> Unit
) {
    val borderColor = Color.Black
    val headerBg = Color(0xFFF2F4F8)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, borderColor)
    ) {
        // Table Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerBg)
        ) {
            // Days Header Cell
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .heightIn(min = 38.dp)
                    .border(0.75.dp, borderColor)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Days", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Black)
            }

            // Columns Header Cells
            rota.columns.forEachIndexed { _, colTitle ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 38.dp)
                        .border(0.75.dp, borderColor)
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = colTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 13.sp
                    )
                }
            }
        }

        // Table Data Rows
        rota.days.forEach { day ->
            Row(modifier = Modifier.fillMaxWidth()) {
                // Day Column Cell
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .heightIn(min = 52.dp)
                        .border(0.75.dp, borderColor)
                        .background(Color.White.copy(alpha = 0.9f))
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                // Department Cells
                rota.columns.forEachIndexed { cIdx, _ ->
                    val cellText = rota.getCell(day, cIdx)
                    val isOff = cellText.equals("OFF", ignoreCase = true)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 52.dp)
                            .border(0.75.dp, borderColor)
                            .background(if (isOff) Color(0xFFFAFAFA).copy(alpha = 0.85f) else Color.White.copy(alpha = 0.85f))
                            .clickable { onCellClick(day, cIdx) }
                            .padding(horizontal = 4.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isOff) {
                            Text(
                                text = "OFF",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        } else if (cellText.isNotBlank()) {
                            Text(
                                text = cellText,
                                fontSize = 10.5.sp,
                                color = Color(0xFF1A202C),
                                textAlign = TextAlign.Center,
                                lineHeight = 12.5.sp
                            )
                        } else {
                            Text(
                                text = "+ Add",
                                fontSize = 9.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Tab 2: Roster Matrix & Data Entry Editor
 */
@Composable
private fun RosterMatrixTab(
    rota: DutyRota,
    onCellClick: (String, Int) -> Unit,
    onManageColumns: () -> Unit,
    onManageStaff: () -> Unit,
    onAddDay: () -> Unit,
    onRemoveDay: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Management Buttons Bar
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Roster Structure", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("${rota.days.size} Days × ${rota.columns.size} Duty Columns", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onManageColumns,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.ViewColumn, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Columns (${rota.columns.size})", fontSize = 12.sp)
                        }
                        OutlinedButton(
                            onClick = onManageStaff,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Group, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Staff Pool (${rota.staffPool.size})", fontSize = 12.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onAddDay,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Day", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Quick Day-by-Day Roster Editor Cards
        items(rota.days) { day ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CalendarToday, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(day, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        IconButton(
                            onClick = { onRemoveDay(day) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Remove Day", tint = Color.Gray, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // List of departments for this day
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        rota.columns.forEachIndexed { cIdx, col ->
                            val cellText = rota.getCell(day, cIdx)
                            val isOff = cellText.equals("OFF", ignoreCase = true)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isOff) Color(0xFFFFEBEE)
                                        else if (cellText.isNotBlank()) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                    )
                                    .clickable { onCellClick(day, cIdx) }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = col.replace("\n", " "),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(0.45f)
                                )
                                Text(
                                    text = if (cellText.isBlank()) "Tap to assign..." else cellText.replace("\n", " | "),
                                    fontSize = 12.sp,
                                    fontWeight = if (isOff) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isOff) Color(0xFFC62828) else if (cellText.isBlank()) Color.Gray else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.weight(0.55f),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Tab 3: Logo & Watermark Settings (Sliders, Opacity, Size fixation)
 */
@Composable
private fun LogoAndWatermarkTab(
    rota: DutyRota,
    logoBitmap: Bitmap,
    onUpdateRota: (DutyRota) -> Unit,
    onPickLogo: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Emblem Preview Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Current Emblem / Logo Preview", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(1.dp, Color.LightGray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = logoBitmap.asImageBitmap(),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(rota.logoSizeDp.dp)
                                .alpha(rota.logoOpacity)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = when (rota.logoPreset) {
                            "HOSPITAL_BUNER" -> "Cat-D Hospital Buner Official Emblem (Sample Match)"
                            "CADUCEUS_HEALTH" -> "Medical Caduceus & Health Shield"
                            "CUSTOM" -> "Custom Uploaded Logo"
                            else -> "Institutional Health Crest"
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Preset Selector
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Select Logo / Emblem Preset:", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = rota.logoPreset == "HOSPITAL_BUNER",
                            onClick = { onUpdateRota(rota.copy(logoPreset = "HOSPITAL_BUNER", customLogoPath = null)) },
                            label = { Text("Cat-D Buner") },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = rota.logoPreset == "CADUCEUS_HEALTH",
                            onClick = { onUpdateRota(rota.copy(logoPreset = "CADUCEUS_HEALTH", customLogoPath = null)) },
                            label = { Text("Medical Caduceus") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onPickLogo,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.CloudUpload, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Upload Custom Logo")
                        }
                    }
                }
            }
        }

        // Header Logo Controls: Size Fixation & Opacity
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("Header Logo Fixation & Controls", fontWeight = FontWeight.Bold, fontSize = 15.sp)

                    // Logo Size Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Logo Size", fontSize = 13.sp)
                            Text("${rota.logoSizeDp.toInt()} dp", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Slider(
                            value = rota.logoSizeDp,
                            onValueChange = { onUpdateRota(rota.copy(logoSizeDp = it)) },
                            valueRange = 40f..120f,
                            steps = 8
                        )
                    }

                    // Logo Opacity Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Logo Opacity", fontSize = 13.sp)
                            Text("${(rota.logoOpacity * 100).toInt()}%", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Slider(
                            value = rota.logoOpacity,
                            onValueChange = { onUpdateRota(rota.copy(logoOpacity = it)) },
                            valueRange = 0.2f..1.0f
                        )
                    }
                }
            }
        }

        // Background Watermark Controls: Size & Opacity
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Background Watermark", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("Draws emblem behind duty table", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = rota.showWatermark,
                            onCheckedChange = { onUpdateRota(rota.copy(showWatermark = it)) }
                        )
                    }

                    if (rota.showWatermark) {
                        // Watermark Opacity Slider
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Watermark Opacity (Subtle & Light)", fontSize = 13.sp)
                                Text("${(rota.watermarkOpacity * 100).toInt()}%", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Slider(
                                value = rota.watermarkOpacity,
                                onValueChange = { onUpdateRota(rota.copy(watermarkOpacity = it)) },
                                valueRange = 0.05f..0.40f
                            )
                        }

                        // Watermark Size Scale Slider
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Watermark Scale", fontSize = 13.sp)
                                Text("${rota.watermarkSizeDp.toInt()} dp", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Slider(
                                value = rota.watermarkSizeDp,
                                onValueChange = { onUpdateRota(rota.copy(watermarkSizeDp = it)) },
                                valueRange = 150f..400f
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Tab 4: Header & Footer Text, Stamp & Distribution List
 */
@Composable
private fun HeaderAndFooterTab(
    rota: DutyRota,
    stampBitmap: Bitmap,
    onUpdateRota: (DutyRota) -> Unit,
    onPickStamp: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Document Header Information
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Header Texts", fontWeight = FontWeight.Bold, fontSize = 15.sp)

                    OutlinedTextField(
                        value = rota.hospitalName,
                        onValueChange = { onUpdateRota(rota.copy(hospitalName = it)) },
                        label = { Text("Hospital / Organization Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = rota.title,
                        onValueChange = { onUpdateRota(rota.copy(title = it)) },
                        label = { Text("Rota Title / Subject") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = rota.subHeader,
                        onValueChange = { onUpdateRota(rota.copy(subHeader = it)) },
                        label = { Text("Sub-header (e.g. Office of the MS)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = rota.referenceNumber,
                            onValueChange = { onUpdateRota(rota.copy(referenceNumber = it)) },
                            label = { Text("Ref / Order No.") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = rota.issueDate,
                            onValueChange = { onUpdateRota(rota.copy(issueDate = it)) },
                            label = { Text("Issue Date") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Footer Authority & Stamp
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Footer Authority & Official Stamp", fontWeight = FontWeight.Bold, fontSize = 15.sp)

                    // Stamp Preview & Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(1.dp, Color.LightGray, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (rota.stampPreset != "NONE") {
                                Image(
                                    bitmap = stampBitmap.asImageBitmap(),
                                    contentDescription = "Stamp",
                                    modifier = Modifier
                                        .size(rota.stampSizeDp.dp)
                                        .alpha(rota.stampOpacity)
                                )
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                FilterChip(
                                    selected = rota.stampPreset == "CIRCULAR_SEAL",
                                    onClick = { onUpdateRota(rota.copy(stampPreset = "CIRCULAR_SEAL")) },
                                    label = { Text("Official Seal", fontSize = 11.sp) }
                                )
                                FilterChip(
                                    selected = rota.stampPreset == "NONE",
                                    onClick = { onUpdateRota(rota.copy(stampPreset = "NONE")) },
                                    label = { Text("No Stamp", fontSize = 11.sp) }
                                )
                            }
                            OutlinedButton(
                                onClick = onPickStamp,
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(Icons.Default.Upload, null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Upload Custom Stamp", fontSize = 11.sp)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = rota.signatoryDesignation,
                        onValueChange = { onUpdateRota(rota.copy(signatoryDesignation = it)) },
                        label = { Text("Signatory Designation") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = rota.signatoryInstitution,
                        onValueChange = { onUpdateRota(rota.copy(signatoryInstitution = it)) },
                        label = { Text("Institution / Location") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Copies to / Distribution list
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Copy Forwarded for Information to (Distribution):", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                    rota.copiesTo.forEachIndexed { index, cp ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(cp, fontSize = 12.sp, modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    onUpdateRota(rota.copy(copiesTo = rota.copiesTo.filterIndexed { i, _ -> i != index }))
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    var newCopyText by remember { mutableStateOf("") }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = newCopyText,
                            onValueChange = { newCopyText = it },
                            placeholder = { Text("e.g. 5. Office Record File") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        IconButton(
                            onClick = {
                                if (newCopyText.isNotBlank()) {
                                    onUpdateRota(rota.copy(copiesTo = rota.copiesTo + newCopyText.trim()))
                                    newCopyText = ""
                                }
                            }
                        ) {
                            Icon(Icons.Default.AddCircle, contentDescription = "Add", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}
