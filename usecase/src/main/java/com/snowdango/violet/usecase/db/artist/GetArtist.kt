package com.snowdango.violet.usecase.db.artist

import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.domain.relation.ArtistWithAlbums
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetArtist(private val db: SongHistoryDatabase) {

    suspend fun getArtist(id: Long = 1L): Artist? = withContext(Dispatchers.IO) {
        return@withContext db.artistDao.getArtist(id).firstOrNull()
    }

    suspend fun getArtistByName(name: String): Artist? = withContext(Dispatchers.IO) {
        return@withContext db.artistDao.getArtistByName(name).firstOrNull()
    }

    suspend fun getArtists(from: Long, size: Long): List<Artist> = withContext(Dispatchers.IO) {
        return@withContext db.artistDao.getArtists(from, size)
    }

    suspend fun getArtistCount(): Long = withContext(Dispatchers.IO) {
        return@withContext db.artistDao.getCount()
    }

    suspend fun getArtistWithAlbums(id: Long = 1L): ArtistWithAlbums? =
        withContext(Dispatchers.IO) {
            return@withContext db.artistDao.getArtistWithAlbums(id).firstOrNull()
        }

    suspend fun getArtistsWithAlbums(from: Long, size: Long): List<ArtistWithAlbums> =
        withContext(Dispatchers.IO) {
            return@withContext db.artistDao.getArtistsWithAlbums(from, size)
        }

    suspend fun containsById(id: Long): Boolean = withContext(Dispatchers.IO) {
        return@withContext db.artistDao.getArtist(id).isNotEmpty()
    }

    suspend fun containsByName(name: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext db.artistDao.getArtistByName(name).isNotEmpty()
    }

}