package com.pichurchyk.fitflow.auth.usecase

import com.pichurchyk.fitflow.auth.repository.AuthRepository
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface GetSignedInUserUseCase {
    suspend fun invoke(): Flow<UserInfo?>
}

internal class GetSignedInUserImpl(
    private val repository: AuthRepository
) : GetSignedInUserUseCase {
    override suspend fun invoke() = repository.getSignedInUser()

}