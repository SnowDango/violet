package com.snowdango.violet.viewmodel.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.snowdango.violet.model.data.DeleteHistoryModel
import com.snowdango.violet.model.data.GetSongAllMetaModel
import com.snowdango.violet.model.paging.SongHistoryPagingModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class HistoryViewModel : ViewModel(), KoinComponent {

    private val songHistoryPagingModel: SongHistoryPagingModel by inject()
    private val getSongAllMetaModel: GetSongAllMetaModel by inject()
    private val deleteHistoryModel: DeleteHistoryModel by inject()

    private val _songAllMetaState: MutableSharedFlow<GetSongAllMetaModel.SongAllMetaState> = MutableSharedFlow()
    val songAllMetaFlow: SharedFlow<GetSongAllMetaModel.SongAllMetaState> = _songAllMetaState

    private val _deleteIdsMutableLiveData: MutableLiveData<List<Long>> = MutableLiveData()
    val deleteIdsLiveData: LiveData<List<Long>> = _deleteIdsMutableLiveData

    val songHistoryFlow = Pager(
        PagingConfig(pageSize = 100, initialLoadSize = 100)
    ) {
        songHistoryPagingModel.getSongHistoriesPagingSource()
    }.flow.cachedIn(viewModelScope)

    fun loadSongAllMeta(id: Long) = viewModelScope.launch {
        _songAllMetaState.emit(GetSongAllMetaModel.SongAllMetaState.Loading)
        _songAllMetaState.emit(getSongAllMetaModel.getSongAllMeta(id))
    }

    fun purgeSongAllMeta() = viewModelScope.launch {
        _songAllMetaState.emit(GetSongAllMetaModel.SongAllMetaState.None)
    }

    fun removeHistory(id: Long) = viewModelScope.launch {
        if (id == -1L) return@launch

        deleteHistoryModel.deleteHistory(id)
        val ids = mutableListOf(id)
        _deleteIdsMutableLiveData.value?.let {
            ids.addAll(it)
        }
        _deleteIdsMutableLiveData.postValue(ids)
    }

}