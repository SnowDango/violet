package com.snowdango.violet.view.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.extention.toBitmap

@Composable
fun LastSongArtWorkImage(lastSong: LastSong, modifier: Modifier) {

    if (lastSong.artwork.isNullOrBlank()) {
        GlideImage(
            imageModel = { "file:///android_asset/${lastSong.platform?.iconAssets}.png" },
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
            loading = {
                Box(modifier = Modifier.matchParentSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                            .background(MaterialTheme.colorScheme.background),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = modifier
        )
    } else {
        Image(
            bitmap = lastSong.artwork!!.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .clip(CircleShape)
        )
    }

}