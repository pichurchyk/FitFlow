package com.pichurchyk.nutrition.database.model.ext

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.DailyInfoDTO
import com.pichurchyk.nutrition.model.IntakeDTO

fun DailyInfoDTO.getFat(): List<IntakeDTO> {
    return this.intakes.filter { it.type == IntakeType.FAT }
}

fun DailyInfoDTO.getCarbs(): List<IntakeDTO> {
    return this.intakes.filter { it.type == IntakeType.CARBS }
}

fun DailyInfoDTO.getProtein(): List<IntakeDTO> {
    return this.intakes.filter { it.type == IntakeType.PROTEIN }
}

fun DailyInfoDTO.getWater(): List<IntakeDTO> {
    return this.intakes.filter { it.type == IntakeType.WATER }
}