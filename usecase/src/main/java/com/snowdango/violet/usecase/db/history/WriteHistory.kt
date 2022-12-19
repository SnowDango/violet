package com.snowdango.violet.usecase.db.history

import com.snowdango.violet.domain.entity.histories.History
import com.snowdango.violet.repository.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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
    
}