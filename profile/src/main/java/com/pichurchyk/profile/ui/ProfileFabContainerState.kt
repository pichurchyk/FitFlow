package com.pichurchyk.profile.ui

sealed class ProfileFabContainerState {

    data object Fab: ProfileFabContainerState()

    data object Screen: ProfileFabContainerState()
}
