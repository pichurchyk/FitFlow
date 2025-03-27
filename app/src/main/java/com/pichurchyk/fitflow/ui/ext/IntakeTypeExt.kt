package com.pichurchyk.fitflow.ui.ext

import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.common.R as commonR
import com.pichurchyk.fitflow.common.ui.theme.color_calories
import com.pichurchyk.fitflow.common.ui.theme.color_carbs
import com.pichurchyk.fitflow.common.ui.theme.color_fat
import com.pichurchyk.fitflow.common.ui.theme.color_protein
import com.pichurchyk.fitflow.common.ui.theme.color_water
import com.pichurchyk.nutrition.database.model.IntakeType

fun IntakeType.getColor() = when (this) {
    IntakeType.FAT -> color_fat
    IntakeType.CARBS -> color_carbs
    IntakeType.PROTEIN -> color_protein
    IntakeType.CALORIES -> color_calories
    IntakeType.WATER -> color_water
}

fun IntakeType.getTitle() = when (this) {
    IntakeType.FAT -> commonR.string.fat
    IntakeType.CARBS -> commonR.string.carbs
    IntakeType.PROTEIN -> commonR.string.protein
    IntakeType.CALORIES -> commonR.string.calories
    IntakeType.WATER -> commonR.string.water
}

fun IntakeType.getUnit(): Int = when (this) {
    IntakeType.FAT -> commonR.string.unit_gram_long
    IntakeType.CARBS -> commonR.string.unit_gram_long
    IntakeType.PROTEIN -> commonR.string.unit_gram_long
    IntakeType.CALORIES -> commonR.string.unit_kcal
    IntakeType.WATER -> commonR.string.unit_ml
}

fun IntakeType.getUnitWithValue(): Int = when (this) {
    IntakeType.FAT -> R.string.unit_with_value_gram
    IntakeType.CARBS -> R.string.unit_with_value_gram
    IntakeType.PROTEIN -> R.string.unit_with_value_gram
    IntakeType.CALORIES -> R.string.unit_with_value_kcal
    IntakeType.WATER -> R.string.unit_with_value_ml
}