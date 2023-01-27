package com.snowdango.violet.presenter.fragment.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.model.data.GetSongAllMetaModel
import com.snowdango.violet.presenter.dialog.SongDetailDialog
import com.snowdango.violet.presenter.fragment.history.view.EmptyHistoryListView
import com.snowdango.violet.presenter.fragment.history.view.HistoryLongClickMenu
import com.snowdango.violet.presenter.fragment.history.view.NotEmptyHistoryListView
import com.snowdango.violet.repository.datastore.LastSongDataStore
import com.snowdango.violet.view.component.GridAfterSaveSongComponent
import com.snowdango.violet.view.component.GridSongComponent
import com.snowdango.violet.view.view.RefreshBox
import com.snowdango.violet.viewmodel.history.HistoryViewModel
import timber.log.Timber

@Composable
fun HistoryScreen(dataStore: LastSongDataStore, twitterPushTweet: (lastSong: LastSong) -> Unit) {
    val viewModel = viewModel<HistoryViewModel>()

    val songHistoryItems = viewModel.songHistoryFlow.collectAsLazyPagingItems()
    val songAllMetaState =
        viewModel.songAllMetaFlow.collectAsState(GetSongAllMetaModel.SongAllMetaState.None)
    val lastSongItems = dataStore.flowLastSong().collectAsState(listOf())

    RefreshBox(
        onRefresh = {
            songHistoryItems.refresh()
            viewModel.removeFilter()
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        onFinish = { songHistoryItems.loadState.refresh != LoadState.Loading },
    ) {
        Timber.d("songHistoryItems.itemSnapshotList.size: ${songHistoryItems.itemSnapshotList.size}")
        if (songHistoryItems.itemSnapshotList.isNotEmpty()) {
            NotEmptyHistoryListView(viewModel, songHistoryItems, lastSongItems) {
                twitterPushTweet(it)
            }
        } else {
            EmptyHistoryListView(viewModel, songHistoryItems, lastSongItems) {
                twitterPushTweet(it)
            }
        }
    }
    when (songAllMetaState.value) {
        is GetSongAllMetaModel.SongAllMetaState.Success -> {
            SongDetailDialog(
                (songAllMetaState.value as GetSongAllMetaModel.SongAllMetaState.Success).songAllMeta
            ) {
                viewModel.purgeSongAllMeta()
            }
        }
        else -> {}
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotEmptyHistoryListComponent(viewModel: HistoryViewModel, dataStore: LastSongDataStore) {
    val songHistoryItems = viewModel.songHistoryFlow.collectAsLazyPagingItems()
    val lastSongItems = dataStore.flowLastSong().collectAsState(listOf())

    var isDialogShow by remember { mutableStateOf(false) }
    var isMenuShow by remember { mutableStateOf(false) }
    val filterIds = remember { viewModel.filterHistoryIds }
    var selectHistoryId by remember { mutableStateOf(-1L) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center
        ) {
            // NowPlaying
            if (lastSongItems.value.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    // LastSongComponent(lastSongItems.value)
                }
            }
            // history
            items(
                songHistoryItems.itemSnapshotList,
                key = { it?.history?.id!! }
            ) { songHistory ->
                if (songHistory?.song != null && filterIds.contains(songHistory.history.id)) {
                    Box(modifier = Modifier.animateItemPlacement()) {
                        GridSongComponent(
                            songHistory.song!!,
                            songHistory.history.platform,
                            onClick = {
                                viewModel.loadSongAllMeta(it)
                                isDialogShow = true
                            },
                            onLongClick = {
                                selectHistoryId = songHistory.history.id
                                isMenuShow = true
                            }
                        )
                        HistoryLongClickMenu(
                            isMenuShow,
                            selectHistoryId,
                            songHistory,
                            onDismiss = {
                                selectHistoryId = -1L
                                isMenuShow = false
                            },
                            deleteFn = {
                                viewModel.removeHistory(songHistory.history.id)
                                selectHistoryId = -1L
                                isMenuShow = false
                            }
                        )
                    }
                } else {
                    GridAfterSaveSongComponent(songHistory?.history?.platform!!)
                }
            }
        }
    }
}

