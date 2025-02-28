package com.pichurchyk.fitflow.viewmodel.addintake

import com.pichurchyk.nutrition.model.CreateIntakeModel

sealed class AddIntakeViewState(open val intakes: List<CreateIntakeModel>) {

    data class Loading(override val intakes: List<CreateIntakeModel>) : AddIntakeViewState(intakes)
    data class Init(override val intakes: List<CreateIntakeModel>) : AddIntakeViewState(intakes)

    data class ValidationException(
        override val intakes: List<CreateIntakeModel>,
        val validationException: AddIntakeValidationException
    ) : AddIntakeViewState(intakes)

    data class Error(
        override val intakes: List<CreateIntakeModel>,
        val errorMessage: String
    ) : AddIntakeViewState(intakes)

    data class Success(override val intakes: List<CreateIntakeModel>): AddIntakeViewState(intakes = intakes)
}