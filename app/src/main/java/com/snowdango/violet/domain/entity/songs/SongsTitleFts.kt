package com.snowdango.violet.domain.entity.songs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4(contentEntity = Songs::class)
@Entity(tableName = SongsTableName + "TitleFts")
data class SongsTitleFts(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String
)