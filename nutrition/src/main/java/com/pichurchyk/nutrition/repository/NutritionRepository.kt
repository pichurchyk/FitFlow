package com.pichurchyk.nutrition.repository

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.DailyInfoDTO
import com.pichurchyk.nutrition.model.IntakeDTO
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NutritionRepository {
    suspend fun saveIntake(intake: IntakeDTO): Flow<Unit>

    suspend fun removeIntake(intake: IntakeDTO): Flow<Unit>

    suspend fun getAllIntakesByDateAndType(date: Date, type: IntakeType): Flow<List<IntakeDTO>>

    suspend fun getDailyInfo(date: Date, needFetchWithRemote: Boolean = true): Flow<DailyInfoDTO>
}