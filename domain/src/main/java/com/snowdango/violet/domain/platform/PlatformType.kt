package com.snowdango.violet.domain.platform

enum class PlatformType(
    val packageName: String,
    val songLink: String,
    val iconAssets: String,
    val songLinkEntityString: String
) {
    AppleMusic("com.apple.android.music", "appleMusic", "apple_music", "ITUNES_SONG"),
    Spotify("com.spotify.music", "spotify", "spotify", "SPOTIFY_SONG"),
}