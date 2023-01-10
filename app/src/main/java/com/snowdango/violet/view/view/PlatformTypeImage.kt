package com.snowdango.violet.view.view

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.snowdango.violet.R
import com.snowdango.violet.domain.platform.PlatformType

@Composable
fun PlatformTypeImage(platformType: PlatformType?, modifier: Modifier = Modifier) {
    val painter: Painter = when (platformType) {
        PlatformType.Spotify -> painterResource(R.drawable.spotify)
        PlatformType.AppleMusic -> painterResource(R.drawable.apple_music)
        null -> painterResource(R.drawable.violet)
    }
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}