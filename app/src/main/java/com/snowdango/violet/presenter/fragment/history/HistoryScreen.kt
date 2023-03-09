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
import com.snowdango.violet.view.view.RefreshBox
import com.snowdango.violet.viewmodel.history.HistoryViewModel
import timber.log.Timber

@Composable
fun HistoryScreen(dataStore: LastSongDataStore) {
    val viewModel = viewModel<HistoryViewModel>()

    val songHistoryItems = viewModel.songHistoryFlow.collectAsLazyPagingItems()
    val lastSongItems = dataStore.flowLastSong().collectAsState(listOf())
    val songAllMetaState =
        viewModel.songAllMetaFlow.collectAsState(GetSongAllMetaModel.SongAllMetaState.None)

    RefreshBox(
        onRefresh = {
            songHistoryItems.refresh()
            viewModel.removeFilter()
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        onFinish = { songHistoryItems.loadState.refresh != LoadState.Loading },
    ) {
        if (songHistoryItems.itemSnapshotList.isNotEmpty()) {
            HistoryNotEmptyScreen(lastSongItems, songHistoryItems, viewModel)
        } else {
            HistoryEmptyScreen(lastSongItems, songHistoryItems)
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
fun HistoryNotEmptyScreen(
    lastSongItems: State<List<LastSong>>,
    songHistoryItems: LazyPagingItems<HistoryWithSong>,
    viewModel: HistoryViewModel
) {
    val scrollState = rememberLazyGridState()
    var isDialogShow by remember { mutableStateOf(false) }
    val filterIds = remember { viewModel.filterHistoryIds }

    Box(
        modifier = Modifier.fillMaxSize(),
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
                songHistoryItems.itemSnapshotList.filter { !filterIds.contains(it?.history?.id) },
                key = { it?.history?.id!! }
            ) { songHistory ->
                Timber.d(songHistory.toString())
                SongHistory(
                    songHistory,
                    viewModel,
                    Modifier.animateItemPlacement(),
                    onClick = {
                        viewModel.loadSongAllMeta(it)
                        isDialogShow = true
                    }
                )
            }
        }
    }
}

@Composable
fun HistoryEmptyScreen(
    lastSongItems: State<List<LastSong>>,
    songHistoryItems: LazyPagingItems<HistoryWithSong>
) {
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

@Composable
fun SongHistory(
    songHistory: HistoryWithSong?,
    viewModel: HistoryViewModel,
    modifier: Modifier,
    onClick: ((it: Long) -> Unit)? = null,
) {
    var isMenuShow: Boolean by remember { mutableStateOf(false) }
    if (songHistory?.song != null) {
        Box(modifier = modifier) {
            GridSongComponent(
                songHistory.song!!,
                songHistory.history.platform,
                onClick = {
                    onClick?.invoke(it)
                },
                onLongClick = {
                    isMenuShow = true
                }
            )
            SongDropMenu(
                songHistory.history.id,
                viewModel,
                isMenuShow,
            ) {
                isMenuShow = false
            }
        }
    } else {
        GridAfterSaveSongComponent(songHistory?.history?.platform!!)
    }
}

@Composable
fun SongDropMenu(
    historyId: Long,
    viewModel: HistoryViewModel,
    isMenuShow: Boolean,
    onDismissRequest: (() -> Unit)? = null
) {
    DropdownMenu(
        expanded = isMenuShow,
        onDismissRequest = {
            onDismissRequest?.invoke()
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
                viewModel.removeHistory(historyId)
                onDismissRequest?.invoke()
            }
        )
    }
}


