package com.snowdango.db.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.db.domain.entity.albums.Album
import com.snowdango.db.domain.entity.artists.Artist

data class AlbumWithArtist(
    @Embedded val album: Album,

    @Relation(
        parentColumn = "artist_id",
        entityColumn = "id"
    )
    val artist: Artist
)
