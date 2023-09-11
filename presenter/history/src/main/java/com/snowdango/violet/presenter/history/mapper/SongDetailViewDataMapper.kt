package com.snowdango.violet.presenter.history.mapper

import com.snowdango.violet.domain.relation.SongAllMeta
import com.snowdango.violet.presenter.common.mapper.convert
import com.snowdango.violet.presenter.common.mapper.convertMobileUri
import com.snowdango.violet.presenter.history.domain.Platform
import com.snowdango.violet.presenter.history.domain.SongDetailViewData


fun SongAllMeta.convert(): SongDetailViewData {
    return SongDetailViewData(
        title = song.title,
        artwork = song.thumbnailUrl,
        artist = artist.name,
        album = albumWithArtist.album.title,
        albumArtist = albumWithArtist.artist?.name ?: "",
        genre = song.genre,
        platform = platforms.filter {
            it.platform != null &&
                    platforms.first { platform ->
                        platform.platform?.name == it.platform?.name
                    }.id == it.id
        }.map {
            Platform(
                type = it.platform.convert(),
                mobileScheme = it.convertMobileUri()
            )
        }
    )
}