package com.snowdango.violet.view.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TwitterImageButton(modifier: Modifier) {
    GlideImage(
        imageModel = { "file:///android_asset/twitter.png" },
        requestOptions = {
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
        },
        success = { imageState ->
            imageState.imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .clip(CircleShape)
                )
            }
        },
        modifier = modifier
    )
}