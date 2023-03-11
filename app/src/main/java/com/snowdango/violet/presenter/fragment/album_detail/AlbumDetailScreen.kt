package com.snowdango.violet.presenter.fragment.album_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snowdango.violet.domain.relation.AlbumAllMeta
import com.snowdango.violet.model.data.GetAlbumAllMetaModel
import com.snowdango.violet.view.component.HeadAlbumComponent
import com.snowdango.violet.viewmodel.album_detail.AlbumDetailViewModel
import timber.log.Timber

@Composable
fun AlbumDetailScreen(id: Long) {

    val viewModel = viewModel<AlbumDetailViewModel>()
    viewModel.albumId = id

    val albumDetailState = viewModel.albumAllMetaFlow.collectAsState(GetAlbumAllMetaModel.AlbumAllMetaState.None)

    when (albumDetailState.value) {
        is GetAlbumAllMetaModel.AlbumAllMetaState.None -> {
            viewModel.loadAlbumAllMeta()
        }

        is GetAlbumAllMetaModel.AlbumAllMetaState.Success -> {
            AlbumDetailSuccessScreen(
                (albumDetailState.value as GetAlbumAllMetaModel.AlbumAllMetaState.Success).albumAllMeta
            )
        }

        else -> {}
    }

}

@Composable
fun AlbumDetailSuccessScreen(albumAllMeta: AlbumAllMeta) {
    val scrollState = rememberLazyListState()
    Timber.d(albumAllMeta.toString())
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .fillMaxHeight(),
            state = scrollState
        ) {
            item {
                HeadAlbumComponent(
                    albumAllMeta.album,
                    albumAllMeta.artist
                )
            }
        }
    }
}