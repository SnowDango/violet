package com.snowdango.db.repository.dao

import androidx.room.*
import com.snowdango.db.domain.entity.histories.HistoriesTableName
import com.snowdango.db.domain.entity.histories.History
import com.snowdango.db.domain.relation.HistoryWithSong

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: History): Long

    @Query("SELECT * FROM `$HistoriesTableName` WHERE id = :id")
    suspend fun getHistory(id: Long): List<History>

    @Query("SELECT * FROM `$HistoriesTableName` ORDER BY datetime desc limit :offset,100")
    suspend fun getHistoryLimit100(offset: Long = 0): List<History>

    @Transaction
    @Query("SELECT * FROM `$HistoriesTableName` WHERE id = :id")
    suspend fun getHistoryWithSong(id: Long): List<HistoryWithSong>

    @Transaction
    @Query("SELECT * FROM `$HistoriesTableName` ORDER BY datetime desc limit :offset,100")
    suspend fun getHistoryWithSongLimit100(offset: Long): List<HistoryWithSong>

}