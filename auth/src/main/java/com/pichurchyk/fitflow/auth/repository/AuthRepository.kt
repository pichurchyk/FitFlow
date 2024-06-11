package com.pichurchyk.fitflow.auth.repository

import com.google.firebase.auth.AuthCredential
import com.pichurchyk.fitflow.auth.model.SignInResult
import kotlinx.coroutines.flow.Flow

internal interface AuthRepository {

    suspend fun checkIsUserAuthenticated(): Flow<Boolean>

    suspend fun signIn(authCredential: AuthCredential): Flow<SignInResult>

    suspend fun signOut(): Flow<Unit>

}