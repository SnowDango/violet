package com.snowdango.violet.usecase.db.song

import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.repository.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WriteSong(private val db: SongHistoryDatabase) {

    suspend fun insertSong(song: Song) = withContext(Dispatchers.IO) {
        db.songDao.insertSong(song)
    }

    suspend fun insetsSongs(songs: List<Song>) = withContext(Dispatchers.IO) {
        db.songDao.insertSongs(songs)
    }

    suspend fun deleteSong(song: Song) = withContext(Dispatchers.IO) {
        db.songDao.deleteSong(song)
    }

    suspend fun deleteSongById(id: Long) = withContext(Dispatchers.IO) {
        db.songDao.deleteSongById(id)
    }

}