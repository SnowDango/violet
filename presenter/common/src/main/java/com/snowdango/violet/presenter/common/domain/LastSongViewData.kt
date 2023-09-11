package com.snowdango.violet.presenter.common.domain

data class LastSongViewData(
    val artwork: String,
    val title: String,
    val artist: String,
    val album: String,
    val platformType: PlatformType,
    val canShow: Boolean,
    val datetime: Long
)
