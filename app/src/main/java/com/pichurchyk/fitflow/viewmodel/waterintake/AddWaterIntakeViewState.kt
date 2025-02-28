package com.pichurchyk.fitflow.viewmodel.waterintake

import com.pichurchyk.nutrition.model.CreateIntakeModel

sealed class AddWaterIntakeViewState(open val intake: CreateIntakeModel) {

    data class Loading(override val intake: CreateIntakeModel) : AddWaterIntakeViewState(intake)
    data class Init(override val intake: CreateIntakeModel) : AddWaterIntakeViewState(intake)

    data class Error(
        override val intake: CreateIntakeModel,
        val errorMessage: String
    ) : AddWaterIntakeViewState(intake)

    data class ValidationException(
        override val intake: CreateIntakeModel,
        val validationException: AddWaterIntakeValidationException
    ) : AddWaterIntakeViewState(intake)

    data class Success(override val intake: CreateIntakeModel): AddWaterIntakeViewState(intake)
}