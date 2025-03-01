package com.pichurchyk.nutrition.model

import com.pichurchyk.fitflow.common.utils.date.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Intake(
    val id: Long = 0,

    @Serializable(with = DateSerializer::class)
    val date: Date,

    val values: List<IntakeValue>,
    val calories: Int,
)