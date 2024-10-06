package com.pichurchyk.nutrition.usecase

import com.pichurchyk.nutrition.repository.NutritionRepository
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO
import kotlinx.coroutines.flow.Flow

class SaveIntakeUseCase(
    private val repository: NutritionRepository
) {
    suspend fun invoke(intake: IntakeDTO): Flow<Unit> = repository.saveIntake(intake)
}