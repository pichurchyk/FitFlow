package com.pichurchyk.nutrition.remote.mapper

import com.pichurchyk.nutrition.model.IntakeDTO
import com.pichurchyk.nutrition.remote.model.IntakePayload
import com.pichurchyk.nutrition.remote.model.IntakeResponse
import java.util.Date

object IntakeRemoteMapper {

    fun fromRemote(response: IntakeResponse): IntakeDTO = IntakeDTO(
        id = response.id,
        date = Date(response.date),
        value = response.value,
        type = response.type,
    )

    fun fromDTO(dto: IntakeDTO): IntakePayload = IntakePayload(
        date = dto.date.time,
        value = dto.value,
        type = dto.type
    )
}