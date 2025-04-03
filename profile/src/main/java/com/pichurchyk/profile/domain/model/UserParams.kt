package com.pichurchyk.profile.domain.model

import kotlin.math.roundToInt

data class UserParams(
    val weightGrams: Int,
    val heightCm: Int,
    val age: Int
) {
    val weightKg: Double = (weightGrams / 100.0).roundToInt() / 10.0
}