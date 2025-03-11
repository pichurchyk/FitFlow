package com.pichurchyk.fitflow.ui.screen.dashboard.components

sealed class DashboardFabContainerState {

    data class Fab(val isExpanded: Boolean): DashboardFabContainerState()

    data object AddIntakeScreen: DashboardFabContainerState()

    data object AddWaterIntakeScreen: DashboardFabContainerState()
}
