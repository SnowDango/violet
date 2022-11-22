package com.snowdango.db.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.snowdango.db.domain.entity.platforms.Platform
import com.snowdango.db.domain.entity.platforms.PlatformsTableName

@Dao
interface PlatformDao {

    @Insert
    suspend fun insertPlatform(platform: Platform): Long

    @Query("SELECT * FROM `$PlatformsTableName` WHERE id = :id")
    suspend fun getPlatform(id: Long): List<Platform>

    @Query("SELECT * FROM `$PlatformsTableName` WHERE media_id = :mediaId")
    suspend fun getPlatformList(mediaId: String): List<Platform>

}