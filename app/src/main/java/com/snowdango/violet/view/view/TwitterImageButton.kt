package com.snowdango.violet.view.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation


@Composable
fun TwitterImageButton(
    modifier: Modifier,
    clickFn: (() -> Unit)? = null,
) {
    val builder = ImageRequest.Builder(LocalContext.current)
        .data("file:///android_asset/twitter.png")
        .transformations(CircleCropTransformation())
    SubcomposeAsyncImage(
        model = builder.build(),
        contentDescription = null,
        success = {
            Image(
                painter = it.painter,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .clip(CircleShape)
                    .clickable { clickFn?.invoke() }
            )
        },
        modifier = modifier
    )
}