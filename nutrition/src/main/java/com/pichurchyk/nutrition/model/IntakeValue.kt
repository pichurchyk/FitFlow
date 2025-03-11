package com.pichurchyk.nutrition.model

import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.serialization.Serializable

@Serializable
data class IntakeValue(
    val value: Int,
    val intakeType: IntakeType
)