package com.snowdango.violet.viewmodel.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.snowdango.violet.model.paging.AlbumPagingModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlbumViewModel : ViewModel(), KoinComponent {

    private val albumPagingModel: AlbumPagingModel by inject()

    val albumsFlow = Pager(
        PagingConfig(pageSize = 100, initialLoadSize = 100)
    ) {
        albumPagingModel.getAlbumsPagingSource()
    }.flow.cachedIn(viewModelScope)

}