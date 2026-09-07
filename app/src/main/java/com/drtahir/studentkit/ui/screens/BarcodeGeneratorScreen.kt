package com.drtahir.studentkit.ui.screens

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcodeGeneratorScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var inputText by remember { mutableStateOf("") }
    var generatedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isGenerating by remember { mutableStateOf(false) }

    val barcodeFormats = listOf(
        "CODE_128" to BarcodeFormat.CODE_128,
        "CODE_39" to BarcodeFormat.CODE_39,
        "UPC_A" to BarcodeFormat.UPC_A,
        "EAN_13" to BarcodeFormat.EAN_13,
        "EAN_8" to BarcodeFormat.EAN_8,
        "ITF" to BarcodeFormat.ITF,
        "PDF_417" to BarcodeFormat.PDF_417,
        "CODABAR" to BarcodeFormat.CODABAR
    )
    
    var selectedFormatIndex by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    fun generateBarcode() {
        if (inputText.isBlank()) {
            Toast.makeText(context, "Please enter some text", Toast.LENGTH_SHORT).show()
            return
        }
        
        isGenerating = true
        coroutineScope.launch {
            val formatPair = barcodeFormats[selectedFormatIndex]
            val format = formatPair.second
            
            // Basic validation for numbers-only formats
            val isNumberOnlyFormat = format == BarcodeFormat.UPC_A || 
                                     format == BarcodeFormat.EAN_13 || 
                                     format == BarcodeFormat.EAN_8 || 
                                     format == BarcodeFormat.ITF
                                     
            if (isNumberOnlyFormat && !inputText.all { it.isDigit() }) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "${formatPair.first} requires numeric input only", Toast.LENGTH_SHORT).show()
                    isGenerating = false
                }
                return@launch
            }
            
            // specific length requirements
            if (format == BarcodeFormat.UPC_A && inputText.length != 11 && inputText.length != 12) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "UPC-A requires 11 or 12 digits", Toast.LENGTH_SHORT).show()
                    isGenerating = false
                }
                return@launch
            }
            if (format == BarcodeFormat.EAN_13 && inputText.length != 12 && inputText.length != 13) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "EAN-13 requires 12 or 13 digits", Toast.LENGTH_SHORT).show()
                    isGenerating = false
                }
                return@launch
            }
            if (format == BarcodeFormat.EAN_8 && inputText.length != 7 && inputText.length != 8) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "EAN-8 requires 7 or 8 digits", Toast.LENGTH_SHORT).show()
                    isGenerating = false
                }
                return@launch
            }

            val bmp = generateBarcodeBitmap(inputText, format, 800, 400)
            withContext(Dispatchers.Main) {
                generatedBitmap = bmp
                isGenerating = false
                if (bmp == null) {
                    Toast.makeText(context, "Failed to generate barcode. Check input format.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Barcode Data", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = barcodeFormats[selectedFormatIndex].first,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Barcode Format") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        barcodeFormats.forEachIndexed { index, pair ->
                            DropdownMenuItem(
                                text = { Text(pair.first) },
                                onClick = {
                                    selectedFormatIndex = index
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                
                val isNumberOnly = barcodeFormats[selectedFormatIndex].second in listOf(
                    BarcodeFormat.UPC_A, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.ITF
                )

                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Enter text or numbers") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = if (isNumberOnly) KeyboardType.Number else KeyboardType.Text)
                )
                
                val hintText = when (barcodeFormats[selectedFormatIndex].second) {
                    BarcodeFormat.UPC_A -> "11 or 12 digits"
                    BarcodeFormat.EAN_13 -> "12 or 13 digits"
                    BarcodeFormat.EAN_8 -> "7 or 8 digits"
                    BarcodeFormat.ITF -> "Even number of digits"
                    BarcodeFormat.CODE_39 -> "Uppercase letters, numbers, and space - . * \$ / + %"
                    else -> ""
                }
                if (hintText.isNotEmpty()) {
                    Text(hintText, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                
                Button(
                    onClick = { generateBarcode() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isGenerating
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("Generate Barcode")
                    }
                }
            }
        }

        if (generatedBitmap != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        bitmap = generatedBitmap!!.asImageBitmap(),
                        contentDescription = "Generated Barcode",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    
                    Text(inputText, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp, letterSpacing = 2.sp)

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val saved = saveBarcodeToGallery(context, generatedBitmap!!, barcodeFormats[selectedFormatIndex].first)
                                if (saved) {
                                    Toast.makeText(context, "Barcode saved to gallery!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Failed to save barcode.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save to Gallery")
                    }
                }
            }
        }
    }
}

suspend fun generateBarcodeBitmap(content: String, format: BarcodeFormat, width: Int, height: Int): Bitmap? = withContext(Dispatchers.IO) {
    try {
        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix: BitMatrix = multiFormatWriter.encode(content, format, width, height)
        val w = bitMatrix.width
        val h = bitMatrix.height
        val pixels = IntArray(w * h)
        
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE
            }
        }
        
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h)
        bitmap
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

suspend fun saveBarcodeToGallery(context: Context, bitmap: Bitmap, formatName: String): Boolean = withContext(Dispatchers.IO) {
    try {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "Barcode_${formatName}_${System.currentTimeMillis()}.png")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/StudentKit")
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            resolver.openOutputStream(uri)?.use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            }
            true
        } else {
            false
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
