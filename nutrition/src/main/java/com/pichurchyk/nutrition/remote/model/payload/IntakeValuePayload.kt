package com.pichurchyk.nutrition.remote.model.payload

import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.serialization.Serializable

@Serializable
data class IntakeValuePayload(
    val value: Int,
    val intakeType: IntakeType,
)