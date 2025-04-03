package com.pichurchyk.profile.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserParamsResponse(
    val height: Int,
    val weight: Int,
    val age: Int
)