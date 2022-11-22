package com.snowdango.violet.domain.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.domain.entity.songs.Song

data class SongWithArtist(
    @Embedded val song: Song,
    
    @Relation(
        parentColumn = "artist_id",
        entityColumn = "id"
    )
    val artist: Artist
)
