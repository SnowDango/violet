package com.snowdango.violet.domain.entity.histories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val HistoriesTableName = "histories"

@Entity(tableName = HistoriesTableName)
data class Histories(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "song_id")
    val songId: Long = 0,
    @ColumnInfo(name = "datetime")
    val dateTime: Long = 0,
)
