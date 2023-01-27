package com.snowdango.violet.model.data

import com.snowdango.violet.domain.token.TwitterToken
import com.snowdango.violet.repository.datastore.TwitterTokenDataStore
import com.snowdango.violet.usecase.datastore.GetTwitterToken
import com.snowdango.violet.usecase.datastore.WriteTwitterToken
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TwitterTokenModel : KoinComponent {

    private val dataStore: TwitterTokenDataStore by inject()

    private val writeTwitterToken = WriteTwitterToken(dataStore)
    private val getTwitterToken = GetTwitterToken(dataStore)

    suspend fun saveTwitterToken(token: String, tokenSecret: String) {
        writeTwitterToken.writeTwitterToken(TwitterToken(token, tokenSecret))
    }

    suspend fun saveTwitterToken(twitterToken: TwitterToken) {
        writeTwitterToken.writeTwitterToken(twitterToken)
    }

    suspend fun getTwitterToken(): TwitterToken {
        return getTwitterToken.getTwitterToken()
    }

    fun getTwitterTokenFlow() = getTwitterToken.flowTwitterToken()

}