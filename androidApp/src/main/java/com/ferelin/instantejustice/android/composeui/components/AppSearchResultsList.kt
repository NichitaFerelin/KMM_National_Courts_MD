package com.ferelin.instantejustice.android.composeui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import com.ferelin.instantejustice.android.composeui.components.justiceitem.ClaimsAndCasesItem
import com.ferelin.instantejustice.android.composeui.components.justiceitem.CourtDecisionItem
import com.ferelin.instantejustice.android.composeui.components.justiceitem.CourtRulingItem
import com.ferelin.instantejustice.android.composeui.components.justiceitem.HearingsAgendaItem
import com.ferelin.instantejustice.android.composeui.components.justiceitem.PublicSummonItem
import com.ferelin.instantejustice.android.feature.search.SCREEN_HORIZONTAL_PADDING
import com.ferelin.instantejustice.android.feature.search.uistate.SearchInstanteJusticeItemUi
import com.ferelin.instantejustice.android.feature.search.uistate.errorMsgResource

@Composable
fun AppSearchResultsList(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<SearchInstanteJusticeItemUi>,
    onCopyClick: (SearchInstanteJusticeItemUi) -> Unit,
    onPdfClick: (SearchInstanteJusticeItemUi) -> Unit,
    onRetryRequestClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = SCREEN_HORIZONTAL_PADDING,
            end = SCREEN_HORIZONTAL_PADDING,
            top = 4.dp,
            bottom = 60.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            count = items.itemCount,
            contentType = items.itemContentType { it.viewType }
        ) { index ->
            when (val item = items[index]) {
                is SearchInstanteJusticeItemUi.HearingsAgendaItemUi -> {
                    HearingsAgendaItem(
                        hearingsAgendaItem = item,
                        position = index,
                        onCopyClick = { onCopyClick(item) },
                        onPdfClick = { onPdfClick(item) }
                    )
                }

                is SearchInstanteJusticeItemUi.CourtDecisionItemUi -> {
                    CourtDecisionItem(
                        courtDecisionItem = item,
                        position = index,
                        onCopyClick = { onCopyClick(item) },
                        onPdfClick = { onPdfClick(item) }
                    )
                }

                is SearchInstanteJusticeItemUi.CourtRulingItemUi -> {
                    CourtRulingItem(
                        courtRulingItem = item,
                        position = index,
                        onCopyClick = { onCopyClick(item) },
                        onPdfClick = { onPdfClick(item) }
                    )
                }

                is SearchInstanteJusticeItemUi.PublicSummonItemUi -> {
                    PublicSummonItem(
                        publicSummonItem = item,
                        position = index,
                        onCopyClick = { onCopyClick(item) },
                        onPdfClick = { onPdfClick(item) }
                    )
                }

                is SearchInstanteJusticeItemUi.CourtClaimAndCaseItemUi -> {
                    ClaimsAndCasesItem(
                        claimsAndCasesItem = item,
                        position = index,
                        onCopyClick = { onCopyClick(item) },
                        onPdfClick = { onPdfClick(item) }
                    )
                }

                else -> Unit
            }
        }
        item {
            Crossfade(
                targetState = items.loadState.append,
                label = "search screen crossfade animation append"
            ) { state ->
                when (state) {
                    is LoadState.NotLoading -> Unit

                    is LoadState.Error -> {
                        AppInfoCardError(
                            errorDescription = stringResource(id = state.error.errorMsgResource()),
                            onRetryClick = onRetryRequestClick
                        )
                    }

                    is LoadState.Loading -> {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            AppCircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}