package com.pichurchyk.fitness.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TrainingInfo")
data class TrainingInfoDBO(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val name: String,
    val description: String,
    val type: TrainingType
)