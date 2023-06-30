package com.ferelin.instantejustice.android.composeui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.feature.search.SCREEN_HORIZONTAL_PADDING
import com.ferelin.instantejustice.android.feature.search.uistate.SearchInstanteJusticeTypeUi

private val JUSTICE_TYPE_ITEM_SPACE = 8.dp

@Composable
fun AppSelectJusticeType(
    modifier: Modifier = Modifier,
    justiceType: SearchInstanteJusticeTypeUi,
    onJusticeTypeSelected: (SearchInstanteJusticeTypeUi) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.width(SCREEN_HORIZONTAL_PADDING))
        AppButtonJusticeType(
            title = stringResource(id = R.string.title_hearings_agenda),
            isSelected = justiceType == SearchInstanteJusticeTypeUi.HEARINGS_AGENDA,
            onClick = { onJusticeTypeSelected(SearchInstanteJusticeTypeUi.HEARINGS_AGENDA) }
        )
        Spacer(modifier = Modifier.width(JUSTICE_TYPE_ITEM_SPACE))
        AppButtonJusticeType(
            title = stringResource(id = R.string.title_court_decisions),
            isSelected = justiceType == SearchInstanteJusticeTypeUi.COURT_DECISION,
            onClick = { onJusticeTypeSelected(SearchInstanteJusticeTypeUi.COURT_DECISION) }
        )
        Spacer(modifier = Modifier.width(JUSTICE_TYPE_ITEM_SPACE))
        AppButtonJusticeType(
            title = stringResource(id = R.string.title_court_rulings),
            isSelected = justiceType == SearchInstanteJusticeTypeUi.COURT_RULINGS,
            onClick = { onJusticeTypeSelected(SearchInstanteJusticeTypeUi.COURT_RULINGS) }
        )
        Spacer(modifier = Modifier.width(JUSTICE_TYPE_ITEM_SPACE))
        AppButtonJusticeType(
            title = stringResource(id = R.string.title_public_summons),
            isSelected = justiceType == SearchInstanteJusticeTypeUi.PUBLIC_SUMMONS,
            onClick = { onJusticeTypeSelected(SearchInstanteJusticeTypeUi.PUBLIC_SUMMONS) }
        )
        Spacer(modifier = Modifier.width(JUSTICE_TYPE_ITEM_SPACE))
        AppButtonJusticeType(
            title = stringResource(id = R.string.title_court_claims_and_cases),
            isSelected = justiceType == SearchInstanteJusticeTypeUi.COURT_CLAIMS_CASES,
            onClick = { onJusticeTypeSelected(SearchInstanteJusticeTypeUi.COURT_CLAIMS_CASES) }
        )
        Spacer(modifier = Modifier.width(SCREEN_HORIZONTAL_PADDING))
    }
}