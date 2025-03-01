package com.pichurchyk.fitflow.ui.ext

import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.viewmodel.waterintake.AddWaterIntakeValidationException

fun AddWaterIntakeValidationException.getText(): Int = when(this) {
    AddWaterIntakeValidationException.EMPTY_VALUE -> R.string.validation_water_intake_empty
}