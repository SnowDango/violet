package com.snowdango.violet.domain.entity.songs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4(contentEntity = Song::class)
@Entity(tableName = SongsTableName + "TitleFts")
data class SongTitleFts(
    @PrimaryKey @ColumnInfo(name = "rowid")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String
)