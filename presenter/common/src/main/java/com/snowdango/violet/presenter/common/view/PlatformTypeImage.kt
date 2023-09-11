package com.snowdango.violet.presenter.common.view

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.snowdango.violet.presenter.common.R
import com.snowdango.violet.presenter.common.domain.PlatformType

@Composable
fun PlatformTypeImage(platformType: PlatformType, modifier: Modifier = Modifier) {
    val painter: Painter = when (platformType) {
        PlatformType.SPOTIFY -> painterResource(R.drawable.spotify)
        PlatformType.APPLE_MUSIC -> painterResource(R.drawable.apple_music)
        PlatformType.UNKNOWN -> painterResource(R.drawable.violet)
    }
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}