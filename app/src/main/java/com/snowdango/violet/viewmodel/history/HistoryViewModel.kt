package com.snowdango.violet.viewmodel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.snowdango.violet.model.data.DeleteHistoryModel
import com.snowdango.violet.model.data.GetSongAllMetaModel
import com.snowdango.violet.model.data.TwitterTokenModel
import com.snowdango.violet.model.paging.SongHistoryModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HistoryViewModel : ViewModel(), KoinComponent {

    private val songHistoryModel: SongHistoryModel by inject()
    private val twitterTokenModel: TwitterTokenModel by inject()

    private val _songAllMetaState: MutableSharedFlow<GetSongAllMetaModel.SongAllMetaState> =
        MutableSharedFlow()
    val songAllMetaFlow: SharedFlow<GetSongAllMetaModel.SongAllMetaState> = _songAllMetaState

    val filterHistoryIds: MutableList<Long> = mutableListOf()

    val songHistoryFlow = Pager(
        PagingConfig(pageSize = 100, initialLoadSize = 100)
    ) {
        songHistoryModel.getSongHistoriesPagingSource()
    }.flow.cachedIn(viewModelScope)

    val twitterTokenFlow = twitterTokenModel.getTwitterTokenFlow()

    fun loadSongAllMeta(id: Long) = viewModelScope.launch {
        _songAllMetaState.emit(GetSongAllMetaModel.SongAllMetaState.Loading)
        val getSongAllMetaModel = GetSongAllMetaModel()
        _songAllMetaState.emit(getSongAllMetaModel.getSongAllMeta(id))
    }

    fun removeFilter() {
        filterHistoryIds.clear()
    }

    fun purgeSongAllMeta() = viewModelScope.launch {
        _songAllMetaState.emit(GetSongAllMetaModel.SongAllMetaState.None)
    }

    fun removeHistory(id: Long) = viewModelScope.launch {
        if (id == -1L) return@launch

        val deleteHistoryModel = DeleteHistoryModel()
        deleteHistoryModel.deleteHistory(id)
        filterHistoryIds.add(id)
    }

}