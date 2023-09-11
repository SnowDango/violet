package com.snowdango.violet.presenter.history.domain

import com.snowdango.violet.presenter.common.domain.PlatformType

data class HistorySongViewData(
    val id: Long,
    val songId: Long,
    val title: String,
    val artwork: String,
    val platform: PlatformType
)
