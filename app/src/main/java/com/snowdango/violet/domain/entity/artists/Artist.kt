package com.snowdango.violet.domain.entity.artists

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

const val ArtistsTableName = "artists"

data class Artist(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String = ""
)
