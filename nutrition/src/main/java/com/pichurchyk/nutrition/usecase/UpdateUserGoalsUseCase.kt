package com.pichurchyk.nutrition.usecase

import com.pichurchyk.nutrition.model.goals.NutritionGoal
import com.pichurchyk.nutrition.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow

interface UpdateUserGoalsUseCase {
    suspend fun invoke(goals: List<NutritionGoal>): Flow<List<NutritionGoal>>
}

internal class UpdateUserGoalsUseCaseImpl(
    private val repository: NutritionRepository
) : UpdateUserGoalsUseCase {
    override suspend fun invoke(goals: List<NutritionGoal>) = repository.updateUserGoals(goals)

}