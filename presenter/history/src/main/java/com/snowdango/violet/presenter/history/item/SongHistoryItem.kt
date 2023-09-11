package com.snowdango.violet.presenter.history.item

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.snowdango.violet.presenter.common.component.GridSongComponent
import com.snowdango.violet.presenter.common.view.OnCombinedClickListener
import com.snowdango.violet.presenter.history.domain.HistorySongViewData

@Composable
fun SongHistoryItem(
    historySong: HistorySongViewData,
    modifier: Modifier,
    onClick: ((it: Long) -> Unit),
    onClickAction: ((it: HistorySongDropMenuAction) -> Unit)
) {
    var isMenuShow: Boolean by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        GridSongComponent(
            historySong.title,
            historySong.artwork,
            historySong.platform,
            object : OnCombinedClickListener {
                override fun onClick() {
                    onClick(historySong.songId)
                }

                override fun onLongClick() {
                    isMenuShow = true
                }
            }
        )
        HistorySongDropMenu(
            isMenuShow = isMenuShow,
            onDismissRequest = {
                isMenuShow = false
            },
            onClick = {
                // TODO delete request
                isMenuShow = false
            }
        )
    }
}
