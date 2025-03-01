package com.pichurchyk.fitflow.ui.model

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.Intake

data class CombinedIntakesByType(
    val intakeType: IntakeType,
    val intakes: List<Intake>
) {
    init {
        require(intakes.all { intakeDTO ->
            intakeDTO.values.any { it.intakeType == intakeType }
        }) {
            "All intakes in the list must contain at least one IntakeValue of type: $intakeType"
        }
    }
}
