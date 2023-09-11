package com.snowdango.violet.presenter.common.view

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
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.snowdango.violet.presenter.common.domain.ArtworkImageShape
import com.snowdango.violet.presenter.common.domain.PlatformType

@Composable
fun ArtWorkImage(
    thumbnailUrl: String,
    platformType: PlatformType,
    modifier: Modifier,
    shape: ArtworkImageShape,
    backgroundColor: Color = MaterialTheme.colorScheme.background
) {

    val shapeModifier = when (shape) {
        ArtworkImageShape.CIRCLE -> modifier
            .aspectRatio(1f)
            .clip(CircleShape)

        ArtworkImageShape.ROUNDED -> modifier
            .aspectRatio(1f)
    }

    val builder = ImageRequest.Builder(LocalContext.current)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .data(thumbnailUrl)
        .transformations(
            when (shape) {
                ArtworkImageShape.CIRCLE -> CircleCropTransformation()
                ArtworkImageShape.ROUNDED -> RoundedCornersTransformation(80f)
            }
        )

    SubcomposeAsyncImage(
        model = builder.build(),
        contentDescription = null,
        loading = {
            Box(modifier = Modifier.matchParentSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                        .background(backgroundColor),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        success = {
            Image(
                painter = it.painter,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            )
        },
        error = {
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