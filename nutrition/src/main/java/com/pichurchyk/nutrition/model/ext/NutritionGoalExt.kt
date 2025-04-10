package com.pichurchyk.nutrition.model.ext

import com.pichurchyk.nutrition.model.goals.NutritionGoal
import com.pichurchyk.nutrition.remote.model.NutritionGoalResponse
import com.pichurchyk.nutrition.remote.model.payload.NutritionGoalPayload

fun NutritionGoalResponse.toDomain(): NutritionGoal = NutritionGoal(
    id = this.id,
    intakeType = this.intakeType,
    value = this.value,
)

fun NutritionGoal.toPayload(): NutritionGoalPayload = NutritionGoalPayload(
    id = this.id,
    intakeType = this.intakeType,
    value = this.value
)
