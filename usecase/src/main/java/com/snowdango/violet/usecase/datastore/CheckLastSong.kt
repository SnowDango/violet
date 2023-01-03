package com.snowdango.violet.usecase.datastore

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.memory.InMemoryStore
import com.snowdango.violet.repository.datastore.LastSongDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class CheckLastSong : KoinComponent {

    private val mutex = Mutex()
    private val datastore = LastSongDataStore()
    private val inMemoryStore: InMemoryStore by inject()

    suspend fun checkLastSong(lastSong: LastSong): Boolean = withContext(Dispatchers.IO) {
        mutex.withLock {
            var isDifferent = false
            if (inMemoryStore.lastSong == null) {
                inMemoryStore.lastSong = datastore.getLastSong()
            }
            isDifferent = inMemoryStore.lastSong?.queueId != lastSong.queueId
            if (isDifferent) {
                Timber.d(
                    """
                    new song -> $lastSong
                    memory -> ${inMemoryStore.lastSong}
                    datastore -> ${datastore.getLastSong()}
                """.trimIndent()
                )
                inMemoryStore.lastSong = lastSong
                datastore.saveLastSong(lastSong)
            }
            return@withContext isDifferent
        }
    }

}