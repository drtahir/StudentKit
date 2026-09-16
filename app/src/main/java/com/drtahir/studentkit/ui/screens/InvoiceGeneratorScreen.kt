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
import androidx.compose.runtime.snapshots.SnapshotStateMap
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

                            val itemsList = activeCart.map { (prodId, qty) ->
                                val prod = allProducts.firstOrNull { it.id == prodId }
                                    ?: PosProduct(
                                        id = prodId,
                                        name = "Item $prodId",
                                        category = "General",
                                        price = 0.0,
                                        stock = 100,
                                        unit = "Unit"
                                    )
                                PosOrderItem(
                                    id = UUID.randomUUID().toString(),
                                    orderId = orderId,
                                    productId = prod.id,
                                    name = prod.name,
                                    quantity = qty,
                                    price = prod.price
                                )
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
@OptIn(ExperimentalMaterial3Api::class)
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
    var viewMode by remember { mutableStateOf("Visual Bill") } // "Visual Bill", "Monospace Print"
    var zoomFactor by remember { mutableFloatStateOf(10f) }
    var isDarkPaper by remember { mutableStateOf(false) }

    // Business profile & Logo preferences
    val profile = remember { getSavedBusinessProfile(context) }
    val logoBitmap = remember { BluetoothThermalPrinterHelper.getSavedLogoBitmap(context) }

    val businessName = profile.businessName
    val businessAddress = "${profile.address}, ${profile.cityCountry}"
    val businessPhone = profile.phone
    val businessNtn = profile.ntnNumber

    val a4Text = remember(order, items, client, profile) {
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

    val thermalText = remember(order, items, client, profile, selectedFormatMode) {
        BluetoothThermalPrinterHelper.buildThermalReceiptText(
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
            footerNote = "Thank you for your visit!",
            is80mm = selectedFormatMode == "80mm Thermal"
        )
    }

    // Helper to generate the active high-resolution bitmap for gallery saving and sharing
    fun getActiveBillBitmap(): Bitmap {
        return if (selectedFormatMode == "A4 Standard") {
            BluetoothThermalPrinterHelper.renderA4InvoiceBitmap(
                businessName = businessName,
                tagline = profile.tagline.ifBlank { "Healthcare & Enterprise Solutions" },
                address = businessAddress,
                phone = businessPhone,
                ntn = businessNtn,
                orderId = order.id,
                dateStr = order.date,
                clientName = client?.name ?: "Walking Customer / Cash",
                clientPhone = client?.phone ?: "",
                clientAddress = client?.address ?: "",
                items = items,
                subtotal = order.subtotal,
                discount = order.discount,
                tax = order.tax,
                total = order.total,
                paymentMethod = "Cash / Terminal",
                footerNote = "Official computerized sales invoice. Computer generated.",
                logoBitmap = logoBitmap,
                showLogo = profile.showLogoOnInvoice
            )
        } else {
            BluetoothThermalPrinterHelper.renderThermalReceiptBitmap(
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
                footerNote = "Thank you for your visit!",
                logoBitmap = logoBitmap,
                showLogo = profile.showLogoOnThermal,
                is80mm = selectedFormatMode == "80mm Thermal"
            )
        }
    }

    val invoiceSummary = remember(order, items, client, businessName) {
        "🧾 *Official Bill - ${businessName.ifBlank { "OmniPOS Enterprise" }}*\n" +
        "📄 Invoice #: ${order.id}\n" +
        "📅 Date: ${order.date}\n" +
        "👤 Customer: ${client?.name ?: "Walking Customer / Cash"}\n" +
        "📦 Items: ${items.size} item(s)\n" +
        "💵 Total: Rs ${String.format("%.2f", order.total)}\n" +
        "💳 Payment: Cash / Terminal\n\n" +
        "Thank you for your business!"
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

                            // View Mode Toggle, Zoom & Paper Tone Controls
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                IconButton(
                                    onClick = { viewMode = if (viewMode == "Visual Bill") "Monospace Print" else "Visual Bill" },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        if (viewMode == "Visual Bill") Icons.Default.Code else Icons.Default.ViewAgenda,
                                        contentDescription = "Toggle Sheet/Code View",
                                        modifier = Modifier.size(18.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
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

                        // Format Preset Chips (A4 vs Thermal)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("A4 Standard", "80mm Thermal", "58mm Slip").forEach { fmt ->
                                val isSelected = selectedFormatMode == fmt
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedFormatMode = fmt },
                                    label = {
                                        Text(
                                            when (fmt) {
                                                "A4 Standard" -> "📄 A4 Standard"
                                                "80mm Thermal" -> "🧾 80mm Thermal"
                                                else -> "🧾 58mm Slip"
                                            },
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                // SCROLLABLE DOCUMENT VIEWPORT
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    if (selectedFormatMode == "A4 Standard") {
                        // -------------------------------------------------------------
                        // A4 STANDARD INVOICE VIEWPORT
                        // -------------------------------------------------------------
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .shadow(6.dp, RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDarkPaper) Color(0xFF1E293B) else Color.White
                            ),
                            border = BorderStroke(1.dp, if (isDarkPaper) Color(0xFF334155) else Color(0xFFCBD5E1))
                        ) {
                            if (viewMode == "Monospace Print") {
                                // Raw printable ASCII layout
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .horizontalScroll(rememberScrollState())
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (profile.showLogoOnInvoice && logoBitmap != null) {
                                        Image(
                                            bitmap = logoBitmap.asImageBitmap(),
                                            contentDescription = "Logo",
                                            modifier = Modifier.height(50.dp).padding(bottom = 10.dp),
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
                            } else {
                                // Authentic A4 Tax Invoice Page Layout
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Top Accent Bar
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(4.dp)
                                            .background(Color(0xFF0F766E), RoundedCornerShape(2.dp))
                                    )

                                    // Invoice Header Section
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        // Left: Business Branding
                                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                            if (profile.showLogoOnInvoice && logoBitmap != null) {
                                                Image(
                                                    bitmap = logoBitmap.asImageBitmap(),
                                                    contentDescription = "Logo",
                                                    modifier = Modifier.size(54.dp).clip(RoundedCornerShape(6.dp)),
                                                    contentScale = ContentScale.Fit
                                                )
                                                Spacer(modifier = Modifier.width(10.dp))
                                            }
                                            Column {
                                                Text(
                                                    businessName.ifBlank { "OmniPOS Enterprise" },
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 17.sp,
                                                    color = if (isDarkPaper) Color.White else Color(0xFF0F172A)
                                                )
                                                if (profile.tagline.isNotBlank()) {
                                                    Text(profile.tagline, fontSize = 11.sp, color = Color.Gray)
                                                }
                                                Text(businessAddress, fontSize = 11.sp, color = Color.Gray)
                                                Text("Tel: $businessPhone" + if (businessNtn.isNotBlank()) " | NTN: $businessNtn" else "", fontSize = 11.sp, color = Color.Gray)
                                            }
                                        }

                                        // Right: Tax Invoice Title & Metadata
                                        Column(horizontalAlignment = Alignment.End) {
                                            Surface(
                                                color = Color(0xFF0F766E),
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    "TAX INVOICE",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 13.sp,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                                )
                                            }
                                            Spacer(Modifier.height(4.dp))
                                            Text("Invoice #: ${order.id}", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = if (isDarkPaper) Color.White else Color(0xFF0F172A))
                                            Text("Date: ${order.date}", fontSize = 11.sp, color = Color.Gray)
                                            Text("Status: PAID (Cash)", fontSize = 11.sp, color = Color(0xFF16A34A), fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    HorizontalDivider(color = if (isDarkPaper) Color(0xFF334155) else Color(0xFFE2E8F0))

                                    // Billed To & Issuance Cards
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Surface(
                                            modifier = Modifier.weight(1f),
                                            color = if (isDarkPaper) Color(0xFF0F172A) else Color(0xFFF8FAFC),
                                            shape = RoundedCornerShape(6.dp),
                                            border = BorderStroke(1.dp, if (isDarkPaper) Color(0xFF334155) else Color(0xFFE2E8F0))
                                        ) {
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Text("BILLED TO / CUSTOMER", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                                Spacer(Modifier.height(3.dp))
                                                Text(client?.name ?: "Walking Customer / Cash", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = if (isDarkPaper) Color.White else Color(0xFF0F172A))
                                                if (!client?.phone.isNullOrBlank()) Text("Phone: ${client?.phone}", fontSize = 11.sp, color = Color.Gray)
                                                if (!client?.address.isNullOrBlank()) Text("Address: ${client?.address}", fontSize = 11.sp, color = Color.Gray)
                                            }
                                        }

                                        Surface(
                                            modifier = Modifier.weight(1f),
                                            color = if (isDarkPaper) Color(0xFF0F172A) else Color(0xFFF8FAFC),
                                            shape = RoundedCornerShape(6.dp),
                                            border = BorderStroke(1.dp, if (isDarkPaper) Color(0xFF334155) else Color(0xFFE2E8F0))
                                        ) {
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Text("ISSUANCE & REGISTER", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                                Spacer(Modifier.height(3.dp))
                                                Text("POS Register Terminal #01", fontWeight = FontWeight.Medium, fontSize = 12.sp, color = if (isDarkPaper) Color.White else Color(0xFF0F172A))
                                                Text("Cashier: Administrator", fontSize = 11.sp, color = Color.Gray)
                                                Text("Payment: Cash / Terminal", fontSize = 11.sp, color = Color.Gray)
                                            }
                                        }
                                    }

                                    // Items Table
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(6.dp),
                                        border = BorderStroke(1.dp, if (isDarkPaper) Color(0xFF334155) else Color(0xFFCBD5E1))
                                    ) {
                                        Column {
                                            // Table Header
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(if (isDarkPaper) Color(0xFF0F172A) else Color(0xFF0F172A))
                                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text("S#", modifier = Modifier.width(30.dp), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                                Text("ITEM DESCRIPTION", modifier = Modifier.weight(1f), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                                Text("QTY", modifier = Modifier.width(45.dp), textAlign = TextAlign.Center, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                                Text("RATE", modifier = Modifier.width(65.dp), textAlign = TextAlign.End, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                                Text("TOTAL (RS)", modifier = Modifier.width(80.dp), textAlign = TextAlign.End, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                            }

                                            // Table Rows
                                            items.forEachIndexed { idx, item ->
                                                val isEven = idx % 2 == 0
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(
                                                            if (isDarkPaper) {
                                                                if (isEven) Color(0xFF1E293B) else Color(0xFF172033)
                                                            } else {
                                                                if (isEven) Color.White else Color(0xFFF8FAFC)
                                                            }
                                                        )
                                                        .padding(horizontal = 8.dp, vertical = 7.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text("${idx + 1}", modifier = Modifier.width(30.dp), fontSize = 11.sp, color = if (isDarkPaper) Color.White else Color(0xFF0F172A))
                                                    Text(item.name, modifier = Modifier.weight(1f), fontSize = 11.sp, fontWeight = FontWeight.Medium, color = if (isDarkPaper) Color.White else Color(0xFF0F172A))
                                                    Text("${item.quantity}", modifier = Modifier.width(45.dp), textAlign = TextAlign.Center, fontSize = 11.sp, color = if (isDarkPaper) Color.White else Color(0xFF0F172A))
                                                    Text(String.format("%.2f", item.price), modifier = Modifier.width(65.dp), textAlign = TextAlign.End, fontSize = 11.sp, color = Color.Gray)
                                                    Text(String.format("%.2f", item.price * item.quantity), modifier = Modifier.width(80.dp), textAlign = TextAlign.End, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = if (isDarkPaper) Color.White else Color(0xFF0F172A))
                                                }
                                                if (idx < items.lastIndex) {
                                                    HorizontalDivider(color = if (isDarkPaper) Color(0xFF334155) else Color(0xFFE2E8F0), thickness = 0.5.dp)
                                                }
                                            }
                                        }
                                    }

                                    // Totals & Terms
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        // Left: Terms & Conditions
                                        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                                            Text("TERMS & CONDITIONS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                            Spacer(Modifier.height(2.dp))
                                            Text("• Computer generated tax invoice. No signature required.", fontSize = 10.sp, color = Color.Gray)
                                            Text("• Goods received in proper sealed condition.", fontSize = 10.sp, color = Color.Gray)
                                            Text("• Warranty claims require original bill presentation.", fontSize = 10.sp, color = Color.Gray)
                                        }

                                        // Right: Totals Card
                                        Surface(
                                            modifier = Modifier.width(180.dp),
                                            color = if (isDarkPaper) Color(0xFF0F172A) else Color(0xFFF8FAFC),
                                            shape = RoundedCornerShape(6.dp),
                                            border = BorderStroke(1.dp, if (isDarkPaper) Color(0xFF334155) else Color(0xFFCBD5E1))
                                        ) {
                                            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text("Subtotal:", fontSize = 11.sp, color = Color.Gray)
                                                    Text(String.format("Rs %.2f", order.subtotal), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                                }
                                                if (order.discount > 0) {
                                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                        Text("Discount:", fontSize = 11.sp, color = Color(0xFFE11D48))
                                                        Text(String.format("-Rs %.2f", order.discount), fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFFE11D48))
                                                    }
                                                }
                                                if (order.tax > 0) {
                                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                        Text("Sales Tax:", fontSize = 11.sp, color = Color.Gray)
                                                        Text(String.format("Rs %.2f", order.tax), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                                    }
                                                }
                                                HorizontalDivider(color = if (isDarkPaper) Color(0xFF334155) else Color(0xFFCBD5E1))
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text("TOTAL:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F766E))
                                                    Text(String.format("Rs %.2f", order.total), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F766E))
                                                }
                                            }
                                        }
                                    }

                                    Spacer(Modifier.height(10.dp))

                                    // Signatures Section
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Box(modifier = Modifier.width(130.dp).height(1.dp).background(Color.Gray))
                                            Spacer(Modifier.height(4.dp))
                                            Text("Authorized Signatory & Stamp", fontSize = 10.sp, color = Color.Gray)
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Box(modifier = Modifier.width(130.dp).height(1.dp).background(Color.Gray))
                                            Spacer(Modifier.height(4.dp))
                                            Text("Customer Receiver Signature", fontSize = 10.sp, color = Color.Gray)
                                        }
                                    }

                                    // Footer text
                                    Text(
                                        "Computerized A4 Tax Invoice • Generated via OmniPOS Enterprise Suite • All Rights Reserved",
                                        fontSize = 9.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    } else {
                        // -------------------------------------------------------------
                        // THERMAL RECEIPT VIEWPORT (80mm or 58mm POS Roll)
                        // -------------------------------------------------------------
                        val slipMaxWidth = if (selectedFormatMode == "80mm Thermal") 360.dp else 290.dp

                        Card(
                            modifier = Modifier
                                .widthIn(max = slipMaxWidth)
                                .fillMaxHeight()
                                .shadow(8.dp, RoundedCornerShape(6.dp)),
                            shape = RoundedCornerShape(6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDarkPaper) Color(0xFF1E293B) else Color.White
                            ),
                            border = BorderStroke(1.dp, if (isDarkPaper) Color(0xFF334155) else Color(0xFFCBD5E1))
                        ) {
                            if (viewMode == "Monospace Print") {
                                // Raw printable monospace receipt text
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (profile.showLogoOnThermal && logoBitmap != null) {
                                        Image(
                                            bitmap = logoBitmap.asImageBitmap(),
                                            contentDescription = "Logo",
                                            modifier = Modifier.height(40.dp).padding(bottom = 6.dp),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                    Text(
                                        text = thermalText,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = zoomFactor.sp,
                                        color = if (isDarkPaper) Color(0xFFF1F5F9) else Color(0xFF0F172A),
                                        lineHeight = (zoomFactor * 1.3f).sp
                                    )
                                }
                            } else {
                                // Realistic POS Thermal Paper Roll Slip
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .padding(14.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    // Top Serrated / Dashed Tear Edge
                                    Text(
                                        "- - - - - - - - - - - - - - - - - - - - - - - - - - - -",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )

                                    // Centered Logo
                                    if (profile.showLogoOnThermal && logoBitmap != null) {
                                        Image(
                                            bitmap = logoBitmap.asImageBitmap(),
                                            contentDescription = "Thermal Logo",
                                            modifier = Modifier.height(42.dp).padding(bottom = 4.dp),
                                            contentScale = ContentScale.Fit
                                        )
                                    }

                                    // Store Header
                                    Text(
                                        businessName.uppercase().ifBlank { "OMNIPOS ENTERPRISE" },
                                        fontWeight = FontWeight.Bold,
                                        fontSize = if (selectedFormatMode == "80mm Thermal") 15.sp else 13.sp,
                                        fontFamily = FontFamily.Monospace,
                                        textAlign = TextAlign.Center,
                                        color = if (isDarkPaper) Color.White else Color.Black
                                    )
                                    if (profile.tagline.isNotBlank()) {
                                        Text(profile.tagline, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color.Gray, textAlign = TextAlign.Center)
                                    }
                                    if (businessAddress.isNotBlank()) {
                                        Text(businessAddress, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color.Gray, textAlign = TextAlign.Center)
                                    }
                                    if (businessPhone.isNotBlank()) {
                                        Text("Tel: $businessPhone", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color.Gray, textAlign = TextAlign.Center)
                                    }

                                    // Divider
                                    Text(
                                        "------------------------------------------------",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )

                                    // Receipt Meta Information
                                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("Receipt #:", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color.Gray)
                                            Text(order.id, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                        }
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("Date & Time:", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color.Gray)
                                            Text(order.date, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                                        }
                                        if (!client?.name.isNullOrBlank() && client?.name != "Walking Customer / Cash") {
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("Customer:", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color.Gray)
                                                Text(client!!.name, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Medium)
                                            }
                                        }
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("Payment:", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color.Gray)
                                            Text("Cash / Terminal", fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                                        }
                                    }

                                    // Divider
                                    Text(
                                        "------------------------------------------------",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )

                                    // Columns Header
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("ITEM", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                        Text("QTY", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                        Text("AMOUNT", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                    }

                                    // Divider
                                    Text(
                                        "- - - - - - - - - - - - - - - - - - - - - - - -",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )

                                    // Line Items
                                    items.forEach { item ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                item.name,
                                                modifier = Modifier.weight(1f).padding(end = 4.dp),
                                                fontSize = 11.sp,
                                                fontFamily = FontFamily.Monospace,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                "${item.quantity}",
                                                modifier = Modifier.width(30.dp),
                                                textAlign = TextAlign.Center,
                                                fontSize = 11.sp,
                                                fontFamily = FontFamily.Monospace
                                            )
                                            Text(
                                                String.format("%.2f", item.price * item.quantity),
                                                modifier = Modifier.width(65.dp),
                                                textAlign = TextAlign.End,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }
                                    }

                                    // Divider
                                    Text(
                                        "------------------------------------------------",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )

                                    // Totals breakdown
                                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("Subtotal:", fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                                            Text(String.format("Rs %.2f", order.subtotal), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                                        }
                                        if (order.discount > 0) {
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("Discount:", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = Color(0xFFE11D48))
                                                Text(String.format("-Rs %.2f", order.discount), fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = Color(0xFFE11D48))
                                            }
                                        }
                                        if (order.tax > 0) {
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("Sales Tax / GST:", fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                                                Text(String.format("Rs %.2f", order.tax), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                                            }
                                        }
                                    }

                                    // Double Divider
                                    Text(
                                        "================================================",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )

                                    // Bold Grand Total
                                    Text(
                                        "*** TOTAL: Rs ${String.format("%.2f", order.total)} ***",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = if (isDarkPaper) Color.White else Color.Black,
                                        textAlign = TextAlign.Center
                                    )

                                    // Double Divider
                                    Text(
                                        "================================================",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    // Barcode Graphic Simulation
                                    Text(
                                        "||| | ||||| | ||| |||| | |||",
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        letterSpacing = 1.sp,
                                        color = if (isDarkPaper) Color.White else Color.Black
                                    )
                                    Text("*${order.id}*", fontSize = 10.sp, fontFamily = FontFamily.Monospace)

                                    Spacer(Modifier.height(4.dp))
                                    Text("Thank you for your visit!", fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Medium)
                                    Text("*** Powered by OmniPOS ***", fontSize = 9.sp, fontFamily = FontFamily.Monospace, color = Color.Gray)

                                    // Bottom Tear Edge
                                    Text(
                                        "- - - - - - - - - - - - - - - - - - - - - - - - - - - -",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )
                                }
                            }
                        }
                    }
                }

                // -------------------------------------------------------------
                // BOTTOM PRINT, SAVE TO GALLERY & SHARE CONTROLS
                // -------------------------------------------------------------
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // ROW 1: PRINT & SAVE TO GALLERY
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Print Active Bill Format
                            Button(
                                onClick = {
                                    if (selectedFormatMode == "A4 Standard") {
                                        BluetoothThermalPrinterHelper.printA4ViaSystem(
                                            context = context,
                                            jobName = "OmniPOS_Invoice_${order.id}",
                                            documentTitle = "Invoice_${order.id}",
                                            contentText = a4Text,
                                            logoBitmap = logoBitmap,
                                            showLogo = profile.showLogoOnInvoice
                                        )
                                    } else {
                                        val printerAddress = BluetoothThermalPrinterHelper.getSavedPrinterAddress(context)
                                        if (printerAddress.isBlank()) {
                                            Toast.makeText(context, "No Bluetooth thermal printer paired. Spooling to system printer...", Toast.LENGTH_SHORT).show()
                                            BluetoothThermalPrinterHelper.printA4ViaSystem(
                                                context = context,
                                                jobName = "OmniPOS_Thermal_${order.id}",
                                                documentTitle = "ThermalReceipt_${order.id}",
                                                contentText = thermalText,
                                                logoBitmap = logoBitmap,
                                                showLogo = profile.showLogoOnThermal
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
                                                            contentText = thermalText,
                                                            logoBitmap = logoBitmap,
                                                            showLogo = profile.showLogoOnThermal
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1.1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Print, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    if (selectedFormatMode == "A4 Standard") "Print A4 Bill" else "Print Thermal",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    maxLines = 1
                                )
                            }

                            // Save to Gallery
                            FilledTonalButton(
                                onClick = {
                                    coroutineScope.launch(Dispatchers.IO) {
                                        val bitmap = getActiveBillBitmap()
                                        val title = if (selectedFormatMode == "A4 Standard") "Invoice_${order.id}_A4" else "Receipt_${order.id}_Thermal"
                                        val uri = BluetoothThermalPrinterHelper.saveBillBitmapToGallery(context, bitmap, title)
                                        withContext(Dispatchers.Main) {
                                            if (uri != null) {
                                                Toast.makeText(context, "✅ Saved to Gallery! (Pictures/OmniPOS_Invoices)", Toast.LENGTH_LONG).show()
                                            } else {
                                                Toast.makeText(context, "Could not save image to gallery", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1.1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Save to Gallery", fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1)
                            }
                        }

                        // ROW 2: WHATSAPP DIRECT SHARE, GENERAL SHARE, COPY
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Direct WhatsApp Share (with official WhatsApp Green branding)
                            Button(
                                onClick = {
                                    coroutineScope.launch(Dispatchers.IO) {
                                        val bitmap = getActiveBillBitmap()
                                        withContext(Dispatchers.Main) {
                                            BluetoothThermalPrinterHelper.shareBillViaWhatsApp(
                                                context = context,
                                                bitmap = bitmap,
                                                orderId = order.id,
                                                summaryText = invoiceSummary
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1.3f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF25D366),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Send, null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("WhatsApp", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }

                            // General System Share Sheet
                            OutlinedButton(
                                onClick = {
                                    coroutineScope.launch(Dispatchers.IO) {
                                        val bitmap = getActiveBillBitmap()
                                        withContext(Dispatchers.Main) {
                                            BluetoothThermalPrinterHelper.shareBillGeneral(
                                                context = context,
                                                bitmap = bitmap,
                                                orderId = order.id,
                                                summaryText = invoiceSummary
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Share, null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Share", fontSize = 12.sp)
                            }

                            // Copy Invoice Text
                            OutlinedButton(
                                onClick = {
                                    val textToCopy = if (selectedFormatMode == "A4 Standard") a4Text else thermalText
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("Invoice Text", textToCopy))
                                    Toast.makeText(context, "Bill text copied to clipboard!", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(16.dp))
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
    activeCart: SnapshotStateMap<String, Int>,
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

    // Direct reactive mapping from activeCart so updates always trigger UI recomposition
    val cartItemsList = activeCart.mapNotNull { (prodId, qty) ->
        val prod = allProducts.firstOrNull { it.id == prodId }
            ?: PosProduct(
                id = prodId,
                name = "Item $prodId",
                category = "General",
                price = 0.0,
                stock = 100,
                unit = "Unit"
            )
        prod to qty
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("TOTAL", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text(
                                "Rs ${String.format("%.2f", grandTotal)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1
                            )
                        }
                    }
                }

                // Instant Checkout Button -> Launches Fullscreen Invoice & Print Preview
                val totalQtyInCart = cartItemsList.sumOf { it.second }
                Button(
                    onClick = onCheckout,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(10.dp),
                    enabled = cartItemsList.isNotEmpty()
                ) {
                    Icon(Icons.Default.ReceiptLong, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        if (totalQtyInCart > 0) "Checkout ($totalQtyInCart)" else "Checkout",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        maxLines = 1
                    )
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
