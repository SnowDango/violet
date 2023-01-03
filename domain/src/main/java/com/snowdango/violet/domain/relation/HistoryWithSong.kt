package com.snowdango.violet.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.histories.History
import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.entity.songs.Song

data class HistoryWithSong(
    @Embedded val history: History,

    @Relation(
        parentColumn = "song_id",
        entityColumn = "id"
    )
    val song: Song,
    @Relation(
        parentColumn = "media_id",
        entityColumn = "media_id"
    )
    val platform: Platform?
)
