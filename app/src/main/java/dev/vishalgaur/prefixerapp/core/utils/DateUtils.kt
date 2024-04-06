package dev.vishalgaur.prefixerapp.core.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    const val FORMAT_DATE_YYYY_MM_DD_Time = "yyyy-MM-dd HH:mm:ss"
    const val FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd"

    fun parseDateString(dateString: String): Date? {
        val format = SimpleDateFormat(FORMAT_DATE_YYYY_MM_DD_Time, Locale.getDefault())
        return try {
            format.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    fun formatDateToAFormat(date: Date, format: String): String? {
        val outputDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return try {
            outputDateFormat.format(date)
        } catch (e: Exception) {
            null
        }
    }

    fun getWeekDayFromDateString(dateString: String): String {
        val dateFormat = SimpleDateFormat(FORMAT_DATE_YYYY_MM_DD, Locale.getDefault())
        try {
            val date: Date? = dateFormat.parse(dateString)
            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }
            return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) ?: dateString
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dateString
    }
}
