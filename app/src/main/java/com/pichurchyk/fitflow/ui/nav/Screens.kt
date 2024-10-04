package com.pichurchyk.fitflow.ui.nav

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object Auth: Screen()

    @Serializable
    data object Splash: Screen()

    @Serializable
    data object Dashboard: Screen()

    @Serializable
    data class AddIntake(val selectedDateMillis: Long): Screen()

    @Serializable
    data class AddWaterIntake(val selectedDateMillis: Long): Screen()
}