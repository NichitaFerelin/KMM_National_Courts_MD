package com.ferelin.instantejustice.android.composeui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ferelin.instantejustice.android.composeui.theme.Green1

@Composable
fun AppCircularProgressIndicator(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier.size(34.dp),
        color = Green1,
        strokeWidth = 3.dp,
    )
}
