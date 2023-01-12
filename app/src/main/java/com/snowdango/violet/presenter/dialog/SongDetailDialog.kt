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
import com.snowdango.violet.view.view.DividerOnText

@Composable
fun SongDetailDialog(
    songAllMeta: SongAllMeta,
    onDismissRequest: () -> Unit
) {

    Dialog(
        onDismissRequest = {
            onDismissRequest.invoke()
        }
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
                // title
                SongDetailMetaDivider("Title")
                SongDetailMetaText(songAllMeta.song.title)
                // artist
                SongDetailMetaDivider("Artist")
                SongDetailMetaText(songAllMeta.artist.name)
                // album
                SongDetailMetaDivider("Album")
                SongDetailMetaText(songAllMeta.albumWithArtist.album.title)
                // album artist
                songAllMeta.albumWithArtist.artist?.let {
                    SongDetailMetaDivider("Album Artist")
                    SongDetailMetaText(it.name)
                }
                // genre
                if (songAllMeta.song.genre.isNotBlank()) {
                    SongDetailMetaDivider("Genre")
                    SongDetailMetaText(songAllMeta.song.genre)
                }
            }
        }
    }
}

@Composable
fun SongDetailMetaDivider(text: String) {
    DividerOnText(
        textString = text,
        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
        backGroundColor = MaterialTheme.colorScheme.primaryContainer,
        dividerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(0.dp, 5.dp, 0.dp, 5.dp)
    )
}

@Composable
fun SongDetailMetaText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(16.dp, 0.dp, 16.dp, 4.dp),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        textAlign = TextAlign.Center
    )
}