package com.snowdango.violet.presenter.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.violet.presenter.common.R
import com.snowdango.violet.presenter.common.component.EmptyAndRefreshComponent
import com.snowdango.violet.presenter.common.component.LastSongComponent
import com.snowdango.violet.presenter.common.domain.LastSongViewData
import com.snowdango.violet.presenter.common.view.RefreshBox
import com.snowdango.violet.presenter.history.dialog.SongDetailDialog
import com.snowdango.violet.presenter.history.domain.HistorySongViewData
import com.snowdango.violet.presenter.history.item.HistorySongDropMenuAction
import com.snowdango.violet.presenter.history.item.SongHistoryItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = koinViewModel<HistoryViewModel>()) {

    val songHistoryItems = viewModel.songHistoryFlow.collectAsLazyPagingItems()
    val lastSongItems = viewModel.lastSongFlow.collectAsState(listOf())
    val songDetailState = viewModel.songDetailFlow.collectAsState()

    RefreshBox(
        onRefresh = {
            songHistoryItems.refresh()
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        onFinish = {
            songHistoryItems.loadState.refresh != LoadState.Loading
        }
    ) {
        if (songHistoryItems.itemSnapshotList.isNotEmpty()) {
            HistoryNotEmptyScreen(lastSongItems, songHistoryItems, viewModel)
        } else {
            HistoryEmptyScreen(lastSongItems, songHistoryItems)
        }
    }
    when (songDetailState.value) {
        is HistoryViewModel.SongDetailState.Success -> {
            SongDetailDialog(
                (songDetailState.value as HistoryViewModel.SongDetailState.Success).songDetailViewData
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
    lastSongItems: State<List<LastSongViewData>>,
    songHistoryItems: LazyPagingItems<HistorySongViewData>,
    viewModel: HistoryViewModel
) {
    val scrollState = rememberLazyGridState()
    val deleteIds = viewModel.deleteIdsFlow.collectAsState()

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
                songHistoryItems.itemSnapshotList.filter { !(deleteIds.value.contains(it?.id)) },
                key = { it?.id!! }
            ) { songHistory ->
                SongHistoryItem(
                    songHistory!!,
                    Modifier.animateItemPlacement(),
                    onClick = { viewModel.loadSongAllMeta(it) },
                    onClickAction = {
                        when (it) {
                            HistorySongDropMenuAction.DELETE -> viewModel.removeHistory(songHistory.id)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HistoryEmptyScreen(
    lastSongItems: State<List<LastSongViewData>>,
    songHistoryItems: LazyPagingItems<HistorySongViewData>
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
                stringResource(R.string.not_found_history),
                { songHistoryItems.refresh() },
                Modifier.fillMaxSize(),
                Alignment.Center
            )
        }
    }
}