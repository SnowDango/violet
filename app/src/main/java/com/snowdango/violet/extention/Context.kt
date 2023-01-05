package com.snowdango.violet.extention

import android.content.ComponentName
import android.content.Context
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import com.snowdango.violet.service.MusicNotificationListenerService

fun Context.getMediaController(packageName: String): MediaController? {
    val sessionManager = getSystemService(MediaSessionManager::class.java)
    val component = ComponentName(this, MusicNotificationListenerService::class.java)
    return sessionManager.getActiveSessions(component).firstOrNull {
        it.packageName == packageName
    }
}