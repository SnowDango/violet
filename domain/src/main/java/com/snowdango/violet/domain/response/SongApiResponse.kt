package com.snowdango.violet.domain.response

data class SongApiResponse(
    val entityUniqueId: String,
    val linksByPlatform: Map<String, SongLinkPlatform>,
    val pageUrl: String,
    val userCountry: String,
    val entitiesByUniqueId: Map<String, SongEntity>
)