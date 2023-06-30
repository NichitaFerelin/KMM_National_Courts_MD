package com.ferelin.instantejustice.android.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.ferelin.instantejustice.android.feature.search.uistate.SearchBy
import com.ferelin.instantejustice.android.feature.search.uistate.SearchInstanteJusticeTypeUi
import com.ferelin.instantejustice.android.feature.search.uistate.SearchScreenUiState
import com.ferelin.instantejustice.android.feature.search.uistate.asUi
import com.ferelin.instantejustice.core.coroutines.NAMED_DISPATCHER_DEFAULT
import com.ferelin.instantejustice.domain.InstanteJusticeItem
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.Timer
import kotlin.concurrent.timerTask

val searchModule = module {
    viewModel {
        SearchViewModel(get(named(NAMED_DISPATCHER_DEFAULT)), get())
    }
}

private const val SEARCH_REQUEST_DEBOUNCE = 500L

class SearchViewModel(
    private val defaultDispatcher: CoroutineDispatcher,
    private val firebaseAnalytics: FirebaseAnalytics
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState: StateFlow<SearchScreenUiState> = _uiState.asStateFlow()

    private var lastSearchRequestOptions = _uiState.value
    private var searchRequest = _uiState.value.createSearchRequest()

    private var pagingSource: PagingSource<Int, InstanteJusticeItem>? = null
    val pagerSourceFlow = Pager(
        PagingConfig(pageSize = SearchPagingSource.PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = {
            get<PagingSource<Int, InstanteJusticeItem>>(
                parameters = { parametersOf(searchRequest) }
            ).also { pagingSource = it }
        },
    ).flow
        .map { pagingItems -> pagingItems.map { it.asUi() } }
        .flowOn(defaultDispatcher)
        .cachedIn(viewModelScope)

    private var searchRequestTaskTimer: Timer? = null

    init {
        firebaseAnalytics.logEvent("app_open", null)
    }

    override fun onCleared() {
        searchRequestTaskTimer?.cancel()
    }

    fun onSearchRequested() {
        searchRequestTaskTimer?.cancel()
        invokeSearchRequest()
    }

    fun onSearchTextChanged(input: String) {
        val previousState = _uiState.value
        _uiState.update { it.copy(searchInput = input) }

        if (input == previousState.searchInput) return

        searchRequestTaskTimer?.cancel()
        searchRequestTaskTimer = Timer().apply {
            schedule(timerTask {
                viewModelScope.launch {
                    invokeSearchRequest()
                }
            }, SEARCH_REQUEST_DEBOUNCE)
        }
    }

    fun onJusticeTypeSelected(justiceType: SearchInstanteJusticeTypeUi) {
        _uiState.update { it.copy(selectedJusticeType = justiceType) }
    }

    fun onSearchBySelected(searchBy: SearchBy) {
        _uiState.update { it.copy(searchBy = searchBy) }
    }

    fun onRetryClick() {
        searchRequestTaskTimer?.cancel()
        invokeSearchRequest()
    }

    fun onFilterApplied() {
        if (_uiState.value != lastSearchRequestOptions) {
            searchRequestTaskTimer?.cancel()
            invokeSearchRequest()
        }
    }

    private fun invokeSearchRequest() {
        lastSearchRequestOptions = _uiState.value
        searchRequest = _uiState.value.createSearchRequest()
        pagingSource?.invalidate()
    }
}