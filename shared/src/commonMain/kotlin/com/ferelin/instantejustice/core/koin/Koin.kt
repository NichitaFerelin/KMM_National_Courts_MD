package com.ferelin.instantejustice.core.koin

import com.ferelin.instantejustice.core.coroutines.coroutinesModule
import com.ferelin.instantejustice.data.remote.di.remoteModule
import org.koin.core.module.Module

fun startKoin(platformModules: List<Module> = emptyList()) {
    org.koin.core.context.startKoin {
        modules(platformModules + listOf(coroutinesModule, remoteModule))
    }
}