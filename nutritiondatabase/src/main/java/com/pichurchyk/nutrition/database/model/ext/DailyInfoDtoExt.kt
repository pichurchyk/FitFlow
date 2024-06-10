package com.pichurchyk.nutrition.database.model.ext

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dto.DailyInfoDTO
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO

fun DailyInfoDTO.getFat(): IntakeDTO {
    return this.intakes.firstOrNull { it.type == IntakeType.FAT }
        ?: IntakeDTO.empty(date, IntakeType.FAT)
}

fun DailyInfoDTO.getCarbs(): IntakeDTO {
    return this.intakes.firstOrNull { it.type == IntakeType.CARBS }
        ?: IntakeDTO.empty(date, IntakeType.CARBS)
}

fun DailyInfoDTO.getProtein(): IntakeDTO {
    return this.intakes.firstOrNull { it.type == IntakeType.PROTEIN }
        ?: IntakeDTO.empty(date, IntakeType.PROTEIN)
}