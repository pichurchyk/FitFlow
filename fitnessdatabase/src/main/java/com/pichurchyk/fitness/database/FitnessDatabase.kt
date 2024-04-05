package com.pichurchyk.fitness.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pichurchyk.fitness.database.model.ExerciseDBO
import com.pichurchyk.fitness.database.model.ExerciseRecordDBO
import com.pichurchyk.fitness.database.model.TrainingDBO
import com.pichurchyk.fitness.database.model.TrainingExerciseCrossRef
import com.pichurchyk.fitness.database.model.TrainingInfoDBO
import com.pichurchyk.fitness.database.model.TrainingScheduleDBO

@Database(
    entities = [
        ExerciseDBO::class,
        ExerciseRecordDBO::class,
        TrainingDBO::class,
        TrainingExerciseCrossRef::class,
        TrainingInfoDBO::class,
        TrainingScheduleDBO::class],
    version = 1
)
abstract class FitnessDatabase : RoomDatabase() {
    abstract fun dao(): FitnessDao
}

fun FitnessDatabase(applicationContext: Context) {
    val db = Room.databaseBuilder(
        applicationContext,
        FitnessDatabase::class.java,
        "fitness"
    ).build()
}