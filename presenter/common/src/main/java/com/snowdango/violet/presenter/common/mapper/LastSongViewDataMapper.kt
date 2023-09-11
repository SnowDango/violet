package com.snowdango.violet.presenter.common.mapper

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.presenter.common.domain.LastSongViewData

fun LastSong.convert(): LastSongViewData {
    return LastSongViewData(
        artwork = artwork ?: "",
        title = title ?: "",
        artist = artist ?: "",
        album = album ?: "",
        platformType = platform.convert(),
        canShow = queueId != null && queueId != -1L && dateTime != null,
        datetime = dateTime ?: 0L
    )
}
