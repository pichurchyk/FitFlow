package com.pichurchyk.nutrition.remote.model

import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NutritionGoalResponse(
    val id: Int,

    @SerialName("intake_type")
    val intakeType: IntakeType,

    val value: Int,
)