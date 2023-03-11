package com.snowdango.violet.model.data

import com.snowdango.violet.domain.relation.AlbumAllMeta
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.album.GetAlbum
import org.koin.core.component.KoinComponent
import timber.log.Timber

class GetAlbumAllMetaModel(private val db: SongHistoryDatabase) : KoinComponent {

    suspend fun getAlbumAllMeta(id: Long): AlbumAllMetaState {
        return try {
            val getAlbum = GetAlbum(db)
            val result = getAlbum.getAlbumAllMeta(id)
            if (result != null) {
                AlbumAllMetaState.Success(result)
            } else {
                AlbumAllMetaState.Error(Exception("not data"))
            }
        } catch (e: Exception) {
            Timber.e(e.stackTraceToString())
            AlbumAllMetaState.Error(e)
        }
    }

    sealed class AlbumAllMetaState {
        object None : AlbumAllMetaState()
        object Loading : AlbumAllMetaState()
        data class Success(val albumAllMeta: AlbumAllMeta) : AlbumAllMetaState()
        data class Error(val throwable: Throwable) : AlbumAllMetaState()
    }
}