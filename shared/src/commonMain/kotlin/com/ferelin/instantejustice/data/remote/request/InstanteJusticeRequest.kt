package com.ferelin.instantejustice.data.remote.request

import com.ferelin.instantejustice.domain.InstanteJusticeSupportLanguage
import com.ferelin.instantejustice.domain.InstanteJusticeType

sealed class InstanteJusticeRequest(val builder: InstanteJusticeRequestBuilder) {

    data class HearingsAgendaRequest(
        val language: InstanteJusticeSupportLanguage,
        val caseName: String,
        val caseNumber: String,
        val caseObject: String
    ) : InstanteJusticeRequest(
        InstanteJusticeRequestBuilder(language, InstanteJusticeType.HEARINGS_AGENDA).apply {
            this.caseName = caseName
            this.caseNumber = caseNumber
            this.caseObject = caseObject
        }
    )

    data class CourtDecisionsRequest(
        val language: InstanteJusticeSupportLanguage,
        val caseName: String,
        val caseNumber: String,
        val dateOfPronouncement: String,
        val caseSubject: String,
    ) : InstanteJusticeRequest(
        InstanteJusticeRequestBuilder(language, InstanteJusticeType.HEARINGS_AGENDA).apply {
            this.caseNameV2 = caseName
            this.caseNumberV2 = caseNumber
            this.date = dateOfPronouncement
            this.caseSubject = caseSubject
        }
    )

    data class CourtRulingsRequest(
        val language: InstanteJusticeSupportLanguage,
        val caseName: String,
        val caseNumber: String,
        val registrationDate: String,
        val caseSubject: String
    ) : InstanteJusticeRequest(
        InstanteJusticeRequestBuilder(language, InstanteJusticeType.COURT_RULINGS).apply {
            this.caseNameV2 = caseName
            this.caseNumberV2 = caseNumber
            this.date = registrationDate
            this.caseSubject = caseSubject
        }
    )

    data class PublicSummonsRequest(
        val language: InstanteJusticeSupportLanguage,
        val solrDocument: String,
        val caseNumber: String,
        val summonedPerson: String,
        val judge: String
    ) : InstanteJusticeRequest(
        InstanteJusticeRequestBuilder(language, InstanteJusticeType.PUBLIC_SUMMONS).apply {
            this.caseNumberV2 = caseNumber
            this.solrDoc = solrDocument
            this.judge = judge
            this.summoned = summonedPerson
        }
    )

    data class CourtClaimsAndCasesRequest(
        val language: InstanteJusticeSupportLanguage,
        val caseName: String,
        val caseNumber: String,
        val caseStatus: String,
        val registrationDate: String
    ) : InstanteJusticeRequest(
        InstanteJusticeRequestBuilder(language, InstanteJusticeType.COURT_CLAIMS_CASES).apply {
            this.caseNameV2 = caseName
            this.caseNumberV2 = caseNumber
            this.state = caseStatus
            this.date = registrationDate
        }
    )
}