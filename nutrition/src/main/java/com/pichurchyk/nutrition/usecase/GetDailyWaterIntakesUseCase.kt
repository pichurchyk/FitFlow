package com.pichurchyk.nutrition.usecase

import com.pichurchyk.nutrition.repository.NutritionRepository
import java.util.Date

class GetDailyWaterIntakesUseCase(
    private val repository: NutritionRepository
) {
    suspend fun invoke(date: Date) = repository.getDailyWaterIntakes(date)
}