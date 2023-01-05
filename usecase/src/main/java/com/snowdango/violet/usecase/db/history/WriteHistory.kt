package com.snowdango.violet.usecase.db.history

import com.snowdango.violet.domain.entity.histories.History
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class WriteHistory(private val db: SongHistoryDatabase) {

    suspend fun insertHistory(history: History) = withContext(Dispatchers.IO) {
        db.historyDao.insertHistory(history)
    }

    suspend fun insertHistories(histories: List<History>) = withContext(Dispatchers.IO) {
        db.historyDao.insertHistories(histories)
    }

    suspend fun deleteHistory(history: History) = withContext(Dispatchers.IO) {
        db.historyDao.deleteHistory(history)
    }

    suspend fun deleteHistoryById(id: Long) = withContext(Dispatchers.IO) {
        db.historyDao.deleteHistoryById(id)
    }

    suspend fun insertHistory(songId: Long, platformType: PlatformType) =
        withContext(Dispatchers.IO) {
            val clock = Clock.System.now()
            insertHistory(
                History(
                    songId = songId,
                    dateTime = clock.toLocalDateTime(TimeZone.currentSystemDefault()),
                    platform = platformType
                )
            )
        }

}