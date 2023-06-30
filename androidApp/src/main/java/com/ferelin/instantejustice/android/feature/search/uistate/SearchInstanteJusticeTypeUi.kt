package com.ferelin.instantejustice.android.feature.search.uistate

import androidx.compose.runtime.Stable

@Stable
enum class SearchInstanteJusticeTypeUi {
    HEARINGS_AGENDA,
    COURT_DECISION,
    COURT_RULINGS,
    PUBLIC_SUMMONS,
    COURT_CLAIMS_CASES;
}