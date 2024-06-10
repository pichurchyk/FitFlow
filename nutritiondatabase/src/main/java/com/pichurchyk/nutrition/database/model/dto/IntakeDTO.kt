package com.pichurchyk.nutrition.database.model.dto

import com.pichurchyk.nutrition.database.model.IntakeType
import java.util.Date

data class IntakeDTO(
    val date: Date,
    val value: Double,
    val calories: Int,
    val type: IntakeType
) {
    companion object {
        fun empty(date: Date, type: IntakeType) = IntakeDTO(
            date = date,
            value = 0.0,
            calories = 0,
            type = type
        )
    }
}