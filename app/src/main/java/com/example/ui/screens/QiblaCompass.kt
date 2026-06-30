package com.example.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.input.pointer.pointerInput
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import java.util.Locale
import kotlin.math.*

// City definition for fallback and testing
data class PresetCity(
    val name: String,
    val nameUrdu: String,
    val latitude: Double,
    val longitude: Double,
    val description: String
)

val PRESET_CITIES = listOf(
    PresetCity("Islamabad", "اسلام آباد", 33.6844, 73.0479, "Pakistan - Qibla: 261.6°"),
    PresetCity("Karachi", "کراچی", 24.8607, 67.0011, "Pakistan - Qibla: 264.4°"),
    PresetCity("Lahore", "لاہور", 31.5204, 74.3587, "Pakistan - Qibla: 262.1°"),
    PresetCity("Mecca", "مکہ مکرمہ", 21.4225, 39.8262, "Saudi Arabia (Centered)"),
    PresetCity("Medina", "مدینہ منورہ", 24.4672, 39.6111, "Saudi Arabia - Qibla: 178.6°"),
    PresetCity("London", "لندن", 51.5074, -0.1278, "United Kingdom - Qibla: 118.9°"),
    PresetCity("New York", "نیویارک", 40.7128, -74.0060, "United States - Qibla: 58.5°"),
    PresetCity("Dhaka", "ڈھاکہ", 23.8103, 90.4125, "Bangladesh - Qibla: 276.5°"),
    PresetCity("Jakarta", "جاکارتا", -6.2088, 106.8456, "Indonesia - Qibla: 295.1°")
)

@Composable
fun QiblaCompassWidget(
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surface
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    // Coordinates and Location states
    var selectedCity by remember { mutableStateOf(PRESET_CITIES[0]) }
    var currentLatitude by remember { mutableStateOf(selectedCity.latitude) }
    var currentLongitude by remember { mutableStateOf(selectedCity.longitude) }
    var locationSource by remember { mutableStateOf("Manual: Islamabad") }
    var isCustomCoordinatesMode by remember { mutableStateOf(false) }

    // Compass Sensor States
    var rawAzimuth by remember { mutableStateOf(0f) }
    var continuousTargetAngle by remember { mutableStateOf(0f) }

    // Find shortest angular difference between rawAzimuth and current target to avoid 359->0 spinning glitch
    LaunchedEffect(rawAzimuth) {
        var diff = (rawAzimuth - continuousTargetAngle) % 360f
        if (diff < -180f) diff += 360f
        if (diff > 180f) diff -= 360f
        continuousTargetAngle += diff
    }

    // Buttery-smooth spring-based physics animation for high-refresh rate devices
    val smoothAzimuth by animateFloatAsState(
        targetValue = continuousTargetAngle,
        animationSpec = spring(
            dampingRatio = 0.8f, // Ultra organic fluid compass feel
            stiffness = 110f     // Responsive but completely smooth
        ),
        label = "SmoothAzimuth"
    )

    // Manual Simulation Mode (allows dragging/sliding to rotate compass when sensors are absent)
    var isManualMode by remember { mutableStateOf(false) }
    var hasSensorActivity by remember { mutableStateOf(false) }

    // Auto-detect if sensors are actually firing, otherwise switch to manual mode automatically
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        if (!hasSensorActivity) {
            isManualMode = true
        }
    }

    // GPS Location States & Permission Launcher
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasLocationPermission = isGranted
        if (isGranted) {
            locationSource = "Acquiring GPS..."
        }
    }

    // Active Expansion of the Compass to view controls
    var isExpanded by remember { mutableStateOf(false) }

    // Qibla math calculations
    // Kaaba Coordinates: Lat 21.422524, Lon 39.826206
    val qiblaAngle = remember(currentLatitude, currentLongitude) {
        val lat1Rad = Math.toRadians(currentLatitude)
        val lon1Rad = Math.toRadians(currentLongitude)
        val lat2Rad = Math.toRadians(21.422524)
        val lon2Rad = Math.toRadians(39.826206)

        val y = sin(lon2Rad - lon1Rad)
        val x = cos(lat1Rad) * sin(lat2Rad) - sin(lat1Rad) * cos(lat2Rad) * cos(lon2Rad - lon1Rad)
        var angleRad = atan2(y, x)
        var angleDeg = Math.toDegrees(angleRad)
        if (angleDeg < 0) {
            angleDeg += 360.0
        }
        angleDeg.toFloat()
    }

    // Distances
    val distanceToKaaba = remember(currentLatitude, currentLongitude) {
        val r = 6371.0 // Earth radius in km
        val lat1Rad = Math.toRadians(currentLatitude)
        val lon1Rad = Math.toRadians(currentLongitude)
        val lat2Rad = Math.toRadians(21.422524)
        val lon2Rad = Math.toRadians(39.826206)

        val dLat = lat2Rad - lat1Rad
        val dLon = lon2Rad - lon1Rad

        val a = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        r * c
    }

    // Alignment logic
    val headingDiff = remember(smoothAzimuth, qiblaAngle) {
        var diff = (smoothAzimuth - qiblaAngle) % 360
        if (diff < -180) diff += 360
        if (diff > 180) diff -= 360
        abs(diff)
    }

    val isAligned = headingDiff <= 3.5f

    // Trigger haptic pulse when first entering alignment zone
    var prevAlignedState by remember { mutableStateOf(false) }
    LaunchedEffect(isAligned) {
        if (isAligned && !prevAlignedState) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
        prevAlignedState = isAligned
    }

    // Sensor registration effect
    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        var gravity: FloatArray? = null
        var geomagnetic: FloatArray? = null

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                hasSensorActivity = true
                if (isManualMode) return

                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    val rotationMatrix = FloatArray(9)
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    val orientation = FloatArray(3)
                    SensorManager.getOrientation(rotationMatrix, orientation)
                    val azimuthRad = orientation[0]
                    var azimuthDeg = Math.toDegrees(azimuthRad.toDouble()).toFloat()
                    if (azimuthDeg < 0) azimuthDeg += 360f
                    rawAzimuth = azimuthDeg
                } else {
                    if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                        gravity = event.values.clone()
                    } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                        geomagnetic = event.values.clone()
                    }

                    if (gravity != null && geomagnetic != null) {
                        val rMatrix = FloatArray(9)
                        val iMatrix = FloatArray(9)
                        if (SensorManager.getRotationMatrix(rMatrix, iMatrix, gravity, geomagnetic)) {
                            val orientation = FloatArray(3)
                            SensorManager.getOrientation(rMatrix, orientation)
                            val azimuthRad = orientation[0]
                            var azimuthDeg = Math.toDegrees(azimuthRad.toDouble()).toFloat()
                            if (azimuthDeg < 0) azimuthDeg += 360f
                            rawAzimuth = azimuthDeg
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        if (rotationVectorSensor != null) {
            sensorManager.registerListener(listener, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(listener, magnetometer, SensorManager.SENSOR_DELAY_UI)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    // Animated smooth azimuth angle is handled at the state level above

    // GPS Telemetry updates inside DisposableEffect
    DisposableEffect(hasLocationPermission) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsListener: LocationListener? = null

        if (hasLocationPermission) {
            try {
                val lastKnownGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val lastKnownNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                val bestLastLocation = lastKnownGps ?: lastKnownNetwork

                if (bestLastLocation != null) {
                    currentLatitude = bestLastLocation.latitude
                    currentLongitude = bestLastLocation.longitude
                    locationSource = "GPS: ${String.format(Locale.US, "%.4f", currentLatitude)}, ${String.format(Locale.US, "%.4f", currentLongitude)}"
                    isCustomCoordinatesMode = false
                }

                gpsListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        currentLatitude = location.latitude
                        currentLongitude = location.longitude
                        locationSource = "GPS: ${String.format(Locale.US, "%.4f", currentLatitude)}, ${String.format(Locale.US, "%.4f", currentLongitude)}"
                        isCustomCoordinatesMode = false
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000L, // 10 seconds
                    5f,     // 5 meters
                    gpsListener
                )
            } catch (e: SecurityException) {
                locationSource = "GPS Permission Error"
            }
        }

        onDispose {
            if (gpsListener != null) {
                try {
                    locationManager.removeUpdates(gpsListener)
                } catch (e: Exception) {
                    // Fail-safe cleanup
                }
            }
        }
    }

    // Elegant Pulsing Animation for Glowing Aligned states
    val alignmentGlowAlpha by animateFloatAsState(
        targetValue = if (isAligned) 0.8f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowPulse"
    )

    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    
    // Glassmorphic translucent background matching Islamic colors
    val glassBackground = if (isDark) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0x3E0A2216), // Frosted dark emerald green (24% opacity)
                Color(0x2B1A1A24)  // Frosted dark charcoal (17% opacity)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xF2FFFFFF), // Subtly transparent milky white (95%)
                Color(0xCEFFFDF3)  // Warm frosted gold-beige bottom (80%)
            )
        )
    }

    // Gradient translucent border imitating glass prism light reflection
    val glassBorderBrush = if (isAligned) {
        Brush.linearGradient(
            colors = listOf(
                IslamicGold.copy(alpha = max(0.5f, alignmentGlowAlpha)),
                IslamicGold.copy(alpha = 0.25f),
                EmeraldLight.copy(alpha = 0.4f)
            )
        )
    } else {
        if (isDark) {
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.18f),
                    Color.White.copy(alpha = 0.05f),
                    IslamicGold.copy(alpha = 0.10f)
                )
            )
        } else {
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.85f),
                    SoftGold.copy(alpha = 0.55f),
                    Color.White.copy(alpha = 0.4f)
                )
            )
        }
    }

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = glassBackground,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = if (isAligned) 2.2.dp else 1.2.dp,
                brush = glassBorderBrush,
                shape = RoundedCornerShape(24.dp)
            )
            .testTag("qibla_compass_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Widget Title & Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (isAligned) IslamicGold.copy(alpha = 0.2f) else EmeraldLight.copy(
                                    alpha = 0.1f
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Explore,
                            contentDescription = "Compass Icon",
                            tint = if (isAligned) IslamicGold else EmeraldLight,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Live Qibla Finder",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Text(
                            text = if (isAligned) "Aligned with Kaaba! 🕋" else "Rotate phone to point to Kaaba",
                            fontSize = 11.sp,
                            fontWeight = if (isAligned) FontWeight.Bold else FontWeight.Normal,
                            color = if (isAligned) IslamicGold else CharcoalDark.copy(alpha = 0.7f)
                        )
                    }
                }

                // Show/Hide expanding button
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Expand controls",
                        tint = EmeraldDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Compass Mode Switcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isManualMode) "Mode: Manual Simulator 🧭" else "Mode: Live Sensors 📱",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalDark.copy(alpha = 0.8f)
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(GoldenBeige)
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (!isManualMode) EmeraldLight else Color.Transparent)
                            .clickable { isManualMode = false }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Auto",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!isManualMode) Color.White else CharcoalDark.copy(alpha = 0.7f)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isManualMode) IslamicGold else Color.Transparent)
                            .clickable { isManualMode = true }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Simulate",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isManualMode) Color.White else CharcoalDark.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // Main Compass Graphic Display - Styled as a Frosted Glass Crystal Rotating Lens
            val dialBackground = if (isDark) {
                Brush.radialGradient(
                    colors = listOf(
                        if (isAligned) IslamicGold.copy(alpha = 0.22f) else Color(0x22FFFFFF),
                        Color(0x0C000000)
                    )
                )
            } else {
                Brush.radialGradient(
                    colors = listOf(
                        if (isAligned) IslamicGold.copy(alpha = 0.16f) else Color(0xECFFFFFF),
                        Color(0x55FDF6E2)
                    )
                )
            }

            val dialBorderBrush = if (isAligned) {
                Brush.linearGradient(
                    colors = listOf(
                        IslamicGold,
                        IslamicGold.copy(alpha = 0.5f)
                    )
                )
            } else {
                if (isDark) {
                    Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.White.copy(alpha = 0.08f)
                        )
                    )
                } else {
                    Brush.linearGradient(
                        colors = listOf(
                            SoftGold,
                            Color.White.copy(alpha = 0.65f)
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(220.dp)
                    .background(
                        brush = dialBackground,
                        shape = CircleShape
                    )
                    .border(
                        width = if (isAligned) 3.dp else 1.5.dp,
                        brush = dialBorderBrush,
                        shape = CircleShape
                    )
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { _ ->
                                isManualMode = true
                            },
                            onDrag = { change, _ ->
                                change.consume()
                                val centerX = size.width / 2f
                                val centerY = size.height / 2f
                                val x = change.position.x - centerX
                                val y = change.position.y - centerY
                                var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble())).toFloat()
                                angle = (angle + 90f + 360f) % 360f
                                rawAzimuth = angle
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                // Background compass dial drawing
                Canvas(
                    modifier = Modifier
                        .size(190.dp)
                ) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = size.width / 2

                    // Rotate entire canvas dial according to phone heading
                    rotate(degrees = -smoothAzimuth, pivot = center) {
                        // Draw outer reference ticks
                        for (i in 0 until 360 step 15) {
                            val tickLength = if (i % 90 == 0) 14.dp.toPx() else if (i % 45 == 0) 9.dp.toPx() else 5.dp.toPx()
                            val strokeWidth = if (i % 45 == 0) 2.dp.toPx() else 1.dp.toPx()
                            val color = if (i % 90 == 0) EmeraldDark else SoftGold

                            val angleRad = Math.toRadians(i.toDouble())
                            val startX = center.x + (radius - tickLength) * sin(angleRad).toFloat()
                            val startY = center.y - (radius - tickLength) * cos(angleRad).toFloat()
                            val endX = center.x + radius * sin(angleRad).toFloat()
                            val endY = center.y - radius * cos(angleRad).toFloat()

                            drawLine(
                                color = color,
                                start = Offset(startX, startY),
                                end = Offset(endX, endY),
                                strokeWidth = strokeWidth
                            )
                        }

                        // Native typography for Cardinal Directions (N, E, S, W) on the dial
                        drawIntoCanvas { canvas ->
                            val textPaint = android.graphics.Paint().apply {
                                color = android.graphics.Color.argb(220, 15, 81, 50) // EmeraldDark
                                textSize = 13.dp.toPx()
                                isFakeBoldText = true
                                textAlign = android.graphics.Paint.Align.CENTER
                            }

                            val goldPaint = android.graphics.Paint().apply {
                                color = android.graphics.Color.argb(255, 212, 175, 55) // IslamicGold
                                textSize = 15.dp.toPx()
                                isFakeBoldText = true
                                textAlign = android.graphics.Paint.Align.CENTER
                            }

                            // Draw "N", "E", "S", "W"
                            val labelPadding = 20.dp.toPx()
                            canvas.nativeCanvas.drawText("N", center.x, center.y - radius + labelPadding, goldPaint)
                            canvas.nativeCanvas.drawText("E", center.x + radius - labelPadding, center.y + 4.dp.toPx(), textPaint)
                            canvas.nativeCanvas.drawText("S", center.x, center.y + radius - labelPadding + 8.dp.toPx(), textPaint)
                            canvas.nativeCanvas.drawText("W", center.x - radius + labelPadding, center.y + 4.dp.toPx(), textPaint)
                        }

                        // Draw Qibla marker (Crescent/Kaaba indication) on the dial at the qiblaAngle
                        rotate(degrees = qiblaAngle, pivot = center) {
                            // Draw path pointing to Kaaba
                            val markerRadius = radius - 36.dp.toPx()
                            val markerOffset = Offset(center.x, center.y - markerRadius)

                            // Outer gold indicator circle at Qibla angle
                            drawCircle(
                                color = IslamicGold,
                                radius = 10.dp.toPx(),
                                center = markerOffset
                            )

                            // Inner green dot
                            drawCircle(
                                color = EmeraldDark,
                                radius = 5.dp.toPx(),
                                center = markerOffset
                            )

                            // Draw Mecca Arrow path pointing out
                            val arrowPath = Path().apply {
                                moveTo(center.x, center.y - radius + 8.dp.toPx())
                                lineTo(center.x - 7.dp.toPx(), center.y - radius + 22.dp.toPx())
                                lineTo(center.x + 7.dp.toPx(), center.y - radius + 22.dp.toPx())
                                close()
                            }
                            drawPath(
                                path = arrowPath,
                                color = IslamicGold
                            )
                        }
                    }

                    // Static top pointer (fixed to the top of the phone screen)
                    val pointerPath = Path().apply {
                        moveTo(center.x, center.y - radius - 6.dp.toPx())
                        lineTo(center.x - 10.dp.toPx(), center.y - radius + 10.dp.toPx())
                        lineTo(center.x + 10.dp.toPx(), center.y - radius + 10.dp.toPx())
                        close()
                    }
                    drawPath(
                        path = pointerPath,
                        color = if (isAligned) IslamicGold else EmeraldLight
                    )

                    // Simple clean center pin
                    drawCircle(
                        color = if (isAligned) IslamicGold else EmeraldDark,
                        radius = 8.dp.toPx(),
                        center = center
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 3.dp.toPx(),
                        center = center
                    )
                }

                // Centered text showing Qibla indicator symbol 🕋
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "🕋",
                        fontSize = if (isAligned) 42.sp else 34.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.rotate(qiblaAngle - smoothAzimuth)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${smoothAzimuth.roundToInt()}°",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Compass bearing telemetry readout
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "HEADING",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = CharcoalDark.copy(alpha = 0.5f),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${smoothAzimuth.roundToInt()}° ${getDirectionLetter(smoothAzimuth)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }

                Divider(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp),
                    color = SoftGold
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "QIBLA DIRECTION",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = CharcoalDark.copy(alpha = 0.5f),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${qiblaAngle.roundToInt()}° ${getDirectionLetter(qiblaAngle)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = IslamicGold
                    )
                }

                Divider(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp),
                    color = SoftGold
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "DISTANCE",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = CharcoalDark.copy(alpha = 0.5f),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${distanceToKaaba.roundToInt()} km",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }
            }

            // Expanded Controls: Permission requests, Manual locations, preset cities
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(color = SoftGold, modifier = Modifier.padding(bottom = 12.dp))

                    if (isManualMode) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Simulate Device Heading Rotation:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalDark.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = "${smoothAzimuth.roundToInt()}°",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = IslamicGold
                                )
                            }
                            Slider(
                                value = rawAzimuth,
                                onValueChange = { rawAzimuth = it },
                                valueRange = 0f..360f,
                                colors = SliderDefaults.colors(
                                    thumbColor = IslamicGold,
                                    activeTrackColor = IslamicGold,
                                    inactiveTrackColor = SoftGold
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    // Location telemetry status badge
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(GoldenBeige, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📍 Location: $locationSource",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = CharcoalDark.copy(alpha = 0.8f)
                            )

                            if (!hasLocationPermission) {
                                Text(
                                    text = "Enable GPS",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldLight,
                                    modifier = Modifier
                                        .clickable {
                                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                        }
                                        .padding(4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // City Presets horizontal selector chips
                    Text(
                        text = "Simulate different locations (highly recommended for emulator verification):",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = CharcoalDark.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(androidx.compose.foundation.rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PRESET_CITIES.forEach { city ->
                            val isSelected = selectedCity.name == city.name && !isCustomCoordinatesMode
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedCity = city
                                    currentLatitude = city.latitude
                                    currentLongitude = city.longitude
                                    locationSource = "Manual: ${city.name}"
                                    isCustomCoordinatesMode = false
                                },
                                label = {
                                    Text(
                                        text = "${city.name} (${city.nameUrdu})",
                                        fontSize = 11.sp
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldLight,
                                    selectedLabelColor = Color.White,
                                    containerColor = GoldenBeige
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

// Helper to determine compass letter directions
private fun getDirectionLetter(degree: Float): String {
    val deg = (degree % 360 + 360) % 360
    return when {
        deg >= 337.5 || deg < 22.5 -> "N"
        deg >= 22.5 && deg < 67.5 -> "NE"
        deg >= 67.5 && deg < 112.5 -> "E"
        deg >= 112.5 && deg < 157.5 -> "SE"
        deg >= 157.5 && deg < 202.5 -> "S"
        deg >= 202.5 && deg < 247.5 -> "SW"
        deg >= 247.5 && deg < 292.5 -> "W"
        deg >= 292.5 && deg < 337.5 -> "NW"
        else -> "N"
    }
}
