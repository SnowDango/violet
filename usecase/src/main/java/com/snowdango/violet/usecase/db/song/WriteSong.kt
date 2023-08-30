package com.snowdango.violet.usecase.db.song

import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WriteSong(private val db: SongHistoryDatabase) {

    suspend fun insertSong(song: Song) = withContext(Dispatchers.IO) {
        db.songDao.insertSong(song)
    }

    suspend fun insertSongs(songs: List<Song>) = withContext(Dispatchers.IO) {
        db.songDao.insertSongs(songs)
    }

    suspend fun deleteSong(song: Song) = withContext(Dispatchers.IO) {
        db.songDao.deleteSong(song)
    }

    suspend fun deleteSongById(id: Long) = withContext(Dispatchers.IO) {
        db.songDao.deleteSongById(id)
    }

    suspend fun insertSong(
        title: String?,
        artistId: Long,
        albumId: Long,
        thumbnail: String?,
        genre: String?
    ): Long = withContext(Dispatchers.IO) {
        if (title == null) return@withContext -1L
        insertSong(
            Song(
                title = title,
                artistId = artistId,
                albumId = albumId,
                thumbnailUrl = thumbnail ?: "",
                genre = genre ?: ""
            )
        )
    }

    suspend fun updateThumbnailUrl(id: Long, thumbnailUrl: String) = withContext(Dispatchers.IO) {
        db.songDao.updateThumbnailUrl(id, thumbnailUrl)
    }

}