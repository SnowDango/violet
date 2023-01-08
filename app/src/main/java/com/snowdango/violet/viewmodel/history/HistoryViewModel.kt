package com.snowdango.violet.viewmodel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.snowdango.violet.model.paging.SongHistoryModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HistoryViewModel : ViewModel(), KoinComponent {

    private val songHistoryModel: SongHistoryModel by inject()

    val songHistoryFlow = Pager(
        PagingConfig(pageSize = 100, initialLoadSize = 100)
    ) {
        songHistoryModel.getSongHistoriesPagingSource()
    }.flow.cachedIn(viewModelScope)


}