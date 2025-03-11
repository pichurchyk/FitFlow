package com.pichurchyk.fitflow.viewmodel.waterintake

import com.pichurchyk.nutrition.model.create.CreateWaterIntakeModel


sealed class AddWaterIntakeViewState(open val intake: CreateWaterIntakeModel) {

    data class Loading(override val intake: CreateWaterIntakeModel) : AddWaterIntakeViewState(intake)
    data class Init(override val intake: CreateWaterIntakeModel) : AddWaterIntakeViewState(intake)

    data class Error(
        override val intake: CreateWaterIntakeModel,
        val errorMessage: String
    ) : AddWaterIntakeViewState(intake)

    data class ValidationException(
        override val intake: CreateWaterIntakeModel,
        val validationException: AddWaterIntakeValidationException
    ) : AddWaterIntakeViewState(intake)

    data class Success(override val intake: CreateWaterIntakeModel): AddWaterIntakeViewState(intake)
}