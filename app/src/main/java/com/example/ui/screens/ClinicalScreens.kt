package com.example.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.StudentKitViewModel
import kotlinx.coroutines.delay

// =============================================================================
// MODULE 10: PROFESSIONAL CLINICAL & MEDICAL CALCULATORS
// =============================================================================

/**
 * 1. IV INFUSION RATE & DROP RATE CALCULATOR (Nursing & Clinical)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IvCalculatorScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    
    var volumeText by remember { mutableStateOf("1000") } // mL
    var hoursText by remember { mutableStateOf("8") }
    var minutesText by remember { mutableStateOf("0") }
    var dropFactorText by remember { mutableStateOf("15") } // gtt/mL (standard standard)
    
    // Drop factor presets
    val dropFactorPresets = listOf(
        Pair("10 (Macrodrip)", "10"),
        Pair("15 (Standard)", "15"),
        Pair("20 (Blood)", "20"),
        Pair("60 (Microdrip)", "60")
    )

    val volume = volumeText.toDoubleOrNull() ?: 0.0
    val hours = hoursText.toDoubleOrNull() ?: 0.0
    val minutes = minutesText.toDoubleOrNull() ?: 0.0
    val dropFactor = dropFactorText.toDoubleOrNull() ?: 15.0

    val totalMinutes = (hours * 60.0) + minutes
    val totalHours = totalMinutes / 60.0

    // Calculations
    val infusionRateMlLhr = if (totalHours > 0) volume / totalHours else 0.0
    val dropRateGttMin = if (totalMinutes > 0) (volume * dropFactor) / totalMinutes else 0.0

    // Drop rhythm animator
    var dropTrigger by remember { mutableStateOf(false) }
    val dropIntervalMs = if (dropRateGttMin > 0) (60000.0 / dropRateGttMin).toLong() else 0L

    LaunchedEffect(dropIntervalMs) {
        if (dropIntervalMs > 100) { // Safety limit for animation
            while (true) {
                dropTrigger = !dropTrigger
                delay(dropIntervalMs / 2)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "IV Infusion & Drop Rate Solver",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Calculate medical drip rates (gtt/min) and hourly infusion flow rates accurately.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Main Calculation Results Card
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "🎯 CALCULATED INFUSION FLOW RATES",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    letterSpacing = 1.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // mL/hr Card
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text("Flow Rate", fontSize = 11.sp, color = Color.Gray)
                        Text(
                            text = if (infusionRateMlLhr.isNaN() || infusionRateMlLhr.isInfinite()) "0.0" else String.format("%.1f", infusionRateMlLhr),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text("mL / hour", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    }

                    // Drops / Min Card
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text("Drop Rate", fontSize = 11.sp, color = Color.Gray)
                        Text(
                            text = if (dropRateGttMin.isNaN() || dropRateGttMin.isInfinite()) "0" else String.format("%.0f", dropRateGttMin),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF00C853)
                        )
                        Text("gtt / minute", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF00C853))
                    }
                }

                // Interactive Drop Rhythm Simulator
                if (dropIntervalMs > 100) {
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Flashing drop anim
                        val pulseScale by animateFloatAsState(
                            targetValue = if (dropTrigger) 1.25f else 0.85f,
                            animationSpec = tween(durationMillis = (dropIntervalMs / 2).toInt().coerceAtMost(500)),
                            label = "Drop Pulse"
                        )
                        
                        Icon(
                            imageVector = Icons.Default.WaterDrop,
                            contentDescription = "Pulsing Drop",
                            tint = if (dropTrigger) Color(0xFF1E88E5) else Color(0xFFB0BEC5),
                            modifier = Modifier
                                .size(28.dp)
                                .align(Alignment.CenterVertically)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column {
                            Text("Drip Simulator Active", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Text("Pulsing at ~${String.format("%.0f", dropRateGttMin)} drops/min (1 beat per ${String.format("%.1f", dropIntervalMs / 1000.0)}s)", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        // Inputs Card
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "✏️ Enter Prescription Parameters",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                // Volume Input
                OutlinedTextField(
                    value = volumeText,
                    onValueChange = { volumeText = it.filter { char -> char.isDigit() || char == '.' } },
                    label = { Text("Total Solution Volume (mL)") },
                    leadingIcon = { Icon(Icons.Default.Opacity, null) },
                    suffix = { Text("mL") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Infusion Time Input Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = hoursText,
                        onValueChange = { hoursText = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text("Hours") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = minutesText,
                        onValueChange = { minutesText = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text("Minutes") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                // Drop Factor Input
                OutlinedTextField(
                    value = dropFactorText,
                    onValueChange = { dropFactorText = it.filter { char -> char.isDigit() || char == '.' } },
                    label = { Text("Drop Factor (gtt/mL)") },
                    leadingIcon = { Icon(Icons.Default.FormatListNumbered, null) },
                    suffix = { Text("gtt/mL") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Drop factor presets selection
                Text("Standard Drop Factor Presets:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    dropFactorPresets.forEach { (label, value) ->
                        val isSelected = dropFactorText == value
                        ElevatedFilterChip(
                            selected = isSelected,
                            onClick = { dropFactorText = value },
                            label = { Text(label, fontSize = 10.sp) }
                        )
                    }
                }
            }
        }

        // Clinical Guidelines and Presets Cheat Sheet
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Clinical Infusion Cheat Sheet", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                }

                Text("• Macrodrip Sets (10, 15, 20 gtt/mL): Typically used for adult patients with routine fluid hydration or blood products.", fontSize = 10.5.sp, color = Color.DarkGray)
                Text("• Microdrip Sets (60 gtt/mL): Standard for pediatric therapy and critical medications requiring hyper-precise drip measurements (1 mL/hr = 1 gtt/min).", fontSize = 10.5.sp, color = Color.DarkGray)
                Text("• Double Check Rule: Always cross-reference manual drip rates with mechanical syringe driver infusion pumps in highly sensitive critical wards.", fontSize = 10.5.sp, color = Color.DarkGray)
            }
        }
    }
}


/**
 * 2. CLINICAL DRUG DOSAGE CALCULATOR (Pharmacy & Medicine)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DosageCalculatorScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current

    var patientWeightText by remember { mutableStateOf("15") } // kg
    var weightUnitIsKg by remember { mutableStateOf(true) } // true = kg, false = lbs
    
    var prescribeTypeDose by remember { mutableStateOf(true) } // true = mg/kg/dose, false = mg/kg/day
    var dosageRateText by remember { mutableStateOf("5") } // mg/kg
    
    var concentrationMgText by remember { mutableStateOf("100") } // mg
    var concentrationMlText by remember { mutableStateOf("5") } // per mL (e.g. 100mg per 5mL)
    
    var dailyFrequency by remember { mutableStateOf(3) } // TID (3 times/day)
    val frequencies = listOf(
        Triple("Once Daily (QD)", 1, "Every 24 hours"),
        Triple("Twice Daily (BID)", 2, "Every 12 hours"),
        Triple("Three Times (TID)", 3, "Every 8 hours"),
        Triple("Four Times (QID)", 4, "Every 6 hours")
    )

    // Parse and adjust weight to kg for calculations
    val rawWeight = patientWeightText.toDoubleOrNull() ?: 0.0
    val weightInKg = if (weightUnitIsKg) rawWeight else rawWeight * 0.45359237
    val dosageRate = dosageRateText.toDoubleOrNull() ?: 0.0
    
    val concMg = concentrationMgText.toDoubleOrNull() ?: 1.0
    val concMl = concentrationMlText.toDoubleOrNull() ?: 1.0
    val concentrationRatio = if (concMg > 0) concMl / concMg else 0.0

    // Dose Calculations
    val calculatedDoseMg = weightInKg * dosageRate
    
    val (dosePerDoseMg, dosePerDayMg) = if (prescribeTypeDose) {
        Pair(calculatedDoseMg, calculatedDoseMg * dailyFrequency)
    } else {
        Pair(calculatedDoseMg / dailyFrequency, calculatedDoseMg)
    }

    val doseVolumeMl = dosePerDoseMg * concentrationRatio
    val dailyVolumeMl = dosePerDayMg * concentrationRatio

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MedicalServices,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Clinical Drug Dosage Solver",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Calculate patient body-weight dosages, concentrations, and administration volumes.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Output "Prescription Summary" Card
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "📄 PHARMACEUTICAL DISPENSING SLIP",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    letterSpacing = 1.sp
                )

                // Large Administer Value Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("SINGLE ADMINISTER VOLUME", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (doseVolumeMl.isNaN() || doseVolumeMl.isInfinite()) "0.00 mL" else String.format("%.2f mL", doseVolumeMl),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Equivalent to: ${if (dosePerDoseMg.isNaN() || dosePerDoseMg.isInfinite()) "0.0" else String.format("%.1f mg", dosePerDoseMg)} per single dose",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                }

                // Grid stats details
                Divider(color = Color.LightGray.copy(alpha = 0.5f))
                
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Total Daily Dose (mg)", fontSize = 10.sp, color = Color.Gray)
                        Text(
                            text = if (dosePerDayMg.isNaN() || dosePerDayMg.isInfinite()) "0.0 mg" else String.format("%.1f mg", dosePerDayMg),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Total Daily Vol (mL)", fontSize = 10.sp, color = Color.Gray)
                        Text(
                            text = if (dailyVolumeMl.isNaN() || dailyVolumeMl.isInfinite()) "0.0 mL" else String.format("%.2f mL", dailyVolumeMl),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Frequency Interval", fontSize = 10.sp, color = Color.Gray)
                        Text(
                            text = "Every ${24 / dailyFrequency} hours",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Patient Weight", fontSize = 10.sp, color = Color.Gray)
                        Text(
                            text = "${String.format("%.1f", weightInKg)} kg (${String.format("%.1f", weightInKg * 2.20462)} lbs)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Inputs Card
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "⚙️ Configure Dosage Parameters",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                // Weight Input + Unit Select
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = patientWeightText,
                        onValueChange = { patientWeightText = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text("Patient Weight") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1.5f),
                        singleLine = true
                    )
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Unit", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        Row {
                            ElevatedFilterChip(
                                selected = weightUnitIsKg,
                                onClick = { weightUnitIsKg = true },
                                label = { Text("kg", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            ElevatedFilterChip(
                                selected = !weightUnitIsKg,
                                onClick = { weightUnitIsKg = false },
                                label = { Text("lbs", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Dosage Rate Input + Unit Select
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = dosageRateText,
                        onValueChange = { dosageRateText = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text("Dosage Rate") },
                        suffix = { Text("mg/kg") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1.5f),
                        singleLine = true
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Basis", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        Row {
                            ElevatedFilterChip(
                                selected = prescribeTypeDose,
                                onClick = { prescribeTypeDose = true },
                                label = { Text("Dose", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            ElevatedFilterChip(
                                selected = !prescribeTypeDose,
                                onClick = { prescribeTypeDose = false },
                                label = { Text("Day", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Concentration Ratio (e.g. 125 mg per 5 mL)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = concentrationMgText,
                        onValueChange = { concentrationMgText = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text("Available Drug") },
                        suffix = { Text("mg") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = concentrationMlText,
                        onValueChange = { concentrationMlText = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text("In Volume") },
                        suffix = { Text("mL") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                // Daily Schedule / Frequency Select
                Text("Schedule Frequency:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    frequencies.forEach { (label, count, schedule) ->
                        val isSelected = dailyFrequency == count
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f) else Color.Transparent)
                                .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.LightGray.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                .clickable { dailyFrequency = count }
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = isSelected, onClick = { dailyFrequency = count })
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(schedule, fontSize = 10.5.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}


/**
 * 3. ESTIMATED GFR & CREATININE CLEARANCE SOLVER (Cockcroft-Gault Kidney Function)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GfrCalculatorScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current

    var ageText by remember { mutableStateOf("45") }
    var weightText by remember { mutableStateOf("70") } // kg
    var creatinineText by remember { mutableStateOf("1.1") } // mg/dL
    var isMale by remember { mutableStateOf(true) }

    val age = ageText.toDoubleOrNull() ?: 0.0
    val weight = weightText.toDoubleOrNull() ?: 0.0
    val creatinine = creatinineText.toDoubleOrNull() ?: 0.0

    // Cockcroft-Gault formula
    val rawCrCl = if (creatinine > 0) {
        ((140 - age) * weight) / (72 * creatinine)
    } else {
        0.0
    }
    
    val estimatedCrCl = if (isMale) rawCrCl else rawCrCl * 0.85

    // Chronic Kidney Disease staging interpretation
    val (stageText, stageDesc, stageColor) = when {
        estimatedCrCl >= 90.0 -> Triple("Stage 1 CKD", "Normal or High renal function. Safe standard drug clearings.", Color(0xFF2E7D32))
        estimatedCrCl >= 60.0 -> Triple("Stage 2 CKD", "Mildly decreased renal clearance. Routine diagnostic observation.", Color(0xFF4CAF50))
        estimatedCrCl >= 30.0 -> Triple("Stage 3 CKD", "Moderately decreased renal clearance. Caution: require active dose reductions.", Color(0xFFFBC02D))
        estimatedCrCl >= 15.0 -> Triple("Stage 4 CKD", "Severely decreased clearance. Heavy contraindications for renal cleared drugs.", Color(0xFFF57C00))
        estimatedCrCl > 0.0 -> Triple("Stage 5 CKD", "Kidney Failure. Critical renal impairment. Dialysis indications.", Color(0xFFD32F2F))
        else -> Triple("Awaiting Input", "Enter valid biological parameters above.", Color.Gray)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Science,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Renal Clearance (Cockcroft-Gault CrCl)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Estimate glomerular clearances and drug filtration capacities instantly based on blood metrics.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Kidney Stage Diagnostic Card
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "🔬 ESTIMATED GLOMERULAR RENAL STATUS",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    letterSpacing = 1.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (estimatedCrCl.isNaN() || estimatedCrCl.isInfinite() || estimatedCrCl <= 0) "0.0" else String.format("%.1f", estimatedCrCl),
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Black,
                            color = stageColor
                        )
                        Text(
                            text = "mL / minute (CrCl)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = stageColor
                        )
                    }
                }

                // Stage Badge
                Box(
                    modifier = Modifier
                        .background(stageColor.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                        .border(1.dp, stageColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = stageText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = stageColor
                    )
                }

                Text(
                    text = stageDesc,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        // Inputs Configuration
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "✏️ Enter Diagnostic Labs Parameters",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                // Sex selector
                Column {
                    Text("Biological Sex (at birth):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ElevatedFilterChip(
                            selected = isMale,
                            onClick = { isMale = true },
                            label = { Text("Male", fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                            modifier = Modifier.weight(1f)
                        )
                        ElevatedFilterChip(
                            selected = !isMale,
                            onClick = { isMale = false },
                            label = { Text("Female", fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Creatinine level Input
                OutlinedTextField(
                    value = creatinineText,
                    onValueChange = { creatinineText = it.filter { char -> char.isDigit() || char == '.' } },
                    label = { Text("Serum Creatinine (Scr)") },
                    leadingIcon = { Icon(Icons.Default.Healing, null) },
                    suffix = { Text("mg/dL") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Age & Weight Inputs Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = ageText,
                        onValueChange = { ageText = it.filter { char -> char.isDigit() } },
                        label = { Text("Age") },
                        suffix = { Text("years") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = weightText,
                        onValueChange = { weightText = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text("Actual Weight") },
                        suffix = { Text("kg") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }
        }
    }
}
