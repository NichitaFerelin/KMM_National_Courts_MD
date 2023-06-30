package com.ferelin.instantejustice.android.composeui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ferelin.instantejustice.android.composeui.components.modifier.BOUNCE_SCALE_DOWN
import com.ferelin.instantejustice.android.composeui.components.modifier.bounceClick
import com.ferelin.instantejustice.android.composeui.theme.Black1
import com.ferelin.instantejustice.android.composeui.theme.Green1
import com.ferelin.instantejustice.android.composeui.theme.White1

@Composable
fun AppSearchByButton(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Green1 else Color.Transparent,
        label = "Border width DP animation"
    )

    AppButton(
        modifier = modifier
            .fillMaxWidth()
            .bounceClick(BOUNCE_SCALE_DOWN),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(2.dp, borderColor),
        colors = ButtonDefaults.buttonColors(backgroundColor = White1),
        elevation = ButtonDefaults.elevation(defaultElevation = 4.dp, pressedElevation = 4.dp),
        onClick = onClick
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2,
            color = Black1
        )
    }
}