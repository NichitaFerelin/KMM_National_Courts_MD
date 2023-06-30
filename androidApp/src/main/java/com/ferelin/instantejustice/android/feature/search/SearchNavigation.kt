package com.ferelin.instantejustice.android.feature.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val NAVIGATION_SEARCH_ROUTE = "navigation_search"

fun NavGraphBuilder.searchScreen() {
    composable(route = NAVIGATION_SEARCH_ROUTE) {
        SearchScreenRoute()
    }
}