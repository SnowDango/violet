package com.snowdango.violet.domain.response

data class LinksByPlatform(
    val amazonMusic: SongLinkPlatform?,
    val amazonStore: SongLinkPlatform?,
    val appleMusic: SongLinkPlatform?,
    val deezer: SongLinkPlatform?,
    val google: SongLinkPlatform?,
    val googleStore: SongLinkPlatform?,
    val itunes: SongLinkPlatform?,
    val napster: SongLinkPlatform?,
    val pandora: SongLinkPlatform?,
    val spotify: SongLinkPlatform?,
    val tidal: SongLinkPlatform?,
    val yandex: SongLinkPlatform?,
    val youtube: SongLinkPlatform?,
    val youtubeMusic: SongLinkPlatform?
)