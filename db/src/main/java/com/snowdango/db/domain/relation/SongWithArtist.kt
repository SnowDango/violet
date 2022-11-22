package com.snowdango.db.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.db.domain.entity.artists.Artist
import com.snowdango.db.domain.entity.songs.Song

data class SongWithArtist(
    @Embedded val song: Song,

    @Relation(
        parentColumn = "artist_id",
        entityColumn = "id"
    )
    val artist: Artist
)
