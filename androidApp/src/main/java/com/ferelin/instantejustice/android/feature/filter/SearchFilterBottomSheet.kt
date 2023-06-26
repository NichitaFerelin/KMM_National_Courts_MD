package com.ferelin.instantejustice.android.feature.filter

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.databinding.FragmentSearchFilterBinding
import com.ferelin.instantejustice.domain.InstanteJusticeType
import com.ferelin.instantejustice.domain.toInstanteJusticeType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class SearchFilterBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val RESULT_REQUEST_KEY = "REQUEST_SEARCH_FILTER"
        const val ARG_SELECTED_CASE_TYPE = "CASE_TYPE"
    }

    private lateinit var viewModel: SearchFilterViewModel
    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!

    private var selectedCaseTypeButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val caseTypeKey =
                it.getString(ARG_SELECTED_CASE_TYPE, InstanteJusticeType.COURT_DECISION.key)
            viewModel =
                getViewModel(parameters = { parametersOf(caseTypeKey.toInstanteJusticeType()) })
        }

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            buttonHearingsAgenda.setOnClickListener {
                viewModel.onCaseTypeSelected(InstanteJusticeType.HEARINGS_AGENDA)
            }
            buttonCourtDecisions.setOnClickListener {
                viewModel.onCaseTypeSelected(InstanteJusticeType.COURT_DECISION)
            }
            buttonCourtRulings.setOnClickListener {
                viewModel.onCaseTypeSelected(InstanteJusticeType.COURT_RULINGS)
            }
            buttonPublicSummons.setOnClickListener {
                viewModel.onCaseTypeSelected(InstanteJusticeType.PUBLIC_SUMMONS)
            }
            buttonCourtClaimsAndCases.setOnClickListener {
                viewModel.onCaseTypeSelected(InstanteJusticeType.COURT_CLAIMS_CASES)
            }

            buttonReset.setOnClickListener {
                viewModel.onResetClick()
                setResultAndClose()
            }

            buttonApply.setOnClickListener {
                setResultAndClose()
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.selectedCaseType
                    .onEach(::onJusticeTypeChanged)
                    .launchIn(this)
            }
        }
    }

    private fun onJusticeTypeChanged(justiceType: InstanteJusticeType) {
        val holderButton = when (justiceType) {
            InstanteJusticeType.HEARINGS_AGENDA -> binding.buttonHearingsAgenda
            InstanteJusticeType.COURT_CLAIMS_CASES -> binding.buttonCourtClaimsAndCases
            InstanteJusticeType.PUBLIC_SUMMONS -> binding.buttonPublicSummons
            InstanteJusticeType.COURT_RULINGS -> binding.buttonCourtRulings
            InstanteJusticeType.COURT_DECISION -> binding.buttonCourtDecisions
        }

        selectedCaseTypeButton?.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.whiteLight)
        )

        holderButton.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.blue)
        )
        selectedCaseTypeButton = holderButton
    }

    private fun setResultAndClose() {
        requireActivity().supportFragmentManager.setFragmentResult(
            RESULT_REQUEST_KEY,
            Bundle().apply {
                putString(ARG_SELECTED_CASE_TYPE, viewModel.selectedCaseType.value.key)
            })
        this@SearchFilterBottomSheet.dismiss()
    }
}