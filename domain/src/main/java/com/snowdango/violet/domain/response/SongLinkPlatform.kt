package com.snowdango.violet.domain.response

data class SongLinkPlatform(
    val entityUniqueId: String,
    val url: String,
    val nativeAppUriDesktop: String?,
    val nativeAppUriMobile: String?,
)