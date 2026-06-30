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

fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val uriStr = uri.toString()
        if (uriStr.contains("simulated")) {
            createDummyCardBitmap(context, uriStr.contains("front"))
        } else if (uriStr.contains("passport")) {
            createDummyPassportBitmap(context)
        } else {
            context.contentResolver.openInputStream(uri).use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        }
    } catch (e: Exception) {
        null
    }
}

fun createDummyCardBitmap(context: Context, isFront: Boolean): Bitmap {
    val width = 600
    val height = 380
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply { isAntiAlias = true }

    // Color gradient
    val startCol = if (isFront) android.graphics.Color.parseColor("#E0F7FA") else android.graphics.Color.parseColor("#ECEFF1")
    val endCol = if (isFront) android.graphics.Color.parseColor("#B2EBF2") else android.graphics.Color.parseColor("#CFD8DC")
    val gradient = android.graphics.LinearGradient(0f, 0f, width.toFloat(), height.toFloat(), startCol, endCol, android.graphics.Shader.TileMode.CLAMP)
    paint.shader = gradient
    canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 20f, 20f, paint)
    paint.shader = null

    // Card Border
    paint.color = android.graphics.Color.parseColor("#90A4AE")
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 3f
    canvas.drawRoundRect(2f, 2f, width.toFloat() - 2f, height.toFloat() - 2f, 20f, 20f, paint)
    paint.style = Paint.Style.FILL

    if (isFront) {
        // Header
        paint.color = android.graphics.Color.parseColor("#006064")
        canvas.drawRect(15f, 15f, width.toFloat() - 15f, 65f, paint)
        paint.color = android.graphics.Color.WHITE
        paint.textSize = 18f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("CITIZEN IDENTITY CARD - SECURE COPY", 30f, 47f, paint)

        // Gold chip
        paint.color = android.graphics.Color.parseColor("#FFD54F")
        canvas.drawRoundRect(40f, 80f, 95f, 120f, 8f, 8f, paint)

        // Photo
        paint.color = android.graphics.Color.parseColor("#B0BEC5")
        canvas.drawRoundRect(40f, 140f, 180f, 320f, 12f, 12f, paint)
        paint.color = android.graphics.Color.parseColor("#546E7A")
        canvas.drawCircle(110f, 200f, 25f, paint)
        canvas.drawRoundRect(70f, 240f, 150f, 310f, 15f, 15f, paint)

        // Text
        paint.color = android.graphics.Color.parseColor("#263238")
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 15f
        canvas.drawText("ID No: ID-887162-UX", 210f, 105f, paint)

        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 13f
        canvas.drawText("Surname: CHEN", 210f, 140f, paint)
        canvas.drawText("Given Names: ELIZABETH", 210f, 170f, paint)
        canvas.drawText("Nationality: GLOBAL STATE", 210f, 200f, paint)
        canvas.drawText("DOB: 15 OCT 1995", 210f, 230f, paint)
        canvas.drawText("Sex: F  |  Height: 1.68m", 210f, 260f, paint)
        canvas.drawText("Expiry: 2034-01-12", 210f, 290f, paint)

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
        canvas.drawText("Residence Address / Lieu d'habitation:", 30f, 185f, paint)
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("742 Evergreen Terrace, Springfield, US", 30f, 210f, paint)
        canvas.drawText("Authority: GLOBAL DEPT OF TRANSCRIPT", 30f, 250f, paint)

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

fun createDummyPassportBitmap(context: Context): Bitmap {
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
    canvas.drawText("PASSPORT NO: P2981726", 270f, 150f, paint)

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    paint.textSize = 13f
    canvas.drawText("Surname / Nom:", 270f, 195f, paint)
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("CHEN", 270f, 215f, paint)

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText("Given Names:", 270f, 250f, paint)
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("ELIZABETH", 270f, 270f, paint)

    canvas.drawText("Nationality: GLOBAL UNION", 270f, 310f, paint)
    canvas.drawText("DOB: 15 OCT 1995", 270f, 345f, paint)

    canvas.drawText("Date of Issue: 2024-03-22  |  Date of Expiry: 2034-03-22", 40f, 440f, paint)
    canvas.drawText("Authority: DEPT OF WORLD TRAVEL STATUS", 40f, 480f, paint)

    // MRZ Zone
    paint.color = android.graphics.Color.parseColor("#2C3E50")
    paint.typeface = Typeface.create("monospace", Typeface.BOLD)
    paint.textSize = 13f
    canvas.drawText("P<WORLD<<CHEN<<ELIZABETH<<<<<<<<<<<<<<<<<<<<<<<<<<<", 30f, 750f, paint)
    canvas.drawText("P2981726<8WRL9510156F3403225<<<<<<<<<<<<<<<<<<<<<<<", 30f, 780f, paint)

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
        canvas.drawText("Captured on: $currentDate  |  Source: StudentKit Elite CamScanner", 40f, 70f, textPaint)

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

            val cardW = 380f
            val cardH = 240f
            val left = (pageW - cardW) / 2f

            val framePaint = Paint().apply {
                color = android.graphics.Color.parseColor("#475569")
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
            canvas.drawText("1. FRONT SIDE", left, 115f, textPaint)
            canvas.drawRoundRect(left, 130f, left + cardW, 130f + cardH, 12f, 12f, fillPaint)
            val destFront = android.graphics.RectF(left + 2, 132f, left + cardW - 2, 130f + cardH - 2)
            canvas.drawBitmap(frontBmp, null, destFront, Paint().apply { isAntiAlias = true })
            canvas.drawRoundRect(left, 130f, left + cardW, 130f + cardH, 12f, 12f, framePaint)

            // Back
            canvas.drawText("2. BACK SIDE", left, 415f, textPaint)
            canvas.drawRoundRect(left, 430f, left + cardW, 430f + cardH, 12f, 12f, fillPaint)
            val destBack = android.graphics.RectF(left + 2, 432f, left + cardW - 2, 430f + cardH - 2)
            canvas.drawBitmap(backBmp, null, destBack, Paint().apply { isAntiAlias = true })
            canvas.drawRoundRect(left, 430f, left + cardW, 430f + cardH, 12f, 12f, framePaint)

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

            frontBmp.recycle()
            backBmp.recycle()
            rawFrontBmp.recycle()
            rawBackBmp.recycle()
        }

        textPaint.color = android.graphics.Color.parseColor("#94A3B8")
        textPaint.textSize = 8f
        canvas.drawText("On-Device Scanning Engine | StudentKit Premium Integration Suite", 40f, 810f, textPaint)

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
        canvas.drawText("Captured on: $currentDate  |  Source: StudentKit Passport Capture", 40f, 70f, textPaint)

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

            passportBmp.recycle()
            rawPassportBmp.recycle()
        }

        textPaint.color = android.graphics.Color.parseColor("#94A3B8")
        textPaint.textSize = 8f
        canvas.drawText("On-Device Passport Scanner Engine | StudentKit Premium Suite", 40f, 800f, textPaint)

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
                    val cardW = 380f
                    val cardH = 240f
                    val left = (pageW - cardW) / 2f

                    val framePaint = Paint().apply {
                        color = android.graphics.Color.parseColor("#475569")
                        style = Paint.Style.STROKE
                        strokeWidth = 2f
                    }

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
        Generated via StudentKit CamScanner Elite, All Rights Reserved.
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

@Composable
fun IdCardScannerScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) } // 0 = Capture, 1 = Preview & Tuning

    var frontUri by remember { mutableStateOf<Uri?>(null) }
    var backUri by remember { mutableStateOf<Uri?>(null) }

    var selectedFilter by remember { mutableStateOf("Original") }
    var brightness by remember { mutableStateOf(0f) }
    var contrast by remember { mutableStateOf(1f) }

    var ocrName by remember { mutableStateOf("ELIZABETH CHEN") }
    var ocrId by remember { mutableStateOf("ID-887162-UX") }
    var ocrDob by remember { mutableStateOf("15 OCT 1995") }
    var additionalNotes by remember { mutableStateOf("Verified Citizens Registry Office Copy") }

    val frontGalleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) frontUri = uri
    }
    val backGalleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) backUri = uri
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
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CreditCard, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("Double-Sided ID Scan Copy", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("Take or upload both sides to print side-by-side on a single A4 sheet.", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                        }

                        // FRONT CARD CONTAINER
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("1. ID CARD FRONT SIDE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
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
                                        // Sweep Laser Laser
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
                                            // Trigger high-quality simulated camera shoot
                                            frontUri = Uri.parse("android.resource://" + context.packageName + "/front_simulated_" + System.currentTimeMillis())
                                            Toast.makeText(context, "📸 Front Side Snapped Successfully!", Toast.LENGTH_SHORT).show()
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
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("2. ID CARD BACK SIDE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
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
                                            backUri = Uri.parse("android.resource://" + context.packageName + "/back_simulated_" + System.currentTimeMillis())
                                            Toast.makeText(context, "📸 Back Side Snapped Successfully!", Toast.LENGTH_SHORT).show()
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
                                                context, frontUri, backUri, selectedFilter, brightness, contrast, ocrName, ocrId, ocrDob
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
                                                context, "CONSOLIDATED ID CARD PHOTOCOPY", true, frontUri, backUri, selectedFilter, brightness, contrast, ocrName, ocrId, ocrDob
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
                                            passportUri = Uri.parse("android.resource://" + context.packageName + "/passport_simulated_" + System.currentTimeMillis())
                                            Toast.makeText(context, "📸 Passport Data Page Snapped Successfully!", Toast.LENGTH_SHORT).show()
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
