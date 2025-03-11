package com.pichurchyk.fitflow.viewmodel.splash

sealed class SplashViewState {
    data object Loading: SplashViewState()

    data object UserSignedIn: SplashViewState()

    data object UserNotSignedIn: SplashViewState()
}