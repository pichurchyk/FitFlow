package com.pichurchyk.fitflow.auth.repository

import com.pichurchyk.fitflow.auth.model.SignInResult
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow

internal interface AuthRepository {

    suspend fun signIn(googleIdToken: String): Flow<SignInResult>

    suspend fun getSignedInUser(): Flow<UserInfo?>

    suspend fun signOut(): Flow<Unit>

}