package com.pichurchyk.nutrition.database.model.dbo

import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.serialization.Serializable

@Serializable
data class IntakeValueDBO(
    val value: Int,
    val intakeType: IntakeType
)