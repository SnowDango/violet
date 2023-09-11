package com.snowdango.violet.presenter.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DividerOnText(
    textString: String,
    backGroundColor: Color,
    textColor: Color,
    dividerColor: Color = DividerDefaults.color,
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (text, divider) = createRefs()
        Divider(
            modifier = Modifier.fillMaxWidth()
                .wrapContentWidth()
                .constrainAs(divider) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = dividerColor
        )
        Text(
            text = textString,
            color = textColor,
            modifier = Modifier.wrapContentWidth()
                .wrapContentWidth()
                .background(backGroundColor)
                .padding(5.dp, 0.dp)
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}