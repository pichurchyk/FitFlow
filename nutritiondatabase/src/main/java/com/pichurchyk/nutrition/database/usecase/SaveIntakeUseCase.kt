package com.pichurchyk.nutrition.database.usecase

import com.pichurchyk.nutrition.database.model.dto.IntakeDTO
import com.pichurchyk.nutrition.database.repository.NutritionDatabaseRepository

class SaveIntakeUseCase(
    private val repository: NutritionDatabaseRepository
) {
    suspend fun invoke(intake: IntakeDTO) = repository.saveIntake(intake)
}