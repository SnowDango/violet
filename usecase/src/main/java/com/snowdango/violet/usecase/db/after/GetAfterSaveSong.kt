package com.snowdango.violet.usecase.db.after

import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAfterSaveSong(private val db: SongHistoryDatabase) {

    suspend fun getAllAfterSaveSong() = withContext(Dispatchers.IO) {
        db.afterSaveSongDao.getAllAfterSaveSong()
    }

    suspend fun getAllAfterSaveSongByHistory(historyId: Long) = withContext(Dispatchers.IO) {
        db.afterSaveSongDao.getAllAfterSaveSongWithHistory()
    }

}