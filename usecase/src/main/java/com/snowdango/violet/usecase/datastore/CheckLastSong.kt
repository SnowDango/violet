package com.snowdango.violet.usecase.datastore

import android.content.Context
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.repository.datastore.LastSongDataStore
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CheckLastSong(private val context: Context) {

    private val mutex = Mutex()

    suspend fun checkLastSong(lastSong: LastSong, isDifferentCallback: () -> Unit) {
        var isDifferent = false
        mutex.withLock {
            val datastore = LastSongDataStore(context)
            val data = datastore.getLastSong()
            isDifferent = data.mediaId != lastSong.mediaId
            datastore.saveLastSong(lastSong)
        }
        if (isDifferent) {
            isDifferentCallback()
        }
    }

}