package com.ferelin.instantejustice.android.composeui.components.justiceitem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.composeui.components.AppButton
import com.ferelin.instantejustice.android.composeui.components.modifier.BOUNCE_SCALE_DOWN
import com.ferelin.instantejustice.android.composeui.components.modifier.bounceClick
import com.ferelin.instantejustice.android.composeui.theme.Blue2
import com.ferelin.instantejustice.android.composeui.theme.Green1
import com.ferelin.instantejustice.android.composeui.theme.Green2

@Composable
fun InstanteJusticeItemBase(
    modifier: Modifier = Modifier,
    position: Int,
    onCopyClick: () -> Unit,
    onPdfClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val backgroundColor = if (position % 2 == 0) {
        Blue2
    } else {
        Green2
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .background(color = backgroundColor)
                .padding(vertical = 4.dp, horizontal = 6.dp)
        ) {
            content()
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppButton(
                    modifier = Modifier
                        .height(42.dp)
                        .width(60.dp)
                        .bounceClick(BOUNCE_SCALE_DOWN),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Green1),
                    contentPadding = PaddingValues(4.dp),
                    onClick = onPdfClick
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_pdf),
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                AppButton(
                    modifier = Modifier
                        .size(42.dp)
                        .bounceClick(BOUNCE_SCALE_DOWN),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Green1),
                    contentPadding = PaddingValues(4.dp),
                    onClick = onCopyClick
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_copy),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}