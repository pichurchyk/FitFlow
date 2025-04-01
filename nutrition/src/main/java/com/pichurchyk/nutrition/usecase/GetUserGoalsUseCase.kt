package com.pichurchyk.nutrition.usecase

import com.pichurchyk.nutrition.model.goals.NutritionGoal
import com.pichurchyk.nutrition.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow

interface GetUserGoalsUseCase {
    suspend fun invoke(): Flow<List<NutritionGoal>>
}

internal class GetUserGoalsUseCaseImpl(
    private val repository: NutritionRepository
) : GetUserGoalsUseCase {
    override suspend fun invoke() = repository.getUserGoals()

}