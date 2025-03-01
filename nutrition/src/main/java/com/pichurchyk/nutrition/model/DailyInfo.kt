package com.pichurchyk.nutrition.model

import java.util.Date

data class DailyInfo(
    val date: Date,
    val intakes: List<Intake>
)