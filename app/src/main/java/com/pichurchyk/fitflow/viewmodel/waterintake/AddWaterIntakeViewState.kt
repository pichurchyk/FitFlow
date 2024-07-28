package com.pichurchyk.fitflow.viewmodel.waterintake

import com.pichurchyk.nutrition.database.model.dto.IntakeDTO

sealed class AddWaterIntakeViewState(open val value: IntakeDTO) {

    data class Loading(override val value: IntakeDTO) : AddWaterIntakeViewState(value)
    data class Init(override val value: IntakeDTO) : AddWaterIntakeViewState(value)

    data class Error(
        override val value: IntakeDTO,
        val errorMessage: String
    ) : AddWaterIntakeViewState(value)

    data class Success(override val value: IntakeDTO): AddWaterIntakeViewState(value)
}