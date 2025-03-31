package com.pichurchyk.profile.ui.viewmodel

import com.pichurchyk.fitflow.auth.model.User
import com.pichurchyk.nutrition.model.NutritionGoal
import com.pichurchyk.profile.domain.model.UserParams

sealed class ProfileViewState {
    data object Loading: ProfileViewState()
    data class Error(val message: String): ProfileViewState()
    data class Loaded(
        val userData: User,
        val userParams: UserParams? = null,
        val nutritionGoals: List<NutritionGoal>? = null
    ): ProfileViewState()
}