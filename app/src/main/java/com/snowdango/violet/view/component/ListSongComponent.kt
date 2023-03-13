package com.snowdango.violet.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.util.toShortString
import com.snowdango.violet.view.style.AppTheme

@Composable
fun ListSongComponent(song: Song, artist: Artist? = null, times: Int? = null) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(0.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(8f)
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
            ) {
                Text(
                    song.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                artist?.let {
                    Text(
                        it.name,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1
                    )
                }
            }
            times?.let {
                Text(
                    times.toShortString(),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .weight(2f)
                        .wrapContentHeight(),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    textAlign = TextAlign.End
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Preview(group = "Song", name = "ListSong")
@Composable
fun PreviewListSong() {
    AppTheme {
        val song = Song(
            id = 1,
            title = "Song Title",
            albumId = 1,
            artistId = 1,
            thumbnailUrl = "",
            genre = "Rock"
        )
        val artist = Artist(
            id = 1,
            name = "Artist"
        )
        ListSongComponent(song, artist, 123456)
    }
}

@Preview(group = "Song", name = "ListSongWithoutTime")
@Composable
fun PreviewListSongComponentNotTime() {
    AppTheme {
        val song = Song(
            id = 1,
            title = "Song Title",
            albumId = 1,
            artistId = 1,
            thumbnailUrl = "",
            genre = "Rock"
        )
        val artist = Artist(
            id = 1,
            name = "Artist"
        )
        ListSongComponent(song, artist, null)
    }
}

@Preview(group = "Song", name = "ListSongWithoutArtist")
@Composable
fun PreviewListSongComponentNotArtist() {
    AppTheme {
        val song = Song(
            id = 1,
            title = "Song Title",
            albumId = 1,
            artistId = 1,
            thumbnailUrl = "",
            genre = "Rock"
        )
        ListSongComponent(song, null, null)
    }
}
