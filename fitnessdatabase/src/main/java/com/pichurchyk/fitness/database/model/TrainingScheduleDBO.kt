package com.pichurchyk.fitness.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TrainingSchedule")
data class TrainingScheduleDBO(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val trainingId: Long,
    val dayOfWeek: Int,
)