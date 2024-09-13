package com.pichurchyk.fitflow.ui.screen.dashboard.components

sealed class DashboardFabContainerState {

    data object Fab: DashboardFabContainerState()

    data object AddIntakeScreen: DashboardFabContainerState()

    data object AddWaterIntakeScreen: DashboardFabContainerState()
}
