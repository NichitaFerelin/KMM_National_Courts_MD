package com.ferelin.instantejustice.data.remote.di

import com.ferelin.instantejustice.core.coroutines.NAMED_DISPATCHER_IO
import com.ferelin.instantejustice.data.remote.InstanteJusticeApi
import com.ferelin.instantejustice.data.remote.InstanteJusticeApiImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteModule = module {
    single<HttpClient> { HttpClient() }

    factory<InstanteJusticeApi> {
        InstanteJusticeApiImpl(get(), get(named(NAMED_DISPATCHER_IO)))
    }
}