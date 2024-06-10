package com.pichurchyk.nutrition.database.model.ext

import com.pichurchyk.nutrition.database.model.dto.IntakeDTO

fun List<IntakeDTO>.getIntakesSum(): Double {
    return this.sumOf { intake -> intake.value }
}

fun List<IntakeDTO>.getCaloriesSum(): Int {
    return this.sumOf { intake -> intake.calories }
}