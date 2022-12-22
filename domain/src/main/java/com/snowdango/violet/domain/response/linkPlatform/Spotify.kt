package com.snowdango.violet.domain.response.linkPlatform

data class Spotify(
    val entityUniqueId: String,
    val nativeAppUriDesktop: String,
    val url: String
)