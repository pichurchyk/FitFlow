package com.pichurchyk.fitflow.ui.ext

import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeValidationException

fun AddIntakeValidationException.getText(): Int = when(this) {
    AddIntakeValidationException.EMPTY_VALUES -> R.string.validation_intake_empty_fields
    AddIntakeValidationException.EMPTY_CALORIES -> R.string.validation_empty_calories
}