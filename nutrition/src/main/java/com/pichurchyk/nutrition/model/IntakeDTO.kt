package com.pichurchyk.nutrition.model

import com.pichurchyk.fitflow.common.utils.date.DateSerializer
import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class IntakeDTO(
    val id: Long = 0,

    @Serializable(with = DateSerializer::class)
    val date: Date,

    val value: Int,
    val type: IntakeType
)