package com.snowdango.violet.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun EmptyAndRefreshComponent(
    message: String,
    refreshFn: () -> Unit,
    modifier: Modifier,
    contentAlignment: Alignment
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                message,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Button(
                onClick = refreshFn,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Text("reload")
            }
        }
    }
}