package com.snowdango.violet.usecase.db.artist

import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WriteArtist(private val db: SongHistoryDatabase) {

    suspend fun insertArtist(artist: Artist) = withContext(Dispatchers.IO) {
        db.artistDao.insertArtist(artist)
    }

    suspend fun insertArtists(artists: List<Artist>) = withContext(Dispatchers.IO) {
        db.artistDao.insertArtists(artists)
    }

    suspend fun deleteArtist(artist: Artist) = withContext(Dispatchers.IO) {
        db.artistDao.deleteArtist(artist)
    }

    suspend fun deleteArtistById(id: Long) = withContext(Dispatchers.IO) {
        db.artistDao.deleteArtistById(id)
    }
}