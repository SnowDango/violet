package com.snowdango.violet.domain.entity.songs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val SongsTableName = "songs"

@Entity(tableName = SongsTableName)
data class Songs(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "album_id") val albumId: Long = 0,
    @ColumnInfo(name = "artist") val artistId: Long = 0,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String = "",
    @ColumnInfo(name = "genre") val genre: String
)