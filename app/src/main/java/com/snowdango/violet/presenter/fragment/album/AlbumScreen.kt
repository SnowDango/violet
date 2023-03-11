package com.snowdango.violet.presenter.fragment.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.violet.R
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.view.component.EmptyAndRefreshComponent
import com.snowdango.violet.view.component.GridAlbumComponent
import com.snowdango.violet.view.view.OnCombinedClickListener
import com.snowdango.violet.view.view.RefreshBox
import com.snowdango.violet.viewmodel.album.AlbumViewModel

@Composable
fun AlbumScreen(
    onAlbumDetailNavigate: (albumId: Long) -> Unit
) {
    val viewModel = viewModel<AlbumViewModel>()

    val albumItems = viewModel.albumsFlow.collectAsLazyPagingItems()

    RefreshBox(
        onRefresh = {
            albumItems.refresh()
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        onFinish = { albumItems.loadState.refresh != LoadState.Loading }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            if (albumItems.itemSnapshotList.isNotEmpty()) {
                LazyAlbumsView(
                    albumItems.itemSnapshotList,
                    onAlbumDetailNavigate
                )
            } else {
                EmptyAndRefreshComponent(
                    stringResource(R.string.not_found_history),
                    { albumItems.refresh() },
                    Modifier.fillMaxSize(),
                    Alignment.Center
                )
            }
        }
    }
}

@Composable
fun LazyAlbumsView(
    albums: ItemSnapshotList<Album>,
    onAlbumDetailNavigate: (albumId: Long) -> Unit
) {
    val scrollState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth(0.86f)
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        state = scrollState
    ) {
        items(
            albums,
            key = { it?.id!! }
        ) { album ->
            GridAlbumComponent(
                album = album!!,
                object : OnCombinedClickListener {
                    override fun onClick() {
                        onAlbumDetailNavigate(album.id)
                    }
                }
            )
        }
    }
}