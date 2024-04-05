package com.pichurchyk.fitness.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
@Entity(tableName = "Training")
data class TrainingDBO(
    @Embedded val trainingInfo: TrainingInfoDBO,
    @Relation(
        parentColumn = "trainingInfoId",
        entityColumn = "exerciseId",
        associateBy = Junction(TrainingExerciseCrossRef::class)
    )
    val exercises: List<ExerciseDBO>
)