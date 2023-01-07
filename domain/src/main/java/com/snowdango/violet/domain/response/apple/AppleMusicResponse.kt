package com.snowdango.violet.domain.response.apple

data class AppleMusicResponse(
    val resultCount: Int,
    val results: List<AppleMusicSongResult>
)