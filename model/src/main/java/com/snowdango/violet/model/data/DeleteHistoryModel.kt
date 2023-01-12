package com.snowdango.violet.model.data

import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.history.WriteHistory
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteHistoryModel : KoinComponent {

    private val db: SongHistoryDatabase by inject()

    suspend fun deleteHistory(id: Long) {
        val writeHistory = WriteHistory(db)
        writeHistory.deleteHistoryById(id)
    }

}