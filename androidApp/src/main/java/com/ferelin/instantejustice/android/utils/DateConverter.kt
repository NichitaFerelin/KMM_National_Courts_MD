package com.ferelin.instantejustice.android.utils

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import java.util.Locale


private const val TARGET_DATE_FORMAT = "dd MMM yyyy"

const val DEFAULT_DATE_FORMAT = "dd-MM-yyyy"
const val COURT_DECISION_DATE_FORMAT = "yyyy-MM-dd"

object DateConverter {
    fun convertDate(defaultFormat: String, defaultDate: String): String? = try {
        val defaultFormatDate: DateFormat = SimpleDateFormat(defaultFormat, Locale.getDefault())
        val targetFormatDate: DateFormat = SimpleDateFormat(TARGET_DATE_FORMAT, Locale.getDefault())
        val date = defaultFormatDate.parse(defaultDate)!!
        targetFormatDate.format(date)
    } catch (e: Exception) {
        null
    }
}