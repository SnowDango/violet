package com.snowdango.violet.domain.last

import com.snowdango.violet.domain.platform.PlatformType

data class LastSong(
    var mediaId: String?,
    var title: String?,
    var artist: String?,
    var album: String?,
    var albumArtist: String?,
    var platform: PlatformType?,
    var queueId: Long?,
    var genre: String?
)
