package com.pichurchyk.nutrition.database.model.dto

import com.pichurchyk.nutrition.database.model.IntakeType
import java.util.Date

data class IntakeDTO(
    val id: Long = 0,
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