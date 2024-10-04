package com.pichurchyk.fitflow.auth.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.pichurchyk.fitflow.auth.datasource.AuthDataSource
import com.pichurchyk.fitflow.auth.model.SignInResult
import kotlinx.coroutines.flow.Flow

internal class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource
): AuthRepository {

    override suspend fun signIn(authCredential: AuthCredential): Flow<SignInResult> {
        return authDataSource.signIn(authCredential)
    }

    override suspend fun getSignedInUser(): Flow<FirebaseUser?> {
        return authDataSource.getSignedInUser()
    }

    override suspend fun signOut(): Flow<Unit> {
        return authDataSource.signOut()
    }
}