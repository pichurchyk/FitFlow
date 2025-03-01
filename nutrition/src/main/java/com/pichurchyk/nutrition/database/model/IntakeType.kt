package com.pichurchyk.nutrition.database.model

enum class IntakeType {
    FAT,
    CARBS,
    PROTEIN,
    CALORIES,

    WATER;

    companion object {
        fun getMainTypes(): List<IntakeType> = listOf(CARBS, PROTEIN, FAT, WATER)
    }
}
