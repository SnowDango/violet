package com.snowdango.violet.extention

import com.snowdango.violet.domain.platform.PlatformType

fun String.toPlatformType(): PlatformType {
    return when (this) {
        PlatformType.AppleMusic.name -> PlatformType.AppleMusic
        PlatformType.Spotify.name -> PlatformType.Spotify
        else -> PlatformType.Spotify
    }
}