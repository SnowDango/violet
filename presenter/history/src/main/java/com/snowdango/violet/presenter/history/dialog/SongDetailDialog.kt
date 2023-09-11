package com.snowdango.violet.presenter.history.dialog

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.snowdango.violet.presenter.common.R
import com.snowdango.violet.presenter.common.domain.ArtworkImageShape
import com.snowdango.violet.presenter.common.domain.PlatformType
import com.snowdango.violet.presenter.common.view.ArtWorkImage
import com.snowdango.violet.presenter.common.view.DividerOnText
import com.snowdango.violet.presenter.common.view.PlatformTypeImage
import com.snowdango.violet.presenter.history.domain.Platform
import com.snowdango.violet.presenter.history.domain.SongDetailViewData

@Composable
fun SongDetailDialog(
    songDetailViewData: SongDetailViewData,
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
                    thumbnailUrl = songDetailViewData.artwork,
                    platformType = PlatformType.UNKNOWN,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .wrapContentHeight()
                        .padding(0.dp, 0.dp, 0.dp, 10.dp),
                    shape = ArtworkImageShape.ROUNDED,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                )
                // title
                SongDetailMetaView(stringResource(R.string.title), songDetailViewData.title)
                // artist
                SongDetailMetaView(stringResource(R.string.artist), songDetailViewData.artist)
                // album
                SongDetailMetaView(stringResource(R.string.album), songDetailViewData.album)
                // album artist
                SongDetailMetaView(stringResource(R.string.album_artist), songDetailViewData.albumArtist)
                // genre
                SongDetailMetaView(stringResource(R.string.genre), songDetailViewData.genre)

                if (songDetailViewData.platform.isNotEmpty()) {
                    SongDetailPlatformLink(songDetailViewData.platform)
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SongDetailPlatformLink(platforms: List<Platform>) {
    SongDetailMetaDivider(stringResource(R.string.link))
    val context = LocalContext.current
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Top
    ) {
        platforms.forEach { platform ->
            PlatformTypeImage(
                platformType = platform.type,
                modifier = Modifier
                    .width(60.dp)
                    .height(52.dp)
                    .padding(4.dp, 0.dp)
                    .clip(CircleShape)
                    .clickable {
                        val uri = platform.mobileScheme
                        uri?.let {
                            val intent = Intent(Intent.ACTION_VIEW, it)
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