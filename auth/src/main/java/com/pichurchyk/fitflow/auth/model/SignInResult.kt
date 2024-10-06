package com.pichurchyk.fitflow.auth.model

import io.github.jan.supabase.gotrue.user.UserInfo

sealed class SignInResult {
    data class Success(val user: UserInfo): SignInResult()

    data class Error(val errorMessage: String): SignInResult()

    data object Cancelled: SignInResult()
}