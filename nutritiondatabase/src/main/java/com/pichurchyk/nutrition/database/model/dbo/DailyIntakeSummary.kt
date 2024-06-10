package com.pichurchyk.nutrition.database.model.dbo

import androidx.room.DatabaseView
import com.pichurchyk.nutrition.database.model.IntakeType
import java.util.Date

@DatabaseView("SELECT date, type, SUM(value) AS totalValue, SUM(calories) AS totalCalories FROM Intake GROUP BY date, type")
data class DailyIntakeSummary(
    val date: Date,
    val type: IntakeType,
    val totalValue: Double,
    val totalCalories: Int
)