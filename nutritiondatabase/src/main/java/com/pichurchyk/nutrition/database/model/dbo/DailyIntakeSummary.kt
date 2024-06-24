package com.pichurchyk.nutrition.database.model.dbo

import androidx.room.DatabaseView
import com.pichurchyk.nutrition.database.model.IntakeType
import java.util.Date

@DatabaseView("SELECT date, type, SUM(value) AS totalValue, id FROM Intake GROUP BY date, type, id")
data class DailyIntakeSummary(
    val date: Date,
    val type: IntakeType,
    val totalValue: Int,
    val id: Long
)