package com.drtahir.studentkit.data

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast

/**
 * Data model representing parsed Wi-Fi credentials from a scanned QR Code.
 */
data class ScannedWifiConfig(
    val ssid: String,
    val password: String = "",
    val securityType: String = "WPA",
    val isHidden: Boolean = false
) {
    val isSecured: Boolean
        get() = !securityType.equals("nopass", ignoreCase = true) &&
                !securityType.equals("none", ignoreCase = true) &&
                password.isNotEmpty()

    val displaySecurity: String
        get() = when {
            securityType.equals("nopass", ignoreCase = true) || securityType.equals("none", ignoreCase = true) -> "Open (No Password)"
            securityType.equals("WEP", ignoreCase = true) -> "WEP Security"
            securityType.equals("WPA3", ignoreCase = true) || securityType.equals("SAE", ignoreCase = true) -> "WPA3 Personal"
            else -> "WPA / WPA2 PSK"
        }
}

/**
 * Helper object providing robust Wi-Fi QR parsing and multi-strategy Android Wi-Fi network connection.
 */
object WifiConnectHelper {

    private var activeNetworkCallback: ConnectivityManager.NetworkCallback? = null

    /**
     * Parses standard and custom Wi-Fi QR code strings (e.g. "WIFI:S:MyNetwork;T:WPA;P:secret123;;")
     */
    fun parseWifiQr(raw: String): ScannedWifiConfig? {
        val trimmed = raw.trim()
        if (!trimmed.startsWith("WIFI:", ignoreCase = true) && !trimmed.startsWith("wifi:", ignoreCase = true)) {
            return null
        }

        val content = trimmed.substring(5)
        var ssid = ""
        var password = ""
        var securityType = "WPA"
        var isHidden = false

        // Parse key-value tokens respecting escaped characters (\;, \:, \\)
        val tokens = mutableListOf<String>()
        val sb = StringBuilder()
        var isEscaping = false

        for (c in content) {
            if (isEscaping) {
                sb.append(c)
                isEscaping = false
            } else if (c == '\\') {
                isEscaping = true
            } else if (c == ';') {
                tokens.add(sb.toString())
                sb.clear()
            } else {
                sb.append(c)
            }
        }
        if (sb.isNotEmpty()) {
            tokens.add(sb.toString())
        }

        for (token in tokens) {
            val t = token.trim()
            when {
                t.startsWith("S:", ignoreCase = true) -> ssid = t.substring(2)
                t.startsWith("P:", ignoreCase = true) -> password = t.substring(2)
                t.startsWith("T:", ignoreCase = true) -> securityType = t.substring(2)
                t.startsWith("H:", ignoreCase = true) -> isHidden = t.substring(2).equals("true", ignoreCase = true)
            }
        }

        // Strip surrounding quotes if present
        if (ssid.startsWith("\"") && ssid.endsWith("\"") && ssid.length >= 2) {
            ssid = ssid.substring(1, ssid.length - 1)
        }
        if (password.startsWith("\"") && password.endsWith("\"") && password.length >= 2) {
            password = password.substring(1, password.length - 1)
        }

        if (ssid.isBlank()) return null

        return ScannedWifiConfig(
            ssid = ssid,
            password = password,
            securityType = if (securityType.isBlank()) "WPA" else securityType,
            isHidden = isHidden
        )
    }

    /**
     * Connects to the scanned Wi-Fi network using Android's network connection APIs.
     * Automatically copies password to clipboard, invokes WifiNetworkSpecifier/Suggestions,
     * triggers system connection dialogs, and falls back gracefully.
     */
    fun connectToWifi(
        context: Context,
        wifiConfig: ScannedWifiConfig,
        onStatus: (String) -> Unit = {}
    ) {
        val appContext = context.applicationContext

        // 1. Copy password to clipboard for seamless user convenience
        if (wifiConfig.password.isNotEmpty()) {
            try {
                val clipboard = appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Wi-Fi Password", wifiConfig.password)
                clipboard.setPrimaryClip(clip)
            } catch (e: Exception) {
                // Ignore clipboard exception
            }
        }

        var connectionTriggered = false

        // 2. Android 10+ (API 29+): Use WifiNetworkSpecifier & WifiNetworkSuggestion
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                if (connectivityManager != null) {
                    // Release previous callback if active
                    activeNetworkCallback?.let { oldCb ->
                        try { connectivityManager.unregisterNetworkCallback(oldCb) } catch (_: Exception) {}
                    }

                    val specifierBuilder = WifiNetworkSpecifier.Builder()
                        .setSsid(wifiConfig.ssid)

                    if (wifiConfig.isSecured && wifiConfig.password.isNotEmpty()) {
                        if (wifiConfig.securityType.equals("WPA3", ignoreCase = true) || wifiConfig.securityType.equals("SAE", ignoreCase = true)) {
                            specifierBuilder.setWpa3Passphrase(wifiConfig.password)
                        } else {
                            specifierBuilder.setWpa2Passphrase(wifiConfig.password)
                        }
                    }

                    if (wifiConfig.isHidden) {
                        specifierBuilder.setIsHiddenSsid(true)
                    }

                    val networkRequest = NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        .setNetworkSpecifier(specifierBuilder.build())
                        .build()

                    val callback = object : ConnectivityManager.NetworkCallback() {
                        override fun onAvailable(network: Network) {
                            super.onAvailable(network)
                            connectivityManager.bindProcessToNetwork(network)
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(context, "Connected to ${wifiConfig.ssid}!", Toast.LENGTH_LONG).show()
                                onStatus("Connected to ${wifiConfig.ssid}")
                            }
                        }

                        override fun onUnavailable() {
                            super.onUnavailable()
                            Handler(Looper.getMainLooper()).post {
                                onStatus("Connection canceled or network unavailable.")
                            }
                        }
                    }
                    activeNetworkCallback = callback
                    connectivityManager.requestNetwork(networkRequest, callback)
                    connectionTriggered = true
                }
            } catch (e: Exception) {
                // Fall through to suggestions / intents
            }

            // Also register WifiNetworkSuggestion and trigger system add networks UI
            try {
                val wifiManager = appContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
                val suggestionBuilder = WifiNetworkSuggestion.Builder()
                    .setSsid(wifiConfig.ssid)

                if (wifiConfig.isSecured && wifiConfig.password.isNotEmpty()) {
                    if (wifiConfig.securityType.equals("WPA3", ignoreCase = true) || wifiConfig.securityType.equals("SAE", ignoreCase = true)) {
                        suggestionBuilder.setWpa3Passphrase(wifiConfig.password)
                    } else {
                        suggestionBuilder.setWpa2Passphrase(wifiConfig.password)
                    }
                }
                if (wifiConfig.isHidden) {
                    suggestionBuilder.setIsHiddenSsid(true)
                }

                val suggestion = suggestionBuilder.build()
                wifiManager?.addNetworkSuggestions(listOf(suggestion))

                val bundle = Bundle().apply {
                    putParcelableArrayList(Settings.EXTRA_WIFI_NETWORK_LIST, arrayListOf(suggestion))
                }
                val addNetIntent = Intent(Settings.ACTION_WIFI_ADD_NETWORKS).apply {
                    putExtras(bundle)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                if (addNetIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(addNetIntent)
                    connectionTriggered = true
                }
            } catch (e: Exception) {
                // Handled gracefully
            }
        }

        // 3. Android 9 and below (API <= 28): Use WifiManager.addNetwork & enableNetwork
        if (!connectionTriggered || Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            try {
                @Suppress("DEPRECATION")
                val wifiManager = appContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
                if (wifiManager != null) {
                    if (!wifiManager.isWifiEnabled) {
                        wifiManager.isWifiEnabled = true
                    }
                    val wifiConfigObj = WifiConfiguration().apply {
                        SSID = "\"${wifiConfig.ssid}\""
                        when {
                            !wifiConfig.isSecured -> {
                                allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                            }
                            wifiConfig.securityType.equals("WEP", ignoreCase = true) -> {
                                wepKeys[0] = "\"${wifiConfig.password}\""
                                wepTxKeyIndex = 0
                                allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                                allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                            }
                            else -> {
                                preSharedKey = "\"${wifiConfig.password}\""
                                allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                            }
                        }
                    }
                    val netId = wifiManager.addNetwork(wifiConfigObj)
                    if (netId != -1) {
                        wifiManager.disconnect()
                        wifiManager.enableNetwork(netId, true)
                        wifiManager.reconnect()
                        connectionTriggered = true
                    }
                }
            } catch (e: Exception) {
                // Handled gracefully
            }
        }

        // 4. User feedback
        if (wifiConfig.password.isNotEmpty()) {
            Toast.makeText(
                context,
                "Connecting to \"${wifiConfig.ssid}\"...\n(Password copied to clipboard)",
                Toast.LENGTH_LONG
            ).show()
            onStatus("Connecting to \"${wifiConfig.ssid}\"...")
        } else {
            Toast.makeText(
                context,
                "Connecting to \"${wifiConfig.ssid}\"...",
                Toast.LENGTH_SHORT
            ).show()
            onStatus("Connecting to \"${wifiConfig.ssid}\"...")
        }
    }

    /**
     * Opens system Wi-Fi settings page as an assistive fallback.
     */
    fun openWifiSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            } catch (e2: Exception) {
                Toast.makeText(context, "Could not open Wi-Fi settings", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
