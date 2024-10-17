package com.pichurchyk.nutrition.model

import java.util.Date

data class DailyInfoDTO(
    val date: Date,
    val intakes: List<IntakeDTO>,
    val caloriesSum: Int
)