package com.snowdango.violet.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.entity.songs.Song

data class SongWithAlbum(
    @Embedded val song: Song,

    @Relation(
        parentColumn = "album_id",
        entityColumn = "id"
    )
    val album: Album

)