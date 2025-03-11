package com.pichurchyk.fitflow.viewmodel.splash

sealed class SplashIntent {
    data object CheckIfUserSignedIn: SplashIntent()
}