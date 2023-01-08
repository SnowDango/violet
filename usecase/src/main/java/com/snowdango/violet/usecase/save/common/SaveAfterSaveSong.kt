package com.snowdango.violet.usecase.save.common


import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.after.WriteAfterSaveSong

class SaveAfterSaveSong(private val db: SongHistoryDatabase) {

    suspend fun saveAfterSaveSong(historyId: Long, lastSong: LastSong) {
        val writeAfterSaveSong = WriteAfterSaveSong(db)
        writeAfterSaveSong.insertAfterSaveSong(historyId, lastSong)
    }

}