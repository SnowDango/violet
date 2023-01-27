package com.snowdango.violet.presenter.fragment.history.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.relation.HistoryWithSong
import com.snowdango.violet.domain.token.TwitterToken
import com.snowdango.violet.view.component.GridAfterSaveSongComponent
import com.snowdango.violet.view.component.GridSongComponent
import com.snowdango.violet.view.component.LastSongComponent
import com.snowdango.violet.viewmodel.history.HistoryViewModel
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotEmptyHistoryListView(
    viewModel: HistoryViewModel,
    songHistoryItems: LazyPagingItems<HistoryWithSong>,
    lastSongItems: State<List<LastSong>>,
    pushTwitter: (lastSong: LastSong) -> Unit
) {
    var isDialogShow by remember { mutableStateOf(false) }
    var isMenuShow by remember { mutableStateOf(false) }
    val filterIds = remember { viewModel.filterHistoryIds }
    var selectHistoryId by remember { mutableStateOf(-1L) }

    val twitterTokenState = viewModel.twitterTokenFlow.collectAsState(TwitterToken("", ""))

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
                    LastSongComponent(lastSongItems.value, twitterTokenState.value) {
                        pushTwitter(it)
                    }
                }
            }
            // history
            items(
                songHistoryItems.itemSnapshotList,
                key = { it?.history?.id!! }
            ) { songHistory ->
                Timber.d("songHistory: $songHistory")
                if (songHistory?.song != null) {
                    if (!filterIds.contains(songHistory.history.id)) {
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
                    }
                } else {
                    GridAfterSaveSongComponent(songHistory?.history?.platform!!)
                }
            }
        }
    }
}