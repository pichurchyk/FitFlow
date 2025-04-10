package com.pichurchyk.profile.ui.viewmodel

import com.pichurchyk.fitflow.auth.model.User
import com.pichurchyk.nutrition.model.goals.NutritionGoal
import com.pichurchyk.profile.domain.model.UserParams

sealed class ProfileViewState {
    data object Loading: ProfileViewState()
    data class Error(val message: String): ProfileViewState()
    data class Loaded(
        val userData: User,
        val userParams: UserParams? = null,
        val nutritionGoals: NutritionGoalsViewState = NutritionGoalsViewState.Loading(emptyList())
        ): ProfileViewState()
}

sealed class NutritionGoalsViewState(open val values: List<NutritionGoal>) {
    data class Loading(override val values: List<NutritionGoal>) : NutritionGoalsViewState(values)
    data class Loaded(override val values: List<NutritionGoal>) : NutritionGoalsViewState(values)
    data class Changing(override val values: List<NutritionGoal>, val newValues: List<NutritionGoal>) : NutritionGoalsViewState(values)
    data class Error(override val values: List<NutritionGoal>, val message: String) : NutritionGoalsViewState(values)
}