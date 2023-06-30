package com.ferelin.instantejustice.android.feature.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ferelin.instantejustice.core.coroutines.NAMED_DISPATCHER_DEFAULT
import com.ferelin.instantejustice.data.remote.InstanteJusticeApi
import com.ferelin.instantejustice.data.remote.request.InstanteJusticeRequest
import com.ferelin.instantejustice.domain.InstanteJusticeItem
import com.ferelin.instantejustice.domain.InstanteJusticeType
import com.ferelin.instantejustice.feature.htmlparser.HtmlParser
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.net.UnknownHostException

val pagingSourceModule = module {
    factory<PagingSource<Int, InstanteJusticeItem>> { params ->
        SearchPagingSource(
            get(),
            get(),
            params.get(),
            get(),
            get(named(NAMED_DISPATCHER_DEFAULT))
        )
    }
}

private const val INITIAL_PAGE_INDEX = 0

class SearchPagingSource(
    private val instanceJusticeApi: InstanteJusticeApi,
    private val htmlParser: HtmlParser,
    private val searchRequest: InstanteJusticeRequest,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val defaultDispatcher: CoroutineDispatcher
) : PagingSource<Int, InstanteJusticeItem>() {

    companion object {
        const val PAGE_SIZE = 10
    }

    override fun getRefreshKey(
        state: PagingState<Int, InstanteJusticeItem>
    ): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPosition) ?: return null
        return closestPage.nextKey?.minus(1) ?: closestPage.prevKey?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, InstanteJusticeItem> =
        withContext(defaultDispatcher) {
            if (searchRequest.builder.isEmpty()) {
                return@withContext LoadResult.Page(emptyList(), null, null)
            }

            firebaseAnalytics.logEvent("search_request") {
                param("url", searchRequest.builder.resultUrl)
            }

            val nextPageIndex = params.key ?: INITIAL_PAGE_INDEX
            searchRequest.builder.apply { page = nextPageIndex }

            val apiResponse = instanceJusticeApi.load(searchRequest.builder.resultUrl)
            return@withContext if (apiResponse.isSuccess) {
                val parseResult = apiResponse.getOrThrow().parseJusticeItemsFromHtml()

                if (parseResult.isSuccess) {
                    val items = parseResult.getOrThrow()
                    val nextPage = if (items.size < PAGE_SIZE) null else nextPageIndex + 1
                    LoadResult.Page(items, prevKey = null, nextKey = nextPage)
                } else {
                    val exception = parseResult.exceptionOrNull() ?: Throwable("Unknown error")

                    if (exception !is HtmlParser.EmptyResultException) {
                        firebaseAnalytics.logEvent("exception_parse") {
                            param("exception", exception.toString())
                            param("search_request", searchRequest.builder.resultUrl)
                        }
                    }
                    LoadResult.Error(exception)
                }
            } else {
                val exception = apiResponse.exceptionOrNull() ?: Throwable("Unknown error")

                when (exception) {
                    is UnknownHostException -> Unit

                    is HttpRequestTimeoutException -> {
                        firebaseAnalytics.logEvent("exception_timeout", null)
                    }

                    else -> {
                        firebaseAnalytics.logEvent("exception_unknown") {
                            param("exception", exception.toString())
                            param("search_request", searchRequest.builder.resultUrl)
                        }
                    }
                }
                LoadResult.Error(exception)
            }
        }

    private fun String.parseJusticeItemsFromHtml(): Result<List<InstanteJusticeItem>> {
        return when (searchRequest.builder.justiceType) {
            InstanteJusticeType.COURT_DECISION -> htmlParser.parseCourtDecisions(this)
            InstanteJusticeType.COURT_RULINGS -> htmlParser.parseCourtRulings(this)
            InstanteJusticeType.PUBLIC_SUMMONS -> htmlParser.parsePublicSummons(this)
            InstanteJusticeType.COURT_CLAIMS_CASES -> htmlParser.parseCourtClaimsAndCases(this)
            InstanteJusticeType.HEARINGS_AGENDA -> htmlParser.parseHearingsAgenda(this)
        }
    }
}