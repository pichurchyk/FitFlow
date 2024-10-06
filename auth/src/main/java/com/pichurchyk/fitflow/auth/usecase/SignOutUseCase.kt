package com.pichurchyk.fitflow.auth.usecase

import com.pichurchyk.fitflow.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

interface SignOutUseCase {
    suspend fun invoke(): Flow<Unit>
}

internal class SignOutUseCaseImpl(
    private val repository: AuthRepository
) : SignOutUseCase {
    override suspend fun invoke() = repository.signOut()

}