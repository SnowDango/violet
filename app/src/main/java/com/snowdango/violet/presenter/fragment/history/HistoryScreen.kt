package com.snowdango.violet.presenter.fragment.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.violet.view.component.SongComponent
import com.snowdango.violet.viewmodel.history.HistoryViewModel
import timber.log.Timber

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {

    val songHistoryItems = viewModel.songHistoryFlow.collectAsLazyPagingItems()

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

            items(songHistoryItems.itemSnapshotList) { songHistory ->

                Timber.d(songHistory.toString())

                songHistory?.song?.let {
                    SongComponent(it, songHistory.platform)
                }

            }
        }

    }
}