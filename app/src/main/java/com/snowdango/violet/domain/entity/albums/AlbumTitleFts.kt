package com.snowdango.violet.domain.entity.albums

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Album::class)
@Entity(tableName = AlbumsTableName + "TitleFts")
data class AlbumTitleFts(
    val id: Long,
    val title: String
)