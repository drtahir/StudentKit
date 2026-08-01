package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BiometricAuthHelper
import com.example.data.BluetoothThermalPrinterHelper
import com.example.data.PosOrderItem
import com.example.viewmodel.StudentKitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThermalPrinterManagerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var printers by remember { mutableStateOf(BluetoothThermalPrinterHelper.getAvailablePrinters(context)) }
    var selectedPrinterAddress by remember { mutableStateOf(printers.firstOrNull()?.address ?: "00:11:22:33:44:55") }
    var selectedPrinterName by remember { mutableStateOf(printers.firstOrNull()?.name ?: "Demo Thermal Printer 58mm") }
    
    var customReceiptText by remember { mutableStateOf("OmniPOS Thermal Print Test\nDate: 2026-08-01\nStatus: Bluetooth Ready\n------------------------\nThank you for using OmniPOS!") }
    var printStatusLog by remember { mutableStateOf("Ready to scan and print over Bluetooth.") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Top Header
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Print, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Bluetooth Thermal Printer Manager", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("ESC/POS 58mm & 80mm wireless thermal printer integration for sales receipts & receipts.", fontSize = 12.sp, color = Color.DarkGray)
                }
            }
        }

        // Section 1: Bluetooth Printer Device Picker
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("1. Select Bluetooth Thermal Printer", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    IconButton(onClick = {
                        printers = BluetoothThermalPrinterHelper.getAvailablePrinters(context)
                        Toast.makeText(context, "Refreshed Bluetooth paired devices", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }

                if (printers.isEmpty()) {
                    Text("No Bluetooth devices found. Please pair your Bluetooth Thermal Printer in Android Settings first.", color = Color.Red, fontSize = 11.sp)
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        printers.forEach { p ->
                            val isSelected = p.address == selectedPrinterAddress
                            Card(
                                onClick = {
                                    selectedPrinterAddress = p.address
                                    selectedPrinterName = p.name
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Bluetooth, contentDescription = null, tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                                        Spacer(Modifier.width(8.dp))
                                        Column {
                                            Text(p.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text("MAC: ${p.address}", fontSize = 10.sp, color = Color.Gray)
                                        }
                                    }
                                    if (isSelected) {
                                        Text("ACTIVE", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Quick Test Print & Drawer Actions
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("2. ESC/POS Diagnostic & Quick Actions", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            val sampleItems = listOf(
                                PosOrderItem("1", "1", "p1", "Panadol Extra", 2, 150.0),
                                PosOrderItem("2", "1", "p2", "Chicken Club Sandwich", 1, 450.0)
                            )
                            val payload = BluetoothThermalPrinterHelper.buildPosReceiptPayload(
                                businessName = "Apex Pharmacy & Mart",
                                tagline = "Quality & Care Everyday",
                                address = "Main Commercial Ave, Block 4",
                                phone = "+92 300 1234567",
                                orderId = "INV-9942",
                                dateStr = "2026-08-01 10:30",
                                items = sampleItems,
                                subtotal = 750.0,
                                discount = 50.0,
                                tax = 35.0,
                                total = 735.0,
                                paymentMethod = "CASH",
                                footerNote = "Thank you! Please visit again."
                            )
                            val (success, msg) = BluetoothThermalPrinterHelper.printPayload(context, selectedPrinterAddress, payload)
                            printStatusLog = msg
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Receipt, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Print Sample Receipt", fontSize = 11.sp)
                    }

                    OutlinedButton(
                        onClick = {
                            val drawerPayload = BluetoothThermalPrinterHelper.ESC_OPEN_DRAWER
                            val (success, msg) = BluetoothThermalPrinterHelper.printPayload(context, selectedPrinterAddress, drawerPayload)
                            printStatusLog = "Cash Drawer Trigger Signal Sent (ESC p 0 25 255)."
                            Toast.makeText(context, "Cash Drawer Kick Trigger Sent!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.MeetingRoom, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Kick Cash Drawer", fontSize = 11.sp)
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            val cutPayload = BluetoothThermalPrinterHelper.ESC_FEED_AND_CUT
                            BluetoothThermalPrinterHelper.printPayload(context, selectedPrinterAddress, cutPayload)
                            printStatusLog = "Paper Feed & Cut Command Executed."
                            Toast.makeText(context, "Paper Feed & Cut Command Sent", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.ContentCut, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Feed & Cut Paper", fontSize = 11.sp)
                    }
                }
            }
        }

        // Section 3: Custom Text ESC/POS Printing
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("3. Custom Text / ESC/POS Direct Console", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                OutlinedTextField(
                    value = customReceiptText,
                    onValueChange = { customReceiptText = it },
                    label = { Text("Receipt Payload Text (32 cols max)") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    textStyle = TextStyle(fontFamily = FontFamily.Monospace)
                )
                Button(
                    onClick = {
                        val buffer = java.io.ByteArrayOutputStream()
                        buffer.write(BluetoothThermalPrinterHelper.ESC_INIT)
                        buffer.write(BluetoothThermalPrinterHelper.ESC_ALIGN_CENTER)
                        buffer.write("$customReceiptText\n\n".toByteArray(Charsets.ISO_8859_1))
                        buffer.write(BluetoothThermalPrinterHelper.ESC_FEED_AND_CUT)
                        val (success, msg) = BluetoothThermalPrinterHelper.printPayload(context, selectedPrinterAddress, buffer.toByteArray())
                        printStatusLog = msg
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Send, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Print Custom Payload to $selectedPrinterName")
                }
            }
        }

        // Status Console Log
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Bluetooth Terminal Console Log:", color = Color.Green, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                Spacer(Modifier.height(4.dp))
                Text(printStatusLog, color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiometricManagerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val activity = context as? FragmentActivity

    val biometricStatus = remember { BiometricAuthHelper.checkBiometricAvailability(context) }
    var authResultLog by remember { mutableStateOf("Hardware Biometrics Ready.") }

    var lockOmniPosRegister by remember { mutableStateOf(true) }
    var lockFinanceVault by remember { mutableStateOf(true) }
    var lockSecurityHub by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Top Banner
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Fingerprint, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(36.dp))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Biometric Authentication Hub", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Fingerprint Sensor & Face Unlock hardware security integration.", fontSize = 12.sp, color = Color.DarkGray)
                }
            }
        }

        // Section 1: Device Hardware Diagnostics
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("1. Device Biometric Hardware Status", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                val statusText = when (biometricStatus) {
                    BiometricAuthHelper.BiometricStatus.AVAILABLE -> "✅ Hardware Biometrics Available & Enrolled (Fingerprint/Face/PIN)"
                    BiometricAuthHelper.BiometricStatus.NONE_ENROLLED -> "⚠️ Hardware Present, but no Fingerprint/Face enrolled in Android Settings"
                    BiometricAuthHelper.BiometricStatus.NO_HARDWARE -> "ℹ️ No Hardware Biometric Sensor detected (Device PIN fallback active)"
                    else -> "ℹ️ Biometrics Available / Device Credentials Supported"
                }

                Text(statusText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = if (biometricStatus == BiometricAuthHelper.BiometricStatus.AVAILABLE) Color(0xFF2E7D32) else Color(0xFFE65100))

                Divider()

                Button(
                    onClick = {
                        if (activity != null) {
                            BiometricAuthHelper.authenticate(
                                activity = activity,
                                title = "Test Biometric Scan",
                                subtitle = "Scan fingerprint or face to verify hardware integration",
                                onSuccess = {
                                    authResultLog = "✅ Biometric Authentication Succeeded! Identity Verified."
                                    Toast.makeText(context, "Biometric Auth Success!", Toast.LENGTH_SHORT).show()
                                },
                                onError = { err ->
                                    authResultLog = "❌ Biometric Auth Failed: $err"
                                    Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
                                }
                            )
                        } else {
                            Toast.makeText(context, "Activity Context required for Biometrics", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Fingerprint, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Trigger Live Biometric Prompt Test")
                }
            }
        }

        // Section 2: Protected Application Modules
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("2. Modules Secured by Biometrics", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("OmniPOS Manager Void & Discount", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Require fingerprint before voiding items or opening cash drawer", fontSize = 10.sp, color = Color.Gray)
                    }
                    Switch(checked = lockOmniPosRegister, onCheckedChange = { lockOmniPosRegister = it })
                }

                Divider()

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Financial Vault & Income Records", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Protect income, expense, zakat, and loan logs", fontSize = 10.sp, color = Color.Gray)
                    }
                    Switch(checked = lockFinanceVault, onCheckedChange = { lockFinanceVault = it })
                }

                Divider()

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Security Hub & Private Vaults", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Protect photo vault, PIN vault, and encrypted notes", fontSize = 10.sp, color = Color.Gray)
                    }
                    Switch(checked = lockSecurityHub, onCheckedChange = { lockSecurityHub = it })
                }
            }
        }

        // Log Console
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Biometric Diagnostic Log:", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Spacer(Modifier.height(4.dp))
                Text(authResultLog, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
