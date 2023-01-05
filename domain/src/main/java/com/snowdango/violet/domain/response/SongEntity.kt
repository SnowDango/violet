package com.snowdango.violet.domain.response

data class SongEntity(
    val id: String,
    val type: String,
    val title: String,
    val artistName: String,
    val thumbnailUrl: String?,
    val thumbnailWidth: Int,
    val thumbnailHeight: Int,
    val apiProvider: String,
    val platforms: List<String>
)
