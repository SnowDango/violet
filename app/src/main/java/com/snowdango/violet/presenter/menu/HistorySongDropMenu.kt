package com.snowdango.violet.presenter.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowdango.violet.viewmodel.history.HistoryViewModel

@Composable
fun HistorySongDropMenu(
    historyId: Long,
    viewModel: HistoryViewModel,
    isMenuShow: Boolean,
    onDismissRequest: (() -> Unit)
) {
    DropdownMenu(
        expanded = isMenuShow,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        DropdownMenuItem(
            text = {
                Text("Delete", color = MaterialTheme.colorScheme.onSurface)
            },
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.surface),
            onClick = {
                viewModel.removeHistory(historyId)
                onDismissRequest.invoke()
            }
        )
    }
}