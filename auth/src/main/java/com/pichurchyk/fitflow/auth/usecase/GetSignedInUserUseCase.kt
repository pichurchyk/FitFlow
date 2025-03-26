package com.pichurchyk.fitflow.auth.usecase

import com.pichurchyk.fitflow.auth.model.User
import com.pichurchyk.fitflow.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

interface GetSignedInUserUseCase {
    suspend fun invoke(): Flow<User?>
}

internal class GetSignedInUserUseCaseImpl(
    private val repository: AuthRepository
) : GetSignedInUserUseCase {
    override suspend fun invoke() = repository.getSignedInUser()

}