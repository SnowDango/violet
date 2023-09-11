package com.snowdango.violet.presenter.common.extention

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.squareConformFillMaxWidth(fraction: Float): Modifier = composed {
    this.fillMaxWidth(fraction).wrapContentHeight().aspectRatio(1f)
}
