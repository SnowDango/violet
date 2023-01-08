package com.snowdango.violet.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.snowdango.violet.Const
import com.snowdango.violet.R
import com.snowdango.violet.model.data.SaveAfterSaveSongModel
import timber.log.Timber

class AfterSaveSongWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Timber.d("worker start")
        setForeground(createForegroundInfo())
        val saveAfterSaveSongModel = SaveAfterSaveSongModel()
        saveAfterSaveSongModel.saveAfterSaveSong()
        return Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val channel = NotificationChannel(
            Const.WORKER_CHANNEL_ID,
            Const.WORKER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_NONE
        ).also {
            it.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }
        (applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).also {
            it.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext, Const.WORKER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setWhen(System.currentTimeMillis())
            .setOngoing(true)
            .build()
        return ForegroundInfo(Const.WORKER_NOTIFICATION_ID, notification)
    }


}