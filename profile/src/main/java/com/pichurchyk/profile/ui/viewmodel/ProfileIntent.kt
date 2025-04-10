package com.pichurchyk.profile.ui.viewmodel

import com.pichurchyk.nutrition.model.goals.NutritionGoal

sealed class ProfileIntent {
    data object LoadInfo: ProfileIntent()
    data class ChangeNutritionGoal(val goal: NutritionGoal): ProfileIntent()
    data object SaveChangedNutritionGoals: ProfileIntent()
    data object DiscardChangedNutritionGoals: ProfileIntent()
}