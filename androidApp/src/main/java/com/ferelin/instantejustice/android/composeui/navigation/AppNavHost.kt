package com.ferelin.instantejustice.android.composeui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ferelin.instantejustice.android.feature.search.NAVIGATION_SEARCH_ROUTE
import com.ferelin.instantejustice.android.feature.search.searchScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NAVIGATION_SEARCH_ROUTE,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        searchScreen()
    }
}
