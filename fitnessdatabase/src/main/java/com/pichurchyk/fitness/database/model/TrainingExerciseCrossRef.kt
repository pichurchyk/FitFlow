package com.pichurchyk.fitness.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["trainingInfoId", "exerciseId"], tableName = "TrainingExerciseCrossRef")
data class TrainingExerciseCrossRef(
    val trainingInfoId: Long,
    val exerciseId: Long
)