package com.snowdango.violet.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.view.style.squareConformFillMaxWidth
import com.snowdango.violet.view.view.LastSongArtWorkImage
import com.snowdango.violet.view.view.MarqueeText
import com.snowdango.violet.view.view.TwitterImageButton

@Composable
fun LastSongComponent(lastSongs: List<LastSong>) {

    val fraction = 0.6f

    val listenLastSongs =
        lastSongs.filter { it.queueId != null && it.queueId != -1L && it.dateTime != null }
    if (listenLastSongs.isEmpty()) return

    val lastSong = listenLastSongs.maxByOrNull { it.dateTime!! }
    lastSong?.let {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            constraintSet = lastSongComponentConstraintSet()
        ) {

            LastSongArtWorkImage(
                it,
                modifier = Modifier
                    .squareConformFillMaxWidth(fraction)
                    .layoutId("artwork")
            )

            TwitterImageButton(
                modifier = Modifier
                    .squareConformFillMaxWidth(fraction / 4)
                    .layoutId("twitter")
            )

            MarqueeText(
                text = it.title ?: "Unknown",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .wrapContentHeight()
                    .layoutId("title"),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            MarqueeText(
                text = it.artist ?: "Unknown",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .wrapContentHeight()
                    .layoutId("artist"),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            MarqueeText(
                text = it.album ?: "Unknown",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .wrapContentHeight()
                    .layoutId("album"),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .layoutId("divider")
            )
        }
    }
}

fun lastSongComponentConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val artwork = createRefFor("artwork")
        val twitter = createRefFor("twitter")
        val title = createRefFor("title")
        val artist = createRefFor("artist")
        val album = createRefFor("album")
        val divider = createRefFor("divider")

        constrain(artwork) {
            top.linkTo(parent.top, margin = 8.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(title.top, margin = 10.dp)
        }

        constrain(twitter) {
            start.linkTo(artist.end)
            bottom.linkTo(title.top)
        }

        constrain(title) {
            top.linkTo(artwork.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(artist.top, margin = 8.dp)
        }

        constrain(artist) {
            top.linkTo(title.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(album.top, margin = 8.dp)
        }

        constrain(album) {
            top.linkTo(artist.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(divider.top)
        }

        constrain(divider) {
            top.linkTo(album.bottom, margin = 8.dp)
            start.linkTo(parent.start, margin = 8.dp)
            end.linkTo(parent.end, margin = 8.dp)
            bottom.linkTo(parent.bottom, margin = 8.dp)
        }
    }
}