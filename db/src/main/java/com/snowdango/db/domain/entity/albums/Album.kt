package com.snowdango.db.domain.entity.albums

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val AlbumsTableName = "albums"

@Entity(tableName = AlbumsTableName)
data class Album(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "artist_id") val artistId: Long = -1,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String = ""
)