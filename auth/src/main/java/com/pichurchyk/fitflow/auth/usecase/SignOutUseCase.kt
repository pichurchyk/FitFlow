package com.pichurchyk.fitflow.auth.usecase

import com.google.firebase.auth.AuthCredential
import com.pichurchyk.fitflow.auth.model.SignInResult
import com.pichurchyk.fitflow.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

interface SignInUseCase {
    suspend fun invoke(authCredential: AuthCredential): Flow<SignInResult>
}

internal class SignInUseCaseImpl(
    private val repository: AuthRepository
) : SignInUseCase {
    override suspend fun invoke(authCredential: AuthCredential) = repository.signIn(authCredential)

}