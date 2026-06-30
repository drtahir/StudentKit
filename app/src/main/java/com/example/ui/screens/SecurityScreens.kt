package com.example.ui.screens

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.VolumeMute
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.IntruderDeviceAdminReceiver
import com.example.data.IntruderLog
import com.example.data.KeystoreHelper
import com.example.data.SteganographyHelper
import androidx.core.content.FileProvider
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import android.graphics.Bitmap.CompressFormat
import java.io.FileOutputStream
import com.example.viewmodel.StudentKitViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.sin

// Programmatic Modulated Frequency Siren Player (Zero Assets Needed)
object SirenPlayer {
    private var audioTrack: AudioTrack? = null
    @Volatile
    private var isPlaying = false

    fun start() {
        if (isPlaying) return
        isPlaying = true
        Thread {
            val sampleRate = 44100
            val minBufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            val audioTrackLocal = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(minBufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()

            audioTrack = audioTrackLocal
            audioTrackLocal.play()

            val buffer = ShortArray(1024)
            var angle = 0.0
            var time = 0.0

            while (isPlaying) {
                // Modulate frequency between 700Hz and 1300Hz to make a convincing siren
                val baseFreq = 1000.0
                val modulationMax = 300.0
                val modulationRate = 2.0 // sweeps per second
                val currentFreq = baseFreq + modulationMax * sin(2.0 * Math.PI * modulationRate * time)

                for (i in buffer.indices) {
                    val sampleValue = (sin(angle) * Short.MAX_VALUE * 0.75).toInt().toShort()
                    buffer[i] = sampleValue
                    angle += 2.0 * Math.PI * currentFreq / sampleRate
                    if (angle > 2.0 * Math.PI) {
                        angle -= 2.0 * Math.PI
                    }
                    time += 1.0 / sampleRate
                }
                audioTrackLocal.write(buffer, 0, buffer.size)
            }
            try {
                audioTrackLocal.stop()
                audioTrackLocal.release()
            } catch (e: Exception) {
                // silent
            }
        }.start()
    }

    fun stop() {
        isPlaying = false
        audioTrack = null
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraCaptureHelper(
    triggerCapture: Boolean,
    onImageCaptured: (File) -> Unit,
    onCaptureHandled: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    if (!cameraPermissionState.status.isGranted) {
        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }
        return
    }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build()
        val imageCaptureLocal = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCaptureLocal
            )
            imageCapture = imageCaptureLocal
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    LaunchedEffect(triggerCapture) {
        if (triggerCapture) {
            val imgCapture = imageCapture
            if (imgCapture != null) {
                val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val photoFile = File(storageDir, "intruder_${System.currentTimeMillis()}.jpg")
                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                imgCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            onImageCaptured(photoFile)
                            onCaptureHandled()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            exception.printStackTrace()
                            onCaptureHandled()
                        }
                    }
                )
            } else {
                onCaptureHandled()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun IntruderGuardScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val logs by viewModel.intruderLogs.collectAsStateWithLifecycle()

    var isSirenMuted by remember { mutableStateOf(false) }
    var triggerCameraSnap by remember { mutableStateOf(false) }

    // Simulator lock states
    var showLockScreen by remember { mutableStateOf(false) }
    var userPIN by remember { mutableStateOf("1234") } // Default simulated PIN
    var enteredPIN by remember { mutableStateOf("") }
    var attemptCount by remember { mutableStateOf(0) }
    var selectedLogForDialog by remember { mutableStateOf<IntruderLog?>(null) }

    // Radial scan animation
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Device Policy Manager
    val devicePolicyManager = remember { context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    val adminComponent = remember { ComponentName(context, IntruderDeviceAdminReceiver::class.java) }
    var isAdminActive by remember { mutableStateOf(false) }

    fun refreshAdminStatus() {
        isAdminActive = devicePolicyManager.isAdminActive(adminComponent)
    }

    LaunchedEffect(Unit) {
        refreshAdminStatus()
    }

    val adminLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        refreshAdminStatus()
    }

    // Camera snapshots callback
    CameraCaptureHelper(
        triggerCapture = triggerCameraSnap,
        onImageCaptured = { file ->
            viewModel.addIntruderLog(
                photoPath = file.absolutePath,
                status = "Passcode Wrong Attempt",
                notes = "Intruder photo saved in app storage: ${file.name}"
            )
        },
        onCaptureHandled = {
            triggerCameraSnap = false
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F172A), Color(0xFF020617))
                )
            )
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Modern radar style header
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .border(2.dp, Color(0xFFEF4444).copy(alpha = 0.4f), CircleShape)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(rotationAngle)
                ) {
                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(Color(0xFFEF4444).copy(alpha = 0.1f), Color(0xFFEF4444), Color(0xFFEF4444).copy(alpha = 0.1f))
                        ),
                        startAngle = 0f,
                        sweepAngle = 180f,
                        useCenter = false
                    )
                }

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(Color(0xFF1E293B), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Shield Active",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(45.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "INTRUDER GUARD",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 2.sp
            )
            Text(
                text = "Siren Alarms & Silent Hidden Snapshots",
                fontSize = 12.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Info & Quick Action cards
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Device Administrator",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Enables detection of wrong passwords entered directly on the main Android device lockscreen.",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isAdminActive) "● STATUS: ARMED & ACTIVE" else "● STATUS: DEACTIVATED",
                            fontWeight = FontWeight.Bold,
                            color = if (isAdminActive) Color(0xFF10B981) else Color(0xFFF59E0B),
                            fontSize = 11.sp
                        )
                        Button(
                            onClick = {
                                if (isAdminActive) {
                                    devicePolicyManager.removeActiveAdmin(adminComponent)
                                    refreshAdminStatus()
                                } else {
                                    val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                                        putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
                                        putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Intruder Guard requires administrative rights to securely monitor lockscreen unlock failures.")
                                    }
                                    adminLauncher.launch(intent)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isAdminActive) Color(0xFFEF4444) else Color(0xFF3B82F6)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (isAdminActive) "Revoke Admin" else "Grant Admin",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // In-app Simulated Lock Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Simulated App Shield",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Launches a security lockdown interface. If a wrong PIN is typed, it captures a live front selfie & sounds the police siren immediately.",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                if (!cameraPermissionState.status.isGranted) {
                                    cameraPermissionState.launchPermissionRequest()
                                } else {
                                    showLockScreen = true
                                    enteredPIN = ""
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Lock, contentDescription = "Lock", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Lock Screen", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                isSirenMuted = !isSirenMuted
                                if (isSirenMuted) {
                                    SirenPlayer.stop()
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray),
                            border = BorderStroke(1.dp, Color.LightGray)
                        ) {
                            Icon(
                                imageVector = if (isSirenMuted) Icons.Outlined.VolumeMute else Icons.Outlined.VolumeUp,
                                contentDescription = "Mute",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isSirenMuted) "Siren Muted" else "Siren Sound On", fontSize = 11.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Activities logs section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Activity Log (${logs.size})",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                )

                if (logs.isNotEmpty()) {
                    Text(
                        text = "Clear Logs",
                        color = Color(0xFFEF4444),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable {
                                viewModel.clearAllIntruderLogs()
                                Toast.makeText(context, "All security logs cleared", Toast.LENGTH_SHORT).show()
                            }
                            .padding(4.dp)
                    )
                }
            }

            if (logs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .background(Color(0xFF0F172A).copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Security,
                            contentDescription = "Empty Security",
                            tint = Color.LightGray.copy(alpha = 0.3f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No intrusion attempts logged.",
                            color = Color.LightGray.copy(alpha = 0.5f),
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Use the simulator to capture a snapshot.",
                            color = Color.LightGray.copy(alpha = 0.3f),
                            fontSize = 10.sp
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0B1224)),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(logs) { log ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                                .clickable {
                                    selectedLogForDialog = log
                                }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Captured photo preview or avatar placeholder
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF0F172A)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (log.photoPath != null && File(log.photoPath).exists()) {
                                    val bitmap = remember(log.photoPath) {
                                        BitmapFactory.decodeFile(log.photoPath)
                                    }
                                    if (bitmap != null) {
                                        androidx.compose.foundation.Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = "Intruder Preview",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    } else {
                                        Icon(Icons.Default.Person, contentDescription = "Photo", tint = Color.LightGray)
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Portrait,
                                        contentDescription = "No snapshot",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = log.attemptStatus,
                                    color = Color(0xFFEF4444),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = SimpleDateFormat("MMM dd, yyyy - hh:mm:ss a", Locale.getDefault()).format(Date(log.timestamp)),
                                    color = Color.LightGray,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = log.notes ?: "Unlock security failure detected",
                                    color = Color.Gray,
                                    fontSize = 9.sp,
                                    maxLines = 1
                                )
                            }

                            IconButton(
                                onClick = {
                                    viewModel.removeIntruderLog(log.id)
                                    Toast.makeText(context, "Log entry deleted", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete Log",
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Lock Screen Simulator Overlay Dialog
        if (showLockScreen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF030712).copy(alpha = 0.98f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Secure Lock",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier
                            .size(60.dp)
                            .padding(bottom = 12.dp)
                    )

                    Text(
                        text = "SECURE DEVICE ENVELOPE",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Enter security PIN to unlock",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // PIN Dots
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(bottom = 32.dp)
                    ) {
                        for (i in 0 until 4) {
                            val active = enteredPIN.length > i
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .border(1.5.dp, Color.White, CircleShape)
                                    .background(
                                        if (active) Color(0xFFEF4444) else Color.Transparent,
                                        CircleShape
                                    )
                            )
                        }
                    }

                    // Keypad Grid
                    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "Clear", "0", "Back")
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.widthIn(max = 280.dp)
                    ) {
                        for (row in 0 until 4) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (col in 0 until 3) {
                                    val index = row * 3 + col
                                    val key = keys[index]

                                    Box(
                                        modifier = Modifier
                                            .size(64.dp)
                                            .background(
                                                if (key == "Clear" || key == "Back") Color.Transparent else Color(0xFF1F2937),
                                                CircleShape
                                            )
                                            .clickable {
                                                when (key) {
                                                    "Clear" -> enteredPIN = ""
                                                    "Back" -> {
                                                        showLockScreen = false
                                                        SirenPlayer.stop()
                                                    }
                                                    else -> {
                                                        if (enteredPIN.length < 4) {
                                                            enteredPIN += key
                                                            if (enteredPIN.length == 4) {
                                                                if (enteredPIN == userPIN) {
                                                                    showLockScreen = false
                                                                    SirenPlayer.stop()
                                                                    enteredPIN = ""
                                                                    attemptCount = 0
                                                                    Toast.makeText(context, "System Unlocked", Toast.LENGTH_SHORT).show()
                                                                } else {
                                                                    attemptCount++
                                                                    enteredPIN = ""
                                                                    // Play Siren!
                                                                    if (!isSirenMuted) {
                                                                        SirenPlayer.start()
                                                                    }
                                                                    // Snap Hidden photo
                                                                    triggerCameraSnap = true
                                                                    Toast.makeText(context, "INCORRECT PIN! Security alarm activated", Toast.LENGTH_LONG).show()
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = key,
                                            color = if (key == "Clear" || key == "Back") Color(0xFF9CA3AF) else Color.White,
                                            fontSize = if (key == "Clear" || key == "Back") 12.sp else 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Simulator PIN is set to default: 1234",
                        color = Color.LightGray.copy(alpha = 0.5f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Full Image dialog when tapping on Log entry
        selectedLogForDialog?.let { log ->
            AlertDialog(
                onDismissRequest = { selectedLogForDialog = null },
                confirmButton = {
                    TextButton(onClick = { selectedLogForDialog = null }) {
                        Text("Close", color = Color(0xFFEF4444))
                    }
                },
                title = {
                    Text(
                        text = "Intruder Security File",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (log.photoPath != null && File(log.photoPath).exists()) {
                            val bitmap = remember(log.photoPath) { BitmapFactory.decodeFile(log.photoPath) }
                            if (bitmap != null) {
                                androidx.compose.foundation.Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Full Intruder Snapshot",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(260.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                  )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .background(Color(0xFF0F172A), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.CameraAlt, contentDescription = "Camera Required", tint = Color.LightGray)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("System lockdown - background capture", fontSize = 10.sp, color = Color.LightGray)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Timestamp: " + SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault()).format(Date(log.timestamp)),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = log.notes ?: "No additional telemetry logged.",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
    }
}

// -------------------------------------------------------------
// SECURE FILE UTILITIES
// -------------------------------------------------------------
fun shareSecureFile(context: Context, file: File, mimeType: String = "*/*") {
    try {
        val uri = FileProvider.getUriForFile(context, "com.example.fileprovider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share / Export Secure File"))
    } catch (e: Exception) {
        Toast.makeText(context, "Sharing failed: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

// -------------------------------------------------------------
// FILE ENCRYPTOR & DECRYPTOR (Android Keystore + AES-256)
// -------------------------------------------------------------
@Composable
fun FileEncryptorScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf("") }
    var selectedFileSize by remember { mutableStateOf(0L) }
    var isProcessing by remember { mutableStateOf(false) }
    var actionMessage by remember { mutableStateOf("No file selected yet.") }
    var encryptedFileResult by remember { mutableStateOf<File?>(null) }
    var decryptedFileResult by remember { mutableStateOf<File?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedUri = uri
            encryptedFileResult = null
            decryptedFileResult = null
            actionMessage = "File selected. Ready to encrypt or decrypt."
            // Resolve file details
            try {
                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    val sizeIndex = cursor.getColumnIndex(android.provider.OpenableColumns.SIZE)
                    if (cursor.moveToFirst()) {
                        selectedFileName = cursor.getString(nameIndex)
                        selectedFileSize = cursor.getLong(sizeIndex)
                    }
                }
            } catch (e: Exception) {
                selectedFileName = "Selected Document"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0F172A), Color(0xFF020617))))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.EnhancedEncryption, null, tint = Color(0xFF3B82F6), modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Hardware Key File Encryptor", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                        Text("Uses hardware-backed AES-256 GCM symmetric keys inside the Android Keystore.", fontSize = 11.sp, color = Color.LightGray)
                    }
                }
            }
        }

        // Selection info card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            border = BorderStroke(1.dp, Color(0xFF3B82F6).copy(alpha = 0.3f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("File Locker Room", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                
                if (selectedUri != null) {
                    Icon(Icons.Default.InsertDriveFile, null, tint = Color(0xFF10B981), modifier = Modifier.size(48.dp))
                    Text(selectedFileName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White, textAlign = TextAlign.Center)
                    Text("Size: ${selectedFileSize / 1024} KB", fontSize = 11.sp, color = Color.LightGray)
                } else {
                    Icon(Icons.Default.CloudUpload, null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                    Text("Select any file to encrypt or decrypt", fontSize = 12.sp, color = Color.LightGray)
                }

                Button(
                    onClick = { filePickerLauncher.launch("*/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                ) {
                    Icon(Icons.Default.FolderOpen, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (selectedUri != null) "Choose Different File" else "Browse Document")
                }
            }
        }

        // Action Status Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
        ) {
            Text(
                text = actionMessage,
                color = Color.LightGray,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(12.dp),
                textAlign = TextAlign.Center
            )
        }

        // Actions
        if (selectedUri != null) {
            if (isProcessing) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF3B82F6))
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            val uri = selectedUri ?: return@Button
                            isProcessing = true
                            actionMessage = "Encrypting file bytes..."
                            scope.launch(kotlinx.coroutines.Dispatchers.IO) {
                                try {
                                    val tempInFile = File(context.cacheDir, "temp_to_encrypt")
                                    context.contentResolver.openInputStream(uri)?.use { input ->
                                        tempInFile.outputStream().use { output -> input.copyTo(output) }
                                    }
                                    val resultFile = File(context.cacheDir, selectedFileName + ".enc")
                                    KeystoreHelper.encryptFileWithKeystore(tempInFile, resultFile)
                                    encryptedFileResult = resultFile
                                    actionMessage = "File locked successfully!\nSaved to private cache: ${resultFile.name}"
                                } catch (e: Exception) {
                                    actionMessage = "Encryption Failed: ${e.message}"
                                } finally {
                                    isProcessing = false
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Lock, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Encrypt")
                    }

                    Button(
                        onClick = {
                            val uri = selectedUri ?: return@Button
                            isProcessing = true
                            actionMessage = "Decrypting file bytes..."
                            scope.launch(kotlinx.coroutines.Dispatchers.IO) {
                                try {
                                    val tempInFile = File(context.cacheDir, "temp_to_decrypt")
                                    context.contentResolver.openInputStream(uri)?.use { input ->
                                        tempInFile.outputStream().use { output -> input.copyTo(output) }
                                    }
                                    val cleanName = selectedFileName.removeSuffix(".enc")
                                    val resultFile = File(context.cacheDir, "decrypted_$cleanName")
                                    KeystoreHelper.decryptFileWithKeystore(tempInFile, resultFile)
                                    decryptedFileResult = resultFile
                                    actionMessage = "File unlocked successfully!\nSaved to private cache: ${resultFile.name}"
                                } catch (e: Exception) {
                                    actionMessage = "Decryption Failed: ${e.message}\nMake sure this file was encrypted with your hardware key!"
                                } finally {
                                    isProcessing = false
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.LockOpen, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Decrypt")
                    }
                }
            }
        }

        // Export Actions
        encryptedFileResult?.let { file ->
            Button(
                onClick = { shareSecureFile(context, file) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Share, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share / Save Encrypted File")
            }
        }

        decryptedFileResult?.let { file ->
            Button(
                onClick = { shareSecureFile(context, file) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Share, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share / Save Decrypted File")
            }
        }
    }
}

// -------------------------------------------------------------
// PHOTO / FILE VAULT (Hidden Locker) (Android Keystore + EncryptedFile)
// -------------------------------------------------------------
@Composable
fun HiddenLockerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val lockerDir = remember { File(context.filesDir, "hidden_locker").apply { mkdirs() } }
    var lockerFiles by remember { mutableStateOf(emptyList<File>()) }
    var isImporting by remember { mutableStateOf(false) }
    var selectedPhotoBytesForPreview by remember { mutableStateOf<ByteArray?>(null) }
    var selectedPhotoName by remember { mutableStateOf("") }

    fun refreshLocker() {
        lockerFiles = lockerDir.listFiles()?.toList() ?: emptyList()
    }

    LaunchedEffect(Unit) {
        refreshLocker()
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            isImporting = true
            scope.launch(kotlinx.coroutines.Dispatchers.IO) {
                try {
                    // Resolve file name
                    var displayName = "imported_file_${System.currentTimeMillis()}"
                    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                        val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                        if (cursor.moveToFirst()) {
                            displayName = cursor.getString(nameIndex)
                        }
                    }

                    // Read plain bytes
                    val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                    if (bytes != null) {
                        val secureFile = File(lockerDir, displayName)
                        KeystoreHelper.writeSecureFile(context, secureFile, bytes)
                        scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                            Toast.makeText(context, "Securely Vaulted: $displayName", Toast.LENGTH_SHORT).show()
                            refreshLocker()
                        }
                    }
                } catch (e: Exception) {
                    scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                        Toast.makeText(context, "Failed to vault: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                } finally {
                    isImporting = false
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0F172A), Color(0xFF020617))))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.FolderSpecial, null, tint = Color(0xFFE91E63), modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Secret Locker Vault", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                        Text("Uses Jetpack Security EncryptedFile to sandbox files cryptographically.", fontSize = 11.sp, color = Color.LightGray)
                    }
                }
            }
        }

        // Actions
        Button(
            onClick = { filePickerLauncher.launch("*/*") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            enabled = !isImporting
        ) {
            if (isImporting) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Securing File...")
            } else {
                Icon(Icons.Default.Add, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Vault Any Photo / File")
            }
        }

        Text("Securely Vaulted Files (${lockerFiles.size})", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)

        if (lockerFiles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.LightGray.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A).copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Lock, null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                    Text("Locker is empty", color = Color.LightGray, fontSize = 13.sp)
                    Text("Vaulted files cannot be accessed outside this app.", color = Color.Gray, fontSize = 11.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(lockerFiles) { file ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1E293B), RoundedCornerShape(12.dp))
                            .clickable {
                                // Decrypt in-memory for preview
                                scope.launch(kotlinx.coroutines.Dispatchers.IO) {
                                    try {
                                        val decryptedBytes = KeystoreHelper.readSecureFile(context, file)
                                        selectedPhotoBytesForPreview = decryptedBytes
                                        selectedPhotoName = file.name
                                    } catch (e: Exception) {
                                        scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                                            Toast.makeText(context, "Decryption Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(
                                imageVector = if (file.name.lowercase().endsWith(".jpg") || file.name.lowercase().endsWith(".png") || file.name.lowercase().endsWith(".jpeg")) {
                                    Icons.Default.Image
                                } else {
                                    Icons.Default.InsertDriveFile
                                },
                                contentDescription = null,
                                tint = Color(0xFFE91E63)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(file.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White, maxLines = 1)
                                Text("${file.length() / 1024} KB", fontSize = 11.sp, color = Color.LightGray)
                            }
                        }

                        Row {
                            IconButton(
                                onClick = {
                                    // Export / Share decrypted
                                    scope.launch(kotlinx.coroutines.Dispatchers.IO) {
                                        try {
                                            val bytes = KeystoreHelper.readSecureFile(context, file)
                                            val tempOut = File(context.cacheDir, file.name)
                                            tempOut.writeBytes(bytes)
                                            scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                                                shareSecureFile(context, tempOut)
                                            }
                                        } catch (e: Exception) {
                                            scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                                                Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Share, "Export", tint = Color.LightGray, modifier = Modifier.size(18.dp))
                            }

                            IconButton(
                                onClick = {
                                    file.delete()
                                    refreshLocker()
                                    Toast.makeText(context, "Deleted securely", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(Icons.Default.DeleteOutline, "Delete", tint = Color.Red, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    // In-Memory Decrypted Preview Dialog
    selectedPhotoBytesForPreview?.let { bytes ->
        val bitmap = remember(bytes) {
            try {
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            } catch (e: Exception) {
                null
            }
        }

        AlertDialog(
            onDismissRequest = { selectedPhotoBytesForPreview = null },
            confirmButton = {
                TextButton(onClick = { selectedPhotoBytesForPreview = null }) {
                    Text("Close")
                }
            },
            title = {
                Text(selectedPhotoName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    if (bitmap != null) {
                        androidx.compose.foundation.Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Decrypted secure preview",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Decrypted temporarily in memory (Secure)", color = Color.Gray, fontSize = 10.sp)
                    } else {
                        Icon(Icons.Default.InsertDriveFile, null, modifier = Modifier.size(72.dp), tint = Color.LightGray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("This file cannot be previewed directly inside the app. Tap the Share button to export.", color = Color.LightGray, fontSize = 12.sp, textAlign = TextAlign.Center)
                    }
                }
            }
        )
    }
}

// -------------------------------------------------------------
// STEGANOGRAPHY SCREEN (Least Significant Bit Manipulation)
// -------------------------------------------------------------
@Composable
fun SteganographyScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var activeTab by remember { mutableStateOf(0) } // 0 = Encode, 1 = Decode

    var carrierUri by remember { mutableStateOf<Uri?>(null) }
    var secretText by remember { mutableStateOf("") }
    var stegoBitmapResult by remember { mutableStateOf<Bitmap?>(null) }
    var isProcessing by remember { mutableStateOf(false) }

    var stegoUriForDecoding by remember { mutableStateOf<Uri?>(null) }
    var extractedText by remember { mutableStateOf("") }

    val carrierPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            carrierUri = uri
            stegoBitmapResult = null
        }
    }

    val stegoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            stegoUriForDecoding = uri
            extractedText = ""
        }
    }

    fun shareStegoBitmap(bitmap: Bitmap) {
        scope.launch(kotlinx.coroutines.Dispatchers.IO) {
            try {
                // Steganography requires LOSSLESS compression (PNG) because JPEG ruins LSB bits
                val file = File(context.cacheDir, "stego_image_${System.currentTimeMillis()}.png")
                FileOutputStream(file).use { out ->
                    bitmap.compress(CompressFormat.PNG, 100, out)
                }
                scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                    shareSecureFile(context, file, "image/png")
                }
            } catch (e: Exception) {
                scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                    Toast.makeText(context, "Failed to share: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0F172A), Color(0xFF020617))))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TabRow(
            selectedTabIndex = activeTab,
            containerColor = Color(0xFF1E293B),
            contentColor = Color.White
        ) {
            Tab(selected = activeTab == 0, onClick = { activeTab = 0 }, text = { Text("Encode (Hide)") })
            Tab(selected = activeTab == 1, onClick = { activeTab = 1 }, text = { Text("Decode (Extract)") })
        }

        if (activeTab == 0) {
            // ENCODE PANEL
            Text("Hide Text in Image (LSB)", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
            
            // Carrier selection card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (carrierUri != null) {
                        val bitmap = remember(carrierUri) {
                            try {
                                context.contentResolver.openInputStream(carrierUri!!).use {
                                    BitmapFactory.decodeStream(it)
                                }
                            } catch (e: Exception) {
                                null
                            }
                        }
                        if (bitmap != null) {
                            androidx.compose.foundation.Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Carrier Preview",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Fit
                            )
                            Text("Carrier Size: ${bitmap.width} x ${bitmap.height}", color = Color.LightGray, fontSize = 11.sp)
                        }
                    } else {
                        Icon(Icons.Default.AddPhotoAlternate, null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                        Text("Select a lossless cover image (PNG recommended)", color = Color.LightGray, fontSize = 12.sp)
                    }

                    Button(
                        onClick = { carrierPicker.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                    ) {
                        Text("Select Cover Photo")
                    }
                }
            }

            OutlinedTextField(
                value = secretText,
                onValueChange = { secretText = it },
                label = { Text("Secret message to inject") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFF3B82F6),
                    unfocusedLabelColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            if (carrierUri != null && secretText.isNotEmpty()) {
                if (isProcessing) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF3B82F6))
                    }
                } else {
                    Button(
                        onClick = {
                            isProcessing = true
                            scope.launch(kotlinx.coroutines.Dispatchers.IO) {
                                try {
                                    val inputStream = context.contentResolver.openInputStream(carrierUri!!)
                                    val bitmap = BitmapFactory.decodeStream(inputStream)
                                    if (bitmap != null) {
                                        val encoded = SteganographyHelper.encode(bitmap, secretText)
                                        stegoBitmapResult = encoded
                                        scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                                            Toast.makeText(context, "Successfully hidden!", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } catch (e: Exception) {
                                    scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                                        Toast.makeText(context, "Encoding failed: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                } finally {
                                    isProcessing = false
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Flip, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Encode & Embed Text")
                    }
                }
            }

            stegoBitmapResult?.let { bitmap ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Stego-Image Result Preview", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                        androidx.compose.foundation.Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Stego Result",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Button(
                            onClick = { shareStegoBitmap(bitmap) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                        ) {
                            Icon(Icons.Default.Share, null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Share / Save Lossless PNG")
                        }
                    }
                }
            }
        } else {
            // DECODE PANEL
            Text("Extract Hidden Text (LSB)", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (stegoUriForDecoding != null) {
                        val bitmap = remember(stegoUriForDecoding) {
                            try {
                                context.contentResolver.openInputStream(stegoUriForDecoding!!).use {
                                    BitmapFactory.decodeStream(it)
                                }
                            } catch (e: Exception) {
                                null
                            }
                        }
                        if (bitmap != null) {
                            androidx.compose.foundation.Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Stego Preview",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Fit
                            )
                        }
                    } else {
                        Icon(Icons.Default.Photo, null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                        Text("Select steganographic image with embedded text", color = Color.LightGray, fontSize = 12.sp)
                    }

                    Button(
                        onClick = { stegoPicker.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                    ) {
                        Text("Browse Stego Image")
                    }
                }
            }

            if (stegoUriForDecoding != null) {
                if (isProcessing) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFE91E63))
                    }
                } else {
                    Button(
                        onClick = {
                            isProcessing = true
                            scope.launch(kotlinx.coroutines.Dispatchers.IO) {
                                try {
                                    val inputStream = context.contentResolver.openInputStream(stegoUriForDecoding!!)
                                    val bitmap = BitmapFactory.decodeStream(inputStream)
                                    if (bitmap != null) {
                                        val decoded = SteganographyHelper.decode(bitmap)
                                        scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                                            extractedText = decoded.ifEmpty { "[No hidden message found, or format is unsupported]" }
                                        }
                                    }
                                } catch (e: Exception) {
                                    scope.launch(kotlinx.coroutines.Dispatchers.Main) {
                                        Toast.makeText(context, "Extraction failed: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                } finally {
                                    isProcessing = false
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Search, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Extract Secret Message")
                    }
                }
            }

            if (extractedText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Extracted Message:", fontWeight = FontWeight.Bold, color = Color(0xFF10B981), fontSize = 13.sp)
                        Text(
                            text = extractedText,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                val clip = android.content.ClipData.newPlainText("Decoded Stego", extractedText)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Message copied!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Copy", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

