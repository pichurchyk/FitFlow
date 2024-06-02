package com.pichurchyk.nutrition.database.model.dbo

import androidx.room.Entity
import com.pichurchyk.nutrition.database.model.IntakeType
import java.util.Date

@Entity(tableName = "Intake")
data class IntakeDBO(
    val date: Date,
    val value: Double,
    val type: IntakeType
)