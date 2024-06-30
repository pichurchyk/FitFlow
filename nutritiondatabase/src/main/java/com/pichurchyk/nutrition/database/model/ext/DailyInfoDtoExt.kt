package com.pichurchyk.nutrition.database.model.ext

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dto.DailyInfoDTO
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO

fun DailyInfoDTO.getFat(): List<IntakeDTO> {
    return this.intakes.filter { it.type == IntakeType.FAT }
}

fun DailyInfoDTO.getCarbs(): List<IntakeDTO> {
    return this.intakes.filter { it.type == IntakeType.CARBS }
}

fun DailyInfoDTO.getProtein(): List<IntakeDTO> {
    return this.intakes.filter { it.type == IntakeType.PROTEIN }
}