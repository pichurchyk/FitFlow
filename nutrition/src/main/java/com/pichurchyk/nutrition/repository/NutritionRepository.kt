package com.pichurchyk.nutrition.repository

import com.pichurchyk.nutrition.model.create.CreateIntakeModel
import com.pichurchyk.nutrition.model.DailyInfo
import com.pichurchyk.nutrition.model.Intake
import com.pichurchyk.nutrition.model.WaterIntake
import com.pichurchyk.nutrition.model.create.CreateWaterIntakeModel
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NutritionRepository {
    suspend fun saveIntake(intake: CreateIntakeModel): Flow<Unit>

    suspend fun saveWaterIntake(intake: CreateWaterIntakeModel): Flow<Unit>

    suspend fun removeIntake(intake: Intake): Flow<Unit>

    suspend fun getDailyIntakes(date: Date): Flow<DailyInfo>

    suspend fun getDailyWaterIntakes(date: Date): Flow<List<WaterIntake>>

    suspend fun fetchRemoteAndLocal(date: Date)
}