package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.Screen
import com.example.viewmodel.StudentKitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: StudentKitViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val savedName by viewModel.userName.collectAsState()
    val savedOccupation by viewModel.userOccupation.collectAsState()
    val savedEmail by viewModel.userEmail.collectAsState()
    val savedPhone by viewModel.userPhone.collectAsState()
    val savedCity by viewModel.userCity.collectAsState()
    val isDarkThemeSetting by viewModel.isDarkTheme.collectAsState()

    var nameInput by remember(savedName) { mutableStateOf(savedName) }
    var occupationInput by remember(savedOccupation) { mutableStateOf(savedOccupation) }
    var emailInput by remember(savedEmail) { mutableStateOf(savedEmail) }
    var phoneInput by remember(savedPhone) { mutableStateOf(savedPhone) }
    var cityInput by remember(savedCity) { mutableStateOf(savedCity) }

    val occupationPresets = listOf(
        "Business Person",
        "Government Employee",
        "Working Professional",
        "Entrepreneur",
        "Student",
        "Healthcare Specialist",
        "Engineer / IT Professional",
        "Teacher / Educator",
        "Freelancer",
        "Other"
    )

    val isRegistered = savedName.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Settings & Profile",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("settings_back_button")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Live Preview Welcome Banner Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                ),
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary,
                                                MaterialTheme.colorScheme.secondary
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (nameInput.isNotBlank()) "Good Morning, ${nameInput.trim()}!" else "Welcome",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                if (occupationInput.isNotBlank()) {
                                    Text(
                                        text = occupationInput,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isRegistered) Color(0xFF2E7D32) else MaterialTheme.colorScheme.tertiary
                        ) {
                            Text(
                                text = if (isRegistered) "✓ Registered" else "New Profile",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Section 1: User Profile Registration
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "User Info Registration",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = "Enter your details below. Your name will be displayed personalized across the dashboard, financial statements, and document generators.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Field 1: Full Name
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Full Name *") },
                        placeholder = { Text("e.g. Ali Khan, Sarah Ahmad, Mr. Imran") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("user_name_input"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Field 2: Occupation / Profession
                    OutlinedTextField(
                        value = occupationInput,
                        onValueChange = { occupationInput = it },
                        label = { Text("Profession / Category *") },
                        placeholder = { Text("e.g. Business Person, Government Employee") },
                        leadingIcon = { Icon(Icons.Default.Work, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("user_occupation_input"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Quick Profession Preset Chips
                    Text(
                        text = "Quick Profession Select:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        occupationPresets.forEach { preset ->
                            val isSelected = occupationInput.equals(preset, ignoreCase = true)
                            FilterChip(
                                selected = isSelected,
                                onClick = { occupationInput = preset },
                                label = { Text(preset, fontSize = 11.sp) },
                                leadingIcon = if (isSelected) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                } else null
                            )
                        }
                    }

                    // Field 3: Email
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Email Address (Optional)") },
                        placeholder = { Text("user@example.com") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Field 4: Phone & City in Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = phoneInput,
                            onValueChange = { phoneInput = it },
                            label = { Text("Phone Number") },
                            placeholder = { Text("03XX-XXXXXXX") },
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = cityInput,
                            onValueChange = { cityInput = it },
                            label = { Text("City") },
                            placeholder = { Text("Peshawar, Islamabad...") },
                            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Save Profile Button
                    Button(
                        onClick = {
                            if (nameInput.isBlank()) {
                                Toast.makeText(context, "Please enter your name.", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            viewModel.saveUserProfile(
                                name = nameInput,
                                occupation = occupationInput,
                                email = emailInput,
                                phone = phoneInput,
                                city = cityInput
                            )
                            Toast.makeText(context, "User Profile Registered Successfully!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("save_profile_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isRegistered) "Update Registered Info" else "Register User Info",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Section 2: Appearance & Theme
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Appearance & Theme",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = isDarkThemeSetting == false,
                            onClick = { viewModel.setDarkTheme(false) },
                            label = { Text("Light Mode") },
                            leadingIcon = { Icon(Icons.Default.LightMode, contentDescription = null) },
                            modifier = Modifier.weight(1f)
                        )

                        FilterChip(
                            selected = isDarkThemeSetting == true,
                            onClick = { viewModel.setDarkTheme(true) },
                            label = { Text("Dark Mode") },
                            leadingIcon = { Icon(Icons.Default.DarkMode, contentDescription = null) },
                            modifier = Modifier.weight(1f)
                        )

                        FilterChip(
                            selected = isDarkThemeSetting == null,
                            onClick = { viewModel.setDarkTheme(null) },
                            label = { Text("System Default") },
                            leadingIcon = { Icon(Icons.Default.BrightnessAuto, contentDescription = null) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Section 3: Quick Navigation & About
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Quick Tools & App Info",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    OutlinedButton(
                        onClick = { viewModel.navigateTo(Screen.FinanceReportAndBackup) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Backup, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Data Export, Import & Statement Generator")
                    }

                    OutlinedButton(
                        onClick = { viewModel.navigateTo(Screen.SecurityHub) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Security Hub & Vault Settings")
                    }

                    OutlinedButton(
                        onClick = { viewModel.navigateTo(Screen.About) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("About Developer & Module Directory")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
