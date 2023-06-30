package com.ferelin.instantejustice.core.koin

import com.ferelin.instantejustice.core.coroutines.coroutinesModule
import com.ferelin.instantejustice.data.remote.di.remoteModule
import org.koin.core.KoinApplication
import org.koin.core.module.Module

fun startKoin(platformModules: List<Module> = emptyList(), onStart: KoinApplication.() -> Unit) {
    org.koin.core.context.startKoin {
        onStart()
        modules(platformModules + listOf(coroutinesModule, remoteModule))
    }
}