package com.snowdango.violet.presenter.fragment.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.violet.view.component.SongComponent
import com.snowdango.violet.viewmodel.history.HistoryViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
    val songHistoryItems = viewModel.songHistoryFlow.collectAsLazyPagingItems()
    var refreshing by remember { mutableStateOf(false) }
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            songHistoryItems.refresh()
        }
    )
    Box(modifier = Modifier.pullRefresh(state = state)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth(0.86f)
                    .fillMaxHeight()
            ) {
                if (songHistoryItems.loadState.refresh != LoadState.Loading) {
                    refreshing = false
                    items(songHistoryItems.itemSnapshotList) { songHistory ->
                        Timber.d(songHistory.toString())
                        songHistory?.song?.let {
                            SongComponent(it, songHistory.platform)
                        }
                    }
                }
            }

        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }
}

