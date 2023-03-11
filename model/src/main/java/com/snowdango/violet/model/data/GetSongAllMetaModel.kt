package com.snowdango.violet.model.data

import com.snowdango.violet.domain.relation.SongAllMeta
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.song.GetSong
import org.koin.core.component.KoinComponent
import timber.log.Timber

class GetSongAllMetaModel(private val db: SongHistoryDatabase) : KoinComponent {

    suspend fun getSongAllMeta(id: Long): SongAllMetaState {
        return try {
            val getSong = GetSong(db)
            val result = getSong.getSongAllMeta(id)
            if (result != null) {
                SongAllMetaState.Success(result)
            } else {
                SongAllMetaState.Error(Exception("not date"))
            }
        } catch (e: Exception) {
            Timber.e(e.stackTraceToString())
            SongAllMetaState.Error(e)
        }
    }

    sealed class SongAllMetaState {
        object None : SongAllMetaState()
        object Loading : SongAllMetaState()
        data class Success(val songAllMeta: SongAllMeta) : SongAllMetaState()
        data class Error(val throwable: Throwable) : SongAllMetaState()
    }


}