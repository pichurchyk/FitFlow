package com.pichurchyk.fitflow.viewmodel.waterintake

import com.pichurchyk.nutrition.model.IntakeDTO

sealed class AddWaterIntakeViewState(open val intake: IntakeDTO) {

    data class Loading(override val intake: IntakeDTO) : AddWaterIntakeViewState(intake)
    data class Init(override val intake: IntakeDTO) : AddWaterIntakeViewState(intake)

    data class Error(
        override val intake: IntakeDTO,
        val errorMessage: String
    ) : AddWaterIntakeViewState(intake)

    data class ValidationException(
        override val intake: IntakeDTO,
        val validationException: AddWaterIntakeValidationException
    ) : AddWaterIntakeViewState(intake)

    data class Success(override val intake: IntakeDTO): AddWaterIntakeViewState(intake)
}