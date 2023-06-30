package com.ferelin.instantejustice.android.composeui.components.justiceitem

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.composeui.theme.Black1
import com.ferelin.instantejustice.android.composeui.theme.Gray1
import com.ferelin.instantejustice.android.feature.search.uistate.SearchInstanteJusticeItemUi

@Composable
fun CourtRulingItem(
    modifier: Modifier = Modifier,
    courtRulingItem: SearchInstanteJusticeItemUi.CourtRulingItemUi,
    position: Int,
    onCopyClick: () -> Unit,
    onPdfClick: () -> Unit
) {
    InstanteJusticeItemBase(
        modifier = modifier,
        position = position,
        onCopyClick = onCopyClick,
        onPdfClick = onPdfClick
    ) {
        Text(
            modifier = Modifier.align(Alignment.End),
            text = courtRulingItem.caseNumber,
            style = MaterialTheme.typography.subtitle1,
            color = Gray1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = courtRulingItem.caseName,
            style = MaterialTheme.typography.h2,
            color = Black1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(
                id = R.string.body_judge_row,
                courtRulingItem.courtName,
                courtRulingItem.judgeName,
                courtRulingItem.caseSubject
            ),
            style = MaterialTheme.typography.body2,
            color = Black1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(
                id = R.string.body_registration_date,
                courtRulingItem.registrationDate
            ),
            style = MaterialTheme.typography.subtitle2,
            color = Gray1
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = stringResource(
                id = R.string.body_publicationt_date,
                courtRulingItem.publicationDate
            ),
            style = MaterialTheme.typography.subtitle2,
            color = Gray1
        )
    }
}