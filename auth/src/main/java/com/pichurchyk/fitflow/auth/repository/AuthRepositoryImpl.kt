package com.pichurchyk.fitflow.auth.repository

import com.pichurchyk.fitflow.auth.datasource.AuthDataSource
import com.pichurchyk.fitflow.auth.ext.toUser
import com.pichurchyk.fitflow.auth.model.SignInResult
import com.pichurchyk.fitflow.auth.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource
): AuthRepository {

    override suspend fun signIn(googleIdToken: String): Flow<SignInResult> {
        return authDataSource.signIn(googleIdToken)
    }

    override suspend fun getSignedInUser(): Flow<User?> {
        return authDataSource.getSignedInUser().map { it?.toUser() }
    }

    override suspend fun signOut(): Flow<Unit> {
        return authDataSource.signOut()
    }
}