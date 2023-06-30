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
import com.ferelin.instantejustice.android.composeui.theme.Red1
import com.ferelin.instantejustice.android.feature.search.uistate.SearchInstanteJusticeItemUi

@Composable
fun HearingsAgendaItem(
    modifier: Modifier = Modifier,
    hearingsAgendaItem: SearchInstanteJusticeItemUi.HearingsAgendaItemUi,
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
            text = hearingsAgendaItem.caseNumber,
            style = MaterialTheme.typography.subtitle1,
            color = Gray1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = hearingsAgendaItem.caseName,
            style = MaterialTheme.typography.h2,
            color = Black1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(
                id = R.string.body_judge_row_long,
                hearingsAgendaItem.courtName,
                hearingsAgendaItem.courtRoom,
                hearingsAgendaItem.judgeName,
                hearingsAgendaItem.caseObject
            ),
            style = MaterialTheme.typography.body2,
            color = Black1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = hearingsAgendaItem.hearingResult,
            style = MaterialTheme.typography.h2,
            color = Red1
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(
                id = R.string.body_with_comma_separator,
                hearingsAgendaItem.hearingDate,
                hearingsAgendaItem.hearingHour
            ),
            style = MaterialTheme.typography.h2,
            color = Red1
        )
    }
}