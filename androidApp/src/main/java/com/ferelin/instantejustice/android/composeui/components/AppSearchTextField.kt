package com.ferelin.instantejustice.android.composeui.components

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.composeui.theme.Gray1

@Composable
fun AppSearchTextField(
    modifier: Modifier = Modifier,
    searchInput: String,
    onSearchInputChanged: (String) -> Unit,
    onImeActionSearch: KeyboardActionScope.() -> Unit
) {
    AppTextField(
        modifier = modifier,
        value = searchInput,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = onImeActionSearch),
        onValueChange = onSearchInputChanged,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "",
                tint = Gray1
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.hint_search_field),
                style = MaterialTheme.typography.body1,
                color = Gray1
            )
        }
    )
}