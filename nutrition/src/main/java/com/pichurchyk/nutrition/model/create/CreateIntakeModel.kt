package com.pichurchyk.nutrition.model.create

import com.pichurchyk.fitflow.common.utils.date.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class CreateIntakeModel(
    @Serializable(with = DateSerializer::class)
    val date: Date = Date(),

    val values: List<CreateIntakeValueModel> = emptyList(),
    val calories: Int = 0,
)