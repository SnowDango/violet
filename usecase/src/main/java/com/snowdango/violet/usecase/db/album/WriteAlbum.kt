package com.snowdango.violet.usecase.db.album

import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WriteAlbum(private val db: SongHistoryDatabase) {

    suspend fun insertAlbum(album: Album) = withContext(Dispatchers.IO) {
        db.albumDao.insertAlbum(album)
    }

    suspend fun insertAlbums(albums: List<Album>) = withContext(Dispatchers.IO) {
        db.albumDao.insertAlbums(albums)
    }

    suspend fun deleteAlbum(album: Album) = withContext(Dispatchers.IO) {
        db.albumDao.deleteAlbum(album)
    }

    suspend fun deleteAlbums(albums: List<Album>) = withContext(Dispatchers.IO) {
        db.albumDao.deleteAlbums(albums)
    }

    suspend fun deleteAlbumById(id: Long) = withContext(Dispatchers.IO) {
        db.albumDao.deleteAlbumById(id)
    }

    suspend fun insertAlbum(title: String?, artistId: Long, thumbnail: String): Long =
        withContext(Dispatchers.IO) {
            if (title == null) return@withContext -1L
            insertAlbum(
                Album(
                    title = title,
                    artistId = artistId,
                    thumbnailUrl = thumbnail
                )
            )
        }

}