package com.ferelin.instantejustice.android.composeui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.composeui.theme.Black1
import com.ferelin.instantejustice.android.composeui.theme.White2

@Composable
fun AppInfoCardRequest(
    modifier: Modifier = Modifier,
    description: String
) {
    Surface(
        modifier = modifier,
        color = White2,
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        AppInfoCard(backgroundColor = White2) {
            Text(
                text = description,
                style = MaterialTheme.typography.h2,
                color = Black1,
            )
            Spacer(modifier = Modifier.height(14.dp))
            Image(
                painter = painterResource(id = R.drawable.img_search_example),
                contentDescription = ""
            )
        }
    }
}