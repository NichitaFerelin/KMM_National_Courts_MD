package com.ferelin.instantejustice.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface InstanteJusticeApi {
    suspend fun load(requestUrl: String): Result<String>

    companion object {
        const val BASE_URL = "https://instante.justice.md/"
    }
}

internal class InstanteJusticeApiImpl(
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher
) : InstanteJusticeApi {

    override suspend fun load(requestUrl: String): Result<String> =
        withContext(ioDispatcher) {
            try {
                Result.success(httpClient.get(requestUrl).bodyAsText())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}