package com.snowdango.violet.usecase.db.platform

import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.repository.db.SongHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WritePlatform(private val db: SongHistoryDatabase) {

    suspend fun insertPlatform(platform: Platform) = withContext(Dispatchers.IO) {
        db.platformDao.insertPlatform(platform)
    }

    suspend fun insertPlatforms(platforms: List<Platform>) = withContext(Dispatchers.IO) {
        db.platformDao.insertPlatforms(platforms)
    }

    suspend fun deletePlatform(platform: Platform) = withContext(Dispatchers.IO) {
        db.platformDao.deletePlatform(platform)
    }

    suspend fun deletePlatformById(id: Long) = withContext(Dispatchers.IO) {
        db.platformDao.deletePlatformById(id)
    }

    suspend fun insertPlatform(
        songId: Long,
        platformType: PlatformType,
        mediaId: String,
        url: String
    ): Long = withContext(Dispatchers.IO) {
        insertPlatform(
            Platform(
                songId = songId,
                platform = platformType,
                mediaId = mediaId,
                url = url
            )
        )
    }


}