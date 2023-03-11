package com.snowdango.violet.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.util.toShortString

@Composable
fun ListSongComponent(song: Song, artist: Artist? = null, times: Int? = null) {

    val fraction = if (times == null) 1f else 0.7f

    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(0.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction)
                .wrapContentHeight()
                .padding(0.dp, 0.dp, 8.dp, 0.dp)
        ) {
            Text(
                song.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                color = MaterialTheme.colorScheme.onBackground
            )
            artist?.let {
                Text(
                    it.name,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        times?.let {
            Text(
                times.toShortString(),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .wrapContentHeight(),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}