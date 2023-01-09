package com.snowdango.violet.presenter

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.snowdango.violet.R
import com.snowdango.violet.databinding.ActivityMainBinding
import com.snowdango.violet.service.MusicNotificationListenerService
import com.snowdango.violet.worker.AfterSaveSongWorker
import java.time.Duration

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        binding.navigationBottom.setupWithNavController(navController)
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
            .setTitle("権限の要求")
            .setMessage("このアプリは音楽を認識するために通知を読む権限を必要とします。音楽系のサブスク以外の通知に関しては内容を一切参照したりしません。\nまた、この権限は最初にユーザーが権限を許可する必要があります。")
            .setPositiveButton("OK") { dialog, _ ->
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
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