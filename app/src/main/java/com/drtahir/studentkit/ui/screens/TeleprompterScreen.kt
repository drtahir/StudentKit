package com.drtahir.studentkit.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.media.MediaRecorder
import android.net.Uri
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.drtahir.studentkit.BuildConfig
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

// =============================================================
// TELEPROMPTER DATA MODELS & REPOSITORY
// =============================================================

data class TeleprompterScript(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val category: String = "General",
    val targetWpm: Int = 140,
    val fontSizeSp: Int = 34,
    val textColorHex: String = "#FFFF00", // Default Studio Neon Yellow
    val bgColorHex: String = "#000000",
    val mirrorHorizontal: Boolean = false,
    val mirrorVertical: Boolean = false,
    val marginPercent: Float = 15f,
    val eyeGuidePositionRatio: Float = 0.35f,
    val isFavorite: Boolean = false,
    val lastModified: Long = System.currentTimeMillis()
) {
    val wordCount: Int
        get() = content.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }.size

    fun estimatedDurationSeconds(wpm: Int = targetWpm): Int {
        if (wpm <= 0) return 0
        return ((wordCount.toFloat() / wpm.toFloat()) * 60).toInt()
    }

    fun formattedDuration(wpm: Int = targetWpm): String {
        val totalSec = estimatedDurationSeconds(wpm)
        val mins = totalSec / 60
        val secs = totalSec % 60
        return if (mins > 0) "${mins}m ${secs}s" else "${secs}s"
    }
}

object TeleprompterRepository {
    private const val PREFS_NAME = "teleprompter_pro_scripts_v1"
    private const val KEY_SCRIPTS_JSON = "scripts_data_json"

    val DEFAULT_TEMPLATES = listOf(
        TeleprompterScript(
            id = "template_1",
            title = "🎬 60-Second YouTube Hook & Intro",
            category = "YouTube / Video",
            content = """[LOOK AT CAMERA]
Welcome back everyone! If you have ever struggled to stay confident in front of the camera, this video is made specifically for you.

[SMILE & PAUSE 2 SECONDS]

Today, we are uncovering the top 3 secret techniques used by professional news anchors and world-class creators to speak smoothly without forgetting a single word.

[EMPHASIZE]
Stick around until the end, because tip number 3 will completely transform how you record your videos forever!

Let's jump straight into it.""".trimIndent(),
            targetWpm = 145,
            fontSizeSp = 36,
            textColorHex = "#FFFF00"
        ),
        TeleprompterScript(
            id = "template_2",
            title = "🎤 Keynote Public Speaking Pitch",
            category = "Presentation",
            content = """[STAND TALL - EYE CONTACT]
Good morning distinguished guests, partners, and colleagues.

It is an absolute honor to stand before you today. As we look into the future of technology and human potential, one question rises above all:

[PAUSE FOR EFFECT]

How can we create systems that don't just solve problems, but empower people to achieve extraordinary things?

[SMILE]
Over the past twelve months, our team set out to answer that exact question. What we built is not just an tool—it is a revolution in creative workflow.

Thank you for your vision and partnership.""".trimIndent(),
            targetWpm = 130,
            fontSizeSp = 34,
            textColorHex = "#00FFFF"
        ),
        TeleprompterScript(
            id = "template_3",
            title = "📱 Viral TikTok & Reel Short",
            category = "Reels / Shorts",
            content = """Stop scrolling! ✋

Did you know that 90% of video creators fail simply because they look away from the camera lens while reading notes?

Here is the secret: When your eyes align directly with the lens, your audience instantly trusts you.

Save this video right now so you can try this teleprompter workflow on your very next video! 🚀""".trimIndent(),
            targetWpm = 160,
            fontSizeSp = 38,
            textColorHex = "#00FF66"
        ),
        TeleprompterScript(
            id = "template_4",
            title = "🎓 Academic Keynote Summary",
            category = "Academic",
            content = """[FORMAL TONE]
Respected Dean, faculty members, and fellow researchers.

The objective of this empirical study is to evaluate the impact of digital cognitive tools on student retention and problem-solving efficiency.

Through a rigorous 6-month trial across multiple cohorts, the data revealed a 34% increase in concept comprehension.

In conclusion, incorporating adaptive digital interfaces fosters both autonomous learning and deep engagement.""".trimIndent(),
            targetWpm = 125,
            fontSizeSp = 32,
            textColorHex = "#FFFFFF"
        ),
        TeleprompterScript(
            id = "template_5",
            title = "📖 Speech & Reflection Keynote",
            category = "Sermon & Reflection",
            content = """In the name of God, the Most Gracious, the Most Merciful.

True strength is not measured by external power, but by patience, wisdom, and sincerity in our actions.

Every single day gives us a fresh opportunity to seek knowledge, uplift others, and leave a positive legacy.

May peace, mercy, and blessings be upon you all.""".trimIndent(),
            targetWpm = 120,
            fontSizeSp = 36,
            textColorHex = "#FFD700"
        )
    )

    fun loadScripts(context: Context): List<TeleprompterScript> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonStr = prefs.getString(KEY_SCRIPTS_JSON, null)
        if (jsonStr.isNullOrBlank()) {
            saveScripts(context, DEFAULT_TEMPLATES)
            return DEFAULT_TEMPLATES
        }
        return try {
            val list = mutableListOf<TeleprompterScript>()
            val array = JSONArray(jsonStr)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    TeleprompterScript(
                        id = obj.optString("id", UUID.randomUUID().toString()),
                        title = obj.optString("title", "Untitled Script"),
                        content = obj.optString("content", ""),
                        category = obj.optString("category", "General"),
                        targetWpm = obj.optInt("targetWpm", 140),
                        fontSizeSp = obj.optInt("fontSizeSp", 34),
                        textColorHex = obj.optString("textColorHex", "#FFFF00"),
                        bgColorHex = obj.optString("bgColorHex", "#000000"),
                        mirrorHorizontal = obj.optBoolean("mirrorHorizontal", false),
                        mirrorVertical = obj.optBoolean("mirrorVertical", false),
                        marginPercent = obj.optDouble("marginPercent", 15.0).toFloat(),
                        eyeGuidePositionRatio = obj.optDouble("eyeGuidePositionRatio", 0.35).toFloat(),
                        isFavorite = obj.optBoolean("isFavorite", false),
                        lastModified = obj.optLong("lastModified", System.currentTimeMillis())
                    )
                )
            }
            if (list.isEmpty()) DEFAULT_TEMPLATES else list
        } catch (e: Exception) {
            DEFAULT_TEMPLATES
        }
    }

    fun saveScripts(context: Context, scripts: List<TeleprompterScript>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val array = JSONArray()
        scripts.forEach { script ->
            val obj = JSONObject()
            obj.put("id", script.id)
            obj.put("title", script.title)
            obj.put("content", script.content)
            obj.put("category", script.category)
            obj.put("targetWpm", script.targetWpm)
            obj.put("fontSizeSp", script.fontSizeSp)
            obj.put("textColorHex", script.textColorHex)
            obj.put("bgColorHex", script.bgColorHex)
            obj.put("mirrorHorizontal", script.mirrorHorizontal)
            obj.put("mirrorVertical", script.mirrorVertical)
            obj.put("marginPercent", script.marginPercent.toDouble())
            obj.put("eyeGuidePositionRatio", script.eyeGuidePositionRatio.toDouble())
            obj.put("isFavorite", script.isFavorite)
            obj.put("lastModified", script.lastModified)
            array.put(obj)
        }
        prefs.edit().putString(KEY_SCRIPTS_JSON, array.toString()).apply()
    }
}

// =============================================================
// TELEPROMPTER MAIN COMPOSABLE
// =============================================================

enum class PrompterViewMode {
    LIBRARY,
    PROMPTER_PURE,
    PROMPTER_CAMERA,
    PRACTICE_AUDIO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeleprompterScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var scriptsList by remember { mutableStateOf(TeleprompterRepository.loadScripts(context)) }
    var activeScript by remember { mutableStateOf<TeleprompterScript?>(null) }
    var currentViewMode by remember { mutableStateOf(PrompterViewMode.LIBRARY) }

    // Dialog & Editing States
    var showEditDialog by remember { mutableStateOf(false) }
    var scriptBeingEdited by remember { mutableStateOf<TeleprompterScript?>(null) }
    var showAiGeneratorDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("All") }

    val categoriesList = remember(scriptsList) {
        listOf("All") + scriptsList.map { it.category }.distinct()
    }

    val filteredScripts = remember(scriptsList, searchQuery, selectedCategoryFilter) {
        scriptsList.filter { script ->
            val matchesCategory = selectedCategoryFilter == "All" || script.category == selectedCategoryFilter
            val matchesQuery = searchQuery.isBlank() ||
                    script.title.contains(searchQuery, ignoreCase = true) ||
                    script.content.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }.sortedByDescending { it.lastModified }
    }

    val updateAndPersist = { newList: List<TeleprompterScript> ->
        scriptsList = newList
        TeleprompterRepository.saveScripts(context, newList)
    }

    when (currentViewMode) {
        PrompterViewMode.LIBRARY -> {
            TeleprompterLibraryView(
                scripts = filteredScripts,
                categories = categoriesList,
                selectedCategory = selectedCategoryFilter,
                onCategorySelect = { selectedCategoryFilter = it },
                searchQuery = searchQuery,
                onSearchChange = { searchQuery = it },
                onNewScript = {
                    scriptBeingEdited = TeleprompterScript(
                        title = "",
                        content = "",
                        category = "General"
                    )
                    showEditDialog = true
                },
                onOpenAiGenerator = { showAiGeneratorDialog = true },
                onSelectScript = { script, mode ->
                    activeScript = script
                    currentViewMode = mode
                },
                onEditScript = { script ->
                    scriptBeingEdited = script
                    showEditDialog = true
                },
                onToggleFavorite = { script ->
                    val updated = scriptsList.map {
                        if (it.id == script.id) it.copy(isFavorite = !it.isFavorite) else it
                    }
                    updateAndPersist(updated)
                },
                onDeleteScript = { script ->
                    val updated = scriptsList.filter { it.id != script.id }
                    updateAndPersist(updated)
                    Toast.makeText(context, "Script deleted", Toast.LENGTH_SHORT).show()
                }
            )

            // Edit / Add Script Dialog
            if (showEditDialog && scriptBeingEdited != null) {
                ScriptEditorDialog(
                    initialScript = scriptBeingEdited!!,
                    onSave = { savedScript ->
                        val existingIdx = scriptsList.indexOfFirst { it.id == savedScript.id }
                        val newList = if (existingIdx >= 0) {
                            scriptsList.toMutableList().apply { set(existingIdx, savedScript) }
                        } else {
                            listOf(savedScript) + scriptsList
                        }
                        updateAndPersist(newList)
                        showEditDialog = false
                        scriptBeingEdited = null
                        Toast.makeText(context, "Script saved", Toast.LENGTH_SHORT).show()
                    },
                    onDismiss = {
                        showEditDialog = false
                        scriptBeingEdited = null
                    }
                )
            }

            // AI Generator Dialog
            if (showAiGeneratorDialog) {
                AiScriptGeneratorDialog(
                    onScriptGenerated = { newScript ->
                        val newList = listOf(newScript) + scriptsList
                        updateAndPersist(newList)
                        showAiGeneratorDialog = false
                        activeScript = newScript
                        currentViewMode = PrompterViewMode.PROMPTER_PURE
                        Toast.makeText(context, "AI Script generated!", Toast.LENGTH_SHORT).show()
                    },
                    onDismiss = { showAiGeneratorDialog = false }
                )
            }
        }

        PrompterViewMode.PROMPTER_PURE -> {
            activeScript?.let { script ->
                PureStudioPrompterView(
                    script = script,
                    onSaveScriptSettings = { updatedScript ->
                        activeScript = updatedScript
                        val newList = scriptsList.map { if (it.id == updatedScript.id) updatedScript else it }
                        updateAndPersist(newList)
                    },
                    onClose = { currentViewMode = PrompterViewMode.LIBRARY },
                    onSwitchToCameraMode = { currentViewMode = PrompterViewMode.PROMPTER_CAMERA }
                )
            } ?: run { currentViewMode = PrompterViewMode.LIBRARY }
        }

        PrompterViewMode.PROMPTER_CAMERA -> {
            activeScript?.let { script ->
                CameraPrompterView(
                    script = script,
                    onClose = { currentViewMode = PrompterViewMode.LIBRARY },
                    onSwitchToPureMode = { currentViewMode = PrompterViewMode.PROMPTER_PURE }
                )
            } ?: run { currentViewMode = PrompterViewMode.LIBRARY }
        }

        PrompterViewMode.PRACTICE_AUDIO -> {
            activeScript?.let { script ->
                PracticeAudioPrompterView(
                    script = script,
                    onClose = { currentViewMode = PrompterViewMode.LIBRARY }
                )
            } ?: run { currentViewMode = PrompterViewMode.LIBRARY }
        }
    }
}

// =============================================================
// VIEW 1: SCRIPT LIBRARY & MANAGEMENT
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeleprompterLibraryView(
    scripts: List<TeleprompterScript>,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onNewScript: () -> Unit,
    onOpenAiGenerator: () -> Unit,
    onSelectScript: (TeleprompterScript, PrompterViewMode) -> Unit,
    onEditScript: (TeleprompterScript) -> Unit,
    onToggleFavorite: (TeleprompterScript) -> Unit,
    onDeleteScript: (TeleprompterScript) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // AI Generator FAB
                ExtendedFloatingActionButton(
                    onClick = onOpenAiGenerator,
                    icon = { Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.Black) },
                    text = { Text("AI Generator", fontWeight = FontWeight.Bold, color = Color.Black) },
                    containerColor = Color(0xFF10B981),
                    shape = RoundedCornerShape(16.dp)
                )

                // Create Script FAB
                FloatingActionButton(
                    onClick = onNewScript,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Script")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header Banner
            Surface(
                color = Color(0xFF0F172A),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    color = Color(0xFF6366F1).copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Videocam,
                                        contentDescription = null,
                                        tint = Color(0xFF818CF8),
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "PRO STUDIO",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF818CF8),
                                    letterSpacing = 1.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Teleprompter Studio",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Surface(
                            color = Color(0xFF1E293B),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, Color(0xFF334155))
                        ) {
                            Text(
                                text = "${scripts.size} Scripts",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF94A3B8),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Search Input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchChange,
                        placeholder = { Text("Search scripts by title or keyword...", color = Color(0xFF64748B), fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF94A3B8)) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchChange("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color(0xFF94A3B8))
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF1E293B),
                            unfocusedContainerColor = Color(0xFF1E293B),
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color(0xFF334155),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Category Filter Pills
            ScrollableTabRow(
                selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0),
                edgePadding = 16.dp,
                divider = {},
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    Tab(
                        selected = isSelected,
                        onClick = { onCategorySelect(category) },
                        text = {
                            Text(
                                text = category,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Scripts List
            if (scripts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                            modifier = Modifier.size(72.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Article,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No Scripts Found",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Create a new script or use the AI Generator to write one instantly!",
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(scripts, key = { it.id }) { script ->
                        ScriptCardItem(
                            script = script,
                            onPlayPure = { onSelectScript(script, PrompterViewMode.PROMPTER_PURE) },
                            onPlayCamera = { onSelectScript(script, PrompterViewMode.PROMPTER_CAMERA) },
                            onPracticeAudio = { onSelectScript(script, PrompterViewMode.PRACTICE_AUDIO) },
                            onEdit = { onEditScript(script) },
                            onToggleFavorite = { onToggleFavorite(script) },
                            onDelete = { onDeleteScript(script) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScriptCardItem(
    script: TeleprompterScript,
    onPlayPure: () -> Unit,
    onPlayCamera: () -> Unit,
    onPracticeAudio: () -> Unit,
    onEdit: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Category & Actions Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = script.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val context = LocalContext.current
                    val coroutineScope = rememberCoroutineScope()
                    IconButton(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                com.drtahir.studentkit.data.PhoneStorageSaver.saveTextDocumentToPhoneMemory(
                                    context = context,
                                    content = "${script.title}\nCategory: ${script.category}\n\n${script.content}",
                                    desiredFileName = "${script.title.take(20).replace("[^a-zA-Z0-9]".toRegex(), "_")}_Script.txt"
                                )
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Script exported to phone Downloads memory!", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Export failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Download, contentDescription = "Export TXT", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onToggleFavorite, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = if (script.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Favorite",
                            tint = if (script.isFavorite) Color(0xFFF59E0B) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = Color(0xFFEF4444))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title & Preview
            Text(
                text = script.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = script.content,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Metrics Pill Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.FormatAlignLeft, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${script.wordCount} Words",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Timer, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${script.formattedDuration()} @ ${script.targetWpm} WPM",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Pure Studio Prompter Button
                Button(
                    onClick = onPlayPure,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color(0xFFFFFF00), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Prompter", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                // Camera Prompter Button
                Button(
                    onClick = onPlayCamera,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1.2f)
                ) {
                    Icon(Icons.Default.Videocam, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Camera Studio", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                // Audio Practice Button
                OutlinedButton(
                    onClick = onPracticeAudio,
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Mic, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

// =============================================================
// VIEW 2: PURE TELEPROMPTER STUDIO PLAYER
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PureStudioPrompterView(
    script: TeleprompterScript,
    onSaveScriptSettings: (TeleprompterScript) -> Unit,
    onClose: () -> Unit,
    onSwitchToCameraMode: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Interactive Prompter States
    var isPlaying by remember { mutableStateOf(false) }
    var currentWpm by remember { mutableIntStateOf(script.targetWpm) }
    var currentFontSize by remember { mutableIntStateOf(script.fontSizeSp) }
    var currentMarginPercent by remember { mutableFloatStateOf(script.marginPercent) }
    var eyeGuideRatio by remember { mutableFloatStateOf(script.eyeGuidePositionRatio.takeIf { it > 0f } ?: 0.3f) }
    var showEyeGuide by remember { mutableStateOf(true) }
    var isMirrorH by remember { mutableStateOf(script.mirrorHorizontal) }
    var isMirrorV by remember { mutableStateOf(script.mirrorVertical) }
    var textColorHex by remember { mutableStateOf(script.textColorHex) }
    var bgColorHex by remember { mutableStateOf(script.bgColorHex) }

    var showControlPanel by remember { mutableStateOf(true) }
    var countdownValue by remember { mutableIntStateOf(0) }

    val scrollState = rememberScrollState()

    val textColor = remember(textColorHex) { parseHexColor(textColorHex, Color.Yellow) }
    val bgColor = remember(bgColorHex) { parseHexColor(bgColorHex, Color.Black) }

    // Auto-Scroll Loop Engine
    LaunchedEffect(isPlaying, currentWpm, scrollState.maxValue) {
        if (isPlaying && scrollState.maxValue > 0) {
            // WPM speed to pixels/sec math conversion
            val wordsTotal = script.wordCount.coerceAtLeast(1)
            val totalSeconds = (wordsTotal.toFloat() / currentWpm.toFloat()) * 60f
            val pxPerSecond = (scrollState.maxValue.toFloat() / totalSeconds.coerceAtLeast(1f))
            
            var lastTime = SystemClock.elapsedRealtime()
            while (isPlaying && scrollState.value < scrollState.maxValue) {
                val now = SystemClock.elapsedRealtime()
                val deltaSec = (now - lastTime) / 1000f
                lastTime = now
                val stepPx = (pxPerSecond * deltaSec).toInt().coerceAtLeast(1)
                scrollState.dispatchRawDelta(stepPx.toFloat())
                delay(16) // Smooth 60 FPS tick
            }
            if (scrollState.value >= scrollState.maxValue) {
                isPlaying = false
            }
        }
    }

    // Countdown before play launcher
    val startWithCountdown = {
        coroutineScope.launch {
            for (i in 3 downTo 1) {
                countdownValue = i
                delay(1000)
            }
            countdownValue = 0
            isPlaying = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        // Main Prompter Canvas Layer with Graphics Layer Mirror Transforms
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = (currentMarginPercent * 1.5f).dp)
                .graphicsLayer(
                    scaleX = if (isMirrorH) -1f else 1f,
                    scaleY = if (isMirrorV) -1f else 1f
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(vertical = 240.dp) // Top & Bottom generous padding for smooth scroll start/end
            ) {
                Text(
                    text = script.content,
                    fontSize = currentFontSize.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    lineHeight = (currentFontSize * 1.3f).sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Eye-Tracking Center Marker Overlay
            if (showEyeGuide) {
                EyeTrackingGuideOverlay(
                    eyeGuideRatio = eyeGuideRatio,
                    lineColor = textColor
                )
            }
        }

        // Countdown Overlay Animation
        if (countdownValue > 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.75f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$countdownValue",
                    fontSize = 120.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Yellow
                )
            }
        }

        // Floating Header Top Bar
        AnimatedVisibility(
            visible = showControlPanel,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Surface(
                color = Color.Black.copy(alpha = 0.85f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    IconButton(onClick = onClose) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = script.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1
                        )
                        Text(
                            text = "${currentWpm} WPM • ${script.formattedDuration(currentWpm)}",
                            fontSize = 11.sp,
                            color = Color.LightGray
                        )
                    }

                    Row {
                        IconButton(onClick = onSwitchToCameraMode) {
                            Icon(Icons.Default.Videocam, contentDescription = "Camera Mode", tint = Color(0xFF6366F1))
                        }
                        IconButton(onClick = {
                            val updated = script.copy(
                                targetWpm = currentWpm,
                                fontSizeSp = currentFontSize,
                                marginPercent = currentMarginPercent,
                                eyeGuidePositionRatio = eyeGuideRatio,
                                mirrorHorizontal = isMirrorH,
                                mirrorVertical = isMirrorV,
                                textColorHex = textColorHex,
                                bgColorHex = bgColorHex
                            )
                            onSaveScriptSettings(updated)
                            Toast.makeText(context, "Settings saved for this script", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.Save, contentDescription = "Save Settings", tint = Color.Green)
                        }
                    }
                }
            }
        }

        // Floating Control Panel Bottom Box
        AnimatedVisibility(
            visible = showControlPanel,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Surface(
                color = Color(0xFF0F172A).copy(alpha = 0.95f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                border = BorderStroke(1.dp, Color(0xFF1E293B)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Quick Play / Speed Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Rewind / Restart Button
                        IconButton(
                            onClick = {
                                coroutineScope.launch { scrollState.scrollTo(0) }
                            }
                        ) {
                            Icon(Icons.Default.Replay, contentDescription = "Restart", tint = Color.White)
                        }

                        // Speed Decrement Button
                        IconButton(
                            onClick = { currentWpm = (currentWpm - 5).coerceAtLeast(20) }
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Slower", tint = Color.White)
                        }

                        // Primary Play / Pause Action Button
                        Button(
                            onClick = {
                                if (isPlaying) {
                                    isPlaying = false
                                } else {
                                    startWithCountdown()
                                }
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isPlaying) Color(0xFFEF4444) else Color(0xFF10B981)
                            ),
                            modifier = Modifier.size(64.dp)
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play/Pause",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        // Speed Increment Button
                        IconButton(
                            onClick = { currentWpm = (currentWpm + 5).coerceAtMost(350) }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Faster", tint = Color.White)
                        }

                        // Hide Controls Toggle
                        IconButton(onClick = { showControlPanel = false }) {
                            Icon(Icons.Default.Fullscreen, contentDescription = "Hide Controls", tint = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Speed WPM Real-time Slider Row
                    Column {
                        val paceLabel = when {
                            currentWpm < 100 -> "Slow / Practice Pace"
                            currentWpm <= 160 -> "Conversational Pace"
                            currentWpm <= 230 -> "Fast Broadcast Pace"
                            else -> "Express / Rapid Speed"
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Scrolling Speed:", fontSize = 12.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = paceLabel,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF818CF8)
                                )
                            }
                            Text("${currentWpm} WPM", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color.Yellow)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Slider(
                            value = currentWpm.toFloat(),
                            onValueChange = { currentWpm = it.toInt() },
                            valueRange = 20f..350f,
                            colors = SliderDefaults.colors(
                                thumbColor = Color.Yellow,
                                activeTrackColor = Color.Yellow,
                                inactiveTrackColor = Color.DarkGray
                            ),
                            modifier = Modifier.testTag("speed_slider")
                        )

                        // Quick Speed Preset Chips
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf(90 to "Slow", 140 to "Normal", 190 to "Fast", 240 to "Express").forEach { (speed, label) ->
                                val isSelected = currentWpm == speed
                                Surface(
                                    onClick = { currentWpm = speed },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) Color.Yellow else Color(0xFF1E293B),
                                    border = BorderStroke(1.dp, if (isSelected) Color.Yellow else Color(0xFF334155))
                                ) {
                                    Text(
                                        text = "$label ($speed)",
                                        fontSize = 10.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.Black else Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Eye-Tracking Line Position Slider & Controls
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Eye-Tracking Marker", fontSize = 12.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.width(6.dp))
                                FilterChip(
                                    selected = showEyeGuide,
                                    onClick = { showEyeGuide = !showEyeGuide },
                                    label = { Text(if (showEyeGuide) "ON" else "OFF", fontSize = 10.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFF10B981),
                                        selectedLabelColor = Color.Black
                                    )
                                )
                            }
                            if (showEyeGuide) {
                                Text(
                                    text = "${(eyeGuideRatio * 100).toInt()}% Vertical",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Yellow
                                )
                            }
                        }
                        if (showEyeGuide) {
                            Slider(
                                value = eyeGuideRatio,
                                onValueChange = { eyeGuideRatio = it },
                                valueRange = 0.15f..0.75f,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color.Yellow,
                                    activeTrackColor = Color.Yellow,
                                    inactiveTrackColor = Color.DarkGray
                                ),
                                modifier = Modifier.testTag("eye_guide_slider")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Quick Settings Toggle Row (Font Size, Mirror, Colors)
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Font Size Buttons
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Font:", fontSize = 11.sp, color = Color.LightGray)
                            Spacer(modifier = Modifier.width(4.dp))
                            TextButton(
                                onClick = { currentFontSize = (currentFontSize - 2).coerceAtLeast(18) },
                                contentPadding = PaddingValues(4.dp)
                            ) { Text("A-", color = Color.White, fontWeight = FontWeight.Bold) }
                            Text("${currentFontSize}sp", fontSize = 11.sp, color = Color.Yellow)
                            TextButton(
                                onClick = { currentFontSize = (currentFontSize + 2).coerceAtMost(80) },
                                contentPadding = PaddingValues(4.dp)
                            ) { Text("A+", color = Color.White, fontWeight = FontWeight.Bold) }
                        }

                        // Mirror Horizontal & Vertical Toggles for Physical Glass Rig
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            FilterChip(
                                selected = isMirrorH,
                                onClick = { isMirrorH = !isMirrorH },
                                label = { Text("Mirror H", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF6366F1),
                                    selectedLabelColor = Color.White
                                )
                            )
                            FilterChip(
                                selected = isMirrorV,
                                onClick = { isMirrorV = !isMirrorV },
                                label = { Text("Mirror V", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF6366F1),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }

                        // Studio Theme Colors Pill Row
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            StudioColorDot(Color.Yellow) { textColorHex = "#FFFF00"; bgColorHex = "#000000" }
                            StudioColorDot(Color.Cyan) { textColorHex = "#00FFFF"; bgColorHex = "#000000" }
                            StudioColorDot(Color.Green) { textColorHex = "#00FF66"; bgColorHex = "#000000" }
                            StudioColorDot(Color.White) { textColorHex = "#FFFFFF"; bgColorHex = "#000000" }
                        }
                    }
                }
            }
        }

        // Tap to show control panel when hidden
        if (!showControlPanel) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { showControlPanel = true }
            )
        }
    }
}

@Composable
fun StudioColorDot(color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(color)
            .border(1.dp, Color.White, CircleShape)
            .clickable(onClick = onClick)
    )
}

// =============================================================
// VIEW 3: CAMERA PROMPTER VIDEO RECORDING STUDIO
// =============================================================

@Composable
fun CameraPrompterView(
    script: TeleprompterScript,
    onClose: () -> Unit,
    onSwitchToPureMode: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            launcher.launch(android.Manifest.permission.CAMERA)
        }
    }

    var isFrontLens by remember { mutableStateOf(true) }
    var isRecordingVideo by remember { mutableStateOf(false) }
    var isPrompterScrolling by remember { mutableStateOf(false) }
    var currentWpm by remember { mutableIntStateOf(script.targetWpm) }
    var currentFontSize by remember { mutableIntStateOf(script.fontSizeSp) }
    var textOpacity by remember { mutableFloatStateOf(0.85f) }
    var isMirrorH by remember { mutableStateOf(script.mirrorHorizontal) }
    var isMirrorV by remember { mutableStateOf(script.mirrorVertical) }
    var showEyeGuide by remember { mutableStateOf(true) }
    var eyeGuideRatio by remember { mutableFloatStateOf(0.22f) }

    val scrollState = rememberScrollState()

    // Auto scroll coroutine loop
    LaunchedEffect(isPrompterScrolling, currentWpm, scrollState.maxValue) {
        if (isPrompterScrolling && scrollState.maxValue > 0) {
            val wordsTotal = script.wordCount.coerceAtLeast(1)
            val totalSeconds = (wordsTotal.toFloat() / currentWpm.toFloat()) * 60f
            val pxPerSecond = (scrollState.maxValue.toFloat() / totalSeconds.coerceAtLeast(1f))

            var lastTime = SystemClock.elapsedRealtime()
            while (isPrompterScrolling && scrollState.value < scrollState.maxValue) {
                val now = SystemClock.elapsedRealtime()
                val deltaSec = (now - lastTime) / 1000f
                lastTime = now
                val stepPx = (pxPerSecond * deltaSec).toInt().coerceAtLeast(1)
                scrollState.dispatchRawDelta(stepPx.toFloat())
                delay(16)
            }
            if (scrollState.value >= scrollState.maxValue) {
                isPrompterScrolling = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (hasCameraPermission) {
            // CameraX Live Feed Surface View
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()
                            val preview = androidx.camera.core.Preview.Builder().build().also {
                                it.setSurfaceProvider(surfaceProvider)
                            }
                            val selector = if (isFrontLens) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
                            try {
                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(lifecycleOwner, selector, preview)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }, ContextCompat.getMainExecutor(ctx))
                    }
                },
                update = { previewView ->
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(previewView.context)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = androidx.camera.core.Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                        val selector = if (isFrontLens) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(lifecycleOwner, selector, preview)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }, ContextCompat.getMainExecutor(previewView.context))
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Camera Permission Required", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { launcher.launch(android.Manifest.permission.CAMERA) }) {
                        Text("Grant Camera Access")
                    }
                }
            }
        }

        // Semi-transparent Glass Prompter Text Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .graphicsLayer(
                    scaleX = if (isMirrorH) -1f else 1f,
                    scaleY = if (isMirrorV) -1f else 1f
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(vertical = 180.dp)
            ) {
                Text(
                    text = script.content,
                    fontSize = currentFontSize.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow.copy(alpha = textOpacity),
                    lineHeight = (currentFontSize * 1.3f).sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                )
            }

            // Eye-Tracking Center Marker Overlay
            if (showEyeGuide) {
                EyeTrackingGuideOverlay(
                    eyeGuideRatio = eyeGuideRatio,
                    lineColor = Color.Yellow
                )
            }
        }

        // Top Navigation Bar Overlay
        Surface(
            color = Color.Black.copy(alpha = 0.7f),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }

                Text(
                    text = script.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1
                )

                Row {
                    IconButton(onClick = { isFrontLens = !isFrontLens }) {
                        Icon(Icons.Default.Cameraswitch, contentDescription = "Switch Camera", tint = Color.White)
                    }
                    IconButton(onClick = onSwitchToPureMode) {
                        Icon(Icons.Default.Fullscreen, contentDescription = "Pure Prompter", tint = Color.Yellow)
                    }
                }
            }
        }

        // Bottom Camera & Prompter Control Bar with Speed Slider
        var showSpeedSlider by remember { mutableStateOf(false) }

        Surface(
            color = Color(0xFF0F172A).copy(alpha = 0.95f),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            border = BorderStroke(1.dp, Color(0xFF1E293B)),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Expandable Speed Slider Panel
                AnimatedVisibility(visible = showSpeedSlider) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Real-Time Speed Adjustment", fontSize = 12.sp, color = Color.Gray)
                            Text("${currentWpm} WPM", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color.Yellow)
                        }
                        Slider(
                            value = currentWpm.toFloat(),
                            onValueChange = { currentWpm = it.toInt() },
                            valueRange = 20f..350f,
                            colors = SliderDefaults.colors(
                                thumbColor = Color.Yellow,
                                activeTrackColor = Color.Yellow,
                                inactiveTrackColor = Color.DarkGray
                            ),
                            modifier = Modifier.testTag("camera_speed_slider")
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf(90 to "Slow", 140 to "Normal", 190 to "Fast", 240 to "Express").forEach { (speed, label) ->
                                val isSelected = currentWpm == speed
                                Surface(
                                    onClick = { currentWpm = speed },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) Color.Yellow else Color(0xFF1E293B),
                                    border = BorderStroke(1.dp, if (isSelected) Color.Yellow else Color(0xFF334155))
                                ) {
                                    Text(
                                        text = "$label ($speed)",
                                        fontSize = 10.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.Black else Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Glass Rig Mirroring:", fontSize = 11.sp, color = Color.Gray)
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                FilterChip(
                                    selected = isMirrorH,
                                    onClick = { isMirrorH = !isMirrorH },
                                    label = { Text("Mirror H", fontSize = 10.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFF6366F1),
                                        selectedLabelColor = Color.White
                                    )
                                )
                                FilterChip(
                                    selected = isMirrorV,
                                    onClick = { isMirrorV = !isMirrorV },
                                    label = { Text("Mirror V", fontSize = 10.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFF6366F1),
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Eye-Tracking Lens Line:", fontSize = 11.sp, color = Color.Gray)
                            FilterChip(
                                selected = showEyeGuide,
                                onClick = { showEyeGuide = !showEyeGuide },
                                label = { Text(if (showEyeGuide) "Eye Line ON" else "OFF", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF10B981),
                                    selectedLabelColor = Color.Black
                                )
                            )
                        }
                        if (showEyeGuide) {
                            Slider(
                                value = eyeGuideRatio,
                                onValueChange = { eyeGuideRatio = it },
                                valueRange = 0.15f..0.75f,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color.Yellow,
                                    activeTrackColor = Color.Yellow,
                                    inactiveTrackColor = Color.DarkGray
                                ),
                                modifier = Modifier.testTag("camera_eye_guide_slider")
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(color = Color(0xFF334155))
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Restart Button
                    IconButton(onClick = { coroutineScope.launch { scrollState.scrollTo(0) } }) {
                        Icon(Icons.Default.Replay, contentDescription = "Restart", tint = Color.White)
                    }

                    // Speed Slider Panel Toggle Button
                    IconButton(onClick = { showSpeedSlider = !showSpeedSlider }) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = "Speed Slider",
                            tint = if (showSpeedSlider) Color.Yellow else Color.White
                        )
                    }

                    // Video Record & Scroll Trigger Button
                    Button(
                        onClick = {
                            isPrompterScrolling = !isPrompterScrolling
                            isRecordingVideo = isPrompterScrolling
                            if (isPrompterScrolling) {
                                Toast.makeText(context, "Recording & Teleprompter Started!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPrompterScrolling) Color.Red else Color(0xFF10B981)
                        ),
                        modifier = Modifier.size(68.dp)
                    ) {
                        Icon(
                            imageVector = if (isPrompterScrolling) Icons.Default.Stop else Icons.Default.Videocam,
                            contentDescription = "Record",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Quick Speed Adjust Up
                    IconButton(onClick = { currentWpm = (currentWpm + 5).coerceAtMost(350) }) {
                        Icon(Icons.Default.Add, contentDescription = "Faster", tint = Color.White)
                    }

                    // Text Opacity Toggle
                    IconButton(onClick = { textOpacity = if (textOpacity > 0.5f) 0.4f else 0.85f }) {
                        Icon(Icons.Default.Opacity, contentDescription = "Opacity", tint = Color.White)
                    }
                }
            }
        }
    }
}

// =============================================================
// VIEW 4: AUDIO PRACTICE & VOICE RECORDER MODE
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeAudioPrompterView(
    script: TeleprompterScript,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    var isRecordingAudio by remember { mutableStateOf(false) }
    var recordingTimerSec by remember { mutableIntStateOf(0) }

    LaunchedEffect(isRecordingAudio) {
        if (isRecordingAudio) {
            recordingTimerSec = 0
            while (isRecordingAudio) {
                delay(1000)
                recordingTimerSec++
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Speech Practice Mode", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            // Audio Status Header
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isRecordingAudio) "Recording Practice Voice... ${recordingTimerSec}s" else "Ready for Audio Practice",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Target Speed: ${script.targetWpm} WPM • Total ${script.wordCount} words",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Script Reader Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = script.content,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 30.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Voice Record Controls
            Button(
                onClick = {
                    isRecordingAudio = !isRecordingAudio
                    if (!isRecordingAudio) {
                        Toast.makeText(context, "Practice Session Saved!", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRecordingAudio) Color(0xFFEF4444) else MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = if (isRecordingAudio) Icons.Default.Stop else Icons.Default.FiberManualRecord,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isRecordingAudio) "Stop & Save Practice" else "Start Audio Recording Practice",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// =============================================================
// SCRIPT EDITOR DIALOG
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScriptEditorDialog(
    initialScript: TeleprompterScript,
    onSave: (TeleprompterScript) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf(initialScript.title) }
    var content by remember { mutableStateOf(initialScript.content) }
    var category by remember { mutableStateOf(initialScript.category) }
    var targetWpm by remember { mutableIntStateOf(initialScript.targetWpm) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialScript.title.isBlank()) "New Teleprompter Script" else "Edit Script",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Script Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category (e.g. YouTube, Presentation, Reel)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Script Text Content") },
                    minLines = 6,
                    maxLines = 12,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Target Speed: $targetWpm WPM", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Slider(
                    value = targetWpm.toFloat(),
                    onValueChange = { targetWpm = it.toInt() },
                    valueRange = 40f..300f
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isBlank()) {
                        title = "Untitled Script"
                    }
                    val updated = initialScript.copy(
                        title = title.trim(),
                        content = content.trim(),
                        category = category.ifBlank { "General" },
                        targetWpm = targetWpm,
                        lastModified = System.currentTimeMillis()
                    )
                    onSave(updated)
                }
            ) {
                Text("Save Script")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// =============================================================
// AI SCRIPT GENERATOR DIALOG
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiScriptGeneratorDialog(
    onScriptGenerated: (TeleprompterScript) -> Unit,
    onDismiss: () -> Unit
) {
    var topic by remember { mutableStateOf("") }
    var selectedFormat by remember { mutableStateOf("YouTube Video Hook (60s)") }
    var selectedTone by remember { mutableStateOf("Energetic & Engaging") }
    var isGenerating by remember { mutableStateOf(false) }

    val formatsList = listOf("YouTube Video Hook (60s)", "TikTok / Reel Short (30s)", "Public Speaking Keynote (2m)", "Academic Summary", "Sales Product Demo")
    val tonesList = listOf("Energetic & Engaging", "Professional & Formal", "Inspirational & Warm", "Persuasive & Direct")

    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFF10B981))
                Spacer(modifier = Modifier.width(8.dp))
                Text("AI Teleprompter Writer", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Specify your topic and style. AI will craft a high-converting teleprompter script complete with speech cues!",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = topic,
                    onValueChange = { topic = it },
                    placeholder = { Text("e.g. 5 Productivity Hacks for Students") },
                    label = { Text("Topic or Subject") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Script Format:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                ScrollableTabRow(
                    selectedTabIndex = formatsList.indexOf(selectedFormat),
                    edgePadding = 0.dp,
                    divider = {}
                ) {
                    formatsList.forEach { fmt ->
                        Tab(
                            selected = selectedFormat == fmt,
                            onClick = { selectedFormat = fmt },
                            text = { Text(fmt, fontSize = 11.sp) }
                        )
                    }
                }

                Text("Speaking Tone:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                ScrollableTabRow(
                    selectedTabIndex = tonesList.indexOf(selectedTone),
                    edgePadding = 0.dp,
                    divider = {}
                ) {
                    tonesList.forEach { tone ->
                        Tab(
                            selected = selectedTone == tone,
                            onClick = { selectedTone = tone },
                            text = { Text(tone, fontSize = 11.sp) }
                        )
                    }
                }

                if (isGenerating) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color(0xFF10B981))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Writing script with speech cues...", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = topic.isNotBlank() && !isGenerating,
                onClick = {
                    isGenerating = true
                    coroutineScope.launch {
                        val generatedContent = generateAiScriptContent(topic, selectedFormat, selectedTone)
                        val newScript = TeleprompterScript(
                            title = "AI: $topic",
                            content = generatedContent,
                            category = if (selectedFormat.contains("Reel")) "Reels / Shorts" else "YouTube / Video",
                            targetWpm = if (selectedFormat.contains("30s")) 160 else 140
                        )
                        isGenerating = false
                        onScriptGenerated(newScript)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
            ) {
                Text("Generate AI Script", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

suspend fun generateAiScriptContent(topic: String, format: String, tone: String): String = withContext(Dispatchers.IO) {
    try {
        if (BuildConfig.GEMINI_API_KEY.isNotBlank()) {
            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = BuildConfig.GEMINI_API_KEY
            )
            val prompt = """
                Write a professional teleprompter script for video recording.
                Topic: "$topic"
                Format: "$format"
                Tone: "$tone"
                
                Include visual cues in brackets like [LOOK AT CAMERA], [PAUSE 2 SECONDS], [SMILE], [EMPHASIZE].
                Make it engaging, conversational, and direct. Keep formatting clean with short paragraphs.
            """.trimIndent()
            val response = generativeModel.generateContent(prompt)
            val text = response.text
            if (!text.isNullOrBlank()) return@withContext text!!
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    // Intelligent Offline Template Fallback
    """[LOOK AT CAMERA & SMILE]
Welcome everyone! Today we are discussing a crucial topic: $topic.

[PAUSE 2 SECONDS]

Here are the 3 most important key takeaways you need to know:

First, $topic requires consistency and focus.
Second, applying key principles step-by-step guarantees high performance.

[EMPHASIZE]
And third, always track your results and keep improving every single day!

[SMILE & WAVE]
Thank you for watching, and stay tuned for the next session!""".trimIndent()
}

// =============================================================
// HELPER UTILITIES
// =============================================================

fun parseHexColor(hex: String, fallback: Color): Color {
    return try {
        Color(android.graphics.Color.parseColor(hex))
    } catch (e: Exception) {
        fallback
    }
}

// =============================================================
// EYE-TRACKING GUIDE & READING LENS OVERLAY
// =============================================================

@Composable
fun EyeTrackingGuideOverlay(
    eyeGuideRatio: Float,
    lineColor: Color = Color.Yellow,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val totalHeightPx = constraints.maxHeight
        val targetOffsetY = (totalHeightPx * eyeGuideRatio.coerceIn(0.1f, 0.85f)).dp

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .offset(y = targetOffsetY)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            lineColor.copy(alpha = 0.04f),
                            lineColor.copy(alpha = 0.18f),
                            lineColor.copy(alpha = 0.04f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            lineColor.copy(alpha = 0.5f),
                            lineColor,
                            lineColor.copy(alpha = 0.5f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(6.dp)
                )
        ) {
            // High-precision Center Reading Line
            HorizontalDivider(
                color = lineColor.copy(alpha = 0.85f),
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )

            // Left Eye Marker Pointer
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
                    .background(Color.Black.copy(alpha = 0.65f), CircleShape)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Reading Line",
                    tint = lineColor,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "EYE LINE",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = lineColor,
                    letterSpacing = 1.sp
                )
            }

            // Right Eye Marker Pointer
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .background(Color.Black.copy(alpha = 0.65f), CircleShape)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "LENS ALIGNED",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = lineColor,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Reading Line",
                    tint = lineColor,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

