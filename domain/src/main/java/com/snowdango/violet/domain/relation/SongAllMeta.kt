package com.snowdango.violet.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.entity.songs.Song

data class SongAllMeta(
    @Embedded val song: Song,

    @Relation(
        parentColumn = "artist_id",
        entityColumn = "id"
    )
    val artist: Artist,

    @Relation(
        parentColumn = "album_id",
        entityColumn = "id",
        entity = Album::class
    )
    val albumWithArtist: AlbumWithArtist,

    @Relation(
        parentColumn = "id",
        entityColumn = "song_id"
    )
    val platforms: List<Platform>
)