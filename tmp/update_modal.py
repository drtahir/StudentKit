import sys

path = 'app/src/main/java/com/drtahir/studentkit/ui/screens/DocumentScreens.kt'
with open(path, 'r', encoding='utf-8') as f:
    content = f.read()

start_target = '    // PRINTABLE RECEIPT / INVOICE PREVIEW MODAL (A4 & THERMAL + WHATSAPP SHARE)\n    if (showReceiptModal && generatedOrderForReceipt != null) {'
end_target = '        )\n    }\n}\n\n@OptIn(ExperimentalMaterial3Api::class)\n@Composable\nfun OmniPosInventoryTab'

p1 = content.find(start_target)
p2 = content.find(end_target)
assert p1 != -1, 'start_target not found'
assert p2 != -1, 'end_target not found'

replacement = """    // FULL SCREEN PRINTABLE RECEIPT / INVOICE PREVIEW MODAL (A4 & THERMAL + WHATSAPP SHARE)
    if (showReceiptModal && generatedOrderForReceipt != null) {
        val order = generatedOrderForReceipt!!
        val items = generatedItemsForReceipt
        val profile = getSavedBusinessProfile(context)
        var previewFormat by remember { mutableStateOf("A4 Tax Invoice") } // "A4 Tax Invoice" vs "Thermal 80mm"

        Dialog(
            onDismissRequest = { showReceiptModal = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    // Top Header Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.ReceiptLong,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    "Invoice Preview & Print",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    "Invoice #${order.id} • ${order.date}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        IconButton(
                            onClick = { showReceiptModal = false },
                            modifier = Modifier
                                .size(36.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(18.dp))
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    // Format Switcher Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = previewFormat == "A4 Tax Invoice",
                            onClick = { previewFormat = "A4 Tax Invoice" },
                            label = { Text("A4 Tax Invoice Sheet", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                            leadingIcon = { Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = previewFormat == "Thermal 80mm",
                            onClick = { previewFormat = "Thermal 80mm" },
                            label = { Text("Thermal Receipt (80mm)", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                            leadingIcon = { Icon(Icons.Default.Receipt, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    // Center Scrollable View with background
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (previewFormat == "Thermal 80mm") {
                                // THERMAL RECEIPT 80MM POS FORMAT
                                Column(
                                    modifier = Modifier
                                        .widthIn(max = 380.dp)
                                        .fillMaxWidth()
                                        .background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp))
                                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                        .padding(16.dp)
                                ) {
                                    Text(profile.businessName.uppercase(), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    Text(profile.tagline, fontSize = 10.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    Text("${profile.address}, ${profile.cityCountry}", fontSize = 9.sp, color = Color.DarkGray, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    Text("Tel: ${profile.phone} | WA: ${profile.whatsapp}", fontSize = 9.sp, color = Color.DarkGray, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    
                                    if (profile.ntnNumber.isNotBlank() || profile.strnNumber.isNotBlank()) {
                                        Text("NTN: ${profile.ntnNumber} | STRN: ${profile.strnNumber}", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    }
                                    if (profile.drugSaleLicenseNo.isNotBlank()) {
                                        Text("Pharma DSL License #: ${profile.drugSaleLicenseNo}", fontSize = 9.sp, color = Color(0xFF00695C), modifier = Modifier.align(Alignment.CenterHorizontally))
                                    }
                                    if (profile.healthCommissionNo.isNotBlank()) {
                                        Text("Health Reg #: ${profile.healthCommissionNo}", fontSize = 9.sp, color = Color(0xFF1565C0), modifier = Modifier.align(Alignment.CenterHorizontally))
                                    }
                                    if (profile.foodSafetyLicenseNo.isNotBlank()) {
                                        Text("Food Safety License #: ${profile.foodSafetyLicenseNo}", fontSize = 9.sp, color = Color(0xFFE65100), modifier = Modifier.align(Alignment.CenterHorizontally))
                                    }
                                    Text("--------------------------------------------------", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    Text("INVOICE #: ${order.id}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.Black)
                                    Text("DATE: ${order.date}", fontSize = 10.sp, color = Color.Black)
                                    Text("CLIENT: ${selectedClient?.name ?: "Walk-in Customer"}", fontSize = 10.sp, color = Color.Black)
                                    Text("PAYMENT MODE: ${order.documentType}", fontSize = 10.sp, color = Color.Black)
                                    Text("--------------------------------------------------", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))

                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("ITEM DESCR.", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(2f))
                                        Text("QTY", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(0.7f))
                                        Text("TOTAL", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(1f))
                                    }
                                    Text("--------------------------------------------------", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))

                                    items.forEach { item ->
                                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text(item.name, fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(2f))
                                            Text("${item.quantity}", fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(0.7f))
                                            Text("${profile.currency} ${item.price * item.quantity}", fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(1f))
                                        }
                                    }
                                    Text("--------------------------------------------------", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))

                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("SUBTOTAL:", fontSize = 10.sp, color = Color.Black)
                                        Text("${profile.currency} ${order.subtotal}", fontSize = 10.sp, color = Color.Black)
                                    }
                                    if (order.discount > 0) {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text("DISCOUNT:", fontSize = 10.sp, color = Color.Black)
                                            Text("- ${profile.currency} ${order.discount}", fontSize = 10.sp, color = Color.Black)
                                        }
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("TAX (SALES/GOVT):", fontSize = 10.sp, color = Color.Black)
                                        Text("${profile.currency} ${order.tax}", fontSize = 10.sp, color = Color.Black)
                                    }
                                    Text("--------------------------------------------------", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("NET TOTAL:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Black)
                                        Text("${profile.currency} ${order.total}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Black)
                                    }
                                    Text("--------------------------------------------------", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(profile.invoiceFooterNote, fontSize = 9.sp, color = Color.DarkGray, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    Text("System Generated via OmniPOS Suite", fontSize = 8.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
                                }
                            } else {
                                // A4 TAX INVOICE FORMAT
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White, RoundedCornerShape(8.dp))
                                        .border(1.dp, Color(0xFF0D47A1), RoundedCornerShape(8.dp))
                                        .padding(16.dp)
                                ) {
                                    // A4 Top Header with Business Branding & Tax Details
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Column(modifier = Modifier.weight(2f)) {
                                            Text(profile.businessName.uppercase(), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0D47A1))
                                            Text(profile.tagline, fontSize = 10.sp, color = Color.DarkGray)
                                            Spacer(Modifier.height(4.dp))
                                            Text("📍 ${profile.address}, ${profile.cityCountry}", fontSize = 9.sp, color = Color.DarkGray)
                                            Text("📞 Tel: ${profile.phone} | WA: ${profile.whatsapp}", fontSize = 9.sp, color = Color.Black)
                                            Text("✉️ Email: ${profile.email} | Web: ${profile.website}", fontSize = 9.sp, color = Color.Gray)
                                        }
                                        Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1.3f)) {
                                            Box(
                                                modifier = Modifier
                                                    .background(Color(0xFF0D47A1), RoundedCornerShape(4.dp))
                                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                            ) {
                                                Text("OFFICIAL TAX INVOICE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                            }
                                            Spacer(Modifier.height(6.dp))
                                            Text("Invoice #: ${order.id}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.Black)
                                            Text("Date: ${order.date}", fontSize = 10.sp, color = Color.DarkGray)
                                            Text("Status: PAID", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color(0xFF2E7D32))
                                        }
                                    }

                                    Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFF0D47A1))

                                    // TAX & PROFESSIONAL REGISTRATIONS CHIPS GRID
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            if (profile.ntnNumber.isNotBlank()) Text("NTN: ${profile.ntnNumber}", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                            if (profile.strnNumber.isNotBlank()) Text("STRN: ${profile.strnNumber}", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                            if (profile.fbrPosId.isNotBlank()) Text("FBR POS ID: ${profile.fbrPosId}", fontSize = 9.sp, color = Color.DarkGray)
                                        }
                                        
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            if (profile.drugSaleLicenseNo.isNotBlank()) Text("DSL (Pharma): ${profile.drugSaleLicenseNo}", fontSize = 9.sp, color = Color(0xFF00695C))
                                            if (profile.healthCommissionNo.isNotBlank()) Text("PMC/Health Reg: ${profile.healthCommissionNo}", fontSize = 9.sp, color = Color(0xFF1565C0))
                                            if (profile.foodSafetyLicenseNo.isNotBlank()) Text("Food Safety: ${profile.foodSafetyLicenseNo}", fontSize = 9.sp, color = Color(0xFFE65100))
                                            if (profile.tradeLicenseNo.isNotBlank()) Text("Trade License: ${profile.tradeLicenseNo}", fontSize = 9.sp, color = Color.DarkGray)
                                        }
                                    }

                                    Spacer(Modifier.height(10.dp))

                                    // BILL TO CLIENT BOX
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7FA)),
                                        border = BorderStroke(0.5.dp, Color.LightGray)
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text("BILL TO / CUSTOMER DETAILS:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
                                            Text("Name: ${selectedClient?.name ?: "Walk-in Customer"}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.Black)
                                            Text("Phone: ${selectedClient?.phone ?: "N/A"} | Address: ${selectedClient?.address ?: "N/A"}", fontSize = 10.sp, color = Color.DarkGray)
                                            Text("Payment Method: ${order.documentType}", fontSize = 10.sp, color = Color.Black)
                                        }
                                    }

                                    Spacer(Modifier.height(12.dp))

                                    // ITEMIZATION TABLE
                                    Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF0D47A1)).padding(6.dp)) {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            Text("Description", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.White, modifier = Modifier.weight(2.5f))
                                            Text("Price", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.White, modifier = Modifier.weight(1f))
                                            Text("Qty", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.White, modifier = Modifier.weight(0.7f))
                                            Text("Amount", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.White, modifier = Modifier.weight(1.2f))
                                        }
                                    }

                                    items.forEachIndexed { idx, item ->
                                        val bg = if (idx % 2 == 0) Color.White else Color(0xFFF9FAFC)
                                        Row(
                                            modifier = Modifier.fillMaxWidth().background(bg).padding(horizontal = 6.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(item.name, fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(2.5f))
                                            Text("${profile.currency} ${item.price}", fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(1f))
                                            Text("${item.quantity}", fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(0.7f))
                                            Text("${profile.currency} ${item.price * item.quantity}", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.Black, modifier = Modifier.weight(1.2f))
                                        }
                                    }

                                    Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 6.dp))

                                    // SUMMARY TOTALS & STAMP
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        Column(modifier = Modifier.weight(1.2f)) {
                                            Box(
                                                modifier = Modifier
                                                    .size(110.dp, 44.dp)
                                                    .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text("Authorized Stamp / Sign", fontSize = 8.sp, color = Color.Gray)
                                            }
                                        }

                                        Column(modifier = Modifier.weight(1.5f)) {
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("Subtotal:", fontSize = 10.sp, color = Color.Black)
                                                Text("${profile.currency} ${order.subtotal}", fontSize = 10.sp, color = Color.Black)
                                            }
                                            if (order.discount > 0) {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text("Discount:", fontSize = 10.sp, color = Color.Black)
                                                    Text("- ${profile.currency} ${order.discount}", fontSize = 10.sp, color = Color.Black)
                                                }
                                            }
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("Tax (Sales/Govt):", fontSize = 10.sp, color = Color.Black)
                                                Text("${profile.currency} ${order.tax}", fontSize = 10.sp, color = Color.Black)
                                            }
                                            Divider(modifier = Modifier.padding(vertical = 4.dp), color = Color.Black)
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("GRAND TOTAL:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0D47A1))
                                                Text("${profile.currency} ${order.total}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0D47A1))
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text("Terms & Conditions: ${profile.invoiceTerms}", fontSize = 8.sp, color = Color.DarkGray)
                                    Text(profile.invoiceFooterNote, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.align(Alignment.CenterHorizontally))
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    // Bottom Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Direct WhatsApp Share Button
                        Button(
                            onClick = {
                                shareReceiptViaWhatsApp(context, order, items, selectedClient, profile)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                            Spacer(Modifier.width(4.dp))
                            Text("WhatsApp", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        // A4 Bluetooth & System Print Button
                        Button(
                            onClick = {
                                val clientName = selectedClient?.name ?: "Cash Customer"
                                val a4Text = BluetoothThermalPrinterHelper.buildA4InvoiceText(
                                    businessName = profile.businessName,
                                    tagline = profile.tagline,
                                    address = profile.address,
                                    phone = profile.phone,
                                    orderId = order.id,
                                    dateStr = order.date,
                                    clientName = clientName,
                                    items = items,
                                    subtotal = order.subtotal,
                                    discount = order.discount,
                                    tax = order.tax,
                                    total = order.total,
                                    paymentMethod = order.documentType,
                                    footerNote = profile.invoiceFooterNote
                                )
                                BluetoothThermalPrinterHelper.printA4ViaSystem(
                                    context = context,
                                    jobName = "A4_Invoice_${order.id}",
                                    documentTitle = "Invoice_${order.id}",
                                    contentText = a4Text
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 12.dp),
                            modifier = Modifier.weight(1.1f)
                        ) {
                            Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                            Spacer(Modifier.width(4.dp))
                            Text("A4 Print", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        // Bluetooth ESC/POS Thermal Print Button
                        Button(
                            onClick = {
                                val printers = BluetoothThermalPrinterHelper.getAvailablePrinters(context)
                                val targetAddr = printers.firstOrNull()?.address ?: BluetoothThermalPrinterHelper.getSavedPrinterAddress(context)
                                val payload = BluetoothThermalPrinterHelper.buildPosReceiptPayload(
                                    businessName = profile.businessName,
                                    tagline = profile.tagline,
                                    address = profile.address,
                                    phone = profile.phone,
                                    orderId = order.id,
                                    dateStr = order.date,
                                    items = items,
                                    subtotal = order.subtotal,
                                    discount = order.discount,
                                    tax = order.tax,
                                    total = order.total,
                                    paymentMethod = order.documentType,
                                    footerNote = profile.invoiceFooterNote
                                )
                                val (success, msg) = BluetoothThermalPrinterHelper.printPayload(context, targetAddr, payload)
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 12.dp),
                            modifier = Modifier.weight(1.2f)
                        ) {
                            Icon(Icons.Default.Bluetooth, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                            Spacer(Modifier.width(4.dp))
                            Text("Thermal Print", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OmniPosInventoryTab"""

new_content = content[:p1] + replacement + content[p2 + len(end_target):]
with open(path, 'w', encoding='utf-8') as f:
    f.write(new_content)
print('Done successfully!')
