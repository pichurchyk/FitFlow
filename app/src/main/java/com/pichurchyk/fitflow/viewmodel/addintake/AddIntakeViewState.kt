package com.pichurchyk.fitflow.viewmodel.addintake

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.create.CreateIntakeModel

sealed class AddIntakeViewState(open val intake: CreateIntakeModel) {

    data class Loading(override val intake: CreateIntakeModel) : AddIntakeViewState(intake)
    data class Init(override val intake: CreateIntakeModel) : AddIntakeViewState(intake)

    data class ValidationException(
        override val intake: CreateIntakeModel,
        val validationException: AddIntakeValidationException
    ) : AddIntakeViewState(intake)

    data class Error(
        override val intake: CreateIntakeModel,
        val errorMessage: String
    ) : AddIntakeViewState(intake)

    data class Success(override val intake: CreateIntakeModel): AddIntakeViewState(intake)

    fun getValuesSumOfType(intakeType: IntakeType): Int {
        return intake.values.filter { it.intakeType == intakeType }.sumOf { it.value }
    }
}