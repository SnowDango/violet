package com.snowdango.violet.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.entity.songs.Song

data class SongWithPlatforms(
    @Embedded val song: Song,

    @Relation(
        parentColumn = "id",
        entityColumn = "song_id"
    )
    val platforms: List<Platform>
)
