package com.pichurchyk.fitflow.auth.repository

import com.pichurchyk.fitflow.auth.datasource.AuthDataSource
import com.pichurchyk.fitflow.auth.model.SignInResult
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow

internal class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource
): AuthRepository {

    override suspend fun signIn(googleIdToken: String): Flow<SignInResult> {
        return authDataSource.signIn(googleIdToken)
    }

    override suspend fun getSignedInUser(): Flow<UserInfo?> {
        return authDataSource.getSignedInUser()
    }

    override suspend fun signOut(): Flow<Unit> {
        return authDataSource.signOut()
    }
}