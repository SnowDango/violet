package com.snowdango.violet.usecase.db.history

import com.snowdango.violet.domain.entity.histories.History
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class WriteHistory(private val db: SongHistoryDatabase) {

    suspend fun insertHistory(history: History) = withContext(Dispatchers.IO) {
        db.historyDao.insertHistory(history)
    }

    suspend fun insertHistories(histories: List<History>) = withContext(Dispatchers.IO) {
        db.historyDao.insertHistories(histories)
    }

    suspend fun updateHistory(history: History) = withContext(Dispatchers.IO) {
        db.historyDao.updateHistory(history)
    }

    suspend fun deleteHistory(history: History) = withContext(Dispatchers.IO) {
        db.historyDao.deleteHistory(history)
    }

    suspend fun deleteHistoryById(id: Long) = withContext(Dispatchers.IO) {
        db.historyDao.deleteHistoryById(id)
    }

    suspend fun insertHistory(songId: Long, platformType: PlatformType, datetime: Long): Long {
        val instant = Instant.fromEpochMilliseconds(datetime)
        return insertHistory(
            History(
                songId = songId,
                dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault()),
                platform = platformType
            )
        )
    }

    suspend fun updateHistory(id: Long, songId: Long, platformType: PlatformType, datetime: Long) =
        withContext(Dispatchers.IO) {
            val instant = Instant.fromEpochMilliseconds(datetime)
            updateHistory(
                History(
                    id = id,
                    songId = songId,
                    dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault()),
                    platform = platformType
                )
            )
        }

}