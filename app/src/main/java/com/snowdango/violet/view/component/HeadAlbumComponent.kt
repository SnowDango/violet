package com.snowdango.violet.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.view.style.squareConformFillMaxWidth
import com.snowdango.violet.view.view.ArtWorkImage
import com.snowdango.violet.view.view.ArtWorkImageShape
import com.snowdango.violet.view.view.MarqueeText

@Composable
fun HeadAlbumComponent(
    album: Album,
    artist: Artist?
) {

    val fraction = 0.6f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArtWorkImage(
            thumbnailUrl = album.thumbnailUrl,
            platformType = null,
            modifier = Modifier
                .squareConformFillMaxWidth(fraction)
                .padding(0.dp, 8.dp, 0.dp, 10.dp),
            shape = ArtWorkImageShape.CIRCLE
        )
        MarqueeText(
            text = album.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth(fraction)
                .wrapContentHeight()
                .padding(0.dp, 0.dp, 0.dp, 8.dp),
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        MarqueeText(
            text = artist?.name ?: "UnKnow",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth(fraction)
                .wrapContentHeight()
                .padding(0.dp, 0.dp, 0.dp, 8.dp),
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        )
    }
}