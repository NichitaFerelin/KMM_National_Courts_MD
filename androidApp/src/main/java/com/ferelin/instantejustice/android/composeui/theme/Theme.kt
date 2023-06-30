package com.ferelin.instantejustice.android.composeui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = LightAndroidColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}

val LightAndroidColorScheme = Colors(
    primary = Green1,
    onPrimary = White2,
    secondary = Blue1,
    onSecondary = White2,
    background = White1,
    onBackground = Black1,
    surface = White2,
    onSurface = Black1,
    error = Red1,
    onError = White2,
    primaryVariant = Gray1,
    secondaryVariant = White2,
    isLight = true
)
