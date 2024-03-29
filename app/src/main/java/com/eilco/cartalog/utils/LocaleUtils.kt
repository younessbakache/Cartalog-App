package com.vitor238.cartalog.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object LocaleUtils {
    fun getLanguage(): String {
        return Locale.getDefault().toLanguageTag()
    }

    fun getCountry(): String {
        return Locale.getDefault().country
    }

    fun parseDate(date: String?): String {
        val tmdbPattern = "yyyy-MM-dd"
        val localizedDateFormat: DateFormat =
            DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault())
        val originalDateFormat = SimpleDateFormat(tmdbPattern, Locale.ENGLISH)

        return try {
            val parsedDate = originalDateFormat.parse(date!!)
            localizedDateFormat.format(parsedDate!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}