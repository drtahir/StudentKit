package com.drtahir.studentkit.data

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.UUID

class IntruderDeviceAdminReceiver : DeviceAdminReceiver() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Toast.makeText(context, "Intruder Shield Activated", Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Toast.makeText(context, "Intruder Shield Deactivated", Toast.LENGTH_SHORT).show()
    }

    override fun onPasswordFailed(context: Context, intent: Intent, userHandle: android.os.UserHandle) {
        super.onPasswordFailed(context, intent, userHandle)
        val database = AppDatabase.getDatabase(context)

        // Read battery status safely
        var batteryLevel = 0
        var isCharging = false
        try {
            val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
                context.registerReceiver(null, filter)
            }
            batteryLevel = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Store flag for unhandled breach
        try {
            val prefs = context.getSharedPreferences("intruder_guard_prefs", Context.MODE_PRIVATE)
            val currentCount = prefs.getInt("unhandled_breach_count", 0)
            prefs.edit()
                .putBoolean("has_unhandled_lockscreen_breach", true)
                .putInt("unhandled_breach_count", currentCount + 1)
                .putLong("last_breach_timestamp", System.currentTimeMillis())
                .apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Post High Priority System Alert Notification
        IntruderNotificationHelper.showBreachNotification(
            context,
            "🚨 SECURITY BREACH ALERT!",
            "Unauthorized unlock attempt failed on your phone lock screen! Tap to view breach report."
        )

        scope.launch {
            val log = IntruderLog(
                id = UUID.randomUUID().toString(),
                timestamp = System.currentTimeMillis(),
                photoPath = null,
                attemptStatus = "System Lock Screen Failed",
                notes = "Incorrect PIN/Pattern entered on main device lockscreen. Battery: $batteryLevel% (${if (isCharging) "Charging" else "Discharging"}).",
                batteryLevel = if (batteryLevel > 0) batteryLevel else null,
                networkStatus = if (isCharging) "Charging" else "Battery Power",
                cameraFacing = "Front Camera"
            )
            database.dao().insertIntruderLog(log)
        }
    }
}

