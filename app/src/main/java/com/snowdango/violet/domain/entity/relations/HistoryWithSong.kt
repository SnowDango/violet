package com.snowdango.violet.domain.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.histories.History
import com.snowdango.violet.domain.entity.songs.Song

data class HistoryWithSong(
    @Embedded val history: History,
    
    @Relation(
        parentColumn = "song_id",
        entityColumn = "id"
    )
    val song: Song
)
