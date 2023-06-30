package com.ferelin.instantejustice.android

import android.app.Application
import com.ferelin.instantejustice.android.feature.htmlparser.htmlParserFeatureModule
import com.ferelin.instantejustice.android.feature.search.pagingSourceModule
import com.ferelin.instantejustice.android.feature.search.searchModule
import com.ferelin.instantejustice.core.koin.startKoin
import com.google.firebase.analytics.FirebaseAnalytics
import io.ktor.client.engine.android.Android
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    factory<FirebaseAnalytics> {
        FirebaseAnalytics.getInstance(get())
    }
    single { Android.create() }
}

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            onStart = {
                androidContext(this@App)
            },
            platformModules = listOf(
                htmlParserFeatureModule,
                searchModule,
                pagingSourceModule,
                firebaseModule
            )
        )
    }
}