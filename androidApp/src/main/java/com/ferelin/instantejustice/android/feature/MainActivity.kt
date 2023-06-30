package com.ferelin.instantejustice.android.feature

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ferelin.instantejustice.android.composeui.navigation.AppNavHost
import com.ferelin.instantejustice.android.composeui.theme.AppTheme
import com.ferelin.instantejustice.android.feature.search.NAVIGATION_SEARCH_ROUTE

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                AppNavHost(
                    navController = rememberNavController(),
                    startDestination = NAVIGATION_SEARCH_ROUTE,
                )
            }
        }
    }
}