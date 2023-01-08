package com.snowdango.violet.repository.db.dao

import androidx.room.*
import com.snowdango.violet.domain.entity.after.AfterSaveSong
import com.snowdango.violet.domain.entity.after.AfterSaveSongTableName
import com.snowdango.violet.domain.relation.AfterSaveSongWithHistory

@Dao
interface AfterSaveSongDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAfterSaveSong(afterSaveSong: AfterSaveSong): Long

    @Delete
    suspend fun deleteAfterSaveSong(afterSaveSong: AfterSaveSong)

    @Query("DELETE FROM `$AfterSaveSongTableName` WHERE id = :id")
    suspend fun deleteAfterSaveSongById(id: Long)

    @Query("DELETE FROM `$AfterSaveSongTableName` WHERE id in (:ids)")
    suspend fun deleteAfterSaveSongByIds(ids: List<Long>)

    @Query("SELECT * FROM `$AfterSaveSongTableName`")
    suspend fun getAllAfterSaveSong(): List<AfterSaveSong>

    @Transaction
    @Query("SELECT * FROM `$AfterSaveSongTableName`")
    suspend fun getAllAfterSaveSongWithHistory(): List<AfterSaveSongWithHistory>

}