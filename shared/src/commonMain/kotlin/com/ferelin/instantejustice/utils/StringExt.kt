package com.ferelin.instantejustice.utils

fun String.sentenceFormat() : String = runCatching {
    val withFirstCapital = this[0].uppercaseChar().plus(this.substring(1))
    if (withFirstCapital.last() == '.') withFirstCapital else "$withFirstCapital."
}.getOrNull() ?: this