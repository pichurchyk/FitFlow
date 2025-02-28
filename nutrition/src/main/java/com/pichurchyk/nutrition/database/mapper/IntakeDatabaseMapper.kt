package com.pichurchyk.nutrition.database.mapper

import com.pichurchyk.nutrition.database.model.dbo.IntakeDBO
import com.pichurchyk.nutrition.model.CreateIntakeModel
import com.pichurchyk.nutrition.model.IntakeDTO

object IntakeDatabaseMapper {

    fun fromDto(dto: IntakeDTO): IntakeDBO = IntakeDBO(
        date = dto.date,
        value = dto.value,
        type = dto.type,
        id = dto.id
    )

    fun fromCreationModel(dto: CreateIntakeModel): IntakeDBO = IntakeDBO(
        date = dto.date,
        value = dto.value,
        type = dto.type,
    )

    fun fromDbo(dbo: IntakeDBO): IntakeDTO = IntakeDTO(
        date = dbo.date,
        value = dbo.value,
        type = dbo.type,
        id = dbo.id
    )
}