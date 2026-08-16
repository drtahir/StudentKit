package com.drtahir.studentkit.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.io.FileOutputStream
import java.io.OutputStream
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

    fun savePrinterAddress(context: Context, address: String, name: String) {
        getPrefs(context).edit()
            .putString(KEY_PRINTER_ADDRESS, address)
            .putString(KEY_PRINTER_NAME, name)
            .apply()
    }

    fun getSavedPaperSize(context: Context): String {
        return getPrefs(context).getString(KEY_PAPER_SIZE, PAPER_A4) ?: PAPER_A4
    }

    fun savePaperSize(context: Context, paperSize: String) {
        getPrefs(context).edit().putString(KEY_PAPER_SIZE, paperSize).apply()
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
     * Builds ESC/POS 58mm (32-character line) thermal receipt payload.
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
        footerNote: String
    ): ByteArray {
        val buffer = java.io.ByteArrayOutputStream()

        fun write(bytes: ByteArray) = buffer.write(bytes)
        fun writeLine(text: String = "") = buffer.write("$text\n".toByteArray(Charsets.ISO_8859_1))

        // Reset
        write(ESC_INIT)

        // Header
        write(ESC_ALIGN_CENTER)
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
     */
    fun printA4ViaSystem(
        context: Context,
        jobName: String,
        documentTitle: String,
        contentText: String
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

                // Standard A4 Page Dimensions: 595 x 842 points
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
                val page = pdfDocument?.startPage(pageInfo)

                if (page != null) {
                    val canvas: Canvas = page.canvas
                    val paint = Paint().apply {
                        color = Color.BLACK
                        textSize = 10f
                        typeface = Typeface.MONOSPACE
                    }

                    var yPos = 40f
                    val xPos = 40f
                    val lines = contentText.split("\n")

                    lines.forEach { line ->
                        if (yPos < 800f) {
                            canvas.drawText(line, xPos, yPos, paint)
                            yPos += 14f
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
        thermalBytes: ByteArray
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
            printA4ViaSystem(context, jobName, documentTitle, a4Text)
        } else {
            // Thermal Receipt 58mm or 80mm
            val targetAddr = if (deviceAddr.isNotBlank()) deviceAddr else getAvailablePrinters(context).firstOrNull()?.address ?: ""
            val (success, msg) = printPayload(context, targetAddr, thermalBytes)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }
}

