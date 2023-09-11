package com.snowdango.violet.presenter.common.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RefreshBox(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    onFinish: () -> Boolean,
    contextAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {

    var refreshing by remember { mutableStateOf(false) }

    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            onRefresh()
            refreshing = true
        }
    )

    Box(
        modifier = modifier.pullRefresh(state = state),
        contentAlignment = contextAlignment
    ) {
        if (onFinish()) {
            refreshing = false
        }
        content()

        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

}