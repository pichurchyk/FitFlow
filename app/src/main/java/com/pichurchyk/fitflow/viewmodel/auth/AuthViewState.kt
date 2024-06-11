package com.pichurchyk.fitflow.viewmodel.auth

sealed class AuthViewState {
    data object Init: AuthViewState()

    data object Loading: AuthViewState()

    data object Success: AuthViewState()

    data class Error(val message: String): AuthViewState()
}