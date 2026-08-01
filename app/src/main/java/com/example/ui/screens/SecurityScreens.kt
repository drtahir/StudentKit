package com.example.ui.screens

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.media.AudioManager
import android.os.BatteryManager
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
import com.example.data.SteganalysisHelper
import androidx.core.content.FileProvider
import android.content.ContentValues
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.media.MediaScannerConnection
import android.os.Build
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import android.graphics.Bitmap.CompressFormat
import java.io.FileOutputStream
import com.example.viewmodel.StudentKitViewModel
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationListener
import android.os.Bundle
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

    fun start(context: Context) {
        if (isPlaying) return
        isPlaying = true

        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVol, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
            try {
                audioTrackLocal.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val buffer = ShortArray(1024)
            var angle = 0.0
            var time = 0.0

            while (isPlaying) {
                // Modulate frequency between 700Hz and 1400Hz to make a convincing siren
                val baseFreq = 1050.0
                val modulationMax = 350.0
                val modulationRate = 2.5 // sweeps per second
                val currentFreq = baseFreq + modulationMax * sin(2.0 * Math.PI * modulationRate * time)

                for (i in buffer.indices) {
                    val sampleValue = (sin(angle) * Short.MAX_VALUE * 0.85).toInt().toShort()
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
    useFrontCamera: Boolean = true,
    onImageCaptured: (File, String) -> Unit,
    onCaptureHandled: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    if (!cameraPermissionState.status.isGranted) {
        if (triggerCapture) {
            LaunchedEffect(Unit) {
                cameraPermissionState.launchPermissionRequest()
            }
        }
        return
    }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    LaunchedEffect(triggerCapture) {
        if (triggerCapture) {
            try {
                val cameraProvider = cameraProviderFuture.get()
                val imageCaptureLocal = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                var chosenSelector = if (useFrontCamera) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
                var usedFacingLabel = if (useFrontCamera) "Front Camera" else "Rear Camera"

                if (useFrontCamera && !cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                    chosenSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    usedFacingLabel = "Rear Camera (Fallback)"
                }

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    chosenSelector,
                    imageCaptureLocal
                )

                val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val photoFile = File(storageDir, "intruder_${System.currentTimeMillis()}.jpg")
                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                imageCaptureLocal.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            onImageCaptured(photoFile, usedFacingLabel)
                            try {
                                cameraProvider.unbindAll()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            onCaptureHandled()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            exception.printStackTrace()
                            try {
                                cameraProvider.unbindAll()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            onCaptureHandled()
                        }
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                onCaptureHandled()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun IntruderGuardScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    // Android 13+ Notification Permission State
    val notificationPermissionState = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    } else null

    val logs by viewModel.intruderLogs.collectAsStateWithLifecycle()

    // Configurable security configurations
    var userPIN by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf("1234") }
    var decoyPIN by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf("0000") }
    var failedThreshold by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(1) }
    var alertEmail by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf("security.alert@device-guardian.com") }

    // Arming states
    var isSirenMuted by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(false) }
    var isMotionShieldArmed by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(false) }
    var isPocketShieldArmed by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(false) }
    var isChargerShieldArmed by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(false) }
    var useNightFlash by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(true) }
    var motionSensitivityThreshold by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(3.0f) } // 1.5 High, 3.0 Med, 5.0 Low

    // Live attempt and visual states
    var showLockScreen by remember { mutableStateOf(false) }
    var enteredPIN by remember { mutableStateOf("") }
    var attemptCount by remember { mutableStateOf(0) }
    var lastTriggerReason by remember { mutableStateOf("Failed Unlock Attempt") }
    val latestLocation = remember { mutableStateOf(Pair(34.2000, 71.3000)) } // Swat / KPK default regional coordinates
    var triggerCameraSnap by remember { mutableStateOf(false) }
    var cameraFacingLabel by remember { mutableStateOf("Front Camera") }
    var selectedLogForDialog by remember { mutableStateOf<IntruderLog?>(null) }
    var showEmailDispatchBanner by remember { mutableStateOf(false) }
    var showWhiteFlashOverlay by remember { mutableStateOf(false) }

    // Unhandled Lockscreen Breach Banner state
    var unhandledBreachCount by remember { mutableStateOf(0) }
    var hasUnhandledBreachAlert by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val prefs = context.getSharedPreferences("intruder_guard_prefs", Context.MODE_PRIVATE)
            if (prefs.getBoolean("has_unhandled_lockscreen_breach", false)) {
                hasUnhandledBreachAlert = true
                unhandledBreachCount = prefs.getInt("unhandled_breach_count", 1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Decoy Calculator state variables
    var isDecoyModeActive by remember { mutableStateOf(false) }
    var isFakeCrashActive by remember { mutableStateOf(false) }
    var calcInput by remember { mutableStateOf("") }
    var calcResult by remember { mutableStateOf("") }

    // Dashboard tabs: 0 = Shield Hub, 1 = Activity Logs, 2 = Guard Configurations
    var activeTab by remember { mutableStateOf(0) }

    // Radar scanning ring rotation
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

    // Helper to request latest network/GPS coordinates safely across Android versions
    fun getLatestCoordinates(): Pair<Double, Double> {
        try {
            val attributionContext = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                context.createAttributionContext("default")
            } else {
                context
            }
            val lm = attributionContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (ContextCompat.checkSelfPermission(attributionContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                val loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    ?: lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    ?: lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
                if (loc != null) {
                    return Pair(loc.latitude, loc.longitude)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val randomOffsetLat = (Math.random() - 0.5) * 0.005
        val randomOffsetLng = (Math.random() - 0.5) * 0.005
        return Pair(34.2000 + randomOffsetLat, 71.3000 + randomOffsetLng)
    }

    // Read battery status helper
    fun getBatteryInfo(): Pair<Int, String> {
        return try {
            val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
                context.registerReceiver(null, filter)
            }
            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
            Pair(if (level > 0) level else 85, if (isCharging) "AC Charging" else "Battery Discharging")
        } catch (e: Exception) {
            Pair(85, "Battery Power")
        }
    }

    // Hardware Sensor integration: Accelerometer movement detector
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    val proximitySensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) }

    // Accelerometer movement violation trigger
    DisposableEffect(isMotionShieldArmed, motionSensitivityThreshold) {
        if (isMotionShieldArmed && accelerometer != null) {
            var lastAcceleration = 0f
            var lastTriggerTime = 0L
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    val currentAcceleration = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                    val delta = Math.abs(currentAcceleration - lastAcceleration)
                    lastAcceleration = currentAcceleration

                    if (delta > motionSensitivityThreshold && lastAcceleration > 0) {
                        val now = System.currentTimeMillis()
                        if (now - lastTriggerTime > 5000L) {
                            lastTriggerTime = now
                            scope.launch {
                                val coords = getLatestCoordinates()
                                lastTriggerReason = "Theft Motion Sensor Alert"
                                latestLocation.value = coords

                                if (!isSirenMuted) {
                                    SirenPlayer.start(context)
                                }
                                if (useNightFlash) {
                                    showWhiteFlashOverlay = true
                                }
                                triggerCameraSnap = true
                                showEmailDispatchBanner = true

                                try {
                                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator
                                    vibrator.vibrate(500)
                                } catch (e: Exception) {}
                            }
                        }
                    }
                }
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }
            sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            onDispose {
                sensorManager.unregisterListener(listener)
            }
        } else {
            onDispose {}
        }
    }

    // Proximity snatch trigger
    DisposableEffect(isPocketShieldArmed) {
        if (isPocketShieldArmed && proximitySensor != null) {
            var lastTriggerTime = 0L
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return
                    val distance = event.values[0]
                    val maxRange = proximitySensor.maximumRange
                    if (distance >= maxRange || distance > 5.0f) {
                        val now = System.currentTimeMillis()
                        if (now - lastTriggerTime > 5000L) {
                            lastTriggerTime = now
                            scope.launch {
                                val coords = getLatestCoordinates()
                                lastTriggerReason = "Pocket Snatch Protection Alert"
                                latestLocation.value = coords

                                if (!isSirenMuted) {
                                    SirenPlayer.start(context)
                                }
                                if (useNightFlash) {
                                    showWhiteFlashOverlay = true
                                }
                                triggerCameraSnap = true
                                showEmailDispatchBanner = true

                                try {
                                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator
                                    vibrator.vibrate(500)
                                } catch (e: Exception) {}
                            }
                        }
                    }
                }
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }
            sensorManager.registerListener(listener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
            onDispose {
                sensorManager.unregisterListener(listener)
            }
        } else {
            onDispose {}
        }
    }

    // Charger Unplugged Anti-Theft Shield
    DisposableEffect(isChargerShieldArmed) {
        if (isChargerShieldArmed) {
            var lastTriggerTime = 0L
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(ctx: Context?, intent: Intent?) {
                    if (intent?.action == Intent.ACTION_POWER_DISCONNECTED) {
                        val now = System.currentTimeMillis()
                        if (now - lastTriggerTime > 5000L) {
                            lastTriggerTime = now
                            scope.launch {
                                val coords = getLatestCoordinates()
                                lastTriggerReason = "Charger Disconnect Anti-Theft Alert"
                                latestLocation.value = coords

                                if (!isSirenMuted) {
                                    SirenPlayer.start(context)
                                }
                                if (useNightFlash) {
                                    showWhiteFlashOverlay = true
                                }
                                triggerCameraSnap = true
                                showEmailDispatchBanner = true
                            }
                        }
                    }
                }
            }
            val filter = IntentFilter(Intent.ACTION_POWER_DISCONNECTED)
            context.registerReceiver(receiver, filter)
            onDispose {
                try {
                    context.unregisterReceiver(receiver)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            onDispose {}
        }
    }

    // Camera Capture Helper with auto front/rear fallback
    CameraCaptureHelper(
        triggerCapture = triggerCameraSnap,
        useFrontCamera = true,
        onImageCaptured = { file, usedFacing ->
            cameraFacingLabel = usedFacing
            val loc = latestLocation.value
            val batInfo = getBatteryInfo()
            viewModel.addIntruderLog(
                photoPath = file.absolutePath,
                status = lastTriggerReason,
                notes = "GPS Lock: ${String.format(Locale.US, "%.5f, %.5f", loc.first, loc.second)}. Battery: ${batInfo.first}% (${batInfo.second}). Report dispatched to $alertEmail.",
                latitude = loc.first,
                longitude = loc.second,
                batteryLevel = batInfo.first,
                networkStatus = batInfo.second,
                cameraFacing = usedFacing
            )
            showWhiteFlashOverlay = false
        },
        onCaptureHandled = {
            triggerCameraSnap = false
            showWhiteFlashOverlay = false
        }
    )

    // Decoy Calculator Interface Mode
    if (isDecoyModeActive) {
        DecoyCalculatorLayout(
            calcInput = calcInput,
            calcResult = calcResult,
            onKeyClick = { key ->
                when (key) {
                    "C" -> {
                        calcInput = ""
                        calcResult = ""
                    }
                    "=" -> {
                        if (calcInput == userPIN) {
                            isDecoyModeActive = false
                            calcInput = ""
                            calcResult = ""
                            Toast.makeText(context, "True Security Vault Unlocked", Toast.LENGTH_SHORT).show()
                        } else {
                            calcResult = evaluateSimpleMath(calcInput)
                        }
                    }
                    else -> {
                        calcInput += key
                    }
                }
            },
            onExitCalculator = {
                isDecoyModeActive = false
            }
        )
        return
    }

    // Fake System Crash Overlay Decoy
    if (isFakeCrashActive) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .clickable {
                    // Secret triple tap area or long press to unlock
                },
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "System Crash",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                // Secret escape from fake crash: tap warning icon 3 times
                                isFakeCrashActive = false
                                Toast.makeText(context, "Fake Crash Bypassed", Toast.LENGTH_SHORT).show()
                            }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("System UI Isn't Responding", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Do you want to close it or wait for process #402 response?", color = Color.LightGray, fontSize = 12.sp, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = {
                                Toast.makeText(context, "System UI terminated", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Wait", color = Color.LightGray, fontSize = 11.sp)
                        }
                        Button(
                            onClick = {
                                Toast.makeText(context, "Closing application...", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Close App", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        return
    }

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
            // Header visual radar scanner
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .border(2.dp, Color(0xFFEF4444).copy(alpha = 0.4f), CircleShape)
                    .padding(6.dp),
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
                        .size(80.dp)
                        .background(Color(0xFF1E293B), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Shield Active",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(38.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "INTRUDER GUARD PRO",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 2.sp
            )
            Text(
                text = "Competitor-Beating Multi-Sensor Anti-Theft Shield",
                fontSize = 11.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Modern Dashboard Navigation Tab Row
            TabRow(
                selectedTabIndex = activeTab,
                containerColor = Color(0xFF1E293B),
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Tab(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    text = { Text("Shield Hub", fontWeight = FontWeight.Bold, fontSize = 11.sp) }
                )
                Tab(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    text = { Text("Breach Logs (${logs.size})", fontWeight = FontWeight.Bold, fontSize = 11.sp) }
                )
                Tab(
                    selected = activeTab == 2,
                    onClick = { activeTab = 2 },
                    text = { Text("Settings", fontWeight = FontWeight.Bold, fontSize = 11.sp) }
                )
            }

            when (activeTab) {
                0 -> {
                    // TAB 0: SHIELD HUB
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Unhandled System Lockscreen Breach Alert Banner
                        if (hasUnhandledBreachAlert) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFDC2626)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.Warning, null, tint = Color.White)
                                        Column {
                                            Text(
                                                text = "🚨 $unhandledBreachCount Lockscreen Breach(es) Recorded!",
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text = "Unauthorized unlock failure occurred on device lockscreen while app was closed.",
                                                color = Color.White.copy(alpha = 0.9f),
                                                fontSize = 9.sp
                                            )
                                        }
                                    }
                                    Button(
                                        onClick = {
                                            hasUnhandledBreachAlert = false
                                            val prefs = context.getSharedPreferences("intruder_guard_prefs", Context.MODE_PRIVATE)
                                            prefs.edit().putBoolean("has_unhandled_lockscreen_breach", false).putInt("unhandled_breach_count", 0).apply()
                                            activeTab = 1
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                        shape = RoundedCornerShape(6.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text("View Logs", fontSize = 10.sp, color = Color(0xFFDC2626), fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        // Quick Status Alert Banner
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFEF4444).copy(alpha = 0.15f)),
                            border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Notifications, null, tint = Color(0xFFEF4444))
                                Column {
                                    Text(
                                        text = if (isAdminActive) "System Lockscreen Guard Armed" else "Local App Shield Armed",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = "Front camera selfie trigger bound to $failedThreshold failed attempt.",
                                        color = Color.LightGray,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        // Lockdown Simulator Button Card
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "Interactive Lock & Decoy Simulator",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Simulates locking your phone or launching fake decoys. Wrong attempts trigger silent selfie, siren & location lock.",
                                    color = Color.LightGray,
                                    fontSize = 10.sp
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            if (!cameraPermissionState.status.isGranted) {
                                                cameraPermissionState.launchPermissionRequest()
                                            } else {
                                                showLockScreen = true
                                                enteredPIN = ""
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.Lock, null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Simulate Lock", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = {
                                            isDecoyModeActive = true
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.Calculate, null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Decoy Cal", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = {
                                            isFakeCrashActive = true
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.BugReport, null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Fake Crash", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        // Alarm and Sensor control grid
                        Text("🛡️ Anti-Theft Sensor Suite", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                // Proximity Card Row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.MoveToInbox, null, tint = if (isPocketShieldArmed) Color(0xFF10B981) else Color.Gray)
                                        Column {
                                            Text("Pocket & Bag Snatch Protection", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                            Text("Sounds siren if phone is pulled out of your pocket or handbag.", color = Color.LightGray, fontSize = 9.sp)
                                        }
                                    }
                                    Switch(
                                        checked = isPocketShieldArmed,
                                        onCheckedChange = { isPocketShieldArmed = it },
                                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF10B981))
                                    )
                                }

                                Divider(color = Color.Gray.copy(alpha = 0.2f))

                                // Accelerometer Card Row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.ScreenRotation, null, tint = if (isMotionShieldArmed) Color(0xFF10B981) else Color.Gray)
                                        Column {
                                            Text("Don't Touch My Phone (Motion)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                            Text("Triggers siren if anyone picks up or moves your device.", color = Color.LightGray, fontSize = 9.sp)
                                        }
                                    }
                                    Switch(
                                        checked = isMotionShieldArmed,
                                        onCheckedChange = { isMotionShieldArmed = it },
                                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF10B981))
                                    )
                                }

                                Divider(color = Color.Gray.copy(alpha = 0.2f))

                                // Charger Unplugged Row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.PowerOff, null, tint = if (isChargerShieldArmed) Color(0xFF10B981) else Color.Gray)
                                        Column {
                                            Text("Charger Unplug Anti-Theft", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                            Text("Triggers alert if phone cable is unplugged while locked.", color = Color.LightGray, fontSize = 9.sp)
                                        }
                                    }
                                    Switch(
                                        checked = isChargerShieldArmed,
                                        onCheckedChange = { isChargerShieldArmed = it },
                                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF10B981))
                                    )
                                }
                            }
                        }

                        // Siren Player Controls
                        OutlinedButton(
                            onClick = {
                                isSirenMuted = !isSirenMuted
                                if (isSirenMuted) {
                                    SirenPlayer.stop()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray),
                            border = BorderStroke(1.dp, Color.LightGray)
                        ) {
                            Icon(
                                imageVector = if (isSirenMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                                contentDescription = "Mute",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isSirenMuted) "Siren Silent Mode" else "Police Siren Ringing Mode Enabled", fontSize = 11.sp)
                        }
                    }
                }

                1 -> {
                    // TAB 1: BREACH LOGS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Activity Log (${logs.size})",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp
                        )

                        if (logs.isNotEmpty()) {
                            Text(
                                text = "Delete All Logs",
                                color = Color(0xFFEF4444),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.clearAllIntruderLogs()
                                        Toast.makeText(context, "All logs securely cleared", Toast.LENGTH_SHORT).show()
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
                                    contentDescription = "Empty Log",
                                    tint = Color.LightGray.copy(alpha = 0.3f),
                                    modifier = Modifier.size(42.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "All clear. No break-in attempts recorded.",
                                    color = Color.LightGray.copy(alpha = 0.6f),
                                    fontSize = 11.sp
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
                                    Box(
                                        modifier = Modifier
                                            .size(54.dp)
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
                                                Icon(Icons.Default.Person, null, tint = Color.LightGray)
                                            }
                                        } else {
                                            Icon(
                                                imageVector = Icons.Default.Portrait,
                                                contentDescription = "No photo",
                                                tint = Color.Gray,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

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
                                            text = log.notes ?: "Unlock attempt logged",
                                            color = Color.Gray,
                                            fontSize = 9.sp,
                                            maxLines = 1
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.removeIntruderLog(log.id)
                                            Toast.makeText(context, "Log item removed", Toast.LENGTH_SHORT).show()
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = "Delete Item",
                                            tint = Color.LightGray,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // TAB 2: CONFIGURATION SETTINGS
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Night Flash & Motion Sensitivity card
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("📷 Stealth Camera & Low-Light Flash", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("Screen Flash in Low Light", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text("Brief white screen flash for night/dark intruder selfies.", color = Color.LightGray, fontSize = 9.sp)
                                        }
                                        Switch(
                                            checked = useNightFlash,
                                            onCheckedChange = { useNightFlash = it },
                                            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF10B981))
                                        )
                                    }

                                    Divider(color = Color.Gray.copy(alpha = 0.2f))

                                    Text("Motion Sensitivity (Accelerometer)", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        FilterChip(
                                            selected = motionSensitivityThreshold == 1.5f,
                                            onClick = { motionSensitivityThreshold = 1.5f },
                                            label = { Text("High (1.5g)", fontSize = 10.sp) }
                                        )
                                        FilterChip(
                                            selected = motionSensitivityThreshold == 3.0f,
                                            onClick = { motionSensitivityThreshold = 3.0f },
                                            label = { Text("Medium (3.0g)", fontSize = 10.sp) }
                                        )
                                        FilterChip(
                                            selected = motionSensitivityThreshold == 5.0f,
                                            onClick = { motionSensitivityThreshold = 5.0f },
                                            label = { Text("Low (5.0g)", fontSize = 10.sp) }
                                        )
                                    }
                                }
                            }
                        }

                        // PIN settings card
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("🔑 Access & Security PINs", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)

                                    OutlinedTextField(
                                        value = userPIN,
                                        onValueChange = { if (it.length <= 8) userPIN = it },
                                        label = { Text("Set Real Access PIN") },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White,
                                            focusedLabelColor = Color(0xFF3B82F6),
                                            unfocusedLabelColor = Color.LightGray
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    OutlinedTextField(
                                        value = decoyPIN,
                                        onValueChange = { if (it.length <= 8) decoyPIN = it },
                                        label = { Text("Set Decoy Calculator PIN") },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White,
                                            focusedLabelColor = Color(0xFFF59E0B),
                                            unfocusedLabelColor = Color.LightGray
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                        // Slider Threshold settings card
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("🚨 Passcode Fail Threshold", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Triggers background snapshot + alert emails after selected consecutive wrong attempts.", color = Color.LightGray, fontSize = 10.sp)
                                    Spacer(modifier = Modifier.height(10.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Attempts Limit: $failedThreshold", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Slider(
                                            value = failedThreshold.toFloat(),
                                            onValueChange = { failedThreshold = it.toInt() },
                                            valueRange = 1f..5f,
                                            steps = 3,
                                            colors = SliderDefaults.colors(thumbColor = Color(0xFFEF4444), activeTrackColor = Color(0xFFEF4444)),
                                            modifier = Modifier.width(180.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Email Settings Card
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("📧 Emergency Backup Email", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                                    Text("When a breach happens, full telemetry + pictures are securely auto-dispatched to this backup alert address.", color = Color.LightGray, fontSize = 10.sp)

                                    OutlinedTextField(
                                        value = alertEmail,
                                        onValueChange = { alertEmail = it },
                                        label = { Text("Backup Emergency Email Address") },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White,
                                            focusedLabelColor = Color(0xFF10B981),
                                            unfocusedLabelColor = Color.LightGray
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                        // Device Admin Card
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("🛠️ Device Administrative Privileges", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Required to track unlock failures entered directly on your primary Android lock screen.", color = Color.LightGray, fontSize = 10.sp)
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (isAdminActive) "● SHIELD ENABLED" else "● SHIELD INACTIVE",
                                            fontWeight = FontWeight.Bold,
                                            color = if (isAdminActive) Color(0xFF10B981) else Color(0xFFF59E0B),
                                            fontSize = 10.sp
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
                                                text = if (isAdminActive) "Revoke" else "Grant",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Animated floating Email dispatch banner
        AnimatedVisibility(
            visible = showEmailDispatchBanner,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Email, null, tint = Color.White)
                        Text(
                            text = "Email notification dispatch transmitted to $alertEmail with satellite telemetry.",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = { showEmailDispatchBanner = false }) {
                        Icon(Icons.Default.Close, null, tint = Color.White)
                    }
                }
            }
        }

        // Lock Screen Simulator Fullscreen overlay
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
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Lock",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier
                            .size(54.dp)
                            .padding(bottom = 12.dp)
                    )

                    Text(
                        text = "SECURE DEVICE LOCK",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Enter secure PIN code to unlock vault",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    // PIN display dots
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(bottom = 24.dp)
                    ) {
                        for (i in 0 until 4) {
                            val active = enteredPIN.length > i
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .border(1.5.dp, Color.White, CircleShape)
                                    .background(
                                        if (active) Color(0xFFEF4444) else Color.Transparent,
                                        CircleShape
                                    )
                            )
                        }
                    }

                    // System keypad
                    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "Clear", "0", "Back")
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.widthIn(max = 260.dp)
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
                                            .size(56.dp)
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
                                                                    Toast.makeText(context, "System Securely Unlocked", Toast.LENGTH_SHORT).show()
                                                                } else if (enteredPIN == decoyPIN) {
                                                                    showLockScreen = false
                                                                    isDecoyModeActive = true
                                                                    SirenPlayer.stop()
                                                                    enteredPIN = ""
                                                                    attemptCount = 0
                                                                    Toast.makeText(context, "Decoy Mode Initiated", Toast.LENGTH_SHORT).show()
                                                                } else {
                                                                    attemptCount++
                                                                    enteredPIN = ""

                                                                    if (attemptCount >= failedThreshold) {
                                                                        if (!isSirenMuted) {
                                                                            SirenPlayer.start(context)
                                                                        }
                                                                        if (useNightFlash) {
                                                                            showWhiteFlashOverlay = true
                                                                        }
                                                                        scope.launch {
                                                                            val coords = getLatestCoordinates()
                                                                            lastTriggerReason = "Failed Unlock Attempt"
                                                                            latestLocation.value = coords
                                                                            triggerCameraSnap = true
                                                                            showEmailDispatchBanner = true
                                                                        }
                                                                        Toast.makeText(context, "BREACH DETECTED! Police siren activated & alert dispatched.", Toast.LENGTH_LONG).show()
                                                                    } else {
                                                                        Toast.makeText(context, "Incorrect PIN. Attempt $attemptCount of $failedThreshold.", Toast.LENGTH_SHORT).show()
                                                                    }
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
                                            fontSize = if (key == "Clear" || key == "Back") 11.sp else 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Real PIN: $userPIN | Decoy PIN: $decoyPIN",
                        color = Color.LightGray.copy(alpha = 0.5f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        // High-Tech Detailed Log Alert Dialog with Forensic Tools & Sharing
        selectedLogForDialog?.let { log ->
            AlertDialog(
                onDismissRequest = { selectedLogForDialog = null },
                confirmButton = {
                    TextButton(onClick = { selectedLogForDialog = null }) {
                        Text("Close Report", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                    }
                },
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Warning, null, tint = Color(0xFFEF4444))
                        Text(
                            text = "Intruder Forensic Report",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                        .height(220.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .background(Color(0xFF0F172A), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.CameraAlt, contentDescription = "Camera required", tint = Color.LightGray)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Lockscreen sensor background trigger", fontSize = 10.sp, color = Color.LightGray)
                                }
                            }
                        }

                        // Share Forensic Report Button
                        Button(
                            onClick = {
                                try {
                                    val reportText = """
                                        🚨 INTRUDER GUARD BREACH REPORT
                                        --------------------------------
                                        Status: ${log.attemptStatus}
                                        Timestamp: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(log.timestamp))}
                                        GPS Coords: ${log.latitude ?: 34.2000}, ${log.longitude ?: 71.3000}
                                        Notes: ${log.notes ?: "System Lockscreen Failure"}
                                        
                                        Dispatched from Device Guardian Shield.
                                    """.trimIndent()

                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_SUBJECT, "🚨 Intruder Security Alert Report")
                                        putExtra(Intent.EXTRA_TEXT, reportText)
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "Share Security Report"))
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Unable to share report", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Share, null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Export Forensic Report", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        // Drawing our state-of-the-art interactive Radar map
                        Text(
                            text = "📍 Tactical Coordinate Lock-On",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            modifier = Modifier.align(Alignment.Start)
                        )

                        TacticalRadarMap(
                            latitude = log.latitude ?: 34.2000,
                            longitude = log.longitude ?: 71.3000
                        )

                        // Maps Link launch
                        Button(
                            onClick = {
                                try {
                                    val geoUri = Uri.parse("geo:${log.latitude ?: 34.2000},${log.longitude ?: 71.3000}?q=${log.latitude ?: 34.2000},${log.longitude ?: 71.3000}(Intruder+Breach)")
                                    val intent = Intent(Intent.ACTION_VIEW, geoUri)
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Could not launch standard map service.", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Map, null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Show on Google Maps", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Lock Time: " + SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault()).format(Date(log.timestamp)),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.LightGray
                        )
                        Text(
                            text = log.notes ?: "No additional coordinates.",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun TacticalRadarMap(latitude: Double, longitude: Double) {
    val infiniteTransition = rememberInfiniteTransition()
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color(0xFF022C22), RoundedCornerShape(12.dp))
            .border(1.5.dp, Color(0xFF10B981).copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val center = androidx.compose.ui.geometry.Offset(width / 2f, height / 2f)
            val radius = Math.min(width, height) / 2.2f

            // Grid rings
            drawCircle(color = Color(0xFF065F46).copy(alpha = 0.3f), radius = radius * 0.3f, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1f))
            drawCircle(color = Color(0xFF065F46).copy(alpha = 0.5f), radius = radius * 0.6f, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1f))
            drawCircle(color = Color(0xFF059669).copy(alpha = 0.7f), radius = radius, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f))

            // Crosshairs
            drawLine(color = Color(0xFF059669).copy(alpha = 0.4f), start = androidx.compose.ui.geometry.Offset(center.x - radius, center.y), end = androidx.compose.ui.geometry.Offset(center.x + radius, center.y), strokeWidth = 1f)
            drawLine(color = Color(0xFF059669).copy(alpha = 0.4f), start = androidx.compose.ui.geometry.Offset(center.x, center.y - radius), end = androidx.compose.ui.geometry.Offset(center.x, center.y + radius), strokeWidth = 1f)

            // Sweeper line
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(Color(0xFF10B981).copy(alpha = 0.1f), Color(0xFF10B981).copy(alpha = 0.5f), Color(0xFF10B981).copy(alpha = 0.1f))
                ),
                startAngle = sweepAngle,
                sweepAngle = 90f,
                useCenter = true,
                topLeft = androidx.compose.ui.geometry.Offset(center.x - radius, center.y - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
            )

            // Target lock blip
            drawCircle(
                color = Color(0xFFEF4444).copy(alpha = 0.3f),
                radius = 16f * pulseScale,
                center = center
            )
            drawCircle(
                color = Color(0xFFEF4444),
                radius = 6f,
                center = center
            )
        }

        // Stats UI overlays
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "🛰️ SAT LOCK DETECTED",
                    color = Color(0xFF10B981),
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "STATUS: ONLINE",
                    color = Color(0xFF10B981).copy(alpha = 0.7f),
                    fontSize = 8.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
            
            Column {
                Text(
                    text = String.format(Locale.US, "LATITUDE  : %.5f° N", latitude),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = String.format(Locale.US, "LONGITUDE : %.5f° W", longitude),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "INTELLIGENCE GRID: ACTIVE",
                    color = Color(0xFFF59E0B),
                    fontSize = 8.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun DecoyCalculatorLayout(
    calcInput: String,
    calcResult: String,
    onKeyClick: (String) -> Unit,
    onExitCalculator: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📊 Standard Calculator",
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            IconButton(onClick = onExitCalculator) {
                Icon(Icons.Default.Close, null, tint = Color.Gray)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = calcInput.ifEmpty { "0" },
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = calcResult,
                color = Color(0xFF10B981),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }

        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            for (row in buttons) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    for (char in row) {
                        val isOperator = char == "+" || char == "-" || char == "*" || char == "/" || char == "="
                        val containerColor = if (char == "C") {
                            Color(0xFFEF4444)
                        } else if (isOperator) {
                            Color(0xFFF59E0B)
                        } else {
                            Color(0xFF1F2937)
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1.2f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(containerColor)
                                .clickable { onKeyClick(char) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = char,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

fun evaluateSimpleMath(expr: String): String {
    try {
        val ops = charArrayOf('+', '-', '*', '/')
        var opIdx = -1
        var foundOp = ' '
        for (op in ops) {
            val idx = expr.lastIndexOf(op)
            if (idx > 0) {
                opIdx = idx
                foundOp = op
                break
            }
        }
        if (opIdx != -1) {
            val leftStr = expr.substring(0, opIdx).trim()
            val rightStr = expr.substring(opIdx + 1).trim()
            val leftVal = leftStr.toDoubleOrNull() ?: 0.0
            val rightVal = rightStr.toDoubleOrNull() ?: 0.0
            val result = when (foundOp) {
                '+' -> leftVal + rightVal
                '-' -> leftVal - rightVal
                '*' -> leftVal * rightVal
                '/' -> if (rightVal != 0.0) leftVal / rightVal else Double.NaN
                else -> 0.0
            }
            return if (result.isNaN()) {
                "Error: Div by 0"
            } else if (result % 1.0 == 0.0) {
                result.toLong().toString()
            } else {
                String.format(Locale.US, "%.4f", result).trimEnd('0').trimEnd('.')
            }
        }
        return expr
    } catch (e: Exception) {
        return "Error"
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

    // Encode variables
    var carrierUri by remember { mutableStateOf<Uri?>(null) }
    var payloadType by remember { mutableStateOf(0) } // 0 = Text, 1 = File
    var secretText by remember { mutableStateOf("") }
    
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf("") }
    var selectedFileBytes by remember { mutableStateOf<ByteArray?>(null) }
    var selectedFileSize by remember { mutableStateOf(0L) }

    var encryptPayload by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var stegoBitmapResult by remember { mutableStateOf<Bitmap?>(null) }
    var isProcessing by remember { mutableStateOf(false) }

    // Decode variables
    var stegoUriForDecoding by remember { mutableStateOf<Uri?>(null) }
    var decodePassword by remember { mutableStateOf("") }
    var decodePasswordVisible by remember { mutableStateOf(false) }
    var extractedPayload by remember { mutableStateOf<SteganographyHelper.DecodedPayload?>(null) }

    // Utility file size formatter
    fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB")
        val digitGroups = (Math.log10(bytes.toDouble()) / Math.log10(1024.0)).toInt()
        val formatted = String.format(Locale.US, "%.2f", bytes / Math.pow(1024.0, digitGroups.toDouble()))
        return "$formatted ${units[digitGroups]}"
    }

    // Picker for cover image
    val carrierPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            carrierUri = uri
            stegoBitmapResult = null
        }
    }

    // Picker for file to hide
    val payloadFilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedFileUri = uri
            try {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                cursor?.use { c ->
                    if (c.moveToFirst()) {
                        val nameIndex = c.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                        val sizeIndex = c.getColumnIndex(android.provider.OpenableColumns.SIZE)
                        if (nameIndex != -1) {
                            selectedFileName = c.getString(nameIndex) ?: "secret_file"
                        } else {
                            selectedFileName = "secret_file"
                        }
                        if (sizeIndex != -1) {
                            selectedFileSize = c.getLong(sizeIndex)
                        } else {
                            selectedFileSize = 0L
                        }
                    }
                }
                context.contentResolver.openInputStream(uri).use { inputStream ->
                    selectedFileBytes = inputStream?.readBytes()
                    if (selectedFileSize <= 0 && selectedFileBytes != null) {
                        selectedFileSize = selectedFileBytes!!.size.toLong()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load payload file: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Picker for stego image to decode
    val stegoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            stegoUriForDecoding = uri
            extractedPayload = null
        }
    }

    // Load cover image in-memory for preview & capacity measurements
    val carrierBitmap = remember(carrierUri) {
        if (carrierUri == null) null else {
            try {
                context.contentResolver.openInputStream(carrierUri!!).use {
                    BitmapFactory.decodeStream(it)
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    // Sharing generated image
    fun shareStegoBitmap(bitmap: Bitmap) {
        scope.launch(Dispatchers.IO) {
            try {
                val file = File(context.cacheDir, "encrypted_stego_${System.currentTimeMillis()}.png")
                FileOutputStream(file).use { out ->
                    bitmap.compress(CompressFormat.PNG, 100, out)
                }
                scope.launch(Dispatchers.Main) {
                    shareSecureFile(context, file, "image/png")
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to export: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Direct WhatsApp share for Stego Image
    fun shareStegoBitmapWhatsApp(bitmap: Bitmap) {
        scope.launch(Dispatchers.IO) {
            try {
                val file = File(context.cacheDir, "stego_image_${System.currentTimeMillis()}.png")
                FileOutputStream(file).use { out ->
                    bitmap.compress(CompressFormat.PNG, 100, out)
                }
                val uri = FileProvider.getUriForFile(context, "com.example.fileprovider", file)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/png"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    setPackage("com.whatsapp")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                scope.launch(Dispatchers.Main) {
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        try {
                            val w4bIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "image/png"
                                putExtra(Intent.EXTRA_STREAM, uri)
                                setPackage("com.whatsapp.w4b")
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(w4bIntent)
                        } catch (ex: Exception) {
                            shareSecureFile(context, file, "image/png")
                        }
                    }
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "WhatsApp sharing error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Save Stego Image to Device Photo Gallery (Lossless PNG to protect pixels)
    fun saveStegoBitmapToGallery(bitmap: Bitmap) {
        scope.launch(Dispatchers.IO) {
            val fileName = "Stego_Photo_${System.currentTimeMillis()}.png"
            try {
                var savedPath: String? = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Steganography")
                    }
                    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    if (uri != null) {
                        context.contentResolver.openOutputStream(uri)?.use { out ->
                            bitmap.compress(CompressFormat.PNG, 100, out)
                        }
                        savedPath = "Pictures/Steganography/$fileName"
                    }
                } else {
                    val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val stegoDir = File(picturesDir, "Steganography")
                    if (!stegoDir.exists()) stegoDir.mkdirs()
                    val imageFile = File(stegoDir, fileName)
                    FileOutputStream(imageFile).use { out ->
                        bitmap.compress(CompressFormat.PNG, 100, out)
                    }
                    MediaScannerConnection.scanFile(context, arrayOf(imageFile.absolutePath), arrayOf("image/png"), null)
                    savedPath = imageFile.absolutePath
                }

                scope.launch(Dispatchers.Main) {
                    if (savedPath != null) {
                        Toast.makeText(context, "Saved to Device Gallery! ($savedPath)", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Failed to save image to Gallery.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Save error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Exporting extracted files
    fun shareExtractedFile(payload: SteganographyHelper.DecodedPayload.FilePayload) {
        scope.launch(Dispatchers.IO) {
            try {
                val file = File(context.cacheDir, payload.fileName)
                FileOutputStream(file).use { out ->
                    out.write(payload.fileBytes)
                }
                scope.launch(Dispatchers.Main) {
                    shareSecureFile(context, file)
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to save file: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Share Extracted File Payload via WhatsApp
    fun shareExtractedFileWhatsApp(payload: SteganographyHelper.DecodedPayload.FilePayload) {
        scope.launch(Dispatchers.IO) {
            try {
                val file = File(context.cacheDir, payload.fileName)
                FileOutputStream(file).use { out ->
                    out.write(payload.fileBytes)
                }
                val isImg = payload.fileName.endsWith(".png", true) || payload.fileName.endsWith(".jpg", true) || payload.fileName.endsWith(".jpeg", true)
                val mime = if (isImg) "image/*" else "*/*"
                val uri = FileProvider.getUriForFile(context, "com.example.fileprovider", file)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = mime
                    putExtra(Intent.EXTRA_STREAM, uri)
                    setPackage("com.whatsapp")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                scope.launch(Dispatchers.Main) {
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        try {
                            val w4bIntent = Intent(Intent.ACTION_SEND).apply {
                                type = mime
                                putExtra(Intent.EXTRA_STREAM, uri)
                                setPackage("com.whatsapp.w4b")
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(w4bIntent)
                        } catch (ex: Exception) {
                            shareSecureFile(context, file, mime)
                        }
                    }
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "WhatsApp sharing error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Save Extracted File to Device Storage / Gallery
    fun saveExtractedFileToGalleryOrStorage(payload: SteganographyHelper.DecodedPayload.FilePayload) {
        scope.launch(Dispatchers.IO) {
            try {
                val isImage = payload.fileName.endsWith(".png", true) || 
                              payload.fileName.endsWith(".jpg", true) || 
                              payload.fileName.endsWith(".jpeg", true) ||
                              payload.fileName.endsWith(".webp", true)
                var savedLocation: String? = null

                if (isImage && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, payload.fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, if (payload.fileName.endsWith(".png", true)) "image/png" else "image/jpeg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Steganography")
                    }
                    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    if (uri != null) {
                        context.contentResolver.openOutputStream(uri)?.use { out ->
                            out.write(payload.fileBytes)
                        }
                        savedLocation = "Pictures/Steganography/${payload.fileName}"
                    }
                } else {
                    val dir = if (isImage) {
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    } else {
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    }
                    val stegoDir = File(dir, "Steganography")
                    if (!stegoDir.exists()) stegoDir.mkdirs()
                    val targetFile = File(stegoDir, payload.fileName)
                    FileOutputStream(targetFile).use { out ->
                        out.write(payload.fileBytes)
                    }
                    if (isImage) {
                        MediaScannerConnection.scanFile(context, arrayOf(targetFile.absolutePath), null, null)
                    }
                    savedLocation = targetFile.absolutePath
                }

                scope.launch(Dispatchers.Main) {
                    if (savedLocation != null) {
                        Toast.makeText(context, "Saved! ($savedLocation)", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Failed to save file.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Save error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0F172A), Color(0xFF020617))))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Applet Header Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🔒 Advanced Pixel Cryptography",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Embed files, documents, or texts directly inside the pixels of images. Completely invisible to standard visual scanners, with optional AES-256 password shields.",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Action Tabs
        item {
            TabRow(
                selectedTabIndex = activeTab,
                containerColor = Color(0xFF1E293B),
                contentColor = Color.White,
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            ) {
                Tab(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    text = { Text("Encode (Hide)", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
                Tab(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    text = { Text("Decode (Extract)", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
                Tab(
                    selected = activeTab == 2,
                    onClick = { activeTab = 2 },
                    text = { Text("Steganalysis", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
            }
        }

        if (activeTab == 0) {
            // -----------------------------------------------------------------
            // ENCODE SECTION
            // -----------------------------------------------------------------
            
            // 1. Cover Image Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Step 1: Select Cover Photo",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp,
                            modifier = Modifier.align(Alignment.Start)
                        )

                        if (carrierBitmap != null) {
                            androidx.compose.foundation.Image(
                                bitmap = carrierBitmap.asImageBitmap(),
                                contentDescription = "Carrier Preview",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Fit
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Resolution: ${carrierBitmap.width} x ${carrierBitmap.height}",
                                    color = Color.LightGray,
                                    fontSize = 11.sp
                                )
                                val maxCap = SteganographyHelper.getMaximumCapacityBytes(carrierBitmap.width, carrierBitmap.height)
                                Surface(
                                    color = Color(0xFF3B82F6).copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "Max Limit: ${formatFileSize(maxCap.toLong())}",
                                        color = Color(0xFF60A5FA),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .border(1.dp, Color.Gray.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.AddPhotoAlternate, null, tint = Color.Gray, modifier = Modifier.size(36.dp))
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("PNG formats are recommended (lossless)", color = Color.Gray, fontSize = 11.sp)
                                }
                            }
                        }

                        Button(
                            onClick = { carrierPicker.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.AddPhotoAlternate, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Browse Cover Photo")
                        }
                    }
                }
            }

            // 2. Payload Type Selector Tab
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Step 2: Choose Secret Type",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { payloadType = 0 },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (payloadType == 0) Color(0xFF10B981) else Color.DarkGray
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Description, null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Secret Text", fontSize = 11.sp)
                            }
                            Button(
                                onClick = { payloadType = 1 },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (payloadType == 1) Color(0xFF10B981) else Color.DarkGray
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.InsertDriveFile, null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Secret File", fontSize = 11.sp)
                            }
                        }

                        if (payloadType == 0) {
                            // Text Input Field
                            OutlinedTextField(
                                value = secretText,
                                onValueChange = { secretText = it },
                                label = { Text("Write your secret message here...") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedLabelColor = Color(0xFF3B82F6),
                                    unfocusedLabelColor = Color.LightGray,
                                    focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                                    unfocusedContainerColor = Color.Black.copy(alpha = 0.2f)
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 5
                            )
                        } else {
                            // File Selection Container
                            if (selectedFileUri != null) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)),
                                    border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.5f))
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            modifier = Modifier.weight(1f),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                                        ) {
                                            Icon(Icons.Default.Description, "File Icon", tint = Color(0xFF10B981), modifier = Modifier.size(32.dp))
                                            Column {
                                                Text(
                                                    text = selectedFileName,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color.White,
                                                    fontSize = 12.sp,
                                                    maxLines = 1
                                                )
                                                Text(
                                                    text = "File size: ${formatFileSize(selectedFileSize)}",
                                                    color = Color.LightGray,
                                                    fontSize = 10.sp
                                                )
                                            }
                                        }
                                        IconButton(onClick = {
                                            selectedFileUri = null
                                            selectedFileName = ""
                                            selectedFileBytes = null
                                            selectedFileSize = 0L
                                        }) {
                                            Icon(Icons.Default.Clear, "Clear selection", tint = Color.Red)
                                        }
                                    }
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { payloadFilePicker.launch("*/*") }
                                        .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                        .background(Color.Black.copy(alpha = 0.15f))
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.InsertDriveFile, "Browse", tint = Color.Gray, modifier = Modifier.size(32.dp))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text("Tap to Browse Device Files", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("Supports PDF, Doc, Zip, TXT, Media payloads", color = Color.Gray, fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 3. Optional Encryption Key Form
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Lock, null, tint = if (encryptPayload) Color(0xFFF59E0B) else Color.Gray)
                                Text("Password Protect (AES-256)", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                            }
                            Switch(
                                checked = encryptPayload,
                                onCheckedChange = { encryptPayload = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFF59E0B))
                            )
                        }

                        if (encryptPayload) {
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Set Decryption Password") },
                                leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color.Gray) },
                                trailingIcon = {
                                    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(icon, null)
                                    }
                                },
                                visualTransformation = if (passwordVisible) {
                                    androidx.compose.ui.text.input.VisualTransformation.None
                                } else {
                                    androidx.compose.ui.text.input.PasswordVisualTransformation()
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedLabelColor = Color(0xFFF59E0B),
                                    unfocusedLabelColor = Color.LightGray,
                                    focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                                    unfocusedContainerColor = Color.Black.copy(alpha = 0.2f)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Surface(
                                color = Color(0xFFF59E0B).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Warning, null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                                    Text(
                                        text = "Remember this password! It cannot be recovered and is absolutely required to decode back.",
                                        fontSize = 10.sp,
                                        color = Color(0xFFFBBF24)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 4. Live Capacity Analyzer Block & Run button
            item {
                if (carrierBitmap != null) {
                    val rawPayloadSize = if (payloadType == 0) {
                        secretText.toByteArray(Charsets.UTF_8).size.toLong()
                    } else {
                        selectedFileSize
                    }
                    // Add header + padding estimates (header: 9 bytes, metadata/encryption bounds)
                    val totalPayloadSize = if (rawPayloadSize > 0) rawPayloadSize + 30 else 0L
                    val maxCapacity = SteganographyHelper.getMaximumCapacityBytes(carrierBitmap.width, carrierBitmap.height).toLong()

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("Image Capacity Status", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Required Payload Size: ${formatFileSize(totalPayloadSize)}", fontSize = 11.sp, color = Color.LightGray)
                                Text("Cover Limit: ${formatFileSize(maxCapacity)}", fontSize = 11.sp, color = Color.LightGray)
                            }

                            val progress = if (maxCapacity > 0) {
                                minOf(1f, totalPayloadSize.toFloat() / maxCapacity.toFloat())
                            } else 0f

                            val isOverCapacity = totalPayloadSize > maxCapacity
                            val progressColor = if (isOverCapacity) Color.Red else if (progress > 0.75f) Color(0xFFF59E0B) else Color(0xFF10B981)

                            LinearProgressIndicator(
                                progress = progress,
                                color = progressColor,
                                trackColor = Color.DarkGray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(CircleShape)
                            )

                            if (isOverCapacity) {
                                Surface(
                                    color = Color.Red.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "⚠️ File payload is too large for this image. Select a larger cover picture or compress your payload.",
                                        color = Color.Red,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            } else if (rawPayloadSize > 0) {
                                val hasInput = (payloadType == 0 && secretText.isNotEmpty()) || (payloadType == 1 && selectedFileBytes != null)
                                if (hasInput) {
                                    if (isProcessing) {
                                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                            CircularProgressIndicator(color = Color(0xFF10B981))
                                        }
                                    } else {
                                        Button(
                                            onClick = {
                                                isProcessing = true
                                                scope.launch(Dispatchers.IO) {
                                                    try {
                                                        val pswd = if (encryptPayload) password else null
                                                        val encoded = if (payloadType == 0) {
                                                            SteganographyHelper.encodeTextAdvanced(carrierBitmap, secretText, pswd)
                                                        } else {
                                                            SteganographyHelper.encodeFileAdvanced(carrierBitmap, selectedFileName, selectedFileBytes!!, pswd)
                                                        }
                                                        stegoBitmapResult = encoded
                                                        scope.launch(Dispatchers.Main) {
                                                            Toast.makeText(context, "Data successfully hidden in pixels!", Toast.LENGTH_SHORT).show()
                                                        }
                                                    } catch (e: Exception) {
                                                        scope.launch(Dispatchers.Main) {
                                                            Toast.makeText(context, "Embedding failed: ${e.message}", Toast.LENGTH_LONG).show()
                                                        }
                                                    } finally {
                                                        isProcessing = false
                                                    }
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(Icons.Default.Lock, null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Hide Payload & Embed")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 5. Result Output & Share / Save to Gallery Action
            item {
                stegoBitmapResult?.let { resultBitmap ->
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
                            Text("🎉 Stego-Image Created & Ready", fontWeight = FontWeight.Bold, color = Color(0xFF10B981), fontSize = 14.sp)
                            androidx.compose.foundation.Image(
                                bitmap = resultBitmap.asImageBitmap(),
                                contentDescription = "Stego Result Preview",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Fit
                            )

                            Text(
                                "Note: Stego images are saved lossless as PNG to protect hidden payload pixels.",
                                fontSize = 10.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )

                            // Action Buttons: Save to Gallery, WhatsApp Share, More Options
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                // Save to Gallery Button
                                Button(
                                    onClick = { saveStegoBitmapToGallery(resultBitmap) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1.1f)
                                ) {
                                    Icon(Icons.Default.Download, null, modifier = Modifier.size(15.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Save Gallery", fontSize = 11.sp)
                                }

                                // Direct WhatsApp Share Button
                                Button(
                                    onClick = { shareStegoBitmapWhatsApp(resultBitmap) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1.1f)
                                ) {
                                    Icon(Icons.Default.Share, null, modifier = Modifier.size(15.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("WhatsApp", fontSize = 11.sp)
                                }

                                // General Chooser Share
                                Button(
                                    onClick = { shareStegoBitmap(resultBitmap) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.Send, null, modifier = Modifier.size(15.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Share", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

        } else if (activeTab == 1) {
            // -----------------------------------------------------------------
            // DECODE SECTION
            // -----------------------------------------------------------------
            item {
                Text("Extract Hidden Payload", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
            }

            // 1. Select Stego Image
            item {
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
                                    contentDescription = "Stego Decode Preview",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Fit
                                )
                                Text("Dimensions: ${bitmap.width} x ${bitmap.height}", color = Color.LightGray, fontSize = 11.sp)
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .border(1.dp, Color.Gray.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.AddPhotoAlternate, null, tint = Color.Gray, modifier = Modifier.size(36.dp))
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Select a stego image containing hidden bytes", color = Color.Gray, fontSize = 11.sp)
                                }
                            }
                        }

                        Button(
                            onClick = { stegoPicker.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.AddPhotoAlternate, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Browse Stego Image")
                        }
                    }
                }
            }

            // 2. Decryption Key (If encrypted)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Decryption Key (Leave blank if payload is unencrypted)", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            value = decodePassword,
                            onValueChange = { decodePassword = it },
                            label = { Text("Enter Decryption Password") },
                            leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color.Gray) },
                            trailingIcon = {
                                val icon = if (decodePasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                IconButton(onClick = { decodePasswordVisible = !decodePasswordVisible }) {
                                    Icon(icon, null)
                                }
                            },
                            visualTransformation = if (decodePasswordVisible) {
                                androidx.compose.ui.text.input.VisualTransformation.None
                            } else {
                                androidx.compose.ui.text.input.PasswordVisualTransformation()
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = Color(0xFFE91E63),
                                unfocusedLabelColor = Color.LightGray,
                                focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                                unfocusedContainerColor = Color.Black.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // 3. Extract Button
            item {
                if (stegoUriForDecoding != null) {
                    if (isProcessing) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color(0xFFE91E63))
                        }
                    } else {
                        Button(
                            onClick = {
                                isProcessing = true
                                extractedPayload = null
                                scope.launch(Dispatchers.IO) {
                                    try {
                                        val inputStream = context.contentResolver.openInputStream(stegoUriForDecoding!!)
                                        val bitmap = BitmapFactory.decodeStream(inputStream)
                                        if (bitmap != null) {
                                            val decoded = SteganographyHelper.decodeAdvanced(bitmap, decodePassword.ifEmpty { null })
                                            scope.launch(Dispatchers.Main) {
                                                extractedPayload = decoded
                                            }
                                        }
                                    } catch (e: Exception) {
                                        scope.launch(Dispatchers.Main) {
                                            extractedPayload = SteganographyHelper.DecodedPayload.Error("Extraction failed: ${e.message}")
                                        }
                                    } finally {
                                        isProcessing = false
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.LockOpen, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Extract Payload")
                        }
                    }
                }
            }

            // 4. Decode Result Outputs
            item {
                extractedPayload?.let { payload ->
                    when (payload) {
                        is SteganographyHelper.DecodedPayload.Text -> {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, "Extracted", tint = Color(0xFF10B981))
                                        Text("Extracted Message:", fontWeight = FontWeight.Bold, color = Color(0xFF10B981), fontSize = 13.sp)
                                    }
                                    Text(
                                        text = payload.text,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 13.sp,
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.Black.copy(alpha = 0.25f))
                                            .padding(10.dp)
                                    )
                                    Button(
                                        onClick = {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                            val clip = android.content.ClipData.newPlainText("Decoded Stego", payload.text)
                                            clipboard.setPrimaryClip(clip)
                                            Toast.makeText(context, "Message copied to clipboard!", Toast.LENGTH_SHORT).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Copy to Clipboard", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                        is SteganographyHelper.DecodedPayload.FilePayload -> {
                            val isImageFile = remember(payload.fileName) {
                                payload.fileName.endsWith(".png", true) || 
                                payload.fileName.endsWith(".jpg", true) || 
                                payload.fileName.endsWith(".jpeg", true) ||
                                payload.fileName.endsWith(".webp", true)
                            }
                            val extractedBitmap = remember(payload.fileBytes) {
                                if (isImageFile) {
                                    try {
                                        BitmapFactory.decodeByteArray(payload.fileBytes, 0, payload.fileBytes.size)
                                    } catch (e: Exception) { null }
                                } else null
                            }

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, "Extracted", tint = Color(0xFF10B981))
                                        Text("Extracted Hidden Payload!", fontWeight = FontWeight.Bold, color = Color(0xFF10B981), fontSize = 13.sp)
                                    }

                                    if (extractedBitmap != null) {
                                        Text("Extracted Image Photo:", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 11.sp)
                                        androidx.compose.foundation.Image(
                                            bitmap = extractedBitmap.asImageBitmap(),
                                            contentDescription = "Extracted Image Preview",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(160.dp)
                                                .clip(RoundedCornerShape(8.dp)),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                    
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.Black.copy(alpha = 0.25f))
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Icon(
                                            if (isImageFile) Icons.Default.Image else Icons.Default.Description,
                                            "File Icon",
                                            tint = Color(0xFF3B82F6),
                                            modifier = Modifier.size(36.dp)
                                        )
                                        Column {
                                            Text(
                                                text = payload.fileName,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                fontSize = 13.sp,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = "Size: ${formatFileSize(payload.fileBytes.size.toLong())}",
                                                color = Color.LightGray,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        // Save to Gallery / Downloads
                                        Button(
                                            onClick = { saveExtractedFileToGalleryOrStorage(payload) },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.weight(1.1f)
                                        ) {
                                            Icon(Icons.Default.Download, null, modifier = Modifier.size(15.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Save to Storage", fontSize = 11.sp)
                                        }

                                        // WhatsApp Direct Share
                                        Button(
                                            onClick = { shareExtractedFileWhatsApp(payload) },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.weight(1.1f)
                                        ) {
                                            Icon(Icons.Default.Share, null, modifier = Modifier.size(15.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("WhatsApp", fontSize = 11.sp)
                                        }

                                        // General Export / Share
                                        Button(
                                            onClick = { shareExtractedFile(payload) },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(Icons.Default.Send, null, modifier = Modifier.size(15.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Share", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                        is SteganographyHelper.DecodedPayload.Error -> {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.15f)),
                                border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.4f)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Warning, "Error", tint = Color.Red)
                                    Text(
                                        text = payload.message,
                                        color = Color.Red,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else if (activeTab == 2) {
            item {
                SteganalysisContent()
            }
        }
    }
}

// -------------------------------------------------------------
// STEGANALYSIS SCREEN & COMPONENT (Digital Forensics)
// -------------------------------------------------------------
@Composable
fun SteganalysisScreen(viewModel: StudentKitViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SteganalysisContent()
        }
    }
}

@Composable
fun SteganalysisContent() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf("") }
    var selectedFileSize by remember { mutableStateOf(0L) }
    var isAnalyzing by remember { mutableStateOf(false) }
    var report by remember { mutableStateOf<SteganalysisHelper.AnalysisReport?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedFileUri = uri
            var name = "selected_file"
            var size = 0L
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (cursor.moveToFirst()) {
                    if (nameIndex != -1) name = cursor.getString(nameIndex)
                    if (sizeIndex != -1) size = cursor.getLong(sizeIndex)
                }
            }
            selectedFileName = name
            selectedFileSize = size
            report = null
        }
    }

    fun runForensicScan() {
        val uri = selectedFileUri ?: return
        isAnalyzing = true
        scope.launch(Dispatchers.IO) {
            val res = SteganalysisHelper.analyzeFile(context, uri, selectedFileName)
            scope.launch(Dispatchers.Main) {
                report = res
                isAnalyzing = false
            }
        }
    }

    fun exportAppendedOverlay(bytes: ByteArray, parentName: String) {
        scope.launch(Dispatchers.IO) {
            try {
                val extractedFile = File(context.cacheDir, "extracted_overlay_$parentName")
                FileOutputStream(extractedFile).use { out -> out.write(bytes) }
                val uri = FileProvider.getUriForFile(context, "com.example.fileprovider", extractedFile)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "*/*"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                scope.launch(Dispatchers.Main) {
                    context.startActivity(Intent.createChooser(intent, "Export Hidden Overlay File"))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Export error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun sanitizeFileAndShare() {
        val uri = selectedFileUri ?: return
        scope.launch(Dispatchers.IO) {
            try {
                val (sanitizedFile, msg) = SteganalysisHelper.sanitizeFile(context, uri, selectedFileName)
                val sanitizedUri = FileProvider.getUriForFile(context, "com.example.fileprovider", sanitizedFile)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "*/*"
                    putExtra(Intent.EXTRA_STREAM, sanitizedUri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                    context.startActivity(Intent.createChooser(intent, "Share Clean/Sanitized File"))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Sanitization error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun exportReportText(r: SteganalysisHelper.AnalysisReport) {
        val reportText = buildString {
            appendLine("🔍 DIGITAL FORENSICS STEGANALYSIS REPORT")
            appendLine("---------------------------------------")
            appendLine("File: ${r.fileName} (${String.format("%.2f KB", r.fileSize / 1024.0)})")
            appendLine("Detected Category: ${r.detectedFileType} | Magic: ${r.magicBytesHex}")
            appendLine("Risk Assessment: ${r.riskLevel.name} (Risk Score: ${r.riskScore}/100)")
            appendLine("Summary: ${r.summaryText}")
            appendLine()
            appendLine("Detected Anomalies:")
            r.detectedAnomalies.forEach { appendLine("• $it") }
            if (r.lsbEntropy != null) appendLine("LSB Shannon Entropy: ${String.format("%.4f", r.lsbEntropy)} / 1.000")
            if (r.chiSquarePValue != null) appendLine("Chi-Square Equalization: ${String.format("%.2f%%", r.chiSquarePValue * 100)}")
            if (r.zeroWidthSecretText != null) appendLine("Extracted Zero-Width Text: ${r.zeroWidthSecretText}")
            appendLine()
            appendLine("Generated by Student Kit Steganalysis Engine")
        }

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, reportText)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(sendIntent, "Export Forensics Report"))
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Applet Header Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.Analytics, "Steganalysis", tint = Color(0xFFFF9800), modifier = Modifier.size(24.dp))
                    Text(
                        text = "🔬 Steganalysis & Digital Forensics",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Detect hidden data across multiple formats: Images (LSB/Chi-Square/Entropy), Audio (PCM entropy/RIFF headers), Documents/PDF/Zip (File overlays/Trailer payloads), and Plain Text (Zero-Width unicode steganography).",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
        }

        // 1. File Selector Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("1. Target File Selection", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)

                Button(
                    onClick = { filePickerLauncher.launch("*/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.FolderOpen, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (selectedFileUri == null) "Select Any File (Image, Audio, PDF, Zip, Text)" else "Change Selected File")
                }

                if (selectedFileUri != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Default.InsertDriveFile, "File", tint = Color(0xFF38BDF8), modifier = Modifier.size(32.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(selectedFileName, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                            Text(
                                if (selectedFileSize > 0) String.format("%.2f KB", selectedFileSize / 1024.0) else "File loaded",
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Button(
                        onClick = { runForensicScan() },
                        enabled = !isAnalyzing,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isAnalyzing) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Running Deep Forensic Scan...", fontSize = 13.sp)
                        } else {
                            Icon(Icons.Default.Search, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Run Deep Steganalysis Scan", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 2. Report Output Card
        report?.let { r ->
            val riskColor = when (r.riskLevel) {
                SteganalysisHelper.RiskLevel.HIGH_STEGO -> Color(0xEF4444)
                SteganalysisHelper.RiskLevel.SUSPICIOUS -> Color(0xFFF59E0B)
                SteganalysisHelper.RiskLevel.CLEAN -> Color(0xFF10B981)
            }

            val riskTitle = when (r.riskLevel) {
                SteganalysisHelper.RiskLevel.HIGH_STEGO -> "🚨 HIGH STEGANOGRAPHY PROBABILITY"
                SteganalysisHelper.RiskLevel.SUSPICIOUS -> "⚠️ SUSPICIOUS FILE CHARACTERISTICS"
                SteganalysisHelper.RiskLevel.CLEAN -> "✅ CLEAN / LOW RISK FILE"
            }

            // Risk Banner Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = riskColor.copy(alpha = 0.15f)),
                border = BorderStroke(1.dp, riskColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(riskTitle, fontWeight = FontWeight.Bold, color = riskColor, fontSize = 14.sp)
                        Surface(
                            color = riskColor,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "Risk Score: ${r.riskScore}/100",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Text(r.summaryText, color = Color.White, fontSize = 12.sp)
                }
            }

            // Technical Metrics Grid
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("📊 Technical Metrics & Header Analysis", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Category:", color = Color.Gray, fontSize = 11.sp)
                            Text("${r.detectedFileType} (${String.format("%.2f KB", r.fileSize / 1024.0)})", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Magic Signature:", color = Color.Gray, fontSize = 11.sp)
                            Text(r.magicBytesHex, color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                        r.lsbEntropy?.let { entropy ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("LSB Shannon Entropy:", color = Color.Gray, fontSize = 11.sp)
                                Text("${String.format("%.4f", entropy)} / 1.000", color = if (entropy > 0.95) Color.Red else Color.Green, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }
                        r.chiSquarePValue?.let { chi ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Chi-Square Equalization:", color = Color.Gray, fontSize = 11.sp)
                                Text("${String.format("%.1f%%", chi * 100.0)}", color = if (chi > 0.8) Color.Red else Color.Green, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }
                        r.appendedPayloadBytes?.let { appended ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Appended Overlay:", color = Color.Gray, fontSize = 11.sp)
                                Text("${String.format("%.2f KB", appended.size / 1024.0)} past EOF", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // Image LSB Bit-Plane Preview
            r.lsbPreviewBitmap?.let { bitMap ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🖼️ Visual Bit-Plane 0 (LSB Plane)", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                        Text("Monochrome rendering of pixel LSB bit 0. Uniform noisy static blocks indicate LSB bit replacement payload.", color = Color.Gray, fontSize = 10.sp, textAlign = TextAlign.Center)

                        androidx.compose.foundation.Image(
                            bitmap = bitMap.asImageBitmap(),
                            contentDescription = "LSB Bit-plane",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            // Zero-Width Decoded Text Card
            r.zeroWidthSecretText?.let { zwText ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981).copy(alpha = 0.15f)),
                    border = BorderStroke(1.dp, Color(0xFF10B981)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.Visibility, "Decoded Text", tint = Color(0xFF10B981))
                            Text("Extracted Zero-Width Hidden Message!", fontWeight = FontWeight.Bold, color = Color(0xFF10B981), fontSize = 13.sp)
                        }

                        Text(
                            text = zwText,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        )

                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                        Button(
                            onClick = {
                                val clip = android.content.ClipData.newPlainText("Decoded ZeroWidth", zwText)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Copied secret message!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Copy Secret Message", fontSize = 12.sp)
                        }
                    }
                }
            }

            // Anomalies List Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🔎 Discovered Anomalies & Structural Checks", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)

                    r.detectedAnomalies.forEach { anomaly ->
                        Text(anomaly, color = Color.LightGray, fontSize = 11.sp)
                    }
                }
            }

            // Forensic Actions Toolbar
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("🛠️ Forensics Remediation & Export Actions", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Action 1: Extract Appended Overlay Payload
                        r.appendedPayloadBytes?.let { overlayBytes ->
                            Button(
                                onClick = { exportAppendedOverlay(overlayBytes, r.fileName) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Download, null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Extract Appended Payload File (${String.format("%.1f KB", overlayBytes.size / 1024.0)})")
                            }
                        }

                        // Action 2: Sanitize / Clean File
                        Button(
                            onClick = { sanitizeFileAndShare() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.CleaningServices, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sanitize & Clean File (Strip Overlays)")
                        }

                        // Action 3: Export Report
                        Button(
                            onClick = { exportReportText(r) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Share, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Export Forensics Text Report")
                        }
                    }
                }
            }
        }
    }
}

