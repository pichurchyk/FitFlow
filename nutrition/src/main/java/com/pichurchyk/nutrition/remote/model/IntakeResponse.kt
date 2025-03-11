package com.pichurchyk.nutrition.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class IntakeResponse(
    val id: Long = 0,
    val date: Long,
    val values: List<IntakeValueResponse>,
    val calories: Int
)