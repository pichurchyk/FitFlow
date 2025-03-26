package com.pichurchyk.fitflow.auth.repository

import com.pichurchyk.fitflow.auth.model.SignInResult
import com.pichurchyk.fitflow.auth.model.User
import kotlinx.coroutines.flow.Flow

internal interface AuthRepository {

    suspend fun signIn(googleIdToken: String): Flow<SignInResult>

    suspend fun getSignedInUser(): Flow<User?>

    suspend fun signOut(): Flow<Unit>

}