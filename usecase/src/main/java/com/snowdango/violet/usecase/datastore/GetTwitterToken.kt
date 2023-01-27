package com.snowdango.violet.usecase.datastore


import com.snowdango.violet.repository.datastore.TwitterTokenDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class GetTwitterToken(private val dataStore: TwitterTokenDataStore) : KoinComponent {

    suspend fun getTwitterToken() = withContext(Dispatchers.IO) {
        dataStore.getTwitterToken()
    }
    
    fun flowTwitterToken() = dataStore.flowTwitterToken()

}