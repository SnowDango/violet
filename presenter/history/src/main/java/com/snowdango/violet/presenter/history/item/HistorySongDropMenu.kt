package com.snowdango.violet.presenter.history.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.snowdango.violet.presenter.common.R

@Composable
fun HistorySongDropMenu(
    isMenuShow: Boolean,
    onDismissRequest: (() -> Unit),
    onClick: ((HistorySongDropMenuAction) -> Unit)
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
                Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.onSurface)
            },
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.surface),
            onClick = {
                onClick.invoke(HistorySongDropMenuAction.DELETE)
            }
        )
    }
}

enum class HistorySongDropMenuAction {
    DELETE
}