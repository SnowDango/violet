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
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.extention.toBitmap

@Composable
fun LastSongArtWorkImage(lastSong: LastSong, modifier: Modifier) {

    if (lastSong.artwork.isNullOrBlank()) {

        val builder = ImageRequest.Builder(LocalContext.current)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .data("file:///android_asset/${lastSong.platform?.iconAssets}.png")
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
                )
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