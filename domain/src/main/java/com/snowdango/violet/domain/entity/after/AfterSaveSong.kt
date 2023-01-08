package com.snowdango.violet.domain.entity.after

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.snowdango.violet.domain.platform.PlatformType


const val AfterSaveSongTableName = "after_save_song"

@Entity(tableName = AfterSaveSongTableName)
data class AfterSaveSong(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "media_id") val mediaId: String?,
    @ColumnInfo(name = "history_id") val historyId: Long = -1L,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "artist") val artist: String?,
    @ColumnInfo(name = "album") val album: String?,
    @ColumnInfo(name = "albumArtist") val albumArtist: String?,
    @ColumnInfo(name = "platform") val platformType: PlatformType?,
    @ColumnInfo(name = "genre") val genre: String?,
    @ColumnInfo(name = "datetime") val dateTime: Long?
)
