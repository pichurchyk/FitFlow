package com.pichurchyk.nutrition.usecase

import com.pichurchyk.nutrition.repository.NutritionRepository
import com.pichurchyk.nutrition.model.IntakeDTO
import kotlinx.coroutines.flow.Flow

class SaveIntakeUseCase(
    private val repository: NutritionRepository
) {
    suspend fun invoke(intake: IntakeDTO): Flow<Unit> = repository.saveIntake(intake)
}