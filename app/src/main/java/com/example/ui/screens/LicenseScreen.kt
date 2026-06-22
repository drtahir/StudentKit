package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LicenseService
import kotlinx.coroutines.launch

@Composable
fun LicenseScreen(onLicenseValid: () -> Unit) {
    val context = LocalContext.current
    val licenseService = remember { LicenseService(context) }
    val scope = rememberCoroutineScope()

    var phone by remember { mutableStateOf("") }
    var key by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    var checkingExisting by remember { mutableStateOf(true) }

    // Auto-check on launch — skip screen if already licensed
    LaunchedEffect(Unit) {
        val status = licenseService.checkLicense()
        if (status == com.example.data.LicenseStatus.VALID || status == com.example.data.LicenseStatus.GRACE) {
            onLicenseValid()
        } else {
            checkingExisting = false
        }
    }

    if (checkingExisting) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1A28)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color(0xFF1A237E), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Key, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                }
                Spacer(Modifier.height(16.dp))
                Text("StudentKit License", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("Enter your registered phone & license key", fontSize = 13.sp, color = Color.Gray)
                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Registered Phone") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = key,
                    onValueChange = { key = it },
                    label = { Text("License Key") },
                    leadingIcon = { Icon(Icons.Default.Key, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                errorMsg?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(it, color = Color.Red, fontSize = 12.sp)
                }

                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        if (phone.isBlank() || key.isBlank()) {
                            errorMsg = "Please fill in both fields"
                            return@Button
                        }
                        isLoading = true
                        errorMsg = null
                        scope.launch {
                            val success = licenseService.activateLicense(phone.trim(), key.trim())
                            isLoading = false
                            if (success) {
                                onLicenseValid()
                            } else {
                                errorMsg = "Invalid phone or license key"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("Activate License", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
