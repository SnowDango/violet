package com.snowdango.violet.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.entity.artists.Artist

data class ArtistWithAlbums(
    @Embedded val artist: Artist,

    @Relation(
        parentColumn = "id",
        entityColumn = "artist_id"
    )
    val albums: List<Album>
)