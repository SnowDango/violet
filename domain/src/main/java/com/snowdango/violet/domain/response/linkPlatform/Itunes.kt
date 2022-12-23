package com.snowdango.violet.domain.response.linkPlatform

data class Itunes(
    val entityUniqueId: String,
    val nativeAppUriDesktop: String,
    val nativeAppUriMobile: String,
    val url: String
)