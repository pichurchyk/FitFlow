package com.pichurchyk.profile.domain.ext

import com.pichurchyk.profile.data.model.response.UserParamsResponse
import com.pichurchyk.profile.domain.model.UserParams

fun UserParamsResponse.toDomain(): UserParams = UserParams(
    heightCm = this.height,
    weightGrams = this.weight,
    age = this.age
)