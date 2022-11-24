package com.snowdango.db.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.db.domain.entity.albums.Album
import com.snowdango.db.domain.entity.artists.Artist

data class ArtistWithAlbums(
    @Embedded val artist: Artist,

    @Relation(
        parentColumn = "id",
        entityColumn = "artist_id"
    )
    val albums: List<Album>
)