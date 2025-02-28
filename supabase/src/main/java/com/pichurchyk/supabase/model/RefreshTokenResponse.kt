package com.pichurchyk.supabase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    @SerialName("refresh_token")
    val refreshToken: String,

    @SerialName("access_token")
    val accessToken: String
)