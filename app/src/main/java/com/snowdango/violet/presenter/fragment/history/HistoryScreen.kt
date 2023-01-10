package com.snowdango.violet.presenter.fragment.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.relation.HistoryWithSong
import com.snowdango.violet.model.data.GetSongAllMetaModel
import com.snowdango.violet.presenter.dialog.SongDetailDialog
import com.snowdango.violet.repository.datastore.LastSongDataStore
import com.snowdango.violet.view.component.EmptyAndRefreshComponent
import com.snowdango.violet.view.component.GridAfterSaveSongComponent
import com.snowdango.violet.view.component.GridSongComponent
import com.snowdango.violet.view.component.LastSongComponent
import com.snowdango.violet.viewmodel.history.HistoryViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(dataStore: LastSongDataStore) {
    val viewModel = viewModel<HistoryViewModel>()
    val songHistoryItems = viewModel.songHistoryFlow.collectAsLazyPagingItems()
    val lastSongItems = dataStore.flowLastSong().collectAsState(listOf())
    val songAllMetaState =
        viewModel.songAllMetaFlow.collectAsState(GetSongAllMetaModel.SongAllMetaState.None)
    var refreshing by remember { mutableStateOf(false) }
    var isDialogShow by remember { mutableStateOf(false) }

    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            songHistoryItems.refresh()
        }
    )

    Box(
        modifier = Modifier.pullRefresh(state = state)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (songHistoryItems.loadState.refresh != LoadState.Loading) {
                refreshing = false
                if (songHistoryItems.itemSnapshotList.isNotEmpty()) {
                    ShowHistoryWithNowPlaying(lastSongItems, songHistoryItems) {
                        viewModel.loadSongAllMeta(it)
                        isDialogShow = true
                    }
                } else {
                    ShowEmptyHistoryWithNowPlaying(lastSongItems, songHistoryItems)
                }
            }
        }
        // push refresh indicator
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

    ShowAllMetaDialog(isDialogShow, songAllMetaState.value) {
        isDialogShow = false
    }
}

@Composable
fun ShowHistoryWithNowPlaying(
    lastSongItems: State<List<LastSong>>,
    songHistoryItems: LazyPagingItems<HistoryWithSong>,
    onClick: (id: Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth(0.86f)
            .fillMaxHeight()
    ) {
        // NowPlaying
        if (lastSongItems.value.isNotEmpty()) {
            item(span = { GridItemSpan(2) }) {
                LastSongComponent(lastSongItems.value)
            }
        }
        // history
        items(songHistoryItems.itemSnapshotList) { songHistory ->
            if (songHistory?.song != null) {
                GridSongComponent(songHistory.song!!, songHistory.history.platform, onClick)
            } else {
                GridAfterSaveSongComponent(songHistory?.history?.platform!!)
            }
        }
    }
}

@Composable
fun ShowEmptyHistoryWithNowPlaying(
    lastSongItems: State<List<LastSong>>,
    songHistoryItems: LazyPagingItems<HistoryWithSong>
) {
    val viewModel = viewModel<HistoryViewModel>()
    Column(
        modifier = Modifier
            .fillMaxWidth(0.86f)
            .fillMaxHeight()
    ) {
        // NowPlaying
        if (lastSongItems.value.isNotEmpty()) {
            LastSongComponent(lastSongItems.value)
        }
        //
        EmptyAndRefreshComponent(
            "履歴がありません",
            { songHistoryItems.refresh() },
            Modifier.fillMaxSize(),
            Alignment.Center
        )
    }
}

@Composable
fun ShowAllMetaDialog(
    isShow: Boolean,
    songAllMetaState: GetSongAllMetaModel.SongAllMetaState,
    onDismiss: () -> Unit
) {
    if (isShow && songAllMetaState is GetSongAllMetaModel.SongAllMetaState.Success) {
        SongDetailDialog(songAllMetaState.songAllMeta, onDismiss = onDismiss)
    }
}


