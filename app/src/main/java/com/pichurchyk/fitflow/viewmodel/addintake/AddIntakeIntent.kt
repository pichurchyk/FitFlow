package com.pichurchyk.fitflow.viewmodel.addintake

import com.pichurchyk.nutrition.database.model.IntakeType

sealed class AddIntakeIntent {
    data class ChangeIntakeValue(val value: Int, val intakeType: IntakeType): AddIntakeIntent()
    data object CloseError: AddIntakeIntent()
    data object Submit: AddIntakeIntent()
    data object Reset: AddIntakeIntent()
}