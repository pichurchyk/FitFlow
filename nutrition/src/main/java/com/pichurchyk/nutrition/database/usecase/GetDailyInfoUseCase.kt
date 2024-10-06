package com.pichurchyk.nutrition.database.usecase

import com.pichurchyk.nutrition.database.repository.NutritionDatabaseRepository
import java.util.Date

class GetDailyInfoUseCase(
    private val repository: NutritionDatabaseRepository
) {
    suspend fun invoke(date: Date) = repository.getDailyInfo(date)
}