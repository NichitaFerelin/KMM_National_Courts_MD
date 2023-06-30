package com.ferelin.instantejustice.data.remote.di

import com.ferelin.instantejustice.core.coroutines.NAMED_DISPATCHER_IO
import com.ferelin.instantejustice.data.remote.InstanteJusticeApi
import com.ferelin.instantejustice.data.remote.InstanteJusticeApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteModule = module {
    single<HttpClient> {
        HttpClient(get()) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5_000
            }
        }
    }

    factory<InstanteJusticeApi> {
        InstanteJusticeApiImpl(get(), get(named(NAMED_DISPATCHER_IO)))
    }
}