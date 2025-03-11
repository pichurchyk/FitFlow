package com.pichurchyk.fitflow.common.ext.date

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pichurchyk.fitflow.common.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import com.pichurchyk.fitflow.common.utils.date.DateFormat

fun Date.toStartOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getDefault()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

fun Date.toEndOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getDefault()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.time
}

fun Date.getNextDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_YEAR, 1)
    return calendar.time
}

fun Date.getPreviousDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    return calendar.time
}

fun Date.toDateString(format: DateFormat = DateFormat.FULL_DATE): String {
    val formatter = SimpleDateFormat(format.pattern, Locale.getDefault())
    return formatter.format(this)
}

fun Date.isToday() : Boolean {
    val today = Date().toDateString()
    return this.toDateString() == today
}

fun Date.isTomorrow(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = Date()
    calendar.add(Calendar.DAY_OF_YEAR, 1)
    val tomorrow = calendar.time.toDateString()
    return this.toDateString() == tomorrow
}

fun Date.isYesterday(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = Date()
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    val yesterday = calendar.time.toDateString()
    return this.toDateString() == yesterday
}

fun Date.isCurrentMonth(): Boolean {
    val currentCalendar = Calendar.getInstance()
    val dateCalendar = Calendar.getInstance().apply { time = this@isCurrentMonth }
    return currentCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR) &&
            currentCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)
}

fun Date.isCurrentYear(): Boolean {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val dateYear = Calendar.getInstance().apply { time = this@isCurrentYear }.get(Calendar.YEAR)
    return currentYear == dateYear
}

@Composable
fun Date.toPrettyString(): String {
    return when {
        this.isToday() -> stringResource(id = R.string.today)
        this.isTomorrow() -> stringResource(id = R.string.tomorrow)
        this.isYesterday() -> stringResource(id = R.string.yesterday)
        this.isCurrentMonth() -> this.toDateString(format = DateFormat.DAY)
        this.isCurrentYear() -> this.toDateString(format = DateFormat.DAY_SHORT_MONTH)
        else -> this.toDateString()
    }
}