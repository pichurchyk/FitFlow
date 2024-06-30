package com.pichurchyk.fitflow.auth.model

import com.google.firebase.auth.FirebaseUser

sealed class SignInResult {
    data class Success(val firebaseUser: FirebaseUser): SignInResult()

    data class Error(val errorMessage: String): SignInResult()

    data object Cancelled: SignInResult()
}