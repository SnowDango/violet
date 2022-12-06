package com.snowdango.violet.domain.entity.histories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

const val HistoriesTableName = "histories"

@Entity(tableName = HistoriesTableName)
data class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "song_id")
    val songId: Long = -1,
    @ColumnInfo(name = "datetime")
    val dateTime: LocalDateTime,
)
