package com.snowdango.violet.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class AfterSaveSongWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        // TODO after save song worker
        return Result.success()
    }

}