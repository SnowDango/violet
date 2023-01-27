package com.snowdango.violet.presenter.fragment.history.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowdango.violet.domain.relation.HistoryWithSong

@Composable
fun HistoryLongClickMenu(
    isMenuShow: Boolean,
    selectHistoryId: Long,
    songHistory: HistoryWithSong,
    onDismiss: () -> Unit,
    deleteFn: () -> Unit
) {
    DropdownMenu(
        expanded = isMenuShow && selectHistoryId == songHistory.history.id,
        onDismissRequest = {
            onDismiss()
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
                deleteFn()
            }
        )
    }
}