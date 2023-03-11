package com.snowdango.violet.viewmodel.album_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.violet.model.data.GetAlbumAllMetaModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlbumDetailViewModel : ViewModel(), KoinComponent {

    private val getAlbumAllMetaModel: GetAlbumAllMetaModel by inject()

    var albumId: Long = 0

    private val _albumAllMetaState: MutableSharedFlow<GetAlbumAllMetaModel.AlbumAllMetaState> = MutableSharedFlow()
    val albumAllMetaFlow: SharedFlow<GetAlbumAllMetaModel.AlbumAllMetaState> = _albumAllMetaState

    fun loadAlbumAllMeta() = viewModelScope.launch {
        _albumAllMetaState.emit(GetAlbumAllMetaModel.AlbumAllMetaState.Loading)
        _albumAllMetaState.emit(getAlbumAllMetaModel.getAlbumAllMeta(albumId))
    }


}