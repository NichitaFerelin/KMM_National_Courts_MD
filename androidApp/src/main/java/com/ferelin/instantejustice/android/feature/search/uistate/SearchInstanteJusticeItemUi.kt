package com.ferelin.instantejustice.android.feature.search.uistate

import android.content.Context
import androidx.compose.runtime.Immutable
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.utils.COURT_DECISION_DATE_FORMAT
import com.ferelin.instantejustice.android.utils.DEFAULT_DATE_FORMAT
import com.ferelin.instantejustice.android.utils.DateConverter
import com.ferelin.instantejustice.data.remote.InstanteJusticeApi
import com.ferelin.instantejustice.domain.InstanteJusticeItem
import com.ferelin.instantejustice.utils.sentenceFormat

sealed class SearchInstanteJusticeItemUi(
    val viewType: Int,
    val pdfUrl: String
) {
    abstract fun generateMessageToCopy(context: Context): String

    @Immutable
    data class HearingsAgendaItemUi(
        val caseNumber: String,
        val caseName: String,
        val courtName: String,
        val courtRoom: String,
        val judgeName: String,
        val caseObject: String,
        val hearingDate: String,
        val hearingHour: String,
        val hearingResult: String,
        val pdfUrlPath: String
    ) : SearchInstanteJusticeItemUi(
        viewType = 0,
        pdfUrl = InstanteJusticeApi.BASE_URL + pdfUrlPath
    ) {
        override fun generateMessageToCopy(context: Context): String = context.getString(
            R.string.copy_hearings_agenda,
            this.caseNumber,
            this.caseName,
            this.courtName,
            this.courtRoom,
            this.judgeName,
            this.caseObject,
            this.hearingDate,
            this.hearingHour,
            this.hearingResult,
            InstanteJusticeApi.BASE_URL + pdfUrlPath
        )
    }

    @Immutable
    data class CourtDecisionItemUi(
        val caseNumber: String,
        val caseName: String,
        val courtName: String,
        val judgeName: String,
        val caseSubject: String,
        val dateOfPronouncement: String,
        val registrationDate: String,
        val publicationDate: String,
        val pdfUrlPath: String
    ) : SearchInstanteJusticeItemUi(
        viewType = 1,
        pdfUrl = InstanteJusticeApi.BASE_URL + pdfUrlPath
    ) {
        override fun generateMessageToCopy(context: Context): String = context.getString(
            R.string.copy_court_decision,
            this.caseNumber,
            this.caseName,
            this.courtName,
            this.judgeName,
            this.caseSubject,
            this.dateOfPronouncement,
            this.registrationDate,
            this.publicationDate,
            InstanteJusticeApi.BASE_URL + pdfUrlPath
        )
    }

    @Immutable
    data class CourtRulingItemUi(
        val caseNumber: String,
        val caseName: String,
        val courtName: String,
        val judgeName: String,
        val caseSubject: String,
        val registrationDate: String,
        val publicationDate: String,
        val pdfUrlPath: String
    ) : SearchInstanteJusticeItemUi(
        viewType = 2,
        pdfUrl = InstanteJusticeApi.BASE_URL + pdfUrlPath
    ) {
        override fun generateMessageToCopy(context: Context): String = context.getString(
            R.string.copy_court_ruling,
            this.caseNumber,
            this.caseName,
            this.courtName,
            this.judgeName,
            this.caseSubject,
            this.registrationDate,
            this.publicationDate,
            InstanteJusticeApi.BASE_URL + pdfUrlPath
        )
    }

    @Immutable
    data class PublicSummonItemUi(
        val caseNumber: String,
        val caseName: String,
        val courtName: String,
        val courtRoom: String,
        val judgeName: String,
        val caseObject: String,
        val publicationDate: String,
        val summonedPerson: String,
        val hearingDate: String,
        val hearingHour: String,
        val pdfUrlPath: String
    ) : SearchInstanteJusticeItemUi(
        viewType = 3,
        pdfUrl = InstanteJusticeApi.BASE_URL + pdfUrlPath
    ) {
        override fun generateMessageToCopy(context: Context): String = context.getString(
            R.string.copy_public_summon,
            this.caseNumber,
            this.caseName,
            this.courtName,
            this.courtRoom,
            this.judgeName,
            this.caseObject,
            this.summonedPerson,
            this.publicationDate,
            this.hearingDate,
            this.hearingHour,
            InstanteJusticeApi.BASE_URL + pdfUrlPath
        )
    }

    @Immutable
    data class CourtClaimAndCaseItemUi(
        val courtName: String,
        val caseName: String,
        val caseNumber: String,
        val caseReferenceNumber: String,
        val caseStatus: String,
        val caseType: String,
        val registrationDate: String,
        val pdfUrlPath: String
    ) : SearchInstanteJusticeItemUi(
        viewType = 4,
        pdfUrl = InstanteJusticeApi.BASE_URL + pdfUrlPath
    ) {
        override fun generateMessageToCopy(context: Context): String = context.getString(
            R.string.copy_court_claim_cases,
            this.caseNumber,
            this.caseReferenceNumber,
            this.caseName,
            this.courtName,
            this.caseStatus,
            this.caseType,
            this.registrationDate,
            InstanteJusticeApi.BASE_URL + pdfUrlPath
        )
    }
}

fun InstanteJusticeItem.asUi(): SearchInstanteJusticeItemUi =
    when (this) {
        is InstanteJusticeItem.HearingsAgendaItem -> this.asUi()
        is InstanteJusticeItem.CourtDecisionItem -> this.asUi()
        is InstanteJusticeItem.CourtRulingItem -> this.asUi()
        is InstanteJusticeItem.PublicSummonItem -> this.asUi()
        is InstanteJusticeItem.CourtClaimAndCaseItem -> this.asUi()
    }

fun InstanteJusticeItem.HearingsAgendaItem.asUi(): SearchInstanteJusticeItemUi.HearingsAgendaItemUi {
    val caseObjectNew = caseObject.sentenceFormat()
    val hearingResultNew = hearingResult.sentenceFormat()
    val hearingDateNew = DateConverter.convertDate(DEFAULT_DATE_FORMAT, hearingDate) ?: hearingDate

    return SearchInstanteJusticeItemUi.HearingsAgendaItemUi(
        caseNumber = this.caseNumber,
        caseName = this.caseName,
        courtName = this.courtName,
        courtRoom = this.courtRoom,
        judgeName = this.judgeName,
        caseObject = caseObjectNew,
        hearingDate = hearingDateNew,
        hearingHour = this.hearingHour,
        hearingResult = hearingResultNew,
        pdfUrlPath = this.pdfUrlPath
    )
}

fun InstanteJusticeItem.CourtDecisionItem.asUi(): SearchInstanteJusticeItemUi.CourtDecisionItemUi {
    val caseSubjectNew = this.caseSubject.sentenceFormat()

    val dateOfPronounNew = DateConverter
        .convertDate(COURT_DECISION_DATE_FORMAT, dateOfPronouncement) ?: dateOfPronouncement
    val registrationDateNew = DateConverter
        .convertDate(COURT_DECISION_DATE_FORMAT, registrationDate) ?: registrationDate
    val publicationDateNew = DateConverter
        .convertDate(COURT_DECISION_DATE_FORMAT, publicationDate) ?: publicationDate

    return SearchInstanteJusticeItemUi.CourtDecisionItemUi(
        caseNumber = this.caseNumber,
        caseName = this.caseName,
        courtName = this.courtName,
        judgeName = this.judgeName,
        caseSubject = caseSubjectNew,
        dateOfPronouncement = dateOfPronounNew,
        registrationDate = registrationDateNew,
        publicationDate = publicationDateNew,
        pdfUrlPath = this.pdfUrlPath
    )
}

fun InstanteJusticeItem.CourtRulingItem.asUi(): SearchInstanteJusticeItemUi.CourtRulingItemUi {
    val caseSubjectNew = this.caseSubject.sentenceFormat()

    val registrationDateNew = DateConverter
        .convertDate(COURT_DECISION_DATE_FORMAT, registrationDate) ?: registrationDate
    val publicationDateNew = DateConverter
        .convertDate(COURT_DECISION_DATE_FORMAT, publicationDate) ?: publicationDate

    return SearchInstanteJusticeItemUi.CourtRulingItemUi(
        caseNumber = this.caseNumber,
        caseName = this.caseName,
        courtName = this.courtName,
        judgeName = this.judgeName,
        caseSubject = caseSubjectNew,
        registrationDate = registrationDateNew,
        publicationDate = publicationDateNew,
        pdfUrlPath = this.pdfUrlPath
    )
}

fun InstanteJusticeItem.PublicSummonItem.asUi(): SearchInstanteJusticeItemUi.PublicSummonItemUi {
    val caseObjectNew = this.caseObject.sentenceFormat()

    val hearingDateNew = DateConverter.convertDate(DEFAULT_DATE_FORMAT, hearingDate) ?: hearingDate
    val publicationDateNew = DateConverter
        .convertDate(DEFAULT_DATE_FORMAT, publicationDate) ?: publicationDate

    return SearchInstanteJusticeItemUi.PublicSummonItemUi(
        caseName = this.caseName,
        caseNumber = this.caseNumber,
        courtName = this.courtName,
        courtRoom = this.courtRoom,
        judgeName = this.judgeName,
        caseObject = caseObjectNew,
        publicationDate = publicationDateNew,
        summonedPerson = this.summonedPerson,
        hearingDate = hearingDateNew,
        hearingHour = this.hearingHour,
        pdfUrlPath = this.pdfUrlPath
    )
}

fun InstanteJusticeItem.CourtClaimAndCaseItem.asUi(): SearchInstanteJusticeItemUi.CourtClaimAndCaseItemUi {
    val registrationDateNew = DateConverter
        .convertDate(COURT_DECISION_DATE_FORMAT, registrationDate) ?: registrationDate

    return SearchInstanteJusticeItemUi.CourtClaimAndCaseItemUi(
        caseNumber = this.caseNumber,
        caseReferenceNumber = this.caseReferenceNumber,
        caseName = this.caseName,
        courtName = this.courtName,
        caseStatus = this.caseStatus,
        caseType = caseType,
        registrationDate = registrationDateNew,
        pdfUrlPath = this.pdfUrlPath
    )
}