package com.snowdango.violet.view.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import com.snowdango.violet.domain.platform.PlatformType

@Composable
fun ArtWorkImage(
    thumbnailUrl: String,
    platformType: PlatformType?,
    modifier: Modifier,
    shape: ArtWorkImageShape,
    backgroundColor: Color = MaterialTheme.colorScheme.background
) {

    val shapeModifier = when (shape) {
        ArtWorkImageShape.CIRCLE -> modifier
            .aspectRatio(1f)
            .clip(CircleShape)
        ArtWorkImageShape.ROUNDED -> modifier
            .aspectRatio(1f)
    }

    GlideImage(
        imageModel = { thumbnailUrl },
        requestOptions = {
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            when (shape) {
                ArtWorkImageShape.CIRCLE -> options.circleCrop()
                ArtWorkImageShape.ROUNDED -> options.transform(CenterCrop(), RoundedCorners(80))
            }
        },
        success = { imageState ->
            imageState.imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                )
            }
        },
        loading = {
            Box(modifier = Modifier.matchParentSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                        .background(backgroundColor),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        failure = {
            PlatformTypeImage(
                platformType,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            )
        },
        modifier = shapeModifier
    )

}

enum class ArtWorkImageShape {
    CIRCLE,
    ROUNDED
}