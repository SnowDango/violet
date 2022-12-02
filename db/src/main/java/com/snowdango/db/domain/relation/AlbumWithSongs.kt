package com.snowdango.db.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.db.domain.entity.albums.Album
import com.snowdango.db.domain.entity.songs.Song

data class AlbumWithSongs(
    @Embedded val album: Album,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "album_id"
    )
    val songs: List<Song>
)
