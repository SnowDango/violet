package com.snowdango.violet.usecase.datastore

import com.snowdango.violet.domain.token.TwitterToken
import com.snowdango.violet.repository.datastore.TwitterTokenDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class WriteTwitterToken(private val dataStore: TwitterTokenDataStore) : KoinComponent {

    suspend fun writeTwitterToken(twitterToken: TwitterToken) = withContext(Dispatchers.IO) {
        dataStore.saveTwitterToken(twitterToken)
    }


}