package com.snowdango.violet.presenter.dialog

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.relation.SongAllMeta
import com.snowdango.violet.extention.mobileSchemeUri
import com.snowdango.violet.view.view.ArtWorkImage
import com.snowdango.violet.view.view.ArtWorkImageShape
import com.snowdango.violet.view.view.DividerOnText
import com.snowdango.violet.view.view.PlatformTypeImage
import timber.log.Timber

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
                SongDetailMetaView("Title", songAllMeta.song.title)
                // artist
                SongDetailMetaView("Artist", songAllMeta.artist.name)
                // album
                SongDetailMetaView("Album", songAllMeta.albumWithArtist.album.title)
                // album artist
                SongDetailMetaView("Album Artist", songAllMeta.albumWithArtist.artist?.name)
                // genre
                SongDetailMetaView("Genre", songAllMeta.song.genre)

                val platforms = songAllMeta.platforms.filter {
                    it.platform != null
                            && songAllMeta.platforms.first { it1 ->
                        it1.platform?.name == it.platform?.name
                    }.id == it.id
                }

                if (platforms.isNotEmpty()) {
                    SongDetailPlatformLink(platforms)
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SongDetailPlatformLink(platforms: List<Platform>) {
    SongDetailMetaDivider("Link")
    val context = LocalContext.current
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        platforms.forEach { platform ->
            PlatformTypeImage(
                platformType = platform.platform,
                modifier = Modifier
                    .width(60.dp)
                    .height(52.dp)
                    .padding(4.dp, 0.dp)
                    .clip(CircleShape)
                    .clickable {
                        val uri = platform.mobileSchemeUri()
                        Timber.d(uri.toString())
                        if (uri != null) {
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                        }
                    }
            )
        }
    }
}


@Composable
fun SongDetailMetaView(head: String, body: String?) {
    if (!body.isNullOrBlank()) {
        SongDetailMetaDivider(head)
        SongDetailMetaText(body)
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
            .padding(0.dp, 4.dp, 0.dp, 0.dp)
    )
}

@Composable
fun SongDetailMetaText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(16.dp, 4.dp, 16.dp, 0.dp),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        textAlign = TextAlign.Center
    )
}