package com.snowdango.violet.repository.api.provide.endpoint

import com.snowdango.violet.domain.response.songlink.SongApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SongLinkApi {

    @GET("links")
    fun getSongLink(
        @Query("platform") platform: String,
        @Query("userCountry") userCountry: String,
        @Query("id") id: String,
        @Query("type") type: String
    ): Call<SongApiResponse>

}