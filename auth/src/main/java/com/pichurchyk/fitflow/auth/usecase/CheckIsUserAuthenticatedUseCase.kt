package com.pichurchyk.fitflow.auth.usecase

import com.google.firebase.auth.AuthCredential
import com.pichurchyk.fitflow.auth.model.SignInResult
import com.pichurchyk.fitflow.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

interface CheckIsUserAuthenticatedUseCase {
    suspend fun invoke(): Flow<Boolean>
}

internal class CheckIsUserAuthenticatedUseCaseImpl(
    private val repository: AuthRepository
) : CheckIsUserAuthenticatedUseCase {
    override suspend fun invoke() = repository.checkIsUserAuthenticated()

}