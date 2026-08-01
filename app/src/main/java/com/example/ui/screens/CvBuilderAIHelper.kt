package com.example.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.example.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File

data class ExtractedCvData(
    val fullName: String,
    val headline: String,
    val email: String,
    val phone: String,
    val location: String,
    val summaryText: String,
    val workExperiences: List<ResumeWorkHistory>,
    val academicList: List<ResumeAcademic>,
    val projectsList: List<ResumeProject>,
    val skillsCsv: String,
    val languagesCsv: String
)

suspend fun processCvWithAI(context: Context, uri: Uri, isPdf: Boolean, targetFormat: String): ExtractedCvData? = withContext(Dispatchers.IO) {
    try {
        val bitmaps = mutableListOf<Bitmap>()
        
        if (isPdf) {
            val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: return@withContext null
            val renderer = PdfRenderer(pfd)
            val pageCount = renderer.pageCount
            
            for (i in 0 until minOf(3, pageCount)) { // Max 3 pages
                val page = renderer.openPage(i)
                val bitmap = Bitmap.createBitmap(page.width * 2, page.height * 2, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                canvas.drawColor(Color.WHITE)
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                bitmaps.add(bitmap)
                page.close()
            }
            renderer.close()
            pfd.close()
        } else {
            // It's an image
            val bitmap = android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            bitmaps.add(bitmap)
        }

        if (bitmaps.isEmpty()) return@withContext null

        val generativeModel = GenerativeModel(
            modelName = "gemini-3.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )

        val prompt = """
            Extract all resume information from the provided images and convert it perfectly to the requested $targetFormat resume format. 
            Respond ONLY with a JSON object in this exact structure, with no markdown formatting or backticks around it:
            {
              "fullName": "...",
              "headline": "...",
              "email": "...",
              "phone": "...",
              "location": "...",
              "summaryText": "...",
              "workExperiences": [
                { "title": "...", "company": "...", "duration": "...", "duty1": "...", "duty2": "...", "description": "..." }
              ],
              "academicList": [
                { "degree": "...", "school": "...", "duration": "...", "grade": "..." }
              ],
              "projectsList": [
                { "title": "...", "techStack": "...", "url": "...", "impact": "..." }
              ],
              "skillsCsv": "...",
              "languagesCsv": "..."
            }
            Make sure the output is highly professional, 100% accurate, and conforms strictly to the regional resume standards for $targetFormat. 
            Tailor the summary and formatting rules to $targetFormat expectations (e.g., Canadian vs UAE formats differ in tone and detail).
        """.trimIndent()

        val inputContent = content {
            bitmaps.forEach { image(it) }
            text(prompt)
        }

        val response = generativeModel.generateContent(inputContent)
        var responseText = response.text ?: return@withContext null
        
        // Clean up markdown if any
        if (responseText.startsWith("```json")) {
            responseText = responseText.removePrefix("```json")
        }
        if (responseText.startsWith("```")) {
            responseText = responseText.removePrefix("```")
        }
        if (responseText.endsWith("```")) {
            responseText = responseText.removeSuffix("```")
        }
        
        val json = JSONObject(responseText.trim())
        
        val workList = mutableListOf<ResumeWorkHistory>()
        if (json.has("workExperiences")) {
            val arr = json.getJSONArray("workExperiences")
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                workList.add(ResumeWorkHistory(
                    title = obj.optString("title"),
                    company = obj.optString("company"),
                    duration = obj.optString("duration"),
                    duty1 = obj.optString("duty1"),
                    duty2 = obj.optString("duty2"),
                    description = obj.optString("description")
                ))
            }
        }
        
        val eduList = mutableListOf<ResumeAcademic>()
        if (json.has("academicList")) {
            val arr = json.getJSONArray("academicList")
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                eduList.add(ResumeAcademic(
                    degree = obj.optString("degree"),
                    school = obj.optString("school"),
                    duration = obj.optString("duration"),
                    grade = obj.optString("grade")
                ))
            }
        }
        
        val projList = mutableListOf<ResumeProject>()
        if (json.has("projectsList")) {
            val arr = json.getJSONArray("projectsList")
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                projList.add(ResumeProject(
                    title = obj.optString("title"),
                    techStack = obj.optString("techStack"),
                    url = obj.optString("url"),
                    impact = obj.optString("impact")
                ))
            }
        }
        
        ExtractedCvData(
            fullName = json.optString("fullName", ""),
            headline = json.optString("headline", ""),
            email = json.optString("email", ""),
            phone = json.optString("phone", ""),
            location = json.optString("location", ""),
            summaryText = json.optString("summaryText", ""),
            workExperiences = workList,
            academicList = eduList,
            projectsList = projList,
            skillsCsv = json.optString("skillsCsv", ""),
            languagesCsv = json.optString("languagesCsv", "")
        )

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
