package com.snowdango.violet.presenter.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.snowdango.violet.model.data.DeleteHistoryModel
import com.snowdango.violet.model.data.GetSongAllMetaModel
import com.snowdango.violet.model.paging.SongHistoryPagingModel
import com.snowdango.violet.presenter.common.mapper.convert
import com.snowdango.violet.presenter.history.domain.SongDetailViewData
import com.snowdango.violet.presenter.history.mapper.convert
import com.snowdango.violet.repository.datastore.LastSongDataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HistoryViewModel(private val dataStore: LastSongDataStore) : ViewModel(), KoinComponent {

    private val songHistoryPagingModel: SongHistoryPagingModel by inject()
    private val getSongAllMetaModel: GetSongAllMetaModel by inject()
    private val deleteHistoryModel: DeleteHistoryModel by inject()

    private val _songDetailState: MutableStateFlow<SongDetailState> = MutableStateFlow(
        SongDetailState.None
    )
    val songDetailFlow = _songDetailState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _songDetailState.value
    )

    private val _deleteIdsState: MutableStateFlow<List<Long>> = MutableStateFlow(listOf())
    val deleteIdsFlow = _deleteIdsState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _deleteIdsState.value
    )

    val lastSongFlow = dataStore.flowLastSong().map { listLastSong ->
        listLastSong.map {
            it.convert()
        }
    }.flowOn(viewModelScope.coroutineContext)

    val songHistoryFlow = Pager(
        PagingConfig(pageSize = 100, initialLoadSize = 100)
    ) {
        songHistoryPagingModel.getSongHistoriesPagingSource()
    }.flow.map { pagingData ->
        pagingData.map {
            it.convert()
        }
    }.cachedIn(viewModelScope)

    fun loadSongAllMeta(id: Long) = viewModelScope.launch {
        _songDetailState.emit(SongDetailState.Loading)
        val response = getSongAllMetaModel.getSongAllMeta(id)
        _songDetailState.emit(
            when (response) {
                is GetSongAllMetaModel.SongAllMetaState.Success ->
                    SongDetailState.Success(response.songAllMeta.convert())

                is GetSongAllMetaModel.SongAllMetaState.Error -> SongDetailState.Error(response.throwable)
                is GetSongAllMetaModel.SongAllMetaState.None -> SongDetailState.None
                is GetSongAllMetaModel.SongAllMetaState.Loading -> SongDetailState.Loading
            }
        )
    }

    fun purgeSongAllMeta() = viewModelScope.launch {
        _songDetailState.emit(SongDetailState.None)
    }

    fun removeHistory(id: Long) = viewModelScope.launch {
        if (id == -1L) return@launch

        deleteHistoryModel.deleteHistory(id)
        val ids = mutableListOf(id)
        _deleteIdsState.value.let {
            ids.addAll(it)
        }
        _deleteIdsState.emit(ids)
    }

    sealed interface SongDetailState {
        object None : SongDetailState
        object Loading : SongDetailState
        data class Success(val songDetailViewData: SongDetailViewData) : SongDetailState
        data class Error(val throwable: Throwable) : SongDetailState
    }

}