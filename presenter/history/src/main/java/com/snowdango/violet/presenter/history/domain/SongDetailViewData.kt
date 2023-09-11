package com.snowdango.violet.presenter.history.domain

data class SongDetailViewData(
    val title: String,
    val artwork: String,
    val artist: String,
    val album: String,
    val albumArtist: String,
    val genre: String,
    val platform: List<Platform>
)