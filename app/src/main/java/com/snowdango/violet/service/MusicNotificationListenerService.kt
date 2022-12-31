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
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.extention.getMediaController
import com.snowdango.violet.model.SaveSongHistoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

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
        Timber.d(notification.toString())
        notification?.let {
            if (notification.category != Notification.CATEGORY_TRANSPORT ||
                !PlatformType.values().any { platform -> platform.packageName == sbn.packageName }
            ) {
                return
            }
            val data = getData(sbn.packageName)
            val model = SaveSongHistoryModel(applicationContext)
            CoroutineScope(Dispatchers.Default).launch {
                data?.let { model.saveSongHistory(it) }
            }
        }
        super.onNotificationPosted(sbn)
    }

    private fun getData(packageName: String): LastSong? {
        val mediaController = applicationContext.getMediaController(packageName)
        val metadata = mediaController?.metadata ?: return null
        val queue = mediaController.queue?.firstOrNull() ?: return null
        return LastSong(
            mediaId = metadata.getString(MediaMetadata.METADATA_KEY_MEDIA_ID),
            title = metadata.getString(MediaMetadata.METADATA_KEY_TITLE),
            artist = metadata.getString(MediaMetadata.METADATA_KEY_ARTIST),
            album = metadata.getString(MediaMetadata.METADATA_KEY_ALBUM),
            albumArtist = metadata.getString(MediaMetadata.METADATA_KEY_ALBUM_ARTIST),
            platform = PlatformType.values().firstOrNull { it.packageName == packageName },
            queueId = queue.queueId
        )
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
}