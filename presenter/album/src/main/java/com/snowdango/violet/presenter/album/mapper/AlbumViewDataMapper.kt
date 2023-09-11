package com.snowdango.violet.presenter.album.mapper

import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.presenter.album.domain.AlbumViewData

fun Album.convert(): AlbumViewData {
    return AlbumViewData(
        id = id,
        title = title,
        artwork = thumbnailUrl
    )
}