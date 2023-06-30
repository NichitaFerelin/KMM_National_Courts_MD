package com.ferelin.instantejustice.android.feature.search.uistate

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.data.remote.request.InstanteJusticeRequest
import com.ferelin.instantejustice.domain.DEFAULT_SUPPORT_LANGUAGE
import com.ferelin.instantejustice.feature.htmlparser.HtmlParser
import io.ktor.client.plugins.HttpRequestTimeoutException
import java.net.UnknownHostException

@Immutable
data class SearchScreenUiState(
    val searchInput: String = "",
    val selectedJusticeType: SearchInstanteJusticeTypeUi = SearchInstanteJusticeTypeUi.HEARINGS_AGENDA,
    val searchBy: SearchBy = SearchBy.CASE_NAME
) {
    fun createSearchRequest(): InstanteJusticeRequest {
        return when (selectedJusticeType) {
            SearchInstanteJusticeTypeUi.HEARINGS_AGENDA -> {
                when (searchBy) {
                    SearchBy.CASE_NUMBER -> {
                        InstanteJusticeRequest.HearingsAgendaRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseName = "",
                            caseNumber = searchInput,
                            caseObject = ""
                        )
                    }

                    SearchBy.CASE_NAME -> {
                        InstanteJusticeRequest.HearingsAgendaRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseName = searchInput,
                            caseNumber = "",
                            caseObject = ""
                        )
                    }
                }
            }

            SearchInstanteJusticeTypeUi.COURT_DECISION -> {
                when (searchBy) {
                    SearchBy.CASE_NUMBER -> {
                        InstanteJusticeRequest.CourtDecisionsRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseName = "",
                            caseNumber = searchInput,
                            dateOfPronouncement = "",
                            caseSubject = ""
                        )
                    }

                    SearchBy.CASE_NAME -> {
                        InstanteJusticeRequest.CourtDecisionsRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseName = searchInput,
                            caseNumber = "",
                            dateOfPronouncement = "",
                            caseSubject = ""
                        )
                    }
                }
            }

            SearchInstanteJusticeTypeUi.COURT_CLAIMS_CASES -> {
                when (searchBy) {
                    SearchBy.CASE_NUMBER -> {
                        InstanteJusticeRequest.CourtClaimsAndCasesRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseName = "",
                            caseNumber = searchInput,
                            caseStatus = "",
                            registrationDate = ""
                        )
                    }

                    SearchBy.CASE_NAME -> {
                        InstanteJusticeRequest.CourtClaimsAndCasesRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseName = searchInput,
                            caseNumber = "",
                            caseStatus = "",
                            registrationDate = ""
                        )
                    }
                }
            }

            SearchInstanteJusticeTypeUi.PUBLIC_SUMMONS -> {
                when (searchBy) {
                    SearchBy.CASE_NUMBER -> {
                        InstanteJusticeRequest.PublicSummonsRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseName = "",
                            caseNumber = searchInput,
                            solrDocument = "",
                            summonedPerson = "",
                            judge = ""
                        )
                    }

                    SearchBy.CASE_NAME -> {
                        InstanteJusticeRequest.PublicSummonsRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseName = searchInput,
                            caseNumber = "",
                            solrDocument = "",
                            summonedPerson = "",
                            judge = ""
                        )
                    }
                }
            }

            SearchInstanteJusticeTypeUi.COURT_RULINGS -> {
                when (searchBy) {
                    SearchBy.CASE_NUMBER -> {
                        InstanteJusticeRequest.CourtRulingsRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseNumber = searchInput,
                            caseName = "",
                            registrationDate = "",
                            caseSubject = ""
                        )
                    }

                    SearchBy.CASE_NAME -> {
                        InstanteJusticeRequest.CourtRulingsRequest(
                            language = DEFAULT_SUPPORT_LANGUAGE,
                            caseNumber = "",
                            caseName = searchInput,
                            registrationDate = "",
                            caseSubject = ""
                        )
                    }
                }
            }
        }
    }
}

@Stable
fun Throwable.errorMsgResource(): Int = when (this) {
    is UnknownHostException -> R.string.error_unknown_host
    is HttpRequestTimeoutException -> R.string.error_timeout
    is HtmlParser.EmptyResultException -> R.string.error_no_search_results
    else -> R.string.error_unknown_exception
}