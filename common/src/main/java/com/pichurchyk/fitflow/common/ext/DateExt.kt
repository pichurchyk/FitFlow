package com.pichurchyk.fitflow.common.ext

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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

fun Date.toDateString(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(this)
}

fun Date.isToday() : Boolean {
    val today = Date().toDateString()
    return this.toDateString() == today
}