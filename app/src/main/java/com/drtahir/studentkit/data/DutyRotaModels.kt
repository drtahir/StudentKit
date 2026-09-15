package com.drtahir.studentkit.data

import android.content.Context
import android.graphics.*
import android.net.Uri
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

/**
 * Data models and helper utilities for the Duty Rota / Roster Generator module.
 */
data class DutyRota(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "Weekly OPD Duty Rota of Doctors / September 2026",
    val hospitalName: String = "CAT-D HOSPITAL PACHA KALAY BUNER",
    val subHeader: String = "Office of the Medical Superintendent",
    val referenceNumber: String = "No. 5420-30/MS/Cat-D",
    val issueDate: String = "12-09-2026",
    val columns: List<String> = listOf(
        "Pediatric OPD",
        "Surgical OPD",
        "OT",
        "Medical OPD",
        "General OPD",
        "Skin OPD",
        "Cardiology OPD",
        "Orthopedic OPD"
    ),
    val days: List<String> = listOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    ),
    // Map key: "${day}_${columnIndex}" -> cell text (e.g. "Dr SaadUllah\nDr Harinder Kumar")
    val cells: Map<String, String> = emptyMap(),
    val staffPool: List<String> = listOf(
        "Dr SaadUllah",
        "Dr Harinder Kumar",
        "Dr Jehan Said",
        "Dr Ahmad Ghani",
        "Dr Abid",
        "Dr Khan Bahadar",
        "Dr Adnan Shah",
        "Dr Siraj Ul Mulk",
        "Dr Javid",
        "Dr Akhtar Khan",
        "Dr Rehman Zeb",
        "Dr Amir Saeed",
        "Dr Sajid Ali Shah",
        "Dr Zia U Rehman",
        "Dr M.Arif",
        "Dr Kazim",
        "Dr Ibrar"
    ),
    val logoPreset: String = "HOSPITAL_BUNER", // HOSPITAL_BUNER, CADUCEUS_HEALTH, STAR_CRESCENT_GOVT, SHIELD_MED, CUSTOM, NONE
    val customLogoPath: String? = null,
    val logoSizeDp: Float = 75f, // 40f to 120f
    val logoOpacity: Float = 1.0f, // 0.1f to 1.0f
    val logoPosition: String = "RIGHT", // RIGHT, LEFT, CENTER
    val showWatermark: Boolean = true,
    val watermarkOpacity: Float = 0.12f, // 0.05f to 0.40f
    val watermarkSizeDp: Float = 260f, // 150f to 400f
    val signatoryName: String = "Dr. Tahir Khan",
    val signatoryDesignation: String = "Medical Superintendent",
    val signatoryInstitution: String = "Cat D Hospital Pacha",
    val stampPreset: String = "CIRCULAR_SEAL", // CIRCULAR_SEAL, CUSTOM, NONE
    val customStampPath: String? = null,
    val stampSizeDp: Float = 75f,
    val stampOpacity: Float = 0.85f,
    val copiesTo: List<String> = listOf(
        "1. The Director General Health Services KP, Peshawar.",
        "2. The District Health Officer, Buner.",
        "3. Incharges of all Concerned OPDs & Units.",
        "4. Notice Board / Office Record File."
    )
) {
    fun getCell(day: String, colIndex: Int): String {
        return cells["${day}_$colIndex"] ?: ""
    }

    fun withCell(day: String, colIndex: Int, text: String): DutyRota {
        val newCells = cells.toMutableMap()
        if (text.isBlank()) {
            newCells.remove("${day}_$colIndex")
        } else {
            newCells["${day}_$colIndex"] = text.trim()
        }
        return copy(cells = newCells)
    }

    fun toJson(): String {
        val obj = JSONObject()
        obj.put("id", id)
        obj.put("title", title)
        obj.put("hospitalName", hospitalName)
        obj.put("subHeader", subHeader)
        obj.put("referenceNumber", referenceNumber)
        obj.put("issueDate", issueDate)

        val colsArray = JSONArray()
        columns.forEach { colsArray.put(it) }
        obj.put("columns", colsArray)

        val daysArray = JSONArray()
        days.forEach { daysArray.put(it) }
        obj.put("days", daysArray)

        val cellsObj = JSONObject()
        cells.forEach { (k, v) -> cellsObj.put(k, v) }
        obj.put("cells", cellsObj)

        val staffArray = JSONArray()
        staffPool.forEach { staffArray.put(it) }
        obj.put("staffPool", staffArray)

        obj.put("logoPreset", logoPreset)
        obj.put("customLogoPath", customLogoPath ?: "")
        obj.put("logoSizeDp", logoSizeDp.toDouble())
        obj.put("logoOpacity", logoOpacity.toDouble())
        obj.put("logoPosition", logoPosition)
        obj.put("showWatermark", showWatermark)
        obj.put("watermarkOpacity", watermarkOpacity.toDouble())
        obj.put("watermarkSizeDp", watermarkSizeDp.toDouble())

        obj.put("signatoryName", signatoryName)
        obj.put("signatoryDesignation", signatoryDesignation)
        obj.put("signatoryInstitution", signatoryInstitution)
        obj.put("stampPreset", stampPreset)
        obj.put("customStampPath", customStampPath ?: "")
        obj.put("stampSizeDp", stampSizeDp.toDouble())
        obj.put("stampOpacity", stampOpacity.toDouble())

        val copiesArray = JSONArray()
        copiesTo.forEach { copiesArray.put(it) }
        obj.put("copiesTo", copiesArray)

        return obj.toString()
    }

    companion object {
        fun fromJson(jsonStr: String): DutyRota {
            return try {
                val obj = JSONObject(jsonStr)
                val cols = mutableListOf<String>()
                val colsArr = obj.optJSONArray("columns")
                if (colsArr != null) {
                    for (i in 0 until colsArr.length()) cols.add(colsArr.getString(i))
                }

                val days = mutableListOf<String>()
                val daysArr = obj.optJSONArray("days")
                if (daysArr != null) {
                    for (i in 0 until daysArr.length()) days.add(daysArr.getString(i))
                }

                val cells = mutableMapOf<String, String>()
                val cellsObj = obj.optJSONObject("cells")
                if (cellsObj != null) {
                    val keys = cellsObj.keys()
                    while (keys.hasNext()) {
                        val k = keys.next()
                        cells[k] = cellsObj.getString(k)
                    }
                }

                val staff = mutableListOf<String>()
                val staffArr = obj.optJSONArray("staffPool")
                if (staffArr != null) {
                    for (i in 0 until staffArr.length()) staff.add(staffArr.getString(i))
                }

                val copies = mutableListOf<String>()
                val copiesArr = obj.optJSONArray("copiesTo")
                if (copiesArr != null) {
                    for (i in 0 until copiesArr.length()) copies.add(copiesArr.getString(i))
                }

                val customLogo = obj.optString("customLogoPath").takeIf { it.isNotBlank() }
                val customStamp = obj.optString("customStampPath").takeIf { it.isNotBlank() }

                DutyRota(
                    id = obj.optString("id", UUID.randomUUID().toString()),
                    title = obj.optString("title", "Weekly OPD Duty Rota of Doctors / September 2026"),
                    hospitalName = obj.optString("hospitalName", "CAT-D HOSPITAL PACHA KALAY BUNER"),
                    subHeader = obj.optString("subHeader", "Office of the Medical Superintendent"),
                    referenceNumber = obj.optString("referenceNumber", "No. 5420-30/MS/Cat-D"),
                    issueDate = obj.optString("issueDate", "12-09-2026"),
                    columns = if (cols.isNotEmpty()) cols else listOf("Pediatric OPD", "Surgical OPD", "OT", "Medical OPD", "General OPD", "Skin OPD", "Cardiology OPD", "Orthopedic OPD"),
                    days = if (days.isNotEmpty()) days else listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
                    cells = cells,
                    staffPool = staff,
                    logoPreset = obj.optString("logoPreset", "HOSPITAL_BUNER"),
                    customLogoPath = customLogo,
                    logoSizeDp = obj.optDouble("logoSizeDp", 75.0).toFloat(),
                    logoOpacity = obj.optDouble("logoOpacity", 1.0).toFloat(),
                    logoPosition = obj.optString("logoPosition", "RIGHT"),
                    showWatermark = obj.optBoolean("showWatermark", true),
                    watermarkOpacity = obj.optDouble("watermarkOpacity", 0.12).toFloat(),
                    watermarkSizeDp = obj.optDouble("watermarkSizeDp", 260.0).toFloat(),
                    signatoryName = obj.optString("signatoryName", "Dr. Tahir Khan"),
                    signatoryDesignation = obj.optString("signatoryDesignation", "Medical Superintendent"),
                    signatoryInstitution = obj.optString("signatoryInstitution", "Cat D Hospital Pacha"),
                    stampPreset = obj.optString("stampPreset", "CIRCULAR_SEAL"),
                    customStampPath = customStamp,
                    stampSizeDp = obj.optDouble("stampSizeDp", 75.0).toFloat(),
                    stampOpacity = obj.optDouble("stampOpacity", 0.85).toFloat(),
                    copiesTo = copies
                )
            } catch (e: Exception) {
                createCatDBunerSample()
            }
        }

        /**
         * Creates the exact pre-filled Duty Rota matching the user's uploaded document from Cat-D Hospital Pacha Kalay Buner!
         */
        fun createCatDBunerSample(): DutyRota {
            val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            val cols = listOf(
                "Pediatric\nOPD",
                "Surgical\nOPD",
                "OT",
                "Medical\nOPD",
                "General OPD",
                "Skin\nOPD",
                "Cardiology\nOPD",
                "Orthopedic\nOPD"
            )
            val cells = mutableMapOf<String, String>()

            // Monday
            cells["Monday_0"] = "Dr SaadUllah\nDr Harinder Kumar"
            cells["Monday_1"] = "Dr Jehan Said\nDr Ahmad Ghani"
            cells["Monday_2"] = "OFF"
            cells["Monday_3"] = "Dr Abid,\nDr Khan Bahadar"
            cells["Monday_4"] = "Off"
            cells["Monday_5"] = "Dr Siraj Ul Mulk"
            cells["Monday_6"] = "Dr Javid"
            cells["Monday_7"] = "Dr Akhtar Khan"

            // Tuesday
            cells["Tuesday_0"] = "Dr SaadUllah\nDr Harinder Kumar"
            cells["Tuesday_1"] = "Dr Amir Saeed\nDr Ahmad Ghani"
            cells["Tuesday_2"] = "Dr Jehan Said"
            cells["Tuesday_3"] = "Dr Abid,\nDr Khan Bahadar"
            cells["Tuesday_4"] = "Dr Adnan Shah"
            cells["Tuesday_5"] = "Dr Siraj Ul Mulk"
            cells["Tuesday_6"] = "Dr Javid"
            cells["Tuesday_7"] = "Dr Akhtar Khan"

            // Wednesday
            cells["Wednesday_0"] = "Dr Rehman Zeb\nDr SaadUllah\nDr Harinder kumar"
            cells["Wednesday_1"] = "Dr Jehan Said"
            cells["Wednesday_2"] = "OFF"
            cells["Wednesday_3"] = "Dr Abid\nDr Ahmad Ghani"
            cells["Wednesday_4"] = "Dr Adnan Shah"
            cells["Wednesday_5"] = "Dr Siraj Ul Mulk\nDr Kazim"
            cells["Wednesday_6"] = "Dr Javid"
            cells["Wednesday_7"] = "Dr Akhtar Khan"

            // Thursday
            cells["Thursday_0"] = "Dr Saeed Ullah\nDr Rehman Zeb"
            cells["Thursday_1"] = "Dr Amir Saeed"
            cells["Thursday_2"] = "OFF"
            cells["Thursday_3"] = "Dr Sajid Ali Shah\nDr Ahmad Ghani"
            cells["Thursday_4"] = "Dr Adnan"
            cells["Thursday_5"] = "Dr Siraj Ul Mulk"
            cells["Thursday_6"] = "Dr Javid\nDr Ibrar"
            cells["Thursday_7"] = "OFF"

            // Friday
            cells["Friday_0"] = "Dr Rehman Zeb\nDr M.Arif"
            cells["Friday_1"] = "Dr Jehan Said\nDr Kazim"
            cells["Friday_2"] = "Dr Amir Saeed"
            cells["Friday_3"] = "Dr Sajid Ali Shah\nDr Zia U Rehman"
            cells["Friday_4"] = "Dr Adnan"
            cells["Friday_5"] = "OFF"
            cells["Friday_6"] = "Dr Ibrar"
            cells["Friday_7"] = "OFF"

            // Saturday
            cells["Saturday_0"] = "Dr Rehman Zeb\nDr Arif"
            cells["Saturday_1"] = "Dr Amir Saeed"
            cells["Saturday_2"] = "OFF"
            cells["Saturday_3"] = "Dr Zia U Rehman\nDr Sajid Ali Shah"
            cells["Saturday_4"] = "OFF"
            cells["Saturday_5"] = "OFF"
            cells["Saturday_6"] = "Dr Ibrar"
            cells["Saturday_7"] = "OFF"

            return DutyRota(
                title = "Weekly OPD Duty Rota of Doctors /September 2026",
                hospitalName = "CAT-D HOSPITAL PACHA KALAY BUNER",
                subHeader = "OFFICE OF THE MEDICAL SUPERINTENDENT",
                columns = cols,
                days = days,
                cells = cells,
                logoPreset = "HOSPITAL_BUNER",
                showWatermark = true,
                watermarkOpacity = 0.12f,
                watermarkSizeDp = 270f,
                signatoryName = "Dr. Tahir Khan",
                signatoryDesignation = "Medical Superintendent",
                signatoryInstitution = "Cat D Hospital Pacha"
            )
        }

        fun createEmergencyShiftSample(): DutyRota {
            val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
            val cols = listOf("Morning Shift (8AM-2PM)", "Evening Shift (2PM-8PM)", "Night Shift (8PM-8AM)", "On-Call Specialist")
            val cells = mutableMapOf<String, String>()

            for (d in days) {
                cells["${d}_0"] = "Dr. Asad Ullah (MO)\nStaff Nurse Samina"
                cells["${d}_1"] = "Dr. Bilal Khan (MO)\nStaff Nurse Waqas"
                cells["${d}_2"] = "Dr. Farhan Ali (CMO)\nDispenser Jamil"
                cells["${d}_3"] = "Dr. Tahir (Consultant)"
            }

            return DutyRota(
                title = "24/7 Casualty & Emergency Department Duty Roster",
                hospitalName = "GOVERNMENT CATEGORY-D HOSPITAL PACHA KALAY BUNER",
                subHeader = "Emergency & Trauma Care Unit",
                columns = cols,
                days = days,
                cells = cells,
                logoPreset = "HOSPITAL_BUNER",
                showWatermark = true,
                watermarkOpacity = 0.10f
            )
        }
    }
}

/**
 * Utility to generate high-resolution Bitmaps for official emblems, stamps, and watermarks.
 */
object DutyRotaEmblemGenerator {

    /**
     * Generates a high-quality Bitmap emblem for the Hospital/Department (Cat-D Buner or Medical Caduceus).
     */
    fun createEmblemBitmap(
        preset: String,
        widthPx: Int = 400,
        heightPx: Int = 400
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val cx = widthPx / 2f
        val cy = heightPx / 2f
        val radius = widthPx * 0.44f

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = Color.rgb(20, 35, 45) // Deep Slate / Charcoal
            strokeWidth = 4f
        }

        when (preset) {
            "HOSPITAL_BUNER" -> {
                // 1. Outer circular laurel wreath / gear border
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 5f
                paint.color = Color.rgb(30, 40, 50)
                canvas.drawCircle(cx, cy, radius, paint)

                paint.strokeWidth = 2f
                canvas.drawCircle(cx, cy, radius - 10f, paint)

                // 2. Star and Crescent at top
                val starCrescentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.rgb(30, 40, 50)
                    style = Paint.Style.FILL
                }
                // Crescent
                val crescentPath = Path().apply {
                    addCircle(cx, cy - radius * 0.72f, 18f, Path.Direction.CW)
                    val cutPath = Path().apply {
                        addCircle(cx + 6f, cy - radius * 0.76f, 14f, Path.Direction.CW)
                    }
                    op(cutPath, Path.Op.DIFFERENCE)
                }
                canvas.drawPath(crescentPath, starCrescentPaint)

                // 5-point Star
                val starPath = createStarPath(cx + 14f, cy - radius * 0.78f, 7f, 3.5f)
                canvas.drawPath(starPath, starCrescentPaint)

                // 3. Laurel Wreath branches on sides
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 3f
                paint.color = Color.rgb(40, 50, 60)
                // Left and right wreath arcs
                val wreathRect = RectF(cx - radius * 0.85f, cy - radius * 0.85f, cx + radius * 0.85f, cy + radius * 0.85f)
                canvas.drawArc(wreathRect, 110f, 140f, false, paint)
                canvas.drawArc(wreathRect, 290f, 140f, false, paint)

                // Draw laurel leaves along arcs
                val leafPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.rgb(40, 50, 60)
                    style = Paint.Style.FILL
                }
                for (angle in 120..240 step 20) {
                    val rad = Math.toRadians(angle.toDouble())
                    val lx = (cx + radius * 0.85f * Math.cos(rad)).toFloat()
                    val ly = (cy + radius * 0.85f * Math.sin(rad)).toFloat()
                    canvas.drawOval(RectF(lx - 6f, ly - 3f, lx + 6f, ly + 3f), leafPaint)
                }
                for (angle in 300..420 step 20) {
                    val rad = Math.toRadians(angle.toDouble())
                    val lx = (cx + radius * 0.85f * Math.cos(rad)).toFloat()
                    val ly = (cy + radius * 0.85f * Math.sin(rad)).toFloat()
                    canvas.drawOval(RectF(lx - 6f, ly - 3f, lx + 6f, ly + 3f), leafPaint)
                }

                // 4. Central Shield with Mountain silhouette (Elum / Buner mountains)
                val shieldPath = Path().apply {
                    moveTo(cx - radius * 0.55f, cy - radius * 0.40f)
                    lineTo(cx + radius * 0.55f, cy - radius * 0.40f)
                    lineTo(cx + radius * 0.55f, cy + radius * 0.15f)
                    quadTo(cx + radius * 0.35f, cy + radius * 0.55f, cx, cy + radius * 0.65f)
                    quadTo(cx - radius * 0.35f, cy + radius * 0.55f, cx - radius * 0.55f, cy + radius * 0.15f)
                    close()
                }
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 4f
                paint.color = Color.rgb(25, 35, 45)
                canvas.drawPath(shieldPath, paint)

                // Mountains in top half of shield
                val mountainPath = Path().apply {
                    moveTo(cx - radius * 0.50f, cy - radius * 0.05f)
                    lineTo(cx - radius * 0.25f, cy - radius * 0.32f)
                    lineTo(cx, cy - radius * 0.12f)
                    lineTo(cx + radius * 0.25f, cy - radius * 0.35f)
                    lineTo(cx + radius * 0.50f, cy - radius * 0.05f)
                    close()
                }
                val mtnPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.rgb(200, 210, 220)
                    style = Paint.Style.FILL
                }
                canvas.drawPath(mountainPath, mtnPaint)
                paint.strokeWidth = 2.5f
                canvas.drawPath(mountainPath, paint)

                // 5. Caduceus / Rod of Asclepius in bottom center of shield
                val staffPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.rgb(30, 40, 50)
                    strokeWidth = 4f
                    style = Paint.Style.STROKE
                }
                canvas.drawLine(cx, cy - radius * 0.05f, cx, cy + radius * 0.45f, staffPaint)

                // Snake winding
                val snakePath = Path().apply {
                    moveTo(cx - 10f, cy + radius * 0.35f)
                    quadTo(cx + 15f, cy + radius * 0.25f, cx, cy + radius * 0.15f)
                    quadTo(cx - 15f, cy + radius * 0.05f, cx + 5f, cy - radius * 0.02f)
                }
                staffPaint.strokeWidth = 3f
                canvas.drawPath(snakePath, staffPaint)

                // 6. Bottom Ribbon / Banner: "CATEGORY D HOSPITAL" & "PACHA KALAY - PIR BABA - BUNER"
                val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.rgb(20, 30, 40)
                    textSize = 15f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    textAlign = Paint.Align.CENTER
                }
                canvas.drawText("CATEGORY D HOSPITAL", cx, cy + radius * 0.78f, textPaint)
                textPaint.textSize = 12f
                canvas.drawText("PACHA KALAY - PIR BABA - BUNER", cx, cy + radius * 0.90f, textPaint)
            }
            "CADUCEUS_HEALTH" -> {
                // Medical Caduceus with wings and two snakes
                val staffPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.rgb(15, 75, 130)
                    strokeWidth = 6f
                    style = Paint.Style.STROKE
                }
                // Staff
                canvas.drawLine(cx, cy - radius * 0.6f, cx, cy + radius * 0.7f, staffPaint)
                // Top sphere
                val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.rgb(15, 75, 130)
                    style = Paint.Style.FILL
                }
                canvas.drawCircle(cx, cy - radius * 0.65f, 14f, fillPaint)

                // Wings
                val wingPath = Path().apply {
                    moveTo(cx, cy - radius * 0.50f)
                    cubicTo(cx - radius * 0.4f, cy - radius * 0.75f, cx - radius * 0.7f, cy - radius * 0.6f, cx - radius * 0.65f, cy - radius * 0.3f)
                    quadTo(cx - radius * 0.3f, cy - radius * 0.35f, cx, cy - radius * 0.35f)
                    moveTo(cx, cy - radius * 0.50f)
                    cubicTo(cx + radius * 0.4f, cy - radius * 0.75f, cx + radius * 0.7f, cy - radius * 0.6f, cx + radius * 0.65f, cy - radius * 0.3f)
                    quadTo(cx + radius * 0.3f, cy - radius * 0.35f, cx, cy - radius * 0.35f)
                }
                staffPaint.strokeWidth = 3f
                canvas.drawPath(wingPath, fillPaint)

                // Double snakes
                val snakePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.rgb(15, 75, 130)
                    style = Paint.Style.STROKE
                    strokeWidth = 4f
                }
                val s1 = Path().apply {
                    moveTo(cx - 20f, cy + radius * 0.5f)
                    cubicTo(cx + 30f, cy + radius * 0.3f, cx - 30f, cy + radius * 0.1f, cx + 25f, cy - radius * 0.1f)
                    cubicTo(cx - 25f, cy - radius * 0.25f, cx + 20f, cy - radius * 0.35f, cx - 10f, cy - radius * 0.45f)
                }
                canvas.drawPath(s1, snakePaint)

                // Outer decorative circle
                paint.color = Color.rgb(15, 75, 130)
                paint.strokeWidth = 4f
                canvas.drawCircle(cx, cy, radius, paint)
                paint.strokeWidth = 1.5f
                canvas.drawCircle(cx, cy, radius - 8f, paint)
            }
            else -> {
                // Default clean institutional crest
                paint.color = Color.rgb(20, 40, 60)
                paint.strokeWidth = 5f
                canvas.drawCircle(cx, cy, radius, paint)
                paint.strokeWidth = 2f
                canvas.drawCircle(cx, cy, radius - 10f, paint)

                val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.rgb(20, 40, 60)
                    textSize = 28f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    textAlign = Paint.Align.CENTER
                }
                canvas.drawText("HEALTH SERVICES", cx, cy - 10f, textPaint)
                textPaint.textSize = 20f
                canvas.drawText("GOVT OF KP", cx, cy + 30f, textPaint)
            }
        }

        return bitmap
    }

    /**
     * Generates a realistic official circular rubber stamp for the Medical Superintendent.
     */
    fun createOfficialStampBitmap(
        hospitalName: String = "CATEGORY-D HOSPITAL PACHA KALAY BUNER",
        designation: String = "MEDICAL SUPERINTENDENT",
        signatory: String = "Dr. Tahir Khan",
        widthPx: Int = 360,
        heightPx: Int = 360
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val cx = widthPx / 2f
        val cy = heightPx / 2f
        val radius = widthPx * 0.44f

        // Classic violet/blue official stamp ink: #1A365D or #283593 with realistic stamp texture
        val inkColor = Color.rgb(35, 55, 130)

        val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = inkColor
            style = Paint.Style.STROKE
            strokeWidth = 4.5f
        }
        // Outer border
        canvas.drawCircle(cx, cy, radius, circlePaint)
        // Inner thin border
        circlePaint.strokeWidth = 2f
        canvas.drawCircle(cx, cy, radius - 10f, circlePaint)
        // Core border
        circlePaint.strokeWidth = 1.5f
        canvas.drawCircle(cx, cy, radius - 55f, circlePaint)

        // Arc path for top curved text: "Medical Superintendent"
        val topPath = Path().apply {
            val oval = RectF(cx - radius + 22f, cy - radius + 22f, cx + radius - 22f, cy + radius - 22f)
            addArc(oval, 180f, 180f)
        }
        val topTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = inkColor
            textSize = 15f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawTextOnPath(designation.uppercase(), topPath, 0f, 0f, topTextPaint)

        // Arc path for bottom curved text: "Cat D Hospital Pacha Kalay Buner"
        val bottomPath = Path().apply {
            val oval = RectF(cx - radius + 32f, cy - radius + 32f, cx + radius - 32f, cy + radius - 32f)
            addArc(oval, 180f, -180f)
        }
        val bottomTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = inkColor
            textSize = 13f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        val shortHosp = if (hospitalName.length > 32) "CAT-D HOSPITAL PACHA KALAY" else hospitalName.uppercase()
        canvas.drawTextOnPath(shortHosp, bottomPath, 0f, 0f, bottomTextPaint)

        // Center cursive/freehand signature representation inside the seal
        val sigPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = inkColor
            style = Paint.Style.STROKE
            strokeWidth = 2.5f
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }
        val sigPath = Path().apply {
            moveTo(cx - 45f, cy + 5f)
            quadTo(cx - 20f, cy - 25f, cx, cy - 10f)
            quadTo(cx + 25f, cy + 15f, cx + 45f, cy - 15f)
            quadTo(cx + 10f, cy + 25f, cx - 35f, cy + 15f)
            lineTo(cx + 35f, cy + 18f)
        }
        canvas.drawPath(sigPath, sigPaint)

        // Two stars separating top and bottom text
        val starPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = inkColor
            style = Paint.Style.FILL
        }
        val leftStar = createStarPath(cx - radius + 15f, cy, 6f, 3f)
        val rightStar = createStarPath(cx + radius - 15f, cy, 6f, 3f)
        canvas.drawPath(leftStar, starPaint)
        canvas.drawPath(rightStar, starPaint)

        return bitmap
    }

    private fun createStarPath(cx: Float, cy: Float, outerR: Float, innerR: Float): Path {
        val path = Path()
        val step = Math.PI / 5.0
        var angle = -Math.PI / 2.0
        path.moveTo((cx + outerR * Math.cos(angle)).toFloat(), (cy + outerR * Math.sin(angle)).toFloat())
        for (i in 0 until 5) {
            angle += step
            path.lineTo((cx + innerR * Math.cos(angle)).toFloat(), (cy + innerR * Math.sin(angle)).toFloat())
            angle += step
            path.lineTo((cx + outerR * Math.cos(angle)).toFloat(), (cy + outerR * Math.sin(angle)).toFloat())
        }
        path.close()
        return path
    }
}
