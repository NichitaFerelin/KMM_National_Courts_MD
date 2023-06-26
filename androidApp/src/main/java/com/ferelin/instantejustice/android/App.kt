package com.ferelin.instantejustice.android

import android.app.Application
import com.ferelin.instantejustice.android.feature.filter.searchFilterModule
import com.ferelin.instantejustice.android.feature.htmlparser.htmlParserFeatureModule
import com.ferelin.instantejustice.android.feature.search.pagingSourceModule
import com.ferelin.instantejustice.android.feature.search.searchModule
import com.ferelin.instantejustice.core.koin.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            platformModules = listOf(
                htmlParserFeatureModule,
                searchModule,
                pagingSourceModule,
                searchFilterModule
            )
        )
    }
}