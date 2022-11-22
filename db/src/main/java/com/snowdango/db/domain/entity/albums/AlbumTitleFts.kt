package com.snowdango.db.domain.entity.albums

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4(contentEntity = Album::class)
@Entity(tableName = AlbumsTableName + "TitleFts")
data class AlbumTitleFts(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String
)