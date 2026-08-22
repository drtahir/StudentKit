package com.drtahir.studentkit.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drtahir.studentkit.R
import kotlinx.coroutines.delay

@Composable
fun AnimatedBrandSplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val bgGradient = if (isDark) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF031614),
                Color(0xFF062822),
                Color(0xFF02100E)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFE8F5E9),
                Color(0xFFF1F8E9),
                Color(0xFFFFFFFF)
            )
        )
    }

    // Intro entrance scale & alpha animatable
    val logoScale = remember { Animatable(0.4f) }
    val logoAlpha = remember { Animatable(0f) }
    val contentAlpha = remember { Animatable(0f) }
    var showTagline by remember { mutableStateOf(false) }

    // Infinite breathing glow & shimmer
    val infiniteTransition = rememberInfiniteTransition(label = "splash_infinite")
    val pulseGlow by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_glow"
    )

    val auraRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "aura_rotation"
    )

    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val brandTitleBrush = Brush.linearGradient(
        colors = if (isDark) {
            listOf(
                Color(0xFFFFD54F),
                Color(0xFF80CBC4),
                Color(0xFF4DB6AC),
                Color(0xFFFFE082),
                Color(0xFF26A69A)
            )
        } else {
            listOf(
                Color(0xFF00695C),
                Color(0xFF004D40),
                Color(0xFF00897B),
                Color(0xFFD4AF37),
                Color(0xFF00695C)
            )
        },
        start = Offset(shimmerOffset - 300f, 0f),
        end = Offset(shimmerOffset + 300f, 150f)
    )

    LaunchedEffect(Unit) {
        logoScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
        )
    }

    LaunchedEffect(Unit) {
        logoAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = LinearEasing)
        )
        delay(200)
        contentAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
        )
        showTagline = true
        delay(1400)
        onSplashFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgGradient)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onSplashFinished()
            }
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Animated Brand Logo Container with Halo and Rings
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(170.dp)
                    .graphicsLayer {
                        scaleX = logoScale.value
                        scaleY = logoScale.value
                        alpha = logoAlpha.value
                    }
            ) {
                // Outer subtle glowing aura ring
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .scale(pulseGlow)
                        .rotate(auraRotation)
                        .clip(CircleShape)
                        .background(
                            Brush.sweepGradient(
                                colors = listOf(
                                    Color(0xFF00897B).copy(alpha = 0.35f),
                                    Color(0xFFFFD54F).copy(alpha = 0.25f),
                                    Color(0xFF004D40).copy(alpha = 0.35f),
                                    Color(0xFF26A69A).copy(alpha = 0.4f),
                                    Color(0xFF00897B).copy(alpha = 0.35f)
                                )
                            )
                        )
                )

                // Soft background shadow disc
                Box(
                    modifier = Modifier
                        .size(136.dp)
                        .shadow(
                            elevation = 20.dp,
                            shape = CircleShape,
                            ambientColor = Color(0xFF004D40),
                            spotColor = Color(0xFF26A69A)
                        )
                        .clip(CircleShape)
                        .background(if (isDark) Color(0xFF07211C) else Color(0xFFE0F2F1))
                )

                // The Brand Logo Image
                Surface(
                    shape = CircleShape,
                    border = BorderStroke(
                        width = 2.5.dp,
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFFFFD54F),
                                Color(0xFF00897B),
                                Color(0xFFFFE082),
                                Color(0xFF26A69A)
                            )
                        )
                    ),
                    shadowElevation = 12.dp,
                    color = Color.Transparent,
                    modifier = Modifier.size(126.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_brand_logo),
                        contentDescription = "Hikmah Omni Suite Brand Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Animated Typography Brand Block
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(contentAlpha.value)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Hikmah",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        style = TextStyle(
                            brush = brandTitleBrush,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isDark) Color(0xFF004D40).copy(alpha = 0.7f) else Color(0xFF00695C).copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, Color(0xFF26A69A).copy(alpha = 0.5f)),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "Omni Suite",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = if (isDark) Color(0xFF80CBC4) else Color(0xFF00695C),
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 3.5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                AnimatedVisibility(
                    visible = showTagline,
                    enter = fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.9f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "INTELLIGENCE • KNOWLEDGE • PRODUCTIVITY",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            color = if (isDark) Color(0xFFB2DFDB) else Color(0xFF004D40).copy(alpha = 0.85f)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Comprehensive All-in-One Professional Workspace",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (isDark) Color(0xFF80CBC4).copy(alpha = 0.7f) else Color(0xFF004D40).copy(alpha = 0.65f)
                        )
                    }
                }
            }
        }

        // Bottom skip / loading row
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
                .alpha(contentAlpha.value),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = if (isDark) Color(0xFF80CBC4) else Color(0xFF00695C)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Tap anywhere to enter",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = if (isDark) Color(0xFF80CBC4).copy(alpha = 0.6f) else Color(0xFF004D40).copy(alpha = 0.6f)
            )
        }
    }
}
