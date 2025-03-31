package com.pichurchyk.nutrition.model

import com.pichurchyk.nutrition.database.model.IntakeType

data class NutritionGoal(
    val intakeType: IntakeType,
    val goal: Int
) {
    val goalCalories = intakeType.calories * goal
}