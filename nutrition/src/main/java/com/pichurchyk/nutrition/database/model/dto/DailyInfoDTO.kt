package com.pichurchyk.nutrition.database.model.dto

import java.util.Date

data class DailyInfoDTO(
    val date: Date,
    val intakes: List<IntakeDTO>,
    val caloriesSum: Int
)