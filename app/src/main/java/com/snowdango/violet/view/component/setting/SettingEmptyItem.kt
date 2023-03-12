package com.snowdango.violet.view.component.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.snowdango.violet.view.view.OnCombinedClickListener

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingEmptyItem(
    title: String,
    description: String = "",
    onCombinedClickListener: OnCombinedClickListener? = null
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight()
                .padding(12.dp, 8.dp)
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
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Preview(group = "Setting Component", name = "Empty Component")
@ShowkaseComposable(group = "Setting Component", name = "Empty Component")
@Composable
fun PreviewSettingEmptyItem() {
    SettingEmptyItem("title", "description")
}