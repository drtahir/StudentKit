package com.studentkit.buner.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studentkit.buner.data.LicenseService
import com.studentkit.buner.data.LicenseStatus
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

    // WhatsApp pulse animation
    val infiniteTransition = rememberInfiniteTransition(label = "wa")
    val waPulse by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "waPulse"
    )
    val waRingAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f, targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseOut),
            repeatMode = RepeatMode.Restart
        ), label = "waRing"
    )
    val waRingScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseOut),
            repeatMode = RepeatMode.Restart
        ), label = "waRingScale"
    )

    LaunchedEffect(Unit) {
        val status = licenseService.checkLicense()
        if (status == LicenseStatus.VALID || status == LicenseStatus.GRACE) {
            onLicenseValid()
        } else {
            checkingExisting = false
        }
    }

    if (checkingExisting) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF0B1221)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF25D366))
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0B1221), Color(0xFF0D1F12))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(24.dp))

            // App name header
            Text(
                "StudentKit",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 2.sp
            )
            Text(
                "by Dr. M. Tahir",
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF25D366),
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(32.dp))

            // License card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2235)),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Key icon
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF1A237E), Color(0xFF00897B))
                                ),
                                RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Key,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "License Activation",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Enter your registered phone & license key",
                        fontSize = 12.sp,
                        color = Color(0xFF8899AA),
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(24.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Registered Phone", color = Color(0xFF8899AA)) },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF25D366))
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF25D366),
                            unfocusedBorderColor = Color(0xFF2A3A4A),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF25D366)
                        )
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = key,
                        onValueChange = { key = it },
                        label = { Text("License Key", color = Color(0xFF8899AA)) },
                        leadingIcon = {
                            Icon(Icons.Default.Key, contentDescription = null, tint = Color(0xFF25D366))
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF25D366),
                            unfocusedBorderColor = Color(0xFF2A3A4A),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF25D366)
                        )
                    )

                    AnimatedVisibility(visible = errorMsg != null) {
                        errorMsg?.let {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                it,
                                color = Color(0xFFFF5252),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
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
                                if (success) onLicenseValid()
                                else errorMsg = "Invalid phone or license key. Please check and try again."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1A237E)
                        ),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Activate License",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Divider with text
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(modifier = Modifier.weight(1f), color = Color(0xFF2A3A4A))
                Text(
                    "  Don't have a license?  ",
                    fontSize = 12.sp,
                    color = Color(0xFF8899AA)
                )
                Divider(modifier = Modifier.weight(1f), color = Color(0xFF2A3A4A))
            }

            Spacer(Modifier.height(24.dp))

            // WhatsApp contact button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF1A2A1A))
                    .clickable {
                        val msg = "السلام علیکم! مجھے StudentKit app کے لیے license key خریدنی ہے۔ براہ کرم مجھے details بتائیں۔\n\nAssalamu Alaikum! I am interested in purchasing a license key for StudentKit app by Dr. M. Tahir. Please share the details."
                        val waNumber = "923465552678"
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://wa.me/$waNumber?text=${Uri.encode(msg)}")
                        }
                        context.startActivity(intent)
                    }
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Animated WhatsApp icon
                    Box(contentAlignment = Alignment.Center) {
                        // Pulse ring
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .scale(waRingScale)
                                .clip(CircleShape)
                                .background(Color(0xFF25D366).copy(alpha = waRingAlpha))
                        )
                        // WhatsApp green circle
                        Box(
                            modifier = Modifier
                                .scale(waPulse)
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF25D366)),
                            contentAlignment = Alignment.Center
                        ) {
                            // WhatsApp logo text
                            Text(
                                "✓",
                                fontSize = 22.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Column {
                        Text(
                            "Contact Tahir Buneri",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Text(
                            "on WhatsApp",
                            fontSize = 12.sp,
                            color = Color(0xFF25D366)
                        )
                        Text(
                            "+92 346 555 2678",
                            fontSize = 11.sp,
                            color = Color(0xFF8899AA),
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Tap above to send a WhatsApp message",
                fontSize = 11.sp,
                color = Color(0xFF445566),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}
