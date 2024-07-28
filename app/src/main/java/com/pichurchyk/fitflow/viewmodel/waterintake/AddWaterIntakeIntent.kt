package com.pichurchyk.fitflow.viewmodel.waterintake

import com.pichurchyk.nutrition.database.model.IntakeType

sealed class AddWaterIntakeIntent {
    data class ChangeIntakeValue(val value: Int, val intakeType: IntakeType): AddWaterIntakeIntent()
    data object CloseError: AddWaterIntakeIntent()
    data object Submit: AddWaterIntakeIntent()
    data object Reset: AddWaterIntakeIntent()
}