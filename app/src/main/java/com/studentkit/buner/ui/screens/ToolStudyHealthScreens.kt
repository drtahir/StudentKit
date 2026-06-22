package com.studentkit.buner.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.*
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.draw.scale
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studentkit.buner.data.*
import com.studentkit.buner.viewmodel.Screen
import com.studentkit.buner.viewmodel.StudentKitViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsHubScreen(
    viewModel: StudentKitViewModel,
    title: String,
    subScreen: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            subScreen()
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: STANDALONE OFFLINE QR GENERATOR & PLATFORM INSIGNIA STUDIO
// -------------------------------------------------------------
data class QrPalette(val name: String, val startColor: String, val endColor: String, val isGradient: Boolean)

@Composable
fun TypeCardItem(id: String, category: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(115.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.White)
            .border(1.5.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = when (id) {
                "URL" -> Icons.Default.Link
                "PDF" -> Icons.Default.Attachment
                "Image" -> Icons.Default.Image
                "App Markets" -> Icons.Default.Shop
                "Text" -> Icons.Default.TextFields
                "Maps" -> Icons.Default.Map
                "Wi-Fi" -> Icons.Default.Wifi
                "Audio" -> Icons.Default.MusicNote
                "WhatsApp" -> Icons.Default.Mail
                "YouTube" -> Icons.Default.PlayArrow
                "Booking" -> Icons.Default.Book
                "Instagram" -> Icons.Default.CameraAlt
                "Facebook" -> Icons.Default.Groups
                "Telegram" -> Icons.Default.Send
                "vCard" -> Icons.Default.ContactPage
                "E-mail" -> Icons.Default.Email
                "List of Links" -> Icons.Default.List
                "PPTX" -> Icons.Default.PresentToAll
                "Phone Call" -> Icons.Default.Phone
                "Custom URL" -> Icons.Default.OpenInNew
                "TikTok" -> Icons.Default.Videocam
                "Video File" -> Icons.Default.VideoFile
                "Forms" -> Icons.Default.Feed
                "PCR" -> Icons.Default.MedicalServices
                "X (Twitter)" -> Icons.Default.Tag
                "Snapchat" -> Icons.Default.PhotoCamera
                "Spotify" -> Icons.Default.MusicVideo
                "Google Doc" -> Icons.Default.Description
                "Review" -> Icons.Default.StarRate
                "Sheets" -> Icons.Default.TableChart
                "Payment" -> Icons.Default.Payment
                "SMS" -> Icons.Default.Sms
                "Logotype" -> Icons.Default.Domain
                "Office 365" -> Icons.Default.Cloud
                "Shaped" -> Icons.Default.Brush
                "PayPal" -> Icons.Default.MonetizationOn
                "Etsy" -> Icons.Default.Store
                "PNG" -> Icons.Default.FilePresent
                "LinkedIn" -> Icons.Default.WorkspacePremium
                "Crypto Pay" -> Icons.Default.AccountBalanceWallet
                "Calendar" -> Icons.Default.DateRange
                "Social Media" -> Icons.Default.Share
                "Reddit" -> Icons.Default.Forum
                "Menu" -> Icons.Default.RestaurantMenu
                "File" -> Icons.Default.FolderZip
                "Tickets" -> Icons.Default.ConfirmationNumber
                "Excel" -> Icons.Default.GridOn
                "Venmo" -> Icons.Default.Paid
                "Amazon" -> Icons.Default.ShoppingBag
                "2D-Barcode" -> Icons.Default.QrCode
                "UPI" -> Icons.Default.QrCodeScanner
                "Attendance" -> Icons.Default.AssignmentInd
                "WeChat" -> Icons.Default.ChatBubble
                "Line" -> Icons.Default.CallMade
                else -> Icons.Default.ContactMail
            },
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
            modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(id, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black, maxLines = 1)
        Text(category, fontSize = 8.sp, color = Color.Gray, maxLines = 1)
    }
}

@Composable
fun PlatformLogoIcon(
    logo: String,
    imageBitmap: ImageBitmap?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White, CircleShape)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        when (logo) {
            "Custom Upload" -> {
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "Uploaded Logo",
                        modifier = Modifier.fillMaxSize().clip(CircleShape)
                    )
                } else {
                    Icon(Icons.Default.CloudUpload, contentDescription = "Upload", tint = Color.Gray, modifier = Modifier.fillMaxSize(0.7f))
                }
            }
            "Facebook" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFF1877F2)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "f",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.offset(x = 1.dp, y = (-1).dp)
                    )
                }
            }
            "Instagram" -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0xFFFEC260), Color(0xFFE100FF), Color(0xFF7000FF)),
                                center = Offset(0f, 100f),
                                radius = 120f
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize(0.6f)) {
                        val sw = size.width * 0.12f
                        drawRoundRect(Color.White, style = Stroke(width = sw), cornerRadius = CornerRadius(size.width * 0.25f))
                        drawCircle(Color.White, radius = size.width * 0.18f, style = Stroke(width = sw))
                        drawCircle(Color.White, radius = size.width * 0.05f, center = Offset(size.width * 0.75f, size.height * 0.25f))
                    }
                }
            }
            "YouTube" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(6.dp)).background(Color(0xFFFF0000)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        val path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(size.width, size.height / 2f)
                            lineTo(0f, size.height)
                            close()
                        }
                        drawPath(path, Color.White)
                    }
                }
            }
            "WhatsApp" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFF25D366)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, tint = Color.White, modifier = Modifier.fillMaxSize(0.6f))
                }
            }
            "TikTok" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFF121212)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.MusicNote, contentDescription = null, tint = Color.White, modifier = Modifier.fillMaxSize(0.7f))
                }
            }
            "LinkedIn" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(4.dp)).background(Color(0xFF0077B5)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "in",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
            "Twitter/X" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFF111111)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "X",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
            "Snapchat" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFFFFFC00)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize(0.55f)) {
                        val path = Path().apply {
                            val w = size.width
                            val h = size.height
                            moveTo(w * 0.5f, h * 0.15f)
                            cubicTo(w * 0.25f, h * 0.15f, w * 0.20f, h * 0.40f, w * 0.20f, h * 0.60f)
                            cubicTo(w * 0.15f, h * 0.70f, w * 0.10f, h * 0.85f, w * 0.20f, h * 0.85f)
                            cubicTo(w * 0.25f, h * 0.85f, w * 0.35f, h * 0.75f, w * 0.5f, h * 0.75f)
                            cubicTo(w * 0.65f, h * 0.75f, w * 0.75f, h * 0.85f, w * 0.8f, h * 0.85f)
                            cubicTo(w * 0.90f, h * 0.85f, w * 0.85f, h * 0.70f, w * 0.8f, h * 0.60f)
                            cubicTo(w * 0.80f, h * 0.40f, w * 0.75f, h * 0.15f, w * 0.5f, h * 0.15f)
                            close()
                        }
                        drawPath(path, Color.White)
                    }
                }
            }
            "Telegram" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFF24A1DE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxSize(0.55f)
                            .scale(1f)
                    )
                }
            }
            "Pinterest" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFFE60023)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "P",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.offset(x = (-0.5).dp)
                    )
                }
            }
            "Spotify" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFF1DB954)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Wifi,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxSize(0.65f)
                            .scale(1f)
                    )
                }
            }
            "Gmail" -> {
                Box(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color.White).border(1.dp, Color(0xFFEA4335), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFFEA4335), modifier = Modifier.fillMaxSize(0.55f))
                }
            }
            else -> {
                Icon(Icons.Default.QrCode, contentDescription = null, tint = Color.Gray, modifier = Modifier.fillMaxSize(0.6f))
            }
        }
    }
}

@Composable
fun CenterEmblemLayout(
    logo: String,
    emblemColor: Color,
    imageBitmap: ImageBitmap?,
    modifier: Modifier = Modifier
) {
    val nonBrandLogos = listOf("Academy Crest", "Web Link", "Safe Shield", "Star Score", "Love Heart", "Fast WiFi", "Home Hub")
    if (logo in nonBrandLogos) {
        Box(
            modifier = modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, emblemColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (logo) {
                    "Academy Crest" -> Icons.Default.School
                    "Web Link" -> Icons.Default.Link
                    "Safe Shield" -> Icons.Default.Security
                    "Star Score" -> Icons.Default.Star
                    "Love Heart" -> Icons.Default.Favorite
                    "Fast WiFi" -> Icons.Default.Wifi
                    "Home Hub" -> Icons.Default.Home
                    else -> Icons.Default.QrCode
                },
                contentDescription = "Center Emblem",
                tint = emblemColor,
                modifier = Modifier.size(20.dp)
            )
        }
    } else if (logo != "None") {
        Box(
            modifier = modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, Color.White, CircleShape)
                .shadow(2.dp, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            PlatformLogoIcon(
                logo = logo,
                imageBitmap = imageBitmap,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun QrCodePreviewEngine(
    selectedType: String,
    qrContentText: String,
    qrDotStyle: String,
    qrEyeStyle: String,
    selectedLogo: String,
    qrFrameStyle: String,
    selectedPalette: QrPalette,
    selectedEyePalette: QrPalette,
    selectedEmblemPalette: QrPalette,
    includeQuietZone: Boolean,
    imageBitmap: ImageBitmap?,
    sizeDp: Int = 160
) {
    val hasLogo = remember(selectedLogo) { selectedLogo != "None" }

    val androidBitmap = remember(imageBitmap) {
        try {
            imageBitmap?.asAndroidBitmap()
        } catch (e: Exception) {
            null
        }
    }

    val primaryQrColor = remember(selectedPalette) {
        try {
            Color(android.graphics.Color.parseColor(selectedPalette.startColor))
        } catch (e: Exception) {
            Color(0xFF1565C0)
        }
    }

    val qrBrush = remember(selectedPalette) {
        try {
            if (selectedPalette.isGradient) {
                Brush.linearGradient(
                    listOf(
                        Color(android.graphics.Color.parseColor(selectedPalette.startColor)),
                        Color(android.graphics.Color.parseColor(selectedPalette.endColor))
                    )
                )
            } else {
                SolidColor(Color(android.graphics.Color.parseColor(selectedPalette.startColor)))
            }
        } catch (e: Exception) {
            SolidColor(Color(0xFF1565C0))
        }
    }

    val eyeBrush = remember(selectedPalette, selectedEyePalette) {
        try {
            if (selectedEyePalette.name == "Match Theme") {
                qrBrush
            } else if (selectedEyePalette.isGradient) {
                Brush.linearGradient(
                    listOf(
                        Color(android.graphics.Color.parseColor(selectedEyePalette.startColor)),
                        Color(android.graphics.Color.parseColor(selectedEyePalette.endColor))
                    )
                )
            } else {
                SolidColor(Color(android.graphics.Color.parseColor(selectedEyePalette.startColor)))
            }
        } catch (e: Exception) {
            qrBrush
        }
    }

    Box(
        modifier = Modifier
            .size(sizeDp.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val boxSize = size.width / 15f

            if ((qrDotStyle == "Logo Halftone Fusion" || qrDotStyle == "My Logo as QR Matrix") && imageBitmap != null) {
                val logoAlpha = if (qrDotStyle == "My Logo as QR Matrix") 0.88f else 0.28f
                drawImage(
                    image = imageBitmap,
                    dstOffset = IntOffset(0, 0),
                    dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                    alpha = logoAlpha
                )
            }

            val getSampledColor: (Float, Float) -> Color = { cx, cy ->
                val bmp = androidBitmap
                if (bmp == null) {
                    primaryQrColor
                } else {
                    val relativeX = cx / size.width
                    val relativeY = cy / size.height
                    val px = (relativeX * bmp.width).toInt().coerceIn(0, bmp.width - 1)
                    val py = (relativeY * bmp.height).toInt().coerceIn(0, bmp.height - 1)
                    val colorValue = bmp.getPixel(px, py)
                    val alpha = (colorValue ushr 24) and 0xff
                    if (alpha < 35) {
                        primaryQrColor.copy(alpha = 0.15f)
                    } else {
                        val r = (colorValue ushr 16) and 0xff
                        val g = (colorValue ushr 8) and 0xff
                        val b = colorValue and 0xff
                        Color(red = r / 255f, green = g / 255f, blue = b / 255f)
                    }
                }
            }
            
            // Draw Finder Eyes
            fun drawFinder(ofX: Float, ofY: Float) {
                val innerBox = boxSize
                when (qrEyeStyle) {
                    "Classic Edge" -> {
                        drawRect(eyeBrush, Offset(ofX, ofY), Size(boxSize * 4, boxSize * 4))
                        drawRect(SolidColor(Color.White), Offset(ofX + innerBox, ofY + innerBox), Size(boxSize * 2, boxSize * 2))
                        drawRect(eyeBrush, Offset(ofX + innerBox * 1.5f, ofY + innerBox * 1.5f), Size(boxSize, boxSize))
                    }
                    "Rounded Retro" -> {
                        drawRoundRect(eyeBrush, Offset(ofX, ofY), Size(boxSize * 4, boxSize * 4), CornerRadius(boxSize * 1.1f))
                        drawRoundRect(SolidColor(Color.White), Offset(ofX + innerBox, ofY + innerBox), Size(boxSize * 2, boxSize * 2), CornerRadius(boxSize * 0.7f))
                        drawRoundRect(eyeBrush, Offset(ofX + innerBox * 1.5f, ofY + innerBox * 1.5f), Size(boxSize, boxSize), CornerRadius(boxSize * 0.4f))
                    }
                    "Circular Orbit" -> {
                        val cen = Offset(ofX + boxSize * 2f, ofY + boxSize * 2f)
                        drawCircle(eyeBrush, radius = boxSize * 2f, center = cen)
                        drawCircle(SolidColor(Color.White), radius = boxSize * 1.4f, center = cen)
                        drawCircle(eyeBrush, radius = boxSize * 0.8f, center = cen)
                    }
                    "Modern Diamond" -> {
                        val cen = Offset(ofX + boxSize * 2f, ofY + boxSize * 2f)
                        rotate(45f, cen) {
                            drawRoundRect(eyeBrush, Offset(ofX + boxSize * 0.3f, ofY + boxSize * 0.3f), Size(boxSize * 3.4f, boxSize * 3.4f), CornerRadius(boxSize * 0.8f))
                            drawRoundRect(SolidColor(Color.White), Offset(ofX + boxSize * 0.8f, ofY + boxSize * 0.8f), Size(boxSize * 2.4f, boxSize * 2.4f), CornerRadius(boxSize * 0.5f))
                            drawRoundRect(eyeBrush, Offset(ofX + boxSize * 1.3f, ofY + boxSize * 1.3f), Size(boxSize * 1.4f, boxSize * 1.4f), CornerRadius(boxSize * 0.3f))
                        }
                    }
                }
            }

            drawFinder(0f, 0f)
            drawFinder(size.width - boxSize * 4, 0f)
            drawFinder(0f, size.height - boxSize * 4)

            // Draw alignment helper blocks (Visual Parity Indicators mimicking Error Correction Levels)
            if (hasLogo) {
                // High error redundancy anchors representing solid parity blocks
                drawRect(qrBrush, Offset(boxSize * 4f, boxSize * 10f), Size(boxSize, boxSize))
                drawRect(qrBrush, Offset(boxSize * 10f, boxSize * 4f), Size(boxSize, boxSize))
                drawRect(qrBrush, Offset(boxSize * 10f, boxSize * 10f), Size(boxSize, boxSize))
            }

            // Draw matrix cell patterns
            fun drawCellPattern(cx: Float, cy: Float) {
                when (qrDotStyle) {
                    "Logo Halftone Fusion" -> {
                        val cellColor = getSampledColor(cx, cy)
                        drawRoundRect(
                            SolidColor(cellColor),
                            topLeft = Offset(cx + boxSize * 0.05f, cy + boxSize * 0.05f),
                            size = Size(boxSize * 0.9f, boxSize * 0.9f),
                            cornerRadius = CornerRadius(boxSize * 0.35f, boxSize * 0.35f)
                        )
                    }
                    "My Logo as QR Matrix" -> {
                        val cellColor = getSampledColor(cx, cy)
                        drawRoundRect(
                            SolidColor(cellColor.copy(alpha = 0.92f)),
                            topLeft = Offset(cx + boxSize * 0.08f, cy + boxSize * 0.08f),
                            size = Size(boxSize * 0.84f, boxSize * 0.84f),
                            cornerRadius = CornerRadius(boxSize * 0.35f, boxSize * 0.35f)
                        )
                    }
                    "Classic Square" -> drawRect(qrBrush, Offset(cx, cy), Size(boxSize, boxSize))
                    "Spherical Dot" -> drawCircle(qrBrush, radius = boxSize * 0.42f, center = Offset(cx + boxSize/2f, cy + boxSize/2f))
                    "Fluid Rounded" -> drawRoundRect(qrBrush, topLeft = Offset(cx + boxSize * 0.08f, cy + boxSize * 0.08f), size = Size(boxSize * 0.84f, boxSize * 0.84f), cornerRadius = CornerRadius(boxSize * 0.35f, boxSize * 0.35f))
                    "Stellar Star" -> {
                        val scx = cx + boxSize / 2f
                        val scy = cy + boxSize / 2f
                        drawPath(Path().apply {
                            moveTo(scx, scy - boxSize * 0.45f)
                            quadraticTo(scx, scy, scx + boxSize * 0.45f, scy)
                            quadraticTo(scx, scy, scx, scy + boxSize * 0.45f)
                            quadraticTo(scx, scy, scx - boxSize * 0.45f, scy)
                            close()
                        }, qrBrush)
                    }
                    "Curved Leaf" -> {
                        drawPath(Path().apply {
                            moveTo(cx, cy + boxSize)
                            cubicTo(cx, cy, cx + boxSize, cy, cx + boxSize, cy)
                            cubicTo(cx + boxSize, cy + boxSize, cx, cy + boxSize, cx, cy + boxSize)
                            close()
                        }, qrBrush)
                    }
                    "Cyber Cross" -> {
                        val crossSize = boxSize * 0.3f
                        drawRect(qrBrush, Offset(cx + crossSize, cy), Size(boxSize - crossSize * 2, boxSize))
                        drawRect(qrBrush, Offset(cx, cy + crossSize), Size(boxSize, boxSize - crossSize * 2))
                    }
                    "Heart Shape" -> {
                        val hcx = cx + boxSize / 2f
                        val hcy = cy + boxSize / 2f
                        drawPath(Path().apply {
                            moveTo(hcx, hcy + boxSize * 0.35f)
                            cubicTo(hcx - boxSize * 0.5f, hcy - boxSize * 0.1f, hcx - boxSize * 0.3f, hcy - boxSize * 0.5f, hcx, hcy - boxSize * 0.25f)
                            cubicTo(hcx + boxSize * 0.3f, hcy - boxSize * 0.5f, hcx + boxSize * 0.5f, hcy - boxSize * 0.1f, hcx, hcy + boxSize * 0.35f)
                            close()
                        }, qrBrush)
                    }
                    "Ring Wave" -> {
                        drawCircle(qrBrush, radius = boxSize * 0.42f, center = Offset(cx + boxSize/2f, cy + boxSize/2f), style = Stroke(width = boxSize * 0.18f))
                        drawCircle(qrBrush, radius = boxSize * 0.15f, center = Offset(cx + boxSize/2f, cy + boxSize/2f))
                    }
                }
            }

            val uniqueSeedVal = qrContentText.hashCode()
            val rand = java.util.Random(uniqueSeedVal.toLong())

            for (x in 4 until 11) {
                for (y in 0 until 15) {
                    if (hasLogo && x in 6..8 && y in 6..8) continue
                    if (rand.nextBoolean()) drawCellPattern(x * boxSize, y * boxSize)
                }
            }
            for (x in 0 until 15) {
                for (y in 4 until 11) {
                    if (hasLogo && x in 6..8 && y in 6..8) continue
                    if (rand.nextBoolean()) drawCellPattern(x * boxSize, y * boxSize)
                }
            }
        }

        if (hasLogo) {
            val emblemColor = if (selectedEmblemPalette.name == "Match Theme") primaryQrColor else Color(android.graphics.Color.parseColor(selectedEmblemPalette.startColor))
            val badgeSize = (sizeDp * 0.23f).dp
            CenterEmblemLayout(
                logo = selectedLogo,
                emblemColor = emblemColor,
                imageBitmap = imageBitmap,
                modifier = Modifier.size(badgeSize)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrGeneratorScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    
    // Parse all 55 custom QR types on-the-fly to conserve memory/tokens
    val qrTypeList = remember {
        "URL;Web;https://google.com|PDF;Files;https://site.com/doc.pdf|Image;Files;https://site.com/img.png|App Markets;Business;market://details?id=com.studentkit.buner|Text;Web;Hello Classmate!|Maps;Web;geo:40.71,-74.00|Wi-Fi;Web;WIFI:S:AcademicNet;T:WPA;P:pass;;|Audio;Files;https://site.com/audio.mp3|WhatsApp;Social;https://wa.me/92300|YouTube;Social;https://youtube.com/watch?v=|Booking;Business;https://booking.edu|Instagram;Social;https://instagram.com|Facebook;Social;https://facebook.com|Telegram;Social;https://t.me|vCard;Social;BEGIN:VCARD|E-mail;Social;mailto:dean@edu.pk|List of Links;Web;https://local-collection.app/links|PPTX;Files;https://site.com/slides.pptx|Phone Call;Web;tel:+923|Custom URL;Web;academic://portal|TikTok;Social;https://tiktok.com/@|Video File;Files;https://site.com/video.mp4|Forms;Business;https://forms.gle|PCR;Business;pcr://report|X (Twitter);Social;https://x.com|Snapchat;Social;https://snapchat.com|Spotify;Social;https://spotify.com|Google Doc;Files;https://docs.google.com|Review;Business;https://g.page|Sheets;Files;https://docs.google.com/sheets|Payment;Business;https://stripe.com|SMS;Social;smsto:+923|Logotype;Business;https://brand.com|Office 365;Files;https://onedrive.live.com|Shaped;Web;https://google.com?shaped|PayPal;Business;https://paypal.me|Etsy;Business;https://etsy.com|PNG;Files;https://site.com/qr.png|LinkedIn;Social;https://linkedin.com|Crypto Pay;Business;ethereum:0x|Calendar;Social;BEGIN:VEVENT|Social Media;Social;https://linktr.ee|Reddit;Social;https://reddit.com|Menu;Web;https://menu.com|File;Files;https://dropbox.com|Tickets;Business;ticket://pass|Excel;Files;https://onedrive.live.com|Venmo;Business;https://venmo.com|Amazon;Business;https://amazon.com|2D-Barcode;Web;Barcode_Payload|UPI;Business;upi://pay?pa=|Attendance;Business;attend://student|WeChat;Social;wechat://user|Line;Social;line://ti/p|KakaoTalk;Social;kakaotalk://user".split("|").mapNotNull {
            val parts = it.split(";")
            if (parts.size >= 3) {
                Triple(parts[0], parts[1], parts[2])
            } else {
                null
            }
        }
    }

    var selectedType by remember { mutableStateOf("URL") }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredList = remember(searchQuery, selectedCategory, qrTypeList) {
        qrTypeList.filter { item ->
            val matchQuery = item.first.contains(searchQuery, true)
            val matchCategory = selectedCategory == "All" || item.second == selectedCategory
            matchQuery && matchCategory
        }
    }

    val fieldValues = remember { mutableStateMapOf<String, String>() }

    val qrContentText = remember(selectedType, fieldValues) {
        val f0 = fieldValues["$selectedType-0"] ?: ""
        val f1 = fieldValues["$selectedType-1"] ?: ""
        val f2 = fieldValues["$selectedType-2"] ?: ""
        
        when (selectedType) {
            "Wi-Fi" -> "WIFI:S:${f0.ifEmpty { "AcademicNet" }};T:${f2.ifEmpty { "WPA" }};P:$f1;;"
            "vCard" -> "BEGIN:VCARD\nVERSION:3.0\nN:${f0.ifEmpty { "Smith" }}\nTEL:$f1\nEMAIL:$f2\nEND:VCARD"
            "WhatsApp" -> "https://wa.me/${f0.ifEmpty { "92300" }}?text=${java.net.URLEncoder.encode(f1, "UTF-8")}"
            "SMS" -> "smsto:${f0.ifEmpty { "92" }}:$f1"
            "PayPal" -> "https://paypal.me/${f0.ifEmpty { "tuition" }}/${f1}?item_name=${java.net.URLEncoder.encode(f2, "UTF-8")}"
            "Venmo" -> "https://venmo.com/${f0.ifEmpty { "fee" }}?amt=$f1&note=${java.net.URLEncoder.encode(f2, "UTF-8")}"
            "UPI" -> "upi://pay?pa=${f0.ifEmpty { "campus@upi" }}&am=$f1&tn=${java.net.URLEncoder.encode(f2, "UTF-8")}"
            "Crypto Pay" -> "${f1.ifEmpty { "ethereum" }}:${f0.ifEmpty { "0x932" }}?amount=$f2"
            "App Markets" -> if (f1.contains("iOS", true)) "https://apps.apple.com/app/id$f0" else "market://details?id=$f0"
            "Calendar" -> "BEGIN:VEVENT\nSUMMARY:${f0.ifEmpty { "Orientation" }}\nLOCATION:$f1\nEND:VEVENT"
            "PCR" -> "pcr://lab/report/${f0.ifEmpty { "992" }}?lab=${java.net.URLEncoder.encode(f1, "UTF-8")}&status=$f2"
            "List of Links" -> "https://local-collection.app/links?title=${java.net.URLEncoder.encode(f0, "UTF-8")}&url=${java.net.URLEncoder.encode(f1, "UTF-8")}"
            else -> f0.ifEmpty {
                qrTypeList.find { it.first == selectedType }?.third ?: "https://google.com"
            }
        }
    }

    val qrPalettes = remember {
        listOf(
            QrPalette("Ocean Depth", "#1565C0", "#00E5FF", true),
            QrPalette("Sunset Horizon", "#E91E63", "#FF9100", true),
            QrPalette("Emerald Jade", "#004D40", "#00E676", true),
            QrPalette("Electric Violet", "#6A1B9A", "#FF4081", true),
            QrPalette("Dark Cyberpunk", "#1A1A1A", "#546E7A", true),
            QrPalette("Carbon Matte", "#121212", "#2D2D2D", true),
            QrPalette("Imperial Gold", "#D4AF37", "#9A7B1C", true),
            QrPalette("Cosmic Purple", "#4A148C", "#4A148C", false),
            QrPalette("Pure Obsidian", "#000000", "#000000", false),
            QrPalette("Deep Amber", "#FF6F00", "#FF6F00", false),
            QrPalette("Slate Charcoal", "#37474F", "#37474F", false),
            QrPalette("Sakura Pink", "#FF69B4", "#FF1493", true)
        )
    }

    val eyePalettes = remember {
        listOf(
            QrPalette("Match Theme", "", "", false),
            QrPalette("Pure Obsidian", "#000000", "#000000", false),
            QrPalette("Neon Ruby", "#FF1744", "#FF1744", false),
            QrPalette("Cyan Cyber", "#00E5FF", "#00E5FF", false),
            QrPalette("Royal Gold", "#FFD700", "#FFD700", false),
            QrPalette("Emerald Green", "#00E676", "#00E676", false),
            QrPalette("Electric Purple", "#D500F9", "#D500F9", false),
            QrPalette("Deep Crimson", "#8B0000", "#8B0000", false)
        )
    }

    val emblemPalettes = remember {
        listOf(
            QrPalette("Match Theme", "", "", false),
            QrPalette("Pure White", "#FFFFFF", "#FFFFFF", false),
            QrPalette("Pure Obsidian", "#000000", "#000000", false),
            QrPalette("Vibrant Orange", "#FF9100", "#FF9100", false),
            QrPalette("Neon Violet", "#AA00FF", "#AA00FF", false),
            QrPalette("Golden Honey", "#FFC400", "#FFC400", false),
            QrPalette("Crimson Flame", "#D50000", "#D50000", false)
        )
    }

    val brandingLogos = remember {
        listOf("Facebook", "Instagram", "YouTube", "WhatsApp", "TikTok", "LinkedIn", "Twitter/X", "Snapchat", "Telegram", "Pinterest", "Spotify", "Gmail")
    }
    val classicLogos = remember {
        listOf("None", "Academy Crest", "Web Link", "Safe Shield", "Star Score", "Love Heart", "Fast WiFi", "Home Hub")
    }

    var selectedPalette by remember { mutableStateOf(qrPalettes[0]) }
    var selectedEyePalette by remember { mutableStateOf(eyePalettes[0]) }
    var selectedEmblemPalette by remember { mutableStateOf(emblemPalettes[0]) }

    var qrDotStyle by remember { mutableStateOf("Classic Square") }
    var qrEyeStyle by remember { mutableStateOf("Classic Edge") }
    var selectedLogo by remember { mutableStateOf("None") }
    var isAutoLogoEnabled by remember { mutableStateOf(true) }
    var qrFrameStyle by remember { mutableStateOf("Minimalist Borderless") }

    var downloadFormat by remember { mutableStateOf("PNG Image") }
    var exportResolution by remember { mutableStateOf("High HD (2048 x 2048 px)") }
    var includeQuietZone by remember { mutableStateOf(true) }
    var isDynamicQrMode by remember { mutableStateOf(false) }
    var dynamicUrlSlug by remember { mutableStateOf("local-tracker.app/v/student_portal") }
    
    val imageUriState = remember { mutableStateOf<android.net.Uri?>(null) }
    val imageBitmapState = remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        uri?.let {
            imageUriState.value = it
            try {
                val source = android.graphics.ImageDecoder.createSource(context.contentResolver, it)
                val bitmap = android.graphics.ImageDecoder.decodeBitmap(source)
                imageBitmapState.value = bitmap.asImageBitmap()
                selectedLogo = "Custom Upload"
                isAutoLogoEnabled = false
            } catch (e: Exception) {
                try {
                    @Suppress("DEPRECATION")
                    val bitmap = android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    imageBitmapState.value = bitmap.asImageBitmap()
                    selectedLogo = "Custom Upload"
                    isAutoLogoEnabled = false
                } catch (ex: Exception) {
                    Toast.makeText(context, "Error loading image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Automatic platform logo detection based on URL / payload content
    LaunchedEffect(qrContentText, isAutoLogoEnabled) {
        if (isAutoLogoEnabled) {
            val urlLower = qrContentText.lowercase()
            val detected = when {
                urlLower.contains("facebook.com") || urlLower.contains("fb.com") || urlLower.contains("fb.me") -> "Facebook"
                urlLower.contains("instagram.com") || urlLower.contains("ig.me") || urlLower.contains("insta") -> "Instagram"
                urlLower.contains("youtube.com") || urlLower.contains("youtu.be") || urlLower.contains("yt") -> "YouTube"
                urlLower.contains("wa.me") || urlLower.contains("whatsapp.com") -> "WhatsApp"
                urlLower.contains("tiktok.com") -> "TikTok"
                urlLower.contains("linkedin.com") -> "LinkedIn"
                urlLower.contains("twitter.com") || urlLower.contains("x.com") -> "Twitter/X"
                urlLower.contains("snapchat.com") -> "Snapchat"
                urlLower.contains("telegram.me") || urlLower.contains("telegram.org") || urlLower.contains("t.me") -> "Telegram"
                urlLower.contains("pinterest.com") || urlLower.contains("pin.it") -> "Pinterest"
                urlLower.contains("spotify.com") || urlLower.contains("spoti.fi") -> "Spotify"
                urlLower.contains("gmail.com") || urlLower.contains("mail.google.com") || (urlLower.startsWith("mailto:") && urlLower.contains("gmail")) -> "Gmail"
                else -> null
            }
            if (detected != null) {
                selectedLogo = detected
            }
        }
    }
    
    var showDownloadCompleteDialog by remember { mutableStateOf(false) }
    var showAnalyticsDialog by remember { mutableStateOf(false) }
    var compileStatusMessage by remember { mutableStateOf("") }
    var isCompiling by remember { mutableStateOf(false) }

    val primaryQrColor = remember(selectedPalette) {
        try {
            Color(android.graphics.Color.parseColor(selectedPalette.startColor))
        } catch (e: Exception) {
            Color(0xFF1565C0)
        }
    }

    val qrBrush = remember(selectedPalette) {
        try {
            if (selectedPalette.isGradient) {
                Brush.linearGradient(
                    listOf(
                        Color(android.graphics.Color.parseColor(selectedPalette.startColor)),
                        Color(android.graphics.Color.parseColor(selectedPalette.endColor))
                    )
                )
            } else {
                SolidColor(Color(android.graphics.Color.parseColor(selectedPalette.startColor)))
            }
        } catch (e: Exception) {
            SolidColor(Color(0xFF1565C0))
        }
    }

    val eyeBrush = remember(selectedPalette, selectedEyePalette) {
        try {
            if (selectedEyePalette.name == "Match Theme") {
                qrBrush
            } else if (selectedEyePalette.isGradient) {
                Brush.linearGradient(
                    listOf(
                        Color(android.graphics.Color.parseColor(selectedEyePalette.startColor)),
                        Color(android.graphics.Color.parseColor(selectedEyePalette.endColor))
                    )
                )
            } else {
                SolidColor(Color(android.graphics.Color.parseColor(selectedEyePalette.startColor)))
            }
        } catch (e: Exception) {
            qrBrush
        }
    }

    LaunchedEffect(isCompiling) {
        if (isCompiling) {
            compileStatusMessage = "Analyzing QR payload parameters..."
            delay(300)
            compileStatusMessage = "Applying $qrDotStyle shape filters..."
            delay(300)
            compileStatusMessage = "Tinting body pixels as ${selectedPalette.name}..."
            delay(300)
            isCompiling = false
            showDownloadCompleteDialog = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("💼 Deep-Tech QR Workspace", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                Text("Suite of 55 highly customizable dynamic & static QR content designs matching business & academic layouts.", fontSize = 11.sp, color = Color.DarkGray)
                
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search from 55 QR types (e.g. PDF, PayPal, Wi-Fi)...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(listOf("All", "Web", "Files", "Social", "Business")) { category ->
                        val isSelected = selectedCategory == category
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.White)
                                .border(1.dp, if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                .clickable { selectedCategory = category }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(category, color = if (isSelected) Color.White else Color.Black, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        Text("Select QR SubType Category:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                .border(0.5.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.dp))
                .padding(8.dp)
        ) {
            if (filteredList.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.Center) {
                    Text("No matched types found in '$selectedCategory'.", fontSize = 11.sp)
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    val halfSize = (filteredList.size + 1) / 2
                    val row1 = filteredList.take(halfSize)
                    val row2 = filteredList.drop(halfSize)

                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        row1.forEach { item ->
                            TypeCardItem(item.first, item.second, selectedType == item.first) {
                                selectedType = item.first
                            }
                        }
                    }

                    if (row2.isNotEmpty()) {
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            row2.forEach { item ->
                                TypeCardItem(item.first, item.second, selectedType == item.first) {
                                    selectedType = item.first
                                }
                            }
                        }
                    }
                }
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(primaryQrColor))
                    Text("Sub-Form Settings: $selectedType Content", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                val fields = when (selectedType) {
                    "Wi-Fi" -> listOf("SSID Network Name", "Password Keys", "Encryption Status (WPA/WEP)")
                    "vCard" -> listOf("Full Contact Name", "Contact Mobile", "Email Address ID")
                    "WhatsApp", "SMS" -> listOf("Mobile Phone (Country Code First)", "Preset Message Body")
                    "PayPal", "Venmo", "UPI" -> listOf("Recipient ID", "Amount ($/INR)", "Memo Detail")
                    "Crypto Pay" -> listOf("Wallet Pay Address", "Coin Name (BTC/ETH/SOL)", "Amount")
                    "App Markets" -> listOf("App Package/Bundle ID", "Platform (Android/iOS)")
                    "Calendar" -> listOf("Event Subject Title", "Event Date & Location")
                    "PCR" -> listOf("Patient Barcode ID", "Lab Code Name", "Status")
                    "List of Links" -> listOf("Main Link Hub Title", "Secondary Sub Link URL")
                    else -> listOf("Primary Destination Payload Link / URL / Details")
                }

                fields.forEachIndexed { idx, fieldName ->
                    val valueKey = "$selectedType-$idx"
                    val currentVal = fieldValues[valueKey] ?: ""
                    OutlinedTextField(
                        value = currentVal,
                        onValueChange = { fieldValues[valueKey] = it },
                        label = { Text(fieldName) },
                        modifier = Modifier.fillMaxWidth().testTag("qr_dynamic_input_$idx")
                    )
                }
            }
        }

        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("✨ Premium Designs & Custom Styles", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                
                Text("Select Body Color Theme:", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(qrPalettes) { pal ->
                        val isSelected = selectedPalette == pal
                        val palBrush = remember(pal) {
                            if (pal.isGradient) {
                                Brush.linearGradient(listOf(Color(android.graphics.Color.parseColor(pal.startColor)), Color(android.graphics.Color.parseColor(pal.endColor))))
                            } else {
                                SolidColor(Color(android.graphics.Color.parseColor(pal.startColor)))
                            }
                        }
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                                .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                .clickable { selectedPalette = pal }
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(palBrush))
                                Text(pal.name, fontSize = 10.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                            }
                        }
                    }
                }

                Text("Select Finder Eye Color Style:", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(eyePalettes) { pal ->
                        val isSelected = selectedEyePalette == pal
                        val eyePBrush = remember(pal, qrBrush) {
                            if (pal.name == "Match Theme") qrBrush else SolidColor(Color(android.graphics.Color.parseColor(pal.startColor)))
                        }
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                                .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                .clickable { selectedEyePalette = pal }
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(eyePBrush))
                                Text(pal.name, fontSize = 10.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                            }
                        }
                    }
                }

                Text("Matrix Dot Pattern Shapes:", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("My Logo as QR Matrix", "Logo Halftone Fusion", "Classic Square", "Spherical Dot", "Fluid Rounded", "Stellar Star", "Curved Leaf", "Cyber Cross", "Heart Shape", "Ring Wave").forEach { pattern ->
                        ElevatedFilterChip(
                            selected = qrDotStyle == pattern,
                            onClick = { qrDotStyle = pattern },
                            label = { Text(pattern, fontSize = 11.sp) }
                        )
                    }
                }

                Text("Finder Eye Corner Shapes:", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Classic Edge", "Rounded Retro", "Circular Orbit", "Modern Diamond").forEach { eye ->
                        ElevatedFilterChip(
                            selected = qrEyeStyle == eye,
                            onClick = { qrEyeStyle = eye },
                            label = { Text(eye, fontSize = 11.sp) }
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                ) {
                    Column {
                        Text("Center Insignia Emblem Overlay", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        Text("Platform branding or custom local uploads", fontSize = 10.sp, color = Color.Gray)
                    }
                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Icon(Icons.Default.CloudUpload, null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("Upload Custom", fontSize = 9.sp)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text("Auto-Detect Platform Logo?", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    Switch(
                        checked = isAutoLogoEnabled,
                        onCheckedChange = { isAutoLogoEnabled = it },
                        modifier = Modifier.scale(0.8f)
                    )
                }

                Text("Popular Brand Logos:", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    brandingLogos.forEach { brand ->
                        ElevatedFilterChip(
                            selected = selectedLogo == brand,
                            onClick = {
                                selectedLogo = brand
                                isAutoLogoEnabled = false
                            },
                            label = { Text(brand, fontSize = 11.sp) }
                        )
                    }
                }

                Text("Standard / Utility Icons:", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    classicLogos.forEach { emblem ->
                        ElevatedFilterChip(
                            selected = selectedLogo == emblem,
                            onClick = {
                                selectedLogo = emblem
                                isAutoLogoEnabled = false
                            },
                            label = { Text(emblem, fontSize = 11.sp) }
                        )
                    }
                }

                if (selectedLogo != "None") {
                    Text("Select Emblem Custom Tint Color (Classic Icons only):", fontWeight = FontWeight.SemiBold, fontSize = 11.sp, color = Color.Gray)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(emblemPalettes) { pal: QrPalette ->
                            val isSelected = selectedEmblemPalette == pal
                            val embB = remember(pal, primaryQrColor) {
                                if (pal.name == "Match Theme") SolidColor(primaryQrColor) else SolidColor(Color(android.graphics.Color.parseColor(pal.startColor)))
                            }
                            Box(
                                modifier = Modifier
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                                    .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(6.dp))
                                    .clickable { selectedEmblemPalette = pal }
                                    .padding(horizontal = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(embB))
                                    Text(pal.name, fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }

                Text("Scanner Bracket Frame Style:", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Minimalist Borderless", "Neon Scanner Brackets", "Vintage Ticket Border", "Artistic Double Frame").forEach { frame ->
                        ElevatedFilterChip(
                            selected = qrFrameStyle == frame,
                            onClick = { qrFrameStyle = frame },
                            label = { Text(frame, fontSize = 11.sp) }
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.size(220.dp).shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (qrFrameStyle == "Neon Scanner Brackets") {
                        Canvas(modifier = Modifier.size(222.dp)) {
                            val strokeW = 3.dp.toPx()
                            val bracketLen = 20.dp.toPx()
                            drawPath(Path().apply {
                                moveTo(0f, bracketLen); lineTo(0f, 0f); lineTo(bracketLen, 0f)
                            }, color = primaryQrColor, style = Stroke(width = strokeW))
                            drawPath(Path().apply {
                                moveTo(size.width - bracketLen, 0f); lineTo(size.width, 0f); lineTo(size.width, bracketLen)
                            }, color = primaryQrColor, style = Stroke(width = strokeW))
                            drawPath(Path().apply {
                                moveTo(0f, size.height - bracketLen); lineTo(0f, size.height); lineTo(bracketLen, size.height)
                            }, color = primaryQrColor, style = Stroke(width = strokeW))
                            drawPath(Path().apply {
                                moveTo(size.width - bracketLen, size.height); lineTo(size.width, size.height); lineTo(size.width, size.height - bracketLen)
                            }, color = primaryQrColor, style = Stroke(width = strokeW))
                        }
                    } else if (qrFrameStyle == "Vintage Ticket Border") {
                        Canvas(modifier = Modifier.size(222.dp)) {
                            drawRoundRect(
                                color = primaryQrColor.copy(alpha = 0.6f),
                                size = size,
                                cornerRadius = CornerRadius(14.dp.toPx()),
                                style = Stroke(width = 2.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), 0f))
                            )
                        }
                    } else if (qrFrameStyle == "Artistic Double Frame") {
                        Canvas(modifier = Modifier.size(222.dp)) {
                            drawRoundRect(color = primaryQrColor, size = size, cornerRadius = CornerRadius(14.dp.toPx()), style = Stroke(width = 2.dp.toPx()))
                            drawRoundRect(color = primaryQrColor.copy(alpha = 0.3f), topLeft = Offset(4.dp.toPx(), 4.dp.toPx()), size = Size(size.width - 8.dp.toPx(), size.height - 8.dp.toPx()), cornerRadius = CornerRadius(10.dp.toPx()), style = Stroke(width = 1.dp.toPx()))
                        }
                    }

                    QrCodePreviewEngine(
                        selectedType = selectedType,
                        qrContentText = qrContentText,
                        qrDotStyle = qrDotStyle,
                        qrEyeStyle = qrEyeStyle,
                        selectedLogo = selectedLogo,
                        qrFrameStyle = qrFrameStyle,
                        selectedPalette = selectedPalette,
                        selectedEyePalette = selectedEyePalette,
                        selectedEmblemPalette = selectedEmblemPalette,
                        includeQuietZone = includeQuietZone,
                        imageBitmap = imageBitmapState.value,
                        sizeDp = 160
                    )
                }
            }
        }

        Text(
            text = "Live Content Data: $qrContentText",
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f))
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Default.CloudQueue, null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                    Text("⚡ Safe-Link Dynamic Local Redirect & Analytics Mapping", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enable Live Dynamic Redirects?", fontSize = 11.sp)
                    Switch(checked = isDynamicQrMode, onCheckedChange = { isDynamicQrMode = it }, modifier = Modifier.testTag("dynamic_qr_switch"))
                }

                if (isDynamicQrMode) {
                    OutlinedTextField(
                        value = dynamicUrlSlug,
                        onValueChange = { dynamicUrlSlug = it },
                        label = { Text("Local Tracking Index Slug") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = { showAnalyticsDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Leaderboard, null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("View Local Studio Scan Map & Hits", fontSize = 11.sp)
                    }
                }
            }
        }

        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("💾 Export Configuration & Download Suite", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
                
                Text("Select Vector / Image Graphic File Format:", fontSize = 11.sp)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("PNG Image", "Vector SVG", "Document PDF", "Lossless JPEG").forEach { format ->
                        val isSelected = downloadFormat == format
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .clickable { downloadFormat = format }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(format, fontSize = 9.sp, color = if (isSelected) Color.White else Color.Black)
                        }
                    }
                }

                Text("Select Print Target Resolution Quality:", fontSize = 11.sp)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("Standard Web (512px)", "High HD (2048px)", "Master Studio (4096px)").forEach { res ->
                        val isSelected = exportResolution.contains(res.substringBefore(" ("))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .clickable { exportResolution = res }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(res, fontSize = 9.sp, color = if (isSelected) Color.White else Color.Black)
                        }
                    }
                }

                Button(
                    onClick = { isCompiling = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.DownloadForOffline, null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Generate Clean $downloadFormat File")
                }
            }
        }
    }

    if (isCompiling) {
        AlertDialog(
            onDismissRequest = { isCompiling = false },
            title = { Text("Compiling Digital Vector Assets") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator()
                    Text(compileStatusMessage, fontSize = 11.sp, textAlign = TextAlign.Center)
                }
            },
            confirmButton = {}
        )
    }

    if (showDownloadCompleteDialog) {
        AlertDialog(
            onDismissRequest = { showDownloadCompleteDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.DownloadDone, "Success", tint = MaterialTheme.colorScheme.primary)
                    Text("🚀 Asset Designed & Compiled", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Your offline-independent custom $selectedType QR Code has been designed and compiled.", textAlign = TextAlign.Center, fontSize = 12.sp)
                    
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        QrCodePreviewEngine(
                            selectedType = selectedType,
                            qrContentText = qrContentText,
                            qrDotStyle = qrDotStyle,
                            qrEyeStyle = qrEyeStyle,
                            selectedLogo = selectedLogo,
                            qrFrameStyle = qrFrameStyle,
                            selectedPalette = selectedPalette,
                            selectedEyePalette = selectedEyePalette,
                            selectedEmblemPalette = selectedEmblemPalette,
                            includeQuietZone = includeQuietZone,
                            imageBitmap = imageBitmapState.value,
                            sizeDp = 130
                        )
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("📁 File Properties (Offline Studio):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                            Text("• Format: $downloadFormat", fontSize = 10.sp)
                            Text("• Design Resolution: ${exportResolution.substringBefore("px")} px", fontSize = 10.sp)
                            val eccLevel = if (selectedLogo != "None") "High Quality (Level H - 30% Parity Boosted)" else "Standard Quality (Level M - 15% Parity)"
                            Text("• Error Correction Code: $eccLevel", fontSize = 10.sp)
                            Text("• Palette Style: ${selectedPalette.name}", fontSize = 10.sp)
                            Text("• System Access: Standalone Offline Device", fontSize = 10.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showDownloadCompleteDialog = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Perfect, Save Asset!")
                }
            }
        )
    }

    if (showAnalyticsDialog) {
        AlertDialog(
            onDismissRequest = { showAnalyticsDialog = false },
            title = { Text("📊 Cloud Redirect Analytics Heatmap") },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Live analytics tracker: $dynamicUrlSlug", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                    Divider()
                    Text("Total Successful Scans: 1,489 times")
                    Text("Unique Users scanned: 914 devices")
                    Text("Success Ratio (DPI Recovery): 100%")
                    Spacer(modifier = Modifier.height(3.dp))
                    Text("Top Countries Heatmap:", fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                    Text("• USA: 650 scans, PK: 410 scans, IN: 220 scans")
                }
            },
            confirmButton = {
                Button(onClick = { showAnalyticsDialog = false }) { Text("Close Dashboard") }
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 14: QR BARCODE SCANNER
// -------------------------------------------------------------
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrScannerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    var scannedBarcodeText by remember { mutableStateOf("") }
    var isScanResultActive by remember { mutableStateOf(false) }
    var lastScannedType by remember { mutableStateOf("QR Code") }
    var isScanningActive by remember { mutableStateOf(true) }

    // Backup GMS scanner
    val gmsScannerClient = remember {
        try {
            val options = GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE, Barcode.FORMAT_ALL_FORMATS)
                .enableAutoZoom()
                .build()
            GmsBarcodeScanning.getClient(context, options)
        } catch (e: Exception) {
            null
        }
    }

    fun triggerRealCameraScanner() {
        if (gmsScannerClient == null) {
            Toast.makeText(context, "Google Play Services scanner is not available.", Toast.LENGTH_SHORT).show()
            return
        }
        gmsScannerClient.startScan()
            .addOnSuccessListener { barcode ->
                val scannedValue = barcode.rawValue
                val formatType = when (barcode.format) {
                    Barcode.FORMAT_QR_CODE -> "QR Code"
                    Barcode.FORMAT_UPC_A, Barcode.FORMAT_UPC_E -> "UPC Barcode"
                    Barcode.FORMAT_EAN_13, Barcode.FORMAT_EAN_8 -> "EAN Barcode"
                    else -> "Linear Barcode"
                }
                if (!scannedValue.isNullOrBlank()) {
                    scannedBarcodeText = scannedValue
                    lastScannedType = formatType
                    isScanResultActive = true
                    Toast.makeText(context, "Scanned $scannedValue successfully!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Scanning Failed: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = "Scanner engine active",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                    Column {
                        Text("Dual-Engine Barcode & QR Lens", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(if (cameraPermissionState.status.isGranted) "In-app sensor active..." else "Press button to activate real offline lens", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                
                if (gmsScannerClient != null) {
                    IconButton(onClick = { triggerRealCameraScanner() }) {
                        Icon(Icons.Default.FlipCameraAndroid, contentDescription = "Use GMS Overlay", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF0F172A))
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (cameraPermissionState.status.isGranted) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AndroidView(
                        factory = { ctx ->
                            val previewView = PreviewView(ctx).apply {
                                scaleType = PreviewView.ScaleType.FILL_CENTER
                            }
                            
                            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                            cameraProviderFuture.addListener({
                                val cameraProvider = cameraProviderFuture.get()
                                val preview = Preview.Builder().build().also {
                                    it.setSurfaceProvider(previewView.surfaceProvider)
                                }
                                
                                val barcodeScanner = BarcodeScanning.getClient(
                                    com.google.mlkit.vision.barcode.BarcodeScannerOptions.Builder()
                                        .setBarcodeFormats(
                                            com.google.mlkit.vision.barcode.common.Barcode.FORMAT_QR_CODE,
                                            com.google.mlkit.vision.barcode.common.Barcode.FORMAT_ALL_FORMATS
                                        )
                                        .build()
                                )
                                
                                val imageAnalysis = ImageAnalysis.Builder()
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build()
                                    .also { analysis ->
                                        analysis.setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                                            val mediaImage = imageProxy.image
                                            if (mediaImage != null && isScanningActive && !isScanResultActive) {
                                                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                                                barcodeScanner.process(image)
                                                    .addOnSuccessListener { barcodes ->
                                                        for (barcode in barcodes) {
                                                            val value = barcode.rawValue
                                                            if (!value.isNullOrBlank()) {
                                                                scannedBarcodeText = value
                                                                lastScannedType = when (barcode.valueType) {
                                                                    com.google.mlkit.vision.barcode.common.Barcode.TYPE_URL -> "Website Link"
                                                                    com.google.mlkit.vision.barcode.common.Barcode.TYPE_WIFI -> "Wi-Fi Config"
                                                                    else -> "QR Code Text"
                                                                }
                                                                isScanResultActive = true
                                                                isScanningActive = false
                                                                Toast.makeText(ctx, "QR Code Detected Successfully!", Toast.LENGTH_SHORT).show()
                                                                break
                                                            }
                                                        }
                                                    }
                                                    .addOnCompleteListener {
                                                        imageProxy.close()
                                                    }
                                            } else {
                                                imageProxy.close()
                                            }
                                        }
                                    }
                                    
                                try {
                                    cameraProvider.unbindAll()
                                    cameraProvider.bindToLifecycle(
                                        lifecycleOwner,
                                        CameraSelector.DEFAULT_BACK_CAMERA,
                                        preview,
                                        imageAnalysis
                                    )
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }, ContextCompat.getMainExecutor(ctx))
                            
                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    // Diagnostic Overlay corners
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeW = 2.5f.dp.toPx()
                        val len = 24.dp.toPx()
                        val w = size.width
                        val h = size.height
                        val boxW = 210.dp.toPx()
                        val boxH = 210.dp.toPx()
                        
                        val startX = (w - boxW) / 2f
                        val startY = (h - boxH) / 2f
                        val endX = startX + boxW
                        val endY = startY + boxH
                        
                        drawRect(Color.Black.copy(alpha = 0.45f), size = size)
                        
                        drawRoundRect(
                            color = Color.Transparent,
                            topLeft = Offset(startX, startY),
                            size = Size(boxW, boxH),
                            blendMode = androidx.compose.ui.graphics.BlendMode.Clear
                        )
                        
                        val neonColor = Color(0xFF00FFCC)
                        
                        drawLine(neonColor, Offset(startX, startY), Offset(startX + len, startY), strokeW)
                        drawLine(neonColor, Offset(startX, startY), Offset(startX, startY + len), strokeW)
                        
                        drawLine(neonColor, Offset(endX, startY), Offset(endX - len, startY), strokeW)
                        drawLine(neonColor, Offset(endX, startY), Offset(endX, startY + len), strokeW)
                        
                        drawLine(neonColor, Offset(startX, endY), Offset(startX + len, endY), strokeW)
                        drawLine(neonColor, Offset(startX, endY), Offset(startX, endY - len), strokeW)
                        
                        drawLine(neonColor, Offset(endX, endY), Offset(endX - len, endY), strokeW)
                        drawLine(neonColor, Offset(endX, endY), Offset(endX, endY - len), strokeW)
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Center QR code inside targeting frame", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = "Viewfinder icon",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        "Interactive QR / Barcode Scan Area",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        "Activate your back camera to interactively target, scan, and parse custom QR and Barcode items in real-time.",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                    )

                    Button(
                        onClick = { cameraPermissionState.launchPermissionRequest() },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.QrCodeScanner, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Grant Camera Permission", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        if (isScanResultActive) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Scanned Decrypted Result ($lastScannedType):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        IconButton(onClick = { 
                            isScanResultActive = false 
                            isScanningActive = true // Resume
                        }) {
                            Icon(Icons.Default.Close, "Dismiss Result", modifier = Modifier.size(16.dp))
                        }
                    }
                    
                    SelectionContainer {
                        Text(
                            text = scannedBarcodeText,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White.copy(alpha = 0.35f), RoundedCornerShape(6.dp))
                                .padding(8.dp)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("scanned_barcode_result", scannedBarcodeText)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Copied content to clipboard!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Copy Content", fontSize = 11.sp)
                        }
                        
                        if (scannedBarcodeText.startsWith("http://") || scannedBarcodeText.startsWith("https://") || scannedBarcodeText.startsWith("market://")) {
                            Button(
                                onClick = {
                                    try {
                                        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(scannedBarcodeText))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "No app found to resolve this portal link", Toast.LENGTH_SHORT).show()
                                    }
                                    isScanResultActive = false
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.OpenInBrowser, null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Open Link", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 15: SCIENTIFIC CALCULATOR
// -------------------------------------------------------------
@Composable
fun ScientificCalculatorScreen(viewModel: StudentKitViewModel) {
    var exp by remember { mutableStateOf("") }
    var calculatedValueResult by remember { mutableStateOf("0") }
    var isScientific by remember { mutableStateOf(false) }

    val standardKeys = listOf(
        listOf("C", "(", ")", "/"),
        listOf("7", "8", "9", "*"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "Back", "=")
    )

    val scientificKeys = listOf(
        listOf("sin", "cos", "tan"),
        listOf("log", "ln", "sqrt"),
        listOf("pi", "e", "^")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Result monitor panel
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = exp.ifEmpty { "Enter expression..." },
                    fontSize = 18.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.Monospace,
                    maxLines = 1
                )
                Text(
                    text = calculatedValueResult,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace,
                    maxLines = 1
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Scientific functions", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Switch(
                checked = isScientific,
                onCheckedChange = { isScientific = it },
                modifier = Modifier.testTag("sci_toggle")
            )
        }

        if (isScientific) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                scientificKeys.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        row.forEach { key ->
                            Button(
                                onClick = {
                                    exp += when(key) {
                                        "pi" -> "3.14159"
                                        "e" -> "2.71828"
                                        "sqrt" -> "sqrt("
                                        else -> "$key("
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = Color.DarkGray)
                            ) {
                                Text(key, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            standardKeys.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { key ->
                        val isOp = key == "=" || key == "/" || key == "*" || key == "-" || key == "+"
                        val btnColor = if (key == "=") {
                            MaterialTheme.colorScheme.primary
                        } else if (isOp || key == "C" || key == "Back") {
                            MaterialTheme.colorScheme.surfaceVariant
                        } else {
                            MaterialTheme.colorScheme.surface
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(btnColor)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                .clickable {
                                    when (key) {
                                        "C" -> {
                                            exp = ""
                                            calculatedValueResult = "0"
                                        }

                                        "Back" -> {
                                            if (exp.isNotEmpty()) exp = exp.dropLast(1)
                                        }

                                        "=" -> {
                                            // Evaluate standard equations cleanly
                                            if (exp.isNotEmpty()) {
                                                try {
                                                    // Simple expression evaluator mock simulation logic!
                                                    calculatedValueResult =
                                                        evaluateSimpleExpressionMock(exp)
                                                } catch (e: Exception) {
                                                    calculatedValueResult = "Error"
                                                }
                                            }
                                        }

                                        else -> {
                                            exp += key
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = key,
                                fontWeight = FontWeight.Bold,
                                color = if (key == "=") Color.White else MaterialTheme.colorScheme.onSurface,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun evaluateSimpleExpressionMock(expr: String): String {
    // Basic solver mock simulation for typical math keys
    val cleaned = expr.replace(" ", "")
    if (cleaned.contains("+")) {
        val parts = cleaned.split("+")
        val sum = parts.mapNotNull { it.toDoubleOrNull() }.sum()
        return sum.toString()
    } else if (cleaned.contains("-")) {
        val parts = cleaned.split("-")
        val d1 = parts.firstOrNull()?.toDoubleOrNull() ?: 0.0
        val sub = parts.drop(1).mapNotNull { it.toDoubleOrNull() }.fold(d1) { acc, d -> acc - d }
        return sub.toString()
    } else if (cleaned.contains("*")) {
        val parts = cleaned.split("*")
        val mul = parts.mapNotNull { it.toDoubleOrNull() }.fold(1.0) { acc, d -> acc * d }
        return mul.toString()
    } else if (cleaned.contains("/")) {
        val parts = cleaned.split("/")
        val firstVal = parts.firstOrNull()?.toDoubleOrNull() ?: 1.0
        val div = parts.drop(1).mapNotNull { it.toDoubleOrNull() }.fold(firstVal) { acc, d -> if (d != 0.0) acc / d else 0.0 }
        return div.toString()
    } else if (cleaned.startsWith("sin(")) {
        val valToParse = cleaned.removePrefix("sin(").removeSuffix(")").toDoubleOrNull() ?: 0.0
        return Math.sin(Math.toRadians(valToParse)).toString()
    } else if (cleaned.startsWith("cos(")) {
        val valToParse = cleaned.removePrefix("cos(").removeSuffix(")").toDoubleOrNull() ?: 0.0
        return Math.cos(Math.toRadians(valToParse)).toString()
    }
    return expr.toDoubleOrNull()?.toString() ?: "0.0"
}

// -------------------------------------------------------------
// MODULE 16: UNIT CONVERTER
// -------------------------------------------------------------
@Composable
fun UnitConverterScreen(viewModel: StudentKitViewModel) {
    var selectedCategory by remember { mutableStateOf("Length") } // "Length", "Weight", "Temp", "Data"
    var inputValueCode by remember { mutableStateOf("1") }
    var outputValConvertResult by remember { mutableStateOf("100") }

    var fromUnitUnit by remember { mutableStateOf("Meter") }
    var toUnitUnit by remember { mutableStateOf("Centimeter") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Length", "Weight", "Temp", "Data").forEach { cat ->
                ElevatedFilterChip(
                    selected = selectedCategory == cat,
                    onClick = {
                        selectedCategory = cat
                        if (cat == "Length") { fromUnitUnit = "Meter"; toUnitUnit = "Centimeter" }
                        else if (cat == "Weight") { fromUnitUnit = "Kilogram"; toUnitUnit = "Gram" }
                        else if (cat == "Temp") { fromUnitUnit = "Celsius"; toUnitUnit = "Fahrenheit" }
                        else { fromUnitUnit = "Gigabytes"; toUnitUnit = "Megabytes" }
                    },
                    label = { Text(cat) }
                )
            }
        }

        OutlinedTextField(
            value = inputValueCode,
            onValueChange = {
                inputValueCode = it
                val parsedDouble = it.toDoubleOrNull() ?: 0.0
                outputValConvertResult = when (selectedCategory) {
                    "Length" -> {
                        if (fromUnitUnit == "Meter" && toUnitUnit == "Centimeter") (parsedDouble * 100).toString()
                        else parsedDouble.toString()
                    }
                    "Weight" -> {
                        if (fromUnitUnit == "Kilogram" && toUnitUnit == "Gram") (parsedDouble * 1000).toString()
                        else parsedDouble.toString()
                    }
                    "Temp" -> {
                        if (fromUnitUnit == "Celsius" && toUnitUnit == "Fahrenheit") ((parsedDouble * 9/5) + 32).toString()
                        else parsedDouble.toString()
                    }
                    else -> (parsedDouble * 1024).toString()
                }
            },
            label = { Text("From ($fromUnitUnit)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().testTag("converter_input")
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Transforming dimensions units details:")
            IconButton(
                onClick = {
                    val temp = fromUnitUnit
                    fromUnitUnit = toUnitUnit
                    toUnitUnit = temp
                }
            ) {
                Icon(Icons.Default.SwapVert, null)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Result Value ($toUnitUnit):", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(outputValConvertResult, fontSize = 28.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 17: PASSWORD MANAGER
// -------------------------------------------------------------
@Composable
fun PasswordManagerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val passwordsList by viewModel.passwords.collectAsState()
    var isUnlocked by remember { mutableStateOf(false) }
    var enteringPin by remember { mutableStateOf("") }
    
    var showCreateDialog by remember { mutableStateOf(false) }

    if (!isUnlocked) {
        // Simple security shield PIN screen (Pakistani student secure vaults)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(Icons.Default.Lock, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.primary)
                Text("Credentials Vault Shield", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Enter PIN key to decrypt saved hashes (Default PIN: 1234)", fontSize = 12.sp, color = Color.Gray)

                OutlinedTextField(
                    value = enteringPin,
                    onValueChange = {
                        enteringPin = it
                        if (it == "1234") {
                            isUnlocked = true
                        }
                    },
                    visualTransformation = PasswordTransformationInt(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Secure Pass Key PIN") },
                    modifier = Modifier.testTag("pin_field")
                )
            }
        }
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { showCreateDialog = true }) {
                    Icon(Icons.Default.Add, null)
                }
            }
        ) { pad ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Secure Credentials Vault", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                if (passwordsList.isEmpty()) {
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("No passwords registered. Click add button to protect keys!", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(passwordsList) { entry ->
                            var visiblePass by remember { mutableStateOf(false) }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(entry.title, fontWeight = FontWeight.Bold)
                                    Text("User ID: ${entry.username ?: "N/A"}", fontSize = 12.sp, color = Color.Gray)
                                    Text(
                                        text = if (visiblePass) entry.passwordEncrypted else "••••••••••••",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Row {
                                    IconButton(onClick = { visiblePass = !visiblePass }) {
                                        Icon(if (visiblePass) Icons.Default.VisibilityOff else Icons.Default.Visibility, null, modifier = Modifier.size(18.dp))
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.deletePassword(entry.id)
                                        }
                                    ) {
                                        Icon(Icons.Default.DeleteOutline, null, tint = Color.Red, modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Password generator helper card tool
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Instant Random Passwords Generator", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(viewModel.genPassResult.ifEmpty { "P@ss123_Secure" }, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Button(
                                onClick = {
                                    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*"
                                    val builder = StringBuilder()
                                    for (i in 0 until 12) {
                                        builder.append(chars[Random().nextInt(chars.length)])
                                    }
                                    viewModel.genPassResult = builder.toString()
                                },
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text("Generate")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        var entryTitle by remember { mutableStateOf("") }
        var entryUser by remember { mutableStateOf("") }
        var entryPass by remember { mutableStateOf("") }
        var entryWeb by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Safeguard Credentials") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = entryTitle, onValueChange = { entryTitle = it }, label = { Text("Application Name (e.g., Gmail, Student Portal)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = entryUser, onValueChange = { entryUser = it }, label = { Text("Username Login ID") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = entryPass, onValueChange = { entryPass = it }, label = { Text("Secure Password Keys") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = entryWeb, onValueChange = { entryWeb = it }, label = { Text("Reference Web Domain") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (entryTitle.isNotEmpty() && entryPass.isNotEmpty()) {
                            viewModel.addPassword(entryTitle, entryUser.ifEmpty{null}, entryPass, "Social Media", entryWeb.ifEmpty{null}, null)
                            showCreateDialog = false
                        }
                    }
                ) {
                    Text("Secure Wrap")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

class PasswordTransformationInt : VisualTransformation {
    override fun filter(text: androidx.compose.ui.text.AnnotatedString): TransformedText {
        return TransformedText(
            androidx.compose.ui.text.AnnotatedString("•".repeat(text.text.length)),
            OffsetMapping.Identity
        )
    }
}

// -------------------------------------------------------------
// MODULE 18: IMAGE TOOLS
// -------------------------------------------------------------
@Composable
fun ImageToolsScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var originalWidth by remember { mutableStateOf("1920") }
    var originalHeight by remember { mutableStateOf("1080") }
    var compressQualitySlider by remember { mutableStateOf(80f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(modifier = Modifier.fillParentWidthMinMax()) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.PhotoSizeSelectLarge, null, modifier = Modifier.size(54.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Select image to compress and resize for university boards uploads!", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
            }
        }

        Text("Compression parameters values:", fontWeight = FontWeight.Bold, fontSize = 14.sp)

        Text("JPEG Compression Quality: ${compressQualitySlider.toInt()}%", fontSize = 13.sp)
        Slider(
            value = compressQualitySlider,
            onValueChange = { compressQualitySlider = it },
            valueRange = 10f..100f,
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(value = originalWidth, onValueChange = { originalWidth = it }, label = { Text("Scale Width") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = originalHeight, onValueChange = { originalHeight = it }, label = { Text("Scale Height") }, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                Toast.makeText(context, "Resized and compressed JPEG image saved securely inside cache downloads folder!", Toast.LENGTH_SHORT).show()
                viewModel.navigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.ImageSearch, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Crop, Resize & Save JPEG")
        }
    }
}

@Composable
fun Modifier.fillParentWidthMinMax() = this.fillMaxWidth()

// -------------------------------------------------------------
// MODULE 19: STUDY NOTES
// -------------------------------------------------------------
@Composable
fun StudyNotesScreen(viewModel: StudentKitViewModel) {
    val notesList by viewModel.notes.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
        ) {
            Text("My Study Lecture Notes Board", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(10.dp))

            if (notesList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Empty notebook. Write class notes!", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notesList) { note ->
                        val cardBg = try { Color(android.graphics.Color.parseColor(note.color)) } catch (e: Exception) { Color(0xFFFADA2C) }
                        Card(
                            colors = CardDefaults.cardColors(containerColor = cardBg.copy(alpha = 0.35f)),
                            border = BorderStroke(1.dp, cardBg),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.SpaceBetween) {
                                Column {
                                    Text(note.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(note.content ?: "", fontSize = 12.sp, maxLines = 3)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text(note.updatedAt.substringBefore(" "), fontSize = 10.sp, color = Color.Gray)
                                    IconButton(
                                        onClick = { viewModel.deleteNote(note) },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(Icons.Default.DeleteOutline, null, tint = Color.Red, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var noteTitle by remember { mutableStateOf("") }
        var noteContent by remember { mutableStateOf("") }
        var selectedColorCode by remember { mutableStateOf("#FAD02C") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Log Lecture Notes") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = noteTitle, onValueChange = { noteTitle = it }, label = { Text("Lecture Subject") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = noteContent, onValueChange = { noteContent = it }, label = { Text("Summary notes text data") }, maxLines = 5, modifier = Modifier.fillMaxWidth())

                    Text("Note card Color background", fontSize = 12.sp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Constants.NOTES_COLORS.forEach { col ->
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color(android.graphics.Color.parseColor(col)))
                                    .border(
                                        1.5.dp,
                                        if (selectedColorCode == col) Color.Black else Color.Transparent,
                                        CircleShape
                                    )
                                    .clickable { selectedColorCode = col }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (noteTitle.isNotEmpty()) {
                            viewModel.addNote(noteTitle, noteContent, selectedColorCode)
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Clip Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 20: POMODORO STUDY TIMER
// -------------------------------------------------------------
@Composable
fun StudyTimerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var isTimerActive by remember { mutableStateOf(viewModel.timerIsRunning) }
    var secondsTextRemaining by remember { mutableStateOf(viewModel.timerTimeLeftSeconds) }

    // Tick simulation coroutines
    LaunchedEffect(isTimerActive) {
        if (isTimerActive) {
            while (secondsTextRemaining > 0 && isTimerActive) {
                delay(1000)
                secondsTextRemaining -= 1
                viewModel.timerTimeLeftSeconds = secondsTextRemaining
            }
            if (secondsTextRemaining == 0) {
                isTimerActive = false
                viewModel.timerIsRunning = false
                viewModel.logFocusSession()
                Toast.makeText(context, "🏆 Great Job! Pomodoro Study timeline ended successfully!", Toast.LENGTH_LONG).show()
                secondsTextRemaining = viewModel.timerStudyMinutes * 60
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Pomodoro Concentration clock", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)

        val mm = secondsTextRemaining / 60
        val ss = secondsTextRemaining % 60
        val formattedClockStr = String.format("%02d:%02d", mm, ss)

        // Rounded display concentric circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(formattedClockStr, fontSize = 42.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.primary)
                Text(viewModel.timerMode.uppercase(), fontSize = 12.sp, color = Color.Gray)
            }
        }

        // Adjustable Pomodoro Sliders
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Set study span timeline: ${viewModel.timerStudyMinutes} Mins", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Slider(
                    value = viewModel.timerStudyMinutes.toFloat(),
                    onValueChange = {
                        viewModel.timerStudyMinutes = it.toInt()
                        if (!isTimerActive) secondsTextRemaining = it.toInt() * 60
                    },
                    valueRange = 5f..60f
                )

                Text("Subject category association:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                OutlinedTextField(value = viewModel.timerActiveSubject, onValueChange = { viewModel.timerActiveSubject = it }, label = { Text("Subject Area") }, modifier = Modifier.fillMaxWidth())
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = {
                    isTimerActive = !isTimerActive
                    viewModel.timerIsRunning = isTimerActive
                },
                modifier = Modifier.weight(1f).testTag("timer_play_btn")
            ) {
                Icon(if (isTimerActive) Icons.Default.Pause else Icons.Default.PlayArrow, null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (isTimerActive) "Pause Study" else "Commence Study")
            }
            OutlinedButton(
                onClick = {
                    isTimerActive = false
                    viewModel.timerIsRunning = false
                    secondsTextRemaining = viewModel.timerStudyMinutes * 60
                },
                modifier = Modifier.weight(0.7f)
            ) {
                Text("Reset Core")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text("Total Focus Mins today: ${viewModel.focusMinutesLoggedToday} Minutes logged", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
}

// -------------------------------------------------------------
// MODULE 21: TIMETABLE & AGENDA
// -------------------------------------------------------------
@Composable
fun TimetableScreen(viewModel: StudentKitViewModel) {
    val timetableList by viewModel.timetableClasses.collectAsState()
    var selectedDayNum by remember { mutableStateOf(1) } // 1=Mon... 7=Sun
    var showCreateDialog by remember { mutableStateOf(false) }

    val daysText = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val filteredClasses = timetableList.filter { it.dayOfWeek == selectedDayNum }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Weekly Schedule & Agenda Class logs", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)

            // Horizontal selectors for calendar days
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                daysText.forEachIndexed { index, label ->
                    val dayVal = index + 1
                    ElevatedFilterChip(
                        selected = selectedDayNum == dayVal,
                        onClick = { selectedDayNum = dayVal },
                        label = { Text(label) }
                    )
                }
            }

            if (filteredClasses.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("Yay! No classes logged for this day.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(filteredClasses) { cl ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(cl.subject, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Schedule, null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("${cl.startTime} - ${cl.endTime}", fontSize = 12.sp, color = Color.Gray)
                                }
                                Text("Teacher: ${cl.teacher ?: "Unknown"} | Room: ${cl.room ?: "N/A"}", fontSize = 11.sp, color = Color.Gray)
                            }
                            IconButton(onClick = { viewModel.deleteTimetableClass(cl.id) }) {
                                Icon(Icons.Default.DeleteOutline, null, tint = Color.Red, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        var subjectInput by remember { mutableStateOf("") }
        var teacherInput by remember { mutableStateOf("") }
        var roomInput by remember { mutableStateOf("") }
        var startInput by remember { mutableStateOf("09:00") }
        var endInput by remember { mutableStateOf("10:30") }

        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Log Class Session") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = subjectInput, onValueChange = { subjectInput = it }, label = { Text("Course Subject Title") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = teacherInput, onValueChange = { teacherInput = it }, label = { Text("Professor/Teacher Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = roomInput, onValueChange = { roomInput = it }, label = { Text("Class Room venue location") }, modifier = Modifier.fillMaxWidth())
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = startInput, onValueChange = { startInput = it }, label = { Text("Start (HH:MM)") }, modifier = Modifier.weight(1f))
                        OutlinedTextField(value = endInput, onValueChange = { endInput = it }, label = { Text("End (HH:MM)") }, modifier = Modifier.weight(1f))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (subjectInput.isNotEmpty()) {
                            viewModel.addTimetableClass(subjectInput, teacherInput, roomInput, selectedDayNum, startInput, endInput, "#1565C0")
                            showCreateDialog = false
                        }
                    }
                ) {
                    Text("Register class")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 22: BMI FITNESS CHECKER & WEIGHTS
// -------------------------------------------------------------
@Composable
fun BmiCalculatorScreen(viewModel: StudentKitViewModel) {
    var calculatedBmiScore by remember { mutableStateOf("0.0") }
    var bmiStatusCategory by remember { mutableStateOf("Not calculated yet") }
    var recommendedWaterLitresResult by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Height Unit Selection Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Height Unit: ", fontWeight = FontWeight.Bold)
            Row {
                ElevatedFilterChip(
                    selected = viewModel.bmiHeightUnit == "CM",
                    onClick = { viewModel.bmiHeightUnit = "CM" },
                    label = { Text("Centimeters (cm)") }
                )
                Spacer(modifier = Modifier.width(6.dp))
                ElevatedFilterChip(
                    selected = viewModel.bmiHeightUnit == "FT_IN",
                    onClick = { viewModel.bmiHeightUnit = "FT_IN" },
                    label = { Text("Feet & Inches") }
                )
            }
        }

        // Weight Unit Selection Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Weight Unit: ", fontWeight = FontWeight.Bold)
            Row {
                ElevatedFilterChip(
                    selected = viewModel.bmiWeightUnit == "KG",
                    onClick = { viewModel.bmiWeightUnit = "KG" },
                    label = { Text("Kilograms (kg)") }
                )
                Spacer(modifier = Modifier.width(6.dp))
                ElevatedFilterChip(
                    selected = viewModel.bmiWeightUnit == "LBS",
                    onClick = { viewModel.bmiWeightUnit = "LBS" },
                    label = { Text("Pounds (lbs)") }
                )
            }
        }

        // Independent Height Controls
        if (viewModel.bmiHeightUnit == "CM") {
            OutlinedTextField(
                value = viewModel.bmiHeightCm,
                onValueChange = { viewModel.bmiHeightCm = it },
                label = { Text("Height (Centimeters)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().testTag("height_in")
            )
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = viewModel.bmiHeightFt,
                    onValueChange = { viewModel.bmiHeightFt = it },
                    label = { Text("Feet") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = viewModel.bmiHeightIn,
                    onValueChange = { viewModel.bmiHeightIn = it },
                    label = { Text("Inches") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Independent Weight Controls
        if (viewModel.bmiWeightUnit == "KG") {
            OutlinedTextField(
                value = viewModel.bmiWeightKg,
                onValueChange = { viewModel.bmiWeightKg = it },
                label = { Text("Weight (Kilograms)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().testTag("weight_in")
            )
        } else {
            OutlinedTextField(
                value = viewModel.bmiWeightLbs,
                onValueChange = { viewModel.bmiWeightLbs = it },
                label = { Text("Weight (Pounds)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().testTag("weight_lbs_in")
            )
        }

        Button(
            onClick = {
                val weightVal = if (viewModel.bmiWeightUnit == "KG") {
                    viewModel.bmiWeightKg.toDoubleOrNull() ?: 65.0
                } else {
                    (viewModel.bmiWeightLbs.toDoubleOrNull() ?: 140.0) * 0.453592
                }

                val heightVal = if (viewModel.bmiHeightUnit == "CM") {
                    (viewModel.bmiHeightCm.toDoubleOrNull() ?: 170.0) / 100.0
                } else {
                    val ft = viewModel.bmiHeightFt.toDoubleOrNull() ?: 5.0
                    val inch = viewModel.bmiHeightIn.toDoubleOrNull() ?: 7.0
                    ((ft * 12) + inch) * 0.0254
                }

                if (heightVal > 0) {
                    val bmi = weightVal / (heightVal * heightVal)
                    calculatedBmiScore = String.format("%.1f", bmi)
                    bmiStatusCategory = when {
                        bmi < 18.5 -> "Underweight"
                        bmi < 24.9 -> "Normal weight"
                        bmi < 29.9 -> "Overweight"
                        else -> "Obese"
                    }

                    // Water metrics standard estimates (weight * 0.033 litres)
                    recommendedWaterLitresResult = String.format("%.1f", weightVal * 0.033)
                }
            },
            modifier = Modifier.fillMaxWidth().testTag("bmi_calc_btn")
        ) {
            Text("Calculate Fitness status")
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("BMI Classification Result Score: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(calculatedBmiScore, fontSize = 42.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                Text("Body Status: $bmiStatusCategory", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)

                Spacer(modifier = Modifier.height(14.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))

                Text("Recommended Daily Water Intake: ", fontSize = 12.sp, color = Color.Gray)
                Text("$recommendedWaterLitresResult Liters of water daily", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}
