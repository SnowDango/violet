package com.snowdango.violet.domain.entity.after

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


const val AfterSaveSongTableName = "after_save_song"

@Entity(tableName = AfterSaveSongTableName)
data class AfterSaveSong(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "media_id") val mediaId: String = "",
    @ColumnInfo(name = "history_id") val historyId: Long = -1L
)
