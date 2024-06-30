package com.pichurchyk.nutrition.database.usecase

import com.pichurchyk.nutrition.database.model.dto.IntakeDTO
import com.pichurchyk.nutrition.database.repository.NutritionDatabaseRepository
import kotlinx.coroutines.flow.Flow

class SaveIntakeUseCase(
    private val repository: NutritionDatabaseRepository
) {
    suspend fun invoke(intake: IntakeDTO): Flow<Unit> = repository.saveIntake(intake)
}