package com.pichurchyk.nutrition.model.goals

import com.pichurchyk.nutrition.database.model.IntakeType

data class NutritionGoal(
    val id: Int,
    val intakeType: IntakeType,
    val value: Int
) {
    val goalCalories = intakeType.calories * value
}