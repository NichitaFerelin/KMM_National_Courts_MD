package com.ferelin.instantejustice.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NAMED_DISPATCHER_IO = "Dispatcher IO"
const val NAMED_DISPATCHER_DEFAULT = "Dispatcher Default"
const val NAMED_DISPATCHER_MAIN = "Dispatcher Main"

val coroutinesModule = module {
    factory(named(NAMED_DISPATCHER_IO)) { Dispatchers.IO }
    factory(named(NAMED_DISPATCHER_DEFAULT)) { Dispatchers.Default }
    factory<CoroutineDispatcher>(named(NAMED_DISPATCHER_MAIN)) { Dispatchers.Main }
}