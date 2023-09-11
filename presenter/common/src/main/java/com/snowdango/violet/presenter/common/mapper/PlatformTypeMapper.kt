package com.snowdango.violet.presenter.common.mapper

import com.snowdango.violet.domain.platform.PlatformType

fun PlatformType?.convert(): com.snowdango.violet.presenter.common.domain.PlatformType {
    return when (this) {
        PlatformType.AppleMusic -> com.snowdango.violet.presenter.common.domain.PlatformType.APPLE_MUSIC
        PlatformType.Spotify -> com.snowdango.violet.presenter.common.domain.PlatformType.SPOTIFY
        null -> com.snowdango.violet.presenter.common.domain.PlatformType.UNKNOWN
    }
}
