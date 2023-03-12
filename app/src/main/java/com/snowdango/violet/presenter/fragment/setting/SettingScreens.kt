package com.snowdango.violet.presenter.fragment.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.airbnb.android.showkase.models.Showkase
import com.snowdango.violet.presenter.getBrowserIntent
import com.snowdango.violet.view.component.setting.SettingEmptyItem
import com.snowdango.violet.view.component.setting.SettingHeader
import com.snowdango.violet.view.view.OnCombinedClickListener

@Composable
fun SettingScreen() {

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        item {
            SettingHeader("Develop Mode")
        }
        item {
            SettingEmptyItem(
                "showkase",
                "showkaceを表示します",
                object : OnCombinedClickListener {
                    override fun onClick() {
                        startActivity(context, Showkase.getBrowserIntent(context), null)
                    }
                }
            )
        }
    }
}
