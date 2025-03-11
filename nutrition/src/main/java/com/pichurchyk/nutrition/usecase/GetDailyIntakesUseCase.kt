package com.pichurchyk.nutrition.usecase

import com.pichurchyk.nutrition.repository.NutritionRepository
import java.util.Date

class GetDailyIntakesUseCase(
    private val repository: NutritionRepository
) {
    suspend fun invoke(date: Date) = repository.getDailyIntakes(date)
}