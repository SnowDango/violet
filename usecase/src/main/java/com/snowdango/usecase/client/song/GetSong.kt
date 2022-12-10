package com.snowdango.usecase.client.song

import android.content.Context
import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.repository.SongHistoryDatabase

class GetSong(context: Context) {

    private val db = SongHistoryDatabase.getInstance(context)

    suspend fun getSong(id: Long): Song? {
        val data = db.songDao.getSong(id)
        return if (data.isEmpty()) {
            null
        } else {
            data.first()
        }
    }

    suspend fun getSongs(from: Long, size: Long): List<Song> {
        return db.songDao.getSongs(from, size)
    }

    
}