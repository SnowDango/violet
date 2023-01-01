package com.snowdango.violet.model

import android.content.Context
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.repository.api.ApiRepository
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.datastore.CheckLastSong
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class SaveSongHistoryModel(private val context: Context) : KoinComponent {

    private val db: SongHistoryDatabase by inject()
    private val apiRepository: ApiRepository by inject()

    suspend fun saveSongHistory(data: LastSong) {

        val checkLastSong = CheckLastSong(context)
        checkLastSong.checkLastSong(data, object : CheckLastSong.Callback {
            override suspend fun isDifferent() {
                getSongData(data)
            }
        })
    }

    private suspend fun getSongData(data: LastSong) {
        data.platform?.songLink?.let { platform ->
            data.mediaId?.let { mediaId ->
                val response = apiRepository.getSongLink(platform, id = mediaId, type = "song")
                Timber.d(response.toString())
                Timber.d(data.toString())
            }
        }
    }

}