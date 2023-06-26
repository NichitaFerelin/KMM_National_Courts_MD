package com.ferelin.instantejustice.domain

val DEFAULT_INSTANTE_JUSTIPE_TYPE = InstanteJusticeType.HEARINGS_AGENDA

enum class InstanteJusticeType(val key: String) {
    HEARINGS_AGENDA("agenda-sedintelor"),
    COURT_DECISION("hotaririle-instantei"),
    COURT_RULINGS("incheierile-instantei"),
    PUBLIC_SUMMONS("citatii-publice"),
    COURT_CLAIMS_CASES("cereri-si-doasare-pendite");
}

fun String.toInstanteJusticeType(): InstanteJusticeType = when {
    this == InstanteJusticeType.HEARINGS_AGENDA.key -> InstanteJusticeType.HEARINGS_AGENDA
    this == InstanteJusticeType.COURT_DECISION.key -> InstanteJusticeType.COURT_DECISION
    this == InstanteJusticeType.COURT_RULINGS.key -> InstanteJusticeType.COURT_RULINGS
    this == InstanteJusticeType.PUBLIC_SUMMONS.key -> InstanteJusticeType.PUBLIC_SUMMONS
    this == InstanteJusticeType.COURT_CLAIMS_CASES.key -> InstanteJusticeType.COURT_CLAIMS_CASES
    else -> error("Cannot map [$this] into InstanteJusticeType class")
}
