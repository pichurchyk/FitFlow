package com.pichurchyk.nutrition.database.model.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pichurchyk.nutrition.database.model.IntakeType
import java.util.Date

@Entity(tableName = "Intake")
data class IntakeDBO(
    @PrimaryKey(autoGenerate = false)
    val date: Date,
    val value: Double,
    val type: IntakeType
)