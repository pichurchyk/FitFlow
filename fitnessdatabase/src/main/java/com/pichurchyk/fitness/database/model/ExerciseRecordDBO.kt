package com.pichurchyk.fitness.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "ExerciseRecord",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseDBO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("exercises")
        )
    ]
)
data class ExerciseRecordDBO(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo("weight") val weight: Double,
    @ColumnInfo("sets") val sets: Int,
    @ColumnInfo("reps") val reps: Int,
    @ColumnInfo("date") val date: Date,

    @ColumnInfo("exercises") val exercise: ExerciseDBO
)