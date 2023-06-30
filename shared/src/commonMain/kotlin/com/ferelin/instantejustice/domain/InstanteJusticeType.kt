package com.ferelin.instantejustice.domain

enum class InstanteJusticeType(val key: String) {
    HEARINGS_AGENDA("agenda-sedintelor"),
    COURT_DECISION("hotaririle-instantei"),
    COURT_RULINGS("incheierile-instantei"),
    PUBLIC_SUMMONS("citatii-publice"),
    COURT_CLAIMS_CASES("cereri-si-doasare-pendite");
}