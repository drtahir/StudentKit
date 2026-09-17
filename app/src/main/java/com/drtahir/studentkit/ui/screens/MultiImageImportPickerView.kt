package com.drtahir.studentkit.ui.screens

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CropFree
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.drtahir.studentkit.data.DocumentEdgeProcessor
import com.drtahir.studentkit.data.DocumentEdgeProcessor.ScanFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Thumbnail loader helper using native platform decoding for high performance.
 */
fun loadThumbnailFromUri(context: Context, uri: Uri, targetSize: Int = 320): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val size = Size(targetSize, targetSize)
            context.contentResolver.loadThumbnail(uri, size, null)
        } else {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeStream(stream, null, options)

                var inSampleSize = 1
                val height = options.outHeight
                val width = options.outWidth
                if (height > targetSize || width > targetSize) {
                    val halfHeight = height / 2
                    val halfWidth = width / 2
                    while ((halfHeight / inSampleSize) >= targetSize && (halfWidth / inSampleSize) >= targetSize) {
                        inSampleSize *= 2
                    }
                }

                context.contentResolver.openInputStream(uri)?.use { stream2 ->
                    val decodeOptions = BitmapFactory.Options().apply { this.inSampleSize = inSampleSize }
                    BitmapFactory.decodeStream(stream2, null, decodeOptions)
                }
            }
        }
    } catch (e: Exception) {
        null
    }
}

/**
 * Queries device gallery photos using Android MediaStore.
 */
fun queryDeviceGalleryPhotos(context: Context, limit: Int = 300): List<Uri> {
    val results = mutableListOf<Uri>()
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATE_ADDED
    )
    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
    val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    try {
        context.contentResolver.query(
            queryUri,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            var count = 0
            while (cursor.moveToNext() && count < limit) {
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                results.add(contentUri)
                count++
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return results
}

/**
 * Multi-Image Gallery Picker & Automatic Perspective Cropping Studio.
 * Matches CamScanner / Edge Scanner multi-image selection flow:
 * - Numbered selection badges (1, 2, 3...)
 * - Auto-crop checkbox with intelligent document corner detection
 * - Enhance filter presets (Magic Color, B&W, Grayscale, Lighten, Original)
 * - Immediate compilation into multi-page document studio
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiImageImportPickerView(
    onClose: () -> Unit,
    onImportCompleted: (List<Bitmap>) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val galleryPhotos = remember { mutableStateListOf<Uri>() }
    val selectedUris = remember { mutableStateListOf<Uri>() }
    var isLoadingGallery by remember { mutableStateOf(true) }

    // Bottom controls state
    var isAutoCropEnabled by remember { mutableStateOf(true) }
    var selectedFilter by remember { mutableStateOf(ScanFilter.ENHANCE) }
    var showFilterMenu by remember { mutableStateOf(false) }

    // Batch processing state
    var isProcessing by remember { mutableStateOf(false) }
    var processingProgress by remember { mutableIntStateOf(0) }
    var processingTotal by remember { mutableIntStateOf(0) }
    var processingStepTitle by remember { mutableStateOf("") }

    // Fallback / system file picker launcher
    val systemPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            uris.forEach { u ->
                if (!selectedUris.contains(u)) {
                    selectedUris.add(u)
                }
                if (!galleryPhotos.contains(u)) {
                    galleryPhotos.add(0, u)
                }
            }
        }
    }

    // Load gallery on screen entry
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val photos = queryDeviceGalleryPhotos(context)
            withContext(Dispatchers.Main) {
                galleryPhotos.clear()
                galleryPhotos.addAll(photos)
                isLoadingGallery = false
            }
        }
    }

    // Execution function: Process all selected photos with Auto Crop and Filter
    fun performImportAndCrop() {
        if (selectedUris.isEmpty()) {
            Toast.makeText(context, "Please select at least 1 image to import", Toast.LENGTH_SHORT).show()
            return
        }

        isProcessing = true
        processingTotal = selectedUris.size
        processingProgress = 0

        coroutineScope.launch(Dispatchers.IO) {
            val processedBitmaps = mutableListOf<Bitmap>()

            for ((index, uri) in selectedUris.withIndex()) {
                withContext(Dispatchers.Main) {
                    processingProgress = index + 1
                    processingStepTitle = if (isAutoCropEnabled) {
                        "Auto-Cropping & Enhancing Page ${index + 1} of ${selectedUris.size}..."
                    } else {
                        "Importing & Enhancing Page ${index + 1} of ${selectedUris.size}..."
                    }
                }

                val rawBitmap = loadAndCorrectOrientationFromUri(context, uri)
                if (rawBitmap != null) {
                    // Step 1: Document Edge Detection & Perspective Crop
                    val croppedBitmap = if (isAutoCropEnabled) {
                        try {
                            val corners = DocumentEdgeProcessor.detectDocumentCorners(rawBitmap)
                            DocumentEdgeProcessor.warpPerspectiveCrop(rawBitmap, corners)
                        } catch (e: Exception) {
                            rawBitmap
                        }
                    } else {
                        rawBitmap
                    }

                    // Step 2: Apply Selected Filter (Magic Color / Enhance, B&W, etc.)
                    val finalBitmap = if (selectedFilter != ScanFilter.ORIGINAL) {
                        try {
                            DocumentEdgeProcessor.applyFilter(croppedBitmap, selectedFilter)
                        } catch (e: Exception) {
                            croppedBitmap
                        }
                    } else {
                        croppedBitmap
                    }

                    processedBitmaps.add(finalBitmap)
                }
            }

            withContext(Dispatchers.Main) {
                isProcessing = false
                if (processedBitmaps.isNotEmpty()) {
                    onImportCompleted(processedBitmaps)
                } else {
                    Toast.makeText(context, "Failed to load selected images", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // =========================================================================
            // TOP BAR (✕ Close | Select Photo ▼ | Import(N) Button)
            // =========================================================================
            Surface(
                color = Color(0xFF1E1E1E),
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Close Button ✕
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Center: Select Photo dropdown header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { systemPickerLauncher.launch("image/*") }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Select Photo",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color(0xFF00C853),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Right: Import(N) Green Pill Button
                    Button(
                        onClick = { performImportAndCrop() },
                        enabled = selectedUris.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00C853),
                            disabledContainerColor = Color(0xFF333333)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.testTag("import_selected_photos_button")
                    ) {
                        Text(
                            text = if (selectedUris.isNotEmpty()) "Import(${selectedUris.size})" else "Import",
                            color = if (selectedUris.isNotEmpty()) Color.White else Color(0xFF888888),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // =========================================================================
            // PHOTO GRID CONTENT
            // =========================================================================
            if (isLoadingGallery) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(color = Color(0xFF00C853))
                        Text("Scanning device pictures...", color = Color(0xFFB0B0B0), fontSize = 13.sp)
                    }
                }
            } else if (galleryPhotos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF242424)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.PhotoLibrary,
                                contentDescription = null,
                                tint = Color(0xFF00C853),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Text(
                            "No Gallery Photos Found",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Tap below to browse and select photos or documents using the system gallery picker:",
                            color = Color(0xFF888888),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { systemPickerLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.FolderOpen, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Browse Photos", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(galleryPhotos, key = { it.toString() }) { photoUri ->
                        val selectedIndex = selectedUris.indexOf(photoUri)
                        val isSelected = selectedIndex != -1
                        val orderBadge = if (isSelected) selectedIndex + 1 else -1

                        GalleryThumbnailItem(
                            uri = photoUri,
                            orderIndex = orderBadge,
                            onClick = {
                                if (isSelected) {
                                    selectedUris.remove(photoUri)
                                } else {
                                    selectedUris.add(photoUri)
                                }
                            }
                        )
                    }
                }
            }

            // =========================================================================
            // BOTTOM CONTROL BAR (Enhance ▲ | [✓] Auto Crop)
            // =========================================================================
            Surface(
                color = Color(0xFF1E1E1E),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left: Enhance Selector Dropdown
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showFilterMenu = true }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color(0xFF00C853),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Enhance (${selectedFilter.displayName})",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                Icons.Default.ArrowDropUp,
                                contentDescription = null,
                                tint = Color(0xFF888888),
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showFilterMenu,
                            onDismissRequest = { showFilterMenu = false }
                        ) {
                            listOf(
                                ScanFilter.ENHANCE,
                                ScanFilter.MAGIC_PRO,
                                ScanFilter.BW,
                                ScanFilter.GRAYSCALE,
                                ScanFilter.LIGHTEN,
                                ScanFilter.ORIGINAL
                            ).forEach { filter ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            if (selectedFilter == filter) {
                                                Icon(
                                                    Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = Color(0xFF00C853),
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            } else {
                                                Spacer(modifier = Modifier.size(16.dp))
                                            }
                                            Column {
                                                Text(filter.displayName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                                Text(filter.description, fontSize = 10.sp, color = Color.Gray, maxLines = 1)
                                            }
                                        }
                                    },
                                    onClick = {
                                        selectedFilter = filter
                                        showFilterMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Right: [✓] Auto Crop Checkbox
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { isAutoCropEnabled = !isAutoCropEnabled }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Checkbox(
                            checked = isAutoCropEnabled,
                            onCheckedChange = { isAutoCropEnabled = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF00C853),
                                uncheckedColor = Color(0xFF888888),
                                checkmarkColor = Color.White
                            )
                        )
                        Text(
                            text = "Auto Crop",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // =========================================================================
        // PROGRESS DIALOG (Importing & Auto Cropping in Background)
        // =========================================================================
        if (isProcessing) {
            Dialog(
                onDismissRequest = {},
                properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF222222)),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00C853).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.CropFree,
                                contentDescription = null,
                                tint = Color(0xFF00C853),
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Text(
                            text = if (isAutoCropEnabled) "Auto-Cropping Pages" else "Importing Pages",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )

                        Text(
                            text = processingStepTitle,
                            color = Color(0xFFB0B0B0),
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )

                        LinearProgressIndicator(
                            progress = { if (processingTotal > 0) processingProgress.toFloat() / processingTotal.toFloat() else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = Color(0xFF00C853),
                            trackColor = Color(0xFF444444)
                        )

                        Text(
                            text = "$processingProgress of $processingTotal completed",
                            color = Color(0xFF888888),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * High-performance grid thumbnail item with numbered selection badge.
 */
@Composable
private fun GalleryThumbnailItem(
    uri: Uri,
    orderIndex: Int,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val bitmapState = produceState<Bitmap?>(initialValue = null, key1 = uri) {
        value = withContext(Dispatchers.IO) {
            loadThumbnailFromUri(context, uri, 300)
        }
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(Color(0xFF1E1E1E))
            .clickable { onClick() }
    ) {
        bitmapState.value?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                color = Color.White.copy(alpha = 0.3f),
                strokeWidth = 2.dp
            )
        }

        // Selection Checkbox / Badge on Top-Left
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(6.dp)
        ) {
            if (orderIndex > 0) {
                // Selected: Green badge with order number (1, 2, 3...)
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF00C853)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$orderIndex",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp
                    )
                }
            } else {
                // Unselected: White outline square
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Black.copy(alpha = 0.35f))
                        .border(1.5.dp, Color.White.copy(alpha = 0.85f), RoundedCornerShape(5.dp))
                )
            }
        }
    }
}
