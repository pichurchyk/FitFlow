package com.pichurchyk.profile.ui.viewmodel

import com.pichurchyk.nutrition.model.NutritionGoal

sealed class ProfileIntent {
    data object LoadInfo: ProfileIntent()
    data class OnNutritionGoalChanged(val goal: NutritionGoal): ProfileIntent()
}