package com.pichurchyk.fitflow.common.utils.date

enum class DateFormat(val pattern: String) {
    FULL_DATE("d MMMM yyyy"),
    DAY_FULL_MONTH("d MMMM"),
    DAY_SHORT_MONTH("d MMM"),
    DAY("d")
}