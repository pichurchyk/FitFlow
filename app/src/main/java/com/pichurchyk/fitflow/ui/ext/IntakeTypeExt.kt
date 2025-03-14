package com.pichurchyk.fitflow.ui.ext

import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.theme.color_calories
import com.pichurchyk.fitflow.ui.theme.color_carbs
import com.pichurchyk.fitflow.ui.theme.color_fat
import com.pichurchyk.fitflow.ui.theme.color_protein
import com.pichurchyk.fitflow.ui.theme.color_water
import com.pichurchyk.nutrition.database.model.IntakeType

fun IntakeType.getColor() = when (this) {
    IntakeType.FAT -> color_fat
    IntakeType.CARBS -> color_carbs
    IntakeType.PROTEIN -> color_protein
    IntakeType.CALORIES -> color_calories
    IntakeType.WATER -> color_water
}

fun IntakeType.getTitle() = when (this) {
    IntakeType.FAT -> R.string.fat
    IntakeType.CARBS -> R.string.carbs
    IntakeType.PROTEIN -> R.string.protein
    IntakeType.CALORIES -> R.string.calories
    IntakeType.WATER -> R.string.water
}

fun IntakeType.getUnit(): Int = when (this) {
    IntakeType.FAT -> R.string.unit_gram
    IntakeType.CARBS -> R.string.unit_gram
    IntakeType.PROTEIN -> R.string.unit_gram
    IntakeType.CALORIES -> R.string.unit_kcal
    IntakeType.WATER -> R.string.unit_ml
}

fun IntakeType.getUnitWithValue(): Int = when (this) {
    IntakeType.FAT -> R.string.unit_with_value_gram
    IntakeType.CARBS -> R.string.unit_with_value_gram
    IntakeType.PROTEIN -> R.string.unit_with_value_gram
    IntakeType.CALORIES -> R.string.unit_with_value_kcal
    IntakeType.WATER -> R.string.unit_with_value_ml
}