package com.ferelin.instantejustice.android.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.ferelin.instantejustice.android.databinding.FragmentSearchBinding
import com.ferelin.instantejustice.android.feature.filter.SearchFilterBottomSheet
import com.ferelin.instantejustice.android.feature.search.adapter.SearchResultsItemDecoration
import com.ferelin.instantejustice.domain.DEFAULT_INSTANTE_JUSTIPE_TYPE
import com.ferelin.instantejustice.domain.InstanteJusticeType
import com.ferelin.instantejustice.domain.toInstanteJusticeType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val BOTTOM_SHEET_TAG = "FILTER_BOTTOM_SHEET"

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSearchResults.adapter = viewModel.searchResultsAdapter
        binding.recyclerViewSearchResults.addItemDecoration(SearchResultsItemDecoration())

        requireActivity().supportFragmentManager.setFragmentResultListener(
            SearchFilterBottomSheet.RESULT_REQUEST_KEY,
            viewLifecycleOwner
        ) { requestKey, bundle ->
            if (requestKey == SearchFilterBottomSheet.RESULT_REQUEST_KEY) {
                val justiceTypeKey = bundle.getString(
                    SearchFilterBottomSheet.ARG_SELECTED_CASE_TYPE,
                    DEFAULT_INSTANTE_JUSTIPE_TYPE.key
                )
                viewModel.onSearchSettingsChanged(justiceTypeKey.toInstanteJusticeType())
            }
        }

        binding.buttonRetry.setOnClickListener {
            viewModel.onRetryClick()
        }

        binding.textInputLayoutSearchRequest.setEndIconOnClickListener {
            val bottomSheet = SearchFilterBottomSheet()
            bottomSheet.arguments = Bundle().apply {
                putString(
                    SearchFilterBottomSheet.ARG_SELECTED_CASE_TYPE,
                    viewModel.selectedJusticeType.key
                )
            }
            bottomSheet.show(requireActivity().supportFragmentManager, BOTTOM_SHEET_TAG)
        }

        binding.textInputSearchRequest.addTextChangedListener {
            viewModel.onSearchTextChanged(it?.toString() ?: "")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.initialLoadState
                    .onEach(::onInitialLoadStateChanged)
                    .launchIn(this)
            }
        }
    }

    override fun onDestroyView() {
        binding.recyclerViewSearchResults.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun onInitialLoadStateChanged(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                binding.progressIndicator.isVisible = true
                binding.viewGroupError.isVisible = false
                binding.recyclerViewSearchResults.isVisible = false
            }

            is LoadState.Error -> {
                binding.progressIndicator.isVisible = false
                binding.viewGroupError.isVisible = true
                binding.textViewErrorMessage.text = loadState.error.toString()
                binding.recyclerViewSearchResults.isVisible = false
            }

            else -> {
                binding.progressIndicator.isVisible = false
                binding.viewGroupError.isVisible = false
                binding.recyclerViewSearchResults.isVisible = true
            }
        }
    }
}