package com.snowdango.violet.model.data

import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.history.WriteHistory
import org.koin.core.component.KoinComponent

class DeleteHistoryModel(private val db: SongHistoryDatabase) : KoinComponent {

    suspend fun deleteHistory(id: Long): Long {
        val writeHistory = WriteHistory(db)
        writeHistory.deleteHistoryById(id)
        return id
    }

}