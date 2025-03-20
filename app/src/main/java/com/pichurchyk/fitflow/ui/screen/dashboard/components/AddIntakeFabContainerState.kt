package com.pichurchyk.fitflow.ui.screen.dashboard.components

sealed class AddIntakeFabContainerState {

    data class Fab(val isExpanded: Boolean): AddIntakeFabContainerState()

    data object AddIntakeScreen: AddIntakeFabContainerState()

    data object AddWaterIntakeScreen: AddIntakeFabContainerState()
}
