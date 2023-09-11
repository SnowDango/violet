package com.snowdango.violet.presenter.common.component

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
import com.snowdango.violet.presenter.common.domain.ArtworkImageShape
import com.snowdango.violet.presenter.common.domain.PlatformType
import com.snowdango.violet.presenter.common.view.ArtWorkImage
import com.snowdango.violet.presenter.common.view.MarqueeText
import com.snowdango.violet.presenter.common.view.OnCombinedClickListener

private const val THUMBNAIL_ID = "thumbnail"
private const val TITLE_ID = "title"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridAlbumComponent(
    title: String?,
    artwork: String?,
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
            artwork ?: "",
            platformType = PlatformType.UNKNOWN,
            modifier = Modifier
                .fillMaxWidth(0.71f)
                .wrapContentHeight()
                .layoutId(THUMBNAIL_ID),
            shape = ArtworkImageShape.ROUNDED
        )
        MarqueeText(
            text = title ?: "",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.71f)
                .wrapContentHeight()
                .layoutId(TITLE_ID),
            maxLines = 1,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

private fun albumComponentConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val thumbnail = createRefFor(THUMBNAIL_ID)
        val title = createRefFor(TITLE_ID)

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