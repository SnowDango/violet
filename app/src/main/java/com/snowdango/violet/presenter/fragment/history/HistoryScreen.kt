package com.snowdango.violet.presenter.fragment.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.violet.model.data.GetSongAllMetaModel
import com.snowdango.violet.presenter.dialog.SongDetailDialog
import com.snowdango.violet.repository.datastore.LastSongDataStore
import com.snowdango.violet.view.component.EmptyAndRefreshComponent
import com.snowdango.violet.view.component.GridAfterSaveSongComponent
import com.snowdango.violet.view.component.GridSongComponent
import com.snowdango.violet.view.component.LastSongComponent
import com.snowdango.violet.view.view.RefreshBox
import com.snowdango.violet.viewmodel.history.HistoryViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(dataStore: LastSongDataStore) {
    val viewModel = viewModel<HistoryViewModel>()

    val songHistoryItems = viewModel.songHistoryFlow.collectAsLazyPagingItems()
    val lastSongItems = dataStore.flowLastSong().collectAsState(listOf())
    val songAllMetaState =
        viewModel.songAllMetaFlow.collectAsState(GetSongAllMetaModel.SongAllMetaState.None)

    val scrollState = rememberLazyGridState()
    var isDialogShow by remember { mutableStateOf(false) }
    var isMenuShow by remember { mutableStateOf(false) }
    var selectHistoryId by remember { mutableStateOf(-1L) }

    RefreshBox(
        onRefresh = { songHistoryItems.refresh() },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        onFinish = { songHistoryItems.loadState.refresh != LoadState.Loading },
    ) {
        if (songHistoryItems.itemSnapshotList.isNotEmpty()) {

            Box(
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth(0.86f)
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    state = scrollState,
                ) {
                    // NowPlaying
                    if (lastSongItems.value.isNotEmpty()) {
                        item(span = { GridItemSpan(2) }) {
                            LastSongComponent(lastSongItems.value)
                        }
                    }
                    // history
                    items(
                        songHistoryItems.itemSnapshotList,
                        key = { it?.history?.id!! }
                    ) { songHistory ->
                        if (songHistory?.song != null) {
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
                                DropdownMenu(
                                    expanded = isMenuShow && selectHistoryId == songHistory.history.id,
                                    onDismissRequest = {
                                        selectHistoryId = -1L
                                        isMenuShow = false
                                    },
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .wrapContentWidth()
                                        .background(MaterialTheme.colorScheme.surface)
                                ) {
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                "Delete",
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        },
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .wrapContentHeight()
                                            .background(MaterialTheme.colorScheme.surface),
                                        onClick = {
                                            viewModel.removeHistory(songHistory.history.id)
                                            songHistoryItems.refresh()
                                            selectHistoryId = -1L
                                            isMenuShow = false
                                        }
                                    )
                                }
                            }
                        } else {
                            GridAfterSaveSongComponent(songHistory?.history?.platform!!)
                        }
                    }
                }
            }

        } else {

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.86f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // NowPlaying
                if (lastSongItems.value.isNotEmpty()) {
                    LastSongComponent(lastSongItems.value)
                }
                // empty view
                if (songHistoryItems.loadState.prepend == LoadState.NotLoading(true)) {
                    EmptyAndRefreshComponent(
                        "履歴がありません",
                        { songHistoryItems.refresh() },
                        Modifier.fillMaxSize(),
                        Alignment.Center
                    )
                }
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

