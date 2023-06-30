package com.ferelin.instantejustice.android.composeui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
    shape: Shape = MaterialTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primaryVariant
    ),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    border: BorderStroke? = null,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding,
        border = border,
        content = content,
        onClick = onClick,
    )
}
