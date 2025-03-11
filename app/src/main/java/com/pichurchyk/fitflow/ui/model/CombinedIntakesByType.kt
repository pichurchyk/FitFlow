package com.pichurchyk.fitflow.ui.model

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.Intake
import com.pichurchyk.nutrition.model.IntakeValue

data class CombinedIntakesByType(
    val intakeType: IntakeType,
    val intakes: List<IntakeValue>
) {
    init {
        require(intakes.all { intakeValue ->
            intakeValue.intakeType == intakeType
        }) {
            "All intakes in the list must contain at least one IntakeValue of type: $intakeType"
        }
    }
}
