package com.pichurchyk.profile.domain.usecase

import com.pichurchyk.profile.domain.model.UserParams
import com.pichurchyk.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

interface GetUserParamsUseCase {
    suspend fun invoke(): Flow<UserParams>
}

internal class GetUserParamsUseCaseImpl(
    private val repository: ProfileRepository
) : GetUserParamsUseCase {
    override suspend fun invoke() = repository.getUserParams()

}