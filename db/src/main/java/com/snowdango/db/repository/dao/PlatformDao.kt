package com.snowdango.db.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.snowdango.db.domain.entity.platforms.Platform
import com.snowdango.db.domain.entity.platforms.PlatformsTableName

@Dao
interface PlatformDao {

    @Insert
    suspend fun insertPlatform(platform: Platform): Long

    @Insert
    suspend fun insertPlatforms(platforms: List<Platform>): List<Long>

    @Delete
    suspend fun deletePlatform(platform: Platform)

    @Query("DELETE FROM $PlatformsTableName WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT COUNT(id) FROM $PlatformsTableName")
    suspend fun getCount(): Long

    @Query("SELECT * FROM `$PlatformsTableName` WHERE id = :id")
    suspend fun getPlatform(id: Long): List<Platform>

    @Query("SELECT * FROM `$PlatformsTableName` WHERE media_id = :mediaId")
    suspend fun getPlatforms(mediaId: String): List<Platform>

}