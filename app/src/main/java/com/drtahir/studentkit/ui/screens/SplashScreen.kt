package com.drtahir.studentkit.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drtahir.studentkit.R
import com.drtahir.studentkit.viewmodel.Screen
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import kotlinx.coroutines.delay

/**
 * High-craft Animated Brand Logo Component
 * Combines golden orbital rings, pulsing glowing radiance, and multi-layered depth.
 */
@Composable
fun AnimatedBrandLogo(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    showRings: Boolean = true,
    isInteractive: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "logo_anim")

    // Rotation for outer cosmic ring
    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring_rotate"
    )

    // Reverse rotation for inner accent dots
    val innerRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "inner_rotate"
    )

    // Breathing pulse scale
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    // Golden glow opacity
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    Box(
        modifier = modifier
            .size(size + if (showRings) 24.dp else 0.dp)
            .then(
                if (isInteractive && onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        // Outer Glowing Aura
        Box(
            modifier = Modifier
                .size(size * 1.15f)
                .scale(pulseScale)
                .alpha(glowAlpha)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFFD54F).copy(alpha = 0.45f),
                            Color(0xFF00C853).copy(alpha = 0.20f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Orbital Ring Canvas
        if (showRings) {
            Canvas(
                modifier = Modifier
                    .size(size + 20.dp)
                    .rotate(ringRotation)
            ) {
                // Gold geometric dashed ring
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xFFFFD700),
                            Color(0xFF00E676),
                            Color(0xFF00B0FF),
                            Color(0xFFFFD700)
                        )
                    ),
                    radius = this.size.minDimension / 2f,
                    style = Stroke(
                        width = 2.dp.toPx()
                    )
                )
            }

            // Accent orbital dots
            Canvas(
                modifier = Modifier
                    .size(size + 10.dp)
                    .rotate(innerRotation)
            ) {
                val r = this.size.minDimension / 2f
                drawCircle(
                    color = Color(0xFFFFD54F),
                    radius = 3.dp.toPx(),
                    center = Offset(this.size.width / 2f + r * 0.95f, this.size.height / 2f)
                )
                drawCircle(
                    color = Color(0xFF00E676),
                    radius = 3.dp.toPx(),
                    center = Offset(this.size.width / 2f - r * 0.95f, this.size.height / 2f)
                )
            }
        }

        // Inner Core Logo Shield / Badge
        Surface(
            modifier = Modifier
                .size(size)
                .scale(pulseScale)
                .shadow(12.dp, shape = RoundedCornerShape(percent = 32)),
            shape = RoundedCornerShape(percent = 32),
            color = Color(0xFF0A192F),
            border = BorderStroke(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFD700),
                        Color(0xFF81C784),
                        Color(0xFF0D47A1)
                    )
                )
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Image Logo Asset
                Image(
                    painter = painterResource(id = R.drawable.app_brand_logo_1787573819899),
                    contentDescription = "Hikmah Omni Suite Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(percent = 30))
                )

                // Shimmer Overlay highlight sweep
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.15f),
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.05f)
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                            )
                        )
                )
            }
        }
    }
}

/**
 * Full-Screen Animated Splash Screen
 * Automatically progresses with loading status and allows quick tap-through.
 */
@Composable
fun SplashScreen(
    viewModel: StudentKitViewModel,
    modifier: Modifier = Modifier,
    onFinish: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }
    var statusText by remember { mutableStateOf("Initializing Suite Engines...") }

    // Start auto-progress animation
    LaunchedEffect(Unit) {
        val steps = listOf(
            0.20f to "Warming Secure Sandbox...",
            0.50f to "Verifying Offline Databases...",
            0.80f to "Loading Smart Utilities & Modules...",
            1.00f to "Welcome to Hikmah Omni Suite!"
        )

        for ((targetProgress, message) in steps) {
            statusText = message
            val start = progress
            val delta = targetProgress - start
            val frames = 12
            for (i in 1..frames) {
                progress = start + (delta * (i / frames.toFloat()))
                delay(35)
            }
            delay(180)
        }
        delay(250)
        onFinish()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "splash_glow")
    val titleShimmer by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "title_shimmer"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF051329),
                        Color(0xFF0A1E3F),
                        Color(0xFF030D1A)
                    )
                )
            )
            .clickable { onFinish() }
            .testTag("splash_screen_container")
    ) {
        // Subtle ambient background light circles
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-60).dp)
                .clip(CircleShape)
                .background(Color(0xFF00E676).copy(alpha = 0.06f))
                .blur(40.dp)
        )
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 80.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFD700).copy(alpha = 0.05f))
                .blur(50.dp)
        )

        // Center Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top App Pill / Category Flag
            Row(
                modifier = Modifier.padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.08f),
                    border = BorderStroke(1.dp, Color(0xFFFFD700).copy(alpha = 0.35f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "HIKMAH OMNI SUITE PRO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFE082),
                            letterSpacing = 1.2.sp
                        )
                    }
                }
            }

            // Center Logo and Titles
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Animated Master Brand Logo
                AnimatedBrandLogo(
                    size = 120.dp,
                    showRings = true,
                    isInteractive = true,
                    onClick = { onFinish() }
                )

                Spacer(modifier = Modifier.height(6.dp))

                // App Title with luxury gold touch
                Text(
                    text = "Hikmah Omni Suite",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(titleShimmer)
                )

                Text(
                    text = "All-in-One Multi-Utility, Clinical & Enterprise Suite",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFB0BEC5),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }

            // Bottom Progress & Developer Signature
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Status message
                Text(
                    text = statusText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFFD54F),
                    textAlign = TextAlign.Center
                )

                // Elegant Linear Progress Bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Color(0xFFFFD700),
                    trackColor = Color.White.copy(alpha = 0.12f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Developer Signature
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Developed by Dr. Tahir Buneri",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White.copy(alpha = 0.6f),
                        letterSpacing = 0.4.sp
                    )
                }

                Text(
                    text = "Tap anywhere to skip",
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.35f)
                )
            }
        }
    }
}
