package com.snowdango.violet.domain.entity.albums

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val AlbumsTableName = "albums"

@Entity(tableName = AlbumsTableName)
data class Albums(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "artist_id") val artistId: Long,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String
)