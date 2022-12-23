package com.snowdango.violet.domain.response

import com.snowdango.violet.domain.response.linkPlatform.*

data class LinksByPlatform(
    val amazonMusic: AmazonMusic?,
    val amazonStore: AmazonStore?,
    val appleMusic: AppleMusic?,
    val deezer: Deezer?,
    val google: Google?,
    val googleStore: GoogleStore?,
    val itunes: Itunes?,
    val napster: Napster?,
    val pandora: Pandora?,
    val spotify: Spotify?,
    val tidal: Tidal?,
    val yandex: Yandex?,
    val youtube: Youtube?,
    val youtubeMusic: YoutubeMusic?
)