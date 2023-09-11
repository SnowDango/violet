package com.snowdango.violet.presenter.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.snowdango.violet.model.paging.AlbumPagingModel
import com.snowdango.violet.presenter.album.mapper.convert
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlbumViewModel : ViewModel(), KoinComponent {

    private val albumPagingModel: AlbumPagingModel by inject()

    val albumsFlow = Pager(
        PagingConfig(pageSize = 100, initialLoadSize = 100)
    ) {
        albumPagingModel.getAlbumsPagingSource()
    }.flow.map { paging ->
        paging.map {
            it.convert()
        }
    }.cachedIn(viewModelScope)

}