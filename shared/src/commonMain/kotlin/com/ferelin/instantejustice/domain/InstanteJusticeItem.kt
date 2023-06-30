package com.ferelin.instantejustice.domain

sealed class InstanteJusticeItem {

    data class HearingsAgendaItem(
        val courtName: String,
        val caseName: String,
        val caseNumber: String,
        val caseObject: String,
        val caseType: String,
        val judgeName: String,
        val hearingDate: String,
        val hearingHour: String,
        val hearingResult: String,
        val courtRoom: String,
        val pdfUrlPath: String
    ) : InstanteJusticeItem()

    data class CourtDecisionItem(
        val caseName: String,
        val courtName: String,
        val caseNumber: String,
        val caseSubject: String,
        val caseType: String,
        val judgeName: String,
        val dateOfPronouncement: String,
        val registrationDate: String,
        val publicationDate: String,
        val pdfUrlPath: String
    ) : InstanteJusticeItem()

    data class CourtRulingItem(
        val caseName: String,
        val courtName: String,
        val caseNumber: String,
        val caseSubject: String,
        val caseType: String,
        val registrationDate: String,
        val publicationDate: String,
        val judgeName: String,
        val pdfUrlPath: String
    ) : InstanteJusticeItem()

    data class PublicSummonItem(
        val courtName: String,
        val caseName: String,
        val caseNumber: String,
        val caseObject: String,
        val judgeName: String,
        val publicationDate: String,
        val hearingDate: String,
        val hearingHour: String,
        val summonedPerson: String,
        val courtRoom: String,
        val pdfUrlPath: String
    ) : InstanteJusticeItem()

    data class CourtClaimAndCaseItem(
        val courtName: String,
        val caseName: String,
        val caseNumber: String,
        val caseReferenceNumber: String,
        val caseStatus: String,
        val caseType: String,
        val registrationDate: String,
        val pdfUrlPath: String
    ) : InstanteJusticeItem()
}