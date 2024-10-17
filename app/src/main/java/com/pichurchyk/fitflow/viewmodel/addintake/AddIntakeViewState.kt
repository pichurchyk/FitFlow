package com.pichurchyk.fitflow.viewmodel.addintake

import com.pichurchyk.nutrition.model.IntakeDTO

sealed class AddIntakeViewState(open val intakes: List<IntakeDTO>) {

    data class Loading(override val intakes: List<IntakeDTO>) : AddIntakeViewState(intakes)
    data class Init(override val intakes: List<IntakeDTO>) : AddIntakeViewState(intakes)

    data class ValidationException(
        override val intakes: List<IntakeDTO>,
        val validationException: AddIntakeValidationException
    ) : AddIntakeViewState(intakes)

    data class Error(
        override val intakes: List<IntakeDTO>,
        val errorMessage: String
    ) : AddIntakeViewState(intakes)

    data class Success(override val intakes: List<IntakeDTO>): AddIntakeViewState(intakes = intakes)
}