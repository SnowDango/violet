package com.snowdango.violet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.NotificationManagerCompat
import com.snowdango.violet.service.MusicNotificationListenerService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // TODO create navHost
        }
        startListenerService()
    }

    private fun startListenerService() {
        if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(packageName)) {
            val intent = Intent(this, MusicNotificationListenerService::class.java)
            startForegroundService(intent)
        }
    }

}