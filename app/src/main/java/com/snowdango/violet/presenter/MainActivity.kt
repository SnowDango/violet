package com.snowdango.violet.presenter

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.snowdango.violet.R
import com.snowdango.violet.databinding.ActivityMainBinding
import com.snowdango.violet.service.MusicNotificationListenerService
import com.snowdango.violet.worker.AfterSaveSongWorker
import java.time.Duration

class MainActivity : ComponentActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        startService()
    }

    private fun startService() {
        if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(packageName)) {
            descriptionPermissionDialog()
        } else {
            val intent = Intent(this, MusicNotificationListenerService::class.java)
            startForegroundService(intent)
            startWorker()
        }
    }

    private fun descriptionPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.first_permission_require_title))
            .setMessage(resources.getString(R.string.first_permission_require_text))
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.cencel)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }.show()
    }

    private fun startWorker() {
        val manager = WorkManager.getInstance(this)
        val request = PeriodicWorkRequest.Builder(
            AfterSaveSongWorker::class.java,
            Duration.ofHours(1)
        ).build()
        manager.enqueue(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}