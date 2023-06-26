package com.ferelin.instantejustice.android.feature.filter

import androidx.lifecycle.ViewModel
import com.ferelin.instantejustice.domain.DEFAULT_INSTANTE_JUSTIPE_TYPE
import com.ferelin.instantejustice.domain.InstanteJusticeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchFilterModule = module {
    viewModel { params -> SearchFilterViewModel(params.get()) }
}

class SearchFilterViewModel(currentCaseType: InstanteJusticeType) : ViewModel() {

    private val _selectedCaseType = MutableStateFlow(currentCaseType)
    val selectedCaseType: StateFlow<InstanteJusticeType> = _selectedCaseType.asStateFlow()

    fun onCaseTypeSelected(selectedCaseType: InstanteJusticeType) {
        _selectedCaseType.value = selectedCaseType
    }

    fun onResetClick() {
        _selectedCaseType.value = DEFAULT_INSTANTE_JUSTIPE_TYPE
    }
}