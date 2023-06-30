package com.ferelin.instantejustice.android.composeui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.composeui.components.modifier.BOUNCE_SCALE_DOWN
import com.ferelin.instantejustice.android.composeui.components.modifier.bounceClick
import com.ferelin.instantejustice.android.composeui.theme.Black1
import com.ferelin.instantejustice.android.composeui.theme.Red1
import com.ferelin.instantejustice.android.composeui.theme.White2

@Composable
fun AppInfoCardError(
    modifier: Modifier = Modifier,
    errorDescription: String,
    onRetryClick: () -> Unit
) {
    AppInfoCard(
        modifier = modifier,
        backgroundColor = Red1
    ) {
        Text(
            text = errorDescription,
            style = MaterialTheme.typography.h2,
            color = White2,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            modifier = Modifier
                .align(Alignment.End)
                .bounceClick(BOUNCE_SCALE_DOWN),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = White2
            ),
            onClick = onRetryClick
        ) {
            Text(
                text = stringResource(id = R.string.hint_retry_request),
                style = MaterialTheme.typography.h2,
                color = Black1,
            )
        }
    }
}