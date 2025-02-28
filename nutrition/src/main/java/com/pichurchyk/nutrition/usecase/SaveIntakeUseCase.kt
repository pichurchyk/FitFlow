package com.pichurchyk.nutrition.usecase

import com.pichurchyk.nutrition.model.CreateIntakeModel
import com.pichurchyk.nutrition.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow

class SaveIntakeUseCase(
    private val repository: NutritionRepository
) {
    suspend fun invoke(intake: CreateIntakeModel): Flow<Unit> = repository.saveIntake(intake)
}