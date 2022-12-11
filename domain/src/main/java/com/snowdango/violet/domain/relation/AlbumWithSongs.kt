package com.snowdango.violet.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.entity.songs.Song

data class AlbumWithSongs(
    @Embedded val album: Album,

    @Relation(
        parentColumn = "id",
        entityColumn = "album_id"
    )
    val songs: List<Song>
)
