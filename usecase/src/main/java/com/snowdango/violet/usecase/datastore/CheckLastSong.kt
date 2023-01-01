package com.snowdango.violet.usecase.datastore

import android.content.Context
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.memory.InMemoryStore
import com.snowdango.violet.repository.datastore.LastSongDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CheckLastSong(private val context: Context) : KoinComponent {

    private val mutex = Mutex()
    private val datastore = LastSongDataStore(context)
    private val inMemoryStore: InMemoryStore by inject()

    suspend fun checkLastSong(lastSong: LastSong): Boolean = withContext(Dispatchers.IO) {
        var isDifferent = false
        mutex.withLock {
            if (inMemoryStore.lastSong == null) {
                inMemoryStore.lastSong = datastore.getLastSong()
            }
            isDifferent = inMemoryStore.lastSong?.queueId != lastSong.queueId
            if (isDifferent) {
                inMemoryStore.lastSong = lastSong
                datastore.saveLastSong(lastSong)
            }
            return@withContext isDifferent
        }
    }

}