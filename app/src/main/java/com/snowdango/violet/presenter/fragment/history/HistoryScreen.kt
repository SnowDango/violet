package com.snowdango.violet.presenter.fragment.history

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.violet.viewmodel.history.HistoryViewModel

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
    
    val songHistoryItems = viewModel.songHistoryFlow.collectAsLazyPagingItems()
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {

        items(songHistoryItems.itemSnapshotList) { songHistory ->

            Text(songHistory?.song?.title.toString())

        }
    }
}