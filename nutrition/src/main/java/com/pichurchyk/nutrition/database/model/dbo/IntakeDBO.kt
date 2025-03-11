package com.pichurchyk.nutrition.database.model.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Intake")
data class IntakeDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val values: List<IntakeValueDBO>,
    val calories: Int,
)