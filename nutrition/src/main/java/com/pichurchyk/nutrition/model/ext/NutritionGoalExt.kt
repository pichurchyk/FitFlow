package com.pichurchyk.nutrition.model.ext

import com.pichurchyk.nutrition.model.goals.NutritionGoal
import com.pichurchyk.nutrition.remote.model.NutritionGoalsResponse

fun NutritionGoalsResponse.toDomain(): NutritionGoal = NutritionGoal(
    intakeType = this.intakeType,
    value = this.value
)
