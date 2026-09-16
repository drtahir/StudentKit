package com.drtahir.studentkit.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object BluetoothThermalPrinterHelper {

    val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    // Paper Size Constants
    const val PAPER_58MM = "58mm Thermal Receipt (32 Cols)"
    const val PAPER_80MM = "80mm Thermal Receipt (48 Cols)"
    const val PAPER_A4 = "A4 Standard Document (80 Cols)"

    private const val PREFS_NAME = "BluetoothPrinterPrefs"
    private const val KEY_PRINTER_ADDRESS = "printer_address"
    private const val KEY_PRINTER_NAME = "printer_name"
    private const val KEY_PAPER_SIZE = "paper_size"
    private const val LOGO_FILE_NAME = "omni_pos_logo.png"

    // ESC/POS Command Definitions
    val ESC_INIT = byteArrayOf(0x1B.toByte(), 0x40.toByte())
    val ESC_ALIGN_LEFT = byteArrayOf(0x1B.toByte(), 0x61.toByte(), 0x00.toByte())
    val ESC_ALIGN_CENTER = byteArrayOf(0x1B.toByte(), 0x61.toByte(), 0x01.toByte())
    val ESC_ALIGN_RIGHT = byteArrayOf(0x1B.toByte(), 0x61.toByte(), 0x02.toByte())
    val ESC_BOLD_ON = byteArrayOf(0x1B.toByte(), 0x45.toByte(), 0x01.toByte())
    val ESC_BOLD_OFF = byteArrayOf(0x1B.toByte(), 0x45.toByte(), 0x00.toByte())
    val ESC_DOUBLE_SIZE = byteArrayOf(0x1D.toByte(), 0x21.toByte(), 0x11.toByte())
    val ESC_NORMAL_SIZE = byteArrayOf(0x1D.toByte(), 0x21.toByte(), 0x00.toByte())
    val ESC_FEED_AND_CUT = byteArrayOf(0x1D.toByte(), 0x56.toByte(), 0x42.toByte(), 0x05.toByte())
    val ESC_OPEN_DRAWER = byteArrayOf(0x1B.toByte(), 0x70.toByte(), 0x00.toByte(), 0x19.toByte(), 0xFF.toByte())

    data class PrinterDevice(
        val name: String,
        val address: String,
        val isPaired: Boolean = true
    )

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getSavedPrinterAddress(context: Context): String {
        return getPrefs(context).getString(KEY_PRINTER_ADDRESS, "") ?: ""
    }

    fun getSavedPrinterName(context: Context): String {
        return getPrefs(context).getString(KEY_PRINTER_NAME, "No Printer Selected") ?: "No Printer Selected"
    }

    fun savePrinterAddress(context: Context, address: String, name: String = "") {
        val finalName = if (name.isNotBlank()) name else {
            getAvailablePrinters(context).find { it.address == address }?.name ?: "Bluetooth Printer"
        }
        getPrefs(context).edit()
            .putString(KEY_PRINTER_ADDRESS, address)
            .putString(KEY_PRINTER_NAME, finalName)
            .apply()
    }

    fun getSavedPaperSize(context: Context): String {
        return getPrefs(context).getString(KEY_PAPER_SIZE, PAPER_A4) ?: PAPER_A4
    }

    fun savePaperSize(context: Context, paperSize: String) {
        getPrefs(context).edit().putString(KEY_PAPER_SIZE, paperSize).apply()
    }

    /**
     * Permanent logo storage & decoding helpers
     */
    fun saveLogoFromUri(context: Context, sourceUri: Uri): String? {
        return try {
            val file = File(context.filesDir, LOGO_FILE_NAME)
            val inputStream: InputStream? = context.contentResolver.openInputStream(sourceUri)
            if (inputStream != null) {
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                inputStream.close()
                file.absolutePath
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getSavedLogoBitmap(context: Context): Bitmap? {
        return try {
            val file = File(context.filesDir, LOGO_FILE_NAME)
            if (file.exists() && file.length() > 0) {
                BitmapFactory.decodeFile(file.absolutePath)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun deleteSavedLogo(context: Context): Boolean {
        return try {
            val file = File(context.filesDir, LOGO_FILE_NAME)
            if (file.exists()) file.delete() else true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Converts a high-resolution Bitmap into crisp ESC/POS 1-bit monochrome Raster bit-image payload (GS v 0).
     * Automatically scales to printer width (e.g. 384 dots for 58mm, 576 dots for 80mm) with luminance thresholding.
     */
    fun createEscPosRasterImage(
        bitmap: Bitmap,
        targetWidth: Int = 384,
        threshold: Int = 128
    ): ByteArray {
        val buffer = java.io.ByteArrayOutputStream()
        try {
            val width = ((targetWidth / 8) * 8).coerceAtLeast(8) // Must be multiple of 8
            val aspectRatio = bitmap.height.toFloat() / bitmap.width.toFloat()
            val height = (width * aspectRatio).toInt().coerceAtLeast(8)

            val scaled = Bitmap.createScaledBitmap(bitmap, width, height, true)
            val xBytes = width / 8
            val xL = (xBytes % 256).toByte()
            val xH = (xBytes / 256).toByte()
            val yL = (height % 256).toByte()
            val yH = (height / 256).toByte()

            // Header for GS v 0 (Raster bit image)
            buffer.write(byteArrayOf(0x1D, 0x76, 0x30, 0x00, xL, xH, yL, yH))

            for (y in 0 until height) {
                for (xChunk in 0 until xBytes) {
                    var byteVal = 0
                    for (b in 0 until 8) {
                        val px = xChunk * 8 + b
                        val pixel = scaled.getPixel(px, y)
                        val alpha = (pixel shr 24) and 0xFF
                        if (alpha < 60) {
                            // Transparent background -> Treat as white (0)
                        } else {
                            val r = (pixel shr 16) and 0xFF
                            val g = (pixel shr 8) and 0xFF
                            val bl = pixel and 0xFF
                            val lum = (0.299 * r + 0.587 * g + 0.114 * bl).toInt()
                            if (lum < threshold) {
                                // Black pixel (1)
                                byteVal = byteVal or (1 shl (7 - b))
                            }
                        }
                    }
                    buffer.write(byteVal)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer.toByteArray()
    }

    @SuppressLint("MissingPermission")
    fun getAvailablePrinters(context: Context): List<PrinterDevice> {
        val adapter = BluetoothAdapter.getDefaultAdapter() ?: return emptyList()

        if (!adapter.isEnabled) {
            return emptyList()
        }

        val list = mutableListOf<PrinterDevice>()
        try {
            val bondedDevices: Set<BluetoothDevice>? = adapter.bondedDevices
            bondedDevices?.forEach { dev ->
                list.add(PrinterDevice(dev.name ?: "Unknown Printer", dev.address, true))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }

    /**
     * Sends ESC/POS or A4 raw text byte payload over Bluetooth socket.
     */
    @SuppressLint("MissingPermission")
    fun printPayload(context: Context, deviceAddress: String, payload: ByteArray): Pair<Boolean, String> {
        val adapter = BluetoothAdapter.getDefaultAdapter()

        if (adapter == null || !adapter.isEnabled) {
            return Pair(false, "Bluetooth is disabled. Please enable Bluetooth in settings and connect your printer device.")
        }

        val targetAddress = if (deviceAddress.isNotBlank()) deviceAddress else getSavedPrinterAddress(context)

        if (targetAddress.isBlank()) {
            return Pair(false, "No printer selected. Please connect and select a Bluetooth printer in Settings.")
        }

        return try {
            val device = adapter.getRemoteDevice(targetAddress)
            val socket: BluetoothSocket = device.createRfcommSocketToServiceRecord(SPP_UUID)
            adapter.cancelDiscovery()
            socket.connect()

            val os: OutputStream = socket.outputStream
            os.write(payload)
            os.flush()
            os.close()
            socket.close()

            Pair(true, "Document printed successfully over Bluetooth to ${device.name ?: "Printer"}!")
        } catch (e: Exception) {
            Pair(false, "Bluetooth printer error: ${e.message ?: "Connection failed"}. Check power & pairing.")
        }
    }

    /**
     * Builds ESC/POS 58mm (32-character line) thermal receipt payload with optional high-resolution 1-bit raster logo.
     */
    fun buildPosReceiptPayload(
        businessName: String,
        tagline: String,
        address: String,
        phone: String,
        orderId: String,
        dateStr: String,
        items: List<PosOrderItem>,
        subtotal: Double,
        discount: Double,
        tax: Double,
        total: Double,
        paymentMethod: String,
        footerNote: String,
        logoBitmap: Bitmap? = null,
        showLogo: Boolean = true,
        threshold: Int = 128
    ): ByteArray {
        val buffer = java.io.ByteArrayOutputStream()

        fun write(bytes: ByteArray) = buffer.write(bytes)
        fun writeLine(text: String = "") = buffer.write("$text\n".toByteArray(Charsets.ISO_8859_1))

        // Reset
        write(ESC_INIT)

        // Center Align for Header
        write(ESC_ALIGN_CENTER)

        // Optional Thermal Logo Header (384 dots for 58mm / 576 dots for 80mm)
        if (showLogo && logoBitmap != null) {
            val logoBytes = createEscPosRasterImage(logoBitmap, targetWidth = 384, threshold = threshold)
            if (logoBytes.isNotEmpty()) {
                write(logoBytes)
                writeLine()
            }
        }

        // Business Header
        write(ESC_DOUBLE_SIZE)
        write(ESC_BOLD_ON)
        writeLine(businessName.take(16))
        write(ESC_NORMAL_SIZE)
        write(ESC_BOLD_OFF)
        if (tagline.isNotBlank()) writeLine(tagline.take(32))
        writeLine(address.take(32))
        writeLine("Tel: ${phone.take(24)}")
        writeLine("--------------------------------")

        // Order Details
        write(ESC_ALIGN_LEFT)
        writeLine("Invoice #: ${orderId.take(20)}")
        writeLine("Date: $dateStr")
        writeLine("Payment: $paymentMethod")
        writeLine("--------------------------------")

        // Items Header
        write(ESC_BOLD_ON)
        writeLine(String.format("%-14s %3s %12s", "ITEM", "QTY", "PRICE"))
        write(ESC_BOLD_OFF)
        writeLine("--------------------------------")

        // Items
        items.forEach { item ->
            val nameTrunc = if (item.name.length > 14) item.name.substring(0, 14) else item.name
            val line = String.format("%-14s %3d %12.2f", nameTrunc, item.quantity, item.price * item.quantity)
            writeLine(line)
        }

        writeLine("--------------------------------")

        // Totals
        write(ESC_ALIGN_RIGHT)
        writeLine(String.format("Subtotal:  Rs %10.2f", subtotal))
        if (discount > 0) writeLine(String.format("Discount: -Rs %10.2f", discount))
        if (tax > 0) writeLine(String.format("Tax:       Rs %10.2f", tax))
        write(ESC_BOLD_ON)
        write(ESC_DOUBLE_SIZE)
        writeLine(String.format("TOTAL: Rs %.2f", total))
        write(ESC_NORMAL_SIZE)
        write(ESC_BOLD_OFF)

        write(ESC_ALIGN_CENTER)
        writeLine("--------------------------------")
        writeLine(footerNote.take(32))
        writeLine("*** Powered by OmniPOS ***")
        writeLine("\n\n")

        // Cut paper & open cash drawer
        write(ESC_OPEN_DRAWER)
        write(ESC_FEED_AND_CUT)

        return buffer.toByteArray()
    }

    /**
     * Builds full 80-column A4 Invoice Text Document Layout.
     */
    fun buildA4InvoiceText(
        businessName: String,
        tagline: String,
        address: String,
        phone: String,
        orderId: String,
        dateStr: String,
        clientName: String,
        items: List<PosOrderItem>,
        subtotal: Double,
        discount: Double,
        tax: Double,
        total: Double,
        paymentMethod: String,
        footerNote: String
    ): String {
        val sb = StringBuilder()
        val sep = "================================================================================" // 80 chars
        val thinSep = "--------------------------------------------------------------------------------"

        sb.appendLine(sep)
        sb.appendLine(businessName.uppercase().padStart((80 + businessName.length) / 2))
        if (tagline.isNotBlank()) sb.appendLine(tagline.padStart((80 + tagline.length) / 2))
        sb.appendLine(address.padStart((80 + address.length) / 2))
        sb.appendLine("Tel: $phone".padStart((80 + "Tel: $phone".length) / 2))
        sb.appendLine(sep)
        sb.appendLine("                      OFFICIAL A4 SALES TAX INVOICE                      ")
        sb.appendLine(sep)
        sb.appendLine(String.format("Invoice No : %-25s Date     : %s", orderId, dateStr))
        sb.appendLine(String.format("Customer   : %-25s Payment  : %s", clientName, paymentMethod))
        sb.appendLine(thinSep)
        sb.appendLine(String.format("%-5s %-35s %8s %12s %14s", "S.NO", "ITEM DESCRIPTION", "QTY", "RATE (RS)", "TOTAL (RS)"))
        sb.appendLine(thinSep)

        items.forEachIndexed { idx, item ->
            val name = if (item.name.length > 35) item.name.substring(0, 35) else item.name
            sb.appendLine(String.format("%-5d %-35s %8d %12.2f %14.2f", idx + 1, name, item.quantity, item.price, item.price * item.quantity))
        }

        sb.appendLine(thinSep)
        sb.appendLine(String.format("%64s: Rs %12.2f", "Subtotal", subtotal))
        if (discount > 0) sb.appendLine(String.format("%64s:-Rs %12.2f", "Discount", discount))
        if (tax > 0) sb.appendLine(String.format("%64s: Rs %12.2f", "Tax / VAT", tax))
        sb.appendLine(sep)
        sb.appendLine(String.format("%64s: RS %12.2f", "GRAND TOTAL", total))
        sb.appendLine(sep)
        sb.appendLine()
        if (footerNote.isNotBlank()) sb.appendLine("Terms & Conditions: $footerNote")
        sb.appendLine("Status: PAYMENT RECEIVED VIA $paymentMethod")
        sb.appendLine()
        sb.appendLine("Prepared By: Authorized Signature                    Receiver Stamp / Sign")
        sb.appendLine("_________________________________                    _____________________")
        sb.appendLine()
        sb.appendLine("                  Thank you for your business! - Powered by OmniPOS               ")
        sb.appendLine(sep)

        return sb.toString()
    }

    /**
     * Builds ESC/POS 58mm Daily Shift Z-Report payload.
     */
    fun buildZReportPayload(
        cashier: String,
        dateStr: String,
        openingFloat: Double,
        salesRev: Double,
        cashSales: Double,
        cardSales: Double,
        walletSales: Double,
        expectedCash: Double,
        actualCash: Double,
        variance: Double
    ): ByteArray {
        val buffer = java.io.ByteArrayOutputStream()
        fun write(bytes: ByteArray) = buffer.write(bytes)
        fun writeLine(text: String = "") = buffer.write("$text\n".toByteArray(Charsets.ISO_8859_1))

        write(ESC_INIT)
        write(ESC_ALIGN_CENTER)
        write(ESC_BOLD_ON)
        write(ESC_DOUBLE_SIZE)
        writeLine("SHIFT Z-REPORT")
        write(ESC_NORMAL_SIZE)
        write(ESC_BOLD_OFF)
        writeLine("Cashier: $cashier")
        writeLine("Date: $dateStr")
        writeLine("--------------------------------")

        write(ESC_ALIGN_LEFT)
        writeLine(String.format("Opening Float:   Rs %9.2f", openingFloat))
        writeLine(String.format("Total Sales Rev: Rs %9.2f", salesRev))
        writeLine(String.format("  - Cash Sales:  Rs %9.2f", cashSales))
        writeLine(String.format("  - Card Sales:  Rs %9.2f", cardSales))
        writeLine(String.format("  - Wallet QR:   Rs %9.2f", walletSales))
        writeLine("--------------------------------")
        writeLine(String.format("Expected Cash:   Rs %9.2f", expectedCash))
        writeLine(String.format("Actual Cash:     Rs %9.2f", actualCash))
        write(ESC_BOLD_ON)
        writeLine(String.format("Drawer Variance: Rs %9.2f", variance))
        write(ESC_BOLD_OFF)
        writeLine("--------------------------------")
        write(ESC_ALIGN_CENTER)
        writeLine("Manager Sign: __________________")
        writeLine("\n\n")

        write(ESC_FEED_AND_CUT)

        return buffer.toByteArray()
    }

    /**
     * Builds A4 Shift Z-Report text payload.
     */
    fun buildA4ZReportText(
        cashier: String,
        dateStr: String,
        openingFloat: Double,
        salesRev: Double,
        cashSales: Double,
        cardSales: Double,
        walletSales: Double,
        expectedCash: Double,
        actualCash: Double,
        variance: Double
    ): String {
        val sep = "================================================================================"
        val thinSep = "--------------------------------------------------------------------------------"
        val sb = StringBuilder()

        sb.appendLine(sep)
        sb.appendLine("                    OFFICIAL SHIFT REGISTER Z-REPORT (A4)                       ")
        sb.appendLine(sep)
        sb.appendLine("Cashier Name : $cashier")
        sb.appendLine("Shift Date   : $dateStr")
        sb.appendLine("Terminal ID  : TERM-01 (OmniPOS Enterprise)")
        sb.appendLine(thinSep)
        sb.appendLine(String.format("Opening Register Float : Rs %12.2f", openingFloat))
        sb.appendLine(String.format("Total Sales Revenue    : Rs %12.2f", salesRev))
        sb.appendLine(String.format("  - Cash Tendered      : Rs %12.2f", cashSales))
        sb.appendLine(String.format("  - Card / Terminal    : Rs %12.2f", cardSales))
        sb.appendLine(String.format("  - Mobile Wallet QR   : Rs %12.2f", walletSales))
        sb.appendLine(thinSep)
        sb.appendLine(String.format("Expected Cash in Drawer: Rs %12.2f", expectedCash))
        sb.appendLine(String.format("Physical Cash Counted  : Rs %12.2f", actualCash))
        sb.appendLine(String.format("Drawer Variance        : Rs %12.2f (%s)", variance, if (variance == 0.0) "PERFECT BALANCED" else if (variance > 0) "SURPLUS" else "DEFICIT"))
        sb.appendLine(sep)
        sb.appendLine()
        sb.appendLine("Shift Audit Verification:")
        sb.appendLine("Cashier Sign: __________________         Manager Approval: __________________")
        sb.appendLine(sep)

        return sb.toString()
    }

    /**
     * Triggers Android System PrintManager spooling for A4 page layout (works with Bluetooth, Wi-Fi, and USB A4 printers).
     * Renders company logo in best quality directly onto the high-resolution vector PDF canvas.
     */
    fun printA4ViaSystem(
        context: Context,
        jobName: String,
        documentTitle: String,
        contentText: String,
        logoBitmap: Bitmap? = getSavedLogoBitmap(context),
        showLogo: Boolean = true
    ) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
        if (printManager == null) {
            Toast.makeText(context, "System printing service not available.", Toast.LENGTH_SHORT).show()
            return
        }

        val printAdapter = object : PrintDocumentAdapter() {
            private var pdfDocument: PdfDocument? = null

            override fun onLayout(
                oldAttributes: PrintAttributes?,
                newAttributes: PrintAttributes?,
                cancellationSignal: CancellationSignal?,
                callback: LayoutResultCallback?,
                extras: Bundle?
            ) {
                if (cancellationSignal?.isCanceled == true) {
                    callback?.onLayoutCancelled()
                    return
                }

                val info = PrintDocumentInfo.Builder("$documentTitle.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(1)
                    .build()

                callback?.onLayoutFinished(info, true)
            }

            override fun onWrite(
                pages: Array<out PageRange>?,
                destination: ParcelFileDescriptor?,
                cancellationSignal: CancellationSignal?,
                callback: WriteResultCallback?
            ) {
                pdfDocument = PdfDocument()

                // Standard A4 Page Dimensions: 595 x 842 points (300 DPI target)
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
                val page = pdfDocument?.startPage(pageInfo)

                if (page != null) {
                    val canvas: Canvas = page.canvas
                    val textPaint = Paint().apply {
                        color = Color.BLACK
                        textSize = 9.5f
                        typeface = Typeface.MONOSPACE
                        isAntiAlias = true
                    }

                    var yPos = 35f
                    val xPos = 35f

                    // Draw Logo on A4 Header in pristine high quality
                    if (showLogo && logoBitmap != null) {
                        try {
                            val bitmapPaint = Paint().apply {
                                isAntiAlias = true
                                isFilterBitmap = true
                                isDither = true
                            }
                            val maxLogoW = 90f
                            val maxLogoH = 50f
                            val aspect = logoBitmap.width.toFloat() / logoBitmap.height.toFloat()
                            val drawW = if (aspect >= 1f) maxLogoW else maxLogoH * aspect
                            val drawH = if (aspect >= 1f) maxLogoW / aspect else maxLogoH

                            // Center the logo above the document header
                            val logoLeft = (595f - drawW) / 2f
                            val destRect = RectF(logoLeft, yPos, logoLeft + drawW, yPos + drawH)
                            canvas.drawBitmap(logoBitmap, null, destRect, bitmapPaint)
                            yPos += drawH + 10f
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    val lines = contentText.split("\n")
                    lines.forEach { line ->
                        if (yPos < 815f) {
                            canvas.drawText(line, xPos, yPos, textPaint)
                            yPos += 13.5f
                        }
                    }

                    pdfDocument?.finishPage(page)
                }

                try {
                    destination?.fileDescriptor?.let { fd ->
                        FileOutputStream(fd).use { out ->
                            pdfDocument?.writeTo(out)
                        }
                    }
                    callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback?.onWriteFailed(e.message)
                } finally {
                    pdfDocument?.close()
                }
            }
        }

        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setResolution(PrintAttributes.Resolution("A4_RES", "A4 Printing", 300, 300))
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
            .build()

        printManager.print(jobName, printAdapter, printAttributes)
    }

    /**
     * Unified print helper that checks saved paper size preference and dispatches to Bluetooth or System A4 Printer.
     */
    fun printDocument(
        context: Context,
        jobName: String,
        documentTitle: String,
        a4Text: String,
        thermalBytes: ByteArray,
        logoBitmap: Bitmap? = getSavedLogoBitmap(context),
        showLogo: Boolean = true
    ) {
        val paperSize = getSavedPaperSize(context)
        val deviceAddr = getSavedPrinterAddress(context)

        if (paperSize == PAPER_A4) {
            // First attempt direct Bluetooth transmit if printer address exists, and also open System A4 Print Spooler
            if (deviceAddr.isNotBlank()) {
                val (success, msg) = printPayload(context, deviceAddr, a4Text.toByteArray(Charsets.ISO_8859_1))
                if (!success) {
                    Toast.makeText(context, "Bluetooth A4 Direct: $msg. Opening System A4 Printer...", Toast.LENGTH_SHORT).show()
                }
            }
            printA4ViaSystem(context, jobName, documentTitle, a4Text, logoBitmap, showLogo)
        } else {
            // Thermal Receipt 58mm or 80mm
            val targetAddr = if (deviceAddr.isNotBlank()) deviceAddr else getAvailablePrinters(context).firstOrNull()?.address ?: ""
            val (success, msg) = printPayload(context, targetAddr, thermalBytes)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Builds authentic ASCII text for 58mm / 80mm thermal receipts.
     */
    fun buildThermalReceiptText(
        businessName: String,
        tagline: String,
        address: String,
        phone: String,
        orderId: String,
        dateStr: String,
        clientName: String,
        items: List<PosOrderItem>,
        subtotal: Double,
        discount: Double,
        tax: Double,
        total: Double,
        paymentMethod: String,
        footerNote: String,
        is80mm: Boolean = false
    ): String {
        val lineWidth = if (is80mm) 44 else 32
        val sep = "-".repeat(lineWidth)
        val doubleSep = "=".repeat(lineWidth)
        val sb = StringBuilder()

        fun center(text: String): String {
            if (text.length >= lineWidth) return text.take(lineWidth)
            val pad = (lineWidth - text.length) / 2
            return " ".repeat(pad) + text
        }

        sb.appendLine(doubleSep)
        sb.appendLine(center(businessName.uppercase()))
        if (tagline.isNotBlank()) sb.appendLine(center(tagline))
        if (address.isNotBlank()) sb.appendLine(center(address))
        if (phone.isNotBlank()) sb.appendLine(center("Tel: $phone"))
        sb.appendLine(doubleSep)

        sb.appendLine("Receipt #: $orderId")
        sb.appendLine("Date: $dateStr")
        if (clientName.isNotBlank() && clientName != "Walking Customer / Cash") {
            sb.appendLine("Customer : ${clientName.take(lineWidth - 11)}")
        }
        sb.appendLine("Payment  : $paymentMethod")
        sb.appendLine(sep)

        if (is80mm) {
            sb.appendLine(String.format("%-18s %4s %8s %10s", "ITEM", "QTY", "RATE", "TOTAL"))
            sb.appendLine(sep)
            items.forEach { item ->
                val name = if (item.name.length > 18) item.name.substring(0, 18) else item.name
                sb.appendLine(String.format("%-18s %4d %8.2f %10.2f", name, item.quantity, item.price, item.price * item.quantity))
                if (item.name.length > 18) {
                    sb.appendLine("  ${item.name.substring(18).take(24)}")
                }
            }
        } else {
            sb.appendLine(String.format("%-15s %3s %12s", "ITEM", "QTY", "AMOUNT"))
            sb.appendLine(sep)
            items.forEach { item ->
                val name = if (item.name.length > 15) item.name.substring(0, 15) else item.name
                sb.appendLine(String.format("%-15s %3d %12.2f", name, item.quantity, item.price * item.quantity))
                if (item.name.length > 15) {
                    sb.appendLine("  ${item.name.substring(15).take(15)}")
                }
            }
        }

        sb.appendLine(sep)
        val prefixPad = if (is80mm) 28 else 16
        sb.appendLine(String.format("%${prefixPad}s: Rs %9.2f", "Subtotal", subtotal))
        if (discount > 0) sb.appendLine(String.format("%${prefixPad}s:-Rs %9.2f", "Discount", discount))
        if (tax > 0) sb.appendLine(String.format("%${prefixPad}s: Rs %9.2f", "Tax/GST", tax))
        sb.appendLine(doubleSep)
        sb.appendLine(center(String.format("*** TOTAL: Rs %.2f ***", total)))
        sb.appendLine(doubleSep)

        sb.appendLine()
        sb.appendLine(center("||| | ||||| | ||| |||| | |||"))
        sb.appendLine(center("*$orderId*"))
        sb.appendLine()
        val note = if (footerNote.isNotBlank()) footerNote else "Thank you for your visit!"
        sb.appendLine(center(note))
        sb.appendLine(center("*** Powered by OmniPOS ***"))

        return sb.toString()
    }

    /**
     * Renders a high-resolution professional A4 Enterprise Tax Invoice Bitmap for gallery saving and sharing.
     */
    fun renderA4InvoiceBitmap(
        businessName: String,
        tagline: String,
        address: String,
        phone: String,
        ntn: String,
        orderId: String,
        dateStr: String,
        clientName: String,
        clientPhone: String = "",
        clientAddress: String = "",
        items: List<PosOrderItem>,
        subtotal: Double,
        discount: Double,
        tax: Double,
        total: Double,
        paymentMethod: String,
        footerNote: String,
        logoBitmap: Bitmap? = null,
        showLogo: Boolean = true
    ): Bitmap {
        val width = 1240
        val height = 1754
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        val paint = Paint().apply { isAntiAlias = true }

        // Top teal header banner
        paint.color = Color.rgb(15, 118, 110)
        canvas.drawRect(0f, 0f, width.toFloat(), 16f, paint)

        val left = 60f
        val right = width - 60f
        val y = 70f

        var textLeft = left
        if (showLogo && logoBitmap != null) {
            try {
                val maxLogoW = 140f
                val maxLogoH = 85f
                val aspect = logoBitmap.width.toFloat() / logoBitmap.height.toFloat()
                val drawW = if (aspect >= 1f) maxLogoW else maxLogoH * aspect
                val drawH = if (aspect >= 1f) maxLogoW / aspect else maxLogoH
                val destRect = RectF(left, y - 10f, left + drawW, y - 10f + drawH)
                canvas.drawBitmap(logoBitmap, null, destRect, Paint().apply { isAntiAlias = true; isFilterBitmap = true })
                textLeft = left + drawW + 20f
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Business details
        paint.color = Color.rgb(15, 23, 42)
        paint.textSize = 28f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText(businessName.ifBlank { "OmniPOS Enterprise" }, textLeft, y + 16f, paint)

        paint.color = Color.rgb(71, 85, 105)
        paint.textSize = 14f
        paint.typeface = Typeface.DEFAULT
        var subY = y + 38f
        if (tagline.isNotBlank()) {
            canvas.drawText(tagline, textLeft, subY, paint)
            subY += 20f
        }
        canvas.drawText(address, textLeft, subY, paint)
        subY += 20f
        val contactLine = "Tel: $phone" + if (ntn.isNotBlank()) "  |  NTN/GST: $ntn" else ""
        canvas.drawText(contactLine, textLeft, subY, paint)

        // Right Header: TAX INVOICE
        paint.textAlign = Paint.Align.RIGHT
        paint.color = Color.rgb(15, 118, 110)
        paint.textSize = 34f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("TAX INVOICE", right, y + 20f, paint)

        paint.textSize = 15f
        paint.typeface = Typeface.DEFAULT
        paint.color = Color.rgb(51, 65, 85)
        canvas.drawText("Invoice #: $orderId", right, y + 48f, paint)
        canvas.drawText("Date: $dateStr", right, y + 70f, paint)
        canvas.drawText("Status: PAID ($paymentMethod)", right, y + 92f, paint)
        paint.textAlign = Paint.Align.LEFT

        var curY = 195f

        // Divider
        paint.color = Color.rgb(226, 232, 240)
        paint.strokeWidth = 2f
        canvas.drawLine(left, curY, right, curY, paint)
        curY += 20f

        // Billed To & Order Meta Cards
        val cardH = 95f
        paint.color = Color.rgb(248, 250, 252)
        paint.style = Paint.Style.FILL
        val custRect = RectF(left, curY, left + 540f, curY + cardH)
        canvas.drawRoundRect(custRect, 10f, 10f, paint)
        paint.color = Color.rgb(203, 213, 225)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.5f
        canvas.drawRoundRect(custRect, 10f, 10f, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.rgb(100, 116, 139)
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("BILLED TO / CUSTOMER", left + 18f, curY + 24f, paint)

        paint.color = Color.rgb(15, 23, 42)
        paint.textSize = 17f
        canvas.drawText(clientName.ifBlank { "Walking Customer / Cash" }, left + 18f, curY + 48f, paint)
        if (clientPhone.isNotBlank() || clientAddress.isNotBlank()) {
            paint.color = Color.rgb(71, 85, 105)
            paint.textSize = 13f
            val cDetail = listOf(clientPhone, clientAddress).filter { it.isNotBlank() }.joinToString(" • ")
            canvas.drawText(cDetail, left + 18f, curY + 70f, paint)
        }

        // Right card: Payment & Dispatch
        val metaRect = RectF(right - 540f, curY, right, curY + cardH)
        paint.color = Color.rgb(248, 250, 252)
        canvas.drawRoundRect(metaRect, 10f, 10f, paint)
        paint.color = Color.rgb(203, 213, 225)
        paint.style = Paint.Style.STROKE
        canvas.drawRoundRect(metaRect, 10f, 10f, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.rgb(100, 116, 139)
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("PAYMENT & ISSUANCE DETAILS", right - 522f, curY + 24f, paint)

        paint.color = Color.rgb(15, 23, 42)
        paint.textSize = 14f
        paint.typeface = Typeface.DEFAULT
        canvas.drawText("Payment Mode: $paymentMethod", right - 522f, curY + 48f, paint)
        canvas.drawText("Issued Via  : OmniPOS Computerized Register", right - 522f, curY + 70f, paint)

        curY += cardH + 25f

        // Items Table Header
        val tblH = 38f
        paint.color = Color.rgb(15, 23, 42)
        paint.style = Paint.Style.FILL
        val tblHeadRect = RectF(left, curY, right, curY + tblH)
        canvas.drawRoundRect(tblHeadRect, 6f, 6f, paint)

        paint.color = Color.WHITE
        paint.textSize = 14f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("S#", left + 16f, curY + 24f, paint)
        canvas.drawText("ITEM DESCRIPTION", left + 75f, curY + 24f, paint)
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("QTY", left + 670f, curY + 24f, paint)
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("UNIT RATE (RS)", left + 890f, curY + 24f, paint)
        canvas.drawText("TOTAL (RS)", right - 20f, curY + 24f, paint)
        paint.textAlign = Paint.Align.LEFT

        curY += tblH

        // Table Rows
        val rowH = 36f
        items.forEachIndexed { idx, item ->
            val isEven = idx % 2 == 0
            if (isEven) {
                paint.color = Color.rgb(248, 250, 252)
                paint.style = Paint.Style.FILL
                canvas.drawRect(left, curY, right, curY + rowH, paint)
            }
            paint.color = Color.rgb(226, 232, 240)
            paint.strokeWidth = 1f
            canvas.drawLine(left, curY + rowH, right, curY + rowH, paint)

            paint.color = Color.rgb(30, 41, 59)
            paint.textSize = 14f
            paint.typeface = Typeface.DEFAULT
            canvas.drawText("${idx + 1}", left + 16f, curY + 23f, paint)
            val itemName = if (item.name.length > 50) item.name.substring(0, 48) + "…" else item.name
            canvas.drawText(itemName, left + 75f, curY + 23f, paint)

            paint.textAlign = Paint.Align.CENTER
            canvas.drawText("${item.quantity}", left + 670f, curY + 23f, paint)

            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(String.format("%.2f", item.price), left + 890f, curY + 23f, paint)
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas.drawText(String.format("%.2f", item.price * item.quantity), right - 20f, curY + 23f, paint)
            paint.textAlign = Paint.Align.LEFT

            curY += rowH
        }

        curY += 20f

        // Financial Totals Summary on the right
        val sumW = 440f
        val sumLeft = right - sumW
        val sumH = 140f
        paint.color = Color.rgb(248, 250, 252)
        paint.style = Paint.Style.FILL
        val sumRect = RectF(sumLeft, curY, right, curY + sumH)
        canvas.drawRoundRect(sumRect, 8f, 8f, paint)
        paint.color = Color.rgb(203, 213, 225)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.5f
        canvas.drawRoundRect(sumRect, 8f, 8f, paint)

        paint.style = Paint.Style.FILL
        var totalY = curY + 28f
        fun drawSumLine(label: String, value: Double, isBold: Boolean = false, isAccent: Boolean = false) {
            paint.color = if (isAccent) Color.rgb(15, 118, 110) else Color.rgb(71, 85, 105)
            paint.textSize = if (isBold) 18f else 14f
            paint.typeface = if (isBold) Typeface.create(Typeface.DEFAULT, Typeface.BOLD) else Typeface.DEFAULT
            canvas.drawText(label, sumLeft + 20f, totalY, paint)
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(String.format("Rs %.2f", value), right - 20f, totalY, paint)
            paint.textAlign = Paint.Align.LEFT
            totalY += 26f
        }

        drawSumLine("Subtotal", subtotal)
        if (discount > 0) drawSumLine("Discount Applied (-)", discount)
        if (tax > 0) drawSumLine("Sales Tax / VAT (+)", tax)
        paint.color = Color.rgb(203, 213, 225)
        paint.strokeWidth = 1.5f
        canvas.drawLine(sumLeft + 15f, totalY - 14f, right - 15f, totalY - 14f, paint)
        drawSumLine("GRAND TOTAL", total, isBold = true, isAccent = true)

        // Left Side: Notes & Terms
        paint.color = Color.rgb(71, 85, 105)
        paint.textSize = 13f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("TERMS & CONDITIONS", left, curY + 25f, paint)
        paint.typeface = Typeface.DEFAULT
        paint.textSize = 12f
        val termNote = if (footerNote.isNotBlank()) footerNote else "Official computerized invoice. Goods received in good order."
        canvas.drawText(termNote, left, curY + 45f, paint)
        canvas.drawText("Status: Payment received via $paymentMethod.", left, curY + 65f, paint)

        // Bottom Signatures & Stamp
        val footY = height - 120f
        paint.color = Color.rgb(203, 213, 225)
        paint.strokeWidth = 1.5f
        canvas.drawLine(left, footY, left + 300f, footY, paint)
        canvas.drawLine(right - 300f, footY, right, footY, paint)

        paint.color = Color.rgb(100, 116, 139)
        paint.textSize = 12f
        paint.typeface = Typeface.DEFAULT
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("Authorized Signatory / Stamp", left + 150f, footY + 20f, paint)
        canvas.drawText("Customer Stamp / Receiver Sign", right - 150f, footY + 20f, paint)

        // Bottom Footer Banner
        paint.color = Color.rgb(100, 116, 139)
        paint.textSize = 11f
        canvas.drawText("Computerized A4 Tax Invoice • Generated via OmniPOS Enterprise Suite • All Rights Reserved", width / 2f, height - 30f, paint)

        return bitmap
    }

    /**
     * Renders an authentic POS thermal roll receipt Bitmap for gallery saving and sharing.
     */
    fun renderThermalReceiptBitmap(
        businessName: String,
        tagline: String,
        address: String,
        phone: String,
        orderId: String,
        dateStr: String,
        clientName: String,
        items: List<PosOrderItem>,
        subtotal: Double,
        discount: Double,
        tax: Double,
        total: Double,
        paymentMethod: String,
        footerNote: String,
        logoBitmap: Bitmap? = null,
        showLogo: Boolean = true,
        is80mm: Boolean = false
    ): Bitmap {
        val width = if (is80mm) 576 else 420
        val rowH = 34f
        val baseH = 480f + (items.size * rowH) + 260f
        val height = baseH.toInt().coerceAtLeast(700)

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        val paint = Paint().apply { isAntiAlias = true }
        val left = 24f
        val right = width - 24f
        var y = 35f

        // Centered logo
        if (showLogo && logoBitmap != null) {
            try {
                val maxW = (width * 0.5f)
                val maxH = 80f
                val aspect = logoBitmap.width.toFloat() / logoBitmap.height.toFloat()
                val drawW = if (aspect >= 1f) maxW else maxH * aspect
                val drawH = if (aspect >= 1f) maxW / aspect else maxH
                val logoLeft = (width - drawW) / 2f
                val destRect = RectF(logoLeft, y, logoLeft + drawW, y + drawH)
                canvas.drawBitmap(logoBitmap, null, destRect, Paint().apply { isAntiAlias = true; isFilterBitmap = true })
                y += drawH + 15f
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Business Header (Center)
        paint.color = Color.BLACK
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = if (is80mm) 24f else 20f
        paint.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
        canvas.drawText(businessName.uppercase().ifBlank { "OMNIPOS ENTERPRISE" }, width / 2f, y, paint)
        y += 24f

        paint.textSize = 13f
        paint.typeface = Typeface.MONOSPACE
        if (tagline.isNotBlank()) {
            canvas.drawText(tagline, width / 2f, y, paint)
            y += 18f
        }
        if (address.isNotBlank()) {
            canvas.drawText(address, width / 2f, y, paint)
            y += 18f
        }
        if (phone.isNotBlank()) {
            canvas.drawText("Tel: $phone", width / 2f, y, paint)
            y += 18f
        }

        // Dotted line
        paint.textAlign = Paint.Align.LEFT
        fun drawDottedLine(currentY: Float) {
            paint.color = Color.BLACK
            paint.strokeWidth = 1.5f
            var x = left
            while (x < right) {
                canvas.drawLine(x, currentY, (x + 6f).coerceAtMost(right), currentY, paint)
                x += 10f
            }
        }

        y += 6f
        drawDottedLine(y)
        y += 22f

        // Receipt Meta
        paint.textSize = 13f
        paint.typeface = Typeface.MONOSPACE
        canvas.drawText("Receipt #: $orderId", left, y, paint)
        y += 18f
        canvas.drawText("Date     : $dateStr", left, y, paint)
        y += 18f
        if (clientName.isNotBlank() && clientName != "Walking Customer / Cash") {
            canvas.drawText("Customer : $clientName", left, y, paint)
            y += 18f
        }
        canvas.drawText("Payment  : $paymentMethod", left, y, paint)
        y += 18f

        drawDottedLine(y)
        y += 22f

        // Column Headers
        paint.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
        paint.textSize = 13f
        canvas.drawText("ITEM", left, y, paint)
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("QTY", left + (width * 0.55f), y, paint)
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("AMOUNT", right, y, paint)
        paint.textAlign = Paint.Align.LEFT
        y += 12f
        drawDottedLine(y)
        y += 20f

        // Items
        paint.typeface = Typeface.MONOSPACE
        paint.textSize = 13f
        items.forEach { item ->
            val maxChars = if (is80mm) 20 else 14
            val name = if (item.name.length > maxChars) item.name.substring(0, maxChars) else item.name
            canvas.drawText(name, left, y, paint)

            paint.textAlign = Paint.Align.CENTER
            canvas.drawText("${item.quantity}", left + (width * 0.55f), y, paint)

            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(String.format("%.2f", item.price * item.quantity), right, y, paint)
            paint.textAlign = Paint.Align.LEFT
            y += 22f
        }

        drawDottedLine(y)
        y += 22f

        // Totals
        fun drawTotalRow(label: String, amount: Double, isGrandTotal: Boolean = false) {
            paint.textAlign = Paint.Align.LEFT
            paint.typeface = if (isGrandTotal) Typeface.create(Typeface.MONOSPACE, Typeface.BOLD) else Typeface.MONOSPACE
            paint.textSize = if (isGrandTotal) 18f else 13f
            canvas.drawText(label, left + 30f, y, paint)

            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(String.format("Rs %.2f", amount), right, y, paint)
            paint.textAlign = Paint.Align.LEFT
            y += if (isGrandTotal) 26f else 20f
        }

        drawTotalRow("Subtotal:", subtotal)
        if (discount > 0) drawTotalRow("Discount:", -discount)
        if (tax > 0) drawTotalRow("Tax / GST:", tax)

        y += 4f
        drawDottedLine(y)
        y += 22f

        drawTotalRow("TOTAL:", total, isGrandTotal = true)

        drawDottedLine(y)
        y += 28f

        // Barcode rendering
        val barH = 35f
        val barW = 2.5f
        var barX = (width - 240f) / 2f
        val barPattern = booleanArrayOf(
            true, false, true, true, false, true, false, false, true, true, true, false,
            true, false, true, false, true, true, false, true, false, false, true, false,
            true, true, true, false, true, false, true, true, false, false, true, false
        )
        paint.color = Color.BLACK
        barPattern.forEach { isBlack ->
            if (isBlack) {
                canvas.drawRect(barX, y, barX + barW, y + barH, paint)
            }
            barX += barW + 1.5f
        }
        y += barH + 14f

        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 12f
        paint.typeface = Typeface.MONOSPACE
        canvas.drawText("*$orderId*", width / 2f, y, paint)
        y += 24f

        val note = if (footerNote.isNotBlank()) footerNote else "Thank you for your visit!"
        canvas.drawText(note, width / 2f, y, paint)
        y += 18f
        canvas.drawText("*** Powered by OmniPOS ***", width / 2f, y, paint)

        return bitmap
    }

    /**
     * Saves invoice/receipt bitmap to Android Gallery (Pictures/OmniPOS_Invoices) and registers it with MediaStore.
     */
    fun saveBillBitmapToGallery(context: Context, bitmap: Bitmap, title: String): Uri? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val cleanTitle = if (title.isBlank()) "Invoice_$timeStamp" else title.replace(" ", "_")
        val fileName = "${cleanTitle}_$timeStamp.jpg"

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/OmniPOS_Invoices")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val resolver = context.contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        if (imageUri != null) {
            try {
                resolver.openOutputStream(imageUri)?.use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 96, out)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(imageUri, values, null, null)
                }
                return imageUri
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Fallback: direct public pictures folder
        return try {
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val posDir = File(picturesDir, "OmniPOS_Invoices").apply { mkdirs() }
            val file = File(posDir, fileName)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 96, out)
            }
            MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), arrayOf("image/jpeg"), null)
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Shares invoice/receipt directly via WhatsApp (or WhatsApp Business / chooser fallback) with image and summary.
     */
    fun shareBillViaWhatsApp(
        context: Context,
        bitmap: Bitmap,
        orderId: String,
        summaryText: String
    ) {
        try {
            val cacheDir = File(context.cacheDir, "shared_invoices").apply { mkdirs() }
            val file = File(cacheDir, "OmniPOS_Bill_${orderId}.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }
            val uri = FileProvider.getUriForFile(context, "com.drtahir.studentkit.fileprovider", file)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, summaryText)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            intent.setPackage("com.whatsapp")
            try {
                context.startActivity(intent)
            } catch (e1: Exception) {
                try {
                    intent.setPackage("com.whatsapp.w4b")
                    context.startActivity(intent)
                } catch (e2: Exception) {
                    intent.setPackage(null)
                    context.startActivity(Intent.createChooser(intent, "Share Bill via WhatsApp"))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Could not share bill: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * General share chooser for the generated bill image and summary text.
     */
    fun shareBillGeneral(
        context: Context,
        bitmap: Bitmap,
        orderId: String,
        summaryText: String
    ) {
        try {
            val cacheDir = File(context.cacheDir, "shared_invoices").apply { mkdirs() }
            val file = File(cacheDir, "OmniPOS_Bill_${orderId}.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }
            val uri = FileProvider.getUriForFile(context, "com.drtahir.studentkit.fileprovider", file)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, summaryText)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Share Bill"))
        } catch (e: Exception) {
            e.printStackTrace()
            val textIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, summaryText)
            }
            context.startActivity(Intent.createChooser(textIntent, "Share Bill"))
        }
    }
}

