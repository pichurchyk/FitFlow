package com.pichurchyk.fitflow.ui.ext

import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.theme.color_carbs
import com.pichurchyk.fitflow.ui.theme.color_fat
import com.pichurchyk.fitflow.ui.theme.color_protein
import com.pichurchyk.nutrition.database.model.IntakeType

fun IntakeType.getColor() = when (this) {
    IntakeType.FAT -> color_fat
    IntakeType.CARBS -> color_carbs
    IntakeType.PROTEIN -> color_protein
}

fun IntakeType.getTitle() = when (this) {
    IntakeType.FAT -> R.string.fat
    IntakeType.CARBS -> R.string.carbs
    IntakeType.PROTEIN -> R.string.protein
}