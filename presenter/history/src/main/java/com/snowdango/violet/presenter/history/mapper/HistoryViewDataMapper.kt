package com.snowdango.violet.presenter.history.mapper

import com.snowdango.violet.domain.relation.HistoryWithSong
import com.snowdango.violet.presenter.common.mapper.convert
import com.snowdango.violet.presenter.history.domain.HistorySongViewData

fun HistoryWithSong.convert(): HistorySongViewData {
    return HistorySongViewData(
        id = history.id,
        songId = song?.id ?: -1L,
        title = song?.title ?: "",
        artwork = song?.thumbnailUrl ?: "",
        platform = history.platform.convert()
    )
}
