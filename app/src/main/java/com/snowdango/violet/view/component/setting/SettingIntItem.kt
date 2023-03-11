package com.snowdango.violet.view.component.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snowdango.violet.repository.datastore.SettingDataStore
import com.snowdango.violet.view.view.OnCombinedClickListener

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingIntItem(
    title: String,
    description: String = "",
    key: String,
    defValue: Int,
    dataStore: SettingDataStore,
    onCombinedClickListener: OnCombinedClickListener? = null,
    stateContent: (@Composable (state: Int) -> Unit)? = null
) {

    val state = dataStore.getIntFlow(key, defValue).collectAsState(defValue)

    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(0.dp, 8.dp)
            .combinedClickable(
                onClick = { onCombinedClickListener?.onClick() },
                onLongClick = { onCombinedClickListener?.onLongClick() },
                onDoubleClick = { onCombinedClickListener?.onDoubleClick() }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = description,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        stateContent?.invoke(state.value)
    }
}