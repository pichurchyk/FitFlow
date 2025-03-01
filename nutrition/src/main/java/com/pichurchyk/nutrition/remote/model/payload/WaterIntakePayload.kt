package com.pichurchyk.nutrition.remote.model.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WaterIntakePayload(
    val date: Long,
    val value: Int,

    @SerialName("user_id")
    val userUuid: String
)