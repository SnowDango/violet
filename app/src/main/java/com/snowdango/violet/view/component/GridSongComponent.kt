package com.snowdango.violet.view.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.view.view.ArtWorkImage
import com.snowdango.violet.view.view.ArtWorkImageShape
import com.snowdango.violet.view.view.MarqueeText
import com.snowdango.violet.view.view.OnCombinedClickListener


private const val THUMBNAIL_ID = "thumbnail"
private const val TITLE_ID = "title"
private const val APP_ICON_ID = "app_icon"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridSongComponent(
    song: Song,
    platformType: PlatformType,
    onCombinedClickListener: OnCombinedClickListener? = null
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .combinedClickable(
                onClick = { onCombinedClickListener?.onClick() },
                onLongClick = { onCombinedClickListener?.onLongClick() },
                onDoubleClick = { onCombinedClickListener?.onDoubleClick() }
            ),
        constraintSet = songComponentConstraintSet()
    ) {

        ArtWorkImage(
            song.thumbnailUrl,
            platformType = platformType,
            modifier = Modifier
                .fillMaxWidth(0.71f)
                .wrapContentHeight()
                .layoutId(THUMBNAIL_ID),
            shape = ArtWorkImageShape.ROUNDED
        )

        MarqueeText(
            text = song.title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.71f)
                .wrapContentHeight()
                .layoutId(TITLE_ID),
            maxLines = 1,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        GlideImage(
            imageModel = { "file:///android_asset/${platformType.iconAssets}.png" },
            requestOptions = {
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
            },
            success = { imageState ->
                imageState.imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.08f)
                .wrapContentHeight()
                .aspectRatio(1f)
                .layoutId(APP_ICON_ID)
                .background(MaterialTheme.colorScheme.background),
        )

    }

}

private fun songComponentConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val thumbnail = createRefFor(THUMBNAIL_ID)
        val title = createRefFor(TITLE_ID)
        val appIcon = createRefFor(APP_ICON_ID)

        constrain(thumbnail) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(title.top, margin = 8.dp)
        }

        constrain(title) {
            top.linkTo(thumbnail.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }

        constrain(appIcon) {
            top.linkTo(thumbnail.bottom)
            start.linkTo(title.end)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    }
}