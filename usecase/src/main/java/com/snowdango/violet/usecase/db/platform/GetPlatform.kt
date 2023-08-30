package com.snowdango.violet.usecase.db.platform

import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.domain.relation.PlatformWithSong
import com.snowdango.violet.domain.relation.PlatformWithSongAndAlbum
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPlatform(private val db: SongHistoryDatabase) {

    suspend fun getPlatform(id: Long = 1L): Platform? = withContext(Dispatchers.IO) {
        return@withContext db.platformDao.getPlatform(id).firstOrNull()
    }

    suspend fun getPlatformByMediaId(mediaId: String): Platform? = withContext(Dispatchers.IO) {
        return@withContext db.platformDao.getPlatformByMediaId(mediaId).firstOrNull()
    }

    suspend fun getPlatformCount(): Long = withContext(Dispatchers.IO) {
        return@withContext db.platformDao.getCount()
    }

    suspend fun containsById(id: Long): Boolean = withContext(Dispatchers.IO) {
        return@withContext db.platformDao.getPlatform(id).isNotEmpty()
    }

    suspend fun containsByMediaId(mediaId: String, platform: PlatformType): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext db.platformDao.getPlatformByMediaId(mediaId)
                .any { it.platform == platform }
        }

    suspend fun getPlatformWithSong(mediaId: String): PlatformWithSong? =
        withContext(Dispatchers.IO) {
            return@withContext db.platformDao.getPlatformWithSongByMediaId(mediaId).firstOrNull()
        }

    suspend fun getPlatformsWithSong(mediaId: String): List<PlatformWithSong> =
        withContext(Dispatchers.IO) {
            return@withContext db.platformDao.getPlatformWithSongByMediaId(mediaId)
        }

    suspend fun getPlatformWithSongAndAlbum(mediaId: String): PlatformWithSongAndAlbum? =
        withContext(Dispatchers.IO) {
            return@withContext db.platformDao.getPlatformWithSongAndAlbumByMediaId(mediaId).firstOrNull()
        }

}