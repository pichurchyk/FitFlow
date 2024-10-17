package com.pichurchyk.nutrition.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenPayload(
    @SerialName("refresh_token")
    val refreshToken: String
)