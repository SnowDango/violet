package com.snowdango.violet.presenter.fragment.history.item

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.snowdango.violet.domain.relation.HistoryWithSong
import com.snowdango.violet.presenter.menu.HistorySongDropMenu
import com.snowdango.violet.view.component.GridAfterSaveSongComponent
import com.snowdango.violet.view.component.GridSongComponent
import com.snowdango.violet.view.view.OnCombinedClickListener
import com.snowdango.violet.viewmodel.history.HistoryViewModel

@Composable
fun SongHistoryItem(
    songHistory: HistoryWithSong?,
    viewModel: HistoryViewModel,
    modifier: Modifier,
    onClick: ((it: Long) -> Unit),
) {
    var isMenuShow: Boolean by remember { mutableStateOf(false) }

    if (songHistory?.song != null) {
        Box(modifier = modifier) {
            GridSongComponent(
                songHistory.song!!,
                songHistory.history.platform,
                object : OnCombinedClickListener {
                    override fun onClick() {
                        onClick(songHistory.song!!.id)
                    }

                    override fun onLongClick() {
                        isMenuShow = true
                    }
                }
            )
            HistorySongDropMenu(songHistory.history.id, viewModel, isMenuShow) {
                isMenuShow = false
            }
        }
    } else {
        GridAfterSaveSongComponent(songHistory?.history?.platform!!)
    }
}
