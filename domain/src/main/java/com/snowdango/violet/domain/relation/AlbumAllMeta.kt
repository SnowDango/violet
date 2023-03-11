package com.snowdango.violet.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.domain.entity.songs.Song

data class AlbumAllMeta(
    @Embedded val album: Album,

    @Relation(
        parentColumn = "artist_id",
        entityColumn = "id"
    )
    val artist: Artist?,

    @Relation(
        parentColumn = "id",
        entityColumn = "album_id",
        entity = Song::class
    )
    val songs: List<SongWithArtist>
)