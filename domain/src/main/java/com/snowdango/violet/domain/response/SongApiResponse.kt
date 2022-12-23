package com.snowdango.violet.domain.response

data class SongApiResponse(
    val entityUniqueId: String,
    val linksByPlatform: LinksByPlatform,
    val pageUrl: String,
    val userCountry: String
)