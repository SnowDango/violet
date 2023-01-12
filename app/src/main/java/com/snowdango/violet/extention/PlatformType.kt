package com.snowdango.violet.extention

import android.net.Uri
import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.platform.PlatformType


fun Platform.mobileSchemeUri(): Uri? {
    if (this.url == null) return null

    return when (this.platform) {
        PlatformType.AppleMusic -> {
            val uri = Uri.parse(this.url)
            return Uri.Builder()
                .scheme("apple-music")
                .authority("itunes.apple.com")
                .path(uri.path)
                .query(uri.query)
                .build()
        }
        PlatformType.Spotify -> {
            val uri = Uri.parse(this.url)
            return Uri.Builder()
                .scheme("spotify")
                .authority("track")
                .path(uri.path?.removePrefix("/track/"))
                .build()
        }
        else -> null
    }
}