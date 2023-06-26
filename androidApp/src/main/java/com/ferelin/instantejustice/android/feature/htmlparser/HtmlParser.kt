package com.ferelin.instantejustice.android.feature.htmlparser

import com.ferelin.instantejustice.domain.InstanteJusticeItem.CourtClaimAndCaseItem
import com.ferelin.instantejustice.domain.InstanteJusticeItem.CourtDecisionItem
import com.ferelin.instantejustice.domain.InstanteJusticeItem.CourtRulingItem
import com.ferelin.instantejustice.domain.InstanteJusticeItem.HearingsAgendaItem
import com.ferelin.instantejustice.domain.InstanteJusticeItem.PublicSummonItem
import com.ferelin.instantejustice.feature.htmlparser.HtmlParser
import org.jsoup.Jsoup
import org.koin.dsl.module

val htmlParserFeatureModule = module {
    factory<HtmlParser> { HtmlParserImpl() }
}

internal class HtmlParserImpl : HtmlParser {

    override fun parseHearingsAgenda(html: String): Result<List<HearingsAgendaItem>> {
        return parseHtmlPage(html, pdfIndex = 10) { cellData, pdfUrl ->
            HearingsAgendaItem(
                courtName = cellData[0],
                caseNumber = cellData[1],
                judgeName = cellData[2],
                hearingDate = cellData[3],
                hearingHour = cellData[4],
                courtRoom = cellData[5],
                caseName = cellData[6],
                caseObject = cellData[7],
                caseType = cellData[8],
                hearingResult = cellData[9],
                pdfUrlPath = pdfUrl
            )
        }
    }

    override fun parseCourtDecisions(html: String): Result<List<CourtDecisionItem>> {
        return parseHtmlPage(html, pdfIndex = 9) { cellData, pdfUrl ->
            CourtDecisionItem(
                courtName = cellData[0],
                caseNumber = cellData[1],
                caseName = cellData[2],
                dateOfPronouncement = cellData[3],
                registrationDate = cellData[4],
                publicationDate = cellData[5],
                caseType = cellData[6],
                caseSubject = cellData[7],
                judgeName = cellData[8],
                pdfUrlPath = pdfUrl
            )
        }
    }

    override fun parseCourtRulings(html: String): Result<List<CourtRulingItem>> {
        return parseHtmlPage(html, pdfIndex = 8) { cellData, pdfUrl ->
            CourtRulingItem(
                courtName = cellData[0],
                caseNumber = cellData[1],
                caseName = cellData[2],
                registrationDate = cellData[3],
                publicationDate = cellData[4],
                caseType = cellData[5],
                caseSubject = cellData[6],
                judgeName = cellData[7],
                pdfUrlPath = pdfUrl
            )
        }
    }

    override fun parsePublicSummons(html: String): Result<List<PublicSummonItem>> {
        return parseHtmlPage(html, pdfIndex = 10) { cellData, pdfUrl ->
            PublicSummonItem(
                courtName = cellData[0],
                caseNumber = cellData[1],
                caseName = cellData[2],
                caseObject = cellData[3],
                hearingDate = cellData[4],
                hearingHour = cellData[5],
                summonedPerson = cellData[6],
                courtRoom = cellData[7],
                judgeName = cellData[8],
                publicationDate = cellData[9],
                pdfUrlPath = pdfUrl
            )
        }
    }

    override fun parseCourtClaimsAndCases(html: String): Result<List<CourtClaimAndCaseItem>> {
        return parseHtmlPage(html, pdfIndex = 7) { cellData, pdfUrl ->
            CourtClaimAndCaseItem(
                courtName = cellData[0],
                caseNumber = cellData[1],
                caseReferenceNumber = cellData[2],
                registrationDate = cellData[3],
                caseStatus = cellData[4],
                caseType = cellData[5],
                caseName = cellData[6],
                pdfUrlPath = pdfUrl
            )
        }
    }

    private fun <T> parseHtmlPage(
        html: String,
        pdfIndex: Int,
        transform: (fields: List<String>, pdfUrl: String) -> T
    ): Result<List<T>> = try {
        val document = Jsoup.parse(html)
        val tableWithData = document.getElementsByTag("tbody").first()!!
        val tableRows = tableWithData.getElementsByTag("tr")

        val result = tableRows.map { tableRow ->
            val tableRowCells = tableRow.getElementsByTag("td")
            val cellData = tableRowCells.map { it.text() }
            val pdfUrl = tableRowCells[pdfIndex].getElementsByTag("a").first()!!.attr("href")
            transform(cellData, pdfUrl)
        }

        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }
}