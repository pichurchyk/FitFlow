package com.pichurchyk.nutrition.model.create

import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.serialization.Serializable

@Serializable
data class CreateIntakeValueModel(
    val value: Int = 0,
    val intakeType: IntakeType
)