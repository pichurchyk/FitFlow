package com.pichurchyk.nutrition.database.model

enum class IntakeType(val calories: Double) {
    FAT(9.3),
    CARBS(4.1),
    PROTEIN(4.1),

    WATER(0.0);

    companion object {
        fun getMainTypes(): List<IntakeType> = listOf(CARBS, PROTEIN, FAT, WATER)
    }
}
