package com.pichurchyk.nutrition.model.goals

import com.pichurchyk.nutrition.database.model.IntakeType

data class NutritionGoal(
    val intakeType: IntakeType,
    val value: Int
) {
    val goalCalories = intakeType.calories * value
}