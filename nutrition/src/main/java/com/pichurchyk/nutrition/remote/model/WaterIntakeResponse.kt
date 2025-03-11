package com.pichurchyk.nutrition.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WaterIntakeResponse(
    val id: Long = 0,
    val date: Long,
    val value: Int,
    val calories: Int
)