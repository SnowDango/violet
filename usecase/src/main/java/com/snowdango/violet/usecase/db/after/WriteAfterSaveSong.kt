package com.snowdango.violet.usecase.db.after

import com.snowdango.violet.domain.entity.after.AfterSaveSong
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WriteAfterSaveSong(private val db: SongHistoryDatabase) {

    suspend fun insertAfterSaveSong(afterSaveSong: AfterSaveSong) = withContext(Dispatchers.IO) {
        db.afterSaveSongDao.insertAfterSaveSong(afterSaveSong)
    }

    suspend fun deleteAfterSaveSong(afterSaveSong: AfterSaveSong) = withContext(Dispatchers.IO) {
        db.afterSaveSongDao.deleteAfterSaveSong(afterSaveSong)
    }

    suspend fun deleteAfterSaveSongById(id: Long) = withContext(Dispatchers.IO) {
        db.afterSaveSongDao.deleteAfterSaveSongById(id)
    }

    suspend fun deleteAfterSaveSongByIds(ids: List<Long>) = withContext(Dispatchers.IO) {
        db.afterSaveSongDao.deleteAfterSaveSongByIds(ids)
    }

    suspend fun insertAfterSaveSong(
        mediaId: String,
        historyId: Long,
    ): Long {
        return insertAfterSaveSong(
            AfterSaveSong(
                mediaId = mediaId,
                historyId = historyId
            )
        )
    }
}