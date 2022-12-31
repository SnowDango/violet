package com.snowdango.violet.model

import android.content.Context
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.usecase.datastore.CheckLastSong
import timber.log.Timber

class SaveSongHistoryModel(private val context: Context) {

    suspend fun saveSongHistory(data: LastSong) {

        val checkLastSong = CheckLastSong(context)
        checkLastSong.checkLastSong(data) {
            Timber.d("is change song.")
        }
    }

}