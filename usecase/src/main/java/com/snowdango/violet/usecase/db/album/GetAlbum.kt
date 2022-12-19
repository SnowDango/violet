package com.snowdango.violet.usecase.db.album

import com.snowdango.violet.repository.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAlbum(private val db: SongHistoryDatabase) {

    suspend fun getAlbum(id: Long = 1L) = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbum(id).first()
    }

    suspend fun getAlbums(from: Long, size: Long) = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbums(from, size)
    }
    
    suspend fun getAlbumCount() = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getCount()
    }

    suspend fun getAlbumWithSongs(id: Long = 1L) = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbumWithSongs(id).first()
    }

    suspend fun getAlbumsWithSongs(from: Long, size: Long) = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbumsWithSongs(from, size)
    }

    suspend fun getAlbumWithArtist(id: Long = 1L) = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbumWithArtist(id).first()
    }

    suspend fun getAlbumsWithArtist(from: Long, size: Long) = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbumsWithArtist(from, size)
    }

}