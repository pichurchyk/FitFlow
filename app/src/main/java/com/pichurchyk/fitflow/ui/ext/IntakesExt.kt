package com.pichurchyk.fitflow.ui.ext

import com.pichurchyk.fitflow.ui.model.CombinedIntakesByType
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.Intake

fun List<Intake>.groupByIntakeType(): List<CombinedIntakesByType> {
    // Extract all unique intake types from the IntakeDTO list
    val allTypes = this.flatMap { it.values.map { intakeValue -> intakeValue.intakeType } }
        .distinct()

    // Create a list of CombinedIntakesByType, grouping IntakeDTOs by type
    return allTypes.map { type ->
        val filteredIntakes = this.filter { intakeDTO ->
            intakeDTO.values.any { it.intakeType == type }
        }
        CombinedIntakesByType(type, filteredIntakes)
    }
}

fun List<Intake>.filterByType(intakeType: IntakeType): List<Intake> {
    return this.mapNotNull { intake ->
        val filteredValues = intake.values.filter { it.intakeType == intakeType }
        if (filteredValues.isNotEmpty()) {
            intake.copy(values = filteredValues)
        } else {
            null
        }
    }
}
