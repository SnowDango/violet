package com.snowdango.violet.presenter.fragment.history.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.relation.HistoryWithSong
import com.snowdango.violet.domain.token.TwitterToken
import com.snowdango.violet.view.component.EmptyAndRefreshComponent
import com.snowdango.violet.view.component.LastSongComponent
import com.snowdango.violet.viewmodel.history.HistoryViewModel

@Composable
fun EmptyHistoryListView(
    viewModel: HistoryViewModel,
    songHistoryItems: LazyPagingItems<HistoryWithSong>,
    lastSongItems: State<List<LastSong>>,
    pushTwitter: (lastSong: LastSong) -> Unit
) {

    val twitterTokenState = viewModel.twitterTokenFlow.collectAsState(TwitterToken("", ""))

    Column(
        modifier = Modifier
            .fillMaxWidth(0.86f)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // NowPlaying
        if (lastSongItems.value.isNotEmpty()) {
            LastSongComponent(lastSongItems.value, twitterTokenState.value) {
                pushTwitter(it)
            }
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