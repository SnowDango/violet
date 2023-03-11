package com.snowdango.violet.usecase.db.album

import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.relation.AlbumAllMeta
import com.snowdango.violet.domain.relation.AlbumWithArtist
import com.snowdango.violet.domain.relation.AlbumWithSongs
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAlbum(private val db: SongHistoryDatabase) {

    suspend fun getAlbum(id: Long = 1L): Album? = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbum(id).firstOrNull()
    }

    suspend fun getAlbumByTitle(title: String): Album? = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbumByTitle(title = title).firstOrNull()
    }

    suspend fun getAlbumByTitleAndArtistId(title: String, artistId: Long): Album? =
        withContext(Dispatchers.IO) {
            return@withContext db.albumDao.getAlbumByTitleAndArtistId(
                title = title,
                artistId = artistId
            ).firstOrNull()
        }

    suspend fun getAlbums(from: Long, size: Long): List<Album> = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbums(from, size)
    }

    suspend fun getAlbumCount(): Long = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getCount()
    }

    suspend fun getAlbumWithSongs(id: Long = 1L): AlbumWithSongs? = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbumWithSongs(id).firstOrNull()
    }

    suspend fun getAlbumsWithSongs(from: Long, size: Long): List<AlbumWithSongs> =
        withContext(Dispatchers.IO) {
            return@withContext db.albumDao.getAlbumsWithSongs(from, size)
        }

    suspend fun getAlbumWithArtist(id: Long = 1L): AlbumWithArtist? = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbumWithArtist(id).firstOrNull()
    }

    suspend fun getAlbumsWithArtist(from: Long, size: Long): List<AlbumWithArtist> =
        withContext(Dispatchers.IO) {
            return@withContext db.albumDao.getAlbumsWithArtist(from, size)
        }

    suspend fun getAlbumAllMeta(id: Long): AlbumAllMeta? = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbumAllMeta(id).firstOrNull()
    }

    suspend fun containsById(id: Long): Boolean = withContext(Dispatchers.IO) {
        return@withContext db.albumDao.getAlbum(id).isNotEmpty()
    }

}