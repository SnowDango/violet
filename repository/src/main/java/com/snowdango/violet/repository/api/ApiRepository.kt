package com.snowdango.violet.repository.api

import com.snowdango.violet.domain.response.apple.AppleMusicResponse
import com.snowdango.violet.domain.response.songlink.SongApiResponse
import com.snowdango.violet.repository.api.provide.ApiProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection

class ApiRepository(private val apiProvider: ApiProvider) {

    suspend fun getSongLink(
        platform: String,
        userCountry: String = "JP",
        id: String,
        type: String
    ): SongApiResponse? = withContext(Dispatchers.Default) {
        val response = apiProvider.songLinkApi.getSongLink(
            platform, userCountry, id, type
        ).execute()
        return@withContext when (response.code()) {
            HttpURLConnection.HTTP_OK -> response.body()
            else -> null
        }
    }

    suspend fun getAppleMusic(
        id: String,
        entity: String = "song",
        country: String = "JP"
    ): AppleMusicResponse? = withContext(Dispatchers.Default) {
        val response = apiProvider.appleMusicApi.getAppleMusic(
            id, entity, country
        ).execute()
        return@withContext when (response.code()) {
            HttpURLConnection.HTTP_OK -> response.body()
            else -> null
        }
    }

}