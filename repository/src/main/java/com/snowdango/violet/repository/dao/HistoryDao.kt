package com.snowdango.violet.repository.dao

import androidx.room.*
import com.snowdango.violet.domain.entity.histories.HistoriesTableName
import com.snowdango.violet.domain.entity.histories.History
import com.snowdango.violet.domain.relation.HistoryWithSong

@Dao
interface HistoryDao {

    @Insert
    suspend fun insertHistory(history: History): Long

    @Insert
    suspend fun insertHistories(histories: List<History>): List<Long>

    @Delete
    suspend fun deleteHistory(history: History)

    @Query("DELETE FROM $HistoriesTableName WHERE id = :id")
    suspend fun deleteHistoryById(id: Long)

    @Query("SELECT COUNT(id) FROM $HistoriesTableName")
    suspend fun getCount(): Long

    @Query("SELECT * FROM `$HistoriesTableName` WHERE id = :id")
    suspend fun getHistory(id: Long): List<History>

    @Query("SELECT * FROM `$HistoriesTableName` ORDER BY datetime desc limit :from,:size")
    suspend fun getHistories(from: Long, size: Long): List<History>

    @Transaction
    @Query("SELECT * FROM `$HistoriesTableName` WHERE id = :id")
    suspend fun getHistoryWithSong(id: Long): List<HistoryWithSong>

    @Transaction
    @Query("SELECT * FROM `$HistoriesTableName` ORDER BY datetime desc limit :from,:size")
    suspend fun getHistoriesWithSong(from: Long, size: Long): List<HistoryWithSong>

}