package com.pichurchyk.fitflow.auth.usecase

import com.google.firebase.auth.FirebaseUser
import com.pichurchyk.fitflow.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

interface GetSignedInUserUseCase {
    suspend fun invoke(): Flow<FirebaseUser?>
}

internal class GetSignedInUserImpl(
    private val repository: AuthRepository
) : GetSignedInUserUseCase {
    override suspend fun invoke() = repository.getSignedInUser()

}