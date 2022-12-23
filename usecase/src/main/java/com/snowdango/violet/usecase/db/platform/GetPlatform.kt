package com.snowdango.violet.usecase.db.platform

import com.snowdango.violet.domain.entity.platforms.Platform
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

}