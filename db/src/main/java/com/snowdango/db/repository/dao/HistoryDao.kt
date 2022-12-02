package com.snowdango.db.repository.dao

import androidx.room.*
import com.snowdango.db.domain.entity.histories.HistoriesTableName
import com.snowdango.db.domain.entity.histories.History
import com.snowdango.db.domain.relation.HistoryWithSong

@Dao
interface HistoryDao {

    @Insert
    suspend fun insertHistory(history: History): Long

    @Insert
    suspend fun insertHistories(histories: List<History>): List<Long>

    @Delete
    suspend fun deleteHistory(history: History)

    @Query("DELETE FROM $HistoriesTableName WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT COUNT(id) FROM $HistoriesTableName")
    suspend fun getCount(): Long

    @Query("SELECT * FROM `$HistoriesTableName` WHERE id = :id")
    suspend fun getHistory(id: Long): List<History>

    @Query("SELECT * FROM `$HistoriesTableName` ORDER BY datetime desc limit :offset,100")
    suspend fun getHistoriesLimit100(offset: Long = 0): List<History>

    @Transaction
    @Query("SELECT * FROM `$HistoriesTableName` WHERE id = :id")
    suspend fun getHistoryWithSong(id: Long): List<HistoryWithSong>

    @Transaction
    @Query("SELECT * FROM `$HistoriesTableName` ORDER BY datetime desc limit :offset,100")
    suspend fun getHistoriesWithSongLimit100(offset: Long): List<HistoryWithSong>

}