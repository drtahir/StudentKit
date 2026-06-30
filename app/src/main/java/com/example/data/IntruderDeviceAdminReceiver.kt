package com.example.data

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
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
        scope.launch {
            val log = IntruderLog(
                id = UUID.randomUUID().toString(),
                timestamp = System.currentTimeMillis(),
                photoPath = null,
                attemptStatus = "System Lock Screen Failed",
                notes = "Incorrect password or pattern on device lock screen."
            )
            database.dao().insertIntruderLog(log)
        }
    }
}
