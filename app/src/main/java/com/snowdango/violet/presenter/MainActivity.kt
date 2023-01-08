package com.snowdango.violet.presenter

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            startActivity(intent)
        } else {
            val intent = Intent(this, MusicNotificationListenerService::class.java)
            startForegroundService(intent)
            startWorker()
        }
    }

    private fun startWorker() {
        val manager = WorkManager.getInstance(applicationContext)
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