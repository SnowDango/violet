package com.snowdango.violet.presenter.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.snowdango.violet.domain.relation.SongAllMeta
import com.snowdango.violet.view.view.ArtWorkImage
import com.snowdango.violet.view.view.ArtWorkImageShape

@Composable
fun SongDetailDialog(songAllMeta: SongAllMeta, onDismiss: () -> Unit) {

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    RoundedCornerShape(10)
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                ArtWorkImage(
                    thumbnailUrl = songAllMeta.song.thumbnailUrl,
                    platformType = null,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .wrapContentHeight()
                        .padding(0.dp, 0.dp, 0.dp, 10.dp),
                    shape = ArtWorkImageShape.ROUNDED,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                )
                Text(
                    text = "title: ${songAllMeta.song.title}",
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "artist: ${songAllMeta.artist.name}",
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "album: ${songAllMeta.albumWithArtist.album.title}",
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
                songAllMeta.albumWithArtist.artist?.let {
                    Text(
                        text = "album artist: ${it.name}",
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                }
                if (songAllMeta.song.genre.isNotBlank()) {
                    Text(
                        text = "genre: ${songAllMeta.song.genre}",
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}