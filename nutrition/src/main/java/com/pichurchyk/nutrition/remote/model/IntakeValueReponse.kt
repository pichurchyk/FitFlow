package com.pichurchyk.nutrition.remote.model

import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.serialization.Serializable

@Serializable
data class IntakeValueResponse(
    val value: Int,
    val type: IntakeType,
)