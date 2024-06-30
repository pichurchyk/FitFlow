package com.pichurchyk.fitflow.viewmodel.auth

import com.google.firebase.auth.AuthCredential

sealed class AuthIntent {
    data class Auth(val credentials: AuthCredential): AuthIntent()
    data object Clear: AuthIntent()
}