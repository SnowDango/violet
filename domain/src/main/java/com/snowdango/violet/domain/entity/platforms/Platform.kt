package com.snowdango.violet.domain.entity.platforms

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val PlatformsTableName = "platforms"

@Entity(tableName = PlatformsTableName)
data class Platform(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "song_id")
    val songId: Long = -1,
    @ColumnInfo(name = "platform")
    val platform: String = "",
    @ColumnInfo(name = "media_id")
    val mediaId: String = "",
    @ColumnInfo(name = "url")
    val url: String? = null
)