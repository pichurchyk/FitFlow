package com.pichurchyk.fitness.database

import androidx.room.Dao
import androidx.room.Query
import com.pichurchyk.fitness.database.model.TrainingScheduleDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface FitnessDao {

    @Query("SELECT * FROM TrainingSchedule")
    suspend fun getAll(): Flow<List<TrainingScheduleDBO>>

}