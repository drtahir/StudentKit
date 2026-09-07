package com.drtahir.studentkit.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import com.drtahir.studentkit.data.*
import com.drtahir.studentkit.viewmodel.StudentKitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Enterprise OmniPOS Suite & Fullscreen Invoice / Print Preview Studio
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceGeneratorScreen(viewModel: StudentKitViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val allProducts by viewModel.allPosProducts.collectAsState(initial = emptyList())
    val allClients by viewModel.allPosClients.collectAsState(initial = emptyList())
    val allOrders by viewModel.allPosOrders.collectAsState(initial = emptyList())

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "🛒 POS Terminal",
        "📄 Invoices Ledger",
        "📦 Inventory",
        "📊 Financial Reports",
        "👥 Staff & Shifts",
        "⚙️ Settings & Branding"
    )

    // Active Cart State for Terminal
    val activeCart = remember { mutableStateMapOf<String, Int>() } // productId -> quantity
    var selectedClientId by remember { mutableStateOf<String?>(null) }
    var terminalDiscountPercent by remember { mutableDoubleStateOf(0.0) }
    var terminalTaxPercent by remember { mutableDoubleStateOf(0.0) }
    var terminalPaymentMethod by remember { mutableStateOf("Cash") }
    var terminalDocType by remember { mutableStateOf("Invoice") }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    // Fullscreen Invoice Preview Modal State
    var activePreviewOrder by remember { mutableStateOf<PosOrder?>(null) }
    var activePreviewItems by remember { mutableStateOf<List<PosOrderItem>>(emptyList()) }
    var activePreviewClient by remember { mutableStateOf<PosClient?>(null) }
    var showFullscreenInvoiceModal by remember { mutableStateOf(false) }

    // New Product Dialog
    var showAddProductDialog by remember { mutableStateOf(false) }

    // Custom Invoice Builder Dialog
    var showCustomInvoiceDialog by remember { mutableStateOf(false) }

    // Seed default sample inventory if empty on first launch
    LaunchedEffect(allProducts.isEmpty()) {
        if (allProducts.isEmpty()) {
            val sampleItems = listOf(
                PosProduct("prod_01", "Panadol Extra 500mg (Box of 20)", "Pharmacy", 180.0, 150, "Box"),
                PosProduct("prod_02", "Surgical Face Mask (50 pcs)", "Medical", 350.0, 80, "Box"),
                PosProduct("prod_03", "Digital BP Monitor", "Equipment", 4200.0, 25, "Unit"),
                PosProduct("prod_04", "Hand Sanitizer 500ml 75% Alcohol", "Hygiene", 290.0, 95, "Bottle"),
                PosProduct("prod_05", "Multivitamin & Zinc Syrup 120ml", "Pharmacy", 240.0, 110, "Bottle"),
                PosProduct("prod_06", "A4 Printing Paper Ream (500 Sheets)", "Stationery", 1250.0, 40, "Ream"),
                PosProduct("prod_07", "Stainless Steel Stethoscope", "Equipment", 2800.0, 15, "Unit"),
                PosProduct("prod_08", "Infrared Non-Contact Thermometer", "Equipment", 1950.0, 30, "Unit")
            )
            sampleItems.forEach { viewModel.insertPosProduct(it) }

            if (allClients.isEmpty()) {
                val sampleClients = listOf(
                    PosClient("client_01", "Dr. Tahir Medical Complex", "0300-1234567", "info@tahirclinic.pk", "Buner, KP, Pakistan", "Customer"),
                    PosClient("client_02", "Al-Khidmat Pharmacy & Diagnostic", "0345-9876543", "alkhidmat@buner.org", "Main Bazar, Daggar, Buner", "Customer"),
                    PosClient("client_03", "Khyber Surgical Supplies Ltd", "091-5840001", "orders@khybersurgical.com", "Industrial Estate, Hayatabad, Peshawar", "Supplier")
                )
                sampleClients.forEach { viewModel.insertPosClient(it) }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Module Top Header Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 4.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.PointOfSale, null, tint = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                "OmniPOS Enterprise Suite",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "Tax Invoices • ESC/POS Receipts • Shift Audit",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Top quick actions
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(
                            onClick = { showAddProductDialog = true },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.AddBox, contentDescription = "Add Product", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(
                            onClick = { showCustomInvoiceDialog = true },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.PostAdd, contentDescription = "Custom Invoice", tint = Color(0xFF00897B))
                        }
                        IconButton(
                            onClick = { selectedTab = 5 },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings & Logo Studio", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Module Navigation Scrollable Tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    edgePadding = 12.dp,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            }
                        )
                    }
                }
            }
        }

        // TAB CONTENTS
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                0 -> {
                    // POS TERMINAL & BILLING
                    PosTerminalSection(
                        allProducts = allProducts,
                        allClients = allClients,
                        activeCart = activeCart,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        selectedCategory = selectedCategory,
                        onSelectCategory = { selectedCategory = it },
                        selectedClientId = selectedClientId,
                        onSelectClient = { selectedClientId = it },
                        discountPercent = terminalDiscountPercent,
                        onDiscountChange = { terminalDiscountPercent = it },
                        taxPercent = terminalTaxPercent,
                        onTaxChange = { terminalTaxPercent = it },
                        paymentMethod = terminalPaymentMethod,
                        onPaymentMethodChange = { terminalPaymentMethod = it },
                        documentType = terminalDocType,
                        onDocumentTypeChange = { terminalDocType = it },
                        onAddToCart = { prodId ->
                            activeCart[prodId] = (activeCart[prodId] ?: 0) + 1
                        },
                        onRemoveFromCart = { prodId ->
                            val current = activeCart[prodId] ?: 0
                            if (current <= 1) activeCart.remove(prodId) else activeCart[prodId] = current - 1
                        },
                        onClearCart = { activeCart.clear() },
                        onCheckout = {
                            if (activeCart.isEmpty()) {
                                Toast.makeText(context, "Cart is empty. Add products to bill.", Toast.LENGTH_SHORT).show()
                                return@PosTerminalSection
                            }

                            val orderId = "INV-${System.currentTimeMillis() % 1000000}"
                            val dateStr = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())

                            val itemsList = activeCart.mapNotNull { (prodId, qty) ->
                                val prod = allProducts.firstOrNull { it.id == prodId }
                                prod?.let {
                                    PosOrderItem(
                                        id = UUID.randomUUID().toString(),
                                        orderId = orderId,
                                        productId = it.id,
                                        name = it.name,
                                        quantity = qty,
                                        price = it.price
                                    )
                                }
                            }

                            val subtotal = itemsList.sumOf { it.price * it.quantity }
                            val discountAmt = subtotal * (terminalDiscountPercent / 100.0)
                            val taxAmt = (subtotal - discountAmt) * (terminalTaxPercent / 100.0)
                            val grandTotal = (subtotal - discountAmt) + taxAmt

                            val newOrder = PosOrder(
                                id = orderId,
                                date = dateStr,
                                clientId = selectedClientId,
                                subtotal = subtotal,
                                tax = taxAmt,
                                discount = discountAmt,
                                total = grandTotal,
                                documentType = terminalDocType
                            )

                            // Save to Room DB
                            viewModel.insertPosOrder(newOrder)
                            itemsList.forEach { viewModel.insertPosOrderItem(it) }

                            // Open Fullscreen Invoice Preview Immediately
                            activePreviewOrder = newOrder
                            activePreviewItems = itemsList
                            activePreviewClient = allClients.firstOrNull { it.id == selectedClientId }
                            showFullscreenInvoiceModal = true

                            // Clear cart
                            activeCart.clear()
                            Toast.makeText(context, "Order $orderId Created Successfully!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                1 -> {
                    // INVOICES & RECEIPTS LEDGER
                    PosInvoicesLedgerSection(
                        allOrders = allOrders,
                        allClients = allClients,
                        viewModel = viewModel,
                        onOpenFullscreenInvoice = { order, items, client ->
                            activePreviewOrder = order
                            activePreviewItems = items
                            activePreviewClient = client
                            showFullscreenInvoiceModal = true
                        },
                        onCreateCustomInvoice = { showCustomInvoiceDialog = true }
                    )
                }

                2 -> {
                    // PRODUCTS & INVENTORY
                    PosInventorySection(
                        allProducts = allProducts,
                        onAddProduct = { showAddProductDialog = true },
                        onDeleteProduct = { viewModel.deletePosProductById(it) }
                    )
                }

                3 -> {
                    // EMBEDDED 1-CLICK FINANCIAL REPORTS
                    OmniPosFinancialReportsScreen(viewModel = viewModel)
                }

                4 -> {
                    // EMBEDDED STAFF & SHIFT MANAGEMENT
                    OmniPosEmployeesTab(viewModel = viewModel)
                }

                5 -> {
                    // ENTERPRISE SUITE SETTINGS & OFFICIAL LOGO STUDIO
                    PosEnterpriseSettingsSection(context = context)
                }
            }
        }
    }

    // FULLSCREEN INVOICE PREVIEW & PRINT STUDIO MODAL (User's #1 Request)
    if (showFullscreenInvoiceModal && activePreviewOrder != null) {
        val order = activePreviewOrder!!
        FullscreenInvoicePreviewModal(
            context = context,
            order = order,
            items = activePreviewItems,
            client = activePreviewClient,
            onDismiss = { showFullscreenInvoiceModal = false }
        )
    }

    // Add Product Modal
    if (showAddProductDialog) {
        AddPosProductDialog(
            onDismiss = { showAddProductDialog = false },
            onConfirm = { product ->
                viewModel.insertPosProduct(product)
                showAddProductDialog = false
                Toast.makeText(context, "Product added to catalog", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Custom Invoice Builder Dialog
    if (showCustomInvoiceDialog) {
        CustomInvoiceBuilderDialog(
            allClients = allClients,
            onDismiss = { showCustomInvoiceDialog = false },
            onCreateOrder = { newOrder, newItems ->
                viewModel.insertPosOrder(newOrder)
                newItems.forEach { viewModel.insertPosOrderItem(it) }
                showCustomInvoiceDialog = false
                activePreviewOrder = newOrder
                activePreviewItems = newItems
                activePreviewClient = allClients.firstOrNull { it.id == newOrder.clientId }
                showFullscreenInvoiceModal = true
                Toast.makeText(context, "Custom Invoice Generated!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

// -------------------------------------------------------------
// FULLSCREEN INVOICE & PRINT PREVIEW MODAL (FIT TO SCREEN)
// -------------------------------------------------------------
@Composable
fun FullscreenInvoicePreviewModal(
    context: Context,
    order: PosOrder,
    items: List<PosOrderItem>,
    client: PosClient?,
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedFormatMode by remember { mutableStateOf("A4 Standard") } // "A4 Standard", "80mm Thermal", "58mm Slip"
    var zoomFactor by remember { mutableFloatStateOf(10f) }
    var isDarkPaper by remember { mutableStateOf(false) }

    // Business profile & Logo preferences
    val profile = remember { getSavedBusinessProfile(context) }
    var logoBitmap by remember { mutableStateOf(BluetoothThermalPrinterHelper.getSavedLogoBitmap(context)) }

    val businessName = profile.businessName
    val businessAddress = "${profile.address}, ${profile.cityCountry}"
    val businessPhone = profile.phone
    val businessNtn = profile.ntnNumber

    val a4Text = remember(order, items, client, selectedFormatMode, profile) {
        BluetoothThermalPrinterHelper.buildA4InvoiceText(
            businessName = businessName,
            tagline = profile.tagline.ifBlank { "Healthcare & Enterprise Solutions" },
            address = businessAddress,
            phone = businessPhone,
            orderId = order.id,
            dateStr = order.date,
            clientName = client?.name ?: "Walking Customer / Cash",
            items = items,
            subtotal = order.subtotal,
            discount = order.discount,
            tax = order.tax,
            total = order.total,
            paymentMethod = "Cash / Terminal",
            footerNote = "Official computerized sales invoice. Computer generated."
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = if (isDarkPaper) Color(0xFF0F172A) else Color(0xFFE2E8F0)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top App Bar with Controls
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 6.dp
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            ) {
                                IconButton(onClick = onDismiss) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Close Preview")
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "Invoice ${order.id}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                order.documentType,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        "Rs ${String.format("%.2f", order.total)} • ${order.date}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            // Zoom & View Mode Controls
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                IconButton(
                                    onClick = { zoomFactor = (zoomFactor - 1f).coerceAtLeast(6.5f) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.ZoomOut, contentDescription = "Zoom Out", modifier = Modifier.size(18.dp))
                                }
                                Text("${zoomFactor.toInt()}pt", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                IconButton(
                                    onClick = { zoomFactor = (zoomFactor + 1f).coerceAtMost(18f) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.ZoomIn, contentDescription = "Zoom In", modifier = Modifier.size(18.dp))
                                }
                                IconButton(
                                    onClick = { isDarkPaper = !isDarkPaper },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        if (isDarkPaper) Icons.Default.LightMode else Icons.Default.DarkMode,
                                        contentDescription = "Toggle Paper Tone",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        // Format Preset Chips
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("A4 Standard", "80mm Thermal", "58mm Slip").forEach { fmt ->
                                val isSelected = selectedFormatMode == fmt
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedFormatMode = fmt },
                                    label = { Text(fmt, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                // SCROLLABLE FULLSCREEN INVOICE DOCUMENT VIEWPORT (Fit to Screen Guaranteed)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(6.dp, RoundedCornerShape(10.dp)),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDarkPaper) Color(0xFF1E293B) else Color.White
                        ),
                        border = BorderStroke(1.dp, if (isDarkPaper) Color(0xFF334155) else Color(0xFFCBD5E1))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .horizontalScroll(rememberScrollState())
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // High Quality Logo Preview on top of invoice paper
                            if (profile.showLogoOnInvoice && logoBitmap != null) {
                                Image(
                                    bitmap = logoBitmap!!.asImageBitmap(),
                                    contentDescription = "Enterprise Logo",
                                    modifier = Modifier
                                        .height(55.dp)
                                        .padding(bottom = 12.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }

                            Text(
                                text = a4Text,
                                fontFamily = FontFamily.Monospace,
                                fontSize = zoomFactor.sp,
                                color = if (isDarkPaper) Color(0xFFF1F5F9) else Color(0xFF0F172A),
                                lineHeight = (zoomFactor * 1.35f).sp
                            )
                        }
                    }
                }

                // Bottom Print & Spool Actions
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    BluetoothThermalPrinterHelper.printA4ViaSystem(
                                        context = context,
                                        jobName = "OmniPOS_Invoice_${order.id}",
                                        documentTitle = "Invoice_${order.id}",
                                        contentText = a4Text,
                                        logoBitmap = logoBitmap,
                                        showLogo = profile.showLogoOnInvoice
                                    )
                                },
                                modifier = Modifier.weight(1.2f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Print, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Print A4 PDF", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }

                            FilledTonalButton(
                                onClick = {
                                    val printerAddress = BluetoothThermalPrinterHelper.getSavedPrinterAddress(context)
                                    if (printerAddress.isBlank()) {
                                        Toast.makeText(context, "No Bluetooth printer paired in settings. Opening system spooler...", Toast.LENGTH_SHORT).show()
                                        BluetoothThermalPrinterHelper.printA4ViaSystem(
                                            context = context,
                                            jobName = "OmniPOS_Thermal_${order.id}",
                                            documentTitle = "ThermalReceipt_${order.id}",
                                            contentText = a4Text,
                                            logoBitmap = logoBitmap,
                                            showLogo = profile.showLogoOnInvoice
                                        )
                                    } else {
                                        coroutineScope.launch(Dispatchers.IO) {
                                            val payload = BluetoothThermalPrinterHelper.buildPosReceiptPayload(
                                                businessName = businessName,
                                                tagline = profile.tagline.ifBlank { "Healthcare & Enterprise Solutions" },
                                                address = businessAddress,
                                                phone = businessPhone,
                                                orderId = order.id,
                                                dateStr = order.date,
                                                items = items,
                                                subtotal = order.subtotal,
                                                discount = order.discount,
                                                tax = order.tax,
                                                total = order.total,
                                                paymentMethod = "Cash / Terminal",
                                                footerNote = "Thank you for your business!",
                                                logoBitmap = logoBitmap,
                                                showLogo = profile.showLogoOnThermal,
                                                threshold = profile.thermalDitherThreshold
                                            )
                                            val (success, message) = BluetoothThermalPrinterHelper.printPayload(context, printerAddress, payload)
                                            withContext(Dispatchers.Main) {
                                                if (success) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                                    BluetoothThermalPrinterHelper.printA4ViaSystem(
                                                        context = context,
                                                        jobName = "OmniPOS_Thermal_${order.id}",
                                                        documentTitle = "ThermalReceipt_${order.id}",
                                                        contentText = a4Text,
                                                        logoBitmap = logoBitmap,
                                                        showLogo = profile.showLogoOnInvoice
                                                    )
                                                }
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1.2f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Receipt, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Thermal Print", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }

                            OutlinedButton(
                                onClick = {
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, a4Text)
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, "Share Invoice ${order.id}"))
                                },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Share, null, modifier = Modifier.size(18.dp))
                            }

                            OutlinedButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("Invoice Text", a4Text))
                                    Toast.makeText(context, "Invoice copied to clipboard!", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// POS TERMINAL TAB
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PosTerminalSection(
    allProducts: List<PosProduct>,
    allClients: List<PosClient>,
    activeCart: Map<String, Int>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    selectedClientId: String?,
    onSelectClient: (String?) -> Unit,
    discountPercent: Double,
    onDiscountChange: (Double) -> Unit,
    taxPercent: Double,
    onTaxChange: (Double) -> Unit,
    paymentMethod: String,
    onPaymentMethodChange: (String) -> Unit,
    documentType: String,
    onDocumentTypeChange: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onRemoveFromCart: (String) -> Unit,
    onClearCart: () -> Unit,
    onCheckout: () -> Unit
) {
    val categories = remember(allProducts) {
        listOf("All") + allProducts.map { it.category }.distinct()
    }

    val filteredProducts = remember(allProducts, searchQuery, selectedCategory) {
        allProducts.filter { prod ->
            val matchCat = selectedCategory == "All" || prod.category.equals(selectedCategory, ignoreCase = true)
            val matchSearch = searchQuery.isBlank() || prod.name.contains(searchQuery, ignoreCase = true) || prod.id.contains(searchQuery, ignoreCase = true)
            matchCat && matchSearch
        }
    }

    val cartItemsList = remember(activeCart, allProducts) {
        activeCart.mapNotNull { (prodId, qty) ->
            val prod = allProducts.firstOrNull { it.id == prodId }
            prod?.let { it to qty }
        }
    }

    val subtotal = cartItemsList.sumOf { (prod, qty) -> prod.price * qty }
    val discountAmt = subtotal * (discountPercent / 100.0)
    val taxAmt = (subtotal - discountAmt) * (taxPercent / 100.0)
    val grandTotal = (subtotal - discountAmt) + taxAmt

    Row(modifier = Modifier.fillMaxSize()) {
        // Left Column: Catalog & Items Grid
        Column(
            modifier = Modifier
                .weight(1.1f)
                .fillMaxHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Search & Category Row
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Search items or scan barcode...", fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(Icons.Default.Clear, null)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Category Chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectCategory(cat) },
                        label = { Text(cat, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            // Products Grid
            if (filteredProducts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Inventory2, null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No products found in category", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 140.dp),
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredProducts) { prod ->
                        val inCartCount = activeCart[prod.id] ?: 0
                        Card(
                            onClick = { onAddToCart(prod.id) },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (inCartCount > 0) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(
                                width = if (inCartCount > 0) 1.5.dp else 1.dp,
                                color = if (inCartCount > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            prod.category,
                                            fontSize = 9.sp,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    if (inCartCount > 0) {
                                        Surface(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        ) {
                                            Text(
                                                "$inCartCount",
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    prod.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Rs ${prod.price.toInt()}",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 13.sp
                                    )
                                    Text("Stock: ${prod.stock}", fontSize = 10.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Right Column: Real-time Live Cart & Billing Summary
        Surface(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxHeight(),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Cart Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Billing Cart (${activeCart.values.sum()})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    if (activeCart.isNotEmpty()) {
                        TextButton(onClick = onClearCart) {
                            Text("Clear", color = Color.Red, fontSize = 11.sp)
                        }
                    }
                }

                // Customer Selection
                var clientDropdownExpanded by remember { mutableStateOf(false) }
                val currentClient = allClients.firstOrNull { it.id == selectedClientId }

                OutlinedCard(
                    onClick = { clientDropdownExpanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Bill To Customer:", fontSize = 10.sp, color = Color.Gray)
                            Text(
                                currentClient?.name ?: "Walking Customer / Cash",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Icon(Icons.Default.ArrowDropDown, null)
                    }
                    DropdownMenu(
                        expanded = clientDropdownExpanded,
                        onDismissRequest = { clientDropdownExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Walking Customer / Cash") },
                            onClick = {
                                onSelectClient(null)
                                clientDropdownExpanded = false
                            }
                        )
                        allClients.forEach { client ->
                            DropdownMenuItem(
                                text = { Text("${client.name} (${client.phone})") },
                                onClick = {
                                    onSelectClient(client.id)
                                    clientDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Cart Items List
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    if (cartItemsList.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Cart is empty\nTap items to add", textAlign = TextAlign.Center, color = Color.Gray, fontSize = 12.sp)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(cartItemsList) { (prod, qty) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                                        .padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f).padding(end = 4.dp)) {
                                        Text(prod.name, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text("Rs ${prod.price.toInt()} × $qty = Rs ${(prod.price * qty).toInt()}", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(onClick = { onRemoveFromCart(prod.id) }, modifier = Modifier.size(24.dp)) {
                                            Icon(Icons.Default.Remove, null, modifier = Modifier.size(14.dp))
                                        }
                                        Text("$qty", fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 4.dp))
                                        IconButton(onClick = { onAddToCart(prod.id) }, modifier = Modifier.size(24.dp)) {
                                            Icon(Icons.Default.Add, null, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Discount & Tax quick toggles
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = if (discountPercent == 0.0) "" else "${discountPercent.toInt()}",
                        onValueChange = { onDiscountChange(it.toDoubleOrNull() ?: 0.0) },
                        label = { Text("Disc %", fontSize = 10.sp) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = if (taxPercent == 0.0) "" else "${taxPercent.toInt()}",
                        onValueChange = { onTaxChange(it.toDoubleOrNull() ?: 0.0) },
                        label = { Text("Tax %", fontSize = 10.sp) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                // Totals Breakdown
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal", fontSize = 11.sp)
                            Text("Rs ${String.format("%.2f", subtotal)}", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                        if (discountAmt > 0) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Discount", fontSize = 11.sp, color = Color(0xFF2E7D32))
                                Text("-Rs ${String.format("%.2f", discountAmt)}", fontSize = 11.sp, color = Color(0xFF2E7D32))
                            }
                        }
                        if (taxAmt > 0) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Tax / GST", fontSize = 11.sp)
                                Text("+Rs ${String.format("%.2f", taxAmt)}", fontSize = 11.sp)
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("GRAND TOTAL", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Rs ${String.format("%.2f", grandTotal)}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                // Instant Checkout Button -> Launches Fullscreen Invoice & Print Preview
                Button(
                    onClick = onCheckout,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(10.dp),
                    enabled = cartItemsList.isNotEmpty()
                ) {
                    Icon(Icons.Default.ReceiptLong, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Checkout & Print Preview", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// INVOICES & RECEIPTS LEDGER TAB
// -------------------------------------------------------------
@Composable
fun PosInvoicesLedgerSection(
    allOrders: List<PosOrder>,
    allClients: List<PosClient>,
    viewModel: StudentKitViewModel,
    onOpenFullscreenInvoice: (PosOrder, List<PosOrderItem>, PosClient?) -> Unit,
    onCreateCustomInvoice: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterType by remember { mutableStateOf("All") }

    val filteredOrders = remember(allOrders, searchQuery, selectedFilterType) {
        allOrders.filter { order ->
            val matchType = selectedFilterType == "All" || order.documentType.equals(selectedFilterType, ignoreCase = true)
            val matchSearch = searchQuery.isBlank() || order.id.contains(searchQuery, ignoreCase = true) || order.date.contains(searchQuery, ignoreCase = true)
            matchType && matchSearch
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Top Filter & Search Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search invoice #, date...", fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onCreateCustomInvoice,
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("New Bill", fontSize = 12.sp)
            }
        }

        // Filter Chips
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("All", "Invoice", "Receipt", "Estimate").forEach { type ->
                val isSelected = selectedFilterType == type
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilterType = type },
                    label = { Text(type, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) }
                )
            }
        }

        // Invoices List
        if (filteredOrders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ReceiptLong, null, modifier = Modifier.size(54.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("No billing records found", color = Color.Gray, fontSize = 14.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredOrders) { order ->
                    val matchedClient = allClients.firstOrNull { it.id == order.clientId }

                    Card(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.getPosOrderItems(order.id).collect { items ->
                                    onOpenFullscreenInvoice(order, items, matchedClient)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(order.id, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Surface(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            order.documentType,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    matchedClient?.name ?: "Walking Customer / Cash",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(order.date, fontSize = 10.sp, color = Color.Gray)
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    "Rs ${String.format("%.2f", order.total)}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    FilledTonalIconButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                viewModel.getPosOrderItems(order.id).collect { items ->
                                                    onOpenFullscreenInvoice(order, items, matchedClient)
                                                }
                                            }
                                        },
                                        modifier = Modifier.size(30.dp)
                                    ) {
                                        Icon(Icons.Default.Fullscreen, null, modifier = Modifier.size(16.dp))
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.deletePosOrderById(order.id)
                                            Toast.makeText(context, "Order ${order.id} deleted", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(30.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, null, tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(16.dp))
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

// -------------------------------------------------------------
// INVENTORY & STOCK MANAGEMENT TAB
// -------------------------------------------------------------
@Composable
fun PosInventorySection(
    allProducts: List<PosProduct>,
    onAddProduct: () -> Unit,
    onDeleteProduct: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredProducts = remember(allProducts, searchQuery) {
        allProducts.filter {
            searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true) || it.category.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search catalog...", fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
            )

            Button(onClick = onAddProduct, shape = RoundedCornerShape(10.dp)) {
                Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Item", fontSize = 12.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredProducts) { prod ->
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                            Text(prod.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Category: ${prod.category} • Unit: ${prod.unit}", fontSize = 11.sp, color = Color.Gray)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Rs ${prod.price.toInt()}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
                            Text("Stock: ${prod.stock}", fontSize = 11.sp, color = if (prod.stock < 10) Color.Red else Color.DarkGray)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { onDeleteProduct(prod.id) }, modifier = Modifier.size(30.dp)) {
                            Icon(Icons.Default.Delete, null, tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// ADD PRODUCT DIALOG
// -------------------------------------------------------------
@Composable
fun AddPosProductDialog(
    onDismiss: () -> Unit,
    onConfirm: (PosProduct) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Pharmacy") }
    var priceStr by remember { mutableStateOf("") }
    var stockStr by remember { mutableStateOf("50") }
    var unit by remember { mutableStateOf("Piece") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Catalog Item", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Item Name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(value = priceStr, onValueChange = { priceStr = it }, label = { Text("Price (Rs)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = stockStr, onValueChange = { stockStr = it }, label = { Text("Initial Stock") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true, modifier = Modifier.weight(1f))
                }
                OutlinedTextField(value = unit, onValueChange = { unit = it }, label = { Text("Unit (e.g. Box, Piece, Kg)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val product = PosProduct(
                            id = "prod_${System.currentTimeMillis() % 100000}",
                            name = name.trim(),
                            category = category.trim(),
                            price = priceStr.toDoubleOrNull() ?: 100.0,
                            stock = stockStr.toIntOrNull() ?: 10,
                            unit = unit.trim()
                        )
                        onConfirm(product)
                    }
                }
            ) {
                Text("Add Product")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

// -------------------------------------------------------------
// CUSTOM INVOICE BUILDER DIALOG
// -------------------------------------------------------------
@Composable
fun CustomInvoiceBuilderDialog(
    allClients: List<PosClient>,
    onDismiss: () -> Unit,
    onCreateOrder: (PosOrder, List<PosOrderItem>) -> Unit
) {
    var docType by remember { mutableStateOf("Invoice") }
    var selectedClientId by remember { mutableStateOf<String?>(null) }
    var customItemName by remember { mutableStateOf("") }
    var customItemRate by remember { mutableStateOf("") }
    var customItemQty by remember { mutableStateOf("1") }
    val customItems = remember { mutableStateListOf<PosOrderItem>() }
    var discountStr by remember { mutableStateOf("0") }
    var taxStr by remember { mutableStateOf("0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Custom Invoice / Quotation", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 450.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Doc Type Switcher
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Invoice", "Receipt", "Estimate").forEach { type ->
                        FilterChip(
                            selected = docType == type,
                            onClick = { docType = type },
                            label = { Text(type, fontSize = 11.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Add Custom Line Item Form
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Add Custom Line Item:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        OutlinedTextField(
                            value = customItemName,
                            onValueChange = { customItemName = it },
                            label = { Text("Item Description") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            OutlinedTextField(
                                value = customItemRate,
                                onValueChange = { customItemRate = it },
                                label = { Text("Rate (Rs)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = customItemQty,
                                onValueChange = { customItemQty = it },
                                label = { Text("Qty") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Button(
                            onClick = {
                                if (customItemName.isNotBlank()) {
                                    customItems.add(
                                        PosOrderItem(
                                            id = UUID.randomUUID().toString(),
                                            orderId = "",
                                            productId = "custom_${System.currentTimeMillis() % 10000}",
                                            name = customItemName.trim(),
                                            quantity = customItemQty.toIntOrNull() ?: 1,
                                            price = customItemRate.toDoubleOrNull() ?: 0.0
                                        )
                                    )
                                    customItemName = ""
                                    customItemRate = ""
                                    customItemQty = "1"
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Add Line Item", fontSize = 12.sp)
                        }
                    }
                }

                // Added Items
                if (customItems.isNotEmpty()) {
                    Text("Included Items (${customItems.size}):", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    customItems.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(6.dp))
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                Text("${item.quantity} × Rs ${item.price} = Rs ${(item.quantity * item.price).toInt()}", fontSize = 10.sp, color = Color.Gray)
                            }
                            IconButton(onClick = { customItems.removeAt(index) }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.Delete, null, tint = Color.Red, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (customItems.isNotEmpty()) {
                        val orderId = "INV-${System.currentTimeMillis() % 1000000}"
                        val dateStr = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
                        val subtotal = customItems.sumOf { it.price * it.quantity }
                        val discount = subtotal * ((discountStr.toDoubleOrNull() ?: 0.0) / 100.0)
                        val tax = (subtotal - discount) * ((taxStr.toDoubleOrNull() ?: 0.0) / 100.0)
                        val total = (subtotal - discount) + tax

                        val finalItems = customItems.map { it.copy(orderId = orderId) }
                        val newOrder = PosOrder(
                            id = orderId,
                            date = dateStr,
                            clientId = selectedClientId,
                            subtotal = subtotal,
                            tax = tax,
                            discount = discount,
                            total = total,
                            documentType = docType
                        )
                        onCreateOrder(newOrder, finalItems)
                    }
                },
                enabled = customItems.isNotEmpty()
            ) {
                Text("Create Invoice")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

// -------------------------------------------------------------
// ENTERPRISE SUITE SETTINGS & OFFICIAL LOGO STUDIO TAB
// -------------------------------------------------------------
@Composable
fun PosEnterpriseSettingsSection(context: Context) {
    val coroutineScope = rememberCoroutineScope()
    val initialProfile = remember { getSavedBusinessProfile(context) }

    var bizName by remember { mutableStateOf(initialProfile.businessName) }
    var bizTagline by remember { mutableStateOf(initialProfile.tagline) }
    var bizAddress by remember { mutableStateOf(initialProfile.address) }
    var bizCity by remember { mutableStateOf(initialProfile.cityCountry) }
    var bizPhone by remember { mutableStateOf(initialProfile.phone) }
    var bizEmail by remember { mutableStateOf(initialProfile.email) }
    var bizNtn by remember { mutableStateOf(initialProfile.ntnNumber) }
    var bizStrn by remember { mutableStateOf(initialProfile.strnNumber) }
    var bizPosId by remember { mutableStateOf(initialProfile.fbrPosId) }
    var bizCurrency by remember { mutableStateOf(initialProfile.currency) }

    var showLogoOnInvoice by remember { mutableStateOf(initialProfile.showLogoOnInvoice) }
    var showLogoOnThermal by remember { mutableStateOf(initialProfile.showLogoOnThermal) }
    var thermalThreshold by remember { mutableFloatStateOf(initialProfile.thermalDitherThreshold.toFloat()) }

    var logoBitmap by remember { mutableStateOf(BluetoothThermalPrinterHelper.getSavedLogoBitmap(context)) }

    // Bluetooth Hardware settings
    var selectedPrinterAddress by remember { mutableStateOf(BluetoothThermalPrinterHelper.getSavedPrinterAddress(context)) }
    var selectedPaperSize by remember { mutableStateOf(BluetoothThermalPrinterHelper.getSavedPaperSize(context)) }
    var availablePrinters by remember { mutableStateOf(BluetoothThermalPrinterHelper.getAvailablePrinters(context)) }

    // File picker for Logo Upload (PNG/JPG/WEBP)
    val logoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val savedPath = BluetoothThermalPrinterHelper.saveLogoFromUri(context, uri)
            if (savedPath != null) {
                logoBitmap = BluetoothThermalPrinterHelper.getSavedLogoBitmap(context)
                Toast.makeText(context, "Logo Uploaded in Best Quality! Ready for A4 & Thermal Bills.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Could not process selected image. Please try another PNG or JPG.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Dynamic 1-Bit Thermal Simulation Preview Bitmap
    val simulatedThermalBitmap = remember(logoBitmap, thermalThreshold) {
        logoBitmap?.let { bmp ->
            try {
                val targetWidth = 384
                val aspect = bmp.height.toFloat() / bmp.width.toFloat()
                val targetHeight = (targetWidth * aspect).toInt().coerceAtLeast(1)
                val scaled = Bitmap.createScaledBitmap(bmp, targetWidth, targetHeight, true)
                val mono = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
                val pixels = IntArray(targetWidth * targetHeight)
                scaled.getPixels(pixels, 0, targetWidth, 0, 0, targetWidth, targetHeight)
                val thresh = thermalThreshold.toInt()
                for (i in pixels.indices) {
                    val c = pixels[i]
                    val a = (c ushr 24) and 0xFF
                    val r = (c ushr 16) and 0xFF
                    val g = (c ushr 8) and 0xFF
                    val b = c and 0xFF
                    if (a < 50) {
                        pixels[i] = AndroidColor.WHITE
                    } else {
                        val lum = (0.299 * r + 0.587 * g + 0.114 * b).toInt()
                        pixels[i] = if (lum < thresh) AndroidColor.BLACK else AndroidColor.WHITE
                    }
                }
                mono.setPixels(pixels, 0, targetWidth, 0, 0, targetWidth, targetHeight)
                mono
            } catch (e: Exception) {
                null
            }
        }
    }

    val saveSettings = {
        val updatedProfile = PosBusinessProfile(
            businessName = bizName.trim(),
            tagline = bizTagline.trim(),
            address = bizAddress.trim(),
            cityCountry = bizCity.trim(),
            phone = bizPhone.trim(),
            email = bizEmail.trim(),
            ntnNumber = bizNtn.trim(),
            strnNumber = bizStrn.trim(),
            fbrPosId = bizPosId.trim(),
            currency = bizCurrency.trim().ifBlank { "Rs" },
            logoUri = if (logoBitmap != null) "internal://omni_pos_logo.png" else "",
            showLogoOnInvoice = showLogoOnInvoice,
            showLogoOnThermal = showLogoOnThermal,
            thermalDitherThreshold = thermalThreshold.toInt()
        )
        saveBusinessProfile(context, updatedProfile)
        BluetoothThermalPrinterHelper.savePrinterAddress(context, selectedPrinterAddress)
        BluetoothThermalPrinterHelper.savePaperSize(context, selectedPaperSize)
        Toast.makeText(context, "Enterprise Profile & Branding Settings Saved!", Toast.LENGTH_SHORT).show()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // HEADER TITLE CARD
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Storefront, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            "Enterprise Suite & Branding Studio",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            "Upload official logos, tune thermal contrast, customize receipts & A4 tax invoices",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // -------------------------------------------------------------
        // SECTION 1: OFFICIAL BUSINESS LOGO STUDIO (A4 & THERMAL)
        // -------------------------------------------------------------
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Image, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.width(8.dp))
                            Text("Official Business Logo", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        if (logoBitmap != null) {
                            Surface(
                                color = Color(0xFFE8F5E9),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "✓ High-Res Active",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Text(
                        "Upload your hospital, clinic, pharmacy, or enterprise logo. The app automatically renders it in vector-sharp quality for A4 PDFs and converts it to crisp 1-bit monochrome raster dots for ESC/POS thermal printers.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // LOGO PREVIEW CANVAS
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSystemInDarkTheme()) Color(0xFF1E293B) else Color(0xFFF1F5F9))
                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (logoBitmap != null) {
                            Image(
                                bitmap = logoBitmap!!.asImageBitmap(),
                                contentDescription = "Uploaded Business Logo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    Icons.Default.CloudUpload,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    "No Logo Uploaded (Default text header active)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // UPLOAD / REMOVE BUTTONS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { logoPickerLauncher.launch("image/*") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.UploadFile, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(if (logoBitmap == null) "Upload Logo" else "Change Logo", fontWeight = FontWeight.Bold)
                        }

                        if (logoBitmap != null) {
                            OutlinedButton(
                                onClick = {
                                    BluetoothThermalPrinterHelper.deleteSavedLogo(context)
                                    logoBitmap = null
                                    Toast.makeText(context, "Logo removed", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                            ) {
                                Icon(Icons.Default.Delete, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Remove")
                            }
                        }
                    }

                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    // SWITCHES FOR OUTPUT DESTINATIONS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                            Text("Show Logo on A4 Invoices & PDFs", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Displays high-resolution branded header on laser/inkjet printed A4 bills and exported PDFs", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = showLogoOnInvoice,
                            onCheckedChange = { showLogoOnInvoice = it }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                            Text("Show Logo on Thermal Receipts", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Burns 1-bit monochrome raster image at the top of 58mm & 80mm ESC/POS receipts", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = showLogoOnThermal,
                            onCheckedChange = { showLogoOnThermal = it }
                        )
                    }

                    // THERMAL MONOCHROME CONTRAST / DITHER TUNER
                    if (logoBitmap != null && showLogoOnThermal) {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Thermal Contrast / Dither Threshold", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Text("${thermalThreshold.toInt()} / 255", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                }

                                Slider(
                                    value = thermalThreshold,
                                    onValueChange = { thermalThreshold = it },
                                    valueRange = 50f..200f,
                                    steps = 30
                                )

                                Text(
                                    "Live 1-Bit Thermal Simulation Preview (What the thermal head prints):",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                if (simulatedThermalBitmap != null) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color.White)
                                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(6.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            bitmap = simulatedThermalBitmap.asImageBitmap(),
                                            contentDescription = "Simulated Thermal Raster",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(6.dp),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // -------------------------------------------------------------
        // SECTION 2: ENTERPRISE BUSINESS & LEGAL PROFILE
        // -------------------------------------------------------------
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Business, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Text("Business & Legal Information", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedTextField(
                        value = bizName,
                        onValueChange = { bizName = it },
                        label = { Text("Enterprise / Clinic Name") },
                        leadingIcon = { Icon(Icons.Default.Store, null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = bizTagline,
                        onValueChange = { bizTagline = it },
                        label = { Text("Business Tagline / Subtitle") },
                        leadingIcon = { Icon(Icons.Default.Info, null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = bizAddress,
                            onValueChange = { bizAddress = it },
                            label = { Text("Street Address") },
                            modifier = Modifier.weight(1.4f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = bizCity,
                            onValueChange = { bizCity = it },
                            label = { Text("City / Region") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = bizPhone,
                            onValueChange = { bizPhone = it },
                            label = { Text("Phone / WhatsApp") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = bizEmail,
                            onValueChange = { bizEmail = it },
                            label = { Text("Support Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = bizNtn,
                            onValueChange = { bizNtn = it },
                            label = { Text("NTN Number") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = bizStrn,
                            onValueChange = { bizStrn = it },
                            label = { Text("STRN Number") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = bizPosId,
                            onValueChange = { bizPosId = it },
                            label = { Text("FBR POS ID") },
                            modifier = Modifier.weight(1.2f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = bizCurrency,
                            onValueChange = { bizCurrency = it },
                            label = { Text("Currency Symbol") },
                            modifier = Modifier.weight(0.8f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }
        }

        // -------------------------------------------------------------
        // SECTION 3: PRINTER & HARDWARE INTEGRATION
        // -------------------------------------------------------------
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Print, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.width(8.dp))
                            Text("Bluetooth & Paper Setup", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                        IconButton(
                            onClick = {
                                availablePrinters = BluetoothThermalPrinterHelper.getAvailablePrinters(context)
                                Toast.makeText(context, "Found ${availablePrinters.size} paired devices", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh paired printers")
                        }
                    }

                    // Paper Presets
                    Text("Default Printing Paper Format:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(
                            BluetoothThermalPrinterHelper.PAPER_58MM to "58mm Thermal",
                            BluetoothThermalPrinterHelper.PAPER_80MM to "80mm Thermal",
                            BluetoothThermalPrinterHelper.PAPER_A4 to "A4 Page / PDF"
                        ).forEach { (formatKey, label) ->
                            val isSelected = selectedPaperSize == formatKey
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedPaperSize = formatKey },
                                label = { Text(label, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Paired Printers List
                    Text("Paired Bluetooth Printer (${availablePrinters.size} available):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    if (availablePrinters.isEmpty()) {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "No paired Bluetooth printers found. Pair your thermal printer in Android Bluetooth Settings first, then tap refresh above.",
                                fontSize = 11.sp,
                                modifier = Modifier.padding(10.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            availablePrinters.forEach { device ->
                                val isSelected = selectedPrinterAddress == device.address
                                Surface(
                                    onClick = { selectedPrinterAddress = device.address },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                    border = if (isSelected) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(device.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(device.address, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                        if (isSelected) {
                                            Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // TEST PRINT WITH LOGO
                    FilledTonalButton(
                        onClick = {
                            val sampleItems = listOf(
                                PosOrderItem("1", "TEST-01", "1", "Panadol Extra 500mg", 2, 180.0),
                                PosOrderItem("2", "TEST-01", "2", "Multivitamin & Zinc 120ml", 1, 240.0)
                            )
                            val a4SampleText = BluetoothThermalPrinterHelper.buildA4InvoiceText(
                                businessName = bizName.ifBlank { "AL-TAHIR HEALTHCARE & ENTERPRISE" },
                                tagline = bizTagline.ifBlank { "Official Test Receipt" },
                                address = "${bizAddress.ifBlank { "Hospital Road" }}, ${bizCity.ifBlank { "Buner" }}",
                                phone = bizPhone.ifBlank { "+92 300 1234567" },
                                orderId = "TEST-9999",
                                dateStr = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date()),
                                clientName = "Test Client / Quality Check",
                                items = sampleItems,
                                subtotal = 600.0,
                                discount = 0.0,
                                tax = 0.0,
                                total = 600.0,
                                paymentMethod = "Test Run",
                                footerNote = "Test print verification with logo rendering."
                            )

                            if (selectedPaperSize == BluetoothThermalPrinterHelper.PAPER_A4 || selectedPrinterAddress.isBlank()) {
                                BluetoothThermalPrinterHelper.printA4ViaSystem(
                                    context = context,
                                    jobName = "OmniPOS_Test_Print",
                                    documentTitle = "Test_Receipt_With_Logo",
                                    contentText = a4SampleText,
                                    logoBitmap = logoBitmap,
                                    showLogo = showLogoOnInvoice
                                )
                            } else {
                                coroutineScope.launch(Dispatchers.IO) {
                                    val payload = BluetoothThermalPrinterHelper.buildPosReceiptPayload(
                                        businessName = bizName.ifBlank { "AL-TAHIR HEALTHCARE & ENTERPRISE" },
                                        tagline = bizTagline.ifBlank { "Official Test Receipt" },
                                        address = "${bizAddress.ifBlank { "Hospital Road" }}, ${bizCity.ifBlank { "Buner" }}",
                                        phone = bizPhone.ifBlank { "+92 300 1234567" },
                                        orderId = "TEST-9999",
                                        dateStr = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date()),
                                        items = sampleItems,
                                        subtotal = 600.0,
                                        discount = 0.0,
                                        tax = 0.0,
                                        total = 600.0,
                                        paymentMethod = "Test Run",
                                        footerNote = "Test print verification with logo rendering.",
                                        logoBitmap = logoBitmap,
                                        showLogo = showLogoOnThermal,
                                        threshold = thermalThreshold.toInt()
                                    )
                                    val (success, msg) = BluetoothThermalPrinterHelper.printPayload(context, selectedPrinterAddress, payload)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.ReceiptLong, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("🖨️ Test Print Sample Bill (with Logo)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // -------------------------------------------------------------
        // SAVE & APPLY FLOATING ACTION
        // -------------------------------------------------------------
        item {
            Button(
                onClick = { saveSettings() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Save & Apply Enterprise Settings", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(30.dp))
        }
    }
}
