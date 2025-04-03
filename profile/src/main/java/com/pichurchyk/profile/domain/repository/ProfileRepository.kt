package com.pichurchyk.profile.domain.repository

import com.pichurchyk.profile.domain.model.UserParams
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserParams(): Flow<UserParams>
}