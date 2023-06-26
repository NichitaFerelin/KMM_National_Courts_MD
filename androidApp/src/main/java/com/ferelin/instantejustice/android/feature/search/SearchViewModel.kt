package com.ferelin.instantejustice.android.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ferelin.instantejustice.android.feature.search.adapter.SearchResultsAdapter
import com.ferelin.instantejustice.android.feature.search.adapter.SearchResultsLoadAdapter
import com.ferelin.instantejustice.data.remote.request.InstanteJusticeRequestBuilder
import com.ferelin.instantejustice.domain.DEFAULT_INSTANTE_JUSTIPE_TYPE
import com.ferelin.instantejustice.domain.InstanteJusticeItem
import com.ferelin.instantejustice.domain.InstanteJusticeSupportLanguage
import com.ferelin.instantejustice.domain.InstanteJusticeType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import java.util.Timer
import kotlin.concurrent.timerTask

val searchModule = module {
    viewModel {
        SearchViewModel()
    }
}

private const val SEARCH_REQUEST_DEBOUNCE = 500L

class SearchViewModel : ViewModel(), KoinComponent {

    private val _searchResultsAdapter = SearchResultsAdapter()
    private val _footerResultsAdapter = SearchResultsLoadAdapter(
        retry = _searchResultsAdapter::retry
    )

    private var requestBuilder = InstanteJusticeRequestBuilder(
        language = InstanteJusticeSupportLanguage.EN,
        justiceType = DEFAULT_INSTANTE_JUSTIPE_TYPE
    )

    private val pagerSourceFlow = Pager(
        PagingConfig(pageSize = SearchPagingSource.PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = {
            get<PagingSource<Int, InstanteJusticeItem>>(
                parameters = { parametersOf(requestBuilder) }
            )
        },
    ).flow
        .onEach { _searchResultsAdapter.submitData(it) }
        .launchIn(viewModelScope)

    private var searchRequestTaskTimer: Timer? = null
    private var searchRequestInput: String = ""

    val initialLoadState: StateFlow<LoadState> = _searchResultsAdapter.loadStateFlow
        .map { it.refresh }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = LoadState.NotLoading(false)
        )

    val searchResultsAdapter: RecyclerView.Adapter<ViewHolder>
        get() = _searchResultsAdapter.withLoadStateFooter(_footerResultsAdapter)

    val selectedJusticeType: InstanteJusticeType
        get() = requestBuilder.justiceType

    override fun onCleared() {
        searchRequestTaskTimer?.cancel()
    }

    fun onSearchTextChanged(input: String) {
        if (input == searchRequestInput) return
        searchRequestInput = input.replace(' ', '+')

        searchRequestTaskTimer?.cancel()
        searchRequestTaskTimer = Timer().apply {
            schedule(timerTask {
                viewModelScope.launch {
                    requestBuilder = InstanteJusticeRequestBuilder(
                        language = InstanteJusticeSupportLanguage.EN,
                        justiceType = selectedJusticeType
                    ).apply { caseName = searchRequestInput }

                    _searchResultsAdapter.refresh()
                }
            }, SEARCH_REQUEST_DEBOUNCE)
        }
    }

    fun onSearchSettingsChanged(justiceType: InstanteJusticeType) {
        if (justiceType != requestBuilder.justiceType) {
            requestBuilder = InstanteJusticeRequestBuilder(
                language = InstanteJusticeSupportLanguage.EN,
                justiceType = justiceType
            ).apply { caseName = searchRequestInput }

            _searchResultsAdapter.refresh()
        }
    }

    fun onRetryClick() {
        _searchResultsAdapter.retry()
    }
}