package com.drtahir.studentkit.data

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object DutyRotaDocxExporter {

    private fun escapeXml(text: String): String {
        return text.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }

    /**
     * Resolves the logo bitmap either from custom URI/path or built-in preset.
     */
    fun resolveLogoBitmap(context: Context, rota: DutyRota): Bitmap {
        if (!rota.customLogoPath.isNullOrBlank()) {
            try {
                val uri = Uri.parse(rota.customLogoPath)
                val input: InputStream? = context.contentResolver.openInputStream(uri)
                if (input != null) {
                    val bmp = BitmapFactory.decodeStream(input)
                    input.close()
                    if (bmp != null) return bmp
                }
            } catch (e: Exception) {
                // fallback to preset
            }
        }
        return DutyRotaEmblemGenerator.createEmblemBitmap(rota.logoPreset)
    }

    /**
     * Resolves the stamp bitmap either from custom path or generated rubber seal.
     */
    fun resolveStampBitmap(context: Context, rota: DutyRota): Bitmap {
        if (!rota.customStampPath.isNullOrBlank()) {
            try {
                val uri = Uri.parse(rota.customStampPath)
                val input: InputStream? = context.contentResolver.openInputStream(uri)
                if (input != null) {
                    val bmp = BitmapFactory.decodeStream(input)
                    input.close()
                    if (bmp != null) return bmp
                }
            } catch (e: Exception) {
                // fallback to preset
            }
        }
        return DutyRotaEmblemGenerator.createOfficialStampBitmap(
            hospitalName = rota.hospitalName,
            designation = rota.signatoryDesignation,
            signatory = rota.signatoryName
        )
    }

    /**
     * Compiles and exports the complete duty rota into a native Microsoft Word (.docx) document.
     */
    fun exportToWordDocx(context: Context, rota: DutyRota): Uri? {
        try {
            val cleanTitle = rota.title.replace("[^a-zA-Z0-9._-]".toRegex(), "_")
            val fileName = "Duty_Rota_${cleanTitle}_${System.currentTimeMillis()}"

            val contentResolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.docx")
                put(
                    MediaStore.MediaColumns.MIME_TYPE,
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
            }

            val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                ?: return null

            contentResolver.openOutputStream(uri)?.use { out ->
                val zip = ZipOutputStream(out)

                // 1. [Content_Types].xml
                zip.putNextEntry(ZipEntry("[Content_Types].xml"))
                val contentTypesXml = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
                      <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
                      <Default Extension="xml" ContentType="application/xml"/>
                      <Default Extension="png" ContentType="image/png"/>
                      <Default Extension="jpeg" ContentType="image/jpeg"/>
                      <Default Extension="jpg" ContentType="image/jpeg"/>
                      <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
                    </Types>
                """.trimIndent()
                zip.write(contentTypesXml.toByteArray(Charsets.UTF_8))
                zip.closeEntry()

                // 2. _rels/.rels
                zip.putNextEntry(ZipEntry("_rels/.rels"))
                val rootRelsXml = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                      <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
                    </Relationships>
                """.trimIndent()
                zip.write(rootRelsXml.toByteArray(Charsets.UTF_8))
                zip.closeEntry()

                // 3. Prepare images
                val logoBmp = resolveLogoBitmap(context, rota)
                val stampBmp = resolveStampBitmap(context, rota)

                val logoBytesOut = ByteArrayOutputStream()
                logoBmp.compress(Bitmap.CompressFormat.PNG, 95, logoBytesOut)
                val logoBytes = logoBytesOut.toByteArray()

                val stampBytesOut = ByteArrayOutputStream()
                stampBmp.compress(Bitmap.CompressFormat.PNG, 90, stampBytesOut)
                val stampBytes = stampBytesOut.toByteArray()

                // Put image1.png (Logo)
                zip.putNextEntry(ZipEntry("word/media/image1.png"))
                zip.write(logoBytes)
                zip.closeEntry()

                // Put image2.png (Stamp)
                zip.putNextEntry(ZipEntry("word/media/image2.png"))
                zip.write(stampBytes)
                zip.closeEntry()

                // 4. word/_rels/document.xml.rels
                zip.putNextEntry(ZipEntry("word/_rels/document.xml.rels"))
                val docRelsXml = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                      <Relationship Id="rIdLogo" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image" Target="media/image1.png"/>
                      <Relationship Id="rIdStamp" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image" Target="media/image2.png"/>
                    </Relationships>
                """.trimIndent()
                zip.write(docRelsXml.toByteArray(Charsets.UTF_8))
                zip.closeEntry()

                // 5. word/document.xml
                zip.putNextEntry(ZipEntry("word/document.xml"))
                val docXml = generateWordDocumentXml(rota)
                zip.write(docXml.toByteArray(Charsets.UTF_8))
                zip.closeEntry()

                zip.finish()
            }

            return uri
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Constructs the complete OpenXML string for word/document.xml
     */
    private fun generateWordDocumentXml(rota: DutyRota): String {
        // Logo dimensions in EMUs (1 inch = 914400 EMUs; 1 dp approx 12700 EMUs)
        val logoWidthEmu = (rota.logoSizeDp * 12700).toLong().coerceIn(400000L, 1600000L)
        val logoHeightEmu = logoWidthEmu // 1:1 square emblem
        val stampWidthEmu = (rota.stampSizeDp * 12700).toLong().coerceIn(500000L, 1400000L)
        val stampHeightEmu = stampWidthEmu

        val escHospital = escapeXml(rota.hospitalName)
        val escTitle = escapeXml(rota.title)
        val escSubHeader = escapeXml(rota.subHeader)
        val escRef = escapeXml(rota.referenceNumber)
        val escDate = escapeXml(rota.issueDate)
        val escSignatory = escapeXml(rota.signatoryName)
        val escDesignation = escapeXml(rota.signatoryDesignation)
        val escInstitution = escapeXml(rota.signatoryInstitution)

        return buildString {
            append("""<?xml version="1.0" encoding="UTF-8" standalone="yes"?>""")
            append("""<w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" """)
            append("""xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" """)
            append("""xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" """)
            append("""xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main" """)
            append("""xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">""")
            append("<w:body>")

            // --- HEADER WITH LOGO ---
            // 2-column table for header (Left: Titles, Right: Logo)
            append("<w:tbl>")
            append("<w:tblPr>")
            append("<w:tblW w:w=\"15300\" w:type=\"dxa\"/>")
            append("<w:jc w:val=\"center\"/>")
            append("<w:tblBorders>")
            append("<w:top w:val=\"none\"/><w:left w:val=\"none\"/><w:bottom w:val=\"none\"/><w:right w:val=\"none\"/>")
            append("<w:insideH w:val=\"none\"/><w:insideV w:val=\"none\"/>")
            append("</w:tblBorders>")
            append("</w:tblPr>")

            append("<w:tr>")
            // Header text column (width 12500 dxa)
            append("<w:tc><w:tcPr><w:tcW w:w=\"12500\" w:type=\"dxa\"/><w:vAlign w:val=\"center\"/></w:tcPr>")

            // Hospital Name
            append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:after=\"60\" w:before=\"0\"/></w:pPr>")
            append("<w:r><w:rPr><w:b/><w:sz w:val=\"34\"/><w:color w:val=\"1A202C\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr>")
            append("<w:t>$escHospital</w:t></w:r></w:p>")

            // Title
            append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:after=\"80\" w:before=\"0\"/></w:pPr>")
            append("<w:r><w:rPr><w:b/><w:u w:val=\"single\"/><w:sz w:val=\"26\"/><w:color w:val=\"2D3748\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr>")
            append("<w:t>$escTitle</w:t></w:r></w:p>")

            // Optional Subheader or Ref/Date
            if (escSubHeader.isNotBlank() || escRef.isNotBlank()) {
                append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:after=\"40\" w:before=\"0\"/></w:pPr>")
                append("<w:r><w:rPr><w:sz w:val=\"20\"/><w:color w:val=\"4A5568\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr>")
                val subLine = listOfNotNull(
                    escSubHeader.takeIf { it.isNotBlank() },
                    escRef.takeIf { it.isNotBlank() },
                    ("Dated: $escDate").takeIf { escDate.isNotBlank() }
                ).joinToString("  |  ")
                append("<w:t>$subLine</w:t></w:r></w:p>")
            }
            append("</w:tc>")

            // Logo column (width 2800 dxa)
            append("<w:tc><w:tcPr><w:tcW w:w=\"2800\" w:type=\"dxa\"/><w:vAlign w:val=\"center\"/></w:tcPr>")
            append("<w:p><w:pPr><w:jc w:val=\"right\"/><w:spacing w:after=\"0\" w:before=\"0\"/></w:pPr>")
            if (rota.logoPreset != "NONE") {
                append("<w:r><w:drawing>")
                append("<wp:inline distT=\"0\" distB=\"0\" distL=\"0\" distR=\"0\">")
                append("<wp:extent cx=\"$logoWidthEmu\" cy=\"$logoHeightEmu\"/>")
                append("<wp:docPr id=\"1\" name=\"HospitalLogo\"/>")
                append("<a:graphic>")
                append("<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">")
                append("<pic:pic>")
                append("<pic:nvPicPr><pic:cNvPr id=\"1\" name=\"Logo.png\"/><pic:cNvPicPr/></pic:nvPicPr>")
                append("<pic:blipFill><a:blip r:embed=\"rIdLogo\"/><a:stretch><a:fillRect/></a:stretch></pic:blipFill>")
                append("<pic:spPr><a:xfrm><a:off x=\"0\" y=\"0\"/><a:ext cx=\"$logoWidthEmu\" cy=\"$logoHeightEmu\"/></a:xfrm><a:prstGeom prst=\"rect\"><a:avLst/></a:prstGeom></pic:spPr>")
                append("</pic:pic>")
                append("</a:graphicData>")
                append("</a:graphic>")
                append("</wp:inline>")
                append("</w:drawing></w:r>")
            }
            append("</w:p></w:tc>")
            append("</w:tr>")
            append("</w:tbl>")

            // Space before table
            append("<w:p><w:pPr><w:spacing w:after=\"120\"/></w:pPr></w:p>")

            // --- MAIN DUTY ROTA TABLE ---
            append("<w:tbl>")
            append("<w:tblPr>")
            append("<w:tblW w:w=\"15300\" w:type=\"dxa\"/>")
            append("<w:jc w:val=\"center\"/>")
            append("<w:tblBorders>")
            append("<w:top w:val=\"single\" w:sz=\"8\" w:space=\"0\" w:color=\"000000\"/>")
            append("<w:left w:val=\"single\" w:sz=\"8\" w:space=\"0\" w:color=\"000000\"/>")
            append("<w:bottom w:val=\"single\" w:sz=\"8\" w:space=\"0\" w:color=\"000000\"/>")
            append("<w:right w:val=\"single\" w:sz=\"8\" w:space=\"0\" w:color=\"000000\"/>")
            append("<w:insideH w:val=\"single\" w:sz=\"6\" w:space=\"0\" w:color=\"000000\"/>")
            append("<w:insideV w:val=\"single\" w:sz=\"6\" w:space=\"0\" w:color=\"000000\"/>")
            append("</w:tblBorders>")
            append("</w:tblPr>")

            val totalCols = rota.columns.size + 1
            val dayColWidth = 1700
            val remainingWidth = 15300 - dayColWidth
            val otherColWidth = if (rota.columns.isNotEmpty()) remainingWidth / rota.columns.size else remainingWidth

            // Table Header Row
            append("<w:tr><w:trPr><w:tblHeader/><w:cantSplit/></w:trPr>")
            // "Days" Column Header
            append("<w:tc><w:tcPr><w:tcW w:w=\"$dayColWidth\" w:type=\"dxa\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"F0F4F8\"/><w:vAlign w:val=\"center\"/></w:tcPr>")
            append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:before=\"80\" w:after=\"80\"/></w:pPr>")
            append("<w:r><w:rPr><w:b/><w:sz w:val=\"22\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr><w:t>Days</w:t></w:r></w:p>")
            append("</w:tc>")

            // Department Column Headers
            for (colTitle in rota.columns) {
                append("<w:tc><w:tcPr><w:tcW w:w=\"$otherColWidth\" w:type=\"dxa\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"F0F4F8\"/><w:vAlign w:val=\"center\"/></w:tcPr>")
                val lines = colTitle.split("\n")
                for (ln in lines) {
                    append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:before=\"40\" w:after=\"40\"/></w:pPr>")
                    append("<w:r><w:rPr><w:b/><w:sz w:val=\"21\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr><w:t>${escapeXml(ln.trim())}</w:t></w:r></w:p>")
                }
                append("</w:tc>")
            }
            append("</w:tr>")

            // Table Data Rows
            for (day in rota.days) {
                append("<w:tr><w:trPr><w:cantSplit/></w:trPr>")
                // Day cell
                append("<w:tc><w:tcPr><w:tcW w:w=\"$dayColWidth\" w:type=\"dxa\"/><w:vAlign w:val=\"center\"/></w:tcPr>")
                append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:before=\"60\" w:after=\"60\"/></w:pPr>")
                append("<w:r><w:rPr><w:b/><w:sz w:val=\"21\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr><w:t>${escapeXml(day)}</w:t></w:r></w:p>")
                append("</w:tc>")

                // Department cells
                for (cIdx in rota.columns.indices) {
                    val rawCellText = rota.getCell(day, cIdx).trim()
                    val isOff = rawCellText.equals("OFF", ignoreCase = true)
                    val lines = if (rawCellText.isEmpty()) listOf("") else rawCellText.split("\n")

                    val cellShading = if (isOff) " w:fill=\"FAFAFA\"" else ""
                    append("<w:tc><w:tcPr><w:tcW w:w=\"$otherColWidth\" w:type=\"dxa\"/>$cellShading<w:vAlign w:val=\"center\"/></w:tcPr>")

                    for (ln in lines) {
                        val trimmedLn = ln.trim()
                        append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:before=\"30\" w:after=\"30\" w:line=\"220\" w:lineRule=\"auto\"/></w:pPr>")
                        if (trimmedLn.isNotEmpty()) {
                            val rPr = if (isOff) {
                                "<w:rPr><w:b/><w:sz w:val=\"20\"/><w:color w:val=\"000000\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr>"
                            } else {
                                "<w:rPr><w:sz w:val=\"20\"/><w:color w:val=\"1A202C\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr>"
                            }
                            append("<w:r>$rPr<w:t>${escapeXml(trimmedLn)}</w:t></w:r>")
                        }
                        append("</w:p>")
                    }
                    append("</w:tc>")
                }
                append("</w:tr>")
            }
            append("</w:tbl>")

            // Space before footer
            append("<w:p><w:pPr><w:spacing w:before=\"160\" w:after=\"100\"/></w:pPr></w:p>")

            // --- FOOTER & SIGNATORY SECTION ---
            // 2-column table: Left: Copies to / distribution, Right: Stamp & Signature
            append("<w:tbl>")
            append("<w:tblPr>")
            append("<w:tblW w:w=\"15300\" w:type=\"dxa\"/>")
            append("<w:jc w:val=\"center\"/>")
            append("<w:tblBorders>")
            append("<w:top w:val=\"none\"/><w:left w:val=\"none\"/><w:bottom w:val=\"none\"/><w:right w:val=\"none\"/>")
            append("<w:insideH w:val=\"none\"/><w:insideV w:val=\"none\"/>")
            append("</w:tblBorders>")
            append("</w:tblPr>")

            append("<w:tr>")
            // Left: Copies / Distribution
            append("<w:tc><w:tcPr><w:tcW w:w=\"9500\" w:type=\"dxa\"/><w:vAlign w:val=\"top\"/></w:tcPr>")
            if (rota.copiesTo.isNotEmpty()) {
                append("<w:p><w:pPr><w:spacing w:after=\"40\"/></w:pPr><w:r><w:rPr><w:b/><w:u w:val=\"single\"/><w:sz w:val=\"19\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr><w:t>Copy forwarded for information to:</w:t></w:r></w:p>")
                for (cp in rota.copiesTo) {
                    append("<w:p><w:pPr><w:spacing w:before=\"20\" w:after=\"20\"/></w:pPr>")
                    append("<w:r><w:rPr><w:sz w:val=\"18\"/><w:color w:val=\"4A5568\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr><w:t>${escapeXml(cp)}</w:t></w:r></w:p>")
                }
            }
            append("</w:tc>")

            // Right: Stamp & Signatory
            append("<w:tc><w:tcPr><w:tcW w:w=\"5800\" w:type=\"dxa\"/><w:vAlign w:val=\"top\"/></w:tcPr>")
            append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:after=\"40\"/></w:pPr>")

            // Stamp Image
            if (rota.stampPreset != "NONE") {
                append("<w:r><w:drawing>")
                append("<wp:inline distT=\"0\" distB=\"0\" distL=\"0\" distR=\"0\">")
                append("<wp:extent cx=\"$stampWidthEmu\" cy=\"$stampHeightEmu\"/>")
                append("<wp:docPr id=\"2\" name=\"OfficialStamp\"/>")
                append("<a:graphic>")
                append("<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">")
                append("<pic:pic>")
                append("<pic:nvPicPr><pic:cNvPr id=\"2\" name=\"Stamp.png\"/><pic:cNvPicPr/></pic:nvPicPr>")
                append("<pic:blipFill><a:blip r:embed=\"rIdStamp\"/><a:stretch><a:fillRect/></a:stretch></pic:blipFill>")
                append("<pic:spPr><a:xfrm><a:off x=\"0\" y=\"0\"/><a:ext cx=\"$stampWidthEmu\" cy=\"$stampHeightEmu\"/></a:xfrm><a:prstGeom prst=\"rect\"><a:avLst/></a:prstGeom></pic:spPr>")
                append("</pic:pic>")
                append("</a:graphicData>")
                append("</a:graphic>")
                append("</wp:inline>")
                append("</w:drawing></w:r>")
            }
            append("</w:p>")

            // Signatory Details
            append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:before=\"40\" w:after=\"20\"/></w:pPr>")
            append("<w:r><w:rPr><w:b/><w:sz w:val=\"22\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr><w:t>$escDesignation</w:t></w:r></w:p>")

            append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:spacing w:before=\"0\" w:after=\"20\"/></w:pPr>")
            append("<w:r><w:rPr><w:sz w:val=\"20\"/><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/></w:rPr><w:t>$escInstitution</w:t></w:r></w:p>")

            append("</w:tc>")
            append("</w:tr>")
            append("</w:tbl>")

            // Page Setup: Landscape A4 (width 16838 dxa, height 11906 dxa), 0.5 in margins (720 dxa)
            append("<w:sectPr>")
            append("<w:pgSz w:w=\"16838\" w:h=\"11906\" w:orient=\"landscape\"/>")
            append("<w:pgMar w:top=\"720\" w:right=\"720\" w:bottom=\"720\" w:left=\"720\" w:header=\"360\" w:footer=\"360\" w:gutter=\"0\"/>")
            append("</w:sectPr>")

            append("</w:body></w:document>")
        }
    }

    /**
     * Generates a high-quality landscape PDF document and writes it to cache/storage.
     */
    fun exportToPdf(context: Context, rota: DutyRota): File? {
        try {
            // Landscape A4 points: 842 x 595
            val pageWidth = 842
            val pageHeight = 595
            val margin = 36f

            val doc = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
            val page = doc.startPage(pageInfo)
            val canvas = page.canvas

            // Background white
            canvas.drawColor(AndroidColor.WHITE)

            // Resolve Bitmaps
            val logoBmp = resolveLogoBitmap(context, rota)
            val stampBmp = resolveStampBitmap(context, rota)

            // 1. Watermark in center of table area (if enabled)
            if (rota.showWatermark) {
                val wmSize = rota.watermarkSizeDp * 1.2f
                val wmPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    alpha = (rota.watermarkOpacity * 255).toInt().coerceIn(10, 200)
                }
                val wmLeft = (pageWidth - wmSize) / 2f
                val wmTop = (pageHeight - wmSize) / 2f + 25f
                val wmDst = RectF(wmLeft, wmTop, wmLeft + wmSize, wmTop + wmSize)
                canvas.drawBitmap(logoBmp, null, wmDst, wmPaint)
            }

            // 2. Header
            val headerTop = margin
            val logoSize = rota.logoSizeDp * 0.9f
            if (rota.logoPreset != "NONE") {
                val logoPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    alpha = (rota.logoOpacity * 255).toInt().coerceIn(20, 255)
                }
                val logoDst = RectF(pageWidth - margin - logoSize, headerTop, pageWidth - margin, headerTop + logoSize)
                canvas.drawBitmap(logoBmp, null, logoDst, logoPaint)
            }

            // Header Texts
            val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = AndroidColor.BLACK
                textSize = 15f
                typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                textAlign = Paint.Align.CENTER
            }
            val titleX = pageWidth / 2f
            var curY = headerTop + 18f
            canvas.drawText(rota.hospitalName.uppercase(), titleX, curY, titlePaint)

            curY += 16f
            titlePaint.textSize = 12.5f
            titlePaint.isUnderlineText = true
            canvas.drawText(rota.title, titleX, curY, titlePaint)
            titlePaint.isUnderlineText = false

            if (rota.subHeader.isNotBlank() || rota.referenceNumber.isNotBlank()) {
                curY += 14f
                val subPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = AndroidColor.DKGRAY
                    textSize = 9.5f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                    textAlign = Paint.Align.CENTER
                }
                val subLine = listOfNotNull(
                    rota.subHeader.takeIf { it.isNotBlank() },
                    rota.referenceNumber.takeIf { it.isNotBlank() },
                    ("Dated: ${rota.issueDate}").takeIf { rota.issueDate.isNotBlank() }
                ).joinToString("  |  ")
                canvas.drawText(subLine, titleX, curY, subPaint)
            }

            // 3. Table Dimensions
            val tableTop = (headerTop + logoSize.coerceAtLeast(50f) + 12f).coerceAtLeast(curY + 14f)
            val tableLeft = margin
            val tableWidth = pageWidth - (margin * 2)

            val dayColWidth = 85f
            val remainingWidth = tableWidth - dayColWidth
            val otherColWidth = if (rota.columns.isNotEmpty()) remainingWidth / rota.columns.size else remainingWidth

            val totalCols = rota.columns.size + 1
            val headerRowHeight = 28f
            val rowHeight = ((pageHeight - tableTop - 110f) / rota.days.size).coerceIn(40f, 58f)
            val tableBottom = tableTop + headerRowHeight + (rowHeight * rota.days.size)

            val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = AndroidColor.BLACK
                style = Paint.Style.STROKE
                strokeWidth = 1.2f
            }

            // Header Background Shading
            val hdrBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = AndroidColor.rgb(240, 243, 246)
                style = Paint.Style.FILL
            }
            canvas.drawRect(tableLeft, tableTop, tableLeft + tableWidth, tableTop + headerRowHeight, hdrBgPaint)

            // Header Cell Texts
            val colHdrPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = AndroidColor.BLACK
                textSize = 9f
                typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                textAlign = Paint.Align.CENTER
            }
            // "Days"
            canvas.drawText("Days", tableLeft + (dayColWidth / 2f), tableTop + 17f, colHdrPaint)

            // Columns
            for (cIdx in rota.columns.indices) {
                val cLeft = tableLeft + dayColWidth + (cIdx * otherColWidth)
                val cCenterX = cLeft + (otherColWidth / 2f)
                val colText = rota.columns[cIdx]
                val lines = colText.split("\n")
                if (lines.size == 1) {
                    canvas.drawText(lines[0].trim(), cCenterX, tableTop + 17f, colHdrPaint)
                } else {
                    canvas.drawText(lines[0].trim(), cCenterX, tableTop + 12f, colHdrPaint)
                    canvas.drawText(lines[1].trim(), cCenterX, tableTop + 23f, colHdrPaint)
                }
            }

            // Draw Data Rows
            val cellTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = AndroidColor.BLACK
                textSize = 8.5f
                typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                textAlign = Paint.Align.CENTER
            }
            val dayTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = AndroidColor.BLACK
                textSize = 9.5f
                typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                textAlign = Paint.Align.CENTER
            }

            for (rIdx in rota.days.indices) {
                val day = rota.days[rIdx]
                val rTop = tableTop + headerRowHeight + (rIdx * rowHeight)
                val rCenterY = rTop + (rowHeight / 2f)

                // Day name
                canvas.drawText(day, tableLeft + (dayColWidth / 2f), rCenterY + 3.5f, dayTextPaint)

                // Each department
                for (cIdx in rota.columns.indices) {
                    val cLeft = tableLeft + dayColWidth + (cIdx * otherColWidth)
                    val cCenterX = cLeft + (otherColWidth / 2f)
                    val text = rota.getCell(day, cIdx).trim()

                    if (text.equals("OFF", ignoreCase = true)) {
                        val offPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.BLACK
                            textSize = 9f
                            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                            textAlign = Paint.Align.CENTER
                        }
                        canvas.drawText("OFF", cCenterX, rCenterY + 3.5f, offPaint)
                    } else if (text.isNotBlank()) {
                        val lines = text.split("\n")
                        val lineSpacing = 11f
                        val totalTextHeight = lines.size * lineSpacing
                        var startY = rCenterY - (totalTextHeight / 2f) + 8f

                        for (ln in lines) {
                            canvas.drawText(ln.trim(), cCenterX, startY, cellTextPaint)
                            startY += lineSpacing
                        }
                    }
                }
            }

            // Draw Grid Borders
            // Horizontal lines
            canvas.drawLine(tableLeft, tableTop, tableLeft + tableWidth, tableTop, borderPaint)
            canvas.drawLine(tableLeft, tableTop + headerRowHeight, tableLeft + tableWidth, tableTop + headerRowHeight, borderPaint)
            for (rIdx in 1..rota.days.size) {
                val y = tableTop + headerRowHeight + (rIdx * rowHeight)
                canvas.drawLine(tableLeft, y, tableLeft + tableWidth, y, borderPaint)
            }

            // Vertical lines
            canvas.drawLine(tableLeft, tableTop, tableLeft, tableBottom, borderPaint)
            canvas.drawLine(tableLeft + dayColWidth, tableTop, tableLeft + dayColWidth, tableBottom, borderPaint)
            for (cIdx in 1..rota.columns.size) {
                val x = tableLeft + dayColWidth + (cIdx * otherColWidth)
                canvas.drawLine(x, tableTop, x, tableBottom, borderPaint)
            }

            // 4. Footer & Signature Block
            val footerTop = tableBottom + 12f
            val stampSize = rota.stampSizeDp * 0.8f

            // Right side: Stamp & Signatory
            val sigRight = pageWidth - margin
            val sigCenterX = sigRight - 80f

            if (rota.stampPreset != "NONE") {
                val stampPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    alpha = (rota.stampOpacity * 255).toInt().coerceIn(50, 255)
                }
                val stampDst = RectF(sigCenterX - (stampSize / 2f), footerTop - 4f, sigCenterX + (stampSize / 2f), footerTop + stampSize - 4f)
                canvas.drawBitmap(stampBmp, null, stampDst, stampPaint)
            }

            val sigTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = AndroidColor.BLACK
                textSize = 9.5f
                typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                textAlign = Paint.Align.CENTER
            }
            canvas.drawText(rota.signatoryDesignation, sigCenterX, footerTop + stampSize + 8f, sigTextPaint)
            sigTextPaint.typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            sigTextPaint.textSize = 8.5f
            canvas.drawText(rota.signatoryInstitution, sigCenterX, footerTop + stampSize + 20f, sigTextPaint)

            // Left side: Copies to list
            if (rota.copiesTo.isNotEmpty()) {
                val copyHdrPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = AndroidColor.BLACK
                    textSize = 8.5f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    isUnderlineText = true
                }
                canvas.drawText("Copy forwarded for information to:", tableLeft, footerTop + 10f, copyHdrPaint)

                val copyItemPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = AndroidColor.DKGRAY
                    textSize = 8f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                }
                var cY = footerTop + 22f
                for (cp in rota.copiesTo) {
                    canvas.drawText(cp, tableLeft + 5f, cY, copyItemPaint)
                    cY += 11f
                }
            }

            doc.finishPage(page)

            // Write to file
            val file = File(context.cacheDir, "Duty_Rota_${System.currentTimeMillis()}.pdf")
            val fos = FileOutputStream(file)
            doc.writeTo(fos)
            fos.close()
            doc.close()

            return file
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun openInWord(context: Context, docxUri: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(
                    docxUri,
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                )
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(Intent.createChooser(intent, "Open in Microsoft Word / Docs"))
        } catch (e: Exception) {
            Toast.makeText(context, "No Word viewer app installed. File saved to Downloads!", Toast.LENGTH_LONG).show()
        }
    }

    fun shareDocx(context: Context, docxUri: Uri) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                putExtra(Intent.EXTRA_STREAM, docxUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Word Duty Rota"))
        } catch (e: Exception) {
            Toast.makeText(context, "Could not share file: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun sharePdf(context: Context, pdfFile: File) {
        try {
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", pdfFile)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share PDF Duty Rota"))
        } catch (e: Exception) {
            Toast.makeText(context, "Could not share PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
