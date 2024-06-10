package com.pichurchyk.nutrition.database.mapper

import com.pichurchyk.nutrition.database.model.dbo.IntakeDBO
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO

object IntakeMapper {

    fun fromDto(dto: IntakeDTO): IntakeDBO = IntakeDBO(
        date = dto.date,
        value = dto.value,
        type = dto.type,
        calories = dto.calories
    )

    fun fromDbo(dbo: IntakeDBO): IntakeDTO = IntakeDTO(
        date = dbo.date,
        value = dbo.value,
        type = dbo.type,
        calories = dbo.calories
    )
}