package com.pichurchyk.nutrition.usecase

import com.pichurchyk.nutrition.repository.NutritionRepository
import java.util.Date

class GetDailyInfoUseCase(
    private val repository: NutritionRepository
) {
    suspend fun invoke(date: Date) = repository.getDailyInfo(date)
}