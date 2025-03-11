package com.pichurchyk.fitflow.viewmodel.auth

sealed class AuthIntent {
    data class Auth(val googleIdToken: String): AuthIntent()
    data object Clear: AuthIntent()
}