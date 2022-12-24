package com.snowdango.violet.usecase.db.history

import com.snowdango.violet.domain.entity.histories.History
import com.snowdango.violet.domain.relation.HistoryWithSong
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHistory(private val db: SongHistoryDatabase) {

    suspend fun getHistory(id: Long = 1L): History? = withContext(Dispatchers.IO) {
        return@withContext db.historyDao.getHistory(id).firstOrNull()
    }

    suspend fun getHistories(from: Long, size: Long): List<History> = withContext(Dispatchers.IO) {
        return@withContext db.historyDao.getHistories(from, size)
    }

    suspend fun getHistoryCount(): Long = withContext(Dispatchers.IO) {
        return@withContext db.historyDao.getCount()
    }

    suspend fun getHistoryWithSong(id: Long = 1L): HistoryWithSong? = withContext(Dispatchers.IO) {
        return@withContext db.historyDao.getHistoryWithSong(id).firstOrNull()
    }

    suspend fun getHistoriesWithSong(from: Long, size: Long): List<HistoryWithSong> =
        withContext(Dispatchers.IO) {
            return@withContext db.historyDao.getHistoriesWithSong(from, size)
        }

    suspend fun containsById(id: Long): Boolean = withContext(Dispatchers.IO) {
        return@withContext db.historyDao.getHistory(id).isNotEmpty()
    }

}