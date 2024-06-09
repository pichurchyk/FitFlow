package com.pichurchyk.nutrition.database.repository

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dto.DailyInfoDTO
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO
import java.util.Date

interface NutritionDatabaseRepository {
    suspend fun saveIntake(intake: IntakeDTO)

    suspend fun removeIntake(intake: IntakeDTO)

    suspend fun getAllIntakesByDateAndType(date: Date, type: IntakeType): List<IntakeDTO>

    suspend fun getDailyInfo(date: Date): DailyInfoDTO
}