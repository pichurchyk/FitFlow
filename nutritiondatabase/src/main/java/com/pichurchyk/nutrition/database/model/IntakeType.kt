package com.pichurchyk.nutrition.database.model

enum class IntakeType {
    FAT,
    CARBS,
    PROTEIN,
    CALORIES;

    companion object {
        fun getMainTypes(): List<IntakeType> = listOf(CARBS, PROTEIN, FAT, CALORIES)
    }
}
