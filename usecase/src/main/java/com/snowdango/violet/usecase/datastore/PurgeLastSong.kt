package com.snowdango.violet.usecase.datastore

import com.snowdango.violet.domain.memory.InMemoryStore
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.repository.datastore.LastSongDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PurgeLastSong : KoinComponent {

    private val datastore: LastSongDataStore by inject()
    private val inMemoryStore: InMemoryStore by inject()

    suspend fun purgeLastSong(platformType: PlatformType) = withContext(Dispatchers.IO) {
        inMemoryStore.lastSong.remove(platformType.name)
        datastore.purgeLastSong(platformType)
    }
}