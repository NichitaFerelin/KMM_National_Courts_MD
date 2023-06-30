package com.ferelin.instantejustice.android.composeui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.composeui.theme.Black1
import com.ferelin.instantejustice.android.feature.search.SCREEN_HORIZONTAL_PADDING
import com.ferelin.instantejustice.android.feature.search.uistate.SearchBy
import com.ferelin.instantejustice.android.feature.search.uistate.SearchInstanteJusticeTypeUi

@Composable
fun AppSearchFilter(
    modifier: Modifier = Modifier,
    searchBy: SearchBy,
    justiceType: SearchInstanteJusticeTypeUi,
    onJusticeTypeSelected: (SearchInstanteJusticeTypeUi) -> Unit,
    onSearchBySelected: (SearchBy) -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.padding(start = SCREEN_HORIZONTAL_PADDING),
            text = stringResource(id = R.string.title_search_for),
            style = MaterialTheme.typography.h1,
            color = Black1
        )
        Spacer(modifier = Modifier.height(12.dp))
        AppSelectJusticeType(
            justiceType = justiceType,
            onJusticeTypeSelected = onJusticeTypeSelected
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(start = SCREEN_HORIZONTAL_PADDING),
            text = stringResource(id = R.string.title_search_by),
            style = MaterialTheme.typography.h1,
            color = Black1
        )
        Spacer(modifier = Modifier.height(12.dp))
        AppSearchByButton(
            modifier = Modifier.padding(horizontal = SCREEN_HORIZONTAL_PADDING),
            title = stringResource(id = R.string.body_search_by_case_name),
            isSelected = searchBy == SearchBy.CASE_NAME,
            onClick = { onSearchBySelected(SearchBy.CASE_NAME) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        AppSearchByButton(
            modifier = Modifier.padding(horizontal = SCREEN_HORIZONTAL_PADDING),
            title = stringResource(id = R.string.body_search_by_case_number),
            isSelected = searchBy == SearchBy.CASE_NUMBER,
            onClick = { onSearchBySelected(SearchBy.CASE_NUMBER) }
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}