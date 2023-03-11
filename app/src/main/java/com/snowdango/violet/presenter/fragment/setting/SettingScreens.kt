package com.snowdango.violet.presenter.fragment.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowdango.violet.view.component.setting.SettingEmptyItem
import com.snowdango.violet.view.component.setting.SettingHeader

@Composable
fun SettingScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        item {
            SettingHeader("Develop Mode")
        }
        item {
            SettingEmptyItem("showkase", "showkaceを表示します")
        }
    }
}
