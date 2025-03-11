package com.pichurchyk.nutrition.database.model.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "WaterIntake")
data class WaterIntakeDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val value: Int,
)