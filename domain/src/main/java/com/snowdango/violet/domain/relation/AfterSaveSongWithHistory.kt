package com.snowdango.violet.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.snowdango.violet.domain.entity.after.AfterSaveSong
import com.snowdango.violet.domain.entity.histories.History

data class AfterSaveSongWithHistory(
    @Embedded val afterSaveSong: AfterSaveSong,

    @Relation(
        parentColumn = "history_id",
        entityColumn = "id"
    )
    val history: History?
)
