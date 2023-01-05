package com.snowdango.violet.usecase.datastore

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.memory.InMemoryStore
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.repository.datastore.LastSongDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class CheckLastSong : KoinComponent {

    private val datastore: LastSongDataStore by inject()
    private val inMemoryStore: InMemoryStore by inject()

    suspend fun checkLastSong(lastSong: LastSong, platformType: PlatformType): Boolean =
        withContext(Dispatchers.IO) {
            inMemoryStore.mutex.withLock {
                var isDifferent = false
                if (!inMemoryStore.lastSong.containsKey(platformType.name)) {
                    inMemoryStore.lastSong[platformType.name] = datastore.getLastSong(platformType)
                }
                isDifferent = inMemoryStore.lastSong[platformType.name]?.queueId != lastSong.queueId
                if (isDifferent) {
                    Timber.d(
                        """
                    new song -> $lastSong
                    memory -> ${inMemoryStore.lastSong}
                    datastore -> ${datastore.getLastSong(platformType)}
                """.trimIndent()
                    )
                    inMemoryStore.lastSong[platformType.name] = lastSong
                    datastore.saveLastSong(lastSong, platformType)
                } else {
                    inMemoryStore.lastSong[platformType.name]?.artwork = lastSong.artwork
                    datastore.updateArtwork(lastSong.artwork, platformType)
                }
                return@withContext isDifferent
            }
        }

}