package com.snowdango.violet.usecase.db.after

import com.snowdango.violet.domain.entity.after.AfterSaveSong
import com.snowdango.violet.domain.last.LastSong
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
        historyId: Long,
        lastSong: LastSong
    ): Long {
        return insertAfterSaveSong(
            AfterSaveSong(
                historyId = historyId,
                mediaId = lastSong.mediaId,
                title = lastSong.title,
                artist = lastSong.artist,
                album = lastSong.album,
                albumArtist = lastSong.albumArtist,
                platformType = lastSong.platform,
                genre = lastSong.genre,
                dateTime = lastSong.dateTime
            )
        )
    }
}