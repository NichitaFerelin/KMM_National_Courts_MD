package com.ferelin.instantejustice.feature.htmlparser

import com.ferelin.instantejustice.domain.InstanteJusticeItem

interface HtmlParser {
    fun parseHearingsAgenda(html: String): Result<List<InstanteJusticeItem.HearingsAgendaItem>>
    fun parseCourtDecisions(html: String): Result<List<InstanteJusticeItem.CourtDecisionItem>>
    fun parseCourtRulings(html: String): Result<List<InstanteJusticeItem.CourtRulingItem>>
    fun parsePublicSummons(html: String): Result<List<InstanteJusticeItem.PublicSummonItem>>
    fun parseCourtClaimsAndCases(html: String): Result<List<InstanteJusticeItem.CourtClaimAndCaseItem>>

    class EmptyResultException: Exception("No items found to parse object")
}