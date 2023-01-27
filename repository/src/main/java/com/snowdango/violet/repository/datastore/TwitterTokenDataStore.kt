package com.snowdango.violet.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.snowdango.violet.domain.token.TwitterToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent

class TwitterTokenDataStore(private val dataStore: DataStore<Preferences>) : KoinComponent {

    private fun accessTokenKey() = stringPreferencesKey("twitter_access_token")

    private fun accessTokenSecretKey() = stringPreferencesKey("twitter_access_token_secret")

    suspend fun saveTwitterToken(twitterToken: TwitterToken) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey()] = twitterToken.accessToken
            preferences[accessTokenSecretKey()] = twitterToken.accessTokenSecret
        }
    }

    suspend fun getTwitterToken(): TwitterToken {
        val preferences = dataStore.data.first()
        return TwitterToken(
            preferences[accessTokenKey()] ?: "",
            preferences[accessTokenSecretKey()] ?: ""
        )
    }

    fun flowTwitterToken() = dataStore.data.map { preferences ->
        TwitterToken(
            preferences[accessTokenKey()] ?: "",
            preferences[accessTokenSecretKey()] ?: ""
        )
    }

}