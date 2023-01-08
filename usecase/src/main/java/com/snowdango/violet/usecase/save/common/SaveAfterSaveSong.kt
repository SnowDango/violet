package com.snowdango.violet.usecase.save.common


import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.after.WriteAfterSaveSong

class SaveAfterSaveSong(private val db: SongHistoryDatabase) {

    suspend fun saveAfterSaveSong(mediaId: String, historyId: Long) {
        val writeAfterSaveSong = WriteAfterSaveSong(db)
        writeAfterSaveSong.insertAfterSaveSong(mediaId, historyId)
    }

}