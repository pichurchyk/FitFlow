package com.pichurchyk.nutrition.database.model.dto

import com.pichurchyk.nutrition.database.model.IntakeType
import java.util.Date

data class IntakeDTO(
    val date: Date,
    val value: Double,
    val type: IntakeType
)

fun List<IntakeDTO>.getIntakesSum(): Double {
    return this.sumOf { intake -> intake.value }
}