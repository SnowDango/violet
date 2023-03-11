package com.snowdango.violet.view.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.view.view.ArtWorkImage
import com.snowdango.violet.view.view.ArtWorkImageShape
import com.snowdango.violet.view.view.MarqueeText
import com.snowdango.violet.view.view.OnCombinedClickListener

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridAlbumComponent(
    album: Album,
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
        constraintSet = albumComponentConstraintSet()
    ) {
        ArtWorkImage(
            album.thumbnailUrl,
            platformType = null,
            modifier = Modifier
                .fillMaxWidth(0.71f)
                .wrapContentHeight()
                .layoutId("thumbnail"),
            shape = ArtWorkImageShape.ROUNDED
        )
        MarqueeText(
            text = album.title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.71f)
                .wrapContentHeight()
                .layoutId("title"),
            maxLines = 1,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

private fun albumComponentConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val thumbnail = createRefFor("thumbnail")
        val title = createRefFor("title")

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
    }
}