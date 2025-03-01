package com.pichurchyk.nutrition.usecase

import com.pichurchyk.nutrition.model.create.CreateWaterIntakeModel
import com.pichurchyk.nutrition.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow

class SaveWaterIntakeUseCase(
    private val repository: NutritionRepository
) {
    suspend fun invoke(intake: CreateWaterIntakeModel): Flow<Unit> = repository.saveWaterIntake(intake)
}