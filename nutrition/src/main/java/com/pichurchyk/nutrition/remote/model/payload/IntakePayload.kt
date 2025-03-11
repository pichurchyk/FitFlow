package com.pichurchyk.nutrition.remote.model.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IntakePayload(
    val date: Long,
    val values: List<IntakeValuePayload>,
    val calories: Int,

    @SerialName("user_id")
    val userUuid: String
)