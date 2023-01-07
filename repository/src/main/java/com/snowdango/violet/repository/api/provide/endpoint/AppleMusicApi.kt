package com.snowdango.violet.repository.api.provide.endpoint

import com.snowdango.violet.domain.response.apple.AppleMusicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AppleMusicApi {

    @GET("lookup")
    fun getAppleMusic(
        @Query("id") id: String,
        @Query("entity") entity: String,
        @Query("country") country: String
    ): Call<AppleMusicResponse>

}