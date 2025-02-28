package com.pichurchyk.nutrition.model

import com.pichurchyk.fitflow.common.utils.date.DateSerializer
import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class CreateIntakeModel(
    @Serializable(with = DateSerializer::class)
    val date: Date,

    val value: Int,
    val type: IntakeType
) {
    companion object {
        fun empty(date: Date, type: IntakeType) = CreateIntakeModel(
            date = date,
            value = 0,
            type = type,
        )

        fun getIntakesDtoByMainTypes(date: Date) = IntakeType.getMainTypes().map { type ->
            empty(date, type)
        }
    }
}