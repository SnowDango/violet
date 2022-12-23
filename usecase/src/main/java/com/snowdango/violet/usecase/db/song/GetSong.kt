package com.snowdango.violet.usecase.db.song

import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.domain.relation.SongAllMeta
import com.snowdango.violet.domain.relation.SongWithArtist
import com.snowdango.violet.domain.relation.SongWithPlatforms
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetSong(private val db: SongHistoryDatabase) {

    suspend fun getSong(id: Long = 1L): Song? = withContext(Dispatchers.IO) {
        return@withContext db.songDao.getSong(id).firstOrNull()
    }

    suspend fun getSongList(from: Long, size: Long): List<Song> = withContext(Dispatchers.IO) {
        return@withContext db.songDao.getSongs(from, size)
    }

    suspend fun getSongCount(): Long = withContext(Dispatchers.IO) {
        return@withContext db.songDao.getCount()
    }

    suspend fun getSongWithArtist(id: Long = 1L): SongWithArtist? = withContext(Dispatchers.IO) {
        return@withContext db.songDao.getSongWithArtist(id).firstOrNull()
    }

    suspend fun getSongsWithArtist(from: Long, size: Long): List<SongWithArtist> =
        withContext(Dispatchers.IO) {
            return@withContext db.songDao.getSongsWithArtist(from, size)
        }

    suspend fun getSongWithPlatforms(id: Long = 1L): SongWithPlatforms? =
        withContext(Dispatchers.IO) {
            return@withContext db.songDao.getSongWithPlatforms(id).firstOrNull()
        }

    suspend fun getSongsWithPlatforms(from: Long, size: Long): List<SongWithPlatforms> =
        withContext(Dispatchers.IO) {
            return@withContext db.songDao.getSongsWithPlatforms(from, size)
        }

    suspend fun getSongAllMeta(id: Long = 1L): SongAllMeta? = withContext(Dispatchers.IO) {
        return@withContext db.songDao.getSongAllMeta(id).firstOrNull()
    }

    suspend fun getSongsAllMeta(from: Long, size: Long): List<SongAllMeta> =
        withContext(Dispatchers.IO) {
            return@withContext db.songDao.getSongsAllMeta(from, size)
        }

    suspend fun containsById(id: Long): Boolean = withContext(Dispatchers.IO) {
        return@withContext db.songDao.getSong(id).isNotEmpty()
    }

}