package com.pichurchyk.profile.data.repository

import com.pichurchyk.profile.data.source.ProfileDataSource
import com.pichurchyk.profile.domain.ext.toDomain
import com.pichurchyk.profile.domain.model.UserParams
import com.pichurchyk.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(
    private val dataSource: ProfileDataSource
) : ProfileRepository {

    override suspend fun getUserParams(): Flow<UserParams> =
        dataSource
            .getUserParams()
            .map { it.toDomain() }
}