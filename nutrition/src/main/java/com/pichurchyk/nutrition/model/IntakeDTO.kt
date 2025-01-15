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
) {
    companion object {
        fun empty(date: Date, type: IntakeType) = IntakeDTO(
            date = date,
            value = 0,
            type = type,
            id = 0
        )

        fun getIntakesDtoByMainTypes(date: Date) = IntakeType.getMainTypes().map {  type ->
            empty(date, type)
        }
    }
}