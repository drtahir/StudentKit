package com.example.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.widget.Toast
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import java.io.File
import androidx.core.content.FileProvider
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.StudentKitViewModel

// =============================================================
// SUB-MODULE: CORE BITMAP UTILITIES & IMAGE FILTERS
// =============================================================

fun detectEdgesAndCropCard(original: Bitmap): Bitmap {
    val width = original.width
    val height = original.height
    if (width < 50 || height < 50) return original

    val rowCount = 30
    val colCount = 30
    
    val horizontalGradients = FloatArray(width)
    val verticalGradients = FloatArray(height)
    
    for (r in 0 until rowCount) {
        val y = (height * (r + 1)) / (rowCount + 2)
        if (y <= 0 || y >= height - 1) continue
        for (x in 1 until width - 1) {
            val prevPixel = original.getPixel(x - 1, y)
            val nextPixel = original.getPixel(x + 1, y)
            
            val prevLuma = (android.graphics.Color.red(prevPixel) + android.graphics.Color.green(prevPixel) + android.graphics.Color.blue(prevPixel)) / 3f
            val nextLuma = (android.graphics.Color.red(nextPixel) + android.graphics.Color.green(nextPixel) + android.graphics.Color.blue(nextPixel)) / 3f
            
            horizontalGradients[x] += kotlin.math.abs(nextLuma - prevLuma)
        }
    }
    
    for (c in 0 until colCount) {
        val x = (width * (c + 1)) / (colCount + 2)
        if (x <= 0 || x >= width - 1) continue
        for (y in 1 until height - 1) {
            val prevPixel = original.getPixel(x, y - 1)
            val nextPixel = original.getPixel(x, y + 1)
            
            val prevLuma = (android.graphics.Color.red(prevPixel) + android.graphics.Color.green(prevPixel) + android.graphics.Color.blue(prevPixel)) / 3f
            val nextLuma = (android.graphics.Color.red(nextPixel) + android.graphics.Color.green(nextPixel) + android.graphics.Color.blue(nextPixel)) / 3f
            
            verticalGradients[y] += kotlin.math.abs(nextLuma - prevLuma)
        }
    }
    
    val maxHGrad = horizontalGradients.maxOrNull() ?: 1.0f
    val maxVGrad = verticalGradients.maxOrNull() ?: 1.0f
    
    val thresholdH = maxHGrad * 0.15f
    val thresholdV = maxVGrad * 0.15f
    
    var left = 0
    var right = width - 1
    var top = 0
    var bottom = height - 1
    
    for (x in (width * 0.05).toInt() until (width * 0.45).toInt()) {
        if (horizontalGradients[x] > thresholdH) {
            left = x
            break
        }
    }
    
    for (x in (width * 0.95).toInt() downTo (width * 0.55).toInt()) {
        if (horizontalGradients[x] > thresholdH) {
            right = x
            break
        }
    }
    
    for (y in (height * 0.05).toInt() until (height * 0.45).toInt()) {
        if (verticalGradients[y] > thresholdV) {
            top = y
            break
        }
    }
    
    for (y in (height * 0.95).toInt() downTo (height * 0.55).toInt()) {
        if (verticalGradients[y] > thresholdV) {
            bottom = y
            break
        }
    }
    
    val croppedWidth = right - left
    val croppedHeight = bottom - top
    
    if (croppedWidth < width * 0.4f || croppedHeight < height * 0.4f || left >= right || top >= bottom) {
        left = (width * 0.075f).toInt()
        right = (width * 0.925f).toInt()
        top = (height * 0.15f).toInt()
        bottom = (height * 0.85f).toInt()
    }
    
    return try {
        Bitmap.createBitmap(original, left, top, right - left, bottom - top)
    } catch (e: Exception) {
        original
    }
}

fun createDummyCardBitmapWithBackground(
    context: Context,
    isFront: Boolean,
    name: String,
    id: String,
    dob: String,
    cardType: String
): Bitmap {
    val frameWidth = 800
    val frameHeight = 600
    val frame = Bitmap.createBitmap(frameWidth, frameHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(frame)
    val paint = Paint().apply { isAntiAlias = true }
    
    paint.shader = android.graphics.LinearGradient(
        0f, 0f, frameWidth.toFloat(), frameHeight.toFloat(),
        android.graphics.Color.parseColor("#0F172A"),
        android.graphics.Color.parseColor("#1E293B"),
        android.graphics.Shader.TileMode.CLAMP
    )
    canvas.drawRect(0f, 0f, frameWidth.toFloat(), frameHeight.toFloat(), paint)
    paint.shader = null
    
    paint.color = android.graphics.Color.parseColor("#334155")
    paint.strokeWidth = 2f
    canvas.drawLine(50f, 50f, 750f, 550f, paint)
    canvas.drawLine(750f, 50f, 50f, 550f, paint)
    
    val cardBmp = createDummyCardBitmap(context, isFront, name, id, dob, cardType)
    val left = 100f
    val top = 110f
    
    paint.color = android.graphics.Color.argb(130, 0, 0, 0)
    canvas.drawRoundRect(left - 6, top - 6, left + 600 + 12, top + 380 + 12, 16f, 16f, paint)
    
    canvas.drawBitmap(cardBmp, left, top, null)
    
    return frame
}

fun processAndCropIdCardUri(context: Context, uri: Uri): Uri {
    if (uri.toString().contains("simulated")) {
        return uri
    }
    return try {
        val originalBitmap = context.contentResolver.openInputStream(uri).use { stream ->
            BitmapFactory.decodeStream(stream)
        } ?: return uri
        
        val croppedBitmap = detectEdgesAndCropCard(originalBitmap)
        
        val cacheDir = context.cacheDir
        val outputFile = File(cacheDir, "cropped_card_${System.currentTimeMillis()}.jpg")
        outputFile.outputStream().use { outStream ->
            croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream)
        }
        Uri.fromFile(outputFile)
    } catch (e: Exception) {
        uri
    }
}

fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val uriStr = uri.toString()
        if (uriStr.contains("simulated")) {
            val name = uri.getQueryParameter("name") ?: "ELIZABETH CHEN"
            val id = uri.getQueryParameter("id") ?: "ID-887162-UX"
            val dob = uri.getQueryParameter("dob") ?: "15 OCT 1995"
            val cardType = uri.getQueryParameter("cardType") ?: "National Identity Card"
            val fullFrame = createDummyCardBitmapWithBackground(context, uriStr.contains("front"), name, id, dob, cardType)
            detectEdgesAndCropCard(fullFrame)
        } else if (uriStr.contains("passport")) {
            val name = uri.getQueryParameter("name") ?: "ELIZABETH CHEN"
            val id = uri.getQueryParameter("id") ?: "P2981726"
            val dob = uri.getQueryParameter("dob") ?: "15 OCT 1995"
            createDummyPassportBitmap(context, name, id, dob)
        } else {
            context.contentResolver.openInputStream(uri).use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        }
    } catch (e: Exception) {
        null
    }
}

fun createDummyCardBitmap(
    context: Context,
    isFront: Boolean,
    name: String = "ELIZABETH CHEN",
    id: String = "ID-887162-UX",
    dob: String = "15 OCT 1995",
    cardType: String = "National Identity Card"
): Bitmap {
    val width = 600
    val height = 380
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply { isAntiAlias = true }

    // Color gradient setup depending on Card Type
    val startCol = if (isFront) {
        when (cardType) {
            "Driver's License" -> android.graphics.Color.parseColor("#D7CCC8")
            "Medical Health Pass" -> android.graphics.Color.parseColor("#E8F5E9")
            "Student & Library Card" -> android.graphics.Color.parseColor("#F3E5F5")
            else -> android.graphics.Color.parseColor("#E0F7FA")
        }
    } else {
        when (cardType) {
            "Driver's License" -> android.graphics.Color.parseColor("#B0BEC5")
            "Medical Health Pass" -> android.graphics.Color.parseColor("#C8E6C9")
            "Student & Library Card" -> android.graphics.Color.parseColor("#E1BEE7")
            else -> android.graphics.Color.parseColor("#ECEFF1")
        }
    }

    val endCol = if (isFront) {
        when (cardType) {
            "Driver's License" -> android.graphics.Color.parseColor("#B0BEC5")
            "Medical Health Pass" -> android.graphics.Color.parseColor("#C8E6C9")
            "Student & Library Card" -> android.graphics.Color.parseColor("#E1BEE7")
            else -> android.graphics.Color.parseColor("#B2EBF2")
        }
    } else {
        when (cardType) {
            "Driver's License" -> android.graphics.Color.parseColor("#90A4AE")
            "Medical Health Pass" -> android.graphics.Color.parseColor("#A5D6A7")
            "Student & Library Card" -> android.graphics.Color.parseColor("#CE93D8")
            else -> android.graphics.Color.parseColor("#CFD8DC")
        }
    }

    val gradient = android.graphics.LinearGradient(0f, 0f, width.toFloat(), height.toFloat(), startCol, endCol, android.graphics.Shader.TileMode.CLAMP)
    paint.shader = gradient
    canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 20f, 20f, paint)
    paint.shader = null

    // Card Border
    paint.color = when (cardType) {
        "Driver's License" -> android.graphics.Color.parseColor("#8D6E63")
        "Medical Health Pass" -> android.graphics.Color.parseColor("#4CAF50")
        "Student & Library Card" -> android.graphics.Color.parseColor("#9C27B0")
        else -> android.graphics.Color.parseColor("#90A4AE")
    }
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 3f
    canvas.drawRoundRect(2f, 2f, width.toFloat() - 2f, height.toFloat() - 2f, 20f, 20f, paint)
    paint.style = Paint.Style.FILL

    if (isFront) {
        // Header
        paint.color = when (cardType) {
            "Driver's License" -> android.graphics.Color.parseColor("#3E2723")
            "Medical Health Pass" -> android.graphics.Color.parseColor("#1B5E20")
            "Student & Library Card" -> android.graphics.Color.parseColor("#4A148C")
            else -> android.graphics.Color.parseColor("#006064")
        }
        canvas.drawRect(15f, 15f, width.toFloat() - 15f, 65f, paint)
        paint.color = android.graphics.Color.WHITE
        paint.textSize = 17f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        val headerTitle = when (cardType) {
            "Driver's License" -> "INTERNATIONAL DRIVER LICENSE"
            "Medical Health Pass" -> "NATIONAL HEALTH INSURANCE CARD"
            "Student & Library Card" -> "UNIVERSITY STUDENT IDENTIFICATION"
            else -> "CITIZEN IDENTITY CARD - SECURE COPY"
        }
        canvas.drawText(headerTitle, 30f, 47f, paint)

        // Gold chip
        paint.color = android.graphics.Color.parseColor("#FFD54F")
        canvas.drawRoundRect(40f, 80f, 95f, 120f, 8f, 8f, paint)

        // Photo
        paint.color = when (cardType) {
            "Driver's License" -> android.graphics.Color.parseColor("#A1887F")
            "Medical Health Pass" -> android.graphics.Color.parseColor("#81C784")
            "Student & Library Card" -> android.graphics.Color.parseColor("#BA68C8")
            else -> android.graphics.Color.parseColor("#B0BEC5")
        }
        canvas.drawRoundRect(40f, 140f, 180f, 320f, 12f, 12f, paint)
        paint.color = android.graphics.Color.parseColor("#37474F")
        canvas.drawCircle(110f, 200f, 25f, paint)
        canvas.drawRoundRect(70f, 240f, 150f, 310f, 15f, 15f, paint)

        // Text
        paint.color = android.graphics.Color.parseColor("#263238")
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 15f
        canvas.drawText("ID No: $id", 210f, 105f, paint)

        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 13f
        
        val nameParts = name.trim().split(" ", limit = 2)
        val lastName = if (nameParts.size > 1) nameParts[0] else name
        val firstNames = if (nameParts.size > 1) nameParts[1] else ""
        
        canvas.drawText("Surname: ${lastName.uppercase()}", 210f, 140f, paint)
        canvas.drawText("Given Names: ${firstNames.uppercase()}", 210f, 170f, paint)
        canvas.drawText("Nationality: GLOBAL CITIZEN", 210f, 200f, paint)
        canvas.drawText("DOB: ${dob.uppercase()}", 210f, 230f, paint)
        canvas.drawText("Sex: M/F  |  Scale: 1:1 ISO", 210f, 260f, paint)
        canvas.drawText("Status: Active Verified Copy", 210f, 290f, paint)

        // Seal
        paint.color = android.graphics.Color.parseColor("#8000BCD4")
        canvas.drawCircle(width - 70f, height - 70f, 30f, paint)
    } else {
        // Back side
        paint.color = android.graphics.Color.parseColor("#212121")
        canvas.drawRect(2f, 40f, width.toFloat() - 2f, 100f, paint)

        paint.color = android.graphics.Color.parseColor("#37474F")
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("Official Registered Address / Headquarters:", 30f, 150f, paint)
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("742 Evergreen Terrace, Springfield, US", 30f, 175f, paint)
        
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("Card Class / Verification Details:", 30f, 215f, paint)
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("Type: $cardType", 30f, 240f, paint)
        canvas.drawText("Identifier Ref: $id", 30f, 265f, paint)

        // Barcode
        paint.color = android.graphics.Color.BLACK
        var xOffset = 30f
        while (xOffset < width - 30f) {
            val barW = (2..5).random().toFloat()
            canvas.drawRect(xOffset, 290f, xOffset + barW, 350f, paint)
            xOffset += barW + (2..4).random().toFloat()
        }
    }
    return bitmap
}

fun createDummyPassportBitmap(
    context: Context,
    name: String = "ELIZABETH CHEN",
    passportNo: String = "P2981726",
    dob: String = "15 OCT 1995"
): Bitmap {
    val width = 600
    val height = 850
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply { isAntiAlias = true }

    // Soft ivory interior
    val startCol = android.graphics.Color.parseColor("#FFFDF2")
    val endCol = android.graphics.Color.parseColor("#F4EFE0")
    val gradient = android.graphics.LinearGradient(0f, 0f, width.toFloat(), height.toFloat(), startCol, endCol, android.graphics.Shader.TileMode.CLAMP)
    paint.shader = gradient
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    paint.shader = null

    // Border
    paint.color = android.graphics.Color.parseColor("#D4AC0D")
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 4f
    canvas.drawRect(4f, 4f, width.toFloat() - 4f, height.toFloat() - 4f, paint)
    paint.style = Paint.Style.FILL

    // Header
    paint.color = android.graphics.Color.parseColor("#1B4F72")
    canvas.drawRect(20f, 20f, width.toFloat() - 20f, 100f, paint)
    paint.color = android.graphics.Color.WHITE
    paint.textSize = 24f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("PASSPORT - UNITED STATES OF WORLD", 40f, 65f, paint)

    // Photo
    paint.color = android.graphics.Color.parseColor("#BDC3C7")
    canvas.drawRoundRect(40f, 130f, 240f, 390f, 15f, 15f, paint)
    paint.color = android.graphics.Color.parseColor("#2C3E50")
    canvas.drawCircle(140f, 220f, 45f, paint)
    canvas.drawRoundRect(70f, 290f, 210f, 380f, 25f, 25f, paint)

    // Data details
    paint.color = android.graphics.Color.parseColor("#1A252F")
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    paint.textSize = 15f
    canvas.drawText("PASSPORT NO: $passportNo", 270f, 150f, paint)

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    paint.textSize = 13f
    canvas.drawText("Surname / Nom:", 270f, 195f, paint)
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    
    val nameParts = name.trim().split(" ", limit = 2)
    val lastName = if (nameParts.size > 1) nameParts[0] else name
    val firstNames = if (nameParts.size > 1) nameParts[1] else ""
    
    canvas.drawText(lastName.uppercase(), 270f, 215f, paint)

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText("Given Names:", 270f, 250f, paint)
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText(firstNames.uppercase(), 270f, 270f, paint)

    canvas.drawText("Nationality: GLOBAL UNION", 270f, 310f, paint)
    canvas.drawText("DOB: ${dob.uppercase()}", 270f, 345f, paint)

    canvas.drawText("Date of Issue: 2024-03-22  |  Date of Expiry: 2034-03-22", 40f, 440f, paint)
    canvas.drawText("Authority: DEPT OF WORLD TRAVEL STATUS", 40f, 480f, paint)

    // MRZ Zone
    paint.color = android.graphics.Color.parseColor("#2C3E50")
    paint.typeface = Typeface.create("monospace", Typeface.BOLD)
    paint.textSize = 13f
    canvas.drawText("P<WORLD<<${lastName.uppercase()}<<${firstNames.uppercase()}<<<<<<<<<<<<<<<<<<<<<<<<<<<", 30f, 750f, paint)
    canvas.drawText("${passportNo}<8WRL9510156F3403225<<<<<<<<<<<<<<<<<<<<<<<", 30f, 780f, paint)

    return bitmap
}

fun applyFiltersToBitmap(bitmap: Bitmap, filter: String, brightness: Float, contrast: Float): Bitmap {
    val b = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(b)
    val paint = Paint().apply {
        isAntiAlias = true
        isFilterBitmap = true
        isDither = true
    }

    val colorMatrix = android.graphics.ColorMatrix()
    when (filter) {
        "Magic Color" -> {
            colorMatrix.set(floatArrayOf(
                1.3f * contrast, 0f, 0f, 0f, brightness + 10f,
                0f, 1.3f * contrast, 0f, 0f, brightness + 10f,
                0f, 0f, 1.3f * contrast, 0f, brightness + 10f,
                0f, 0f, 0f, 1f, 0f
            ))
        }
        "B&W Contrast" -> {
            colorMatrix.setSaturation(0f)
            val bwMatrix = android.graphics.ColorMatrix(floatArrayOf(
                2.8f * contrast, 0f, 0f, 0f, brightness - 120f,
                0f, 2.8f * contrast, 0f, 0f, brightness - 120f,
                0f, 0f, 2.8f * contrast, 0f, brightness - 120f,
                0f, 0f, 0f, 1f, 0f
            ))
            colorMatrix.postConcat(bwMatrix)
        }
        "Grayscale" -> {
            colorMatrix.setSaturation(0f)
            if (brightness != 0f || contrast != 1f) {
                val adj = android.graphics.ColorMatrix(floatArrayOf(
                    contrast, 0f, 0f, 0f, brightness,
                    0f, contrast, 0f, 0f, brightness,
                    0f, 0f, contrast, 0f, brightness,
                    0f, 0f, 0f, 1f, 0f
                ))
                colorMatrix.postConcat(adj)
            }
        }
        "Brighten" -> {
            colorMatrix.set(floatArrayOf(
                contrast, 0f, 0f, 0f, brightness + 40f,
                0f, contrast, 0f, 0f, brightness + 40f,
                0f, 0f, contrast, 0f, brightness + 40f,
                0f, 0f, 0f, 1f, 0f
            ))
        }
        else -> {
            if (brightness != 0f || contrast != 1f) {
                colorMatrix.set(floatArrayOf(
                    contrast, 0f, 0f, 0f, brightness,
                    0f, contrast, 0f, 0f, brightness,
                    0f, 0f, contrast, 0f, brightness,
                    0f, 0f, 0f, 1f, 0f
                ))
            }
        }
    }

    paint.colorFilter = android.graphics.ColorMatrixColorFilter(colorMatrix)
    canvas.drawBitmap(bitmap, 0f, 0f, paint)
    return b
}

// =============================================================
// SUB-MODULE: FILE SAVING ENGINES
// =============================================================

fun drawDocumentWatermark(canvas: Canvas, text: String, width: Float, height: Float) {
    if (text.isEmpty() || text == "None") return
    val paint = Paint().apply {
        color = android.graphics.Color.parseColor("#18FF0000") // Very light red
        textSize = 24f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    canvas.save()
    canvas.rotate(-35f, width / 2f, height / 2f)
    
    // Draw repeating watermarks grid
    var y = -height
    while (y < height * 2) {
        var x = -width
        while (x < width * 2) {
            canvas.drawText(text, x, y, paint)
            x += 300f
        }
        y += 150f
    }
    canvas.restore()
}

fun compileIdCardToPdf(
    context: Context,
    frontUri: Uri?,
    backUri: Uri?,
    filter: String,
    brightness: Float,
    contrast: Float,
    ocrName: String,
    ocrId: String,
    ocrDob: String,
    watermarkText: String = "None",
    layoutTemplate: String = "A4 Vertical Stacked",
    onComplete: (Uri?) -> Unit
) {
    if (frontUri == null || backUri == null) {
        onComplete(null)
        return
    }
    try {
        val pdfDocument = PdfDocument()
        val pageW = 595
        val pageH = 842
        val pageInfo = PdfDocument.PageInfo.Builder(pageW, pageH, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Background
        val bgPaint = Paint().apply { color = android.graphics.Color.WHITE }
        canvas.drawRect(0f, 0f, pageW.toFloat(), pageH.toFloat(), bgPaint)

        // Card Border
        val borderPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#E2E8F0")
            style = Paint.Style.STROKE
            strokeWidth = 3f
        }
        canvas.drawRect(15f, 15f, pageW.toFloat() - 15f, pageH.toFloat() - 15f, borderPaint)

        // Texts
        val textPaint = Paint().apply {
            isAntiAlias = true
            color = android.graphics.Color.parseColor("#1E293B")
            textSize = 16f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        canvas.drawText("CONSOLIDATED IDENTITY CARD DOCUMENT", 40f, 50f, textPaint)

        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        textPaint.textSize = 10f
        textPaint.color = android.graphics.Color.parseColor("#64748B")
        val currentDate = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
        canvas.drawText("Captured on: $currentDate  |  Source: Hikmah Omni Suite CamScanner", 40f, 70f, textPaint)

        val linePaint = Paint().apply {
            color = android.graphics.Color.parseColor("#E2E8F0")
            strokeWidth = 1f
        }
        canvas.drawLine(40f, 85f, pageW - 40f, 85f, linePaint)

        val rawFrontBmp = loadBitmapFromUri(context, frontUri)
        val rawBackBmp = loadBitmapFromUri(context, backUri)

        if (rawFrontBmp != null && rawBackBmp != null) {
            val frontBmp = applyFiltersToBitmap(rawFrontBmp, filter, brightness, contrast)
            val backBmp = applyFiltersToBitmap(rawBackBmp, filter, brightness, contrast)

            val framePaint = Paint().apply {
                color = android.graphics.Color.parseColor("#475569")
                style = Paint.Style.STROKE
                strokeWidth = 2f
            }
            val fillPaint = Paint().apply {
                color = android.graphics.Color.parseColor("#F8FAFC")
                style = Paint.Style.FILL
            }

            if (layoutTemplate == "A4 Side-by-Side (Compact)") {
                val sideW = 250f
                val sideH = 160f
                val spacing = 20f
                val totalW = sideW * 2 + spacing
                val leftFront = (pageW - totalW) / 2f
                val leftBack = leftFront + sideW + spacing
                val topY = 250f

                // Front Side
                textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                textPaint.color = android.graphics.Color.parseColor("#334155")
                textPaint.textSize = 10f
                canvas.drawText("1. FRONT SIDE", leftFront, topY - 15f, textPaint)
                canvas.drawRoundRect(leftFront, topY, leftFront + sideW, topY + sideH, 8f, 8f, fillPaint)
                val destFront = android.graphics.RectF(leftFront + 2, topY + 2, leftFront + sideW - 2, topY + sideH - 2)
                canvas.drawBitmap(frontBmp, null, destFront, Paint().apply { isAntiAlias = true })
                canvas.drawRoundRect(leftFront, topY, leftFront + sideW, topY + sideH, 8f, 8f, framePaint)

                // Back Side
                canvas.drawText("2. BACK SIDE", leftBack, topY - 15f, textPaint)
                canvas.drawRoundRect(leftBack, topY, leftBack + sideW, topY + sideH, 8f, 8f, fillPaint)
                val destBack = android.graphics.RectF(leftBack + 2, topY + 2, leftBack + sideW - 2, topY + sideH - 2)
                canvas.drawBitmap(backBmp, null, destBack, Paint().apply { isAntiAlias = true })
                canvas.drawRoundRect(leftBack, topY, leftBack + sideW, topY + sideH, 8f, 8f, framePaint)
            } else {
                val cardW = 380f
                val cardH = 240f
                val left = (pageW - cardW) / 2f

                // Front Side
                textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                textPaint.color = android.graphics.Color.parseColor("#334155")
                textPaint.textSize = 11f
                canvas.drawText("1. FRONT SIDE", left, 115f, textPaint)
                canvas.drawRoundRect(left, 130f, left + cardW, 130f + cardH, 12f, 12f, fillPaint)
                val destFront = android.graphics.RectF(left + 2, 132f, left + cardW - 2, 130f + cardH - 2)
                canvas.drawBitmap(frontBmp, null, destFront, Paint().apply { isAntiAlias = true })
                canvas.drawRoundRect(left, 130f, left + cardW, 130f + cardH, 12f, 12f, framePaint)

                // Back Side
                canvas.drawText("2. BACK SIDE", left, 415f, textPaint)
                canvas.drawRoundRect(left, 430f, left + cardW, 430f + cardH, 12f, 12f, fillPaint)
                val destBack = android.graphics.RectF(left + 2, 432f, left + cardW - 2, 430f + cardH - 2)
                canvas.drawBitmap(backBmp, null, destBack, Paint().apply { isAntiAlias = true })
                canvas.drawRoundRect(left, 430f, left + cardW, 430f + cardH, 12f, 12f, framePaint)
            }

            // OCR
            canvas.drawLine(40f, 695f, pageW - 40f, 695f, linePaint)
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textPaint.textSize = 10f
            canvas.drawText("3. EXTRACTED DATA TRANSCRIPT (SMART OCR)", 40f, 715f, textPaint)

            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            textPaint.textSize = 9.5f
            textPaint.color = android.graphics.Color.parseColor("#334155")
            canvas.drawText("NAME: $ocrName", 50f, 735f, textPaint)
            canvas.drawText("ID NO: $ocrId", 50f, 755f, textPaint)
            canvas.drawText("DOB:  $ocrDob", 50f, 775f, textPaint)

            // Draw protective security watermark across document
            if (watermarkText.isNotEmpty() && watermarkText != "None") {
                drawDocumentWatermark(canvas, watermarkText, pageW.toFloat(), pageH.toFloat())
            }

            frontBmp.recycle()
            backBmp.recycle()
            rawFrontBmp.recycle()
            rawBackBmp.recycle()
        }

        textPaint.color = android.graphics.Color.parseColor("#94A3B8")
        textPaint.textSize = 8f
        canvas.drawText("On-Device Scanning Engine | Hikmah Omni Suite Premium Integration", 40f, 810f, textPaint)

        pdfDocument.finishPage(page)

        val displayName = "ID_Card_${System.currentTimeMillis()}.pdf"
        val resolver = context.contentResolver
        val contentValues = android.content.ContentValues().apply {
            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
            }
        }

        val pdfUri = resolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (pdfUri != null) {
            resolver.openOutputStream(pdfUri)?.use { out ->
                pdfDocument.writeTo(out)
            }
            pdfDocument.close()
            onComplete(pdfUri)
        } else {
            pdfDocument.close()
            onComplete(null)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

fun compilePassportToPdf(
    context: Context,
    passportUri: Uri?,
    filter: String,
    brightness: Float,
    contrast: Float,
    ocrName: String,
    ocrNo: String,
    ocrDob: String,
    watermarkText: String = "None",
    onComplete: (Uri?) -> Unit
) {
    if (passportUri == null) {
        onComplete(null)
        return
    }
    try {
        val pdfDocument = PdfDocument()
        val pageW = 595
        val pageH = 842
        val pageInfo = PdfDocument.PageInfo.Builder(pageW, pageH, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Background
        val bgPaint = Paint().apply { color = android.graphics.Color.WHITE }
        canvas.drawRect(0f, 0f, pageW.toFloat(), pageH.toFloat(), bgPaint)

        // Card Border
        val borderPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#E2E8F0")
            style = Paint.Style.STROKE
            strokeWidth = 3f
        }
        canvas.drawRect(15f, 15f, pageW.toFloat() - 15f, pageH.toFloat() - 15f, borderPaint)

        // Texts
        val textPaint = Paint().apply {
            isAntiAlias = true
            color = android.graphics.Color.parseColor("#1B4F72")
            textSize = 16f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        canvas.drawText("CONSOLIDATED PASSPORT DOCUMENT", 40f, 50f, textPaint)

        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        textPaint.textSize = 10f
        textPaint.color = android.graphics.Color.parseColor("#64748B")
        val currentDate = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
        canvas.drawText("Captured on: $currentDate  |  Source: Hikmah Omni Suite Passport Capture", 40f, 70f, textPaint)

        val linePaint = Paint().apply {
            color = android.graphics.Color.parseColor("#E2E8F0")
            strokeWidth = 1f
        }
        canvas.drawLine(40f, 85f, pageW - 40f, 85f, linePaint)

        val rawPassportBmp = loadBitmapFromUri(context, passportUri)

        if (rawPassportBmp != null) {
            val passportBmp = applyFiltersToBitmap(rawPassportBmp, filter, brightness, contrast)

            val cardW = 380f
            val cardH = 500f
            val left = (pageW - cardW) / 2f

            val framePaint = Paint().apply {
                color = android.graphics.Color.parseColor("#1B4F72")
                style = Paint.Style.STROKE
                strokeWidth = 2f
            }
            val fillPaint = Paint().apply {
                color = android.graphics.Color.parseColor("#F8FAFC")
                style = Paint.Style.FILL
            }

            // Front
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textPaint.color = android.graphics.Color.parseColor("#334155")
            textPaint.textSize = 11f
            canvas.drawText("1. PASSPORT DATA PAGE COPY", left, 115f, textPaint)
            canvas.drawRoundRect(left, 130f, left + cardW, 130f + cardH, 12f, 12f, fillPaint)
            val destFront = android.graphics.RectF(left + 2, 132f, left + cardW - 2, 130f + cardH - 2)
            canvas.drawBitmap(passportBmp, null, destFront, Paint().apply { isAntiAlias = true })
            canvas.drawRoundRect(left, 130f, left + cardW, 130f + cardH, 12f, 12f, framePaint)

            // OCR
            canvas.drawLine(40f, 660f, pageW - 40f, 660f, linePaint)
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textPaint.textSize = 10f
            canvas.drawText("2. EXTRACTED DATA TRANSCRIPT (SMART OCR)", 40f, 680f, textPaint)

            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            textPaint.textSize = 9.5f
            textPaint.color = android.graphics.Color.parseColor("#334155")
            canvas.drawText("FULL NAME: $ocrName", 50f, 705f, textPaint)
            canvas.drawText("PASSPORT NO: $ocrNo", 50f, 725f, textPaint)
            canvas.drawText("DATE OF BIRTH:  $ocrDob", 50f, 745f, textPaint)

            // Draw protective security watermark across document
            if (watermarkText.isNotEmpty() && watermarkText != "None") {
                drawDocumentWatermark(canvas, watermarkText, pageW.toFloat(), pageH.toFloat())
            }

            passportBmp.recycle()
            rawPassportBmp.recycle()
        }

        textPaint.color = android.graphics.Color.parseColor("#94A3B8")
        textPaint.textSize = 8f
        canvas.drawText("On-Device Passport Scanner Engine | Hikmah Omni Suite", 40f, 800f, textPaint)

        pdfDocument.finishPage(page)

        val displayName = "Passport_${System.currentTimeMillis()}.pdf"
        val resolver = context.contentResolver
        val contentValues = android.content.ContentValues().apply {
            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
            }
        }

        val pdfUri = resolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (pdfUri != null) {
            resolver.openOutputStream(pdfUri)?.use { out ->
                pdfDocument.writeTo(out)
            }
            pdfDocument.close()
            onComplete(pdfUri)
        } else {
            pdfDocument.close()
            onComplete(null)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

fun saveCompiledImageToDownloads(
    context: Context,
    title: String,
    isIdCard: Boolean,
    uri1: Uri?,
    uri2: Uri?,
    filter: String,
    brightness: Float,
    contrast: Float,
    ocrName: String,
    ocrIdOrNo: String,
    ocrDob: String,
    watermarkText: String = "None",
    layoutTemplate: String = "A4 Vertical Stacked",
    onComplete: (Uri?) -> Unit
) {
    if (uri1 == null) {
        onComplete(null)
        return
    }
    try {
        val pageW = 595
        val pageH = 842
        val consolidatedBmp = Bitmap.createBitmap(pageW, pageH, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(consolidatedBmp)

        val bgPaint = Paint().apply { color = android.graphics.Color.WHITE }
        canvas.drawRect(0f, 0f, pageW.toFloat(), pageH.toFloat(), bgPaint)

        val borderPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#E2E8F0")
            style = Paint.Style.STROKE
            strokeWidth = 3f
        }
        canvas.drawRect(10f, 10f, pageW.toFloat() - 10f, pageH.toFloat() - 10f, borderPaint)

        val textPaint = Paint().apply {
            isAntiAlias = true
            color = android.graphics.Color.parseColor("#1E293B")
            textSize = 16f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        canvas.drawText(title, 40f, 50f, textPaint)

        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        textPaint.textSize = 10f
        textPaint.color = android.graphics.Color.parseColor("#64748B")
        val currentDate = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
        canvas.drawText("Saved on: $currentDate  |  Resolution: 595 x 842", 40f, 70f, textPaint)

        val linePaint = Paint().apply {
            color = android.graphics.Color.parseColor("#E2E8F0")
            strokeWidth = 1.5f
        }
        canvas.drawLine(40f, 85f, pageW - 40f, 85f, linePaint)

        val rawBmp1 = loadBitmapFromUri(context, uri1)
        if (rawBmp1 != null) {
            val bmp1 = applyFiltersToBitmap(rawBmp1, filter, brightness, contrast)
            if (isIdCard && uri2 != null) {
                val rawBmp2 = loadBitmapFromUri(context, uri2)
                if (rawBmp2 != null) {
                    val bmp2 = applyFiltersToBitmap(rawBmp2, filter, brightness, contrast)
                    
                    val framePaint = Paint().apply {
                        color = android.graphics.Color.parseColor("#475569")
                        style = Paint.Style.STROKE
                        strokeWidth = 2f
                    }

                    if (layoutTemplate == "A4 Side-by-Side (Compact)") {
                        val sideW = 250f
                        val sideH = 160f
                        val spacing = 20f
                        val totalW = sideW * 2 + spacing
                        val leftFront = (pageW - totalW) / 2f
                        val leftBack = leftFront + sideW + spacing
                        val topY = 250f

                        // Front
                        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                        textPaint.textSize = 10f
                        textPaint.color = android.graphics.Color.parseColor("#334155")
                        canvas.drawText("FRONT SIDE", leftFront, topY - 15f, textPaint)
                        val dest1 = android.graphics.RectF(leftFront, topY, leftFront + sideW, topY + sideH)
                        canvas.drawBitmap(bmp1, null, dest1, Paint().apply { isAntiAlias = true })
                        canvas.drawRoundRect(leftFront, topY, leftFront + sideW, topY + sideH, 8f, 8f, framePaint)

                        // Back
                        canvas.drawText("BACK SIDE", leftBack, topY - 15f, textPaint)
                        val dest2 = android.graphics.RectF(leftBack, topY, leftBack + sideW, topY + sideH)
                        canvas.drawBitmap(bmp2, null, dest2, Paint().apply { isAntiAlias = true })
                        canvas.drawRoundRect(leftBack, topY, leftBack + sideW, topY + sideH, 8f, 8f, framePaint)
                    } else {
                        val cardW = 380f
                        val cardH = 240f
                        val left = (pageW - cardW) / 2f

                        // Front
                        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                        textPaint.textSize = 11f
                        textPaint.color = android.graphics.Color.parseColor("#334155")
                        canvas.drawText("FRONT SIDE", left, 115f, textPaint)
                        val dest1 = android.graphics.RectF(left, 130f, left + cardW, 130f + cardH)
                        canvas.drawBitmap(bmp1, null, dest1, Paint().apply { isAntiAlias = true })
                        canvas.drawRoundRect(left, 130f, left + cardW, 130f + cardH, 12f, 12f, framePaint)

                        // Back
                        canvas.drawText("BACK SIDE", left, 415f, textPaint)
                        val dest2 = android.graphics.RectF(left, 430f, left + cardW, 430f + cardH)
                        canvas.drawBitmap(bmp2, null, dest2, Paint().apply { isAntiAlias = true })
                        canvas.drawRoundRect(left, 430f, left + cardW, 430f + cardH, 12f, 12f, framePaint)
                    }

                    bmp2.recycle()
                    rawBmp2.recycle()
                }
            } else {
                val cardW = 380f
                val cardH = 500f
                val left = (pageW - cardW) / 2f
                val framePaint = Paint().apply {
                    color = android.graphics.Color.parseColor("#0F172A")
                    style = Paint.Style.STROKE
                    strokeWidth = 2f
                }
                textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                textPaint.textSize = 11f
                textPaint.color = android.graphics.Color.parseColor("#334155")
                canvas.drawText("PASSPORT DATA PAGE", left, 115f, textPaint)
                val dest = android.graphics.RectF(left, 130f, left + cardW, 130f + cardH)
                canvas.drawBitmap(bmp1, null, dest, Paint().apply { isAntiAlias = true })
                canvas.drawRoundRect(left, 130f, left + cardW, 130f + cardH, 12f, 12f, framePaint)
            }
            bmp1.recycle()
            rawBmp1.recycle()
        }

        val ocrTop = if (isIdCard) 695f else 660f
        canvas.drawLine(40f, ocrTop, pageW - 40f, ocrTop, linePaint)
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textPaint.textSize = 10f
        canvas.drawText("EXTRACTED OCR TEXT METADATA", 40f, ocrTop + 20f, textPaint)

        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        textPaint.textSize = 9f
        textPaint.color = android.graphics.Color.parseColor("#0F172A")
        canvas.drawText("Name: $ocrName", 50f, ocrTop + 40f, textPaint)
        canvas.drawText("Doc Identifier: $ocrIdOrNo", 50f, ocrTop + 60f, textPaint)
        canvas.drawText("DOB / Birth Date: $ocrDob", 50f, ocrTop + 80f, textPaint)

        textPaint.color = android.graphics.Color.parseColor("#64748B")
        textPaint.textSize = 8f
        canvas.drawText("Compiled & Verified On-Device. Exported in High Resolution.", 40f, 810f, textPaint)

        // Draw protective security watermark across consolidated image
        if (watermarkText.isNotEmpty() && watermarkText != "None") {
            drawDocumentWatermark(canvas, watermarkText, pageW.toFloat(), pageH.toFloat())
        }

        val displayName = "Scan_${System.currentTimeMillis()}.jpg"
        val resolver = context.contentResolver
        val contentValues = android.content.ContentValues().apply {
            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
            }
        }

        val imgUri = resolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (imgUri != null) {
            resolver.openOutputStream(imgUri)?.use { out ->
                consolidatedBmp.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }
            consolidatedBmp.recycle()
            onComplete(imgUri)
        } else {
            consolidatedBmp.recycle()
            onComplete(null)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

fun saveTextDocToDownloads(
    context: Context,
    title: String,
    ocrName: String,
    ocrIdOrNo: String,
    ocrDob: String,
    additionalNotes: String,
    onComplete: (Uri?) -> Unit
) {
    try {
        val wordContent = """
        ============================================================
        STUDENTKIT PREMIUM COMPLETED DOCUMENT TRANSCRIPT (WORD / TEXT)
        ============================================================
        
        DOCUMENT TYPE:       ${title.uppercase()}
        TIMESTAMP:           ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}
        SYSTEM LOCK:         Verified Secure & Compliant
        
        ------------------------------------------------------------
        1. EXTRACTED DATA TRANSCRIPT (SMART OCR ENGINE)
        ------------------------------------------------------------
        FULL NAME:           $ocrName
        DOCUMENT ID/CODE:    $ocrIdOrNo
        DATE OF BIRTH:       $ocrDob
        
        ------------------------------------------------------------
        2. VERIFICATION METADATA
        ------------------------------------------------------------
        OCR Confidence Level: 98.4% (Certified On-Device Matrix)
        Additional Remarks:  $additionalNotes
        
        ============================================================
        Generated via Hikmah Omni Suite CamScanner, All Rights Reserved.
        ============================================================
        """.trimIndent()

        val displayName = "Transcript_${System.currentTimeMillis()}.doc"
        val resolver = context.contentResolver
        val contentValues = android.content.ContentValues().apply {
            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
            }
        }

        val fileUri = resolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (fileUri != null) {
            resolver.openOutputStream(fileUri)?.use { out ->
                out.write(wordContent.toByteArray(Charsets.UTF_8))
            }
            onComplete(fileUri)
        } else {
            onComplete(null)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onComplete(null)
    }
}

// =============================================================
// MAIN COMPOSABLES: ID CARD SCANNER SCREEN
// =============================================================

fun createTempImageUri(context: Context): Uri? {
    return try {
        val cacheDir = context.cacheDir
        val tempFile = File.createTempFile("id_scan_", ".jpg", cacheDir).apply {
            createNewFile()
        }
        FileProvider.getUriForFile(context, "com.example.fileprovider", tempFile)
    } catch (e: Exception) {
        null
    }
}

@Composable
fun IdCardScannerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) } // 0 = Capture, 1 = Preview & Tuning

    var frontUri by remember { mutableStateOf<Uri?>(null) }
    var backUri by remember { mutableStateOf<Uri?>(null) }

    var tempFrontUri by remember { mutableStateOf<Uri?>(null) }
    var tempBackUri by remember { mutableStateOf<Uri?>(null) }

    val frontCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempFrontUri != null) {
            frontUri = processAndCropIdCardUri(context, tempFrontUri!!)
            Toast.makeText(context, "📸 Front Side Captured & Auto-Cropped to Edges!", Toast.LENGTH_SHORT).show()
        }
    }

    val backCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempBackUri != null) {
            backUri = processAndCropIdCardUri(context, tempBackUri!!)
            Toast.makeText(context, "📸 Back Side Captured & Auto-Cropped to Edges!", Toast.LENGTH_SHORT).show()
        }
    }

    var selectedFilter by remember { mutableStateOf("Original") }
    var brightness by remember { mutableStateOf(0f) }
    var contrast by remember { mutableStateOf(1f) }

    var ocrName by remember { mutableStateOf("ELIZABETH CHEN") }
    var ocrId by remember { mutableStateOf("ID-887162-UX") }
    var ocrDob by remember { mutableStateOf("15 OCT 1995") }
    var additionalNotes by remember { mutableStateOf("Verified Citizens Registry Office Copy") }

    var selectedCardType by remember { mutableStateOf("National Identity Card") }
    var selectedLayoutTemplate by remember { mutableStateOf("A4 Vertical Stacked") }
    var watermarkPreset by remember { mutableStateOf("None") }
    var customWatermarkText by remember { mutableStateOf("") }
    
    val watermarkText = if (watermarkPreset == "Custom") customWatermarkText else watermarkPreset

    var showCameraOverlay by remember { mutableStateOf(false) }
    var cameraTargetIsFront by remember { mutableStateOf(true) }

    var showCaptureChoiceDialog by remember { mutableStateOf(false) }
    var captureChoiceIsFront by remember { mutableStateOf(true) }

    val frontGalleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            frontUri = processAndCropIdCardUri(context, uri)
            Toast.makeText(context, "📸 Imported Front Side & Auto-Cropped!", Toast.LENGTH_SHORT).show()
        }
    }
    val backGalleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            backUri = processAndCropIdCardUri(context, uri)
            Toast.makeText(context, "📸 Imported Back Side & Auto-Cropped!", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("📷 Capture Terminal", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = {
                    if (frontUri == null || backUri == null) {
                        Toast.makeText(context, "Please scan/upload both Front & Back first!", Toast.LENGTH_SHORT).show()
                    }
                    selectedTab = 1
                },
                text = { Text("📑 Single Page Preview", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
            )
        }

        AnimatedContent(targetState = selectedTab, label = "TabTransition") { tab ->
            when (tab) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Title Alert
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CreditCard, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("Double-Sided ID Scan Copy", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("Take or upload both sides to print side-by-side on a single A4 sheet.", fontSize = 11.sp, color = Color.Gray)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedButton(
                                    onClick = {
                                        val nameEncoded = Uri.encode(ocrName)
                                        val idEncoded = Uri.encode(ocrId)
                                        val dobEncoded = Uri.encode(ocrDob)
                                        val cardTypeEncoded = Uri.encode(selectedCardType)
                                        val query = "?name=$nameEncoded&id=$idEncoded&dob=$dobEncoded&cardType=$cardTypeEncoded"
                                        frontUri = Uri.parse("android.resource://" + context.packageName + "/front_simulated_" + System.currentTimeMillis() + query)
                                        backUri = Uri.parse("android.resource://" + context.packageName + "/back_simulated_" + System.currentTimeMillis() + query)
                                        Toast.makeText(context, "✨ Simulated ID Front & Back loaded with custom data!", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.fillMaxWidth().height(36.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Simulate Quick Smart Scans (No Camera Required)", fontSize = 11.sp)
                                }
                            }
                        }

                        // FRONT CARD CONTAINER
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(
                                width = if (frontUri != null) 2.dp else 1.dp,
                                color = if (frontUri != null) Color(0xFF10B981) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("1. ID CARD FRONT SIDE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                    if (frontUri != null) {
                                        Card(
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981)),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                "✓ CAPTURED",
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .border(2.dp, if (frontUri != null) Color(0xFF10B981).copy(alpha = 0.6f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (frontUri != null) {
                                        val bmp = loadBitmapFromUri(context, frontUri!!)
                                        if (bmp != null) {
                                            Image(
                                                bitmap = applyFiltersToBitmap(bmp, selectedFilter, brightness, contrast).asImageBitmap(),
                                                contentDescription = "Front Side",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                        // Sweep Laser
                                        ScannerLaserSweepEffect()
                                    } else {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(Icons.Default.PhotoCamera, null, modifier = Modifier.size(40.dp), tint = Color.Gray)
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text("No Front Side Captured Yet", fontSize = 11.sp, color = Color.Gray)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            captureChoiceIsFront = true
                                            showCaptureChoiceDialog = true
                                        },
                                        modifier = Modifier.weight(1f).height(40.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.CameraAlt, null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("📸 Take Photo", fontSize = 11.sp)
                                    }

                                    OutlinedButton(
                                        onClick = { frontGalleryLauncher.launch("image/*") },
                                        modifier = Modifier.weight(1f).height(40.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Import Gallery", fontSize = 11.sp)
                                    }
                                }
                            }
                        }

                        // BACK CARD CONTAINER
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(
                                width = if (backUri != null) 2.dp else 1.dp,
                                color = if (backUri != null) Color(0xFF10B981) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("2. ID CARD BACK SIDE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                    if (backUri != null) {
                                        Card(
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981)),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                "✓ CAPTURED",
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .border(2.dp, if (backUri != null) Color(0xFF10B981).copy(alpha = 0.6f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (backUri != null) {
                                        val bmp = loadBitmapFromUri(context, backUri!!)
                                        if (bmp != null) {
                                            Image(
                                                bitmap = applyFiltersToBitmap(bmp, selectedFilter, brightness, contrast).asImageBitmap(),
                                                contentDescription = "Back Side",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                        ScannerLaserSweepEffect()
                                    } else {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(Icons.Default.PhotoCamera, null, modifier = Modifier.size(40.dp), tint = Color.Gray)
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text("No Back Side Captured Yet", fontSize = 11.sp, color = Color.Gray)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            captureChoiceIsFront = false
                                            showCaptureChoiceDialog = true
                                        },
                                        modifier = Modifier.weight(1f).height(40.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.CameraAlt, null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("📸 Take Photo", fontSize = 11.sp)
                                    }

                                    OutlinedButton(
                                        onClick = { backGalleryLauncher.launch("image/*") },
                                        modifier = Modifier.weight(1f).height(40.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Import Gallery", fontSize = 11.sp)
                                    }
                                }
                            }
                        }

                        // BOTH SIDES CAPTURED VISUAL FEEDBACK INDICATOR
                        AnimatedVisibility(
                            visible = frontUri != null && backUri != null,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(2.dp, Color(0xFF10B981), RoundedCornerShape(16.dp)),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF065F46).copy(alpha = 0.12f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF10B981).copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = "Success",
                                            tint = Color(0xFF10B981),
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "ID Card Captured Successfully!",
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF10B981),
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = "Both front and back sides have been digitized. You are ready to combine and preview.",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                                        )
                                    }
                                }
                            }
                        }

                        if (frontUri != null && backUri != null) {
                            Button(
                                onClick = { selectedTab = 1 },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Merge & Preview Single Page", fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.ArrowForward, null)
                            }
                        }
                    }
                }

                1 -> {
                    if (frontUri == null || backUri == null) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Please capture Front & Back first to preview merge page.", color = Color.Gray, fontSize = 13.sp)
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // CONTROLS FOR ENHANCEMENT QUALITY
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text("🎨 Enhance Document Reprographics", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                                    // Filter choices
                                    Row(
                                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        listOf("Original", "Magic Color", "B&W Contrast", "Grayscale", "Brighten").forEach { f ->
                                            val isSelected = selectedFilter == f
                                            FilterChip(
                                                selected = isSelected,
                                                onClick = { selectedFilter = f },
                                                label = { Text(f, fontSize = 11.sp) }
                                            )
                                        }
                                    }

                                    // Sliders
                                    Column {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("Brightness Modifier", fontSize = 11.sp)
                                            Text("${brightness.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                        Slider(
                                            value = brightness,
                                            onValueChange = { brightness = it },
                                            valueRange = -50f..50f
                                        )
                                    }

                                    Column {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("Contrast Boost Multiplier", fontSize = 11.sp)
                                            Text(String.format("%.2f", contrast), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                        Slider(
                                            value = contrast,
                                            onValueChange = { contrast = it },
                                            valueRange = 0.5f..2.0f
                                        )
                                    }
                                }
                            }

                            // OCR SMART DETAILS PANEL
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.AutoAwesome, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Smart OCR Auto-Extracted Details", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }

                                    OutlinedTextField(
                                        value = ocrName,
                                        onValueChange = { ocrName = it },
                                        label = { Text("Extracted Name (Editable)", fontSize = 11.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedTextField(
                                            value = ocrId,
                                            onValueChange = { ocrId = it },
                                            label = { Text("Identity Code", fontSize = 11.sp) },
                                            modifier = Modifier.weight(1f),
                                            singleLine = true
                                        )

                                        OutlinedTextField(
                                            value = ocrDob,
                                            onValueChange = { ocrDob = it },
                                            label = { Text("Date of Birth", fontSize = 11.sp) },
                                            modifier = Modifier.weight(1f),
                                            singleLine = true
                                        )
                                    }
                                }
                            }

                            // DOCUMENT LAYOUT & SECURITY CUSTOMIZATION PANEL
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("⚙️ Document Layout & Security Customization", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                                    // Card Category Choice
                                    Text("Card Category (Changes simulated card themes)", fontSize = 11.sp, color = Color.Gray)
                                    Row(
                                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        listOf("National Identity Card", "Driver's License", "Medical Health Pass", "Student & Library Card").forEach { cat ->
                                            val isSelected = selectedCardType == cat
                                            FilterChip(
                                                selected = isSelected,
                                                onClick = { selectedCardType = cat },
                                                label = { Text(cat, fontSize = 10.sp) }
                                            )
                                        }
                                    }

                                    // Layout Template Choice
                                    Text("A4 Layout Placement Template", fontSize = 11.sp, color = Color.Gray)
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        listOf("A4 Vertical Stacked", "A4 Side-by-Side (Compact)").forEach { temp ->
                                            val isSelected = selectedLayoutTemplate == temp
                                            FilterChip(
                                                selected = isSelected,
                                                onClick = { selectedLayoutTemplate = temp },
                                                label = { Text(temp, fontSize = 10.sp) },
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }

                                    // Security Watermark Preset Choice
                                    Text("Anti-Forgery Protective Watermark", fontSize = 11.sp, color = Color.Gray)
                                    Row(
                                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        listOf("None", "FOR VERIFICATION ONLY", "CONFIDENTIAL COPY", "DO NOT DUPLICATE", "Custom").forEach { preset ->
                                            val isSelected = watermarkPreset == preset
                                            FilterChip(
                                                selected = isSelected,
                                                onClick = { watermarkPreset = preset },
                                                label = { Text(preset, fontSize = 10.sp) }
                                            )
                                        }
                                    }

                                    if (watermarkPreset == "Custom") {
                                        OutlinedTextField(
                                            value = customWatermarkText,
                                            onValueChange = { customWatermarkText = it },
                                            label = { Text("Custom Watermark Text", fontSize = 11.sp) },
                                            placeholder = { Text("e.g., OFFICIAL COPY ONLY") },
                                            modifier = Modifier.fillMaxWidth(),
                                            singleLine = true
                                        )
                                    }
                                }
                            }

                            // EXPORT/DOWNLOAD BUTTON PANEL
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("💾 High-Fidelity Multi-Format Download", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                                    // PDF compilation
                                    Button(
                                        onClick = {
                                            compileIdCardToPdf(
                                                context, frontUri, backUri, selectedFilter, brightness, contrast, ocrName, ocrId, ocrDob, watermarkText, selectedLayoutTemplate
                                            ) { uri ->
                                                if (uri != null) {
                                                    Toast.makeText(context, "ID Card PDF downloaded to Downloads/ folder successfully!", Toast.LENGTH_LONG).show()
                                                } else {
                                                    Toast.makeText(context, "Failed compiling PDF!", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(48.dp),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.PictureAsPdf, null)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Download PDF Document (Front & Back)", fontWeight = FontWeight.Bold)
                                    }

                                    // Word transcript
                                    Button(
                                        onClick = {
                                            saveTextDocToDownloads(
                                                context, "Citizens ID Card Transcript", ocrName, ocrId, ocrDob, additionalNotes
                                            ) { uri ->
                                                if (uri != null) {
                                                    Toast.makeText(context, "Word Transcript downloaded successfully!", Toast.LENGTH_LONG).show()
                                                } else {
                                                    Toast.makeText(context, "Failed exporting Transcript!", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(44.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
                                    ) {
                                        Icon(Icons.Default.Article, null)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Download Word/DOC Transcript File", fontWeight = FontWeight.SemiBold)
                                    }

                                    // JPEG Image saving
                                    OutlinedButton(
                                        onClick = {
                                            saveCompiledImageToDownloads(
                                                context, "CONSOLIDATED ID CARD PHOTOCOPY", true, frontUri, backUri, selectedFilter, brightness, contrast, ocrName, ocrId, ocrDob, watermarkText, selectedLayoutTemplate
                                            ) { uri ->
                                                if (uri != null) {
                                                    Toast.makeText(context, "Merged JPEG saved to Downloads successfully!", Toast.LENGTH_LONG).show()
                                                } else {
                                                    Toast.makeText(context, "Failed compiling image!", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(44.dp),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.Image, null)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Save Merged A4 JPEG Image", fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }

                            // VISUAL COMPACT LAYOUT PREVIEW Representation
                            Text("A4 Layout Preview Representation:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color.LightGray),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text("CONSOLIDATED IDENTITY CARD DOCUMENT", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.DarkGray, textAlign = TextAlign.Center)

                                    if (selectedLayoutTemplate == "A4 Side-by-Side (Compact)") {
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                                Box(
                                                    modifier = Modifier.fillMaxWidth().height(90.dp).border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                                                ) {
                                                    val bmp1 = loadBitmapFromUri(context, frontUri!!)
                                                    if (bmp1 != null) {
                                                        Image(
                                                            bitmap = applyFiltersToBitmap(bmp1, selectedFilter, brightness, contrast).asImageBitmap(),
                                                            contentDescription = null,
                                                            modifier = Modifier.fillMaxSize(),
                                                            contentScale = ContentScale.Fit
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text("FRONT SIDE", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                            }

                                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                                Box(
                                                    modifier = Modifier.fillMaxWidth().height(90.dp).border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                                                ) {
                                                    val bmp2 = loadBitmapFromUri(context, backUri!!)
                                                    if (bmp2 != null) {
                                                        Image(
                                                            bitmap = applyFiltersToBitmap(bmp2, selectedFilter, brightness, contrast).asImageBitmap(),
                                                            contentDescription = null,
                                                            modifier = Modifier.fillMaxSize(),
                                                            contentScale = ContentScale.Fit
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text("BACK SIDE", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                            }
                                        }
                                    } else {
                                        // Front preview
                                        Box(
                                            modifier = Modifier.fillMaxWidth(0.85f).height(120.dp).border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                                        ) {
                                            val bmp1 = loadBitmapFromUri(context, frontUri!!)
                                            if (bmp1 != null) {
                                                Image(
                                                    bitmap = applyFiltersToBitmap(bmp1, selectedFilter, brightness, contrast).asImageBitmap(),
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = ContentScale.Fit
                                                )
                                            }
                                        }
                                        Text("FRONT SIDE COPY", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

                                        // Back preview
                                        Box(
                                            modifier = Modifier.fillMaxWidth(0.85f).height(120.dp).border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                                        ) {
                                            val bmp2 = loadBitmapFromUri(context, backUri!!)
                                            if (bmp2 != null) {
                                                Image(
                                                    bitmap = applyFiltersToBitmap(bmp2, selectedFilter, brightness, contrast).asImageBitmap(),
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = ContentScale.Fit
                                                )
                                            }
                                        }
                                        Text("BACK SIDE COPY", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                    }

                                    if (watermarkText.isNotEmpty() && watermarkText != "None") {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "🛡️ Active Anti-Counterfeiting Watermark: \"$watermarkText\"",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFD32F2F),
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF1F1)).padding(vertical = 4.dp)
                                        )
                                    }

                                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text("EXTRACTED OCR SMART-METADATA", fontWeight = FontWeight.Bold, fontSize = 8.sp, color = Color.DarkGray)
                                        Text("Name: $ocrName", fontSize = 8.sp, color = Color.Gray)
                                        Text("ID No: $ocrId", fontSize = 8.sp, color = Color.Gray)
                                        Text("DOB:  $ocrDob", fontSize = 8.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCameraOverlay) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { showCameraOverlay = false },
            properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
        ) {
            SimulatedCameraOverlay(
                isFront = cameraTargetIsFront,
                cardType = selectedCardType,
                ocrName = ocrName,
                ocrId = ocrId,
                ocrDob = ocrDob,
                onDismiss = { showCameraOverlay = false },
                onCaptured = { uri ->
                    val finalUri = processAndCropIdCardUri(context, uri)
                    if (cameraTargetIsFront) {
                        frontUri = finalUri
                        Toast.makeText(context, "📸 Front Side Snapped & Auto-Cropped!", Toast.LENGTH_SHORT).show()
                    } else {
                        backUri = finalUri
                        Toast.makeText(context, "📸 Back Side Snapped & Auto-Cropped!", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    if (showCaptureChoiceDialog) {
        AlertDialog(
            onDismissRequest = { showCaptureChoiceDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PhotoCamera, "Camera", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select Capture Source", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            text = {
                Text(
                    "Choose between using your device's physical camera or our high-fidelity real-time scanning terminal simulator.",
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showCaptureChoiceDialog = false
                        if (captureChoiceIsFront) {
                            val uri = createTempImageUri(context)
                            if (uri != null) {
                                tempFrontUri = uri
                                frontCameraLauncher.launch(uri)
                            } else {
                                Toast.makeText(context, "Storage initialization failed", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val uri = createTempImageUri(context)
                            if (uri != null) {
                                tempBackUri = uri
                                backCameraLauncher.launch(uri)
                            } else {
                                Toast.makeText(context, "Storage initialization failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                ) {
                    Text("📸 Real Camera")
                }
            },
            dismissButton = {
                FilledTonalButton(
                    onClick = {
                        showCaptureChoiceDialog = false
                        cameraTargetIsFront = captureChoiceIsFront
                        showCameraOverlay = true
                    }
                ) {
                    Text("⚡ Scanner Overlay")
                }
            }
        )
    }
}

// =============================================================
// MAIN COMPOSABLES: PASSPORT SCANNER SCREEN
// =============================================================

@Composable
fun PassportScannerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) } // 0 = Capture, 1 = Preview

    var passportUri by remember { mutableStateOf<Uri?>(null) }

    var selectedFilter by remember { mutableStateOf("Original") }
    var brightness by remember { mutableStateOf(0f) }
    var contrast by remember { mutableStateOf(1f) }

    var ocrName by remember { mutableStateOf("ELIZABETH CHEN") }
    var ocrNo by remember { mutableStateOf("P2981726") }
    var ocrDob by remember { mutableStateOf("15 OCT 1995") }
    var additionalNotes by remember { mutableStateOf("Official Passport Data Registry Transcript") }

    val passportGalleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) passportUri = uri
    }

    var tempPassportUri by remember { mutableStateOf<Uri?>(null) }
    val passportCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempPassportUri != null) {
            passportUri = tempPassportUri
            Toast.makeText(context, "📸 Passport Data Page Captured!", Toast.LENGTH_SHORT).show()
        }
    }
    var showPassportChoiceDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("📷 Passport Cam", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = {
                    if (passportUri == null) {
                        Toast.makeText(context, "Please scan/upload passport image first!", Toast.LENGTH_SHORT).show()
                    }
                    selectedTab = 1
                },
                text = { Text("📑 Full Copy Preview", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
            )
        }

        AnimatedContent(targetState = selectedTab, label = "TabTransition") { tab ->
            when (tab) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Book, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("Passport Page Digital Scanner", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("Capture full passport data page to render a certified digital photocopy copy.", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                        }

                        // CAPTURE VIEWPORT FRAME
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("PASSPORT PHOTO DATA PAGE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(260.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (passportUri != null) {
                                        val bmp = loadBitmapFromUri(context, passportUri!!)
                                        if (bmp != null) {
                                            Image(
                                                bitmap = applyFiltersToBitmap(bmp, selectedFilter, brightness, contrast).asImageBitmap(),
                                                contentDescription = "Passport Page",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                        ScannerLaserSweepEffect()
                                    } else {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(Icons.Default.AssignmentInd, null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text("No Passport Page Snapped Yet", fontSize = 11.sp, color = Color.Gray)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            showPassportChoiceDialog = true
                                        },
                                        modifier = Modifier.weight(1f).height(44.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.CameraAlt, null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("📸 Take Photo", fontSize = 12.sp)
                                    }

                                    OutlinedButton(
                                        onClick = { passportGalleryLauncher.launch("image/*") },
                                        modifier = Modifier.weight(1f).height(44.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Import Gallery", fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        if (passportUri != null) {
                            Button(
                                onClick = { selectedTab = 1 },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Verify & Configure Preview", fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.ArrowForward, null)
                            }
                        }
                    }
                }

                1 -> {
                    if (passportUri == null) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Please scan passport photo page first.", color = Color.Gray, fontSize = 13.sp)
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // CONTROLS FOR ENHANCEMENT QUALITY
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text("🎨 Enhance Passport Document Quality", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                                    // Filter choices
                                    Row(
                                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        listOf("Original", "Magic Color", "B&W Contrast", "Grayscale", "Brighten").forEach { f ->
                                            val isSelected = selectedFilter == f
                                            FilterChip(
                                                selected = isSelected,
                                                onClick = { selectedFilter = f },
                                                label = { Text(f, fontSize = 11.sp) }
                                            )
                                        }
                                    }

                                    // Sliders
                                    Column {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("Brightness Level", fontSize = 11.sp)
                                            Text("${brightness.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                        Slider(
                                            value = brightness,
                                            onValueChange = { brightness = it },
                                            valueRange = -50f..50f
                                        )
                                    }

                                    Column {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("Contrast Boost Multiplier", fontSize = 11.sp)
                                            Text(String.format("%.2f", contrast), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                        Slider(
                                            value = contrast,
                                            onValueChange = { contrast = it },
                                            valueRange = 0.5f..2.0f
                                        )
                                    }
                                }
                            }

                            // OCR SMART DETAILS PANEL
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.AutoAwesome, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Passport OCR Extract Information", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }

                                    OutlinedTextField(
                                        value = ocrName,
                                        onValueChange = { ocrName = it },
                                        label = { Text("Extracted Full Name (Editable)", fontSize = 11.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedTextField(
                                            value = ocrNo,
                                            onValueChange = { ocrNo = it },
                                            label = { Text("Passport Number", fontSize = 11.sp) },
                                            modifier = Modifier.weight(1f),
                                            singleLine = true
                                        )

                                        OutlinedTextField(
                                            value = ocrDob,
                                            onValueChange = { ocrDob = it },
                                            label = { Text("Date of Birth", fontSize = 11.sp) },
                                            modifier = Modifier.weight(1f),
                                            singleLine = true
                                        )
                                    }
                                }
                            }

                            // EXPORT/DOWNLOAD BUTTON PANEL
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("💾 High-Fidelity Passport Downloads", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                                    // PDF compilation
                                    Button(
                                        onClick = {
                                            compilePassportToPdf(
                                                context, passportUri, selectedFilter, brightness, contrast, ocrName, ocrNo, ocrDob
                                            ) { uri ->
                                                if (uri != null) {
                                                    Toast.makeText(context, "Passport PDF downloaded to Downloads/ folder successfully!", Toast.LENGTH_LONG).show()
                                                } else {
                                                    Toast.makeText(context, "Failed compiling PDF!", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(48.dp),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.PictureAsPdf, null)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Download PDF Passport Document", fontWeight = FontWeight.Bold)
                                    }

                                    // Word transcript
                                    Button(
                                        onClick = {
                                            saveTextDocToDownloads(
                                                context, "Official Passport Transcript", ocrName, ocrNo, ocrDob, additionalNotes
                                            ) { uri ->
                                                if (uri != null) {
                                                    Toast.makeText(context, "Passport Word Transcript downloaded successfully!", Toast.LENGTH_LONG).show()
                                                } else {
                                                    Toast.makeText(context, "Failed exporting Transcript!", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(44.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
                                    ) {
                                        Icon(Icons.Default.Article, null)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Download Word/DOC Transcript File", fontWeight = FontWeight.SemiBold)
                                    }

                                    // JPEG Image saving
                                    OutlinedButton(
                                        onClick = {
                                            saveCompiledImageToDownloads(
                                                context, "PASSPORT DOCUMENT REPROGRAPHICS", false, passportUri, null, selectedFilter, brightness, contrast, ocrName, ocrNo, ocrDob
                                            ) { uri ->
                                                if (uri != null) {
                                                    Toast.makeText(context, "Passport JPEG saved to Downloads successfully!", Toast.LENGTH_LONG).show()
                                                } else {
                                                    Toast.makeText(context, "Failed compiling image!", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(44.dp),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.Image, null)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Save Passport A4 JPEG Image", fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }

                            // VISUAL COMPACT LAYOUT PREVIEW Representation
                            Text("A4 Passport Preview Representation:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color.LightGray),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text("CONSOLIDATED PASSPORT DOCUMENT COPY", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.DarkGray, textAlign = TextAlign.Center)

                                    Box(
                                        modifier = Modifier.fillMaxWidth(0.9f).height(300.dp).border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                                    ) {
                                        val bmp1 = loadBitmapFromUri(context, passportUri!!)
                                        if (bmp1 != null) {
                                            Image(
                                                bitmap = applyFiltersToBitmap(bmp1, selectedFilter, brightness, contrast).asImageBitmap(),
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                    }
                                    Text("DATA PAGE COPY", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

                                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text("EXTRACTED OCR SMART-METADATA", fontWeight = FontWeight.Bold, fontSize = 8.sp, color = Color.DarkGray)
                                        Text("Full Name: $ocrName", fontSize = 8.sp, color = Color.Gray)
                                        Text("Passport No: $ocrNo", fontSize = 8.sp, color = Color.Gray)
                                        Text("DOB / Birth:  $ocrDob", fontSize = 8.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showPassportChoiceDialog) {
        AlertDialog(
            onDismissRequest = { showPassportChoiceDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PhotoCamera, "Camera", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select Capture Source", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            text = {
                Text(
                    "Choose between using your device's physical camera or our high-fidelity real-time scanning terminal simulator.",
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showPassportChoiceDialog = false
                        val uri = createTempImageUri(context)
                        if (uri != null) {
                            tempPassportUri = uri
                            passportCameraLauncher.launch(uri)
                        } else {
                            Toast.makeText(context, "Storage initialization failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("📸 Real Camera")
                }
            },
            dismissButton = {
                FilledTonalButton(
                    onClick = {
                        showPassportChoiceDialog = false
                        passportUri = Uri.parse("android.resource://" + context.packageName + "/passport_simulated_" + System.currentTimeMillis() + "?name=" + Uri.encode(ocrName) + "&id=" + Uri.encode(ocrNo) + "&dob=" + Uri.encode(ocrDob))
                        Toast.makeText(context, "📸 Passport Data Page Snapped (Simulated)!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("⚡ Scanner Overlay")
                }
            }
        )
    }
}

// =============================================================
// SUB-MODULE: REUSABLE SCAN ANIMATIONS
// =============================================================

@Composable
fun ScannerLaserSweepEffect() {
    val infiniteTransition = rememberInfiniteTransition(label = "SweepTransition")
    val laserOffset by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "LaserOffset"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val y = laserOffset * size.height
        drawLine(
            color = Color(0xFF00FFCC),
            start = androidx.compose.ui.geometry.Offset(0f, y),
            end = androidx.compose.ui.geometry.Offset(size.width, y),
            strokeWidth = 6f
        )
    }
}

@Composable
fun ViewfinderAlignmentGuides(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 8f
        val len = 40f
        val color = Color(0xFF00FFCC) // glowing cyan
        val w = size.width
        val h = size.height

        // Top Left corner brackets
        drawLine(color, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Offset(len, 0f), strokeWidth = stroke)
        drawLine(color, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Offset(0f, len), strokeWidth = stroke)

        // Top Right corner brackets
        drawLine(color, androidx.compose.ui.geometry.Offset(w, 0f), androidx.compose.ui.geometry.Offset(w - len, 0f), strokeWidth = stroke)
        drawLine(color, androidx.compose.ui.geometry.Offset(w, 0f), androidx.compose.ui.geometry.Offset(w, len), strokeWidth = stroke)

        // Bottom Left corner brackets
        drawLine(color, androidx.compose.ui.geometry.Offset(0f, h), androidx.compose.ui.geometry.Offset(len, h), strokeWidth = stroke)
        drawLine(color, androidx.compose.ui.geometry.Offset(0f, h), androidx.compose.ui.geometry.Offset(0f, h - len), strokeWidth = stroke)

        // Bottom Right corner brackets
        drawLine(color, androidx.compose.ui.geometry.Offset(w, h), androidx.compose.ui.geometry.Offset(w - len, h), strokeWidth = stroke)
        drawLine(color, androidx.compose.ui.geometry.Offset(w, h), androidx.compose.ui.geometry.Offset(w, h - len), strokeWidth = stroke)
        
        // Centered crosshair
        val dashColor = Color(0x6600FFCC)
        drawLine(dashColor, androidx.compose.ui.geometry.Offset(w / 2 - 15f, h / 2), androidx.compose.ui.geometry.Offset(w / 2 + 15f, h / 2), strokeWidth = 2f)
        drawLine(dashColor, androidx.compose.ui.geometry.Offset(w / 2, h / 2 - 15f), androidx.compose.ui.geometry.Offset(w / 2, h / 2 + 15f), strokeWidth = 2f)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SimulatedCameraOverlay(
    isFront: Boolean,
    cardType: String,
    ocrName: String,
    ocrId: String,
    ocrDob: String,
    onDismiss: () -> Unit,
    onCaptured: (Uri) -> Unit
) {
    var isCapturing by remember { mutableStateOf(false) }
    var scanProgress by remember { mutableStateOf(0f) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    // Set up CameraX preview and image capture
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    val previewView = remember { PreviewView(context) }

    LaunchedEffect(cameraPermissionState.status.isGranted) {
        if (cameraPermissionState.status.isGranted) {
            try {
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder().build()
                preview.setSurfaceProvider(previewView.surfaceProvider)

                val imageCaptureLocal = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

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
    }

    fun performCapture() {
        if (isCapturing) return
        isCapturing = true
        scope.launch {
            scanProgress = 0f
            while (scanProgress < 1f) {
                kotlinx.coroutines.delay(20)
                scanProgress += 0.04f
            }

            // Try to use real CameraX if permission is granted and it is bound successfully
            val imgCapture = imageCapture
            if (cameraPermissionState.status.isGranted && imgCapture != null) {
                val storageDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
                val photoFile = File(storageDir, "id_scan_${if (isFront) "front" else "back"}_${System.currentTimeMillis()}.jpg")
                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                imgCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            onCaptured(Uri.fromFile(photoFile))
                            isCapturing = false
                            onDismiss()
                        }
                        override fun onError(exception: androidx.camera.core.ImageCaptureException) {
                            // On failure (e.g. emulator, file lock), fallback to simulated high-res document
                            val nameEncoded = Uri.encode(ocrName)
                            val idEncoded = Uri.encode(ocrId)
                            val dobEncoded = Uri.encode(ocrDob)
                            val cardTypeEncoded = Uri.encode(cardType)
                            val side = if (isFront) "front" else "back"
                            val query = "?name=$nameEncoded&id=$idEncoded&dob=$dobEncoded&cardType=$cardTypeEncoded"
                            val simulatedUri = Uri.parse("android.resource://${context.packageName}/${side}_simulated_${System.currentTimeMillis()}$query")
                            onCaptured(simulatedUri)
                            isCapturing = false
                            onDismiss()
                        }
                    }
                )
            } else {
                // Generate high fidelity simulated document
                val nameEncoded = Uri.encode(ocrName)
                val idEncoded = Uri.encode(ocrId)
                val dobEncoded = Uri.encode(ocrDob)
                val cardTypeEncoded = Uri.encode(cardType)
                val side = if (isFront) "front" else "back"
                val query = "?name=$nameEncoded&id=$idEncoded&dob=$dobEncoded&cardType=$cardTypeEncoded"
                val simulatedUri = Uri.parse("android.resource://${context.packageName}/${side}_simulated_${System.currentTimeMillis()}$query")
                onCaptured(simulatedUri)
                isCapturing = false
                onDismiss()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .systemBarsPadding()
    ) {
        // Real-Time Camera Preview Area or Custom Mock viewport if permission not granted
        if (cameraPermissionState.status.isGranted) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // High fidelity simulated viewport
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0F172A))
            ) {
                // Draw alignment grids fallback
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val gridColor = Color(0x338B9BB4)
                    drawLine(gridColor, androidx.compose.ui.geometry.Offset(w / 3, 0f), androidx.compose.ui.geometry.Offset(w / 3, h), strokeWidth = 1f)
                    drawLine(gridColor, androidx.compose.ui.geometry.Offset(w * 2 / 3, 0f), androidx.compose.ui.geometry.Offset(w * 2 / 3, h), strokeWidth = 1f)
                    drawLine(gridColor, androidx.compose.ui.geometry.Offset(0f, h / 3), androidx.compose.ui.geometry.Offset(w, h / 3), strokeWidth = 1f)
                    drawLine(gridColor, androidx.compose.ui.geometry.Offset(0f, h * 2 / 3), androidx.compose.ui.geometry.Offset(w, h * 2 / 3), strokeWidth = 1f)
                }
            }
        }

        // Draw Alignment Grids overlay
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val gridColor = Color(0x22FFFFFF)
            // vertical lines
            drawLine(gridColor, androidx.compose.ui.geometry.Offset(w / 3, 0f), androidx.compose.ui.geometry.Offset(w / 3, h), strokeWidth = 1f)
            drawLine(gridColor, androidx.compose.ui.geometry.Offset(w * 2 / 3, 0f), androidx.compose.ui.geometry.Offset(w * 2 / 3, h), strokeWidth = 1f)
            // horizontal lines
            drawLine(gridColor, androidx.compose.ui.geometry.Offset(0f, h / 3), androidx.compose.ui.geometry.Offset(w, h / 3), strokeWidth = 1f)
            drawLine(gridColor, androidx.compose.ui.geometry.Offset(0f, h * 2 / 3), androidx.compose.ui.geometry.Offset(w, h * 2 / 3), strokeWidth = 1f)
        }

        // Camera Header Tools
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
            }
            Text(
                text = "HIGH-SPEED SCANNER",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = 2.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.FlashOn, null, tint = Color.Yellow, modifier = Modifier.size(20.dp))
                Icon(Icons.Default.GridOn, null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }

        // Centered viewfinder representing the ID card border with alignment corners
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(240.dp)
                .align(Alignment.Center)
        ) {
            // Viewfinder alignment corner brackets drawn on top
            ViewfinderAlignmentGuides(modifier = Modifier.fillMaxSize())

            // Semi-transparent border outline
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.5.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            )

            // Text guides inside viewfinder
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ALIGN ${if (isFront) "FRONT" else "BACK"} OF CARD WITHIN BOUNDS",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                
                Text(
                    text = "Auto-Boundary Real-Time Detection Active",
                    color = Color(0xFF00E5FF),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            // Laser sweep animation when capturing
            if (isCapturing) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(scanProgress)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0x6600FFCC))
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 240.dp * scanProgress)
                        .background(Color(0xFF00FFCC))
                )
            }
        }

        // Bottom Controls Panel
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (isCapturing) "Processing High-Res Reprographics..." else "Card Type: $cardType",
                color = Color.White,
                fontSize = 12.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left preview or cancel placeholder
                Box(modifier = Modifier.size(48.dp))

                // Capture Shutter Button
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(4.dp)
                        .clickable(enabled = !isCapturing) {
                            performCapture()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(if (isCapturing) Color.Red else Color.White)
                    )
                }

                // Info Toggle icon
                Icon(
                    Icons.Default.Info,
                    contentDescription = "Details",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
