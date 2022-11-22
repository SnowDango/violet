package com.snowdango.db.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.db.domain.entity.histories.History
import com.snowdango.db.domain.entity.songs.Song

data class HistoryWithSong(
    @Embedded val history: History,

    @Relation(
        parentColumn = "song_id",
        entityColumn = "id"
    )
    val song: Song
)
