package com.snowdango.violet.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.MediaMetadata
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import com.snowdango.violet.Const
import com.snowdango.violet.R
import com.snowdango.violet.extention.getMediaController
import com.snowdango.violet.repository.platform.Platforms

class MusicNotificationListenerService : NotificationListenerService() {

    override fun onBind(intent: Intent?): IBinder? {
        startForeground()
        return super.onBind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    private fun startForeground() {
        val channel = NotificationChannel(
            Const.CHANNEL_ID,
            Const.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_NONE
        ).also {
            it.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).also {
            it.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, Const.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setWhen(System.currentTimeMillis())
            .setOngoing(true)
            .build()
        startForeground(Const.NOTIFICATION_ID, notification)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        val notification = sbn?.notification
        notification?.let {
            if (notification.category != Notification.CATEGORY_TRANSPORT ||
                Platforms.platforms.any { it.packageName == packageName }
            ) {
                return
            }
            val mediaId: String = getMediaId() ?: return
        }
        super.onNotificationPosted(sbn)
    }

    private fun getMediaId(): String? {
        val mediaController = applicationContext.getMediaController(packageName)
        return mediaController?.metadata?.getString(MediaMetadata.METADATA_KEY_MEDIA_ID)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
}