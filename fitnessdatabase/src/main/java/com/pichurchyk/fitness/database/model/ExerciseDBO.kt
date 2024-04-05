package com.pichurchyk.fitness.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Exercise")
data class ExerciseDBO(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo("name") val name: String,
)