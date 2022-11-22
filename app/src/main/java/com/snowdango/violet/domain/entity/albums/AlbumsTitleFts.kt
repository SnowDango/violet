package com.snowdango.violet.domain.entity.albums

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Albums::class)
@Entity(tableName = AlbumsTableName + "TitleFts")
data class AlbumsTitleFts(
    val id: Long,
    val title: String
)