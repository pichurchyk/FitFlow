package com.pichurchyk.nutrition.remote.model

import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.serialization.Serializable

@Serializable
data class IntakePayload(
    val date: Long,
    val value: Int,
    val type: IntakeType
)