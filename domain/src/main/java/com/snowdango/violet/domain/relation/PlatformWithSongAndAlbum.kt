package com.snowdango.violet.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.entity.songs.Song

data class PlatformWithSongAndAlbum(
    @Embedded val platform: Platform,

    @Relation(
        parentColumn = "song_id",
        entityColumn = "id",
        entity = Song::class
    )
    val song: List<SongWithAlbum>

)