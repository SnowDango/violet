package com.snowdango.violet.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

const val SongsTableName = "songs"

@Entity(
    tableName = SongsTableName,
    indices = [Index(value = ["album_id", "artist"])]
)
data class Songs(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "album_id") val albumId: Long = 0,
    @ColumnInfo(name = "artist") val artist: String = "",
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String = ""
)
